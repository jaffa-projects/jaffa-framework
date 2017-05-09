Ext.onReady(function(){
  Ext.QuickTips.init();
  // Create main controller
  myQueueManager = new Jaffa.component.CRUDController ({
    proxy: new Jaffa.data.DWRCRUDProxy({
      query: Jaffa_QM_QueueManager.queueQuery
    })
    ,reader: new Jaffa.data.DWRQueryResponseReader()
    ,criteria: {resultGraphRules: ['**'], objectLimit: 0}
  });
  // Create local function that will build the view port
  buildViewPort = function(title){
    var scrollerMenu = new Ext.ux.TabScrollerMenu({
      maxText  : 20,
      pageSize : 5
    });
    var viewport = new Jaffa.maintenance.Viewport( {
      controller : myQueueManager
      ,header : new Product1.maintenance.HeaderPanel({
        helpPathName : 'jaffa/qm/queuemanager'
        ,headerTitle: title
        ,generateHeaderTitleSuffix: function () {
          return '';
        }        
      })
      ,body: new Jaffa.maintenance.TabPanel({plugins:[scrollerMenu]})
    });
    var contentsPanel = new Ext.form.FormPanel({
      metaClass: 'QueueGraph',
      defaultType: 'textfield',
      title:Labels.get('label.Jaffa.QM.QueueManager.Summary'),
      labelWidth: 120,
      items:[
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.Queues'),
          mapping: 'queues',
          textOnly:true
        },
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.Open'),
          mapping: 'open',
          textOnly:true
        },
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.Error'),
          mapping: 'error',
          textOnly:true
        },
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.InProcess'),
          mapping: 'inprocess',
          textOnly:true
        },
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.Interrupted'),
          mapping: 'interrupted',
          textOnly:true
        },
        {
          fieldLabel: Labels.get('label.Jaffa.QM.QueueManager.Success'),
          mapping: 'success',
          textOnly:true
        }
      ],
      plugins: new Jaffa.maintenance.plugins.PanelLoadSave({
        loadData: function(){
          var data = {
            queues: 0,
            open: 0,
            error: 0,
            inprocess: 0,
            interrupted: 0,
            success: 0
          };
          this.controller.model.each(function(queue){
            data.queues = data.queues + 1;
            if (queue.openCount) data.open = data.open + queue.openCount;
            if (queue.errorCount) data.error = data.error + queue.errorCount;
            if (queue.inProcessCount) data.inprocess = data.inprocess +  queue.inProcessCount;
            if (queue.interruptedCount) data.interrupted = data.interrupted + queue.interruptedCount;
            if (queue.successCount) data.success = data.success + queue.successCount;
          });
          this.controller.setPanelFields(this, data);
        }
      })
    });
    var colorPanel = new Ext.Panel({
      title: Labels.get('label.Jaffa.QM.QueueManager.ColorGuide'),
      plugins: new Jaffa.maintenance.plugins.Panel(),
      html: '<div id="queues-view">' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-ACTIVE-BRIGHT"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueActiveBrightDescription') + '</span>' +
                '</div>' +
              '</div>' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-ACTIVE"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueActiveDescription') + '</span>' +
                '</div>' +
              '</div>' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-ACTIVE-DARK"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueActiveDarkDescription') + '</span>' +
                '</div>' +
              '</div>' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-FAILED-BRIGHT"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueFailedBrightDescription') + '</span>' +
                '</div>' +
              '</div>' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-FAILED"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueFailedDescription') + '</span>' +
                '</div>' +
              '</div>' +
              '<div class="color-wrap">' +
                '<div class="queue-color queue-FAILED-DARK"' +
                  '<span>' + Labels.get('label.Jaffa.QM.QueueManager.QueueFailedDarkDescription') + '</span>' +
                '</div>' +
              '</div>' +
            '</div>'
    });
    viewport.addAccordionPanel(contentsPanel);
    viewport.addAccordionPanel(colorPanel);
    return viewport;
  }
  myQueueManager.load(function() {
    // What to do after the load
    if (myQueueManager.isLoaded && myQueueManager.model) {
      var viewport = buildViewPort(Labels.get('title.Jaffa.QM.QueueManager'));
      var queueManagerDetail = new Jaffa.QM.QueueManagerDetail({controller: myQueueManager});
      viewport.addTabPanel(queueManagerDetail);
      var queueManagerList = new Jaffa.QM.QueueManagerList({controller: myQueueManager});
      viewport.addTabPanel(queueManagerList);
      viewport.tabPanel.activate(queueManagerDetail);
      if(params.transType && params.transType!=''){
          queueManagerDetail.dataview.getStore().each(function(item, index, totalItems ) {
              if(item.get('type')== params.transType){
                  queueManagerDetail.loadTransactionFinder(queueManagerDetail.dataview, index);
                  return;
              }
          });
      }
      if(params.transType && params.transType!=''){
          queueManagerDetail.dataview.getStore().each(function(item, index, totalItems ) {
              if(item.get('type')== params.transType){
                  queueManagerDetail.loadTransactionFinder(queueManagerDetail.dataview, index);
                  return;
              }
          });          
      }
      viewport.doLayout();
    }
    myQueueManager.loadMask = true;
  });
});