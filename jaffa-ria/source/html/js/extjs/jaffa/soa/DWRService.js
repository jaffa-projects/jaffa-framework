/**
 * @author PaulE,SeanZ,BobbyC
 */

/**
 * Abstract Base Class for a 'Service' based on using DWR to access a Jaffa CRUD
 * Service Object in Java. <br/> When extended you must provide the DWR method
 * needed to query and update the related graph records. It provides a basic
 * cache of data read from the query so that it can be updated and sent back.<br/>
 * You can access the returned object graph directly, or it will automatically
 * create you a StoreFactory that can then be used to access the root records in
 * the graph.
 */
Jaffa.DWRService = function(config) {
  Ext.apply(this, config);
  this.addEvents(
    /**
     * @event beforeload
     * Fires before a request is made for a new data object.  If the beforeload handler returns false
     * the load action will be canceled.
     */
    'beforeload',
    /**
     * @event load
     * Fires after a new set of Records has been loaded.
     * @param {Service} this
     */
    'load'
  );
  Jaffa.DWRService.superclass.constructor.call(this);
}
Ext.inst

// STATIC: Copy fields from a Record to a panel
Jaffa.DWRService.loadPanelFields = function(panel, data) {
  //console.debug("Loading Panel id=",panel.id," from data=",data);
  Jaffa.DWRService.applyMetaRules(panel, data);
  var p,c;
  panel.cascade(function(f) {
    if (f.mapping != null && f.mapping!='') {
      var x = Jaffa.data.Util.get(data, f.mapping);
      if (x != undefined) {
        if (f.linkTemplate && f.textOnly) {
          var t = f.linkTemplate;
          t.compile();
          var laV = new Array(f.linkArgs.length);
          if (f.linkArgs && f.linkArgs.length > 0){
            for(var i = 0;i<f.linkArgs.length;i++){
              laV[i]=Jaffa.data.Util.get(data,f.linkArgs[i])==undefined?'':Jaffa.data.Util.get(data,f.linkArgs[i]);
            }
          }
          var frag = t.apply(laV);
          f.setValue(frag);
        } else if (f.xtype == 'radio') {
          if (x == f.value) {
            f.checked = true;
          } else{
            f.checked = false;
          }
        } else if(f.xtype == 'datefield' || f.xtype == 'xdatetime'){
            //If value is not an object, it is not a date. So convert value to date.
            if (typeof x == 'object' || x == '' || x == null){
                f.setValue(x);
            }else{
                f.setValue(new Date(x));
            }
        } else {
          f.setValue(x);
          if (f.rendered && f instanceof Ext.form.HtmlEditor) {
            f.pushValue();
          }
        }
      } else if (!f.ignoreSave) {
        if (f.linkTemplate && f.textOnly && f.getValue() != '') {
          f.setValue('');
        } else if (f.xtype == 'radio') {
          if (f.value) {
            f.checked = false;
          } else{
            f.checked = true;
          }
        } else if (typeof f.getValue() == 'string' && f.getValue()!=''){
          f.setValue('');
          if (f.rendered && f instanceof Ext.form.HtmlEditor) {
            f.pushValue();
          }
        } else if (typeof f.getValue() == 'boolean') {
          f.setValue(false);
        } else if (typeof f.getValue() == 'object') {
          f.setValue(null);
        }
      }
    }

    // Get Field Description Value
    if (f.descriptionMapping != null /*& !f.disabled*/) {
      var x = Jaffa.data.Util.get(data, f.descriptionMapping);
      f.fieldDescription = x;
      //@TODO, should probably support a f.setDescription() method on the field that handles this bit!
      if (x != null && f.rendered) {
        f.el.dom.parentNode.nextSibling.innerHTML="<i>("+x+")</i>";
      } else if (f.el && f.el.dom && f.el.dom.parentNode && f.el.dom.parentNode.nextSibling){
        f.el.dom.parentNode.nextSibling.innerHTML="";
      }
    }
  }, this);
};


/**
 *  Add rules to Grid.
*/
Jaffa.DWRService.applyMetaRulesToGrid = function(grid, includeList) {
  if (!includeList || includeList == '') includeList = ['LABEL', 'CASE', 'MINLENGTH', 'MAXLENGTH', 'READONLY', 'HIDDEN', 'MANDATORY'];

  var defaultMetaClass = grid.metaClass;
  if (! defaultMetaClass && grid.defaults) {
    defaultMetaClass = grid.defaults.metaClass;
  }
  var ccf = grid.getColumnModel().config;
  for (var i=0; i<ccf.length; i++) {
    var metaClass = ccf[i].metaClass?ccf[i].metaClass:defaultMetaClass, metaField;
    if (ccf[i].editor && ccf[i].editor.field) {
      metaClass = ccf[i].editor.field.initialConfig.metaClass || metaClass;
      metaField = ccf[i].editor.field.initialConfig.metaField;
    } else {
      metaField = ccf[i].metaField;
    }
    // resolve metaField from mapping or dataIndex
    if (metaField == null && grid.getStore() && grid.getStore().fields) {
      // metaField is functionally independent of mapping and dataIndex
      // The following algorithm is a convenience way to get metaField when it is happened to 
      // be the same as what is in mapping or dataIndex.
      var recT = grid.getStore().fields.get(ccf[i].dataIndex);
      if (recT) {
        if (recT.mapping) {
          metaField = recT.mapping;
          metaField = metaField.split('.')[metaField.split('.').length - 1];
        } else {
          metaField = recT.name;
        }
      }
    }
    // resolve metaClass
    if (metaClass && typeof metaClass == 'string') {
      // metaClass can be specified by the name of the graph class or just the graph class
      metaClass = ClassMetaData[metaClass];
    }
    // find the meta
    var meta = null;
    if (metaClass && metaClass.fields && metaField != null && metaField != '') {
      meta = metaClass.fields[metaField];
    }
    // apply rules
    if (meta) {
      // apply labels
      if (meta.label && meta.label.indexOf('???')<0 && ccf[i].noLabel != true)
          ccf[i].header = meta.label;
      
      // apply case type to the editor
      if (ccf[i].editor && ccf[i].editor.field) {
        Jaffa.DWRService.applyMetaValidations(ccf[i].editor.field, metaClass, meta, includeList);
      }
    }
  }
};


/**
 * STATIC: Apply rules from meta data to a panel.
 *  
 * Applies meta data to the panel based on information from the object being bound to the widgets
 * This MUST be called BEFORE the screen is rendered, if the widgets are already rendered this
 * routine will fail to apply much of its metadata.  
 *
 * It by default looks for all metadata in the static object ClassMetaData
 * It uses the "className" embedded in the data object along with the "mapping" name 
 * specified in the widget to determing the Field Metadata to use.
 * If the mapping contains nested referenced (ie: a.b.c.d) it will extract the "className"
 * from the object referred to as 'a.b.c' and look for field name 'd'    
 *
 * The class name can be overriden on the widget by the property 'metaClass'
 * The field name can be overriden on the widget by the property 'metaField'
 *
 * Input parameter includeList can be used to only apply a subset of rules to the panel
 * If no value is supplied to this parameter then all rules are applied.
 * Currently supported rules are: LABEL, CASE, MINLENGTH, MAXLENGTH, READONLY, HIDDEN, MANDATORY
 *
 */      
