/** @class QueueManagerList
 * Main Tab Panel to display queue records in list form
 * Ext.ModalWindow is defined in js\extjs\ux\form\ModalWindow.js and is not an extjs class
 * textOnly is not a config option of Ext.form.TextField, 
 */
Jaffa.QM.QueueManagerList = Ext.extend(Ext.grid.GridPanel, {
  constructor: function (config) {
    this.QueueRecord = Jaffa.QM.QueueManagerHelper.queueRecord();
    var sm = new Jaffa.grid.HideableCheckboxSelectionModel();
    var columns = [sm, {
        header: Labels.get('label.Jaffa.QM.Queue.Type'),
        dataIndex: 'type',
        width: 200,
        sortable: true
      }, {
        header: Labels.get('label.Jaffa.QM.Queue.Status'),
        dataIndex: 'status',
        width: 100,
        sortable: true
      }, {
        header: Labels.get('label.Jaffa.QM.Queue.OpenCount'),
        dataIndex: 'openCount',
        width: 150,
        sortable: true,
        type: 'int'
      }, {
        header: Labels.get('label.Jaffa.QM.Queue.InProcessCount'),
        dataIndex: 'inProcessCount',
        width: 150,
        sortable: true,
        type: 'int'
      }, {
        header: Labels.get('label.Jaffa.QM.Queue.ErrorCount'),
        dataIndex: 'errorCount',
        width: 150,
        sortable: true,
        type: 'int'
      }];
    config = Ext.applyIf(config || {}, {
      sm: sm,
      metaClass: 'QueueGraph',
      title: Labels.get('label.Jaffa.QM.QueueManager.ListView'),
      layout: 'fit',
      width: 750,
      height: 250,
      frame: false,
      border: false,
      plugins: [
        new Ext.ux.plugins.MetaColumns({columns: columns}),
        new Jaffa.maintenance.plugins.GridLoadSave({
          findDataSource: function(){
            return this.controller.model;
          }
        })
        ],
      ds: new Ext.data.GroupingStore({
        sortInfo: {
          field: 'type',
          direction: 'ASC'
        },
        proxy: new Ext.data.MemoryProxy(),
        reader: new Ext.data.JsonReader({
          id: 'type'
        }, this.QueueRecord)
      }),
      listeners: {
        rowdblclick: function(grid, index, e){
          var queue = grid.getStore().getAt(index);
          var tab = Ext.getCmp(queue.get('type'));
          var reload = function(){
            var cp = tabLog.items[0];
            cp.store.baseParams = cp.baseParams;
            cp.store.loadMore(true);
          };
          if (!tab) {
            var tabLog = new Jaffa.QM.TransactionFinder({
              controller: myQueueManager,
              type: queue.get('type'),
              queueSystemId: queue.get('queueSystemId'),
              resultsPanel:{
                ref: 'resultsPanel',
                header: false
              },
              criteriaPanel: {
                hidden: true
              },
              criteria: {},
              load: function(){
                this.resultsPanel.bottomToolbar.getComponent('excel').enable();
              },
              listeners: {
                beforedestroy: function(){
                  myQueueManager.un('load', reload);
                }
              }
            });
            Ext.getCmp('viewport').addTabPanel(tabLog);

            Ext.apply(tabLog.items[0].baseParams, {
              resultGraphRules:['**'],
              type:{values:[queue.get('type')]},
              queueSystemId:{values: [queue.get('queueSystemId')]}});
            var cp = tabLog.items[0];
            cp.store.baseParams = cp.baseParams;

            //Apply default filter to grid to hide successful transactions
            var statusFilter = tabLog.items[1].filters.getFilter('status');
            statusFilter.menu.setSelected(['ERROR','IN_PROCESS','OPEN']);
            statusFilter.active = true;
            cp.store.loadMore(true);

            myQueueManager.on('load', reload);
          } else{
            tab.ownerCt.activate(tab);
          }
        }
      }
    });
    Jaffa.QM.QueueManagerList.superclass.constructor.call(this, config);
  },
  setDirty: Ext.emptyFn
});