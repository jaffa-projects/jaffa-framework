/**
 * @class Jaffa.maintenance.Panel
 * @extends Ext.Panel
 *
 * This is the base class for a panel that will either be displayed
 * or updated some data being managed by a CRUDController
 *
 * This base class describes the standard life-cycle methods needed
 *
 * @deprecated Instead of being tied to a specific Panel, create any appropriate Panel and
 * apply the 'Jaffa.maintenance.plugins.Panel' plugin
 */
Jaffa.maintenance.Panel = Ext.extend(Ext.Panel, {
  /* Apply a specific CSS to the body of the Panel. */
  bodyCfg: {
    cls: 'indent'
  },
  
  /**
   * @cfg {boolean} readOnly
   * Change listeners will not be registered for a readOnly panel.
   */
  readOnly: true,
  
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
  
  constructor: function(config) {
    Jaffa.maintenance.Panel.superclass.constructor.apply(this, arguments);
    this.addEvents(    /**
     * @event datamodified
     * Fires after the panel becomes flagged as dirty (if readOnly == false).
     * It is also fired if a dirty panel is set to be "clean" again.
     * @param {Object} this
     */
    'datamodified');
    
    // Use this to inform us if any widget changes data
    // This internally will raise the "datamodfied" event that we listen to
    if (!this.readOnly) {
      this._setChangeListeners(this.setDirty.createDelegate(this, [true]));
    }
    
    //TODO - Add standard buttons to Toolbar: save, add, remove, ...
  },
  
  /**
   * (Optional) Implement if automatic loading from controller will be used
   *
   * A general load method that is automatically wired in to the controller's
   * onload event as part of the panels registration
   */
  load: Ext.emptyFn,
  
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
  onSaveSuccess: function() {
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
   * Public property to infer if any values on the panel have been modifed
   *
   * DONOT modify this directly, use the setDirty(boolean) method
   */
  isDirty: false,
  
  recordWithEnteredValueAlreadyExistsMsgText: 'Warnings / Pending Events',
  
  duplicateValueTitleText: 'Duplicate value',
  
  /**
   * Set or clear the dirty state of this panel
   *
   * In both cases this will fire the "datamodified" event
   */
  setDirty: function(dirty) {
    if (this.isDirty != dirty) {
      this.isDirty = dirty;
      this.fireEvent("datamodified", this);
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
  _setChangeListeners: function(onChangeFn) {
    this.cascade(function(f) {
      if (f.mapping && !f.readOnly && !f.textOnly) {
        if (f.xtype == 'htmleditor' || f.xtype == 'xhtmleditor' || f.xtype == 'comment') {
          f.on('sync', function(f, oldValue, newValue) {
            // add old value and new value comparison to allow this event also act like change event
            if (!this.isDirty && ((f.getValue() != f.value) || (oldValue !== newValue))) {
              onChangeFn.call(this);
            }
          }, this);
        }
        else if (f.xtype == 'combo' || f.xtype == 'finderComboGrid') {
          f.on({
            'keyup': {
              fn: function(tField, evt) {
                if (evt.getKey) {
                  if (!this.isDirty &&
                  (evt.getKey() === Ext.EventObject.BACKSPACE || evt.getKey() === Ext.EventObject.SPACE ||
                  (evt.getKey() >= Ext.EventObject.DELETE && evt.getKey() <= Ext.EventObject.Z))) {
                    onChangeFn.call(this);
                  }
                }
              },
              scope: this
            },
            'select': {
              fn: function(combo, record, index) {
                if (index) {
                  if (!this.isDirty) {
                    onChangeFn.call(this);
                  }
                }
              },
              scope: this
            }
          });
        }
        else if (f.xtype == 'checkbox' || f.xtype == 'radio') {
          f.on('check', function(fld, isChecked) {
            if (!this.isDirty) {
              onChangeFn.call(this);
            }
          }, this);
        }
        else {
          f.on('keyup', function(tField, evt) {
            if (evt.getKey) {
              if (!this.isDirty &&
              (evt.getKey() === Ext.EventObject.BACKSPACE || evt.getKey() === Ext.EventObject.SPACE ||
              (evt.getKey() >= Ext.EventObject.DELETE && evt.getKey() <= Ext.EventObject.Z))) {
                onChangeFn.call(this);
              }
            }
          }, this);
          
          // Enforce duplicate-key check
          var metaClass = this.metaClass;
          if (metaClass && typeof metaClass == 'string') {
            metaClass = ClassMetaData[metaClass];
          }
          if (metaClass && f.mapping == metaClass.key) {
            this._setDuplicateKeyListener(f);
          }
        }
      }
      else if (f.getStore) {
        var store = f.getStore();
        if (store) {
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
  _setDuplicateKeyListener: function(f) {
    f.on('change', function(field, newValue, oldValue) {
      if (newValue && this.controller) {
        var criteria = {};
        var originalStartValue = field.startValue;
        criteria[field.mapping] = {
          values: [newValue]
        };
        this.controller.proxy.load(criteria, this.controller.reader || new Jaffa.data.QueryResponseReader(), function(data) {
          if (data && data.records && data.records.length > 0) {
            var panel = this;
            Ext.MessageBox.show({
              title: this.duplicateValueTitleText,
              msg: this.recordWithEnteredValueAlreadyExistsMsgText,
              icon: Ext.MessageBox.ERROR,
              buttons: Ext.MessageBox.OK,
              fn: function(buttonId) {
                //if (buttonId == 'ok') {
                //  //load the existing record
                //  panel.controller.load();
                //}
                //else if (buttonId == 'cancel') {
                  //focus on the key-field
                  field.focus();
                  field.startValue = originalStartValue; //ensures that the change-listener is fired on a subsequent blur, even if the invalid value remains unchanged in Firefox/Chrome
                  field.hasFocus = true; //ensures that the change-listener is fired on a subsequent blur, even if the invalid value remains unchanged in IE
                //}
              }
            });
          }
        }, this);
      }
    }, this);
  }
});

Ext.reg('maintenancepanel', Jaffa.maintenance.Panel);

