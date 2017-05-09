Jaffa.Transaction.PayloadPanel = {
  xtype: 'panel',
  title: Labels.get('label.Jaffa.Transaction.TransactionMaintenance.PayloadTab'), //Internal Payload
  plugins:[{ptype: 'panelloadsave'}],
  metaClass: 'TransactionPayloadGraph',
  
  items:[
    {
      xtype: 'form',
      style: 'background-color: transparent',
      border: false,
      labelWidth: 150,
      items: [
        {
          mapping: 'transactionPayload.internalMessageClass',
          textOnly: true
        }
      ]
    },
    {
      xtype: 'textarea',
      width: '100%',
      height: '90%',
      autoScroll: true,
      style: 'font-family:Courier New;padding:8px',
      readOnly: true,  // IE doens't like textOnly, textOnly causes a runtime error "Invalid Argument"
      mapping: 'transactionPayload.internalMessage',
      setValue: function(val){
        var convertedVal = eval("String.fromCharCode(" + val + ")"); //The stored value is UTF-8 byte code
        Ext.form.TextArea.prototype.setValue.call(this,convertedVal);
      }
    }
  ]
};