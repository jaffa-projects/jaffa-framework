/**
 * @class SoaEventTestPanel
 * @extends Ext.Panel
 * @author Saravanan N
 * 
 * This SoaEventTestPanel has input fields where
 * user can enter the data and create the test 
 * SoaEvent on the system
 * 
 */
SoaEventTestPanel = Ext.extend(Ext.Panel, {
    closable: true,
    autoScroll:true,
    bodyStyle:'padding:5px 5px 0',
    layout: 'form',            
    initComponent : function(){
        var id = this.id;
        var eventName = this.eventName;
        var description = this.description;
        var soaEventParamsJsonFormat = JSON.stringify(this.soaEventParamsJsonFormat,null,8);
        
        Ext.apply(this,{
            items:[{
                /** This fieldset hold the SoaEvent details **/
                xtype:'fieldset',
                title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEvent'),
                autoHeight:true,
                labelWidth: 300,
                items:[{
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventName') ,
                    id:id+'_soaEventName',
                    value:this.eventName,
                    autoWidth:true
                },{
                    xtype:'displayfield',
                    fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.label'),
                    id:id+'_label',
                    value: this.label,
                    autoWidth:true
                },{
                    xtype:'displayfield',
                    fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.desription'),
                    id:id+'_desription',
                    value:this.description,
                    autoWidth:true
                },{
                    xtype:'textfield',
                    fieldLabelToken:'label.Jaffa.SC.SystemConfigDesktop.Category',
                    id:id+'_category',
                    autoWidth:true
                }]
            }, {
                /** This fieldset hold the SoaEventParams input text/numer fields **/
                xtype:'form',
                id:id+'_soaEventParamPanel',
                frame:true,
                labelWidth: 300,
                items:[{
                    xtype:'fieldset',
                    title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventParams'),
                    autoHeight:true,
                    id:id+'_soaEventParams',
                    items:[Ext.decode(soaEventParamsJsonFormat).data]
                }]
            } ,{
               /** This radio group holds two option to create the soaevents. 
                 * 1) Database entry
                 * 2) Queue Entry
                 */
                xtype: 'radiogroup',
                fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.submitAs'),
                defaults: {
                    xtype: 'radio',
                    id: id+'_submitType'
                },
                items: [
                {
                    boxLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.databaseEntry'),
                    inputValue: 'db'
                },
                {
                    checked: true,                    
                    boxLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.queueEntry'),
                    inputValue: 'q'
                }]
            },{
                xtype       : 'compositefield',
                hideLabel  : true,
                labelWidth : 100,
                items         : [{ 
                    xtype  : 'displayfield',
                    width : 180,
                    value : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.postMessage')
                },{
                    xtype:'numberfield',
                    id:id+'_postMessage',
                    value: '1'
                },{ 
                    xtype  : 'displayfield',
                    width : 200,
                    value : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.times')
                },{
                    xtype  :  'displayfield',
                    width : 350
                },{
                    id: id+'_testbutton',
                    text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.testButtonTxt'),
                    xtype: 'button',
                    width: 70,
                    iconCls: 'icon-tick',
                    handler:function(){
                        
                        var regex = new RegExp(eventName, 'g');
                        var vals = Ext.getCmp(id+'_soaEventParamPanel').getForm().getValues(true).replace(regex,'').split('&');
                     
                        var soaEventParamJson = '{'
                        for (var i = 0; i < vals.length; i++){
                            var keyVal = vals[i].split('=');
                            if(keyVal[1] == 'on'){
                                soaEventParamJson+= keyVal[0]  +": 'true',";    
                            }else{
                                soaEventParamJson+= keyVal[0]  +": '"+ keyVal[1]+"',";    
                            }
                        }
                        soaEventParamJson+= "}";
                    
                        //console.log(soaEventParamJson);
                        
                        var postMessageTimes = Ext.getCmp(id+'_postMessage').getValue();
                        var category = Ext.getCmp(id+'_category').getValue();
                        var submitType = Ext.getCmp(id+'_submitType').getGroupValue();
                        
                        Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Saving'),'x-mask-loading');
                    
                        Ext.Ajax.request({
                            url : 'jaffa/sc/systemconfigdesktop/soaEventTest.jsp',
                            method: 'POST',
                            params :{
                                eventName:eventName, 
                                description:description,
                                soaEventParamJson:soaEventParamJson,
                                postMessageTimes:postMessageTimes,
                                category:category,
                                submitType:submitType
                            },
                            success: function ( result, request ) {
                                var resultMessage;
                                var jsonData = Ext.util.JSON.decode(result.responseText);
                                if(jsonData.success){
                                    resultMessage = JSON.stringify(jsonData.data.result,null,8);
                                    Ext.getCmp(id+"_output").setValue(resultMessage.replace(new RegExp('"', 'g'),''));
                                }else{
                                    resultMessage = jsonData.data.result;
                                    Ext.getCmp(id+"_output").getEl().setStyle('background', '#FF9999');
                                    Ext.getCmp(id+"_output").setValue(resultMessage);
                                }
                                Ext.getCmp(id).getEl().unmask();
                            },
                            failure: function ( result, request ) {
                                fnErrorMessage(result.responseText, 'Error');
                                Ext.getCmp(id).getEl().unmask();
                            }
                        });
                    }
                }]
            },{
                xtype:'fieldset',
                title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.response'),
                autoHeight:true,
                id:id+"_response",
                items:[{
                    xtype  : 'displayfield',    
                    fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.result'),
                    id: id+'_output',
                    autoWidth : true
                }]
            }]
            
        });
        
        SoaEventTestPanel.superclass.initComponent.call(this);
    }

});

/**
 *  @param {string} message
 *  @param {string} title
 *  
 *  This method will show the error message returned from
 *  server call and show in the popup message box.
 */
function fnErrorMessage( message, title ){
    Ext.Msg.show({
        title: title,
        msg: message ,
        buttons: Ext.MessageBox.OK,
        icon: Ext.MessageBox.INFO
    });
}