<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.modules.scheduler.components.taskmaintenance.ui.TaskMaintenanceForm" %>
<%@ page import="org.jaffa.modules.scheduler.services.ScheduledTask" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>

<j:FoldingSection id='TaskDetails' key='label.Jaffa.Scheduler.TaskMaintenance.TaskDetails'>
  <table>
    <j:Property field='taskType'>
      <tr>
        <td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.TaskType'/>:</td>
        <td><j:Text/></td>
      </tr>
    </j:Property>
    <j:Property field='description'>
      <tr>
        <td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.Description'/>:</td>
        <td><j:Text/></td>
      </tr>
    </j:Property>
    <j:Property field='runAs'>
      <tr>
        <td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.RunAs'/>:</td>
        <td><j:Text/></td>
      </tr>
    </j:Property>
  </table>
</j:FoldingSection>    

<j:FoldingSection id='TriggerDetails' key='label.Jaffa.Scheduler.TaskMaintenance.TriggerDetails'>    
  <table>
    <j:Property field='startOn'>
      <tr>
        <td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.StartOn'/>:</td>
        <td><j:Text/></td>
      </tr>
    </j:Property>
    <j:Property field='endOn'>
      <tr>
        <td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.EndOn'/>:</td>
        <td><j:Text/></td>
      </tr>
    </j:Property>
  </table>
  
  <table>
    <j:Property field='recurrence'>
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.ONCE_ONLY.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.ONCE_ONLY %>'/></td>
      </tr>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.OFTEN.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.OFTEN %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.OFTEN.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <tr>
                <td><%= ((TaskMaintenanceForm) TagHelper.getFormBase(pageContext)).getOftenHours() != null ? Formatter.format(((TaskMaintenanceForm) TagHelper.getFormBase(pageContext)).getOftenHours()) : "0" %> <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Hours'/></td>
                <td><j:DropDown displayOnly='true' field='oftenMinutes'/> <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Minutes'/></td>
                <td><j:DropDown displayOnly='true' field='oftenSeconds'/> <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Seconds'/></td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.DAILY.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.DAILY %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.DAILY.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <tr>
                <td><j:RadioButton displayOnly='true' field='dailyWeekDaysOnly' selectValue='true'/> <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.DAILY.WeekDaysOnly.true'/></td>
                <td><j:RadioButton displayOnly='true' field='dailyWeekDaysOnly' selectValue='false'/> <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.DAILY.WeekDaysOnly.false'/></td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.WEEKLY.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.WEEKLY %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.WEEKLY.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <tr>
                <td><j:DropDown displayOnly='true' field='weeklyFrequency'/></td>
                <td><j:DropDown displayOnly='true' field='weeklyDay'/></td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.MONTHLY.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.MONTHLY %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.MONTHLY.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <logic:equal property='monthlyType' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.MonthlyType.DAY.toString() %>'>
                <tr>
                  <td>
                    <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Day'/>
                    <j:DropDown displayOnly='true' field='monthlyDay'/>
                  </td>
                </tr>
              </logic:equal>
              <logic:equal property='monthlyType' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.MonthlyType.WEEK.toString() %>'>
                <tr>
                  <td>
                    <j:DropDown displayOnly='true' field='monthlyWeekFrequency'/>
                    <j:DropDown displayOnly='true' field='monthlyWeekDay'/>
                  </td>
                </tr>
              </logic:equal>
              <tr><td><j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Months'/>:</td></tr>
              <tr>
                <td>
                  <j:Grid field='monthlyMonths' displayOnly='true'>
                    <j:GridColumn label='Column0'><j:CheckBox displayOnly='true' field='selected0'/><j:Label key='<%= (String) TagHelper.getModelMap(pageContext).get("label0") %>'/></j:GridColumn>
                    <j:GridColumn label='Column1'><j:CheckBox displayOnly='true' field='selected1'/><j:Label key='<%= (String) TagHelper.getModelMap(pageContext).get("label1") %>'/></j:GridColumn>
                    <j:GridColumn label='Column2'><j:CheckBox displayOnly='true' field='selected2'/><j:Label key='<%= (String) TagHelper.getModelMap(pageContext).get("label2") %>'/></j:GridColumn>
                    <j:GridColumn label='Column3'><j:CheckBox displayOnly='true' field='selected3'/><j:Label key='<%= (String) TagHelper.getModelMap(pageContext).get("label3") %>'/></j:GridColumn>
                  </j:Grid>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.YEARLY.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.YEARLY %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.YEARLY.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <tr>
                <td>
                  <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.Every'/>
                  <j:Text field='yearlyFrequency'/>
                  <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.YEARLY.Frequency'/>
                  <j:Label key='label.Jaffa.Scheduler.ScheduledTask.Recurrence.YEARLY.On'/>
                  <j:DropDown displayOnly='true' field='yearlyOn'/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
      
      <tr>
        <td><j:RadioButton displayOnly='true' selectValue='<%= TaskMaintenanceForm.RecurrenceEnum.CUSTOM.toString() %>'/></td>
        <td><j:Label key='<%= "label.Jaffa.Scheduler.ScheduledTask.Recurrence." + TaskMaintenanceForm.RecurrenceEnum.CUSTOM %>'/></td>
      </tr>
      <logic:equal property='recurrence' name='<%= TagHelper.getFormName(pageContext) %>' value='<%= TaskMaintenanceForm.RecurrenceEnum.CUSTOM.toString() %>'>
        <tr>
          <td/>
          <td>
            <table>
              <tr>
                <td><j:Text field='customPattern'/></td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:equal>
    </j:Property>
  </table>
