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
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceForm" %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/defaultNav.jsp' -->
<%
FormSelectionMaintenanceForm f = ((FormSelectionMaintenanceForm)TagHelper.getFormBase(pageContext));
%>
<table width="100%" class="defaultNav">
  <tr>
    <%// If directDisplay is the only option, open a new window to display the PDF document.
    if( f.getDirectDisplay() && !(f.getPrinting()||f.getEmail()||f.getWebPublish()) ){%>
      <td width="100%" align="right" valign="middle">
        <Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true' target="_blank"/>
      </td>
      <td align="right" width="1%">
        <Portlet:Button field='Finish' type='image' label='[label.Jaffa.Widgets.Button.Finish]' image="jaffa/imgs/icons/nextArrows.gif" guarded='true' target="_blank"/>
      </td>    
    <%}else{%>
      <td width="100%" align="right" valign="middle">
        <Portlet:Button field='Finish' type='link'  label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'  /></td>
      <td align="right" width="1%">
        <Portlet:Button field='Finish' type='image' label='[label.Jaffa.Widgets.Button.Finish]' image="jaffa/imgs/icons/nextArrows.gif" guarded='true'  /></td>
    <%}%>  
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/defaultNav.jsp' -->
