/**
 * @class Ext.layout.FormDescriptionLayout
 * @extends Ext.layout.AnchorLayout
 * <p>This is a layout specifically designed for creating forms.
 * This class can be extended or created via the layout:'form' {@link Ext.Container#layout} config,
 * and should generally not need to be created directly via the new keyword.  However, when used in an application,
 * it will usually be preferrable to use a {@link Ext.FormPanel} (which automatically uses FormLayout as its layout
 * class) since it also provides built-in functionality for loading, validating and submitting the form.</p>
 * <p>Note that when creating a layout via config, the layout-specific config properties must be passed in via
 * the {@link Ext.Container#layoutConfig} object which will then be applied internally to the layout.  The container
 * using the FormLayout can also supply the following form-specific config properties which will be applied by the layout:
 * <ul>
 * <li><b>hideLabels</b>: (Boolean) True to hide field labels by default (defaults to false)</li>
 * <li><b>itemCls</b>: (String) A CSS class to add to the div wrapper that contains each field label
 * and field element (the default class is 'x-form-item' and itemCls will be added to that)</li>
 * <li><b>labelAlign</b>: (String) The default label alignment.  The default value is empty string ''
 * for left alignment, but specifying 'top' will align the labels above the fields.</li>
 * <li><b>labelPad</b>: (Number) The default padding in pixels for field labels (defaults to 5).  labelPad only
 * applies if labelWidth is also specified, otherwise it will be ignored.</li>
 * <li><b>labelWidth</b>: (Number) The default width in pixels of field labels (defaults to 100)</li>
 * </ul></p>
 * <p>Any type of components can be added to a FormLayout, but items that inherit from {@link Ext.form.Field}
 * can also supply the following field-specific config properties:
 * <ul>
 * <li><b>clearCls</b>: (String) The CSS class to apply to the special clearing div rendered directly after each
 * form field wrapper (defaults to 'x-form-clear-left')</li>
 * <li><b>fieldLabel</b>: (String) The text to display as the label for this field (defaults to '')</li>
 * <li><b>hideLabel</b>: (Boolean) True to hide the label and separator for this field (defaults to false).</li>
 * <li><b>itemCls</b>: (String) A CSS class to add to the div wrapper that contains this field label
 * and field element (the default class is 'x-form-item' and itemCls will be added to that).  If supplied,
 * itemCls at the field level will override the default itemCls supplied at the container level.</li>
 * <li><b>labelSeparator</b>: (String) The separator to display after the text of the label for this field
 * (defaults to a colon ':' or the layout's value for {@link #labelSeparator}).  To hide the separator use empty string ''.</li>
 * <li><b>labelStyle</b>: (String) A CSS style specification string to add to the field label for this field
 * (defaults to '' or the layout's value for {@link #labelStyle}).</li>
 * </ul>
 * Example usage:</p>
 * <pre><code>
// Required if showing validation messages
Ext.QuickTips.init();

// While you can create a basic Panel with layout:'form', practically
// you should usually use a FormPanel to also get its form functionality
// since it already creates a FormLayout internally.
var form = new Ext.FormPanel({
    labelWidth: 75,
    layout: 'formDescription',
    title: 'Form Description Layout',
    bodyStyle:'padding:15px',
    width: 350,
    labelPad: 10,
    defaultType: 'textfield',
    defaults: {
        // applied to each contained item
        width: 230,
        msgTarget: 'side'
    },
    layoutConfig: {
        // layout-specific configs go here
        labelSeparator: ''
    },
    items: [{
            fieldLabel: 'First Name',
            name: 'first',
            allowBlank: false
        },{
            fieldLabel: 'Last Name',
            name: 'last'
        },{
            fieldLabel: 'Company',
            name: 'company'
        },{
            fieldLabel: 'Email',
            name: 'email',
            vtype:'email'
        }
    ],
    buttons: [{
        text: 'Save'
    },{
        text: 'Cancel'
    }]
});
</code></pre>
 */
Ext.layout.FormDescriptionLayout = Ext.extend(Ext.layout.FormLayout, {
    fieldDescription : '',
    fieldTpl: undefined,

    floatStr: "float:left;",
    paddingStr: "padding-left:",

    constructor: function(config) {
      /**
       * The Ext.isRTL value sets in loadProduct1.jsp.
       * This value will be TRUE in case of Arabic Language and FALSE if it is ENGLISH.
       * 
       * Dynamically setting the paddingStr field values, in case of Arabic its value 
       * is paddingStr = "padding-right:" and floatStr='float:right;'
       */
      if (Ext.isRTL) {
        this.floatStr = "float:right;";
        this.paddingStr='padding-right:';
      }
      Ext.layout.FormDescriptionLayout.superclass.constructor.call(config);
    },
 
    // private
    setContainer : function(ct){
      if(!this.fieldTpl){
        // the default field template used by all form layouts
        var t = new Ext.Template(
            '<div class="x-form-item {5}" tabIndex="-1">',
                '<label for="{0}" style="{2}" class="x-form-item-label">{1}{7}{4}</label>',
                '<div  id="x-form-el-{0}" style="'+this.floatStr+'{3}">',
                '</div>',
                '<div style="clear:none;text-align:left;'+this.paddingStr+'3px;" class="x-form-item-description">{8}</div>',
                '<div class="{6}"></div>',
            '</div>'
        );
        t.disableFormats = true;
        t.compile();
        Ext.layout.FormDescriptionLayout.prototype.fieldTpl = t;
      }
      
      Ext.layout.FormDescriptionLayout.superclass.setContainer.call(this, ct);
      // set padding to zero because the template uses float:left;
      this.elementStyle = "padding-left:0px;";
    },

    // private
    renderItem : function(c, position, target){
        if(c && !c.rendered && c.isFormField && c.inputType != 'hidden'){
			c.fieldLabel=(c.fieldLabel==undefined)?Labels.get(c.fieldLabelToken):c.fieldLabel;
            var args = [
                   c.id, (c.fieldLabelToken && c.fieldLabel && c.fieldLabel.indexOf("???")!=0 && Jaffa.util.GetLabelWidget)?(c.fieldLabel + Jaffa.util.GetLabelWidget(c)):c.fieldLabel,
                   c.labelStyle||this.labelStyle||'',
                   this.elementStyle||'',
                   typeof c.labelSeparator == 'undefined' ? this.labelSeparator : c.labelSeparator,
                   (c.itemCls||this.container.itemCls||'') + (c.hideLabel ? ' x-hide-label' : ''),
                   c.clearCls || 'x-form-clear-left' , 
                   c.allowBlank==false&&!c.textOnly?"<span class='x-mand-flag'>*</span>":null,
                   Boolean(c.fieldDescription) ? '('+c.fieldDescription+')' : null];
            if(typeof position == 'number'){
                position = target.dom.childNodes[position] || null;
            }
            if(position){
                this.fieldTpl.insertBefore(position, args);
            }else{
//                if (c.value) args[7] =  c.value + " " + args[7];
                this.fieldTpl.append(target, args);
            }
            //if (c.disabled){
            //   c.rendered = true;
            //}else{              
               c.render('x-form-el-'+c.id);
            //}
        }else {
            Ext.layout.FormDescriptionLayout.superclass.renderItem.apply(this, arguments);
        }
    }
});

Ext.Container.LAYOUTS['formdescription'] = Ext.layout.FormDescriptionLayout;