/**
 * @class Jaffa.tree.DwrColumnTreeLoader
 * @extends Ext.tree.TreeLoader
 * @author SeanZ
 *
 * Custom Column Tree Loader to load data via a DWR service.
 * <p>
 * Example
cols=[{
      header: 'Part',
      width: 100,
      dataIndex: 'part',
      mapping: 'part.part'
    },...];
svc = new Jaffa.DWRService({
    dwrQuery: Parts.query
    ,dwrUpdate: Parts.update
    ,objectLabel: 'Parts Catalog'
});
new Ext.tree.ColumnTree({
  columns : cols
  ,loader: new Jaffa.tree.DwrColumnTreeLoader({
    dataFieldMap : cols
    ,dwrService : svc
    ,getGenericJsonObject : function(dwrBean) {
      return {
        uiProvider : 'col'
        ,draggable : false
        ,iconCls = 'woIcon'
      };
    }
    ,getDefaultNodeCriteria: function(dwrBean) {
      if (dwrBean.hasChildren) {
        return {
          nextHigherWorkOrderNo: {values:[dwrBean.workOrderNo]},
          workOrderNo: {operator: 'NotEquals', values:[dwrBean.workOrderNo]},
          topWO: false
        };
      } else {
        return null;
      }
    }  
  })
})
 * @note This is based on DWRService, which is being deprecated
 */
