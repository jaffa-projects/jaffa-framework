/** Declare Extension and Jaffa Name Spaces */
Ext.namespace("Ext.ux","Ext.ux.form","Ext.ux.grid","Ext.ux.plugins",
              "Jaffa","Jaffa.component","Jaffa.data","Jaffa.finder","Jaffa.attachment", 'Jaffa.widget',
              "Jaffa.form","Jaffa.grid","Jaffa.maintenance","Jaffa.maintenance.plugins","Jaffa.state","Jaffa.tree","Jaffa.util");


/**
 * Create a new method for Wrapping a function where you can access its result
 * http://www.sencha.com/forum/archive/index.php/t-97018.html
 */
Ext.apply(Function.prototype, {
  createWrapper : function(fcn, scope) {
    var method = this;
    return !Ext.isFunction(fcn) ? this : function() {
      //console.warn("Wrapped Function Invoked", method);
      this.wrappedFn = method;
      var args = Array.prototype.slice.call(arguments, 0);
      return fcn.apply(scope || this || window, args);
    };
  }
});
              
/**
 * @method

 * String convenience method for checking if the start of this string equals a
 * given string.
 *
 * @return boolean
 */
String.prototype.startsWith = function(str){
    return (this.indexOf(str) === 0);
}

/**
 * @method
 * String convenience method for checking if the end of this string equals a
 * given string.
 *
 * @return boolean
 * @throws IllegalArgumentException
 *       for parameters not of type String
 */
String.prototype.endsWith = function(s) {
  if ('string' != typeof s) {
    throw ('IllegalArgumentException: Must pass a string to String.prototype.endsWith()');
  }
  var start = this.length - s.length;
  return this.substring(start) == s;
};

htmlEntityDecode = function(str){
  try{
		var  tarea=document.createElement('textarea');
		tarea.innerHTML = str; return tarea.value;
		tarea.parentNode.removeChild(tarea);
	} catch(e){
		//for IE add <div id="htmlconverter" style="display:none;"></div> to the page
		document.getElementById("htmlconverter").innerHTML = '<textarea id="innerConverter">' + str + '</textarea>';
		var content = document.getElementById("innerConverter").value;
		document.getElementById("htmlconverter").innerHTML = "";
		return content;
	}
};


// add RegExp.escape if it has not been already added
if ('function' !== typeof RegExp.escape)
  /**
   * @method @static
   * RegExp convenience method for escaping characters in a string that are considered
   * 'special' characters used by RegExp, for example .*+? ...
   *
   * @param {String} s String with data to escape
   * @return {String} String with special characters prefixed with '\'
   */
  RegExp.escape = function(s){
    if ('string' !== typeof s) {
      return s;
    }
    // Note: if pasting from forum, precede ]/\ with backslash manually
    return s.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
  }; // eo function escape


Ext.applyIf(Array.prototype, {
  /**
   * Checks whether or not the specified string matches partially/entirely with an item in the array.
   * @param {Object} o The object to check for
   * @param {Number} from (Optional) The index at which to begin the search
   * @return {Number} The index of o in the array (or -1 if it is not found)
   */
  matches : function(o, from){
    var len = this.length;
    from = from || 0;
    from += (from < 0) ? len : 0;
    for (; from < len; ++from){
      if(this[from].indexOf(o) >= 0){
        return from;
      }
    }
    return -1;
  }
});
/**
 * @class Ext.grid.GridPanel
 */
Ext.override(Ext.grid.GridPanel, {
  /**
   * EXTENSION - Resize the overall grid panel based on the width of all currently visible columns
   */
  resizeToColumns: function () {
    // Dynamically set the grid width
    var w=0;
    var c=this.colModel.config;
    for(var i=0; i < c.length; i++)
      if(!c[i].hidden /*&& c[i].width*/)
        w+=c[i].width || this.colModel.defaultWidth || 100;
    // console.debug("RESIZED grid from ", this.width, " to ", w);
    if (this.rendered) {
      this.setWidth(w);
      // doLayout() sets back the scrollbars correctly on the grid canvas
      if (this.ownerCt && typeof this.ownerCt.doLayout == 'function')
        this.ownerCt.doLayout();
    } else this.width = w;
  }

});
Ext.applyIf(Ext, {
  /**
   * Checks whether or not the specified object is empty or not.
   * @param {Object} obj
   * Ext.isEmptyObject({}) // true
   * Ext.isEmptyObject({ name: "Red" }) // false
   */
  isEmptyObject: function( obj ) {
    if(obj === null || !Ext.isDefined(obj)){
      return true;
    }
    for (var name in obj ) {
      return false;
    }
    return true;
  }
});

/**
 * @class Ext.form.Checkbox
 */
Ext.override(Ext.form.Checkbox, {
  initValue : function() {
    this.originalValue = this.getValue();
    if (this.textOnly && this.xtype !== 'hidden'){
      this.setTextOnly(true);
    } 
  },
  setTextOnly: function(yesNo){
    if (this.rendered){
      this.setDisabled(yesNo);
    } else {
      this.disabled = yesNo;
    }
  }
});

/**
 * @class Ext.form.Field
 */
