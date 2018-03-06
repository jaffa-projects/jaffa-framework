/** Current implementation uses the function String(s) which when s=null returns
 * 'null' as the value, and hence null strings get sorted in the middle of 'm' and 'o'!
 * These modified functions convert null strings to '' empty strings so they get
 * sorted to the top
 */
Ext.data.SortTypes.asText = function (s) {
    return String(s == null ? '' : s).replace(this.stripTagsRE, "");
};
Ext.data.SortTypes.asUCText = function (s) {
    return Ext.data.SortTypes.asText(Ext.data.SortTypes.asUCString(s));
};
Ext.data.SortTypes.asUCString = function (s) {
    return String(s == null ? '' : s).toUpperCase();
};

/** Override createAccessor which is used to generate extraction functions.
 * If it creates an extraction function that has nested values (ie extract part.description)
 * in its original state, if part is null, an NullPointerException will be thrown when the
 * function is used. In this new version this is caught and 'null' is returned.
 *
 * NOTE: This creates a function on the fly, that has a local variable. This function executes
 * and returns the real function to be used in the prototype. The real function now references
 * that original local variable, which there will only be one copy of. This could be avoided if
 * the 're' variable was made "global'. It is a clever use of a 'closure' to
 * scope the variable and prevent it being initialized each time this function is invoked
 *
 */

