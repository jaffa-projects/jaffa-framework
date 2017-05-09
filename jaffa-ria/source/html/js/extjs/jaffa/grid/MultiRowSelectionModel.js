/**
 * @class Jaffa.grid.MultiRowSelectionModel 
 * @extends Ext.grid.RowSelectionModel
 * @author SeanZ
 * 
 * Modified from super class to allow multi-select without need to hold down shift or control key.
 */
Jaffa.grid.MultiRowSelectionModel = Ext.extend(Ext.grid.RowSelectionModel, {
  allowMultiSelect : true,
  
  // private. Modified from super class to allow multi-select without need to hold down shift or control key.
  handleMouseDown : function(g, rowIndex, e) {
    if (e.button !== 0 || this.isLocked()) {
      return;
    };
    var view = this.grid.getView();
    if (e.shiftKey && this.last !== false) {
      var last = this.last;
      this.selectRange(last, rowIndex, e.ctrlKey);
      this.last = last; // reset the last
      view.focusRow(rowIndex);
    } else {
      var isSelected = this.isSelected(rowIndex);
      if ((e.ctrlKey || this.allowMultiSelect) && isSelected) {
        this.deselectRow(rowIndex);
      } else if (!isSelected || this.getCount() > 1) {
        this.selectRow(rowIndex, e.ctrlKey || e.shiftKey || this.allowMultiSelect);
        view.focusRow(rowIndex);
      }
    }
  }
});
