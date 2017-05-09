Ext.ns("Ext.ux.renderer");

/** @class Ext.ux.renderer */

/** @static
 * @method ComboRenderer
 * provides a renderer for a column that is based on an editable combo box
 * such that we should render the equivilent of the 'displayField' from the combox box
 */
Ext.ux.renderer.ComboRenderer = function(options) {
    var value = options.value;
    var combo = options.combo;

    var returnValue = value;
    var valueField = combo.valueField;
        
    var idx = combo.store.findBy(function(record) {
        if(record.get(valueField) == value) {
            returnValue = record.get(combo.displayField);
            return true;
        }
    });
        
    return returnValue;
};

/** @static
 * @method ComboRenderer
 * provides a renderer for a column that is based on an editable combo box
 * such that we should render the equivilent of the 'displayField' from the combox box
 */
Ext.ux.renderer.Combo = function(combo) {
    return function(value, meta, record) {
        return Ext.ux.renderer.ComboRenderer({value: value, meta: meta, record: record, combo: combo});
    };
};

/** @static
 * @method getDefaultLayout
 * Return the default 'layout' that should be used based on a certain fields 'type', where 'type' 
 * is typically provided by classMetaData.jsp, and will be one of the following values
 * <code>dateonly, datetime, float, int, boolean, currency</code>
 *
 * By default for rendered we convert the 'type' into a 'layout' and then lookup the renderer based on 'layout'
 * This is an alternate approach to get the correct renderer then the ExtJS default of specifying an 'xtype' and
 * 'format' on the column. The 'xtype' will take presedence if provided.
 */
Ext.ux.renderer.getDefaultLayout = function(dataType) {
    if(dataType=='monthyear')
        return '[monthyear.format]';
    else if(dataType=='dateonly' || dataType=='date')
        return '[dateonly.format]';
    else if(dataType=='datetime')
        return '[datetime.format]';
    else if(dataType=='float'||dataType=='number')
        return '[decimal.format]';
    else if(dataType=='boolean'||dataType=='bool')
        return '[boolean.format]';
    else if(dataType=='int')
        return '[integer.format]';
    else if(dataType=='currency')
        return '[currency.format]';
    else
        return undefined;
}


/** @static
 * @property Renderers
 * This is an array of renderers indexed by 'layout' that provide backwards compatability with the java layouts
 * used in Jaffa and those provided via the <layout> AOP rule in ClassMetaData
 */
Ext.ux.renderer.Renderers = [];

/** 'number' based formatters */
Ext.ux.renderer.Renderers['[hh:mm]'] = Jaffa.grid.HourMinuteRenderer();

Ext.ux.renderer.Renderers['[decimal.format]'] = Ext.util.Format.numberRenderer('0,000.00');
Ext.ux.renderer.Renderers['[decimalOptional.format]'] = Ext.util.Format.numberRenderer('0,000.00'); // Not really supported with optional decimals!
Ext.ux.renderer.Renderers['[0.00]'] = Ext.util.Format.numberRenderer('0.00');

Ext.ux.renderer.Renderers['[decimal1.format]'] = Ext.util.Format.numberRenderer('0,000.0');
Ext.ux.renderer.Renderers['#,##0.0'] = Ext.util.Format.numberRenderer('0');

Ext.ux.renderer.Renderers['[decimal2NoSeperator.format]'] = Ext.util.Format.numberRenderer('0.00');
Ext.ux.renderer.Renderers['########0.00'] = Ext.ux.renderer.Renderers['[decimal2NoSeperator.format]']
Ext.ux.renderer.Renderers['######.00'] = Ext.ux.renderer.Renderers['[decimal2NoSeperator.format]']

Ext.ux.renderer.Renderers['[decimal3.format]'] = Ext.util.Format.numberRenderer('0,000.000');
Ext.ux.renderer.Renderers['#0.000'] = Ext.ux.renderer.Renderers['[decimal3.format]']
Ext.ux.renderer.Renderers['#,##0.000'] = Ext.ux.renderer.Renderers['[decimal3.format]']

Ext.ux.renderer.Renderers['[decimal5.format]'] = Ext.util.Format.numberRenderer('0,000.00000'); // Not really supported with optional decimals

Ext.ux.renderer.Renderers['[integerNoSeperator.format]'] = Ext.util.Format.numberRenderer('0');
Ext.ux.renderer.Renderers['########'] = Ext.ux.renderer.Renderers['[integerNoSeperator.format]']
Ext.ux.renderer.Renderers['#######'] = Ext.ux.renderer.Renderers['[integerNoSeperator.format]']
Ext.ux.renderer.Renderers['######'] = Ext.ux.renderer.Renderers['[integerNoSeperator.format]']
Ext.ux.renderer.Renderers['####'] = Ext.ux.renderer.Renderers['[integerNoSeperator.format]']

Ext.ux.renderer.Renderers['[integer.format]'] = Ext.util.Format.numberRenderer('0,000');
Ext.ux.renderer.Renderers['#,##0'] = Ext.ux.renderer.Renderers['[integer.format]']

Ext.ux.renderer.Renderers['[currency.format]'] = Ext.util.Format.money;

/** 'boolean' based formatters */
Ext.ux.renderer.Renderers['[boolean.format]'] = Ext.ux.form.CheckBoxRenderer('check');
Ext.ux.renderer.Renderers['[boolean_yn]'] = Ext.ux.form.CheckBoxRenderer('check');

/** 'date' based formatters */
Ext.ux.renderer.Renderers['[dateonly.format]'] = Ext.util.Format.dateRenderer();
Ext.ux.renderer.Renderers['[monthyear.format]'] = Ext.util.Format.dateRenderer('M/Y');
Ext.ux.renderer.Renderers['[dateonly.shortYear.format]'] = Ext.ux.renderer.Renderers['[dateonly.format]']; // Not Really supported!
Ext.ux.renderer.Renderers['[datetime.format]'] = Ext.util.Format.dateTimeRenderer();

Ext.ux.renderer.Renderers['[timeonly.format]'] = Ext.util.Format.dateTimeRenderer('H:i:s'); // Hours in 24 hr clock, leading zero
Ext.ux.renderer.Renderers['HH:mm'] = Ext.util.Format.dateRenderer('H:i'); // Hours in 24 hr clock, leading zero
Ext.ux.renderer.Renderers['H:mm'] = Ext.util.Format.dateRenderer('G:i'); // Hours in 24 hr clock, no leading zero
Ext.ux.renderer.Renderers['KK:mm a'] = Ext.util.Format.dateRenderer('h:i A'); // Hours in 12 hr clock, leading zero
Ext.ux.renderer.Renderers['K:mm a'] = Ext.util.Format.dateRenderer('g:i A'); // Hours in 12 hr clock, no leading zero
Ext.ux.renderer.Renderers['[dateonly.julian5]'] = Ext.util.Format.dateRenderer('yJ'); //5 digit Julian Date, two diget year followed by 3 digit day of year 



