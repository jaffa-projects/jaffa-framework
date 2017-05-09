// Sort Date base on mm-dd-yy format
function parseDate(s) {
  if (s == " " || s==null || s=="") {
    return null;
  }
  return Date.parse(s.replace(/\-/g, '/'));
}
