/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

// Include this javascript file in the <head> tag, if requiring the reset functionality

function resetForm() {
  //New outer loop to reset multiple forms on the page due to the new tile architecture
  for (i=0; i<document.forms.length; i++) {
    document.forms[i].reset();
    for (j=0; j<document.forms[i].elements.length; j++) {
      var el = document.forms[i].elements[j];
      if (el.type == "text" || el.type == "textarea")
        el.value='';
      else if (el.type == "checkbox")
        el.checked='';
      else if (el.type == "hidden" && el.className == "WidgetCheckBox")
        el.value='false';
      else if (el.type == "select-one" && el.className == "WidgetDropDown" && el.selectedIndex>=0)
        el.selectedIndex = 0;

      // Remove the error-messages for the element
      try {
        if (document.getElementById(el.id + "-error") != "true")
          document.body.removeChild(document.getElementById(el.id + "-error"));
      } catch (e) {
      }
    }
  }

  // Remove the error-messages for the folding-sections
  var objs = foldingSectionList;
  for(i = 0; i<objs.length; i++) {
    try {
      if (document.getElementById(objs[i].id + "section-error") != null)
        document.body.removeChild(document.getElementById(objs[i].id + "section-error"));
    } catch (e) {
    }
  }

  // Reset the valid-state
  validationRules.valid = true;
}


function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
  var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
  if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
