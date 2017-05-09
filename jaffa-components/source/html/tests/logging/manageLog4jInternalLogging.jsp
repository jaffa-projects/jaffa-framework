<%@ page language="java" %>
<%@page import='java.lang.reflect.Field' %>
<%@page import='org.apache.log4j.helpers.LogLog' %>

<%
// Obtain handles on the private static fields of the LogLog class
Field debugEnabledField = LogLog.class.getDeclaredField("debugEnabled");
Field quietModeField = LogLog.class.getDeclaredField("quietMode");
debugEnabledField.setAccessible(true);
quietModeField.setAccessible(true);

// Enable/disable internal logging based on the input event
String event = request.getParameter("event");
if ("Enable".equals(event)) {
  LogLog.setInternalDebugging(true);
  LogLog.setQuietMode(false);
} else if ("Disable".equals(event)) {
  LogLog.setInternalDebugging(false);
  LogLog.setQuietMode(true);
}

// Current settings
boolean debugEnabled = ((Boolean) debugEnabledField.get(null)).booleanValue();
boolean quietMode = ((Boolean) quietModeField.get(null)).booleanValue();
String currentStatus = debugEnabled && !quietMode ? "enabled" : "disabled";
String allowedAction = debugEnabled && !quietMode ? "Disable" : "Enable";
%>

<html>
  <head>
    <title>Manage Log4J's internal logging</title>
    <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
  </head>
  <body>
    <h1>Manage Log4J's internal logging</h1>
    <p>
    <h2>Current Status: <%= currentStatus %></h2>
    <p>
    <form action="manageLog4jInternalLogging.jsp" method="post">
      <table>
        <tr>
          <td><input type="submit" name="event" value="Refresh"></td>
          <td><input type="submit" name="event" value="<%= allowedAction %>"></td>
        </tr>
      </table>
    </form>
  </body>
</html>
