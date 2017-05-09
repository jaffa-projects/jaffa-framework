/**
 * @auth Sean Zhou 
 * 
 * A a lookup grid that users can select multiple records and passed back to a callback handler.
 * 
 */

Ext.ns('Jaffa.widget');

Jaffa.widget.MultiSelectionLookupGrid = Ext.extend(Ext.ModalWindow, {
  /**
   * @cfg {object} meta (mandatory) The meta Object.containing the information required for creating the widget. This is a required property.
   */
  /**
   * @cfg {Function} callbackHandler (mandatory) A function called when the lookup window is closed.
   * The handler is passed the following parameters:

         * 
        records : An array of {@link Ext.data.Record}
        The records user selected from the the lookup.
        
         * 
   */
  /**
   * @cfg {object} baseParam (optional) The baseParam used for the store of the lookup grid.
   */
  /**
   * @cfg {object} lookupGridCfg (optional) The json config object for the lookup grid.
   */
  /**
   * @cfg {object} gridOfSelectedCfg (optional) The json config object for the grid to show the selected records.
   */
  getStaticBaseParams: Ext.emptyFn,
  maximizable: true,
  autoHeight: true,
  width: 600,
  resizeHandles: 'e',
  keys: [{
    key: [Ext.EventObject.UP, Ext.EventObject.DOWN],
    fn: function(){
      grid.getView().focusEl.focus();
    }
  }, {
    key: [Ext.EventObject.ENTER],
    fn: function(keyValue, evt){
      // find the row index
      evt.stopEvent();
      var rowIdx = grid.getStore().indexOf(grid.getSelectionModel().getSelected());
      grid.fireEvent('rowdblclick', grid, rowIdx);
    }
  }],
  
  /**
   * obtain encoded json key from the input record based on the key fields defined in meta.
   * @param {Object} rec
   */
  getCompositKey: function(rec) {
    if (!rec) return '';
    if (Ext.isArray(this.meta.key)) {
      var json = {};
      for (var i=0; i<this.meta.key.length; i++) {
        json[this.meta.key[i]] = rec.get(this.meta.key[i]);
      }
      return Ext.encode(json);
    } else {
      return rec.get(key);
    }
  }
  // private
  ,createGridOfSelectedRecords : function(config) {
    return new Jaffa.form.FinderGridPanel(Ext.apply({
      xtype: 'finderGridPanel',
      meta: this.meta,
      hidden : true,
      bbar: [{
        textToken: 'label.jaffa.jaffaRIA.jaffa.finder.grid.confirmSelects',
        scope : this,
        handler : function(){
          this.callbackHandler(this.gridOfSelected.getStore().getRange());
          this.close();
        }
      }, {xtype: 'tbfill'}, {
        textToken: 'label.jaffa.jaffaRIA.jaffa.finder.grid.showQueried',
        id : 'showQueriedButton',
        scope : this,
        handler : function() {
          if (this.grid && this.grid.show) {
            grid.hide();
            this.grid.show();
          } else {
            console.debug('Error: LookupButton.grid does not exist.');
          }
        }
      }]
    }, config));
  },
  //private
  createFinderGrid : function(config) {
    config = Ext.apply({
      meta: this.meta,
      addCheckBox: true,
      //checkBoxColumn process event should be ignored. It will be handled by the MultiRowSelectionModel.
      checkBoxColumnConfig: {header:'',processEvent:Ext.emptyFn},
      selModel : new Jaffa.grid.MultiRowSelectionModel()
    }, config);
    
    var grid = new Jaffa.form.FinderGridPanel(config);
    grid.getStore().on('load', function(){
      // detect whether any filter menu is open
      var filtersInFocus = false;
      this.filters.filters.each(function(flt){
        if (!flt.menu.hidden) {
          filtersInFocus = true;
          return false;
        }
      });
      // escape focus when any column filter menu is open
      if (filtersInFocus) 
        return;
    }, grid);
    
    //applies local filtering and paging
    if(this.mode === 'local'){
      grid.filters.local = true;
      grid.getStore().on('load', function(){
        var recCount = 0;
        var pageSize = this.bottomToolbar.pageSize;
        var pageData = this.bottomToolbar.getPageData();
        var startRecord = (pageSize * pageData.activePage) - pageSize;
        var maxRecords = this.store.baseParams.maxRecords;
        var endRecord = startRecord + maxRecords;
        this.store.filterBy(function(rec, id){
          recCount++;
          return recCount > startRecord && recCount <= endRecord;
        });
      }, grid);
    }
    
    grid.deFilterBtn = new Ext.Button({
      textToken: 'label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters',
      scope: grid,
      handler: function(){
        this.filters.clearFilters();
      }
    });
    
    grid.showSelectedBtn = new Ext.Button({
      textToken: 'label.jaffa.jaffaRIA.jaffa.finder.grid.showSelected',
      scope : this,
      handler : function() {
        if (this.gridOfSelected && this.gridOfSelected.show) {
          grid.hide();
          // reset the store
          this.gridOfSelected.getStore().removeAll();
          this.gridOfSelected.getStore().add(this.grid.selectedRecordMap.getRange());
          this.gridOfSelected.show();
          this.gridOfSelected.getView().refresh();
        } else {
          console.debug('Error: LookupButton.gridOfSelected does not exist.');
        }
      }
    });
    
    grid.addAdditionalBtns = function() {
      // add a clear filter button
      var bbar = grid.getBottomToolbar();
      bbar.addFill();
      bbar.addButton([grid.deFilterBtn, grid.showSelectedBtn]);
    };
    
    return grid;
  },

  constructor : function(config) {
    config = config || {};
    config.lookupGridCfg = config.lookupGridCfg || {};
    config.gridOfSelectedCfg = config.gridOfSelectedCfg || {};
    config.lookupGridCfg.meta = config.meta;
    config.gridOfSelectedCfg.meta = config.meta;
    
    if (config.id) {
      config.lookupGridCfg.id = config.id+'_grid';
      config.gridOfSelectedCfg.id = config.id + '_gridOfSelected';
    } else if (config.itemId) {
      config.lookupGridCfg.itemId = config.itemId+'_grid';
      config.gridOfSelectedCfg.itemId = config.itemId + '_gridOfSelected';
    }
    if (config.stateId) {
      config.lookupGridCfg.stateId = config.stateId+'_grid';
      config.gridOfSelectedCfg.stateId = config.stateId + '_gridOfSelected';
    }
    
    this.grid = this.createFinderGrid(config.lookupGridCfg);
    this.gridOfSelected = this.createGridOfSelectedRecords(config.gridOfSelectedCfg);
    delete config.lookupGridCfg;
    delete config.gridOfSelectedCfg;
    
    Ext.applyIf(this.grid.store.baseParams, Ext.apply(this.baseParams || {}, config.baseParams));
    config.items = [this.grid, this.gridOfSelected];
    
    Jaffa.widget.MultiSelectionLookupGrid.superclass.constructor.call(this, config);
    
    this.addGridListeners();
    this.showGridAndLoadData();
    this.addWindowResizeListener();
    this.setGridFilters(this.baseParams);
  }
  
  // private
  ,loadGridOfSelected : function() {
    if (! this.gridOfSelected) return;
    this.gridOfSelected.getStore().removeAll();
    if (!this.grid.selectedRecordMap) {
      this.grid.selectedRecordMap = new Jaffa.util.RecordCache();
    }
    // load the grid
    this.grid.store.load();
    this.grid.focus();
  }
  // private
  ,showGridAndLoadData : function() {
    // show the grid
    this.grid.show();
    this.grid.addAdditionalBtns();
    this.gridOfSelected.hide();
    //this.grid.setWidth(this.getSize().width - 20);
    if (this.gridOfSelected && this.gridOfSelected.rendered)
      this.gridOfSelected.setWidth(this.getSize().width - 20);
    
    this.loadGridOfSelected();
  }
  // private
  ,addGridListeners : function(btn) {
    // remove the record from selected-records grid store after user clicks on it
    this.grid.on('rowclick', function(grid, rowIdx, evt) {
      // find the record
      var rec = grid.getStore().getAt(rowIdx);
      if (rec) {
        if (! grid.getSelectionModel().isSelected(rowIdx)) {
          // perform deselect
          // remove checked mark in the record
          rec.set(this.checkField, false);
          // remove the record from selected record cache
          grid.selectedRecordMap.removeKey(this.getCompositKey(rec));
        } else {
          // perform select
          // mark the record as checked
          rec.set(this.checkField, true);
          // add the record to selected record cache
          // do not add directly to the selected grid store because if user toggle a record quickly
          // could corrupt the record when add/remove are concurrent.
          // synch the selected records at the time of grid toggle.
          grid.selectedRecordMap.setRecords(this.getCompositKey(rec), rec);
        }
      }
    }, this);
    if (this.gridOfSelected) {
      this.gridOfSelected.on('rowclick', function(grid, rowIdx, evt) {
        // find the record
        var rec = grid.getStore().getAt(rowIdx);
        if (rec) {
          // perform deselect
          // remove record from selected grid store
          grid.getStore().remove(rec);
          // deselect the record from grid store
          var idx = this.grid.getStore().findBy(function(srec) {
            if (Ext.isArray(this.meta.key)) {
              var isMatch = true; 
              for (var i=0; i<this.meta.key.length; i++) {
                if (srec.get(this.meta.key[i])!=rec.get(this.meta.key[i])) return false;
              }
              return true;
            } else {
              if (srec.get(this.meta.key) == rec.get(this.meta.key)) return true;
            }
          }, this);
          if (idx>=0) {
            this.grid.getSelectionModel().deselectRow(idx);
          }
          // remove it the record from cache in this.grid as well
          this.grid.selectedRecordMap.removeKey(this.getCompositKey(rec));
        }
      }, this);
    }
    this.grid.getStore().on('load', function(store) {
      // NOTE: use render event to make sure the selection is correct every time the grid is shown or modified.
      // find the records in grid store that are checked in selected grid store
      var rs = [];
      store.each(function(rec, idx) {
        var rec = this.grid.selectedRecordMap.getRecords(this.getCompositKey(rec));
        if (rec) {
          rs.push(idx);
        }
      }, this);
      // select the rows in those records.
      if (rs.length > 0) {
        this.grid.getSelectionModel().selectRows(rs, false);
      } else {
        this.grid.getSelectionModel().clearSelections();
      }
    }, this);
  }
  // private
  ,addWindowResizeListener : function() {
    //this.setWidth(this.grid.width+20);
    this.on('afterrender', function() {
      this.on('resize', function(wndw, width, height){
        this.grid.setWidth(width - 20);
        if (this.gridOfSelected) this.gridOfSelected.setWidth(width - 20);
      }, this);
      this.grid.setWidth(this.width-20);
      this.gridOfSelected.setWidth(this.width - 20);
    }, this, {single: true});
  }
  ,setGridFilters: function(baseParams) {
    // Add a filter to the grid
    this.grid.filters.clearFilters();
    var qParams = this.staticBaseParams || this.getStaticBaseParams() || {};
    Ext.apply(qParams, baseParams);
    this.grid.filters.filters.each(function(flt) {
      // disable filters for staticBaseParams
      var cmColumns = this.grid.colModel.config;
      if(qParams && qParams[flt.dataIndex]){
        Ext.each(cmColumns, function(c){
          if(c.dataIndex === flt.dataIndex){
            c.filter = false; // disables filtering for that column, see ux-overrides GridFilters.onMenu
          }
        });
      }

    }, this);
  }
  

});
Ext.reg('multiselectlookupgrid', Jaffa.widget.MultiSelectionLookupGrid);
