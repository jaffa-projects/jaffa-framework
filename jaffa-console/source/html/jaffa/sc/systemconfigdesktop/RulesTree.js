Jaffa.SC.RulesTree = Ext.extend(Ext.tree.TreePanel, {
  constructor: function(config){
    config = Ext.applyIf(config || {}, {
      titleToken: 'label.Jaffa.SC.SystemConfigDesktop.accordionTitle',
      split: true,
      width: 280,
      minSize: 175,
      maxSize: 500,
      collapsible: true,
      margins: '0 0 5 5',
      cmargins: '0 0 0 0',
      lines: false,
      autoScroll: true,
      animCollapse: false,
      animate: false,
      collapseMode: 'mini',
      root: new Ext.tree.TreeNode({
        text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.accordionTitle')
      }),
      collapseFirst: false,
      tbar:[
        new Ext.form.TextField({
          width: 200,
          emptyText:Labels.get('label.Jaffa.SC.SystemConfigDesktop.searchEmptyText'),
          enableKeyEvents: true,
          listeners:{
            render: function(f){
              this.filter = new Ext.tree.TreeFilter(this, {
                clearBlank: true,
                autoClear: true
              });
            },
            keydown: {
              fn: this.filterTree,
              buffer: 350,
              scope: this
            },
            scope: this
          }
        })
      ],
      hiddenPkgs: []
    });
    Jaffa.SC.RulesTree.superclass.constructor.call(this, config);
    this.on(
      'afterlayout', function(panel){
        if (!Jaffa.SC.RulesLoader.isLoaded)
          panel.body.mask(Labels.get('label.Jaffa.Mask.Loading.smallCaps'),'x-mask-loading');
      }, this, {single:true}
    );
  },

  requestTree: function(){
    if (!Jaffa.SC.RulesLoader.isLoaded){
      Jaffa.SC.RulesLoader.on('afterload',function(success){
        if (success)
          this.loadTree();
        else
          this.body.unmask();
      }, this, {single:true});
      Jaffa.SC.RulesLoader.load();
    } else {
      this.loadTree();
    }
  },

  filterTree: function(t, e){
    var text = t.getValue();
    Ext.each(this.hiddenPkgs, function(n){
      n.ui.show();
    });
    if(!text){
      this.filter.clear();
      this.collapseAll();
      this.root.expand(false);
      return;
    }
    this.expandAll();

    var re = new RegExp(Ext.escapeRe(text), 'i');
    this.root.cascade(function(n) {
      if (re.test(n.attributes.path)) {
        n.filtermatch=true;
      }else{
        n.filtermatch=false;
/*        if (n.attributes.rules){
          for (var i = 0; i < n.attributes.rules.length; i++) {
            var rule = n.attributes.rules[i];
            if (re.test(rule))
              n.filtermatch=true;
          }
        }*/
      }
      if (n.filtermatch){
        n.bubble(function(b){
          b.filtermatch=true;
        });
      }
    });

    // hide empty packages that weren't filtered
    this.hiddenPkgs = [];
    var me = this;

    this.root.cascade(function(n){
      if (!n.filtermatch) {
        n.ui.hide();
        me.hiddenPkgs.push(n);
      }
    });
  },
  // private
  initComponent: function(){
    Jaffa.SC.RulesTree.superclass.initComponent.call(this);
    new Ext.tree.TreeSorter(this, {
      folderSort: true
    });
    this.on({
      render: function(){
        if (!this.collapsed){
          if (!Jaffa.SC.BusinessRules){
            this.requestTree();
          }
        } else {
          this.on({
            expand: function(){
              if (!Jaffa.SC.BusinessRules){
                this.requestTree();
              }
            }
          })
        }
      }
    }, this);
  },

  loadTree: function(response, options){
    if (this.body)
      this.body.unmask();
    this.loaded=true;

    //Build tree based on unique rules
    for (property in Jaffa.SC.BusinessRules.uniqueRuleSet) {
      var pList = property.toLowerCase().split('.');
      var parentNode = this.getRootNode();
      var path = '';
      for (var i = 0; i < pList.length - 1; i++) {
        path = i > 0 ? path + '.' + pList[i] : pList[i];
        var childNode = parentNode.findChild('value', pList[i]);
        if (childNode) {
          parentNode = childNode;
        }
        else {
          parentNode = parentNode.appendChild(new Ext.tree.TreeNode({
            text: pList[i],
            value: pList[i],
            iconCls: 'copy',
            path: path,
            count: 0
          }));
        }
        if (i == pList.length - 2) {
          parentNode.attributes.count = parentNode.attributes.count + 1;
          if (!parentNode.attributes.rules)parentNode.attributes.rules=[];
          parentNode.attributes.rules[parentNode.attributes.rules.length]=pList[pList.length-1];
          parentNode.text = parentNode.attributes.value + ' (' + parentNode.attributes.count + ')';
        }
      }
    }
  },
  loaderror: function(){
    alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.FailedLoadBusinessRuleDataRefreshScreenAlertMsg'));
  },
  loadAnnoError: function(){
    alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.FailedLoadBusinessRuleMetaDataRefreshScreenAlertMsg'));
  }
});
