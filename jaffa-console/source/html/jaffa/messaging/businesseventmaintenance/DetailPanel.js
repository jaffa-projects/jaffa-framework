
Jaffa.Messaging.BusinessEventDetailPanel = Ext.extend(Ext.Panel, {
  title: Labels.get('label.Jaffa.Messaging.BusinessEvent.Maintenance.DetailsTab'), // Details
  layout: 'form',
  xtype: 'form',
  border: false,
  frame: true,
  metaClass: 'BusinessEventGraph',
  controller: Jaffa.Messaging.controller,
  constructor: function(config){
    Jaffa.Messaging.BusinessEventDetailPanel.superclass.constructor.call(this, config);
  },
  initComponent: function(){
    Ext.apply(this, {
      defaults: {
        xtype: 'textfield',
        metaClass: 'BusinessEventLogGraph',
        textOnly: true
      },
      items: [
        {
          mapping: 'logId',
          id: 'logId',
          ref: 'logId'
        },
        {
          mapping: 'correlationType'
        },
        {
          mapping: 'correlationKey1'
        },
        {
          mapping: 'correlationKey2'
        },
        {
          mapping: 'correlationKey3'
        },
        {
          mapping: 'scheduledTaskId'
        },
        {
          mapping: 'loggedOn'
        },
        {
          mapping: 'loggedBy'
        },
        {
          mapping: 'processName'
        },
        {
          mapping: 'subProcessName'
        },
        {
          mapping: 'messageType'
        },
        {
          mapping: 'messageText'
        },
        {
          mapping: 'sourceClass'
        },
        {
          mapping: 'sourceMethod'
        },
        {
          mapping: 'sourceLine'
        }
      ],
      plugins: new Jaffa.maintenance.plugins.PanelLoadSave(),
      tbar: false
    });
    Jaffa.Messaging.BusinessEventDetailPanel.superclass.initComponent.call(this);
  }
});

Ext.reg('businesseventlogdetailpanel', Jaffa.Messaging.BusinessEventDetailPanel);
