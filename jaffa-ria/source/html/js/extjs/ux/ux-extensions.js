/**
 * @author Sean Zhou, 2012
 */

Ext.ux.form.LovCombo.prototype.setValue = Ext.ux.form.LovCombo.prototype.setValue.createInterceptor(function(v) {
  if(v && this.mode == 'remote' && !Ext.isDefined(this.store.totalLength)) {
    this.store.on('load', this.setValue.createDelegate(this, arguments), null, {single: true});
    this.doQuery(this.allQuery, true); 
    return false;
  }
  return true;
});

Ext.ux.IconCombo.prototype.setIconImage = Ext.ux.IconCombo.prototype.setIconImage.createInterceptor(function() {
  if(this.icon && Ext.isEmpty(this.getValue())) {
    Ext.get(this.icon.id).setStyle('background', '');
    return false;
  }
  return true;
});

