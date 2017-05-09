/**
 * @author Sean Zhou
 * 
 * @param {Object} config
 */
Jaffa.Setup.ValidFieldValueFinderContainer = function(config){

  var ValidFieldValueRecord = Jaffa.data.Record.create([{
    name: 'tableName', filter: true, sortable: true, key: true
  }, {
    name: 'fieldName', filter: true, sortable: true, key: true
  }, {
    name: 'legalValue', filter: true, sortable: true, key: true
  }, {
    name: 'description', filter: true, sortable: true
  }]);

  ValidFieldValueRecord.defaultMetaClass = 'ValidFieldValueGraph';
  var checkBoxSM = new Jaffa.grid.HideableCheckboxSelectionModel();

  var store =  new Ext.ux.grid.MultiGroupingPagingDWRStore({
    proxy: new Jaffa.data.DWRCRUDProxy({
      query: Jaffa_Setup_ValidFieldValueService.query
      ,update: Jaffa_Setup_ValidFieldValueService.update
    }),
    reader: new Ext.data.DwrReader({}, ValidFieldValueRecord),
    sortInfo: {
      field: 'tableName',
      direction: 'ASC'
    }
  });

  var containerConfig = {
    xtype:  'findercontainer',
    store:  store,
    layout: 'border', //params.layout.toLowerCase(),
    region: 'center',
    id : 'finderContainer',
    criteria: params,
    criteriaPanel: {
      xtype: 'criteriapanel'
      ,titleToken: 'title.Jaffa.Setup.ValidFieldValueMaintenance.Criteria'
      ,metaClass: 'ValidFieldValueGraph'
      ,defaults: {
        xtype: 'finderCriteriaFieldPanel'
      }
      ,items: [{
        mapping: 'tableName'
      }, {
        mapping: 'fieldName'
      }, {
        mapping: 'legalValue'
      }, {
        mapping: 'description'
      }]
    },
       
    //********************************************************
    //This is result panel which will be displayed on the right side.
    //Record update,delete,add can be done on the grid also
    //**********************************************************
    resultsPanel: {
      xtype: 'pagingfindergrid'
      ,disabled: false
      ,sm: checkBoxSM
      ,columns: [checkBoxSM,'tableName','fieldName','legalValue','description']
      ,titleToken: 'title.Jaffa.Setup.ValidFieldValueMaintenance.Results'
      ,plugins: [{
        ptype: 'maintenanceinlineroweditor'
        ,metaClass: 'ValidFieldValueGraph'
        ,allowAdd: security.hasMaintenance
        ,allowDelete:security.hasMaintenance
        ,allowEdit:security.hasMaintenance
        ,useSequence: false
      },{
        ptype: 'exporttoexcel'
        ,serviceClassName: 'org.jaffa.modules.setup.apis.ValidFieldValueService'
        ,criteriaClassName:  'org.jaffa.modules.setup.apis.data.ValidFieldValueCriteria'
      }]
    }
  };

  Ext.apply(containerConfig, config);
  return containerConfig;
};