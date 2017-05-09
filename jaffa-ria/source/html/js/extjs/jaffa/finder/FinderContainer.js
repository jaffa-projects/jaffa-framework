/**
 * @author BobbyC
 */
Jaffa.finder.FinderContainer = function(config) {
  config = config||{};

  config.criteriaPanel.store = config.store||{};
  config.resultsPanel.store = config.store||{};
  config.criteriaPanel.region = config.criteriaPanel.region||'west';
  config.resultsPanel.region = 'center';
  if (!config.resultsPanel.id) {
    // make sure the results panel is assigned an id so that the user adjusted column width is persisted.
    config.resultsPanel.id = config.id + '-resultsPanel';
  }
  Ext.applyIf(config.resultsPanel,{disabled: true});
  this.resultsPanel = Ext.ComponentMgr.create(config.resultsPanel, Ext.Panel);
  config.criteriaPanel.grid = this.resultsPanel;
  this.criteriaPanel = Ext.ComponentMgr.create(config.criteriaPanel, Ext.Panel);
  this.criteriaPanel.on('afterrender', function(){
    this.criteriaPanel.hasInitCriteria = this.criteriaPanel.setParamsToPanel(config.criteria);
  },this,{single : true});

  var resultGraphRules = this.resultsPanel.getResultGraphRules();
  if (resultGraphRules.length > 0) {
    if (!this.criteriaPanel.baseParams)this.criteriaPanel.baseParams={};
    Ext.applyIf(this.criteriaPanel.baseParams, {
      resultGraphRules: resultGraphRules
    });
  }

  delete config.resultsPanel;
  delete config.criteriaPanel;

  config = Ext.apply(config, {
    layout: config.layout||'border',
    items:[this.criteriaPanel,this.resultsPanel]
  });
  
  config.store.on('load', function (store, data, criteria) {
    if (store.totalCount > 0) {
      if (this.resultsPanel && this.resultsPanel.getBottomToolbar()) {
        var bottomTb = this.resultsPanel.getBottomToolbar();
        if (bottomTb && bottomTb.getComponent('excel') && bottomTb.getComponent('excel').disabled)
          bottomTb.getComponent('excel').enable();
      }
    }
  }, this);
  
  this.resultsPanel.on('render', function (resultPanel) {
    if (resultPanel.getBottomToolbar()) {
      if (resultPanel.getBottomToolbar().getComponent('more'))
        resultPanel.getBottomToolbar().getComponent('more').disable();
      if (resultPanel.getBottomToolbar().getComponent('excel'))
        resultPanel.getBottomToolbar().getComponent('excel').disable();
    }
  }, this);
  
  if (config.layout && (config.layout=='card'||config.layout=='slide')){
    this.criteriaPanel.collapsible= false;
    this.criteriaPanel.collapse = function(){
      this.layout.setActiveItem(1);
    }.createDelegate(this);
    
    var critTools = this.criteriaPanel.getTopToolbar();
    for (var i = 0; i < critTools.items.length; i++){
      if (critTools.items.itemAt(i).iconCls == 'search') {
        critTools.items.itemAt(i).hidden = true;
        critTools.items.itemAt(i-1).hidden = true;
      }
    };
    
    this.criteriaPanel.keys.fn = function(){
      this.criteriaPanel.search(); 
    }.createDelegate(this);
    
    config = Ext.apply(config, {
      activeItem: 0,
      bbar: [
      {
        id: 'search',
        text: this.searchAgainButtonText,
        scope: this,
        handler: function(){
          this.layout.setActiveItem(0);
          this.setCriteriaButtons();
        },
        hidden: true
      }, '->',
      {
        id: 'next',
        text: this.searchButtonText,
        scope: this,
        handler: function(){
          this.criteriaPanel.search();
        }
      },{
        id: 'viewlist',
        text: this.viewListButtonText,
        scope: this,
        handler: function(){
          this.layout.setActiveItem(1);
          this.setResultButtons();
        }
      }]

    });
    
    // Add a listener to the load event of the store, to render the Grid and appropriate result-buttons
    this.resultsPanel.store.on('load', function (store, records, options) {
      this.layout.setActiveItem(1);
      this.setResultButtons();
    }, this);
    
    // The collapse() method is invoked programmatically by CriteriaPanel.search() to render the Grid.
    // To correct the Grid rendering in ExtJS-3.1.2, the following overrides the collapse() method and does nothing.
    // It is left to the 'load' listener to render the Grid.
    this.criteriaPanel.collapse = function (animate) {
    }.createDelegate(this);
  }
  
  Jaffa.finder.FinderContainer.superclass.constructor.call(this, config);
  
};

Ext.extend(Jaffa.finder.FinderContainer, Ext.Panel, {
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
});
Ext.reg('findercontainer', Jaffa.finder.FinderContainer)
