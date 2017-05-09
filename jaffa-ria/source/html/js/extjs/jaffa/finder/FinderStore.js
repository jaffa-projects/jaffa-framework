/**
 * @class Jaffa.data.FinderStore
 * @extends Ext.data.Store
 * An extension to {@link Ext.data.Store} for loading the output returned by Jaffa's Finder components.
 */
Jaffa.data.FinderStore = function(config) {
    /**
     * @cfg {object} meta The meta Object.containing the information required for creating the Store. This is a required property.
     */

    /**
     * @cfg {object} finderReaderMeta The meta Object.containing the information required for creating the Reader. This is a required property.
     */

    /**
     * @cfg {int} maxRecords The maximum number of records to be retrieved by the Store. This is a required property.
     */

    config.proxy = config.meta.getProxy();
    if (config.finderReaderMeta) {
      config.reader = new Jaffa.data.FinderReader(config.finderReaderMeta);
      delete config.finderReaderMeta;
    } else if (! config.reader){
      config.reader =  config.meta.getReader();
    }

    config.baseParams = Ext.apply(config.baseParams ? config.baseParams : {}, config.meta.getBaseParams(config));
    if (config.maxRecords) {
      config.baseParams.maxRecords = config.maxRecords;
      delete config.maxRecords;
    }
    config.paramNames = Ext.apply(config.meta.paramNames, config.paramNames);
    delete config.meta;
    
    var listenersFromConfig = config.listeners;
    delete config.listeners;
    
    Jaffa.data.FinderStore.superclass.constructor.call(this, config);
    
    if (listenersFromConfig) {
      // listeners in config object should be registered after the listeners defined in the class
      // so that the dummy fields are created correctly before they are used.
      this.on(listenersFromConfig);
    }
};

Ext.extend(Jaffa.data.FinderStore, Ext.data.GroupingStore, {
    listeners: {
        // The 'load' event is fired after a new set of Records has been loaded.
        // Will evaluate the dummy properties for each Record
        // Will set the custom property 'isLoaded' to true.
        load: function(store, records, options) {
            // Evaluate the dummy properties for each Record
            store.evaluateDummyProperties(records);
            
            // Initialize the firstRecord, else the PagingToolbar instance inside a FinderGridPanel is unable to calculate the initial cursor and displays 'NaN' in the current page field
            if (options.params && !options.params.firstRecord)
                options.params.firstRecord = 0;
            
            // Flag this Store as loaded to avoid reloads in the FinderCombo, which changes its mode from remote to local. This will however have no effect on the FinderComboGrid in lookup-mode
            store.isLoaded = true;
        },
        
        // The 'add' event is fired when Records have been added to the Store.
        // Will evaluate the dummy properties for each Record
        add: function(store, records, index) {
            // Evaluate the dummy properties for each Record
            store.evaluateDummyProperties(records);
        }
    },
    
    // private
    // Evaluates the dmmy properties for each Record
    evaluateDummyProperties: function(records) {
        if (records && records.length > 0) {
            // Determine the dummy properties by looking up fields having the 'tpl' attribute
            var dummyFields = [];
            var dummyFieldExists = false;
            for (var i = 0; i < this.reader.meta.fields.length; i++) {
                var field = this.reader.meta.fields[i];
                if (field.tpl) {
                    dummyFields[field.name] = typeof field.tpl == 'string' ? (new Ext.Template(field.tpl)).compile() : field.tpl;
                    dummyFieldExists = true;
                }
            }
            
            // Now create dummyFields for each record
            if (dummyFieldExists) {
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    for (var dummyFieldName in dummyFields) {
                        if (dummyFields[dummyFieldName] instanceof Ext.Template) {
                            var dummyFieldValue = dummyFields[dummyFieldName].applyTemplate(record.data);
                            if (dummyFieldValue && dummyFieldValue != '') {
                              // trim the spaces before and end of the string because it is trimmed in the field widget
                              // this trimming makes the comparison between the value in field and value in record possible
                              dummyFieldValue = dummyFieldValue.replace(/^\s+|\s+$/g, '');
                            }
                            record.data[dummyFieldName] = dummyFieldValue;
                        }
                    }
                }
                
                // Refresh the view
                this.fireEvent('datachanged', this);
            }
        }
    },
    
    /**
     * Remove the event handler when the unerlying object is to be destroyed.
     * @param {Object} o
     * @param {Object} options
     * @param {Object} success
     */
    loadRecords : function(o, options, success) {
      var dls = [];
      // find obsolete listeners
      for (var i in this.events) {
        if (typeof this.events[i] =='object') {
          for (var k=this.events[i].listeners.length-1; k>=0; k--) {
            if (this.events[i].listeners[k].scope.destroyed) {
              dls.push({event: i, scope: this.events[i].listeners[k].scope, fn: this.events[i].listeners[k].fn});
            }
          }
        }
      }
      // remove the listeners
      for (var i=0; i<dls.length; i++) {
        this.un(dls[i].event, dls[i].fn, dls[i].scope);
      }
      try {
        Jaffa.data.FinderStore.superclass.loadRecords.call(this, o, options, success);
      } catch (ex) {
        // avoid the error thrown from destroyed calling objects.
      }
    },
    
    /**
     * @TODO: Skipping reload when doNotReload is true is a hack to solve following problem.
     * When clicking on trigger box on a finder combo grid, a poppup window shows with two dwr queries.
     * The first one is generated at creating the grid in FinderComboGrid.renderGrid(); 
     * ExtJs by default calls
     *     grid.initState() -> grid.applyState() -> grid.store.reload().
     * This one does not call beforeautoselectqeury event first. 
     * The second dwr query is inititiated towards the end of FinderComboGrid.renderGrid() and calls 
     * beforeautoselectquery first. 
     * 
     * The first one is a wrong one. However, if it takes longer time to return than the second, the 
     * grid popped up is going to show the wrong information. Setting reload() to Ext.emptyFn solves the 
     * problem given the fact that it is not called in the life cycle of FinderGridPanel. 
     * 
     * doNotReload is set in Jaffa.form.FinderGridPanel.initComponent() and removed after first load event. 
     * This arrangement seems working. 
     * So far, reload() is know to be called in Ext.grid.GridPanel.applyState() during construction of grid panel
     * and when column filtering is set. 
     * 
     */
    reload: function(options) {
      if (this.doNotReload) return;
      Jaffa.data.FinderStore.superclass.reload.call(this, options);
    },
    
    /**
     * Unlike add(), it creates a new record useing the record type defined for the reader in this store.
     * @param {Jaffa.data.Record[]} records
     */
    add : function(records) {
      records = [].concat(records);
      if(records.length < 1){
        return;
      }
      if (this.reader.recordType) {
        for (var i=0; i<records.length; i++) {
          if (records[i].store != this.store) {
            records[i] = new this.reader.recordType(records[i].data);
          }
        }
      }
      Jaffa.data.FinderStore.superclass.add.call(this, records);
    },
    
    /**
     * Overwrite the parent class implementation to handle Jaffa DWR sorting parameters
     * @param {Object} options
     */
    load : function(options) {
      options = options || {};
      if(this.fireEvent("beforeload", this, options) !== false){
        this.storeOptions(options);
        var p = Ext.apply(options.params || {}, this.baseParams);
        if(this.sortInfo && this.sortInfo.field && this.remoteSort){
          // make the first letter of field name to uppercase (required to make the domain model to work)
          p[this.paramNames.sort] = [{
            fieldName:this.sortInfo.field.substring(0,1).toUpperCase()+this.sortInfo.field.substring(1), 
            sortAscending:this.sortInfo.direction=='ASC'
          }];
        }

        if (this.groupField && this.groupField!='' && this.remoteGroup) {
          if (this.sortInfo && this.remoteSort && this.sortInfo.field==this.groupField) {
            this.groupDirection = this.sortInfo.direction=='ASC';
          } else {
            var tmp = {fieldName : this.groupField.charAt(0).toUpperCase()+this.groupField.substring(1)};
            if (this.groupDirection != null) {
              tmp.sortAscending = Boolean(this.groupDirection);
            }
            if (p[this.paramNames.sort]) {
              p[this.paramNames.sort] = [tmp].concat(p[this.paramNames.sort]);
            } else {
              p[this.paramNames.sort] = [tmp];
            }
          }
        }
        this.proxy.load(p, this.reader, this.loadRecords, this, options);
        return true;
      } else {
        return false;
      }
    }
    
});
Ext.reg('finderstore', Jaffa.data.FinderStore);

