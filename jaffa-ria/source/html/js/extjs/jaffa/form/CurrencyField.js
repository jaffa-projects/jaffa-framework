/**
 * @class Jaffa.form.CurrencyField
 * @extends Ext.form.NumberField
 * @author BobbyC
 *
 * This registers a new widget type called 'currencyfield'
 * The main difference from NumberField is that this field is right aligned,
 * and displays with a fixed precision (based on property 'decimalPrecision')
 *
 * Currency 'symbol' is derived from Ext.util.Format.defaultCurrencySymbol which is set in loadExtJS.jsp
 */
Jaffa.form.CurrencyField = Ext.extend(Ext.form.NumberField, {
    padZeros: 2,
    decimalPrecision: 2,
    emptyText: '',
    constructor: function(config) {
      Jaffa.form.CurrencyField.superclass.constructor.call(this, Ext.applyIf(config || {}, {
        symbolText: Ext.util.Format.defaultCurrencySymbol,
        stripCharsRe: new RegExp()
      }));
      if (this.symbolText==='$') this.stripCharsRe.compile('^\\'+this.symbolText+'\\s*');
      else this.stripCharsRe.compile('^'+this.symbolText+'\\s*');
      // leave the assignment of emptyText to user default to empty.
      // this.emptyText = this.symbolText+'0.00';
    },
    setValue:function(v){
      if (v===null || v===undefined || v==='') {
        v = this.initialConfig.value;
      }
      if (typeof v == 'string') {
        v = v.replace(this.stripCharsRe,'');
      }
      // v = isNaN(v) ? '' : String(parseFloat(v).toFixed(this.decimalPrecision)).replace(".", this.decimalSeparator);
      Jaffa.form.CurrencyField.superclass.setValue.call(this, v);
    },
    setRawValue: function(v) {
      if (typeof v == 'number') {
        v = this.symbolText + v;
      } else if (v) {
        v = String(v);
        if (v.length>0 && v.search(this.stripCharsRe) == -1) {
          v = this.symbolText + v;
        } else if (v.length==0){
          v = '';
        }
      }
      Jaffa.form.CurrencyField.superclass.setRawValue.call(this, v);
    },
    getValue: function() {
      var v = Ext.form.NumberField.prototype.getValue.call(this);
      if (typeof v == 'string') v = v.replace(this.stripCharsRe, '');
      v = this.fixPrecision(this.parseValue(v));
      v = Jaffa.util.Format.toFullFixed(parseFloat(v));
      return isNaN(v)? '' : v;
    },
    parseValue: function (value) {
      if (typeof value === 'string')
        value = value.replace(this.stripCharsRe, '');
      return Jaffa.form.CurrencyField.superclass.parseValue.call(this, value);
    }
});
Ext.reg('currencyfield', Jaffa.form.CurrencyField);

/**
 * DEPRECATED. Use Ext.util.Format.money() defined in ext-extension.js
 *
 * @param {Object} value
 */
Jaffa.form.CurrencyField.renderer = function(value){
  var v = parseFloat(value);
  if (isNaN(v)) {
    v = 0.00;
  }
  var i = Math.floor(v);
  var d = String(Math.round((v - i) * 100));
  if (d == '')
    d = '00';
  else if (d.length == 1)
    d = d + '0';
  return Ext.util.Format.defaultCurrencySymbol + i + '.' + d;
};