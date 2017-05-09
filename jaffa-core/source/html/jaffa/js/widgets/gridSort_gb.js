// Sort Date base on dd-mm-yy format
function parseDate(s) {
  if (s == " " || s==null || s=="") {
    return null;
  }
  theDay = s.substring(0,2);
  theMonth = s.substring(3,5);
  seperator = s.substring(2,3);
    stringLength = s.length;
/**  if (!ie5) {
    stringLength = s.length;
  } else {
    stringLength = s.length - 4;
  }
  removed to allow dates parsed correctd in IE for UK locale. */
  rest = s.substring(6,stringLength);
  s =  theMonth + seperator + theDay + seperator + rest;
  return Date.parse(s.replace(/\-/g, '/'));
}