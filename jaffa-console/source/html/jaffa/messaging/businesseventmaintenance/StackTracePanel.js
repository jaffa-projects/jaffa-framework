//@author ChrisO


Jaffa.Messaging.StackTracePanel = Ext.extend(Ext.Panel, {
  title: Labels.get('label.Jaffa.Messaging.BusinessEventLog.StackTrace'), // Stack Trace
  border: false,
  frame: true,
  metaClass: 'BusinessEventLogGraph',
  controller: Jaffa.Messaging.controller,
  initComponent: function(){
    Ext.apply(this, {
      defaults: {
        xtype: 'textfield',
        metaClass: 'BusinessEventLogGraph'
      },
      items: [{
        xtype: 'textarea',
        width: '100%',
        height: '95%',
        mapping: 'stackTrace',
        id: 'stackTrace',
        ref: 'stackTrace',
        autoScroll: true,
        style: 'font-family:Courier New;',
        readOnly: true  // IE doens't like textOnly, textOnly causes a runtime error "Invalid Argument"
      }],
      plugins: [{ptype: 'panelloadsave'}],
      tbar: false
    });
    Jaffa.Messaging.StackTracePanel.superclass.initComponent.call(this);
  }
});

Ext.reg('businesseventlogstacktracepanel', Jaffa.Messaging.StackTracePanel);