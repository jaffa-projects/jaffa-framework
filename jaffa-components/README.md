# jaffa-components Public Methods

***
**org.apache.log4j.jdbcplus.ExpressionCache**

```
public ExpressionEvaluator get(final String key)
public static ExpressionCache getInstance()
public synchronized void put(final String key, final ExpressionEvaluator value)
```
***
**org.apache.log4j.jdbcplus.ExpressionCacheTest**

```
public void setUp()
public void testDecide()
```
***
**org.apache.log4j.jdbcplus.JDBCAppender**

```
public void append(LoggingEvent event)
public void close()
public void flush_buffer()
public String getBuffer()
public boolean getCommit()
public JDBCConnectionHandler getConnectionHandler()
public String getConnector()
public String getDatasourceJNDIName()
public String getDbclass()
public String getLayoutPartsDelimiter()
public String getPassword()
public String getProcedure()
public String getSql()
public String getTable()
public int getThrowableMaxChars()
public String getUrl()
public String getUsername()
public boolean isQuoteReplace()
public boolean isUsePreparedStatements()
public boolean ready()
public boolean requiresLayout()
public void setBuffer(String value)
public void setColumn(String column, int logtype, Object value, String type, int sqlType)
public void setColumn(String value)
public void setCommit(boolean value)
public void setConnectionHandler(JDBCConnectionHandler connectionHandler)
public void setConnector(String value)
public void setDatasourceJNDIName(String datasourceJNDIName)
public void setDbclass(String value)
public void setLayout(Layout layout)
public void setLayoutPartsDelimiter(String c)
public void setPassword(String value)
public void setProcedure(String procedure)
public void setQuoteReplace(boolean b)
public void setSql(String value)
public void setSqlHandler(JDBCSqlHandler sqlHandler) throws Exception
public void setSqlhandler(String value)
public void setTable(String value)
public void setThrowableMaxChars(int throwableMaxChars)
public void setUrl(String value)
public void setUsePreparedStatements(boolean usePreparedStatements)
public void setUsername(String value)
```
***
**org.apache.log4j.jdbcplus.JDBCAppenderConfig**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCColumnHandler**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCColumnStorage**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCConnectionHandler**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCDefaultConnectionHandler**

```
public void freeConnection(Connection con) throws Exception
public Connection getConnection() throws Exception
public Connection getConnection(String _url, String _username, String _password) throws Exception
public Connection getConnection(String datasourceJNDIName)
public String getPassword()
public String getUrl()
public String getUsername()
public void setPassword(String string)
public void setUrl(String string)
public void setUsername(String string)
```
***
**org.apache.log4j.jdbcplus.JDBCIDHandler**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCLogColumn**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCLogType**

```
public static boolean isLogType(int _lt)
public static boolean isLogType(String _lt)
public static int parseLogType(String _lt)
```
***
**org.apache.log4j.jdbcplus.JDBCLogger**

```
public void append(LoggingEvent event, Layout layout) throws Exception
public void appendSQL(LoggingEvent aEvent, Layout layout) throws Exception
public void freeConnection() throws Exception
public String getErrorMsg()
public String getLayoutPartsDelimiter()
public String getProcedure()
public int getThrowableMaxChars()
public String getThrowableRepresentationFromLoggingEvent(LoggingEvent aLoggingEvent)
public boolean isCommit()
public boolean isConfigured()
public boolean isConnected()
public boolean isQuoteReplace()
public boolean isUsePreparedStatements()
public boolean prepareConnection() throws Exception
public boolean ready()
public String replace(String source, String find, String replacement)
public String replace(String source, String find, int replacement)
public String replace(String source, int start, int end, String replacement)
public String replace(String source, int start, int end, int replacement)
public void setCommit(boolean b)
public void setConnection(Object obj) throws Exception
public void setLayoutPartsDelimiter(String c)
public void setLogType(String _name, int _logtype, Object _value) throws Exception
public void setProcedure(String procedure, ArrayList columns) throws Exception
public void setQuoteReplace(boolean b)
public void setSQL(String _sql) throws Exception
public void setSqlHandler(JDBCSqlHandler sqlHandler) throws Exception
public void setTable(String _table) throws Exception
public void setThrowableMaxChars(int throwableMaxChars)
public void setUsePreparedStatements(boolean usePreparedStatements)
public void tryToFreeConnection()
```
***
**org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler**

No public methods.
***
**org.apache.log4j.jdbcplus.JDBCSequencer**

```
public synchronized Object getID()
```
***
**org.apache.log4j.jdbcplus.JDBCSqlHandler**

No public methods.
***
**org.apache.log4j.jdbcplus.MDCFilter**

```
public int decide(final LoggingEvent event)
public String getExpression()
public String getKeys()
public void setExpression(final String expression)
public void setKeys(final String keys)
```
***
**org.apache.log4j.jdbcplus.MDCFilterTest**

```
public void decide()
public void setUp()
```
***
**org.apache.log4j.jdbcplus.VMIDHandler**

```
public Object getID()
```
***
**org.apache.log4j.jdbcplus.helper.JDBCIDHandler**

```
public Object getID()
```
***
**org.jaffa.components.attachment.apis.AttachmentFactory**

```
public static IAttachmentData getAttachmentDataImpl()
public static void setAttachmentDataImplFactory(IAttachmentDataImplFactory attachmentDataImplFactory)
```
***
**org.jaffa.components.attachment.apis.AttachmentService**

