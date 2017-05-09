

/**
 * Produces a controller 
 */
Jaffa.component.CopyController = Ext.extend(Jaffa.component.PanelController, {
  /** Text objects that can be replaced for internationalization */
  
  cloneFailureTitleText: 'Clone Failed',
  cloneErrorTitleText: "Clone Error"
  ,cloneFieldErrorMsgTpl: "Error Cloning Field: {0}"
  ,cloneErrorMsgText: "An Internal Error occurred during the clone operation"
  
  /** @cfg {Ext.data.CRUDProxy} proxy (Mandatory) This proxy provides clone
   *  function to read and write to an extenal service
   */
  ,proxy: null
  
  /**
   * @cfg {Object} criteria  This is the criteria object that will be passed to the
   * clone function of the proxy to get the data to be modified by this controller
   */
  ,criteria : null

  /**
   * @cfg {Object} inputData  This is the JSON object that will be passed to the
   * clone function of the proxy to set the data to the retrieved graph
   */
  ,inputData : null

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


  /**
   * @property inputPanel This is the panel where new input is from
   * @type Ext.Panel
   */
  ,inputPanel: null

  ,cloningMaskText: 'Cloning...'
  
  ,constructor: function(config) {
    Ext.apply(this, config);
    Jaffa.component.CopyController.superclass.constructor.apply(this, arguments);
    this.addEvents(
      /**
       * @event beforeclone
       * Fires before a network request is made to clone the data objects.
       * @param {Object} this
       * @param {Object} params The params object passed to the {@link #clone} function
       */
      'beforeclone'
      /**
       * @event clone
       * Fires before the clone method's callback is called, assuming the clone was successful
       * @param {Object} this
       * @param {Object} o The data object
       * @param {Object} arg The callback's arg object passed to the {@link #clone} function
       */
      ,'clone'
      /**
       * @event cloneexception
       * Fires before the clone method's callback is called, assuming there was an error on the clone.
       * In this case returning a valid 'respone' array containing errors is not considered a 
       * 'clone exception'
       * @param {Jaffa.component.CopyController} this the controller
       * @param {Object} response The data object
       * @param {boolean} foundPending whether it has pending events in the response.
       */
      ,'cloneexception'
    );
  }
  
  /**
   * Clones an existing object to database with supplemented data.
   * 
   * @param {Object} callbackOk
   * @param {Object} callbackError
   */
  ,clone: function(callbackOk,callbackError) {
    if (this.fireEvent("beforeclone", this) !== false) {
      // validate all registered tabs to make sure we can clone
      if(!this._validatePanels(callbackOk,callbackError))
        return false;

      this._buildInputData();

      // @FIXME: Use label for 'Cloning...'
      Ext.get(document.body).mask(this.cloningMaskText, 'x-mask-loading');
      // TODO: need to look at Jaffa.data.DWRProxy.load and Jaffa.data.DWRCRUDProxy.save to implement this call
      this.proxy.copy(
        this.criteria, 
        this.inputData,
        this._cloneOk.createDelegate(this,[callbackOk,callbackError],0),
        this._cloneError.createDelegate(this,[callbackOk,callbackError],0),
        this);
    }
  }

  /**
   * @private - Handle Clone Sucesses
   *
   * Need to parse out the response object as it can have "embedded" errors
   * as these are not considered "clone errors".
   *
   * These embedded errors are of 3 types
   * - Application Errors that should be reported to the end user
   * - Pending Event Exceptions, which infer additional detail are needed when saving
   * - Warning Event Exceptions, which infer the user should be warn about something prior 
   *   to continuing with the clone operation.
   * 
   * If there are no embedded errors, each registered panel will have its 'tabCloneSucess()'
   * method called, otherwise if there are error each panels 'tabCloneFail()' will be called
   */
  ,_cloneOk: function(cbOk, cbError, response) {
      console.debug("CopyController._cloneOK: response=",response);
      Ext.get(document.body).unmask();

      var ok = true;
      var msg = '';
      var foundPending = false;
      if (response) {
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
                msg += appExp.localizedMessage + '\n<br>';
            }
          } else if (responseEl.runtimeError) {
            // FrameworkException
            ok = false;
            msg += responseEl.runtimeError.localizedMessage + '\n<br>';
          }
        }
      }

      // notify registered panels about clone fail/success
      if (ok) {
        // On Clone Success
        if (this.inputPanel.onCloneSuccess) {
          this.inputPanel.onCloneSuccess(response);
        }
      } else {
        // On Clone Failure
        if (this.inputPanel.onCloneFailed) {
          this.inputPanel.onCloneFailed();
        }
      }

      if (ok) {
        console.debug(this.objectName," Service Cloned OK");
        this.fireEvent("clone", this);
        // CallBack to post clone method
        if (typeof cbOk == 'function')
          cbOk(response);
      } else {
        if (msg) {
          // CallBack to post clone method
          Ext.MessageBox.show( {
              title : this.cloneFailureTitleText,//FIXME
              msg : msg,
              // width : 400,
              buttons : Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR
          });
        } else if (foundPending) {
          // Only pending events came back. Invoke the default pending-event handler, but only in the absence of an error callback.
          if (typeof cbError != 'function')
            this._handlePendingEvents(cbOk, cbError, response);
        }
        if (typeof cbError == 'function')
          cbError(response);
        return this.fireEvent("cloneexception", this, response, foundPending);        
      }
  }



  /**
   * @private - Handle Clone Error
   */
  ,_cloneError : function(cbOk,cbError,response, ex) {
      // Handle is something REAL BAD happens
      console.debug("Internal Errors on Clone",response, ex);
      Ext.get(document.body).unmask();
      var txt = response;
      if(ex) {
        if((ex.cause && ex.cause.applicationExceptionArray) ||
          ((response==null||response=="") && ex.cause) ||
          (ex.cause && ex.javaClassName.match(/^java.+/) && ex.cause.localizedMessage))
          ex=ex.cause;
        if(ex.applicationExceptionArray) {
        txt="";
          var e = ex.applicationExceptionArray;
          for (var j=0;j<e.length;j++) {
            txt += e[j].localizedMessage + '\n<br>';
            }
        } else {
          // console.error("Internal Error: ", ex);
          txt = this.cloneErrorMsgText;
        }
      }

      Ext.MessageBox.show( {
        title : this.cloneErrorTitleText,
        msg : txt,
        buttons : Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });

      if (typeof cbError == 'function')
        cbError(response);
        
      return this.fireEvent("cloneexception", this, response, false);
  }


  /**
   * @private - Validate all fields in input panel to make sure we can clone
   */
  ,_validatePanels : function(callbackOk,callbackError) {
      var ok = true;
      if (this.inputPanel) {
        if (this.inputPanel.validateData) {
          var cloneStatus = {
            status: '',
            message: ''
          };
          var isValid = this.inputPanel.validateData(cloneStatus);
          if (isValid === false || (typeof isValid === 'string' && isValid.length > 0)) {
            var response = {};
            if (cloneStatus && cloneStatus.message && cloneStatus.message.length > 0)
              response.validationError = cloneStatus.message;
            else if (typeof isValid === 'string' && isValid.length > 0)
              response.validationError = isValid;
            if (callbackError)
              callbackError(this.model, response);
            else if (response.validationError) {
              console.debug("_validatePanels Failed.", response);
              Ext.get(document.body).unmask();
              Ext.MessageBox.alert(this.cloneFailureTitleText, response.validationError);
            }
            ok = false;
            return false;
          }
        }
      }
      return ok;
  }

  /**
   * @private Handles PendingEventException and WarningEventException by invoking the appropriate registered panels
   */
  ,_handlePendingEvents: function (cbOk, cbError, response) {
    // Rearrange the pending events array in each response element to comply with the order of events passed in the pendingEventConfig
    this._rearrangePendingEvents(response);
    var finishHandler = function (panel) {


      this._buildInputData();

      for (var i = 0, len = panel.response.length; i < len; i++) {
        var processEventGraphs = panel.response[i].source.processEventGraphs;
        if (!processEventGraphs)
          panel.response[i].source.processEventGraphs = processEventGraphs = [];
        if (i>0 && panel.applyToAllCheckbox && panel.applyToAllCheckbox.getValue()) {
          panel.items.get(0).resetPendingEventParams(panel.response[i]);
          panel.items.get(0).addProcessEvents(processEventGraphs);
        } else {
          panel.items.get(i).addProcessEvents(processEventGraphs);
        }
        this.inputData.processEventGraphs=processEventGraphs;
      }
      if (panel.applyToAllCheckbox && panel.applyToAllCheckbox.getValue()) {
        panel.items.get(0).resetPendingEventParams(panel.response[0]);
      }


      panel.close();
      // @FIXME: Use label for 'Cloning...'
      Ext.get(document.body).mask(this.cloningMaskText, 'x-mask-loading');
      // TODO: need to look at Jaffa.data.DWRProxy.load and Jaffa.data.DWRCRUDProxy.save to implement this call
      this.proxy.copy(
        this.criteria,
        this.inputData,
        this._cloneOk.createDelegate(this,[cbOk,cbError],0),
        this._cloneError.createDelegate(this,[cbOk,cbError],0),
        this);
    };

    // Render the pending events in a modal window
    var window = new Jaffa.maintenance.PendingEventsWindow({
      controller: this,
      cbOk: cbOk,
      cbError: cbError,
      response: response,
      finishHandler: finishHandler.createDelegate(this)
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
  /**
   * @private
   * 
   * This takes the input panel and creates a Graph Object of the fields to pass
   */
  ,_buildInputData: function() {
      this.inputData = {};
      this.getModifiedPanelFields(this.inputPanel, this.inputData);
    }

});
