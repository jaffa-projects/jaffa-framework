/**
 * This contains patches to be applied to the ExtJS release
 * These are only "emergency" fixes in the event it is not
 * possible to move to the next stable release because
 *   a) the rest of the JaffaRIA project has not yet been tested on the later release
 *   b) the fix has been done in ExtJS SVN, but is not in an official ExtJS release
 *
 * This file is included by default as part of loadExtJS.jsp
 */


/*
 * These are specific modifications that have since been added to v2.2
 */
if(Ext.version=='2.1') {

  // FIX Mask Resizing issue with IE
  // Panel.js, revision:2373 - bmoeskau  6/30/2008 1:20:03 PM
  //                         - Enable proper Panel disabling in IE (fixes for bad opacity and resize monitoring)
  Ext.override(Ext.Panel, {
    onResize : function(w, h) {
        if(w !== undefined || h !== undefined){
            if(!this.collapsed){
                if(typeof w == 'number'){
                    this.body.setWidth(
                            this.adjustBodyWidth(w - this.getFrameWidth()));
                }else if(w == 'auto'){
                    this.body.setWidth(w);
                }

                if(typeof h == 'number'){
                    this.body.setHeight(
                            this.adjustBodyHeight(h - this.getFrameHeight()));
                }else if(h == 'auto'){
                    this.body.setHeight(h);
                }
                
                if(this.disabled && this.el._mask){
                    this.el._mask.setSize(this.el.dom.clientWidth, this.el.getHeight());
                }
            }else{
                this.queuedBodySize = {width: w, height: h};
                if(!this.queuedExpand && this.allowQueuedExpand !== false){
                    this.queuedExpand = true;
                    this.on('expand', function(){
                        delete this.queuedExpand;
                        this.onResize(this.queuedBodySize.width, this.queuedBodySize.height);
                        this.doLayout();
                    }, this, {single:true});
                }
            }
            this.fireEvent('bodyresize', this, w, h);
        }
        this.syncShadow();
    }
  });
  
  // The Ext.form.TimeField widget in ExtJS-2.1 goes into an infinite loop on the day DST takes effect (i.e. clock moves forward).
  // See http://extjs.com/forum/showthread.php?t=28875 for more details.
  // Added the following patch with code from ExtJS-2.2.
  Ext.override(Ext.form.TimeField, {
    // private - This is the date to use when generating time values in the absence of either minValue
    // or maxValue.  Using the current date causes DST issues on DST boundary dates, so this is an 
    // arbitrary "safe" date that can be any date aside from DST boundary dates.
    initDate: '1/1/2008',

    // private
    initComponent : function(){
        Ext.form.TimeField.superclass.initComponent.call(this);

        if(typeof this.minValue == "string"){
            this.minValue = this.parseDate(this.minValue);
        }
        if(typeof this.maxValue == "string"){
            this.maxValue = this.parseDate(this.maxValue);
        }

        if(!this.store){
            var min = this.parseDate(this.minValue);
            if(!min){
                min = new Date(this.initDate).clearTime();
            }
            var max = this.parseDate(this.maxValue);
            if(!max){
                max = new Date(this.initDate).clearTime().add('mi', (24 * 60) - 1);
            }
            var times = [];
            while(min <= max){
                times.push([min.dateFormat(this.format)]);
                min = min.add('mi', this.increment);
            }
            this.store = new Ext.data.SimpleStore({
                fields: ['text'],
                data : times
            });
            this.displayField = 'text';
        }
    }
  });
}



/*
 * These are specific modifications that have since been added to v2.2
 */
