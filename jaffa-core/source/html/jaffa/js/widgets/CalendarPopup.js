// ===================================================================
// Author: Matt Kruse <matt@mattkruse.com>
// WWW: http://www.mattkruse.com/
//
// NOTICE: You may use this code for any purpose, commercial or
// private, without any further permission from the author. You may
// remove this notice from your final code if you wish, however it is
// appreciated by the author if at least my web site address is kept.
//
// You may *NOT* re-distribute this code in any way except through its
// use. That means, you can include it in your product, or your web
// site, or any other form where the code is actually being used. You
// may not put the plain javascript up on your site for download or
// include it in your javascript libraries for download. Instead,
// please just point to my URL to ensure the most up-to-date versions
// of the files. Thanks.
// ===================================================================

// DEPRECATED - USED BY THE DateTimeTag which is deprecated

/*
CalendarPopup.js
Author: Matt Kruse
Last modified: 5/31/01

DESCRIPTION: This object implements a popup calendar to allow the user to
select a date.

COMPATABILITY: Works with Netscape 4.x, 6.x, IE 5.x on Windows. Some small
positioning errors - usually with Window positioning - occur on the
Macintosh platform.
The calendar can be modified to work for any location in the world by
changing which weekday is displayed as the first column, changing the month
names, and changing the column headers for each day.




USAGE:
// Create a new CalendarPopup object of type WINDOW
var cal = new CalendarPopup();

// Create a new CalendarPopup object of type DIV using the DIV named 'mydiv'
var cal = new CalendarPopup('mydiv');

// Set the name of the function to be called when the user clicks on a date.
// This function should accept year,month,date as arguments. It is then the
// responsibility of the function to populate a text box with the date or
// do whetever else is necessary
cal.setReturnFunction(functionname);

// Show the calendar relative to a given anchor
cal.showCalendar(anchorname);

// Hide the calendar. The calendar is set to autoHide automatically
cal.hideCalendar();

// Set the month names to be used. Default are English month names
cal.setMonthNames("Jan","Feb","Mar",...);

// Set the text to be used above each day column. The days start with
// sunday regardless of the value of WeekStartDay
cal.setDayHeaders("S","M","T",...);

// Set the day for the first column in the calendar grid. By default this
// is Sunday (0) but it may be changed to fit the conventions of other
// countries.
cal.setWeekStartDay(1); // week is Monday - Saturday

// Set the calendar offset to be different than the default. By default it
// will appear just below and to the right of the anchorname. So if you have
// a text box where the date will go and and anchor immediately after the
// text box, the calendar will display immediately under the text box.
cal.offsetX = 20;
cal.offsetY = 20;

NOTES:
1) Requires the functions in AnchorPosition.js and PopupWindow.js

2) Your anchor tag MUST contain both NAME and ID attributes which are the
same. For example:
<A NAME="test" ID="test"> </A>

3) There must be at least a space between <A> </A> for IE5.5 to see the
anchor tag correctly. Do not do <A></A> with no space.

4) When a CalendarPopup object is created, a handler for 'onmouseup' is
attached to any event handler you may have already defined. Do NOT define
an event handler for 'onmouseup' after you define a CalendarPopup object
or the autoHide() will not work correctly.

5) The calendar display uses "graypixel.gif" which is a 1x1 gray pixel of
color #C0C0C0. If this file is not present, the calendar display should
still be fine but will not show the gray lines.

6) The calendar popup display uses style sheets to make it look nice.

 */

// CONSTRUCTOR for the CalendarPopup Object

function CalendarPopup()
{
  var newCalendar;
  if (arguments.length > 0)
  {
    newCalendar = new PopupWindow(arguments[0]);
  }
  else
  {
    newCalendar = new PopupWindow();
    newCalendar.setSize(150, 175);
  }
  newCalendar.offsetX =  - 152;
  newCalendar.offsetY = 25;
  newCalendar.autoHide();
  // Calendar-specific properties
  newCalendar.monthNames = new Array("January", "February", "March", "April",
    "May", "June", "July", "August", "September", "October", "November",
    "December");
  newCalendar.dayHeaders = new Array("S", "M", "T", "W", "T", "F", "S");
  newCalendar.returnFunction = "tmpReturnFunction";
  newCalendar.weekStartDay = 0;
  // Method mappings
  newCalendar.setReturnFunction = CalendarPopup_setReturnFunction;
  newCalendar.setMonthNames = CalendarPopup_setMonthNames;
  newCalendar.setDayHeaders = CalendarPopup_setDayHeaders;
  newCalendar.setWeekStartDay = CalendarPopup_setWeekStartDay;
  newCalendar.showCalendar = CalendarPopup_showCalendar;
  newCalendar.hideCalendar = CalendarPopup_hideCalendar;
  newCalendar.getStyles = CalendarPopup_getStyles;
  newCalendar.refreshCalendar = CalendarPopup_refreshCalendar;
  newCalendar.getCalendar = CalendarPopup_getCalendar;
  // Return the object
  return newCalendar;
}

