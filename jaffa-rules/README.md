# jaffa-rules Public Methods

***
**org.jaffa.config.JaffaRulesConfig**

```
public AopXmlLoader aopFolderWatcher(Environment env) throws JaffaRulesFrameworkException
public CandidateKeyValidator candidateKeyValidator()
public CaseTypeValidator caseTypeValidator()
public CommentValidator commentValidator()
public ForeignKeyValidator foreignKeyValidator()
public GenericForeignKeyValidator genericForeignKeyValidator()
public InListValidator inListValidator()
public InitializerFactory initializerFactory()
public MandatoryValidator mandatoryValidator()
public MaxLengthValidator maxLengthValidator()
public MaxValueValidator maxValueValidator()
public MinLengthValidator minLengthValidator()
public MinValueValidator minValueValidator()
public NotInListValidator notInListValidator()
public PartialForeignKeyValidator partialForeignKeyValidator()
public PatternValidator patternValidator()
public PrimaryKeyValidator primaryKeyValidator()
public IRuleEvaluator ruleHelper()
public RuleValidatorFactory ruleValidatorFactory()
```
***
**org.jaffa.config.PropertyPlaceholderConfig**

```
public PropertyPlaceholderConfigurer conversionService()
```
***
**org.jaffa.config.PropertyPlaceholderConfigTest**

```
public void testReadConfig() throws Exception
```
***
**org.jaffa.modules.setup.domain.ValidFieldValue**

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
**org.jaffa.persistence.FakeModel**

```
public String getField1()
public String getField2()
public Validator getValidator()
public void setField1(String field1) throws ReadOnlyObjectException, AlreadyLockedObjectException
public void setField2(String field2)
public void setValidator(Validator validator)
public void validate()
```
***
**org.jaffa.persistence.FakeModelMeta**

No public methods.
***
**org.jaffa.persistence.UOW**

```
public void acquireLock(Object object) throws AlreadyLockedObjectException
public void add(Object object) throws AddFailedException
public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException
public void delete(Object object) throws DeleteFailedException
public void flush() throws UOWException
public Class getActualPersistentClass(Object persistentObject)
public boolean isActive()
public IPersistent newPersistentInstance(Class persistentClass)
public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException
public void rollback() throws RollbackFailedException
public void update(Object object) throws UpdateFailedException
```
***
**org.jaffa.rules.AccessTest**

```
public static void main(String[] args)
public void testAccessField()
public void testAccessHiddenField()
public void testAccessReadOnlyField()
public void testHiddenPropertyInspection()
public void testInitializeHiddenField()
public void testInitializeReadOnlyField()
public void testPropertyInspection()
public void testReadOnlyPropertyInspection()
public void testUpdateField()
public void testUpdateHiddenField()
public void testUpdateReadOnlyField()
```
***
**org.jaffa.rules.AopLoaderBootstrapServlet**

No public methods.
***
**org.jaffa.rules.AopXmlLoader**

No public methods.
***
**org.jaffa.rules.DataSecurityTest**

```
public void dataSecurityEnabled() throws FrameworkException, ApplicationExceptions
public void dataSecurityNotEnabled() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.Dummy**

No public methods.
***
**org.jaffa.rules.ExecutionOrderTest**

```
public static void main(String[] args)
public void test2DifferentClasses()
public void test2DifferentInstances()
public void testAOPInjection()
public void testInterceptors()
```
***
**org.jaffa.rules.ExecutionRealmTest**

```
public static void main(String[] args)
public void testPassForNow()
```
***
**org.jaffa.rules.ExtensionTest**

```
public static void main(String[] args)
public void testAOPInjection()
public void testCompositeForeignKey()
public void testFormat()
public void testFormatUsingPropertyTranslator()
public void testLabelAtObjectLevelPassingClass()
public void testLabelPassingClass()
public void testLayout()
public void testMaxLengthOverride()
public void testMinAndMaxValue()
public void testMinLengthOverride()
```
***
**org.jaffa.rules.FlexFieldsTest**

```
public void testDelete() throws FrameworkException, ApplicationExceptions
public void testHasFlexFields() throws FrameworkException, ApplicationExceptions
public void testIsModified() throws FrameworkException, ApplicationExceptions
public void testNoFlexFields() throws FrameworkException, ApplicationExceptions
public void testPostAdd() throws FrameworkException, ApplicationExceptions
public void testValidate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.InitializeTest**

```
public void testAOPInjection()
```
***
**org.jaffa.rules.JaffaRulesFrameworkException**

No public methods.
***
**org.jaffa.rules.JavaInheritanceTest**

```
public static void main(String[] args)
public void testLabelUsingIntrospector()
```
***
**org.jaffa.rules.RuleActorFactory**

```
public ActorT getActor(Object object)
```
***
**org.jaffa.rules.RulesInheritanceTest**

```
public static void main(String[] args)
public void testPassForNow()
```
***
**org.jaffa.rules.ScriptTest**

```
public static void main(String[] args)
public void testPassForNow()
```
***
**org.jaffa.rules.SelectiveInheritanceTest**

```
public static void main(String[] args)
public void testSuperRuleFiltering()
```
***
**org.jaffa.rules.StringsTest**

```
public static void main(String[] args)
public void testAOPInjection()
public void testMixedThenUpperCaseField()
```
***
**org.jaffa.rules.TestConfig**

