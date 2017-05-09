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
<%@ page import = "org.jaffa.util.MessageHelper" %>
<html>
<head>
    <title><%= MessageHelper.findMessage("title.Jaffa.PageExpired.titleHeader", null) %></title>
    <Portlet:Header/>
    <LINK rel='STYLESHEET' type='text/css' href='jaffa/css/jaffa.css'>
</head>
<body>
  <CENTER>
    <B><%= MessageHelper.findMessage("title.Jaffa.PageExpired.InfoMsg", null) %></B>
  </CENTER>
</body>
</html>