Jaffa.DWRService.applyMetaRules = function(panel, data, includeList) {
  //console.debug("Loading Panel id=",panel.id," from data=",data);
  if (!includeList || includeList == '') includeList = ['LABEL', 'CASE', 'MINLENGTH', 'MAXLENGTH', 'READONLY', 'HIDDEN', 'MANDATORY'];
  
  panel.cascade(function(f) {
    if (f && f instanceof Ext.grid.GridPanel) {
      Jaffa.DWRService.applyMetaRulesToGrid(f, includeList);
    } else if (((f.mapping != null && f.mapping!='') || f.metaField != null) && f.layout != 'adjacentfield') {
      //console.debug("Applying Metadata to widget ",f.mapping);
      var meta = null;
      var metaClass = null;
      var metaField = null;

      if (f.metaField && f.metaField!=null)
        metaField = f.metaField;
      else{
        metaField = f.mapping.split('.')[f.mapping.split('.').length-1];
      }

      if (f.metaClass && f.metaClass!=null)
        metaClass = f.metaClass;
      else if (f.mapping) {
        var mapSplit = f.mapping.split('.');
        if (data) {
          var metaNode = data;
          for (var i = 0 ; i <= mapSplit.length-2 && metaNode; i++) {
            metaNode = metaNode[mapSplit[i]];
          }
          if (metaNode && metaNode.className)
            metaClass = metaNode.className;
        }
      }
      if (metaClass == null) {
        metaClass = this.metaClass;
      }
      if (metaClass == null) {
        metaClass = panel.metaClass || panel.initialConfig.metaClass;
      }

      if (metaClass && typeof metaClass == 'string') {
        // metaClass can be specified as the name of the graph class inside of ClassMetaData 
        // or just the graph class
        metaClass = ClassMetaData[metaClass];
      }
      if (metaClass && metaClass.fields && metaField != null && metaField != '')
          meta = metaClass.fields[metaField];

      if(meta!=null) {
        //console.debug('Got meta for:' + metaClass +'.'+ metaField);
        //Evaluate Label Rule
        if (includeList.indexOf('LABEL') >= 0 && meta.label && meta.label.indexOf('???')<0 && f.noLabel != true)
            if (f.fieldLabel==''||f.fieldLabel==null)
                f.fieldLabel = meta.label;

        Jaffa.DWRService.applyMetaValidations(f, metaClass, meta, includeList);

        if (includeList.indexOf('HIDDEN') >= 0 && meta.hidden && meta.hidden == true && f.noLabel != true) {
          if (f.rendered) {
            f.hidden = true;
            if (f.getEl().up('.x-form-item')) 
              f.getEl().up('.x-form-item').setDisplayed(false);
          }
          else {
            f.hidden = true;
            f.hideLabel = true;
            f.hide();
            f.on('render', function(){
              if (this.getEl().up('.x-form-item')) 
                this.getEl().up('.x-form-item').setDisplayed(false);
            });
          }
        }
        
        if (includeList.indexOf('READONLY') >= 0 && meta.readOnly && meta.readOnly==true)
          f.textOnly = true;
      } else {
        //console.debug('No Metadata for ' + metaClass +'.'+ metaField);
      }
    }
  });
};


Jaffa.DWRService.applyMetaValidations = function(field, metaClass, meta, includeList) {
  // Apply MetaRules if this field is not a criteria operator
  if (field.operator != true) {

    // Evaluate Mandatory Rule
    if (includeList.indexOf('MANDATORY') >= 0 && meta.mandatory && meta.mandatory==true && this.isSearchPanel != true)
      field.allowBlank = false;

    if((field.xtype=='combo'||field.xtype=='finderCombo'||field.xtype=='finderComboGrid') && (field.valueField!=field.displayField)) {
      // The following COMBO BOX rules apply to the valueField and displayField differently

      if (includeList.indexOf('CASE') >= 0)
        // The current meta will be the metadata for the valueField, we have no reference to the display field meta!
        if (meta.caseType && meta.caseType != 'MixedCase') {
          field.valueFieldCase = meta.caseType.toLowerCase();

        // See if we can find metadata for the displayField on the same metaClass, if we can we can use it!
        // provide a property on the field called 'displayFieldMeta' to specify a different field name. 
        // This is useful if you have a dummy field (code+description) but what it to use the description meta data
        var dmeta = metaClass.fields[field.initialConfig.displayFieldMeta || field.displayField];
        if(dmeta && dmeta.caseType && dmeta.caseType != 'MixedCase') {
          //console.debug("Case Rule: ",field.mapping, f,meta);
          field.displayFieldCase = meta.caseType.toLowerCase();
          field.style = 'text-transform: '+field.displayFieldCase+';';
          field.initialConfig.style = field.style;
        }
      }

    } else {
      // The following rules should not be applied if its a combo with a displayField

      if (includeList.indexOf('CASE') >= 0 && meta.caseType && meta.caseType != 'MixedCase') {
        //console.debug("Case Rule: ",field.mapping, f,meta);
        field.valueFieldCase = meta.caseType.toLowerCase();
        field.displayFieldCase = meta.caseType.toLowerCase();
        field.style = 'text-transform: '+field.displayFieldCase+';';
        field.initialConfig.style = field.style;
      }

      if (!field.ignoreSave) {
        if (includeList.indexOf('MINLENGTH') >= 0 && meta.minLength && meta.minLength >= 0) 
          field.minLength = meta.minLength;

        if (includeList.indexOf('MAXLENGTH') >= 0 && meta.maxLength && meta.maxLength >= 0) {
          field.maxLength = meta.maxLength;
          if (field.rendered) {
            field.getEl().dom.maxLength = field.maxLength;
            if (!field.width)
              field.setWidth(Math.min(Math.max(field.maxLength, 5), 40) * 9);
          } else {
            // TODO: Need to set the maxLength for non-rendered fields
            if (!field.width)
              field.width = Math.min(Math.max(field.maxLength, 5), 40) * 9;
          }
        }
      }
    }
  }
};


// STATIC: Copy fields from a panel to a Record
Jaffa.DWRService.loadRecordFields = function(panel, data) {
  panel.items.each(function(f) {
    if (f.mapping != null) {
      var x = Jaffa.data.Util.get(data, f.mapping);
      //TODO properly handle readOnly fields
      if((f.xtype=='datefield' || f.xtype=='xdatetime')){
        if(!f.readOnly && !f.textOnly && f.el && (x?x.toString():"") != (f.getValue()?f.getValue().toString():"")){
          //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
          Jaffa.data.Util.set(data, f.mapping, new Date(f.getValue()));
        }
      } else if(!f.readOnly && !f.textOnly && f.el && x != f.getValue()) {
        //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
        Jaffa.data.Util.set(data, f.mapping, f.getValue());
      }
    }
    if (f.items) {
      Jaffa.DWRService.loadRecordFields(f, data);
    }
  }, this);
};


