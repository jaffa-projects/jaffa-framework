/**
 * @class Jaffa.maintenance.plugins.Panel
 *
 * This is the base class for a maintenance panel plugin, that will either display
 * or update data being managed by a CRUDController.
 *
 * This base class describes the standard life-cycle methods needed.
 * 
 * WARNING: bodyCfg:{cls:indent} causes IE8 fail to show scrollbar on a grid (ExtJs 3.2.2).
 *          bodyCfg is set to null in Jaffa.maintenance.plugins.GridLoadSave 
 */
Jaffa.maintenance.plugins.Panel = Ext.extend(Ext.emptyFn, {
  constructor: function (config) {
    if (config)
      Ext.apply(this, config);
  },

  duplicateValueText: 'Duplicate value',
  
  recordWithTheEnteredText: 'A record with the entered',
  
  valueText: 'value',
  
  alreadyExistsDuplicatesNotAllowedReEnterInformationTextMsg: 'already exists.  Duplicates are not allowed, please re-enter this key information.',
  
   /**
   * ExtJS invokes the init function of each plugin after the component is created.
   * This function will apply the necessary Load and Save behavior to the input Panel.
   * @param c A component, usually a Panel, for which this plugin was created.
   */
  init: function (c) {
    c.on('beforerender', this.beforerenderHandler);

    if (this.bodyCfg)
      c.bodyCfg = this.bodyCfg; // SeanZ: why not Ext.apply(c.bodyCfg, this.bodyCfg)?
    // WARNING: bodyCfg:{cls:indent} causes IE8 fail to show scrollbar on a grid (ExtJs 3.2.2).
    //          bodyCfg is set to null in Jaffa.maintenance.plugins.GridLoadSave 

    Ext.applyIf(c, {
      readOnly: this.readOnly,
      updateOnly: this.updateOnly,
      controller: this.controller,
      metaClass: this.metaClass,
      isDirty: this.isDirty,
      loadData: this.loadData,
      validateData: this.validateData,
      setDirty: this.setDirty,
      findAToolbarButton: this.findAToolbarButton,
      enableAToolbarButton: this.enableAToolbarButton,
      disableAToolbarButton: this.disableAToolbarButton,
      _setChangeListeners: this._setChangeListeners,
      _setDuplicateKeyListener: this._setDuplicateKeyListener,
      duplicateValueText: this.duplicateValueText,
      recordWithTheEnteredText: this.recordWithTheEnteredText,
      valueText: this.valueText,
      alreadyExistsDuplicatesNotAllowedReEnterInformationTextMsg: this.alreadyExistsDuplicatesNotAllowedReEnterInformationTextMsg
    });

    // add events
    c.addEvents(
      /**
       * @event datamodified
       * Fires after the panel becomes flagged as dirty.
       * It is also fired if a dirty panel is set to be "clean" again.
       * @param {Object} this
       */
      'datamodified',
      /**
       * @event duplicatekeyvalcomplete
       * Fires after duplicate key validation is completed.
       * @param {Object} this
       */
       'duplicatekeyvalcomplete',
       /**
       * @event loadexistingcomplete
       * Fires after an existing duplicate record is loaded.
       * @param {Object} this
       */
       'loadexistingcomplete'

    );

    // Use this to inform us if any widget changes data
    // This internally will raise the "datamodfied" event that we listen to
    if (!c.readOnly) {
      c._setChangeListeners(c.setDirty.createDelegate(c, [true]));
      Ext.applyIf(c, {
        saveData: this.saveData,
        doOnSaveSuccess: this.doOnSaveSuccess,
        onSaveSuccess: this.onSaveSuccess,
        onSaveFailed: this.onSaveFailed,
        onSaveCancel: this.onSaveCancel
      });
    }
  },

  /**
   * @protected
   * The init() method registers this handler with the component's 'beforerender' event.
   * By the time this handler is invoked, the ownerCt would have been stamped on the component,
   * allowing us to lookup the metaClass and controller, if they were not passed initially.
   * This handler then performs the initialization based on metaClass and controller.
   * @param c A component, usually a Panel, for which this plugin was created.
   */
  beforerenderHandler: function (c) {
    //determine metaClass
    if (!c.metaClass && c.lookupMetaClass && typeof c.lookupMetaClass === 'function')
      c.metaClass = c.lookupMetaClass(); //recursively lookup the metaClass from the component and it's parent chain

    //determine controller
    if (!c.controller && c.lookupController && typeof c.lookupController === 'function')
      c.controller = c.lookupController(); //recursively lookup the controller from the component and it's parent chain
    if (c.controller && typeof c.controller === "string")
      c.controller = Jaffa.component.Controller.getController(c.controller);

    // Find the datasource
    var dataSource = c.findDataSource && typeof c.findDataSource === 'function' ? c.findDataSource() : null;

    // Register this component with the controller and the specific data item
    if (c.controller)
      c.controller.registerPanel(c, dataSource);
  },

  /* Apply a specific CSS to the body of the Panel. */
  bodyCfg: {cls: 'indent'},
  // WARNING: indent cls causes IE8 fail to show scrollbar on a grid (ExtJs 3.2.2). 
  // bodyCfg is set to null in Jaffa.maintenance.plugins.GridLoadSave 

  /**
   * @cfg {boolean} readOnly
   * Change listeners will not be registered for a readOnly panel.
   */
  readOnly: true,

  /**
   * @cfg {boolean} updateOnly
   * duplicateKey listeners will not be added to the key fields.
   */

  /**
   * @cfg {Jaffa.controller.CRUDController} controller
   * This is a reference to the controller that this panel is associated with
   */
  controller: undefined,

  /**
   * @cfg {Object} metaClass
   * This is a reference a ClassMetaData object that contains the details to be used for validation.
   */
  metaClass: undefined,

  /**
   * Public property to infer if any values on the panel have been modifed
   *
   * DONOT modify this directly, use the setDirty(boolean) method
   */
  isDirty: false,

   /**
   * @cfg {Object} Boolean
   * Flag to determine whether validation for duplicate key is in progress.
   * If this flag is true , save operation is halted
   */
  _duplicateKeyValInProgress : false,

  /**
   * (Optional) Implement if automatic loading from controller will be used
   *
   * A general load method that is automatically wired in to the controller's
   * onload event as part of the panels registration
   */
  loadData: Ext.emptyFn,

  /**
   * (Optional) Implement if validation is required prior to save
   *
   * A general validation method that is invoked to see if this panel's
   * data can be saved without errors. This is invoked because (a) a save
   * has been invoked on the controller related to this panel, and (b) because
   * this panel when created registered its self with the controller
   *
   * @param saveStatus {Object} (optional) This object has 2 properties
   *            status, which is set to false if the vaildation fails
   *            message, which can be updated with a validation error message if we are returning false.
   * @return {boolean} true if there are no validation errors on this page,
   *         typically this is the same value as saveStatus.status
   *
   * @DEPRECATES tabValidate
   */
  validateData: Ext.emptyFn,

  /**
   * (Optional) Implement if data changed on this panel should be saved
   *
   * This packages up the data to saved from this panel, the original data object is provided
   * so a delta between it and the panel's values so only changed values need to be saved.
   *
   *
   * @param origData This is the original graph data object this panel is changing
   * @param saveData This object should be modifed by this function to contain a copy of
   *                 the origData including all the changes made to the panel.
   *                 It is recommended that you only include the fields that have changed,
   *                 assuming your back end service can support this.
   *
   * @DEPRECATES tabSave
   */
  saveData: Ext.emptyFn,

  /**
   * (Optional) Implement if you want to handle a callback once the save has been sucessfully completed
   *
   * This callback is invoked prior to the main "Save OK" callback provided to the controller's save() method
   *
   * @DEPRECATES tabSaveSuccess
   */
  onSaveSuccess: function (response) {
    this.doOnSaveSuccess();
    this.fireEvent("save", this, response);
  },

  /**
   * @protected
   * Make the process of onSaveSuccess() extensible because the 'save' event needs to be fired at the end of the successful save.
   */
  doOnSaveSuccess: function() {
    this.setDirty(false);
  },

  /**
   * (Optional) Implement if you want to handle a callback if the save fails
   *
   * This callback is invoked prior to the main "Save Error" callback provided to the controller's save() method
   *
   * @DEPRECATES tabSaveFail
   */
  onSaveFailed: Ext.emptyFn,

  /**
   * (Optional) Implement if you want to handle a callback if the save is cancelled
   *
   * In cases where the underlying service supports pending events, it is possible that
   * the save may have initially failed, and resulted in a "Pending Event Window" being
   * displayed. At this point the user typically fixed the pending events, and re-saves the
   * the data (via another call to the controller's save() method) or they decided to cancel
   * the saving process completely. In this later case the default behavoiur of the "Pending
   * Event Window" it to call this "onSaveCancel" callback method.
   *
   * @DEPRECATES tabSaveCancel
   */
  onSaveCancel: Ext.emptyFn,

  /**
   * Set or clear the dirty state of this panel
   *
   * In both cases this will fire the "datamodified" event
   */
  setDirty: function(dirty) {
    if (this.isDirty != dirty) {
      this.isDirty = dirty;
      this.fireEvent("datamodified", this);

      // propagate to outer component(s)
      if (this.ownerCt && this.ownerCt.setDirty && typeof this.ownerCt.setDirty === 'function')
        this.ownerCt.setDirty(dirty);
    }

    if (this.controller)
      this.controller.setDirty(dirty);
  },

  /**
   * Find a button on the toolbars. Look for top toolbar first.
   * @param {Object} btnId itemId placed on the button (ExtJs 3.x or above). For ExtJs 2.x, btnId is the id
   * of the button.
   */
  findAToolbarButton: function(btnId) {
    var btn = null;
    var fn = function(tb) {
      if (tb) {
      if (tb) {
        if (Ext.versionDetail) {
          return tb.getComponent(btnId);
        } else if (this.rendered) {
          return tb.items.get(this.id + btnId);
        } else {
          for (var i=0; i<tb.length; i++) {
            if (tb[i].id == btnId || tb[i].id == (this.id+btnId) || tb[i].itemId == (btnId)) {
              return tb[i];
            }
          }
        }
      }
      return null;
      }
    };
    if (typeof this.getTopToolbar =='function') btn = fn(this.getTopToolbar());
    if (btn) return btn;
    if (typeof this.getBottomToolbar == 'function') return fn(this.getBottomToolbar());
    return null;
  },

  /**
   * @deprecated in ExtJs 3.2, it can be replaced by panel.findAToolbarButton(btnId).enable()
   *
   * Enable a toolbar button.
   * @param {Ext.Button, string} btn the button object or the id of the button
   */
  enableAToolbarButton: function(btn){
    if (btn && typeof btn == 'string') btn = this.findAToolbarButton(btn);
    if (btn) {
      if (Ext.versionDetail) {
        // this must be Ext 3.x or above
        btn.enable();
        return;
      }
      if (this.rendered) {
        btn.enable();
      } else {
        btn.disabled = false;
      }
    }
  },

  /**
   * @deprecated in ExtJs 3.2, it can be replaced by panel.findAToolbarButton(btnId).disable()
   *
   * Disable a toolbar button
   * @param {Ext.Button, string} btn the button object or the id of the button
   */
  disableAToolbarButton: function(btn){
    if (btn && typeof btn == 'string') btn = this.findAToolbarButton(btn);
    if (btn) {
      if (Ext.versionDetail) {
        // this must be Ext 3.x or above
        btn.disable();
        return;
      }
      if (this.rendered) {
        btn.disable();
      } else {
        btn.disabled = true;
      }
    }
  },

  /**
   * @private
   *
   * Register a change listener on all components in the panel,
   * including Grid's and their associated stores.
   *
   * @param {Function} onChangeFn The function to invoke when data changes
   */
  _setChangeListeners: function (ocf) {
    var onChangeFn = ocf;
    if (typeof this.cascade != 'function') return;
    // incase it is used by an MVC controller rather than a panel.
    this.cascade(function (f) {
      if (f.mapping && !f.readOnly && (!f.textOnly || f.useChangeListener)) {
        // place keyup listener on fields except radio buttons and checkboxes
        if (f.xtype != 'checkbox' && f.xtype != 'radio' && f.xtype!='htmleditor' && f.xtype!='xhtmleditor' && f.xtype!='comment') {
          f.on('keyup', function (tField, evt) {
            if (evt.getKey) {
              if (!this.isDirty && !evt.isNavKeyPress() && (evt.keyCode==evt.BACKSPACE || evt.keyCode==evt.DELETE || !evt.isSpecialKey()) && !(evt.hasModifier() && evt.keyCode==evt.C)) {
                onChangeFn.call(this);
              }
            }
          }, this);
        }
        if (f.xtype == 'htmleditor' || f.xtype == 'xhtmleditor' || f.xtype == 'comment') {
          f.on('change', function(f, oldValue, newValue){
            // add old value and new value comparison to allow this event also act like change event
            // make sure that the ''==undefined==null in the test
            if (!this.isDirty && f.activated === true && f.textOnly !== true) {
              onChangeFn.call(this);
            }
          }, this);
        } else if ((f.xtype && f.xtype.toLowerCase().indexOf('combo')>=0)
            || f instanceof Ext.form.ComboBox || f instanceof Ext.form.DateField
            || f.xtype=='xdatetime' || f.xtype=='datefield'
            || f.xtype == 'checkbox' || f.xtype == 'radio' || f.xtype == 'radiogroup' ) {
          f.on({
            focus: function(combo) {
              combo.isOnFocus = true;
            }
            ,blur: function(combo) {
              combo.isOnFocus = false;
            }
          });
          if ((f.xtype && f.xtype.toLowerCase().indexOf('combo')>=0) || f instanceof Ext.form.ComboBox) {
            f.on('select', function(combo, record, index){
              if (combo.isOnFocus && !this.isDirty) {
                onChangeFn.call(this);
              }
            }, this);
          } else if (f.xtype=='xdatetime' || f instanceof Ext.ux.form.DateTime) {
            f.df.on({
              scope: this,
                select: function(df, date){
                if (!this.isDirty) {
                  onChangeFn.call(this);
                }
              },
                keyup: function(dft, evt) {
                if (evt.getKey) {
                  if (!this.isDirty && !evt.isNavKeyPress() && (!evt.isSpecialKey() || evt.getKey()==evt.BACKSPACE || evt.getKey()==evt.DELETE) && !(evt.hasModifier() && evt.keyCode==evt.C)) {
                    onChangeFn.call(this);
                  }
                }
              }
            });
            f.tf.on({
              scope: this,
              select: function(tf, date){
                if (!this.isDirty) {
                  onChangeFn.call(this);
                }
              },
              keyup: function(tf, evt) {
                if (evt.getKey) {
                  if (!this.isDirty && !evt.isNavKeyPress() && (!evt.isSpecialKey() || evt.getKey()==evt.BACKSPACE || evt.getKey()==evt.DELETE) && !(evt.hasModifier() && evt.keyCode==evt.C)) {
                    onChangeFn.call(this);
                  }
                }
              }
            });
          } else if (f.xtype=='datefield' || f instanceof Ext.form.DateField) {
            f.on('select', function(fld, date){
              if (!this.isDirty) {
                onChangeFn.call(this);
              }
            }, this);
          } else if (f.xtype == 'checkbox' || f.xtype == 'radio') {
            f.on('check', function(fld, isChecked){
              if (fld.isOnFocus && !this.isDirty) onChangeFn.call(this);
            }, this);
          } else if (f.xtype == 'radiogroup') {
            f.on('change', function(fld){
              if (fld.isOnFocus && !this.isDirty) onChangeFn.call(this);
            }, this);
          }
        }
        if (!this.updateOnly && (f.xtype && ((f.xtype=='textfield') || f.xtype.toLowerCase().indexOf('combo')>=0))) {
          // Enforce duplicate-key check
          var metaClass = this.metaClass;
          if (metaClass && typeof metaClass == 'string'){
            metaClass = ClassMetaData[metaClass];
          }
          if (metaClass && (f.mapping == metaClass.key || (Ext.isArray(metaClass.key) && metaClass.key.indexOf(f.mapping) >= 0))){
            this.keyFields = metaClass.key;
            this._setDuplicateKeyListener(f);
          }
        }
      } else if (f.getStore) {
        var store = f.getStore();
        if (store && (store.ignoreChangeListeners !== true) && (!f.xtype || f.xtype.toLowerCase().indexOf('combo') < 0)) {
          // used for grids
          store.on('update', onChangeFn, this);
          store.on('add', onChangeFn, this);
          store.on('remove', onChangeFn, this);
        }
      }
    }, this);
  },

  /**
   * @private
   *
   * Registers a change listener on key fields in the panel.
   * The listener will perform duplicate key checks.
   *
   * @param {Ext.Field} f A key field.
   */
  _setDuplicateKeyListener: function (f) {
    f.on('change', function (field, newValue, oldValue) {
      if (newValue && this.controller) {
        var criteria = {};
        var originalStartValue = field.startValue;
        var multiFieldKey = false;
        var keyFieldList = '';
        //multikey support
        if(this.keyFields && Ext.isArray(this.keyFields)){
          for(var i=0; i < this.keyFields.length; i++){
            var keyField = this.find('mapping', this.keyFields[i])[0];
            if(keyField && Ext.isFunction(keyField.getValue)){
              criteria[this.keyFields[i]] = {values: [keyField.getValue()]};
              keyFieldList = keyFieldList==''?keyField.fieldLabel:(keyFieldList + ', ' + keyField.fieldLabel);
            } else {
              console.error('Key field value not found: ' + this.keyFields[i]); 
              return;
            }
          }
          multiFieldKey = true;
        } else {
          criteria[field.mapping] = {values: [newValue]};
          keyFieldList = field.fieldLabel;
        }
        for(var p in criteria){
          if(criteria[p].values && Ext.isEmpty(criteria[p].values[0])){ //if part of the key is empty don't validate
            return false;
          }
        }
        
        //Controller save proceeds based on this flag. We don't want to save before validating duplicate key
        this._duplicateKeyValInProgress = true;
        this.controller.proxy.load(criteria, this.controller.reader || new Jaffa.data.QueryResponseReader(), function (data) {
          this._duplicateKeyValInProgress = false;
          if (data && data.records && data.records.length > 0) {
            var panel = this;
            Ext.MessageBox.show({
              title: this.duplicateValueText,
              msg: this.recordWithTheEnteredText +' ' + keyFieldList +' '+this.valueText + (multiFieldKey?'s':'') + ' '+this.alreadyExistsDuplicatesNotAllowedReEnterInformationTextMsg,
              icon : Ext.MessageBox.ERROR,
              buttons: Ext.MessageBox.OK,
              fn: function (buttonId) {
                //if (buttonId == 'ok') {
                //  //load the existing record
                //  panel.controller.criteria[field.mapping] = {values: [newValue]};
                //  panel.controller.load(function(){
                //    panel.fireEvent("loadexistingcomplete", panel, data);
                //  });
                //} else if (buttonId == 'cancel') {
                  //focus on the key-field
                  field.focus();
                  field.startValue = originalStartValue; //ensures that the change-listener is fired on a subsequent blur, even if the invalid value remains unchanged in Firefox/Chrome
                  if (multiFieldKey)field.setValue();
                  field.hasFocus = true; //ensures that the change-listener is fired on a subsequent blur, even if the invalid value remains unchanged in IE
                //}
              }
            });
          }
          this.fireEvent("duplicatekeyvalcomplete", this, data);
        }, this);
      }
    }, this);
  }
});

Ext.preg('panel', Jaffa.maintenance.plugins.Panel);