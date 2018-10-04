/**
 * @class SoaEventSummaryPanel
 * @extends Ext.Panel
 * @author Saravanan N
 * 
 * This SoaEventSummaryPanel has various functionality
 * to check topic administration,ruleset view and status 
 * of poller and events.
 * 
 */
SoaEventSummaryPanel = Ext.extend(Ext.Panel, {
    closable: true,
    autoScroll:true,
    bodyStyle:'padding:5px 5px 0',
    layout:'form',
    initComponent : function(){
        var id = this.id;
        var subscriberList = eval(this.subscriberList);
        var drlFilePath = eval(this.drlFilePath);
        var schedulerPollerAndEventConfig = Ext.util.JSON.decode(this.schedulerPollerAndEventConfig);
        var durableSubGrid = this.loadDurableSubDetails(this.id,subscriberList);  
        var ruleGrid = this.loadRuleSet(this.id,drlFilePath);  
        Ext.apply(this,{
            items:[{ 
                xtype:'displayfield',
                fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.localId'),
                id:id+'_localID',
                value:schedulerPollerAndEventConfig.localId,
                autoWidth:true,
                hidden: schedulerPollerAndEventConfig.localId ==''? true:false,
                hideLabel: schedulerPollerAndEventConfig.localId ==''? true:false
            },{
                xtype:'fieldset',
                title: schedulerPollerAndEventConfig.dbEventCount+" "+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.eventsInDatabase'),
                autoHeight:true,
                id:id+"_dbEvents",
                labelWidth: 310,
                items:[{ 
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.pollerConfig'),
                    id:id+'_pollerConfig',
                    html: security.hasSchedulerAccess? getSchedulerLink(schedulerPollerAndEventConfig.pollerConfig): schedulerPollerAndEventConfig.pollerConfig,
                    autoWidth:true
                },{ 
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.schedulderRunning'),
                    id:id+'_schedulderRunning',
                    html: getFontStyle(schedulerPollerAndEventConfig.schedulderStatus),
                    autoWidth:true
                },{ 
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.transactionQueuePollerRunning',security.hasQueueInquiry?getQMHref('Poller'):'Poller'),
                    id:id+'_transactionQueuePollerStatus',
                    html: getFontStyle(schedulerPollerAndEventConfig.transactionQueuePollerStatus),
                    autoWidth:true
                }]
            },{
                xtype:'fieldset',
                title: schedulerPollerAndEventConfig.transactionEventCount+" "+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.eventTransactions'),
                autoHeight:true,
                id:id+"_transactionEvents",
                labelWidth: 310,
                items:[{ 
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.transactionQueuePollerRunning',security.hasQueueInquiry?getQMHref('Event'):'Event') ,
                    id:id+'_transactionQueueEventStatus',
                    html: getFontStyle(schedulerPollerAndEventConfig.transactionQueueEventStatus),
                    autoWidth:true
                }
                /*Removing this, since the JaffaHighVelocityQueue no longer available
                ,{ 
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.transactionQueuePollerRunning',security.hasQueueInquiry?getQMHref('JaffaHighVelocityQueue'):'JaffaHighVelocityQueue') ,
                    id:id+'_jmsQueueStatus',
                    html: getFontStyle(schedulerPollerAndEventConfig.jaffaHighVelocityQueueStatus),
                    autoWidth:true
                }*/]
            },{
                xtype:'fieldset',
                title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.durableSubscribers'),
                autoHeight:true,
                id:id+"_durableSubscribers",
                items:[{
                    xtype:'combo',
                    fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.selectTopicName'),
                    triggerAction: 'all',
                    name:'topicName',
                    valueField: 'topicName',
                    mode: 'local',
                    displayField:'displayText',
                    value:'OutboundEvents',
                    store: new Ext.data.ArrayStore({
                       fields: [
                          'topicName',
                          'displayText'
                       ],
                       data: [['OutboundEvents', 'Outbound Events'], ['PublicEvents', 'Public API Events']]
                    }),
                    listeners: {
                        scope: this,
                        select: function (combo, record) {
                           if(record.data.topicName === params.topicName){
                              return;
                           }
                           Ext.getCmp('topicHeader').setValue(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.'+record.data.topicName+'.durableSubscribersDescription'));
                           params.topicName=record.data.topicName;
                           Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Loading'),'x-mask-loading');
                           Ext.Ajax.request({
                                url: 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                                method: 'POST',
                                params: {
                                  topicName: record.data.topicName
                                },
                                success: function (result, request) {
                                   var resultMessage;
                                   var jsonData = Ext.util.JSON.decode(result.responseText);
                                   if (jsonData.success) {
                                       resultMessage = JSON.stringify(jsonData.data, null, 8);
                                       var durableSubStore = Ext.getCmp(id + "_durableSubDetailsGrid").getStore();
                                       durableSubStore.loadData(eval(resultMessage));
                                   } else {
                                       resultMessage = jsonData.data.result;
                                       fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                   }
                                   Ext.getCmp(id).getEl().unmask();
                                },
                                failure: function (result, request) {
                                    var jsonData = Ext.util.JSON.decode(result.responseText);
                                    var resultMessage = jsonData.data.result;
                                    fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                    Ext.getCmp(id).getEl().unmask();
                                }
                           });
                        }
                    }
                },{
                    xtype : 'displayfield',
                    id:'topicHeader',
                    value : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.OutboundEvents.durableSubscribersDescription')
                },durableSubGrid
                ]
            },ruleGrid]
        });
        
        Ext.apply(this,{
            tbar:[{
                xtype:'button',
                text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.refresh') ,
                iconCls: 'x-tbar-loading',
                id:id+'_refresh',
                ref: 'refreshBtn',
                handler:function(btn,e){

                    Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Loading'),'x-mask-loading');

                    Ext.Ajax.request({
                        url : 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                        method: 'POST',
                        params :{
                            topicName:params.topicName
                        },
                        success: function ( result, request ) {
                            var resultMessage;
                            var jsonData = Ext.util.JSON.decode(result.responseText);
                            if(jsonData.success){
                                resultMessage = JSON.stringify(jsonData.data,null,8);
                                var resultMessage1 = JSON.stringify(jsonData.schedulerPollerAndEventConfig.drlFilePath,null,8);
                                var schedulerPollerAndEventConfig = Ext.util.JSON.decode(JSON.stringify(jsonData.schedulerPollerAndEventConfig,null,8));
		
                                var durableSubStore = Ext.getCmp(id+"_durableSubDetailsGrid").getStore();
                                durableSubStore.loadData(eval(resultMessage));
                                var ruleSetStore = Ext.getCmp(id+"_ruleSetGrid").getStore();
                                ruleSetStore .loadData(eval(resultMessage1));
                                Ext.getCmp(id+"_dbEvents").setTitle(schedulerPollerAndEventConfig.dbEventCount+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.eventsInDatabase'));
                                Ext.getCmp(id+"_transactionEvents").setTitle(schedulerPollerAndEventConfig.transactionEventCount+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.eventTransactions'));
		
                                Ext.getCmp(id+'_pollerConfig').update(getSchedulerLink(schedulerPollerAndEventConfig.pollerConfig));
                                Ext.getCmp(id+'_schedulderRunning').update(getFontStyle(schedulerPollerAndEventConfig.schedulderStatus));
                                Ext.getCmp(id+'_transactionQueuePollerStatus').update(getFontStyle(schedulerPollerAndEventConfig.transactionQueuePollerStatus));
                                Ext.getCmp(id+'_transactionQueueEventStatus').update(getFontStyle(schedulerPollerAndEventConfig.transactionQueueEventStatus));
                                ////Removing this, since the JaffaHighVelocityQueue no longer available
                                //Ext.getCmp(id+'_jmsQueueStatus').update(getFontStyle(schedulerPollerAndEventConfig.jaffaHighVelocityQueueStatus));
		
                            }else{
                                resultMessage = jsonData.data.result;
                                fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                            }
	    
                            Ext.getCmp(id).getEl().unmask();
                        },
                        failure: function ( result, request ) {
                            var jsonData = Ext.util.JSON.decode(result.responseText);
                            var resultMessage = jsonData.data.result;
                            fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                            Ext.getCmp(id).getEl().unmask();
                        }
                    });  
                }
            }]
        }); 
        
            
        SoaEventSummaryPanel.superclass.initComponent.call(this);
    },
    refresh: function(evt) {
      this.getTopToolbar().refreshBtn.onClick(evt);
    },

    loadDurableSubDetails :function(id,durableSubDetailsJson) {
        
        var durableSubReader = new Ext.data.JsonReader({
            
            },Ext.data.Record.create([{
                name: 'clientID'
            },{
                name: 'subscriberName'
            },{
                name: 'messageSelector'
            },{
                name: 'pendingMessageSize'
            },{
                name: 'status'
            }])
            );

        var store = new Ext.data.Store({
            reader: durableSubReader
        });
        store.loadData(durableSubDetailsJson);

        var durableSubGrid = new Ext.grid.GridPanel({
            id:id+"_durableSubDetailsGrid",
            labelAlign: 'top',
            store: store,
            tbar:[{
                id: id+'_subscribe',
                text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.subscribe'),
                xtype: 'button',
                iconCls:'icon-sub',
                autoWidth : true,
                handler:function(){
                    subWindow  = new Ext.Window(
                    {
                        id: 'subscribe',
                        closeAction: 'close',
                        iconCls:'icon-sub',
                        title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.subscribe'),
                        width: 500,
                        items: new Ext.Panel({
                            id:id+'_subscriberPanel',
                            layout: 'form',            
                            frame:true,
                            bodyStyle:'padding:5px 5px 0',
                            items : [{ 
                                xtype:'textfield',
                                fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.clientID') ,
                                id:id+'_clientId',
                                allowBlank: false,
                                width:200
                            },{ 
                                xtype:'textfield',
                                fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.subscriptionName') ,
                                id:id+'_subName',
                                allowBlank: false,
                                width:200
                            },{ 
                                xtype:'textfield',
                                fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.messageSelector') ,
                                id:id+'_msgSelector',
                                width:200
                            },{
                                id: id+'_subbutton',
                                text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.subscribe'),
                                xtype: 'button',
                                autiWidth: true,
                                handler:function(btn,e){

                                    if(Ext.getCmp(id+'_clientId').getValue() === '' || !Ext.getCmp(id+'_clientId').getValue()){
                                        alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.ClientId.alert'));
                                        return;
                                    }
                                    
                                    if(Ext.getCmp(id+'_subName').getValue() === '' || !Ext.getCmp(id+'_subName').getValue()){
                                        alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Subscription.alert'));
                                        return;
                                    }
                                    
                                    Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Subscribing'),'x-mask-loading');
                            
                                    Ext.Ajax.request({
                                        url : 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                                        method: 'POST',
                                
                                        params :{
                                            topicName : params.topicName,
                                            subscriptionName: Ext.getCmp(id+'_subName').getValue(),
                                            clientID:Ext.getCmp(id+'_clientId').getValue(),
                                            messageSelector:Ext.getCmp(id+'_msgSelector').getValue(),
                                            action: 'subscribe'
                                        },
                                
                                        success: function ( result, request ) {
                                            var resultMessage;
                                            var jsonData = Ext.util.JSON.decode(result.responseText);
                                            if(jsonData.success){
                                                resultMessage = JSON.stringify(jsonData.data,null,8);
                                                var store = Ext.getCmp(id+"_durableSubDetailsGrid").getStore();
                                                store.loadData(eval(resultMessage));
                                            }else{
                                                resultMessage = jsonData.data.result;
                                                fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                            }
                                            Ext.getCmp(id).getEl().unmask();
                                        },
                                        failure: function ( result, request ) {
                                            var jsonData = Ext.util.JSON.decode(result.responseText);
                                            var resultMessage = jsonData.data.result;
                                            fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                            Ext.getCmp(id).getEl().unmask();
                                        }
                                    });  
                                    subWindow.close();
                                }
                            }]
                        })
                    });
                    subWindow.show();
                }
            },'-',{
                id: id+'_unsubscribe',
                text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.unsubscribe'),
                xtype: 'button',
                iconCls:'icon-unsub',
                autoWidth : true,
                handler:function(){
                        
                    var durableSubGrid = Ext.ComponentMgr.get(id+"_durableSubDetailsGrid");
                    var selection = durableSubGrid.getSelectionModel().getSelections();
                            
                    var clientID ='';
                    var subscriberName ='';
                    var rowIndex = 0;
                    for(var i = 0; i < selection.length ; i++){
                        var gridrecord = durableSubGrid.getSelectionModel().getSelected();
                        clientID = selection[i].get('clientID');
                        subscriberName = selection[i].get('subscriberName');
                        rowIndex = durableSubGrid.getStore().indexOf(gridrecord);
                    }
                            
                    if(selection.length==0){
                        fnMessage(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Unsubscribe.alert'), Labels.get('label.jaffaRIA.MessageBox.AlertInfoMsg'));
                        return;
                    }
                    
                    Ext.Msg.confirm(Labels.get('label.Jaffa.SC.SystemConfigDesktop.ConfirmActionAlertMsg'), Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Unsubscribe.confirm'), function(btn) {
                        if(btn == 'no'){
                            return;
                        }else{
                            Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Unsubscribing'),'x-mask-loading');
                            
                            Ext.Ajax.request({
                                url : 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                                method: 'POST',
                                
                                params :{
                                    topicName : params.topicName,
                                    subscriptionName: subscriberName,
                                    clientID: clientID,
                                    action: 'unsubscribe'
                                },
                                
                                success: function ( result, request ) {
                                    var resultMessage;
                                    var jsonData = Ext.util.JSON.decode(result.responseText);
                                    if(jsonData.success){
                                        resultMessage = JSON.stringify(jsonData.data.result,null,8);
                                        var store = Ext.getCmp(id+"_durableSubDetailsGrid").getStore();
                                        store.removeAt(rowIndex);
                                        fnMessage(resultMessage, 'Info');
                                    }else{
                                        resultMessage = jsonData.data.result;
                                        fnMessage(resultMessage, 'Error');
                                    }
                                    Ext.getCmp(id).getEl().unmask();
                                },
                                failure: function ( result, request ) {
                                    var jsonData = Ext.util.JSON.decode(result.responseText);
                                    var resultMessage = jsonData.data.result;
                                    fnMessage(resultMessage, 'Error');
                                    Ext.getCmp(id).getEl().unmask();
                                }
                            });  
                        }
                    });

                }
            } ,'->','-',{
                id: id+'_consume',
                text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.consume'),
                xtype: 'button',
                iconCls:'icon-consume',
                autoWidth : true,
                disabled : Rules.get("com.systemconfigdesktop.eventSummary") === 'false' ? true : false,
                handler:function(){
                            
                    
                    var durableSubGrid = Ext.ComponentMgr.get(id+"_durableSubDetailsGrid");
                    var selection = durableSubGrid.getSelectionModel().getSelections();
                            
                    var clientID ='';
                    var subscriberName ='';
                    var pendingMessageSize ='';
                    var messageSelector ='';
                    for(var i = 0; i < selection.length ; i++){
                        clientID = selection[i].get('clientID');
                        subscriberName = selection[i].get('subscriberName');
                        pendingMessageSize = selection[i].get('pendingMessageSize');
                        messageSelector = selection[i].get('messageSelector');
                    }
                            
                    if(selection.length===0){
                        fnMessage(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Consume.alert'), Labels.get('label.jaffaRIA.MessageBox.AlertInfoMsg'));
                        return;
                    }
                            
                    Ext.Msg.confirm(Labels.get('label.Jaffa.SC.SystemConfigDesktop.ConfirmActionAlertMsg'), Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Consume.confirm'), function(btn) {
                        if(btn == 'no'){
                            return;
                        }else{
                            Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Consuming'),'x-mask-loading');
                            
                            Ext.Ajax.request({
                                url : 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                                method: 'POST',
                                
                                params :{
                                    topicName : params.topicName,
                                    subscriptionName: subscriberName,
                                    clientID: clientID,
                                    pendingMessageSize: pendingMessageSize,
                                    messageSelector:messageSelector,
                                    action: 'consume',
                                    numberOfMsgs: Ext.getCmp(id+'_numberOfMessages').getValue()
                                },
                                
                                success: function ( result, request ) {
                                    var resultMessage;
                                    var jsonData = Ext.util.JSON.decode(result.responseText);
                                    if(jsonData.success){
                                        resultMessage = JSON.stringify(jsonData.data,null,8);
                                        var store = Ext.getCmp(id+"_durableSubDetailsGrid").getStore();
                                        store.loadData(eval(resultMessage));
                                        fnMessage(Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.Consume.success'), Labels.get('label.jaffaRIA.MessageBox.AlertInfoMsg'));
                                        var url = params.appCtx+"/jaffa/sc/systemconfigdesktop/showMessages.jsp";
                                        window.open(url, 'popup_name','height=' + screen.height + ',width=' + screen.width + ',resizable=yes,scrollbars=yes,location=yes');
                                    }else{
                                        resultMessage = jsonData.data.result;
                                        fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                    }
                                    Ext.getCmp(id).getEl().unmask();
                                },
                                failure: function ( result, request ) {
                                    var jsonData = Ext.util.JSON.decode(result.responseText);
                                    var resultMessage = jsonData.data.result;
                                    fnMessage(resultMessage, Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'));
                                    Ext.getCmp(id).getEl().unmask();
                                }
                            });                            
                        }
                    });

                }
            },'-',{
                xtype:'numberfield',
                id:id+'_numberOfMessages',
                width:50,
                value: '10'
            },{
                xtype : 'displayfield',
                value : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.messages')
            }],
                
            columns: [
            {
                dataIndex:'clientID',     
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.clientID'),
                autowidth: true,  
                sortable: true, 
                id:'clientID'
            },
            {
                dataIndex:'subscriberName',         
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.subscriberName'),
                autowidth: true,  
                sortable: true
            },
            {
                dataIndex:'messageSelector',      
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.messageSelector'),
                autowidth: true,  
                sortable: true
            },
            {
                dataIndex:'pendingMessageSize',      
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.DurableSubDetailsGrid.pendingMessageSize'),
                autowidth: true,  
                sortable: true
            },
            {
                dataIndex:'status',      
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.Status'),
                autowidth: true,  
                sortable: true
            }
            ],
            stripeRows: true,
            autoHeight: true,
            viewConfig: {
                forceFit: true
            },            
            sm: new Ext.grid.RowSelectionModel({
                singleSelect:true
            })
        });
        
        return durableSubGrid;
    },
    loadRuleSet :function(id,drlFilePaths) {
        
        var myReader = new Ext.data.JsonReader({
            
            },Ext.data.Record.create([{
                name: 'path'
            }])
            );

        var store = new Ext.data.Store({
            reader: myReader
        });
        
        store.loadData(drlFilePaths);

        var rgrid = new Ext.grid.GridPanel({
            id:id+"_ruleSetGrid",
            labelAlign: 'top',
            store: store,
            columns: [{
                header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEventSummay.RuleSetGrid.ruleSets'),
                sortable: true, 
                xtype: 'templatecolumn', 
                tpl: '<a href="{params.appCtx}/jaffa/sc/systemconfigdesktop/showRuleSet.jsp?path={path}" target="_blank">{path}</a>',
                dataIndex: 'path',
                id:'path'
            }],
            stripeRows: true,
            autoHeight: true,
            viewConfig: {
                forceFit: true
            }
        });
        
        return rgrid;
    }    
});


    
/**
*  @param {string} message
*  @param {string} title
*  
*  This method will show the error message returned from
*  server call and show in the popup message box.
*/
function fnMessage( message, title ){
    
    Ext.Msg.show({
        title: title,
        msg: message.indexOf('label.')!=-1? Labels.get(message):message ,
        buttons: Ext.MessageBox.OK,
        icon: title == Labels.get('label.jaffaRIA.MessageBox.AlertInfoMsg')? Ext.MessageBox.INFO : Ext.MessageBox.ERROR
    });        
   
}

function getQMHref(transType){

    return '<a href="'+params.appCtx+'/startComponent.do?component=Jaffa.QM.QueueManager&transType='+transType+'&finalUrl=jaffa_closeBrowser" target="_blank" style="text-decoration : underline;">'+transType+'</a>';
    
}

function getFontStyle(value){
    if(value=='Yes'){
        return '<div align="center" style="width:45px; background-color:green;color:white"><b>'+value+'</b></div>';
    }else{
        return '<div align="center" style="width:45px; background-color:red;color:white"><b>'+value+'</b></div>';
    }
}

function getSchedulerLink(value){
    var schedulerLink = '';
    if( value == 'Not Configured'){
        schedulerLink = '<a href="'+params.appCtx+'/startComponent.do?component=Jaffa.Scheduler.TaskMaintenance&taskType=SOAEventPoller&finalUrl=jaffa_closeBrowser" target="_blank" style="text-decoration : underline;">'+value+'</a>';
    }else{
        schedulerLink = '<a href="'+params.appCtx+'/startComponent.do?component=Jaffa.Scheduler.TaskFinder&finalUrl=jaffa_closeBrowser" target="_blank" style="text-decoration : underline;">'+value+'</a>';
    }
    
    return schedulerLink;
}