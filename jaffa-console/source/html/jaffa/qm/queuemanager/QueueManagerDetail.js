/** @class QueueManagerDetail
 * Main Tab Panel to display queue records
 * Ext.ModalWindow is defined in js\extjs\ux\form\ModalWindow.js and is not an extjs class
 * textOnly is not a config option of Ext.form.TextField,
 */
Jaffa.QM.QueueManagerDetail = Ext.extend(Jaffa.maintenance.FormPanel, {
  constructor: function (config) {
    var queueRecords = [];
    if (config && config.controller && config.controller.model && config.controller.model.getCount() >= 1)
      queueRecords = config.controller.model.getRange();

    this.QueueRecord = Jaffa.QM.QueueManagerHelper.queueRecord();

    this.store = new Ext.data.Store({
        proxy   : new Ext.data.MemoryProxy(),
        reader: new Ext.data.DwrReader({},this.QueueRecord),
        sortInfo: {
            field    : 'type',
            direction: 'ASC'
        }
    });


    this.dataview = new Ext.DataView({
        store: this.store,
        tpl  :new Ext.XTemplate(
          '<tpl for=".">',
            '<div class="queue-wrap" id="{type}">',
              '<div class="queue queue-{displayStatus}" ext:qtitle="{type}" ext:qtip="{quickTip}">',
                '<div class="queueType', Ext.isIE?'-ie':'', ' queueType-{queueSystemId}"></div>',
                '<span>{type}</span>',
                '<div class="runStatus', Ext.isIE?'-ie':'', ' runStatus-{runStatus}"></div>',
              '</div>',
            '</div>',
          '</tpl>'
        ),

        plugins : [
          new Ext.DataView.DragSelector()
        ],
        id: 'queues-view',

        itemSelector: 'div.queue-wrap',
        overClass   : 'x-view-over',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true,
        listeners:{
          dblclick: function(dataView, index, node, e){
              this.loadTransactionFinder(dataView, index, node, e);
          },
          scope: this
        }
    });

    config = Ext.applyIf(config || {}, {
      metaClass: 'QueueGraph',
      layout: 'fit',
      title: Labels.get('label.Jaffa.QM.QueueManager.Dashboard'),
      labelWidth: 150,
      bodyStyle: 'padding: 0px',
      tbar: [{
          text: Labels.get('label.Jaffa.QM.QueueManager.SortBy') + ":"
        }, {
          id: 'sortSelect',
          xtype: 'combo',
          typeAhead: true,
          triggerAction: 'all',
          width: 100,
          editable: false,
          mode: 'local',
          displayField: 'desc',
          valueField: 'name',
          lazyInit: false,
          value: 'type',
          store: new Ext.data.ArrayStore({
            fields: ['name', 'desc'],
            data : [['queueSystemId', Labels.get('label.Commons.Type')],['type', Labels.get('label.Commons.Name')],['displayStatus', Labels.get('label.Commons.Status')]]
          }),
          listeners: {
            'select': {fn:this.sortQueues, scope:this}
          }
        },{
          text: Labels.get('label.Jaffa.QM.QueueManager.FilterBy') + ":"
        }, {
          id: 'filterSystem',
          xtype: 'combo',
          typeAhead: true,
          triggerAction: 'all',
          width: 100,
          editable: false,
          mode: 'local',
          displayField: 'desc',
          valueField: 'name',
          lazyInit: false,
          value: params.sortFilter || '',
          store: new Ext.data.ArrayStore({
            fields: ['name', 'desc'],
            data : [['', Labels.get('label.Commons.AllQueues')],['WKTR', Labels.get('label.Commons.WKTR')],['Transaction', Labels.get('label.Commons.Transaction')]]
          }),
          listeners: {
            'select': {fn:this.filterQueues, scope:this}
          }
        }],
      items: [this.dataview]
    })
    Jaffa.QM.QueueManagerDetail.superclass.constructor.call(this, config);
  },
  loadTransactionFinder:function(dataView, index, node, e){
    var queue = dataView.getStore().getAt(index);
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
        type:{
          values:[queue.get('type')]
        },
        queueSystemId:{
          values: [queue.get('queueSystemId')]
        }
      });
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
  },
  loadData: function () {
    this.store.loadData({
      graphs: myQueueManager.model.getRange()
    }, false);
    if (Ext.getCmp('sortSelect').getValue())
      this.sortQueues();
    if (Ext.getCmp('filterSystem').getValue()!='')
      this.filterQueues();
  },
  sortQueues : function(){
    var v = Ext.getCmp('sortSelect').getValue();
    this.dataview.store.sort(v,'ASC');
  },
  filterQueues : function(){
    this.dataview.store.clearFilter();
    var filter = Ext.getCmp('filterSystem');
    if (filter.getValue()!='')
      this.dataview.store.filter('queueSystemId', filter.getValue());
  },
  setDirty: Ext.emptyFn
});
Ext.DataView.DragSelector = function(cfg){
    cfg = cfg || {};
    var view, proxy, tracker;
    var rs, bodyRegion, dragRegion = new Ext.lib.Region(0,0,0,0);
    var dragSafe = cfg.dragSafe === true;

    this.init = function(dataView){
        view = dataView;
        view.on('render', onRender);
    };
    function fillRegions(){
        rs = [];
        view.all.each(function(el){
            rs[rs.length] = el.getRegion();
        });
        bodyRegion = view.el.getRegion();
    }
    function cancelClick(){
        return false;
    }
    function onBeforeStart(e){
        return !dragSafe || e.target == view.el.dom;
    }
    function onStart(e){
        view.on('containerclick', cancelClick, view, {single:true});
        if(!proxy){
            proxy = view.el.createChild({cls:'x-view-selector'});
        }else{
            if(proxy.dom.parentNode !== view.el.dom){
                view.el.dom.appendChild(proxy.dom);
            }
            proxy.setDisplayed('block');
        }
        fillRegions();
        view.clearSelections();
    }
    function onDrag(e){
        var startXY = tracker.startXY;
        var xy = tracker.getXY();

        var x = Math.min(startXY[0], xy[0]);
        var y = Math.min(startXY[1], xy[1]);
        var w = Math.abs(startXY[0] - xy[0]);
        var h = Math.abs(startXY[1] - xy[1]);

        dragRegion.left = x;
        dragRegion.top = y;
        dragRegion.right = x+w;
        dragRegion.bottom = y+h;

        dragRegion.constrainTo(bodyRegion);
        proxy.setRegion(dragRegion);

        for(var i = 0, len = rs.length; i < len; i++){
            var r = rs[i], sel = dragRegion.intersect(r);
            if(sel && !r.selected){
                r.selected = true;
                view.select(i, true);
            }else if(!sel && r.selected){
                r.selected = false;
                view.deselect(i);
            }
        }
    }
    function onEnd(e){
        if (!Ext.isIE) {
            view.un('containerclick', cancelClick, view);
        }
        if(proxy){
            proxy.setDisplayed(false);
        }
    }
    function onRender(view){
        tracker = new Ext.dd.DragTracker({
            onBeforeStart: onBeforeStart,
            onStart: onStart,
            onDrag: onDrag,
            onEnd: onEnd
        });
        tracker.initEl(view.el);
    }
};