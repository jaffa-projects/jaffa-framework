/**
 * @class Jaffa.tree
 * @method @static
 * @author SeanZ
 *
 * This can be used as a renderer in a ColumnModel 
 * @param {Object} d the value from the column of the node being rendered
 * @param {Ext.tree.TreeNode} n the node being rendered
 * @param {Object} a 
 */
Jaffa.tree.moneyRenderer = function(d, n, a) {
	var v = parseFloat(d);
	if (isNaN(v)) {
		if (n.attributes.isFolder == true) {
			return '';
		} else {
			v = 0;
		}
	}
	return Ext.util.Format.money(v);
};
