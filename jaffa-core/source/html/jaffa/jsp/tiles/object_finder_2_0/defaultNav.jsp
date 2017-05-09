<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'defaultNav' of FinderLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/defaultNav.jsp' -->
<table class="defaultNav">
  <tr>
    <logic:notEmpty name='defaultNavLinks'>
    <logic:iterate id='defaultNavLink' name='defaultNavLinks'>

    <%-- Default BACK actions --%>
    <logic:equal name='defaultNavLink' value='Close'>
    <td class="backIcon"><Portlet:Button field='Close' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    <td class="backButton"><Portlet:Button field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    </logic:equal>
    
    <logic:equal name='defaultNavLink' value='ModifySearch'>
    <td class="backIcon"><Portlet:Button field='ModifySearch' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.ModifySearch]' preprocess='false'/></td>
    <td class="backButton"><Portlet:Button field='ModifySearch' type='link' label='[label.Jaffa.Widgets.Button.ModifySearch]' preprocess='false'/></td>
    </logic:equal>
    

    <%-- Default FORWARD action --%>
    <logic:equal name='defaultNavLink' value='Search'>
    <td class="forwardButton"><Portlet:Button field='Search' type='link'  label='[label.Jaffa.Widgets.Button.Search]' submit='true' guarded='true'/></td>
    <td class="forwardIcon"><Portlet:Button field='Search' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Search]' guarded='true'/></td>
    </logic:equal>

    </logic:iterate>
    </logic:notEmpty>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/defaultNav.jsp' -->


