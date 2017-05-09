<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.components.finder.FinderForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.UserGridTag" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_WEB_PAGE %>"/>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_EXCEL %>"/>
</logic:equal>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_XML %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_XML %>"/>
</logic:equal>

<Portlet:UserGrid field="rows" userGridId="user_userRequestFinder" outputType="<%= outputType %>" detail="true" target="_blank">
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.RequestId]" dataType="Number" >
      <Portlet:Text field="requestId" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="RequestId"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.UserName]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="userName" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="UserName"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.FirstName]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="firstName" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="FirstName"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.LastName]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="lastName" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="LastName"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.Password]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="password" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="Password"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.EMailAddress]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="eMailAddress" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="EMailAddress"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.SecurityQuestion]" dataType="Number" >
      <Portlet:Text field="securityQuestion" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="SecurityQuestion"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.SecurityAnswer]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="securityAnswer" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="SecurityAnswer"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.Remarks]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="remarks" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="Remarks"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.CreatedOn]" dataType="Date" >
      <Portlet:Text field="createdOn" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="CreatedOn"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.ProcessedDateTime]" dataType="Date" >
      <Portlet:Text field="processedDatetime" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="ProcessedDatetime"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.ProcessedUserId]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="processedUserId" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="ProcessedUserId"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="[label.Jaffa.User.UserRequest.Status]" dataType="CaseInsensitiveString" >
      <Portlet:Text field="status" domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" domainField="Status"/>
    </Portlet:UserGridColumn>

  <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>">
    <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
      <Portlet:ComponentGuard name="Jaffa.User.UserRequestViewer">
        <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
      </Portlet:ComponentGuard>
      <Portlet:ComponentGuard name="Jaffa.User.UserRequestMaintenance">
        <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
      </Portlet:ComponentGuard>
      <Portlet:ComponentGuard name="Jaffa.User.UserRequestMaintenance">
        <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
      </Portlet:ComponentGuard>
    </Portlet:UserGridColumn>
  </logic:equal>
</Portlet:UserGrid>

<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>">
  <table>
    <tr>
      <td>
        <logic:greaterThan name="<%= TagHelper.getFormName(pageContext) %>" property="numberOfRecords" value="0">
          <Portlet:Label key='label.Jaffa.Inquiry.numberOfRecords' arg0='<%= Formatter.format(((FinderForm) TagHelper.getFormBase(pageContext)).getNumberOfRecords()) %>'/>
          <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
            <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsPrefix'/> <Portlet:Button field="MoreRecords" type="link" label="[label.Jaffa.Inquiry.moreRecordsExist]"/> <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsSuffix'/> 
          </logic:equal>
        </logic:greaterThan>
      </td>
    </tr>
  </table>
</logic:equal>
