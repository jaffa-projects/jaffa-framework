/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//////////////////////////////////////
// Grid.js - Default Code for all Grid Widgets to use
//
// By Paul Extance
//
// Version: 1.0 - Implement the Grid Packing Function
// Modified: 14-Aug-2001
// 
// Version: 1.1 - Remove inline code and moved grid pop-up function to .js file
// Modified: 21-Jul-2005
//
// Version: 1.2 - Merged Grid & UserGrid code
// Modified: 5-Oct-2005
//
////////////////////////////////////////////////////

function gridPack(theGridNode) {
  //debug("Pack Widget : " + field);
  var destField = theGridNode.element;
  var data = "\n";
  if (theGridNode.children != null && theGridNode.children.length > 0) {
    // format : form_field_row_name
    var pattern =  /^.*_([0-9]+)_(.*)$/;
    for (var i = 0; i < theGridNode.children.length; i++) {
      var el = theGridNode.children[i].element;
      if (el.isGroupWidget=='true') {
        for (var j=0; j<el.elements.length; j++) {
          if (el.elements[j].checked) {
            el = el.elements[j];
            break;
          }
        }
      }

      var matchArray = null
      // Special code to ignore unchecked radio group values
      if(el.type=="radio" && !el.checked) {
         // Skip as this is a un-selected radio value
         //debug("Radio : "+el.id+"\n="+el.value+" Not Selected");
      } else {
        matchArray = el.id.match(pattern);
      }
      
      if (matchArray != null) {
        data += "<widget row=\"" + matchArray[1] + "\" field=\"" + matchArray[2] + "\">";
        // If Widget Is Lazy, Build Content
        // If(el.className.substr(6) == "Xxx") xxxPack(?,?);
        data += toXML(el.type=="checkbox" && !el.checked ? "false" : el.value);
        data += "</widget>\n";
      }
    }
  }

  // If this a usergrid include the settings
  // Find the id of the settings hidden field, which is the grid id without "_h" and suffixed by "_colSettings"
  var colSettingsElement = document.getElementById(theGridNode.element.id.substr(0, theGridNode.element.id.length-2) + "_colSettings");
  if (colSettingsElement != null)
    data += colSettingsElement.value;
  //theGridNode.element.value = data;
  

  // If this is a tree, get the state of all nodes and return it
  // first extract the grid name
  var field = theGridNode.element.name;
  field = field.substr(0, field.length-2);
  // then find the id of the table, which is the grid id without "_h"
  var rows = document.getElementById(theGridNode.element.id.substr(0, theGridNode.element.id.length-2)).rows;
  var expandedRows = "";
  var rootId = null;
  var counter = 0;
  for (var i = 0; i < rows.length; i++) {
    var r = rows[i];
    if (r.id.indexOf(field + ":") != "-1") {
      if (r.style.display != "none") {
        if(currentState.expandedLevels[r.id + "-"] != null) {
          expandedRows +="<display row='"+(counter++)+"' expanded='true'>true</display>";
        } else {
          expandedRows +="<display row='"+(counter++)+"' expanded='false'>true</display>";
        }
      } else {
        expandedRows +="<display row='"+(counter++)+"' expanded='false'>false</display>";
      }
    }
  }
  //theGridNode.element.value += expandedRows;
  data += expandedRows;
  
  // For some reason this does not work the 2nd time the same grid is packed if using AJAX to change the form settings!
  theGridNode.element.value=data;
  if(document.getElementById(theGridNode.element.id).value=="" && data!="") {
    document.getElementById(theGridNode.element.id).value=data; // hack!!
    try{//log hack if needed, via firebug
      if(console)
        console.debug("Apply set element hack on id="+theGridNode.element.id+", value=" + document.getElementById(theGridNode.element.id).value);
    } catch (e) {}
  }    

}
