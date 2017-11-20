# jaffa-components-scheduler Public Methods

***
**org.jaffa.modules.scheduler.components.taskfinder.ITaskFinder**

```
public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void clearEventLogs() throws FrameworkException, ApplicationExceptions
public void destroy()
public TaskFinderOutDto find() throws FrameworkException, ApplicationExceptions
public TaskFinderOutDto find(String taskType) throws FrameworkException, ApplicationExceptions
public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void pauseScheduler() throws FrameworkException, ApplicationExceptions
public void startScheduler() throws FrameworkException, ApplicationExceptions
public void stopScheduler() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutDto**

```
public TaskFinderOutRowDto[] getRows()
public SchedulerHelper.SchedulerStatusEnumeration getSchedulerStatus()
public void setRows(TaskFinderOutRowDto[] rows)
public void setSchedulerStatus(SchedulerHelper.SchedulerStatusEnumeration schedulerStatus)
public String toString()
```
***
**org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutRowDto**

```
public Integer getFailedTaskCount()
public Boolean getHasAdminTaskAccess()
public String getLastError()
public void setFailedTaskCount(Integer failedTaskCount)
public void setHasAdminTaskAccess(Boolean hasAdminTaskAccess)
public void setLastError(String lastError)
public String toString()
```
***
**org.jaffa.modules.scheduler.components.taskfinder.tx.TaskFinderTx**

```
public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void clearEventLogs() throws FrameworkException, ApplicationExceptions
public void clearEventLogs(String scheduledTaskId) throws FrameworkException, ApplicationExceptions
public void destroy()
public TaskFinderOutDto find() throws FrameworkException, ApplicationExceptions
public TaskFinderOutDto find(String taskType) throws FrameworkException, ApplicationExceptions
public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void pauseScheduler() throws FrameworkException, ApplicationExceptions
public void startScheduler() throws FrameworkException, ApplicationExceptions
public void stopScheduler() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderAction**

```
public FormKey do_ClearEventLog_Clicked()
public FormKey do_Close_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_PauseScheduler_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Rows_Activate_Clicked(String rowNum)
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Inactivate_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_ViewFailedTasks_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
public FormKey do_StartScheduler_Clicked()
public FormKey do_StopScheduler_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderComponent**

```
public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void clearEventLogs() throws FrameworkException, ApplicationExceptions
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String scheduledTaskId) throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayResults() throws ApplicationExceptions, FrameworkException
public Exception getError()
public TaskFinderOutDto getFinderOutDto()
public FormKey getResultsFormKey()
public String getTaskType()
public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void pauseScheduler() throws FrameworkException, ApplicationExceptions
public void performInquiry() throws ApplicationExceptions, FrameworkException
public void quit()
public void setTaskType(String taskType)
public void startScheduler() throws FrameworkException, ApplicationExceptions
public void stopScheduler() throws FrameworkException, ApplicationExceptions
public FormKey updateObject(java.lang.String scheduledTaskId) throws ApplicationExceptions, FrameworkException
public FormKey viewFailedTasks(String scheduledTaskId, Object businessObject) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String scheduledTaskId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.scheduler.components.taskfinder.ui.TaskFinderForm**

