/**
 * @class Jaffa.finder.CriteriaOptionsFactory
 * @singleton
 * @author SeanZ,PaulE
 *
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
Jaffa.finder.CriteriaOptionsFactory = {
  labels : {
    'Equals' :              '=', 
    'NotEquals' :           '!=',
    'GreaterThan' :         '>', 
    'SmallerThan' :         '<', 
    'GreaterThanOrEquals' : '>=',
    'SmallerThanOrEquals' : '<=',
    'IsNotNull' :           'Is Not Null',
    'IsNull' :              'Is Null',
    'Between' :             'Between',
    'In' :                  'In List',
    'BeginsWith' :          'Begins',
    'EndWith' :             'Ends',
    'Like' :                'Contains',
    '' :                    'Any',
    'True' :                'True',
    'False' :               'False'
  }
  
  ,getBooleanStore: function() {
    var l = Jaffa.finder.CriteriaOptionsFactory.labels;
    var d=[];
    var ops = ['','True','False'];
    for(var x=0;x<ops.length; x++) {
        d[d.length] = [l[ops[x]],ops[x]];
    }
    return new Ext.data.SimpleStore({ fields: ['label', 'value'], data: d });
  }
  
  ,getNumericStore: function(excludeNull) {
    var l = Jaffa.finder.CriteriaOptionsFactory.labels;
    var d=[];
    var ops = ['Equals','NotEquals','GreaterThan','SmallerThan','GreaterThanOrEquals', 'SmallerThanOrEquals', 'IsNotNull', 'IsNull', 'Between', 'In']
    if (excludeNull)
      ops.splice(7, 1);
    for(var x=0;x<ops.length; x++) {
        d[d.length] = [l[ops[x]],ops[x]];
    }
    return new Ext.data.SimpleStore({ fields: ['label', 'value'], data: d });
  }

  ,getDateStore: function(excludeNull) {
    var l = Jaffa.finder.CriteriaOptionsFactory.labels;
    var d=[];
    // @TODO: add Between and In selections after multiple date picker is implemented. http://extjs.com/forum/showthread.php?p=148587
    var ops = ['Equals','NotEquals','GreaterThan','SmallerThan','GreaterThanOrEquals', 'SmallerThanOrEquals', 'IsNotNull', 'IsNull', 'Between']
    if (excludeNull)
      ops.splice(7, 1);
    for(var x=0;x<ops.length; x++) {
        d[d.length] = [l[ops[x]],ops[x]];
    }
    return new Ext.data.SimpleStore({ fields: ['label', 'value'], data: d });
  }

  ,getStringStore: function(excludeNull) {
    var l = Jaffa.finder.CriteriaOptionsFactory.labels;
    var d=[];
    var ops = ['Equals','NotEquals','GreaterThan','SmallerThan','GreaterThanOrEquals', 'SmallerThanOrEquals', 'IsNotNull', 'IsNull', 'Between', 'In', 'BeginsWith', 'EndWith', 'Like']
    if (excludeNull)
      ops.splice(7, 1);
    for(var x=0;x<ops.length; x++) {
        d[d.length] = [l[ops[x]],ops[x]];
    }
    return new Ext.data.SimpleStore({ fields: ['label', 'value'], data: d });
  }

  ,genericCriteriaCombo : function(config){
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
  }
  
  ,getBooleanCriteriaCombo: function(config){
      if (!config.store) {
        if(!Jaffa.finder.CriteriaOptionsFactory.booleanStore) Jaffa.finder.CriteriaOptionsFactory.booleanStore = Jaffa.finder.CriteriaOptionsFactory.getBooleanStore();
        config.store = Jaffa.finder.CriteriaOptionsFactory.booleanStore;
      }  
      return Jaffa.finder.CriteriaOptionsFactory.genericCriteriaCombo(config);
  }

  ,getNumericCriteriaCombo: function(config){
      if (!config.store) {
        if (config.excludeNull)
          config.store = Jaffa.finder.CriteriaOptionsFactory.getNumericStore(true);
        else {
          if(!Jaffa.finder.CriteriaOptionsFactory.numericalStore) Jaffa.finder.CriteriaOptionsFactory.numericalStore = Jaffa.finder.CriteriaOptionsFactory.getNumericStore();
          config.store = Jaffa.finder.CriteriaOptionsFactory.numericalStore;
        }
      }  
      return Jaffa.finder.CriteriaOptionsFactory.genericCriteriaCombo(config);
  }
  
  ,getDateCriteriaCombo: function(config){
      if (!config.store) {
        if (config.excludeNull)
          config.store = Jaffa.finder.CriteriaOptionsFactory.getDateStore(true);
        else {
          if(!Jaffa.finder.CriteriaOptionsFactory.dateStore) Jaffa.finder.CriteriaOptionsFactory.dateStore = Jaffa.finder.CriteriaOptionsFactory.getDateStore();
          config.store = Jaffa.finder.CriteriaOptionsFactory.dateStore;
        }  
      }  
      return Jaffa.finder.CriteriaOptionsFactory.genericCriteriaCombo(config);
  }
  
  ,getAllCriteriaCombo: function(config){
      if (!config.store) {
        if (config.excludeNull)
          config.store = Jaffa.finder.CriteriaOptionsFactory.getStringStore(true);
        else {
          if(!Jaffa.finder.CriteriaOptionsFactory.stringStore) Jaffa.finder.CriteriaOptionsFactory.stringStore = Jaffa.finder.CriteriaOptionsFactory.getStringStore();
          config.store = Jaffa.finder.CriteriaOptionsFactory.stringStore;
        }  
      }  
      return Jaffa.finder.CriteriaOptionsFactory.genericCriteriaCombo(config);
  }
  
};