Jaffa.tree.DwrColumnTreeLoader = Ext.extend(Ext.tree.TreeLoader, {
	/**
	 * @cfg {Object} dwrService (required) A service object extended from
	 *      {@link Jaffa.DWRService}.
	 */
	dwrService : null,

	loadingMaskText : 'Loading...',
	
	uiProviders : {
		'col' : Ext.tree.ColumnNodeUI
	},
	/**
	 * @cfg {Object} dataFieldMap (required) An array of data objects with
	 *      fields of 'dataIndex' refering to the fields of the nodes and
	 *      'mapping' refereing to the dwr data fields.
	 */
	dataFieldMap : null,

	pathDelim : ',,',
    
    /**
     * @cfg {Object} expandChildren (optional) Defaults to true to expand the children. 
     * If set to false, the parent node will not show the expand icon (+ sign) and the 
     * node is not expandable.
     */
    expandChildren: true,

	/**
	 * @cfg {function} getDefaultNodeCriteria (required) create additional
	 *      criteria of the nodes based on a dwr data graph.
	 */
	getDefaultNodeCriteria : function(dwrBean) {
		if (dwrBean.hasChildren) {
			// implement the dwrCriteria for the node
			return null;
		} else {
			// this is a leaf node; normally just return null
			return null;
		}
	},

	/*
	 * A combo box returns the selected field mappings specifying the order of
	 * grouping the objects in the model
	 */
	groupingRule : null,
	getGroupingRuleValue : function() {
		if (this.groupingRule == null)
			return null;
		var flds = this.groupingRule.getValue();
		if (flds == null || flds == '')
			return null;
		if (typeof flds == 'string') {
			flds = [flds];
		}
		return flds;
	},

	/*
	 * A combo box returns the selected field mappings specifying the order of
	 * sorting the objects in the model
	 */
	sortingRule : null,
	getSortingRuleValue : function() {
		if (this.sortingRule == null)
			return null;
		var flds = this.sortingRule.getValue();
		if (flds == null || flds == '')
			return null;
		if (typeof flds == 'string') {
			flds = [flds];
		}
		return flds;
	},

	/**
	 * Separate the DWR beans in the input according to the grouping rule.
	 * Returns a {@link Ext.util.MixedCollection} with grouping folder path to
	 * DWR beans array.
	 * 
	 * @param {Object}
	 *            model, the model object of a {@link Jaffa.DWRService}.
	 */
	groupingDWRBeans : function(model, flds) {
		var output = new Ext.util.MixedCollection();
		if (flds == null) {
			flds = this.getGroupingRuleValue();
		}
		if (flds == null) {
			output.add('', model);
		} else {
			console.debug("Group DWR beans by " + flds);
			for (var i = 0; i < model.length; i++) {
				var pth;
				for (var j = 0; j < flds.length; j++) {
					var k = Jaffa.data.Util.get(model[i], flds[j]) || 'null';
					if (j == 0) {
						pth = k;
					} else {
						pth = pth + this.pathDelim + k;
					}
				}
				var obj = output.key(pth);
				if (obj == null) {
					obj = new Array();
					output.add(pth, obj);
				}
				obj.push(model[i]);
			}
			output.keySort('ASC', function(a, b) {
				return String(a).localeCompare(String(b));
			});
		}
		return output;
	},

	/**
	 * Sorts the DWR beans from input according to the sorting rule.
	 * 
	 * @param {Object}
	 *            beans
	 */
	sortDWRBeans : function(beans) {
		var flds = this.getSortingRuleValue();
		if (flds == null) {
			return beans;
		}
		var bag = this.groupingDWRBeans(beans, flds);
		var output = new Array();
		bag.eachKey(function(key) {
			var pk = bag.get(key);
			for (var i = 0; i < pk.length; i++) {
				output.push(pk[i]);
			}
		});
		return output;
	},

	/**
	 * Load an {@link Ext.tree.TreeNode} from the DWR service specified in the
	 * constructor. This is called automatically when a node is expanded, but
	 * may be used to reload a node (or append new children if the
	 * {@link #clearOnLoad} option is false.)
	 * 
	 * @param {Object}
	 *            node node for which child elements should be retrieved
	 * @param {Function}
	 *            callback function that should be called before executing the
	 *            DWR call
	 */
	load : function(node, callback) {
		var cs, i;
		if (this.clearOnLoad) {
			while (node.firstChild) {
				node.removeChild(node.firstChild);
			}
		}
		if (node.attributes.children && node.attributes.hasChildren) { // preloaded
			// json
			// children
			cs = node.attributes.children;
			for (i = 0, len = cs.length; i < len; i++) {
				node.appendChild(this.createNode(cs[i]));
			}
			if (typeof callback == "function") {
				callback();
			}
		} else {
			this.invokeService(node, callback);
		}
	},

	invokeService : function(node, callback) {
		// var success, error;
		var t = this;
		if (this.fireEvent("beforeload", this, node, callback) !== false) {
			// success = this.handleResponse.createDelegate(this, [node,
			// callback], 1);
			// error = this.handleFailure.createDelegate(this, [node, callback],
			// 1);
			// set the loading criteria
			if (node.attributes.dwrCriteria) {
				if (node.isRoot) {
					this.dwrService.criteria = {};
                    Ext.apply(this.dwrService.criteria, node.attributes.dwrCriteria);
                    if (node.attributes.additionalDwrCriteria) {
                        Ext.apply(this.dwrService.criteria, node.attributes.additionalDwrCriteria);
                    }
				} else {
					this.dwrService.criteria = {};
                    Ext.apply(this.dwrService.criteria, node.getOwnerTree().getRootNode().attributes.dwrCriteria);
                    Ext.apply(this.dwrService.criteria, node.attributes.dwrCriteria);
				}

				// return if no criteria is defined
				if (this.dwrService.criteria) {
					var shouldStop = true;
					for (var i in this.dwrService.criteria) {
						shouldStop = false;
						break;
					}
					if (shouldStop)
						return;
				} else {
					return;
				}

//				node.ownerTree.el.mask('Loading...', 'x-mask-loading');
				Ext.get(document.body).mask(this.loadingMaskText, 'x-mask-loading');

				this.dwrService.load(function(service) {
					if (service.model == null) {
						// load error
						t.fireEvent("loadexception", this, node);
						if (typeof callback == "function") {
							callback(t, node);
						}
					} else {
						// load OK
						t.transId = false;
						t.createNodes(t, node, service.model);
						t.fireEvent("load", this, node);
					}
					Ext.get(document.body).unmask();
				});
			}
		}

		// Make sure we notify the node that we are done
		if (typeof callback == "function") {
			callback();
		}
	},

	createNodes : function(loader, node, model) {
		if (node.isRoot && this.groupingRule) {
			// group DWR beans
			var fields = this.getGroupingRuleValue();
			var gBeans = this.groupingDWRBeans(model.getRange(), fields);
			// create the grouping folder nodes
			var nodeStack = new Array();
			gBeans.eachKey(function(key) {
				var beans = gBeans.get(key);
				if (key == '') {
					this.addDWRbean2tree(node, beans);
				} else {
					var ks = key.split(this.pathDelim);
					var currentNode = node;
					// create the group folder nodes in the tree
					for (var i = 0; i < ks.length; i++) {
						if (i < nodeStack.length
								&& nodeStack[i].attributes._folderName == ks[i]) {
							currentNode = nodeStack[i];
							continue;
						} else if (i == 0) {
							nodekStack = new Array();
						} else {
							// trim the node stack
							nodeStack = nodeStack.slice(0, i);
						}
						nodeStack[i] = this.createNode({
							uiProvider : 'col',
							cls : 'woRow',
							// iconCls: 'woIcon',
							_folderName : ks[i],
							_folderType : fields[i],
							hasChildren : true,
							draggable : false,
							expanded : true,
							isFolder : true,
							_aggregate : {},
							_count : 0
						});
						// nodeStack[i].attributes[node.attributes.nodeNameColumn]
						// = ks[i];
						currentNode.appendChild(nodeStack[i]);
						currentNode = nodeStack[i];
					}
					// add DWR beans to the terminal folder node
					this.addDWRbean2tree(currentNode, beans);
					console.debug("Added Node for " + key);
					// update the counts
					for (var i = 0; i < ks.length; i++) {
						nodeStack[i].attributes._count += beans.length;
						this.folderRollup(nodeStack[i], beans);
					}
				}
			}, this);
		} else {
			// create the DWR bean nodes
			this.addDWRbean2tree(node, model.getRange());
			// TODO: update the node counts in the parent nodes
		}
		this.nodeUiUpdate(node);
	},

	folderRollup : function(node, beans) {
		// empty method to be implemented by the container of this tree loader.
		// to perform the rollup operations on the node.
		// node is a folder,
		// beans are the leaves in a lowerst folder.
	},

	folderNodeUpdate : function(node) {
		// empty method to be implemented by the container of this tree loader.
		// updates the attributes of the node for the columns in the tree.
	},

	nodeUiUpdate : function(node, updateParent) {
		if (typeof node == 'undefined' || node == null)
			return;
		if (node.isRoot == true && updateParent == true)
			return;
		if (node.isRoot == true) {
			for (var i = 0; i < node.childNodes.length; i++) {
				this.nodeUiUpdate(node.childNodes[i], false);
			}
		} else {
			// update the children and itself
			if (node.attributes.isFolder == true) {
				// update the node
				this.folderNodeUpdate(node);
				node.getUI().update();
				// update the children
				if (updateParent != true) {
					for (var i = 0; i < node.childNodes.length; i++) {
						this.nodeUiUpdate(node.childNodes[i], false);
					}
				}
			}
			// update the parent
			if (updateParent != false) {
				this.nodeUiUpdate(node.parentNode, true);
			}
		}
	},

	addDWRbean2tree : function(theNode, beans) {
		if (beans == null || theNode == null) {
			console.debug("Error: either the node or the beans is null.");
		} else if (beans.length == 0) {
			console.debug("Error: No beans to add.");
		}

		// sorting the beans
		beans = this.sortDWRBeans(beans);

		// adding the beans to the node.
		for (var i = 0; i < beans.length; i++) {
			var jsonObj = this.dwr2jsonObj(beans[i]);
			if (jsonObj) {
				var n = this.createNode(jsonObj);
				if (n) {
					theNode.appendChild(n);
					// console.debug("Added Node:",n);
				}
			}
		}
	},

	/**
	 * May be overwritten for look-and-feel of the nodes.
	 * 
	 * @param {Object}
	 *            dwrBean dwrBean a dwr data graph of the new node
	 */
	getGenericJsonObject : function(dwrBean) {
		return {
			draggable : false
		};
	},

	// returns a json object from a dwr bean
	dwr2jsonObj : function(dwrBean) {
		var jsonObj = this.getGenericJsonObject(dwrBean);
		for (var i = 0; i < this.dataFieldMap.length; i++) {
			jsonObj[this.dataFieldMap[i].dataIndex] = Jaffa.data.Util.get(
					dwrBean, this.dataFieldMap[i].mapping);
		}
		jsonObj.hasChildren = dwrBean.hasChildren;
		if (this.getDefaultNodeCriteria) {
			var ctr = this.getDefaultNodeCriteria(dwrBean);
			if (ctr)
				jsonObj.dwrCriteria = ctr;
		}
		return jsonObj;
	},

	/**
	 * Creates a new tree node. Node will be an AsyncTreeNode if node has
	 * children that might be loaded later
	 * 
	 * @param {Object}
	 *            attr attributes of this new node
	 */
	createNode : function(attr) {
		if (this.baseAttrs) {
			Ext.applyIf(attr, this.baseAttrs);
		}
		if (this.applyLoader !== false) {
			attr.loader = this;
		}
		if (typeof attr.uiProvider == 'string') {
			attr.uiProvider = this.uiProviders[attr.uiProvider]
					|| eval(attr.uiProvider);
		}
		return (this.expandChildren && attr.hasChildren
				? new Ext.tree.AsyncTreeNode(attr)
				: new Ext.tree.TreeNode(attr));
	}

});
