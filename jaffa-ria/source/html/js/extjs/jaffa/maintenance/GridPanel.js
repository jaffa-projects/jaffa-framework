
/**
 * @class Jaffa.maintenance.GridPanel
 * @extends Jaffa.maintenance.Panel
 *
 * This is the base class for a creating a grid panel to be used in a 
 * maintenance component that is rendering an updatable grid.
 * It provides the default behaviour to allow
 * the grid store to be loaded from the data in a controller,
 * as well as participate in the controllers "save" process by providing
 * grid cell validation, and returning the modified records from the store that should be persisted
 * <p>
 * Because this panel is not "readOnly" by default it will register "change listeners"
 * so that when any record in the grid store is modified, the panel is marked as "dirty".
 * It this panel has been registered as a tab, the tab will indicate that the panel is
 * dirty.
 * <p>
 * By default this panel will get a "save" option in the toolbar, which will be tied to
 * the controllers "save" process, as well as an "Add" and "Remove" option for modifying the grid.
 *
 * @deprecated Instead of creating this unnecessary Panel around a Grid,
 * apply the 'Jaffa.maintenance.plugins.GridLoadSave' plugin on the Grid.
 */
Jaffa.maintenance.GridPanel = Ext.extend(Jaffa.maintenance.Panel, {
  /**
   * @cfg {Ext.grid.GridPanel} gridPanel
   * This is a reference to the GridPanel being used inside this panel. This is needed
   * for reading/writing to the grid's store, as well as updating it's view.
   */
  gridPanel: undefined,

  readOnly: false,
  
  constructor: function (config) {
    Jaffa.maintenance.GridPanel.superclass.constructor.call(this, config);
    
    // Disable primary key fields for existing records
    if (this.gridPanel && ClassMetaData[this.metaClass] && ClassMetaData[this.metaClass].key && typeof ClassMetaData[this.metaClass].key === 'string') {
      this.gridPanel.on('beforeedit', function (e) {
        if (!e.record.isNew && e.field === ClassMetaData[this.metaClass].key){
          return false;
        }  
      }, this);
    }
  },
  
  /**
   * A general load method that is automatically wired in to the controller's
   * onload event as part of the panels registration.
   */
  load: function(){
    if (this.isDirty){
      this.onSaveSuccess();
    }
    
    this.gridPanel.getStore().removeAll();
    this.controller.applyGridColumnMetaRules(this.gridPanel);
    var ds = this.findDataSource();
    if (ds) {
      console.debug('Loading store ', ds.getRange());
      this.gridPanel.getStore().loadData(ds.getRange());
      console.debug('Store is ', this.gridPanel.getStore());
    }
    
    if (this.gridPanel.rendered){
      this.gridPanel.getView().refresh();
    }
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
      if(!this.controller.validateGrid(this.gridPanel)){
         return false;
      }
      return this.controller.validateStoreAndPanels(this.gridPanel.getStore(), ClassMetaData[this.metaClass]);
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
    var output = this.controller.getModifiedPanelStore(origData, saveData, this.gridPanel.getStore(), this.metaClass);
    console.debug("GridPanel.saveData: ", origData, saveData, this.metaClass, output);
    return output;
  },
  
  /**
   * A convenience method to add the input record to the grid.
   * @param {Jaffa.data.Record} record A record.
   */
  addRecord: function (record) {
    record.isNew = true;
    this.gridPanel.getStore().add(record);
    this.gridPanel.startEditing(this.gridPanel.getStore().getCount() - 1, 1);
  },
  
  /**
   * A convenience method to remove the input record from the grid.
   * If the input record already exists in the model, then that corresponding object will be marked for deletion.
   * @param {Jaffa.data.Record} record A record.
   */
  removeRecord: function (record) {
    this.gridPanel.getStore().deleteRecord(record);
  }
});

Ext.reg('maintenancegridpanel', Jaffa.maintenance.GridPanel);

