/**
 * @author SeanZ
 */
/** setting the milliseconds to zero in xdatetime field output */
Ext.override(Ext.ux.form.DateTime, {
  timeFormat:'H:i:s',
  getValue : function() {
    if (this.dateValue) {
      // create new instance of date with milliseconds set to zero
      var o = new Date(this.dateValue);
      o.setMilliseconds(0);
      return o;
    }
    return '';
  },
  isValid: function() {
    if (Ext.isEmpty(this.getValue()) && this.allowBlank===false) {
      return false;
    }
    return this.df.isValid() && this.tf.isValid();
  }
});

/** This override should not be applied in all cases it is a special extension when using
 * Jaffa style criteria objects to apply filters.
 *
 * It also been extended to support getting a filterFieldName from Record metadata in cases
 * where the dataIndex name and the name to use on the search are different.
 */
Ext.override(Ext.ux.grid.GridFilters, {
  onBeforeLoad : function(store, options) {
    var prms = {};
    var pn = this.grid.getStore().paramNames;
    var bp = this.grid.getStore().baseParams;//shortcut
    if (bp)
      Ext.apply(prms, bp);
    if (options.params) {
      if (options.params[pn.start]) {
        prms[pn.start] = options.params[pn.start];
      }
      if (options.params[pn.limit]) {
        prms[pn.limit] = options.params[pn.limit];
      }
    }
    var flts = this.getFilterData();
    if (flts && flts.length > 0) {
      for (var i = 0; i < flts.length; i++) {
        var meta = this.grid.store.reader.recordType.getField(flts[i].field);
        var name = meta.filterFieldName || flts[i].field;
        if(bp[name]) {
          console.error("Can't apply this filter to ",name);
          continue;
        }
        //console.info("Filter on field ",flts[i].field,'(aka ',name,"), type=",flts[i].data.type,typeof flts[i].data.value);
        var dataType = flts[i].data.type;
        var value = (dataType=='date'? Date.parseDate(flts[i].data.value,Ext.ux.grid.filter.DateFilter.prototype.dateFormat) : flts[i].data.value);
        var op = flts[i].data.comparison;

        if (dataType == 'numeric' || dataType == 'int' || dataType == 'long' ||
            dataType == 'float' || dataType == 'double' || dataType == 'date')
        {
          if (op == 'gt'|| op == 'after') {
            if (prms[name] && prms[name].operator == 'SmallerThanOrEquals') {
              prms[name] = {
                operator : 'Between',
                values : [value, prms[name].values[0]]
              };
            } else {
              prms[name] = {
                operator : 'GreaterThanOrEquals',
                values : [value]
              };
            }
          } else if (op == 'lt' || op == 'before') {
            if (prms[name] && prms[name].operator == 'GreaterThanOrEquals') {
              prms[name] = {
                operator : 'Between',
                values : [prms[name].values[0], value]
              };
            } else {
              prms[name] = {
                operator : 'SmallerThanOrEquals',
                values : [value]
              };
            }
          } else if (op == 'eq' || op == 'on') {
            prms[name] = {
              values : [value]
            };
          }
        } else if (dataType == 'string') {
          prms[name] = {
            operator : op?op:'BeginsWith',
            values : [value]
          };
        } else if (dataType == 'list') {
          var meta = this.grid.metaModel.get(name);
          var values = value;
          if(meta && meta.inList) {
            for(var n=0; n<values.length; n++) {
              for(var v in meta.inList) {
                if(meta.inList[v] == values[n]){
                  values[n] = v
                  break;
                }
              }
            }
          }
          prms[name] = {
            operator : 'In',
            values : values
          };
        } else if (dataType == 'boolean') {
          if (value) {
            prms[name] = {values: ['True']};
          } else {
            prms[name] = {values: ['False']};
          }
        }else{
          prms[name]=value;
        }
      }
    }
    options.params = prms;
  },

  /** private **/
  reload: function(){
    if(this.local){
      this.grid.store.clearFilter(true);
      this.grid.store.filterBy(this.getRecordFilter());
    } else {
      this.deferredUpdate.cancel();
      var store = this.grid.store;
      if(this.toolbar){
        var start = this.toolbar.paramNames.start;
        if(store.lastOptions && store.lastOptions.params && store.lastOptions.params[start])
          store.lastOptions.params[start] = 0;
      }
      // reset the first record when reload to make sure it starts from the first record
      // reload() is called by either start of the grid or load after grid filter is changed.
      if(store.lastOptions && store.lastOptions.params){
        store.lastOptions.params.firstRecord=0;
      }
      store.reload();
    }
  },
  //Overrode so that the filter menu is disabled when the column in not sortable.
  onMenu: function (filterMenu) {
    var filter = this.getMenuFilter();
    if (filter) {
        this.menu.menu = filter.menu;
        this.menu.setChecked(filter.active, false);
        var view = this.grid.view;
        var menuItems = view.hmenu.items;
        var cm = view.cm;
        //disabledDynamically is used by criteria panel to disable filters on columns that have criteria set
        this.menu.setDisabled(filter.disabled === true || cm.config[view.hdCtxIndex].filter === false || filter.disabledDynamically);
    }
    this.menu.setVisible(filter !== undefined);
    this.sep.setVisible(filter !== undefined);
  }
});

