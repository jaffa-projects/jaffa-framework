/**
 * @class Jaffa.util.RecordCache
 * @extends Ext.util.MixedCollection
 * @author SeanZ
 */
Jaffa.util.RecordCache = Ext.extend(Ext.util.MixedCollection, {
  /**
   * Get a single item based on key
   * @param {String} key 
   */
  get : function(key){
    var item = this.map[key];
    return typeof item != 'function' || this.allowFunctions ? item : null; // for prototype!
  },
  
  /**
   * Get a record recursivley based on a cascading key. 
   * @param {String} key 
   */
  getRecords : function(key) {
    var k = this.get(key);
    while (typeof k == 'string') {
      k = this.get(k);
    }
    return k;
  },
  
  /**
   * Add the records to cache with key cascades. 
   * @param {String} key 
   * @param {Ext.data.Record or Array of Ext.data.Record} records
   * @param {String} recKey
   */
  setRecords : function(key, records, recKey) {
    if (key==null) {
      return;
    } else if (recKey && ! this.get(recKey) && records) {
      this.add(recKey, records);
    }
    if (recKey == null && records) {
      this.add(key, records);
    } else if (recKey && key != recKey) {
      this.add(key, recKey);
    }
  }
});
