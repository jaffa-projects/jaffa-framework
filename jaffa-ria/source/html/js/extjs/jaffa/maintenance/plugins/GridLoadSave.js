/**
 * @class Jaffa.maintenance.plugins.GridLoadSave
 *
 * An instance of this class can be used to apply Load and Save behavior
 * to any maintenance grid.
 */
Jaffa.maintenance.plugins.GridLoadSave = Ext.extend(Jaffa.maintenance.plugins.Panel, {
  bodyCfg: null, // SeanZ: removed because indent cls causes scrollbar to disappear in IE8 (ExtJs 3.2.2).
  
  /**
   * ExtJS invokes the init function of each plugin after the component is created.
   * This function will apply the necessary Load and Save behavior to the input Panel.
   * @param c A component, usually a Panel, for which this plugin was created.
   */
  init: function (c) {
    Jaffa.maintenance.plugins.GridLoadSave.superclass.init.apply(this, arguments);

    Ext.applyIf(c, {
      findDataSource: this.findDataSource,
      addRecord: this.addRecord,
      removeRecord: this.removeRecord,
      doLoadData: this.doLoadData
    });

    // Register a listener to perform cleanup after a component is removed
    c.on('beforedestroy', function (c) {
      if (c.controller)
        c.controller.unregisterPanel(c, c.findDataSource());
    });
  },

  /**
   * @protected
   * The init() method registers this handler with the component's 'beforerender' event.
   * By the time this handler is invoked, the ownerCt would have been stamped on the component,
   * allowing us to lookup the metaClass and controller, if they were not passed initially.
   * This handler then performs the initialization based on metaClass and controller.
   * @param c A component, usually a Grid, for which this plugin was created.
   */
  beforerenderHandler: function (c) {
    Jaffa.maintenance.plugins.GridLoadSave.superclass.beforerenderHandler.apply(this, arguments);

    // NOTE: The following code is similar to the one in Jaffa.maintenance.Viewport.addTabPanel() method
    var dataSource = c.findDataSource();

    // Propagate the metadata class name from the data to the component if available
    if (dataSource && !c.metaClass) {
      if (Ext.isArray(dataSource)) {
        if (dataSource[0])
          c.metaClass = dataSource[0].className;
      } else if (dataSource.itemAt && typeof dataSource.itemAt === 'function') {
        if (dataSource.itemAt(0))
          c.metaClass = dataSource.itemAt(0).className;
      } else {
        c.metaClass = dataSource.className;
      }
    }

    if (c.controller) {
      // invoke the load event if the controller already had data, else initialize the fields of the panel
      if (c.controller.isLoaded)
        c.loadData();
      else
        c.controller.setPanelFields(c);
    }

    // Disable primary key fields for existing records
    if (c.metaClass && ClassMetaData[c.metaClass] && ClassMetaData[c.metaClass].key && typeof ClassMetaData[c.metaClass].key === 'string') {
      c.on('beforeedit', function (e) {
        if (!e.record.isNew && e.field === ClassMetaData[this.metaClass].key)
          return false;
      }, c);
    }
  },

  readOnly: false,

  /**
   * A general load method that is automatically wired in to the controller's
   * onload event as part of the panels registration.
   */
  doLoadData: function(){
    if (this.isDirty)
      this.onSaveSuccess();

    this.getStore().removeAll();
    this.getStore().removed = [];
    this.getStore().modified = [];
    this.controller.applyGridColumnMetaRules(this);
    var ds = this.findDataSource();
    if (ds) {
      if (Ext.isArray(ds)) {
	    // handling the circumstance that the array of graphs are not converted to mixed collections
        console.debug('Loading store ', ds);
        this.getStore().loadData(ds);
        console.debug('Store is ', this.getStore());
      } else {
        console.debug('Loading store ', ds.getRange());
        this.getStore().loadData(ds.getRange());
        console.debug('Store is ', this.getStore());
      }
    }

    if (this.rendered)
      this.getView().refresh();
  },

  loadData: function() {
    this.doLoadData();
    this.fireEvent('load', this);
  },

  /**
   * A general validation method that is invoked to see if this panel's
   * data can be saved without errors. This is invoked because (a) a save
   * has been invoked on the controller related to this panel, and (b) because
   * this panel when created registered its self with the controller
   *
   * @return {String/Boolean} Returns a 'null' string or 'true' if all vaildation passes
   *          If it fails a HTML string containing the errors to be displayed can be returned,
   *          or a 'false' can be return if this routine takes care of the error display.
   *
   * @DEPRECATES tabValidate
   */
  validateData: function() {
    if (!this.controller.validateGrid(this))
      return false;
    return true;
  },

  /**
   * This packages up the data to saved from this panel, the original data object is provided
   * so a delta between it and the panel's values so only changed values need to be saved.
   *
   *
   * @param {Object} origData This is the original graph data object this panel is changing
   * @param {Object} saveData This object should be modifed by this function to contain a copy of
   *                 the origData including all the changes made to the panel.
   *                 It is recommended that you only include the fields that have changed,
   *                 assuming your back end service can support this.
   * @return {boolean} Return true if there is a change in the data, ie something needs
   *          to be saved
   *
   * @DEPRECATES tabSave
   */
  saveData: function(origData, saveData) {
    var output = this.controller.getModifiedPanelStore(origData, saveData, this.getStore(), this.metaClass);
    console.debug("GridPanel.saveData: ", origData, saveData, this.metaClass, output);
    return output;
  },

  /**
   * This method is used to locate the data source associated to this panel by
   * the {@link #load} method. On reload of a previously registered panel this method
   * is also used by the controller to register this panel with it's data source.
   *
   * @return {Ext.util.MixedCollection} Returns first entry in array of objects in the associated controller's model
   */
  findDataSource: function(){
    return this.controller.model;
  },

  /**
   * A convenience method to add the input record to the grid.
   * @param {Jaffa.data.Record} record A record.
   */
  addRecord: function (record) {
    record.isNew = true;
    this.getStore().add(record);
    if (this.startEditing) this.startEditing(this.getStore().getCount() - 1, 1);
  },

  /**
   * A convenience method to remove the input record from the grid.
   * If the input record already exists in the model, then that corresponding object will be marked for deletion.
   * @param {Jaffa.data.Record} record A record.
   */
  removeRecord: function (record) {
    this.getStore().deleteRecord(record);
    if(this.activeEditor) {
      this.activeEditor.hideEdit();
      this.activeEditor.field.initialized = false;
      this.activeEditor = null;
    } 
  },
  
  
  setDirty: function(dirty) {
    if (!dirty) {
      this.store.each(function(rec){
        rec.commit(true);
      });
    }
    Jaffa.maintenance.plugins.GridLoadSave.superclass.setDirty.call(this, dirty);
  }
  
});

Ext.preg('gridloadsave', Jaffa.maintenance.plugins.GridLoadSave);
