/**
 * @class Jaffa.form.FinderLovGridPanel
 * @extends Jaffa.form.FinderGridPanel
 * An extension to {@link Jaffa.form.FinderGridPanel} for rendering the output returned by Jaffa's Finder components.
 */
Jaffa.form.FinderLovGridPanel = Ext.extend(Jaffa.form.FinderGridPanel, {
  initComponent: function(){
    // clone this.meta so that it can be extended
    this.meta = Ext.ux.clone(this.meta);
    
    // add new Jaffa.grid.HideableCheckboxSelectionModel() to grid column
    this.meta.finder.grid.cm = new Jaffa.grid.HideableCheckboxSelectionModel();
    this.meta.finder.grid.columns.unshift(this.meta.finder.grid.cm);
    
    // Invoke the initComponent of the base class
    Jaffa.form.FinderLovGridPanel.superclass.initComponent.call(this);
  }
  
});

Ext.reg('finderLovGridPanel', Jaffa.form.FinderLovGridPanel);
