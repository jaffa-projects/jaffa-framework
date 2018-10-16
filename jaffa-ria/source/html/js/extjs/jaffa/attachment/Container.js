/**
 * This class is used to manage Attachments.
 * It renders existing Attachments inside a Grid.
 * It also supports a details Panel for creating/updating Attachments.
 * @class Jaffa.attachment.Container
 */
Jaffa.attachment.Container = Ext.extend(Ext.Panel, {
	
	discardUnsavedDataChangesMsgText: Labels.get('label.jaffaRIA.MessageBox.DiscardUnsavedDataChangesMsg'),
	
  /**
   * Constructs a new instance.
   * @param {Object} config At the minimum, the config should contain a serializedKey for loading the related Attachment records.
   */
  constructor: function (config) {
    //ensure that the required metadata has been loaded
    Jaffa.attachment.Util.loadMetaData();
    
    // Stamp the serializedKey as a field on this instance
    this.serializedKey = config ? config.serializedKey : null;
    if (!this.serializedKey) {
      var e = "Jaffa.attachment.Container cannot be constructed since mandatory config parameter 'serializedKey' has not been passed";
      console.error(e);
      throw e;
    }
    
    this.addEvents(
      /**
       * @event datachanged
       * Fires after any record has been added, updated or deleted.
       * @param {Object} this.
       */
      'datachanged'
    );

    // Create the Grid and maintain a reference to it
    var gridConfig = Ext.applyIf(config.gridConfig || {}, {
      serializedKey: this.serializedKey,
      region: 'center',
      allowUpload : config.allowUpload,
      allowEdit : config.allowEdit,
      allowAdd : config.allowAdd,
      allowDelete : config.allowDelete
    });
    this.grid = new Jaffa.attachment.Grid(gridConfig);
    
    // Create Panel using panelConfig
    var panelConfig = Ext.applyIf(config.panelConfig || {}, {
      serializedKey: this.serializedKey,
      frame: true,
      disabled: true,
      autoScroll: true,
      region: 'south',
      split: true,
      useSplitTips: true,
      header: true,
      collapsible: true,
      collapsed: true,
      allowContinue: false,
      allowEdit : config.allowEdit,
      height: 250
    });
    this.panel = new Jaffa.attachment.Panel(panelConfig);
    
    // alert if the user if a dirty panel is being deselected
    this.grid.getSelectionModel().addListener('beforerowselect', function (selectionModel, rowIndex, keepExisting, record) {
      if (this.panel.isDirty) {
        Ext.MessageBox.show({
          titleToken: 'title.JaffaRIA.Maintenance.DiscardChanges',
          msg: this.discardUnsavedDataChangesMsgText,
          buttons: Ext.MessageBox.YESNO,
          icon: Ext.MessageBox.QUESTION,
          scope: this,
          fn: function (btn) {
            if (btn == 'yes') {
              this.panel.unloadData();
              selectionModel.selectRow(rowIndex);
            }
          }
        });
        return false;
      }
    }, this);
    
    // display the attachment details in the panel only if a single row is selected. Else clear the panel
    this.grid.getSelectionModel().addListener('rowselect', function (selectionModel, rowIndex, record) {
      if (selectionModel.getCount() === 1)
        this.panel.loadGraph(record.json);
      else
        this.panel.unloadData();
    }, this);
    
    // if only a single row remains in the selection, then display it's details in the panel. Else clear the panel.
    this.grid.getSelectionModel().addListener('rowdeselect', function (selectionModel, rowIndex, record) {
      if (selectionModel.getCount() === 0)
        this.panel.unloadData();
      else if (selectionModel.getCount() === 1)
        this.panel.loadGraph(selectionModel.getSelected().json);
    }, this);

    // Create references between the grid and the panel
    this.grid.registerPanel(this.panel);
    this.panel.registerGrid(this.grid);
    
    // Listen to data changes in the Grid and Panel, and fire events
    this.grid.on('datachanged', function () {
      this.fireEvent('datachanged', this);
    }, this);
    this.panel.on('datachanged', function () {
      this.fireEvent('datachanged', this);
    }, this);
    
    // Construct this container
    config.items = [this.grid, this.panel];
    Ext.applyIf(config, {
      layout: 'border'
    });
    Jaffa.attachment.Container.superclass.constructor.call(this, config);
    
    // Attach a listener to the 'refresh' event of the header
    var viewport = this.viewport || Ext.getCmp('viewport');
    if (viewport && viewport.header) {
      this.mon(viewport.header, 'beforerefresh', function () {
        if (this.panel.isDirty) {
          Ext.MessageBox.show({
            titleToken: 'title.JaffaRIA.Maintenance.DiscardChanges',
            msg: this.discardUnsavedDataChangesMsgText,
            buttons: Ext.MessageBox.YESNO,
            icon: Ext.MessageBox.QUESTION,
            scope: this,
            fn: function (btn) {
              if (btn == 'yes') {
                this.panel.setDirty(false);
                viewport.header.onRefresh();
              }
            }
          });
          return false;
        }
      }, this);
      this.mon(viewport.header, 'refresh', function () {
        this.reload();
      }, this);
    }
  },
  
  /**
   * Loads the Grid
   */
  loadData: function () {
    this.grid.loadData();
  },
  
  /**
   * Reloads the Grid
   */
  reload: function () {
    this.grid.reload();
  },
  
  /**
   * Set or clear the dirty state of this panel
   * In both cases this will fire the "datamodified" event
   */
  setDirty: Jaffa.maintenance.plugins.Panel.prototype.setDirty
});


