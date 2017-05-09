/**
 * @class Jaffa.maintenance.PendingEventsPanel
 * @extends Ext.Panel
 *
 * This Panel will be rendered as a Card in the PendingEventsWindow.
 * It'll contain sub-panels, one for each PendingEvent for a response element.
 */
Jaffa.maintenance.PendingEventsPanel = Ext.extend(Ext.Panel, {
  /**
   * Constructs an instance.
   * @param {Object} config At the bare minimum, the config should contain at least the controller and responseEl elements.
   *        The controller should contain the pendingEventConfig for configuring this panel.
   */
  constructor: function(config) {
    this.controller = config.controller;
    this.responseEl = config.responseEl;
    
    // Create a panel for each PendingEvent and add to the items array of the config
    if (!config.items) {
      config.items = [];
    }
    outer_loop: for (var i = 0, iLen = this.responseEl._pendingEvents.length; i < iLen; i++) {
      var pendingEvent = this.responseEl._pendingEvents[i];
      
      //Before creating a unique panel for a PendingEvent, check if any of the existing panels are willing to process the event
      for (var j = 0, jLen = config.items.length; j < jLen; j++) {
        var panel = config.items[j];
        if (panel.processAdditionalPendingEvent && typeof panel.processAdditionalPendingEvent === 'function' && panel.processAdditionalPendingEvent(pendingEvent) === true) {
          continue outer_loop;
        }
      }

      // An existing panel has not processed the event. So create a new panel for the event.
      var p = this.createPanel(this.controller, pendingEvent,config);
      if(p) config.items.push(p);
    }
    
    Jaffa.maintenance.PendingEventsPanel.superclass.constructor.apply(this, arguments);
  },

  /**
   * This method is used to replace the response element of this panel and the pending Event of 
   * each pending event panels inside of this panel. 
   * 
   * This method is used when all the pending events panels use the input data from the first pending
   * events panel.
   * 
   * @param {Object} responseEl
   */
  resetPendingEventParams: function(responseEl) {
    this.responseEl = responseEl;
    this.items.each(function(pep) {
      pep.resetPendingEventParams(responseEl);
    }, this);
  },
  
  /**
   * Constructs a new Panel for the input pendingEvent.
   * The pendingEventConfig will be used to determine the Panel class to instantiate for a given event.
   * If the Panel class cannnot be deteremined, then the default PendingEventPanel/WarningPanel will be instantiated.
   */
  createPanel: function(controller, pendingEvent,panelConfig) {
    var p;
    if (controller && controller.pendingEventConfig && controller.pendingEventConfig.events && controller.pendingEventConfig.events.length > 0) {
      for (var i = 0, len = controller.pendingEventConfig.events.length; i < len; i++) {
        var config = controller.pendingEventConfig.events[i];
        if ((config.warn && pendingEvent.warn) || (!config.warn && !pendingEvent.warn && config.event == pendingEvent.event)) {
          p = config.handler;
          break;
        }
      }
    }
    return p ? new p({
      controller: controller,
      responseEl: this.responseEl,
      pendingEvent: pendingEvent,
      parentConfig : panelConfig
    }) : (pendingEvent.warn ? new Jaffa.maintenance.WarningPanel({
      controller: controller,
      responseEl: this.responseEl,
      pendingEvent: pendingEvent
    }) : new Jaffa.maintenance.PendingEventPanel({
      controller: controller,
      responseEl: this.responseEl,
      pendingEvent: pendingEvent,
      html: pendingEvent.text
    }));
  },
  
  /**
   * Invokes the validate() method on each sub-panel.
   * Returns false if the validations fail.
   */
  validate: function() {
    for (var i = 0, len = this.items.getCount(); i < len; i++) {
      if (this.items.get(i).validate() === false) {
        return false;
      }
    }
  },
  
  /**
   * Invokes the getProcessEvents() method on each sub-panel.
   * The output from each sub-panel is added to the input array of processEventGraphs.
   */
  addProcessEvents: function(input) {
    for (var i = 0, iLen = this.items.getCount(); i < iLen; i++) {
      var processEvents = this.items.get(i).getProcessEvents();
      if (processEvents) {
        // convert to an array for convenience
        if (!Ext.isArray(processEvents))
          processEvents = [processEvents];
        
        // Remove earlier matching elements from the input, if panel is flagged for singletonEvents
        if (input.length > 0 && this.items.get(i).singletonEvents === true) {
          for (var j = 0, jLen = processEvents.length; j < jLen; j++) {
            var eventName = processEvents[j].eventName;
            for (var k = input.length - 1; k >= 0; k--) {
              if (input[k].eventName == eventName)
                input.splice(k, 1);
            }
          }
        }
        
        // push the processEvents into the input
        for (var j = 0, jLen = processEvents.length; j < jLen; j++)
          input.push(processEvents[j]);
      }
    }
  }
});

Ext.reg('pendingeventspanel', Jaffa.maintenance.PendingEventsPanel);
