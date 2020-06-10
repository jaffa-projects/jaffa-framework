/**
 * @class Jaffa.form
 * @author BobbyC
 *
 * Textonly renderer for standard textfields. This simply does an html encode of the value to be displayed
 */
Jaffa.form.textRenderer = function(value) {
    if (value) {
      return Ext.util.Format.htmlEncode(value);
    }
    return '';
}


/**
 *
 * Textonly renderer that renders the value as a hyperlink to a GOLDesp component.
 * Field must have a hyperlink property which contains a component and optionally inputs, values and prefix properties.
 *
 */
Jaffa.form.hyperlinkRenderer = function(value) {
    if (value) {
      var dataSource;
      this.ownerCt.bubble(function(){
        if (this.findDataSource) {
          dataSource = this.findDataSource();
          return false;
        }
      })
      if(!dataSource){
        return value;
      }

      var link = '<a style="vertical-align:top;" href="';
      link = link + 'startComponent.do?component=' + encodeURIComponent(this.hyperlink.component);
      
      var inputs = this.hyperlink.inputs?this.hyperlink.inputs.split(';'):[];
      var values = this.hyperlink.values?this.hyperlink.values.split(';'):[];
      for (var i = 0; i < inputs.length; i++){
        var dataValue = value;
        if(dataSource){
          dataValue = this.dynaClass ? Jaffa.data.Util.get(dataSource.flexBean, ((this.hyperlink.prefix?this.hyperlink.prefix + '.':'') + values[i])) : Jaffa.data.Util.get(dataSource, ((this.hyperlink.prefix?this.hyperlink.prefix + '.':'') + values[i]));
        }
        link = link + "&" + inputs[i] + "=" + encodeURIComponent(dataValue);
      }
      if(this.hyperlink.displayResultsScreen){
        link = link + "&displayResultsScreen=true";
      }
      link = link + '" target="_blank">'+ Ext.util.Format.htmlEncode(value) +'</a>';
      return link;
    }
    return '';
}