/**
 * The following is a copy of Ext.ux.UploadDialog.FileRecord.
 * It contains the additional fields: contentType, description and remarks
 * @class Jaffa.attachment.FileRecord
 */
Jaffa.attachment.FileRecord = Ext.data.Record.create([
  {name: 'filename'},
  {name: 'contentType'},
  {name: 'description'},
  {name: 'remarks'},
  {name: 'state', type: 'int'},
  {name: 'note'},
  {name: 'input_element'},
  {name: 'params'}
]);
Jaffa.attachment.FileRecord.STATE_QUEUE = Ext.ux.UploadDialog.FileRecord.STATE_QUEUE;
Jaffa.attachment.FileRecord.STATE_FINISHED = Ext.ux.UploadDialog.FileRecord.STATE_FINISHED;
Jaffa.attachment.FileRecord.STATE_FAILED = Ext.ux.UploadDialog.FileRecord.STATE_FAILED;
Jaffa.attachment.FileRecord.STATE_PROCESSING = Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING;


/**
 * An extension to Ext.ux.UploadDialog.Dialog, this class includes 3 additional editable fields
 * in the grid; viz. contentType, description and remarks.
 *
 * This class can be used to perform multi-file uploads, creating an Attachment record for each file.
 *
 * @class Jaffa.attachment.UploadDialog
 */
Jaffa.attachment.UploadDialog = Ext.extend(Ext.ux.UploadDialog.Dialog, {
  /**
   * Constructs a new instance.
   * @param {Object} config At the minimum, the config should contain a serializedKey, which will be stamped on the Attachment record.
   */
  constructor: function (config) {
    // Stamp the serializedKey as a field on this instance
    this.serializedKey = config ? config.serializedKey : null;
    if (!this.serializedKey) {
      var e = "Jaffa.attachment.UploadDialog cannot be constructed since mandatory config parameter 'serializedKey' has not been passed";
      console.error(e);
      throw e;
    }
    
    Ext.applyIf(config, {
      url: 'js/extjs/jaffa/attachment/service.jsp',
      width: 600
    });
    Jaffa.attachment.UploadDialog.superclass.constructor.call(this, config);
    
    //attach a listener to pass the user-entered data during the file-upload
    this.on('beforefileuploadstart', function (dialog, filename, record) {
      var params = Ext.applyIf(record.get('params') || {}, dialog.base_params || dialog.baseParams || dialog.params);
      var v;
      v = record.get('contentType');
      if (v != null && v != '')
        params.contentType = v;
      v = record.get('description');
      if (v != null && v != '')
        params.description = v;
      v = record.get('remarks');
      if (v != null && v != '')
        params.remarks = v;
      params.serializedKey = this.serializedKey;
      params.command = 'update';
      record.set('params', params);
    });
  },
  
  /**
   * The following is a copy of the similarly named method of the base class, with the following additions:
   * - It uses Jaffa.attachment.FileRecord as the Record for the store.
   * - Creates a ColumnModel with the additional fields: contentType, description and remarks.
   * - It creates an editable grid.
   */
  createGrid: function() {
    var store = new Ext.data.Store({
      proxy: new Ext.data.MemoryProxy([]),
      reader: new Ext.data.JsonReader({}, Jaffa.attachment.FileRecord),
      sortInfo: {field: 'state', direction: 'DESC'},
      pruneModifiedRecords: true
    });
    
    var cm = new Ext.grid.ColumnModel([
      {
        header: this.i18n.state_col_title,
        width: this.i18n.state_col_width,
        resizable: false,
        dataIndex: 'state',
        sortable: true,
        renderer: this.renderStateCell.createDelegate(this)
      }, {
        header: this.i18n.filename_col_title,
        width: this.i18n.filename_col_width,
        dataIndex: 'filename',
        sortable: true,
        renderer: this.renderFilenameCell.createDelegate(this)
      }, {
        header: this.i18n.contentType_col_title,
        width: this.i18n.contentType_col_width,
        dataIndex: 'contentType',
        sortable: true,
        editor: new Ext.form.TextField()
      }, {
        header: this.i18n.description_col_title,
        width: this.i18n.description_col_width,
        dataIndex: 'description',
        sortable: true,
        editor: new Ext.form.TextField()
      }, {
        header: this.i18n.remarks_col_title,
        width: this.i18n.remarks_col_width,
        dataIndex: 'remarks',
        sortable: true,
        editor: new Ext.form.TextField()
      }, {
        header: this.i18n.note_col_title,
        width: this.i18n.note_col_width, 
        dataIndex: 'note',
        sortable: true,
        renderer: this.renderNoteCell.createDelegate(this)
      }
    ]);
  
    this.grid_panel = new Ext.grid.EditorGridPanel({
      stateId: 'attachmentsUploadDialogGridPanel',
      clicksToEdit: 1,
      sm: new Ext.grid.RowSelectionModel(), //ensures that rows(s) can be selected and removed
      view: new Ext.grid.GridView({markDirty: false}), //ensures that the cells are never marked as dirty
      
      ds: store,
      cm: cm,
      x: 0,
      y: 22,
      anchor: '0 0',
      border: true,
      viewConfig: {
        autoFill: true,
        forceFit: true
      },
      bbar : new Ext.Toolbar()
    });
    this.grid_panel.on('render', this.onGridRender, this);
    
    //attach a listener to ensure that the row is editable only initially. it should not be editable after an upload has been attempted.
    this.grid_panel.on('beforeedit', function (e) {
      return e.record.get('state') == Jaffa.attachment.FileRecord.STATE_QUEUE;
    }, this);
    
    this.add(this.grid_panel);
    
    this.grid_panel.getSelectionModel().on('selectionchange', this.onGridSelectionChange, this);
  },
  
  /**
   * The following is a copy of the similarly named method of the base class, with the following additions:
   * - It uses Jaffa.attachment.FileRecord as the Record for the store.
   */
  addFileToUploadQueue: function (browse_btn) {
    var input_file = browse_btn.detachInputFile();
    
    input_file.appendTo(this.form);
    input_file.setStyle('width', '100px');
    input_file.dom.disabled = true;
    
    var store = this.grid_panel.getStore();
    store.add(
      new Jaffa.attachment.FileRecord({
          state: Ext.ux.UploadDialog.FileRecord.STATE_QUEUE,
          filename: input_file.dom.value.replace('C:\\fakepath\\', ''),
          note: this.i18n.note_queued_to_upload,
          input_element: input_file
        })
      );
    this.fsa.postEvent('file-added', input_file.dom.value);
  }
});


/**
 * Create a copy of the base class' i18n field, add support for the new fields, and then
 * stamp the field on to the prototype of this class.
 */
var i18n = Ext.apply({}, Ext.ux.UploadDialog.Dialog.prototype.i18n);
Ext.apply(i18n, {
  contentType_col_title: Labels.get('label.jaffa.jaffaRIA.attachment.UploadDialog.contentType_col_title'),
  contentType_col_width: 150,
  description_col_title: Labels.get('label.jaffa.jaffaRIA.attachment.UploadDialog.description_col_title'),
  description_col_width: 200,
  remarks_col_title: Labels.get('label.jaffa.jaffaRIA.attachment.UploadDialog.remarks_col_title'),
  remarks_col_width: 300
});
Jaffa.attachment.UploadDialog.prototype.i18n = i18n;