/** Extended to support new config option 'editorStyle' which will be
 * additional style to the TextField, for example to force uppercase
 */
if (!Ext.ux.grid.filter.StringFilter.prototype._jaffa_orig_init)
  Ext.ux.grid.filter.StringFilter.prototype._jaffa_orig_init = Ext.ux.grid.filter.StringFilter.prototype.init;

Ext.override(Ext.ux.grid.filter.StringFilter, {
	
	beginsWithFiltersMenu:	'Begins With',
	endsWithFiltersMenu:	'Ends With',
	likeFiltersMenu:		'Like',
	equalsFiltersMenu:		'Equals',
	
  init: function(config) {
    config = config || {};
    Ext.applyIf(config, {
      itemId: 'bw'
      ,style: this.editorStyle
      ,iconCls: this.icon
    });

    Ext.applyIf(config, {
        enableKeyEvents: true,
        listeners: {
            scope: this,
            keyup: this.onInputKeyUp
        }
    });

    var tf ={
      ref: 'valueField',
      xtype: 'textfield',
      width: 150,
      bodySyle: 'padding: 0 0 0 5;'
    };

    Ext.applyIf(tf, config);

    this.inputItem = new Ext.Container({
      width: 198,
      layout: 'hbox',
      layoutConfig:{defaultMargins: '0 5 0 0'},
      items: [{
        ref: 'operatorField',
        xtype: 'button',
        text: ' ',
        iconCls: 'x-sf-beginswith',
        width: 40,
        operator: 'BeginsWith',
        menu:{
          allowOtherMenus: true,
          items :[
            new Ext.menu.Item({
              iconCls: 'x-sf-beginswith',
              text: this.beginsWithFiltersMenu,
              scope: this,
              handler: function(menuItem, e){
                menuItem.parentMenu.ownerCt.operator = 'BeginsWith';
                menuItem.parentMenu.ownerCt.setIconClass('x-sf-beginswith');
                this.updateTask.delay(this.updateBuffer);
              }
            }),
            new Ext.menu.Item({
              iconCls: 'x-sf-endwith',
              text: this.endsWithFiltersMenu,
              scope: this,
              handler: function(menuItem, e){
                menuItem.parentMenu.ownerCt.operator = 'EndWith';
                menuItem.parentMenu.ownerCt.setIconClass('x-sf-endwith');
                this.updateTask.delay(this.updateBuffer);
              }
            }),
            new Ext.menu.Item({
              iconCls: 'x-sf-like',
              text: this.likeFiltersMenu,
              scope: this,
              handler: function(menuItem, e){
                menuItem.parentMenu.ownerCt.operator = 'Like';
                menuItem.parentMenu.ownerCt.setIconClass('x-sf-like');
                this.updateTask.delay(this.updateBuffer);
              }
            }),
            new Ext.menu.Item({
              iconCls: 'x-sf-equals',
              text: this.equalsFiltersMenu,
              scope: this,
              handler: function(menuItem, e){
                menuItem.parentMenu.ownerCt.operator = 'Equals';
                menuItem.parentMenu.ownerCt.setIconClass('x-sf-equals');
                this.updateTask.delay(this.updateBuffer);
              }
            })
          ]        }
      }, tf],
      getValue: function (){
        return this.valueField.getValue();
      }
    });

    this.menu.add(this.inputItem);

    this.menu.cls = 'x-no-bg';
    this.updateTask = new Ext.util.DelayedTask(this.fireUpdate, this);
  },

  getSerialArgs : function () {
      return {type: 'string', comparison: this.getOperator(), value: this.getValue()};
  },
  setValue: function(v){
    if (typeof(v) == "string") {
      this.inputItem.valueField.setValue(v);
    } else {
      if (v.value) this.inputItem.valueField.setValue(v.value);
      if (v.operator) this.setOperator(v.operator);
    }
  },
  getValue: function (){
    return this.inputItem.valueField.getValue();
  },
  getOperator: function(){
    if (this.inputItem.operatorField.operator){
      return this.inputItem.operatorField.operator;
    } else {
      return 'BeginsWith';
    }
  },
  setOperator: function(v){
    var of = this.inputItem.operatorField;
    if (v=="EndWith"){
      of.operator = 'EndWith';
      of.setIconClass('x-sf-endwith');
    } else if (v=="Like"){
      of.operator = 'Like';
      of.setIconClass('x-sf-like');
    } else if (v=="Equals"){
      of.operator = 'Equals';
      of.setIconClass('x-sf-equals');
    } else {
      of.operator = 'BeginsWith';
      of.setIconClass('x-sf-beginswith');
    }
  },
  validateRecord : function(record){
    var val = record.get(this.dataIndex);
    if(typeof val != 'string') {
      return (this.getValue().length === 0);
    }
    var op = this.getOperator();
    if (op=='EndWith'){
      return val.toLowerCase().indexOf(this.getValue().toLowerCase(), val.toLowerCase().length - this.getValue().toLowerCase().length) != -1;
    } else if (op=='BeginsWith'){
      return val.toLowerCase().indexOf(this.getValue().toLowerCase()) == 0;
    } else if (op=='Equals'){
      return val.toLowerCase()==this.getValue().toLowerCase();
    } else {
      return val.toLowerCase().indexOf(this.getValue().toLowerCase()) > -1;
    }
  }

});


