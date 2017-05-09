/**
 * @class Jaffa.data.DWRProxy
 * @extends Ext.data.DataProxy
 *  
 * An implementation of {@link Ext.data.DataProxy} that uses DWR to read a data object from a remote server.
 * <p>
 * The DWRProxy has been obtained from http://yui-ext.com/forum/showthread.php?t=19529 
 * <p>
 * DWRProxy is a simple reader proxy that allows a reader to get its
 * data from a DWR method. It assumes that the method will return an 
 * array of object, and that it excepts a single object that contains all
 * the query parameters. So the underlying exposed Java Method through DWR
 * would look something like &lt;D&gt;[] myQueryMethod(&lt;C&gt;), where &lt;D&gt; is the data
 * results object and &lt;C&gt; is the criteria object.
 */
Jaffa.data.DWRProxy = Ext.extend(Ext.data.DataProxy, {
  
  /**
   * @cfg Should contain the property 'query' that exposes the underlying Java method for performing a query.
   */
  constructor: function(config) {
    Jaffa.data.DWRProxy.superclass.constructor.call(this);
    Ext.apply(this, config);
    this.addEvents(
        /**
         * @event loadexception
         * Fires before the load method's callback is called, assuming there was an error
         * returned while doing the load. If there are no errors, the normal 'load' event is called.
         * @param {Object} this
         * @param {Object} o The data object
         * @param {Object} arg The callback's arg object passed to the {@link #save} function
         */
        'loadexception'
    );
  }
  
  /**
   * 
   * @param {Object} params 
   * @param {Jaffa.data.Record} reader
   * @param {function} loadCallback
   * @param {Object} scope
   * @param {Object} arg the option object passed from the caller to hold the context information at the call
   */
  ,load : function(params, reader, loadCallback, scope, arg) {
    var dataProxy = this;
    dataProxy.fireEvent("beforeload", dataProxy, params);
    var args = [];
    if (params) args[args.length] = params;
    args[args.length] = {
      callback: function(response) {
        dataProxy.fireEvent("load", dataProxy, response, loadCallback);
        console.debug('Invoking reader.read', reader, response);
        var records = response;
        
        // in case of using old service which returns an array of graph objects as response
        if (Ext.isArray(response)) {
          records = {
            graphs: response,
            totalRecords: response.length
          };
        }
        
        // arg holds the context information of the caller at the time of calling load()
        // When load() is initiated from a tree node, arg holds the node that load() is requested. 
        // If the branch loaded is from a different source, in which case the nodes loaded needs 
        // significan modification to be fit into the target tree, arg is indispensable.
        if (reader && !records.errors) records = reader.read(records, arg);        
        loadCallback.call(scope, records, arg, true);
      }
      ,exceptionHandler: function(response, e) {
        dataProxy.fireEvent("loadexception", dataProxy, response, loadCallback, e);
        loadCallback.call(scope, null, arg, false);
      }
    };
    this.query.apply(this, args);
  }
});

/**
 * @class Jaffa.data.DWRCRUDProxy
 * @extends Jaffa.data.DWRProxy
 *  
 * An implementation of {@link Ext.data.DataProxy} that uses DWR to read and write
 * data objects from/to a remote server.
 * <p>
 * DWRCRUDProxy is an extension of the DWRProxy, but instead of just providing
 * a method for getting an array of objects, this also provides an update method
 * so that you can pass back any modified objects the query returned.
 * The underlying exposed Java Method for the update through DWR would look something
 * like &lt;R&gt;[] myUpdateMethod(&lt;D&gt;[]), where &lt;R&gt; is the response object,
 * per &lt;D&gt; supplied, and &lt;D&gt; is the root data object being updated
 */
