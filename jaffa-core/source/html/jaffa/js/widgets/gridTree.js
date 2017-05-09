/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//---------------------------------------
//Functions for managing the Folding Tree
//---------------------------------------

function hasChildren(thisID) {
  var rows = document.getElementsByTagName("TR");
  for (var j = 0; j < rows.length; j++) {
    if(rows[j].id.indexOf(thisID) != -1) {
      return true
    }
  }
  return false;
}

function toggleRows(elm , callBack) {
  var rows = document.getElementsByTagName("TR");
  var newDisplay = "none";
  parentElement = elm.parentNode;
  while (parentElement.tagName != "TR") {
    parentElement = parentElement.parentNode;
  }

  var thisID = parentElement.id + "-";
  if (hasChildren(thisID)) {
    pathname = location.href;
    myDomain = pathname.substring(0,pathname.lastIndexOf('/')) +'/';
    var folder = elm.getElementsByTagName("IMG")[0];
    if (folder.src == myDomain + "jaffa/imgs/tree/contractLevel.gif") {
      folder.src = myDomain + "jaffa/imgs/tree/expandLevel.gif";
    } else {
      if (folder.src == myDomain + "jaffa/imgs/tree/expandLevel.gif") {
        folder.src =  myDomain + "jaffa/imgs/tree/contractLevel.gif";
      }
    }

    // Are we expanding or contracting? If the first child is hidden, we expand
    for (var i = 0; i < rows.length; i++) {
      var r = rows[i];
      if (matchStart(r.id, thisID, true)) {
        if (r.style.display == "none") {
          if (document.all) newDisplay = "block"; //IE4+ specific code
          else newDisplay = "table-row"; //Netscape and Mozilla
        }
        break;
      }
    }

    // When expanding, only expand one level.  Collapse all desendants.
    var matchDirectChildrenOnly = (newDisplay != "none");

    if(matchDirectChildrenOnly) {
      currentState.addExpanded(thisID);
    } else {
      currentState.removeExpanded(thisID);
    }

    for (var j = 0; j < rows.length; j++) {
      var s = rows[j];
      if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
        s.style.display = newDisplay;
      }
    }

    if(matchDirectChildrenOnly) {
      if (currentState.expandedLevels.length > 0) {
        for (i= 0 ; i < currentState.expandedLevels.length ; i++) {
          if(thisID != currentState.expandedLevels[i]) {
            if (matchStart(currentState.expandedLevels[i], thisID,  false)) {
              toggleChildren(currentState.expandedLevels[i]);

            }
          }
        }
      }
    }
  } else {
    currentState.setCallBack(parentElement.id);
    while (parentElement.tagName != "FORM") {
      parentElement = parentElement.parentNode;
    }
    frm = parentElement;
    frm.eventId.value=callBack+";GetChildren";
    frm.target='_self';
    preProcess(frm, null);
    postForm(frm, null)
  }
}

function toggleChildren(thisID , eventId) {
  var rows = document.getElementsByTagName("TR");
  var newDisplay = "none";
  // Are we expanding or contracting? If the first child is hidden, we expand
  for (var i = 0; i < rows.length; i++) {
    var r = rows[i];
    if (matchStart(r.id, thisID, true)) {
      if (r.style.display == "none") {
        if (document.all) newDisplay = "block"; //IE4+ specific code
        else newDisplay = "table-row"; //Netscape and Mozilla
      }
      break;
    }
  }

  function getParent(thisID) {
    return thisID.substring(0,thisID.lastIndexOf("-")); 
  }

  // When expanding, only expand one level.  Collapse all desendants.
  var matchDirectChildrenOnly = true;

  for (var j = 0; j < rows.length; j++) {
    var s = rows[j];
    if (matchStart(s.id, thisID, matchDirectChildrenOnly)) {
      if(document.getElementById(getParent(s.id)).style.display != "none") {
        s.style.display = newDisplay;
      }
    }
  }
}


function matchStart(target, pattern, matchDirectChildrenOnly) {
  var pos = target.indexOf(pattern);
  if (pos != 0) return false;
  if (!matchDirectChildrenOnly) return true;
  if (target.slice(pos + pattern.length, target.length).indexOf("-") >= 0) return false;
  return true;
}

function collapseAllRows() {
  var rows = document.getElementsByTagName("TR");
  for (var j = 0; j < rows.length; j++) {
    var r = rows[j];
    if (r.id.indexOf("-") >= 0) {
      r.style.display = "none";
    }
  }
}
