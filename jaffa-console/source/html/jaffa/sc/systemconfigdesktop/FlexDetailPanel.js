Jaffa.SC.FlexDetailPanel = Ext.extend(Ext.form.FormPanel,{
  closable: true,
  contentType: 'FLEXFIELDS',
  initComponent : function(){
    var tbar = [
      {
        iconCls:'save',
        text:Labels.get('label.Jaffa.SC.SystemConfigDesktop.save'),
        itemId: 'save',
        disabled: !this.controller.isDirty(),
        handler:function(){
          Jaffa.SC.DesktopController.save();
        }
      }
    ];

    var rulePanels = [];
    for (var rule in RuleMetaData){
      if (RuleMetaData[rule].panel){
        rulePanels[rulePanels.length]=RuleMetaData[rule].panel;
      }
    }

    Ext.apply(this, {
      title: this.flexObject.domainName + " (" + this.flexObject.flexName + ")",
      id: this.flexObject.flexName+":"+this.flexObject.flexSourceFile,
      baseTitle: this.flexObject.domainName + " (" + this.flexObject.flexName + ")",
      flexObject: this.flexObject,
      contentType: 'FLEXFIELDS',
      tbar: tbar,
      bodyStyle: 'padding:5px',
      listeners:{
        beforeClose: function(){
          if (this._dirty){
            Ext.MessageBox.show({
              title : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SaveWarning'),
              msg : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SaveWarningMessage'),
              width : 400,
              buttons : Ext.MessageBox.YESNO,
              icon : Ext.MessageBox.QUESTION,
              scope: this,
              fn : function(btn) {
                if (btn == 'yes') {
                  this.setDirty(false);
                  this.ownerCt.remove(this);
                }
              }
            });
            return false;
          }
        },
        render: function(){
          var flexObjectService = new Ext.data.DWRProxy2(Jaffa_Rules_MetaDataReader.getClassMetaData);
          flexObjectService.load([this.flexObject.flexClass,this.flexObject.flexSourceFile],
            {read: function(r){return r;}},
            this.loadFields,
            this
          );

        }
      },
      defaultType:'textfield',
      items:[
        {
          fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.ParentDomainObject'),
          ref: 'domainNameField',
          textOnly: true
        },{
          fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.Condition'),
          ref: 'conditionField',
          textOnly: true
        },{
          fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.FlexObject'),
          ref: 'flexObjectField',
          textOnly: true
        },{
          fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.FlexObjectLabel'),
          ref: 'flexObjectLabelField',
          width: 400
        },{
          fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.SourceFile'),
          ref: 'sourceFileField',
          textOnly: true
        }, {
          xtype: 'griddetailcontainer',
          height: 700,
          ref: 'flexFieldsGrid',
          titleToken: 'label.common.FlexFields', //Labels.get('label.Item.WorkflowSetup.WorkflowTemplate.WorkflowTemplateStepsTab'), //Steps
          controller: Jaffa.SC.DesktopController,
          floatingDataSource:true,
          layout: 'border',
          addButton: false,
          gridModifyButton: false,
          detailAddButton: false,
          detailPanel: {
            xtype: 'flexfielddetailpanel',
            title:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.FlexEditor'),
            ruleCards: rulePanels,
            height: 350
          },
          gridPanel: {
            xtype: 'flexDetailTree',
            controller: Jaffa.SC.DesktopController,
            tbar:[{
              text:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.AddFlexField'),
              iconCls: 'add-row',
              handler: function(){
                this.ownerCt.ownerCt.ownerCt.addFlexField();
              }
            },{
              text:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.AddFlexFieldRule'),
              iconCls: 'add-row',
              handler: function(){
                if (this.ownerCt.ownerCt.getSelectionModel().getSelections().length!=1){
                  Ext.MessageBox.show({
                    title: Labels.get('title.jaffaRIA.Error'),
                    msg: Labels.get('label.jaffaRIA.selectOneRecord'),
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                  });
                }else{
                  var recParent = this.ownerCt.ownerCt.getSelectionModel().getSelected().get('_parent');
                  var recName = this.ownerCt.ownerCt.getSelectionModel().getSelected().get('name');
                  if (recParent=="")
                    this.ownerCt.ownerCt.ownerCt.addFlexFieldRule(recName);
                  else
                    this.ownerCt.ownerCt.ownerCt.addFlexFieldRule(recParent);
                }
              }
            }]
          },
          initComponent: function(){
            this.oldOnRowClick = this.onRowClick;

            this.onRowClick = function(grid, rowIndex, e){
              var rec = {};
              if (typeof rowIndex === "number") {
                rec = this.gridPanel.store.getAt(rowIndex);
              }
              else {
                rec = Ext.apply(rec, rowIndex);
                rowIndex = this.gridPanel.store.indexOf(rowIndex);
              }

              this.oldOnRowClick(grid, rowIndex, e);
              if (this.gridPanel.getTopToolbar().getComponent('_details'))
                this.gridPanel.getTopToolbar().getComponent('_details').enable();
            },
            Jaffa.maintenance.GridDetailContainer.prototype.initComponent.call(this);
          },

          onRowClick: function(grid, rowIndex, e){
            if (grid.getSelectionModel().getSelections().length == 0){
              if (!this.detailPanel.isDirty){
                this.detailPanel.clearForm();
                this.detailPanel.setTitle(this.detailPanel.baseTitle);
              }else{
                if (this.detailPanel.validate()) {
                  this.detailPanel.updateRecord();
                  this.detailPanel.clearForm();
                  this.detailPanel.setTitle(this.detailPanel.baseTitle);
                }
              }
            }else if (grid.getSelectionModel().getSelections().length > 1){
              if (!this.detailPanel.isDirty){
                this.detailPanel.clearForm();
                this.detailPanel.setTitle(this.detailPanel.baseTitle);
              }else{
                if (this.detailPanel.validate()) {
                  this.detailPanel.updateRecord();
                  var record = this.detailPanel.record;
                  this.detailPanel.clearForm();
                  this.detailPanel.setTitle(this.detailPanel.baseTitle);
                }
              }
            }else{
              if (!this.detailPanel.isDirty){
                if (rowIndex >= grid.getStore().getCount()) rowIndex = grid.getStore().getCount() - 1;
                var row = grid.getStore().getAt(rowIndex);
                if (row) {
                  this.detailPanel.loadRecord(row);
                  if (row.get('isNew')) {
                    this.detailPanel.currentTitle = '('+(this.addRecordText?this.addRecordText:this.ownerCt.addRecordText)+') ' + this.detailPanel.baseTitle;
                    this.detailPanel.setTitle(this.detailPanel.currentTitle);
                  } else {
                    this.detailPanel.currentTitle = '('+(this.modifyRecordText?this.modifyRecordText:this.ownerCt.modifyRecordText)+') ' + this.detailPanel.baseTitle;
                    this.detailPanel.setTitle(this.detailPanel.currentTitle);
                  }
                }
              }else{
                if (this.detailPanel.validate(
                  function(){
                    this.detailPanel.clearForm();
                    if (this.layout.type=='card'||this.layout.type == 'slide')
                      this.onRowClick(grid, rowIndex, e);
                    else
                      this.ownerCt.onRowClick(grid, rowIndex, e);
                  }.createDelegate(this))
                  ){
                  this.detailPanel.updateRecord();
                  var row = grid.getStore().getAt(rowIndex);
                  if (row) {
                    this.detailPanel.loadRecord(row);
                    if (row.get('isNew')) {
                      this.detailPanel.currentTitle = '('+(this.addRecordText?this.addRecordText:this.ownerCt.addRecordText)+') ' + this.detailPanel.baseTitle;
                      this.detailPanel.setTitle(this.detailPanel.currentTitle);
                    } else {
                      this.detailPanel.currentTitle = '('+(this.modifyRecordText?this.modifyRecordText:this.ownerCt.modifyRecordText)+') ' + this.detailPanel.baseTitle;
                      this.detailPanel.setTitle(this.detailPanel.currentTitle);
                    }
                  }
                }
              }
            }
            if (this.layout.type=='card'||this.layout.type=='slide') this.layout.setActiveItem(1);
          },

          onDetailOk: function(){
            if (this.detailPanel.validate()) {
              this.detailPanel.updateRecord();
              if (this.layout.type == 'card' || this.layout.type == 'slide')
                this.layout.setActiveItem(0);
            }
          },

          onDetailCancel: function(){
            var record = this.detailPanel.record;
            if (this.detailPanel.isDirty) {
              Ext.MessageBox.confirm('' + this.confirmText + ':', this.discardText, function(mBtn){
                if (mBtn == 'yes') {
                  if (record.get('isNew') && !this.gridPanel.getStore().getById(record.id)) {
                    this.detailPanel.clearForm();
                    if (this.layout.type == 'card' || this.layout.type == 'slide') {
                      this.layout.setActiveItem(0);
                      this.gridPanel.getTopToolbar().getComponent('_details').disable();
                    }
                  }
                  else {
                    this.detailPanel.clearForm();
                    this.detailPanel.loadRecord(record);
                  }
                }
              }, this);
            }
            else {
              if (record.get('isNew') && !this.gridPanel.getStore().getById(record.id)) {
                this.detailPanel.clearForm();
              }
              if (this.layout.type == 'card' || this.layout.type == 'slide') {
                this.resetDetailPanel();
              }
              if (record.json == undefined && (!record.dirty) && (!record.keepOnCancel)) {
                this.gridPanel.getStore().remove(record);
                this.gridPanel.getView().refresh();
              }
            }
          },

          onStepForward: function(){
            if (!this.detailPanel.validate())
              return false;
            var nextNode = this.gridPanel.getStore().getNodeNextSibling(this.detailPanel.record)
            var nextIndex = this.gridPanel.getStore().indexOf(nextNode);
            if (nextIndex > 0)
              this.openRecord(nextIndex);
          },
          onStepBack: function(){
            if (!this.detailPanel.validate())
              return false;
            var prevNode = this.gridPanel.getStore().getNodePrevSibling(this.detailPanel.record)
            var prevIndex = this.gridPanel.getStore().indexOf(prevNode);
            if (prevIndex > 0)
              this.openRecord(prevIndex);
          },
          addFlexField: function(){
            var flexSections = [{
              xtype: 'panel',
              layout: 'form',
              ref: 'flexFieldNamePanel',
              collapsible: false,
              collapsed: false,
              border: false,
              labelWidth: 150,
              items:[{
                xtype:'textfield',
                maskRe: /[a-zA-Z0-9]/,
                validator: function(value){
                  var validRe = new RegExp(/((^[A-Z][A-Z0-9]*$)|(^[a-z][a-zA-Z0-9]*$))/);
                  if (validRe.test(value)){
                    return true;
                  }else{
                    return Labels.get('error.Jaffa.SC.SystemConfigDesktop.FlexFields.InvalidFieldMessage');
                  }
                },
                ref: 'flexFieldNameField',
                fieldLabel:Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.FieldName'),
                allowBlank: false}
              ]
            }];
            for (rule in RuleMetaData){
              var newSection = {
                xtype: 'fieldset',
                title: RuleMetaData[rule].label,
                collapsed: true,
                checkboxToggle: true,
                labelWidth: 150,
                items:RuleMetaData[rule].panel.items,
                ruleMetaData: RuleMetaData[rule],
                rulePanel: true
              };
              flexSections[flexSections.length]=newSection;
            }
            var newFlexFieldPanel = new Ext.ModalWindow({
              title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.AddFlexField'),
              width: 600,
              height: 500,
              autoScroll: true,
              gridPanelStore: this.gridPanel.getStore(),
              items:[{
                xtype: 'form',
                border: false,
                ref: 'flexSections',
                items: flexSections
              }],
              bbar:['->', {
                iconCls: 'ok',
                text: Labels.get('label.JaffaRIA.Button.Add'),
                handler:function(){
                  if (this.ownerCt.ownerCt.addField(this)){
                    this.ownerCt.ownerCt.close();
                  }
                }
              },{
                iconCls: 'cancel',
                text: Labels.get('label.Jaffa.Widgets.Button.Cancel'),
                handler: function () {
                  Ext.MessageBox.confirm(Labels.get('title.jaffaRIA.Warning'), Labels.get('label.jaffa.jaffaRIA.Finder.ConfirmationMessage'), function(btn) {
                    if (btn=='yes') this.ownerCt.ownerCt.close();
                  }, this);
                }
              }],
              addField: function(){
                var flexFieldName = this.flexSections.flexFieldNamePanel.flexFieldNameField.getValue();
                if (!this.flexSections.flexFieldNamePanel.flexFieldNameField.isValid()){
                  Ext.MessageBox.show({
                    title: Labels.get('title.jaffaRIA.Error'),
                    msg: Labels.get('error.Jaffa.SC.SystemConfigDesktop.FlexFields.ValidFlexFieldNameRequired'),
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                  });
                  return false;
                }else if (!flexFieldName || flexFieldName==''){
                  Ext.MessageBox.show({
                    title: Labels.get('title.jaffaRIA.Error'),
                    msg: Labels.get('error.Jaffa.SC.SystemConfigDesktop.FlexFields.FlexFieldNameRequired'),
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                  });
                  return false;
                } else if (
                    this.gridPanelStore.findBy(function(record, id){
                      if (record.get('_id').toLowerCase()==flexFieldName.toLowerCase()) return true;
                    }
                  )>0){
                  Ext.MessageBox.show({
                    title: Labels.get('title.jaffaRIA.Error'),
                    msg: Labels.get('error.Jaffa.SC.SystemConfigDesktop.FlexFields.FlexFieldAlreadyExists'),
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                  });
                  return false;
                } else {
                  var newRecords = [];
                  newRecords[newRecords.length] = {
                    _id: flexFieldName,
                    _is_leaf: false,
                    label: flexFieldName,
                    name: flexFieldName,
                    hasChildren: true,
                    expanded: true
                  };
                  var flexProperties = this.flexSections.items.each(
                    function(flexSection){
                      if (flexSection.rulePanel && flexSection.ruleMetaData){
                        if (!flexSection.collapsed){
                          var paramData = {};
                          flexSection.cascade(function(field){
                            if (field.paramField && field.metaField){
                              paramData[field.metaField]=field.getValue();
                            }
                          })

                          newRecords[newRecords.length] = {
                            _parent: flexFieldName,
                            label: flexSection.ruleMetaData.label,
                            name: flexSection.ruleMetaData.panel.ruleName,
                            nodeType: 'multiProp',
                            iconCls: 'rule',
                            parameters: Ext.util.JSON.encode(paramData),
                            condition: flexSection.condition.getValue()
                          };

                        }
                      }
                    }
                  );
                  this.gridPanelStore.loadData(newRecords, true);
                  this.gridPanelStore.expandNode(this.gridPanelStore.getById(flexFieldName));
                  return true;
                }
              }
            });
            Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(newFlexFieldPanel);
            newFlexFieldPanel.show();
          },
          addFlexFieldRule: function(parentId){
            var ruleCards = []
            var ruleList=[]
            var initialRule = "";
            for (rule in RuleMetaData){
              var ruleCard = {
                xtype: 'fieldset',
                title: RuleMetaData[rule].label,
                collapsed: false,
                collapsible: false,
                labelWidth: 150,
                items:RuleMetaData[rule].panel.items,
                ruleMetaData: RuleMetaData[rule],
                rulePanel: true,
                rule: rule,
                autoScroll: true,
                autoHeight: true
              };
              ruleCards[ruleCards.length]=ruleCard;
              ruleList[ruleList.length]=[RuleMetaData[rule].label,rule];
              if (initialRule == "") initialRule = rule;
            }

            var newFlexFieldPanel = new Ext.ModalWindow({
              title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.AddFlexField'),
              parentId: parentId,
              resizable: false,
              width: 600,
              height: 400,
              autoScroll: true,
              gridPanelStore: this.gridPanel.getStore(),
              layout: 'form',
              items:[
                {
                  xtype: "combo",
                  fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.RuleType'),
                  mode: 'local',
                  triggerAction: 'all',
                  displayField: 'ruleLabel',
                  valueField: 'rule',
                  forceSelection: true,
                  value: initialRule,
                  store: new Ext.data.SimpleStore({
                    fields: ['ruleLabel','rule'],
                    data: ruleList
                  }),
                  listeners:{
                    'select':function(combo, record, index){
                      var rule = record.get('rule');
                      this.ownerCt.ruleCards.getLayout().setActiveItem(
                        this.ownerCt.ruleCards.getLayout().container.items.find(
                          function(card){
                            return card.rule == rule;
                          }
                        )
                      );
                    }
                  }
                },
                {
                  xtype: 'panel',
                  ref: 'ruleCards',
                  layout: 'card',
                  layoutOnCardChange: true,
                  border: false,
                  items: ruleCards,
                  activeItem: 0,
                  height: 200
                }
              ],
              bbar:['->', {
                iconCls: 'ok',
                text: Labels.get('label.JaffaRIA.Button.Add'),
                handler:function(){
                  this.ownerCt.ownerCt.addRule(this);
                  this.ownerCt.ownerCt.close();
                }
              },{
                iconCls: 'cancel',
                text: Labels.get('label.Jaffa.Widgets.Button.Cancel'),
                handler: function () {
                  Ext.MessageBox.confirm(Labels.get('title.jaffaRIA.Warning'), Labels.get('label.jaffa.jaffaRIA.Finder.ConfirmationMessage'), function(btn) {
                    if (btn=='yes') this.ownerCt.ownerCt.close();
                  }, this);
                }
              }],
              addRule: function(form){
                var paramData = {};
                this.ruleCards.getLayout().activeItem.cascade(function(field){
                  if (field.paramField && field.metaField){
                    paramData[field.metaField]=field.getValue();
                  }
                })

                var newRecords = [{
                  _parent: this.parentId,
                  label: this.ruleCards.getLayout().activeItem.ruleMetaData.label,
                  name: this.ruleCards.getLayout().activeItem.ruleMetaData.panel.ruleName,
                  nodeType: 'multiProp',
                  iconCls: 'rule',
                  parameters: Ext.util.JSON.encode(paramData),
                  condition: this.ruleCards.getLayout().activeItem.condition.getValue()
                }];

                this.gridPanelStore.loadData(newRecords, true)
              }
            });
            Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(newFlexFieldPanel);
            newFlexFieldPanel.show();
          }
        }
      ],
      loadFields: function(response, options){
        this.flexFieldObjects = eval(response);
        this.flexFields = [];
        this.flexObjectField.setValue(this.flexObject.flexName);
        this.domainNameField.setValue(this.flexObject.domainName + ((this.flexObject.domainLabel && this.flexObject.domainLabel!="")?(" (" + this.flexObject.domainLabel + ")"):""));
        if (this.flexObject.condition && this.flexObject.condition!="" ){
          this.conditionField.setValue(this.flexObject.condition);
        }else{
          this.conditionField.hideFormItem();
        }
        this.sourceFileField.setValue(this.flexObject.flexSourceFile);
        this.flexObjectLabelField.setValue(this.flexObject.flexLabel);

        if (this.flexFieldObjects && this.flexFieldObjects.length && this.flexFieldObjects.length > 0){
          for (var i = 0; i < this.flexFieldObjects.length; i++){
            var flexFieldObject = this.flexFieldObjects[i];
            if (flexFieldObject && flexFieldObject.properties && flexFieldObject.properties.length && flexFieldObject.properties.length > 0){
              for (var k = 0; k < flexFieldObject.properties.length; k++){
                this.flexFields = this.flexFields.concat(this.convertMetaToRecord(flexFieldObject.properties[k]));
              }
            }
          }
        }
        this.flexFieldsGrid.gridPanel.getStore().loadData(this.flexFields);

      },
      convertMetaToRecord:function(flexField){
        var property = {name:flexField.propertyName};
        var records = [];
        records[0] = {
          _id: flexField.propertyName,
          _is_leaf: false,
          label: flexField.propertyName,
          name: flexField.propertyName,
          hasChildren: true,
          condition: flexField.condition,
          extendsProperty: flexField.extendsProperty,
          extendsClass: flexField.extendsClass,
          language: flexField.language
        };
        if (flexField.rules && flexField.rules.length && flexField.rules.length>0){
          for (var i = 0; i < flexField.rules.length; i++){
            var rule = flexField.rules[i];
            var ruleMetaData = RuleMetaData[rule.ruleName];
            if (ruleMetaData){
              if (ruleMetaData && ruleMetaData.properties){
                var params = {};
                for (var prop in ruleMetaData.properties){
                  params[prop]=rule.parameters[prop];
                }
                records[records.length]={
                  _parent: flexField.propertyName,
                  label: ruleMetaData.label,
                  name: rule.ruleName,
                  nodeType: 'multiProp',
                  iconCls: 'rule',
                  parameters: Ext.util.JSON.encode(params),
                  condition: (rule.parameters['condition']==flexField.condition)?"":rule.parameters['condition']
                };
              }
            }
          }
        }
        return records;
      }
    })
    Jaffa.SC.FlexDetailPanel.superclass.initComponent.call(this);

    this.flexFieldsGrid.gridPanel.getStore().on('update', this.setDirty, this);
    this.flexFieldsGrid.gridPanel.getStore().on('add', this.setDirty, this);
    this.flexFieldsGrid.gridPanel.getStore().on('remove', this.setDirty, this);

    this.flexObjectLabelField.on('keyup', function (tField, evt) {
      if (evt.getKey) {
        if (!this.isDirty && !evt.isNavKeyPress() && (evt.keyCode==evt.BACKSPACE || evt.keyCode==evt.DELETE || !evt.isSpecialKey())) {
          this.setDirty.call(this, true);
        }
      }
    }, this);

  },
  _dirty: false,
  setDirty: function(dirty) {
    if (dirty && !this._dirty){
      this.setTitle(this.baseTitle + '*');
      this.controller.setDirty(true);
      this._dirty = true;
    }
    if (!dirty) {
      this._dirty = false;
      this.setTitle(this.baseTitle);
    }
  },
  refresh: function(){
    this.flexFieldsGrid.detailPanel.clearForm();
    this.setDirty(false);
    var flexObjectService = new Ext.data.DWRProxy2(Jaffa_Rules_MetaDataReader.getClassMetaData);
    flexObjectService.load([this.flexObject.flexClass,this.flexObject.flexSourceFile],
      {read: function(r){return r;}},
      this.loadFields,
      this
    );
  },
  // Build a flexDto object from the data in the grid
  getFlexData: function(){
    var flexStore = this.flexFieldsGrid.gridPanel.getStore();
    var flexData = {sourceFileName: this.flexObject.flexSourceFile,name:this.flexObject.flexClass ,properties:[]};

    if (this.flexFieldObjects && this.flexFieldObjects.length > 0 && this.flexFieldObjects[0].executionRealm) flexData.executionRealm = this.flexFieldObjects[0].executionRealm;
    if (this.flexFieldObjects && this.flexFieldObjects.length > 0 && this.flexFieldObjects[0].extendsClass) flexData.extendsClass = this.flexFieldObjects[0].extendsClass;
    if (this.flexFieldObjects && this.flexFieldObjects.length > 0 && this.flexFieldObjects[0].language) flexData.language = this.flexFieldObjects[0].language;

    if (this.flexObjectLabelField.getValue()!=""){
      flexData.rules=[{ruleName:'label',parameters:{token: this.flexObject.flexLabelToken, tokenValue: this.flexObjectLabelField.getValue()}}]
      this.flexObject.flexLabel = this.flexObjectLabelField.getValue();
    }

    var fieldNodes = flexStore.getRootNodes();
    for (var i = 0; i < fieldNodes.length; i++){
      var fieldNode = fieldNodes[i];
      var flexFieldData = {propertyName: fieldNode.get('name'), rules: []};
      if (fieldNode.get('extendsProperty')!="") flexFieldData.extendsProperty = fieldNode.get('extendsProperty');
      if (fieldNode.get('extendsClass')!="" && this.flexFieldObjects && this.flexFieldObjects.length > 0 && fieldNode.get('extendsClass')!=this.flexFieldObjects[0].extendsClass) flexFieldData.extendsClass = fieldNode.get('extendsClass');
      if (fieldNode.get('language')!="" && this.flexFieldObjects && this.flexFieldObjects.length > 0 && fieldNode.get('language')!=this.flexFieldObjects[0].language) flexFieldData.language = fieldNode.get('language');
      if (fieldNode.get('condition')!='') flexFieldData.condition=fieldNode.get('condition');
      var ruleNodes = flexStore.getNodeChildren(fieldNode);
      var foundFlexFieldNode = false;
      for (var j = 0; j < ruleNodes.length; j++){
        var ruleNode = ruleNodes[j];
        var ruleData = {ruleName: ruleNode.get('name'),parameters: {}};
        if (ruleNode.get('condition')!='') ruleData.parameters['condition']=ruleNode.get('condition');
        var ruleMetaData = RuleMetaData[ruleNode.get('name')];
        for (var prop in ruleMetaData['properties']){
          ruleData.parameters[prop]=Ext.util.JSON.decode(ruleNode.get('parameters'))[prop];
        }
        flexFieldData.rules[flexFieldData.rules.length]=ruleData;
        if (ruleNode.get('name')=='flex-field')foundFlexFieldNode=true;
      }
      //Add default flex-field rule if one is not found
      if (!foundFlexFieldNode) flexFieldData.rules[flexFieldData.rules.length]={ruleName:'flex-field'};
      flexData.properties[flexData.properties.length]=flexFieldData;
    }
    return flexData;
  },
  //Validate the contents of the grid
  validateFlexData: function(){
    if (this.flexFieldsGrid.detailPanel.validate()){
      this.flexFieldsGrid.onDetailOk();
    } else {
      return false;
    }

    var flexStore = this.flexFieldsGrid.gridPanel.getStore();
    var fieldNodes = flexStore.getRootNodes();
    for (var i = 0; i < fieldNodes.length; i++){
      var fieldNode = fieldNodes[i];
      var ruleNodes = flexStore.getNodeChildren(fieldNode);
      var foundLabel = false;
      for (var j = 0; j < ruleNodes.length; j++){
        ruleNode = ruleNodes[j];
        if (ruleNode.get('name')=='label'){
          if (Ext.util.JSON.decode(ruleNode.get('parameters'))['value'] && Ext.util.JSON.decode(ruleNode.get('parameters'))['value']!=''){
            foundLabel = true;
          }
        }
      }
      if (!foundLabel){
        Ext.MessageBox.show( {
          title : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SaveError'),
          msg : Labels.get('error.Jaffa.SC.SystemConfigDesktop.FlexFields.NoLabelDefined'),
          buttons : Ext.MessageBox.OK,
          icon: Ext.MessageBox.ERROR
        });
        return false;
      }
    }
    return true;
  },
  //Write out the contents of the grid to an xml file
  saveFlexData: function(){
    var flexData = this.getFlexData();
    var flexObjectService = new Ext.data.DWRProxy2(Jaffa_Rules_MetaDataWriter.write);
    flexObjectService.on("loadexception", function(proxy,response,callback,error){
      Ext.MessageBox.show({
        title: Labels.get('title.jaffaRIA.Error'),
        msg: Ext.util.Format.htmlEncode(error.localizedMessage),
        buttons: Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });
    });
    flexObjectService.load([flexData],
      {read: function(r){return r;}},
      function(){
        this.refresh();
      },
      this
    );
  }
});