```
public Realm businessRealm()
public Rule candidateKeyRule()
public Rule caseTypeRule()
public Rule commentRule()
public Rule deleteDomainInfoRule()
public FakeModel fakeModel()
public ClassMetaData fakeModelMeta()
public Rule foreignKeyRule()
public Rule genericForeignKeyRule()
public Rule hiddenRule()
public Rule inListRule()
public Rule initializeRule()
public Rule labelRule()
public Rule layoutRule()
public Rule lookupRule()
public Rule mandatoryRule()
public Rule maxLengthRule()
public Rule maxValueRule()
public Rule minLengthRule()
public Rule minValueRule()
public TestModel model()
public Rule notInListRule()
public Rule partialForeignKeyRule()
public Rule patternRule()
public Rule primaryKeyRule()
public Rule readOnlyRule()
public ClassMetaData testModelMeta()
```
***
**org.jaffa.rules.TestHelper**

```
public static void setupRepos() throws JaffaRulesFrameworkException
public static void shutdownRepos()
```
***
**org.jaffa.rules.TestModel**

```
public static boolean exists(UOW uow, Object key)
public String getField1()
public Integer getField2()
public String getOriginalValue(String property)
public static boolean isExistsInDb()
public static void setExistsInDb(boolean existsInDb)
public void setField1(String field1)
public void setField2(Integer field2)
public void setOriginalValue(String originalValue)
public void validate()
```
***
**org.jaffa.rules.TestSupport**

```
public static IPersistenceEngine getEngine()
public static void setupContext() throws Exception
```
***
**org.jaffa.rules.TestValueClass**

```
public String getLegalValue()
```
***
**org.jaffa.rules.VariationTest**

```
public static void main(String[] args)
public void testClassLevelVariation()
public void testCoreRules()
public void testNoVariationRules()
public void testOverridingUriMatchingVariation()
public void testPropertyLevelVariation()
public void testRuleLevelVariation()
```
***
**org.jaffa.rules.VirtualClassTest**

```
public static void main(String[] args)
public void testVirtualClass()
```
***
**org.jaffa.rules.commons.AbstractLoader**

```
public abstract void clear() throws JaffaRulesFrameworkException
public void load(File f) throws JaffaRulesFrameworkException
public void load(URL url) throws JaffaRulesFrameworkException
public void load(String uri) throws JaffaRulesFrameworkException
public void load(Reader characterStream, String source) throws JaffaRulesFrameworkException
public void load(Document document, String source) throws JaffaRulesFrameworkException
public abstract void load(Element metadataElement, String source) throws JaffaRulesFrameworkException
public void unload(File f) throws JaffaRulesFrameworkException
public void unload(URL url) throws JaffaRulesFrameworkException
public abstract void unload(String uri) throws JaffaRulesFrameworkException
```
***
**org.jaffa.rules.commons.AopConstants**

```
public static DocumentBuilder createParser() throws ParserConfigurationException
public static Map<XPathExpression, AbstractLoader> createRepoMap() throws XPathExpressionException
```
***
**org.jaffa.rules.commons.MetaData**

```
public Integer getLine()
public String getName()
public String getSource()
public void setLine(Integer line)
public void setName(String name)
public void setSource(String source)
public String toString()
```
***
**org.jaffa.rules.dto.ClassMetaDataCriteria**

```
public String getClassName()
public String[] getHasClassRules()
public Boolean getReturnProperties()
public String getSourceFileName()
public void setClassName(String className)
public void setHasClassRules(String[] hasClassRules)
public void setReturnProperties(Boolean returnProperties)
public void setSourceFileName(String sourceFileName)
```
***
**org.jaffa.rules.dto.ClassMetaDataDto**

```
public String getCondition()
public String getExecutionRealm()
public String getExtendsClass()
public String getLanguage()
public String getName()
public List<PropertyMetaDataDto> getProperties()
public List<RuleMetaDataDto> getRules()
public String getSourceFileName()
public String getVariation()
public void setCondition(String condition)
public void setExecutionRealm(String executionRealm)
public void setExtendsClass(String extendsClass)
public void setLanguage(String language)
public void setName(String name)
public void setProperties(List<PropertyMetaDataDto> properties)
public void setRules(List<RuleMetaDataDto> rules)
public void setSourceFileName(String sourceFileName)
public void setVariation(String variation)
```
***
**org.jaffa.rules.dto.FlexClassMetaData**

```
public String getCondition()
public String getDomainClass()
public String getDomainLabel()
public String getDomainLabelToken()
public String getDomainName()
public String getFlexClass()
public String getFlexLabel()
public String getFlexLabelToken()
public String getFlexName()
public String getFlexSourceFile()
public void setCondition(String condition)
public void setDomainClass(String domainClass)
public void setDomainLabel(String domainLabel)
public void setDomainLabelToken(String domainLabelToken)
public void setDomainName(String domainName)
public void setFlexClass(String flexClass)
public void setFlexLabel(String flexLabel)
public void setFlexLabelToken(String flexLabelToken)
public void setFlexName(String flexName)
public void setFlexSourceFile(String flexSourceFile)
```
***
**org.jaffa.rules.dto.PropertyMetaDataDto**

```
public String getCondition()
public String getExtendsClass()
public String getExtendsProperty()
public String getLanguage()
public String getPropertyName()
public List<RuleMetaDataDto> getRules()
public String getVariation()
public void setCondition(String condition)
public void setExtendsClass(String extendsClass)
public void setExtendsProperty(String extendsProperty)
public void setLanguage(String language)
public void setPropertyName(String propertyName)
public void setRules(List<RuleMetaDataDto> rules)
public void setVariation(String variation)
```
***
**org.jaffa.rules.dto.RuleMetaDataDto**

