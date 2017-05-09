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
    for(var i=0,sum=0;i<this.length;i++) {
      var f = parseFloat(this[i].data[field]);
      if(f!=NaN)
        sum+=f;
    }
  else
    for(var i=0,sum=0;i<this.length;sum+=this[i++]);
  return sum;
} 
 
Ext.onReady(function(){
    Ext.QuickTips.init();    

  var WorkOrderRecord = Ext.data.Record.create([
     {name: 'workOrderNo'}
    ,{name: 'segregationCode'}
    ,{name: 'topWorkOrderNo'}
    ,{name: 'nextHigherWorkOrderNo'}
    ,{name: 'workOrderLevel'}
    ,{name: 'part'}
    ,{name: 'serial'}
    ,{name: 'span'}
    ,{name: 'ecd', type:'date'}
    ,{name: 'laborHours', type:'float',defaultValue:0.00}
    ,{name: 'materialCosts', type:'float',defaultValue:0.00}
  ]);
    
  var columnModel = new Ext.grid.ColumnModel([{
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
      ,dataIndex: 'nextHigherWorkOrderNo'
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
     root: 'rows'
    ,id: 'workOrderNo'
  }, WorkOrderRecord); 

  var groupStore = new Ext.ux.grid.MultiGroupingPagingStore({
     proxy: new Ext.data.HttpProxy({
        url: 'workOrderQuery.jsp'
       ,method: 'GET'
     })
    ,reader: reader
    ,sortInfo: {field: 'workOrderNo', direction: 'ASC'}
    ,groupField: ['part','serial']
  });
  
  var groupView = new Ext.ux.grid.MultiGroupingView({
     hideGroupedColumn :true

    ,emptyGroupText: 'All Group Fields Empty'

    ,displayEmptyFields: true //you can choose to show the group fields, even when they have no values

     // We are using the new 'incomplete' setting to indicate the row count is not complete, and it
     // will suppress the totals, as this would lead to inacurate information
    ,groupTextTpl: '{text} : {group} '+
                   '({values.rs.length}{[values.incomplete?"+":""]} '+
                   'Record{[values.rs.length>1?"s":""]}) '+
                   '<tpl if="values.incomplete!=true">'+
                   '/ Total Material Cost={[fm.usMoney(values.rs.sum("materialCosts"))]}'+
                   '</tpl>'
    // Templates can be set up per grouping field if required                     
    ,groupFieldTemplates: {
        'serial': 'Special Grouping For Serial Number {gvalue}'
    }
  });

  var grid = new Ext.ux.grid.MultiGroupingPagingGrid({
     store: groupStore
    ,cm:columnModel
    ,view: groupView
    ,width: 900
    ,height: 300
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
    // Show how custom stuff can still be added to the bottom bar
    ,bbar: [
       {
          text:"Clear All"
         ,scope:groupStore 
         ,handler: function() {
            console.debug("This=",this);
            this.clearGrouping();
          }
       },'-',{
          text:"Remove Part"
         ,scope:groupStore 
         ,handler: function() {
            this.removeGroupField('part');
          }
       },'-',{
          text:"Set to ECD"
         ,scope:groupStore 
         ,handler: function() {
            this.groupBy(['ecd']);
          }
       },'-',{
          text:"Set To Part, Serial"
         ,scope:groupStore 
         ,handler: function() {
            this.groupBy(['part','serial']);
          }
       }
     ]
  }); 
  
  // Now go and get the data...
  groupStore.loadMore(true);

});
