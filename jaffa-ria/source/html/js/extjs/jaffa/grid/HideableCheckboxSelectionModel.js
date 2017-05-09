/**
 * @class Jaffa.grid.HideableCheckboxSelectionModel
 * @extends Ext.grid.CheckboxSelectionModel
 * @author SeanZ
 * @constructor
 * @param {Object} config Configuration object
 */
Jaffa.grid.HideableCheckboxSelectionModel = function(config){
   this.editable = false;
    Ext.apply(this, config);

    this.disabledRows = new Ext.util.MixedCollection(false, function(o){
       return o.id;
    });

    Jaffa.grid.HideableCheckboxSelectionModel.superclass.constructor.call(this);

};
/**
 * This is like is base class except that the selection checkbox can be hidden on
 * a per row basis. This is used when only a sub-set of the rows being displayed can
 * be selected.
 * <p>
 * Unless you override the {@link #renderer} method, this behaves just like the {@link Ext.grid.CheckboxSelectionModel}
 * <p>
 * Example
var sm = new Ext.grid.HideableCheckboxSelectionModel({
    renderer: function(v, p, record){
        if (record.get('status') != 'I') {
            record.selectable = false;
            return '<div> </div>';
        } else {
            record.selectable = true;
            return '<div class="x-grid3-row-checker"> </div>';
        }
    }
});
 */
Ext.extend(Jaffa.grid.HideableCheckboxSelectionModel, Ext.grid.CheckboxSelectionModel, {

    // private
    initEvents : function(){
       Jaffa.grid.HideableCheckboxSelectionModel.superclass.initEvents.call(this);
       this.on("beforerowselect", function(sm, index, keepExisting, r) {
           if (r.selectable)
               return !(sm.disabledRows.key(r.id));
           else
               return false;
       }, this);
    },

    disableRow: function(index) {
       if(this.locked || (index < 0 || index >= this.grid.store.getCount())) return;
       var r = this.grid.store.getAt(index);
       if(r){
           this.disabledRows.add(r);
           this.deselectRow(index);
           this.grid.getView().getCell(index, 0).firstChild.firstChild.className = '';
       }
    },

    enableRow: function(index) {
       if(this.locked || (index < 0 || index >= this.grid.store.getCount())) return;
       var r = this.grid.store.getAt(index);
       if(r){
           this.disabledRows.remove(r);
           this.grid.getView().getCell(index, 0).firstChild.firstChild.className = 'x-grid3-hd-checker';
       }
    },

    onMouseDown : function(e, t){
         if(t.href && t.href!='' && t.href!=null){
            e.stopEvent();
        }else if(t.className == 'x-grid3-row-checker'){
            e.stopEvent();
            var row = e.getTarget('.x-grid3-row');
            if(row){
                var index = row.rowIndex;
                if(this.isSelected(index)){
                    this.deselectRow(index);
                }else{
                    this.selectRow(index, true);
                }
            }
        }
    },

    /**
     * @Override this method when creating a selection model to conditionally disable rows
     */
    renderer : function(v, p, record){
        if (false){         //Add custom condition here to allow checkbox on subset of records
            record.selectable = false;
            return '<div> </div>';
        }else{
            record.selectable = true;
            return '<div class="x-grid3-row-checker"></div>';
        }
    }

});
