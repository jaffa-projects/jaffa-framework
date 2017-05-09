/**
 * @class Jaffa.finder.RemoteSelector
 * @extends Ext.util.Observable
 * @author seanz
 * Handles remote validation via Jaffa.data.FinderStore
 * @param {Object} config
 */
Jaffa.finder.RemoteSelector = function(combo){
  Jaffa.finder.RemoteSelector.superclass.constructor.call(this);
  
  Ext.apply(this, {
    combo : combo,
    metaForValidation : combo.metaForValidation,
    comboMeta : combo.meta,
    valueField : combo.valueField,
    validatedRecords : new Jaffa.util.RecordCache(),
    invalidValues : new Ext.util.MixedCollection(),
    validExactValues : new Jaffa.util.HashSet(),
    invalidExactValues : new Jaffa.util.HashSet()
  });
  
  this.addEvents(
    /**
     * @event beforedestroy
     * Fires before the component is destroyed. Return false to stop the destroy.
     * @param {Ext.Component} this
     */
    'beforedestroy',
    /**
     * @event destroy
     * Fires after the component is destroyed.
     * @param {Ext.Component} this
     */
    'destroy'
  );
};
Ext.extend(Jaffa.finder.RemoteSelector, Ext.util.Observable, {
  /**
   * @cfg {Jaffa.form.JaffaComboBox} combo (required)
   */
  
  /**
   * PRIVATE property to cache validated combo records from autoselect calls.
   */
  validatedRecords : null,
  
  /**
   * PRIVATE property to cache the invalid values from autoselect calls.
   */
  invalidValues : null,
  
  /**
   * PRIVATE property to cache the valid exact query values from validation only calls
   */
  validExactValues : null,
  
  /**
   * PRIVATE property to cache the invalid exact query values from validation only calls
   */
  invalidExactValues : null,
  
  /**
   * Auto-select a record based on the input value. This method is called by change event from
   * the combobox. 'change' event is triggered by onBlur() when value has been changed.
   * @param {String} value Input value to be verified
   */
  autoSelect : function(value) {
    // exame the cache first
    var recs = this.validatedRecords.get(value);
    if (recs) {
      if (Ext.isArray(recs)) {
        this.remoteSuccessMultiRecords(recs);
      } else {
        this.remoteSuccessSingleRecord(value, recs);
      }
    } else if (this.invalidValues.containsKey(value)) {
      if (this.invalidValues.get(value)) {
        this.invalidateComboNGrid(null, this.combo.invalidateNotFoundText);
      } else {
        this.invalidateComboNGrid(null, this.combo.invalidateMultipleRecordsFoundText);
      }
    } else {
      // call up remote service
      this.remoteAutoSelect(value, false);
    }
  },
  
  /**
   * Auto-validate a record based on the input value. This method is typically called to validate
   * a program set value on the combo, which does not need to fire 'autoselect' event if validation 
   * success. It also allow multiple records by a given value. The validation query is always exact (Equals). 
   * @param {String} value
   */
  autoValidate : function(value) {
    // exame the cache first
    if (this.validatedRecords.containsKey(value) || this.validExactValues.contains(value)) {
      return;
    } else if (this.invalidExactValues.contains(value)) {
      this.invalidateComboNGrid(null, this.combo.invalidateNotFoundText);
    } else {
      // call up remote service
      this.remoteAutoSelect(value, true);
    }
    
  },
  
  /**
   * PROTECTED. Remote-autoSelect a record based on the input value. 
   * @param {String} value Input value to be verified
   * @param {boolean} validateOnly indicates whether it is just for validation without updates.
   */
  remoteAutoSelect: function(value, validateOnly){
    if (!this.combo || !this.combo.store || value == null || value == '') 
      return;
    
    // perform remote validation
    var baseParams = Ext.ux.clone(this.combo.store.baseParams);
    if (this.combo.fireEvent('beforeautoselectquery', this.combo, baseParams, true) !== false) {
      baseParams.findTotalRecords = this.combo.allowMultiple ? true : false;
      baseParams[this.combo.valueField] = {
        values : [value]
      };
      delete baseParams[this.combo.valueField].operator;
      // create a temporary Store, supplying criteria to limit the Records returned to match the input
      baseParams.maxRecords = this.combo.allowMultiple ? null : 2;
      this.dwrSelect(value, baseParams, this.combo.getGridRecord(), validateOnly, null);
      // No state set, as this is delegated to the above process that is now async.
    }
  },
  
  /**
   * PROTECTED. The work horse of validating a value. 
   * @param {String} value The input value to be validated.
   * @param {Object} baseParams json object of the query parameters.
   * @param {@link Ext.data.Record} gridRecord The record in the grid that this combobox is editing.
   * @param {boolean} validateOnly indicates whether it is just for validation without updates.
   * @param {Object} extraParams an extra parameters object to pass along the remote call chain.
   */
  dwrSelect: function(value, baseParams, gridRecord, validateOnly, extraParams) {
    var trackingNo = (new Date()).getTime();
    var store = new Jaffa.data.FinderStore({
      meta : this.metaForValidation ? this.metaForValidation : this.comboMeta,
      baseParams : baseParams,
      exactQuery : true,
      allowMultiple : this.combo.allowMultiple,
      listeners : {
        scope : this,
        load : function(store){
          if (this.combo == null) return;
          console.debug(trackingNo, value + ' dwr query load: exactQ=' + store.exactQuery);
          if (extraParams && extraParams.callback && typeof extraParams.callback == 'function') {
            extraParams.callback.call(extraParams.scope?extraParams.scope:store, store, value, gridRecord);
          } else if (validateOnly) {
            this.dwrValidateCallback(value, store, gridRecord, extraParams);
          } else {
            this.dwrSelectCallback(value, store, gridRecord, extraParams);
          }
          console.debug(trackingNo, 'exit at: ' + (new Date()).getTime());
        }
      }
    });
    console.debug(trackingNo, 'enter Jaffa.form.FinderComboBox.dwrSelect');
    store.load();
  },
  
  /**
   * DWR callback function handling remote autoselect request.
   * @param {String} value
   * @param {@link Ext.data.Store} store
   * @param {@link Ext.data.Record} gridRecord
   */
  dwrSelectCallback : function(value, store, gridRecord) {
    if (store.data.items.length == 0) {
      // query returned no records
      if (store.exactQuery && store.fields.get(this.valueField).type == 'string') {
        // this.isValueValid = false;
        // Perform wild-card query
        store.baseParams[this.valueField].operator = 'BeginsWith';
        store.exactQuery = false;
        store.load();
      } else {
        // report no record is found by invalidate the combobox.
        this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
        this.invalidValues.add(value, true);
      }
    } else if (store.data.items.length == 1) {
      // query returned one record
      if (this.metaForValidation) {
        // validation uses different dwr finder
        // try to find the record from cache of validated records list
        var recValue = store.getAt(0).get(this.valueField);
        var cbRec = this.validatedRecords.getRecords(recValue);
        if (cbRec == null) {
          // try to load the record
          this.loadComboRecords(recValue, gridRecord);
        }
      } else {
        // validation uses the same dwr finder as what combo store uses
        this.remoteSuccessSingleRecord(value, store.getAt(0), gridRecord);
      }
    } else if (store.allowMultiple && store.exactQuery) {
      if (this.metaForValidation) {
        // validation uses different dwr finder
        // try to find the record from cache of validated records list
        var cbRec = this.validatedRecords.getRecords(value);
        if (cbRec == null) {
          // try to load the record
          this.loadComboRecords(value, gridRecord);
        }
      } else {
        // validation uses the same dwr finder as what combo store uses
        this.remoteSuccessMultiRecords(store.getRange(), gridRecord);
      }
    } else {
      // mark invalid 
      this.invalidateComboNGrid(gridRecord, this.combo.invalidateMultipleRecordsFoundText);
      this.invalidValues.add(value, false);
    }
  },
  
  /**
   * PRIVATE method to be executed when validation is success and only single record is found.
   * @param {String} value
   * @param {@link Ext.data.Record} rec
   * @param {@link Ext.data.Record} gridRecord
   */
  remoteSuccessSingleRecord : function(value, rec, gridRecord) {
    if (this.combo==null) return;
    // regenerate the record to allow properties that are not defined in ReocrdType to be carried over.
    var rcd = new Ext.data.Record(rec.data);
    this.validatedRecords.setRecords(value, rcd, rcd.get(this.valueField));
    if (this.gridNotEdit(gridRecord)) {
      this.updateOffFocusGridCell(gridRecord, rcd);
    } else {
      this.combo.setValue(rcd);
    }
    this.combo.isValueValid = true;
    // this.combo.validate() is executed in this.combo.setValue()
    this.combo.fireEvent('autoselect', this.combo, [rcd], gridRecord);
  },
  
  /**
   * PRIVATE method to be executed when validation is success and multiple records are found.
   * @param {Array of Ext.data.Record} recs
   * @param {@link Ext.data.Record} gridRecord
   */
  remoteSuccessMultiRecords : function(recs, gridRecord) {
    if (this.combo==null) return;
    // regenerate the record to allow properties that are not defined in ReocrdType to be carried over.
    var rcds = [];
    for (var i = 0; i < recs.length; i++) {
      rcds.push(new Ext.data.Record(recs[i].data));
    }
    this.validatedRecords.setRecords(rcds[0].get(this.valueField), rcds);
    // there is no need to update the grid cell and combo field
    this.combo.isValueValid = true;
    this.combo.validate();
    this.combo.fireEvent('autoselect', this.combo, rcds, gridRecord);
  },
  
  /**
   * PRIVATE To find the records using a finder same to what combo box store use. Executed when remote select/validation
   * uses different finder from what combo box store uses.
   * @param {String} exactValue
   * @param {@link Ext.data.Record} gridRecord
   */
  loadComboRecords : function(exactValue, gridRecord){
    if (this.combo == null) return;
    var baseParams = Ext.ux.clone(this.combo.store.baseParams);
    if (this.combo.fireEvent('beforeautoselectquery', this.combo, baseParams, true) !== false) {
      // 1. construct base params for query
      baseParams.findTotalRecords = this.combo.allowMultiple ? true : false;
      baseParams[this.combo.valueField] = {
        values : [exactValue]
      };
      // delete operator to make the query exact
      delete baseParams[this.valueField].operator;
      // 2. construct a store similar to combo store for query
      var store = new Jaffa.data.FinderStore({
        meta: this.comboMeta,
        maxRecords: this.combo.allowMultiple ? null : 2,
        baseParams: baseParams,
        listeners: {
          scope: this,
          load: function(store) {
            if (store.getCount() == 0) {
              console.debug('ERROR: ' + exactValue + ' not found record through dwr.');
              this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
              this.invalidValues.add(exactValue, true);
            } else if (store.getCount() == 1){
              this.remoteSuccessSingleRecord(exactValue, store.getAt(0), gridRecord);
            } else {
              this.remoteSuccessMultiRecords(store.getRange(), gridRecord);
              // console.debug('ERROR: ' + exactValue + ' returned multiple records through dwr.');
              // this.invalidValues.add(exactValue, false);
              // this.invalidateComboNGrid(gridRecord, this.combo.invalidateMultipleRecordsFoundText);
            }
          } 
        }   
      });
      // 3. initiate query
      store.load();
    }
  },
  
  /**
   * DWR callback function handling remote validation request.
   * @param {String} value
   * @param {@link Ext.data.Store} store
   * @param {@link Ext.data.Record} gridRecord
   */
  dwrValidateCallback : function(value, store, gridRecord) {
    if (store.getCount() == 0) {
      // query returned no records
      this.invalidExactValues.add(value);
      this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
    } else {
      // mark valid 
      this.combo.isValueValid = true;
      this.validExactValues.add(value);
      if (store.getCount()==1) {
        // setting the record to get displayValue shown correctly
        if (this.gridNotEdit(gridRecord)) {
          this.updateOffFocusGridCell(gridRecord, store.getAt(0));
        } else {
          this.combo.setValue(store.getAt(0));
        }
      }
      // this.combo.validate() is called in this.combo.setValue()
    }
  },
  
  /**
   * Test to find out whether the combo box is inside of a grid and the cell that the remote 
   * call originating is not under editing.
   * There are following senarios: 
   * 1. the combobox is in a panel
   * 2. the combobox is in a grid
   *    a. the combobox is editing the cell in the row of gridRecord
   *    b. the combobox is editing a cell not in the row of gridRecord
   *    c. the combobox is not in editing mode
   * This method returns true under 2.b or 2.c
   * @param {@link Ext.data.Record} gridRecord
   */
  gridNotEdit : function(gridRecord){
    return gridRecord && (gridRecord != this.combo.getGridRecord() || !this.combo.editor.editing);
  },
  
  updateOffFocusGridCell : function(gridRecord, record) {
    // add the record to the combobox to be used for getting display value by grid renderer.
    if (!this.combo.findRecord(this.valueField, record.get(this.valueField))) 
      this.combo.store.add(record);
    // combobox is in a grid but it is not editing the cell that the call was originating
    if (gridRecord.get(this.combo.editorRecordField) == record.data[this.valueField]) {
      // value not changed.
      this.combo.parentGrid.getView().refreshRow(gridRecord);
    } else {
      gridRecord.set(this.combo.editorRecordField, record.data[this.valueField]);
    }
  },
  
  /**
   * Mark invalid to the combobox and grid cell. 
   * @param {@link Ext.data.Record} gridRecord
   * @param {String} msg
   */
  invalidateComboNGrid: function(gridRecord, msg){
    this.combo.markInvalid(msg);
    if (this.gridNotEdit(gridRecord)) {
      // set again to kick off the invalid mark on grid
      this.combo.parentGrid.getView().refreshRow(gridRecord);
    } else {
      this.combo.isValueValid = false;
    }
  },

  destroy : function(){
    if(this.fireEvent("beforedestroy", this) !== false){
      this.beforeDestroy();
      this.onDestroy();
      this.fireEvent("destroy", this);
      this.purgeListeners();
    }
  },

	// private
  beforeDestroy : Ext.emptyFn,

	// private
  onDestroy  : function() {
    this.resetCache();
    this.combo = null;
  },

  /**
   * Reset all the caches. Expected to be called when the combobox is recreateStore() or reseted. 
   */
  resetCache : function() {
    this.validatedRecords.clear();
    this.invalidValues.clear();
    this.invalidExactValues.clear();
    this.validExactValues.clear();
  }
  
});

