# jaffa-ria Public Methods

***
**org.jaffa.dwr.JawrServletTest**

```
public void testJawrPropertiesLoad() throws Exception
```
***
**org.jaffa.ria.finder.apis.ExcelExportService**

```
public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child, String sheetName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
public static Object[] jsonArrayToBeanArray(String input, String beanClassName) throws ClassNotFoundException
public static Object jsonToBean(String input, String beanClassName) throws ClassNotFoundException
```
***
**org.jaffa.ria.finder.apis.QueryServiceConfig**

```
public Object[] getColumnModel()
public String getCriteriaClassName()
public String getCriteriaObject()
public String[] getMasterKeyFieldNames()
public String getServiceClassMethodName()
public String getServiceClassName()
public void setColumnModel(String columnModel)
public void setColumnModel(Object[] columnModel)
public void setCriteriaClassName(String criteriaClassName)
public void setCriteriaObject(String criteriaObject)
public void setMasterKeyFieldNames(Object[] masterKeyFieldNames)
public void setServiceClassMethodName(String serviceClassMethodName)
public void setServiceClassName(String serviceClassName)
```
***
**org.jaffa.ria.finder.apis.SavedQueryService**

```
public SavedQueryQueryResponse query(SavedQueryCriteria criteria)
```
***
**org.jaffa.ria.finder.apis.data.SavedQueryCriteria**

```
public StringCriteriaField getComponentRef()
public StringCriteriaField getContextRef()
public StringCriteriaField getCriteria()
public BooleanCriteriaField getIsDefault()
public StringCriteriaField getQueryId()
public StringCriteriaField getQueryName()
public StringCriteriaField getUserId()
public Criteria returnQueryClause(Criteria c)
public void setComponentRef(StringCriteriaField componentRef)
public void setContextRef(StringCriteriaField contextRef)
public void setCriteria(StringCriteriaField criteria)
public void setIsDefault(BooleanCriteriaField isDefault)
public void setQueryId(StringCriteriaField queryId)
public void setQueryName(StringCriteriaField queryName)
public void setUserId(StringCriteriaField userId)
```
***
**org.jaffa.ria.finder.apis.data.SavedQueryGraph**

```
public java.lang.String getComponentRef()
public java.lang.String getContextRef()
public java.lang.String getCriteria()
public java.lang.Boolean getIsDefault()
public java.lang.String getQueryId()
public java.lang.String getQueryName()
public java.lang.String getUserId()
public void setComponentRef(java.lang.String componentRef)
public void setContextRef(java.lang.String contextRef)
public void setCriteria(java.lang.String criteria)
public void setIsDefault(java.lang.Boolean isDefault)
public void setQueryId(java.lang.String queryId)
public void setQueryName(java.lang.String queryName)
public void setUserId(java.lang.String userId)
```
***
**org.jaffa.ria.finder.apis.data.SavedQueryQueryResponse**

```
public SavedQueryGraph[] getGraphs()
public void setGraphs(SavedQueryGraph[] graphs)
```
***
**org.jaffa.ria.finder.apis.data.SavedQueryUpdateResponse**

```
public SavedQueryGraph getSource()
public void setSource(SavedQueryGraph source)
```
***
**org.jaffa.ria.finder.apis.impl.SavedQueryUpdateHandler**

No public methods.
***
**org.jaffa.ria.finder.domain.SavedQuery**

```
public Object clone() throws CloneNotSupportedException
public static void deleteQuery(String componentRef, String contextRef, String queryName) throws FrameworkException
public static boolean exists(UOW uow, java.lang.String queryId) throws FrameworkException
public static SavedQuery findByPK(UOW uow, java.lang.String queryId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String queryId)
public void generateKey() throws ApplicationExceptions, FrameworkException
public java.lang.String getComponentRef()
public java.lang.String getContextRef()
public java.lang.String getCriteria()
public java.lang.Boolean getIsDefault()
public java.lang.String getQueryId()
public java.lang.String getQueryName()
public static String getSavedQueries(String componentRef, String contextRef) throws FrameworkException
public java.lang.String getUserId()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public static void saveQuery(String componentRef, String contextRef, String queryName, String queryData, Boolean isDefault) throws FrameworkException
public void setComponentRef(java.lang.String componentRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setContextRef(java.lang.String contextRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCriteria(java.lang.String criteria) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setIsDefault(java.lang.Boolean isDefault) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setQueryId(java.lang.String queryId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setQueryName(java.lang.String queryName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUserId(java.lang.String userId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateComponentRef(java.lang.String componentRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateContextRef(java.lang.String contextRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCriteria(java.lang.String criteria) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateIsDefault(java.lang.Boolean isDefault) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateQueryId(java.lang.String queryId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateQueryName(java.lang.String queryName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateUserId(java.lang.String userId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateComponentRef(java.lang.String componentRef) throws ValidationException, FrameworkException
public java.lang.String validateContextRef(java.lang.String contextRef) throws ValidationException, FrameworkException
public java.lang.String validateCriteria(java.lang.String criteria) throws ValidationException, FrameworkException
public java.lang.Boolean validateIsDefault(java.lang.Boolean isDefault) throws ValidationException, FrameworkException
public java.lang.String validateQueryId(java.lang.String queryId) throws ValidationException, FrameworkException
public java.lang.String validateQueryName(java.lang.String queryName) throws ValidationException, FrameworkException
public java.lang.String validateUserId(java.lang.String userId) throws ValidationException, FrameworkException
```
***
**org.jaffa.ria.finder.domain.SavedQueryMeta**

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
**org.jaffa.ria.listener.JawrInitializer**

```
public void contextDestroyed(ServletContextEvent evt)
public void contextInitialized(ServletContextEvent evt)
```
***
**org.jaffa.ria.metadata.FinderMetaDataHelper**

```
public static Map<String, Map<String, String>> getFieldMap(Class serviceClass, Class outputClass, Class domainClass, boolean graphBased, String[] keys) throws Exception
public static void perform(HttpServletRequest request, JspWriter out, ServletContext servletContext) throws Exception
```
***
**org.jaffa.ria.metadata.ForeignKeyLookup**

```
public String getForeignGraphKey()
public String getForeignKey()
public int getForeignKeySize()
public String getTitle()
public void setForeignGraphKey(String foreignGraphKey)
public void setForeignKey(String foreignKey)
public void setForeignKeySize(int foreignKeySize)
public void setTitle(String title)
public String toString()
```
***
**org.jaffa.ria.util.JaffaComponentHelper**

```
public String getBaseRef()
public String getClassMetaData()
public String getPathRef()
public String writeParameterData() throws MissingParameterException, InvalidParameterException
public String writeSecurityData()
```
***
**org.jaffa.ria.util.MissingParameterException**

No public methods.
***
**org.jaffa.ria.util.PropsFilePropertiesSource**

No public methods.
