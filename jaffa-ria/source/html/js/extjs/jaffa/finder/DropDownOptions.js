/**
 * @author SeanZ
 * The options are in format of [label, value]
 * To use, create a store for a specific combo box
 *   var store = new Ext.data.SimpleStore({
 *      fields: ['label', 'value'],
 *      data : [the option] // from states.js
 *   });
 *   var combo = new Ext.form.ComboBox({
 *      store: store,
 *      displayField:'state',
 *      typeAhead: true,
 *      mode: 'local',
 *      triggerAction: 'all',
 *      emptyText:'Select a state...',
 *      selectOnFocus:true,
 *      applyTo: 'local-states'
 *  });
 *
 */
//@TODO need to put better namespacing on this variable
//also we should only create it on demand as it is an overhead for pages that don'y use it
/*
var criteriaOptionFactory = function(){
  var booleanCriteriaDropDownOptions = [
    [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.All'), null], 
    [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.True'), 'True'], 
    [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.False'), 'False']
  ];
  
  var dropDownCriteriaDropDownOptions = [
    ['=', 'Equals'], ['!=', 'NotEquals'], ['>', 'GreaterThan'], ['<', 'SmallerThan'], 
    ['>=', 'GreaterThanOrEquals'], ['<=', 'SmallerThanOrEquals'], 
    [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.IsNotNull'), 'IsNotNull'], 
    [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.IsNull'), 'IsNull']
  ];
  
  var numericalCriteriaDropDownOptions = Ext.apply(new Array(), dropDownCriteriaDropDownOptions);
  numericalCriteriaDropDownOptions[numericalCriteriaDropDownOptions.length] = [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.Between'), 'Between'];
  numericalCriteriaDropDownOptions[numericalCriteriaDropDownOptions.length] = [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.In'), 'In'];
  
  // TODO: add Between and In selections after multiple date picker is implemented. http://extjs.com/forum/showthread.php?p=148587
  var dateCriteriaDropDownOptions = Ext.apply(new Array(), dropDownCriteriaDropDownOptions);

  var allCriteriaDropDownOptions = Ext.apply(new Array(), numericalCriteriaDropDownOptions);
  allCriteriaDropDownOptions[allCriteriaDropDownOptions.length] = [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.BeginsWith'), 'BeginsWith'];
  allCriteriaDropDownOptions[allCriteriaDropDownOptions.length] = [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.EndsWith'), 'EndWith'];
  allCriteriaDropDownOptions[allCriteriaDropDownOptions.length] = [Labels.get('label.jaffa.jaffaRIA.jaffa.DropDownOption.Like'), 'Like'];
  
  var options = {
    booleanOptions: new Ext.data.SimpleStore({
      fields: ['label', 'value'],
      data: booleanCriteriaDropDownOptions
    }),
    dropDownOptions: new Ext.data.SimpleStore({
      fields: ['label', 'value'],
      data: dropDownCriteriaDropDownOptions
    }),
    numericalOptions: new Ext.data.SimpleStore({
      fields: ['label', 'value'],
      data: numericalCriteriaDropDownOptions
    }),
    dateOptions: new Ext.data.SimpleStore({
      fields: ['label', 'value'],
      data: dateCriteriaDropDownOptions
    }),
    allOptions: new Ext.data.SimpleStore({
      fields: ['label', 'value'],
      data: allCriteriaDropDownOptions
    })
  };
  
  var genericCriteriaCombo = function(config){
    config = Ext.applyIf(config || {}, {
      mode : 'local'
      ,operator : 'true'
      ,displayField : 'label'
      ,valueField : 'value'
      ,typeAhead : true
      ,triggerAction : 'all'
      ,forceSelection : true
      ,width : 100
    });
    var output = new Ext.form.ComboBox(config);
    // output.defaultAutoCreate.size = config.size;
    return output;
  };
  
  return {
    getBooleanCriteriaCombo: function(config){
      if (!config.store) 
        config.store = options.booleanOptions;
      return genericCriteriaCombo(config);
    },
    getNumericCriteriaCombo: function(config){
      if (!config.store) 
        config.store = options.numericalOptions;
      return genericCriteriaCombo(config);
    },
    getDateCriteriaCombo: function(config){
      if (!config.store) 
        config.store = options.dateOptions;
      return genericCriteriaCombo(config);
    },
    getAllCriteriaCombo: function(config){
      if (!config.store) 
        config.store = options.allOptions;
      return genericCriteriaCombo(config);
    }
  };
}();
*/