/**
 * This class is used to renders Attachments inside a Grid.
 * @class Jaffa.attachment.Grid
 */
Jaffa.attachment.Grid = Ext.extend(Ext.grid.GridPanel, {
  
  buttonErrorTitle: Labels.get('label.jaffa.jaffaRIA.attachment.Container.buttonErrorTitle'),
  
  deleteButtonSelectAtLeastOneRecordText: Labels.get('label.jaffa.jaffaRIA.attachment.Container.deleteButtonSelectAtLeastOneRecordText'),
  
  addButtonText: Labels.get('label.JaffaRIA.Button.Add'),
  
  uploadButtonText: Labels.get('label.JaffaRIA.Button.Upload'),
  
  deleteButtonText: Labels.get('label.JaffaRIA.Button.Delete'),
  
  modifyButtonText: Labels.get('label.JaffaRIA.Button.Modify'),
  
  deletePromptMessageText: Labels.get('label.JaffaRIA.Maintenance.DeleteSelected'),
  
  discardUnsavedDataChangesMsgText: Labels.get('label.jaffaRIA.MessageBox.DiscardUnsavedDataChangesMsg'),
  
  /**
   * Constructs a new instance.
   * @param {Object} config At the minimum, the config should contain a serializedKey for loading the related Attachment records.
   */
  constructor: function (config) {
    // Stamp the serializedKey as a field on this instance
    this.serializedKey = config ? config.serializedKey : null;
    if (!this.serializedKey) {
      var e = "Jaffa.attachment.Grid cannot be constructed since mandatory config parameter 'serializedKey' has not been passed";
      console.error(e);
      throw e;
    }
    
    this.addEvents(
      /**
       * @event datachanged
       * Fires after any record has been added, updated or deleted.
       * @param {Object} this.
       */
      'datachanged'
    );

    // store for the grid
    var store = new Ext.data.Store({
      proxy: new Ext.data.DWRProxy(Jaffa_Attachment_AttachmentService.query),
      reader: new Ext.data.DwrReader({id: 'attachmentId'}, Jaffa.attachment.Record),
      baseParams: {serializedKey: {values: [this.serializedKey]}, resultGraphRules: ['-data', '*'], objectLimit: -1, findTotalRecords: true},
      sortInfo: {field: 'createdOn', direction: 'DESC'}
    });
    
    // the column model
    var sm = new Ext.grid.CheckboxSelectionModel();
    var columns = [
      sm,
      {
        dataIndex: 'originalFileName',
        renderer: function (data, cell, record, row_index, column_index, store) {
          // Render hyperlink, based on the attachmentType
          var attachmentType = record.get('attachmentType');
          switch (attachmentType) {
            case 'W':
              return '<a target="_blank" href="' + data + '">' + data + '</a>';
            case 'E':
            case 'D':
              var attachmentId = record.get('attachmentId');
              return '<a target="_blank" href="js/extjs/jaffa/attachment/service.jsp?command=viewAttachmentData&attachmentId=' + attachmentId + '">' + data + '</a>';
            default:
              return data;
          }
        }
      }, {
        dataIndex: 'attachmentType',
        renderer: function (data, cell, record, row_index, column_index, store) {
          switch (data) {
            case 'E':
            case 'D':
              return 'Embedded';
            case 'W':
              return 'Web';
            case 'L':
              return 'Local';
            default:
              return data;
          }
        }
      },
      'contentType',
      'description',
      'remarks',
      {dataIndex: 'createdOn', renderer: Ext.util.Format.dateTimeRenderer()},
      'createdBy',
      {dataIndex: 'lastChangedOn', renderer: Ext.util.Format.dateTimeRenderer()},
      'lastChangedBy'
    ];
    
    // top toolbar
    var tbar = [
      {
        disabled: !security.hasAttachmentMaintenance || !config.allowUpload,
        iconCls: 'ext-ux-uploaddialog-uploadstartbtn',
        text: this.uploadButtonText,
        itemId : 'uploadBtn',
        scope: this,
        handler: function () {
          var dialog = new Jaffa.attachment.UploadDialog({
            serializedKey: this.serializedKey,
            modal: true,
            listeners: {
              uploadcomplete: function (dialog) {
                this.reload();
                this.fireEvent('datachanged', this);
              },
              scope: this
            }
          });
          dialog.show();
        }
      }, {
        disabled: !security.hasAttachmentMaintenance || !config.allowAdd,
        iconCls: 'add',
        text: this.addButtonText,
        itemId : 'addBtn',
        scope: this,
        handler: function () {
          if (this.panel) {
            if (this.panel.isDirty) {
              Ext.MessageBox.show({
                titleToken: 'title.JaffaRIA.Maintenance.DiscardChanges',
                msg: this.discardUnsavedDataChangesMsgText,
                buttons: Ext.MessageBox.YESNO,
                icon: Ext.MessageBox.QUESTION,
                scope: this,
                fn: function (btn) {
                  if (btn == 'yes') {
                    this.getSelectionModel().clearSelections();
                    this.panel.activateAddMode();
                  }
                }
              });
            } else {
              this.getSelectionModel().clearSelections();
              this.panel.activateAddMode();
            }
          } else {
            // This Grid is currently designed with an associated details panel. For now, do nothing
          }
        }
      }, {
        disabled: !security.hasAttachmentMaintenance || !config.allowDelete,
        iconCls: 'delete',
        text: this.deleteButtonText,
        itemId : 'deleteBtn',
        scope: this,
        handler: function () {
          if (this.getSelectionModel().getCount() > 0) {
            Ext.MessageBox.show({
              titleToken: 'title.JaffaRIA.Maintenance.DeleteSelected',
              msg: this.deletePromptMessageText,
              width: 400,
              buttons: Ext.MessageBox.YESNO,
              icon: Ext.MessageBox.QUESTION,
              scope: this,
              fn: function (btn) {
                if (btn == 'yes') {
                  // build the array of AttachmentGraph objects to be deleted
                  var graphs = [];
                  var recs = this.getSelectionModel().getSelections();
                  for (var i = 0; i < recs.length; i++) {
                    var rec = recs[i];
                    graphs.push({
                      attachmentId: rec.get('attachmentId'),
                      deleteObject: true
                    });
                  }
                  
                  // invoke the update() method on the AttachmentService
                  var proxy = new Jaffa.data.DWRCRUDProxy({update: Jaffa_Attachment_AttachmentService.update});
                  proxy.update(graphs, function (response) {
                    if (response && response.errors && response.errors.length > 0) {
                      Ext.MessageBox.show({
                        msg: Ext.util.Format.htmlEncode(response.errors[0].localizedMessage),
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.ERROR
                      });
                    } else {
                      this.reload();
                      this.fireEvent('datachanged', this);
                    }
                  }.createDelegate(this));
                }
              }
            });
          } else {
            Ext.MessageBox.alert(this.buttonErrorTitle, this.deleteButtonSelectAtLeastOneRecordText);
          }
        }
      }
    ];
    
    // Create grid config
    Ext.applyIf(config, {
      plugins: new Ext.ux.plugins.MetaColumns({columns: columns, record: Jaffa.attachment.Record}),
      sm: sm,
      ds: store,
      tbar: tbar,
      itemId: 'Jaffa_attachment_Grid',
      stateId: config.id || 'Jaffa_attachment_Grid'
    });
    
    Jaffa.attachment.Grid.superclass.constructor.call(this, config);
  },
  
  /**
   * Loads the Grid
   */
  loadData: function () {
    this.getStore().load();
  },
  
  /**
   * Reloads the Grid
   */
  reload: function () {
    this.getStore().reload();
  },
  
  /**
   * Maintains a reference to the input Panel.
   */
  registerPanel: function (panel) {
    this.panel = panel;
  }
});


