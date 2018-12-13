/**
 * @author Nagaraju Punna
 * @class Jaffa.form.InListComboBox
 * @extends Ext.form.ComboBox
 *
 * @xtype inlistcombo
 *
 * This component can be used to display value and description from list as display field.
 */
Jaffa.form.InListComboBox = Ext.extend(Ext.form.ComboBox, {
  inListdata: undefined,
  sortInfo:{field: 'value', direction: 'ASC'},
  initComponent: function() {
    for (var i = 0; i < this.inListdata.length; i++) {
      this.inListdata[i].push(this.inListdata[i][0] + ' - ' + this.inListdata[i][1]);
    }
    Ext.apply(this, {
      mode: 'local',
      triggerAction: 'all',
      displayField: '_valueDescription',
      valueField: 'value',
      forceSelection: true,
      store: new Ext.data.SimpleStore({
        fields: ['value', 'description', '_valueDescription'],
        data: this.inListdata,
        sortInfo:this.sortInfo,
        filter : function(property, value, anyMatch, caseSensitive, exactMatch){
          Ext.data.Store.prototype.filter.call(this,'description', value, anyMatch, caseSensitive, exactMatch);
        }
      })
    });
    Jaffa.form.InListComboBox.superclass.initComponent.call(this);
  }
});
Ext.reg('inlistcombo', Jaffa.form.InListComboBox);