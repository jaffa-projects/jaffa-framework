/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/** 
 * @class Jaffa.maintenance.GridDetailContainer
 * @extends Ext.Panel 
 * @author BobbyC
 *
 * Container that handles interaction between a grid panel and a form panel editor for
 * the records in the panel.
 * 
 */
Jaffa.maintenance.GridDetailContainer = Ext.extend(Ext.Panel, {
  /** Text objects that can be replaced for internationalization */
  modifyButtonText: 'Modify',
  buttonErrorTitle: 'Error',
  modifyButtonSelectOneRecordText: 'You must select one record before performing this action.',
  viewButtonText: 'View',
  addButtonText:'Add',
  removeButtonText:'Remove',
  removeButtonSelectAtLeastOneRecordText:'You must select at least one record before performing this action.',
  okButtonText:'OK',
  cancelButtonText: 'Cancel',
  detailButtonText: 'Form View',
  listButtonText: 'Grid View',
  fixText: 'Fix Record',
  discardText:'Do you want to discard changes?',
  confirmText: 'Confirm Action',
  warningText:'Warning',
  addRecordText: 'Add',
  modifyRecordText: 'Modify',
 
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

  /** @cfg gridPanel A grid or grid config that will be used as the grid part of the container
   */   
  gridPanel: undefined,

  /** @cfg detailPanel A form or form config that will be used as the editor part of the container
   */   
  detailPanel: undefined,

  /** @cfg layout Layout for the container panels. Currently supports 'border', 'slide' and 'card'. Default to 'border'.
   */   
  layout: 'border',

  /** @cfg buttonIndex Index at which default buttons should be inserted into the grid toolbar.
   */   
  buttonIndex: 0,

  /** @cfg (boolean) removeButton True to include default remove button on grid toolbar
   */   
  removeButton: true,

  /** @cfg (boolean) addButton True to include default add button on grid toolbar
   */   
  addButton: true,
  
  /** @cfg (boolean) floatingDataSource True to indicate that the data source of the grid is dynamic. This 
   *                 happens when a GridDetailContainer is inside of a Detail panel of another GridDetailContainer.
   */
  floatingDataSource: false,

  /** @cfg  copyFieldList An array of fields that will be copied from the current/highlighted record
   *                      when creating a new record in the store.
   */
  copyFieldList: [],
  copyErrorTitle: 'Copy Error',
  copySelectOneRecordText:'Select a single record as the basis for the new record.',

  /** @cfg (json) modifyBtnCfg Configuration info on modify button.
   *  
   */
  
  gridModifyButton: true,
  detailOkButton: true,
  detailAddButton: true,
  detailCancelButton: true,
  navigationButtons: true,
  //Container uses panel plugin for change listeners, also stops cascade load/save into the detail panel
  plugins: new Jaffa.maintenance.plugins.Panel({beforerenderHandler: Ext.emptyFn, bodyCfg: ''}),
  initComponent: function(){
    this.addEvents(
     /**
     * @event beforeloadrecord
     * Fires before a record is loaded into the 
     * @param {this} ct the griddetailcontainer
     * @param {Object} rec the record being edited
     */
      'beforeloadrecord',
     /**
     * @event loadrecord
     * Fires after a record is loaded into the 
     * @param {this} ct the griddetailcontainer
     * @param {Object} rec the record being edited
     */
      'loadrecord',
     /**
     * @event updaterecord
     * Fires after a record in the grid is updated by the detail panel 
     * @param {this} ct the griddetailcontainer
     * @param {Object} oldrec the record being changed
     * @param {Object} newrec the updated record
     */
      'updaterecord',
     /**
     * @event removerecord
     * Fires after a record in the grid is removed 
     * @param {this} ct the griddetailcontainer
     * @param {Object} records the deleted records
     */
      'removerecord'
    );
    this.gridPanel.boxMinHeight= this.gridPanel.boxMinHeight || 200;
    //If gridPanel config supplied, initialize toolbar
    if (!(this.gridPanel instanceof Ext.grid.GridPanel)){
      this.gridPanel.bodyCfg='';
      if (!this.gridPanel.tbar && (this.removeButton||this.addButton)) this.gridPanel.tbar=[];
      this.gridPanel = Ext.ComponentMgr.create(this.gridPanel, Ext.Panel);
    }
    
    //Inject updateRecord method into grid save process
    if (typeof this.gridPanel.saveData == 'function'){
      var originalSave = this.gridPanel.saveData.createDelegate(this.gridPanel);
      this.gridPanel.saveData = function(origData, saveData){
        this.detailPanel.updateRecord();
        return originalSave(origData, saveData);
      };
    } else if (!this.floatingDataSource) {
      this.gridPanel.saveData = function(origData, saveData) {
        this.detailPanel.updateRecord();
        return Jaffa.maintenance.plugins.GridLoadSave.prototype.saveData.call(this, origData, saveData);
      };
    }
    
    //Inject validateData method into grid save process
    if (typeof this.gridPanel.validateData == 'function'){
      var originalValidate = this.gridPanel.validateData.createDelegate(this.gridPanel);
      this.gridPanel.validateData = function(){
        var msg = this.detailPanel.validateData();
        if (msg) return msg;
        return originalValidate();
      };
    } else {
      this.gridPanel.validateData = function() {
        var msg = this.detailPanel.validateData();
        if (msg) return msg;
        return Jaffa.maintenance.plugins.GridLoadSave.prototype.validateData.call(this); 
      };
      }
    
    Ext.applyIf(this.gridPanel, {
      region: 'center'
    });

    //baseTitle is used to create title on detail panel, adding modifiers when necessary ('Add', 'Modify', '*')
    this.detailPanel.baseTitle = this.detailPanel.title;
    if (this.detailPanel.titleToken) {
      this.detailPanel.baseTitle = Labels.get(this.detailPanel.titleToken);
    }
    this.detailPanel.boxMinHeight= this.detailPanel.boxMinHeight || 200;
    //Detail panel gets basic panel plugin for change listeners.
    this.detailPanel.plugins = this.detailPanel.plugins || [];
    if (!Ext.isArray(this.detailPanel.plugins)) this.detailPanel.plugins= [this.detailPanel.plugins];
    this.detailPanel.plugins.unshift(new Jaffa.maintenance.plugins.Panel({
      beforerenderHandler: Ext.emptyFn,
      setDirty: function(dirty) {
        if (this.isDirty != dirty) {
          this.isDirty = dirty;
          if(this.setTitleText){
            this.setTitleText();
          }
        }
      },
      validateData: function() {
        if (this.isDirty) {
          return Jaffa.maintenance.plugins.PanelLoadSave.prototype.validateData.call(this);
        }
        return '';
      },
      readOnly:false}
    ));

    //Set some properties on detail panel required for border layout
    if (this.layout == 'border'){
      Ext.applyIf(this.detailPanel, {
        split: true,
        region: 'south',
        collapsible: true
      });
      Ext.applyIf(this.gridPanel, {region: 'center'});
    }

    //If detailPanel config supplied, initialise toolbar and create component
    if (!(this.detailPanel instanceof Ext.Panel)){
      this.detailPanel.bodyCfg='';
      if (!this.detailPanel.tbar) this.detailPanel.tbar=[];
      this.detailPanel = Ext.ComponentMgr.create(this.detailPanel, Ext.Panel);
    }
    this.detailPanel.floatingDataSource = true; // indicates that this panel is updating to a record rather than on the data model.
    this.gridPanel.detailPanel = this.detailPanel;
    this.detailPanel.gridPanel = this.gridPanel;
    this.detailPanel.transientFields = true;
    
    if (!this.allowEdit) 
      this.detailPanel.cascade(function(f){
        if (f.mapping) {
          if (f.xtype == 'combo' || f.xtype == 'finderComboGrid') {
            f.disable();
          }
          else 
            if (f.xtype == 'checkbox') {
              if (f.rendered) {
                f.disable();
              }
              else {
                f.disabled = true;
              }
            }
            else {
              if (f.rendered) {
                f.setTextOnly(true);
              }
              else {
                f.textOnly = true;
              }
            }
        }
      });

    Ext.applyIf(this.detailPanel, {
      controller: this.gridPanel.controller,
      clearForm: function() {
        this.controller.clearPanelFields(this);
        delete this.record;
        this.disable();
        this.isDirty = false;
        this.setTitleText();
      },
      //Calls validate data method on detail panel
      validate: function(cb) {
        var msg = this.validateData(); // TODO can we escape the return of this for XSS or does this include html styling?
        if (msg) {
          Ext.MessageBox.show({
            buttons: {
              ok: this.ownerCt.fixText,
              cancel: this.ownerCt.discardText
            },
            title: this.ownerCt.warningText + ':', 
            msg: msg,
            fn: function(btn, text) {
              if (btn=='cancel') {
                if (cb) 
                  cb();
                else {
                  this.ownerCt.resetDetailPanel();
                  if (this.ownerCt.layout.type=='card'||this.ownerCt.layout.type=='slide') this.ownerCt.layout.setActiveItem(0);
                }
              }
            }, 
            scope: this,
            closable: false
          });
          return false;
        }
        return true;
      },
      //Updates grid record with changes made on detail panel
      updateRecord: function() {
        if (this.isDirty) {
          var oldRecordData={};
          for(var property in this.record.data) 
            oldRecordData[property] = this.record.data[property];
          var oldRecord = new (this.gridPanel.getStore().recordType)(oldRecordData);;
          
          this.controller.updateRecordFromPanel(this.record, this);
          if (this.record.get('isNew') && !this.gridPanel.getStore().getById(this.record.id))
            this.gridPanel.getStore().add(this.record)
          this.setDirty(false);
          this.ownerCt.fireEvent('updaterecord', this.ownerCt, oldRecord, this.record);
        } if (this.record && this.record.isNew && !this.record.modified) {
          this.record.store.remove(this.record);
        }
      },
      //Loads a record into the detail panel
      loadRecord: function(record) {
        this.fireEvent('beforeloadrecord', this, record);
        this.record = record;
        this.controller.clearPanelFields(this);

        if (this.loadDetailPanel)
          this.loadDetailPanel(this.record);
        else
          this.controller.setPanelFields(this, this.record.data);

        this.enable();
        
        if (this.ownerCt.layout.type == 'card' && this.ownerCt.navigationButtons) {
          var currentIndex = this.gridPanel.getStore().indexOf(this.record);
          var currentCount = this.gridPanel.getStore().getCount();
          this.getTopToolbar().getComponent('stepBack').enable();
          this.getTopToolbar().getComponent('stepForward').enable();
          if (currentIndex >= currentCount - 1) {
            this.getTopToolbar().getComponent('stepForward').disable();
          }
          if (currentIndex == 0 || currentCount==0) {
            this.getTopToolbar().getComponent('stepBack').disable();
          }
          this.setPageNumber();
        }
        this.originalRecordJSON = Ext.ux.clone(record.json);
        this.fireEvent('loadrecord', this, record);
        
      },
      setTitleText: function(text) {
        if (typeof text != 'string') {
          text = this.currentTitle||this.title;
          if (this.isDirty) {
            text = "*" + text;
          }
        }
        this.setTitle(text||this.currentTitle);
      },
      setPageNumber: function(){
        var index = this.gridPanel.getStore().indexOf(this.record);
        if (index >= 0){
          index = index + 1;
        } else {
          index = Labels.get('title.jaffaRIA.common.NEW');
        }
       this.getTopToolbar().getComponent('recordPosition').setText(index + ' '+Labels.get('label.Common.of')+' ' + this.gridPanel.getStore().getCount());
      }
    });
    this.detailPanel.disable();

    //Inject 'Add' and 'Remove' buttons into grid panel toolbar if required
    if (this.removeButton || this.addButton)
      if (this.gridPanel.getTopToolbar()) {
        if (this.removeButton)
          this.gridPanel.getTopToolbar().insert(this.buttonIndex, {
            text: this.removeButtonText,
            itemId: 'removeBtn',
            iconCls: 'remove-row',
            disabled: !this.allowDelete,
            scope: this,
            handler: function(){
              this.onGridRemove();
            }
          });
        if (this.addButton)
          this.gridPanel.getTopToolbar().insert(this.buttonIndex, {
            text: this.addButtonText,
            itemId: 'addBtn',
            iconCls: 'add-row',
            scope: this,
            disabled: !this.allowAdd,
            handler: function(){
              this.onGridAdd();
            }
          });
      }
      else 
        alert('err');
        //console.error();

    //Inject 'modify' button into grid panel toolbar if using card style layout
    if (['card','slide'].indexOf(this.layout)>=0 && this.gridPanel.getTopToolbar() && this.gridModifyButton) {
      this.gridPanel.getTopToolbar().insert(this.buttonIndex, Ext.apply({
        text: this.allowEdit?this.modifyButtonText:this.viewButtonText,
        itemId: 'modifyBtn',
        iconCls: 'table_edit',
        scope: this,
        handler: function(){
          if (this.gridPanel.getSelectionModel().getSelections().length == 1) {
            this.onModify.call(this,this.gridPanel.getSelectionModel().getSelected())
          } else {
            Ext.MessageBox.alert(this.buttonErrorTitle,this.modifyButtonSelectOneRecordText);
          }
        }
      }, this.modifyBtnCfg));
    }

    //Inject 'Ok' and 'Cancel' buttons at start of detail panel toolbar
    var detailTb = this.detailPanel.getTopToolbar();
    if (detailTb) {
      var i = 0;
      if (this.detailOkButton) {
        detailTb.insert(i, {
          text: this.okButtonText,
          itemId: 'okBtn',
          iconCls: 'ok',
          disabled: !this.allowEdit,
          scope: this,
          handler: this.onDetailOk
        });
        i++;
      }
      
      if (this.detailAddButton) {
        detailTb.insert(i, {
          text: this.addButtonText,
          itemId: 'addBtn',
          iconCls: 'add-row',
          disabled: !this.allowAdd,
          scope: this,
          handler: this.onGridAdd
        });
        i++;
      }
      //adds the cancel button always at end
      if (this.detailCancelButton) {
        detailTb.addButton({
          text: this.cancelButtonText,
          itemId: 'cancelBtn',
          iconCls: 'cancel',
          scope: this,
          handler: function() {
            this.onDetailCancel();
          }
        });
      }
      
    }

    //If using card style layout, add 'Detail' and 'List' navigation buttons to grid and detail panel
    if ((this.layout == 'card'||this.layout == 'slide') && this.navigationButtons) {
      this.gridPanel.getTopToolbar().add('->');
      this.gridPanel.getTopToolbar().add({
        text: this.detailButtonText,
        iconCls: 'application_form',
        itemId: '_details',
        scope: this,
        disabled: true,
        handler: function(){
          this.onFormView();
        }
      });
      this.detailPanel.getTopToolbar().add('->');
      this.detailPanel.getTopToolbar().add({
        iconCls: 'step-back',
        itemId: 'stepBack',
        scope:this,
        handler: function(){
          this.onStepBack();
        }
      });
      this.detailPanel.getTopToolbar().add({
        xtype: 'label',
        itemId: 'recordPosition'
      });
      this.detailPanel.getTopToolbar().add({
        iconCls: 'step-forward',
        itemId: 'stepForward',
        scope:this,
        handler: function(){
          this.onStepForward();
        }
      });
      this.detailPanel.getTopToolbar().add({
        text: this.listButtonText,
        itemId: 'gridViewBtn',
        iconCls: 'table_go',
        scope: this,
        handler: function(){
          this.layout.setActiveItem(0);
        }
      });
    }

    if (this.layout=='card'||this.layout == 'slide'){
      this.activeItem = 0;
    }

    if (!this.items) this.items=[];
    this.items.push(this.gridPanel);
    this.items.push(this.detailPanel);
    
    //Set up row click handlers for the grid
    this.gridPanel.on({
      rowclick: this.layout=='card'||this.layout == 'slide'?Ext.emptyFn:this.onRowClick,
      rowdblclick: this.layout=='card'||this.layout == 'slide' ? this.onCardRowDblClick : this.onBorderRowDblClick,
      scope: this
    });
    
    if (!this.floatingDataSource) this.gridPanel.controller.registerPanel(this.gridPanel, this.gridPanel.findDataSource(),this.gridPanel.controller);
    this.gridPanel.controller.applyPanelFieldsMetaRules(this.gridPanel.detailPanel)
    Jaffa.maintenance.plugins.Panel.prototype._setChangeListeners.call(this.detailPanel, this.detailPanel.setDirty.createDelegate(this, [true]));
    Jaffa.maintenance.GridDetailContainer.superclass.initComponent.call(this);
    this.controller.on('load', this.resetDetailPanel, this);
    var destroyFn = function(){
      this.controller.un('load', this.resetDetailPanel, this);
    };
    this.on('destroy', destroyFn, this, {single:true});
  },
 
  onCardRowDblClick: function(grid, rowIndex, e){
    if (grid.getStore().getAt(rowIndex).selectable) {
      this.onRowClick(grid, rowIndex, e);
      if (this.gridPanel.getTopToolbar().getComponent('_details')) this.gridPanel.getTopToolbar().getComponent('_details').enable();
    }
  },
  
  onBorderRowDblClick: function(grid, rowIndex, e){
   this.detailPanel.expand();
  },
  
  //Method to handle selection of rows on the grid
  onRowClick: function(grid, rowIndex, e){
    if (grid.getSelectionModel().getSelections().length == 0 && !e.stepping){
      if (!this.detailPanel.isDirty){
        this.detailPanel.clearForm();
        this.detailPanel.setTitle(this.detailPanel.baseTitle);
      }else{
        if (this.detailPanel.validate()) {
          this.detailPanel.updateRecord();
          this.detailPanel.clearForm();
          this.detailPanel.setTitle(this.detailPanel.baseTitle);
        }
      }
    }else if (grid.getSelectionModel().getSelections().length > 1){
      if (!this.detailPanel.isDirty){
        this.detailPanel.clearForm();
        this.detailPanel.setTitle(this.detailPanel.baseTitle);
      }else{
        if (this.detailPanel.validate()) {
          this.detailPanel.updateRecord();
          var record = this.detailPanel.record;
          this.detailPanel.clearForm();
          this.detailPanel.setTitle(this.detailPanel.baseTitle);
        }
      }
    }else{
      if (!this.detailPanel.isDirty){
        if (rowIndex >= grid.getStore().getCount()) rowIndex = grid.getStore().getCount() - 1;
        this.setRowDataToDetailPanel(grid,rowIndex);
      }else{
        //TODO test validate before update
        if (this.detailPanel.validate(
          function(){
              this.detailPanel.clearForm();
              if (this.layout.type=='card'||this.layout.type == 'slide')
                this.onRowClick(grid, rowIndex, e);
              else
                this.ownerCt.onRowClick(grid, rowIndex, e);
          }.createDelegate(this))
        ){
          this.detailPanel.updateRecord();
          this.setRowDataToDetailPanel(grid,rowIndex);
        }
      }
    }
    if (this.layout.type=='card'||this.layout.type=='slide') this.layout.setActiveItem(1);
  },
  setRowDataToDetailPanel : function(grid,rowIndex){
    var row = grid.getStore().getAt(rowIndex);
    if (row) {
      this.detailPanel.loadRecord(row);
      if (row.get('isNew')) {
        this.detailPanel.currentTitle = '('+(this.addRecordText?this.addRecordText:this.ownerCt.addRecordText)+') ' + this.detailPanel.baseTitle;
        this.detailPanel.setTitle(this.detailPanel.currentTitle);
      } else {
        this.detailPanel.currentTitle = '('+(this.modifyRecordText?this.modifyRecordText:this.ownerCt.modifyRecordText)+') ' + this.detailPanel.baseTitle;
        this.detailPanel.setTitle(this.detailPanel.currentTitle);
      }
    }
  },
  //Add button handler
  onGridAdd: function(btn) {
    if (this.detailPanel.record && this.detailPanel.record.isNew && !this.detailPanel.record.modified) {
      // avoid double clicks on the add button
      return;
    }
    if (this.detailPanel.validate(function(){
      this.createNewRecord();
    }.createDelegate(this))){
      this.detailPanel.updateRecord();
      if (!this.createNewRecord()) return false;
    };
    if (this.layout.type == 'card'||this.layout.type == 'slide') {
      this.layout.setActiveItem(1);
      this.gridPanel.getTopToolbar().getComponent('_details').enable();
    } else {
      this.detailPanel.expand();
    }
  },
  //Creates a new record and loads it into the detail panel
  createNewRecord: function(){
    var rec;
    if (this.copyFieldList && this.copyFieldList.length && this.copyFieldList.length > 0){
      var oldRec;
      if (this.layout.container.items.indexOf(this.layout.activeItem)>0){
        //Adding from detail panel
        oldRec = this.detailPanel.record;
      }else{
        //Adding from grid panel
        if (this.gridPanel.getSelectionModel().getSelections().length==0){
          oldRec = null;
        }else if (this.gridPanel.getSelectionModel().getSelections().length==1){
          oldRec = this.gridPanel.getSelectionModel().getSelected();
        }else{
          //error
          Ext.MessageBox.alert(this.copyErrorTitle,this.copySelectOneRecordText);
          return false;
        }

      }
      rec = new (this.gridPanel.getStore().recordType)({isNew:true});
      if (oldRec){
        for (var i=0; i<this.copyFieldList.length; i++){
          rec.set(this.copyFieldList[i], oldRec.get(this.copyFieldList[i]));
        }
      }
    }else{
      rec = new (this.gridPanel.getStore().recordType)({isNew:true});
    }
    this.detailPanel.loadRecord(rec);
    this.gridPanel.getSelectionModel().clearSelections();
    this.detailPanel.currentTitle = '('+this.addButtonText+') ' + this.detailPanel.baseTitle;
    this.detailPanel.setTitle(this.detailPanel.currentTitle);
    return true;
  },

  //Remove button handler
  onGridRemove: function() {
    if (!this.allowDelete) return false;
    var recs = this.gridPanel.getSelectionModel().getSelections();
    if (Ext.isArray(recs) && recs.length>0) {
      for (var i=0; i<recs.length; i++) {
        this.gridPanel.removeRecord(recs[i]);
      }
      this.resetDetailPanel();
    } else {
      Ext.MessageBox.show( {
        title : this.buttonErrorTitle,
        msg : this.removeButtonSelectAtLeastOneRecordText,
        buttons : Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });
    }
    this.fireEvent('removerecord', this, recs);
  },
  //Ok button handler
  onDetailOk: function(){
    if (this.detailPanel.validate()) {
      this.detailPanel.updateRecord();
      if (this.layout.type == 'card' || this.layout.type == 'slide') {
        this.layout.setActiveItem(0);
        this.gridPanel.getTopToolbar().getComponent('_details').disable();
      }
      var record = this.detailPanel.record;
      //Make sure to mark the row as modified if a substore has been modified
      if(record.isSubStoreModified && record.isSubStoreModified()){ 
       record.modified = {subStoreModified: true};
       record.dirty = true;
      }
      this.gridPanel.getView().refresh(); //refresh to display dirty indicators
      return true;
    }
  },
  //Cancel button handler
  onDetailCancel: function(){
    if (this.detailPanel.isDirty) {
      Ext.MessageBox.confirm(''+this.confirmText+':', 
      this.discardText, function(mBtn) {
        if (mBtn=='yes') {
          var record = this.detailPanel.record;
          if (record.get('isNew') && !this.gridPanel.getStore().getById(record.id) ) {
            this.detailPanel.clearForm();
            if (this.layout.type == 'card'||this.layout.type == 'slide') {
              this.layout.setActiveItem(0);
              this.gridPanel.getTopToolbar().getComponent('_details').disable();
            }
          } else if (this.layout.type == 'card'||this.layout.type == 'slide') {
            this.resetDetailPanel();
          } else {
            this.detailPanel.clearForm();
            if(this.detailPanel.originalRecordJSON){
              var rec = new record.constructor(this.detailPanel.originalRecordJSON);
              this.detailPanel.loadRecord(rec);
            } else {
              this.detailPanel.loadRecord(record);
            }
          }
        }
      }, this);
    } else {
      if (this.detailPanel.record.get('isNew') && !this.gridPanel.getStore().getById(this.detailPanel.record.id) ) {
        this.detailPanel.clearForm();
      }
      if (this.layout.type == 'card'||this.layout.type == 'slide') {
        this.resetDetailPanel();
      }
    }
  },
  onStepForward: function(){
    var currentIndex = this.gridPanel.getStore().indexOf(this.detailPanel.record);
    var newIndex = this.gridPanel.getStore().getCount() - 1;
    if (currentIndex < (this.gridPanel.getStore().getCount() - 1) && currentIndex >=0)
      newIndex = currentIndex + 1;
    if (currentIndex < 0)
      newIndex = this.gridPanel.getStore().getCount();

    this.onRowClick(this.gridPanel, newIndex, {stepping:true});
  },
  onStepBack: function(){
    var currentIndex = this.gridPanel.getStore().indexOf(this.detailPanel.record);
    var newIndex = 0;
    if (currentIndex > 0)
      newIndex = currentIndex - 1;

    this.onRowClick(this.gridPanel, newIndex, {stepping:true});
  },
  //Reset detail panel
  resetDetailPanel: function(){
    this.detailPanel.clearForm();
    if (this.layout.type == 'card'||this.layout.type == 'slide') {
      this.layout.setActiveItem(0);
      if (this.gridPanel.getTopToolbar().getComponent('_details')) this.gridPanel.getTopToolbar().getComponent('_details').disable();
    }
  },
  onFormView : function(){
    this.layout.setActiveItem(1);
  },
  onModify : function(rec){
    if(this.onRowClick(this.gridPanel, this.gridPanel.getStore().indexOf(rec), null)  !== false){
      if(this.layout && this.layout.setActiveItem){
        this.layout.setActiveItem(1);
      }
      if (this.gridPanel.getTopToolbar().getComponent('_details'))
        this.gridPanel.getTopToolbar().getComponent('_details').enable();
    }
  }
});

Ext.reg('griddetailcontainer', Jaffa.maintenance.GridDetailContainer)

