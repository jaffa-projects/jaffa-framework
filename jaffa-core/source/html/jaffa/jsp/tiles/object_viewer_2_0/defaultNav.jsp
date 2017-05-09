<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'defaultNav' of ViewerLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_viewer_2_0/defaultNav.jsp' -->
<table class="defaultNav">
  <tr>

    <%-- Default BACK actions --%>
    <td class="backIcon"><Portlet:Button field='Close' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    <td class="backButton"><Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    
    <%-- Default FORWARD action --%>

  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_viewer_2_0/defaultNav.jsp' -->
