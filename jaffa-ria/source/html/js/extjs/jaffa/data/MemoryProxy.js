//-----------
// DEPRECATED - will be removed
//-----------

/**
 * @class Jaffa.data.MemoryProxy
 * @extends Ext.data.DataProxy
 * An implementation of {@link Ext.data.DataProxy} that uses the DWR object to load data into grids.
 * @constructor
 */
Jaffa.data.MemoryProxy = function(config){
    Jaffa.data.MemoryProxy.superclass.constructor.call(this);
    if (config.data) {
      this.data = config.data;
    }
    if (config.filters) {
      this.filters = config.filters;
    }
};
Ext.extend(Jaffa.data.MemoryProxy, Ext.data.DataProxy, {
  
    /**
     * Load data from the requested source (in this case an in-memory
     * data object passed to the constructor), read the data object into
     * a block of Ext.data.Records using the passed Ext.data.DataReader implementation, and
     * process that block using the passed callback. It is assumed that the in-memory data is an DWR object.
     * @param {Object} params This parameter is not used here.
     * @param {Ext.data.DataReader) reader The Reader object is not used here.
     * object into a block of Ext.data.Records.
     * @param {Function} callback The function into which to pass the block of Ext.data.records.
     * The function must be passed <ul>
     * <li>The Record block object</li>
     * <li>The "arg" argument from the load function</li>
     * <li>A boolean success indicator</li>
     * </ul>
     * @param {Object} scope The scope in which to call the callback
     * @param {Object} arg An optional argument which is passed to the callback as its second parameter.
     */
  load : function(params, reader, callback, scope, arg){
    this.snapshot = this.snapshot || scope.getRange();
    params = params || {};
    var result;
    try {
      result = this.dataFiltering(this.snapshot);
    }catch(e){
      this.fireEvent("loadexception", this, arg, null, e);
      callback.call(scope, null, arg, false);
      return;
    }
    callback.call(scope, result, arg, true);
  },
  
  dataFiltering : function(records) {
    var output = records;
    var flts = this.filters.getFilterData();
    if (flts.length>0) {
      for (var i=0; i<flts.length; i++) {
        output = this.filteringRecords(output, flts[i]);
      }
    }
    return {
      records: output,
      success: true,
      totalRecords: output.length
    };
  },
  
  filteringRecords : function(records, flt) {
    var output = new Array();
    if (flt && flt.data && flt.field) {
      var v;
      if (flt.data.type=='numeric') {
        // flt.data.comparison = 'gt', 'lt', 'eq'
        // comparing with flt.data.value
        for (var i=0; i<records.length; i++) {
          v = records[i].get(flt.field);
          if (v==null) continue;
          if (flt.data.comparison=='gt') {
            if (v > flt.data.value) 
              output[output.length] = records[i];
          } else if (flt.data.comparison=='lt') {
            if (v < flt.data.value) 
              output[output.length] = records[i];
          } else if (flt.data.comparison=='eq') {
            if (v == flt.data.value) 
              output[output.length] = records[i];
          }
        }
      } else if (flt.data.type=='list') {
        // flt.data.value=['choice1','choice2','choice3']
        // flt.field = 'dataIndex'
        // filtering the records
        var fltVal = flt.data.value.join('::');
        for (var i=0; i<records.length; i++) {
          v = records[i].get(flt.field);
          if (v==null) continue;
          if (fltVal.search(v)>=0)
            output[output.length] = records[i];
        }
      } else if (flt.data.type=='date') {
        // flt.data.value='mm/dd/yyyy'
        // flter.data.comparison='lt' for before, 'gt', for later, 'eq' for on
        var fltDate = new Date(flt.data.value);
        for (var i=0; i<records.length; i++) {
          v = records[i].get(flt.field);
          if (v==null) continue;
          v = v.clone();
          v.clearTime();
          if (v < fltDate) {
            if (flt.data.comparison == 'lt')
              output[output.length] = records[i];
          } else if (v > fltDate) {
            if (flt.data.comparison == 'gt')
              output[output.length] = records[i];
          } else if (flt.data.comparison == 'eq') {
            output[output.length] = records[i];
          } 
        }
      } else if (flt.data.type=='string') {
        // flt.data.value='**'
        for (var i=0; i<records.length; i++) {
          v = records[i].get(flt.field);
          if (v==null) continue;
          if (v.search(flt.data.value)>=0)
            output[output.length] = records[i];
        }
      } else if (flt.data.type=='boolean') {
        // flt.data.value='true', 'false'
        for (var i=0; i<records.length; i++) {
          v = records[i].get(flt.field);
          if (v==null) continue;
          if (flt.data.value == v)
            output[output.length] = records[i];
        }
      }
    }
    return output;
  }
  
});

