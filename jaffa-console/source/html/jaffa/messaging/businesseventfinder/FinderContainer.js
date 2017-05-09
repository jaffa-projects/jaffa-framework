/**
 * @author ChrisO
 */

// Create the Record Definition for the store
 /** Parameters that are used by a record to make life easier for grid construction
  * @param name Simple name used throughout for this field
  * @param type What data type the field is (Default is string, others are float, int, date, ...)
  * @param defaultValue The value to use in the record if there is no data for this field
  * @param mapping JSON Path to get the value out of the data structure for this record, assumes 'name' if not supplied
  * @param sortable Boolean to indicate whether sorting is available from the server for this field
  * @param sortFieldName The alternate {name} to use when passing the sort by field list to the server, assumes 'name' if sortable is true and not provided
  * @param filter Boolean to indicate whether a filter will be provided to limit the selection on this column
  * @param filterFieldName The alternate {name} to use when passing the filtering clause to the server, assumes 'name' if filter is true and not provided
  * @param hidden Boolean to indicate if by default this column should be initially hidden
  */
Jaffa.Messaging.BusinessEventRecord = Jaffa.data.Record.create([
    {
      name: 'logId', sortable: true,
      filter: true,
			key: true
    },
    {	name: 'correlationType',  sortable: true},
    {name: 'correlationKey1',   sortable: true},
    {name: 'correlationKey2',   sortable: true},
    {name: 'correlationKey3',   sortable: true},
    {name: 'loggedOn',          sortable: true, layout:'[datetime.format]'},
    {name: 'loggedBy',          sortable: true},
    {name: 'processName',       sortable: true},
    {name: 'subProcessName',    sortable: true},
    {name: 'messageType',       sortable: true},
    {name: 'messageText',       sortable: true},
    {name: 'sourceClass',       sortable: true},
    {name: 'sourceMethod',      sortable: true},
    {name: 'sourceLine',        sortable: true},
    {name: 'messageId',         sortable: true}

  ]);
Jaffa.Messaging.BusinessEventRecord.defaultMetaClass= 'BusinessEventLogGraph';

var baseParams = {
  resultGraphRules: [
    'logId',
    'correlationType',
    'correlationKey1',
    'correlationKey2',
    'correlationKey3',
	'messageId',
    'loggedOn',
    'loggedBy',
    'processName',
    'subProcessName',
    'messageType',
    'messageText',
    'sourceClass',
    'sourceMethod',
    'sourceLine'
  ]
};
if (params.scheduledTaskId)
  baseParams.scheduledTaskId = {values: [params.scheduledTaskId]};
if (params.messageId)
  baseParams.messageId = {values: [params.messageId]};

Jaffa.Messaging.FinderContainer = function(config){
  var containerConfig = {
    xtype:'findercontainer',
    store: new Ext.ux.grid.MultiGroupingPagingDWRStore({
			proxy: new Jaffa.data.DWRProxy({
        query: Jaffa_Messaging_BusinessEventLogService.query
      }),
      reader: new Ext.data.DwrReader({id: 'logId'}, Jaffa.Messaging.BusinessEventRecord),
      sortInfo: {field: 'logId', direction: 'ASC'}
    }),
    layout: 'border', //params.layout.toLowerCase(),
    region: 'center',
    criteria: params,
    criteriaPanel:{
      xtype:'criteriapanel',
      title: Labels.get('title.Jaffa.Messaging.BusinessEventFinder.criteria'), // Business Event Log Criteria
      items: [
				Jaffa.Messaging.CriteriaGroups['general']
			],
      metaClass: 'BusinessEventLogGraph',
      defaults: {
          metaClass: 'BusinessEventLogGraph',
          isSearchPanel: true
      },
			baseParams: baseParams
    },
    resultsPanel:{
      metaClass: 'BusinessEventLogGraph',
      xtype:'multigroupingpagingfindergrid',
      disabled: true,
      title: Labels.get('title.Jaffa.Messaging.BusinessEventFinder.results'),  // Business Event Log List
      columns: [
          'correlationType',
          'correlationKey1',
          'correlationKey2',
          'correlationKey3',
          'messageId',
          'loggedOn',
          'loggedBy',
          'processName',
          'subProcessName',
          'messageType',
          'messageText',
          'sourceClass',
          'sourceMethod',
          'sourceLine'
      ],
      view: new Ext.ux.grid.MultiGroupingView({
         hideGroupedColumn :true,
         displayEmptyFields: true,
         groupTextTpl: "{text} : {group} " + "({values.rs.length}{[values.incomplete?'+':'']} " + "{[values.rs.length > 1 ? Labels.get('label.Jaffa.Common.Records') : Labels.get('label.Jaffa.Common.Record')]}) "
      }),
      plugins: new Ext.ux.plugins.ExportToExcelPlugin({
        serviceClassName:'org.jaffa.modules.messaging.apis.BusinessEventLogService',
        criteriaClassName:'org.jaffa.modules.messaging.apis.data.BusinessEventLogCriteria'
      })
    }
  };

  if (config.criteriaPanel) {
    Ext.apply(containerConfig.criteriaPanel, config.criteriaPanel);
    delete config.criteriaPanel;
  }

  if (config.resultsPanel) {
    Ext.apply(containerConfig.resultsPanel, config.resultsPanel);
    delete config.resultsPanel;
  }
  Ext.apply(containerConfig, config);

  return containerConfig;
}


