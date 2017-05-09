/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/**
 * @author PaulE
 */
 
/** Creates a sum function for an array of objects
 * @param field this is the name of the field in each object occurence that should be totalled
 */
Array.prototype.sum = function(field){
  if(field)
    for(var i=0,sum=0;i<this.length;sum+=parseFloat(this[i++].data[field]));
  else
    for(var i=0,sum=0;i<this.length;sum+=this[i++]);
  return sum;
} 
 
var todayString,yesterdayString;
 
Ext.onReady(function(){
  Ext.QuickTips.init();    
  
  Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

  var WorkOrderRecord = Ext.data.Record.create([
     {name: 'workOrderNo'}
    ,{name: 'contract'}
    ,{name: 'segregationCode'}
    ,{name: 'topWorkOrderNo'}
    ,{name: 'nhWorkOrderNo'}
    ,{name: 'workOrderLevel'}
    ,{name: 'part'}
    ,{name: 'serial'}
    ,{name: 'span'}
    ,{name: 'ecd', type:'date'}
    ,{name: 'laborHours'}
    ,{name: 'materialCosts'}
  ]);
    
  var columnModel = new Ext.grid.ColumnModel([{
//      header:'x',width:12,hidden:false,fixed:true,dataIndex:''
//    },{
      header: 'Contract No'
      ,width: 100
      ,dataIndex: 'contract'
      ,sortable: true
    }, {
      header: 'Segregation Code'
      ,width: 100
      ,dataIndex: 'segregationCode'
      ,sortable: true
    }, {
      header: 'Top Work Order'
      ,width: 100
      ,dataIndex: 'topWorkOrderNo'
      ,sortable: true
    }, {
      header: 'Parent Work Order'
      ,width: 100
      ,dataIndex: 'nhWorkOrderNo'
      ,sortable: true
    }, {
      header: 'Level'
      ,width: 100
      ,dataIndex: 'workOrderLevel'
      ,sortable: true
    }, {
      header: 'Work Order'
      ,width: 100
      ,dataIndex: 'workOrderNo'
      ,sortable: true
    }, {
      header: 'Part'
      ,width: 100
      ,dataIndex: 'part'
      ,sortable: true
    }, {
      header: 'Serial'
      ,width: 100
      ,dataIndex: 'serial'
      ,sortable: true
    }, {
      header: 'Span'
      ,width: 100
      ,dataIndex: 'span'
      ,align: 'right'
      ,sortable: true
    }, {
      header: 'ECD'
      ,width: 100
      ,dataIndex: 'ecd'
      ,renderer: Ext.util.Format.dateRenderer()
      ,sortable: true
    }, {
      header: 'Labor Hours'
      ,width: 100
      ,dataIndex: 'laborHours'
      ,align: 'right'
    }, {
      header: 'Material Costs'
      ,width: 100
      ,dataIndex: 'materialCosts'
      ,align: 'right'
    }
  ]);


  // create reader that reads into Topic records
  var reader = new Ext.data.JsonReader({
     totalProperty: 'total'
    ,root: 'rows'
    ,id: 'workOrderNo'
  }, WorkOrderRecord); 

  var groupStore = new Ext.ux.grid.MultiGroupingStore({
     proxy: new Ext.data.HttpProxy({
       url: 'orders.json',
       method: 'GET'
     })                     
    ,reader: reader
    ,sortInfo: {field: 'workOrderNo', direction: 'ASC'}
    ,groupField: ['contract','part','topWorkOrderNo']
  });

  
  var groupView = new Ext.ux.grid.MultiGroupingView({
     hideGroupedColumn :true
    ,emptyGroupText: 'All Group Fields Empty'
    ,displayEmptyFields: true //you can choose to show the group fields, even when they have no values
    ,groupTextTpl: '{text} : {group} '+
                   '({values.rs.length} '+
                   'Record{[values.rs.length>1?"s":""]}) '+
                   '/ Total Material Cost={[fm.usMoney(values.rs.sum("materialCosts"))]}'
    // Templates can be set up per grouping field if required                     
    ,groupFieldTemplates: {
        'serial': 'Special Grouping For Serial Number {gvalue}'
       ,'ecd': '{text}: {[todayString==values.group?"Today":yesterdayString==values.group?"Yesterday":values.group]}'
    }
  });
  
  todayString = Ext.util.Format.dateRenderer()(new Date());
  yesterdayString = Ext.util.Format.dateRenderer()((new Date()).add(Date.DAY, -1));
  
  var grid = new Ext.ux.grid.MultiGroupingGrid({
     store: groupStore
    ,cm:columnModel
    ,stateful: true
    ,view: groupView
    ,width: 900
    ,height: 400
    ,collapsible: true
    ,animCollapse: true
    ,title: 'Grouping Example'
    ,iconCls: 'icon-grid'
    ,renderTo: 'multiGroupEx1'
    ,selModel: new Ext.grid.RowSelectionModel({
       listeners : {
           scope: this
          ,'rowselect' : function(rsm, rowIndex, r ) {
       	     console.debug("rowselect : ", rsm, rowIndex, r );
       	     Ext.Msg.show({
               title:'Show Details',
               msg: 'You have selected Work Order ' +  r.data.workOrderNo,
               buttons: Ext.Msg.OK,
               icon: Ext.MessageBox.INFO
              });
          }
       }
     })    
    ,bbar: [
       {
          text:"Clear All"
         ,scope:groupStore 
         ,handler: function() {
            this.clearGrouping();
          }
       },'-',{
          text:"Remove Contract"
         ,scope:groupStore 
         ,handler: function() {
            this.removeGroupField('contract');
          }
       },'-',{
          text:"Set to ECD"
         ,scope:groupStore 
         ,handler: function() {
            this.groupBy(['ecd']);
          }
       },'-',{
          text:"Set To Contract, Work Order"
         ,scope:groupStore 
         ,handler: function() {
            this.groupBy(['contract','topWorkOrderNo']);
          }
       }
     ]
  }); 

  // Go get the data
  groupStore.load();

});
