/**
 * Ext.ux.IconCombo
 *
 * @author  Ing. Jozef Sakalos
 * @modified Prudhvi K
 * @class Ext.ux.IconCombo
 * @extends Jaffa.form.FinderComboBox
 */

Ext.ux.IconCombo = Ext.extend(Jaffa.form.FinderComboBox, {
  iconMode: 'class', // use 'image' to insert actual image rather than using css class
  imagePath: '', //absolute path to image
  iconExtension: '.png',
  initComponent:function() {

    if(this.iconMode === 'image'){
      Ext.apply(this, {
        tpl:  '<tpl for=".">'
        + '<div class="x-combo-list-item">'
        + '<div  class="ux-icon-combo-item " style="background: url('+this.imagePath + '{' + this.iconImageField + '}' + this.iconExtension+') no-repeat scroll transparent;">'
        + '</div>'
        + '{' + this.displayField + '}'
        + '</div></tpl>'
      });
    } else {
      Ext.apply(this, {
        tpl:  '<tpl for=".">'
        + '<div class="x-combo-list-item">'
        + '<div class="ux-icon-combo-item '
        + '{' + this.iconClsField + '}"></div>'
        + '{' + this.displayField + '}'
        + '</div></tpl>'
      });
    }

    // call parent initComponent
    Ext.ux.IconCombo.superclass.initComponent.call(this);
  }, // end of function initComponent

  onRender: function(ct, position) {
    // call parent onRender
    Ext.ux.IconCombo.superclass.onRender.call(this, ct, position);

    // adjust styles
    this.wrap.applyStyles({position:'relative'});
    this.el.addClass('ux-icon-combo-input');
    this.el.setStyle('padding-left','25px');

    // add div for icon
    this.icon = Ext.DomHelper.append(this.el.up('div.x-form-field-wrap'), {
      tag: 'div', style:'position:absolute', id : this.el.id + '-icon-el'
    });
  }, // end of function onRender

  setIconCls: function() {
    if(!Ext.isEmpty(this.getValue())){
      var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
      if(rec) {
        if(this.textOnly){
          var el = Ext.get(this.el.id + '-text');
          if(el) {
            el.addClass('ux-icon-combo-icon ' + this.getIconClsName(rec));
            el.setStyle('padding-left','25px');
          }
        } else if(this.icon) {
          this.icon.className = 'ux-icon-combo-icon ' + this.getIconClsName(rec);
        }
      }
    }
  }, // end of function setIconCls
  setIconImage: function() {
    if(!Ext.isEmpty(this.getValue())){
      var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
      if(rec) {
        var backgroundCSS = 'url('+this.getIconImagePath(rec)+') no-repeat scroll transparent';
        if(this.textOnly){
          var el = Ext.get(this.el.id + '-text');
          if(el) {
            el.addClass('ux-icon-combo-icon');
            el.setStyle('padding-left','25px');
            el.setStyle('background', backgroundCSS);
          }
        } else if(this.icon) {
          this.icon.className = 'ux-icon-combo-icon';
          Ext.get(this.icon.id).setStyle('background', backgroundCSS);
        }
      }
    }
  },
  setValue: function(value) {
    Ext.ux.IconCombo.superclass.setValue.call(this, value);
    if(this.iconMode == 'image'){
      this.setIconImage();
    } else {
      this.setIconCls();
    }
  }, // end of function setValue

  getIconClsName : function(rec){
    return rec.get(this.iconClsField);
  },

  getIconImagePath: function(rec){
    return this.imagePath + rec.get(this.iconImageField) + this.iconExtension;
  }
});

// register xtype
Ext.reg('iconcombo', Ext.ux.IconCombo)