/**
 * This class shouldn't be created directly use NestedSetStore or AdjacencyListStore instead.
 *
 * @abstract
 */
Ext.ux.maximgb.tg.AbstractTreeStore = Ext.extend(Ext.data.Store, {
  /**
   * @cfg {String} is_leaf_field_name Record leaf flag field name.
   */
  leaf_field_name: '_is_leaf',
  
  /**
   * Current page offset.
   *
   * @access private
   */
  page_offset: 0,
  
  /**
   * Current active node.
   *
   * @access private
   */
  active_node: null,
  
  /**
   * @constructor
   */
  constructor: function(config){
    Ext.ux.maximgb.tg.AbstractTreeStore.superclass.constructor.call(this, config);
    
    if (!this.paramNames.active_node) {
      this.paramNames.active_node = 'anode';
    }
    
    this.addEvents(    /**
     * @event beforeexpandnode
     * Fires before node expand. Return false to cancel operation.
     * param {AbstractTreeStore} this
     * param {Record} record
     */
    'beforeexpandnode',    /**
     * @event expandnode
     * Fires after node expand.
     * param {AbstractTreeStore} this
     * param {Record} record
     */
    'expandnode',    /**
     * @event expandnodefailed
     * Fires when expand node operation is failed.
     * param {AbstractTreeStore} this
     * param {id} Record id
     * param {Record} Record, may be undefined
     */
    'expandnodefailed',    /**
     * @event beforecollapsenode
     * Fires before node collapse. Return false to cancel operation.
     * param {AbstractTreeStore} this
     * param {Record} record
     */
    'beforecollapsenode',    /**
     * @event collapsenode
     * Fires after node collapse.
     * param {AbstractTreeStore} this
     * param {Record} record
     */
    'collapsenode',    /**
     * @event beforeactivenodechange
     * Fires before active node change. Return false to cancel operation.
     * param {AbstractTreeStore} this
     * param {Record} old active node record
     * param {Record} new active node record
     */
    'beforeactivenodechange',    /**
     * @event activenodechange
     * Fires after active node change.
     * param {AbstractTreeStore} this
     * param {Record} old active node record
     * param {Record} new active node record
     */
    'activenodechange');
  },
  
  // Store methods.
  // -----------------------------------------------------------------------------------------------  
  /**
   * Removes record and all its descendants.
   *
   * @access public
   * @param {Record} record Record to remove.
   */
  remove: function(record){
    // ----- Modification start
    if (record === this.active_node) {
      this.setActiveNode(null);
    }
    this.removeNodeDescendants(record);
    // ----- End of modification        
    Ext.ux.maximgb.tg.AbstractTreeStore.superclass.remove.call(this, record);
  },
  
  /**
   * Removes node descendants.
   *
   * @access private
   */
  removeNodeDescendants: function(rc){
    var i, len, children = this.getNodeChildren(rc);
    for (i = 0, len = children.length; i < len; i++) {
      this.remove(children[i]);
    }
  },
  
  /**
   * Removes the record and it's descendants from the store.
   * Additionally, if this record is not new, then it will be added to the '_deletedRecords' property of the parent record
   * NOTE: A new record will however be removed from the store.
   * @access public
   * @param {Record} record Record to mark for deletion.
   * @return {boolean} true if the input record is an existing record, indicating that a Save will need to be performed to perform the actual deletion.
   */
  deleteRecord: function(record){
    var needsSave = !this.isNewNode(record);
    if (needsSave) {
      var parentRecord = this.getNodeParent(record);
      if (parentRecord) {
        if (!parentRecord._deletedRecords) 
          parentRecord._deletedRecords = [];
        parentRecord._deletedRecords.push(record);
      }
    }
    this.remove(record);
    return needsSave;
  },
  /**
   * Loads current active record data.
   */
  load: function(options){
    if (options) {
      if (options.params) {
        if (options.params[this.paramNames.active_node] === undefined) {
          options.params[this.paramNames.active_node] = this.active_node ? this.active_node.id : null;
        }
      }
      else {
        options.params = {};
        options.params[this.paramNames.active_node] = this.active_node ? this.active_node.id : null;
      }
    }
    else {
      options = {
        params: {}
      };
      options.params[this.paramNames.active_node] = this.active_node ? this.active_node.id : null;
    }
    
    if (options.params[this.paramNames.active_node] !== null) {
      options.add = true;
    }
    
    return Ext.ux.maximgb.tg.AbstractTreeStore.superclass.load.call(this, options);
  },
  
  /**
   * Called as a callback by the Reader during load operation.
   *
   * @access private
   */
  loadRecords: function(o, options, success){
    if (!o || success === false) {
      if (success !== false) {
        this.fireEvent("load", this, [], options);
      }
      if (options.callback) {
        options.callback.call(options.scope || this, [], options, false);
      }
      return;
    }
    
    var r = o.records, t = o.totalRecords || r.length, page_offset = this.getPageOffsetFromOptions(options), loaded_node_id = this.getLoadedNodeIdFromOptions(options), loaded_node, i, len, self = this;
    
    if (!options || options.add !== true/* || loaded_node_id === null*/) {
      if (this.pruneModifiedRecords) {
        this.modified = [];
      }
      for (var i = 0, len = r.length; i < len; i++) {
        r[i].join(this);
      }
      if (this.snapshot) {
        this.data = this.snapshot;
        delete this.snapshot;
      }
      this.data.clear();
      this.data.addAll(r);
      this.page_offset = page_offset;
      this.totalLength = t;
      this.applySort();
      this.fireEvent("datachanged", this);
    }
    else {
      if (loaded_node_id) {
        loaded_node = this.getById(loaded_node_id);
      }
      if (loaded_node) {
        this.setNodeLoaded(loaded_node, true);
        this.setNodeChildrenOffset(loaded_node, page_offset);
        this.setNodeChildrenTotalCount(loaded_node, Math.max(t, r.length));
        this.removeNodeDescendants(loaded_node);
      }
      this.suspendEvents();
      updated = null;
      for (i = 0, len = r.length; i < len; i++) {
        record = r[i];
        idx = this.indexOfId(record.id);
        if (idx == -1) {
          this.add(record);
        }
        else {
          if (!updated)
            updated = {};
          updated[record.id] = true;
          prev_record = this.getAt(idx);
          prev_record.reject();
          prev_record.data = record.data;
          r[i] = prev_record;
        }
      }
      this.applySort();
      this.resumeEvents();
      
      r.sort(function(r1, r2){
        var idx1 = self.data.indexOf(r1), idx2 = self.data.indexOf(r2), result;
        
        if (idx1 > idx2) {
          result = 1;
        }
        else {
          result = -1;
        }
        return result;
      });
      
      if (updated) {
        for (i = 0, len = r.length; i < len; i++) {
          record = r[i];
          if (updated[record.id] == true) {
            this.fireEvent('update', this, record, Ext.data.Record.COMMIT);
          }
          else {
            this.fireEvent("add", this, [record], this.data.indexOf(record));
          }
        }
      } else if(r.length>0) {
        this.fireEvent("add", this, r, this.data.indexOf(r[0]));
      }
    }
    this.fireEvent("load", this, r, options);
    if (options.callback) {
      options.callback.call(options.scope || this, r, options, true);
    }
  },
  
  /**
   * Sort the Records.
   *
   * @access public
   */
  sort: function(fieldName, dir){
    if (this.remoteSort) {
      this.setActiveNode(null);
      if (this.lastOptions) {
        this.lastOptions.add = false;
        if (this.lastOptions.params) {
          this.lastOptions.params[this.paramNames.active_node] = null;
        }
      }
    }
    
    return Ext.ux.maximgb.tg.AbstractTreeStore.superclass.sort.call(this, fieldName, dir);
  },
  
  /**
   * Applyes current sort method.
   *
   * @access private
   */
  applySort: function(){
    if (this.sortInfo && !this.remoteSort) {
      var s = this.sortInfo, f = s.field;
      this.sortData(f, s.direction);
    }
    // ----- Modification start
    else {
      this.applyTreeSort();
    }
    // ----- End of modification
  },
  
  /**
   * Sorts data according to sort params and then applyes tree sorting.
   *
   * @access private
   */
  sortData: function(f, direction){
    direction = direction || 'ASC';
    var st = this.fields.get(f).sortType;
    var fn = function(r1, r2){
      var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
      return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
    };
    this.data.sort(direction, fn);
    if (this.snapshot && this.snapshot != this.data) {
      this.snapshot.sort(direction, fn);
    }
    // ----- Modification start
    this.applyTreeSort();
    // ----- End of modification
  },
  
  // Tree support methods.
  // -----------------------------------------------------------------------------------------------
  
  /**
   * Sorts store data with respect to nodes parent-child relation. Every child node will be
   * positioned after its parent.
   *
   * @access public
   */
  applyTreeSort: function(){
    var i, len, temp, rec, records = [], roots = this.getRootNodes();
    
    // Sorting data
    for (i = 0, len = roots.length; i < len; i++) {
      rec = roots[i];
      records.push(rec);
      this.collectNodeChildrenTreeSorted(records, rec);
    }
    
    if (records.length > 0) {
      this.data.clear();
      this.data.addAll(records);
    }
    
    // Sorting the snapshot if one present.
    if (this.snapshot && this.snapshot !== this.data) {
      temp = this.data;
      this.data = this.snapshot;
      this.snapshot = null;
      this.applyTreeSort();
      this.snapshot = this.data;
      this.data = temp;
    }
  },
  
  /**
   * Recusively collects rec descendants and adds them to records[] array.
   *
   * @access private
   * @param {Record[]} records
   * @param {Record} rec
   */
  collectNodeChildrenTreeSorted: function(records, rec){
    var i, len, child, children = this.getNodeChildren(rec);
    
    for (i = 0, len = children.length; i < len; i++) {
      child = children[i];
      records.push(child);
      this.collectNodeChildrenTreeSorted(records, child);
    }
  },
  
  /**
   * Returns current active node.
   *
   * @access public
   * @return {Record}
   */
  getActiveNode: function(){
    return this.active_node;
  },
  
  /**
   * Sets active node.
   *
   * @access public
   * @param {Record} rc Record to set active.
   */
  setActiveNode: function(rc){
    if (this.active_node !== rc) {
      if (rc) {
        if (this.data.indexOf(rc) != -1) {
          if (this.fireEvent('beforeactivenodechange', this, this.active_node, rc) !== false) {
            this.active_node = rc;
            this.fireEvent('activenodechange', this, this.active_node, rc);
          }
        }
        else {
          throw "Given record is not from the store.";
        }
      }
      else {
        if (this.fireEvent('beforeactivenodechange', this, this.active_node, rc) !== false) {
          this.active_node = rc;
          this.fireEvent('activenodechange', this, this.active_node, rc);
        }
      }
    }
  },
  
  /**
   * Returns true if node is expanded.
   *
   * @access public
   * @param {Record} rc
   */
  isExpandedNode: function(rc){
    return rc.ux_maximgb_tg_expanded === true;
  },
  
  /**
   * Sets node expanded flag.
   *
   * @access private
   */
  setNodeExpanded: function(rc, value){
    rc.ux_maximgb_tg_expanded = value;
  },
  
  /**
   * Returns true if node's ancestors are all expanded - node is visible.
   *
   * @access public
   * @param {Record} rc
   */
  isVisibleNode: function(rc){
    var i, len, ancestors = this.getNodeAncestors(rc), result = true;
    
    for (i = 0, len = ancestors.length; i < len; i++) {
      result = result && this.isExpandedNode(ancestors[i]);
      if (!result) {
        break;
      }
    }
    
    return result;
  },
  
  /**
   * Returns true if node is a leaf.
   *
   * @access public
   * @return {Boolean}
   */
  isLeafNode: function(rc){
    return rc.data[this.leaf_field_name] == true;
  },
  
  /**
   * Returns true if node was loaded.
   *
   * @access public
   * @return {Boolean}
   */
  isLoadedNode: function(rc){
    var result;
    
    if (rc.ux_maximgb_tg_loaded !== undefined) {
      result = rc.ux_maximgb_tg_loaded;
    }
    else 
      if (this.isLeafNode(rc) || this.hasChildNodes(rc)) {
        result = true;
      }
      else {
        result = false;
      }
    
    return result;
  },
  
  /**
   * Returns true if node is created new (assumes no rec.json object).
   *
   * @access public
   * @param {Object} rc
   * @return {Boolean}
   */
  isNewNode: function(rc){
    if (rc.json) {
      return false;
    }
    else {
      return true;
    }
  },
  
  /**
   * Sets node loaded state.
   *
   * @access private
   * @param {Record} rc
   * @param {Boolean} value
   */
  setNodeLoaded: function(rc, value){
    rc.ux_maximgb_tg_loaded = value;
  },
  
  /**
   * Returns node's children offset.
   *
   * @access public
   * @param {Record} rc
   * @return {Integer}
   */
  getNodeChildrenOffset: function(rc){
    return rc.ux_maximgb_tg_offset || 0;
  },
  
  /**
   * Sets node's children offset.
   *
   * @access private
   * @param {Record} rc
   * @parma {Integer} value
   */
  setNodeChildrenOffset: function(rc, value){
    rc.ux_maximgb_tg_offset = value;
  },
  
  /**
   * Returns node's children total count
   *
   * @access public
   * @param {Record} rc
   * @return {Integer}
   */
  getNodeChildrenTotalCount: function(rc){
    return rc.ux_maximgb_tg_total || 0;
  },
  
  /**
   * Sets node's children total count.
   *
   * @access private
   * @param {Record} rc
   * @param {Integer} value
   */
  setNodeChildrenTotalCount: function(rc, value){
    rc.ux_maximgb_tg_total = value;
  },
  
  /**
   * Collapses node.
   *
   * @access public
   * @param {Record} rc
   * @param {Record} rc Node to collapse.
   */
  collapseNode: function(rc){
    if (this.isExpandedNode(rc) &&
    this.fireEvent('beforecollapsenode', this, rc) !== false) {
      this.setNodeExpanded(rc, false);
      this.fireEvent('collapsenode', this, rc);
    }
  },
  
  /**
   * This method determines whether a plus icon should be added on the node in TreeGrid.js
   *
   * It allows an empty folder to maintain _is_leaf=false, while not showing plus icon
   *   if it has been loaded without records retrieved
   *   or has no proxy defined for loading it
   *
   * Here is the logic to add a plus sign:
   *    has children -> true
   *      is labeled as leaf -> false
   *
   * @note justification
   *
   * @param {Object} rc
   */
  isExpandableNode: function(rc){
    if (this.hasChildNodes(rc)) 
      return true;
    else 
      if (this.isLeafNode(rc)) 
        return false;
      else 
        if (rc.ux_maximgb_treegrid_loaded) 
          return false;
        else 
          if (this.proxy) {
            if (this.proxy.canLoad) 
              return this.proxy.canLoad(rc);
            else 
              return true;
          }
          else 
            return false;
  },
  
  /**
   * Expands node.
   *
   * @access public
   * @param {Record} rc
   */
  expandNode: function(rc){
    var params;
    
    if (!this.isExpandedNode(rc) &&
    this.fireEvent('beforeexpandnode', this, rc) !== false) {
      // If node is already loaded then expanding now.
      // modified to allow new nodes to be added
      //if (this.isLoadedNode(rc) || this.isNewNode(rc)) {
      // this.isNewNode() test is taken away because we are allowing to load from multiple remote sources. 
      if (this.isLoadedNode(rc)) {
        this.setNodeExpanded(rc, true);
        this.fireEvent('expandnode', this, rc);
      }
      // If node isn't loaded yet and it is not newly added then expanding after load.
      // if the proxy has canLoad() test, pass that test this is for loading grafted branches.
      else 
        if (this.proxy && (this.proxy.canLoad ? this.proxy.canLoad(rc) : !this.isNewNode(rc))) {
          params = {};
          params[this.paramNames.active_node] = rc.id;
          this.load({
            add: true,
            params: params,
            callback: this.expandNodeCallback,
            scope: this
          });
        }
        else {
          // although the following process is same as node has been loaded, logically 
          // it is differnt condition, which potentially can be treated differently. 
          this.setNodeExpanded(rc, true);
          this.fireEvent('expandnode', this, rc);
        }
    }
  },
  
  /**
   * @access private
   */
  expandNodeCallback: function(r, options, success){
    var rc = this.getById(options.params[this.paramNames.active_node]);
    
    if (success && rc) {
      this.setNodeExpanded(rc, true);
      this.fireEvent('expandnode', this, rc);
    }
    else {
      this.fireEvent('expandnodefailed', this, options.params[this.paramNames.active_node], rc);
    }
  },
  
  /**
   * Expands all nodes.
   *
   * @access public
   */
  expandAll: function(){
    var r, i, len, records = this.data.getRange();
    this.suspendEvents();
    for (i = 0, len = records.length; i < len; i++) {
      r = records[i];
      if (!this.isExpandedNode(r)) {
        this.expandNode(r);
      }
    }
    this.resumeEvents();
    this.fireEvent('datachanged', this);
  },
  
  /**
   * Collapses all nodes.
   *
   * @access public
   */
  collapseAll: function(){
    var r, i, len, records = this.data.getRange();
    
    this.suspendEvents();
    for (i = 0, len = records.length; i < len; i++) {
      r = records[i];
      if (this.isExpandedNode(r)) {
        this.collapseNode(r);
      }
    }
    this.resumeEvents();
    this.fireEvent('datachanged', this);
  },
  
  /**
   * Returns loaded node id from the load options.
   *
   * @access public
   */
  getLoadedNodeIdFromOptions: function(options){
    var result = null;
    if (options && options.params && options.params[this.paramNames.active_node]) {
      result = options.params[this.paramNames.active_node];
    }
    return result;
  },
  
  /**
   * Returns start offset from the load options.
   */
  getPageOffsetFromOptions: function(options){
    var result = 0;
    if (options && options.params && options.params[this.paramNames.start]) {
      result = parseInt(options.params[this.paramNames.start], 10);
      if (isNaN(result)) {
        result = 0;
      }
    }
    return result;
  },
  
  // Public
  hasNextSiblingNode: function(rc){
    return this.getNodeNextSibling(rc) !== null;
  },
  
  // Public
  hasPrevSiblingNode: function(rc){
    return this.getNodePrevSibling(rc) !== null;
  },
  
  // Public
  hasChildNodes: function(rc){
    return this.getNodeChildrenCount(rc) > 0;
  },
  
  // Public
  getNodeAncestors: function(rc){
    var ancestors = [], parent;
    
    parent = this.getNodeParent(rc);
    while (parent) {
      ancestors.push(parent);
      parent = this.getNodeParent(parent);
    }
    
    return ancestors;
  },
  
  // Public
  getNodeChildrenCount: function(rc){
    return this.getNodeChildren(rc).length;
  },
  
  // Public
  getNodeNextSibling: function(rc){
    var siblings, parent, index, result = null;
    
    parent = this.getNodeParent(rc);
    if (parent) {
      siblings = this.getNodeChildren(parent);
    }
    else {
      siblings = this.getRootNodes();
    }
    
    index = siblings.indexOf(rc);
    
    if (index < siblings.length - 1) {
      result = siblings[index + 1];
    }
    
    return result;
  },
  
  // Public
  getNodePrevSibling: function(rc){
    var siblings, parent, index, result = null;
    
    parent = this.getNodeParent(rc);
    if (parent) {
      siblings = this.getNodeChildren(parent);
    }
    else {
      siblings = this.getRootNodes();
    }
    
    index = siblings.indexOf(rc);
    if (index > 0) {
      result = siblings[index - 1];
    }
    
    return result;
  },
  
  // Abstract tree support methods.
  // -----------------------------------------------------------------------------------------------
  
  // Public - Abstract
  getRootNodes: function(){
    throw 'Abstract method call';
  },
  
  // Public - Abstract
  getNodeDepth: function(rc){
    throw 'Abstract method call';
  },
  
  // Public - Abstract
  getNodeParent: function(rc){
    throw 'Abstract method call';
  },
  
  // Public - Abstract
  getNodeChildren: function(rc){
    throw 'Abstract method call';
  },
  
  // Public - Abstract
  addToNode: function(parent, child){
    throw 'Abstract method call';
  },
  
  // Public - Abstract
  removeFromNode: function(parent, child){
    throw 'Abstract method call';
  },
  
  // Paging support methods.
  // -----------------------------------------------------------------------------------------------
  /**
   * Returns top level node page offset.
   *
   * @access public
   * @return {Integer}
   */
  getPageOffset: function(){
    return this.page_offset;
  },
  
  /**
   * Returns active node page offset.
   *
   * @access public
   * @return {Integer}
   */
  getActiveNodePageOffset: function(){
    var result;
    
    if (this.active_node) {
      result = this.getNodeChildrenOffset(this.active_node);
    }
    else {
      result = this.getPageOffset();
    }
    
    return result;
  },
  
  /**
   * Returns active node children count.
   *
   * @access public
   * @return {Integer}
   */
  getActiveNodeCount: function(){
    var result;
    
    if (this.active_node) {
      result = this.getNodeChildrenCount(this.active_node);
    }
    else {
      result = this.getRootNodes().length;
    }
    
    return result;
  },
  
  /**
   * Returns active node total children count.
   *
   * @access public
   * @return {Integer}
   */
  getActiveNodeTotalCount: function(){
    var result;
    
    if (this.active_node) {
      result = this.getNodeChildrenTotalCount(this.active_node);
    }
    else {
      result = this.getTotalCount();
    }
    
    return result;
  }
});

