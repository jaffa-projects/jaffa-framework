/**
 * @class Jaffa.state.WidgetStateProvider
 * @extends Ext.state.Provider
 * @author SeanZ
 * The default Provider implementation which saves state via States.
 * <br />Usage:
 <pre><code>
   var cp = new Jaffa.state.WidgetStateProvider({
       path: "/cgi-bin/",
       expires: new Date(new Date().getTime()+(1000*60*60*24*30)), //30 days
       domain: "extjs.com"
   });
   Ext.state.Manager.setProvider(cp);
 </code></pre>
 * @cfg {String} path The path for which the State is active (defaults to root '/' which makes it active for all pages in the site)
 * @cfg {Date} expires The State expiration date (defaults to 7 days from now)
 * @cfg {String} domain The domain to save the State for.  Note that you cannot specify a different domain than
 * your page is on, but you can specify a sub-domain, or simply the domain itself like 'extjs.com' to include
 * all sub-domains if you need to access States across different sub-domains (defaults to null which uses the same
 * domain the page is running on including the 'www' like 'www.extjs.com')
 * @cfg {Boolean} secure True if the site is using SSL (defaults to false)
 * @constructor
 * Create a new Jaffa.state.WidgetStateProvider
 * @param {Object} config The configuration object
 */
Jaffa.state.WidgetStateProvider = function(config){
  var states = config.states || {};
  config.states = null;
  Jaffa.state.WidgetStateProvider.superclass.constructor.call(this);
  Ext.apply(this, config);
  for (var i=0; i<states.length; i++) {
    this.state[states[i][0]] = states[i][1];
  }
};

Ext.extend(Jaffa.state.WidgetStateProvider, Ext.state.Provider, {
  // private
  set : function(name, value){
    if(typeof value == "undefined" || value === null){
      this.clear(name);
      return;
    }
    // filter out ext-comp widget settings
    var len = 'ext-comp-'.length;
    if (name.length > len && name.substr(0,len)=='ext-comp-') {
      return;
    }
    if (value.collapsed) {
      // there is a bug that the fields are not shown correctly when panel starts with collapsed mode.
      value.collapsed = false;
    }
    // add the state on the server
    var pvd = this;
    var opt = {
      params: {
        pageRef: this.pageRef,
        eventId: 'save',
        name: name,
        data: encodeURI(Ext.encode(value))
      },
      method: 'POST',
      success: function(response, optional) {
        if (response.status==200)
          Jaffa.state.WidgetStateProvider.superclass.set.call(pvd, name, value);
      }
    };
    this.submit(opt);
  },

  // private
  clear : function(name){
    // remove the state from server
    var pvd = this;
    var opt = {
      params: {
        pageRef: this.pageRef,
        eventId: 'delete',
        name: name
      },
      method: 'POST',
      success: function(response, optional) {
        if (response.status==200)
          Jaffa.state.WidgetStateProvider.superclass.clear.call(pvd, name);
      }
    };
    this.submit(opt);
  },

  // private
  clearAll : function(){
    // remove the state from server
    var pvd = this;
    var opt = {
      params: {
        pageRef: this.pageRef,
        eventId: 'deleteAll'
      },
      method: 'POST',
      success: function(response, optional) {
        if (response.status==200)
          Jaffa.state.WidgetStateProvider.superclass.clear.call(pvd, null);
      }
    };
    this.submit(opt);
  },

  submit: function(opt) {
    var conn = new Ext.data.Connection({
      url: this.url,
      method: this.method
    });
    if (opt.url===null) opt.url = this.url;
    conn.request(opt);
  }  
});