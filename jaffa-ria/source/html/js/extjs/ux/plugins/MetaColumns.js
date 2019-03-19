/**
 * @class Ext.ux.plugins.MetaColumns
 * @author PaulE
 * @param {Object} config Options object
 * @constructor
Example
<code>
new Ext.grid.GridPanel({
  ...
  plugins: new Ext.ux.plugins.MetaColumns({
    columns:columns,
    record:store.recordType,
    filter:true
  })
  ...
});  
</code>
Combined with the Maintenance Plugin, in an editable grid.
<code>
    this.gridPanel = new Ext.grid.EditorGridPanel({
      plugins: [
        new Ext.ux.plugins.MetaColumns({
          columns:columns,
          record:store.recordType
        }),
        new Jaffa.maintenance.plugins.GridLoadSave({
          metaClass: 'EffectivityGraph',
          controller: config.controller,
          findDataSource: function () {
            var output = this.controller.model.itemAt(0).effectivities;
            if (!output) {
              output = new Ext.util.MixedCollection();
              this.controller.model.itemAt(0).effectivities = output;
            }
            return output;
          }
        })
      ],
      ...
    });  
</code>
 */
Ext.ux.plugins.MetaColumns = function(config) {
    var config = config||{};
    /**
     * @cfg {Object[]} columns (Mandatory)
     */
    /**
     * @cfg {Jaffa.data.Record} record (Optional if metaClass provided) 
     */
    /**
     * @cfg {String/Object} metaClass (Optional if record provided)
     */
    /**
     * @cfg {boolean} filter (Optional) if true, the filter plugin will be used and built
     * from the meta data
     */
   
    
    return {
        init: function(grid) {
          this.grid = grid; // Let the filter have a reference to the grid
          
          // finding default meta class
          var dmClass = config.metaClass;
          if (typeof dmClass == 'string') dmClass = ClassMetaData[dmClass];
          if (!dmClass) dmClass = this.grid.metaClass;
          if (typeof dmClass == 'string') dmClass = ClassMetaData[dmClass];
          
          grid.colModel = this.buildColumnModel(config.columns, config.record, dmClass);
          // set all the editable columns to editable=false if the grid.editable=false
          if (grid.editable===false) {
            for (var i=0; i<grid.colModel.config.length; i++) {
              if (grid.colModel.config[i].editor) {
                grid.colModel.config[i].editable=false;
              }
            }
          }
          
          // determine whether has inline editor plugin
          var hasInlineRowEditor = false;
          if (Ext.isArray(grid.initialConfig.plugins))
          for (var i=0; i<grid.initialConfig.plugins.length; i++) {
            if (grid.initialConfig.plugins[i].ptype && grid.initialConfig.plugins[i].ptype.toLowerCase().indexOf('roweditor')>=0) {
              hasInlineRowEditor = true; 
              break;
            }
          }
          // determine whether there are editable columns, 
          var hasEditableColumn = false;
          for (var i=0; i<grid.colModel.config.length; i++) {
            if (grid.colModel.config[i].editor) {
              hasEditableColumn = true; 
              break;
            }
          }
          if ((hasEditableColumn || hasInlineRowEditor) && this.grid.editable !== false) {
            // set the checkable row selection model to checkOnly=true
            var cm = this.grid.getSelectionModel();
            if (cm.width>0 && !cm.singleSelect) {
              cm.checkOnly = true;
              cm.handleMouseDown = Ext.emptyFn;
            }
            // create a template to determine editable background highlight colors
            // it can be modified to accomondate the requirement of highlighting on the row basis
            var view = this.grid.getView();
            view.templates = view.templates || {};
            view.templates.cell = new Ext.XTemplate(
              '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css} {[values.editable?"'+view.editableCss+'":""]}" style="{style}" tabIndex="0" {cellAttr}>',
              '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>',
              '</td>'
            );
            // apply mandatory rule on column header
            grid.on('render', function(grid) {
              var cm = grid.getColumnModel();
              for (var i=0; i<cm.getColumnCount(); i++) {
                var col = cm.getColumnAt(i);
                if (col.mandatory && !col.hidden && col.editor && col.editable!==false) {
                  cm.setColumnHeader(i, '<span class="x-mand-flag">*</span>' + col.header);
                }
              }
            });
          }
          
          if(config.filter) {
            var isLocal = false;
            if (grid.getStore && grid.getStore().local==true) isLocal = true;
            this.filter = this.buildFilters(config.columns, config.record, dmClass, isLocal);
            this.filter.init(grid);
          }
        }
        
        /**
         * EXTENSION - Build a column model by merging in data from a Jaffa.data.Record definition and its associated Class Meta Data
         * @method
         * @param columns {Object[]} (Mandatory) This array may contain "strings", Ext.grid.Column config objects, or other valid column obejcts 
         * like CheckboxSelectionModel. Strings are converted into Column config object using the string as the dataIndex
         * @param record {Ext.data.Record} A record that may contain metadata used for building columns. The additional metadata config
         * properties are...
         *   <li> name - {String} Simple name used throughout for this field
         *   <li> type - {String} What data type the field is (Default is string, others are float, int, date, ...)        
         *   <li> defaultValue - {String} The value to use in the record if there is no data for this field        
         *   <li> mapping - {String} JSON Path to get the value out of the data structure for this record, assumes 'name' if not supplied
         *   <li> sortable - {Boolean} to indicate whether sorting is available from the server for this field
         *   <li> sortFieldName - The alternate {name} to use when passing the sort by field list to the server, assumes 'name' if sortable is true and not provided
         *   <li> filter - {Boolean} to indicate whether a filter will be provided to limit the selection on this column
         *   <li> filterFieldName - {String} The alternate {name} to use when passing the filtering clause to the server, assumes 'name' if filter is true and not provided
         *   <li> hidden - {Boolean} to indicate if by default this column should be initially hidden  
         *   <li> alwaysHidden - {Boolean} true if this field should not be used in the column model at all.
         *   <li> metaClass - {String} that can override the Record's defaultMetaClass 
         *   <li> metaField - {String} an override to the default use of the mapping or name to derive the correct metadata field
         *   <li> renderer - 
         *   <li> inList - 
         *   <li> Basically any other property that can be found in a ClassMetaData field definition.
         * The Record.defaultMetaClass property is can be used a the default setting for the metaClass field property
         * @param defaultMetaClass {string/object} can either be the name of actual instance of a ClassMetaData object.
         * This can be used as an alternate to passing in a Record which has a defaultMetaClass
         */ 
        ,buildColumnModel: function (columns, record, defaultMetaClass) {
        
          if(this.grid.metaModel==null)
              this._createMetaModel(columns, record, defaultMetaClass);
        
          var columnModel = [];
          for (var i=0; i<columns.length; i++) {
            var column=columns[i];
            if(!(typeof(column) == "string" || column.dataIndex || column.name)){
              // This skips over already pre-built column objects like selection models, and includes them 'as is'
              columnModel[columnModel.length] = column;
            }
            else {
              column = this.grid.metaModel.get(typeof(column) == "string" ? column : column.dataIndex);
              if (column.alwaysHide) continue;
              var cm = Ext.apply({}, {
                  dataIndex: column.dataIndex || column.name,
                  hidden: column.hidden===true,
                  sortable: column.sortable===true,
                  groupable: column.groupable===true,
                  header: column.label || column.header
                  ,headerToken: column.labelToken || column.headerToken
              }, column);
              delete cm.name;
              
              // We expect header text to be defined, but will either instantiate from headerToken or default to the column name if not present.
              if(!cm.header) {
                if (cm.headerToken) cm.header = Labels.get(cm.headerToken);
                else {
                  console.warn("Grid ",this.id || this," Column:", cm.dataIndex, " has no header label defined");
                  cm.header = cm.dataIndex; 
                }
              }
              
              // Important to pass through the ID column definition, especially with trees.
              if(column.id) cm.id = column.id;
              if(column.editor) cm.editor = this.applyMetaRulesToEditor(column.editor,defaultMetaClass, column.metaField || column.mapping);
              if(column.width) cm.width = column.width;
        
              // Calculate Width
              if(!cm.width || cm.width===undefined || cm.width === Ext.grid.ColumnModel.prototype.defaultWidth) {
                if (column.type === 'dateonly' || column.type === 'monthyear')
                  cm.width=100;
                else if (column.type === 'datetime')
                  cm.width=140;
                else if (column.type === 'boolean')
                  cm.width=50;
                else if (column.maxLength)
                  cm.width = Math.min(Math.max(column.maxLength,5),40)*8;
              }
              // Default the alignment
              if(!cm.align && column.type && (column.type=='float'||column.type=='int'||column.type=='number'))
                  cm.align = 'right';
                  
              //Number Formatting when renderer is not provided.
              // Set the layout for numeric fields to render significant digits only if allowDecimals is false; do not apply this if a renderer has been provided
              if (!column.layout && !column.renderer && column.allowDecimals === false && ['number', 'float', 'int'].indexOf(column.type) >= 0)
                column.layout = '[integer.format]';
              else if(['number', 'int', 'float'].indexOf(column.type)>=0 && !column.xtype && !column.renderer){
                cm.xtype = 'numbercolumn';
                if(column.allowDecimals === false || (!column.allowDecimals && column.type=='int')){
                  cm.format = '0';
                }
              }
              
              // Default standard renderers, based on layout or datatype
              if(column.renderer!==undefined) {
                cm.renderer = column.renderer;
                //console.debug("Column Meta:",cm.dataIndex,"renderer provided",column.renderer);
              } else {
                //console.debug("Column Meta:",cm.dataIndex,column.type,column.layout,column.renderer);
                var layout = column.layout||Ext.ux.renderer.getDefaultLayout(column.type);
                if(layout) {
                  var renderer = Ext.ux.renderer.Renderers[layout];
                  if(renderer) {
                    cm.renderer = renderer;
                    //console.debug("Column Meta - Apply Renderer:",cm.dataIndex,layout,renderer);
                  }
                } else if (Ext.isArray(column.inList) && column.inList.length>0) {
                  // display the labels 
                  cm.inList = {};
                  var displayFieldIndex = column.displayFieldIndex==undefined?1:column.displayFieldIndex;
                  for (var j=0; j<column.inList.length; j++) {
                    cm.inList[column.inList[j][0]] = column.inList[j][displayFieldIndex];
                  }
                  cm.renderer = (function(v) {
                    var o = v;
                    if (this.inList[v] !== undefined) o = this.inList[v];
                    return o;
                  });
                }
              }
              
              //console.info("Add Column:",cm.dataIndex,"model:",cm,"config:",column);
              columnModel[columnModel.length] = cm;
            }   
          }
          var colModel = new Ext.grid.ColumnModel(columnModel);
          colModel.defaultSortable = true;
          return colModel;
        }
        
        /**
         * EXTENSION - Build a column filter configuration by merging in data from a Jaffa.data.Record
         * definition and its associated Class Meta Data
         *
         * This is for use when using the filter plugin.
         */ 
        ,buildFilters: function (columns, record, defaultMetaClass, isLocal) {
        
          if(this.grid.metaModel==null)
              this._createMetaModel(columns, record, defaultMetaClass);
        
          var filters = [];
          for (var i=0; i<columns.length; i++) {
            var column=columns[i];
            if (typeof(column) == "string" || column.dataIndex || column.name) {
              column = this.grid.metaModel.get(typeof(column) == "string" ? column : column.dataIndex);
              if (column.filter===undefined || column.filter == false) continue;
              var flt = { dataIndex: column.dataIndex };
                       
              // Figure out correct dataType to use for this filter
              var dt = 'string';
              if(column.type && column.type!='auto') dt=column.type;
              if (dt=='dateonly' || dt=='monthyear' || dt=='datetime')
                dt='date';
              else if (dt=='int' || dt=='long' || dt=='float' || dt=='double' || dt == 'number')
                dt='numeric';
              else if (dt=='bool')
                dt='boolean';
              else if (dt=='string' && column.inList) {
                dt='list';
                // construct option list
                flt.options = [];
                if (Ext.isArray(column.inList)) {
                  for (var k=0; k<column.inList.length; k++) {
                    flt.options.push(column.inList[k]);
                  }
                } else if (typeof column.inList == 'object'){
                  for(var o in column.inList)
                    if (typeof column.inList[o] == 'string')
                      flt.options.push(column.inList[o]);
                }
                // @TODO Add in support for list of values...
                if (Ext.isArray(column.values) && column.values.length>0) {
                  flt.value = [];
                  for (var k=0; k<column.values.length; k++) {
                    flt.value.push(column.values[k]);
                  }
                }
              }
              
              if(dt!='numeric'&&dt!='string'&&dt!='numeric'&&dt!='date'&&dt!='list'&&dt!='boolean') {
                console.error("Filter on ",this.id || this," field ",column.dataIndex," not supported for data type ",dt,' / ',column.type);
                continue;
              } else {
                flt.type = dt;
              }
              
              // Propergate CaseType
              if (column.type=='string' && column.caseType && column.caseType.toLowerCase() != 'mixed') {
                flt.editorStyle = 'text-transform: '+column.caseType.toLowerCase()+';';
              }
              filters[filters.length] = flt;
            }
          }
          var oo = {filters: filters};
          if (isLocal == true) oo.local = true;
          return new Ext.ux.grid.GridFilters(oo);
        }
        
        /**
         * @private
         * This builds a "meta model" of all the column data based either on
         * a) (columns[string], defaultMetaClass) where the columns are a list of 
         * fields defined in the referenced class meta data object
         * b) (columns[string], null, Ext.data.Record) where the columns are a list of 
         * fields defined in the referenced Record object, where the record object either
         * directly defined the field or may reference a metaClass either per field, or globally at the 
         * Record.defaultMetaClass level
         *
         * Alternatly columns can also contain column definition in the normal for based on Ext.grid.Column, but
         * can additional define the metaClass and metaField so metadata will be pulled in from the related 
         * ClassMetaData definition.
         *
         * If both a Column config object is provided as well as a record definition as well as a ClassMetaData
         * reference, then the order of presedence is Column config first, then Record MetaData then ClassMetaData.
         *
         * Other objects provided in the column list like a CheckboxSelectionModel, will be ignored as there will be no
         * meta data for these.
         */
        ,_createMetaModel: function (columns, record, defaultMetaClass) {
          this.grid.metaModel = new Ext.util.MixedCollection();
          var hideColumnRule;
          if (this.grid.hideColumnList !== false) {
            hideColumnRule = this.grid.hideColumnList || Rules.get('Jaffa.metadata.HideColumnList');
            hideColumnRule = hideColumnRule.split(',');
          }
          for (var i=0; i<columns.length; i++) {
            var column=columns[i];
            if(typeof column == 'string')
              column  = { dataIndex:column };
        
            if(typeof(column) == "string" || column.dataIndex || column.name) {
              
              // Apply additional metadata to field definition
              if(record) {
                var meta = record.getField(column.dataIndex);
                if(meta) {
                  Ext.applyIf (column, meta);
                  //console.info("Apply Record Data :",column.dataIndex,meta);
                }
                if(column.type && column.type == 'auto') {
                  delete column.type;
                }
                  
                //Remove the default renderer, since that the renderer specific to the datatype can be applied
                if (column.renderer === Ext.grid.Column.prototype.renderer)
                  column.renderer = undefined;
                //else
                //  console.warn("------Column:",column.dataIndex," has a renderer ", column.renderer);
              } else {
                column.filter = !column.noFilter;
                delete column.noFilter;
              }
              
              // Determine source of meta data for field
              var metaClass = column.metaClass ? column.metaClass : (record && record.defaultMetaClass ? record.defaultMetaClass : defaultMetaClass);
              //console.info("ClassMeta: ", metaClass,column.metaClass);
              if(typeof(metaClass) == "string")
                  metaClass = ClassMetaData[metaClass];
              var metaField = column.metaField || column.mapping || column.name || column.dataIndex;
              if(!metaField)
                console.error("ClassMeta - No Field: ", metaField, " Class:",metaClass, "Column:",column);
        
              // Apply ClassMetaData if available
              if (metaClass && metaClass.fields && metaField != null && metaField != '') {
                var meta = metaClass.fields[metaField] || metaClass.fields[metaField.split('.').pop()];
                if (meta) {
                  Ext.applyIf (column, meta);
                  if (meta.filter === false) column.filter = false;
                  if (meta.hidden) column.alwaysHide = true;
                  if (meta.width) {
                    column.width = Math.min(100,Math.max(20,column.width*5));
                  }
                }
              }
             
              // Apply Rules to hide columns
              if (Ext.isArray(hideColumnRule) && hideColumnRule.length>0) {
                if (hideColumnRule.indexOf(column.dataIndex)>=0) {
                  column.alwaysHide = true;
                }
              }
             
              // Consolidate Sort/Group Options
              if(column.sortable==true || (column.sortable!=false&&column.sortFieldName!=null&&column.sortFieldName!='')) {
                if(column.groupable===false)
                  delete column.groupable;
                else
                  column.groupable=true;
                column.sortable=true;
                column.sortFieldName = column.sortFieldName || column.name || column.dataIndex;  
              } else {
                delete column.sortable;
                delete column.groupable;
                delete column.sortFieldName;
                delete column.sortDir;
              }
              
              // Consolidate Filter Options
              if(column.filter==true || column.filterFieldName) {
                column.filter=true;
                column.filterFieldName = column.filterFieldName || column.name || column.dataIndex;
              } else {
               // delete column.filter;
                delete column.filterFieldName;
              }  
              //console.info("Final MetaField: ",column.dataIndex,column);
              this.grid.metaModel.add(column.dataIndex,column);
              
              if (column.renderer)
                column.customRenderer = true;
            }  
          }
        },
      /**
       * applyMetaRulesToEditor
       * Applies meta rules to the editor based on ClassMetaData.
       * @return {Object} editor the editor config object after applying metadata
       */
      applyMetaRulesToEditor: function(editor,defaultMetaClass,defaultMetaField){
        var metaClass = editor.metaClass || defaultMetaClass ;
        var includeList = editor.includeList || ['CASE', 'MINLENGTH', 'MAXLENGTH', 'MANDATORY', 'MINVALUE', 'MAXVALUE'];
        if(metaClass) {
          if (Ext.isString(metaClass)) {
            // metaClass can be specified as the name of the graph class inside of ClassMetaData or just the graph class
            metaClass = ClassMetaData[metaClass] ;
          }
          if(metaClass){
            var metaRules = metaClass.fields[editor.metaField || editor.mapping || defaultMetaField];
            if (metaRules ) {
              Jaffa.component.PanelController.prototype.applyValidationMetaData(editor, metaClass, metaRules, includeList);
            }
          }
        }
        return editor;
      }
    };
};

Ext.preg('metacolumns', Ext.ux.plugins.MetaColumns);
