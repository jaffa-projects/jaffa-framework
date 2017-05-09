/**
 * @author SeanZ
 */
/**
 * Class defined to store the base DWR criteria for all DWR queries
 * 
 * @param {Object} config
 */
Jaffa.data.BaseDwrCriteria = function(config) {
  // the collections needs to be recreated for each instance.
  this.criteria = new Ext.util.MixedCollection();
  this.resultGraphRules = new Ext.util.MixedCollection();
  
  for (var i in config) {
    this.addCriteria(i, config[i]);
  }
};
Ext.extend(Jaffa.data.BaseDwrCriteria, Ext.util.Observable, {
  
	alertMsgsetGroupOrderByDisplayValueMsgText : 'The group order does not match with what is expected in the selector.', 
	
	alertMsgSortGrouprFieldNotRegisteredWithQueryManagerMsgText: 'Group order field is not registered with the query manager.',
	
	alertMsgSortOrderFieldNotRegisteredWithQueryManagerMsgText: 'Sort order field is not registered with the query manager.',
	
	alertMsgSetSortOrderByDisplayValueMsgText: 'The sort order does not match with what is expected in the selector.',
	
  /**
   * The target panel {@link Ext.Panel} of query.
   * @type Ext.Panel
   * @property dwrCriteriaContainer
   */
  /**
   * The root panel {@link Ext.Panel} of the search panels.
   * @type Ext.Panel
   * @property ownerCt
   */
  /**
   * The field that handles selection of sorting query results.
   * @type Ext.form.ComboBox
   * @property sortOrderField
   */
  /**
   * The field that handles selection of grouping query results.
   * @type Ext.form.ComboBox
   * @property groupOrderField
   */
  
  criteria: new Ext.util.MixedCollection(),
  resultGraphRules: new Ext.util.MixedCollection(),

  getSortOrderDisplayValue: function() {
    if (this.sortOrderField) {
      var fld = this.sortOrderField.store.getAt(this.sortOrderField.selectedIndex);
      if (fld) return fld.get(this.sortOrderField.displayField);
      else return '';
    } else {
      return '';
    }
  },
  
  setSortOrderByDisplayValue: function(sOrder) {
    if (this.sortOrderField) {
      for (var i=0; i<this.sortOrderField.store.getCount(); i++) {
        var itm = this.sortOrderField.store.getAt(i);
        if (itm.get(this.sortOrderField.displayField)==sOrder) {
          this.sortOrderField.setValue(itm.get(this.sortOrderField.valueField));
          return;
        }
      }
      alert(this.alertMsgSetSortOrderByDisplayValueMsgText);
    } else {
      alert(this.alertMsgSortOrderFieldNotRegisteredWithQueryManagerMsgText);
    }
  },
  
  getGroupOrderDisplayValue: function() {
    if (this.groupOrderField) {
      var fld = this.groupOrderField.store.getAt(this.groupOrderField.selectedIndex);
      if (fld) return fld.get(this.groupOrderField.displayField);
      else return '';
    } else {
      return null;
    }
  },
  
  setGroupOrderByDisplayValue: function(gOrder) {
    if (this.groupOrderField) {
      for (var i=0; i<this.groupOrderField.store.getCount(); i++) {
        var itm = this.groupOrderField.store.getAt(i);
        if (itm.get(this.groupOrderField.displayField)==gOrder) {
          this.groupOrderField.setValue(itm.get(this.groupOrderField.valueField));
          return;
        }
      }
      alert(this.alertMsgsetGroupOrderByDisplayValueMsgText);
    } else {
      alert(this.alertMsgSortGrouprFieldNotRegisteredWithQueryManagerMsgText);
    }    
  },
  
  addCriteria: function(mapping, value) {
    if (mapping=='resultGraphRules') {
      return;
    } else if (value && mapping) {
      if (typeof value == 'object' && value.length) {
        var vobj = this.criteria.get(mapping);
        if (vobj == null) {
          vobj = {};
          this.criteria.add(mapping, vobj);
        }
        vobj.values = value;
      } else {
        this.criteria.add(mapping, value);
      }
    }
  },
  
  addOperator: function(mapping, value) {
    if (value && mapping) {
      var obj = this.criteria.get(mapping);
      if (obj == null) {
        obj = {};
        this.criteria.add(mapping, obj);
      }
      obj.operator = value;
    }
  },
  
  /**
   * reset the criteria object from the input DWR criteria object.
   * @param {Object} crt an DWR criteria object
   */
  resetCriteria: function(crt) {
    this.criteria.clear();
    if (crt) {
      for (var i in crt) {
        if (i == 'resultGraphRules') continue;
        if (typeof crt[i] == 'object') {
          if (crt[i].values) {
            this.criteria.add(i, crt[i]);
          }
        } else {
          this.criteria.add(i, crt[i]);
        }
      }
    }
  },
  
  addResultGraphRules: function(rules) {
    if (rules==null || rules=='') return;
    if (typeof rules == 'string') rules = [rules];
    for (var i=0; i<rules.length; i++) {
      if (rules[i]=='') continue;
      if (! this.resultGraphRules.containsKey(rules[i])) {
        this.resultGraphRules.add(rules[i], rules[i]);
      }
    }
  },
  
  getCriterion: function(mapping) {
    return this.criteria.get(mapping);
  },
  
  getCriteria: function() {
    var output = {};
    this.criteria.eachKey(function(key) {
      output[key] = this.get(key);
    }, this.criteria);
    output.resultGraphRules = new Array();
    this.resultGraphRules.eachKey(function(key){
      output.resultGraphRules.push(key);
    });
    return output;
  },
  
  populateRootCriteria: function() {
    this.dwrCriteriaContainer.dwrCriteria = this.getCriteria();
  }
});