// STATIC: Copy changed fields from a panel to a Record
Jaffa.DWRService.saveRecordChanges = function(panel, newData, oldData) {
    var isChanged = false;
    panel.items.each(function(f) {
        if (f.mapping != null && !f.ignoreSave) {
            var fieldChanged=false;
            var oldValue = Jaffa.data.Util.get(oldData, f.mapping);
            //TODO properly handle readOnly fields
            if((f.xtype=='datefield' || f.xtype=='xdatetime')){
                if (typeof oldValue == 'object' || oldValue == undefined) {
                    if (!f.readOnly && !f.textOnly && f.el && (oldValue ? oldValue.toString() : "") != (f.getValue() ? f.getValue().toString() : "")) {
                        //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
                        Jaffa.data.Util.set(newData, f.mapping, f.getValue() ? f.getValue() : '');
                        fieldChanged = true;
                    }
                }else{
                    var oldValueDate;
                    if (oldValue != '' && oldValue != null) {
                        oldValueDate = new Date(oldValue);
                    } else{
                        oldValueDate = '';
                    }
                    if (!f.readOnly && !f.textOnly && f.el && (oldValueDate ? oldValueDate.toString() : "") != (f.getValue() ? f.getValue().toString() : "")) {
                        //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
                        Jaffa.data.Util.set(newData, f.mapping, f.getValue() ? f.getValue() : '');
                        fieldChanged = true;
                    }
                }
            } else if (f.xtype == 'radio') {
                if (f.checked == true && (oldValue == undefined ? '' : oldValue) != f.value){
                    Jaffa.data.Util.set(newData, f.mapping, f.value);
                    fieldChanged = true;
                }
            } else if (!f.readOnly && !f.textOnly && f.el && (oldValue == undefined ? '' : oldValue) != f.getValue()) {
                    Jaffa.data.Util.set(newData, f.mapping, f.getValue());
                fieldChanged = true;
            }
            if(fieldChanged) {
               //console.debug("   Field ", f.mapping, " changed from ",oldValue," to ", f.getValue() );
               isChanged=true;
               
               Jaffa.DWRService.StampDirtyReadCheck(newData, oldData, f.mapping);
            }
        }
        // Look at nested items if this is a panel rather that a field
        if (f.items) {
            if (Jaffa.DWRService.saveRecordChanges(f, newData, oldData)) {
                isChanged = true;
            }
        }
    }, this);
    if(isChanged) {
      newData.isChanged = true;
      //console.debug("Panel had changes",panel.id, "(",panel.title,")");
    }
    return isChanged
};

Jaffa.DWRService.StampDirtyReadCheck = function(newData, oldData, mapping) {
  // To enforce dirty-read check for a Graph, pass performDirtyReadCheck=true and the last known value for the field used for that check
  // @todo: Use ClassMetaData to determine the field used in a dirty-read check
  // For now use the hard-coded fieldname "lastChangedOn"
  //if (f.metaClass && f.metaClass.dirtyRead) {
  var prefix = mapping || '', lastChangedOn=null;
  if (oldData) {
    var i=1;
    while (lastChangedOn==null && i>=0) {
      i = prefix.lastIndexOf('.');
      prefix = i <= 0 || i==(prefix.length-1)? '' : prefix.substring(0, i);
      if (prefix=='' || Jaffa.data.Util.get(oldData, prefix)) {
        prefix = prefix=='' ? prefix : prefix+'.';
        lastChangedOn = Jaffa.data.Util.get(oldData, prefix + "lastChangedOn");
      } else {
        // mapping resides in a new graph.
        prefix = prefix=='' ? prefix : prefix+'.';
        break;
      }
    }
  } else {
    prefix = prefix=='' ? prefix : prefix+'.';
  }
  Jaffa.data.Util.set(newData, prefix+'performDirtyReadCheck', true);
  //Jaffa.data.Util.set(newData, prefix + f.metaClass.dirtyRead, Jaffa.data.Util.get(oldData, prefix + f.metaClass.dirtyRead));
  Jaffa.data.Util.set(newData, prefix+'lastChangedOn', lastChangedOn);
  //}
};

// STATIC: Copy fields from a panel to a Record
Jaffa.DWRService.loadNewRecord = function(panel, data) {
    panel.items.each(function(f) {
        if (f.mapping != null) {
            if(!f.readOnly && !f.textOnly && (f.xtype=='datefield' || f.xtype=='xdatetime') &&f.getValue()){
                Jaffa.data.Util.set(data, f.mapping, new Date(f.getValue()));
            }else if(!f.readOnly && !f.textOnly && f.el) {
                Jaffa.data.Util.set(data, f.mapping, f.getValue());
            }
        }
        if (f.items && !f.items.stopCascadeSave) {
            Jaffa.DWRService.loadNewRecord(f, data);
        }
    }, this);
};


// STATIC: Hide fields on panels
Jaffa.DWRService.applyHiddenRules = function(panel, hiddenList) {
    var hideFields = hiddenList.split(',');
    
    if (panel.getTopToolbar && panel.getTopToolbar()) {
      for (var i = 0; i < hideFields.length; i++) {
        if (panel.rendered && panel.getTopToolbar().items && panel.getTopToolbar().items.get(hideFields[i])) {
          panel.getTopToolbar().items.get(hideFields[i]).hide();
          panel.getTopToolbar().items.get(hideFields[i]).brHidden=true;
        }
        else {
          var tb = panel.getTopToolbar();
          for (var j = 0; j < tb.length; j++) {
            if (tb[j].id == hideFields[i]) {
              tb[j].hidden = true;
              tb[j].brHidden = true;
            }
          }
        }
      }
    }
    
    panel.items.each(function(f) {
        if (f.mapping){
            if(hideFields.indexOf(f.mapping) >= 0)
                if (f.rendered) {
                    f.hidden = true;
                    if (f.getEl().up('.x-form-item'))
                        f.getEl().up('.x-form-item').setDisplayed(false);
                }
                else {
                    f.hidden = true;
                    f.hideLabel = true;
                    f.hide();
                    f.on('render', function(){
                        if (this.getEl().up('.x-form-item'))
                            this.getEl().up('.x-form-item').setDisplayed(false);
                    });
                }
        }
        if (f.root && f.root.attributes && f.root.attributes.children){
            for (var i=0; i<f.root.attributes.children.length;i++){
                if (hideFields.indexOf(f.root.attributes.children[i].id) >= 0) {
                    f.root.attributes.children.remove(f.root.attributes.children[i]);
                    i = i - 1;
                }
            }
        }

        if (f.getTopToolbar && f.getTopToolbar()) {
          for (var i = 0; i < hideFields.length; i++) {
            if (f.rendered && f.getTopToolbar().items && f.getTopToolbar().items.get(hideFields[i])) {
              f.getTopToolbar().items.get(hideFields[i]).hide();
              f.getTopToolbar().items.get(hideFields[i]).brHidden=true;
            }
            else {
              var tb = f.getTopToolbar();
              for (var j = 0; j < tb.length; j++) {
                if (tb[j].id == hideFields[i]) 
                  tb[j].hidden = true;
                  tb[j].brHidden = true;
              }
            }
          }
        }

        if (f.items) {
            Jaffa.DWRService.applyHiddenRules(f,hiddenList);
        }

    }, this);
};


// STATIC: Hide panels on other panels
Jaffa.DWRService.applyHiddenPanelRules = function(panel, hiddenPanelList, panelPrefix) {
    var hidePanels = hiddenPanelList.split(',');
    if  (panelPrefix==undefined) panelPrefix='';
    
    for (var i = 0; i < hidePanels.length; i++ ){
        if (Ext.getCmp(panelPrefix + hidePanels[i]) && Ext.getCmp(panelPrefix + hidePanels[i]).hide)
            Ext.getCmp(panelPrefix + hidePanels[i]).hide();

        if (Ext.getCmp(panelPrefix + hidePanels[i]) && Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt && Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt.xtype == 'tabpanel')
            Ext.getCmp(panelPrefix + hidePanels[i]).ownerCt.remove(Ext.getCmp(panelPrefix + hidePanels[i]));

    }
};


