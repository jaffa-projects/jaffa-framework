<%-- Removes the shortcut flag of the specified saved query --%>
<%-- Expects parameters of componentName=[c name]&savedQueryId=[id] --%>

<%@ page language="java" %>
<%@ page import="org.jaffa.components.finder.QuerySaver" %>

<%
  String cname = request.getParameter("componentName");
  String qId = request.getParameter("savedQueryId");
  QuerySaver.removeShortcutFlag(cname, qId);
%>
