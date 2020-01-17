/**
 * Produces a maintenance object that is derived from an existing maintenance screen.
 *
 */
Jaffa.maintenance.CopyPanel = Ext.extend(Ext.ModalWindow, {
	
  /**
   * The constructor to build the panel.
   * @cfg {Jaffa.component.CRUDController} controller the parent panel controller
   *        with copyCriteria object populated. If copyCriteria is not found, controller.criteria
   *        is used to construct the new controller for copy. The copyCriteria should include all
   *        the fields needed to be copied and exclude all the fields not copied.

   * @cfg {String} forwardUrl the url of the new window for the copied graph
   * 
   * @cfg {Array} keyNames an array of request parameter names to be used in retrieve the copied graph
   *
   * @param {Object} config a configuration JSON to build the modal screen as a form panel.
   *        It should include:
   *           items, an array of JSON objects to build the screen
   *           title, the title of the panel
   * @param {Jaffa.component.CRUDController} controller the parent panel controller
   *        with copyCriteria object populated. If copyCriteria is not found, controller.criteria
   *        is used to construct the new controller for copy. The copyCriteria should include all
   *        the fields needed to be copied and exclude all the fields not copied.
   * @param {Object} urlConfig a JSON object to provide
   *        forwardUrl, which is the url of the new window for the copied graph
   *        keyNames, which is an array of request parameter names to be used in retrieve the copied graph
   */
  constructor: function(config, controller, urlConfig) {
    // create a new CRUD controller
    this.controller = new Jaffa.component.CopyController({
      proxy: controller?controller.proxy:config.controller.proxy,
      reader: controller?controller.reader:config.controller.reader,
      criteria: controller?(controller.copyCriteria  || Ext.ux.clone(controller.criteria)):(config.controller.copyCriteria || Ext.ux.clone(config.controller.criteria)),
      pendingEventConfig : controller && controller.pendingEventConfig ? (controller.pendingEventConfig) : (config && config.controller && config.controller.pendingEventConfig ? config.controller.pendingEventConfig : {})  
    });
    
    // register the key properties to retrieve new copy
    this.keyNames = (urlConfig?urlConfig.keyNames:config.keyNames) || [];
    
    // url to open in new copy window
    this.forwardUrl = urlConfig?urlConfig.forwardUrl:config.forwardUrl;
    
    var config2use = Ext.apply({
      bodyBorder: false,
      frame: true,
      defaults: {
        labelWidth: 100
      },
      width: 450,
      height: 300,
      defaultType: 'textfield',
      buttons: [{
        text: Labels.get('label.jaffaRIA.Button.Ok'),
        scope: this,
        handler: function(btn, evt) {
          this.controller.clone();
        }
      }, {
        text: Labels.get('label.jaffaRIA.Button.Cancel'),
        scope: this,
        handler: function(btn, evt) {
          this.close();
        }
      }]
    }, config);
    delete config2use.controller;
    delete config2use.forwardUrl;
    delete config2use.keyNames;
    
    // construct input panel
    Jaffa.maintenance.CopyPanel.superclass.constructor.call(this, config2use);
    // this.controller.registerPanel(this);
    this.controller.inputPanel = this;
    this.controller.applyPanelFieldsMetaRules(this);
    
  },
  
  onCloneSuccess: function(response) {
    if (response && response.length > 0) {
      // open the new window with new key properties in request params.
      var reqParams = '';
      for (var i = 0; i < this.keyNames.length; i++) {
        if (i > 0) {
          reqParams += '&';
        }
        else {
          reqParams += '?';
        }
        reqParams += this.keyNames[i] + '=' + Jaffa.data.Util.get(response[0].source, this.keyNames[i]);
      }
      window.open(this.forwardUrl + reqParams, "_self");
    }
  }
});

Ext.reg('copypanel', Jaffa.maintenance.CopyPanel);
