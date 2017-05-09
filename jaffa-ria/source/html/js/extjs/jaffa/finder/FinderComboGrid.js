/**
 * @class Jaffa.form.FinderComboGrid
 * @extends Jaffa.form.FinderComboBox
 * An extension to {@link Jaffa.form.FinderComboBox} for rendering the output returned by Jaffa's Finder components.
 * By default the output is rendered in a dropdown. However if the output exceeds the maxRecords threshold, then the output will be rendered in a Grid.
 */
Jaffa.form.FinderComboGrid = Ext.extend(Jaffa.form.FinderComboBox, {
    flagGridIsOn : false,
    popupGridCloseTime : null,
	
    /**
     * @cfg targetFields a collection of name value pairs that are used to populate other fields in the form.
     * if the targetFields = ['siteCode=siteCode'], the field in the same form that has a mapping of siteCode will be populated with value of the siteCodeField in the selected record
     */
    targetFields: [],
    primarySearchField : undefined,
   /**
    * @cfg columns (Array) - Custom columns that will be displayed on finder combo grid. These columns will replace columns that are inferred from meta data.
    * These columns should be subset of the columns in the meta data.
    */
    gridColumns : undefined,
	deactivateFiltersText : 'Clear Filters',
    initComponent: function() {
        // Dynamically load meta, if not provided in the config
        this.loadMeta();

        // Apply all the properties declared in the meta object
        Ext.apply(this, this.meta.finder.grid);

        // Invoke the initComponent of the base class
        Jaffa.form.FinderComboGrid.superclass.initComponent.call(this);

        this.store.baseParams.maxRecords = this.meta.finder.combo.maxRecords;

        //Applying static base parameters.
        Ext.applyIf(this.store.baseParams, this.staticBaseParams ||  this.getStaticBaseParams());

        this.addEvents(
            /**
             * @event rowdblclick
             * Fires after double-clicking on a row in the GridPanel
             * @param {Jaffa.form.FinderComboGrid} comboGrid This comboGrid
             * @param {Ext.data.Record} record The data record returned from the underlying store
             */
            'rowdblclick'
        );

        // Fires before all queries are processed. Returns false in lookup-mode. The Grid can only be rendered by down-arrow or combo-button
        this.addListener('beforequery', function(queryEvent) {
            if (queryEvent.combo.popupAlways)
                return false;
        });

        // The following can be used to render a Grid automatically on a blur, if multiple records are found
        // Fires when an item is selected automatically by the blur handler. Will render a Grid if the more than 1 item is contained in the input data.
        // this.addListener('autoselect', function(combo, items) {
        //    if (items && items.length > 1)
        //        combo.renderGrid();
        // });

        this.on('select', this.populateTargetFields);
    },

    //a selection handler that is used to populate other fields on the form
    populateTargetFields: function(cbo, rec, rowIndex){
      var userTriggered = Ext.isNumber(rowIndex);
      var doPopulate = false;
      if(userTriggered){
        doPopulate = true;
      } else { //targetFields do not populate when duplicates exist during autoselection
        var matches = cbo.store.queryBy(function(record, id){
          return Boolean(record.get(cbo.valueField) === rec.get(cbo.valueField));
        });
        if(matches.getCount() === 1){
          doPopulate = true;
        }
      }
      if(!cbo.targetFields || cbo.targetFields.length === 0 || !doPopulate){return;}
      var ownerPanel = cbo.ownerCt;
      for(var i = 0; i < cbo.targetFields.length; i++){
        var rule = cbo.targetFields[i];
        var targetPanelFieldMapping  = rule.split('=')[0];
        var targetRecFieldName       = rule.split('=')[1];
        if(targetPanelFieldMapping && targetRecFieldName){
           this.setTargetFieldValue(ownerPanel,rec,targetPanelFieldMapping ,targetRecFieldName,cbo);
        }
      }
    },
    setTargetFieldValue : function(ownerPanel,rec,targetPanelFieldMapping ,targetRecFieldName,cbo){
      var targetPanelField = ownerPanel.find('mapping', targetPanelFieldMapping)[0] || ownerPanel.find('itemId', targetPanelFieldMapping)[0] || ownerPanel.find('mapping', 'flexBean.' + targetPanelFieldMapping)[0];
      if(targetPanelField && rec && Ext.isDefined(rec.data[targetRecFieldName]) && targetPanelField !== cbo){
        if(targetPanelField.getValue() !== rec.get(targetRecFieldName)){ //prevents infinate recurssion when the targetPanelField has this finderCombo as one of its targetField
          targetPanelField.setValue(rec.get(targetRecFieldName), true /* doNotAutoSelect: also creates infinate recurssion */);
        }
      }
    },
    // This method is called by the doQuery. This will hide the list in lookup-mode or when more records exist
    expand: function() {
        if (this.popupAlways || (this.store.reader.jsonData && (this.store.reader.jsonData.moreRecordsExist || this.store.reader.jsonData.totalRecords > this.store.reader.jsonData[this.meta.finder.root].length)))
            this.list.hide();
        else
            Jaffa.form.FinderComboGrid.superclass.expand.call(this);
    },

    // This method is called when the down-arrow is clicked or when the combo-button is clicked
    // It'll invoke the default logic. Subsequently, it'll render the Grid in lookup-mode or when more records exist
    onTriggerClick: function() {
        if (this.disabled)
            return;
        // bring in focus fixes the problem that if popupAlways=true will fail to trigger change listener after the record is selected from the lookup grid.
        // in some cases, even if popupAlways=false, as long as the popup grid came earlier than the dropdown query, the combobox will fail to trigger change listeners as well.
        // change listeners at this point is implemented with focus flag to indicate the subsequent modifications on the widget should trigger a dirty flag on the panel.
        this.fireEvent('focus', this);
        // in IE, focus() does not fire focus event, while firefox does???
        this.isOnFocus=true;
        if(!this.staticBaseParams && this.getStaticBaseParams()){
            this.staticBaseParams = this.getStaticBaseParams();
        }
        if(this.staticBaseParams){
          Ext.applyIf(this.store.baseParams,this.staticBaseParams);
        }
        this.applyCriteriaFields(this.store.baseParams);
        // Render the Grid in lookup-mode
        if (this.popupAlways || (this.store.reader.jsonData && (this.store.reader.jsonData.moreRecordsExist || this.store.reader.jsonData.totalRecords > this.store.reader.jsonData[this.meta.finder.root].length))){
          this.renderGrid();
        } else {
          // cache the current record if any
          var record = this.getValue() != null ? this.findRecord(this.valueField, this.getValue()) : null;

          var loadListener = function(store, records, options) {
            // render grid if more data exists
            if (this.store.reader.jsonData && (this.store.reader.jsonData.moreRecordsExist || this.store.reader.jsonData.totalRecords > this.store.reader.jsonData[this.meta.finder.root].length)) {
              // Add the current record to the Store so that the lookup will fetch the current value, and look for the displayValue
              if (record != null && !this.findRecord(this.valueField, record.data[this.valueField]))
                this.store.add(record);
              this.renderGrid();
            }

            // remove this listener since it is to be executed only once
            this.store.removeListener('load', loadListener, this);
          };
          this.store.addListener('load', loadListener, this);

          // Call the default logic
          Jaffa.form.FinderComboGrid.superclass.onTriggerClick.call(this);
        }
    },

    // Ext's Combo widget registers a DelayedTask, which calls the initQuery() function automatically after a certain a delay on the field
    // The initQuery() function in turn tries to retrieve data.
    // The following will override the default implemenation and do nothing in lookup-mode
    initQuery: function() {
        if (!this.popupAlways)
            Jaffa.form.FinderComboGrid.superclass.initQuery.call(this);
    },

    // return false when either the combo dropdown is on or the popup grid is on
    validateBlur : function(evt){
        /**
         * This method is used to determine whether the combo field should be blur after a mouse click.
         */
        return Jaffa.form.FinderComboGrid.superclass.validateBlur.call(this, evt) && (! this.flagGridIsOn);
    },

    fireKey : function(e){
        if (e.isNavKeyPress() && e.getKey() == e.ENTER && this.validateBlur() && this.popupGridCloseTime) {
            // console.debug('fireEnterKey', (new Date()).getTime());
            if ((new Date()).getTime() - this.popupGridCloseTime.getTime() < 500) {
                this.popupGridCloseTime = null;
                e.stopEvent();
                return;
            }
        }
        Jaffa.form.FinderComboGrid.superclass.fireKey.call(this, e);
    },

    //if the finderComboGrid is an Ext.Editor field and is valid then stopEditing
    completeEditing: function(){
      if (this.parentGrid && this.parentGrid.activeEditor && this.parentGrid.activeEditor.field && (this.parentGrid.activeEditor.field instanceof Jaffa.form.FinderComboGrid)) {
        this.parentGrid.activeEditor.completeEdit(false,true);
      }
    },

    /* Renders the Grid */
    renderGrid: function() {
        // mask the current element
        var comboGridElement = this;
        comboGridElement.disable();

        // create the grid
        comboGridElement.grid = new Jaffa.form.FinderGridPanel({
            id: comboGridElement.id + '_grid',
            meta: comboGridElement.meta,
            columns : comboGridElement.gridColumns,
            listeners: {
                rowdblclick: function(gridElement, rowIndex, eventObject) {
                    var gridRec = gridElement.store.data.items[rowIndex];
                    var record = new Ext.data.Record(gridRec.data);
                    if(!record.json) record.json = gridRec.json;
                    var originalValue = comboGridElement.getValue();
                    comboGridElement.setValue(record);
                    var newValue = comboGridElement.getValue();
                    comboGridElement.fireEvent('select', comboGridElement, record, rowIndex);
                    comboGridElement.fireEvent('rowdblclick', comboGridElement, record, rowIndex);
                    if (originalValue != newValue) {
                        comboGridElement.fireEvent('change', comboGridElement, newValue, originalValue, true);
                    }
                    comboGridElement.window.close();
                    comboGridElement.completeEditing();
                }
            }

        });
        comboGridElement.grid.getStore().on('load', function() {
            // detect whether any filter menu is open
            var filtersInFocus = false;
            comboGridElement.grid.filters.filters.each(function(flt) {
              if (! flt.menu.hidden) {
                filtersInFocus = true;
                return false;
              }
            });
            // escape focus when any column filter menu is open
            if (filtersInFocus) return;
            // set focus on the grid
            this.focus();
            if (this.getStore().getCount() > 0) {
                this.getSelectionModel().selectFirstRow();
                try {
                    this.getView().focusRow(0);
                } catch(ex) {}
            }
        }, comboGridElement.grid);

        // Determine baseParams
        var baseParams = Ext.apply({}, comboGridElement.store.baseParams);
        comboGridElement.fireEvent('beforeautoselectquery', comboGridElement, baseParams, false);

        comboGridElement.setGridFilters(comboGridElement, baseParams);

         // Apply baseParams to the Store
         if(comboGridElement.staticBaseParams)  Ext.apply(comboGridElement.grid.store.baseParams,comboGridElement.staticBaseParams);
         Ext.applyIf(comboGridElement.grid.store.baseParams, baseParams);

        // create a window with the grid
        comboGridElement.window = new Ext.ModalWindow({
            id: comboGridElement.grid.id+'_wndw',
            maximizable: true,
            autoHeight: true,
            width: comboGridElement.grid.width + 20,
            items: comboGridElement.grid,
            resizeHandles: 'e',
            keys: [{
                key: [Ext.EventObject.UP, Ext.EventObject.DOWN],
                fn: function() {
                    comboGridElement.grid.getView().focusEl.focus();
                }
            }, {
                key: [Ext.EventObject.ENTER],
                fn: function(keyValue, evt) {
                    // find the row index
                    evt.stopEvent();
                    var rowIdx = comboGridElement.grid.getStore().indexOf(comboGridElement.grid.getSelectionModel().getSelected());
                    comboGridElement.grid.fireEvent('rowdblclick', comboGridElement.grid, rowIdx);
                }
            }],
            listeners: {
                close: function(panel) {
                    comboGridElement.enable();
                    comboGridElement.focus(true);
                    comboGridElement.flagGridIsOn = false;
                    // console.debug('closeEvent', (new Date()).getTime());
                    comboGridElement.popupGridCloseTime = new Date();
                },
                show: function (c) {
                  // Ensure that z-index of the window is not lower than the z-index of the combo, else the combo overlaps the window.
                  // This issue has been observed when the finderComboGrid is used in an EditableGrid.
                  var comboZIndex = comboGridElement.findZIndex();
                  if (comboZIndex) {
                    var windowZIndex = c.findZIndex();
                    if (!windowZIndex || windowZIndex < comboZIndex)
                      c.el.setStyle('z-index', comboZIndex);
                  }
                }
            }
        });
        comboGridElement.window.show();
        this.flagGridIsOn = true;
        comboGridElement.window.on('resize', function(wndw, width, height) {
            comboGridElement.grid.setWidth(width-20);
        }, comboGridElement.window);
        comboGridElement.grid.setWidth(comboGridElement.window.getSize().width-20);

        // add a clear filter button
        var bbar = comboGridElement.grid.getBottomToolbar();
        bbar.addFill();
        bbar.addButton({
            text: this.deactivateFiltersText,
            scope: comboGridElement.grid,
            handler: function () {
               comboGridElement.clearFilters.call(comboGridElement);
            }
        });

        // load the grid
        comboGridElement.grid.store.load();
        comboGridElement.grid.focus();
        comboGridElement.onFocus=true; // added so that the select event can trigger change listener

        // set the 'popupAlways' property to true to avoid the unnecessary load of the combo store
        comboGridElement.popupAlways = true;

        // No need to collapse the combo, since the expand() function has been overridden
        // collapse the combo
        // comboGridElement.list.hide();
    },
    clearFilters : function(){
      this.grid.filters.clearFilters();
      var staticParameters = this.staticBaseParams || this.getStaticBaseParams();
      if(staticParameters && this.grid){
        Ext.applyIf(this.grid.store.baseParams, staticParameters);
      }
      this.applyCriteriaFields(this.grid.store.baseParams);
    },
   /**
    * This method validates combo box value. Overridden to apply static baseParams.
    */
    dwrSelect: function(value, baseParams, exactQuery, gridRecord) {
       Ext.apply(baseParams, this.staticBaseParams ||  this.getStaticBaseParams());
       this.applyCriteriaFields(baseParams);
       Jaffa.form.FinderComboGrid.superclass.dwrSelect.call(this,value, baseParams, exactQuery, gridRecord);
    },
    setGridFilters : function(comboGridElement,baseParams){

      // add the default value to the filter
      // use primarySearchField in case the value field is not displayed in the popup grid.
      // primarySearchField is likely to be part of the display field that user see.
      var primarySearchField = comboGridElement.primarySearchField || comboGridElement.meta.finder.primarySearchField || comboGridElement.valueField;

      // Add a filter to the grid
      comboGridElement.grid.filters.clearFilters();
      var comboValue = comboGridElement.el.getValue();
      if (comboValue != null && comboValue.length > 0) {
        var record = comboGridElement.findRecordInStore(comboGridElement.displayField, comboValue);
        if (!record) {
          // assuming value is user typed in value
          if (comboGridElement.valueFieldCase && comboGridElement.valueFieldCase == 'uppercase')
            comboValue = comboValue.toUpperCase();
          record = comboGridElement.findRecordInStore(comboGridElement.valueField, comboValue);
        }
        if (record && record.data)
          comboValue = record.data[primarySearchField];
      }

      comboGridElement.grid.filters.filters.each(function(flt) {
        var value;
        var operator;
        if(flt.dataIndex == primarySearchField){
          value = comboValue;
        } else if(comboGridElement.meta && comboGridElement.meta.fields && comboGridElement.meta.fields[flt.dataIndex] && !comboGridElement.meta.fields[flt.dataIndex].hidden) {
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
        var staticParameters = comboGridElement.staticBaseParams || comboGridElement.getStaticBaseParams();
        var cmColumns = comboGridElement.grid.colModel.config;
        if(staticParameters && staticParameters[flt.dataIndex]){
          Ext.each(cmColumns, function(c){
            if(c.dataIndex === flt.dataIndex){
              c.filter = false; // disables filtering for that column, see ux-overrides GridFilters.onMenu
            }
          });
        } else if(!Ext.isEmpty(value)) { //activates primarySearchField's filter when the user provided a value
          flt.setActive(true);
        }

      });
    }
});

Ext.reg('finderComboGrid', Jaffa.form.FinderComboGrid);