// STATIC: Set fields as Ned
Jaffa.DWRService.applyNonEditableRules = function(panel, nonEditList) {
    var nonEditFields = nonEditList.split(',');
    panel.items.each(function(f) {
        if (f.mapping) {
            if (nonEditFields.indexOf(f.mapping) >= 0) {
                if (f.xtype == 'finderComboGrid') {
                    f.disable();
                }
                else if (f.xtype == 'checkbox'){
                    if (f.rendered) {
                        f.disable();
                    }
                    else {
                        f.disabled = true;
                    }
                }
                else {
                    if (f.rendered) {
                        f.setTextOnly(true);
                    }
                    else {
                        f.textOnly = true;
                    }
                }
            }
        }

        if (f.items) {
            Jaffa.DWRService.applyNonEditableRules(f,nonEditList);
        }

    }, this);
};


// STATIC: Set fields as mandatory
Jaffa.DWRService.applyMandatoryRules = function(panel, mandatoryList) {
    var mandatoryFields = mandatoryList.split(',');
    panel.items.each(function(f) {
        if (f.mapping) {
            if (mandatoryFields.indexOf(f.mapping) >= 0) 
                f.allowBlank = false;
        }
        
        if (f.items) {
            Jaffa.DWRService.applyMandatoryRules(f,mandatoryList);
        }

    }, this);
};