Jaffa.data.DWRCRUDProxy = Ext.extend(Jaffa.data.DWRProxy, {
  
  /**
   * @cfg Should contain the property 'query' that exposes the underlying Java method for performing a query.
   * It should also contain the property 'update' that exposes the underlying Java method for performing an update.
   */
  constructor: function(config) {
    Jaffa.data.DWRCRUDProxy.superclass.constructor.call(this, config);
    this.addEvents(
        /**
         * @event beforesave
         * Fires before a network request is made to save the data objects.
         * @param {Object} this
         * @param {Object} params The params object passed to the {@link #save} function
         */
        'beforesave',
        /**
         * @event save
         * Fires before the save method's callback is called, assuming the save was successful
         * @param {Object} this
         * @param {Object} o The data object
         * @param {Object} arg The callback's arg object passed to the {@link #save} function
         */
        'save',
        /**
         * @event saveexception
         * Fires before the save method's callback is called, assuming there was an error on the save.
         * In this case returning a vaild 'respone' array containing errors is not considered a 
         * 'save exception'
         * @param {Object} this
         * @param {Object} o The data object
         * @param {Object} arg The callback's arg object passed to the {@link #save} function
         */
        'saveexception',
        /**
         * @event beforeclone
         * Fires before a network request is made to clone the data objects.
         * @param {Object} this
         * @param {Object} params The params object passed to the {@link #clone} function
         */
        'beforeclone',
        /**
         * @event clone
         * Fires before the clone method's callback is called, assuming the clone was successful
         * @param {Object} this
         * @param {Object} o The data object
         * @param {Object} arg The callback's arg object passed to the {@link #clone} function
         */
        'clone',
        /**
         * @event cloneexception
         * Fires before the clone method's callback is called, assuming there was an error on the clone.
         * In this case returning a vaild 'respone' array containing errors is not considered a 
         * 'clone exception'
         * @param {Object} this
         * @param {Object} o The data object
         * @param {Object} arg The callback's arg object passed to the {@link #clone} function
         */
        'cloneexception'
    );
  }
  /**
   * @param params
   * @param saveCallback function to call back on save sucess. 
   *        parameters to this callback will be fn(Object arg)
   * @param errorCallback function to call back on save failure. 
   *        parameters to this callback will be fn(Object arg)
   * @param scope Scope to be used for the callback function
   * @param arg Additional argument that will be supplied to the callback function
   */
  ,save: function(params, saveCallback, errorCallback, scope, arg) {
    var dataProxy = this;
    dataProxy.fireEvent("beforesave", dataProxy, params);
    var args = [];
    if (typeof(params) == 'Array') {
      for (var param in params) {
        args[args.length] = params[param];
      }
    } else {
      args[args.length] = params;
    }    
    args[args.length] = {
      callback: function(response) {
        dataProxy.fireEvent("save", dataProxy, response, saveCallback);
        saveCallback.call(scope, response, arg);
      }
      ,exceptionHandler: function(response, e) {
        dataProxy.fireEvent("saveexception", dataProxy, response, errorCallback, e);
        errorCallback.call(scope, response, arg);
      }
    };
    this.update.apply(this, args);
  }
  
  /**
   * Clone a set of records on database supplimented with the input data
   * 
   * @param {Object} criteria
   * @param {Object} inputData supplemented data for cloning a set of records.
   * @param {Object} cloneCallback
   * @param {Object} errorCallback
   * @param {Object} scope
   * @param {Object} arg
   */
  ,copy: function(criteria, inputData, cloneCallback, errorCallback, scope, arg) {
    var dataProxy = this;
    dataProxy.fireEvent("beforeclone", dataProxy, params);
    var args = [criteria, inputData];
    args.push({
      callback: function(response) {
        dataProxy.fireEvent("clone", dataProxy, response, cloneCallback);
        cloneCallback.call(scope, response, arg);
      }
      ,exceptionHandler: function(response, e) {
        dataProxy.fireEvent("cloneexception", dataProxy, response, errorCallback, e);
        errorCallback.call(scope, response, arg);
      }
    });
    this.clone.apply(this, args);
  }
});


