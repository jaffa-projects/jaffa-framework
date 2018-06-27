/**
 * @author PrudhviK
 * @class Jaffa.maintenance.plugins.MultiRowUpdate
 * @extends Ext.Panel
 *
 * Grid plugin to update multiple rows at the same time.(Batch update)
 *
 * Example:
 * {
 *    ptype : 'multirowupdate',
 *    updatePanel : new UpdatePanel({}),
 *    windowConfig : {width : 950},
 *    updateButtonConfig :{
 *      tbar :  Panel.getTopToolbar(),
 *      index : 1
 *    },
 *    pendingEventConfig: {
 *      titleTpl: 'Warnings / Pending Events',
 *      events: [{
 *          warn: true
 *        },{
 *          event: 'event1',
 *          handler: PendingEventHandler
 *        }]
 *    }
 *  }
 * @ptype multirowupdate
 */
Jaffa.maintenance.plugins.MultiRowUpdate = Ext.extend(Ext.grid.GridPanel, {
  /**
   * @cfg {String} cancelText Text for cancel button
   */
  cancelText:'Cancel',
  /**
   * @cfg {String} cancelText Text for cancel button
   */
  updateText:'Update',
  /**
   * @cfg {String} errorText Title for error message
   */
  errorText : 'Error',
  /**
   * @cfg {String} noSelectionErrorText Error message when no record is selected
   */
  noSelectionErrorText : 'You must select at least one record before performing this action.',
  /**
   * @cfg {String} validationErrorText Error message when validation errors occur.
   */
  validationErrorText : 'Cannot update until all the validation errors are corrected.',
  /**
   * @cfg {Object} defaultWindowConfig Default configuration of window
   */
  defaultWindowConfig:{
    closeAction:'hide',
    width : 1000,
    modal:true,
    height : 600,
    border : false
  },
  
	saveErrorsTitleText: 'Save Errors',
	
	notSelectedForUpdatedTitleText: 'None of the fields are selected for update. Select at least one.',
	
	flexFieldsTitleText: 'Flex Fields',
	
	okButtonText: 'OK',
	
	errorsTitleText: 'Errors',
  
  /**
   * @cfg {Object} defaultUpdateButtonConfig Default configuration of update button
   */
  defaultUpdateButtonConfig:{
    xtype: 'button',
    iconCls : 'save-all',
    text: "Update Multiple Records",
    itemId: 'multiupdatebutton',
    ref : 'multiupdatebutton',
    disabled : false,
    hidden : false
  },
  /**
   * @cfg {boolean} Flag to display selections as a separate grid in the MultiUpdate popup.
   */
  displayGridForSelections : true,
  /**
   * @cfg {Object} Optional configuration for the grid that display selections in the MultiUpdate popup.
   */
  selectionsGridConfig : {},
  /**
   * @cfg {Object} Optional updateButtonConfig - Configuration used to add update button to the toolbar.
   * If not passed update button is added to the grid bottom bar.
   */
  updateButtonConfig : undefined,
  /**
   * @cfg {Object} Optional pendingEventConfig - Configuration for the pending event.
   * This is used to create pending event window
   */
  pendingEventConfig : undefined,
  /**
   * @cfg {Ext.Panel} Required pendingEventConfig - Panel that is used to update multiple records.
   */
  updatePanel : undefined,
  /**
   * @cfg {Boolean} Flag to enable flex fields panel.
   */
  showFlexFields : false,
  /**
   * @cfg {Object} Optional flexFieldsConfig - Configuration for the flex fields panel.
   */
  flexFieldsConfig : undefined,
  /**
   * @cfg {Object} Optional updateProxy -Custom proxy for the Update controller
   */
  updateProxy : undefined,
  /**
   * @cfg {Object} Optional keyField -Custom Key field if it different from the one specified in ClassMetaData
   */
  keyField : undefined,
  /**
   * @cfg {Object} Optional keyFieldMapping - This should be provided if the keyField in the record is different from the
   * the one in saveData graph.
   */
  keyFieldMapping : undefined,

  initComponent: function(){
    Jaffa.maintenance.plugins.MultiRowUpdate.superclass.initComponent.call(this);
    this.addEvents(
      /**
       * @event beforeupdate
       */
      'beforeupdate',
      /**
       * @event cancel
       */
      'cancel',
      /**
       * @event beforevalidate

       */
      'beforevalidate',
      /**
       * @event beforecancel
       */
      'beforecancel',
      /**
       * @event beforewindowshow
       */
      'beforewindowshow'
    );
  },
  /**
   * Main init function
   * @private
   */
  init:function(grid) {
    // save reference to grid
    this.grid = grid;
    if(this.showFlexFields === true){
      this.addFlexPanel();
    }
    this.applyPanelMetaRules();
    this.addToolbarButton();
  }
  /**
   * Override this to add custom logic after update
   * @param {response} Response text from the server after save.
   */
  ,afterUpdate:Ext.emptyFn
  /**
   * Retrieve update panel reference.
   */
  ,getUpdatePanel :function() {
    Ext.apply(this.updatePanel,{
      region : 'center'
    })
    return this.updatePanel
  } // eo function createFormConfig

  /**
   * OK button click handler
   */
  ,onOK:function() {
    if (this.fireEvent('beforevalidate', this) !== false) {
      if(!this.validateData()) {
        Ext.MessageBox.alert(this.errorText,this.validationErrorText)
        return false;
      }
      this.updateSelectedRecords();
    }
  }
  /**
   * Cancel button handler, removes new record if it is not dirty
   */
  ,onCancel:function() {
    if (this.fireEvent('beforecancel', this) !== false) {
      this.closeWindow();
      this.fireEvent('cancel', this)
    }
  }
  /**
   * Close window
   */
  ,closeWindow : function(){
    if(this.window){
      this.window.hide(null);
      this.clearFields();
    }
  }
  /**
   * Retrieve window using defaultWindowConfig and windowConfig.
   */
  ,getWindow:function() {
    if(this.window) {
      return this.window;
    }
    var config = Ext.apply({}, this.defaultWindowConfig);;
    config = Ext.apply(config, this.windowConfig);

    var items = [];
    if(this.displayGridForSelections){
      this.selectionsGrid = this.getGridForSelections();
      items.push(this.selectionsGrid);
    }
    items.push(this.getUpdatePanel());
    Ext.applyIf(config, {
      layout:'border',
      defaults: {
        split: true,
        collapsible: false
      },
      autoScroll : true,
      items : items,
      title:this.title,
      iconCls:this.iconCls || this.grid.iconCls,
      buttons:  [{
        text:this.updateText
        ,scope:this
        ,handler:this.onOK
      },{
        text:this.cancelText
        ,scope:this
        ,handler:this.onCancel
      }]
      // ok on enter
      ,keys:[{
        key:[10,13] // enter
        ,scope:this
        ,stopEvent:true
        ,fn:this.onOK
      },{
        key:[27] // escape
        ,scope:this
        ,stopEvent:true
        ,fn:this.onCancel
      }],
      listeners : {
        scope : this,
        activate : function(p){
          this.reconfigureSelGridStore();
        }
      }
    });
    return new Ext.Window(config);

  },
  /**
   * Adds the 'Update Multiple Records' button to the toolbar.
   */
  addToolbarButton : function(){
    var buttonConfig = Ext.apply(this.defaultUpdateButtonConfig,this.updateButtonConfig);
    var button = Ext.apply(buttonConfig,{
      scope:this,
      handler: function(item) {
        if(this.disabled){
          return false;
        }
        this.showWindow();
      }
    });
    if(buttonConfig.tbar){
      if(buttonConfig.tbar instanceof Ext.Toolbar){
        if(Ext.isDefined(buttonConfig.index)){
          buttonConfig.tbar.insertButton(buttonConfig.index,button);
        } else {
          buttonConfig.tbar.addButton(button);
        }
      } else if(Ext.isArray(buttonConfig.tbar)){
        buttonConfig.tbar.push(button);
      }
    } else {
      var grid = this.grid;
      var tName = (this.buttonConfig && this.buttonConfig.addToTopToolbar)? 'topToolbar' : 'bottomToolbar';
      var tShortName = (this.buttonConfig && this.buttonConfig.addToTopToolbar)? 'tbar' : 'bbar';
      if (grid.rendered){
        if (!grid[tShortName]) grid[tShortName] = new Ext.Toolbar();
        grid[tShortName].addButton(button);
      } else if (Ext.isArray(grid[tName])) {
        grid[tName].splice(0,0,button);
      } else if (grid[tName]){
        grid[tName].insertButton(0, button);
      } else {
        this.elements += ','+tShortName;
        grid[tName] = grid.createToolbar(button);
      }
    }
  },
  /**
   * Applies meta rules to each field in the panel.
   */
  applyPanelMetaRules : function(){
    var updatePanel = this.getUpdatePanel();
    if(updatePanel){
      updatePanel.cascade(function(f){
        if(f.mapping){
          if(f.isXType('checkboxfield') || f instanceof Jaffa.form.CheckBoxField){
            var field = f.getFormField();
            if(field){
              Jaffa.component.PanelController.prototype.applyFieldMetaRules(field, null,null,updatePanel);
              field.setReadOnly(false);
              Ext.apply(f, {
                fieldLabelToken : field.fieldLabelToken,
                fieldLabel : field.fieldLabel,
                allowBlank : field.allowBlank,
                hidden : field.hidden,
                hideLabel : field.hideLabel,
                disabled : field.disabled || field.textOnly
              });
            }
          } else {
            Jaffa.component.PanelController.prototype.applyFieldMetaRules(f,null,null,updatePanel);
          }
        }
      },this)
    }
  },
  /**
   * Displays update panel wrapped inside a modal window
   */
  showWindow:function(record, animEl) {
    if (this.fireEvent('beforewindowshow', this) !== false) {
      if(!this.grid.getSelectionModel().hasSelection()){
        Ext.MessageBox.alert(this.errorText,this.noSelectionErrorText);
        return false;
      }
      // lazy create window
      if(!this.window) {
        this.window = this.getWindow();
      }
      // show window
      this.window.show(animEl);
    }
  } // eo function show

  /**
   * Updates selected records in the store
   * @private
   */
  ,updateSelectedRecords:function() {
    var selections = this.grid.getSelectionModel().getSelections();
    var data = this.getModifiedPanelData();
    if(!data.isChanged){
      Ext.MessageBox.alert(this.errorText, this.notSelectedForUpdatedTitleText);
      return false;
    }
    var saveData = [];
    var keyField = this.keyField || ClassMetaData[this.getUpdatePanel().metaClass].key;
    var ctrl = this.getController();
    for(var i=0; i < selections.length; i++){
      var obj = {};
      if (Ext.isArray(keyField)) {
        for (var j=0; j < keyField.length; j++) {
          if(selections[i].data[keyField[j]]){
            obj[(this.keyFieldMapping && this.keyFieldMapping[keyField[j]]) || keyField[j]] = selections[i].data[keyField[j]];
          }
        }
      } else{
        if(selections[i].data[keyField]){
          obj[(this.keyFieldMapping && this.keyFieldMapping[keyField]) || keyField] = selections[i].data[keyField];
        }
      }
      Ext.applyIf(obj,data);
      saveData.push(obj);
    }
    if (this.fireEvent('beforeupdate', this, selections, saveData) !== false) {
      var me = this;
      ctrl.processSave(saveData, function(serverResponse){
        if (serverResponse && serverResponse.length > 0) {
          me.onSave.call(me,serverResponse);
        }
      },this);
    }
  },
  validateData : function(){
    var valid = true;
    this.getUpdatePanel().cascade(function(f) {
      if(f.mapping && f instanceof Ext.form.Field){
        if(!f.isValid()){
          valid = false;
        }
      }

    }, this);
    return valid;
  },
  getModifiedPanelData : function(newData){
    var isChanged = false;
    var newData = newData || {};
    this.getUpdatePanel().cascade(function(f) {
      if (f.mapping != null  && f instanceof Ext.form.Field && !f.ignoreSave && !f.readOnly && !f.textOnly) {
        if((!f.isXType('checkboxfield') && f.getValue()) || (f instanceof Jaffa.form.CheckBoxField && f.isChecked())){
          Jaffa.data.Util.set(newData, f.mapping, f.getValue() ? f.getValue() : '');
          isChanged = true;
        }
      }
    }, this);
    newData.isChanged = isChanged;
    if (isChanged) {
      // For a FlexBean, mix-in the name of the associated FlexClass
      if (newData.flexBean && (typeof newData.flexBean == 'object')) {
        if (!newData.flexBean.dynaClass && this.flexFieldsConfig && this.flexFieldsConfig.flexBean && this.flexFieldsConfig.flexBean.dynaClass  
            && this.flexFieldsConfig.flexBean.dynaClass.name) {
          newData.flexBean.dynaClass = {name: this.flexFieldsConfig.flexBean.dynaClass.name};
        }
      }
    }
    return newData;
  },
  onSave : function(ctrl,cbOk, cbError,response){
    Ext.get(document.body).unmask();
    var ok = true;
    var msg = [];
    var foundPending = false;
    if (response) {
      for (var i = 0; i < response.length; i++) {
        var responseEl = response[i];
        if (responseEl.errors && (responseEl.errors.applicationExceptionArray || responseEl.errors.length > 0)) {
          // ApplicationExceptions
          var appExps = responseEl.errors.applicationExceptionArray ? responseEl.errors.applicationExceptionArray : responseEl.errors;
          for (var j = 0; j < appExps.length; j++) {
            var appExp = appExps[j];
            ok = false;
            var type = appExp.javaClassName ? appExp.javaClassName : appExp.type;
            if (type === "org.jaffa.soa.rules.PendingEventException" || type === "org.jaffa.soa.rules.WarningEventException") {
              foundPending = true;
              if (!responseEl._pendingEvents)
                responseEl._pendingEvents = [];
              responseEl._pendingEvents[responseEl._pendingEvents.length] = {
                event: appExp.arguments[0],
                params: appExp.params,
                text: appExp.localizedMessage,
                warn: (type === "org.jaffa.soa.rules.WarningEventException")
              };
            } else {
              var localMsg = this.getLocalizedErrorMsg((appExp.localizedMessage || this.defaultLocalizedErrorMessage),response[i]);
              msg.push([localMsg]);
            }
          }
        } else if (responseEl.runtimeError) {
          // FrameworkException
          ok = false;
          var localMsg = this.getLocalizedErrorMsg(responseEl.runtimeError.localizedMessage,response[i]);
          msg.push([localMsg]);
        }
      }
    } else {
      ctrl.processEventGraphs = null;
      // this.registeredPanels = null;
    }

    if(ok){
      this.onSaveSuccess(response)
      this.fireEvent("save",this, response);
    }  else {
      this.onSaveFailed(response,msg);
      if (!Ext.isEmpty(msg)) {
        this.displayErrors(msg);
        // If application exception occurs then remove all process events
        ctrl.processEventGraphs = null;
      } else if (foundPending) {
        // Only pending events came back. Invoke the default pending-event handler, but only in the absence of an error callback.
        ctrl._handlePendingEvents.call(ctrl,cbOk, cbError, response);
      }
      this.fireEvent("saveexception",this, response, foundPending);
    }
  },
  onSaveSuccess : function(response){
    this.grid.getStore().reload();
    this.closeWindow();
    this.afterUpdate(response);
  },
  onSaveFailed : function(response,errors){
    var sm = this.grid.getSelectionModel();
    var selections = sm.getSelections(),ds = this.grid.getStore();
    for(var i =0 ; i < selections.length; i++){
      if(!this.hasError.call(this,response,selections[i])){
        sm.deselectRow(ds.indexOfId(selections[i].id));
      }
    }
    this.reconfigureSelGridStore();
  },
  hasError : function(response,rec){
    var error = false;
    var keyField = this.keyField || ClassMetaData[this.getUpdatePanel().metaClass].key;
    for(var i =0 ; i< response.length; i++){
      var source = response[i].source;
      if(source){
        if (Ext.isArray(keyField)) {
          var matchCount = 0;
          for (var j=0; j<keyField.length; j++) {
            if(rec.get(keyField[j]) === source[(this.keyFieldMapping && this.keyFieldMapping[keyField[j]]) || keyField[j]]){
              matchCount++;
            }
          }
          if(matchCount === keyField.length){
            error = true;
            break;
          }
        } else if(rec.get(keyField) === source[(this.keyFieldMapping && this.keyFieldMapping[keyField]) || keyField]){
          error = true;
          break;
        }
      }
    }
    return error;
  },
  getLocalizedErrorMsg : function(msg,response){
    return msg;
  },
  displayErrors : function(errors){
    if(Ext.isArray(errors)){
      if(errors.length === 1){
        Ext.MessageBox.show( {
          title : this.saveErrorsTitleText,//FIXME
          msg : errors[0][0], // TODO can we escape the return of this for XSS or does this include html styling?
          // width : 400,
          buttons : Ext.MessageBox.OK,
          icon: Ext.MessageBox.ERROR
        });
      } else {
        var store = new Ext.data.ArrayStore({
          fields: [
            {name: 'errorMsg'}
          ]
        });
        store.loadData(errors);

        // create the Grid
        var grid = new Ext.grid.GridPanel({
          store: store,
          columns: [ {header: this.errorsTitleText, width: 680, sortable: true, dataIndex: 'errorMsg'}],
          stripeRows: true,
          height:350,
          width:700,
          title:this.saveErrorsTitleText,
          trackMouseOver:false
        });


        var win =new Ext.Window({
          layout :'fit',
          width:750,
          items: [grid],
          modal : true,
          buttons:  [{
            text:this.okButtonText
            ,scope:this
            ,handler:function(){
              win.close();
            }
          }]
        });
        win.show();
      }
    }
  },
  clearFields : function(){
    this.getUpdatePanel().cascade(function(f){
      if(f instanceof Ext.form.Field){
        f.reset();
      }
    })
  },
  getController: function () {
    var me = this;
    return new Jaffa.component.CRUDController ({
      proxy: this.updateProxy || new Jaffa.data.DWRCRUDProxy({
        update: this.grid.store.proxy.update
      }),
      pendingEventConfig: me.pendingEventConfig,
      _saveOk :function(cbOk, cbError, response) {
        me.onSave.call(me,this,cbOk, cbError,response);
      },
      processSave: function(saveModel, msgToken) {
        this.addListener('beforesave', function (c) {
          if(this.processEventGraphs && saveModel && Ext.isArray(saveModel)){
            for (var i=0; i < saveModel.length > 0 ; i++){
              if(!saveModel[i].processEventGraphs){
                saveModel[i].processEventGraphs = this.processEventGraphs;
              }
            }
          }
          c.saveModel = saveModel;
        });
        // Invoke save method on the controller to cancel the selected kit orders
        this.save(function (model, response) {

        });
      }
    });
  },
  addFlexPanel : function(){
    var updatePanel = this.getUpdatePanel();
    if(updatePanel &&  Ext.isFunction(updatePanel.add)){
      var panel = this.getFlexFieldsPanel(updatePanel.metaClass);
      if(panel){
        updatePanel.add(panel);
      }
    }
  },
  getFlexFieldsPanel : function(metaClass){
    //TODO: Need to clean up and have Jaffa.maintenance.FlexFields to return flexInfo
    var config = this.getFlexFieldsConfig();
    var metaClass =  metaClass || this.config.metaClass;
    if(config.flexBean  || (metaClass && Jaffa.maintenance.FlexFields.isPresent({ metaClass: metaClass}))){
	// determine flexInfo, an array of objects, each containing the source attribute
      var flexInfo;

      if (config.flexBean) {
        // flexBean should contain dynaClass.name
        if (config.flexBean.dynaClass && config.flexBean.dynaClass.name)
          flexInfo = [{source: config.flexBean.dynaClass.name}];
      } else if ( metaClass) {
        // metaClass should contain flexInfo
        var meta = Ext.isString(metaClass) ? ClassMetaData[metaClass] : metaClass;
        flexInfo = meta.flexInfo;
      }

      if (!flexInfo || flexInfo.length == 0)
        return null;

      // add fields for each element of the flexInfo to the config
      var items = config.items || [];

      for (var i = 0; i < flexInfo.length; i++) {
        var flexSource = flexInfo[i].source;
        if (!flexSource)
          continue;

        // Retain only the simple name from the fully-qualified flex class name
        var flexClass = flexSource.split('.');
        flexClass = flexClass[flexClass.length - 1];

        if (!ClassMetaData[flexClass]) {
          // load metadata for the flex class
          // @todo: load the metadata asynchronously and build the panel in the response handler
          var metaSource = Ext.Ajax.synchronousRequest({
            url: 'js/extjs/jaffa/metadata/classMetaData.jsp',
            params: {
              className: flexSource
            }
          });
          if (metaSource)
            eval(metaSource);
        }


        // add each field of the flex class to items
        for (var fieldName in ClassMetaData[flexClass].fields) {
          var fieldMeta = ClassMetaData[flexClass].fields[fieldName];
          if (fieldMeta.hidden)
            continue;

          Ext.apply(fieldMeta, {
            metaClass : flexClass,
            dynaClass: flexInfo[0].source,
            layout : undefined
          });

          if(fieldMeta.label) fieldMeta.fieldLabel = fieldMeta.label;
          if(fieldMeta.labelToken) fieldMeta.fieldLabelToken = fieldMeta.labelToken;
          if(fieldMeta.type == 'boolean'){
            fieldMeta.xtype = 'booleancombo';
          }
          // create a config for a field and apply defaults
          items.push({
            xtype : 'checkboxfield',
            mapping: 'flexBean.' + fieldName,
            field : fieldMeta
          });
        }
      }
      var column1 = [],column2 = [];
      if (items.length > 0){
        for(var i=0 ; i < items.length; i++){
          if(i % 2 === 0){
            column1.push(items[i])
          } else {
            column2.push(items[i])
          }
        }
        config.items = [{
          xtype : 'form',
          columnWidth: .5,
          items :column1
        },{
          xtype : 'form',
          columnWidth: .5,
          items :column2
        }]
        // create the Panel
        return new Ext.form.FieldSet(config);
      } else
        return null;
    }
  },
  getFlexFieldsConfig : function(){
    var winWidth = (this.windowConfig && this.windowConfig.width) || this.defaultWindowConfig.width;
    var config = {
      frame: false,
      border : true,
      layout : 'column',
      title: this.flexFieldsTitleText,
      autoScroll: true,
      width : Ext.isNumber(winWidth)? (winWidth -50) : '95%',
      collapsed : true,
      collapsible:true,
      ref : 'flexFieldsPanel',
      itemId:'flexFieldsPanel',
      bodyStyle : 'padding:5px'
    }
    Ext.apply(config,this.flexFieldsConfig);
    return config;
  },
  getGridForSelections : function(){
    var ds = this.grid.getStore();
    var store = new Ext.data.Store({
      recordType: ds.recordType,
      reader : ds.reader
    });

    var config = Ext.applyIf(this.selectionsGridConfig,{
      store : store,
      height : 150,
      disableSelection : true,
      region : 'north',
      layout : 'fit'
    });
    if(!config.columns && !config.colModel && this.grid.getColumnModel()){
      var columns = this.grid.getColumnModel().getColumnsBy(function(c){
        return !(c.hidden || c instanceof Ext.grid.RowSelectionModel);
      });
      config.columns = columns;
    }
    var grid = new Ext.grid.GridPanel(config);
    return grid ;
  },
  reconfigureSelGridStore : function(){
    if(this.selectionsGrid){
      var store = this.selectionsGrid.getStore();
      store.removeAll();
      var sm = this.grid.getSelectionModel();
      var selections = sm.getSelections();
      var records = [];
      for(var i=0; i< selections.length; i++){
        records.push(selections[i].copy());
      }
      store.add(records);
    }
  }
}); // eo extend



// register ptype
Ext.preg('multirowupdate', Jaffa.maintenance.plugins.MultiRowUpdate);

