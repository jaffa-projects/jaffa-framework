# jaffa-soa Public Methods

***
**org.jaffa.config.GraphDataObjectBeanConfig**

```
public GraphDataObject graphDataObject(GraphDataObject graphDataObject)
```
***
**org.jaffa.config.GraphDataObjectConfig**

```
public void setApplicationContext(ApplicationContext applicationContext)
```
***
**org.jaffa.config.LifecycleHandlerConfigTest**

```
public void setup()
public void testTransformationHandlerAllHandlers()
public void testTransformationHandlerNoCustomerHandlers()
public void testTransformationHandlerOnlyAppendedHandlers()
public void testTransformationHandlerOnlyPrependedHandlers()
public void testTransformationHandlerRegisterWithStaticContext()
```
***
**org.jaffa.config.TestProvider**

```
public ITransformationHandler getHandler()
```
***
**org.jaffa.config.TestTransformationHandler**

```
public int getIndex()
```
***
**org.jaffa.loader.LifecycleHandlerConfig**

```
public void init()
public TransformationHandler transformationHandler(TransformationHandler handler)
public ITransformationHandlerFactory transformationHandlerFactory()
```
***
**org.jaffa.loader.SoaLoaderConfig**

```
public DroolsLoader droolLoader()
public DroolsManager droolManager()
public TransactionManager jaffaTransactionManager()
public MessagingManager messagingManager()
public ResourceLoader<MessagingManager> messagingManagerXmlLoader()
public SchedulerManager schedulerManager()
public ResourceLoader<SchedulerManager> schedulerManagerXmlLoader()
public SoaEventManager soaEventManager()
public ResourceLoader<SoaEventManager> soaEventManagerXmlLoader()
public ResourceLoader<TransactionManager> transactionManagerXmlLoader()
```
***
**org.jaffa.loader.drools.DroolsLoader**

```
public DroolsManager getManager()
public void loadDrools()
public void setManager(DroolsManager manager)
```
***
**org.jaffa.loader.drools.DroolsLoaderTest**

```
public void testDroolLoading()
```
***
**org.jaffa.loader.drools.DroolsManager**

```
public void createAgents()
public synchronized RuleAgent getAgent(String serviceName, String variation)
public Map<RuleAgentKey, StringBuilder> getDroolsFiles()
public String getModuleName(String path)
public Map<RuleAgentKey, RuleAgent> getRuleAgents()
public String getServiceName(String path)
public synchronized void refreshAgent(String serviceName)
public synchronized void registerDrool(Resource resource, String variation) throws IOException
public synchronized void registerDrool(String serviceName, String path, String variation) throws IOException
public void setDroolsFiles(Map<RuleAgentKey, StringBuilder> droolsFiles)
public void setRuleAgents(Map<RuleAgentKey, RuleAgent> ruleAgents)
public synchronized void unRegisterDrool(Resource resource, String variation) throws IOException
```
***
**org.jaffa.loader.drools.DroolsManagerTest**

```
public void createAgentsTest() throws Exception
public void getModuleNameTest()
public void getServiceNameTest()
public void refreshAgentTest() throws Exception
public void testRegisterDrool() throws Exception
public void testRegisterMultipleDrool() throws Exception
public void testUnRegisterDrool() throws Exception
```
***
**org.jaffa.loader.messaging.JndiJmsManager**

```
public JmsConfig getJmsConfig()
public IRepository<JmsConfig> getJmsRepository()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void setJmsRepository(IRepository<JmsConfig> repo)
```
***
**org.jaffa.loader.messaging.MessagingManager**

```
public IRepository<MessageFilter> getMessageFilterRepository()
public List<MessageFilter> getMessageFilters()
public MessageInfo getMessageInfo(String dataBeanClass)
public IRepository<MessageInfo> getMessageInfoRepository()
public QueueInfo getQueueInfo(String dataBeanClassName)
public IRepository<QueueInfo> getQueueInfoRepository()
public String[] getQueueNames()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public TopicInfo getTopicInfo(String dataBeanClassName)
public IRepository<TopicInfo> getTopicInfoRepository()
public String[] getTopicNames()
public void registerMessageFilter(ContextKey key, MessageFilter value)
public void registerMessageInfo(ContextKey key, MessageInfo value)
public void registerQueueInfo(ContextKey key, QueueInfo value)
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void registerTopicInfo(ContextKey key, TopicInfo value)
public void setMessageFilterRepository(IRepository<MessageFilter> messageFilterRepository)
public void setMessageInfoRepository(IRepository<MessageInfo> messageInfoRepository)
public void setQueueInfoRepository(IRepository<QueueInfo> repo)
public void setTopicInfoRepository(IRepository<TopicInfo> topicInfoRepository)
public void unregisterMessageFilter(ContextKey key)
public void unregisterMessageInfo(ContextKey key)
public void unregisterQueueInfo(ContextKey key)
public void unregisterTopicInfo(ContextKey key)
```
***
**org.jaffa.loader.messaging.MessagingManagerTest**

```
public void getMessageInfo() throws Exception
public void getQueueInfo() throws Exception
public void getTopicInfo() throws Exception
public void registerXML() throws Exception
public void setUp() throws Exception
public void tearDown() throws Exception
public void testCheckForQueueAndTopicNamingConflictsConflict() throws Exception
public void testCheckForQueueAndTopicNamingConflictsNoConflict() throws Exception
public void testGetMessageFilterRepository() throws Exception
public void testGetMessageFilters() throws Exception
public void testGetMessageRepository() throws Exception
public void testGetName() throws Exception
public void testGetQueueInfoRepository() throws Exception
public void testGetQueueNames() throws Exception
public void testGetTopicInfoRepository() throws Exception
public void testGetTopicNames() throws Exception
public void testRegisterMessageFilter() throws Exception
public void testRegisterMessageInfo() throws Exception
public void testValidateMessageInfoEmpty() throws Exception
public void testValidateMessageInfoWithQueueName() throws Exception
public void testValidateMessageInfoWithTopicName() throws Exception
public void testValidateMessageInfoWithTopicNameLockCheck() throws Exception
```
***
**org.jaffa.loader.messaging.MessagingXmlLoadTest**

```
public void testGetQueueNames()
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testGetTopicNames()
public void testXmlLoad()
```
***
**org.jaffa.loader.scheduler.SchedulerManager**

```
public Task[] getAllSchedulerTasks()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public Task getSchedulerTask(String dataBeanClass)
public Task getSchedulerTaskByTypeName(String typeName)
public IRepository<Task> getSchedulerTaskRepository()
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void registerSchedulerTask(ContextKey contextKey, Task schedulerTask)
public void setSchedulerTaskRepository(IRepository<Task> schedulerTaskRepository)
public void unregisterSchedulerTask(ContextKey contextKey)
```
***
**org.jaffa.loader.scheduler.SchedulerXmlLoadTest**

```
public void testGetAllSchedulerTasks()
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testGetSchedulerTaskByTaskName()
public void testXmlLoad()
```
***
**org.jaffa.loader.soa.SoaEventManager**

```
public SoaEventInfo[] getAllSoaEventInfo(List<String> contextOrderParam)
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public SoaEventInfo getSoaEventInfo(String soaEventName)
public String[] getSoaEventNames()
public IRepository<SoaEventInfo> getSoaEventRepository()
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void registerSoaEventInfo(ContextKey contextKey, SoaEventInfo soaEventInfo)
public void setSoaEventRepository(IRepository<SoaEventInfo> soaEventRepository)
public void setSoaEventRepository(MapRepository<SoaEventInfo> soaEventRepository)
public void unregisterSoaEventInfo(ContextKey contextKey)
```
***
**org.jaffa.loader.transaction.TransactionManager**

```
public TransactionInfo[] getAllTransactionInfo()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public TransactionInfo getTransactionInfo(String dataBeanClassName)
public TransactionInfo getTransactionInfo(Class dataBeanClass)
public IRepository<TransactionInfo> getTransactionRepository()
public TypeInfo getTypeInfo(String typeName)
public IRepository<TypeInfo> getTypeInfoRepository()
public String[] getTypeNames()
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void registerTransactionInfo(ContextKey contextKey, TransactionInfo transactionInfo)
public void registerTypeInfo(ContextKey contextKey, TypeInfo typeInfo)
public void setTransactionRepository(IRepository<TransactionInfo> transactionRepository)
public void setTypeInfoRepository(IRepository<TypeInfo> typeInfoRepository)
public void unregisterTransactionInfo(ContextKey contextKey)
public void unregisterTypeInfo(ContextKey contextKey)
public void unregisterXML(String uri, String context, String variation) throws JAXBException, SAXException, IOException
```
***
**org.jaffa.loader.transaction.TransactionManagerTest**

```
public void setup()
public void testGetAllTransactionInfo()
public void testGetTransactionInfo()
public void testGetTypeInfo()
public void testGetTypeNames()
public void testGetxmlFileName()
public void testRegisterTransactionInfo()
public void testRegisterTypeInfo()
public void testRegisterXml() throws Exception
public void testUnRegisterTransactionInfo()
public void testUnRegisterTypeInfo()
public void testUnregisterXml() throws Exception
```
***
**org.jaffa.loader.transaction.TransactionXmlLoadTest**

```
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testSoaEventsXmlLoad()
public void testTransactionXmlLoad()
```
***
**org.jaffa.modules.common.IMessageHandler**

