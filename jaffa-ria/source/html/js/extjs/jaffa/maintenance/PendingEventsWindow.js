
/**
 * @class Jaffa.maintenance.PendingEventsWindow
 * @extends Ext.ModalWindow
 *
 * An instance of this ModalWindow can be used to render Warnings/PendingEvents raised during a CRUD operation.
 * A wizard-like screen will be rendered if multiple graphs have Warnings/PendingEvents.
 */
Jaffa.maintenance.PendingEventsWindow = Ext.extend (Ext.ModalWindow, {
  /**
   * @cfg applyToAllText {string} the text label of the checkbox
   */
  applyToAllText: '  Apply to all',
  /**
   * @cfg allowApplyToAll {bool} if false, apply to all checkbox will not show. 
   */
  allowApplyToAll: true,
  /**
   * @cfg closable {bool} set to false so that users are forced to use the buttons which handles controller state correctly. 
   */
  closable: false,
  
  /**
   * @cfg singleObjectResponse {bool} if true, the save model/response will contain a single object rather than an array
   */
  
  singleObjectResponse: false,
    
  /**
   * Constructs an instance.
   * @param {Object} config At the bare minimum, the config should contain at least the controller and response elements.
   *        The controller should contain the pendingEventConfig for configuring this window.
   */
  finishButtonText: 'Finish',
  
  cancelButtonText: 'Cancel', 
  
  previousButtonText: 'Previous',
  
  nextButtonText: 'Next',
  
  continueButtonText: 'Continue',
  
  warningsPendingEventsTitleText: 'Warnings / Pending Events',
	
  constructor: function (config) {
    this.controller = config.controller;
    this.cbOk = config.cbOk;
    this.cbError = config.cbError;
    this.response = config.response;
    if (config.finishHandler) this.finishHandler = config.finishHandler;

    // Create elements for the bottom toolbar
    this.previousButton = this.response.length == 1 ? undefined : new Ext.Button({
      iconCls: 'previous',
      text: this.previousButtonText,
      disabled: true,
      scope: this,
      handler: function () {
        this.renderPanel(this.findCurrentIndex() - 1);
      }
    });
    this.nextButton = this.response.length == 1 ? undefined : new Ext.Button({
      iconCls: 'next',
      text: this.nextButtonText,
      disabled: true,
      scope: this,
      handler: function () {
        var index = this.findCurrentIndex();
        if (this.items.get(index).validate() !== false){
          this.renderPanel(index + 1);
        }
      }
    });
    this.finishButton = new Ext.Button({
      iconCls: 'finish',
      text: this.finishButtonText,
      disabled: true,
      scope: this,
      handler:function(){
        this.finishHandler(this)
      }
    });
    /**
     * The syntax of the contorller proxy should be:
     * 
        var proxy = new Jaffa.data.DWRProxy({
          query: WorkOrderService.prevalidate,
          reader: {
            read : function(response) {
              return response;
            }
          },
          cacheProcessEventGraphs: function(peGraphs) {
            // this is to cache the process event graphs onto the calling panel so that 
            // the subsequent load calls from the proxy can have the process event graphs preloaded
            if (!this.loadCallbackScope.prevalidateProcessEventGraphs)
              this.loadCallbackScope.prevalidateProcessEventGraphs = [];
            for (var i=0; i<peGraphs.length; i++) {
              this.loadCallbackScope.prevalidateProcessEventGraphs.push(peGraphs[i]);
            }
          },
          loadCallbackScope: panel, // the panel that initiates the proxy load initially
          loadCallback: function(response, options, success) {
            // this is the call back handler when the load is successful
            .....
          }
        });
        .....
        if (panel.prevalidateProcessEventGraphs)
          data.processEventGraphs = panel.prevalidateProcessEventGraphs;
        proxy.load(data, proxy.reader, proxy.loadCallback, this, {proxy: proxy}); 
     * 
     */
    this.continueButton = new Ext.Button({
      iconCls: 'next',
      text: this.continueButtonText,
      disabled: true,
      scope: this,
      handler: function () {
        if (this.validate() !== false) {
          var saveModel = this.buildSaveModel();
          this.close();
          if (this.controllerProxy) {
            this.controllerProxy.cacheProcessEventGraphs(saveModel[0].processEventGraphs);
            this.controllerProxy.load(saveModel[0], this.controllerProxy.reader, this.controllerProxy.loadCallback, this.controllerProxy.loadCallbackScope, {proxy: this.controllerProxy});
          }
        }
      }
    });
    this.cancelButton = new Ext.Button({
      iconCls: 'cancel',
      text: this.cancelButtonText,
      scope: this,
      handler: function () {
        if (this.controller.model) this.controller.deleteGraphInSaveModel(this.controller.model.itemAt(0));
        this.close();
      }
    });
    
    // Add standard configuration settings
    config = Ext.applyIf(config, {
      layout: 'card',
      title: this.warningsPendingEventsTitleText,
      width : 600,
      defaults: {
        autoHeight: true,
        autoScroll: true,
        border: false
      }
    });
    
    // Create the bottom toolbar
    if (config.controllerProxy) {
      config.bbar = ['->', this.continueButton, this.cancelButton];
    } else if (this.response.length == 1) {
      config.bbar = ['->', this.finishButton, this.cancelButton];
    } else {
      config.bbar = ['->', this.previousButton, this.nextButton, this.finishButton, this.cancelButton];
      // check all the responses are the same type
      if (this.allowApplyToAll && this.areAllPendingEventsPanelSame()) {
        this.applyToAllCheckbox = new Ext.form.Checkbox({
          style: 'margin-top: 0px;'
        });
        this.applyToAllLabel = new Ext.form.Label({
          text: ' '+this.applyToAllText
        })
        config.bbar.unshift([this.applyToAllCheckbox, this.applyToAllLabel]);
      }
    }
    
    // Create the window and render pendingEvents from the first response
    Jaffa.maintenance.PendingEventsWindow.superclass.constructor.apply(this, arguments);
    this.renderPanel(0);
    if (this.applyToAllCheckbox) {
      this.applyToAllCheckbox.on('check', function(cb, checked) {
        if (checked) {
          this.nextButton.disable();
          this.finishButton.enable();
        } else {
          this.nextButton.enable();
        }
      }, this);
    }
  },
  
  /**
   * This method returns true if all the cards (pending events panels) are the same types so that 
   * they can use the input from the first card. This method is used to determine whether apply-to-all 
   * button is provided.
   * 
   * This method may be overrided for different logic. 
   */
  areAllPendingEventsPanelSame: function() {
    var sameResps = true, eventList=[];
    for (var i=0; i<this.response.length; i++) {
      if (i==0) {
        for (var j=0; j<this.response[i]._pendingEvents.length; j++) {
          eventList.push(this.response[i]._pendingEvents[j].event);
        }
      } else {
        if (this.response[i]._pendingEvents.length != eventList.length) {
          sameResps = false; 
          break;
        }
        for (var j=0; j<this.response[i]._pendingEvents.length; j++) {
          if (eventList.indexOf(this.response[i]._pendingEvents[j].event) < 0) {
            sameResps = false;
            break;
          }
        }
        if (!sameResps) break;
      }
    }
    return sameResps;
  },
  
  /**
   * Returns the index of the current panel
   */
  findCurrentIndex: function () {
    if (this.items && this.getLayout() && this.getLayout().activeItem) {
      for (var i = 0, len = this.items.getCount(); i < len; i++) {
        if (this.items.get(i) === this.getLayout().activeItem){
          return i;
        }
      }
    }
    return 0;
  },
  
  /**
   * Returns the panel specified the input index. A new panel will be created, if one doesn't exist.
   */
  renderPanel: function (index) {
    // Find the panel at the given index. Create if one doesn't exist
    var p = this.items ? this.items.get(index) : undefined;
    if (!p) {
      p = this.createPanel(index);
      this.add(p);
    }
    
    // Make the panel active
    if (this.getLayout() && this.getLayout().setActiveItem){
      this.getLayout().setActiveItem(index);
    } else {
      this.activeItem = index;
    }
    
    // Reset the associate elements
    if (this.controller.pendingEventConfig && this.controller.pendingEventConfig.titleTpl){
      var titleParams = {_pageNumber: '1/1'};
      if (this.response.length > 1) {
        titleParams._pageNumber = (index + 1) + '/' + this.response.length;
      }
      titleParams = Ext.apply(titleParams, this.response[index].source);
      this.setTitle(new Ext.Template(this.controller.pendingEventConfig.titleTpl).apply(titleParams));
    }
    if (this.applyToAllCheckbox) {
      if (index==0) {
        this.applyToAllCheckbox.show();
        this.applyToAllLabel.show();
      } else {
        this.applyToAllCheckbox.hide();
        this.applyToAllLabel.hide();
    }
    }
    if (this.previousButton){
      this.previousButton.setDisabled(index === 0);
    }
    if (this.nextButton){
      this.nextButton.setDisabled(index === (this.response.length - 1));
    }
    if (this.finishButton){
      this.finishButton.setDisabled(this.items.getCount() !== this.response.length);
    }
    if (this.continueButton){
      this.continueButton.setDisabled(this.items.getCount() !== this.response.length);
    }
    
    // Resize the card element. ExtJS-3.x has introduced the 'layoutOnCardChange' layoutConfig option, which can be used to achieve the same result
    if (p.doLayout){
      p.doLayout();
    }
  },
  
  /**
   * Constructs a new Panel at the input index.
   */
  createPanel: function (index) {
    var panel = new Jaffa.maintenance.PendingEventsPanel({
      controller: this.controller,
      responseEl: this.response[index]
    });
    this.controller.applyPanelFieldsMetaRules(panel);
    return panel;
  },
  
  /**
   * Invokes the validate() method on each panel.
   * Returns false if the validations fail.
   */
  validate: function () {
    var len = this.items.getCount();
    if (this.applyToAllCheckbox && this.applyToAllCheckbox.getValue()) len = 1;
    for (var i = 0; i < len; i++) {
      if (this.items.get(i).validate() === false) {
        if (i != this.findCurrentIndex()){
          this.renderPanel(i);
        }
        return false;
      }
    }
  },
  
  /**
   * Invokes the getProcessEvents() method on each panel.
   * It then adds them to the source property of each response element.
   * Returns an array of elements which can then be passed to the save() method.
   */
  buildSaveModel: function () {
    var saveModel = [];
    for (var i = 0, len = this.response.length; i < len; i++) {
      var processEventGraphs = this.response[i].source.processEventGraphs;
      if (!processEventGraphs)
        this.response[i].source.processEventGraphs = processEventGraphs = [];
      if (i>0 && this.applyToAllCheckbox && this.applyToAllCheckbox.getValue()) {
        this.items.get(0).resetPendingEventParams(this.response[i]);
        this.items.get(0).addProcessEvents(processEventGraphs);
      } else {
        this.items.get(i).addProcessEvents(processEventGraphs);
      }
      saveModel.push(this.response[i].source);
    }
    if (this.applyToAllCheckbox && this.applyToAllCheckbox.getValue()) {
      this.items.get(0).resetPendingEventParams(this.response[0]);
    }
    return this.singleObjectResponse?saveModel[0]:saveModel;
  },

  finishHandler: function (panel) {
    if (this.validate() !== false) {
      this.controller.saveModel = this.buildSaveModel();
      this.close();
      this.controller._invokeSave(this.cbOk, this.cbError);
    }
  }
});

Ext.reg('pendingeventswindow', Jaffa.maintenance.PendingEventsWindow);
