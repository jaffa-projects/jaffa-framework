
/**
 * @class Jaffa.maintenance.Viewport
 * @extends Ext.Viewport
 *
 * This is the base class used for the maintenance viewport.
 *
 * By default it supports a header in the north, an accordion
 * in the west and a tab panel in the center. Methods are exposed
 * for adding both accordion and tab panels to the viewport.
 */
Jaffa.maintenance.Viewport = Ext.extend(Ext.Viewport, {
  /**
   * @cfg {Ext.Panel} body
   * (Optional) this is the panel that will be used as the "center" section of the viewport,
   * by default a new Jaffa.maintenance.TabPanel is used.
   */
  /**
   * @cfg {Jaffa.maintenance.NavPanel} nav
   * (Optional) this is the panel that will be used as the "west" section of the viewport,
   * by default a new Jaffa.maintenance.NavPanel is used.
   */
  /**
   * @cfg {Jaffa.maintenance.TabPanel} tabs
   * (Option) this is the panel that will be used as the "center" section of the viewport,
   * by default a new Jaffa.maintenance.TabPanel is used.
   * @deprecated Use the body.
   */
  /**
   * @cfg {Ext.Panel} header
   * (Mandatory) this is the panel that will be used as the "north" section of the viewport
   */
  header: undefined,

  /**
   * @cfg  {Jaffa.component.CRUDController} controller
   * (Mandatory) Primary controller to be used by this viewport.
   * This is the controller that will be loaded via the header panel's "refresh" button
   */
  controller: undefined,

  /**
   * @property refreshFromChildWindow - Setting this flag to true will refresh parent window
   * viewport from the child window/tab.
   * @type boolean
   * @defaultValue false
   */
  refreshFromChildWin : false,

  /**
   * (Optional) Implement this function if custom reloadData is required
   * @param {Object} Child controller data
   */
   onChildWindowReload : undefined,
   
   alertMsgUnableAutomaticallyCloseWindow: 'Unable to automatically close this window, please close it yourself.',
   
   confirmSaveChange: 'Save Changes?',
   
   discardUnSavedChangesMsg: 'You will lose all unsaved changes. Are you sure you want to close this screen?',
   
  /**
   * @constructor
   * @param {Object} config a configuration object.
   */
  constructor: function(config) {
    this.addEvents(    /**
     * @event switchtoupdate
     * Fires after the viewport has been used to create a new record, and now
     * it has been saved and the new record reloaded.
     * This will be fired after the load event on the controller has been fired, so
     * you can assume all the panels registered with the controller will have been loaded
     * @param {Object} this
     */
    'switchtoupdate');

    var itemIds = {};

    config = Ext.apply({}, config);
    config.bodyPanel = config.body ? config.body : (config.tabs ? config.tabs : new Jaffa.maintenance.TabPanel());
    delete config.body;
    delete config.tabs;
    config.bodyPanel.itemId = config.bodyPanel.itemId || config.bodyPanel.id || 'viewPortBodyPanel';
    itemIds.bodyPanel = config.bodyPanel.itemId;

    //the tabPanel field is for backwards compatibility only.
    config.tabPanel = config.bodyPanel instanceof Jaffa.maintenance.TabPanel ? config.bodyPanel : null;
    if (config.tabPanel)
      config.tabPanel.ref = config.tabPanel.ref || '../tabPanel';

    // Set the default controller on the header if it doesn't have one
    if (config.header) {
      if (!config.header.controller) config.header.controller = config.controller;
      if (!(config.header instanceof Ext.Panel)) {
        config.header = Ext.ComponentMgr.create(config.header, 'panel');
      }
      config.controller.registerPanel(config.header);
    }
    itemIds.header = config.header.itemId || config.header.id || 'viewportHeader';

    Ext.applyIf(config, {
      id: 'viewport',
      layout: 'border',
      items: [config.header, config.bodyPanel]
    });
    delete config.header;
    delete config.bodyPanel;

    config.navPanel = config.nav !== undefined ? config.nav : new Jaffa.maintenance.NavPanel();
    delete config.nav;
    if (config.navPanel) {
      config.navPanel.itemId = config.navPanel.itemId || config.navPanel.id || 'viewportNavPanel';
      itemIds.navPanel = config.navPanel.itemId;
      config.items.push(config.navPanel);
      delete config.navPanel;
    }

    config.plugins = config.plugins || [];
    config.plugins.unshift({
      init: function(c) {
        // attach the panel objects to viewport
        for (var a in itemIds) {
          c[a] = c.getComponent(itemIds[a]);
        }
      }
    });
    // Create the viewport
    Jaffa.maintenance.Viewport.superclass.constructor.call(this, config);

    // Add code to manage state change
    this.mode = '';
    config.controller.on({
      'load': function() {
        if (this.controller.isLoaded && this.header) {
          var tbar = this.header.getTopToolbar();
          if (tbar.refreshBtn){
            tbar.refreshBtn.enable();
          }
          if (typeof this.header.load == 'function') {
            this.header.load();
          }
        }
        if (this.mode == 'create') {
          this.fireEvent('switchtoupdate', this);
        }
        this.mode = 'update';
        //console.info("Mode Change: ", this.mode);
      },
      'save': function() {
        if (this.mode == '') {
          this.mode = 'create';
        }
        //console.info("Mode Change: ", this.mode);
      },
      scope: this
    });
  },

  /**
   * @method
   * See if this maintenance viewport is currently in Create mode, if its not
   * it most be in update mode. This applied to the primary controller being used
   * @return {boolean} true if in create mode, false if in update mode.
   */
  isCreateMode: function() {
    return this.mode != 'update';
  },

  /**
   * Adds a section to the navigation accordion
   *
   * @param {Ext.Panel} panel
   * @param {Object} dataSource Entry in the controller's model that will be the
   *        source of the data used in this panel. The object can be an array/collection
   *        in the case of a grid based panel
   * @param {Jaffa.component.CRUDController} controller The controller object that is being
   *        used to load and save the data referenced in 'dataSource'
   */
  addAccordionPanel: function(panel, dataSource, controller, index) {
    if (!panel.controller) {
      panel.controller = controller;
    }

    if (!panel.controller) {
      panel.controller = this.navPanel.lookupController();
    }

    if (!controller) {
      controller = panel.controller;
    }

    // Find the datasource
    if (!dataSource && panel.findDataSource) {
      dataSource = panel.findDataSource();
    }
    
    if (!(panel instanceof Ext.Panel)) panel = Ext.ComponentMgr.create(panel, 'panel');

    if (controller) {
      controller.registerPanel(panel, dataSource);
    }

    if (controller) {
      // invoke the load event if the controller already had data and loadData() is defined on the panel
      if (controller.isLoaded) {
        if(panel.loadData) {
          panel.loadData();
        }
        controller.applyPanelFieldsMetaRules(panel, {});
      } else {
        controller.setPanelFields(panel);
      }
    }

    if(Ext.isDefined(index)){
      this.navPanel.insert(index,panel);
    } else {
      this.navPanel.add(panel);
    }
    if (panel.wireEvents) {
      panel.wireEvents();
    }

    if (this.rendered) {
      panel.show();
      this.navPanel.doLayout();
    }
  },

  /**
   * Adds a tab to the center tab panel section
   *
   * @param {Ext.Panel} panel
   * @param {Object} dataSource Entry in the controller's model that will be the
   *        source of the data used in this panel. The object can be an array/collection
   *        in the case of a grid based panel
   * @param {Jaffa.component.CRUDController} controller The controller object that is being
   *        used to load and save the data referenced in 'dataSource'
   */
  addPanel: function(panel, dataSource, controller) {
    // force instantiation of panel
    panel = (panel instanceof Ext.Container) ? panel : Ext.create(panel, 'panel');
    console.debug("Add New Tab:", arguments);
    // Stamp the controller on the panel
    if (!panel.controller) {
      panel.controller = controller;
    }

    if (!panel.controller) {
      panel.controller = this.bodyPanel.lookupController();
    }

    if (!controller) {
      controller = panel.controller;
    }

    // Find the datasource
    if (!dataSource && panel.findDataSource) {
      dataSource = panel.findDataSource();
    }

    // Register this panel with the controller and the specific data item
    if (controller) {
      controller.registerPanel(panel, dataSource);
    }

    // Propergate the metadata class name from the data to the panel if available
    if (dataSource && !panel.metaClass) {
      panel.metaClass = dataSource.className;
    }

    if (controller) {
      // invoke the load event if the controller already had data, else initialize the fields of the panel
      if (controller.isLoaded) {
        //@TODO: Remove the 'panel.load()' invocation once the legacy code has been refactored to use the new loadData method
        if (panel.loadData) {
          panel.loadData();
        }
        else{
          panel.load();
        }
      }
      else {
        controller.setPanelFields(panel);
      }
    }

    // Add the panel to the Tab Master and render it  
    if (this.bodyPanel instanceof Jaffa.maintenance.TabPanel) {
      this.bodyPanel.addTab(panel);
      // GOLD-56317(Part Number Packaging Page Fields Maxlength Not Enforced)
      if (panel.controller && typeof panel.findDataSource == 'function') panel.controller.applyPanelFieldsMetaRules(panel,panel.findDataSource());
    }
    else {
      this.bodyPanel.add(panel);
    }
    if (panel.wireEvents) {
      panel.wireEvents();
    }

    // Listen to changes on the Panel so we can add an "modified" indicator in the tab
    panel.on("datamodified", function() {
      //FIXME: Should the 'star' feature be controlled by the config 'showDirtyOnTitle' property (see WorkOrderTabPanel.js)
      //FIXME: Enable/Disable the save and other buttons on the toolbar
      console.debug("Data Has Changed On Tab");
      if (this.isDirty) {
        this.title_clean = this.title;
        this.setTitle('*' + this.title);
      }
      else if (this.title_clean) {
        this.setTitle(this.title_clean);
        delete this.title_clean;
      }
    }, panel);
    this.doLayout();

    if (typeof Rules.get('Jaffa.metadata.HidePanelList')=='string' && Rules.get('Jaffa.metadata.HidePanelList').split(',').indexOf(panel.itemId) >= 0) {
      if (this.bodyPanel.rendered) {
        panel.hide();
        if (this.bodyPanel instanceof Ext.TabPanel) this.bodyPanel.hideTabStripItem(panel);
      } else {
        this.bodyPanel.on('afterrender', function() {
          this[1].hide();
          if (this[0] instanceof Ext.TabPanel) this[0].hideTabStripItem(this[1]);
        }, [this.bodyPanel, panel], {single: true});
      }
    }
  },

  /**
   * Adds a tab to the center tab panel section
   *
   * @param {Ext.Panel} panel
   * @param {Object} dataSource Entry in the controller's model that will be the
   *        source of the data used in this panel. The object can be an array/collection
   *        in the case of a grid based panel
   * @param {Jaffa.component.CRUDController} controller The controller object that is being
   *        used to load and save the data referenced in 'dataSource'
   * @deprecated Use addPanel.
   */
  addTabPanel: function(panel, dataSource, controller) {
    this.addPanel(panel, dataSource, controller);
  },

  /**
   * Calls a method to destroy the viewport and close the window. If any panels
   * have been modified, a warning will be issued that any changes will be lost.
   */
  closeWindow: function() {
    // If any tabs are dirty, warn user before close
    var dirty = false;
    this.controller.registeredPanels.each(function(panel) {
      if (panel.isDirty) {
        dirty = true;
        return false;
      }
    });

    if (dirty) {
      Ext.MessageBox.show({
        title: this.confirmSaveChange,
        msg: this.discardUnSavedChangesMsg,
        width: 400,
        buttons: Ext.MessageBox.YESNO,
        icon: Ext.MessageBox.QUESTION,
        scope: this,
        fn: function(btn) {
          if (btn == 'yes') {
            this.closeWindowOk();
          }
        }
      });
    }
    else {
      this.closeWindowOk();
    }
  },

  /**
   * The default behaviour is to destroy the viewport and close the window. This
   * assumes that the window to close was opened as a new browser window and is
   * no longer needed, as is common when opening one WEB2.0 window from another.
   *
   * When opening a WEB2.0 screen from a WEB1.0 screen in the same
   * browser window, this method provides a way to close the WEB2.0 screen and
   * return to the WEB1.0 screen is its original state.  To do this, the
   * return-to path must be set in params.returnToActionPath and the
   * return-to component Id can be set in params.returnToFormKey_componentId.
   * Alternately, a final URL can be set in params.finalUrl.  The URL that is
   * formed from these params is opened in the same browser window.
   */
  closeWindowOk: function() {
    // Determine the URL to use.
    var url = null;
    if (params && params.returnToActionPath) {
      url = params.returnToActionPath + '.do';
      if (params.returnToFormKey_componentId) {
        url = url + '?componentId=' + params.returnToFormKey_componentId + '&eventId=' + 'refresh';
      }
    }
    else if (params && params.finalUrl) {
      url = '/startComponent.do?finalUrl=' + escape(params.finalUrl);
    }

    if (Ext.getCmp('viewport')) {
      Ext.getCmp('viewport').destroy();
      // When a URL is supplied open it in the same window, otherwise
      // close the window.
      if (url !== null) {
        window.open(Jaffa.data.getAppRootUrl() + url, '_self');
      }
      else {
        try {
          window.close();
        }
        catch (e) {
          alert(this.alertMsgUnableAutomaticallyCloseWindow);
        }
      }
    }
    else {
      alert(this.alertMsgUnableAutomaticallyCloseWindow);
    }
  },

  /**
   *   If the body element contains a loadMask, the rendering of the viewport will remove the loadmask
   */
  onRender: function(ct, position) {
    var loadMaskText = Ext.get('loading');
    var loadMask = Ext.get('loading-mask');
    if (loadMaskText) {
      loadMaskText.remove();
    }
    if (loadMask) {
      loadMask.fadeOut({
        remove: true
      });
    }
    Jaffa.maintenance.Viewport.superclass.onRender.call(this, ct, position);
    if (this.header) {
      var tbar = this.header.getTopToolbar();
      if (tbar.refreshBtn){
        tbar.refreshBtn.setDisabled(!this.controller.isLoaded);
      }
    }
    if(this.controller){
      this.controller.on('save',this.reloadParent,this);
    }
  },
  reloadParent : function() {
    var parentWin = self.opener;
    try {
      if(parentWin && !parentWin.closed && parentWin.Ext){
        var parentViewport = parentWin.Ext.getCmp('viewport');
        if(parentViewport && parentViewport.refreshFromChildWin) {
          if(Ext.isFunction(parentViewport.onChildWindowReload)){
            var data = this.controller && this.controller.model && this.controller.model.itemAt(0);
            parentViewport.onChildWindowReload.call(parentViewport,data);
          } else if(parentViewport.controller && !parentViewport.controller._isDirty) {
            (parentViewport.header) ? parentViewport.header.onRefresh() : parentViewport.controller.load();
          }
        }
      }
    } catch (e){
      //This is to catch permission denied error that occasionally occurs in firefox
      console.info('Error refreshing parent window');
    }
  }

});

Ext.reg('maintenanceviewport', Jaffa.maintenance.Viewport);
