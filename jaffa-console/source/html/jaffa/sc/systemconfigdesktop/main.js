Ext.onReady(function () {
    Ext.QuickTips.init();

    /**
     * The DWR call to the WebServiceDocService and
     * load the ApiPanel with the returned json data.
     */
    if (security.hasDomainInquiry)
        DomainModelDocService.loadTree(loadTreeFunction);

    /**
     * The DWR call to the SOAEventMetaDataService and
     * load the SoaEventTreePanel with the returned json data.
     */
    if (security.hasSoaEventInquiry)
        SOAEventMetaDataService.loadTree(loadSoaEventTree);

    Jaffa.SC.DesktopController = new Jaffa.component.CRUDController({
        _dirty: false,
        isDirty: function () {
            return this._dirty;
        },
        loadMaskComponent: Ext.get(document.body),
        setDirty: function (flag) {
            //set dirty
            if (flag && !this._dirty) {
                viewport.tabPanel.items.each(
                    function (tab) {
                        if (tab.getTopToolbar() && tab.getTopToolbar().getComponent('save'))
                            tab.getTopToolbar().getComponent('save').enable();
                    }
                );
                this._dirty = true;
            }
            //set clean
            if (!flag && this._dirty) {
                viewport.tabPanel.items.each(
                    function (tab) {
                        if (tab.getTopToolbar() && tab.getTopToolbar().getComponent('save'))
                            tab.getTopToolbar().getComponent('save').disable();
                    }
                );
                this._dirty = false;
            }
        },
        save: function () {
            var updatedRules = {};
            var tabError;
            var flexError = false;
            viewport.tabPanel.items.each(
                function (tab) {
                    if (tab.contentType == 'BUSINESSRULES') {
                        if (tab.activeEditor) {
                            tabError = true;
                            tab.ownerCt.activate(tab);
                        }
                    } else if (tab.contentType == 'FLEXFIELDS') {
                        if (!this.validateFlexData()) {
                            flexError = true;
                            tab.ownerCt.activate(tab);
                        }
                    }
                }
            );
            if (tabError) {
                Ext.MessageBox.show({
                    title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SaveError'),
                    msg: Labels.get('label.Jaffa.SC.SystemConfigDesktop.InvalidValue'),
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                });
                return false;
            } else if (flexError) {
                return false;
            }
            Ext.get(document.body).mask(Labels.get('label.jaffaRIA.proxyAction.Saving'), 'x-mask-loading');
            viewport.tabPanel.items.each(
                function (tab) {
                    if (tab.contentType == 'BUSINESSRULES') {
                        tab.store.each(
                            function (rec) {
                                if (rec.dirty) {
                                    if (rec.get('varRule') !== Jaffa.SC.BusinessRules.varRules[rec.get('rule')]) {
                                        if (!updatedRules.varRules) updatedRules.varRules = {};
                                        updatedRules['varRules'][rec.get('rule')] = rec.get('varRule').toString();
                                    }
                                    if (rec.get('globalRule') !== Jaffa.SC.BusinessRules.globalRules[rec.get('rule')]) {
                                        if (!updatedRules.globalRules) updatedRules.globalRules = {};
                                        updatedRules['globalRules'][rec.get('rule')] = rec.get('globalRule').toString();
                                    }
                                }
                            }
                        );
                    } else if (tab.contentType == 'FLEXFIELDS') {
                        tab.saveFlexData();
                    }
                }
            );
            var opt = {
                params: {
                    pageRef: this.pageRef,
                    eventId: 'save',
                    globalRules: Ext.util.JSON.encode(updatedRules.globalRules),
                    varRules: Ext.util.JSON.encode(updatedRules.varRules),
                    userRules: Ext.util.JSON.encode(updatedRules.userRules)
                },
                method: 'POST',
                success: function (response, optional) {
                    viewport.tabPanel.items.each(
                        function (tab) {
                            if (tab.contentType == 'BUSINESSRULES') {
                                tab.setDirty(false);
                            } else if (tab.contentType == 'FLEXFIELDS') {
                                tab.setDirty(false);
                            }
                        }
                    );
                    Jaffa.SC.DesktopController.setDirty(false);

                    var globalRules = Ext.util.JSON.decode(optional.params.globalRules);
                    var varRules = Ext.util.JSON.decode(optional.params.varRules);
                    var userRules = Ext.util.JSON.decode(optional.params.userRules);
                    var prop;
                    if (globalRules != "null") {
                        for (prop in globalRules) {
                            Jaffa.SC.BusinessRules.globalRules[prop] = globalRules[prop];
                        }
                    }
                    if (varRules != "null") {
                        for (prop in varRules) {
                            Jaffa.SC.BusinessRules.varRules[prop] = varRules[prop];
                        }
                    }
                    if (userRules != "null") {
                        for (prop in userRules) {
                            Jaffa.SC.BusinessRules.userRules[prop] = userRules[prop];
                        }
                    }
                    Ext.get(document.body).unmask();
                }
            };
            var conn = new Ext.data.Connection({
                url: 'jaffa/sc/systemconfigdesktop/context.jsp'
            });
            if (opt.url === null) opt.url = this.url;
            conn.request(opt);
        },
        updateRule: function (updatedBy, ruleName, varValue, globalValue) {
            viewport.tabPanel.items.each(function (tab) {
                if (tab.contentType == 'BUSINESSRULES' && tab.id != updatedBy) {
                    var matchingRecord = tab.store.findExact('rule', ruleName);
                    if (matchingRecord >= 0) {
                        var foundRecord = tab.store.getAt(matchingRecord);
                        if (foundRecord.get('varRule') != varValue) foundRecord.set('varRule', varValue);
                        if (foundRecord.get('globalRule') != globalValue) foundRecord.set('globalRule', globalValue);
                    }
                }
            });
        }
    });

    var rulesLoader = Ext.extend(Ext.util.Observable, {
        isLoaded: false,
        isLoading: false,
        initComponent: function () {
            Ext.util.Observable.superclass.initComponent.call(this);
            this.addEvents('afterload');
        },
        load: function () {
            if (!this.isLoading && !this.isLoaded) {
                this.isLoading = true;
                var onLoad = function (response, options) {
                    ClassMetaData.BusinessRules = eval(response.responseText);
                    Ext.Ajax.request({
                        url: 'jaffa/sc/systemconfigdesktop/context.jsp',
                        success: this.loadSuccess,
                        failure: this.loadError,
                        scope: this
                    });
                };

                //This call can take a long time, especially if called while loading flex data. Set high timeout to prevent load error.
                Ext.Ajax.timeout = 600000;

                Ext.Ajax.request({
                    url: 'jaffa/sc/systemconfigdesktop/annotations.jsp',
                    success: onLoad,
                    failure: this.loadAnnoError,
                    scope: this
                });
            }
        },
        loadSuccess: function (response, options) {
            Jaffa.SC.BusinessRules = eval(response.responseText);
            var uniqueRuleSet = {};
            var foundMeta = false;
            if (ClassMetaData && ClassMetaData.BusinessRules && ClassMetaData.BusinessRules.fields) foundMeta = true;
            if (foundMeta) {
                for (prop in ClassMetaData.BusinessRules.fields) {
                    if (prop.indexOf('*') < 0) {
                        uniqueRuleSet[prop] = true;
                    }
                }
            }

            //todo Remove this code - test to find what rules need documented
            /*
             for (propertyType in Jaffa.SC.BusinessRules) {
             for (property in Jaffa.SC.BusinessRules[propertyType]) {
             if (ClassMetaData.BusinessRules.fields[property]){

             }else{
             ClassMetaData.BusinessRules.missing[ClassMetaData.BusinessRules.missing.length]=property;
             }
             }
             }*/

            Jaffa.SC.BusinessRules.uniqueRuleSet = uniqueRuleSet;
            this.isLoaded = true;
            this.fireEvent('afterload', true);
            this.isLoading = false;
        },
        loadError: function () {
            this.fireEvent('afterload', false);
            this.isLoading = false;
            alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.FailedLoadBusinessRuleDataRefreshScreenAlertMsg'));
        },
        loadAnnoError: function () {
            this.fireEvent('afterload', false);
            this.isLoading = false;
            alert(Labels.get('label.Jaffa.SC.SystemConfigDesktop.FailedLoadBusinessRuleMetaDataRefreshScreenAlertMsg'));
        }
    });

    Jaffa.SC.RulesLoader = new rulesLoader();

    viewport = new Jaffa.maintenance.Viewport({
        controller: Jaffa.SC.DesktopController,
        nav: new Jaffa.maintenance.NavPanel({
            width: 280
        }),
        header: new Product1.maintenance.HeaderPanel({
            headerTitle: Labels.get('title.Jaffa.SC.SystemConfigDesktop'),
            generateHeaderTitleSuffix: function () {
                return '';
            },
            helpPathName: 'jaffa/sc/systemconfigdesktop',
            onRefresh: function (btn, evt) {
                if (Jaffa.SC.DesktopController.isDirty()) {
                    Ext.MessageBox.show({
                        titleToken: 'label.Jaffa.SC.SystemConfigDesktop.RefreshWarning',
                        msg: Labels.get('label.Jaffa.SC.SystemConfigDesktop.RefreshWarningMessage'),
                        width: 400,
                        buttons: Ext.MessageBox.YESNO,
                        icon: Ext.MessageBox.QUESTION,
                        scope: this,
                        fn: function (btn) {
                            if (btn == 'yes') {
                                Ext.get(document.body).mask(this.controller.loadMaskText, 'x-mask-loading');
                                viewport.tabPanel.items.each(
                                    function (tab) {
                                        tab.refresh(evt);
                                    }
                                );
                                Jaffa.SC.DesktopController.setDirty(false);
                                Ext.defer(function () {
                                    Ext.get(document.body).unmask();
                                }, 500);
                            }
                        }
                    });
                } else {
                    Ext.get(document.body).mask(this.controller.loadMaskText, 'x-mask-loading');
                    viewport.tabPanel.items.each(
                        function (tab) {
                            tab.refresh(evt);
                        }
                    );
                    Jaffa.SC.DesktopController.setDirty(false);
                    Ext.defer(function () {
                        Ext.get(document.body).unmask();
                    }, 500);
                }
            }
        })
    });

    viewport.addTabPanel(new Ext.Panel({
            titleToken: 'title.Jaffa.SC.SystemConfigDesktop.Introduction',
            id: 'intro',
            closable: false,
            refresh: Ext.emptyFn,
            bodyStyle: 'background-color:white;padding: 10px;',
            html: "<div><h1>"+Labels.get('title.Jaffa.SC.SystemConfigDesktop')+"</h1><br>" +
                Labels.get('label.Jaffa.SC.SystemConfigDesktop.SystemConfigDesktopSummary')+"<br>" +
                /*(security.hasFlexFieldInquiry ? "<h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFieldEditor')+"</h2><br>" +
                    Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFieldsSummary')+"<br>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFieldTree')+"</h2><img src='jaffa/sc/imgs/flexfieldtree.PNG'></div>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.FlexFieldEditorPanel')+"</h2><img src='jaffa/sc/imgs/flexfieldtab.PNG'></div><br class='clearboth'>" : "") +*/
                (security.hasRuleInquiry ? "<h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleEditor')+"</h2><br>" +
					Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleEditorSummary')+"<br>" +
                    "<ul><li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleTreeDesc')+"</li>" +
                    "<li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleSearchPanelDesc')+"</li>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleTree')+"</h2><img src='jaffa/sc/imgs/busruletree.PNG'></div>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.BusinessRuleSearchPanel')+"</h2><img src='jaffa/sc/imgs/busrulesearch.PNG'></div>" +
                    "<br class='clearboth'>" : "") +
                (security.hasSoaEventInquiry ? "<h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEvents')+"</h2><br>" +
                    Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventsSummary')+"<br>" +
                    "<ul><li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventTreeDesc')+"</li>" +
                    "<li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventDetailsDesc')+"</li>" +
                    "<li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventTestPanelDesc')+"</li></ul>" +
                    "<li class='intro-list'>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.EventSummaryPanelDesc')+"</li></ul>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventTree')+"</h2><img src='jaffa/sc/imgs/soatree.PNG'></div>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventDetails')+"</h2><img src='jaffa/sc/imgs/soadetails.PNG'></div>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.SOAEventTestPanel')+"</h2><img src='jaffa/sc/imgs/soatest.PNG'></div>" +
                    "<div class='intro-img'><h2>"+Labels.get('label.Jaffa.SC.SystemConfigDesktop.EventSummaryPanel')+"</h2><img src='jaffa/sc/imgs/eventsummary.PNG'></div>" : "") +
                "</div>"
        }
    ));

    // the SOA events tree
    var soaEventTree;

    // the SOA events service
    var soaEventService = new Ext.data.DWRProxy2(SOAEventMetaDataService.loadTree);

    /**
     * Initializes and loads the SOA events tree with SOA event data.
     * This will also check all SOA events to see if they have an open details panel, if they do the details
     * panel will also be updated.
     *
     * @param json the SOA event data to load the tree with
     */
    function loadSoaEventTree(json) {

        /**
         * Intializing SoaEventTreePanel
         */
        soaEventTree = new SoaEventTreePanel(json);

        /**
         * The onlick event to load the selected
         * service class details into mainpanel in
         * new tab.
         */
        soaEventTree.on('click', function (node, e) {
            if (node.attributes.isSelectable) {
                e.stopEvent();

                // try to find a tab panel with the event's name
                var tab = viewport.tabPanel.findById(node.attributes.soaEventName);

                // if we found a tab panel with the event name, activate it
                // else, create a new tab panel and load it into the view
                if (tab) {
                    viewport.tabPanel.activate(tab);
                } else {

                    // create an event details panel
                    var detailsPanel = new SoaEventDetailPanel({
                        id: node.attributes.soaEventName,
                        title: node.attributes.soaEventName,
                        eventName: node.attributes.soaEventName,
                        label: node.attributes.label,
                        description: node.attributes.description,
                        status: node.attributes.isEnabled,
                        eventParams: node.attributes.soaEventParams
                    });

                    // add a handler so the details panel can tell us when to refresh the tree
                    detailsPanel.on('reloadTree', function () {
                        if (security.hasSoaEventInquiry) {
                            soaEventService.load(null,
                                {read: function (r) {
                                    return r;
                                }},
                                reloadSoaEventTree,
                                this
                            );
                        }
                    });

                    // add the details panel to the tab in the viewport
                    viewport.addTabPanel(detailsPanel);
                }
            }
        });

        // Add the SOA events tree panel to the AccordionPanel
        viewport.addAccordionPanel(soaEventTree);

        // force the view to refresh its layout
        viewport.doLayout();
    }

    /**
     * Reloads the data in the SOA event tree.  This will reopen all tree nodes that
     * are currently open.
     *
     * @param json the SOA event data to use to load the tree
     */
    function reloadSoaEventTree(json) {

        // load the new data into the tree, this will remove all of the old data
        soaEventTree.reload(json);

        // update all open event details panels with current data
        updateEventDetailsPanels(json);
    }

    /**
     * Checks to see if any of SOA events in the json have open event details panels.  If they do,
     * the event details panels are updated with new data.
     *
     * @param treeNodes the new SOA event details to load details panels with
     */
    function updateEventDetailsPanels(treeNodes) {

        // get an array of all selectable leaf nodes in the tree nodes structure
        var leafNodesArray = [];
        addLeafNodesToArray(treeNodes, leafNodesArray);

        // cascade through all of the tab panel tabs to update any event details panels
        viewport.tabPanel.cascade(function (tabPanelContainer) {

            // get the Id of the tab panel container
            if (tabPanelContainer && tabPanelContainer.id) {
                var tabId = tabPanelContainer.id;

                // if this has the id of any child node, update the tab with the child node
                Ext.each(leafNodesArray, function (node) {

                    // get the Id of the tree node
                    if (node && node.soaEventName) {
                        var nodeId = node.soaEventName;

                        // if the tree node and tab have the same Id, update the tab with fresh data
                        if (nodeId && (nodeId == tabId)) {
                            tabPanelContainer.reload({
                                title: node.soaEventName,
                                eventName: node.soaEventName,
                                label: node.label,
                                description: node.description,
                                status: node.isEnabled,
                                eventParams: node.soaEventParams
                            });
                            return false;
                        }
                    }
                });
            }
        });
    }

    /**
     * Gets all of the leaf nodes out of the array of all tree nodes.  This will only add leaf nodes
     * that are selectable since they are the only ones we care about.  This method updates the input
     * array with the new results.
     *
     * @param treeNodes the array of all tree nodes
     * @param leafNodesArray the array to add selectable leaf nodes to
     */
    function addLeafNodesToArray(treeNodes, leafNodesArray) {

        // loop through all children in the tree nodes and add selectable leaf nodes to the results
        for (var i = 0; i < treeNodes.length; i++) {

            // get the node
            var node = treeNodes[i];

            // check if the node should be added to the results
            if (node && node.leaf && node.isSelectable) {
                leafNodesArray.push(node);
            }

            // get the children of this node
            var children = node.children;

            // add all applicable children to the results
            if (children) {
                addLeafNodesToArray(children, leafNodesArray);
            }
        }
    }

    function loadTreeFunction(json) {

        /**
         * Intializing ApiPanel
         */
        var api = new ApiPanel(json, "Domain API");
        /**
         * Intializing MainPanel
         */
        var mainPanel = new MainPanel({
            autoLoad: {
                url: 'jaffa/sc/systemconfigdesktop/welcome.html',
                scope: this
            }
        });

        /**
         * The onlick event to load the selected
         * service class details into mainpanel in
         * new tab.
         */
        api.on('click', function (node, e) {
            if (node.isLeaf()) {
                e.stopEvent();
                mainPanel.loadClass(node.attributes.className, node.attributes.serviceName, node.attributes.dbTableName);
            }
        });

        mainPanel.on('tabchange', function (tp, tab) {
            api.selectClass(tab.cclass);
        });

        /**
         * Adding api tree panel to AccordionPanel
         */
        viewport.addAccordionPanel(api);
        /**
         * Adding main panel to AccordionPanel
         */
        viewport.addTabPanel(mainPanel);

        viewport.doLayout();
    }

    if (security.hasRuleInquiry) {
        viewport.addAccordionPanel(new Jaffa.SC.RulesTree({
            listeners: {
                'click': function (node, event) {
                    var tab = viewport.tabPanel.findById(node.attributes.path);
                    if (tab) {
                        viewport.tabPanel.activate(tab);
                    }
                    else {
                        if (node.attributes.count && node.attributes.count > 0) {
                            viewport.addTabPanel(new Jaffa.SC.RulesDetailPanel({
                                path: node.attributes.path,
                                stateId: 'rulegrid',
                                controller: Jaffa.SC.DesktopController
                            }));
                        }
                    }
                }
            },
            tools: [
                {
                    id: 'maximize',
                    qtip: Labels.get('label.Jaffa.SC.SystemConfigDesktop.OpenRuleSearchTab.QTip'),
                    on: {
                        click: function (e) {
                            e.stopEvent();
                            var tab = viewport.tabPanel.findById('ruleSearchTab');
                            if (tab) {
                                viewport.tabPanel.activate(tab);
                            } else {
                                viewport.addTabPanel(new Jaffa.SC.RulesDetailPanel({
                                        titleToken: 'label.Jaffa.SC.SystemConfigDesktop.Search',
                                        id: 'ruleSearchTab',
                                        controller: Jaffa.SC.DesktopController,
                                        stateId: 'rulesearchgrid',
                                        enableSearch: true
                                    }
                                ));
                            }
                        }
                    }
                }
            ]
        }));
    }
	
	/*if (security.hasFlexFieldInquiry){
		viewport.addAccordionPanel(new Jaffa.SC.FlexTree({
		  listeners: {
			'click': function(node, event){
			  var tab = viewport.tabPanel.findById(node.attributes.flexObject.flexName+":"+node.attributes.flexObject.flexSourceFile);
			  if (tab) {
				viewport.tabPanel.activate(tab);
			  }
			  else {
				viewport.addTabPanel(new Jaffa.SC.FlexDetailPanel({
				  controller: Jaffa.SC.DesktopController,
				  flexObject: node.attributes.flexObject
				}));
			  }
			}
		  }
		}));
	}*/

    viewport.header.topToolbar.refreshBtn.enable();
    viewport.doLayout();
    Ext.get(document.body).unmask();
});
