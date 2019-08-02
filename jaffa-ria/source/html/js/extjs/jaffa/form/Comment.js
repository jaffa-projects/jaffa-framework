/**
 * Ext.ux.form.Comment Extension Class for Ext 3.x Library
 * @author    BobbyC
 */
Ext.ns('Ext.ux.form');

/**
 * @class Ext.ux.form.Comment
 * @extends Ext.Container
 */
Ext.ux.form.Comment = Ext.extend(Ext.Container, {
  /**
   * @cfg {Object} commentConfig Config for the comment editor constructor.
   */
  commentConfig: null,
  /**
   * @cfg {Object} historyConfig Config for history comment constructor.
   */
  historyConfig: null,
  /**
   * @cfg {Object} commentStyle Style property to determine appearance of comment. Choices are: 'COMMENT','LIFO','FIFO'.
   */
  commentStyle: 'plain',
  /**
   * private
   */
  border : true,
  initComponent: function(){
    //Get meta data to determine comment type

    var meta = null;
    var metaClass = null;
    var metaField = this.metaField ? this.metaField : this.mapping;

    // determine metaClass
    if (this.metaClass && this.metaClass != null) {
      metaClass = this.metaClass;
    } else if (this.ownerCt && this.ownerCt.metaClass) {
      metaClass = this.ownerCt.metaClass;
    }

    if (metaClass && typeof metaClass == 'string') {
      // metaClass can be specified as the name of the graph class inside of ClassMetaData
      // or just the graph class
      metaClass = ClassMetaData[metaClass];
    }
    if (metaClass && metaClass.fields && metaField != null && metaField != '')
      meta = metaClass.fields[metaField] || metaClass.fields[metaField.split('.').pop()];

    if (meta && meta.commentStyle) this.commentStyle = meta.commentStyle;

    // create comment field
    var commentConfig = Ext.apply({}, {
      itemId: 'xhtmlCommentEditor',
      xtype: 'xhtmleditor',
      toggleSourceEdit : function(sourceEditMode){
        if (this.enableSourceEdit === false) {
          return;
        }

        var iframeHeight,
          elHeight,
          ls;

        if (sourceEditMode === undefined) {
          sourceEditMode = !this.sourceEditMode;
        }
        this.sourceEditMode = sourceEditMode === true;
        var btn = this.tb.getComponent('sourceedit');

        if (btn.pressed !== this.sourceEditMode) {
          btn.toggle(this.sourceEditMode);
          if (!btn.xtbHidden) {
            return;
          }
        }
        if (this.sourceEditMode) {
          ls = this.getSize();

          iframeHeight = Ext.get(this.iframe).getHeight();

          this.disableItems(true);
          this.syncValue();
          this.iframe.className = 'x-hidden';
          this.el.removeClass('x-hidden');
          this.el.dom.removeAttribute('tabIndex');
          this.el.focus();
          this.el.dom.style.height = iframeHeight + 'px';
        }
        else {
          elHeight = parseInt(this.el.dom.style.height, 10);
          if (this.initialized) {
            this.disableItems(this.readOnly);
          }
          this.pushValue();
          this.iframe.className = '';
          this.el.addClass('x-hidden');
          this.el.dom.setAttribute('tabIndex', -1);
          this.deferFocus();

          this.setSize(ls);
          //this.iframe.style.height = elHeight + 'px';
        }
        this.fireEvent('editmodechange', this, this.sourceEditMode);
      }
    }, this.commentConfig);

    this.cf = Ext.ComponentMgr.create(commentConfig);

    this.commentRecord = Jaffa.data.Record.create([{
      name: 'user',
      type: 'string'
    },{
      name: 'date',
      type: 'string'
    },{
      name: 'comment',
      type: 'string'
    },{
      name : 'commentTitle',
      type: 'string'
    }]);

    var historyExpander = new Ext.ux.grid.RowExpander({
      tpl : new Ext.Template(
        '<p></br> {comment}</p>'
      ),
      listeners : {
        'expand' : function(expander){
          expander.grid.getView().refresh()
        },
        'collapse' : function(expander){
          expander.grid.getView().refresh()
        }
      }
    });

    // create history field
    var historyConfig = Ext.apply({}, {
      itemId: 'commentHistoryGrid',
      xtype:'grid',
      border: true,
      frame: false,
      plugins: historyExpander,
      ownerCt: this,
      disableSelection: true,
      height: 200,
      hideHeaders: true,
      style: {'background-color':'red'},
      width: 698,
      autoExpandColumn: 1,
      columns: [historyExpander,{
        header: Labels.get('label.Asset.Pad.PadMaintenance.User'),
        dataIndex: 'user',
        width : 300,
        sortable: true,
        scope : this,
        renderer : function(v,meta,rec,rowIndex){
          if(v){
            //@TODO:Need to add CSS instead of inline styling.
            meta.attr = 'style="color: #0464BB;font-weight: bold"';
            v = Labels.get('label.jaffaRIA.jaffa.Form.Comment.UserID',[v])+ '<span style="float:right;color:#708090;padding-right: 22px;">'+rec.get("date")+'</span>';
            if(this.hf.view){
              var row =this.hf.view.getRow(rowIndex);
              if(!row  || Ext.fly(row).hasClass('x-grid3-row-collapsed'))
                v = v + '<br /><span style="font-size:110%;font-weight: normal;color:#555555;">' + rec.get("commentTitle") + '</span>';
            }
          }
          return v;
        }
      }],
      ds: new Ext.data.Store({
        scope: this,
        local: true,
        ignoreChangeListeners : true,
        remoteSort: false,
        sortInfo: {
          field: 'date',
          direction: "ASC"
        },
        reader: new Ext.data.JsonReader({}, this.commentRecord)
      })
    }, this.historyConfig);




    this.hf = Ext.ComponentMgr.create(historyConfig);

    if (!this.items) {
      this.items = new Ext.util.MixedCollection();
    }
    if (this.commentStyle != 'lifo')
      this.items.add(this.cf);
    if (this.commentStyle != 'plain')
      this.items.add(this.hf);
    if (this.commentStyle == 'lifo')
      this.items.add(this.cf);

    // call parent initComponent
    Ext.Container.superclass.initComponent.call(this);

    // relay events
    this.relayEvents(this.cf, ['focus', 'specialkey', 'invalid', 'valid', 'maskreadonlychange', 'previewchange', 'change']);

  },
  /**
   * Disable this component.
   * @return {Ext.Component} this
   */
  disable: function(){
    return this;
  },
  /**
   * Enable this component.
   * @return {Ext.Component} this
   */
  enable: function(){
    return this;
  },
  /**
   * @param {Mixed} val Value to set
   * Sets the value of this field
   */
  setValue: function(val){
    if (this.hf.getStore()) this.hf.getStore().removeAll();
    if (this.commentStyle == 'plain')
      this.cf.setValue(val);
    else {
      if(val === null) val = "";
      if (val.toUpperCase().indexOf("------- ADDITIONAL COMMENTS FROM ")>=0 || val.toUpperCase().indexOf("------- COMMENTS FROM ")>=0){
        var allComments = val.split(/-------\sADDITIONAL\sCOMMENTS\sFROM\s|-------\sCOMMENTS\sFROM\s/i);
        for (var i = 0; i< allComments.length && i<50; i++){
          var comment = allComments[i];
          var space = comment.indexOf(" ");
          var commentName = comment.substr(0,space);
          comment = comment.substr(space+1);
          var commentDate = "";
          if (comment.toUpperCase().indexOf("ON")==0) {
            comment = comment.substr(3);
            commentDate = comment.substr(0,19)
            comment = comment.substr(28);
          }else{
            comment = comment.substr(10);
          }

          comment = comment.replace(/&nbsp;/gi, " ");

          if(!comment) comment = ' - None -';
          var commentTitle =  comment.replace(/<br>/gi, " ");
          commentTitle =  commentTitle.replace(/<(?:.|\n)*?>/gmi, '');
          commentTitle =  (commentTitle.length > 50) ? (commentTitle.substr(0,50) + '....') :commentTitle ;

          if (commentName != '') this.hf.getStore().add(new this.commentRecord({user:commentName,date:commentDate,comment:comment,commentTitle : commentTitle}));
        }
      } else {
        this.hf.getStore().add(new this.commentRecord({user:'Unknown',date:'',comment:val}));
      }

    }
  },
  /**
   * @return {Date/String} Returns value of this field
   */
  getValue: function(){
    return this.cf.getValue();
  },
  /**
   * @param {Mixed} val Value to set
   * Sets the value of this field
   */
  setTextOnly: function(val){
    if (this.commentStyle == 'plain')
      this.cf.setTextOnly(val);
    else
    if (val)
      this.cf.hide();
    else
      this.cf.show();
  },
  /**
   * Hide this component.
   * @return {Ext.Component} this
   */
  hide: function(){
    if (this.rendered) {
      this.cf.hide();
      this.hf.hide();
    }
    this.hidden = true;
    this.cf.hidden = true;
    this.hf.hidden = true;
    this.fireEvent("hide", this);
    return this;
  },
  /**
   * Show this component.
   * @return {Ext.Component} this
   */
  show: function(){
    if (this.rendered) {
      this.cf.show();
      this.hf.show();
    }
    this.hidden = false;
    this.cf.hidden = false;
    this.hf.hidden = false;
    this.fireEvent("show", this);
    return this;
  },
  /**
   * @return {Boolean} true = valid, false = invalid
   * private Calls isValid methods of underlying DateField and TimeField and returns the result
   */
  isValid: function(){
    return this.cf.isValid() && this.hf.isValid();
  },
  reset: function(){
    return this.cf.reset();
  },
  destroy: function(){
    if (this.cf) {
      this.cf.destroy();
    }
    if (this.hf) {
      this.hf.destroy();
    }
    Ext.Container.superclass.destroy.call(this);
  },
  getToolbar: function(){
    return this.cf.getToolbar();
  }
});

// register xtype
Ext.reg('comment', Ext.ux.form.Comment);
