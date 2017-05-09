
/**
 * @class Jaffa.util.Format
 * Reusable data formatting functions
 * @singleton
 */
Jaffa.util.Format = {

  /**
   * @method @static
   * Returns an html fragment rendering function that can be reused to apply an html link format
   * <p>
   * Example
<pre><code> 
{
    header:'TaskCode'
    ,dataIndex:'taskCode'
    ,width:75
    ,renderer:Jaffa.util.Format.linkRenderer(new Ext.Template('<a target="_blank" href="TaskViewer.jsp&taskCode={1}">{0}</a>'))
}   
</code></pre>
   * 
   * in which {1} = encodeURIComponent({0})
   * 
   * @param {String} link An Ext.Template
   * @return {Function} The link formatting function
   */
  linkRenderer: function(link){
    if (typeof link == 'string') link = new Ext.Template(link);
    link.compile();
    return function(value, metadata, record, row, col, store) {
      var laV = [value, encodeURIComponent(value)];
      var frag = link.apply(laV);
      return frag;
    };
  }

  /**
   * @method @static
   * If the string being rendered is a HTTP based url, then make render it as a hyperlink
   * <p>
   * Example
<pre><code> 
{
    header:'External Ref'
    ,dataIndex:'procedureUrl'
    ,width:100
    ,renderer:Jaffa.util.Format.isLinkRenderer()
}   
</code></pre>
   *
   * @param {String} link An Ext.Template
   * @return {Function} The link formatting function
   */
  ,isLinkRenderer: function(){
      return function(value){
        if (value.indexOf('http') == 0)
              return '<a target="_blank" href="'+ value + '">'+value+'</a>';
          else
          return value;
      };
  }

//Covert a number in exponential form to full number
  ,toFullFixed: function(num) {
    if (!num || String(num).indexOf('e')<0) return num;
    var s = Math.abs(num).toExponential();
    var a = s.split('e');
    var f = a[0].replace('.', '');
    var d = f.length;
    var e = parseInt(a[1], 10);
    var n = Math.abs(e);
    if (e >= 0) {
        n = n - d + 1;}
    var z = '';
    for (var i = 0; i < n; ++i) {
        z += '0';
    }
    if (e <= 0) {
        f = z + f;
        f = f.substring(0, 1) + '.' + f.substring(1);
    } else {
        f = f + z;
        if (n < 0) {
            f = f.substring(0, e + 1) + '.' + f.substring(e + 1);
        }
    }
    if (this < 0) {
        f = '-' + f;
    }
    return f;
  }

};