```
public Map<String, String> getParameters()
public String getRuleName()
public String getText()
public String getVariation()
public String[] getVariationArray()
public void setParameters(Map<String, String> parameters)
public void setRuleName(String ruleName)
public void setText(String text)
public void setVariation(String variation)
public void setVariationArray(String[] variationArray)
```
***
**org.jaffa.rules.initializers.FieldInitializer**

```
public T initialize(T object) throws FrameworkException
```
***
**org.jaffa.rules.initializers.FieldInitializerTest**

```
public void setup() throws Exception
public void testConditionalInitialization() throws FrameworkException, ApplicationExceptions
public void testInitializer() throws FrameworkException
public void testInterceptor() throws Throwable
public void testNullPropertyRuleMap() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.initializers.RuleInitializerFactory**

```
public Initializer<T> getInitializer(T object)
```
***
**org.jaffa.rules.initializers.RuleInitializerFactoryTest**

```
public void before()
public void testCaching()
public void testDifferent() throws JaffaRulesFrameworkException
public void testInitializer() throws FrameworkException, ApplicationException
public void testNoInitializer()
public void testVariation() throws JaffaRulesFrameworkException
```
***
**org.jaffa.rules.interceptors.CxfFunctionGuardInterceptor**

```
public void handleMessage(Message message) throws Fault
```
***
**org.jaffa.rules.jbossaop.mixins.Mixin**

```
public boolean equals(Object obj)
public void generateIntroduction(BufferedWriter writer, String className) throws IOException
public String getConstruction()
public String[] getInterfaces()
public String getMixinClass()
public int hashCode()
public void setConstruction(String construction)
public void setInterfaces(String[] interfaces)
public void setMixinClass(String mixinClass)
public String toString()
```
***
**org.jaffa.rules.meta.ClassMetaData**

```
public void addProperty(PropertyMetaData property)
public void addRule(RuleMetaData rule)
public ClassMetaData deleteDomainInfo(String name)
public List<RuleMetaData> getClassRules()
public String getCondition()
public String getExecutionRealm()
public String getExtendsClass()
public String getLabelFormat()
public String getLanguage()
public List<PropertyMetaData> getProperties()
public List<RuleMetaData> getRules()
public String getVariation()
public ClassMetaData label(String value)
public PropertyMetaData property(String name)
public PropertyMetaData property(String name, Class<?> extendsClass)
public PropertyMetaData property(String name, Class<?> extendsClass, String extendsProperty)
public PropertyMetaData property(String name, Class<?> extendsClass, String extendsProperty, String superExcludes)
public void register()
public ClassMetaData rule(String name, String value, String condition, String... params)
public void setCondition(String condition)
public void setExecutionRealm(String executionRealm)
public void setExtendsClass(String extendsClass)
public void setLabelFormat(String labelFormat)
public void setLanguage(String language)
public void setVariation(String variation)
public String toString()
```
***
**org.jaffa.rules.meta.ClassMetaDataTest**

```
public void testConstructionFromClass()
public void testProperty()
public void testRegister()
```
***
**org.jaffa.rules.meta.ClassRuleKey**

```
public Object clone()
public int compareTo(ClassRuleKey obj)
public boolean equals(Object obj)
public String getClassName()
public String getRuleName()
public int hashCode()
public String toString()
```
***
**org.jaffa.rules.meta.MetaDataAuditIntrospector**

```
public Map<String, Properties> getAuditInfoForProperties()
public String toString()
```
***
**org.jaffa.rules.meta.MetaDataIntrospector**

```
public Properties findInfo(String className, String propertyName, Object obj, String ruleName)
public String format(Object property)
public String getAnnotation()
public Properties getAuditInfo()
public Map<String, Properties> getAuditInfoForProperties()
public Properties getBaseDomainInfo()
public String getCaseType()
public String getClientRule()
public String getCommentStyle()
public Properties[] getDeclaredFlexInfo()
public Properties getDeleteDomainInfo()
public Properties getDomainInfo()
public Properties getFlexInfo()
public Map<String, Properties> getFlexInfoForProperties()
public Properties getForeignKeyInfo()
public Properties getHyperlinkInfo()
public Map<String, String> getInListValues()
public static Map<String, String> getInListValues(RuleMetaData rule)
public Map<String, Properties> getInfoForProperties()
public String getLabel()
public String getLayout()
public Integer getMaxFracLength()
public Integer getMaxLength()
public Object getMaxValue()
public List<Properties> getMetaDataByRule(String ruleName)
public Integer getMinLength()
public Object getMinValue()
public String[] getPattern()
public String[] getPrimaryKey()
public Properties getPropertyInfo()
public Class getPropertyType()
public Map getServiceInfo()
public boolean isHidden()
public boolean isMandatory()
public boolean isReadOnly()
public Properties ruleToProperties(RuleMetaData rule)
public String toString()
```
***
**org.jaffa.rules.meta.MetaDataRepository**

```
public void addClassMetaData(ClassMetaData cmd)
public synchronized void clear() throws JaffaRulesFrameworkException
public List<ClassMetaData> getClassMetaDataListByClassName(String className)
public List<ClassMetaData> getClassMetaDataListBySource(String source)
public String[] getClassNamesByRuleName(String ruleName)
public Map<String, List<RuleMetaData>> getPropertyRuleMap(String className, String ruleName)
public Map<String, List<ClassMetaData>> getRepository()
public List<RuleMetaData> getRuleList(String className, String propertyName, String ruleName)
public static MetaDataRepository instance()
public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException
public synchronized void unload(String uri) throws JaffaRulesFrameworkException
```
***
**org.jaffa.rules.meta.MetaDataRulesEngine**

```
public void clearConditionEvaluationCache()
public IObjectRuleIntrospector getAuditRuleIntrospector(String className)
public String[] getClassNamesByRuleName(String ruleName)
public static Map<Object, Map<String, Boolean>> getConditionEvaluationCache()
public IObjectRuleIntrospector getObjectRuleIntrospector(Object obj)
public IObjectRuleIntrospector getObjectRuleIntrospector(Class clazz)
public IObjectRuleIntrospector getObjectRuleIntrospector(String className, Object obj)
public IPropertyRuleIntrospector getPropertyRuleIntrospector(Object obj, String propertyName)
public IPropertyRuleIntrospector getPropertyRuleIntrospector(Class clazz, String propertyName)
public IPropertyRuleIntrospector getPropertyRuleIntrospector(String className, String propertyName, Object obj)
```
***
**org.jaffa.rules.meta.PropertyMetaData**

```
public void addRule(RuleMetaData rule)
public PropertyMetaData candidateKey(String value, Boolean ignoreNulls)
public PropertyMetaData candidateKey(String value)
public PropertyMetaData caseType(String value)
public PropertyMetaData comment(String value)
public PropertyMetaData comment(String value, RuleCondition<T> condition)
public PropertyMetaData extendsClass(String extendsClass)
public PropertyMetaData extendsProperty(String extendsProperty)
public PropertyMetaData foreignKey(String domainObject)
public PropertyMetaData formatLabel()
public PropertyMetaData formatLabel(String partialName)
public PropertyMetaData formatLabel(String partialName, RuleCondition<T> condition)
public PropertyMetaData genericForeignKey(String tableName, String fieldName)
public PropertyMetaData genericForeignKey(String tableName, String fieldName, RuleCondition<T> condition)
public PropertyMetaData genericForeignKey(String tableName, String fieldName, RuleCondition<T> condition, String domainClassName)
public ClassMetaData getClassMetaData()
public String getCondition()
public String getExtendsClass()
public String getExtendsProperty()
public String getLanguage()
public List<RuleMetaData> getRules(String targetClassName)
public List<RuleMetaData> getRules()
public String getVariation()
public PropertyMetaData hidden()
public PropertyMetaData hidden(RuleCondition<T> condition)
public PropertyMetaData inList(String value)
public PropertyMetaData inList(String value, String errorCode)
public PropertyMetaData initialize(String value)
public PropertyMetaData initialize(String value, String member)
public PropertyMetaData initialize(String value, String member, RuleCondition<T> condition)
public PropertyMetaData initialize(String value, String member, RuleCondition<T> condition, String expression)
public PropertyMetaData label(String value)
public PropertyMetaData label(String value, RuleCondition<T> condition)
public PropertyMetaData layout(String value)
public PropertyMetaData lookup()
public PropertyMetaData lowerCase()
public PropertyMetaData mandatory()
public PropertyMetaData maxLength(Number value)
public PropertyMetaData maxLength(String value)
public PropertyMetaData maxValue(Number value)
public PropertyMetaData maxValue(String value)
public PropertyMetaData minLength(Number value)
public PropertyMetaData minLength(String value)
public PropertyMetaData minValue(Number value)
public PropertyMetaData minValue(String value)
public PropertyMetaData mixedCase()
public PropertyMetaData notInList(String value)
public PropertyMetaData notInList(String value, String errorCode)
public PropertyMetaData partialForeignKey(String domainObject)
public PropertyMetaData partialForeignKey(String domainObject, String propertyName)
public PropertyMetaData pattern(String value)
public PropertyMetaData primaryKey(String value)
public PropertyMetaData readOnly()
public PropertyMetaData readOnly(RuleCondition<T> condition)
public PropertyMetaData rule(String name, String value, RuleCondition<T> condition, String... params)
public void setClassMetaData(ClassMetaData classMetaData)
public void setCondition(String condition)
public void setExtendsClass(String extendsClass)
public void setExtendsProperty(String extendsProperty)
public void setLanguage(String language)
public void setName(String name)
public void setVariation(String variation)
public String toString()
public PropertyMetaData upperCase()
```
***
**org.jaffa.rules.meta.PropertyMetaDataTest**

```
public void testFormatLabel()
```
***
**org.jaffa.rules.meta.RealmRepositoryTest**

```
public void testAddRealm()
```
***
**org.jaffa.rules.meta.RealmTest**

```
public void testRealmConfig()
```
***
**org.jaffa.rules.meta.RuleCondition**

No public methods.
***
**org.jaffa.rules.meta.RuleMetaData**

```
public void addParamIfNonNull(String key, String value)
public void addParameter(String key, String value)
public void addParameters(Map<String, String> parameters)
public boolean evaluateCondition(Object bean) throws Exception
public ClassMetaData getClassMetaData()
public RuleCondition<?> getCondition()
public String getParameter(String key)
public Map<String, String> getParameters()
public PropertyMetaData getPropertyMetaData()
public String getText()
public String getVariation()
public String[] getVariationArray()
public void setClassMetaData(ClassMetaData classMetaData)
public void setCondition(RuleCondition<T> condition)
public void setPropertyMetaData(PropertyMetaData propertyMetaData)
public void setText(String text)
public void setVariation(String variation)
public String toString()
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.rules.meta.RuleRepositoryTest**

