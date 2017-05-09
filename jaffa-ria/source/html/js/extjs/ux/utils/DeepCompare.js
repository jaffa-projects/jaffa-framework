/**
 *
 * Perform recursive comaprison of two javascript objects of all types, including the primitive types.
 *
 * @author: Sean Zhou, Sep. 2012
 *  
 */

Ext.ux.objCompareStrict = function(a,b){
  if (typeof a != typeof b) return false;
  if (typeof a=='function') return true;
  
  if (Ext.isArray(a) && Ext.isArray(b)) {
    if (a.length != b.length) return false;
    var len = a.length;
    for (var i=0; i<len; i++) {
      for (var j=0; j<len; j++) {
        if (Ext.ux.objCompareStrict(a[i], b[j])) break;
      }
      if (j==len) return false;
    }
    return true;
  } else if (Ext.isArray(a) || Ext.isArray(b)) return false;
  else if (typeof a == 'object') {
    for (var i in a) {
      if (Ext.ux.objCompareStrict(a[i], b[i])) continue; 
      return false;
    }
    return true;
  } else return a===b;
};
