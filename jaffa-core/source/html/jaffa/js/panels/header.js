/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//////////////////////////////////////
// PageHeader.js - Page Header Java Script
// By Paul Extance
//
// Should be included at the top of the Outer Page.
// In the case of PageLayouts and Panels, this should be
// included once in the panel.
//
// The WebWidgets rely on functions in this file
//
// Version: 1.0a
// Modified: 28-Jun-2001
////////////////////////////////////////////////////
// Modified the postForm function to support packing
// data for lazy widgets, and to build extra form fields
// into an XML document and post as a hidden field
//
// Version: 1.1
// Modified: 1-Aug-2001
////////////////////////////////////////////////////


// *** BROWSER DETECTION CODE
function Browser() {
  // TODO: refactor this browser detection mechanism using http://www.quirksmode.org/js/detect.html
  // Note: On IE5, these return 4, so use is_ie5up to detect IE5.
  var agt = navigator.userAgent.toLowerCase();

  //------ from usergridsettings.js
  var is_major = parseInt(navigator.appVersion);
  this.version = parseFloat(navigator.appVersion);
  this.isIE = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));
  var is_ie3 = (this.isIE && (is_major < 4));
  this.isIE4 = (this.isIE && (is_major == 4) && (agt.indexOf("msie 4") !=  - 1));
  var is_ie4up = (this.isIE && (is_major >= 4));
  this.isIE5 = (this.isIE && (is_major == 4) && (agt.indexOf("msie 5.0") !=  - 1));
  this.isIE5_5 = (this.isIE && (is_major == 4) && (agt.indexOf("msie 5.5") !=  - 1));
  this.isIE5up = (this.isIE && !is_ie3 && !this.isIE4);
  var is_ie5_5up = (this.isIE && !is_ie3 && !this.isIE4 && !this.isIE5);
  var is_ie6 = (this.isIE && (is_major == 4) && (agt.indexOf("msie 6.") !=  - 1));
  var is_ie6up = (this.isIE && !is_ie3 && !this.isIE4 && !this.isIE5 && !this.isIE5_5);
  var is_ie7 = (this.isIE && (agt.indexOf("msie 7.") !=  - 1));
  var is_ie8 = (this.isIE && (agt.indexOf("msie 8.") !=  - 1));
  this.isIE7up = is_ie7 || is_ie8;
  
  //this.isNS = (document.getElementsByTagName) ? true : false;
  this.isNS = !this.isIE; // a hack to solve bug 11157 in which this.isNS==true under IE6
  if ((i = navigator.userAgent.indexOf("Firefox/")) >= 0) {
    this.isNS = true;
  }
  if ((i = navigator.userAgent.indexOf("Netscape6/")) >= 0) {
    this.isNS = true;
  }
  if ((i = navigator.userAgent.indexOf("Gecko")) >= 0) {
    // Treat any other "Gecko" browser as NS 6.1.
    this.isNS = true;
    this.version = 6.1;
  }

  //------ from usergridtooltip.js
  var dom = (document.getElementById) ? true : false;
  this.ns5 = ((navigator.userAgent.indexOf("Gecko") > -1) && dom) ? true: false;
  this.ie5 = ((navigator.userAgent.indexOf("MSIE") > -1) && dom) ? true : false;
  this.ns4 = (document.layers && !dom) ? true : false;
  this.ie4 = (document.all && !dom) ? true : false;
  this.nodyn = (!this.ns5 && !this.ns4 && !this.ie4 && !this.ie5) ? true : false;
  //------ from navigation.js
}
var browser = new Browser();

//**END OF BROWSER DETECTION CODE
var globalConfig={};

// This variable will be set to true after a POST has been invoked. It'll then serve as a flag to avoid un-intentional double-clicks
var postDone = false;

// Variable to keep track of all the folding sections on the page
var foldingSectionList = new Array();

