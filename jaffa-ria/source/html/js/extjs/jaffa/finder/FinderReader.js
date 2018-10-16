/**
 * @class Jaffa.data.FinderReader
 * @extends Ext.data.JsonReader
 * An extension to {@link Ext.data.JsonReader} for reading the output returned by Jaffa's Finder components.
 * It reads the 'rows' property from the response object returned by the Finder components.
 */
Jaffa.data.FinderReader = Ext.extend(Ext.data.JsonReader, {
    read: function(response) {
      var o = response;
      if (Ext.isArray(o) && this.meta.root) {
        // move the received results to root. This happens when reading from service.
        o = {};
        // FIXME -- temporary fix to get all the values
        // it should be reverted after a fix is on the old service to deliver the total number of rows
        o[this.meta.totalProperty] = response.length;
        o.rowsCount = Math.min(response.length,16);
        if (response.length>16) {
          o.moreRecordsExist = true;
        } else {
          o.moreRecordsExist = false;
        }
        o[this.meta.root] = response;
      }
      if (o.errors && !Ext.isArray(o.errors)) o.errors = [o.errors];
      if (Ext.isArray(o.errors)) {
        var msg = '';
        for (var i=0; i<o.errors.length; i++) {
          msg += Ext.util.Format.htmlEncode(o.errors[i].localizedMessage) + '\n<br>';
        }
        if (msg) {
          // CallBack to post save method
          Ext.MessageBox.show( {
              titleToken : 'title.JaffaRIA.MessageBox.LoadError',//FIXME
              msg : msg,
              // width : 400,
              buttons : Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR
          });
          return null;
        }
      }
      return this.readRecords(o);
    }
});