</j:FoldingSection>    

<j:FoldingSection id='RecoveryDetails' key='label.Jaffa.Scheduler.TaskMaintenance.RecoveryDetails'>
  <table>
    <j:Property field='misfireRecovery'>
      <tr><td><j:Label key='label.Jaffa.Scheduler.TaskMaintenance.RecoveryDetails.Question'/></td></tr>
      <tr><td><j:Label key='label.Jaffa.Scheduler.TaskMaintenance.RecoveryDetails.MisfireRecovery'/></td></tr>
      <tr><td><j:RadioButton displayOnly='true' selectValue='<%= ScheduledTask.MisfireRecovery.START_AS_SCHEDULED.toString() %>'/><j:Label key='label.Jaffa.Scheduler.ScheduledTask.MisfireRecovery.START_AS_SCHEDULED'/></td></tr>
      <tr><td><j:RadioButton displayOnly='true' selectValue='<%= ScheduledTask.MisfireRecovery.RUN_LAST_MISSED.toString() %>'/><j:Label key='label.Jaffa.Scheduler.ScheduledTask.MisfireRecovery.RUN_LAST_MISSED'/></td></tr>
    </j:Property>
  </table>
</j:FoldingSection>    

<%-- @TODO - implement custom jsp include as done in JaffaComponentsMessaging\source\html\jaffa\messaging\messageviewer\view.jsp --%>
<j:FoldingSection id='TaskData' key='label.Jaffa.Scheduler.TaskMaintenance.TaskData' closed='true'>
  <j:Property field='serializedBusinessObject'>
    <table class='comments'><tr><td><pre><j:Text/></pre></td></tr></table>
  </j:Property>
</j:FoldingSection>    

<j:FoldingSection id='BusinessEventLog' key='label.Jaffa.Messaging.BusinessEventLog' closed='true'>
  <table>
    <tr>
      <td>
        <j:UserGrid field="relatedBusinessEventLog" userGridId="jaffa_messaging_messageViewer_businessEventLog">
          <j:Property field='loggedOn' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <j:UserGridColumn dataType="Date" label='[label.Jaffa.Messaging.BusinessEventLog.LoggedOn]'>
              <j:Text />
            </j:UserGridColumn>
          </j:Property>
          <j:Property field='messageType' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageType]'>
              <j:Text />
            </j:UserGridColumn>
          </j:Property>
          <j:Property field='messageText' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageText]'>
              <j:Text />
            </j:UserGridColumn>
          </j:Property>
          <j:Property field='processName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.ProcessName]'>
              <j:Text />
            </j:UserGridColumn>
          </j:Property>
          <j:Property field='subProcessName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <j:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.SubProcessName]'>
              <j:Text />
            </j:UserGridColumn>
          </j:Property>
          <j:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <j:ComponentGuard name="Jaffa.Messaging.BusinessEventLogViewer">
              <j:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
            </j:ComponentGuard>
          </j:UserGridColumn>
        </j:UserGrid>
      </td>
    </tr>
    <tr>
      <td>
        <logic:greaterThan name="<%= TagHelper.getFormName(pageContext) %>" property="numberOfRecords" value="0">
          <j:Label key='label.Jaffa.Inquiry.numberOfRecords' arg0='<%= Formatter.format(((TaskMaintenanceForm) TagHelper.getFormBase(pageContext)).getNumberOfRecords()) %>'/>
          <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
            <j:Label key='label.Jaffa.Inquiry.moreRecordsPrefix'/> <j:Button field="MoreRecords" type="link" label="[label.Jaffa.Inquiry.moreRecordsExist]" target="_blank"/> <j:Label key='label.Jaffa.Inquiry.moreRecordsSuffix'/>
          </logic:equal>
        </logic:greaterThan>
      </td>
    </tr>
  </table>
</j:FoldingSection>
