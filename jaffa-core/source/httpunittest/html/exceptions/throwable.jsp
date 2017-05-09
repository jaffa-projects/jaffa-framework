<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<%@page import="java.io.*"%>
<%@page import="javax.servlet.ServletException"%>

<html>
<head><title>Throwable</title></head>
<body>
A Throwable Exception has been thrown. Please contact the System Administrator.
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
