# jaffa-console Public Methods

***
**org.jaffa.qm.finder.apis.ExcelExportService**

```
public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child, String sheetName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
public static Object[] jsonArrayToBeanArray(String input, String beanClassName) throws ClassNotFoundException
public static Object jsonToBean(String input, String beanClassName) throws ClassNotFoundException
```
***
**org.jaffa.sc.RMIContextFactory**

```
public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException
```
***
**org.jaffa.sc.apis.DomainModelDocService**

```
public JSONArray loadTree()
```
***
**org.jaffa.sc.apis.SOAEventMetaDataService**

```
public JSONArray loadTree() throws JAXBException, MalformedURLException
public void setEnabled(String eventName, boolean isEnabled)
```
***
**org.jaffa.sc.apis.TreeNode**

```
public void addChild(TreeNode node)
public List<TreeNode> getChild()
public String getClassName()
public String getDbTableName()
public String getDescription()
public String getIconCls()
public Integer getId()
public boolean getIsEnabled()
public boolean getIsSelectable()
public String getLabel()
public String getServiceName()
public String getSoaEventName()
public String getSoaEventParams()
public String getText()
public String getUniqueName()
public boolean isIsClass()
public boolean isLeaf()
public void setClassName(String className)
public void setDbTableName(String dbTableName)
public void setDescription(String description)
public void setIconCls(String iconCls)
public void setId(Integer id)
public void setIsClass(boolean isClass)
public void setIsEnabled(boolean isEnabled)
public void setIsSelectable(boolean isSelectable)
public void setLabel(String label)
public void setLeaf(boolean leaf)
public void setServiceName(String serviceName)
public void setSoaEventName(String soaEventName)
public void setSoaEventParams(String soaEventParams)
public void setText(String text)
public void setUniqueName(String uniqueName)
public JSONObject toJson()
```
