/**
 * @author BobbyC
 */
AuditTransactionGrid = function(groupStore, id) {
  console.debug("Create Grid ", groupStore);

  var columns = [
    {dataIndex: 'transactionId', hidden:true},
    {dataIndex: 'processName'},
    {dataIndex: 'subProcessName'},
    {dataIndex: 'reason', hidden:true},
    {dataIndex: 'changeType'},
    {dataIndex: 'createdBy'},
    {dataIndex: 'createdOn'},
    {dataIndex: 'objectName'},
    {dataIndex: 'objectLabel'},
    {dataIndex: 'fieldName'},
    {dataIndex: 'fieldLabel'},
    {dataIndex: 'fromValue'},
    {dataIndex: 'toValue'}
  ];

  var groupView = new Ext.ux.grid.MultiGroupingView({
    hideGroupedColumn :true,
    displayEmptyFields: true
  });

  AuditTransactionGrid.superclass.constructor.call(this, {
    id: id,
    store: groupStore,
    width: 100,
    view: groupView,
    plugins: [new Ext.ux.plugins.MetaColumns({columns:columns, record:groupStore.recordType, filter:true}),
      new Ext.ux.plugins.ExportToExcelPlugin({
        serviceClassName:'org.jaffa.components.audit.apis.AuditTransactionViewService',
        criteriaClassName:'org.jaffa.components.audit.apis.data.AuditTransactionCriteria'
      })],
    autoScroll: true,
    frame: true,
    loadMask: true,
    forceFit: false,
    region: "center",
    collapsible: false,
    disabled: true,
    title: Labels.get('label.Jaffa.Audit.AuditTransaction') + ' ' + Labels.get('title.Jaffa.Finder.Results'),
    id :'finder-grid'
  });

  this.store.on("load", function() {
    console.debug("Enable Panel");
    if(this.disabled)
      this.enable();
  }, this);

};

Ext.extend(AuditTransactionGrid, Ext.ux.grid.MultiGroupingPagingGrid, {
});
