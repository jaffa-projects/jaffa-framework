<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --

  -- The following customizations have been made                   --
  --   Field 'outputType' is being treated as a key field. It'll be non-editable in update mode --
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
  <Portlet:Property field='outputType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text/>
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("outputType")) { %>
          <Portlet:Text/>
        <% } else { %>
          <Portlet:EditBox/>
          <Portlet:Lookup component='Jaffa.Printing.PrinterOutputTypeLookup' targetFields='outputType=outputType' bypassCriteriaScreen='true' staticParameters='' dynamicParameters='outputType=outputType'>&nbsp;</Portlet:Lookup>
        <% } %>
      </logic:equal>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='sequenceNo'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='osPattern'>
  <tr> 
    <td class="label">
      <Portlet:Label key='[label.Jaffa.Printing.CurrentOsName]'/>:
    </td>
    <td>
      <%=StringHelper.convertToHTML( System.getProperty("os.name") )%>
    </td>
  </tr>
  <tr> 
    <td class="label">
      <Portlet:Label key='[label.Jaffa.Printing.OutputCommand.OsPattern]'/>:
    </td>
    <td>
      <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='commandLine'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
       <Portlet:EditBox />
    </td>
  </tr>
  <tr> 
    <td class="label">
    </td>
    <td>
      <Portlet:Label key='label.Jaffa.Printing.OutputCommand.Parameters'/>
    </td>
  </tr>
  </Portlet:Property>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