Ext.override(Ext.form.Field, {
  // private, retains a set of invalid values this should be used by grid column renderers to determine whether
  // a value missing from combo store should be fetched from dwr.
  invalidValues: new Ext.util.MixedCollection(),

  /**
   * @cfg {boolean} textOnly
   * make the field display the data at plain text instead of using the data entry version of this widget
   */
  textOnly: false,

  //hideFormItem When the field is in a form, this method will hide the fieldLabel and field, field.hide() will only hide the field
  hideFormItem: function(){
    this.setTextOnly(this.textOnly);
    if (this.rendered) {
      var formItem = this.getEl().up('.x-form-item');
      this.hide();
      this.disable(); //prevents validation
      if (formItem){
        formItem.setDisplayed(false);
      }
    } else {
      this.hidden = true;
      this.disabled = true; //prevents validation
      this.on('render', function() {
        var formItem = this.getEl().up('.x-form-item');
        if (formItem){
          formItem.setDisplayed(false);
        }
      });
    }
  },
    
  showFormItem: function(){
    if(this.rendered){
      var formItem = this.getEl().up('.x-form-item');
      this.show();
      this.enable();
      if(formItem){
        formItem.setDisplayed(true);
      }
    } else {
      this.hidden = false;
      this.disabled = false;
      this.on('render', function(){
        var formItem = this.getEl().up('.x-form-item');
        if(formItem){
          formItem.setDisplayed(true);
        }
      });
    }
    this.setTextOnly(this.textOnly);
  },

  /**
   * @private
   * @override Override base version so the textOnly value is set from the configuration on initial render
   */
  initValue : function(){
    if (this.textOnly && this.xtype !== 'hidden'){
      this.setTextOnly(true);
    }
    if(this.value !== undefined){
      this.setRawValue(this.value);
    }else if(this.el.getValue() != undefined && this.el.getValue().length > 0){
      this.setRawValue(this.el.getValue());
    }
  },

  /**
   * Set the field to be text only. Unlike the readOnly setting which just makes the
   * editbox readonly, this causes the field to be rendered using plain html
   */
  setTextOnly: function(textOnly) {
    if (this.rendered) {
      if (textOnly) {
        if (document.getElementById(this.id + "-text")) {
          this.hide();
        } else {
          if (!this.hidden){
            this.hide();
          }
          var txt = document.createElement('div');
          txt.setAttribute("id", this.id + "-text");
          txt.setAttribute("class", "x-form-textonly");
          if (this.xtype == 'xdatetime') {
              var w = this.dateValue;
              if (w) txt.appendChild(document.createTextNode(Ext.util.Format.datetime(w)));
          } else if (this.xtype == 'datefield') {
              var w = this.getValue();
              if (w) txt.appendChild(document.createTextNode(Ext.util.Format.date(w, this.format)));
          } else {
              var uStyle = this.initialConfig.style || this.style;
              if (Boolean(uStyle)) {
                if (this.width && !this.descriptionMapping && this.ownerCt.initialConfig.layout != 'adjacentfield') {
                  if (typeof uStyle == 'string' && uStyle.indexOf('width')==-1 && this.width!='auto') uStyle += 'width:'+this.width+'px;';
                  else if (typeof uStyle == 'object' && !Boolean(uStyle.width)) uStyle.width = this.width;
                }
                Ext.DomHelper.applyStyles(txt, uStyle);
              }
              // assign the value to the widget
              var w = this.getEl().getValue();
              if (this.xtype == 'radiogroup') {
                w = this.getValue().boxLabel;
              }
              if (this.xtype == 'currencyfield') {
                w = this.getValue();
                if (w !== null) w = Ext.util.Format.money(w);
              }
              txt.appendChild(document.createTextNode(w));
          }
          var formEl = document.getElementById("x-form-el-" + this.id);
          if(formEl){
            formEl.appendChild(txt);
          }
        }
      } else {
        var txt = document.getElementById(this.id + "-text");
        if (txt) {
            txt.parentNode.removeChild(txt);
        }
        this.show();
      }
    } else {
      if (textOnly) {
        if (typeof this.hide === 'function')
          this.hide();
        else
         this.hidden = true;
      }else{
        if (typeof this.show === 'function')
          this.show();
        else
         this.hidden = true;
      }
    }
    this.textOnly = textOnly;
    return this;
  },

  /**
   * Returns the normalized data value (undefined or emptyText will be returned as '').
   * To return the raw value see {@link #getRawValue}.
   *
   * @override This is overridden as the data is stored differently if this is a textOnly field
   * @return {Mixed} value The field value
   */
  getValue : function(){
    if(!this.textOnly || this.xtype == 'htmleditor' || this.xtype == 'xhtmleditor'){
      if(!this.rendered) {
        return this.value;
      }
      var v = this.el.getValue();
      if(v === this.emptyText || v === undefined){
        v = '';
      }
      return v;
    } else {
      return this.value;
    }
  },

  /**
     * Sets the underlying DOM field's value directly, bypassing validation.  To set the value with validation see {@link #setValue}.
     * @param {Mixed} value The value to set
     * @return {Mixed} value The field value that is set
     */
    setRawValue: function(v){
        if (!this.textOnly || this.xtype == 'htmleditor' || this.xtype == 'xhtmleditor') {
          return this.rendered && this.el.dom ? (this.el.dom.value = (Ext.isEmpty(v) ? '' : v)) : '';
        } else {
          this.value = v;
          if (this.rendered) {
            this.el.dom.value = (v === null || v === undefined ? '' : v);
            if (document.getElementById(this.id + "-text")) {
              if (this.xtype == 'datefield') {
                var w = v ? new Date(Date.parseDate(v, this.format)) : null;
                if (w) document.getElementById(this.id + "-text").innerHTML = Ext.util.Format.date(w, this.format);
              }
              else if (this.xtype == 'xdatetime') {
                var w = v ? new Date(Date.parseDate(v, this.format || Ext.util.Format.defaultDateTimeLayout)) : null;
                if (w) document.getElementById(this.id + "-text").innerHTML = Ext.util.Format.datetime(w);
              }
              else if (this.xtype == 'currencyfield') {
                if (v || v == 0) document.getElementById(this.id + "-text").innerHTML = (v.search(this.stripCharsRe) == -1) ?Ext.util.Format.money(v) : v;
                else document.getElementById(this.id + "-text").innerHTML = '';
              }
              else if (this.displayRenderer){
                document.getElementById(this.id + "-text").innerHTML = this.displayRenderer(v);
              }
              else document.getElementById(this.id + "-text").innerHTML = (v === null || v === undefined ? '' : v);
            }
            else {
              this.hide();
              var txt = document.createElement('div');
              txt.setAttribute("id", this.id + "-text");
              txt.setAttribute("class", "x-form-textonly");
              if (this.xtype == 'datefield') txt.appendChild(document.createTextNode(Ext.util.Format.date(this.getEl().getValue(), this.format)));
              else if (this.xtype == 'xdatetime') {
                var w = this.dateValue;
                if (w) txt.appendChild(document.createTextNode(Ext.util.Format.datetime(w)));
              }
              else if (this.xtype == 'currencyfield') {
                var w = this.getValue();
                if (w !== null) txt.appendChild(document.createTextNode(Ext.util.Format.money(w)));
                else txt.appendChild(document.createTextNode(''));
              }
              else txt.appendChild(document.createTextNode(this.getEl().getValue()));
              var formEl = document.getElementById("x-form-el-" + this.id);
              if (formEl) {
                formEl.appendChild(txt);
              }
            }
          }
        }
    },

  /**
   * Sets a data value into the field and validates it.
   * To set the value directly without validation see {@link #setRawValue}.
   *
   * @override This is overridden as the data is stored differently if this is a textOnly field
   * @param {Mixed} value The value to set
   */
  setValue: function(v){
      if (!this.textOnly || this.xtype == 'htmleditor' || this.xtype == 'xhtmleditor') {
          this.value = v;
          if (this.rendered) {
              // this.el.dom.value = (v === null || v === undefined ? '' : v);
              // use setRawValue() to set dom value
              this.setRawValue(v);
              if (this.pushValue) this.pushValue();
              this.validate();
          }
      }
      else {
          this.value = v;
          if (this.rendered) {
              this.el.dom.value = (v === null || v === undefined ? '' : v);
              if (document.getElementById(this.id + "-text")) {
                  if (this.xtype == 'datefield') {
                      var w = v ? new Date(Date.parseDate(v, this.format)) : null;
                      document.getElementById(this.id + "-text").innerHTML = (w ? Ext.util.Format.date(w) : '');
                  } else if (this.xtype == 'xdatetime') {
                      var w = v ? new Date(Date.parseDate(v, this.format || Ext.util.Format.defaultDateTimeLayout)) : null;
                      document.getElementById(this.id + "-text").innerHTML = (w ? Ext.util.Format.datetime(w) : '');
                  } else if (this.xtype == 'currencyfield') {
                      if (v || v==0) document.getElementById(this.id + "-text").innerHTML = Ext.util.Format.money(v);
                      else document.getElementById(this.id + "-text").innerHTML = '';
                  } else if (this.displayRenderer){
                    document.getElementById(this.id + "-text").innerHTML = this.displayRenderer(v);
                  } else
                      document.getElementById(this.id + "-text").innerHTML = (v === null || v === undefined ? '' : v);
             } else {
                  this.hide();
                  var txt = document.createElement('div');
                  txt.setAttribute("id", this.id + "-text");
                  txt.setAttribute("class", "x-form-textonly");
                  if (this.xtype == 'datefield')
                      txt.appendChild(document.createTextNode(Ext.util.Format.date(this.getEl().getValue())));
                  else if (this.xtype == 'xdatetime') {
                      var w = this.dateValue;
                      if (w)
                          txt.appendChild(document.createTextNode(Ext.util.Format.datetime(w)));
                  } else if (this.xtype == 'currencyfield') {
                      var w = this.getValue();
                      if (w !== null) txt.appendChild(document.createTextNode(Ext.util.Format.money(w)));
                      else txt.appendChild(document.createTextNode(''));
                  } else
                      txt.appendChild(document.createTextNode(this.getEl().getValue()));
                  var formEl = document.getElementById("x-form-el-" + this.id);
                  if (formEl) {
                    formEl.appendChild(txt);
                  }
              }
          }
      }
  }


});
// setting all number fields right aligned.
Ext.form.NumberField.prototype.style = 'text-align:right;';