Jaffa.data.HttpCRUDProxy = Ext.extend(Ext.data.HttpProxy, {

    constructor: function() {
      Jaffa.data.HttpCRUDProxy.superclass.constructor.apply(this,arguments);
    }
    
  ,save: function(params, saveCallback, errorCallback, scope, arg) {
        if(this.fireEvent("beforesave", this, params) !== false){
            var  o = {
                params : params || {},
                request: {
                    callback : saveCallback,
                    errorCallback : errorCallback,
                    scope : scope,
                    arg : arg
                },
                callback : this.saveResponse,
                scope: this
            };
            if(this.useAjax){
                Ext.applyIf(o, this.conn);
                o.url = this.conn.saveUrl;
                if(this.activeRequest){
                    Ext.Ajax.abort(this.activeRequest);
                }
                this.activeRequest = Ext.Ajax.request(o);
            }else{
                this.conn.request(o);
            }
        }else{
            callback.call(scope||this, null, arg, false);
        }
    }
    
  ,saveResponse : function(o, success, response){
        delete this.activeRequest;
        if(!success){
            this.fireEvent("saveexception", this, o, response);
            o.request.errorCallback.call(o.request.scope, null, o.request.arg, false);
            return;
        }
        this.fireEvent("save", this, o, o.request.arg);
        o.request.callback.call(o.request.scope, result, o.request.arg, true);
    }    
});

/**
 * @class Jaffa.data.QueryResponseReader
 * @extends Ext.data.JsonReader 
 * 
 * This is a reader to be used by the Jaffa.component.CRUDController.
 * It is similar to the Ext.data.DataReader family of objects, but instead
 * or returning an array of Ext.data.Record objects, it returns the plain
 * native objects.
 * 
 * It is typically used in conjunction with a Jaffa.data.HttpCRUDProxy or
 * a Jaffa.data.DWRCRUDProxy     
 */
Jaffa.data.QueryResponseReader = Ext.extend(Ext.data.JsonReader,{
  metaData : {
    totalProperty : 'totalRows'
    ,successProperty : undefined
    ,root : 'graphs'
  }  

  /**
   * Create a data block containing Ext.data.Records from an Array.
   * @param {Object} o An Array of row objects which represents the dataset.
   * @return {Object} data A data block which is used by an Ext.data.Store object as
   * a cache of Ext.data.Records.
   */
  ,readRecords : function(o){
    // Generate extraction functions for the totalProperty, the root
    if (!this.ef) {
      if(this.metaData.totalProperty) {
        this.getTotal = this.createAccessor(this.metaData.totalProperty);
      }
      if(this.metaData.successProperty) {
        this.getSuccess = this.createAccessor(this.metaData.successProperty);
      }
      this.getRoot = this.createAccessor(this.metaData.root);
      
      this.ef = [];
    }
    var rows = this.getRoot(o);
    console.debug("Rows=", rows, o, this);
    return {
        records : rows
        ,totalRecords : this.getTotal?this.getTotal(o):rows.length
    };
  }
});

/**
 * @class Jaffa.data.DWRQueryResponseReader
 * @extends Jaffa.data.QueryResponseReader 
 *
 * This is a reader to be used by the Jaffa.component.CRUDController.
 * It is similar to the Ext.data.DataReader family of objects, but instead
 * or returning an array of Ext.data.Record objects, it returns the plain
 * native objects.
 * 
 * It is typically used in conjunction with a Jaffa.data.HttpCRUDProxy or
 * a Jaffa.data.DWRCRUDProxy     
 */
Jaffa.data.DWRQueryResponseReader = Ext.extend(Jaffa.data.QueryResponseReader, {
  
  /**
   * This returns a response that can be used by a CRUD Controller
   * @param {Object} the source object that contains the data that will be returned in
   *        the 'records' property of the return object.
   * @return {Object} returns an object with the following properties
   *    'records' and 'totalRecords'. In this case 'records' is an array of
   *    native objects, not converted into Ext.data.Record objects as per a normal reader   
   */                 
  read: function(response, options) {
      console.debug("DWRQueryResponseReader.read: ", response);
      
      // To handle a DWR response, directly invoke the readRecords method
      return this.readRecords(response);
  }

});

