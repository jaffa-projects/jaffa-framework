/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/


tableSortBrowserVar = function() {
  this.dom = (document.getElementsByTagName) ? true : false;
  this.ie5 = (document.getElementsByTagName && document.all) ? true : false;
  return this;
}();

var arrowUp, arrowDown;
var tbody=null;
var theadrow=null;
var colCount = null;
var reverse = false;
var lastclick = -1;                    // stores the object of our last used object
var arrHitTest = new Array();
var bDragMode = false;
var objDragItem;
var arrHitTest = new Array();
var childNodeList = new Array();
var iArrayHit = false;
var initialised = false;
var theadrow=null;

function doClick(e) {
  if(!tableSortBrowserVar.ie5) {
    doClickN(e)
  } else {
    if (e.srcElement.parentElement.parentElement.tagName == "THEAD") {
      var tbody= null;
      var colCount= null;
      if (!initialised)
        init(e);
      var clickObject = e.srcElement;

      while (clickObject.tagName != "TD") {
        clickObject = clickObject.parentElement;
      }

      // clear the sort images in the head
      var imgcol= theadrow.all('srtimg');
      for(var x = 0; x < imgcol.length; x++) {
        imgcol[x].src = "jaffa/imgs/table/blank.gif";
      }

      if(lastclick == clickObject.selectIndex) {
        if(reverse == false) {
          clickObject.children[0].src = "jaffa/imgs/table/down.gif";
          reverse = true;
        } else {
          clickObject.children[0].src = "jaffa/imgs/table/up.gif";
          reverse = false;
        }
      } else {
        reverse = false;
        lastclick = clickObject.selectIndex;
        clickObject.children[0].src = "jaffa/imgs/table/up.gif";
      }
      sortColumn(e);
    }
  }
}

function doClickN(e) {
  if (e.target.parentNode.parentNode.tagName == "TBODY" ||
      e.target.parentNode.parentNode.parentNode.tagName == "TBODY" ) {
  } else {
    var tbody= null;
    var colCount= null;
    if (!initialised)
      initN(e);
    var clickObject = e.target;
    while (clickObject.tagName != "TD") {
      clickObject = clickObject.parentNode;
    }
    // clear the sort images in the head
    var imgcol = theadrow.cells;
    for(var x = 0; x < imgcol.length; x++) {
      imgcol[x].lastChild.src= "jaffa/imgs/table/blank.gif";
    }

    if(lastclick == clickObject.selectIndex) {
      if(reverse == false) {
        clickObject.lastChild.src = "jaffa/imgs/table/down.gif";
        reverse = true;
      } else {
        clickObject.lastChild.src = "jaffa/imgs/table/up.gif";
        reverse = false;
      }
    } else {
      reverse = false;
      lastclick = clickObject.selectIndex;
      clickObject.lastChild.src= "jaffa/imgs/table/up.gif";
    }
    sortColumn(e);
  }
}


function init(e) {
  initialised = true;
  theadrow = e.srcElement.parentElement;
  if (theadrow.tagName != "TR") return;

  //theadrow.runtimeStyle.cursor = "hand";
  colCount = theadrow.children.length;
  var l, clickCell;
  var cx=0;
  var cy=0;
  var c;

  for (var i=0; i<colCount; i++) {
    // Create our blank gif
    l=document.createElement("IMG");
    l.src="jaffa/imgs/table/blank.gif";
    l.id="srtImg";
    l.width=13;
    l.height=7;

    clickCell = theadrow.children[i];
    clickCell.selectIndex = i;
    clickCell.width = clickCell.offsetWidth + 40;
    clickCell.insertAdjacentElement("beforeEnd", l);
  }
}

function initN(e) {
  initialised = true;
  theadrow = e.target.parentNode;
  //Check for Netscape as it differs slightly from Mozilla
  if (theadrow.tagName != "TR") {
    theadrow = theadrow.parentNode;
  }
  if (theadrow.tagName != "TR") return;

  //theadrow.runtimeStyle.cursor = "hand";
  colCount = theadrow.cells.length * 2;
  var l, clickCell;
  var cx=0;
  var cy=0;
  var c;

  for (var i=0; i<colCount; i++) {
    // Create our blank gif
    l=document.createElement("IMG");
    l.src="jaffa/imgs/table/blank.gif";
    l.id="srtImg";
    l.width=13;
    l.height=7;
    l.align="right";
    l.valign="top";
    childNodeList = theadrow.childNodes;
    clickCell = childNodeList[i];
    if (clickCell.tagName == "TD") {
      clickCell.selectIndex = i;
      clickCell.width = clickCell.offsetWidth + 25;
      clickCell.appendChild(l);
    }
  }
}