```
public void onDelete(UOW uow, Map<String, String> messageHeaders, Object payload) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.apis.BusinessEventLogService**

```
public BusinessEventLogQueryResponse query(BusinessEventLogCriteria criteria)
public BusinessEventLogUpdateResponse[] update(BusinessEventLogGraph[] graphs)
```
***
**org.jaffa.modules.messaging.apis.data.BusinessEventLogCriteria**

```
public StringCriteriaField getCorrelationKey1()
public StringCriteriaField getCorrelationKey2()
public StringCriteriaField getCorrelationKey3()
public StringCriteriaField getCorrelationType()
public StringCriteriaField getLogId()
public StringCriteriaField getLoggedBy()
public DateTimeCriteriaField getLoggedOn()
public StringCriteriaField getMessageId()
public StringCriteriaField getMessageText()
public StringCriteriaField getMessageType()
public StringCriteriaField getProcessName()
public StringCriteriaField getScheduledTaskId()
public StringCriteriaField getSourceClass()
public StringCriteriaField getSubProcessName()
public Criteria returnQueryClause(Criteria c)
public void setCorrelationKey1(StringCriteriaField correlationKey1)
public void setCorrelationKey2(StringCriteriaField correlationKey2)
public void setCorrelationKey3(StringCriteriaField correlationKey3)
public void setCorrelationType(StringCriteriaField correlationType)
public void setLogId(StringCriteriaField logId)
public void setLoggedBy(StringCriteriaField loggedBy)
public void setLoggedOn(DateTimeCriteriaField loggedOn)
public void setMessageId(StringCriteriaField messageId)
public void setMessageText(StringCriteriaField messageText)
public void setMessageType(StringCriteriaField messageType)
public void setProcessName(StringCriteriaField processName)
public void setScheduledTaskId(StringCriteriaField scheduledTaskId)
public void setSourceClass(StringCriteriaField sourceClass)
public void setSubProcessName(StringCriteriaField subProcessName)
```
***
**org.jaffa.modules.messaging.apis.data.BusinessEventLogGraph**

```
public String getCorrelationKey1()
public String getCorrelationKey2()
public String getCorrelationKey3()
public String getCorrelationType()
public String getLogId()
public String getLoggedBy()
public DateTime getLoggedOn()
public String getMessageId()
public String getMessageText()
public String getMessageType()
public String getProcessName()
public String getScheduledTaskId()
public String getSourceClass()
public String getStackTrace()
public String getSubProcessName()
public void setCorrelationKey1(String correlationKey1)
public void setCorrelationKey2(String correlationKey2)
public void setCorrelationKey3(String correlationKey3)
public void setCorrelationType(String correlationType)
public void setLogId(String logId)
public void setLoggedBy(String loggedBy)
public void setLoggedOn(DateTime loggedOn)
public void setMessageId(String messageId)
public void setMessageText(String messageText)
public void setMessageType(String messageType)
public void setProcessName(String processName)
public void setScheduledTaskId(String scheduledTaskId)
public void setSourceClass(String sourceClass)
public void setStackTrace(String stackTrace)
public void setSubProcessName(String subProcessName)
```
***
**org.jaffa.modules.messaging.apis.data.BusinessEventLogQueryResponse**

```
public BusinessEventLogGraph[] getGraphs()
public void setGraphs(BusinessEventLogGraph[] graphs)
```
***
**org.jaffa.modules.messaging.apis.data.BusinessEventLogUpdateResponse**

```
public BusinessEventLogGraph getSource()
public void setSource(BusinessEventLogGraph source)
```
***
**org.jaffa.modules.messaging.domain.BusinessEventLog**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String logId) throws FrameworkException
public static BusinessEventLog findByPK(UOW uow, java.lang.String logId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String logId)
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
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCorrelationKey1(java.lang.String correlationKey1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCorrelationKey2(java.lang.String correlationKey2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCorrelationKey3(java.lang.String correlationKey3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCorrelationType(java.lang.String correlationType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLogId(java.lang.String logId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLoggedBy(java.lang.String loggedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLoggedOn(org.jaffa.datatypes.DateTime loggedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setMessageId(java.lang.String messageId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setMessageText(java.lang.String messageText) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setMessageType(java.lang.String messageType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProcessName(java.lang.String processName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setScheduledTaskId(java.lang.String scheduledTaskId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSourceClass(java.lang.String sourceClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSourceLine(java.lang.Long sourceLine) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSourceMethod(java.lang.String sourceMethod) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStackTrace(java.lang.String stackTrace) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSubProcessName(java.lang.String subProcessName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCorrelationKey1(java.lang.String correlationKey1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCorrelationKey2(java.lang.String correlationKey2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCorrelationKey3(java.lang.String correlationKey3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCorrelationType(java.lang.String correlationType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLogId(java.lang.String logId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLoggedBy(java.lang.String loggedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLoggedOn(org.jaffa.datatypes.DateTime loggedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateMessageId(java.lang.String messageId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateMessageText(java.lang.String messageText) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateMessageType(java.lang.String messageType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProcessName(java.lang.String processName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateScheduledTaskId(java.lang.String scheduledTaskId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSourceClass(java.lang.String sourceClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSourceLine(java.lang.Long sourceLine) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSourceMethod(java.lang.String sourceMethod) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStackTrace(java.lang.String stackTrace) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSubProcessName(java.lang.String subProcessName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCorrelationKey1(java.lang.String correlationKey1) throws ValidationException, FrameworkException
public java.lang.String validateCorrelationKey2(java.lang.String correlationKey2) throws ValidationException, FrameworkException
public java.lang.String validateCorrelationKey3(java.lang.String correlationKey3) throws ValidationException, FrameworkException
public java.lang.String validateCorrelationType(java.lang.String correlationType) throws ValidationException, FrameworkException
public java.lang.String validateLogId(java.lang.String logId) throws ValidationException, FrameworkException
public java.lang.String validateLoggedBy(java.lang.String loggedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLoggedOn(org.jaffa.datatypes.DateTime loggedOn) throws ValidationException, FrameworkException
public java.lang.String validateMessageId(java.lang.String messageId) throws ValidationException, FrameworkException
public java.lang.String validateMessageText(java.lang.String messageText) throws ValidationException, FrameworkException
public java.lang.String validateMessageType(java.lang.String messageType) throws ValidationException, FrameworkException
public java.lang.String validateProcessName(java.lang.String processName) throws ValidationException, FrameworkException
public java.lang.String validateScheduledTaskId(java.lang.String scheduledTaskId) throws ValidationException, FrameworkException
public java.lang.String validateSourceClass(java.lang.String sourceClass) throws ValidationException, FrameworkException
public java.lang.Long validateSourceLine(java.lang.Long sourceLine) throws ValidationException, FrameworkException
public java.lang.String validateSourceMethod(java.lang.String sourceMethod) throws ValidationException, FrameworkException
public java.lang.String validateStackTrace(java.lang.String stackTrace) throws ValidationException, FrameworkException
public java.lang.String validateSubProcessName(java.lang.String subProcessName) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.messaging.domain.BusinessEventLogMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.modules.messaging.services.ConfigurationService**

```
public static ConfigurationService getInstance()
public static ConfigurationService getInstance(final boolean setJmsConfig)
public JmsConfig getJmsConfig()
public static JndiJmsManager getJndiJmsManager()
public List<MessageFilter> getMessageFilters()
public MessageInfo getMessageInfo(String dataBeanClassName) throws ClassNotFoundException
public static MessagingManager getMessagingManager()
public QueueInfo getQueueInfo(String queueName)
public String[] getQueueNames()
public TopicInfo getTopicInfo(String topicName)
public String[] getTopicNames()
public static void setJndiJmsManager(JndiJmsManager manager)
public static void setMessagingManager(MessagingManager manager)
```
***
**org.jaffa.modules.messaging.services.HeaderParam**

```
public String getName()
public String getValue()
public void setName(String name)
public void setValue(String value)
```
***
**org.jaffa.modules.messaging.services.IHasDestinationName**

```
public String getQueueName()
public String getTopicName()
public void setQueueName(String queueName)
public void setTopicName(String topicName)
```
***
**org.jaffa.modules.messaging.services.IHasHeaderParams**

```
public HeaderParam[] getHeaderParams()
public void setHeaderParams(HeaderParam[] headerParams)
```
***
**org.jaffa.modules.messaging.services.IManageableMessageHandler**

No public methods.
***
**org.jaffa.modules.messaging.services.IMessageFilter**

```
public void doFilter(List<Object> payloads) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.InitialContextFactrory**

```
public static void cleanInitialContext()
public static InitialContext obtainInitialContext() throws FrameworkException, ApplicationExceptions
public static Object obtainParamValue(final Param param, final Object bean) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.JaffaConnectionFactory**

```
public static void closeConnection()
public static Connection obtainConnection() throws FrameworkException, ApplicationExceptions
public static ConnectionFactory obtainConnectionFactory() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.messaging.services.JaffaMessageBean**

```
public void ejbCreate()
public void ejbRemove()
public void onMessage(final Message message)
public void setMessageDrivenContext(final MessageDrivenContext ctx)
```
***
**org.jaffa.modules.messaging.services.JaffaMessageConsumer**

```
public void onMessage(Message message)
public static void processMessage(final Message message)
public static void processPayload(final MessageInfo messageInfo, final Object payload, final String userId, final String scheduledTaskId, final String originalMessageId) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.JaffaMessagingApplicationException**

No public methods.
***
**org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException**

No public methods.
***
**org.jaffa.modules.messaging.services.JmsBrowser**

```
public static String changeMessagePriority(String queueName, String messageId, int newPriority) throws FrameworkException, ApplicationExceptions
public static void deleteMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions
public static String[] getAccessibleQueueNames()
public static Message getMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions
public static Message[] getPendingMessages(String queueName) throws FrameworkException, ApplicationExceptions
public static Message[] getPendingMessages(String queueName, String filter) throws FrameworkException, ApplicationExceptions
public static boolean hasAdminMessageAccess(String queueName)
public static boolean hasBrowseAllMessagesAccess(String queueName)
public static boolean hasBrowseQueueAccess(String queueName)
public static boolean hasChangePriorityAccess(String queueName)
public static String resubmitMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.JmsClientHelper**

```
public String add(Object payload, String[] dependsOnIds, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public String addOutbound(Object payload) throws FrameworkException, ApplicationExceptions
public Transaction addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public void commit() throws FrameworkException, ApplicationExceptions
public static JmsClientHelper getInstance() throws FrameworkException, ApplicationExceptions
public ISender getSender()
public static Queue obtainQueue(String queueName) throws FrameworkException, ApplicationExceptions
public void prepareDeletesForCommit(Collection deletes)
public void rollback() throws FrameworkException, ApplicationExceptions
public static String send(Object payload) throws FrameworkException, ApplicationExceptions
public static String send(Object payload, String userId, String scheduledTaskId) throws FrameworkException, ApplicationExceptions
public void sendObjectMessage(String queueOrTopicName, Serializable message, boolean postToTopic, Properties properties)
public void sendTextMessage(final String queueName, final String message)
public void sendTextMessage(final String queueName, final String message, final Properties properties)
public void setSender(ISender sender)
public static String toQueueName(String queueName)
public static String toTopicName(String topicName)
```
***
**org.jaffa.modules.messaging.services.JmsQueueAdmin**

```
public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs)
public MessageQueryResponse messageQuery(MessageCriteria criteria)
public QueueQueryResponse queueQuery(QueueCriteria criteria)
public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs)
public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs)
```
***
**org.jaffa.modules.messaging.services.JmsSender**

```
public String send(Object payload, UserContext user) throws FrameworkException, ApplicationExceptions
public String send(Object payload, String userId, String scheduledTaskId, UserContext user) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.LockingService**

```
public static void checkLock(Object payload) throws FrameworkException, ApplicationExceptions
public static void checkLock(Object payload, MessageInfo messageInfo) throws FrameworkException, ApplicationExceptions
public static void deleteLockingMessages(Object payload) throws FrameworkException, ApplicationExceptions
public static void deleteLockingMessages(Object payload, MessageInfo messageInfo) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.messaging.services.LoggingService**

```
public static void setLoggingContext(Object payload, MessageInfo messageInfo, String userId, String scheduledTaskId, String messageId)
public static void unsetLoggingContext(Object payload, MessageInfo messageInfo)
```
***
**org.jaffa.modules.messaging.services.ManageableMessageHandlerService**

```
public static void addManageableMessageHandler(String messageId, IManageableMessageHandler messageHandler)
public static String[] listManageableMessageIds() throws FrameworkException, ApplicationExceptions
public static void removeManageableMessageHandler(String messageId)
```
***
**org.jaffa.modules.messaging.services.configdomain.Config**

```
public List<Object> getMessageOrQueueOrTopic()
```
***
**org.jaffa.modules.messaging.services.configdomain.DisplayParam**

