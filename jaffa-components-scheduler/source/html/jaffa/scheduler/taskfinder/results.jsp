<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderComponent" %>
<%@ page import="org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderForm" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.security.SecurityManager" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<% boolean hasAccessToQueueViewer = SecurityManager.checkComponentAccess("Jaffa.Messaging.QueueViewer"); %>
<% boolean hasSchedulerTaskMaintenanceDirect = SecurityManager.checkFunctionAccess("Jaffa.Scheduler.Task.MaintenanceDirect"); %>
<table>
  <tr> 
    <td class="label"><Portlet:Label key='[label.Jaffa.Scheduler.SchedulerStatus]'/>:</td>
<%
TaskFinderComponent myComp = ((TaskFinderComponent) TagHelper.getFormBase(pageContext).getComponent());
if (myComp.getError() != null) {
%>
    <td class='mainError'><Portlet:Label key='error.Jaffa.Scheduler.TaskFinder.SchedulerError'/></td>
<% } else { %>
    <td><Portlet:Label key='<%= "label.Jaffa.Scheduler.SchedulerStatus." + ((TaskFinderForm) TagHelper.getFormBase(pageContext)).getSchedulerStatus() %>'/></td>
<% } %>
  </tr>
</table>

<Portlet:UserGrid field="rows" userGridId="jaffa_scheduler_taskFinder" detail="true" target="_blank">
  <%
    java.util.Map map = TagHelper.getModelMap(pageContext);
    Integer failedTaskCount = map != null ? (Integer) map.get("failedTaskCount") : null;
    Boolean hasAdminTaskAccess = map != null ? (Boolean) map.get("hasAdminTaskAccess") : null;
  %>
  <Portlet:Property field='taskType'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.ScheduledTask.TaskType]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='description'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.ScheduledTask.Description]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='nextDue'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Scheduler.ScheduledTask.NextDue]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastRunOn'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Scheduler.ScheduledTask.LastRunOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='runAs'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.ScheduledTask.RunAs]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastError'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.TaskFinder.ScheduledTask.LastError]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='failedTaskCount'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.TaskFinder.ScheduledTask.FailedTaskCount]'>
      <% if (hasAccessToQueueViewer && failedTaskCount != null && failedTaskCount.intValue() > 0) { %>
        <Portlet:Button field='ViewFailedTasks' target='_blank' type='link' label='<%= Formatter.format(failedTaskCount) %>'/>
      <% } else { %>
        <Portlet:Text />
      <% } %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='status'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Scheduler.ScheduledTask.Status]'>
      <Portlet:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Status." + TagHelper.getModelMap(pageContext).get("status") %>'/></td>
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
    <% if (hasAdminTaskAccess != null && hasAdminTaskAccess.booleanValue()) { %>
      <Portlet:Button field="Activate" label="[label.Jaffa.Scheduler.TaskFinder.ScheduledTask.Activate]" image="jaffa/imgs/icons/refresh.gif"/>
      <Portlet:Button field="Inactivate" label="[label.Jaffa.Scheduler.TaskFinder.ScheduledTask.Inactivate]" image="jaffa/imgs/icons/close.gif" confirm='[label.Jaffa.Scheduler.TaskFinder.ScheduledTask.Inactivate.confirm]'/>
    <% } %>
    <Portlet:ComponentGuard name="Jaffa.Scheduler.TaskViewer">
      <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
    </Portlet:ComponentGuard>
    <% if (hasAdminTaskAccess != null && hasAdminTaskAccess.booleanValue() && hasSchedulerTaskMaintenanceDirect) { %>
      <Portlet:ComponentGuard name="Jaffa.Scheduler.TaskMaintenance">
        <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
        <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif" confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
      </Portlet:ComponentGuard>
    <% } %>
  </Portlet:UserGridColumn>
   
</Portlet:UserGrid>
