/**
 * A function to filtering out the columns based on a template.
 *
 * @author Sean Zhou, Jan. 2013 
 */
Jaffa.util.GridColumnFilter = function(columnArray, cln2retain) {
  var clnm = [];
  for (var i=0; i<columnArray.length; i++) {
    if (typeof columnArray[i] == 'string') {
      if (cln2retain.indexOf(columnArray[i]) >= 0) clnm.push(columnArray[i]);
    } else if (!columnArray[i].dataIndex || cln2retain.indexOf(columnArray[i].dataIndex) >= 0) {
      clnm.push(columnArray[i]);
    } 
  }
  return clnm;
}
