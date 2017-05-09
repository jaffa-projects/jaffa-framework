  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

/////////////////////////////////////////////////////////////////////////
// Generic Resize by Erik Arvidsson                                    //
//                                                                     //
// You may use this script as long as this disclaimer is remained.     //
// See www.dtek.chalmers.se/~d96erik/dhtml/ for mor info               //
//                                                                     //
// How to use this script!                                             //
// Link the script in the HEAD and create a container (DIV, preferable //
// absolute positioned) and add the class="sort" to it.                //
/////////////////////////////////////////////////////////////////////////

var theobject = null; //This gets a value as soon as a resize start
var childSizes = null;
var resize = false;
function resizeObject()
{
  this.el = null; //pointer to the object
  this.dir = ""; //type of current resize (n, s, e, w, ne, nw, se, sw)
  this.grabx = null; //Some useful values
  this.graby = null;
  this.width = null;
  this.height = null;
  this.left = null;
  this.top = null;
}


//Find out what kind of resize! Return a string inlcluding the directions
function getDirection(el)
{
  var xPos, yPos, offset, dir;
  dir = "";

  xPos = window.event.offsetX;
  yPos = window.event.offsetY;

  offset = 8; //The distance from the edge in pixels

  //if (yPos<offset) dir += "n";
  //else if (yPos > el.offsetHeight-offset) dir += "s";
  //if (xPos<offset) dir += "w";
  //else
  if (xPos > el.offsetWidth - offset)
    dir += "e";

  return dir;
}

function doDown()
{
  var el = getReal(event.srcElement, "className", "sort");


  if (el == null)
  {
    theobject = null;
    return ;
  }


  dir = getDirection(el);
  if (dir == "")
    return ;

  theobject = new resizeObject();

  theobject.el = el;
  theobject.dir = dir;

  //getChildrenSize(el);

  theobject.grabx = window.event.clientX;
  theobject.graby = window.event.clientY;
  theobject.width = el.offsetWidth;
  theobject.height = el.offsetHeight;
  theobject.left = el.offsetLeft;
  theobject.top = el.offsetTop;

  window.event.returnValue = false;
  window.event.cancelBubble = true;
}

function doUp()
{

  if (theobject != null)
  {
    //formatOtherCells(theobject.el);
    theobject = null;
  }
}

function doMove()
{
  var el, xPos, yPos, str, xMin, yMin;
  xMin = 8; //The smallest width possible
  yMin = 8; //             height

  el = getReal(event.srcElement, "className", "sort");

  if (el.className == "sort" && el.parentElement.parentElement.tagName ==
    "THEAD")
  {
    str = getDirection(el);
    //Fix the cursor
    if (str == "")
      str = "default";
    else
      str += "-resize";
    el.style.cursor = str;
  }

  if (el.className == "sort" && el.parentElement.parentElement.tagName ==
    "THEAD")
  {

    //Dragging starts here
    if (theobject != null)
    {
      if (dir.indexOf("e") !=  - 1)
      {
        var formerWidth = theobject.el.offsetWidth;
        var newWidth = Math.max(xMin, theobject.width + window.event.clientX -
          theobject.grabx);
        ((theobject.el.parentElement).parentElement).parentElement.style.width
          = ((theobject.el.parentElement).parentElement)
          .parentElement.offsetWidth - (formerWidth - newWidth);
        theobject.el.style.width = Math.max(xMin, theobject.width +
          window.event.clientX - theobject.grabx);
        resize = true;
        document.getElementById(theobject.el.id + '_size').value =
          theobject.el.style.width;

      }
      /**if (dir.indexOf("s") != -1)
      theobject.el.style.height = Math.max(yMin, theobject.height + window.event.clientY - theobject.graby) + "px";

      if (dir.indexOf("w") != -1) {
      theobject.el.style.left = Math.min(theobject.left + window.event.clientX - theobject.grabx, theobject.left + theobject.width - xMin) + "px";
      theobject.el.style.width = Math.max(xMin, theobject.width - window.event.clientX + theobject.grabx) + "px";
      }
      if (dir.indexOf("n") != -1) {
      theobject.el.style.top = Math.min(theobject.top + window.event.clientY - theobject.graby, theobject.top + theobject.height - yMin) + "px";
      theobject.el.style.height = Math.max(yMin, theobject.height - window.event.clientY + theobject.graby) + "px";
      }**/

      //alert(((theobject.el.parentElement).parentElement).parentElement.tagName);
      window.event.returnValue = false;
      window.event.cancelBubble = true;
    }
  }
}


function formatOtherCells(el)
{

  tableObject = ((theobject.el.parentElement).parentElement).parentElement;
  parentCell = theobject.el.parentElement;


  for (var i = 0; i < childSizes.length; i++)
  {

    if (theobject.el != theobject.el.parentElement.children[i])
    {
      theobject.el.parentElement.children[i].style.width = childSizes[i];

    }
    else
    {
      childSizes[i] = theobject.el.offsetWidth;
    }
  }




}

function getChildrenSize(el)
{

  childSizes = new Array(theobject.el.parentElement.length);

  colCount = theobject.el.parentElement.children.length;

  for (var i = 0; i < colCount; i++)
  {

    childSizes[i] = theobject.el.parentElement.children[i].offsetWidth;

  }


}

function getReal(el, type, value)
{
  temp = el;
  while ((temp != null) && (temp.tagName != "BODY"))
  {
    if (eval("temp." + type) == value)
    {
      el = temp;
      return el;
    }
    temp = temp.parentElement;

  }
  return el;
}

document.onmousedown = doDown;
document.onmouseup = doUp;
document.onmousemove = doMove;