Ext.form.CheckboxGroup.prototype.setValue = Ext.form.CheckboxGroup.prototype.setValue.createSequence(function() {
  if (this.textOnly) {
    var v = this.getValue() && this.getValue().boxLabel;
    if (this.rendered) {
      if (document.getElementById(this.id + "-text")) {
        document.getElementById(this.id + "-text").innerHTML = (v === null || v === undefined ? '' : v);
     } else {
          this.hide();
          var txt = document.createElement('div');
          txt.setAttribute("id", this.id + "-text");
          txt.setAttribute("class", "x-form-textonly");
          txt.appendChild(document.createTextNode(v));
          var formEl = document.getElementById("x-form-el-" + this.id);
          if (formEl) {
            formEl.appendChild(txt);
          }
      }
    }
  }
  return this;
});

// Remove state listeners on gridfilters as we don't want filter information saved into state.
Ext.ux.grid.GridFilters.prototype.init = Ext.ux.grid.GridFilters.prototype.init.createSequence(function(grid){
    grid.un('beforestaterestore',this.applyState, this);
    grid.un('beforestatesave',this.saveState, this);
});

/**
 * @class Ext.form.DateField
 */
Ext.override(Ext.form.DateField, {
  numberTooLarge: 'Must use value less than 10000 when using t or n date format.',
  nRegExp: /(\bn\s*[\+\-]\s*[0-9]+)/i,
  tRegExp: /(\bt\s*[\+\-]\s*[0-9]+)/i,
  beforeBlur : function(){
    var v;
    if (!this.isCriteriaField) {
      var v = this.getRawValue().trim(), isNT=false;
      if (v == 't' || v == 'T' || this.tRegExp.test(v)) {
        this.ntFilter = 't';
        isNT = true;
      }
      
      if (v == 'n' || v == 'N' || this.nRegExp.test(v)) {
        this.ntFilter = 'n';
        isNT = true;
      }
      
      if (isNT) {
        if (this.getRawValue().indexOf('+') > 0) {
          var a = (this.getRawValue().split('+'))[1];
          if (!Ext.num(a) || parseInt(Ext.num(a))>9999) return false;
          var d = new Date();
          d.setDate(d.getDate() + parseInt(Ext.num(a)));
          this.setValue(d); 
        }
        else 
          if (this.getRawValue().indexOf('-') > 0) {
            var a = (this.getRawValue().split('-'))[1];
            if (!Ext.num(a) || parseInt(Ext.num(a))>9999) return false;
            var d = new Date();
            d.setDate(d.getDate() - parseInt(Ext.num(a)));
            this.setValue(d);
          }
          else {
            var d = new Date();
            this.setValue(d);
          }
      }
      
      v = this.parseDate(this.getRawValue());
    } else{
      v = this.getRawValue();
    }
    if(v){
      this.setValue(v);
    }
  },
  setValue : function(date){
    if (this.isCriteriaField && (typeof date == 'string' && (this.getRawValue().indexOf('n') || this.getRawValue().indexOf('t')))) {
      return Ext.form.DateField.superclass.setValue.call(this, date);
    }
    else {
      return Ext.form.DateField.superclass.setValue.call(this, this.formatDate(this.parseDate(date)));
    }
  },
  getValue : function(){
    if (this.isCriteriaField) {
      if (typeof this.parseDate(Ext.form.DateField.superclass.getValue.call(this))=='object')
        return this.parseDate(Ext.form.DateField.superclass.getValue.call(this));
      else
        return Ext.form.DateField.superclass.getValue.call(this);
    }else{
      return this.parseDate(Ext.form.DateField.superclass.getValue.call(this)) || "";
    }
  },  
  validateValue : function(value) {
    if (!this.isCriteriaField){
      if (value.indexOf('+')>=0 || value.indexOf('-')>=0){
        var numberValue = value.split(/-|\+/)[1];
        if (Ext.isNumber(Ext.num(numberValue)) && parseInt(Ext.num(numberValue))>9999){
          this.markInvalid(this.numberTooLarge);
          return false;
        }
      }
      var error = this.getErrors(value)[0];
      if (error == undefined) {
        return true;
      } else {
        this.markInvalid(error);
        return false;
      }
    }else{
      var error = this.getErrors(this.evalDate(value))[0];
      if (error == undefined) {
        return true;
      } else {
        this.markInvalid(error);
        return false;
      }
    }
  },
  evalDate: function(value){
    if (!value||value=='')
      return value;
    
    if (this.parseDate(value))
      return value;

    if (typeof value == 'string') value = value.toLowerCase();

    if (value == 't' || value == 'n') {
      return new Date();
    }

    if (value.indexOf('t') == 0 || value.indexOf('n') == 0) {
      if (value.indexOf('+') == 1) {
        var a = (value.split('+'))[1];
        if (Ext.isNumber(Ext.num(a))) {
          if (Ext.num(a)>9999) return 'value too large';
          var d = new Date();
          d.setDate(d.getDate() + parseInt(a));
          return d;
        }
      }
      if (value.indexOf('-') == 1) {
        var a = (value.split('-'))[1];
        if (Ext.isNumber(Ext.num(a))) {
          if (Ext.num(a)>9999) return 'value too large';
          var d = new Date();
          d.setDate(d.getDate() - parseInt(a));
          return d;
        }
      }
    }
    return 'invalid date';
  }
});


