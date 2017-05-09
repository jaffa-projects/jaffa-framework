<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'header' of MainLayout.jsp --%>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<!-- Start of '/jaffa/jsp/tiles/header.jsp' -->
<Portlet:Header/>
<tiles:useAttribute name='title' classname='String' ignore='true'/>
<logic:notEmpty name='title'><title><% out.print(MessageHelper.hasTokens(title) ? MessageHelper.replaceTokens(pageContext, title) : title); %></title></logic:notEmpty>
<script type='text/javascript' src='jaffa/js/panels/reset.js'></script>
<link href='jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
<!-- End of '/jaffa/jsp/tiles/header.jsp' -->
