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
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceForm" %>

<!-- Start of 'jaffa\printing\formselectionmaintenance\contextNav.jsp' -->
<%
FormSelectionMaintenanceForm f = ((FormSelectionMaintenanceForm)TagHelper.getFormBase(pageContext));
%>    
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
            
            <logic:equal name='contextNavLink' value='Search'>
            <img src="jaffa/imgs/icons/search.gif">
            <Portlet:Button field='Search' type='link'  label='[label.Jaffa.Widgets.Button.Search]' guarded='true'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='Close'>
            <img src="jaffa/imgs/icons/close.gif">
            <Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='CancelResult'>
            <img src="jaffa/imgs/icons/cancel.gif">
            <Portlet:Button  field='CancelResult' type='link' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false' confirm='[label.Jaffa.Inquiry.cancel.confirm]'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='Refresh'>
            <img src="jaffa/imgs/icons/refresh.gif">
            <Portlet:Button field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]'/>
            </logic:equal>
            
            <logic:equal name='contextNavLink' value='ModifySearch'>
            <img src="jaffa/imgs/icons/searchAgain.gif">
            <Portlet:Button field='ModifySearch' type='link' label='[label.Jaffa.Widgets.Button.ModifySearch]'/>
            </logic:equal>
            
            <%if(f.getPrinting() || f.getEmail() || f.getWebPublish()) {%>       
	       <logic:equal name='contextNavLink' value='Print'>
	          <img src="jaffa/imgs/icons/printer.gif">
	          <Portlet:Button  field='Print' type='link' label='[label.Jaffa.Printing.FormSelection.Print]'/>
	       </logic:equal>
            <%}%>     
            
            <%if( f.getDirectDisplay() &&!(f.getPrinting()||f.getEmail()||f.getWebPublish()) ) {%>
              <logic:equal name='contextNavLink' value='Finish'>
              <img src="jaffa/imgs/icons/finish.gif">
              <Portlet:Button  field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' target="_blank"/>
              </logic:equal>
            <%}else{%>
              <logic:equal name='contextNavLink' value='Finish'>
	      <img src="jaffa/imgs/icons/finish.gif">
	      <Portlet:Button  field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]'/>
              </logic:equal>
            <%}%> 
            
            <%-- Preview is available and DirectDisplay is enabled and not the only option --%>
            <%if(  f.getDirectDisplay() && (f.getPrinting()||f.getEmail()||f.getWebPublish()) ) {%>
              <logic:equal name='contextNavLink' value='Preview'>
              <img src="jaffa/imgs/icons/viewTransIcon.gif">
              <Portlet:Button  field='Preview' type='link' label='[label.Jaffa.Printing.FormSelection.Preview]' target="_blank"/>
            </logic:equal>
            <%}%>
            
          </td>
        </tr>
        </logic:iterate>
      </table>
    </td>
  </tr>
  </logic:notEmpty>
</table>
<!-- End of 'jaffa\printing\formselectionmaintenance\contextNav.jsp' -->