function treeState() {

  //Variables and Methods Registration
  this.expandedLevels = new Array();

  this.removeExpanded = removeExpanded;
  this.addExpanded = addExpanded;
  this.setCallBack = setCallBack;
  this.getCallBack = getCallBack;

  function clearArray() {
    this.expandedLevels = new Array();
  }

  function addExpanded(nodeId) {
    if (this.expandedLevels[nodeId] == null)  {
      this.expandedLevels[nodeId] = this.expandedLevels.length;
      this.expandedLevels[this.expandedLevels.length] = nodeId;
    }
  }

  function removeExpanded(nodeId) {
    if(this.expandedLevels.length > 0) {
      tempArray = new Array();
      for(i=0; i < this.expandedLevels.length ; i++) {
        if(nodeId != this.expandedLevels[i]) {
          tempArray[tempArray.length] = this.expandedLevels[i];
          tempArray[this.expandedLevels[i]] = tempArray.length;
        }
      }
      this.expandedLevels = null;
      this.expandedLevels = tempArray;
    }
  }

  function setCallBack(nodeId) {
    this.expandedLevels["callBack"] = nodeId;
  }

  function getCallBack() {
    return this.expandedLevels["callBack"];
  }


}

currentState = new treeState();

// Common insert adjacent code for use in 2 loactions.
if(typeof HTMLElement!="undefined" && ! HTMLElement.prototype.insertAdjacentElement) {
  HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode) {
    switch (where) {
    case 'beforeBegin':
    case 'BeforeBegin':
      this.parentNode.insertBefore(parsedNode,this)
      break;
    case 'afterBegin':
    case 'AfterBegin':
      this.insertBefore(parsedNode,this.firstChild);
      break;
    case 'beforeEnd':
    case 'BeforeEnd':
      this.appendChild(parsedNode);
      break;
    case 'afterEnd':
    case 'AfterEnd':
      if (this.nextSibling)
        this.parentNode.insertBefore(parsedNode,this.nextSibling);
      else
        this.parentNode.appendChild(parsedNode);
      break;
    }
  }

  HTMLElement.prototype.insertAdjacentHTML = function(where,htmlStr) {
    var r = this.ownerDocument.createRange();
    r.setStartBefore(this);
    var parsedHTML = r.createContextualFragment(htmlStr);
    this.insertAdjacentElement(where,parsedHTML)
  }


  HTMLElement.prototype.insertAdjacentText = function(where,txtStr) {
    var parsedText = document.createTextNode(txtStr)
    this.insertAdjacentElement(where,parsedText)
  }
}


/*
    Written by Jonathan Snook, http://www.snook.ca/jonathan
    Add-ons by Robert Nyman, http://www.robertnyman.com
*/
function getElementsByClassName(oElm, strTagName, strClassName) {
  var arrElements = (strTagName == "*" && document.all) ? document.all : oElm.getElementsByTagName(strTagName);
  var arrReturnElements = new Array();
  strClassName = strClassName.replace(/\-/g, "\\-");
  var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");
  var oElement;
  for (var i = 0; i < arrElements.length; i++){
    oElement = arrElements[i];
    if(oRegExp.test(oElement.className)){
      arrReturnElements.push(oElement);
    }
  }
  return (arrReturnElements)
}


function findPosX(obj) {
  var curleft = 0;
  if (obj.offsetParent) {
    while (obj.offsetParent) {
	
      curleft += obj.offsetLeft
      obj = obj.offsetParent;
    }
  } else if (obj.x)
    curleft += obj.x;
  return curleft;
}
/* added for RTL changes of mandatory error box */
function findPosXRTL(obj){
	var curright = 0;
	curright = document.body.offsetWidth - findPosX(obj);
	return curright;
}

function findPosY(obj) {
  var curtop = 0;
  if (obj.offsetParent) {
    while (obj.offsetParent) {
      curtop += obj.offsetTop
      obj = obj.offsetParent;
    }
  } else if (obj.y)
    curtop += obj.y;
  return curtop;
}

