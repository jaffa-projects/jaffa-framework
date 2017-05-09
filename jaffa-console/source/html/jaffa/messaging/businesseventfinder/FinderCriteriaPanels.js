
Jaffa.Messaging.CriteriaGroups = [];

Jaffa.Messaging.CriteriaGroups['general'] = {
  xtype: 'fieldset',
  title: Labels.get('label.Jaffa.Messaging.BusinessEventFinder.GeneralTab'), //General
  layout: 'form',
  collapsible: true,
  autoHeight: true,
  autoWidth: true,
  bodyStyle: 'background-color:#DFE8F6',
  metaClass: 'BusinessEventLogCriteria',
  defaults:{
    metaClass: 'BusinessEventLogCriteria',
    labelWidth: 150
  },
  labelWidth: 150,
  items: [
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'correlationType'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'correlationKey1'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'correlationKey2'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'correlationKey3'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
        metaClass: 'BusinessEventLogCriteria',
        mapping: 'messageId'
      }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      xtype: 'datefield',
      mapping: 'loggedOn'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'loggedBy'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'processName'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'subProcessName'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'messageType'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'messageText'
    }),
    Jaffa.finder.CriteriaPanelFactory.createPanel({
      metaClass: 'BusinessEventLogCriteria',
      mapping: 'sourceClass'
    })
  ]
};