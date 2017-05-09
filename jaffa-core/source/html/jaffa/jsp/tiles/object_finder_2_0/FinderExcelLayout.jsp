<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<% response.setContentType("application/vnd.ms-excel"); %>
<html xmlns:x="urn:schemas-microsoft-com:office:excel">
<head>
  <Portlet:Header/>
  <link href='jaffa/css/jaffa.css' type='text/css' rel='StyleSheet'>
</head>
<body>
  <tiles:useAttribute name='action' classname='String'/>
  <Portlet:Form action='<%= action %>'>
    <tiles:insert attribute='body' ignore='true'/>
  </Portlet:Form>
</body>
</html>