/**
 * Tree store for adjacency list tree representation.
 */
Ext.ux.maximgb.tg.AdjacencyListStore = Ext.extend(Ext.ux.maximgb.tg.AbstractTreeStore, {
  /**
   * @cfg {String} parent_id_field_name Record parent id field name.
   */
  parent_id_field_name: '_parent',
  
  /**
   * @cfg {String} parent_collection_name Holds the collection name of the parent record, to which a given record belongs
   * NOTE: Ensure that the value of this property is in sync with a similarly named property of the tree store.
   */
  parent_collection_name: '_parentCollectionName',
  
  getRootNodes: function(){
    var i, len, result = [], records = this.data.getRange();
    
    for (i = 0, len = records.length; i < len; i++) {
      // modified by Sean.Zhou to allow parent_id equal to its own id for roots
      var pId = records[i].data[this.parent_id_field_name];
      if (pId == null || pId == '' || pId == records[i].id) {
        result.push(records[i]);
      }
    }
    
    return result;
  },
  
  getNodeDepth: function(rc){
    return this.getNodeAncestors(rc).length;
  },
  
  getNodeParent: function(rc){
    // Modified by Sean.Zhou to avoid infinit loop when root has it own id in its parent field
    if (rc.id == rc.data[this.parent_id_field_name]) 
      return null;
    return this.getById(rc.data[this.parent_id_field_name]);
  },
  
  getNodeChildren: function(rc){
    var i, len, result = [], records = this.data.getRange();
    
    for (i = 0, len = records.length; i < len; i++) {
      // modified by Sean.Zhou to allow parent_id equals to its own id for roots
      if (records[i].id == rc.id) 
        continue;
      if (records[i].data[this.parent_id_field_name] == rc.id) {
        result.push(records[i]);
      }
    }
    
    return result;
  },

  /**
   *
   * @access public
   * @param {Jaffa.data.Record} rc the record to be moved
   * @param {Jaffa.data.Record} refRec the record serve as reference point of drop location
   * @param {String} location string of ['above', 'bellow', 'into'] default='above'
   */
  moveNode: function(rec, refRec, location){
    // grab all the offspring records of rec in the store
    var idx = this.indexOf(rec);
    var nxtIdx = this.findNextInsertionPoint(rec);
    var allRecs = this.getRange(idx, nxtIdx - 1);
    
    // remove all the records from the store
    for (var i = (allRecs.length - 1); i >= 0; i--) {
      this.remove(allRecs[i]);
    }
    
    // locate the insertion point
    // drop back the removed records to the store by inserting into the insertion point
    if (location == 'into') {
      rec = this.addChildNode(refRec, rec);
    }
    else 
      if (location == 'bellow') {
        rec = this.addSiblingNode(refRec, rec);
      }
      else {
        rec.data[this.parent_id_field_name] = refRec.data[this.parent_id_field_name];
        this.insert(this.indexOf(refRec), rec);
      }
    idx = this.indexOf(rec);
    for (var i = 1; i < allRecs.length; i++) {
      this.insert(idx + i, allRecs[i]);
    }
    
    // return the inserted record
    return rec;
  },
  
  addChildNode: function(rec, nrec){
    nrec.data[this.parent_id_field_name] = rec.id;
    if (rec.data[this.leaf_field_name] == true) 
      rec.data[this.leaf_field_name] = false;
    rec.store.insert(rec.store.indexOf(rec) + 1, nrec);
    return nrec;
  },
  
  addSiblingNode: function(rec, nrec){
    nrec.data[this.parent_id_field_name] = rec.data[this.parent_id_field_name];
    rec.store.insert(this.findNextInsertionPoint(rec), nrec);
    return nrec;
  },
  
  //Inserts a sibling directly above the selected node
  insertSiblingBefore: function(rec, nrec){
    nrec.data[this.parent_id_field_name] = rec.data[this.parent_id_field_name];
    this.insert(this.indexOf(rec), nrec);
    return nrec;
  },
  
  //Inserts a sibling directly below the selected node
  insertSiblingAfter: function(rec, nrec){
    nrec.data[this.parent_id_field_name] = rec.data[this.parent_id_field_name];
    var nextSibling = this.getNodeNextSibling(rec);
    if (nextSibling) {
      this.insert(this.indexOf(nextSibling), nrec);
    } else {
      var index = this.getDecendentCount(rec) + this.indexOf(rec) + 1;
      this.insert(index, nrec);
    }
    return nrec;
  },
  
  getDecendentCount: function(node){
    var children = this.getNodeChildren(node);
    var count = children.length;
    for (var i = 0; i < children.length; i++) {
      count += this.getDecendentCount(children[i]); 
    }
    return count;
  },
  
  //Inserts a sibling at the end of the selected node's sibling collection
  appendSibling: function(rec, nrec){
    this.addSiblingNode(rec, nrec);
    return nrec;
  },
  
  //Adds a child to the top of selected node's children
  addChild: function(parent, nrec){
    this.addChildNode(parent, nrec);
    return nrec;
  },
  
  //Adds a child to the bottom of selected node's children
  appendChild: function(parent, nrec){
    //not supported
  },
  
  findNextInsertionPoint: function(rec){
    if (rec == this.getAt(rec.store.getCount() - 1)) {
      return this.getCount();
    }
    var prt = this.getNodeParent(rec);
    if (prt) {
      var chlds = this.getNodeChildren(prt);
      if (rec == chlds[chlds.length - 1]) {
        return this.findNextInsertionPoint(prt);
      }
      else {
        for (var i = 0; i < (chlds.length - 1); i++) {
          if (rec == chlds[i]) {
            var idx = this.indexOf(chlds[i + 1]);
            return idx == -1 ? this.getCount() : idx;
          }
        }
      }
    }
    else {
      // assumes one root
      return this.getCount();
    }
  },
  
  addToNode: function(parent, child){
    child.set(this.parent_id_field_name, parent.id);
    this.addSorted(child);
  },
  
  removeFromNode: function(parent, child){
    this.remove(child);
  }
});

