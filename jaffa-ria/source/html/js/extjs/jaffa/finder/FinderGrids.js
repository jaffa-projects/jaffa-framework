/**
 * @author PaulE
 */
Jaffa.finder.MultiGroupingPagingFinderGrid = function(config){

  if (config.plugins) {
    if (Ext.isArray(config.plugins)) {
      config.plugins.push(new Ext.ux.plugins.MetaColumns({
        columns: config.columns,
        record: config.store.recordType || config.store.reader.recordType,
        filter: true
      }));
    }
    else {
      config.plugins = [new Ext.ux.plugins.MetaColumns({
        columns: config.columns,
        record: config.store.recordType || config.store.reader.recordType,
        filter: true
      }), config.plugins];
    }
  }
  else {
    config.plugins = new Ext.ux.plugins.MetaColumns({
      columns: config.columns,
      record: config.store.recordType || config.store.reader.recordType,
      filter: true
    });
  }
  delete config.columns;

  config = Ext.applyIf(config, {
    //id :'finder-grid'
    autoScroll: true,
    frame: true,
    loadMask: true,
    forceFit: false,
    collapsible: false,
    disabled: true
  });

  Jaffa.finder.MultiGroupingPagingFinderGrid.superclass.constructor.call(this, config);

  // We don't want the filtered column highlighted when initially rendered
  this.filters.clearFilters();

  this.store.on("load", function(){
    if (this.disabled)
      this.enable();
  }, this);

  // Fix bug in extjs where toolbar doesn't move when grid is resized.
  if (Ext.isIE){
    this.on("bodyResize", function(bbar, width, height ){
      if (this.bbar && !this.bbar.hidden){
        this.bbar.hide();
        this.bbar.show();
      }
    });
  }
};

Ext.extend(Jaffa.finder.MultiGroupingPagingFinderGrid, Ext.ux.grid.MultiGroupingPagingGrid, {
	
  getResultGraphRules: function(){
    // Get this list of display columns
    // Use this to build the resultGraphRules parameter
    var results = [];
    var check = {};
    var cm = this.colModel.config;
    for (var i = 0; i < cm.length; i++) {
      var di = cm[i].dataIndex;
      var meta = this.store.recordType.getField(di);
      var f = (meta && meta.mapping) ? meta.mapping : di;
      if (!f) continue;
      if (!check[f]) {
        results[results.length] = f;
        check[f] = true;
      }
      // Bit of a hack to include the object in the list
      // This is because a explicit ref to a foriegn object is not enough
      var li = f.lastIndexOf('.');
      if (li > 0) {
        f = f.slice(0, li);
        if (!check[f]) {
          results[results.length] = f;
          check[f] = true;
        }
      }
    }
    return results;
  }
});

Ext.reg('multigroupingpagingfindergrid', Jaffa.finder.MultiGroupingPagingFinderGrid);

Ext.ux.grid.PagingGrid = function(config){
  config = config||{};
  config.bbar = config.bbar || [];
  config.bbar = config.bbar.concat([{
    xtype: 'tbfill'
  }, {
    xtype: 'tbtext',
    itemId: 'counter',
    text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbfill')
  }, {
    xtype: 'tbspacer'
  }, {
    xtype: 'tbbutton',
    itemId: 'loading',
    hidden: true,
    disabled: true,
    iconCls: "x-tbar-loading"
  }, {
    xtype: 'tbseparator'
  }, {
    xtype: 'tbbutton',
    itemId: 'more',
    text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbseparator'),
    handler: function(){
      this.store.loadMore(false);
    },
    scope: this
  } // FIXME: Label Token
  ]);

  Ext.ux.grid.PagingGrid.superclass.constructor.apply(this, arguments);

  // Create Event that asks for more data when we scroll to the end
  this.on("bodyscroll", function(){
    var s = this.view.scroller.dom;
    if ((s.offsetHeight + s.scrollTop + 5 > s.scrollHeight) && !this.isLoading) {
      console.debug("Grid.on.bodyscroll: Get more...");
      this.store.loadMore(false);
    }
  }, this);

  // When the grid start loading, display a loading icon    
  this.store.on("beforeload", function(store, o){
    if (this.isLoading) {
      console.debug("Store.on.beforeload: Reject Load, one is in progress");
      return false;
    }
    this.isLoading = true;
    if (this.rendered) {
      this.barLoading.show();
    }
    console.debug("Store.on.beforeload: options=", o, this);
    return true;
  }, this);

  // When loading has finished, disable the loading icon, and update the row count
  this.store.on("load", function(){
    delete this.isLoading;
    if (this.rendered) {
      this.barLoading.hide();
      console.debug("Store.on.load: Finished loading.. ", this.store.totalCount);
      var showText = Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.Showing')+" "+ this.store.getCount() + " "+ Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.of')+" ";
      if (isNaN(this.store.totalCount)){
        showText+='?';
      } else{
        var allowLoadAll =  Rules.get('Jaffa.widgets.grid.allowLoadAll');
        if ((allowLoadAll === true) && this.store.totalCount > this.store.getCount() && this.getStore().loadAll){
          var loadAllLink = '<a href="javascript:void(0)" id="barCounterLoadAll" class="x-form-label-override" >' + this.store.totalCount + '</a>';
          showText+=loadAllLink;
        }else{
          showText+=this.store.totalCount;
        }
      }
      this.barCounter.setText(showText);
      if (Ext.fly('barCounterLoadAll'))
        Ext.fly('barCounterLoadAll').on('click', function() {
          this.getStore().loadAll();
        }, this);

      if (this.store.totalCount === this.store.getCount()) {
        this.barMore.disable();
      } else {
        this.barMore.enable();
      }
    }
    return true;
  }, this);

  // When a loading error occurs, disable the loading icon and display error
  this.store.on("loadexception", function(store, e){
    console.debug("Store.loadexception.Event:", arguments);
    delete this.isLoading;
    if (this.rendered) {
      this.barLoading.hide();
    }
    if (e)
      Ext.Msg.show({
        titleToken: 'title.jaffa.jaffaRIA.finder.FinderGrids.ShowDetails',
        msg: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.ErrorLoadingRecordsMsg')+" " + e,
        buttons: Ext.Msg.OK,
        icon: Ext.MessageBox.ERROR
      });
    return false;
  }, this);

  // As the default onLoad to refocus on the first row has been disabled,
  // This has been added so if a load does happen, and its an initial load
  // it refocuses. If this is a refresh caused by a sort/group or a new page
  // of data being loaded, it does not refocus
  this.store.on("load", function(r, o){
    if (o && o.initial == true)
      Ext.ux.grid.PagingGrid.superclass.onLoad.call(this);
  }, this.view);

  // Fix bug in extjs where toolbar doesn't move when grid is resized.
  if (Ext.isIE){
    this.on("bodyResize", function(bbar, width, height ){
      if (this.bbar && !this.bbar.hidden){
        this.bbar.hide();
        this.bbar.show();
      }
    });
  }

};

