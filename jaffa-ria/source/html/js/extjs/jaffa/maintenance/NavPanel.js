/**
 * @class Jaffa.maintenance.NavPanel
 * @extends Ext.Panel
 *
 * This is the base class used for the Nav Panel.
 */
Jaffa.maintenance.NavPanel = Ext.extend(Ext.Panel, {

  /**
   * @constructor
   * @param {Object} config a configuration object.
   */
  constructor: function(config) {
    config = config || {};
    Ext.applyIf(config, {
      titleToken: 'title.jaffa.jaffaRIA.maintenance.NavPanel.Contents', //FIXME
      region: 'west',
      width: 200,
      border: true,
      collapsible: true,
      split: true,
      autoFill: true,
      layout: "full-accordion",
      defaults: {
        //TODO - use CSS
        // applied to each contained panel
        bodyStyle: 'border:1px solid #99BBE8;border-top:0px;background: #eeeeee;'
      },
      layoutConfig: {
        activeOnTop: false,
        animate: false,
        autoFill: true,
        collapseFirst: false,
        hideCollapseTool: false,
        titleCollapse: true
        ,autoWidth: true
      }
    });
    Jaffa.maintenance.NavPanel.superclass.constructor.call(this, config);
  }
});
Ext.reg('maintenancenavpanel', Jaffa.maintenance.NavPanel);