```
public void testAddRule()
```
***
**org.jaffa.rules.meta.RuleTest**

```
public void testExecutionRealms()
```
***
**org.jaffa.rules.realm.Realm**

```
public void addClassRegex(String classRegex)
public void addClassRegexes(List<String> classRegexes)
public void addInheritanceRuleToExclude(String inheritanceRuleToExclude)
public void addInheritanceRuleToInclude(String inheritanceRuleToInclude)
public void addInheritanceRulesToExclude(List<String> inheritanceRulesToExclude)
public void addInheritanceRulesToInclude(List<String> inheritanceRulesToInclude)
public List<String> getClassRegexes()
public List<String> getInheritanceRulesToExclude()
public List<String> getInheritanceRulesToInclude()
public Realm inheritanceRulesToExclude(String excludes)
public Realm inheritanceRulesToInclude(String includes)
public Realm regex(String classRegex)
public Realm register()
public String toString()
```
***
**org.jaffa.rules.realm.RealmRepository**

```
public void addMapping(String className, String executionRealm)
public void addRealm(Realm realm)
public synchronized void clear() throws JaffaRulesFrameworkException
public String find(String className)
public List<RuleMetaData> getInheritableRules(String targetClassName, List<RuleMetaData> rules)
public List<String> getInheritanceRulesToExclude(String realmName)
public List<String> getInheritanceRulesToInclude(String realmName)
public List<Realm> getRealmsBySource(String source)
public static RealmRepository instance()
public boolean isInheritable(String targetClassName, RuleMetaData rule)
public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException
public void removeMapping(String className)
public synchronized void unload(String uri) throws JaffaRulesFrameworkException
```
***
**org.jaffa.rules.rulemeta.AbstractRuleHelper**

