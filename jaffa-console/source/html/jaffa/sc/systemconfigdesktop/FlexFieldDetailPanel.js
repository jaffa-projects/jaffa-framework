Jaffa.SC.FlexFieldDetailPanel = Ext.extend(Ext.Panel, {
  ruleCards: null,
  tbar:[],
  layout: 'card',
  activeItem: 0,
  initComponent: function(){
    this.items = [{
      bodyBorder: false,
      labelWidth: 150,
      bodyStyle: 'padding:5px 5px 0',
      layout: 'form',
      defaults: {
        xtype: 'textfield'
      },
      items : [
          {
            mapping: 'name',
            fieldLabelToken: 'label.Jaffa.Audit.AuditTransactionField.FieldName',
            metaField: 'name',
            textOnly: true
          }
      ]
    }]
    if (this.ruleCards && this.ruleCards.length){
      for (var i = 0; i < this.ruleCards.length; i++){
        this.items[this.items.length] = this.ruleCards[i];
      }
    }
    Jaffa.SC.FlexFieldDetailPanel.superclass.initComponent.call(this);
    Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(this);
  },
  updateRecord: function(){
    var ai = this.layout.activeItem;
    if (this.record)
      this.record.keepOnCancel = true;
    if (this.isDirty) {
      var paramData = {};
      ai.cascade(function(field){
        if (field.paramField && field.metaField){
          paramData[field.metaField]=field.getValue();
        }
      })
      this.record.set('parameters',Ext.util.JSON.encode(paramData));
      this.record.set('condition',ai.condition.getValue());
      this.setDirty(false);
    }
  },

  //Loads a record into the detail panel
  loadRecord: function(record){
    this.fireEvent('beforeloadrecord', this, record);
    this.record = record;

    if (record.get('nodeType') === 'flexfield'){
      this.layout.setActiveItem(0);
    }else{
      var card = this.layout.container.items.findIndex('ruleName', record.get('name'));
      this.layout.setActiveItem(card);
    }

    var ai = this.layout.activeItem;
    this.controller.clearPanelFields(ai);

    //Decode parameter data and load into the active panel
    var paramData = Ext.util.JSON.decode(record.get("parameters"))||{};
    Ext.apply(paramData, this.record.data);
    this.controller.setPanelFields(ai, paramData);

    this.enable();
    this.fireEvent('loadrecord', this, record);
  },

  validate: function(){
    var valid = true;
    var ai = this.layout.activeItem;
    if (ai && ai.validate) {
      valid = ai.validate();
    }
    return valid;
  }
});

Ext.reg('flexfielddetailpanel', Jaffa.SC.FlexFieldDetailPanel);