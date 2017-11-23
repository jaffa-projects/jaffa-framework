# jaffa-components-admin Public Methods

***
**.AllTests**

```
public static void main(String[] args)
public static Test suite()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui.BusinessFunctionAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui.BusinessFunctionComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public FormKey getBusinessFunctionFormKey()
public String getFileContents()
public void setFileContents(String fileContents)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui.BusinessFunctionForm**

```
public String getFileContents()
public EditBoxModel getFileContentsWM()
public void setFileContents(String fileContents)
public void setFileContentsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.businessfunction.ui.exceptions.BusinessFunctionException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public FormKey getCheckPolicyReportFormKey()
public HashMap getComponentErrorMap()
public HashMap getRoleBFMap()
public HashMap getRoleErrorMap()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyForm**

```
public HashMap getComponentErrorMap()
public HashMap getRoleErrorMap()
public HashMap getRoleMap()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.exceptions.CheckPolicyException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.DefaultValueEditorAction**

```
public FormKey do_Cancel_Clicked()
public FormKey do_Components_Clicked(String rowNum)
public FormKey do_Components_Update_Clicked(String rowNum)
public FormKey do_Finish_Clicked()
public FormKey do_Next_Clicked()
public FormKey do_Previous_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.DefaultValueEditorComponent**

```
public String determineAndSetNextScreen()
public String determineAndSetPreviousScreen()
public String determineCurrentScreen()
public FormKey determineFormKey()
public String determineNextScreen()
public String determinePreviousScreen()
public FormKey display() throws ApplicationExceptions, FrameworkException
public List[] getComponents()
public int getCurrentScreenCounter()
public java.lang.String getDefaultValueFile()
public java.lang.String getDefaultValues()
public String[] getScreens()
public boolean isFileUpdateable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setDefaultValueFile(java.lang.String defaultValueFile)
public void setDefaultValues(java.lang.String defaultValues)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.DefaultValueEditorForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public boolean doValidate1(HttpServletRequest request)
public GridModel getComponentsWM()
public int getCurrentScreenCounter()
public java.lang.String getDefaultValueFile()
public java.lang.String getDefaultValues()
public EditBoxModel getDefaultValuesWM()
public boolean isFileUpdateable()
public boolean isNextActionAvailable()
public boolean isPreviousActionAvailable()
public void setComponentsWV(String value)
public void setCurrentScreenCounter(int currentScreenCounter)
public void setDefaultValueFile(java.lang.String defaultValueFile)
public void setDefaultValues(java.lang.String defaultValues)
public void setDefaultValuesWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.exceptions.DefaultValueEditorException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileBean**

```
public int compareTo(Object obj)
public File getFile()
public String getFormattedSize()
public DateTime getLastModified()
public String getName()
public long getSize()
public boolean isAccessable()
public boolean isDeleteAllowed()
public boolean isRenameAllowed()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_FileTree_Download_Clicked(String rowId)
public FormKey do_FileTree_GetChildren(String rowId)
public FormKey do_Refresh_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerBean**

```
public void addFile(File file, boolean canAccess, boolean canRename, boolean canDelete)
public void addFolder(File folder, boolean canAccess, boolean canRename, boolean canDelete)
public FileBean[] getFiles()
public String getFolderName()
public FolderBean[] getFolders()
public boolean isParentAvailable()
public boolean isUploadAllowed()
public void setFolderName(String folderName)
public void setParentAvailable(boolean parentAvailable)
public void setUploadAllowed(boolean uploadAllowed)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerComponent**

```
public boolean canDelete(String relName, File f)
public boolean canDownload(String relName, File f)
public FormKey display() throws FrameworkException, ApplicationExceptions
public FormKey getCurrentFormKey()
public String getPathName()
public static boolean inRange(String name, String[] includes, String[] excludes)
public static boolean inRole(String[] includes, String[] excludes)
public static String[] parseRegex(String list)
public void setPathName(String pathName)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerDownload**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileExplorerForm**

