/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//-----------------------------------------------------------
// Functions for managing popup menu and configuring the grid
//-----------------------------------------------------------


var highlightOnClass = "userGridSettingsHighlightOn";
var highlightOffClass = "sort";

//If the browser is below version 6 then process the stylesheet to determine what the on and off state color values are.
//The class names above are looked up for the 'TR' and the background color is extracted
//@todo-This values may need to be recalculated if dynamic css switching is enabled
var highlightOnColor = null;
var highlightOffColor = null;

function userGridInitHighlights() {
  // Loop through all enabled stylesheets
  for (i = 0; i < document.styleSheets.length; i++) 
    if(!document.styleSheets[i].disabled) { 
      for (j = 0; j < document.styleSheets[i].rules.length; j++) {
        if (highlightOnColor==null && document.styleSheets[i].rules.item(j).selectorText == "TR."+highlightOnClass) {
          highlightOnColor = document.styleSheets[i].rules.item(j).style.background;
          //alert("Found ON color " +highlightOnColor+" in sheet " + document.styleSheets[i].title);
        }
        if (highlightOffColor==null && document.styleSheets[i].rules.item(j).selectorText == "TR."+highlightOffClass) {
          highlightOffColor = document.styleSheets[i].rules.item(j).style.background;
          //alert("Found OFF color " +highlightOffColor+" in sheet " + document.styleSheets[i].title);
        }
        if (highlightOnColor!=null && highlightOffColor!=null) break;
      }
      // Loop through the imports
      for (k = 0; k < document.styleSheets[i].imports.length; k++) {
        for (j = 0; j < document.styleSheets[i].imports[k].rules.length; j++) {
          if (highlightOnColor==null && document.styleSheets[i].imports[k].rules.item(j).selectorText == "TR."+highlightOnClass) {
            highlightOnColor = document.styleSheets[i].imports[k].rules.item(j).style.background;
            //alert("Found ON color " +highlightOnColor+" in sheet " + document.styleSheets[i].title + "/" + document.styleSheets[i].imports[k].href);
          }
          if (highlightOffColor==null && document.styleSheets[i].imports[k].rules.item(j).selectorText == "TR."+highlightOffClass) {
            highlightOffColor = document.styleSheets[i].imports[k].rules.item(j).style.background;
            //alert("Found OFF color " +highlightOffColor+" in sheet " + document.styleSheets[i].title + "/" + document.styleSheets[i].imports[k].href);
          }
          if (highlightOnColor!=null && highlightOffColor!=null) break;
        }
        if (highlightOnColor!=null && highlightOffColor!=null) break;
      }
      if (highlightOnColor!=null && highlightOffColor!=null) break;
    } 
}
if (browser.isIE5_5 && (highlightOnColor==null || highlightOffColor==null) ) 
  userGridInitHighlights();
      


function userGrid_mainClick(e, field) {
  if(e) {
    if(e.button==0) {
      return doClick(e);
    }
    if (e.button==2) {
      userGrid_mainPopN(e, field);
    }
  } else {
    if (event.button==2) {
      userGrid_mainPop(field);
    }
    if (event.button==1) {
      doClick(event);
    }
    return false;
  }
  return false;
}


function userGrid_mainPop(field) {
  currX = window.event.clientX + document.body.scrollLeft;
  currY = window.event.clientY + document.body.scrollTop;
  menu = document.getElementById(field + '_mainMenu');
  if (menu.style.visibility=="visible") {
    userGrid_hideEl(menu);
  } else {
    userGrid_showEl(menu);
  }
  if (event.srcElement.tagName=="A") {
    userGrid_hideEl(menu);
  }
  menu.style.pixelLeft = currX - 50;
  menu.style.pixelTop = currY;
  menu.position='absolute';
  return(false);
}

function userGrid_mainPopN(e, field) {
  currX = e.layerX;
  currY = e.layerY;
  menu = document.getElementById(field + '_mainMenu');
  if(menu.style.visibility=="visible") {
    userGrid_hideEl(menu);
  } else {
    userGrid_showEl(menu);
  }
  if (e.tagName=="A") {
    userGrid_hideEl(menu);
  }
  var leftPixel = currX - 50;
  var topPixel = currY;
  menu.style.left = leftPixel + "px";
  menu.style.top = topPixel + "px";
  return(false);
}

function userGrid_mainSwap(field) {
  userGrid_hideEl( document.getElementById(field + '_main') );
  userGrid_showEl( document.getElementById(field + '_settings') );
  if (document.all) {
    userGrid_mainPop(field);
  } else {
    userGrid_hideEl( document.getElementById(field + '_mainMenu') );
  }
}

function userGrid_mainSwapBack(field) {
  userGrid_showEl( document.getElementById(field + '_main') );
  userGrid_hideEl( document.getElementById(field + '_settings') );
}

function userGrid_showEl(el) {
  el.style.display='block';
  el.style.visibility='visible';
}

function userGrid_hideEl(el) {
  el.style.display = 'none';
  el.style.visibility = 'hidden';
}


