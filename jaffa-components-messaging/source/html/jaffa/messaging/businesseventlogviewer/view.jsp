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
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ page import="org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerForm" %>

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

String correlationType = ((BusinessEventLogViewerForm) TagHelper.getFormBase(pageContext)).getCorrelationType();

String correlationTypeLabel = checkLabelExistence(pageContext, "label.Jaffa.Messaging.BusinessEventLog.CorrelationType." +  correlationType);
String correlationKey1Label = checkLabelExistence(pageContext, "label.Jaffa.Messaging.BusinessEventLog.CorrelationType." +  correlationType + ".CorrelationKey1");
String correlationKey2Label = checkLabelExistence(pageContext, "label.Jaffa.Messaging.BusinessEventLog.CorrelationType." +  correlationType + ".CorrelationKey2");
String correlationKey3Label = checkLabelExistence(pageContext, "label.Jaffa.Messaging.BusinessEventLog.CorrelationType." +  correlationType + ".CorrelationKey3");

%>

<table>
  <Portlet:Property field='logId'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.LogId]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='correlationType'>
    <tr>
      <td class="label">
<% if (correlationTypeLabel== null)  { %>
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.CorrelationType]"/>:
<% } else { %>
        <Portlet:Label key="<%= correlationTypeLabel %>"/>:
<% } %>
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='correlationKey1'>
    <tr>
<% if (correlationType == null || correlationTypeLabel == null)  { %>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey1]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } else if (correlationKey1Label!= null)  { %>
      <td class="label">
        <Portlet:Label key="<%= correlationKey1Label %>"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } %>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='correlationKey2'>
    <tr>
<% if (correlationType == null || correlationTypeLabel == null)  { %>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey2]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } else if (correlationKey2Label!= null)  { %>
      <td class="label">
        <Portlet:Label key="<%= correlationKey2Label %>"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } %>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='correlationKey3'>
    <tr>
<% if (correlationType == null || correlationTypeLabel == null)  { %>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey3]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } else if (correlationKey3Label!= null)  { %>
      <td class="label">
        <Portlet:Label key="<%= correlationKey3Label %>"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
<% } %>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='scheduledTaskId'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.ScheduledTaskId]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='messageId'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.MessageId]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='loggedOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.LoggedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='loggedBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.LoggedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='processName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.ProcessName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='subProcessName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.SubProcessName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='messageType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.MessageType]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='messageText'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.MessageText]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='sourceClass'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.SourceClass]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='sourceMethod'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.SourceMethod]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='sourceLine'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Messaging.BusinessEventLog.SourceLine]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='stackTrace'>
    <tr>
      <td colspan='3'>
        <Portlet:FoldingSection id='stackTrace' key="[label.Jaffa.Messaging.BusinessEventLog.StackTrace]" closed='true'>
          <table class='comments'><tr><td><Portlet:Text/></td></tr></table>
        </Portlet:FoldingSection>
      </td>
    </tr>
  </Portlet:Property>
  <tr>
    <td colspan='3'>
      <Portlet:FoldingSection id="messaging_businessEventLogViewer_attachment" label="<%=org.jaffa.util.MessageHelper.replaceTokens(pageContext,org.jaffa.components.attachment.domain.AttachmentMeta.getLabelToken())%>">
        <Portlet:UserGrid field="relatedAttachment" userGridId="jaffa_messaging_businessEventLogViewer_attachment" detail="true" target="_blank">
            <%
              java.util.Map map = TagHelper.getModelMap(pageContext);
              String originalFileName = map != null ? (String) map.get("originalFileName") : null;
              String attachmentType = map != null ? (String) map.get("attachmentType") : null;
              byte[] data = map != null ? (byte[]) map.get("data") : null;
            %>
            <Portlet:Property field='originalFileName'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.OriginalFileName]'>
                <% if (originalFileName != null && "W".equals(attachmentType)) { %>
                  <a href='<%= originalFileName %>' target='_blank'><%= originalFileName %></a>
                <% } else if (originalFileName != null && "L".equals(attachmentType)) { %>
                  <%-- By default, the browsers do not allow a webpage to access the local filesystem. Hence will display the filename only --%>
                  <%-- <a href='<%= new java.io.File(originalFileName).toURI().toURL().toString() %>' target='_blank'><%= originalFileName %></a> --%>
                  <Portlet:Text />
                <% } else if (originalFileName != null && "E".equals(attachmentType) && data != null && data.length > 0) { %>
                  <Portlet:Button field='ViewAttachmentData' target='_top' type='link' label='<%= originalFileName %>'/>
                <% } else { %>
                  <Portlet:Text />
                <% } %>
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='attachmentType'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.AttachmentType]'>
                <% if (attachmentType != null) { %>
                  <Portlet:Label key="<%= "label.Jaffa.Attachment.Attachment.AttachmentType." + attachmentType %>"/>
                <% } %>
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='contentType'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.ContentType]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='description'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.Description]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='remarks'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.Remarks]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='supercededBy'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.SupercededBy]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='createdOn'>
              <Portlet:UserGridColumn dataType="Date" label='[label.Common.CreatedOn]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='createdBy'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Common.CreatedBy]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='lastChangedOn'>
              <Portlet:UserGridColumn dataType="Date" label='[label.Common.LastChangedOn]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
            <Portlet:Property field='lastChangedBy'>
              <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Common.LastChangedBy]'>
                <Portlet:Text />
              </Portlet:UserGridColumn>
            </Portlet:Property>
          <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <Portlet:ComponentGuard name="Jaffa.Attachment.AttachmentViewer">
              <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
            </Portlet:ComponentGuard>
          </Portlet:UserGridColumn>
        </Portlet:UserGrid>
      </Portlet:FoldingSection>
    </td>
  </tr>
</table>
