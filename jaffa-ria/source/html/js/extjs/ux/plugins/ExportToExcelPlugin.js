/**
 * @class Ext.ux.plugins.ExportToExcelPlugin
 * @author BobbyC, SeanZ
 * @param {Object} config Options object
 * @constructor
Example
<code>
new Ext.grid.GridPanel({
  ...
  plugins: new Ext.ux.plugins.ExportToExcelPlugin({
    sheetName: Labels.get('title.Material.Transaction.PickListMonitor'),
    serviceClassName:'com.mirotechnologies.material.transaction.apis.PickListService'
    ,criteriaClassName:'com.mirotechnologies.material.transaction.apis.data.PickListCriteria'
    ,detailServiceClassName:'com.mirotechnologies.material.transaction.apis.PickListRequestService'
    ,detailCriteriaClassName:'com.mirotechnologies.material.transaction.apis.data.PickListRequestCriteria'
    ,masterKeyFieldNames: '(key field name)' or [(key field names)]
    // need to place some dummy key values in child grid store.baseParams to tell wehter the key fields
    //     in String or StringCriteria field format in the child criteria object.
    // need one of the following three thins
    // 1) need to add child grid as childGrid to the results panel before the button is clicked.
    // 2) ,getChildGrid: function() {}
    // 3) ,childColumnModel: []
  }
  ...
});
</code>

 */



// Ext.ux.plugins.exportToExcelObjectLimit
// Variable to define the number of records that should be exported to excel by default
Ext.ux.plugins.exportToExcelObjectLimit=5000;

// Ext.ux.plugins.ExportToExcel
// Plugin adds a button to the bottom toolbar on a grid. This button will export
// the contents of the grid to an excel document.
// If the grid is using a paging plugin, the full set of records will be retrieved and
// sent to the excel doc.
Ext.ux.plugins.ExportToExcelPlugin = function(config) {
  var plg = {
    init: function(grid) {
      var excelButton = {
        xtype: 'button',
        text: Labels.get('label.jaffaRIA.Button.ExportToExcel')
       ,iconCls: "excel"
       ,itemId: 'excel'
       ,scope:grid
       ,handler: function(item) {
          if(this.disabled) return;
          // constructing the object class to export
          var cfg = null;
          if (this.getColumnConfig) {
            cfg = this.getColumnConfig();
          }
          var cm = this.getColumnModelForExportToExcel(this, cfg);
          var criteria = Ext.apply({}, this.store.baseParams);
          criteria.objectLimit=Ext.ux.plugins.exportToExcelObjectLimit;
          var form = Ext.DomHelper.append(document.body, {
            tag:'form',
            method:'POST',
            action : params.appCtx + (config.jspPath?config.jspPath:'/js/extjs/jaffa/finder/exportToExcel.jsp')
          });
          if (config.sheetName) Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'sheetName', value:config.sheetName});
          Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'serviceClassName', value:config.serviceClassName});
          if (config.serviceClassMethodName)
            Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'serviceClassMethodName', value:config.serviceClassMethodName});
          Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'criteriaClassName', value:config.criteriaClassName});
          Ext.DomHelper.append(form, {
            tag:'input', type:'hidden',
            name:'criteriaObject'
          }).value = Ext.util.JSON.encode(criteria);
          Ext.DomHelper.append(form, {
            tag:'input', type:'hidden',
            name:'columnModel'
          }).value = Ext.util.JSON.encode(cm);

          // constructing the children object class to export
          if (!grid.childGrid && Ext.isFunction(grid.getChildGrid)) grid.childGrid = grid.getChildGrid();
          if (this.childGrid) {
            cfg = null;
            if (Ext.isFunction(this.getChildColumnConfig)) {
              cfg = this.getChildColumnConfig();
            }
            var chldcm = this.getColumnModelForExportToExcel(this.childGrid, cfg);
            var detailCriteria = Ext.apply({}, this.childGrid.getStore().baseParams);
            detailCriteria.objectLimit = 500;
            delete detailCriteria.groupBy;
            // handling master key fields
            if (Ext.isString(config.masterKeyFieldNames)) config.masterKeyFieldNames = [config.masterKeyFieldNames];
            for (var i=0; i<config.masterKeyFieldNames.length; i++) {
              if (Ext.isString(detailCriteria[config.masterKeyFieldNames[i]])) {
                detailCriteria[config.masterKeyFieldNames[i]] = '{'+i+'}';
              } else if (Ext.isArray(detailCriteria[config.masterKeyFieldNames[i]])){
                detailCriteria[config.masterKeyFieldNames[i]] = {values:['{'+i+'}']};
              } else {
                Ext.MessageBox.alert(Labels.get('title.jaffaRIA.Error'), Labels.get('label.jaffaRIA.MessageBox.SimpleExportToExcelPlugin.KeyFieldMsg')+': '+config.masterKeyFieldNames[i]+' '+Labels.get('label.jaffaRIA.MessageBox.SimpleExportToExcelPlugin.NeedsDefinedBaseParamsChildStoreMsg'));
                return;
              }
            }

            Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'detailServiceClassName', value:config.detailServiceClassName});
            Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'detailCriteriaClassName', value:config.detailCriteriaClassName});
            Ext.DomHelper.append(form, {
              tag:'input',
              type:'hidden',
              name:'detailCriteria'
            }).value = Ext.util.JSON.encode(detailCriteria);
            Ext.DomHelper.append(form, {
              tag: 'input',
              type: 'hidden',
              name: 'masterKeyFieldNames'
            }).value = Ext.util.JSON.encode(config.masterKeyFieldNames);
            Ext.DomHelper.append(form, {
              tag:'input', type:'hidden',
              name:'detailColumnModel'
            }).value = Ext.util.JSON.encode(chldcm);
          }

          form.submit();
          setTimeout(function(){Ext.removeNode(form);}, 100);
        }
    };

      if (grid.rendered){
        if (!grid.bbar) grid.bbar = new Ext.Toolbar();
          grid.bbar.addButton(excelButton);
      } else if (Ext.isArray(grid.bottomToolbar)) {
        grid.bottomToolbar.splice(0,0,excelButton);
      } else if (grid.bottomToolbar){
        grid.bottomToolbar.insertButton(0, excelButton);
      } else {
        this.elements += ',bbar';
        grid.bottomToolbar = grid.createToolbar(excelButton);
      }

      if (Ext.isFunction(config.getColumnConfig)) {
        grid.getColumnConfig = config.getColumnConfig;
      }
      if (config.childGrid) grid.childGrid = config.childGrid;
      if (Ext.isFunction(config.getChildGrid)) grid.getChildGrid = config.getChildGrid;

      grid.getColumnModelForExportToExcel = this.getColumnModelForExportToExcel;
    }
    ,getColumnModelForExportToExcel: function(grid, cmConfig){
      var cfg = cmConfig || grid.getColumnModel().config;
      var cm = [];
      for (var i = 0; i < cfg.length; i++) {
        var col = cfg[i];
        var meta = grid.store.reader.recordType.getField(col.dataIndex);
        if (meta && (col.hidden != true || (grid.store.groupField && grid.store.groupField.indexOf(col.dataIndex) >= 0)))
          cm.push({
          header: (col.label&&col.label!='')?col.label:col.header,
          mapping: meta.mapping || meta.name,
          layout: col.layout || ''
        });
      }
      return cm;
    },
    exportToExcelEnabled: true
  };
  if (Ext.isFunction(config.getColumnConfig)) plg.getColumnConfig = config.getColumnConfig;
  return plg;
};

Ext.preg('exporttoexcel', Ext.ux.plugins.ExportToExcelPlugin);
