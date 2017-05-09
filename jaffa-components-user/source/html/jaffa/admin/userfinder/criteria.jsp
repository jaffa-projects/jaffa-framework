<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
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
            <Portlet:DropDown field="EMailAddressDd"/>
        </td>
        <td>
            <Portlet:EditBox field="EMailAddress"/>
        </td>
    </tr>

    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.export"/>:</td>
        <td colspan="2">
            <table>
                <tr>
                    <td class="label"><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>"/><A class="checkboxlabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.webPage"/></A></td>
                    <td class="label"><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>"/><A class="checkboxlabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.excel"/></A></td>
                    <td class="label"><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_XML %>"/><A class="checkboxlabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.xml"/></A></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
