<%--
ServerParams.jsp

Generates a javascript 'ServerParams' object that has a getServerTime() method for getting the server time.
Current implementation only provides current server time.

@param command Name of the action

Should be included in the main JSP that loads all the java script using the following...

<script type="text/javascript" src="js/extjs/jaffa/util/ServerParams.jsp%>"></script>

--%><%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Date,java.util.TimeZone" %>
<%@ page import = "org.jaffa.datatypes.Formatter" %>
<%
String cmd=(String)request.getParameter("command");
if (cmd.equals("getServerTime")) {
  out.write((new java.util.Date()).getTime()+"");
} else if (cmd.equals("getServerTimeZone")) {
  java.util.TimeZone tz = java.util.TimeZone.getDefault();
  out.write("{name:'"+tz.getDisplayName()+"',utcOffset:'"+tz.getRawOffset()+"'}");
}
%>