<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'defaultNav' of MaintenanceLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_maintenance_2_0/defaultNav.jsp' -->
<table width="100%" class="defaultNav">
  <tr>

    <%-- Default BACK actions --%>
    <logic:equal property='previousActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
    <td align="left" width="1%"><Portlet:Button field='Previous' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Previous]' guarded='true'/></td>
    <td align="left" valign="middle"><Portlet:Button field='Previous' type='link' label='[label.Jaffa.Widgets.Button.Previous]' guarded='true'/></td>
    </logic:equal>
    
    <logic:equal property='previousActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
    <td align="left" width="1%"><Portlet:Button field='Cancel' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false'/></td>
    <td align="left" valign="middle"><Portlet:Button  field='Cancel' type='link' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false'/></td>
    </logic:equal>
    
    <%-- Default FORWARD action --%>
    <logic:equal property='nextActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
    <td style="width:100%; text-align: right;" valign="middle"><Portlet:Button field='Next' type='link' label='[label.Jaffa.Widgets.Button.Next]' guarded='true' submit='true'/></td>
    <td width="1%"><Portlet:Button field='Next' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Next]' guarded='true'/></td>
    </logic:equal>

    <logic:equal property='nextActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
    <td style="width:100%; text-align: right;" valign="middle"><Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true' submit='true'/></td>
    <td width="1%"><Portlet:Button field='Finish' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/></td>
    </logic:equal>

  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_maintenance_2_0/defaultNav.jsp' -->
