<!DOCTYPE html>
<%--
/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/
--%>

<%@page import="java.util.*,java.text.*" %>

<%
  TimeZone utcTZ = TimeZone.getTimeZone("Etc/GMT");
  TimeZone serverTZ = TimeZone.getDefault();
  DateFormat utcDF = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
  utcDF.setTimeZone(utcTZ);
  String serverTZStr = serverTZ.getDisplayName();
  if (serverTZ.useDaylightTime()) serverTZStr += ", has DST";
  else serverTZStr += ", no DST";

  String rcp=request.getContextPath();
  
  Calendar cd = new GregorianCalendar();
  cd.setTimeZone(utcTZ);
  cd.clear();
  cd.set(1999,6,1);
  
  Enumeration rnames = request.getParameterNames();
  if (rnames.hasMoreElements()) {
    StringBuffer dateStrings = new StringBuffer();
    StringBuffer dateValues = new StringBuffer();
    while(rnames.hasMoreElements()) {
      String nm = (String) rnames.nextElement();
      try {
        cd.clear();
        cd.setTimeInMillis(Long.parseLong(request.getParameter(nm)));
        if (dateStrings.length()>0) {
          dateStrings.append(",");
          dateValues.append(",");
        }
        dateStrings.append(nm+":'"+utcDF.format(cd.getTime())+"'");
        dateValues.append(nm+":new Date("+cd.getTimeInMillis()+")");
      } catch (NumberFormatException ex) {}
    }
%>
    {
      dateStrings : {<%= dateStrings.toString() %>},
      dateValues : {<%= dateValues.toString() %>}
    }
<%} else {%>
<html>
  <head>
    <title>Server Time Zone (<%= serverTZStr %>)</title>

    <script type="text/javascript" src="<%= rcp %>/js/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/ext-all-debug.js"></script>

    <script type="text/javascript" src="<%= rcp %>/js/extjs/ext-patches.js"></script>
    <script type="text/javascript">
      <%--
        Override many aspects of the Ext.util.Format
        - Look at Java's default layout for DateOnly / DateTime and convert them to JavaScript Equivilents for the Locale
        - Set a currency symbol based on an application rule or the default one for the locale of the server (not the user!)
        - Add a default date, dateTime and money formatter/renderer to support the above settings
      --%>
      if(Ext.util.Format){
        Ext.util.Format.defaultDateLayout = 'm/d/Y';
        Ext.util.Format.defaultDateTimeLayout = 'm/d/Y H:i:s';
        Ext.util.Format.defaultCurrencySymbol = '$';
      }
      
      // Firebug hack
      if(!console) var console={debug:Ext.emptyFn,info:Ext.emptyFn,warn:Ext.emptyFn,error:Ext.emptyFn,trace:Ext.emptyFn,dir:Ext.emptyFn};
      // Stop ref's to ExtJS site
      Ext.BLANK_IMAGE_URL = "<%= rcp %>/js/extjs/resources/images/default/s.gif";

      var valuesFromServer = {
        dateStrings : {serverGen: '<%= utcDF.format(cd.getTime()) %>', dtGen: '<%= utcDF.format(cd.getTime()) %>'},
        dateValues : {serverGen: new Date(<%= cd.getTimeInMillis() %>), dtGen: new Date(<%= cd.getTimeInMillis() %>)},
        appCtx : '<%= rcp %>'
      }

      <!-- add server timezone to javascript Date class; this is also implemented in loadExtJS.jsp -->
      Date.serverTimezone = {
        longName: '<%= serverTZ.getDisplayName(false, TimeZone.LONG, request.getLocale()) %>',
        shortName: '<%= serverTZ.getDisplayName(false, TimeZone.SHORT, request.getLocale()) %>',
        tzOffset: parseInt('<%= serverTZ.getOffset((new Date()).getTime()) %>'), // current timezone offset.
        stdOffset: parseInt('<%= serverTZ.getRawOffset() %>') // standard offset
      };
      // if daylight savings time is needed, should bring in displayNames, offset value, and start/end date of DST.
    </script>
    
    <!-- ExtJS Add-Ons -->
    <script type="text/javascript" src="<%= rcp %>/js/extjs/ext-extensions.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/ext-overrides.js"></script>
    <!-- Include Ext stylesheets here: -->
    <link rel="stylesheet" type="text/css" href="<%= rcp %>/js/extjs/resources/css/ext-all.css" />

    <script type="text/javascript" src="<%= rcp %>/js/extjs/jaffa/data/Util.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/ux/form/DateTime.js"></script>
    
    <script type="text/javascript" src="<%= rcp %>/js/extjs/jaffa/dateOverrides/util/Date.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/jaffa/dateOverrides/form/DateField.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/jaffa/dateOverrides/ux/form/DateTime.js"></script>
    <script type="text/javascript" src="<%= rcp %>/js/extjs/jaffa/dateOverrides/widgets/DatePicker.js"></script>

    <script type="text/javascript" src="ServerTimeZone.js"></script>

    
  </head>
  <body>
  </body>
</html>
<% } %>