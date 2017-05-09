<%@ page language="java" %>
<%@ page import="org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.session.IContextManager" %>
<%
IContextManager cm = ContextManagerFactory.instance();
cm.setThreadContext(request);
cm.setUserPreference("user.desktopId" , request.getParameter("Id"));
%>