```
public boolean checkExecutionRealm(String className, Rule ruleInfo)
public List<RuleMetaData> getApplicableRules(String className, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException
public List<RuleMetaData> getApplicableRules(String className, Object obj, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException
public void validate(RuleMetaData rule) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.rules.rulemeta.AuditRuleHelper**

No public methods.
***
**org.jaffa.rules.rulemeta.AuditRuleMetaHelper**

```
public static RuleMetaData findRule(RuleMetaDataCriteria criteria)
public static List<RuleMetaData> findRules(RuleMetaDataCriteria criteria)
```
***
**org.jaffa.rules.rulemeta.DefaultRuleHelper**

```
public boolean checkCondition(Object obj, RuleMetaData rule) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.rules.rulemeta.IFormatter**

```
public String format(Object property)
public String getLayout()
public void setLayout(String layout)
```
***
**org.jaffa.rules.rulemeta.IRuleEvaluator**

No public methods.
***
**org.jaffa.rules.rulemeta.IRuleHelper**

```
public boolean checkExecutionRealm(String className, Rule ruleInfo)
public List<RuleMetaData> getApplicableRules(String className, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException
public List<RuleMetaData> getApplicableRules(String className, Object obj, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException
public void validate(RuleMetaData rule) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.rules.rulemeta.IRuleManager**

No public methods.
***
**org.jaffa.rules.rulemeta.InvalidRuleParameterException**

```
public String getMessage()
```
***
**org.jaffa.rules.rulemeta.Parameter**

```
public Boolean getCaseInsensitive()
public String getDefault()
public Boolean getMandatory()
public String[] getValidValues()
public void setCaseInsensitive(Boolean caseInsensitive)
public void setDefault(String def)
public void setMandatory(Boolean mandatory)
public void setValidValues(String[] validValues)
public String toString()
```
***
**org.jaffa.rules.rulemeta.Rule**

```
public void addParameter(Parameter parameter)
public void addParameters(List<Parameter> parameters)
public Rule executionRealms(String realms)
public String[] getExecutionRealms()
public String getExtendsRule()
public String getHelper()
public String getInterceptor()
public Parameter getParameter(String name)
public List<Parameter> getParameters()
public String getPrecedence()
public String getTextParameter()
public boolean isInheritable()
public boolean isPrecedenceAll()
public boolean isPrecedenceFirst()
public boolean isPrecedenceLast()
public boolean isPrepared()
public Rule parameter(String name)
public Rule parameter(String name, boolean mandatory)
public Rule parameter(String name, boolean mandatory, String def)
public Rule parameter(String name, boolean mandatory, String def, String validValues)
public Rule parameter(String name, boolean mandatory, String def, String validValues, boolean caseInsensitive)
public Rule precedenceAll()
public Rule precedenceFirst()
public Rule precedenceLast()
public void prepare()
public Rule register()
public void setExecutionRealms(String[] executionRealms)
public void setExtendsRule(String extendsRule)
public void setHelper(String helper)
public void setInterceptor(String interceptor)
public void setPrecedence(String precedence)
public void setTextParameter(String textParameter)
public Rule textParameter(String textParameter)
public String toString()
```
***
**org.jaffa.rules.rulemeta.RuleHelperFactory**

```
public static IRuleHelper instance(String ruleName)
```
***
**org.jaffa.rules.rulemeta.RuleMetaHelper**

```
public static RuleMetaData findRule(RuleMetaDataCriteria criteria)
public static List<RuleMetaData> findRules(RuleMetaDataCriteria criteria)
```
***
**org.jaffa.rules.rulemeta.RuleParameterException**

```
public void setClassName(String className)
```
***
**org.jaffa.rules.rulemeta.RuleRepository**

```
public void addRule(Rule rule)
public synchronized void clear() throws JaffaRulesFrameworkException
public Rule getRuleByName(String name)
public Collection<String> getRuleNames()
public List<Rule> getRulesBySource(String source)
public static RuleRepository instance()
public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException
public synchronized void unload(String uri) throws JaffaRulesFrameworkException
```
***
**org.jaffa.rules.rulemeta.ScriptLifecycleHandler**

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
**org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria**

```
public String getClassName()
public Object getObj()
public String getPropertyName()
public String getRuleName()
public boolean isApplyPrecedence()
public void setApplyPrecedence(boolean applyPrecedence)
public void setClassName(String className)
public void setObj(Object obj)
public void setPropertyName(String propertyName)
public void setRuleName(String ruleName)
```
***
**org.jaffa.rules.testinterceptors.CxfFunctionGuardInterceptorTest**

```
public void setUp() throws Exception
public void testInterceptor() throws NoSuchMethodException, FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.testinterceptors.Log1Interceptor**

