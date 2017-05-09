//Helper to go to the label editor if needed
Jaffa.util.LabelTooltipTokenEditor=function(token) {
    window.open(params.appCtx+'/startComponent.do?component=Jaffa.Admin.LabelEditor&finalUrl=jaffa_closeBrowser&labelFilter='+token,'_blank');
};

Jaffa.util.LabelTooltipTokenEditorContextMenu=function(compId) {
  var comp = Ext.getCmp(compId);
  if (comp) {
    Jaffa.util.LabelTooltipTokenEditor(comp.fieldLabelToken || comp.textToken || comp.titleToken);
  }
};
Jaffa.util.GetLabelLink = function(token) {
  return '<a class="edit-label" title="Edit Label" onclick="javascript:Jaffa.util.LabelTooltipTokenEditor(\''+token+'\');return false;">&nbsp;</a>';
};
Jaffa.util.GetLabelWidget = function(field) {
  return '<a class="edit-label" title="Edit Label" onclick="javascript:Jaffa.util.LabelTooltipTokenEditorContextMenu(\''+field.id+'\');return false;">&nbsp;</a>';
};
Jaffa.util.GetLabelWidgetConfig = function(field) {
  return {
    tag:    'a',
    'class':  'edit-label',
    titleToken:  'title.jaffa.jaffaRIA.labelEditor.EditLabel',
    onClick:'javascript:Jaffa.util.LabelTooltipTokenEditorContextMenu(\''+field.id+'\');return false;',
    html:   '&nbsp;'
  };
};


/**
 * Extend Panel and Button to use token for title/text.
 */

