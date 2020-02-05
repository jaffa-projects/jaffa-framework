Ext.ns("Jaffa.dashboard");

/**
 * @class Jaffa.dashboard.MenuIndex
 * 
 * This builds a TreeNode structure based on the menu options in navigation.xml that the current user has access to.  
 */ 
Jaffa.dashboard.MenuIndex = Ext.extend(Ext.tree.TreeNode, {

    /**
     * @cfg appCtx {String}
     *      This is the root of the application i.e. "http://localhost:8080/GOLD"
     *      it must be provided so the application knows where to get '/js/extjs/jaffa/dashboard/menu_json.jsp' from.
     */         

    constructor: function (config) {
        this.startMenu = config.startMenu;
        Jaffa.dashboard.MenuIndex.superclass.constructor.call(this,config);
        this.requestMenuData(config.appCtx);
     },
       
       
    requestMenuData: function(appCtx) {
      if (! this.hasInitiated) {
        //this.el.mask('Loading...', 'x-mask-loading');
        Ext.Ajax.request({
            url: appCtx + '/js/extjs/jaffa/dashboard/menu_json.jsp',
            success: this.loaddata,
            failure: this.loaderror,
            scope: this
        });
      }
    },

    loaddata: function(response, options) {
        //console.debug(response);
        var data = eval(response.responseText);
        //console.debug(data);
        this.nodeId = 1;
        this.beginUpdate();
        this.addNodes(this, data);
        this.endUpdate();
        this.hasInitiated = true;
        delete this.text; 
        
        // populate the start menu
        if(this.startMenu && this.startMenu.programMenu) {
          var programMenu = this.startMenu.programMenu;
          if(programMenu.menu){
            this.addStartMenuNodes(programMenu.menu, this, this.addStartMenuNodes.createDelegate());
          }
          programMenu.parentMenu.doLayout();
        } 
    },
    
    addNodes: function(topnode, nodes) {
      for(var i=0; i<nodes.length; i++) {
        var x = nodes[i];
        //console.debug("Added Node",x.label,x," to ", topnode.text,topnode);
        if(x.menu) {
          var m = topnode.appendChild(new Ext.tree.TreeNode({
            text: x.label,
            id: "menu-"+(this.nodeId++),
            //isLeaf: false,
            //expanded: true, //topnode.isRoot, // Only show level 1
            loaded: true,
            singleClickExpand:true,
            //iconCls: 'menu',
            type: 'menu',
            data: x
          }));
          this.addNodes(m,x.menu);
        } else
          topnode.appendChild(new Ext.tree.TreeNode({
            text: x.label,
            id: "menu-"+(this.nodeId++),
            //isLeaf: true,
            //loaded: true,
            iconCls: 'screen',
            type: 'screen',
            data : x
          }));
      }
    },

    addStartMenuNodes: function(menu,menuNode,callback) {
        console.debug("Populate menu ",menu,", from node:",menuNode);
        // Clear the menu if it already has stuff in it.
        menu.removeAll();
        menuNode.eachChild(function(n) {
            // Create an 'opt' object to create the menu option with
            var opt = {
                id: menu.id + '_' + n.id,
                text: n.text
            };
            //console.debug("Add Option:",n.text, n.type, n.attributes.data,n);
            if(n.attributes.data.menu) {
                // If this should have a submenu, create a lazy way to render it.
                opt.menu = new Ext.menu.Menu({
                    menuSourceNode : n,
                    listeners: {
                        'beforerender' : function(i) {
                            console.debug("Beforerender:",i,i.items,i.menuSourceNode);
                            if((!i.items || i.items.getCount()==0) && i.menuSourceNode) {
                              console.debug("Populate sub-menu from node: ",i.menuSourceNode);
                              callback(i,i.menuSourceNode,callback);
                            }
                        },
                        scope: this
                      }
                  });  
            } else
              opt.handler = Jaffa.dashboard.MenuIndex.invokeOption.createCallback(n.attributes.data);
            this.add(opt);
        },menu);
    },

    loaderror: function() {
    },
    
    getLeaves : function(menuNode, root) {
        var nodes = [];
        menuNode.eachChild(function(n) {
            if(n.childNodes && n.childNodes.length>0)
                nodes = nodes.concat(root.getLeaves(n,root));
            else
                nodes.push(n);
        });
        return nodes;
        
    }
});    

/**
 * @class Jaffa.dashboard.MenuIndex
 * @static
 * @method invokeOption
 * Allows a command to be invoked based on an option object
 * The option object should be a node built by the Jaffa.dashboard.MenuIndex class
 */    
Jaffa.dashboard.MenuIndex.invokeOption = function(opt) {
    if(opt.url || opt.component) {
        var u = '';
        if(opt.component)
           u+=params.appCtx+"/startComponent.do?component="+opt.component;
        else if(opt.url)
           u+=(/^https?:/i.test(opt.url)?"": params.appCtx+"/")+opt.url;
        else
           return; // no detail feature
        // add params...
        if(opt.params)
          u+=(u.indexOf("?")==-1?"?":"&")+opt.params
        // add finalUrl if needed (ie this is a struts action call!)
        if(u.indexOf(".do?")>0)
          u+="&finalUrl=jaffa_closeBrowser"
        console.debug("url:", u,opt);
        window.open(u, "_blank");
    }
}
