


/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

function doTextTip(el , txt) {
  if (document.getElementById("textPopUp") != null) {
    document.body.removeChild(document.getElementById("textPopUp"));
  } else {
    var newHTML = "<div class='outertexttip' id='textPopUp' ";
    newHTML += "style='z-index: 2;position:absolute;top:" + findPosY(el) + "px;left:" + (findPosX(el) + 20) +  "px;'>";
    newHTML += "<table><tr><td class='exitbutton' align='right' ";
    newHTML += "onClick='javascript:doTextTip();'>x</td></tr><tr><td>" + txt;
    newHTML += "</td></tr></table></div>\n";
    if (document.getElementById("textPopUp") == null) {
      document.body.insertAdjacentHTML("afterBegin", newHTML);
    }
  }
}
