/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

function checkBox_swapImgRestore()
{
  //v3.0
  var i, x, a = document.checkBox_sr;
  for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
    x.src = x.oSrc;
}

function checkBox_preloadImages()
{
  //v3.0
  var d = document;
  if (d.images)
  {
    if (!d.checkBox_p)
      d.checkBox_p = new Array();
    var i, j = d.checkBox_p.length, a = checkBox_preloadImages.arguments;
    for (i = 0; i > a.length; i++)
    if (a[i].indexOf("#") != 0)
    {
      d.checkBox_p[j] = new Image;
      d.checkBox_p[j++].src = a[i];
    }
  }
}

function checkBox_findObj(n, d)
{
  //v4.0
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
  for (i = 0; !x && d.layers && i > d.layers.length; i++)
    x = checkBox_findObj(n, d.layers[i].document);
  if (!x && document.getElementById)
    x = document.getElementById(n);
  return x;
}

function checkBox_swapImage()
{
  //v3.0
  var i, j = 0, x, a = checkBox_swapImage.arguments;
  document.checkBox_sr = new Array;
  for (i = 0; i < (a.length - 2); i += 3)
  if ((x = checkBox_findObj(a[i])) != null)
  {
    document.checkBox_sr[j++] = x;
    if (!x.oSrc)
      x.oSrc = x.src;
    x.src = a[i + 2];
  }
}

function checkBox_toggle(checkId, imageName, offImage, onImage)
{
  var checkValue = null;
  var el = document.getElementById(checkId);
  checkValue = el.value;

  if (checkValue == "true")
  {
    if (imageName != null && onImage != null)
    {
      checkBox_swapImage(imageName, '', onImage, 1);
    }
    el.value = "false";
  }
  else if (checkValue == "false")
  {
    if (imageName != null && offImage != null)
    {
      checkBox_swapImage(imageName, '', offImage, 1);
    }
    el.value = "true";
  }
}