// Get any named cookie, Returns null if cookie not found
function getCookie(name) {
  var data;
  if (document.cookie) {
    index=document.cookie.indexOf(name+"=");
    if (index != -1) {
      namestart=(document.cookie.indexOf("=",index)+1);
      nameend=document.cookie.indexOf(";",index);
      if (nameend==-1) {
        nameend=document.cookie.length;
      }
      data=unescape(document.cookie.substring(namestart,nameend));
      return data;
    }
  }
  return null;
}


// Find the next field to prompt to.....
// The next field is either the next higher anywhere
// Or one with the same index later on.
function nextField(field) {
  //new code that actually works
  for(var d = 0; d < document.forms.length; d++) 
    for(var e = 0; e < document.forms[d].elements.length; e++) 
      if (e != document.forms[d].elements.length - 1) 
        if (document.forms[d].elements[e] == field)
          return document.forms[d].elements[e+1];
  return null;
}


// Common routine used to post the form.
// This should always be used, NEVER use the form.submit() directly!
// The field is optional, but if specified it will go to the next field on return
function preProcess(frm, fld) {
  debug("Posting Form...");
  var xml = "";
  postProcess(frm, fld);
  for (var i=0; i<formsWithWidgets.length; i++) {
    var formNode = formsWithWidgets[i];
    var formObj = formNode.element;
    var cf = (formObj == frm);
    debug("Processing Form :" + formObj.name + " " + cf);
    if(!cf) {
      xml += "<form name=\""+ formObj.name + "\">\n";
    }
    if (formNode.children != null && formNode.children.length > 0) {
      for (var j=0; j<formNode.children.length; j++) {
        var el = formNode.children[j].element;
        if (el.className == "WidgetTableTree") 
          tableTreePack(formNode.children[j]);
        else if (el.className == "WidgetGrid") 
          gridPack(formNode.children[j]);
        else if (el.className == "WidgetUserGrid") 
          userGridPack(formNode.children[j]);
        else if (el.className == "WidgetTree")
          treePack(formNode.children[j]);
        else {
          debug("Widget: " + el.name + " = " + el.value);
          // This is an active widget, encode if needed
          if(!cf) {
            // if this is not the form being posted make the active widgets data XML friendly
            el.value = toXML(el.value);
          }
        }
        // if this is not the form being posted then write to XML Buffer
        if(!cf && (el.className != "RadioButton" || (el.className == "RadioButton" && el.checked))) {
            xml += "<widget name=\""+ el.name + "\">" + el.value + "</widget>\n";
            debug("XML Now is\n" + xml);
        }
      }
    }
    if(!cf) {
      xml += "</form>\n";
    }
  }
  
  // Incase there is no hidden field, ignore this...
  try { frm.extraContext.value = xml; } catch (e) {}

  var curr = "";
  if(fld != null) curr = fld.id;
  document.cookie = "focus=" + curr;
}

function startProgressBar(formName) {
  try {
    if (document.getElementById(formName).target != "_blank") {
      progress_clear() ;
      eval("if (document.getElementById('" + formName + "_EntirePage') != null) {document.getElementById('" + formName + "_EntirePage').style.display = 'none';}");
      eval("if (document.getElementById('" + formName + "_PushPage') != null) {document.getElementById('" + formName + "_PushPage').style.display = 'block';}");
      progressTimer = setTimeout('progress_update()',progressInterval);
    }
  } catch (e) { }
  return true;
}