/**
 * This class is used to create/update an Attachment.
 * @class Jaffa.attachment.Panel
 */
Jaffa.attachment.Panel = Ext.extend(Ext.form.FormPanel, {
	
	discardUnsavedDataChangesMsgText: Labels.get('label.jaffaRIA.MessageBox.DiscardUnsavedDataChangesMsg'),
	
	modifyButtonText: Labels.get('label.JaffaRIA.Button.Modify'),
	
	addButtonText: Labels.get('label.JaffaRIA.Button.Add'),
	
	clearButtonText: Labels.get('label.jaffaRIA.Button.Clear'),
	
	cancelButtonText: Labels.get('label.jaffaRIA.Button.Cancel'),
	
	saveButtonText: Labels.get('label.jaffaRIA.Button.Save'),
	
  /**
   * Constructs a new instance.
   * @param {Object} config The configuration for this Panel.
   */
  constructor: function (config) {
    // Stamp the serializedKey as a field on this instance
    this.serializedKey = config ? config.serializedKey : null;
    if (!this.serializedKey) {
      var e = "Jaffa.attachment.Panel cannot be constructed since mandatory config parameter 'serializedKey' has not been passed";
      console.error(e);
      throw e;
    }
    
    this.addEvents(
      /**
       * @event datachanged
       * Fires after any record has been added, updated or deleted.
       * @param {Object} this.
       */
      'datachanged'
    );

    // Create config
    config = Ext.applyIf(config || {}, {
      controller: new Jaffa.component.CRUDController({
        proxy: new Jaffa.data.DWRCRUDProxy({
          query: Jaffa_Attachment_AttachmentService.query,
          update: Jaffa_Attachment_AttachmentService.update
        }),
        reader: new Jaffa.data.DWRQueryResponseReader({id: 'attachmentId'}),
        listeners: {
          beforepostdata: function (controller) {
            // This listener is called before data is posted.
            //CREATE mode: Set default data for the non-rendered fields in CREATE mode
            var graph = controller.saveModel && controller.saveModel.length == 1 ? controller.saveModel[0] : null;
            if (graph) {
              if (!graph.attachmentId) {
                graph.serializedKey = this.serializedKey;
                graph.attachmentType = 'W';
              }
            }
          },
          save: function (controller, response) {
            // In CREATE mode, the response will contain the generated attachmentId.
            // Maintain a reference to it, so that the attachment can be reloaded or selected in the associated grid.
            if (response && response.length == 1 && response[0].source)
              this.attachmentId = response[0].source.attachmentId;
            if (this.grid)
              this.grid.reload();
            this.fireEvent('datachanged', this);
          },
          beforeload: function (controller) {
            // This listener is called immediately after a save. Create a criteria to reload the attachment.
            // However, in there is an associated grid, then there is no need to reload the attachment in this panel,
            // since the grid will be reloaded anyway, and the appropriate attachment will be automatically
            // selected, causing the freshing of data within this panel
            if (this.grid)
              return false;
            else
              controller.criteria = {attachmentId: {values: [this.attachmentId]}, resultGraphRules: ['-data', '*']};
          },
          load: function (controller) {
            // This listener is called after an attachment is loaded.
            if (controller.model && controller.model.getCount() == 1) {
              var graph = controller.model.itemAt(0);
              if (graph.attachmentId) {
                //UPDATE
                this.attachmentId = graph.attachmentId;
                this.setTitle(this.modifyButtonText);
                this.datesPanel.show();
                this.clearButton.hide();
              } else {
                //CREATE
                delete attachmentId;
                this.setTitle(this.addButtonText);
                this.datesPanel.hide();
                this.clearButton.show();
              }
              this.originalFileName.label.dom.innerHTML = !graph.attachmentType || graph.attachmentType === 'W' ? Labels.get('label.jaffa.jaffaRIA.attachment.Container.WebLink') : Labels.get('label.jaffa.jaffaRIA.attachment.Container.FileName');
              this.setDisabled(!security.hasAttachmentMaintenance || (graph.attachmentId && this.allowEdit == false));
              if (this.collapsed)
                this.expand();
            } else {
              //default
              delete attachmentId;
              this.setTitle('');
              this.datesPanel.hide();
              this.clearButton.show();
              this.originalFileName.label.dom.innerHTML = Labels.get('label.jaffa.jaffaRIA.attachment.Container.WebLink');
              this.setDisabled(true);
            }
            this.saveButton.disable();
          },
          scope: this
        }
      }),
      plugins: new Jaffa.maintenance.plugins.PanelLoadSave(),
      metaClass: 'AttachmentGraph',
      itemId: 'Jaffa_attachment_Panel',
      stateId: config.id || 'Jaffa_attachment_Panel'
    });
    
    // The date-fields are added in another panel, so that hey can be easily hidden/unhidden
    config.items = [
      {mapping: 'originalFileName', ref: 'originalFileName', fieldLabel: Labels.get('label.jaffa.jaffaRIA.attachment.Container.WebLink')},
      {mapping: 'description'},
      {mapping: 'remarks'},
      {
        xtype: 'form',
        ref: 'datesPanel',
        hidden: true,
        items: [
          {mapping: 'createdOn', textOnly: true},
          {mapping: 'createdBy', textOnly: true},
          {mapping: 'lastChangedOn', textOnly: true},
          {mapping: 'lastChangedBy', textOnly: true}
        ]
      }
    ];
    
    config.bbar = [
      {
        text: this.clearButtonText,
        iconCls: 'clear',
        ref: '../clearButton',
        scope: this,
        handler: function() {
          this.controller.clearPanelFields(this);
        }
      }, {
        text: this.cancelButtonText,
        iconCls: 'cancel',
        scope: this,
        handler: function() {
          if (this.isDirty) {
            Ext.MessageBox.show({
              titleToken: 'title.JaffaRIA.Maintenance.DiscardChanges',
              msg: this.discardUnsavedDataChangesMsgText,
              buttons: Ext.MessageBox.YESNO,
              icon: Ext.MessageBox.QUESTION,
              scope: this,
              fn: function (btn) {
                if (btn == 'yes') {
                  this.unloadData();
                  if (this.grid)
                    this.grid.getSelectionModel().clearSelections();
                }
              }
            });
          } else {
              this.unloadData();
              if (this.grid)
                this.grid.getSelectionModel().clearSelections();
            }
        }
      }, {
        disabled: true,
        text: this.saveButtonText,
        iconCls: 'save',
        ref: '../saveButton',
        scope: this,
        handler: function() {
          this.controller.save();
        }
      }
    ];
    
    Jaffa.attachment.Panel.superclass.constructor.call(this, config);
    Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(this);
    
    // Listen to changes on the Panel so we can add a "modified" indicator in the title
    this.on('datamodified', function () {
      if (this.isDirty) {
        this.title_clean = this.title;
        this.setTitle('*' + this.title);
        if (security.hasAttachmentMaintenance)
          this.saveButton.enable();
      } else if (this.title_clean) {
        this.setTitle(this.title_clean);
        delete this.title_clean;
        this.saveButton.disable();
      }
    }, this);
  },
  
  /** Loads the input Graph into this Panel. */
  loadGraph: function (graph) {
    this.controller._onLoad({records: [graph]});
  },
  
  /** Unloads data from this Panel. */
  unloadData: function () {
    this.controller._onLoad();
  },
  
  /** Prepares this panel to be in 'add' mode. */
  activateAddMode: function () {
    this.controller._onLoad({records: [{className: 'AttachmentGraph'}]});
  },
  
  /**
   * Maintains a reference to the input Grid.
   * Attaches the necessary listeners to that Grid.
   */
  registerGrid: function (grid) {
    this.grid = grid;
    // attach a listener to the grid's view; but only once. the listener will select the appropriate record in the grid.
    if (!this._gridViewRefreshListener) {
      this._gridViewRefreshListener = function (view) {
        if (this.attachmentId) {
          var rowIndex = this.grid.getStore().find('attachmentId', this.attachmentId);
          if (rowIndex >= 0)
            this.grid.getSelectionModel().selectRow(rowIndex);
          else
            this.unloadData();
        }
      };
      this.grid.getView().on('refresh', this._gridViewRefreshListener, this);
    }
  }
});