Ext.reg('Ext.ux.maximgb.tg.AdjacencyListStore', Ext.ux.maximgb.tg.AdjacencyListStore);

/**
 * Tree store for nested set tree representation.
 */
Ext.ux.maximgb.tg.NestedSetStore = Ext.extend(Ext.ux.maximgb.tg.AbstractTreeStore, {
  /**
   * @cfg {String} left_field_name Record NS-left bound field name.
   */
  left_field_name: '_lft',
  
  /**
   * @cfg {String} right_field_name Record NS-right bound field name.
   */
  right_field_name: '_rgt',
  
  /**
   * @cfg {String} level_field_name Record NS-level field name.
   */
  level_field_name: '_level',
  
  /**
   * @cfg {Number} root_node_level Root node level.
   */
  root_node_level: 1,
  
  getRootNodes: function(){
    var i, len, result = [], records = this.data.getRange();
    
    for (i = 0, len = records.length; i < len; i++) {
      if (records[i].data[this.level_field_name] == this.root_node_level) {
        result.push(records[i]);
      }
    }
    
    return result;
  },
  
  getNodeDepth: function(rc){
    return rc.data[this.level_field_name] - this.root_node_level;
  },
  
  getNodeParent: function(rc){
    var result = null, rec, records = this.data.getRange(), i, len, lft, r_lft, rgt, r_rgt, level, r_level;
    
    lft = rc.data[this.left_field_name];
    rgt = rc.data[this.right_field_name];
    level = rc.data[this.level_field_name];
    
    for (i = 0, len = records.length; i < len; i++) {
      rec = records[i];
      r_lft = rec.data[this.left_field_name];
      r_rgt = rec.data[this.right_field_name];
      r_level = rec.data[this.level_field_name];
      
      if (r_level == level - 1 &&
      r_lft < lft &&
      r_rgt > rgt) {
        result = rec;
        break;
      }
    }
    
    return result;
  },
  
  getNodeChildren: function(rc){
    var lft, r_lft, rgt, r_rgt, level, r_level, records, rec, result = [];
    
    records = this.data.getRange();
    
    lft = rc.data[this.left_field_name];
    rgt = rc.data[this.right_field_name];
    level = rc.data[this.level_field_name];
    
    for (var i = 0, len = records.length; i < len; i++) {
      rec = records[i];
      r_lft = rec.data[this.left_field_name];
      r_rgt = rec.data[this.right_field_name];
      r_level = rec.data[this.level_field_name];
      
      if (r_level == level + 1 &&
      r_lft > lft &&
      r_rgt < rgt) {
        result.push(rec);
      }
    }
    
    return result;
  }
});

Ext.ux.maximgb.treegrid.AbstractTreeStore = Ext.ux.maximgb.tg.AbstractTreeStore;
Ext.ux.maximgb.treegrid.AdjacencyListStore = Ext.ux.maximgb.tg.AdjacencyListStore;
Ext.ux.maximgb.treegrid.NestedSetStore = Ext.ux.maximgb.tg.NestedSetStore;
