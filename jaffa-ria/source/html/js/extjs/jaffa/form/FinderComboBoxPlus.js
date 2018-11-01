/**
 * @class Jaffa.form.FinderComboBoxPlus
 * @extends Ext.form.ComboBox
 * An extension to {@link Ext.form.ComboBox} for rendering the output returned by Jaffa's Finder components.
 */
Jaffa.form.FinderComboBoxPlus = Ext.extend(Ext.form.ComboBox, {
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
   * @cfg {boolean} shareStore true to share the store between components of the same store finder. Defaults to true.
   */
  shareStore: true,
  
  isValueValid: true,
  allowMultiple: false,
  
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
   * @cfg {boolean} validateStoreOnly true will force validation only against values in the store.
   * Only use this if you are forcing the store to be loaded in all cases. Otherwise typing a value directly and
   * then leaving the field will always report the value as invalid
   */
  validateStoreOnly: false,
  
  /**
   * PRIVATE property to point to the remote services for autoselect and remote validation.
   */
  remoteSelector : null,
  
  initComponent: function(){
    // Invoke the initComponent of the base class
    Jaffa.form.FinderComboBoxPlus.superclass.initComponent.call(this);
	
    // Dynamically load meta, if not provided in the config
    this.loadMeta();
    
    this.addEvents(    /**
     * @event autoselect
     * Fires when an item is selected automatically by the blur handler
     * @param {Jaffa.form.FinderComboBoxPlus} combo This combo box
     * @param {Array} items An array of matching items from the data element of the underlying store
     * @param {Ext.data.Record} gridRecord the record in the grid to be edited. null assumes the current record.
     */
    'autoselect',    /**
     * @event beforeautoselectquery
     * Fires before invoking the query for selecting an item automatically by the blur handler.
     * A listener may modify the query. To cancel the query, return a false.
     * @param {Jaffa.form.FinderComboBoxPlus} combo This combo box
     * @param {Object} baseParams The query to be modified.
     * @param (Boolean) exactQuery Indicates if an exactQuery is to be performed.
     */
    'beforeautoselectquery',    /**
     * @event load
     * Fires after a new set of Records has been loaded.
     * NOTE: If a listener updates the Store, then it may also need to fire the 'datachanged' event on that Store so that the associated View(s) get refreshed
     * @param {Ext.data.Store} store The Store for this combo box
     * @param {Ext.data.Record[]} records The Records that were loaded
     * @param {Object} options The loading options that were specified for the Store
     */
    'load');
    
    // Apply all the properties declared in the meta object
    Ext.apply(this, this.meta.finder.combo.config);
    
    // Apply all the properties that may have been declared for the finderCombo
    Ext.apply(this, this.initialConfig);
    
    // Create or reuse a Store
    if (!this.store) {
      var storeId;
      if (this.shareStore) {
        storeId = this.determineStoreId(this.meta);
        this.store = Ext.StoreMgr.lookup(storeId);
      }
      if (!this.store) {
        try {
          this.store = new Jaffa.data.FinderStore({
            storeId: storeId,
            meta: this.meta,
            maxRecords: this.meta.finder.combo.maxRecords
          });
          if (this.listeners && this.listeners.load) 
            this.store.addListener('load', this.listeners.load);
        } 
        catch (e) {
          // do nothing
          console.debug('Exception thrown during creation of Store for a Finder combo. This could happen when the DWR fragment is missing', e);
        }
      }
    }    
    this.loadRemoteSelector();
    
    // determine the fieldLabel
    // disabling this functionality - let component set label.
    if (false && !this.fieldLabel) {
      var fieldName = this.displayField;
      var field = this.meta.fields[fieldName];
      this.fieldLabel = field.label;
    }
    
    // Fires before all queries are processed.
    // Will change the mode to 'local', if the Store is flagged as 'loaded'. The combo will thus filter the data locally, without the need to fire remote events.
    this.addListener('beforequery', function(queryEvent){
      if (queryEvent.combo.store.isLoaded && queryEvent.combo.mode != 'local') 
        queryEvent.combo.mode = 'local';
    });
    
    // The following setting ensures that the validateValue() isn't called during a blur event. Otherwise the error-marking on an invalid value was getting cleared
    // An alternative would be to code the validation logic on the validateValue() method
    // But we want the validation to take place only when the value is modified, and not on every blur.
    this.validateOnBlur = false;
    this.validationEvent = false;
    
    // fire autoselect after user selects the record from the dropdown or popup grid (in JaffaComboGrid)
    this.wireEvents();
  },
  
  /**
   * Intercepts the validation, passing the actual-value of the combo to the underlying validation routine, instead of the display-value.
   */
  validateValue: function(value){
    // return false when this.isValueValid==false so that when doAutoValidate() failed from internal checking, 
    // this.validate() in setValue() does not revert the error marking.
    if (! this.isValueValid) return false;
    return Jaffa.form.FinderComboBoxPlus.superclass.validateValue.call(this, this.getValue() == null ? '' : this.getValue());
  },
  
  wireEvents: function(){
    // Validates the value when the field is modified
    this.addListener('change', function(combo, newValue, oldValue){
      console.debug('value changed: ' + newValue);
      if (newValue && newValue.length > 0) {
        if (combo.store) {
          if (this.validateStoreOnly) {
            if (! combo.findRecordInStore(combo.valueField, newValue)) {
              combo.isValueValid = false;
              combo.markInvalid(combo.invalidateNotFoundText);
            }
          } else {
            combo.remoteSelector.autoSelect(newValue);
          }
        }
      } else {
        combo.isValueValid = true;
        combo.clearInvalid();
        combo.fireEvent('autoselect', combo, null);
      }
    });
    
    /**
     * Wire up firing autoselect event after user selects the records from dropdown or popup grid.
     * Should be overwriten in multi-value combobox, in which autoselect should fire after the user
     * finalized the selections in the list of values. Alternative implementation is to use collapse event. 
     */
    this.on('select', function(combo, record, index){
      combo.fireEvent('autoselect', combo, [record], null);
      // make sure user selected value always be valid.
      this.isValueValid = true;
    }, this);
  },
  
  loadRemoteSelector : function() {
    if (this.store) {
      this.remoteSelector = new Jaffa.finder.RemoteSelector(this);
    }
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
  setValue: function(v, doNotAutoselect){
    if (v instanceof Ext.data.Record) {
      if (!this.findRecord(this.valueField, v.data[this.valueField])) 
        this.store.add(v);
      v = v.get(this.valueField);
      console.debug('setValue by record: ' + v);
      Jaffa.form.FinderComboBoxPlus.superclass.setValue.call(this, v);
    } else if (!doNotAutoselect) {
      console.debug('setValue: ' + v);
      Jaffa.form.FinderComboBoxPlus.superclass.setValue.call(this, v);
      this.doAutoValidate();
    } else {
      Jaffa.form.FinderComboBoxPlus.superclass.setValue.call(this, v);
    }
    
    // Even though the actual-value is passed in the earlier call to setValue(), 
    // Combo.setValue() passes the display-value to Field.setValue(), which in turn invokes the validate() method.
    // The Field.validate() method fails since it validates the display-value.
    // Finally the Combo.setValue() sets the this.value field with the actual-value.
    // The following repeat call to validate() is necessary since it'll validate the actual-value.
    if (this.rendered) 
      this.validate();
  },
  
  /**
   * Validate without firing autoselect. Allows multiple records for a value.
   * See distinction between autoSelect and autoValidate in Jaffa.finder.RemoteSelector
   */
  doAutoValidate : function() {
    if (this.store==null) return;
    var value = this.getValue();
    if (value != null && (value.length > 0 || value.toString().length > 0)) {
      var record = this.findRecord(this.valueField, this.getValue());
      if (! record) {
        if (this.validateStoreOnly) {
          this.isValueValid = false;
          this.markInvalid(this.invalidateNotFoundText);
        } else {
          this.remoteSelector.autoValidate(value);
        }
      }
    }
  },
  
  // private
  initValue: function(){
    this.originalValue = this.getValue();
    Jaffa.form.FinderComboBoxPlus.superclass.initValue.call(this);
  },
  
  /**
   * This is a helper function to recreate a new Store for this widget.
   * @param {Object} baseParams Additional parameters to be passed to the Finder component.
   */
  recreateStore: function(baseParams){
    var store = new Jaffa.data.FinderStore({
      meta: this.meta,
      maxRecords: this.meta.finder.combo.maxRecords,
      baseParams: baseParams
    });
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
  isDirty: function(){
    if (this.disabled) {
      return false;
    }
    if (this.valueField) {
      // The following line is adapted from Combo.getValue(); treat the originalValue field similar to the value field
      var original = typeof this.originalValue != 'undefined' ? this.originalValue : '';
      
      // The following line is adapted from Field.isDirty(); compare getValue() against the original variable
      return String(this.getValue()) !== String(original);
    } else 
      return Jaffa.form.FinderComboBoxPlus.superclass.isDirty.call(this);
  },
  
  /* This method sets the 'value' field with a modified dom-value. This will ensure that the 'change' listeners are fired. */
  beforeBlur: function(){
    if (this.valueField) {
      var displayValue = this.el.getValue();
      if (String(displayValue) !== String(this.startValue)) {
        var r = displayValue && this.displayField ? this.findRecordInStore(this.displayField, displayValue) : null;
        this.value = r != null ? r.data[this.valueField] : displayValue;
      }
    }
    Jaffa.form.FinderComboBoxPlus.superclass.beforeBlur.call(this);
  },
  
  /* Creates a storeId based on the input */
  determineStoreId: function(meta){
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
  findRecordInStore: function(prop, value){
    var record;
    if ((this.store.snapshot && this.store.snapshot.length > 0) || this.store.data.length > 0) {
      var fn = function(r){
        if (r.data[prop] == value) {
          record = r;
          return false;
        }
      };
      this.store.snapshot ? this.store.snapshot.each(fn) : this.store.data.each(fn);
    }
    return record;
  },
  
  getGridRecord: function(){
    if (this.editor) {
      return this.editor.record;
    }
    return null;
  },
  
  onRender: function(ct, position){
    // temporaray fix: proporgate uppercase rule from combobox to grid filter
    if (this.initialConfig.style) {
      if (this.initialConfig.style.indexOf('uppercase') >= 0) {
        this.meta.fields[this.valueField].caseType = 'UpperCase';
      }
    }
    Jaffa.form.FinderComboBoxPlus.superclass.onRender.call(this, ct, position);
  },
  
  initQuery: function(){
    this.setUpBaseParams();
    Jaffa.form.FinderComboBoxPlus.superclass.initQuery.call(this);
  },
  
  onTriggerClick: function(){
    if (this.disabled) 
      return;
    
    // setup the baseParams
    this.setUpBaseParams();
    Jaffa.form.FinderComboBoxPlus.superclass.onTriggerClick.call(this);
  },
  
  /** Dynamically load meta, if not provided in the config. */
  loadMeta: function () {
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
  
  setUpBaseParams: function(){
    if (!this.store.originalBaseParams) {
      this.store.originalBaseParams = Ext.ux.clone(this.store.baseParams);
    }
    this.fireEvent('beforeautoselectquery', this, this.store.baseParams, false);
    delete this.store.baseParams[this.valueField];
  },
  
  reset : function() {
    if (this.store) this.recreateStore(this.store.originalBaseParams || this.store.baseParams);
    this.remoteSelector.resetCache();
    Jaffa.form.FinderComboBoxPlus.superclass.reset.call(this);
  },
  
  onDestroy : function() {
    this.remoteSelector.destroy();
    this.remoteSelector = null;
    Jaffa.form.FinderComboBoxPlus.superclass.onDestroy.call(this);
  }
});

Ext.reg('finderComboPlus', Jaffa.form.FinderComboBoxPlus);
