ClassMetaData = {
    /** Convert any arrays of records to a collection of records, indexed by
     * its key field(s)
     */         
    convertArrays: function(o) {
        if(o && o instanceof Array) {
            var c = new Ext.util.MixedCollection(false);
            if (o.registeredPanels) {
                c.registeredPanels = o.registeredPanels;
            }
            var key=undefined,cls=null;
            for(var i=0; i<o.length; i++) {
                var r=o[i], id=null;
                if(key==undefined) {
                    if(r && r.className && ClassMetaData[r.className] && ClassMetaData[r.className].key) {
                        cls=r.className;
                        key=ClassMetaData[cls].key;
                    } else    
                        key=null;
                }
                if(key) {
                    id = typeof(key)=="function" ? key(r) :
                         Ext.isArray(key) ? ClassMetaData.mergeKeys(key,r) :
                         Jaffa.data.Util.get(r , key);
                }
                if(id)
                    c.add(id, ClassMetaData.convertArrays(r));
                else
                    c.add(ClassMetaData.convertArrays(r));
            }
            //console.debug("Converted Array ",cls," (size=",o.length,") to collection ",c.items);
            return c;
        }
        if(o && o.className) {
            var m = ClassMetaData[o.className];
            if(m && m.collectionNames) {            
                for(var i=0;i<m.collectionNames.length;i++) {
                    var f = m.collectionNames[i];
                    //console.debug("Converting ",f," on ",o.className,o);
                    if(o[f])
                        o[f] = ClassMetaData.convertArrays(o[f]);
                }
            }
        } 
        return o;
    },

    /** Build a string which is the concatinated field list values with a | seperator
     *  example mergeKeys(['x','y'],{x:'one',y:'two',z:'three'}) => 'one|two'
     *  
     * @param {Object} fieldList
     * @param {Object} record
     */
    mergeKeys: function(fieldList, record) {
      var o=Jaffa.data.Util.get(record, fieldList[0]);
      for (var i=1; i<fieldList.length; i++) {
        o += '|'+Jaffa.data.Util.get(record, fieldList[i]);
      }
      return o;
    },

    /** Convert any collections back into arrays of records to a collection of records
     * @param o Initial Collection to be converted    
     */         
    convertCollections: function(o) {
        var a = [];
        if(o && o.items) {
            if (o.registeredPanels) {
                a.registeredPanels = o.registeredPanels;            }
            o.each(function (r) {
                //@TODO if(r.isDirty)
                a[a.length] = r;
                if(r.className) {
                    var m = ClassMetaData[r.className];
                    if(m && m.collectionNames) {            
                        for(var i=0;i<m.collectionNames.length;i++) {
                            var f = m.collectionNames[i];
                            //console.debug("Convert To Array ",f," on ",r.className,r);
                            if(r[f])
                            r[f] = ClassMetaData.convertCollections(r[f]);
                        }
                    }
                }
            },this);
        } else {
            a = o;
            if (o.className){
                var m = ClassMetaData[o.className];
                if(m && m.collectionNames) {            
                    for(var i=0;i<m.collectionNames.length;i++) {
                        var f = m.collectionNames[i];
                        //console.debug("Convert To Array ",f," on ",r.className,r);
                        if(o[f])
                          o[f] = ClassMetaData.convertCollections(o[f]);
                    }
                }
            }
        }
        return a;
    }    
}
