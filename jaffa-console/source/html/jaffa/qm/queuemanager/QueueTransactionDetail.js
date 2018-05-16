/** @class QueueTransactionDetail
 * Main Tab Panel to display transactions in a queue
 * Ext.ModalWindow is defined in js\extjs\ux\form\ModalWindow.js and is not an extjs class
 * textOnly is not a config option of Ext.form.TextField,
 */
Jaffa.QM.QueueTransactionDetail = Ext.extend(Ext.Panel, {
  constructor: function (config) {
    this.type = config.type;
    var messageId = config.messageId;
    var status = config.status;
    this.queueSystemId = config.queueSystemId;
    var initialJson = config.initialJson;
    var queueMetaData = config.queueMetaData;

    var appFields = initialJson.applicationFields?initialJson.applicationFields:[];

    //Loop and build 1st panel
    var tabGeneral = {
      xtype: 'form',
      title: Labels.get('label.Jaffa.QM.QueueManager.General'),
      ref: 'general',
      defaultType: 'textfield',
      bodyStyle: 'padding:5px;',
      items:[
        {mapping:'priority', textOnly: true, width:100, style:'text-align:left;'}
      ]
    };

    if (appFields.length > 0){
      for (var i = 0; i < appFields.length; i++){
        tabGeneral.items[tabGeneral.items.length] = {appFieldMapping:appFields[i].name, fieldLabel:appFields[i].label?appFields[i].label:appFields[i].name, mapping:'dummyMapping', textOnly: true, labelWidth: false, labelStyle: 'width: auto'};
      }
    }

    tabGeneral.items[tabGeneral.items.length] = {mapping: 'createdBy', textOnly: true};
    tabGeneral.items[tabGeneral.items.length] = {mapping: 'createdOn', textOnly: true};
    tabGeneral.items[tabGeneral.items.length] = {mapping: 'errorMessage', textOnly: true};

    var panelTabs = [tabGeneral]

    if (queueMetaData.supportsTechnicalFields){
      var tabTechnical = {
        xtype: 'form',
        title: Labels.get('label.Jaffa.QM.QueueManager.TechnicalDetails'),
        defaultType: 'textfield',
        ref: 'techFields',
        bodyStyle: 'padding:5px;',
        autoScroll: true,
        items:[]
      };

      panelTabs[panelTabs.length] = tabTechnical;
    }

    if (queueMetaData.supportsDependencies){
      var dependFields = [
        { name: 'dependsOnId',
          mapping: 'dependsOnId',
          type: 'string'
        },{
          name: 'status',
          mapping: 'status',
          type: 'string'
        }
      ];
      this.DependencyRecord = new Jaffa.data.Record.create(dependFields);

      var columns = [{
        header: Labels.get('label.Jaffa.QM.MessageDependency.DependsOnId'),
        dataIndex: 'dependsOnId',
        sortable: true
      },{
        header: Labels.get('label.Jaffa.QM.MessageDependency.Status'),
        dataIndex: 'status',
        sortable: true,
        filter: true
      }];

      var tabDependencies = {
        xtype: 'grid',
        ref: 'tabDepend',
        title: Labels.get('label.Jaffa.QM.QueueManager.Dependencies'),
        metaClass: 'MessageGraph',
        plugins: [
          new Ext.ux.plugins.MetaColumns({columns: columns})
        ],
        ds: new Ext.data.GroupingStore({
          proxy: new Ext.data.MemoryProxy(),
          reader: new Ext.data.JsonReader({}, this.DependencyRecord)
        })
      };

      panelTabs[panelTabs.length] = tabDependencies;
    }

    var tabPayload = {
      xtype: 'panel',
      layout: 'fit',
      title: Labels.get('label.Jaffa.QM.QueueManager.Message'),
      items:[
        {
          mapping:'payload',
          xtype: 'textarea',
          readOnly: true,
          hideLabel: true,
          preventMark: true,
          metaClass:'MessageGraph',
          // Turn off auto-spellchecking
          autoCreate: {tag: "textarea", autocomplete: "off", spellcheck: "false", style:"background-color:#DFE8F6;background-image:url()"}
        }
      ]
    };
    panelTabs[panelTabs.length] = tabPayload;

    if (queueMetaData.supportsBusinessEventLogs) {
      Jaffa.Messaging.CriteriaGroups.general = {};
      var tabLog = new Jaffa.Messaging.FinderContainer({
        ref: 'eventLogPanel',
        title: Labels.get('label.Jaffa.Messaging.BusinessEventLog'),
        resultsPanel: {
          ref: 'resultsPanel',
          header: false,
          listeners: {
            rowdblclick: function(grid, recId, evt){
              if (recId >= 0) {
                var pkVal = this.store.getAt(recId).data.logId;
                window.open(params.appCtx + '/jaffa/messaging/businesseventmaintenance/main.jsp?logId=' + encodeURIComponent(pkVal));
              }
              return true;
            }
          }
        },
        criteriaPanel: {
          hidden: true
        },
        criteria: {},
        load: function(){
          this.resultsPanel.bottomToolbar.getComponent('excel').enable();
        }
      });
      panelTabs[panelTabs.length] = tabLog;
    }

    config = Ext.applyIf(config || {}, {
      metaClass: 'MessageGraph',
      id: messageId,
      title: messageId,
      frame: false,
      border: false,
      closable: true,
      layout: 'fit',
      items:[{
        xtype:'tabpanel',
        activeTab: 0,
        items: panelTabs,
        ref: 'tabs'
      }],
      plugins: [
        new Jaffa.maintenance.plugins.PanelLoadSave({
          loadData: function(){
            var messageService = new Ext.data.DWRProxy2(Jaffa_QM_QueueManager.messageQuery);
            messageService.load({resultGraphRules:['**'], type:{values:[this.type]}, messageId:{values: [this.messageId]}, status:{values: [this.status]}, queueSystemId:{values: [this.queueSystemId]}}, {
              read: function(r){
                return r;
              }
            }, function(response){
              if (response && response.graphs && response.graphs.length > 0){
                var data = response.graphs[0];
                this.controller.setPanelFields(this, data);
                if (this.tabs.general) {
                  this.tabs.general.items.each(function(field){
                    if (field.appFieldMapping && data.applicationFields){
                      for (var i = 0; i< data.applicationFields.length; i++){
                        if (data.applicationFields[i].name==field.appFieldMapping)
                          field.setValue(data.applicationFields[i].value);
                      }
                    }
                  })
                }
                if (this.tabs.techFields){
                  this.tabs.techFields.items.each(function(field){
                    this.tabs.techFields.remove(field);
                  }, this);
                  if (data.technicalFields) {
                    if (data.technicalFields.length > 0) {
                      for (var i = 0; i < data.technicalFields.length; i++) {
                        this.tabs.techFields.add({
                          xtype: 'textfield',
                          value: data.technicalFields[i].value,
                          fieldLabel: data.technicalFields[i].name,
                          textOnly: true
                        });
                        if (data.technicalFields[i].name=='jaffa_originalMessageId') this.originalMessageId = data.technicalFields[i].value;
                      }
                    }
                  }
                  if (this.tabs.techFields.isVisible()){
                    this.tabs.techFields.doLayout();
                  }else{
                    this.tabs.techFields.on('activate', function(){
                      this.doLayout();
                    },this.tabs.techFields,{single: true})
                  }
                }
                if (this.tabs.eventLogPanel){
                  if (this.tabs.eventLogPanel.isVisible()){
                    Ext.apply(this.tabs.eventLogPanel.criteriaPanel.baseParams, {type:{values:[this.type]}, queueSystemId:{values: [this.queueSystemId]}});
                    this.tabs.eventLogPanel.criteriaPanel.search();
                  }else{
                    this.tabs.eventLogPanel.on('activate', function(){
                      Ext.apply(this.tabs.eventLogPanel.criteriaPanel.baseParams, {messageId: {values:[this.originalMessageId||this.messageId]}});
                      this.tabs.eventLogPanel.criteriaPanel.search();
                    },this,{single: true})
                  }
                }
                if (this.tabs.tabDepend){
                  this.tabs.tabDepend.getStore().removeAll();
                  this.tabs.tabDepend.getStore().loadData(data.messageDependencies||[]);
                }
              } else {
                this.setTitle('<font color="red"><i>' + this.messageId + '</i></font>');
              }
            }, this);
          },
          beforerenderHandler: function (c) {
            Jaffa.maintenance.plugins.GridLoadSave.superclass.beforerenderHandler.apply(this, arguments);
          },
          scope: this
        })
      ]
    });

    Jaffa.QM.QueueTransactionDetail.superclass.constructor.call(this, config);

    config.controller.applyPanelFieldsMetaRules(this);
  },

  setDirty: Ext.emptyFn
});
