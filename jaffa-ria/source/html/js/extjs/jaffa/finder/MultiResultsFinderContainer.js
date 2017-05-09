/**
 * A finderContainer that houses multiple results tabs. 
 * The data loading of each tab could come from a store or a controller.
 *
 * If the controller/store on the panel has id property defined, the controller/store will be registered to 
 * the criteria panel using addResultsPanel(). Upon search, the registered controller/store will be loaded. 
 * Use removeResultsPanel() to remove the panel. This method unregisters the controller/store. 
 *
 * Controller/store without id property defined has to be loaded by the panel. 
 *
 * Important: Assumes no sharing of store nor controller.
 * 
 * @author Sean Zhou, Jan. 2012
 * 
 * 
 */
Jaffa.finder.MultiResultsFinderContainer = Ext.extend(Ext.Panel, {
  initComponent: function() {
    // adding layout configuration
    Ext.applyIf(this.criteriaPanel, {region:'west'});
    Ext.applyIf(this.resultsPanel, {
      autoScroll: true,
      frame: true,
      loadMask: true,
      forceFit: false,
      collapsible: false,
      disabled: true
      ,disabled: true
      ,activeTab: 0
    });
    if (this.resultsWrapperPanel) {
      Ext.applyIf(this.resultsWrapperPanel, {region: 'center'});
    } else {
      Ext.applyIf(this.resultsPanel, {region: 'center'});
    }
    
    // create the results and criteria panels
    for (var i=0; i<this.resultsPanel.items.length; i++) {
      if (!this.resultsPanel.items[i].id) this.resultsPanel.items[i].id = this.id+'-resultsPanel-'+i;
    }
    this.resultsPanel = Ext.ComponentMgr.create(this.resultsPanel, Ext.TabPanel);
    this.criteriaPanel = Ext.ComponentMgr.create(this.criteriaPanel, Ext.Panel);
    if (this.resultsWrapperPanel) {
      this.resultsWrapperPanel.items.push(this.resultsPanel);
      this.resultsWrapperPanel = Ext.ComponentMgr.create(this.resultsWrapperPanel);
    }
    
    // ordering the panels to border layout
    Ext.applyIf(this, {
      layout: 'border',
      items:[this.criteriaPanel, this.resultsWrapperPanel|| this.resultsPanel]
    });
    if (this.layout=='') this.layout = 'border';
    
    Jaffa.finder.MultiResultsFinderContainer.superclass.initComponent.call(this);
    
    this.criteriaPanel.stores = [];
    this.criteriaPanel.controllers = [];
    this.criteriaPanel.resultsPanel = this.resultsPanel;
    this.criteriaPanel.on('afterrender', function(){
      this.criteriaPanel.hasInitCriteria = this.criteriaPanel.setParamsToPanel(this.criteriaPanel.criteria);
    },this,{single : true});
    
    for (var i=0; i<this.resultsPanel.items.getCount(); i++) {
      var panel = this.resultsPanel.getComponent(i);
      if (panel instanceof Ext.grid.GridPanel) {
        var grid = panel;
        var store = grid.store;
        if (!store) continue;
        this.criteriaPanel.stores.push(store);
        if (!store) continue;
        // setting resultsGraphRules
        if (typeof grid.getResultGraphRules == 'function' && grid.getResultGraphRules().length>0) {
          store.staticBaseParams = store.staticBaseParams || {};
          store.staticBaseParams.resultGraphRules = grid.getResultGraphRules();
          // add the id fields
          var ids = Jaffa.data.Util.get(grid, 'store.reader.meta.idProperty');
          if (Ext.isArray(ids) && ids.length>0) {
            for (var j=0; j<ids.length; j++) {
              if (store.staticBaseParams.resultGraphRules.indexOf(ids[j])<0)
                store.staticBaseParams.resultGraphRules.push(ids[j]);
            }
          }
        }
        // toggle excel button
        store.on('load', function (store, data, criteria) {
          if (store.totalCount > 0) {
            if (this.getBottomToolbar()) {
              var bottomTb = this.getBottomToolbar();
              if (bottomTb && bottomTb.getComponent('excel') && bottomTb.getComponent('excel').disabled)
                bottomTb.getComponent('excel').enable();
            }
          }
        }, grid);
        grid.on('render', function (resultPanel) {
          if (resultPanel.getBottomToolbar()) {
            if (resultPanel.getBottomToolbar().getComponent('more'))
              resultPanel.getBottomToolbar().getComponent('more').disable();
            if (resultPanel.getBottomToolbar().getComponent('excel'))
              resultPanel.getBottomToolbar().getComponent('excel').disable();
          }
        });
      } else {
        var cntrl = panel.controller;
        if (!cntrl) continue;
        this.criteriaPanel.controllers.push(cntrl);
        
      }
    }
  },
  
  setCriteriaButtons: function(){
    if (this.rendered) {
      this.getBottomToolbar().items.get('search').hide();
      this.getBottomToolbar().items.get('next').show();
      this.getBottomToolbar().items.get('viewlist').show();
    }
    else {
      var tb = this.getBottomToolbar();
      for (var i = 0; i < tb.length; i++) {
        if (tb[i].id == 'search') 
          tb[i].hidden = true;
        if (tb[i].id == 'next') 
          tb[i].hidden = false;
        if (tb[i].id == 'viewlist') 
          tb[i].hidden = false;            }
    }      
  },
  
  setResultButtons: function(){
    if (this.rendered) {
      this.getBottomToolbar().items.get('search').show();
      this.getBottomToolbar().items.get('next').hide();
      this.getBottomToolbar().items.get('viewlist').hide();
    }
    else {
      var tb = this.getBottomToolbar();
      for (var i = 0; i < tb.length; i++) {
        if (tb[i].id == 'search') 
          tb[i].hidden = false;
        if (tb[i].id == 'next') 
          tb[i].hidden = true;
        if (tb[i].id == 'viewlist') 
          tb[i].hidden = true;
      }
    }      
  }
  
  /**
   *  If the controller/store on the panel has id property defined, the controller/store will be registered to the criteria panel. Upon search, 
   *  the controller/store registered on the criteria panel will be loaded. 
   */
  ,addResultsPanel: function(panel) {
    var newPanel = false;
    if (panel.controller && panel.controller.id) {
      for (var i=0; i<this.criteriaPanel.controllers.length; i++) {
        if (panel.controller.id == this.criteriaPanel.controllers[i].id) return;
      }
      this.criteriaPanel.controllers.push(panel.controller);
      newPanel = true;
    } else if (panel.store && panel.store.id) {
      for (var i=0; ithis.criteriaPanel.stores.length; i++) {
        if (panel.store.id == this.criteriaPanel.stores[i].id) return;
      }
      this.criteriaPanel.stores.push(panel.store);
      newPanel = true;
    } else if (!panel.store && !panel.controller) {
      newPanel = true;
    }
    if (newPanel) this.resultsPanel.add(panel);
  }
  
  /**
   *  If the controller/store on the panel has id property defined, the controller/store will be unregistered from the criteria panel. 
   */
  ,removeResultsPanel: function(panel) {
    if (panel.controller) {
      for (var i=0; i<this.criteriaPanel.controllers.length; i++) {
        if (panel.controller == this.controllers[i]) {
          this.criteriaPanel.controllers.splice(i,1);
          break;
        }
      }
    } else if (panel.store) {
      for (var i=0; i<this.criteriaPanel.stores.length; i++) {
        if (panel.store == this.criteriaPanel.stores[i]) {
          this.criteriaPanel.stores.splice(i,1);
          break;
        }
      }
    }
    this.resultsPanel.remove(panel);
  }
});
Ext.reg('multiresultsfindercontainer', Jaffa.finder.MultiResultsFinderContainer);