/**
 * Retrieve records via a finder.
 * @param {Object} inputParams.metaClass The meta class of the finder
 * @param {Object} inputParams.queryJSON The query condition in dwr json object
 * @param {Function} inputParams.callback A function({@link Ext.data.Record}) to process the returned records.
 */
Jaffa.data.FinderStore.fetchARecord = function(inputParams) {
  var fndStore = new Jaffa.data.FinderStore({
    meta: inputParams.metaClass,
    maxRecords: 2,
    baseParams: inputParams.queryJSON,
    listeners: {
      load: function(store){
        if (inputParams.callback && typeof inputParams.callback=='function') {
          if (store.getCount() == 1) {
            inputParams.callback(store.getAt(0));
          } else {
            inputParams.callback(null);
            console.debug('Error: '+inputParams.metaClass.finder.DWRFunctionName+' retrieved '+store.getCount()+' records.');
          }
        }
      }
    }   
  });
  fndStore.load();
};


Jaffa.data.FinderStore.fetchRecords = function(inputParams) {
  var fndStore = new Jaffa.data.FinderStore({
    meta: inputParams.metaClass,
    maxRecords: 0,
    baseParams: inputParams.queryJSON,
    listeners: {
      load: function(store){
        if (inputParams.callback && typeof inputParams.callback=='function') {
          inputParams.callback(store);
        }
      }
    }   
  });
  fndStore.load();
};

Jaffa.data.FinderStore.getTotalRecordCount = function(inputParams) {
  var fndStore = new Jaffa.data.FinderStore({
    meta: inputParams.metaClass,
    maxRecords: 1,
    baseParams: inputParams.queryJSON,
    listeners: {
      load: function(store){
        if (inputParams.callback && typeof inputParams.callback=='function') {
          inputParams.callback(store.reader.jsonData.totalRecords);
        }
      }
    }   
  });
  fndStore.load();
};


