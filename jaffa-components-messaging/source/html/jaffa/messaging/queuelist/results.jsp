<%@ page language="java" %>
<%@ page import="org.jaffa.modules.messaging.components.queuelist.ui.QueueListComponent" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j" %>

<%
QueueListComponent myComp = ((QueueListComponent) TagHelper.getFormBase(pageContext).getComponent());
if (myComp.getError() != null) {
%>
  <table><tr><td class='mainError'><j:Label key='[error.Jaffa.Messaging.QueueList.QueueError]'/></td></tr></table>
<% } %>

<j:UserGrid field="rows" userGridId="jaffa_messaging_queueList">
  <j:Property field='queue' propertyClass='org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto'>
    <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.QueueList.Queue]'>
      <j:Text/>
    </j:UserGridColumn>
  </j:Property>
  <j:Property field='pending' propertyClass='org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto'>
    <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.QueueList.Pending]'>
      <% if (((Long) TagHelper.getModelMap(pageContext).get("pending")).intValue() > 0) { %>
        <j:Button type="link" field="ViewPendingMessages" label="<%=Formatter.format(TagHelper.getModelMap(pageContext).get("pending"))%>"/>
      <% } else { %>
      <j:Text/>
      <% } %>
    </j:UserGridColumn>
  </j:Property>
  <j:Property field='error' propertyClass='org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto'>
    <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.QueueList.Error]'>
      <% if (((Long) TagHelper.getModelMap(pageContext).get("error")).intValue() > 0) { %>    
        <j:Button type="link" field="ViewErrorMessages" label="<%=Formatter.format(TagHelper.getModelMap(pageContext).get("error"))%>"/>
      <% } else { %>
        <j:Text/>
      <% } %>
    </j:UserGridColumn>
  </j:Property>
  <j:Property field='inProcess' propertyClass='org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto'>
    <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.QueueList.InProcess]'>
      <% if (((Long) TagHelper.getModelMap(pageContext).get("inProcess")).intValue() > 0) { %>    
        <j:Button type="link" field="ViewInProcessMessages" label="<%=Formatter.format(TagHelper.getModelMap(pageContext).get("inProcess"))%>"/>
      <% } else { %>
        <j:Text/>
      <% } %>
    </j:UserGridColumn>
  </j:Property>
  <j:Property field='activeConsumer' propertyClass='org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto'>
    <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.QueueList.ActiveConsumer]'>
      <j:Text/>
    </j:UserGridColumn>
  </j:Property>
</j:UserGrid>