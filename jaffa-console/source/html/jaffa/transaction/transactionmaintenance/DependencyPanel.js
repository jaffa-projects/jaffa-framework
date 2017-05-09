
Jaffa.Transaction.DependencyRecord = Jaffa.data.Record.create([
  {
    name: 'transactionId',
    key: true,
    hidden: true,
    sortable: true
  },
  {
    name: 'dependsOnId',
    metaField: 'dependsOnId',
    mapping: 'dependsOnId',
    sortable: true
  }, 
  {
    name: 'status',
    metaField: 'status',
    mapping: 'status',
    sortable: true
  }
]);
Jaffa.Transaction.DependencyRecord.defaultMetaClass = 'TransactionDependencyGraph';


//////////////////////////////////////////////////////////////
///                                                        ///
///               DEPENDENCIES GRID PANEL                  /// 
///                                                        ///
//////////////////////////////////////////////////////////////


Jaffa.Transaction.DependencyPanel = {
  xtype: 'grid',
  title: Labels.get('label.Jaffa.Transaction.TransactionMaintenance.DependencyTab'),  //Dependencies
  plugins:[
    {
      ptype: 'metacolumns',
      metaClass: 'TransactionDependencyGraph',
      record: Jaffa.Transaction.DependencyRecord,
      columns: [
        'transactionId',
        'dependsOnId',
        'status'
      ]
    },
    {
      ptype: 'gridloadsave',
      findDataSource: function(){
        if (!this.controller.model.itemAt(0).transactionDependencies) {
            this.controller.model.itemAt(0).transactionDependencies = new Ext.util.MixedCollection();
        }
        return this.controller.model.itemAt(0).transactionDependencies;
      }
    }
  ],
  metaClass: 'TransactionDependencyGraph',
  store: {
    xtype: 'store',
    id: 'transactionDependencyStore',
    recordType: Jaffa.Transaction.DependencyRecord,
    sortInfo: {
      field: 'transactionId',
      direction: "ASC"
    },
    reader: new Ext.data.JsonReader({
      idProperty: 'transactionId'
    }, Jaffa.Transaction.DependencyRecord)
  }
};