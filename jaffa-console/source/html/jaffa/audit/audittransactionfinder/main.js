// Page variable to access the data service, used by all widgets
var viewport = null;

// Create the Record Definition for the store 
 /** Parameters that are used by a record to make life easier for grid construction
  * @param name Simple name used throughout for this field
  * @param type What data type the field is (Default is string, others are float, int, date, ...)        
  * @param defaultValue The value to use in the record if there is no data for this field        
  * @param mapping JSON Path to get the value out of the data structure for this record, assumes 'name' if not supplied
  * @param sortable Boolean to indicate whether sorting is available from the server for this field
  * @param sortFieldName The alternate {name} to use when passing the sort by field list to the server, assumes 'name' if sortable is true and not provided
  * @param filter Boolean to indicate whether a filter will be provided to limit the selection on this column
  * @param filterFieldName The alternate {name} to use when passing the filtering clause to the server, assumes 'name' if filter is true and not provided
  * @param hidden Boolean to indicate if by default this column should be initially hidden  
  */  

function datetime(v, record){
    return v.format(Ext.util.Format.defaultDateTimeLayout);
}

var AuditTransactionRecord = Jaffa.data.Record.create([
  {
    name: 'transactionId',
    filter: true,
    sortFieldName: 'TransactionId',
    sortable: true,
    hidden: true
  },{
    name: 'processName',
    filter: true,
    sortFieldName: 'ProcessName',
    sortable: true
  },{
    name: 'subProcessName',
    filter: true,
    sortFieldName: 'SubProcessName',
    sortable: true
  },{
    name: 'reason',
    filter: true,
    sortFieldName: 'Reason',
    sortable: true
  }, {
    name: 'changeType',
    metaClass: 'AuditTransactionObjectGraph',
    filter: true,
    sortable: true
  }, {
    name: 'createdBy',
    filter: true,
    mapping:'createdBy', 
    metaField: 'createdBy',
    sortFieldName: 'CreatedBy',
    sortable: true,
    type: 'string'
  }, {
    name:'createdOn', 
    filter: true,
    sortFieldName: 'CreatedOn',
    sortable: true,
    convert: datetime
  },{
    name: 'objectName',
    metaClass: 'AuditTransactionObjectGraph',
    filter: true,
    sortFieldName: 'ObjectName',
    sortable: true,
    hidden: true
  },{
    name: 'objectLabel',
    metaClass: 'AuditTransactionObjectGraph',
    metaField: 'objectName',
    label: Labels.get('label.Jaffa.Audit.AuditTransactionFinder.ObjectLabel')
  },{
    name: 'fieldName',
    metaClass: 'AuditTransactionFieldGraph',
    filter: true,
    sortFieldName: 'FieldName',
    sortable: true,
    hidden: true
  },{
    name: 'fieldLabel',
    metaField: 'fieldName',
    metaClass: 'AuditTransactionFieldGraph',
    label: Labels.get('label.Jaffa.Audit.AuditTransactionFinder.FieldLabel')
  },{
    name: 'fromValue',
    metaClass: 'AuditTransactionFieldGraph',
    filter: true,
    sortFieldName: 'FromValue',
    sortable: true
  },{
    name: 'toValue',
    metaClass: 'AuditTransactionFieldGraph',
    filter: true,
    sortFieldName: 'ToValue',
    sortable: true
  }
]);

// Specify a default meta data class to use to lookup field details
AuditTransactionRecord.defaultMetaClass = 'AuditTransactionGraph';

var myStore = new Ext.ux.grid.MultiGroupingPagingDWRStore({
   proxy: new Ext.data.DWRProxy(Jaffa_Audit_AuditTransactionViewService.query,{
     //FIXME - filters: filters
   })
  ,reader: new Ext.data.DwrReader({idProperty:{}}, AuditTransactionRecord)
  ,sortInfo: {field: 'transactionId', direction: 'ASC'}
});

