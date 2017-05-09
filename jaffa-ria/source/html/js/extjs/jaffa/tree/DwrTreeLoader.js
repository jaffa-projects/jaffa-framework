/**
 * @class Jaffa.tree.DwrTreeLoader
 * @extends Ext.tree.TreeLoader
 * @author SeanZ
 *
 * Custom Tree Loader to load data via a DWR service.
 * <p>
 * Example
new Ext.tree.TreePanel({
  loader : new Jaffa.tree.DwrTreeLoader({
    dataFieldMap : [{
      dataIndex : 'workOrderNo',
      mapping : 'workOrderNo'
    }],
    dwrService : new WorkOrderService({
      criteria : {}
    }),
    getGenericJsonObject : function(dwrBean) {
      return {
        draggable : false
        ,text : dwrBean.workOrderNo
        ,iconCls : dwrBean.hasChildren?'partIcon':'woIcon'
      };
    },
    getDefaultNodeCriteria : function(dwrBean) {
      if (dwrBean.hasChildren) {
        return {
          nextHigherWorkOrderNo : { values : [dwrBean.workOrderNo] }
          ,workOrderNo : { operator : 'NotEquals', values : [dwrBean.workOrderNo] }
          ,topWO : { values : ['False'] }
        };
      } else {
        return null;
      }
    }
  })
}) 
 */
Jaffa.tree.DwrTreeLoader = Ext.extend(Ext.tree.TreeLoader, {
	/**
	 * @cfg {Object} dwrService (required) A service object extended from
	 *      {@link Jaffa.DWRService}.
	 */
	dwrService : null,
	/**
	 * @cfg {Object} dataFieldMap (required) An array of data objects with
	 *      fields of 'dataIndex' refering to the fields of the nodes and
	 *      'mapping' refereing to the dwr data fields.
	 */
	dataFieldMap : null,
	
	loadingMaskText : 'Loading...',

	load : function(node, callback) {
		if (this.clearOnLoad) {
			while (node.firstChild) {
				node.removeChild(node.firstChild);
			}
		}
		if (this.doPreload(node)) { // preloaded json children
			if (typeof callback == "function") {
				callback();
			}
		} else if (this.dataUrl || this.url) {
			this.requestData(node, callback);
		} else if (this.dwrService) {
			this.requestDwrData(node, callback);
		} else {
			if (typeof callback == "function") {
				callback();
			}
		}
	},

	requestDwrData : function(node, callback) {
		if (this.fireEvent("beforeload", this, node, callback) !== false) {
			if (node.attributes.dwrCriteria) {
				if (node.isRoot) {
					this.dwrService.criteria = node.attributes.dwrCriteria;
				} else {
					this.dwrService.criteria = {};
					var rootCrt = node.getOwnerTree().getRootNode().attributes.dwrCriteria;
					for (var i in rootCrt) {
						this.dwrService.criteria[i] = rootCrt[i];
					}
					for (var i in node.attributes.dwrCriteria) {
						this.dwrService.criteria[i] = node.attributes.dwrCriteria[i];
					}
				}

				// return if no criteria is defined
				if (this.dwrService.criteria) {
					var shouldStop = true;
					for (var i in this.dwrService.criteria) {
						shouldStop = false;
						break;
					}
					if (shouldStop) {
						if (typeof callback == "function") {
							callback();
						}
						return;
					}
				} else {
					if (typeof callback == "function") {
						callback();
					}
					return;
				}

				node.ownerTree.el.mask(this.loadingMaskText, 'x-mask-loading');

				var t = this;
				this.dwrService.load(function(service) {
					t.transId = false;
					node.ownerTree.el.unmask();
					if (service.model == null) {
						t.handleDwrFailure(node, service.model, callback);
					} else {
						// load OK
						t.processDwrResponse(node, service.model, callback);
						t.fireEvent("load", this, node);
					}
				});
				this.transId = true;
			}
		} else {
			// if the load is cancelled, make sure we notify
			// the node that we are done
			if (typeof callback == "function") {
				callback();
			}
		}
	},

	/**
	 * Assumes the query retrieves only children of the node.
	 * 
	 * @param {Object} node
     * @param {Object} model
     * @param {Function} callback
	 */
	processDwrResponse : function(node, model, callback) {
		try {
			node.beginUpdate();
			model.each(function(cs) {
				var att = this.dwr2jsonObj(cs);
				var n = this.createNode(att);
				if (n) {
					node.appendChild(n);
				}
			}, this);
			node.endUpdate();
			if (typeof callback == "function") {
				callback(this, node);
			}
		} catch (e) {
			this.handleDwrFailure(node, model, callback, e);
		}

	},

	handleDwrFailure : function(node, model, callback, error) {
		var response = {
			argument : {
				node : node,
				callback : callback
			},
			responseText : Ext.util.JSON.encode(model)
		};
		if (error) {
			response.exception = error;
		}
		this.handleFailure(response);
	},

	/**
	 * May be overwritten for look-and-feel of the nodes.
	 * 
	 * @param {Object} dwrBean a dwr data graph of the new node
	 */
	getGenericJsonObject : function(dwrBean) {
		return {
			draggable : false
		};
	},

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

	// returns a json object from a dwr bean
	dwr2jsonObj : function(dwrBean) {
		var jsonObj = this.getGenericJsonObject(dwrBean);
		for (var i = 0; i < this.dataFieldMap.length; i++) {
			jsonObj[this.dataFieldMap[i].dataIndex] = Jaffa.data.Util.get(
					dwrBean, this.dataFieldMap[i].mapping);
		}
		jsonObj.hasChildren = dwrBean.hasChildren;
		if (jsonObj.hasChildren) {
			jsonObj.leaf = false;
		} else {
			jsonObj.leaf = true;
		}
		if (this.getDefaultNodeCriteria) {
			var ctr = this.getDefaultNodeCriteria(dwrBean);
			if (ctr)
				jsonObj.dwrCriteria = ctr;
		}
		return jsonObj;
	}

});
