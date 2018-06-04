OrderDetails = Ext.extend( Jaffa.maintenance.FormPanel , {
  constructor: function(config){
    OrderDetails.superclass.constructor.call(this,{
      metaClass: 'OrderDetails'
      ,title: "Order Details"
      ,id:'OrderDetailsPanel'
      ,items:[{
        mapping:"defaultName"
      },{
        mapping:"description"
      }]
      ,tbar: [{
        iconCls: 'save',
        text: Labels.get('label.WorkRecording.WorkOrder.Toolbar.Save'),
        id: 'save',
        scope: this,
        //disabled: true,
        handler: function(){
          this.controller.save();
        }
      }] 
    });
  }
});

ProcessList = Ext.extend (Jaffa.maintenance.GridPanel, {
  constructor: function(config){
    OrderDetails.superclass.constructor.apply(this,arguments);
  }

  ,load: function() {
  
    // Create Store...
  }

});

HeaderPanel = Ext.extend (Jaffa.maintenance.HeaderPanel, {
  titleText : 'Order Number'
  
  ,updateHeader: function() {
    // Stretch the header bit
    var tb=this.getTopToolbar();
    if(tb.rendered && tb.tr)
      tb.tr.cells[0].style.width="100%";
    
    // Update text in toolbar
    var id=Ext.get('headerTitle');
    if(id) {
      if(this.controller.isLoaded) {
        var status = this.controller.model.itemAt(0).status;
        var orderNo = this.controller.model.itemAt(0).defaultName;
        id.update(
                '<span class="headerTitle-text">'
                + this.titleText+' : '+orderNo
                +'</span><span class="headerTitle-text headerTitle-text-'+status+'">'
                +'&nbsp;('+status+')'
                +'</span>'
                +'</div>'
        );
      } else  {
        id.update(
                '<span class="headerTitle-text">'
                + this.titleText+' : NEW RECORD'
                +'</span>'
                +'</div>'
        );
      }
    }
    
    // Update the HTML in the panels body
    var htmlDetails = ""
    var id2 = Ext.get('headerPanel-text');
    if (id2) {
        id2.update(htmlDetails);
    } else {
        htmlDetails ='<div class="headerPanel"><span class="headerPanel-text" id="headerPanel-text">'
                    + htmlDetails
                    + '</span></div>';
        var el = this.getComponent(0);             
        if(el.rendered) {
          console.debug("Update Contents", el, el.getEl());
          el.getEl().update(htmlDetails); //FIXME
        } else              
          el.html = htmlDetails;
    }

  }
});

OrderListTree = Ext.extend(Ext.ux.maximgb.treegrid.GridPanel, {
    constructor: function(config){
        var grid_sm = new Jaffa.grid.HideableCheckboxSelectionModel({
            renderer : function(v, p, record){
                if (record.get('_is_leaf') == false){
                    record.selectable = false;
                    return '<div> </div>';
                }else{
                    record.selectable = true;
                    return '<div class="x-grid3-row-checker"> </div>';
                }
            }    
        });

        OrderListTree.superclass.constructor.call(this, {
            store: config.store,
            master_column_id: 'orderNo',
            columns: [{
                id: 'orderNo',
                header: "Order No",
                width: 100,
                sortable: false,
                dataIndex: 'orderNo'
            }, {
                header: "Default Name",
                width: 75,
                sortable: false,
                dataIndex: 'defaultName'
            }, {
                header: "Description",
                width: 150,
                sortable: false,
                dataIndex: 'description'
            }, {
                header: "Status",
                width: 75,
                sortable: false,
                dataIndex: 'status'
            },grid_sm ],
            stripeRows: true,
            selModel: grid_sm,
            title: 'Order List',
            root_title: 'Orders',
            tbar: [{
                iconCls: 'save',
                text: Labels.get('label.WorkRecording.WorkOrder.Toolbar.Save'),
                id: 'save',
                scope: this,
                //disabled: true,
                handler: function(){
                    this.controller.save();
                }
            }]
        });
        this.wireEvent();
    },
    
    isInBreadCrumbsEl: function(evt) {
      alert(Ext.fly(evt.getTarget()).hasClass('ux-maximgb-treegrid-brditem'));
      return;
      var brcEl = this.getView().getBreadcrumbsEl();
      var el = Ext.fly(evt.getTarget().id);
      if (el) {
        el = el.dom;
        while (el.parentNode && el.id != brcEl.dom.id) {
          el = el.parentNode;
        }
        if (el.id == brcEl.dom.id) {
          alert('in history');
          return true;
        }
      }
      return false;
    },
    
    rowContextMenu: function(grid, rowNum, evt) {
      console.debug(rowNum);
    },
    
    wireEvent: function() {
      this.on('contextmenu', this.isInBreadCrumbsEl, this);
      this.on('rowcontextmenu', this.rowContextMenu, this);
    }
});
          