/**
 * @class Ext.form.HtmlEditor
 */
Ext.override(Ext.form.HtmlEditor, {

  initValue : function(){
      if(this.value !== undefined){
          this.setRawValue(this.value);
      }else if(this.el.getValue().length > 0){
          this.setRawValue(this.el.getValue());
      }
  },

  setValue : function(v){
    Ext.form.HtmlEditor.superclass.setValue.call(this, v);
    if (this.rendered && this.wrap && this.wrap.dom && this.wrap.isMasked()){
      var roMask =  Ext.Element.data(this.wrap.dom, 'mask');
      if(roMask && roMask.dom){
        roMask.dom.innerHTML = v;
        if (roMask.childNodes && roMask.childNodes.length > 0)
          for (var i=0; i<roMask.childNodes.length; i++) {
            if (roMask.childNodes[i].href && roMask.childNodes[i].href != '')
              roMask.childNodes[i].target = '_blank';
          };
      }
    }
  },

  /**
   * Set a textonly mask over the editor
   * @param {Boolean} textOnly - True to set the read only property, False to switch to the editor
   */
  setTextOnly: function(textOnly){
        if(textOnly){
            if (this.rendered) {
                this.syncValue();
                var roMask = this.wrap.mask();
                roMask.dom.style.filter = "alpha(opacity=100);"; // IE
                roMask.dom.style.opacity = "1"; // Mozilla
                roMask.dom.style.background = "CCCCCC";
                roMask.dom.style.overflow = "scroll";
                roMask.dom.innerHTML = this.getValue();
                if (roMask.dom.childNodes && roMask.dom.childNodes.length > 0)
                    for (var i=0; i<roMask.dom.childNodes.length; i++) {
                      if (roMask.dom.childNodes[i].href && roMask.dom.childNodes[i].href != '')
                            roMask.dom.childNodes[i].target = '_blank';
                    };
                if(this.el && this.el.dom) this.el.dom.readOnly = true;
            }else{
                this.textOnly = true;
            }
      } else {
        if(this.rendered){
              this.wrap.unmask();
          }
          if(this.el && this.el.dom) this.el.dom.readOnly = false;
          this.textOnly = false;
      }
  },

    // private
    initEvents : function(){
        this.originalValue = this.getValue();
    },

   // private
    onRender : function(ct, position){
        Ext.form.HtmlEditor.superclass.onRender.call(this, ct, position);
        this.el.dom.style.border = '0 none';
        this.el.dom.setAttribute('tabIndex', -1);
        this.el.addClass('x-hidden');
        if(Ext.isIE){
            this.el.applyStyles('margin-top:-1px;margin-bottom:-1px;');
        }
        this.wrap = this.el.wrap({
            cls:'x-html-editor-wrap', cn:{cls:'x-html-editor-tb'}
        });

        this.createToolbar(this);

        this.disableItems(true);

        this.tb.doLayout();

        this.createIFrame();

        if(!this.width){
            var sz = this.el.getSize();
            this.setSize(sz.width, this.height || sz.height);
        }
        this.resizeEl = this.positionEl = this.wrap;
        //added to support jaffa's textOnly feature
        this.setTextOnly.defer(500, this, [this.textOnly]);
    }

});

