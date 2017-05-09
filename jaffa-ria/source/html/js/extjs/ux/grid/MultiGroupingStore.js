/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/** 
 * @class Ext.ux.grid.MultiGroupingStore
 * @extends Ext.grid.GroupingStore 
 * @author chander, PaulE
 *
 * Provided an extended grid store, that allows grouping by multiple columns
 * <p>
 * Credits - Based on Original Work found at http://extjs.com/forum/showthread.php?p=203828#post203828
 * 
 * @TODO Support ordering (ASC/DESC) of grouped columns, currently only ASC is provided
 */
Ext.ux.grid.MultiGroupingStore = Ext.extend(Ext.data.GroupingStore, {
    staticBaseParams: null,
    constructor: function(config){
      var sortInfo = config.sortInfo || this.sortInfo;
      Ext.ux.grid.MultiGroupingStore.superclass.constructor.apply(this, arguments);
      if (Ext.isArray(sortInfo)) {
        if (sortInfo.length==0) this.sortInfo = [];
        else this.sortInfo = sortInfo;
      } 
    }

   /* ----------------------------------------------------------------------
      Below is needed for Multi Grouping
      ---------------------------------------------------------------------- */
    /**
    * @cfg {Object} sortInfo A config object in the format: [{field: "fieldName", direction: "ASC|DESC"}].  The direction
    * property is case-sensitive.
    *     
    * This Override Ext.data.Store as this is now an array    
    */
   ,sortInfo: []
    
    /**
     * @param field If a single field is passed in it is appended to the grouped 
     *              fields (it is ignored if its already in the list).
     *              If an array is passed, we replace the current list of group
     *              fields with this new list.
     *              If field is empty/null it has the same effect as clearGrouping().
     * @param forceRegroup If true it forces the regroup logic even if there were
     *                     no changes to the groupField list.
     *
     * Overrides Ext.data.GroupingStore                               
     */           
   ,groupBy: function(field, forceRegroup){
      //alert("groupBy   " + field + "   " + forceRegroup);
      if (!forceRegroup && this.groupField == field) {
          return; // already grouped by this field
      }
      
      //if field passed in is an array, assume this is a complete replacement for the 'groupField'
      if(Ext.isArray(field)) {
        if(field.length==0)
          // @todo: field passed in is empty/null, assume this means group by nothing, ie remove all groups
          this.groupField=false;
        else
          this.groupField=field;
      } else {
        // Add the field passed as as an additional group
        if (this.groupField) {
          // If there is already some grouping, make sure this field is not already in here
          if(this.groupField.indexOf(field)==-1)
            this.groupField.push(field);
          else
            return; // Already grouped by this field  
        } else 
          // If there is no grouping already use this field
          this.groupField = [field];
      }
      if (this.remoteGroup) {
          if (!this.baseParams) {
              this.baseParams = {};
          }
          this.baseParams['groupBy'] = this.groupField;
      }
      console.debug("store.groupBy: data=",this.lastOptions);
      if(this.lastOptions!=null) { // do nothing if the store has never been loaded
        if (this.groupOnSort) {
            this.sort(field);
            return;
        }
        if (this.remoteGroup) {
            this.reload();
        }
        else {
            var si = this.sortInfo || [];
            if (si.field != field) {
                this.applySort();
            }
            else {
              //  alert(field);
                this.sortData(field);
            }
            this.fireEvent('datachanged', this);
        }
      }
    }

    /** private - Overrides Ext.data.GroupingStore
     * Initially sort based on sortInfo (if set and not remote)
     * Then resort based on groupFields (if set and not remote)
     */      
   ,applySort: function(){
        var si = this.sortInfo;
        if (si && si.length > 0 && !this.remoteSort) {
            this.sortData(si, si[0].direction);
        }
        if (!this.groupOnSort && !this.remoteGroup) {
            var gs = this.getGroupState();
            if (gs && gs != this.sortInfo) {
                this.sortData(this.groupField);
            }
        }
    }
    
    /** private - Overrides Ext.data.Store
     * @param flist is an array of fields to sort by
     */     
   ,sortData: function(flist, direction) {
      //console.debug('Store.sortData: ',flist, direction);
      direction = direction || 'ASC';
      var st = [];
      var o;
      for (var i = 0, len = flist.length; i < len; ++i) {
        o = flist[i];
        st.push(this.fields.get(o.field ? o.field : o).sortType);
      }
      var fn = function(r1, r2){
        var v1 = [];
        var v2 = [];
        var len = flist.length;
        var o;
        var name;
        
        for (var i = 0; i < len; ++i) {
          o = flist[i];
          name = o.field ? o.field : o;
          v1.push(st[i](r1.data[name]));
          v2.push(st[i](r2.data[name]));
        }
        
        var result;
        for (var i = 0; i < len; ++i) {
          result = v1[i] > v2[i] ? 1 : (v1[i] < v2[i] ? -1 : 0);
          if (result !== 0) 
            return result;
        }
        
        return result; //if it gets here, that means all fields are equal
      };
      
      this.data.sort(direction, fn);
      if (this.snapshot && this.snapshot != this.data) {
          this.snapshot.sort(direction, fn);
      }
    }

    /**
     * Sort the Records. Overrides Ext.data.store
     * If remote sorting is used, the sort is performed on the server, and the cache is
     * reloaded. If local sorting is used, the cache is sorted internally.
     * @param {String} field This is either a single field (String) or an array of fields [<String>] to sort by
     * @param {String} dir (optional) The sort order, "ASC" or "DESC" (case-sensitive, defaults to "ASC")
     */
   ,sort: function(field, dir){
      //console.debug('Store.sort: ',field,dir);
      var f = [];
      if (Ext.isArray(field)) {
          for (var i = 0, len = field.length; i < len; ++i) {
              var fld = this.fields.get(field[i]);
              if (fld) f.push(fld);
          }
      } else {
          f.push(this.fields.get(field));
      }
      
      if (f.length < 1) {
          return false;
      }
      
      if (!dir) {
          if (this.sortInfo && this.sortInfo.length > 0 && this.sortInfo[0].field == f[0].name) { // toggle sort dir
              dir = (this.sortToggle[f[0].name] || "ASC").toggle("ASC", "DESC");
          } else {
              dir = f[0].sortDir;
          }
      }
      
      var st = (this.sortToggle) ? this.sortToggle[f[0].name] : null;
      var si = (this.sortInfo) ? this.sortInfo : null;
      
      this.sortToggle[f[0].name] = dir;
      this.sortInfo = [];
      for (var i = 0, len = f.length; i < len; ++i) {
          this.sortInfo.push({
              field: f[i].name,
              direction: dir
          });
      }

      console.debug("store.sort: data=",this.lastOptions);
      if(this.lastOptions!=null) { // do nothing if the store has never been loaded
        if (!this.remoteSort) {
            this.applySort();
            this.fireEvent("datachanged", this);
        } else {
            this.nextKey = null;
            if (!this.reload()) {
                if (st) {
                    this.sortToggle[f[0].name] = st;
                }
                if (si) {
                    this.sortInfo = si;
                }
            }
        }
      }        
    }
    
    /**
     * Returns an object describing the current sort state of this Store.
     * @return {Object} The sort state of the Store. An object with two properties:<ul>
     * <li><b>field : String<p class="sub-desc">The name of the field by which the Records are sorted.</p></li>
     * <li><b>direction : String<p class="sub-desc">The sort order, "ASC" or "DESC" (case-sensitive).</p></li>
     * </ul>
     */
    ,getSortState : function(){
        return this.sortInfo && this.sortInfo.length>0 ?
               {field:this.sortInfo[0].field, direction:this.sortInfo[0].direction} :
               {};
     }
    
    /**
     * Sets the default sort column and order to be used by the next load operation.
     * Overrides Ext.data.Store     
     * @param {String} field The name of the field to sort by, or an array of fields
     * @param {String} dir (optional) The sort order, "ASC" or "DESC" (case-sensitive, defaults to "ASC")
     */    
   ,setDefaultSort: function(field, dir){
        // alert('setDefaultSort '+ field);
        dir = dir ? dir.toUpperCase() : "ASC";
        this.sortInfo = [];
        
        if (!Ext.isArray(field)) 
            this.sortInfo.push({
                field: field,
                direction: dir
            });
        else {
            for (var i = 0, len = field.length; i < len; ++i) {
                this.sortInfo.push({
                    field: field[i].field,
                    direction: dir
                });
                this.sortToggle[field[i]] = dir;
            }
        }
    }
    
    
   ,removeGroupField: function(fld) {
    // @todo
      if(this.groupField) {
        var i=this.groupField.length;
        this.groupField.remove(fld);
        // See if anything was really removed?
        if(this.groupField.length < i) {
           if(this.groupField.length==0)
             this.groupField=false;
           // Fire event so grid can be re-drawn  
           this.fireEvent('datachanged', this);
        }
      }  
    }
});




