/**
 * @class Ext.ux.plugins.ApplyFastAccessMetaRules
 * @author BobbyC
 * Example
 * Ext.extend(FastAccessPanel, Ext.Panel, {
 *   plugins: [new Ext.ux.plugins.ApplyFastAccessMetaRules({})],
 *   ...
 * });
 *
 * This is used to apply meta data to a fast access toolbar.
 * The main benefit is to hide buttons on the bar using the default module.submodule.component.hidePanelList business rule.
 */
Ext.ux.plugins.ApplyFastAccessMetaRules = function() {
    return {
        init: function(fa) {
          if (fa.toolbars && fa.toolbars.length > 0)
            Jaffa.component.PanelController.prototype.applyPanelFieldsMetaRules(fa.toolbars[0]);
        }
    };
};