/**
 * @class Ext.grid.EditorGridPanel
 */
Ext.override(Ext.grid.EditorGridPanel, {
  onRender: function(ct, position) {
    // assign the grid and editor to comboboxes
    var cm = this.getColumnModel().config;
    for (var i=0; i<cm.length; i++) {
      if (cm[i].editor && cm[i].editor.field) {
        cm[i].editor.field.editor = cm[i].editor;
        cm[i].editor.field.parentGrid = this;
        cm[i].editor.field.editorRecordField = cm[i].dataIndex;
      }
    }

    Ext.grid.EditorGridPanel.superclass.onRender.apply(this, arguments);
  }
});



/**
 * Extend the ExtJS Format object to use variables for the default date, dateTime
 * and money layouts. Added additional renderers for dateTime as they don't have one
 *
 * This assumes the following properties have been set for the specific locale
 *   Ext.util.Format.defaultDateLayout = 'd/m/Y';
 *   Ext.util.Format.defaultDateTimeLayout = 'd/m/Y h:ia';
 *   Ext.util.Format.defaultCurrencySymbol = '£';
 */
if(Ext.util.Format){

  Ext.util.Format.date = function(v, format){
    if(!v) return "";
    format = format || Ext.util.Format.defaultDateLayout;
    if(!(v instanceof Date)) v = Date.parseDate(v, format);
    return v.dateFormat(format);
  };

  Ext.util.Format.datetime = function(v, format){
    if(!v) return "";
    format = format || Ext.util.Format.defaultDateTimeLayout;
    if(!(v instanceof Date)) v = Date.parseDate(v, format);
    return v.dateFormat(format);
  };

  Ext.util.Format.dateTimeRenderer = function(format){
    return function(v){
      return Ext.util.Format.datetime(v, format);
    };
  };

  Ext.util.Format.money = function(v){
    if (v==='' || isNaN(v)) return'';
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    v = String(v);
    var ps = v.split('.');
    var whole = ps[0];
    var sub = ps[1] ? '.'+ ps[1] : '.00';
    var r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
      whole = whole.replace(r, '$1' + ',' + '$2');
    }
    v = whole + sub;
    if(v.charAt(0) == '-'){
      return '-'+ Ext.util.Format.defaultCurrencySymbol + v.substr(1);
    }
    return v===''?'':Ext.util.Format.defaultCurrencySymbol +  v;
  };

};

// Make the Date Widgets honor the same defaults as the formatter!
if(Ext.DatePicker)
  Ext.DatePicker.prototype.format = Ext.util.Format.defaultDateLayout;
if(Ext.form.DateField)
  Ext.form.DateField.prototype.format = Ext.util.Format.defaultDateLayout;
  
//Remove altFormats that don't have seperators except mdY (mmddyyyy) and added 5 digit julian date(yyddd) as a valid alt format
Ext.form.DateField.prototype.altFormats="m/d/Y|n/j/Y|n/j/y|m/j/y|n/d/y|m/j/Y|n/d/Y|m-d-y|m-d-Y|m/d|m-d|mdY|Y-m-d|n-j|n/j|yJ";

//Add extra altFormats for if using UK style date format
if (Ext.form.DateField.prototype.format=="d/m/Y"||Ext.form.DateField.prototype.format=="d/m/y")
  Ext.form.DateField.prototype.altFormats="d/m/Y|j/n/Y|j/n/y|j/m/y|d/n/y|j/m/Y|d/n/Y|d-m-y|d-m-Y|d/m|d-m|dmY|Y-d-m|yJ";

if (Ext.form.DateField.prototype.format=="m/d/Y"||Ext.form.DateField.prototype.format=="m/d/y")
  Ext.form.DateField.prototype.altFormats="m/d/Y|n/j/Y|n/j/y|m/j/y|n/d/y|m/j/Y|n/d/Y|m-d-y|m-d-Y|m/d|m-d|mdY|Y-m-d|yJ";


Ext.override(Ext.Button, {
  /**
   * To trickle the functional state change (disable/enable) to another button.
   * @param {Object} btn
   */
  synchStateWith : function(btn) {
    if (btn && btn instanceof Ext.Button) {
      if (this.disabled) {
        btn.disable();
      } else {
        btn.enable();
      }
      this.on('disable', function() {btn.disable();});
      this.on('enable', function() {btn.enable();});
    } else {
      console.debug('trying to register a non-button object to the state synchronization.');
    }
  }
});

Ext.form.ComboBox.override({
    validate : function() {
        if(this.disabled || this.validateValue(this.processValue(this.getValue() || ''))){
            this.clearInvalid();
            return true;
        }
        return false;
    },
    
    // override the one in Ext.form.Field so that it handles displayValue correctly.
    initValue: function() {
      if (this.textOnly && this.xtype !== 'hidden'){
        this.setTextOnly(true);
      }
      if(this.value !== undefined){
        this.setValue(this.value);
      }else if(this.el.getValue() != undefined && this.el.getValue().length > 0){
        this.setValue(this.el.getValue());
      }
      
      if(this.hiddenField){
        this.hiddenField.value =
             Ext.value(Ext.isDefined(this.hiddenValue) ? this.hiddenValue : this.value, '');
      }
    }
});

/** This is an extension for menus that are so big the need scroll functionality
 * This extension was provided by the community via the following post
 * http://extjs.com/forum/showthread.php?p=208950#post208950
 * @author <a href='http://extjs.com/forum/member.php?u=13909'>vahid4134</a>
 */
