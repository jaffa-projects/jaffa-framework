/**
 * @class Jaffa.grid
 */
 
/* 
 * @method @static
 * @author SeanZ
 *
 * This can be used as a renderer in a ColumnModel 
 * @param {Object} value the value from the cell
 * <p>
 * Example
<pre><code> 
{
    header:"Excluded",
    dataIndex:'excluded',
    width:70,
    align:'center',
    renderer: Jaffa.grid.ColumnCheckbox
}
</code></pre>
 */
Jaffa.grid.ColumnCheckbox = function(val) {
    if (val==true) {
        return "<input type='checkbox' checked disabled/>";
    } else {
        return "<input type='checkbox' disabled/>";
    }
};

Jaffa.grid.ColumnCheckboxTrueOnly = function(val) {
    if (val==true) {
        return "<div class='x-grid3-check-col-on' ></div>";
    } else {
        return "";
    }
};

/**
 * @method @static
 * @author SeanZ
 *
 * This can be used as a renderer in US Money format,
 * Unlike {@link Ext.util.Format#usMoney} this displays 0.00 by default if the value in not valid instead of 'NaN'
 *
 * @param {Object} value the value from the cell
 * @param {Object} el the cell element of the grid
 * @param {Object} record the record in the grid store
 */
Jaffa.grid.UsMoneyRenderer = function(value, el, record) {
	var v = parseFloat(value);
	if (isNaN(v)) {
		v = 0.00;
	}
	return Ext.util.Format.usMoney(v);
};

/**
 * @method @static
 * @author SeanZ
 *
 * @param {Object} value the value from the cell
 * @param {Object} el the cell element of the grid
 * @param {Object} record the record in the grid store
 * @param {Object} rowIdx row number of the record in the grid
 * @param {Object} colIdx column number of the record in the grid
 * @param {Object} store the store in the grid
 *
 * This renders a number as hours in the format of h+:mm
 */   
Jaffa.grid.HourMinuteRenderer = function(grid){
  if(Ext.util.Format.defaultHoursFormat === 'hh:mm'){
      return Jaffa.util.converter.decimal2hmm;
  }
  return  Ext.util.Format.numberRenderer(Ext.util.Format.defaultHoursFormat || '0.00');
};
