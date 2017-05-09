<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import='org.jaffa.components.navigation.NavAccessor' %>
<%@ page import='org.jaffa.components.navigation.NavOption' %>
<%@ page import="java.util.*,org.jaffa.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%! private static Logger log = Logger.getLogger("jaffa.jsp.tiles.desktop");%>

<% 
  String desktopId = (String)request.getAttribute("jaffa.desktopId");
 
  if (desktopId == null) {
    desktopId = request.getParameter("desktopId");
  }
  if (desktopId == null) {
    log.debug("Cannot display the requested desktop...Displaying the default desktop");%>
    <tiles:insert definition="site.index" flush="true" />  
  <%} else {
    NavAccessor nA = NavAccessor.getNavAccessor(request);
    NavOption nOption  = nA.getDesktopNavOptions(desktopId);
    if(nOption == null) {
     log.debug("DesktopId is not valid...Displaying the default desktop");%>
     <tiles:insert definition="site.index" flush="true" />
<%} else {
       request.setAttribute("jaffa.desktopOption", nOption);
%>
<tiles:insert definition='<%=(nOption.getStrutsTileTemplate()==null?"global.desktop":nOption.getStrutsTileTemplate())%>' flush="true" >
  <tiles:put name="title" value="<%=nOption.getLabel()%>"/>
</tiles:insert>
<div id="desktopIdField" style="display:none;" value='<%= desktopId %>' />
<%  }
  }%>