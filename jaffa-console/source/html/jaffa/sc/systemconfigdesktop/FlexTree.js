Jaffa.SC.FlexTree = Ext.extend(Ext.tree.TreePanel, {
  constructor: function(config){
    config = Ext.applyIf(config || {}, {
      title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.accordionTitle'), //Flex Fields
      split: true,
      width: 280,
      minSize: 175,
      maxSize: 500,
      collapsible: true,
      loaded: false,
      margins: '0 0 5 5',
      cmargins: '0 0 0 0',
      lines: false,
      autoScroll: true,
      animCollapse: false,
      animate: false,
      collapseMode: 'mini',
      root: new Ext.tree.TreeNode({
        text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexRoot')
      }),
      rootVisible: false
    });
    Jaffa.SC.FlexTree.superclass.constructor.call(this, config);
    this.on(
      'afterlayout', function(panel){
        if (!panel.loaded)
          panel.body.mask(Labels.get('label.Jaffa.Mask.Loading'),'x-mask-loading');
      }
    )
  },

  requestTree: function(){
    if (!this.hasInitiated) {
      //If component has not been initiated, call helper class to get list of flex-enabled classes
      var flexObjectService = new Ext.data.DWRProxy(Jaffa_Rules_MetaDataReader.getFlexClass);

      flexObjectService.load(null,
        {read: function(r){return r;}},
        this.loadtree,
        this
      );

      this.loadFlexMeta();
    }
  },

  // private
  initComponent: function(){
    Jaffa.SC.FlexTree.superclass.initComponent.call(this);
    this.hasInitiated = false;
    new Ext.tree.TreeSorter(this, {
      folderSort: true
    });
    this.on({
      render: function(){
        if (!this.collapsed){
          if(!Jaffa.SC.FlexRules){
            this.requestTree();
          }
        } else {
          this.on({
            expand: function(){
              if (!Jaffa.SC.FlexRules){
                this.requestTree();
              }
            }
          })
        }
      }
    }, this);
  },

  //Builds flex tree using information returned from helper getFlexClass method
  loadtree: function(response, options){
    if (this.body)
      this.body.unmask();
    this.loaded = true;
    var flexObjects = eval(response);
    Jaffa.SC.FlexRules = flexObjects;
    //Build tree based on flex objects
    if (flexObjects && flexObjects.length && flexObjects.length > 0){
      for (var i = 0; i<flexObjects.length;i++) {
        var flexObject = flexObjects[i];
        var parentNode = this.getRootNode();

        parentNode = parentNode.appendChild(new Ext.tree.TreeNode({
          text: flexObject.domainName + " (" + flexObject.flexName + ")",
          flexObject: flexObject,
          iconCls: 'copy'
        }));
      }
    }
  },

  //Load flex field meta data
  loadFlexMeta: function(){
    //parse RuleMetaData and build panels for editing each rule
    for (var rule in RuleMetaData){
      var multiProps = false;
      var propertyPanelFields = [];
      for (var prop in RuleMetaData[rule].properties){
        propertyPanelFields[propertyPanelFields.length]={
          mapping: prop,
          metaClass: {fields:RuleMetaData[rule].properties},
          metaField: prop,
          paramField: true,
          metaData: RuleMetaData[rule].properties[prop]
        };

        if (multiProps){
          RuleMetaData[rule].multiProps = true;
        }
        multiProps = true;
      }
      propertyPanelFields[propertyPanelFields.length] = {
        mapping: 'condition',
        xtype: 'textfield',
        fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields.Condition'),
        metaField: 'condition',
        ref:'condition',
        width: 350
      };
      RuleMetaData[rule].panel = {
          xtype: 'panel',
          layout: 'form',
          ruleName: rule,
          bodyBorder: false,
          labelWidth: 150,
          bodyStyle: 'padding:5px 5px 0',
          defaultType: 'textfield',
          items: propertyPanelFields
        };
    }
  }
});