Ext.override(Ext.data.JsonReader, {
    buildExtractors: function () { //overrode buildExtractors to handle the createAccessor for getId to support multi-key records
        if (this.ef) {
            return;
        }
        var s = this.meta, Record = this.recordType,
            f = Record.prototype.fields, fi = f.items, fl = f.length;

        if (s.totalProperty) {
            this.getTotal = this.createAccessor(s.totalProperty);
        }
        if (s.successProperty) {
            this.getSuccess = this.createAccessor(s.successProperty);
        }
        if (s.messageProperty) {
            this.getMessage = this.createAccessor(s.messageProperty);
        }
        this.getRoot = s.root ? this.createAccessor(s.root) : function (p) {
            return p;
        };

        s.idProperty = this.determineIdProperty(s, Record);

        if (s.idProperty) {
            var g = this.createAccessor(s.idProperty); //single key
            if (Ext.isArray(s.idProperty)) { //composite-key
                g = function (recJson) {
                    if (recJson) {
                        var keyVal = [];
                        for (var i = 0; i < s.idProperty.length; i++) {
                            keyVal.push(recJson[s.idProperty[i]]);
                        }
                        if (keyVal.length > 0) {
                            return keyVal;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                };
            }
            this.getId = function (rec) {
                var r = g(rec);
                return (r === undefined || r === '') ? null : r;
            };
        } else {
            this.getId = function () {
                return null;
            };
        }
        var ef = [];
        for (var i = 0; i < fl; i++) {
            f = fi[i];
            var map = (f.mapping !== undefined && f.mapping !== null) ? f.mapping : f.name;
            ef.push(this.createAccessor(map));
        }
        this.ef = ef;
    },

    determineIdProperty: function (meta, recordType) { //Order of precedence: meta.id || meta.idFields || meta.idProperty || metaClass.key ||recordType.metaClass, 'id'
        var idProperty = null;
        if (meta) {
            idProperty = meta.id;
            if (meta.idFields && meta.idFields.length && meta.idFields.length > 0) {
                meta.idProperty = meta.idFields;
            } else if (meta.idProperty && meta.idProperty !== 'id') { //Note: The JsonReader's constructor set the idProperty to 'id' if not defined, we want to override this
                idProperty = meta.idProperty;
            }
            if (!idProperty && meta.metaClass) {
                if (typeof meta.metaClass === 'object' && meta.metaClass.key) {
                    idProperty = meta.metaClass.key;
                } else if (typeof meta.metaClass === 'string' && ClassMetaData && ClassMetaData[meta.metaClass] && ClassMetaData[meta.metaClass].key) {
                    idProperty = ClassMetaData[meta.metaClass].key;
                }
            }
        }
        if (!idProperty && recordType) {
            if (recordType.defaultMetaClass && ClassMetaData[recordType.defaultMetaClass] && ClassMetaData[recordType.defaultMetaClass].key) {
                idProperty = ClassMetaData[recordType.defaultMetaClass].key;
            }
        }
        return idProperty || 'id';
    },
    createAccessor: function () {
        var re = /[\[\.]/;
        return function (expr) {
            try {
                if (Ext.isEmpty(expr)) {
                    return Ext.emptyFn;
                } else if (Ext.isFunction(expr)) {
                    return expr;
                } else if (Ext.isArray(expr)) {
                    return function (obj) {
                        var fs = expr;
                        if (!obj) return null;
                        var o = obj[fs[0]];
                        o = o ? o : '';
                        for (var i = 1; i < fs.length; i++) {
                            if (re.test(fs[i])) {
                                try {
                                    o += '|' + eval('obj.' + fs[i]);
                                } catch (e) {
                                    o += '|';
                                }
                            } else
                                o += '|' + (obj[fs[i]] != null && obj[fs[i]] != undefined ? obj[fs[i]] : '');
                        }
                        if (o.length == fs.length) o = null;
                        return o;
                    };
                } else {
                    return(re.test(expr)) ?
                        new Function("obj", "try{return obj." + expr + "} catch(e){return null}") :
                        function (obj) {
                            return obj == null ? null : obj[expr];
                        };
                }
            } catch (e) {
                return Ext.emptyFn;
            }
        };
    }(),
    getId: function (obj) {
        return null;
    }
});

/**
 * @class Ext.form.Field
 * @ignore
 *
 * add trimming of spaces begin/end of the input string.
 * this feature can be diabled by setting 'notrim' to true.
 */
Ext.override(Ext.form.Field, {
    beforeBlur: function () {
        if (this.el.dom.value && !this.notrim) {
            this.el.dom.value = this.el.dom.value.replace(/^\s+|\s+$/g, '');
        }
    }
});
Ext.override(Ext.form.TriggerField, {
    beforeBlur: function () {
        Ext.form.TriggerField.superclass.beforeBlur.call(this);
    }
});

// Add in the UpperCase conversion when it is specified in style of textfield.
// To use add following to the field config object:
//    style: 'text-transform: uppercase',
/**
 * @class Ext.form.TextField
 */
Ext.override(Ext.form.TextField, {
    selectOnFocus: true,
    enableKeyEvents: true,
    getValue: function () {
        var v = Ext.form.TextField.superclass.getValue.call(this);
        if (Ext.isString(v)) {
            if (Ext.isString(this.initialConfig.style) && this.initialConfig.style.indexOf('uppercase') >= 0) {
                v = v.toUpperCase();
            } else if (Ext.isObject(this.initialConfig.style) && this.initialConfig.style.textTransform === 'uppercase') {
                v = v.toUpperCase();
            }
        }
        return v;
    }
});

/**
 * @class Ext.form.ComboBox
 */
Ext.override(Ext.form.ComboBox, {
    /* This method sets the 'value' field with a modified dom-value. This will ensure that the 'change' listeners are fired. */
    beforeBlur: function () {
        if (this.valueField) {
            var displayValue = this.el.getValue();
            if (String(displayValue) !== String(this.startValue)) {
                var r = displayValue && this.displayField ? this.findRecord(this.displayField, displayValue) : null;
                if (r != null) {
                    this.value = r.data[this.valueField];
                } else if (this.forceSelection) {
                    if (displayValue.length > 0 && displayValue != this.emptyText) {
                        this.el.dom.value = Ext.value(this.lastSelectionText, '');
                        this.applyEmptyText();
                    } else {
                        this.clearValue();
                    }
                } else {
                    this.value = displayValue;
                }
            }
        }
        Ext.form.ComboBox.superclass.beforeBlur.call(this);
    },

    getValue: function () {
        var v = null;
        if (this.valueField) {
            // Changed default code to handle 'emptyText' correctly
            v = typeof this.value != 'undefined' && this.value != this.emptyText ? this.value : '';

        } else {
            v = Ext.form.ComboBox.superclass.getValue.call(this);
        }
        // do upper case conversion if we're bypassing the base class implementation
        //if (typeof v == 'string' && this.initialConfig.style && this.initialConfig.style.indexOf('uppercase') >= 0) {
        if (typeof v == 'string' && this.valueFieldCase == 'uppercase')
            v = v.toUpperCase();
        return v;
    }

});

// Add padding to fill up the digits after decimal
/**
 * @class Ext.form.NumberField
 */
Ext.override(Ext.form.NumberField, {
    /**
     * @cfg {Boolean/Number} padZeros True to pad zeros upto the precision specified (defaults to false). If the value of this property is a Number, then that value be used for padding, rather than the decimalPrecision.
     */
    padZeros: false,

    maxIntegralLengthText: "The maximum integral length for this field is {0}",

    maxFractionalLengthText: "The maximum fractional length for this field is {0}",

    setValue: function (v) {
        Ext.form.NumberField.superclass.setValue.call(this, String(this.fixPrecision(v)).replace(".", this.decimalSeparator));
    },

    getValue: function () {
        return Jaffa.util.Format.toFullFixed(this.fixPrecision(this.parseValue(Ext.form.NumberField.superclass.getValue.call(this))));
    },

    fixPrecision: function (value) {
        var nan = isNaN(value) || value == null;
        if (!this.allowDecimals || this.decimalPrecision == -1 || nan || !value) {
            return nan ? '' : value;
        }
        if (this.padZeros === undefined || this.padZeros === null || this.padZeros === false)
            return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
        else if (this.padZeros === true)
            return parseFloat(value).toFixed(this.decimalPrecision);
        else
            return parseFloat(value).toFixed(this.padZeros);
    },

    validateValue: function (value) {
        // perform the integralLength and decimalPrecision validations
        if (value.length > 0) {
            if (value.indexOf('e') >= 0) value = Jaffa.util.Format.toFullFixed(value);
            var i = value.indexOf(this.decimalSeparator);
            var integralValue = i > -1 ? value.substr(0, i) : value;
            var fractionalValue = i > -1 && value.length > (i + 1) ? value.substr(i + 1) : '';

            // Remove the +/- sign from the integralValue
            if (integralValue.length > 0 && (integralValue.charAt(0) === '+' || integralValue.charAt(0) === '-'))
                integralValue = integralValue.length > 1 ? integralValue.substr(1) : '';

            if (this.maxIntegralLength !== undefined && this.maxIntegralLength !== null && integralValue.length > this.maxIntegralLength) {
                this.markInvalid(String.format(this.maxIntegralLengthText, this.maxIntegralLength));
                return false;
            }

            maxFractionalLength = !this.allowDecimals || !this.decimalPrecision ? 0 : this.decimalPrecision;
            if (fractionalValue.length > maxFractionalLength) {
                this.markInvalid(String.format(this.maxFractionalLengthText, maxFractionalLength));
                return false;
            }
        }

        // perform the default validations
        return Ext.form.NumberField.superclass.validateValue.apply(this, arguments);
    }
});

// Begin of Edit-grid tab-out no triggerBlur bug fix (onSpecialKey)
// Added functionality to no validate when first entering the editor (startEdit)
// Also, added the ability to disable hiding the editor when finished editing and the new value is not valid (completeEdit)
Ext.override(Ext.Editor, {
    deferInitialValidation: true,
    disableHideWhenInvalid: true,
    onSpecialKey: function (field, e) {
        var key = e.getKey();
        if (this.completeOnEnter && key == e.ENTER) {
            e.stopEvent();
            this.completeEdit();
        } else if (this.cancelOnEsc && key == e.ESC) {
            this.cancelEdit();
        } else if (this.field.triggerBlur && (key == e.ENTER || key == e.ESC || key == e.TAB)) {
            this.field.un('blur', this.onBlur, this);
            this.field.triggerBlur();
            this.field.on('blur', this.onBlur, this);
            this.fireEvent('specialkey', field, e);
        } else {
            this.fireEvent('specialkey', field, e);
        }
    },
    startEdit: function (el, value) {
        var a = this;
        if (this.editing) {
            this.completeEdit();
        }
        this.boundEl = Ext.get(el);
        var v = value !== undefined ? value : this.boundEl.dom.innerHTML;
        if (!this.rendered) {
            this.render(this.parentEl || document.body);
        }
        if (this.fireEvent("beforestartedit", this, this.boundEl, v) !== false) {
            this.startValue = v;
            this.field.reset();
            if (this.deferInitialValidation && this.field.initialized) {
                this.field.setValue(v);
            } else if (this.field instanceof Ext.form.DateField) {
                this.field.setValue(v);
            } else {
                this.field.setRawValue(v);
            }
            this.field.initialized = true;
            this.realign(true);
            this.editing = true;
            this.show();
        }
    },
    completeEdit: function (remainVisible, doNotAssert) {
        if (!this.editing) {
            return;
        }
        if (this.disableHideWhenInvalid && this.field.isValid && !this.field.isValid()) {
            return;
        }
        // Assert combo values first
        if (this.field.assertValue && !doNotAssert) {
            this.field.assertValue();
        }

        var v = this.getValue();
        if (!this.field.isValid()) {
            if (this.revertInvalid !== false) {
                this.cancelEdit(remainVisible);
            }
            return;
        }
        if (String(v) === String(this.startValue) && this.ignoreNoChange) {
            this.hideEdit(remainVisible);
            return;
        }
        if (this.fireEvent("beforecomplete", this, v, this.startValue) !== false) {
            v = this.getValue();
            if (this.updateEl && this.boundEl) {
                this.boundEl.update(v);
            }
            this.hideEdit(remainVisible);
            this.fireEvent("complete", this, v, this.startValue);
        }
    }
});
// End of Edit-grid bug fix

// Override default template to add support for the {mandatoryFlag} argument,
// Also removed the "for" attribute because of a focus issue in IE when there is no boxLabel
Ext.override(Ext.layout.ContainerLayout, {
    fieldTpl: (function () {
        var t = new Ext.Template(
            '<div class="x-form-item {itemCls}" tabIndex="-1">',
            '<label style="{labelStyle}" class="x-form-item-label">{label}{mandatoryFlag}{labelSeparator}</label>',
            '<div class="x-form-element" id="x-form-el-{id}" style="{elementStyle}">',
            '</div><div class="{clearCls}"></div>',
            '</div>'
        );
        t.disableFormats = true;
        return t.compile();
    })()
});

// Overload default arguments to include the 'mandatoryFlag' argument
Ext.override(Ext.layout.FormLayout, {
    getTemplateArgs: function (field) {
        var noLabelSep = !field.fieldLabel || field.hideLabel;

        return {
            id: field.id,
            label: field.fieldLabel,
            itemCls: (field.itemCls || this.container.itemCls || '') + (field.hideLabel ? ' x-hide-label' : ''),
            clearCls: field.clearCls || 'x-form-clear-left',
            labelStyle: this.getLabelStyle(field.labelStyle),
            elementStyle: this.elementStyle || '',
            labelSeparator: noLabelSep ? '' : (Ext.isDefined(field.labelSeparator) ? field.labelSeparator : this.labelSeparator),
            mandatoryFlag: field.allowBlank == false && !field.textOnly ? "<span class='x-mand-flag'>*</span>" : ''
        };
    }
});

// ExtJs Bug http://extjs.com/forum/showthread.php?t=31452
Ext.form.ComboBox.override({
    restrictHeight: function () {
        this.innerList.dom.style.height = '';
        var inner = this.innerList.dom;
        var pad = this.list.getFrameWidth('tb') + (this.resizable ? this.handleHeight : 0) + this.assetHeight;
        var h = Math.max(inner.clientHeight, inner.offsetHeight, inner.scrollHeight);
        var ha = this.getPosition()[1] - Ext.getBody().getScroll().top;
        var hb = Ext.lib.Dom.getViewHeight() - ha - this.getSize().height;
        var space = Math.max(ha, hb, 0) - this.list.shadowOffset - pad - 5;
        h = Math.min(h, space);
        h = h.constrain(this.minListHeight, this.maxHeight);
        this.innerList.setHeight(h);
        this.list.beginUpdate();
        this.list.setHeight(h + pad);
        this.list.alignTo(this.wrap, this.listAlign);
        this.list.endUpdate();
    },
// End of ExtJs Bug http://extjs.com/forum/showthread.php?t=31452

// Bug from forum fixed... http://www.sencha.com/forum/showthread.php?75751-OPEN-42-ComboBox-s-setValue-call-with-a-remotely-loaded-Store&p=385027&viewfull=1#post385027
    setValue: function (v, displayText) {
        var text = displayText || v; // Having displayText means we already have the record. No need to load the data from the server.
        if (this.valueField && !displayText) {
            if (this.mode == 'remote' && !Ext.isDefined(this.store.totalLength)) {
                this.store.on('load', this.setValue.createDelegate(this, arguments), null, {single: true});
                if (this.store.lastOptions === null) {
                    var params = {};
                    var primarySearchField = this.primarySearchField || ((this.meta && this.meta.finder && this.meta.finder.primarySearchField) ? this.meta.finder.primarySearchField : false) || this.valueField;
                    if (primarySearchField && v) {
                        params[primarySearchField] = this.getSearchFieldCriteria(v);
                        if (this.staticBaseParams) {
                            Ext.applyIf(params, this.staticBaseParams);
                        }
                        if (Ext.isFunction(this.applyCriteriaFields)) {
                            this.applyCriteriaFields(params);
                        }
                    } else {
                        params[this.queryParam] = this.allQuery;
                    }
                    this.store.load({params: params});
                }
                return;
            }
            text = this.findValueRecord(text, v);
        }
        this.lastSelectionText = text;
        if (this.hiddenField) {
            this.hiddenField.value = v;
        }
        if (this.textOnly) {
            Ext.form.ComboBox.superclass.setValue.call(this, text);
            this.value = v;
        } else {
            this.value = v;
            if (this.rendered) {
                Ext.form.ComboBox.superclass.setRawValue.call(this, text);
                // validate() called in superclass.setValue() validates on the display value
                // this validate() is to validate on the inherent value
                this.validate();
            }
        }
    },

    getSearchFieldCriteria: function (v) {
        return {operator: 'Equals', values: [v]};
    },

    findValueRecord: function (text, v) {
        var r = this.findRecord(this.valueField, v);
        if (r) {
            text = r.data[this.displayField];
        } else if (this.valueNotFoundText !== undefined) {
            text = this.valueNotFoundText;
        }
        return text;
    },

// End of ExtJS Bug ... http://www.sencha.com/forum/showthread.php?75751-OPEN-42-ComboBox-s-setValue-call-with-a-remotely-loaded-Store&p=385027&viewfull=1#post385027

    //Override to use '==='. This allows the use of true and false values in the store.
    findRecord: function (prop, value, record) {
        var records = [], rec;
        if (this.store.getCount() > 0) {
            this.store.each(function (r) {
                if (r.data[prop] === value) {
                    if (this.useKeyToFilterRecord !== true || !record) {
                        rec = r;
                        return false;
                    } else {
                        records.push(r);
                    }
                }

            }, this);
        }
        //Used key field to find the matching record. This method is called when useKeyToFilterRecord is set to true.
        var filterRecByKeyField = function () {
            var key = this.meta && this.meta.key;
            if (key && record) {
                for (var i = 0; i < records.length; i++) {
                    if (Ext.isArray(key)) {
                        var count = 0;
                        for (var j = 0; j < key.length; j++) {
                            if (records[i].get(key[j]) && records[i].get(key[j]) === record.get(key[j])) {
                                count++;
                            }
                        }
                        if (count === key.length) {
                            rec = records[i];
                            break;
                        }
                    } else {
                        if (records[i].get(key) && records[i].get(key) === record.get(key)) {
                            rec = records[i];
                            break;
                        }
                    }
                }

            }
        };
        if (!Ext.isEmpty(records)) {
            filterRecByKeyField.call(this);
        }
        return rec;
    }

});

Ext.apply(Ext.form.ComboBox.prototype, {
    listWidth: 250,
    width: 250
});

Ext.override(Ext.Component, {
    onDestroy: function () {
        this.destroyed = true;
    },
    lookupMetaClass: function () {
        if (!this.metaClass) {
            // Check the plugins property, since the metaClass may have been defined in a plugin
            if (this.plugins) {
                if (Ext.isArray(this.plugins)) {
                    for (var i = 0; i < this.plugins.length; i++) {
                        // the following combines an assignment and a comparison in a single statement
                        if (this.metaClass == this.plugins[i].metaClass) {
                            break;
                        }
                    }
                } else
                    this.metaClass = this.plugins.metaClass;
            }

            // If not found, recursively check the parent container for the metaClass
            if (!this.metaClass && this.ownerCt && this.ownerCt.lookupMetaClass && typeof this.ownerCt.lookupMetaClass === 'function')
                this.metaClass = this.ownerCt.lookupMetaClass();
        }
        return this.metaClass;
    },
    lookupController: function () {
        if (!this.controller && this.ownerCt && this.ownerCt.lookupController && typeof this.ownerCt.lookupController === 'function')
            this.controller = this.ownerCt.lookupController();
        return this.controller;
    }
});

// Maintain a reference to the original createComponent() method.
if (!Ext.Container.prototype._jaffa_orig_createComponent)
    Ext.Container.prototype._jaffa_orig_createComponent = Ext.Container.prototype.createComponent;
Ext.override(Ext.Container, {
    /**
     * Override the createComponent method of the Container class,
     * determing the xtype by looking up the metadata.
     */
    createComponent: function (config, defaultType) {
        return this._jaffa_orig_createComponent(config, this.determineXtype(config, defaultType));
    },

    /**
     * Returns the xtype from the config.
     * If config does not contain an xtype, the xtype is determined by looking up the metadata.
     * If metadata is not available, then the defaultType will be returned.
     */
    determineXtype: function (config, defaultType) {
        if (config.xtype == 'finderCriteriaFieldPanel') {
            // allow comb or lovcomb to be set from aop rules.
            var cfg = Ext.apply({isValueField: true, isCriteriaField: true}, config);
            delete cfg.xtype;
            var xtype = this.determineXtypeFromMetadata(cfg);
            if (xtype == 'combo' || xtype == 'lovcombo') {
                Ext.apply(config, cfg);
            }
        }
        return config.xtype || this.determineXtypeFromMetadata(config) || defaultType || this.defaultType;
    },

    /**
     * Determines the xtype by looking up the metadata.
     */
    determineXtypeFromMetadata: function (config) {
        var meta = this.determineMetadata(config);
        if (meta) {
            var xtype;
            if (meta.foreignKeyInfo && meta.foreignKeyInfo.component) {
                if (!config.foreignKeyInfo)
                    config.foreignKeyInfo = {};
                Ext.applyIf(config.foreignKeyInfo, meta.foreignKeyInfo);
                xtype = 'finderComboGrid';
            } else if (meta.inList) {
                Ext.applyIf(config, {
                    store: meta.inList,
                    minChars: 0,
                    triggerAction: 'all',
                    maxHeight: 150,
                    resizable: true
                });
                if (config.isCriteriaField) {
                    xtype = 'lovcombo';
                    config.displayField = 'field2';
                } else {
                    xtype = 'combo';
                }
            } else {
                switch (meta.type) {
                    case 'string':
                        //@todo: if comment rule is defined, return comment xtype, that takes into account the mime-type
                        xtype = 'textfield';
                        break;
                    case 'number':
                        xtype = 'numberfield';
                        break;
                    case 'dateonly':
                    case 'monthyear':
                        xtype = 'datefield';
                        break;
                    case 'datetime':
                        xtype = 'xdatetime';
                        break;
                    case 'boolean':
                        xtype = 'checkbox';
                        break;
                    case 'currency':
                        xtype = 'currencyfield';
                        break;
                    default:
                        xtype = 'textfield';
                }
            }
            config.xtype = xtype;
            return xtype;
        }
    },

    /**
     * Determines the metadata, based on the metaClass and metaField/mapping.
     */
    determineMetadata: function (config) {
        // determine the metaClass
        var metaClass = config.metaClass || this.lookupMetaClass();
        if (metaClass) {
            if (typeof metaClass === 'string')
                metaClass = ClassMetaData[metaClass];
            if (metaClass) {
                // determine the metaField
                var metaField = config.metaField ? config.metaField : config.mapping;
                if (metaField)
                    return metaClass.fields[metaField] || metaClass.fields[metaField.split('.').pop()];
            }
        }
    }
});

// Begin a forum fix of "permission denied ... a non-chrome context" firebug error message
Ext.override(Ext.Element, {
    contains: function () {
        var isXUL = Ext.isGecko ? function (node) {
            return Object.prototype.toString.call(node) == '[object XULElement]';
        } : Ext.emptyFn;

        return function (el) {
            return !this.dom.firstChild || // if this Element has no children, return false immediately
                !el ||
                isXUL(el) ? false : Ext.lib.Dom.isAncestor(this.dom, el.dom ? el.dom : el);
        };
    }()
});
// End of forum fix of "permission .." from http://extjs.com/forum/showthread.php?p=364724#post364724

// ExtJs bug fix: should be removed after upgrade to ExtJs 3.1
Ext.override(Ext.form.DateField, {
    width: 94,
    menuListeners: {
        select: function (m, d) {
            this.setValue(d);
            this.fireEvent('select', this, d);
        },
        show: function () { // retain focus styling
            this.onFocus();
        },
        hide: function () {
            this.focus.defer(10, this);
            var ml = this.menuListeners;
            this.menu.un("select", ml.select, this);
            this.menu.un("show", ml.show, this);
            this.menu.un("hide", ml.hide, this);
        }
    }
});

//Add change event to radiogroup
Ext.override(Ext.form.RadioGroup, {
    afterRender: function () {
        var group = this;
        this.items.each(function (field) {
            // Listen for 'change' event on each child item
            field.on("change", function (self, checked) {
                // if checkbox is checked, then fire 'change' event on RadioGroup container
                if (checked)
                // Note, oldValue (third parameter in 'change' event listener) is not passed,
                // because is not easy to get it
                    group.fireEvent('change', group, self.getRawValue());
            });
        });
        Ext.form.RadioGroup.superclass.afterRender.call(this);
    },
    getRadioByValue: function (v) {
        var o;
        this.items.each(function (itm) {
            if (itm.inputValue == v) {
                o = itm;
                return false;
            }
        });
        return o;
    }
});

/**
 * Ext.grid.ColumnModel.setConfig() instantiates Ext.grid.Column for each column passed in the config.
 * The following interceptor to the setConfig() method ensures that an array of Strings gets molded
 * into an array of Objects, before invoking the original setConfig() method.
 */
Ext.grid.ColumnModel.prototype.setConfig = Ext.grid.ColumnModel.prototype.setConfig.createInterceptor(function (config, initial) {
    if (config && Ext.isArray(config)) {
        for (var i = 0; i < config.length; i++) {
            if (typeof config[i] === 'string')
                config[i] = {dataIndex: config[i]};
        }
    }
});

if (!Ext.grid.GridPanel.prototype._jaffa_orig_applyState)
    Ext.grid.GridPanel.prototype._jaffa_orig_applyState = Ext.grid.GridPanel.prototype.applyState;
Ext.override(Ext.grid.GridPanel, {
    applyState: function (state) {
        // a local function to clear the State
        var clearState = function (panel, state) {
            // do not clear if the State was inherited from the defaults
            if (!state._inherited) {
                console.debug('Clearing the State');
                Ext.state.Manager.clear(panel.getStateId());
            } else
                console.debug('State will not be cleared since it was inherited from the defaults');
        };

        // Try to repair state if it is not compatible with the ColumnModel
        if (state && state.columns && state.columns.length > 0) {

            // - Match the id and dataIndex(if available) between the columns in the ColumnModel and the State
            for (var i = 0, len = state.columns.length; i < len; i++) {
                var s = state.columns[i];
                var c = this.colModel.getColumnById(s.id);
                if (!c || s.dataIndex && s.dataIndex !== c.dataIndex) {
                    //If column model doesn't match state columns fix id on the state
                    if (s.dataIndex && this.colModel.findColumnIndex(s.dataIndex) && this.colModel.findColumnIndex(s.dataIndex) >= 0 && this.colModel.getColumnId(this.colModel.findColumnIndex(s.dataIndex)))
                        s.id = this.colModel.getColumnId(this.colModel.findColumnIndex(s.dataIndex));
                    else
                        s.id = this.colModel.getColumnCount() + 1;
                } else {
                    // Prior to ExtJS-3.x, the width was not being stored in the State when a column was moved.
                    // Use the ColumnModel to determine the missing width, so that the data is correctly rendered.
                    if (!s.width && c.width)
                        s.width = c.width;
                }
            }
        }

        // The applyState() method of the GridPanel applies all the state properties onto a grid,
        // which messes up the filters object of the grid. Hence temporarily, remove the filters property from the state.
        var filters = state.filters;
        delete state.filters;

        // The applyState() method of the GridPanel now supports the 'group' property.
        // Pass the 'groupFields' property as the 'group'.
        if (!state.group && state.groupFields) {
            state.group = state.groupFields;
            delete state.groupFields;
        }

        var cm = this.colModel,
            cs = state.columns,
            s,
            c,
            oldIndex;

        // Add support to hide/unhide columns when applying state. This enables apply state to be called after panel is rendered.
        if (cs) {
            for (var i = 0, len = cs.length; i < len; i++) {
                s = cs[i];
                c = cm.getColumnById(s.id);
                if (c) {
                    if (c.hidden && !s.hidden) {
                        cm.setHidden(cm.getIndexById(s.id), false);
                    } else if (s.hidden) {
                        cm.setHidden(cm.getIndexById(s.id), true);
                    }
                }
            }
        }

        // Invoke the original applyState() method
        //this._jaffa_orig_applyState(state);
        //The original applyState was changed to allow for multiSort.
        var store = this.store;
        if (cs) {
            for (var i = 0, len = cs.length; i < len; i++) {
                s = cs[i];
                c = cm.getColumnById(s.id);
                if (c) {
                    c.hidden = s.hidden;
                    c.width = s.width;
                    oldIndex = cm.getIndexById(s.id);
                    if (oldIndex != i) {
                        cm.moveColumn(oldIndex, i);
                    }
                }
            }
        }
        if (store) {
            s = state.sort;
            if (s) {
                if (this.multiSortInfo && this.multiSortInfo.sorters && this.multiSortInfo.sorters.length > 0)
                    store['sort'](s.sorters);
                else
                    store[store.remoteSort ? 'setDefaultSort' : 'sort'](s.field, s.direction);
            }
            s = state.group;
            if (store.groupBy) {
                if (s) {
                    store.groupBy(s);
                } else {
                    store.clearGrouping();
                }
            }
        }
        var o = Ext.apply({}, state);
        delete o.columns;
        delete o.sort;
        Ext.grid.GridPanel.superclass.applyState.call(this, o);
        // Reinstate the filters property
        if (filters)
            state.filters = filters;
    }
});

/**
 * The following override to the GridPanel.getState() function, adds the 'dataIndex' to the state of each column.
 */
if (!Ext.grid.GridPanel.prototype._jaffa_orig_getState)
    Ext.grid.GridPanel.prototype._jaffa_orig_getState = Ext.grid.GridPanel.prototype.getState;
Ext.override(Ext.grid.GridPanel, {
    getState: function () {
        var o = this._jaffa_orig_getState();
        if (o.columns && o.columns.length > 0) {
            for (var i = 0, c; (c = this.colModel.config[i]); i++)
                o.columns[i].dataIndex = c.dataIndex;
        }
        return o;
    }
});

/*
 * Creates a listener on a panel, that is initially collapsed, that will layout child elements when expanding.
 * This fixes the issue in extjs-3.1, which doesn't correctly layout the collapsed elements.
 */
Ext.override(Ext.Panel, {
    doLayout: function () {
        var a = arguments;
        if (this.collapsed) {
            this.on('expand', function () {
                this.doLayout.apply(this, a);
            }, this, {single: true});
            return;
        }
        Ext.Panel.superclass.doLayout.apply(this, a);
    }
});

/**
 * Add a 'render' listener to all Viewport instances.
 * The listener will remove the page-load-mask, which was added by loadExtJS.jsp
 */
Ext.Viewport.prototype.initComponent = Ext.Viewport.prototype.initComponent.createSequence(function (config) {
    this.on('render', function (viewport) {
        Jaffa.state.PageLoader.unmask();
    });
});

/**
 * Intercept all calls to the mask() method. The interceptor will remove the page-load-mask, which was added by loadExtJS.jsp.
 * This will ensure that multiple masks are not applied simultaneously.
 */
Ext.Element.prototype.mask = Ext.Element.prototype.mask.createInterceptor(function (msg, msgCls) {
    Jaffa.state.PageLoader.unmask();
});

// This override tricks the browser into not showing the following message: 'the webpage you are viewing is trying to close the tab' in IE7+
origWindowCloseFn = window.close;
window.close = function () {
    window.open("", "_self");
    origWindowCloseFn();
};

// override added to cancel startEditing when an editor is already open and the open editor is invalid.
Ext.override(Ext.grid.EditorGridPanel, {
    clicksToEdit: 1,
    startEditing: function (row, col) {
        if (this.activeEditor && this.activeEditor.editing) {
            var foundInvalid = false;
            if (this.activeEditor.getEl().dom && this.activeEditor.getEl().dom.getElementsByClassName) {
                var errors = this.activeEditor.getEl().dom.getElementsByClassName('x-form-invalid');
                if (errors && errors.length > 0) {
                    foundInvalid = true;
                }
            } else if (this.activeEditor.getEl().dom && this.activeEditor.getEl().dom.getElementsByTagName) {
                var elements = this.activeEditor.getEl().dom.getElementsByTagName('input');
                for (var i = 0; i < elements.length; i++) {
                    if (elements[i].className && elements[i].className.indexOf('x-form-invalid') >= 0) {
                        foundInvalid = true;
                    }
                }
            }
            if (foundInvalid) {
                this.view.focusCell(row, col);
                return;
            }
        }
        this.stopEditing();
        if (this.editable !== false && this.colModel.isCellEditable(col, row)) {
            this.view.ensureVisible(row, col, true);
            var r = this.store.getAt(row),
                field = this.colModel.getDataIndex(col),
                e = {
                    grid: this,
                    record: r,
                    field: field,
                    value: r.data[field],
                    row: row,
                    column: col,
                    cancel: false
                };
            if (this.fireEvent("beforeedit", e) !== false && !e.cancel) {
                this.editing = true;
                var ed = this.colModel.getCellEditor(col, row);
                if (!ed) {
                    return;
                }
                if (!ed.rendered) {
                    ed.parentEl = this.view.getEditorParent(ed);
                    ed.on({
                        scope: this,
                        render: {
                            fn: function (c) {
                                c.field.focus(false, true);
                            },
                            single: true,
                            scope: this
                        },
                        specialkey: function (field, e) {
                            this.getSelectionModel().onEditorKey(field, e);
                        },
                        complete: this.onEditComplete,
                        canceledit: this.stopEditing.createDelegate(this, [true])
                    });
                }
                Ext.apply(ed, {
                    row: row,
                    col: col,
                    record: r
                });
                this.lastEdit = {
                    row: row,
                    col: col
                };
                ed.field.parentGrid = this; //Override: gives the field a handle on the grid editor, see FinderComboGrid.completeEditing()
                this.activeEditor = ed;
                // Set the selectSameEditor flag if we are reusing the same editor again and
                // need to prevent the editor from firing onBlur on itself.
                ed.selectSameEditor = (this.activeEditor == this.lastActiveEditor);
                var v = this.preEditValue(r, field);
                ed.startEdit(this.view.getCell(row, col).firstChild, Ext.isDefined(v) ? v : '');

                // Clear the selectSameEditor flag
                (function () {
                    delete ed.selectSameEditor;
                }).defer(50);
            }
        }
    }

});

/**
 * Default to HTML encoding all grid cell contents.  Each column can set a renderer if necessary.
 */
Ext.override(Ext.grid.ColumnModel, {
    defaults: {
        renderer: Ext.util.Format.htmlEncode
    }
});

/**
 * overrides to allow a custom css class to add on column. This allowed editable columns be marked
 * differently from non-editable ones. At mean time the color of editable columns changes when row is selected.
 * This override provides 'editable' flag and 'record' to cell template. The template will use editable==true to set the
 * editable css on the cell and/or use values in record to determine setting editable css per row basis.
 *
 * To make this work, the cell template needs to use Ext.XTemplate. See how it is done in ux/plugins/MetaColumn.js
 *
 * Based on overrides from
 *   http://www.sencha.com/forum/showthread.php?92926-You-could-add-a-CSS-class-on-a-grid-column&highlight=background+color+editable+cell
 */
Ext.override(Ext.grid.GridView, {
    editableCss: 'x-grid3-cell-editable',
    // OVERRIDDEN: we added the columnCssClass attribute (this is ExtJS 3.1.0)
    getColumnData: function () {
        // build a map for all the columns
        var cs = [],
            cm = this.cm,
            colCount = cm.getColumnCount();

        for (var i = 0; i < colCount; i++) {
            var name = cm.getDataIndex(i);

            var col = cm.getColumnAt(i);

            cs[i] = {
                name: (!Ext.isDefined(name) ? this.ds.fields.get(i).name : name),
                renderer: cm.getRenderer(i),
                scope: cm.getRendererScope(i),
                id: cm.getColumnId(i),
                style: this.getColumnStyle(i),
                editable: col.editable !== false && (Boolean(col.editor) || col.isEditable === true)
            };
        }

        return cs;
    },
    // OVERRIDDEN: we added the columnCssClass attribute (this is ExtJS 3.1.0)
    doRender: function (columns, records, store, startRow, colCount, stripe) {
        var templates = this.templates,
            cellTemplate = templates.cell,
            rowTemplate = templates.row,
            last = colCount - 1;

        var tstyle = 'width:' + this.getTotalWidth() + ';';

        // buffers
        var rowBuffer = [],
            colBuffer = [],
            rowParams = {tstyle: tstyle},
            meta = {},
            column,
            record;

        //build up each row's HTML
        for (var j = 0, len = records.length; j < len; j++) {
            record = records[j];
            colBuffer = [];

            var rowIndex = j + startRow;

            //build up each column's HTML
            for (var i = 0; i < colCount; i++) {
                column = columns[i];

                meta = {};

                meta.id = column.id;
                meta.css = i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
                meta.attr = meta.cellAttr = '';
                meta.style = column.style;
                meta.value = column.renderer.call(column.scope, record.data[column.name], meta, record, rowIndex, i, store);
                if (column.editable && this.grid.getColumnModel().isCellEditable(i, rowIndex)) {
                    meta.editable = true;
                    meta.record = column.record;
                }

                if (Ext.isEmpty(meta.value)) {
                    meta.value = '&#160;';
                }

                if (this.markDirty && record.dirty && Ext.isDefined(record.modified[column.name])) {
                    meta.css += ' x-grid3-dirty-cell';
                }

                colBuffer[colBuffer.length] = cellTemplate.apply(meta);
            }

            //set up row striping and row dirtiness CSS classes
            var alt = [];

            if (stripe && ((rowIndex + 1) % 2 === 0)) {
                alt[0] = 'x-grid3-row-alt';
            }

            if (record.dirty) {
                alt[1] = ' x-grid3-dirty-row';
            }

            rowParams.cols = colCount;

            if (this.getRowClass) {
                alt[2] = this.getRowClass(record, rowIndex, rowParams, store);
            }

            rowParams.alt = alt.join(' ');
            rowParams.cells = colBuffer.join('');

            rowBuffer[rowBuffer.length] = rowTemplate.apply(rowParams);
        }

        return rowBuffer.join('');
    },
    refreshRow: function (record) {
        var store = this.ds,
            colCount = this.cm.getColumnCount(),
            columns = this.getColumnData(),
            last = colCount - 1,
            cls = ['x-grid3-row'],
            rowParams = {
                tstyle: String.format("width: {0};", this.getTotalWidth())
            },
            colBuffer = [],
            cellTpl = this.templates.cell,
            rowIndex, row, column, meta, css, i;

        if (Ext.isNumber(record)) {
            rowIndex = record;
            record = store.getAt(rowIndex);
        } else {
            rowIndex = store.indexOf(record);
        }
        if (!record || rowIndex < 0) {
            return;
        }
        for (i = 0; i < colCount; i++) {
            column = columns[i];
            if (i == 0) {
                css = 'x-grid3-cell-first';
            } else {
                css = (i == last) ? 'x-grid3-cell-last ' : '';
            }
            meta = {
                id: column.id,
                style: column.style,
                css: css,
                attr: "",
                cellAttr: "",
                editable: column.editable // <-- override added here: this is needed by the template to display editableCss
            };
            meta.value = column.renderer.call(column.scope, record.data[column.name], meta, record, rowIndex, i, store);
            if (Ext.isEmpty(meta.value)) {
                meta.value = '&#160;';
            }
            if (this.markDirty && record.dirty && typeof record.modified[column.name] != 'undefined') {
                meta.css += ' x-grid3-dirty-cell';
            }
            colBuffer[i] = cellTpl.apply(meta);
        }

        row = this.getRow(rowIndex);
        row.className = '';

        if (this.grid.stripeRows && ((rowIndex + 1) % 2 === 0)) {
            cls.push('x-grid3-row-alt');
        }

        if (this.getRowClass) {
            rowParams.cols = colCount;
            cls.push(this.getRowClass(record, rowIndex, rowParams, store));
        }

        this.fly(row).addClass(cls).setStyle(rowParams.tstyle);
        rowParams.cells = colBuffer.join("");
        row.innerHTML = this.templates.rowInner.apply(rowParams);

        this.fireEvent('rowupdated', this, rowIndex, record);
    },
    beforeColMenuShow: function () {
        var colModel = this.cm,
            colCount = colModel.getColumnCount(),
            colMenu = this.colMenu,
            i,
            exportEnabled = false;

        colMenu.removeAll();

        var metaClass;
        if (this.grid.store.reader.recordType) {
            metaClass = ClassMetaData[this.grid.store.reader.recordType.defaultMetaClass];
        } else if (typeof this.grid.metaClass === 'string') {
            metaClass = ClassMetaData[this.grid.metaClass];
        } else if (typeof this.grid.metaClass === 'object') {
            metaClass = this.grid.metaClass;
        }

        //Check if the grid is using an export to excel plugin. If it is we will add icons to the column
        //selection menu to show if the column value will be exported A)as is B)with out custom rendering
        //or c)not exported at all.
        if (this.grid && this.grid.plugins && this.grid.plugins.length > 0) {
            var plugins = this.grid.plugins;
            for (var i = 0; i < plugins.length; i++) {
                if (plugins[i].exportToExcelEnabled) {
                    exportEnabled = true;
                }
            }
        }

        for (i = 0; i < colCount; i++) {
            if (colModel.config[i].hideable !== false) {
                var excelMode = '';
                if (exportEnabled) {
                    var inDatabase = false;
                    // Get field data for the column from the store record
                    var fieldData = this.grid.store.reader.recordType.prototype.fields.get(colModel.config[i].dataIndex);
                    // Get the mapping for the field (default to name if mapping not specified)
                    var mappingValue = fieldData ? (fieldData.mapping ? fieldData.mapping : fieldData.name) : null;
                    // Get meta data for the store readers metaclass
                    var metaData = ClassMetaData[this.grid.store.reader.recordType.defaultMetaClass];

                    // If mapping contains a '.', assume it must be mapped to a domain field
                    // Otherwise check if the mapping field has meta data, if it does we will
                    // assume it is coming from the database
                    if (mappingValue && mappingValue.indexOf('.') >= 0) {
                        inDatabase = true;
                    } else if (metaData && mappingValue && metaData.fields[mappingValue]) {
                        inDatabase = true;
                    }

                    if (inDatabase) {
                        // If column uses custom renderer or has an inList we cannot be sure it will look the same when exported
                        // to excel. Use a different icon to represent this case.
                        if (colModel.config[i].customRenderer || colModel.config[i].inList) {
                            excelMode = '-rend';
                        }
                        else {
                            excelMode = '-yes';
                        }
                    }
                    else {
                        excelMode = '-no';
                    }
                }

                colMenu.add(new Ext.menu.CheckItem({
                    text: colModel.getColumnHeader(i),
                    itemId: 'col-' + colModel.getColumnId(i),
                    checked: !colModel.isHidden(i),
                    disabled: colModel.config[i].hideable === false || this.grid.editing || (colModel.config[i].mandatory && colModel.config[i].editor),
                    hideOnClick: false,
                    itemCls: exportEnabled ? ('x-menu-item x-menu-check-item x-menu-excel' + excelMode) : 'x-menu-item x-menu-check-item'
                }));
            }
        }
    },
    //Custom renderHeaders method to add label editor when required.
    renderHeaders: function () {
        var cm = this.cm,
            ts = this.templates,
            ct = ts.hcell,
            cb = [],
            p = {},
            len = cm.getColumnCount(),
            last = len - 1;

        for (var i = 0; i < len; i++) {
            p.id = cm.getColumnId(i);
            p.value = this.getColumnHeaderValue(cm, i);

            p.style = this.getColumnStyle(i, true);
            p.tooltip = this.getColumnTooltip(i);
            p.css = i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');

            if (cm.config[i].align == 'right') {
                p.istyle = 'padding-right:16px';
            } else {
                delete p.istyle;
            }
            cb[cb.length] = ct.apply(p);
        }
        return ts.header.apply({cells: cb.join(''), tstyle: 'width:' + this.getTotalWidth() + ';'});
    },
    getColumnHeaderValue: function (cm, i) {
        return cm.getColumnHeader(i) || '';
    }
});

/**
 * Override to allow tabbing-out of a cell into another row without selecting the new rows and deselecting the other
 * selectd rows.
 * Adopted from Sencha tech support. and the bug reference
 *
 * http://www.sencha.com/forum/showthread.php?114051-tab-out-de-selects-other-rows-in-CheckboxSelectionModel-when-checkOnly-true&highlight=tab-out
 *
 * @param {Object} field
 * @param {Object} e
 */
Ext.override(Ext.grid.CheckboxSelectionModel, {
    onEditorKey: function (field, e) {
        var k = e.getKey(),
            newCell,
            g = this.grid,
            last = g.lastEdit,
            ed = g.activeEditor,
            shift = e.shiftKey,
            ae, last, r, c;

        if (k == e.TAB) {
            e.stopEvent();
            if (ed) {
                ed.completeEdit();
                if (shift) {
                    newCell = g.walkCells(ed.row, ed.col - 1, -1, this.acceptsNav, this);
                } else {
                    newCell = g.walkCells(ed.row, ed.col + 1, 1, this.acceptsNav, this);
                }
            }
        } else if (k == e.ENTER) {
            if (this.moveEditorOnEnter !== false) {
                if (shift) {
                    newCell = g.walkCells(last.row - 1, last.col, -1, this.acceptsNav, this);
                } else {
                    newCell = g.walkCells(last.row + 1, last.col, 1, this.acceptsNav, this);
                }
            }
        }
        if (newCell) {
            r = newCell[0];
            c = newCell[1];

            if (last.row != r && !this.checkOnly) {
                this.selectRow(r); // *** highlight newly-selected cell and update selection
            }

            if (g.isEditor && g.editing) { // *** handle tabbing while editorgrid is in edit mode
                ae = g.activeEditor;
                if (ae && ae.field.triggerBlur) {
                    // *** if activeEditor is a TriggerField, explicitly call its triggerBlur() method
                    ae.field.triggerBlur();
                }
            }
            g.startEditing(r, c);
        }
    }
});

/**
 * The HtmlEditor appends a non-printable character to its contents.
 * The following override removes that character.
 * This issue is seen in Firefox only.
 */
if (Ext.isGecko) {
    if (!Ext.form.HtmlEditor.prototype._jaffa_orig_cleanHtml)
        Ext.form.HtmlEditor.prototype._jaffa_orig_cleanHtml = Ext.form.HtmlEditor.prototype.cleanHtml;
    Ext.override(Ext.form.HtmlEditor, {
        cleanHtml: function (html) {
            html = this._jaffa_orig_cleanHtml(html);
            if (html && html.length > 1) {
                var defaultCharCode = this.defaultValue.replace(/\D/g, '');
                if (html == '<br>' + String.fromCharCode(defaultCharCode))
                    html = '';
                else if (html.charCodeAt(html.length - 1) == defaultCharCode)
                    html = html.substring(0, html.length - 1);
            }
            return html;
        }
    });
}

/**
 * Allow a field label to be set on a field of a panel after it has been constructed
 * Also support the setting directly via tokens
 */
Ext.override(Ext.Panel, {

    setFieldLabel: function (field, text) {
        if (field.rendered) {
            field.el.up('.x-form-item', 10, true).child('.x-form-item-label').update(text);
        }
        field.fieldLabel = text;
    },

    setFieldLabelToken: function (field, token) {
        field.fieldLabelToken = token;
        this.setFieldLabel(Labels.get(token));
    }
});

Ext.override(Ext.DatePicker, {
    setValue: function (value) {
        if (value.clearTime)
            this.value = value.clearTime(true);
        else
            this.value = new Date().clearTime(true);
        this.update(this.value);
    }
});

Ext.override(Ext.data.Store, {
    getSortState: function () {
        if (this.multiSortInfo && this.multiSortInfo.sorters && this.multiSortInfo.sorters.length > 0 && !this.sortInfo)
            return this.multiSortInfo;
        else
            return this.sortInfo;
    }
});

//Override to add clearAll method
Ext.state.Manager = function () {
    var provider = new Ext.state.Provider();

    return {

        setProvider: function (stateProvider) {
            provider = stateProvider;
        },

        get: function (key, defaultValue) {
            return provider.get(key, defaultValue);
        },

        set: function (key, value) {
            provider.set(key, value);
        },

        clear: function (key) {
            provider.clear(key);
        },

        clearAll: function () {
            provider.clearAll();
        },

        getProvider: function () {
            return provider;
        }
    };
}();

Ext.override(Ext.Toolbar, {
    enableOverflow: true,
    autoScroll: true
});

//This value was changed in 3.4.0. and caused textOnly fields in form layouts to disappear.
//Since Jaffa manages hiding labels, reverting back to false.
Ext.layout.FormLayout.prototype.trackLabels = false;

//For 3.4.0 need to add a default getEditor function to the checkbox selection model
//so that it plays well with the inlineroweditor.
Ext.grid.CheckboxSelectionModel.prototype.getEditor = function () {
    return false;
};

Ext.override(Ext.form.Checkbox, {
    onRender: function (ct, position) {
        Ext.form.Checkbox.superclass.onRender.call(this, ct, position);
        if (this.inputValue !== undefined) {
            this.el.dom.value = this.inputValue;
        }
        this.wrap = this.el.wrap({cls: 'x-form-check-wrap'});
        if (this.boxLabel && this.boxLabel != '&#160;') { //Override: do not add label if it is empty
            this.wrap.createChild({tag: 'label', htmlFor: this.el.id, cls: 'x-form-cb-label', html: this.boxLabel });
        }
        if (this.checked) {
            this.setValue(true);
        } else {
            this.checked = this.el.dom.checked;
        }
        // Need to repaint for IE, otherwise positioning is broken
        if (Ext.isIE && !Ext.isStrict) {
            this.wrap.repaint();
        }
        this.resizeEl = this.positionEl = this.wrap;
    },
    onClick: function () {
        this.hasFocus = true;
        this.isOnFocus = true;
        if (this.el.dom.checked != this.checked) {
            this.setValue(this.el.dom.checked);
        }
    }
});

Ext.override(Ext.grid.HeaderDragZone, {
    getDragData: function (e) {
        var t = Ext.lib.Event.getTarget(e);
        var h = this.view.findHeaderCell(t);
        if (h && (this.grid.colModel.config && this.grid.colModel.config[this.view.getCellIndex(h)].draggable !== false)) {
            return {ddel: h.firstChild, header: h};
        }
        return false;
    }
});

//If editor field in grid is showing invalid value, don't allow column resizing.
Ext.override(Ext.grid.GridView.SplitDragZone, {
    allowHeaderDrag: function (e) {
        if (this.grid && this.grid.activeEditor && this.grid.activeEditor.field && this.grid.activeEditor.field.isValid && !this.grid.activeEditor.field.isValid()) {
            return false;
        } else {
            return true;
        }
    }
});

// User can type in a value with a different case from the value in the store, if the user tabs out of the field the correct value is selected. If the user clicks out of the field
// the value with the incorrect case is left behind. This override will cause the correct value to be selected when clicking.
Ext.override(Ext.form.ComboBox, {
    onTypeAhead: function () {
        if (this.store.getCount() > 0) {
            var r = this.store.getAt(0);
            var newValue = r.data[this.displayField];
            var len = newValue.length;
            var selStart = this.getRawValue().length;
            this.setRawValue(newValue);
            this.selectText(selStart, newValue.length);
        }
    }
});

//Overridden to support textOnly, when a field is textOnly it should not become enabled when the parent form is enabled. Example: checkboxes
Ext.override(Ext.FormPanel, {
    onEnable: function () {
        Ext.FormPanel.superclass.onEnable.call(this);
        if (this.form) {
            this.form.items.each(function () {
                if (!this.textOnly) {
                    this.enable();
                }
            });
        }
    }
});

/**
 * Sencha 3.4.1.1 override for IE10 support.  We are adding a class to the panel when it is in the expanded state
 * so we can uniquely identify dom elements in the header of the panel.  This is the default definition of the
 * class name.
 *
 * @cfg {String} expandedCls
 * A CSS class to add to the panel's element after it has been expanded (defaults to <code>'x-panel-expanded'</code>).
 */
Ext.override(Ext.Panel, {
    expandedCls: 'x-panel-expanded'
});

/**
 * Sencha 3.4.1.1 override for IE10 support.  We are adding a class to the panel when it is in the expanded state
 * so we can uniquely identify dom elements in the header of the panel.  We must now remove that class
 * name when the panel collapses.
 *
 * @param animate true if this transition is animated
 * @returns {*} {Ext.Panel} this
 */
Ext.override(Ext.Panel, {
    collapse: function (animate) {
        if (this.collapsed || this.el.hasFxBlock() || this.fireEvent('beforecollapse', this, animate) === false) {
            return;
        }
        var doAnim = animate === true || (animate !== false && this.animCollapse);
        this.el.removeClass(this.expandedCls);  // this is the removal of the newly added expanded class name
        this.beforeEffect(doAnim);
        this.onCollapse(doAnim, animate);
        return this;
    }
});

/**
 * Sencha 3.4.1.1 override for IE10 support.  We are adding a class to the panel when it is in the expanded state
 * so we can uniquely identify dom elements in the header of the panel.
 *
 * @param anim true if this transition is animated
 */
(function () {
    var originalAfterExpand = Ext.Panel.prototype.afterExpand;
    Ext.override(Ext.Panel, {
        afterExpand: function (anim) {
            this.el.addClass(this.expandedCls);
            originalAfterExpand.apply(this, arguments);
        }
    });
})();

