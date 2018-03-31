/**
 * This class is used to render a few recent attachments for an object,
 * as identified by it's serializedKey.
 * Instances of this class are designed to render as an accordion.
 * @class Jaffa.attachment.Accordion
 */
Jaffa.attachment.Accordion = Ext.extend(Ext.grid.GridPanel, {
  
  /** The number of records to retrieve. A value <= 0 will result in retrieval of all records. */
  objectLimit: 5,
  buttonErrorTitle: Labels.get('label.jaffaRIA.MessageBox.AlertErrorMsg'),
  deleteButtonSelectAtLeastOneRecordText: Labels.get('label.jaffa.jaffaRIA.attachment.Accordion.deleteButtonSelectAtLeastOneRecordText'),
   /**
   * @cfg {Boolean} allowUpload
   * true to allow the user to upload new attachments and enables the allowUpload button (defaults to true)
   * false to disable the add button
   */
  allowUpload: true,
  
  /**
   * @cfg {Boolean} allowEdit
   * allows the user to edit records (defaults to true)
   */
  allowEdit: true,

  /**
   * @cfg {Boolean} allowDelete
   * true to allow the user to delete the selected attachments and enables the delete button (defaults to true)
   * false to disable the delete button
   */
  allowDelete: true,
    /**
   * @cfg {Boolean} allowAdd
   * true to allow the user to add the selected attachments and enables the add button (defaults to true)
   * false to disable the add button
   */
  allowAdd: true,
  
  descriptionText : Labels.get('label.jaffa.jaffaRIA.attachment.Accordion.Description'),
  
  remarksText : Labels.get('label.jaffa.jaffaRIA.attachment.Accordion.Remarks'),
  
  openAttachmentsTabQtipText: Labels.get('label.JaffaRIA.Maintenance.AttachmentsSection.OpenTip'),
  
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
      var e = "Jaffa.attachment.Accordion cannot be constructed since mandatory config parameter 'serializedKey' has not been passed";
      console.error(e);
      throw e;
    }
    //Apply default values to config
    Ext.applyIf(config,{allowUpload:true, allowDelete:true});
    
    this.addEvents(
      /**
       * @event datachanged
       * Fires after any record has been added or deleted.
       * @param {Object} this.
       */
      'datachanged'
    );
    
    // store for the grid
    if (config.objectLimit)
      this.objectLimit = config.objectLimit;
    var store = new Ext.data.Store({
      proxy: new Ext.data.DWRProxy(Jaffa_Attachment_AttachmentService.query),
      reader: new Ext.data.DwrReader({id: 'attachmentId'}, Jaffa.attachment.Record),
      baseParams: {serializedKey: {values: [this.serializedKey]}, resultGraphRules: ['-data', '*'], objectLimit: this.objectLimit, findTotalRecords: true, orderByFields: [{fieldName: 'createdOn', sortAscending: false}]},
      //sortInfo: {field: 'createdOn', direction: 'DESC'},
      listeners: {
        load: function (store, records, options) {
          if (!this._originalTitle)
            this._originalTitle = this.title;
          this.setTitle(this._originalTitle + ' (' + store.getTotalCount() + ')');
        },
        scope: this
      }
    });
    
    // the column model
    var sm = new Ext.grid.CheckboxSelectionModel();
    var columns = [
      sm,
      {
        dataIndex: 'originalFileName',
        renderer: function (originalFileName, cell, record, row_index, column_index, store) {
          // Display description or originalFileName, whichever is available
          var description = record.get('description');
          var displayData = description ? description : originalFileName;
          
          // Render originalFileName+description+remarks as qtip
          var remarks = record.get('remarks');
          var qtip = originalFileName;
          if (description != null && description != '')
            qtip += '<BR>'+this.descriptionText+': ' + description;
          if (remarks != null && remarks != '')
            qtip += '<BR>'+this.remarksText+': ' + remarks;
          cell.attr = Ext.QuickTips.isEnabled() ? 'ext:qtip="' + Ext.util.Format.htmlEncode(qtip) + '"' : 'title="' + qtip + '"';
          
          // Render hyperlink, based on the attachmentType
          var attachmentType = record.get('attachmentType');
          switch (attachmentType) {
            case 'W':
              return '<a target="_blank" href="' + originalFileName + '">' + displayData + '</a>';
            case 'E':
            case 'D':			
              var attachmentId = record.get('attachmentId');
              return '<a target="_blank" href="js/extjs/jaffa/attachment/service.jsp?command=viewAttachmentData&attachmentId=' + attachmentId + '">' + displayData + '</a>';
            default:
              return displayData;
          }
        }
      }
    ];
    
    // additional buttons in the header
    var tools = [
      {
        id: 'maximize',
        qtip:this.openAttachmentsTabQtipText,
        scope: this,
        handler: function () {
          // Create a new Tab, if it has not been opened yet, or else re-focus to the existing Tab
          var viewport = this.viewport || Ext.getCmp('viewport');
          if (viewport.bodyPanel.isXType('maintenancewizardpanel')) 
            viewport = viewport.bodyPanel.layout.activeItem;

          var tabPanel = viewport.bodyPanel.findById(config.tabConfig && config.tabConfig.itemId ? config.tabConfig.itemId : 'Jaffa_attachment_Container');
          if (!tabPanel) {
            var tabConfig = config.tabConfig || {};
            Ext.applyIf(tabConfig, {
              serializedKey: this.serializedKey,
              itemId: 'Jaffa_attachment_Container',
              ref : 'attachmentGridContainer',
              titleToken: 'label.JaffaRIA.Maintenance.AttachmentsTab',
              closable: true,
              allowUpload : this.allowUpload,
              allowEdit : this.allowEdit,
              allowAdd : this.allowAdd,
              allowDelete : this.allowDelete
            });
            tabPanel = new Jaffa.attachment.Container(tabConfig);
            viewport.addPanel(tabPanel);
            
            // Create listeners between this Accordion and the Tab, so that the data between the two is synchronized.
            // Invoke the mon() method on the Tab for creation of listeners, thus ensuring that the listeners
            // are automatically destroyed when the Tab is closed.
            tabPanel.mon(this, 'datachanged', function () {
              tabPanel.reload();
            }, this);
            tabPanel.mon(tabPanel, 'datachanged', function () {
              this.reload();
            }, this);
          }
          viewport.bodyPanel.activate(tabPanel);
        }
      }
    ];
    
    // top toolbar
    var tbar = [
      {
        disabled: !security.hasAttachmentMaintenance || config.allowUpload===false,
        iconCls: 'ext-ux-uploaddialog-uploadstartbtn',
        textToken: 'label.Jaffa.Widgets.Button.Upload',
        scope: this,
        itemId : 'uploadBtn',
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
        disabled: !security.hasAttachmentMaintenance || config.allowDelete===false,
        iconCls: 'delete',
        textToken: 'label.Jaffa.Widgets.Button.Delete',
        scope: this,
        itemId : 'deleteBtn',
        handler: function () {
          if (this.getSelectionModel().getCount() > 0) {
            Ext.MessageBox.show({
              titleToken: 'title.JaffaRIA.Maintenance.DeleteSelected',
              msgToken: 'label.JaffaRIA.Maintenance.DeleteSelected',
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
    config = Ext.applyIf(config, {
      plugins: new Ext.ux.plugins.MetaColumns({columns: columns, record: Jaffa.attachment.Record}),
      sm: sm,
      ds: store,
      titleToken: 'label.JaffaRIA.Maintenance.AttachmentsSection',
      hideHeaders: true,
      tools: tools,
      tbar: tbar
    });
    
    // Construct the grid
    Jaffa.attachment.Accordion.superclass.constructor.call(this, config);
    
    this.on('expand', this.reload);
    
    // Attach a listener to the 'refresh' event of the header
    this.on('render', function() {
      var viewport = this.viewport || Ext.getCmp('viewport');
      if (viewport) {
        if (viewport.header) {
          viewport.header.on('refresh', this.reload, this);
        } else if (!viewport.rendered) {
          viewport.on('render', function(vpt) {
            if (vpt.header) {
              vpt.header.on('refresh', this.reload, this);
            }
          }, this, {single: true});
        } else {
          console.info('Unknow state detected in Accordion.js');
        }
      } else {
        console.info('view port not found in Accordion.js');
      }
    }, this, {single: true});
  },
  
  /**
   * Loads the Grid
   */
  loadData: function() {
    this.getStore().load();
  },
  
  /**
   * Reloads the Grid
   */
  reload: function () {
    this.getStore().reload();
  }
});

Ext.reg('attachmentaccordion', Jaffa.attachment.Accordion);
