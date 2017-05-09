/**
 * @class Ext.ux.plugins.InsertFromGrid
 * @author BobbyC
 * @param {Object} config Options object
 * @constructor
Example
<code>
new Ext.grid.GridPanel({
  ...
  plugins: Ext.ux.plugins.InsertFromGrid(
    {meta:ClassMetaData.CommentFinderOutDto,
     gridDblClickHandler: function(gridElement, rowIndex, eventObject){
       ...
     }.createDelegate(this)})
  ...
});  
</code>

 */

// Ext.ux.plugins.InsertFromGrid
// Plugin adds a button to an html editor toolbar. This button will call a combo grid 
// using the meta defined in the config. Double clicking a row in the grid will
// call the supplied gridDblClickHandler method to process the row.
Ext.ux.plugins.InsertFromGrid = function(config) {

  // pointer to Ext.ux.HTMLEditor
  var editor;

  return {

    init: function(htmlEditor) {
      this.editor = htmlEditor;
      this.editor.on('render', this.onRender, this);
    },
    
    gridDblClickHandler: function(gridElement, rowIndex, eventObject){
      var editor = this.editor;
      var cmts = gridElement.store.data.itemAt(rowIndex).data.comments;
      if (cmts && (editor.xtype=='htmleditor' || editor instanceof Ext.form.HtmlEditor)) {
        var isInputText = cmts.indexOf('\n')>=0 || cmts.indexOf(' ')>=0;
        if (isInputText) {
          cmts = cmts.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, '&nbsp;');
          cmts = "<font face='courier new'>" + cmts + '</font></br>';
        }
      }
      if (editor.activated){
        editor.insertAtCursor(cmts);
      } else{
        if (editor.getValue().length > 0 && editor.getValue()!= '<br>')
          editor.setValue(editor.getValue() + '<br>' + cmts);
        else
          editor.setValue(cmts);
      }
    },

    onRender: function() {
      this.editor.getToolbar().insertButton(0,
      [{
        itemId: 'defaultCommentAddBtn',
        iconCls: 'add',
        tabIndex: -1,
        hidden: config.hideAddButton,
        disabled: config.disableAddButton,
        editor: this.editor,
        handler: function(){
          this.renderGrid();
        },
        tooltip: config.tooltip,
        meta: config.meta,
        gridDblClickHandler: config.gridDblClickHandler || this.gridDblClickHandler,

        renderGrid: function() {
            // mask the current element
            var comboGridElement = this;
            comboGridElement.disable();
            
            // create the grid
            comboGridElement.grid = new Jaffa.form.FinderGridPanel({
                itemId: 'defaultCommentGridPanel',
                meta: comboGridElement.meta,
                listeners: {
                  rowdblclick: function(gridElement, rowIndex, eventObject){
                    if (comboGridElement.gridDblClickHandler) {
                      var edt = comboGridElement.editor;
                      var ov = edt.getValue();
                      comboGridElement.gridDblClickHandler(gridElement, rowIndex, eventObject)
                      edt.fireEvent('sync', edt, ov, edt.getValue());
                    }

                    comboGridElement.window.close();
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
            
            // Add a filter to the grid
            var baseParams;
            if (comboGridElement.store) baseParams = Ext.apply({}, comboGridElement.store.baseParams);
            if (baseParams && !baseParams[comboGridElement.valueField]) {
                var value = comboGridElement.el.getValue();
                if (value != null && value.length > 0) {
                    var record = comboGridElement.findRecordInStore(comboGridElement.displayField, value);
                    if (!record) {
                      // assuming value is user typed in value
                      if (comboGridElement.valueFieldCase && comboGridElement.valueFieldCase == 'uppercase')
                        value = value.toUpperCase();
                      record = comboGridElement.findRecordInStore(comboGridElement.valueField, value);
                    }
                    if (record && record.data)
                        value = record.data[comboGridElement.valueField];
    
                    // add the default value to the filter
                    // use primarySearchField in case the value field is not displayed in the popup grid. 
                    // primarySearchField is likely to be part of the display field that user see.
                    var primarySearchField = comboGridElement.meta.finder.primarySearchField || comboGridElement.valueField;
                    comboGridElement.grid.filters.filters.each(function(flt) {
                        if (flt.dataIndex == primarySearchField) {
                            if (flt.type == 'list') {
                                flt.setValue([value]);
                            } else if (flt.type == 'string') {
                                flt.setValue(value);
                            } else if (flt.type == 'boolean') {
                                flt.setValue(value);
                            } else if (flt.type == 'date') {
                                flt.setValue({on: value});
                            } else if (flt.type == 'numeric') {
                                flt.setValue({eq: value});
                            }
                            flt.setActive(true);
                            return false;
                        }
                    });
                } else {
                    comboGridElement.grid.filters.clearFilters();                
                }
            }

            comboGridElement.fireEvent('beforeautoselectquery', comboGridElement, baseParams, false);
            Ext.applyIf(comboGridElement.grid.store.baseParams, baseParams);
            
            // create a window with the grid
            comboGridElement.window = new Ext.ModalWindow({
                itemId: 'defaultCommentModalWindow',
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
                text: Labels.get('label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters'),
                scope: comboGridElement.grid,
                handler: function () {
                    this.filters.clearFilters();
                }
            });
            
            // load the grid
            comboGridElement.grid.store.load();
            comboGridElement.grid.focus();
            
            // set the 'popupAlways' property to true to avoid the unnecessary load of the combo store
            comboGridElement.popupAlways = true;
            
            // No need to collapse the combo, since the expand() function has been overridden
            // collapse the combo
            // comboGridElement.list.hide();
        }
      }, new Ext.Toolbar.Spacer({
        hidden: config.hideAddButton
      })]);
    }
  }  
};

Ext.preg('insertfromgrid', Ext.ux.plugins.InsertFromGrid);
