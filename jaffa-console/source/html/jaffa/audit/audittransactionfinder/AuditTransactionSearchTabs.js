var criteriaWidgetWidth = 100;
AuditTransactionSearchTab = function(config){
  config = config || {};
  Ext.apply(config, {
    titleToken: 'title.Jaffa.Audit.AuditTransactionFinder.AuditTransaction', //Labels.get('label.WorkRecording.Defaults.DefaultWorkOrderFinder.DefaultWorkOrderSearchTab'),
    layout: 'form',
    collapsible: true,
    autoHeight: true,
    bodyStyle: 'background-color:#DFE8F6',
    defaults: {
      labelWidth: config.labelWidth
    },
    items: [
      Jaffa.finder.CriteriaPanelFactory.createPanel({
        mapping: 'processName'
      }), 
      Jaffa.finder.CriteriaPanelFactory.createPanel({
        mapping: 'subProcessName'
      }), 
      Jaffa.finder.CriteriaPanelFactory.createPanel({
        mapping: 'reason'
      }), 
      Jaffa.finder.CriteriaPanelFactory.createPanel({
        xtype: "datefield",
        mapping: 'createdOn'
      }), 
      Jaffa.finder.CriteriaPanelFactory.createPanel({
        mapping: 'createdBy'
      })
    ]
  });
  AuditTransactionSearchTab.superclass.constructor.call(this, config);
};
Ext.extend(AuditTransactionSearchTab, Ext.form.FieldSet);

AuditFieldSearchTab = function(config){
  config = config || {};

  this.auditableProperties = new Ext.data.SimpleStore({
    emptyText: Labels.get('label.Jaffa.Audit.TransactionFinder.DomainField.Combobox.noSelection'),
    fields: ['auditableProperty', 'className']
  });
  
  this.auditablePropertyRecord = new Ext.data.Record.create(
    {name: 'auditableProperty'},
    {name: 'className'}
  );


  this.domainCombo = new Ext.form.ComboBox({
    id: 'auditDomainObjects',
    fieldLabel: Labels.get('label.Jaffa.Audit.AuditTransactionFinder.DomainObject'),
    displayField: 'label',
    valueField: 'logicalName',
    triggerAction: 'all',
    mode: 'local',
    mapping: 'objectName',
    isValueField: true,
    width: 150,
    store: new Ext.data.SimpleStore({
      fields: ['label', 'className']
    }),
    listeners: {
      select: function(combo, record, index ){
        this.ownerCt.auditableProperties.removeAll();
        this.ownerCt.auditableProperties.add(new this.ownerCt.auditablePropertyRecord({
          auditableProperty: this.ownerCt.auditableProperties.emptyText,
          className: ''
        }));
        var fields = this.ownerCt.find('isAuditSearchPanel', true);
        for (var i=0; i<fields.length; i++){
          fields[i].items.each(function(field){
            field.setValue(null);
          });
        }
        var auditablePropertiesQuery = new Ext.data.DWRProxy(Jaffa_Audit_AuditTransactionViewService.getAuditableProperties);
        auditablePropertiesQuery.load(record.get('className'), {read: function(r){return r;}}, function(response){
          for (var field in response) {
            this.ownerCt.auditableProperties.add(new this.ownerCt.auditablePropertyRecord({
              auditableProperty: response[field].label?response[field].label:field,
              className: response[field].name
            }));
          }
        }, this);
      }
    }
  });

  var auditableClassesQuery = new Ext.data.DWRProxy(Jaffa_Audit_AuditTransactionViewService.getAuditableClasses);
  if (!params || !params.objectName) {
    auditableClassesQuery.load(null, {
      read: function(r){
        return r;
      }
    }, function(response){
      var record = new Ext.data.Record.create({
        name: 'label'
      }, {
        name: 'className'
      }, {
        name: 'logicalName'
      });
      for (var field in response) {
        if (response[field]) 
          this.domainCombo.store.add(new record({
            label: response[field].label ? response[field].label : field.split('.')[(field.split('.').length) - 1],
            className: field,
            logicalName: response[field].name ? response[field].name : field.split('.')[(field.split('.').length) - 1]
          }));
        else 
          this.domainCombo.store.add(new record({
            label: response[field].label ? response[field].label : field.split('.')[(field.split('.').length) - 1],
            className: field,
            logicalName: field.split('.')[(field.split('.').length) - 1]
          }));
      }
    }, this);
  }

  Ext.apply(config, {
    titleToken: 'title.Jaffa.Audit.AuditTransactionFinder.AuditFields',
    id: 'AuditFieldSearchTab',
    layout: 'form',
    collapsible: true,
    autoHeight: true,
    bodyStyle: 'background-color:#DFE8F6',
    defaults: {
      labelWidth: config.labelWidth
    },
    items: [
      this.domainCombo, 
      new Ext.Panel({layout:'form', items: [{fieldLabelToken:'label.Jaffa.Audit.AuditTransactionFinder.DomainFields',xtype: 'textfield',hidden:true}]}),
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties}), 
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties}), 
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties}), 
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties}), 
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties}), 
      Jaffa.finder.DomainFieldPanelFactory.createPanel({displayField: 'auditableProperty', valueField: 'className', fieldStore: this.auditableProperties})
    ]
  });
