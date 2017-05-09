/**
 * @class Jaffa.data.FinderOutDto
 * This is the base class for MetaData instances used by the Finder widgets.
 * The default implementation assumes to query a finder component through its Tx and 
 * receive the records in Dto objects.
 */
Jaffa.data.FinderOutDto = function(config){
  /**
   * @cfg {String/Array} key The key field(s). This is a required property.
   */
  /**
   * @cfg {Object} fields An object containing the fields returned by the Finder.  Each field will be an Object containing field mapping information. This is a required property.
   */
  Ext.apply(this, config);
  
  this.finder = Ext.applyIf(this.finder ? this.finder : {}, {
    /**
     * @cfg {String} DWRFunctionName The DWR function to be used for invoking the Finder. This is a required property.
     */
    /**
     * @cfg {String} DWRFunctionInput The optional argument to be passed to the DWR function.
     */
    /**
     * @cfg {String} orderByFields The optional argument to be passed to the DWR function for sorting the output from the Finder.
     */
    totalProperty: 'totalRecords',
    root: 'rows'
  });
  
  this.finder.combo = Ext.applyIf(this.finder.combo ? this.finder.combo : {}, {
    /**
     * @cfg {Array} columns An array of fields to be added to the underlying Store of a FinderCombo. This is a required property.
     */
    maxRecords: 25
  });
  
  this.finder.combo.config = Ext.applyIf(this.finder.combo.config ? this.finder.combo.config : {}, {
    /**
     * @cfg {String} valueField The underlying data value name to bind to this ComboBox. This is a required property.
     */
    /**
     * @cfg {String} displayField The underlying data field name to bind to this ComboBox. This is a required property.
     */
    mode: 'remote',
    minChars: 0,
    triggerAction: 'all',
    maxHeight: 150,
    resizable: true
  });
  
  this.finder.grid = Ext.applyIf(this.finder.grid ? this.finder.grid : {}, {
    /**
     * @cfg {Array} columns An array of fields to be added to the underlying Store of a FinderGridPanel. This is a required property.
     */
    pagingSize: 15,
    popupAlways: false,
    showCriteria: false
  });
  
  this.finder.grid.config = Ext.applyIf(this.finder.grid.config ? this.finder.grid.config : {}, {
    enableColLock: false,
    loadMask: true,
    height: 385,
    width: 400
  });
  
};

Jaffa.data.FinderOutDto.prototype.getProxy = function(){
  return new Ext.data.DWRProxy(eval(this.finder.DWRFunctionName));
};

Jaffa.data.FinderOutDto.prototype.getBaseParams = function(){
  var baseParams = '';
  if (this.finder.DWRFunctionInput) 
    baseParams = this.finder.DWRFunctionInput;
  if (this.finder.orderByFields) 
    baseParams = (baseParams ? (baseParams + ', ') : '') + ('orderByFields: ' + this.finder.orderByFields);
  return eval('({' + baseParams + '})');
};

Jaffa.data.FinderOutDto.prototype.getReader = function(isGrid){
  // calculate the field list for the reader.
  var fields = [];
  var columns = this.finder.combo.columns;
  if (isGrid) {
    columns = this.finder.grid.columns;
  }
  for (var i = 0; i < columns.length; i++) {
    var fieldName = columns[i];
    if (typeof fieldName == 'string') {
      // needs to filter out the multi-row-select column when used for LOV combo
      var field = this.fields[fieldName];
      field.name = fieldName;
      fields.push(field);
    }
  }
  
  return new Jaffa.data.FinderReader({
    idFields: this.key,
    totalProperty: this.finder.totalProperty,
    root: this.finder.root,
    fields: fields
  });
};

Jaffa.data.FinderOutDto.prototype.paramNames = {
  'start':'firstRecord'
  ,'limit':'maxRecords'
  ,'sort':'orderByFields'
  ,'dir' : undefined 
};
