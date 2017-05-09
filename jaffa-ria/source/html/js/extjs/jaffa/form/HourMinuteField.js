/**
 * @class Jaffa.form.HourMinuteField
 * @extends Ext.form.TextField
 * @author SeanZ
 *
 * This registers a new widget type called 'hourminutefield'
 * The value that you get or set on this field is in hours, and
 * the minutes are represented in fractions of an hour.
 * <p>
 * When rendered the format h:mm is used where h represent the hours in 1 or
 * more digits. This rendered format is also the only input format supported
 *
 * @TODO we could consider allowing data entry in decimal and converting it if required
 */
Jaffa.form.HourMinuteField = Ext.extend(Ext.form.NumberField, {
  defaultTimeFormat : Ext.util.Format.defaultHoursFormat, // defaults to 'hh:mm'
  invalidText : 'The input value is not valid. It must be in h*:mm format.',
  decimalFormatInvalidText : 'The input value is not valid. It must be in 0.00 format.',
  emptyText : '',
  numberFormatEmptyText : '',
  hourFormatEmptyText : '',
  allowNegative : false,
  decimalFormat : false,
  decimalSeparator : ':.',
  initComponent: function(){
    if(this.defaultTimeFormat && this.defaultTimeFormat.startsWith('0')){
      this.orignalPrecision =   this.defaultTimeFormat.startsWith('0.0') ? this.defaultTimeFormat.length-2 : 0 ;
      this.decimalPrecision = this.orignalPrecision ;
      Ext.apply(this,{ decimalFormat  : true,  emptyText : this.numberFormatEmptyText });
    } else {
      Ext.apply(this,{ decimalFormat  : false,  emptyText : this.hourFormatEmptyText });
    }
    if(this.allowNegative){
      this.baseChars += '-';
    }
    Jaffa.form.HourMinuteField.superclass.initComponent.call(this);
  },
  // currently only implements us money
  initValue: function(){
    if (this.textOnly) {
      this.setTextOnly(true);
    }
    if (!Ext.isEmpty(this.value)) {
      this.setValue(this.value);
    } else if (this.el.getValue() !== this.emptyText && this.el.getValue().length > 0) {
      this.setValue(this.el.getValue());
    }
  },

  /**
   * @method
   * Returns the value as a decimal number of hours (it does not return the h:mm value)
   * Note: Field.getValue() reads from el directly if v is not defined.
   * @return {float} The hours as a decimal
   */
  getValue: function(){
    if (this.rendered) {
      // get the value from the element to avoid getting old value before setValue() is run.
      // onBlur() calls getValue() before getting values from elements. This cause new value not
      // being assigned if getValue() is not getting from the element.
      var v = this.el.getValue();
      if(v && v !== this.emptyText){
        return (this.decimalFormat)? this.convertValue(v) : Jaffa.util.converter.hmm2decimal(v).toFixed(this.decimalPrecision);
      } else {
        return '';
      }
    }
    return this.value;
  },

  /**
   * Clears any text/value currently set in the field
   */
  clearValue: function(){
    this.setRawValue('');
    this.lastSelectionText = '';
    this.applyEmptyText();
    this.value = '';
  },

  /**
   * Sets the specified value into the field.
   * @param {float} v The hours as a decimal
   */
  setValue: function(v){
    var text =  this.convertValue(v) || v ;
    this.lastSelectionText = text;
    Ext.form.NumberField.superclass.setValue.call(this, text);
    this.value = v;
    this.validate();
  },
  // private
  convertValue : function(v, isDecimal, precision){
    if (v===0) return v;
    if (!Boolean(v)) return ''; // this includes v=undefined, null, 0, ''
    var text=v;
    if (this.decimalFormat || isDecimal) {
      if (String(v).indexOf(':') >= 0)
        text = (Jaffa.util.converter.hmm2decimal(v)).toFixed(this.orignalPrecision || this.decimalPrecision || precision);
      else
        text = Number(v).toFixed(this.orignalPrecision || this.decimalPrecision || precision);
      return text;
    }
    else  if (String(v).indexOf(':') < 0) {
      text = Jaffa.util.converter.decimal2hmm(v);
      return text;
    }
    return v;
  },
  // private
  validateValue : function(value){
    value = value.toString();
    if(!Jaffa.form.HourMinuteField.superclass.validateValue.call(this, Jaffa.util.converter.hmm2decimal(value))){
      return false;
    }
    if(value.length < 1){ // if it's blank and textfield didn't flag it then it's valid
      return true;
    }
    if (this.isDisplayForm(value)) return true;
    this.markInvalid((value.indexOf(':') >=0) ? this.invalidText : this.decimalFormatInvalidText);
    return false;
  },


  isDisplayForm: function(v){
    if (v) {
      var d;
      if(typeof v != 'string') return false;
      if(v.indexOf(':') >=0 && v.indexOf('.') >=0) return false;
      if(v.indexOf(':') >=0)
        d = String(v).split(':');
      else
        d = String(v).split('.');

      if (d.length<1 || d.length>2) return false;
      if (d.length == 2) {
        var tmp = parseFloat(d[1]);
        if (isNaN(tmp)) return false;
        if (v.indexOf(':') >=0 && (tmp <0 || tmp>=60)) return false;
      }
      var d1 = parseFloat(d[0]);
      if (isNaN(d1)) return false;
      if(!this.allowNegative && d1 < 0)  return false;
      return true;
    }
    return false;
  },
  beforeBlur : function(){
    var v = this.getRawValue();
    if (!Ext.isEmpty(v)) {
      this.setValue(v);
    }
  },
  allowDecimalsOnly : function(){
    this.decimalFormat = true;
    this.emptyText  = this.numberFormatEmptyText;
    this.maskRe = new RegExp('[' + Ext.escapeRe(this.baseChars+ '.') + ']');
    this.originalValue = '';
    this.reset();
  },
  allowDecimalsAndTime : function() {
    if(this.defaultTimeFormat == '0.00'){
      Ext.apply(this,{ decimalFormat  : true,  emptyText : this.numberFormatEmptyText });
    } else {
      Ext.apply(this,{ decimalFormat  : false,  emptyText : this.hourFormatEmptyText });
    }
    this.maskRe = new RegExp('[' + Ext.escapeRe(this.baseChars+ ':.') + ']');
    this.originalValue = '';
    this.reset();
  }
});
Ext.reg('hourminutefield', Jaffa.form.HourMinuteField);
