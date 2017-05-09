Ext.override(Ext.layout.BorderLayout,{
	onLayout: function(ct, target){	  
		
        var collapsed, i, c, pos, items = ct.items.items, len = items.length;
        if(!this.rendered){
            collapsed = [];
            for(i = 0; i < len; i++) {
                c = items[i];
                pos = c.region;
				// Retrieving the item which was set in loadExtJS.jsp using "sessionStorage" for Arabic Localization
					if(c.region=='west'){
						pos = 'east';
					}else if(c.region=='east'){
						pos = 'west';
					}
                if(c.collapsed){
                    collapsed.push(c);
                }
                c.collapsed = false;
                if(!c.rendered){
                    c.render(target, i);
                    c.getPositionEl().addClass('x-border-panel');
                }
                this[pos] = pos != 'center' && c.split ?
                    new Ext.layout.BorderLayout.SplitRegion(this, c.initialConfig, pos) :
                    new Ext.layout.BorderLayout.Region(this, c.initialConfig, pos);
                this[pos].render(target, c);
            }
            this.rendered = true;
        }

        var size = this.getLayoutTargetSize();
        if(size.width < 20 || size.height < 20){ 
            if(collapsed){
                this.restoreCollapsed = collapsed;
            }
            return;
        }else if(this.restoreCollapsed){
            collapsed = this.restoreCollapsed;
            delete this.restoreCollapsed;
        }

        var w = size.width, h = size.height,
            centerW = w, centerH = h, centerY = 0, centerX = 0,
            n = this.north, s = this.south, west = this.west, e = this.east, c = this.center,
            b, m, totalWidth, totalHeight;
        if(!c && Ext.layout.BorderLayout.WARN !== false){
            throw 'No center region defined in BorderLayout ' + ct.id;
        }

        if(n && n.isVisible()){
            b = n.getSize();
            m = n.getMargins();
            b.width = w - (m.left+m.right);
            b.x = m.left;
            b.y = m.top;
            centerY = b.height + b.y + m.bottom;
            centerH -= centerY;
            n.applyLayout(b);
        }
        if(s && s.isVisible()){
            b = s.getSize();
            m = s.getMargins();
            b.width = w - (m.left+m.right);
            b.x = m.left;
            totalHeight = (b.height + m.top + m.bottom);
            b.y = h - totalHeight + m.top;
            centerH -= totalHeight;
            s.applyLayout(b);
        }
        if(west && west.isVisible()){
            b = west.getSize();
            m = west.getMargins();
            b.height = centerH - (m.top+m.bottom);
            b.x = m.left;
            b.y = centerY + m.top;
            totalWidth = (b.width + m.left + m.right);
            centerX += totalWidth;
            centerW -= totalWidth;
            west.applyLayout(b);
        }
        if(e && e.isVisible()){
            b = e.getSize();
            m = e.getMargins();
            b.height = centerH - (m.top+m.bottom);
            totalWidth = (b.width + m.left + m.right);
            b.x = w - totalWidth + m.left;
            b.y = centerY + m.top;
            centerW -= totalWidth;
            e.applyLayout(b);
        }
        if(c){
            m = c.getMargins();
            var centerBox = {
                x: centerX + m.left,
                y: centerY + m.top,
                width: centerW - (m.left+m.right),
                height: centerH - (m.top+m.bottom)
            };
            c.applyLayout(centerBox);
        }
        if(collapsed){
            for(i = 0, len = collapsed.length; i < len; i++){
                collapsed[i].collapse(false);
            }
        }
        if(Ext.isIE9m && Ext.isStrict){ 
            target.repaint();
        }
        
        if (i = target.getStyle('overflow') && i != 'hidden' && !this.adjustmentPass) {
            var ts = this.getLayoutTargetSize();
            if (ts.width != size.width || ts.height != size.height){
                this.adjustmentPass = true;
                this.onLayout(ct, target);
            }
        }
        delete this.adjustmentPass;
	}

});

