/**
 * @class SoaEventDetailPanel
 * @extends Ext.Panel
 * @author Saravanan N
 *
 * The SoaEventDetailPanel loads the SoaEvent and
 * SoaEventParams metadata details that configured
 * on the soa-events.xml.
 *
 */
SoaEventDetailPanel = Ext.extend(Ext.Panel, {
    closable: true,
    autoScroll: true,
    bodyStyle: 'padding:5px 5px 0',
    layout: 'form',
    status: this.status,
    maskedElement: null,

    // the event fields
    nameFieldInput: null,
    labelFieldInput: null,
    descriptionFieldInput: null,
    statusFieldInput: null,
    soaEventParamsGrid: null,
    enableDisableButton: null,

    /**
     * Initialize the panel with data passed in.
     */
    initComponent: function () {

        var id = this.id;
        var eventName = this.eventName;
        var label = this.label;
        var description = this.description;
        var statusField;
        var enableButtonIconClass;
        var enableDisableButtonText;
        var eventParams = this.eventParams;
        var currentPanel = this;
        var soaEventEnabledService = new Ext.data.DWRProxy2(SOAEventMetaDataService.setEnabled);

        // set the value of the status field based on the status flag
        if (this.status) {
            statusField = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventEnabled');
            enableButtonIconClass = 'icon-filter-disabled';
            enableDisableButtonText = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.disableEvent');
        } else {
            statusField = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventDisabled');
            enableButtonIconClass = 'icon-filter-enabled';
            enableDisableButtonText = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.enableEvent');
        }

        // define the name field
        this.nameFieldInput = new Ext.form.DisplayField({
            fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.soaEventName'),
            value: this.eventName,
            autoWidth: true
        });

        // define the label field
        this.labelFieldInput = new Ext.form.DisplayField({
            fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.label'),
            value: this.label,
            autoWidth: true
        });

        // define the description field
        this.descriptionFieldInput = new Ext.form.DisplayField({
            fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.desription'),
            value: this.description,
            autoWidth: true
        });

        // define the status field
        this.statusFieldInput = new Ext.form.DisplayField({
            fieldLabel: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventStatus'),
            value: statusField,
            autoWidth: true
        });

        // define the parameters grid
        this.soaEventParamsGrid = this.loadSoaEventParamsData(id, eventParams, eventName, label, description);

        // define the enable/disable button
        this.enableDisableButton = new Ext.Button({
            id: this.id + '_enableDisableButton',
            iconCls: enableButtonIconClass,
            text: enableDisableButtonText,

            /**
             * Enable/disable the event with the SOA event service.
             * If this is successful, reload the tree with current data.
             * If this fails, show an error message to the user.
             */
            handler: function () {

                // show the loading mask
                Ext.getCmp(id).getEl().mask(
                    Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.loadingMessage'), 'x-mask-loading');

                // handle an exception encountered on this service call
                soaEventEnabledService.on("loadexception", function () {

                    // hide the loading mask
                    Ext.getCmp(id).getEl().unmask();

                    // determine the message ans title based on the status of the event
                    var message;
                    var title;
                    if (currentPanel.status) {
                        message = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventDisableError');
                        title = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventDisableErrorTitle');
                    } else {
                        message = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventEnableError');
                        title = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventEnableErrorTitle');
                    }

                    // show the message box
                    fnMessage(message, title);
                });

                // change the status of the event with the SOA event service
                soaEventEnabledService.load([eventName, !currentPanel.status],
                    {read: function (r) {
                        return r;
                    }},
                    currentPanel.eventStatusUpdated,
                    currentPanel
                );
            }
        });

        Ext.apply(this, {
            items: [
                {
                    /** This fieldset hold the SoaEvent details **/
                    xtype: 'fieldset',
                    title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEvent'),
                    autoHeight: true,
                    items: [
                        this.nameFieldInput,
                        this.labelFieldInput,
                        this.descriptionFieldInput,
                        this.statusFieldInput
                    ]
                },
                {
                    /** This fieldset hold the SoaEventParams details and metadata details**/
                    xtype: 'fieldset',
                    title: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventParams'),
                    autoHeight: true,
                    items: [currentPanel.soaEventParamsGrid]
                }
            ],
            tbar: [

                // the test event button
                {
                    id: this.id + '_testbutton',
                    text: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.submitTestEvent'),
                    iconCls: 'icon-tick',
                    handler: function () {
                        var tab = viewport.tabPanel.findById(id + '_test');
                        if (tab) {
                            viewport.tabPanel.activate(tab);
                            return;
                        }
                        Ext.getCmp(id).getEl().mask(Labels.get('label.Jaffa.Mask.Loading'), 'x-mask-loading');
                        Ext.Ajax.request({
                            url: 'jaffa/sc/systemconfigdesktop/jsonHelper.jsp',
                            method: 'POST',
                            params: {
                                json: Ext.encode(eventParams),
                                eventName: eventName
                            },
                            success: function (result, request) {
                                var soaEventParamsJsonFormat = Ext.util.JSON.decode(result.responseText);
                                var p = Ext.getCmp('viewport').addTabPanel(new SoaEventTestPanel({
                                    id: id + '_test',
                                    title: id + ' '+Labels.get('label.common.Test'),
                                    iconCls: 'icon-test',
                                    eventName: eventName,
                                    label: label,
                                    description: description,
                                    soaEventParamsJsonFormat: soaEventParamsJsonFormat
                                }));
                                Ext.getCmp(id).getEl().unmask();
                            },
                            failure: function (result, request) {
                                Ext.getCmp(id).getEl().unmask();
                            }
                        });
                    }
                },

                // put a spacer between the buttons
                '-',

                // the enable/disable button to toggle the enabled state of the event
                this.enableDisableButton
            ]
        });

        SoaEventDetailPanel.superclass.initComponent.call(this);
    },

    /**
     * Reloads the panel with SOA event data
     *
     * @param params the new SOA event data to reload the panel with
     */
    reload: function (params) {
        var eventName = params.eventName;
        var label = params.label;
        var description = params.description;
        var statusField;
        var enableButtonIconClass;
        var enableDisableButtonText;
        var eventParams = params.eventParams;
        this.status = params.status;

        // reload the grid with data
        this.soaEventParamsGrid.store.loadData(eventParams);

        // set the value of the status field based on the status flag
        if (params.status) {
            statusField = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventEnabled');
            enableButtonIconClass = 'icon-filter-disabled';
            enableDisableButtonText = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.disableEvent');
        } else {
            statusField = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.soaEventDisabled');
            enableButtonIconClass = 'icon-filter-enabled';
            enableDisableButtonText = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.enableEvent');
        }

        // update all of the fields in the panel
        if (this.nameFieldInput) {
            this.nameFieldInput.setValue(eventName);
        }
        if (this.labelFieldInput) {
            this.labelFieldInput.setValue(label);
        }
        if (this.descriptionFieldInput) {
            this.descriptionFieldInput.setValue(description);
        }
        if (this.statusFieldInput) {
            this.statusFieldInput.setValue(statusField);
        }
        if (this.enableDisableButton) {
            this.enableDisableButton.setIconClass(enableButtonIconClass);
            this.enableDisableButton.setText(enableDisableButtonText);
        }

        // refresh the panel so the GUI will show the new values
        this.doLayout();
    },

    /**
     * Called after the status of an event has been updated with the SOA event enabled service.
     *
     * @param response the response of the service call
     * @param params parameters that were passed into the DWR call
     * @param isSuccess true if the call was successful
     */
    eventStatusUpdated: function (response, params, isSuccess) {

        // hide the loading mask
        Ext.getCmp(this.id).getEl().unmask();

        // if the call was a success, fire the event to reload the SOA event tree
        // else, show an error message to the user
        if (isSuccess) {
            this.fireEvent('reloadTree');
        } else {
            var message;
            var title;

            // determine the message ans title based on the status of the event
            if (this.status) {
                message = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventDisableError');
                title = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventDisableErrorTitle');
            } else {
                message = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventEnableError');
                title = Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventEnableErrorTitle');
            }

            // show the message box
            fnMessage(message, title);
        }
    },

    loadSoaEventParamsData: function (id, eventParams) {

        var eventParamsReader = new Ext.data.JsonReader({

            }, Ext.data.Record.create([
            {
                name: 'name'
            },
            {
                name: 'dataType'
            },
            {
                name: 'description'
            }
        ])
        );

        var store = new Ext.data.Store({
            reader: eventParamsReader
        });
        store.loadData(eventParams);

        var eventParamGrid = new Ext.grid.GridPanel({
            id: id + "_paramgrid",
            labelAlign: 'top',
            store: store,
            columns: [
                {
                    dataIndex: 'name',
                    header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.eventParamName'),
                    width: 15,
                    sortable: true,
                    id: 'name'
                },

                {
                    dataIndex: 'dataType',
                    header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.SoaEvent.type'),
                    width: 30,
                    sortable: true
                },

                {
                    dataIndex: 'description',
                    header: Labels.get('label.Jaffa.SC.SystemConfigDesktop.desription'),
                    width: 20,
                    sortable: true
                }
            ],
            stripeRows: true,
            autoHeight: true,
            viewConfig: {
                forceFit: true
            },
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true
            })
        });

        return eventParamGrid;
    },
    refresh: Ext.emptyFn
});

/**
 *  @param {string} message the message of the box
 *  @param {string} title the title of the box
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