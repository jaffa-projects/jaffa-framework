<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.components.finder.FinderForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.UserGridTag" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ page import="org.jaffa.modules.messaging.components.businesseventlogfinder.ui.BusinessEventLogFinderComponent" %>

<%!
private String checkLabelExistence(PageContext pageContext, String key) {
    String label = MessageHelper.findMessage(pageContext, key, null);
    if (label == null || label.startsWith("?"))
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

String userGridId = "jaffa_messaging_businessEventLogFinder";
if (retrieveInputCorrelationTypeOnly)
  userGridId = "jaffa_messaging_businessEventLogFinder_" + correlationType.replaceAll(" ", "_");

%>

<bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_WEB_PAGE %>"/>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_EXCEL %>"/>
</logic:equal>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_XML %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_XML %>"/>
</logic:equal>

<Portlet:UserGrid field="rows" userGridId="<%=userGridId%>" outputType="<%= outputType %>" detail="true" target="_blank">

 <% if (!retrieveInputCorrelationTypeOnly) { %>
  <Portlet:Property field='correlationType' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.CorrelationType]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
 <% } else { %>
  <Portlet:Property field='correlationType' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>

    <% if (checkLabelExistence(pageContext, correlationTypeLabel) != null) { %>
      <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%= '[' + correlationTypeLabel + ']' %>'>
        <Portlet:Text />
      </Portlet:UserGridColumn>
    <% } else { %>
      <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.CorrelationType]'>
        <Portlet:Text />
      </Portlet:UserGridColumn>
    <% } %>

  </Portlet:Property>
 <% } %>

 <% if (!retrieveInputCorrelationTypeOnly) { %>
  <Portlet:Property field='correlationKey1' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey1]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
 <% } else if (correlationKey1Label != null)  { %>
  <Portlet:Property field='correlationKey1' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%= '[' + correlationKey1Label + ']' %>'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
 <% } %>

 <% if (!retrieveInputCorrelationTypeOnly) { %>
  <Portlet:Property field='correlationKey2' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey2]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <% } else if (correlationKey2Label != null) { %>
   <Portlet:Property field='correlationKey2' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
     <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%= '[' + correlationKey2Label + ']' %>'>
       <Portlet:Text />
     </Portlet:UserGridColumn>
   </Portlet:Property>
  <% } %>


 <% if (!retrieveInputCorrelationTypeOnly) { %>
  <Portlet:Property field='correlationKey3' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey3]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
 <% } else if (correlationKey3Label != null) { %>
  <Portlet:Property field='correlationKey3' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%= '[' + correlationKey3Label + ']' %>'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
 <% } %>

  <Portlet:Property field='loggedOn' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Messaging.BusinessEventLog.LoggedOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='loggedBy' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.LoggedBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='processName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.ProcessName]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='subProcessName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.SubProcessName]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='messageType' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageType]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='messageText' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageText]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='sourceClass' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.SourceClass]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='sourceMethod' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.SourceMethod]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='sourceLine' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="Number" label='[label.Jaffa.Messaging.BusinessEventLog.SourceLine]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>">
    <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
      <Portlet:ComponentGuard name="Jaffa.Messaging.BusinessEventLogViewer">
        <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
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
