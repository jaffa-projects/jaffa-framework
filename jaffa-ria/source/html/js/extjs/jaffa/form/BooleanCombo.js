/**
 * @author PrudhviK
 * @class Jaffa.form.BooleanComboBox
 * @extends Ext.form.ComboBox
 *
 * @xtype booleancombo
 *
 * This component can be used instead of using checkbox when representing boolean values.
 */
Jaffa.form.BooleanComboBox = Ext.extend(Ext.form.ComboBox, {
  anyText : 'Any',
  trueText : 'True',
  falseText : 'False',
  initComponent: function(){
    var data = [['',this.anyText],['T',this.trueText],['F',this.falseText]]
    Ext.apply(this, {
      mode: 'local',
      triggerAction: 'all',
      displayField: 'description',
      valueField: 'value',
      store: new Ext.data.SimpleStore({
        fields: ['value', 'description'],
        data: data
      })
    });
    Jaffa.form.BooleanComboBox.superclass.initComponent.call(this);
  }
});
Ext.reg('booleancombo',Jaffa.form.BooleanComboBox);