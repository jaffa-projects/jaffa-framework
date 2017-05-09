/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *------------------------------------------------------------------------
 * coreRules.js - Defalut code for EditBox rules engine
 *
 * By Jonny Reid/Paul Extance
 * Version: 1.1
 * Modified: 29-June-2005
 *------------------------------------------------------------------------
 * <description>
 ************************************************************************/


/************************************************************************
 * Global Rules
 *------------------------------------------------------------------------
 * Register these rules with
 *      validationRules.addGlobalRule("rules-mandatory" , "mandatory");
 ***********************************************************************/

// Trim leading and trailing white-space from a field
function mandatory(el) {
  if(el.value == null || el.value.length == 0) {
    return jaffaMessageRoot.coreRules.FieldMandatory;
  } else {
    return "";
  }
}

/**************************************************************************
 * Field Rules
 *------------------------------------------------------------------------
 * Register these rules with
 *      validationRules.addFieldRule("rules-datatype" , "checkDataType");
 *      validationRules.addFieldRule("rules-trim" , "trimWhitespace");
 *************************************************************************/

// Trim leading and trailing white-space from a field
function trimWhitespace(field) {
  // skip leading and trailing whitespace and return everything in between
  //field.value = field.value.replace(/^\s*(\b.*\b|)\s*$/, "$1");
  assignValueAndPreserveSelection(field, field.value.replace(/^\s*(\b.*\b|)\s*$/, "$1"));
}

// This routine will check assign which rules to be executed dependant on data type.
function checkDataType(el , datatypeValue) {
  if (datatypeValue=="Integer" || datatypeValue=="Decimal" ) {
    return checkNumericValidation(el, el.getAttribute("rules-datatype-maxSize") , el.getAttribute("rules-datatype-minSize") , (datatypeValue=="Decimal" ? el.getAttribute("rules-datatype-maxDigits") : 0) , el.getAttribute("rules-datatype-minValue") , el.getAttribute("rules-datatype-maxValue") , "," , ".");
  } else
  if (datatypeValue=="String") {
    var msg = null;
    if (el.getAttribute("rules-datatype-maxSize")) {
      msg = checkMaxLength(el, el.getAttribute("rules-datatype-maxSize"));
    }
    if (msg != null) return msg;
    if (el.getAttribute("rules-datatype-minSize")) {
      msg = checkMinLength(el, el.getAttribute("rules-datatype-minSize"));
    }
    if (msg != null) return msg;
    if (el.getAttribute("rules-datatype-layout")) {
      msg = checkRegularExpression(el, el.getAttribute("rules-datatype-layout"));
    }
    if (msg != null) return msg;
  } else
  if (datatypeValue=="DateOnly") {
    return checkDateTime(el, "false" , el.getAttribute("rules-datatype-layout"));
  } else
  if (datatypeValue=="DateTime") {
    return checkDateTime(el, "true" , el.getAttribute("rules-datatype-layout"));
  }
}

// Part of function checkDataType(...)
function checkNumericValidation(field, maxSize , minSize, digits , minValue , maxValue , groupingSeparator , decimalSeparator) {
  source = field.value.toString();
  if (source == null || source.length == 0) {
    return;
  }
  // Remove the groupingSeparator from number
  var i;
  while ((i = source.indexOf(groupingSeparator)) != -1) {
    temp1 = source.substring(0, i);
    temp2 = (i + 1) < source.length ? source.substring(i + 1, source.length) : "";
    source = temp1 + temp2;
  }
  // Replace the decimalSeparator with a '.', if its anything else
  if (decimalSeparator != "." && (i = source.indexOf(decimalSeparator)) != -1) {
    temp1 = source.substring(0, i);
    temp2 = (i + 1) < source.length ? source.substring(i + 1, source.length) : "";
    source = temp1 + "." + temp2;
  }
  // Is this a number
  var patten = /^[+-]?([0-9]*)(\.([0-9]*))?$/;
  var matchArray = source.match(patten);
  if (matchArray == null) {
    return jaffaMessageRoot.coreRules.NumberNotValid;
  } else {
    significant = matchArray[1] != null ? matchArray[1] : "";
    fraction = matchArray[3] != null ? matchArray[3] : "";
    significantLength = significant.length;
    fractionLength = fraction.length
    if (matchArray[2] != null && matchArray[2].length > 0 && (digits == null || digits == 0)) {
      return jaffaMessageRoot.coreRules.OnlyIntegerValuesUsed;
    }
    if(digits != null && digits > 0) {
      if (significantLength > (maxSize - digits))
        return jaffaMessageRoot.coreRules.Only+" " + (maxSize - digits) + " "+jaffaMessageRoot.coreRules.SignificantDigitsAllowed;
      if (fractionLength > digits)
        return jaffaMessageRoot.coreRules.Only+" " + digits + " "+jaffaMessageRoot.coreRules.DecimalPlacesAllowed;
    }
    if (maxSize != null && significantLength + fractionLength > maxSize) {
      return jaffaMessageRoot.coreRules.DigitsLengthExceeded+" "+maxSize+".";
    }
    if (minValue != null && parseFloat(source) < parseFloat(minValue)) {
      return jaffaMessageRoot.coreRules.ValueCannotLessThan+" " + minValue + ".";
    }
    if (maxValue != null && parseFloat(source) > parseFloat(maxValue)) {
      return jaffaMessageRoot.coreRules.ValueExceedsMaxValue+" " + maxValue + ".";
    }
    return;
  }
}


