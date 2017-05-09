AuditTransactionCriteriaPanel = function(config) {
  config = config||{};

  var labelWidth = 150;
  config = Ext.apply(config, {
    region: 'west',
    title: Labels.get('label.Jaffa.Audit.AuditTransaction') + ' ' + Labels.get('title.Jaffa.Finder.Criteria'),
    minWidth: 450,
    autoScroll: true,
    split: true,
    collapsible: true,
    titleCollapse: true,
    frame: true,
    metaClass: 'AuditTransactionGraph',
    bodyStyle: 'padding:5px 5px 0',
    defaults: {
      isSearchPanel: true
    },
    keys:({
      key: Ext.EventObject.ENTER,
	    fn: function(){
        this.search();
      },
	    scope: this
    }),
    items: [
      new AuditTransactionSearchTab ( {id: 'auditTransactionSearchTab', labelWidth: labelWidth }),
      new AuditFieldSearchTab ( {id: 'auditFieldSearchTab', labelWidth: labelWidth })
    ]
  });
  
  // Create the Criteria Panel with its contents
  console.debug("AuditTransactionCriteriaPanel: create ", config);
  AuditTransactionCriteriaPanel.superclass.constructor.call(this, config);
  
  // Apply all metadata to the panel fields
  Jaffa.DWRService.applyMetaRules(this, null, ['LABEL','CASE']);
};

Ext.extend(AuditTransactionCriteriaPanel, Jaffa.finder.CriteriaPanel, {
  initComponent: function() {
    // Invoke the initComponent of the base class
    AuditTransactionCriteriaPanel.superclass.initComponent.call(this);
  }

  ,resetPanel: function() {
    AuditTransactionCriteriaPanel.superclass.resetPanel.call(this);
    Ext.getCmp('AuditFieldSearchTab').resetPanel();
  }

  /** Convert all the fields that have values into a criteria object
   */     
  ,getCriteriaFromPanel: function() {
    var c = {};
    var fields = this.find('isValueField', true);
    if (fields && typeof fields == 'object' && fields.length && fields.length>0) {
      for (var i = 0; i < fields.length; i++) {
        var fld = fields[i];
        var mapping = fld.mapping;
        var addOp=null, addValue=null;
        if (mapping == null) {
          var valueField = fld;
          var ownerField = fld.ownerCt;
          if (ownerField) {
            mapping = ownerField.mapping;
            if (mapping == null) continue;
            var opr = ownerField.find('operator', true);
            if (opr && opr[0] && opr[0].getValue()) {
              addOp=opr[0].getValue();
            }
            if (ownerField.isArray && valueField.getValue()) {
              var tmp = valueField.getValue().split(',');
              for (var k = 0; k < tmp.length; k++) {
                tmp[k] = tmp[k].replace(/\s+$/, '');
              }
              addValue=tmp;
            } else if (valueField.getValue()){
              addValue=[valueField.getValue()];
            }
          }
        } else if (fld.xtype=='finderComboGrid') {
          if (fld.getValue()) {
            var tmp = fld.getValue().split(',');
            for (var k = 0; k < tmp.length; k++) {
              tmp[k] = tmp[k].replace(/\s+$/, '');
            }
            addOp='In';
            addValue=tmp;
          }
        } else if (fld.getValue()) {
          addValue=fld.getValue();
        }
        
        // Need to add somthing?
        if(mapping && (addOp||addValue ) ) {
          if (fld.auditFieldCriteria == true) {
            if (!c.auditTransactionFields) c.auditTransactionFields = [];
            
            var tfConfig ={fieldName:mapping};
            if (addOp && addOp=='Any'){
              tfConfig.changed={values:'True'};
            } else {
              if (addOp)
                tfConfig.toValue={operator:addOp};
              if (addValue){
                if (tfConfig.toValue)
                  tfConfig.toValue.values=addValue;
                else
                  tfConfig.toValue={values:addValue};
              }
            }
            c.auditTransactionFields[c.auditTransactionFields.length]=tfConfig;
          }
          else {
            if (mapping == 'objectName') {
              if (addValue)
                c[mapping]=addValue;
            }
            else {
              if (!c[mapping]) 
                c[mapping] = {};
              if (addOp) 
                c[mapping].operator = addOp;
              if (addValue) 
                c[mapping].values = addValue;
            }
          }
        }
      }// for
    }
    return c;
  }

});