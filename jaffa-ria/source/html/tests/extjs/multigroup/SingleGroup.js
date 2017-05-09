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

Ext.onReady(function(){
    Ext.QuickTips.init();    

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

  var groupStore = new Ext.data.GroupingStore({
     proxy: new Ext.data.HttpProxy({
       url: 'orders.json',
       method: 'GET'
     })                     
    ,reader: reader
    ,sortInfo: {field: 'workOrderNo', direction: 'ASC'}
    ,groupField: ['contract']
  });

  var groupView = new Ext.grid.GroupingView({
     hideGroupedColumn :true
    ,emptyGroupText: 'All Group Fields Empty'
    ,displayEmptyFields: true //you can choose to show the group fields, even when they have no values

     // We are using the new 'incomplete' setting to indicate the row count is not complete, and it
     // will suppress the totals, as this would lead to inacurate information
    ,groupTextTpl: '{text} : {group} '+
                   '({values.rs.length} '+
                   'Record{[values.rs.length>1?"s":""]})'
  });

  var grid = new Ext.grid.GridPanel({
     store: groupStore
    ,cm:columnModel
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
          text:"Set to ECD"
         ,scope:groupStore 
         ,handler: function() {
            this.groupBy('ecd');
          }
       }
     ]
  }); 

  // Go get the data
  groupStore.load();

});
