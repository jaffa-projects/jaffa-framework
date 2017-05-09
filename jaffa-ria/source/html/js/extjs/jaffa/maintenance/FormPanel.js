
/**
 * @class Jaffa.maintenance.FormPanel
 * @extends Jaffa.maintenance.Panel
 *
 * This is the base class for a creating a form panel to be used in a
 * maintenance component. It provide the default behaviour to allow
 * the fields on the panel to be loaded from the data in a controller,
 * as well as participate in the controllers "save" process by providing
 * panel validation, and returning its modified values that should be persisted
 * <p>
 * Because this panel is not "readOnly" by default it will register "change listeners"
 * so that when any field in the panel is modified, the panel is marked as "dirty".
 * It this panel has been registered as a tab, the tab will indicate that the panel is
 * dirty.
 * <p>
 * By default this panel will get a "save" option in the toolbar, which will be tied to
 * the controllers "save" process.
 *
 * @deprecated Instead of being tied to a specific Panel, create any appropriate Panel and
 * apply the 'Jaffa.maintenance.plugins.PanelLoadSave' plugin
 */
Jaffa.maintenance.FormPanel = Ext.extend(Ext.Panel, {
  defaultType: 'textfield',
  layout: 'formdescription',
  plugins: new Jaffa.maintenance.plugins.PanelLoadSave()
});

Ext.reg('maintenanceformpanel', Jaffa.maintenance.FormPanel);

