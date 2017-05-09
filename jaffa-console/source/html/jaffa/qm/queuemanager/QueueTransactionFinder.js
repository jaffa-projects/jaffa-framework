/** @class QueueTransactionFinder
 * Main Tab Panel to display transactions in a queue
 * Ext.ModalWindow is defined in js\extjs\ux\form\ModalWindow.js and is not an extjs class
 * textOnly is not a config option of Ext.form.TextField, 
 */

Jaffa.QM.TransactionFinder = function(config){
  var type = config.type;
  var queueSystemId = config.queueSystemId;
  var queueDetails = {};
  
  myQueueManager.model.each(function(queue){
    if (queue.type == type && queue.queueMetaData.queueSystemId == queueSystemId) {
      queueDetails = queue;
      return false;
    }
  })

  this.queueMetaData = queueDetails.queueMetaData;
  var appFields = this.queueMetaData.supportedApplicationFields?this.queueMetaData.supportedApplicationFields:[];
  var transactionFields = [];
  var sm = new Jaffa.grid.HideableCheckboxSelectionModel();
  var columns = [sm, {
      dataIndex: 'status',
      metaClass: 'MessageGraph',
      sortable: true,
      filter: true
    }];
  
  if (appFields.length > 0){
    for (var i = 0; i < appFields.length; i++){
      transactionFields[transactionFields.length] = {name:'appFields.' + appFields[i].name,mapping:'appFields.' + appFields[i].name};
      columns[columns.length] = {
        header: appFields[i].label?appFields[i].label:appFields[i].name,
        dataIndex: 'appFields.' + appFields[i].name,
        width: 200,
        sortable: false
      };
    } 
  }
  transactionFields = transactionFields.concat([
    { name: 'status',
      mapping: 'status',
      type: 'string'
    },{
      name: 'messageId',
      mapping: 'messageId',
      type: 'string'
    },{
      name: 'type',
      mapping: 'type',
      type: 'string'
    },{
      name: 'createdBy',
      mapping: 'createdBy',
      type: 'string'
    },{
      name: 'createdOn',
      mapping: 'createdOn',
      type: 'datetime'
    },{
      name: 'applicationFields',
      mapping: 'applicationFields'
    }
  ]);
  this.TransactionRecord = new Jaffa.data.Record.create(transactionFields);
  Ext.override(this.TransactionRecord, {
    initData: function(data) {
      if (data.applicationFields){
        for (var i = 0; i < data.applicationFields.length; i++) {
          data['appFields.' + data.applicationFields[i].name] = data.applicationFields[i].value;
        }
      }
       
    }
  });
  columns = columns.concat([{
      dataIndex: 'createdBy',
      metaClass: 'MessageGraph',
      sortable: true,
      filter: true
    }, {
      dataIndex: 'createdOn',
      metaClass: 'MessageGraph',
      sortable: true,
      filter: true
  }]);

  var containerConfig = {
    xtype:'findercontainer',
    id: type,
    frame: false,
    border: false,
    closable: true,
    title: queueDetails.type + ' - ' + queueDetails.queueMetaData.queueSystemId,
    store: new Ext.ux.grid.MultiGroupingPagingDWRStore({
      proxy: new Jaffa.data.DWRProxy({
        query: Jaffa_QM_QueueManager.messageQuery
      }),
      reader: new Ext.data.DwrReader({id: 'messageId'}, this.TransactionRecord),
      sortInfo: {field: 'messageId', direction: 'ASC'}
    }),
    layout: 'border', //params.layout.toLowerCase(),
    region: 'center',
    criteria: params,
    criteriaPanel:{
      xtype:'criteriapanel',
      items: [
        {}
      ],
      baseParams: {
        resultGraphRules: ['**']
      }
    },
    resultsPanel:{
      sm: sm,
      xtype:'multigroupingpagingfindergrid',
      disabled: true,
      columns: columns,
      view: new Ext.ux.grid.MultiGroupingView({
         hideGroupedColumn :true,
         displayEmptyFields: true,
         groupTextTpl: "{text} : {group} " + "({values.rs.length}{[values.incomplete?'+':'']} " + "{[values.rs.length > 1 ? Labels.get('label.Jaffa.Common.Records') : Labels.get('label.Jaffa.Common.Record')]}) "
      }),
      plugins: new Ext.ux.plugins.ExportToExcelPlugin({
        serviceClassName:'org.jaffa.qm.apis.QueueManager',
        serviceClassMethodName:'messageQuery',
        criteriaClassName:'org.jaffa.qm.apis.data.MessageCriteria',
        jspPath:'/jaffa/qm/queuemanager/exportToExcel.jsp'
      }),
      listeners: {
        beforeRender:function(grid){
          if (grid.filters){
            grid.filters.getFilterData=function () {
              var filters = [], i, len;
              var applicationFieldValues = [];
              this.filters.each(function (f) {
                if (f.active) {
                  var d = [].concat(f.serialize());
                  for (i = 0, len = d.length; i < len; i++) {
                    if (f.dataIndex.indexOf('appFields.')==0){
                      applicationFieldValues[applicationFieldValues.length]={
                        name:f.dataIndex.substring(10),
                        value:{
                          operator:d[i].comparison,values:[d[i].value]
                        }
                      };
                    }else{
                      filters.push({
                        field: f.dataIndex,
                        data: d[i]
                      });
                    }
                  }
                }
              });
              if (applicationFieldValues.length>0){
                filters.push({
                  field:'applicationFields',
                  data: {
                    value:applicationFieldValues
                  }
                });
              }

              return filters;
            }
          }
        },
        rowdblclick: function(grid, index, e){
          var queue = grid.getStore().getAt(index);
          var tab = Ext.getCmp(queue.get('messageId'));
          if (!tab) {
            var queueTransactionDetail = new Jaffa.QM.QueueTransactionDetail({
              controller: myQueueManager,
              type:queue.get('type'),
              messageId:queue.get('messageId'),
              status:queue.get('status'),
              queueSystemId:this.queueMetaData.queueSystemId,
              queueMetaData:this.queueMetaData,
              initialJson:queue.json
            });
            Ext.getCmp('viewport').addTabPanel(queueTransactionDetail);
          } else{
            tab.ownerCt.activate(tab);
          }
        },
        scope:this
      },
      bbar: [{
        text: Labels.get('label.Jaffa.Widgets.Button.Delete'),
        iconCls: 'tb-delete',
        disabled: !security.hasQueueAdmin || !queueDetails.hasAdminAccess,
        handler: function(){
          var sel = this.ownerCt.ownerCt.getSelectionModel().getSelections();
          var messageList = [];
          if (sel && sel.length > 0){
            Ext.Msg.confirm(
              Labels.get('title.jaffaRIA.Confirm'),
              Labels.get('label.Jaffa.Transaction.TransactionMaintenance.DeletePromptMessage'),
              function(btn, text){
                if (btn == 'yes'){
                  for (var i = 0; i < sel.length; i++){
                    messageList[messageList.length] = {type: sel[i].get('type'), messageId: sel[i].get('messageId'), status: sel[i].get('status'), queueMetaData: {
                      queueSystemId: this.ownerCt.ownerCt.ownerCt.queueSystemId
                    }};
                  }
                  if (messageList.length > 0)
                    Jaffa.QM.QueueManagerHelper.deleteMessage(messageList, function(){
                      //myQueueManager.load();
                      this.ownerCt.ownerCt.getStore().reload();
                    }, this);

                }
              },
              this
            );
          }
          else{
            Ext.MessageBox.show({
              titleToken: 'label.jaffaRIA.MessageBox.AlertErrorMsg',
              msg: Labels.get("label.jaffaRIA.NoRecordSelected"),
              width: 400,
              buttons: Ext.MessageBox.OK
            });
          }
        }
      }, {
        text: Labels.get('label.JaffaRIA.Button.Retry'),
        iconCls: 'tb-retry',
        disabled: !security.hasQueueAdmin || !queueDetails.hasAdminAccess,
        handler: function(){
          var sel = this.ownerCt.ownerCt.getSelectionModel().getSelections();
          var messageList = [];
          if (sel && sel.length > 0){
            for (var i = 0; i < sel.length; i++){
              messageList[messageList.length] = {type: sel[i].get('type'), messageId: sel[i].get('messageId'), status: sel[i].get('status'), queueMetaData: {
                queueSystemId: this.ownerCt.ownerCt.ownerCt.queueSystemId
              }};
            }
            if (messageList.length > 0)
              Jaffa.QM.QueueManagerHelper.resubmitMessage(messageList, function(){
                //myQueueManager.load();
                this.ownerCt.ownerCt.getSelectionModel().clearSelections();
                this.ownerCt.ownerCt.getStore().reload();
              }, this);
          }
          else{
            Ext.MessageBox.show({
              titleToken: 'label.jaffaRIA.MessageBox.AlertErrorMsg',
              msg: Labels.get("label.jaffaRIA.NoRecordSelected"),
              width: 400,
              buttons: Ext.MessageBox.OK
            });
          }
        }
      }]

    }
  };

  if (config.criteriaPanel) {
    Ext.apply(containerConfig.criteriaPanel, config.criteriaPanel);
    delete config.criteriaPanel;
  }

  if (config.resultsPanel) {
    Ext.apply(containerConfig.resultsPanel, config.resultsPanel);
    delete config.resultsPanel;
  }
  Ext.apply(containerConfig, config);

  return containerConfig;
}


