/** 
 *
 * @author Sean Zhou, Nov, 2010
 */
Jaffa.Transaction.TransactionCriteriaSearchTab = function(config){
  return Ext.apply({
    header: false,
    ref: 'transactionCriteriaPanel',
    layout: 'form',
    autoHeight: true,
    bodyStyle: 'background-color:#DFE8F6',
    defaults: {
      xtype: 'finderCriteriaFieldPanel'
    },
    items: [{
      mapping: 'direction'
    }, {
      xtype: 'lovcombo'
      ,ref: 'typeCriteriaField'
      ,mapping: "type"
      ,typeAhead: true
      ,triggerAction: 'all'
      ,valueField: 'typeName'
      ,displayField: 'typeLabel'
      ,isValueField: true
      ,isCriteriaField: true
      ,store: {
        xtype: 'store'
        ,reader: new (Ext.extend(Ext.data.DwrReader, {
          read: function(resp) {
            var dd = [];
            for (var i=0; i<resp.graphs.length; i++) {
              dd.push({
                typeName: resp.graphs[i].split('=')[1],
                typeLabel: resp.graphs[i].split('=')[0]
              });
            }
            resp.graphs = dd;
            return this.readRecords(resp);
          }
        }))({}, Jaffa.data.Record.create([{name: 'typeName'},{name: 'typeLabel'}]))
        ,proxy: new Jaffa.data.DWRProxy({
          query: Jaffa_Transaction_TransactionService.getTypeNames
          ,load: function(params, reader, loadCallback, scope, arg) {
            Jaffa.data.DWRProxy.prototype.load.call(this, null, reader, loadCallback, scope, arg)
          }
        })
      }
    }, {
      xtype: 'lovcombo'
      ,mapping: 'subType'
      ,typeAhead: true
      ,triggerAction: 'all'
      ,valueField: 'typeName'
      ,displayField: 'typeName'
      ,isValueField: true
      ,isCriteriaField: true
      ,store: {
        xtype: 'store'
        ,reader: new (Ext.extend(Ext.data.DwrReader, {
          read: function(resp) {
            var dd = [];
            for (var i=0; i<resp.graphs.length; i++) {
              dd.push({
                typeName: resp.graphs[i]
              });
            }
            resp.graphs = dd;
            return this.readRecords(resp);
          }
        }))({}, Jaffa.data.Record.create([{name: 'typeName'}]))
        ,proxy: new Jaffa.data.DWRProxy({
          query: Jaffa_Transaction_TransactionService.getSubTypeNames
          ,load: function(params, reader, loadCallback, scope, arg) {
            Jaffa.data.DWRProxy.prototype.load.call(this, null, reader, loadCallback, scope, arg)
          }
        })
      }
    }, {
      mapping: 'status'
    }, {
      mapping: "errorMessage"
    }, {
      mapping: 'createdOn'
      ,fieldType: 'datefield' 
    }, {
      mapping: 'createdBy'
    }, {
      mapping: 'lastChangedOn'
      ,fieldType: 'datefield' 
    }, {
      mapping: 'lastChangedBy'
    }]
  }, config);
};

Jaffa.Transaction.TransactionFieldTab = function(config){
  return Ext.apply({
    xtype: 'fieldset',
    layout: 'form',
    title: Labels.get('title.Jaffa.Transaction.TransactionFinder.Fields'),
    collapsible: true,
    autoHeight: true,
    bodyStyle: 'background-color:#DFE8F6',
    defaultType: 'textfielddomainpanel',
    defaults: {
      mappingRoot: 'transactionFields'
    },
    items: [{
      idx: 0
    }, {
      idx: 1
    }, {
      idx: 2
    }, {
      idx: 3
    }, {
      idx: 4
    }, {
      idx: 5
    }]
  }, config);
};

