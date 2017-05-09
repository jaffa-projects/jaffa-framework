/**
 * Special DWR Reader as the response should already be an array, don't need to 'eval()' it!
 * But we still extend the JSON reader as we need to create record fields via object traversal,
 * i.e. {name: 'part', mapping: 'part.part'}
 */ 
Ext.data.DwrReader = function(meta, recordType) {
  meta = meta || {};
  Ext.applyIf(meta, {
    root: 'graphs',
    totalProperty: 'totalRecords'
  });
  Ext.data.DwrReader.superclass.constructor.call(this, meta, recordType);
};
Ext.extend(Ext.data.DwrReader, Ext.data.JsonReader, {
  read : function(response) {
    // Instead of passing just the graphs, pass the entire response to the readRecords() method,
    // so that it can stamp the totalProperty, amongst other things
    //return this.readRecords(response.graphs);
    return this.readRecords(response);
  }
});