(function() {

// Overload the getTemplateArgs so that on return we add the LabelEditor link to the label being rendered
Ext.layout.FormLayout.prototype.getTemplateArgs = Ext.layout.FormLayout.prototype.getTemplateArgs.createWrapper(function(field) {
    //console.warn("Form Layout Wrapper", this);
  var result = this.wrappedFn(field);
    //console.warn("Overload label ", result.label);
    if(result.label && field.fieldLabelToken && result.label.indexOf("???")!=0)
        result.label += Jaffa.util.GetLabelWidget(field);
  return result;
  });


  Ext.layout.AdjacentFieldLayout.prototype.renderAll = Ext.layout.AdjacentFieldLayout.prototype.renderAll.createInterceptor(function(ct, target){
    // Loop through all the labels and add the editor to them
    var items = ct.items.items;
    for (var i = 0, len = items.length; i < len; i++) {
      var c = items[i];
      if (c && !c.rendered && c.fieldLabel && c.fieldLabelToken && c.fieldLabel.indexOf("???")!=0)
        c.label = c.fieldLabel + Jaffa.util.GetLabelWidget(c);
      }
  });

    
/*
  Ext.form.FieldSet.prototype.onRender = Ext.form.FieldSet.prototype.onRender.createSequence(function() {
    //console.warn("Add to fieldset header",this,this.header);
    if(this.header && this.title && this.titleToken && this.title.indexOf("???")!=0 ) {
      //var x = this.header.createChild({tag:'a',href:'#', html:'test'});
      var x = this.header.createChild(Jaffa.util.GetLabelWidgetConfig(this));
      //console.warn("Created fieldset link",x);
    }
  });
*/

  // Works for normal panels & fieldsets
  Ext.Panel.prototype.onRender = Ext.Panel.prototype.onRender.createSequence(function() {
    //console.warn("Add to panel",this);
    if(this.header && this.title && this.titleToken && this.title.indexOf("???")!=0 ) {
      //var x = this.header.createChild({tag:'a',href:'#', html:'test'});
      var x = this.header.createChild(Jaffa.util.GetLabelWidgetConfig(this));
      //console.warn("Created fieldset link",x);
    }
  });

  // Works for normal panels & fieldsets
  Ext.TabPanel.prototype.initTab = Ext.TabPanel.prototype.initTab.createSequence(function(item, index) {
    //this = the main panel
    //item = is the tab
    //index = in which tab sequence
    var el = Ext.fly(this.getTabEl(item));
    el.addClass("x-tab-strip-editable");
    //console.warn("Add to tab item",el, item, this);
    var x = el.insertFirst({
      tag:    'a',
      'class':  'x-tab-strip-edit',
      titleToken:  'title.jaffa.jaffaRIA.labelEditor.EditLabel',
      onClick:'javascript:Jaffa.util.LabelTooltipTokenEditorContextMenu(\''+item.id+'\');return false;',
      html:   '&nbsp;'
    });
  });


  Ext.Button.prototype.onRender = Ext.Button.prototype.onRender.createSequence(function() {
    //console.warn("Add to button header",this,this.header);
    if(this.text && this.textToken && this.text.indexOf("???")!=0 ) {
      var x = this.btnEl.parent().createChild(Jaffa.util.GetLabelWidgetConfig(this));
    }
  });


  Jaffa.finder.FinderHeader.prototype.tbTitle = Jaffa.finder.FinderHeader.prototype.tbTitle.createWrapper(function(config) {
    //console.warn("FinderHeader Wrapper", this);
    var result = this.wrappedFn(config);
    //console.warn("Overload label ", result);
    if(this.headerTitleToken)
        result.text += Jaffa.util.GetLabelLink(this.headerTitleToken);
    return result;
  });

  Jaffa.finder.FinderHeader.prototype.updateHeaderTitle = Jaffa.finder.FinderHeader.prototype.updateHeaderTitle.createSequence(function() {
    if(this.headerTitleToken) {
      var headerTitleEl = Ext.getCmp('headerTitle');
      console.warn('reset header',headerTitleEl);
      if (headerTitleEl /*&& headerTitleEl.text.indexOf('edit-label')==-1*/) {
        var t = headerTitleEl.getText();
        if (t && t.indexOf('edit-label')==-1) {
          console.warn('reset header',t);
          headerTitleEl.setText(t + Jaffa.util.GetLabelLink(this.headerTitleToken));
        }
      }
    }
  });

  // Overload the input to Ext.Message.show to expand token if passed
  Ext.MessageBox.show = Ext.MessageBox.show.createWrapper(function(config) {
    //console.warn("Ext.Message", this, config);
    if(config.titleToken && !config.title)
        config.title = Labels.get(config.titleToken) + Jaffa.util.GetLabelLink(config.titleToken);
    if(config.msgToken && !config.msg)
        config.msg = Labels.get(config.msgToken) + Jaffa.util.GetLabelLink(config.msgToken);

    return this.wrappedFn(config);
  });

  Ext.menu.Item.prototype.getTemplateArgs = Ext.menu.Item.prototype.getTemplateArgs.createWrapper(function() {
    var result = this.wrappedFn();
    if(result.text && this.textToken && this.text.indexOf("???")!=0)
      result.text = Labels.get(this.textToken);

    return result;
  });

  Ext.menu.Item.prototype.onRender = Ext.menu.Item.prototype.onRender.createSequence(function(container, position) {
    //console.warn("Add to button header",this,this.header);
    if(this.text && this.textToken && this.text.indexOf("???")!=0 ) {
      var x = this.textEl.parent().createChild(Jaffa.util.GetLabelWidgetConfig(this));
    }
  });

  Ext.grid.GridView.prototype.getColumnHeaderValue = function (cm, i){
    var columnHeaderValue = cm.getColumnHeader(i) || '';
    if (cm.getColumnAt(i).headerToken && cm.getColumnAt(i).headerToken!=''){
      if (columnHeaderValue == '') columnHeaderValue = Labels.get(cm.getColumnAt(i).headerToken);
      columnHeaderValue += Jaffa.util.GetLabelLink(cm.getColumnAt(i).headerToken);
    }
    return columnHeaderValue;
  };

})();

//@TODO
//Ext.layout.AdjacentFieldLayout.renderItem

