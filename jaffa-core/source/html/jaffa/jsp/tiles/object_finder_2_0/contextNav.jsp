<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of FinderLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/contextNav.jsp' -->
<table cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <logic:notEmpty name='contextNavLinks'>
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
        <logic:iterate id='contextNavLink' name='contextNavLinks'>
        <tr>
          <td>
            <logic:equal name='contextNavLink' value='Clear'>
            <img src="jaffa/imgs/icons/clear.gif">
            <a href='javascript:resetForm()'><%= MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.Button.Clear]") %></a>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='Search'>
            <img src="jaffa/imgs/icons/search.gif">
            <Portlet:Button field='Search' type='link'  label='[label.Jaffa.Widgets.Button.Search]' guarded='true'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='CreateFromCriteria'>
            <Portlet:ComponentGuard name="<%= (String) request.getAttribute("createComponent") %>">
            <img src="jaffa/imgs/icons/create.gif">
            <Portlet:Button field='CreateFromCriteria' type='link' label='[label.Jaffa.Widgets.Button.CreateNew]'/>
            </Portlet:ComponentGuard>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='Close'>
            <img src="jaffa/imgs/icons/close.gif">
            <Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='Refresh'>
            <img src="jaffa/imgs/icons/refresh.gif">
            <Portlet:Button field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' guarded='true' preprocess='false'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='ModifySearch'>
            <img src="jaffa/imgs/icons/searchAgain.gif">
            <Portlet:Button field='ModifySearch' type='link' label='[label.Jaffa.Widgets.Button.ModifySearch]' preprocess='false'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='CreateFromResults'>
            <Portlet:ComponentGuard name="<%= (String) request.getAttribute("createComponent") %>">
            <img src="jaffa/imgs/icons/create.gif">
            <Portlet:Button field='CreateFromResults' type='link' label='[label.Jaffa.Widgets.Button.CreateNew]' preprocess='false'/>
            </Portlet:ComponentGuard>
            </logic:equal>
          </td>
        </tr>
        </logic:iterate>
      </table>
      </span>
    </td>
  </tr>
  </logic:notEmpty>
</table>
<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/contextNav.jsp' -->
