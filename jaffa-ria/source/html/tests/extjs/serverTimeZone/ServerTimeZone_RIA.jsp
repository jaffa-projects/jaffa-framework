
<%--
/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/
 
 !!! This is not functional. Needs fixes !!!
--%>

<%@page import="java.util.*,java.text.*" %>
<%@ page import = "org.jaffa.util.URLHelper" %>

<%
  TimeZone utcTZ = TimeZone.getTimeZone("Etc/GMT");
  TimeZone serverTZ = TimeZone.getDefault();
  DateFormat utcDF = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
  utcDF.setTimeZone(utcTZ);
  String serverTZStr = serverTZ.getDisplayName();
  if (serverTZ.useDaylightTime()) serverTZStr += ", has DST";
  else serverTZStr += ", no DST";

  String rcp=request.getContextPath();
  String pageRef=request.getRequestURI().substring(rcp.length()+1);
  
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Server Time Zone (<%= serverTZStr %>)</title>
    <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>

    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
    </jsp:include>

    <script type="text/javascript">
      var valuesFromServer = {
        dateStrings : {serverGen: '<%= utcDF.format(cd.getTime()) %>', dtGen: '<%= utcDF.format(cd.getTime()) %>'},
        dateValues : {serverGen: new Date(<%= cd.getTimeInMillis() %>), dtGen: new Date(<%= cd.getTimeInMillis() %>)},
        appCtx : '<%= rcp %>'
      }

    </script>
    
    <script type="text/javascript" src="<%= rcp %>/tests/extjs/serverTimeZone/ServerTimeZone.js"></script>

  </head>
  <body>
  </body>
</html>
<% } %>