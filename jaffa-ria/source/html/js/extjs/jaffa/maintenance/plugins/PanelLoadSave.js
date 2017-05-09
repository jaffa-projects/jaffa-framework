/**
 * @class Jaffa.maintenance.plugins.PanelLoadSave
 *
 * An instance of this class can be used to apply Load and Save behavior
 * to any maintenance panel.
 */
Jaffa.maintenance.plugins.PanelLoadSave = Ext.extend(Jaffa.maintenance.plugins.Panel, {
  /**
   * ExtJS invokes the init function of each plugin after the component is created.
   * This function will apply the necessary Load and Save behavior to the input Panel.
   * @param c A component, usually a Panel, for which this plugin was created.
   */
  init: function (c) {
    Jaffa.maintenance.plugins.PanelLoadSave.superclass.init.apply(this, arguments);

    if (this.autoScroll)
      c.autoScroll = this.autoScroll;

    Ext.applyIf(c, {
      isNew: this.isNew,
      findDataSource: this.findDataSource,
      doLoadData: this.doLoadData
      ,setKeyFieldsEditable: this.setKeyFieldsEditable
    });

    if (!this.readOnly) {
      Ext.applyIf(c, {
        createData: this.createData
      });
    }

    c.addEvents(
      /**
       * @event load
       * Fires after the panel has loaded data.
       * @param {Object} this
       */
      'load',

      /**
       * @event save
       * Fires after the save has been successful.
       * @param {Object} this
       */
      'save',

      /**
       * @event validatedata
       * Fires before throwing validation error message. User can add custom message using the options object
       * @param {Object} this
       */
      'validatedata'
    );
  },

  autoScroll: true,

  /**
   * @cfg {boolean} isNew
   * This indicates if the Panel is being used to create a record.
   */
  isNew: false,

  /**
   * @cfg {boolean} readOnly
   * Change listeners will not be registered for a readOnly panel.
   */
  readOnly: false,

  /**
   * @protected
   * Make the process of loadData() extensible because the 'load' event needs to be fired at the end of load.
   */
  doLoadData: function() {
    this.controller.clearPanelFields(this);
    if(this.fireEvent("beforeload", this, this.findDataSource()) !== false){
      if (this.isDirty)
        this.onSaveSuccess();

      this.controller.setPanelFields(this, this.findDataSource());

      // Disable primary key fields for existing records
      this.setKeyFieldsEditable(false);

      // reset the isNew flag to support subsequent saves after a creation of a new record
      this.isNew = false;
    }
  },
  
  setKeyFieldsEditable: function(makeEditable) {
    if (makeEditable !== false) makeEditable = true;
    // expose primary key fields to be used in create mode
    if (ClassMetaData[this.metaClass] && ClassMetaData[this.metaClass].key) {
      var keys = ClassMetaData[this.metaClass].key;
      if (typeof keys == 'string') keys = [keys];
      for (var i=0; i<keys.length; i++) {
        var keyField = this.find("mapping", keys[i]);
        if (keyField && keyField.length == 1)
          keyField[0].setTextOnly(!makeEditable);
      }
    }
  },

  /**
   * A general load method that is automatically wired in to the controller's
   * onload event as part of the panels registration. This method generally will
   * use the panel's {@link #findDataSource} method to figure out what data to
   * load into the panel. For this to work the "mapping" properties on the widgets
   * in the panel should be based on the object returned as the data source.
   * <p>
   * By default this method will make "textOnly" any field on this panel that is specified
   * as a "key" field in its class metadata definition.
   */
  loadData: function () {
    this.doLoadData();
    this.fireEvent("load", this);
  },

  /**
   * A general validation method that is invoked to see if this panel's
   * data can be saved without errors. This is invoked because (a) a save
   * has been invoked on the controller related to this panel, and (b) because
   * this panel when created registered its self with the controller
   *
   * @return {String} Returns a null string if all vaildation pass, or
   *         a HTML string containing the errors to be displayed     *
   * @DEPRECATES tabValidate
   */
  validateData: function () {
    var msg = this.controller.validatePanelFields(this);
    var options = {msg : msg};
    this.fireEvent('validatedata',this, options);
    return options.msg;
  },

  /**
   * This packages up the data to saved from this panel, the original data object is provided
   * so a delta between it and the panel's values so only changed values need to be saved.
   *
   * If the 'isDelete' flag is set to true on the panel, the object will be marked for deletion
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
  saveData: function (origData, saveData) {
    // Propagate the isDelete flag so the source object will be deleted
    if (this.isDelete) {
      saveData.deleteObject = true;
      this.isDelete = false;
      return true;
    }
    return this.controller.getModifiedPanelFields(this, saveData, origData);
  },

  /**
   * This method is used to locate the data source associated to this panel by
   * the {@link #load} method. On reload of a previously registered panel this method
   * is also used by the controller to register this panel with it's data source.
   *
   * @return {Object} Returns first entry in array of objects in the associated controller's model
   */
  findDataSource: function () {
    var output;
      if (this.controller && this.controller.model) {
        if (this.controller.model.getCount() == 1) {
          output = this.controller.model.itemAt(0);
        } else {
          output = this.controller.model;
        }
      } else {
        output = new Ext.util.MixedCollection();
        if (this.controller) {
          this.controller.model = output;
        }
        this.isNew = true;
      }
    return output;
  },

  /**
   * (Optional) Implement if data added to this panel should be saved.
   *
   * This packages up the data to saved from this panel, the original data object is provided
   * so a delta between it and the panel's values so only changed values need to be saved.
   *
   * If this Panel contains new data, the Panel is registered with a Collection, as opposed
   * to the instance being modified. Therefore the difference between the data passed here, compared
   * to the saveData() method, is that the origData is the array of these kinds of records, and
   * saveData is also the array of records to save.
   *
   * @param {Object} origData This is the original array of graph data objects.
   * @param {Object} saveData This array should have the new object appended to it.
   */
  createData: function (origData, saveData) {
    var child, exists = false;
    // @TODO: Lookup existing object in saveData. Reuse, if present
    if (!exists)
      child = {};
    var updated = this.saveData(null, child);
    if (updated && !exists)
      saveData[saveData.length] = child;
    return updated;
  }
});


/**
 * Experimental. Do not use.
 * @param {Object} c
 */
Jaffa.maintenance.plugins.StoreLoadSave = Ext.extend(Jaffa.maintenance.plugins.PanelLoadSave, {
  beforerenderHandler: function (c) {
    Jaffa.maintenance.plugins.StoreLoadSave.superclass.beforerenderHandler.apply(this, arguments);

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

  doLoadData: function() {

    if (this.isDirty){
      this.onSaveSuccess();
    }
    this.getStore().removeAll();
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
  },

  saveData: function(origData, saveData) {
    var output = this.controller.getModifiedPanelStore(origData, saveData, this.getStore(), this.metaClass);
    console.debug("Store.saveData: ", origData, saveData, this.metaClass, output);
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
  },

  /**
   * A convenience method to remove the input record from the grid.
   * If the input record already exists in the model, then that corresponding object will be marked for deletion.
   * @param {Jaffa.data.Record} record A record.
   */
  removeRecord: function (record) {
    if (!record.isNew) {
      var keyValue = record.get(this.store.keyField || ClassMetaData[this.metaClass].key);
      this.findDataSource().get(keyValue).deleteObject = true;
    }
    this.getStore().remove(record);
  }
});

Ext.preg('panelloadsave', Jaffa.maintenance.plugins.PanelLoadSave);