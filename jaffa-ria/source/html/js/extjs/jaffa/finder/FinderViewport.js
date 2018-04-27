/*!
 * @author : Prudhvi K
 */
/**
 * @class Jaffa.form.FinderViewport
 * @extends Ext.Viewport
 * <p>A specialized viewport to build a finder/integrated finder screen.</p>.
 * This finder viewport will automatically create finderContainer(criteria,results) and fast access panel
 * based on the config passed. For the creation of integrated finder maintenance panel is required.
 <pre><code>
 (
 function () {
 var record = Jaffa.data.Record.create([{
 name: 'userName',
 sortFieldName: 'UserName',
 filter:true,
 sortable: true
 } .......
 ]);

 var columns = ['userName',.......]

 Ext.create({
 xtype : 'finderViewport',
 criteriaPanels : [{xtype :'panel1'}.....],
 metaClass : 'UserGraph',
 dataDictionaryDomain: 'User',
 helpPath: 'domain/subdomain/user',
 titleToken: 'labe.user.user...',
 queryService :  User_UserService.query,
 updateService : User_UserService.update,
 record : record,
 columns : columns,
 maintenanceCompName : 'User.UserMaintenance'
 gridPlugins : [new Ext.ux.plugins.ExportToExcelPlugin({
 serviceClassName: 'com.package..........UserService',
 criteriaClassName: 'com.package..........UserCriteria'
 })],
 hasFastAccess : false,
 hasMaintenanceAccess : security.hasMaintenanceAccess,
 maintenancePanel:{
 xtype: 'maintenancePanel',
 height: 300
 }
 });
 }
 )();
 </code></pre>
 * @constructor
 * Create a new Finder Viewport
 * @param {Object} config The config object
 * @xtype finderViewport
 */
