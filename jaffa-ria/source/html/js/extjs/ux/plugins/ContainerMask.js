/**
 * @class Ext.ux.plugins.ContainerMask
 * @author hendricd@forum.extjs
 * <p>
 * Credits - Based on Original Work found at http://www.extjs.net/forum/showthread.php?p=135612
 * <p>
 * Example 
// Create Panel with a special mask 
var aPanel = new Ext.Panel({
    id: 'aPanel'
    ,plugins: [new Ext.ux.plugins.ContainerMask ({msg:'Data Not Available'})]
    ,...
});    

// Apply the panels mask if it has no data        
var tab = Ext.getCmp('itemAsIs');
if (!someData){
  if (tab.rendered)
    tab.showMask(); 
  else
    tab.on('render', tab.showMask.createDelegate(tab,[null]) ,tab, {delay:10, single:true});
} else {
  tab.hideMask();
}
 * @param {Object} config Options obejct
 */
Ext.ux.plugins.ContainerMask = function(config) {
    var options = config||{};
    /**
     * @cfg {String} msg the default mask message to be displayed when shown
     */
    /**
     * @cfg {String} msgClass (Optional) CSS className applied to the message
     */
    /**
     * @cfg {String} maskClass (Optional) CSS className applied to the mask
     */
    /**
     * @cfg {boolean} masked (Optional) if true, the mask is shown during initial Container rendering
     */
    /**
     * @cfg {Ext.Element} el (Optional) element to use as the masking element. Can be a property name (ie: 'bwrap'), or even a specific element elsewhere on the page. Defaults to main container element.
     */
    
    return {
        init: function(c) {
            Ext.applyIf(c,{
                showMask : function(msg, msgClass, maskClass){
                    var el;
                    if(this.rendered && (el = this[options.el] || Ext.get(options.el) || this.getEl?this.getEl():null)){
                      el.mask.call(el,msg || options.msg, msgClass || options.msgClass, maskClass || options.maskClass);
                    }
                },
                hideMask : function(){
                    var el;
                    if(this.rendered && (el = this[options.el] || Ext.get(options.el) ||  this.getEl?this.getEl():null)){ 
                      el.unmask.call(el);
                    }
                }
            });
            if(options.masked){ 
                c.on('render', c.showMask.createDelegate(c,[null]) ,c, {delay:10, single:true}) ; 
            }
        }
    };
};