var jaffaAjaxEventList=[];
function postForm(frm, validate) {
  var fwd=null, pane=null;
  var ajaxEvent=false;
 
  // lookup ajaxEvent
  if (jaffaAjaxEventList[frm.id]) {
    var aes = jaffaAjaxEventList[frm.id];
    for (var i=0; i<aes.length && !ajaxEvent; i++) {
      var rlist=aes[i].fields;
      for (var j=0; j<rlist.length && !ajaxEvent; j++) {
        var regex = new RegExp(rlist[j]);
        if (regex.test(frm.eventId.value)) {
          fwd = aes[i].dataForward;
          pane = document.getElementById(aes[i].containerId);
          ajaxEvent=true;
        }  
      }
    }
  }
  
  if (ajaxEvent) {
    sendAjaxRequest(pane, null, frm, fwd);
  } else {
    // Bail out if the post has already been done. This is to handle un-intentional double-clicks
    if (postDone) {
      debug("Page is being submitted. Have patience");
      return;
    }

    //Make sure the form has actually finished loading!
    if(document.getElementById('dateTimeStamp')==null) {
      alert(Labels.get('label.Jaffa.Panels.alertHeaderMsg.FormNotLoaded'));
      return;
    }
    if (validate) {
      if (validate && validateForm(frm.id)) {
        frm.submit();
        if (frm.target == null || frm.target == "" || frm.target == "_self") postDone = true;
      }
    } else {
       frm.submit();
       if (frm.target == null || frm.target == "" || frm.target == "_self") postDone = true;
    }
  }
}

// Convert a string in to an XML Friendly String
// Used by various widgets for packing data
function toXML(txt) {
  if(txt==null) return txt;
  var regexp = /&/g
  var s = txt.replace(regexp, "&amp;");
  regexp = /</g
  s = s.replace(regexp, "&lt;");
  regexp = />/g
  s = s.replace(regexp, "&gt;");
  regexp = /'/g
  s = s.replace(regexp, "&apos;");
  regexp = /"/g
  s = s.replace(regexp, "&quot;");
  regexp = /#/g
  s = s.replace(regexp, "%23");
  return s;
}


// Control Debug Messages...
function debug(msg) {
  // Uncomment for messages...
  //alert(msg);
  // If using firebug for development this is a better option...
  //console.log(msg)
}


// Variable to hold Error Messages
var messageList = new Array();
var index = 0;

// Display Error Messages
function showMessages(i) {
  if (i < messageList.length)
    document.getElementById("msg").innerText = messageList[i];
}

function previous() {
  if (index != 0) {
    index--;
    showMessages(index);
    if (document.getElementById("calendardd") != null)
      document.getElementById("calendardd").disabled = false;
  }
}

function next() {
  if (index < messageList.length - 1) {
    index++;
    showMessages(index);
    if (document.getElementById("calendardd") != null)
        document.getElementById("calendardd").disabled = false;
  }
}

function okerror() {
  document.all.errorBox.style.display = "none" ;
  if (document.getElementById("calendardd") != null)
    document.getElementById("calendardd").disabled = true;
}

