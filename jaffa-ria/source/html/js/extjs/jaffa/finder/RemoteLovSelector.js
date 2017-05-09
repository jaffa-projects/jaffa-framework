/**
 * @author seanz
 */

/**
 * @class Jaffa.finder.RemoteLovSelector
 * @extends Jaffa.finder.RemoteSelector
 * Handles remote validation of multiple values via Jaffa.data.FinderStore
 * @param {Object} config
 */
Jaffa.finder.RemoteLovSelector = Ext.extend(Jaffa.finder.RemoteSelector, {
  /**
   * Auto-select a list of records based on the input values. This method is called by change event from
   * the combobox. 'change' event is triggered by onBlur() when values have been changed.
   * @param {String} valueStr Input values to be verified
   */
  autoSelect : function(valueStr) {
    if (!(this.combo && valueStr)) return;
    var values = Jaffa.data.Util.strSetArray(valueStr, this.combo.separator);
    // exame the cache first
    var dvs = [], validValueMap = new Jaffa.util.RecordCache();
    for (var i=0; i<values.length; i++) {
      if (this.invalidValues.containsKey(values[i])) {
        if (this.invalidValues.get(values[i])) {
          this.invalidateComboNGrid(null, this.combo.invalidateNotFoundText);
        } else {
          this.invalidateComboNGrid(null, this.combo.invalidateMultipleRecordsFoundText);
        }
        return;
      } else if (this.validatedRecords.get(values[i])) {
        validValueMap.add(values[i], this.validatedRecords.get(values[i]));
      } else {
        // value cannot be determined by cached records
        dvs.push(values[i]);
      }
    }
    if (dvs.length > 0) {
      // call up remote service
      this.remoteAutoSelect(dvs, false, validValueMap);
    } else if (this.combo) {
      this.combo.handleCacheValidationSuccess(validValueMap);
    }
  },
  
  /**
   * Auto-validate a list of record based on the input values. This method is typically called to validate
   * a program set values on the combo, which does not need to fire 'autoselect' event if validation 
   * success. It also allow multiple records by a given value. The validation query is always exact (Equals). 
   * @param {String} valueStr
   */
  autoValidate : function(valueStr) {
    if (!(this.combo && valueStr)) return;
    var values = Jaffa.data.Util.strSetArray(valueStr, this.combo.separator);
    // exame the cache first
    var dvs = [];
    for (var i=0; i<values.length; i++) {
      if (this.invalidExactValues.contains(values[i])) {
        this.invalidateComboNGrid(null, this.combo.invalidateNotFoundText);
        return;
      } else if (! this.validatedRecords.containsKey(values[i]) && ! this.validExactValues.contains(values[i])){
        // value cannot be determined from cached values
        dvs.push(values[i]);
      }
    }
    if (dvs.length > 0) {
      // call up remote service
      this.remoteAutoSelect(dvs, true);
    } else if (this.combo) {
      this.combo.handleCacheValidationSuccess();
    }
  },
  
  autoGridSelect: function(valueArray, callback, scope) {
    this.remoteAutoSelect(valueArray, false, {callback: callback, scope: scope});
  },
  
  /**
   * PROTECTED. Remote-autoSelect a lsit of records based on the input values. 
   * @param {Array of String} values Input values to be verified
   * @param {boolean} validateOnly indicates whether it is just for validation without updates.
   * Return void.
   */
  remoteAutoSelect: function(values, validateOnly, extraParams){
    if (!this.combo || !this.combo.store || values == null || values == '' || values.length==0) 
      return;
    
    // perform remote validation
    var baseParams = Ext.ux.clone(this.combo.store.baseParams);
    if (this.combo.fireEvent('beforeautoselectquery', this.combo, baseParams, true) !== false) {
      baseParams.findTotalRecords = this.combo.allowMultiple ? true : false;
      baseParams[this.combo.valueField] = {
        values : values
      };
      if (values.length==1) {
        delete baseParams[this.combo.valueField].operator;
      } else {
        baseParams[this.combo.valueField].operator = 'In';
      }
      baseParams.maxRecords = this.combo.allowMultiple ? null : values.length*5;
      // create a temporary Store, supplying criteria to limit the Records returned to match the input
      this.dwrSelect(values, baseParams, this.combo.getGridRecord(), validateOnly, extraParams);
      // No state set, as this is delegated to the above process that is now async.
    }
  },
  
  /**
   * DWR callback function handling remote autoselect request.
   * @param {String} values
   * @param {@link Ext.data.Store} store
   * @param {@link Ext.data.Record} gridRecord
   * @param {@link Jaffa.util.RecordCache} validValueMap
   */
  dwrSelectCallback : function(values, store, gridRecord, validValueMap) {
    // examine the validation result
    var recValMap = this.groupRecords2values(store);
    for (var i=0; i<values.length; i++) {
      var recs = recValMap.getRecords(values[i]);
      if (recs==null || recs.length == 0) {
        // report no record is found by invalidate the combobox.
        this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
        this.invalidValues.add(values[i], true);
        return;
      } else if (recs.length>1 && !store.allowMultiple) {
        // mark invalid 
        this.invalidateComboNGrid(gridRecord, this.combo.invalidateMultipleRecordsFoundText);
        this.invalidValues.add(values[i], false);
        return;
      }
    }
    // validation passed
    if (this.metaForValidation) {
      // load combo records
      this.loadComboRecords(recValMap.keys, gridRecord, validValueMap);
    } else {
      this.remoteSuccess(recValMap, gridRecord, validValueMap);
    }
  },
  
  /**
   * PRIVATE method to be executed when validation is success.
   * @param {@link Jaffa.util.RecordCache} recValMap
   * @param {@link Ext.data.Record} gridRecord
   * @param {@link Jaffa.util.RecordCache} validValueMap
   */
  remoteSuccess : function(recValMap, gridRecord, validValueMap) {
    var values = recValMap.keys;
    for (var i = 0; i < values.length; i++) {
      var rcds = [], recs=recValMap.get(values[i]);
      for (var j=0; j<recs.length; j++) {
        // 1. regenerate the record to allow properties that are not defined in ReocrdType to be carried over.
        // 2. use Ext.data.Record is to decouple the store so that when the validation/selection 
        // method is finished, the store can be garbage collected.
        rcds.push(new Ext.data.Record(recs[j].data));
      }
      this.validatedRecords.setRecords(values[i], rcds);
      validValueMap.setRecords(values[i], rcds);
    }
    
    // update the combo store records
    this.updateComboStoreRecords(validValueMap, gridRecord);
    
    // there is no need to update the grid cell and combo field
    this.combo.isValueValid = true;
    this.combo.validate();
    this.combo.fireEvent('autoselect', this.combo, validValueMap, gridRecord);
  },
  
  /**
   * PRIVATE To find the records using a finder same to what combo box store use. Executed when remote select/validation
   * uses different finder from what combo box store uses.
   * @param {Object} exactValue
   * @param {Object} gridRecord
   * @param {@link Jaffa.util.RecordCache} validValueMap
   */
  loadComboRecords : function(exactValues, gridRecord, validValueMap){
    if (this.combo == null) return;
    var baseParams = Ext.ux.clone(this.combo.store.baseParams);
    if (this.combo.fireEvent('beforeautoselectquery', this.combo, baseParams, true) !== false) {
      // 1. construct base params for query
      baseParams.findTotalRecords = this.combo.allowMultiple ? true : false;
      if (exactValues.length==0) {
        baseParams[this.combo.valueField] = {
          values : [value]
        };
        // delete operator to make the query exact
        delete baseParams[this.valueField].operator;
      } else {
        baseParams[this.combo.valueField] = {
          operator : 'In',
          values : [value]
        };
      }
      // 2. construct a store similar to combo store for query
      var store = new Jaffa.data.FinderStore({
        meta: this.comboMeta,
        maxRecords: this.combo.allowMultiple ? null : 2,
        baseParams: baseParams,
        listeners: {
          scope: this,
          load: function(store) {
            var recValMap = this.groupRecords2values(store);
            for (var i=0; i<exactValues.length; i++) {
              var rs = recValMap.getRecords(exactValues[i]);
              if (rs.length==0) {
                console.debug('ERROR: ' + exactValues[i] + ' not found record through dwr.');
                this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
                this.invalidValues.add(exactValues[i], true);
                return;
              }
            }
            this.remoteSuccess(recValMap, gridRecord, validValueMap);
          } 
        }   
      });
      // 3. initiate query
      store.load();
    }
  },
  
  /**
   * DWR callback function handling remote validation request.
   * @param {Object} values
   * @param {Object} store
   * @param {Object} gridRecord
   */
  dwrValidateCallback : function(values, store, gridRecord) {
    var recValMap = this.groupRecords2values(store);
    for (var i=0; i<values.length; i++) {
      var rs = recValMap.getRecords(values[i]);
      if (rs==null || rs.length==0) {
        // query returned no records
        this.invalidExactValues.add(values[i]);
        this.invalidateComboNGrid(gridRecord, this.combo.invalidateNotFoundText);
        return;
      }
      this.validExactValues.add(values[i]);
    }
    
    // set validation
    this.combo.isValueValid = true;
    this.combo.validate();
    // update the combo store records
    this.updateComboStoreRecords(recValMap, gridRecord);
  },
  
  updateComboStoreRecords : function(validValueMap, gridRecord) {
    if (this.gridNotEdit(gridRecord)) {
      if (! this.combo.gridRec2ComboRecMap) {
        this.combo.gridRec2ComboRecMap = new Ext.util.MixedCollection();
      }
      this.combo.gridRec2ComboRecMap.add(gridRecord, validValueMap);
    } else {
      // the combo is in a panel or in a grid while editing the cell to be validated
      // add the results to the combo store and set the record selected if it is single
      var recs = [];
      validValueMap.eachKey(function(key) {
        var recs = validValueMap.getRecords(key);
        if (recs.length==1) {
          recs[0].set(this.combo.checkField, true);
        }
        // add records into the combo store
        if (!this.combo.findRecord(this.valueField, recs[0].data[this.valueField])) {
          for (var i=0; i<recs.length; i++) 
            this.combo.store.add(recs[i]);
        } else if (recs.length==1) {
          // set record to checked so that it will be used to get displayValue
          this.combo.findRecord(this.valueField, recs[0].data[this.valueField]).set(this.combo.checkField, true);
        }
      }, this);
    }
  },
  
  /**
   * Group the records in a store according to its value from value field of the combobox
   * @param {@link Ext.data.Store} store
   */
  groupRecords2values : function(store) {
    var oo = new Jaffa.util.RecordCache();
    store.each(function(rec) {
      var rs = oo.getRecords(rec.get(this.valueField));
      if (rs == null) {
        rs = [];
        oo.setRecords(rec.get(this.valueField), rs);
      }
      rs.push(rec);
    }, this);
    return oo;
  }
  
});

