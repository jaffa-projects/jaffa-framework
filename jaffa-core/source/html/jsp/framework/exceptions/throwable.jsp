<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<%@page import="java.io.*"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="org.jaffa.util.MessageHelper"%>

<html>
<head><title><%= MessageHelper.findMessage("title.Common.Throwable", null) %></title></head>
<body>
<%= MessageHelper.findMessage("exception.jaffa.throwable.contactSystemAdminError", null) %>
<BR/>
<BR/>
<PRE>
<%
StringWriter writer = new StringWriter();
Throwable th = null;
if (exception instanceof ServletException)
    th = ((ServletException) exception).getRootCause();
if (th == null)
    th = exception;
th.printStackTrace(new PrintWriter(new BufferedWriter(writer)));
writer.flush();
out.println( writer.toString());
%>
</PRE>
</body>
</html>