// Add an Error Message to the variable
function addMessage(ermsg) {
  messageList[messageList.length] = ermsg;
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  
  //'x' sometimes contains a NodeList. The following will obtain the 1st element from that list
  if (x.length > 0)
    x = x[0];
  return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


//Toggle collapsable section and update session cookie 
function expand(spanId,imageName,expandOnly,bypass,setcookie) {
  pathname = location.pathname;
  myDomain = pathname.substring(0,pathname.lastIndexOf('/')) +'/';
  if (expandOnly == null) {
    if (document.getElementById(spanId).style.display == "none") {
      MM_swapImage(imageName,'','jaffa/imgs/foldingsection/arrowminimize.gif',1);
      document.getElementById(spanId).style.display = "block";
      if(setcookie!=false) setExpanded(spanId , "O");
    } else {
      MM_swapImage(imageName,'','jaffa/imgs/foldingsection/arrowexpand.gif',1);
      document.getElementById(spanId).style.display = "none";
      if(setcookie!=false) setExpanded(spanId , "C");
    }
  } else {
    MM_swapImage(imageName,'','jaffa/imgs/foldingsection/arrowminimize.gif',1);
    window.location.hash = spanId + "section";
    document.getElementById(spanId).style.display = "block";
    if(setcookie!=false) setExpanded(spanId , "O");
  }
  if (bypass == true) {
  } else {
    if (validationRules.valid == false) {
      validationRules.validatePage();
    }
  }
}

// initialize a closed section
function initialExpand(spanId,imageName) {
  if (isExpanded(spanId)!="O") {
    expand(spanId,imageName,null,true,false);
  }
}

// initialize a open section
function setDefaultState(spanId,imageName) {
  if(isExpanded(spanId)=="C") {
    expand(spanId,imageName,null,true,false);
  }
}

// Ensures that the user input does not contain unexpected semicolon ';' or equals signs '='
function sanitizeName(name) {
  if(name!= null){
    var truncationIndex = name.indexOf(";");
    var equalIndex = name.indexOf("=");
    // Cut away anything that follows ";" or "=", whichever appears first,
    // to help avoid potential security risks
    if ((truncationIndex < 0) ||
      (equalIndex >= 0 && equalIndex < truncationIndex)) {
      truncationIndex = equalIndex;
    }
    if (truncationIndex >= 0) {
      name = name.substring(0, truncationIndex);
    }
  }
  return name;
}

// Only store collapsed sections. Expanded should be "O"pen or "C"losed
function setExpanded(name, expanded) {
  var secs = sanitizeSection(sanitizeName(name), expanded);
  saveExpanded(secs);
}

function sanitizeSection(name, expanded) {
  var secs = getCookie("Sections");
  secs = sanitizeName(secs);
  if(secs == null || secs == "") secs = "|";
  var i=secs.indexOf("|"+name+":");
  if(i>=0) secs = secs.substring(0,i) + secs.substring(i+name.length+3);
  secs="|"+name+":"+expanded+secs;
  return secs;
}

// See if a section is expanded and move cookie to top of the list
// returns "" if there is no cookie,"O" it it should be open or "C" it it should be closed
function isExpanded(name) {
  name = sanitizeName(name);
  return sanitizeSectionExpanded(name);
}
function sanitizeSectionExpanded(name) {
  var secs = getCookie("Sections");
  secs = sanitizeName(secs);
  if(secs == null||secs == "") secs = "|";
    var i=secs.indexOf("|"+name+":");
    if(i<0)
      return "";
      else {
        var value=secs.substring(i+name.length+2,i+name.length+3);
        secs = "|" + name + ":" + value + secs.substring(0,i) + secs.substring(i+name.length+3);
        saveExpanded(secs);
        return value;
      }
}

// Save cookie, and truncate if too big
function saveExpanded(secs) {
  // find the application path
  var appPath = location.pathname;
  if (appPath.substring(0,1)=='/') {
    appPath = appPath.substring(1, appPath.length+1);
  }
  var idx = appPath.indexOf('/');
  if (idx<0) idx = appPath.length;
  appPath='/'+appPath.substring(0, idx+1);
  // alert(appPath);
  try{
    secs = "Sections=" + secs;
    if(secs.length > 4000-appPath.length-8) {
      var i=secs.lastIndexOf("|",secs.length-2);
      if(i>0) {
        secs = secs.substr(0,i+1);
      } else
        alert(Labels.get('label.Jaffa.Panels.alertHeaderMsg.FoldingSectionCookieTooBig')+"\n" + secs);
    }
    document.cookie=secs+'; path='+appPath;
  } catch(e) {
    alert(Labels.get('label.jaffaRIA.MessageBox.alertErrorMsgText')+" " + e);
  }
}


function validateForm(formName) {
  if (validationRules.validatePage()) {
    return startProgressBar(formName);
  } else {
    return false;
  }
}


function validateTrapKey(formName) {
  //return true;
  /** TODO: removal of the following code is to disable the validation step in trapKey() function.
     Reason: The progress bar screen breaks the javascript validation code implemented in methods overwrite postForm(). 
             On the other hand, validation will be taken in postForm(). 
             It is not clear why trapKey() needs to do the validation to skip postForm().
  */
  if (validationRules.validatePage()) {
    //The progress-bar makes a page un-usable; especially when a button has 'submit=true and confirm='{xyz} and guarded=true',
    //and the user cancel's the Action when asked to confirm it. In any case, the progress-bar will be displayed by the postForm().
    //So the commenting of the following logic shouldn't break anything.
    /*
    try {
      if (document.getElementById(formName).target != "_blank") {
        progress_clear() ;
        eval("if (document.getElementById('" + formName + "_EntirePage') != null) {document.getElementById('" + formName + "_EntirePage').style.display = 'none';}");
        eval("if (document.getElementById('" + formName + "_PushPage') != null) {document.getElementById('" + formName + "_PushPage').style.display = 'block';}");
        progressTimer = setTimeout('progress_update()',progressInterval);
      }
    } catch (e) { }
    */
    return true;
  } else {
    return false;
  }
}

function postProcess(frm, fld) {
}

function resizeTextArea(el , factorW , factorH , offsetW , offsetH , maxW , maxH) {
  var screenW = 640, screenH = 480;

  if (parseInt(navigator.appVersion)>3) {
    screenW = screen.width;
    screenH = screen.height;
  }
  else if (navigator.appName == "Netscape" && parseInt(navigator.appVersion)==3 && navigator.javaEnabled() ) {
    var jToolkit = java.awt.Toolkit.getDefaultToolkit();
    var jScreenSize = jToolkit.getScreenSize();
    screenW = jScreenSize.width;
    screenH = jScreenSize.height;
  }

  if (maxW != null)
    screenW = maxW;
  if (maxH != null)
    screenH = maxH;
  rows = (screenH + offsetH ) / 22;
  cols = (screenW + offsetW ) / 7;
  rows = rows * (factorH / 100);
  cols = cols * (factorW / 100);

  if (factorH != null)
    el.rows = rows;
  if (factorW != null)
    el.cols = cols;
}


// Used for a basic get or for a complete form post based on options provided
// url will default to the form action in post mode if no url is supplied
// on a form post, if fwd is supplied it is added as a hidden input
function sendAjaxRequest(pane, url, frm, fwd) {
  if(frm) {
    // hide the form (if there is a span) and give the loading message
    var e=document.getElementById(frm.id+"_EntirePage");
    if(e) {
      e.style.display='none';
      var d = document.createElement("span");
      d.setAttribute("class","ajaxLoad");
      d.appendChild(document.createTextNode("Loading..."));
      pane.appendChild(d);
    }
  } else {
    // just replace the html
    pane.innerHTML="<span class='ajaxLoad'>Loading...</span>";
  }
  var kw = {
    url : url,
    load : function(type, data, evt) {
      //@TODO - if component or session has timed out, need to detect log-on page on return
      if (data.indexOf("<html")!=-1 || data.indexOf("<HTML")!=-1) {
        // handle the error situation
        var i = data.split("<!-- Start Of Message-->");
        if (i.length>1) {
          i = i[1];
          i = i.split("<!-- End Of Message-->")[0];
        } else {
          // most likely a logon page 
          if (data.indexOf("<!-- this is the logon screen -->") != -1) {
            pane.innerHTML = '<span class="ajaxError">Your session has expired. Please click <a class="navLogout" href="logout.dop">here</a> to log back on your server. </span>';
            return;
          }
          i = data;
        }
        pane.innerHTML = i;
      } else {
        try {
          pane.innerHTML = data;
          loadJsCss(pane);
          if (messageList.length > 0) {
            pane.innerHTML = null;
            openDOMBrowser('_document');
          } else {
            var onload = pane.getAttribute("onLoad");
            if(onload!=null && onload!="") {
              //console.log("Execute onLoad for ",pane.id," code=",onload);
              try {
                evaluate(onload);
              } catch (e) {
                alert("JavaScript Error in onLoad(). Exception was "+e+"\nTarget DIV id="+pane.id+"\nCode="+onload);
              }
            }
          }
        } catch (ex) {
          alert("dojo.io.bind:load() failed for div.id="+pane.id+" : "+ex);
        }
      }
    },
    timeoutSeconds : 600,
    timeout : function(type, data, evt) {
      pane.innerHTML = "<span class='ajaxError'>Server loading timeout.</span>"; 
      //console.log("TIMEOUT div.id=",pane.id," type=",type," evt=",evt," data=",data);
    },
    error : function(type, data, evt) {
      pane.innerHTML = "<span class='ajaxError'>Fail to load from server.</span>";
      //console.log("ERROR div.id=",pane.id," type=",type," evt=",evt," data=",data);
    }
  };
  
  if(frm!=null) {
    kw.url=frm.action;
    kw.formNode=frm;
    if(fwd!=null) {
      var el=document.createElement('input')
      el.setAttribute("type","hidden");
      el.setAttribute("name", "ajaxForward");
      el.setAttribute("value", fwd);
      frm.appendChild(el);
    }
  } else {
    kw.method="GET";
  }
  if(url!=null) {
    kw.url = url;
  }
  //alert("Load AJAX\nURL="+kw.url+"\nForm="+(frm?frm.name+" - "+frm.elements.length+" fields":"none") );  
  dojo.io.bind(kw);
}

var jaffaLoadedJsCss="";

// http://forum.nextapp.com/forum/lofiversion/index.php/t2613.html
function evaluate (src, scope) {
  scope = scope || window;
  if (window.execScript) {
    window.execScript(src);
  } else if (eval.call) {
    eval.call(scope, src);
  } else {
    eval (src);
  }
}

function loadJsCss(pane) {
  var hd=document.getElementsByTagName('head')[0];
  var el=null;
  var data=pane.getElementsByTagName("script");
  if(data!=null) {
    for (var i=0; i<data.length; i++) {      
      if (data[i].src=="") {
        //console.log("EVAL JavaScript : DIV id=", pane.id, " has script (id=", i, ") code=", data[i].innerHTML);
        try {
          evaluate(data[i].innerHTML);
        } catch (e) {
          alert("JavaScript Error. Exception was "+e+"\nTarget DIV id="+pane.id+" (script index="+i+")\nCode="+data[i].innerHTML);
        }
      } else if(jaffaLoadedJsCss.indexOf(data[i].src)==-1) {
        //console.log("LOAD JavaScript : DIV id=", pane.id, " has script (id=", i, ") src=", data[i].src);
        el=document.createElement('script')
        el.setAttribute("type","text/javascript");
        el.setAttribute("src", data[i].src);
        hd.appendChild(el);
        jaffaLoadedJsCss+=";"+data[i].src;
      }
    }
  }  
    
  data=pane.getElementsByTagName("link"); 
  if(data!=null) {
    for (var i=0; i<data.length; i++) {      
      if(data[i].href!=="" && jaffaLoadedJsCss.indexOf(data[i].href)==-1) {
        //console.log("LOAD CSS : DIV id=", pane.id, " has link (id=", i, ") href=", data[i].href);
        el=document.createElement("link")
        el.setAttribute("rel", "stylesheet");
        el.setAttribute("type", "text/css");
        el.setAttribute("href", data[i].href);
        hd.appendChild(el);  
        jaffaLoadedJsCss+=";"+data[i].href;
      }
    }
  }
}

//Debug routine that will display the contents of all the form objects on the page
//Optional parameter to limit the display to a specific form's name
function debugElements(m) {
 for(i=0;i<document.forms.length;i++){
  f=document.forms[i];
  //if(m==null || m==f.name) {
   s="Form="+f.name+"\n";
   for(j=0;j<f.elements.length;j++){
    e=f.elements[j];
    s+="   Field="+e.name+", id="+e.id+", class="+e.className+", value="+e.value+"\n";
   }
   alert(s);
  //}
 }
}