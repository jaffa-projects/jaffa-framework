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
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%@ page import = "org.jaffa.components.finder.FinderForm"%>
<%@ page import = "org.jaffa.components.finder.FinderComponent2"%>
<%@ page import = "org.jaffa.presentation.portlet.widgets.taglib.TagHelper"%>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/FinderLayout.jsp' -->
<tiles:useAttribute name='action' classname='String'/>

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
      <Portlet:Form action='<%= action %>'>
      <%
      // The following ensures that the grid in results.jsp renders correctly in a web-page. Separate Layout JSPs exist to handle other exportTypes.
      Object form = TagHelper.getFormBase(pageContext);
      if (form != null && form instanceof FinderForm && ((FinderForm) form).getComponent() != null && ((FinderForm) form).getComponent() instanceof FinderComponent2)
          ((FinderForm) form).setExportType(FinderComponent2.EXPORT_TYPE_WEB_PAGE);
      %>
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
            <div id="ContextMenuContents" style="display:none">
              <tiles:useAttribute name='createComponent' scope='request' ignore='true'/>
              <tiles:useAttribute name='contextNavLinks' scope='request' ignore='true'/>
              <tiles:insert attribute='contextNav' ignore='true'/>
            </div>
            <table cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td>
                  <%-- This will determine if the Criteria screen is to be displayed --%>
                  <tiles:useAttribute name='body1'/>
                  <logic:notEmpty name='body1'>
                    <tiles:insert attribute='body1'/>
                  </logic:notEmpty>
                </td>
              </tr>
              <tr>
                <td>
                  <%-- This will determine if the Results screen is to be displayed --%>
                  <tiles:useAttribute name='body2'/>
                  <logic:notEmpty name='body2'>
                    <tiles:insert attribute='body2'/>
                  </logic:notEmpty>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <tiles:useAttribute name='createComponent' scope='request' ignore='true'/>
            <tiles:useAttribute name='defaultNavLinks' scope='request' ignore='true'/>
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

<%-- This is a hack to post a form to a new window if the export type is either 'E'xcel or 'X'ml --%>
<script type='text/javascript' src='js/jquery/jquery-1.10.2.min.js'></script>
<script type='text/javascript' src='js/json/json2.js'></script>
<script type='text/javascript' src='jaffa/js/panels/finder.js'></script>

<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/FinderLayout.jsp' -->
