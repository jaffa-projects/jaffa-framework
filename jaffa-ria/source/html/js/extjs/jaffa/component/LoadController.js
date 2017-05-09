



/**
 * @class Jaffa.component.LoadController
 * @extends Jaffa.component.PanelController
 *
 * This is a Controller used to perform Load operation
 * It has been targetted to exchange data using a DWRCRUDProxy (although there is
 * a HttpCRUDProxy, and any other CRUDProxy could be created). It has also been optimized to only
 * send delta changes back for updates. This is based on the SOA data exchange concepts built into
 * the JaffaSOA module that provides a Java back end for creating SOA based Graph Services.
 */
Jaffa.component.LoadController = Ext.extend(Jaffa.component.PanelController, {
  /** @cfg {Ext.data.CRUDProxy} proxy (Mandatory) This proxy provides query and update
   *  functions to read and write to an extenal service
   */
  proxy: null

  /**
   * @cfg {Object} criteria  This is the criteria object that will be passed to the
   * query function of the proxy to get the data to be modified by this controller
   */
  ,criteria : null

  /**
   * @cfg {Ext.data.DataReader} reader Allow a custom reader to be supplied for reading the response from
   * the service invoked by the proxy. If no reader is provided the default {@link Jaffa.data.DWRQueryResponseReader}
   * will be used as per the the JaffaSOA java interface
   */
  ,reader : null

  /**
   * @cfg {String} objectLabel (Mandatory) This must be provided as it is used when
   * displaying user error messages
   */
  ,objectLabel : '*Not Set!*'

  ,loadMask: false

  ,loadMaskText: 'Loading ...'

  ,loadMaskComponent: null

  /**
   * @property model This is where the main object array is stored
   * @type Ext.util.MixedCollection
   */
  ,model : null

  /**
   * @property isLoaded Flag to indicate if the query to load the data has been
   * peformed may be true even if the model is null
   * @type boolean
   */
  ,isLoaded : false

  /**
   * @property registeredPanels This is the list of panels that have registered with various parts of the model
   * for editing its contents. This is in assition to the _panelId attributes that are set
   * internally on the Store Records when editing a record.
   * This is used internally on a 'save' so all registered panels can be validated
   * @type Ext.util.MixedCollection
   */
  ,registeredPanels : null

  ,defaultLocalizedErrorMessage: 'Internal errors occurred, please contact your system administrator.'

  ,loadFailureMsgText: 'load failure'
  
  ,loadErrorTitleText: 'Load Error:'
  /*
   * @constructor
   * @param {Object} config a configuration object.
   */
  ,constructor: function(config) {

    // registeredPanels should be created for each instance
    this.registeredPanels = new Ext.util.MixedCollection();

    Ext.apply(this, config);
    this.addEvents(
      /**
       * @event beforeload
       * Fires before a network request is made to load the data objects.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #load} function
       */
      'beforeload'
      /**
       * @event load
       * Fires after the load method's callback is called, assuming the load was successful
       * @param {Object} this
       */
      ,'load'
      /**
       * @event beforepostdata
       * Fires after the data on panel has been used to construct a data graph to be saved to the server
       * but before the post action. This method gives a final chance to change the data graph to be saved
       * on the server.
       * @param {Jaffa.component.LoadController} this the controller
       * @param {Object} saveMode The data object that is to be posted to the server.
       */
      ,'beforepostdata'
      /**
       * @event beforeupdatepanel
       * Fires after the model in controller has been updated from the loaded data but before the model is used to
       * populate the panels
       * @param {Jaffa.component.LoadController} this the controller
       */
      ,'beforeupdatepanel'
      /**
       * @event unregister
       * Fires when a registered panel is unregistered.
       * @param {Jaffa.component.LoadController} this the controller
       */
      ,'unregister'
    );
    Jaffa.component.LoadController.superclass.constructor.apply(this, arguments);
  }


  /**
   * Invoke this to load the data based on the criteria. As this is
   * asyncronous, a callback method can be provided, or the on("load") event
   * on the proxy can be used to process the data once it has been loaded
   */
  ,load : function(callback) {
    console.debug("LoadController.load: criteria = ", this.criteria);
    if (this.fireEvent("beforeload", this, this.criteria) !== false) {
      this.isLoaded = false;
      delete this.model;
      var onLoad = this._onLoad.createDelegate(this, [callback || this._defaultLoadCallback], 3);
      if (this.loadMask) {
        if (Jaffa.state.PageLoader.isActive())
          Jaffa.state.PageLoader.mask(this.loadMaskText);
        else {
          var loadMaskComponent = this.loadMaskComponent || Ext.get(document.body);
          if (!loadMaskComponent.dom) {
            delete this.loadMaskComponent;
            loadMaskComponent = Ext.get(document.body);
          }
          var maskComp = loadMaskComponent.mask(this.loadMaskText, 'x-mask-loading');
          maskComp.dom.className = 'x-mask-hide';
        }
      }
      this.proxy.load(this.criteria, this.reader || new Jaffa.data.JsonDWRQueryResponseReader(), onLoad, this);
    }
  }

  /**
   * Registers a panel with a data object in the graph
   * This panel will be used to determine if fields have been modfied.
   * For a panel to participate in a 'save' operation it should implement a 'validateTab' method
   *
   * @param {Ext.Panel} panel  Panel
   * @param {Object} graphNode  (Optional) Graph Object to link panel to
   */
  ,registerPanel: function(panel, graphNode){
    panel.controller = this;
    if (this.registeredPanels.indexOf(panel) < 0) {
      this.registeredPanels.add(panel);
      //@TODO: Remove the 'panel.load' handler once the legacy code has been refactored to use the new loadData method
      if (panel.loadData)
        this.on('load', panel.loadData, panel);
      else if (panel.load)
        this.on('load', panel.load, panel);
    }
    if (graphNode){
      if (!graphNode.registeredPanels) {
        graphNode.registeredPanels = new Ext.util.MixedCollection();
      }
      if (graphNode.registeredPanels.indexOf(panel) < 0) {
        graphNode.registeredPanels.add(panel);
      }
    }

    panel.on('beforedestroy', function(pnl) {
      this.unregisterPanel(pnl, graphNode);
    }, this);
  }

  /**
   * Unregisters a panel that had been associated with a data object in the graph
   *
   * @param panel {Ext.Panel} Panel
   * @param graphNode {Object} (Optional) Graph Object panel is linked to
   */
  ,unregisterPanel: function(panel, graphNode){
    var ctrl = this;
    var localUnregister = function(p, gn) {
      if (ctrl.registeredPanels.indexOf(p)>=0) {
        ctrl.registeredPanels.remove(p);
        if (gn && gn.registeredPanels) {
          gn.registeredPanels.remove(p);
        }
        
        //@TODO: Remove the 'p.load' handler once the legacy code has been refactored to use the new loadData method
        if (p.loadData) {
          ctrl.un('load', p.loadData, p);
        }
        else if (p.load) {
          ctrl.un('load', p.load, p);
        }
        
        ctrl.fireEvent('unregister', ctrl);
      }
    };
    
    if(Ext.isFunction(panel.cascade)){
      panel.cascade(function(item) {
        if (item.findDataSource) {
          localUnregister(item, item.findDataSource());
        }else{
          localUnregister(item);
        }
      });
      localUnregister(panel, graphNode);
    }
  }



  /**
   * @private - this is used as the default callback function if it is not provided in load().
   */
  ,_defaultLoadCallback: function() {
    // Create callback handler to give error and close page if not found
    if(this.isLoaded && this.model==null) {
      Ext.MessageBox.show( {
        title: this.objectName+' '+this.loadErrorTitleText, //FIXME Load Error
        msg: this.objectName+' '+this.loadFailureMsgText, //FIXME load failure
        buttons: Ext.MessageBox.OK
      });
    }
  }


  /**
   * @private - This is called internally once the query returns
   * @param data {Object} data that was loaded
   * @param callback {function} Callback to invoke once the model is loaded
   */
  ,_onLoad : function(data, params, sucess, callback) {
    if (this.loadMask){
      var loadMaskComponent = this.loadMaskComponent||Ext.get(document.body);
      loadMaskComponent.unmask();
    }
    this.model = data;
    if (!this.fireEvent('beforeupdatepanel', this)) {
      return false;
    }
    this.registeredPanels.each(function(panel){
      var ds = null;
      if (panel.findDataSource)
        ds = panel.findDataSource(); //TODO check this shouldn't be .call(panel);
      if (!ds) {
        if (panel.id != 'Header') {
          this.unregisterPanel(panel);
          // TODO Can we also destroy panel?
        }
      }else{
        if (!ds.registeredPanels) {
          ds.registeredPanels = new Ext.util.MixedCollection();
        }
        if (ds.registeredPanels.indexOf(panel) < 0) {
          ds.registeredPanels.add(panel);
        }
      }
    }, this);
    console.debug("Loaded Data ", data, " model=",this.model);
    if (typeof callback == 'function')
      if(callback(this)===false)
        return false;
    return this.fireEvent("load", this, data);
  }


});