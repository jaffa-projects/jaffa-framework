Jaffa.SC.FlexTreeRecord = new Jaffa.data.Record.create([
  {name: '_id', displayOnly:true, key:true},
  {name: '_parent', type: 'auto', displayOnly:true},
  {name: '_is_leaf', type: 'bool', displayOnly:true},
  {name: 'hasChildren', defaultValue: true, displayOnly: true, type: 'boolean'},
  {name: 'iconCls', defaultValue: 'field', displayOnly: true},
  {name: 'nodeType', defaultValue: 'flexfield', displayOnly: true},
  {name: 'name'},
  {name: 'label'},
  {name: 'mapping'},
  {name: 'condition'},
  {name: 'parameters'},
  {name: 'extendsClass'},
  {name: 'extendsProperty'},
  {name: 'language'}
]);

Jaffa.SC.FlexTreeRecord.prototype.initData = function(data, id, o){
  if (data.condition && data.condition!="") data.iconCls = data.iconCls + "-cond";
};

Jaffa.SC.FlexTreeRecord.getId = function(rec){
  return rec["_id"];
};

Jaffa.SC.FlexDetailTree = Ext.extend(Ext.ux.maximgb.treegrid.GridPanel, {
  initComponent:function() {
    Ext.apply(this, {
      store: new Ext.ux.maximgb.treegrid.AdjacencyListStore({
        reader: new Jaffa.data.DWRTreeGridReader({
          getRecordType: function(o){
            return Jaffa.SC.FlexTreeRecord;
          }
        }),
        recordType: Jaffa.SC.FlexTreeRecord,
        remoteStore:false
      })
      ,plugins: new Ext.ux.plugins.MetaColumns({columns:[{
        header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.Name'),
        dataIndex: 'name',
        id:'name',
        width: 500,
        renderer: function(value, metadata, record, row, col, store){
          var template = ""
          if (record.get('nodeType')=='flexfield'){
            store.each(function(rec){
              if (rec.get('_parent')==record.get('_id') && rec.get('name')=='label'){
                if (Ext.util.JSON.decode(rec.get('parameters'))['value']!=""){
                  template = Ext.util.JSON.decode(rec.get('parameters'))['value'];
                  return true;
                }
              }
            })
          }else{
            if (record.get('name') && RuleMetaData && RuleMetaData[record.get('name')] && RuleMetaData[record.get('name')].ruleTemplate){
              template = RuleMetaData[record.get('name')].ruleTemplate;
            }

            while (template.indexOf('[') >= 0 && template.indexOf(']')> 0 && template.indexOf(']')>template.indexOf('[') ){
              var startIndex = template.indexOf('[');
              var endIndex = template.indexOf(']');
              var preString = template.substring(0,startIndex);
              var endString = template.substring(endIndex + 1);
              var token = template.substring(startIndex + 1, endIndex);
              var replaceString = Ext.util.JSON.decode(record.get('parameters'))[token]||"";
              template = preString + replaceString + endString;
            }
          }
          if (record.get('condition') && record.get('condition')!=""){
            if (record.get("nodeType")=="flexfield"){
              record.set("iconCls", "field-cond");
            }else{
              record.set("iconCls", "rule-cond");
            }
          }

          return record.get('label') + (template!=""?(" (" + template + ")"):"");
        }
      }], record:Jaffa.SC.FlexTreeRecord})
      ,root: new Ext.tree.AsyncTreeNode({
        text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFields')
      })
    });
    Ext.ux.maximgb.treegrid.GridPanel.superclass.initComponent.call(this);

    this.getSelectionModel().on('rowselect',
      function(sel, index, row){
        this.ownerCt.onRowClick(this, index);
      },
      this
    );

  }
  ,master_column_id: 'name'
  ,bodyCfg: null
  ,rootVisible: true
  ,autoScroll: true
  ,header: false
  ,containerScroll: true
  ,frame: false
  ,border: false
  ,layout: 'fit'
  ,removeRecord: function(record){
    this.getStore().remove(record);
  }
});

Ext.reg('flexDetailTree', Jaffa.SC.FlexDetailTree);