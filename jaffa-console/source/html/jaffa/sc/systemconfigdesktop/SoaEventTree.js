/**
 * @param {json} treeNodes
 *
 * Load the web service details into ApiTree
 */
SoaEventTreePanel = function (treeNodes) {
    SoaEventTreePanel.superclass.constructor.call(this, {
        id: 'soaevent-tree',
        title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEvents'),
        split: true,
        width: 280,
        minSize: 175,
        maxSize: 500,
        margins: '0 0 5 5',
        cmargins: '0 0 0 0',
        rootVisible: false,
        lines: false,
        autoScroll: true,
        animCollapse: false,
        animate: false,
        collapseMode: 'mini',
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEvents'),
            id: '0',
            expanded: true,
            children: treeNodes
        }),
        collapseFirst: false,
        stateAll: "all",
        stateEnabled: "enabled",
        stateDisabled: "disabled",
        filterState: this.stateAll,
        filterTextField: null
    });
    new Ext.tree.TreeSorter(this, {
        folderSort: true,
        leafAttr: 'isClass'
    });

    /**
     * Fired when a tree node is selected by the user.
     * If this is not a selectable node, return false to cancel loading the event details panel
     */
    this.getSelectionModel().on('beforeselect', function (sm, node) {
        return node.attributes.isSelectable;
    });
};

/**
 * @class ApiPanel
 * @extends Ext.tree.TreePanel
 *
 */