```
public static AttachmentUpdateResponse cloneAttachment(UOW uow, String oldSerializedKey, String newSerializedKey) throws FrameworkException, ApplicationExceptions
public static void deleteAttachment(UOW uow, String serializedKey) throws FrameworkException, ApplicationExceptions
public AttachmentQueryResponse query(AttachmentCriteria criteria)
public AttachmentUpdateResponse[] update(AttachmentGraph[] graphs)
```
***
**org.jaffa.components.attachment.apis.IAttachmentData**

```
public boolean create(String documentReferenceId, byte[] data)
public void delete(String documentReferenceId)
public byte[] read(String documentReferenceId)
```
***
**org.jaffa.components.attachment.apis.IAttachmentDataImplFactory**

```
public IAttachmentData getAttachmentDataImpl()
```
***
**org.jaffa.components.attachment.apis.data.AttachmentCriteria**

```
public StringCriteriaField getAttachmentId()
public StringCriteriaField getAttachmentType()
public StringCriteriaField getContentType()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOriginalFileName()
public StringCriteriaField getRemarks()
public StringCriteriaField getSerializedKey()
public StringCriteriaField getSupercededBy()
public Criteria returnQueryClause(Criteria nestedClause)
public void setAttachmentId(StringCriteriaField attachmentId)
public void setAttachmentType(StringCriteriaField attachmentType)
public void setContentType(StringCriteriaField contentType)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOriginalFileName(StringCriteriaField originalFileName)
public void setRemarks(StringCriteriaField remarks)
public void setSerializedKey(StringCriteriaField serializedKey)
public void setSupercededBy(StringCriteriaField supercededBy)
```
***
**org.jaffa.components.attachment.apis.data.AttachmentGraph**

```
public String getAttachmentId()
public String getAttachmentType()
public String getContentType()
public String getCreatedBy()
public DateTime getCreatedOn()
public byte[] getData()
public String getDescription()
public String getDocumentReferenceId()
public String getLastChangedBy()
public DateTime getLastChangedOn()
public String getOriginalFileName()
public String getRemarks()
public String getSerializedKey()
public String getSupercededBy()
public void setAttachmentId(String attachmentId)
public void setAttachmentType(String attachmentType)
public void setContentType(String contentType)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setData(byte[] data)
public void setDescription(String description)
public void setDocumentReferenceId(String documentReferenceId)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedOn(DateTime lastChangedOn)
public void setOriginalFileName(String originalFileName)
public void setRemarks(String remarks)
public void setSerializedKey(String serializedKey)
public void setSupercededBy(String supercededBy)
```
***
**org.jaffa.components.attachment.apis.data.AttachmentQueryResponse**

```
public AttachmentGraph[] getGraphs()
public void setGraphs(AttachmentGraph[] graphs)
```
***
**org.jaffa.components.attachment.apis.data.AttachmentUpdateResponse**

```
public AttachmentGraph getSource()
public void setSource(AttachmentGraph source)
```
***
**org.jaffa.components.attachment.apis.impl.AttachmentHandler**

