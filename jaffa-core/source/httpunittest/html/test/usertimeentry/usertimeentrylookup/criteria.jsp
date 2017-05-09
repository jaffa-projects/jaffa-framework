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
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="UserName"/>:
        </td>        <td>
            <Portlet:DropDown field="userNameDd"/>
        </td>
        <td>
            <Portlet:EditBox field="userName"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="ProjectCode"/>:
        </td>        <td>
            <Portlet:DropDown field="projectCodeDd"/>
        </td>
        <td>
            <Portlet:EditBox field="projectCode"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="Activity"/>:
        </td>        <td>
            <Portlet:DropDown field="activityDd"/>
        </td>
        <td>
            <Portlet:EditBox field="activity"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="Task"/>:
        </td>        <td>
            <Portlet:DropDown field="taskDd"/>
        </td>
        <td>
            <Portlet:EditBox field="task"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="PeriodStart"/>:
        </td>        <td>
            <Portlet:DropDown field="periodStartDd"/>
        </td>
        <td>
            <Portlet:EditBox field="periodStart"/>
        </td>
    </tr>
    <tr> 
        <td class="label">
            <Portlet:Label domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" field="PeriodEnd"/>:
        </td>        <td>
            <Portlet:DropDown field="periodEndDd"/>
        </td>
        <td>
            <Portlet:EditBox field="periodEnd"/>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
