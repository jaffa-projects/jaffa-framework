<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>



<table width='100%' border='0' cellspacing='0' align='center'>
  <Portlet:Property field='formId'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("formId")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
          <Portlet:ComponentGuard name="Jaffa.Printing.FormDefinitionLookup">
            <Portlet:Lookup component='Jaffa.Printing.FormDefinitionLookup' targetFields='formId=formId' bypassCriteriaScreen='true' staticParameters='' dynamicParameters='formId=formId'>&nbsp;</Portlet:Lookup>
          </Portlet:ComponentGuard>
        <% } %>
      </logic:equal>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='templateData'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='layoutData'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

