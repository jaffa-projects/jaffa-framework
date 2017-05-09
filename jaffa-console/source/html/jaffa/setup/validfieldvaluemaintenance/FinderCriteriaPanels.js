/**
 * @author Sean Zhou
 */
Jaffa.Setup.ValidFieldValueCriteriaPanel = {
  header: false,
  layout: 'form',
  collapsible: true,
  autoHeight: true,
  bodyStyle: 'background-color:#DFE8F6',
  defaults: {
    xtype: 'finderCriteriaFieldPanel'
  },
  items: [{
    mapping: 'tableName'
  }, {
    mapping: 'fieldName'
  }, {
    mapping: 'legalValue'
  }, {
    mapping: 'description'
  }]
};