/**
 * Override numeric filter icons to make "greater than" "greater than or equals"
 * and "less than" "less than or equals"
 */
Ext.override(Ext.ux.grid.filter.NumericFilter, {
  iconCls : {
    gt : 'x-rangemenu-gt',
    lt : 'x-rangemenu-lt',
    eq : 'ux-rangemenu-eq'
  },
  validateRecord : function (record) {
        var val = record.get(this.dataIndex),
            values = this.getValue();
        if (values.eq !== undefined && val != values.eq) {
            return false;
        }
        if (values.lt !== undefined && val > values.lt) {
            return false;
        }
        if (values.gt !== undefined && val < values.gt) {
            return false;
        }
        return true;
    }  
});

/**
 * @class Jaffa.form.CurrencyField
 * @extends Ext.ux.grid.filter.NumericFilter
 * Override fieldCls as Jaffa.form.CurrencyField to allow 3 decimal precision on filter
 */
Ext.ux.grid.filter.CurrencyFilter=Ext.extend(Ext.ux.grid.filter.NumericFilter, {
  fieldCls : Jaffa.form.CurrencyField
});

/** Extended to support new config option 'editorStyle' which will be
 * additional style to the TextField, for example to force uppercase
 */
Ext.override(Ext.ux.grid.filter.DateFilter, {
  //Override date filter text to imply filters are inclusive.
  afterText : 'From',
  beforeText : 'To',

  setValue: function(value) {
    for(var key in this.dates) {
      //-- Patched Code
      if( value[key] && typeof value[key] == 'string') {
        value[key] = Date.parseDate(value[key],"Y-m-d\\TH:i:s"); // Note: this is the format used by Ext.util.JSON.encodeDate
        if(value[key]===undefined ||  value[key] == "Invalid Date")
          delete value[key];
      }
      //-- End Patch
      if(value[key]) {
        this.dates[key].menu.picker.setValue(value[key]);
        this.dates[key].setChecked(true);
      } else {
        this.dates[key].setChecked(false);
      }
    }
  }
});


/** This adds back tab behavour to this widget so it plays nicely when
 * use on a form or inside an editable grid
 */
Ext.override(Ext.ux.form.LovCombo, {
  // {{{
  /**
   * Route tab key to onRealBlur
   * @private
   */
  initEvents:function() {
    Ext.ux.form.LovCombo.superclass.initEvents.apply(this, arguments);

    this.keyNav.tab = function(e){
      this.mimicing = false;
      Ext.get(Ext.isIE ? document.body : document).un("mousedown", this.mimicBlur);
      if(this.monitorTab){
          this.el.un("specialkey", this.checkTab, this);
      }
      this.beforeBlur();
      this.wrap.removeClass('x-trigger-wrap-focus');
      this.onRealBlur(e);
    }

  } // eo function initEvents
  // }}}
});


