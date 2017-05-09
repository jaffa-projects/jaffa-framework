/**
 Notes from SeanZ: 
   1. Added in 'n' for 'now' and 't' for 'today' handling.
   2. Added test examples
   3. Modified onSpecialKey() to trigger updateValue() only on date field to resolve following issues:
       a) typing n/t to date field and tab out and the time field is not populated
       b) select/type-in a time value and hit return cause the time field to be cleared.
   4. added test case for datetime in grid and date portion is hidden.
   5. do not stop specialKey event if the date or time portion is hidden. onSpecialKey()
   6. test if (this.df/tf.el) exists before setting their width
 */

/**
 * Ext.ux.form.DateTime Extension Class for Ext 2.x Library
 *
 * @author    Ing. Jozef Sakalos
 * @copyright (c) 2008, Ing. Jozef Sakalos
 * @version $Id: DateTime.js,v 1.8 2009-03-04 19:43:21 seanz Exp $
 *
 * @license Ext.ux.grid DragSelector is licensed under the terms of
 * the Open Source LGPL 3.0 license.  Commercial use is permitted to the extent
 * that the code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * License details: http://www.gnu.org/licenses/lgpl.html
 */

Ext.ns('Ext.ux.form');

/**
 * @class Ext.ux.form.DateTime
 * @extends Ext.form.Field
 */