```
public String getLabel()
public String getName()
public void setLabel(String value)
public void setName(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.Filter**

```
public String getLanguage()
public String getValue()
public boolean isExpression()
public void setExpression(Boolean value)
public void setLanguage(String value)
public void setValue(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.Header**

```
public List<Param> getParam()
```
***
**org.jaffa.modules.messaging.services.configdomain.JmsConfig**

```
public String getConnectionFactory()
public JndiContext getJndiContext()
public String getPassword()
public String getUser()
public void setConnectionFactory(String value)
public void setJndiContext(JndiContext value)
public void setPassword(String value)
public void setUser(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.JndiConfig**

```
public JmsConfig getJmsConfig()
public void setJmsConfig(JmsConfig value)
```
***
**org.jaffa.modules.messaging.services.configdomain.JndiContext**

```
public List<Param> getParam()
```
***
**org.jaffa.modules.messaging.services.configdomain.LockCheck**

```
public Filter getFilter()
public List<Param> getParam()
public void setFilter(Filter value)
```
***
**org.jaffa.modules.messaging.services.configdomain.MessageFilter**

```
public String getFilterClass()
public String getFilterName()
public void setFilterClass(String value)
public void setFilterName(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.MessageInfo**

```
public String getDataBean()
public Header getHeader()
public LockCheck getLockCheck()
public String getQueueName()
public String getToClass()
public String getToMethod()
public String getTopicName()
public void setDataBean(String value)
public void setHeader(Header value)
public void setLockCheck(LockCheck value)
public void setQueueName(String value)
public void setToClass(String value)
public void setToMethod(String value)
public void setTopicName(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.ObjectFactory**

```
public Config createConfig()
public DisplayParam createDisplayParam()
public Filter createFilter()
public Header createHeader()
public JmsConfig createJmsConfig()
public JndiContext createJndiContext()
public LockCheck createLockCheck()
public MessageFilter createMessageFilter()
public MessageInfo createMessageInfo()
public Param createParam()
public QueueInfo createQueueInfo()
public Security createSecurity()
public TopicInfo createTopicInfo()
```
***
**org.jaffa.modules.messaging.services.configdomain.Param**

```
public String getLanguage()
public LoggingName getLoggingName()
public String getName()
public String getValue()
public boolean isExpression()
public void setExpression(Boolean value)
public void setLanguage(String value)
public void setLoggingName(LoggingName value)
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.QueueInfo**

```
public ConsumerPolicy getConsumerPolicy()
public List<DisplayParam> getDisplayParam()
public String getName()
public Security getSecurity()
public boolean isErrorQueue()
public void setConsumerPolicy(ConsumerPolicy value)
public void setErrorQueue(Boolean value)
public void setName(String value)
public void setSecurity(Security value)
```
***
**org.jaffa.modules.messaging.services.configdomain.Security**

```
public String getAdminMessage()
public String getBrowseAllMessages()
public String getBrowseQueue()
public String getChangePriority()
public void setAdminMessage(String value)
public void setBrowseAllMessages(String value)
public void setBrowseQueue(String value)
public void setChangePriority(String value)
```
***
**org.jaffa.modules.messaging.services.configdomain.TopicInfo**

```
public ConsumerPolicy getConsumerPolicy()
public String getName()
public void setConsumerPolicy(ConsumerPolicy value)
public void setName(String value)
```
***
**org.jaffa.modules.scheduler.polling.DatabasePoller**

```
public void stopPoll()
```
***
**org.jaffa.modules.scheduler.polling.DatabasePollerShutdownThread**

```
public void run()
```
***
**org.jaffa.modules.scheduler.polling.FilePoller**

No public methods.
***
**org.jaffa.modules.scheduler.services.Custom**

```
public String getCustomPattern()
public void setCustomPattern(String customPattern)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.Daily**

```
public Boolean getWeekDaysOnly()
public void setWeekDaysOnly(Boolean weekDaysOnly)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.Heartbeat**

```
public DateTime getProcessedOn()
public DateTime getStartedOn()
public DateTime getSubmittedOn()
```
***
**org.jaffa.modules.scheduler.services.JaffaSchedulerApplicationException**

No public methods.
***
**org.jaffa.modules.scheduler.services.JaffaSchedulerFrameworkException**

No public methods.
***
**org.jaffa.modules.scheduler.services.Monthly**

```
public Integer getDay()
public Month[] getMonths()
public WeekDay getWeekDay()
public WeekFrequency getWeekFrequency()
public void setDay(Integer day)
public void setMonths(Month[] months)
public void setWeekDay(WeekDay weekDay)
public void setWeekFrequency(WeekFrequency weekFrequency)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.Often**

```
public Integer getHours()
public Integer getMinutes()
public Integer getSeconds()
public void setHours(Integer hours)
public void setMinutes(Integer minutes)
public void setSeconds(Integer seconds)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.Recurrence**

```
public abstract void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.ScheduledTask**

```
public Object getBusinessObject()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getDescription()
public DateTime getEndOn()
public String getLastChangedBy()
public DateTime getLastChangedOn()
public DateTime getLastRunOn()
public MisfireRecovery getMisfireRecovery()
public DateTime getNextDue()
public Recurrence getRecurrence()
public String getRunAs()
public String getScheduledTaskId()
public DateTime getStartOn()
public TaskStatusEnumeration getStatus()
public String getTaskType()
public void setBusinessObject(Object businessObject)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setDescription(String description)
public void setEndOn(DateTime endOn)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedOn(DateTime lastChangedOn)
public void setLastRunOn(DateTime lastRunOn)
public void setMisfireRecovery(MisfireRecovery misfireRecovery)
public void setNextDue(DateTime nextDue)
public void setRecurrence(Recurrence recurrence)
public void setRunAs(String runAs)
public void setScheduledTaskId(String scheduledTaskId)
public void setStartOn(DateTime startOn)
public void setStatus(TaskStatusEnumeration status)
public void setTaskType(String taskType)
public String toString()
```
***
**org.jaffa.modules.scheduler.services.SchedulerBrowser**

```
public static String findTypeName(String taskType) throws FrameworkException
public static boolean hasAdminTaskAccess(String taskType) throws FrameworkException
public static boolean hasBrowseTaskAccess(String taskType) throws FrameworkException
public static boolean hasBrowseTaskAccess(ScheduledTask task) throws FrameworkException
public static boolean isTaskOwner(ScheduledTask task)
```
***
**org.jaffa.modules.scheduler.services.SchedulerConfiguration**

```
public static synchronized SchedulerConfiguration getInstance()
public static SchedulerManager getSchedulerManager()
public Task getTask(String type)
public Task getTaskByDataBean(String dataBeanClassName) throws ClassNotFoundException
public Task[] getTasks()
public static void setSchedulerManager(SchedulerManager schedulerManager)
```
***
**org.jaffa.modules.scheduler.services.SchedulerHelper**

```
public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void addTask(ScheduledTask task) throws FrameworkException, ApplicationExceptions
public void deleteTask(String taskId) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getAllTasks() throws FrameworkException, ApplicationExceptions
public DateTime[] getExecutionDates(ScheduledTask task, DateTime after, Integer count) throws FrameworkException, ApplicationExceptions
public SchedulerStatusEnumeration getSchedulerStatus()
public ScheduledTask getTask(String taskId) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getTasks(String userId, String taskType) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getUserTasks(String userId) throws FrameworkException, ApplicationExceptions
public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions
public void instantiateSchedulerFactory()
public void pauseScheduler()
public void shutdownScheduler()
public void shutdownScheduler(boolean waitForJobsToComplete)
public void startScheduler()
public void updateTask(ScheduledTask task) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.SchedulerHelperFactory**

```
public static SchedulerHelper instance()
```
***
**org.jaffa.modules.scheduler.services.Weekly**

```
public WeekDay getDay()
public WeekFrequency getFrequency()
public void setDay(WeekDay day)
public void setFrequency(WeekFrequency frequency)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.Yearly**

```
public Integer getFrequency()
public Month getOn()
public void setFrequency(Integer frequency)
public void setOn(Month on)
public String toString()
public void validate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.configdomain.Config**

```
public List<Task> getTask()
```
***
**org.jaffa.modules.scheduler.services.configdomain.ObjectFactory**

```
public Config createConfig()
public Param createParam()
public Task createTask()
```
***
**org.jaffa.modules.scheduler.services.configdomain.Param**

```
public String getName()
public String getValue()
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.modules.scheduler.services.configdomain.Task**

```
public String getDataBean()
public String getType()
public boolean isAutoCreateDataBean()
public void setAutoCreateDataBean(Boolean value)
public void setDataBean(String value)
public void setType(String value)
```
***
**org.jaffa.modules.scheduler.services.quartz.OracleJDBCDelegate**

```
public int insertJobDetail(Connection conn, JobDetail job) throws IOException, SQLException
public int updateJobData(Connection conn, JobDetail job) throws IOException, SQLException
public int updateJobDetail(Connection conn, JobDetail job) throws IOException, SQLException
```
***
**org.jaffa.modules.scheduler.services.quartz.QuartzInitializerListener**

```
public void contextDestroyed(final ServletContextEvent sce)
public void contextInitialized(final ServletContextEvent sce)
```
***
**org.jaffa.modules.scheduler.services.quartz.QuartzSchedulerHelper**

```
public void activateTask(final String taskId) throws FrameworkException, ApplicationExceptions
public void addTask(ScheduledTask task) throws FrameworkException, ApplicationExceptions
public void deleteTask(final String taskId) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getAllTasks() throws FrameworkException, ApplicationExceptions
public DateTime[] getExecutionDates(ScheduledTask task, DateTime after, Integer count) throws FrameworkException, ApplicationExceptions
public JobKey getJobKey(final String taskId)
public SchedulerStatusEnumeration getSchedulerStatus()
public ScheduledTask getTask(final String taskId) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getTasks(String userId, String taskType) throws FrameworkException, ApplicationExceptions
public ScheduledTask[] getUserTasks(String userId) throws FrameworkException, ApplicationExceptions
public void inactivateTask(final String taskId) throws FrameworkException, ApplicationExceptions
public void instantiateSchedulerFactory()
public void pauseScheduler()
public void shutdownScheduler()
public void shutdownScheduler(final boolean waitForJobsToComplete)
public void startScheduler()
public void updateTask(ScheduledTask task) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.scheduler.services.quartz.TransactionInvokerJob**

```
public void execute(JobExecutionContext context) throws JobExecutionException
```
***
**org.jaffa.modules.scheduler.services.quartz.TriggerHelper**

```
public static Trigger taskToTrigger(ScheduledTask task) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.setup.apis.ValidFieldValueService**

```
public ValidFieldValueQueryResponse query(ValidFieldValueCriteria criteria)
public ValidFieldValueUpdateResponse[] update(ValidFieldValueGraph[] graphs)
```
***
**org.jaffa.modules.setup.apis.data.ValidFieldValueCriteria**

```
public StringCriteriaField getDescription()
public StringCriteriaField getFieldName()
public StringCriteriaField getLegalValue()
public StringCriteriaField getRemarks()
public StringCriteriaField getTableName()
public Criteria returnQueryClause(Criteria c)
public void setDescription(StringCriteriaField description)
public void setFieldName(StringCriteriaField fieldName)
public void setLegalValue(StringCriteriaField legalValue)
public void setRemarks(StringCriteriaField remarks)
public void setTableName(StringCriteriaField tableName)
```
***
**org.jaffa.modules.setup.apis.data.ValidFieldValueGraph**

```
public String getDescription()
public String getFieldName()
public String getLegalValue()
public String getRemarks()
public String getTableName()
public void setDescription(String description)
public void setFieldName(String fieldName)
public void setLegalValue(String legalValue)
public void setRemarks(String remarks)
public void setTableName(String tableName)
```
***
**org.jaffa.modules.setup.apis.data.ValidFieldValueQueryResponse**

```
public ValidFieldValueGraph[] getGraphs()
public void setGraphs(ValidFieldValueGraph[] graphs)
```
***
**org.jaffa.modules.setup.apis.data.ValidFieldValueUpdateResponse**

```
public ValidFieldValueGraph getSource()
public void setSource(ValidFieldValueGraph source)
```
***
**org.jaffa.modules.setup.domain.CompositeKey**

```
public boolean equals(Object obj)
public java.lang.String getFieldName()
public java.lang.String getLegalValue()
public java.lang.String getTableName()
public int hashCode()
public void setFieldName(java.lang.String fieldName)
public void setLegalValue(java.lang.String legalValue)
public void setTableName(java.lang.String tableName)
```
***
**org.jaffa.modules.setup.domain.ValidFieldValue**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException
public static ValidFieldValue findByPK(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue)
public CompositeKey getCompositeKey()
public java.lang.String getDescription()
public java.lang.String getFieldName()
public java.lang.String getLegalValue()
public java.lang.String getRemarks()
public java.lang.String getTableName()
public java.lang.Long getVersionNumber()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCompositeKey(CompositeKey compositeKey)
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLegalValue(java.lang.String legalValue) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTableName(java.lang.String tableName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLegalValue(java.lang.String legalValue) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTableName(java.lang.String tableName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.String validateLegalValue(java.lang.String legalValue) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
public java.lang.String validateTableName(java.lang.String tableName) throws ValidationException, FrameworkException
public java.lang.Long validateVersionNumber(java.lang.Long versionNumber) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.setup.domain.ValidFieldValueMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.polling.DatabasePoller**

```
public void stopPoll()
```
***
**org.jaffa.polling.DatabasePollerShutdownThread**

```
public void run()
```
***
**org.jaffa.polling.FilePoller**

No public methods.
***
**org.jaffa.qm.apis.IQueueAdmin**

```
public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs)
public MessageQueryResponse messageQuery(MessageCriteria criteria)
public QueueQueryResponse queueQuery(QueueCriteria criteria)
public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs)
public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs)
```
***
**org.jaffa.qm.apis.QueueAdminException**

No public methods.
***
**org.jaffa.qm.apis.QueueManager**

```
public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs)
public MessageQueryResponse messageQuery(MessageCriteria criteria) throws ApplicationExceptions
public QueueQueryResponse queueQuery(QueueCriteria criteria)
public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs)
public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs)
```
***
**org.jaffa.qm.apis.data.MessageAdminResponse**

```
public ServiceError[] getErrors()
public MessageGraph getSource()
public void setErrors(ServiceError[] errors)
public void setSource(MessageGraph source)
public String toString()
```
***
**org.jaffa.qm.apis.data.MessageCriteria**

```
public MessageFieldCriteria[] getApplicationFields()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDirection()
public StringCriteriaField getErrorMessage()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getMessageId()
public IntegerCriteriaField getPriority()
public StringCriteriaField getQueueSystemId()
public StringCriteriaField getStatus()
public StringCriteriaField getSubType()
public StringCriteriaField getType()
public void setApplicationFields(MessageFieldCriteria[] applicationFields)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDirection(StringCriteriaField direction)
public void setErrorMessage(StringCriteriaField errorMessage)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setMessageId(StringCriteriaField messageId)
public void setPriority(IntegerCriteriaField priority)
public void setQueueSystemId(StringCriteriaField queueSystemId)
public void setStatus(StringCriteriaField status)
public void setSubType(StringCriteriaField subType)
public void setType(StringCriteriaField type)
```
***
**org.jaffa.qm.apis.data.MessageDependency**

```
public String getDependsOnId()
public Status getStatus()
public void setDependsOnId(String dependsOnId)
public void setStatus(Status status)
```
***
**org.jaffa.qm.apis.data.MessageField**

```
public String getLabel()
public String getName()
public String getValue()
public void setLabel(String label)
public void setName(String name)
public void setValue(String value)
```
***
**org.jaffa.qm.apis.data.MessageFieldCriteria**

```
public String getName()
public StringCriteriaField getValue()
public void setName(String name)
public void setValue(StringCriteriaField value)
```
***
**org.jaffa.qm.apis.data.MessageFieldMetaData**

```
public String getLabel()
public String getName()
public void setLabel(String label)
public void setName(String name)
```
***
**org.jaffa.qm.apis.data.MessageGraph**

```
public MessageField[] getApplicationFields()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getDirection()
public String getErrorMessage()
public Boolean getHasAdminAccess()
public String getLastChangedBy()
public DateTime getLastChangedOn()
public MessageDependency[] getMessageDependencies()
public String getMessageId()
public String getPayload()
public Long getPriority()
public QueueMetaData getQueueMetaData()
public Status getStatus()
public String getSubType()
public MessageField[] getTechnicalFields()
public String getType()
public void setApplicationFields(MessageField[] applicationFields)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setDirection(String direction)
public void setErrorMessage(String errorMessage)
public void setHasAdminAccess(Boolean hasAdminAccess)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedOn(DateTime lastChangedOn)
public void setMessageDependencies(MessageDependency[] messageDependencies)
public void setMessageId(String messageId)
public void setPayload(String payload)
public void setPriority(Long priority)
public void setQueueMetaData(QueueMetaData queueMetaData)
public void setStatus(Status status)
public void setSubType(String subType)
public void setTechnicalFields(MessageField[] technicalFields)
public void setType(String type)
```
***
**org.jaffa.qm.apis.data.MessageQueryResponse**

```
public ServiceError[] getErrors()
public MessageGraph[] getGraphs()
public Integer getTotalRecords()
public void setErrors(ServiceError[] errors)
public void setGraphs(MessageGraph[] graphs)
public void setTotalRecords(Integer totalRecords)
public String toString()
```
***
**org.jaffa.qm.apis.data.QueueAdminResponse**

```
public ServiceError[] getErrors()
public QueueGraph getSource()
public void setErrors(ServiceError[] errors)
public void setSource(QueueGraph source)
public String toString()
```
***
**org.jaffa.qm.apis.data.QueueCriteria**

```
public IntegerCriteriaField getConsumerCount()
public IntegerCriteriaField getErrorCount()
public IntegerCriteriaField getHoldCount()
public IntegerCriteriaField getInProcessCount()
public IntegerCriteriaField getInterruptedCount()
public IntegerCriteriaField getOpenCount()
public StringCriteriaField getQueueSystemId()
public StringCriteriaField getStatus()
public IntegerCriteriaField getSuccessCount()
public StringCriteriaField getType()
public void setConsumerCount(IntegerCriteriaField consumerCount)
public void setErrorCount(IntegerCriteriaField errorCount)
public void setHoldCount(IntegerCriteriaField holdCount)
public void setInProcessCount(IntegerCriteriaField inProcessCount)
public void setInterruptedCount(IntegerCriteriaField interruptedCount)
public void setOpenCount(IntegerCriteriaField openCount)
public void setQueueSystemId(StringCriteriaField queueSystemId)
public void setStatus(StringCriteriaField status)
public void setSuccessCount(IntegerCriteriaField successCount)
public void setType(StringCriteriaField type)
```
***
**org.jaffa.qm.apis.data.QueueGraph**

```
public Long getConsumerCount()
public Long getErrorCount()
public Boolean getHasAdminAccess()
public Long getHoldCount()
public Long getInProcessCount()
public Long getInterruptedCount()
public DateTime getLastErroredOn()
public Long getOpenCount()
public QueueMetaData getQueueMetaData()
public Status getStatus()
public Long getSuccessCount()
public String getType()
public void setConsumerCount(Long consumerCount)
public void setErrorCount(Long errorCount)
public void setHasAdminAccess(Boolean hasAdminAccess)
public void setHoldCount(Long holdCount)
public void setInProcessCount(Long inProcessCount)
public void setInterruptedCount(Long interruptedCount)
public void setLastErroredOn(DateTime lastErroredOn)
public void setOpenCount(Long openCount)
public void setQueueMetaData(QueueMetaData queueMetaData)
public void setStatus(Status status)
public void setSuccessCount(Long successCount)
public void setType(String type)
```
***
**org.jaffa.qm.apis.data.QueueMetaData**

```
public String getQueueSystemId()
public MessageFieldMetaData[] getSupportedApplicationFields()
public MessageGraph.Status[] getSupportedMessageStatus()
public Boolean getSupportsBusinessEventLogs()
public Boolean getSupportsDependencies()
public Boolean getSupportsTechnicalFields()
public String getType()
public void setQueueSystemId(String queueSystemId)
public void setSupportedApplicationFields(MessageFieldMetaData[] supportedApplicationFields)
public void setSupportedMessageStatus(MessageGraph.Status[] supportedMessageStatus)
public void setSupportsBusinessEventLogs(Boolean supportsBusinessEventLogs)
public void setSupportsDependencies(Boolean supportsDependencies)
public void setSupportsTechnicalFields(Boolean supportsTechnicalFields)
public void setType(String type)
```
***
**org.jaffa.qm.apis.data.QueueQueryResponse**

```
public ServiceError[] getErrors()
public QueueGraph[] getGraphs()
public Integer getTotalRecords()
public void setErrors(ServiceError[] errors)
public void setGraphs(QueueGraph[] graphs)
public void setTotalRecords(Integer totalRecords)
public String toString()
```
***
**org.jaffa.qm.util.PropertyFilter**

```
public boolean areSubFieldsIncluded(String fieldPrefix)
public String[] getIncludedFields()
public static PropertyFilter getInstance(Class clazz) throws IntrospectionException
public static PropertyFilter getInstance(Class clazz, String[] rules) throws IntrospectionException
public String[] getRules()
public Boolean includeField(String field)
public boolean isFieldIncluded(String field)
```
***
**org.jaffa.rules.GraphCriteriaFlexFieldsTest**

```
public void testCriteriaClauseDomainMapped() throws FrameworkException, ApplicationExceptions
public void testHasFlexFields() throws FrameworkException, ApplicationExceptions
public void testNoFlexFields() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.GraphObjectFlexFieldsTest**