```
public boolean doValidate() throws FrameworkException, ApplicationExceptions
public void expandRow(String rowId)
public GridModel getFileTreeWM()
public String getPathName()
public String getReturnMessage()
public void setFileTreeWV(String value)
public void setReturnMessage(String returnMessage)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FileFilter**

```
public boolean accept(File dir, String name)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FolderBean**

```
public int compareTo(Object obj)
public File getFile()
public DateTime getLastModified()
public String getName()
public boolean isAccessable()
public boolean isDeleteAllowed()
public boolean isRenameAllowed()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.FolderFilter**

```
public boolean accept(File dir, String name)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.LabelEditorAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
public FormKey do_SyncToSource_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.LabelEditorComponent**

```
public FormKey display() throws FrameworkException
public Boolean getDisplayOverridesOnly()
public FormKey getLabelEditorFormKey()
public String getLabelFilter()
public Map getLabels()
public String getSearchPathForSourceFragments()
public String getSourceFragmentName()
public void setDisplayOverridesOnly(Boolean displayOverridesOnly)
public void setLabelFilter(String labelFilter)
public void setSearchPathForSourceFragments(String searchPathForSourceFragments)
public void setSourceFragmentName(String sourceFragmentName)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.LabelEditorForm**

```
public boolean doValidate()
public Boolean getDisplayOverridesOnly()
public SimpleWidgetModel getDisplayOverridesOnlyWM()
public String getLabelFilter()
public SimpleWidgetModel getLabelFilterWM()
public GridModel getRowsWM()
public String getSearchPathForSourceFragments()
public SimpleWidgetModel getSearchPathForSourceFragmentsWM()
public String getSourceFragmentName()
public SimpleWidgetModel getSourceFragmentNameWM()
public void setDisplayOverridesOnly(Boolean displayOverridesOnly)
public void setDisplayOverridesOnlyWV(String value)
public void setLabelFilter(String labelFilter)
public void setLabelFilterWV(String value)
public void setRowsWV(String value)
public void setSearchPathForSourceFragments(String searchPathForSourceFragments)
public void setSearchPathForSourceFragmentsWV(String value)
public void setSourceFragmentName(String sourceFragmentName)
public void setSourceFragmentNameWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.exceptions.LabelEditorException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.Log4jConfigAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.Log4jConfigComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public FormKey getLog4jConfigFormKey()
public void setFileContents(String fileContents)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.Log4jConfigForm**

```
public boolean doValidate() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public EditBoxModel getFileContentsWM()
public void setFileContents(String fileContents)
public void setFileContentsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.exceptions.Log4jConfigException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.menunavigation.ui.MenuNavigationAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.menunavigation.ui.MenuNavigationComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public FormKey getMenuNavigationFormKey()
public void setFileContents(String fileContents)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.menunavigation.ui.MenuNavigationForm**

```
public boolean doValidate() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public EditBoxModel getFileContentsWM()
public void setFileContents(String fileContents)
public void setFileContentsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.menunavigation.ui.exceptions.MenuNavigationException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.roleseditor.ui.RolesEditorAction**

```
public FormKey do_CheckPolicy_Clicked()
public FormKey do_Close_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.roleseditor.ui.RolesEditorComponent**

```
public FormKey display() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public FormKey getRolesEditorFormKey()
public FormKey runCheckPolicy() throws ApplicationExceptions, FrameworkException
public void setFileContents(String fileContents)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.roleseditor.ui.RolesEditorForm**

```
public boolean doValidate() throws FrameworkException, ApplicationExceptions
public String getFileContents()
public EditBoxModel getFileContentsWM()
public void setFileContents(String fileContents)
public void setFileContentsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.roleseditor.ui.exceptions.RolesEditorException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui.ValidationRulesEditorAction**

```
public FormKey do_Cancel_Clicked()
public FormKey do_CoreRulesUrl_Clicked()
public FormKey do_Finish_Clicked()
public FormKey do_Next_Clicked()
public FormKey do_Previous_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Rules_Clicked(String rowId)
public FormKey do_Rules_Update_Clicked(String rowId)
public FormKey do_Save_Clicked()
public FormKey do_ValidatorsUrls_Clicked(String rowId)
public FormKey do_ValidatorsUrls_Update_Clicked(String rowId)
public FormKey do_refresh()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui.ValidationRulesEditorComponent**

