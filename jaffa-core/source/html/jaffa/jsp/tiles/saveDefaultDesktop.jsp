<%@ page language="java" %>
<%@ page import="org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.session.IContextManager" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%
IContextManager cm = ContextManagerFactory.instance();
cm.setThreadContext(request);
cm.setUserPreference("user.desktopId" , StringHelper.escapeJavascript(request.getParameter("Id")));
%>
