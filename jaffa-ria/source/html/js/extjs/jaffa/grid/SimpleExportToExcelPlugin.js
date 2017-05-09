/**
 * @class Jaffa.grid.plugins.SimpleExportToExcelPlugin
 * @author BobbyC
 * @param {Object} config Options object
 * @constructor
 Example
 <code>
 new Ext.grid.GridPanel({
 ...
 plugins: new Jaffa.grid.plugins.SimpleExportToExcelPlugin({
 sheetName:'My Records' //Name of the generated worksheet
 ,excelName:'MyExcel' //Name of the generated workbook
 ,insertToolbar: 'TOP' //'TOP' or 'BOTTOM', expects bbar or tbar on grid
 ,insertIndex: 0 //index where button should be inserted
 }
 ...
 });
 </code>

 */

// Ext.ux.plugins.SimpleExportToExcel
// Plugin adds a button to a grid. This button will export the contents of the grid to an excel document.
// Plugin will send the exact contents as viewed at the time of clicking.
Ext.ns('Jaffa.grid.plugins');
Jaffa.grid.plugins.SimpleExportToExcelPlugin = function(config) {
  return {
    init: function(grid) {
      var excelButton = {
        xtype: 'button',
        text: config.text || Labels.get('label.jaffaRIA.Button.ExportToExcel')
        ,iconCls: "excel"
        ,itemId: 'excel'
        ,scope:grid
        ,sheetName:config.sheetName||Labels.get('label.jaffaRIA.ExcelSheetName.GridExport')
        ,excelName:config.excelName||Labels.get('label.jaffaRIA.ExcelSheetName.GridExport')
        ,handler: function(item) {
          if(this.disabled) return;
          // constructing the object class to export
          var exportColumns = [];
          var exportRecords = [];
          var columns = this.getColumnModel().config;

          var headers = [];

          var tables = this.getView().mainHd.query('.x-grid3-header-offset > table'), tw = this.getView().getTotalWidth(), rows = this.getView().cm.rows;
          for(var row = 0; row < tables.length; row++){
              tables[row].style.width = tw;
              header = [];
              if(rows && row < rows.length){
                  var cells = tables[row].firstChild.firstChild.childNodes;
                  for(var i = 0, gcol = 0; i < cells.length; i++){
                      var group = rows[row][i];
                      if((typeof col != 'number') || (col >= gcol && col < gcol + group.colspan)){

                        var width = 0, hidden = true, colCount = 0;
                        for(var j = gcol, len = gcol + group.colspan; j < len; j++){
                          if(!this.getView().cm.isHidden(j)){
                            var cw = this.getView().cm.getColumnWidth(j);
                            if(typeof cw == 'number'){
                              width += cw;
                            }
                            hidden = false;
                            colCount=colCount+1;
                          }
                        }
                        if (colCount > 0)
                          header[header.length]={colspan:colCount,header:group.header||""};
                      }
                      gcol += group.colspan;
                  }
                  headers[headers.length]=header;
              }
          }



          for (var i = 0; i < columns.length; i++){
            if (columns[i].dataIndex && columns[i].dataIndex!='' && !columns[i].hidden){
              exportColumns[exportColumns.length]={header:columns[i].header,dataIndex:columns[i].dataIndex, width:columns[i].width, type:columns[i].type};
            }
          }
          this.getStore().data.each(function(rec){
            var record = {};
            for (var j = 0;j < exportColumns.length;j++){
              if (exportColumns[j].type=='boolean'){
                if (rec.get(exportColumns[j].dataIndex)===true)
                  record[exportColumns[j].dataIndex]='TRUE';
                else if (rec.get(exportColumns[j].dataIndex)===false)
                  record[exportColumns[j].dataIndex]='FALSE';
                else
                  record[exportColumns[j].dataIndex]='';
              }else {
                record[exportColumns[j].dataIndex]=rec.get(exportColumns[j].dataIndex);
              }
            }
            exportRecords[exportRecords.length]=record;
          });

          var exportData = {
            headers:headers,
            columns:exportColumns,
            records:exportRecords
          };

          var form = Ext.DomHelper.append(document.body, {
            tag:'form',
            method:'POST',
            action : params.appCtx + '/js/extjs/jaffa/grid/simpleExportToExcel.jsp'
          });
          Ext.DomHelper.append(form, {
            tag:'input', type:'hidden',
            name:'jsonToExport'
          }).value = Ext.util.JSON.encode(exportData);
          Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'sheetName', value:item.sheetName});
          Ext.DomHelper.append(form, {tag:'input', type:'hidden', name:'excelName', value:item.excelName});

          form.submit();
          setTimeout(function(){Ext.removeNode(form);}, 100);
        }
      };


      if (config.insertToolbar!='BOTTOM'){
        if (grid.rendered){
          if (!grid.tbar) grid.tbar = new Ext.Toolbar();
          grid.tbar.addButton(excelButton);
        } else if (Ext.isArray(grid.topToolbar)) {
          if (config.insertIndex >= 0)
            grid.topToolbar.splice(config.insertIndex,0,excelButton);
          else
            grid.topToolbar.splice(grid.topToolbar.length,0,excelButton);
        } else if (grid.topToolbar) {
          if (config.insertIndex >= 0)
            grid.topToolbar.insertButton(config.insertIndex, excelButton);
          else
            grid.topToolbar.insertButton(grid.topToolbar.items.length, excelButton);
        } else {
          grid.elements += ',tbar';
          grid.topToolbar = grid.createToolbar([excelButton]);
        }
      }else{
        if (grid.rendered){
          if (!grid.bbar) grid.bbar = new Ext.Toolbar();
          grid.bbar.addButton(excelButton);
        } else if (Ext.isArray(grid.bottomToolbar)) {
          if (config.insertIndex >= 0)
            grid.bottomToolbar.splice(config.insertIndex,0,excelButton);
          else
            grid.bottomToolbar.splice(grid.bottomToolbar.length,0,excelButton);
        } else if (grid.bottomToolbar) {
          if (config.insertIndex >= 0)
            grid.bottomToolbar.insertButton(config.insertIndex, excelButton);
          else
            grid.bottomToolbar.insertButton(grid.bottomToolbar.items.length, excelButton);
        } else {
          grid.elements += ',bbar';
          grid.bottomToolbar = grid.createToolbar([excelButton]);
        }
      }
    }
  };
};

Ext.preg('simpleexporttoexcel', Jaffa.grid.plugins.SimpleExportToExcelPlugin);
