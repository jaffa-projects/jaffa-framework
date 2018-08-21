/**
 * @class Ext.layout.AdjacentFieldLayout
 * @extends Ext.layout.AnchorLayout
 * <p>
 * This adds a new layout called 'adjacentfield'.
 * <p>
 * This can be used within a form panel to layout several fields on the same line. In this case the labels are
 * merged together with a seperator (typically a /) and the editable fields are also seperated (with a /)
 * <p>
 * Example usage:</p>
 * <pre>
 * User ID :           [.......]
 * Email Address:      [.......]
 * First Name/Surname: [.......]/[.......]
 * </pre>
 * <p>
 * <pre><code>
 new Ext.FormPanel({
 defaultType: 'textfield',
 items: [{
 fieldLabel: 'User ID'
 ,name: 'userid'
 },{
 fieldLabel: 'Email Address'
 ,name: 'email'
 }, {
 layout: 'adjacentfield'
 ,defaultType: 'textfield'
 ,items: [{
 fieldLabel: 'First Name'
 ,name: 'firstName'
 },{
 fieldLabel: 'Surname'
 ,name: 'surname'
 }]
 }]
 })
 </code></pre>
 */
Ext.layout.AdjacentFieldLayout = Ext.extend(Ext.layout.AnchorLayout, {
/**
 * @cfg {String} labelStyle
 * A CSS style specification string to add to each field label in this layout (defaults to '').
 */
/**
 * @cfg {String} elementStyle
 * A CSS style specification string to add to each field element in this layout (defaults to '').
 */
  /**
   * @cfg {String} labelSeparator
   * The standard separator to display after the text of each form label (defaults to a colon ':').  To turn off
   * separators for all fields in this layout by default specify empty string '' (if the labelSeparator value is
   * explicitly set at the field level, those will still be displayed).
   */
  labelSeparator : ':',
  adjacentSeparator : '/',
  paddingStr: 'padding-left:',
  /**
   * @cfg {String} hideFieldSeparator
   * Flag to display adjacent separator between fields even when the fields have noLabels
   */
  hideFieldSeparator : true,
  
  // private
  getAnchorViewSize : function(ct, target){
    return ct.body.getStyleSize();
  },
  
  constructor: function(config) {
    /**
     * The Ext.isRTL value sets in loadProduct1.jsp.
     * This value will be TRUE in case of Arabic Language and FALSE if it is ENGLISH.
     * 
     * Dynamically setting the paddingStr field values, in case of Arabic its value 
     * is paddingStr = "padding-right:".
     */
    if (Ext.isRTL) this.paddingStr='padding-right:';
    Ext.layout.AdjacentFieldLayout.superclass.constructor.call(this, config);
  },
 
  // private
  setContainer : function(ct){
  
    Ext.layout.AdjacentFieldLayout.superclass.setContainer.call(this, ct);
  
    if(ct.labelAlign){
      ct.addClass('x-form-label-'+ct.labelAlign);
    }

    if(ct.hideLabels){
      this.labelStyle = "display:none";
      this.elementStyle = "padding-left:0;";
      this.labelAdjust = 0;
    }else{
      this.labelSeparator = ct.labelSeparator || this.labelSeparator;
      this.adjacentSeparator = ct.adjacentSeparator || this.adjacentSeparator;
      ct.labelWidth = ct.labelWidth || 175;
      if(Ext.isNumber(ct.labelWidth)){
        var pad = (typeof ct.labelPad == 'number' ? ct.labelPad : 5);
        this.labelAdjust = ct.labelWidth+pad;
        this.labelStyle = "width:"+ct.labelWidth+"px;";
        this.elementStyle = "padding-left:"+(ct.labelWidth+pad)+'px';
      }
      if(ct.labelAlign == 'top'){
        this.labelStyle = "width:auto;";
        this.labelAdjust = 0;
        this.elementStyle = this.paddingStr + "80px;";
      }
    }

    if(!this.fieldTpl){
      // the default field template used by all form layouts
      var t = new Ext.Template(
        '<div class="x-form-item x-form-item-adj {itemCls}" tabIndex="-1">',
        '<label for="{id}" style="{labelStyle}" class="x-form-item-label">{label}{mandatoryFlag}{labelSeparator}</label>',
        '<div class="x-form-element" id="x-form-el-{id}" style="{elementStyle}">',
        '</div><div class="{clearCls}"></div>',
        '</div>'
      );
      t.disableFormats = true;
      t.compile();
      Ext.layout.AdjacentFieldLayout.prototype.fieldTpl = t;
    }
  },

  // private
  renderItem : function(c, position, target){
    if(c && !c.rendered && c.isFormField && c.inputType != 'hidden'){
      if (position == 0){

        var t = new Ext.Template(
          '<div class="x-form-item x-form-item-adj {itemCls}" tabIndex="-1">',
          '<label for="{id}" style="{labelStyle}" class="x-form-item-label">{compositeLabel}{labelSeparator}</label>',
          '<div class="x-form-element" id="x-form-el-{id}" style="{elementStyle}">',
          '</div><div class="{clearCls}"></div>',
          '</div>'
        );
        t.disableFormats = true;
        t.compile();
        Ext.layout.AdjacentFieldLayout.prototype.fieldTpl = t;
        var args = this.getTemplateArgs(c,position);
        position = target.dom.childNodes[0] || null;
        this.fieldTpl.append(target, args);

      } else {
        if (position == 1){
          var t = new Ext.Template(
            '<div class="x-form-item x-form-item-adj {itemCls}" tabIndex="-1">',
            '<label for="{id}" class="x-form-item-label" style="width:5px;'+this.paddingStr+'1px;">{adjacentSeparator}</label>',
            '<div class="x-form-element-adj" id="x-form-el-{id}">',
            '</div>',
            '</div>'
          );
          t.disableFormats = true;
          t.compile();
          Ext.layout.AdjacentFieldLayout.prototype.fieldTpl = t;
        }
        var showSep = Boolean(c.getValue()) || c.getValue()===0 || !c.textOnly;
        var args = this.getTemplateArgs(c,position);
        position = target.dom.childNodes[0] || null;
        //         this.fieldTpl.insertBefore(position, args);
        this.fieldTpl.append(target, args);
      }
      c.render('x-form-el-'+c.id);
    }else {
      Ext.layout.AdjacentFieldLayout.superclass.renderItem.apply(this, arguments);
    }
  },

  renderAll : function(ct, target){
    var items = ct.items.items;
    // computing the compositeLabel by rollup all labels.
    var compositeLabel = '';
    for (var i = 0, len = items.length; i < len; i++) {
      var c = items[i];
      if (c.fieldLabel && !c.hideLabel ){
        if (compositeLabel) {
          compositeLabel += this.adjacentSeparator + (c.label || c.fieldLabel);
        } else {
          compositeLabel = c.label || c.fieldLabel;
        }
        
        if (c.allowBlank == false && !c.textOnly)
          compositeLabel += "<span class='x-mand-flag'>*</span>";
      }
    }

    for (var i = 0, len = items.length, position = -1; i < len; i++) {
      var c = items[i];
      if (position == -1 && !c.hideLabel) {
        position = 0;
        c.compositeLabel = compositeLabel;
        this.renderItem(c, position, target);
      } else if (position == -1 && c.hideLabel) {
        this.renderItem(c, 1, target);
      } else {
        position ++;
        this.renderItem(c, position, target);
      }
      
    }
  },

  // private
  adjustWidthAnchor : function(value, comp){
    return value - (comp.hideLabel ? 0 : this.labelAdjust);
  },

  // private
  isValidParent : function(c, target){
    return true;
  },

  getTemplateArgs: function(field,position) {
    var noLabel = (!field.fieldLabel || field.hideLabel) && this.hideFieldSeparator;
    return {
      id            : field.id,
      label         : field.label || field.fieldLabel,
      compositeLabel: field.compositeLabel,
      itemCls       : (field.itemCls || this.container.itemCls || '') + ((field.hideLabel && this.hideFieldSeparator) ? ' x-hide-label' : ''),
      clearCls      : field.clearCls || '',
      labelStyle    : field.labelStyle || this.labelStyle || '',
      elementStyle  : this.elementStyle || '',
      labelSeparator: (noLabel)? '' : (Ext.isDefined(field.labelSeparator) ? field.labelSeparator : this.labelSeparator),
      mandatoryFlag : (field.allowBlank==false&&!field.textOnly && !noLabel)?"<span class='x-mand-flag'>*</span>":'',
      adjacentSeparator : (position > 0 && !noLabel) ? this.adjacentSeparator : ''
    };
  }

/**
 * @property activeItem
 * @hide
 */
});

Ext.Container.LAYOUTS['adjacentfield'] = Ext.layout.AdjacentFieldLayout;