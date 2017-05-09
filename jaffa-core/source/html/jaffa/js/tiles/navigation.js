<!--
/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 * 
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/
//////////////////////////////////////////////////////
// File:   navigation.js
// Author: PaulE
// Version 1.0 @ 26-Nov-03
// Version 1.1 @ 08-Oct-04 - Fixed for IE/Firefox use with <!DOCTYPE loose.dtd>
//////////////////////////////////////////////////////
var navMenuHistory=new Array();

//---http://www.webreference.com/dhtml/diner/realpos1/7.html
function getElementLeft(e) {
  var nLeftPos = e.offsetLeft;
  var ePar = e.offsetParent;
  while (ePar != null) {
    nLeftPos += ePar.offsetLeft;
    ePar = ePar.offsetParent;
  }
  return nLeftPos;
}
function getElementTop(e) {
  var nTopPos = e.offsetTop;
  var ePar = e.offsetParent;
  while (ePar != null) {
    nTopPos += ePar.offsetTop;
    ePar = ePar.offsetParent;
  }
  return nTopPos;
}
//---http://www.webreference.com/dhtml/diner/realpos1/7.html
function displayMenu(ev, id) {
  histClear(0);
  var el = document.getElementById(id);
  if(el!=null) {
    t = ev.target;
    if(t==null) t=ev.srcElement;
    alignArrows(el);
    histAdd(id);
    if(browser.isIE && !browser.isIE7up) try { document.getElementById("jaffaDropDown").disabled = false;} catch(e) {}
    el.style.top= ( getElementTop(t) + t.offsetHeight ) + "px";
    el.style.left=getElementLeft(t) + "px";
    el.style.visibility="visible";
    el.style.zindex=parseInt(t.style.zindex)+1;
  }
}
function displaySubMenu(ev, id) {
  var el = document.getElementById(id);
  if(el!=null) {
    t = ev.target;
    if(t==null) t=ev.srcElement;
    if(t.tagName!="A") return -1;
    histAdd(getDivId(t));
    histAdd(id);
    alignArrows(el);
    el.style.top=getElementTop(t) + "px"; // + t.parentNode.offsetHeight;
    el.style.left=(getElementLeft(t.parentNode) + t.parentNode.offsetWidth) + "px";
    el.style.visibility="visible";
  }
}
function alignArrows(el) {
  // loop through inner span.menuArrows, and set width
  var w = el.offsetWidth;
  var l = el.getElementsByTagName("A");
  var d = false;
  for(i = 0; i < l.length && !d; i++) {
    var ancor = l.item(i);
    var l2 = ancor.getElementsByTagName("SPAN");
    var cw = 0;
    for(j = 0; j<l2.length && !d; j++) {
      span = l2.item(j);
      if(span.className=="navMenuText") cw = span.offsetWidth;
      if(span.className=="navMenuMore") {
        if(span.style.paddingLeft == "")
          span.style.paddingLeft = w - cw;
        else
          d=true;
      }
    }
  }
}

function hideSubMenu(e) {
  if(e!=null) {
    var t = e.target;
    if(t==null) t=e.srcElement;
    if(t.tagName=="A") {
      // If a submenu is being displayed, hide it
      histAdd(getDivId(t));
    }
  }
}
function getDivId(e) {
  while(e!=null) {
    if(e.tagName=="DIV")
      return e.id;
    e=e.parentNode;
  }
  return null;
}

function histLength() {
  var i=navMenuHistory.length;
  while(--i>=0 &&!navMenuHistory[i]);
  return ++i;
}
function histAdd(name) {
  if(!name) return;
  // See if name is already here...
  var l = histLength(), i;
  for(i=0;i<l;i++) {
    if(navMenuHistory[i]==name) {
      // clear remaining entries
      if(i+1<l) histClear(i+1);
      break;
    }
  }
  if(i==l)
    navMenuHistory[i]=name;
}
function histClear(i) {
  for(;i<navMenuHistory.length;i++) {
    var id=navMenuHistory[i];
    navMenuHistory[i]=null;
    if(id) {
      var el=document.getElementById(id);
      if(el!=null) el.style.visibility="hidden";
    }
  }
}
// Hide all layers, unless on a navMenu element
function globalClick(e) {

  if(e!=null) {
    var t = e.target;
    if(t==null) t=e.srcElement;
    if(t.className!=null &&  (t.className.indexOf("navMenu") == 0  || t.className.indexOf("navBar") == 0)) { 
    return;
    }
  }
  var l = getElementsByClassName(document,"div", "navMenu");
  for(var i=0;i<l.length;i++)
     l[i].style.visibility="hidden";
  
  // Unhide all the dropdowns in IE
  if (browser.isIE) {
    try {
      // Do not unhide dropdowns if a Calendar has been rendered
      var objs = getElementsByClassName(document,"div","CalendarWidget");
      if (objs != null && objs.length > 0)
        return;
      if (!browser.isIE7up)
        document.getElementById("jaffaDropDown").disabled = true;
    } catch(e) {
    }
  }
}
// Browser independent Event Registration
function addEvent(objObject, strEventName, fnHandler) {
 if (objObject.addEventListener) // DOM-compliant
   objObject.addEventListener(strEventName, fnHandler, false);
 else if (objObject.attachEvent) // IE/windows
   objObject.attachEvent("on" + strEventName, fnHandler);
}
// Register mouse click to close all menus
addEvent(document, "click", globalClick);

//-->