Ext.override(Ext.menu.Menu, {
  maxHeight:undefined,
  saveState:false,

  onScrollTop:function(){
    this.ul.scroll("t",50,true);
  }

  ,onScrollBottom:function(){
    this.ul.scroll("b",50,true);
  }

  ,showAt : function(xy, parentMenu, /* private: */_e) {
     this.parentMenu = parentMenu;
     if(!this.el){
       this.render();
     }
     if(_e !== false){
       this.fireEvent("beforeshow", this);
       xy = this.el.adjustForConstraints(xy);
     }
     var maxHeight= (this.maxHeight==undefined)? Ext.lib.Dom.getViewHeight()-xy[1] : this.maxHeight;
     var last_ul_height=this.ul.getHeight();
     if(last_ul_height>maxHeight || this.scrolled==true){
       this.ul.setHeight(maxHeight-60);
       if(!this.saveState){
         this.ul.scrollTo("top",1);
       }
     }
     if(last_ul_height>maxHeight){
       var sb = this.el.createChild({
         tag: "div", cls: "menu-scroll-bottom"
       });
       sb.addClassOnOver('x-tab-scroller-left-over');
       this.TopRepeater = new Ext.util.ClickRepeater(sb, {
         interval : 200,
         handler: this.onScrollBottom,
         scope: this
       });
       var st = this.el.insertFirst({
         tag: "div", cls: "menu-scroll-top"
       });
       st.addClassOnOver('x-tab-scroller-left-over');
       this.leftRepeater = new Ext.util.ClickRepeater(st, {
         interval : 200,
         handler: this.onScrollTop,
         scope: this
       });
       this.scrollBottom = sb;
       this.scrollTop = st;
       this.scrolled=true;
     }
     this.el.setXY(xy);
     this.el.show();
     this.hidden = false;
     this.focus();
     this.fireEvent("show", this);
   }
});


/**
 * This is an extension to the Ext.Ajax instance.
 * Sends a "synchronous" HTTP request to a remote server.
 * @param {Object} options - Should contain the following properties:
 *          {String} url - The URL to which to send the request.
 *          {String} method (Optional) - The HTTP method to use for the request. Defaults to "GET" if no parameters are being sent, and "POST" if parameters are being sent. Note that the method name is case-sensitive and should be all caps.
 *          {Object} params (Optional) - An object containing properties which are used as parameters to the request.
 * @return the responseText.
 */
Ext.Ajax.synchronousRequest = function (options) {
  var url = options.url;
  var method = options.method ? options.method : (options.params ? "POST" : "GET");
  var params = options.params && typeof options.params == "object" ? Ext.urlEncode(options.params) : options.params;

  //var c = Ext.lib.Ajax.getConnectionObject().conn;
  var c;
  try {
    c = new XMLHttpRequest();
  } catch (e) {
    var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
    for (var i = 0; i < activeX.length; i++) {
      try {
        c = new ActiveXObject(activeX[i]);
        break;
      } catch(e) {
      }
    }
  }
  c.open(method, url, false);
  c.setRequestHeader("X-Requested-With", "XMLHttpRequest");
  if (method == "POST")
    c.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  c.send(params || null);
  return c.responseText;
};


/**
 * Extends Ext.data.Store to implement parallel feature of caching deleted records in ExtJs 3.
 * FIXME -- this implementation should be modified when switching to ExtJs 3.
 */
Ext.override(Ext.data.Store, {
  // and array of removed records, it has to be created for each instance of store
  removed: null,

  deleteRecord: function(record, index) {
    if (this.modified.indexOf(record) != -1) {  // <-- handled already if @cfg pruneModifiedRecords == true
      this.modified.remove(record);
    }
    if (!record.isNew) {
      this.removed = this.removed || [];
      this.removed.push(record);
      record.lastIndex = index;
    }
    this.remove(record);
  },

  getDeletedRecords: function() {
    if (this.removed) return this.removed;
    else return null;
  }
});

/**
 * This is a render event handler used to add a tooltip to a Ext.Form.Field.
 * Ext 3.1 may have implementation for the same function
 * To use this method, the field should have a qtipText property defined. Then this method
 * should be added as a listener to render event.
 * In ExtJs 3.1, Ext.form.Field has afterrender event. If this method is still used,
 * it should be added to afterrender event.
 */
Ext.form.Field.TooltipRender = function() {
  if(this.qtipText){
    Ext.QuickTips.register({
      target:  this.getEl(),
      title: '',
      text: this.qtipText,
      enabled: true
    });
    // place the same qtip to the label
    var label;
    var wrapDiv = this.getEl().up('div.x-form-item');
    if(wrapDiv) label = wrapDiv.child('label');
    if (!label) {
      wrapDiv = this.getEl().up('div.x-form-element');
      if(wrapDiv) label = wrapDiv.child('label');
    }
    if(label){
      Ext.QuickTips.register({
          target:  label,
          title: '',
          text: this.qtipText,
          enabled: true
      });
    }
  }
};


/**
 * A helper routine to determine the z-index for a rendered component.
 * If the z-index is not found, then this routine traverses up the DOM, until a z-index is found.
 * Nothing is returned, if a z-index is not found.
 */
Ext.Component.prototype.findZIndex = function () {
  var el = this.el;
  while (el) {
    var zindex = el.getStyle('z-index');
    if (!isNaN(zindex))
      return parseInt(zindex);
    el = el.dom && el.dom.parentNode && el.dom.parentNode.id ? Ext.fly(el.dom.parentNode.id) : null;
  }
};

/**
 * Add mechanism to auto focus on the first open editable field.
 * This method only works after the container has been rendered.
 */
Ext.Container.prototype.focusFirstField = function() {
  if (this.rendered && this.isVisible()) {
    var ff = null;
    this.items.each(function(itm) {
      if (ff) return false;
      if (typeof itm.focusFirstField == 'function') {
        ff = itm.focusFirstField();
      } else if (itm.rendered && itm.isVisible() && itm instanceof Ext.form.Field && !itm.readOnly && !itm.textOnly) {
        ff = itm;
        ff.focus();
      }
    }, this);
    return ff;
  }
};

