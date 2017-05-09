  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/


// Create CalendarPopup objects
var cal = new CalendarPopup();
cal.setReturnFunction("showDate");

var cal2 = new CalendarPopup("testdiv1");
cal2.setReturnFunction("showDate");
//cal2.setMonthNames('Januar'y,'February','March','April','May','June','July','August','September','October','November','December');
//cal2.setDayHeaders('S','M','D','M','D','F','S');
//cal2.setWeekStartDay(1);

var textfield = null;
var hiddenfield = null;
var currentDate = false;
var currentMonth = 0;
var currentYear = 0;
var currentDay = 0;


// Function to get input back from calendar popup
function showDate(y, m, d)
{
  document.getElementById(textfield).value = m + "/" + d + "/" + y;
  datetime_updateHidden(hiddenfield);
}

function setCurrentDate(dateField)
{


  text = document.getElementById(dateField).value;

  if (text != "")
  {
    currentDate = true;
    textLength = text.length;
    index = text.indexOf("/");
    currentMonth = text.substring(0, index);
    text = text.slice(index + 1, textLength);

    textLength = text.length;
    index = text.indexOf("/");
    currentDay = text.substring(0, index);
    text = text.slice(index + 1, textLength);
    currentYear = text;
  }
}



// Function to get input back from calendar popup
function setTextField(textField)
{
  textfield = textField + "_date";
  hiddenfield = textField;
}
