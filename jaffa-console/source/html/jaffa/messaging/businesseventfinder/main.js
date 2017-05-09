
/***************************************************/
/* Main Entry Point Once Page Has Loaded All Files */
/***************************************************/
Ext.onReady(function(){
  Ext.QuickTips.init();

  var viewport = new Ext.Viewport({
    layout: 'border',
    autoScroll: true,
    items: [{
		    xtype: 'finderheader',
		    title: Labels.get('title.Jaffa.Messaging.BusinessEventFinder.consolidated'),  //Business Event Log Inquiry
		    dataDictionaryDomain: 'BusinessEventLog',
		    helpPath: 'jaffa/messaging/businesseventfinder'
      },
      new Jaffa.Messaging.FinderContainer({
		    layout: params._layout,
		    id:'finderContainer',
		    resultsPanel:{
		      id: 'businessEventResultsPanel',
		      listeners: {
		        rowclick:function(grid, recId, evt){
              if (params._mode != 'nt') {
                if (recId >= 0) {
                  Ext.getCmp('updateButton').setDisabled(false);
                  Ext.getCmp('businessEventFastAccess').keyField.setValue(this.store.getAt(recId).data.logId);
                }
              }
              return true;  
            },
						rowdblclick:function(grid, recId, evt){
              if (params._mode != 'nt') {
                if (recId >= 0) {
                  Ext.getCmp('updateButton').setDisabled(false);
									var pkVal = this.store.getAt(recId).data.logId;
									Ext.getCmp('businessEventFastAccess').keyField.setValue(pkVal);
                  window.open(params.appCtx + '/jaffa/messaging/businesseventmaintenance/main.jsp?logId=' + encodeURIComponent(pkVal));
                }
              }
              return true;  
            }
		      }
        }
     }),
     new Jaffa.Messaging.FastAccess()
  ]
	
  });

  if (params.displayResultsScreen == "true") {
    viewport.findById('finderContainer').criteriaPanel.search();
  } else {
    // if there is a default query, load it and do a search();
    viewport.findById('finderContainer').criteriaPanel.runDefaultQuery();
  }

});