/**
 * Extends the feature of the checkbox in the header so that it will be reset to uncheck after the grid
 * is loaded
 */
(function() {
  var original= Ext.grid.CheckboxSelectionModel.prototype.initEvents;
  Ext.grid.CheckboxSelectionModel.prototype.initEvents = function() {
    original.apply(this, arguments);
    this.grid.on('render', function() {
      Ext.fly(this.getView().innerHd).on('mousedown', function(e,t) {
        var selM = this.getSelectionModel();
        if (t.className == 'x-grid3-hd-checker' && selM && !selM.singleSelect && !selM.headerCheckBoxEl) {
          selM.headerCheckBoxEl = t;
          this.getStore().on('load', function(store, recs) {
            this.getSelectionModel().clearSelections();
            var hcBoxEl = this.getSelectionModel().headerCheckBoxEl;
            if (hcBoxEl && hcBoxEl.parentNode)
              Ext.fly(hcBoxEl.parentNode).removeClass('x-grid3-hd-checker-on');
          }, this);
        }
      }, this, {single: true});
    }, this.grid, {single: true});
  }
})();

/**
 * Extend Panel and Button to use token for title/text.
 */
(function() {
  Ext.Panel.prototype.initComponent = Ext.Panel.prototype.initComponent.createInterceptor(function() {
    if (this.titleToken) {
      this.title = Labels.get(this.titleToken);
      //Adds header and footer blurbs to the fieldset, if corresponding labels are defined
      if((this.isXType(Ext.form.FieldSet) || this instanceof Ext.form.FieldSet) && Ext.isArray(this.items)){
        var header = Labels.get(this.titleToken+'.header');
        if(header && !(header.indexOf("?") === 0 && header.lastIndexOf("?") ===  (header.length -1))){
          this.items.splice(0,0,{xtype:'container', html : header, cls: "x-form-item-label",
            width : this.headerWidth || this.blurbWidth, style: {marginBottom: '15px'}
          })
        }
        var footer = Labels.get(this.titleToken+'.footer');
        if(footer && !(footer.indexOf("?")=== 0 && footer.lastIndexOf("?") ===  (footer.length -1))){
          this.items.push({xtype:'container', html : footer,  cls: "x-form-item-label",
            width : this.footerWidth || this.blurbWidth, style: {marginTop: '15px'}
          })
        }
      }
    }
    return true;
  });
  
  Ext.Button.prototype.initComponent = Ext.Button.prototype.initComponent.createInterceptor(function() {
    if (this.textToken) {
      this.text = Labels.get(this.textToken);
    }
    return true;
  });
  
  
  // Overload the input to Ext.Message.show to expand token if passed
  Ext.MessageBox.show = Ext.MessageBox.show.createWrapper(function(config) {
    //console.warn("Ext.Message", this, config);
    if(config.titleToken && !config.title) 
        config.title = Labels.get(config.titleToken);
    if(config.msgToken && !config.msg) 
        config.msg = Labels.get(config.msgToken);
    return this.wrappedFn(config);
  });

})();

Ext.Button.prototype.setTextToken = function(token) {
  this.textToken = token;
  this.setText(Labels.get(this.textToken));
}

Ext.Panel.prototype.setTitleToken = function(token) {
  this.titleToken = token;
  this.setTitle(Labels.get(this.titleToken));
}

/**
 * Intercept all calls to the FormLayout setContainer() method. The interceptor will set the default labelWidth of any
 * container that uses FormLayout to 175px;
 */
Ext.layout.FormLayout.prototype.setContainer = Ext.layout.FormLayout.prototype.setContainer.createInterceptor(function (ct) {
  ct.labelWidth = ct.labelWidth || 175;
  return true;
});

/**
 * ColumnLayout ignores margins (which are not ignored by BoxLayout).  The new layout manager ColumnWithMarginsLayout
 *  overrides onLayout() of ColumnLayout and updates margins after fields have been rendered. This is used by RowEditor plugin
 */
Ext.layout.ColumnWithMarginsLayout = Ext.extend(Ext.layout.ColumnLayout, {
    onLayout : function(ct, target) {
        Ext.layout.ColumnWithMarginsLayout.superclass.onLayout.apply(this, arguments);
        var me = this;
        ct.items.each(function() {
            if (this.margins) {
                var box = this.getEl().findParent('.' + me.extraCls);
                if (box) {
                    box = Ext.get(box);
                    box.setStyle({
                        'margin-left': this.margins.left,
                        'margin-top': this.margins.top,
                        'margin-right': this.margins.right,
                        'margin-bottom': this.margins.bottom
                    });
                }
            }
        });
    }
});

Ext.Container.LAYOUTS['columnwithmargins'] = Ext.layout.ColumnWithMarginsLayout;

/**
 * Add Extra Feature to the tool bar TextItem so we can get the text content of the button
 */
Ext.Toolbar.TextItem.prototype.getText = function() {
  if(this.rendered)
    return this.el.dom.innerHtml;
  else
    return this.text;
};

/**
 * @class Date
 * added format code 'J' for Julian date support
 * to use this code you would specify a date format like the following: 'yJ', 
 * 'yJ' would format to a 2 digit year concatenated with a 3 digit day of year, example: '01/05/2002' --> '02005'
 *
 *   JULIAN DATE ENTRY HAS THE FOLLOWING BEHAVIOR
 *
 *  If 5 characters are entered, the first 2 characters are the year and last 3 are less than 366
 *  If date is 0 then it is invalid
 *  If date is 1-99, then add 0 and assume the current year
 *  If date is 100-366 then assume the current year
 *  If date is > 366 it is invalid
 *  If the last 3 characters are 366 (leap year) and the year is not a leap year then roll the date over to next year.  EX. 11366 ==> 12001
 */
 
Date.formatCodes.J = "String.leftPad(this.getDayOfYear()+1, 3, '0')";