/**
 * @class Jaffa.data.JsonObjectResponseReader
 * @extends Ext.data.JsonReader 
 *
 * This is a reader to be used by the Jaffa.component.CRUDController.
 * It is used when the JSON being returned is a single object that will be used as the model,
 * not an array of objects which is typical for a reader.
 * 
 * It is typically used in conjunction with a Jaffa.data.HttpCRUDProxy
 */
Jaffa.data.JsonObjectResponseReader = Ext.extend(Ext.data.JsonReader, {
  
  /**
   * Provides a simple pass thru. It assumes that the JSON object in the response
   * is alreay the correct object that this reader should be returning, and does
   * not try and convert it into an Array of Ext.data.Record objects.
   * @param {Object} o An Array of row objects which represents the dataset.
   * @return {Object} A data block which is used by a CRUD Controller as its model.
   */
  readRecords : function(o){
    return o;
  }  
});

/**
 * @class Jaffa.data.JsonDWRQueryResponseReader
 * @extends Jaffa.data.QueryResponseReader 
 *
 * This is a reader to be used by the Jaffa.component.LoadController.
 * It is used when the JSON being returned is a single object that will be used as the model,
 * not an array of objects which is typical for a reader.
 * 
 * It is typically used in conjunction with a Jaffa.data.HttpCRUDProxy
 */
Jaffa.data.JsonDWRQueryResponseReader = Ext.extend(Jaffa.data.JsonObjectResponseReader, {
  
  /**
   * This returns a response that can be used by a Load Controller
   * @param {Object} the source object that contains the data that will be returned in
   *        the 'records' property of the return object.
   * @return {Object} returns an object with the following properties
   *    'records' and 'totalRecords'. In this case 'records' is an array of
   *    native objects, not converted into Ext.data.Record objects as per a normal reader   
   */                 
  read: function(response, options) {
      console.debug("JsonDWRQueryResponseReader.read: ", response);
      
      // To handle a DWR response, directly invoke the readRecords method
      return this.readRecords(response);
  }

});


/**
 * @class Jaffa.data.TreeGridReader
 *
 * This is a reader to be used by the TreeGrid.
 */