// Part of function checkDataType(...)
function checkDateTime(fld ,isDateTime, layout) {
  var isUk = false;
  var dateSeparator = '/';
  var dotSeparator = '.';
  var isDotSeparator = false;
  if (layout != null && layout.substr(0,1) == "d") {
    if (layout.substr(2,1) === dotSeparator) {
      isDotSeparator = true;
      dateSeparator = dotSeparator;
    } else {
      isUk = true;
    }
  } else {
    isUk = false;
  }
  if (fld.value != "") {
    var source = fld.value;
    //Use this one if spaces are allowed, at the moment the servlet does not support this!
    var datePat1 = /^ *([NnTt]) *([-+]) *([0-9]+)$/;
    //var datePat1 = /^([NnTt])(([-+])([0-9]+)([YyMmDdHh])?)?$/;
    var datePatRe2 = '^(' + '\\' + 'd{1,2})' + '\\' + dateSeparator + '(' + '\\' + 'd{1,2})' + '\\' + dateSeparator + '(' + '\\' + 'd{4})$';
    var datePat2 = RegExp(datePatRe2);
    var datePatRe3 = '^(' + '\\' + 'd{1,2})' + '\\' + dateSeparator + '(' + '\\' + 'd{1,2})' + '\\' + dateSeparator + '(' + '\\' + 'd{2})$';
    var datePat3 = RegExp(datePatRe3);
    var datetimePatRe1 = '^('+'\\'+'d{1,2})'+'\\'+dateSeparator+'('+'\\'+'d{1,2})'+'\\'+dateSeparator+'('+'\\'+'d{4}) ('+'\\'+'d{1,2})\:('+'\\'+'d{1,2})\:('+'\\'+'d{1,2})$';
    var datetimePat1 = RegExp(datetimePatRe1);
    var datetimePatRe2 = '^('+'\\'+'d{1,2})'+'\\'+dateSeparator+'('+'\\'+'d{1,2})'+'\\'+dateSeparator+'('+'\\'+'d{2}) ('+'\\'+'d{1,2})\:('+'\\'+'d{1,2})\:('+'\\'+'d{1,2})$';
    var datetimePat2 = RegExp(datetimePatRe2);
    var datatype = "";
    var matchArray = source.match(datePat1); // is the format ok?
    if (matchArray == null) {
      var datePat1 = /^ *([NnTt])*$/;
      datatype = "n or t";
      var matchArray = source.match(datePat1); // is the format ok?
    }
    if (matchArray == null) {
      matchArray = source.match(datePat2); // is the format ok?
      if (matchArray == null) {
        matchArray = source.match(datePat3); // is the format ok?
        if (matchArray == null) {
          matchArray = source.match(datetimePat1); // is the format ok?
          datatype = "dateTime";
          if (matchArray == null) {
            matchArray = source.match(datetimePat2); // is the format ok?
            datatype = "dateTime";
            if (matchArray == null) {
              // Did not understand format of string
              var msg = jaffaMessageRoot.coreRules.InvalidDate;
              if(isDateTime)
                msg += "/"+jaffaMessageRoot.coreRules.alertTime;
              msg += " "+jaffaMessageRoot.coreRules.alertValue+" ";
              if(layout == null)
                msg += jaffaMessageRoot.coreRules.CorrectFormatUsage;
              else  
                msg += jaffaMessageRoot.coreRules.EitherFormat+" " + layout + " "+jaffaMessageRoot.coreRules.UseNTCurrentDateExp;
              return msg;  
            }
          }
        }
      }
      if ((isUk != null && isUk == true) || (isDotSeparator != null && isDotSeparator === true)) {
        month = matchArray[2]; // parse date into variables
        day = matchArray[1];
        year = matchArray[3];
      } else {
        month = matchArray[1]; // parse date into variables
        day = matchArray[2];
        year = matchArray[3];
      }
      if (year.length == 2) year = "20" + year;
      // Check Month
      if (month < 1 || month > 12) { // check month range
        return(jaffaMessageRoot.coreRules.MonthBet1and12);
      }
        // Check Day
      if (day < 1 || day > 31) {
        return(jaffaMessageRoot.coreRules.DaysBet1and31);
      }
      // Check Day Based On Month
      if ((month==4 || month==6 || month==9 || month==11) && day==31) {
        return(jaffaMessageRoot.coreRules.Month+" "+month+" "+jaffaMessageRoot.coreRules.DoesnotHave31Days);
      }
      // Check Day Based On Leap Year
      if (month == 2) { // check for february 29th
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day==29 && !isleap)) {
          return(jaffaMessageRoot.coreRules.February+" " + year + " "+jaffaMessageRoot.coreRules.DoesnotHave+" " + day + " "+jaffaMessageRoot.coreRules.days+"!");
        }
      }
      return "";
    } else {
      var newdate=new Date();
      try{newdate=getServerDate();} catch(e) {} //See if server dates are being used.
      if (matchArray[2] == "+") {
        var newtimems=newdate.getTime()+(matchArray[3]*24*60*60*1000);
      } else if (matchArray[2] == "-") {
        var newtimems=newdate.getTime()-(matchArray[3]*24*60*60*1000);
      } else {
        var newtimems=newdate.getTime();
      }
      newdate.setTime(newtimems);
      if ((isUk != null && isUk == true) || (isDotSeparator != null && isDotSeparator === true)) {
        if ((isDateTime == "true") && (matchArray[1] == "n" || matchArray[1] == "N" )) {
          fld.value = newdate.getDate() + dateSeparator + (newdate.getMonth()+1) + dateSeparator +  newdate.getFullYear() + " " + newdate.getHours() + ":" + (newdate.getMinutes()<10?"0"+newdate.getMinutes():""+newdate.getMinutes()) + ":" + (newdate.getSeconds()<10?"0"+newdate.getSeconds():""+newdate.getSeconds());
        } else {
          fld.value = newdate.getDate() + dateSeparator + (newdate.getMonth()+1) + dateSeparator +  newdate.getFullYear();
        }
      } else {
        if ((isDateTime == "true") && (matchArray[1] == "n" || matchArray[1] == "N" )) {
          fld.value = (newdate.getMonth()+1) + dateSeparator + newdate.getDate() + dateSeparator + newdate.getFullYear() + " " + newdate.getHours() + ":" + (newdate.getMinutes()<10?"0"+newdate.getMinutes():""+newdate.getMinutes()) + ":" + (newdate.getSeconds()<10?"0"+newdate.getSeconds():""+newdate.getSeconds());
        } else {
          fld.value = (newdate.getMonth()+1) + dateSeparator + newdate.getDate() + dateSeparator + newdate.getFullYear();
        }
      }
      return "";
    }
  } else {
    return "";
  }
}


