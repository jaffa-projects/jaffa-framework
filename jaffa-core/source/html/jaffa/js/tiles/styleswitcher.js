// Switch to named theme, and 
function setCurrentTheme(title) {
  var i, a, b, f=false;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
      if(a.getAttribute("rel").indexOf("alt") == -1) b=a;
      a.disabled = true;
      if(a.getAttribute("title") == title) {
         a.disabled = false;
         f=true;
    }
  } 
  } 
  if(!f && b!=null) { // If title not found use the default one.
    b.disabled = false;
    title = b.getAttribute("title");
  }
  setThemeCookie(title, 365);
}

function getCurrentTheme() {
  var i, a;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title") && !a.disabled) return a.getAttribute("title");
  }
  return null;  
}

function getDefaultTheme() {
  var i, a;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    //alert("Looking at style = " + a.getAttribute("rel") + " / " + a.getAttribute("title"));
    if(a.getAttribute("rel").indexOf("style") != -1 &&
       a.getAttribute("rel").indexOf("alt") == -1 &&
       a.getAttribute("title") != null)
      return a.getAttribute("title");
  }
  return null;
}

function setThemeCookie(value,days) {
  if (days) {
    var date = new Date();
    date.setTime(date.getTime()+(days*24*60*60*1000));
    var expires = "; expires="+date.toGMTString();
  }
  else expires = "";
  document.cookie = "theme="+value+expires+"; path=/";
}

function getThemeCookie() {
  var nameEQ = "theme=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ')
      c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) {
      return c.substring(nameEQ.length,c.length);
    }
  }
  return null;
}

/*
//This Method will swap all images written to a page to be CSS relative.
//Will be implemented at a later date.
function processThemeImages(title) {  
  var css_path = eval(title + '_path'); 
  for(i=0; (a = document.getElementsByTagName("img")[i]); i++) {
       var path_array=a.src.split("themes/");
       a.src = path_array[0] + css_path + path_array[1].substr(path_array[1].indexOf("/") , path_array[1].length);
  }
}
*/

window.onload = function(e) {
  var themeCookie = getThemeCookie();
  var themeName = themeCookie ? themeCookie : getDefaultTheme();
  setCurrentTheme(themeName);  
}

window.onunload = function(e) {
  setThemeCookie(getCurrentTheme(), 365);
}

var themeCookie = getThemeCookie();
var themeName = themeCookie ? themeCookie : getDefaultTheme();
setCurrentTheme(themeName);  