/**
 * Remove the emptyText from the elements of the NumericFilter.
 */
delete Ext.ux.grid.filter.NumericFilter.prototype.menuItemCfgs.emptyText;

//This override checks to see if the check column is disabled
Ext.ux.grid.CheckColumn.prototype.onMouseDown = function(e,t) {
  if(!this.disabled && Ext.fly(t).hasClass(this.createId())){
      e.stopEvent();
      var index = this.grid.getView().findRowIndex(t);
      var record = this.grid.store.getAt(index);
      record.set(this.dataIndex, !record.data[this.dataIndex]);
  }
}

/** This default is set so that the extention that added the
 *  editableCss class to a grid cell will do this when the CheckBox editor is used
 */
Ext.ux.grid.CheckColumn.prototype.isEditable=true;

/**
 * This adds support for resizing and removing columns in RowEditor. This change was copied from the
 * community
 * <Link> http://www.sencha.com/forum/showthread.php?92140-OPEN-575-roweditor-column-drag-drop-resize-problems/page3
 */
Ext.override(Ext.ux.grid.RowEditor, {
  layout: 'columnwithmargins',
  init: function(grid){
    this.grid = grid;
    this.ownerCt = grid;
    if(this.clicksToEdit === 2){
      grid.on('rowdblclick', this.onRowDblClick, this);
    }else{
      grid.on('rowclick', this.onRowClick, this);
      if(Ext.isIE){
        grid.on('rowdblclick', this.onRowDblClick, this);
      }
    }

    // stopEditing without saving when a record is removed from Store.
    grid.getStore().on('remove', function() {
      this.stopEditing(false);
    },this);

    grid.on({
      scope: this,
      keydown: this.onGridKey,
      columnresize: this.verifyLayout,
      columnmove: this.columnMove,
      reconfigure: this.refreshFields,
      beforedestroy : this.beforedestroy,
      destroy : this.destroy,
      bodyscroll: {
        buffer: 250,
        fn: this.positionButtons
      }
    });
    grid.getColumnModel().on('hiddenchange', this.verifyLayout, this, {delay:1});
    grid.on('resize', this.verifyLayout, this, {delay: 1});
    grid.getView().on('refresh', this.stopEditing.createDelegate(this, []));
  },
  initFields: function(){
    var cm = this.grid.getColumnModel(), pm = Ext.layout.ContainerLayout.prototype.parseMargins;
    this.removeAll(false);
    if(this.displayFields){
      Ext.each(this.displayFields, function(){
        this.destroy();
      });
    }
    for(var i = 0, len = cm.getColumnCount(); i < len; i++){
      var c = cm.getColumnAt(i),
      ed = c.getEditor();
      if(!ed){
        if(c.displayEditor){
          ed = c.displayEditor;
        } else{
          ed = new Ext.form.DisplayField({html:'&nbsp;',style:{minHeight:'1px'}});
          if(!this.displayFields)
            this.displayFields = [];
          this.displayFields.push(ed);
        }
      } else if(ed.rendered){
        this.getLayout().configureItem(ed);
      }
      if(i == 0){
        ed.margins = pm('0 1 2 1');
      } else if(i == len - 1){
        ed.margins = pm('0 0 2 1');
      } else{
        if (Ext.isIE) {
          ed.margins = pm('0 0 2 0');
        }
        else {
          ed.margins = pm('0 1 2 0');
        }
      }
      ed.setWidth(cm.getColumnWidth(i));
      ed.column = c;
      if(ed.ownerCt !== this){
        ed.on('focus', this.ensureVisible, this);
        ed.on('specialkey', this.onKey, this);
      }
      this.insert(i, ed);
    }
    this.initialized = true;
  },
  columnMove: function(oldIndex, newIndex) {
    if(this.initialized){
      this.items.insert(newIndex, this.items.removeAt(oldIndex));
      var layout = this.getLayout();
      var targetEl = layout.innerCt ? layout.innerCt : this.getLayoutTarget();
      var node = targetEl.dom.childNodes.item(oldIndex);
      var refNode = targetEl.dom.childNodes.item(newIndex);
      targetEl.dom.insertBefore(node, refNode);
    }
  }
});

//Overload label on upload dialog window
Ext.ux.UploadDialog.Dialog.prototype.i18n.progress_waiting_text='Please select file(s) for upload.';

/**
 * @class Ext.form.TextField
 */
Ext.override(Ext.form.TextField, {
  displayRenderer: Jaffa.form.textRenderer
});