else if(Ext.version=='2.2') {

  /** Radio Button Fixes As Per
   *  http://extjs.com/forum/showthread.php?p=211564#post211564
   */
  Ext.override(Ext.form.Checkbox, {
    onRender: function(ct, position){
      Ext.form.Checkbox.superclass.onRender.call(this, ct, position);
      if(this.inputValue !== undefined){
        this.el.dom.value = this.inputValue;
      }
      //this.el.addClass('x-hidden');
      this.innerWrap = this.el.wrap({
        //tabIndex: this.tabIndex,
        cls: this.baseCls+'-wrap-inner'
      });
      this.wrap = this.innerWrap.wrap({cls: this.baseCls+'-wrap'});
      this.imageEl = this.innerWrap.createChild({
        tag: 'img',
        src: Ext.BLANK_IMAGE_URL,
        cls: this.baseCls
      });
      if(this.boxLabel){
        this.labelEl = this.innerWrap.createChild({
          tag: 'label',
          htmlFor: this.el.id,
          cls: 'x-form-cb-label',
          html: this.boxLabel
        });
      }
      //this.imageEl = this.innerWrap.createChild({
        //tag: 'img',
        //src: Ext.BLANK_IMAGE_URL,
        //cls: this.baseCls
      //}, this.el);
      if(this.checked){
        this.setValue(true);
      }else{
        this.checked = this.el.dom.checked;
      }
      this.originalValue = this.checked;
      console.debug("Rendered checkbox:",this.name, this);
    },
    afterRender: function(){
      Ext.form.Checkbox.superclass.afterRender.call(this);
      //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
      this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
      console.debug("Apply CSS to Image:",this.imageEl);
    },
    initCheckEvents: function(){
      //this.innerWrap.removeAllListeners();
      this.innerWrap.addClassOnOver(this.overCls);
      this.innerWrap.addClassOnClick(this.mouseDownCls);
      this.innerWrap.on('click', this.onClick, this);
      //this.innerWrap.on('keyup', this.onKeyUp, this);
    },
    onFocus: function(e) {
      Ext.form.Checkbox.superclass.onFocus.call(this, e);
      //this.el.addClass(this.focusCls);
      this.innerWrap.addClass(this.focusCls);
    },
    onBlur: function(e) {
      Ext.form.Checkbox.superclass.onBlur.call(this, e);
      //this.el.removeClass(this.focusCls);
      this.innerWrap.removeClass(this.focusCls);
    },
    onClick: function(e){
      if (e.getTarget().htmlFor != this.el.dom.id) {
        if (e.getTarget() !== this.el.dom) {
          this.el.focus();
        }
        if (!this.disabled && !this.readOnly) {
          this.toggleValue();
        }
      }
      //e.stopEvent();
    },
    onEnable: Ext.form.Checkbox.superclass.onEnable,
    onDisable: Ext.form.Checkbox.superclass.onDisable,
    onKeyUp: undefined,
    setValue: function(v) {
      var checked = this.checked;
      this.checked = (v === true || v === 'true' || v == '1' || String(v).toLowerCase() == 'on');
      if(this.rendered){
        this.el.dom.checked = this.checked;
        this.el.dom.defaultChecked = this.checked;
        //this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
        this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
      }
      if(checked != this.checked){
        this.fireEvent("check", this, this.checked);
        if(this.handler){
          this.handler.call(this.scope || this, this, this.checked);
        }
      }
    },
    getResizeEl: function() {
      //if(!this.resizeEl){
        //this.resizeEl = Ext.isSafari ? this.wrap : (this.wrap.up('.x-form-element', 5) || this.wrap);
      //}
      //return this.resizeEl;
      return this.wrap;
    },
    
    //-- ADDIONAL FIX: Added this as default version returns false if not rendered, not the real value --
    getValue : function(){
      if(this.rendered){
        return this.el.dom.checked;
      }
      return this.checked;
    }
    
  });
  Ext.override(Ext.form.Radio, {
    checkedCls: 'x-form-radio-checked'
  });

}



/*
 * These are specific modifications that have since been added to v3.2.1
 */
else if(Ext.version=='3.2.1') {

  // In IE7, the 'this.iframe.contentWindow' statement, which is called by getWin(), which in
  // turn is called by getDoc() during a destroy, throws an error.
  // The workaround mentioned in http://www.extjs.com/forum/showthread.php?91768-ExtJS-3.1-HtmlEditor-IE7-issue,
  // does not work at all times.
  // The following override to getDoc() ignores any errors thrown by the original code.
  if (!Ext.form.HtmlEditor.prototype._jaffa_orig_getDoc)
    Ext.form.HtmlEditor.prototype._jaffa_orig_getDoc = Ext.form.HtmlEditor.prototype.getDoc;
  Ext.override(Ext.form.HtmlEditor, {
    getDoc: function () {
      try {
        return this._jaffa_orig_getDoc();
      } catch (e) {
      }
    }
  });
}
/**
 * fix the array out of bound error in Ext.EventManager.removeFromSpecialCache()
 */
else if(Ext.version=='3.4.1.1' || Ext.version=='3.4.3') {
  (function(){
    var f = Ext.EventManager.removeFromSpecialCache;
    Ext.EventManager.removeFromSpecialCache = function() {
      try {
            // f.apply is needed if interface and implementation 
            //of removeFromSpecialCache will be changed
            
            f.apply(Ext.EventManager, arguments);
        } catch (e) {
            // nothing to catch because specialCache is behaves like a set.
        }
    };    
  })();
}

/*
 * The email vtype has been changed in 3.3 to allow more characters (eg. apostrophe is a valid character)
 * This override provides that same validation for earlier versions.
 * This could be removed for 3.3 and later versions pending testing of the improvement
 */

Ext.apply(Ext.form.VTypes, {
    email:  function(v) {
        return /^[a-z0-9!#\$%&'\*\+/=\?\^_`{|}~\-]+(?:\.[a-z0-9!#\$%&'\*\+/=\?\^_`{|}~\-]+)*@(?:[a-z0-9](?:[a-z0-9\-]*[a-z0-9])?\.){1,5}(?:[a-z]){2,6}$/i.test(v);
    },
    emailMask: /[a-z0-9!#\$%&'\*\+/=\?\^_`{|}~\-\.@]/i
});