```
public String determineAndSetNextScreen()
public String determineAndSetPreviousScreen()
public String determineCurrentScreen()
public FormKey determineFormKey()
public String determineNextScreen()
public String determinePreviousScreen()
public FormKey display() throws ApplicationExceptions, FrameworkException
public int getCurrentScreenCounter()
public String getFileContents()
public String[] getScreens()
public java.lang.String getValidationRulesFile()
public boolean isFileUpdateable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setFileContents(String fileContents)
public void setValidationRulesFile(java.lang.String validationRulesFile)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui.ValidationRulesEditorForm**

```
public boolean doValidate(HttpServletRequest request) throws FrameworkException, ApplicationExceptions
public boolean doValidate0(HttpServletRequest request)
public boolean doValidate1(HttpServletRequest request) throws FrameworkException, ApplicationExceptions
public String getCoreRulesUrl()
public int getCurrentScreenCounter()
public String getFileContents()
public EditBoxModel getFileContentsWM()
public GridModel getRulesWM()
public java.lang.String getValidationRulesFile()
public GridModel getValidatorsUrlsWM()
public String getVariationRulesDir()
public boolean isFileUpdateable()
public boolean isNextActionAvailable()
public boolean isPreviousActionAvailable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setFileContents(String fileContents)
public void setFileContentsWV(String value)
public void setRulesWV(String value)
public void setValidationRulesFile(java.lang.String validationRulesFile)
public void setValidatorsUrlsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui.ViewerHyperlinkConfigAction**

```
public FormKey do_Cancel_Clicked()
public FormKey do_Finish_Clicked()
public FormKey do_Next_Clicked()
public FormKey do_Previous_Clicked()
public FormKey do_Refresh_Clicked()
public FormKey do_Save_Clicked()
public FormKey do_refresh()
```
***
**org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui.ViewerHyperlinkConfigComponent**

```
public String determineAndSetNextScreen()
public String determineAndSetPreviousScreen()
public String determineCurrentScreen()
public FormKey determineFormKey()
public String determineNextScreen()
public String determinePreviousScreen()
public FormKey display() throws ApplicationExceptions, FrameworkException
public int getCurrentScreenCounter()
public java.lang.String getDomainFieldViewerComponentMappingContents()
public java.lang.String getDomainFieldViewerComponentMappingFileName()
public java.lang.String getKeyFieldPerViewerComponentContents()
public java.lang.String getKeyFieldPerViewerComponentFileName()
public String[] getScreens()
public boolean isFile0Updateable()
public boolean isFile1Updateable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setDomainFieldViewerComponentMappingContents(java.lang.String domainFieldViewerComponentMappingContents)
public void setKeyFieldPerViewerComponentContents(java.lang.String keyFieldPerViewerComponentContents)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui.ViewerHyperlinkConfigForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public boolean doValidate1(HttpServletRequest request)
public int getCurrentScreenCounter()
public java.lang.String getDomainFieldViewerComponentMappingContents()
public EditBoxModel getDomainFieldViewerComponentMappingContentsWM()
public java.lang.String getDomainFieldViewerComponentMappingFileName()
public java.lang.String getKeyFieldPerViewerComponentContents()
public EditBoxModel getKeyFieldPerViewerComponentContentsWM()
public java.lang.String getKeyFieldPerViewerComponentFileName()
public boolean isFile0Updateable()
public boolean isFile1Updateable()
public boolean isNextActionAvailable()
public boolean isPreviousActionAvailable()
public void setCurrentScreenCounter(int currentScreenCounter)
public void setDomainFieldViewerComponentMappingContents(java.lang.String domainFieldViewerComponentMappingContents)
public void setDomainFieldViewerComponentMappingContentsWV(String value)
public void setKeyFieldPerViewerComponentContents(java.lang.String keyFieldPerViewerComponentContents)
public void setKeyFieldPerViewerComponentContentsWV(String value)
```
***
**org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui.exceptions.ViewerHyperlinkConfigException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.exceptions.ConfigException**

No public methods.
***
**org.jaffa.applications.jaffa.modules.admin.exceptions.RequestException**

No public methods.
