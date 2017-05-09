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
    <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='RequestId'/>:
    </td>
    <td>
  
        <span class="dropdownOptionalPrefix">&nbsp;</span><Portlet:Text field='requestId' domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' domainField='RequestId'/>
      
    </td>
  </tr>
</logic:equal>
  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='UserName'/>:
    </td>
    <td>
          <Portlet:EditBox field='userName' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='FirstName'/>:
    </td>
    <td>
          <Portlet:EditBox field='firstName' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='LastName'/>:
    </td>
    <td>
          <Portlet:EditBox field='lastName' />
    </td>
  </tr>

   <tr> 
            <td class="label">
                <Portlet:Label key='[label.Jaffa.Admin.User.Password]'/>:
            </td>
            <td>
                        <Portlet:EditBox field='password1' password='true'/>
            </td>
        </tr>

        <tr> 
            <td class="label">
                <Portlet:Label key='[label.Jaffa.Admin.User.VerifyPassword]'/>:
            </td>
            <td>
                        <Portlet:EditBox field='password2' password='true'/>
            </td>
        </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='EMailAddress'/>:
    </td>
    <td>
          <Portlet:EditBox field='EMailAddress' />
    </td>
  </tr>

 <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='SecurityQuestion'/>:
    </td>
    <td>
          <Portlet:DropDown field='securityQuestion1'/>
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='SecurityAnswer'/>:
    </td>
    <td>
          <Portlet:EditBox field='securityAnswer' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='Remarks'/>:
    </td>
    <td>
          <Portlet:EditBox field='remarks' rows='3' />
    </td>
  </tr>
  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='CreatedOn'/>:
    </td>
    <td>
          <span class="dropdownOptionalPrefix">&nbsp;</span><Portlet:Text field='createdOn' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='ProcessedDatetime'/>:
    </td>
    <td>
          <span class="dropdownOptionalPrefix">&nbsp;</span><Portlet:Text field='processedDatetime' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='ProcessedUserId'/>:
    </td>
    <td>
          <span class="dropdownOptionalPrefix">&nbsp;</span><Portlet:Text field='processedUserId' />
    </td>
  </tr>
  </logic:equal>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <tr> 
    <td class="label">
      <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='Status'/>:
    </td>
    <td>
      <Portlet:DropDown field='status'/>
    </td>
  </tr>
  </logic:equal>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

