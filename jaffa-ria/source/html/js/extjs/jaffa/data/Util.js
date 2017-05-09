
Jaffa.data.getAppRootUrl = function() {
  var base = document.getElementsByTagName('base')[0];
  if (base && base.href && (base.href.length > 0)) {
    base = base.href;
  } else {
    base = document.URL;
  }
  var appUrl = base.substr(0, base.indexOf("/", base.indexOf("/", base
      .indexOf('/'+'/')
      + 2)
      + 1));
  if (Jaffa.params == null) {
    Jaffa.params = {
      appUrl : appUrl
    };
  } else if (Jaffa.params.appUrl == null) {
    Jaffa.params.appUrl = appUrl;
  }
  return appUrl;
}

/**
 * @class Jaffa.data.Util
 */
Jaffa.data.Util = {
  /**
   * @method @static
   * Get the value out of an source object, given the path.
   * @param {Object} data Source object
   * @param {String} name Path of the value to get from the object. This can be in
   * javascript notation for extracting values from inner objects or arrays.
   * For example 'x.y[3].z' would be valid
   */
  get: function (data, name) {
    if (!name) return null;
    var flds = name.replace(/\[/g, '.').split('.');
    var bean = data;
    for (var i = 0, len = flds.length; i < len; i++) {
      var fld = flds[i];
      if (fld.endsWith(']')) {
        // An array
        var index = fld.substring(0, fld.length - 1) * 1;
        bean = bean[index];
      } else if (fld.indexOf('itemAt(') === 0 && fld.endsWith(')')) {
        // A MixedCollection. Either use an eval statement, or it's internal 'items' array.
        var index = fld.substring('itemAt('.length, fld.length - 1) * 1;
        bean = bean.items ? bean.items[index] : null;
      } else {
        // a property
        bean = bean[fld];
      }
      if (bean == null) {
        bean = null;
        break;
      }
    }
    return bean;
  },

  /**
   * A method used to get the value of a record by the mapping defined by its fields.
   *
   * @method @static
   * Get the value out of an source record, given the path.
   * @param {Ext.data.Record} rec Source record
   * @param {String} mapping Path of the value to get from the object.
   */
  getByMapping: function(rec, mapping) {
    // A record constructed from the Jaffa.data.Record.create() method will itself contain a getByMapping() method
    if (rec.getByMapping)
      return rec.getByMapping(mapping);

    // The input record was not created by an invocation of the Jaffa.data.Record.create() method. Continue with the original logic
    console.debug('Code review needed: The input record should have been constructed by the Jaffa.data.Record.create() method. ', rec);
    if (!rec.store) return rec.get(mapping);
    var fname;
    if (rec.store.mapping2name) {
      fname = rec.store.mapping2name.get(mapping);
      if (fname) {
        return rec.get(fname);
      }
    }
    if (mapping) {
      // build <mapping, field_name> map
      rec.fields.each(function(f) {
        if (f.mapping == mapping) {
          // Do not build the mapping2name MixedCollection if rec.store.recordType is not present. This may happen when the store is being used for multiple recordTypes.
          if (rec.store.recordType) {
            if (!rec.store.mapping2name)
              rec.store.mapping2name = new Ext.util.MixedCollection();
            rec.store.mapping2name.add(mapping, f.name);
          } else {
            delete rec.store.mapping2name;
            fname = f.name;
          }
          return false;
        }
      });
      if (rec.store.mapping2name)
        fname = rec.store.mapping2name.get(mapping);
      if (fname) {
        return rec.get(fname);
      }
      // still not found? assume mapping==name
      rec.fields.each(function(f) {
        if (mapping == f.name) {
          // Do not build the mapping2name MixedCollection if rec.store.recordType is not present. This may happen when the store is being used for multiple recordTypes.
          if (rec.store.recordType) {
            if (!rec.store.mapping2name)
              rec.store.mapping2name = new Ext.util.MixedCollection();
            rec.store.mapping2name.add(mapping, f.name);
          } else {
            delete rec.store.mapping2name;
            fname = f.name;
          }
          return false;
        }
      });
      if (rec.store.mapping2name)
        fname = rec.store.mapping2name.get(mapping);
      if (fname) {
        return rec.get(fname);
      } else {
        // I give up
        return null;
      }
    } else return rec.get(mapping);
  },

  /**
   * @method @static
   * Get the parent object's value out of a source object, given the path.
   * @param {Object} data Source object
   * @param {String} name Path of the value to get from the object. This can be in
   * javascript notation for extracting values from inner objects or arrays.
   * For example 'x.y[3].z' would result in the value of 'x.y[3]' being returned.
   * For example 'x.y[3]' would result in the value of 'x.y' being returned.
   */
  getParent: function (data, name) {
    if (!name) return null;
    name = name.replace(/\[/g, '.');
    var index = name.lastIndexOf('.');
    return index > 0 ? this.get(data, name.substring(0, index)) : data;
  },
  
  /**
   * @method @static
   * Set the value of an property in the source object with the given the path and value.
   * @param {Object} data Source object
   * @param {String} name Path of the value to get from the object. This can be in
   * javascript notation for extracting values from inner objects or arrays.
   * For example 'x.y[3].z' would be valid
   * @param {Object} value value to be set.
   * @note If any intermediate object don't exist in the path, they will be created,
   * for example if the source object has a property x, but the path is x.y[0].z it will
   * create a property on x called y, that is an array, it will add an object into the array
   * at position 0, and put a property 'z' on that object and set its value.
   */
  set: function(data, name, value) {
    if (!name) return;
    // @TODO - set object to be dirty
    var flds = name.replace(/\[/g, '.').split('.');
    var bean = data;
    var valueToSet = value === '' ? null : (value == null ? null : value);
    for (var i = 0, len = flds.length; i < len; i++) {
      var fld = flds[i];
      var nextFld = i + 1 < len ? flds[i + 1] : null;
      if (fld.endsWith(']')) {
        // An array
        var index = fld.substring(0, fld.length - 1) * 1;
        if (nextFld == null) {
          // do not set the value if it is null and if the field does not already exist
          if (value == null && bean[index] == undefined)
            return;
          bean[index] = valueToSet;
        } else if (bean[index] == null) {
          // do not create an intermediate object if the value is null; simply bail out
          if (value == null)
            return;
          if (nextFld.endsWith(']'))
            bean[index] = [];
          else if (nextFld.indexOf('itemAt(') === 0 && nextFld.endsWith(')'))
            bean[index] = new Ext.util.MixedCollection();
          else
            bean[index] = {};
        }
        bean = bean[index];
      } else if (fld.indexOf('itemAt(') === 0 && fld.endsWith(')')) {
        // A MixedCollection
        var index = fld.substring('itemAt('.length, fld.length - 1) * 1;
        if (nextFld == null) {
          // do not set the value if it is null and if the field does not already exist
          if (value == null && bean.itemAt(index) == undefined)
            return;
          value = valueToSet;
          if (bean.itemAt(index) == null)
            bean.insert(index, value);
          else
            bean.items[index] = value;
        } else if (bean.itemAt(index) == null) {
          // do not create an intermediate object if the value is null; simply bail out
          if (value == null)
            return;
          if (nextFld.endsWith(']'))
            bean.insert(index, []);
          else if (nextFld.indexOf('itemAt(') === 0 && nextFld.endsWith(')'))
            bean.insert(index, new Ext.util.MixedCollection());
          else
            bean.insert(index, {});
        }
        bean = bean.itemAt(index);
      } else {
        // a property
        if (nextFld == null) {
          // do not set the value if it is null and if the field does not already exist
          if (value == null && bean[fld] == undefined)
            return;
          bean[fld] = valueToSet;
        } else if (bean[fld] == null) {
          // do not create an intermediate object if the value is null; simply bail out
          if (value == null)
            return;
          if (nextFld.endsWith(']'))
            bean[fld] = [];
          else if (nextFld.indexOf('itemAt(') === 0 && nextFld.endsWith(')'))
            bean[fld] = new Ext.util.MixedCollection();
          else
            bean[fld] = {};
        }
        bean = bean[fld];
      }
    }
  },

  /**
   * @method @static
   * Splits a string by the separator. Trim spaces begin and end of the each value string. returns array of unique strings.
   * @param {String} v
   * @param {String} sep default=comma
   */
  strSetArray : function(v, sep) {
    if (v && typeof v == 'string' && v!='') {
      var r = v.split(sep || ','), bkt = new Jaffa.util.HashSet();
      for (var i=0; i<r.length; i++) {
        // removes spaces before and end of the string
        bkt.add(r[i].replace(/^\s+|\s+$/g, ''));
      }
      return bkt.toArray();
    } else {
      return [];
    }
  },
  
  /**
   * A utility function to scrap data from a panel.
   * @param {Ext.Panel} panel the panel contains the data
   * @param {Object} dataSet data set to be updated
   */
  getDataSetFromPanel: function(panel, dataSet){
    if (!dataSet) var dataSet = {};
    panel.items.each(function(f){
      if (f.mapping != null) {
        if (f.getValue != 'undefined') 
          Jaffa.data.Util.set(dataSet, f.mapping, f.getValue());
        else 
          Jaffa.data.Util.set(dataSet, f.mapping, '');
      }
      
      if (f.items) {
        Jaffa.data.Util.getDataSetFromPanel(f, dataSet);
      }
    }, this);
    return dataSet;
  },

  /**
   * A multi-level apply function 
   * @param {Object} destination the receiver of the properties
   * @param {Object} source the source of the properties
   * @param {Object} level the depth to which properties should be copied
   */
  deepApply: function(destination, source, level){
    if (destination && source) {
      for (var p in source) {
        if (!Ext.isDefined(destination[p])||!Ext.isObject(destination[p])) {
          destination[p] = source[p];
        }
        else 
          if (Ext.isObject(destination[p]) && level) {
            if (Ext.isNumber(level)) {
              level--;
            }
            Jaffa.data.Util.deepApply(destination[p], source[p], level);
          }
      }
    }
    return destination;
  }
  
};