//  AuditFieldSearchTab.domainCombo = domainCombo;
  AuditFieldSearchTab.superclass.constructor.call(this, config);
};
Ext.extend(AuditFieldSearchTab, Ext.form.FieldSet, {
  resetPanel: function(){
    this.auditableProperties.removeAll();
    var auditCriteriaPanels = this.find('isAuditSearchPanel', true);
    if (auditCriteriaPanels && auditCriteriaPanels.length && auditCriteriaPanels.length > 0) {
      for (var i = 0; i<auditCriteriaPanels.length; i++)
        auditCriteriaPanels[i].items.itemAt(0).setValue();
    }
  }
});

Jaffa.finder.DomainFieldPanelFactory = function() {
  return {
    /**
     * Config.isArray==true indicates that the value of this field can be comma delimited list of values. On the
     * server side, this field is handled by org.jaffa.components.finder.CriteriaField. Otherwise, the field will 
     * be handled by java primitive data types on the serverside.
     * @param {Object} config
     */
    createPanel : function(config) {
      config.bodyBorder = false;
      config.bodyStyle = 'background-color:#DFE8F6';
      config.layout = 'absolute';
      config.frame = false;
      config.height = 25;
      config.isSearchPanel=true;
      config.isAuditSearchPanel=true;
      
      this.auditFieldCombo = {
        xtype:'combo',
        displayField: config.displayField,
        valueField: config.valueField,
        editable: false,
        triggerAction: 'all',
        forceSelection: true,
        mode: 'local',
        width: 150,
        store: config.fieldStore,
        listeners: {
          select: function(combo, record, index){
            this.ownerCt.mapping = record.get('className');
          }
        }
      };
      
      var criteriaComboStore = Jaffa.finder.CriteriaOptionsFactory.getStringStore();
      criteriaComboStore.data.add(new criteriaComboStore.recordType({label:Labels.get('label.Common.Any'), value:'Any'}));
      
      this.criteriaCombo = Jaffa.finder.CriteriaOptionsFactory.getAllCriteriaCombo({
        x: 155,
        editable: false,
        operator: true,
        store: criteriaComboStore
      });
      this.valueTextField = {
        xtype: 'textfield',
        x: 260,
        validationEvent: false,
        isValueField: true,  // used to label the value fields for search
        value: config.value || null,
        auditFieldCriteria: true
      };
    /**
		*	The Ext.isRTL value sets in loadProduct1.jsp.
		*	This value will be TRUE in case of Arabic Language and 	FALSE if it is ENGLISH.
		*	changed x value (left alignment of text boxes of audit field in Audit Histories)
		*/		
			if(Ext.isRTL){
				this.auditFieldCombo.x = 355;
				this.criteriaCombo.x = 250;
				this.valueTextField.x = 110;
			}
			  
      config.items = [
        this.auditFieldCombo,
        this.criteriaCombo,
        this.valueTextField
      ];
      config.isArray = config.isArray==null ? true : config.isArray;

      config.xtype = null;
      return new Ext.Panel(config);
    }
  };
}();

