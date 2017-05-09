/**
 * @class Jaffa.maintenance.WarningPanel
 * @extends Jaffa.maintenance.PendingEventPanel
 *
 * This Panel can be used to render warnings.
 */
Jaffa.maintenance.WarningPanel = Ext.extend(Jaffa.maintenance.PendingEventPanel, {
	
	warningTitleText: 'Warning',
	
  /**
   * Constructs an instance.
   * @param {Object} config At the bare minimum, the config should contain at least the pendingEvent element.
   */
  constructor: function(config) {
    config = Ext.applyIf(config, {
      title: this.warningTitleText
    });
    config.html = config.pendingEvent.text;
    Jaffa.maintenance.WarningPanel.superclass.constructor.apply(this, arguments);
  },
  
  /**
   * Returns a process event object for the warning
   */
  getProcessEvents: function() {
    return {
      eventName: this.pendingEvent.event,
      params: this.pendingEvent.params,
      cancelEvent: true
    };
  }
});

Ext.reg('warningpanel', Jaffa.maintenance.WarningPanel);