/**
 * @class Ext.ux.grid.MultiGroupingPagingStore
 * @extends Ext.ux.grid.MultiGroupingStore
 * A specialized {@link Ext.data.Store} that allows data to be appended a page at
 * a time as the user scrolls through. It is based on performing server-side sorting
 * and grouping and should be used in conjunction with a {@link Ext.ux.grid.MultiGroupPagingGrid}  
 * @constructor
 * Create a new MultiGroupingPagingStore
 * @param {Object} config The config object
 * 
 * @author PaulE  
 */
Ext.ux.grid.MultiGroupingPagingStore = Ext.extend(Ext.ux.grid.MultiGroupingStore, {

  /** When creating the store, register an internal callback for post load processing
   */  
    constructor: function(config) {
   	  Ext.ux.grid.MultiGroupingPagingStore.superclass.constructor.apply(this, arguments);
      // When loading has finished, need to see if there are more records
      /*  
      this.on("load", function(store, r, options) {
        return this.loadComplete(r, options);
      }, this);*/
      this.remoteSort=true;
      this.remoteGroup=true;
    }

  /**
   * @cfg {Number} pageSize
   * The number of records to read/display per page (defaults to 20)
   */
   ,pageSize: 20

   /** Private: The Key of the extra record read if there is more that the page size
    */
   ,nextKey: null

   ,defaultLocalizedErrorMessage: 'Internal errors occurred, please contact your system administrator.'
   
   ,loadErrorsTitleText: 'Load Errors'
   
    /** Override the load method so it can merge the groupFields and sortField
     * into a single sort criteria (group fields need to be sorted by first!)
     */         
   ,load : function(options){
      console.debug("Store.load: ", options, this.isLoading);
      options = options || {};
      if(this.fireEvent("beforeload", this, options) !== false){
        this.storeOptions(options);
        if(options.initial==true) {
          delete this.nextKey;
          delete this.totalCount;
        }
        delete this.baseParams.groupBy;
        var p = Ext.apply(options.params || {}, this.baseParams);
        Ext.apply(p, this.staticBaseParams);
        var sort=[];
        var meta=this.recordType.getField;
        var f;
        if(this.groupField && this.remoteGroup){
          if(Ext.isArray(this.groupField))
            for(var i=0;i<this.groupField.length;i++) {
              f=meta(this.groupField[i]);
              sort[sort.length] = (f.sortFieldName||this.groupField[i]) + ' ' + (f.sortDir || '');
            }
          else {
            f=meta(this.groupField);
            sort[sort.length] = (f.sortFieldName||this.groupField) + ' ' + (f.sortDir || '');
          }
        }
        if(this.sortInfo && this.remoteSort){
          if(Ext.isArray(this.sortInfo))
            for(var i=0;i<this.sortInfo.length;i++) {
              f=meta(this.sortInfo[i].field);
              sort[sort.length]=(f.sortFieldName || this.sortInfo[i].field) + " " + this.sortInfo[i].direction;
            }  
          else {
            f=meta(this.sortInfo.field);
            sort[sort.length]=(f.sortFieldName || this.sortInfo.field) + " " + this.sortInfo.direction;
          }
        }
        p[this.paramNames.sort]=sort.join(",");
        console.debug("Store.load : Query Parameters ",p,sort,this.sortInfo.field,this.sortInfo.direction, this);
        this.proxy.load(p, this.reader, this.loadRecords, this, options);
        return true;
      } else {
        return false;
      }
    }
    
   /** Reload the current set of record, using by default the current options
    * This will reload the same number of records that have currently been loaded, not
    * just the initial page again.       
    * @param options, additional query options that can be provided if needed
    */       
   ,reload : function(options){
        //The reload should be performed only if a load had happened earlier, in which case
        //the lastOptions field will be stamped. Bail out otherwise
        if (!this.lastOptions)
          return;
        
        var o = Ext.applyIf(options||{}, this.lastOptions);
        var pn = this.paramNames;
        if(!o.params) o.params=[];
        o.params[pn.start] = 0;
        o.params[pn.limit] = Math.max(this.pageSize,this.data.length) + 1;
        o.add = false;
        o.initial = false;
        console.debug("Store.reload :",o,this.sortInfo);
        return this.load(o);
    }
   /** Load the next page of records, if there are more available
    * @param initial, set to true if this should be a initial load
    */       
   ,loadMore : function(initial){
        if(!initial && !this.nextKey) {
          console.debug("Store.loadMore : Reject load, no more records left");
          return;
        } 

        var o = {}, pn = this.paramNames;
        o[pn.start] = initial?0:this.getCount();
        o[pn.limit] = this.pageSize + 1;
        console.debug("Store.loadMore : Loading based on ",o);
        this.load({params:o,add:!initial,initial:initial});
    }
  /** Load the all of records, if there are more available
   * @param initial, set to true if this should be a initial load
   */
  ,loadAll : function(initial){
       if(!initial && !this.nextKey) {
         console.debug("Store.loadMore : Reject load, no more records left");
         return;
       }

       var o = {}, pn = this.paramNames;
       o[pn.start] = initial?0:this.getCount();
       o[pn.limit] = -1;
       console.debug("Store.loadAll : Loading based on ",o);
       this.load({params:o,add:!initial,initial:initial});
   }
   /** Private - Override default callback handler once records have been loaded.
    * Looks to see if we are able to find more that just the page size, if so
    * it removes the extra one, but keeps it for consistency checking for when the
    * next page is loaded
    * @param r, array of records read from the server
    * @param options, the options that were used by the load operation to do the query
    */       
   ,loadRecords : function(o, options, success){
      if(o && success && o.records) {
        var r=o.records;
        console.debug("Store.loadRecords : rows=", r.length, options);
        var nextKey = this.nextKey;
        delete this.nextKey;
        // Need to compare the prior next key, to the first row that was added
        // This could trigger a complete reload
        if(nextKey && r.length>0) {
          var id = this.reader.meta.id; // Get key field name from reader
          console.debug("Store.loadRecords : Refresh Check...",id,r[0].data[id],nextKey.data[id]);
          if(r[0].data[id] != nextKey.data[id]) {
            console.debug("Store.loadRecords : Need to refresh all records as they are out of sync");
            var pn = this.paramNames;
            var oldLimit = (isNaN(options.params[pn.limit]) ? 0 : options.params[pn.limit]);
            var oldStart = (isNaN(options.params[pn.start]) ? 0 : options.params[pn.start]);
            options.params[pn.limit] = oldLimit < 0?oldLimit:(oldLimit + oldStart);
            options.params[pn.start] = 0;
            options.add = false;
            options.initial = false;
            delete this.nextKey;
            this.fireEvent("loadexception", this);
            console.debug("Store.loadRecords : Reload Using ",options);
            //this.load.defer(20, this, [options]);
            this.load(options);
            return;
          }
        }   
        // Need to remove the extra record, and put it in the next key.
        if(options.params[this.paramNames.limit]>=0 && r.length>=options.params[this.paramNames.limit]) {
          console.debug("Store.loadRecords : More records exist, remove extra one");
          this.nextKey = r[r.length-1];
          // remove this last record
          r.remove(this.nextKey);
          this.totalCount = o.totalRecords;
          console.debug("Store.loadRecords : Total=",this.data.length,this.getCount());
        } else
          // Set the total count as we now know what it is
          this.totalCount = r.length+(options.add==true?this.getCount():0);
      } 
      if (Ext.isArray(o.errors) && o.errors.length>0) {
        var msg = '';
        for (var i=0; i<o.errors.length; i++) {msg += (o.errors[i].localizedMessage || this.defaultLocalizedErrorMessage) + '\n<br>';}
        if (msg) {Ext.MessageBox.show( {title : this.loadErrorsTitleText,msg : msg,buttons : Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});}
        this.fireEvent('load', this);
      } else {
        //Check if any of the new records have keys that exist in the current store
        var newRecs = o.records?o.records:[];
        var foundKey = false;
        if (options && options.add){
          for (var i = 0; i < newRecs.length && !foundKey; i++){
            if (this.data && this.data.containsKey && newRecs[i] && newRecs[i].id && this.data.containsKey(newRecs[i].id)){
              foundKey = true;
            }
          }
        }
        //If a duplicate key is found then records are out of sync, reload all the required records.
        if (foundKey){
          console.debug("Store.loadRecords : Need to refresh all records as they are out of sync");
          var pn = this.paramNames;
          var oldLimit = (isNaN(options.params[pn.limit]) ? 0 : options.params[pn.limit]);
          var oldStart = (isNaN(options.params[pn.start]) ? 0 : options.params[pn.start]);
          options.params[pn.limit] = oldLimit < 0?oldLimit:(oldLimit + oldStart);
          options.params[pn.start] = 0;
          options.add = false;
          options.initial = false;
          delete this.nextKey;
          this.fireEvent("loadexception", this);
          console.debug("Store.loadRecords : Reload Using ",options);
          this.load(options);
          return;
        }else{
          Ext.ux.grid.MultiGroupingStore.superclass.loadRecords.call(this, o, options, success);
        }

      }
    }
});  