// This routine will check the max length of a String.
// Part of function checkDataType(...) for a String
function checkMaxLength(el, maxLength) {
  if (el.value != null) {
    if ((el.value.length - carriageReturnCount(el.value)) > maxLength) {
      return jaffaMessageRoot.coreRules.LengthCannotExceed+" " + maxLength + " "+jaffaMessageRoot.coreRules.characters;
    }
  }
}

// This routine will check the min length of a String.
// Part of function checkDataType(...) for a String
function checkMinLength(el, minLength) {
  if (el.value != null) {
    if (el.value.length < minLength) {
    return jaffaMessageRoot.coreRules.LengthLessThan+" " + minLength + " "+jaffaMessageRoot.coreRules.characters;
    }
  }
}

// This routine will check the String against a Reg Exp.
// Part of function checkDataType(...) for a String
function checkRegularExpression(el , expValue) {
   var re = new RegExp("" + expValue);
   if (el.value.match(expValue) == null) {
    return jaffaMessageRoot.coreRules.ValueDoesNotMatchExpression;
  }
}


// This routine will check to see is a value is within a list.
// @todo - is this still needed?
function checkInList(el , listValue) {
  var re = new RegExp(listValue);
  if (el.value.search(re) < 0) {
    return jaffaMessageRoot.coreRules.ValueNotContained;
  }
}

// This routine will auto focus the next targetted field
// @todo - is this still needed?
function changeField(fieldId,fieldObject){
  if(fieldObject.value.length == fieldObject.maxLength){
    document.getElementById(fieldId).focus();
  }
}

// This routine returns the number of CarriageReturn characters in the input String.
// In IE6.0, entering a newline in a TextArea results in a character-count of 2
// Hence if we try to check the length of that String, it'll return erroneous results
// This routine can be used to ignore all CarriageReturn characters.
function carriageReturnCount(s) {
  var count = 0;
  if (s != null) {
    for (var i = 0; i < s.length; i++) {
      if (s.charCodeAt(i) == 13)
        ++count;
    }
  }
  return count;
}
