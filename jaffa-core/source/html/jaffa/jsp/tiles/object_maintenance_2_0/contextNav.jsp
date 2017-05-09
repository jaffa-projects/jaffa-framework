<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of MaintenanceLayout.jsp --%>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.util.Properties" %>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.datatypes.Parser" %>
<%@ page import="org.jaffa.security.SecurityTag" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_maintenance_2_0/contextNav.jsp' -->
<table cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <tr>
    <td valign='bottom' width='100%'>
      <table class='contextNavHeader' width='100%'>
        <tr>
          <td class='contextNavTitle'><Portlet:Label key='title.Jaffa.ContextNav'/></td>
          <td align='right'>
            <a href="javascript:expand('context','contextExpand')" >
            <img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr align='middle'>
    <td>
      <span id="context" syle="display:block">
        <table class='contextNavBody' cellspacing='0' cellpadding='0' width='100%'>
          <tr>
            <td>
              <img src="jaffa/imgs/icons/cancel.gif">
              <Portlet:Button field='Cancel' type='link' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false' confirm='[label.Jaffa.Inquiry.cancel.confirm]'/>
            </td>
          </tr>

          <logic:equal property='saveActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/save.gif">
                <Portlet:Button field='Save' type='link' label='[label.Jaffa.Widgets.Button.Save]' guarded='true'/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='finishActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/finish.gif">
                <Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='refreshActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/refresh.gif">
                <Portlet:Button field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' preprocess='false' guarded='true'/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='deleteActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/delete.gif">
                <Portlet:Button field='Delete' type='link' label='[label.Jaffa.Widgets.Button.Delete]' preprocess='false' guarded='true' confirm='[label.Jaffa.Inquiry.delete.confirm]'/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='clearActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/clear.gif">
                <a href='javascript:resetForm()'><%= MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.Button.Clear]") %></a>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='previousActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/prev.gif">
                <Portlet:Button field='Previous' type='link' label='[label.Jaffa.Widgets.Button.Previous]' preprocess='false' guarded='true'/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal property='nextActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
            <tr>
              <td>
                <img src="jaffa/imgs/icons/next.gif">
                <Portlet:Button field='Next' type='link' label='[label.Jaffa.Widgets.Button.Next]' guarded='true'/>
              </td>
            </tr>
          </logic:equal>

          <%--
            Used to add extra options to the ContextNav of the MaintenanceLayout via the tiles definition.
            Use the attribute 'contextNavLinks' with the following definition for each required link

            field={name};type=(link|image|...);label={...};image={*.jpg);guarded=(true|false);preprocess=(true|false);mode=(both|create|update);target={_blank};confirm={...};componentGuard={...};functionGuard={...}
          --%>
          <logic:iterate id='contextNavLink' name='contextNavLinks'>
            <tr>
              <td>
                <%
                boolean updateMode = ((MaintForm) TagHelper.getFormBase(pageContext)).isUpdateMode();
                Properties p = new Properties();
                p.load(new ByteArrayInputStream(((String) contextNavLink).replace(";","\n").getBytes()));
                String field = p.getProperty("field", "");
                String type = p.getProperty("type", "link");
                String label = p.getProperty("label", "UNDEFINED");
                String image = p.getProperty("image", null);
                boolean guarded = Parser.parseBoolean(p.getProperty("guarded", "false")).booleanValue();
                boolean preprocess = Parser.parseBoolean(p.getProperty("preprocess", "true")).booleanValue();
                String mode = p.getProperty("mode", null);
                String target = p.getProperty("target", "_self");
                String confirm = p.getProperty("confirm", "");
                String componentGuard = p.getProperty("componentGuard", null);
                String functionGuard = p.getProperty("functionGuard", null);
                if ((mode == null || mode.equalsIgnoreCase("both") || (mode.equalsIgnoreCase("create") && !updateMode) || (mode.equalsIgnoreCase("update") && updateMode))
                && (componentGuard == null || SecurityTag.hasComponentAccess(request, componentGuard))
                && (functionGuard == null || SecurityTag.hasFunctionAccess(request, functionGuard))) {
                  if (image!=null && image.length() > 0) { %> <img src='<%=image%>'> <% } %>
                  <Portlet:Button field='<%=field%>' type='<%=type%>' label='<%=label%>' guarded='<%=guarded%>' preprocess='<%=preprocess%>' target='<%=target%>' confirm='<%=confirm%>'/>
                <%}%>
              </td>
            </tr>
          </logic:iterate>

        </table>
      </span>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_maintenance_2_0/contextNav.jsp' -->
