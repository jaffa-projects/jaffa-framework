<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of MaintenanceLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
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
          <td align='right'><a href="javascript:expand('context','contextExpand')" ><img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a></td>
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
            <Portlet:Button  field='Cancel' type='link' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false'/>
          </td>
        </tr>

 

        <logic:equal property='finishActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/finish.gif">
            <Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/>
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


      </table>
      </span>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_maintenance_2_0/contextNav.jsp' -->
