/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/**
 * @class Ext.ux.grid.MultiGroupingGrid
 * @extends Ext.grid.GridPanel
 * @author chander, PaulE
 * @TODO - Don't allow a field to be grouped by, if it is not sortable in the column model!!!
 *
 * Provided an extended grid panel where the top toolbar is used to drag grouping columns to.
 * <p>
 * Credits - Based on Original Work found at http://extjs.com/forum/showthread.php?p=203828#post203828
 */
Ext.ux.grid.MultiGroupingGrid = function(config) {
    config = config||{};

    // Cache the orignal column model, before state is applied
    if(config.cm)
      this.origColModel = Ext.ux.clone(config.cm.config);
    else if(config.colModel)
      this.origColModel = Ext.ux.clone(config.colModel.config);

    config.tbar = [{
       xtype:'tbtext'
      ,text:this.emptyToolbarText
    },{
       xtype:'tbfill'
      ,noDelete:true
    },{
       xtype:'tbbutton'
      ,text:this.optionMenuText // "Options"
      ,noDelete:true
      ,itemId: 'options'
      ,menu:{
         items: [/*{
           text:this.restoreMenuText // "Restore Default Column Settings"
           ,scope: this
           ,handler: function() {
               var id = this.getStateId();
               if(id){
                 var state = Ext.state.Manager.clear(id);
                 var state = this.origState;
                 if(state){
                   if(this.fireEvent('beforestaterestore', this, state) !== false){
                     this.applyState(Ext.apply({}, state));
                     this.fireEvent('staterestore', this, state);
                   }
                 }
               }
             return true;
           }
         },*/{
           text:this.groupColumnsMenuText // "Show Grouped Columns"
          ,checked: !config.view.hideGroupedColumn
          ,scope:this
          ,checkHandler: function (item, checked) {
             this.view.setHideGroupedColumn(!checked);
           }
         }]
       }
    }];
    Ext.ux.grid.MultiGroupingGrid.superclass.constructor.call(this, config);
    //console.debug("Create MultiGroupingGrid",config);
};
Ext.extend(Ext.ux.grid.MultiGroupingGrid, Ext.grid.GridPanel, {

  /** Text objects that can be replaced for internationalization */
  optionMenuText: 'Options',
  restoreMenuText: 'Restore Default Columns',
  groupColumnsMenuText: 'Show Grouped Columns',
  clearFiltersMenuText: 'Clear Filters', // Labels.get('label.jaffa.jaffaRIA.jaffa.finder.grid.deactivateFilters')


   initComponent : function(){
     //console.debug("MultiGroupingGrid.initComponent",this);
     Ext.ux.grid.MultiGroupingGrid.superclass.initComponent.call(this);

     // Initialise DragZone
     this.on("render", this.setUpDragging, this);

     this.on("beforerender", function(){
       // If this has filters, add the Clear Filter option
       if(this.filters && this.filters.filters.getCount()>0) {
         var opt = {
            text: this.clearFiltersMenuText // "Clear Filters"
           ,scope: this
           ,handler: function(){ this.filters.clearFilters(); }
         };
         if(Ext.isArray(this.getTopToolbar())) {
            this.getTopToolbar()[2].menu.items.push(opt);
         } else {
            var menu = this.getTopToolbar().items.itemAt(2);
            if (menu.menu)
              menu = menu.menu;
            menu.add(opt);
         }
       }
     },this);

     this.on("beforestaterestore", function(){
       if (!this.origState) this.origState = this.getState();
     });

     this.on("render", function(){
       if (!this.origState) this.origState = this.getState();
     });

   }

   /** @cfg emptyToolbarText String to display on tool bar when there are no groups
    */
  ,emptyToolbarText : "Drop Columns Here To Group"
   /** Extend basic version so the Grouping Columns State is remebered
    */
  ,getState : function(){
     var s = Ext.ux.grid.MultiGroupingGrid.superclass.getState.call(this);
     s.groupFields = this.store.getGroupState();
     return s;
   }

  ,setUpDragging: function() {
     //console.debug("SetUpDragging", this);
     this.dragZone = new Ext.dd.DragZone(this.getTopToolbar().getEl(), {
       ddGroup:"grid-body" + this.getGridEl().id //FIXME - does this need to be unique to support multiple independant panels on the same page
      ,panel:this
      ,scroll:false
       // @todo - docs
      ,onInitDrag : function(e) {
         // alert('init');
         var clone = this.dragData.ddel;
         clone.id = Ext.id('ven'); //FIXME??
         // clone.class='x-btn button';
         this.proxy.update(clone);
         return true;
       }

       // @todo - docs
      ,getDragData: function(e) {
         var target = Ext.get(e.getTarget().id);
         //console.debug("DragZone: ",e,target);
         if(!target || target.hasClass('x-toolbar x-small-editor')) {
           return false;
         }

         d = e.getTarget().cloneNode(true);
         d.id = Ext.id();
         console.debug("getDragData",this, target);

         this.dragData = {
           repairXY: Ext.fly(target).getXY(),
           ddel: d,
           btn:e.getTarget()
         };
         return this.dragData;
       }

       //Provide coordinates for the proxy to slide back to on failed drag.
       //This is the original XY coordinates of the draggable element.
      ,getRepairXY: function() {
         return this.dragData.repairXY;
       }
     });

     // This is the target when columns are dropped onto the toolbar (ie added to the group)
     this.dropTarget2s = new Ext.dd.DropTarget(this.getTopToolbar().getEl(), {
       ddGroup: "gridHeader" + this.getGridEl().id
      ,panel:this
      ,notifyDrop: function(dd, e, data) {
         if (this.panel.getColumnModel().config[this.panel.getView().getCellIndex(data.header)].groupable) {
             console.debug("Adding Filter", data);
             var btname = this.panel.getColumnModel().getDataIndex(this.panel.getView().getCellIndex(data.header));
             this.panel.store.groupBy(btname);
             return true;
         }
         else {
             return false;
         }
       }
       ,notifyOver: function(dd,e,data){
         if (this.panel.getColumnModel().config[this.panel.getView().getCellIndex(data.header)].groupable) {
             return this.dropAllowed;
         }
         else {
             return this.dropNotAllowed;
         }
       }
     });

     // This is the target when columns are dropped onto the grid (ie removed from the group)
     this.dropTarget22s = new Ext.dd.DropTarget(this.getView().el.dom.childNodes[0].childNodes[1], {
       ddGroup: "grid-body" + this.getGridEl().id  //FIXME - does this need to be unique to support multiple independant panels on the same page
      ,panel:this
      ,notifyDrop: function(dd, e, data) {
         var txt = Ext.get(data.btn).dom.innerHTML;
         var tb = this.panel.getTopToolbar();
         console.debug("Removing Filter", txt);
         var bidx = tb.items.findIndexBy(function(b) {
           console.debug("Match button ",b.text);
           return b.text==txt;
         },this);
         console.debug("Found matching button", bidx);
         if(bidx<0) return; // Error!
         var fld = tb.items.get(bidx).fieldName;

         // Remove from toolbar
         Ext.removeNode(Ext.getDom(tb.items.get(bidx).id));
         if(bidx>0) Ext.removeNode(Ext.getDom(tb.items.get(bidx-1).id));;

         console.debug("Remove button", fld);
         //console.dir(button);
         var cidx=this.panel.view.cm.findColumnIndex(fld);

         if(cidx<0)
           console.error("Can't find column for field ", fld);

         this.panel.view.cm.setHidden(cidx, false);

         //Ext.removeNode(Ext.getDom(data.btn.id));

         // Remove this group from the groupField array
         // @todo - replace with method on store
         // this.panel.store.removeGroupField(fld);
         var temp=[];
         for(var i=this.panel.store.groupField.length-1;i>=0;i--) {
           if(this.panel.store.groupField[i]==fld) {
             this.panel.store.groupField.pop();
             break;
           }
           temp.push(this.panel.store.groupField[i]);
           this.panel.store.groupField.pop();
         }

         for(var i=temp.length-1;i>=0;i--) {
           this.panel.store.groupField.push(temp[i]);
         }

         if(this.panel.store.groupField.length==0)
           this.panel.store.groupField=false;

         this.panel.store.fireEvent('datachanged', this);
         return true;
       }
     });
   }
});

