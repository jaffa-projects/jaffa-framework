# jaffa-components-messaging Public Methods

***
**org.jaffa.modules.messaging.components.businesseventlogfinder.IBusinessEventLogFinder**

```
public void destroy()
public BusinessEventLogFinderOutDto find(BusinessEventLogFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto**

```
public StringCriteriaField getCorrelationKey1()
public StringCriteriaField getCorrelationKey2()
public StringCriteriaField getCorrelationKey3()
public StringCriteriaField getCorrelationType()
public HeaderDto getHeaderDto()
public StringCriteriaField getLogId()
public StringCriteriaField getLoggedBy()
public DateTimeCriteriaField getLoggedOn()
public StringCriteriaField getMessageId()
public StringCriteriaField getMessageText()
public StringCriteriaField getMessageType()
public StringCriteriaField getProcessName()
public StringCriteriaField getScheduledTaskId()
public StringCriteriaField getSourceClass()
public IntegerCriteriaField getSourceLine()
public StringCriteriaField getSourceMethod()
public StringCriteriaField getStackTrace()
public StringCriteriaField getSubProcessName()
public void setCorrelationKey1(StringCriteriaField correlationKey1)
public void setCorrelationKey2(StringCriteriaField correlationKey2)
public void setCorrelationKey3(StringCriteriaField correlationKey3)
public void setCorrelationType(StringCriteriaField correlationType)
public void setHeaderDto(HeaderDto headerDto)
public void setLogId(StringCriteriaField logId)
public void setLoggedBy(StringCriteriaField loggedBy)
public void setLoggedOn(DateTimeCriteriaField loggedOn)
public void setMessageId(StringCriteriaField messageId)
public void setMessageText(StringCriteriaField messageText)
public void setMessageType(StringCriteriaField messageType)
public void setProcessName(StringCriteriaField processName)
public void setScheduledTaskId(StringCriteriaField scheduledTaskId)
public void setSourceClass(StringCriteriaField sourceClass)
public void setSourceLine(IntegerCriteriaField sourceLine)
public void setSourceMethod(StringCriteriaField sourceMethod)
public void setStackTrace(StringCriteriaField stackTrace)
public void setSubProcessName(StringCriteriaField subProcessName)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto**

```
public void addRows(BusinessEventLogFinderOutRowDto rows)
public void clearRows()
public BusinessEventLogFinderOutRowDto getRows(int index)
public BusinessEventLogFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(BusinessEventLogFinderOutRowDto rows)
public void setRows(BusinessEventLogFinderOutRowDto rows, int index)
public void setRows(BusinessEventLogFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto**

```
public java.lang.String getCorrelationKey1()
public java.lang.String getCorrelationKey2()
public java.lang.String getCorrelationKey3()
public java.lang.String getCorrelationType()
public java.lang.String getLogId()
public java.lang.String getLoggedBy()
public org.jaffa.datatypes.DateTime getLoggedOn()
public java.lang.String getMessageId()
public java.lang.String getMessageText()
public java.lang.String getMessageType()
public java.lang.String getProcessName()
public java.lang.String getScheduledTaskId()
public java.lang.String getSourceClass()
public java.lang.Long getSourceLine()
public java.lang.String getSourceMethod()
public java.lang.String getStackTrace()
public java.lang.String getSubProcessName()
public void setCorrelationKey1(java.lang.String correlationKey1)
public void setCorrelationKey2(java.lang.String correlationKey2)
public void setCorrelationKey3(java.lang.String correlationKey3)
public void setCorrelationType(java.lang.String correlationType)
public void setLogId(java.lang.String logId)
public void setLoggedBy(java.lang.String loggedBy)
public void setLoggedOn(org.jaffa.datatypes.DateTime loggedOn)
public void setMessageId(java.lang.String messageId)
public void setMessageText(java.lang.String messageText)
public void setMessageType(java.lang.String messageType)
public void setProcessName(java.lang.String processName)
public void setScheduledTaskId(java.lang.String scheduledTaskId)
public void setSourceClass(java.lang.String sourceClass)
public void setSourceLine(java.lang.Long sourceLine)
public void setSourceMethod(java.lang.String sourceMethod)
public void setStackTrace(java.lang.String stackTrace)
public void setSubProcessName(java.lang.String subProcessName)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.tx.BusinessEventLogFinderTx**

