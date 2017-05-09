/**
 * @author PrudhviK
 * Date: 4/13/11
 *
 * Example:
 *      var window = new Jaffa.form.FinderGridWindow({
 *         meta :ClassMetaData.BinLocationFinderOutDto,
 *         onRecordSelect : function(rec,grid){}
 *         listeners: {
 *            'recordselect' : function(rec,grid){}
 *          }
 *      });
 *      window.show();
 */
Jaffa.form.FinderGridWindow = Ext.extend(Ext.ModalWindow, {
  maximizable: true,
  autoHeight: true,
  height : 300,
  layout : 'fit',
  selectionModel : new Ext.grid.RowSelectionModel({singleSelect : true}),
  /**
   * @cfg {object} meta The meta Object.containing the information required for creating the widget. This is a required property.
   * @required
   */
  meta : undefined,
  /**
   * @cfg {object} staticBaseParams   - Static baseParams that will be applied on the store.These params are applied
   * during component initialization(initComponent) .These baseParams remain static and will not be cleared,
   * even if clear filters button is clicked on the grid.
   *
   * Example: staticBaseParams : { userId  : {values: ['sam'] }, status : {values : ['A']} }
   */
  staticBaseParams : undefined,
  /**
   * This method should be overridden to apply staticBaseParams if the parameters can't be initialized using staticBaseParams config.
   * The baseParams that are retrieved using this method will be applied inside onTriggerClick method. These baseParams remain static
   * and will not be cleared, even if clear filters button is clicked on the grid.
   *
   * Returns baseParams object;
   * Example : return { userId  : {values: ['sam'] }, status : {values : ['A']} }
   *
   * Defaults to Ext.emptyFn.
   */
  getStaticBaseParams : Ext.emptyFn,
  /**
   *  Handler to get the selected record. Selected record and grid element are passed as parameters to
   *  to this function on grid row double click.
   */
  onRecordSelect :  Ext.emptyFn,
	deactivateFiltersText : 'Clear Filters',
  initComponent : function(){
    this.addEvents('beforequery','recordselect');
    this.renderGrid();
    Ext.apply(this, {
      items: [this.grid] ,
      keys: [{
        key: [Ext.EventObject.UP, Ext.EventObject.DOWN],
        scope : this,
        fn: function() {
          this.grid.getView().focusEl.focus();
        }
      }, {
        key: [Ext.EventObject.ENTER],
        scope : this,
        fn: function(keyValue, evt) {
          // find the row index
          evt.stopEvent();
          var rowIdx = this.grid.getStore().indexOf(this.grid.getSelectionModel().getSelected());
          this.grid.fireEvent('rowdblclick', this.grid, rowIdx);
        }
      }]
    });
    Jaffa.form.FinderGridWindow.superclass.initComponent.call(this);
  },
  /* Renders the Grid */
  renderGrid: function() {
    this.grid = new Jaffa.form.FinderGridPanel({
      meta: this.meta,
      loadMask :  true,
      selModel : this.selectionModel,
      listeners: {
        scope : this,
        rowdblclick: function(gridElement, rowIndex, evt) {
          var selectedRec =  gridElement.getStore().getAt(rowIndex);
          this.onRecordSelect(selectedRec,gridElement);
          this.fireEvent('recordselect',selectedRec, gridElement);
          evt.stopEvent();
          this.close.defer(250, this);
        },

        afterrender : function(gridEl){
          // load the grid
          gridEl.getStore().load();
          gridEl.focus();
        }
      }
    });

    this.grid.getStore().on('load', function() {
      if(this.getEl()) {
        this.getEl().unmask();
      }
      // detect whether any filter menu is open
      var filtersInFocus = false;
      if(this.filters) {
        this.filters.filters.each(function(flt) {
          if (! flt.menu.hidden) {
            filtersInFocus = true;
            return false;
          }
        });
        // escape focus when any column filter menu is open
        if (filtersInFocus) return;
      }
      // set focus on the grid
      this.focus();
      if (this.getStore().getCount() > 0) {
        this.getSelectionModel().selectFirstRow();
        try {
          this.getView().focusRow(0);
        } catch(ex) {}
      }
    }, this.grid);

    this.applyFilters();
    // Apply static baseParams to the Store
    Ext.apply(this.grid.store.baseParams, this.staticBaseParams ||  this.getStaticBaseParams());
    // add a clear filter button
    var bbar = this.grid.getBottomToolbar();
    bbar.addFill();
    bbar.addButton({
      text: this.deactivateFiltersText,
      scope: this,
      handler: function () {
        this.clearFilters.call(this);
      }
    });
  },
  applyFilters : function(){
    // Determine baseParams
    var baseParams = Ext.apply({}, this.grid.store.baseParams);
    this.fireEvent('beforequery', this.grid, baseParams, false);

    // Add a filter to the grid
    this.grid.filters.clearFilters();

    this.grid.filters.filters.each(function(flt) {
      var value,operator;
      if (this.meta && this.meta.fields && this.meta.fields[flt.dataIndex] && !this.meta.fields[flt.dataIndex].hidden) {
        var baseParam = baseParams[flt.dataIndex];
        if (flt.type !== "string" && baseParam && (!baseParam.operator || baseParam.operator == 'Equals' || baseParam.operator == 'BeginsWith') && baseParam.values && baseParam.values.length == 1) {
          value = baseParam.values[0];
        }
        else
        if (flt.type == "string" && baseParam && (!baseParam.operator || baseParam.operator == 'Equals' || baseParam.operator == 'BeginsWith' || baseParam.operator == 'Like' || baseParam.operator == 'EndWith') && baseParam.values && baseParam.values.length == 1) {
          value = baseParam.values[0];
          if (baseParam.operator) operator = baseParam.operator;
        }
      }

      //reset primarySearchField baseParams
      if (!Ext.isEmpty(value)) {
        delete baseParams[flt.dataIndex];
        if (flt.type == 'list') {
          flt.setValue([value]);
        } else if (flt.type == 'string') {
          flt.setValue({
            operator: operator,
            value: value
          });
        } else if (flt.type == 'boolean') {
          flt.setValue(value);
        } else if (flt.type == 'date') {
          flt.setValue({on: value});
        } else if (flt.type == 'numeric') {
          flt.setValue({eq: value});
        }
      }
      // disable filters for staticBaseParams
      var staticParameters = this.staticBaseParams || this.getStaticBaseParams();
      var cmColumns = this.grid.colModel.config;
      if(staticParameters && staticParameters[flt.dataIndex]){
        Ext.each(cmColumns, function(c){
          if(c.dataIndex === flt.dataIndex){
            c.filter = false;  // disables filtering for that column, see ux-overrides GridFilters.onMenu
          }
        });
      } else if(!Ext.isEmpty(value)) { //activates primarySearchField's filter when the user provided a value
        flt.setActive(true);
      }
    },this);
  },
  clearFilters : function(){
    this.grid.filters.clearFilters();
    Ext.applyIf(this.grid.store.baseParams, this.staticBaseParams ||  this.getStaticBaseParams());
  }
});