/***************************************************/
/* Main Entry Point Once Page Has Loaded All Files */
/***************************************************/
Ext.onReady(function(){
  Ext.QuickTips.init();

  // Create the Fast Access Panel
  var fastAccessPanel = new AuditTransactionFastAccess();
  // Create the Main Grid Panel for the results, and add the on-click events
  var auditTransactionGrid = new AuditTransactionGrid(myStore, 'auditTransactionGridPanel');
  // Single click prompts the transactionId into the Fast Access Panel
  auditTransactionGrid.on('rowclick', function(grid, recId, evt){
    if (recId >= 0) {
      var transactionId = this.getStore().getAt(recId).data.transactionId;
      console.debug("Audit Transaction Clicked " + transactionId);
      fastAccessPanel.transactionIdField.setValue(transactionId);
    }
    return true;  
  }, auditTransactionGrid);

  // Double click calls the audit transaction maintenance screen
  auditTransactionGrid.on('rowdblclick', function(grid, recId, evt){
    if (recId >= 0) {
      var transactionId = this.getStore().getAt(recId).data.transactionId;
      console.debug("Audit Transaction DoubleClicked " + transactionId);
      fastAccessPanel.transactionIdField.setValue(transactionId);
      fastAccessPanel.buttonAction();
    }
    return true;  
  }, auditTransactionGrid);

  // Create the criteria panel an default its values from the URL
  var auditTransactionCriteriaPanel = new AuditTransactionCriteriaPanel({
     store: myStore
    ,grid: auditTransactionGrid
    ,baseParams: {transactionId:{operator:'IsNotNull'}}
  });
  
  auditTransactionCriteriaPanel.setParamsToPanel(params);
  if (params && params.objectName){
    var record = new Ext.data.Record.create(
      {name: 'label'},
      {name: 'className'},
      {name: 'logicalName'}
    );
    auditTransactionCriteriaPanel.getComponent('AuditFieldSearchTab').domainCombo.store.add(new record({
      label: params.objectLabel,
      className: params.domainObject,
      logicalName: params.objectName
    }));    
    auditTransactionCriteriaPanel.getComponent('AuditFieldSearchTab').domainCombo.setValue(params.objectName);
    auditTransactionCriteriaPanel.getComponent('AuditFieldSearchTab').domainCombo.disable();
  }

  //if parameters include audit field data then pre-load fields
  if (params && params.auditFieldsList) {
    var fieldPanel = auditTransactionCriteriaPanel.getComponent('AuditFieldSearchTab');
    fieldPanel.auditableProperties.add(new fieldPanel.auditablePropertyRecord({
      auditableProperty: fieldPanel.auditableProperties.emptyText,
      className: ''
    }));
    for (var field in params.auditFieldsList) {
      fieldPanel.auditableProperties.add(new fieldPanel.auditablePropertyRecord({
        auditableProperty: params.auditFieldsList[field].label ? params.auditFieldsList[field].label : field,
        className: params.auditFieldsList[field].name
      }));
    }
    
    //if parameters also has criteria params, load those too
    if (params && params.auditFields && params.auditFields.length > 0) {
      for (var i = 0; i < params.auditFields.length; i++) {
        fieldPanel.items.each(function(auditField){
          if (auditField.displayField == 'auditableProperty' && !auditField.mapping) {
            auditField.mapping = params.auditFields[i].mapping;
            if (params.auditFieldsList && params.auditFieldsList[params.auditFields[i].mapping])
              auditField.items.itemAt(0).setValue(params.auditFieldsList[params.auditFields[i].mapping].label);
            else
              auditField.items.itemAt(0).setValue(params.auditFields[i].mapping);
            
            auditField.items.itemAt(2).setValue(params.auditFields[i].value);
            return false;
          }
        });
      }
    }
  }

  // Get this list of display columns off the grid
  // Use this to build the resultGraphRules parameter
  // Add this parameter to the baseParams on the CriteriaPanel
  var results=[];
  var check=[];
  var cm = auditTransactionGrid.colModel.config;
  for(var i=0; i<cm.length; i++) {
    var di = cm[i].dataIndex;
    var meta = myStore.recordType.getField(di);
    var f = (meta && meta.mapping) ? meta.mapping : di;
    if(!check[f]){
      results[results.length] = f;
      check[f]=true;
    }  
    // Bit of a hack to include the object in the list
    // This is because a explicit ref to a foriegn object is not enough
    var li=f.lastIndexOf('.');
    if(li>0) {
      f = f.slice(0,li);
      if(!check[f]){
        results[results.length] = f;
        check[f]=true;
      }  
    }  
  }

  auditTransactionCriteriaPanel.baseParams.resultGraphRules = results;
  //console.debug("Set Base Params ", auditTransactionCriteriaPanel.baseParams);

  viewport = new Ext.Viewport({
    layout: 'border',
    autoScroll: true,
    items: [{
        xtype: 'finderheader',
        dataDictionaryDomain: 'AuditTransaction',
        helpPath: 'jaffa/audit/audittransactionfinder',
        titleToken: 'label.Jaffa.Audit.AuditTransactionFinder.AuditTransactionInquiry', //Audit Transaction Inquiry
        listeners: {
          refresh: function(){
            // perform an initial load on the store based previous parameters
            myStore.loadMore(true);
          }
        }
      }, 
      fastAccessPanel, 
      auditTransactionGrid, 
      auditTransactionCriteriaPanel
    ]
  });
  
  // if preselect data is passed in then run a search otherwise if there is a default query, load it and do a search();
  if (params.auditFields) {
    auditTransactionCriteriaPanel.search();
  }
  else {
    auditTransactionCriteriaPanel.runDefaultQuery();
  }
  
  
});