function sortTable(tableNode, nCol, bDesc, sType) {
    var tBody = tableNode.tBodies[0];
    var trs = tBody.rows;
    var a = new Array();
    oldstatus = window.status; 
    window.status = "Sorting Column .....";
    for (var i=0; i<trs.length; i++) {
        a[i] = trs[i];
    }
    a.sort(compareByColumn(nCol,bDesc,sType));
    for (var i=0; i<a.length; i++) {
        tBody.appendChild(a[i]);
    }
    window.status = oldstatus;
}

function CaseInsensitiveString(s) {
    return String(s).toUpperCase();
}

function parseDate(s) {
  if (s == " ") {
    return null;
  }
  return Date.parse(s.replace(/\-/g, '/'));
}

/* alternative to number function
 * This one is slower but can handle non numerical characters in
 * the string allow strings like the follow (as well as a lot more)
 * to be used:
 *    "1,000,000"
 *    "1 000 000"
 *    "100cm"
 */
function toNumber(s) {
  if (s == " ") {
    return null;
  }
  return Number(s.replace(/[^0-9\.]/g, ""));
}

function compareByColumn(nCol, bDescending, sType) {
  var c = nCol;
  var d = bDescending;
  var fTypeCast = String;

  if (sType == "Number")
    fTypeCast = toNumber;
  else if (sType == "Date")
    fTypeCast = parseDate;
  else if (sType == "CaseInsensitiveString")
    fTypeCast = CaseInsensitiveString;

  return function (n1, n2) {
    if (fTypeCast(getInnerText(n1.cells[c])) < fTypeCast(getInnerText(n2.cells[c])))
      return d ? -1 : +1;
    if (fTypeCast(getInnerText(n1.cells[c])) > fTypeCast(getInnerText(n2.cells[c])))
      return d ? +1 : -1;
    return 0;
  };
}


function sortColumn(e) {
  var tmp, el, tHeadParent;
  if (tableSortBrowserVar.ie5)
    tmp = e.srcElement;
  else if (tableSortBrowserVar.dom)
    tmp = e.target;

  tHeadParent = getParent(tmp, "THEAD");
  el = getParent(tmp, "TD");

  if (tHeadParent == null)
    return;

  if (el != null) {
    var p = el.parentNode;
    var i;

    if (el._descending)    // catch the null
      el._descending = false;
    else
      el._descending = true;

    // get the index of the td
    for (i=0; i<p.cells.length; i++) {
        if (p.cells[i] == el) break;
    }

    var table = getParent(el, "TABLE");
    // can't fail
    sortTable(table,i,el._descending, el.getAttribute("type"));
    for (r=1;r<table.rows.length;r++) {
      if ((r % 2) > 0) {
        table.rows[r].className = "sort";
      } else {
        table.rows[r].className = "altsort";
      }
    }
  }
}


function getInnerText(el) {
  if (tableSortBrowserVar.ie5) return el.innerText;    //Not needed but it is faster
  var str = "";
  for (var i=0; i<el.childNodes.length; i++) {
    switch (el.childNodes.item(i).nodeType) {
      case 1: //ELEMENT_NODE
        str += getInnerText(el.childNodes.item(i));
        break;
      case 3:    //TEXT_NODE
        str += el.childNodes.item(i).nodeValue;
        break;
    }
  }
  var pattern = / /g;

  str = str.replace(/[^ A-Za-z0-9`~!@#\$%\^&\*\(\)-_=\+\\\|\]\[\}\{'";:\?\/\.>,<]/g, "");
  str = str.replace(/'/g, "");
  str = str.replace(/ +/g, " ");
  str = str.replace(/^\s/g, "");
  str = str.replace(/\s$/g, "");
  return str;
}

function getParent(el, pTagName) {
  if (el == null) return null;
  else if (el.nodeType == 1 && el.tagName.toLowerCase() == pTagName.toLowerCase())    // Gecko bug, supposed to be uppercase
    return el;
  else
    return getParent(el.parentNode, pTagName);
}
