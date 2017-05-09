  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/


////////////////////////////////////////////////////
// PageFooter.js - Page Header Java Script
//
// By Paul Extance and Jonny Reid
//
// Should be included at the bottom of the Outer Page, but be for the </BODY> tag.
//
// The WebWidgets rely on functions in this file
// This file assumes that PageHeader.js has been already loaded.
//
// Version: 1.0a
// Modified: 28-Jun-2001
////////////////////////////////////////////////////


// Position cursor on the next field!
var currfield = getCookie("focus");

if (document.getElementById("rowTarget") == null) {
  if (currfield != null && currfield.length > 0) {
    x = document.getElementById(currfield);
    if (x != null) {
      //alert("Current Field Was " + x.id);
      n = focusOnNext(x);
    }
  } else {
    promptFirstEditBox();
  }
}

function focusOnNext(object) {
  nextObject = nextField(object);
  try {
    nextObject.focus();
  } catch (e) {
    focusOnNext(nextObject);
  }
}

function promptFirstEditBox() {
  for (var d = 0; d < document.forms.length; d++) {
    for (var e = 0; e < document.forms[d].elements.length; e++) {
      if (document.forms[d].elements[e].className == "WidgetEditBox" ||
        document.forms[d].elements[e].className == "WidgetMandatoryEditBox" ||
        (globalConfig.focusWidgetDropDown && document.forms[d].elements[e].className == "WidgetDropDown"))
      {
        try {
          document.forms[d].elements[e].focus();
          break;
        } catch (x) {
          continue;	  	 
        }
      }
    }
  }
}

if (messageList.length > 0) {
  if (windowType != "true") {
    document.getElementById("errorBox").style.display = "block";
    document.getElementById("errorBox").style.left = "" + (
      (document.body.clientWidth) / 2) - 150;
    document.getElementById("errorBox").style.top = "" + (
      (document.body.clientHeight) / 2) - 120;
    if (messageList.length < 2) {
      document.getElementById("previous").style.visibility = "hidden";
      document.getElementById("next").style.visibility = "hidden";
    }
    showMessages(0);
  } else {
    if (document.getElementById("calendardd") != null)
      document.getElementById("calendardd").disabled = true;
    openDOMBrowser('_document');
  }
} else {
  if (document.getElementById("calendardd") != null)
    document.getElementById("calendardd").disabled = true;
}