```
public void testClearChanges() throws FrameworkException, ApplicationExceptions
public void testHasChanged() throws FrameworkException, ApplicationExceptions
public void testHasFlexFields() throws FrameworkException, ApplicationExceptions
public void testNoFlexFields() throws FrameworkException, ApplicationExceptions
public void testValidate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.TestHelper**

```
public static void setupRepos() throws JaffaRulesFrameworkException
public static void shutdownRepos()
```
***
**org.jaffa.rules.testmodels.User**

```
public String getId()
public String getName()
public String getUserRef1()
public String getUserRef10()
public String getUserRef11()
public String getUserRef12()
public String getUserRef13()
public String getUserRef14()
public String getUserRef15()
public String getUserRef2()
public String getUserRef3()
public String getUserRef4()
public String getUserRef5()
public String getUserRef6()
public String getUserRef7()
public String getUserRef8()
public String getUserRef9()
public void setId(String id)
public void setName(String name)
public void setUserRef1(String userRef1)
public void setUserRef10(String userRef10)
public void setUserRef11(String userRef11)
public void setUserRef12(String userRef12)
public void setUserRef13(String userRef13)
public void setUserRef14(String userRef14)
public void setUserRef15(String userRef15)
public void setUserRef2(String userRef2)
public void setUserRef3(String userRef3)
public void setUserRef4(String userRef4)
public void setUserRef5(String userRef5)
public void setUserRef6(String userRef6)
public void setUserRef7(String userRef7)
public void setUserRef8(String userRef8)
public void setUserRef9(String userRef9)
```
***
**org.jaffa.rules.testmodels.UserCriteria**

```
public String getId()
public String getName()
public String getUserRef1()
public String getUserRef10()
public String getUserRef11()
public String getUserRef12()
public String getUserRef13()
public String getUserRef14()
public String getUserRef15()
public String getUserRef2()
public String getUserRef3()
public String getUserRef4()
public String getUserRef5()
public String getUserRef6()
public String getUserRef7()
public String getUserRef8()
public String getUserRef9()
public void setId(String id)
public void setName(String name)
public void setUserRef1(String userRef1)
public void setUserRef10(String userRef10)
public void setUserRef11(String userRef11)
public void setUserRef12(String userRef12)
public void setUserRef13(String userRef13)
public void setUserRef14(String userRef14)
public void setUserRef15(String userRef15)
public void setUserRef2(String userRef2)
public void setUserRef3(String userRef3)
public void setUserRef4(String userRef4)
public void setUserRef5(String userRef5)
public void setUserRef6(String userRef6)
public void setUserRef7(String userRef7)
public void setUserRef8(String userRef8)
public void setUserRef9(String userRef9)
```
***
**org.jaffa.rules.testmodels.UserCriteriaNoFlex**

```
public String getId()
public String getName()
public String getUserRef1()
public String getUserRef10()
public String getUserRef11()
public String getUserRef12()
public String getUserRef13()
public String getUserRef14()
public String getUserRef15()
public String getUserRef2()
public String getUserRef3()
public String getUserRef4()
public String getUserRef5()
public String getUserRef6()
public String getUserRef7()
public String getUserRef8()
public String getUserRef9()
public void setId(String id)
public void setName(String name)
public void setUserRef1(String userRef1)
public void setUserRef10(String userRef10)
public void setUserRef11(String userRef11)
public void setUserRef12(String userRef12)
public void setUserRef13(String userRef13)
public void setUserRef14(String userRef14)
public void setUserRef15(String userRef15)
public void setUserRef2(String userRef2)
public void setUserRef3(String userRef3)
public void setUserRef4(String userRef4)
public void setUserRef5(String userRef5)
public void setUserRef6(String userRef6)
public void setUserRef7(String userRef7)
public void setUserRef8(String userRef8)
public void setUserRef9(String userRef9)
```
***
**org.jaffa.rules.testmodels.UserGraph**

```
public String getId()
public String getName()
public String getUserRef1()
public String getUserRef10()
public String getUserRef11()
public String getUserRef12()
public String getUserRef13()
public String getUserRef14()
public String getUserRef15()
public String getUserRef2()
public String getUserRef3()
public String getUserRef4()
public String getUserRef5()
public String getUserRef6()
public String getUserRef7()
public String getUserRef8()
public String getUserRef9()
public void setId(String id)
public void setName(String name)
public void setUserRef1(String userRef1)
public void setUserRef10(String userRef10)
public void setUserRef11(String userRef11)
public void setUserRef12(String userRef12)
public void setUserRef13(String userRef13)
public void setUserRef14(String userRef14)
public void setUserRef15(String userRef15)
public void setUserRef2(String userRef2)
public void setUserRef3(String userRef3)
public void setUserRef4(String userRef4)
public void setUserRef5(String userRef5)
public void setUserRef6(String userRef6)
public void setUserRef7(String userRef7)
public void setUserRef8(String userRef8)
public void setUserRef9(String userRef9)
```
***
**org.jaffa.rules.testmodels.UserGraphNoFlex**

```
public String getId()
public String getName()
public String getUserRef1()
public String getUserRef10()
public String getUserRef11()
public String getUserRef12()
public String getUserRef13()
public String getUserRef14()
public String getUserRef15()
public String getUserRef2()
public String getUserRef3()
public String getUserRef4()
public String getUserRef5()
public String getUserRef6()
public String getUserRef7()
public String getUserRef8()
public String getUserRef9()
public void setId(String id)
public void setName(String name)
public void setUserRef1(String userRef1)
public void setUserRef10(String userRef10)
public void setUserRef11(String userRef11)
public void setUserRef12(String userRef12)
public void setUserRef13(String userRef13)
public void setUserRef14(String userRef14)
public void setUserRef15(String userRef15)
public void setUserRef2(String userRef2)
public void setUserRef3(String userRef3)
public void setUserRef4(String userRef4)
public void setUserRef5(String userRef5)
public void setUserRef6(String userRef6)
public void setUserRef7(String userRef7)
public void setUserRef8(String userRef8)
public void setUserRef9(String userRef9)
```
***
**org.jaffa.soa.dataaccess.BaseElement**

```
public String getDomainFieldName()
public Field getGraphField()
public String getGraphFieldName()
public PropertyDescriptor getGraphPropertyDescriptor()
public String[] getScope()
public String getTargetScope()
public boolean isNoCloning()
public boolean isReadOnly()
```
***
**org.jaffa.soa.dataaccess.DataTransformer**

```
public static void buildGraphFromDomain(Object source, Object target) throws ApplicationExceptions, FrameworkException
public static void buildGraphFromDomain(Object source, Object target, MappingFilter filter, String objectPath) throws ApplicationExceptions, FrameworkException
public static void buildGraphFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys) throws ApplicationExceptions, FrameworkException
public static void buildGraphFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys, GraphCriteria originalCriteria, ITransformationHandler handler) throws ApplicationExceptions, FrameworkException
public static GraphDataObject cloneGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler, GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException
public static void deleteGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler) throws ApplicationExceptions, FrameworkException
public static void massUpdateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler, GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException
public static GraphDataObject prevalidateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler) throws ApplicationExceptions, FrameworkException
public static GraphDataObject updateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.dataaccess.DirtyReadInfo**