Ext.ux.form.DateTime = Ext.extend(Ext.form.Field, {
    /**
     * @cfg {String/Object} defaultAutoCreate DomHelper element spec
     * Let superclass to create hidden field instead of textbox. Hidden will be submittend to server
     */
     defaultAutoCreate:{tag:'input', type:'hidden'}
    /**
     * @cfg {Number} timeWidth Width of time field in pixels (defaults to 100)
     */
    ,timeWidth:100
    /**
     * @cfg {String} dtSeparator Date - Time separator. Used to split date and time (defaults to ' ' (space))
     */
    ,dtSeparator:' '
    /**
     * @cfg {String} hiddenFormat Format of datetime used to store value in hidden field
     * and submitted to server (defaults to 'Y-m-d H:i:s' that is mysql format)
     */
    ,hiddenFormat:'Y-m-d H:i:s'
    /**
     * @cfg {Boolean} otherToNow Set other field to now() if not explicly filled in (defaults to true)
     */
    ,otherToNow:true
    /**
     * @cfg {Boolean} emptyToNow Set field value to now if on attempt to set empty value.
     * If it is true then setValue() sets value of field to current date and time (defaults to false)
    /**
     * @cfg {String} timePosition Where the time field should be rendered. 'right' is suitable for forms
     * and 'bellow' is suitable if the field is used as the grid editor (defaults to 'right')
     */
    ,timePosition:'right' // valid values:'bellow', 'right'
    /**
     * @cfg {String} dateFormat Format of DateField. Can be localized.
     *      (defaults to same default as Ext.form.DateField)
     */
    /**
     * @cfg {String} timeFormat Format of TimeField. Can be localized. (defaults to 'g:i A')
     */
    ,timeFormat:'g:i:s A'
    /**
     * @cfg {Object} dateConfig Config for DateField constructor.
     */
    /**
     * @cfg {Object} timeConfig Config for TimeField constructor.
     */

    // {{{
    /**
     * private
     * creates DateField and TimeField and installs the necessary event handlers
     */
    ,initComponent:function() {
        // call parent initComponent
        Ext.ux.form.DateTime.superclass.initComponent.call(this);

        // create DateField
        var dateConfig = Ext.apply({}, {
             id:this.id + '-date'
            ,format:this.dateFormat || Ext.form.DateField.prototype.format
            ,width:this.timeWidth
            ,selectOnFocus:this.selectOnFocus
            ,ntFilter:''
            ,listeners:{
                  blur:{scope:this, fn:this.onBlur}
                 ,focus:{scope:this, fn:this.onFocus}
            }
        }, this.dateConfig);
        this.df = new Ext.form.DateField(dateConfig);
        delete(this.dateFormat);

        // create TimeField
        var timeConfig = Ext.apply({}, {
             id:this.id + '-time'
            ,format:this.timeFormat || Ext.form.TimeField.prototype.format
            ,width:this.timeWidth
            ,selectOnFocus:this.selectOnFocus,
            getErrors: function(value) {
                var errors = Ext.ux.form.DateTime.superclass.getErrors.apply(this, arguments);
                value = this.formatDate(value || this.processValue(this.getRawValue()));
                if (value && !this.parseDate(value)) {
                    errors.push(String.format(this.invalidText, value, this.format));
                    return errors;
                }
                return errors;
            }
            ,listeners:{
                  blur:{scope:this, fn:this.onBlur}
                 ,focus:{scope:this, fn:this.onFocus}
            }
        }, this.timeConfig);
        this.tf = new Ext.form.TimeField(timeConfig);
        this.tf.altFormats += '|G:i:s';
        delete(this.timeFormat);

        // relay events
        this.relayEvents(this.df, ['focus', 'specialkey', 'invalid', 'valid']);
        this.relayEvents(this.tf, ['focus', 'specialkey', 'invalid', 'valid']);

        this.on('specialkey', this.onSpecialKey, this);
    } // eo function initComponent
    // }}}
    // {{{
    /**
     * private
     * Renders underlying DateField and TimeField and provides a workaround for side error icon bug
     */
    ,onRender:function(ct, position) {
        // don't run more than once
        if(this.isRendered) {
            return;
        }

        // render underlying hidden field
        Ext.ux.form.DateTime.superclass.onRender.call(this, ct, position);

        // render DateField and TimeField
        // create bounding table
        var t;
        if(this.column && position && position.className === 'x-clear'){ // Render in a inline row editor
          t = Ext.DomHelper.insertBefore(ct.dom.childNodes[this.column.index], {
              tag: 'table',
              style: 'border-collapse:collapse',
              children: [
                {
                  tag: 'tr',
                  children: [
                    {
                      tag: 'td',
                      style: 'padding-right:4px', 
                      cls: 'ux-datetime-date'
                    },
                    {
                      tag:'td', 
                      cls:'ux-datetime-time'
                    }
                ]
              }
            ]
          }, true);
        } else {
          if('below' === this.timePosition || 'bellow' === this.timePosition) {
              t = Ext.DomHelper.append(ct, {
                tag:'table',
                style:'border-collapse:collapse',
                children:[
                  {
                    tag:'tr',
                    children:[
                      {
                        tag:'td', 
                        style:'padding-bottom:1px', 
                        cls:'ux-datetime-date'
                      }
                    ]
                  },
                  {
                    tag:'tr',
                    children:[
                      {
                        tag:'td', 
                        cls:'ux-datetime-time'
                      }
                    ]
                  }
              ]}, true);
          } else {
              t = Ext.DomHelper.append(ct, {
                tag:'table',
                style:'border-collapse:collapse',
                children:[
                  {
                    tag:'tr',
                    children:[
                      {
                        tag:'td',
                        style:'padding-right:4px', 
                        cls:'ux-datetime-date'
                      },
                      {
                        tag:'td', 
                        cls:'ux-datetime-time'
                      }
                  ]
                }
              ]}, true);
          }
        }

        this.tableEl = t;
        this.wrap = t.wrap({cls:'x-form-field-wrap'});
        this.wrap.on("mousedown", this.onMouseDown, this, {delay:10});

        // render DateField & TimeField
        this.df.render(t.child('td.ux-datetime-date'));
        this.tf.render(t.child('td.ux-datetime-time'));

        // workaround for IE trigger misalignment bug
        if(Ext.isIE && Ext.isStrict) {
            t.select('input').applyStyles({top:0});
        }

        this.df.el.swallowEvent(['keydown', 'keypress']);
        this.tf.el.swallowEvent(['keydown', 'keypress']);

        // create icon for side invalid errorIcon
        if('side' === this.msgTarget) {
            var elp = this.el.findParent('.x-form-element', 10, true);
            this.errorIcon = elp.createChild({cls:'x-form-invalid-icon'});

            this.df.errorIcon = this.errorIcon;
            this.tf.errorIcon = this.errorIcon;
        }

        // we're rendered flag
        this.isRendered = true;

    } // eo function onRender
    // }}}
    // {{{
    /**
     * private
     */
    ,adjustSize:Ext.BoxComponent.prototype.adjustSize
    // }}}
    // {{{
    /**
     * private
     */
    ,alignErrorIcon:function() {
        this.errorIcon.alignTo(this.tableEl, 'tl-tr', [2, 0]);
    }
    // }}}
    // {{{
    /**
     * private initializes internal dateValue
     */
    ,initDateValue:function() {
        this.dateValue = this.otherToNow ? new Date() : new Date(1970, 0, 1, 0, 0, 0);
    }
    // }}}
    // {{{
    /**
     * Disable this component.
     * @return {Ext.Component} this
     */
    ,disable:function() {
        if(this.isRendered) {
            this.df.disabled = this.disabled;
            this.df.onDisable();
            this.tf.onDisable();
        }
        this.disabled = true;
        this.df.disabled = true;
        this.tf.disabled = true;
        this.fireEvent("disable", this);
        return this;
    } // eo function disable
    // }}}
    // {{{
    /**
     * Enable this component.
     * @return {Ext.Component} this
     */
    ,enable:function() {
        if(this.rendered){
            this.df.onEnable();
            this.tf.onEnable();
        }
        this.disabled = false;
        this.df.disabled = false;
        this.tf.disabled = false;
        this.fireEvent("enable", this);
        return this;
    } // eo function enable
    // }}}
    // {{{
    /**
     * private Focus date filed
     */
    ,focus:function() {
      if (!this.df.hidden)
        this.df.focus();
      else if (!this.tf.hidden)
        this.tf.focus();
    } // eo function focus
    // }}}
    // {{{
    /**
     * private
     */
    ,getPositionEl:function() {
        return this.wrap;
    }
    // }}}
    // {{{
    /**
     * private
     */
    ,getResizeEl:function() {
        return this.wrap;
    }
    // }}}
    // {{{
    /**
     * @return {Date/String} Returns value of this field
     */
    ,getValue:function() {
        // create new instance of date
        return this.dateValue ? new Date(this.dateValue) : '';
    } // eo function getValue
    // }}}
    // {{{
    /**
     * Hide this component.
     * @return {Ext.Component} this
     */
    ,hide:function() {
        if(this.isRendered) {
            this.df.hide();
            this.tf.hide();
        }
        this.hidden = true;
        this.df.hidden = true;
        this.tf.hidden = true;
        this.fireEvent("hide", this);
        return this;
    } // eo function hide
    // }}}
    // {{{
    /**
     * Show this component.
     * @return {Ext.Component} this
     */
    ,show:function() {
        if(this.rendered){
            this.df.show();
            this.tf.show();
        }
        this.hidden = false;
        this.df.hidden = false;
        this.tf.hidden = false;
        this.fireEvent("show", this);
        return this;
    } // eo function show
    // }}}
    // {{{
    /**
     * @return {Boolean} true = valid, false = invalid
     * private Calls isValid methods of underlying DateField and TimeField and returns the result
     */
    ,isValid:function() {
        return this.df.isValid() && this.tf.isValid();
    } // eo function isValid
    // }}}
    // {{{
    /** 
     * private Handles blur event
     */
    ,onBlur:function(f) {
        // called by both DateField and TimeField blur events

        // revert focus to previous field if clicked in between
        if(this.wrapClick) {
            f.focus();
            this.wrapClick = false;
        }

        // update underlying value
        if(f === this.df) {
            this.updateDate();
        }
        else {
            this.updateTime();
        }
        this.updateHidden();

        // fire events later
        (function() {
            if(!this.df.hasFocus && !this.tf.hasFocus) {
                var v = this.getValue();
                if(String(v) !== String(this.startValue)) {
                    this.fireEvent("change", this, v, this.startValue);
                }
                this.hasFocus = false;
                this.fireEvent('blur', this);
            }
        }).defer(100, this);

    } // eo function onBlur
    // }}}
    // {{{
    /**
     * private Handles focus event
     */
    ,onFocus:function() {
        if(!this.hasFocus){
            this.hasFocus = true;
            this.startValue = this.getValue();
            this.fireEvent("focus", this);
        }
    }
    // }}}
    // {{{
    /**
     * private Just to prevent blur event when clicked in the middle of fields
     */
    ,onMouseDown:function(e) {
        this.wrapClick = 'td' === e.target.nodeName.toLowerCase();
    }
    // }}}
    // {{{
    /**
     * private
     * Handles Tab and Shift-Tab events
     */
    ,onSpecialKey:function(t, e) {
        var key = e.getKey();
        if(key == e.TAB) {
            if(t === this.df && !e.shiftKey && !this.tf.hidden) {
                e.stopEvent();
                this.df.beforeBlur();
                this.tf.focus();
            }
            if(t === this.tf && !this.df.hidden) {
              if(e.shiftKey) {
                  e.stopEvent();
                  this.df.focus();
                  // note by SeanZ:
                  // not having this.updateValue() on tf solves the problem that type-in n/t to
                  // date field and tab out and the time field is not populated.
              }  else if(this.gridEditor){
                  //Blur is not fired on tab out of the time field when in editor grid. To solve this, directly calling onBlur
                  Ext.form.Field.prototype.onBlur.call(this.tf);
              }
            }
            //this.updateValue(); // make it df only
        }
        // otherwise it misbehaves in editor grid
        if((key == e.ENTER || key === e.TAB) && t==this.df) {
            this.updateValue();
            // note by SeanZ:
            // not having this.updateValue() in tf on enter key solves the problem that select/type-in 
            // value to time field then hit enter causes the time field to be reset to blank.
        }

    } // eo function onSpecialKey
    // }}}
    // {{{
    /**
     * private Sets the value of DateField
     */
    ,setDate:function(date) {
        this.df.setValue(date);
    } // eo function setDate
    // }}}
    // {{{
    /** 
     * private Sets the value of TimeField
     */
    ,setTime:function(date) {
        this.tf.setValue(date);
    } // eo function setTime
    // }}}
    // {{{
    /**
     * private
     * Sets correct sizes of underlying DateField and TimeField
     * With workarounds for IE bugs
     */
    ,setSize:function(w, h) {
        if(!w || w == 'auto') {
            return;
        }
        if('bellow' == this.timePosition) {
            this.df.setSize(w, h);
            this.tf.setSize(w, h);
            if(Ext.isIE) {
                if (this.df.el) this.df.el.up('td').setWidth(w);
                if (this.tf.el) this.tf.el.up('td').setWidth(w);
            }
        }
        else {
            this.df.setSize(w - this.timeWidth - 4, h);
            this.tf.setSize(this.timeWidth, h);

            if(Ext.isIE) {
                if (this.df.el) this.df.el.up('td').setWidth(w - this.timeWidth - 4);
                if (this.tf.el) this.tf.el.up('td').setWidth(this.timeWidth);
            }
        }
    } // eo function setSize
    // }}}
    // {{{
    /**
     * @param {Mixed} val Value to set
     * Sets the value of this field
     */
    ,setValue:function(val) {
        if(!val && true === this.emptyToNow) {
            this.setValue(new Date());
            return;
        }
        else if(!val) {
            this.setDate('');
            this.setTime('');
            if (this.textOnly)
              Ext.ux.form.DateTime.superclass.setValue.call(this, val);
            this.updateValue();
            return;
        }
        val = val ? val : new Date(1970, 0 ,1, 0, 0, 0);
        var da, time;
        if(val instanceof Date) {
            this.setDate(val);
            this.setTime(val);
            this.dateValue = new Date(val);
            if (this.textOnly)
              Ext.ux.form.DateTime.superclass.setValue.call(this, Ext.util.Format.datetime(this.dateValue));
        }
        else {
            da = val.split(this.dtSeparator);
            this.setDate(da[0]);
            if(da[1]) {
                this.setTime(da[1]);
            }
        }
        this.updateValue();
    } // eo function setValue
    // }}}
    // {{{
    /**
     * private Updates the date part
     */
    ,updateDate:function() {

        var d = this.df.getValue();
        if(d) {
            if(!(this.dateValue instanceof Date)) {
                this.initDateValue();
                if(!this.tf.getValue())
                    this.setTime(this.dateValue);
            }
            if (this.df.ntFilter == 'n') {
                this.setTime(new Date());
                this.df.ntFilter = '';
            }
            if (this.df.ntFilter == 't') {
                this.setTime((new Date()).clearTime());
                if (this.dateValue instanceof Date){
                  this.dateValue.clearTime();
                }
                this.df.ntFilter = '';
            }
            this.dateValue.setMonth(0);
            this.dateValue.setFullYear(d.getFullYear());
            this.dateValue.setMonth(d.getMonth(), d.getDate());
        }
        else {
            this.dateValue = '';
            this.setTime('');
        }
    } // eo function updateDate
    // }}}
    // {{{
    /**
     * private
     * Updates the time part
     */
    ,updateTime:function() {
        var t = this.tf.getValue();
        if(t && !(t instanceof Date)) {
            t = Date.parseDate(t, this.tf.format);
        }
        if(t && !this.df.getValue()) {
            this.initDateValue();
            this.setDate(this.dateValue);
        }
        if(this.dateValue instanceof Date) {
            if(t) {
                this.dateValue.setHours(t.getHours());
                this.dateValue.setMinutes(t.getMinutes());
                this.dateValue.setSeconds(t.getSeconds());
            }
            else {
                this.dateValue.setHours(0);
                this.dateValue.setMinutes(0);
                this.dateValue.setSeconds(0);
            }
        }
    } // eo function updateTime
    // }}}
    // {{{
    /**
     * private Updates the underlying hidden field value
     */
    ,updateHidden:function() {
        if(this.isRendered) {
            var value = this.dateValue instanceof Date ? this.dateValue.format(this.hiddenFormat) : '';
            this.el.dom.value = value;
        }
    }
    // }}}
    // {{{
    /**
     * private Updates all of Date, Time and Hidden
     */
    ,updateValue:function() {

        this.updateDate();
        this.updateTime();
        this.updateHidden();

        return;
    } // eo function updateValue
    // }}}
    // {{{
    /**
     * @return {Boolean} true = valid, false = invalid
     * callse validate methods of DateField and TimeField
     */
    ,validate:function() {
        return this.df.validate() && this.tf.validate();
    } // eo function validate
    // }}}
    // {{{
    /**
     * Returns renderer suitable to render this field
     * @param {Object} Column model config
     */
    ,renderer: function(field) {
        var format = field.editor.dateFormat || Ext.ux.form.DateTime.prototype.dateFormat;
        format += ' ' + (field.editor.timeFormat || Ext.ux.form.DateTime.prototype.timeFormat);
        var renderer = function(val) {
            var retval = Ext.util.Format.date(val, format);
            return retval;
        };
        return renderer;
    } // eo function renderer
    // }}}
    ,destroy: function() {
      if (this.df) {
        this.df.destroy();
      }
      if (this.tf) {
        this.tf.destroy();
      }
      Ext.ux.form.DateTime.superclass.destroy.call(this);
    }

}); // eo extend

