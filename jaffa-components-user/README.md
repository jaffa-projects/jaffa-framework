# jaffa-components-user Public Methods

***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.IUserFinder**

```
public void destroy()
public UserFinderOutDto find(UserFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto.UserFinderInDto**

```
public StringCriteriaField getEMailAddress()
public StringCriteriaField getFirstName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastName()
public StringCriteriaField getStatus()
public StringCriteriaField getUserName()
public void setEMailAddress(StringCriteriaField eMailAddress)
public void setFirstName(StringCriteriaField firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(StringCriteriaField lastName)
public void setStatus(StringCriteriaField status)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto.UserFinderOutDto**

```
public void addRows(UserFinderOutRowDto rows)
public void clearRows()
public UserFinderOutRowDto getRows(int index)
public UserFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserFinderOutRowDto rows)
public void setRows(UserFinderOutRowDto rows, int index)
public void setRows(UserFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto.UserFinderOutRowDto**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.String getStatus()
public java.lang.String getStatusDescription()
public java.lang.String getUserName()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setStatus(java.lang.String status)
public void setStatusDescription(java.lang.String statusDescription)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.tx.UserFinderTx**

```
public void destroy()
public UserFinderOutDto find(UserFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.ui.UserFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.ui.UserFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
public String getEMailAddress()
public String getEMailAddressDd()
public String getFirstName()
public String getFirstNameDd()
public String getLastName()
public String getLastNameDd()
public String getStatus()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setStatus(String status)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userfinder.ui.UserFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getEMailAddress()
public String getEMailAddressDd()
public DropDownModel getEMailAddressDdWM()
public EditBoxModel getEMailAddressWM()
public String getFirstName()
public String getFirstNameDd()
public DropDownModel getFirstNameDdWM()
public EditBoxModel getFirstNameWM()
public String getLastName()
public String getLastNameDd()
public DropDownModel getLastNameDdWM()
public EditBoxModel getLastNameWM()
public String getStatus()
public GridModel getStatusWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setEMailAddressDdWV(String value)
public void setEMailAddressWV(String value)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setFirstNameDdWV(String value)
public void setFirstNameWV(String value)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setLastNameDdWV(String value)
public void setLastNameWV(String value)
public void setStatus(String status)
public void setStatusWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.IUserLookup**

```
public void destroy()
public UserLookupOutDto find(UserLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupInDto**

```
public StringCriteriaField getEMailAddress()
public StringCriteriaField getFirstName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastName()
public StringCriteriaField getStatus()
public StringCriteriaField getUserName()
public void setEMailAddress(StringCriteriaField eMailAddress)
public void setFirstName(StringCriteriaField firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(StringCriteriaField lastName)
public void setStatus(StringCriteriaField status)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupOutDto**

```
public void addRows(UserLookupOutRowDto rows)
public void clearRows()
public UserLookupOutRowDto getRows(int index)
public UserLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserLookupOutRowDto rows)
public void setRows(UserLookupOutRowDto rows, int index)
public void setRows(UserLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupOutRowDto**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.String getStatus()
public java.lang.String getStatusDescription()
public java.lang.String getUserName()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setStatus(java.lang.String status)
public void setStatusDescription(java.lang.String statusDescription)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.tx.UserLookupTx**

```
public void destroy()
public UserLookupOutDto find(UserLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.ui.UserLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.ui.UserLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
public String getEMailAddress()
public String getEMailAddressDd()
public String getFirstName()
public String getFirstNameDd()
public String getLastName()
public String getLastNameDd()
public String getStatus()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setStatus(String status)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userlookup.ui.UserLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getEMailAddress()
public String getEMailAddressDd()
public DropDownModel getEMailAddressDdWM()
public EditBoxModel getEMailAddressWM()
public String getFirstName()
public String getFirstNameDd()
public DropDownModel getFirstNameDdWM()
public EditBoxModel getFirstNameWM()
public String getLastName()
public String getLastNameDd()
public DropDownModel getLastNameDdWM()
public EditBoxModel getLastNameWM()
public String getStatus()
public GridModel getStatusWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setEMailAddressDdWV(String value)
public void setEMailAddressWV(String value)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setFirstNameDdWV(String value)
public void setFirstNameWV(String value)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setLastNameDdWV(String value)
public void setLastNameWV(String value)
public void setStatus(String status)
public void setStatusWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.IUserMaintenance**

```
public UserMaintenanceRetrieveOutDto create(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserMaintenancePrevalidateOutDto prevalidateCreate(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenancePrevalidateOutDto prevalidateUpdate(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenanceRetrieveOutDto retrieve(UserMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenanceRetrieveOutDto update(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenanceCreateInDto**

```
public void addUserRole(UserRoleDto userRole)
public void clearUserRole()
public java.lang.Boolean getAutoPassword()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public HeaderDto getHeaderDto()
public java.lang.String getLastName()
public java.lang.Boolean getNotifyUser()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public java.lang.String getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public java.lang.String getUserName()
public UserRoleDto getUserRole(int index)
public UserRoleDto[] getUserRole()
public int getUserRoleCount()
public boolean removeUserRole(UserRoleDto userRole)
public void setAutoPassword(java.lang.Boolean autoPassword)
public void setEMailAddress(java.lang.String EMailAddress)
public void setFirstName(java.lang.String firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(java.lang.String lastName)
public void setNotifyUser(java.lang.Boolean notifyUser)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setRequestId(java.lang.String requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public void setUserRole(UserRoleDto userRole, int index)
public void setUserRole(UserRoleDto[] objects)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public Boolean getPerformDirtyReadCheck()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenanceRetrieveOutDto**

```
public void addUserRole(UserRoleDto userRole)
public void clearUserRole()
public java.lang.Boolean getAutoPassword()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.Boolean getNotifyUser()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public java.lang.String getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public java.lang.String getUserName()
public UserRoleDto getUserRole(int index)
public UserRoleDto[] getUserRole()
public int getUserRoleCount()
public boolean removeUserRole(UserRoleDto userRole)
public void setAutoPassword(java.lang.Boolean autoPassword)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String EMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setNotifyUser(java.lang.Boolean notifyUser)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setRequestId(java.lang.String requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public void setUserRole(UserRoleDto userRole, int index)
public void setUserRole(UserRoleDto[] objects)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserMaintenanceUpdateInDto**

```
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public Boolean getPerformDirtyReadCheck()
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.UserRoleDto**

```
public java.lang.String getRoleName()
public void setRoleName(java.lang.String roleName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.UserMaintenanceTx**

```
public UserMaintenanceRetrieveOutDto create(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserMaintenancePrevalidateOutDto prevalidateCreate(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenancePrevalidateOutDto prevalidateUpdate(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenanceRetrieveOutDto retrieve(UserMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserMaintenanceRetrieveOutDto update(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceAction**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.Boolean getAutoPassword()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.Boolean getNotifyUser()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public UserRoleDto[] getRelatedObjectUserRoleDto()
public java.lang.String getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void quit()
public void setAutoPassword(java.lang.Boolean autoPassword)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String EMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setNotifyUser(java.lang.Boolean notifyUser)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setRelatedObjectUserRoleDto(UserRoleDto[] relatedObjectUserRoleDto)
public void setRequestId(java.lang.String requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public String generatePassword()
public java.lang.Boolean getAutoPassword()
public CheckBoxModel getAutoPasswordWM()
public java.lang.String getCreatedBy()
public EditBoxModel getCreatedByWM()
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public java.lang.String getEMailAddress()
public EditBoxModel getEMailAddressWM()
public java.lang.String getFirstName()
public EditBoxModel getFirstNameWM()
public java.lang.String getLastName()
public EditBoxModel getLastNameWM()
public java.lang.String getLastUpdatedBy()
public EditBoxModel getLastUpdatedByWM()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public DateTimeModel getLastUpdatedOnWM()
public java.lang.Boolean getNotifyUser()
public CheckBoxModel getNotifyUserWM()
public java.lang.String getPassword1()
public EditBoxModel getPassword1WM()
public java.lang.String getPassword2()
public EditBoxModel getPassword2WM()
public GridModel getRelatedUserRoleWM()
public java.lang.String getRequestId()
public EditBoxModel getRequestIdWM()
public GridModel getRolesGridWM()
public java.lang.String getSecurityAnswer()
public EditBoxModel getSecurityAnswerWM()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public DropDownModel getSecurityQuestion1WM()
public EditBoxModel getSecurityQuestionWM()
public java.lang.String getStatus()
public DropDownModel getStatusWM()
public java.lang.String getUserName()
public EditBoxModel getUserNameWM()
public void setAutoPassword(java.lang.Boolean autoPassword)
public void setAutoPasswordWV(String value)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedByWV(String value)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setEMailAddress(java.lang.String EMailAddress)
public void setEMailAddressWV(String value)
public void setFirstName(java.lang.String firstName)
public void setFirstNameWV(String value)
public void setLastName(java.lang.String lastName)
public void setLastNameWV(String value)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedByWV(String value)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setLastUpdatedOnWV(String value)
public void setNotifyUser(java.lang.Boolean notifyUser)
public void setNotifyUserWV(String value)
public void setPassword1(java.lang.String password1)
public void setPassword1WV(String value)
public void setPassword2(java.lang.String password2)
public void setPassword2WV(String value)
public void setRelatedUserRoleWV(String value)
public void setRequestId(java.lang.String requestId)
public void setRequestIdWV(String value)
public void setRolesGridWV(String value)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityAnswerWV(String value)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setSecurityQuestion1WV(String value)
public void setSecurityQuestionWV(String value)
public void setStatus(java.lang.String status)
public void setStatusWV(String value)
public void setUserName(java.lang.String userName)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userselfupdate.ui.UserSelfUpdateComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.IUserViewer**

```
public void destroy()
public UserViewerOutDto read(UserViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserRoleDto**

```
public java.lang.String getRoleName()
public java.lang.String getUserName()
public void setRoleName(java.lang.String roleName)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getUserName()
public void setHeaderDto(HeaderDto headerDto)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserViewerOutDto**

```
public void addUserRole(UserRoleDto userRole)
public void clearUserRole()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.String getStatus()
public java.lang.String getStatusDescription()
public java.lang.String getUserName()
public UserRoleDto getUserRole(int index)
public UserRoleDto[] getUserRole()
public int getUserRoleCount()
public boolean removeUserRole(UserRoleDto userRole)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String EMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn)
public void setStatus(java.lang.String status)
public void setStatusDescription(java.lang.String statusDescription)
public void setUserName(java.lang.String userName)
public void setUserRole(UserRoleDto userRole, int index)
public void setUserRole(UserRoleDto[] objects)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.tx.UserViewerTx**

```
public void destroy()
public UserViewerOutDto read(UserViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui.UserViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui.UserViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getUserName()
public UserViewerOutDto getUserViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setUserName(java.lang.String userName)
public void setUserViewerOutDto(UserViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui.UserViewerForm**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public GridModel getRelatedUserRoleWM()
public java.lang.String getStatus()
public java.lang.String getStatusDescription()
public java.lang.String getUserName()
public void setRelatedUserRoleWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.domain.CompositeKey**

```
public boolean equals(Object obj)
public java.lang.String getRoleName()
public java.lang.String getUserName()
public int hashCode()
public void setRoleName(java.lang.String roleName)
public void setUserName(java.lang.String userName)
```
***
**org.jaffa.applications.jaffa.modules.admin.domain.User**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String userName) throws FrameworkException
public static User findByPK(UOW uow, java.lang.String userName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String userName)
public Criteria findUserRoleCriteria()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getLastUpdatedBy()
public org.jaffa.datatypes.DateTime getLastUpdatedOn()
public java.lang.String getPassword()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
public UserRole[] getUserRoleArray() throws FrameworkException
public UserRole newUserRoleObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEMailAddress(java.lang.String eMailAddress) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFirstName(java.lang.String firstName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastName(java.lang.String lastName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastUpdatedBy(java.lang.String lastUpdatedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPassword(java.lang.String password) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEMailAddress(java.lang.String eMailAddress) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFirstName(java.lang.String firstName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastName(java.lang.String lastName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastUpdatedBy(java.lang.String lastUpdatedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePassword(java.lang.String password) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateEMailAddress(java.lang.String eMailAddress) throws ValidationException, FrameworkException
public java.lang.String validateFirstName(java.lang.String firstName) throws ValidationException, FrameworkException
public java.lang.String validateLastName(java.lang.String lastName) throws ValidationException, FrameworkException
public java.lang.String validateLastUpdatedBy(java.lang.String lastUpdatedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn) throws ValidationException, FrameworkException
public java.lang.String validatePassword(java.lang.String password) throws ValidationException, FrameworkException
public java.lang.String validateSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, FrameworkException
public java.lang.Long validateSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, FrameworkException
public java.lang.String validateStatus(java.lang.String status) throws ValidationException, FrameworkException
public java.lang.String validateUserName(java.lang.String userName) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.domain.UserMeta**

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
**org.jaffa.applications.jaffa.modules.admin.domain.UserRole**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String userName, java.lang.String roleName) throws FrameworkException
public static UserRole findByPK(UOW uow, java.lang.String userName, java.lang.String roleName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String userName, java.lang.String roleName)
public CompositeKey getCompositeKey()
public java.lang.String getRoleName()
public java.lang.String getUserName()
public User getUserObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setCompositeKey(CompositeKey compositeKey)
public void setRoleName(java.lang.String roleName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateRoleName(java.lang.String roleName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateUserName(java.lang.String userName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateRoleName(java.lang.String roleName) throws ValidationException, FrameworkException
public java.lang.String validateUserName(java.lang.String userName) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta**

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
**org.jaffa.applications.jaffa.modules.user.components.requestpassword.IRequestPassword**

```
public void destroy()
```
***
**org.jaffa.applications.jaffa.modules.user.components.requestpassword.tx.RequestPasswordTx**

```
public void destroy()
```
***
**org.jaffa.applications.jaffa.modules.user.components.requestpassword.ui.RequestPasswordAction**

```
public FormKey do_Cancel_Clicked()
public FormKey do_Clear_Clicked()
public FormKey do_Finish_Clicked()
public FormKey do_Next_Clicked()
public FormKey do_Previous_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.applications.jaffa.modules.user.components.requestpassword.ui.RequestPasswordComponent**

```
public String determineAndSetNextScreen()
public String determineAndSetPreviousScreen()
public String determineCurrentScreen()
public FormKey determineFormKey()
public String determineNextScreen()
public String determinePreviousScreen()
public FormKey display() throws ApplicationExceptions, FrameworkException
public int getCurrentScreenCounter()
public java.lang.String getEmail()
public String[] getScreens()
public java.lang.String getSecurityAnswer()
public java.lang.String getSecurityQuestion()
public java.lang.String getUserName()
public void quit()
public void sendEmail()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setEmail(java.lang.String email)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.String securityQuestion)
public void setUserName(java.lang.String userName)
```
***
**org.jaffa.applications.jaffa.modules.user.components.requestpassword.ui.RequestPasswordForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public int getCurrentScreenCounter()
public java.lang.String getEmail()
public EditBoxModel getEmailWM()
public java.lang.String getSecurityAnswer()
public EditBoxModel getSecurityAnswerWM()
public java.lang.String getSecurityQuestion()
public DropDownModel getSecurityQuestionWM()
public java.lang.String getUserName()
public EditBoxModel getUserNameWM()
public boolean isNextActionAvailable()
public boolean isPreviousActionAvailable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setEmail(java.lang.String email)
public void setEmailWV(String value)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityAnswerWV(String value)
public void setSecurityQuestion(java.lang.String securityQuestion)
public void setSecurityQuestionWV(String value)
public void setUserName(java.lang.String userName)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.IUserRequestFinder**

```
public void destroy()
public UserRequestFinderOutDto find(UserRequestFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderInDto**

```
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getEMailAddress()
public StringCriteriaField getFirstName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastName()
public StringCriteriaField getPassword()
public DateTimeCriteriaField getProcessedDatetime()
public StringCriteriaField getProcessedUserId()
public StringCriteriaField getRemarks()
public IntegerCriteriaField getRequestId()
public StringCriteriaField getSecurityAnswer()
public IntegerCriteriaField getSecurityQuestion()
public StringCriteriaField getStatus()
public StringCriteriaField getUserName()
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setEMailAddress(StringCriteriaField eMailAddress)
public void setFirstName(StringCriteriaField firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(StringCriteriaField lastName)
public void setPassword(StringCriteriaField password)
public void setProcessedDatetime(DateTimeCriteriaField processedDatetime)
public void setProcessedUserId(StringCriteriaField processedUserId)
public void setRemarks(StringCriteriaField remarks)
public void setRequestId(IntegerCriteriaField requestId)
public void setSecurityAnswer(StringCriteriaField securityAnswer)
public void setSecurityQuestion(IntegerCriteriaField securityQuestion)
public void setStatus(StringCriteriaField status)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderOutDto**

```
public void addRows(UserRequestFinderOutRowDto rows)
public void clearRows()
public UserRequestFinderOutRowDto getRows(int index)
public UserRequestFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserRequestFinderOutRowDto rows)
public void setRows(UserRequestFinderOutRowDto rows, int index)
public void setRows(UserRequestFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderOutRowDto**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setPassword(java.lang.String password)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.tx.UserRequestFinderTx**

```
public void destroy()
public UserRequestFinderOutDto find(UserRequestFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.ui.UserRequestFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.ui.UserRequestFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
public String getCreatedOn()
public String getCreatedOnDd()
public String getEMailAddress()
public String getEMailAddressDd()
public String getFirstName()
public String getFirstNameDd()
public String getLastName()
public String getLastNameDd()
public String getPassword()
public String getPasswordDd()
public String getProcessedDatetime()
public String getProcessedDatetimeDd()
public String getProcessedUserId()
public String getProcessedUserIdDd()
public String getRemarks()
public String getRemarksDd()
public String getRequestId()
public String getRequestIdDd()
public String getSecurityAnswer()
public String getSecurityAnswerDd()
public String getSecurityQuestion()
public String getSecurityQuestionDd()
public String getStatus()
public String getStatusDd()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setPassword(String password)
public void setPasswordDd(String passwordDd)
public void setProcessedDatetime(String processedDatetime)
public void setProcessedDatetimeDd(String processedDatetimeDd)
public void setProcessedUserId(String processedUserId)
public void setProcessedUserIdDd(String processedUserIdDd)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRequestId(String requestId)
public void setRequestIdDd(String requestIdDd)
public void setSecurityAnswer(String securityAnswer)
public void setSecurityAnswerDd(String securityAnswerDd)
public void setSecurityQuestion(String securityQuestion)
public void setSecurityQuestionDd(String securityQuestionDd)
public void setStatus(String status)
public void setStatusDd(String statusDd)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.ui.UserRequestFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getEMailAddress()
public String getEMailAddressDd()
public DropDownModel getEMailAddressDdWM()
public EditBoxModel getEMailAddressWM()
public String getFirstName()
public String getFirstNameDd()
public DropDownModel getFirstNameDdWM()
public EditBoxModel getFirstNameWM()
public String getLastName()
public String getLastNameDd()
public DropDownModel getLastNameDdWM()
public EditBoxModel getLastNameWM()
public String getPassword()
public String getPasswordDd()
public DropDownModel getPasswordDdWM()
public EditBoxModel getPasswordWM()
public String getProcessedDatetime()
public String getProcessedDatetimeDd()
public DropDownModel getProcessedDatetimeDdWM()
public EditBoxModel getProcessedDatetimeWM()
public String getProcessedUserId()
public String getProcessedUserIdDd()
public DropDownModel getProcessedUserIdDdWM()
public EditBoxModel getProcessedUserIdWM()
public String getRemarks()
public String getRemarksDd()
public DropDownModel getRemarksDdWM()
public EditBoxModel getRemarksWM()
public String getRequestId()
public String getRequestIdDd()
public DropDownModel getRequestIdDdWM()
public EditBoxModel getRequestIdWM()
public String getSecurityAnswer()
public String getSecurityAnswerDd()
public DropDownModel getSecurityAnswerDdWM()
public EditBoxModel getSecurityAnswerWM()
public String getSecurityQuestion()
public String getSecurityQuestionDd()
public DropDownModel getSecurityQuestionDdWM()
public EditBoxModel getSecurityQuestionWM()
public String getStatus()
public String getStatusDd()
public DropDownModel getStatusDdWM()
public EditBoxModel getStatusWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setEMailAddressDdWV(String value)
public void setEMailAddressWV(String value)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setFirstNameDdWV(String value)
public void setFirstNameWV(String value)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setLastNameDdWV(String value)
public void setLastNameWV(String value)
public void setPassword(String password)
public void setPasswordDd(String passwordDd)
public void setPasswordDdWV(String value)
public void setPasswordWV(String value)
public void setProcessedDatetime(String processedDatetime)
public void setProcessedDatetimeDd(String processedDatetimeDd)
public void setProcessedDatetimeDdWV(String value)
public void setProcessedDatetimeWV(String value)
public void setProcessedUserId(String processedUserId)
public void setProcessedUserIdDd(String processedUserIdDd)
public void setProcessedUserIdDdWV(String value)
public void setProcessedUserIdWV(String value)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRemarksDdWV(String value)
public void setRemarksWV(String value)
public void setRequestId(String requestId)
public void setRequestIdDd(String requestIdDd)
public void setRequestIdDdWV(String value)
public void setRequestIdWV(String value)
public void setSecurityAnswer(String securityAnswer)
public void setSecurityAnswerDd(String securityAnswerDd)
public void setSecurityAnswerDdWV(String value)
public void setSecurityAnswerWV(String value)
public void setSecurityQuestion(String securityQuestion)
public void setSecurityQuestionDd(String securityQuestionDd)
public void setSecurityQuestionDdWV(String value)
public void setSecurityQuestionWV(String value)
public void setStatus(String status)
public void setStatusDd(String statusDd)
public void setStatusDdWV(String value)
public void setStatusWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.IUserRequestLookup**

```
public void destroy()
public UserRequestLookupOutDto find(UserRequestLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupInDto**

```
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getEMailAddress()
public StringCriteriaField getFirstName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastName()
public StringCriteriaField getPassword()
public DateTimeCriteriaField getProcessedDatetime()
public StringCriteriaField getProcessedUserId()
public StringCriteriaField getRemarks()
public IntegerCriteriaField getRequestId()
public StringCriteriaField getSecurityAnswer()
public IntegerCriteriaField getSecurityQuestion()
public StringCriteriaField getStatus()
public StringCriteriaField getUserName()
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setEMailAddress(StringCriteriaField eMailAddress)
public void setFirstName(StringCriteriaField firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(StringCriteriaField lastName)
public void setPassword(StringCriteriaField password)
public void setProcessedDatetime(DateTimeCriteriaField processedDatetime)
public void setProcessedUserId(StringCriteriaField processedUserId)
public void setRemarks(StringCriteriaField remarks)
public void setRequestId(IntegerCriteriaField requestId)
public void setSecurityAnswer(StringCriteriaField securityAnswer)
public void setSecurityQuestion(IntegerCriteriaField securityQuestion)
public void setStatus(StringCriteriaField status)
public void setUserName(StringCriteriaField userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupOutDto**

```
public void addRows(UserRequestLookupOutRowDto rows)
public void clearRows()
public UserRequestLookupOutRowDto getRows(int index)
public UserRequestLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(UserRequestLookupOutRowDto rows)
public void setRows(UserRequestLookupOutRowDto rows, int index)
public void setRows(UserRequestLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupOutRowDto**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setPassword(java.lang.String password)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.tx.UserRequestLookupTx**

```
public void destroy()
public UserRequestLookupOutDto find(UserRequestLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.ui.UserRequestLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.ui.UserRequestLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
public String getCreatedOn()
public String getCreatedOnDd()
public String getEMailAddress()
public String getEMailAddressDd()
public String getFirstName()
public String getFirstNameDd()
public String getLastName()
public String getLastNameDd()
public String getPassword()
public String getPasswordDd()
public String getProcessedDatetime()
public String getProcessedDatetimeDd()
public String getProcessedUserId()
public String getProcessedUserIdDd()
public String getRemarks()
public String getRemarksDd()
public String getRequestId()
public String getRequestIdDd()
public String getSecurityAnswer()
public String getSecurityAnswerDd()
public String getSecurityQuestion()
public String getSecurityQuestionDd()
public String getStatus()
public String getStatusDd()
public String getUserName()
public String getUserNameDd()
public void quit()
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setPassword(String password)
public void setPasswordDd(String passwordDd)
public void setProcessedDatetime(String processedDatetime)
public void setProcessedDatetimeDd(String processedDatetimeDd)
public void setProcessedUserId(String processedUserId)
public void setProcessedUserIdDd(String processedUserIdDd)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRequestId(String requestId)
public void setRequestIdDd(String requestIdDd)
public void setSecurityAnswer(String securityAnswer)
public void setSecurityAnswerDd(String securityAnswerDd)
public void setSecurityQuestion(String securityQuestion)
public void setSecurityQuestionDd(String securityQuestionDd)
public void setStatus(String status)
public void setStatusDd(String statusDd)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public FormKey updateObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.ui.UserRequestLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getEMailAddress()
public String getEMailAddressDd()
public DropDownModel getEMailAddressDdWM()
public EditBoxModel getEMailAddressWM()
public String getFirstName()
public String getFirstNameDd()
public DropDownModel getFirstNameDdWM()
public EditBoxModel getFirstNameWM()
public String getLastName()
public String getLastNameDd()
public DropDownModel getLastNameDdWM()
public EditBoxModel getLastNameWM()
public String getPassword()
public String getPasswordDd()
public DropDownModel getPasswordDdWM()
public EditBoxModel getPasswordWM()
public String getProcessedDatetime()
public String getProcessedDatetimeDd()
public DropDownModel getProcessedDatetimeDdWM()
public EditBoxModel getProcessedDatetimeWM()
public String getProcessedUserId()
public String getProcessedUserIdDd()
public DropDownModel getProcessedUserIdDdWM()
public EditBoxModel getProcessedUserIdWM()
public String getRemarks()
public String getRemarksDd()
public DropDownModel getRemarksDdWM()
public EditBoxModel getRemarksWM()
public String getRequestId()
public String getRequestIdDd()
public DropDownModel getRequestIdDdWM()
public EditBoxModel getRequestIdWM()
public String getSecurityAnswer()
public String getSecurityAnswerDd()
public DropDownModel getSecurityAnswerDdWM()
public EditBoxModel getSecurityAnswerWM()
public String getSecurityQuestion()
public String getSecurityQuestionDd()
public DropDownModel getSecurityQuestionDdWM()
public EditBoxModel getSecurityQuestionWM()
public String getStatus()
public String getStatusDd()
public DropDownModel getStatusDdWM()
public EditBoxModel getStatusWM()
public String getUserName()
public String getUserNameDd()
public DropDownModel getUserNameDdWM()
public EditBoxModel getUserNameWM()
public void populateRows(GridModel rows)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setEMailAddress(String eMailAddress)
public void setEMailAddressDd(String eMailAddressDd)
public void setEMailAddressDdWV(String value)
public void setEMailAddressWV(String value)
public void setFirstName(String firstName)
public void setFirstNameDd(String firstNameDd)
public void setFirstNameDdWV(String value)
public void setFirstNameWV(String value)
public void setLastName(String lastName)
public void setLastNameDd(String lastNameDd)
public void setLastNameDdWV(String value)
public void setLastNameWV(String value)
public void setPassword(String password)
public void setPasswordDd(String passwordDd)
public void setPasswordDdWV(String value)
public void setPasswordWV(String value)
public void setProcessedDatetime(String processedDatetime)
public void setProcessedDatetimeDd(String processedDatetimeDd)
public void setProcessedDatetimeDdWV(String value)
public void setProcessedDatetimeWV(String value)
public void setProcessedUserId(String processedUserId)
public void setProcessedUserIdDd(String processedUserIdDd)
public void setProcessedUserIdDdWV(String value)
public void setProcessedUserIdWV(String value)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRemarksDdWV(String value)
public void setRemarksWV(String value)
public void setRequestId(String requestId)
public void setRequestIdDd(String requestIdDd)
public void setRequestIdDdWV(String value)
public void setRequestIdWV(String value)
public void setSecurityAnswer(String securityAnswer)
public void setSecurityAnswerDd(String securityAnswerDd)
public void setSecurityAnswerDdWV(String value)
public void setSecurityAnswerWV(String value)
public void setSecurityQuestion(String securityQuestion)
public void setSecurityQuestionDd(String securityQuestionDd)
public void setSecurityQuestionDdWV(String value)
public void setSecurityQuestionWV(String value)
public void setStatus(String status)
public void setStatusDd(String statusDd)
public void setStatusDdWV(String value)
public void setStatusWV(String value)
public void setUserName(String userName)
public void setUserNameDd(String userNameDd)
public void setUserNameDdWV(String value)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.IUserRequestMaintenance**

```
public UserRequestMaintenanceRetrieveOutDto create(UserRequestMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserRequestMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserRequestMaintenancePrevalidateOutDto prevalidateCreate(UserRequestMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserRequestMaintenancePrevalidateOutDto prevalidateUpdate(UserRequestMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public UserRequestMaintenanceRetrieveOutDto retrieve(UserRequestMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserRequestMaintenanceRetrieveOutDto update(UserRequestMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenanceCreateInDto**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public HeaderDto getHeaderDto()
public java.lang.String getLastName()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public java.lang.String getUrl()
public java.lang.String getUserName()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastName(java.lang.String lastName)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUrl(java.lang.String value)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public java.lang.Long getRequestId()
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public void setRequestId(java.lang.Long requestId)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public java.lang.Long getRequestId()
public void setHeaderDto(HeaderDto headerDto)
public void setRequestId(java.lang.Long requestId)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenanceRetrieveOutDto**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.UserRequestMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.tx.UserRequestMaintenanceTx**

```
public UserRequestMaintenanceRetrieveOutDto create(UserRequestMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserRequestMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(UserRequestMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public UserRequestMaintenancePrevalidateOutDto prevalidateCreate(UserRequestMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public UserRequestMaintenancePrevalidateOutDto prevalidateUpdate(UserRequestMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public static String replaceAll(String input, String forReplace, String replaceWith)
public UserRequestMaintenanceRetrieveOutDto retrieve(UserRequestMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public UserRequestMaintenanceRetrieveOutDto update(UserRequestMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceAction**

No public methods.
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword1()
public java.lang.String getPassword2()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public java.lang.String getStatus()
public String getUrl()
public java.lang.String getUserName()
public void quit()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setPassword1(java.lang.String password1)
public void setPassword2(java.lang.String password2)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setStatus(java.lang.String status)
public void setUrl(String value)
public void setUserName(java.lang.String userName)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public java.lang.String getEMailAddress()
public EditBoxModel getEMailAddressWM()
public java.lang.String getFirstName()
public EditBoxModel getFirstNameWM()
public java.lang.String getLastName()
public EditBoxModel getLastNameWM()
public java.lang.String getPassword1()
public EditBoxModel getPassword1WM()
public java.lang.String getPassword2()
public EditBoxModel getPassword2WM()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public DateTimeModel getProcessedDatetimeWM()
public java.lang.String getProcessedUserId()
public EditBoxModel getProcessedUserIdWM()
public java.lang.String getRemarks()
public EditBoxModel getRemarksWM()
public java.lang.Long getRequestId()
public EditBoxModel getRequestIdWM()
public java.lang.String getSecurityAnswer()
public EditBoxModel getSecurityAnswerWM()
public java.lang.Long getSecurityQuestion()
public java.lang.String getSecurityQuestion1()
public DropDownModel getSecurityQuestion1WM()
public EditBoxModel getSecurityQuestionWM()
public java.lang.String getStatus()
public DropDownModel getStatusWM()
public java.lang.String getUserName()
public EditBoxModel getUserNameWM()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setEMailAddress(java.lang.String eMailAddress)
public void setEMailAddressWV(String value)
public void setFirstName(java.lang.String firstName)
public void setFirstNameWV(String value)
public void setLastName(java.lang.String lastName)
public void setLastNameWV(String value)
public void setPassword1(java.lang.String password1)
public void setPassword1WV(String value)
public void setPassword2(java.lang.String password2)
public void setPassword2WV(String value)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedDatetimeWV(String value)
public void setProcessedUserId(java.lang.String processedUserId)
public void setProcessedUserIdWV(String value)
public void setRemarks(java.lang.String remarks)
public void setRemarksWV(String value)
public void setRequestId(java.lang.Long requestId)
public void setRequestIdWV(String value)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityAnswerWV(String value)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setSecurityQuestion1(java.lang.String securityQuestion1)
public void setSecurityQuestion1WV(String value)
public void setSecurityQuestionWV(String value)
public void setStatus(java.lang.String status)
public void setStatusWV(String value)
public void setUserName(java.lang.String userName)
public void setUserNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenanceunsecured.ui.UserRequestMaintenanceComponent**

No public methods.
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.IUserRequestViewer**

```
public void destroy()
public UserRequestViewerOutDto read(UserRequestViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto.UserRequestViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.Long getRequestId()
public void setHeaderDto(HeaderDto headerDto)
public void setRequestId(java.lang.Long requestId)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto.UserRequestViewerOutDto**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEMailAddress(java.lang.String eMailAddress)
public void setFirstName(java.lang.String firstName)
public void setLastName(java.lang.String lastName)
public void setPassword(java.lang.String password)
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
public void setProcessedUserId(java.lang.String processedUserId)
public void setRemarks(java.lang.String remarks)
public void setRequestId(java.lang.Long requestId)
public void setSecurityAnswer(java.lang.String securityAnswer)
public void setSecurityQuestion(java.lang.Long securityQuestion)
public void setStatus(java.lang.String status)
public void setUserName(java.lang.String userName)
public String toString()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.tx.UserRequestViewerTx**

```
public void destroy()
public UserRequestViewerOutDto read(UserRequestViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.ui.UserRequestViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.ui.UserRequestViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.Long getRequestId()
public UserRequestViewerOutDto getUserRequestViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setRequestId(java.lang.Long requestId)
public void setUserRequestViewerOutDto(UserRequestViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.ui.UserRequestViewerForm**

```
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
```
***
**org.jaffa.applications.jaffa.modules.user.domain.UserRequest**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.Long requestId) throws FrameworkException
public static UserRequest findByPK(UOW uow, java.lang.Long requestId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.Long requestId)
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEMailAddress()
public java.lang.String getFirstName()
public java.lang.String getLastName()
public java.lang.String getPassword()
public org.jaffa.datatypes.DateTime getProcessedDatetime()
public java.lang.String getProcessedUserId()
public java.lang.String getRemarks()
public java.lang.Long getRequestId()
public java.lang.String getSecurityAnswer()
public java.lang.Long getSecurityQuestion()
public java.lang.String getStatus()
public java.lang.String getUserName()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEMailAddress(java.lang.String eMailAddress) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFirstName(java.lang.String firstName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastName(java.lang.String lastName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPassword(java.lang.String password) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setProcessedUserId(java.lang.String processedUserId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRequestId(java.lang.Long requestId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setUserName(java.lang.String userName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEMailAddress(java.lang.String eMailAddress) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFirstName(java.lang.String firstName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastName(java.lang.String lastName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePassword(java.lang.String password) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateProcessedUserId(java.lang.String processedUserId) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRequestId(java.lang.Long requestId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateStatus(java.lang.String status) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateUserName(java.lang.String userName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateEMailAddress(java.lang.String eMailAddress) throws ValidationException, FrameworkException
public java.lang.String validateFirstName(java.lang.String firstName) throws ValidationException, FrameworkException
public java.lang.String validateLastName(java.lang.String lastName) throws ValidationException, FrameworkException
public java.lang.String validatePassword(java.lang.String password) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) throws ValidationException, FrameworkException
public java.lang.String validateProcessedUserId(java.lang.String processedUserId) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
public java.lang.Long validateRequestId(java.lang.Long requestId) throws ValidationException, FrameworkException
public java.lang.String validateSecurityAnswer(java.lang.String securityAnswer) throws ValidationException, FrameworkException
public java.lang.Long validateSecurityQuestion(java.lang.Long securityQuestion) throws ValidationException, FrameworkException
public java.lang.String validateStatus(java.lang.String status) throws ValidationException, FrameworkException
public java.lang.String validateUserName(java.lang.String userName) throws ValidationException, FrameworkException
```
***
**org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta**

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
**org.jaffa.applications.jaffa.modules.user.exceptions.RequestException**

No public methods.
***
**org.jaffa.modules.user.services.DummyPortletFilter**

```
public void initUserInfo(UserSession us)
```
***
**org.jaffa.modules.user.services.MockHttpServletRequest**

```
public Object getAttribute(String string)
public Enumeration getAttributeNames()
public String getAuthType()
public String getCharacterEncoding()
public int getContentLength()
public String getContentType()
public String getContextPath()
public Cookie[] getCookies()
public long getDateHeader(String string)
public String getHeader(String string)
public Enumeration getHeaderNames()
public Enumeration getHeaders(String string)
public ServletInputStream getInputStream() throws IOException
public int getIntHeader(String string)
public String getLocalAddr()
public String getLocalName()
public int getLocalPort()
public Locale getLocale()
public Enumeration getLocales()
public String getMethod()
public String getParameter(String string)
public Map getParameterMap()
public Enumeration getParameterNames()
public String[] getParameterValues(String string)
public String getPathInfo()
public String getPathTranslated()
public String getProtocol()
public String getQueryString()
public BufferedReader getReader() throws IOException
public String getRealPath(String string)
public String getRemoteAddr()
public String getRemoteHost()
public int getRemotePort()
public String getRemoteUser()
public RequestDispatcher getRequestDispatcher(String string)
public String getRequestURI()
public StringBuffer getRequestURL()
public String getRequestedSessionId()
public List getRoles()
public String getScheme()
public String getServerName()
public int getServerPort()
public String getServletPath()
public HttpSession getSession(boolean b)
public HttpSession getSession()
public Principal getUserPrincipal()
public boolean isRequestedSessionIdFromCookie()
public boolean isRequestedSessionIdFromURL()
public boolean isRequestedSessionIdFromUrl()
public boolean isRequestedSessionIdValid()
public boolean isSecure()
public boolean isUserInRole(String string)
public void removeAttribute(String string)
public void setAttribute(String string, Object object)
public void setCharacterEncoding(String string) throws UnsupportedEncodingException
```
***
**org.jaffa.modules.user.services.MockHttpSession**

```
public Object getAttribute(String string)
public Enumeration getAttributeNames()
public long getCreationTime()
public String getId()
public long getLastAccessedTime()
public int getMaxInactiveInterval()
public ServletContext getServletContext()
public HttpSessionContext getSessionContext()
public Object getValue(String string)
public String[] getValueNames()
public void invalidate()
public boolean isNew()
public void putValue(String string, Object object)
public void removeAttribute(String string)
public void removeValue(String string)
public void setAttribute(String string, Object object)
public void setMaxInactiveInterval(int i)
```
***
**org.jaffa.modules.user.services.MockPrincipal**

```
public String getName()
```
***
**org.jaffa.modules.user.services.UserContextWrapper**

```
public UserContext getUserContext()
public void unsetContext()
```
***
**org.jaffa.modules.user.services.UserContextWrapperFactory**

```
public static UserContextWrapper instance(String userId) throws UserSessionSetupException
public static UserContextWrapper instance(UserContext userContext) throws UserSessionSetupException
```
***
**org.jaffa.modules.user.services.UserContextWrapperTest**

```
public static Test suite()
public void testContextManager() throws Exception
public void testLocaleContext() throws Exception
public void testSecurityContext() throws Exception
public void testSessionManager() throws Exception
public void testUserRoles() throws Exception
public void testVariationContext() throws Exception
```
***
**org.jaffa.modules.user.services.UserDataWrapper**

No public methods.
***
**org.jaffa.unittest.AbstractDataWrapper**

No public methods.
***
**org.jaffa.unittest.ContextWrapper**

No public methods.
***
**org.jaffa.unittest.InitLog4J**

```
public static void init()
```
***
**org.jaffa.unittest.UnitTestUtil**

```
public static boolean areFilesIdentical(File f1, File f2) throws Exception
public static boolean areFilesIdentical(String one, String two) throws Exception
public static double areFilesSimilar(File f1, File f2) throws Exception
public static double areFilesSimilar(String fileOne, String fileTwo) throws Exception
public static double areImagesSimilar(String one, String two) throws Exception
public static double areImagesSimilar(File f1, File f2) throws Exception
public static double arePdfsSimilar(File f1, File f2) throws Exception
public static double arePdfsSimilar(String one, String two) throws Exception
public static File convertPdfToJpg(File f) throws Exception
public static String getDataDirectory()
public static String getTempDirectory()
```