/**
*	This override Combo grid class will be called when the FinderComboGrid resizes
*	ex: PartMaintenance Screen -> Category Instrument Code field
*/
Ext.override(Jaffa.form.FinderComboGrid,{
	renderGrid: function() {
        // mask the current element
        var comboGridElement = this;
        comboGridElement.disable();

        // create the grid
        comboGridElement.grid = new Jaffa.form.FinderGridPanel({
            id: comboGridElement.id + '_grid',
            meta: comboGridElement.meta,
            columns : comboGridElement.gridColumns,
            listeners: {
                rowdblclick: function(gridElement, rowIndex, eventObject) {
                    var gridRec = gridElement.store.data.items[rowIndex];
                    var record = new Ext.data.Record(gridRec.data);
                    if(!record.json) record.json = gridRec.json;
                    var originalValue = comboGridElement.getValue();
                    comboGridElement.setValue(record);
                    var newValue = comboGridElement.getValue();
                    comboGridElement.fireEvent('select', comboGridElement, record, rowIndex);
                    comboGridElement.fireEvent('rowdblclick', comboGridElement, record, rowIndex);
                    if (originalValue != newValue) {
                        comboGridElement.fireEvent('change', comboGridElement, newValue, originalValue, true);
                    }
                    comboGridElement.window.close();
                    comboGridElement.completeEditing();
                }
            }

        });
        comboGridElement.grid.getStore().on('load', function() {
            // detect whether any filter menu is open
            var filtersInFocus = false;
            comboGridElement.grid.filters.filters.each(function(flt) {
              if (! flt.menu.hidden) {
                filtersInFocus = true;
                return false;
              }
            });
            // escape focus when any column filter menu is open
            if (filtersInFocus) return;
            // set focus on the grid
            this.focus();
            if (this.getStore().getCount() > 0) {
                this.getSelectionModel().selectFirstRow();
                try {
                    this.getView().focusRow(0);
                } catch(ex) {}
            }
        }, comboGridElement.grid);

        // Determine baseParams
        var baseParams = Ext.apply({}, comboGridElement.store.baseParams);
        comboGridElement.fireEvent('beforeautoselectquery', comboGridElement, baseParams, false);

        comboGridElement.setGridFilters(comboGridElement, baseParams);

         // Apply baseParams to the Store
         if(comboGridElement.staticBaseParams)  Ext.apply(comboGridElement.grid.store.baseParams,comboGridElement.staticBaseParams);
         Ext.applyIf(comboGridElement.grid.store.baseParams, baseParams);

        // create a window with the grid
        comboGridElement.window = new Ext.ModalWindow({
            id: comboGridElement.grid.id+'_wndw',
            maximizable: true,
            autoHeight: true,
            width: comboGridElement.grid.width + 20,
            items: comboGridElement.grid,
			//Changed the resizeHandles as 'W' for localization.
            resizeHandles: 'w',
            keys: [{
                key: [Ext.EventObject.UP, Ext.EventObject.DOWN],
                fn: function() {
                    comboGridElement.grid.getView().focusEl.focus();
                }
            }, {
                key: [Ext.EventObject.ENTER],
                fn: function(keyValue, evt) {
                    // find the row index
                    evt.stopEvent();
                    var rowIdx = comboGridElement.grid.getStore().indexOf(comboGridElement.grid.getSelectionModel().getSelected());
                    comboGridElement.grid.fireEvent('rowdblclick', comboGridElement.grid, rowIdx);
                }
            }],
            listeners: {
                close: function(panel) {
                    comboGridElement.enable();
                    comboGridElement.focus(true);
                    comboGridElement.flagGridIsOn = false;
                    // console.debug('closeEvent', (new Date()).getTime());
                    comboGridElement.popupGridCloseTime = new Date();
                },
                show: function (c) {
                  // Ensure that z-index of the window is not lower than the z-index of the combo, else the combo overlaps the window.
                  // This issue has been observed when the finderComboGrid is used in an EditableGrid.
                  var comboZIndex = comboGridElement.findZIndex();
                  if (comboZIndex) {
                    var windowZIndex = c.findZIndex();
                    if (!windowZIndex || windowZIndex < comboZIndex)
                      c.el.setStyle('z-index', comboZIndex);
                  }
                }
            }
        });
        comboGridElement.window.show();
        this.flagGridIsOn = true;
        comboGridElement.window.on('resize', function(wndw, width, height) {
           comboGridElement.grid.setWidth(width-20);
        }, comboGridElement.window);
        comboGridElement.grid.setWidth(comboGridElement.window.getSize().width-20);

        // add a clear filter button
        var bbar = comboGridElement.grid.getBottomToolbar();
        bbar.addFill();
        bbar.addButton({
            text: Labels.get('label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters'),
            scope: comboGridElement.grid,
            handler: function () {
               comboGridElement.clearFilters.call(comboGridElement);
            }
        });

        // load the grid
        comboGridElement.grid.store.load();
        comboGridElement.grid.focus();
        comboGridElement.onFocus=true; // added so that the select event can trigger change listener

        // set the 'popupAlways' property to true to avoid the unnecessary load of the combo store
        comboGridElement.popupAlways = true;
        
    }
});
// overridden for combo box drop down alignment for localization