Date.julianToDateObject = function(jDate){
  jDate = String.leftPad(jDate, 5, '0');
  if(Number(jDate) == 0){
    return new Date();
  }
  var year = String(jDate).substring(0,2); //first 2 letters of julian date
  var day = String(jDate).substring(2,5); //last 3 letters of julian date
  year = (year < Date.y2kYear) ? Number(year) + 2000 : Number(year) + 1900;
  var dateObj = new Date(year, 0);
  return new Date(dateObj.setDate(day));
};

Date.parseFunctions['yJ H'] = function(input, strict){
  if(Ext.isDate(input)){
    return input;
  }
  var yrDay = input.split(' ')[0];
  if(yrDay.length <= 3){
    var currYr = new Date().getFullYear().toString().slice(2);
    yrDay = String.leftPad(yrDay, 3, '0');
    if(isNaN(yrDay) || yrDay > 366 || yrDay === '000'){
      yrDay = 'invalid';
    } else {
      yrDay = String(currYr) + String(yrDay);
    }
  }
  var isNotJulian = yrDay.length != 5 || /[^0-9]/.test(yrDay); //if any non numeric (except whitespace) chars assume the input is not in julian format
  if(!isNotJulian){
    if(yrDay.substring(2, yrDay.length) > 366){  //date rollover is invalid for julian dates
      return false;
    }
  }
  if(input && isNotJulian){
    return false;
  } else {
    return Date.julianToDateObject(yrDay);
  }
};

Date.parseFunctions['yJ'] = function(input, strict){
  if(Ext.isDate(input)){
    return input;
  }
  var yrDay = input.split(' ')[0];
  if(yrDay.length <= 3){
    var currYr = new Date().getFullYear().toString().slice(2);
    yrDay = String.leftPad(yrDay, 3, '0');
    if(isNaN(yrDay) || yrDay > 366 || yrDay === '000'){
      yrDay = 'invalid';
    } else {
      yrDay = String(currYr) + String(yrDay);
    }
  }
  var isNotJulian = yrDay.length != 5 || /[^0-9]/.test(yrDay); //if any non numeric (except whitespace) chars assume the input is not in julian format
  if(!isNotJulian){
    if(yrDay.substring(2, yrDay.length) > 366){  //date rollover is invalid for julian dates
      return false;
    }
  }
  if(input && isNotJulian){
    return false;
  } else {
    return Date.julianToDateObject(yrDay);
  }
};

Ext.override(Ext.util.MixedCollection, {
  sum : function(property, start, end){
        var rs = this.items, v = 0;
        start = start || 0;
        end = (end || end === 0) ? end : rs.length-1;

        for(var i = start; i <= end; i++){
            v += (rs[i].data[property] || 0);
        }
        return v;
    }
});

Ext.override(Ext.form.CheckboxGroup, {
  /**
   * return the checkbox or radio button in the group that is matched to the inputValue from input
   * @param {Object} inputValue
   */
  getMember: function(inputValue) {
    var oo;
    if (this.rendered) {
      this.items.each(function(itm) {
        if (itm.inputValue == inputValue) {
          oo = itm;
          return false;
        }
      });
    } else if (Ext.isArray(this.items)) {
      for (var i=0; i<this.items.length; i++) {
        if (this.items[i].inputValue==inputValue) {
          oo = this.items[i];
          break;
        }
      }
    }
    return oo;
  }
});
Ext.form.VTypes = Ext.apply(Ext.form.VTypes, {
  emaillist: function(v) {
    if (v) {
      var k = v.split(';');
      for (var i=0; i<k.length; i++) {
        if (Ext.form.VTypes.email(k[i].trim())) continue;
        return false;
      }
      return true;
    }
    return false;
  },
  emaillistText: 'This field should be e-mail addresses separated in semi-colons. The e-mail addresses should be in the format of "xyz@company.com"'
});

//Overriden to support disabling spellcheck in FF
Ext.override(Ext.form.TextArea, {
  spellcheck: true,
  onRender : function(ct, position){ 
    if(!this.el){
      this.defaultAutoCreate = {
        tag: "textarea",
        style:"width:100px;height:60px;",
        autocomplete: "off"
      };
    }
    Ext.form.TextArea.superclass.onRender.call(this, ct, position);
    if(this.grow){
      this.textSizeEl = Ext.DomHelper.append(document.body, {
          tag: "pre", cls: "x-form-grow-sizer"
      });
      if(this.preventScrollbars){
          this.el.setStyle("overflow", "hidden");
      }
      this.el.setHeight(this.growMin);
    }
    if(!this.spellcheck){
      this.el.dom.spellcheck = false; //disables spellcheck
    }
  }
});

//Override time field to parse number values to valid time values.
Ext.override(Ext.form.TimeField, {
    beforeBlur: function() {
      var v = this.getRawValue();
      if (v && v.indexOf(':')<0 && Ext.isNumber(parseInt(v)) && parseInt(v)<1000000){
        var oldInt = parseInt(v);
        var newValue;
        if (typeof v == 'string' && v.length==4) {
          newValue = v.substr(0,2) + ':' + v.substr(2,2);
        } else if (typeof v == 'string' && v.length==6) {
          newValue = v.substr(0,2) + ':' + v.substr(2,2) + ':' + v.substr(4,2);
        } else if (oldInt<10){
          newValue = '0' + v + ':00:00';
        }else if (oldInt<100){
          newValue = v + ':00:00';
        }else if (oldInt<1000){
          newValue = '0' + v.substring(0,1) + ':' + v.substring(1) + ':00';
        }else if (oldInt<10000){
          newValue = v.substring(0,2) + ':' + v.substring(2) + ':00';
        }else if (oldInt<100000){
          newValue = '0' + v.substring(0,1) + ':' + v.substring(1,3) + ':' + v.substring(3,5);
        }else{
          newValue = v.substring(0,2) + ':' + v.substring(2,4) + ':' + v.substring(4,6);
        }
        this.setValue(newValue);
      }
      Ext.form.TimeField.superclass.beforeBlur.call(this);
    }
});