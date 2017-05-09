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
<%@ page import="org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.*" %>
<%@ page import="org.jaffa.components.maint.IMaintComponent" %>

<Portlet:FoldingSection id='GeneralDetails' label='<%= MessageHelper.findMessage("title.Jaffa.Common.GeneralDetails", null)  %>' closed='false'>
<table width='100%' border='0' cellspacing='0' align='center'>
  <Portlet:Property field='formName'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("formName")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
          <Portlet:Lookup component='Jaffa.Printing.FormGroupLookup' targetFields='formName=formName' dynamicParameters='formName=formName'>&nbsp;</Portlet:Lookup>           
        <% } %>        
      </logic:equal>                      
    </td>    
  </tr>
  </Portlet:Property>

  <Portlet:Property field='formAlternate'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("formAlternate")) { %>
          <Portlet:Text />
        <% } else { %>
    	<Portlet:EditBox />
        <% } %>
      </logic:equal>
    	
    </td>
  </tr>
  </Portlet:Property>
 
  <Portlet:Property field='formVariation'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("navyUnitIdentification")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
        <% } %>
      </logic:equal>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='outputType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("navyUnitIdentification")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
          <Portlet:Lookup component='Jaffa.Printing.PrinterOutputTypeLookup' targetFields='outputType=outputType' dynamicParameters='outputType=outputType'>&nbsp;</Portlet:Lookup>           
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

  <Portlet:Property field='remarks'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='followOnFormName'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />                
	  <Portlet:Lookup component='Jaffa.Printing.FormGroupLookup' targetFields='followOnFormName=formName' dynamicParameters='formName=followOnFormName'>&nbsp;</Portlet:Lookup>                 
    </td>
  </tr>
  </Portlet:Property>


  <Portlet:Property field='followOnFormAlternate'>
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
</Portlet:FoldingSection>   

<Portlet:FoldingSection hideIfNoWidgets='false' id='Template' label='<%= MessageHelper.findMessage("title.Jaffa.Common.Template", null)  %>' closed='false'>
<table width='100%' border='0' cellspacing='0' align='center'>
  <tr> 
    <td class="label">
      <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormTemplate]"/>:
    </td>
    <td>
    <% 
      if( ( (FormDefinitionMaintenanceComponent) ((FormDefinitionMaintenanceForm)TagHelper.getFormBase(pageContext)).getComponent()).getMode() == IMaintComponent.MODE_CREATE ){
    %>
         <span class="editboxMandatoryPrefix">&nbsp;</span>
    <%
     }
    %>     
      <html:file property='file' />

    <% 
      if(((FormDefinitionMaintenanceForm)TagHelper.getFormBase(pageContext)).isUpdateMode()) { 
    %>
      <Portlet:Text field='formTemplate'/>
    <% 
      } 
    %>
      
    </td>
  </tr>


  <tr> 
    <td class="label">
      <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FieldLayout]"/>:
    </td>
    <td>
         <html:file property='layoutFile' />
    <% 
       if(((FormDefinitionMaintenanceForm)TagHelper.getFormBase(pageContext)).isUpdateMode()) { 
    %>
      <Portlet:Text field='fieldLayout'/>
    <% 
      }
    %>
    
    </td>
  </tr>
  </table>
</Portlet:FoldingSection>   


<Portlet:FoldingSection id='DataAccess' label='<%= MessageHelper.findMessage("title.Jaffa.Common.DataAccess", null)  %>' closed='false'>
<table width='100%' border='0' cellspacing='0' align='center'>

  <Portlet:Property field='additionalDataComponent'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domFactory'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>


  <Portlet:Property field='domClass'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey1'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey2'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey3'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey4'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey5'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='domKey6'>
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
</Portlet:FoldingSection>   

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

