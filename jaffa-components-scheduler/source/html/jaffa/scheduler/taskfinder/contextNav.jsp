<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of FinderLayout.jsp --%>
<%@ page import="org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderComponent" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/scheduler/taskfinder/contextNav.jsp' -->
<table cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <tr>
    <td valign='bottom' width='100%'>
      <table class='contextNavHeader' width='100%'>
        <tr>

          <td class='contextNavTitle'><Portlet:Label key='title.Jaffa.ContextNav'/></td>
          <td align='right'>
          <a href="javascript:expand('context','contextExpand')" >
          <img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr align='middle'>
    <td>
    <span id="context" syle="display:block">
      <table class='contextNavBody' cellspacing='0' cellpadding='0' width='100%'>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/refresh.gif">
            <Portlet:Button field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' guarded='true' preprocess='false'/>
          </td>
        </tr>
<%
TaskFinderComponent myComp = ((TaskFinderComponent) TagHelper.getFormBase(pageContext).getComponent());
if (myComp.getError() == null) {
%>
        <tr>
          <td>
            <Portlet:FunctionGuard name="Jaffa.Scheduler.Task.MaintenanceDirect">
              <img src="jaffa/imgs/icons/create.gif">
              <Portlet:Button field='CreateFromResults' type='link' label='[label.Jaffa.Widgets.Button.CreateNew]' preprocess='false'/>
            </Portlet:FunctionGuard>
          </td>
        </tr>
        <tr>
          <td>
            <Portlet:FunctionGuard name="Jaffa.Scheduler.SchedulerAdmin">
              <img src="jaffa/imgs/icons/start.gif">
              <Portlet:Button field='StartScheduler' type='link' label='[label.Jaffa.Scheduler.TaskFinder.StartScheduler]' guarded='true' preprocess='false'/>
            </Portlet:FunctionGuard>
          </td>
        </tr>
        <tr>
          <td>
            <Portlet:FunctionGuard name="Jaffa.Scheduler.SchedulerAdmin">
              <img src="jaffa/imgs/icons/stop.gif">
              <Portlet:Button field='StopScheduler' type='link' label='[label.Jaffa.Scheduler.TaskFinder.StopScheduler]' guarded='true' preprocess='false'/>
            </Portlet:FunctionGuard>
          </td>
        </tr>
        <tr>
          <td>
            <Portlet:FunctionGuard name="Jaffa.Scheduler.SchedulerAdmin">
              <img src="jaffa/imgs/icons/pause.gif">
              <Portlet:Button field='PauseScheduler' type='link' label='[label.Jaffa.Scheduler.TaskFinder.PauseScheduler]' guarded='true' preprocess='false' confirm='[label.Jaffa.Scheduler.TaskFinder.PauseScheduler.confirm]'/>
            </Portlet:FunctionGuard>
          </td>
        </tr>
        <tr>
          <td>
            <Portlet:FunctionGuard name="Jaffa.Scheduler.SchedulerAdmin">
              <img src="jaffa/imgs/icons/clear.gif">
              <Portlet:Button field='ClearEventLog' type='link' label='[label.Jaffa.Scheduler.TaskFinder.ClearEventLog]' guarded='true' preprocess='false' confirm='[label.Jaffa.Scheduler.TaskFinder.ClearEventLog.confirm]'/>
            </Portlet:FunctionGuard>
          </td>
        </tr>
<% } %>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/close.gif">
            <Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
          </td>
        </tr>
      </table>
      </span>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/scheduler/taskfinder/contextNav.jsp' -->
