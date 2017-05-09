/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 * 
 * Modified by Sean Zhou on July 21, 2008 to handle multiple grids on the page and grids that 
 * are loaded through ajax. Assumes only for IE 6 and above and Firefox 2.0 and above.
 * 
 *********************************************************************************************/

var gridSorters = new Array();

function doClick(e) {
  var clickObject = null;
  if (!browser.isIE) {
    clickObject = e.target;
  // doClickN(e)
  } else {
    clickObject = e.srcElement;
  }
  
  if (clickObject.parentNode.parentNode.tagName == "THEAD" &&
      clickObject.parentNode.parentNode.getAttribute("sorting") != 'false') {
    // obtain the initialized object
    var initObj = gridSorters[clickObject.parentNode.parentNode.parentNode.id];
    if (initObj == null) {
      // initialize the grid
      initObj = init(e, clickObject);
      gridSorters[clickObject.parentNode.parentNode.parentNode.id] = initObj;
    }

    while (clickObject.tagName != "TD") {
      clickObject = clickObject.parentNode;
    }

    // clear the sort images in the head
    var imgcol= browser.isIE ? initObj.theadrow.all('srtimg') : initObj.theadrow.cells;
    for(var x = 0; x < imgcol.length; x++) {
      imgcol[x].src = "jaffa/imgs/table/blank.gif";
    }
    
    var icon = null;
    if(initObj.lastclick == clickObject.selectIndex) {
      if(initObj.reverse == false) {
        icon = "jaffa/imgs/table/down.gif";
        initObj.reverse = true;
      } else {
        icon = "jaffa/imgs/table/up.gif";
        initObj.reverse = false;
      }
    } else {
      initObj.reverse = false;
      initObj.lastclick = clickObject.selectIndex;
      icon = "jaffa/imgs/table/up.gif";
    }
    clickObject.lastChild.src = icon;
    sortColumn(e);
  }
}

function init(e, clickObject) {
  var oo = {lastclick: -1, reverse: false};

  oo.theadrow = clickObject.parentNode;
  if (oo.theadrow.tagName != "TR") return null;

  for (var i=0; i<oo.theadrow.childNodes.length; i++) {
    // Create our blank gif
    var l=document.createElement("IMG");
    l.src="jaffa/imgs/table/blank.gif";
    l.id="srtImg";
    l.width=13;
    l.height=7;
    l.align="right";
    l.valign="top";
    var clickCell = oo.theadrow.childNodes[i];
    if (clickCell.tagName == "TD") {
      clickCell.selectIndex = i;
      clickCell.appendChild(l);
    }
  }
  return oo;
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
  if (s == " " || s==null || s=="") {
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

  if(sType == "HTML")
    return function (n1, n2) {
      if (fTypeCast(getInnerHTML(n1.cells[c])) < fTypeCast(getInnerHTML(n2.cells[c])))
        return d ? -1 : +1;
      if (fTypeCast(getInnerHTML(n1.cells[c])) > fTypeCast(getInnerHTML(n2.cells[c])))
        return d ? +1 : -1;
      return 0;
    };
  else
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
  if (browser.isIE)
    tmp = e.srcElement;
  else 
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
  if (browser.isIE) return el.innerText;    //Not needed but it is faster
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
