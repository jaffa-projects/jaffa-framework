/**
 * @author ChrisO
 */

Jaffa.Messaging.controller = new Jaffa.component.CRUDController({
  proxy: new Jaffa.data.DWRCRUDProxy({
    query: Jaffa_Messaging_BusinessEventLogService.query,
    update: Jaffa_Messaging_BusinessEventLogService.update
  }),
  reader: new Jaffa.data.DWRQueryResponseReader({
    id: 'logId'
  }),
  loadMask: true,
  criteria: {
    objectLimit: 1,
    resultGraphRules: [
      'logId',
      'correlationType',
      'correlationKey1',
      'correlationKey2',
      'correlationKey3',
      'scheduledTaskId',
      'loggedOn',
      'loggedBy',
      'processName',
      'subProcessName',
      'messageType',
      'messageText',
      'sourceClass',
      'sourceMethod',
      'sourceLine',
      'stackTrace'
    ]
  },
  initModel : function(){
    if (!this.model) {
      this.model = new Ext.util.MixedCollection();
    }
    if (this.model.getCount()==0) {
      this.model.add({className : 'BusinessEventLogGraph'});
    }
  }
});


Jaffa.Messaging.loadScreen = function(){

  Jaffa.Messaging.controller.initModel();

  var viewport = new Jaffa.maintenance.Viewport({
    metaClass: 'BusinessEventLogGraph',
    controller: Jaffa.Messaging.controller,
    nav: null, //not needed for this screen
    layout: 'border',
    id: 'viewport',
    style: 'padding 0px; margin: 0px',
    header: new Product1.maintenance.HeaderPanel({
      helpPathName: 'jaffa/messaging/businesseventlogmaintenance',
      dataDictionaryDomain: 'BusinessEventLog',
      headerTitle: Labels.get('title.Jaffa.Messaging.BusinessEvent.Maintenance'), //Business Event Log Maintenance
      generateHeaderTitleSuffix: function() {  //Overriden because we dont want to display the tech-key or "NEW RECORD"
        return '';
      }
    })
  });
  viewport.bodyPanel.hide();

  var addAttachmentsTab = function(){
      var tabConfig = {
        serializedKey: 'org.jaffa.modules.messaging.domain.BusinessEventLog;' + params.logId,
        itemId: 'Jaffa_attachment_Container',
        titleToken: 'label.JaffaRIA.Maintenance.AttachmentsTab',
        closable: false
        /** setting panelConfig height=25% causes conflict with the height setting in the attatchment container.
        Consequently, h=Math.max() in Ext.Panel.syncHeight() is set to NaN. This makes IE throws "Invalid Argument" error.
        Reported in bug 17668.
        ,panelConfig: {
          height: '25%'
        } */
      };
      tabPanel = new Jaffa.attachment.Container(tabConfig);
      viewport.addPanel(tabPanel);
  };

  viewport.addPanel({
    id: 'BusinessEventLogDetailPanel',
    xtype: 'businesseventlogdetailpanel'
  });

  viewport.addPanel({
    id: 'BusinessEventLogStackTracePanel',
    xtype: 'businesseventlogstacktracepanel'
  });

  addAttachmentsTab();

  viewport.bodyPanel.setActiveTab(0);
  viewport.bodyPanel.show();
  viewport.bodyPanel.doLayout();
  Ext.get(document.body).unmask();
};


Ext.onReady(function(){
  Ext.QuickTips.init();
  if (params.logId) {
    Jaffa.Messaging.controller.criteria.logId = {
      values: [params.logId]
    };
    Jaffa.Messaging.controller.load(function(ctrl){
      if (ctrl.model) {
        params._update = true;
        Jaffa.Messaging.loadScreen();
      } else if (params._update) {
        Ext.Msg.show({
           title: Labels.get('title.jaffaRIA.NotFound'),
           msg: Labels.get('label.jaffaRIA.NotFound2.Message') + ' ' + params.logId,
           buttons: Ext.Msg.OK,
           fn: function(btn, text){
              window.close();
           },
           icon: Ext.MessageBox.ERROR
        });
      } else {
        params._update = false;
        Jaffa.Messaging.loadScreen();
      }
    });
  } else {
    // There is no create mode for Business Events
    Ext.Msg.show({
      titleToken: 'label.jaffaRIA.MessageBox.ErrorAlertMsg',
      icon: Ext.Msg.ERROR,
      msg: Labels.get('label.Jaffa.Messaging.BusinessEventMaintenance.MissingLogID'),
      buttons: Ext.Msg.OK,
      fn: function(){
        window.close();
      }
    });
  }
});
