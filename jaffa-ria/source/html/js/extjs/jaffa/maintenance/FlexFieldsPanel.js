/**
 * @class Jaffa.maintenance.FlexFields
 * A utility for flex fields.
 */
Jaffa.maintenance.FlexFields = {
    /**
     * Returns true if flex fields are defined for the flexBean or metaClass, passed inside the config.
     * @param {String/Object} config The config should contain the flexBean or metaClass.
     */
    isPresent: function (config) {
        // determine flexInfo, an array of objects, each containing the source attribute
        var flexInfo;

        if (config.flexBean) {
            // flexBean should contain dynaClass.name
            if (config.flexBean.dynaClass && config.flexBean.dynaClass.name)
                flexInfo = [{source: config.flexBean.dynaClass.name}];
        } else if (config.metaClass) {
            // metaClass should contain flexInfo
            var meta = typeof config.metaClass == 'string' ? ClassMetaData[config.metaClass] : config.metaClass;
            flexInfo = meta.flexInfo;
        }

        // iterate through all the flexInfo classes, returning true for any class that has a non-hidden flex-field
        if (flexInfo && flexInfo.length > 0) {
            for (var i = 0; i < flexInfo.length; i++) {
                var flexSource = flexInfo[i].source;
                if (!flexSource)
                    continue;

                // Retain only the simple name from the fully-qualified flex class name
                var flexClass = flexSource.split('.');
                flexClass = flexClass[flexClass.length - 1];

                if (!ClassMetaData[flexClass]) {
                    // load metadata for the flex class
                    // @todo: load the metadata asynchronously and build the panel in the response handler
                    var metaSource = Ext.Ajax.synchronousRequest({
                        url: 'js/extjs/jaffa/metadata/classMetaData.jsp',
                        params: {
                        className: flexSource,
                        outputStyle: "JSON"
                        }
                    });
                    if (metaSource)
                    {
                        ClassMetaData[flexClass] = Ext.decode(metaSource);
                    }
                }

                for (var fieldName in ClassMetaData[flexClass].fields) {
                    var fieldMeta = ClassMetaData[flexClass].fields[fieldName];
                    if (fieldMeta.hidden)
                        continue;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a Panel containing flex fields.
     * Nothing is created if flexBean or metaClass are not passed in the config.
     * @param {Object} config At the bare minimum, the config should contain at least the flexBean or metaClass elements.
     */
    ,createPanel: function (config) {
        // determine flexInfo, an array of objects, each containing the source attribute
        var flexInfo;

        if (config.flexBean) {
            // flexBean should contain dynaClass.name
            if (config.flexBean.dynaClass && config.flexBean.dynaClass.name)
                flexInfo = [{source: config.flexBean.dynaClass.name}];
        } else if (config.metaClass) {
            // metaClass should contain flexInfo
            var meta = typeof config.metaClass == 'string' ? ClassMetaData[config.metaClass] : config.metaClass;
            flexInfo = meta.flexInfo;
        }

        if (!flexInfo || flexInfo.length == 0)
            return null;

        // add fields for each element of the flexInfo to the config
        config.items = [];
        var ungroupedFields = [];
        var fieldSets = [];

        for (var i = 0; i < flexInfo.length; i++) {
            var flexSource = flexInfo[i].source;
            if (!flexSource)
                continue;

            // Retain only the simple name from the fully-qualified flex class name
            var flexClass = flexSource.split('.');
            flexClass = flexClass[flexClass.length - 1];

            if (!ClassMetaData[flexClass]) {
                // load metadata for the flex class
                // @todo: load the metadata asynchronously and build the panel in the response handler
                var metaSource = Ext.Ajax.synchronousRequest({
                    url: 'js/extjs/jaffa/metadata/classMetaData.jsp',
                    params: {
                        className: flexSource
                    }
                });
                if (metaSource)
                    eval(metaSource);
            }

            // Pass the flexClass as the metaClass and dynaClass to each flex field
            Ext.apply(config.defaults, {
                metaClass: flexClass,
                dynaClass: flexInfo[i].source
            });

            if (ClassMetaData[flexClass].label && ClassMetaData[flexClass].label != "") config.title = ClassMetaData[flexClass].label;

            // add each field of the flex class to config.items
            for (var fieldName in ClassMetaData[flexClass].fields) {
                var fieldMeta = ClassMetaData[flexClass].fields[fieldName];
                if (fieldMeta.hidden)
                    continue;

                // create a config for a field and apply defaults
                var fieldConfig = {
                    mapping: 'flexBean.' + fieldName
                };
                Ext.applyIf(fieldConfig, config.defaults);

                // Labels do not appear in a table layout. Hence wrap the field within a form panel.
                var formField = {
                    layout: 'form',
                    style: 'padding:0 10px 0 0;',
                    cls: 'x-panel-mc',
                    sequence: fieldMeta.displaySequence?fieldMeta.displaySequence:0,
                    metaClass: flexClass,
                    dynaClass: flexInfo[i].source,
                    items: [fieldConfig]
                };

                //overrides the form layout wrapper
                if (config.formFieldConfig) {
                    Ext.apply(formField, config.formFieldConfig);
                }

                if (fieldMeta.displayGroup){
                    if (!fieldSets[fieldMeta.displayGroup])
                        fieldSets[fieldMeta.displayGroup] = {
                            label: fieldMeta.displayGroupLabel,
                            fields: []
                        };
                    fieldSets[fieldMeta.displayGroup].fields.push(formField);
                }else{
                    ungroupedFields.push(formField);
                }
            }
        }

        //Add ungrouped fields to panel at top of form
        if (ungroupedFields.length > 0) {
            ungroupedFields.sort(function(a, b){return a.sequence - b.sequence});
            config.items[0] = {
                cls: 'x-panel-mc',
                labelWidth: config.labelWidth ? config.labelWidth : 150,
                layout: 'table',
                items: ungroupedFields,
                layoutConfig: {columns: ungroupedFields.length <= 10 ? 1 : 2}
            };
        }

        //Grouped fields should now exist in fieldsets. Added these fieldsets to the form.
        for (var i = 0; i < fieldSets.length; i++){
            if (fieldSets[i]!=undefined){
                fieldSets[i].fields.sort(function(a, b){return a.sequence - b.sequence});
                var newIndex = config.items.length;
                config.items[newIndex] = {
                    xtype: 'fieldset',
                    title: fieldSets[i].label,
                    labelWidth: config.labelWidth ? config.labelWidth : 150,
                    layout: 'table',
                    items: fieldSets[i].fields,
                    layoutConfig: {columns: fieldSets[i].fields.length <= 10 ? 1 : 2}
                };
            }
        }

        if (config.items.length > 0){
            // create the Panel
            return new Ext.Panel(config);
        } else
            return null;
    }
};