```
public String getDirtyReadDataFieldName()
public String getDirtyReadErrorLabelToken()
public String[] getDirtyReadErrorParams()
```
***
**org.jaffa.soa.dataaccess.Filter**

```
public String getRule()
public String getStaticPrefix()
public boolean isExcluded()
public boolean isRegEx()
public boolean matches(String fld)
```
***
**org.jaffa.soa.dataaccess.ForeignObject**

```
public List<String> getForeignKeys()
```
***
**org.jaffa.soa.dataaccess.GraphMapping**

```
public boolean containsDataField(String dataFieldName)
public Class getDataClass()
public String getDataClassName()
public String getDataClassShortName()
public PropertyDescriptor getDataFieldDescriptor(String dataFieldName)
public String[] getDataFieldNames()
public String[] getDataFieldNames(String targetScope)
public AccessibleObject getDataMutator(String dataFieldName)
public String getDirtyReadDataFieldName()
public String getDirtyReadErrorLabelToken()
public String[] getDirtyReadErrorParams()
public Class getDomainClass()
public String getDomainClassName()
public String getDomainClassShortName()
public PropertyDescriptor getDomainFieldDescriptor(String dataFieldName)
public String getDomainFieldName(String dataFieldName)
public static Field getField(Class clazz, String propertyName, Class propertyType)
public static Map<String, Field> getFieldMap(Class clazz)
public Set getFields()
public Set getFields(String targetScope)
public Set getForeignFields()
public Set getForeignFields(String targetScope)
public List getForeignKeys(String dataFieldName)
public Set getKeyFields()
public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class clazz)
public static PropertyDescriptor[] getPropertyDescriptors(Class clazz)
public Class getQueryDomainClass()
public String getQueryDomainClassName()
public String getQueryDomainClassShortName()
public PropertyDescriptor getQueryDomainFieldDescriptor(String dataFieldName)
public PropertyDescriptor getRealDomainFieldDescriptor(String domainFieldName)
public Set getRelatedFields()
public Set getRelatedFields(String targetScope)
public String[] getScope(String dataFieldName)
public String getTargetScope(String dataFieldName)
public boolean hasFields()
public boolean hasFields(String targetScope)
public boolean hasForeignFields()
public boolean hasForeignFields(String targetScope)
public boolean hasKeyFields()
public boolean hasRelatedFields()
public boolean hasRelatedFields(String targetScope)
public boolean isField(String dataFieldName)
public boolean isForeignField(String dataFieldName)
public boolean isKeyField(String dataFieldName)
public boolean isNoCloning(String dataFieldName)
public boolean isReadOnly(String dataFieldName)
public boolean isRelatedField(String dataFieldName)
```
***
**org.jaffa.soa.dataaccess.GraphService**

```
public U[] localCloneOrMassUpdate(C graphCriteria, G newGraph, boolean clone, UOW uow) throws FrameworkException, ApplicationExceptions
public G[] localQuery(C graphCriteria, UOW uow) throws FrameworkException, ApplicationExceptions
public G localUpdate(String path, G graph, UOW uow) throws FrameworkException, ApplicationExceptions
public G[] localUpdate(String path, G[] graphs, UOW uow) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.soa.dataaccess.ITransformationHandler**

No public methods.
***
**org.jaffa.soa.dataaccess.ITransformationHandlerFactory**

No public methods.
***
**org.jaffa.soa.dataaccess.ITransformationHandlerProvider**

No public methods.
***
**org.jaffa.soa.dataaccess.KeyField**

No public methods.
***
**org.jaffa.soa.dataaccess.MappingFactory**

```
public static GraphMapping getInstance(Object gdo)
public static GraphMapping getInstance(Class gdo)
```
***
**org.jaffa.soa.dataaccess.MappingFilter**

```
public boolean areSubFieldsIncluded(String fieldPrefix)
public static Object clearCache(GraphMapping graph)
public static Map<Class, Collection<String>> getExcludedFieldMap(Class<? extends GraphDataObject> gdo)
public static List<String> getFieldList(Class<? extends GraphDataObject> gdo)
public static List<String> getFieldList(Class<? extends GraphDataObject> gdo, boolean includeUnmappedFields)
public static Map<Class, Collection<String>> getFieldMap(Class<? extends GraphDataObject> gdo)
public static Map<Class, Collection<String>> getFieldMap(Class<? extends GraphDataObject> gdo, boolean includeUnmappedFields)
public String[] getIncludedFields()
public static MappingFilter getInstance(GraphMapping graph)
public static MappingFilter getInstance(GraphMapping graph, String[] rules)
public String[] getRules()
public Boolean includeField(String field)
public boolean isFieldIncluded(String field)
public static void main(String[] args)
```
***
**org.jaffa.soa.dataaccess.PlainField**

No public methods.
***
**org.jaffa.soa.dataaccess.RelatedObject**

No public methods.
***
**org.jaffa.soa.dataaccess.SOAEventBaseHandler**

No public methods.
***
**org.jaffa.soa.dataaccess.SOAEventLifecycleHandler**

```
public void postAdd() throws PostAddFailedException
public void postDelete() throws PostDeleteFailedException
public void postLoad() throws PostLoadFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void setTargetBean(ILifecycleHandler iLifecycleHandler)
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.dataaccess.SOAEventTransformationHandler**

```
public void changeDone(String path, Object source, Object target, UOW uow) throws ApplicationExceptions, FrameworkException
public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public ITransformationHandler getHandler()
public List<ITransformationHandler> getTransformationHandlers()
public boolean isChangeDone()
public boolean isCloning()
public Criteria preQuery(String path, Criteria criteria, GraphCriteria originalCriteria, Class domain) throws ApplicationException, ApplicationExceptions, FrameworkException
public void prevalidateBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void setChangeDone(boolean changeDone)
public void setCloning(boolean cloning)
public void setTargetBean(ITransformationHandler targetBean)
public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.dataaccess.SkipTransformException**

No public methods.
***
**org.jaffa.soa.dataaccess.TestProvider**

```
public ITransformationHandler getHandler()
```
***
**org.jaffa.soa.dataaccess.TestTransformationHandler**

```
public int getId()
```
***
**org.jaffa.soa.dataaccess.TransformException**

No public methods.
***
**org.jaffa.soa.dataaccess.TransformationHandler**

```
public void appendTransformationHandlers(List<ITransformationHandler> handlers)
public void changeDone(String path, Object source, Object target, UOW uow) throws ApplicationExceptions, FrameworkException
public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public ITransformationHandler getTargetBean()
public List<ITransformationHandler> getTransformationHandlers()
public boolean isChangeDone()
public boolean isCloning()
public Criteria preQuery(String path, Criteria criteria, GraphCriteria originalCriteria, Class domain) throws ApplicationException, ApplicationExceptions, FrameworkException
public void prependTransformationHandlers(List<ITransformationHandler> handlers)
public void prevalidateBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void setChangeDone(boolean changeDone)
public void setCloning(boolean cloning)
public void setTargetBean(ITransformationHandler targetBean)
public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.dataaccess.TransformationHandlerFactory**

```
public void addAppendedHandler(Class<?> clazz, ITransformationHandlerProvider handler)
public void addPrependedHandler(Class<?> clazz, ITransformationHandlerProvider handler)
public List<ITransformationHandler> getAppendedHandlers(TransformationHandler handler)
public List<ITransformationHandler> getPrependedHandlers(TransformationHandler handler)
```
***
**org.jaffa.soa.dataaccess.TransformationHandlerFactoryTest**

```
public void testAddAppendedHandler()
public void testAddPrependedHandler()
```
***
**org.jaffa.soa.dataaccess.TransformerUtils**

```
public static String generateSerializedKey(GraphDataObject source) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException
public static String printGraph(Object source)
public static String printGraph(Object source, List objectStack)
public static String printXMLGraph(Object source)
```
***
**org.jaffa.soa.dataaccess.WSDLTrimmer**

```
public static void trim(File sourceWSDL, File targetWSDLToGenerate, String webServiceImplementationClassName) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException, ClassNotFoundException
public static void trim(Element element, String webServiceImplementationClassName) throws ClassNotFoundException
```
***
**org.jaffa.soa.domain.CompositeKey**

