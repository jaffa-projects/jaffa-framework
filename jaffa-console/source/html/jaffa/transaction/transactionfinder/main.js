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
/***************************************************/
/* Main Entry Point Once Page Has Loaded All Files */
/***************************************************/
Ext.onReady(function(){
  
  Ext.QuickTips.init();

  var viewportConfigItems = [Jaffa.Transaction.TransactionFinderContainer({
    layout: params._layout,
    id:'finderContainer',
    ref:'finderContainer',
    resultsPanel:{
      listeners: {
        rowdblclick:function(grid, recId, evt){
          if (recId >= 0) {
            var gn = this.getStore().getAt(recId).get('id');
            if (gn) {
              window.open(params.appCtx + '/jaffa/transaction/transactionmaintenance/main.jsp?id=' + encodeURIComponent(gn));
            }
          }
          return true;  
        }
      }
    }
  }), {
    xtype: 'finderheader'
    ,title: Labels.get('title.Jaffa.Transaction.TransactionFinder')
    ,dataDictionaryDomain: 'Transaction'
    ,helpPath: 'jaffa/transaction/transactionfinder'
    ,useCloseButton: params._mode!='nt'
  }];
  
  viewport = new Ext.Viewport({
    layout: 'border',
    autoScroll: true,
    items: viewportConfigItems
  });
  var criteriaPanel = viewport.finderContainer.criteriaPanel;
  if (params.criteria){
    criteriaPanel.loadQuery(null, {criteria:params.criteria}, params.performQuery=='true');

    if(params.type){ //since the type criteria field is loaded remotely, we need to delay the setting of the value until after the store is loaded.
      var typeCriteriaField = viewport.finderContainer.criteriaPanel.transactionCriteriaPanel.typeCriteriaField;
      typeCriteriaField.store.on('load', function(cbo, recs){
        for(var i=0; i < recs.length; i++){
          if(recs[i].get('typeName') === params.type){
            typeCriteriaField.setRawValue(recs[i].get(typeCriteriaField.displayField));
            typeCriteriaField.assertValue();
          }
        }
      }, typeCriteriaField, {single: true});
      typeCriteriaField.store.load();
    }
  } else if(params.performQuery === "true"){
    viewport.finderContainer.criteriaPanel.search();
  } else {
    // if there is a default query, load it and do a search();
    viewport.finderContainer.criteriaPanel.runDefaultQuery();
  }

  
});
