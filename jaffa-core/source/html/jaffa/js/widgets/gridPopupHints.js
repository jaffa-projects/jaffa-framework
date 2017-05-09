/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

// resize fix for browser.ns4
var origWidth, origHeight;
if (browser.ns4) {
  origWidth = window.innerWidth; origHeight = window.innerHeight;
  window.onresize = function() { if (window.innerWidth != origWidth || window.innerHeight != origHeight) history.go(0); }
}

// avoid error of passing event object in older browsers
if (browser.nodyn) event = "nope";

///////////////////////  CUSTOMIZE HERE   ////////////////////
// settings for tooltip
// Do you want tip to move with mouse movement along link?
var tipFollowMouse  = true;
var tipWidth        = 240;
var offX            = 8;  // how far from mouse to show tip
var offY            = 12;
var tipPadding      = 4;

// The entries below are nolonger used for InternetExplorer, use CSS entries to control
// the look of the tip box (class=UserGridHint)
var tipFontFamily   = "Verdana, arial, helvetica, sans-serif";
var tipFontSize     = "8pt";
var tipFontColor    = "#000000";
var tipBgColor      = "#DDECFF";
var tipBorderColor  = "#000000";
var tipBorderWidth  = 1;
var tipBorderStyle  = "solid";

// preload images to be placed in tooltip
//var img1 = new Image(100,100);  // (width,height)
// img1.src = "your.gif";

// variables for tooltip content



////////////////////  END OF CUSTOMIZATION AREA  ///////////////////

////////////////////////////////////////////////////////////
//  initTip - initialization for tooltip.
//    Global variables for tooltip.
//    Set styles for all but ns4.
//    Set up mousemove capture if tipFollowMouse set true.
////////////////////////////////////////////////////////////
var tooltip=null, tipcss=null;
function initTip() {
  if (browser.nodyn) return;
  
  //tooltip = (browser.ns4)? document.tipDiv.document: (ie4)? document.all['tipDiv']: (ie5||ns5)? document.getElementById('tipDiv'): null;
  //Fix needed for Grids loaded by AJAX, the DIV seems to have messed up absolute positioning!
  if(tooltip==null) {
    //Delete existing DIV (@TODO should be refactored so GridTag does not write one)
    tooltip=document.getElementById("tipDiv");
    if(tooltip)
      tooltip.parentNode.removeChild(tooltip);
      
    // Create new DIV and add to end of document
    tooltip=document.createElement("DIV");
    tooltip.className="UserGridHint";
    tooltip.style.zIndex="100";
    tooltip.style.display="none";
    tooltip.style.position="absolute";
    tooltip.id="tipDiv"
    document.body.appendChild(tooltip);
  }  
  if(tooltip==null) return; // test as tooltips may be turned off!
  tipcss = (browser.ns4)? document.tipDiv: tooltip.style;
  if (browser.ie4||browser.ie5||browser.ns5) {  // browser.ns4 would lose all this on rewrites
    tipcss.width = tipWidth+"px";
  }
  if (tooltip&&tipFollowMouse) {
    if (browser.ns4) document.captureEvents(Event.MOUSEMOVE);
    document.onmousemove = trackMouse;
  }
}
window.onload = initTip;

