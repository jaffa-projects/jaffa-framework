/**
 * @author PrudhviK
 * @class Jaffa.form.CheckBoxField
 * @extends Ext.form.CompositeField
 *
 * @xtype checkboxfield
 */
Jaffa.form.CheckBoxField = Ext.extend(Ext.form.CompositeField, {
  msgTarget : 'side',
  combineErrors: false,
  fieldType : undefined,
  initComponent: function(){
    var field = this.field || {};
    Ext.applyIf(field,{
      xtype : this.meta? 'finderComboGrid': this.fieldType ,
      metaField : this.mapping || this.metaField,
      metaClass : this.metaClass,
      listeners : {
        scope : this,
        'focus' : this.enableField
      }
    });
    if(this.meta) field.meta = this.meta;
    if(this.gridColumns) field.gridColumns = this.gridColumns;
    if(this.popupAlways)field.popupAlways = this.popupAlways;
    var checkbox = {
      xtype: 'checkbox',
      listeners : {
        scope : this,
        'check' : function(cb,checked){
          (checked)? this.enableField() : this.disableField()
        }
      }
    }

    if(field.hidden){
      this.hidden = true;
    }
    if(this.hidden) {
      this.hideLabel = true;
    }
    Ext.apply(this,{
      items :  [checkbox,field]
    })
    Jaffa.form.CheckBoxField.superclass.initComponent.call(this);
  },
  getFormField : function(){
    var formField;
    if(this.items && this.items.getCount() === 2){
      formField =  this.items.itemAt(1);
    }
    return formField;
  },
  getCheckBox : function(){
    var formField;
    if(this.items && this.items.getCount() === 2){
      formField =  this.items.itemAt(0);
    }
    return formField;
  },
  setValue : function(v){
    this.getFormField().setValue(v);
  },
  getValue : function(){
   if(this.isChecked() &&  this.getFormField()){
      return this.getFormField().getValue();
    }
    return null;
  },
  isChecked : function(){
    var checkbox = this.getCheckBox();
     return (checkbox && checkbox.getValue() === true)
  },
  enableField : function(){
    var checkbox = this.getCheckBox();
    var field = this.getFormField();
    if(field.readOnly === true){
      field.setReadOnly(false);
      checkbox.setValue(true);
    }
  },
  disableField : function(){
    var field = this.getFormField();
    if(field.readOnly === false){
      field.reset();
      field.setReadOnly(true);
    }
  },
  /**
   *@override
   */
  validateValue: function(value, preventMark) {
    var valid = true;
    if(this.isChecked()){
      this.eachItem(function(field) {
        if (!field.isValid(preventMark) || (field instanceof Jaffa.form.FinderComboBox && !field.isValueValid)) {
          valid = false;
        }
      });
    }
    return valid;
  }
});
Ext.reg('checkboxfield', Jaffa.form.CheckBoxField);