/**
 * @author jsakalos from Ext Support Team
 * @Modified dj Ext Premium Member 
 * @comment may be integrated into ExtJs releases after Sep 2008, possiblely in Ext.apply()
 * Reference to forum post - http://extjs.com/forum/showthread.php?p=207252#post207252
 */
Ext.ux.clone = function(o){
    if (!o || 'object' !== typeof o) {
        return o;
    }
    if ('function' === typeof o.clone) {
        return o.clone();
    }
    var c = 'function' === typeof o.pop ? [] : {};
    var p, v;
    for (p in o) {
        if (o.hasOwnProperty(p)) {
            v = o[p];
            if (v && 'object' === typeof v) {
                c[p] = Ext.ux.clone(v);
            }
            else {
                c[p] = v;
            }
        }
    }
    return c;
}; // eo function clone

Ext.override(RegExp, {
    clone: function(){
        return new RegExp(this);
    }
});
