/**
 * @author ChrisO
 */

ClassMetaData.TransactionGraph.key = 'id';
ClassMetaData.TransactionDependencyGraph.key = 'transactionId';
ClassMetaData.TransactionPayloadGraph.key = 'id';
ClassMetaData.BusinessEventLogGraph.key = 'logId';

Jaffa.Transaction.controller = new Jaffa.component.CRUDController({
  proxy: new Jaffa.data.DWRCRUDProxy({
    query: Jaffa_Transaction_TransactionService.query,
    update: Jaffa_Transaction_TransactionService.update
  }),
  reader: new Jaffa.data.DWRQueryResponseReader({
    id: 'id'
  }),
  loadMask: true,
  criteria: {
    objectLimit: 1,
    resultGraphRules: [
      'id',
      'direction',
      'type',
      'subType',
      'status',
      'errorMessage',
      'createdOn',
      'createdBy',
      'createdBy',
      'lastChangedOn',
      'lastChangedBy',
      'transactionPayload.*',
      'transactionFields.*',
      'transactionDependencies.*'
    ]
  },
  initModel : function(){
    if (!this.model) {
      this.model = new Ext.util.MixedCollection();
    }
    if (this.model.getCount()==0) {
      this.model.add({className : 'TransactionGraph'});
    }
  },
  listeners:{
    load: function(ctrl){
      //The eventLog panel is lazy loaded when it is activated.
      var viewport = Ext.getCmp('viewport');
      var eventLogPanel = viewport.bodyPanel.getComponent('eventLogPanel');
      eventLogPanel.on('activate', function(){
        Ext.apply(eventLogPanel.criteriaPanel.baseParams, {messageId: {values:[params.id]}});
        eventLogPanel.criteriaPanel.search();
      },eventLogPanel,{single: true});
      if(viewport.bodyPanel.layout.activeItem === eventLogPanel){
        eventLogPanel.fireEvent('activate');
      }
    }
  }
});


Jaffa.Transaction.loadScreen = function(){

  Jaffa.Transaction.controller.initModel();

  var viewport = new Jaffa.maintenance.Viewport({
    metaClass: 'TransactionGraph',
    controller: Jaffa.Transaction.controller,
    nav: null,  // not needed for this design
    layout: 'border',
    id: 'viewport',
    style: 'padding 0px; margin: 0px',
    header: new Product1.maintenance.HeaderPanel({
      helpPathName: 'jaffa/transaction/transactionmaintenance',
      dataDictionaryDomain: 'Transaction',
      headerTitle: Labels.get('title.Jaffa.Transaction.TransactionMaintenance.Maintenance'), //Transaction Maintenance
      generateHeaderTitleSuffix: function() {  //Overriden because we dont want to display the tech-key 
        var text = '';
        if (this.controller) {  
          text = '';
          if (this.controller.isLoaded && this.controller.model) {
            text += '';
          } else {
            text += ' '+Labels.get('title.product.maintenance.newrecord.suffix');
          }
        }
        return text;
      }
    })
  });
  viewport.bodyPanel.hide();
  
  viewport.addPanel(Jaffa.Transaction.DetailPanel);
  viewport.addPanel(Jaffa.Transaction.DependencyPanel);
  viewport.addPanel(Jaffa.Transaction.PayloadPanel);
  viewport.addPanel(Jaffa.Transaction.EventLogPanel);
  
  viewport.bodyPanel.setActiveTab(0);
  viewport.bodyPanel.show();
  viewport.bodyPanel.doLayout();
  Ext.get(document.body).unmask();

};


Ext.onReady(function(){
  Ext.QuickTips.init();
  if (params.id) {
    Jaffa.Transaction.controller.criteria.id = {
      values: [params.id]
    };
    Jaffa.Transaction.controller.load(function(ctrl){
      if (ctrl.model) {
        params._update = true;
        Jaffa.Transaction.loadScreen();
      } else if (params._update) {
        Ext.Msg.show({
           title: Labels.get('title.jaffaRIA.NotFound'),
           msg: Labels.get('label.jaffaRIA.NotFound2.Message') + ' ' + params.id,
           buttons: Ext.Msg.OK,
           fn: function(btn, text){
              window.close();
           },
           icon: Ext.MessageBox.ERROR
        });  
      } else {
        params._update = false;
        Jaffa.Transaction.loadScreen();
      }
    });
  } else {
    params._update = false;
    Jaffa.Transaction.loadScreen();
  }
});