```
public GridModel getRowsWM()
public SchedulerHelper.SchedulerStatusEnumeration getSchedulerStatus()
public void populateRows(GridModel rows)
public void setRowsWV(String value)
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.ITaskMaintenance**

```
public TaskMaintenanceOutDto clearEventLogs(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public TaskMaintenanceOutDto create(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public void delete(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public TaskMaintenanceOutDto retrieve(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public TaskMaintenanceOutDto update(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceDto**

```
public String toString()
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceOutDto**

```
public BusinessEventLogFinderOutDto getBusinessEventLog()
public TaskMaintenanceDto getTaskMaintenanceDto()
public void setBusinessEventLog(BusinessEventLogFinderOutDto businessEventLog)
public void setTaskMaintenanceDto(TaskMaintenanceDto taskMaintenanceDto)
public String toString()
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.tx.TaskMaintenanceTx**

```
public TaskMaintenanceOutDto clearEventLogs(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public TaskMaintenanceOutDto create(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public void delete(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public TaskMaintenanceOutDto retrieve(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
public TaskMaintenanceOutDto update(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.ui.TaskMaintenanceAction**

```
public FormKey do_ClearEventLog_Clicked()
public FormKey do_MoreRecords_Clicked()
public FormKey do_RelatedBusinessEventLog_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.ui.TaskMaintenanceComponent**

```
public void clearEventLogs() throws FrameworkException, ApplicationExceptions
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayBusinessEventLogFinder() throws ApplicationExceptions, FrameworkException
public FormKey displayBusinessEventLogViewer(String logId) throws ApplicationExceptions, FrameworkException
public BusinessEventLogFinderOutDto getBusinessEventLog()
public Object getBusinessObject()
public String getBusinessObjectXML()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getDescription()
public DateTime getEndOn()
public String getLastChangedBy()
public DateTime getLastChangedOn()
public DateTime getLastRunOn()
public ScheduledTask.MisfireRecovery getMisfireRecovery()
public DateTime getNextDue()
public Recurrence getRecurrence()
public String getRunAs()
public String getScheduledTaskId()
public String getSerializedBusinessObject()
public DateTime getStartOn()
public ScheduledTask.TaskStatusEnumeration getStatus()
public TaskMaintenanceDto getTaskMaintenanceDto()
public String getTaskType()
public Collection<String> getTaskTypeCodes()
public void quit()
public void setBusinessObject(Object businessObject)
public void setBusinessObjectXML(String businessObjectXML)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setDescription(String description)
public void setEndOn(DateTime endOn)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedOn(DateTime lastChangedOn)
public void setLastRunOn(DateTime lastRunOn)
public void setMisfireRecovery(ScheduledTask.MisfireRecovery misfireRecovery)
public void setNextDue(DateTime nextDue)
public void setRecurrence(Recurrence recurrence)
public void setRunAs(String runAs)
public void setScheduledTaskId(String scheduledTaskId)
public void setStartOn(DateTime startOn)
public void setStatus(ScheduledTask.TaskStatusEnumeration status)
public void setTaskType(String taskType)
```
***
**org.jaffa.modules.scheduler.components.taskmaintenance.ui.TaskMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public String getCreatedBy()
public SimpleWidgetModel getCreatedByWM()
public DateTime getCreatedOn()
public SimpleWidgetModel getCreatedOnWM()
public String getCustomPattern()
public SimpleWidgetModel getCustomPatternWM()
public Boolean getDailyWeekDaysOnly()
public SimpleWidgetModel getDailyWeekDaysOnlyWM()
public String getDescription()
public SimpleWidgetModel getDescriptionWM()
public DateTime getEndOn()
public SimpleWidgetModel getEndOnWM()
public String getLastChangedBy()
public SimpleWidgetModel getLastChangedByWM()
public DateTime getLastChangedOn()
public SimpleWidgetModel getLastChangedOnWM()
public DateTime getLastRunOn()
public String getMisfireRecovery()
public SimpleWidgetModel getMisfireRecoveryWM()
public Long getMonthlyDay()
public SimpleWidgetModel getMonthlyDayWM()
public GridModel getMonthlyMonthsWM()
public String getMonthlyType()
public SimpleWidgetModel getMonthlyTypeWM()
public String getMonthlyWeekDay()
public SimpleWidgetModel getMonthlyWeekDayWM()
public String getMonthlyWeekFrequency()
public SimpleWidgetModel getMonthlyWeekFrequencyWM()
public boolean getMoreRecordsExist()
public DateTime getNextDue()
public Long getNumberOfRecords()
public Long getOftenHours()
public SimpleWidgetModel getOftenHoursWM()
public Long getOftenMinutes()
public SimpleWidgetModel getOftenMinutesWM()
public Long getOftenSeconds()
public SimpleWidgetModel getOftenSecondsWM()
public String getRecurrence()
public SimpleWidgetModel getRecurrenceWM()
public GridModel getRelatedBusinessEventLogWM()
public String getRunAs()
public SimpleWidgetModel getRunAsWM()
public String getScheduledTaskId()
public SimpleWidgetModel getScheduledTaskIdWM()
public String getSerializedBusinessObject()
public DateTime getStartOn()
public SimpleWidgetModel getStartOnWM()
public String getStatus()
public String getTaskType()
public SimpleWidgetModel getTaskTypeWM()
public String getWeeklyDay()
public SimpleWidgetModel getWeeklyDayWM()
public String getWeeklyFrequency()
public SimpleWidgetModel getWeeklyFrequencyWM()
public Long getYearlyFrequency()
public SimpleWidgetModel getYearlyFrequencyWM()
public String getYearlyOn()
public SimpleWidgetModel getYearlyOnWM()
public void setCreatedByWV(String value)
public void setCreatedOnWV(String value)
public void setCustomPatternWV(String value)
public void setDailyWeekDaysOnlyWV(String value)
public void setDescriptionWV(String value)
public void setEndOnWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOnWV(String value)
public void setMisfireRecoveryWV(String value)
public void setMonthlyDayWV(String value)
public void setMonthlyMonthsWV(String value)
public void setMonthlyTypeWV(String value)
public void setMonthlyWeekDayWV(String value)
public void setMonthlyWeekFrequencyWV(String value)
public void setOftenHoursWV(String value)
public void setOftenMinutesWV(String value)
public void setOftenSecondsWV(String value)
public void setRecurrenceWV(String value)
public void setRelatedBusinessEventLogWV(String value)
public void setRunAsWV(String value)
public void setScheduledTaskIdWV(String value)
public void setStartOnWV(String value)
public void setTaskTypeWV(String value)
public void setWeeklyDayWV(String value)
public void setWeeklyFrequencyWV(String value)
public void setYearlyFrequencyWV(String value)
public void setYearlyOnWV(String value)
```
***
**org.jaffa.modules.scheduler.components.taskviewer.ui.TaskViewerAction**

```
public FormKey do_Close_Clicked()
```
***
**org.jaffa.modules.scheduler.components.taskviewer.ui.TaskViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.scheduler.components.taskviewer.ui.TaskViewerForm**

```
```
