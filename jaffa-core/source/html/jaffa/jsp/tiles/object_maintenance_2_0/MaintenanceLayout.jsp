<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of ../MainLayout.jsp --%>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>

<!-- Start of '/jaffa/jsp/tiles/object_maintenance_2_0/MaintenanceLayout.jsp' -->
<tiles:useAttribute name='action' classname='String'/>
<tiles:useAttribute name='enctype' classname='String' ignore='true'/>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>

<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" valign='top' align="left">
  <tr>
    <td valign='top' align="left">
      <table height="100%" class="leftNav" cellspacing="0" cellpadding="0" border="0">
        <tr>
          <td>
            <div id="ContextMenu" class="ContextMenu"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div id="SectionMenu"></div> <%-- The sectionNav.js file will insert the code here! --%>
          </td>
        </tr>
        <tr height="100%"><td>&nbsp;</td></tr>
        <tr class="leftNavFooter"><td>&nbsp;</td></tr>
      </table>
    </td>
    <td width="100%" valign='top' align="left">
      <Portlet:Form action='<%= action %>' enctype='<%=enctype%>'>
      <%-- Set the currentScreenCounter in the FormBean. This helps maintain the correct state when a link is clicked on the historyNav --%>
      <%
      ((MaintForm) TagHelper.getFormBase(pageContext)).setCurrentScreenCounter(Integer.parseInt(currentScreenCounter));
      %>
    
      <%-- Change the title based on the create/update mode --%>
      <tiles:useAttribute name='title' classname='String' ignore='true'/>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <tiles:useAttribute name='createModeTitle' classname='String' ignore='true'/>
        <%
        if (createModeTitle != null)
            title = createModeTitle;
        %>
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tiles:useAttribute name='updateModeTitle' classname='String' ignore='true'/>
        <%
        if (updateModeTitle != null)
            title = updateModeTitle;
        %>
      </logic:equal>
      <div id="ContextMenuContents" style="display:none">
        <tiles:useAttribute name='contextNavLinks' scope='request' ignore='true'/>
        <tiles:insert attribute='contextNav' ignore='true'/>
      </div>
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
        <%-- This will display the HistoryNav --%>
        <tr>
          <td>
            <tiles:insert attribute='historyNav'>
              <tiles:put name='title' value='<%= title %>'/>
            </tiles:insert>
          </td>
        </tr>
        <tr>
          <td>
            <tiles:insert attribute='body'>
              <tiles:put name='currentScreenCounter' value='<%= currentScreenCounter %>'/>
            </tiles:insert>
          </td>
        </tr>

        <tr>
          <td>
            <tiles:insert attribute='defaultNav' ignore='true'/>
          </td>
        </tr>
      </table>
      <Portlet:RaiseErrors/>
      </Portlet:Form>
    </td>
  </tr>
</table>

<jsp:include page="../sectionNav.jsp"/>
<!-- End of '/jaffa/jsp/tiles/object_maintenance_2_0/MaintenanceLayout.jsp' -->
