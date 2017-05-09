/**
 * @class Ext.ModalWindow
 * @extends Ext.Window
 * Modal Window
 * @constructor
 * Creates a new modal window
 * @param {Object} config Configuration options
 */
Ext.ModalWindow = Ext.extend(Ext.Window, {
    modal: true,
    closable: true,
    shadow: false,
    bodyBorder: false,
    bodyStyle: 'background-color:#DFE8F6;padding:5px',
    minimizable: false,
    draggable: true,
    resizable: true,
    constrainHeader: true,
    width: 500,
    height: 'auto',
    layout: 'formdescription',
    //  plain: true, 
    listeners: {
        beforedestroy: function(w){
            if (w.mask) {
                Ext.destroy(w.mask);
            }
            if (w.proxy) {
                Ext.destroy(w.proxy);
            }
            if (w.plain) {
                Ext.destroy(w.plain);
            }
        }
    }
});