Jaffa.data.TreeGridReader = Ext.extend(Ext.data.JsonReader, {
  
  /**
   * @cfg {String} is_leaf_field_name Record leaf flag field name.
   * NOTE: Ensure that the value of this property is in sync with a similarly named property of the tree store.
   */
  leaf_field_name : '_is_leaf',
  
  /**
   * @cfg {String} parent_id_field_name Record parent id field name.
   * NOTE: Ensure that the value of this property is in sync with a similarly named property of the tree store.
   */
  parent_id_field_name : '_parent',
  
  /**
   * @cfg {String} parent_collection_name Holds the collection name of the parent record, to which a given record belongs
   * NOTE: Ensure that the value of this property is in sync with a similarly named property of the tree store.
   */
  parent_collection_name : '_parentCollectionName',
  
  /**
   * Constructs an instance of this class.
   * The properties passed in the input config are applied to the instance.
   * @param {Object} config The config to be applied to a new instance.
   */
  constructor: function (config, recordTyp) {
    Jaffa.data.TreeGridReader.superclass.constructor.apply(this, arguments);
    Ext.apply(this, config);
  },
  
  /**
   * Create a data block containing Ext.data.Records from an Array.
   * @param {Object} o An Array of row objects which represents the dataset.
   * @return {Object} data A data block which is used by an Ext.data.Store object as
   * a cache of Ext.data.Records.
   */
  readRecords: function (o) {
    var list = [];
    this.readChildren(list, o);
    return {records: list, totalRecords: list.length};
  },
  
  /**
   * Recursively adds Records to the input list, for each child object in the input array
   * If the parentId is passed, it'll be stamped as the '_parent' attribute on each Record being added.
   * @param {Object} list An Array of Record objects which represents the dataset.
   * @param {Object} o An Array of row objects which represents the dataset.
   * @param {String} parentId (Optional) The id of the parent Record.
   */
  readChildren: function (list, o, parentId, parentCollectionName) {
    if (o) {
      // Convert a MixedCollection and a non-array to array, so that the rest of the code is consistent
      if (o.getRange && typeof o.getRange === 'function') {
        o = o.getRange();
      } else if (!Ext.isArray(o)) {
        o = [o];
      }
      
      for (var i = 0, len = o.length; i < len; i++) {
        var record = this.createRecord(o[i]);
        if (parentId)
          record.data[this.parent_id_field_name] = parentId;
        if (parentCollectionName)
          record.data[this.parent_collection_name] = parentCollectionName;
        list[list.length] = record;
        var foundChildren = false;
        var children = record.getChildObjects();
        if (children && children.getRange && typeof children.getRange === 'function' && children.keys && children.keys.length > 0) {
          // a mixed collection containing collectionName and graph[] pairs
          for (var j = 0; j < children.keys.length; j++) {
            var collectionName = children.keys[j];
            var collection = children.get(collectionName);
            if (collection && collection.length > 0) {
              this.readChildren(list, collection, record.id, collectionName);
              foundChildren = true;
            }
          }
        } else if (children && children.length > 0) {
          // an array. in this case the collectionName will have to be defined in the 'childrenFieldName' property of the associated metaClass
          this.readChildren(list, children, record.id);
          foundChildren = true;
        }
        // record.data[this.leaf_field_name] = !foundChildren;
      }
    }
  },
  
  /**
   * By default, this returns the recordType that was passed in the constructor.
   * This may be overridden to return a data-specific recordType.
   * @param {Object} o A row of data.
   */
  getRecordType: function (o) {
    return this.recordType;
  },
  
  /**
   * @private
   * Creates a Record from the input object
   * @param {Object} o a JSON object contains the data in graph structure
   */
  createRecord: function (o) {
    var recordType = this.getRecordType(o);
    
    // NOTE (SeanZ): An issue to consider: The following code creates an association between record type and reader. 
    // this prevent a record type to be used in multiple readers. Suggested modifications to remove the dependency:
    // 1. decide the order of precedence in getting meta information between reader and record type.
    // 2. create recordType.ef inside of the record type creator.
    
    // Determine the metaData
    if (o.metaData) {
      delete recordType.ef;
      recordType.meta = o.metaData;
    }
    if (!recordType.meta)
      recordType.meta = this.meta;
    
    // detemine the metaClass, based on the config of the Record, or the input object
    var mc;
    if (recordType.meta.metaClass)
      mc = typeof recordType.meta.metaClass == 'string' ? ClassMetaData[recordType.meta.metaClass] : recordType.meta.metaClass;
    else if (o.className)
      mc = ClassMetaData[o.className];
    
    // Unlike the JsonReader, which stamps the extract-functions on itself, we'll stamp the extract-functions
    // on the recordType, thus allowing multiple types of records in a tree store.
    if (!recordType.ef) {
      var g;
      if (recordType.meta.id) {
        g = this.createAccessor(recordType.meta.id);
      } else {
        if (mc && mc.key)
          g = this.createAccessor(mc.key);
      }
      if (g) {
        recordType.getId = function (rec) {
          var r = g(rec);
          return (r === undefined || r === "") ? null : r;
        };
      }
      if (!recordType.getId)
        recordType.getId = function () {return null;};
      
      recordType.ef = [];
      for (var i = 0, len = recordType.prototype.fields.length; i < len; i++) {
        var f = recordType.prototype.fields.items[i];
        var map = (f.mapping !== undefined && f.mapping !== null) ? f.mapping : f.name;
        recordType.ef[i] = this.createAccessor(map);
      }
    }
    
    // Build up the values object by using the extract-functions against the input object
    var values = {_metaClass: mc};
    var id = recordType.getId(o);
    for (var i = 0, len = recordType.prototype.fields.length; i < len; i++){
      var f = recordType.prototype.fields.items[i];
      var v = recordType.ef[i](o);
      values[f.name] = f.convert((v !== undefined) ? v : f.defaultValue, o);
    }
    
    // Create the Record
    var record = new recordType(values, id, o);
    record.json = o;
    return record;
  },
  
  /**
   * Creates a new record from a configuration object. 
   * @param {Object} o a javascript data object that provides configuration information. It should have
   *                   direct association to the name of the fields. There should have no data embeded
   *                   in graph-like hireachical structure.
   */
  createNewRecord: function(o) {
    var recordType = this.getRecordType(o);
    
    // Determine the metaData
    if (o.metaData) {
      delete recordType.ef;
      recordType.meta = o.metaData;
    }
    if (!recordType.meta)
      recordType.meta = this.meta;
    
    // detemine the metaClass, based on the config of the Record, or the input object
    var mc;
    if (recordType.meta.metaClass)
      mc = typeof recordType.meta.metaClass == 'string' ? ClassMetaData[recordType.meta.metaClass] : recordType.meta.metaClass;
    else if (o.className)
      mc = ClassMetaData[o.className];
    
    // Build up the values object by using the extract-functions against the input object
    var values = {_metaClass: mc};
    recordType.prototype.fields.each(function(f) {
      values[f.name] = o[f.name] || f.defaultValue;
    });
    
    // Create the Record
    var record = new recordType(values, null, o);
    return record;
  }
});

