<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page import="org.jaffa.util.MessageHelper"%>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.components.lookup.LookupComponent2" %>
<%@ page import="org.jaffa.components.lookup.LookupForm" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/defaultNav.jsp' -->
<bean:define id='component' name='<%= TagHelper.getFormName(pageContext) %>' property='component' type='org.jaffa.components.lookup.LookupComponent2'/>
<bean:define id='inMultiSelectLookupMode' name='component' property='inMultiSelectLookupMode' type='java.lang.Boolean'/>
<table class="defaultNav">
  <tr>
    <logic:notEmpty name='defaultNavLinks'>
    <logic:iterate id='defaultNavLink' name='defaultNavLinks'>

    <%-- Default BACK actions --%>
    <logic:equal name='defaultNavLink' value='Close'>
    <td class="backIcon"><Portlet:Button field='Close' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    <td class="backButton"><Portlet:Button field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    </logic:equal>
    
    <logic:equal name='defaultNavLink' value='ModifySearch'>
    <td class="backIcon"><Portlet:Button field='ModifySearch' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.ModifySearch]' preprocess='false'/></td>
    <td class="backButton"><Portlet:Button field='ModifySearch' type='link' label='[label.Jaffa.Widgets.Button.ModifySearch]' preprocess='false'/></td>
    </logic:equal>
    

    <%-- Default FORWARD action --%>
    <logic:equal name='defaultNavLink' value='Search'>
    <td class="forwardButton"><Portlet:Button field='Search' type='link'  label='[label.Jaffa.Widgets.Button.Search]' submit='true' guarded='true'/></td>
    <td class="forwardIcon"><Portlet:Button field='Search' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Search]' guarded='true'/></td>
    </logic:equal>
    
    <logic:equal name='defaultNavLink' value='Select'>
    <logic:equal name='inMultiSelectLookupMode' value='true'>
    <td class="forwardButton"><Portlet:Button  field='MultiSelect' label='[label.Jaffa.Widgets.Button.Select]' type='link' submit='true'/></td>
    <td class="forwardIcon"><Portlet:Button  field='MultiSelect' label='[label.Jaffa.Widgets.Button.Select]' image='jaffa/imgs/icons/nextArrows.gif'/></td>
    </logic:equal>     
    </logic:equal>     

    </logic:iterate>
    </logic:notEmpty>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_lookup_2_0/defaultNav.jsp' -->
