
/**
 * @class Jaffa.maintenance.HeaderPanel
 * @extends Jaffa.finder.FinderHeader
 *
 * This extends the finder by providing a 2 part updatable title
 * It has different implementation for the refresh too. 
 */
Jaffa.maintenance.HeaderPanel = Ext.extend(Jaffa.finder.FinderHeader, {

  dataNotFoundTitle: 'Record not found.',
  dataNotFoundMessage: 'The record being refreshed is no longer available.',

  /**
   * @method onRefresh
   * The default behavior is to call the onSaveSuccess function of each
   *         panel that is registered to the controller
   *
   * This has a listener for 'beforerefresh' to intercept the default action. Can be used to 
   * check for dirty panels and confirm if the user want to do this action. If this event
   * returns false the refresh will not be performed
   *    
   * Also fires 'refresh' event when a refresh has been done, note as the load is done by AJAX,
   * this event may be fired prior to the load in the controller completing
   */
  onRefresh: function(btn,evt) {
    if (this.fireEvent('beforerefresh', this) !== false) {
      var cmp = Ext.getCmp('viewport');
      if (cmp && cmp.controller) {
        var ctrl = cmp.controller;
        if (ctrl.registeredPanels) {
          ctrl.registeredPanels.each(function(panel) {
            if (panel.onSaveSuccess) {
              panel.onSaveSuccess();
            }
          });
        }
        ctrl.load(function(ctrl){
          if (!ctrl.model || ctrl.model.length==0){
            Ext.MessageBox.show({
              title : this.dataNotFoundTitle,
              msg : this.dataNotFoundMessage,
              width : 400,
              buttons : Ext.MessageBox.OK,
              icon : Ext.MessageBox.ERROR,
              scope: this,
              fn : function(btn) {
                this.closeWindowOk();
              }
            });
            return false;
          }
        }.createDelegate(this));
      }
      this.fireEvent('refresh',this,btn,evt);
    }
  },
  
  /**
   * Callback when the controller associated to the header loads the data
   * Typically this will update the header panel with details relating to
   * the record that was read.
   */
  load: function() {
    if (this.controller.model) {
      var graph = this.controller.model.itemAt(0);
      if (graph) {
        this.controller.setPanelFields(this, graph);
        if (this.rendered) {
          // Set fields in title bar
          this.updateHeader();
        }
      }
    }
  }
});

Ext.reg('maintenanceheader', Jaffa.maintenance.HeaderPanel);
