/**
 * @class Ext.ux.form
 * @method @static
 * @author SeanZ
 * Returns a checkbox renderer with specified icon.
 * If the input is not a boolean nor undefined, nor null, it will return as is with center alignment.
 * If the input is zero, it returns empty string. 
 * Both returning as is and empty string are used in summary rows of grid groups.
 * @param {string} iconType one of ['check', 'loan']
 */
Ext.ux.form.CheckBoxRenderer = function(iconType) {
  return function(v, p, record){
    //If no meta data is being passed in to this function then the field is not being rendered in a column. 
    //In this case just return the value.
    if (!p) return v;
    if (typeof v == 'boolean' || typeof v == 'undefined' || v == null) {
      p.css += ' x-grid3-check-col-td';
      return '<div class="x-grid3-' + iconType + '-col' + (v ? '-on' : '') + ' x-grid3-cc-' + p.id + '">&#160;</div>';
    } else if (v == 0) {
      return '';
    } else {
      return "<div align=center>"+v+"</div>";
    }
  };
};