/**
 * @class Jaffa.data.DWRTreeGridReader
 *
 * This is a reader to be used to read in the tree from a graph inside of the DWR response.
 */
Jaffa.data.DWRTreeGridReader = Ext.extend(Jaffa.data.TreeGridReader, {
  /**
   * @cfg {String} root the root object in DWR response
   */
  
  /**
   * This returns a response that can be used by a CRUD Controller.
   * 
   * @note this method uses Jaffa.data.TreeGridReader.readRecords() 
   *       It handles responses from two types of jaffa services (one is new the other is old)
   * 
   * @return {Object} A data block which is used by an Ext.data.Store object as
   * a cache of Ext.data.Records.
   */                 
  read: function(response, options) {
      console.debug("DWRTreeGridReader.read: ", response);
      
      if (typeof this.beforeReadProcess === 'function') this.beforeReadProcess(response, options);

      // repackaging the responses from an old service which only returns an array of graphs 
      // while the new one will be packaged in response['some root name'] object
      var rsp = response;
      if (this.root) {
        if (Ext.isArray(response)) {
          // response from an old service
          if (response.length==0) return null;
          // find the top root node by parsing this.root
          var rt1 = this.root;
          var i = rt1.indexOf('[');
          if (i>=0) rt1 = rt1.substring(0, i);
          i = rt1.indexOf('.');
          if (i>=0) rt1 = rt1.substring(0, i);
          if (rt1 && rt1 != '') {
            rsp = {};
            rsp[rt1] = response;
          } else {
            throw('root node name is not defined in this reader.');
          }
        }
        rsp = Jaffa.data.Util.get(rsp, this.root);
      }
      // To handle a DWR response, directly invoke the readRecords method
      var recs = this.readRecords(rsp);
      if (typeof this.postReadProcess === 'function') this.postReadProcess(recs, response, options);
      return recs;
  },
  
  /**
   * A utility method to reset the record id value and its children's parent id value if the input records id 
   * is found already in the store.
   * 
   * Assume all the childrens are placed after the parent in the records array.
   * 
   * @param {Array of Jaffa.data.Record} recs
   * @param {Ext.ux.maximgb.treegrid.AdjacencyListStore} store
   */
  restId4DuplicatedRecords: function(recs, store) {
    for (var i=0; i<recs.length; i++) {
      var id = recs[i].id;
      if (store.getById(id)) {
        recs[i].id = ++Ext.data.Record.AUTO_ID;
        for (var j=(i+1); j<recs.length; j++) {
          if (recs[j].get(store.parent_id_field_name) == id)
            recs[j].set(store.parent_id_field_name, recs[i].id);
        }
      }
    }
    return recs;
  }
});