OrderListRecord = Ext.data.Record.create([{
    name: 'orderNo',
    mapping: 'orderNo'
}, {
    name: 'defaultName',
    mapping: 'defaultName'
}, {
    name: 'description',
    mapping: 'description'
}, {
    name: 'status',
    mapping: 'status'
}, {
    name: '_id',
    type: 'int'},
{
    name: 'nhOrderNo',
    type: 'auto'
}, {
    name: '_is_leaf',
    type: 'bool'
}]);
          
// Define the meta data for the graph object
ClassMetaData.OrderDetails = {
  fields: {
    defaultName: {
      label : 'Default Name'
      ,caseType: 'UpperCase'
      ,type : 'string'
      ,maxLength : 20
      ,mandatory: true
    }
    ,description: {
      label : 'Description'
      ,caseType: 'MixedCase'
      ,type : 'string'
      ,maxLength : 100
    }
  }
  ,key : 'defaultName'
  ,  collectionNames : ['maintainedPositions']

}

Ext.onReady(function(){
  Ext.QuickTips.init();

  // Using an anonymous controller
  myOrders = new Jaffa.component.CRUDController ({
    proxy: new Jaffa.data.HttpCRUDProxy({
      url: params.appCtx + "/tests/extjs/maintenance/data.json"
    })
    ,criteria: {objectLimit: 1, resultGraphRules: ['**']}
  });
  
  var myOrderList = new Jaffa.component.CRUDController({
    proxy: new Jaffa.data.HttpCRUDProxy({
      url: params.appCtx + "/tests/extjs/maintenance/dataList.json"
    }),
    criteria: {
      resultGraphRules: ['**']
    }
  });
          
  RequestController = Ext.extend(Jaffa.component.CRUDController, {
    proxy: new Jaffa.data.HttpCRUDProxy({
      url: "requests.json"
    })
    ,criteria: {objectLimit: 1, resultGraphRules: ['**']}
  });
  
  // Creating a custom controller class
  myRequests = new RequestController();


  /*OrderMaintenanceViewport = Ext.extend(Jaffa.maintenance.Viewport, {
    constructor: function(config) {
      OrderMaintenanceViewport.superclass.constructor.apply(this,arguments);
    }
  });*/

  var viewport = new Jaffa.maintenance.Viewport( {
    controller : myOrders
    ,header : new HeaderPanel({
      dataDicObjectName : 'WorkOrder'
      ,helpPathName : 'workRecording/workOrder/workOrderUpdate'
      ,items: [{border:false}]
    })
  });
  
  
  if(params.defaultName) {
      // Update Order Mode, retrieve it
      myOrders.criteria.defaultName = {
          values: [params.defaultName]
      };
      myOrders.load(function(){
          // What to do after the load
          if (myOrders.isLoaded && myOrders.model && myOrders.model.getCount() == 1) {
              viewport.addTabPanel(new OrderDetails(), myOrders.model.itemAt(0), myOrders);
          }
          else {
              Ext.get(document.body).update();
              //@FIXME: Use label for the error message
              Ext.MessageBox.show({
                  title: 'Load Error',
                  msg: 'DefaultWorkOrder ' + params.defaultName + ' Not Found',
                  width: 400,
                  buttons: Ext.MessageBox.OK,
                  fn: function(){
                      console.debug("Close Browser Window");
                      //@FIXME: Fire the handler associated with the close button
                      //window.close();
                  }
              });
              return false;
          }
      });
      
      myOrderList.load(function(){

          orderListStore = new Ext.ux.maximgb.treegrid.AdjacencyListStore({
              autoLoad: true,
              //FIXME shouldn't require using _parent in related data
              parent_id_field_name: 'nhOrderNo',
              reader: new Ext.data.JsonReader(
                {
                  id: '_id'
                },  OrderListRecord),
              proxy: new Ext.data.MemoryProxy(myOrderList.model.getRange())
          });
          
          // What to do after the load
          grid = new OrderListTree({store:orderListStore});
          
          if (myOrderList.isLoaded && myOrderList.model && myOrderList.model.getCount() > 0) {
              viewport.addTabPanel(grid);//, myOrderList.model, myOrderList);
          }
          else {
              Ext.get(document.body).update();
              //@FIXME: Use label for the error message
              Ext.MessageBox.show({
                  title: 'Load Error',
                  msg: 'Default Work Orders ' + params.defaultName + ' Not Found',
                  width: 400,
                  buttons: Ext.MessageBox.OK,
                  fn: function(){
                      console.debug("Close Browser Window");
                      //@FIXME: Fire the handler associated with the close button
                      //window.close();
                  }
              });
              return false;
          }
      });
  } else {
    // Create mode
    viewport.addTabPanel(new OrderDetails(), null, myOrders);
    
    //register a listener to support the reload after a save
    myOrders.on("beforeload", function (controller, criteria) {
      if (!criteria.defaultName)
        criteria.defaultName = {values: [Ext.getCmp("OrderDetailsPanel").find("mapping", "defaultName")[0].getValue()]};
    });
  }
});


