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

/**
 * Special DWR Reader as the response should already be an array, don't need to 'eval()' it!
 * But we still extend the JSON reader as we need to create record fields via object traversal,
 * i.e. {name: 'part', mapping: 'part.part'}
 */ 
Ext.data.DwrReader = function(meta, recordType){
    Ext.data.DwrReader.superclass.constructor.call(this, meta, recordType);
};
Ext.extend(Ext.data.DwrReader, Ext.data.JsonReader, {
   read : function(response){
     return this.readRecords(response);
   }
});

// NOTE: This function can also be found in \js\extjs\ux\ux-overrides.js, which is included by default if \js\extjs\loadJaffaRIA.jsp is used
/**
 * Override default code to package the filter fields so they are compatable with the Jaffa CriteraField objects
 */ 
Ext.override(Ext.ux.grid.GridFilters, {
  buildQuery : function(filters){
    console.debug("GridFilters.buildQuery : ",filters);
    var p = {};
    var flds = this.grid.getStore().recordType.prototype.fields;
    for(var i=0, len=filters.length; i<len; i++){
      var f = filters[i];
      var dt = filters[i].data.type;
      var c = filters[i].data.comparison;
      var fld = filters[i].field;
      var v = filters[i].data.value;
      var name = flds.get(fld).filterFieldName || fld;
        
      if (dt=='numeric' || dt=='int' || dt=='long' || dt=='float' || dt=='double' || dt == 'date') {
        if (c=='gt' || c=='after') {
          if (p[name] && p[name].operator=='SmallerThan')
            // Upgrade this to a Bewteen Clause
            p[name] = {operator : 'Between', values : [p[name].values[0],v] };
          else
            p[name] = {operator : 'GreaterThan', values : [v]};
        } else if (c=='lt' || c=='before') {
          if (p[name] && p[name].operator=='GreaterThan')
            // Upgrade this to a Bewteen Clause
            p[name] = {operator : 'Between', values : [v,p[name].values[0]]};
          else
            p[name] = {operator : 'GreaterThan', values : [v]};
        } else if (c=='eq' || c=='on') 
            p[name] = {values : [v]};
      } else if (dt == 'string') {
          p[name] = {operator : 'BeginsWith', values : [v]};
      } else if (dt == 'list') {
          p[name] = {operator : 'In', values : v.split(',') };
      } else if (dt == 'boolean') {
        p[name] = {values : [v]};
        if (flts[i].data.value) {
          prms[flts[i].field] = 'True';
        } else {
          prms[flts[i].field] = 'False';
        }
      }
    }  
    return p;
  }
});


// NOTE: This function can also be found in \js\extjs\extjs-overrides.js, which is included by default if \js\extjs\loadExtJS.jsp is used
/** Override getJsonAccessor which is used to generate extraction functions.
  * If it creates an extraction function that has nested values (ie extract part.description)
  * in its original state, if part is null, an NullPointerException will be thrown when the
  * function is used. In this new version this is caught and 'null' is returned.
  * 
  * NOTE: This creates a function on the fly, that has a local variable. This function executes
  * and returns the real function to be used in the prototype. The real function now references
  * that original local variable, which there will only be one copy of. This could be avoided if
  * the 're' variable was made "global'. It is a clever use of a 'closure' to
  * scope the variable and prevent it being initialized each time this function is invoked    
  *      
  */