Jaffa.finder.TextFieldDomainPanel = Ext.extend(Ext.Container, {
  /**
   * 
   * @param {Object} config should have mappingRoot and idx
   */
  constructor: function(config) {
    var criteriaComboStore = Jaffa.finder.CriteriaOptionsFactory.getStringStore();
    criteriaComboStore.data.add(new criteriaComboStore.recordType({label:Labels.get('label.jaffa.jaffaRIA.form.BooleanCombo.anyText'), value:'Any'}));

    config = Ext.apply({
      bodyBorder : false,
      bodyStyle : 'background-color:#DFE8F6',
      layout : 'absolute',
      frame : false,
      height : 25,
      isSearchPanel : true,
      idx: 0,
      isArray: true,
      items: [{
        xtype: 'textfield'
        ,ref: 'fieldName'
        ,width: 150
      }, Jaffa.finder.CriteriaOptionsFactory.getAllCriteriaCombo({
        ref: 'operator',
        x: 155,
        editable: false,
        store: criteriaComboStore
      }), {
        xtype: 'textfield',
        ref: 'value',
        x: 260,
        validationEvent: false,
        value: config.value || null
      }]
    }, config);
    /**
		*	The Ext.isRTL value sets in loadProduct1.jsp.
		*	This value will be TRUE in case of Arabic Language and 	FALSE if it is ENGLISH.
		*	changed x value(left alignment of text boxes of Transaction Fields in Transaction Desktop)
		*/	
		if(Ext.isRTL){
		config.items[0].x = 355;
		config.items[1].x = 250;
		config.items[2].x = 110;
		} 
    Jaffa.finder.TextFieldDomainPanel.superclass.constructor.call(this, config);
  }
  ,resetPanel: function() {
    this.fieldName.reset();
    this.operator.reset();
    this.value.reset();
  }
  ,setPanelFromCriteria: function(data) {
    var dd = Jaffa.data.Util.get(data.criteria, this.mappingRoot) || [];
    if (dd.length > this.idx) {
      this.fieldName.setValue(dd[this.idx].fieldName);
      this.operator.setValue(dd[this.idx].value.operator);
      this.value.setValue(dd[this.idx].value.values.join(','));
    }
  }
  ,getCriteriaFromPanel: function(criteria) {
    if (this.fieldName.getValue() && this.value.getValue()) {
      var dd = Jaffa.data.Util.get(criteria, this.mappingRoot);
      if (!dd) {
        dd = [];
        Jaffa.data.Util.set(criteria, this.mappingRoot, dd);
      }
      dd[this.idx] = {fieldName: this.fieldName.getValue()};
      var operator = this.operator.getValue();
      var values = this.value.getValue().split(',');
      for (var i=0; i<values.length; i++) {
        values[i] = values[i].trim();
      }
      dd[this.idx].value = {values: values};
      if (operator) {
        dd[this.idx].value.operator = operator;
      }
    }
  }
  ,setParamsToPanel: function(p) {
    var crt = {};
    for (var i in p) {
      Jaffa.data.Util.set(crt, i, p[i]);
    }
    var dd = Jaffa.data.Util.get(crt, this.mappingRoot) || [];
    if (dd.length > this.idx) {
      this.fieldName.setValue(dd[this.idx].fieldName);
      if (dd[this.idx].value.operator) this.operator.setValue(dd[this.idx].value.operator);
      this.value.setValue(dd[this.idx].value.values);
    }
  }
});
Ext.reg('textfielddomainpanel', Jaffa.finder.TextFieldDomainPanel);

Jaffa.finder.CriteriaPanel2 = Ext.extend(Jaffa.finder.CriteriaPanel, {
  resetPanel: function() {
    var fields = this.find('isSearchPanel', true) || [];
    for (var i=0; i<fields.length; i++) {
      if (typeof fields[i].resetPanel == 'function') {
        fields[i].resetPanel();
      }
    }
    Jaffa.finder.CriteriaPanel2.superclass.resetPanel.call(this);
  }
  ,setPanelFromCriteria: function(data) {
    Jaffa.finder.CriteriaPanel2.superclass.setPanelFromCriteria.call(this, data);
    var fields = this.find('isSearchPanel', true) || [];
    for (var i=0; i<fields.length; i++) {
      if (typeof fields[i].setPanelFromCriteria == 'function') {
        fields[i].setPanelFromCriteria(data);
      }
    }
  }
  ,getCriteriaFromPanel: function() {
    var criteria = Jaffa.finder.CriteriaPanel2.superclass.getCriteriaFromPanel.call(this);
    var fields = this.find('isSearchPanel', true) || [];
    for (var i=0; i<fields.length; i++) {
      if (typeof fields[i].getCriteriaFromPanel == 'function') {
        fields[i].getCriteriaFromPanel(criteria);
      }
    }
    return criteria;
  }
  ,setParamsToPanel: function(p) {
    var fields = this.find('isSearchPanel', true) || [];
    for (var i=0; i<fields.length; i++) {
      if (typeof fields[i].setParamsToPanel == 'function') {
        fields[i].setParamsToPanel(p);
      }
    }
    Jaffa.finder.CriteriaPanel2.superclass.setParamsToPanel.call(this, p);
  }
});
Ext.reg('criteriapanel2', Jaffa.finder.CriteriaPanel2);