// STATIC: Check mandatory fields are populated
Jaffa.DWRService.checkRules = function(panel) {
    var failedCheck = "";
    var mandatoryCheck = "";
    var lengthCheck = "";
    var comboCheck = "";
    panel.cascade(function(f) {
        if (f.allowBlank == false) {
            if (!f.textOnly && (f.getValue() == null || (f.getValue() == ""&&f.getValue() != '0'))) {
                if (mandatoryCheck == "") 
                    mandatoryCheck = Labels.get('label.jaffaRIA.MessageBox.FollowingFieldsAreMandatory.tooltip')+" " + f.fieldLabel;
                else 
                    mandatoryCheck = mandatoryCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (f.minLength>=0) {
            if (!f.textOnly && f.getValue() && f.getValue().length < f.minLength) {
                if (lengthCheck == "") 
                    lengthCheck = Labels.get('label.jaffaRIA.MessageBox.FollowingFieldsAreShorterThanAllowed.tooltip')+" " + f.fieldLabel;
                else 
                    lengthCheck = lengthCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (f.maxLength>=0) {
            if (!f.textOnly && f.getValue() && f.getValue().length > f.maxLength) {
                if (lengthCheck == "") 
                    lengthCheck = Labels.get('label.jaffaRIA.MessageBox.FollowingFieldsAreLongerThanAllowed.tooltip')+" " + f.fieldLabel;
                else 
                    lengthCheck = lengthCheck + ", " + f.fieldLabel;
                return false;
            }
        }
        if (['finderCombo', 'finderComboGrid', 'finderLovCombo', 'finderLovComboGrid'].indexOf(f.xtype) >= 0 && !f.isValueValid) {
            if (comboCheck == "") 
                comboCheck = Labels.get('label.jaffaRIA.MessageBox.FollowingFieldsContainInvalidValues.tooltip')+" " + f.fieldLabel;
            else 
                comboCheck = comboCheck + ", " + f.fieldLabel;
            return false;
        }
    }, this);
    if (mandatoryCheck!="" && lengthCheck!=""){
        failedCheck = mandatoryCheck + "<br>" + lengthCheck;
    }else{
        if (mandatoryCheck!="")
            failedCheck = mandatoryCheck;
        else if (lengthCheck!="")            
            failedCheck = lengthCheck;
    }
    if (comboCheck!=''){
        if (failedCheck!=''){
            failedCheck = failedCheck + "<br>" + comboCheck;            
        }else{
            failedCheck = comboCheck;
        }
    }

    return failedCheck;
};


/** STATIC: Clear panel fields
 * @param {Array} excludedFields a string array enumerates the mapping values of the fields to be excluded.
 */
Jaffa.DWRService.clearPanelFields = function(panel, excludedFields) {
    var tmp = (excludedFields || []).toString() || '';
    panel.cascade(function(f) {
        if (f.mapping != null && f.mapping!='' && 
          (!tmp || tmp=='' || !(tmp==f.mapping || tmp.search(','+f.mapping)>=0 || tmp.search(f.mapping+',')>=0))) {
            if (f.xtype == 'checkbox') {
                f.setValue(false);
                f.originalValue = false;
            } else if (f.xtype == 'combo'){
                f.clearValue();
                f.setValue(null);
                f.originalValue = '';
            } else if (f.xtype == 'finderComboGrid'){
                f.originalValue = '';
                f.clearValue();
                f.setValue(null);
            } else{
                f.setValue(null);
                f.originalValue = '';
            }
            f.reset();
        }

    }, this);
};


/**
 * STATIC: Copy changed fields of a Jaffa.data.Record to a node in the graph.
 * 1. if key field in record is different from that of oldData, abort the operation.
 * 2. if key field in record is not null, copy it over to newData
 * 3. if oldData!=null, copy from rec to newGraph for the fields that are different in rec and in oldData.
 * 4. if oldData!=null && metaClass!=null && newGraph has updated, populate the mandatory fields in newGraph from those in rec 
 *    if they are not null, otherwise from those in oldData
 * 5. if oldData==null, copy all non-null fields in rec.
 * 
 * @param {Object} rec a Jaffa.data.Record object.
 * @param {Object} newData modified data to be populated to
 * @param {Object} oldData old data to determine which field has been modified. If it is null, 
 * it means the rec is created new.
 * @param {Object} metaClass defines the aop rules to the fields of the record
 */
Jaffa.DWRService.saveChangedStoreRecordsToGraph = function(rec, newData, oldData, metaClass) {
  var isChanged = false;
  if (oldData) {
    rec.fields.each(function(f) {
      if (f.key==true) {
        // step 1.
        var oldValue = Jaffa.data.Util.get(oldData, f.mapping);
        if (oldValue && oldValue!=rec.get(f.name)) {
          Ext.MessageBox.alert(Labels.get('label.jaffa.jaffaRIA.jaffa.DWRService.Data.Error'), 
              Labels.get('label.jaffa.jaffaRIA.jaffa.DWRService.Data.Error.message',[f.mapping]));
          return false;
        } else if (oldValue) {
          // step 2.
          Jaffa.data.Util.set(newData, f.mapping, oldValue);
        }
      } else if (f.mapping != null && (!f.displayOnly)) {
        // step 3.
        var oldValue = Jaffa.data.Util.get(oldData, f.mapping);
        //TODO: properly handle readOnly fields
        if((f.xtype=='datefield' || f.xtype=='xdatetime')){
          if((oldValue?oldValue.toString():"") != (rec.get(f.name)?rec.get(f.name).toString():"")){
            //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
            Jaffa.data.Util.set(newData, f.mapping, rec.get(f.name)?new Date(rec.get(f.name)):'');
            isChanged = true;
          }
        } else if((oldValue==undefined?'':oldValue) !== rec.get(f.name)) {
          //console.debug("setField", f.mapping, " on ", data, " to ", f.getValue() );
          Jaffa.data.Util.set(newData, f.mapping, rec.get(f.name));
          isChanged = true;
        }
        // step 4.
        if (isChanged==true) {
          Jaffa.DWRService.updateMandatoryFields(newData, oldData, metaClass);
          Jaffa.DWRService.StampDirtyReadCheck(newData, oldData, f.mapping);
        }
      }
    }, this);
  } else {
    // step 5.
    for (var i in rec.data) {
      if (rec.get(i) && rec.fields.get(i).mapping && (!rec.fields.get(i).displayOnly)) {
        Jaffa.data.Util.set(newData, rec.fields.get(i).mapping, rec.get(i));
        Jaffa.DWRService.StampDirtyReadCheck(newData, oldData, rec.fields.get(i).mapping);
        isChanged = true;
      }
    }
  }
  return isChanged
};


/**
 * Update the key field and the mandatory fields if they are null or empty in newData. Process abort if 
 * either oldData or metaClass is null
 * @param {Object} newData
 * @param {Object} oldData
 * @param {Object} metaClass
 */
Jaffa.DWRService.updateMandatoryFields = function(newData, oldData, metaClass) {
  if (metaClass && oldData) {
    // update mandatory fields
    var v;
    for (var i in metaClass.fields) {
      if (metaClass.fields[i].mandatory == true) {
        v = Jaffa.data.Util.get(newData, i);
        if (! v || v=='') {
          Jaffa.data.Util.set(newData, i, Jaffa.data.Util.get(oldData, i));
        }
      }
    }
    // update key fields
    if (metaClass.key) {
      Jaffa.data.Util.set(newData, metaClass.key, Jaffa.data.Util.get(oldData, metaClass.key));
    }
  }
};


/**
 * STATIC: Update a Jaffa.data.Record from the fields in a Ext.Panel according to the matching mapping values.
 * @param {Object} rec the Jaffa.data.Record object
 * @param {Object} panel the Ext.Panel object
 */
Jaffa.DWRService.updateStoreRecordFromPanel = function(rec, panel) {
  if (rec && panel) {
    //console.debug("Loading Panel id=",panel.id," from data=",data);
    if(panel instanceof Ext.Container){
      Jaffa.DWRService.applyMetaRules(panel, rec.data);
    }
    panel.items.each(function(f) {
        if (f.mapping != null && f.mapping!='') {
          var i = rec.fields.findIndex('name', f.mapping);
          if (i >= 0) {
            rec.set(rec.fields.get(i).name, f.getValue());
          }
        }
        
        // Get Field Description Value
        if (f.descriptionMapping != null /*& !f.disabled*/) {
          var i = rec.fields.findIndex('name', f.descriptionMapping);
          if (i >= 0) {
            rec.set(rec.fields.get(i).name, f.fieldDescription);
          }
        }
        
        // Recursive call if this item has nested item
        if (f.items) {
            Jaffa.DWRService.updateStoreRecordFromPanel(rec, f);
        }
    }, this);
  }
};


/**
 * STATIC: Check the status of each grid cell. Alert the first invalid cell. After 
 * the user clicked on the pop-up window, the cell will be on focus. The recognition of invalid cell
 * is relied on its <td> css class names contains the work 'invalid'.
 * @param {Object} grid
 */
Jaffa.DWRService.checkGridCellValidation = function(grid) {
  var rlen=grid.getStore().getCount(), clen=grid.getColumnModel().config.length;
  for (var ir=0; ir<rlen; ir++) {
    for (var ic=0; ic<clen; ic++) {
      var cell = grid.getView().getCell(ir, ic);
      if (cell.className.indexOf('invalid') >=0 ) {
        Ext.MessageBox.alert(Labels.get('label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error'), 
           Labels.get('label.jaffa.jaffaRIA.jaffa.DWRService.Grid.Validation.ErrorMessage', [grid.getColumnModel().getColumnHeader(ic)]), 
           function(btn) {
             this.grid.getView().focusCell(this.row, this.col);
           }, {grid: grid, row: ir, col: ic});
        return false;
      }
    }
  }
  return true;
};


/**
 * STATIC: Check mandatory fields in Jaffa.data.Record objects inside of a Ext.data.Store
 * @param {Object} store a Ext.data.Store object
 * @param {Object} metaClass defines the aop rules to the fields of the records in the class
 */
Jaffa.DWRService.checkRulesOnRecords = function(store, metaClass) {
    var failedCheck = "";
    var mandatoryCheck = "";
    var lengthCheck = "";
    var keyCheck = '';
    var recordDetailCheck = '';
    
    // step 1. make an array of mandatory field names from metaClass
    var mandatoryFields = new Array(), idx, f;
    for (var i in metaClass.fields) {
      if (metaClass.fields[i].mandatory == true) {
        idx = store.fields.findIndex('mapping', i);
        if (idx >= 0) {
          f = store.fields.get(idx);
          if (! f.notEditable) mandatoryFields[mandatoryFields.length] = f.name;
        }
      }
    }
    // step 2. check each record of their mandatory fields
    var tabValidateError = false;
    store.each(function(rec) {
      var tab;
      if (rec.get('_panelId')) {
        tab = Ext.getCmp(rec.get('_panelId'));
      }
      if (tab) {
        // check the detail panel of the record
        if (tab.tabValidate && tab.tabValidate()==false) {
          Ext.getCmp("tabMaster").activate(tab);
          tabValidateError = true;
          return false;
        }
      } else {
        // check the record
        for (var i=0; i<mandatoryFields.length; i++) {
          f = rec.get(mandatoryFields[i]);
          if (f == null || f == '') {
            mandatoryCheck = mandatoryFields[i]+' '+Labels.get('label.jaffaRIA.MessageBox.IsMandatoryMsg.SmallCAPS');
            return false;
          }
        }
      }
    }, store);
    if (tabValidateError) {
      // error message has been displayed in the tab.
      return false;
    }

    // check key and natureKey in the store records.
    if (store.keyField==null || store.natureKeyField==null || store.kmapping==null || store.nkmapping==null) {
      store.fields.each(function(f) {
        if (f.key == true) {
          this.keyField = f.name;
          this.kmapping = f.mapping;
        }
        if (f.natureKey == true) {
          this.natureKeyField = f.name;
          this.nkmapping = f.mapping;
        }
        if (this.keyField && this.natureKeyField) return false;
      }, store);
    }
    if (store.keyField==null || store.natureKeyField==null || store.kmapping==null || store.nkmapping==null) {
      keyCheck = Labels.get('label.jaffaRIA.MessageBox.BothKeyAndNatureKeyMustBeDefinedInTheStoreAlongWithMappings.tooltip');
    }
    
    
    // combine the error messages
    failedCheck = mandatoryCheck;
    if (lengthCheck != "") {
      if (failedCheck == '') {
        failedCheck = lengthCheck;
      } else {
        failedCheck = failedCheck + "<br>" + lengthCheck;
      }
    }    
    if (keyCheck != "") {
      if (failedCheck == '') {
        failedCheck = keyCheck;
      } else {
        failedCheck = failedCheck + "<br>" + keyCheck;
      }
    }    
    if (recordDetailCheck != "") {
      if (failedCheck == '') {
        failedCheck = recordDetailCheck;
      } else {
        failedCheck = failedCheck + "<br>" + recordDetailCheck;
      }
    }    
    
    // display the error message
    if (failedCheck != '') {
      Ext.MessageBox.alert(Labels.get('label.jaffa.jaffaRIA.jaffa.DWRService.Validation.Error'), failedCheck);
      return false;
    }
    return true;
};

/**
 * Loop through the records in the grid store. Make updates to newGraph if the record value is different from 
 * that of oldGraph. Each record maps onto the graph in an array by key field or natureKey field.
 * 
 * @param {Object} origGraph an array of graphs from service model.
 * @param {Object} newGraph a Clone of origGraph to be modified or added by the Records in this grid store
 * @param {Object} store a Ext.data.Store object, the records in which are to be updated to the newGraph
 * @param {Object} metaClass a ClassMetaData.* object defining the aop rules.
 */
Jaffa.DWRService.gridSave2DwrGraph = function(origGraph, newGraph, store, metaClass) {
  var isChanged = false;
  
  store.each(function(rec) {
    var recId = rec.get(this.keyField);
    if (recId) {
      if (!rec.dirty) return;
      for (var i=0; i<origGraph.length; i++) {
        if (recId == Jaffa.data.Util.get(origGraph[i], this.kmapping)) {
          var hasNewGraph=false;
          for (var j=0; j<newGraph.length; j++) {
            if (recId == Jaffa.data.Util.get(newGraph[j], this.kmapping)) {
              hasNewGraph = true;
              if (Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, newGraph[j], origGraph[i])) {
                Jaffa.DWRService.updateMandatoryFields(newGraph[j], origGraph[i], metaClass);
                newGraph[j].isChanged = true;
                isChanged = true;
              }
              break;
            }
          }
          if (hasNewGraph == false) {
            var oo = {};
            if (Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, oo, origGraph[i])) {
              Jaffa.DWRService.updateMandatoryFields(oo, origGraph[i], metaClass);
              oo.isChanged = true;
              newGraph[newGraph.length] = oo;
              isChanged = true;
            }
          }
        }
      }
    } else {
      var hasNewGraph = false;
      for (var i=0; i<newGraph.length; i++) {
        if (Jaffa.data.Util.get(newGraph[i], this.kmapping)==null) {
          var nkv = Jaffa.data.Util.get(newGraph[i], this.nkmapping);
          if (nkv && rec.get(this.natureKeyField)==nkv) {
            hasNewGraph = true;
            if (Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, newGraph[i])) {
              newGraph[i].isChanged = true;
              isChanged = true;
            }
          }
        }
      }
      if (hasNewGraph == false) {
        var oo = {};
        if (Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, oo)) {
          oo.isChanged = true;
          isChanged = true;
          newGraph[newGraph.length]=oo;
        }
      }
    }
  }, store);
  
  if (isChanged) {
    newGraph.isChanged = true;
  }
  
  return isChanged;
}


