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
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.admin.domain.User" field="UserName"/>:
        </td>        <td>
            <Portlet:DropDown field="userNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="userName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.admin.domain.User" field="FirstName"/>:
        </td>        <td>
            <Portlet:DropDown field="firstNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="firstName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.admin.domain.User" field="LastName"/>:
        </td>        <td>
            <Portlet:DropDown field="lastNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="lastName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.admin.domain.User" field="Status"/>:
        </td>        <td colspan="2">
            <Portlet:Grid field="status" displayOnly="true">
                <Portlet:GridColumn label="">
                    <Portlet:CheckBox field="checked"/>
                    <a class=checkboxlabel><%= MessageHelper.replaceTokens(pageContext, (String) TagHelper.getModelMap(pageContext).get("description")) %></a>
                </Portlet:GridColumn>
            </Portlet:Grid>
        </td>
        <td>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.jaffa.modules.admin.domain.User" field="EMailAddress"/>:
        </td>        <td>
            <Portlet:DropDown field="eMailAddressDd"/>
        </td>
        <td>
            <Portlet:EditBox field="eMailAddress"/>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