Ext.data.JsonReader.prototype.getJsonAccessor =  function(){
    var re = /[\[\.]/;
    return function(expr) {
        try {
            return(re.test(expr)) ?
                new Function("obj", "try{return obj."+expr+"} catch(e){return null}") :
                function(obj){return obj==null?null:obj[expr];};
        } catch(e){
          return Ext.emptyFn;
        }
    };
}();

 
Ext.onReady(function(){
    Ext.QuickTips.init();    

 /** Parameters that are used by a record to make life easier for grid construction
  * @param name Simple name used throughout for this field
  * @param type What data type the field is (Default is string, others are float, int, date, ...)        
  * @param defaultValue The value to use in the record if there is no data for this field        
  * @param mapping JSON Path to get the value out of the data structure for this record
  * @param sortable Boolean to indicate whether sorting is available from the server for this field
  * @param sortFieldName The alternate {name} to use when passing the sort by field list to the server
  * @param filterFieldName The alternate {name} to use when passing the filtering clause to the server
  */  
  var WorkOrderRecord = Ext.data.Record.create([
     {name: 'workOrderNo',
            sortable: true,
            sortFieldName:'WorkOrderNo'}
    ,{name: 'segregationCode',
            mapping: 'segregationCode.segregationCode',
            sortable: true}
    ,{name: 'topWorkOrderNo'}
    ,{name: 'nextHigherWorkOrderNo'}
    ,{name: 'workOrderLevel'}
    ,{name: 'part',
            mapping: 'part.part',
            sortable: true,
            sortFieldName:'Part'}
    ,{name: 'serial',
            sortFieldName:'Serial'}
/* FIXME - Still an Error with JSON reader if item object is null.
    ,{name: 'topSerial',
            mapping: 'item.serial'}
*/            
    ,{name: 'span',
            sortable: false}
    ,{name: 'ecd',
            type:'date'}
    ,{name: 'laborHours',
            type:'float',
            defaultValue:0.00}
    ,{name: 'materialCosts',
            type:'float',
            defaultValue:0.00}
  ]);

  var filters = new Ext.ux.grid.GridFilters({filters:[
        {type: 'string',   dataIndex: 'workOrderNo'},
        {type: 'string',   dataIndex: 'segregationCode'},
        {type: 'string',   dataIndex: 'topWorkOrderNo'},
        {type: 'string',   dataIndex: 'nextHigherWorkOrderNo'},
        {type: 'string',   dataIndex: 'part'},
        {type: 'string',   dataIndex: 'serial'},
        {type: 'numeric',  dataIndex: 'span'},
        {type: 'numeric',  dataIndex: 'laborHours'},
        {type: 'numeric',  dataIndex: 'materialCosts'},
        {type: 'date',     dataIndex: 'ecd'}
    ], local:false});

    
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
  var reader = new Ext.data.DwrReader({
     id: 'workOrderNo'
  }, WorkOrderRecord); 

  var groupStore = new Ext.ux.grid.MultiGroupingPagingStore({
     proxy: new Ext.data.DWRProxy(WorkOrderService.query,{
       filters: filters
     })
    ,reader: reader
    ,sortInfo: {field: 'workOrderNo', direction: 'ASC'}
    ,groupField: ['part','serial']
    ,baseParams : {resultGraphRules:['*']}
    ,paramNames : {
        'start':'objectStart'
       ,'limit':'objectLimit'
       ,'sort':'orderByFields'
       ,'dir' : undefined 
    }
    /** Override the load method so it can merge the groupFields and sortField
     * into a single sort criteria (group fields need to be sorted by first!)
     */         
   ,load : function(options){
      console.debug("Store.load: ", options, this.isLoading);
      options = options || {};
      if(this.fireEvent("beforeload", this, options) !== false){
        this.storeOptions(options);
        var p = Ext.apply(options.params || {}, this.baseParams);
        var sort=[];
        var meta=this.recordType.getField;
        var f;
        if(this.groupField && this.remoteGroup){
          if(Ext.isArray(this.groupField))
            for(var i=0;i<this.groupField.length;i++) {
              f=meta(this.groupField[i]);
              sort[sort.length] = {fieldName: f.sortFieldName||this.groupField[i], sortAcending: (f.sortDir=='ASC') };
            }
          else {
            f=meta(this.groupField);
            //sort[sort.length] = (f.sortFieldName||this.groupField) + ' ' + (f.sortDir || '');
          }
        }
        if(this.sortInfo && this.remoteSort){
          if(Ext.isArray(this.sortInfo))
            for(var i=0;i<this.sortInfo.length;i++) {
              f=meta(this.sortInfo[i].field);
              //sort[sort.length]=(f.sortFieldName || this.sortInfo[i].field) + " " + this.sortInfo[i].direction;
            }  
          else {
            f=meta(this.sortInfo.field);
            //sort[sort.length]=(f.sortFieldName || this.sortInfo.field) + " " + this.sortInfo.direction;
          }
        }
        p[this.paramNames.sort]=sort;
        console.debug("Store.load : Query Parameters ",p,sort,this.sortInfo.field,this.sortInfo.direction, this);
        this.proxy.load(p, this.reader, this.loadRecords, this, options);
        return true;
      } else {
        return false;
      }
    }    
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
    ,plugins: filters
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
