/**
 * @class Jaffa.form.FinderComboBox
 * @extends Ext.form.ComboBox
 * An extension to {@link Ext.form.ComboBox} for rendering the output returned by Jaffa's Finder components.
 */
Jaffa.form.FinderComboBox = Ext.extend(Ext.form.ComboBox, {
    /**
     * @cfg {object} meta The meta Object.containing the information required for creating the widget. This is a required property.
     */

    /**
     * @cfg {object} metaForValidation The meta Object containing the information required for validating the widget. This is an optional property. If absent, the 'meta' property will be used for validation.
     */

    /**
     * @cfg {object} editor The editor Object from the editable parentGrid column assigned from Ext.grid.EditorGridPanel.onRender() from extension.
     */

    /**
     * @cfg {object} parentGrid The editable grid Object {@link Ext.grid.EditorGridPanel}, in which this field is a cell.
     */

    /**
     * @cfg {string} editorRecordField The dataIndex of the grid column for this combobox assigned in Ext.grid.EditorGridPanel.onRender() from extension.
     */

    /**
     * @cfg {boolean} validateExtendValues Validate true to values not in the list but are in the database. It will skip beforeautoselectquery event at validation query.
     */
    
    /**
     * @cfg {boolean} shareStore true to share the store between components of the same store finder when
     * displayField==valueField. When displayField!=valueField, store is needed to find value from display value, 
     * therefore the store cannot be shared. Defaults to false.
     */
    shareStore: false,

    isValueValid: true,
    
    /**
     * @cfg {boolean} allowMultiple true to allow multiple values when validate
     */
    allowMultiple: false,
    
    /**
     * @cfg {boolean} allowInvalid true to allow invalidate values although validation will still be performed.
     */
    allowInvalid: false,
    
    /**
     * @cfg {String} invalidateNotFoundText The error text to use when marking a field invalid due to the record not found.
     * (defaults to "The value in this field is invalid")
     */
    invalidateNotFoundText: 'Record not found',
    
    /**
     * @cfg {String} invalidateMultipleRecordsFoundText The error text to use when marking a field invalid due to more than one records found.
     * (defaults to "The value in this field is invalid")
     */
    invalidateMultipleRecordsFoundText: 'Multiple records found that match the value in this field',

    /**
     * @cfg {String} invalidNumberText The error text to use when marking a field invalid when it must contain a numeric value.
     * (defaults to "The value in this field is invalid")
     */
    invalidNumberText: 'The value for this field must be a number',

    /**
     * @cfg {boolean} validateStoreOnly true will force validation only against values in the store.
     * Only use this if you are forcing the store to be loaded in all cases. Otherwise typing a value directly and
     * then leaving the field will always report the value as invalid
     */
    validateStoreOnly: false,
    /**
     * @cfg {boolean} forceRemoteQuery true  -  Will fire remote query every time when combo trigger is clicked,
     * Setting this to false will set the mode to 'local', if the Store is flagged as 'loaded'.
     * The combo will thus filter the data locally, without the need to fire remote events.
     */
    forceRemoteQuery : false,
    /**
    * @cfg {object} staticBaseParams   - Static baseParams that will be applied on the store.These params are applied
    * during component initialization(initComponent) .These baseParams remain static and will not be cleared,
    * even if clear filters button is clicked on the grid.
    *
    * Example: staticBaseParams : { userId  : {values: ['sam'] }, status : {values : ['A']} }
    */
    staticBaseParams : undefined,
    /**
     * This method should be overridden to apply staticBaseParams if the parameters can't be initialized using staticBaseParams config.
     * The baseParams that are retrieved using this method will be applied inside onTriggerClick method. These baseParams remain static
     * and will not be cleared, even if clear filters button is clicked on the grid.
     *
     * Returns baseParams object;
     * Example : return { userId  : {values: ['sam'] }, status : {values : ['A']} }
     *
     * Defaults to Ext.emptyFn.
     */
    getStaticBaseParams : Ext.emptyFn,

  /**
   * @cfg {boolean} allowNumberOnly false  -  If true will validate that the value is a number.
   */
    allowNumberOnly : false,
    /*
      @cfg {Record}. Reference to the selected record. Do not access this property directly.
      Use getSelectedRecord() method to access this value.
     */
    selectedRecord : undefined,
    /**
     * @cfg {String} Specify a string to pass as the configuration string for Ext.XTemplate. This will overwrite the default displayField
     * with a custom field which internally uses this displayTpl
     */
    displayTpl : undefined,
    /**
     * @cfg {boolean} Auto loads the store by calling initQuery when the value is set to true. Default Value - false.
     */
    autoLoad : false,
    /**
     * @cfg {boolean} This flag will force to use the Key in addition to the value field to find matching Record in the store. Default Value - false.
     */
    useKeyToFilterRecord : false,
    initComponent: function() {
        // Invoke the initComponent of the base class
        Jaffa.form.FinderComboBox.superclass.initComponent.call(this);
        
        // Dynamically load meta, if not provided in the config
        this.loadMeta();

        this.validatedRecords = new Ext.util.MixedCollection();
        this.validatedRecords.getByValue = function(v) {
          if (typeof this.map[v] != "undefined") return null;
          return this.map[v];
        };
        //Set custom display tpl.
        if(this.displayTpl && this.meta.fields && this.meta.finder && this.meta.finder.combo){
           Ext.apply(this.meta.fields,{
             _customDisplayField : {
               tpl : this.displayTpl
             }
           });
           this.meta.finder.combo.columns.push('_customDisplayField');
           if(this.meta.finder.combo.config) this.meta.finder.combo.config.displayField = '_customDisplayField';
        }
        this.addEvents(
            /**
             * @event autoselect
             * Fires when an item is selected automatically by the blur handler
             * @param {Jaffa.form.FinderComboBox} combo This combo box
             * @param {Array} items An array of matching items from the data element of the underlying store
             * @param {Ext.data.Record} gridRecord The record representing the row in the grid when combobox is used in grids.
             */
            'autoselect',
            /**
             * @event beforeautoselectquery
             * Fires before invoking the query for selecting an item automatically by the blur handler.
             * A listener may modify the query. To cancel the query, return a false.
             * @param {Jaffa.form.FinderComboBox} combo This combo box
             * @param {Object} baseParams The query to be modified.
             * @param (Boolean) exactQuery Indicates if an exactQuery is to be performed.
             */
            'beforeautoselectquery',
            /**
             * @event beforevalidationquery
             * Fires before invoking the query for validating an item automatically.
             * A listener may modify the query. To cancel the query, return a false.
             * This listener fires after beforeautoselectquery event. A typical usage of this event is to have queries for 
             * lookup grid to have broader search params than that of the validation query.   
             * @param {Jaffa.form.FinderComboBox} combo This combo box
             * @param {Object} baseParams The query to be modified.
             * @param (Boolean) exactQuery Indicates if an exactQuery is to be performed.
             */
            'beforevalidationquery',
            /**
             * @event load
             * Fires after a new set of Records has been loaded.
             * NOTE: If a listener updates the Store, then it may also need to fire the 'datachanged' event on that Store so that the associated View(s) get refreshed
             * @param {Ext.data.Store} store The Store for this combo box
             * @param {Ext.data.Record[]} records The Records that were loaded
             * @param {Object} options The loading options that were specified for the Store
             */
            'load'
        );
        
        // Apply all the properties declared in the meta object
        Ext.apply(this, this.meta.finder.combo.config);
        
        // Apply all the properties that may have been declared for the finderCombo
        Ext.apply(this, this.initialConfig);
        
        // Create or reuse a Store
        if (!this.store) {
            var storeId;
            if (this.shareStore && this.valueField==this.displayField) {
              // Note: when displayField != valueField, the record is needed to find value from display value.
              //       however, the record in store is reseted each time a new value is set, leading to 
              //       the confusion if the store is shared.
              storeId = this.determineStoreId(this.meta);
              this.store = Ext.StoreMgr.lookup(storeId);
            }
            if (!this.store) {
                try {
                    this.store = new Jaffa.data.FinderStore({
                        storeId: storeId,
                        meta: this.meta
                    });
                    if (this.listeners && this.listeners.load)
                      this.store.addListener('load', this.listeners.load);
                } catch (e) {
                    // do nothing
                    console.debug('Exception thrown during creation of Store for a Finder combo. This could happen when the DWR fragment is missing', e);
                }
            }
        }

        // determine the fieldLabel
        // disabling this functionality - let component set label.
        if (false && !this.fieldLabel) {
            var fieldName = this.displayField;
            var field = this.meta.fields[fieldName];
            this.fieldLabel = field.label;
        }

        this.on('expand', function(){
          // Fires before all queries are processed.
          // Will change the mode to 'local', if the Store is flagged as 'loaded' and forceRemoteQuery is set to false.
          // The combo will thus filter the data locally, without the need to fire remote events.
          this.addListener('beforequery', function(queryEvent) {
              if (queryEvent.combo.store.isLoaded && queryEvent.combo.mode != 'local'){
                if(queryEvent.combo.forceRemoteQuery)  {
                  //This is remove the cached lastQuery and force a remote query every time.
                  delete queryEvent.combo.lastQuery;
                } else {
                  queryEvent.combo.mode = 'local';
                }
              }
          });
        },this,{single : true})

        // Validates the value when the field is modified
        this.addListener('change', function(combo, newValue, oldValue,doNotAutoSelect) {
            console.debug('value changed: '+ newValue);
            if (doNotAutoSelect !== true && newValue && newValue.length > 0) {
                combo.doAutoselect(combo, true);
            } else {
                combo.isValueValid = true;
                combo.clearInvalid();
                combo.fireEvent('autoselect', combo, null);
            }
        });
        
        // The following setting ensures that the validateValue() isn't called during a blur event. Otherwise the error-marking on an invalid value was getting cleared
        // An alternative would be to code the validation logic on the validateValue() method
        // But we want the validation to take place only when the value is modified, and not on every blur.
        this.validateOnBlur = false;
        this.validationEvent = false;

        var psf = this.primarySearchField || (this.meta && this.meta.finder && this.meta.finder.primarySearchField) || this.valueField;
        if (this.meta.fields[psf]){
          var searchFieldMeta = this.meta.fields[psf];
          if (searchFieldMeta && (searchFieldMeta.type=='int'||searchFieldMeta.type=='float')){
            this.allowNumberOnly = true;
          }
        }

        this.on('autoselect', function(combo, items) {
          if (items && items.length==1) {
            this.fireEvent('select', combo, items[0], Number.NaN); // the index value does not exist because items may not be in the combo store.
          }
        }, this);
        this.on('select', function(combo, record, idx) {
          // make sure user selected value always be valid.
          this.isValueValid = true;
          this.selectedRecord = record;
        }, this);

        this.applyCriteriaFields(this.store.baseParams);
        if(this.autoLoad === true){
          this.setUpBaseParams();
          this.doQuery(this.getRawValue());
        }
    },
    
    /**
     * Intercepts the validation, passing the actual-value of the combo to the underlying validation routine, instead of the display-value.
     */
    validateValue: function(value) {
        var valid = Jaffa.form.FinderComboBox.superclass.validateValue.call(this, this.getValue()==null?'':this.getValue());
        return (valid && (this.isValueValid !== false))
    },

    /**
     * Searches the snapshot/data field of the Store for a Record having the input value for the valueField.
     * If a record does not exist, a new Record (that can be added to this widget's store) will be created.
     * However, there are a couple of limitations on the creation of the Record:
     * - The mapping for the valueField should reference a codeGraph. For example 'X.Y'. If the mapping is merely 'X', then the Record will not be created.
     * - This method assumes that the displayField, if it is a concatenation of 2 fields, will have the format 'X - Y'. Hence if the value of the 2nd field cannot be determined, the record will be ignoed.
     * This method will return a NULL if any error occurs.
     */
    findOrCreateRecord: function(graph, mapping, value) {
      if (value != null) {
        try {
          // Check for an existing record
          var record = this.findRecordInStore(this.valueField, value);
          if (!record) {
            // Record not found. Determine the code graph and ensure that it is not the same as the main graph
            var parentGraph = Jaffa.data.Util.getParent(graph, mapping);
            if (parentGraph != graph) {
              var records = this.getStore().reader.extractData([parentGraph], true);
              if (records && records.length > 0) {
                record =  records[0];
                record.set(this.valueField, value);
                // Ensure that the displayValue does not end with ' -'
                if (this.valueField != this.displayField) {
                  // evaluate dummy properties. however ensure that no events get fired on the store
                  this.getStore().suspendEvents();
                  this.getStore().evaluateDummyProperties(records);
                  this.getStore().resumeEvents();
                  var displayValue = record.get(this.displayField);
                  var suffix = ' -';
                  if (displayValue.length > suffix.length && displayValue.substring(displayValue.length - suffix.length) == suffix)
                    record = null;
                }
              }
            }
          }
          return record;
        } catch (ignore) {
        }
      }
      return null;
    },
    
    /**
     * Overrides the setValue() method of the base class.
     * Intercepts the passing of a Ext.data.Record object.
     * This object will be added to the underlying Store.
     * The combo will then be initialized to the valueField.
     * This will allow the combo to display the value-and-description without retrieving any data from the server.
     * If just the value is passed, then the doAutoselect() will be invoked to obtain more details.
     * @param {Ext.data.Record/String} v The value.
     * @param {boolean} doNotAutoselect If true, and if the input value is not a Record, then the doAutoselect() will not be invoked to obtain more details. Use this only when not wanting additional details.
     */
      setValue: function(v, doNotAutoselect) {
        if (v instanceof Ext.data.Record) {
          var rec = this.findRecord(this.valueField, v.data[this.valueField], v);
          if (!rec){
            this.store.add([v]);
          }
          var displayText = (rec || v).data[this.displayField] ||  this.valueNotFoundText ;
          v = v.get(this.valueField);
          if (v === this.getValue() && (v === null || v === ''))
            return;
          this.value = v;
          Jaffa.form.FinderComboBox.superclass.setValue.call(this, v, displayText);
        } else {
          if  (v === null || v === ''){
            //Bypassing overridden setValue method (see ext-overrides#setValue) that will load store remotely. In the case of no value remote load is not required.
            Ext.form.ComboBox.superclass.setValue.call(this, v);
          } else {
            if(!doNotAutoselect){
              this.fireEvent('beforeautoselectquery', this, this.store.baseParams, false); //The before autoselect query should be called before the super.setValue otherwise the setValue will load the store
            }
            Jaffa.form.FinderComboBox.superclass.setValue.call(this, v);
            if (!doNotAutoselect){
              this.doAutoselect(this, true);
            }
          }
        }

        // Even though the actual-value is passed in the earlier call to setValue(),
        // Combo.setValue() passes the display-value to Field.setValue(), which in turn invokes the validate() method.
        // The Field.validate() method fails since it validates the display-value.
        // Finally the Combo.setValue() sets the this.value field with the actual-value.
        // The following repeat call to validate() is necessary since it'll validate the actual-value.
        if (this.rendered && !this.textOnly)
          this.validate();

    },
    
    setRawValue: function(v, doNotAutoselect) {
        if (v instanceof Ext.data.Record) {
            if (!this.findRecord(this.valueField, v.data[this.valueField])) {
              this.store.add([v]);
            }
            v = v.get(this.valueField);
            Jaffa.form.FinderComboBox.superclass.setRawValue.call(this, v);
        } else {
            // Changed to use the Raw from the base class...don't know if this was an earlier oversight?
            Jaffa.form.FinderComboBox.superclass.setRawValue.call(this, v);
            // ** REMOVED, seems to cause resursion
            //if (!doNotAutoselect) 
            //  this.doAutoselect(this, true);
          }
    },

    // private
    initValue : function(){
        this.originalValue = this.getValue();
        Jaffa.form.FinderComboBox.superclass.initValue.call(this);
    },

    /**
     * This is a helper function to recreate a new Store for this widget.
     * @param {Object} baseParams Additional parameters to be passed to the Finder component.
     */
    recreateStore: function(baseParams) {
        var store = new Jaffa.data.FinderStore({
            meta: this.meta,
            maxRecords: this.meta.finder.combo.maxRecords,
            baseParams: baseParams
        });
        if (store.listeners && store.listeners.load)
          store.addListener('load', store.listeners.load);
        if (this.listeners && this.listeners.load)
          store.addListener('load', this.listeners.load);
        this.bindStore(store, false);
        this.lastQuery = null;
        this.mode = this.meta.finder.combo.config.mode;
    },

    /**
     * Returns true if this field has been changed since it was originally loaded and is not disabled.
     * This overrides the method in Field.js, since that always returns true, when a combo contains a valueField.
     * The combo, if it contains a valueField, returns an empty String instead of an undefined value.
     */
    isDirty: function() {
        if(this.disabled) {
            return false;
        }
        if (this.valueField) {
            // The following line is adapted from Combo.getValue(); treat the originalValue field similar to the value field
            var original = typeof this.originalValue != 'undefined' ? this.originalValue : '';
            
            // The following line is adapted from Field.isDirty(); compare getValue() against the original variable
            return String(this.getValue()) !== String(original);
        } else
            return Jaffa.form.FinderComboBox.superclass.isDirty.call(this);
    },

    /* Creates a storeId based on the input */
    determineStoreId: function(meta) {
        var storeId = this.meta.finder.DWRFunctionName;
        if (this.meta.finder.DWRFunctionInput)
            storeId += '_' + this.meta.finder.DWRFunctionInput;
        if (this.meta.finder.orderByFields)
            storeId += '_orderByFields:' + this.meta.finder.orderByFields;
        if (this.meta.finder.combo.maxRecords)
            storeId += '_maxRecords:' + this.meta.finder.combo.maxRecords;
        return storeId;
    },

    // Searches the snapshot/data field of the Store for a Record having the input value for the given property.
    // This function is similar to the findRecord() function of the Store class
    // The only difference is that it searches the 'snapshot' field, if available, instead of just the 'data' field of the Store
    findRecordInStore: function(prop, value) {
        var record;
        if ((this.store.snapshot && this.store.snapshot.length > 0) || this.store.data.length > 0) {
            var fn = function(r) {
                if (r.data[prop] === value) {
                    record = r;
                    return false;
                }
            };
            this.store.snapshot ? this.store.snapshot.each(fn) : this.store.data.each(fn);
        }
        return record;
    },

    // Retrieves records that match the current data. AutoSelects a record if only one is returned.
    // The field is marked as invalid if no record or multiple records are retrieved.
    doAutoselect: function(combo, exactQuery) {

      if (!Ext.isNumber(Number(combo.getValue())) && this.allowNumberOnly){
        this.markInvalid(this.invalidNumberText);
        this.isValueValid = false;
        return;
      }

        if (! combo.store) return;
        this.selectedRecord = null; //Resetting before auto selecting a record;
        var value = combo.getValue();
        
        if (value != null && (value.length > 0 || value.toString().length > 0) ) { 
            // check if the value already exists in the Store
            var record = combo.findRecordInStore(combo.valueField, value);
            if (record == null) {
              record = combo.validatedRecords.getByValue(value);
            }
            
            if (record == null) {
              // See if we need to do a query to validate the value entered
              if (combo.validateStoreOnly) {
                // Value failed validation
                combo.isValueValid = false;
                combo.invalidateNotFound(value, combo.getGridRecord());
              } else {
                var baseParams = eval('({findTotalRecords: false, ' + combo.valueField + ': {operator: "' + (exactQuery ? 'Equals' : 'BeginsWith') + '", values: [value]}})');
                if(!this.staticBaseParams && this.getStaticBaseParams()){
                    this.staticBaseParams = this.getStaticBaseParams();
                }
                if(this.staticBaseParams){
                  Ext.applyIf(baseParams,this.staticBaseParams);
                }
                this.applyCriteriaFields(this.store.baseParams);
                if (combo.fireEvent('beforeautoselectquery', combo, baseParams, exactQuery) !== false) {
                  // create a temporary Store, supplying criteria to limit the Records returned to match the input
                  if (combo.fireEvent('beforevalidationquery', combo, baseParams, exactQuery) !== false) {
                    combo.dwrSelect(value, baseParams, exactQuery, combo.getGridRecord());
                    // No state set, as this is delegated to the above process that is now async.
                  }
                }
              }
            } else {
                // Value passed validation
                combo.clearInvalid();
                combo.isValueValid = true;
                combo.setValue(record);
                combo.fireEvent('autoselect', combo, [record], combo.getGridRecord());
            }
        }
    }
    
  ,getGridRecord: function() {
    if (this.editor) {
      return this.editor.record;
    }
    return null;
  }
  
  ,onRender: function(ct, position) {
    Jaffa.form.FinderComboBox.superclass.onRender.call(this, ct, position);
  }
  
  ,loadARecord: function(exactValue, baseParams, callback, gridRecord) {
    if (baseParams[this.valueField].operator) {
      // make it exact query
      delete baseParams[this.valueField].operator;
    }
    baseParams[this.valueField].values=[exactValue];
    var store = new Jaffa.data.FinderStore({
      meta: this.meta,
      maxRecords: 2,
      baseParams: baseParams,
      listeners: {
        scope: this,
        load: function(store) {
          if (store.getCount() == 0) {
            console.debug('WARNING: ' + exactValue + ' not found through dwr.');
          } else {
            if (typeof callback == 'function') {
              callback.call(this,store);
              this.fireEvent('autoselect', this, store.getRange(), gridRecord);
            }
          }
        } 
      }   
    });
    if (store.listeners && store.listeners.load)
      store.addListener('load', store.listeners.load);
    if (this.listeners && this.listeners.load)
      store.addListener('load', this.listeners.load);
    store.load();
  }
  
  ,updateField: function(gridRecord, comboRecord) {
    if (gridRecord && (!combo.editor.editing || gridRecord != combo.editor.record)) {
      if (gridRecord.get(this.valueField)==comboRecord.get(this.valueField)) {
        this.parentGrid.getView().refreshRow(gridRecord);
      } else {
        gridRecord.set(this.editorRecordField, comboRecord.get(this.valueField));
      }
    } else {
      this.setValue(comboRecord);
    }
  }
  
  ,initQuery: function() {
    this.setUpBaseParams();
    if (this.isOnFocus === false) {
      // this ensures that the widget is still under focus when initiating the drop down. 
      // isOnFocus is defined in Jaffa.maintenance.plugins.Panel._setChangeListeners()
      // the consequence of not having this check: when user type in the value and tab out right way, 
      //     next time when user tab over this widget, it will show validation error if the widget is having 
      //     displayField != valueField. The reason is that when user type in and tabout right way, it makes two 
      //     remote calls. First is the autoselect, which brings back a record and added to the store. Next is
      //     this call to initialize the store and using rawValue of this widget. Because displayField!=valueField,
      //     the rawValue is not the real value; the store will be initiated with wrong records while the correct record
      //     from the first call will be wiped out. Because the display value lost its record backing, next time when user 
      //     tab over this field, it is going to validate with the display value, which results an error.
      return;
    }
    Jaffa.form.FinderComboBox.superclass.initQuery.call(this);
  }
  
  ,onTriggerClick: function() {
    if (this.disabled)
        return;

    // setup the baseParams
    this.setUpBaseParams();
    Jaffa.form.FinderComboBox.superclass.onTriggerClick.call(this);
  }
  
  ,setUpBaseParams: function() {
    if (! this.store.originalBaseParams) {
      this.store.originalBaseParams = this.store.baseParams;
    }
    this.store.baseParams = {};
    Ext.apply(this.store.baseParams, this.store.originalBaseParams);
    var staticParameters = this.staticBaseParams || this.getStaticBaseParams();
    if(staticParameters){
      Ext.applyIf(this.store.baseParams, staticParameters);
    }
    this.applyCriteriaFields(this.store.baseParams);
    this.fireEvent('beforeautoselectquery', this, this.store.baseParams, false);
    delete this.store.baseParams[this.valueField];    
  }

  ,applyCriteriaFields: function(baseParams){
    if (this.criteriaFields){
      this.forceRemoteQuery = true;
      var criteriaParams={};
      var criteriaFields = this.criteriaFields;
      if (criteriaFields.length > 0){
        for (var i=0; i < criteriaFields.length; i++){
          var valueFieldMapping = criteriaFields[i].split("=")[1];
          var valueFieldValue = '';

          var targetPanelField = this.ownerCt.find('mapping', valueFieldMapping)[0] || this.ownerCt.find('itemId', valueFieldMapping)[0] || this.ownerCt.find('mapping', 'flexBean.' + valueFieldMapping)[0];
          if(targetPanelField && targetPanelField.getValue()){
            valueFieldValue = targetPanelField.getValue();
          }
          if (valueFieldValue != ''){
            criteriaParams[criteriaFields[i].split("=")[0]]={
              values: [valueFieldValue],
              operator: 'Equals'
            };
          } else {
            delete baseParams[criteriaFields[i].split("=")[0]];
          }
        }
        Ext.apply(baseParams,criteriaParams);
      }
    }
    return baseParams;
  }

  /**
   * PROTECTED. The work horse of validating a value.
   * @param {Object} value The input value to be validated.
   * @param {Object} baseParams json object of the query parameters.
   * @param {Boolean} exactQuery Should the query be exact.
   * @param {@link Ext.data.Record} gridRecord The record in the grid that this combobox is editing.
   */
  ,dwrSelect: function(value, baseParams, exactQuery, gridRecord) {
    var metaToUse = this.metaForValidation ? this.metaForValidation : this.meta;
    var trackingNo = (new Date()).getTime();
    var store = new Jaffa.data.FinderStore({
      meta: metaToUse,
      maxRecords: 2,
      baseParams: baseParams,
      listeners: {
        scope: this,
        load: function(store) {
          console.debug(trackingNo, value+' dwr query load: exactQ='+exactQuery);
          this.validateRecordAfterLoad.call(this,store,value, baseParams, exactQuery, gridRecord,trackingNo);
          console.debug(trackingNo, 'exit at: '+(new Date()).getTime());
        }
      }
    });
    if (this.listeners && this.listeners.load)
      store.addListener('load', this.listeners.load);
    console.debug(trackingNo, 'enter Jaffa.form.FinderComboBox.dwrSelect');  
    store.load();
  },
  validateRecordAfterLoad :function(store,value, baseParams, exactQuery, gridRecord,trackingNo) {
    var gridNotEdit = gridRecord && (!this.editor.editing || gridRecord != this.editor.record);
    if (store.data.items.length == 0) {
      console.debug(value+' dwr query not found: exactQ='+exactQuery);
      if (exactQuery && store.fields.get(this.valueField).type=='string') {
        // this.isValueValid = false;
        // Perform wild-card query
        baseParams[this.valueField].operator = 'BeginsWith';
        this.dwrSelect(value, baseParams, false, gridRecord);
      } else if (!this.allowInvalid) {
        // report no record is found by invalidate the combobox.
        this.invalidateNotFound(value, gridRecord);
      }
    } else if (store.data.items.length == 1) {
      this.isValueValid = true;
      var rcd = new Ext.data.Record(store.data.items[0].data);
      if(store.data.items[0].json)
        rcd.json = store.data.items[0].json;
      console.debug(trackingNo, value+' dwr query found: exactQ='+exactQuery+' gridNotEdit: '+gridNotEdit);
      if (this.metaForValidation) {
        // validation uses different dwr finder
        // load the value to the main combo store
        var cbRec = this.findRecordInStore(this.valueField, rcd.get(this.valueField));
        if (cbRec == null) {
          // try to find the record from already validated record list
          cbRec = this.validatedRecords.getByValue(rcd.get(this.valueField));
        }
        if (cbRec == null) {
          // try to load the record
          this.loadARecord(rcd.get(this.valueField), baseParams, function(store) {
            if (store.getCount()==1) {
              var vRec = store.getAt(0);
              console.debug('dwrSelect.loadARecord', this.id, value, vRec);
              if (!this.validatedRecords.getByValue(value)) {
                this.validatedRecords.add(value, vRec);
              }
              if (!this.validatedRecords.getByValue(vRec.get(this.valueField))) {
                this.validatedRecords.add(vRec.get(this.valueField), vRec);
              }
              this.updateField(gridRecord, vRec);
              this.fireEvent('autoselect', this, store.data.items, gridRecord);
            }
          }, gridRecord);
        }
      } else {
        // validation was using the same dwr finder
        // update the combobox
        if (gridNotEdit) {
          if (!this.findRecordInStore(this.valueField, rcd.get(this.valueField))) {
            console.debug('dwrSelect.gridNotEdit', this.id, value, rcd);
            if (!this.validatedRecords.getByValue(value)) {
              this.validatedRecords.add(value, rcd);
            }
          }
          if (exactQuery) {
            this.parentGrid.getView().refreshRow(gridRecord);
          } else {
            gridRecord.set(this.editorRecordField, rcd.data[this.valueField]);
          }
        } else {
          console.debug('dwrSelect.gridNotEdit.false', this.id, value, rcd, this);
          if (!this.validatedRecords.getByValue(value)) {
            this.validatedRecords.add(value, rcd);
          }
          this.setValue(rcd);
        }
        this.fireEvent('autoselect', this, store.data.items, gridRecord);
      }
    } else {
      // this ensures that the 'autoselect' is fired when the contents of the field changes from "xyz" -> erroneousValue -> "xyz"
      // combo.lastSelectionText = null;
      console.debug(trackingNo, value+' dwr query multiple found: exactQ='+exactQuery);
      if (!this.allowMultiple || !exactQuery) {
        this.invalidValues.add(value, true);
        this.markInvalid(this.invalidateMultipleRecordsFoundText);
        if (gridNotEdit) {
          this.parentGrid.getView().refreshRow(gridRecord);
        } else {
          this.isValueValid = false;
        }
      }else{
        this.isValueValid = true;
      }
    }
  }
  ,invalidateNotFound: function(value, gridRecord) {
    this.invalidValues.add(value, true);
    if (gridRecord && (!this.editor.editing || gridRecord != this.editor.record)) {
      // set again to kick off the invalid mark on grid
      this.parentGrid.getView().refreshRow(gridRecord);
    } else {
      this.markInvalid(this.invalidateNotFoundText);
      this.isValueValid = false;
    }
  }

  /** Dynamically load meta, if not provided in the config. */
  ,loadMeta: function () {
    // load meta
        // load meta
        if (!this.meta && this.foreignKeyInfo) {
            var jsonParams = JSON.parse(JSON.stringify(this.foreignKeyInfo));
            jsonParams["outputStyle"] = "JSON";
            var metaSourceJ = Ext.Ajax.synchronousRequest(
                {
                    url: 'js/extjs/jaffa/metadata/finderMetaData.jsp',
                    params: jsonParams
                });
            if (metaSourceJ) {
                var decodedMetaSource = Ext.decode(metaSourceJ);
                // The DWRFunctionName will be something like "Material_Core_TemporaryOutFinder.find".
                // The part before the "." is the service name.
                let dotIndex = decodedMetaSource.finder.DWRFunctionName.indexOf(".");
                var serviceName = decodedMetaSource.finder.DWRFunctionName.substring(0, dotIndex);
                var dto = new Jaffa.data.FinderOutDto(decodedMetaSource);
                var objectToApply = {};
                objectToApply[serviceName] = dto;

                Ext.apply(ClassMetaData, objectToApply);
                this.meta = ClassMetaData[serviceName];
            }
        }
    // load the supporting DWR script
    if (this.meta && this.meta.finder.DWRFunctionName) {
      try {
        eval(this.meta.finder.DWRFunctionName);
      } catch (ex) {
        // remove the function from the DWRFunctionName to obtain the serviceName
        var serviceName = this.meta.finder.DWRFunctionName;
        var i = serviceName.indexOf('.');
        if (i > 0)
          serviceName = serviceName.substring(0, i);
        var dwrSource = Ext.Ajax.synchronousRequest({url: 'dwr/interface/' + serviceName + '.js'});
        if (dwrSource) {
          //The following creates DWR function within the scope of 'this' object only. However, the DWR function needs to be globally accessible!
          //eval(dwrSource);

          //Create a <script> element and add it to the <head> element of the document
          var se = document.createElement("script");
          se.type="text/javascript";
          se.text = dwrSource;
          document.getElementsByTagName("head")[0].appendChild(se);
        }
      }
    }
  },
  getSelectedRecord : function(){
    if(!this.isValid() || (this.isValueValid === false) || Ext.isEmpty(this.getValue()) || Ext.isEmpty(this.getRawValue())){
      this.selectedRecord = null;
    } else if(!this.selectedRecord){
      this.selectedRecord = this.findRecord(this.valueField, this.getValue())
    }
    return this.selectedRecord;
  },
  clearInvalid : function() {
    Ext.form.ComboBox.prototype.clearInvalid.call(this);
    this.isValueValid = true;
    this.selectedRecord = null;
  }

});

Ext.reg('finderCombo', Jaffa.form.FinderComboBox);