Ext.extend(SoaEventTreePanel, Ext.tree.TreePanel, {
    initComponent: function () {
        this.hiddenPkgs = [];
        this.hiddenPackageIds = [];

        // a text field that will filter the tree based on input
        this.filterTextField = new Ext.form.TextField({
            width: 170,
            emptyText: Labels.get('label.Jaffa.SC.SystemConfigDesktop.findAEventEmptyTxt'),
            enableKeyEvents: true,
            listeners: {
                render: function (f) {
                    this.filter = new Ext.tree.TreeFilter(this, {
                        clearBlank: true,
                        autoClear: true
                    });
                },
                keydown: {
                    fn: this.filterTree,
                    buffer: 350,
                    scope: this
                },
                scope: this
            }
        });

        // the menu to attach to the event filter button
        var filterMenu = new Ext.menu.Menu({
            id: 'filterMenu',
            items: [

                // the "all" filter icon
                {
                    iconCls: 'icon-filter-all',
                    handler: function () {

                        // if we are in the "all" state, do nothing
                        if (this.filterState == this.stateAll) {
                            return;
                        }

                        // change the filter to the "all" state
                        menuIcon.setIconClass('icon-filter-all');
                        this.filterState = this.stateAll;
                        this.filterTree(this.filterTextField);
                    },
                    scope: this,
                    text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventTreeShowAll')
                },

                // the "enabled" filter icon
                {
                    iconCls: 'icon-filter-enabled',
                    handler: function () {

                        // if we are in the "enabled" state, do nothing
                        if (this.filterState == this.stateEnabled) {
                            return;
                        }

                        // change the filter to the "all" state
                        menuIcon.setIconClass('icon-filter-enabled');
                        this.filterState = this.stateEnabled;
                        this.filterTree(this.filterTextField);
                    },
                    scope: this,
                    text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventTreeShowEnabled')
                },

                // the "disabled" filter icon
                {
                    iconCls: 'icon-filter-disabled',
                    handler: function () {

                        // if we are in the "disabled" state, do nothing
                        if (this.filterState == this.stateDisabled) {
                            return;
                        }

                        // change the filter to the "all" state
                        menuIcon.setIconClass('icon-filter-disabled');
                        this.filterState = this.stateDisabled;
                        this.filterTree(this.filterTextField);
                    },
                    scope: this,
                    text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventTreeShowDisabled')
                }
            ]
        });

        // the icon that launches the filter menu, this defaults ot the "all" filter state
        var menuIcon = new Ext.Button({
            iconCls: 'icon-filter-all',
            tooltip: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventTreeFilterTooltip'),
            menu: filterMenu,
            scope: this
        });

        Ext.apply(this, {
            tools: [
                {
                    id: 'maximize',
                    qtip: Labels.get('label.Jaffa.SC.SystemConfigDesktop.OpenEventSummaryTab'),
                    on: {
                        click: function (e) {
                            e.stopEvent();
                            var tab = viewport.tabPanel.findById('soaEventSummary');
                            if (tab) {
                                viewport.tabPanel.activate(tab);
                            }
                            else {
                                Ext.Ajax.request({
                                    url: 'jaffa/sc/systemconfigdesktop/topicAdmin.jsp',
                                    method: 'POST',
                                    params: {
                                        topicName: params.topicName
                                    },
                                    success: function (result, request) {
                                        var subscriberList;
                                        var jsonData = Ext.util.JSON.decode(result.responseText);
                                        if (jsonData.success) {
                                            subscriberList = JSON.stringify(jsonData.data, null, 8);

                                            viewport.addTabPanel(new SoaEventSummaryPanel({
                                                id: 'soaEventSummary',
                                                titleToken: 'label.Jaffa.SC.SystemConfigDesktop.EventSummary',
                                                subscriberList: subscriberList,
                                                schedulerPollerAndEventConfig: JSON.stringify(jsonData.schedulerPollerAndEventConfig, null, 8),
                                                drlFilePath: JSON.stringify(jsonData.schedulerPollerAndEventConfig.drlFilePath, null, 8)
                                            }));

                                        } else {
                                            resultMessage = jsonData.data.result;
                                            fnMessage(resultMessage, 'Error');
                                        }
                                    },
                                    failure: function (result, request) {
                                        var jsonData = Ext.util.JSON.decode(result.responseText);
                                        var resultMessage = jsonData.data.result;
                                        fnMessage(resultMessage, 'Error');
                                        Ext.getCmp(id).body.unmask();
                                    }
                                });

                            }
                        }
                    }
                }
            ],
            tbar: [
                ' ',
                this.filterTextField,
                ' ',
                ' ',
                menuIcon,
                '-',
                {
                    iconCls: 'icon-expand-all',
                    tooltip: Labels.get('label.Jaffa.SC.SystemConfigDesktop.ExpandAllTooltip'),
                    handler: function () {
                        this.root.expand(true);
                    },
                    scope: this
                },
                '-',
                {
                    iconCls: 'icon-collapse-all',
                    tooltip: Labels.get('label.Jaffa.SC.SystemConfigDesktop.ExpandAllTooltip'),
                    handler: function () {
                        this.root.collapse(true);
                    },
                    scope: this
                }]
        });
        SoaEventTreePanel.superclass.initComponent.call(this);
    },

    /**
     * @param {json} treeNodes the tree nodes to reload the tree with
     *
     * Load the tree with new SOA event data
     */
    reload: function (treeNodes) {

        // get all of the currently opened nodes
        var openNodes = [];

        // traverse the tree and check all of the nodes to get the open ones
        this.root.cascade(function (node) {
            if (node.expanded && !node.hidden) {
                if (node.attributes.uniqueName) {
                    openNodes.push(node.attributes.uniqueName);
                }
            }
        });

        // set the new set of child nodes on the root node
        this.root.removeAll();
        this.root.appendChild(treeNodes);

        // expand the root node
        this.root.expand();

        // get the child nodes of the hidden root node
        var rootChildNodes = this.root.childNodes;

        // reopen all previously opened nodes in the tree
        this.openChildren(rootChildNodes, openNodes);

        // get the text value of the text field, if it doesn't exist yet, skip this method
        if (!this.filterTextField) {
            return;
        }
        var filterText = this.filterTextField.getValue();

        // if the filter text is empty, clear the filter
        if (!filterText) {
            if (this.filter) {
                this.filter.clear();
            }
        }

        // create a regex to be used in filtering the soa events in the tree
        if (filterText) {
            var filterRegex = new RegExp(Ext.escapeRe(filterText), 'i');
        }

        // a reference to the tree so internal members can be accessed inside the bubble function
        var me = this;

        // traverse the tree and check which events should be filtered out
        this.root.cascade(function (node) {

            // if the name of the event has a match with the filter text, set the filter match flag to true
            // else, the filter match flag is false
            if (filterRegex) {
                if (filterRegex.test(node.attributes.soaEventName)) {
                    node.filterMatch = true;
                } else {
                    node.filterMatch = false;
                }
            } else {
                node.filterMatch = true;
            }

            // if a node is not a filter match, we don't care if it is a state match
            // else, if we only want to show active events, hide inactive events
            // else, if we only want to show inactive events, hide active events
            // else, we are showing all states
            if (!node.filterMatch) {
                node.stateMatch = false;
            } else if ((me.filterState == me.stateEnabled) && !node.attributes.isEnabled) {
                node.stateMatch = false;
            } else if ((me.filterState == me.stateDisabled) && node.attributes.isEnabled) {
                node.stateMatch = false;
            } else {
                node.stateMatch = true;
            }

            // if the node is a state and filter match, bubble this to its parents
            if (node.filterMatch && node.stateMatch) {
                node.bubble(function (b) {
                    b.filterMatch = true;
                    b.stateMatch = true;
                });
            }
        });

        // an array to hold the hidden packages
        this.hiddenPkgs = [];
        this.hiddenPackageIds = [];

        // traverse the tree and check all of the nodes
        this.root.cascade(function (node) {

            // if the node does not have a filter match, hide it and add it to the array of hidden nodes
            if (!node.filterMatch || !node.stateMatch) {
                node.ui.hide();
                me.hiddenPkgs.push(node);
                me.hiddenPackageIds.push(node.attributes.uniqueName);
            }
        });
    },

    /**
     * Checks all of the input nodes against the list of open nodes.
     * If an input node is in the list of open nodes, expand it and check all of its children
     * against the list of open nodes.  The list of open nodes contain the uniqueNames of all tree nodes
     * that should be in the open state.
     *
     * @param childNodes the list of input nodes to check against the list of open nodes
     * @param openNodes the list of open nodes to check the input nodes against
     */
    openChildren: function (childNodes, openNodes) {
        if (!childNodes) {
            return;
        }

        // loop through each child node in the input list
        for (var i = 0; i < childNodes.length; i++) {

            // get the unique name of the child node
            var childNodeUniqueName = childNodes[i].attributes.uniqueName;

            // check each unique name against the list of open nodes unique names
            for (var j = 0; j < openNodes.length; j++) {

                // if we find a match, expand the node and check its children against the open list
                if (childNodeUniqueName == openNodes[j]) {
                    childNodes[i].expand();
                    this.openChildren(childNodes[i].childNodes, openNodes);
                    break;
                }
            }
        }
    },

    /**
     * Filters the tree based on the text value entered in the text input as well as the selection
     * of the filter icon.  The user has the option to filter on all, enabled or disabled events.
     *
     * @param filterTextField the text input field that fired this method
     * @param event the key down event that fired this method
     */
    filterTree: function (filterTextField, event) {

        // get the text value of the text field, if it doesn't exist yet, skip this method
        if (!filterTextField) {
            return;
        }
        var filterText = filterTextField.getValue();

        // show all of the currently hidden packages
        Ext.each(this.hiddenPkgs, function (node) {
            node.ui.show();
        });

        // if the filter text is empty, clear the filter
        if (!filterText) {
            if (this.filter) {
                this.filter.clear();
            }
        }

        // expand the tree
        this.expandAll();

        // create a regex to be used in filtering the soa events in the tree
        if (filterText) {
            var filterRegex = new RegExp(Ext.escapeRe(filterText), 'i');
        }

        // a reference to the tree so internal members can be accessed inside the bubble function
        var me = this;

        // traverse the tree and check which events should be filtered out
        this.root.cascade(function (node) {

            // if the name of the event has a match with the filter text, set the filter match flag to true
            // else, the filter match flag is false
            if (filterRegex) {
                if (filterRegex.test(node.attributes.soaEventName)) {
                    node.filterMatch = true;
                } else {
                    node.filterMatch = false;
                }
            } else {
                node.filterMatch = true;
            }

            // if a node is not a filter match, we don't care if it is a state match
            // else, if we only want to show active events, hide inactive events
            // else, if we only want to show inactive events, hide active events
            // else, we are showing all states
            if (!node.filterMatch) {
                node.stateMatch = false;
            } else if ((me.filterState == me.stateEnabled) && !node.attributes.isEnabled) {
                node.stateMatch = false;
            } else if ((me.filterState == me.stateDisabled) && node.attributes.isEnabled) {
                node.stateMatch = false;
            } else {
                node.stateMatch = true;
            }

            // if the node is a state and filter match, bubble this to its parents
            if (node.filterMatch && node.stateMatch) {
                node.bubble(function (b) {
                    b.filterMatch = true;
                    b.stateMatch = true;
                });
            }
        });

        // an array to hold the hidden packages
        this.hiddenPkgs = [];
        this.hiddenPackageIds = [];

        // traverse the tree and check all of the nodes
        this.root.cascade(function (node) {

            // if the node does not have a filter match, hide it and add it to the array of hidden nodes
            if (!node.filterMatch || !node.stateMatch) {
                node.ui.hide();
                me.hiddenPkgs.push(node);
                me.hiddenPackageIds.push(node.attributes.uniqueName);
            }
        });
    }
});

/**
 *  @param {string} message
 *  @param {string} title
 *
 *  This method will show the error message returned from
 *  server call and show in the popup message box.
 */
function fnMessage(message, title) {

    Ext.Msg.show({
        title: title,
        msg: message,
        buttons: Ext.MessageBox.OK,
        icon: title == Labels.get('label.jaffaRIA.MessageBox.AlertInfoMsg') ? Ext.MessageBox.INFO : Ext.MessageBox.ERROR
    });

}