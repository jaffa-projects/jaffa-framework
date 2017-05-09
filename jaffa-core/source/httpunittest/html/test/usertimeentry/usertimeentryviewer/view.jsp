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
<%@ page import="org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui.UserTimeEntryViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.UserName]"/>:
            </td>
            <td>
                <Portlet:Text field="userName" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="UserName"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.ProjectCode]"/>:
            </td>
            <td>
                <Portlet:Text field="projectCode" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="ProjectCode"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.Activity]"/>:
            </td>
            <td>
                <Portlet:Text field="activity" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="Activity"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.Task]"/>:
            </td>
            <td>
                <Portlet:Text field="task" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="Task"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.PeriodStart]"/>:
            </td>
            <td>
                <Portlet:Text field="periodStart" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="PeriodStart"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td class="label">
                    <Portlet:Label key="[label.Jaffa.Admin.Time.PeriodEnd]"/>:
            </td>
            <td>
                <Portlet:Text field="periodEnd" domain="org.jaffa.applications.test.modules.time.domain.UserTimeEntry" domainField="PeriodEnd"/>
            </td>
            <td></td>
        </tr>
</table>


