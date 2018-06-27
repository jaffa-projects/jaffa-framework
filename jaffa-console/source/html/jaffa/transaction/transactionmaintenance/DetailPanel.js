Jaffa.Transaction.DetailPanel = {
  xtype: 'form',
  id: 'transactionDetailPanel',
  metaClass: 'TransactionGraph',
  title: Labels.get('label.Jaffa.Transaction.TransactionMaintenance.DetailTab'), //Details
  plugins:[
    {
      ptype: 'panelloadsave'
    },
    {
      init: function(p) {
        p.getTopToolbar().getComponent('deleteBtn').scope = p;
        p.getTopToolbar().getComponent('retryBtn').scope = p;
        Jaffa.Transaction.controller.on('load', function(controller) {
          (new Jaffa.data.DWRProxy({
            query: Jaffa_Transaction_TransactionService.hasAdminAccess
          })).load(controller.model.itemAt(0).type, null, function(response) {
            var delBtn = this.getTopToolbar().getComponent('deleteBtn');
            var retBtn = this.getTopToolbar().getComponent('retryBtn');
            delBtn.setDisabled(!response);
            retBtn.setDisabled(!response);
          },p);
        },p);
      }
    }
  ],
  tbar: [
    {
      iconCls: 'delete',
      itemId: 'deleteBtn',
      disabled: !security.hasTransactionMaintenance,
      text: Labels.get('label.Jaffa.Widgets.Button.Delete'), //Delete
      handler: function(btn) {
        var ctrl = btn.ownerCt.ownerCt.controller;
          Ext.Msg.confirm(
            Labels.get('title.jaffaRIA.Confirm'),
            Labels.get('label.Jaffa.Transaction.TransactionMaintenance.DeletePromptMessage'),
            function(btn, text){
              if (btn == 'yes'){
                 var saveModel = [{
                   id: ctrl.model.keys[0],
                   deleteObject: 'true',
                   isChanged: true
                 }];
                 ctrl.proxy.update(saveModel, function(serverResponse){
                  if (serverResponse && serverResponse[0] && serverResponse[0].errors && serverResponse[0].errors[0]) {
                    Ext.Msg.alert(Labels.get("exception.org.jaffa.persistence.exceptions.DeleteFailedException"), serverResponse[0].errors[0].localizedMessage);
                  } else {
                    Ext.Msg.alert(Labels.get('title.JaffaRIA.Maintenance.DeleteSuccessTitle'),Labels.get('label.Jaffa.Transaction.TransactionMaintenance.DeleteSuccess'));
                    window.close();
                  }
                });
              }
          },
          this
         );
      }
    },
    {
      iconCls: 'arrow_redo',
      text: Labels.get('label.JaffaRIA.Button.Retry'),  //Retry
      itemId: 'retryBtn',
      disabled: !security.hasTransactionMaintenance,
      handler: function(btn){
        var ctrl = btn.ownerCt.ownerCt.controller;
        var idParam = ctrl.model.keys[0];
        if(ctrl.model.itemAt(0) && ctrl.model.itemAt(0).status === 'E'){
          ctrl.proxy.save([{id:idParam, status: 'O'}], function(serverResponse){ 
            if(serverResponse && serverResponse[0].errors){
              Ext.MessageBox.show({
                title: serverResponse[0].errors[0].className,
                msg: Ext.util.Format.htmlEncode(serverResponse[0].errors[0].localizedMessage),
                icon: Ext.MessageBox.ERROR
              });
            } else {
              ctrl.load();
            }
          }, function(errorResponse){
              Ext.MessageBox.show({
                titleToken: 'label.jaffaRIA.MessageBox.alertErrorMsgText',
                msg: Ext.util.Format.htmlEncode(errorResponse),
                icon: Ext.MessageBox.ERROR
              });
          });
        } else {
          Ext.MessageBox.show({
            titleToken: 'label.jaffaRIA.MessageBox.alertErrorMsgText',
            msg: Labels.get('label.Jaffa.Transaction.TransactionMaintenance.RetryInvalidStatusException'),
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK
          });
        }
      }
    }
  ],
  defaults: {
    textOnly: true
  },
  labelWidth: 250,
  items:[
    {
      mapping: 'direction'
    },
    {
      mapping: 'type'
    },
    {
      mapping: 'subType'
    },
    {
      mapping: 'status'
    },
    {
      mapping: 'errorMessage'
    },
    {
      mapping: 'createdOn'
    },
    {
      mapping: 'createdBy'
    },
    {
      mapping: 'lastChangedOn'
    },
    {
      mapping: 'lastChangedBy'
    },{
      xtype: 'fieldset',
      width: 500,
      border: false,
      style: 'padding:0px;',
      mapping: 'transactionFields',
      defaults: {
        xtype: 'textfield',
        textOnly: true
      },
      labelWidth: 250,
      reset: Ext.emptyFn,
      clearInvalid: Ext.emptyFn,
      getValue: Ext.emptyFn,
      setValue: function(val){
        if(val instanceof Ext.util.MixedCollection){
          i = 0;
          val.each(function(field){
            i ++;
            this.add({
              id: 'transactionFields_' + i ,
              fieldLabel: field.fieldName,
              value: field.value
            });
          }, this);
        }
      }
    }
  ]
};