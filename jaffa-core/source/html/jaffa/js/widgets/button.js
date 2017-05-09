  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//////////////////////////////////////
// button.js - Default Code for all Button Widgets to use
//
// By Paul Extance
//
// Added support for roll over image buttons.
//
// Version: 1.1
// Modified: 1-Aug-2001
////////////////////////////////////////////////////


function buttonEvent(f, e)
{
  //alert("Button Event");
  f.form.eventId.value = f.name + "=" + e;
  //alert("Button Event :" + f.form.eventId.value);
  postForm(f.form, f);
}

function buttonEvent2(frmObj, fld, e)
{
  //alert("Button Event 2");
  frmObj.eventId.value = fld + "=" + e;
  //alert("Button Event :" + frmObj.eventId.value);
  postForm(frmObj, null);
}

function buttonFindObj(n, d)
{
  //v3.0
  var p, i, x;
  if (!d)
    d = document;
  if ((p = n.indexOf("?")) > 0 && parent.frames.length)
  {
    d = parent.frames[n.substring(p + 1)].document;
    n = n.substring(0, p);
  }
  if (!(x = d[n]) && d.all)
    x = d.all[n];
  for (i = 0; !x && i < d.forms.length; i++)
    x = d.forms[i][n];
  for (i = 0; !x && d.layers && i < d.layers.length; i++)
    x = buttonFindObj(n, d.layers[i].document);
  return x;
}

/* Functions that swaps images. */
function buttonSwapImage()
{
  //v3.0
  var i, j = 0, x, a = buttonSwapImage.arguments;
  document.buttonsr = new Array;
  for (i = 0; i < (a.length - 2); i += 3)
  if ((x = buttonFindObj(a[i])) != null)
  {
    document.buttonsr[j++] = x;
    if (!x.oSrc)
      x.oSrc = x.src;
    x.src = a[i + 2];
  }
}

function buttonSwapImgRestore()
{
  var i, x, a = document.buttonsr;
  for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
    x.src = x.oSrc;
}

function buttonPreloadImages()
{
  var d = document;
  if (d.images)
  {
    if (!d.buttonp)
      d.buttonp = new Array();
    var i, j = d.buttonp.length, a = buttonPreloadImages.arguments;
    for (i = 0; i < a.length; i++)
    if (a[i].indexOf("#") != 0)
    {
      d.buttonp[j] = new Image;
      d.buttonp[j++].src = a[i];
    }
  }
}

// Debug confirmation
//alert("Loaded button.js");