Ext.extend(Ext.ux.grid.PagingGrid, Ext.grid.GridPanel, {
  onRender: function(ct, position){
    Ext.ux.grid.PagingGrid.superclass.onRender.call(this, ct, position);
    var bb = this.getBottomToolbar();
    this.barCounter = bb.items.itemAt(bb.items.length - 5);
    this.barMore = bb.items.itemAt(bb.items.length - 1);
    this.barLoading = bb.items.itemAt(bb.items.length - 3);
  }
});

/**
 * @author PaulE
 */
Jaffa.finder.PagingFinderGrid = function(config){

  if (config.plugins) {
    if (Ext.isArray(config.plugins)) {
      config.plugins.push(new Ext.ux.plugins.MetaColumns({
        columns: config.columns,
        record: config.store.recordType,
        filter: true
      }));
    }
    else {
      config.plugins = [new Ext.ux.plugins.MetaColumns({
        columns: config.columns,
        record: config.store.recordType,
        filter: true
      }), config.plugins];
    }
  }
  else {
    config.plugins = new Ext.ux.plugins.MetaColumns({
      columns: config.columns,
      record: config.store.recordType,
      filter: true
    });
  }
  delete config.columns;

  config = Ext.applyIf(config, {
    autoScroll: true,
    frame: true,
    loadMask: true,
    forceFit: false,
    collapsible: false,
    disabled: true,
    view: new Ext.grid.GridView({onLoad: function(grid,data,p){
      if (p && p.initial==true){
        if (Ext.isGecko) {
          if (!this.scrollToTopTask) {
            this.scrollToTopTask = new Ext.util.DelayedTask(this.scrollToTop, this);
          }
          this.scrollToTopTask.delay(1);
        } else {
          this.scrollToTop();
        }
      }
    }})
  });

  Jaffa.finder.PagingFinderGrid.superclass.constructor.call(this, config);

  // We don't want the filtered column highlighted when initially rendered
  this.filters.clearFilters();

  this.store.on("load", function(){
    if (this.disabled)
      this.enable();
  }, this);
};

Ext.extend(Jaffa.finder.PagingFinderGrid, Ext.ux.grid.PagingGrid, {
  getResultGraphRules: function(){
    // Get this list of display columns
    // Use this to build the resultGraphRules parameter
    var results = [];
    var check = {};
    var cm = this.colModel.config;
    for (var i = 0; i < cm.length; i++) {
      var di = cm[i].dataIndex;
      var meta = this.store.recordType.getField(di);
      var f = (meta && meta.mapping) ? meta.mapping : di;
      if (!f) continue;
      if (!check[f]) {
        results[results.length] = f;
        check[f] = true;
      }
      // Bit of a hack to include the object in the list
      // This is because a explicit ref to a foriegn object is not enough
      var li = f.lastIndexOf('.');
      if (li > 0) {
        f = f.slice(0, li);
        if (!check[f]) {
          results[results.length] = f;
          check[f] = true;
        }
      }
    }
    return results;
  }
});

Ext.reg('pagingfindergrid', Jaffa.finder.PagingFinderGrid);


