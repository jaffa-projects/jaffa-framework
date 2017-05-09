// -------------
// Deprecated by Jaffa.finder.CriteriaPanel
// -------------


/**
 * @author SeanZ
 */

//@TODO add namespace!
LoadQueryMenu = function(config) {
  config = config || {};
  var nameQueryPairs = config.nameQueryPairs || SavedQueries.nameQueryPairs;
  if (config.nameQueryPairs) config.nameQueryPairs = null;
  LoadQueryMenu.superclass.constructor.call(this, config);
  if (nameQueryPairs) {
    for (var i=0; i<nameQueryPairs.length; i++) {
      this.addQuery(nameQueryPairs[i][0], nameQueryPairs[i][1]);
    }
  }
};
Ext.extend(LoadQueryMenu, Ext.menu.Menu, {

	alertMsgQueryText : 'Query',
	
	alertMsgNotInTheListText : 'is not in the list',
	
	loadQueryTitleText: 'Load Query',
	
	savedQueriesTitleText: 'Saved Queries',
	
	saveQueryTitleText: 'Save Query',
	
	promtQueryNameToSaveTitleText: 'Query Name to Save',
	
	promtMsgEnterQueryNameToSavedText: 'Please enter the name of the query to be saved',
	
	saveButtonText: 'Save',
	
	saveAsDefaultQueryButtonText: 'Save as Default Query',
	
	cancelButtonText: 'Cancel',
	
	deleteQueryTitleText: 'Delete Query',
	
	confirmDeleteQueryConfirmationTitleText: 'Delete query confirmation',
	
	deleteQueryActionConfirmMsgText: 'Please confirm your action of deleting query',
	
	clearCriteriaTitleText: 'Clear Criteria',
	
	searchTitleText: 'Search',
	
	alertMsgNoSavedQuerySelectedText: 'No saved query is selected.',
	
	alertMsgBasecriteriaObjecNotRegSavedQueryPanelText: 'Base criteria object is not registered in Saved Query Panel.',
	
	
	
  /**
   * @cfg {String} selectedQuery Name of the selected query.
   */
  /**
   * @cfg {Object} containerPanel A {@link Ext.Panel} object that contains this menu. 
   * This panel should implement loadQuery().
   */
  
  /**
   * Get the query item by its name. Return null if it is not found.
   * @param {Object} qname, the query name.
   */
  getItem: function(qname) {
    var v = null;
    this.items.each(function(itm) {
      if (itm.name==qname) {
        v = itm;
        return false;
      }
    });
    return v;
  },
  
  /**
   * Convert a display name to the name of a query.
   * @param {Object} dname, the display name of the query.
   */
  getQueryNameByDisplayName: function(dname) {
    if (dname==null || dname=='') return '';
    var i = this.items.findIndex('text', dname);
    if (i>=0) {
      return this.items.itemAt(i).name;
    } else {
      return '';
    }
  },
  
  /**
   * Add a query data to the query item store. If it is a default query, it will remove
   * default status on other queries. Then the added query will be loaded to the container.
   * 
   * @param {Object} name, name of the query
   * @param {Object} data, query data.
   */
  addQuery: function(name, data) {
    var theMenu = this;
    var q = this.getItem(name);
    if (q) {
      q.queryData = data;
      if (data.isDefault) {
        this.setAsDefaultQuery(name);
      }
      theMenu.loadQuery2Panel(q.text, data);
    } else {
      if (data.isDefault) {
        this.items.each(function(itm){
          itm.queryData.isDefault = false;
          itm.setText(itm.name);
        });
      }
      this.addMenuItem({
        name: name, 
        text: data.isDefault? '*'+name+'*':name,
        queryData: data,
        handler: function() {
          theMenu.loadQuery2Panel(this.text, this.queryData);
        }
      });
    }
  },
  
  /**
   * Remove the query from the item store.
   * @param {Object} name, the name of the query.
   * @param {Object} scope, the store.
   */
  removeQuery: function(name, scope) {
    scope = scope || this;
    var i = scope.items.findIndex('name', name);
    if (i>=0) {
      scope.remove(scope.items.itemAt(i));
    } else {
      alert(this.alertMsgQueryText+" "+name+" "+this.alertMsgNotInTheListText+".");
    }
  },
  
  /**
   * Populate the fields in the container panel by the query data. Then add the display query name
   * to the container title bar.
   * 
   * @param {Object} qname, display query name
   * @param {Object} criteria, the query data to be populated to the fields in container panel.
   */
  loadQuery2Panel: function(qname, criteria) {
    if (this.containerPanel) {
      this.containerPanel.loadQuery(qname, criteria);
    }
  },
  
  /**
   * Set a query to default query and remove default query status from other queries.
   * @param {Object} qname, name of the query.
   */
  setAsDefaultQuery: function(qname) {
    this.items.each(function (itm) {
      if (itm.name == qname) {
        itm.queryData.isDefault = true;
        itm.setText('*'+itm.name+'*');
      } else {
        itm.queryData.isDefault = false;
        itm.setText(itm.name);
      }
    }, this);
  }
});