/**
 * @class Ext.ux.grid.MultiGroupingPagingDWRStore
 * @extends Ext.ux.grid.MultiGroupingPagingStore
 * @constructor Create a new MultiGroupingPagingStore
 * @param {Object} config The config object
 * 
 * @author PaulE  
 */
Ext.ux.grid.MultiGroupingPagingDWRStore = Ext.extend(Ext.ux.grid.MultiGroupingPagingStore, {

    /** When creating the store, register an internal callback for post load processing
     */  
    constructor: function(config) {
   	  Ext.ux.grid.MultiGroupingPagingDWRStore.superclass.constructor.apply(this, arguments);
      this.paramNames = {
        'start':'objectStart'
       ,'limit':'objectLimit'
       ,'sort':'orderByFields'
       ,'dir' : undefined 
      }
    }
    
    /** Override the load method so it can merge the groupFields and sortField
     * into a single sort criteria (group fields need to be sorted by first!)
     */         
   ,load : function(options){
      console.debug("Store.load: ", options, this.isLoading);
      options = options || {};
      if(this.fireEvent("beforeload", this, options) !== false){
        this.storeOptions(options);
        if(options.initial==true) {
          delete this.nextKey;
          delete this.totalCount;
        }
        delete this.baseParams.groupBy;
        var p = Ext.apply(options.params || {}, this.baseParams);
        Ext.apply(p, this.staticBaseParams);
        var sort=[];
        var meta=this.recordType.getField;
        var f;
        if(this.groupField && this.remoteGroup){
          if(Ext.isArray(this.groupField))
            for(var i=0;i<this.groupField.length;i++) {
              f=meta(this.groupField[i]);
              if (f) sort[sort.length] = {fieldName: f.sortFieldName||this.groupField[i], sortAscending: f.sortDir=='ASC' };
            }
          else {
            f=meta(this.groupField);
            if (f) sort[sort.length] = {fieldName: f.sortFieldName||this.groupField, sortAscending: f.sortDir=='ASC' };
          }
        }
        if(this.sortInfo && this.remoteSort){
          if(Ext.isArray(this.sortInfo)){
            for(var i=0;i<this.sortInfo.length;i++) {
              f=meta(this.sortInfo[i].field);
              if(f){
                sort[sort.length] = {fieldName: f.sortFieldName || this.sortInfo[i].field, sortAscending: this.sortInfo[i].direction=='ASC' };
              }
            }  
          } else {
            f=meta(this.sortInfo.field);
            if(f){
              sort[sort.length] = {fieldName: f.sortFieldName || this.sortInfo.field, sortAscending: this.sortInfo.direction=='ASC' };
            }
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
Ext.reg('multigroupingpagingdwrstore', Ext.ux.grid.MultiGroupingPagingDWRStore);