Jaffa.form.FinderViewport = Ext.extend(Ext.Viewport, {
  /** @cfg {String} metaClass (@mandatory)
   *  Meta Class that used to retrieve meta data for the finder. This property is mandatory
   */
  metaClass:undefined,
  /** @cfg {String} dataDictionaryDomain (@mandatory)
   *  Data Dictionary domain name to retrieve dictionary for that domain. This property is mandatory
   */
  dataDictionaryDomain:undefined,
  /** @cfg {String} helpPath (@mandatory)
   *  Help path for the user help. This property is mandatory
   */
  helpPath:undefined,
  /** @cfg {String} titleToken (@mandatory)
   *  Localized title token string for the finder. This property is mandatory. The token is used to
   *  construct Header,Criteria,Results section titles.
   */
  titleToken:undefined,
  /** @cfg {String} queryService (@mandatory)
   *  Namespaced query service name that will be used to query by the finder. This property is mandatory
   */
  queryService:undefined,
  /** @cfg {String} updateService (@optional)
   *  Namespaced update service name that will be used to update by the integrated maintenance panel.
   *  This property is only required for integrated maintenance.
   */
  updateService:undefined,
  /** @cfg {Object} record (@mandatory)
   *  The record that will be used by the finder to query and store results.
   *  This property is mandatory
   */
  record:undefined,
  /** @cfg {Object} criteriaPanels (@mandatory)
   *  Array of criteria panels/fieldsets that needs to rendered in to the criteria section.
   *  This property is mandatory
   */
  criteriaPanels:undefined,
  /** @cfg {Object} columns (@mandatory)
   *  Array of columns that needs to be displayed in the results sections .
   *  This property is mandatory
   */
  columns:undefined,
  /** @cfg {Object} gridPlugins (@optional)
   *  Plugins that needs to applied to results grid. Usually exportToExcel plugin
   *  This property is optional
   */
  gridPlugins:undefined,
  /** @cfg {String} maintenanceCompName (@optional)- Mandatory for finder pattern and optional of integrated maintenance
   * The maintenance component name that will be invoked from fast access. Ex: User.Component.UserMaintenance.
   *  window.open(params.appCtx + '/startComponent.do?component=User.Component.UserMaintenance) will be invoked from fast access.
   */
  maintenanceCompName:undefined,
  /** @cfg {Object} maintenancePanel (@optional)- Mandatory for integrated maintenance
   *  The maintenance panel that is used for integrated finder maintenance
   */
  maintenancePanel:undefined,
  /** @cfg {boolean} hasViewAccess (@default true)- Optional
   *  By setting this property to false will restrict access to maintenance/view screen
   *  This property is optional
   */
  hasViewAccess: true,
  /** @cfg {boolean} hasMaintenanceAccess (@default false)- Mandatory
   *  By setting this property to false will restrict access update access.
   *  This property is mandatory
   */
  hasMaintenanceAccess:false,
  /** @cfg {String} sortField - (@optional)
   *  If the sort field name is not passed. Key field will be the sort field
   */
  sortField:undefined,
  /** @cfg {String} keyField - (@optional)
   *  If the key field name is not passed, it will be derived from meta class
   */
  keyField:undefined,
  /** @cfg {String} keyField - (@optional)
   *  If the key param name is not passed, key field name will be param name
   */
  keyParamName:undefined,
  /** @cfg {Object} additionalResultGraphRules - (@optional)
   *  Additional results graph rules that needs to be passed as a part of query.
   */
  additionalResultGraphRules:[],
  /** @cfg {Object} hasFastAccess - (@optional)- Defaults to true.
   *  Setting this property to false will hide fast access panel
   */
  hasFastAccess:true,
  /** @cfg {Object} inlineMaintenance - (@optional)- Defaults to false.
   *  Setting this property to true will use pagingfindergrid.
   */
  inlineMaintenance : false,
  //Below are default configuration parameters/labels.
  deleteObjectLabel : 'deleteObject',
  layout:'border',
  autoScroll:true,
  updateLabel:'Update',
  viewLabel:'View',
  createLabel:'Create New',
  inquiryLabel:'Inquiry',
  criteriaLabel:'Criteria',
  resultsLabel:'List',
  fastAccessLabel:'Fast Access - ',
  saveChangesTitle:'Save Changes?',
  operationConfirmLabel:'Are you sure you want to perform this operation?',
  createNewRecLabel:' (Create New Record)',
  loseChangesMsg:'You are about to lose any unsaved changes. Do you want to continue?',
  deleteTitle:'Delete',
  deleteSuccessMsg:'The selected record(s) are successfully deleted.',
  errorTitle:'Error',
  warningTitle:'Warning',
  saveLabel:'Save',
  cancelLabel:'Cancel',
  selectAtLeastOneRecMsg:'You must select at least one record before performing this action',
  selectARecMsg:'You must select one record before performing this action.',
  defaultLocalizedErrorMessage: 'Internal errors occurred, please contact your system administrator.',
  addButtonText: 'Add',
  deleteButtonText:'Delete',
  recordsText: 'Records',
  recordText: 'Record',
  
  constructor: function(config) {

    Ext.apply(this, config);
    this.addEvents(
      /**
       * @event beforesave
       */
      'beforesave'
      /**
       * @event add
       */
      ,'add'
      /**
       * @event beforedelete
       */
      ,'beforedelete'
    );
    Jaffa.form.FinderViewport.superclass.constructor.apply(this, arguments);
  },
  initComponent:function () {
    var viewport = this;
    if (!this.keyField) {
      this.keyField = ClassMetaData[this.metaClass].key;
    }
    var store = new Ext.ux.grid.MultiGroupingPagingDWRStore({
      proxy:new Jaffa.data.DWRCRUDProxy({
        query:viewport.queryService,
        update:viewport.updateService
      }),
      groupField: viewport.groupField,
      reader:new Ext.data.DwrReader({}, viewport.record),
      sortInfo:{
        field:viewport.sortField || Ext.isArray(viewport.keyField) ? viewport.keyField[0] : viewport.keyField,
        direction:'ASC'
      }
    });
    var sm = viewport.sm || viewport.selModel || new Jaffa.grid.HideableCheckboxSelectionModel({});
    if(viewport.maintenancePanel){
      sm.on('rowdeselect',viewport.onRowDeSelect,viewport);
      sm.on('rowselect',viewport.onRowSelect,viewport);

      // We want to clear the detail panel when a new search is performed or the window is refreshed. So before the store is reloaded
      // check if records are being added (ie. not a refresh) and clear the detail panel as necessary.
      store.on('beforeLoad'
        ,function(store, options){
          if (!options.add){
            this.cancelData();
          }
        }
        ,viewport);
    }

    if(viewport.titleToken) viewport.title = Labels.get(viewport.titleToken);
    var columns  = [sm].concat(viewport.columns);
    if(Ext.isArray(viewport.gridPlugins)){
      Ext.each(viewport.gridPlugins,function(item){
        if(item.ptype === 'metacolumns' || item instanceof Ext.ux.plugins.MetaColumns){
          item.columns = columns;
          return false;
        }
      });
    }
    var items = [
      {
        xtype:'finderheader',
        title: viewport.title + ' ' + ((viewport.maintenancePanel || viewport.inlineMaintenance)? '' : viewport.inquiryLabel),
        dataDictionaryDomain:viewport.dataDictionaryDomain,
        helpPath:viewport.helpPath
      },
      {
        xtype:'findercontainer',
        ref:'finderContainer',
        id:'finderContainer',
        layout:'border',
        store:store,
        region:'center',
        criteria: params,
        criteriaPanel:{
          xtype:'criteriapanel',
          ref:'criteriaPanel',
          title: viewport.title + ' ' + viewport.criteriaLabel,
          items:viewport.criteriaPanels,
          metaClass:viewport.metaClass,
          defaults:{
            metaClass:viewport.metaClass,
            isSearchPanel:true
          }
        },
        resultsPanel:{
          sm:sm,
          xtype:(viewport.maintenancePanel || viewport.inlineMaintenance) ? 'pagingfindergrid' : 'multigroupingpagingfindergrid',
          ref:'resultsPanel',
          disabled: (viewport.maintenancePanel || viewport.inlineMaintenance)? false :true,
          columns: columns,
          metaClass:viewport.metaClass,
          title: viewport.title + ' ' + viewport.resultsLabel,
          view: (viewport.groupTextTpl) ? viewport.groupTextTpl : ((viewport.maintenancePanel) ? new Ext.grid.GroupingView({
            hideGroupedColumn:true,
            displayEmptyFields:true,
            groupTextTpl:'{text} ' + '({values.rs.length} ' + '{[values.rs.length > 1 ? this.recordsText : this.recordText]})  ',
            onLoad: function(grid,data,p){
               if (p && p.initial==true){
       	          if (Ext.isGecko) {
	                 if (!this.scrollToTopTask) {
	                    this.scrollToTopTask = new Ext.util.DelayedTask(this.scrollToTop, this);
	                 }
	                 this.scrollToTopTask.delay(1);
	              } else {
	                  this.scrollToTop();
	              }
	           }
	        }
          }) : new Ext.ux.grid.MultiGroupingView({
            hideGroupedColumn:true,
            displayEmptyFields:true,
            groupTextTpl:'{text} : {group} ' + '({values.rs.length}{[values.incomplete?"+":""]} ' + '{[values.rs.length > 1 ? this.recordsText : this.recordText]})  '
          })),
          plugins:viewport.gridPlugins,
          getResultGraphRules:function () {
            var rules = Jaffa.finder.MultiGroupingPagingFinderGrid.prototype.getResultGraphRules.call(this);
            return rules.concat(viewport.additionalResultGraphRules);
          },
          listeners:{
            rowclick:function (grid, recId, evt) {
              viewport.onGridRowClick.call(viewport, grid, recId, evt)
            },
            rowdblclick:function (grid, recId, evt) {
              viewport.onGridRowDblClick.call(viewport, grid, recId, evt)
            }
          },
          tbar:(viewport.maintenancePanel) ? [
            {
              iconCls:'add',
              text:this.addButtonText,
              disabled:!viewport.hasMaintenanceAccess,
              handler:function () {
                viewport.onAddRec.call(viewport);
              }
            },
            {
              iconCls:'delete',
              text:this.deleteButtonText,
              disabled:!viewport.hasMaintenanceAccess,
              handler: function(){
                viewport.deleteHandler.call(viewport);
              }
            }
          ] : undefined
        }
      }
    ];

    if (viewport.hasFastAccess && !viewport.maintenancePanel) {
      var fastAccess = viewport.getFastAccess.call(viewport);
      if(fastAccess){
        items.push(fastAccess);
      }
    } else if (viewport.maintenancePanel) {
      viewport.maintenancePanel.ref = 'maintenancePanelRef';
      Ext.applyIf(viewport.maintenancePanel,{
        collapsible: true,
        collapsed: true,
        disabled: true,
        bodyStyle: 'background-color:#DFE8F6',
        hideCollapseTool: false,
        height: 300,
        tbar : [{
          id:'save',
          iconCls:'save',
          text:viewport.saveLabel,
          scope:this,
          disabled:true,
          handler:function () {
            viewport.doSave.call(viewport);
          }
        },
          {
            id:'cancel',
            iconCls:'cancel',
            text:viewport.cancelLabel,
            scope:this,
            handler:function () {
              viewport.cancelData(true);
            }
          }
        ]
      });
      items.push(viewport.maintenancePanel);
    }
    Ext.apply(this, {
      defaults:{
        xtype:'textfield',
        metaClass:viewport.metaClass
      },
      items:items
    });
    Jaffa.form.FinderViewport.superclass.initComponent.call(this);
  },
  listeners:{
    'beforerender':function (viewport) {
      var maintPanel = viewport.maintenancePanelRef;
      if(maintPanel){
        if(!maintPanel.baseTitle){
          maintPanel.baseTitle = (maintPanel.titleToken) ? Labels.get(maintPanel.titleToken) :maintPanel.title
        }
        maintPanel.controller.applyPanelFieldsMetaRules(maintPanel);
        maintPanel.on('render', function(){
          maintPanel.controller.loadMaskComponent=maintPanel.getEl();
        });
        // Listen to changes on the Panel so we can add an "modified" indicator in the tab
        maintPanel.on("datamodified", function() {
          if (this.isDirty) {
            this.title_clean = this.title;
            this.setTitle('*' + this.title);
          }
          else if (this.title_clean) {
            this.setTitle(this.title_clean);
            this.setTitle(this.title_clean);
            delete this.title_clean;
          }
        }, maintPanel);
      }
    },
    'afterrender':function (viewport) {
      if (viewport.maintenancePanelRef) {
        viewport.maintenancePanelRef.controller.registerPanel(viewport.maintenancePanelRef, viewport.maintenancePanelRef.findDataSource());

        viewport.maintenancePanelRef.controller.on('load',function(ctrl,response){
          if (response  && response.errors && response.errors[0]) {
            Ext.Msg.alert(viewport.errorTitle, response.errors[0].localizedMessage);
          }
          viewport.maintenancePanelRef.setTitle((viewport.maintenancePanelRef.baseTitle || viewport.title) + ' (Modify)') ;
        })
      }
      if (params.displayResultsScreen == "true") {
        viewport.finderContainer.criteriaPanel.search();
      } else {
        // if there is a default query, load it and do a search();
        viewport.finderContainer.criteriaPanel.runDefaultQuery();
      }
    }
  },
  onAddRec:function () {
    this.supressRowDeSelect = true;
    this.finderContainer.resultsPanel.getSelectionModel().clearSelections();
    if (this.cancelData(false, this.doAddRec,this)!=false){
      this.doAddRec();
    }
  },
  doAddRec: function() {
    this.maintenancePanelRef.expand();
    this.maintenancePanelRef.setTitle((this.maintenancePanelRef.baseTitle || this.title) +' '+ this.createNewRecLabel);
    this.maintenancePanelRef.controller.registerPanel(this.maintenancePanelRef, this.maintenancePanelRef.findDataSource());
    this.enableMaintenancePanel();
    this.fireEvent('add', this, this.maintenancePanelRef);
    this.supressRowDeSelect = false;
  },
  enableMaintenancePanel : function(){
    this.maintenancePanelRef.enable();
  },
  deleteHandler : function () {
    var viewport = this;
    var selected = viewport.finderContainer.resultsPanel.getSelectionModel().getSelections();
    if (selected.length > 0) {
      if (this.fireEvent("beforedelete", this,selected) !== false) {
        Ext.Msg.show({
          title:viewport.saveChangesTitle,
          msg:viewport.operationConfirmLabel,
          buttons:Ext.Msg.YESNO,
          scope:this,
          fn:function (result) {
            if (result == 'yes') {
              viewport.onDeleteRec.call(viewport, selected);
            } else if (result == 'no') {
              return false;
            }
          },
          animEl:'elId',
          icon:Ext.MessageBox.QUESTION
        });
      }
    } else {
      Ext.MessageBox.alert(viewport.warningTitle, viewport.selectAtLeastOneRecMsg);
    }
  },
  onDeleteRec:function (selections) {
    var deleteCriteria = this.retrieveDeleteCriteria(selections);
    var viewport = this;
    this.finderContainer.store.proxy.save(deleteCriteria, function (response) {
      if(viewport.validateResponse(response) !== false){
        Ext.Msg.alert(viewport.deleteTitle, viewport.deleteSuccessMsg);
        viewport.finderContainer.store.loadMore(true);
      } else {
        if (viewport.maintenancePanelRef) viewport.maintenancePanelRef.enable();
      }
    });
    if (this.maintenancePanelRef) this.maintenancePanelRef.disable();
  },
  retrieveDeleteCriteria : function(selections){
    var deleteCriteria = [];
    for (var i = 0; i < selections.length; i++) {
      if (this.keyField) {
        var obj = {};
        obj[this.deleteObjectLabel] = true;
        if (Ext.isArray(this.keyField)) {
          for (var j = 0; j < this.keyField.length; j++) {
            obj[this.keyField[j]] = selections[i].get(this.keyField[j]);
          }
        } else {
          obj[this.keyField] = selections[i].get(this.keyField);
        }
        deleteCriteria.push(obj);
      }
    }
    return deleteCriteria;
  },
  onRowSelect:function (sm, rowIndex, rec) {
    if (sm.getCount() != 1) {
      this.cancelData(true);
    } else {
      this.loadRecord(sm.getSelected());
    }
  },
  onRowDeSelect:function (sm, rowIndex, rec) {
    if (!this.supressRowDeSelect) {
      if (sm.getCount() != 1) {
        this.cancelData(true);
      } else {
        this.loadRecord(sm.getSelected());
      }
    }
  },
  onGridRowClick:function (grid, recId, evt) {
    if (this.hasFastAccess && this.hasViewAccess && recId >= 0) {
      grid.ownerCt.ownerCt.fastAccessRef.keyFieldRef.setValue(grid.store.getAt(recId).data[this.keyField]);
    }
    return true;
  },
  onGridRowDblClick:function (grid, recId, evt) {
    if (this.hasFastAccess && this.hasViewAccess && recId >= 0) {
      var pkVal = grid.store.getAt(recId).data[this.keyField];
      grid.ownerCt.ownerCt.fastAccessRef.keyFieldRef.setValue(pkVal);
      window.open(params.appCtx + '/startComponent.do?component=' + this.maintenanceCompName +
        '&finalUrl=jaffa_closeBrowser&' + (this.keyParamName || this.keyField) + '=' + encodeURIComponent(pkVal), "_blank");
    }
    return true;
  },
  loadRecord:function (rec) {
    if (this.maintenancePanelRef.isDirty) {
      Ext.Msg.show({
        title:this.saveChangesTitle,
        msg:this.loseChangesMsg,
        buttons:Ext.Msg.YESNO,
        scope:this,
        fn:function (result) {
          if (result == 'yes') {
            this.continueLoad(rec);
          } else if (result == 'no') {
            return false;
          }
        },
        animEl:'elId',
        icon:Ext.MessageBox.QUESTION
      });
    } else {
      this.continueLoad(rec);
    }
  },
  continueLoad:function (rec, response) {
    this.maintenancePanelRef.expand();
    this.maintenancePanelRef.enable();
    this.togglePanelFields(!this.hasMaintenanceAccess);
    if (rec) {
      if (this.keyField) {
        if (Ext.isArray(this.keyField)) {
          for (var i = 0; i < this.keyField.length; i++) {
            var val = (response) ? response[0].source[this.keyField[i]] : (rec.get(this.keyField[i]) ? rec.get(this.keyField[i]) : (rec.itemAt(0) && rec.itemAt(0)[this.keyField[i]]));
            if (val)
              this.maintenancePanelRef.controller.criteria[this.keyField[i]] = {  values:[val] };
          }
        } else {
          var val =  (response) ? (response[0].source[this.keyField]) : ((rec.itemAt && rec.itemAt(0)) ? rec.itemAt(0)[this.keyField] : (rec.get && rec.get(this.keyField)));
          if (val)
            this.maintenancePanelRef.controller.criteria[this.keyField] = {  values:[val] };
        }
      }
      this.maintenancePanelRef.controller.load();
    }
  },
  cancelData:function (textOnly, callback,scope) {
    if (this.maintenancePanelRef.isDirty) {
      Ext.Msg.show({
        title:this.saveChangesTitle,
        msg:this.loseChangesMsg,
        buttons:Ext.Msg.YESNO,
        scope:this,
        fn:function (result) {
          if (result == 'yes') {
            this.doCancel(textOnly);
            if (typeof callback == 'function'){
              callback.call(scope);
            }
          }
        },
        animEl:'elId',
        icon:Ext.MessageBox.QUESTION
      });
      return false;
    } else {
      this.doCancel(textOnly);
    }
  },
  resetMaintenancePanel:function () {
    this.maintenancePanelRef.controller.clearPanelFields(this.maintenancePanelRef);
    delete this.maintenancePanelRef.controller.model;
    this.maintenancePanelRef.controller.isLoaded = false;
    this.maintenancePanelRef.setDirty(false);
    if (this.keyField) {
      if (Ext.isArray(this.keyField)) {
        for (var i = 0; i < this.keyField.length; i++) {
          delete this.maintenancePanelRef.controller.criteria[this.keyField[i]];
        }
      } else {
        if (this.maintenancePanelRef.controller.criteria[this.keyField]) {
          delete this.maintenancePanelRef.controller.criteria[this.keyField];
        }
      }
    }
  },
  doCancel:function (textOnly) {
  // Here Whenever user tries to modify any field on click of Cancel button
	// Say field name is "Description", from value "testDescp" to "testDescp1"
	// Then after click on "yes" here, re-intializing to original value
	// i.e, the Description field value again appears as "testDescp" 
	// Ex: ScreenName = LocationCodes
	if (this && this.maintenancePanelRef && 
		(!(this.maintenancePanelRef.isDirty) && (textOnly != undefined))) {
		this.maintenancePanelRef.collapse();
	}	
	if(textOnly) {
		if(this && this.maintenancePanelRef) {
			this.maintenancePanelRef.setDisabled(false);
		}
		if(this && this.maintenancePanelRef && 
			this.maintenancePanelRef.loadData) {
			this.maintenancePanelRef.loadData(true);
		}
	} else {
			this.resetMaintenancePanel();
			this.togglePanelFields(textOnly);
			this.maintenancePanelRef.setTitle(this.maintenancePanelRef.baseTitle || this.title);
	}
  },
  togglePanelFields:function (textOnly) {
    this.maintenancePanelRef.cascade(function (item) {
      if(item.xtype == 'checkbox' || item.xtype == 'radio'){
        item.setDisabled(textOnly);
      }else if (Ext.isFunction(item.setTextOnly)) {
        item.setTextOnly(textOnly);
      }
    });
  },
  doSave:function () {
    var viewport = this;
    if (this.fireEvent("beforesave", this) !== false) {
      this.maintenancePanelRef.controller.save(function (model, response) {
        viewport.continueLoad(model, response);
        viewport.finderContainer.store.reload();
      }, this);
    }
  },
  validateResponse : function(response){
    if(response && Ext.isArray(response)){
      var msg="",hasError = false;
      for (var i = 0; i < response.length; i++) {
        var responseEl = response[i];
        if (responseEl.errors && (responseEl.errors.applicationExceptionArray || responseEl.errors.length > 0)) {
          // ApplicationExceptions
          var appExps = responseEl.errors.applicationExceptionArray ? responseEl.errors.applicationExceptionArray : responseEl.errors;
          hasError = true;
          for (var j = 0; j < appExps.length; j++) {
            var appExp = appExps[j];
            msg += (appExp.localizedMessage || viewport.defaultLocalizedErrorMessage) + '\n<br>';
          }
        } else if (responseEl.runtimeError) {
          // FrameworkException
          hasError = true;
          msg += responseEl.runtimeError.localizedMessage + '\n<br>';
        }
      }
      if (hasError) {
        Ext.Msg.alert(this.errorTitle,msg);
        return false;
      }
    }
    return true;
  },
  getFastAccess : function(){
    var viewport = this;
    var keyFieldMeta = ClassMetaData[viewport.metaClass].fields[viewport.keyField];
    return {
      xtype:'panel',
      region:"south",
      bodyStyle:'display:none', // Hack for IE
      collapsible:true,
      header:false,
      layout:'form',
      ref:'fastAccessRef',
      keys:({
        key:Ext.EventObject.ENTER,
        fn:function () {
          if (viewport.hasViewAccess) {
            window.open(params.appCtx + '/startComponent.do?component=' + viewport.maintenanceCompName +
              '&finalUrl=jaffa_closeBrowser&' + (viewport.keyParamName || viewport.keyField) + '=' + encodeURIComponent(viewport.fastAccessRef.keyFieldRef.getValue()), "_blank");
          }
        },
        scope:this
      }),
      tbar:[ viewport.fastAccessLabel + ' - ' + keyFieldMeta.label + ':&nbsp;', {
        xtype:'textfield',
        ref:'../keyFieldRef',
        style:'text-transform: uppercase'
      }, '-',
        {
          text:viewport.hasMaintenanceAccess ? viewport.updateLabel : viewport.viewLabel,
          scope:this,
          hidden:!(viewport.hasViewAccess),
          handler:function () {
            if (Ext.isEmpty(viewport.fastAccessRef.keyFieldRef.getValue())) {
              Ext.MessageBox.show({
                title:viewport.errorTitle,
                msg:viewport.selectARecMsg,
                width:400,
                buttons:Ext.MessageBox.OK
              });
              return false;
            }
            window.open(params.appCtx + '/startComponent.do?component=' + viewport.maintenanceCompName +
              '&finalUrl=jaffa_closeBrowser&' + (viewport.keyParamName || viewport.keyField) + '=' + encodeURIComponent(viewport.fastAccessRef.keyFieldRef.getValue()), "_blank");
          }
        }, '-', {
          text:viewport.createLabel,
          scope:this,
          hidden:!(viewport.hasViewAccess && viewport.hasMaintenanceAccess),
          handler:function () {
            window.open(params.appCtx + '/startComponent.do?component=' + viewport.maintenanceCompName +
              '&finalUrl=jaffa_closeBrowser', "_blank");
          }
        }
      ]
    }
  }
});

Ext.reg('finderViewport',Jaffa.form.FinderViewport);
