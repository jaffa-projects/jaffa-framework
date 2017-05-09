/**
 * @class Jaffa.util.HashSet
 * @extends Ext.util.Observable
 * @author SeanZ
 *
 * @note Used internally by Jaffa.finder.RemoteSelector
 */
Jaffa.util.HashSet=function() {
  this.data = [];
  Jaffa.util.HashSet.superclass.constructor.call(this);
};

Ext.extend(Jaffa.util.HashSet, Ext.util.Observable, {
  /**
   * PRIVATE variable to store the set of data
   */
  data: null,

  /**
   * @method
   *
   *
   * @params {Object} o
   */
  add : function(o) {
    if (! this.contains(o)) {
      this.data.push(o);
    }
  },
  
  /**
   * @method
   *
   *
   * @params {Object} cn
   */
  addAll : function(cn) {
    if (Ext.isArray(cn)) {
      for (var i=0; i<cn.length; i++) {
        if (!this.contains(cn[i])) {
          this.data.push(cn[i]);
        }
      }
    }
  },
  
  /**
   * @method
   *
   *
   */
  clear : function() {
    this.data = [];
  },
  
  /**
   * @method
   *
   *
   * @params {Object} o
   */
  contains : function(o) {
    if (this.data.indexOf(o) < 0) return false;
    else return true;
  },
  
  /**
   * @method
   *
   *
   * @params {Object} cn
   */
  containsAll : function(cn) {
    if (Ext.isArray(cn)) {
      for (var i=0; i<cn.length; i++) {
        if (! this.contains(cn[i])) return false;
      }
    }
    return true;
  },
  
  /**
   * @method
   *
   *
   */
  isEmpty : function() {
    if (this.data.length==0) return true;
    else return false;
  },
  
  /**
   * @method
   *
   *
   * @params {Object} o
   */
  remove : function(o) {
    var i = this.data.indexOf(o);
    if (i>=0 && i<this.data.length) {
      this.data.splice(i,1);
      return true
    } else {
      return false;
    }
  },
  
  /**
   * @method
   *
   *
   * @params {Object} cn
   */
  removeAll : function(cn) {
    if (Ext.isArray(cn)) {
      for (var i=0; i<cn.length; i++) {
        this.remove(cn[i]);
      }
    }
  },
  
  /**
   * @method
   *
   *
   */
  size : function() {
    return this.data.length;
  },
  
  /**
   * @method
   *
   *
   * @params {Object} o
   */
  toArray : function() {
    return this.data;
  }
});