```
public void destroy()
public BusinessEventLogFinderOutDto find(BusinessEventLogFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.ui.BusinessEventLogFinderAction**

```
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.ui.BusinessEventLogFinderComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public String getCorrelationKey1()
public String getCorrelationKey1Dd()
public String getCorrelationKey2()
public String getCorrelationKey2Dd()
public String getCorrelationKey3()
public String getCorrelationKey3Dd()
public String getCorrelationType()
public String getCorrelationTypeDd()
public String getLogId()
public String getLogIdDd()
public String getLoggedBy()
public String getLoggedByDd()
public String getLoggedOn()
public String getLoggedOnDd()
public String getMessageId()
public String getMessageIdDd()
public String getMessageText()
public String getMessageTextDd()
public String getMessageType()
public String getMessageTypeDd()
public String getProcessName()
public String getProcessNameDd()
public boolean getRetrieveInputCorrelationTypeOnly()
public String getScheduledTaskId()
public String getScheduledTaskIdDd()
public String getSourceClass()
public String getSourceClassDd()
public String getSourceLine()
public String getSourceLineDd()
public String getSourceMethod()
public String getSourceMethodDd()
public String getStackTrace()
public String getStackTraceDd()
public String getSubProcessName()
public String getSubProcessNameDd()
public void quit()
public void setCorrelationKey1(String correlationKey1)
public void setCorrelationKey1Dd(String correlationKey1Dd)
public void setCorrelationKey2(String correlationKey2)
public void setCorrelationKey2Dd(String correlationKey2Dd)
public void setCorrelationKey3(String correlationKey3)
public void setCorrelationKey3Dd(String correlationKey3Dd)
public void setCorrelationType(String correlationType)
public void setCorrelationTypeDd(String correlationTypeDd)
public void setLogId(String logId)
public void setLogIdDd(String logIdDd)
public void setLoggedBy(String loggedBy)
public void setLoggedByDd(String loggedByDd)
public void setLoggedOn(String loggedOn)
public void setLoggedOnDd(String loggedOnDd)
public void setMessageId(String messageId)
public void setMessageIdDd(String messageIdDd)
public void setMessageText(String messageText)
public void setMessageTextDd(String messageTextDd)
public void setMessageType(String messageType)
public void setMessageTypeDd(String messageTypeDd)
public void setProcessName(String processName)
public void setProcessNameDd(String processNameDd)
public void setRetrieveInputCorrelationTypeOnly(boolean retrieveInputCorrelationTypeOnly)
public void setScheduledTaskId(String scheduledTaskId)
public void setScheduledTaskIdDd(String scheduledTaskIdDd)
public void setSourceClass(String sourceClass)
public void setSourceClassDd(String sourceClassDd)
public void setSourceLine(String sourceLine)
public void setSourceLineDd(String sourceLineDd)
public void setSourceMethod(String sourceMethod)
public void setSourceMethodDd(String sourceMethodDd)
public void setStackTrace(String stackTrace)
public void setStackTraceDd(String stackTraceDd)
public void setSubProcessName(String subProcessName)
public void setSubProcessNameDd(String subProcessNameDd)
public FormKey viewObject(java.lang.String logId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.businesseventlogfinder.ui.BusinessEventLogFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCorrelationKey1()
public String getCorrelationKey1Dd()
public DropDownModel getCorrelationKey1DdWM()
public EditBoxModel getCorrelationKey1WM()
public String getCorrelationKey2()
public String getCorrelationKey2Dd()
public DropDownModel getCorrelationKey2DdWM()
public EditBoxModel getCorrelationKey2WM()
public String getCorrelationKey3()
public String getCorrelationKey3Dd()
public DropDownModel getCorrelationKey3DdWM()
public EditBoxModel getCorrelationKey3WM()
public String getCorrelationType()
public String getCorrelationTypeDd()
public DropDownModel getCorrelationTypeDdWM()
public EditBoxModel getCorrelationTypeWM()
public String getLogId()
public String getLogIdDd()
public DropDownModel getLogIdDdWM()
public EditBoxModel getLogIdWM()
public String getLoggedBy()
public String getLoggedByDd()
public DropDownModel getLoggedByDdWM()
public EditBoxModel getLoggedByWM()
public String getLoggedOn()
public String getLoggedOnDd()
public DropDownModel getLoggedOnDdWM()
public EditBoxModel getLoggedOnWM()
public String getMessageId()
public String getMessageIdDd()
public DropDownModel getMessageIdDdWM()
public EditBoxModel getMessageIdWM()
public String getMessageText()
public String getMessageTextDd()
public DropDownModel getMessageTextDdWM()
public EditBoxModel getMessageTextWM()
public String getMessageType()
public String getMessageTypeDd()
public DropDownModel getMessageTypeDdWM()
public EditBoxModel getMessageTypeWM()
public String getProcessName()
public String getProcessNameDd()
public DropDownModel getProcessNameDdWM()
public EditBoxModel getProcessNameWM()
public String getScheduledTaskId()
public String getScheduledTaskIdDd()
public DropDownModel getScheduledTaskIdDdWM()
public EditBoxModel getScheduledTaskIdWM()
public String getSourceClass()
public String getSourceClassDd()
public DropDownModel getSourceClassDdWM()
public EditBoxModel getSourceClassWM()
public String getSourceLine()
public String getSourceLineDd()
public DropDownModel getSourceLineDdWM()
public EditBoxModel getSourceLineWM()
public String getSourceMethod()
public String getSourceMethodDd()
public DropDownModel getSourceMethodDdWM()
public EditBoxModel getSourceMethodWM()
public String getStackTrace()
public String getStackTraceDd()
public DropDownModel getStackTraceDdWM()
public EditBoxModel getStackTraceWM()
public String getSubProcessName()
public String getSubProcessNameDd()
public DropDownModel getSubProcessNameDdWM()
public EditBoxModel getSubProcessNameWM()
public void populateRows(GridModel rows)
public void setCorrelationKey1(String correlationKey1)
public void setCorrelationKey1Dd(String correlationKey1Dd)
public void setCorrelationKey1DdWV(String value)
public void setCorrelationKey1WV(String value)
public void setCorrelationKey2(String correlationKey2)
public void setCorrelationKey2Dd(String correlationKey2Dd)
public void setCorrelationKey2DdWV(String value)
public void setCorrelationKey2WV(String value)
public void setCorrelationKey3(String correlationKey3)
public void setCorrelationKey3Dd(String correlationKey3Dd)
public void setCorrelationKey3DdWV(String value)
public void setCorrelationKey3WV(String value)
public void setCorrelationType(String correlationType)
public void setCorrelationTypeDd(String correlationTypeDd)
public void setCorrelationTypeDdWV(String value)
public void setCorrelationTypeWV(String value)
public void setLogId(String logId)
public void setLogIdDd(String logIdDd)
public void setLogIdDdWV(String value)
public void setLogIdWV(String value)
public void setLoggedBy(String loggedBy)
public void setLoggedByDd(String loggedByDd)
public void setLoggedByDdWV(String value)
public void setLoggedByWV(String value)
public void setLoggedOn(String loggedOn)
public void setLoggedOnDd(String loggedOnDd)
public void setLoggedOnDdWV(String value)
public void setLoggedOnWV(String value)
public void setMessageId(String messageId)
public void setMessageIdDd(String messageIdDd)
public void setMessageIdDdWV(String value)
public void setMessageIdWV(String value)
public void setMessageText(String messageText)
public void setMessageTextDd(String messageTextDd)
public void setMessageTextDdWV(String value)
public void setMessageTextWV(String value)
public void setMessageType(String messageType)
public void setMessageTypeDd(String messageTypeDd)
public void setMessageTypeDdWV(String value)
public void setMessageTypeWV(String value)
public void setProcessName(String processName)
public void setProcessNameDd(String processNameDd)
public void setProcessNameDdWV(String value)
public void setProcessNameWV(String value)
public void setScheduledTaskId(String scheduledTaskId)
public void setScheduledTaskIdDd(String scheduledTaskIdDd)
public void setScheduledTaskIdDdWV(String value)
public void setScheduledTaskIdWV(String value)
public void setSourceClass(String sourceClass)
public void setSourceClassDd(String sourceClassDd)
public void setSourceClassDdWV(String value)
public void setSourceClassWV(String value)
public void setSourceLine(String sourceLine)
public void setSourceLineDd(String sourceLineDd)
public void setSourceLineDdWV(String value)
public void setSourceLineWV(String value)
public void setSourceMethod(String sourceMethod)
public void setSourceMethodDd(String sourceMethodDd)
public void setSourceMethodDdWV(String value)
public void setSourceMethodWV(String value)
public void setStackTrace(String stackTrace)
public void setStackTraceDd(String stackTraceDd)
public void setStackTraceDdWV(String value)
public void setStackTraceWV(String value)
public void setSubProcessName(String subProcessName)
public void setSubProcessNameDd(String subProcessNameDd)
public void setSubProcessNameDdWV(String value)
public void setSubProcessNameWV(String value)
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.IBusinessEventLogViewer**

```
public void destroy()
public BusinessEventLogViewerOutDto read(BusinessEventLogViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.dto.AttachmentDto**

```
public java.lang.String getAttachmentId()
public java.lang.String getAttachmentType()
public java.lang.String getContentType()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public byte[] getData()
public java.lang.String getDescription()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOriginalFileName()
public java.lang.String getRemarks()
public java.lang.String getSerializedKey()
public java.lang.String getSupercededBy()
public void setAttachmentId(java.lang.String attachmentId)
public void setAttachmentType(java.lang.String attachmentType)
public void setContentType(java.lang.String contentType)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setData(byte[] data)
public void setDescription(java.lang.String description)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOriginalFileName(java.lang.String originalFileName)
public void setRemarks(java.lang.String remarks)
public void setSerializedKey(java.lang.String serializedKey)
public void setSupercededBy(java.lang.String supercededBy)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getLogId()
public void setHeaderDto(HeaderDto headerDto)
public void setLogId(java.lang.String logId)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerOutDto**

```
public void addAttachment(AttachmentDto attachment)
public void clearAttachment()
public AttachmentDto getAttachment(int index)
public AttachmentDto[] getAttachment()
public int getAttachmentCount()
public java.lang.String getCorrelationKey1()
public java.lang.String getCorrelationKey2()
public java.lang.String getCorrelationKey3()
public java.lang.String getCorrelationType()
public java.lang.String getLogId()
public java.lang.String getLoggedBy()
public org.jaffa.datatypes.DateTime getLoggedOn()
public java.lang.String getMessageId()
public java.lang.String getMessageText()
public java.lang.String getMessageType()
public java.lang.String getProcessName()
public java.lang.String getScheduledTaskId()
public java.lang.String getSourceClass()
public java.lang.Long getSourceLine()
public java.lang.String getSourceMethod()
public java.lang.String getStackTrace()
public java.lang.String getSubProcessName()
public boolean removeAttachment(AttachmentDto attachment)
public void setAttachment(AttachmentDto attachment, int index)
public void setAttachment(AttachmentDto[] objects)
public void setCorrelationKey1(java.lang.String correlationKey1)
public void setCorrelationKey2(java.lang.String correlationKey2)
public void setCorrelationKey3(java.lang.String correlationKey3)
public void setCorrelationType(java.lang.String correlationType)
public void setLogId(java.lang.String logId)
public void setLoggedBy(java.lang.String loggedBy)
public void setLoggedOn(org.jaffa.datatypes.DateTime loggedOn)
public void setMessageId(java.lang.String messageId)
public void setMessageText(java.lang.String messageText)
public void setMessageType(java.lang.String messageType)
public void setProcessName(java.lang.String processName)
public void setScheduledTaskId(java.lang.String scheduledTaskId)
public void setSourceClass(java.lang.String sourceClass)
public void setSourceLine(java.lang.Long sourceLine)
public void setSourceMethod(java.lang.String sourceMethod)
public void setStackTrace(java.lang.String stackTrace)
public void setSubProcessName(java.lang.String subProcessName)
public String toString()
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.tx.BusinessEventLogViewerTx**

```
public void destroy()
public BusinessEventLogViewerOutDto read(BusinessEventLogViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_RelatedAttachment_Clicked(String rowNum)
public FormKey do_RelatedAttachment_ViewAttachmentData_Clicked(String rowNum)
public FormKey do_RelatedAttachment_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public BusinessEventLogViewerOutDto getBusinessEventLogViewerOutDto()
public java.lang.String getLogId()
public FormKey getViewerFormKey()
public void quit()
public void setBusinessEventLogViewerOutDto(BusinessEventLogViewerOutDto outputDto)
public void setLogId(java.lang.String logId)
public FormKey viewAttachment(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerForm**

```
public java.lang.String getCorrelationKey1()
public java.lang.String getCorrelationKey2()
public java.lang.String getCorrelationKey3()
public java.lang.String getCorrelationType()
public java.lang.String getLogId()
public java.lang.String getLoggedBy()
public org.jaffa.datatypes.DateTime getLoggedOn()
public java.lang.String getMessageId()
public java.lang.String getMessageText()
public java.lang.String getMessageType()
public java.lang.String getProcessName()
public GridModel getRelatedAttachmentWM()
public java.lang.String getScheduledTaskId()
public java.lang.String getSourceClass()
public java.lang.Long getSourceLine()
public java.lang.String getSourceMethod()
public java.lang.String getStackTrace()
public java.lang.String getSubProcessName()
public void setRelatedAttachmentWV(String value)
```
***
**org.jaffa.modules.messaging.components.messageviewer.IMessageViewer**

```
public String changePriority(MessageViewerInDto input, Long newPriority) throws FrameworkException, ApplicationExceptions
public void destroy()
public String getMessageIdForBusinessEventLog(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions
public MessageViewerOutDto read(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.messageviewer.dto.HeaderElementDto**

```
public java.lang.String getLabel()
public java.lang.String getName()
public java.lang.String getValue()
public void setLabel(java.lang.String label)
public void setName(java.lang.String name)
public void setValue(java.lang.String value)
public String toString()
```
***
**org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerInDto**

```
public java.lang.String getJMSMessageID()
public MessageModeEnum getMessageMode()
public java.lang.String getQueue()
public void setJMSMessageID(java.lang.String JMSMessageID)
public void setMessageMode(MessageModeEnum messageMode)
public void setQueue(java.lang.String queue)
public String toString()
```
***
**org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerOutDto**

```
public BusinessEventLogFinderOutDto getBusinessEventLog()
public java.lang.String getError()
public java.lang.Boolean getHasPriorityAccess()
public HeaderElementDto[] getHeaderElements()
public java.lang.String getJMSCorrelationID()
public java.lang.Integer getJMSDeliveryMode()
public javax.jms.Destination getJMSDestination()
public java.lang.Long getJMSExpiration()
public java.lang.String getJMSMessageID()
public java.lang.Boolean getJMSRedelivered()
public javax.jms.Destination getJMSReplyTo()
public DateTime getJMSTimestamp()
public java.lang.String getJMSType()
public java.lang.String getPayLoad()
public java.lang.Long getPriority()
public void setBusinessEventLog(BusinessEventLogFinderOutDto businessEventLogDto)
public void setError(java.lang.String error)
public void setHasPriorityAccess(java.lang.Boolean hasPriorityAccess)
public void setHeaderElements(HeaderElementDto[] headerElements)
public void setJMSCorrelationID(java.lang.String JMSCorrelationID)
public void setJMSDeliveryMode(java.lang.Integer JMSDeliveryMode)
public void setJMSDestination(javax.jms.Destination JMSDestination)
public void setJMSExpiration(java.lang.Long JMSExpiration)
public void setJMSMessageID(java.lang.String JMSMessageID)
public void setJMSRedelivered(java.lang.Boolean JMSRedelivered)
public void setJMSReplyTo(javax.jms.Destination JMSReplyTo)
public void setJMSTimestamp(DateTime JMSTimestamp)
public void setJMSType(java.lang.String JMSType)
public void setPayLoad(java.lang.String payLoad)
public void setPriority(java.lang.Long priority)
public String toString()
```
***
**org.jaffa.modules.messaging.components.messageviewer.tx.MessageViewerTx**

```
public String changePriority(MessageViewerInDto input, Long newPriority) throws FrameworkException, ApplicationExceptions
public void destroy()
public String getMessageIdForBusinessEventLog(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions
public MessageViewerOutDto read(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.messageviewer.ui.IMessageViewerListener**

```
public void processDone(EventObject source) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.messageviewer.ui.MessageViewerAction**

```
public FormKey do_ChangePriority_Clicked()
public FormKey do_Close_Clicked()
public FormKey do_MoreRecords_Clicked()
public FormKey do_RelatedBusinessEventLog_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.messaging.components.messageviewer.ui.MessageViewerComponent**

```
public void addMessageViewerListener(IMessageViewerListener listener)
public FormKey changePriority() throws FrameworkException, ApplicationExceptions
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayBusinessEventLogFinder() throws ApplicationExceptions, FrameworkException
public FormKey displayBusinessEventLogViewer(String logId) throws ApplicationExceptions, FrameworkException
public void doInquiry() throws ApplicationExceptions, FrameworkException
public java.lang.String getJMSMessageID()
public MessageModeEnum getMessageMode()
public MessageViewerOutDto getMessageViewerOutDto()
public java.lang.Long getPriority()
public java.lang.String getQueue()
public FormKey getViewerFormKey()
public void quit()
public boolean removeMessageViewerListener(IMessageViewerListener listener)
public void setJMSMessageID(java.lang.String JMSMessageID)
public void setMessageMode(MessageModeEnum messageMode)
public void setMessageViewerOutDto(MessageViewerOutDto outputDto)
public void setPriority(java.lang.Long priority)
public void setQueue(java.lang.String queue)
```
***
**org.jaffa.modules.messaging.components.messageviewer.ui.MessageViewerForm**

```
public boolean doValidate(HttpServletRequest request)
public java.lang.String getError()
public GridModel getHeadersWM()
public java.lang.String getJMSCorrelationID()
public java.lang.Integer getJMSDeliveryMode()
public javax.jms.Destination getJMSDestination()
public java.lang.Long getJMSExpiration()
public java.lang.String getJMSMessageID()
public java.lang.Boolean getJMSRedelivered()
public javax.jms.Destination getJMSReplyTo()
public DateTime getJMSTimestamp()
public java.lang.String getJMSType()
public boolean getMoreRecordsExist()
public Long getNumberOfRecords()
public java.lang.String getPayLoad()
public java.lang.Long getPriority()
public DropDownModel getPriorityWM()
public GridModel getRelatedBusinessEventLogWM()
public GridModel getTechnicalDetailsWM()
public void setHeadersWV(String value)
public void setPriority(java.lang.Long priority)
public void setPriorityWV(String value)
public void setRelatedBusinessEventLogWV(String value)
public void setTechnicalDetailsWV(String value)
```
***
**org.jaffa.modules.messaging.components.queuelist.IQueueList**

```
public void destroy()
public QueueListOutDto query(String filter) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutDto**

```
public QueueListOutRowDto[] getRows()
public void setRows(QueueListOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto**

```
public Long getActiveConsumer()
public Long getError()
public Long getInProcess()
public Long getPending()
public String getQueue()
public Boolean getStarted()
public Boolean getStopped()
public void setActiveConsumer(Long activeConsumer)
public void setError(Long error)
public void setInProcess(Long inProcess)
public void setPending(Long pending)
public void setQueue(String queue)
public void setStarted(Boolean started)
public void setStopped(Boolean stopped)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queuelist.tx.QueueListTx**

```
public void destroy()
public QueueListOutDto query(String filter) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.components.queuelist.ui.QueueListAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Rows_ViewErrorMessages_Clicked(String rowNum)
public FormKey do_Rows_ViewInProcessMessages_Clicked(String rowNum)
public FormKey do_Rows_ViewPendingMessages_Clicked(String rowNum)
public FormKey do_refresh()
```
***
**org.jaffa.modules.messaging.components.queuelist.ui.QueueListComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayResults() throws ApplicationExceptions, FrameworkException
public Exception getError()
public String getFilter()
public QueueListOutDto getFinderOutDto()
public FormKey getResultsFormKey()
public void quit()
public void setFilter(String filter)
public FormKey viewMessages(String queue, MessageModeEnum messageMode) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.queuelist.ui.QueueListForm**

```
public GridModel getRowsWM()
public void populateRows(GridModel rows)
public void setRowsWV(String value)
```
***
**org.jaffa.modules.messaging.components.queueviewer.IQueueViewer**

```
public void deleteMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException
public void destroy()
public QueueViewerOutDto read(QueueViewerInDto input) throws FrameworkException, ApplicationExceptions
public void resubmitMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.queueviewer.dto.HeaderElementDto**

```
public java.lang.String getLabel()
public java.lang.String getName()
public java.lang.String getValue()
public void setLabel(java.lang.String label)
public void setName(java.lang.String name)
public void setValue(java.lang.String value)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queueviewer.dto.MessageDto**

```
public HeaderElementDto[] getHeaderElements()
public Boolean getManageable()
public String getMessageId()
public void setHeaderElements(HeaderElementDto[] headerElements)
public void setManageable(Boolean manageable)
public void setMessageId(String messageId)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queueviewer.dto.QueueViewerInDto**

```
public java.lang.String getFilter()
public MessageModeEnum getMessageMode()
public java.lang.String getQueueName()
public void setFilter(java.lang.String filter)
public void setMessageMode(MessageModeEnum messageMode)
public void setQueueName(java.lang.String queueName)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queueviewer.dto.QueueViewerOutDto**

```
public java.lang.Boolean getHasAdminAccess()
public HeaderElementDto[] getHeaderElements()
public MessageDto[] getMessages()
public void setHasAdminAccess(java.lang.Boolean hasAdminAccess)
public void setHeaderElements(HeaderElementDto[] headerElements)
public void setMessages(MessageDto[] messages)
public String toString()
```
***
**org.jaffa.modules.messaging.components.queueviewer.tx.QueueViewerTx**

```
public void deleteMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException
public void destroy()
public QueueViewerOutDto read(QueueViewerInDto input) throws FrameworkException, ApplicationExceptions
public void resubmitMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.queueviewer.ui.IQueueViewerListener**

```
public void processDone(EventObject source) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_RelatedQueueHeader_Delete_Clicked(String rowNum)
public FormKey do_RelatedQueueHeader_ReSubmit_Clicked(String rowNum)
public FormKey do_RelatedQueueHeader_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerComponent**

```
public void addQueueViewerListener(IQueueViewerListener listener)
public FormKey deleteMessage(String messageId) throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public void doInquiry() throws ApplicationExceptions, FrameworkException
public java.lang.String getFilter()
public MessageModeEnum getMessageMode()
public java.lang.String getQueue()
public QueueViewerOutDto getQueueViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public boolean removeQueueViewerListener(IQueueViewerListener listener)
public FormKey resubmitMessage(String messageId) throws ApplicationExceptions, FrameworkException
public void setFilter(java.lang.String filter)
public void setMessageMode(MessageModeEnum messageMode)
public void setQueue(java.lang.String queue)
public void setQueueViewerOutDto(QueueViewerOutDto outputDto)
public FormKey viewMessage(String messageId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerForm**

```
public boolean doValidate(HttpServletRequest request)
public java.lang.String getFilter()
public EditBoxModel getFilterWM()
public java.lang.String getQueue()
public GridModel getRelatedQueueHeaderWM()
public void setFilter(java.lang.String filter)
public void setFilterWV(String value)
public void setQueue(java.lang.String queue)
public void setRelatedQueueHeaderWV(String value)
```
***
**org.jaffa.modules.messaging.services.BaseMessage**

```
```
***
**org.jaffa.modules.messaging.services.ConfigurationServiceTest**

```
public static Test suite()
public void testConfigurationService()
public void testGetInstance()
public void testGetJmsConfig()
public void testGetMessageInfo() throws ClassNotFoundException
public void testGetMessageInfoForChildClassWithNoDefinition() throws ClassNotFoundException
public void testGetQueueInfo()
public void testGetQueueNames()
```
***
**org.jaffa.modules.messaging.services.Example1Message**

```
```
***
**org.jaffa.modules.messaging.services.Example2Message**

```
```