/////////////////////////////////////////////////////////////
//  doTooltip function
//      Assembles content for tooltip and writes it to tipDiv.
//      Call positionTip function from here if tipFollowMouse
//      is set to false.
//////////////////////////////////////////////////////////////
var t1,t2;  // for setTimeouts
var tipOn = false;  // check if over tooltip link
function doTooltip(evt, cell) {
  initTip();
  if (!tooltip) return;
  var row = cell.parentNode;
  var usergrid = row.parentNode.parentNode.id;
  var colNames = document.getElementById(usergrid+"_colNames").value;
  var colIndexes = document.getElementById(usergrid+"_colIndexes").value;
  var keyLabel = document.getElementById(usergrid+"_keyLabel").value;
  var colLabel = document.getElementById(usergrid+"_colLabel").value;
  var hint = "<a class='title'>" + colLabel + "</a><br>" + row.parentNode.parentNode.rows[0].cells[cell.cellIndex].innerHTML + "<br>";
  if (colNames != "" && colIndexes != "") {
    hint = hint + "<a class='title'>" + keyLabel + "</a><br>";
    var colArray1=colNames.split("|");
    var colArray2=colIndexes.split("|");
    var colLoop=0;
    while (colLoop < colArray2.length) {
      hint = hint + "<a class='key'>"+colArray1[colLoop]+"</a>=<a class='value'>"+row.cells[colArray2[colLoop]-1].innerHTML + "</a><br>";
      colLoop++;
    }
  }
  if (t1) clearTimeout(t1);
  if (t2) clearTimeout(t2);
  tipOn = true;
  if (browser.ns4) {
    tip = '<table bgcolor="' + tipBorderColor + '" width="' + tipWidth + '" cellspacing="0" cellpadding="' + tipBorderWidth +
          '" border="0"><tr><td><table bgcolor="' + tipBgColor + '" width="100%" cellspacing="0" cellpadding="' + tipPadding +
          '" border="0"><tr><td><span style="font-family:' + tipFontFamily + '; font-size:' + tipFontSize + '; color:' + tipFontColor +
          ';">' + hint  + '</span></td></tr></table></td></tr></table>';
    tooltip.write(tip);
    tooltip.close();
  } else if (browser.ie4||browser.ie5||browser.ns5) {
    tip = hint;
    tooltip.innerHTML = tip;
  }
  positionTip(evt); // needed always for iframe resizing bug in IE. FF does not fire the event!
}

var mouseX, mouseY;
function trackMouse(evt) {
  mouseX = (browser.ns4||browser.ns5)? evt.pageX: window.event.clientX + document.body.scrollLeft;
  mouseY = (browser.ns4||browser.ns5)? evt.pageY: window.event.clientY + document.body.scrollTop;
  if (tipOn) positionTip(evt);
}

/////////////////////////////////////////////////////////////
//  positionTip function
//    If tipFollowMouse set false, so trackMouse function
//    not being used, get position of mouseover event.
//    Calculations use mouseover event position,
//    offset amounts and tooltip width to position
//    tooltip within window.
/////////////////////////////////////////////////////////////
function positionTip(evt) {
  if (!tipFollowMouse) {
    mouseX = (browser.ns4||browser.ns5)? evt.pageX: window.event.clientX + document.body.scrollLeft;
    mouseY = (browser.ns4||browser.ns5)? evt.pageY: window.event.clientY + document.body.scrollTop;
  }
  // tooltip width and height
  var tpWd = (browser.ns4)? tooltip.width: (browser.isIE4||browser.isIE5)? tooltip.clientWidth: tooltip.offsetWidth;
  var tpHt = (browser.ns4)? tooltip.height: (browser.isIE4||browser.isIE5)? tooltip.clientHeight: tooltip.offsetHeight;
  // document area in view (subtract scrollbar width for ns)
  var winWd = (browser.ns4||browser.ns5)? window.innerWidth-20+window.pageXOffset: document.body.clientWidth+document.body.scrollLeft;
  var winHt = (browser.ns4||browser.ns5)? window.innerHeight+window.pageYOffset: document.body.clientHeight+document.body.scrollTop;
  // check mouse position against tip and window dimensions
  // and position the tooltip
  if ((mouseX+offX+tpWd)>winWd-2)
    tipcss.left = (browser.ns4)? mouseX-(tpWd+offX): mouseX-(tpWd+offX)+"px";
  else
    tipcss.left = (browser.ns4)? mouseX+offX: mouseX+offX+"px";
  if ((mouseY+offY+tpHt)>winHt-2)
    tipcss.top = (browser.ns4)? mouseY-(tpHt+offY): mouseY-(tpHt+offY)+"px";
  else
    tipcss.top = (browser.ns4)? mouseY+offY: mouseY+offY+"px";
  t1=setTimeout("tipcss.display=''",300);
}

function hideTip() {
  if (!tooltip) return;
  t2=setTimeout("tipcss.display='none'",300);
  tipOn = false;
}
