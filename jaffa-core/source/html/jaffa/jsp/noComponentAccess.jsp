<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@page import="org.jaffa.util.MessageHelper"%>
<%@ page import="org.owasp.encoder.Encode" %>
<html>
<head>
    <title><%= MessageHelper.findMessage("title.Jaffa.NoComponentAccess.titleHeader", null) %></title>
    <Portlet:Header/>
    <LINK rel='STYLESHEET' type='text/css' href='jaffa/css/jaffa.css'>
</head>
<body>
<h2><%= MessageHelper.findMessage("label.Jaffa.NoComponentAccess.ComponentAccessViolation", null) %></h2>
<p>
<%= MessageHelper.findMessage("label.Jaffa.NoComponentAccess.NoAuthorityExecuteMsg", null) %>
</p>
<BR>
<%= MessageHelper.findMessage("label.Jaffa.NoComponentAccess.ComponentName", null) %>: <i><%=Encode.forHtml((String) request.getAttribute("componentName"))%></i>
</body>
</html>
