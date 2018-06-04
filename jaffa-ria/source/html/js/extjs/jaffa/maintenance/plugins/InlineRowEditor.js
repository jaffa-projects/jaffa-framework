/*
 * @author ChrisO
 */

/**
 * @class Jaffa.maintenance.plugins.InlineRowEditor
 * @extends Ext.ux.grid.RowEditor
 *
 * Plugin (ptype = 'maintenanceinlineroweditor') that adds the ability to rapidly edit full rows in a grid when doubleclicking a row.
 * The inline editor can handle Jaffa metaClass rules to construct the editor fields on the fly.
 * By default the errorSummary popup has been disabled because it was annoying and buggy.
 * Validation only occurs when bluring a field rather than when startEditing occurs.
 * Each field's error messages in the roweditor now contain the column names in the messages.
 * Any grid that has this plugin may reference it's roweditor by using this.editor.
 * Events associated with CRUD have been added.
 *
 * @ptype maintenanceinlineroweditor
 */
Jaffa.maintenance.plugins.InlineRowEditor = Ext.extend(Ext.ux.grid.RowEditor, {

  /**
   * @cfg {Number} clicksToEdit
   * amount of clicks it takes to initialize the roweditor (defaults to 2)
   */
  clicksToEdit: 2,

  /**
   * @cfg {String} metaClass
   * name of the meta-class within the ClassMetaData graph
   * (defaults to 2)
   */
  metaClass: undefined,

  /**
   * @cfg {Boolean} allowAdd
   * true to allow the user to add new records and enables the add button (defaults to true)
   * false to disable the add button
   * Note: setting allowAdd to undefined will hide the add button
   */
  allowAdd: true,

  /**
   * @cfg {Boolean} allowEdit
   * allows the user to edit records (defaults to true)
   */
  allowEdit: true,

  /**
   * @cfg {Boolean} allowDelete
   * true to allow the user to delete the selected records and enables the delete button (defaults to true)
   * false to disable the delete button
   * Note: setting allowDelete to undefined will hide the delete button
   */
  allowDelete: true,
  /**
   * @cfg {Number} crudButtonsPosition
   * the zero based index that determines where the add and delete buttons are inserted (defaults to 0)
   */
  crudButtonsPosition: 0,
  /**
   * @cfg {Boolean} useSequence
   * if true will remove the primary key references before saving phantom records. (defaults to false)
   * set to true when creating a record that uses a technical key or primary key sequence
   */
  useSequence: false,
  /**
   * @cfg {Boolean} errorSummary
   * true to display a summary list of errors in a blinking tooltip (defaults to false)
   */
  errorSummary: false,
  /**
   * @cfg {Array} includeList
   * a list of metadata validation categories to use when editing a row
   * (defaults to ['CASE', 'MINLENGTH', 'MAXLENGTH', 'MANDATORY', 'MINVALUE', 'MAXVALUE'])
   */
  includeList: ['CASE', 'MINLENGTH', 'MAXLENGTH', 'MANDATORY', 'MINVALUE', 'MAXVALUE'],
  /**
   * @cfg {Ext.ProgressBar} savingProgressBar
   * the progress bar that is displayed when clicking save
   * (creates a new progressBar by default)
   */
  savingProgressBar: new Ext.ProgressBar(),
  /**
   * @cfg {String} waitingText
   * the text that is displayed in the savingProgressBar
   * (defaults to 'Saving Record...')
   */
  waitingText: 'Saving Record...',
  /**
   * @cfg {String} loadingText
   * the text that is displayed in the savingProgressBar when reloading a record, this is called after the record is saved
   * (defaults to 'Loading Record...')
   */
  loadingText: 'Loading Record...',
   /**
   * @cfg {Boolean} disableToolbarsWhenEditing
   * when true the parent grid's toolbars will be disabled when in edit mode and re-enable after editing
   * (defaults to true)
   */
  disableToolbarsWhenEditing: true,
  /**
   * @cfg {String} duplicateKeyText
   * the text that is displayed if a duplicate key gets detected during insertion.
   * (defaults to 'Duplicate Key. A record with this key exists in the database.')
   */
  duplicateKeyText: 'Duplicate Key. A record with this key exists in the database.',
  /**
   * @cfg {String} buttonErrorTitle
   * the text that is displayed as the title of alert box when error occurs.
   * (defaults to 'Error')
   */
  buttonErrorTitle: 'Error',
  /**
   * @cfg {String} duplicateKeyText
   * the text that is displayed if no record is selected for delete operation.
   * (defaults to 'You must select at least one record before performing this action.')
   */
  removeButtonSelectAtLeastOneRecordText:'You must select at least one record before performing this action.',
  
  isRequiredText: 'is required',
  
  theValueInText: 'The value in',
  
  isInvalidText: 'is invalid',
  
  maximumLengthMsgText: 'The maximum length for',
  
  minimumLengthMsgText: 'The minimum length for',
  
  isZeroText: 'is {0}',
  
  addButtonText: 'Add',
    
  deleteButtonText: 'Delete',
  
  deleteSelectedTitleText: 'Delete Selected?',
  
  deletePromptMessageText: 'Are you sure you want to delete the selected record(s)?',
  
  saveErrorsTitleText : 'Save Errors',
    
  /**
   * getSaveGraph
   * uses the controller's getModifiedRecordData as a static method to build a saveModel
   * The saveGraph is build using the field mappings
   * @param {Ext.Record} rec the record that is being edited
   * @return {Object} saveGraph
   */
  getSaveGraph: function(rec){
    var saveGraph = {};
    Jaffa.component.PanelController.prototype.getModifiedRecordData(rec, saveGraph, rec.json, this.metaClass);
    return saveGraph;
  },

  /**
   * setColumnMandatoryFlag
   * appends a red asteric to the text in the column header to indicate that it is required
   * @param {Ext.grid.Column} column the column to apply a mandatory flag to
   */
  setColumnMandatoryFlag: function(column){
    var cm = this.grid.getColumnModel();
    var headerTxt = '<span class="x-mand-flag">*</span>' + column.header;
    cm.setColumnHeader(column.index, headerTxt);
  },

  /**
   * applyMetaRulesToEditor
   * used to help build an editor based on its datatype and metadata information
   * @param {Object} editor the editor config object
   * @return {Object} editor the editor config object after applying metadata
   */
  applyMetaRulesToEditor: function(editor){
    editor.initialConfig = editor;
    var metaClass = editor.metaClass || this.metaClass ;
    if(metaClass) {
      if(!editor.xtype) this.determineXtypeFromMetadata(editor);
      var metaRules = ClassMetaData[metaClass].fields[editor.metaField || editor.mapping];
      if (metaRules ) {
        if( metaRules.mandatory === true){
          // this.setColumnMandatoryFlag(editor.column);
        }
        if (metaClass && Ext.isString(metaClass)) {
          // metaClass can be specified as the name of the graph class inside of ClassMetaData or just the graph class
          metaClass = ClassMetaData[metaClass] ;
        }
        Jaffa.component.PanelController.prototype.applyValidationMetaData(editor, metaClass, metaRules, this.includeList);
      }
    }
    return editor;
  },
  /**
   * generateEditors
   * creates an editor for each editable column that is visible
   * Also adds default properties to an editor such as invalid messages, a reference to its corresponding column and validation behavior
   */
  generateEditors: function(){
    var cm = this.grid.getColumnModel();
    for (var i = 0, len = cm.getColumnCount(); i < len; i++) {
      var c = cm.getColumnAt(i);
      c.index = i;
      if (c.editable !== false) {
        // See if the editor has already been initialized
        if(!c.editor || !c.editor.initialConfig) {
          // Create a default editor config
          var editor = {
            mapping: c.mapping || c.dataIndex,
            metaClass: this.meta || this.metaClass,
            blankText: c.header + ' '+this.isRequiredText+'. ',
            invalidText: this.theValueInText+' ' + c.header + ' '+this.isInvalidText,
            maxLengthText: this.maximumLengthMsgText+' ' + c.header + ' '+this.isZeroText,
            minLengthText: this.minimumLengthMsgText+' ' + c.header + ' '+this.isZeroText,
            column: c,
            preventMark: true
          };
 
          // Add in the config stuff, typically this will not happen, if editor is provided it gets constructed before this point
          if (c.editor) {
            editor = Ext.applyIf(c.editor, editor);
          }
          // Apply Metadata to this object which may select its xtype and other attributes. Need to apply before setting editor
           this.applyMetaRulesToEditor(editor);

          // Now we can construct this editor
          c.setEditor(editor);
          if (c.getEditor().xtype=='combo') {
            var vvFn = c.getEditor().validateValue;
            c.getEditor().validateValue = function(value) {
              return vvFn.call(this, this.getValue()==null?'':this.getValue());
            };
          };
          
        } else {
          // Apply Metadata to this object which may select its xtype and other attributes
          this.applyMetaRulesToEditor(c.editor);
        }
        
        // Fixes IE bug where the cursor is not visible for right aligned fields
        if(Ext.isIE && c.editor && c.align ==='right'){
          c.editor.style += 'padding-right: 1px;';
        }
        
        // Apply special events to editor to make it work in the inline fashion.
        c.editor.on({
          show: function(){
            this.clearInvalid();
            this.preventMark = true;
          },
          render : function(){
            //Centering the checkbox in the editor column, otherwise it is rendering to extreme left.
            if(this.xtype === 'checkbox' && this.wrap){
              this.wrap.setStyle('text-align','center');
            }
          },
          beforeselect: function(cbo, record, index){ //this sets the preventRowSave flag that is evaluated in the inlineroweditor's onKey handler
            this.preventRowSave = (this.isExpanded && this.isExpanded());
          },
          blur: function(f){
            this.preventMark = false;
            if (this.validate() === true && f.column.key === true && String(f.getValue()).length > 0) {
              var rowEditor = f.ownerCt;
              var selCriteria = {};
              selCriteria.objectLimit = 1;
              for(var i=0; i < rowEditor.items.getCount() > 0; i++){
                var field = rowEditor.items.get(i);
                if(field && field.column.key === true)
                  if(!Ext.isEmpty(field.getValue())){
                    selCriteria[field.mapping || field.column.dataIndex] = {operator: 'Equals', values: [field.getValue()]};
                  } else {
                    return false;
                  }
              }
              selCriteria.resultGraphRules = [];
              var container1 = Ext.getCmp('finderContainer');
              container1.store.proxy.query(
              selCriteria
              ,function(queryResponse) {
                if (queryResponse.totalRecords >= 1) {
                  f.markInvalid(container1.resultsPanel.editor.duplicateKeyText);
                  f.duplicateKey = true;
                } else {
                  f.duplicateKey = false;
                  f.clearInvalid();
                }
              });
            }
          }
        });
      } else {
        delete c.editor;
      }
    }
  },
  /**
   * onSaveSuccess
   * handles a successfull save of data.
   * @param {Object} response the response from the server.
   * No response means that the record was Updated, Otherwise the record was added and the response contains the primary key
   * Fires the savesuccess event
   */
  onSaveSuccess: function(response){
    //@TODO use  Controller.PanelController.prototype._setKeyFields(newData, oldData, metaClass)
    if (response && response.length > 0 && response[0].source) {
      var pkVal;
      if (!Ext.isArray(ClassMetaData[this.metaClass].key)) {
        // single key
        var pkField = ClassMetaData[this.metaClass].key;
        pkVal = response[0].source[pkField];
        this.record.data[pkField] = pkVal;
      } else {
        // composite key
        var pkFields = ClassMetaData[this.metaClass].key;
        for (var i = 0; i < pkFields.length; i++) {
          pkVal = response[0].source[pkFields[i]];
          this.record.data[pkFields[i]] = pkVal;
        }
      }
    }
    if(this.fireEvent('savesuccess', response) !== false){
      this.reloadRecord();
    }
  },
  
  reloadRecord: function(rec){
    rec = rec || this.record;
    var store = this.grid.store;
    var proxy = store.proxy;
    var me = this;
    
    if(proxy.query){
      me.savingProgressBar.updateText(me.loadingText);
      var criteria = {objectLimit: 1}; 
      if(store.baseParams.resultGraphRules){
        criteria.resultGraphRules = store.baseParams.resultGraphRules;
      }
      //apply pk to the criteria
      if (!Ext.isArray(ClassMetaData[this.metaClass].key)) {
        // single key
        var pkField = ClassMetaData[this.metaClass].key;
        var pkVal = rec.get(pkField);
        criteria[pkField] = {values: [pkVal]};
      } else {
        // composite key
        var pkFields = ClassMetaData[this.metaClass].key;
        for (var i = 0; i < pkFields.length; i++) {
          var pkVal = rec.get(pkFields[i]);
          criteria[pkFields[i]] = {values: [pkVal]};
        }
      }

      //retreive record after updating it, then replace the old one 
      proxy.query(criteria, function(response){
        if(response && response.graphs){
          var records = store.reader.extractData(response.graphs,true);
          if(records && records[0]){
            var persistedRec = records[0];
            if(store.indexOfId(rec.id) > -1){
              store.data.replace(rec.id, persistedRec);
              persistedRec.id = rec.id;
              rec = persistedRec;
              store.fireEvent('update', this, persistedRec, Ext.data.Record.COMMIT);
            }
            persistedRec.store = store;
            persistedRec.phantom = false;
          }
        }
        me.hide();
        me.setEditorsDisabled(false);
        me.savingProgressBar.hide();
        me.savingProgressBar.updateText(me.waitingText);
        me.setButtonsVisible(true);
        me.grid.store.commitChanges();
        me.grid.view.refresh();
      });
    }
  },

  /**
   * setButtonsVisible
   * used to hide or show the save and cancel buttons
   * @param {Boolean} visible
   */
  setButtonsVisible: function(visible){
    this.savingProgressBar.setVisible(!visible);
    this.btns.items.each(function(btn){
      if (btn instanceof Ext.Button) {
        btn.setVisible(visible);
      }
    });
  },

  /**
   * onSaveFail
   * handles a failed save of data.
   * @param {Object} response the response from the server.
   * The default behavior is to alert out the error message
   * You can override this by returning false in the savefail listener
   */
  onSaveFail: function(err){
    if (this.fireEvent('savefail', err) !== false) {
      Ext.Msg.alert(err.className, err.localizedMessage);
      this.setEditorsDisabled(false);
      this.savingProgressBar.hide();
      this.setButtonsVisible(true);
    }
  },

  /**
   * saveRecord
   * saves a record
   * @param {Object} response the response from the server.
   * The default behavior is to alert out the error message
   * You can mute this by returning false in the savefail listener
   */
  saveRecord: function(roweditor, changes, rec, rowIndex){
    if (this.fireEvent('beforesaverecord', roweditor, changes, rec, rowIndex) !== false) {
      this.setEditorsDisabled(true);
      if (!this.savingProgressBar.rendered) {
        this.savingProgressBar.render(this.btns.body);
      } else {
        this.savingProgressBar.show();
      }
      this.savingProgressBar.wait({
        text: this.waitingText
      });
      this.setButtonsVisible(false);
      var keyField = ClassMetaData[this.metaClass].key;
      if (this.useSequence && rec.phantom) {
        if (Ext.isArray(keyField)) {
          for (var i=0; i<keyField.length; i++) {
            delete rec.data[keyField[i]];
          }
        } else delete rec.data[keyField];
      }
      var saveGraph = this.getSaveGraph(rec);
      this.grid.store.proxy.update([saveGraph], function(serverResponse){
        if (serverResponse && serverResponse.length > 0) {
          if (serverResponse[0].errors) {
            roweditor.onSaveFail(serverResponse[0].errors[0]);
          } else {
            roweditor.onSaveSuccess(serverResponse);
          }
        } else {
          roweditor.onSaveSuccess();
        }
      });
    }
  },

  /**
   * initCrudButtons
   * inserts the add and delete buttons to the grid
   * @param {Ext.GridPanel} grid
   */
  initCrudButtons: function(grid){
    var crudButtons = [{
      id: 'add',
      iconCls: 'add',
      text: this.addButtonText,
      scope: this,
      disabled: !this.allowAdd,
      hidden: (this.allowAdd === undefined),
      handler: function(btn){
        var rec = new this.grid.store.reader.recordType();
        rec.phantom = true;
        this.stopEditing();
        this.grid.store.insert(0, rec);
        this.grid.getView().refresh();
        this.grid.getSelectionModel().selectRow(0);
        this.startEditing(0);
      }
    }, {
      id: 'delete',
      iconCls: 'delete',
      text: this.deleteButtonText,
      scope: this,
      disabled: !this.allowDelete,
      hidden: (this.allowDelete === undefined),
      handler: function(btn){
        var grid = this.grid;
        var selected = grid.getSelectionModel().getSelections();
        if (selected.length > 0) {
          Ext.MessageBox.show({
            title : this.deleteSelectedTitleText,
            msg : this.deletePromptMessageText,
            width : 400,
            buttons : Ext.MessageBox.YESNO,
            icon : Ext.MessageBox.QUESTION,
            scope: this,
            fn : function(btn) {
              if (btn == 'yes') {
                var deleteCriteria = [];
                var keyField = ClassMetaData[this.metaClass].key;
                for (var i = 0; i < selected.length; i++) {
                  var deleteObj = {
                    deleteObject: true
                  };
                  if (Ext.isArray(keyField)) {
                    //composite key
                    for (var j = 0; j < keyField.length; j++) {
                      deleteObj[keyField[j]] = selected[i].get(keyField[j]);
                    }
                  } else {
                    //surrogate key
                    deleteObj[keyField] = selected[i].get(keyField);
                  }
                  deleteObj[keyField] = selected[i].get(keyField);
                  deleteCriteria[deleteCriteria.length] = deleteObj;
                }
                var errorExists = this.saveErrorHandler;
                grid.store.proxy.save(deleteCriteria, function(response){
                  if(errorExists(response, grid)){
                    return false;
                  }
                  grid.store.loadMore(true);
                });
              }
            }
          });
        } else {
          Ext.MessageBox.alert(this.buttonErrorTitle,this.removeButtonSelectAtLeastOneRecordText);
        }
      }
    }];
    var gridToolbar = grid.getTopToolbar();
    if (!gridToolbar) {
      grid.elements += ',tbar';
      grid.topToolbar = grid.createToolbar(crudButtons);
    } else {
      gridToolbar.insertButton(this.crudButtonsPosition, crudButtons);
    }
  },

  //private , added events and gave the grid a reference to this editor and support for allowEdit       
  init: function(grid){
    this.addEvents(    /**
     * @event beforesaverecord
     * Fires before a record has been saved
     * @param {this} roweditor
     * @param {Object} changes an object containing the changes data
     * @param {Object} rec the record being edited
     * @param {Object} rec the rowIndex the index of the record in the store
     */
    'beforesaverecord',    /**
     * @event savesuccess
     * Fires after a record has been saved
     * @param {Object} response a server response object
     */
    'savesuccess',    /**
     * @event savefail
     * Fires after a record has been rejected by the service
     * @param {Object} response a server response object
     */
    'savefail');
    grid.editor = this;
    this.initCrudButtons(grid);
    this.grid = grid;
    this.ownerCt = grid;
    if (this.allowEdit) {
      if (this.clicksToEdit === 2) {
        grid.on('rowdblclick', this.onRowDblClick, this);
      } else {
        grid.on('rowclick', this.onRowClick, this);
        if (Ext.isIE) {
          grid.on('rowdblclick', this.onRowDblClick, this);
        }
      }
    }else{
      grid.on('beforeRender',function(grid){
        if (grid.getColumnModel()){
          var colMod = grid.getColumnModel().config;
          if (colMod && colMod.length > 0){
            for (var i = 0; i< colMod.length; i++){
              colMod[i].editable = false;
            }
          }
        }
      })
    }
    // stopEditing without saving when a record is removed from Store.
    grid.getStore().on('remove', function(){
      this.stopEditing(false);
    }, this);

    // stopEditing without saving when a the store is refreshed or loaded.
    grid.getStore().on('beforeload', function(){
      this.stopEditing(false);
    }, this);

    grid.on({
      scope: this,
      keydown: this.onGridKey,
      columnresize: this.verifyLayout,
      columnmove: this.columnMove,
      reconfigure: this.refreshFields,
      beforedestroy: this.beforedestroy,
      destroy: this.destroy,
      bodyscroll: {
        buffer: 250,
        fn: this.positionButtons
      }
    });

    grid.on('render', function() {
      this.generateEditors();
      grid.getColumnModel().on('hiddenchange', this.verifyLayout, this, {
          delay: 1
      });
      grid.getView().on('refresh', this.stopEditing.createDelegate(this, []));
      },
      this,
      {single: true}
    );
    
  },

  // private   checks for valid and dirty. 
  bindHandler: function(){
    var disable;
    if (!this.bound) {
      return false; // stops binding
    }
    var valid = this.isValid();
    if (!valid && this.errorSummary) {
      this.showTooltip(this.getErrorText().join(''));
    }
    this.btns.saveBtn.setDisabled(!valid || !this.isDirty());
    this.fireEvent('validation', this, valid);
  },

  isValid: function(){
    var valid = true;
    this.items.each(function(f){
      if (!f.isValid(true) || f.duplicateKey) {
        valid = false;
        return false;
      }
    });
    return valid;
  },

  /**
   * if the grid has toolbars, they will be masked or unmasked
   * @param {Object} yesNo
   */
  setToolbarsDisabled: function(yesNo){
    var tbar = this.grid.getTopToolbar();
    var bbar = this.grid.getBottomToolbar();
    if (yesNo) {
      if (tbar) {
        tbar.el.mask();
      }
      if (bbar) {
        bbar.el.mask();
      }
    } else {
      if (tbar) {
        tbar.el.unmask();
      }
      if (bbar) {
        bbar.el.unmask();
      }
    }
  },

  startEditing: function(rowIndex, doFocus){
    if(this.editing && this.isDirty()){
      this.showTooltip(this.commitChangesText);
      return;
    }
    if(Ext.isObject(rowIndex)){
      rowIndex = this.grid.getStore().indexOf(rowIndex);
    }

    if (this.disableToolbarsWhenEditing) {
      this.setToolbarsDisabled(true);
    }

    if(this.fireEvent('beforeedit', this, rowIndex) !== false){
      this.editing = true;
      var g = this.grid, view = g.getView(),
      row = view.getRow(rowIndex),
      record = g.store.getAt(rowIndex);
      g.editing = true;

      this.record = record;
      this.rowIndex = rowIndex;
      this.values = {};
      if(!this.rendered){
        this.render(view.getEditorParent());
      }
      var w = Ext.fly(row).getWidth();
      this.setSize(w);
      if(!this.initialized){
        this.initFields();
      }
      var cm = g.getColumnModel(), fields = this.items.items, f, val;
      for(var i = 0, len = cm.getColumnCount(); i < len; i++){
        var c = cm.getColumnAt(i), ed = c.getEditor();
        if(!ed) {
          ed = c.displayEditor || new Ext.form.DisplayField();
        } else {
          ed.setDisabled((c.key && !this.record.phantom) || c.disabled);
        }
        val = this.preEditValue(record, cm.getDataIndex(i));
        f = fields[i];
        f.setValue(val);
        if(f.xtype === 'combo' && !f.rendered && f.displayField && f.findRecord){
          f.on('afterrender', function(){
            this.setValue(this.value);
          }, f, {single: true});
        }
        this.values[f.id] = Ext.isEmpty(val) ? '' : val;
      }
      this.verifyLayout(true);
      if(!this.isVisible()){
        this.setPagePosition(Ext.fly(row).getXY());
      } else{
        this.el.setXY(Ext.fly(row).getXY(), {duration:0.15});
      }
      if(!this.isVisible()){
        this.show().doLayout();
      }
      if(doFocus !== false){
        this.doFocus.defer(this.focusDelay, this);
      }
    }
  },
  
  initFields: function(){
    var cm = this.grid.getColumnModel(), pm = Ext.layout.ContainerLayout.prototype.parseMargins;
    this.removeAll(false);
    if(this.displayFields){
      Ext.each(this.displayFields, function(){
        this.destroy();
      });
    }
    for(var i = 0, len = cm.getColumnCount(); i < len; i++){
      var c = cm.getColumnAt(i),
      ed = c.getEditor();
      if(!ed){
        if(c.displayEditor){
          ed = c.displayEditor;
        } else {
          ed = new Ext.form.DisplayField({html:'&nbsp;', style:{minHeight:'1px'}});
          if(!this.displayFields)
            this.displayFields = [];
          this.displayFields.push(ed);
        }
      } else if(ed.rendered){
        ed.setDisabled((c.key && !this.record.phantom) || c.disabled);
        this.getLayout().configureItem(ed);
      }
      if(i == 0){
        ed.margins = pm('0 1 2 1');
      } else if(i == len - 1){
        ed.margins = pm('0 0 2 1');
      } else {
        if (Ext.isIE) {
          ed.margins = pm('0 0 2 0');
        } else {
          ed.margins = pm('0 1 2 0');
        }
      }
      ed.setWidth(cm.getColumnWidth(i));
      ed.column = c;
      if(ed.ownerCt !== this && !ed instanceof Ext.ux.form.DateTime) {
        ed.on('focus', this.ensureVisible, this);
        ed.on('specialkey', this.onKey, this);
      }
      this.insert(i, ed);
    }
    this.initialized = true;
  },

  /**
   * setEditorsDisabled
   * disables or enables the fields in the roweditor
   * @param {Boolean} disable true to disable all editors, false to enable
   */
  setEditorsDisabled: function(disable){
    if (this.items) {
      this.items.each(function(f){
        f.setDisabled(disable);
      });
    }
  },
  //private override: changed the target to be the btns not the last column
  showTooltip: function(msg){
    var t = this.tooltip;
    if (!t) {
      t = this.tooltip = new Ext.ToolTip({
        maxWidth: 600,
        cls: 'errorTip',
        width: 200,
        title: this.errorText,
        autoHide: false,
        anchor: 'left',
        anchorToTarget: true,
        mouseOffset: [6, 10]
      });
    }
    var v = this.grid.getView(), top = parseInt(this.el.dom.style.top, 10), scroll = v.scroller.dom.scrollTop, h = this.el.getHeight();

    if (top + h >= scroll) {
      t.initTarget(this.btns.getEl());
      if (!t.rendered) {
        t.show();
        t.hide();
      }
      t.body.update(msg);
      t.doAutoWidth(20);
      t.show();
    } else if (t.rendered) {
      t.hide();
    }
  },

  //private when a phantom record is canceled it is removed from the store
  stopEditing: function(saveChanges){
    this.editing = false;
    this.grid.editing = false;
    if (!this.isVisible()) {
      return;
    }
    if (this.disableToolbarsWhenEditing) {
      this.setToolbarsDisabled(false);
    }
    if (saveChanges === false || !this.isValid()) {
      this.items.each(
        function(f) {
          if (f instanceof Ext.form.TextField && f.duplicateKey) {
            f.duplicateKey = false;
            f.clearInvalid();
          }
        }
      );
      this.hide();
      if (this.fireEvent('canceledit', this, saveChanges === false) !== false) {
        if (this.record.phantom) {
          this.grid.store.remove(this.record);
        }
      }
      return;
    }
    var changes = {}, r = this.record, hasChange = false, cm = this.grid.colModel, fields = this.items.items;
    for (var i = 0, len = cm.getColumnCount(); i < len; i++) {
      if (!cm.isHidden(i)) {
        var dindex = cm.getDataIndex(i);
        if (!Ext.isEmpty(dindex)) {
          var oldValue = r.data[dindex], value = this.postEditValue(fields[i].getValue(), oldValue, r, dindex);
          if (String(oldValue) !== String(value)) {
            changes[dindex] = value;
            hasChange = true;
          }
        }
      }
    }
    if (hasChange && this.fireEvent('validateedit', this, changes, r, this.rowIndex) !== false) {
      r.beginEdit();
      Ext.iterate(changes, function(name, value){
        r.set(name, value);
      });
      r.endEdit();
      if (this.fireEvent('afteredit', this, changes, r, this.rowIndex) !== false) {
        if (r.dirty) {
          this.saveRecord(this, changes, r, this.rowIndex);
        } else if (r.phantom) {
          this.grid.store.remove(r);
        }
      }
    }
  },
  saveErrorHandler : function(response, grid){
    var msg = '';
    if (response) {
      for (var i = 0; i < response.length; i++) {
        var responseEl = response[i];
        if (responseEl.errors && (responseEl.errors.applicationExceptionArray || responseEl.errors.length > 0)) {
          // ApplicationExceptions
          var appExps = responseEl.errors.applicationExceptionArray ? responseEl.errors.applicationExceptionArray : responseEl.errors;
          for (var j = 0; j < appExps.length; j++) {
            var appExp = appExps[j];
            msg += (appExp.localizedMessage || this.defaultLocalizedErrorMessage) + '\n<br>';
          }
        } else if (responseEl.runtimeError) {
          // FrameworkException
          msg += responseEl.runtimeError.localizedMessage + '\n<br>';
        }
      }
    }
    if (msg) {
      Ext.MessageBox.show( {
        title : this.saveErrorsTitleText,//FIXME
        msg : msg,
        // width : 400,
        buttons : Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });
      //If errors are returned for some records then deselect the others.
      var hasError = function(rec){
        var error = false;
        var keyField;
        for(var i =0 ; i< response.length; i++){
          if (!keyField) keyField = ClassMetaData[response[0].source.className].key;
          var source = response[i].source;
          if(source){
            if (Ext.isArray(keyField)) {
              var matchCount = 0;
              for (var j=0; j<keyField.length; j++) {
                if(rec.get(keyField[j]) === source[keyField[j]]){
                  matchCount++;
                }
              }
              if(matchCount === keyField.length){
                error = true;
                break;
              }
            } else if(rec.get(keyField) === source[keyField]){
              error = true;
              break;
            }
          }
        }
        return error;
      }
      var sm = grid.getSelectionModel();
      var selections = sm.getSelections(),ds = grid.getStore();
      for(var i =0 ; i < selections.length; i++){
        if(!hasError.call(this,selections[i])){
          sm.deselectRow(ds.indexOfId(selections[i].id));
        }
      }
	  return true;
    }
	return false;
  },
  onKey: function(f, e){
    if(e.getKey() === e.ENTER){
      var targetField = Ext.getCmp(e.target.id);
      if(targetField && targetField instanceof Ext.form.ComboBox && targetField.preventRowSave){ //prevents the editor from saving the record when the user presses enter to select a list item in a combo 
        e.stopPropagation();
        delete targetField.preventRowSave;
        targetField.focus();
        return;
      }
      this.stopEditing(true);
      e.stopPropagation();
    }
  }
});

Ext.preg('maintenanceinlineroweditor', Jaffa.maintenance.plugins.InlineRowEditor);
