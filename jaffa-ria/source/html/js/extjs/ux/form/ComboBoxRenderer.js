/**
 * @class Ext.ux
 * @method @static
 * @author SeanZ
 *
 * Render for an editable column that is based on a FinderComboGrid style combo box
 * <p>
 * Credits : Adapted from http://extjs.com/forum/showthread.php?t=21379
 * <p>
 * Example
var tradeCodeCombo = new Jaffa.form.FinderComboGrid({
    id: 'tradeCodeCell',
    metaField: 'tradeCode',
    meta: ClassMetaData.TradeCodeFinderOutDto
});

cols = [...,{
  header:"Labor Trade"
  ,dataIndex:'tradeCode'
  ,width:300
  ,editor: tradeCodeCombo
  ,editable: true
  ,renderer: Ext.ux.comboBoxRenderer(tradeCodeCombo)
},...];
 *
 * @param {Jaffa.form.FinderComboGrid} combo Combo box that is being used to edit this column
 */
Ext.ux.comboBoxRenderer = function(combo) {
  return function(value, metadata, record, rowIdx, colIdx, store) {
    if (value) {
      var rec;
      if (combo.findRecordInStore) {
        rec = combo.findRecordInStore(combo.valueField, value);
      } else {
        rec = combo.findRecord(combo.valueField, value);
      }
      if (rec) {
        console.debug(value+' found record: '+rec.get(combo.displayField));
        return rec.get(combo.displayField);
      } else {
        if (combo.validatedRecords) {
          rec = combo.validatedRecords.get(value);
        }
        if (rec) {
          console.debug(value+' found record in validateRecords bucket: '+rec.get(combo.displayField));
          return rec.get(combo.displayField);
        } else {
          if (combo.invalidValues && combo.invalidValues.containsKey(value)) {
            if (metadata.css==null || metadata.css=='') {
              metadata.css = 'x-form-invalid';
            } else {
              metadata.css = metadata.css + ' x-form-invalid';
            }
            console.debug(value+' found invalid.');
          } else {
            console.debug(value+' not found.');
            // The following optimization will ensure that a separate AJAX call is not made by the finderCombo to validate the value.
            // This feature should only be applied when the combo is being initialized to a valid value.
            // It should not be applied when the user is editing the combo.
            if (!this.getEditor() || !this.getEditor().inEditor) {
              //'this' should be the column for the renderer, so use this.dataIndex to get record field data
              if (this.isColumn && this.dataIndex) {
                var recFld = record.fields.get(this.dataIndex); //record.fields.get(colIdx);
                if (recFld && typeof combo.findOrCreateRecord === 'function') {
                  var newRec = combo.findOrCreateRecord(record.json, recFld.mapping ? recFld.mapping : recFld.name, value);
                  if (newRec) {
                    combo.setValue(newRec);
                    return newRec.get(combo.displayField);
                  }
                }
              }
            }
            
            if (combo.editor && !combo.editor.record) {
            	// used at the initial grid load
              combo.editor.record = record;
              combo.setValue(value);
              delete combo.editor.record;
            } else {
              combo.setValue(value);
            }
          }
        }
      }
    }
    return value;
  };
}