/**
 * Loop through the records in the grid store. Make updates to newGraph if the record value is different from 
 * that of oldGraph. If the record has detail tab, the detail tab will be used to update. 
 * Each record maps onto the graph in an array by key field or natureKey field.
 * 
 * @param {Object} origGraph an array of graphs from service model.
 * @param {Object} newGraph a Clone of origGraph to be modified or added by the Records in this grid store
 * @param {Object} store a Ext.data.Store object, the records in which are to be updated to the newGraph
 * @param {Object} metaClass a ClassMetaData.* object defining the aop rules or the name of the class.
 */
Jaffa.DWRService.gridTabSave2DwrGraph = function(origGraph, newGraph, store, metaClass) {
  var isChanged = false;
  if (metaClass && typeof metaClass == 'string') {
    metaClass = ClassMetaData[metaClass];
  }
  
  store.each(function(rec) {
    var recId = rec.get(this.keyField);
    if (recId) {
      if (!rec.dirty && !rec.get('_panelId')) return;
      // update a record that exists on the original graph
      for (var i=0; i<origGraph.length; i++) {
        if (recId == Jaffa.data.Util.get(origGraph[i], this.kmapping)) {
          var hasNewGraph=false;
          for (var j=0; j<newGraph.length; j++) {
            if (recId == Jaffa.data.Util.get(newGraph[j], this.kmapping)) {
              // the graph of this record has been updated elsewhere
              hasNewGraph = true;
              var isUpdated = false;
              if (origGraph[i].deleteObject == true) {
                newGraph[j].deleteObject = true;
                isUpdated = true;
              } else {
                isUpdated = Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, newGraph[j], origGraph[i]);
                if (rec.get('_panelId')) {
                  // update from tab panel (detail of record)
                  var tab = Ext.getCmp(rec.get('_panelId'));
                  if (tab) {
                    isUpdated = isUpdated | Jaffa.DWRService.saveRecordChanges(tab, newGraph[j], origGraph[i]);
                  }
                }
              }
              if (isUpdated) {
                Jaffa.DWRService.updateMandatoryFields(newGraph[j], origGraph[i], metaClass);
                newGraph[j].isChanged = true;
                isChanged = true;
              }
              break;
            }
          }
          if (hasNewGraph == false) {
            // the graph of this record has not been updated elsewhere
            var oo = {}, isUpdated = false;
            if (origGraph[i].deleteObject == true) {
              oo.deleteObject = true;
              oo[this.kmapping] = recId;
              isUpdated = true;
            } else {
              isUpdated = Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, oo, origGraph[i]);
              if (! isUpdated) {
                oo = {};
              }
              if (rec.get('_panelId')) {
                // update from tab panel (detail of record)
                var tab = Ext.getCmp(rec.get('_panelId'));
                if (tab) {
                  isUpdated = isUpdated | Jaffa.DWRService.saveRecordChanges(tab, oo, origGraph[i]);
                }
              }
            }
            if (isUpdated) {
              Jaffa.DWRService.updateMandatoryFields(oo, origGraph[i], metaClass);
              oo.isChanged = true;
              newGraph[newGraph.length] = oo;
              isChanged = true;
            }
          }
        }
      }
    } else {
      // update a new record that is not on the server side
      var hasNewGraph = false;
      for (var i=0; i<newGraph.length; i++) {
        if (Jaffa.data.Util.get(newGraph[i], this.kmapping)==null) {
          // this new record has been updated elsewhere
          var nkv = Jaffa.data.Util.get(newGraph[i], this.nkmapping);
          if (nkv && (rec.get(this.natureKeyField) == nkv)) {
            hasNewGraph = true;
            var isUpdated = Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, newGraph[i]);
            if (rec.get('_panelId')) {
              // update from tab panel (detail of record)
              var tab = Ext.getCmp(rec.get('_panelId'));
              if (tab) {
                isUpdated = isUpdated | Jaffa.DWRService.saveRecordChanges(tab, newGraph[i], {});
              }
            }
            if (isUpdated) {
              newGraph[i].isChanged = true;
              isChanged = true;
            }
          }
        }
      }
      if (hasNewGraph == false) {
        // this new record has not been updated elsewhere
        var oo = {};
        var isUpdated = Jaffa.DWRService.saveChangedStoreRecordsToGraph(rec, oo);
        if (! isUpdated) {
          oo = {};
        }
        if (rec.get('_panelId')) {
          // update from tab panel (detail of record)
          var tab = Ext.getCmp(rec.get('_panelId'));
          if (tab) {
            isUpdated = isUpdated | Jaffa.DWRService.saveRecordChanges(tab, oo, {});
          }
        }
        if (isUpdated) {
          isChanged = true;
          oo.isChanged = true;
          newGraph[newGraph.length]=oo;
        }
      }
    }
  }, store);
  
  // copy over the deleted graphs
  for (var i=0; i<origGraph.length; i++) {
    if (origGraph[i].deleteObject == true) {
      // find matching object in newGraph
      var found = false;
      for (var j=0; j<newGraph.length; j++) {
        if (newGraph[j][store.kmapping] == origGraph[i][store.kmapping]) {
          newGraph[j].deleteObject = true;
          found = true;
          break;
        }
      }
      if (found == false) {
        var oo = {deleteObject: true};
        oo[store.kmapping] = origGraph[i][store.kmapping];
        Jaffa.DWRService.updateMandatoryFields(oo, origGraph[i], metaClass);
        newGraph[newGraph.length] = oo;
      }
      isChanged = true;
    }
  }

  if (isChanged) {
    newGraph.isChanged = true;
  }
  
  return isChanged;
}


