/**
 * @author BobbyC
 */
AuditTransactionFastAccess = function(){
  this.transactionIdField = new Ext.form.TextField({
    id: 'transactionIdField',
    fieldLabel: Labels.get('label.Commons.FastAccess')+' - ' + Labels.get('label.Jaffa.Audit.AuditTransaction.TransactionId')
  });
    
  config = {
    region: "south",
    bodyStyle: 'display:none', // Hack for IE
    collapsible: true,
    header: false,
    layout: 'form',
    keys:({key: Ext.EventObject.ENTER,
      fn: function(){
        window.open(params.appCtx + '/jaffa/audit/audittransactionviewer/main.jsp?transactionId=' + encodeURIComponent(this.transactionIdField.getValue()));
      },
      scope: this}), 
    tbar: [this.transactionIdField,'-',{
      id: 'updateButton',
      text: Labels.get('label.Jaffa.Widgets.Button.View'),
      scope: this,
      handler: this.buttonAction
    }]
  };
  AuditTransactionFastAccess.superclass.constructor.call(this, config);
};

Ext.extend(AuditTransactionFastAccess, Ext.Panel, {
  render: function() {
    AuditTransactionFastAccess.superclass.render.apply(this, arguments);
    Ext.DomHelper.insertBefore(this.transactionIdField.el, "<label class='x-form-item-label'>"+this.transactionIdField.fieldLabel+":&nbsp;</label>");
  }
  
  ,buttonAction : function(btn){
    var btnId = null;
    if (btn && btn.id) {
      btnId = btn.id;
    } else if (btn && typeof btn == 'string'){
      btnId = btn;
    }
    var transactionId = this.transactionIdField.getValue();
    if (transactionId || btnId == 'createButton' || btnId == 'importButton') {
      if (btnId == 'createButton') {
        var wOpen = params.appCtx+'/jaffa/audit/audittransactionviewer/main.jsp';
        window.open(wOpen);
      } else {
        window.open(params.appCtx+'/jaffa/audit/audittransactionviewer/main.jsp?transactionId=' + encodeURIComponent(transactionId));
      }
    }else {
      Ext.MessageBox.alert(Labels.get('title.Jaffa.Alert.IllegalAction'), 
        Labels.get('label.Jaffa.Audit.AuditTransactionViewer.TransactionIdRequiredMsg'));
    } 
  }
});

