Jaffa.Transaction.EventLogPanel = new Jaffa.Messaging.FinderContainer({
  id:'eventLogPanel',
  ref:'eventLogPanel',
  title: Labels.get('label.Jaffa.Messaging.BusinessEventLog'),
  resultsPanel:{
    ref: 'resultsPanel',
    id: 'businessEventResultsPanel',
    header: false,
    listeners: {
      rowdblclick:function(grid, recId, evt){
        if (recId >= 0) {
          var pkVal = this.store.getAt(recId).data.logId;
          window.open(params.appCtx + '/jaffa/messaging/businesseventmaintenance/main.jsp?logId=' + encodeURIComponent(pkVal));
        }
        return true;
      }
    }
  },
  criteriaPanel: {
    hidden: true
  },
  criteria: {},
  load: function(){
    this.resultsPanel.bottomToolbar.getComponent('excel').enable();
  }
});