//Allows user to select a row.
//Optimized to work for IE6, but supports coded work around for IE 5.5 and under browsers
function selectRow(tableName, row, prefix) {
  elAvail = document.getElementById(prefix+'_availableColsValue');
  elSel = document.getElementById(prefix+'_selectedColsValue');
  if (!browser.isIE5_5) { // Modern Browser IE6.0,FF,...
    document.getElementById(row).className = highlightOnClass;
    if (elAvail.value != "" && elAvail.value != row)
      document.getElementById(elAvail.value).className = highlightOffClass;
    if (elSel.value != "" && elSel.value != row)
      document.getElementById(elSel.value).className = highlightOffClass;
  } else { // For IE_5_5 and lower  
    document.getElementById(row).style.background = highlightOnColor;
    if (elAvail.value != "" && elAvail.value != row)
      document.getElementById(elAvail.value).style.background = highlightOffColor;
    if (elSel.value != "" && elSel.value != row)
      document.getElementById(elSel.value).style.background = highlightOffColor;
  }
  if (tableName == (prefix + "_availableCols"))
    elAvail.value = row;
  else
    elSel.value = row;
}

//Allows user to double click on a row to fire event
function doubleClickEvent(tableName, prefix) {
  if (tableName.indexOf('selected') !=  - 1)
    removeRow(prefix);
  else
    addRow(prefix);
}

//Allows user to add a row from available to selected
function addRow(prefix) {
  elAvail = document.getElementById(prefix+'_availableColsValue');
  elSel = document.getElementById(prefix+'_selectedColsValue');
  if (elAvail.value != "") {
    var new_node = document.getElementById(elAvail.value).cloneNode(true);
    var elem = document.getElementById(elAvail.value);
    elem.parentNode.removeChild(elem);
    document.getElementById(prefix+'_selectedCols').getElementsByTagName("TBODY")[0].appendChild(new_node);
    selectRow(prefix + "_selectedCols", new_node.id, prefix);
    elAvail.value = "";
    elSel.value = new_node.id;
  }
}


//Allows user to remove a row from selected back to available
function removeRow(prefix) {
  elAvail = document.getElementById(prefix+'_availableColsValue');
  elSel = document.getElementById(prefix+'_selectedColsValue');
  if (elSel.value != "")  {
    var elem = document.getElementById(elSel.value);
    if('true'==elem.getAttribute('required')) {
      alert(Labels.get('label.Jaffa.Widgets.CalendarPopup.alertMsgGridConfigure'));
      return;
    }
    var new_node = elem.cloneNode(true);
    if (elem.parentNode.firstChild != elem.parentNode.lastChild) {
      elem.parentNode.removeChild(elem);
      document.getElementById(prefix + '_availableCols').getElementsByTagName("TBODY")[0].appendChild(new_node);
      selectRow(prefix+"_availableCols", new_node.id, prefix);
      elSel.value = "";
      elAvail.value = new_node.id;
    }
  }
}

//Allows user to move a selected row up
function moveRowUp(prefix) {
  elSel = document.getElementById(prefix+'_selectedColsValue');
  if (document.getElementById(prefix + '_selectedColsValue').value != "") {
    var new_node = document.getElementById(elSel.value).cloneNode(true);
    var elem = document.getElementById(elSel.value);
    var preSib = elem.previousSibling;
    while (preSib != null && preSib.tagName != "TR") {
      preSib = preSib.previousSibling;
    }
    if (preSib != null)
      elem.parentNode.insertBefore(elem, preSib);
  }
}

//Allows user to move a selected row down
function moveRowDown(prefix) {
  elSel = document.getElementById(prefix+'_selectedColsValue');
  if (document.getElementById(prefix + '_selectedColsValue').value != "") {
    var new_node = document.getElementById(elSel.value).cloneNode(true);
    var elem = document.getElementById(elSel.value);
    var postSib = elem.nextSibling;
    while (postSib != null && postSib.tagName != "TR") {
      postSib = postSib.nextSibling;
    }
    if (postSib != null)
      elem.parentNode.insertBefore(postSib, elem);
  }
}

//Builds an XML document for storing in hidden field for posting.
//Example:buildXML('j1_rows','catalogvendor_core_partFinderForm','j1',false,'false')
function buildXML(id, gridSettingsId, theFormName, restore, hints) {
  var xmlString = "<settings>";
  xmlString+="<gridid>"+gridSettingsId+"</gridid>";
  if(hints!=null && hints!='')
    xmlString+="<hints>"+hints+"</hints>";
  if(restore)
    xmlString+="<restore/>";
  else {
    selectedList = document.getElementById(id + '_selectedCols').getElementsByTagName("TBODY")[0].childNodes;
    if (selectedList != "")
      for (var i = 0; i < selectedList.length; i++)
        if (selectedList[i].tagName == "TR")
          xmlString+="<column name=\""+selectedList[i].getAttribute("name")+"\" width=\"\"></column>";
  }
  xmlString = xmlString + "</settings>";
  document.getElementById(id+"_colSettings").value = xmlString;
  //debug("form "+theFormName+" = "+document.getElementById(theFormName) + "\n"+xmlString);
  document.getElementById(theFormName).eventId.value = 'refresh';
  document.getElementById(theFormName).target='_self';
  preProcess(document.getElementById(theFormName), null);
  postForm(document.getElementById(theFormName), null);
  if (gridSorters[id]) delete gridSorters[id];
}
