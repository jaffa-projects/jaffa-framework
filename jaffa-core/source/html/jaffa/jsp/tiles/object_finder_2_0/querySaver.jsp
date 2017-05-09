<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'QuerySaver' of FinderLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import='org.jaffa.components.finder.FinderComponent2' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.presentation.portlet.FormBase" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/QuerySaver.jsp' -->
<br/>
<br/>
<Portlet:FoldingSection id='savedQueries' key='label.Jaffa.Finder.SavedQueries' closed='true' >

<jsp:include page = "/jaffa/jsp/tiles/object_finder_2_0/querySaverNoFoldingSection.jsp" flush="true"/>

</Portlet:FoldingSection>
<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/QuerySaver.jsp' -->


