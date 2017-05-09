/** @class Jaffa.finder.FinderHeader
 * Header for finder screen with summary info
 *
 * @depends Ext.ux.StartMenu,
 */
Jaffa.finder.FinderHeader = Ext.extend(Ext.Panel, {

    /** @cfg closeScreenTitleText {String}
     * Text for root menu for all screens
     */
    closeScreenTitleText : 'Confirmation',

    /** @cfg {String} closeScreenText
     * Text for root menu for all screens
     */
    closeScreenText : 'Are you sure you want to close?',

    /** @cfg {String} closeTipText
     * Text for close button hover over tip
     */
    closeTipText : 'Close Window',

    /** @cfg {boolean} useCloseButton
     * Set to false to suppress the close button
     */
    useCloseButton : true,

    /** @cfg {boolean} autoRefreshDelay
     * Delay in seconds for auto refresh. If 0 don't enable autorefresh.
     */
    autoRefreshDelay : 0,

    /** @cfg {String} refreshTipText
     * Text for refresh button hover over tip
     */
    refreshTipText : 'Reload the Record',

    /** @cfg {String} optionsTipText
     * Text for options button hover over tip
     */
    optionsTipText : 'Options',

    /** @cfg {String} startAutoRefresh
     * Text for start auto refresh menu option
     */
    startAutoRefreshText : 'Start Auto-Refresh',

    /** @cfg {String} stopAutoRefresh
     * Text for stop auto refresh menu option
     */
    stopAutoRefreshText : 'Stop Auto-Refresh',

    /** @cfg {String} removePersonalizationsText
     * Text for remove personalizations menu option
     */
    removePersonalizationsText: 'Remove Personalizations',

    /** @cfg {String} personalizationsRemovedReloadText
     * Text for personalizations removed reload button text
     */
    personalizationsRemovedReloadText: 'Reload',

    /** @cfg {String} personalizationsRemovedTitle
     * Text for personalizations removed title
     */
    personalizationsRemovedTitle: 'Personalizations Removed',

    /** @cfg {String} personalizationsRemovedMsg
     * Text for personalizations removed message
     */
    personalizationsRemovedMsg: 'Personalizations have been deleted and will be viewable the next time the screen loads.',

    /** @cfg {String} userId
     * This is the string that should contain the user name / id that is displayed on the start menu.
     * Currently this is set in loadJaffaRIA.jsp based on the logged in user's context
     */
    userId: 'unknown',

    /** @cfg {String} rootMenuText
     * Text for root menu for all screens
     */
    rootMenuText: 'All Screens',

    /** @cfg {String} emptySearchText
     * Empty text for screen search box
     */
    emptySearchText: 'Find Screen',

    /** @cfg {String} logoutTitleText
     * Title of the dialogue box on log out
     */
    logoutTitleText: 'Log out Confirmation',

    /** @cfg {String} logoutText
     * Message Text of the dialogue box on log out
     */
    logoutText: 'This will log this window out of the application and any other active windows will stop working once you are logged out. Continue and Log Out?',

    /** @cfg {String} logoutUrl
     * URL to go to on log out. Defaults to 'logout.dop' as used in Jaffa Web 1.0
     */
    logoutUrl:'logout.dop',

    /**
     * @cfg  {Boolean} enableParentWinRefresh
     * Flag to determine parent window refresh (defaults to false).
     */
    parentWinRefresh : false,

    /** @cfg {String} autoWidth
     * To set autoWidth when using windows Snap feature.
     */
    autoWidth:true,

    /** @cfg {String} autoHeight
     * To set autoHeight when using windows Snap feature.
     */
    autoHeight:true,

    /** @cfg {String} autoScroll
     * To set autoScroll when using windows Snap feature.
     */
    autoScroll:true,
    /**
     * @cfg  {String} appVersion
     * This should be set to the version number that should be displayed in the tool menu.
     */
    appVersion : '',
	
	alertPromtCloseWindowMsg : 'Unable to automatically close this window, please close it yourself.',
	
	titleNewRecordText : 'NEW RECORD',
	
	searchInputTitleText : 'Search',
	
	logoutButtonText: 'Logout',
	
	settingsButtonText : 'Settings',
	
	allProgramsButtonText : 'All Programs',
	
	autoRefreshDelaySeconds : 's',

    /** @cfg {Object} settingsComponent
     * This should be a command object as returned by the menuIndex_json.jsp.
     * Typically this would have parameters like <ul>
     * <li>url - Default "usersecurity/user/usermaintenance/main.jsp"
     * <li>params - Default "_selfUpdate=true"
     * <li>target - Default "_blank"
     * <li>component - Default "UserSecurity.User.UserMaintenance"
     * </ul>
     */
    settingsComponent: {
        url:"usersecurity/user/usermaintenance/main.jsp",
        params: "_selfUpdate=true",
        target: "_blank",
        component: "UserSecurity.User.UserMaintenance"
    },

    defaultToolbar: ['tbStartMenu','tbTitle','tbRefresh','tbOptions','tbClose'],

    /**
     * @constructor
     * @param {Object} config a configuration object.
     */
    constructor: function (config) {
        Ext.applyIf(config || {}, {
            //height:16,
            autoHeight:true,
            border: false,
            bodyBorder: false,
            bodyCssClass: 'jaffa-header-body',
            bodyStyle: 'padding: 2px',
            frame: false,
            style:'padding:0',
            region:'north',
            header: false
        });

        // This needs to be done in the constructor because the label editor needs a handle on this stuff
        // prior to the super() and the initComponent() where we normally do this.
        if(config.headerTitleToken) {
            this.headerTitleToken = config.headerTitleToken;
            config.headerTitle = Labels.get(config.headerTitleToken);
        }

        // do not include close button if it is not needed
        if (config.useCloseButton === false) {
            var i = this.defaultToolbar.indexOf('tbClose');
            if (i>=0) this.defaultToolbar.splice(i, 1);
        }

        this._configToolbarActions(config);
        //console.info("Create Header Toolbar", this, config);

        if(self.opener) {
            try {
                this.openerUrl = self.opener.document.location.href;
            } catch (e) {
                // In case of single sign-on, the parent window belongs to another web-site,
                // due to which 'self.opener.document' will result in a 'Permission denied to get property HTMLDocument.location' error.
                // Ignore the error
            }
        }
        //console.dir(config);
        Jaffa.finder.FinderHeader.superclass.constructor.call(this, config);
    },

    onRender: function(ct, p){
        Jaffa.finder.FinderHeader.superclass.onRender.call(this,ct,p);
        this.updateHeader();
    },

    initComponent: function() {
        this.addEvents(
            /** @event beforerefresh
             * Fires before performing the refresh when the refresh button on the toolbar is pressed
             * @param {Object} this
             */
            'beforerefresh',

            /** @event refresh
             * Fires after a refresh has been done, assuming the 'beforerefresh' event returned true
             * @param {Object} this
             */
            'refresh'
        );
        Jaffa.finder.FinderHeader.superclass.initComponent.call(this);
    },

    /**
     * @method removePersonalizations
     * The default behavior is to remove all personalised state that has been applied to a form.
     */
    removePersonalizations: function(btn,evt) {
        var state = Ext.state.Manager.clearAll();
        var cancelText = Ext.MessageBox.buttonText.cancel;
        Ext.MessageBox.buttonText.cancel = this.personalizationsRemovedReloadText;
        Ext.MessageBox.show({
            title: this.personalizationsRemovedTitle,
            msg : this.personalizationsRemovedMsg,
            buttons : Ext.MessageBox.OKCANCEL,
            icon : Ext.MessageBox.INFO,
            fn : function(btn, text) {
                if (btn == 'cancel'){
                    window.location.reload();
                }
            }
        });
        Ext.MessageBox.buttonText.cancel=cancelText;
        return true;
    },

    /**
     * @method onRefresh
     * The default behavior is to get the finderContainer's store and call loadMore(true)
     *
     * This has a listener for 'beforerefresh' to intercept the default action. Can be used to
     * confirm if the user want to do this action. If this event returns false the refresh will not be performed
     *
     * Also fires 'refresh' event when a refresh has been done, note as the load is done by AJAX,
     * this event may be fired prior to the load in the store completing
     */
    onRefresh: function(btn,evt) {
        if (this.fireEvent('beforerefresh', this) !== false) {
            var cmp = Ext.getCmp('finderContainer');
            if (cmp && cmp.store && !cmp.resultsPanel.disabled) {
                cmp.store.reload();
            }
            this.fireEvent('refresh',this,btn,evt);
        }
    },

    /**
     * @method autoRefresh
     *
     * This method is will cause the finder to refresh at the supplied auro refresh rate.
     *
     */

    autoRefreshing: false,
    autoRefresh: function(btn,evt,activate) {
        var tbar = this.topToolbar;
        if (activate && !this.autoRefreshing){
            this.autoRefreshing = true;
            if(tbar && tbar.refreshBtn){
                tbar.refreshBtn.setIconClass('tb-refresh-start');
            }

            var refreshNow = function(scope){
                if (scope.autoRefreshing) {
                    scope.onRefresh(btn,evt);
                    refreshNow.defer(this.autoRefreshDelay*1000, scope, [scope]);
                }
            }
            refreshNow(this);
            Ext.state.Manager.set('autoRefresh','true');
        }

        if (!activate){
            this.autoRefreshing = false;
            if(tbar && tbar.refreshBtn){
                tbar.refreshBtn.setIconClass('tb-refresh-stop');
            }
            Ext.state.Manager.set('autoRefresh','false');
        }
    },


    /**
     * @method onClose
     * The default behavior is to confirm with the user they want to close the window, then close it.
     */
    onClose: function() {
        if (this.fireEvent('beforeclose', this) !== false) {
            var headerPanel = this;
            var panelDirty = false;

            // Check if this screen has a controller, if it does and the controller is dirty ask for confirmation
            if (this.controller && this.controller.isDirty()){
                panelDirty = true;
            }

            // Check if this screen is an inline editor, if it is and a record is being edited, ask for confirmation
            if (this && this.ownerCt && this.ownerCt.finderContainer && this.ownerCt.finderContainer.resultsPanel && this.ownerCt.finderContainer.resultsPanel.editing){
                panelDirty = true;
            }

            //  Check if screen is an integrated finder maintenance panel check the controller on teh maintenance panel, if it's dirty ask for confirmation
            if (this && this.ownerCt && this.ownerCt.layout && this.ownerCt.layout.south && this.ownerCt.layout.south.panel && this.ownerCt.layout.south.panel.controller && this.ownerCt.layout.south.panel.controller.isDirty()){
                panelDirty = true;
            }

            if (panelDirty){
                Ext.MessageBox.show({
                    title : this.closeScreenTitleText,
                    msg : this.closeScreenText,
                    width : 400,
                    buttons : Ext.MessageBox.YESNO,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn) {
                        if (btn == 'yes') {
                            try {
                                headerPanel.closeWindowOk();
                            } catch (e) {
                                alert(this.alertPromtCloseWindowMsg);
                            }
                        }
                    }
                });
            }else{
                try {
                    headerPanel.closeWindowOk();
                } catch (e) {
                    alert(this.alertPromtCloseWindowMsg);
                }
            }
        }
    },

    /**
     * The default behaviour is to close the window. This
     * assumes that the window to close was opened as a new browser window and is
     * no longer needed, as is common when opening one WEB2.0 window from another.
     *
     * This method also provides provides a way to close the WEB2.0 screen
     * and refresh the parent web 1.0 screen or return to the WEB1.0 screen
     * in its original state based on parentWinRefresh flag
     */
    closeWindowOk: function() {
        var url;

        if(this.parentWinRefresh && !this.refreshParentWindow()){
            url = this.calculateUrlToRefreshParentPage();
        }
        // When a URL is supplied open it in the same window, otherwise
        // close the window.
        if (!Ext.isEmpty(url)) {
            window.open(Jaffa.data.getAppRootUrl() + url, '_self');
        }
        else {
            try {
                window.self.open('','_self','');
                window.close();
            }
            catch (e) {
                alert(this.alertPromtCloseWindowMsg);
            }
        }
    },

    tbClose: function(config) {
        return {
            xtype: 'tbbutton',
            tooltip: this.closeTipText,
            tooltipType: 'title',
            iconCls: 'tb-close',
            scope: this,
            handler: this.onClose
        };
    },

    tbRefresh : function(config) {
        if (config.autoRefreshDelay > 0) {
            return {
                xtype: 'splitbutton',
                itemId : 'refreshBtn',
                ref : 'refreshBtn',
                tooltip: this.refreshTipText,
                tooltipType: 'title',
                iconCls: 'tb-refresh-stop',
                scope: this,
                handler: this.onRefresh,
                listeners:{
                    scope: this,
                    render: function(){
                        if (Ext.state.Manager.get('autoRefresh')=='true'){
                            this.autoRefresh(null, null, true);
                        }
                    }
                },
                menu: {
                    items: [{
                        text: this.startAutoRefreshText + ' (' + config.autoRefreshDelay + this.autoRefreshDelaySeconds +')',
                        tooltipType: 'title',
                        iconCls: 'tb-refresh-start',
                        scope: this,
                        handler: function(btn, evt){
                            this.autoRefresh(btn, evt, true);
                        }
                    }, {
                        text: this.stopAutoRefreshText,
                        tooltipType: 'title',
                        iconCls: 'tb-refresh-stop',
                        scope: this,
                        handler: function(btn, evt){
                            this.autoRefresh(btn, evt, false);
                        }
                    }]
                }
            };
        }else{
            return {
                xtype: 'tbbutton',
                itemId : 'refreshBtn',
                ref : 'refreshBtn',
                tooltip: this.refreshTipText,
                tooltipType: 'title',
                iconCls: 'tb-refresh',
                scope: this,
                handler: (config&&config.onRefresh)?config.onRefresh:this.onRefresh
            };
        }
    },

    tbOptions : function(config) {
        return {
            xtype: 'splitbutton',
            itemId : 'optionsBtn',
            ref : 'optionsBtn',
            tooltip: this.optionsTipText,
            tooltipType: 'title',
            iconCls: 'tb-options',
            scope: this,
            menu: {
                items: [{
                    text: this.removePersonalizationsText,
                    tooltipType: 'title',
                    scope: this,
                    handler: function(btn, evt){
                        this.removePersonalizations();
                    }
                }]
            }
        };
    },

    /**
     * Returns a 'tbtext' config object that can be used in a toolbar to create the title of the screen
     * by default it will use the 'title' property from the config.
     *
     * This include a default extension to set the width to 100% on render
     */
    tbTitle: function(config) {
        if (config) {
            this.headerTitle = config.headerTitle || this.headerTitle || config.title || this.title || '';
        }
        return {
            xtype:'tbtext',
            ref: 'tbTitle',
            id: (config && config.id) ? config.id + 'TbTitle' : 'headerTitle',
            cls: 'headerTitle',
            text: '<span>' + this.headerTitle +'</span>',
            listeners: {
                render: function() {
                    this.container.dom.style.width="100%";
                }
            }
        };
    },

    /**
     * Returns a 'button' config object that can be used in a toolbar to provide a 'StartMenu' button that will give
     * access to all screens and other features
     *
     * Only when the start menu is displayed does it attempt an AJAX query to get all the menu options.
     * So there may be a delay seeing the 'All Screens' option on initial render.
     */
    tbStartMenu: function (config) {
        config = config || {};
        return {
            ref: 'startMenu',
            xtype:'button',
            style: config.style,
            iconCls: 'startMenu',
            menu: {
                xtype: 'startmenu',
                iconCls: 'startMenu-user',
                height: 300,
                shadow: true,
                title: this.userId,
                width: 300,
                toolItems: [{
                    text: this.settingsButtonText,
                    iconCls:'startMenu-settings',
                    scope:this,
                    handler: function(){
                        Jaffa.dashboard.MenuIndex.invokeOption(this.settingsComponent);
                    }
                },'-',{
                    text: this.logoutButtonText,
                    iconCls:'startMenu-logout',
                    scope:this,
                    handler: function() {
                        Ext.MessageBox.show({
                            title : this.logoutTitleText,//Labels.get('label.jaffa.jaffaRIA.Finder.ConfirmationTitle'),
                            msg : this.logoutText, //Labels.get('label.jaffa.jaffaRIA.Finder.ConfirmationMessage'),
                            width : 400,
                            buttons : Ext.MessageBox.YESNO,
                            icon : Ext.MessageBox.QUESTION,
                            fn : function(btn) {
                                if (btn == 'yes') {
                                    window.location=params.appCtx + '/' + this.logoutUrl;
                                }
                            },
                            scope: this
                        });
                    }
                }],
                items: [{
                    ref: 'programMenu',
                    text: this.allProgramsButtonText,
                    iconCls:'startMenu-screen',
                    menu: []
                },'-',{
                    xtype:'textfield',
                    emptyText: this.searchInputTitleText,
                    width:180,
                    listeners:{
                        'keyup' : {
                            buffer: 350,
                            fn: function(fld,evt){
                                // Make sure this is the correct type of event, seems there are many misfires!
                                if(evt.type!='keyup') return;
                                this.ownerCt.filterMenu(fld.getValue());
                            }
                        }
                    }
                }],
                listeners: {
                    'beforerender' : function() {
                        this.ownerCt.ownerCt.ownerCt.startMenu = this;
                        this.rootNode = new Jaffa.dashboard.MenuIndex({appCtx:params.appCtx, startMenu: this});
                    },
                    'render': function(){
                        var appVersion;
                        if (this.ownerCt && this.ownerCt.ownerCt && this.ownerCt.ownerCt.ownerCt)
                            appVersion = this.ownerCt.ownerCt.ownerCt.appVersion;
                        if (appVersion){
                            var txt = document.createElement("span");
                            txt.innerHTML=appVersion;
                            txt.style.color="Gray";
                            txt.style.position="absolute";
                            txt.style.bottom="0";
                            txt.style.right="5";
                            this.toolsPanel.dom.appendChild(txt);
                        }
                    }
                }
            }
        };
    },

    /** This processes the toolbar and tries to look up any 'string' objects that begin with 'tb'
     * as an internal class function that will return the config for that object
     *
     * If not toolbar is provided in the config, then the default one is used from 'defaultToolbar'
     */
    _configToolbarActions: function(config) {
        var bar = (config.tbar || this.defaultToolbar).slice(0);
        config.tbar = [];
        for(var i=0; i<bar.length; i++) {
            console.debug("Adding toolbar item:",bar[i]);
            if(Ext.isString(bar[i]) && bar[i].match(/^tb/) && this[bar[i]]) {
                var tb = this[bar[i]](config);
                if(tb) {
                    config.tbar.push(tb);
                    console.debug("Inject toolbar item:",bar[i],tb);
                }
            } else
                config.tbar.push(bar[i]);
        }
    },

    /**
     * If the header has custom stuff beyond what is in the panel
     * then this function can be overridden to deal with that.
     *
     * This is called on initial render, and each time the controller
     * reloads the data.
     *
     * By default, this invokes the updateHeaderTitle() and updateHeaderBody() functions.
     */
    updateHeader: function() {
        this.updateHeaderTitle();
        this.updateHeaderBody();
    },

    /**
     * If the header has custom stuff beyond what is in the panel
     * then this function can be overridden to deal with that.
     *
     * This is called on initial render, and each time the controller
     * reloads the data.
     *
     * By default we create an element called "headerTitle" whose
     * content should probably be modified by this method
     */
    updateHeaderTitle: function() {
        this.headerTitle = this.headerTitle || this.title;
        // Update text in the headerTitle element
        if (this.headerTitle) {
            var headerTitleEl = Ext.getCmp('headerTitle');
            if (headerTitleEl) {
                var text = '<span class="headerTitle-text">' + this.headerTitle + this.generateHeaderTitleSuffix() + '</span>';
                headerTitleEl.setText(text);
            }
        }
    },


    /**
     * If the header has custom stuff beyond what is in the panel
     * then this function can be overridden to deal with that.
     *
     * This is called on initial render, and each time the controller
     * reloads the data.
     */
    updateHeaderBody: Ext.emptyFn,

    /**
     * If the header has custom stuff beyond what is in the panel
     * then this function can be overridden to deal with that.
     *
     * This is called on initial render, and each time the controller
     * reloads the data.
     *
     * By default this will return the value of the key field for an existing record,
     * or the text 'NEW RECORD' for a new record.
     */
    generateHeaderTitleSuffix: function() {
        var text = '';
        if (this.controller) {  //Usage in Maintenance Panel
            text = ' : ';
            if (this.controller.isLoaded && this.controller.model) {
                text += this.controller.model.keys;
            } else {
                text += this.titleNewRecordText;
            }
        }
        return text;
    },

    /**
     * Refreshes parent window by clicking Refresh button on that page.
     */
    refreshParentWindow: function(){
        if (this.isParentWindowAvailable()) {
            // locate the refresh link
            var refBtn, tns= self.opener.document.getElementsByTagName('a'); // Web 1.0 support
            for (var i=0; i<tns.length; i++) {
                if (tns[i].title=='Refresh') {
                    refBtn = tns[i];
                }
            }
            if(refBtn){
                // get the onclick and href values.
                var ock, hrfun;
                for (var i=0; i<refBtn.attributes.length; i++) {
                    if (refBtn.attributes[i].name=='onclick') {
                        ock = refBtn.attributes[i].nodeValue;
                    } else if (refBtn.attributes[i].name=='href') {
                        hrfun = refBtn.attributes[i].nodeValue;
                    }
                }
                self.opener.eval(unescape(ock));
                self.opener.eval(unescape(hrfun));
            } else if(Ext.getCmp('viewport') && Ext.getCmp('viewport').reloadParent){ // Web 2.0 support
                Ext.getCmp('viewport').reloadParent();
            }
            return true;
        } else {
            return false;
        }
    },
    /**
     * Calculates URL to return to web 1.0 screen. To do this, the
     * return-to path must be set in params.returnToActionPath and the
     * return-to component Id can be set in params.returnToFormKey_componentId.
     * Alternately, a final URL can be set in params.finalUrl.  The URL that is
     * formed from these params is opened in the same browser window.
     */
    calculateUrlToRefreshParentPage : function() {
        var url = '';
        if (params && params.returnToActionPath) {
            url = params.returnToActionPath+'.do';
            if (params.returnToFormKey) {
                if (params.returnToFormKey.componentId) {
                    url = url+'?componentId='+params.returnToFormKey.componentId+'&eventId='+'Refresh;Clicked';
                }
            }
        } else if (params.finalUrl) {
            url = '/startComponent.do?finalUrl='+escape(params.finalUrl);
        }
        return url;
    },
    /**
     * Calculates whether the parent window is available or not
     */
    isParentWindowAvailable : function() {
        var pAv = false;
        try {
            pAv = self.opener && (self != self.opener) && (self.opener.location.href == this.openerUrl);
        } catch (e) {}
        return pAv;
    }

});

Ext.reg('finderheader',Jaffa.finder.FinderHeader);