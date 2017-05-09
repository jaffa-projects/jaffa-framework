/**
 * @author SeanZ
 */
/**
 * Private method to flatten a graph tree
 * @param {Ext.util.MixedCollection} mxCollect
 * @param {Object} root the graph
 * @param {String} chldName field name of the array of children
 * @param {String} keyName field name of the key of each child
 */
Jaffa.grid.GraphLoader = function(mxCollect, root, chldName, keyName){
  if (root[chldName]) {
    for (var i = 0; i < root[chldName].length; i++) {
      mxCollect.add(root[chldName][i].keyName, root[chldName][i]);
      if (root[chldName][i][chldName]) {
        Jaffa.grid.GraphLoader(mxCollect, root[chldName][i], chldName, keyName);
      }
    }
  }
}
