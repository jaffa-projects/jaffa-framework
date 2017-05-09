/**
 * @class Jaffa.form.MoneyField
 * @extends Ext.form.NumberField
 * @author SeanZ
 * @ignore from JsDoc
 *
 * This registers a new widget type called 'moneyfield'
 * The main difference from NumberField is that it displays a '$' in the text box
 *
 * @note THIS IS EXPREIMENTAL
 * @note Also See http://extjs.com/forum/showthread.php?p=218620#post218620
 * @note This could probably be merged with the CurrencyField
 */
Jaffa.form.MoneyField = Ext.extend(Ext.form.NumberField, {
	// currently only implements us money
    initValue : function(){
        if (this.textOnly){
            this.setTextOnly(true);
        }
        if(this.value !== undefined){
            this.setValue(this.value);
        }else if(this.el.getValue().length > 0){
            this.setValue(this.el.getValue());
        }
    },
    
	setValue : function(v) {
		Jaffa.form.MoneyField.superclass.setValue.call(this, v);
		if (this.el && this.el.dom)
			this.el.dom.value = '$' + this.getValue();
	}
});
Ext.reg('moneyfield', Jaffa.form.MoneyField);