// Temporary default function to be called when a date is clicked, so no error is thrown
function CalendarPopup_tmpReturnFunction(d, m, y)
{
  alert('Use setReturnFunction() to define which function will get the clicked results!');
}

// Set the name of the function to call to get the clicked date
function CalendarPopup_setReturnFunction(name)
{
  this.returnFunction = name;
}

// Over-ride the built-in month names
function CalendarPopup_setMonthNames()
{
  for (var i = 0; i < arguments.length; i++)
  {
    this.monthNames[i] = arguments[i];
  }
}

// Over-ride the built-in column headers for each day
function CalendarPopup_setDayHeaders()
{
  for (var i = 0; i < arguments.length; i++)
  {
    this.dayHeaders[i] = arguments[i];
  }
}

// Set the day of the week (0-7) that the calendar display starts on
// This is for countries other than the US whose calendar displays start on Monday(1), for example
function CalendarPopup_setWeekStartDay(day)
{
  this.weekStartDay = day;
}

// Hide a calendar object
function CalendarPopup_hideCalendar()
{
  document.getElementById("calendardd").disabled = true;
  if (arguments.length > 0)
  {
    window.popupWindowObjects[arguments[0]].hidePopup();
  }
  else
  {
    this.hidePopup();
  }
}

// Refresh the contents of the calendar display
function CalendarPopup_refreshCalendar(index)
{
  var calObject = window.popupWindowObjects[index];
  if (arguments.length > 1)
  {
    calObject.populate(calObject.getCalendar(arguments[1], arguments[2]));
  }
  else
  {
    calObject.populate(calObject.getCalendar());
  }
  if (document.getElementById("calendardd") != null)
  {
    hideOtherWidgets();
    document.getElementById("calendardd").disabled = false;

  }

  calObject.refresh();
}

// Populate the calendar and display it
function CalendarPopup_showCalendar(anchorname, currentDate)
{
  this.populate(this.getCalendar());
  this.showPopup(anchorname);
}

// Get style block needed to display the calendar correctly
function CalendarPopup_getStyles()
{
  var result = "";
  result += "<STYLE>\n";
  result += "TD.cal { font-family:arial; font-size: 8pt; }\n";
  result +=
    "TD.calmonth { font-family:arial; font-size: 8pt; text-align: right;}\n";
  result +=
    "TD.caltoday { font-family:arial; font-size: 8pt; text-align: right; color: white; background-color:#C0C0C0; border-width:1; border-type:solid; border-color:#800000; }\n";
  result +=
    "A.todaylink { font-family:arial; font-size: 8pt; height: 20px; color: black; }\n";
  result += "A.cal { text-decoration:none; color:#000000; }\n";
  result += "A.calthismonth { text-decoration:none; color:#000000; }\n";
  result += "A.calothermonth { text-decoration:none; color:#808080; }\n";
  result += "</STYLE>\n";
  return result;
}

