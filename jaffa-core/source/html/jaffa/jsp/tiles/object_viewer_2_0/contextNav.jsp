<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of ViewerLayout.jsp --%>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.util.Properties" %>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import="org.jaffa.datatypes.Parser" %>
<%@ page import="org.jaffa.security.SecurityTag" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_viewer_2_0/contextNav.jsp' -->
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
          <logic:notEmpty name='updateComponent'>
            <tr>
              <td>
                <Portlet:ComponentGuard name="<%= (String) request.getAttribute("updateComponent") %>">
                  <img src="jaffa/imgs/icons/update.gif">
                  <Portlet:Button  field='Update' type='link' label='[label.Jaffa.Widgets.Button.Update]' preprocess='false'/>
                </Portlet:ComponentGuard>
              </td>
            </tr>
          </logic:notEmpty>
          
          <logic:notEmpty name='deleteComponent'>
            <tr>
              <td>
                <Portlet:ComponentGuard name="<%= (String) request.getAttribute("deleteComponent") %>">
                  <img src="jaffa/imgs/icons/delete.gif">
                  <Portlet:Button field='Delete' type='link' label='[label.Jaffa.Widgets.Button.Delete]' preprocess='false' guarded='true' confirm='[label.Jaffa.Inquiry.delete.confirm]'/>
                </Portlet:ComponentGuard>
              </td>
            </tr>
          </logic:notEmpty>
          
          <tr>
            <td>
              <img src="jaffa/imgs/icons/close.gif">
              <Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
            </td>
          </tr>
          
          <%--
            Used to add extra options to the ContextNav of the ViewerLayout via the tiles definition.
            Use the attribute 'contextNavLinks' with the following definition for each required link

            field={name};type=(link|image|...);label={...};image={*.jpg);guarded=(true|false);preprocess=(true|false);target={_blank};confirm={...};componentGuard={...};functionGuard={...}
          --%>
          <logic:iterate id='contextNavLink' name='contextNavLinks'>
            <tr>
              <td>
                <%
                Properties p = new Properties();
                p.load(new ByteArrayInputStream(((String) contextNavLink).replace(";","\n").getBytes()));
                String field = p.getProperty("field", "");
                String type = p.getProperty("type", "link");
                String label = p.getProperty("label", "UNDEFINED");
                String image = p.getProperty("image", null);
                boolean guarded = Parser.parseBoolean(p.getProperty("guarded", "false")).booleanValue();
                boolean preprocess = Parser.parseBoolean(p.getProperty("preprocess", "true")).booleanValue();
                String target = p.getProperty("target", "_self");
                String confirm = p.getProperty("confirm", "");
                String componentGuard = p.getProperty("componentGuard", null);
                String functionGuard = p.getProperty("functionGuard", null);
                if ((componentGuard == null || SecurityTag.hasComponentAccess(request, componentGuard))
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
<!-- End of '/jaffa/jsp/tiles/object_viewer_2_0/contextNav.jsp' -->
