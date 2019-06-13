/**
 * @class Jaffa.component.PanelController
 * @extends Ext.util.Observable
 *
 * This is the base class for controlling different types of panels.
 * It provides many routines for extracting data from panels/widgets, as well as loading data.
 * It also provide methods to apply meta data to fields, grids, etc based on the use of a 
 * {@link ClassMetaData} definition
 */
Jaffa.component.PanelController = Ext.extend(Ext.util.Observable, {

  /** Text objects that can be replaced for internationalization */
  saveErrorTitleText: "Save Error"
  ,saveFieldErrorMsgTpl: "Error Saving Field: {0}"
  ,saveErrorMsgText: "An Internal Error occurred during the save operation"
  ,validateErrorTitleText: "Save Error"
  ,validateErrorMsgTpl: "Error Validating Field: {0}"
  ,mandatoryColErrorMsgTpl: "The following column is mandatory: {0}"
  ,invalidMandatoryText : "The following field(s) are mandatory: "
  ,invalidMinLenText : "The following field(s) are shorter than allowed: "
  ,invalidMaxLenText : "The following field(s) are longer than allowed: "
  ,invalidComboValueText : "The following field(s) contain invalid values: "
  ,invalidDateValueText : "The following field(s) contain invalid dates: "
  ,gridEditorValidationError : "The cell being edited contains an invalid value"
  ,keyCheckAlertMsgText: "key must be defined in the store along with with mapping."
  
    
  /**
   * Set the values on a panel's widgets from a specificed data object
   *
   * @param {Ext.Panel} The panel to set the data on
   * @param {Object} The source data object that the widgets will extract from based on their 'mapping' definitions
   *
   * @DEPRECATES Jaffa.DWRService.loadPanelFields
   */
  ,setPanelFields : function(panel, data) {
    this.applyPanelFieldsMetaRules(panel, data);
    if (!data)
      return;
    var p,c;
    panel.cascade(function(f) {
      if (f.mapping != null && f.mapping!='') {
        var x = Jaffa.data.Util.get(data, f.mapping);
        if (x != undefined) {
          if (f.linkTemplate && f.textOnly) {
            var t = f.linkTemplate;
            t.compile();
            var laV = new Array(f.linkArgs.length);
            if (f.linkArgs && f.linkArgs.length > 0){
              for(var i = 0;i<f.linkArgs.length;i++){
                laV[i]=Jaffa.data.Util.get(data,f.linkArgs[i])==undefined?'':Jaffa.data.Util.get(data,f.linkArgs[i]);
              }
            }
            
            var frag = t.apply(laV);
            f.setValue(frag);
          } else if (f.xtype == 'radio') {
            if (x == f.value) {
              f.checked = true;
            } else{
              f.checked = false;
            }
          } else if(f.xtype == 'datefield' || f.xtype == 'xdatetime'){
              // If value is not an object, it is not a date. So convert value to date.
              if (typeof x == 'object' || x == '' || x == null){
                  f.setValue(x);
              }else{
                  f.setValue(new Date(x));
              }
          } else if (f instanceof Ext.form.ComboBox || f.isXType(Ext.form.ComboBox)) {
            var record;
            if(f instanceof Jaffa.form.FinderComboBox){
              // The following optimization will ensure that a separate AJAX call is not made by the finderCombo to validate the value.
              record = f.findOrCreateRecord(data, f.mapping, x);
            }
            f.setValue(record ? record : x);
            if (!f.rendered) {
              //For some unknown reason the displayField is not being honored for an unrendered combo.
              //The following one-time listener will re-invoke the setter, so that the displayField's value is rendered.
              f.on('afterrender', function (combo) {
                this.setValue(record ? record : x);
              }, f, {single: true});
            }
          } else {
            f.setValue(x);
            if (f.rendered && (f instanceof Ext.form.HtmlEditor)) {
              f.pushValue();
            }
          }
        } else if (!f.ignoreSave) {
          if (f.linkTemplate && f.textOnly && f.getValue() != '') {
            f.setValue('');
          } else if (f.xtype == 'radio') {
            if (f.value) {
              f.checked = false;
            } else{
              f.checked = true;
            }
          } else if (typeof f.getValue() == 'string' && f.getValue()!=''){
            f.setValue('');
            if (f.rendered && (f instanceof Ext.form.HtmlEditor)) {
              f.pushValue();
            }
          } else if (typeof f.getValue() == 'boolean') {
            f.setValue(false);
          } else if (typeof f.getValue() == 'object') {
            f.setValue(null);
          }
        }
        if(f.clearInvalid){
          f.clearInvalid();
        }
      }
      
      // Get Field Description Value
      if (f.descriptionMapping != null /*& !f.disabled*/) {
        var x = Jaffa.data.Util.get(data, f.descriptionMapping);
        f.fieldDescription = x;
        // @TODO, should probably support a f.setDescription() method on the field that handles this bit!
        if (x != null && f.rendered) {
          this.setDescription2AdjacentField(f, x);
        } else if (f.el && f.el.dom && f.el.dom.parentNode && f.el.dom.parentNode.nextSibling){
          this.setDescription2AdjacentField(f, '');
        }
      }
    }, this);
  }
  
  /* PRIVATE
   * @param {Ext.form.Field} f
   * @param {Object} x
   */
  ,setDescription2AdjacentField : function(f, x) {
    if (x!='') x = "("+x+")";
    if (f.el && f.el.dom && f.el.dom.parentNode) {
      if (f.el.dom.parentNode.nextSibling) {
        // this handles text field
        f.el.dom.parentNode.nextSibling.innerHTML="<i>"+x+"</i>"; 
      } else if (f.el.dom.parentNode.parentNode && f.el.dom.parentNode.parentNode.nextSibling) {
        // this handles the combobox field
        f.el.dom.parentNode.parentNode.nextSibling.innerHTML="<i>"+x+"</i>";
      }
    }
  } 


  /**
   * Load the data object from the values in the panels fields
   *
   * This is the reverse of the {@link #setPanelFields} method, it is a simple
   * implementation with none of the additional features found on the saveRecordChanges method
   *
   * @param {Ext.Panel} The panel to get the data from
   * @param {Object} The target data object that the widgets will update based on their 'mapping' definitions
   *
   * @DEPRECATED by saveRecordChanges???
   * @DEPRECATES Jaffa.DWRService.loadRecordFields
   */
  ,getPanelFields : function(panel, data) {
    data = data || {};
    panel.cascade(function(f) {
      if (f.mapping != null) {
        var x = Jaffa.data.Util.get(data, f.mapping);
        // TODO properly handle readOnly, disabled, or hidden fields
        if((f.xtype=='datefield' || f.xtype=='xdatetime')){
          if((!f.readOnly && !f.textOnly || f.forceSave) && f.el && (x?x.toString():"") != (f.getValue()?f.getValue().toString():"")){
            // console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
            Jaffa.data.Util.set(data, f.mapping, new Date(f.getValue()));
          }
        } else if(!f.readOnly && !f.textOnly && f.el && x != f.getValue()) {
          // console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
          Jaffa.data.Util.set(data, f.mapping, f.getValue());
        }
      }
    }, this);
    return data;
  }
  
  
  /**
   * Gets data out of a modified store or panel, compares it to an existing data object array and
   * then creates a new  data object array that reflects all the changes.
   *
   * Modified records should either have an associated panel (via a '_panelId' property) or they
   * should have a record marked as dirty (via a 'dirty==true' property).
   *
   * Internally this calls on {#link getModifiedPanelFields} to update the newObject from the panel
   * or the {#link getModifiedRecordData} if is just a dirty record.
   *
   * @param {Object} origGraph an array of graphs from service model.
   * @param {Object} newGraph a Clone of origGraph to be modified or added by the Records in this grid store
   * @param {Object} store a Ext.data.Store object, the records in which are to be updated to the newGraph
   * @param {Object} metaClass a ClassMetaData.* object defining the aop rules or the name of the class.
   *
   * @DEPRECATES Jaffa.DWRService.gridTabSave2DwrGraph
   * @DEPENDS saveChangedStoreRecordsToGraph->getModifiedRecordData
   * @DEPENDS saveRecordChanges->getModifiedPanelFields
   * @DEPENDS updateMandatoryFields
   */
  ,getModifiedPanelStore : function(origGraph, newGraph, store, metaClass) {
    if (!store) {
      return;
    }
    var isChanged = false;
    if (metaClass && typeof metaClass == 'string') {
      metaClass = ClassMetaData[metaClass];
    }
    
    // update the modified and created records
    var that = this;
    var keyField = store.keyField;
    if (!keyField) {
      if (metaClass) 
        keyField = metaClass.key;
      else if (store.defaultMetaClass) 
        keyField = ClassMetaData[store.defaultMetaClass].key;
    }
    var kmapping = store.kmapping || keyField;
    store.each(function(rec) {
      // @TODO: Support composite keys
      // var recId = this.keyFn ? this.keyFn(rec) : rec.get(this.keyField);
      var recId = this.reader.getId(rec.json);
      if (recId && !rec.isNew) {
        if (!rec.dirty)
          return;
        // update a record that exists on the original graph
        for (var i=0; i<origGraph.length; i++) {
          //Check if keys match, keys may be array
          var keysMatch = false;
          if (Ext.isArray(recId)){
            var origKey = this.reader.getId(origGraph[i]);
            if (recId.length == origKey.length){
              keysMatch = true;
              for (var index=0; index<recId.length; index++){
                if (recId[index]!=origKey[index])
                  keysMatch = false;
              }
            }
          } else{
            if (recId == this.reader.getId(origGraph[i]))
              keysMatch=true;
          }
          
          if (keysMatch) {
            var hasNewGraph=false;
            for (var j=0; j<newGraph.length; j++) {
              var updateKeysMatch = false;
              if (Ext.isArray(recId)){
                var origKey = this.reader.getId(newGraph[i]);
                if (recId.length == newGraph.length){
                  updateKeysMatch = true;
                  for (var index=0; index<recId.length; index++){
                    if (recId[index]!=newGraph[index])
                      updateKeysMatch = false;
                  }
                }
              } else{
                if (recId == this.reader.getId(newGraph[i]))
                  updateKeysMatch=true;
              }
              if (updateKeysMatch) {
                // the graph of this record has been updated elsewhere
                hasNewGraph = true;
                var isUpdated = false;
                if (origGraph[i].deleteObject == true) {
                  newGraph[j].deleteObject = true;
                  isUpdated = true;
                } else {
                  isUpdated = that.getModifiedRecordData(rec, newGraph[j], origGraph[i]);
                }
                if (isUpdated) {
                  // that._updateMandatoryFields(newGraph[j], origGraph[i], metaClass);
                  newGraph[j].isChanged = true;
                  isChanged = true;
                }
                break;
              }
            }
            if (!hasNewGraph) {
              // the graph of this record has not been updated elsewhere
              var oo = {}, isUpdated = false;
              if (origGraph[i].deleteObject) {
                oo.deleteObject = true;
                // oo[this.kmapping] = recId;
                isUpdated = true;
              } else {
                isUpdated = that.getModifiedRecordData(rec, oo, origGraph[i]);
              }
              if (isUpdated) {
                // that._updateMandatoryFields(oo, origGraph[i], metaClass);
                that._setKeyFields(oo, origGraph[i], metaClass);
                oo.isChanged = true;
                newGraph.push(oo);
                if (origGraph[i].registeredPanels) origGraph[i].registeredPanels.graphInSaveModel = oo;
                isChanged = true;
              }
            }
          }
        }
      } else {
        // update a new record that is not on the server side
        var oo = {};
        if (that.getModifiedRecordData(rec, oo)) {
          oo.isChanged = true;
          newGraph.push(oo);
          if (rec.json && rec.json.registeredPanels) rec.json.registeredPanels.graphInSaveModel = oo;
          isChanged = true;
        }
      }
    }, store);
    
    // stamp the delete on deleted records
    if (store.getDeletedRecords()) {
      var recs = store.getDeletedRecords();
      for (var i=0; i<recs.length; i++) {
        var recId = store.reader.getId(recs[i].json); 
        if (recId && !recs[i].isNew) {
          var isInNewGraph = false;
          for (var k=0; k<newGraph.length; k++) {
            if(Ext.isArray(recId) && Ext.isArray(keyField)){ //multi-key support, note: you may need to override reader.getId() to return an array
              var isFound = null;
              for(var x=0; x<keyField.length; x++){
                isFound = (recId[x] == newGraph[k][keyField[x]]);
                if(isFound === false){
                  break;
                }
              }
              if(isFound){
                newGraph[k].deleteObject = true;
                isChanged = true;
                isInNewGraph = true;
              } 
            } else if(recId == newGraph[k][keyField]) {
              newGraph[k].deleteObject = true;
              isChanged = true;
              isInNewGraph = true;
            }
          }
          if(!isInNewGraph && keyField){
            var delObj = {deleteObject: true};
            if(Ext.isArray(recId) && Ext.isArray(keyField)){ //multi-key support
              for(var x=0; x<keyField.length; x++){
                delObj[keyField[x]] = recId[x];
              }
            } else {
              delObj[keyField] = recId;
            }
            newGraph.push(delObj);
            isChanged = true;
          }
          
        }
      }
    }
    
    if (isChanged)
      newGraph.isChanged = true;
    
    return isChanged;
  }


  /**
   * Copy changed fields of a Jaffa.data.Record to a node in the graph.
   *
   * 1. if key field in record is different from that of oldData, abort the operation.
   * 2. if key field in record is not null, copy it over to newData
   * 3. if oldData!=null, copy from rec to newGraph for the fields that are different in rec and in oldData.
   * 4. if oldData!=null && metaClass!=null && newGraph has updated, populate the mandatory fields in newGraph from those in rec 
   *    if they are not null, otherwise from those in oldData
   * 5. if oldData==null, copy all non-null fields in rec.
   * 
   * @param {Object} rec a Jaffa.data.Record object.
   * @param {Object} newData modified data to be populated to
   * @param {Object} oldData old data to determine which field has been modified. If it is null, 
   *                 it means the rec is created new.
   * @param {Object} metaClass defines the aop rules to the fields of the record
   *
   * @DEPRECATES Jaffa.DWRService.saveChangedStoreRecordsToGraph
   */
  ,getModifiedRecordData : function(rec, newData, oldData, metaClass) {
    var isChanged = false;
    if (oldData) {
      // step 0. check if the record has been marked for deletion
      if (rec._deleteObject) {
        newData.deleteObject = true;
        isChanged = true;
      }
      rec.fields.each(function(f) {
        var mapping = f.mapping ? f.mapping : f.name;
        if (f.key==true) {
          // step 1.
          var oldValue = Jaffa.data.Util.get(oldData, mapping);
          if ((oldValue || oldValue==0) && oldValue!=rec.get(f.name)) {
            Ext.MessageBox.alert(this.saveErrorTitleText, new Ext.Template(this.saveFieldErrorMsgTpl).apply([mapping]));
            return false;
          } else if (oldValue || oldValue==0) {
            // step 2.
            Jaffa.data.Util.set(newData, mapping, oldValue);
          }
        } else if (mapping != null && (!f.displayOnly || f.forceSave)) {
          // step 3.
          var oldValue = Jaffa.data.Util.get(oldData, mapping);
          // TODO: properly handle readOnly fields
          if((f.xtype=='datefield' || f.xtype=='xdatetime')){
            if((oldValue?oldValue.toString():"") != (rec.get(f.name)?rec.get(f.name).toString():"")){
              //console.debug("setField", mapping, " on ", data, " to ", f.getValue() );
              Jaffa.data.Util.set(newData, mapping, rec.get(f.name)?new Date(rec.get(f.name)):'');
              isChanged = true;
            }
          } else if(rec && !(rec.store instanceof Ext.ux.maximgb.treegrid.AdjacencyListStore) && rec.getSubStoreNames() && rec.getSubStoreNames().indexOf(mapping) > -1){  //added substore support
              var subStore = rec.getSubStore(mapping);
              if(subStore){
                subStore.mapping = subStore.mapping || mapping;
                isChanged = this.getModifiedSubStoreData(subStore, newData, oldData) || isChanged;
              }
          } else if(rec.subStores && !(rec.store instanceof Ext.ux.maximgb.treegrid.AdjacencyListStore) && rec.subStores.find(function(s){ return Boolean(s.mapping === mapping);})) {
              var subStore = rec.subStores.find(function(s){
                return Boolean(s.mapping === mapping);
              });
              if(subStore){
                subStore.mapping = subStore.mapping || mapping;
                isChanged = this.getModifiedSubStoreData(subStore, newData, oldData) || isChanged;
              }
          } else if ((!Ext.isEmpty(oldValue) || !Ext.isEmpty(rec.get(f.name))) && oldValue !== rec.get(f.name)) {
            // this modification makes null, '', undefined, and 0 are all the same
          // } else if ((oldValue == undefined ? '' : oldValue) != (rec.get(f.name) == undefined ? '' : rec.get(f.name))) {
          // } else if((oldValue==undefined?'':oldValue) !== rec.get(f.name)) {
            // console.debug("setField", mapping, " on ", data, " to ", f.getValue() );
            Jaffa.data.Util.set(newData, mapping, rec.get(f.name));
            isChanged = true;
          }
          // step 4.
          if (isChanged==true) {
            // make sure the object of the mapping property exists in newData
            var i = mapping.lastIndexOf('.');
            if (i<0 || Jaffa.data.Util.get(newData, mapping.substring(0, i))) {
              // mandatory fields update only when object exists
              this._updateMandatoryFields(newData, oldData, metaClass);
              this.stampDirtyReadCheck(newData, oldData, mapping);
            }
          }
        }
      },   this);
    } else {
      // step 5.
      for (var i in rec.data) {
        if (rec.fields.get(i)) {
          var mapping = rec.fields.get(i).mapping ? rec.fields.get(i).mapping : rec.fields.get(i).name;
          if (!Ext.isEmpty(rec.get(i)) && mapping && (!rec.fields.get(i).displayOnly || rec.fields.get(i).forceSave)) {
            Jaffa.data.Util.set(newData, mapping, rec.get(i));
            this.stampDirtyReadCheck(newData, oldData, mapping);
            isChanged = true;
          } else if(rec && rec.getSubStoreNames() && rec.getSubStoreNames().indexOf(mapping) > -1){  //added substore support
            var subStore = rec.getSubStore(mapping);
            if(subStore){
              subStore.mapping = subStore.mapping || mapping;
              isChanged = this.getModifiedSubStoreData(subStore, newData, oldData) || isChanged;
            }
          } else if(rec && !(rec.store instanceof Ext.ux.maximgb.treegrid.AdjacencyListStore) && rec.subStores && rec.subStores.find(function(s){ return Boolean(s.mapping === mapping);})) {
            var subStore = rec.subStores.find(function(s){
              return Boolean(s.mapping === mapping);
            });
            if(subStore){
              subStore.mapping = subStore.mapping || mapping;
              isChanged = this.getModifiedSubStoreData(subStore, newData, oldData) || isChanged;
            }
          }
        }
      } 
    }
    if ((typeof rec.saveData == 'function') && rec.saveData != Ext.emptyFn) {
      rec.saveData.call(rec, newData);
    }
    return isChanged;
  }
  
  
  /**
   * Extract the modified/created records/fields in the store into a new graph object. The input old graph
   * is used to compare with what is in the records of the store.
   * 
   * The root graph is not modified.
   * 
   * Assumptions: 
   *   1. There is only one root record in the store.
   *   2. oGraph is a graph tree with single root.
   *   3. nGraph is an empty javascript object.
   *   
   * Notes: 
   *   1. This implementaion is easy to extend for multiple roots
   *   2. To extend this implementation for nGraph not empty, the logic of merging of new nodes in nGraph 
   *      with new records in store needs to be worked out. There are two candidates: a) use certain "natural
   *      key" derived from a composite of fields to uniquely identify the new records/nodes; or b) no merging
   *      at all.
   *      
   * @param {Object} oGraph old graph data object.
   * @param {Object} nGraph new graph data object to register the changed fields. Initially this object should be an
   *                 empty javascript object with field isChanged=false.
   * @param {Ext.ux.maximgb.treegrid.AbstractTreeStore} store a tree store of records. Each record in the store can have it's own
   *        metaClass. In the absence of a record-level metaClass, the store's metaClass will be used.
   *        The MetaClass should have following properties
   *          - 'key' to indicate the id field
   *          - 'childrenFieldName' to indicate the field that has child nodes
   *          - 'siblingOrderFieldName' the field that contains the order of the nodes at that level starting at 1
   */
  ,getModifiedTreeStore: function(oGraph, nGraph, store) {
    var grf = this.getModifiedGraphNode(store.getRootNodes()[0], oGraph);
    if (grf.isChanged){
       var applyRecursivly = function(o, c){
        if(o && c && typeof c === 'object'){
          for(var p in c){
              if(typeof o[p] === 'object'){
                applyRecursivly(o[p], c[p]);
              } else {
                o[p] = c[p];
              }
          }
        }
       };
       applyRecursivly(nGraph, grf);
    }
      //Ext.apply(nGraph, grf);
    return nGraph.isChanged;
  }

  
  /**
   * Iteratively construct a graph tree for updating the modified/created/deleted records in a tree store. The process 
   * started from a record in a tree store. The result is a graph tree object. 
   * 
   * Exclude records that are marked as readOnly from being deleted or updated.
   * 
   * @param {Jaffa.data.Record} rec a record inside of an tree store @link {Ext.ux.maximgb.treegrid.AbstractTreeStore}
   * @param {Object} oGraph a javascript object of original data
   */
  ,getModifiedGraphNode: function(rec, oGraph) {
    var metaClass = this.getNodeMetaClass(rec);
    var nGraph = {isChanged: false};
    
    // Check if this record is modified 
    if (this.getModifiedRecordData(rec, nGraph, oGraph, metaClass)) {
      nGraph.isChanged = true;
      if (nGraph.deleteObject)
        return nGraph;
    }
    
    // Check the child records
    var cldRecs = rec.store.getNodeChildren(rec);
    if (cldRecs && Ext.isArray(cldRecs)) {
      var childrenPerCollection; //a MixedCollection of collectionName and associated number of child records
      for (var i = 0, len = cldRecs.length; i < len; i++) {
        if (! cldRecs[i].readOnly) {
          var cldRec = cldRecs[i];
          var collectionName = cldRec.get(rec.store.parent_collection_name);
          if (!collectionName)
            collectionName = metaClass.childrenFieldName;
          if (!collectionName) {
            //console.error("The record should have the name of the collection of the parent, or the metaClass should have the childrenFieldName property");
          } else {
            // Apply the siblingOrderFieldName
            var childMetaClass = this.getNodeMetaClass(cldRec);
            if (childMetaClass.siblingOrderFieldName) {
              if (!childrenPerCollection)
                childrenPerCollection = new Ext.util.MixedCollection();
              var count = childrenPerCollection.containsKey(collectionName) ? childrenPerCollection.get(collectionName) : 0;
              ++count;
              cldRec.set(childMetaClass.siblingOrderFieldName, count);
              childrenPerCollection.add(collectionName, count);
            }
            
            // Now check if the child record has changed
            var grf = this.getModifiedGraphNode(cldRec, cldRec.json);
            if (grf.isChanged) {
              if (!nGraph[metaClass.key]) {
                if (rec.store.parent_id_field_name && rec.fields.get(rec.store.parent_id_field_name) && rec.fields.get(rec.store.parent_id_field_name).mapping) {
                  // delete the parent id value from child node field when parent is a new node
                  grf[rec.fields.get(rec.store.parent_id_field_name).mapping] = null;
                }
              }
              if (!nGraph[collectionName])
                nGraph[collectionName] = [];
              nGraph[collectionName].push(grf);
              nGraph.isChanged = true;
            }
          }
        }
      }
    }
    
    // Check the deleted child records
    cldRecs = rec._deletedRecords;
    if (cldRecs && Ext.isArray(cldRecs)) {
      for (var i = 0, len = cldRecs.length; i < len; i++) {
        if (! cldRecs[i].readOnly) {
          var cldRec = cldRecs[i];
          var collectionName = cldRec.get(rec.store.parent_collection_name);
          if (!collectionName)
            collectionName = metaClass.childrenFieldName;
          if (!collectionName) {
            //console.error("The record should have the name of the collection of the parent, or the metaClass should have the childrenFieldName property");
          } else {
            // mark the record for deletion
            cldRec._deleteObject = true;
            var grf = this.getModifiedGraphNode(cldRec, cldRec.json);
            if (!nGraph[collectionName])
              nGraph[collectionName] = [];
            nGraph[collectionName].push(grf);
            nGraph.isChanged = true;
          }
        }
      }
    }
    
        // update the arrays of items in graph from sub stores in the record
    // It uses mapping property on subStore to determine the path on the graph. If mapping property is not provided, the subStore name will be used. 
    var subNames = rec.getSubStoreNames();
    if (subNames) {
      for (var i=0; i<subNames.length; i++) {
        var subStore = rec.getSubStore(subNames[i]);
        subStore.mappping = subStore.mapping || subNames[i];
        if (subStore) {
          this.getModifiedSubStoreData(subStore, nGraph, oGraph);
        }
      }
    }
    
    
    if (rec.afterGraphProcess)
      rec.afterGraphProcess(nGraph, oGraph)

    if (nGraph.isChanged)
      this._setKeyFields(nGraph, oGraph, metaClass);
    
    return nGraph;
  }
  
  /**
   * Returns the metaClass for the input record. If not defined, the metaClass of the corresponding store is returned.
   * @param {Jaffa.data.Record} record a record inside of an tree store @link {Ext.ux.maximgb.treegrid.AbstractTreeStore}
   */
  ,getNodeMetaClass: function (record) {
    var metaClass = record.get('_metaClass');
    if (!metaClass)
      metaClass = record.store.metaClass;
    if (typeof metaClass === 'string')
      metaClass = ClassMetaData[metaClass];
    return metaClass;
  }
  
  ,getModifiedSubStoreData: function(subStore, nGraph, oGraph){
    var isChanged = false;
    var subMapping = subStore.mapping;
    var nSubG = Jaffa.data.Util.get(nGraph, subMapping);
    if (!nSubG) {
      nSubG = [];
      Jaffa.data.Util.set(nGraph, subMapping, nSubG);
    }
    var oSubG;
    if (oGraph) {
      // Note: it may need to handle if mixed collection is used to get the sub graphs.
      oSubG = Jaffa.data.Util.get(oGraph, subMapping);
    }
    if (this.getModifiedPanelStore(oSubG, nSubG, subStore, subStore.metaClass)) {
      nGraph.isChanged = true;
      isChanged = true;
    }
    return isChanged;
  }
  
  /**
   * Saves the changed values only on the panel back to the data object.
   * If there are changes these are set on the newData object along with setting
   * an 'isChanged' property to true on the object.
   *
   * @param {Ext.Panel} panel   The panel to get the modified fields from
   * @param {Object} newData The object that is updated with the modified fields
   * @param {Object} oldData The original source object used so the current panel
   *                values can be compare with this to see if there are any
   *                data changes.
   *
   * @return {boolean} Returns true if there at least one field is modified 
   *
   * @DEPRECATES Jaffa.DWRService.saveRecordChanges
   */
  ,getModifiedPanelFields: function(panel, newData, oldData) { //overrriden to support forceSave. This should be removed when support is added to the framework in the next release
      var isChanged = false;
      panel.items.each(function(f) {
        if (f.mapping != null && !f.ignoreSave) {
          var fieldChanged=false;
          var oldValue = oldData ? Jaffa.data.Util.get(oldData, f.mapping) : null;
          // TODO properly handle readOnly fields
          if((f.xtype=='datefield' || f.xtype=='xdatetime')){
            if (typeof oldValue == 'object' || oldValue == undefined) {
              if (f.el && (!f.readOnly && !f.textOnly || f.forceSave) && (oldValue ? oldValue.toString() : "") != (f.getValue() ? f.getValue().toString() : "")) {
                // console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
                Jaffa.data.Util.set(newData, f.mapping, f.getValue() ? f.getValue() : '');
                fieldChanged = true;
              }
            }else{
              var oldValueDate;
              if (oldValue != '' && oldValue != null) {
                oldValueDate = new Date(oldValue);
              } else{
                oldValueDate = '';
              }
              if (f.el && (!f.readOnly && !f.textOnly || f.forceSave) && (oldValueDate ? oldValueDate.toString() : "") != (f.getValue() ? f.getValue().toString() : "")) {
                // console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
                Jaffa.data.Util.set(newData, f.mapping, f.getValue() ? f.getValue() : '');
                fieldChanged = true;
              }
            }
          } else if (f.xtype == 'radiogroup') {
            if (f.getValue() && f.getValue().inputValue != oldValue) {
              Jaffa.data.Util.set(newData, f.mapping, f.getValue().inputValue);
              fieldChanged = true;
            }
          } else if (f.xtype == 'radio') {
            if (f.checked == true && (oldValue == undefined ? '' : oldValue) != f.value){
              Jaffa.data.Util.set(newData, f.mapping, f.value);
              fieldChanged = true;
            }
          } else if (f.xtype=='comment' && f.commentStyle && f.commentStyle.toLowerCase()!='plain'){
            //If comment is fifo or lifo it always post if not null
            var nv = f.getValue();
            nv = Boolean(nv) || nv===0 ? nv : null;
            if (nv != null && nv !="") {
              Jaffa.data.Util.set(newData, f.mapping, f.getValue());
              fieldChanged = true;
            }
          }else if(f.el && ((!f.readOnly || f.xtype == 'xhtmleditor') && !f.textOnly || f.forceSave)) {
            // allow 0 to be valid value to be assigned
            var ov = Boolean(oldValue) || oldValue===0 ? oldValue : null;
            var nv = f.getValue();
            nv = Boolean(nv) || nv===0 ? nv : null;
            if (ov != nv) {
              var value = f.getValue();
              //GOLD-91199: strip out invalid characters
              if (Ext.isString(value) && !Ext.isEmpty(value)) {
                  value = cleanInvalidChar(value);
              }
              Jaffa.data.Util.set(newData, f.mapping, value);
              fieldChanged = true;
            }
          }
          if(fieldChanged) {
            // console.debug("   Field ", f.mapping, " changed from ",oldValue," to ", f.getValue() );
            isChanged=true;

            this.stampDirtyReadCheck(newData, oldData, f.mapping);
          }
        }
        // Look at nested items if this is a panel rather that a field. Stop recursion if panel/f is already registered.
        if (f.items && !f.transientFields && (!this.registeredPanels || this.registeredPanels.indexOf(f)<0) && f.xtype != 'radiogroup' && f.xtype != 'checkboxgroup' && !(f instanceof Ext.form.Field)) {
          if (this.getModifiedPanelFields(f, newData, oldData)) {
            isChanged = true;
          }
        }
      }, this);
      if(isChanged) {
        // For a FlexBean, mix-in the name of the associated FlexClass
        if (newData.flexBean && (typeof newData.flexBean == 'object')) {
          if (!newData.flexBean.dynaClass && oldData.flexBean && oldData.flexBean.dynaClass && oldData.flexBean.dynaClass.name)
            newData.flexBean.dynaClass = {name: oldData.flexBean.dynaClass.name};
          else if (panel.dynaClass)
            newData.flexBean.dynaClass = {name: panel.dynaClass};
        }

        newData.isChanged = true;
        // console.debug("Panel had changes",panel.id, "(",panel.title,")");
      }
      return isChanged;
    }


  /**
   * Applies MetaData to the panel based on information from the object being bound to the widgets.
   * Can be called directly, but is also called as part of {@link #setPanelFields}
   *
   * This <b>MUST be called BEFORE</b> the screen is rendered, if the widgets are already rendered this
   * routine will fail to apply much of its metadata.  
   *
   * It by default looks for all metadata in the static object ClassMetaData
   * It uses the "className" embedded in the data object along with the "mapping" name 
   * specified in the widget to determing the Field Metadata to use.
   * If the mapping contains nested referenced (ie: a.b.c.d) it will extract the "className"
   * from the object referred to as 'a.b.c' and look for field name 'd'    
   *
   * The class name can be overriden on the widget by the property 'metaClass'
   * The field name can be overriden on the widget by the property 'metaField'
   *
   * @param {Ext.Panel} panel (Required) The panel to apply the meta data to. It must have a controller.
   * @param {Object} data (Optional) The source data object, that may contain a reference to the approprite metaClass to be used
   * @param {String} includeList (Optional) This can be used to only apply a subset of rules to the panel. 
   * If no value is supplied to this parameter then all rules are applied.
   * Currently supported rules are: LABEL, CASE, MINLENGTH, MAXLENGTH, READONLY, HIDDEN, MANDATORY, MIVALUE, MAXVALUE, LAYOUT, TARGETFIELDS, CRITERIAFIELDS, STATICPARAMS
   *
   * @DEPRECATES Jaffa.DWRService.applyMetaRules      
   */
  ,applyPanelFieldsMetaRules : function(panel, data, includeList) {
    console.debug("Loading Panel id=", panel.id, " from data=", data);
    if (!includeList || includeList == '') includeList = ['LABEL', 'CASE', 'MINLENGTH', 'MAXLENGTH', 'READONLY', 'HIDDEN', 'MANDATORY', 'MINVALUE', 'MAXVALUE', 'LAYOUT', 'TARGETFIELDS', 'CRITERIAFIELDS', 'STATICPARAMS', 'INITIALIZE', 'COMMENTSTYLE'];

    if (data == null)
      data = new Object();

    var hidePanelList = Rules.get('Jaffa.metadata.HidePanelList') ? Rules.get('Jaffa.metadata.HidePanelList').split(",") : "";
    var hideButtonList = Rules.get('Jaffa.metadata.HideButtonList') ? Rules.get('Jaffa.metadata.HideButtonList').split(",") : "";
    panel.cascade(function(f) {
      console.debug("Applying Metadata to widget ", f);
      if (f && (f instanceof Ext.grid.GridPanel)) {
        if (f.controller) {
          f.controller.applyGridColumnMetaRules(f, includeList);
        } else if (panel.controller) {
          panel.controller.applyGridColumnMetaRules(f, includeList);
        } else {
          var cntrl = f.lookupController();
          if (cntrl) {
            f.controller.applyGridColumnMetaRules(f, includeList);
          }
        }
      } else if (f && !f.mapping && f.fieldLabelToken && !f.fieldLabel) {
        f.fieldLabel = Labels.get(f.fieldLabelToken);
      } else if ((f.mapping != null && f.mapping != '') || f.metaField != null) {
        this.applyFieldMetaRules(f,data,includeList,panel);
      }
      if(hidePanelList) {
        if(f instanceof Ext.TabPanel){
          if(f.items && Ext.isFunction(f.items.each)){
            f.items.each(function(el){
              if((el.itemId || el.id) && (hidePanelList.indexOf(el.itemId)>=0 || hidePanelList.indexOf(el.id)>=0)){
                (!f.rendered) ? f.items.remove(el) : f.remove(el,true);
              }
            })
          }
        } else {
          if ((f.id || f.itemId) && (hidePanelList.indexOf(f.itemId)>=0 || hidePanelList.indexOf(f.id)>=0)){
            f.hide();
            f.hidden=true;
          }
        }
      }
      if(hideButtonList && f instanceof Ext.Panel) {
        this._hideWidgetsOnToolbar(f,hideButtonList);
      }
    },this);
  }

  /**
    * Applies MetaData to the field based on information from the object being bound to the widgets.
    * Can be called directly, but is also called as part of {@link #applyPanelFieldsMetaRules}
    *
    * This <b>MUST be called BEFORE</b> the screen is rendered, if the widgets are already rendered this
    * routine will fail to apply much of its metadata.
    *
    * It by default looks for all metadata in the static object ClassMetaData
    * It uses the "className" embedded in the data object along with the "mapping" name
    * specified in the widget to determing the Field Metadata to use.
    * If the mapping contains nested referenced (ie: a.b.c.d) it will extract the "className"
    * from the object referred to as 'a.b.c' and look for field name 'd'
    *
    * The class name can be overridden on the widget by the property 'metaClass'
    * The field name can be overridden on the widget by the property 'metaField'
    *
    * @param {Ext.form.Field} f (Required).
    * @param {Object} data (Optional) The source data object, that may contain a reference to the appropriate metaClass to be used
    * @param {String} includeList (Optional) This can be used to only apply a subset of rules to the field.
     * @param {Object} panel (Optional) Parent panel of the field.The panel to apply the meta data to.
    * If no value is supplied to this parameter then all rules are applied.
    * Currently supported rules are: LABEL, CASE, MINLENGTH, MAXLENGTH, READONLY, HIDDEN, MANDATORY, MIVALUE, MAXVALUE, LAYOUT, TARGETFIELDS, CRITERIAFIELDS, STATICPARAMS
    */
  ,applyFieldMetaRules : function(f, data, includeList,panel) {
    if(!f){
      return false;
    }
    if (!includeList){
      includeList = ['LABEL', 'CASE', 'MINLENGTH', 'MAXLENGTH', 'READONLY', 'HIDDEN', 'MANDATORY', 'MINVALUE', 'MAXVALUE', 'LAYOUT', 'TARGETFIELDS', 'CRITERIAFIELDS', 'STATICPARAMS', 'INITIALIZE', 'COMMENTSTYLE'];
    }
    if (data == null){
      data = {};
    }
    console.debug("Applying Metadata to field with mapping ", f.mapping);
    var meta = null;
    var metaClass = null;
    var metaField = f.metaField ? f.metaField : f.mapping;

    // determine metaClass
    if (f.metaClass && f.metaClass != null) {
      metaClass = f.metaClass;
    } else if (this.metaClass) {
      metaClass = this.metaClass;
    } else if (panel && (panel.metaClass || panel.initialConfig.metaClass)) {
      metaClass = panel.metaClass || panel.initialConfig.metaClass;
    } else if (f.mapping) {
      // as a last resort, parse the mapping string to figure out the metaClass
      var mapSplit = f.mapping.split('.');
      var metaNode = data;
      for (var i = 0; i <= mapSplit.length - 2; i++) {
        metaNode = metaNode[mapSplit[i]];
      }
      if (metaNode && metaNode.className)
        metaClass = metaNode.className;
    }
    console.debug("Found MetaClass", metaClass, 'for field', f.mapping, f);

    if (metaClass && typeof metaClass == 'string') {
      // metaClass can be specified as the name of the graph class inside of ClassMetaData
      // or just the graph class
      metaClass = ClassMetaData[metaClass];
    }
    if (metaClass && metaClass.fields && metaField != null && metaField != '')
      meta = metaClass.fields[metaField] || metaClass.fields[metaField.split('.').pop()];

    if (meta != null) {

      //apply field level metaOverrides to meta
      if (f.metaOverrides){
        meta = Jaffa.data.Util.deepApply(meta, f.metaOverrides, -1);
      }

      if (f.layout != 'adjacentfield') {
        // console.debug('Got meta for:' + metaClass +'.'+ metaField);
        // Evaluate Label Rule
        if (includeList.indexOf('LABEL') >= 0 && meta.label && meta.label.indexOf('???') < 0 && f.noLabel != true)
          if (f.fieldLabel == '' || f.fieldLabel == null) {
            if (f.fieldLabelToken) {
              f.fieldLabel = Labels.get(f.fieldLabelToken);
            } else {
              f.fieldLabel = meta.label;
              f.fieldLabelToken = meta.labelToken;
            }
          }

        // Apply general metadata to field ('MANDATORY', 'CASE', 'MINLENGTH', 'MAXLENGTH')
        Jaffa.component.PanelController.prototype.applyValidationMetaData(f, metaClass, meta, includeList);
        // Evaluate ReadOnly Rule
        if (includeList.indexOf('READONLY') >= 0 && meta.readOnly && meta.readOnly == true)
          f.textOnly = true;
      }
      // Evaluate Hidden Rule
      if (includeList.indexOf('HIDDEN') >= 0 && meta.hidden && meta.hidden == true && f.noLabel != true) {
        if (f.rendered) {
          f.hidden = true;
          if (f.getEl().up('.x-form-item'))
            f.getEl().up('.x-form-item').setDisplayed(false);
        }
        else {
          f.hidden = true;
          f.hideLabel = true;
          f.hide();
          f.on('render', function() {
            if (this.getEl().up('.x-form-item'))
              this.getEl().up('.x-form-item').setDisplayed(false);
          });
        }
      }

      //Evaluate Layout Rule
      if (includeList.indexOf('LAYOUT') >= 0 && meta.layout) {
        if (meta.layout === '[dateonly.julian5]') {
          f.format = 'yJ';
        } else if (meta.layout === '[password]' || meta.layout === 'password') {
          f.format = 'password';
        }

        if(f.initialConfig){
          f.initialConfig.format = f.format;
        }
        if(f.format === 'password'){
          f.inputType = 'password';
        }
      }

      //Evaluate TARGETFIELDS Rule
      if (includeList.indexOf('TARGETFIELDS') >= 0 && meta.foreignKeyInfo && meta.foreignKeyInfo.targetFields) {
        var targetFieldsArray = meta.foreignKeyInfo.targetFields.split(';');
        f.targetFields = targetFieldsArray;
      }
      //Evaluate CRITERIAFIELDS Rule
      if (includeList.indexOf('CRITERIAFIELDS') >= 0 && meta.foreignKeyInfo && meta.foreignKeyInfo.criteriaFields) {
        var criteriaFieldsArray = meta.foreignKeyInfo.criteriaFields.split(';');
        f.criteriaFields = criteriaFieldsArray;
      }
      //Evaluate STATICPARAMS Rule
      if (includeList.indexOf('STATICPARAMS') >= 0 && meta.foreignKeyInfo && meta.foreignKeyInfo.staticParameters) {
        try {
          var staticParams = Ext.util.JSON.decode(htmlEntityDecode(meta.foreignKeyInfo.staticParameters));
          f.staticBaseParams = staticParams;
        } catch (err){}
      }

      if (includeList.indexOf('INITIALIZE') >= 0 && meta.initialize && (panel && panel.controller && !panel.controller.isLoaded)){
        if (meta.type=='datetime' || meta.type=='dateonly' || meta.type=='monthyear'){
          f.setValue(new Date(meta.initialize));
        } else {
          f.setValue(meta.initialize);
        }
      }

      if (meta.hyperlink && meta.hyperlink.component) {
        f.hyperlink = meta.hyperlink;
      }

      if (includeList.indexOf('COMMENTSTYLE') >= 0 && meta.commentStyle) {
        f.commentStyle = meta.commentStyle;
      }

    } else if (!f.fieldLabel && f.fieldLabelToken) {
      f.fieldLabel = Labels.get(f.fieldLabelToken);
    } else {
      // console.debug('No Metadata for ' + metaClass +'.'+ metaField);
    }

    if (f.hyperlink){
      if (f.constructor.prototype.displayRenderer==f.displayRenderer)
        f.displayRenderer= Jaffa.form.hyperlinkRenderer;
    }

    if(Rules.get('Jaffa.metadata.HideFieldList')) {
      var hideFieldList = Rules.get('Jaffa.metadata.HideFieldList').split(',');
      if (f.mapping && hideFieldList.indexOf(f.mapping)>=0){
        if (f.rendered) {
          f.hidden = true;
          if (f.getEl().up('.x-form-item'))
            f.getEl().up('.x-form-item').setDisplayed(false);
        }
        else {
          f.hidden = true;
          f.hideLabel = true;
          f.hide();
          f.on('render', function() {
            if (this.getEl().up('.x-form-item'))
              this.getEl().up('.x-form-item').setDisplayed(false);
          });
        }
      }
    }

    if(Rules.get('Jaffa.metadata.ReadOnlyFieldList')) {
      if (f.mapping && Rules.get('Jaffa.metadata.ReadOnlyFieldList').split(',').indexOf(f.mapping) >= 0) {
        if (f.xtype == 'finderComboGrid') {
          if (!f.disabled) f.disable();
        }
        else if (f.xtype == 'checkbox'){
          if (!f.disabled) {
            if (f.rendered)
              f.disable();
            else
              f.disabled = true;
          }
        }
        else {
          if (!f.textOnly) {
            if (f.rendered)
              f.setTextOnly(true);
            else
              f.textOnly = true;
          }
        }
      }
    }
  }
  /**
   * Apply MetaData rule to the grid.
   * <p>
   * It by default looks for all metadata in the static object ClassMetaData
   * It uses the "metaClass" embedded in the grid object along with the column's "dataIndex" name 
   * to determing the Field Metadata to use.
   * <p>
   * The class name can be overriden on the column model by the property 'metaClass'
   * The field name can be overriden on the column model by the property 'metaField'
   * <p>
   * Based on the definitions in ClassMetaData the following rules are applied
   * <ul>
   * <li>the labels are applied to the column header
   * <li>the CaseType rule is applied to the column editor
   * </ul>
   *
   * @param {Ext.grid.GridPanel} panel (Required) The panel to apply the meta data to
   * @param {String} includeList (Optional) This can be used to only apply a subset of rules to the panel. 
   * If no value is supplied to this parameter then all rules are applied.
   * Currently supported rules are: LABEL, CASE, MINLENGTH, MAXLENGTH, MANDATORY
   *
   * @DEPRECATES Jaffa.DWRService.applyMetaRulesToGrid
   */
  ,applyGridColumnMetaRules : function(grid, includeList) {
    if (!includeList || includeList == '')
      includeList = ['LABEL', 'CASE', 'MINLENGTH', 'MAXLENGTH', 'READONLY', 'HIDDEN', 'MANDATORY'];

    var defaultMetaClass = grid.metaClass;
    if (! defaultMetaClass && grid.defaults) {
      defaultMetaClass = grid.defaults.metaClass;
    }
    var ccf = grid.getColumnModel().config;
    for (var i=0; i<ccf.length; i++) {
      var metaClass = defaultMetaClass, metaField;
      if (ccf[i].editor && ccf[i].editor.field) {
        metaClass = ccf[i].editor.field.initialConfig.metaClass || defaultMetaClass;
        metaField = ccf[i].editor.field.initialConfig.metaField;
      } else {
        metaField = ccf[i].metaField;
      }
      // resolve metaField from mapping or dataIndex
      if (metaField == null && grid.getStore().fields) {
        // metaField is functionally independent of mapping and dataIndex
        // The following algorithm is a convenience way to get metaField when it is happened to 
        // be the same as what is in mapping or dataIndex.
        var recT = grid.getStore().fields.get(ccf[i].dataIndex);
        if (recT)
          metaField = recT.mapping ? recT.mapping : recT.name;
      }
      // resolve metaClass
      if (metaClass && typeof metaClass == 'string') {
        // metaClass can be specified by the name of the graph class or just the graph class
        metaClass = ClassMetaData[metaClass];
      }
      // find the meta
      var meta = null;
      if (metaClass && metaClass.fields && metaField != null && metaField != '') {
        meta = metaClass.fields[metaField] || metaClass.fields[metaField.split('.').pop()];
      }
      // apply rules
      if (meta) {
        
        // apply labels
        if (includeList.indexOf('LABEL') >= 0 && !ccf[i].label && meta.label && meta.label.indexOf('???')<0 && ccf[i].noLabel != true)
            ccf[i].header = meta.label;
        
        // apply other rules if field is editable
        if (ccf[i].editor && ccf[i].editor.field) {

          // Apply general metadata to field ('MANDATORY', 'CASE', 'MINLENGTH', 'MAXLENGTH')
          Jaffa.component.PanelController.prototype.applyValidationMetaData(ccf[i].editor.field, metaClass, meta, includeList);
        }
      }
    }
  }

  /** 
   * Common routine for either panel fields, or grid editors to apply
   * basic meta data validation rules.
   *
   * Currently implements : 'MANDATORY', 'CASE', 'MINLENGTH', 'MAXLENGTH'
   */
  ,applyValidationMetaData : function(field, metaClass, meta, includeList) {
    // Apply MetaRules if this field is not a criteria operator
    if (field.operator != true) {

      // Evaluate Mandatory Rule
      if (includeList.indexOf('MANDATORY') >= 0 && meta.mandatory && meta.mandatory==true && this.isSearchPanel != true && field.xtype!='checkbox' && (field.mandatory !== false))
        field.allowBlank = false;

      if((field.xtype=='combo'||field.xtype=='finderCombo'||field.xtype=='finderComboGrid'||field.xtype=='lovcombo') && (field.valueField!=field.displayField)) {
        // The following COMBO BOX rules apply to the valueField and displayField differently

        if (includeList.indexOf('CASE') >= 0)
          // The current meta will be the metadata for the valueField, we have no reference to the display field meta!
          if (meta.caseType && meta.caseType != 'MixedCase') {
            field.valueFieldCase = meta.caseType.toLowerCase();

          // See if we can find metadata for the displayField on the same metaClass, if we can we can use it!
          // provide a property on the field called 'displayFieldMeta' to specify a different field name. 
          // This is useful if you have a dummy field (code+description) but what it to use the description meta data
          var dmeta = metaClass.fields[field.initialConfig.displayFieldMeta || field.primarySearchField ||field.displayField] || (field.meta && field.meta.fields ? field.meta.fields[field.primarySearchField || field.initialConfig.displayFieldMeta || field.displayField] : null);
          if(dmeta && dmeta.caseType && dmeta.caseType != 'MixedCase') {
            //console.debug("Case Rule: ",field.mapping, f,meta);
            field.displayFieldCase = meta.caseType.toLowerCase();
            field.style = 'text-transform: '+field.displayFieldCase+';';
            field.initialConfig.style = field.style;
          }
        }

      } else {
        // The following rules should not be applied if its a combo with a displayField

        if (includeList.indexOf('CASE') >= 0 && meta.caseType && meta.caseType != 'MixedCase') {
          //console.debug("Case Rule: ",field.mapping, f,meta);
          field.valueFieldCase = meta.caseType.toLowerCase();
          field.displayFieldCase = meta.caseType.toLowerCase();
          field.style = 'text-transform: '+field.displayFieldCase+';';
          if(field.initialConfig){
            field.initialConfig.style = field.style;
          }
        }
        

        if (!field.ignoreSave) {
          if (includeList.indexOf('MINLENGTH') >= 0 && meta.minLength && meta.minLength >= 0) 
            field.minLength = meta.minLength;

          if (includeList.indexOf('MAXLENGTH') >= 0 && meta.maxLength && meta.maxLength >= 0) {
            field.maxLength = meta.maxLength;
            if (meta.allowDecimals !== undefined && meta.allowDecimals !== null)
              field.allowDecimals = meta.allowDecimals;
            if (meta.maxIntegralLength !== undefined && meta.maxIntegralLength !== null)
              field.maxIntegralLength = meta.maxIntegralLength;
            if (meta.decimalPrecision !== undefined && meta.decimalPrecision !== null)
              field.decimalPrecision = meta.decimalPrecision;
            if (field.rendered) {
              field.getEl().dom.maxLength = field.maxLength;
              if (!field.width)
                field.setWidth(Math.min(Math.max(field.maxLength, 5), 40) * 9);
            } else {
              // TODO: Need to set the maxLength for non-rendered fields
              if (!field.width)
                field.width = Math.min(Math.max(field.maxLength, 5), 40) * 9;
            }
          }
          
          if (includeList.indexOf('MINVALUE') >= 0 && Ext.isNumber(meta.minValue)) {
            field.minValue = meta.minValue;
          }
          if (includeList.indexOf('MAXVALUE') >= 0 && Ext.isNumber(meta.maxValue)) {
            field.maxValue = meta.maxValue;
          }
          
          //Enforces allowDecimals for number criteria fields even when MAXLENGTH is not in the includeList
          if(field.isCriteriaField){
            if (meta.allowDecimals !== undefined && meta.allowDecimals !== null){
              field.allowDecimals = meta.allowDecimals;
            }
          }
          
        }
      }
    }  
  }
  
  // private
  ,_hideWidgetsOnToolbar: function(panel, hideFields) {
    var hideItems  = function(tb){
      if(tb && tb.items && Ext.isFunction(tb.items.each)){
        tb.items.each(function(itm) {
          if (hideFields.indexOf(itm.id)>=0 || hideFields.indexOf(itm.itemId)>=0) {
            itm.hide();
            itm.brHidden = true;
          }
        });
      }
    }
    if (panel.getTopToolbar && panel.getTopToolbar()) {
      hideItems(panel.getTopToolbar())
    }
    if (panel.getBottomToolbar && panel.getBottomToolbar()) {
      hideItems(panel.getBottomToolbar())
    }
  }

  ,toggleToolbarItems: function(panel, disable, fields) {
    var toggled = [];
    if (panel.getTopToolbar && panel.getTopToolbar()) {
      var tb = panel.getTopToolbar();
      if(tb && tb.items){
        tb.items.each(function(itm) {
          if (fields.indexOf(itm.id)>=0 || fields.indexOf(itm.itemId)>=0) {
            (disable) ?itm.disable() : itm.enable();
            toggled.push(itm.itemId || itm.id);
          }
        });
      }
    }
    return toggled;
  }
  ,_deleteTreeNodesByRule: function(panel, hideFields) {
    if (panel.root && panel.root.attributes && panel.root.attributes.children){
      for (var i=0; i<panel.root.attributes.children.length;i++){
        if (hideFields.indexOf(panel.root.attributes.children[i].id) >= 0) {
          panel.root.attributes.children.remove(panel.root.attributes.children[i]);
          i--;
        }
      }
      if (panel.rendered) {
        var toDelete = [];
        panel.getRootNode().eachChild(function(child) {
          if (hideFields.indexOf(child.id)>=0) toDelete.push(child);
        });
        for (var i=0; i<toDelete.length; i++) {
          toDelete[i].remove();
        }
      }
    }
  }
  
  /**
   * Hides a list of fields on a panel or its sub panel
   *
   * @param panel Panel on which to find the fields to hide
   * @param hiddenList Comma seperated list of field names that should be hidden
   * 
   * @DEPRECATES Jaffa.DWRService.applyHiddenRules
   */
  ,hideFields : function(panel, hiddenList) {
    var hideFields = hiddenList.split(',');
    this._deleteTreeNodesByRule(panel, hideFields);
    this._hideWidgetsOnToolbar(panel, hideFields);
    panel.cascade(function(f) {
        if (f.mapping){
            if(hideFields.indexOf(f.mapping) >= 0)
                if (f.rendered) {
                    f.hidden = true;
                    if (f.getEl().up('.x-form-item'))
                        f.getEl().up('.x-form-item').setDisplayed(false);
                    else if (typeof f.hide == 'function') 
                        f.hide();
                }
                else {
                    f.hidden = true;
                    f.hideLabel = true;
                    f.hide();
                    f.on('render', function(){
                        if (this.getEl().up('.x-form-item'))
                            this.getEl().up('.x-form-item').setDisplayed(false);
                        else if (typeof f.hide == 'function') 
                            f.hide();
                    });
                }
        }
        this._deleteTreeNodesByRule(f, hideFields);
        this._hideWidgetsOnToolbar(f, hideFields);
    }, this);
  }
  
  /**
   * Hides a list of child panels base on a parent panel
   *
   * @param panel Panel on which to find the fields to hide
   * @param hiddenPanelList Comma seperated list of panel id's that should be hidden
   * @param panelPrefix (Optional) prefix name used on all the Panel names in the supplied list
   *
   * @DEPRECATES Jaffa.DWRService.applyHiddenPanelRules
   */
  ,hidePanels : function(panel, hiddenPanelList, panelPrefix) {
    var hidePanels = hiddenPanelList.split(',');
    if  (panelPrefix==undefined) panelPrefix='';
    
    for (var i = 0; i < hidePanels.length; i++ ){
        if (Ext.getCmp(panelPrefix + hidePanels[i]) && Ext.getCmp(panelPrefix + hidePanels[i]).hide)
            Ext.getCmp(panelPrefix + hidePanels[i]).hide();

        if (Ext.getCmp(panelPrefix + hidePanels[i]) && Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt && Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt.xtype == 'tabpanel')
            Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt.remove(Ext.getCmp(panelPrefix + hidePanels[i]));

    }
    var removeList = [];
    panel.cascade(function(p) {
        if (p.itemId){
          if(hidePanels.indexOf(p.itemId) >= 0 || hidePanels.indexOf(panelPrefix + p.itemId) >= 0){
            if (p.hide)
              p.hide();
      
            if (p.ownerCt && p.ownerCt.xtype == 'tabpanel')
              removeList[removeList.length] = p;
          }
        }
    }, this);
    for (var i = 0; i < removeList.length; i++){
      removeList[i].ownerCt.remove(removeList[i]);
    }
  }
  /**
   * Apply non-editable rules to the fields on the specified panel
   *
   * @param panel Panel on which to find the fields to hide
   * @param nonEditList Comma seperated list of fields names that should be non-editable
   *
   * @DEPRECATES Jaffa.DWRService.applyNonEditableRules
   */
  ,setNonEditableFields : function(panel, nonEditList) {
    var nonEditFields = nonEditList.split(',');
    var modifiedFields = [];
    panel.cascade(function(f) {
        if (f.mapping) {
            if (nonEditFields.indexOf(f.mapping) >= 0) {
                if (f.xtype == 'finderComboGrid') {
                    if (!f.disabled) {
                        f.disable();
                        modifiedFields.push(f.mapping);
                    }
                }
                else if (f.xtype == 'checkbox'){
                  if (!f.disabled) {
                    if (f.rendered) {
                        f.disable();
                    }
                    else {
                        f.disabled = true;
                    }
                    modifiedFields.push(f.mapping);
                  }
                }
                else {
                  if (!f.textOnly) {
                    if (f.rendered) {
                        f.setTextOnly(true);
                    }
                    else {
                        f.textOnly = true;
                    }
                    modifiedFields.push(f.mapping);
                  }
                }
            }
        }
    }, this);
    return modifiedFields.length>0 ? modifiedFields : null;
  }
  
  ,setEditableFields: function(panel, editList) {
    var efs = editList;
    if (! Ext.isArray(efs)) {
      efs = efs.split(',');
    }
    panel.cascade(function(f) {
      if (f.mapping) {
        if (efs.indexOf(f.mapping) >= 0) {
          if (f.xtype == 'finderComboGrid') {
            f.enable();
          } else if (f.type == 'checkbox') {
            if (f.rendered) {
              f.enable();
            } else {
              f.disabled = false;
            }
          } else if (f.textOnly) {
            if (f.rendered) {
              f.setTextOnly(false);
            } else {
              f.textOnly = false;
            }
          }
        }
      }
    });
  }
  
  /**
   * Apply mandatory rules to the fields on the specified panel
   *
   * @param panel Panel on which to find the fields to make mandatory
   * @param mandatoryList Comma seperated list of fields names that should be mandatory
   *
   * @DEPRECATES Jaffa.DWRService.applyMandatoryRules
   */
  ,setMandatoryFields : function(panel, mandatoryList) {
    var mandatoryFields = mandatoryList.split(',');
    panel.cascade(function(f) {
        if (f.mapping) {
            if (mandatoryFields.indexOf(f.mapping) >= 0) 
                f.allowBlank = false;
        }
    }, this);
  }



  /**
   * Clear all fields on the specified panel
   *
   * @DEPRECATES Jaffa.DWRService.clearPanelFields
   */
  ,clearPanelFields : function(panel, excludedFields) {
    var tmp = (excludedFields || []).toString() || '';
    panel.cascade(function(f) {
      //If panel/field has custom clearPanel method then call it and stop cascade on that branch
      if (f.clearPanel) {
        f.clearPanel();
        return false;
      }
      else {
        if (f.mapping != null && f.mapping != '' &&
        (!tmp || tmp == '' || !(tmp == f.mapping || tmp.search(',' + f.mapping) >= 0 || tmp.search(f.mapping + ',') >= 0))) {
          if (f.xtype == 'checkbox' || f.xtype == 'radio')
            f.originalValue = false;
          else
            f.originalValue = '';
          f.reset();
         // if (f.isXType('finderCombo')) f.recreateStore(f.store.originalBaseParams);
        }
      }
    }, this);
  }
  
   /**
   * Stamps the dirty read check rule, and the lastChangeOn values on the new data object
   * from the values on the old data object, so when this new data object is passed to a
   * service that implements these checks, they will be performed.
   *
   * To enforce dirty-read check for a Graph, pass performDirtyReadCheck=true and the last
   * known value for the field used for that check.
   * 
   * @TODO: Use ClassMetaData to determine the field used in a dirty-read check
   *        For now use the hard-coded fieldname "lastChangedOn"
   *
   * @DEPRECATES Jaffa.DWRService.StampDirtyReadCheck
   */
  ,stampDirtyReadCheck : function(newData, oldData, mapping) {
    var prefix = mapping || '', lastChangedOn=null;
    if (oldData) {
      var i=1;
      while (lastChangedOn==null && i>=0) {
        i = prefix.lastIndexOf('.');
        prefix = i <= 0 || i==(prefix.length-1)? '' : prefix.substring(0, i);
        if (prefix=='' || Jaffa.data.Util.get(oldData, prefix)) {
          prefix = prefix=='' ? prefix : prefix+'.';
          lastChangedOn = Jaffa.data.Util.get(oldData, prefix + "lastChangedOn");
        } else {
          // mapping resides in a new graph.
          prefix = prefix=='' ? prefix : prefix+'.';
          break;
        }
      }
    } else {
      prefix = prefix=='' ? prefix : prefix+'.';
    }
    Jaffa.data.Util.set(newData, prefix+'performDirtyReadCheck', true);
    Jaffa.data.Util.set(newData, prefix+'lastChangedOn', lastChangedOn);
  }


  /**
   * Update a Jaffa.data.Record from the fields in a Ext.Panel by matching mappings on panel to the name on record. 
   *
   * This can be used when using a pop-up modal panel to edit the contents of Record to store the 
   * changes back on the record
   * 
   * Binding rule: mapping of the panel fields binds to the name of the record fields.
   *
   * @param {Jaffa.data.Record} rec 
   * @param {Ext.Panel} panel the  
   *
   * @DEPRECATES Jaffa.DWRService.updateStoreRecordFromPanel
   */
  ,updateRecordFromPanel : function(rec, panel) {
    var recP = rec;
    if (rec && panel && panel.xtype!='radiogroup' && !(panel instanceof Ext.form.Field)) {
      // console.debug("Loading Panel id=",panel.id," from data=",data);
      this.applyPanelFieldsMetaRules(panel, rec.data);
      panel.items.each(function(f) {
          if (f.mapping != null && f.mapping!='') {
            var i = recP.fields.findIndexBy(function(recF) {
              if (recF.name==f.mapping) return true;
              else return false;
            });
            if (i >= 0) {
              var origVal = recP.data[recP.fields.get(i).name];
              var newVal = f.getValue();
              if (f.xtype=='radiogroup') {
                newVal =  f.getValue() && f.getValue().inputValue;
              } else if (f.xtype == 'checkboxgroup') {
                var ckvs = f.getValue();
                for (var j=0; j<ckvs.length; j++) {
                  if (j==0) newVal = ckvs[i].inputValue;
                  newVal += ','+ckvs[i].inputValue;
                }
              }
              if (!(origVal == null && newVal == '')) {  //prevents marking cell as dirty when field goes from null to empty
                recP.set(recP.fields.get(i).name, newVal);
              }
            }
          }
          
          // Get Field Description Value
          if (f.descriptionMapping != null /*& !f.disabled*/) {
            var i = recP.fields.findIndex(function(recF) {
              if (recF.name == f.descriptionMapping) return true;
              else return false;
            });
            if (i >= 0) {
              recP.set(recP.fields.get(i).name, f.fieldDescription);
            }
          }
          
          // Recursive call if this item has nested item
          if (f.items && f.xtype != 'radiogroup' && f.xtype != 'checkboxgroup' && !(f instanceof Ext.form.Field) && f.xtype != 'griddetailcontainer' && !(f instanceof Jaffa.maintenance.GridDetailContainer)) {
              this.updateRecordFromPanel(recP, f);
          }
      }, this);
    }
  }


  /**
   * Check the following rules for all the fields on the specified panel
   * - mandatory fields
   * - min/max field lengths
   * - valid forigen keys (for FinderComboGrid fields)
   *
   * @param panel Panel on which to find the fields to make mandatory
   * @return String Returns a null string if all validation pass, or
   *         a HTML string containing the errors to be displayed
   *
   * @DEPRECATES Jaffa.DWRService.checkRules
   * @TODO: tab is not focused on this panel if it returns an error!
   */
  ,validatePanelFields : function(panel) {
    var failedCheck = "";
    var dateCheck = "";
    var mandatoryCheck = "";
    var lengthCheck = "";
    var comboCheck = "";
    panel.cascade(function(f) {
        if (f.textOnly == true) return;
        if(f instanceof Ext.Panel && (f.fireEvent("beforevalidatedata", f) === false)){
          return false;
        }
        if (f.allowBlank == false && !f.hidden && !f.disabled) {
            if (!f.textOnly && (f.getValue() == null || (f.getValue() == ""&&f.getValue() != '0'))) {
                if (mandatoryCheck == "") 
                    mandatoryCheck = this.invalidMandatoryText + f.fieldLabel;
                else 
                    mandatoryCheck = mandatoryCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (f.minLength>=0 && !f.textOnly && f.getValue()) {
            if ((typeof f.getValue().getByteLen === 'function') ? f.getValue().getByteLen()<f.minLength : f.getValue().length < f.minLength) {
                if (lengthCheck == "") 
                    lengthCheck = this.invalidMinLenText + f.fieldLabel;
                else 
                    lengthCheck = lengthCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (f.maxLength>=0 && !f.textOnly && f.getValue()) {
            if ((typeof f.getValue().getByteLen === 'function') ? f.getValue().getByteLen()>f.maxLength : f.getValue().length > f.maxLength) { // getByteLen() implement UTF-8 string length calculation 
                if (lengthCheck == "") 
                    lengthCheck = this.invalidMaxLenText + f.fieldLabel;
                else 
                    lengthCheck = lengthCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (f.xtype && f.xtype.toLowerCase().indexOf('findercomb')>=0 && !f.isValueValid && f.allowInvalid != true) {
            if (comboCheck == "") 
                comboCheck = this.invalidComboValueText + f.fieldLabel;
            else 
                comboCheck = comboCheck + ", " + f.fieldLabel;
            return false;
        }
        if (f.xtype && (f.xtype.toLowerCase().indexOf('datefield')>=0 || f.xtype.toLowerCase()=='xdatetime') && !f.isValid()) {
          if (dateCheck == "")
              dateCheck = this.invalidDateValueText + f.fieldLabel;
          else
              dateCheck = dateCheck + ", " + f.fieldLabel;
          return false;
        }
    }, this);
    if (mandatoryCheck!="" && lengthCheck!=""){
        failedCheck = mandatoryCheck + "<br>" + lengthCheck;
    }else{
        if (mandatoryCheck!="")
            failedCheck = mandatoryCheck;
        else if (lengthCheck!="")            
            failedCheck = lengthCheck;
    }
    if (comboCheck!=''){
        if (failedCheck!=''){
            failedCheck = failedCheck + "<br>" + comboCheck;            
        }else{
            failedCheck = comboCheck;
        }
    }
    if (dateCheck!=''){
        if (failedCheck!=''){
            failedCheck = failedCheck + "<br>" + dateCheck;
        }else{
            failedCheck = dateCheck;
        }
    }

    return failedCheck;
  }


  /**
   * Validate each grid cell. A message will be displayed for the first invalid cell.
   * After the user close the error message pop-up window, the cell with the error
   * will be given focus.
   * <p>
   * <br/><b>Note</b> The recognition of invalid cell is based on it the css class of the cell's &lt;td&gt; tag
   * containing the the word 'invalid'.
   * <p>
   *
   * @param {Object} grid
   * @return {boolean} Returns true if the grid passes validation, false if an error was displayed
   *
   * @DEPENDS Lables.get() - This relies on the global Labels object for message resources,
   * it specifically uses
   * <ul style="list-style-position: inside; margin-left: 12px; list-style-type: disc;">
   * <li>label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error
   * <li>label.jaffa.jaffaRIA.jaffa.DWRService.Grid.Validation.ErrorMessage
   * </ul>
   *
   * @DEPRECATES Jaffa.DWRService.checkGridCellValidation
   * @TODO Cell does not go into edit mode on error, also may not work if tab is not focused on first
   */
  ,validateGrid : function(grid) {
    if (grid.rendered) {
      var colModel = grid.getColumnModel();
      for (var row = 0; row < grid.getStore().getCount(); row++) {
        for (var col = 0; col <  colModel.config.length; col++) {
          var column = colModel.getColumnById(colModel.getColumnId(col));
          if(column.editable || column.editor){
            var msg = '';
            //Validates if mandatory column has a value by checking the record data.
            if(column.mandatory && column.dataIndex ){
              var rec = grid.getStore().getAt(row);
              if(rec && Ext.isEmpty(rec.get(column.dataIndex))){
                msg = new Ext.Template(this.mandatoryColErrorMsgTpl).apply([column.header]);
              }
            }
            if(!msg){
              var cell  = grid.getView().getCell(row, col);
              if(cell && cell.className.indexOf('invalid') >= 0){
                if (column.validateErrorMsgTpl) {
                  if (typeof column.validateErrorMsgTpl == 'string') column.validateErrorMsgTpl = new Ext.Template(column.validateErrorMsgTpl);
                  msg = column.validateErrorMsgTpl.apply([column.header]);
                } else {
                  msg = new Ext.Template(this.validateErrorMsgTpl).apply([column.header]);
                }
              }
            }
            if(msg){
              var title = this.validateErrorTitleText
              if(grid.title){
                title +=  ' (' + grid.title + ')';
              }
              Ext.MessageBox.alert(title, msg, function(btn){
                this.grid.getView().focusCell(this.row, this.col);
              }, {
                grid: grid,
                row: row,
                col: col
              });
              return false;
            }
          }
        }
      }
      if(grid.editing && grid.activeEditor && grid.activeEditor.field && grid.activeEditor.field.getEl() && grid.activeEditor.field.getEl().hasClass(grid.activeEditor.field.invalidClass)){
        Ext.MessageBox.alert(this.validateErrorTitleText, this.gridEditorValidationError);
        return false;
      }
    }
    return true;
  }


  /**
   * @DEPRECATED the key fields in a record is defined by the metaClass on the record. 
   * keyField on a store no longer valid. 
   * 
   * Validates all the Jaffa.data.Record based objects in an Ext.data.Store based on the
   * meta data rules of the supplied class.
   *
   * This does the following<br/>
   * - It validates the mandatory fields directly in the Record<br/>
   * - Initializes following Store properties - keyField,natureKeyField,kmapping,nkmapping<br/>
   *
   * @param {Object} store a Ext.data.Store object
   * @param {Object} metaClass defines the aop rules to the fields of the records in the class
   *
   * @DEPENDS Lables.get() - This relies on the global Labels object for message resources,
   * it specifically uses
   * <ul style="list-style-position: inside; margin-left: 12px; list-style-type: disc;">
   * <li>label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error
   * </ul>
   *
   * @DEPRECATES Jaffa.DWRService.checkRulesOnRecords
   */
  ,validateStoreAndPanels : function(store, metaClass) {
    // @TODO: The following code needs review
    // Validation cannot be performed if fields are not defined on the Store.
    // This can happen in a tree store when a Record is not provided during construction of the associated reader
    if (!store.fields)
      return true;
    
    // @FIXME: Move the following key-check to the constructor of the GridPanel or the validateGrid() method
    var failedCheck = "";
    var keyCheck = '';
    
    /*
    // step 1. make an array of mandatory field names from metaClass
    var mandatoryFields = new Array(), idx, f;
    for (var i in metaClass.fields) {
      if (metaClass.fields[i].mandatory == true) {
        idx = store.fields.findIndex('mapping', i);
        if (idx >= 0) {
          f = store.fields.get(idx);
          if (! f.notEditable) mandatoryFields[mandatoryFields.length] = f.name;
        }
      }
    }
    // step 2. check each record of their mandatory fields
    store.each(function(rec) {
      for (var i=0; i<mandatoryFields.length; i++) {
        f = rec.get(mandatoryFields[i]);
        if (f == null || f == '') {
          mandatoryCheck = mandatoryFields[i]+' is mandatory.';
          return false;
        }
      }
    }, store);
    */

    // check key and natureKey in the store records.
    if (store.keyField==null || store.natureKeyField==null || store.kmapping==null || store.nkmapping==null) {
      store.fields.each(function(f) {
        if (f.key == true) {
          this.keyField = f.name;
          this.kmapping = f.mapping ? f.mapping : f.name;
        }
        if (f.natureKey == true) {
          this.natureKeyField = f.name;
          this.nkmapping = f.mapping ? f.mapping : f.name;
        }
        if (this.keyField && this.natureKeyField) return false;
      }, store);
    }
    if (store.keyField==null || store.kmapping==null) {
      keyCheck = this.keyCheckAlertMsgText;
    }
    
    if (keyCheck != "") {
      if (failedCheck == '') {
        failedCheck = keyCheck;
      } else {
        failedCheck = failedCheck + "<br>" + keyCheck;
      }
    }    
    
    // display the error message
    if (failedCheck != '') {
      Ext.MessageBox.alert(this.validationErrorTitleText, failedCheck);
      return false;
    }
    return true;
  }

  
  /**
   * @private - For internal use
   *
   * Update the key fields if they are null or empty in newData.
   * Process abort if  either oldData or metaClass is null
   */
  ,_setKeyFields : function(newData, oldData, metaClass) {
    // @TODO: support composite keys
    if (metaClass && oldData && metaClass.key) 
      if(Ext.isArray(metaClass.key)) {
         for(var k=0; k<metaClass.key.length; k++) {
           Jaffa.data.Util.set(newData, metaClass.key[k], Jaffa.data.Util.get(oldData, metaClass.key[k]));
         }
      } else
         Jaffa.data.Util.set(newData, metaClass.key, Jaffa.data.Util.get(oldData, metaClass.key));
  }

  
  /**
   * @private - For internal use
   *
   * Update the key field and the mandatory fields if they are null or empty in newData.
   * Process abort if  either oldData or metaClass is null
   *
   *
   * @DEPRECATES Jaffa.DWRService.updateMandatoryFields
   */
  ,_updateMandatoryFields : function(newData, oldData, metaClass) {
    // @FIXME: This method should only update the key fields
    if (metaClass && oldData) {
      // update mandatory fields
      var v;
      for (var i in metaClass.fields) {
        if (metaClass.fields[i].mandatory == true) {
          v = Jaffa.data.Util.get(newData, i);
          if (! v || v=='') {
            Jaffa.data.Util.set(newData, i, Jaffa.data.Util.get(oldData, i));
          }
        }
      }
      // update key fields
      if (metaClass.key) {
        Jaffa.data.Util.set(newData, metaClass.key, Jaffa.data.Util.get(oldData, metaClass.key));
      }
    }
  }
  
});

function cleanInvalidChar(value) {
    // To simplify the regex, we can add only our customers language codes
    // example: regex for non ascii nor Arabic
    // var nonAsciiNorArabicRegex = /[^\x00-\xFF\u0600-\u06FF]/g;

    //regex for non ascii nor any language
    var nonAsciiNorAnyLangRegex = /[^\x00-\xFF\u0041-\u005A\u0061-\u007A\u00AA\u00B5\u00BA\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02C1\u02C6-\u02D1\u02E0-\u02E4\u02EC\u02EE\u0370-\u0374\u0376\u0377\u037A-\u037D\u0386\u0388-\u038A\u038C\u038E-\u03A1\u03A3-\u03F5\u03F7-\u0481\u048A-\u0527\u0531-\u0556\u0559\u0561-\u0587\u05D0-\u05EA\u05F0-\u05F2\u0620-\u064A\u066E\u066F\u0671-\u06D3\u06D5\u06E5\u06E6\u06EE\u06EF\u06FA-\u06FC\u06FF\u0710\u0712-\u072F\u074D-\u07A5\u07B1\u07CA-\u07EA\u07F4\u07F5\u07FA\u0800-\u0815\u081A\u0824\u0828\u0840-\u0858\u08A0\u08A2-\u08AC\u0904-\u0939\u093D\u0950\u0958-\u0961\u0971-\u0977\u0979-\u097F\u0985-\u098C\u098F\u0990\u0993-\u09A8\u09AA-\u09B0\u09B2\u09B6-\u09B9\u09BD\u09CE\u09DC\u09DD\u09DF-\u09E1\u09F0\u09F1\u0A05-\u0A0A\u0A0F\u0A10\u0A13-\u0A28\u0A2A-\u0A30\u0A32\u0A33\u0A35\u0A36\u0A38\u0A39\u0A59-\u0A5C\u0A5E\u0A72-\u0A74\u0A85-\u0A8D\u0A8F-\u0A91\u0A93-\u0AA8\u0AAA-\u0AB0\u0AB2\u0AB3\u0AB5-\u0AB9\u0ABD\u0AD0\u0AE0\u0AE1\u0B05-\u0B0C\u0B0F\u0B10\u0B13-\u0B28\u0B2A-\u0B30\u0B32\u0B33\u0B35-\u0B39\u0B3D\u0B5C\u0B5D\u0B5F-\u0B61\u0B71\u0B83\u0B85-\u0B8A\u0B8E-\u0B90\u0B92-\u0B95\u0B99\u0B9A\u0B9C\u0B9E\u0B9F\u0BA3\u0BA4\u0BA8-\u0BAA\u0BAE-\u0BB9\u0BD0\u0C05-\u0C0C\u0C0E-\u0C10\u0C12-\u0C28\u0C2A-\u0C33\u0C35-\u0C39\u0C3D\u0C58\u0C59\u0C60\u0C61\u0C85-\u0C8C\u0C8E-\u0C90\u0C92-\u0CA8\u0CAA-\u0CB3\u0CB5-\u0CB9\u0CBD\u0CDE\u0CE0\u0CE1\u0CF1\u0CF2\u0D05-\u0D0C\u0D0E-\u0D10\u0D12-\u0D3A\u0D3D\u0D4E\u0D60\u0D61\u0D7A-\u0D7F\u0D85-\u0D96\u0D9A-\u0DB1\u0DB3-\u0DBB\u0DBD\u0DC0-\u0DC6\u0E01-\u0E30\u0E32\u0E33\u0E40-\u0E46\u0E81\u0E82\u0E84\u0E87\u0E88\u0E8A\u0E8D\u0E94-\u0E97\u0E99-\u0E9F\u0EA1-\u0EA3\u0EA5\u0EA7\u0EAA\u0EAB\u0EAD-\u0EB0\u0EB2\u0EB3\u0EBD\u0EC0-\u0EC4\u0EC6\u0EDC-\u0EDF\u0F00\u0F40-\u0F47\u0F49-\u0F6C\u0F88-\u0F8C\u1000-\u102A\u103F\u1050-\u1055\u105A-\u105D\u1061\u1065\u1066\u106E-\u1070\u1075-\u1081\u108E\u10A0-\u10C5\u10C7\u10CD\u10D0-\u10FA\u10FC-\u1248\u124A-\u124D\u1250-\u1256\u1258\u125A-\u125D\u1260-\u1288\u128A-\u128D\u1290-\u12B0\u12B2-\u12B5\u12B8-\u12BE\u12C0\u12C2-\u12C5\u12C8-\u12D6\u12D8-\u1310\u1312-\u1315\u1318-\u135A\u1380-\u138F\u13A0-\u13F4\u1401-\u166C\u166F-\u167F\u1681-\u169A\u16A0-\u16EA\u1700-\u170C\u170E-\u1711\u1720-\u1731\u1740-\u1751\u1760-\u176C\u176E-\u1770\u1780-\u17B3\u17D7\u17DC\u1820-\u1877\u1880-\u18A8\u18AA\u18B0-\u18F5\u1900-\u191C\u1950-\u196D\u1970-\u1974\u1980-\u19AB\u19C1-\u19C7\u1A00-\u1A16\u1A20-\u1A54\u1AA7\u1B05-\u1B33\u1B45-\u1B4B\u1B83-\u1BA0\u1BAE\u1BAF\u1BBA-\u1BE5\u1C00-\u1C23\u1C4D-\u1C4F\u1C5A-\u1C7D\u1CE9-\u1CEC\u1CEE-\u1CF1\u1CF5\u1CF6\u1D00-\u1DBF\u1E00-\u1F15\u1F18-\u1F1D\u1F20-\u1F45\u1F48-\u1F4D\u1F50-\u1F57\u1F59\u1F5B\u1F5D\u1F5F-\u1F7D\u1F80-\u1FB4\u1FB6-\u1FBC\u1FBE\u1FC2-\u1FC4\u1FC6-\u1FCC\u1FD0-\u1FD3\u1FD6-\u1FDB\u1FE0-\u1FEC\u1FF2-\u1FF4\u1FF6-\u1FFC\u2071\u207F\u2090-\u209C\u2102\u2107\u210A-\u2113\u2115\u2119-\u211D\u2124\u2126\u2128\u212A-\u212D\u212F-\u2139\u213C-\u213F\u2145-\u2149\u214E\u2183\u2184\u2C00-\u2C2E\u2C30-\u2C5E\u2C60-\u2CE4\u2CEB-\u2CEE\u2CF2\u2CF3\u2D00-\u2D25\u2D27\u2D2D\u2D30-\u2D67\u2D6F\u2D80-\u2D96\u2DA0-\u2DA6\u2DA8-\u2DAE\u2DB0-\u2DB6\u2DB8-\u2DBE\u2DC0-\u2DC6\u2DC8-\u2DCE\u2DD0-\u2DD6\u2DD8-\u2DDE\u2E2F\u3005\u3006\u3031-\u3035\u303B\u303C\u3041-\u3096\u309D-\u309F\u30A1-\u30FA\u30FC-\u30FF\u3105-\u312D\u3131-\u318E\u31A0-\u31BA\u31F0-\u31FF\u3400-\u4DB5\u4E00-\u9FCC\uA000-\uA48C\uA4D0-\uA4FD\uA500-\uA60C\uA610-\uA61F\uA62A\uA62B\uA640-\uA66E\uA67F-\uA697\uA6A0-\uA6E5\uA717-\uA71F\uA722-\uA788\uA78B-\uA78E\uA790-\uA793\uA7A0-\uA7AA\uA7F8-\uA801\uA803-\uA805\uA807-\uA80A\uA80C-\uA822\uA840-\uA873\uA882-\uA8B3\uA8F2-\uA8F7\uA8FB\uA90A-\uA925\uA930-\uA946\uA960-\uA97C\uA984-\uA9B2\uA9CF\uAA00-\uAA28\uAA40-\uAA42\uAA44-\uAA4B\uAA60-\uAA76\uAA7A\uAA80-\uAAAF\uAAB1\uAAB5\uAAB6\uAAB9-\uAABD\uAAC0\uAAC2\uAADB-\uAADD\uAAE0-\uAAEA\uAAF2-\uAAF4\uAB01-\uAB06\uAB09-\uAB0E\uAB11-\uAB16\uAB20-\uAB26\uAB28-\uAB2E\uABC0-\uABE2\uAC00-\uD7A3\uD7B0-\uD7C6\uD7CB-\uD7FB\uF900-\uFA6D\uFA70-\uFAD9\uFB00-\uFB06\uFB13-\uFB17\uFB1D\uFB1F-\uFB28\uFB2A-\uFB36\uFB38-\uFB3C\uFB3E\uFB40\uFB41\uFB43\uFB44\uFB46-\uFBB1\uFBD3-\uFD3D\uFD50-\uFD8F\uFD92-\uFDC7\uFDF0-\uFDFB\uFE70-\uFE74\uFE76-\uFEFC\uFF21-\uFF3A\uFF41-\uFF5A\uFF66-\uFFBE\uFFC2-\uFFC7\uFFCA-\uFFCF\uFFD2-\uFFD7\uFFDA-\uFFDC]/g;
    value = value.replace(nonAsciiNorAnyLangRegex, "");
    return value;
}
