Jaffa.SC.RulesDetailPanel = Ext.extend(Ext.grid.EditorGridPanel, {

  closable: true,

  initComponent : function(){
    this.ruleRecord = Jaffa.data.Record.create([{
      name: 'rule',
      type: 'string'
    },{
      name: 'classPath',
      type: 'string'
    },{
      name: 'ruleName',
      type: 'string'
    },{
      name: 'label',
      type: 'string'
    },{
      name: 'description',
      type: 'string'
    },{
      name: 'currentValue',
      type: 'string'
    },{
      name: 'defaultValue',
      type: 'string'
    },{
      name: 'metaData'
    }]);

    var expander = new Ext.ux.grid.RowExpander({
      tpl : new Ext.Template(
        '<p><b>' + Labels.get('label.Jaffa.SC.SystemConfigDesktop.description') + ':</b> {description}</p>'
      )
    });

    this.ruleRenderer =  function(value, metadata, record, rowIndex, colIndex, store){
      var meta;
      if (record && record.get('metaData')){
        meta = record.get('metaData');
      } else {
        return value;
      }

      if (meta.inList){
        var valueList = value.split(',');
        var renderedList = [];
        for (var i = 0; i < valueList.length; i++){
          for (var j = 0; j < meta.inList.length; j++){
            if (meta.inList[j][0] == valueList[i])
              renderedList[renderedList.length] = meta.inList[j][1];
          }
        }
        value = renderedList.join(', ');
      }

      metadata.css = 'editable';

      return value;
    };
    
    var tbar;
    if (this.enableSearch)
      tbar = [
          '-',
          {xtype:'textfield', emptyText:Labels.get('label.Jaffa.SC.SystemConfigDesktop.searchValue'),ref:'searchField',
            listeners : {
            "specialkey": function(field, ev){
                if(ev.getKey() == ev.ENTER){
                    ev.preventDefault();
                    this.ownerCt.ownerCt.search(this.getValue());
                  }
              },
            keydown: {
                fn: function(){
                    this.ownerCt.ownerCt.search(this.getValue());
                  },
                buffer: 350
              }
          }
      },{
          itemId:'searchCount',
              text: Labels.get('label.Jaffa.Widgets.Button.Search') + ': 0 '+Labels.get('label.Common.of')+' 0'
        }
    ];

    Ext.apply(this, {
      title: this.path?this.path:Labels.get('label.Jaffa.SC.SystemConfigDesktop.mainTabTitle'),
      id: this.path?this.path:'apprules',
      baseTitle: this.path?this.path:Labels.get('label.Jaffa.SC.SystemConfigDesktop.mainTabTitle'),
      plugins: expander,
      disableSelection: true,
      contentType: 'BUSINESSRULES',
      tbar: tbar,
      columns: [expander,{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.rule'),
        dataIndex: 'rule',
        width : 300,
        sortable: true
      },{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.classPath'),
        dataIndex: 'classPath',
        width : 200,
        sortable: true
      },{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.ruleName'),
        dataIndex: 'ruleName',
        width : 150,
        sortable: true
      },{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.label'),
        dataIndex: 'label',
        width : 150,
        sortable: true
      },{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.defaultValue'),
        dataIndex: 'defaultValue',
        width : 200,
        sortable: true,
        renderer: this.ruleRenderer
      },{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.current'),
        dataIndex: 'currentValue',
        width : 200,
        sortable: true,
        renderer: this.ruleRenderer
      }],
      ds: new Ext.data.Store({
        scope: this,
        local: true,
        remoteSort: false,
        sortInfo: {
          field: 'rule',
          direction: "ASC"
        },
        reader: new Ext.data.JsonReader({idProperty : 'rule'}, this.ruleRecord)
      }),
      listeners:{
        'cellclick': {
          fn: function (grid, rowIndex, columnIndex, e){
            if (grid.activeEditor){ 
                return false;
            }
            if (grid.getColumnModel().getColumnAt(columnIndex).dataIndex!=='defaultValue' && grid.getColumnModel().getColumnAt(columnIndex).dataIndex !== 'currentValue'){
                return;
            }
            var record = grid.getStore().getAt(rowIndex);
            var _editor = "";
            if (record.get('metaData') && record.get('metaData').readOnly === true){
                return false;
            }
            var fieldConfig = this.buildEditor(record);
            _editor = new Ext.grid.GridEditor(Ext.ComponentMgr.create(fieldConfig));
            grid.getColumnModel().setEditor(columnIndex,_editor);
          }
        }
      }
    })

    Ext.grid.EditorGridPanel.superclass.initComponent.call(this);

    if (this.path) this.loadFromPath(this.path);

    this.store.on('update', function(store, record, operation){
      //this.controller.setDirty(true);
      //this.setDirty(true);
      if (operation=='edit'){
        this.controller.updateRule(this.id, record.get('rule'),record.get('varRule'),record.get('currentValue'));
      }
    }, this);

    this.on(
      'afterlayout', function(panel){
        if (!Jaffa.SC.RulesLoader.isLoaded){
          this.body.mask(Labels.get('label.Jaffa.Mask.Loading.smallCaps'),'x-mask-loading');
          Jaffa.SC.RulesLoader.on('afterload',function(success){
            this.body.unmask();
          }, this, {single:true});
          Jaffa.SC.RulesLoader.load();
        }
      }, this, {single:true}
    );
  },
  loadFromPath: function(path){
    this.path = path;
    this.store.removeAll();
    var tableProperties = {};
    for (var property in Jaffa.SC.BusinessRules.uniqueRuleSet) {
      var prop = property.toLowerCase();
      if (prop.indexOf(path + '.') == 0) {
        if (prop.substring(path.length + 1).split('.').length == 1) {
          this.addRuleToStore(property,this.store);
        }
      }
    }
  },
  addRuleToStore: function(property, store){
    var propMeta = this.getMetaData(property);
    var propArray = property.split('.');
    var defaultValue = '';
    if (propMeta){
      if (propMeta.defaultValue) defaultValue = propMeta.defaultValue;
      if (propMeta.type=='object' && propMeta.inList){
        var splitDefaults = defaultValue.split(",");
        for (var i = 0;i<splitDefaults.length;i++){
          var foundItem = false;
          for (var j = 0; (j<propMeta.inList.length) && !foundItem; j++){
            if (propMeta.inList[j][0]==splitDefaults[i]){
              splitDefaults[i] = propMeta.inList[j][1];
              foundItem = true;
            }
          }
        }
        defaultValue=splitDefaults.join(",");
      }else if (propMeta.inList){
        var foundItem = false;
        for (var i = 0; (i<propMeta.inList.length) && !foundItem; i++){
          if (propMeta.inList[i][0]==defaultValue){
            defaultValue = propMeta.inList[i][1];
            foundItem = true;
          }
        }
      }

    }
    store.add(new this.ruleRecord({
      rule:property,
      classPath:propArray.slice(0,(propArray.length>1?propArray.length-1:0)).join('.'),
      ruleName:(propArray.length>1?propArray[propArray.length-1]:property),
      label:(propMeta&&propMeta.label)?propMeta.label:(propArray.length>1?propArray[propArray.length-1]:property),
      //varRule:Jaffa.SC.BusinessRules.varRules[property],
      currentValue: Jaffa.SC.BusinessRules.varRules[property]? Jaffa.SC.BusinessRules.varRules[property] : Jaffa.SC.BusinessRules.globalRules[property],
      defaultValue: defaultValue,
      description: (propMeta&&propMeta.annotation)?propMeta.annotation:'',
      metaData: propMeta}));
  },
  search: function(searchString){
    this.searchString = searchString;
    this.topToolbar.searchField.setValue(this.searchString);
    if (this._dirty){
      Ext.MessageBox.show({
        title : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SearchWarning'),
        msg : Labels.get('label.Jaffa.SC.SystemConfigDesktop.SearchWarningMessage'),
        width : 400,
        buttons : Ext.MessageBox.YESNO,
        icon : Ext.MessageBox.QUESTION,
        scope: this,
        fn : function(btn) {
          if (btn == 'yes') {
            //this.setDirty(false);
            this.search(searchString);
          }
        }
      });
      return false;
    }
    this.store.removeAll();
    
    var searchCount  = 0;
    var totalCount  = 0;
    this.getTopToolbar().getComponent('searchCount').setText(Labels.get('label.Jaffa.Widgets.Button.Search') + ': ' + searchCount + ' of ' + totalCount);

    if (searchString!=''){
      searchString = searchString.toLowerCase();
      var tableProperties = {};
      for (var property in Jaffa.SC.BusinessRules.uniqueRuleSet) {
        var prop = property.toLowerCase();
        var meta = this.getMetaData(property);
        var searchLimit = 50;
        if (prop.indexOf(searchString) >= 0) {
          totalCount = totalCount + 1;
          if (totalCount <= searchLimit){
            searchCount = searchCount + 1;
            this.addRuleToStore(property,this.store);
          }
        } else if (meta && meta.label && meta.label.toLowerCase().indexOf(searchString) >= 0){
          totalCount = totalCount + 1;
          if (totalCount <= searchLimit){
            searchCount = searchCount + 1;
            this.addRuleToStore(property,this.store);
          }
        } else if (meta && meta.annotation && meta.annotation.toLowerCase().indexOf(searchString) >= 0){
          totalCount = totalCount + 1;
          if (totalCount <= searchLimit){
            searchCount = searchCount + 1;
            this.addRuleToStore(property,this.store);
          }
        }
      }

      this.getTopToolbar().getComponent('searchCount').setText(Labels.get('label.Jaffa.Widgets.Button.Search') + ': ' + searchCount + ' of ' + totalCount);

      if (this.store.sortInfo && this.store.sortInfo.field && this.store.sortInfo.direction)
        this.store.sort(this.store.sortInfo.field,this.store.sortInfo.direction);
    }
  },
  getMetaData: function(property){
    if (ClassMetaData.BusinessRules.fields[property]){
      return ClassMetaData.BusinessRules.fields[property];
    }else{
      var propArray = property.split('.');
      for (var i=0;i<propArray.length-1;i++){
        var p = property.split('.');
        p[i] = '*';
        if ( ClassMetaData.BusinessRules.fields[p.join('.')])
          return ClassMetaData.BusinessRules.fields[p.join('.')];
      }

      var p = property.split('.');
      p = p[p.length-1];
      if (ClassMetaData.BusinessRules.fields['**.' + p])
        return ClassMetaData.BusinessRules.fields['**.' + p];
    }
    return null;
   },
   buildEditor: function (record) {
        var meta = record.get('metaData'),config = {};
        config.xtype = 'textfield';

        if (meta && meta.inList) {
            config.store = meta.inList;
            config.minChars = 0;
            config.triggerAction = 'all';
            config.initialized = 'all';
            config.maxHeight = 150;
            config.resizable = true;
        }
        return config;
   },
   _dirty: false,
   setDirty: function(dirty) {
     if (dirty && !this._dirty){
       this.setTitle(this.baseTitle + '*');
       this._dirty = true;
     }
     if (!dirty) {
       this.store.commitChanges();
       this._dirty = false;
       this.setTitle(this.baseTitle);
     }
   },
   refresh: function(){
     this.setDirty(false);
     if (this.path){
       this.loadFromPath(this.path);
     }else if (this.searchString){
       this.search(this.searchString);
     }
     if (this.store.sortInfo && this.store.sortInfo.field && this.store.sortInfo.direction){
       this.store.sort(this.store.sortInfo.field,this.store.sortInfo.direction);
     }
   }
});
