/**
 * @author ChrisO
 */
Jaffa.Messaging.FastAccess = function(){
  this.keyField = new Ext.form.TextField({
    id: 'keyField',
    hidden: true,
    fieldLabel: Labels.get('label.Commons.FastAccess')+' - ' + Labels.get('label.Jaffa.Messaging.BusinessEventLog.LogId'), //Log Id
    name: "logId"
  });
    
  config = {
    region: "south",
    bodyStyle: 'display:none', // Hack for IE
    collapsible: true,
    header: false,
    layout: 'form',
    keys:({key: Ext.EventObject.ENTER,
      fn: function(){
        window.open(params.appCtx + '/jaffa/messaging/businesseventmaintenance/main.jsp?logId=' + encodeURIComponent(this.keyField.getValue()));
      },
      scope: this
    }), 
    tbar: [
        this.keyField,
        {
          id: 'updateButton',
          disabled: true,
          text: Labels.get("label.Jaffa.Messaging.BusinessEventFinder.Button.View"), //View
          scope: this,
          handler: this.buttonAction
        }
    ]
  };
  Jaffa.Messaging.FastAccess.superclass.constructor.call(this, config);
};

Ext.extend(Jaffa.Messaging.FastAccess, Ext.Panel, {
	id: 'businessEventFastAccess',
  render: function() {
    Jaffa.Messaging.FastAccess.superclass.render.apply(this, arguments);
    Ext.DomHelper.insertBefore(this.keyField.el, "<label class='x-form-item-label'>"+this.keyField.fieldLabel+":&nbsp;</label>");
  }, 
  buttonAction : function(btn){
    var btnId = null;
    if (btn && btn.id) {
      btnId = btn.id;
    } else if (btn && typeof btn == 'string'){
      btnId = btn;
    }
    var key = this.keyField.getValue();
    switch(btnId){
      default:
        window.open(params.appCtx + '/jaffa/messaging/businesseventmaintenance/main.jsp?logId=' + encodeURIComponent(this.keyField.getValue()));
        break;
    }
  }
});
