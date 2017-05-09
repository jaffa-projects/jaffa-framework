/**
 * @class Jaffa.maintenance.TabPanel
 * @extends Ext.TabPanel
 *
 * This is the base class used for the Tab Panel.
 */
Jaffa.maintenance.TabPanel = Ext.extend(Ext.TabPanel, {
  closeWarningText: 'Any unsaved changes will be lost when the tab is closed. Are you sure you want to continue?',
  
  confirmActionMsgText: 'Confirm Action:',
  
  /**
   * @constructor
   * @param {Object} config a configuration object.
   */
  constructor: function(config) {
    //FIXME
    config = config || {};
    Ext.applyIf(config, {
      id: 'tabMaster',
      activeTab: 0,
      region: 'center',
      //resizeTabs: true,
     // minTabWidth: 200,
      enableTabScroll: true,
      defaults: {
        autoScroll: true,
        hideMode: 'offsets'
      },
      header: true,
      setHeaderAsText: true
      //autoHeight: true
    });
    if (Ext.isArray(config.plugins)) {
      config.plugins.splice(0,0, new Ext.ux.TabCloseMenu());
    } else if (config.plugins) {
      config.plugins = [new Ext.ux.TabCloseMenu(), config.plugins]
    } else {
      config.plugins = [new Ext.ux.TabCloseMenu()];
    }
    
    Jaffa.maintenance.TabPanel.superclass.constructor.call(this, config);
    
    // confirm before remove
    this.on('beforeremove', function(tabPanel, panel) {
      if (panel.isDirty && !panel.removeConfirmed) {
        var warningText = panel.closeWarningText || tabPanel.closeWarningText;
        Ext.MessageBox.confirm(this.confirmActionMsgText, warningText, function(btn) {
          if (btn == 'yes') {
            panel.removeConfirmed = true;
            tabPanel.remove(panel);
          }
        });
        return false;
      }
      return true;
    });
    // Register a listener to perform cleanup after a component is removed from the TabPanel
    this.on('remove', function(tabPanel, panel) {
      if (panel.controller) {
        panel.controller.unregisterPanel(panel, panel.findDataSource ? panel.findDataSource() : null);
      }
    });
  },
  
  /**
   * Add a tab to the Tab Master and display it
   * @param {Ext.Panel} panel Panel to add to the tab panel
   */
  addTab: function(p) {
    if (this.rendered) {
      this.add(p).show();
      this.doLayout();
    }
    else {
      this.items.add(p);
    }
  },
  
  onSaveOk: function() {
    //FIXME
  },
  
  onSaveError: function() {
    //FIXME
  }
});

Ext.reg('maintenancetabpanel', Jaffa.maintenance.TabPanel);
