/**
 * @class Jaffa.maintenance.SaveMenuButton
 * @extends Ext.SplitButton
 * A button that has a built-in menu. Typically this would be used to display a dropdown menu that provides functionality
 * such as "Save As.." and "Save and Exit".
 * Example usage:
 *
     tbar :[{
       xtype : 'tbsave',
       saveAsPanel : {
         xtype : 'saveAsPanel'
       }
     }]

 * @constructor
 * Create a new menu button
 * @param {Object} config The config object
 * @xtype tbsave
 */
Jaffa.maintenance.SaveMenuButton = Ext.extend(Ext.SplitButton,{
  iconCls: 'save',
  itemId : 'save',
  disabled : true,
  saveAsPanel : undefined,
  saveBtnText: 'Save',
  saveAsText : 'Save As ...',
  saveAndExitText : 'Save & Exit',
  allowSaveAndExit : true,
  allowMenuWhenDisabled : true,
  disableSaveAs : false,
  disableSaveAsAndExit : false,
  controller : undefined,
  initComponent: function(){
    var menuItems = [];
    this.addEvents('saveasclick','saveandexitclick','beforesaveas');
    this.text = this.saveBtnText;
    this.saveAsItem = new Ext.menu.Item({
      text: this.saveAsText,
      scope : this,
      itemId: 'saveAs',
      enableOnDirty: this.disableSaveAs !== true,
      disableOnDirty : this.disableSaveAs !== true,
      disabled : true,
      handler: function(){
        this.saveAsHandler();
        this.fireEvent("saveasclick");
      }
    });
    if(this.saveAsPanel){
      menuItems.push(this.saveAsItem);
    }
    if(this.allowSaveAndExit === true) {
      menuItems.push({
        text: this.saveAndExitText,
        itemId : 'saveAndExit',
        scope : this,
        disabled : this.disableSaveAsAndExit,
        enableOnDirty : this.disableSaveAsAndExit !== true,
        handler:function() {
          this.saveAndExitHandler();
          this.fireEvent("saveandexitclick");
        }
      })
    }
    if(!Ext.isEmpty(menuItems)) {
      Ext.applyIf(this, { menu : { items: menuItems}});
    }
    Jaffa.maintenance.SaveMenuButton.superclass.initComponent.call(this);
    
    // The SaveAs button should always disabled when data is unsaved
    this.on('afterrender', function(){
      this.controller = this.ownerCt.ownerCt.controller;
      if(this.saveAsItem){
        this.saveAsItem.disable();
      }
      if(this.controller){
        this.controller.on('load', function(ctrl){
          if(this.saveAsItem && !this.disableSaveAs){
            this.saveAsItem.enable();
          }
        }, this);
      }
    });
    
  },
  onDisableChange: function(disabled){
    this.toggleButtonVisual(disabled);
    if(this.btnEl){
      this.btnEl.dom.disabled = disabled;
    }
    this.disabled = disabled;

    //If menu has any items with enableOnDirty flag set to true, they follow the
    //behavior of the parent button
    if(this.menu) {
      this.disableMenuItems(this.menu.find('enableOnDirty', true),disabled);
      this.disableMenuItems(this.menu.find('disableOnDirty', true),!disabled);
    }
  },
  setAllowMenuWhenDisabled: function(tf) {
    this.allowMenuWhenDisabled=tf;
    this.toggleButtonVisual(this.disabled);
  },
  toggleButtonVisual: function(disabled) {
    if(this.el){
	//This is for "The arrow trigger was not showing the menu when the button is disable in IE written by 'cosbor11'.
	// if you comment this line the menu is showing in IE.
     // if(!Ext.isIE || !this.text){
        var el;
        if(this.allowMenuWhenDisabled && disabled) {
           this.el['removeClass'](this.disabledClass);
           this.btnEl['addClass'](this.disabledClass);
        } else {
           this.el[disabled ? 'addClass' : 'removeClass'](this.disabledClass);
           this.btnEl[disabled ? 'addClass' : 'removeClass'](this.disabledClass);
        }
      }
    //}
  },
  disableMenuItems : function(items,disabled) {
    if(!Ext.isEmpty(items)) {
      Ext.each(items, function(item){
        if(item.rendered) {
          item.setDisabled (disabled);
        } else {
          item.disabled = disabled;
        }
      })
    }
  },
  onClick : function (e) {
    e.preventDefault();
    //Shows menu even when button disabled based on allowMenuWhenDisabled property
    if(this.isClickOnArrow(e) &&
            (!this.disabled || (this.disabled && this.allowMenuWhenDisabled === true))){
      if(this.menu && !this.menu.isVisible() && !this.ignoreNextClick){
        this.showMenu();
      }
      this.fireEvent("arrowclick", this, e);
      if(this.arrowHandler){
        this.arrowHandler.call(this.scope || this, this, e);
      }
    }else if (!this.disabled){
      if(this.enableToggle){
        this.toggle();
      }
      this.fireEvent("click", this, e);
      if(this.handler){
        this.handler.call(this.scope || this, this, e);
      }
    }
  },
  saveAsHandler : function(btn) {
    var ctrl = this.ownerCt.ownerCt.controller;
    if(ctrl && ctrl.isLoaded){
      if(this.saveAsPanel) {
        if (this.fireEvent('beforesaveas', this)!==false){
          if(this.saveAsPanel instanceof Ext.Panel){
            this.saveAsPanel.show();
          } else {
            new Ext.create(this.saveAsPanel,'panel').show();
          }
        }
      }
    } else {
      console.error('Cannot perform Save As when controller is not loaded');
    }
  },
  saveAndExitHandler : function() {
    if(this.controller  || this.findController()){
      this.controller.save(function(){
        window.close();
      });
    }
  },
  handler : function() {
    if(this.controller || this.findController()){
      this.controller.save();
    }
  },
  findController : function() {
    var panel = this.ownerCt.findParentBy(function(p){
      return !Ext.isEmpty(p.controller);
    });
    if(panel) this.controller = panel.controller;
    return (panel !== null)
  }
});

Ext.reg('tbsave', Jaffa.maintenance.SaveMenuButton);