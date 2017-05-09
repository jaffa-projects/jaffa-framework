/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
/**
 * @class Ext.ux.StartMenu
 * @extends Ext.menu.Menu
 * A start menu object.
 * @constructor
 * Creates a new StartMenu
 * @param {Object} config Configuration options
 *
 * SAMPLE USAGE:
 *
 * this.startMenu = new Ext.ux.StartMenu({
 *      iconCls: 'user',
 *      height: 300,
 *      shadow: true,
 *      title: get_cookie('memberName'),
 *      width: 300
 *  });
 *
 * this.startMenu.add({
 *      text: 'Grid Window',
 *      iconCls:'icon-grid',
 *      handler : this.createWindow,
 *      scope: this
 *  });
 *
 * this.startMenu.addTool({
 *      text:'Logout',
 *      iconCls:'logout',
 *      handler:function(){ window.location = "logout.php"; },
 *      scope:this
 *  });
 */

Ext.namespace("Ext.ux");

Ext.ux.StartMenu = Ext.extend(Ext.menu.Menu, {
    maxSearchResults: 8,
    initComponent: function(config) {
        Ext.ux.StartMenu.superclass.initComponent.call(this, config);
        var tools = this.toolItems;
        this.toolItems = new Ext.util.MixedCollection();
        if(tools){
            this.addTool.apply(this, tools);
        }
    },

    // private
    onRender : function(ct, position){
        Ext.ux.StartMenu.superclass.onRender.call(this, ct, position);
        var el = this.el.addClass('ux-start-menu');

        var header = el.createChild({
            tag: "div",
            cls: "x-window-header x-unselectable x-panel-icon "+this.iconCls
        });

        this.header = header;

        var headerText = header.createChild({
            tag: "span",
            cls: "x-window-header-text"
        });
        var tl = header.wrap({
            cls: "ux-start-menu-tl"
        });
        var tr = header.wrap({
            cls: "ux-start-menu-tr"
        });
        var tc = header.wrap({
            cls: "ux-start-menu-tc"
        });

        this.menuBWrap = el.createChild({
            tag: "div",
            cls: "x-window-body x-border-layout-ct ux-start-menu-body"
        });
        var ml = this.menuBWrap.wrap({
            cls: "ux-start-menu-ml"
        });
        var mc = this.menuBWrap.wrap({
            cls: "x-window-mc ux-start-menu-bwrap"
        });

        this.menuPanel = this.menuBWrap.createChild({
            tag: "div",
            cls: "x-panel x-border-panel ux-start-menu-apps-panel"
        });
        this.toolsPanel = this.menuBWrap.createChild({
            tag: "div",
            cls: "x-panel x-border-panel ux-start-menu-tools-panel"
        });

        var bwrap = ml.wrap({cls: "x-window-bwrap"});
        var bc = bwrap.createChild({
            tag: "div",
            cls: "ux-start-menu-bc"
        });
        var bl = bc.wrap({
            cls: "ux-start-menu-bl x-panel-nofooter"
        });
        var br = bc.wrap({
            cls: "ux-start-menu-br"
        });

        this.ul.appendTo(this.menuPanel);

        var toolsUl = this.toolsPanel.createChild({
            tag: "ul",
            cls: "x-menu-list"
        });

        this.mon(toolsUl, 'click', this.onClick, this);
        this.mon(toolsUl, 'mouseover', this.onMouseOver, this);
        this.mon(toolsUl, 'mouseout', this.onMouseOut, this);

        this.items.each(function(item){
            item.parentMenu = this;
        }, this);

        this.toolItems.each(
            function(item){
                var li = document.createElement("li");
                li.className = "x-menu-list-item";
                toolsUl.dom.appendChild(li);
                item.render(li);
                item.parentMenu = this;
            }, this);

        this.toolsUl = toolsUl;

        this.menuBWrap.setStyle('position', 'relative');
        this.menuBWrap.setHeight(this.height - 28);

        this.menuPanel.setStyle({
            padding: '2px',
            position: 'absolute',
            overflow: 'auto'
        });

        this.toolsPanel.setStyle({
            padding: '2px 4px 2px 2px',
            position: 'absolute',
            overflow: 'auto'
        });

        this.setTitle(this.title);
    },

    // private
    findTargetItem : function(e){
        var t = e.getTarget(".x-menu-list-item", this.ul,  true);
        if(t && t.menuItemId){
            if(this.items.get(t.menuItemId)){
                return this.items.get(t.menuItemId);
            }else{
                return this.toolItems.get(t.menuItemId);
            }
        }
    },

    /**
     * Displays this menu relative to another element
     * @param {Mixed} element The element to align to
     * @param {String} position (optional) The {@link Ext.Element#alignTo} anchor position to use in aligning to
     * the element (defaults to this.defaultAlign)
     * @param {Ext.ux.StartMenu} parentMenu (optional) This menu's parent menu, if applicable (defaults to undefined)
     */
    show : function(el, pos, parentMenu){
        this.parentMenu = parentMenu;
        if(!this.el){
            this.render();
        }

        this.fireEvent("beforeshow", this);
        this.showAt(this.el.getAlignToXY(el, pos || this.defaultAlign), parentMenu, false);
        var tPanelWidth = 100;
        var box = this.menuBWrap.getBox();
        this.menuPanel.setWidth(box.width-tPanelWidth);
        this.menuPanel.setHeight(box.height);

        this.toolsPanel.setWidth(tPanelWidth);
        this.toolsPanel.setX(box.x+box.width-tPanelWidth);
        this.toolsPanel.setHeight(box.height);
    },

    addTool : function(){
        var a = arguments, l = a.length, item;
        for(var i = 0; i < l; i++){
            var el = a[i];
            if(el.render){ // some kind of Item
                item = this.addToolItem(el);
            }else if(typeof el == "string"){ // string
                if(el == "separator" || el == "-"){
                    item = this.addToolSeparator();
                }else{
                    item = this.addText(el);
                }
            }else if(el.tagName || el.el){ // element
                item = this.addElement(el);
            }else if(typeof el == "object"){ // must be menu item config?
                item = this.addToolMenuItem(el);
            }
        }
        return item;
    },

    /**
     * Adds a separator bar to the Tools
     * @return {Ext.menu.Item} The menu item that was added
     */
    addToolSeparator : function(){
        return this.addToolItem(new Ext.menu.Separator({itemCls: 'ux-toolmenu-sep'}));
    },

    addToolItem : function(item){
        this.toolItems.add(item);
        if(this.ul){
            var li = document.createElement("li");
            li.className = "x-menu-list-item";
            this.ul.dom.appendChild(li);
            item.render(li, this);
            this.delayAutoWidth();
        }
        return item;
    },

    addToolMenuItem : function(config){
        if(!(config instanceof Ext.menu.Item)){
            if(typeof config.checked == "boolean"){ // must be check menu item config?
                config = new Ext.menu.CheckItem(config);
            }else{
                config = new Ext.menu.Item(config);
            }
        }
        return this.addToolItem(config);
    },

    setTitle : function(title, iconCls){
        this.title = title;
        this.header.child('span').update(title);
        return this;
    },

    filterMenu: function(v) {
        // Get the sorted list of leaf nodes from the cache, or build it and cache it.
        var n =  this.nodeList;
        if(!n) {
            n = this.rootNode.getLeaves(this.rootNode, this.rootNode);
            if(n.length > 0) {
                n.sort(function(a, b){
                    if(!a.lc) a.lc=a.text.toLowerCase();
                    if(!b.lc) b.lc=b.text.toLowerCase();
                    if (a.lc>b.lc)
                        return 1;
                    else
                        return -1;
                });
            }
            this.nodeList = n;
        }

        // Make sure we are not doing the same search again because of an event mis-fire!
        if(this.prevSearch === v){
            return;
        }
        this.prevSearch = v;

        // Now get the filtered list, we can stop when we reach our default limit
        var f = [];
        if(v) {
            v = v.toLowerCase();
            for(var i=0; i < n.length && f.length < this.maxSearchResults; i++) {
                if(n[i].lc.match(v)) {
                    var addNew = true;
                    for (var j=0;j<f.length;j++){
                        var fData=f[j].attributes.data;
                        var nData=n[i].attributes.data;
                        if (fData.component  == nData.component
                            && fData.label == nData.label
                            && fData.params == nData.params
                            && fData.target == nData.target
                            && fData.url == nData.url){
                            addNew=false;
                        }
                    }
                    if (addNew) f.push(n[i]);
                    console.debug("filtered",n[i]);
                }
            }
        }
        // Delete old values
        var opts = this.find('menuSearchOption', true);
        if(opts){
            for(var i=0;i<opts.length;i++) {
                this.remove(opts[i],true);
            }
            // Add currently found options
            for(var i=0;i<f.length;i++) {
                var opt = {
                    xtype: 'button',
                    template: Ext.Button.menuStyleTemplate,
                    text: f[i].text,
                    menuSearchOption : true,
                    menuNode: f[i].attributes.data,
                    handler: Jaffa.dashboard.MenuIndex.invokeOption.createCallback(f[i].attributes.data)
                };
                this.add(opt);
            }
        }
    }
});
Ext.reg('startmenu', Ext.ux.StartMenu);