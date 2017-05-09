/**
 * @author Sean Zhou, July 2010
 */
Jaffa.Transaction.TransactionRecord = Jaffa.data.Record.create([
  {name: 'id'}, 
  {name: 'direction',      sortable:true, filter:true}, 
  {name: 'type',           sortable:true, filter:true}, 
  {name: 'subType',        sortable:true, filter:true}, 
  {name: 'status',         sortable:true, filter:true}, 
  {name: 'errorMessage',   sortable:true, filter:true}, 
  {name: 'createdOn',      sortable:true, filter:true,   layout:'[datetime.format]'},
  {name: 'createdBy',      sortable:true, filter:true},
  {name: 'lastChangedOn',  sortable:true, filter:true,   layout:'[datetime.format]'},
  {name: 'lastChangedBy',  sortable:true, filter:true}
]);
Ext.override(Jaffa.Transaction.TransactionRecord, {
  initData: function(data) {}
});
Jaffa.Transaction.TransactionRecord.defaultMetaClass = 'TransactionGraph';

Jaffa.Transaction.TransactionFinderContainer = function(config){
  config = Ext.apply({}, config);
  var sm = new Ext.grid.CheckboxSelectionModel();
  
  var containerConfig = {
    // FIXME -- sortInfo in the multigroupingstore is automatically set to {field: undefined, direction: 'ASC'} from state manager
    xtype:'findercontainer',
    store: new Ext.ux.grid.MultiGroupingPagingDWRStore({
      sortInfo: [
        {field: 'direction', direction: 'ASC'}
        ,{field: 'status', direction: 'ASC'}
        ,{field: 'type', direction: 'ASC'}
        ,{field: 'subType', direction: 'ASC'}
      ],
      proxy: new Ext.data.DWRProxy(Jaffa_Transaction_TransactionService.query)
      ,reader: new Ext.data.DwrReader({},Jaffa.Transaction.TransactionRecord)
    }),
    layout: 'border', //params.layout.toLowerCase(), 
    region: 'center',
    criteria: params,
    criteriaPanel:{
      ref: 'criteriaPanel',
      xtype:'criteriapanel2',
      title:Labels.get('title.Jaffa.Transaction.TransactionFinder.CriteriaPanel'),
      items:[
        Jaffa.Transaction.TransactionCriteriaSearchTab({id: 'transactionSearchFields'}),
        Jaffa.Transaction.TransactionFieldTab({id: 'transactionFlexFields'})
      ],
      metaClass: 'TransactionGraph',
      defaults: {
        isSearchPanel: true
        ,labelWidth: 150
      },
      baseParams: {
        resultGraphRules: ['*']
      }
    },
    resultsPanel: {
      xtype:'multigroupingpagingfindergrid',
      titleToken: 'title.Jaffa.Transaction.TransactionFinder.ResultsPanel'
      ,itemId: 'transactionResultsPanel'
      ,sm: sm
      ,columns: [sm, 'direction' ,'type', 'subType', 'status', 'errorMessage', 
      'createdOn', 'createdBy', 'lastChangedOn', 'lastChangedBy']
      ,view: new Ext.ux.grid.MultiGroupingView({
         hideGroupedColumn :true
        //,emptyGroupText: 'All Group Fields Empty'
        ,displayEmptyFields: true 
        ,groupTextTpl: '{text} : {group}'
      })
      ,deleteableStatus: ['O', 'S', 'E', 'H']
      ,plugins: [{
        ptype: 'exporttoexcel'
        ,serviceClassName:'org.jaffa.transaction.apis.TransactionService'
        ,criteriaClassName:'org.jaffa.transaction.apis.data.TransactionCriteria'
      }, {
        init: function(grid) {
          var fn = function(grid) {
            var recs = grid.getSelectionModel().getSelections();
            if (Ext.isArray(recs) && recs.length>0) {
              return recs;
            } 
            Ext.MessageBox.alert(Labels.get('title.jaffaRIA.Error'), 
            Labels.get('label.Jaffa.Transaction.TransactionFinder.ResultsPanel.noSelectionError'));
            return [];
          };
          grid.getBottomToolbar().getComponent('deleteBtn').handler = (function(btn) {
            var recs = fn(this), delData=[];
            if (recs.length==0) return;
            for (var i=0; i<recs.length; i++) {
              if (this.deleteableStatus.indexOf(recs[i].get('status'))>=0) {
                delData.push({id: recs[i].get('id'), deleteObject: true, isChanged: true});
              } else {
                Ext.MessageBox.alert(Labels.get('title.jaffaRIA.Error'), 
                Labels.get('label.Jaffa.Transaction.TransactionFinder.ResultsPanel.DeleteError', recs[i].get('status')));
                return;
              }
            }
            Ext.MessageBox.confirm(Labels.get('title.jaffaRIA.Confirm'),
              Labels.get('label.Jaffa.Transaction.TransactionFinder.DeleteConfirm'),
              function(btn) {
                if (btn=='yes') {
                  this.getController().processSave(delData, 'label.Jaffa.Transaction.TransactionFinder.ResultsPanel.DeleteSuccess');
                }
              }, this);
          }).createDelegate(grid);
          grid.getBottomToolbar().getComponent('retryBtn').handler = (function(btn) {
            var recs = fn(this), retryData=[];
            if (recs.length==0) return;
            for (var i=0; i<recs.length; i++) {
              if (recs[i].get('status')=='E') {
                retryData.push({id: recs[i].get('id'), status: 'O', isChanged: true});
              } else {
                Ext.MessageBox.alert(Labels.get('title.jaffaRIA.Error'), 
                Labels.get('label.Jaffa.Transaction.TransactionFinder.ResultsPanel.RetryError'));
                return;
              }
            }
            this.getController().processSave(retryData, 'label.Jaffa.Transaction.TransactionFinder.ResultsPanel.RetrySuccess');
          }).createDelegate(grid);
          
          grid.getStore().on('load', function(store, records) {
            if (records && records.length>0) {
              this.getComponent('deleteBtn').enable();
              this.getComponent('retryBtn').enable();
            }
          }, grid.getBottomToolbar(), {single: true});
        }
      }]
      ,bbar: ['-', {
        iconCls: 'delete',
        text: Labels.get('label.Jaffa.Widgets.Button.Delete'),
        itemId: 'deleteBtn'
        ,disabled: true
      }, '-', {
        iconCls: 'arrow_redo',
        text: Labels.get('label.JaffaRIA.Button.Retry'),
        itemId: 'retryBtn'
        ,disabled: true
      }]
      ,getController: function() {
        var grid = this;
        if (!this.controller) {
          this.controller = new Jaffa.component.CRUDController ({
            proxy: new Jaffa.data.DWRCRUDProxy({
              update: Jaffa_Transaction_TransactionService.update
            })
            ,processSave: function(saveModel, msgToken) {
              this.addListener('beforesave', function (c) {
                c.saveModel = saveModel;
              });
              
              var orderLen = saveModel.length, msgLabel = msgToken;
              // Invoke save method on the controller to delete/retry the transactions.
              this.save(function (model, response) {
                Ext.MessageBox.show({
                  titleToken: 'title.jaffaRIA.Success',
                  msg: Labels.get(msgLabel, orderLen),
                  buttons: Ext.MessageBox.OK,
                  icon: Ext.MessageBox.INFO,
                  scope: grid,
                  fn: function () {
                    this.getStore().loadMore(true);
                  }
                });
              });
            }
          });
        }
        return this.controller;
      }
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
  
  if (config.plugins) {
    if (!Ext.isArray(config.plugins)) config.plugins = [config.plugins];
    if (containerConfig.plugins) {
      if (!Ext.isArray(containerConfig.plugins)) containerConfig.plugins = [containerConfig.plugins];
      containerConfig.plugins = containerConfig.plugins.concat(config.plugins);
      delete config.plugins;
    }
  }
  
  Ext.apply(containerConfig, config);
  
  return containerConfig;
};
