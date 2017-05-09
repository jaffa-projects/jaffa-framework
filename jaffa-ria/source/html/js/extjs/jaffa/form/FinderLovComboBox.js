/**
 * Adapted from Ext.ux.form.LovCombo, List of Values Combo
 *
 * @author    Sean Zhou
 * @date      Nov. 6, 2008
 */

/**
 *
 * @class Jaffa.form.FinderLovComboBox
 * @extends Jaffa.form.FinderComboBoxPlus
 */
Jaffa.form.FinderLovComboBox = Ext.extend(Jaffa.form.FinderComboBoxPlus, {

  // {{{
  // configuration options
  /**
   * @cfg {String} checkField name of field used to store checked state.
   * It is automatically added to existing fields.
   * Change it only if it collides with your normal field.
   */
  checkField: 'checked'  
  
  /**
   * @cfg {String} separator separator to use between values and texts
   */  
  ,separator: ','
  
  /**
   * @cfg {String} separator separator to use between values and texts
   */  
  ,lastAutoselectValue: null
  
  /**
   * {@link Ext.util.MixedCollection} <{@link Ext.data.Record}, [{@link Ext.data.Record}]> 
   * private property to cache the records in store according to the grid record when the 
   * combo is inside of a grid. Map('grid record', ['combo record'])
   */
  ,gridRec2ComboRecMap: null
  
  /**
   * @cfg {Object} targetGridCfg with fields:
   *  grid {Ext.grid.GridPanel}, the target grid required
   *  addRecords {function} (optional) to run the operation with input of an 
   *                   array of selected Ext.data.Record. The scope of this function will be set to 
   *                   the targetGrid.
   *  targetField {String} (optional) the record field on the target grid to be populated. Used when
   *                   addRecords() is not defined.
   */
  ,targetGridCfg: null
  
  /**
   * @cfg {String/Array} tpl Template for items.
   * Change it only if you know what you are doing.
   */
  // }}}
  // {{{
  ,initComponent: function(){
  
    // call parent
    Jaffa.form.FinderLovComboBox.superclass.initComponent.apply(this, arguments);
    
    // template with checkbox
    if (!this.tpl) {
      this.tpl = '<tpl for=".">' + '<div class="x-combo-list-item">' + '<img src="' +
        Ext.BLANK_IMAGE_URL + '" ' + 'class="ux-lovcombo-icon ux-lovcombo-icon-' +
        '{[values.' + this.checkField + '?"checked":"unchecked"' + ']}">' +
        '<div class="ux-lovcombo-item-text">{' + (this.displayField || 'text') +
        '}</div>' + '</div>' + '</tpl>';
    }
    
    // remove selection from input field
    this.onLoad = this.onLoad.createSequence(function(){
      if (this.el) {
        var v = this.el.dom.value;
        this.el.dom.value = '';
        this.el.dom.value = v;
      }
    });
    
  } // e/o function initComponent
  // }}}
  // {{{
  ,loadRemoteSelector : function() {
    if (this.store) {
      this.remoteSelector = new Jaffa.finder.RemoteLovSelector(this);
    }
  }
  // }}}
  // {{{
  /**
   * Overrides the superclass implementation on event handling.
   * @protected
   */
  ,wireEvents : function(){
    // Validates the value when the field is modified
    this.addListener('change', function(combo, newValue, oldValue){
      console.debug('value changed: ' + newValue);
      if (newValue && newValue.length > 0) {
        if (combo.store) {
          if (this.validateStoreOnly) {
            // TODO: needs to be remodeled completely this part.
            alert('feature needs to be implemented');
            return;
            if (! combo.findRecordInStore(combo.valueField, newValue)) {
              combo.isValueValid = false;
              combo.markInvalid(combo.invalidateNotFoundText);
            } else {
              this.handleCacheValidationSuccess(new Jaffa.util.RecordCache());
            }
          } else {
            combo.remoteSelector.autoSelect(newValue);
          }
        }
      } else {
        this.handleCacheValidationSuccess(new Jaffa.util.RecordCache());
      }
    });

    this.on('collapse', this.userSelectionHandler, this, this.getCheckedValue());

    this.on('expand', function(combo) {
      // set the checked fields to records in store
      var synchStoreWithComboValue = function(store) {
        if (this.store && this.store.getCount()>0 && 
            !(this.store.reader.jsonData && 
            this.store.reader.jsonData.moreRecordsExist)) 
        {
          this.synchValueWithSelect(this.getRawValue());
        }
        this.store.un('load', synchStoreWithComboValue, this);
      };
      this.store.on('load', synchStoreWithComboValue, this);
    }, this);
  } // e/o function wireEvents
  // }}}
  // {{{
  ,userSelectionHandler : function(values) {
    // set combo value by the checked records in the list
    this.synchValueWithSelect(values);
    // gather all the checked records
    var recMap = new Jaffa.util.RecordCache();
    this.store.each(function(rec) {
      if(rec.get(this.checkField)) {
        var v = recMap.getRecords(rec.get(this.valueField));
        if (! v) {
          v = [];
          recMap.setRecords(rec.get(this.valueField), v);
        }
        v.push(rec);
      }
    }, this);
    
    // If a target grid is defined, following code populate the selected records to the target grid. 
    // It requires targetGridCfg
    if (this.targetGridCfg) {
      if (typeof this.targetGridCfg.addRecords == 'function') {
        var recs = [];
        recMap.eachKey(function(k, v) {
          for (var i=0; i<v.length; i++) {
            recs.push(v[i]);
          }
        });
        this.targetGridCfg.addRecords.call(this.targetGridCfg.grid, recs);
      } else if (this.targetGridCfg.grid){
        var targetField = this.targetGridCfg.targetField || this.valueField;
        // populate with default algorithm
        recMap.eachKey(function(k, v) {
          var store = this.targetGridCfg.grid.getStore();
          if (store.findExact(targetField, k) < 0) {
            for (var i=0; i<v.length; i++) {
              var r = new store.recordType();
              r.set(targetField, v[i].get(this.valueField));
              store.add(r);
            }
          }
        }, this);
      } else {
        alert('targetGridCfg needs to be defined.');
      }
      this.clearValue();
    }

    // fire 'autoselect' event
    this.fireEvent('autoselect', this, this, recMap, this.getGridRecord());
  }
  // }}}
  // {{{
  ,checkTab : function (me, e) {
    // no blur event by 'tabbing out' when list is expanded.
    if (e.getKey() == e.TAB && this.isExpanded()) return;
    Jaffa.form.FinderLovComboBox.superclass.checkTab.call(this, me, e);
  }
  // }}}
  // {{{
  /**
   * Disables default tab key bahavior
   * @private
   */
  ,initEvents : function(){
    Jaffa.form.FinderLovComboBox.superclass.initEvents.apply(this, arguments);
    
    // disable default tab handling - does no good
    this.keyNav.tab = false;
    
  } // eo function initEvents
  // }}}
  // {{{
  /**
   * clears value
   */
  ,clearValue: function(){
    this.value = '';
    if (this.rendered)
      this.setRawValue(this.value);
    this.store.clearFilter();
    this.store.each(function(r){
      r.set(this.checkField, false);
    }, this);
    if (this.hiddenField) {
      this.hiddenField.value = '';
    }
    this.applyEmptyText();
  } // eo function clearValue
  // }}}
  // {{{
  /**
   * @return {String} separator (plus space) separated list of selected displayFields
   * @private
   */
  ,getCheckedDisplay: function(){
    var re = new RegExp(this.separator, "g");
    return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
  } // eo function getCheckedDisplay
  // }}}
  // {{{
  /**
   * @return {String} separator separated list of selected valueFields
   * @private
   */
  ,getCheckedValue: function(field){
    field = field || this.valueField;
    var c = [];
    
    // store may be filtered so get all records
    var snapshot = this.store.snapshot || this.store.data;
    
    snapshot.each(function(r){
      if (r.get(this.checkField)) {
        c.push(r.get(field));
      }
    }, this);
    
    return c.sort().join(this.separator);
  } // eo function getCheckedValue
  // }}}
  // {{{
  /**
   * beforequery event handler - handles multiple selections
   * @param {Object} qe query event
   * @private
   */
  ,onBeforeQuery: function(qe){
    qe.query = qe.query.replace(new RegExp(this.getCheckedDisplay() + '[ ' + this.separator + ']*'), '');
  } // eo function onBeforeQuery
  // }}}
  // {{{
  /**
   * Combo's onSelect override. 
   * @private
   * @param {Ext.data.Record} record record that has been selected in the list
   * @param {Number} index index of selected (clicked) record
   */
  ,onSelect: function(record, index){
    if (this.fireEvent('beforeselect', this, record, index) !== false) {
    
      // toggle checked field
      record.set(this.checkField, !record.get(this.checkField));
      
      // display full list
      if (this.store.isFiltered()) {
        this.doQuery(this.allQuery);
      }
      
      // set (update) value and fire event
      this.synchValueWithSelect(this.getCheckedValue());
      this.fireEvent('select', this, record, index);
    }
  } // eo function onSelect
  // }}}
  // {{{
  /**
   * Checks the record of the LovCombo based on the value input.
   * Returns a string array of values that do not match to a record in the combo store, 
   * or null if the matching is impossible due to valueField unspecified or input is not a string.
   * @param {String} v value
   */
  ,synchValueWithSelect: function(v){
    var oo = null;
    if (v=='') {
      this.clearValue();
      oo = [];
    } else if (v && typeof v == 'string') {
      if (this.valueField) {
        this.store.clearFilter();
        var vs = Jaffa.data.Util.strSetArray(v,this.separator), vsWidthRecs=[];
        oo =[];
        this.store.each(function(r){
          var i = vs.indexOf(r.get(this.valueField));
          r.set(this.checkField, i>=0);
          if (i>=0) vsWidthRecs.push(vs[i]);
        }, this);
        for (var i=0; i<vs.length; i++) {
          if (vsWidthRecs.indexOf(vs[i])<0) oo.push(vs[i]);
        }
        this.value = this.getCheckedValue();
        //this.setRawValue(this.getCheckedDisplay()); // modified to show value displayValue is only for dropdown
        this.setRawValue(this.value);
        if (this.hiddenField) {
          this.hiddenField.value = this.value;
        }
        // reset the startValue to avoid triggering 'change' event
        this.startValue = this.value;
      } else {
        this.value = v;
        this.setRawValue(v);
        if (this.hiddenField) {
          this.hiddenField.value = v;
        }
      }
      if (this.el) {
        this.el.removeClass(this.emptyClass);
      }
    } else {
      this.clearValue();
    }
    this.isValueValid = true;
    this.validate();
    return oo;
  } // eo function synchValueWithSelect
  // }}}
  // {{{
  /**
   * Validate without firing autoselect. Allows multiple records for a value.
   * See distinction between autoSelect and autoValidate in Jaffa.finder.RemoteSelector
   */
  ,doAutoValidate : function() {
    if (this.store==null) return;
    var residualValues = this.selectRecordsByInputValues(this.getValue());
    if (Ext.isArray(residualValues) && residualValues.length>0) {
      if (this.validateStoreOnly) {
        this.isValueValid = false;
        this.markInvalid(this.invalidateNotFoundText);
      } else {
        this.remoteSelector.autoValidate(residualValues.join(this.separator));
      }
    }
  }
  // }}}
  // {{{
  ,selectRecordsByInputValues : function(v) {
    var oo = null;
    if (v=='') {
      this.clearValue();
      oo = [];
    } else if (v && typeof v == 'string') {
      if (this.valueField) {
        this.store.clearFilter();
        var vs = Jaffa.data.Util.strSetArray(v,this.separator), vsWidthRecs=[];
        oo =[];
        this.store.each(function(r){
          var i = vs.indexOf(r.get(this.valueField));
          r.set(this.checkField, i>=0);
          if (i>=0) vsWidthRecs.push(vs[i]);
        }, this);
        for (var i=0; i<vs.length; i++) {
          if (vsWidthRecs.indexOf(vs[i])<0) oo.push(vs[i]);
        }
        this.value = this.getCheckedValue();
      }
    }
    return oo;
  }
  // }}}
  // {{{
  /**
   * Selects all items
   */
  ,selectAll: function(){
    this.store.each(function(record){
      // toggle checked field
      record.set(this.checkField, true);
    }, this);
    
    //display full list
    this.doQuery(this.allQuery);
    this.synchValueWithSelect(this.getCheckedValue());
  } // eo full selectAll
  // }}}
  // {{{
  ,onKeyUp : function(e){
    if(this.editable !== false && !e.isSpecialKey()){
      this.lastKey = e.getKey();
      if (this.typeAhead) {
        // do not trigger query if typeAhead is not enabled.
        this.dqTask.delay(this.queryDelay);
      }
    }
  }
  // }}}
  // {{{
  /**
   * Deselects all items. Synonym for clearValue
   */
  ,deselectAll: function(){
    this.clearValue();
  } // eo full deselectAll 
  // }}}
  // {{{
  ,getRawValue : function() {
    var v = Jaffa.form.FinderLovComboBox.superclass.getRawValue.call(this);
    var t = Jaffa.data.Util.strSetArray(v,this.separator).join(this.separator);
    if (v!=t) this.setRawValue(t);
    return t;
  }
  // }}}
  // {{{
  /**
   * Handle internal validation success.
   * @param {Object} validValueMap
   */
  ,handleCacheValidationSuccess : function(validValueMap) {
    this.isValueValid = true;
    this.clearInvalid();
    if (validValueMap) {
      // fire only when validValueMap is provided.
      this.fireEvent('autoselect', this, validValueMap, this.getGridRecord());
    }
  }

  ,findValueRecord: function(text,v){
    return text;
  }

  ,getSearchFieldCriteria: function(v){
    return {operator:'In',values:v.split(",")};
  }

}); // eo extend
// register xtype
Ext.reg('finderLovCombo', Jaffa.form.FinderLovComboBox);

// eof
