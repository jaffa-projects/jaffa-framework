
/**
 * @class Jaffa.maintenance.PendingEventPanel
 * @extends Ext.Panel
 *
 * This Panel can be used to handle a PendingEvent.
 */
Jaffa.maintenance.PendingEventPanel = Ext.extend(Ext.Panel, {
  /**
   * Constructs an instance.
   * @param {Object} config At the bare minimum, the config should contain at least the pendingEvent element.
   */
  constructor: function(config) {
    this.controller = config.controller;
    this.pendingEvent = config.pendingEvent;
    
    config = Ext.applyIf(config, {
      title: this.pendingEvent ? this.pendingEvent.event : '',
      collapsible: true,
      cls: 'x-panel-mc',
      autoScroll : true
    });
    
    Jaffa.maintenance.PendingEventPanel.superclass.constructor.apply(this, arguments);
  },
  
  /**
   * Implement if any validation is to be performed.
   * Returns false if the validations fail.
   */
  validate: function() {
    // TODO can we escape the return of this for XSS or does this include html styling?
    var errors = Jaffa.component.PanelController.prototype.validatePanelFields(this);
    if (errors) {
      Ext.MessageBox.show({
        msg: errors,
        buttons: Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });
      return false;
    }
  },
  
  /**
   * A flag to ensure that the response will contain only one element for each event.
   * By default, this feature if turned off. An implementation should set the flag to true, if this feature is desired.
   */
  singletonEvents: undefined,
  
  /**
   * Implement to add processEvents to the response. By default, this method returns nothing.
   */
  getProcessEvents: Ext.emptyFn,
  
  /**
   * It is possible that a PendingEventPanel may service more than one PendingEvents.
   * An implementation may choose to incorporate additional events.
   * If it does, then this method should return a 'true', which will ensure that a separate panel is not created
   * for those events.
   * The default implementation does nothing.
   */
  processAdditionalPendingEvent: function(pendingEvent) {
  },
  
  /**
   * This method is used to replace the response element of this panel and its pending Event 
   * 
   * This method is used when all the pending events panels use the input data from the first pending
   * events panel.
   * 
   * This method may be overriden for different way of assigning response params to the panel.
   * 
   * @param {Object} responseEl
   */
  resetPendingEventParams: function(responseEl) {
    this.responseEl = responseEl;
    for (var i=0; i<responseEl._pendingEvents.length; i++) {
      if (this.pendingEvent.event == responseEl._pendingEvents[i].event) {
        this.pendingEvent = responseEl._pendingEvents[i];
        break;
      }
    }
  },
  
  /**
   * A convenience method that checks the params array for elements having the same name as the input.
   * If found, then the input value will be assigned to that element.
   * If more than elements are found, then all except one will be deleted from the array.
   * If not found, a new element will be added to the array.
   */
  put: function(params, name, value) {
    var exists = false;
    for (var i = 0; i < params.length; i++) {
      var param = params[i];
      if (param.name == name) {
        if (!exists) {
          param.value = value;
          exists = true;
        }
        else {
          params.splice(i, 1);
          i--;
        }
      }
    }
    if (!exists) {
      params.push({
        name: name,
        value: value
      });
    }
  },
  
  /**
   * A convenience method that checks the params array for elements having the same name as the input.
   * If found, then the value for that param will be returned.
   * If more than elements are found, then the value for the last matching element will be returned.
   */
  get: function(params, name) {
    for (var i = params.length - 1; i >= 0; i--) {
      var param = params[i];
      if (param.name == name) {
        return param.value;
      }
    }
  }
});

Ext.reg('pendingeventpanel', Jaffa.maintenance.PendingEventPanel);