/**
 * @class Ext.ux.grid.MultiGroupingPagingGrid
 * @extends Ext.ux.grid.MultiGroupingGrid
 * A specialized {@link Ext.data.Store} that allows data to be appended a page at
 * a time as the user scrolls through. It is based on performing server-side sorting
 * and grouping and should be used in conjunction with a {@link Ext.ux.grid.MultiGroupPagingGrid}
 * @constructor
 * Create a new MultiGroupingPagingStore
 * @param {Object} config The config object
 *
 * @author PaulE
 */
Ext.ux.grid.MultiGroupingPagingGrid = Ext.extend(Ext.ux.grid.MultiGroupingGrid, {

  /** When creating the store, register an internal callback for post load processing
   */
    constructor: function(config) {
      config = config||{};
      config.bbar = config.bbar||[];
      config.bbar = config.bbar.concat([
        {xtype:'tbfill'}
       ,{xtype:'tbtext', itemId:'counter', text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbfill')}// FIXME: Label Token
       ,{xtype:'tbspacer'}
       ,{xtype:'tbbutton', itemId:'loading',hidden: true,disabled:true,iconCls: "x-tbar-loading"}
       ,{xtype:'tbseparator'}
       ,{xtype:'tbbutton', itemId:'more',text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbseparator'),handler: function() { this.store.loadMore(false); }, scope: this}
       // FIXME: Label Token
      ]);

      Ext.ux.grid.MultiGroupingPagingGrid.superclass.constructor.apply(this, arguments);

      // Create Event that asks for more data when we scroll to the end
      this.on("bodyscroll",  function() {
            var s = this.view.scroller.dom;
            if( (s.offsetHeight+s.scrollTop+5 > s.scrollHeight) && !this.isLoading) {
              console.debug("Grid.on.bodyscroll: Get more...");
              this.store.loadMore(false);
            }
      }, this);

      // When the grid start loading, display a loading icon
      this.store.on("beforeload", function(store,o) {
        if(this.isLoading) {
          console.debug("Store.on.beforeload: Reject Load, one is in progress");
          return false;
        }
        this.isLoading = true;
        if(this.rendered) {
          this.barLoading.show();
        }
        console.debug("Store.on.beforeload: options=",o, this);
        return true;
      }, this);

      // When loading has finished, disable the loading icon, and update the row count
      this.store.on("load", function() {
        delete this.isLoading;
        if (this.rendered)
          this._updateRowCount();
        return true;
      }, this);

      // When a loading error occurs, disable the loading icon and display error
      this.store.on("loadexception", function(store, e) {
        console.debug("Store.loadexception.Event:",arguments);
        delete this.isLoading;
        if(this.rendered) {
          this.barLoading.hide();
        }
        if(e)
          Ext.Msg.show({
            titleToken:'title.jaffa.jaffaRIA.finder.FinderGrids.ShowDetails',
            msg: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.ErrorLoadingRecordsMsg')+" " + e,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
          });
        return false;
      }, this);

      // As the default onLoad to refocus on the first row has been disabled,
      // This has been added so if a load does happen, and its an initial load
      // it refocuses. If this is a refresh caused by a sort/group or a new page
      // of data being loaded, it does not refocus
      this.store.on("load", function(r,o) {
         if(o&&o.initial==true)
           Ext.ux.grid.MultiGroupingView.superclass.onLoad.call(this);
      }, this.view);
    }

    // private
   ,onRender : function(ct, position){
        Ext.ux.grid.MultiGroupingPagingGrid.superclass.onRender.call(this, ct, position);
        var bb=this.getBottomToolbar();
        this.barCounter = bb.items.itemAt(bb.items.length-5);
        this.barMore = bb.items.itemAt(bb.items.length-1);
        this.barLoading = bb.items.itemAt(bb.items.length-3);

        //Update the row count in case the query has been executed even before the grid has been rendered.
        //This can happen when more than 1 grids are rendered in a screen.
        if (this.store && this.store.lastOptions)
          this._updateRowCount();
    }

    // private
    ,_updateRowCount: function () {
      this.barLoading.hide();
      var barCounterLoadAllid = 'barCounterLoadAll-'+this.id;
      var showText = Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.Showing')+" " + this.store.getCount() + ' '+Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.of')+' ';
      if (isNaN(this.store.totalCount)){
        showText+='?';
      } else{
        var allowLoadAll =  Rules.get('Jaffa.widgets.grid.allowLoadAll');
        if ((allowLoadAll === true) && this.store.totalCount > this.store.getCount() && this.getStore().loadAll){
          var loadAllLink = '<a href="javascript:void(0)" id="'+barCounterLoadAllid+'" class="x-form-label-override" >' + this.store.totalCount + '</a>';
          showText+=loadAllLink;
        }else{
          showText+=this.store.totalCount;
        }
      }
      this.barCounter.setText(showText);
      if (Ext.fly(barCounterLoadAllid))
        Ext.fly(barCounterLoadAllid).on('click', function() { this.getStore().loadAll();}, this);

      if (this.store.totalCount === this.store.getCount())
        this.barMore.disable();
      else
        this.barMore.enable();
    }
});
