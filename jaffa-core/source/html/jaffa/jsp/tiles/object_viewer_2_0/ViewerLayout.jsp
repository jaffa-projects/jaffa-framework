<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of ../MainLayout.jsp --%>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_viewer_2_0/ViewerLayout.jsp' -->
<tiles:useAttribute name='action' classname='String'/>

<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" valign='top' align="left">
  <tr>
    <td valign='top' align="left">
      <table height="100%" class="leftNav" cellspacing="0" cellpadding="0" border="0">
        <tr>
          <td>
            <tiles:useAttribute name='updateComponent' scope='request' ignore='true'/>
            <tiles:useAttribute name='deleteComponent' scope='request' ignore='true'/>
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
      <Portlet:Form action='<%= action %>'>
        <div id="ContextMenuContents" style="display:none">
          <tiles:useAttribute name='contextNavLinks' scope='request' ignore='true'/>
          <tiles:insert attribute='contextNav' ignore='true'/>
        </div>

        <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <%-- This will display the HistoryNav --%>
          <tr>
            <td>
              <tiles:useAttribute name='title' classname='String' ignore='true'/>
              <tiles:insert attribute='historyNav'>
                <tiles:put name='title' value='<%= title %>'/>
              </tiles:insert>
            </td>
          </tr>
          <tr>
            <td>
              <table cellspacing="0" cellpadding="0" border="0">
                <tr>
                  <td>
                    <tiles:insert attribute='body'/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <tiles:useAttribute name='updateComponent' scope='request' ignore='true'/>
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
<!-- End of '/jaffa/jsp/tiles/object_viewer_2_0/ViewerLayout.jsp' -->
