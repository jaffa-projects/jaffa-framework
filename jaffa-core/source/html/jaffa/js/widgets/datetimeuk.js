  /**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

// DEPRECATED - USED BY THE DateTimeTag which is deprecated

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//           DateTime.js - written by Jonny Reid     //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function datetime_checkDateOnly(fld, isDateTime, prefix, interval)
{

  if (fld.value != "")
  {
    var source = fld.value;
    if (isDateTime == "true")
    {
      var msg =
        "This is not a valid date!\n Please use either the format\n    mm/dd/yyyy hh:mm:ss \nor\n    use n/t for current date expressions";
    }
    else
    {
      var msg =
        "This is not a valid date!\n Please use either the format\n    mm/dd/yyyy\nor\n    use n/t for current date expressions";
    }
    //Use this one if spaces are allows, at the moment the servlet does not support this!
    var datePat1 = /^ *([NnTt]) *([-+]) *([0-9]+)$/;
    //var datePat1 = /^([NnTt])(([-+])([0-9]+)([YyMmDdHh])?)?$/;
    var datePat2 = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
    var datePat3 = /^(\d{1,2})\/(\d{1,2})\/(\d{2})$/;
    var datetimePat1 = /^(\d{1,2})\/(\d{1,2})\/(\d{4}) (\d{1,2})\:(\d{1,2})\:(\d{1,2})$/;
    var datetimePat2 = /^(\d{1,2})\/(\d{1,2})\/(\d{2}) (\d{1,2})\:(\d{1,2})\:(\d{1,2})$/;
    var datatype = "";
    var matchArray = source.match(datePat1); // is the format ok?

    if (matchArray == null)
    {
      var datePat1 =  /  ^  * ([NnTt]) * $ / ;
      datatype = "n or t";
      var matchArray = source.match(datePat1); // is the format ok?

    }
    if (matchArray == null)
    {
      matchArray = source.match(datePat2); // is the format ok?

      if (matchArray == null)
      {
        matchArray = source.match(datePat3); // is the format ok?

        if (matchArray == null)
        {
          matchArray = source.match(datetimePat1); // is the format ok?
          datatype = "dateTime";
          if (matchArray == null)
          {
            matchArray = source.match(datetimePat2); // is the format ok?
            datatype = "dateTime";
            if (matchArray == null)
            {
              return msg;
            }
          }
        }

      }
      month = matchArray[2]; // parse date into variables
      day = matchArray[1];
      year = matchArray[3];
      if (year.length == 2)
        year = "20" + year;
      // Check Month
      if (month < 1 || month > 12)
      {
        // check month range
        return ("Month must be between 1 and 12.");
      }
      // Check Day
      if (day < 1 || day > 31)
      {
        return ("Day must be between 1 and 31.");
      }
      // Check Day Based On Month
      if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31)
      {
        return ("Month " + month + " doesn't have 31 days!");
      }
      // Check Day Based On Leap Year
      if (month == 2)
      {
        // check for february 29th
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day == 29 && !isleap))
        {
          return ("February " + year + " doesn't have " + day + " days!");
        }

      }
      if ((datatype == "dateTime") && (isDateTime == "true"))
      {
        roundUp(fld, 15, prefix);
        fld.value = day + "/" + month + "/" + year;
      }
      else
      {
        fld.value = day + "/" + month + "/" + year;
      }
      return "";
    }
    else
    {

      var newdate = new Date();

      if (matchArray[2] == "+")
      {
        var newtimems = newdate.getTime() + (matchArray[3] * 24 * 60 * 60 *
          1000);
      }
      else if (matchArray[2] == "-")
      {
        var newtimems = newdate.getTime() - (matchArray[3] * 24 * 60 * 60 *
          1000);
      }
      else
      {
        var newtimems = newdate;

      }
      newdate.setTime(newtimems);
      if ((isDateTime == "true") && (matchArray[1] == "n" || matchArray[1] ==
        "N"))
      {
        fld.value = (newdate.getMonth() + 1) + "/" + newdate.getDate() + "/" +
          newdate.getFullYear() + " " + newdate.getHours() + ":" +
          newdate.getMinutes() + ":00";
        roundUp(fld, interval, prefix);
      }
      else
      {
        fld.value = newdate.getDate() + "/" + (newdate.getMonth() + 1) + "/" +
          newdate.getFullYear();
      }
      return "";
    }
  }
  else
  {


    return "";
  }

}

function datetime_updateHidden(fld)
{
  var dtwidgethidden = fld ;
  var dtwidgethour = "" + fld + "_hours";
  var dtwidgetmins = "" + fld + "_mins";
  var dtwidgetdate = "" + fld + "_date";
  if (document.getElementById(dtwidgetdate).value != "")
  {
    document.getElementById(dtwidgethidden).value = document.getElementById
      (dtwidgetdate).value + " " + document.getElementById(dtwidgethour).value
      + ":" + document.getElementById(dtwidgetmins).value;
  }
  else
  {
    document.getElementById(dtwidgethidden).value = "";
    document.getElementById(dtwidgethour).value = "";
    document.getElementById(dtwidgetmins).value = "";
  }
}

function roundUp(fld, interval, prefix)
{

  //gets current value of field
  var modDate = new Date(fld.value);

  //gets id's for drop downs
  var dtwidgethour = "" + prefix + "_hours";
  var dtwidgetmins = "" + prefix + "_mins";
  var hours = modDate.getHours();
  var mins = modDate.getMinutes();

  //rounds up the minute to closest interval
  var roundedMinute = parseInt(((modDate.getMinutes() + (interval / 2)) /
    interval)) * interval;

  //works out if the closet roundup will need to increment the hour
  if (roundedMinute > 59)
  {
    //var otherDate = modDate.getTime()+((60-mins)*60*1000) ;
    modDate.setTime(otherDate);
    fld.value = modDate.getDate() + "/" + (modDate.getMonth() + 1) + "/" +
      modDate.getFullYear();
    document.getElementById(dtwidgethour).value = (modDate.getHours() < 10 ?
      "0" + modDate.getHours(): modDate.getHours());
    document.getElementById(dtwidgetmins).value = "00";

  }
  else
  {
    fld.value = modDate.getDate() + "/" + (modDate.getMonth() + 1) + "/" +
      modDate.getFullYear();
    document.getElementById(dtwidgethour).value = (modDate.getHours() < 10 ?
      "0" + modDate.getHours(): modDate.getHours());
    document.getElementById(dtwidgetmins).value = (roundedMinute < 10 ? "0" +
      roundedMinute: roundedMinute);

  }
}
