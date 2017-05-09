/**
 * @class Jaffa.form.FinderLovComboGrid
 * @extends Jaffa.form.JaffaMvsComboBox
 * An extension to {@link Jaffa.form.JaffaMvsComboBox} for rendering the output returned by Jaffa's Finder components.
 * By default the output is rendered in a dropdown. However if the output exceeds the maxRecords threshold, then the output will be rendered in a Grid.
 */
Jaffa.form.FinderLovComboGrid = Ext.extend(Jaffa.form.FinderLovComboBox, {
  flagGridIsOn: false,
  popupGridCloseTime: null,
  /**
   * @cfg {Object} finderGridConfig Custom configuration for finder grid.
   */
  finderGridConfig : {},
  getStaticBaseParams: Ext.emptyFn, 
  confirmSelectsText: 'Confirm Selected',
  showQueriedText: 'Show Found',
  deactivateFiltersText: 'Clear Filters',
  showSelectedText: 'Show Selected',
  
  initComponent: function(){
    // Dynamically load meta, if not provided in the config
    this.loadMeta();
		
    // Apply all the properties declared in the meta object
    Ext.apply(this, this.meta.finder.grid);
    
    // Invoke the initComponent of the base class
    Jaffa.form.FinderLovComboGrid.superclass.initComponent.call(this);
    
    //Applying static base parameters.
	if(this.store)	{
		Ext.applyIf(this.store.baseParams, this.staticBaseParams ||  this.getStaticBaseParams());
	}
    
    // Fires before all queries are processed. Returns false in lookup-mode. The Grid can only be rendered by down-arrow or combo-button
    this.addListener('beforequery', function(queryEvent){
      if (queryEvent.combo.popupAlways) 
        return false;
    });
    
    // The following can be used to render a Grid automatically on a blur, if multiple records are found
    //// Fires when an item is selected automatically by the blur handler. Will render a Grid if the more than 1 item is contained in the input data.
    //this.addListener('autoselect', function(combo, items) {
    //    if (items && items.length > 1)
    //        combo.renderGrid();
    //});
  },
  
  // This method is called by the doQuery. This will hide the list in lookup-mode or when more records exist
  expand: function(){
    if (this.popupAlways || (this.store.reader.jsonData && this.store.reader.jsonData.moreRecordsExist)) 
      this.list.hide();
    else 
      Jaffa.form.FinderLovComboGrid.superclass.expand.call(this);
  },
  
  // This method is called when the down-arrow is clicked or when the combo-button is clicked
  // It'll invoke the default logic. Subsequently, it'll render the Grid in lookup-mode or when more records exist
  onTriggerClick: function(){
    if (this.disabled) 
      return;
    
    // Render the Grid in lookup-mode
    if (this.popupAlways || (this.store.reader.jsonData && this.store.reader.jsonData.moreRecordsExist)) 
      this.renderGrid();
    else {
      // Add a listener if data has not been loaded yet
      if (!this.store.isLoaded) {
        // NOTE: cache the current selected record if any (modified from FindercomboGrid.onTrigerClick())
        var cachedRecordsBeforeStoreLoad = this.store.getRange();
        
        var loadListener = function(store, records, options){
          // render grid if more data exists
          if (this.store.reader.jsonData && this.store.reader.jsonData.moreRecordsExist) {
            // Add the current record to the Store so that the lookup will fetch the current value, and look for the displayValue
            for (var i=0; i<cachedRecordsBeforeStoreLoad.length; i++) {
              var idx = this.store.find(this.valueField, new RegExp('^'+cachedRecordsBeforeStoreLoad[i].get(this.valueField)+'$'));
              if (idx<0) {
                this.store.add(cachedRecordsBeforeStoreLoad[i]);
              } else  {
                this.store.getAt(idx).set(this.checkField, cachedRecordsBeforeStoreLoad[i].get(this.checkField));
              }
            }
            this.renderGrid();
          } else {
            // NOTE: use dropdown. synch the cached records with what are just loaded
          }
          
          // remove this listener since it is to be executed only once
          this.store.removeListener('load', loadListener, this);
        };
        this.store.addListener('load', loadListener, this);
      }
      
      // NOTE: cache the records in combo store before initiate new loads
      
      // Call the default logic
      Jaffa.form.FinderLovComboGrid.superclass.onTriggerClick.call(this);
    }
  },
  
  // Ext's Combo widget registers a DelayedTask, which calls the initQuery() function automatically after a certain a delay on the field
  // The initQuery() function in turn tries to retrieve data.
  // The following will override the default implemenation and do nothing in lookup-mode
  initQuery: function(){
    if (!this.popupAlways) 
      Jaffa.form.FinderLovComboGrid.superclass.initQuery.call(this);
  },
  
  // return false when either the combo dropdown is on or the popup grid is on
  validateBlur: function(evt){
    /**
     * This method is used to determine whether the combo field should be blur after a mouse click.
     */
    return Jaffa.form.FinderLovComboGrid.superclass.validateBlur.call(this, evt) && (!this.flagGridIsOn);
  },
  
  fireKey: function(e){
    if (e.isNavKeyPress() && e.getKey() == e.ENTER && this.validateBlur() && this.popupGridCloseTime) {
      // console.debug('fireEnterKey', (new Date()).getTime());
      if ((new Date()).getTime() - this.popupGridCloseTime.getTime() < 500) {
        this.popupGridCloseTime = null;
        e.stopEvent();
        return;
      }
    }
    Jaffa.form.FinderLovComboGrid.superclass.fireKey.call(this, e);
  },
  
  /* Renders the Grid */
  renderGrid: function(){
    // mask the current element
    this.disable();
    
    // create the grid
    var grid = this.createFinderGrid();
    var gridOfSelected = this.createGridOfSelectedRecords();
    
    // Add a filter to the grid
    grid.filters.clearFilters();
    var baseParams = Ext.ux.clone(this.store.baseParams);
    // NOTE: value filter is not added here to display all the values
    this.fireEvent('beforeautoselectquery', this, baseParams, false);
    Ext.applyIf(grid.store.baseParams, baseParams);
    
    // create a window with the grid
    this.window = this.createPopupWindow(grid, gridOfSelected);
    this.window.show();
    this.flagGridIsOn = true;
    this.window.initializeWindow(this);
    this.onFocus=true;
    this.setGridFilters(baseParams);
    
    // set the 'popupAlways' property to true to avoid the unnecessary load of the combo store
    this.popupAlways = true;
    
    // No need to collapse the combo, since the expand() function has been overridden
    //// collapse the combo
    //this.list.hide();
  },
  
  createGridOfSelectedRecords : function() {
    var grid = new Jaffa.form.FinderGridPanel({
      id : this.id + '_grid4selected',
      meta: this.meta,
      bbar: [{
        text: this.confirmSelectsText,// FIXME: Label Token
        scope : this,
        handler : function(btn){
          // add the records from the grid store to combo store.
          var rs = [];
          this.window.gridOfSelected.getStore().each(function(rec) {
            rs.push(rec.get(this.valueField));
            var searchString = RegExp.escape(rec.get(this.valueField)); //Added an escape to prevent regex runtime errors when matching special characters
            var i = this.store.find(this.valueField, new RegExp('^' + searchString + '$'));
            if (i<0) {
              this.store.add(rec);
            }
          }, this);
          // get the selected value list
          this.userSelectionHandler(rs.sort().join(this.separator));
          this.window.close();
        }
      }, {xtype: 'tbfill'}, {
        text: this.showQueriedText,// FIXME: Label Token
        id : 'showQueriedButton',
        scope : this,
        handler : function() {
          if (this.window.grid && this.window.grid.show) {
            grid.hide();
            this.window.grid.show();
          } else {
            console.debug('Error: FinderLovComboGrid.grid does not exist.');
          }
        }
      }],
      hidden : true
    });
    return grid;
  },

  createFinderGrid : function() {
    var config = Ext.apply({
      id: this.id + '_grid',
      meta: this.meta,
      addCheckBox: true,
      //checkBoxColumn process event should be ignored. It will be handled by the MultiRowSelectionModel.
      checkBoxColumnConfig: {header:'',processEvent:Ext.emptyFn},
      selModel : new Jaffa.grid.MultiRowSelectionModel()
    }, this.finderGridConfig);
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
      text: this.deactivateFiltersText,// FIXME: Label Token
      scope: grid,
      handler: function(){
        if(this.ownerCt.lovComboGrid.fireEvent('beforeclearfilters', this) !== false){
          this.filters.clearFilters();
        }
      }
    });
    
    grid.showSelectedBtn = new Ext.Button({
      text: this.showSelectedText,// FIXME: Label Token
      scope : this,
      handler : function() {
        if (this.window.gridOfSelected && this.window.gridOfSelected.show) {
          grid.hide();
          // reset the store
          this.window.gridOfSelected.getStore().removeAll();
          this.window.gridOfSelected.getStore().add(this.window.grid.selectedRecordMap.getRange());
          this.window.gridOfSelected.show();
          this.window.gridOfSelected.getView().refresh();
        } else {
          console.debug('Error: FinderLovComboGrid.gridOfSelected does not exist.');
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
  
  createPopupWindow : function(grid, gridOfSelected) {
    var window = new Ext.ModalWindow({
      id: grid.id + '_wndw',
      maximizable: true,
      autoHeight: true,
      lovComboGrid: this,
      width: grid.width + 20,
      items: gridOfSelected ? [grid,gridOfSelected] : [grid],
      resizeHandles: 'e',
      grid: grid,
      gridOfSelected: gridOfSelected,
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
      listeners: {
        scope : this, // 'this' is the combobox
        afterrender : function(panel){
          panel.manager.zseed = panel.lovComboGrid.getZIndex();
        },
        close : function(panel){
          this.enable();
          this.focus(true);
          this.flagGridIsOn = false;
          // console.debug('closeEvent', (new Date()).getTime());
          this.popupGridCloseTime = new Date();
          this.window = null;
        }
      }
      // methods initializes the window
      ,initializeWindow : function(combo) {
        this.addGridListeners(combo);
        this.showGridAndLoadData(combo);
        this.addWindowResizeListener();
      }
      ,loadGridOfSelected : function(combo) {
        if (! this.gridOfSelected) return;
        this.gridOfSelected.getStore().removeAll();
        var dvs = [], rs=[];
        if (combo.getRawValue() && combo.getRawValue()!='') {
          // resolve the difference between combo value and combo records
          var vs = Jaffa.data.Util.strSetArray(combo.getRawValue(), combo.separator);
          if (vs.length>0) {
            for (var i=0; i<vs.length; i++) {
              var k = combo.store.find(combo.valueField, new RegExp('^'+vs[i]+'$'));
              if (k>=0) {
                var rec = combo.store.getAt(k);
                rec.set(this.checkField, true);
                rs.push(rec);
              } else {
                dvs.push(vs[i]);
              }
            }
            if (rs.length>=0) {
              this.gridOfSelected.getStore().add(rs);
              if (this.gridOfSelected.rendered) this.gridOfSelected.getView().refresh();
            }
          }
        }
        
        if (dvs.length>0) {
          // fetch the unresolved values from remote select 
          combo.remoteSelector.autoGridSelect(dvs, function(remoteStore, valueArray, gridRecord) {
            var recValMap = combo.remoteSelector.groupRecords2values(remoteStore);
            // discard the invalid and multiple record values
            var rcds = [];
            for (var i = 0; i < valueArray.length; i++) {
              var recs=recValMap.get(valueArray[i]);
              if (recs && recs.length==1) {
                recs[0].set(combo.checkField, true);
                rcds.push(recs[0]);
              }
            }
            if (rcds.length>0) {
              this.gridOfSelected.getStore().add(rcds);
              if (this.gridOfSelected.rendered) this.gridOfSelected.getView().refresh();
            }
            this.copySelectedRecordsFromSelectedGridStoreToGridCache(combo);
            // load the grid
            this.grid.store.load();
            this.grid.focus();
          }, this);
        } else {
          this.copySelectedRecordsFromSelectedGridStoreToGridCache(combo);
          // load the grid
          this.grid.store.load();
          this.grid.focus();
        }
      }
      ,copySelectedRecordsFromSelectedGridStoreToGridCache : function(combo) {
        if (!this.grid.selectedRecordMap) {
          this.grid.selectedRecordMap = new Jaffa.util.RecordCache();
        }
        this.gridOfSelected.getStore().each(function(rec) {
          this.grid.selectedRecordMap.setRecords(rec.get(combo.valueField), rec);
        }, this);
      }
      ,showGridAndLoadData : function(combo) {
        // show the grid
        this.grid.show();
        this.grid.addAdditionalBtns();
        if (this.gridOfSelected) {
          this.gridOfSelected.hide();
          this.grid.showSelectedBtn.show();
        } else {
          this.grid.showSelectedBtn.hide();
        }        
        this.grid.setWidth(this.getSize().width - 20);
        if (this.gridOfSelected && this.gridOfSelected.rendered)
          this.gridOfSelected.setWidth(this.getSize().width - 20);
        
        this.loadGridOfSelected(combo);
      }
      ,addGridListeners : function(combo) {
        var popup = this;
        this.grid.on('rowclick', function(grid, rowIdx, evt) {
          // find the record
          var rec = grid.getStore().getAt(rowIdx);
          if (rec) {
            if (! grid.getSelectionModel().isSelected(rowIdx)) {
              // perform deselect
              // remove checked mark in the record
              rec.set(combo.checkField, false);
              // remove the record from selected record cache
              grid.selectedRecordMap.removeKey(rec.get(combo.valueField));
            } else {
              // perform select
              // mark the record as checked
              rec.set(combo.checkField, true);
              // add the record to selected record cache
              // do not add directly to the selected grid store because if user toggle a record quickly
              // could corrupt the record when add/remove are concurrent.
              // synch the selected records at the time of grid toggle.
              grid.selectedRecordMap.setRecords(rec.get(combo.valueField), rec);
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
              var idx = this.grid.getStore().find(combo.valueField, new RegExp('^'+rec.get(combo.valueField)+'$'));
              if (idx>=0) {
                this.grid.getSelectionModel().deselectRow(idx);
              }
              // remove it the record from cache in this.grid as well
              this.grid.selectedRecordMap.removeKey(rec.get(combo.valueField));
            }
          }, this);
        }
        this.grid.getStore().on('load', function(store) {
          // NOTE: use render event to make sure the selection is correct every time the grid is shown or modified.
          // find the records in grid store that are checked in selected grid store
          var rs = [];
          store.each(function(rec, idx) {
            var rec = this.grid.selectedRecordMap.getRecords(rec.get(combo.valueField));
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
        
        this.grid.getStore().on('beforeload', function(store,options){
          // Apply staticBaseParams to the gridPanel's store
          popup.lovComboGrid.staticBaseParams = popup.lovComboGrid.staticBaseParams || popup.lovComboGrid.getStaticBaseParams();
          if(popup.lovComboGrid.staticBaseParams){
            Ext.apply(store.baseParams, popup.lovComboGrid.staticBaseParams);
          }
          if(!Ext.isEmpty(options.params.authId) && options.params.authId.operator === 'BeginsWith' && options.params.authId.values[0].indexOf(',') > -1){
            options.params.authId.operator = 'In';
            options.params.authId.values = options.params.authId.values[0].split(',');
          }
        });
        
      }
      ,addWindowResizeListener : function() {
        this.on('resize', function(wndw, width, height){
          this.grid.setWidth(width - 20);
          if (this.gridOfSelected) this.gridOfSelected.setWidth(width - 20);
        }, this);
        this.grid.setWidth(this.getSize().width-20);
      }
    });
    
    return window;
  }

  ,setGridFilters: function(baseParams) {

    // add the default value to the filter
    // use primarySearchField in case the value field is not displayed in the popup grid.
    // primarySearchField is likely to be part of the display field that user see.
    var primarySearchField = this.primarySearchField || this.meta.finder.primarySearchField || this.valueField;

    // Add a filter to the grid
    this.window.grid.filters.clearFilters();
    var comboValue = this.el ? this.el.getValue() : this.getValue();
    if (comboValue != null && comboValue.length > 0) {
      var record = this.findRecordInStore(this.displayField, comboValue);
      if (!record) {
        // assuming value is user typed in value
        if (this.valueFieldCase && this.valueFieldCase == 'uppercase')
          comboValue = comboValue.toUpperCase();
        record = this.findRecordInStore(this.valueField, comboValue);
      }
      if (record && record.data)
        comboValue = record.data[primarySearchField];
    }

    var qParams = this.staticBaseParams || this.getStaticBaseParams() || {};
    Ext.apply(qParams, baseParams);
    this.window.grid.filters.filters.each(function(flt) {
      var value;
      var operator;
      if(flt.dataIndex == primarySearchField){
        value = comboValue;
      } else if(this.meta && this.meta.fields && this.meta.fields[flt.dataIndex] && !this.meta.fields[flt.dataIndex].hidden) {
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
      var cmColumns = this.window.grid.colModel.config;
      if(qParams && qParams[flt.dataIndex]){
        Ext.each(cmColumns, function(c){
          if(c.dataIndex === flt.dataIndex){
            c.filter = false; // disables filtering for that column, see ux-overrides GridFilters.onMenu
          }
        });
      } else if(!Ext.isEmpty(value)) { //activates primarySearchField's filter when the user provided a value
        flt.setActive(true);
      }

    }, this);
  }
  
});

Ext.reg('finderLovComboGrid', Jaffa.form.FinderLovComboGrid);
