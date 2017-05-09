/**
 * @class Ext.layout.FullAccordion New "full-accordion" layout extends from the
 *        base Accordion, but this one does not allow all sections to be close,
 *        one must be open all the time.
 */
Ext.layout.FullAccordion = Ext.extend(Ext.layout.Accordion, {
    /**
     * @cfg {Boolean} hideCollapseTool True to hide the contained panels'
     *      collapse/expand toggle buttons, false to display them (defaults to
     *      false). When set to true, {@link #titleCollapse} should be true
     *      also.
     */
    hideCollapseTool : true,

    renderItem : function(c) {
        // console.debug("Render FullAccordion");
        Ext.layout.FullAccordion.superclass.renderItem.apply(this, arguments);
        // c.on('beforeexpand', this.beforeExpand, this);
        c.on('beforecollapse', this.beforeCollapse, this);
        this.allowCollapse = 2;
    },

    // private
    beforeCollapse : function(p, anim) {
        // console.debug("Collapse id=",p.id,"=>",this.allowCollapse);
        if (this.allowCollapse == 1) { // allowed pending another expand
            this.allowCollapse = 2; // don't allow again
            return true;
        }
        return false; // ==2
    },

    beforeExpand : function(p, anim) {
        var ai = this.activeItem;
        // console.debug("Collapsing id=",ai.id," to Expand id=",p.id);
        if (ai) {
            if (this.sequence) {
                delete this.activeItem;
                ai.collapse({callback:function(){
                        p.expand(anim || true);
                }, scope: this});
                return false;
            } else {
                this.allowCollapse = 1;
                ai.collapse(this.animate);
            }
        }
        this.activeItem = p;
        if (this.activeOnTop) {
            p.el.dom.parentNode.insertBefore(p.el.dom, p.el.dom.parentNode.firstChild);
        }
        this.layout();
    }
});
Ext.Container.LAYOUTS['full-accordion'] = Ext.layout.FullAccordion;
