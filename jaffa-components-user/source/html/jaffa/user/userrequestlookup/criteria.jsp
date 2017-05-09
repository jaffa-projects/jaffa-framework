<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="RequestId"/>:
        </td>        <td>
            <Portlet:DropDown field="requestIdDd"/>
        </td>
        <td>
            <Portlet:EditBox field="requestId"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="UserName"/>:
        </td>        <td>
            <Portlet:DropDown field="userNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="userName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="FirstName"/>:
        </td>        <td>
            <Portlet:DropDown field="firstNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="firstName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="LastName"/>:
        </td>        <td>
            <Portlet:DropDown field="lastNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="lastName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="Password"/>:
        </td>        <td>
            <Portlet:DropDown field="passwordDd"/>
        </td>
        <td>
            <Portlet:EditBox field="password"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="EMailAddress"/>:
        </td>        <td>
            <Portlet:DropDown field="eMailAddressDd"/>
        </td>
        <td>
            <Portlet:EditBox field="eMailAddress"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="SecurityQuestion"/>:
        </td>        <td>
            <Portlet:DropDown field="securityQuestionDd"/>
        </td>
        <td>
            <Portlet:EditBox field="securityQuestion"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="SecurityAnswer"/>:
        </td>        <td>
            <Portlet:DropDown field="securityAnswerDd"/>
        </td>
        <td>
            <Portlet:EditBox field="securityAnswer"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="Remarks"/>:
        </td>        <td>
            <Portlet:DropDown field="remarksDd"/>
        </td>
        <td>
            <Portlet:EditBox field="remarks"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="CreatedOn"/>:
        </td>        <td>
            <Portlet:DropDown field="createdOnDd"/>
        </td>
        <td>
            <Portlet:EditBox field="createdOn"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="ProcessedDatetime"/>:
        </td>        <td>
            <Portlet:DropDown field="processedDatetimeDd"/>
        </td>
        <td>
            <Portlet:EditBox field="processedDatetime"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="ProcessedUserId"/>:
        </td>        <td>
            <Portlet:DropDown field="processedUserIdDd"/>
        </td>
        <td>
            <Portlet:EditBox field="processedUserId"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.user.domain.UserRequest" field="Status"/>:
        </td>        <td>
            <Portlet:DropDown field="statusDd"/>
        </td>
        <td>
            <Portlet:EditBox field="status"/>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
