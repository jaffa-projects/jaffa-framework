/**
 * @class Ext.form.MyTextField
 * @extends Ext.form.TextField
 * Supports a 'read-only' mode where it displays the value as plain HTML.
 * getValue() also honors the CaseType metadata
 * <p>
 * This has been superceeded by the extention of the Ext.form.Field to support a 'textOnly' configuration 
 * <p>
 * @constructor
 * Creates a new MyTextField
 * @param {Object} config Configuration options
 */
Ext.form.MyTextField = Ext.extend(Ext.form.TextField, {
    onRender : function(ct, position){
        if(!this.readOnly){
            Ext.form.TextField.superclass.onRender.call(this, ct, position);
        } else {
          // Make Read Only Version
          if(!this.el){
              this.width="auto";// Don't use a fixed width for text
              this.fieldClass="x-form-textonly";
              var cfg = {tag: "div"};
              if(!cfg.name){
                  cfg.name = this.name || this.id;
              }
              this.el = ct.createChild(cfg, position);
          }
          this.el.addClass([this.fieldClass, this.cls]);
          this.initValue();
        }    
    },
    
    initValue : function(){
        if(this.value !== undefined){
            this.setValue(this.value);
        }else if(this.el.dom.value && this.el.getValue().length > 0){
            this.setValue(this.el.getValue());
        }
    },
    /**
     * Returns the normalized data value (undefined or emptyText will be returned as '').  To return the raw value see {@link #getRawValue}.
     * @return {Mixed} value The field value
     */
    getValue : function(){
      var v;
      if(!this.readOnly){
        v = Ext.form.TextField.superclass.getValue.call(this);
      } else {
        v = this.value;
      }
      if (typeof v == 'string' && this.initialConfig.style && this.initialConfig.style.indexOf('uppercase') >= 0) {
        v = v.toUpperCase();
      }
      return v;
    },
    
    validateValue : function(){
        if(!this.readOnly){
            return Ext.form.TextField.superclass.validateValue.call(this);
        } else {
            return this.value;
        }
    },
    
    /**
     * Sets a data value into the field and validates it.  To set the value directly without validation see {@link #setRawValue}.
     * @param {Mixed} value The value to set
     */
    setValue : function(v){
        if(!this.readOnly){
            return Ext.form.TextField.superclass.setValue.call(this, v);
        } else {
          this.value = v;
          if(this.rendered){
              this.el.dom.innerHTML = v;
          }
        }
    }
    
    //@TODO - Implement get/setDescription so it can be dynamically changed
});
Ext.reg('mytextfield', Ext.form.MyTextField);