Ext.override(Ext.form.ComboBox , {
    
    // changed value for localization 
	listAlign : 'tr-br?',    

  // changed value for localization
	minListWidth : 250,    

    // combo width for localization
	width : 250,       

    restrictHeight : function(){
        this.innerList.dom.style.height = '';
        var inner = this.innerList.dom,
            pad = this.list.getFrameWidth('tb') + (this.resizable ? this.handleHeight : 0) + this.assetHeight,
            h = Math.max(inner.clientHeight, inner.offsetHeight, inner.scrollHeight),
            ha = this.getPosition()[1]-Ext.getBody().getScroll().top,
            hb = Ext.lib.Dom.getViewHeight()-ha-this.getSize().height,
            space = Math.max(ha, hb, this.minListHeight || 0)-this.list.shadowOffset-pad-5;
		
		// included for drop downs where drop down list has only one value
		if(inner.childNodes.length == 1){
			h = 100;
		}else{
			h = Math.min(h, space, this.maxHeight);
		} 
        this.innerList.setHeight(h);
        this.list.beginUpdate();
        this.list.setHeight(h+pad);
        this.list.alignTo.apply(this.list, [this.el].concat(this.listAlign));
        this.list.endUpdate();
    }
});

Ext.reg('combo', Ext.form.ComboBox);

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
Ext.override(Ext.ux.grid.RowEditor,{
	floating: true,
    shadow: false,
    layout: 'hbox',
    cls: 'x-small-editor',
    buttonAlign: 'center',
    baseCls: 'x-row-editor',
    elements: 'header,footer,body',
    frameWidth: 5,
    buttonPad: 3,
    clicksToEdit: 'auto',
    monitorValid: true,
    focusDelay: 250,
    errorSummary: true,

    defaults: {
        normalWidth: true
    },

    initComponent: function(){
        Ext.ux.grid.RowEditor.superclass.initComponent.call(this);
        this.addEvents(
            /**
             * @event beforeedit
             * Fired before the row editor is activated.
             * If the listener returns <tt>false</tt> the editor will not be activated.
             * @param {Ext.ux.grid.RowEditor} roweditor This object
             * @param {Number} rowIndex The rowIndex of the row just edited
             */
            'beforeedit',
            /**
             * @event canceledit
             * Fired when the editor is cancelled.
             * @param {Ext.ux.grid.RowEditor} roweditor This object
             * @param {Boolean} forced True if the cancel button is pressed, false is the editor was invalid.
             */
            'canceledit',
            /**
             * @event validateedit
             * Fired after a row is edited and passes validation.
             * If the listener returns <tt>false</tt> changes to the record will not be set.
             * @param {Ext.ux.grid.RowEditor} roweditor This object
             * @param {Object} changes Object with changes made to the record.
             * @param {Ext.data.Record} r The Record that was edited.
             * @param {Number} rowIndex The rowIndex of the row just edited
             */
            'validateedit',
            /**
             * @event afteredit
             * Fired after a row is edited and passes validation.  This event is fired
             * after the store's update event is fired with this edit.
             * @param {Ext.ux.grid.RowEditor} roweditor This object
             * @param {Object} changes Object with changes made to the record.
             * @param {Ext.data.Record} r The Record that was edited.
             * @param {Number} rowIndex The rowIndex of the row just edited
             */
            'afteredit'
        );
    },

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
            columnmove: this.refreshFields,
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

    beforedestroy: function() {
        this.stopMonitoring();
        this.grid.getStore().un('remove', this.onStoreRemove, this);
        this.stopEditing(false);
        Ext.destroy(this.btns, this.tooltip);
    },

    refreshFields: function(){
        this.initFields();
        this.verifyLayout();
    },

    isDirty: function(){
        var dirty;
        this.items.each(function(f){
            if(String(this.values[f.id]) !== String(f.getValue())){
                dirty = true;
                return false;
            }
        }, this);
        return dirty;
    },

    startEditing: function(rowIndex, doFocus){
        if(this.editing && this.isDirty()){
            this.showTooltip(this.commitChangesText);
            return;
        }
        if(Ext.isObject(rowIndex)){
            rowIndex = this.grid.getStore().indexOf(rowIndex);
        }
        if(this.fireEvent('beforeedit', this, rowIndex) !== false){
            this.editing = true;
            var g = this.grid, view = g.getView(),
                row = view.getRow(rowIndex),
                record = g.store.getAt(rowIndex);

            this.record = record;
            this.rowIndex = rowIndex;
            this.values = {};
            if(!this.rendered){
                this.render(view.getEditorParent());
            }
            var w = Ext.fly(row).getWidth();
            this.setSize(w);
            if(!this.initialized){
                this.initFields();
            }
            var cm = g.getColumnModel(), fields = this.items.items, f, val;
            for(var i = 0, len = cm.getColumnCount(); i < len; i++){
                val = this.preEditValue(record, cm.getDataIndex(i));
                f = fields[i];
                f.setValue(val);
                this.values[f.id] = Ext.isEmpty(val) ? '' : val;
            }
            this.verifyLayout(true);
            if(!this.isVisible()){
                this.setPagePosition(Ext.fly(row).getXY());
            } else{
                this.el.setXY(Ext.fly(row).getXY(), {duration:0.15});
            }
            if(!this.isVisible()){
                this.show().doLayout();
            }
            if(doFocus !== false){
                this.doFocus.defer(this.focusDelay, this);
            }
        }
    },

    stopEditing : function(saveChanges){
        this.editing = false;
        if(!this.isVisible()){
            return;
        }
        if(saveChanges === false || !this.isValid()){
            this.hide();
            this.fireEvent('canceledit', this, saveChanges === false);
            return;
        }
        var changes = {},
            r = this.record,
            hasChange = false,
            cm = this.grid.colModel,
            fields = this.items.items;
        for(var i = 0, len = cm.getColumnCount(); i < len; i++){
            if(!cm.isHidden(i)){
                var dindex = cm.getDataIndex(i);
                if(!Ext.isEmpty(dindex)){
                    var oldValue = r.data[dindex],
                        value = this.postEditValue(fields[i].getValue(), oldValue, r, dindex);
                    if(String(oldValue) !== String(value)){
                        changes[dindex] = value;
                        hasChange = true;
                    }
                }
            }
        }
        if(hasChange && this.fireEvent('validateedit', this, changes, r, this.rowIndex) !== false){
            r.beginEdit();
            Ext.iterate(changes, function(name, value){
                r.set(name, value);
            });
            r.endEdit();
            this.fireEvent('afteredit', this, changes, r, this.rowIndex);
        } else {
            this.fireEvent('canceledit', this, false);
        }
        this.hide();
    },

    verifyLayout: function(force){
        if(this.el && (this.isVisible() || force === true)){
            var row = this.grid.getView().getRow(this.rowIndex);
            this.setSize(Ext.fly(row).getWidth(), Ext.isIE ? Ext.fly(row).getHeight() + 9 : undefined);
            var cm = this.grid.colModel, fields = this.items.items;
            for(var i = 0, len = cm.getColumnCount(); i < len; i++){
                if(!cm.isHidden(i)){
                    var adjust = 0;
                    if(i === (len - 1)){
                        adjust += 3; // outer padding
                    } else{
                        adjust += 1;
                    }
                    fields[i].show();
                    var width = cm.getColumnWidth(i) - adjust;
                    fields[i].setWidth(width);
                    fields[i].width = width;
                } else{
                    fields[i].hide();
                }
            }
            this.doLayout();
            this.positionButtons();
        }
    },

    slideHide : function(){
        this.hide();
    },

    initFields: function(){
        var cm = this.grid.getColumnModel(), pm = Ext.layout.ContainerLayout.prototype.parseMargins;
        this.removeAll(false);
        for(var i = 0, len = cm.getColumnCount(); i < len; i++){
            var c = cm.getColumnAt(i),
                ed = c.getEditor();
            if(!ed){
                ed = c.displayEditor || new Ext.form.DisplayField();
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

    onKey: function(f, e){
        if(e.getKey() === e.ENTER){
            this.stopEditing(true);
            e.stopPropagation();
        }
    },

    onGridKey: function(e){
        if(e.getKey() === e.ENTER && !this.isVisible()){
            var r = this.grid.getSelectionModel().getSelected();
            if(r){
                var index = this.grid.store.indexOf(r);
                this.startEditing(index);
                e.stopPropagation();
            }
        }
    },

    ensureVisible: function(editor){
        if(this.isVisible()){
             this.grid.getView().ensureVisible(this.rowIndex, this.grid.colModel.getIndexById(editor.column.id), true);
        }
    },

    onRowClick: function(g, rowIndex, e){
        if(this.clicksToEdit == 'auto'){
            var li = this.lastClickIndex;
            this.lastClickIndex = rowIndex;
            if(li != rowIndex && !this.isVisible()){
                return;
            }
        }
        this.startEditing(rowIndex, false);
        this.doFocus.defer(this.focusDelay, this, [e.getPoint()]);
    },

    onRowDblClick: function(g, rowIndex, e){
        this.startEditing(rowIndex, false);
        this.doFocus.defer(this.focusDelay, this, [e.getPoint()]);
    },

    onRender: function(){
        Ext.ux.grid.RowEditor.superclass.onRender.apply(this, arguments);
        this.el.swallowEvent(['keydown', 'keyup', 'keypress']);
        this.btns = new Ext.Panel({
            baseCls: 'x-plain',
            cls: 'x-btns',
            elements:'body',
            layout: 'table',
            width: (this.minButtonWidth * 2) + (this.frameWidth * 2) + (this.buttonPad * 4), // width must be specified for IE
            items: [{
                ref: 'saveBtn',
                itemId: 'saveBtn',
                xtype: 'button',
                text: this.saveText,
                width: this.minButtonWidth,
                handler: this.stopEditing.createDelegate(this, [true])
            }, {
                xtype: 'button',
                text: this.cancelText,
                width: this.minButtonWidth,
                handler: this.stopEditing.createDelegate(this, [false])
            }]
        });
        this.btns.render(this.bwrap);
    },

    afterRender: function(){
        Ext.ux.grid.RowEditor.superclass.afterRender.apply(this, arguments);
        this.positionButtons();
        if(this.monitorValid){
            this.startMonitoring();
        }
    },

    onShow: function(){
        if(this.monitorValid){
            this.startMonitoring();
        }
        Ext.ux.grid.RowEditor.superclass.onShow.apply(this, arguments);
    },

    onHide: function(){
        Ext.ux.grid.RowEditor.superclass.onHide.apply(this, arguments);
        this.stopMonitoring();
        this.grid.getView().focusRow(this.rowIndex);
    },

    positionButtons: function(){
        if(this.btns){
            var g = this.grid,
                h = this.el.dom.clientHeight,
                view = g.getView(),
                scroll = view.scroller.dom.scrollLeft,
                bw = this.btns.getWidth(),
                width = Math.min(g.getWidth(), g.getColumnModel().getTotalWidth());

            this.btns.el.shift({left: (width/2)-(bw/2)+scroll, top: h - 2, stopFx: true, duration:0.2});
			
			this.btns.el.shift({right: (width/2)-(bw/2)+scroll, top: h - 2, stopFx: true, duration:0.2});
        }
    },

    // private
    preEditValue : function(r, field){
        var value = r.data[field];
        return this.autoEncode && typeof value === 'string' ? Ext.util.Format.htmlDecode(value) : value;
    },

    // private
    postEditValue : function(value, originalValue, r, field){
        return this.autoEncode && typeof value == 'string' ? Ext.util.Format.htmlEncode(value) : value;
    },

    doFocus: function(pt){
        if(this.isVisible()){
            var index = 0,
                cm = this.grid.getColumnModel(),
                c;
            if(pt){
                index = this.getTargetColumnIndex(pt);
            }
            for(var i = index||0, len = cm.getColumnCount(); i < len; i++){
                c = cm.getColumnAt(i);
                if(!c.hidden && c.getEditor()){
                    c.getEditor().focus();
                    break;
                }
            }
        }
    },

    getTargetColumnIndex: function(pt){
        var grid = this.grid,
            v = grid.view,
            x = pt.left,
            cms = grid.colModel.config,
            i = 0,
            match = false;
        for(var len = cms.length, c; c = cms[i]; i++){
            if(!c.hidden){
                if(Ext.fly(v.getHeaderCell(i)).getRegion().right >= x){
                    match = i;
                    break;
                }
            }
        }
        return match;
    },

    startMonitoring : function(){
        if(!this.bound && this.monitorValid){
            this.bound = true;
            Ext.TaskMgr.start({
                run : this.bindHandler,
                interval : this.monitorPoll || 200,
                scope: this
            });
        }
    },

    stopMonitoring : function(){
        this.bound = false;
        if(this.tooltip){
            this.tooltip.hide();
        }
    },

    isValid: function(){
        var valid = true;
        this.items.each(function(f){
            if(!f.isValid(true)){
                valid = false;
                return false;
            }
        });
        return valid;
    },

    // private
    bindHandler : function(){
        if(!this.bound){
            return false; // stops binding
        }
        var valid = this.isValid();
        if(!valid && this.errorSummary){
            this.showTooltip(this.getErrorText().join(''));
        }
        this.btns.saveBtn.setDisabled(!valid);
        this.fireEvent('validation', this, valid);
    },

    lastVisibleColumn : function() {
        var i = this.items.getCount() - 1,
            c;
        for(; i >= 0; i--) {
            c = this.items.items[i];
            if (!c.hidden) {
                return c;
            }
        }
    },

    showTooltip: function(msg){
        var t = this.tooltip;
        if(!t){
            t = this.tooltip = new Ext.ToolTip({
                maxWidth: 600,
                cls: 'errorTip',
                width: 300,
                title: this.errorText,
                autoHide: false,
                anchor: 'left',
                anchorToTarget: true,
                mouseOffset: [40,0]
            });
        }
        var v = this.grid.getView(),
            top = parseInt(this.el.dom.style.top, 10),
            scroll = v.scroller.dom.scrollTop,
            h = this.el.getHeight();

        if(top + h >= scroll){
            t.initTarget(this.lastVisibleColumn().getEl());
            if(!t.rendered){
                t.show();
                t.hide();
            }
            t.body.update(msg);
            t.doAutoWidth(20);
            t.show();
        }else if(t.rendered){
            t.hide();
        }
    },

    getErrorText: function(){
        var data = ['<ul>'];
        this.items.each(function(f){
            if(!f.isValid(true)){
                data.push('<li>', f.getActiveError(), '</li>');
            }
        });
        data.push('</ul>');
        return data;
    }
});