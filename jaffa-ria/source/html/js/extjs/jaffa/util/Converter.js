/**
 * @class Jaffa.util.converter
 * @author SeanZ
 */
Jaffa.util.converter = {
  /**
   * @method @static
   * Convert a decimal number of hours into the format h:mm
   */
  decimal2hmm : function(v){
    v = typeof v == 'number' ? v : parseFloat(v);
    if (isNaN(v)) {
      return '0:00';
    }
    var val = Math.abs(v);
    var h = Math.floor(val);
    var m = Math.round((val - h) * 60);
    //Rounding minutes to hour when it's 60 minutes - 5:60 is not a valid time.
    if(m === 60) {
      h = h+1;
      m = 0;
    }
    var hmm = h + ':' + (m < 10 ? '0' + m : m);
    return (v < 0) ? ('-' + hmm) : hmm;
  },

  /**
   * @method @static
   * Convert a hours in the format of h:mm into a decimal number of hours
   */
  hmm2decimal: function(val){
    if (val && Ext.isString(val)) {
      var v = (val.charAt(0) == '-') ? val.substring(1) : val
      var d = String(v).split(':'), h = 0, m = 0;
      if (d.length == 1) {
        h = parseFloat(d[0]);
        if (isNaN(h))
          return 0;
      } else {
        h = parseFloat(d[0]);
        m = parseFloat(d[1]);
        if (isNaN(h) || isNaN(m))
          return 0;
      }
      v = Math.round((h + m / 60)*100000)/100000;
      return (val.charAt(0) == '-') ? Number('-' + v) : v
    } else {
      return 0;
    }
  }

}