// A way to measure the time differences between server computer and the client computer.
Ext.ux.form.DateTime.ServerTimeBean = {
  numOfRequests : 0,
  conn : new Ext.data.Connection({
    url: Jaffa.data.getAppRootUrl()+'/js/extjs/jaffa/util/ServerParams.jsp'
  }),
  delta : 0
};

Ext.ux.form.DateTime.getServerTime = function () {
  var dd = new Date(new Date().getTime()+Ext.ux.form.DateTime.ServerTimeBean.delta);
  return dd;  
};

Ext.ux.form.DateTime.setServerTimeDelta = function () {
  var startTime = (new Date()).getTime();
  var opt = {
    params: {
      command: 'getServerTime'
    },
    success: function(response, option) {
      // measuring time difference between server and client machines.
      var endTime = (new Date()).getTime();
      var requestTime = endTime-startTime;
      var serverTime = (new Date(parseInt(response.responseText))).getTime();
      if (Ext.ux.form.DateTime.ServerTimeBean.minRequestTime==null) Ext.ux.form.DateTime.ServerTimeBean.minRequestTime = requestTime+1;
      if (requestTime < Ext.ux.form.DateTime.ServerTimeBean.minRequestTime && (serverTime <= startTime || serverTime >= endTime)) {
        Ext.ux.form.DateTime.ServerTimeBean.serverTimeDelta = serverTime-(endTime+startTime)/2;
      }
      if (Ext.ux.form.DateTime.ServerTimeBean.numOfRequests < 7) {
        // re-run the calibration
        setTimeout('Ext.ux.form.DateTime.setServerTimeDelta()', 3000);
      } else {
        delete Ext.ux.form.DateTime.ServerTimeBean.numOfRequests;
        delete Ext.ux.form.DateTime.ServerTimeBean.minRequestTime;
        return;
      }
      Ext.ux.form.DateTime.ServerTimeBean.numOfRequests++;
    }
  };
  Ext.ux.form.DateTime.ServerTimeBean.conn.request(opt);
};

Ext.ux.form.DateTime.getServerTimezone = function () {
  var opt = {
    params : { command : 'getServerTimeZone'},
    success: function(response, option) {
      
    }
  }
};
// Ext.ux.form.DateTime.setServerTimeDelta();
// End of measureing the server/client computer time differences

// register xtype
Ext.reg('xdatetime', Ext.ux.form.DateTime);

Ext.ux.form.DateTime.renderer = function(xdt) {
  return typeof xdt == 'object' ? xdt.format1()+' '+xdt.format0() : xdt;
};