```
public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.attachment.components.attachmentfinder.IAttachmentFinder**

```
public void destroy()
public AttachmentFinderOutDto find(AttachmentFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderInDto**

```
public StringCriteriaField getAttachmentId()
public StringCriteriaField getAttachmentType()
public StringCriteriaField getContentType()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOriginalFileName()
public StringCriteriaField getRemarks()
public StringCriteriaField getSerializedKey()
public StringCriteriaField getSupercededBy()
public void setAttachmentId(StringCriteriaField attachmentId)
public void setAttachmentType(StringCriteriaField attachmentType)
public void setContentType(StringCriteriaField contentType)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOriginalFileName(StringCriteriaField originalFileName)
public void setRemarks(StringCriteriaField remarks)
public void setSerializedKey(StringCriteriaField serializedKey)
public void setSupercededBy(StringCriteriaField supercededBy)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutDto**

```
public void addRows(AttachmentFinderOutRowDto rows)
public void clearRows()
public AttachmentFinderOutRowDto getRows(int index)
public AttachmentFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(AttachmentFinderOutRowDto rows)
public void setRows(AttachmentFinderOutRowDto rows, int index)
public void setRows(AttachmentFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto**

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
**org.jaffa.components.attachment.components.attachmentfinder.tx.AttachmentFinderTx**

```
public void destroy()
public AttachmentFinderOutDto find(AttachmentFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentfinder.ui.AttachmentFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_ViewAttachmentData_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.components.attachment.components.attachmentfinder.ui.AttachmentFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
public String getAttachmentId()
public String getAttachmentIdDd()
public String getAttachmentType()
public String getAttachmentTypeDd()
public String getContentType()
public String getContentTypeDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getDescription()
public String getDescriptionDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOriginalFileName()
public String getOriginalFileNameDd()
public String getRemarks()
public String getRemarksDd()
public String getSerializedKey()
public String getSerializedKeyDd()
public String getSupercededBy()
public String getSupercededByDd()
public void quit()
public void setAttachmentId(String attachmentId)
public void setAttachmentIdDd(String attachmentIdDd)
public void setAttachmentType(String attachmentType)
public void setAttachmentTypeDd(String attachmentTypeDd)
public void setContentType(String contentType)
public void setContentTypeDd(String contentTypeDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOriginalFileName(String originalFileName)
public void setOriginalFileNameDd(String originalFileNameDd)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setSerializedKey(String serializedKey)
public void setSerializedKeyDd(String serializedKeyDd)
public void setSupercededBy(String supercededBy)
public void setSupercededByDd(String supercededByDd)
public FormKey updateObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.attachment.components.attachmentfinder.ui.AttachmentFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getAttachmentId()
public String getAttachmentIdDd()
public DropDownModel getAttachmentIdDdWM()
public EditBoxModel getAttachmentIdWM()
public String getAttachmentType()
public String getAttachmentTypeDd()
public DropDownModel getAttachmentTypeDdWM()
public EditBoxModel getAttachmentTypeWM()
public String getContentType()
public String getContentTypeDd()
public DropDownModel getContentTypeDdWM()
public EditBoxModel getContentTypeWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOriginalFileName()
public String getOriginalFileNameDd()
public DropDownModel getOriginalFileNameDdWM()
public EditBoxModel getOriginalFileNameWM()
public String getRemarks()
public String getRemarksDd()
public DropDownModel getRemarksDdWM()
public EditBoxModel getRemarksWM()
public String getSerializedKey()
public String getSerializedKeyDd()
public DropDownModel getSerializedKeyDdWM()
public EditBoxModel getSerializedKeyWM()
public String getSupercededBy()
public String getSupercededByDd()
public DropDownModel getSupercededByDdWM()
public EditBoxModel getSupercededByWM()
public void populateRows(GridModel rows)
public void setAttachmentId(String attachmentId)
public void setAttachmentIdDd(String attachmentIdDd)
public void setAttachmentIdDdWV(String value)
public void setAttachmentIdWV(String value)
public void setAttachmentType(String attachmentType)
public void setAttachmentTypeDd(String attachmentTypeDd)
public void setAttachmentTypeDdWV(String value)
public void setAttachmentTypeWV(String value)
public void setContentType(String contentType)
public void setContentTypeDd(String contentTypeDd)
public void setContentTypeDdWV(String value)
public void setContentTypeWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOriginalFileName(String originalFileName)
public void setOriginalFileNameDd(String originalFileNameDd)
public void setOriginalFileNameDdWV(String value)
public void setOriginalFileNameWV(String value)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRemarksDdWV(String value)
public void setRemarksWV(String value)
public void setSerializedKey(String serializedKey)
public void setSerializedKeyDd(String serializedKeyDd)
public void setSerializedKeyDdWV(String value)
public void setSerializedKeyWV(String value)
public void setSupercededBy(String supercededBy)
public void setSupercededByDd(String supercededByDd)
public void setSupercededByDdWV(String value)
public void setSupercededByWV(String value)
```
***
**org.jaffa.components.attachment.components.attachmentlookup.IAttachmentLookup**

```
public void destroy()
public AttachmentLookupOutDto find(AttachmentLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupInDto**

```
public StringCriteriaField getAttachmentId()
public StringCriteriaField getAttachmentType()
public StringCriteriaField getContentType()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOriginalFileName()
public StringCriteriaField getRemarks()
public StringCriteriaField getSerializedKey()
public StringCriteriaField getSupercededBy()
public void setAttachmentId(StringCriteriaField attachmentId)
public void setAttachmentType(StringCriteriaField attachmentType)
public void setContentType(StringCriteriaField contentType)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOriginalFileName(StringCriteriaField originalFileName)
public void setRemarks(StringCriteriaField remarks)
public void setSerializedKey(StringCriteriaField serializedKey)
public void setSupercededBy(StringCriteriaField supercededBy)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutDto**

```
public void addRows(AttachmentLookupOutRowDto rows)
public void clearRows()
public AttachmentLookupOutRowDto getRows(int index)
public AttachmentLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(AttachmentLookupOutRowDto rows)
public void setRows(AttachmentLookupOutRowDto rows, int index)
public void setRows(AttachmentLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutRowDto**

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
**org.jaffa.components.attachment.components.attachmentlookup.tx.AttachmentLookupTx**

```
public void destroy()
public AttachmentLookupOutDto find(AttachmentLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentlookup.ui.AttachmentLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_ViewAttachmentData_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.components.attachment.components.attachmentlookup.ui.AttachmentLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
public String getAttachmentId()
public String getAttachmentIdDd()
public String getAttachmentType()
public String getAttachmentTypeDd()
public String getContentType()
public String getContentTypeDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getDescription()
public String getDescriptionDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOriginalFileName()
public String getOriginalFileNameDd()
public String getRemarks()
public String getRemarksDd()
public String getSerializedKey()
public String getSerializedKeyDd()
public String getSupercededBy()
public String getSupercededByDd()
public void quit()
public void setAttachmentId(String attachmentId)
public void setAttachmentIdDd(String attachmentIdDd)
public void setAttachmentType(String attachmentType)
public void setAttachmentTypeDd(String attachmentTypeDd)
public void setContentType(String contentType)
public void setContentTypeDd(String contentTypeDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOriginalFileName(String originalFileName)
public void setOriginalFileNameDd(String originalFileNameDd)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setSerializedKey(String serializedKey)
public void setSerializedKeyDd(String serializedKeyDd)
public void setSupercededBy(String supercededBy)
public void setSupercededByDd(String supercededByDd)
public FormKey updateObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.attachment.components.attachmentlookup.ui.AttachmentLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getAttachmentId()
public String getAttachmentIdDd()
public DropDownModel getAttachmentIdDdWM()
public EditBoxModel getAttachmentIdWM()
public String getAttachmentType()
public String getAttachmentTypeDd()
public DropDownModel getAttachmentTypeDdWM()
public EditBoxModel getAttachmentTypeWM()
public String getContentType()
public String getContentTypeDd()
public DropDownModel getContentTypeDdWM()
public EditBoxModel getContentTypeWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOriginalFileName()
public String getOriginalFileNameDd()
public DropDownModel getOriginalFileNameDdWM()
public EditBoxModel getOriginalFileNameWM()
public String getRemarks()
public String getRemarksDd()
public DropDownModel getRemarksDdWM()
public EditBoxModel getRemarksWM()
public String getSerializedKey()
public String getSerializedKeyDd()
public DropDownModel getSerializedKeyDdWM()
public EditBoxModel getSerializedKeyWM()
public String getSupercededBy()
public String getSupercededByDd()
public DropDownModel getSupercededByDdWM()
public EditBoxModel getSupercededByWM()
public void populateRows(GridModel rows)
public void setAttachmentId(String attachmentId)
public void setAttachmentIdDd(String attachmentIdDd)
public void setAttachmentIdDdWV(String value)
public void setAttachmentIdWV(String value)
public void setAttachmentType(String attachmentType)
public void setAttachmentTypeDd(String attachmentTypeDd)
public void setAttachmentTypeDdWV(String value)
public void setAttachmentTypeWV(String value)
public void setContentType(String contentType)
public void setContentTypeDd(String contentTypeDd)
public void setContentTypeDdWV(String value)
public void setContentTypeWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOriginalFileName(String originalFileName)
public void setOriginalFileNameDd(String originalFileNameDd)
public void setOriginalFileNameDdWV(String value)
public void setOriginalFileNameWV(String value)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRemarksDdWV(String value)
public void setRemarksWV(String value)
public void setSerializedKey(String serializedKey)
public void setSerializedKeyDd(String serializedKeyDd)
public void setSerializedKeyDdWV(String value)
public void setSerializedKeyWV(String value)
public void setSupercededBy(String supercededBy)
public void setSupercededByDd(String supercededByDd)
public void setSupercededByDdWV(String value)
public void setSupercededByWV(String value)
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.IAttachmentMaintenance**

```
public AttachmentMaintenanceRetrieveOutDto create(AttachmentMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(AttachmentMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public AttachmentMaintenancePrevalidateOutDto prevalidateCreate(AttachmentMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenancePrevalidateOutDto prevalidateUpdate(AttachmentMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenanceRetrieveOutDto retrieve(AttachmentMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenanceRetrieveOutDto update(AttachmentMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto**

```
public java.lang.String getAttachmentId()
public java.lang.String getAttachmentType()
public java.lang.String getContentType()
public byte[] getData()
public java.lang.String getDescription()
public HeaderDto getHeaderDto()
public java.lang.String getOriginalFileName()
public java.lang.String getRemarks()
public java.lang.String getSerializedKey()
public java.lang.String getSupercededBy()
public void setAttachmentId(java.lang.String attachmentId)
public void setAttachmentType(java.lang.String attachmentType)
public void setContentType(java.lang.String contentType)
public void setData(byte[] data)
public void setDescription(java.lang.String description)
public void setHeaderDto(HeaderDto headerDto)
public void setOriginalFileName(java.lang.String originalFileName)
public void setRemarks(java.lang.String remarks)
public void setSerializedKey(java.lang.String serializedKey)
public void setSupercededBy(java.lang.String supercededBy)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto**

```
public java.lang.String getAttachmentId()
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setAttachmentId(java.lang.String attachmentId)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceRetrieveInDto**

```
public java.lang.String getAttachmentId()
public HeaderDto getHeaderDto()
public void setAttachmentId(java.lang.String attachmentId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceRetrieveOutDto**

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
**org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceUpdateInDto**

```
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.tx.AttachmentMaintenanceTx**

```
public AttachmentMaintenanceRetrieveOutDto create(AttachmentMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(AttachmentMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(AttachmentMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public AttachmentMaintenancePrevalidateOutDto prevalidateCreate(AttachmentMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenancePrevalidateOutDto prevalidateUpdate(AttachmentMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenanceRetrieveOutDto retrieve(AttachmentMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public AttachmentMaintenanceRetrieveOutDto update(AttachmentMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.ui.AttachmentMaintenanceAction**

No public methods.
***
**org.jaffa.components.attachment.components.attachmentmaintenance.ui.AttachmentMaintenanceComponent**

```
public java.lang.String getAttachmentId()
public java.lang.String getAttachmentType()
public java.lang.String getContentType()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public byte[] getData()
public java.lang.String getDescription()
public FormFile getEmbeddedFile()
public String getEmbeddedFileName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public String getLocalLink()
public java.lang.String getOriginalFileName()
public java.lang.String getRemarks()
public java.lang.String getSerializedKey()
public java.lang.String getSupercededBy()
public String getWebLink()
public void quit()
public void setAttachmentId(java.lang.String attachmentId)
public void setAttachmentType(java.lang.String attachmentType)
public void setContentType(java.lang.String contentType)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setData(byte[] data)
public void setDescription(java.lang.String description)
public void setEmbeddedFile(FormFile embeddedFile)
public void setEmbeddedFileName(String embeddedFileName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLocalLink(String localLink)
public void setOriginalFileName(java.lang.String originalFileName)
public void setRemarks(java.lang.String remarks)
public void setSerializedKey(java.lang.String serializedKey)
public void setSupercededBy(java.lang.String supercededBy)
public void setWebLink(String webLink)
```
***
**org.jaffa.components.attachment.components.attachmentmaintenance.ui.AttachmentMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getAttachmentId()
public EditBoxModel getAttachmentIdWM()
public java.lang.String getAttachmentType()
public RadioButtonModel getAttachmentTypeWM()
public java.lang.String getContentType()
public EditBoxModel getContentTypeWM()
public java.lang.String getCreatedBy()
public EditBoxModel getCreatedByWM()
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public byte[] getData()
public EditBoxModel getDataWM()
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public FormFile getEmbeddedFile()
public String getEmbeddedFileName()
public java.lang.String getLastChangedBy()
public EditBoxModel getLastChangedByWM()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public DateTimeModel getLastChangedOnWM()
public String getLocalLink()
public SimpleWidgetModel getLocalLinkWM()
public java.lang.String getOriginalFileName()
public EditBoxModel getOriginalFileNameWM()
public java.lang.String getRemarks()
public EditBoxModel getRemarksWM()
public java.lang.String getSerializedKey()
public EditBoxModel getSerializedKeyWM()
public java.lang.String getSupercededBy()
public EditBoxModel getSupercededByWM()
public String getWebLink()
public SimpleWidgetModel getWebLinkWM()
public boolean isSaveActionAvailable()
public void setAttachmentId(java.lang.String attachmentId)
public void setAttachmentIdWV(String value)
public void setAttachmentType(java.lang.String attachmentType)
public void setAttachmentTypeWV(String value)
public void setContentType(java.lang.String contentType)
public void setContentTypeWV(String value)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedByWV(String value)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setData(byte[] data)
public void setDataWV(String value)
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setEmbeddedFile(FormFile embeddedFile)
public void setEmbeddedFileName(String embeddedFileName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedByWV(String value)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLastChangedOnWV(String value)
public void setLocalLink(String localLink)
public void setLocalLinkWV(String value)
public void setOriginalFileName(java.lang.String originalFileName)
public void setOriginalFileNameWV(String value)
public void setRemarks(java.lang.String remarks)
public void setRemarksWV(String value)
public void setSerializedKey(java.lang.String serializedKey)
public void setSerializedKeyWV(String value)
public void setSupercededBy(java.lang.String supercededBy)
public void setSupercededByWV(String value)
public void setWebLink(String webLink)
public void setWebLinkWV(String value)
```
***
**org.jaffa.components.attachment.components.attachmentviewer.IAttachmentViewer**

```
public void destroy()
public AttachmentViewerOutDto read(AttachmentViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerInDto**

```
public java.lang.String getAttachmentId()
public HeaderDto getHeaderDto()
public void setAttachmentId(java.lang.String attachmentId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerOutDto**

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
**org.jaffa.components.attachment.components.attachmentviewer.tx.AttachmentViewerTx**

```
public void destroy()
public AttachmentViewerOutDto read(AttachmentViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.components.attachmentviewer.ui.AttachmentViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_Update_Clicked()
public FormKey do_ViewAttachmentData_Clicked()
```
***
**org.jaffa.components.attachment.components.attachmentviewer.ui.AttachmentViewerComponent**

```
public FormKey deleteObject() throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getAttachmentId()
public AttachmentViewerOutDto getAttachmentViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setAttachmentId(java.lang.String attachmentId)
public void setAttachmentViewerOutDto(AttachmentViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.attachment.components.attachmentviewer.ui.AttachmentViewerForm**

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
```
***
**org.jaffa.components.attachment.domain.Attachment**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String attachmentId) throws FrameworkException
public static Attachment findByPK(UOW uow, java.lang.String attachmentId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String attachmentId)
public java.lang.String getAttachmentId()
public java.lang.String getAttachmentType()
public java.lang.String getContentType()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public byte[] getData()
public java.lang.String getDescription()
public java.lang.String getDocumentReferenceId()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOriginalFileName()
public java.lang.String getRemarks()
public java.lang.String getSerializedKey()
public java.lang.String getSupercededBy()
public java.lang.Long getVersionNumber()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void postAdd() throws PostAddFailedException
public void postDelete() throws PostDeleteFailedException
public void postLoad() throws PostLoadFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void setAttachmentId(java.lang.String attachmentId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setAttachmentType(java.lang.String attachmentType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setContentType(java.lang.String contentType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setData(byte[] data) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDocumentReferenceId(java.lang.String documentReferenceId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOriginalFileName(java.lang.String originalFileName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSerializedKey(java.lang.String serializedKey) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSupercededBy(java.lang.String supercededBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateAttachmentId(java.lang.String attachmentId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateAttachmentType(java.lang.String attachmentType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateContentType(java.lang.String contentType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateData(byte[] data) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDocumentReferenceId(java.lang.String documentReferenceId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOriginalFileName(java.lang.String originalFileName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSerializedKey(java.lang.String serializedKey) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSupercededBy(java.lang.String supercededBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateAttachmentId(java.lang.String attachmentId) throws ValidationException, FrameworkException
public java.lang.String validateAttachmentType(java.lang.String attachmentType) throws ValidationException, FrameworkException
public java.lang.String validateContentType(java.lang.String contentType) throws ValidationException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public byte[] validateData(byte[] data) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateDocumentReferenceId(java.lang.String documentReferenceId) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateOriginalFileName(java.lang.String originalFileName) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
public java.lang.String validateSerializedKey(java.lang.String serializedKey) throws ValidationException, FrameworkException
public java.lang.String validateSupercededBy(java.lang.String supercededBy) throws ValidationException, FrameworkException
public java.lang.Long validateVersionNumber(java.lang.Long versionNumber) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.attachment.domain.AttachmentDelete**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String attachmentId) throws FrameworkException
public static AttachmentDelete findByPK(UOW uow, java.lang.String attachmentId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String attachmentId)
public java.lang.String getAttachmentId()
public org.jaffa.datatypes.DateTime getDeletionCreatedOn()
public java.lang.String getSerializedKey()
public java.lang.Long getVersionNumber()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void postAdd() throws PostAddFailedException
public void postDelete() throws PostDeleteFailedException
public void postLoad() throws PostLoadFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void setAttachmentId(java.lang.String attachmentId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSerializedKey(java.lang.String serializedKey) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateAttachmentId(java.lang.String attachmentId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSerializedKey(java.lang.String serializedKey) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateAttachmentId(java.lang.String attachmentId) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, FrameworkException
public java.lang.String validateSerializedKey(java.lang.String serializedKey) throws ValidationException, FrameworkException
public java.lang.Long validateVersionNumber(java.lang.Long versionNumber) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.attachment.domain.AttachmentDeleteMeta**

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
**org.jaffa.components.attachment.domain.AttachmentMeta**

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
**org.jaffa.components.attachment.services.AttachmentService**

```
public static void attach(IPersistent attachTo, Object attachment) throws FrameworkException, ApplicationExceptions
public static void attach(UOW uow, IPersistent attachTo, Object attachment) throws FrameworkException, ApplicationExceptions
public static Attachment createAttachment(IPersistent attachTo, Object attachment) throws FrameworkException, ApplicationExceptions
public static byte[] createAttachmentData(Object attachment) throws FrameworkException, ApplicationExceptions
public static String createAttachmentName(Object attachment) throws FrameworkException, ApplicationExceptions
public static String createContentType(Object attachment) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.services.JDBCAppenderWithAttachment**

```
public String getAttachmentMDCKey()
public String getAttachmentTable()
public String getEngine()
public void setAttachmentMDCKey(String attachmentMDCKey)
public void setAttachmentTable(String attachmentTable)
public void setEngine(String engine)
```
***
**org.jaffa.components.attachment.services.JDBCAppenderWithAttachmentTest**

```
public static Test suite()
public void testBytesAttachment() throws Exception
public void testFileAttachment() throws Exception
public void testJAXBAttachment() throws Exception
public void testLogDebug() throws Exception
public void testLogError() throws Exception
public void testLogInfo1() throws Exception
public void testLogInfo2() throws Exception
public void testStringAttachment() throws Exception
public void testXmlEncoderAttachment() throws Exception
```
***
**org.jaffa.components.attachment.services.JDBCAppenderWithAttachmentWrapper**

No public methods.
***
**org.jaffa.components.attachment.services.JDBCLoggerWithAttachment**

```
public void append(LoggingEvent event, Layout layout) throws Exception
```
***
**org.jaffa.components.attachment.services.VMIDVoucherGenerator**

```
public String generate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.attachment.util.AttachmentComponentHelper**

```
public static FormKey loadAttachmentData(FormBase form, HttpServletRequest request, String fileName, byte[] data)
```
***
**org.jaffa.components.audit.apis.AuditTransactionService**

```
public AuditTransactionQueryResponse query(AuditTransactionCriteria criteria)
```
***
**org.jaffa.components.audit.apis.AuditTransactionViewService**

```
public Map<String, Properties> getAuditableClasses() throws FrameworkException
public Map<String, Properties> getAuditableProperties(String className) throws FrameworkException
public AuditTransactionViewQueryResponse query(AuditTransactionCriteria criteria)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionCriteria**

```
public AuditTransactionFieldCriteria[] getAuditTransactionFields()
public StringCriteriaField getChangeType()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public String getObjectId()
public String getObjectName()
public StringCriteriaField getProcessName()
public StringCriteriaField getReason()
public StringCriteriaField getSubProcessName()
public StringCriteriaField getTransactionId()
public void queryView(boolean queryView)
public Criteria returnQueryClause(Criteria c)
public void setAuditTransactionFields(AuditTransactionFieldCriteria[] auditTransactionFields)
public void setChangeType(StringCriteriaField changeType)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setObjectId(String objectId)
public void setObjectName(String objectName)
public void setProcessName(StringCriteriaField processName)
public void setReason(StringCriteriaField reason)
public void setSubProcessName(StringCriteriaField subProcessName)
public void setTransactionId(StringCriteriaField transactionId)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionFieldCriteria**

```
public BooleanCriteriaField getChanged()
public String getFieldName()
public BooleanCriteriaField getFlex()
public StringCriteriaField getToValue()
public void setChanged(BooleanCriteriaField changed)
public void setFieldName(String fieldName)
public void setFlex(BooleanCriteriaField flex)
public void setToValue(StringCriteriaField toValue)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionFieldGraph**

```
public AuditTransactionOverflowGraph getAuditTransactionOverflow()
public Boolean getChanged()
public String getFieldId()
public String getFieldLabel()
public String getFieldName()
public Boolean getFlex()
public String getFromValue()
public String getMultilineHtmlFlag()
public Boolean getOverflowFlag()
public String getToValue()
public void setAuditTransactionOverflow(AuditTransactionOverflowGraph auditTransactionOverflow)
public void setChanged(Boolean changed)
public void setFieldId(String fieldId)
public void setFieldLabel(String fieldLabel)
public void setFieldName(String fieldName)
public void setFlex(Boolean flex)
public void setFromValue(String fromValue)
public void setMultilineHtmlFlag(String multilineHtmlFlag)
public void setOverflowFlag(Boolean overflowFlag)
public void setToValue(String toValue)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionGraph**

```
public AuditTransactionObjectGraph[] getAuditTransactionObjects()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getProcessName()
public String getReason()
public String getSubProcessName()
public String getTransactionId()
public void setAuditTransactionObjects(AuditTransactionObjectGraph[] auditTransactionObjects)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setProcessName(String processName)
public void setReason(String reason)
public void setSubProcessName(String subProcessName)
public void setTransactionId(String transactionId)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionObjectGraph**

```
public AuditTransactionFieldGraph[] getAuditTransactionFields()
public String getChangeType()
public String getObjectId()
public String getObjectLabel()
public String getObjectName()
public void setAuditTransactionFields(AuditTransactionFieldGraph[] auditTransactionFields)
public void setChangeType(String changeType)
public void setObjectId(String objectId)
public void setObjectLabel(String objectLabel)
public void setObjectName(String objectName)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionOverflowGraph**

```
public String getFieldId()
public String getFieldName()
public String getFromValue()
public String getToValue()
public void setFieldId(String fieldId)
public void setFieldName(String fieldName)
public void setFromValue(String fromValue)
public void setToValue(String toValue)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionQueryResponse**

```
public AuditTransactionGraph[] getGraphs()
public void setGraphs(AuditTransactionGraph[] graphs)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionViewGraph**

```
public String getChangeType()
public Boolean getChanged()
public String getCreatedBy()
public DateTime getCreatedOn()
public String getFieldLabel()
public String getFieldName()
public Boolean getFlex()
public String getFromValue()
public String getMultilineHtmlFlag()
public String getObjectLabel()
public String getObjectName()
public Boolean getOverflowFlag()
public String getProcessName()
public String getReason()
public String getSubProcessName()
public String getToValue()
public String getTransactionId()
public void setChangeType(String changeType)
public void setChanged(Boolean changed)
public void setCreatedBy(String createdBy)
public void setCreatedOn(DateTime createdOn)
public void setFieldLabel(String fieldLabel)
public void setFieldName(String fieldName)
public void setFlex(Boolean flex)
public void setFromValue(String fromValue)
public void setMultilineHtmlFlag(String multilineHtmlFlag)
public void setObjectLabel(String objectLabel)
public void setObjectName(String objectName)
public void setOverflowFlag(Boolean overflowFlag)
public void setProcessName(String processName)
public void setReason(String reason)
public void setSubProcessName(String subProcessName)
public void setToValue(String toValue)
public void setTransactionId(String transactionId)
```
***
**org.jaffa.components.audit.apis.data.AuditTransactionViewQueryResponse**

```
public AuditTransactionViewGraph[] getGraphs()
public void setGraphs(AuditTransactionViewGraph[] graphs)
```
***
**org.jaffa.components.audit.apis.impl.AuditTransactionHandler**

```
public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException
public void removeHiddenFields(AuditTransactionViewQueryResponse response) throws FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransaction**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String transactionId) throws FrameworkException
public Criteria findAuditTransactionObjectCriteria()
public static AuditTransaction findByPK(UOW uow, java.lang.String transactionId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String transactionId)
public void generateKey() throws ApplicationExceptions, FrameworkException
public AuditTransactionObject[] getAuditTransactionObjectArray() throws FrameworkException
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getProcessName()
public java.lang.String getReason()
public java.lang.String getSubProcessName()
public java.lang.String getTransactionId()
public AuditTransactionObject newAuditTransactionObjectObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProcessName(java.lang.String processName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setReason(java.lang.String reason) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSubProcessName(java.lang.String subProcessName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProcessName(java.lang.String processName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateReason(java.lang.String reason) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSubProcessName(java.lang.String subProcessName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTransactionId(java.lang.String transactionId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateProcessName(java.lang.String processName) throws ValidationException, FrameworkException
public java.lang.String validateReason(java.lang.String reason) throws ValidationException, FrameworkException
public java.lang.String validateSubProcessName(java.lang.String subProcessName) throws ValidationException, FrameworkException
public java.lang.String validateTransactionId(java.lang.String transactionId) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransactionField**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String fieldId) throws FrameworkException
public static AuditTransactionField findByPK(UOW uow, java.lang.String fieldId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String fieldId)
public void generateKey() throws ApplicationExceptions, FrameworkException
public AuditTransactionObject getAuditTransactionObjectObject() throws ValidationException, FrameworkException
public AuditTransactionOverflow getAuditTransactionOverflowObject() throws FrameworkException
public java.lang.Boolean getChanged()
public java.lang.String getFieldId()
public java.lang.String getFieldName()
public java.lang.Boolean getFlex()
public java.lang.String getFromValue()
public java.lang.String getMultilineHtmlFlag()
public java.lang.String getObjectId()
public java.lang.Boolean getOverflowFlag()
public java.lang.String getToValue()
public AuditTransactionOverflow newAuditTransactionOverflowObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setChanged(java.lang.Boolean changed) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldId(java.lang.String fieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFlex(java.lang.Boolean flex) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFromValue(java.lang.String fromValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setMultilineHtmlFlag(java.lang.String multilineHtmlFlag) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectId(java.lang.String objectId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOverflowFlag(java.lang.Boolean overflowFlag) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setToValue(java.lang.String toValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateChanged(java.lang.Boolean changed) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldId(java.lang.String fieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFlex(java.lang.Boolean flex) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFromValue(java.lang.String fromValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateMultilineHtmlFlag(java.lang.String multilineHtmlFlag) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectId(java.lang.String objectId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOverflowFlag(java.lang.Boolean overflowFlag) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateToValue(java.lang.String toValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.Boolean validateChanged(java.lang.Boolean changed) throws ValidationException, FrameworkException
public java.lang.String validateFieldId(java.lang.String fieldId) throws ValidationException, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.Boolean validateFlex(java.lang.Boolean flex) throws ValidationException, FrameworkException
public java.lang.String validateFromValue(java.lang.String fromValue) throws ValidationException, FrameworkException
public java.lang.String validateMultilineHtmlFlag(java.lang.String multilineHtmlFlag) throws ValidationException, FrameworkException
public java.lang.String validateObjectId(java.lang.String objectId) throws ValidationException, FrameworkException
public java.lang.Boolean validateOverflowFlag(java.lang.Boolean overflowFlag) throws ValidationException, FrameworkException
public java.lang.String validateToValue(java.lang.String toValue) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransactionFieldMeta**

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
**org.jaffa.components.audit.domain.AuditTransactionMeta**

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
**org.jaffa.components.audit.domain.AuditTransactionObject**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String objectId) throws FrameworkException
public Criteria findAuditTransactionFieldCriteria()
public static AuditTransactionObject findByPK(UOW uow, java.lang.String objectId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String objectId)
public void generateKey() throws ApplicationExceptions, FrameworkException
public AuditTransactionField[] getAuditTransactionFieldArray() throws FrameworkException
public AuditTransaction getAuditTransactionObject() throws ValidationException, FrameworkException
public java.lang.String getChangeType()
public java.lang.String getObjectId()
public java.lang.String getObjectName()
public java.lang.String getTransactionId()
public AuditTransactionField newAuditTransactionFieldObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setChangeType(java.lang.String changeType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectId(java.lang.String objectId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTransactionId(java.lang.String transactionId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateChangeType(java.lang.String changeType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectId(java.lang.String objectId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTransactionId(java.lang.String transactionId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateChangeType(java.lang.String changeType) throws ValidationException, FrameworkException
public java.lang.String validateObjectId(java.lang.String objectId) throws ValidationException, FrameworkException
public java.lang.String validateObjectName(java.lang.String objectName) throws ValidationException, FrameworkException
public java.lang.String validateTransactionId(java.lang.String transactionId) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransactionObjectMeta**

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
**org.jaffa.components.audit.domain.AuditTransactionOverflow**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String fieldId) throws FrameworkException
public static AuditTransactionOverflow findByPK(UOW uow, java.lang.String fieldId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String fieldId)
public java.lang.String getFieldId()
public java.lang.String getFieldName()
public java.lang.String getFromValue()
public java.lang.String getObjectId()
public java.lang.String getToValue()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setFieldId(java.lang.String fieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFromValue(java.lang.String fromValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectId(java.lang.String objectId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setToValue(java.lang.String toValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateFieldId(java.lang.String fieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFromValue(java.lang.String fromValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectId(java.lang.String objectId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateToValue(java.lang.String toValue) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateFieldId(java.lang.String fieldId) throws ValidationException, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.String validateFromValue(java.lang.String fromValue) throws ValidationException, FrameworkException
public java.lang.String validateObjectId(java.lang.String objectId) throws ValidationException, FrameworkException
public java.lang.String validateToValue(java.lang.String toValue) throws ValidationException, FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransactionOverflowMeta**

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
**org.jaffa.components.audit.domain.AuditTransactionView**

```
public java.lang.String getChangeType()
public java.lang.Boolean getChanged()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getFieldId()
public java.lang.String getFieldName()
public java.lang.Boolean getFlex()
public java.lang.String getFromValue()
public java.lang.String getMultilineHtmlFlag()
public java.lang.String getObjectId()
public java.lang.String getObjectName()
public java.lang.Boolean getOverflowFlag()
public java.lang.String getPK()
public java.lang.String getProcessName()
public java.lang.String getReason()
public java.lang.String getSubProcessName()
public java.lang.String getToValue()
public java.lang.String getTransactionId()
public void setChangeType(java.lang.String changeType)
public void setChanged(java.lang.Boolean changed)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setFieldId(java.lang.String fieldId)
public void setFieldName(java.lang.String fieldName)
public void setFlex(java.lang.Boolean flex)
public void setFromValue(java.lang.String fromValue)
public void setMultilineHtmlFlag(java.lang.String multilineHtmlFlag)
public void setObjectId(java.lang.String objectId)
public void setObjectName(java.lang.String objectName)
public void setOverflowFlag(java.lang.Boolean overflowFlag)
public void setPK(java.lang.String pK)
public void setProcessName(java.lang.String processName)
public void setReason(java.lang.String reason)
public void setSubProcessName(java.lang.String subProcessName)
public void setToValue(java.lang.String toValue)
public void setTransactionId(java.lang.String transactionId)
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.audit.domain.AuditTransactionViewMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.components.audit.services.AuditLogger**

```
public void add(IPersistent object) throws ApplicationExceptions, FrameworkException
public void clearLog() throws ApplicationExceptions, FrameworkException
public void delete(IPersistent object) throws ApplicationExceptions, FrameworkException
public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException
public void update(IPersistent object) throws ApplicationExceptions, FrameworkException
public void writeLog() throws ApplicationExceptions, FrameworkException
```
