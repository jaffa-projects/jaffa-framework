/**
 *  Extends Ext.form.HtmlEditor with a view mode, from which, a URL link in the content screen can be activated in IE.
 *    If user adds an URL link in the content in Ext.form.HtmlEditor, this link is not openable in IE, i.e. clicking on this
 *    link does not open up a web page in another browser tab/window. This extension allows the content to be shown in view mode,
 *    which allows the user the click on the link and open up a new window to display the content of the url.
 *
 *  @author Christopher Osborn, Sean Zhou, July 2010
 */
Ext.ux.form.HtmlEditor = Ext.extend(Ext.form.HtmlEditor,{

  /**
   * @cfg {Boolean} previewEnabled specifies weather or not html editor initalizes in preview mode (defaults to true)
   *
   */
  previewEnabled: true,
  /**
   * @cfg {Boolean} previewMode specifies whether html editor is in preview mode
   */
  previewMode: false,

  /**
   * private origValue used when user cancels editing
   */
  origValue: '',

  enableViewMode: true,

  // defaults width to 700 so that the preview button will show at the end of the toolbar.
  width: 700,

  /**
   * @cfg editButton (Ext.Button) the config or instance of a button. used to swich into edit mode
   */
  editButton: {
    text: 'Edit',
    iconCls: 'edit',
    tooltip: 'switch to edit mode',
    previewVisible: true,
    handler: function(btn, e){
      btn.editor.togglePreview(false);
      btn.editor.origValue = btn.editor.getValue();
      btn.editor.cancelButton.setDisabled(false);
      btn.editor.previewButton.setDisabled(false);
    }
  },

  isDirty : function() {
    if(this.disabled || !this.rendered) {
      return false;
    }
    return String(this.getValue()) !== String(this.origValue);
  },
  
  reset: function(){
    if(this.rendered) {
      this.togglePreview(true);
    }
    Ext.ux.form.HtmlEditor.superclass.reset.call(this);
  },

  /**
   * @cfg previewButton (Ext.Button) the config or instance of a Ext.Button. used to exit edit mode and return to readonly
   */
  previewButton: {
    text: 'Preview',
    iconCls: 'next',
    tooltip: 'preview changes',
    previewVisible: false,
    handler: function(btn, e){
      btn.editor.togglePreview(true);
      btn.editor.editButton.setDisabled(false);
    }
  },

  /**
   * @cfg canceButton (Ext.Button) the config or instance of a Ext.Button. used to exit edit mode and return to readonly after replacing the text with origValue
   */
  cancelButton: {
    text: 'Cancel',
    iconCls: 'cancel',
    tooltip: 'cancel changes',
    previewVisible: false,
    handler: function(btn, e){
      btn.editor.setValue(btn.editor.origValue);
      btn.editor.togglePreview(true);
      btn.editor.editButton.setDisabled(false);
    }
  },

  /**
   * @method setReadOnly sets the designmode and content editable properties of the iframe within the htmleditor
   * fires the readonlychange event everytime readonly is changed
   * @param (Boolean) readOnly true to set editor to readonly
   */
  setReadOnlyForPreview: function(readOnly){
    if(this.initialized){
      var newDM = readOnly ? 'off' : 'on',
              doc = this.getDoc();
      if (Ext.isIE){
        doc.body.contentEditable = !readOnly;
      }else {
        if (String(doc.designMode).toLowerCase() != newDM) {
          doc.designMode = newDM;
        }
      }
      this.disableItems(!readOnly);
    }
    if(this.rendered){
      this.el.dom.readOnly = readOnly;
    }
    this.fireEvent('readonlychange', this, readOnly);
  },

  /**
   * private
   * @method replaceLinkTargets programatically replaces all link targets to _blank
   * this ensures that all links open in a new tab/window.
   */
  replaceLinkTargets: function(){
    var bd = this.getEditorBody();
    if (!bd) return;
    var html = bd.innerHTML;
    var syncFlag = false;
    var anchorTags = bd.getElementsByTagName("a");
    for (var i = 0; i < anchorTags.length; i++) {
      var target = anchorTags[i].target;
      if (!target) {
        anchorTags[i].target = '_blank';
        syncFlag = true;
      }
    }
    if (syncFlag) this.syncValue();
  },

  //Overrode because the htmleditor does not have a change listener.
  syncValue : function(){
    if(this.initialized){
        var bd = this.getEditorBody();
        var html = bd.innerHTML;
        var oldVal = this.el.dom.value;
        if(Ext.isWebKit){
            var bs = bd.getAttribute('style'); 
            var m = bs.match(/text-align:(.*?);/i);
            if(m && m[1]){
                html = '<div style="'+m[0]+'">' + html + '</div>';
            }
        }
        html = this.cleanHtml(html);
        if(this.fireEvent('beforesync', this, html) !== false){
            this.el.dom.value = html;
            this.fireEvent('sync', this, html);
        }
        var newVal = this.el.dom.value;
        if(oldVal) oldVal = oldVal.trim();
        if(newVal) newVal = newVal.trim();
        if (oldVal !== newVal) {
          this.fireEvent('change', this, oldVal, newVal);
        }
    }
    },
  
  /**
   * @method togglePreview switches between edit mode and preview mode
   * @param preview set to true to switch to preview mode (defalts to false)
   */
  togglePreview: function(preview){
    this.replaceLinkTargets();
    this.setReadOnlyForPreview(preview);
    this.previewMode = preview;
    if (this.previewEnabled) {
      if (preview) {
        this.activated = false;
        var btns = this.tb.items.items;
        for (var i = 0; i < this.tb.items.length; i++) {
          if (btns[i].rendered) {
            btns[i].setVisible(btns[i].previewVisible);
          }
          else {
            btns[i].hidden = !btns[i].previewVisible;
          }
        }
      }
      else {
        this.focus();
        this.activated = true;
        var btns = this.tb.items.items;
        for (var i = 0; i < this.tb.items.length; i++) {
          if (btns[i].rendered) {
            btns[i].setVisible(!btns[i].previewVisible);
          }
          else {
            btns[i].hidden = btns[i].previewVisible;
          }
        }
        this.toggleSourceEdit(false);
      }
      this.fireEvent('previewchange', this);
    }
  },

  toggleSourceEdit: function(sourceEditMode){
    if (this.enableSourceEdit === false) {
      return;
    }
    Ext.ux.form.HtmlEditor.superclass.toggleSourceEdit.call(this,sourceEditMode);
  },
  
  cleanHtml: function(html) {
    html = String(html);
    if(Ext.isWebKit){ 
        html = html.replace(/\sclass="(?:Apple-style-span|khtml-block-placeholder)"/gi, '');
    }
    
    if(Ext.isIE){ // IE inserts a space entity after the user uses backspace to delete text in edit mode
      html = html.replace('&nbsp;', ' ');
    }
    
    if(html.charCodeAt(0) == this.defaultValue.replace(/\D/g, '')){
        html = html.substring(1);
    }
    return html;
  },

  /**
   * private
   * @method addPreviewButtons adds needed buttons for edit mode and preview mode
   */
  addPreviewButtons: function(){
    if (!this.editButton.initialConfig) {
      this.editButton.editor = this;
      this.editButton = this.tb.addButton(this.editButton);
    }
    if (!this.cancelButton.initialConfig) {
      this.tb.addFill();
      this.cancelButton.editor = this;
      this.cancelButton = this.tb.addButton(this.cancelButton);
    }
    if (!this.previewButton.initialConfig) {
      this.tb.addSpacer();
      this.previewButton.editor = this;
      this.previewButton = this.tb.addButton(this.previewButton);
    }
  },

  /**
   * @method setPreviewEnabled setter for previewEnabled toggles to preview mode if true
   * @param (Boolean) enabled true to enable previewMode (defaults to false)
   */
  setPreviewEnabled: function(enabled){
    if (!this.enableViewMode) return;
    this.previewEnabled = enabled;
    if (enabled) {
      this.addPreviewButtons();
      this.togglePreview(true);
    }
  },
  initComponent: function(){
    this.addEvents('maskreadonlychange', 'previewchange', 'change');
    Ext.ux.form.HtmlEditor.superclass.initComponent.call(this);
    this.on('initialize', function(){
      this.replaceLinkTargets();
      this.originalValue = this.getEditorBody().innerHTML;
      this.setReadOnlyForPreview(this.previewEnabled);
    }, this, {single: true});
  },
  onRender: function(ct, position){
    Ext.ux.form.HtmlEditor.superclass.onRender.call(this,ct,position);
    this.setPreviewEnabled(this.previewEnabled);
  },
  updateToolbar: function(){
    if(this.previewMode){
      this.setReadOnlyForPreview(true);
      return false;
    }
    Ext.ux.form.HtmlEditor.superclass.updateToolbar.call(this);
  }

});

Ext.reg('xhtmleditor', Ext.ux.form.HtmlEditor);

/*

 Example usage :

 Ext.onReady(function(){

 var comments = new Ext.ux.form.HtmlEditor({
 value: 'Select the edit button and create a link to test.'
 });

 viewport = new Ext.Viewport({
 items: [{
 xtype: 'container',
 width: 700,
 items:[{
 xtype:'panel',
 title: 'Jaffa DynamicHtmlEditor Example',
 layout: 'fit',
 items:[comments]
 }]
 }]
 });

 });

 */


