Ext.onReady(function(){
  Ext.QuickTips.init();
  
  // Create main controller
  myAuditTransaction = new Jaffa.component.CRUDController ({
    proxy: new Jaffa.data.DWRCRUDProxy({
      query: Jaffa_Audit_AuditTransactionService.query,
      update: Jaffa_Audit_AuditTransactionService.update
    })
    ,reader: new Jaffa.data.DWRQueryResponseReader()
    ,criteria: {objectLimit: 1, resultGraphRules: ['**']}
    ,loadMask: true
  });
  
  // Create local funtion that will build the viewport
  buildViewPort = function(title){
    var viewport = new Jaffa.maintenance.Viewport( {
      controller : myAuditTransaction
      ,header : new Product1.maintenance.HeaderPanel({
        dataDicObjectName : 'AuditTransaction'
        ,helpPathName : 'jaffa/audit/audittransactionviewer'
        ,headerTitle: title
        ,generateHeaderTitleSuffix: function () {
          return '';
        }        
      }),
      nav: new Jaffa.maintenance.NavPanel({width: '300'})
    });
    viewport.navPanel.collapse();

    var lastUpdatedPanel = new Ext.form.FormPanel({
      metaClass: 'AuditTransactionGraph',
      layout: 'form',
      id: 'lastUpdateAccordion',
      defaultType: 'textfield',
      disabled: !params.defaultName,
      titleToken:'title.Jaffa.Audit.AuditTransactionFinder.LastUpdate',
      labelWidth: 85,
      items:[
        {
          mapping:'createdBy', 
          metaClass:'AuditTransactionGraph', 
          metaField:'createdBy', 
          textOnly:true
        }, {
          mapping:'createdOn', 
          metaClass:'AuditTransactionGraph', 
          metaField:'createdOn', 
          xtype:'xdatetime', 
          textOnly:true
        }
      ],
      plugins: [new Jaffa.maintenance.plugins.PanelLoadSave ()]
    }); 
    myAuditTransaction.applyPanelFieldsMetaRules(lastUpdatedPanel);
    viewport.addAccordionPanel(lastUpdatedPanel);

    return viewport;
  }

  myAuditTransaction.criteria.transactionId = {values: [params.transactionId]};
  myAuditTransaction.load(function() {
    // What to do after the load
    if (myAuditTransaction.isLoaded && myAuditTransaction.model && myAuditTransaction.model.getCount() == 1) {
      var viewport = buildViewPort(Labels.get('title.Jaffa.Audit.AuditTransactionViewer.titleHeader'));
      var auditTransactionDetail = new AuditTransactionDetail({controller: myAuditTransaction});
      viewport.addTabPanel(auditTransactionDetail);
      viewport.tabPanel.activate(auditTransactionDetail);
      viewport.doLayout();
    } else {
      Ext.get(document.body).update();
      //@FIXME: Use label for the error message
      Ext.MessageBox.show({
          titleToken: 'title.Jaffa.Audit.AuditTransactionFinder.LoadError',
          msg: Labels.get('title.Jaffa.Audit.AuditTransactionFinder.AuditTransaction')+' ' + params.transactionId + ' '+Labels.get('label.Jaffa.Audit.AuditTransactionFinder.NotFound'),
          width: 400,
          buttons: Ext.MessageBox.OK,
          fn: function(){
            console.debug("Close Browser Window");
            //@FIXME: Fire the handler associated with the close button
            window.close();
          }
      });
      return false;
    }
  });

});
