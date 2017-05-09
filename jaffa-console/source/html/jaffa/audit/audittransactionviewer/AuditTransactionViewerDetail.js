/** @class AuditTransactionDetail
 * Main Tab Panel to display audit transaction records, which includes additional
 * sub-tabs for additional information
 * Ext.ModalWindow is defined in js\extjs\ux\form\ModalWindow.js and is not an extjs class
 * textOnly is not a config option of Ext.form.TextField, 
 */
AuditTransactionDetail = Ext.extend(Ext.Panel, {
  constructor: function (config) {
    var structure = '';
    if (config && config.controller && config.controller.model && config.controller.model.getCount() == 1)
      auditTransaction = config.controller.model.itemAt(0).items;
    var tabTitle = Labels.get('title.Jaffa.Audit.AuditTransactionViewer.AuditTransactionDetail');

  this.FieldChangeRecord = Jaffa.data.Record.create([, {
    name: 'fieldId',
    type: 'string',
    hidden: true,
    alwaysHidden: true
  }, {
    name: 'objectLabel',
    mapping: 'objectLabel',
    type: 'string'
  }, {
    name: 'fieldLabel',
    mapping: 'fieldLabel',
    type: 'string'
  }, {
    name: 'fromValue',
    mapping: 'fromValue',
    type: 'string'
  }, {
    name: 'toValue',
    mapping: 'toValue',
    type: 'string'
  }, {
    name: 'multilineHtmlFlag',
    mapping: 'multilineHtmlFlag',
    type: 'string',
    hidden: true,
    alwaysHidden: true
  }, {
    name: 'overflowChangedFrom',
    mapping: 'overflowChangedFrom',
    type: 'string',
    hidden: true,
    alwaysHidden: true
  }, {
    name: 'overflowChangedTo',
    mapping: 'overflowChangedTo',
    type: 'string',
    hidden: true,
    alwaysHidden: true
  }]);

  var columns = [{
      header: Labels.get('label.Jaffa.Audit.AuditTransactionObject'),
      dataIndex: 'objectLabel',
      width: 150,
      sortable: true
    }, {
      header: Labels.get('label.Jaffa.Audit.AuditTransactionField'),
      dataIndex: 'fieldLabel',
      width: 150,
      sortable: true
    }, {
      header: Labels.get('label.Jaffa.Audit.AuditTransactionField.FromValue'),
      dataIndex: 'fromValue',
      width: 475,
      sortable: true,
      renderer: function(val, meta, rec) {
        if (val && val.length > 0) {
          if (rec.get('multilineHtmlFlag') === 'M') {
            var text = /(.*?)[\r|\n]/.exec(val);
            if (!Ext.isEmpty(text)) {
              val = text[0];
            }
          }
          if (rec.get('multilineHtmlFlag') === 'M' || rec.get('multilineHtmlFlag') === 'H') {
            val = '<a href="javascript:Ext.getCmp(\'auditTransactionTab\').showModalPopup(\''+ rec.get('fieldId') + '\');">' + (val.length > 100 ? val.substr(0, 100) : val) + '</a>';
          }  
        }
        return val;
      }
    }, {
      header: Labels.get('label.Jaffa.Audit.AuditTransactionField.ToValue'),
      dataIndex: 'toValue',
      width: 475,
      sortable: true,
      renderer: function(val, meta, rec) {
        if (val && val.length > 0) {
          if (rec.get('multilineHtmlFlag') === 'M') {
            var text = /(.*?)[\r|\n]/.exec(val);
            if (!Ext.isEmpty(text)) {
              val = text[0];
            }
          }
          if (rec.get('multilineHtmlFlag') === 'M' || rec.get('multilineHtmlFlag') === 'H') {
            val = '<a href="javascript:Ext.getCmp(\'auditTransactionTab\').showModalPopup(\''+ rec.get('fieldId') + '\');">' + (val.length > 100 ? val.substr(0, 100) : val) + '</a>';
          }  
        }
        return val;
      }
    }];

  this.grid = new Ext.grid.GridPanel({
    flex: 1,
    frame: true,
    border: false,
    plugins: [new Ext.ux.plugins.MetaColumns({columns: columns})],
    ds: new Ext.data.GroupingStore({
      sortInfo: {
        field: 'fieldName',
        direction: 'ASC'
      },
      proxy: new Ext.data.MemoryProxy(),
      reader: new Ext.data.JsonReader({
        id: 'objectName'
      }, this.FieldChangeRecord)
    }),
    view: new Ext.grid.GroupingView({
      forceFit: false,
      groupTextTpl: "{text} ({[values.rs.length]} {[values.rs.length > 1 ? Labels.get('label.Jaffa.Common.Records') : Labels.get('label.Jaffa.Common.Record')]})"
    }),
    bbar: [{
      xtype: 'button',
      text: Labels.get('label.jaffaRIA.Button.ExportToExcel'),
      iconCls: 'excel',
      itemId: 'excel',
      scope: this,
      handler: function() {
        var cfg = this.grid.getColumnModel().config, cm = [], domainCombo = this.findById('auditDomainObjects');
        for (var i = 0; i < cfg.length; i++) {
          var col = cfg[i];
          var meta = this.grid.store.reader.recordType.getField(col.dataIndex);
          if (meta && (col.hidden != true || (this.grid.store.groupField && grid.store.groupField.indexOf(col.dataIndex) >= 0))) {
            cm.push({
              header: col.header,
              mapping: meta.mapping,
              layout: col.layout || ''
            });
          }  
        }
        var criteria = {transactionId: {values: [params.transactionId]}}, index = domainCombo.getValue();
        if (Ext.isNumber(index) && index >= 0) {
          domainCombo.getStore().each(function(rec) {
            if (rec.get('index') === index) {
              criteria.objectId = rec.get('objectId');
              return false;
            }
          });
        }
        criteria.objectLimit=Ext.ux.plugins.exportToExcelObjectLimit;
        var form = Ext.DomHelper.append(document.body, {
          tag:'form',
          method:'POST',
          action : params.appCtx + (config.jspPath?config.jspPath:'/js/extjs/jaffa/finder/exportToExcel.jsp')
        });
        Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'serviceClassName', value: 'org.jaffa.components.audit.apis.AuditTransactionViewService'});
        Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'criteriaClassName', value: 'org.jaffa.components.audit.apis.data.AuditTransactionCriteria'});
        Ext.DomHelper.append(form, {
          tag:'input', type:'hidden',
          name:'criteriaObject'
        }).value = Ext.util.JSON.encode(criteria);
        Ext.DomHelper.append(form, {
          tag:'input', type:'hidden',
          name:'columnModel'
        }).value = Ext.util.JSON.encode(cm);
        form.submit();
        setTimeout(function(){Ext.removeNode(form);}, 100);
      }
    }]
  });

    var domainCombo = new Ext.form.ComboBox({
      id: 'auditDomainObjects',
      fieldLabel: Labels.get('label.Jaffa.Audit.AuditTransactionFinder.DomainObject'),
      displayField: 'object',
      valueField: 'index',
      triggerAction: 'all',
      mode: 'local',
      isValueField: true,
      width: 150,
      store: new Ext.data.SimpleStore({
        fields: ['object', 'index']
      }),
      listeners: {
        scope: this,
        select: function(combo, record, index){
          this.loadGrid(combo.getValue(), record.get('className'));
        }
      }
    });
    
    config = Ext.applyIf(config || {}, {
      layout: 'vbox',
      align: 'stretch',
      id: 'auditTransactionTab',
      title: tabTitle,
      grid: this.grid,
      plugins: new Jaffa.maintenance.plugins.PanelLoadSave(),
      items: [new Ext.Panel({
        height: 150,
        border: false,
        frame: false,
        layout: 'form',
        labelWidth: 150,
        metaClass: 'AuditTransactionGraph',
        items: [{
          mapping: 'processName',
          textOnly: true
        },{
          mapping: 'subProcessName',
          textOnly: true
        },{
          mapping: 'createdOn',
          textOnly: true
        },{
          mapping: 'createdBy',
          textOnly: true
        },domainCombo]
      }), this.grid
      ],
      showModalPopup: function(fieldId) {
        var index = this.grid.getStore().findExact('fieldId', fieldId);
        if (index >= 0) {
          var rec = this.grid.getStore().getAt(index);
          var popupWindow = new Ext.Window({
            modal: true,
            closable: true,
            shadow: false,
            bodyBorder: false,
            bodyStyle: 'background-color:#DFE8F6;padding:5px',
            minimizable: false,
            draggable: true,
            resizable: true,
            constrainHeader: true,
            width: 900,
            height: 500,
            layout: 'fit',
            id:'auditTransactionOverflowFieldWindow',
            title: Labels.get('title.Jaffa.Audit.AuditTransactionViewer.OverflowPopup'),
            items: [{
              xtype: 'panel',
              border: false,
              frame: false,
              bodyStyle: 'background-color:#DFE8F6',
              layout: {
                type: 'vbox',
                align: 'stretch'
              },
              items: [{
                xtype: 'form',
                height: 25,
                border: false,
                frame: false,
                bodyStyle: 'background-color:#DFE8F6',
                items: [{
                  xtype: 'textfield',
                  value: rec.get('fieldLabel'),
                  textOnly: true,
                  fieldLabel: Labels.get('label.Jaffa.Audit.AuditTransactionField')
                }]
              }, {
                xtype: 'panel',
                flex: 1,
                layout: {
                  type: 'hbox',
                  align: 'stretch'
                },
                border: false,
                frame: false,
                items: [{
                  xtype: 'panel',
                  layout: 'fit',
                  flex: 1,
                  border: true,
                  frame: true,
                  title: Labels.get('label.Jaffa.Audit.AuditTransactionField.FromValue'),
                  items: [{
                    xtype: rec.get('multilineHtmlFlag') === 'H' ? 'htmleditor' : 'textarea',
                    value: rec.get('overflowChangedFrom'),
                    readOnly: true,
                    listeners: {
                      afterrender: function(field) {
                        if (field.xtype === 'htmleditor') {
                          var toolbar = field.getToolbar();
                          toolbar.hideParent = true;
                          toolbar.hide();
                        }
                      }
                    }
                  }]
                }, {
                  xtype: 'panel',
                  layout: 'fit',
                  flex: 1,
                  border: true,
                  frame: true,
                  title: Labels.get('label.Jaffa.Audit.AuditTransactionField.ToValue'),
                  items: [{
                    xtype: rec.get('multilineHtmlFlag') === 'H' ? 'htmleditor' : 'textarea',
                    value: rec.get('overflowChangedTo'),
                    readOnly: true,
                    listeners: {
                      afterrender: function(field) {
                        if (field.xtype === 'htmleditor') {
                          var toolbar = field.getToolbar();
                          toolbar.hideParent = true;
                          toolbar.hide();
                        }
                      }
                    }
                  }]
                }]
              }]
            }]
          });
          popupWindow.show();
        }
      }
    });

    AuditTransactionDetail.superclass.constructor.call(this, config);
  },
  
  loadData: function () {
    this.doLoadData();
    
    var domainCombo = this.findById('auditDomainObjects');
    domainCombo.store.removeAll();
    var record = new Ext.data.Record.create(
      {name: 'object'},
      {name: 'className'},
      {name: 'index'},
      {name: 'objectId'}
    );

    domainCombo.store.add(new record({object:'All',className:'All',index:-1}));
    var ds = this.findDataSource();
    var aTOs = ds.auditTransactionObjects ? ds.auditTransactionObjects : new Ext.util.MixedCollection();
    aTOs.each(function(item, i, length){
      domainCombo.store.add(new record({object:item.objectLabel + ' (' + (item.changeType=='I'?'Insert':(item.changeType=='D'?'Delete':'Update')) + ')',className:item.objectName,index:i,objectId:item.objectId}));
    })    
    this.fireEvent("load", this);
    
    domainCombo.setValue('All');
    this.loadGrid(-1, 'All');
  },
  
  loadGrid: function (atoIndex, objectLabel){
    this.grid.getStore().removeAll();
    if (atoIndex>=0) {
      var atfs = this.controller.model.itemAt(0).auditTransactionObjects.itemAt(atoIndex).auditTransactionFields;
      for (var i = 0; i < atfs.length; i++) 
        this.grid.getStore().add(new this.FieldChangeRecord({
          fieldId: atfs[i].fieldId,
          objectLabel: objectLabel,
          fieldLabel: atfs[i].fieldLabel,
          multilineHtmlFlag: atfs[i].multilineHtmlFlag,
          fromValue: atfs[i].changed ? atfs[i].fromValue : atfs[i].toValue,
          toValue: atfs[i].changed ? atfs[i].toValue : Labels.get('label.Jaffa.Audit.AuditTransactionViewer.NoChange'),
          overflowChangedFrom: atfs[i].auditTransactionOverflow ? atfs[i].auditTransactionOverflow.fromValue : '',
          overflowChangedTo: atfs[i].auditTransactionOverflow ? (atfs[i].changed ? atfs[i].auditTransactionOverflow.toValue : Labels.get('label.Jaffa.Audit.AuditTransactionViewer.NoChange')) : ''
        }));
    } else if (this.controller.model.itemAt(0).auditTransactionObjects) {
      for (var j = 0; j < this.controller.model.itemAt(0).auditTransactionObjects.length; j++) {
        var atfs = this.controller.model.itemAt(0).auditTransactionObjects.itemAt(j).auditTransactionFields;
        for (var i = 0; i < atfs.length; i++) 
          this.grid.getStore().add(new this.FieldChangeRecord({
            fieldId: atfs[i].fieldId,
            objectLabel: this.controller.model.itemAt(0).auditTransactionObjects.itemAt(j).objectLabel,
            fieldLabel: atfs[i].fieldLabel,
            multilineHtmlFlag: atfs[i].multilineHtmlFlag,
            fromValue: atfs[i].changed ? atfs[i].fromValue : atfs[i].toValue,
            toValue: atfs[i].changed ? atfs[i].toValue : Labels.get('label.Jaffa.Audit.AuditTransactionViewer.NoChange'),
            overflowChangedFrom: atfs[i].auditTransactionOverflow ? atfs[i].auditTransactionOverflow.fromValue : '',
            overflowChangedTo: atfs[i].auditTransactionOverflow ? (atfs[i].changed ? atfs[i].auditTransactionOverflow.toValue : Labels.get('label.Jaffa.Audit.AuditTransactionViewer.NoChange')) : ''
          }));
      }
    }
  },
  
  setDirty: Ext.emptyFn
});
