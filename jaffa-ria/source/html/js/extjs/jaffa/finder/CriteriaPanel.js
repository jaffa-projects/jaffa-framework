/** This assumes that the js\extjs\jaffa\finder\querySaver.jsp has been included.
 *  The querySaver.jsp creates an object called 'SavedQueries', which contains the following
 *    - pageRef:        Which is the name of this web page, that is used to key all the queries back to this page
 *    - url:            Which is a URL to the querySaver.jsp that is used to save or delete a query
 *    - nameQueryPairs: This is an object structure of all the current save query definitions
 *        - An Array or queries, each entry is
 *        [ <String>{name}, <object>{query}]
 *        - {query} is defined as an object with the following fields
 *          - criteria : The prebuilt criteria object that would be passed to the DWR query service
 *          - groupOrder :  Value from the grouping Field
 *          - isDefault : Boolean, true if this was saved as the default
 *          - sortOrder :  Value from the sort Field
 */
/**
 * @class Jaffa.finder.CriteriaPanel
 * @param config Panel configuration to be overloaded onto the default config created by this class
 */
Jaffa.finder.CriteriaPanel = Ext.extend(Ext.Panel, {
  /**
   * The Menu object configured to list all the saved queries.
   * @type Jaffa.finder.LoadQueryMenu
   * @property loadMenu
   */
  /**
   * @cfg store (Optional)
   * This is the store that the criteria will be applied to for a search. If it is
   * null, it should be obtained from the results panels; in which case, multiple stores
   * may be operating simultaneously.
   */
  /**
   * @cfg hasInitCriteria
   * @type boolean
   * Defaults to false. true if its some of the fields on this criteria panel is set from request parameters.
   * Useful when some default settings to apply when no request parameters are set.
   */
  /**
   * @cfg grid {Ext.gridPanel}
   * (Mandatory) This is the associated Grid, so we can clear any filters prior to a query
   */
  /** Text objects that can be replaced for internationalization */
  loadButtonText: 'Load Query',
  saveButtonText: 'Save Query',
  deleteButtonText: 'Delete Query',
  clearButtonText: 'Clear Criteria',
  searchButtonText: 'Search',
  saveTitleText: 'Save Query',
  saveMsgText: 'Please enter the name of the query to be saved:',
  saveMsgYesText: 'Save',
  saveMsgNoText: 'Save as Default',
  saveMsgCancelText: 'Cancel',
  deleteTitleText: 'Delete query confirmation',
  deleteMsgTpl: 'Please confirm your action of deleting query {0}',
  errorTitleText: 'An Error Occurred',
  saveErrorText: 'Failed to Save Query',
  deleteErrorText: 'Failed to Delete Query',
  titleBase : 'Criteria',
  doGetCriteria: Ext.emptyFn,
  doSetCriteria: Ext.emptyFn,
  doResetCriteria: Ext.emptyFn,
  validationError : 'Validation Error',
  invalidSearchCriteria : 'Search Criteria has invalid value(s)',
  alertMsgQueryMsgText : 'Query',
  alertMsgNotInTheListMsgText : 'is not in the list.',
  maxRecordsTitleText : 'Max Records',
  alertMsgNoSavedQuerySelected: 'No saved query is selected.',
  
  constructor: function(config) {
    Ext.applyIf(config, {
      /** Apply Default properties for all CriteriaPanels */
      split: true,
      collapsible: true,
      titleCollapse: true,
      width: 550,
      minWidth: 450,
      autoScroll: true,
      frame: true,
      bodyStyle: "padding:5px 5px 0",
      keys: ({
        key: Ext.EventObject.ENTER,
        scope: this,
        fn: function() {
          this.search();
        }
      })
    });
    
    // Build the panel that lists all the saved queries
    this.loadMenu = new Jaffa.finder.LoadQueryMenu({
      finderPanel: this
    });
    
    // Configure an empty panel with a toobar for save-query buttons
    var tbar = [{
      text: this.loadButtonText,
      iconCls: 'saved-query-load',
      menu: this.loadMenu // assign menu by instance
    }, '-', {
      text: this.saveButtonText,
      scope: this,
      iconCls: 'saved-query-add',
      handler: function() {
        // get the query name from main panel
        var itm = this.loadMenu.getItemByDisplayName(this.getDisplayQueryName());
        
        var promptWin = new Ext.Window({
          id: 'save-qry-prompt-window',
          autoCreate: true,
          modal: true,
          shim: true,
          width: 400,
          height: 125,
          plain: true,
          closable: true,
          title: this.saveTitleText,
          items: [{
            xtype: 'form',
            labelAlign: 'top',
            border: false,
            frame: false,
            buttonAlign: "center",
            monitorValid: true,
            bodyStyle: 'padding: 5px; background-color: transparent',
            items: [{
              xtype: 'textfield',
              id: 'queryTextPrompt',
              anchor: '95%',
              fieldLabel: this.saveMsgText,
              value: itm ? itm.name : '',
              allowBlank: false
            }],
            buttons: [{
              id: 'save-qry-btn',
              scope: this,
              text: this.saveMsgYesText,
              formBind: true,
              handler: function() {
                this.saveQuery(Ext.getCmp('queryTextPrompt').getValue(), false);
                Ext.getCmp('save-qry-prompt-window').close();
              }
            }, {
              id: 'save-default-qry-btn',
              scope: this,
              text: this.saveMsgNoText,
              formBind: true,
              handler: function() {
                this.saveQuery(Ext.getCmp('queryTextPrompt').getValue(), true);
                Ext.getCmp('save-qry-prompt-window').close();
              }
            }, {
              id: 'cancel-save-qry-btn',
              scope: this,
              text: this.saveMsgCancelText,
              handler: function() {
                Ext.getCmp('save-qry-prompt-window').close();
              }
            }]
          }]
        }).show();
      }
    }, '-', {
      text: this.deleteButtonText,
      iconCls: 'saved-query-delete',
      scope: this,
      handler: function() {
        var itm = this.loadMenu.getItemByDisplayName(this.getDisplayQueryName());
        if (itm == null) return;
        Ext.MessageBox.confirm(this.deleteTitleText, new Ext.Template(this.deleteMsgTpl).apply([itm.name]), function(btn) {
          if (btn == 'yes') {
            this.deleteQuery(itm.name);
          }
        }, this);
      }
    }, '-', {
      text: this.clearButtonText,
      iconCls: 'clear',
      scope: this,
      handler: this.resetPanel
    }, '-', {
      text: this.searchButtonText,
      iconCls: 'search',
      scope: this,
      handler: function() {
        this.search();
      }
    }];
    
    if (config.tbar && config.tbar.length > 0) {
      for (var i = 0; i < config.tbar.length; i++) {
        tbar.push(config.tbar[i]);
      }
    }
    config.tbar = tbar;
    
    // Now create the panel
    Jaffa.finder.CriteriaPanel.superclass.constructor.call(this, config);
    console.debug("Jaffa.finder.CriteriaPanel: created with ", config);
    Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(this, null, ['LABEL', 'CASE', 'HIDDEN', 'LAYOUT']);
  },
  initComponent : function(){
     Jaffa.finder.CriteriaPanel.superclass.initComponent.call(this);
     //By this point interceptor will assign titleToken to this panel
     if(this.title) this.titleBase = this.title;
  },
  /**
   * loadQuery
   * Load the query into the container fields and set the query name to the toolbar.
   *
   * @param {Object} qname, display name of the query
   * @param {Object} queryData, the query data to be populated.
   * @param {Object} autoSearch, if true executes a search after loading the criteria.
   */
  loadQuery: function(qname, queryData, autoSearch) {
    if (queryData == null || typeof queryData != 'object') {
      alert(this.alertMsgNoSavedQuerySelected)
      return;
    }
    // load saved query into the fields in the forms
    if (SavedQueries.version && SavedQueries.version=='2.0'){
      this.setPanelFromDbCriteria(queryData);
    } else {
      this.setPanelFromCriteria(queryData);
    }
    if (this.store) {
      this.sortOrder = queryData.sortOrder;
      this.groupOrder = queryData.groupOrder;
    } else if (Ext.isArray(this.stores) && this.stores.length>0) {
      this.sortOrders = queryData.sortOrders;
      this.groupOrders = queryData.groupOrders;
    }
    this.setDisplayQueryName(qname);
    if (autoSearch) {
      this.search(queryData.criteria);
    }
  }  /** This function initiates a load on the store based on the currently entered criteria
   */
  ,
  search: function(criteria) {
	// disabling the comment panel when we click on the Search button
	// ex: Default Comments screen - below comments section
	if(this && this.grid && this.grid.refOwner && this.grid.refOwner.ownerCt 
		&& this.grid.refOwner.ownerCt.maintenancePanelRef){	
		this.grid.refOwner.ownerCt.maintenancePanelRef.setDisabled(true);
		this.grid.refOwner.ownerCt.maintenancePanelRef.collapse();
	}
    criteria = criteria || this.getCriteriaFromPanel();
    if(criteria == false){
      return false;
    }
    if (this.store) {
      var params = {};
      Ext.apply(params, this.baseParams);
      Ext.apply(params, criteria);
      // Set this to be the base criteria for the store, and reload it!
      console.debug("CriteriaPanel.search: params=", params);
      // @TODO - Apply sorting and grouping state
      this.store.baseParams = params;
      delete this.store.lastOptions;
      if (this.groupOrder) this.store.groupBy(this.groupOrder);
      if (this.sortGroup) this.store.sort(this.sortGroup[0].name, this.sortGroup[0].direction);

      //Mark filters using disabledDynamically where a criteria has been set for that field, filter can then be disabled later.
      var filters;
      if (this.grid.plugins && this.grid.plugins.clearFilters)
        filters = this.grid.plugins;
      if (this.grid.filters && this.grid.filters.clearFilters)
        filters = this.grid.filters;
      if (filters){
        filters.clearFilters();
        filters.filters.each(function (filter) {
          var field = this.grid.getStore().recordType.getField(filter.dataIndex);
          if (criteria && field && (criteria[field.filterFieldName]||criteria[field.name]))
            filter.disabledDynamically=true;
          else
            filter.disabledDynamically=false;
        }, this);
      }

      this.store.loadMore(true);
      if (this.rendered) this.collapse();
    } else {
      var flag = false;
      if (Ext.isArray(this.stores) && this.stores.length>0){
        flag = true;
        for (var i=0; i<this.stores.length; i++) {
          if (this.stores[i]) {
            this.stores[i].baseParams = Ext.ux.clone(criteria);
            delete this.stores[i].lastOptions;
            if (Ext.isArray(this.groupOrders)) 
              this.stores[i].groupBy(this.groupOrders[i]);
            if (Ext.isArray(this.sortGroups)) 
              this.stores[i].sort(this.sortGroups[i][0].name, this.sortGroups[i][0].direction);
            if (this.resultsPanel.getComponent(i).plugins && this.resultsPanel.getComponent(i).plugins.clearFilters) 
              this.resultsPanel.getComponent(i).plugins.clearFilters();
            this.stores[i].loadMore(true);
          }
        }
      } 
      if (Ext.isArray(this.controllers) && this.controllers.length>0){
        flag = true;
        for (var i=0; i<this.controllers.length; i++) {
          if (this.controllers[i]) {
            this.controllers[i].criteria = Ext.ux.clone(criteria);
            this.controllers[i].load();
          }
        }
      } 
      
      if (flag) {
        if (this.rendered) this.collapse();
      } else console.error("No Stores nor controllers Associated with Criteria Panel");
    }
  }  /**
   * deleteQuery
   * Delete the saved query on server side. Executes call back function when deletion is successful.
   * @param {Object} qname, the name of the saved query.
   */
  ,
  deleteQuery: function(qname) {
    if (qname == null || qname == '') return;
    // delete saved query option
    // var toolbar = this;
    var opt;
    if (SavedQueries.version && SavedQueries.version=='2.0'){
      opt = {
        params: {
          componentRef: SavedQueries.componentRef,
          contextRef: SavedQueries.contextRef,
          eventId: 'deleteSavedQuery',
          queryName: qname
        },
        scope: this,
        success: this.deleteOk.createDelegate(this, [qname], 0)
      };
    }else{
      opt = {
        params: {
          pageRef: SavedQueries.pageRef,
          eventId: 'deleteSavedQuery',
          queryName: encodeURI(qname).replace(/'/g, "\\'")
        },
        scope: this,
        success: this.deleteOk.createDelegate(this, [qname], 0)
      };
    }
    this.submit(opt);
  }  // private - called when the delete has processed
  ,
  deleteOk: function(qname, response, options) {
    if (response.status == 200) {
      this.loadMenu.removeQuery(qname, this.loadMenu);
      this.setDisplayQueryName(null);
    }
    else {
      Ext.MessageBox.show({
        title: this.errorTitleText,
        msg: this.deleteErrorText,
        buttons: Ext.Msg.OK,
        icon: Ext.MessageBox.ERROR
      });
    }
  }  /**
   * Returns the currently loaded query name.
   */
  ,
  getDisplayQueryName: function() {
    return this.currentQueryName;
  }  /**
   * setDisplayQueryName
   * Set the current query name to the display name. Set the display name to the title of the Panel.
   * @param {Object} qname, display query name
   */
  ,
  setDisplayQueryName: function(qname) {
    this.currentQueryName = qname;
    if (!this.titleBase && this.title) this.titleBase = this.title;
    if (qname && qname != '') {
      this.setTitle(this.titleBase + " [" + qname + "]");
    }
    else {
      this.setTitle(this.titleBase);
    }
    console.debug("New Title: ", this.title);
  },
  
  /**
   * saveQuery
   * Save the query to server and then populate the query data to the fields in the panel
   * and set the title of Panel to the query display name.
   * @param {Object} qname
   * @param {Object} isDefault
   */
  saveQuery: function(qname, isDefault) {
    var criteria = this.getCriteriaFromPanel();
    if(criteria == false){
      return false;
    }
    var data = {
      criteria: criteria,
      isDefault: isDefault
    };
    if (this.store) {
      Ext.apply(data, {
        sortOrder: this.store.getSortState(),
        groupOrder: this.store.getGroupState()
      });
    } else if (Ext.isArray(this.stores) && this.stores.length>0) {
      var sortOrders = [], groupOrders = [];
      for (var i=0; i<this.stores.length; i++) {
        if (this.stores[i]) {
          sortOrders.push(this.stores[i].getSortState());
          groupOrders.push(this.stores[i].getGroupState());
        }
      }
      Ext.apply(data, {
        sortOrders: sortOrders,
        groupOrders: groupOrders
      });
    }
    if (SavedQueries.version && SavedQueries.version=='2.0'){
      var opt = {
        params: {
          componentRef: SavedQueries.componentRef,
          contextRef: SavedQueries.contextRef,
          eventId: 'saveQuery',
          isDefault: data.isDefault,
          queryName: qname,
          criteria: Ext.encode(data.criteria)
        },
        success: this.saveOk.createDelegate(this, [qname, data], 0)
      };
    }else{
      var opt = {
        params: {
          pageRef: SavedQueries.pageRef,
          eventId: 'saveQuery',
          isDefault: data.isDefault,
          queryName: encodeURI(qname).replace(/'/g, "\\'"),
          jsonData: encodeURI(Ext.encode(data)).replace(/'/g, "\\'")
        },
        success: this.saveOk.createDelegate(this, [qname, data], 0)
      };
    }
    this.submit(opt);
  }  // private - called when the delete has processed
  ,
  saveOk: function(qname, data, response, options) {
    if (response.status == 200) {
      this.setDisplayQueryName(data.isDefault ? '*' + qname + '*' : qname);
      this.loadMenu.addQuery(qname, data);
    }
    else {
      Ext.MessageBox.show({
        title: this.errorTitleText,
        msg: this.saveErrorText,
        buttons: Ext.Msg.OK,
        icon: Ext.MessageBox.ERROR
      });
    }
  }  // private, submit the json call to the server.
  ,
  submit: function(opt) {
    var conn = new Ext.data.Connection({
      url: SavedQueries.url,
      method: 'POST'
    });
    conn.request(opt);
  },
  
  /** Run the loaded query that is flagged as the default one.
   *  Does nothing if no query is flagged as default
   *  Returns true if default query is found.
   */
  runDefaultQuery: function() {
    var qry = this.getDefaultQuery();
    if (qry) {
      this.runQuery(qry);
      return true;
    }
  },
  
  /**
   * getDefaultQuery
   * if a default query exist it is returned, otherwise returns false
   */
  getDefaultQuery: function() {
    if (this.loadMenu.items) {
      var defaultQry = false;
      this.loadMenu.items.each(function(qry) {
        if (qry.queryData.isDefault) {
          defaultQry = qry;
          return;
        }
      });
      return defaultQry;
    }
    return false;
  },
  
  /**
   * getQuery
   * checks if a named query exist, if it does it is returned, otherwise returns false
   */
  getQuery: function(name) {
    if (this.loadMenu.items) {
      var index = this.loadMenu.items.findIndex('name', name);
    }
    if (index >= 0) {
      return this.loadMenu.items.itemAt(index);
    }
    return false;
  },
  
  /**
   * runQuery
   * runs a specified query and executes the search
   * @params qry the query to run
   */
  runQuery: function(qry) {
    if (qry) {
      this.loadQuery(qry.text, qry.queryData, true);
    }
  },
  
  /** This maps any input parameters (that were passed via the URL)
   *  to any of the criteria fields with the same name.
   * Returns true if any parameter is set from input.
   */
  setParamsToPanel: function(p) {
    if (p == null) return;
    var foundMatchingParams;
    for (var i in p) {
      if (p[i] == null || p[i] == 'null') continue;
      var operator,value;
      if(Ext.isObject(p[i])){
        operator = p[i].operator;
        value = p[i].value ||  p[i].values;
        if(Ext.isArray(value)) value =  value.join(',');
      } else {
        operator = null;
        value = p[i];
      }
      var fld = this.find('mapping', i);
      if (fld && Ext.isArray(fld) && !Ext.isEmpty(fld)) {
        fld = fld[0];
        if (fld.setValue) {
          fld.setValue(value);
        }
        else {
          var valueField = fld.find('isValueField', true);
          if (valueField && Ext.isArray(valueField) && !Ext.isEmpty(valueField)) {
            valueField[0].setValue(value);
          }
          var operatorFld = fld.find('operator', true);
          if (!Ext.isEmpty(operator) && operatorFld &&  Ext.isArray(operatorFld) && !Ext.isEmpty(operatorFld)) {
            operatorFld[0].setValue(operator);
          }
        }
        foundMatchingParams = true;
      }
    }
    return foundMatchingParams;
  },
  
  /** Convert all the fields that have values into a criteria object
   */
  getCriteriaFromPanel: function() {
    var c = {};
    var fields = this.find('isValueField', true);
    if (fields && typeof fields == 'object' && fields.length && fields.length > 0) {
      for (var i = 0; i < fields.length; i++) {
        var fld = fields[i];
        var mapping = fld.mapping;
        var addOp = null, addValue = null;
        if (mapping == null) {
          var valueField = fld;
          if(!valueField.isValid()){
            valueField.focus();
            Ext.MessageBox.alert(this.validationError, this.invalidSearchCriteria);
            return false;
          }
          var ownerField = fld.ownerCt;
          if (ownerField) {
            mapping = ownerField.mapping;
            if (mapping == null) continue;
            var opr = ownerField.find('operator', true);
            if (opr && opr[0] && opr[0].getValue()) {
              addOp = opr[0].getValue();
            }
            if (ownerField.isArray && valueField.getValue()) {
              addValue = [];
              if (addOp == 'Between' || addOp == 'In') {
                if (fld.xtype=='datefield'){
                  addValue.push(valueField.getValue());
                  if (ownerField.toDate) {
                    var toDate = ownerField.toDate;
                    addValue.push(toDate.getValue());
                  }
                }else{
                  var tmp = valueField.getValue().split(',');
                  for (var k = 0; k < tmp.length; k++) {
                    var tmpValue = tmp[k].replace(/^\s+|\s+$/g, '');
                    if (tmpValue.length == 0)
                      tmpValue = null;
                    addValue.push(tmpValue);
                  }
                }
              } else {
                addValue.push(valueField.getValue());
              }
            }
            else if (valueField.getValue()) {
              if (fld.isCriteriaField) {
                addValue = [valueField.getValue()];
                if (valueField.xtype=='datefield' && opr[0].getValue()=='Between'){
                  if (valueField.ownerCt.toDate)
                    addValue.push(valueField.ownerCt.toDate.getValue());
                }
              }
              else 
                addValue = valueField.getValue();
            }
          }
        } else if (fld.xtype == 'finderComboGrid' || (fld.xtype && fld.xtype.indexOf('combo')>=0) || fld.xtype == 'finderCombo') {
          if (fld.getValue()) {
            if (fld.isCriteriaField) {
              var tmp = String(fld.getValue()).split(',');
              for (var k = 0; k < tmp.length; k++) {
                tmp[k] = tmp[k].replace(/\s+$/, '');
              }
              if (tmp.length > 1) addOp = 'In';
              addValue = tmp;
            }
            else addValue = fld.getValue();
          } else if (fld.isXType('finderCombo') && fld.getRawValue()) {
              addValue = fld.getRawValue();
          }
        } else if (fld.getValue()) {
          addValue = fld.getValue();
          if (fld.xtype == 'radiogroup') addValue = addValue.inputValue;
        }
        
        // Need to add somthing?
        if (mapping && (addOp || addValue)) {

          //if(!c[mapping]) c[mapping]={};
          var cf = Jaffa.data.Util.get(c, mapping);
          if (!cf) {
            cf = {};
            Jaffa.data.Util.set(c, mapping, cf);
          }
          //If isCriteriaField add criteria object otherwise just set value
          if (fld.isCriteriaField) {
            if (addOp) cf.operator = addOp;
            if (addValue) cf.values = addValue;
          }
          else if (addValue) Jaffa.data.Util.set(c, mapping, addValue);
          
          
          // For a FlexCriteriaBean, mix-in the name of the associated FlexClass
          if (c.flexCriteriaBean && (typeof c.flexCriteriaBean == 'object') && !c.flexCriteriaBean.dynaClass) {
            var dynaClassName = fld.dynaClass ? fld.dynaClass : (ownerField && ownerField.dynaClass ? ownerField.dynaClass : null);
            if (dynaClassName) c.flexCriteriaBean.dynaClass = {
              name: dynaClassName
            };
          }
        }
      }// for
    }
    this.doGetCriteria(c);
    return c;
  },
  resetPanel: function() {
    // blank out any current query name in the title bar
    this.setDisplayQueryName('');
    
    // Blank out all fields flagged as 'isValueField'
    var fields = this.find('isValueField', true);
    if (fields && typeof fields == 'object' && fields.length && fields.length > 0) {
      for (var i = 0; i < fields.length; i++) {
        var fld = fields[i];
        var mapping = fld.mapping;
        if (mapping == null) {
          var valueField = fld;
          var ownerField = fld.ownerCt;
          if (ownerField) {
            mapping = ownerField.mapping;
            if (mapping == null) continue;
            var opr = ownerField.find('operator', true);
            if (opr && opr[0]) {
              opr = opr[0];
              if (opr.isDirty()) {
                opr.reset();
              }
            }
            if (ownerField.isArray || valueField.isDirty()) {
              valueField.reset();
              if (valueField.xtype == 'datefield' && valueField.ownerCt.toDate){
                valueField.ownerCt.toDate.reset();
                valueField.ownerCt.toDate.hide();
              }
            }
            else if (valueField.xtype == 'datefield') {
              valueField.reset();
              if (valueField.ownerCt.toDate)
                valueField.ownerCt.toDate.reset();
            }
          }
        }
        else if (fld.isDirty()) {
          fld.reset();
        }
      }
    }
    
    this.doResetCriteria();
  }  /** Set the fields on the panel based on the supplied
   * criteria object. This in the reverse of the {getCriteriaFromPanel}
   */
  ,
  setPanelFromCriteria: function(criteria) {
    this.resetPanel();
    if (criteria.criteria) {
      var crt = criteria.criteria;
      for (var i in crt) {
        var fld = undefined;
        if (typeof i == 'string') {
          if (i == 'flexCriteriaBean') {
            var flex = crt.flexCriteriaBean;
            for (var j in flex) {
              fld = this.find('mapping', 'flexCriteriaBean.' + j);
              this.setCriteriaData(flex, fld, j);
            }
          }
          else {
            fld = this.find('mapping', i);
            this.setCriteriaData(crt, fld, i);
          }
        }
      }
    }
    this.doSetCriteria(criteria);
   },
  setCriteriaData: function(crt, fld, i) {
    if (fld && fld[0]) {
      fld = fld[0];
      if (fld instanceof Ext.Container) {
        // this handles the cases that the field is an 'adjacentfield' with operator and value sub-fields.
        if (crt[i].operator) {
          var opr = fld.find('operator', true);
          if (opr && opr[0]) {
            opr = opr[0];
            opr.setValue(decodeURIComponent(crt[i].operator));
            if (decodeURIComponent(crt[i].operator) == 'Between' && fld.find('isValueField', true)[0].xtype == 'datefield' && fld.toDate) {
              fld.toDate.show();
            }
          }
        }
        if (crt[i].values) {
          var vf = fld.find('isValueField', true)[0];
          if (fld.isArray) vf.setValue(decodeURIComponent(crt[i].values));
          else {
            if (vf.xtype == 'datefield') {
              var dt;
              if (typeof crt[i].values[0] == 'object') {
                dt = crt[i].values[0];
              } 
              else if (crt[i].values[0].indexOf('n') == 0 || crt[i].values[0].indexOf('t') == 0 || crt[i].values[0].indexOf('N') == 0 || crt[i].values[0].indexOf('T') == 0) {
                dt = crt[i].values[0];
              }else{
                dt = Date.parseDate(crt[i].values[0], "Y-m-d\\TH:i:s");
              }
              vf.setValue(dt);

              if (crt[i].values.length > 1 && vf.ownerCt.toDate) {
                var td;
                if (typeof crt[i].values[1] == 'object') {
                  td = crt[i].values[1];
                }
                else if (crt[i].values[1].indexOf('n') == 0 || crt[i].values[1].indexOf('t') == 0 || crt[i].values[1].indexOf('N') == 0 || crt[i].values[1].indexOf('T') == 0) {
                  td = crt[i].values[1];
                }
                else {
                  td = Date.parseDate(crt[i].values[1], "Y-m-d\\TH:i:s");
                }
                vf.ownerCt.toDate.setValue(td);
              }
            }
            else vf.setValue(decodeURIComponent(crt[i].values[0]));
          }
        }
      }
      else if (crt[i].values) {
        //Load the finderCombo, before setting it
        if (fld.xtype == 'finderCombo' && !fld.store.isLoaded) {
          fld.setUpBaseParams();
          //register a "single-use" listener to set the value on the combo after it has loaded all the valid values
          fld.store.addListener('load', function(store, records, options) {
            this.setValue(decodeURIComponent(crt[this.mapping].values));
          }, fld, {single: true});
        fld.store.load();
        } else
          fld.setValue(decodeURIComponent(crt[i].values));
      } 
      else if (fld.xtype == 'datefield') {
        if (fld.isCriteriaField) {
          if (typeof crt[i].values[0] == 'object') var dt = crt[i].values[0];
          else var dt = Date.parseDate(crt[i].values[0], "Y-m-d\\TH:i:s");
        } else {
          if (typeof crt[i] == 'object') var dt = crt[i];
          else var dt = Date.parseDate(crt[i], "Y-m-d\\TH:i:s");
        }
        fld.setValue(dt);
      } 
      else {
        fld.setValue(decodeURIComponent(crt[i]));
      }
    }
  },
  setPanelFromDbCriteria: function(criteria) {
      this.resetPanel();
      if (criteria.criteria) {
        var crt = criteria.criteria;
        for (var i in crt) {
          if (typeof i == 'string' && crt[i]) {
            var fld = this.find('mapping', i);
            if (fld && fld[0]) {
              fld = fld[0];
              if (fld instanceof Ext.Container) {
                // this handles the cases that the field is an 'adjacentfield' with operator and value sub-fields.
                if (crt[i].operator) {
                  var opr = fld.find('operator', true);
                  if (opr && opr[0]) {
                    opr = opr[0];
                    opr.setValue(crt[i].operator);
                    if (crt[i].operator=='Between' && fld.find('isValueField', true)[0].xtype=='datefield' && fld.toDate){
                      fld.toDate.show();
                    }
                  }
                }
                if (crt[i].values) {
                  var vf = fld.find('isValueField', true)[0];
                  if (fld.isArray) vf.setValue(crt[i].values);
                  else {
                    if (vf.xtype == 'datefield') {
                      var dt;
                      if (typeof crt[i].values[0] == 'object') {
                        dt = crt[i].values[0];
                      }
                      else if (crt[i].values[0].indexOf('n')==0 || crt[i].values[0].indexOf('t')==0 || crt[i].values[0].indexOf('N')==0 || crt[i].values[0].indexOf('T')==0) {
                        dt = crt[i].values[0];
                      }else{
                        dt = Date.parseDate(crt[i].values[0], "Y-m-d\\TH:i:s");
                      }
                      vf.setValue(dt);

                      if (crt[i].values.length > 1 && vf.ownerCt.toDate) {
                        var td;
                        if (typeof crt[i].values[1] == 'object') {
                          td = crt[i].values[1];
                        }
                        else if (crt[i].values[1].indexOf('n')==0 || crt[i].values[1].indexOf('t')==0 || crt[i].values[1].indexOf('N')==0 || crt[i].values[1].indexOf('T')==0) {
                          td = crt[i].values[1];
                        }
                        else {
                          td = Date.parseDate(crt[i].values[1], "Y-m-d\\TH:i:s");
                        }
                        vf.ownerCt.toDate.setValue(td);
                      }
                    }
                    else vf.setValue(crt[i].values[0]);
                  }
                }
              }
              else if (crt[i].values) {
                //Load the finderCombo, before setting it
                if (fld.xtype == 'finderCombo' && !fld.store.isLoaded) {
                  fld.setUpBaseParams();
                  //register a "single-use" listener to set the value on the combo after it has loaded all the valid values
                  fld.store.addListener('load', function (store, records, options) {
                    this.setValue(crt[this.mapping].values);
                  }, fld, {single: true});
                  fld.store.load();
                } else
                  fld.setValue(crt[i].values);
              }
              else if (fld.xtype == 'datefield') {
                if (fld.isCriteriaField) {
                  if (typeof crt[i].values[0] == 'object') var dt = crt[i].values[0];
                  else var dt = Date.parseDate(crt[i].values[0], "Y-m-d\\TH:i:s");
                }
                else {
                  if (typeof crt[i] == 'object') var dt = crt[i];
                  else var dt = Date.parseDate(crt[i], "Y-m-d\\TH:i:s");
                }
                fld.setValue(dt);
              }
              else {
                fld.setValue(crt[i]);
              }
            }
          }
        }// for
      }
      this.doSetCriteria(criteria);
    }
});

Ext.reg('criteriapanel', Jaffa.finder.CriteriaPanel);

/**
 * @class Jaffa.finder.LoadQueryMenu
 */
Jaffa.finder.LoadQueryMenu = Ext.extend(Ext.menu.Menu, {
  /**
   * @cfg {String} selectedQuery Name of the selected query.
   */
  /**
   * @cfg {Object} containerPanel A {@link Ext.Panel} object that contains this menu.
   * This panel should implement loadQuery().
   */
  constructor: function(config) {
    config = config || {};
    var nameQueryPairs = config.nameQueryPairs || SavedQueries.nameQueryPairs;
    if (config.nameQueryPairs) config.nameQueryPairs = null;
    Jaffa.finder.LoadQueryMenu.superclass.constructor.call(this, config);
    if (nameQueryPairs) {
      for (var i = 0; i < nameQueryPairs.length; i++) {
        this.addQuery(nameQueryPairs[i][0], nameQueryPairs[i][1]);
      }
    }
  }  /**
   * getItem
   * Get the query item by its display name.
   * @param {String} dname, the display query name, ie 'My Query'
   * @return {Ext.menu.TextItem} Item found, or null if not found
   */
  ,
  getItem: function(qname) {
    if (this.items && qname != null && qname != '') {
      var i = this.items.findIndex('name', qname);
      if (i >= 0) return this.items.itemAt(i);
    }
    return null;
  },
  
  /**
   * getItemByDisplayName
   * Get the query item by its display name. Return null if it is not found.
   * @param {String} dname, the display query name, ie '*My Query*'
   * @return {Ext.menu.TextItem} Item found, or null if not found
   */
  getItemByDisplayName: function(dname) {
    if (this.items && dname != null && dname != '') {
      var i = this.items.findIndex('text', dname);
      if (i >= 0) return this.items.itemAt(i);
    }
    return null;
  },
  
  /**
   * getQueryNameByDisplayName
   * Convert a display name to the name of a query.
   * @param {Object} dname, the display name of the query.
   */
  getQueryNameByDisplayName: function(dname) {
    var i = getItemByDisplayName(dname);
    return i == null ? '' : i.name;
  },
  
  /**
   * addQuery
   * Add a query data to the query item store. If it is a default query, it will remove
   * default status on other queries. Then the added query will be loaded to the container.
   *
   * @param {Object} name, name of the query
   * @param {Object} data, query data.
   */
  addQuery: function(name, data) {
    var q = this.getItem(name);
    if (q) {
      q.queryData = data;
      if (data.isDefault) {
        this.setAsDefaultQuery(name);
      }
    }
    else {
      if (data.isDefault && this.items) {
        this.items.each(function(itm) {
          itm.queryData.isDefault = false;
          itm.setText(itm.name);
        });
      }
      this.addMenuItem({
        name: name,
        text: data.isDefault ? '*' + name + '*' : name,
        queryData: data,
        scope: this.finderPanel,
        handler: function(item, evt) {
          console.debug("Selected ", item, evt);
          this.loadQuery(item.text, item.queryData);
          return true;
        }
        /*
         listeners : {
         scope: this.finderPanel,
         'click' : function(item, evt) {
         console.debug("Selected ",item,evt);
         this.loadQuery(item.text, item.queryData);
         return true;
         }
         }*/
      });
    }
  },
  
  /**
   * removeQuery
   * Removes the query from the item store.
   * @param {Object} name, the name of the query.
   * @param {Object} scope, the store.
   */
  removeQuery: function(name, scope) {
    scope = scope || this;
    var i = scope.items ? scope.items.findIndex('name', name) : -1;
    if (i >= 0) {
      scope.remove(scope.items.itemAt(i));
    }
    else {
      alert(this.alertMsgQueryMsgText+" "+ name + " "+this.alertMsgNotInTheListMsgText);
    }
  },
  
  
  /**
   * setAsDefaultQuery
   * Set a query to default query and remove default query status from other queries.
   * @param {Object} qname, name of the query.
   */
  setAsDefaultQuery: function(qname) {
    if (this.items) {
      this.items.each(function(itm) {
        if (itm.name == qname) {
          itm.queryData.isDefault = true;
          itm.setText('*' + itm.name + '*');
        }
        else {
          itm.queryData.isDefault = false;
          itm.setText(itm.name);
        }
      }, this);
    }
  }
});

Jaffa.finder.CriteriaFieldPanel = Ext.extend(Ext.Container, {
  invalidNumberMsg: 'Invalid number format.',
  constructor: function(config) {
      config = Ext.apply({}, config);
      config.fieldType = config.fieldType || 'textfield';
      config.hasCombo = config.hasCombo == null ? true : config.hasCombo;
      config.bodyBorder = config.bodyBorder || false;
      config.bodyStyle = config.bodyStyle || 'background-color:#DFE8F6';
      config.layout = 'adjacentfield';
      config.frame = config.frame || false;
      config.style = config.style || 'overflow:hidden;';
      config.cls = config.cls || 'x-form-item'; 
      if (config.cls.indexOf('x-form-item')<0) config.cls += ' x-form-item';
      if (config.style.indexOf('overflow')<0) config.style += 'overflow:hidden;';
      config.isSearchPanel = true;
      var metaClass = config.metaClass && (Ext.isString(config.metaClass) ? ClassMetaData[config.metaClass] : config.metaClass);
      var meta = metaClass && metaClass.fields && metaClass.fields[config.metaField || config.mapping];
      if(meta && meta.type === 'number'){
        config.fieldType = 'numberfield'
      }
      if (config.fieldType == 'textfield') {
        if (config.hasCombo) {
          config.items = [Jaffa.finder.CriteriaOptionsFactory.getAllCriteriaCombo({
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            metaField: config.metaField || config.mapping,
            operator: true,
            store: config.store ? config.store : null,
            excludeNull: config.excludeNull ? config.excludeNull : false
          }), {
            xtype: config.fieldType,
            adjacentSeparator: '',
            noLabel: true,
            metaClass: config.metaClass,
            metaField: config.metaField || config.mapping,
            validationEvent: false,
            isValueField: true, // used to label the value fields for search
            isCriteriaField: true, // builds criteria object rather than string during getCriteriaFromPanel
            value: config.value || null
          }];
          config.isArray = config.isArray == null ? true : config.isArray;
        }
        else {
          config.items = [{
            xtype: config.fieldType,
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            adjacentSeparator: '',
            validationEvent: false,
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: true,
            value: config.value || null
          }];
        }
      }
      else if (config.fieldType == 'numberfield') {
        if (config.hasCombo) {
          config.items = [Jaffa.finder.CriteriaOptionsFactory.getNumericCriteriaCombo({
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            metaField: config.metaField || config.mapping,
            operator: true,
            store: config.store ? config.store : null,
            excludeNull: config.excludeNull ? config.excludeNull : false,
            listeners:{
              select: function(cbo, newVal, oldVal){
                var valueField = cbo.ownerCt.findBy(function(item){
                  return item.isValueField === true;
                })[0];
                if(valueField){
                  valueField.validate();
                }
              }
            }
          }), {
            xtype: 'textfield',
            adjacentSeparator: '',
            noLabel: true,
            metaClass: config.metaClass,
            metaField: config.metaField || config.mapping,
            //validationEvent: false, ------- removed so that stripCharsRe is enforced
            allowDecimals: config.allowDecimals,
            isValueField: true, // flag used to label the value fields for search
            isCriteriaField: true, // builds criteria object rather than string during getCriteriaFromPanel
            value: config.value || null,
            validator : function(val){
              var operator= this.ownerCt.findBy(function(item){
                return item.operator === true;
              })[0];
              if(operator && val){
                var regex;
                if(operator.getValue() === 'In'){
                  regex = config.allowDecimals ? (/^([0-9.]+(,[0-9.]+)?)+$/) : (/^([0-9]+(,[0-9]+)?)+$/);
                } else if(operator.getValue() === 'Between'){
                  regex = config.allowDecimals ? (/^[0-9.]+(,[0-9.]+)?$/) : (/^[0-9]+(,[0-9]+)?$/);
                } else {
                  regex =   config.allowDecimals ? (/^[0-9.]+$/) : (/^[0-9]+$/);
                }
                if (regex && !regex.test(val)) {
                  return this.ownerCt.invalidNumberMsg;
                }
              }
              return true;
            }
          }];
          config.isArray = config.isArray == null ? true : config.isArray;
        }
        else {
          config.items = [{
            xtype: 'textfield',
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            adjacentSeparator: '',
            validationEvent: false,
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: true,
            value: config.value || null
          }];
        }
      }
      else if (config.fieldType == 'datefield') {
        if (config.hasCombo) {
          config.items = [Jaffa.finder.CriteriaOptionsFactory.getDateCriteriaCombo({
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            metaField: config.metaField || config.mapping,
            operator: true,
            excludeNull: config.excludeNull ? config.excludeNull : false,
            listeners: {
              select: function(combo, record, index){
                if (combo.ownerCt.toDate) {
                  if (record.get('value')=='Between'){
                    this.ownerCt.toDate.show();
                  }else{
                    combo.ownerCt.toDate.setValue('');
                    this.ownerCt.toDate.hide();
                  }
                }
              },
              change: function(combo, newValue, oldValue){
                if (combo.ownerCt.toDate) {
                  if (newValue == 'Between') {
                    combo.ownerCt.toDate.show();
                  }
                  else {
                    combo.ownerCt.toDate.setValue('');
                    combo.ownerCt.toDate.hide();
                  }
                }
              }
            }
          }), {
            xtype: config.fieldType,
            adjacentSeparator: '',
            noLabel: true,
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: true,
            value: config.value || null,
            ref: 'fromDate'
          }, {
            xtype: config.fieldType,
            adjacentSeparator: '',
            noLabel: true,
            metaField: config.metaField || config.mapping,
            isCriteriaField: true,
            value: config.value || null,
            hidden: true,
            ref: 'toDate'
          }];
          config.isArray = config.isArray == null ? false : config.isArray;
        }
        else {
          config.items = [{
            xtype: config.fieldType,
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            adjacentSeparator: '',
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: true,
            value: config.value || null
          }];
        }
      }
      else if (config.fieldType == 'checkbox') {
        if (config.useCombo == false) {
          config.items = [{
            xtype: config.fieldType,
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            adjacentSeparator: '',
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: config.isCriteriaField===false ? false : true,
            value: config.value || null
          }];
        }
        else {
          config.items = [Jaffa.finder.CriteriaOptionsFactory.getBooleanCriteriaCombo({
            metaClass: config.metaClass,
            fieldLabel: config.fieldLabel,
            fieldLabelToken: config.fieldLabelToken,
            validationEvent: false,
            metaField: config.metaField || config.mapping,
            isValueField: true,
            isCriteriaField: true,
            value: config.value || ''
          })];
        }
      }
      if (config.tailIcon) {
        config.items.push({
          xtype: 'label',
          html: '<div class="' + config.tailIcon + '">&nbsp;</div>',
          adjacentSeparator: ''
        });
        delete config.tailIcon;
      }
      delete config.fieldLabel;
      delete config.fieldLabelToken;
    Jaffa.finder.CriteriaFieldPanel.superclass.constructor.call(this, config);
    this.labelWidth = this.labelWidth || this.ownerCt.labelWidth || 150;
  }

  ,focusFirstField: function() {
    if (this.rendered && this.isVisible()) {
      var ff = this.find('isValueField', true)[0];
      if (ff && ff.rendered && ff.isVisible()) {
        ff.focus();
        return ff;
      }
    }
  }
});
Ext.reg('finderCriteriaFieldPanel', Jaffa.finder.CriteriaFieldPanel);

Jaffa.finder.CriteriaPanelFactory = function() {
  return {
    /*
     * Config.isArray==true indicates that the value of this field can be comma delimited list of values. On the
     * server side, this field is handled by org.jaffa.components.finder.CriteriaField. Otherwise, the field will
     * be handled by java primitive data types on the serverside.
     * @param {Object} config
     */
    createPanel: function(config) {
      config = Ext.apply({}, config);
      config.fieldType = config.fieldType || config.xtype; // backward compatibility to the code uses xtype for fields
      config.xtype = 'finderCriteriaFieldPanel';
      return config;
    }    /**
     * createMaxRecordsPanel
     * Creates a Panel containing the maxRecords combo
     * @param {Object} config
     */
    ,
    createMaxRecordsPanel: function(config) {
      config = config || {};
      config.bodyBorder = config.bodyBorder || false;
      config.layout = 'form';
      config.frame = config.frame || false;
      config.height = 25;
      config.isSearchPanel = true;
      config.items = [{
        xtype: 'combo',
        mapping: 'objectLimit',
        fieldLabel: this.maxRecordsTitleText,
        mode: 'local',
        displayField: 'label',
        valueField: 'value',
        typeAhead: true,
        triggerAction: 'all',
        forceSelection: true,
        width: 100,
        value: 50,
        store: new Ext.data.SimpleStore({
          fields: ['label', 'value'],
          data: [['25', 25], ['50', 50], ['100', 100], ['250', 250], ['500', 500], ['1000', 1000], ['2500', 2500], ['5000', 5000]]
        })
      }];
      return new Ext.Panel(config);
    }
  };
}();


/**
 * @class Jaffa.finder.FlexCriteria
 * A utility for flex criteria fields.
 */
Jaffa.finder.FlexCriteria = function() {
  return {
    /**
     * Creates a FieldSet containing flex criteria fields.
     * Nothing is created if the metaClass is not passed in the config,
     * or if the metaClass doesn't contain any flexInfo.
     * @param {Object} config At the bare minimum, the config should contain at least the metaClass element.
     */
    createFieldSet: function(config) {
      // Do not create anything if the metaClass is not passed, or if the metaClass doesn't contain any flexInfo
      var metaClass = config ? config.metaClass : null;
      var meta = metaClass ? (typeof metaClass == 'string' ? ClassMetaData[metaClass] : metaClass) : null;
      if (!meta || !meta.flexInfo || meta.flexInfo.length == 0) return null;
      
      // config for the FieldSet
      config = Ext.applyIf(config || {}, {
        title: Labels.get('label.common.FlexFields'),
        layout: 'formdescription',
        collapsible: true,
        collapsed: true,
        autoHeight: true
      });
      
      // defaults for each flex field
      config.defaults = Ext.applyIf(config.defaults || {}, {
        bodyStyle: 'padding-bottom: 1px; padding-right: 10px',
        border: false,
        labelWidth: config.labelWidth ? config.labelWidth : 150
      });
      
      // add fields for each element of the flexInfo to the config
      config.items = [];
      for (var i = 0; i < meta.flexInfo.length; i++) {
        var flexClass = meta.flexInfo[i].source;
        if (!flexClass) continue;
        
        // load metadata for the flex class
        // @todo: load the metadata asynchronously and build the panel in the response handler
        var metaSource = Ext.Ajax.synchronousRequest({
          url: 'js/extjs/jaffa/metadata/classMetaData.jsp',
          params: {
            className: flexClass,
            outputStyle: "JSON"
          }
        });
          if (metaSource) {
              var decoded = Ext.decode(metaSource);
              let start = flexClass.lastIndexOf(".") + 1;
              var indexName = flexClass.substring(start, flexClass.length);
              ClassMetaData[indexName] = decoded;
          }
        
        // Retain only the simple name from the fully-qualified flex class name
        flexClass = flexClass.split('.');
        flexClass = flexClass[flexClass.length - 1];
        
        // Pass the flexClass as the metaClass and dynaClass to each flex field
        Ext.apply(config.defaults, {
          metaClass: flexClass,
          dynaClass: meta.flexInfo[i].source
        });
        
        // add each field of the flex class to config.items
        // @todo: Incorporate display-sequence and display-group from the corresponding property-info rule
        for (var fieldName in ClassMetaData[flexClass].fields) {
          var fieldMeta = ClassMetaData[flexClass].fields[fieldName];
          if (fieldMeta.hidden) continue;
          
          //determine xtype based on datatype
          var xtype;
          switch (fieldMeta.type) {
            case 'string':
              xtype = 'textfield';
              break;
            case 'number':
              //numberfield isn't supported by getCriteriaFromPanel() at the moment
              //xtype = 'numberfield';
              xtype = 'textfield';
              break;
            case 'dateonly':
              xtype = 'datefield';
              break;
            case 'datetime':
              //xdatetime isn't supported by getCriteriaFromPanel() at the moment
              //xtype = 'xdatetime';
              xtype = 'datefield';
              break;
            case 'boolean':
              xtype = 'checkbox';
              break;
            default:
              xtype = 'textfield';
          }
          
          // create a config for a criteria field and apply defaults
          var fieldConfig = {
            mapping: 'flexCriteriaBean.' + fieldName,
            metaField: fieldName,
            xtype: xtype,
            excludeNull: true
          };
          if(fieldMeta.type === 'number'){
            fieldConfig.allowDecimals = fieldMeta.allowDecimals;
          }
          Ext.applyIf(fieldConfig, config.defaults);
          
          // Add a panel containing the field and corresponding operator-dropdown
          config.items.push(Jaffa.finder.CriteriaPanelFactory.createPanel(fieldConfig));
        }
      }

      //If there is no flex fields then return null and which will not show the empty flex field section in criteria screen.
      if(config && config.items && config.items==0){
         return null;
      }

      // create the FieldSet
      return new Ext.form.FieldSet(config);
    }
  };
}();
