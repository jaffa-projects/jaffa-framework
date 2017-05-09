  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

var textfield = null;

function type(text)
{
  var textvalue = document.getElementById(textfield).value;
  document.getElementById(textfield).value = textvalue + text;
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;

  }
}


function backSpace()
{
  fieldvalue = document.getElementById(textfield).value;
  document.getElementById(textfield).value = fieldvalue.substring(0,
    fieldvalue.length - 1);
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;

  }
}

function popUp(textTarget, rows, mode)
{
  position = getAnchorPosition(textTarget + "_anchor", 'outer');
  textfield = textTarget;

  if (mode == "keyboard")
  {

    if (rows)
    {
      document.getElementById("LowerCaseKeyBoard").style.top = position.y +
        (document.getElementById(textTarget).rows * 16) + 5;
      document.getElementById("LowerCaseKeyBoard").style.left = position.x;
      document.getElementById("UpperCaseKeyBoard").style.top = position.y +
        (document.getElementById(textTarget).rows * 16) + 5;
      document.getElementById("UpperCaseKeyBoard").style.left = position.x;
    }
    else
    {
      document.getElementById("LowerCaseKeyBoard").style.top = position.y + 21;
      document.getElementById("LowerCaseKeyBoard").style.left = position.x;
      document.getElementById("UpperCaseKeyBoard").style.top = position.y + 21;
      document.getElementById("UpperCaseKeyBoard").style.left = position.x;
    }

    showKeyboard();
  }
  else
  {
    document.getElementById("KeyPad").style.top = position.y + 21;
    document.getElementById("KeyPad").style.left = position.x;
    showKeyPad();
  }




}

function clear()
{
  document.getElementById(textfield).value = "";
  document.getElementById("calendardd").disabled = false;
}

function showKeyboard()
{
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;
  }

  document.getElementById("LowerCaseKeyBoard").style.visibility = "visible";
}

function showKeyPad()
{
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;
  }

  document.getElementById("KeyPad").style.visibility = "visible";
}


function hideKeyboard()
{
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = true;
  }

  document.getElementById("LowerCaseKeyBoard").style.visibility = "hidden";
  document.getElementById("UpperCaseKeyBoard").style.visibility = "hidden";
  document.getElementById(textfield).focus();
}

function hideKeyPad()
{
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = true;
  }

  document.getElementById("KeyPad").style.visibility = "hidden";
  document.getElementById(textfield).focus();
}


function showLower()
{
  document.getElementById("LowerCaseKeyBoard").style.visibility = "visible";
  document.getElementById("UpperCaseKeyBoard").style.visibility = "hidden";
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;

  }
}

function showUpper()
{
  document.getElementById("LowerCaseKeyBoard").style.visibility = "hidden";
  document.getElementById("UpperCaseKeyBoard").style.visibility = "visible";
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;

  }
}

function sign()
{
  var textvalue = document.getElementById(textfield).value;
  if (textvalue.substring(0, 1) != "-")
  {
    document.getElementById(textfield).value = "-" + textvalue;
  }
  else
  {
    document.getElementById(textfield).value = textvalue.substring(1,
      textvalue.length)
  }
  if (document.getElementById("calendardd") != null)
  {
    document.getElementById("calendardd").disabled = false;
  }
}
