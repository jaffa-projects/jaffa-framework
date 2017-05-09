<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of ViewerLayout.jsp --%>
<%@ page import='org.jaffa.presentation.portlet.widgets.taglib.TagHelper' %>
<%@ page import='org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyComponent' %>
<%@ page import='org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyForm' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper"%>



<!-- Start of '/jaffa/admin/checkpolicy/main.jsp' -->
<table>
  <tr>
    <td>
      <Portlet:FoldingSection id="components" key="[label.Jaffa.Admin.CheckPolicy.Components]" hideIfNoWidgets='false'>
        <table>
          <bean:define id="id1" value='<%= Integer.toString(((CheckPolicyForm)TagHelper.getFormBase(pageContext)).getComponentErrorMap().size()) %>'/>
          <logic:equal name="id1" value="0">
            <tr><td>No errors in components.xml</td></tr>
          </logic:equal>
          <logic:iterate id="element1" name='<%=TagHelper.getFormName(pageContext)%>' property="componentErrorMap">
            <bean:define id="val" name="element1" property="value" type='java.util.Collection'/>
            <logic:iterate name='val' id='elementValue1'>
              <tr><td>Component <b><bean:write name="element1" property="key"/></b> has invalid businessfunction <b><bean:write name="elementValue1"/></b> defined.</td></tr>
            </logic:iterate>
          </logic:iterate> 
        </table>
      </Portlet:FoldingSection>
    </td>
  </tr>
  
  <tr>
    <td>
      <Portlet:FoldingSection id="roles" key="[label.Jaffa.Admin.CheckPolicy.Roles]" hideIfNoWidgets='false'>
        <table>
          <bean:define id="id2" value='<%= Integer.toString(((CheckPolicyForm)TagHelper.getFormBase(pageContext)).getRoleErrorMap().size()) %>'/>
          <logic:equal name="id2" value="0">
            <tr><td>No errors in roles.xml</td></tr>
          </logic:equal>
          <logic:iterate id="element2" name='<%=TagHelper.getFormName(pageContext)%>' property="roleErrorMap">
            <bean:define id="val" name="element2" property="value" type='java.util.Collection'/>
            <logic:iterate name='val' id='elementValue2'>
              <tr><td>Role <b><bean:write name="element2" property="key"/></b> has invalid businessfunction <b><bean:write name="elementValue2"/></b> specified.</td></tr>
            </logic:iterate>
          </logic:iterate> 
        </table>
      </Portlet:FoldingSection>
    </td>
  </tr>
  
  <tr>
    <td>
      <Portlet:FoldingSection id="roleBf" key="[label.Jaffa.Admin.CheckPolicy.RoleBF]" hideIfNoWidgets='false'>
        <table>
          <logic:iterate id="element3" name='<%=TagHelper.getFormName(pageContext)%>' property="roleMap">
            <bean:define id="val" name="element3" property="value" type='java.util.Collection'/>
            <logic:iterate name='val' id='elementValue3'>
              <tr><td><b><bean:write name="element3" property="key"/></b> has function <b><bean:write name="elementValue3"/></b></td></tr>
            </logic:iterate>
          </logic:iterate>
        </table>
      </Portlet:FoldingSection>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/admin/checkpolicy/main.jsp' -->