```
public String getName()
public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
```
***
**org.jaffa.rules.testinterceptors.Log2Interceptor**

```
public String getName()
public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
```
***
**org.jaffa.rules.testinterceptors.TestClass**

```
public void test()
```
***
**org.jaffa.rules.testmodels.Access1**

```
public String getField1()
public String getField2()
public String getField3()
public void setField1(String f)
public void setField2(String f)
public void setField3(String f)
public String xgetField3()
```
***
**org.jaffa.rules.testmodels.Access2**

No public methods.
***
**org.jaffa.rules.testmodels.Access3**

No public methods.
***
**org.jaffa.rules.testmodels.Access4**

No public methods.
***
**org.jaffa.rules.testmodels.AccessConstants**

No public methods.
***
**org.jaffa.rules.testmodels.Child1**

```
public String getField2()
public void setField2(String field2)
public void setField3(String field3)
```
***
**org.jaffa.rules.testmodels.Child2**

```
public String getField2()
public String getField4()
public UOW getUOW() throws UOWException
public void setField2(String field2)
public void setField3(String field3)
public void setField4(String field4)
```
***
**org.jaffa.rules.testmodels.Child3**

```
public String getField1()
public String getField2()
public String getField3()
public String getField4()
public void setField1(String field1)
public void setField2(String field2)
public void setField3(String field3)
public void setField4(String field4)
public void validate()
```
***
**org.jaffa.rules.testmodels.CommentBean**

```
public void clearChanges()
public String getField1()
public String getField2()
public String getField3()
public Object getOriginalValue(String property)
public boolean hasChanged()
public boolean hasChanged(String property)
public void setField1(String f)
public void setField2(String f)
public void setField3(String f)
public void validate()
public String xgetField3()
```
***
**org.jaffa.rules.testmodels.CommentBusinessBean**

```
public void clearChanges()
public String getField1()
public String getField2()
public String getField3()
public Object getOriginalValue(String property)
public boolean hasChanged()
public boolean hasChanged(String property)
public void setField1(String f)
public void setField2(String f)
public void setField3(String f)
public void validate()
public String xgetField3()
```
***
**org.jaffa.rules.testmodels.CommentTest**

```
public static void main(String[] args)
public void testPlain()
```
***
**org.jaffa.rules.testmodels.ExecutionOrder1**

```
public String getField1()
public String getField2()
public String getField3()
public void setField1(String f)
public void setField2(String f)
public void setField3(String f)
```
***
**org.jaffa.rules.testmodels.Extension1**

```
public String getField1()
public String getField10()
public Long getField11()
public String getField2()
public String getField3()
public Double getField4()
public Long getField5()
public DateOnly getField6()
public int getField7()
public DateOnly getField8()
public double getField9()
public UOW getUOW() throws UOWException
public void setField1(String f)
public void setField10(String f)
public void setField11(Long f)
public void setField2(String f)
public void setField3(String f)
public void setField4(Double f)
public void setField5(Long f)
public void setField6(DateOnly f)
public void setField7(int f)
public void setField8(DateOnly f)
public void setField9(double f)
public void validate()
```
***
**org.jaffa.rules.testmodels.Initialize1**

```
public Boolean getFieldBoolean()
public Byte getFieldByte()
public Currency getFieldCurrency()
public DateOnly getFieldDateOnly()
public DateTime getFieldDateTime()
public Double getFieldDouble()
public Float getFieldFloat()
public Integer getFieldInteger()
public Long getFieldLong()
public Short getFieldShort()
public String getFieldString()
public boolean getFieldboolean()
public byte getFieldbyte()
public double getFielddouble()
public float getFieldfloat()
public int getFieldint()
public long getFieldlong()
public short getFieldshort()
public void setFieldBoolean(Boolean value)
public void setFieldByte(Byte value)
public void setFieldCurrency(Currency value)
public void setFieldDateOnly(DateOnly value)
public void setFieldDateTime(DateTime value)
public void setFieldDouble(Double value)
public void setFieldFloat(Float value)
public void setFieldInteger(Integer value)
public void setFieldLong(Long value)
public void setFieldShort(Short value)
public void setFieldString(String value)
public void setFieldboolean(boolean value)
public void setFieldbyte(byte value)
public void setFielddouble(double value)
public void setFieldfloat(float value)
public void setFieldint(int value)
public void setFieldlong(long value)
public void setFieldshort(short value)
```
***
**org.jaffa.rules.testmodels.Initialize2**

