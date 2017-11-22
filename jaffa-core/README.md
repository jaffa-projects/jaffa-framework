# jaffa-core Public Methods

***
**.AllTests**

```
public static void main(String[] args)
public static Test suite()
```
***
**datasecurity.AllTests**

```
public static void main(String[] args)
public static Test suite()
```
***
**datasecurity.JDBCPluginTest**

```
public void testPolicy1()
public void testPolicy2()
```
***
**helpers.ConnectionHelper**

```
public static Connection getConnection() throws Exception
public static String getEngineType() throws Exception
public static int getMaximumConnections() throws Exception
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.IItemFinder**

```
public void destroy()
public ItemFinderOutDto find(ItemFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderInDto**

```
public HeaderDto getHeaderDto()
public StringCriteriaField getPartNo()
public DecimalCriteriaField getQty()
public StringCriteriaField getSegregationCode()
public StringCriteriaField getSerial()
public void setHeaderDto(HeaderDto headerDto)
public void setPartNo(StringCriteriaField partNo)
public void setQty(DecimalCriteriaField qty)
public void setSegregationCode(StringCriteriaField segregationCode)
public void setSerial(StringCriteriaField serial)
public String toString()
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutDto**

```
public void addRows(ItemFinderOutRowDto rows)
public void clearRows()
public ItemFinderOutRowDto getRows(int index)
public ItemFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(ItemFinderOutRowDto rows)
public void setRows(ItemFinderOutRowDto rows, int index)
public void setRows(ItemFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutRowDto**

```
public String getItemId()
public String getPartNo()
public Double getQty()
public String getSegregationCode()
public String getSerial()
public void setItemId(String itemId)
public void setPartNo(String partNo)
public void setQty(Double qty)
public void setSegregationCode(String segregationCode)
public void setSerial(String serial)
public String toString()
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.tx.ItemFinderTx**

```
public void destroy()
public ItemFinderOutDto find(ItemFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.ui.ItemFinderComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayCriteria() throws ApplicationExceptions, FrameworkException
public FormKey displayResults() throws ApplicationExceptions, FrameworkException
public ItemFinderOutDto getItemFinderOutDto()
public String getPartNo()
public String getPartNoDd()
public String getQty()
public String getQtyDd()
public String getSegregationCode()
public String getSegregationCodeDd()
public String getSerial()
public String getSerialDd()
public void quit()
public void setPartNo(String partNo)
public void setPartNoDd(String partNoDd)
public void setQty(String qty)
public void setQtyDd(String qtyDd)
public void setSegregationCode(String segregationCode)
public void setSegregationCodeDd(String segregationCodeDd)
public void setSerial(String serial)
public void setSerialDd(String serialDd)
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.ui.ItemFinderCriteriaAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Search_Clicked()
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.ui.ItemFinderCriteriaForm**

```
public boolean doValidate(HttpServletRequest request)
public String getPartNo()
public String getPartNoDd()
public DropDownModel getPartNoDdWM()
public EditBoxModel getPartNoWM()
public String getQty()
public String getQtyDd()
public DropDownModel getQtyDdWM()
public EditBoxModel getQtyWM()
public String getSegregationCode()
public String getSegregationCodeDd()
public DropDownModel getSegregationCodeDdWM()
public EditBoxModel getSegregationCodeWM()
public String getSerial()
public String getSerialDd()
public DropDownModel getSerialDdWM()
public EditBoxModel getSerialWM()
public void setPartNo(String partNo)
public void setPartNoDd(String partNoDd)
public void setPartNoDdWV(String value)
public void setPartNoWV(String value)
public void setQty(String qty)
public void setQtyDd(String qtyDd)
public void setQtyDdWV(String value)
public void setQtyWV(String value)
public void setSegregationCode(String segregationCode)
public void setSegregationCodeDd(String segregationCodeDd)
public void setSegregationCodeDdWV(String value)
public void setSegregationCodeWV(String value)
public void setSerial(String serial)
public void setSerialDd(String serialDd)
public void setSerialDdWV(String value)
public void setSerialWV(String value)
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.ui.ItemFinderResultsAction**

```
public FormKey do_Close_Clicked()
public FormKey do_ModifySearch_Clicked()
public FormKey do_Refresh_Clicked()
```
***
**org.jaffa.applications.test.modules.material.components.itemfinder.ui.ItemFinderResultsForm**

```
public boolean getMoreRecordsExist()
public GridModel getRowsWM()
public void initForm()
public void setMoreRecordsExist(boolean moreRecordsExist)
public void setRowsWV(String value)
```
***
**org.jaffa.applications.test.modules.material.domain.Item**

```
public java.lang.String getItemId()
public java.lang.String getPart()
public java.lang.Double getQty()
public java.lang.String getSc()
public java.lang.String getSerial()
public void setItemId(java.lang.String itemId)
public void setPart(java.lang.String part)
public void setQty(java.lang.Double qty)
public void setSc(java.lang.String sc)
public void setSerial(java.lang.String serial)
public String toString()
public void updateItemId(java.lang.String itemId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePart(java.lang.String part) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateQty(java.lang.Double qty) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSc(java.lang.String sc) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSerial(java.lang.String serial) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validateItemId(java.lang.String itemId) throws ValidationException, FrameworkException
public void validatePart(java.lang.String part) throws ValidationException, FrameworkException
public void validateQty(java.lang.Double qty) throws ValidationException, FrameworkException
public void validateSc(java.lang.String sc) throws ValidationException, FrameworkException
public void validateSerial(java.lang.String serial) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.test.modules.material.domain.ItemMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.applications.test.modules.security.components.test1.ui.Page1Action**

```
public FormKey do_Button1_Clicked()
public FormKey do_Button1b_Clicked()
public FormKey do_Button2_Clicked()
public FormKey do_Button3_Clicked()
public FormKey do_Logout_Clicked()
```
***
**org.jaffa.applications.test.modules.security.components.test1.ui.Page1Form**

```
public EditBoxModel getField1WM()
public EditBoxModel getField2WM()
public String getMessage()
public void setField1WV(String value)
public void setField2WV(String value)
public void setMessage(String message)
```
***
**org.jaffa.applications.test.modules.security.components.test1.ui.Test1Component**

```
public FormKey display()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.IUserTimeEntryFinder**

```
public void destroy()
public UserTimeEntryFinderOutDto find(UserTimeEntryFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.dto.UserTimeEntryFinderInDto**

```
public StringCriteriaField getActivity()
public HeaderDto getHeaderDto()
public DateTimeCriteriaField getPeriodEnd()
public DateTimeCriteriaField getPeriodStart()
public StringCriteriaField getProjectCode()
public StringCriteriaField getTask()
public StringCriteriaField getUserName()
public void setActivity(StringCriteriaField activity)
public void setHeaderDto(HeaderDto headerDto)
public void setPeriodEnd(DateTimeCriteriaField periodEnd)
public void setPeriodStart(DateTimeCriteriaField periodStart)
public void setProjectCode(StringCriteriaField projectCode)
public void setTask(StringCriteriaField task)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.dto.UserTimeEntryFinderOutDto**

```
public void addRows(UserTimeEntryFinderOutRowDto rows)
public void clearRows()
public UserTimeEntryFinderOutRowDto getRows(int index)
public UserTimeEntryFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserTimeEntryFinderOutRowDto rows)
public void setRows(UserTimeEntryFinderOutRowDto rows, int index)
public void setRows(UserTimeEntryFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.dto.UserTimeEntryFinderOutRowDto**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setActivity(java.lang.String activity)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.tx.UserTimeEntryFinderTx**

```
public void destroy()
public UserTimeEntryFinderOutDto find(UserTimeEntryFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.ui.UserTimeEntryFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.ui.UserTimeEntryFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
public String getActivity()
public String getActivityDd()
public String getPeriodEnd()
public String getPeriodEndDd()
public String getPeriodStart()
public String getPeriodStartDd()
public String getProjectCode()
public String getProjectCodeDd()
public String getTask()
public String getTaskDd()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setActivity(String activity)
public void setActivityDd(String activityDd)
public void setPeriodEnd(String periodEnd)
public void setPeriodEndDd(String periodEndDd)
public void setPeriodStart(String periodStart)
public void setPeriodStartDd(String periodStartDd)
public void setProjectCode(String projectCode)
public void setProjectCodeDd(String projectCodeDd)
public void setTask(String task)
public void setTaskDd(String taskDd)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryfinder.ui.UserTimeEntryFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getActivity()
public String getActivityDd()
public DropDownModel getActivityDdWM()
public EditBoxModel getActivityWM()
public String getPeriodEnd()
public String getPeriodEndDd()
public DropDownModel getPeriodEndDdWM()
public EditBoxModel getPeriodEndWM()
public String getPeriodStart()
public String getPeriodStartDd()
public DropDownModel getPeriodStartDdWM()
public EditBoxModel getPeriodStartWM()
public String getProjectCode()
public String getProjectCodeDd()
public DropDownModel getProjectCodeDdWM()
public EditBoxModel getProjectCodeWM()
public String getTask()
public String getTaskDd()
public DropDownModel getTaskDdWM()
public EditBoxModel getTaskWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setActivity(String activity)
public void setActivityDd(String activityDd)
public void setActivityDdWV(String value)
public void setActivityWV(String value)
public void setPeriodEnd(String periodEnd)
public void setPeriodEndDd(String periodEndDd)
public void setPeriodEndDdWV(String value)
public void setPeriodEndWV(String value)
public void setPeriodStart(String periodStart)
public void setPeriodStartDd(String periodStartDd)
public void setPeriodStartDdWV(String value)
public void setPeriodStartWV(String value)
public void setProjectCode(String projectCode)
public void setProjectCodeDd(String projectCodeDd)
public void setProjectCodeDdWV(String value)
public void setProjectCodeWV(String value)
public void setTask(String task)
public void setTaskDd(String taskDd)
public void setTaskDdWV(String value)
public void setTaskWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.IUserTimeEntryLookup**

```
public void destroy()
public UserTimeEntryLookupOutDto find(UserTimeEntryLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupInDto**

```
public StringCriteriaField getActivity()
public HeaderDto getHeaderDto()
public DateTimeCriteriaField getPeriodEnd()
public DateTimeCriteriaField getPeriodStart()
public StringCriteriaField getProjectCode()
public StringCriteriaField getTask()
public StringCriteriaField getUserName()
public void setActivity(StringCriteriaField activity)
public void setHeaderDto(HeaderDto headerDto)
public void setPeriodEnd(DateTimeCriteriaField periodEnd)
public void setPeriodStart(DateTimeCriteriaField periodStart)
public void setProjectCode(StringCriteriaField projectCode)
public void setTask(StringCriteriaField task)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupOutDto**

```
public void addRows(UserTimeEntryLookupOutRowDto rows)
public void clearRows()
public UserTimeEntryLookupOutRowDto getRows(int index)
public UserTimeEntryLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserTimeEntryLookupOutRowDto rows)
public void setRows(UserTimeEntryLookupOutRowDto rows, int index)
public void setRows(UserTimeEntryLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupOutRowDto**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setActivity(java.lang.String activity)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.tx.UserTimeEntryLookupTx**

```
public void destroy()
public UserTimeEntryLookupOutDto find(UserTimeEntryLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.ui.UserTimeEntryLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.ui.UserTimeEntryLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
public String getActivity()
public String getActivityDd()
public String getPeriodEnd()
public String getPeriodEndDd()
public String getPeriodStart()
public String getPeriodStartDd()
public String getProjectCode()
public String getProjectCodeDd()
public String getTask()
public String getTaskDd()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setActivity(String activity)
public void setActivityDd(String activityDd)
public void setPeriodEnd(String periodEnd)
public void setPeriodEndDd(String periodEndDd)
public void setPeriodStart(String periodStart)
public void setPeriodStartDd(String periodStartDd)
public void setProjectCode(String projectCode)
public void setProjectCodeDd(String projectCodeDd)
public void setTask(String task)
public void setTaskDd(String taskDd)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrylookup.ui.UserTimeEntryLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getActivity()
public String getActivityDd()
public DropDownModel getActivityDdWM()
public EditBoxModel getActivityWM()
public String getPeriodEnd()
public String getPeriodEndDd()
public DropDownModel getPeriodEndDdWM()
public EditBoxModel getPeriodEndWM()
public String getPeriodStart()
public String getPeriodStartDd()
public DropDownModel getPeriodStartDdWM()
public EditBoxModel getPeriodStartWM()
public String getProjectCode()
public String getProjectCodeDd()
public DropDownModel getProjectCodeDdWM()
public EditBoxModel getProjectCodeWM()
public String getTask()
public String getTaskDd()
public DropDownModel getTaskDdWM()
public EditBoxModel getTaskWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setActivity(String activity)
public void setActivityDd(String activityDd)
public void setActivityDdWV(String value)
public void setActivityWV(String value)
public void setPeriodEnd(String periodEnd)
public void setPeriodEndDd(String periodEndDd)
public void setPeriodEndDdWV(String value)
public void setPeriodEndWV(String value)
public void setPeriodStart(String periodStart)
public void setPeriodStartDd(String periodStartDd)
public void setPeriodStartDdWV(String value)
public void setPeriodStartWV(String value)
public void setProjectCode(String projectCode)
public void setProjectCodeDd(String projectCodeDd)
public void setProjectCodeDdWV(String value)
public void setProjectCodeWV(String value)
public void setTask(String task)
public void setTaskDd(String taskDd)
public void setTaskDdWV(String value)
public void setTaskWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.IUserTimeEntryMaintenance**

```
public UserTimeEntryMaintenanceRetrieveOutDto create(UserTimeEntryMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserTimeEntryMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserTimeEntryMaintenancePrevalidateOutDto prevalidateCreate(UserTimeEntryMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenancePrevalidateOutDto prevalidateUpdate(UserTimeEntryMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenanceRetrieveOutDto retrieve(UserTimeEntryMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenanceRetrieveOutDto update(UserTimeEntryMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenanceCreateInDto**

```
public java.lang.String getActivity()
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setActivity(java.lang.String activity)
public void setHeaderDto(HeaderDto headerDto)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenanceRetrieveOutDto**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setActivity(java.lang.String activity)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.UserTimeEntryMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.tx.UserTimeEntryMaintenanceTx**

```
public UserTimeEntryMaintenanceRetrieveOutDto create(UserTimeEntryMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserTimeEntryMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserTimeEntryMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserTimeEntryMaintenancePrevalidateOutDto prevalidateCreate(UserTimeEntryMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenancePrevalidateOutDto prevalidateUpdate(UserTimeEntryMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenanceRetrieveOutDto retrieve(UserTimeEntryMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserTimeEntryMaintenanceRetrieveOutDto update(UserTimeEntryMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceAction**

No public methods.
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceComponent**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public CodeHelperOutElementDto getProjectCodeCodes()
public java.lang.String getTask()
public java.lang.String getUserName()
public void quit()
public void setActivity(java.lang.String activity)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getActivity()
public DropDownModel getActivityWM()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public DateTimeModel getPeriodEndWM()
public org.jaffa.datatypes.DateTime getPeriodStart()
public DateTimeModel getPeriodStartWM()
public java.lang.String getProjectCode()
public DropDownModel getProjectCodeWM()
public java.lang.String getTask()
public DropDownModel getTaskWM()
public java.lang.String getUserName()
public EditBoxModel getUserNameWM()
public void setActivity(java.lang.String activity)
public void setActivityWV(String value)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodEndWV(String value)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setPeriodStartWV(String value)
public void setProjectCode(java.lang.String projectCode)
public void setProjectCodeWV(String value)
public void setTask(java.lang.String task)
public void setTaskWV(String value)
public void setUserName(java.lang.String userName)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.IUserTimeEntryViewer**

```
public void destroy()
public UserTimeEntryViewerOutDto read(UserTimeEntryViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerInDto**

```
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerOutDto**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void setActivity(java.lang.String activity)
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.tx.UserTimeEntryViewerTx**

```
public void destroy()
public UserTimeEntryViewerOutDto read(UserTimeEntryViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui.UserTimeEntryViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui.UserTimeEntryViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public UserTimeEntryViewerOutDto getUserTimeEntryViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
public void setProjectCode(java.lang.String projectCode)
public void setTask(java.lang.String task)
public void setUserName(java.lang.String userName)
public void setUserTimeEntryViewerOutDto(UserTimeEntryViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui.UserTimeEntryViewerForm**

```
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
```
***
**org.jaffa.applications.test.modules.time.domain.ActivityCode**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String activity) throws FrameworkException
public static ActivityCode findByPK(UOW uow, java.lang.String activity) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String activity)
public java.lang.String getActivity()
public java.lang.String getDescription()
public java.lang.String getProjectCode()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setActivity(java.lang.String activity) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProjectCode(java.lang.String projectCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateActivity(java.lang.String activity) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProjectCode(java.lang.String projectCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateActivity(java.lang.String activity) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateProjectCode(java.lang.String projectCode) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.domain.ActivityCodeMeta**

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
**org.jaffa.applications.test.modules.time.domain.ProjectCode**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String projectCode) throws FrameworkException
public static ProjectCode findByPK(UOW uow, java.lang.String projectCode) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String projectCode)
public java.lang.String getDescription()
public java.lang.String getProjectCode()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProjectCode(java.lang.String projectCode) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProjectCode(java.lang.String projectCode) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateProjectCode(java.lang.String projectCode) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.domain.ProjectCodeMeta**

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
**org.jaffa.applications.test.modules.time.domain.TaskCode**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String task) throws FrameworkException
public static TaskCode findByPK(UOW uow, java.lang.String task) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String task)
public java.lang.String getActivity()
public java.lang.String getDescription()
public java.lang.String getTask()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setActivity(java.lang.String activity) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTask(java.lang.String task) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateActivity(java.lang.String activity) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTask(java.lang.String task) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateActivity(java.lang.String activity) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateTask(java.lang.String task) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.domain.TaskCodeMeta**

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
**org.jaffa.applications.test.modules.time.domain.UserTimeEntry**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws FrameworkException
public static UserTimeEntry findByPK(UOW uow, java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd)
public java.lang.String getActivity()
public org.jaffa.datatypes.DateTime getPeriodEnd()
public org.jaffa.datatypes.DateTime getPeriodStart()
public java.lang.String getProjectCode()
public java.lang.String getTask()
public java.lang.String getUserName()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setActivity(java.lang.String activity) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProjectCode(java.lang.String projectCode) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTask(java.lang.String task) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateActivity(java.lang.String activity) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePeriodEnd(org.jaffa.datatypes.DateTime periodEnd) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePeriodStart(org.jaffa.datatypes.DateTime periodStart) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProjectCode(java.lang.String projectCode) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTask(java.lang.String task) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateActivity(java.lang.String activity) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validatePeriodEnd(org.jaffa.datatypes.DateTime periodEnd) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validatePeriodStart(org.jaffa.datatypes.DateTime periodStart) throws ValidationException, FrameworkException
public java.lang.String validateProjectCode(java.lang.String projectCode) throws ValidationException, FrameworkException
public java.lang.String validateTask(java.lang.String task) throws ValidationException, FrameworkException
public java.lang.String validateUserName(java.lang.String userName) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta**

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
**org.jaffa.beans.factory.ILifecycleHandlerFactory**

No public methods.
***
**org.jaffa.beans.factory.ILifecycleHandlerProvider**

No public methods.
***
**org.jaffa.beans.factory.InitializerFactory**

No public methods.
***
**org.jaffa.beans.factory.LifecycleHandlerFactory**

```
public void addAppendedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider)
public void addPrependedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider)
public List<ILifecycleHandler> getAppendedHandlers(ILifecycleHandler handler)
public List<ILifecycleHandler> getPrependedHandlers(ILifecycleHandler handler)
```
***
**org.jaffa.beans.factory.config.DomainModelTestConfig**

```
public InitializerFactory initializerFactory()
public StaticContext staticContext()
public TestBean testBean(TestBean instance)
```
***
**org.jaffa.beans.factory.config.FakeRuleValidatorFactory**

```
public Validator getValidator(Object object)
```
***
**org.jaffa.beans.factory.config.RuleValidatorFactoryTestConfig**

```
public FakeRuleValidatorFactory ruleValidatorFactory()
```
***
**org.jaffa.beans.factory.config.StaticContext**

```
public static void addToFactoryMethodMap(Class<?> clazz, String beanFactoryMethod)
public static void clearApplicationContext()
public static T configure(T object)
public static T getBean(Class<T> beanClass)
public static Object getBean(String beanName)
public static String getFactoryMethodMapFor(Class<?> clazz)
public static boolean hasAppContext()
public static T initialize(T object)
public static void removeFromFactoryMethodMap(Class<?> clazz)
public void setApplicationContext(ApplicationContext applicationContext)
```
***
**org.jaffa.beans.factory.config.StaticContextTest**

```
public static void beforeClass()
public void testConfigAndInitialize() throws Exception
public void testFactoryMethodMappingPersistentBeans() throws Exception
public void testNoBeanConfiguration()
```
***
**org.jaffa.beans.factory.config.TestBean**

```
public Integer getTestInteger()
public String getTestString()
public void setTestInteger(Integer testInteger)
public void setTestString(String testString)
```
***
**org.jaffa.beans.factory.config.TestBeanDerived**

No public methods.
***
**org.jaffa.beans.factory.config.TestPersistentBean**

No public methods.
***
**org.jaffa.beans.moulding.data.Generic**

```
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.beans.moulding.data.criteria.CriteriaDAO**

```
public Integer getObjectLimit()
public String[] getResultGraphRules()
public abstract Criteria returnQueryClause()
public void setObjectLimit(Integer objectLimit)
public void setResultGraphRules(String[] resultGraphRules)
```
***
**org.jaffa.beans.moulding.data.domain.DomainDAO**

```
public final void addPropertyChangeListener(java.beans.PropertyChangeListener l)
public void clearChanges()
public String[] getNullify()
public Object getOriginalValue(String property) throws NoSuchFieldException
public boolean hasChanged(String property)
public final void removePropertyChangeListener(java.beans.PropertyChangeListener l)
public void setNullify(String[] nullify)
public String toString()
public String toString(List objectStack)
public abstract void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.beans.moulding.mapping.AbstractMouldHandler**

```
public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
```
***
**org.jaffa.beans.moulding.mapping.BeanMoulder**

```
public static void deleteBean(String path, DomainDAO source, UOW uow, MouldHandler handler) throws ApplicationExceptions, FrameworkException
public static void moldFromDomain(Object source, Object target) throws ApplicationExceptions, FrameworkException
public static void moldFromDomain(Object source, Object target, MappingFilter filter, String objectPath) throws ApplicationExceptions, FrameworkException
public static void moldFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys) throws ApplicationExceptions, FrameworkException
public static String printBean(Object source)
public static String printBean(Object source, List objectStack)
public static void updateBean(String path, DomainDAO source, UOW uow, MouldHandler handler) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.beans.moulding.mapping.DataTypeMapping**

```
public static boolean isMappable(Class source, Class target)
public static Object map(Object source, Class target) throws ClassCastException
```
***
**org.jaffa.beans.moulding.mapping.GraphMapping**

```
public boolean containsDataField(String dataFieldName)
public Class getDataClass()
public String getDataClassName()
public String getDataClassShortName()
public PropertyDescriptor getDataFieldDescriptor(String dataFieldName)
public String[] getDataFieldNames()
public Class getDomainClass()
public String getDomainClassName()
public String getDomainClassShortName()
public PropertyDescriptor getDomainFieldDescriptor(String dataFieldName)
public String getDomainFieldName(String dataFieldName)
public Set getFields()
public Set getForeignFields()
public List getForeignKeys(String dataFieldName)
public Set getKeyFields()
public static Map getPropertyDescriptorMap(PropertyDescriptor[] props)
public static PropertyDescriptor[] getPropertyDescriptors(Class clazz)
public PropertyDescriptor getRealDomainFieldDescriptor(String domainFieldName)
public Set getRelatedFields()
public boolean hasFields()
public boolean hasForeignFields()
public boolean hasKeyFields()
public boolean hasRelatedFields()
public boolean isField(String dataFieldName)
public boolean isForeignField(String dataFieldName)
public boolean isKeyField(String dataFieldName)
public boolean isRelatedField(String dataFieldName)
```
***
**org.jaffa.beans.moulding.mapping.MappingFactory**

```
public static synchronized GraphMapping getInstance(Class dao)
public static GraphMapping getInstance(Object dao)
```
***
**org.jaffa.beans.moulding.mapping.MappingFilter**

```
public boolean areSubFieldsIncluded(String fieldPrefix)
public static List getFieldList(Class dao)
public String[] getIncludedFields()
public String[] getRules()
public boolean isFieldIncluded(String field)
public static void main(String[] args)
public void setRules(String[] rules)
```
***
**org.jaffa.beans.moulding.mapping.MouldException**

No public methods.
***
**org.jaffa.beans.moulding.mapping.MouldHandler**

```
public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException
```
***
**org.jaffa.cache.CacheManager**

```
public static ICache getCache(String cacheName)
```
***
**org.jaffa.cache.CacheSourceMonitor**

```
public void addFile(String file)
public void startMonitoring()
public void startMonitoring(int minutes)
public void stopMonitoring()
```
***
**org.jaffa.cache.CacheSourceMonitorThread**

```
public void run()
```
***
**org.jaffa.cache.ICache**

```
public void clear()
public boolean containsKey(Object key)
public Object get(Object key)
public boolean isEmpty()
public Set keySet()
public void put(Object key, Object value)
public void remove(Object key)
public int size()
```
***
**org.jaffa.cache.SimpleCache**

```
public void clear()
public boolean containsKey(Object key)
public Object get(Object key)
public boolean isEmpty()
public Set keySet()
public void put(Object key, Object value)
public void remove(Object key)
public int size()
```
***
**org.jaffa.cache.WeakCache**

```
public void clear()
public boolean containsKey(Object key)
public Object get(Object key)
public boolean isEmpty()
public Set keySet()
public void put(Object key, Object value)
public void remove(Object key)
public int size()
```
***
**org.jaffa.components.codehelper.ICodeHelper**

No public methods.
***
**org.jaffa.components.codehelper.dto.CodeHelperInDto**

```
public void addCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto)
public void clearCodeHelperInElementDtos()
public CodeHelperInElementDto getCodeHelperInElementDto(int index)
public int getCodeHelperInElementDtoCount()
public CodeHelperInElementDto[] getCodeHelperInElementDtos()
public HeaderDto getHeaderDto()
public boolean removeCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto)
public void setCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto, int index)
public void setCodeHelperInElementDtos(CodeHelperInElementDto[] codeHelperInElementDtos)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.components.codehelper.dto.CodeHelperInElementDto**

```
public void addCriteriaField(CriteriaElementDto criteriaField)
public void clearCriteriaFields()
public String getAppendBeginMarker()
public String getAppendEndMarker()
public String getCode()
public String getCodeFieldName()
public CriteriaElementDto getCriteriaField(int index)
public int getCriteriaFieldCount()
public CriteriaElementDto[] getCriteriaFields()
public String getDescriptionFieldName()
public String getDomainClassName()
public boolean isAppendCodeAndDescription()
public boolean removeCriteriaField(CriteriaElementDto criteriaField)
public void setAppendBeginMarker(String appendBeginMarker)
public void setAppendCodeAndDescription(boolean appendCodeAndDescription)
public void setAppendEndMarker(String appendEndMarker)
public void setCode(String code)
public void setCodeFieldName(String codeFieldName)
public void setCriteriaField(CriteriaElementDto criteriaField, int index)
public void setCriteriaFields(CriteriaElementDto[] criteriaFields)
public void setDescriptionFieldName(String descriptionFieldName)
public void setDomainClassName(String domainClassName)
public String toString()
```
***
**org.jaffa.components.codehelper.dto.CodeHelperOutCodeDto**

```
public Object getCode()
public Object getDescription()
public void setCode(Object code)
public void setDescription(Object description)
public String toString()
```
***
**org.jaffa.components.codehelper.dto.CodeHelperOutDto**

```
public void addCodeHelperOutElementDto(CodeHelperOutElementDto codeHelperOutElementDto)
public void clearCodeHelperOutElementDtos()
public CodeHelperOutElementDto getCodeHelperOutElementDto(int index)
public int getCodeHelperOutElementDtoCount()
public CodeHelperOutElementDto[] getCodeHelperOutElementDtos()
public boolean removeCodeHelperOutElementDto(CodeHelperOutElementDto codeHelperOutElementDto)
public void setCodeHelperOutElementDto(CodeHelperOutElementDto codeHelperOutElementDto, int index)
public void setCodeHelperOutElementDtos(CodeHelperOutElementDto[] codeHelperOutElementDtos)
public String toString()
```
***
**org.jaffa.components.codehelper.dto.CodeHelperOutElementDto**

```
public void addCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto)
public void clearCodeHelperOutCodeDtos()
public String getCode()
public CodeHelperOutCodeDto getCodeHelperOutCodeDto(int index)
public int getCodeHelperOutCodeDtoCount()
public CodeHelperOutCodeDto[] getCodeHelperOutCodeDtos()
public String getDomainClassName()
public boolean removeCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto)
public void setCode(String code)
public void setCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto, int index)
public void setCodeHelperOutCodeDtos(CodeHelperOutCodeDto[] codeHelperOutCodeDtos)
public void setDomainClassName(String domainClassName)
public String toString()
```
***
**org.jaffa.components.codehelper.dto.CriteriaElementDto**

```
public CriteriaField getCriteria()
public String getFieldName()
public void setCriteria(CriteriaField criteria)
public void setFieldName(String fieldName)
public String toString()
```
***
**org.jaffa.components.codehelper.tx.CodeHelperTx**

```
public void destroy()
public static Object formatDescription(CodeHelperInElementDto inputElement, Object code, Object description)
public CodeHelperOutDto getCodes(CodeHelperInDto input) throws ApplicationExceptions, FrameworkException
public static void main(String[] args)
```
***
**org.jaffa.components.dto.HeaderDto**

```
public String getUserId()
public String getVariation()
public void setUserId(String userId)
public void setVariation(String variation)
public String toString()
```
***
**org.jaffa.components.finder.BooleanCriteriaField**

```
public static BooleanCriteriaField getBooleanCriteriaField(String operator, String value, BooleanFieldMetaData meta)
public String getOperator()
public Boolean[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(Boolean[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.CriteriaDropDownOptions**

```
public static Map getAllCriteriaDropDownOptions()
public static Map getBooleanCriteriaDropDownOptions()
public static Map getDateCriteriaDropDownOptions()
public static Long getDefaultMaxRecordsDropDownOption()
public static Map getDropDownCriteriaDropDownOptions()
public static Map getMaxRecordsDropDownOptions()
public static Map getNumericalCriteriaDropDownOptions()
```
***
**org.jaffa.components.finder.CriteriaField**

```
public String getOperator()
public Object[] returnValuesAsObjectArray()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.CurrencyCriteriaField**

```
public static CurrencyCriteriaField getCurrencyCriteriaField(String operator, String value, CurrencyFieldMetaData meta) throws FormatCurrencyException
public String getOperator()
public Currency[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(Currency[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.DateCriteriaField**

```
public Object clone()
public String getOperator()
public Date[] getValues()
public void setOperator(String operator)
public void setValues(Date[] values)
public DateOnlyCriteriaField toDateOnlyCriteriaField()
public DateTimeCriteriaField toDateTimeCriteriaField()
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.DateOnlyCriteriaField**

```
public static DateOnlyCriteriaField getDateOnlyCriteriaField(String operator, String value, DateOnlyFieldMetaData meta) throws FormatDateOnlyException
public String getOperator()
public DateOnly[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(DateOnly[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.DateTimeCriteriaField**

```
public static DateTimeCriteriaField getDateTimeCriteriaField(String operator, String value, DateTimeFieldMetaData meta) throws FormatDateTimeException
public String getOperator()
public DateTime[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(DateTime[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.DecimalCriteriaField**

```
public static DecimalCriteriaField getDecimalCriteriaField(String operator, String value, DecimalFieldMetaData meta) throws FormatDecimalException
public String getOperator()
public Double[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(Double[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.FinderAction**

```
public FormKey do_Close_Clicked()
public FormKey do_DeleteQuery_Clicked()
public FormKey do_LoadQuery_Clicked()
public FormKey do_ModifySearch_Clicked()
public FormKey do_MoreRecords_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_RunQuery_Clicked()
public FormKey do_SaveQuery_Clicked()
public FormKey do_Search_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.components.finder.FinderComponent2**

```
public void deleteQuery() throws ApplicationException
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey displayCriteria() throws ApplicationExceptions, FrameworkException
public FormKey displayResults() throws ApplicationExceptions, FrameworkException
public FormKey displayResultsConsiderExport() throws ApplicationExceptions, FrameworkException
public Boolean getConsolidatedCriteriaAndResults()
public FormKey getCriteriaFormKey()
public Boolean getDefaultQueryYn()
public Boolean getDisplayResultsScreen()
public String getExportType()
public FinderOutDto getFinderOutDto()
public Integer getMaxRecords()
public String getNewQueryName()
public Boolean getQueryHasShortcutYn()
public DateTime getQueryLastRunOn()
public FormKey getResultsFormKey()
public FormKey getResultsFormKey(boolean considerExportType)
public Long getSavedQueryId()
public CodeHelperOutElementDto getSavedQueryIdCodes()
public String getSortDropDown()
public Boolean getUseDefaultQuery()
public String getUseQuery()
public void incrementMaxRecords()
public void loadDefaultQuery() throws ApplicationException
public void loadQuery() throws ApplicationException
public void performInquiry() throws ApplicationExceptions, FrameworkException
public Map<String, String> retrieveQueryTypes()
public void saveQuery() throws ApplicationException
public void setConsolidatedCriteriaAndResults(Boolean consolidatedCriteriaAndResults)
public void setDefaultQueryYn(Boolean defaultQueryYn)
public void setDisplayResultsScreen(Boolean displayResultsScreen)
public void setExportType(String exportType)
public void setMaxRecords(Integer maxRecords)
public void setNewQueryName(String newQueryName)
public void setQueryHasShortcutYn(Boolean queryHasShortcutYn)
public void setSavedQueryId(Long savedQueryId)
public void setSortDropDown(String sortDropDown)
public void setUseDefaultQuery(Boolean useDefaultQuery)
public void setUseQuery(String useQuery)
```
***
**org.jaffa.components.finder.FinderForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidateForLoadQuery(HttpServletRequest request)
public boolean doValidateForSaveQuery(HttpServletRequest request)
public Boolean getDefaultQueryYn()
public RadioButtonModel getDefaultQueryYnWM()
public String getExportType()
public RadioButtonModel getExportTypeWM()
public Integer getMaxRecords()
public DropDownModel getMaxRecordsWM()
public boolean getMoreRecordsExist()
public String getNewQueryName()
public EditBoxModel getNewQueryNameWM()
public Long getNumberOfRecords()
public Boolean getQueryHasShortcutYn()
public RadioButtonModel getQueryHasShortcutYnWM()
public DateTime getQueryLastRunOn()
public GridModel getRowsWM()
public Long getSavedQueryId()
public DropDownModel getSavedQueryIdWM()
public String getSortDropDown()
public DropDownModel getSortDropDownWM()
public boolean isHasSavedQueries()
public abstract void populateRows(GridModel rows)
public void setDefaultQueryYn(Boolean defaultQueryYn)
public void setDefaultQueryYnWV(String value)
public void setExportType(String exportType)
public void setExportTypeWV(String value)
public void setMaxRecords(Integer maxRecords)
public void setMaxRecordsWV(String value)
public void setNewQueryName(String newQueryName)
public void setNewQueryNameWV(String value)
public void setQueryHasShortcutYn(Boolean queryHasShortcutYn)
public void setQueryHasShortcutYnWV(String value)
public void setRowsWV(String value)
public void setSavedQueryId(Long savedQueryId)
public void setSavedQueryIdWV(String value)
public void setSortDropDown(String sortDropDown)
public void setSortDropDownWV(String value)
```
***
**org.jaffa.components.finder.FinderInDto**

```
public void addOrderByFields(OrderByField orderByFields)
public void clearOrderByFields()
public Boolean getFindTotalRecords()
public Integer getFirstRecord()
public Integer getMaxRecords()
public OrderByField getOrderByFields(int index)
public OrderByField[] getOrderByFields()
public int getOrderByFieldsCount()
public boolean removeOrderByFields(OrderByField orderByFields)
public void setFindTotalRecords(Boolean findTotalRecords)
public void setFirstRecord(Integer firstRecord)
public void setMaxRecords(Integer maxRecords)
public void setOrderByFields(OrderByField orderByFields, int index)
public void setOrderByFields(OrderByField[] orderByFields)
public String toString()
```
***
**org.jaffa.components.finder.FinderOutDto**

```
public Boolean getMoreRecordsExist()
public Integer getTotalRecords()
public void setMoreRecordsExist(Boolean moreRecordsExist)
public void setTotalRecords(Integer totalRecords)
public String toString()
```
***
**org.jaffa.components.finder.FinderTx**

```
public static void addCriteria(CriteriaField field, String name, Criteria criteria)
public static void addCriteria(DateTimeCriteriaField field, String name, Criteria criteria)
public static void findTotalRecords(UOW uow, FinderInDto input, Criteria criteria, FinderOutDto output, int rowsCount) throws FrameworkException
public static boolean isCriteria(CriteriaField field)
public static boolean match(Object input, CriteriaField criteriaField)
```
***
**org.jaffa.components.finder.IntegerCriteriaField**

```
public static IntegerCriteriaField getIntegerCriteriaField(String operator, String value, IntegerFieldMetaData meta) throws FormatIntegerException
public String getOperator()
public Long[] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(Long[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.InvalidCriteriaRuntimeException**

No public methods.
***
**org.jaffa.components.finder.OrderByField**

```
public String getFieldName()
public Boolean getSortAscending()
public void setFieldName(String fieldName)
public void setSortAscending(Boolean sortAscending)
public String toString()
```
***
**org.jaffa.components.finder.QueryIdList**

```
public String nextAvailable()
public void remove(String toRemove)
public String[] toArray()
public String toString()
```
***
**org.jaffa.components.finder.QueryIdListTest**

```
public final void testReoveSorting()
public final void testnextAvailable()
public final void testnextAvailableAddInMiddle()
public final void testnextAvailableEmptyList()
public final void testreSorting()
public final void testtoArray()
```
***
**org.jaffa.components.finder.QueryNotFoundException**

No public methods.
***
**org.jaffa.components.finder.QuerySaver**

```
public static List<SavedQueryBean> getSavedShortcutQueryBeans()
public static void removeShortcutFlag(String componentName, String savedQueryId) throws IOException
```
***
**org.jaffa.components.finder.QuerySaverTest**

```
public final void testBuildPropertyStringEmptySuffix()
public final void testBuildPropertyStringMultipleSuffix()
public final void testBuildPropertyStringNullSuffix()
public final void testBuildPropertyStringSingleSuffix()
public final void testDecodeQueryDataMapMultipleMapEntry()
public final void testDecodeQueryDataMapSingleMapEntry()
public final void testEncodeQueryDataMapMultipleMapEntry()
public final void testEncodeQueryDataMapMultipleMapEntryOneNonString()
public final void testEncodeQueryDataMapSingleMapEntry()
public final void testGetDefaultQueryFields() throws NoDefaultException
public final void testGetDefaultQueryFieldsWithNoDefault()
public final void testGetDefaultQueryId()
public final void testGetSavedQueryFields()
public final void testGetSavedQueryList()
public final void testGetSavedQueryListNullValues()
public final void testRemoveQueryShortCut() throws IOException
public final void testRemoveQueryShortCutBackOfList() throws IOException
public final void testRemoveQueryShortCutFrontOfList() throws IOException
public final void testRemoveQueryShortCutLastInListList() throws IOException
public final void testRemoveQueryShortCutNotInList() throws IOException
public final void testRemoveSavedQuery() throws IOException
public final void testSaveQueryNoneSavedDefaultNotShortCut() throws IOException
public final void testSaveQueryNoneSavedDefaultShortCut() throws IOException
public final void testSaveQueryNoneSavedNotDefaultNotShortCut() throws IOException
```
***
**org.jaffa.components.finder.RawCriteriaField**

```
public String getOperator()
public static RawCriteriaField getRawCriteriaField(String operator, String value, RawFieldMetaData meta)
public byte[][] getValues()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(byte[][] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.finder.SavedQuery**

```
public String getId()
public String getName()
public String getUrl()
```
***
**org.jaffa.components.finder.SavedQueryBean**

```
public String getComponentName()
public List<SavedQuery> getSavedQueries()
public void setSavedQueryUrl(String id, String name, String url)
```
***
**org.jaffa.components.finder.StringCriteriaField**

```
public boolean equals(Object obj)
public String getOperator()
public static StringCriteriaField getStringCriteriaField(String operator, String value, StringFieldMetaData meta)
public String[] getValues()
public int hashCode()
public Object[] returnValuesAsObjectArray()
public void setOperator(String operator)
public void setValues(String[] values)
public String toString()
public void validate() throws InvalidCriteriaRuntimeException
```
***
**org.jaffa.components.lookup.IMultiSelectLookupListener**

```
public void rowsSelected(MultiSelectLookupEvent event) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.lookup.ISelectLookupListener**

```
public void rowSelected(SelectLookupEvent event) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.lookup.LookupAction**

```
public FormKey do_Close_Clicked()
public FormKey do_MultiSelect_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Select_Clicked(String rowNum)
```
***
**org.jaffa.components.lookup.LookupComponent2**

```
public void addMultiSelectLookupListener(IMultiSelectLookupListener listener)
public void addSelectLookupListener(ISelectLookupListener listener)
public FormKey displayResults() throws ApplicationExceptions, FrameworkException
public Boolean getAutoSelect()
public Boolean getErrorIfNoRows()
public String getReturnStyle()
public String getTargetFields()
public boolean isInMultiSelectLookupMode()
public boolean isInSelectLookupMode()
public FormKey performLookup(HttpServletRequest request, Object selectedRow)
public FormKey performMultiSelectLookup(HttpServletRequest request, MultiSelectLookupEvent event) throws ApplicationExceptions, FrameworkException
public FormKey performSelectLookup(HttpServletRequest request, SelectLookupEvent event) throws ApplicationExceptions, FrameworkException
public FormKey quitLookup(HttpServletRequest request)
public boolean removeMultiSelectLookupListener(IMultiSelectLookupListener listener)
public boolean removeSelectLookupListener(ISelectLookupListener listener)
public void setAutoSelect(Boolean autoSelect)
public void setErrorIfNoRows(Boolean errorIfNoRows)
public void setReturnStyle(String returnStyle)
public void setTargetFields(String targetFields)
```
***
**org.jaffa.components.lookup.LookupForm**

No public methods.
***
**org.jaffa.components.lookup.MultiSelectLookupEvent**

```
public Object[] getSelectedRows()
```
***
**org.jaffa.components.lookup.SelectLookupEvent**

```
public Object getSelectedRow()
```
***
**org.jaffa.components.maint.CreateComponent**

```
public void addCreateListener(ICreateListener listener)
public void quit()
public boolean removeCreateListener(ICreateListener listener)
```
***
**org.jaffa.components.maint.DeleteComponent**

```
public void addDeleteListener(IDeleteListener listener)
public void quit()
public boolean removeDeleteListener(IDeleteListener listener)
```
***
**org.jaffa.components.maint.ICreateComponent**

```
public void addCreateListener(ICreateListener listener)
public boolean removeCreateListener(ICreateListener listener)
```
***
**org.jaffa.components.maint.ICreateListener**

```
public void createDone(EventObject source)
```
***
**org.jaffa.components.maint.IDeleteComponent**

```
public void addDeleteListener(IDeleteListener listener)
public boolean removeDeleteListener(IDeleteListener listener)
```
***
**org.jaffa.components.maint.IDeleteListener**

```
public void deleteDone(EventObject source)
```
***
**org.jaffa.components.maint.IMaintComponent**

```
public int getMode()
public boolean isCreateMode()
public boolean isDeleteMode()
public boolean isUpdateMode()
public void setMode(int mode)
```
***
**org.jaffa.components.maint.IUpdateComponent**

```
public void addUpdateListener(IUpdateListener listener)
public boolean removeUpdateListener(IUpdateListener listener)
```
***
**org.jaffa.components.maint.IUpdateListener**

```
public void updateDone(EventObject source)
```
***
**org.jaffa.components.maint.MaintAction**

```
public FormKey do_Cancel_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_Finish_Clicked()
public FormKey do_Next_Clicked()
public FormKey do_Previous_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.components.maint.MaintComponent2**

```
public void addCreateListener(ICreateListener listener)
public void addDeleteListener(IDeleteListener listener)
public void addDisplayOnlyField(String fieldName)
public void addUpdateListener(IUpdateListener listener)
public void create() throws ApplicationExceptions, FrameworkException
public void delete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException
public MaintComponent2.Screen determineAndSetNextScreen()
public MaintComponent2.Screen determineAndSetPreviousScreen()
public MaintComponent2.Screen determineCurrentScreen()
public static URL determineDefaultValuesUrl(Class componentClass)
public FormKey determineFormKey()
public MaintComponent2.Screen determineNextScreen()
public MaintComponent2.Screen determinePreviousScreen()
public FormKey display() throws ApplicationExceptions, FrameworkException
public int getCurrentScreenCounter()
public int getMode()
public MaintComponent2.Screen[] getScreens()
public boolean isCreateMode()
public boolean isDeleteMode()
public boolean isDisplayOnlyField(String fieldName)
public boolean isRefreshData()
public boolean isUpdateMode()
public void prevalidateCreate() throws ApplicationExceptions, FrameworkException
public void prevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException
public void quit()
public boolean removeCreateListener(ICreateListener listener)
public boolean removeDeleteListener(IDeleteListener listener)
public boolean removeUpdateListener(IUpdateListener listener)
public void retrieve() throws ApplicationExceptions, FrameworkException
public void setCurrentScreenCounter(int currentScreenCounter)
public void setMode(int mode)
public void setReturnToFormKey(FormKey returnToFormKey)
public void update(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.components.maint.MaintForm**

```
public boolean doValidate(HttpServletRequest request)
public int getCurrentScreenCounter()
public boolean isClearActionAvailable()
public boolean isDeleteActionAvailable()
public boolean isDisplayOnlyField(String fieldName)
public boolean isFinishActionAvailable()
public boolean isNextActionAvailable()
public boolean isPreviousActionAvailable()
public boolean isRefreshActionAvailable()
public boolean isSaveActionAvailable()
public boolean isUpdateMode()
public void setCurrentScreenCounter(int currentScreenCounter)
```
***
**org.jaffa.components.maint.MaintTx**

No public methods.
***
**org.jaffa.components.maint.Screen**

```
public String getFormName()
public boolean isAvailableInCreateMode()
public boolean isAvailableInUpdateMode()
public boolean isPerformTxValidationOnNextAction()
public boolean isSaveActionAvailableInCreateMode()
```
***
**org.jaffa.components.maint.UpdateComponent**

```
public void addUpdateListener(IUpdateListener listener)
public void quit()
public boolean removeUpdateListener(IUpdateListener listener)
```
***
**org.jaffa.components.menu.Group**

```
public String getDescription()
public String getGroupIcon()
public String getGroupName()
public String getGroupTitle()
public void setDescription(String description)
public void setGroupIcon(String groupIcon)
public void setGroupName(String groupName)
public void setGroupTitle(String groupTitle)
public String toString()
```
***
**org.jaffa.components.menu.MainMenu**

```
public void determineComp(HttpServletRequest request, PageContext pageContext)
public Map getComponents()
public static void main(String[] args)
public void reset()
```
***
**org.jaffa.components.menu.Option**

```
public String getCompURL()
public String getIcon()
public String getName()
public boolean getURLTrue()
public void setCompURL(String compURL)
public void setIcon(String icon)
public void setName(String name)
public void setURLTrue(boolean URLTrue)
public String toString()
```
***
**org.jaffa.components.navigation.NavAccessor**

```
public void clearSession(HttpSession session)
public NavOption getDesktopNavOptions(String desktopId)
public List getGlobalNavOptions()
public static NavAccessor getNavAccessor(HttpServletRequest request)
public static void main(String[] args)
```
***
**org.jaffa.components.navigation.NavCache**

```
public static void clearCache()
public static String getDefaultFileLocation()
public static String getFileLocation()
public GlobalMenu getGlobalMenu()
public static NavCache getInstance()
public static NavigationManager getNavigationManager()
public static String getVariationFileLocation()
public static void setNavigationManager(NavigationManager navigationManager)
```
***
**org.jaffa.components.navigation.NavOption**

```
public List getChildren()
public String getComponent()
public String getDesktopId()
public String getHomePage()
public String getLabel()
public String getParameters()
public String getStrutsTileTemplate()
public String getTarget()
public String getToken()
public String getType()
public String getURL()
public boolean isComponent()
public boolean isDesktop()
public boolean isRia()
public boolean isSubMenu()
public boolean isURL()
```
***
**org.jaffa.components.navigation.domain.ComponentAction**

```
public String getComponentName()
public List<ComponentAction.Param> getParam()
public String getTarget()
public String getUrlSuffix()
public void setComponentName(String value)
public void setTarget(String value)
public void setUrlSuffix(String value)
```
***
**org.jaffa.components.navigation.domain.DesktopMenu**

```
public String getHomePage()
public String getLabel()
public List<MenuOption> getMenuOption()
public String getStrutsTileTemplate()
public void setHomePage(String value)
public void setLabel(String value)
public void setStrutsTileTemplate(String value)
```
***
**org.jaffa.components.navigation.domain.GlobalMenu**

```
public List<MenuOption> getMenuOption()
```
***
**org.jaffa.components.navigation.domain.MenuOption**

```
public ComponentAction getComponentAction()
public DesktopMenu getDesktopMenu()
public String getLabel()
public SubMenu getSubMenu()
public UrlAction getUrlAction()
public void setComponentAction(ComponentAction value)
public void setDesktopMenu(DesktopMenu value)
public void setLabel(String value)
public void setSubMenu(SubMenu value)
public void setUrlAction(UrlAction value)
```
***
**org.jaffa.components.navigation.domain.ObjectFactory**

```
public ComponentAction createComponentAction()
public ComponentAction.Param createComponentActionParam()
public DesktopMenu createDesktopMenu()
public GlobalMenu createGlobalMenu()
public MenuOption createMenuOption()
public SubMenu createSubMenu()
public UrlAction createUrlAction()
public UrlAction.Url createUrlActionUrl()
```
***
**org.jaffa.components.navigation.domain.Param**

```
public String getName()
public String getValue()
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.components.navigation.domain.SubMenu**

```
public List<MenuOption> getMenuOption()
```
***
**org.jaffa.components.navigation.domain.Url**

```
public String getValue()
public boolean isAppendFinal()
public void setAppendFinal(Boolean value)
public void setValue(String value)
```
***
**org.jaffa.components.navigation.domain.UrlAction**

```
public List<String> getRequiresComponentAccess()
public List<String> getRequiresFunctionAccess()
public String getTarget()
public UrlAction.Url getUrl()
public void setTarget(String value)
public void setUrl(UrlAction.Url value)
```
***
**org.jaffa.components.voucher.AbstractVoucherGenerator**

```
public abstract String generate() throws FrameworkException, ApplicationExceptions
public Connection getConnection()
public String getDomainClassName()
public String getFieldName()
public String getLabelToken()
public Integer getLength()
public String getPrefix()
public String getType()
public UOW getUow()
public void setConnection(Connection connection)
public void setDomainClassName(String domainClassName)
public void setFieldName(String fieldName)
public void setLabelToken(String labelToken)
public void setLength(Integer length)
public void setPrefix(String prefix)
public void setType(String type)
public void setUow(UOW uow)
```
***
**org.jaffa.components.voucher.IVoucherGenerator**

```
public String generate() throws FrameworkException, ApplicationExceptions
public Connection getConnection()
public String getDomainClassName()
public String getFieldName()
public String getLabelToken()
public Integer getLength()
public String getPrefix()
public String getType()
public UOW getUow()
public void setConnection(Connection connection)
public void setDomainClassName(String domainClassName)
public void setFieldName(String fieldName)
public void setLabelToken(String labelToken)
public void setLength(Integer length)
public void setPrefix(String prefix)
public void setType(String type)
public void setUow(UOW uow)
```
***
**org.jaffa.components.voucher.SimpleVoucherGenerator**

```
public String generate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.components.voucher.VoucherGeneratorException**

No public methods.
***
**org.jaffa.components.voucher.VoucherGeneratorFactory**

```
public static IVoucherGenerator instance()
```
***
**org.jaffa.config.AppConfigServlet**

```
public void destroy()
public void init(ServletConfig servletConfig) throws ServletException
```
***
**org.jaffa.config.ApplicationResourceLoader**

```
public Map<String, Properties> getApplicationResources()
public Properties getApplicationResourcesDefault()
public Properties getApplicationResourcesOverride()
public static ApplicationResourceLoader getInstance()
public Properties getLocaleProperties(String locale)
```
***
**org.jaffa.config.ApplicationResourceLoaderTest**

```
public void testApplicationResourceLoader()
```
***
**org.jaffa.config.ComponentLoaderTest**

```
public void testComponentLoader() throws Exception
```
***
**org.jaffa.config.Config**

```
public static Object getProperty(String key) throws MissingResourceException
public static Object getProperty(String key, Object defValue)
public static void setProperty(String key, Object value)
```
***
**org.jaffa.config.ContextManagerTest**

```
public void testContextManager() throws Exception
```
***
**org.jaffa.config.InitApp**

```
public void destroy()
public static void generateApplicationResources() throws IOException
public void init(ServletConfig cfg) throws ServletException
```
***
**org.jaffa.config.InitAppTest**

```
public void generateApplicationResourcesTest() throws Exception
public void setUp() throws Exception
```
***
**org.jaffa.config.InitLog4J**

```
public static void init()
```
***
**org.jaffa.config.PersistentBeanConfig**

```
public Persistent persistent(Persistent persistent) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.config.PersistentConfig**

```
public ILifecycleHandlerFactory lifecycleHandlerFactory()
public void setApplicationContext(ApplicationContext applicationContext)
```
***
**org.jaffa.config.SpringConfig**

No public methods.
***
**org.jaffa.datatypes.ConvertDataTypesTest**

```
public static void main(String[] args)
public void testAddClassConverters() throws Exception
public void testAddConverters() throws Exception
public void testConvertStringToBoolean()
public void testConvertStringToDateOnly()
public void testConvertStringToDateTime()
public void testConvertStringToDouble()
public void testConvertStringToFloat()
public void testConvertStringToInteger()
public void testConvertStringToLong()
public void testConvertStringToShort()
public void testConvertStringToboolean()
public void testConvertStringToint()
public void testJaffaParserConversion() throws Exception
public void testJaffaTypesConversion() throws Exception
public void testPrimativeConversion() throws Exception
public void testPrimativeParserConversion() throws Exception
public void testPrimativeWrapperConversion() throws Exception
public void testPrimativeWrapperParserConversion() throws Exception
```
***
**org.jaffa.datatypes.Converter**

```
public static String abcd(StringBuffer b)
```
***
**org.jaffa.datatypes.Currency**

```
public Currency add(Currency c)
public Object clone()
public int compareTo(Object obj)
public Currency convert(String currencyType)
public boolean equals(Object obj)
public String getCurrencyType()
public Double getValue()
public int hashCode()
public String toString()
```
***
**org.jaffa.datatypes.DataTypeMapper**

```
public void addMapping(Class source, Class target, Object converters)
public int addMappings(Class mapper, boolean publicOnly)
public static Class autoBox(Class clazz)
public static DataTypeMapper instance()
public boolean isMappable(Class source, Class target)
public static void main(String[] args) throws Exception
public Object map(Object source, Class target) throws ClassCastException
public Object map(Object source, Class target, String layout) throws ClassCastException
public static String[] parseString(String s)
public boolean removeMapping(Class source, Class target)
public static Character toCharacter(String s) throws ClassCastException
```
***
**org.jaffa.datatypes.DateBase**

```
public Object clone() throws CloneNotSupportedException
public int compareTo(Object obj)
public boolean equals(Object obj)
public int hashCode()
public String toString()
```
***
**org.jaffa.datatypes.DateOnly**

```
public static DateOnly addDay(DateOnly date, int days)
public static DateOnly addMonth(DateOnly date, int months)
public static DateOnly addYear(DateOnly date, int years)
public Calendar calendar()
public Object clone() throws CloneNotSupportedException
public int compareTo(Object obj)
public int day()
public int dayOfWeek()
public int dayOfWeekInMonth()
public int dayOfYear()
public static int daysBetween(DateOnly date1, DateOnly date2)
public boolean equals(Object obj)
public int firstDayOfWeek()
public java.util.Date getUtilDate()
public int hashCode()
public boolean isAfter(DateOnly when)
public boolean isBefore(DateOnly when)
public int julian()
public int month()
public static DateOnly parse(String dateString) throws ParseException
public static DateOnly parse(String dateString, String layout) throws ParseException
public void setUtilDate(java.util.Date utilDate)
public java.sql.Date sqlDate()
public Time sqlTime()
public long timeInMillis()
public Timestamp timestamp()
public static DateTime toDateTime(DateOnly date)
public String toString()
public int weekOfMonth()
public int weekOfYear()
public int year()
```
***
**org.jaffa.datatypes.DateOnlyTest**

```
public void testClone()
public void testCompareTo()
public void testEquals()
public void testParse()
```
***
**org.jaffa.datatypes.DateTime**

```
public static DateTime addDay(DateTime datetime, int days)
public static DateTime addHour(DateTime datetime, int hours)
public static DateTime addMilli(DateTime datetime, int millis)
public static DateTime addMinute(DateTime datetime, int minutes)
public static DateTime addMonth(DateTime datetime, int months)
public static DateTime addSecond(DateTime datetime, int seconds)
public static DateTime addYear(DateTime datetime, int years)
public boolean am()
public Calendar calendar()
public static DateTime clearTime(DateTime datetime)
public Object clone() throws CloneNotSupportedException
public int compareTo(Object obj)
public int day()
public int dayOfWeek()
public int dayOfWeekInMonth()
public int dayOfYear()
public static float daysBetween(DateTime date1, DateTime date2)
public boolean equals(Object obj)
public int firstDayOfWeek()
public java.util.Date getUtilDate()
public int hashCode()
public int hour()
public int hourOfDay()
public boolean isAfter(DateTime when)
public boolean isBefore(DateTime when)
public int julian()
public int milli()
public int minute()
public int month()
public static DateTime parse(String dateString) throws ParseException
public static DateTime parse(String dateString, String layout) throws ParseException
public boolean pm()
public void roundMinutes(int precision, String rule) throws DateTimeRoundingPrecisionException
public int second()
public void setUtilDate(java.util.Date utilDate)
public java.sql.Date sqlDate()
public Time sqlTime()
public long timeInMillis()
public Timestamp timestamp()
public static DateOnly toDateOnly(DateTime datetime)
public String toString()
public int weekOfMonth()
public int weekOfYear()
public int year()
```
***
**org.jaffa.datatypes.DateTimeTest**

```
public void testClearTime()
public void testClone()
public void testCompareTo()
public void testEquals()
public void testParse()
public void testToDateOnlyAndBack()
```
***
**org.jaffa.datatypes.Defaults**

```
public static Class getClass(String key)
public static String getClassString(String key)
public static String getDataType(Object obj)
public static String getDataType(String clazz)
```
***
**org.jaffa.datatypes.FieldValidator**

```
public static String validate(String field, StringFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, TooMuchDataException, TooLittleDataException, PatternMismatchException
public static Boolean validate(Boolean field, BooleanFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException
public static Currency validate(Currency field, CurrencyFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, TooMuchDataException, BelowMinimumException, ExceedsMaximumException
public static DateOnly validate(DateOnly field, DateOnlyFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, BelowMinimumException, ExceedsMaximumException
public static DateTime validate(DateTime field, DateTimeFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, BelowMinimumException, ExceedsMaximumException
public static Double validate(Double field, DecimalFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, TooMuchDataException, BelowMinimumException, ExceedsMaximumException
public static Long validate(Long field, IntegerFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, TooMuchDataException, BelowMinimumException, ExceedsMaximumException
public static byte[] validate(byte[] field, RawFieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException
public static Object validateData(String field, FieldMetaData meta) throws MandatoryFieldException, TooMuchDataException, TooLittleDataException, PatternMismatchException, BelowMinimumException, ExceedsMaximumException, FormatCurrencyException, FormatDateOnlyException, FormatDateTimeException, FormatDecimalException, FormatIntegerException
public static Object validateData(String field, FieldMetaData meta, boolean checkMandatory) throws MandatoryFieldException, TooMuchDataException, TooLittleDataException, PatternMismatchException, BelowMinimumException, ExceedsMaximumException, FormatCurrencyException, FormatDateOnlyException, FormatDateTimeException, FormatDecimalException, FormatIntegerException
```
***
**org.jaffa.datatypes.FieldValidatorTest**

```
public static void main(java.lang.String[] args)
public void setUp()
public static Test suite()
public void tearDown()
public void testValidateBoolean()
public void testValidateCurrency()
public void testValidateDateOnly()
public void testValidateDateTime()
public void testValidateDecimal()
public void testValidateInteger()
public void testValidateRaw()
public void testValidateString()
```
***
**org.jaffa.datatypes.Formatter**

```
public static String format(Boolean input)
public static String format(Boolean input, String layout)
public static String format(Long input)
public static String format(Long input, String layout)
public static String format(Double input)
public static String format(Double input, String layout)
public static String format(DateOnly input)
public static String format(DateOnly input, String layout)
public static String format(DateTime input)
public static String format(DateTime input, String layout)
public static String format(Currency input)
public static String format(Currency input, String layout)
public static String format(String input)
public static String format(byte[] input)
public static String format(Object input)
public static String format(Object input, String layout)
```
***
**org.jaffa.datatypes.FormatterTest**

```
public static void main(java.lang.String[] args)
public void setUp()
public static Test suite()
public void tearDown()
public void testFormatBoolean()
public void testFormatCurrency() throws ValidationException
public void testFormatDateOnly() throws ValidationException
public void testFormatDateTime() throws ValidationException
public void testFormatDecimal() throws ValidationException
public void testFormatInteger() throws ValidationException
public void testFormatRaw() throws ValidationException
```
***
**org.jaffa.datatypes.IDateBase**

No public methods.
***
**org.jaffa.datatypes.Parser**

```
public static Boolean parseBoolean(String input)
public static Boolean parseBoolean(String input, String layout)
public static Currency parseCurrency(String input) throws FormatCurrencyException
public static Currency parseCurrency(String input, String layout) throws FormatCurrencyException
public static DateOnly parseDateOnly(String input) throws FormatDateOnlyException
public static DateOnly parseDateOnly(String input, String layout) throws FormatDateOnlyException
public static DateTime parseDateTime(String input) throws FormatDateTimeException
public static DateTime parseDateTime(String input, String layout) throws FormatDateTimeException
public static Double parseDecimal(String input) throws FormatDecimalException
public static Double parseDecimal(String input, String layout) throws FormatDecimalException
public static Long parseInteger(String input) throws FormatIntegerException
public static Long parseInteger(String input, String layout) throws FormatIntegerException
public static byte[] parseRaw(String input)
public static String parseString(String input)
```
***
**org.jaffa.datatypes.ValidationException**

```
public void setField(String field)
```
***
**org.jaffa.datatypes.adapters.BooleanTFConverter**

```
public String convertToDatabaseColumn(Boolean value)
public Boolean convertToEntityAttribute(String value)
```
***
**org.jaffa.datatypes.adapters.BooleanYNConverter**

```
public String convertToDatabaseColumn(Boolean value)
public Boolean convertToEntityAttribute(String value)
```
***
**org.jaffa.datatypes.adapters.DateOnlyConverter**

```
public Date convertToDatabaseColumn(DateOnly value)
public DateOnly convertToEntityAttribute(Date value)
```
***
**org.jaffa.datatypes.adapters.DateOnlyType**

```
public Object assemble(Serializable serializable, Object o) throws HibernateException
public Object deepCopy(Object o) throws HibernateException
public Serializable disassemble(Object o) throws HibernateException
public boolean equals(Object x, Object y) throws HibernateException
public int hashCode(Object o) throws HibernateException
public boolean isMutable()
public Object nullSafeGet(ResultSet resultSet, String[] names, Object o) throws HibernateException, SQLException
public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException, SQLException
public Object replace(Object o, Object o1, Object o2) throws HibernateException
public Class returnedClass()
public int[] sqlTypes()
```
***
**org.jaffa.datatypes.adapters.DateTimeConverter**

```
public Date convertToDatabaseColumn(DateTime value)
public DateTime convertToEntityAttribute(Date value)
```
***
**org.jaffa.datatypes.adapters.DateTimeType**

```
public Object assemble(Serializable serializable, Object o) throws HibernateException
public Object deepCopy(Object o) throws HibernateException
public Serializable disassemble(Object o) throws HibernateException
public boolean equals(Object x, Object y) throws HibernateException
public int hashCode(Object o) throws HibernateException
public boolean isMutable()
public Object nullSafeGet(ResultSet resultSet, String[] names, Object o) throws HibernateException, SQLException
public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException, SQLException
public Object replace(Object o, Object o1, Object o2) throws HibernateException
public Class returnedClass()
public int[] sqlTypes()
```
***
**org.jaffa.datatypes.adapters.TimestampAdapter**

```
public Date marshal(Timestamp v)
public Timestamp unmarshal(Date v)
```
***
**org.jaffa.datatypes.exceptions.BelowMinimumException**

No public methods.
***
**org.jaffa.datatypes.exceptions.DateTimeRoundingPrecisionException**

No public methods.
***
**org.jaffa.datatypes.exceptions.ExceedsMaximumException**

No public methods.
***
**org.jaffa.datatypes.exceptions.FormatCurrencyException**

No public methods.
***
**org.jaffa.datatypes.exceptions.FormatDateOnlyException**

No public methods.
***
**org.jaffa.datatypes.exceptions.FormatDateTimeException**

No public methods.
***
**org.jaffa.datatypes.exceptions.FormatDecimalException**

No public methods.
***
**org.jaffa.datatypes.exceptions.FormatIntegerException**

No public methods.
***
**org.jaffa.datatypes.exceptions.InListException**

No public methods.
***
**org.jaffa.datatypes.exceptions.InvalidForeignKeyException**

No public methods.
***
**org.jaffa.datatypes.exceptions.InvalidGenericForeignKeyException**

No public methods.
***
**org.jaffa.datatypes.exceptions.InvalidPatternRuntimeException**

No public methods.
***
**org.jaffa.datatypes.exceptions.MandatoryFieldException**

No public methods.
***
**org.jaffa.datatypes.exceptions.PatternExclusionException**

No public methods.
***
**org.jaffa.datatypes.exceptions.PatternMismatchException**

No public methods.
***
**org.jaffa.datatypes.exceptions.TooLittleDataException**

No public methods.
***
**org.jaffa.datatypes.exceptions.TooMuchDataException**

No public methods.
***
**org.jaffa.datatypes.exceptions.UnknownDataTypeRuntimeException**

No public methods.
***
**org.jaffa.exceptions.ApplicationException**

```
public void addContext(String context)
public String getLocalizedMessage()
```
***
**org.jaffa.exceptions.ApplicationExceptionWithContext**

No public methods.
***
**org.jaffa.exceptions.ApplicationExceptions**

```
public boolean add(ApplicationException exception)
public void addContext(String context)
public ApplicationException[] getApplicationExceptionArray()
public String getLocalizedMessage()
public String getMessage()
public Iterator iterator()
public void printStackTrace()
public void printStackTrace(PrintStream s)
public void printStackTrace(PrintWriter s)
public boolean remove(ApplicationException exception)
public int size()
```
***
**org.jaffa.exceptions.ComponentExpiredException**

No public methods.
***
**org.jaffa.exceptions.CustomException**

```
public Object[] getArguments()
public String getLocalizedMessage()
public String getMessage()
public void setArguments(Object[] arguments)
```
***
**org.jaffa.exceptions.CustomRuntimeException**

No public methods.
***
**org.jaffa.exceptions.DomainObjectChangedException**

No public methods.
***
**org.jaffa.exceptions.DomainObjectNotFoundException**

No public methods.
***
**org.jaffa.exceptions.DuplicateCandidateKeyException**

No public methods.
***
**org.jaffa.exceptions.DuplicateKeyException**

No public methods.
***
**org.jaffa.exceptions.FrameworkException**

No public methods.
***
**org.jaffa.exceptions.GetQueryDataException**

No public methods.
***
**org.jaffa.exceptions.IncompleteKeySpecifiedException**

No public methods.
***
**org.jaffa.exceptions.LabelException**

No public methods.
***
**org.jaffa.exceptions.MiddlewareException**

No public methods.
***
**org.jaffa.exceptions.MultipleDomainObjectsFoundException**

No public methods.
***
**org.jaffa.exceptions.NoDefaultException**

No public methods.
***
**org.jaffa.exceptions.RelatedDomainObjectFoundException**

No public methods.
***
**org.jaffa.exceptions.SaveQueryException**

No public methods.
***
**org.jaffa.exceptions.ScriptingException**

No public methods.
***
**org.jaffa.exceptions.ServiceFault**

```
public String[] getErrors()
public void setErrors(String[] errors)
public String toString()
```
***
**org.jaffa.exceptions.SetQueryDataException**

No public methods.
***
**org.jaffa.exceptions.TokenMismatchException**

No public methods.
***
**org.jaffa.exceptions.UOWSecurityException**

No public methods.
***
**org.jaffa.flexfields.FlexBean**

```
public final void addPropertyChangeListener(PropertyChangeListener l)
public void clearChanges()
public static void configureFlexBean(IFlexFields flexInstance)
public boolean contains(String name, String key)
public void delete() throws ApplicationExceptions, FrameworkException
public Object get(String name)
public Object get(String name, int index)
public Object get(String name, String key)
public DynaClass getDynaClass()
public FlexParam[] getFlexParams()
public Object getOriginalValue(String name) throws NoSuchFieldException
public IPersistent getPersistentObject()
public boolean hasChanged()
public boolean hasChanged(String name)
public static FlexBean instance(Object object) throws ApplicationExceptions, FrameworkException
public static FlexBean instance(FlexClass flexClass) throws ApplicationExceptions, FrameworkException
public void remove(String name, String key)
public final void removePropertyChangeListener(PropertyChangeListener l)
public void set(String name, Object value)
public void set(String name, int index, Object value)
public void set(String name, String key, Object value)
public void setDynaClass(DynaClass flexClass)
public void setFlexParams(FlexParam[] flexParams)
public String toString()
public void update() throws ApplicationExceptions, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.flexfields.FlexClass**

```
public FlexProperty[] getDynaProperties()
public FlexProperty getDynaProperty(String name)
public FlexProperty getDynaPropertyByLogicalName(String logicalName)
public String getLogicalName()
public String getName()
public String getNameByLogicalName(String logicalName)
public static FlexClass instance(String className) throws ApplicationExceptions, FrameworkException
public static FlexClass instance(Class clazz) throws ApplicationExceptions, FrameworkException
public static FlexClass instance(Object object) throws ApplicationExceptions, FrameworkException
public FlexBean newInstance() throws IllegalAccessException, InstantiationException
public void setName(String name)
```
***
**org.jaffa.flexfields.FlexCriteriaBean**

```
public static void configureFlexFields(IFlexCriteriaFields flexCriteraInstance)
public boolean contains(String name, String key)
public Object get(String name, int index)
public Object get(String name, String key)
public Object get(String name)
public DynaClass getDynaClass()
public FlexCriteriaParam[] getFlexCriteriaParams()
public static FlexCriteriaBean instance(Object object) throws ApplicationExceptions, FrameworkException
public static FlexCriteriaBean instance(FlexClass flexClass) throws ApplicationExceptions, FrameworkException
public void remove(String name, String key)
public Criteria returnQueryClause(Criteria criteria) throws FrameworkException
public void set(String name, int index, Object value)
public void set(String name, String key, Object value)
public void set(String name, Object value)
public void setDynaClass(DynaClass flexClass)
public void setFlexCriteriaParams(FlexCriteriaParam[] flexCriteriaParams)
public String toString()
```
***
**org.jaffa.flexfields.FlexCriteriaParam**

```
public boolean equals(Object obj)
public String getName()
public StringCriteriaField getValue()
public int hashCode()
public void setName(String name)
public void setValue(StringCriteriaField value)
public String toString()
```
***
**org.jaffa.flexfields.FlexParam**

```
public Object clone()
public int compareTo(Object o)
public boolean equals(Object obj)
public String getName()
public String getValue()
public int hashCode()
public void setName(String name)
public void setValue(String value)
public String toString()
```
***
**org.jaffa.flexfields.FlexProperty**

```
public Properties getFlexInfo()
public String getLogicalName()
```
***
**org.jaffa.flexfields.IFlexCriteriaFields**

```
public FlexCriteriaBean getFlexCriteriaBean() throws ApplicationExceptions, FrameworkException
public void setFlexCriteriaBean(FlexCriteriaBean flexCriteriaBean) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.flexfields.IFlexFields**

```
public FlexBean getFlexBean() throws ApplicationExceptions, FrameworkException
public void setFlexBean(FlexBean flexBean) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.flexfields.domain.FlexField**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String flexFieldId) throws FrameworkException
public static FlexField findByPK(UOW uow, java.lang.String flexFieldId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String flexFieldId)
public void generateKey() throws ApplicationExceptions, FrameworkException
public java.lang.String getFieldName()
public java.lang.String getFlexFieldId()
public java.lang.String getKey1()
public java.lang.String getKey2()
public java.lang.String getKey3()
public java.lang.String getObjectName()
public java.lang.String getValue()
public java.lang.Long getVersionNumber()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFlexFieldId(java.lang.String flexFieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKey1(java.lang.String key1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKey2(java.lang.String key2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKey3(java.lang.String key3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateFieldName(java.lang.String fieldName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFlexFieldId(java.lang.String flexFieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKey1(java.lang.String key1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKey2(java.lang.String key2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKey3(java.lang.String key3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateValue(java.lang.String value) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.String validateFlexFieldId(java.lang.String flexFieldId) throws ValidationException, FrameworkException
public java.lang.String validateKey1(java.lang.String key1) throws ValidationException, FrameworkException
public java.lang.String validateKey2(java.lang.String key2) throws ValidationException, FrameworkException
public java.lang.String validateKey3(java.lang.String key3) throws ValidationException, FrameworkException
public java.lang.String validateObjectName(java.lang.String objectName) throws ValidationException, FrameworkException
public java.lang.String validateValue(java.lang.String value) throws ValidationException, FrameworkException
public java.lang.Long validateVersionNumber(java.lang.Long versionNumber) throws ValidationException, FrameworkException
```
***
**org.jaffa.flexfields.domain.FlexFieldDelete**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String flexFieldId) throws FrameworkException
public static FlexFieldDelete findByPK(UOW uow, java.lang.String flexFieldId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String flexFieldId)
public org.jaffa.datatypes.DateTime getDeletionCreatedOn()
public java.lang.String getFlexFieldId()
public java.lang.String getKey1()
public java.lang.String getObjectName()
public java.lang.Long getVersionNumber()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void setDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFlexFieldId(java.lang.String flexFieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKey1(java.lang.String key1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFlexFieldId(java.lang.String flexFieldId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKey1(java.lang.String key1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateObjectName(java.lang.String objectName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateVersionNumber(java.lang.Long versionNumber) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public org.jaffa.datatypes.DateTime validateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn) throws ValidationException, FrameworkException
public java.lang.String validateFlexFieldId(java.lang.String flexFieldId) throws ValidationException, FrameworkException
public java.lang.String validateKey1(java.lang.String key1) throws ValidationException, FrameworkException
public java.lang.String validateObjectName(java.lang.String objectName) throws ValidationException, FrameworkException
public java.lang.Long validateVersionNumber(java.lang.Long versionNumber) throws ValidationException, FrameworkException
```
***
**org.jaffa.flexfields.domain.FlexFieldDeleteMeta**

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
**org.jaffa.flexfields.domain.FlexFieldMeta**

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
**org.jaffa.integrationapi.LocaleProvider**

```
public Locale getLocale()
```
***
**org.jaffa.integrationimpl.ContextManagerLocaleProvider**

```
public Locale getLocale()
```
***
**org.jaffa.loader.ContextKey**

```
public int compareTo(ContextKey o)
public boolean equals(Object o)
public String getFileName()
public String getId()
public String getPrecedence()
public String getVariation()
public int hashCode()
public void setFileName(String fileName)
public void setId(String id)
public void setPrecedence(String precedence)
public void setVariation(String variation)
public String toString()
```
***
**org.jaffa.loader.CoreLoaderConfig**

```
public ApplicationRulesManager applicationRulesManager()
public ResourceLoader<ApplicationRulesManager> applicationRulesManagerPropertiesLoader()
public BusinessFunctionManager businessFunctionManager()
public ResourceLoader<BusinessFunctionManager> businessFunctionManagerXmlLoader()
public NavigationManager navigationManager()
public ResourceLoader<NavigationManager> navigationManagerXmlLoader()
public RoleManager roleManager()
public ResourceLoader<RoleManager> roleManagerXmlLoader()
```
***
**org.jaffa.loader.IManager**

No public methods.
***
**org.jaffa.loader.IRepository**

```
public String getName()
public Map<String, T> getRepositoryByVariation(String variation)
public Map getRepositoryMap()
public T queryByVariation(String id, String variation)
```
***
**org.jaffa.loader.ManagerRepositoryService**

```
public void add(String className, IManager manager)
public static ManagerRepositoryService getInstance()
public Map<String, IManager> getManagerMap()
```
***
**org.jaffa.loader.MapRepository**

```
public synchronized ContextKey findKey(String id)
public synchronized Set<String> getAllKeyIds()
public synchronized Set<ContextKey> getAllKeys()
public synchronized List<T> getAllValues()
public Set<String> getKeyIds()
public Set<ContextKey> getKeys()
public synchronized Map<String, T> getMyRepository()
public String getName()
public synchronized Map<String, T> getRepositoryByVariation(String variation)
public Map getRepositoryMap()
public List<T> getValues()
public synchronized T query(ContextKey contextKey)
public synchronized T query(String id)
public synchronized T queryByVariation(String id, String variation)
public synchronized void register(ContextKey repositoryKey, T repositoryElement)
public synchronized void unregister(ContextKey contextKey)
```
***
**org.jaffa.loader.MapRepositoryTest**

```
public void setup()
public void testGetAllKeys()
public void testGetAllValues()
public void testGetName()
public void testKeyOrder()
public void testQuery()
public void testRegister()
public void testUnregister()
```
***
**org.jaffa.loader.ResourceLoader**

```
public T getManager()
public void loadXmls()
public void setManager(T manager)
```
***
**org.jaffa.loader.ResourceLoaderTest**

```
public void setup()
public void testGetContext()
public void testLoadXmls() throws Exception
```
***
**org.jaffa.loader.components.ComponentManager**

```
public ComponentDefinition getComponentDefinition(String name)
public IRepository<ComponentDefinition> getComponentRepository()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public void registerComponentDefinition(ContextKey contextKey, ComponentDefinition value)
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void setComponentRepository(IRepository<ComponentDefinition> repository)
public void unregisterComponentDefinition(ContextKey contextKey)
```
***
**org.jaffa.loader.components.ComponentManagerTest**

```
public void getXmlFileName() throws Exception
public void setComponentRepository() throws Exception
public void setUp() throws Exception
public void tearDown() throws Exception
public void testGetComponentRepository() throws Exception
public void testGetName() throws Exception
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testRegisterComponentDefinition() throws Exception
```
***
**org.jaffa.loader.components.ComponentResourceLoaderTest**

```
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testXmlLoad()
```
***
**org.jaffa.loader.components.ComponentXmlLoaderConfig**

```
public ComponentManager componentManager()
public ResourceLoader<ComponentManager> componentManagerXmlLoader()
```
***
**org.jaffa.loader.components.ComponentXmlLoaderTestConfig**

```
public ComponentManager componentManager()
public ArrayList<String> contextOrder()
public ArrayList<String> defaultContexts()
```
***
**org.jaffa.loader.config.ApplicationRulesManager**

```
public Properties getApplicationRulesGlobal()
public IRepository<Properties> getApplicationRulesRepository()
public Properties getApplicationRulesVariation(String variation)
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public void registerProperties(ContextKey contextKey, Properties properties)
public void registerResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException
public void setApplicationRulesRepository(IRepository<Properties> applicationRulesRepository)
public void unregisterProperties(ContextKey contextKey)
```
***
**org.jaffa.loader.config.ApplicationRulesXmlLoadTest**

```
public void testApplicationRulesRegistration()
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testXmlLoad()
```
***
**org.jaffa.loader.navigation.NavigationManager**

```
public GlobalMenu getGlobalMenu()
public IRepository<GlobalMenu> getNavigationRepository()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public void registerGlobalMenu(ContextKey contextKey, GlobalMenu globalMenu)
public void registerResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException
public void setNavigationRepository(IRepository<GlobalMenu> navigationRepository)
public void unregisterGlobalMenu(ContextKey contextKey)
```
***
**org.jaffa.loader.navigation.NavigationXmlLoadTest**

```
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testNavigationRegistration()
public void testXmlLoad()
```
***
**org.jaffa.loader.policy.BusinessFunctionManager**

```
public List<BusinessFunction> getAllBusinessFunctions()
public List<String> getAllBusinessFunctionsStrings()
public BusinessFunction getBusinessFunction(String businessFunctionName)
public IRepository<BusinessFunction> getBusinessFunctionRepository()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public void registerBusinessFunction(ContextKey contextKey, BusinessFunction businessFunction)
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void setBusinessFunctionRepository(IRepository<BusinessFunction> businessFunctionRepository)
public void unregisterBusinessFunction(ContextKey contextKey)
```
***
**org.jaffa.loader.policy.BusinessFunctionXmlLoadTest**

```
public void testGetAllBusinessFunctions()
public void testGetBusinessFunctionRegistration()
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testXmlLoad()
```
***
**org.jaffa.loader.policy.RoleManager**

```
public Map<String, List<String>> buildRoleMap()
public List<Role> getAllRoles()
public IRepository<?> getRepositoryByName(String name)
public Set getRepositoryNames()
public String getResourceFileName()
public Role getRole(String roleName)
public IRepository<Role> getRoleRepository()
public Roles getRoles()
public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException
public void registerRole(ContextKey contextKey, Role role)
public void setRoleRepository(IRepository<Role> roleRepository)
public void unregisterRole(ContextKey contextKey)
```
***
**org.jaffa.loader.policy.RoleXmlLoadTest**

```
public void testBuildRoleMap()
public void testGetRepositoryByName() throws Exception
public void testGetRepositoryNames()
public void testRoleRegistration()
public void testXmlLoad()
```
***
**org.jaffa.metadata.BooleanFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public String getLayout()
public String getPattern()
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.metadata.CurrencyFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public static String getCurrencyFormat()
public Integer getFracSize()
public Integer getIntSize()
public String getLayout()
public Currency getMaxValue()
public Currency getMinValue()
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.metadata.DateOnlyFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public static String getDateOnlyFormat()
public static String[] getDateOnlyParse()
public String getLayout()
public DateOnly getMaxValue()
public DateOnly getMinValue()
public int getWidth()
public int hashCode()
public DateTimeFieldMetaData toDateTimeFieldMetaData()
public String toString()
```
***
**org.jaffa.metadata.DateTimeFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public static String getDateTimeFormat()
public static String[] getDateTimeParse()
public String getLayout()
public DateTime getMaxValue()
public DateTime getMinValue()
public int getWidth()
public int hashCode()
public DateOnlyFieldMetaData toDateOnlyFieldMetaData()
public String toString()
```
***
**org.jaffa.metadata.DecimalFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public static String getDecimalFormat()
public Integer getFracSize()
public Integer getIntSize()
public String getLayout()
public Double getMaxValue()
public Double getMinValue()
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.metadata.DomainMetaDataCheck**

```
public Object[] getPublicStaticFieldValues(Field[] fields)
public static void main(String[] args)
public void performChecks() throws DomainMetaDataCheckException
```
***
**org.jaffa.metadata.DomainMetaDataCheckException**

No public methods.
***
**org.jaffa.metadata.DomainMetaDataHelper**

```
public static String[] getAttributes(Map fieldMap)
public static FieldMetaData[] getFieldMetaData(Map fieldMap)
public static FieldMetaData getFieldMetaData(Map fieldMap, String fieldName)
```
***
**org.jaffa.metadata.FieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public String getDataType()
public String getLabelToken()
public String getName()
public abstract int getWidth()
public int hashCode()
public Boolean isMandatory()
public String toString()
```
***
**org.jaffa.metadata.IntegerFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public Integer getIntSize()
public static String getIntegerFormat()
public String getLayout()
public Long getMaxValue()
public Long getMinValue()
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData**

```
public Properties findInfo(String className, String propertyName, Object obj, String ruleName)
public String format(Object property)
public String getAnnotation()
public Properties getAuditInfo()
public String getCaseType()
public String getClientRule()
public String getCommentStyle()
public Properties getFlexInfo()
public Properties getForeignKeyInfo()
public Properties getHyperlinkInfo()
public Map<String, String> getInListValues()
public String getLabel()
public String getLayout()
public Integer getMaxFracLength()
public Integer getMaxLength()
public Object getMaxValue()
public Integer getMinLength()
public Object getMinValue()
public String[] getPattern()
public Properties getPropertyInfo()
public Class getPropertyType()
public Map<String, Object> getServiceInfo()
public boolean isHidden()
public boolean isMandatory()
public boolean isReadOnly()
public String toString()
```
***
**org.jaffa.metadata.RawFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.metadata.StringFieldMetaData**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public String getCaseType()
public Integer getLength()
public Integer getMinLength()
public String getPattern()
public int getWidth()
public int hashCode()
public String toString()
```
***
**org.jaffa.middleware.CreateServiceException**

No public methods.
***
**org.jaffa.middleware.Factory**

```
public static Object createObject(Class interfaceClass) throws CreateServiceException
```
***
**org.jaffa.persistence.AtomicCriteria**

```
public boolean getOrLogic()
```
***
**org.jaffa.persistence.AtomicCriteriaEntry**

```
public AtomicCriteria getEntry()
public String toString()
```
***
**org.jaffa.persistence.Criteria**

```
public void addAggregate(Criteria criteria)
public void addAtomic(AtomicCriteria atomicCriteria)
public void addCriteria(String name, Object value)
public void addCriteria(String name, int operator, Object value)
public void addCriteria(String name, int operator)
public void addDualCriteria(String name, String name2)
public void addDualCriteria(String name, int operator, String name2)
public void addFunction(int function, String name, String id)
public void addFunction(int function, boolean distinct, String name, String id)
public void addGroupBy(String name, String id)
public void addInnerCriteria(String name, String name2)
public void addInnerCriteria(String name, int operator, String name2)
public void addOrAtomic(AtomicCriteria atomicCriteria)
public void addOrCriteria(String name, Object value)
public void addOrCriteria(String name, int operator, Object value)
public void addOrCriteria(String name, int operator)
public void addOrderBy(String orderByElement)
public void addOrderBy(String orderByElement, int ordering)
public void clearInnerCriteria()
public void clearOrderBy()
public Collection getAggregates()
public Collection getCriteriaEntries()
public Integer getFirstResult()
public Collection getFunctionEntries()
public Collection getGroupBys()
public Collection getInners()
public int getLocking()
public Integer getMaxResults()
public Collection getOrderBys()
public String getTable()
public UOW getUow()
public void setFirstResult(Integer firstResult)
public void setLocking(int locking)
public void setMaxResults(Integer maxResults)
public void setTable(String table)
public void setUow(UOW uow)
public String toString()
```
***
**org.jaffa.persistence.CriteriaEntry**

```
public boolean getDual()
public int getLogic()
public String getName()
public int getOperator()
public String getTableName()
public Object getValue()
public boolean isLogicAND()
public String toString()
```
***
**org.jaffa.persistence.FunctionEntry**

```
public int getFunction()
public String getId()
public String getName()
public boolean isDistinct()
public String toString()
```
***
**org.jaffa.persistence.GroupBy**

```
public String getId()
public String getName()
public String toString()
```
***
**org.jaffa.persistence.ILifecycleHandler**

No public methods.
***
**org.jaffa.persistence.IPersistent**

No public methods.
***
**org.jaffa.persistence.InnerAtomicCriteria**

```
public void addCriteria(String tableName, String name, Object value)
public void addCriteria(String tableName, String name, int operator, Object value)
public void addCriteria(String tableName, String name, int operator)
public void addOrCriteria(String tableName, String name, Object value)
public void addOrCriteria(String tableName, String name, int operator, Object value)
public void addOrCriteria(String tableName, String name, int operator)
```
***
**org.jaffa.persistence.OrderBy**

```
public String getOrderByElement()
public int getOrdering()
public String toString()
```
***
**org.jaffa.persistence.Persistent**

```
public void appendLifecycleHandlers(List<ILifecycleHandler> handlers)
public Object clone() throws CloneNotSupportedException
public void doValidate() throws ApplicationExceptions, FrameworkException
public boolean equals(Object obj)
public FlexBean getFlexBean() throws FrameworkException, ApplicationExceptions
public List<ILifecycleHandler> getLifecycleHandlers()
public int getLocking()
public Map<String, Object> getModifiedFields()
public UOW getUOW()
public int hashCode()
public boolean isDatabaseOccurence()
public boolean isLocked()
public boolean isModified()
public boolean isModified(String fieldName)
public boolean isQueued()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void postAdd() throws PostAddFailedException
public void postDelete() throws PostDeleteFailedException
public void postLoad() throws PostLoadFailedException
public void postUpdate() throws PostUpdateFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void prependLifecycleHandlers(List<ILifecycleHandler> handlers)
public Object returnInitialValue(String fieldName)
public void setDatabaseOccurence(boolean databaseOccurence)
public void setFlexBean(FlexBean flexBean) throws ApplicationExceptions, FrameworkException
public void setLocked(boolean locked)
public void setLocking(int locking)
public void setModified(boolean modified)
public void setQueued(boolean queued)
public void setTargetBean(ILifecycleHandler targetBean)
public void setUOW(UOW uow)
public void setValidator(Validator<Persistent> validator)
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.persistence.PersistentConfigTest**

```
public static void beforeClass()
public void testFallBackToPersistent() throws PreAddFailedException, NoSuchFieldException, IllegalAccessException
```
***
**org.jaffa.persistence.UOW**

```
public void acquireLock(Object object) throws AlreadyLockedObjectException
public void add(Object object) throws AddFailedException
public String addMessage(Object payload) throws FrameworkException, ApplicationExceptions
public String addMessage(Object payload, String[] dependencies) throws FrameworkException, ApplicationExceptions
public String addMessage(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public String addOutboundMessage(Object payload) throws FrameworkException, ApplicationExceptions
public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void addSpecial(Object object) throws AddFailedException
public Object addTransaction(Object payload) throws FrameworkException, ApplicationExceptions
public Object addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions
public void close()
public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException
public void delete(Object object) throws DeleteFailedException
public void deleteSpecial(Object object) throws DeleteFailedException
public void flush() throws UOWException
public Class getActualPersistentClass(Object persistentObject)
public boolean isActive()
public IPersistent newPersistentInstance(Class persistentClass)
public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException
public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin)
public void rollback() throws RollbackFailedException
public void update(Object object) throws UpdateFailedException
public void updateSpecial(Object object) throws UpdateFailedException
```
***
**org.jaffa.persistence.blackboxtests.AddTest**

```
public static Test suite()
public void testAddDateTime()
public void testAddDateTimeUsingProxy()
public void testAddDecimal()
public void testAddDecimalUsingProxy()
public void testAddDuplicates()
public void testAddDuplicatesUsingProxy()
public void testAddLong()
public void testAddLongUsingProxy()
public void testAddWithAutoKey()
public void testAddWithAutoKeyUsingProxy()
public void testCheckRollbackAfterAdd()
public void testCheckRollbackAfterAddUsingProxy()
public void testCreateCategoryOfInstrument()
public void testCreateCategoryOfInstrumentUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.CharFieldTest**

```
public static Test suite()
public void testAdd()
public void testDelete()
public void testEndsWithQuery()
public void testJoinQueryBetweenCharAndVarchar()
public void testNotNullQuery()
public void testNullQuery()
public void testUpdate()
```
***
**org.jaffa.persistence.blackboxtests.ConcurrencyTest**

```
public static Test suite()
public void testConcurrentUowCreation()
public void testDirtyRead()
public void testOptimisticLocking()
public void testParanoidLocking()
public void testPessimisticLocking()
public void testReadOnlyLocking()
```
***
**org.jaffa.persistence.blackboxtests.ConnectionPoolTest**

```
public void testConnectionPoolRobustness()
public void testConnectionsExhausted()
```
***
**org.jaffa.persistence.blackboxtests.DeleteTest**

```
public static Test suite()
public void testCheckRollbackAfterDelete()
public void testCheckRollbackAfterDeleteUsingProxy()
public void testDeleteCondition()
public void testDeleteConditionUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.DynamicRulesTest**

```
public static Test suite()
public void testBooleanFieldValidator()
public void testCurrencyFieldValidator()
public void testDateOnlyFieldValidator()
public void testDateTimeFieldValidator()
public void testDecimalFieldValidator()
public void testDomainObjectValidation()
public void testExtendsFieldValidation()
public void testForeignKeyFieldValidator()
public void testGenericForeignKeyFieldValidator()
public void testInListFieldValidator()
public void testIntegerFieldValidator()
public void testMandatoryFieldValidator()
public void testOverridesFieldValidation()
public void testStringFieldValidator()
```
***
**org.jaffa.persistence.blackboxtests.EqualsTest**

```
public static Test suite()
public void testEquals()
public void testHashCode()
```
***
**org.jaffa.persistence.blackboxtests.FlushTest**

```
public static Test suite()
public void testFlush()
```
***
**org.jaffa.persistence.blackboxtests.FunctionTest**

```
public static Test suite()
public void testAvg()
public void testCount()
public void testCountWithJoin()
public void testCountWithWhereClause()
public void testDistinctTwoFieldsUsingGroupBy()
public void testDistinctUsingGroupBy()
public void testGroupBy()
public void testMax()
public void testMin()
public void testObtainCurrentDateTime()
public void testSum()
```
***
**org.jaffa.persistence.blackboxtests.HitlistTest**

```
public static Test suite()
public void testHitlist()
public void testHitlistUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.LobTest**

```
public static Test suite()
public void testAddClobAndBlob()
public void testAddClobAndBlobUsingProxy()
public void testUpdateBlob()
public void testUpdateBlobUsingProxy()
public void testUpdateClob()
public void testUpdateClobUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.ObjectCacheTest**

```
public static Test suite()
public void testCaching()
public void testDisableCaching()
```
***
**org.jaffa.persistence.blackboxtests.PagingTest**

```
public static Test suite()
public void testPaging()
```
***
**org.jaffa.persistence.blackboxtests.PerformanceTest**

```
public static Test suite()
public void testRead1000()
public void testRead10000()
public void testWrite1000()
public void testWrite10000()
```
***
**org.jaffa.persistence.blackboxtests.QueryTest**

```
public static Test suite()
public void testAtomicInnerJoinQuery()
public void testBasicQuery()
public void testBasicQueryUsingProxy()
public void testDateTimeQuery()
public void testDateTimeQueryUsingProxy()
public void testDualFieldQuery()
public void testDualFieldQueryUsingProxy()
public void testInnerJoinQuery()
public void testInnerJoinQueryUsingProxy()
public void testLongQuery()
public void testLongQueryUsingProxy()
public void testNestedAtomicInnerJoinQuery()
public void testNestedInnerJoinQuery()
public void testNestedInnerJoinQueryUsingProxy()
public void testNotBeginsWithQuery()
public void testNotBeginsWithQueryUsingProxy()
public void testNotEndsWithQuery()
public void testNotEndsWithQueryUsingProxy()
public void testNotLikeWithQuery()
public void testNotLikeWithQueryUsingProxy()
public void testOrAndQuery()
public void testOrAndQueryUsingProxy()
public void testQueryWithEscapeCharacters()
public void testQueryWithEscapeCharactersUsingProxy()
public void testTwoAtomicInnerJoinQuery()
public void testTwoInnerJoinsQuery()
public void testTwoInnerJoinsQueryUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.RawTest**

```
public static Test suite()
public void testAddRawAndLongRaw()
public void testAddRawAndLongRawUsingProxy()
public void testUpdateRawAndLongRaw()
public void testUpdateRawAndLongRawUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.RelationshipTest**

```
public static Test suite()
public void testCascadeDeleteOfManyObject()
public void testCascadeDeleteOfOptionalOneObject()
public void testMandatoryOneObjectUntouchedOnDelete()
public void testNonValidationOfManyObject()
public void testNonValidationOfOptionalOneObject()
public void testOneObjectUntouchedOnDelete()
public void testRestrictConstraintOfManyObject()
public void testRestrictConstraintOfOptionalOneObject()
public void testValidationOfMandatoryOneObject()
public void testValidationOfOneObject()
```
***
**org.jaffa.persistence.blackboxtests.SpecialAddUpdateDeleteTest**

```
public static Test suite()
public void testAdd()
public void testDelete()
public void testUpdate()
```
***
**org.jaffa.persistence.blackboxtests.StoredProcedureTest**

```
public static Test suite()
public void testStoredProcedureReturningObjects()
public void testVoucherStoredProcedure()
```
***
**org.jaffa.persistence.blackboxtests.UowCreationThread**

```
public void run()
```
***
**org.jaffa.persistence.blackboxtests.UpdateTest**

```
public static Test suite()
public void testCheckRollbackAfterUpdate()
public void testCheckRollbackAfterUpdateUsingProxy()
public void testUpdateCategoryOfInstrument()
public void testUpdateCategoryOfInstrumentUsingProxy()
public void testUpdateDateTime()
public void testUpdateDateTimeUsingProxy()
public void testUpdateDecimal()
public void testUpdateDecimalUsingProxy()
public void testUpdateLong()
public void testUpdateLongUsingProxy()
public void testUpdateUsingQuote()
public void testUpdateUsingQuoteUsingProxy()
```
***
**org.jaffa.persistence.blackboxtests.Wrapper**

No public methods.
***
**org.jaffa.persistence.blackboxtests.logging.DemoLogger**

```
public void add(IPersistent obj) throws ApplicationExceptions, FrameworkException
public void clearLog() throws ApplicationExceptions, FrameworkException
public void delete(IPersistent obj) throws ApplicationExceptions, FrameworkException
public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException
public void update(IPersistent obj) throws ApplicationExceptions, FrameworkException
public void writeLog() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.persistence.blackboxtests.logging.DomainXmlReader**

```
public Iterator iterator() throws XMLStreamException
```
***
**org.jaffa.persistence.blackboxtests.logging.DomainXmlTest**

```
public static Test suite()
public void testEscapedDomainXml() throws Exception
public void testUnEscapedDomainXml() throws Exception
```
***
**org.jaffa.persistence.blackboxtests.logging.DomainXmlWriter**

```
public void addObject(IPersistent obj)
public void write(Writer w, boolean escapeDomainXml) throws IOException
```
***
**org.jaffa.persistence.blackboxtests.logging.PersistenceLoggingTest**

```
public static Test suite()
public void testLogging() throws Exception
```
***
**org.jaffa.persistence.blackboxtests.logging.XmlIterator**

```
public boolean hasNext()
public Object next()
public void remove()
```
***
**org.jaffa.persistence.domainobjects.Asset**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.Long assetTk) throws FrameworkException
public static Asset findByPK(UOW uow, java.lang.Long assetTk) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.Long assetTk)
public java.lang.String getAssetId()
public java.lang.Long getAssetTk()
public java.lang.String getCondition()
public Condition getConditionObject() throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime getCreatedDatetime()
public java.lang.String getCustodian()
public java.lang.String getKeyRef()
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public java.lang.Long getQty()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setAssetId(java.lang.String assetId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setAssetTk(java.lang.Long assetTk) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCondition(java.lang.String condition) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCustodian(java.lang.String custodian) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKeyRef(java.lang.String keyRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPart(java.lang.String part) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setQty(java.lang.Long qty) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateAssetId(java.lang.String assetId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateAssetTk(java.lang.Long assetTk) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCondition(java.lang.String condition) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCustodian(java.lang.String custodian) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKeyRef(java.lang.String keyRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePart(java.lang.String part) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateQty(java.lang.Long qty) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateAssetId(java.lang.String assetId) throws ValidationException, FrameworkException
public java.lang.Long validateAssetTk(java.lang.Long assetTk) throws ValidationException, FrameworkException
public java.lang.String validateCondition(java.lang.String condition) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, FrameworkException
public java.lang.String validateCustodian(java.lang.String custodian) throws ValidationException, FrameworkException
public java.lang.String validateKeyRef(java.lang.String keyRef) throws ValidationException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
public java.lang.Long validateQty(java.lang.Long qty) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.AssetMeta**

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
**org.jaffa.persistence.domainobjects.CategoryOfInstrument**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String categoryInstrument) throws FrameworkException
public static CategoryOfInstrument findByPK(UOW uow, java.lang.String categoryInstrument) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String categoryInstrument)
public Criteria findPartCriteria()
public java.lang.Boolean getCalculateMtbf()
public java.lang.String getCategoryInstrument()
public java.lang.String getDescription()
public Part[] getPartArray() throws FrameworkException
public java.lang.Boolean getSupportEquip()
public Part newPartObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCalculateMtbf(java.lang.Boolean calculateMtbf) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSupportEquip(java.lang.Boolean supportEquip) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCalculateMtbf(java.lang.Boolean calculateMtbf) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSupportEquip(java.lang.Boolean supportEquip) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.Boolean validateCalculateMtbf(java.lang.Boolean calculateMtbf) throws ValidationException, FrameworkException
public java.lang.String validateCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.Boolean validateSupportEquip(java.lang.Boolean supportEquip) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.CategoryOfInstrumentMeta**

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
**org.jaffa.persistence.domainobjects.Condition**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String condition) throws FrameworkException
public Criteria findAssetCriteria()
public static Condition findByPK(UOW uow, java.lang.String condition) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String condition)
public Criteria findItemCriteria()
public Asset[] getAssetArray() throws FrameworkException
public java.lang.String getCondition()
public java.lang.String getDescription()
public Item[] getItemArray() throws FrameworkException
public Asset newAssetObject() throws ValidationException, FrameworkException
public Item newItemObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCondition(java.lang.String condition) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCondition(java.lang.String condition) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCondition(java.lang.String condition) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.ConditionMeta**

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
**org.jaffa.persistence.domainobjects.IAsset**

```
public java.lang.String getAssetId()
public java.lang.Long getAssetTk()
public java.lang.String getCondition()
public org.jaffa.datatypes.DateTime getCreatedDatetime()
public java.lang.String getCustodian()
public java.lang.String getKeyRef()
public java.lang.String getPart()
public java.lang.Long getQty()
public void setAssetId(java.lang.String assetId)
public void setAssetTk(java.lang.Long assetTk)
public void setCondition(java.lang.String condition)
public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime)
public void setCustodian(java.lang.String custodian)
public void setKeyRef(java.lang.String keyRef)
public void setPart(java.lang.String part)
public void setQty(java.lang.Long qty)
```
***
**org.jaffa.persistence.domainobjects.ICategoryOfInstrument**

```
public java.lang.Boolean getCalculateMtbf()
public java.lang.String getCategoryInstrument()
public java.lang.String getDescription()
public java.lang.Boolean getSupportEquip()
public void setCalculateMtbf(java.lang.Boolean calculateMtbf)
public void setCategoryInstrument(java.lang.String categoryInstrument)
public void setDescription(java.lang.String description)
public void setSupportEquip(java.lang.Boolean supportEquip)
```
***
**org.jaffa.persistence.domainobjects.ICondition**

```
public java.lang.String getCondition()
public java.lang.String getDescription()
public void setCondition(java.lang.String condition)
public void setDescription(java.lang.String description)
```
***
**org.jaffa.persistence.domainobjects.IItem**

```
public java.lang.String getCondition()
public org.jaffa.datatypes.DateTime getCreatedDatetime()
public java.lang.String getItemId()
public java.lang.String getKeyRef()
public java.lang.String getPart()
public java.lang.Double getPrice()
public java.lang.String getPrime()
public java.lang.Long getQty()
public java.lang.String getReceivedItemId()
public java.lang.String getSc()
public java.lang.String getStatus1()
public java.lang.String getStatus2()
public java.lang.String getStatus3()
public void setCondition(java.lang.String condition)
public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime)
public void setItemId(java.lang.String itemId)
public void setKeyRef(java.lang.String keyRef)
public void setPart(java.lang.String part)
public void setPrice(java.lang.Double price)
public void setPrime(java.lang.String prime)
public void setQty(java.lang.Long qty)
public void setReceivedItemId(java.lang.String receivedItemId)
public void setSc(java.lang.String sc)
public void setStatus1(java.lang.String status1)
public void setStatus2(java.lang.String status2)
public void setStatus3(java.lang.String status3)
```
***
**org.jaffa.persistence.domainobjects.IPart**

```
public java.lang.String getCategoryInstrument()
public java.lang.String getNoun()
public java.lang.String getPart()
public void setCategoryInstrument(java.lang.String categoryInstrument)
public void setNoun(java.lang.String noun)
public void setPart(java.lang.String part)
```
***
**org.jaffa.persistence.domainobjects.IPartPicture**

```
public java.lang.String getPart()
public byte[] getPicture()
public byte[] getSmallPicture()
public void setPart(java.lang.String part)
public void setPicture(byte[] picture)
public void setSmallPicture(byte[] smallPicture)
```
***
**org.jaffa.persistence.domainobjects.IPartRemarks**

```
public java.lang.String getPart()
public java.lang.String getRemarks()
public void setPart(java.lang.String part)
public void setRemarks(java.lang.String remarks)
```
***
**org.jaffa.persistence.domainobjects.IPartRemarksPicture**

```
public java.lang.String getPart()
public byte[] getPicture()
public java.lang.String getRemarks()
public void setPart(java.lang.String part)
public void setPicture(byte[] picture)
public void setRemarks(java.lang.String remarks)
```
***
**org.jaffa.persistence.domainobjects.Item**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String itemId) throws FrameworkException
public static Item findByPK(UOW uow, java.lang.String itemId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String itemId)
public java.lang.String getCondition()
public Condition getConditionObject() throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime getCreatedDatetime()
public java.lang.String getItemId()
public java.lang.String getKeyRef()
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public java.lang.Double getPrice()
public java.lang.String getPrime()
public java.lang.Long getQty()
public java.lang.String getReceivedItemId()
public java.lang.String getSc()
public java.lang.String getStatus1()
public java.lang.String getStatus2()
public java.lang.String getStatus3()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preDelete() throws PreDeleteFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCondition(java.lang.String condition) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setItemId(java.lang.String itemId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setKeyRef(java.lang.String keyRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPart(java.lang.String part) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPrice(java.lang.Double price) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPrime(java.lang.String prime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setQty(java.lang.Long qty) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setReceivedItemId(java.lang.String receivedItemId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSc(java.lang.String sc) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus1(java.lang.String status1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus2(java.lang.String status2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus3(java.lang.String status3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCondition(java.lang.String condition) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateItemId(java.lang.String itemId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateKeyRef(java.lang.String keyRef) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePart(java.lang.String part) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePrice(java.lang.Double price) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePrime(java.lang.String prime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateQty(java.lang.Long qty) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateReceivedItemId(java.lang.String receivedItemId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSc(java.lang.String sc) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus1(java.lang.String status1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus2(java.lang.String status2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus3(java.lang.String status3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCondition(java.lang.String condition) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime) throws ValidationException, FrameworkException
public java.lang.String validateItemId(java.lang.String itemId) throws ValidationException, FrameworkException
public java.lang.String validateKeyRef(java.lang.String keyRef) throws ValidationException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
public java.lang.Double validatePrice(java.lang.Double price) throws ValidationException, FrameworkException
public java.lang.String validatePrime(java.lang.String prime) throws ValidationException, FrameworkException
public java.lang.Long validateQty(java.lang.Long qty) throws ValidationException, FrameworkException
public java.lang.String validateReceivedItemId(java.lang.String receivedItemId) throws ValidationException, FrameworkException
public java.lang.String validateSc(java.lang.String sc) throws ValidationException, FrameworkException
public java.lang.String validateStatus1(java.lang.String status1) throws ValidationException, FrameworkException
public java.lang.String validateStatus2(java.lang.String status2) throws ValidationException, FrameworkException
public java.lang.String validateStatus3(java.lang.String status3) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.ItemMeta**

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
**org.jaffa.persistence.domainobjects.ItemStoredProcedure**

```
public java.lang.String getItemId()
public Item[] getItems()
public int[] getParamDirections()
public String[] getParamSqlTypes()
public String[] getParameters()
public String prepareCall()
public void setItemId(java.lang.String itemId)
public void setItems(Item[] items)
public String toString()
public void updateItemId(java.lang.String itemId) throws ValidationException
public void validateItemId(java.lang.String itemId) throws ValidationException
```
***
**org.jaffa.persistence.domainobjects.ItemStoredProcedureMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.persistence.domainobjects.Part**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException
public Criteria findAssetCriteria()
public static Part findByPK(UOW uow, java.lang.String part) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String part)
public Criteria findItemCriteria()
public Asset[] getAssetArray() throws FrameworkException
public java.lang.String getCategoryInstrument()
public CategoryOfInstrument getCategoryOfInstrumentObject() throws ValidationException, FrameworkException
public Item[] getItemArray() throws FrameworkException
public java.lang.String getNoun()
public java.lang.String getPart()
public PartAdditional getPartAdditionalObject() throws FrameworkException
public PartPicture getPartPictureObject() throws FrameworkException
public PartRemarks getPartRemarksObject() throws FrameworkException
public PartRemarksPicture getPartRemarksPictureObject() throws FrameworkException
public Asset newAssetObject() throws ValidationException, FrameworkException
public Item newItemObject() throws ValidationException, FrameworkException
public PartAdditional newPartAdditionalObject() throws ValidationException, FrameworkException
public PartPicture newPartPictureObject() throws ValidationException, FrameworkException
public PartRemarks newPartRemarksObject() throws ValidationException, FrameworkException
public PartRemarksPicture newPartRemarksPictureObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setNoun(java.lang.String noun) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateNoun(java.lang.String noun) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCategoryInstrument(java.lang.String categoryInstrument) throws ValidationException, FrameworkException
public java.lang.String validateNoun(java.lang.String noun) throws ValidationException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.PartAdditional**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException
public static PartAdditional findByPK(UOW uow, java.lang.String part) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String part)
public java.lang.String getField1()
public java.lang.String getField2()
public java.lang.String getField3()
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setField1(java.lang.String field1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setField2(java.lang.String field2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setField3(java.lang.String field3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateField1(java.lang.String field1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateField2(java.lang.String field2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateField3(java.lang.String field3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateField1(java.lang.String field1) throws ValidationException, FrameworkException
public java.lang.String validateField2(java.lang.String field2) throws ValidationException, FrameworkException
public java.lang.String validateField3(java.lang.String field3) throws ValidationException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.PartAdditionalMeta**

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
**org.jaffa.persistence.domainobjects.PartMeta**

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
**org.jaffa.persistence.domainobjects.PartPicture**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException
public static PartPicture findByPK(UOW uow, java.lang.String part) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String part)
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public byte[] getPicture()
public byte[] getSmallPicture()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setPart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPicture(byte[] picture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSmallPicture(byte[] smallPicture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updatePart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePicture(byte[] picture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSmallPicture(byte[] smallPicture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
public byte[] validatePicture(byte[] picture) throws ValidationException, FrameworkException
public byte[] validateSmallPicture(byte[] smallPicture) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.PartPictureMeta**

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
**org.jaffa.persistence.domainobjects.PartRemarks**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException
public static PartRemarks findByPK(UOW uow, java.lang.String part) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String part)
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public java.lang.String getRemarks()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setPart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updatePart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.PartRemarksMeta**

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
**org.jaffa.persistence.domainobjects.PartRemarksPicture**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException
public static PartRemarksPicture findByPK(UOW uow, java.lang.String part) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String part)
public java.lang.String getPart()
public Part getPartObject() throws ValidationException, FrameworkException
public byte[] getPicture()
public java.lang.String getRemarks()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setPart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPicture(byte[] picture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updatePart(java.lang.String part) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePicture(byte[] picture) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validatePart(java.lang.String part) throws ValidationException, FrameworkException
public byte[] validatePicture(byte[] picture) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.PartRemarksPictureMeta**

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
**org.jaffa.persistence.domainobjects.ValidFieldValue**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException
public static ValidFieldValue findByPK(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue)
public java.lang.String getDescription()
public java.lang.String getFieldName()
public java.lang.String getLegalValue()
public java.lang.String getRemarks()
public java.lang.String getTableName()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLegalValue(java.lang.String legalValue) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTableName(java.lang.String tableName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldName(java.lang.String fieldName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLegalValue(java.lang.String legalValue) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTableName(java.lang.String tableName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateFieldName(java.lang.String fieldName) throws ValidationException, FrameworkException
public java.lang.String validateLegalValue(java.lang.String legalValue) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
public java.lang.String validateTableName(java.lang.String tableName) throws ValidationException, FrameworkException
```
***
**org.jaffa.persistence.domainobjects.ValidFieldValueMeta**

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
**org.jaffa.persistence.domainobjects.VoucherStoredProcedure**

```
public java.lang.Long getLength()
public int[] getParamDirections()
public String[] getParamSqlTypes()
public String[] getParameters()
public java.lang.String getPrefix()
public java.lang.String getVoucher()
public String prepareCall()
public void setLength(java.lang.Long length)
public void setPrefix(java.lang.String prefix)
public void setVoucher(java.lang.String voucher)
public String toString()
public void updateLength(java.lang.Long length) throws ValidationException
public void updatePrefix(java.lang.String prefix) throws ValidationException
public void updateVoucher(java.lang.String voucher) throws ValidationException
public void validateLength(java.lang.Long length) throws ValidationException
public void validatePrefix(java.lang.String prefix) throws ValidationException
public void validateVoucher(java.lang.String voucher) throws ValidationException
```
***
**org.jaffa.persistence.domainobjects.VoucherStoredProcedureMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.persistence.engines.IJdbcPersistenceEngine**

```
public DataSource getExistingDataSource()
public void initialize(UOW uow, DataSource dataSource) throws FrameworkException
```
***
**org.jaffa.persistence.engines.IMessagingEngine**

No public methods.
***
**org.jaffa.persistence.engines.IPersistenceEngine**

```
public void acquireLock(IPersistent object) throws AlreadyLockedObjectException
public void add(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException
public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void close()
public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException
public void delete(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException
public void flush() throws UOWException
public Class getActualPersistentClass(Object persistentObject)
public Collection getDeletes()
public void initialize(UOW uow) throws FrameworkException
public IPersistent newPersistentInstance(Class persistentClass)
public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException
public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin)
public void rollback() throws RollbackFailedException
public void update(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException
```
***
**org.jaffa.persistence.engines.IPersistenceEngineFactory**

No public methods.
***
**org.jaffa.persistence.engines.ISender**

No public methods.
***
**org.jaffa.persistence.engines.MessagingEngineFactory**

```
public static IMessagingEngine newInstance(UOW uow) throws FrameworkException, ApplicationExceptions
public static void setFactory(IMessagingEngineFactory messagingEngineFactory)
```
***
**org.jaffa.persistence.engines.PersistenceEngineFactory**

```
public static IPersistenceEngine newInstance(UOW uow) throws EngineInstantiationException
public static IPersistenceEngine newInstance() throws EngineInstantiationException
public static void setFactory(IPersistenceEngineFactory persistenceEngineFactory)
```
***
**org.jaffa.persistence.engines.jdbcengine.Engine**

```
public void acquireLock(IPersistent object) throws AlreadyLockedObjectException
public void add(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException
public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void close()
public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException
public void delete(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException
public static ApplicationExceptions extractApplicationExceptions(Throwable exception)
public void flush() throws UOWException
public Class getActualPersistentClass(Object persistentObject)
public Collection getDeletes()
public DataSource getExistingDataSource()
public void initialize(UOW uow) throws FrameworkException
public void initialize(UOW uow, DataSource dataSource) throws FrameworkException
public IPersistent newPersistentInstance(Class persistentClass)
public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException
public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin)
public void rollback() throws RollbackFailedException
public void update(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException
```
***
**org.jaffa.persistence.engines.jdbcengine.IMessagingEngineFactory**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.IStoredProcedure**

```
public int[] getParamDirections()
public String[] getParamSqlTypes()
public String[] getParameters()
public String prepareCall()
```
***
**org.jaffa.persistence.engines.jdbcengine.LockedApplicationException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.SQLApplicationException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData**

```
public void addAttribute(String attName, String type)
public void addKeyField(String attName, String type, boolean autogen)
public void addMember(String attName, String memberName)
public void addRpad(String attName, Integer rpad)
public void addSqlName(String attName, String sqlName)
public void addSqlType(String attName, String type)
public AccessibleObject getAccessor(String attributeName)
public Collection<String> getAllKeyFieldNames()
public Collection<String> getAttributes()
public String getClassName()
public AccessibleObject getMutator(String attributeName)
public Collection<String> getNonAutoKeyFieldNames()
public Integer getRpad(String attributeName)
public String getSqlName(String attributeName)
public String getSqlType(String attributeName)
public String getTable()
public String getType(String attributeName)
public URL getXmlFileUrl()
public boolean isKeyField(String attributeName)
public boolean isLockable()
public void setClassName(String classname)
public void setLockable(boolean lockable)
public void setTable(String table)
public void setXmlFileUrl(URL xmlFileUrl)
public void validate()
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService**

```
public Database getDatabase(String name)
public Map getDatabases()
public static ConfigurationService getInstance()
public ClassMetaData getMetaData(String classname)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.MappingParser**

```
public ClassMetaData getMetaData()
public void startElement(String uri, String sName, String qName, Attributes atts)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.PropertyMetaData**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.configservice.exceptions.ClassMetaDataValidationRuntimeException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.configservice.exceptions.ConfigurationServiceRuntimeException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.configservice.exceptions.InitFileNotFoundRuntimeException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.ConfLocation**

```
public List<DirLoc> getDirLoc()
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.ConnectionFactory**

```
public String getClassName()
public List<Param> getParam()
public void setClassName(String value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database**

```
public ConnectionFactory getConnectionFactory()
public String getEngine()
public HitlistSize getHitlistSize()
public JdbcSecurityPlugin getJdbcSecurityPlugin()
public String getName()
public UsePreparedStatement getUsePreparedStatement()
public void setConnectionFactory(ConnectionFactory value)
public void setEngine(String value)
public void setHitlistSize(HitlistSize value)
public void setJdbcSecurityPlugin(JdbcSecurityPlugin value)
public void setName(String value)
public void setUsePreparedStatement(UsePreparedStatement value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.DirLoc**

```
public String getDir()
public void setDir(String value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.HitlistSize**

```
public int getValue()
public void setValue(int value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Init**

```
public ConfLocation getConfLocation()
public List<Database> getDatabase()
public Preload getPreload()
public void setConfLocation(ConfLocation value)
public void setPreload(Preload value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.JdbcSecurityPlugin**

```
public String getValue()
public void setValue(String value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.ObjectFactory**

```
public ConfLocation createConfLocation()
public ConnectionFactory createConnectionFactory()
public Database createDatabase()
public DirLoc createDirLoc()
public HitlistSize createHitlistSize()
public Init createInit()
public JdbcSecurityPlugin createJdbcSecurityPlugin()
public Param createParam()
public Preload createPreload()
public PreloadClass createPreloadClass()
public UsePreparedStatement createUsePreparedStatement()
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Param**

```
public String getName()
public String getValue()
public void setName(String value)
public void setValue(String value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Preload**

```
public List<PreloadClass> getPreloadClass()
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.PreloadClass**

```
public String getName()
public void setName(String value)
```
***
**org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.UsePreparedStatement**

```
public boolean isValue()
public void setValue(boolean value)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.BoneCPDataSourceConnectionFactory**

```
public Connection createConnection() throws SQLException, IOException
public void freeConnection(Connection connection) throws SQLException, IOException
public String getDriverClass()
public synchronized Connection getInternalConnection(Connection connection)
public Integer getMaxCheckoutSeconds()
public Double getMaxConnTime()
public Integer getMaximumConnections()
public Integer getMinimumConnections()
public Integer getPartitions()
public String getPassword()
public Integer getRetryAttempts()
public String getUrl()
public String getUser()
public void setDriverClass(String driverClass)
public void setMaxCheckoutSeconds(Integer maxCheckoutSeconds)
public void setMaxConnTime(Double maxConnTime)
public void setMaximumConnections(Integer maximumConnections)
public void setMinimumConnections(Integer minimumConnections)
public void setPartitions(Integer partitions)
public void setPassword(String password)
public void setRetryAttempts(Integer retryAttempts)
public void setUrl(String url)
public void setUser(String user)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DataSource**

```
public void cacheObject(IPersistent object)
public void clearObjectCache()
public void closeStatement(final Statement statement) throws SQLException
public void commit() throws SQLException
public Collection executeQuery(String sql, ClassMetaData classMetaData, Criteria criteria, int queryTimeout, IPagingPlugin pagingPlugin) throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException
public Collection executeQuery(PreparedStatement statement, ClassMetaData classMetaData, Criteria criteria, int queryTimeout, IPagingPlugin pagingPlugin) throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException
public void executeUpdate(String sql) throws SQLException
public void executeUpdate(PreparedStatement stmt) throws SQLException
public void freeConnection()
public CallableStatement getCallableStatement(String sql) throws SQLException
public String getEngineType()
public Integer getHitlistSize()
public PreparedStatement getPreparedStatement(String sql) throws SQLException
public Statement getStatement() throws SQLException
public Boolean getUsePreparedStatement()
public Collection loadResultSet(Statement statement, ResultSet resultSet, ClassMetaData classMetaData, Criteria criteria) throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException
public IPersistent lookupObjectCache(Criteria criteria)
public void rollback() throws SQLException
public void uncacheObject(IPersistent object)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DataSourceContainer**

```
public synchronized void free()
public synchronized DataSource get()
public synchronized DataSource get(boolean createIfNeeded)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DataSourceCursor**

```
public boolean add(Object obj)
public boolean addAll(Collection collection)
public void clear() throws DataSourceCursorRuntimeException
public boolean contains(Object obj)
public boolean containsAll(Collection collection)
public boolean isEmpty()
public Iterator iterator()
public boolean remove(Object obj)
public boolean removeAll(Collection collection)
public boolean retainAll(Collection collection)
public int size()
public Object[] toArray()
public Object[] toArray(Object[] obj)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DataSourceCursorIterator**

```
public boolean hasNext() throws DataSourceCursorRuntimeException
public Object next() throws DataSourceCursorRuntimeException
public void remove()
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DataSourceFactory**

```
public static void freeDataSource(DataSource ds)
public static DataSource getDataSource(Database database) throws DataSourceCreationException
public static DataSource getDataSource(Database database, Connection connection) throws DataSourceCreationException
public static Connection getUnderlyingConnection(Connection connection) throws Exception
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DbConnectionBrokerConnectionFactory**

```
public Connection createConnection() throws SQLException, IOException
public void freeConnection(Connection connection) throws SQLException, IOException
public Integer getDebugLevel()
public String getDriverClass()
public Boolean getLogAppend()
public String getLogFileName()
public Integer getMaxCheckoutSeconds()
public Double getMaxConnTime()
public Integer getMaximumConnections()
public Integer getMinimumConnections()
public String getPassword()
public String getUrl()
public String getUser()
public void setDebugLevel(Integer debugLevel)
public void setDriverClass(String driverClass)
public void setLogAppend(Boolean logAppend)
public void setLogFileName(String logFileName)
public void setMaxCheckoutSeconds(Integer maxCheckoutSeconds)
public void setMaxConnTime(Double maxConnTime)
public void setMaximumConnections(Integer maximumConnections)
public void setMinimumConnections(Integer minimumConnections)
public void setPassword(String password)
public void setUrl(String url)
public void setUser(String user)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.DbcpDataSourceConnectionFactory**

```
public Connection createConnection() throws SQLException, IOException
public void freeConnection(Connection connection) throws SQLException, IOException
public String getDriverClass()
public Properties getDriverProperties()
public Long getMaxWait()
public Integer getMaximumConnections()
public Integer getMinimumConnections()
public String getPassword()
public Boolean getTestOnBorrow()
public Boolean getTestOnReturn()
public String getUrl()
public String getUser()
public String getValidationQuery()
public void setDriverClass(String driverClass)
public void setDriverProperties(Properties driverProperties)
public void setMaxWait(Long maxWait)
public void setMaximumConnections(Integer maximumConnections)
public void setMinimumConnections(Integer minimumConnections)
public void setPassword(String password)
public void setTestOnBorrow(Boolean testOnBorrow)
public void setTestOnReturn(Boolean testOnReturn)
public void setUrl(String url)
public void setUser(String user)
public void setValidationQuery(String validationQuery)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.IConnectionFactory**

```
public Connection createConnection() throws SQLException, IOException
public void freeConnection(Connection connection) throws SQLException, IOException
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.InternalConnectionManager**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.datasource.JndiDataSourceConnectionFactory**

```
public Connection createConnection() throws SQLException, IOException
public void freeConnection(Connection connection) throws SQLException, IOException
public String getConnectionClassName()
public Connection getInternalConnection(Connection connection) throws SQLException
public String getJndiDataSourceName()
public Long getMaxWait()
public void setConnectionClassName(String className)
public void setJndiDataSourceName(String jndiDataSourceName)
public void setMaxWait(Long maxWait)
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.OracleDriver**

```
public Connection connect(String url, Properties info) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.PersistentTransaction**

```
public void addObject(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException, IllegalPersistentStateRuntimeException
public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException
public void close() throws SQLException
public void commit() throws CommitFailedException, SQLException
public void deleteObject(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException, IllegalPersistentStateRuntimeException
public Collection getAdds()
public DataSource getDataSource()
public Collection getDeletes()
public DataSource getExistingDataSource()
public Criteria getQuery()
public Collection getUpdates()
public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin)
public void rollback() throws SQLException
public void setQuery(Criteria criteria)
public void updateObject(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException, IllegalPersistentStateRuntimeException
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.PreparedStatementProxyForDebugging**

```
public void addBatch(String sql) throws SQLException
public void addBatch() throws SQLException
public void cancel() throws SQLException
public void clearBatch() throws SQLException
public void clearParameters() throws SQLException
public void clearWarnings() throws SQLException
public void close() throws SQLException
public void closeOnCompletion() throws SQLException
public boolean execute(String sql, int[] columnIndexes) throws SQLException
public boolean execute(String sql, String[] columnNames) throws SQLException
public boolean execute(String sql) throws SQLException
public boolean execute(String sql, int autoGeneratedKeys) throws SQLException
public boolean execute() throws SQLException
public int[] executeBatch() throws SQLException
public java.sql.ResultSet executeQuery(String sql) throws SQLException
public java.sql.ResultSet executeQuery() throws SQLException
public int executeUpdate(String sql, int[] columnIndexes) throws SQLException
public int executeUpdate(String sql, String[] columnNames) throws SQLException
public int executeUpdate(String sql) throws SQLException
public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException
public int executeUpdate() throws SQLException
public java.sql.Connection getConnection() throws SQLException
public int getFetchDirection() throws SQLException
public int getFetchSize() throws SQLException
public java.sql.ResultSet getGeneratedKeys() throws SQLException
public int getMaxFieldSize() throws SQLException
public int getMaxRows() throws SQLException
public java.sql.ResultSetMetaData getMetaData() throws SQLException
public boolean getMoreResults(int current) throws SQLException
public boolean getMoreResults() throws SQLException
public java.sql.ParameterMetaData getParameterMetaData() throws SQLException
public int getQueryTimeout() throws SQLException
public java.sql.ResultSet getResultSet() throws SQLException
public int getResultSetConcurrency() throws SQLException
public int getResultSetHoldability() throws SQLException
public int getResultSetType() throws SQLException
public int getUpdateCount() throws SQLException
public java.sql.SQLWarning getWarnings() throws SQLException
public boolean isCloseOnCompletion() throws SQLException
public boolean isClosed() throws SQLException
public boolean isPoolable() throws SQLException
public boolean isWrapperFor(java.lang.Class<?> iface) throws java.sql.SQLException
public void setArray(int i, java.sql.Array x) throws SQLException
public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException
public void setAsciiStream(int parameterIndex, java.io.InputStream x, long length) throws SQLException
public void setAsciiStream(int parameterIndex, java.io.InputStream x) throws SQLException
public void setBigDecimal(int parameterIndex, java.math.BigDecimal x) throws SQLException
public void setBinaryStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException
public void setBinaryStream(int parameterIndex, java.io.InputStream x, long length) throws SQLException
public void setBinaryStream(int parameterIndex, java.io.InputStream x) throws SQLException
public void setBlob(int i, java.sql.Blob x) throws SQLException
public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException
public void setBoolean(int parameterIndex, boolean x) throws SQLException
public void setByte(int parameterIndex, byte x) throws SQLException
public void setBytes(int parameterIndex, byte[] x) throws SQLException
public void setCharacterStream(int parameterIndex, java.io.Reader reader, int length) throws SQLException
public void setCharacterStream(int parameterIndex, java.io.Reader reader, long length) throws SQLException
public void setCharacterStream(int parameterIndex, java.io.Reader reader) throws SQLException
public void setClob(int i, java.sql.Clob x) throws SQLException
public void setClob(int parameterIndex, Reader reader, long length) throws SQLException
public void setClob(int parameterIndex, Reader reader) throws SQLException
public void setCursorName(String name) throws SQLException
public void setDate(int parameterIndex, java.sql.Date x, java.util.Calendar cal) throws SQLException
public void setDate(int parameterIndex, java.sql.Date x) throws SQLException
public void setDouble(int parameterIndex, double x) throws SQLException
public void setEscapeProcessing(boolean enable) throws SQLException
public void setFetchDirection(int direction) throws SQLException
public void setFetchSize(int rows) throws SQLException
public void setFloat(int parameterIndex, float x) throws SQLException
public void setInt(int parameterIndex, int x) throws SQLException
public void setLong(int parameterIndex, long x) throws SQLException
public void setMaxFieldSize(int max) throws SQLException
public void setMaxRows(int max) throws SQLException
public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException
public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException
public void setNClob(int parameterIndex, NClob value) throws SQLException
public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException
public void setNClob(int parameterIndex, Reader reader) throws SQLException
public void setNString(int parameterIndex, String value) throws SQLException
public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException
public void setNull(int parameterIndex, int sqlType) throws SQLException
public void setObject(int parameterIndex, Object x) throws SQLException
public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException
public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException
public void setPoolable(boolean poolable) throws SQLException
public void setQueryTimeout(int seconds) throws SQLException
public void setRef(int i, java.sql.Ref x) throws SQLException
public void setRowId(int parameterIndex, RowId x) throws SQLException
public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException
public void setShort(int parameterIndex, short x) throws SQLException
public void setString(int parameterIndex, String x) throws SQLException
public void setTime(int parameterIndex, java.sql.Time x, java.util.Calendar cal) throws SQLException
public void setTime(int parameterIndex, java.sql.Time x) throws SQLException
public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws SQLException
public void setTimestamp(int parameterIndex, java.sql.Timestamp x, java.util.Calendar cal) throws SQLException
public void setURL(int parameterIndex, java.net.URL x) throws SQLException
public void setUnicodeStream(int parameterIndex, java.io.InputStream x, int length) throws SQLException
public String toString()
public T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCreationException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCursorRuntimeException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.AbstractInterceptor**

```
public AbstractInterceptor getNextInterceptor()
public abstract Object invoke(PersistentTransaction pt) throws UOWException
public void setNextInterceptor(AbstractInterceptor next)
```
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.AddInterceptor**

```
public Object invoke(PersistentTransaction pt) throws UOWException
```
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.DeleteInterceptor**

```
public Object invoke(PersistentTransaction pt) throws UOWException
```
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.LockInterceptor**

```
public Object invoke(PersistentTransaction pt) throws UOWException
```
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.QueryInterceptor**

```
public Object invoke(PersistentTransaction pt) throws UOWException
```
***
**org.jaffa.persistence.engines.jdbcengine.interceptor.UpdateInterceptor**

```
public Object invoke(PersistentTransaction pt) throws UOWException
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.AbstractPagingPlugin**

```
public int getFirstResult()
public int getMaxResults()
public boolean next(ResultSet resultSet) throws SQLException
public String preQuery(String sql)
public void setFirstResult(int firstResult)
public void setMaxResults(int maxResults)
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.DefaultPagingPlugin**

```
public boolean next(ResultSet resultSet) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.IPagingPlugin**

```
public int getFirstResult()
public int getMaxResults()
public boolean next(ResultSet resultSet) throws SQLException
public String preQuery(String sql)
public void setFirstResult(int firstResult)
public void setMaxResults(int maxResults)
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.MysqlPagingPlugin**

```
public String preQuery(String sql)
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.OraclePagingPlugin**

```
public String preQuery(String sql)
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.PostgresqlPagingPlugin**

```
public String preQuery(String sql)
```
***
**org.jaffa.persistence.engines.jdbcengine.paging.SqlServerPagingPlugin**

```
public String preQuery(String sql)
```
***
**org.jaffa.persistence.engines.jdbcengine.proxy.PersistentDelegate**

```
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceFactory**

```
public static Class getActualPersistentClass(IPersistent object)
public static IPersistent newPersistentInstance(Class persistentClass)
public static void setInstanceValue(IPersistent object, String attributeName, Object value)
```
***
**org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceInvocationHandler**

```
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
```
***
**org.jaffa.persistence.engines.jdbcengine.proxy.ProxyFieldValidatoRuntimeException**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.BlobTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.BooleanAsBitTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.BooleanAsStringTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.BooleanTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.ClobTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.Counter**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.CurrencyTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.DataTranslator**

```
public static Object getAppObject(Object value, String typeName, String engineType) throws IOException
public static Object getAppObject(ResultSet rs, String columnName, String typeName, String engineType) throws SQLException, IOException
public static Object getAppObject(CallableStatement cstmt, int parameterIndex, String typeName, String engineType) throws SQLException, IOException
public static Object getAppValue(Object value, String typeName, String engineType)
public static String getBeginsWithDml(Object object, String typeName, String engineType) throws IOException
public static String getDml(Object object, String typeName, String engineType) throws IOException
public static String getEndsWithDml(Object object, String typeName, String engineType) throws IOException
public static String getLikeDml(Object object, String typeName, String engineType) throws IOException
public static int getSqlType(String typeName, String engineType)
public static void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String typeName, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.DateOnlyTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.DateTimeTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.DecimalTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.IntegerTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.JdbcBridge**

```
public static void executeAdd(IPersistent object, DataSource dataSource) throws SQLException, IllegalAccessException, InvocationTargetException, IOException
public static void executeDelete(IPersistent object, DataSource dataSource) throws SQLException, IllegalAccessException, InvocationTargetException, IOException
public static void executeLock(IPersistent object, DataSource dataSource) throws SQLException, IllegalAccessException, InvocationTargetException, IOException
public static Collection executeQuery(Criteria criteria, DataSource dataSource) throws IOException, SQLException, PostLoadFailedException, DataSourceCursorRuntimeException
public static void executeStoredProcedure(IStoredProcedure sp, Criteria criteria, DataSource dataSource) throws SQLException, PostLoadFailedException, IllegalAccessException, InvocationTargetException, IOException, InstantiationException
public static void executeUpdate(IPersistent object, DataSource dataSource) throws SQLException, IllegalAccessException, InvocationTargetException, IOException
public static void updatePersistentFlagsOnAdd(IPersistent object)
public static void updatePersistentFlagsOnDelete(IPersistent object)
public static void updatePersistentFlagsOnLock(IPersistent object)
public static void updatePersistentFlagsOnQuery(IPersistent object, Criteria criteria)
public static void updatePersistentFlagsOnUpdate(IPersistent object)
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.LongRawTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.LongStringTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.NullStringTypeDefinition**

```
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.NullTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.PreparedStatementArgument**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.PreparedStatementHelper**

```
public static String getDeletePreparedStatementString(ClassMetaData classMetaData, String engineType)
public static String getExistsPreparedStatementString(ClassMetaData classMetaData, String engineType)
public static String getFindByPKPreparedStatementString(int locking, ClassMetaData classMetaData, String engineType)
public static String getInsertPreparedStatementString(ClassMetaData classMetaData, String engineType)
public static String getLockPreparedStatementString(ClassMetaData classMetaData, String engineType)
public static String getUpdatePreparedStatementString(ClassMetaData classMetaData, String engineType)
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.QueryInfo**

```
public boolean equals(Object obj)
public List getArguments()
public String getSQL()
public int hashCode()
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.QueryStatementHelper**

```
public static PreparedStatement getPreparedStatement(Criteria criteria, DataSource dataSource, IPagingPlugin pagingPlugin) throws IOException, SQLException
public static String getSQL(Criteria criteria, DataSource dataSource, IPagingPlugin pagingPlugin) throws IOException
public static QueryInfo getSQL(Criteria criteria, String engineType, IPagingPlugin pagingPlugin) throws IOException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.RawTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.StatementHelper**

```
public static String getDeleteStatementString(IPersistent object, String engineType) throws IllegalAccessException, InvocationTargetException, IOException
public static String getInsertStatementString(IPersistent object, String engineType) throws IllegalAccessException, InvocationTargetException, IOException
public static String getLockStatementString(IPersistent object, String engineType) throws IllegalAccessException, InvocationTargetException, IOException
public static String getUpdateStatementString(IPersistent object, String engineType) throws IllegalAccessException, InvocationTargetException, IOException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.StringTypeDefinition**

```
public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public Object getAppValue(Object value, String typeName, String engineType)
public String getDml(Object object, String engineType) throws IOException
public Object getHibernateDml(Object object, String engineType) throws IOException
public String getLikeDml(Object object, int operator, String engineType) throws IOException
public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.TypeDefinition**

```
public abstract Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException
public abstract Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException
public abstract Object getAppValue(Object value, String typeName, String engineType)
public abstract String getDml(Object object, String engineType) throws IOException
public abstract Object getHibernateDml(Object object, String engineType) throws IOException
public abstract String getLikeDml(Object object, int operator, String engineType) throws IOException
public abstract void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException
```
***
**org.jaffa.persistence.engines.jdbcengine.querygenerator.TypeDefs**

```
public static int getSqlType(String typeName, String engineType)
public static TypeDefinition getTypeDefinitionBySqlTypeName(String typeName)
```
***
**org.jaffa.persistence.engines.jdbcengine.security.IJdbcSecurityPlugin**

No public methods.
***
**org.jaffa.persistence.engines.jdbcengine.util.Introspection**

```
public static Field getAccessibleField(Class clazz, String fieldName, Class datatype)
public static Method getAccessorMethod(Class clazz, String fieldName, Class datatype)
public static Map getAccessors(Class clazz, Map fields, Map members)
public static Method getMutatorMethod(Class clazz, String fieldName, Class datatype)
public static Map getMutators(Class clazz, Map fields, Map members)
```
***
**org.jaffa.persistence.engines.jdbcengine.util.MoldingService**

```
public static Map getFunctionQueryMap(Criteria criteria, ClassMetaData classMetaData, ResultSet rs, String engineType) throws SQLException, IOException
public static Object getInstanceValue(IPersistent object, ClassMetaData classMetaData, String attributeName) throws IllegalAccessException, InvocationTargetException
public static IPersistent getObject(ClassMetaData classMetaData) throws ClassNotFoundException, InstantiationException, IllegalAccessException
public static IPersistent getObject(ClassMetaData classMetaData, ResultSet rs, String engineType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, IOException
public static Object getValueFromCriteria(Criteria criteria, ClassMetaData classMetaData, String attributeName)
public static void setInstanceValue(IPersistent object, ClassMetaData classMetaData, String attributeName, Object value) throws IllegalAccessException, InvocationTargetException
```
***
**org.jaffa.persistence.engines.jdbcengine.variants.Variant**

```
public static String getProperty(String variant, String key) throws MissingResourceException
```
***
**org.jaffa.persistence.exceptions.AddFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.AlreadyLockedObjectException**

No public methods.
***
**org.jaffa.persistence.exceptions.CommitFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.DeleteFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.DomainObjectValidationException**

No public methods.
***
**org.jaffa.persistence.exceptions.EngineInstantiationException**

No public methods.
***
**org.jaffa.persistence.exceptions.IllegalPersistentStateRuntimeException**

No public methods.
***
**org.jaffa.persistence.exceptions.InactiveUowRuntimeException**

No public methods.
***
**org.jaffa.persistence.exceptions.InvalidUowRuntimeException**

No public methods.
***
**org.jaffa.persistence.exceptions.PostAddFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PostDeleteFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PostLoadFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PostUpdateFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PreAddFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PreDeleteFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.PreUpdateFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.QueryFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.ReadOnlyObjectException**

No public methods.
***
**org.jaffa.persistence.exceptions.RollbackFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.UOWException**

No public methods.
***
**org.jaffa.persistence.exceptions.UpdateFailedException**

No public methods.
***
**org.jaffa.persistence.exceptions.UpdatePrimaryKeyException**

No public methods.
***
**org.jaffa.persistence.logging.IPersistenceLoggingPlugin**

```
public void add(IPersistent object) throws ApplicationExceptions, FrameworkException
public void clearLog() throws ApplicationExceptions, FrameworkException
public void delete(IPersistent object) throws ApplicationExceptions, FrameworkException
public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException
public void update(IPersistent object) throws ApplicationExceptions, FrameworkException
public void writeLog() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.persistence.util.PersistentHelper**

```
public static void checkMandatoryFields(IPersistent object) throws ApplicationExceptions, FrameworkException
public static boolean exists(UOW uow, IPersistent object) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, FrameworkException
public static Criteria generateKeyCriteria(IPersistent object) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException
public static String generateSerializedKey(IPersistent object) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException
public static String generateSerializedKey(Criteria criteria) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException
public static FieldMetaData[] getFieldMetaData(String persistentClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static FieldMetaData getFieldMetaData(String persistentClassName, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static FieldMetaData[] getKeyFields(String persistentClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static String getLabelToken(String persistentClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static String getLabelToken(String persistentClassName, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static FieldMetaData[] getMandatoryFields(String persistentClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
public static Class getMetaClass(String persistentClassName) throws ClassNotFoundException
public static IPersistent loadFromSerializedKey(UOW uow, String serializedKey) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, IntrospectionException, FrameworkException
```
***
**org.jaffa.persistence.util.UOWHelper**

```
public static void addMessage(Object payload) throws FrameworkException, ApplicationExceptions
public static String[] addMessages(UOW uow, Object[] payloads, boolean dependencyChain) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.presentation.portlet.ActionBase**

```
public FormKey do_HistoryNav_Clicked(String index)
public FormKey do_refresh()
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
```
***
**org.jaffa.presentation.portlet.CustomRequestProcessor**

No public methods.
***
**org.jaffa.presentation.portlet.EventHandlerMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.FormBase**

```
public void cleanup()
public void clearErrors(HttpServletRequest request)
public Component getComponent()
public static ActionMessages getErrors(HttpServletRequest request)
public WidgetCache getWidgetCache()
public static boolean hasErrors(HttpServletRequest request)
public void initForm()
public static void raiseError(HttpServletRequest request, String property, ActionMessage error)
public static void raiseError(HttpServletRequest request, String property, String key)
public static void raiseError(HttpServletRequest request, String property, String key, Object value0)
public static void raiseError(HttpServletRequest request, String property, String key, Object value0, Object value1)
public static void raiseError(HttpServletRequest request, String property, String key, Object value0, Object value1, Object value2)
public static void raiseError(HttpServletRequest request, String property, String key, Object value0, Object value1, Object value2, Object value3)
public static void raiseError(HttpServletRequest request, String property, String key, Object[] values)
public static void raiseError(HttpServletRequest request, String property, ApplicationException appExp)
public static void raiseError(HttpServletRequest request, String property, ApplicationExceptions appExps)
public void reset(ActionMapping mapping, HttpServletRequest request)
public void setComponent(Component component)
```
***
**org.jaffa.presentation.portlet.FormKey**

```
public Object clone()
public int compareTo(Object obj)
public boolean equals(Object obj)
public static FormKey getCloseBrowserFormKey()
public String getComponentId()
public String getFormName()
public String getTitle()
public int hashCode()
public void setTitle(String title)
public String toString()
```
***
**org.jaffa.presentation.portlet.FormKeyChangeEvent**

```
public FormKey getNewFormKey()
```
***
**org.jaffa.presentation.portlet.FormKeyChangeListener**

```
public void formKeyChanged(FormKeyChangeEvent e)
```
***
**org.jaffa.presentation.portlet.HistoryNav**

```
public static void addFormKeyToHistoryNav(HttpServletRequest request, FormKey fk)
public static List decode(String historyNavXml)
public static String encode(List historyNavList)
public static List initializeHistoryNav(HttpServletRequest request)
public static List initializeHistoryNav(HttpServletRequest request, String finalUrl) throws UnsupportedEncodingException
public static List obtainHistoryNav(HttpServletRequest request)
public static boolean threadHasHistory()
```
***
**org.jaffa.presentation.portlet.HistoryNavHandler**

```
public void characters(char[] ch, int start, int length)
public void endElement(String uri, String sName, String qName)
public List getHistoryNavList()
public void startElement(String uri, String sName, String qName, Attributes atts)
```
***
**org.jaffa.presentation.portlet.JaffaHttpSessionListener**

```
public void sessionCreated(HttpSessionEvent se)
public void sessionDestroyed(HttpSessionEvent se)
```
***
**org.jaffa.presentation.portlet.PortletFilter**

```
public void autoAuthenticate(HttpServletRequest request) throws IOException, ServletException
public void autoAuthenticate(HttpServletRequest request, UserContext userContext) throws IOException, ServletException
public void autoAuthenticate(HttpServletRequest request, UserContext userContext, boolean register) throws IOException, ServletException
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void doFilterUnderSecurityContext(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig filterConfig) throws ServletException
public void initUserInfo(UserSession us) throws UserSessionSetupException
public void initUserInfo(UserSession us, UserContext userContext) throws UserSessionSetupException
public void setContexts(HttpServletRequest request) throws IOException, ServletException
```
***
**org.jaffa.presentation.portlet.StartComponentAction**

```
public FormKey defaultAction() throws Exception
```
***
**org.jaffa.presentation.portlet.component.Component**

```
public void addChildComponent(Component component)
public void addFormKeyChangeListener(FormKeyChangeListener listener)
public abstract FormKey display() throws FrameworkException, ApplicationExceptions
public ComponentDefinition getComponentDefinition()
public String getComponentId()
public FormKey getContainerFormKey()
public FormKeyChangeListener[] getFormKeyChangeListeners()
public FormKey getReturnToFormKey()
public String getToken()
public UserSession getUserSession()
public boolean isActive()
public void quit()
public FormKey quitAndReturnToCallingScreen()
public void reflectAndSetParms(HttpServletRequest request)
public void removeFormKeyChangeListener(FormKeyChangeListener listener)
public boolean replaceWithContainerFormKey(FormKey fk)
public Collection returnChildComponents()
public DateTime returnLastActivityDate()
public Component run(String component)
public void setContainerFormKey(FormKey containerFormKey)
public void setReturnToFormKey(FormKey returnToFormKey)
public void setToken(String token)
public void updateLastActivityDate()
```
***
**org.jaffa.presentation.portlet.component.ComponentCreationRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.component.ComponentDefinition**

```
public String getComponentClass()
public String getComponentDescription()
public String getComponentName()
public String getComponentType()
public String[] getMandatoryFunctions()
public String[] getOptionalFunctions()
public List<Object> getParameters()
public List<ComponentSecurity> getRefFunctions()
public boolean isRia()
```
***
**org.jaffa.presentation.portlet.component.ComponentDefinitionException**

No public methods.
***
**org.jaffa.presentation.portlet.component.ComponentManager**

```
public static ComponentDefinition find(String comp)
public static Map<String, String[]> getComponentRequirements()
public static Component run(String comp, UserSession us)
```
***
**org.jaffa.presentation.portlet.component.ComponentManagerTest**

```
public void testGetRequirements()
public void testReadDefinitions()
```
***
**org.jaffa.presentation.portlet.component.ComponentManagerWrapper**

No public methods.
***
**org.jaffa.presentation.portlet.component.ComponentSecurity**

```
public final String getName()
public final String getRef()
public final boolean isComponentRef()
public final boolean isRequired()
public final void setComponentRef(final boolean isComponentRef)
public final void setName(final String name)
public final void setRef(final String ref)
public final void setRequired(final boolean isRequired)
```
***
**org.jaffa.presentation.portlet.component.IComponent**

```
public void addFormKeyChangeListener(FormKeyChangeListener listener)
public FormKey display() throws FrameworkException, ApplicationExceptions
public ComponentDefinition getComponentDefinition()
public String getComponentId()
public FormKey getContainerFormKey()
public FormKeyChangeListener[] getFormKeyChangeListeners()
public FormKey getReturnToFormKey()
public String getToken()
public UserSession getUserSession()
public boolean isActive()
public void quit()
public FormKey quitAndReturnToCallingScreen()
public void removeFormKeyChangeListener(FormKeyChangeListener listener)
public void setContainerFormKey(FormKey containerFormKey)
public void setReturnToFormKey(FormKey returnToFormKey)
public void setToken(String token)
```
***
**org.jaffa.presentation.portlet.component.RiaWrapperComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public Properties getParameters()
public String getRiaUrl()
public void reflectAndSetParms(HttpServletRequest request)
public void setParameters(Properties parameters)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.BooleanTypeParam**

```
public String getName()
public String getValue()
public Boolean isRequired()
public void setName(String value)
public void setRequired(Boolean value)
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.Component**

```
public String getClassName()
public String getDefaultNavigation()
public List<DependentComponent> getDependentComponent()
public String getDescription()
public String getId()
public String getLicenseRef()
public List<MandatoryFunction> getMandatoryFunction()
public List<OptionalFunction> getOptionalFunction()
public Component.Params getParams()
public String getType()
public String getUrlAction()
public List<UseDesignPattern> getUseDesignPattern()
public void setClassName(String value)
public void setDefaultNavigation(String value)
public void setDescription(String value)
public void setId(String value)
public void setLicenseRef(String value)
public void setParams(Component.Params value)
public void setType(String value)
public void setUrlAction(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.Components**

```
public List<Component> getComponent()
```
***
**org.jaffa.presentation.portlet.component.componentdomain.DependentComponent**

```
public String getName()
public String getRef()
public void setName(String value)
public void setRef(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.IntTypeParam**

```
public String getName()
public BigInteger getValue()
public Boolean isRequired()
public void setName(String value)
public void setRequired(Boolean value)
public void setValue(BigInteger value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.Loader**

```
public static synchronized Map<String, ComponentDefinition> getComponentPool()
public static Loader getInstance()
public static void setComponentManager(ComponentManager componentManager)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.MandatoryFunction**

```
public String getName()
public String getRef()
public void setName(String value)
public void setRef(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.ObjectFactory**

```
public BooleanTypeParam createBooleanTypeParam()
public Component createComponent()
public Component.Params createComponentParams()
public Components createComponents()
public DependentComponent createDependentComponent()
public IntTypeParam createIntTypeParam()
public MandatoryFunction createMandatoryFunction()
public ObjectTypeParam createObjectTypeParam()
public OptionalFunction createOptionalFunction()
public StringTypeParam createStringTypeParam()
public UseDesignExtension createUseDesignExtension()
public UseDesignPattern createUseDesignPattern()
```
***
**org.jaffa.presentation.portlet.component.componentdomain.ObjectTypeParam**

```
public String getClassName()
public void setClassName(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.OptionalFunction**

```
public String getName()
public String getRef()
public void setName(String value)
public void setRef(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.Params**

```
public List<Object> getParamStringOrParamIntOrParamBoolean()
```
***
**org.jaffa.presentation.portlet.component.componentdomain.StringTypeParam**

```
public String getName()
public String getValue()
public Boolean isRequired()
public void setName(String value)
public void setRequired(Boolean value)
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.UseDesignExtension**

```
public String getName()
public void setName(String value)
```
***
**org.jaffa.presentation.portlet.component.componentdomain.UseDesignPattern**

```
public String getName()
public List<UseDesignExtension> getUseDesignExtension()
public void setName(String value)
```
***
**org.jaffa.presentation.portlet.session.GetImageServlet**

No public methods.
***
**org.jaffa.presentation.portlet.session.IdleComponentGarbageCollector**

```
public void run()
```
***
**org.jaffa.presentation.portlet.session.LocaleContext**

```
public static Locale getLocale()
public static void setLocale(Locale locale)
```
***
**org.jaffa.presentation.portlet.session.SessionManager**

```
public static void addSession(UserSession s)
public static UserSession getSession(String sessionId)
public static UserSession[] getSessions()
public static void removeSession(UserSession s)
public static void startGarbageCollectionOfIdleComponents()
public static void stopGarbageCollectionOfIdleComponents()
```
***
**org.jaffa.presentation.portlet.session.UserSession**

```
public void addComponent(Component comp)
public void addImage(String key, byte[] imageContents, String mimeType)
public void dropSessionObject(String name)
public void garbageCollectIdleComponents(int timeOutMinutes)
public Component getComponent(String compId)
public Collection getComponents()
public HttpSession getHttpSession()
public byte[] getImageContents(String key)
public String getImageMimeType(String key)
public String getNextComponentId()
public String getSessionId()
public Object getSessionObject(String name)
public Object getUserData()
public String getUserHostAddr()
public String getUserId()
public static UserSession getUserSession(HttpServletRequest request)
public static UserSession getUserSession(HttpServletRequest request, boolean register)
public String getVariation()
public WidgetCache getWidgetCache(String key)
public static boolean isUserSession(HttpServletRequest request)
public boolean isValid()
public void kill()
public void killAllComponents()
public void removeComponent(Component comp)
public void removeImage(String key)
public void setSessionId(String sessionId)
public void setUserData(Object userData)
public void setUserId(String userId)
public void setVariation(String variation)
public void showInfo()
public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent)
public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent)
```
***
**org.jaffa.presentation.portlet.session.UserSessionSetupException**

No public methods.
***
**org.jaffa.presentation.portlet.session.WidgetCache**

```
public void addModel(String fieldName, IWidgetModel model)
public void clear()
public boolean containsModel(String fieldName)
public IWidgetModel getModel(String fieldName)
public IWidgetModel removeModel(String fieldName)
```
***
**org.jaffa.presentation.portlet.widgets.controller.CheckBoxController**

```
public static void updateModel(String value, CheckBoxModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.DateTimeController**

```
public static void updateModel(String value, DateTimeModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.DropDownController**

```
public static void updateModel(String value, DropDownModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.EditBoxController**

```
public static void updateModel(String value, EditBoxModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.GridController**

```
public static void updateModel(String value, GridModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.PropertyEditorHelper**

```
public static void addRow(GridModel model)
public static String getProperties(GridModel model)
public static GridModel getPropertiesWM(FormBase form, String cacheName, String source)
public static void removeRow(GridModel model, int rowId)
```
***
**org.jaffa.presentation.portlet.widgets.controller.RadioButtonController**

```
public static void updateModel(String value, RadioButtonModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.SimpleWidgetController**

```
public static void updateModel(String value, SimpleWidgetModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.TableController**

```
public static void updateModel(String value, TableModel model)
```
***
**org.jaffa.presentation.portlet.widgets.controller.UserGridController**

```
public static void updateModel(String value, GridModel model, FormBase form)
```
***
**org.jaffa.presentation.portlet.widgets.controller.UserGridManager**

```
public List getColSettings(String userId, String userGridId)
public String getTableWidth(String userId, String userGridId)
```
***
**org.jaffa.presentation.portlet.widgets.controller.exceptions.XmlStructureRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.controller.usergriddomain.ObjectFactory**

```
public UserGridColumnSettings createUserGridColumnSettings()
public UserGridSettings createUserGridSettings()
```
***
**org.jaffa.presentation.portlet.widgets.controller.usergriddomain.UserGridColumnSettings**

```
public String getName()
public String getWidth()
public void setName(String value)
public void setWidth(String value)
```
***
**org.jaffa.presentation.portlet.widgets.controller.usergriddomain.UserGridSettings**

```
public String getGridWidth()
public List<UserGridColumnSettings> getUserGridColumnSettings()
public void setGridWidth(String value)
```
***
**org.jaffa.presentation.portlet.widgets.model.CheckBoxModel**

```
public boolean getState()
public void setState(boolean state)
```
***
**org.jaffa.presentation.portlet.widgets.model.Column**

```
public String getDataType()
public String getName()
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.model.DateTimeModel**

```
public DateTime getValue()
public void setValue(DateTime value)
```
***
**org.jaffa.presentation.portlet.widgets.model.DropDownModel**

```
public String getValue()
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.widgets.model.EditBoxModel**

```
public String getValue()
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.widgets.model.GridModel**

```
public void clearRows()
public Object getElement(String columnName, int rowNum)
public boolean getErrorInSavingUserSettings()
public GridModelRow getRow(int rowNum)
public GridModelRow getRowById(int rowId)
public int getRowNum(GridModelRow row)
public Collection getRows()
public GridModelRow getTarget()
public GridModelRow newRow()
public GridModelRow newRow(int rowNum)
public void removeRow(GridModelRow row)
public void setErrorInSavingUserSettings(boolean value)
public void setTarget(GridModelRow row)
```
***
**org.jaffa.presentation.portlet.widgets.model.GridModelRow**

```
public void addElement(String id, Object obj)
public Object getElement(String id)
public int getRowId()
public boolean hasElement(String id)
```
***
**org.jaffa.presentation.portlet.widgets.model.IWidgetModel**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.model.ImageModel**

```
public String getExtention()
public byte[] getImage() throws IOException
public String getImageUrl()
public String getMimeType()
```
***
**org.jaffa.presentation.portlet.widgets.model.Option**

```
public int compareTo(java.lang.Object obj)
public boolean equals(Object obj)
public String getLabel()
```
***
**org.jaffa.presentation.portlet.widgets.model.PropertyRuleIntrospectorUsingWidgetModel**

```
public Properties findInfo(String className, String propertyName, Object obj, String ruleName)
public String format(Object property)
public String getAnnotation()
public Properties getAuditInfo()
public String getCaseType()
public String getClientRule()
public String getCommentStyle()
public Properties getFlexInfo()
public Properties getForeignKeyInfo()
public Properties getHyperlinkInfo()
public Map<String, String> getInListValues()
public String getLabel()
public String getLayout()
public Integer getMaxFracLength()
public Integer getMaxLength()
public Object getMaxValue()
public Integer getMinLength()
public Object getMinValue()
public String[] getPattern()
public Properties getPropertyInfo()
public Class getPropertyType()
public Map<String, Object> getServiceInfo()
public boolean isHidden()
public boolean isMandatory()
public boolean isReadOnly()
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.model.RadioButtonModel**

```
public String getValue()
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel**

```
public void addOption(String label, Object value)
public Boolean getBooleanValue()
public Currency getCurrencyValue()
public String getDataType()
public DateOnly getDateOnlyValue()
public DateTime getDateTimeValue()
public Double getDecimalValue()
public FieldMetaData getFieldMetaData()
public Long getIntegerValue()
public String getLayout()
public Boolean getMandatoryOverride()
public List getOptions()
public byte[] getRawValue()
public String getStringCase()
public String getStringValue()
public Object getWidgetValue()
public boolean isMandatory()
public boolean isModelChanged()
public void setLayout(String layout)
public void setMandatory(boolean mandatory)
public void setModelChanged(boolean modelChanged)
public void setStringCase(String stringCase)
public void setWidgetValue(Object widgetValue)
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.model.StringModel**

```
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.model.TableModel**

```
public void addColumn(String name, String dataType)
public void addRow(List fields)
public void addRow(int position, List fields)
public void clearColumns()
public void clearRows()
public String getColumnDataType(String columnName)
public List getColumnNames()
public List getColumns()
public List getRow(int rowNum)
public List getRows()
public List getSelectedRows()
public Object getValue(String columnName, int rowNum)
public boolean isModelChanged()
public void setSelectedRows(List rowNums)
```
***
**org.jaffa.presentation.portlet.widgets.model.exceptions.ColumnMismatchRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.model.exceptions.DataTypeMismatchRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.model.exceptions.IllegalExtensionRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.ButtonTag**

```
public void doFinally()
public String getArg0()
public String getArg1()
public String getArg2()
public String getArg3()
public String getArg4()
public String getClassExtn()
public String getClassOverride()
public String getColor()
public String getConfirm()
public boolean getDetail()
public String getFontname()
public String getFontsize()
public String getFooterHtml()
public boolean getGuarded()
public static String getHeader()
public String getHeight()
public String getImage()
public String getLabel()
public boolean getPreprocess()
public String getRolloverImage()
public boolean getSubmit()
public String getTarget()
public boolean getText()
public String getType()
public String getWidth()
public int getWidthFactor()
public void otherDoStartTagOperations()
public void setArg0(String value)
public void setArg1(String value)
public void setArg2(String value)
public void setArg3(String value)
public void setArg4(String value)
public void setClassExtn(String value)
public void setClassOverride(String value)
public void setColor(String value)
public void setConfirm(String value)
public void setDetail(boolean value)
public void setFontname(String value)
public void setFontsize(String value)
public void setGuarded(boolean value)
public void setHeight(String value)
public void setImage(String value)
public void setLabel(String value)
public void setPreprocess(boolean value)
public void setRolloverImage(String value)
public void setSubmit(boolean value)
public void setTarget(String value)
public void setText(boolean value)
public void setType(String value)
public void setWidth(String value)
public void setWidthFactor(int value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.CalendarTag**

```
public boolean getDate()
public String getLayout()
public IPropertyRuleIntrospector getPropertyRuleIntrospector()
public boolean getTime()
public void otherDoEndTagOperations() throws JspException
public void otherDoStartTagOperations()
public void setDate(boolean value)
public void setLayout(java.lang.String value)
public void setPropertyRuleIntrospector(IPropertyRuleIntrospector propertyRuleIntrospector)
public void setTime(boolean value)
public boolean theBodyShouldBeEvaluated()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.CheckBoxTag**

```
public void doFinally()
public boolean getDisplayOnly()
public String getFooterHtml()
public static String getHeader()
public String getImageOff()
public String getImageOn()
public String getTooltip()
public String getType()
public void otherDoStartTagOperations()
public void setDisplayOnly(boolean value)
public void setImageOff(String value)
public void setImageOn(String value)
public void setTooltip(String value)
public void setType(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.Column**

```
public boolean equals(Object obj)
public String getLabelEditorLink()
public String getName()
public String getTitle()
public String getWidth()
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.ColumnHead**

```
public String getColumnCss()
public String getColumnTitle()
public String getDataType()
public String getLabel()
public String getLabelEditorLink()
public String getStyle()
public boolean isRequired()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.CommentTag**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.CustomModelTag**

```
public void doFinally()
public String getField()
public String getHtmlIdPrefix()
public String getJaffaEventNamePrefix()
public void setField(String value)
public String toString()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.CustomTag**

```
public int doAfterBody() throws JspException
public void doCatch(Throwable t) throws Throwable
public int doEndTag() throws JspException
public void doFinally()
public void doInitBody() throws JspException
public int doStartTag() throws JspException
public static ICustomTag findCustomTagAncestorWithClass(ICustomTag from, Class klass)
public String getFooterHtml()
public String getHeaderHtml()
public static Stack getNestedComponentStack(PageContext pageContext)
public Tag getParent()
public ICustomTag getParentCustomTag()
public Tag getParentTag()
public void otherDoEndTagOperations() throws JspException
public void otherDoStartTagOperations() throws JspException
public void register(ICustomTag tag)
public void setParent(final Tag parent)
public void setParentCustomTag(ICustomTag jParent)
public void setParentTag(Tag parent)
public boolean shouldEvaluateRestOfPageAfterEndTag()
public boolean theBodyShouldBeEvaluated()
public boolean theBodyShouldBeEvaluatedAgain() throws JspException
public String toString()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.DateTimeTag**

```
public void doFinally()
public boolean getBold()
public int getColumns()
public boolean getDateOnly()
public String getFooterHtml()
public static String getHeader()
public int getMinInterval()
public boolean getValidate()
public void otherDoStartTagOperations()
public void setBold(boolean value)
public void setColumns(int value)
public void setDateOnly(boolean value)
public void setMinInterval(int value)
public void setValidate(boolean value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.DropDownOptionTag**

```
public void doFinally()
public String getLabel()
public String getValue()
public void otherDoStartTagOperations()
public void setLabel(String value)
public void setValue(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.DropDownTag**

```
public void addOption(String label, String value)
public void doFinally()
public String getDependant1()
public String getDependant2()
public String getDependant3()
public boolean getDisplayOnly()
public String getDomain()
public String getDropdownDescField()
public String getDropdownValueField()
public String getKey1()
public String getKey2()
public String getKey3()
public void otherDoEndTagOperations() throws JspException
public void otherDoStartTagOperations()
public void setDependant1(String value)
public void setDependant2(String value)
public void setDependant3(String value)
public void setDisplayOnly(boolean value)
public void setDomain(String value)
public void setDropdownDescField(String value)
public void setDropdownValueField(String value)
public void setKey1(String value)
public void setKey2(String value)
public void setKey3(String value)
public boolean theBodyShouldBeEvaluated()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.EditBoxTag**

```
public void doFinally()
public String getAutoTabTo()
public boolean getBold()
public int getColumns()
public char getDecimalSymbol(Locale value)
public String getFontname()
public String getFontsize()
public String getFooterHtml()
public String getGroupingSymbol(Locale value)
public static String getHeader()
public String getHeight()
public String getHorizontalAbsolutePixelSize()
public String getHorizontalOffset()
public String getHorizontalSizePercentage()
public boolean getIgnoreDefaultColumnLimit()
public boolean getItalics()
public boolean getLookup()
public int getMetaWidth(Integer intSize, Integer fracSize, boolean suppressNegative)
public String getOnValidate()
public boolean getPassword()
public int getRows()
public boolean getTrim()
public boolean getValidate()
public String getVerticalAbsolutePixelSize()
public String getVerticalOffset()
public String getVerticalSizePercentage()
public String getWidth()
public void otherDoStartTagOperations() throws JspException
public void setAutoTabTo(String value)
public void setBold(boolean value)
public void setColumns(int value)
public void setFontname(String value)
public void setFontsize(String value)
public void setHeight(String value)
public void setHorizontalAbsolutePixelSize(String value)
public void setHorizontalOffset(String value)
public void setHorizontalSizePercentage(String value)
public void setIgnoreDefaultColumnLimit(boolean ignoreDefaultColumnLimit)
public void setItalics(boolean value)
public void setLookup(boolean value)
public void setOnValidate(String value)
public void setPassword(boolean value)
public void setRows(int value)
public void setTrim(boolean value)
public void setValidate(boolean value)
public void setVerticalAbsolutePixelSize(String value)
public void setVerticalOffset(String value)
public void setVerticalSizePercentage(String value)
public void setWidth(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.FoldingSectionTag**

```
public int doAfterBody() throws JspException
public void doFinally()
public boolean getClosed()
public String getHeaderHtml()
public boolean getHideIfNoWidgets()
public String getId()
public String getKey()
public String getLabel()
public String getName()
public void otherDoStartTagOperations()
public void register(ICustomTag tag)
public void setClosed(boolean value)
public void setFoldingTagNotHidden()
public void setHideIfNoWidgets(boolean hideIfNoWidgets)
public void setId(String value)
public void setKey(String value)
public void setLabel(String value)
public String toString()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.FooterTag**

```
public void doFinally()
public void otherDoStartTagOperations() throws JspException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.FormTag**

```
public void doCatch(Throwable t) throws Throwable
public int doEndTag() throws JspException
public void doFinally()
public int doStartTag() throws JspException
public String getFooterHtml()
public String getGuardedHtmlLocation()
public String getHeaderHtml()
public String getHtmlIdPrefix()
public String getHtmlName()
public ICustomTag getParentCustomTag()
public String getUrl()
public String getUseBean()
public void register(ICustomTag tag)
public void registerGuardedButton()
public void setGuardedHtmlLocation(String value)
public void setHtmlName(String name)
public void setParentCustomTag(ICustomTag jaffaParent)
public void setUrl(String value)
public void setUseBean(String useBean)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.GridColumnTag**

```
public void doFinally()
public String getColumnCssClass()
public String getDataType()
public String getLabel()
public String getStyle()
public String getTreeField()
public String getTreeSpacer()
public boolean isKey()
public boolean isRequired()
public void otherDoStartTagOperations() throws JspException
public void register(ICustomTag tag)
public void setColumnCssClass(String value)
public void setDataType(String value)
public void setKey(boolean value)
public void setLabel(String value)
public void setRequired(boolean required)
public void setStyle(String value)
public void setTreeField(String value)
public void setTreeSpacer(String value)
public boolean theBodyShouldBeEvaluated()
public String toString()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.GridRowTag**

```
public int doAfterBody() throws JspException
public void doFinally()
public Properties getAttributes()
public String getRowCssClass()
public String getStyle()
public void otherDoStartTagOperations() throws JspException
public void setRowCssClass(String value)
public void setStyle(String value)
public boolean theBodyShouldBeEvaluated()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.GridTag**

```
public void addAvailableCols(String columnHeadLabel)
public void addColumnContentsToStack(String label, String value)
public void addColumnHead(String label, String style, String dataType, String labelEditorLink, boolean required, String columnCss)
public void addKeyColumn(String label)
public void clearStack()
public GridModelRow currentGridRow()
public void doFinally()
public void doInitBody() throws JspException
public String getColumnCssClass()
public String getColumnTitle(String label)
public String getCssClass()
public String getCurrentLevelId()
public boolean getDetail()
public boolean getDisplayOnly()
public String getFooterHtml()
public boolean getIsTarget()
public String getKeyColumns()
public String getNewColumnName()
public String getNoRecordsKey()
public String getNoRecordsText()
public String getOutputType()
public boolean getPopupHints()
public String getRowAttributes()
public String getRowCssClass()
public int getRowNumber()
public String getRowStyle()
public String getServerSideCall()
public String getStyle()
public String getTarget()
public String getUserGridId()
public boolean isColumnHidden(String label)
public boolean isFirstPass()
public boolean isRowDisplayed()
public void otherDoEndTagOperations() throws JspException
public void otherDoStartTagOperations() throws JspException
public void register(ICustomTag tag)
public String retrieveLastLevelId(Integer level)
public void setColumnCssClass(String columnCssClass)
public void setColumnCssClasses(String css)
public void setCssClass(String value)
public void setCurrentLevelId(String value)
public void setDetail(boolean value)
public void setDisplayOnly(boolean value)
public void setHeaderCssClass(String css)
public void setNoRecordsKey(String value)
public void setOutputType(String value)
public void setPopupHints(boolean value)
public void setRowAttributes(Properties rowAttributes)
public void setRowCssClass(String rowCssClass)
public void setRowCssClasses(String css)
public void setRowDisplayed(boolean value)
public void setRowStyle(String rowStyle)
public void setStyle(String value)
public void setTarget(String value)
public void setUserGridId(String value)
public boolean theBodyShouldBeEvaluatedAgain() throws JspException
public void updateLastLevelId(Integer level, String Id)
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.HeaderTag**

```
public void doCatch(Throwable t) throws Throwable
public void doFinally()
public static String generateGetServerDateFunction()
public boolean getErrorBoxInSameWindow()
public boolean getNoCache()
public void otherDoStartTagOperations() throws JspException
public void setErrorBoxInSameWindow(boolean value)
public void setNoCache(boolean value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.IBodyTag**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.ICustomModelTag**

```
public String getField()
public String getHtmlIdPrefix()
public String getJaffaEventNamePrefix()
public void setField(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.ICustomTag**

```
public String getFooterHtml()
public String getHeaderHtml()
public ICustomTag getParentCustomTag()
public void register(ICustomTag tag)
public void setParentCustomTag(ICustomTag tag)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.IFormTag**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.IWidgetTag**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.ImageTag**

```
public void doFinally()
public String getDefImage()
public void otherDoStartTagOperations() throws JspException
public void setDefImage(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.LabelTag**

```
public void doFinally()
public String getArg0()
public String getArg1()
public String getArg2()
public String getArg3()
public String getArg4()
public String getDomain()
public String getKey()
public void otherDoStartTagOperations() throws JspException
public void setArg0(String value)
public void setArg1(String value)
public void setArg2(String value)
public void setArg3(String value)
public void setArg4(String value)
public void setDomain(String value)
public void setKey(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.LayoutTag**

```
public void addSectionContent(String sectionId, String value)
public void doFinally()
public String getId()
public LayoutType getLayoutType()
public String getSectionIds()
public String getType()
public void otherDoEndTagOperations() throws JspException
public void otherDoStartTagOperations() throws JspException
public void register(ICustomTag tag)
public void setId(String value)
public void setSectionIds(String value)
public void setType(String value)
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.LookupTag**

```
public void doFinally()
public java.lang.Boolean getBypassCriteriaScreen()
public String getComponent()
public String getDynamicParameters()
public String getReturnStyle()
public String getStaticParameters()
public String getTargetFields()
public void otherDoStartTagOperations() throws JspException
public void setBypassCriteriaScreen(java.lang.Boolean value)
public void setComponent(String value)
public void setDynamicParameters(String value)
public void setReturnStyle(String returnStyle)
public void setStaticParameters(String value)
public void setTargetFields(String value)
public boolean theBodyShouldBeEvaluated()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.PropertyTag**

```
public int doAfterBody() throws JspException
public void doFinally()
public java.lang.String getPropertyClass()
public java.lang.String getPropertyName()
public IPropertyRuleIntrospector getPropertyRuleIntrospector()
public void otherDoStartTagOperations() throws JspException
public void setPropertyClass(java.lang.String value)
public void setPropertyName(java.lang.String value)
public boolean theBodyShouldBeEvaluated()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.RadioButtonTag**

```
public void doFinally()
public boolean getDisplayOnly()
public String getSelectValue()
public void otherDoStartTagOperations() throws JspException
public void setDisplayOnly(boolean value)
public void setSelectValue(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.RaiseErrorsTag**

```
public void otherDoEndTagOperations() throws JspException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.SectionHead**

```
public String getDataForward()
public String getId()
public String getJaffaDataUrl()
public String getLabel()
public String getLabelEditorLink()
public String getLabelText()
public String getOnclick()
public String getOnload()
public String getStyleId()
public boolean isClosed()
```
***
**org.jaffa.presentation.portlet.widgets.taglib.SectionTag**

```
public void doFinally()
public String getDataForward()
public String getId()
public String getJaffaDataUrl()
public String getLabel()
public String getOnclick()
public String getOnload()
public String getStyleId()
public boolean isClosed()
public boolean isHideIfNoWidgets()
public void otherDoStartTagOperations() throws JspException
public void register(ICustomTag tag)
public void setClosed(boolean value)
public void setDataForward(String value)
public void setHideIfNoWidgets(boolean hideIfNoWidgets)
public void setId(String value)
public void setJaffaDataUrl(String value)
public void setLabel(String value)
public void setOnclick(String value)
public void setOnload(String value)
public void setStyleId(String value)
public String toString()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.TableColumnTag**

```
public void doFinally()
public String getColumn()
public String getTitle()
public String getWidth()
public void otherDoStartTagOperations() throws JspException
public void setColumn(String value)
public void setTitle(String value)
public void setWidth(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.TableTag**

```
public void addColumn(String columnName, String title, String width, String labelEditorLink)
public void doFinally()
public String getAlign()
public boolean getDetail()
public String getFooterHtml()
public static String getHeader()
public boolean getMultiSelect()
public String getNoRecordsKey()
public String getRollovercolor()
public String getSelectcolor()
public void otherDoStartTagOperations()
public void setAlign(String value)
public void setDetail(boolean value)
public void setMultiSelect(boolean value)
public void setNoRecordsKey(String value)
public void setRollovercolor(String value)
public void setSelectcolor(String value)
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.TagHelper**

```
public static String generateHyperlink(PageContext pageContext, String value, String targetComponentName, String targetFieldNames, String currentFieldNames)
public static String getEventPrefix(PageContext pageContext)
public static FieldMetaData getFieldMetaData(String className, String fieldName) throws Exception
public static Object getFieldValue(PageContext pageContext, String fieldName, String tagName)
public static FormBase getFormBase(PageContext pageContext)
public static String getFormName(PageContext pageContext)
public static FormTag getFormTag(PageContext pageContext)
public static String getHtmlFormName(PageContext pageContext)
public static String getIdPrefix(PageContext pageContext)
public static String getLabelEditorLink(PageContext pageContext, String labelFilter)
public static Object getModel(PageContext pageContext, String fieldName, String tagName)
public static Map getModelMap(PageContext pageContext)
public static Map getOriginalAttributes(PageContext pageContext)
public static String getParentNodeId(PageContext pageContext)
public static IPropertyRuleIntrospector getPropertyRuleIntrospector(PageContext pageContext, String propertyName) throws JspException
public static IPropertyRuleIntrospector getPropertyRuleIntrospector(PageContext pageContext, String propertyName, String propertyClass) throws JspException
public static String getTextForHiddenField(PageContext pageContext)
public static UserSession getUserSession(PageContext pageContext)
public static boolean isEnclosed(PageContext pageContext)
public static void restoreOriginalAttributes(PageContext pageContext, Map map)
public static IPropertyRuleIntrospector wrapPropertyRuleIntrospector(IPropertyRuleIntrospector sourcePropertyRuleIntrospector, SimpleWidgetModel widgetModel) throws JspException
```
***
**org.jaffa.presentation.portlet.widgets.taglib.TextTag**

```
public void doFinally()
public boolean getDisableHyperlink()
public String getDomain()
public String getDomainField()
public String getField()
public String getFooterHtml()
public static String getHeader()
public String getLayout()
public Integer getMaxLength()
public boolean getPopUp()
public String getType()
public boolean isPreFormated(IPropertyRuleIntrospector propertyRuleIntrospector)
public void otherDoStartTagOperations()
public static void reloadViewerHyperlinkConfig() throws IOException
public void setDisableHyperlink(boolean value)
public void setDomain(String value)
public void setDomainField(String value)
public void setField(String value)
public void setLayout(String value)
public void setMaxLength(Integer value)
public void setPopUp(boolean value)
public void setType(String value)
```
***
**org.jaffa.presentation.portlet.widgets.taglib.UserGridTag**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.ColumnAlreadyExistsRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.InvalidParametersRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.InvalidWidgetModelReturnedRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterDropDownTagMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterGridTagMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterLayoutTagMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterTableTagMissingRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.TagCannotBeEnclosedRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.WidgetModelAccessMethodInvocationRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.taglib.exceptions.WidgetModelAccessMethodNotFoundRuntimeException**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.tests.ButtonAction**

```
public FormKey do_Butt1_Clicked() throws IOException, ServletException
public FormKey do_Butt2_Clicked() throws IOException, ServletException
public FormKey do_Butt3_Clicked() throws IOException, ServletException
public FormKey do_Butt4_Clicked() throws IOException, ServletException
public FormKey do_Butt6_Clicked() throws IOException, ServletException
public FormKey do_Butt7_Clicked() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.ButtonComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.ButtonForm**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.tests.CheckBoxAction**

```
public FormKey do_Butt1_Clicked() throws IOException, ServletException
public FormKey do_FieldWithValidation_Validate() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.CheckBoxComponent**

```
public FormKey display()
public boolean getFieldLinkedToCC()
public boolean getFieldLinkedToCCAndCached()
public void setFieldLinkedToCC(boolean fieldLinkedToCC)
public void setFieldLinkedToCCAndCached(boolean fieldLinkedToCCAndCached)
```
***
**org.jaffa.presentation.portlet.widgets.tests.CheckBoxForm**

```
public boolean getFieldLinkedToCC()
public boolean getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public boolean getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public boolean getFieldWithValidation()
public WidgetModel getFieldWithValidationWM()
public void initForm()
public void setFieldLinkedToCC(boolean value)
public void setFieldLinkedToCCAndCached(boolean value)
public void setFieldLinkedToCCAndCachedWV(String value)
public void setFieldLinkedToCCWV(String value)
public void setFieldWithCachedModel(boolean value)
public void setFieldWithCachedModelWV(String value)
public void setFieldWithValidation(boolean value)
public void setFieldWithValidationWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.DateTimeAction**

```
public FormKey do_Butt1_Clicked() throws IOException, ServletException
public FormKey do_FieldWithValidation_Validate() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.DateTimeComponent**

```
public FormKey display()
public DateTime getFieldLinkedToCC()
public DateTime getFieldLinkedToCCAndCached()
public void setFieldLinkedToCC(DateTime fieldLinkedToCC)
public void setFieldLinkedToCCAndCached(DateTime fieldLinkedToCCAndCached)
```
***
**org.jaffa.presentation.portlet.widgets.tests.DateTimeForm**

```
public DateTime getFieldLinkedToCC()
public DateTime getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public DateTime getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public DateTime getFieldWithValidation()
public WidgetModel getFieldWithValidationWM()
public void initForm()
public void setFieldLinkedToCC(DateTime value)
public void setFieldLinkedToCCAndCached(DateTime value)
public void setFieldLinkedToCCAndCachedWV(String value)
public void setFieldLinkedToCCWV(String value)
public void setFieldWithCachedModel(DateTime value)
public void setFieldWithCachedModelWV(String value)
public void setFieldWithValidation(DateTime value)
public void setFieldWithValidationWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.DropDownAction**

```
public FormKey do_Butt1_Clicked() throws IOException, ServletException
public FormKey do_FieldWithValidation_Validate() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.DropDownComponent**

```
public FormKey display()
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public void setFieldLinkedToCC(String fieldLinkedToCC)
public void setFieldLinkedToCCAndCached(String fieldLinkedToCCAndCached)
```
***
**org.jaffa.presentation.portlet.widgets.tests.DropDownForm**

```
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public String getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public String getFieldWithValidation()
public WidgetModel getFieldWithValidationWM()
public void initForm()
public void setFieldLinkedToCC(String value)
public void setFieldLinkedToCCAndCached(String value)
public void setFieldLinkedToCCAndCachedWV(String value)
public void setFieldLinkedToCCWV(String value)
public void setFieldWithCachedModel(String value)
public void setFieldWithCachedModelWV(String value)
public void setFieldWithValidation(String value)
public void setFieldWithValidationWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.EditBoxAction**

```
public FormKey do_FieldWithValidation_Validate() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.EditBoxComponent**

```
public FormKey display()
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public void setFieldLinkedToCC(String fieldLinkedToCC)
public void setFieldLinkedToCCAndCached(String fieldLinkedToCCAndCached)
```
***
**org.jaffa.presentation.portlet.widgets.tests.EditBoxForm**

```
public String getDataTypeDateOnly()
public WidgetModel getDataTypeDateOnlyWM()
public String getDataTypeDateTime()
public WidgetModel getDataTypeDateTimeWM()
public String getDataTypeDecimal()
public WidgetModel getDataTypeDecimalWM()
public String getDataTypeInteger()
public WidgetModel getDataTypeIntegerWM()
public String getDataTypeString()
public WidgetModel getDataTypeStringWM()
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public String getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public String getFieldWithKeyBoard()
public WidgetModel getFieldWithKeyBoardWM()
public String getFieldWithValidation()
public WidgetModel getFieldWithValidationWM()
public String getTextArea()
public WidgetModel getTextAreaWM()
public void setDataTypeDateOnly(String value)
public void setDataTypeDateOnlyWV(String value)
public void setDataTypeDateTime(String value)
public void setDataTypeDateTimeWV(String value)
public void setDataTypeDecimal(String value)
public void setDataTypeDecimalWV(String value)
public void setDataTypeInteger(String value)
public void setDataTypeIntegerWV(String value)
public void setDataTypeString(String value)
public void setDataTypeStringWV(String value)
public void setFieldLinkedToCC(String value)
public void setFieldLinkedToCCAndCached(String value)
public void setFieldLinkedToCCAndCachedWV(String value)
public void setFieldLinkedToCCWV(String value)
public void setFieldWithCachedModel(String value)
public void setFieldWithCachedModelWV(String value)
public void setFieldWithKeyBoard(String value)
public void setFieldWithKeyBoardWV(String value)
public void setFieldWithValidation(String value)
public void setFieldWithValidationWV(String value)
public void setTextArea(String value)
public void setTextAreaWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.FoldingSectionAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.FoldingSectionComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.FoldingSectionForm**

```
public void initForm()
```
***
**org.jaffa.presentation.portlet.widgets.tests.GridAction**

```
public FormKey do_Model1_Butt1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model1_Clicked(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Model1_Validate(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Model2_Butt1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model2_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model2_Clicked(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Model2_GetChildren(String rowNum) throws IOException, ServletException
public FormKey do_Model3_Butt1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model3_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model3_Clicked(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Model3_GetChildren(String rowNum) throws IOException, ServletException
public FormKey do_Post_Clicked()
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.GridComponent**

```
public FormKey display()
public void getGridChildren(String rowNo, String field, GridForm myForm)
```
***
**org.jaffa.presentation.portlet.widgets.tests.GridForm**

```
public WidgetModel getModel1WM()
public WidgetModel getModel2WM()
public WidgetModel getModel3WM()
public void setModel1WV(String value)
public void setModel2WV(String value)
public void setModel3WV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.ImageAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.ImageComponent**

```
public FormKey display()
public byte[] getFieldLinkedToCC()
public byte[] getFieldLinkedToCCAndCached()
public static byte[] getImageBytes(String fileName)
```
***
**org.jaffa.presentation.portlet.widgets.tests.ImageForm**

```
public byte[] getField1()
public WidgetModel getField1WM()
public byte[] getFieldLinkedToCC()
public byte[] getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public byte[] getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public void initForm()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LabelAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LabelComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LabelForm**

No public methods.
***
**org.jaffa.presentation.portlet.widgets.tests.LabelMeta**

```
public static String[] getAttributes()
public static FieldMetaData[] getFieldMetaData()
public static FieldMetaData getFieldMetaData(String fieldName)
public static String getLabelToken()
public static String getName()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupAction**

```
public FormKey do_Model1_Clicked(String rowNum) throws IOException, ServletException
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupClientAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupClientComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupClientForm**

```
public String getField1()
public EditBoxModel getField1WM()
public String getField2()
public EditBoxModel getField2WM()
public String getField3()
public EditBoxModel getField3WM()
public String getField4()
public EditBoxModel getField4WM()
public void setField1(String field1)
public void setField1WV(String value)
public void setField2(String field2)
public void setField2WV(String value)
public void setField3(String field3)
public void setField3WV(String value)
public void setField4(String field4)
public void setField4WV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.LookupForm**

```
public GridModel getModel1WM()
public void setModel1WV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.RadioButtonAction**

```
public FormKey do_Butt1_Clicked() throws IOException, ServletException
public FormKey do_FieldWithValidation_Validate() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.RadioButtonComponent**

```
public FormKey display()
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public void setFieldLinkedToCC(String fieldLinkedToCC)
public void setFieldLinkedToCCAndCached(String fieldLinkedToCCAndCached)
```
***
**org.jaffa.presentation.portlet.widgets.tests.RadioButtonForm**

```
public String getFieldLinkedToCC()
public String getFieldLinkedToCCAndCached()
public WidgetModel getFieldLinkedToCCAndCachedWM()
public WidgetModel getFieldLinkedToCCWM()
public String getFieldWithCachedModel()
public WidgetModel getFieldWithCachedModelWM()
public String getFieldWithValidation()
public WidgetModel getFieldWithValidationWM()
public void initForm()
public void setFieldLinkedToCC(String value)
public void setFieldLinkedToCCAndCached(String value)
public void setFieldLinkedToCCAndCachedWV(String value)
public void setFieldLinkedToCCWV(String value)
public void setFieldWithCachedModel(String value)
public void setFieldWithCachedModelWV(String value)
public void setFieldWithValidation(String value)
public void setFieldWithValidationWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.TableAction**

```
public FormKey do_Model1_Clicked() throws IOException, ServletException
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.TableComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.TableForm**

```
public WidgetModel getModel1WM()
public void setModel1WV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.TextAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.TextComponent**

```
public FormKey display()
public String getFieldLinkedToCC()
public void setFieldLinkedToCC(String fieldLinkedToCC)
```
***
**org.jaffa.presentation.portlet.widgets.tests.TextForm**

```
public String getFieldLinkedToCC()
public String getFieldNotLinked()
public void initForm()
public void setFieldLinkedToCC(String value)
public void setFieldNotLinked(String fieldNotLinked)
```
***
**org.jaffa.presentation.portlet.widgets.tests.TreeAction**

```
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.TreeComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.TreeForm**

```
public TreeModel getTreeWM()
public void setTreeWV(String value)
```
***
**org.jaffa.presentation.portlet.widgets.tests.UserGridAction**

```
public FormKey do_Model1_Butt1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model1_Clicked(String rowNum) throws IOException, ServletException
public FormKey do_Model1_Clicked(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Model1_Validate(String rowNum, String fieldName) throws IOException, ServletException
public FormKey do_Post_Clicked()
public FormKey do_Quit_Clicked()
```
***
**org.jaffa.presentation.portlet.widgets.tests.UserGridComponent**

```
public FormKey display()
```
***
**org.jaffa.presentation.portlet.widgets.tests.UserGridForm**

```
public WidgetModel getModel1WM()
public WidgetModel getModel2WM()
public void setModel1WV(String value)
public void setModel2WV(String value)
```
***
**org.jaffa.rules.IObjectRuleIntrospector**

```
public String getAnnotation()
public Properties getAuditInfo()
public Map<String, Properties> getAuditInfoForProperties()
public Properties[] getDeclaredFlexInfo()
public Properties getDomainInfo()
public Properties getFlexInfo()
public Map<String, Properties> getFlexInfoForProperties()
public Map<String, Properties> getInfoForProperties()
public String getLabel()
public List<Properties> getMetaDataByRule(String ruleName)
public String[] getPrimaryKey()
```
***
**org.jaffa.rules.IPropertyRuleIntrospector**

```
public Properties findInfo(String className, String propertyName, Object obj, String ruleName)
public String format(Object property)
public String getAnnotation()
public Properties getAuditInfo()
public String getCaseType()
public String getClientRule()
public String getCommentStyle()
public Properties getFlexInfo()
public Properties getForeignKeyInfo()
public Properties getHyperlinkInfo()
public Map<String, String> getInListValues()
public String getLabel()
public String getLayout()
public Integer getMaxFracLength()
public Integer getMaxLength()
public Object getMaxValue()
public Integer getMinLength()
public Object getMinValue()
public String[] getPattern()
public Properties getPropertyInfo()
public Class getPropertyType()
public Map<String, Object> getServiceInfo()
public boolean isHidden()
public boolean isMandatory()
public boolean isReadOnly()
```
***
**org.jaffa.rules.IRulesEngine**

No public methods.
***
**org.jaffa.rules.KeySet**

```
public boolean equals(Object o)
public Set<String> getKeySet()
public int hashCode()
public void setKeySet(Set<String> keySet)
public String toString()
```
***
**org.jaffa.rules.RulesEngine**

```
public static void doAllValidationsForDomainField(String domainClassName, String fieldName, Object fieldValue, UOW uow) throws ValidationException, FrameworkException
public static void doAllValidationsForDomainObject(Object domainObject, UOW uow) throws ValidationException, FrameworkException
public static void doMandatoryValidationsForDomainField(String domainClassName, String fieldName, Object fieldValue, UOW uow) throws ValidationException, FrameworkException
public static void doMandatoryValidationsForDomainObject(Object domainObject, UOW uow) throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.RulesEngineException**

No public methods.
***
**org.jaffa.rules.RulesEngineFactory**

```
public static IRulesEngine getRulesEngine() throws RulesEngineException
public static synchronized void registerRulesEngine(String rulesEngineClassName) throws RulesEngineException
```
***
**org.jaffa.rules.fieldvalidators.AbstractFieldValidator**

```
public void cleanup()
public String getLabelToken()
public UOW getUow()
public Object getValue()
public void init()
public void setLabelToken(String labelToken)
public void setUow(UOW uow)
public void setValue(Object value)
public abstract void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.BooleanFieldValidator**

```
public String getPattern()
public void setPattern(String pattern)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.CurrencyFieldValidator**

```
public Integer getFracSize()
public Integer getIntSize()
public String getLayout()
public Currency getMaxValue()
public Currency getMinValue()
public void setFracSize(Integer fracSize)
public void setIntSize(Integer intSize)
public void setLayout(String layout)
public void setMaxValue(Currency maxValue)
public void setMinValue(Currency minValue)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.DateOnlyFieldValidator**

```
public String getLayout()
public DateOnly getMaxValue()
public DateOnly getMinValue()
public void setLayout(String layout)
public void setMaxValue(DateOnly maxValue)
public void setMinValue(DateOnly minValue)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.DateTimeFieldValidator**

```
public String getLayout()
public DateTime getMaxValue()
public DateTime getMinValue()
public void setLayout(String layout)
public void setMaxValue(DateTime maxValue)
public void setMinValue(DateTime minValue)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.DecimalFieldValidator**

```
public Integer getFracSize()
public Integer getIntSize()
public String getLayout()
public Double getMaxValue()
public Double getMinValue()
public void setFracSize(Integer fracSize)
public void setIntSize(Integer intSize)
public void setLayout(String layout)
public void setMaxValue(Double maxValue)
public void setMinValue(Double minValue)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.ForeignKeyFieldValidator**

```
public String getDomainClass()
public String getProperty()
public void setDomainClass(String domainClass)
public void setProperty(String property)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.GenericForeignKeyFieldValidator**

```
public String getDomainClassName()
public String getFieldName()
public String getFieldNameForField()
public String getFieldNameForTable()
public String getFieldNameForValue()
public String getTableName()
public void setDomainClassName(String domainClassName)
public void setFieldName(String fieldName)
public void setFieldNameForField(String fieldNameForField)
public void setFieldNameForTable(String fieldNameForTable)
public void setFieldNameForValue(String fieldNameForValue)
public void setTableName(String tableName)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.IFieldValidator**

```
public void cleanup()
public String getLabelToken()
public UOW getUow()
public Object getValue()
public void init()
public void setLabelToken(String labelToken)
public void setUow(UOW uow)
public void setValue(Object value)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.InListFieldValidator**

```
public String getList()
public String getSeparator()
public void setList(String list)
public void setSeparator(String separator)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.IntegerFieldValidator**

```
public Integer getIntSize()
public String getLayout()
public Long getMaxValue()
public Long getMinValue()
public void setIntSize(Integer intSize)
public void setLayout(String layout)
public void setMaxValue(Long maxValue)
public void setMinValue(Long minValue)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.MandatoryFieldValidator**

```
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.StringFieldValidator**

```
public String getCaseType()
public Integer getLength()
public Integer getMinLength()
public String getPattern()
public void setCaseType(String caseType)
public void setLength(Integer length)
public void setMinLength(Integer minLength)
public void setPattern(String pattern)
public void validate() throws ValidationException, FrameworkException
```
***
**org.jaffa.rules.fieldvalidators.Validator**

No public methods.
***
**org.jaffa.rules.fieldvalidators.ValidatorFactory**

No public methods.
***
**org.jaffa.rules.fieldvalidators.ValidatorKeyTest**

```
public void setup() throws Exception
public void testEquals() throws Exception
public void testHashCode() throws Exception
public void testOrderIndependence() throws Exception
public void testToString() throws Exception
```
***
**org.jaffa.rules.initializers.Initializer**

No public methods.
***
**org.jaffa.rules.metadata.ClassMetaData**

```
public void addField(FieldMetaData field)
public void clearFields()
public String getClassName()
public FieldMetaData getField(String fieldName)
public FieldMetaData[] getFields()
public void setClassName(String className)
public void setFields(FieldMetaData[] fields)
public String toString()
```
***
**org.jaffa.rules.metadata.CustomHandler**

```
public void endElement(String uri, String sName, String qName) throws SAXException
public void startElement(String uri, String sName, String qName, Attributes atts)
```
***
**org.jaffa.rules.metadata.FieldMetaData**

```
public void addRule(RuleMetaData rule)
public void clearRules()
public String getExtendsClass()
public String getExtendsField()
public String getName()
public boolean getOverridesDefault()
public RuleMetaData[] getRules()
public void setExtendsClass(String extendsClass)
public void setExtendsField(String extendsField)
public void setName(String name)
public void setOverridesDefault(boolean overridesDefault)
public void setRules(RuleMetaData[] rules)
public String toString()
```
***
**org.jaffa.rules.metadata.FieldValidatorMetaData**

```
public void addParameter(String name, String value)
public void clearParameters()
public String getClassName()
public String getDescription()
public boolean getMandatory()
public String getName()
public String getParameter(String name)
public Map getParameters()
public void setClassName(String className)
public void setDescription(String description)
public void setMandatory(boolean mandatory)
public void setName(String name)
public String toString()
```
***
**org.jaffa.rules.metadata.RuleMetaData**

```
public void addParameter(String name, String value)
public void clearParameters()
public String getName()
public String getParameter(String name)
public Map getParameters()
public void setName(String name)
public String toString()
```
***
**org.jaffa.rules.metadata.RulesMetaDataService**

```
public static void clearCache()
public static void clearCache(String variation)
public static ClassMetaData getClassMetaData(String className, String variation) throws SAXException, IOException
public static void main(String[] args)
```
***
**org.jaffa.rules.metadata.ValidatorMetaDataService**

```
public static FieldValidatorMetaData getFieldValidatorMetaData(String name) throws SAXException, IOException
public static void main(String[] args)
```
***
**org.jaffa.security.CheckPolicy**

```
public void destroy()
public static BusinessFunctionManager getBusinessFunctionManager()
public static HashMap getCompErrors()
public static HashMap getRoleErrors()
public String getServletInfo()
public void init(ServletConfig config) throws ServletException
public static void main(String[] args)
public static void setBusinessFunctionManager(BusinessFunctionManager businessFunctionManager)
```
***
**org.jaffa.security.CheckPolicyTest**

```
public void setup()
public void testCheckPolicy() throws ServletException
```
***
**org.jaffa.security.EncryptionHelper**

```
public static SecretKey createKey()
public static String encryptObjectForURL(Object source, SecretKey key) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.NoSuchPaddingException, java.io.UnsupportedEncodingException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.io.NotSerializableException
public static String encryptStringForURL(String source, SecretKey key) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.NoSuchPaddingException, java.io.UnsupportedEncodingException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException
public static byte fromHex(char c)
public static byte[] fromHexString(String in)
public static Object getObjectFromEncryptedURL(String data, SecretKey key) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.NoSuchPaddingException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException
public static String getStringFromEncryptedURL(String data, SecretKey key) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.NoSuchPaddingException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException
public static byte[] intoBytes(String in) throws UnsupportedEncodingException
public static byte[] intoBytes16(String in)
public static String intoHexString(byte[] in)
public static String intoString(byte[] in)
public static String intoString16(byte[] in)
public static void main(String[] args)
public static SecretKey readKey(File file) throws IOException, ClassNotFoundException
public static SecretKey readKeyClassPath(String name) throws IOException, ClassNotFoundException
public static char toHex(byte b)
```
***
**org.jaffa.security.EncryptionHelperTest**

```
public void testBasicFunctions()
public void testBasicFunctions2()
public void testCreateKey()
public void testEncryptObject()
public void testEncryptString()
```
***
**org.jaffa.security.FakePrincipal**

```
public java.lang.String getName()
```
***
**org.jaffa.security.FakeRequest**

```
public java.lang.Object getAttribute(java.lang.String str)
public java.util.Enumeration getAttributeNames()
public java.lang.String getAuthType()
public java.lang.String getCharacterEncoding()
public int getContentLength()
public java.lang.String getContentType()
public java.lang.String getContextPath()
public javax.servlet.http.Cookie[] getCookies()
public long getDateHeader(java.lang.String str)
public java.lang.String getHeader(java.lang.String str)
public java.util.Enumeration getHeaderNames()
public java.util.Enumeration getHeaders(java.lang.String str)
public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
public int getIntHeader(java.lang.String str)
public String getLocalAddr()
public String getLocalName()
public int getLocalPort()
public java.util.Locale getLocale()
public java.util.Enumeration getLocales()
public java.lang.String getMethod()
public java.lang.String getParameter(java.lang.String str)
public java.util.Map getParameterMap()
public java.util.Enumeration getParameterNames()
public java.lang.String[] getParameterValues(java.lang.String str)
public java.lang.String getPathInfo()
public java.lang.String getPathTranslated()
public java.lang.String getProtocol()
public java.lang.String getQueryString()
public java.io.BufferedReader getReader() throws java.io.IOException
public java.lang.String getRealPath(java.lang.String str)
public java.lang.String getRemoteAddr()
public java.lang.String getRemoteHost()
public int getRemotePort()
public java.lang.String getRemoteUser()
public javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String str)
public java.lang.String getRequestURI()
public StringBuffer getRequestURL()
public java.lang.String getRequestedSessionId()
public java.lang.String getScheme()
public java.lang.String getServerName()
public int getServerPort()
public java.lang.String getServletPath()
public javax.servlet.http.HttpSession getSession()
public javax.servlet.http.HttpSession getSession(boolean param)
public java.security.Principal getUserPrincipal()
public boolean isRequestedSessionIdFromCookie()
public boolean isRequestedSessionIdFromURL()
public boolean isRequestedSessionIdFromUrl()
public boolean isRequestedSessionIdValid()
public boolean isSecure()
public boolean isUserInRole(java.lang.String str)
public void removeAttribute(java.lang.String str)
public void setAttribute(java.lang.String str, java.lang.Object obj)
public void setCharacterEncoding(String str) throws java.io.UnsupportedEncodingException
```
***
**org.jaffa.security.JDBCSecurityPlugin**

```
public static void clearCache()
public static void clearCacheForUser(final String userId)
public void freeConnection(Connection pooledConnection) throws java.sql.SQLException
public boolean isSecurableConnection(Connection connection)
public void newConnection(Connection pooledConnection) throws java.sql.SQLException
```
***
**org.jaffa.security.PolicyCache**

```
public static String getDefaultFileLocation()
public static String getFileLocation()
public static RoleManager getRoleManager()
public static Roles getRoles()
public static String getVariationFileLocation()
public static void setRoleManager(RoleManager roleManager)
```
***
**org.jaffa.security.PolicyManager**

```
public static void clearCache()
public static Set<String> getRoleSet()
public static String[] getRolesForComponent(String componentName)
public static String[] getRolesForFunction(String functionName)
```
***
**org.jaffa.security.PolicyManagerTest**

```
public void testComponentNoConstraints()
public void testComponentWithConstraintsAllUsers()
public void testComponentWithConstraintsLimitedUsers()
public void testComponentWithConstraintsNoUsers()
public void testFunctionAccess()
public void testInvalidComponentAccess()
```
***
**org.jaffa.security.SecurityContext**

```
public boolean equals(Object obj)
public String toString()
```
***
**org.jaffa.security.SecurityContextStack**

```
public SecurityContext getContext()
public void pop()
public void push(SecurityContext ctx)
```
***
**org.jaffa.security.SecurityManager**

```
public static boolean checkComponentAccess(String componentName)
public static boolean checkFunctionAccess(String functionName)
public static Principal getPrincipal()
public static List getUserRoles()
public static Object runFunction(String functionName, PrivilegedAction action) throws AccessControlException
public static Object runFunction(String functionName, PrivilegedExceptionAction action) throws PrivilegedActionException, AccessControlException
public static Object runWithContext(HttpServletRequest ctx, Object obj, String method, Object[] args) throws Exception
public static Object runWithContext(HttpServletRequest ctx, Object obj, String method, Object[] args, Class[] sig) throws Exception
public static Object runWithContext(HttpServletRequest ctx, Object obj, Method method, Object[] args) throws Exception
public static Object runWithContext(EJBContext ctx, Object obj, String method, Object[] args) throws Exception
public static Object runWithContext(EJBContext ctx, Object obj, String method, Object[] args, Class[] sig) throws Exception
public static Object runWithContext(EJBContext ctx, Object obj, Method method, Object[] args) throws Exception
```
***
**org.jaffa.security.SecurityTag**

```
public static boolean hasComponentAccess(HttpServletRequest request, String componentName)
public static boolean hasFunctionAccess(HttpServletRequest request, String functionName)
public static void setThreadContext(HttpServletRequest request) throws SecurityException
public static void unsetThreadContext(HttpServletRequest request) throws SecurityException
```
***
**org.jaffa.security.TestDataSecurity**

```
public void testItemFilterPolicy1()
```
***
**org.jaffa.security.TestPolicy**

```
public void loadBF()
public void loadRoles()
public static void main(String[] args)
```
***
**org.jaffa.security.TestThread**

```
public static void main(String[] args)
public void test1()
public void test2()
```
***
**org.jaffa.security.ThreadLocalContextStack**

```
public Object initialValue()
```
***
**org.jaffa.security.UserContext**

```
public String[] getRoles()
public String getUserId()
public void setRoles(String[] roles)
public void setUserId(String userId)
```
***
**org.jaffa.security.VariationContext**

```
public static String getVariation()
public static void setVariation(String variation)
```
***
**org.jaffa.security.businessfunctionsdomain.BusinessFunction**

```
public String getDescription()
public String getName()
public void setDescription(String value)
public void setName(String value)
```
***
**org.jaffa.security.businessfunctionsdomain.BusinessFunctions**

```
public List<BusinessFunction> getBusinessFunction()
```
***
**org.jaffa.security.businessfunctionsdomain.ObjectFactory**

```
public BusinessFunction createBusinessFunction()
public BusinessFunctions createBusinessFunctions()
```
***
**org.jaffa.security.filter.SecurityFilter**

```
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig filterConfig) throws ServletException
```
***
**org.jaffa.security.filter.SecurityFilterPrincipal**

```
public String getName()
public List getRoles()
public void setRoles(List roles)
```
***
**org.jaffa.security.filter.SecurityFilterRequestWrapper**

```
public String getRemoteUser()
public Principal getUserPrincipal()
public boolean isUserInRole(String role)
```
***
**org.jaffa.security.jdbcsecurity.domain.JdbcSecuritySetContext**

```
public String getLocalId()
public int[] getParamDirections()
public String[] getParamSqlTypes()
public String[] getParameters()
public String[] getRole()
public String getUserId()
public String prepareCall()
public void setLocalId(String localId)
public void setRole(String[] role)
public void setUserId(String userId)
```
***
**org.jaffa.security.jdbcsecurity.domain.JdbcSecurityUnsetContext**

```
public int[] getParamDirections()
public String[] getParamSqlTypes()
public String[] getParameters()
public String prepareCall()
```
***
**org.jaffa.security.securityrolesdomain.Exclude**

```
public String getName()
public void setName(String value)
```
***
**org.jaffa.security.securityrolesdomain.GrantFunctionAccess**

```
public String getName()
public void setName(String value)
```
***
**org.jaffa.security.securityrolesdomain.Include**

```
public String getName()
public void setName(String value)
```
***
**org.jaffa.security.securityrolesdomain.ObjectFactory**

```
public Exclude createExclude()
public GrantFunctionAccess createGrantFunctionAccess()
public Include createInclude()
public Role createRole()
public Roles createRoles()
```
***
**org.jaffa.security.securityrolesdomain.Role**

```
public String getDescription()
public List<Exclude> getExclude()
public List<GrantFunctionAccess> getGrantFunctionAccess()
public List<Include> getInclude()
public String getName()
public void setDescription(String value)
public void setName(String value)
```
***
**org.jaffa.security.securityrolesdomain.Roles**

```
public List<Role> getRole()
```
***
**org.jaffa.security.taglib.ComponentGuardTag**

```
public int doAfterBody() throws JspException
public int doEndTag() throws JspException
public int doStartTag() throws JspException
public boolean getHasAccess()
public String getName()
public void otherDoEndTagOperations()
public void otherDoStartTagOperations()
public void setHasAccess(boolean value)
public void setName(String value)
public boolean shouldEvaluateRestOfPageAfterEndTag()
public boolean theBodyShouldBeEvaluated()
public boolean theBodyShouldBeEvaluatedAgain()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.security.taglib.FunctionGuardTag**

```
public int doAfterBody() throws JspException, JspException
public int doEndTag() throws JspException, JspException
public int doStartTag() throws JspException, JspException
public boolean getHasAccess()
public String getName()
public void otherDoEndTagOperations()
public void otherDoStartTagOperations()
public void setHasAccess(boolean value)
public void setName(String value)
public boolean shouldEvaluateRestOfPageAfterEndTag()
public boolean theBodyShouldBeEvaluated()
public boolean theBodyShouldBeEvaluatedAgain()
public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException
```
***
**org.jaffa.session.ContextManager**

```
public static ApplicationRulesManager getApplicationRulesManager()
public Object getProperty(Object key)
public Set getPropertyNames()
public Set getPropertyNames(String filter)
public Map getThreadContext()
public static void setApplicationRulesManager(ApplicationRulesManager applicationRulesManager)
public Object setProperty(Object key, Object value)
public void setThreadContext(HttpServletRequest request)
public void setUserPreference(String name, String value) throws IOException
public void setUserPreferences(Properties userPreferences) throws IOException
public void unSetUserPreference(String name) throws IOException
public void unSetUserPreferences(Set userPreferences) throws IOException
public void unsetThreadContext()
```
***
**org.jaffa.session.ContextManagerFactory**

```
public static IContextManager instance()
public static void newInstance()
```
***
**org.jaffa.session.ContextManagerService**

```
public void reload()
public void start()
public void stop()
```
***
**org.jaffa.session.ContextManagerServiceMBean**

```
public void reload()
public void start()
public void stop()
```
***
**org.jaffa.session.IContextManager**

```
public Object getProperty(Object key)
public Set getPropertyNames()
public Set getPropertyNames(String filter)
public Map getThreadContext()
public Object setProperty(Object key, Object value)
public void setThreadContext(HttpServletRequest request)
public void setUserPreference(String name, String value) throws IOException
public void setUserPreferences(Properties userPreferences) throws IOException
public void unSetUserPreference(String name) throws IOException
public void unSetUserPreferences(Set userPreferences) throws IOException
public void unsetThreadContext()
```
***
**org.jaffa.session.SessionFilter**

```
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig filterConfig) throws ServletException
```
***
**org.jaffa.session.WebServiceSessionFilter**

```
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig filterConfig) throws ServletException
```
***
**org.jaffa.session.WebServiceSessionListener**

```
public void sessionCreated(HttpSessionEvent event)
public void sessionDestroyed(HttpSessionEvent event)
```
***
**org.jaffa.struts.action.ActionServlet**

No public methods.
***
**org.jaffa.struts.action.ActionServletTest**

```
public void setup() throws Exception
public void testWithMetaInfConfig() throws Exception
public void testWithoutMetaInfConfig() throws Exception
```
***
**org.jaffa.struts.tiles.JaffaI18nFactorySet**

```
public void initFactory(ServletContext servletContext, String proposedFilename) throws DefinitionsFactoryException, FileNotFoundException
```
***
**org.jaffa.struts.tiles.JaffaI18nFactorySetTest**

```
public void setup() throws Exception
public void testXmlDefinitionsLoading() throws Exception
```
***
**org.jaffa.util.BeanHelper**

```
public static Object cloneBean(Object bean, boolean deepCloneForeignField) throws IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException
public static Object convertDataType(Class beanClass, String propertyName, String propertyValue) throws IntrospectionException, IllegalArgumentException, ClassCastException
public static Object getField(Object bean, String field) throws NoSuchMethodException
public static Class getPropertyType(Class beanClass, String propertyName) throws IntrospectionException
public static String getReaderName(String field)
public static String listMethods(Class beanClass)
public static boolean setField(Object bean, String propertyName, String propertyValue) throws IntrospectionException, IllegalAccessException, InvocationTargetException
public static boolean setField(Object bean, String propertyName, Object propertyValue) throws IntrospectionException, IllegalAccessException, InvocationTargetException
```
***
**org.jaffa.util.CallBackDropdownHelper**

```
public CodeHelperOutElementDto getOptions(HttpServletRequest request, String returnField, String descField, String domainName, CriteriaElementDto[] criteriaFields) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.util.Children**

```
public void clear()
public boolean contains(Object o)
public Iterator iterator()
public int size()
```
***
**org.jaffa.util.ContextHelper**

```
public static String getContextSalience(String contextPath)
public static String getManifestParameter(String manifestPath, boolean readContextSalience)
public static String getManifestParameter(Class clazz) throws IOException
public static String getManifestParameter(Manifest manifest, boolean readContextSalience)
public static String getVariationSalience(String contextPath)
```
***
**org.jaffa.util.ContextHelperTest**

```
public void testGetContext() throws IOException
public void testGetContextFromClass() throws IOException
```
***
**org.jaffa.util.CssParser**

```
public static void main(String[] args)
public static Map parse(String css) throws ParseException
public static String toString(Map css)
```
***
**org.jaffa.util.DefaultEntityResolver**

```
public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
```
***
**org.jaffa.util.DefaultErrorHandler**

```
public void error(SAXParseException exception) throws SAXException
public void fatalError(SAXParseException exception) throws SAXException
public void warning(SAXParseException exception) throws SAXException
```
***
**org.jaffa.util.DirFilter**

```
public boolean accept(File dir, String name)
```
***
**org.jaffa.util.DirList**

No public methods.
***
**org.jaffa.util.EmailHelper**

```
public static void emailAttachments(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from, String[] to, String subject, String bodyText, BodyPart[] attachments) throws MessagingException
public static void emailExcel(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from, String[] to, String subject, String bodyText, TableModel excelData) throws MessagingException
public static void emailExcel(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from, String[] to, String subject, String bodyText, TableModel[] excelDataArray) throws MessagingException
public static void emailFiles(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from, String[] to, String subject, String bodyText, File[] files) throws MessagingException
```
***
**org.jaffa.util.EmailHelperAuthenticator**

```
public PasswordAuthentication getPasswordAuthentication()
```
***
**org.jaffa.util.EmailerBean**

```
public String getAdministrator()
public Boolean getSendPartial()
public String getSmtpHost()
public String getSmtpLocalHost()
public String getSmtpPassword()
public String getSmtpUser()
public String processTemplate(String template, Object data) throws MessagingException
public void sendMail(String[] to, String subject, String bodyText) throws MessagingException
public void sendMail(String from, String[] to, String subject, String bodyText) throws MessagingException
public void sendMail(String from, String[] to, String subject, String bodyText, TableModel excelData) throws MessagingException
public void sendMail(String from, String[] to, String subject, String bodyText, TableModel[] excelDataArray) throws MessagingException
public void sendMail(String from, String[] to, String subject, String bodyText, File[] files) throws MessagingException
public void sendMailTemplate(String[] to, String subject, String bodyText, Object data) throws MessagingException
public void sendMailTemplate(String from, String[] to, String subject, String bodyText, Object data) throws MessagingException
public void sendMailTemplate(String from, String[] to, String subject, String bodyText, Object data, BodyPart[] attachments) throws MessagingException
public void sendMailTemplateWithFileAttachments(String from, String[] to, String subject, String bodyText, Object data, File[] fileAttachments) throws MessagingException
public void setAdministrator(String value)
public void setSendPartial(Boolean sendpartial)
public void setSmtpHost(String value)
public void setSmtpLocalHost(String value)
public void setSmtpPassword(String value)
public void setSmtpUser(String value)
```
***
**org.jaffa.util.EntrySet**

```
public void clear()
public boolean contains(Object o)
public Iterator iterator()
public boolean remove(Object o)
public int size()
```
***
**org.jaffa.util.EventHandler**

```
public static EventHandler.Method[] getEventMethods(String eventId)
```
***
**org.jaffa.util.ExceptionHelper**

```
public static ApplicationExceptions extractApplicationExceptions(Throwable exception)
public static ApplicationExceptions extractApplicationExceptionsFromSQLException(SQLException exception)
public static String extractErrorMessage(Throwable exception)
public static Throwable extractException(Throwable exception, Class exceptionClass)
public static FrameworkException extractFrameworkException(Throwable exception)
public static String getStackTrace(Throwable exception)
public static Throwable throwAF(Throwable exception) throws ApplicationExceptions, FrameworkException
public static RuntimeException throwAFR(Throwable exception) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.util.FileHelper**

```
public static byte[] loadBinaryFile(File file) throws IOException
public static String loadTextFile(File file) throws IOException
public static void saveTextFile(File file, String text) throws FileNotFoundException, IOException
public static void sortFiles(File[] files)
```
***
**org.jaffa.util.FindFiles**

```
public List getList()
```
***
**org.jaffa.util.HTMLHelper**

```
public static String extractText(String html)
public static String removeAllComments(String html)
public static String removeAllTags(String html)
public static String replaceAllEntities(String html)
public static String replaceAllTags(String html)
public static String replaceTag(String html, String replaceTag, String replaceWith)
```
***
**org.jaffa.util.JAXBHelper**

```
public static Schema createSchema(String url) throws IOException, SAXException
public static String marshalPayload(Object payload) throws JAXBException
public static JAXBContext obtainJAXBContext(Class clazz) throws JAXBException
public static T unmarshalConfigFile(Class clazz, Resource resource, String configurationSchemaFile) throws IOException, JAXBException, SAXException
public static Object unmarshalPayload(String xml, String dataBeanClassName) throws JAXBException, ClassNotFoundException
```
***
**org.jaffa.util.JSONHelper**

```
public static String map2json(Map m)
public static String map2jsonConverter(Map hm)
public static String requestParams2json(ServletRequest request)
```
***
**org.jaffa.util.KeySet**

```
public void clear()
public boolean contains(Object o)
public Iterator iterator()
public boolean remove(Object o)
public int size()
```
***
**org.jaffa.util.LabelHelper**

```
public static void setLabel(String token, String value) throws FrameworkException
```
***
**org.jaffa.util.Line**

```
public String getContents()
public String getEol()
public String toString()
```
***
**org.jaffa.util.ListMap**

```
public void clear()
public Object clone() throws CloneNotSupportedException
public boolean containsKey(Object key)
public boolean containsValue(Object value)
public Set entrySet()
public boolean equals(Object o)
public Object get(Object key)
public Object getByIndex(int index)
public int hashCode()
public int indexOf(Object key)
public boolean isEmpty()
public Set keySet()
public Object put(Object key, Object value)
public Object put(int index, Object key, Object value)
public void putAll(Map t)
public Object remove(Object key)
public Object remove(int index)
public int size()
public Collection values()
```
***
**org.jaffa.util.ListMapEntry**

```
public boolean equals(Object o)
public Object getKey()
public Object getValue()
public int hashCode()
public Object setValue(Object value)
```
***
**org.jaffa.util.ListMapIterator**

```
public boolean hasNext()
public Object next()
public void remove()
```
***
**org.jaffa.util.ListMapTest**

```
public static void main(java.lang.String[] args)
public void setUp()
public static Test suite()
public void tearDown()
public void testClear()
public void testContainsKey()
public void testContainsKey1()
public void testContainsValue()
public void testEntrySet()
public void testEntrySet1()
public void testEntrySet2()
public void testEntrySet3()
public void testEquals()
public void testEquals1()
public void testGet()
public void testGet1()
public void testHashCode()
public void testHashCode1()
public void testIsEmpty()
public void testIsEmpty1()
public void testIsEmpty2()
public void testKeySet()
public void testKeySet1()
public void testKeySet2()
public void testKeySet3()
public void testKeySet4()
public void testPut()
public void testPutAll()
public void testPutAndSubstitute()
public void testPutAndSubstitute1()
public void testRemove()
public void testRemove1()
public void testRemove2()
public void testRemove3()
public void testRemove4()
public void testRemove5()
public void testRemove6()
public void testRemove7()
public void testSize()
public void testValues()
public void testValues1()
public void testValues2()
public void testValues3()
public void testValues4()
public void testValues5()
```
***
**org.jaffa.util.ListProperties**

```
public void clear()
public Object clone()
public boolean containsKey(Object key)
public boolean containsValue(Object value)
public Enumeration elements()
public Set entrySet()
public boolean equals(Object o)
public Object get(Object key)
public String getComments(String key)
public String getProperty(String key)
public String getProperty(String key, String defaultValue)
public int hashCode()
public boolean isEmpty()
public Set keySet()
public Enumeration keys()
public void list(PrintStream out)
public void list(PrintWriter out)
public void load(InputStream inStream) throws IOException
public void load(Reader r) throws IOException
public Enumeration propertyNames()
public Object put(Object key, Object value)
public void putAll(Map t)
public Object remove(Object key)
public void setComments(String key, String comments)
public Object setProperty(String key, String value, String comments)
public Object setProperty(String key, String value)
public int size()
public void sort()
public void store(OutputStream out, String header) throws IOException
public void store(Writer w, String header) throws IOException
public String toString()
public Collection values()
```
***
**org.jaffa.util.ListSet**

```
public boolean add(Object o)
public void add(int index, Object o)
public boolean addAll(Collection c)
public void clear()
public Object clone() throws CloneNotSupportedException
public boolean contains(Object o)
public boolean containsAll(Collection c)
public boolean equals(Object o)
public Object get(int index)
public int hashCode()
public int indexOf(Object o)
public boolean isEmpty()
public Iterator iterator()
public boolean remove(Object o)
public Object remove(int index)
public boolean removeAll(Collection c)
public boolean retainAll(Collection c)
public int size()
public Object[] toArray()
public Object[] toArray(Object[] a)
```
***
**org.jaffa.util.LocaleHelper**

```
public static String determineLayout(String inputLayout)
public static String getProperty(String dataType)
public static Locale string2Locale(String lstr)
```
***
**org.jaffa.util.LocalizationRTL**

```
public static boolean isRtlLanguage(String language)
```
***
**org.jaffa.util.LoggerHelper**

```
public static void init()
```
***
**org.jaffa.util.MessageHelper**

```
public static String findMessage(String key, Object[] args)
public static String findMessage(Locale locale, String key, Object[] args)
public static String findMessage(PageContext pageContext, String key, Object[] args)
public static String findMessage(PageContext pageContext, String resources, String locale, String key, Object[] args)
public static String findMessage(MessageResources resources, Locale locale, String key, Object[] args)
public static boolean hasTokens(String input)
public static boolean isSingleToken(String input)
public static String removeTokenMarkers(String input)
public static String replaceTokens(String message)
public static String replaceTokens(Locale locale, String message)
public static String replaceTokens(PageContext pageContext, String message)
public static String replaceTokens(PageContext pageContext, String resources, String locale, String message)
public static String replaceTokens(MessageResources resources, Locale locale, String message)
public static String tokenize(String input)
```
***
**org.jaffa.util.Method**

```
public Class[] getArgumentTypes()
public Object[] getArgumentValues()
public String getName()
public String toString()
```
***
**org.jaffa.util.NestedMap**

```
public void clear()
public boolean containsKey(Object key)
public boolean containsValue(Object value)
public Set entrySet()
public Object get(Object key)
public boolean isEmpty()
public Set keySet()
public Object put(Object key, Object value)
public void putAll(Map t)
public Object remove(Object key)
public int size()
public Collection values()
```
***
**org.jaffa.util.Node**

```
public void addChild(Node node)
public Object getAttribute(Object key)
public Map getAttributes()
public Node getChildById(String id)
public Node getChildByName(String name)
public Collection getChildren()
public Node getFromFamilyById(String id)
public String getId()
public String getName()
public Node getParent()
public Node getRoot()
public Object getValue()
public boolean hasChildren()
public boolean isRoot()
public static void main(String[] args)
public void makeRoot()
public boolean parentHasMoreChildren()
public void printNode(java.io.Writer writer) throws java.io.IOException
public void printNode(java.io.Writer writer, String pad, String padIncrement) throws java.io.IOException
public Object removeAttribute(Object key)
public boolean removeChild(Node node)
public boolean removeChild(String name)
public boolean removeChildren()
public Object setAttribute(Object key, Object value)
public void setAttributes(Map attributes)
public void setName(String name)
public void setValue(Object value)
```
***
**org.jaffa.util.NodeIterator**

```
public boolean hasNext()
public Object next()
public void remove()
```
***
**org.jaffa.util.NodeTest**

```
public static void main(java.lang.String[] args)
public void setUp()
public static Test suite()
public void tearDown()
public void testAddChild()
public void testGetAttribute()
public void testGetAttributes()
public void testGetChild()
public void testGetChildren()
public void testGetFromFamilyById()
public void testGetId()
public void testGetName()
public void testGetParent()
public void testGetRoot()
public void testGetValue()
public void testHasChildren()
public void testIsRoot()
public void testMakeRoot()
public void testPrintNode()
public void testRemoveAttribute()
public void testRemoveChild()
public void testRemoveChildren()
public void testSetAttribute()
public void testSetAttributes()
public void testSetName()
public void testSetValue()
```
***
**org.jaffa.util.NumberHelper**

```
public static Double round(Double input, int newScale)
```
***
**org.jaffa.util.OrderedPathMatchingResourcePatternResolver**

```
public static OrderedPathMatchingResourcePatternResolver getInstance()
public Resource[] getResources(String locationPattern) throws IOException
public boolean isAscending()
public void setAscending(boolean ascending)
```
***
**org.jaffa.util.OsScriptingBean**

```
public void destroy()
public int exec() throws ScriptingException
public int exec(String[] command) throws ScriptingException
public String[] getCommand()
public Properties getEnvironment()
public String getError()
public String getOutput()
public File getScriptFile() throws ScriptingException
public List getSetupScripts()
public List getTeardownScripts()
public String getUnixShell()
public static void main(String[] args)
public void reset()
public void setCommand(String[] command)
public void setEnvironment(Properties environment)
public void setSetupScripts(List setupScripts)
public void setTeardownScripts(List teardownScripts)
public void setUnixShell(String unixShell)
```
***
**org.jaffa.util.Property**

```
public Object clone() throws CloneNotSupportedException
public String getCommentsInFile()
public String getContents()
public String getKey()
public String getKeyInFile()
public String getSeparatorInFile()
public String getValue()
public String getValueInFile()
public void setCommentsInFile(String commentsInFile)
public void setKey(String key)
public void setSeparatorInFile(String separatorInFile)
public void setValue(String value)
public String toString()
```
***
**org.jaffa.util.PropertyMessageResources**

```
public synchronized void flushCache()
```
***
**org.jaffa.util.PropertyMessageResourcesFactory**

```
public MessageResources createResources(String config)
public static MessageResources getDefaultMessageResources()
```
***
**org.jaffa.util.SettingsBase**

No public methods.
***
**org.jaffa.util.SplitString**

```
public String getPrefix()
public String getSuffix()
public boolean isValid()
```
***
**org.jaffa.util.StreamFilter**

```
public String getData()
public void run()
```
***
**org.jaffa.util.StringHelper**

```
public static String addCommentWithStamp(String originalComment, String additionalComment, boolean lifo, String userId)
public static String addHTMLLineBreak(String s, int lineLimit)
public static String convertFromHTML(String s)
public static String convertToHTML(String s)
public static ArrayList convertToList(String commaList)
public static boolean equalsIgnoreCaseFirstChar(String first, String second)
public static String escapeJavascript(String s)
public static Map<String, String> extractNameValuePairs(String input)
public static Map<String, String> extractNameValuePairs(String input, char separator)
public static String findEol(String input)
public static String formatDescription(Object field, String layout, String domainClassWithPackage, String domainField, boolean toHtml)
public static String formatDescription(Object field, String layout, String domainClassWithPackage, String domainField, boolean toHtml, String beginMarker, String endMarker)
public static String formatDescription(Object field, String layout, String domainClassWithPackage, String domainField, boolean toHtml, String beginMarker, String endMarker, int limit, String truncateIndicator)
public static long fromBase(String num, String baseChars)
public static String getDefaultDescriptionBeginMarker()
public static String getDefaultDescriptionEndMarker()
public static String getJavaBeanStyle(String input)
public static String getJavaStyle(String input)
public static String getLower(String input)
public static String getLower1(String input)
public static String getShortClassName(Class clazz)
public static String getShortClassName(String className)
public static String getSpace(String input)
public static String getStatic(String input)
public static String getString(Reader reader) throws IOException
public static StringBuffer getStringBuffer(Reader reader) throws IOException
public static String getUpper(String input)
public static String getUpper1(String input)
public static String linePad(String text, int indent)
public static String linePad(String text, int indent, String indentWith)
public static String linePad(String text, int indent, String indentWith, boolean supressFirst)
public static String lpad(String input, int length)
public static String lpad(String input, int length, char padChar)
public static void main(String[] args)
public static String pad(int number)
public static String pad(int number, int length)
public static String pad(int number, int length, char padChar)
public static String pad(String input, int length, char padChar, boolean rpad)
public static String[] parseString(String s)
public static String[] parseString(String s, String delim)
public static String printArray(String[] s)
public static Line readLine(PushbackReader reader) throws IOException
public static String replace(String source, String find, String replace)
public static String replicate(String text, int count)
public static String rpad(String input, int length)
public static String rpad(String input, int length, char padChar)
public static String[] split(String input, char separator)
public static String[] splitLines(String str, int maxLength)
public static String toBase(long num, String baseChars)
public static String toBase16(long num)
public static String toBase36(long num)
public static String toBase64(long num)
```
***
**org.jaffa.util.TestBean1**

```
public String getTest1()
public Long getTest2()
public int getTest3()
public Boolean getTest4()
public DateTime getTest5(int index)
public DateTime[] getTest5()
public long getTest6(int index)
public long[] getTest6()
public HeaderDto getTest7()
public void setTest1(String test1)
public void setTest2(Long test2)
public void setTest3(int test3)
public void setTest4(Boolean test4)
public void setTest5(int index, DateTime test5)
public void setTest5(DateTime[] test5)
public void setTest6(int index, long test6)
public void setTest6(long[] test6)
public void setTest7(HeaderDto test7)
```
***
**org.jaffa.util.Tokenizer**

```
public boolean hasLiteral()
public boolean hasToken()
public String next()
```
***
**org.jaffa.util.URLHelper**

```
public static String buildUrl(String url, HttpServletRequest req)
public static String getBase(HttpServletRequest request)
public static String getFullUrl(HttpServletRequest request)
public static InputStream getInputStream(String resourceName) throws IOException
public static URL getUrl(String resourceName)
public static void main(String[] args)
public static URL newExtendedURL(String url) throws MalformedURLException
```
***
**org.jaffa.util.URLHelperTest**

```
public void destroy()
public String getServletInfo()
public void init(ServletConfig config) throws ServletException
```
***
**org.jaffa.util.UnzipFiles**

```
public static void unzip(String zipFilePath, String destDirectory) throws IOException
```
***
**org.jaffa.util.VMIDVoucherGenerator**

```
public String generate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.util.Values**

```
public void clear()
public boolean contains(Object o)
public Iterator iterator()
public int size()
```
***
**org.jaffa.util.Voucher**

```
public static synchronized String getNext()
public static void main(String[] args)
```
***
**org.jaffa.util.WebServiceInvoker**

```
public String getOperationName()
public String getPassword()
public String getTargetHostPlusApplication()
public String getTargetNamespace()
public String getUserId()
public Class getWebServiceClass()
public Object invoke(Object... arguments) throws SOAPException, SOAPFaultException, JAXBException
public void setOperationName(String operationName)
public void setPassword(String password)
public void setTargetHostPlusApplication(String targetHostPlusApplication)
public void setTargetNamespace(String targetNamespace)
public void setUserId(String userId)
public void setWebServiceClass(Class webServiceClass)
```
***
**org.jaffa.util.XmlHelper**

```
public static String getTextTrim(Node node)
public static boolean hasChildElements(Node node)
public static InputStream stripDoctypeDeclaration(URL in) throws IOException
public static InputStream stripDoctypeDeclaration(InputStream in) throws IOException
```
***
**org.jaffa.util.ZipFiles**

```
public void zipFolder(String srcPath, String destPath, String zipFileName) throws Exception
```
***
**org.owasp.filters.ClickjackFilter**

```
public void destroy()
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
public void init(FilterConfig filterConfig)
```
***
**security.AllTests**

```
public static void main(String[] args)
public static Test suite()
```
***
**security.JSPGuardsTest**

```
public void testBadLogon()
public void testClerkAccess()
public void testClerkRunComponent()
public void testManagerAccess()
public void testManagerClerkRunComponent()
```
