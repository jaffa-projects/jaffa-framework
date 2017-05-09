<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
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
  <Portlet:Property field='printerId'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("printerId")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
        <% } %>
      </logic:equal>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='description'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='siteCode'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
        <Portlet:EditBox />
        <Portlet:Lookup>&nbsp;</Portlet:Lookup>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='locationCode'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='remote'>
  <tr> 
    <td class="label">
      <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Type]"/>:
    </td>
    <td>
      <Portlet:RadioButton selectValue='false'/><Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Type.local]"/>
    </td>
  </tr>
  <Portlet:Property field='realPrinterName'>
  <tr>
    <td></td>
    <td>
       <Portlet:Label/>: <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>
  <Portlet:Property field='printerOption1'>
  <tr> 
    <td></td>
    <td>
      <Portlet:Label/>: <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>
  <Portlet:Property field='printerOption2'>
  <tr> 
    <td></td>
    <td>
      <Portlet:Label/>: <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>
<%-- Currently an extension point. Hide from user
  <tr>
    <td></td>
    <td>
      <Portlet:RadioButton selectValue='true'/><Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Type.remote]"/>
    </td>
  </tr>
--%>
  </Portlet:Property>
  <Portlet:Property field='outputType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:DropDown/>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='scaleToPageSize'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:DropDown/>
    </td>
  </tr>
  </Portlet:Property>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

<SCRIPT type="text/javascript" src="jaffa/printing/printerdefinitionmaintenance/remoteOptions.js"></SCRIPT>
<SCRIPT>
var formId='<%=TagHelper.getFormTag(pageContext).getHtmlIdPrefix()%>';

    // Register a function to the onClick() event of the RadioButtons
    remote = document.getElementById(formId).elements['remoteWV'];    
    for (var i = 0; i < remote.length; i++) {remote[i].onclick=function() {remoteOptions(remote,formId);};}

    // Invoke the functions when the screen is first rendered
    remoteOptions(remote,formId);
</SCRIPT>