```
public Boolean getFieldBoolean()
public Byte getFieldByte()
public Currency getFieldCurrency()
public DateOnly getFieldDateOnly()
public DateTime getFieldDateTime()
public Double getFieldDouble()
public Float getFieldFloat()
public Integer getFieldInteger()
public Long getFieldLong()
public Short getFieldShort()
public String getFieldString()
public boolean getFieldboolean()
public byte getFieldbyte()
public double getFielddouble()
public float getFieldfloat()
public int getFieldint()
public long getFieldlong()
public short getFieldshort()
public void setFieldBoolean(Boolean value)
public void setFieldByte(Byte value)
public void setFieldCurrency(Currency value)
public void setFieldDateOnly(DateOnly value)
public void setFieldDateTime(DateTime value)
public void setFieldDouble(Double value)
public void setFieldFloat(Float value)
public void setFieldInteger(Integer value)
public void setFieldLong(Long value)
public void setFieldShort(Short value)
public void setFieldString(String value)
public void setFieldboolean(boolean value)
public void setFieldbyte(byte value)
public void setFielddouble(double value)
public void setFieldfloat(float value)
public void setFieldint(int value)
public void setFieldlong(long value)
public void setFieldshort(short value)
```
***
**org.jaffa.rules.testmodels.Parent**

```
public String getField0()
public String getField1()
public String getField2()
public String getField3()
public void setField0(String field0)
public void setField1(String field1)
public void setField2(String field2)
public void setField3(String field3)
public void validate()
```
***
**org.jaffa.rules.testmodels.PersistentBase**

```
public void preAdd()
```
***
**org.jaffa.rules.testmodels.PersistentChild**

```
public String getField1()
public void setField1(String field1)
```
***
**org.jaffa.rules.testmodels.ScriptBean**

```
public String getField1()
public String getField2()
public String getField3()
public String getField4()
public void preUpdate()
public void preUpdate(Object o)
public void setField1(String f)
public void setField2(String f)
public void setField3(String f)
public void setField4(String f)
public String xgetField3()
```
***
**org.jaffa.rules.testmodels.SomeForeignClass**

```
public static boolean exists(UOW uow, String someKeyField) throws FrameworkException
public static SomeForeignClass findByPK(UOW uow, String someKeyField) throws FrameworkException
public String getField1()
public String getField2()
public String getSomeKeyField()
public void setField1(String field1)
public void setField2(String field2)
public void setSomeKeyField(String someKeyField)
```
***
**org.jaffa.rules.testmodels.SomeForeignClassWithCompositeKey**

```
public static boolean exists(UOW uow, String someKeyField1, Long someKeyField2) throws FrameworkException
public static SomeForeignClassWithCompositeKey findByPK(UOW uow, String someKeyField1, Long someKeyField2) throws FrameworkException
public String getField1()
public String getField2()
public String getSomeKeyField1()
public Long getSomeKeyField2()
public void setField1(String field1)
public void setField2(String field2)
public void setSomeKeyField1(String someKeyField1)
public void setSomeKeyField2(Long someKeyField2)
```
***
**org.jaffa.rules.testmodels.SomeGraphCriteria**

```
public StringCriteriaField getField1()
public StringCriteriaField getField2()
public StringCriteriaField getField3()
public Criteria returnQueryClause(Criteria arg0)
public void setField1(StringCriteriaField field1)
public void setField2(StringCriteriaField field2)
public void setField3(StringCriteriaField field3)
```
***
**org.jaffa.rules.testmodels.Strings1**

```
public String getField1()
public String getField2()
public String getField3()
public String getField4()
public UOW getUOW()
public void setField1(String f) throws ReadOnlyObjectException, AlreadyLockedObjectException
public void setField2(String f) throws ReadOnlyObjectException, AlreadyLockedObjectException
public void setField3(String f) throws ReadOnlyObjectException, AlreadyLockedObjectException
public void setField4(String f) throws ReadOnlyObjectException, AlreadyLockedObjectException
public void validateSuper() throws FrameworkException, ApplicationExceptions
public String xgetField3()
```
***
**org.jaffa.rules.testmodels.StringsConstants**

No public methods.
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
**org.jaffa.rules.testmodels.UserMeta**

```
public static FieldMetaData[] getKeyFields()
public static FieldMetaData[] getMandatoryFields()
```
***
**org.jaffa.rules.testmodels.UserNoFlex**

```
public String getId()
public String getName()
public void setId(String id)
public void setName(String name)
```
***
**org.jaffa.rules.testmodels.VariationBean**

```
public String getField1()
public String getField2()
public String getField3()
public void setField1(String field1)
public void setField2(String field2)
public void setField3(String field3)
```
***
**org.jaffa.rules.util.AbstractFormatter**

```
public abstract String format(Object property)
public String getLayout()
public void setLayout(String layout)
```
***
**org.jaffa.rules.util.DataSecurity**

