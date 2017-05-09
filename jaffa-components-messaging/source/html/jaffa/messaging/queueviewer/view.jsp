<%@ page language="java" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum" %>
<%@ page import="org.jaffa.modules.messaging.components.queueviewer.dto.HeaderElementDto" %>
<%@ page import="org.jaffa.modules.messaging.components.queueviewer.dto.QueueViewerOutDto" %>
<%@ page import="org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerComponent" %>
<%@ page import="org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j" %>

<table>
  <j:Property field='queue'>
    <tr>
      <td class="label">
        <j:Label key="[label.Jaffa.Messaging.QueueViewer.Queue]"/>:
      </td>
      <td>
        <j:Text/>
      </td>
    </tr>
  </j:Property>

  <j:Property field='filter'>
    <tr>
      <td class="label">
        <j:Label key="[label.Jaffa.Messaging.QueueViewer.Filter]"/>:
      </td>
      <td>
        <j:EditBox/>
      </td>
    </tr>
  </j:Property>

  <tr>
    <td colspan="2">
      <%
      QueueViewerForm myForm = (QueueViewerForm) TagHelper.getFormBase(pageContext);
      QueueViewerComponent myComp = (QueueViewerComponent) myForm.getComponent();
      QueueViewerOutDto outputDto = myComp.getQueueViewerOutDto();
      HeaderElementDto[] headerElements = outputDto != null ? outputDto.getHeaderElements() : null;
      boolean hasAdminAccess = outputDto != null && outputDto.getHasAdminAccess() != null && outputDto.getHasAdminAccess().booleanValue();
      boolean hasResubmitAccess = hasAdminAccess && myComp.getMessageMode() == MessageModeEnum.ERROR;
      if (headerElements != null && headerElements.length > 0) {
      %>
        <j:UserGrid field="relatedQueueHeader" userGridId='<%= "jaffa_messaging_queueViewer_" + myForm.getQueue() %>'>
          <% if (headerElements.length > 0) { %> <j:Property field='header0'><j:UserGridColumn label='<%= headerElements[0].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 1) { %> <j:Property field='header1'><j:UserGridColumn label='<%= headerElements[1].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 2) { %> <j:Property field='header2'><j:UserGridColumn label='<%= headerElements[2].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 3) { %> <j:Property field='header3'><j:UserGridColumn label='<%= headerElements[3].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 4) { %> <j:Property field='header4'><j:UserGridColumn label='<%= headerElements[4].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 5) { %> <j:Property field='header5'><j:UserGridColumn label='<%= headerElements[5].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 6) { %> <j:Property field='header6'><j:UserGridColumn label='<%= headerElements[6].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 7) { %> <j:Property field='header7'><j:UserGridColumn label='<%= headerElements[7].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 8) { %> <j:Property field='header8'><j:UserGridColumn label='<%= headerElements[8].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          <% if (headerElements.length > 9) { %> <j:Property field='header9'><j:UserGridColumn label='<%= headerElements[9].getLabel() %>'><j:Text/></j:UserGridColumn></j:Property> <% } %>
          
          <j:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <j:ComponentGuard name="Jaffa.Messaging.MessageViewer">
              <j:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
            </j:ComponentGuard>
            <% if (hasAdminAccess && (myComp.getMessageMode() != MessageModeEnum.IN_PROCESS 
                  || ((Boolean) TagHelper.getModelMap(pageContext).get("manageable")).booleanValue())) { %>
              <j:Button field='Delete' label='[label.Jaffa.Widgets.Button.Delete]' image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]" />
            <% } %>
            <% if (hasResubmitAccess) { %>
              <j:Button field='ReSubmit' label="[label.Jaffa.Widgets.Button.Resubmit]" image="jaffa/imgs/icons/update.gif" />
            <% } %>
          </j:UserGridColumn>
        </j:UserGrid>
      <% } else { %>
        <j:Label key='label.Jaffa.Messaging.QueueViewer.DisplayParamNotDefined' arg0='<%= myComp.getQueue() %>'/>
      <% } %>
    </td>
  </tr>
</table>

<%-- Workaround for the Jaffa bug related to the 'enter' key in screens having a single EditBox --%>
<span id="hiddenTextBox" name="hiddenTextBox" style="display:none">
  <input type="text" name="tempTextBox">
</span>
