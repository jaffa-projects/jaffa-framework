/**
 * @author Sean Zhou, 2010
 *
 * @obsolete the system code fields are now implemented through aop rules.
 */
/**
 * 
 * @param {Array} systemCodes a string array of status codes
 * @param {String} labelRoot the root of the status labels.
 */
Jaffa.util.SystemCodeGenerator = function(systemCodes, labelRoot) {
  if (systemCodes && systemCodes.systemCodes) {
    labelRoot = systemCodes.labelRoot;
    systemCodes = systemCodes.systemCodes;
  }
  while (labelRoot.endsWith('.')) labelRoot = labelRoot.substr(0,labelRoot.length-1);
  
  var sa = [];
  if (!Ext.isArray(systemCodes)) systemCodes = [systemCodes];
  for (var i=0; i<systemCodes.length; i++) {
    sa.push({
      code: systemCodes[i],
      label: labelRoot ? (systemCodes[i] ? Labels.get(labelRoot+'.'+systemCodes[i]) : Labels.get(labelRoot)) : systemCodes[i]
    });
  }
  
  return {
    systemCodes: sa,
    getTemplateConfig: function(config) {
      return Ext.apply({
        valueField: 'code',
        displayField: 'label',
        typeAhead: true,
        triggerAction: 'all',
        //lazyRender: true,
        mode: 'local',
        store: {
          xtype: 'store',
          fields: ['code', 'label'],
          data: {
            records: this.systemCodes.length,
            rows: this.systemCodes
          }
          ,reader: new Ext.data.JsonReader({
            idProperty: 'code',
            root: 'rows',
            fields: [{
              name: 'code'
            }, {
              name: 'label'
            }]
          })
        }
      }, config);
    },
    getComboConfig: function(config) {
      return Ext.apply(this.getTemplateConfig({
        xtype: "combo"
      }), config);
    },
    getLovComboConfig: function(config) {
      return Ext.apply(this.getTemplateConfig({
        xtype: "lovcombo"
      }), config);
    }
  };
};