```
public static boolean enforceDataSecurity(Object target, String methodName) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.rules.util.MetaDataReader**

```
public static ClassMetaDataDto[] getClassMetaData(String flexClassName, String fileName)
public static List<FlexClassMetaData> getFlexClass()
public static ClassMetaDataDto[] getMetaData(ClassMetaDataCriteria criteria)
```
***
**org.jaffa.rules.util.MetaDataWriter**

```
public static void write(ClassMetaDataDto cmd) throws MetaDataWriterException
```
***
**org.jaffa.rules.util.MetaDataWriterException**

No public methods.
***
**org.jaffa.rules.util.PropertyTranslator**

```
public String format(Object property)
```
***
**org.jaffa.rules.util.RuleActor**

```
public boolean equals(Object o)
public String getActualClassName(Class clazz)
public String getName()
public IRuleEvaluator getRuleEvaluator()
public Map<String, List<RuleMetaData>> getRuleMap()
public int hashCode()
public void initializeRuleMapFromMetaData(List<String> classNames) throws FrameworkException, ApplicationExceptions
public void setRuleEvaluator(IRuleEvaluator ruleEvaluator)
public void setRuleMap(Map<String, List<RuleMetaData>> ruleMap)
public ApplicationException wrapException(Class<S> exception, T targetObject, String targetPropertyName, RuleMetaData rule)
```
***
**org.jaffa.rules.util.ScriptEngineInstanceFactory**

```
public ScriptEngine create() throws RuntimeException
public PooledObject<ScriptEngine> wrap(ScriptEngine scriptEngine)
```
***
**org.jaffa.rules.util.ScriptEnginePool**

```
public ScriptEngine borrowEngine(String language, Map<String, Object> vars) throws Exception
public void returnEngine(String language, ScriptEngine scriptEngine) throws Exception
```
***
**org.jaffa.rules.util.ScriptHelper**

```
public Object evaluate(String scriptFileName, Object expression, Object bean, String source, int lineNo, int columnNo) throws Exception
public Object evaluate(String scriptFileName, Object expression, Map beans, String source, int lineNo, int columnNo) throws Exception
public static ScriptHelper instance(String scriptLanguage)
```
***
**org.jaffa.rules.validators.BatchValidator**

```
public Set<Validator<T>> getValidatorSet()
public void setValidatorSet(Set<Validator<T>> validatorSet)
public void validate(T object) throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.BatchValidatorTest**

```
public void testValidation() throws FrameworkException, ApplicationException
public void testValidationException() throws FrameworkException, ApplicationException
```
***
**org.jaffa.rules.validators.CandidateKeyValidator**

No public methods.
***
**org.jaffa.rules.validators.CandidateKeyValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.CaseTypeValidator**

No public methods.
***
**org.jaffa.rules.validators.CaseTypeValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.CommentValidator**

No public methods.
***
**org.jaffa.rules.validators.CommentValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.ForeignKeyValidator**

No public methods.
***
**org.jaffa.rules.validators.ForeignKeyValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.GenericForeignKeyValidator**

No public methods.
***
**org.jaffa.rules.validators.GenericForeignKeyValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.InListValidator**

No public methods.
***
**org.jaffa.rules.validators.InListValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.KeyValidator**

```
public void validate(T targetObject) throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.MandatoryValidator**

No public methods.
***
**org.jaffa.rules.validators.MandatoryValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.MaxLengthValidator**

No public methods.
***
**org.jaffa.rules.validators.MaxLengthValidatorTest**

```
public void populateMetaData() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.MaxValueValidator**

No public methods.
***
**org.jaffa.rules.validators.MaxValueValidatorTest**

```
public void populateMetaData() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.MinLengthValidator**

No public methods.
***
**org.jaffa.rules.validators.MinLengthValidatorTest**

```
public void populateMetaData() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.MinValueValidator**

No public methods.
***
**org.jaffa.rules.validators.MinValueValidatorTest**

```
public void populateMetaData() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.NotInListValidator**

No public methods.
***
**org.jaffa.rules.validators.NotInListValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.PartialForeignKeyValidator**

No public methods.
***
**org.jaffa.rules.validators.PartialForeignKeyValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.PatternValidator**

No public methods.
***
**org.jaffa.rules.validators.PatternValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.PersistentConfigValidatorTest**

```
public void setup() throws Exception
public void testPersistentConfig() throws NoSuchFieldException, IllegalAccessException
```
***
**org.jaffa.rules.validators.PrimaryKeyValidator**

No public methods.
***
**org.jaffa.rules.validators.PrimaryKeyValidatorTest**

```
public void setup() throws Exception
public void testInterceptor() throws Throwable
public void testNoFailure() throws ApplicationException, FrameworkException
public void testValidator() throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.RuleValidator**

```
public void validate(T targetObject) throws ApplicationException, FrameworkException
```
***
**org.jaffa.rules.validators.RuleValidatorFactory**

```
public void addValidatorTypes(String... types)
public Validator getValidator(Object object)
public List<String> getValidatorTypes()
public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
public void setValidatorTypes(List<String> validatorTypes)
```
***
**org.jaffa.rules.validators.RuleValidatorFactoryTest**

```
public void setup() throws Exception
public void testCaching()
public void testGetValidator() throws Exception
public void testNullValidator() throws Exception
public void testReturnNullValidator() throws Exception
public void testReturnValidator() throws Exception
```
***
**org.jaffa.rules.validators.RuleValidatorTest**

```
public void testConditionalValidation() throws FrameworkException, ApplicationExceptions, ApplicationException
public void testSetRuleMap()
```
***
**org.jaffa.rules.validators.TestModelPersistent**

No public methods.
***
**org.jaffa.rules.validators.TestModelPersistentConfig**

```
public TestModelPersistent testModelPersistent(TestModelPersistent testModelPersistent)
```
***
**org.jaffa.rules.validators.TestUOW**

```
public Collection query(Criteria criteria)
```
***
**org.jaffa.rules.validators.TestValidator**

No public methods.
***
**org.jaffa.rules.variation.UriMetaData**

```
public Pattern getPattern()
public String getRegex()
public String getVariation()
public void setRegex(String regex)
public void setVariation(String variation)
public String toString()
```
***
**org.jaffa.rules.variation.VariationRepository**

```
public synchronized void clear() throws JaffaRulesFrameworkException
public String find(String uri)
public static VariationRepository instance()
public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException
public synchronized void unload(String uri) throws JaffaRulesFrameworkException
```
***
**org.jaffa.soa.graph.GraphCriteria**

No public methods.