SavedQueryPanel = function(idVal){
  var loadQueryMenu = new LoadQueryMenu({
    fieldLabel: this.loadQueryTitleText
  });
  var config = {
    id: idVal,
    title: this.savedQueriesTitleText,
    layout: 'formDescription',
    baseParams: {
      pageRef: SavedQueries.pageRef
    },
    url: SavedQueries.url,
    baseCriteriaObj: null,
    items: [
          {
            text: this.loadQueryTitleText,
            iconCls: 'bmenu',  // <-- icon
            menu: loadQueryMenu  // assign menu by instance
          },'-',{
            text: this.saveQueryTitleText,
            handler: function() {
              // save the query to the 
              var mainPanel = Ext.getCmp(idVal);
              // get the query name from main panel
              var qname = loadQueryMenu.getQueryNameByDisplayName(mainPanel.getDisplayQueryName());
              var itm = loadQueryMenu.getItem(qname)
              Ext.MessageBox.show({
                title: this.promtQueryNameToSaveTitleText,
                msg: this.promtMsgEnterQueryNameToSavedText+':',
                prompt: true,
                buttons: {yes: this.saveButtonText, no: this.saveAsDefaultQueryButtonText, cancel: this.cancelButtonText},
                value: qname,
                fn: function(btn, text){
                  if (btn == 'cancel') return;
                  qname = text;
                  var isDefault = false;
                  if (btn == 'no') isDefault = true;
                  // save the query
                  mainPanel.saveQuery(qname, isDefault, function(qname, criteriaData){
                    loadQueryMenu.addQuery(qname, criteriaData);
                  });
                }
              });
            }
          },'-',{
            text: this.deleteQueryTitleText,
            handler: function() {
              var mainPanel = Ext.getCmp(idVal);
              var qname = loadQueryMenu.getQueryNameByDisplayName(mainPanel.getDisplayQueryName());
              if (qname==null || qname=='') return;
              Ext.MessageBox.confirm(this.confirmDeleteQueryConfirmationTitleText, this.deleteQueryActionConfirmMsgText+' '+qname, function(btn) {
                if (btn=='yes') {
                  mainPanel.deleteQuery(qname, loadQueryMenu.removeQuery, loadQueryMenu); 
                }
              })
            }
          },'-',{
            text: this.clearCriteriaTitleText,
            handler: function() {
              var mainPanel = Ext.getCmp(idVal);
              mainPanel.resetCurrentQuery(); // this should include reset query name to empty.
            }
          },'-',{
            text: this.searchTitleText,
            handler: function() {
              var mainPanel = Ext.getCmp(idVal);
              mainPanel.search();
            }
          }
        ]
  };
  
  SavedQueryPanel.superclass.constructor.call(this, config);
  loadQueryMenu.containerPanel = this;
  this.savedQueryMenu = loadQueryMenu;
};
Ext.extend(SavedQueryPanel, Ext.Toolbar, {
  /**
   * The Menu object configured to list all the saved queries.
   * @type LoadQueryMenu
   * @property savedQueryMenu
   */
  
  /**
   * Load the query into the container fields and set the query name to the toolbar.
   * 
   * @param {Object} qname, display name of the query
   * @param {Object} queryData, the query data to be populated.
   */
  loadQuery: function(qname, queryData) {
    if (queryData==null || typeof queryData!='object') {
      alert(this.alertMsgNoSavedQuerySelectedText)
      return;
    }
    if (this.baseCriteriaObj == null) {
      alert(this.alertMsgBasecriteriaObjecNotRegSavedQueryPanelText);
      return;
    }
    // load saved query into the fields in the forms
    this.resetCurrentQuery(qname, queryData);
  },
  
  /**
   * Apply the search action. Called by the search button.
   */
  search: function() {
    this.baseCriteriaObj.ownerCt.search();
  },
  
  /**
   * Delete the saved query on server side. Executes call back function when deletion is successful.
   * 
   * @param {Object} qname, the name of the saved query.
   * @param {Object} fn, callback function when deletion is successful.
   * @param {Object} savedQueryMenu, the container menu that hosts the saved queries.
   */
  deleteQuery: function(qname, fn, savedQueryMenu) {
    if (qname==null || qname=='') return;
    // delete saved query option
    var toolbar = this;
    var opt = {
      params: {
        pageRef: SavedQueries.pageRef,
        eventId: 'deleteSavedQuery',
        queryName: encodeURI(qname).replace(/'/g, "\\'")
      },
      method: 'POST',
      success: function(response, options) {
        if (response.status==200) {
          if (fn) fn(qname, savedQueryMenu);
          toolbar.setDisplayQueryName(null);
        }
      }
    };
    this.submit(opt);
  },
  
  /**
   * Loads the query data into the fields of the container panel and sets the query name to the title bar.
   * @param {Object} displayName, the display query name to be set to the title bar of the panel.
   * @param {Object} queryData, the query data to be used to populate the fields.
   */
  resetCurrentQuery: function(displayName, queryData) {
    queryData = queryData || {};
    this.baseCriteriaObj.resetCriteria(queryData.criteria);
    this.baseCriteriaObj.ownerCt.setFieldsFromCriteria();
    if (queryData.sortOrder) 
      this.baseCriteriaObj.setSortOrderByDisplayValue(queryData.sortOrder);
    if (queryData.groupOrder)
      this.baseCriteriaObj.setGroupOrderByDisplayValue(queryData.groupOrder);
    displayName = displayName || '';
    this.setDisplayQueryName(displayName);
  },
  
  /**
   * Returns the currently loaded query name.
   */
  getDisplayQueryName: function() {
    return this.currentQueryName;
  },
  
  /**
   * Set the current query name to the display name. Set the display name to the title of the Panel.
   * @param {Object} qname, display query name
   */
  setDisplayQueryName: function(qname) {
    this.currentQueryName = qname;
    this.snapshotOwnerCtTitle = this.snapshotOwnerCtTitle || this.baseCriteriaObj.ownerCt.title;
    if (qname && qname != '') {
      this.baseCriteriaObj.ownerCt.setTitle(this.snapshotOwnerCtTitle + " [" + qname + "]");
    } else {
      this.baseCriteriaObj.ownerCt.setTitle(this.snapshotOwnerCtTitle);
    }
  },
  
  /**
   * Save the query to server and then populate the query data to the fields in the panel 
   * and set the title of Panel to the query display name. 
   * @param {Object} qname
   * @param {Object} isDefault
   * @param {Object} fn
   */
  saveQuery: function(qname, isDefault, fn) {
    if (this.baseCriteriaObj == null) {
      alert(this.alertMsgBasecriteriaObjecNotRegSavedQueryPanelText);
      return;
    }
    this.baseCriteriaObj.ownerCt.setCriteriaFromFields();
    var data = {
      criteria: this.baseCriteriaObj.getCriteria(),
      sortOrder: this.baseCriteriaObj.getSortOrderDisplayValue(),
      groupOrder: this.baseCriteriaObj.getGroupOrderDisplayValue(),
      isDefault: isDefault
    };
    var toolbar = this;
    var opt = {
      params: {
        pageRef: SavedQueries.pageRef,
        eventId: 'saveQuery',
        isDefault: data.isDefault,
        queryName: encodeURI(qname).replace(/'/g, "\\'"),
        jsonData: encodeURI(Ext.encode(data)).replace(/'/g, "\\'")
      },
      method: 'POST',
      success: function(response) {
        if (response.status==200) {
          if (fn) fn(qname, data);
          var displayName = qname;
          if (data.isDefault) displayName = '*'+qname+'*';
          toolbar.setDisplayQueryName(displayName);
        }
      }
    };
    this.submit(opt);
  },
  
  // private, submit the json call to the server.
  submit: function(opt) {
    var conn = new Ext.data.Connection({
      url: this.url,
      method: this.method
    });
    if (opt.url===null) opt.url = this.url;
    conn.request(opt);
  },
  
  runDefaultQuery: function() {
    this.savedQueryMenu.items.each(function(itm) {
      if (itm.queryData.isDefault) {
        this.loadQuery(itm.text, itm.queryData);
        this.search();
        return false;
      }
    }, this);
  },
  
  /**
   * Register the target criteria object for this query saver.
   * @param {Object} baseCriteriaObj, the target criteria object.
   */
  wireActions: function(baseCriteriaObj) {
    this.baseCriteriaObj = baseCriteriaObj
  }
});
