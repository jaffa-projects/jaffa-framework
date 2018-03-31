



/**
 * @class Jaffa.component.CRUDController
 * @extends Jaffa.component.PanelController
 *
 * This is a Controller used to perform CRUD (Create, Update and Delete) operations
 * on a graph of data objects. It has been built for general use, but within the JaffaRIA
 * framework it has been targetted to exchange data using a DWRCRUDProxy (although there is
 * a HttpCRUDProxy, and any other CRUDProxy could be created). It has also been optimized to only
 * send delta changes back for updates. This is based on the SOA data exchange concepts built into
 * the JaffaSOA module that provides a Java back end for creating SOA based Graph Services.
 */
Jaffa.component.CRUDController = Ext.extend(Jaffa.component.PanelController, {
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
   * the service invoked by the proxy. If no reader is provided the default {@link Jaffa.data.QueryResponseReader}
   * will be used as per the the JaffaSOA java interface
   * <a href="http://jaffa.cvs.sourceforge.net/viewvc/jaffa/JaffaSOA/source/java/org/jaffa/soa/graph/GraphQueryResponse.java?view=markup" target="_blank">GraphQueryResponse</a>
   */
  ,reader : undefined

  /**
   * @cfg {String} objectLabel (Mandatory) This must be provided as it is used when
   * displaying user error messages
   */
  ,objectLabel : '*Not Set!*'

  ,loadMask: false

  ,loadMaskText: 'Loading ...'

  ,loadMaskComponent: null
  
  ,saveErrorTitle: 'Save Errors'

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

  //Private variable to store the dirty state of the controller.
  //Controller is considered dirty if any of it's registered panels have changed data
  ,_isDirty : false

  /**
   * @property registeredPanels This is the list of panels that have registered with various parts of the model
   * for editing its contents. This is in assition to the _panelId attributes that are set
   * internally on the Store Records when editing a record.
   * This is used internally on a 'save' so all registered panels can be validated
   * @type Ext.util.MixedCollection
   */
  ,registeredPanels : null

  ,defaultLocalizedErrorMessage: 'Internal errors occurred, please contact your system administrator.'

  ,noChangeToSaveText: 'Data has not been changed. Save is not necessary.'

  ,noChangeToSaveTitle: 'Alert:'
  
  ,savingMaskText: 'Saving...'
    
  ,PreValidationFailedMsgText: 'Prevalidation Failed'
  
  ,alertValidationFailedTitleText: 'Validation Failed'
  
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
       * @event beforesave
       * Fires before a network request is made to save the data objects.
       * A listener may perform some preprocessing, or it may even create the saveModel.
       * The save process will be halted if the listener returns a false.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #save} function
       */
      ,'beforesave'

      /**
       * @event beforeprevalidate
       * Fires before the proxy's prevalidate is called.
       * The prevalidate process will be halted if the listener returns a false.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #save} function
       */
      ,'beforeprevalidate'

      /**
       * @event prevalidate
       * Fires after the proxy's prevalidate is called.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #save} function
       */
      ,'prevalidate'
      /**
       * @event prevalidatefailed
       * Fires after the proxy's prevalidate call throws an error message.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #_invokePrevalidate} function
       */
      ,'prevalidatefailed'
      /**
       * @event save
       * Fires before the save method's callback is called, assuming the save was successful
       * @param {Object} this
       * @param {Object} o The data object
       * @param {Object} arg The callback's arg object passed to the {@link #save} function
       */
      ,'save'
      /**
       * @event saveexception
       * Fires before the save method's callback is called, assuming there was an error on the save.
       * In this case returning a valid 'respone' array containing errors is not considered a
       * 'save exception'
       * @param {Jaffa.component.CRUDController} this the controller
       * @param {Object} response The data object
       * @param {boolean} foundPending whether it has pending events in the response.
       */
      ,'saveexception'
      /**
       * @event beforeprevalidatepostdata
       * Fires after the data on panel has been used to construct a data graph to be saved to the server
       * but before the prevalidate post action. This method gives a final chance to change the data graph to be prevalidated
       * on the server.
       * @param {Jaffa.component.CRUDController} this the controller
       * @param {Object} saveMode The data object that is to be posted to the server.
       */
      ,'beforeprevalidatepostdata'
      /**
       * @event beforepostdata
       * Fires after the data on panel has been used to construct a data graph to be saved to the server
       * but before the post action. This method gives a final chance to change the data graph to be saved
       * on the server.
       * @param {Jaffa.component.CRUDController} this the controller
       * @param {Object} saveMode The data object that is to be posted to the server.
       */
      ,'beforepostdata'
      /**
       * @event beforeupdatepanel
       * Fires after the model in controller has been updated from the loaded data but before the model is used to
       * populate the panels
       * @param {Jaffa.component.CRUDController} this the controller
       */
      ,'beforeupdatepanel'
      /**
       * @event deleted
       * Fires to signal the deletion of a root record.
       */
      ,'deleted'
      /**
       * @event dirtychanged
       * Fires when a change to a registered panel cause the controller to be registered as dirty.
       */
      ,'dirtychanged'
      /**
       * @event unregister
       * Fires when a registered panel is unregistered.
       * @param {Jaffa.component.CRUDController} this the controller
       */
      ,'unregister'
    );
    Jaffa.component.CRUDController.superclass.constructor.apply(this, arguments);
  }


  /**
   * Invoke this to load the data based on the criteria. As this is
   * asyncronous, a callback method can be provided, or the on("load") event
   * on the proxy can be used to process the data once it has been loaded
   */
  ,load : function(callback) {
    console.debug("CRUDController.load: criteria = ", this.criteria);
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
      this.proxy.load(this.criteria, this.reader || new Jaffa.data.QueryResponseReader(), onLoad, this);
    }
  }


  /**
   * Save the Graph Data Object model
   * This will cause the model to validate all registered panels
   * perform mandatory checks and other meta rules, then build up a
   * "changes only" version of the Graph model, and use the supplied
   * proxy to save this back to the web server.
   *
   * @important saveData(originalSubGraph_Array, newSubGraph_Array) on panels are called to perform the save action.
   * When sub graphs to the input graph are created in newSubGraph_Array, it should be registered in the corresponding originalSubGraph_Array.
   *
   * <pre><code>
   * // when creating a sub graph for the saveModel
   * if (originalSubGraph_Array.maintenance[3]) {
   *   var obj;
   *   if (originalSubGraph_Array.maintenance[3].registeredPanels.graphInSaveModel) {
   *     obj = originalSubGraph_Array.maintenance[3].registeredPanels.graphInSaveModel;
   *   } else {
   *     obj = Ext.ux.clone(originalSubGraph_Array.maintenance[3]);
   *     originalSubGraph_Array.maintenance[3].registeredPanels.graphInSaveModel = obj;
   *   }
   * }
   * // --------- performing updates on obj -----------
   * newSubGraph_Array.maintenance[1] = obj;
   * if (originalSubGraph_Array.maintenance[3].registeredPanels)
   *   originalSubGraph_Array.maintenance[3].registeredPanels.graphInSaveModel = obj;
   * </code></pre>
   *
   * @param {function} callbackOk  Method to invoke if the save was sucessful, if not
   * passed the default callback handler will be used which performs a load() on this controller
   * @param {function} callbackError Method to invoke if the save failed
   */
  ,save : function(callbackOk,callbackError) {
    this.saveModel = null;
    if (this.fireEvent("beforesave", this) !== false) {
      // validate all registered tabs to make sure we can save
      if(!this._validatePanels(callbackOk,callbackError, this.save.createDelegate(this,[callbackOk,callbackError])))
        return false;

      // pre-process "model" to create a "dirty" data view to save,
      // removing the MixedCollections
      if (this.saveModel)
        this.saveModel.isChanged = true;
      else
        this._buildSaveModel();

      // invoke the service only if the model has changed
      if (this.saveModel && this.saveModel.isChanged) {
        this._invokeSave(callbackOk, callbackError);
      } else {
        Ext.MessageBox.alert(this.noChangeToSaveTitle, this.noChangeToSaveText);
        ClassMetaData.convertArrays(this.model.itemAt(0));
        this.registeredPanels.each(function(p){
          if (typeof p.setDirty === 'function'){
            p.setDirty(false);
          }
        });
      }
    }
  }

   /** @method preValidate performs prevalidation on the changed data
   *   prevalidation will no be called if the proxy has no prevalidation method
   *   The beforeprevalidation event is fired before any validation occurs
   *   The prevalidation event occurs after the server has responded but before the callback is called.
   *   You can exit prevalidation in the beforeprevalidation event handler by returning false.
   *   @param {function} callbackOk  Method to invoke if the prevalidation was sucessful and no server errors were returned,
   *   @param {function} callbackError Method to invoke if server errors were returned or the prevalidation failed
   */
  ,prevalidate : function(callbackOk, callbackError) {
    this.saveModel = null;
    if (this.fireEvent("beforeprevalidate", this) !== false) {
      if(!this._validatePanels(callbackOk,callbackError , this.prevalidate.createDelegate(this,[callbackOk,callbackError]))){
         return false;
      }
      this._buildSaveModel();
      if (this.model && this.model.length>0) this.deleteGraphInSaveModel(this.model.itemAt(0));
      if (this.proxy.prevalidate && this.saveModel) {
        this._invokePrevalidate(callbackOk, callbackError);
      }else if(callbackOk === 'function'){
        callbackOk();
      }
    }
  }

   /** @method isDirty return the dirty state of the controller
   */
  ,isDirty : function(){
    return this._isDirty;
  }

   /** @method setDirtyis used to chage the dirty state of the controller
   *   The dirtychanged event is fired if new dirty state is different from the current state
   *   @param {boolean} value  The value the dirty state should be changed to
   */
  ,setDirty : function(value){
    if (!this._isDirty && value){
      this.registeredPanels.each(function(panel){
        if (panel.getTopToolbar && panel.getTopToolbar() && panel.getTopToolbar().getComponent('save'))
          panel.getTopToolbar().getComponent('save').enable();
      });
      this.fireEvent('dirtychanged', this, value);
    }

    if (this._isDirty && !value){
      this.registeredPanels.each(function(panel){
        if (panel.getTopToolbar && panel.getTopToolbar() && panel.getTopToolbar().getComponent('save'))
          panel.getTopToolbar().getComponent('save').disable();
      });
      this.fireEvent('dirtychanged', this, value);
    }
    this._isDirty = value;
  }

   /**
   * @private - This is called internally to invoke the prevalidation method on the proxy.
   */
  ,_invokePrevalidate: function(callbackOk,callbackError){
    var me = this;
    if (this.fireEvent('beforeprevalidatepostdata', this) !== false) {
      console.debug("CRUDController.save: saveModel = ", this.saveModel);
      this.proxy.prevalidate(this.saveModel[0], function(serverResponse){
        if (serverResponse && !(serverResponse.errors && serverResponse.errors.length > 0)) {
          me.fireEvent("prevalidate", this, serverResponse);
          if (typeof callbackOk == 'function') {
            callbackOk(serverResponse);
          }
        } else if (me.fireEvent("prevalidatefailed", me,serverResponse) !== false){
          if (typeof callbackError === 'function') {
            callbackError(serverResponse);
          }
          else {
            Ext.MessageBox.show({
              titleToken: 'label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error',
              msg: (serverResponse) ? Ext.util.Format.htmlEncode(serverResponse.errors[0].localizedMessage) : this.PreValidationFailedMsgText,
              buttons: Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR
            });
          }
        }
      });
    }
  }

  /**
   * @private - This is called internally to invoke the save method on the proxy.
   */
  ,_invokeSave: function (callbackOk, callbackError) {
    if (this.fireEvent('beforepostdata', this) !== false) {
      console.debug("CRUDController.save: saveModel = ", this.saveModel);
      Ext.get(document.body).mask(this.savingMaskText, 'x-mask-loading');
      this.proxy.save(
        this.saveModel,
        this._saveOk.createDelegate(this,[callbackOk,callbackError],0),
        this._saveError.createDelegate(this,[callbackOk,callbackError],0),
        this);
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
    if (this.isDirty() && panel.getTopToolbar && panel.getTopToolbar() && panel.getTopToolbar().getComponent('save'))
          panel.getTopToolbar().getComponent('save').enable();

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
        title: this.objectName+' '+this.loadErrorTitleText, //FIXME 'Load Error'
        msg: this.objectName+' '+this.loadFailureMsgText, //FIXME 'load failure'
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
    if (this.isDirty()) this.setDirty(false);
    if (data && data.records && typeof data.records == 'object' && data.records.length > 0) {
      // pre-process all data and convert arrays to MixedCollections
      this.model = ClassMetaData.convertArrays(data.records);
      console.debug("Converted To Collection", data.records, this.model);
      this.isLoaded = true;
    } else
      this.model = null;
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


  /**
   * @private - Handle Save Sucesses
   *
   * Need to parse out the response object as it can have "embedded" errors
   * as these are not considered "save errors".
   *
   * These embedded errors are of 3 types
   * - Application Errors that should be reported to the end user
   * - Pending Event Exceptions, which infer additional detail are needed when saving
   * - Warning Event Exceptions, which infer the user should be warn about something prior
   *   to continuing with the save operation.
   *
   * If there are no embedded errors, each registered panel will have its 'tabSaveSucess()'
   * method called, otherwise if there are error each panels 'tabSaveFail()' will be called
   */
  ,_saveOk: function(cbOk, cbError, response) {
      console.debug("CRUDController._saveOK: response=",response);
      Ext.get(document.body).unmask();

      // Remove saveModel node after save
      this.saveModel = null;

      // After Save convert arrays back to MixedCollections
      if(this.model!=null)
        ClassMetaData.convertArrays(this.model.itemAt(0));

      var ok = true;
      var msg = '';
      var foundPending = false;
      var singleObjectResponse = false;
      if (response) {
        if (!response.length) {
          var resp = [];
          resp[0] = response;
          response = resp;
          singleObjectResponse = true;
        }
        for (var i = 0; i < response.length; i++) {
          var responseEl = response[i];
          if (responseEl.errors && (responseEl.errors.applicationExceptionArray || responseEl.errors.length > 0)) {
            // ApplicationExceptions
            var appExps = responseEl.errors.applicationExceptionArray ? responseEl.errors.applicationExceptionArray : responseEl.errors;
            for (var j = 0; j < appExps.length; j++) {
              var appExp = appExps[j];
              ok = false;
              var type = appExp.javaClassName ? appExp.javaClassName : appExp.type;
              if (type === "org.jaffa.soa.rules.PendingEventException" || type === "org.jaffa.soa.rules.WarningEventException") {
                foundPending = true;
                if (!responseEl._pendingEvents)
                  responseEl._pendingEvents = [];
                responseEl._pendingEvents[responseEl._pendingEvents.length] = {
                  event: appExp.arguments[0],
                  params: appExp.params,
                  text: appExp.localizedMessage,
                  warn: (type === "org.jaffa.soa.rules.WarningEventException")
                };
              } else
                msg += (Ext.util.Format.htmlEncode(appExp.localizedMessage) || this.defaultLocalizedErrorMessage) + '\n<br>';
            }
          } else if (responseEl.runtimeError) {
            // FrameworkException
            ok = false;
            msg += Ext.util.Format.htmlEncode(responseEl.runtimeError.localizedMessage) + '\n<br>';
          }
        }
      } else {
        this.processEventGraphs = null;
        // this.registeredPanels = null;
      }

      // notify registered panels about save fail/success
      if (this.registeredPanels) {
        if (ok) {
          // On Save Success
          this.registeredPanels.each(function(panel){
            if (panel.onSaveSuccess) {
              panel.onSaveSuccess(response);
            }
          });
        } else {
          // On Save Failure
          this.registeredPanels.each(function(panel){
            if (panel.onSaveFailed) {
              panel.onSaveFailed(response);
            }
          });
        }
      }

      if (!foundPending && this.model) {
        this.deleteGraphInSaveModel(this.model.itemAt(0));
        if (this.model.itemAt(0) && this.model.itemAt(0).deleteObject) delete this.model.itemAt(0).deleteObject;
      }

      if (ok) {
        console.debug(this.objectName," Service Saved OK");
        this.fireEvent("save", this, response);
        // CallBack to post save method
        if (typeof cbOk == 'function'){
          cbOk(this.model, response);
        } else {
          this.defaultSaveOkReload(this.model, response);
        }
      } else {
        if (msg) {
          // CallBack to post save method
          Ext.MessageBox.show( {
              title : this.saveErrorTitle,
              msg : msg,
              // width : 400,
              buttons : Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR
          });
          // If application exception occurs then remove all process events
          this.processEventGraphs = null;
        } else if (foundPending) {
          // Only pending events came back. Invoke the default pending-event handler, but only in the absence of an error callback.
          if (typeof cbError != 'function')
            this._handlePendingEvents(cbOk, cbError, response,singleObjectResponse);
        }
        if (typeof cbError == 'function')
          cbError(this.model, response);
        return this.fireEvent("saveexception",this, response, foundPending);
      }
  }

  /**
   *  can be overridden to allow controller being used in the context of other than create/update mode. 
   *  One example is when the controller is used in create/search mode, key values should not be assigned to criteria object before reload.
   */
  ,defaultSaveOkReload: function(model, response) {
    if(this.criteria && response && ClassMetaData[Jaffa.data.Util.get(response, '0.source.className')]){
      var key = ClassMetaData[response[0].source.className].key;
      if(Ext.isArray(key)){
        Ext.each(key,function(k){
          if(response[0].source[k] && !this.criteria[k]){
            this.criteria[k] = {values : [response[0].source[k]] };
          }
        },this)
      } else if(key && response[0].source[key] && !this.criteria[key]){
        this.criteria[key] = {values : [response[0].source[key]] };
      }
    }
    this.load();
  }

  /**
   * @private - Handle Save Error
   */
  ,_saveError : function(cbOk,cbError,response, ex) {
      // delete the graphInSaveModel
      this.deleteGraphInSaveModel(this.model.itemAt(0));
      // Handle is something REAL BAD happens
      console.debug("Internal Errors on Save",response, ex);
      Ext.get(document.body).unmask();
      var txt = Ext.util.Format.htmlEncode(response);
      if(ex) {
        if((ex.cause && ex.cause.applicationExceptionArray) ||
          ((response==null||response=="") && ex.cause) ||
          (ex.cause && ex.javaClassName.match(/^java.+/) && ex.cause.localizedMessage))
          ex=ex.cause;
        if(ex.applicationExceptionArray) {
        txt="";
          var e = ex.applicationExceptionArray;
          for (var j=0;j<e.length;j++) {
            txt += Ext.util.Format.htmlEncode(e[j].localizedMessage) + '\n<br>';
            }
        } else {
          // console.error("Internal Error: ", ex);
          txt = this.saveErrorMsgText;
        }
      }

      Ext.MessageBox.show( {
        title : this.saveErrorTitleText,
        msg : txt,
        buttons : Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });

      if (typeof cbError == 'function')
        cbError(this.model, response);

      return this.fireEvent("saveexception", this, response, false);
  }

  ,deleteGraphInSaveModel: function(obj) {
    if (!obj) return;
    if (obj.registeredPanels) delete obj.registeredPanels.graphInSaveModel;
    if (Ext.isArray(obj)) {
      for (var i=0; i<obj.length; i++) {
        this.deleteGraphInSaveModel(obj[i]);
      }
    } else if (typeof obj.each == 'function') {
      obj.each(function(o) {
        this.deleteGraphInSaveModel(o);
      }, this);
    } else if (obj.className) {
      for (var i in obj) {
        if (i!='registeredPanels' && typeof obj[i] == 'object') this.deleteGraphInSaveModel(obj[i]);
      }
    }
  }

  /**
   * @private - Validate all registered tabs to make sure we can save
   */
  ,_validatePanels : function(callbackOk,callbackError,duplicateValCallBackFn) {
      var ok = true, ctrl = this;
      if (this.registeredPanels) {
        this.registeredPanels.each(function(panel){
          if (panel.validateData) {
            var saveStatus = {
              status: '',
              message: ''
            };
            var isValid = panel.validateData(saveStatus);
            if (isValid === false || (typeof isValid === 'string' && isValid.length > 0)) {
              try {
                //Find the fields that caused an error.
                var errorFields = panel.findBy(function(f) {
                  if (f.allowBlank == false && !f.hidden && !f.textOnly && (f.getValue() == null || (f.getValue() == ""&&f.getValue() != '0'))) {
                    return true;
                  }
                  if (f.minLength>=0 && !f.textOnly && f.getValue()) {
                    if ((typeof f.getValue().getByteLen === 'function') ? f.getValue().getByteLen()<f.minLength : f.getValue().length < f.minLength)  {
                      // getByteLen() implement UTF-8 string length calculation
                      return true;
                    } 
                  }
                  if (f.maxLength>=0 && !f.textOnly && f.getValue()) {
                    if ((typeof f.getValue().getByteLen == 'function') ? f.getValue().getByteLen()>f.maxLength : f.getValue().length > f.maxLength)  // getByteLen() implement UTF-8 string length calculation 
                      return true;
                  }
                  if (f.xtype && f.xtype.toLowerCase().indexOf('findercomb')>=0 && !f.isValueValid) {
                    return true;
                  }
                }, this);
                //Bubble up from error field and activate any tab panels. This should make the first error field visible.
                var firstErrorField = panel;
                if (errorFields && errorFields.length > 0)
                  firstErrorField = errorFields[0]

                if (firstErrorField.ownerCt && firstErrorField.ownerCt.activate)firstErrorField.activate();
                var childComp = firstErrorField;
                firstErrorField.ownerCt.bubble(
                  function(comp){
                    if (comp.setActiveTab) comp.setActiveTab(childComp);
                    childComp = comp;
                  }
                )

              } catch (e) {
                // this allows to register panels that are not inside of tabMaster
              }
              var response = {};
              if (saveStatus && saveStatus.message && saveStatus.message.length > 0)
                response.validationError = saveStatus.message;
              else if (typeof isValid === 'string' && isValid.length > 0)
                response.validationError = isValid;
              if (callbackError && typeof callbackError =='function')
                callbackError(this.model, response);
              else if (response.validationError) {
                console.debug("_validatePanels Failed.", response);
                Ext.get(document.body).unmask();
                Ext.MessageBox.alert(ctrl.alertValidationFailedTitleText, response.validationError);
              }
              ok = false;
              return false;
            } else if (panel._duplicateKeyValInProgress){
               //Save halted because of in progress duplicate key validation
              panel.on('duplicatekeyvalcomplete', function(el,data){
                 if((!data || !data.records || data.records.length == 0) && Ext.isFunction(duplicateValCallBackFn)){
                   //Calling corresponding function again since no duplicate records are found (Resuming Operation)
                   duplicateValCallBackFn();
                 }
              },panel,{single :true})
               ok = false;
               return false;
            }
          }
        });
      }
      return ok;
  }


  /**
   * @private
   *
   * This takes the Graph Object 'model', and returns an new instance of it with all the
   * modified fields set. This value is cached in the 'saveModel' property.
   */
  ,_buildSaveModel: function() {
      // pre-process "model" to create a "dirty" data view to save,
      // removing the MixedCollections
      this.saveModel = this._extractChangedData(ClassMetaData.convertCollections(this.model));
      if (this.processEventGraphs && this.processEventGraphs != null) {
        if (this.saveModel.length == 0) {
          this.saveModel[0] = new Object();
          this.saveModel[0].className = this.model.itemAt(0).className;
          if (typeof ClassMetaData[this.saveModel[0].className].key == 'object' && ClassMetaData[this.saveModel[0].className].key > 0)
            for (var i = 0; i < ClassMetaData[this.saveModel[0].className].key.length; i++) {
              this.saveModel[0][ClassMetaData[this.saveModel[0].className].key[i]] = this.model.itemAt(0)[ClassMetaData[this.saveModel[0].className].key[i]];
            }
          else
            this.saveModel[0][ClassMetaData[this.saveModel[0].className].key] = this.model.itemAt(0)[ClassMetaData[this.saveModel[0].className].key];
        }
        this.saveModel[0].processEventGraphs = this._extractAllData(this.processEventGraphs);
        this.saveModel.isChanged = true;
      }
    }


  /**
   * @private ExtractChangedData Function.
   *
   * This first goes through the registed panels invoking the 'tabSave()' method incase
   * the panel needs to do some local changes to the model prior to cloning.
   *
   * The saveData() is called with parameters 'originalObject' and 'newObject', and it is
   * assumed that the fields modified in the panel and set on the 'newObject'. This is typically
   * done on a panel using the {#link getModifiedPanelFields(panel, newData, oldData)}
   *
   * This performs a deep clone by recursing through nested objects and arrays.
   *
   * @DEPRECATES Jaffa.DWRService.clone
   */
  ,_extractChangedData: function(myObj){
    if (myObj == null)
      return null;

    if (myObj.isNew) {
      if (myObj.registeredPanels && myObj.registeredPanels.getCount() == 0) {
        // when registeredPanels is on a new graph object, this new graph is created on the orignal graph.
        // If its registeredPanels is empty, this object this object is not backed by any panels; it should
        // be deleted. 
        return null;
      }
    }

    var objectClone;
    if (myObj.registeredPanels && myObj.registeredPanels.graphInSaveModel) {
      objectClone = myObj.registeredPanels.graphInSaveModel;
    } else if (myObj.isNew){
      objectClone = this._extractAllData(myObj);
      objectClone.isChanged = true;
    } else {
      objectClone = new myObj.constructor();
      objectClone.isChanged = false;
    }
    if (myObj.registeredPanels) myObj.registeredPanels.graphInSaveModel = objectClone;

    // Loop through objects registeredPanels and save changed values from the panel
    if (myObj.registeredPanels && myObj.registeredPanels.getCount() > 0)
      myObj.registeredPanels.each(function(panel) {
        if (panel.isNew && panel.createData) {
          // panels with new data are registered at the array level, but only return a single object to be added to the array
          var childChanged = panel.createData(myObj, objectClone);
          if (childChanged)
            objectClone.isChanged = true;
        } else if (panel.saveData) {
          var childChanged = panel.saveData(myObj, objectClone);
          if (childChanged)
            objectClone.isChanged = true;
        }
      });

    if (!myObj.isNew) {
      if (myObj.propertyChanged) {
        objectClone = myObj;
        objectClone.isChanged = true;
      } else {
        // loop through arrays within the object (looking for registered panels)
        if (typeof myObj == 'object' && myObj.length && myObj.length > 0)
          for (var i = 0; i < myObj.length; i++) {
            // @TODO: Lookup existing object in objectClone, based on key/createKey and pass it, if found
            this._bindTargetGraph(myObj[i], objectClone);
            var oc = this._extractChangedData(myObj[i]);
            if (oc && oc.isChanged) {
              delete oc.isChanged;
              objectClone.isChanged = true;
              if (objectClone.indexOf(oc)<0) objectClone.push(oc);
            }
          }

        if (myObj.deleteObject){
          objectClone.deleteObject = true;
          objectClone.isChanged = true;
        } else {
        // loop through other objects within the object (looking for registered panels)
          for (var property in myObj) {
            if (typeof myObj[property] == 'object' && !this._isNumber(property) && property !== 'registeredPanels') {
              if (objectClone[property] && objectClone[property]!=null) {
                  // Don't process nodes added to graph from registered panels.
              } else {
                console.debug('Cloning ', property);
                var ocs = this._extractChangedData(myObj[property]);
                if (ocs && ocs.isChanged) {
                  delete ocs.isChanged;
                  objectClone[property] = ocs;
                  objectClone.isChanged = true;
                }
              }
            }
          }
        }

        // If clone includes changes then add key field properties
        if (objectClone.isChanged)
          if (myObj.className) {
            if (typeof ClassMetaData[myObj.className].key == 'object' && ClassMetaData[myObj.className].key > 0)
              for (var i = 0; i < ClassMetaData[myObj.className].key.length; i++) {
                if (!objectClone[ClassMetaData[myObj.className].key[i]])
                  objectClone[ClassMetaData[myObj.className].key[i]] = myObj[ClassMetaData[myObj.className].key[i]];
              }
            else if (Ext.isArray(ClassMetaData[myObj.className].key)) {
              for (var i=0; i<ClassMetaData[myObj.className].key.length; i++) {
                var k = ClassMetaData[myObj.className].key[i];
                if (Jaffa.data.Util.get(objectClone, k)==null)
                  Jaffa.data.Util.set(objectClone, k, Jaffa.data.Util.get(myObj, k));
              }
            } else
              if (Jaffa.data.Util.get(objectClone, ClassMetaData[myObj.className].key)==null)
                Jaffa.data.Util.set(objectClone, ClassMetaData[myObj.className].key, Jaffa.data.Util.get(myObj , ClassMetaData[myObj.className].key));
          }
      }
    }

    return objectClone;
  }

  /**
   * Lookup existing object in the new object array, based on key/createKey and bind it to the
   * corresponding original object.
   * This becomes neccesary when the application developer forgot to make the binding in saveData()
   * on their panel.
   *
   * @private
   *
   * @param {Object} origObj
   * @param {Object} newObjArray
   */
  ,_bindTargetGraph: function(origObj, newObjArray) {
    if (!origObj.registeredPanels ||origObj.registeredPanels.graphInSaveModel) return;
    if (!ClassMetaData[origObj.className]) return;
    var key = ClassMetaData[origObj.className].key;
    if (!key) return;
    var altKey = ClassMetaData[origObj.className].createKey;
    if (origObj[key]) {
      for (var i=0; i<newObjArray.length; i++) {
        if (origObj[key] == newObjArray[i][key]) {
          origObj.registeredPanels.graphInSaveModel = newObjArray[i];
          break;
        }
      }
    } else if (origObj[altKey]) {
      for (var i=0; i<newObjArray.length; i++) {
        if (origObj[altKey] == newObjArray[i][altKey]) {
          origObj.registeredPanels.graphInSaveModel = newObjArray[i];
          break;
        }
      }
    }
  }

  /**
   * @private
   *
   * Full clone of a graph object (excluding any 'registeredPanels' property)
   * This is a deep copy at it will recurse if there are nested objects
   *
   * @DEPRECATES Jaffa.DWRService.fullclone
   */
  ,_extractAllData: function(myObj){
    if (myObj == null)
      return null;

    if ('object' !== typeof myObj) {
      return myObj;
    }
    var objectClone = new myObj.constructor();

    if (myObj.map && typeof myObj.map == 'object') {
      for (var i = 0; i < myObj.items.length; i++) {
        objectClone.add(myObj.keys[i], this._extractAllData(myObj.items[i]));
        var a = 1;
      }
    } else {
      if (myObj.dateFormat) {
        objectClone = myObj;
      } else {
        for (var property in myObj) {
          if (property != 'registeredPanels') {
            if (typeof myObj[property] == 'object')
              objectClone[property] = this._extractAllData(myObj[property]);
            else
              objectClone[property] = myObj[property];
          }
        }
      }
    }
    if (myObj.registeredPanels) myObj.registeredPanels.graphInSaveModel = objectClone;

    return objectClone;
  }

  /**
   * @private Simple function to see if an object is a number
   */
  ,_isNumber:function(sText) {
      var ValidChars = "0123456789.";
      var IsNumber = true;
      var Char;
      for (var i = 0; i < sText.length && IsNumber == true; i++) {
        Char = sText.charAt(i);
        if (ValidChars.indexOf(Char) == -1) {
          IsNumber = false;
        }
      }
      return IsNumber;
  }

  /**
   * @private Handles PendingEventException and WarningEventException by invoking the appropriate registered panels
   */
  ,_handlePendingEvents: function (cbOk, cbError, response, singleObjectResponse) {
    // Rearrange the pending events array in each response element to comply with the order of events passed in the pendingEventConfig
    this._rearrangePendingEvents(response);

    // Render the pending events in a modal window
    var window = new Jaffa.maintenance.PendingEventsWindow({
      controller: this,
      cbOk: cbOk,
      cbError: cbError,
      response: response,
      singleObjectResponse: singleObjectResponse
    });
    window.show();
  }

  /**
   * @private Rearrange the pending events array in each response element to comply with the order of events passed in the pendingEventConfig
   */
  ,_rearrangePendingEvents: function (response) {
    if (response && response.length > 0 && this.pendingEventConfig && this.pendingEventConfig.events && this.pendingEventConfig.events.length > 0) {
      for (var i = 0, iLen = response.length; i < iLen; i++) {
        var responseEl = response[i];
        if (responseEl._pendingEvents && responseEl._pendingEvents.length > 0) {
          var newPendingEvents = []; //holds the rearranged pending events
          for (var j = 0, jLen = this.pendingEventConfig.events.length; j < jLen; j++) {
            var config = this.pendingEventConfig.events[j];
            for (var k = 0; k < responseEl._pendingEvents.length; k++) {
              var pendingEvent = responseEl._pendingEvents[k];
              if ((config.warn && pendingEvent.warn) || (!config.warn && !pendingEvent.warn && config.event == pendingEvent.event)) {
                newPendingEvents[newPendingEvents.length] = pendingEvent;
                responseEl._pendingEvents.splice(k, 1);
                k--;
              }
            }
          }
          newPendingEvents = newPendingEvents.concat(responseEl._pendingEvents); //concatenate the events which may not have defined in pendingEventConfig
          responseEl._pendingEvents = newPendingEvents;
        }
      }
    }
  }
});