Ext.extend(Jaffa.DWRService, Ext.util.Observable, {
    /**
     * This is used for user messages.<br/> Abstract, must be provided by
     * sub-class
     */
    objectLabel : '*ObjectName*',

    /**
     * This is the DWR Service to be used to load the data.<br/> Abstract, must
     * be provided by sub-class
     */
    dwrQuery : null,

    /**
     * This is the DWR Service to be used to save modified data.<br/> Abstract,
     * must be provided by sub-class
     */
    dwrUpdate : null,

    /**
     * This is the criteria used for the query
     */
    criteria : null,

    /**
     * This is where the main object array is stored
     */
    model : null,

    /**
     * Flag to indicate if the query to load the data has been peformed May be
     * true even if the model is null
     */
    isLoaded : false,

    /**
     * Invoke this to load the data based on the criteria. As this is
     * asyncronous, a callback method can be provided, or the on("load") event
     * handler can be used to process the data once it has been loaded via the
     * DWR service
     */
    load : function(callback) {
        // console.debug("Criteria return fields", this.criteria.resultGraphRules);
        if (this.fireEvent("beforeload", this, {}) !== false) {
            this.isLoaded = false;
            delete this.model;
            var loaded = this.onLoad.createDelegate(this, [callback || this.defaultLoadCallback], 1);
            this.dwrQuery(this.criteria, loaded);
        }
    },
    
    /**
     * PRIVATE: this is used as the default callback function if it is not provided in load().
     * @param {Object} service
     */
    defaultLoadCallback: function(service) {
      // Create callback handler to give error and close page if not found
      if(service.isLoaded && service.model==null) {
        Ext.MessageBox.show( {
          title: service.objectName+' '+Labels.get('title.jaffa.jaffaRIA.soa.DWRService.LoadError'),
          msg: service.objectName+' '+Labels.get('label.jaffa.jaffaRIA.soa.DWRService.LoadFailure'),
          //width:400,
          buttons: Ext.MessageBox.OK
        });
      }    
    },

    /**
     * PRIVATE: This is called internally once the DWR query returns
     */
    onLoad : function(data, callback) {
        if (data && !Ext.isArray(data)) {
          // modified to handle the upgrade of service to graph structure.
          data = data.graphs;
        }
        if (data != null && typeof data == 'object' && data.length > 0) {
            //pre-process all data and convert arrays to MixedCollections
            this.model = ClassMetaData.convertArrays(data);
        } else
            this.model = null;
        this.isLoaded = true;
        this.registeredPanels = null;
        console.debug("Loaded Data ", data, " model=",this.model);
        if (typeof callback == 'function')
          if(callback(this)===false)
            return false;
        return this.fireEvent("load", this);
    },

    //register panels with graph nodes for save purposes
    registeredPanels:null,
    addRegisteredPanel: function(panelId, graphNode){
        if (!this.registeredPanels || this.registeredPanels == null) {
            this.registeredPanels = [panelId];
        }
        else {
            if (this.registeredPanels.indexOf(panelId) < 0) {
                this.registeredPanels[this.registeredPanels.length] = panelId;
            }
        }
        if (graphNode.registeredPanels) {
            if (graphNode.registeredPanels.indexOf(panelId) < 0) {
                graphNode.registeredPanels[graphNode.registeredPanels.length] = panelId;
            }
        }
        else {
            graphNode.registeredPanels = [panelId];
        }
    },
    
    removeRegisteredPanel: function(panelId, graphNode){
        if (this.registeredPanels.indexOf(panelId) >= 0)
            this.registeredPanels.splice(this.registeredPanels.indexOf(panelId));

        if (graphNode.registeredPanels)
            if (graphNode.registeredPanels.indexOf(panelId) >= 0)
                graphNode.registeredPanels.splice(graphNode.registeredPanels.indexOf(panelId));
    },


    /**
     * Save Object Structure
     */ 
    save : function(callbackOk,callbackError) {

      //validate all registered tabs to make sure we can save
      if(!this.validatePanels(callbackOk,callbackError))
                      return false;
  
      //pre-process "model" to create a "dirty" data view to save,
      //removing the MixedCollections
      this.buildSaveModel();

      console.debug("DWR Data to be stored ", this.saveModel);
      //var svc = this;
      this.dwrUpdate(this.saveModel, {
        errorHandler: this.saveError.createDelegate(this,[callbackOk,callbackError],0),
        callback: this.saveOk.createDelegate(this,[callbackOk,callbackError],0)
      });
    },
      
    saveError : function(cbOk,cbError,response, ex) {
      // Handle is something REAL BAD happens
      console.debug("Internal Errors on Save",response, ex);
      var txt = response;
      if(ex) {
        if((ex.cause && ex.cause.applicationExceptionArray) ||
          ((response==null||response=="") && ex.cause) ||
          (ex.cause && ex.javaClassName.match(/^java.+/) && ex.cause.localizedMessage))
          ex=ex.cause;
        if(ex.applicationExceptionArray) {
        txt="";
          var e = ex.applicationExceptionArray;
          for (var j=0;j<e.length;j++) {
            txt += e[j].localizedMessage + '\n<br>';
            }
        } else {
          //console.error("Internal Error: ", ex);
          txt = Labels.get('error.framework.general');
        }
      }

      Ext.MessageBox.show( {
        titleToken : 'title.jaffaRIA.MessageBox.SaveErrors',
        msg : txt,
        buttons : Ext.MessageBox.OK,
        icon: Ext.MessageBox.ERROR
      });

      if (typeof cbError == 'function')
        cbError(this.model, response);
    },


    saveOk: function(cbOk,cbError,response) {
      console.debug("Response from save was",response);


      //Remove saveModel node after save
      this.saveModel = null;
          
      //After Save convert arrays back to MixedCollections
      if(this.model!=null)
        ClassMetaData.convertArrays(this.model.itemAt(0));

      var ok=true;
      var msg = '';
      this.pending = [];
      if (response) {
        for(var i=0;i<response.length;i++) {
          var responseEl = response[i];
          if (responseEl.errors && (responseEl.errors.applicationExceptionArray  || responseEl.errors.length > 0)) {
            // ApplicationExceptions
            var e = responseEl.errors.applicationExceptionArray? responseEl.errors.applicationExceptionArray : responseEl.errors;
            for (var j=0;j < e.length; j++) {
              var appExp = e[j]
              ok=false;
              var type = appExp.javaClassName ? appExp.javaClassName : appExp.type;

              if (type === "org.jaffa.soa.rules.PendingEventException") {
                this.pending[this.pending.length] = { event: appExp.arguments[0], params: appExp.params, warn:false };
              } else if(type === "org.jaffa.soa.rules.WarningEventException") {
                this.pending[this.pending.length] = { event: appExp.arguments[0], text:appExp.localizedMessage, params: appExp.params, warn:true };
              } else
                msg += appExp.localizedMessage + '\n<br>';
            }
          } else if (responseEl.runtimeError) {
            // FrameworkException
            //console.error("Internal Error: ", responseEl.runtimeError);
            ok=false;
            msg += Labels.get('error.framework.general') + '\n<br>';
          }   
        }
      } else {
        this.processEventGraphs = null;
        //this.registeredPanels = null;
      }

      //notify registered panels about save fail/success
      if (this.registeredPanels) {
        if (ok) {
          //On Save Success
          for (var i = 0; i < this.registeredPanels.length; i++) {
            if (Ext.getCmp(this.registeredPanels[i]) && Ext.getCmp(this.registeredPanels[i]).tabSaveSuccess) {
              Ext.getCmp(this.registeredPanels[i]).tabSaveSuccess();
            }
          }
        } else {
          //On Save Failure
          for (var i = 0; i < this.registeredPanels.length; i++) {
            if (Ext.getCmp(this.registeredPanels[i]) && Ext.getCmp(this.registeredPanels[i]).tabSaveFail) {
              Ext.getCmp(this.registeredPanels[i]).tabSaveFail();
            }
          }
        }
      }

      if(ok) {
        console.debug(this.objectName," Service Saved OK");
        // CallBack to post save method
        if (typeof cbOk == 'function')
          cbOk(this.model, response);
      } else if(msg) {
        // CallBack to post save method
        Ext.MessageBox.show( {
            titleToken : 'title.jaffaRIA.MessageBox.SaveErrors',
            msg : msg,
            //width : 400,
            buttons : Ext.MessageBox.OK,
            icon: Ext.MessageBox.ERROR
        });
        //If application exception occurs then remove all process events
        this.processEventGraphs = null;
        if (typeof cbError == 'function')
          cbError(this.model, response);
      } else if (this.pending.length>0) {
        // Only pending events came back
        response.pending = this.pending;
        cbError(this.model, response);
      } else {
        // Should never happen!!!
      }
    },

    //validate all registered tabs to make sure we can save
    validatePanels : function(callbackOk,callbackError) {
      if (this.registeredPanels) {
        for (var i = 0; i < this.registeredPanels.length; i++) {
          if (Ext.getCmp(this.registeredPanels[i]) && Ext.getCmp(this.registeredPanels[i]).tabValidate) {
            var saveStatus = {
              status: '',
              message: ''
            };
            var isValid = Ext.getCmp(this.registeredPanels[i]).tabValidate(saveStatus);
            if (!isValid) {
              try {
                Ext.getCmp("tabMaster").activate(Ext.getCmp(this.registeredPanels[i]));
              } 
              catch (e) {
                // this allows to register panels that are not inside of tabMaster
              }
              var response = new Object();
              response.validationError = saveStatus.message;
              callbackError(this.model, response);
              return false;
            }
          }
        }
      }
      return true;
    },

    // This take the service.model, and returns an new instance of it with all the
    // modified fields set. This value is cached in service.saveModel
    buildSaveModel: function() {
      //pre-process "model" to create a "dirty" data view to save,
      //removing the MixedCollections
      this.saveModel = this.clone(ClassMetaData.convertCollections(this.model));
      if (this.processEventGraphs && this.processEventGraphs != null) {
        if (this.saveModel.length == 0) {
          this.saveModel[0] = new Object();
          this.saveModel[0].className = this.model.itemAt(0).className;
          if (typeof ClassMetaData[this.saveModel[0].className].key == 'object' && ClassMetaData[this.saveModel[0].className].key > 0) 
            for (var i = 0; i < ClassMetaData[this.saveModel[0].className].key.length; i++) {
              this.saveModel[0][ClassMetaData[this.saveModel[0].className].key[i]] = this.model.itemAt(0)[ClassMetaData[this.saveModel[0].className].key[i]];
            }
          else 
            this.saveModel[0][ClassMetaData[this.saveModel[0].className].key] = this.model.itemAt(0)[ClassMetaData[this.saveModel[0].className].key];
        }
        this.saveModel[0].processEventGraphs = this.fullClone(this.processEventGraphs);
      }
    },
    
    /**
     * Clone Function
     */
    clone: function(myObj, validationFailed){
      if (myObj == null)
        return null;

      var objectClone = new myObj.constructor();
      objectClone.isChanged = false;

      if (myObj.isNew) {
        objectClone = this.fullClone(myObj);
        objectClone.isChanged = true;
      }

      // Loop through objects registeredPanels and save changed values from the panel
      if (myObj.registeredPanels && myObj.registeredPanels.length > 0) 
        for (var i = 0; i < myObj.registeredPanels.length; i++) {
          if (Ext.getCmp(myObj.registeredPanels[i]) && Ext.getCmp(myObj.registeredPanels[i]).tabSave) {
            var childChanged = Ext.getCmp(myObj.registeredPanels[i]).tabSave(myObj, objectClone);
            if (childChanged) 
              objectClone.isChanged = true;
          }
        }

      if (!myObj.isNew) {
        if (myObj.propertyChanged) {
          objectClone = myObj;
          objectClone.isChanged = true;
        } else {
          // loop through arrays within the object (looking for registered panels)
          if (typeof myObj == 'object' && myObj.length && myObj.length > 0) 
            for (var i = 0; i < myObj.length; i++) {
              objectClone[objectClone.length] = this.clone(myObj[i]);
              if (objectClone[objectClone.length - 1].isChanged) {
                delete objectClone[objectClone.length - 1].isChanged;
                objectClone.isChanged = true;
              }
              else {
                objectClone[objectClone.length - 1] = '';
                objectClone.splice((objectClone.length - 1), 1);
              }
            }

          //loop through other objects within the object (looking for registered panels)
          if (!objectClone.deleteObject) {
            for (var property in myObj) {
              if (typeof myObj[property] == 'object' && !this.IsNumeric(property)){// && myObj[property].length && myObj[property].length > 0) {
                if (objectClone[property] && objectClone[property]!=null) {
                    //Don't process nodes added to graph from registered panels.
                } else {
                  objectClone[property] = this.clone(myObj[property]);
                  if (objectClone[property] && objectClone[property].isChanged) {
                    delete objectClone[property].isChanged;
                    objectClone.isChanged = true;
                  } else {
                    objectClone[property] = '';
                    delete objectClone[property];
                  }
                }
              }
            }
          }

          //If clone includes changes then add classname and key field properties
          if (objectClone.isChanged) 
            if (myObj.className) {
              objectClone.className = myObj.className
              if (typeof ClassMetaData[myObj.className].key == 'object' && ClassMetaData[myObj.className].key > 0) 
                for (var i = 0; i < ClassMetaData[myObj.className].key.length; i++) {
                  objectClone[ClassMetaData[myObj.className].key[i]] = myObj[ClassMetaData[myObj.className].key[i]];
                }
              else 
                objectClone[ClassMetaData[myObj.className].key] = myObj[ClassMetaData[myObj.className].key];
            }
        }
      }

      return objectClone;
    },

    /**
     * Full clone Function
     */
    fullClone: function(myObj){
      if (myObj == null)
        return null;

      if ('object' !== typeof myObj) {
        return myObj;
      }
      var objectClone = new myObj.constructor();
      
      if (myObj.map && typeof myObj.map == 'object') {
        for (var i = 0; i < myObj.items.length; i++) {
          objectClone.add(myObj.keys[i], this.fullClone(myObj.items[i]));
          var a = 1;
        }
      } else {
        if (myObj.dateFormat) {
          objectClone = myObj;
        } else {
          for (var property in myObj) {
            if (property != 'registeredPanels') {
              if (typeof myObj[property] == 'object') 
                objectClone[property] = this.fullClone(myObj[property]);
              else 
                objectClone[property] = myObj[property];
            }
          }
        }
      }
      return objectClone;
    },
    
    IsNumeric: function (sText){
      var ValidChars = "0123456789.";
      var IsNumber = true;
      var Char;
      for (var i = 0; i < sText.length && IsNumber == true; i++) {
        Char = sText.charAt(i);
        if (ValidChars.indexOf(Char) == -1) {
          IsNumber = false;
        }
      }
      return IsNumber;
    }
});
