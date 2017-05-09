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
<%@ page import="org.jaffa.modules.messaging.components.businesseventlogfinder.ui.BusinessEventLogFinderComponent" %>

<%!
private String checkLabelExistence(PageContext pageContext, String key) {
    String label = MessageHelper.findMessage(pageContext, key, null);
    if (label == null || label.startsWith("???"))
        return null;
    else
        return key;
}
%>

<%
boolean retrieveInputCorrelationTypeOnly = false;
BusinessEventLogFinderComponent myComp = (BusinessEventLogFinderComponent) TagHelper.getFormBase(pageContext).getComponent();

if (myComp.getRetrieveInputCorrelationTypeOnly())
    retrieveInputCorrelationTypeOnly = true;

String correlationType = myComp.getCorrelationType();
String correlationTypeLabel = "label.Jaffa.Messaging.BusinessEventLog.CorrelationType." +  correlationType;
String correlationKey1Label = checkLabelExistence(pageContext, correlationTypeLabel + ".CorrelationKey1");
String correlationKey2Label = checkLabelExistence(pageContext, correlationTypeLabel + ".CorrelationKey2");
String correlationKey3Label = checkLabelExistence(pageContext, correlationTypeLabel + ".CorrelationKey3");
%>

<table>
    <Portlet:Property field='correlationType'>
    <tr> 
      <% if (!retrieveInputCorrelationTypeOnly) { %>
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationTypeDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } else { %>
        <td class='label'>
           <% if (checkLabelExistence(pageContext, correlationTypeLabel) != null) { %>
              <Portlet:Label key='<%= correlationTypeLabel %>'/>:
           <% } else { %>
              <Portlet:Label/>:
           <% } %>
        </td>
        <td>
            <Portlet:Text field="correlationTypeDd"/>
        </td>
        <td>
            <Portlet:Text/>
        </td>
      <% } %>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='correlationKey1'>
    <tr> 
      <% if (!retrieveInputCorrelationTypeOnly) { %>
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey1Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } else if (correlationKey1Label != null) { %>
        <td class='label'>
            <Portlet:Label key='<%= correlationKey1Label %>'/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey1Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } %>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='correlationKey2'>
    <tr> 
      <% if (!retrieveInputCorrelationTypeOnly) { %>
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey2Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } else if (correlationKey2Label != null) { %>
        <td class='label'>
            <Portlet:Label key='<%= correlationKey2Label %>'/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey2Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } %>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='correlationKey3'>
    <tr> 
      <% if (!retrieveInputCorrelationTypeOnly) { %>
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey3Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } else if (correlationKey3Label != null) { %>
        <td class='label'>
            <Portlet:Label key='<%= correlationKey3Label %>'/>:
        </td>
        <td>
            <Portlet:DropDown field="correlationKey3Dd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
      <% } %>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='loggedOn'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="loggedOnDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
            <Portlet:Calendar/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='loggedBy'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="loggedByDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='processName'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="processNameDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='subProcessName'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="subProcessNameDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='messageType'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="messageTypeDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='messageText'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="messageTextDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='sourceClass'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="sourceClassDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <tr>
        <td class='label'><Portlet:Label key="label.Jaffa.Inquiry.sort"/>:</td>
        <td colspan="2">
            <Portlet:DropDown field="sortDropDown">
                <Portlet:DropDownOption label='<%= org.jaffa.modules.messaging.domain.BusinessEventLogMeta.META_LOGGED_ON.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortAscending]" %>' value='LoggedOn'/>
                <Portlet:DropDownOption label='<%= org.jaffa.modules.messaging.domain.BusinessEventLogMeta.META_LOGGED_ON.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortDescending]" %>' value='LoggedOn desc'/>
            </Portlet:DropDown>
        </td>
    </tr>
    <tr>
        <td class='label'><Portlet:Label key="label.Jaffa.Inquiry.export"/>:</td>
        <td colspan="2">
            <table>
                <tr>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.webPage"/></span></td>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.excel"/></span></td>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_XML %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.xml"/></span></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