```
public boolean equals(Object obj)
public java.lang.String getEventId()
public java.lang.String getName()
public int hashCode()
public void setEventId(java.lang.String eventId)
public void setName(java.lang.String name)
```
***
**org.jaffa.soa.domain.SOAEvent**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String eventId) throws FrameworkException
public static SOAEvent findByPK(UOW uow, java.lang.String eventId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String eventId)
public Criteria findSOAEventParamCriteria()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getEventId()
public java.lang.String getEventName()
public java.lang.String getLocalId()
public SOAEventParam[] getSOAEventParamArray() throws FrameworkException
public SOAEventParam newSOAEventParamObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEventId(java.lang.String eventId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEventName(java.lang.String eventName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLocalId(java.lang.String localId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEventId(java.lang.String eventId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEventName(java.lang.String eventName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLocalId(java.lang.String localId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateEventId(java.lang.String eventId) throws ValidationException, FrameworkException
public java.lang.String validateEventName(java.lang.String eventName) throws ValidationException, FrameworkException
public java.lang.String validateLocalId(java.lang.String localId) throws ValidationException, FrameworkException
```
***
**org.jaffa.soa.domain.SOAEventMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.soa.domain.SOAEventParam**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String eventId, java.lang.String name) throws FrameworkException
public static SOAEventParam findByPK(UOW uow, java.lang.String eventId, java.lang.String name) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String eventId, java.lang.String name)
public CompositeKey getCompositeKey()
public java.lang.String getEventId()
public java.lang.String getName()
public SOAEvent getSOAEventObject() throws ValidationException, FrameworkException
public java.lang.String getValue()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCompositeKey(CompositeKey compositeKey)
public void setEventId(java.lang.String eventId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setName(java.lang.String name) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateEventId(java.lang.String eventId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateName(java.lang.String name) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateEventId(java.lang.String eventId) throws ValidationException, FrameworkException
public java.lang.String validateName(java.lang.String name) throws ValidationException, FrameworkException
public java.lang.String validateValue(java.lang.String value) throws ValidationException, FrameworkException
```
***
**org.jaffa.soa.domain.SOAEventParamMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.soa.events.IProcessEvent**

```
public void execute(UOW uow) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.soa.events.NullProcessEvent**

```
public void execute(UOW uow) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.soa.events.Param**

```
public java.lang.String getName()
public java.lang.String getValue()
public void setName(java.lang.String name)
public void setValue(java.lang.String value)
public String toString()
```
***
**org.jaffa.soa.events.ProcessEventGraph**

```
public Boolean getCancelEvent()
public String getEventName()
public Param[] getParams()
public void setCancelEvent(Boolean cancelEvent)
public void setEventName(String eventName)
public void setParams(Param[] params)
```
***
**org.jaffa.soa.events.ProcessEventHandler**

```
public static IProcessEvent createProcessEvent(ProcessEventGraph processEventGraph) throws ApplicationExceptions, FrameworkException
public static void process(UOW uow, ProcessEventGraph processEventGraph) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.graph.CommentGraph**

```
public String getComments()
public void setComments(String comments)
```
***
**org.jaffa.soa.graph.GraphComparator**

```
public int compare(Object o1, Object o2)
```
***
**org.jaffa.soa.graph.GraphCriteria**

```
public Criteria buildQueryCriteria()
public Boolean getFindTotalRecords()
public Integer getFirstRecord()
public FlexCriteriaBean getFlexCriteriaBean() throws ApplicationExceptions, FrameworkException
public Integer getMaxRecords()
public Integer getObjectLimit()
public Integer getObjectStart()
public OrderByField[] getOrderByFields()
public String[] getResultGraphRules()
public Criteria returnQueryClause(Criteria nestedClause)
public void setFindTotalRecords(Boolean findTotalRecords)
public void setFirstRecord(Integer objectStart)
public void setFlexCriteriaBean(FlexCriteriaBean flexCriteriaBean) throws ApplicationExceptions, FrameworkException
public void setMaxRecords(Integer objectLimit)
public void setObjectLimit(Integer objectLimit)
public void setObjectStart(Integer objectStart)
public void setOrderByFields(OrderByField[] orderByFields)
public void setResultGraphRules(String[] resultGraphRules)
public String toString()
```
***
**org.jaffa.soa.graph.GraphDataObject**

```
public final void addPropertyChangeListener(java.beans.PropertyChangeListener l)
public Map<String, Object> changedFields()
public void clearChanges()
public boolean equals(Object obj)
public Boolean getDeleteObject()
public FlexBean getFlexBean() throws FrameworkException, ApplicationExceptions
public String[] getNullify()
public Object getOriginalValue(String property) throws NoSuchFieldException
public Boolean getPerformDirtyReadCheck()
public ProcessEventGraph[] getProcessEventGraphs()
public boolean hasChanged()
public boolean hasChanged(String property)
public int hashCode()
public final void removePropertyChangeListener(java.beans.PropertyChangeListener l)
public void setDeleteObject(Boolean deleteObject)
public void setFlexBean(FlexBean flexBean) throws ApplicationExceptions, FrameworkException
public void setNullify(String[] nullify)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public void setProcessEventGraphs(ProcessEventGraph[] processEventGraphs)
public void setValidator(Validator<GraphDataObject> validator)
public String toString()
public String toString(List objectStack)
public String toXMLString()
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.graph.GraphQueryResponse**

```
public ServiceError[] getErrors()
public Integer getTotalRecords()
public void setErrors(ServiceError[] errors)
public void setTotalRecords(Integer totalRecords)
public String toString()
```
***
**org.jaffa.soa.graph.GraphUpdateResponse**

```
public ServiceError[] getErrors()
public void setErrors(ServiceError[] errors)
```
***
**org.jaffa.soa.graph.ServiceError**

```
public static ServiceError[] generate(Throwable cause)
public String[] getArguments()
public String getLocalizedMessage()
public String getMessage()
public Param[] getParams()
public String getType()
public void setArguments(String[] arguments)
public void setLocalizedMessage(String localizedMessage)
public void setMessage(String message)
public void setParams(Param[] params)
public void setType(String type)
public String toString()
```
***
**org.jaffa.soa.graph.SystemCodeGraph**

```
public String getCode()
public String getDescription()
public void setCode(String code)
public void setDescription(String description)
```
***
**org.jaffa.soa.rules.ApplicationRule**

```
public static ApplicationRule createFact(String name)
public static Collection<ApplicationRule> createFacts(String name)
public boolean equals(Object obj)
public String getName()
public Object getValue()
public int hashCode()
public static void insertFact(StatefulSession session, String name)
public static void insertFacts(StatefulSession session, String name)
public void setName(String name)
public void setValue(Object value)
public String toString()
```
***
**org.jaffa.soa.rules.FieldChanged**

```
public boolean equals(Object obj)
public String getField()
public Object getKey1()
public Object getKey2()
public Object getKey3()
public String getObject()
public Object getOldValue()
public void setField(String field)
public void setKey1(Object key1)
public void setKey2(Object key2)
public void setKey3(Object key3)
public void setObject(String object)
public void setOldValue(Object oldValue)
public String toString()
```
***
**org.jaffa.soa.rules.PendingEventException**

```
public Param[] getParams()
public void setParams(Param[] params)
```
***
**org.jaffa.soa.rules.RuleAgentKey**

```
public boolean equals(Object o)
public String getServiceName()
public String getVariant()
public int hashCode()
public void setServiceName(String serviceName)
public void setVariant(String variant)
```
***
**org.jaffa.soa.rules.RuleApplicationException**

```
public String getLocalizedMessage()
public String getMessage()
```
***
**org.jaffa.soa.rules.ServiceRulesInterceptor**

```
public synchronized void add(IPersistent domainObject) throws ApplicationExceptions, FrameworkException
public synchronized void addFact(Object fact)
public synchronized void clearLog() throws ApplicationExceptions, FrameworkException
public synchronized void delete(IPersistent domainObject) throws ApplicationExceptions, FrameworkException
public static DroolsManager getDroolsManager()
public synchronized RuleBase getRuleBase()
public StatefulSession getSession()
public void initialize() throws ApplicationExceptions, FrameworkException
public synchronized void initialize(UOW uow) throws ApplicationExceptions, FrameworkException
public static synchronized void refreshAgent(String serviceName)
public static void setDroolsManager(DroolsManager droolsManager)
public synchronized void update(IPersistent domainObject) throws ApplicationExceptions, FrameworkException
public synchronized void writeLog() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.soa.rules.WarningEventException**

```
public String getLocalizedMessage()
public String getMessage()
```
***
**org.jaffa.soa.services.ConfigurationService**

```
public SoaEventInfo[] getAllSoaEventInfo()
public static ConfigurationService getInstance()
public SoaEventInfo getSoaEventInfo(String soaEventName)
public static SoaEventManager getSoaEventManager()
public String[] getSoaEventNames()
public static void setSoaEventManager(SoaEventManager soaEventManager)
```
***
**org.jaffa.soa.services.EventMessageService**

```
public static EventMessage getEventMessage(SOAEventQueueMessage eventQueueMessage)
```
***
**org.jaffa.soa.services.InitSOAEventServer**

```
public void destroy()
public void init(ServletConfig cfg) throws ServletException
```
***
**org.jaffa.soa.services.RaiseEventService**

```
public void raiseSoaEvent(UOW uow, String eventName, String description, String category, List<HeaderParam> headerParams) throws FrameworkException, ApplicationExceptions
public void raiseSoaEvent(UOW uow, String eventName, String description, HeaderParam[] headerParams) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.soa.services.SOAEventEnabledConfiguration**

```
public boolean areEventsEnabledByDefault()
public List<String> getEventsInNonDefaultState()
public boolean isEnabled(String eventName)
public boolean isEnabled(SOAEventQueueMessage event)
public boolean isEnabled(SOAEvent event)
public boolean isEnabled(Object event)
public void setEnabled(String eventName, boolean isEnabled)
public void setEnabled(Map<String, Boolean> events)
```
***
**org.jaffa.soa.services.SOAEventEnabledConfigurationFactory**

```
public static synchronized SOAEventEnabledConfiguration instance()
public static synchronized void reload()
```
***
**org.jaffa.soa.services.SOAEventEnabledConfigurationImpl**

```
public boolean areEventsEnabledByDefault()
public List<String> getEventsInNonDefaultState()
public boolean isEnabled(String eventName)
public boolean isEnabled(SOAEventQueueMessage event)
public boolean isEnabled(SOAEvent event)
public boolean isEnabled(Object event)
public void setEnabled(String eventName, boolean isEnabled)
public void setEnabled(Map<String, Boolean> events)
```
***
**org.jaffa.soa.services.SOAEventEnabledConfigurationService**

```
public void reload()
public void start()
public void stop()
```
***
**org.jaffa.soa.services.SOAEventEnabledConfigurationServiceMBean**

```
public void reload()
public void start()
public void stop()
```
***
**org.jaffa.soa.services.SOAEventHandler**

```
public void invoke(UOW uow, SOAEventQueueMessage message) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.soa.services.SOAEventMessage**

No public methods.
***
**org.jaffa.soa.services.SOAEventPoller**

```
public String getScheduleTaskId()
public void poll(UOW uow, SOAEventPoller databean) throws FrameworkException, ApplicationException, ApplicationExceptions
public void setScheduleTaskId(String scheduleTaskId)
```
***
**org.jaffa.soa.services.SOAEventQueueMessage**

```
public String getCategory()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getDescription()
public String getEventId()
public String getEventName()
public HeaderParam getHeaderParam(String name)
public HeaderParam[] getHeaderParams()
public void setCategory(String category)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setDescription(String description)
public void setEventId(String eventId)
public void setEventName(String eventName)
public void setHeaderParams(HeaderParam[] headerParams)
```
***
**org.jaffa.soa.services.SOAEventServer**

```
public static String getEvent()
public static String getServerState()
public static DateTime getStartedOn()
public static void start()
public static void stop()
```
***
**org.jaffa.soa.services.SOAEventServerThread**

```
public String getEvent()
public ServerState getServerState()
public DateTime getStartedOn()
public void kill()
public void run()
```
***
**org.jaffa.soa.services.configdomain.InjectDomainFact**

```
public String getDomainClass()
public List<String> getDomainKey()
public void setDomainClass(String value)
```
***
**org.jaffa.soa.services.configdomain.ObjectFactory**

```
public InjectDomainFact createInjectDomainFact()
public Param createParam()
public SoaEventInfo createSoaEventInfo()
public SoaEvents createSoaEvents()
```
***
**org.jaffa.soa.services.configdomain.Param**

```
public String getDataType()
public String getDescription()
public String getName()
public void setDataType(String value)
public void setDescription(String value)
public void setName(String value)
```
***
**org.jaffa.soa.services.configdomain.SoaEventInfo**

```
public String getCategory()
public String getDescription()
public List<InjectDomainFact> getInjectDomainFact()
public String getLabel()
public String getName()
public List<Param> getParam()
public void setCategory(String value)
public void setDescription(String value)
public void setLabel(String value)
public void setName(String value)
```
***
**org.jaffa.soa.services.configdomain.SoaEvents**

```
public List<SoaEventInfo> getSoaEvent()
```
***
**org.jaffa.soa.services.event.EventMessage**

```
public String getCategory()
public String getCreatedBy()
public XMLGregorianCalendar getCreatedOn()
public String getDescription()
public String getEventId()
public String getEventName()
public List<HeaderParam> getHeaderParams()
public void setCategory(String value)
public void setCreatedBy(String value)
public void setCreatedOn(XMLGregorianCalendar value)
public void setDescription(String value)
public void setEventId(String value)
public void setEventName(String value)
```
***
**org.jaffa.soa.services.event.HeaderParam**

```
public String getName()
public String getValue()
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.soa.services.event.ObjectFactory**

```
public EventMessage createEventMessage()
public HeaderParam createHeaderParam()
```
***
**org.jaffa.soa.transformation.controller.BufferedServletInputStream**

```
public int available()
public int read()
public int read(byte[] buf, int off, int len)
```
***
**org.jaffa.soa.transformation.controller.SoapMessageFilter**

```
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig fc) throws ServletException
```
***
**org.jaffa.soa.transformation.controller.SoapRequestWrapper**

```
public ServletInputStream getInputStream() throws java.lang.IllegalStateException, java.io.IOException
public void setSoapMessage(String soapMessage)
```
***
**org.jaffa.soa.transformation.controller.SoapResponseWrapper**

```
public StringBuffer getBuffer()
public ServletOutputStream getOutputStream()
public PrintWriter getWriter()
public String toString()
```
***
**org.jaffa.soa.transformation.controller.StringOutputStream**

```
public void write(int c)
```
***
**org.jaffa.soa.transformation.controller.WebServiceController**

No public methods.
***
**org.jaffa.soa.transformation.helper.DozerBeanMapperSingletonWrapper**

```
public static DozerBeanMapper instance()
```
***
**org.jaffa.soa.transformation.helper.MappingHelper**

```
public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date)
public static XMLGregorianCalendar asXMLGregorianCalendarDateOnly(java.util.Date date)
public static List<U> mapList(final List<T> source, final Class<U> destType)
public static Object sourceToDestinationMapping(Object source, Class destination)
```
***
**org.jaffa.soa.transformation.helper.TransformationBean**

```
public String getRealEndPoint()
public String getTransformedMessage()
public void setRealEndPoint(String realEndPoint)
public void setTransformedMessage(String transformedMessage)
```
***
**org.jaffa.soa.transformation.helper.TransformationHelper**

```
public static String findFunction(String xmlString) throws Exception
public static String getEndPoint(MessageContext context)
public static String marshall(Object argument, Mapping mapping) throws Exception, RuntimeException
public static String transform(String xmlString, String xsltScript) throws Exception, RuntimeException
public static Object transform(Object source, Class target, String endPoint, String function, String direction) throws Exception, RuntimeException
public static TransformationBean transform(String soapMessage, String endPoint, String direction) throws Exception
public static Object unmarshall(Class clazz, String xmlString, boolean isArray) throws Exception, RuntimeException
```
***
**org.jaffa.soa.transformation.helper.XMLDateConverter**

```
public static XMLGregorianCalendar getCalendarFromDate(Date fromDate)
public static XMLGregorianCalendar getCalendarFromDate(DateOnly fromDate)
public static XMLGregorianCalendar getCalendarFromDate(DateTime fromDate)
public static XMLGregorianCalendar getCalendarFromString(String fromDate)
```
***
**org.jaffa.soa.transformation.helper.XMLGregorianCalendarConverter**

```
public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass)
```
***
**org.jaffa.soa.transformation.meta.Config**

```
public List<Mapping> getMapping()
```
***
**org.jaffa.soa.transformation.meta.Mapping**

```
public String getDirection()
public String getEndpoint()
public String getFunction()
public String getInboundSchema()
public String getPath()
public String getQName()
public String getRootElement()
public String getXsltscript()
public Boolean isArray()
public void setArray(Boolean value)
public void setDirection(String value)
public void setEndpoint(String value)
public void setFunction(String value)
public void setInboundSchema(String value)
public void setPath(String value)
public void setQName(String value)
public void setRootElement(String value)
public void setXsltscript(String value)
```
***
**org.jaffa.soa.transformation.meta.MetaDataRepository**

```
public Map<String, String> getEndPointMapping()
public static MetaDataRepository getInstance()
public Mapping getMapping(String key)
```
***
**org.jaffa.soa.transformation.meta.ObjectFactory**

```
public Config createConfig()
public Mapping createMapping()
```
***
**org.jaffa.transaction.apis.TransactionService**

```
public String[] getSubTypeNames()
public String[] getTypeNames()
public boolean hasAdminAccess(String typeName)
public boolean hasBrowseAccess(String typeName)
public TransactionGraph localUpdate(String path, TransactionGraph graph, UOW uow) throws FrameworkException, ApplicationExceptions
public TransactionQueryResponse query(TransactionCriteria criteria)
public TransactionUpdateResponse[] update(TransactionGraph[] graphs)
```
***
**org.jaffa.transaction.apis.data.TransactionCriteria**

```
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDirection()
public StringCriteriaField getErrorMessage()
public StringCriteriaField getId()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getStatus()
public StringCriteriaField getSubType()
public TransactionFieldCriteria[] getTransactionFields()
public StringCriteriaField getType()
public Criteria returnQueryClause(Criteria nestedClause)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDirection(StringCriteriaField direction)
public void setErrorMessage(StringCriteriaField errorMessage)
public void setId(StringCriteriaField id)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setStatus(StringCriteriaField status)
public void setSubType(StringCriteriaField subType)
public void setTransactionFields(TransactionFieldCriteria[] transactionFields)
public void setType(StringCriteriaField type)
```
***
**org.jaffa.transaction.apis.data.TransactionDependencyGraph**

```
public String getDependsOnId()
public String getStatus()
public void setDependsOnId(String dependsOnId)
public void setStatus(String status)
```
***
**org.jaffa.transaction.apis.data.TransactionFieldCriteria**

```
public String getFieldName()
public StringCriteriaField getValue()
public void setFieldName(String fieldName)
public void setValue(StringCriteriaField value)
```
***
**org.jaffa.transaction.apis.data.TransactionFieldGraph**

```
public String getFieldName()
public String getValue()
public void setFieldName(String fieldName)
public void setValue(String value)
```
***
**org.jaffa.transaction.apis.data.TransactionGraph**

```
public String getCreatedBy()
public DateTime getCreatedOn()
public String getDirection()
public String getErrorMessage()
public String getId()
public String getLastChangedBy()
public DateTime getLastChangedOn()
public String getStatus()
public String getSubType()
public TransactionDependencyGraph[] getTransactionDependencies()
public TransactionFieldGraph[] getTransactionFields()
public TransactionPayloadGraph getTransactionPayload()
public String getType()
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setDirection(String direction)
public void setErrorMessage(String errorMessage)
public void setId(String id)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedOn(DateTime lastChangedOn)
public void setStatus(String status)
public void setSubType(String subType)
public void setTransactionDependencies(TransactionDependencyGraph[] transactionDependencies)
public void setTransactionFields(TransactionFieldGraph[] transactionFields)
public void setTransactionPayload(TransactionPayloadGraph transactionPayload)
public void setType(String type)
```
***
**org.jaffa.transaction.apis.data.TransactionPayloadGraph**

```
public byte[] getExternalMessage()
public byte[] getInternalMessage()
public String getInternalMessageClass()
public void setExternalMessage(byte[] externalMessage)
public void setInternalMessage(byte[] internalMessage)
public void setInternalMessageClass(String internalMessageClass)
```
***
**org.jaffa.transaction.apis.data.TransactionQueryResponse**

```
public TransactionGraph[] getGraphs()
public void setGraphs(TransactionGraph[] graphs)
```
***
**org.jaffa.transaction.apis.data.TransactionUpdateResponse**

```
public TransactionGraph getSource()
public void setSource(TransactionGraph source)
```
***
**org.jaffa.transaction.apis.impl.TransactionHandler**

No public methods.
***
**org.jaffa.transaction.daos.ITransactionMessageDAOFactory**

```
public T getDAO(Class<T> type)
```
***
**org.jaffa.transaction.daos.TransactionDAOBase**

No public methods.
***
**org.jaffa.transaction.daos.TransactionMessageDAO**

No public methods.
***
**org.jaffa.transaction.daos.TransactionMessageDAOFactory**

```
public static T getDAO(Class<T> type)
public static TransactionMessageDAO getTransactionMessageDAO()
public static TransactionMessageService getTransactionMessageService()
public static void setDaoFactory(ITransactionMessageDAOFactory daoFactory)
```
***
**org.jaffa.transaction.daos.TransactionMessageService**

No public methods.
***
**org.jaffa.transaction.daos.impl.JaffaTransactionMessageService**

```
public void delete(UOW uow, Transaction transaction) throws FrameworkException
public void delete(UOW uow, TransactionDependency transactionDependency) throws FrameworkException
public List<String> filterSetOfTxIdsToOpenOrInProgress(List<String> transactionIds) throws FrameworkException
public List<Transaction> findByFieldValueOrderByCreatedOnDesc(String field, String value) throws FrameworkException
public long getCountByType(String type) throws FrameworkException
public long getCountInboundOpenInProgress(String field, String value, String type, String subType) throws FrameworkException
public long getCountNotSatisfiedOrInError(String field, String value, String type) throws FrameworkException
public long getCountOpenInProgress(String field, String value) throws FrameworkException
public long getCountSatisfiedOrInError(String field, String value, String type) throws FrameworkException
public long getCountTxInError(List<String> transactionIds) throws FrameworkException
public Collection<TransactionDependency> getDependenciesByDependsOnId(String transactionId) throws FrameworkException
public Collection<TransactionDependencySweeperView> getDependencySweeperViewsIfOpenNoDependsOnTx() throws FrameworkException
public TransactionField getFieldByNameValue(String fieldName, String fieldValue) throws FrameworkException
public Collection<Transaction> getInboundByTypeSubTypeAndFields(String type, String subType, HashMap<String, String> fields) throws FrameworkException
public DateTime getLastErrorTimeByType(String type) throws FrameworkException
public Collection<TransactionDependency> getOpenDependenciesByDependsOnId(String transactionId) throws FrameworkException
public Collection<Transaction> getOpenInProgressLastChangedBefore(DateTime input) throws FrameworkException
public List<Transaction> getOpenOutboundByTypeOrderByCreatedOnDesc(String type) throws FrameworkException
public long getOpenTransactionDependencyCount(String transactionId) throws FrameworkException
public TransactionSweeperView getSweeperViewIfOnHoldNoOpenDependency(String transactionId) throws FrameworkException
public Collection<TransactionSweeperView> getSweeperViewsIfOnHoldNoOpenDependency() throws FrameworkException
public Transaction getTransaction(String id) throws FrameworkException
public Transaction getTransactionByFields(HashMap<String, String> fields) throws FrameworkException
public Transaction getTransactionByFieldsAndBeginsWithFields(HashMap<String, String> fields, HashMap<String, String> beginsWith) throws FrameworkException
public TransactionStatusCount getTransactionCountPerStatusByType(String type) throws FrameworkException
public Map<String, TransactionStatusCount> getTransactionCountPerStatusForTypes(String[] types) throws FrameworkException
public Collection<TransactionDependency> getTransactionDependencies(String transactionId) throws FrameworkException
public TransactionDependency getTransactionDependency(String transactionId, String dependsOnId) throws FrameworkException
public TransactionField getTransactionField(String transactionId, String fieldName) throws FrameworkException
public Collection<TransactionField> getTransactionFields(String transactionId) throws FrameworkException
public TransactionPayload getTransactionPayload(String transactionPayloadId) throws FrameworkException
public TransactionPayload getTransactionPayloadByTransactionId(String transactionId) throws FrameworkException
public Collection<Transaction> getTransactionsByField(String fieldName, String value) throws FrameworkException
public Collection<Transaction> getTransactionsByFields(HashMap<String, List<Serializable>> fields) throws FrameworkException
public Collection<Transaction> getTransactionsByFieldsOred(HashMap<String, Serializable> fields) throws FrameworkException
public List<Transaction> getTransactionsByTypeSubTypeFieldsOrderBy(String type, String subType, HashMap<String, List<String>> fields, LinkedHashMap<String, Boolean> orderBy) throws FrameworkException
public Transaction save(UOW uow, Transaction transaction) throws FrameworkException
public TransactionPayload save(UOW uow, TransactionPayload transactionPayload) throws FrameworkException
public TransactionField save(UOW uow, TransactionField transactionField) throws FrameworkException
public TransactionDependency save(UOW uow, TransactionDependency transactionDependency) throws FrameworkException
```
***
**org.jaffa.transaction.domain.CompositeKey**

```
public boolean equals(Object obj)
public java.lang.String getDependsOnId()
public java.lang.String getStatus()
public java.lang.String getTransactionId()
public int hashCode()
public void setDependsOnId(java.lang.String dependsOnId)
public void setStatus(java.lang.String status)
public void setTransactionId(java.lang.String transactionId)
```
***
**org.jaffa.transaction.domain.Transaction**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String id) throws FrameworkException
public static Transaction findByPK(UOW uow, java.lang.String id) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String id)
public Criteria findTransactionDependencyCriteria()
public Criteria findTransactionFieldCriteria()
public void generateKey() throws ApplicationExceptions, FrameworkException
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDirection()
public java.lang.String getErrorMessage()
public java.lang.String getId()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getStatus()
public java.lang.String getSubType()
public TransactionDependency[] getTransactionDependencyArray() throws FrameworkException
public TransactionField[] getTransactionFieldArray() throws FrameworkException
public TransactionInfo getTransactionInfo()
public TransactionPayload getTransactionPayloadObject() throws FrameworkException
public java.lang.String getType()
public UserContext getUserContext()
public TransactionDependency newTransactionDependencyObject() throws ValidationException, FrameworkException
public TransactionField newTransactionFieldObject() throws ValidationException, FrameworkException
public TransactionPayload newTransactionPayloadObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void postAdd() throws PostAddFailedException
public void postDelete() throws PostDeleteFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDirection(java.lang.String direction) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setErrorMessage(java.lang.String errorMessage) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setId(java.lang.String id) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSubType(java.lang.String subType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTransactionDependencyCollection(Collection transactionDependencyCollection)
public void setTransactionFieldCollection(Collection transactionFieldCollection)
public void setTransactionInfo(TransactionInfo transactionInfo)
public void setTransactionPayload(TransactionPayload transactionPayload) throws FrameworkException
public void setType(java.lang.String type) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUOW(UOW uow)
public void setUserContext(UserContext userContext)
public void stampError(Exception exception) throws ValidationException, FrameworkException
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDirection(java.lang.String direction) throws ValidationException, FrameworkException
public java.lang.String validateErrorMessage(java.lang.String errorMessage) throws ValidationException, FrameworkException
public java.lang.String validateId(java.lang.String id) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateStatus(java.lang.String status) throws ValidationException, FrameworkException
public java.lang.String validateSubType(java.lang.String subType) throws ValidationException, FrameworkException
public java.lang.String validateType(java.lang.String type) throws ValidationException, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionDependency**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String transactionId, java.lang.String dependsOnId) throws FrameworkException
public static TransactionDependency findByPK(UOW uow, java.lang.String transactionId, java.lang.String dependsOnId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String transactionId, java.lang.String dependsOnId)
public java.lang.String getDependsOnId()
public Transaction getDependsOnTransactionObject() throws ValidationException, FrameworkException
public java.lang.String getStatus()
public java.lang.String getTransactionId()
public Transaction getTransactionObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void postDelete() throws PostDeleteFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void setDependsOnId(java.lang.String dependsOnId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateDependsOnId(java.lang.String dependsOnId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateDependsOnId(java.lang.String dependsOnId) throws ValidationException, FrameworkException
public java.lang.String validateStatus(java.lang.String status) throws ValidationException, FrameworkException
public java.lang.String validateTransactionId(java.lang.String transactionId) throws ValidationException, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionDependencyMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.transaction.domain.TransactionDependencySweeperView**

```
public CompositeKey getCompositeKey()
public java.lang.String getDependsOnId()
public java.lang.String getStatus()
public java.lang.String getTransactionId()
public void setCompositeKey(CompositeKey compositeKey)
public void setDependsOnId(java.lang.String dependsOnId)
public void setStatus(java.lang.String status)
public void setTransactionId(java.lang.String transactionId)
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionDependencySweeperViewMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.transaction.domain.TransactionField**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String transactionId, java.lang.String fieldName) throws FrameworkException
public static TransactionField findByPK(UOW uow, java.lang.String transactionId, java.lang.String fieldName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String transactionId, java.lang.String fieldName)
public java.lang.String getFieldName()
public java.lang.String getTransactionId()
public Transaction getTransactionObject() throws ValidationException, FrameworkException
public java.lang.String getValue()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void setFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.String validateTransactionId(java.lang.String transactionId) throws ValidationException, FrameworkException
public java.lang.String validateValue(java.lang.String value) throws ValidationException, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionFieldMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.transaction.domain.TransactionMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.transaction.domain.TransactionPayload**

```
public void addInternalPayload(Object payload) throws ValidationException, FrameworkException
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String id) throws FrameworkException
public static TransactionPayload findByPK(UOW uow, java.lang.String id) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String id)
public byte[] getExternalMessage()
public java.lang.String getId()
public byte[] getInternalMessage()
public java.lang.String getInternalMessageClass()
public Transaction getTransactionObject() throws ValidationException, FrameworkException
public Object moldInternalPayload()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public String returnInternalPayload()
public void setExternalMessage(byte[] externalMessage) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setId(java.lang.String id) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setInternalMessage(byte[] internalMessage) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setInternalMessageClass(java.lang.String internalMessageClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateExternalMessage(byte[] externalMessage) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateId(java.lang.String id) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateInternalMessage(byte[] internalMessage) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateInternalMessageClass(java.lang.String internalMessageClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public byte[] validateExternalMessage(byte[] externalMessage) throws ValidationException, FrameworkException
public java.lang.String validateId(java.lang.String id) throws ValidationException, FrameworkException
public byte[] validateInternalMessage(byte[] internalMessage) throws ValidationException, FrameworkException
public java.lang.String validateInternalMessageClass(java.lang.String internalMessageClass) throws ValidationException, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionPayloadMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.transaction.domain.TransactionStatusCount**

```
public long getErrorCount()
public long getHoldCount()
public long getInProcessCount()
public long getInterruptedCount()
public long getOpenCount()
public long getSuccessCount()
public long getTotalCount()
public void setErrorCount(long errorCount)
public void setHoldCount(long holdCount)
public void setInProcessCount(long inProcessCount)
public void setInterruptedCount(long interruptedCount)
public void setOpenCount(long openCount)
public void setSuccessCount(long successCount)
```
***
**org.jaffa.transaction.domain.TransactionSweeperView**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String id) throws FrameworkException
public static TransactionSweeperView findByPK(UOW uow, java.lang.String id) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String id)
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getId()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setId(java.lang.String id) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateId(java.lang.String id) throws ValidationException, FrameworkException
```
***
**org.jaffa.transaction.domain.TransactionSweeperViewMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static FieldMetaData[] getKeyFields()
public static String getLabelToken()
public static FieldMetaData[] getMandatoryFields()
public static String getName()
```
***
**org.jaffa.transaction.services.ConfigurationService**

```
public TransactionInfo[] getAllTransactionInfo()
public static ConfigurationService getInstance()
public TransactionInfo getTransactionInfo(Object dataBean)
public TransactionInfo getTransactionInfo(String dataBeanClassName) throws ClassNotFoundException
public TransactionInfo getTransactionInfo(Class dataBeanClass)
public static TransactionManager getTransactionManager()
public TypeInfo getTypeInfo(String typeName)
public String[] getTypeNames()
public boolean isTypeSingleton(final String typeName)
public static void setTransactionManager(TransactionManager transactionManager)
```
***
**org.jaffa.transaction.services.HeaderParam**

```
public String getName()
public String getValue()
public void setName(String name)
public void setValue(String value)
```
***
**org.jaffa.transaction.services.IHasHeaderParams**

```
public HeaderParam[] getHeaderParams()
public void setHeaderParams(HeaderParam[] headerParams)
```
***
**org.jaffa.transaction.services.ILockCheckFilter**

```
public Collection<Transaction> getFilteredResults(Object dataBean, TransactionMessageService transactionDAO) throws FrameworkException
```
***
**org.jaffa.transaction.services.ITransactionMessagingEngineFactory**

No public methods.
***
**org.jaffa.transaction.services.InterruptedException**

No public methods.
***
**org.jaffa.transaction.services.JaffaTransactionFrameworkException**

No public methods.
***
**org.jaffa.transaction.services.LockingService**

```
public static void checkLock(Object dataBean) throws FrameworkException, ApplicationExceptions
public static void checkLock(UOW uow, Object dataBean) throws FrameworkException, ApplicationExceptions
public static void checkLock(UOW uow, Object dataBean, TransactionInfo transactionInfo) throws FrameworkException, ApplicationExceptions
public static void checkLock(UOW uow, Object dataBean, TransactionInfo transactionInfo, String transactionId) throws FrameworkException, ApplicationExceptions
public static void deleteLockingTransactions(Object dataBean) throws FrameworkException, ApplicationExceptions
public static void deleteLockingTransactions(UOW uow, Object dataBean) throws FrameworkException, ApplicationExceptions
public static void deleteLockingTransactions(UOW uow, Object dataBean, TransactionInfo transactionInfo) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.transaction.services.LoggingService**

```
public static void setLoggingContext(Object payload, TransactionInfo transactionInfo, Transaction transaction)
public static void unsetLoggingContext()
```
***
**org.jaffa.transaction.services.TransactionAdmin**

```
public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs)
public MessageQueryResponse messageQuery(MessageCriteria criteria)
public QueueQueryResponse queueQuery(QueueCriteria criteria)
public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs)
public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs)
```
***
**org.jaffa.transaction.services.TransactionConsumer**

```
public void process(TransactionMessage transactionMessage) throws Exception
public UOW process(UOW uow, String transactionId, Transaction unsavedTransaction) throws Exception
public UOW process(UOW uow, TransactionMessage transactionMessage) throws Exception
```
***
**org.jaffa.transaction.services.TransactionDependencySweeper**

```
public String getLoggedBy()
public String getScheduleTaskId()
public void poll(UOW uow, TransactionDependencySweeper databean) throws FrameworkException, ApplicationException, ApplicationExceptions
public void setLoggedBy(String loggedBy)
public void setScheduleTaskId(String scheduleTaskId)
```
***
**org.jaffa.transaction.services.TransactionEngine**

```
public void createTransactionDependency(UOW uow, Transaction transaction, String dependsOnId) throws ApplicationException, ApplicationExceptions, FrameworkException
public void deleteTransaction(UOW uow, String id) throws FrameworkException, ApplicationExceptions
public String[] getAccessibleSubTypeNames()
public String[] getAccessibleTypeNames()
public String[] getAccessibleTypeNamesWithLabels()
public static TransactionEngine getInstance()
public boolean hasAdminAccess(String typeName)
public boolean hasBrowseAccess(String typeName)
public static String obtainParamValue(Param param, Object dataBean) throws FrameworkException, ApplicationExceptions
public static void sendFailureNotification(Transaction transaction, Exception exception) throws MessagingException, FrameworkException
public void updateTransactionStatusToError(String id, Exception exception) throws FrameworkException, ApplicationExceptions
public void updateTransactionStatusToInProcess(String id) throws FrameworkException, ApplicationExceptions
public void updateTransactionStatusToInProcess(Transaction transaction) throws FrameworkException, ApplicationExceptions
public void updateTransactionStatusToInProcess(UOW uow, String id) throws FrameworkException, ApplicationExceptions
public void updateTransactionStatusToInProcess(UOW uow, Transaction transaction) throws FrameworkException, ApplicationExceptions
public void updateTransactionStatusToSatisfied(UOW uow, String id) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.transaction.services.TransactionMessage**

```
public String getId()
public String getQueueName()
public String getTopicName()
public String getType()
public void setId(String id)
public void setQueueName(String queueName)
public void setTopicName(String topicName)
public void setType(String type)
public String toString()
```
***
**org.jaffa.transaction.services.TransactionMessagingEngine**

```
public String add(Object payload, String[] dependsOnIds, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public String addOutbound(Object payload) throws FrameworkException, ApplicationExceptions
public Transaction addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public void commit() throws FrameworkException, ApplicationExceptions
public static IMessagingEngine getInstance(UOW uow) throws FrameworkException, ApplicationExceptions
public ISender getSender()
public void prepareDeletesForCommit(Collection deletes)
public void rollback() throws FrameworkException, ApplicationExceptions
public static void setFactory(ITransactionMessagingEngineFactory factory)
public void setSender(ISender sender)
```
***
**org.jaffa.transaction.services.configdomain.Config**

```
public List<Object> getTransactionOrType()
```
***
**org.jaffa.transaction.services.configdomain.DisplayParam**

```
public String getLabel()
public String getName()
public void setLabel(String value)
public void setName(String value)
```
***
**org.jaffa.transaction.services.configdomain.Filter**

```
public String getClassName()
public String getValue()
public void setClassName(String value)
public void setValue(String value)
```
***
**org.jaffa.transaction.services.configdomain.Header**

```
public List<Param> getParam()
```
***
**org.jaffa.transaction.services.configdomain.LockCheck**

```
public Filter getFilter()
public List<Param> getParam()
public void setFilter(Filter value)
```
***
**org.jaffa.transaction.services.configdomain.ObjectFactory**

```
public Config createConfig()
public DisplayParam createDisplayParam()
public Filter createFilter()
public Header createHeader()
public LockCheck createLockCheck()
public Param createParam()
public Security createSecurity()
public TransactionInfo createTransactionInfo()
public TypeInfo createTypeInfo()
```
***
**org.jaffa.transaction.services.configdomain.Param**

```
public String getLanguage()
public LoggingName getLoggingName()
public String getName()
public String getValue()
public boolean isExpression()
public void setExpression(Boolean value)
public void setLanguage(String value)
public void setLoggingName(LoggingName value)
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.transaction.services.configdomain.Security**

```
public String getAdmin()
public String getBrowse()
public void setAdmin(String value)
public void setBrowse(String value)
```
***
**org.jaffa.transaction.services.configdomain.TransactionInfo**

```
public String getDataBean()
public String getDescription()
public Header getHeader()
public LockCheck getLockCheck()
public String getSubType()
public String getToClass()
public String getToMethod()
public String getType()
public void setDataBean(String value)
public void setDescription(String value)
public void setHeader(Header value)
public void setLockCheck(LockCheck value)
public void setSubType(String value)
public void setToClass(String value)
public void setToMethod(String value)
public void setType(String value)
```
***
**org.jaffa.transaction.services.configdomain.TypeInfo**

```
public String getDescription()
public List<DisplayParam> getDisplayParam()
public String getLabel()
public String getName()
public String getQueueName()
public Security getSecurity()
public Boolean getSingleton()
public void setDescription(String value)
public void setLabel(String value)
public void setName(String value)
public void setQueueName(String value)
public void setSecurity(Security value)
public void setSingleton(Boolean singleton)
```
***
**org.jaffa.transaction.tester.CreateGraphTest**

```
public List<GraphUpdateResponse> getUpdateResponses()
public void setUpdateResponses(List<GraphUpdateResponse> updateResponses)
public List testGraphCreate(String userId, long count, int poolSize, GraphDataObject[] graph, Class serviceClazz)
```
***
**org.jaffa.transaction.tester.GraphCreateThread**

```
public void run()
```
***
**org.jaffa.transaction.tester.SoaEventTest**

```
public void testSoaEvent(String userId, String soaEventParamName, String soaEventParamValue, String eventName, String eventDesc, long count, int poolSize)
```
***
**org.jaffa.transaction.tester.TestConsumer**

```
public void invoke(UOW uow, TestMessage message) throws Exception
public void invoke(UOW uow, TestMessageSingleton message) throws Exception
```
***
**org.jaffa.transaction.tester.TestMessage**

```
public long getDbQuery()
public long getDelay()
public String getEmailAddress()
public boolean isFail()
public void setDbQuery(long dbQuery)
public void setDelay(long delay)
public void setEmailAddress(String emailAddress)
public void setFail(boolean fail)
```
***
**org.jaffa.transaction.tester.TestMessageSingleton**

```
public long getDbQuery()
public long getDelay()
public String getEmailAddress()
public boolean isFail()
public void setDbQuery(long dbQuery)
public void setDelay(long delay)
public void setEmailAddress(String emailAddress)
public void setFail(boolean fail)
```
***
**org.jaffa.transaction.tester.TestThread**

```
public void run()
```