// Return a string containing all the calendar code to be displayed
function CalendarPopup_getCalendar()
{
  if (currentDate)
  {
    if (currentYear < 100)
    {
      if (currentYear < 50)
      {
        currentYear = 2000+parseInt(currentYear);
      }
      else
      {
        currentYear = parseInt(currentYear) + 1900;
      }
    }
    var now = new Date(currentYear, currentMonth - 1, currentDay);
    var thisYear = now.getYear();
  }
  else
  {
    var now = new Date();
  }
  var now2 = new Date();
  if (arguments.length > 0)
  {
    var month = arguments[0];
  }
  else
  {
    var month = now.getMonth() + 1;
  }
  if (arguments.length > 1)
  {
    var year = arguments[1];
  }
  else
  {
    var year = now.getFullYear();
  }
  var daysinmonth = new Array(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    ;
  if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
  {
    // leap year
    daysinmonth[2] = 29;
  }
  var current_month = new Date(year, month - 1, 1);
  var display_year = year;
  var display_month = month;
  var display_date = 1;
  var weekday = current_month.getDay();
  var offset = 0;
  if (weekday >= this.weekStartDay)
  {
    offset = weekday - this.weekStartDay;
  }
  else
  {
    offset = 7-this.weekStartDay + weekday;
  }
  if (offset > 0)
  {
    display_month--;
    if (display_month < 1)
    {
      display_month = 12;
      display_year--;
    }
    display_date = daysinmonth[display_month] - offset + 1;
  }
  var next_month = month + 1;
  var next_month_year = year;
  if (next_month > 12)
  {
    next_month = 1;
    next_month_year++;
  }
  var last_month = month - 1;
  var last_month_year = year;
  if (last_month < 1)
  {
    last_month = 12;
    last_month_year--;
  }
  var date_class;
  var result = "";
  if (this.type == "WINDOW")
  {
    var windowref = "window.opener.";
  }
  else
  {
    var windowref = "";
  }
  // If POPUP, write entire HTML document
  if (this.type == "WINDOW")
  {
    result += "<HTML><HEAD><TITLE>"+Labels.get('label.Jaffa.Widget.CalenderPopup.titleHeader')+"</TITLE>" + this.getStyles() +
      "</HEAD><BODY MARGINWIDTH='0' MARGINHEIGHT='0' TOPMARGIN='0' RIGHTMARGIN='0' LEFTMARGIN='0'>\n";
    result += '<CENTER><TABLE WIDTH="100%" BORDER="0" BORDERWIDTH="0" CELLSPACING="0" CELLPADDING="0">\n';
  }
  else
  {
    result += '<TABLE WIDTH="144" BORDER="1" BORDERWIDTH="1" BORDERCOLOR="#808080" CELLSPACING="0" CELLPADDING="1">\n';
    result += '<TR><TD ALIGN="CENTER">\n';
    result += '<CENTER>\n';
    result += '<TABLE WIDTH="144" BORDER="0" BORDERWIDTH="0" CELLSPACING="0" CELLPADDING="0">\n';
  }
  result += '<TR BGCOLOR="#C0C0C0">\n';
  result += ' <TD BGCOLOR="#C0C0C0" CLASS="cal" WIDTH="22" ALIGN="CENTER" VALIGN="MIDDLE"><B><A CLASS="cal" HREF="javascript:' + windowref + 'CalendarPopup_refreshCalendar(' + this.index + ',' + last_month + ',' + last_month_year + ');">&lt;&lt;</A></B></TD>\n';
  result += ' <TD BGCOLOR="#C0C0C0" CLASS="cal" WIDTH="100" ALIGN="CENTER">' +
    this.monthNames[month - 1] + ' ' + year + '</TD>\n';
  result += ' <TD BGCOLOR="#C0C0C0" CLASS="cal" WIDTH="22" ALIGN="CENTER" VALIGN="MIDDLE"><B><A CLASS="cal" HREF="javascript:' + windowref + 'CalendarPopup_refreshCalendar(' + this.index + ',' + next_month + ',' + next_month_year + ');">&gt;&gt;</A></B></TD>\n';
  result += '</TR></TABLE>\n';
  result += '<TABLE WIDTH="120" BORDER="0" CELLSPACING="1" CELLPADDING="0" ALIGN="CENTER">\n';
  result += '<TR>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 1) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 2) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 3) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 4) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 5) % 7] + '</TD>\n';
  result += ' <TD CLASS="cal" ALIGN="RIGHT" WIDTH="14%">' + this.dayHeaders[(this.weekStartDay + 6) % 7] + '</TD>\n';
  result += '</TR>\n';
  result += '<TR><TD COLSPAN="7" ALIGN="CENTER"><IMG SRC="graypixel.gif" WIDTH="120" HEIGHT="1"></TD></TR>\n';
  for (var row = 1; row <= 6; row++)
  {
    result += '<TR>\n';
    for (var col = 1; col <= 7; col++)
    {
      if (display_month == month)
      {
        date_class = "calthismonth";
      }
      else
      {
        date_class = "calothermonth";
      }
      if ((display_month == now.getMonth() + 1) && (display_date == now.getDate
        ()) && (display_year == now.getFullYear()))
      {
        td_class = "caltoday";
      }
      else
      {
        td_class = "calmonth";
      }
      result += ' <TD CLASS="' + td_class + '"><A HREF="javascript:' + windowref + this.returnFunction + '(' + display_year + ',' + display_month + ',' + display_date + ');' + windowref + 'CalendarPopup_hideCalendar(\'' + this.index + '\');" CLASS="' + date_class + '">' + display_date + '</A></TD>\n';
      display_date++;
      if (display_date > daysinmonth[display_month])
      {
        display_date = 1;
        display_month++;
      }
      if (display_month > 12)
      {
        display_month = 1;
        display_year++;
      }
    }
    result += '</TR>';
  }
  result += '<TR><TD COLSPAN="7" ALIGN="CENTER"><IMG SRC="graypixel.gif" WIDTH="120" HEIGHT="1"></TD></TR>\n';
  result += '<TR>\n';
  result += ' <TD COLSPAN="7" ALIGN="CENTER">\n';
  result += '   <A CLASS="todaylink" HREF="javascript:' + windowref + this.returnFunction + '(\'' + now2.getFullYear() + '\',\'' + (now2.getMonth() + 1) + '\',\'' + now2.getDate() + '\');' + windowref + 'CalendarPopup_hideCalendar(\'' + this.index + '\');">Today</A>\n';
  result += '   <BR>\n';
  result += ' </TD></TR></TABLE></CENTER></TD></TR></TABLE>\n';
  if (this.type == "WINDOW")
  {
    result += "</BODY></HTML>\n";
  }
  return result;
}

function hideOtherWidgets()
{
  if (document.getElementById("LowerCaseKeyBoard") != null)
  {
    document.getElementById("LowerCaseKeyBoard").style.visibility = "hidden";
    document.getElementById("UpperCaseKeyBoard").style.visibility = "hidden";
  }
  if (document.getElementById("KeyPad") != null)
  {
    document.getElementById("KeyPad").style.visibility = "hidden";
  }
}
