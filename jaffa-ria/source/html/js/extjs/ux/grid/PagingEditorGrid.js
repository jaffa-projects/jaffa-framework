/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/**
 * @class Ext.ux.grid.PagingEditorGrid 
 * @extends Ext.grid.EditorGridPanel
 * @author Nagaraju Punna
 */
Ext.ux.grid.PagingEditorGrid = Ext.extend(Ext.grid.EditorGridPanel, {
  /** When creating the store, register an internal callback for post load processing
   */
  constructor: function(config) {
      config = config || {};
      config.bbar = config.bbar || [];
      config.bbar = config.bbar.concat([{
          xtype: 'tbfill'
        }, {
          xtype: 'tbtext',
          itemId: 'counter',
          text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbfill')
        } // FIXME: Label Token
        , {
          xtype: 'tbspacer'
        }, {
          xtype: 'tbbutton',
          itemId: 'loading',
          hidden: true,
          disabled: true,
          iconCls: "x-tbar-loading"
        }, {
          xtype: 'tbseparator'
        }, {
          xtype: 'tbbutton',
          itemId: 'more',
          text: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.tbseparator'),
          handler: function() {
            this.store.loadMore(false);
          },
          scope: this
        }
        // FIXME: Label Token
      ]);

      Ext.ux.grid.PagingEditorGrid.superclass.constructor.apply(this, arguments);

      // Create Event that asks for more data when we scroll to the end
      this.on("bodyscroll", function() {
        var s = this.view.scroller.dom;
        if ((s.offsetHeight + s.scrollTop + 5 > s.scrollHeight) && !this.isLoading) {
          console.debug("Grid.on.bodyscroll: Get more...");
          this.store.loadMore(false);
        }
      }, this);

      // When the grid start loading, display a loading icon
      this.store.on("beforeload", function(store, o) {
        if (this.isLoading) {
          console.debug("Store.on.beforeload: Reject Load, one is in progress");
          return false;
        }
        Ext.get(document.body).mask(Labels.get('label.Jaffa.Mask.LoadingSpecialAttributes'), 'x-mask-loading');
        this.isLoading = true;
        if (this.rendered) {
          this.barLoading.show();
        }
        console.debug("Store.on.beforeload: options=", o, this);
        return true;
      }, this);

      // When loading has finished, disable the loading icon, and update the row count
      this.store.on("load", function() {
        delete this.isLoading;
        Ext.get(document.body).unmask();
        if (this.rendered)
          this._updateRowCount();
        return true;
      }, this);

      // When a loading error occurs, disable the loading icon and display error
      this.store.on("loadexception", function(store, e) {
        console.debug("Store.loadexception.Event:", arguments);
        delete this.isLoading;
        if (this.rendered) {
          this.barLoading.hide();
        }
        if (e)
          Ext.Msg.show({
            titleToken: 'title.jaffa.jaffaRIA.finder.FinderGrids.ShowDetails',
            msg: Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.ErrorLoadingRecordsMsg') + " " + e,
            buttons: Ext.Msg.OK,
            icon: Ext.MessageBox.ERROR
          });
        return false;
      }, this);

      // As the default onLoad to refocus on the first row has been disabled,
      // This has been added so if a load does happen, and its an initial load
      // it refocuses. If this is a refresh caused by a sort/group or a new page
      // of data being loaded, it does not refocus
      this.store.on("load", function(r, o) {
        if (o && o.initial == true)
          Ext.ux.grid.MultiGroupingView.superclass.onLoad.call(this);
      }, this.view);
    }

    // private
    ,
  onRender: function(ct, position) {
      Ext.ux.grid.PagingEditorGrid.superclass.onRender.call(this, ct, position);
      var bb = this.getBottomToolbar();
      this.barCounter = bb.items.itemAt(bb.items.length - 5);
      this.barMore = bb.items.itemAt(bb.items.length - 1);
      this.barLoading = bb.items.itemAt(bb.items.length - 3);

      //Update the row count in case the query has been executed even before the grid has been rendered.
      //This can happen when more than 1 grids are rendered in a screen.
      if (this.store && this.store.lastOptions)
        this._updateRowCount();
    }

    // private
    ,
  _updateRowCount: function() {
    this.barLoading.hide();
    var barCounterLoadAllid = 'barCounterLoadAll-' + this.id;
    var showText = Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.Showing') + " " + this.store.getCount() + ' ' + Labels.get('label.jaffa.jaffaRIA.finder.FinderGrids.PagingGrid.of') + ' ';
    if (isNaN(this.store.totalCount)) {
      showText += '?';
    } else {
      var allowLoadAll = Rules.get('Jaffa.widgets.grid.allowLoadAll');
      if ((allowLoadAll === true) && this.store.totalCount > this.store.getCount() && this.getStore().loadAll) {
        var loadAllLink = '<a href="javascript:void(0)" id="' + barCounterLoadAllid + '" class="x-form-label-override" >' + this.store.totalCount + '</a>';
        showText += loadAllLink;
      } else {
        showText += this.store.totalCount;
      }
    }
    this.barCounter.setText(showText);
    if (Ext.fly(barCounterLoadAllid))
      Ext.fly(barCounterLoadAllid).on('click', function() {
        this.getStore().loadAll();
      }, this);

    if (this.store.totalCount === this.store.getCount())
      this.barMore.disable();
    else
      this.barMore.enable();
  }
});