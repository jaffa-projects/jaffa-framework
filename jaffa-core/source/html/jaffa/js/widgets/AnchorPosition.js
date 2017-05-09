  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/



function findParentNodes(node, tagname)
{
  if (node.nodeName == tagname)
  {
    return node;
  }
  if (node.parentNode != null)
  {

    return findParentNodes(node.parentNode, tagname);
  }
  return null;

}





function getAnchorPosition(anchorname, mode)
{
  obj = document.getElementById(anchorname);
  var coordinates = new Object();
  coordinates.x = findPosX(obj);
  coordinates.y = findPosY(obj);
  return coordinates;
}

// Functions for IE to get position of an object
function AnchorPosition_getPageOffsetLeft(el)
{
  var ol = el.offsetLeft;
  while ((el = el.offsetParent) != null)
  {
    ol += el.offsetLeft;
  }
  return ol;
}

function AnchorPosition_getWindowOffsetLeft(el)
{
  var scrollamount = document.body.scrollLeft;
  return AnchorPosition_getPageOffsetLeft(el) - scrollamount;
}

function AnchorPosition_getPageOffsetTop(el)
{
  var ot = el.offsetTop;
  while ((el = el.offsetParent) != null)
  {
    ot += el.offsetTop;
  }
  return ot;
}

function AnchorPosition_getWindowOffsetTop(el)
{
  var scrollamount = document.body.scrollTop;
  return AnchorPosition_getPageOffsetTop(el) - scrollamount;
}
