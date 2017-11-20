# jaffa-components-printing Public Methods

***
**org.jaffa.modules.printing.components.formdefinitionfinder.IFormDefinitionFinder**

```
public void destroy()
public FormDefinitionFinderOutDto find(FormDefinitionFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderInDto**

```
public StringCriteriaField getFormAlternate()
public IntegerCriteriaField getFormId()
public StringCriteriaField getFormName()
public StringCriteriaField getFormTemplate()
public StringCriteriaField getFormVariation()
public HeaderDto getHeaderDto()
public StringCriteriaField getOutputType()
public void setFormAlternate(StringCriteriaField formAlternate)
public void setFormId(IntegerCriteriaField formId)
public void setFormName(StringCriteriaField formName)
public void setFormTemplate(StringCriteriaField formTemplate)
public void setFormVariation(StringCriteriaField formVariation)
public void setHeaderDto(HeaderDto headerDto)
public void setOutputType(StringCriteriaField outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutDto**

```
public void addRows(FormDefinitionFinderOutRowDto rows)
public void clearRows()
public FormDefinitionFinderOutRowDto getRows(int index)
public FormDefinitionFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormDefinitionFinderOutRowDto rows)
public void setRows(FormDefinitionFinderOutRowDto rows, int index)
public void setRows(FormDefinitionFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getOutputType()
public void setDescription(java.lang.String description)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.tx.FormDefinitionFinderTx**

```
public void destroy()
public FormDefinitionFinderOutDto find(FormDefinitionFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.ui.FormDefinitionFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.ui.FormDefinitionFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public String getFormAlternate()
public String getFormAlternateDd()
public String getFormId()
public String getFormIdDd()
public String getFormName()
public String getFormNameDd()
public String getFormTemplate()
public String getFormTemplateDd()
public String getFormVariation()
public String getFormVariationDd()
public String getOutputType()
public String getOutputTypeDd()
public void quit()
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormTemplate(String formTemplate)
public void setFormTemplateDd(String formTemplateDd)
public void setFormVariation(String formVariation)
public void setFormVariationDd(String formVariationDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formdefinitionfinder.ui.FormDefinitionFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getFormAlternate()
public String getFormAlternateDd()
public DropDownModel getFormAlternateDdWM()
public EditBoxModel getFormAlternateWM()
public String getFormId()
public String getFormIdDd()
public DropDownModel getFormIdDdWM()
public EditBoxModel getFormIdWM()
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getFormTemplate()
public String getFormTemplateDd()
public DropDownModel getFormTemplateDdWM()
public EditBoxModel getFormTemplateWM()
public String getFormVariation()
public String getFormVariationDd()
public DropDownModel getFormVariationDdWM()
public EditBoxModel getFormVariationWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public void populateRows(GridModel rows)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormAlternateDdWV(String value)
public void setFormAlternateWV(String value)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormIdDdWV(String value)
public void setFormIdWV(String value)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setFormTemplate(String formTemplate)
public void setFormTemplateDd(String formTemplateDd)
public void setFormTemplateDdWV(String value)
public void setFormTemplateWV(String value)
public void setFormVariation(String formVariation)
public void setFormVariationDd(String formVariationDd)
public void setFormVariationDdWV(String value)
public void setFormVariationWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.IFormDefinitionLookup**

```
public void destroy()
public FormDefinitionLookupOutDto find(FormDefinitionLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupInDto**

```
public StringCriteriaField getAdditionalDataComponent()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public StringCriteriaField getDomClass()
public StringCriteriaField getDomFactory()
public StringCriteriaField getDomKey1()
public StringCriteriaField getDomKey2()
public StringCriteriaField getDomKey3()
public StringCriteriaField getDomKey4()
public StringCriteriaField getDomKey5()
public StringCriteriaField getDomKey6()
public StringCriteriaField getFieldLayout()
public StringCriteriaField getFollowOnFormAlternate()
public StringCriteriaField getFollowOnFormName()
public StringCriteriaField getFormAlternate()
public IntegerCriteriaField getFormId()
public StringCriteriaField getFormName()
public StringCriteriaField getFormTemplate()
public StringCriteriaField getFormVariation()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOutputType()
public StringCriteriaField getRemarks()
public void setAdditionalDataComponent(StringCriteriaField additionalDataComponent)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setDomClass(StringCriteriaField domClass)
public void setDomFactory(StringCriteriaField domFactory)
public void setDomKey1(StringCriteriaField domKey1)
public void setDomKey2(StringCriteriaField domKey2)
public void setDomKey3(StringCriteriaField domKey3)
public void setDomKey4(StringCriteriaField domKey4)
public void setDomKey5(StringCriteriaField domKey5)
public void setDomKey6(StringCriteriaField domKey6)
public void setFieldLayout(StringCriteriaField fieldLayout)
public void setFollowOnFormAlternate(StringCriteriaField followOnFormAlternate)
public void setFollowOnFormName(StringCriteriaField followOnFormName)
public void setFormAlternate(StringCriteriaField formAlternate)
public void setFormId(IntegerCriteriaField formId)
public void setFormName(StringCriteriaField formName)
public void setFormTemplate(StringCriteriaField formTemplate)
public void setFormVariation(StringCriteriaField formVariation)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOutputType(StringCriteriaField outputType)
public void setRemarks(StringCriteriaField remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutDto**

```
public void addRows(FormDefinitionLookupOutRowDto rows)
public void clearRows()
public FormDefinitionLookupOutRowDto getRows(int index)
public FormDefinitionLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormDefinitionLookupOutRowDto rows)
public void setRows(FormDefinitionLookupOutRowDto rows, int index)
public void setRows(FormDefinitionLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutRowDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey6(java.lang.String domKey6)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.tx.FormDefinitionLookupTx**

```
public void destroy()
public FormDefinitionLookupOutDto find(FormDefinitionLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.ui.FormDefinitionLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.ui.FormDefinitionLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public String getAdditionalDataComponent()
public String getAdditionalDataComponentDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getDescription()
public String getDescriptionDd()
public String getDomClass()
public String getDomClassDd()
public String getDomFactory()
public String getDomFactoryDd()
public String getDomKey1()
public String getDomKey1Dd()
public String getDomKey2()
public String getDomKey2Dd()
public String getDomKey3()
public String getDomKey3Dd()
public String getDomKey4()
public String getDomKey4Dd()
public String getDomKey5()
public String getDomKey5Dd()
public String getDomKey6()
public String getDomKey6Dd()
public String getFieldLayout()
public String getFieldLayoutDd()
public String getFollowOnFormAlternate()
public String getFollowOnFormAlternateDd()
public String getFollowOnFormName()
public String getFollowOnFormNameDd()
public String getFormAlternate()
public String getFormAlternateDd()
public String getFormId()
public String getFormIdDd()
public String getFormName()
public String getFormNameDd()
public String getFormTemplate()
public String getFormTemplateDd()
public String getFormVariation()
public String getFormVariationDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOutputType()
public String getOutputTypeDd()
public String getRemarks()
public String getRemarksDd()
public void quit()
public void setAdditionalDataComponent(String additionalDataComponent)
public void setAdditionalDataComponentDd(String additionalDataComponentDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDomClass(String domClass)
public void setDomClassDd(String domClassDd)
public void setDomFactory(String domFactory)
public void setDomFactoryDd(String domFactoryDd)
public void setDomKey1(String domKey1)
public void setDomKey1Dd(String domKey1Dd)
public void setDomKey2(String domKey2)
public void setDomKey2Dd(String domKey2Dd)
public void setDomKey3(String domKey3)
public void setDomKey3Dd(String domKey3Dd)
public void setDomKey4(String domKey4)
public void setDomKey4Dd(String domKey4Dd)
public void setDomKey5(String domKey5)
public void setDomKey5Dd(String domKey5Dd)
public void setDomKey6(String domKey6)
public void setDomKey6Dd(String domKey6Dd)
public void setFieldLayout(String fieldLayout)
public void setFieldLayoutDd(String fieldLayoutDd)
public void setFollowOnFormAlternate(String followOnFormAlternate)
public void setFollowOnFormAlternateDd(String followOnFormAlternateDd)
public void setFollowOnFormName(String followOnFormName)
public void setFollowOnFormNameDd(String followOnFormNameDd)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormTemplate(String formTemplate)
public void setFormTemplateDd(String formTemplateDd)
public void setFormVariation(String formVariation)
public void setFormVariationDd(String formVariationDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formdefinitionlookup.ui.FormDefinitionLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getAdditionalDataComponent()
public String getAdditionalDataComponentDd()
public DropDownModel getAdditionalDataComponentDdWM()
public EditBoxModel getAdditionalDataComponentWM()
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
public String getDomClass()
public String getDomClassDd()
public DropDownModel getDomClassDdWM()
public EditBoxModel getDomClassWM()
public String getDomFactory()
public String getDomFactoryDd()
public DropDownModel getDomFactoryDdWM()
public EditBoxModel getDomFactoryWM()
public String getDomKey1()
public String getDomKey1Dd()
public DropDownModel getDomKey1DdWM()
public EditBoxModel getDomKey1WM()
public String getDomKey2()
public String getDomKey2Dd()
public DropDownModel getDomKey2DdWM()
public EditBoxModel getDomKey2WM()
public String getDomKey3()
public String getDomKey3Dd()
public DropDownModel getDomKey3DdWM()
public EditBoxModel getDomKey3WM()
public String getDomKey4()
public String getDomKey4Dd()
public DropDownModel getDomKey4DdWM()
public EditBoxModel getDomKey4WM()
public String getDomKey5()
public String getDomKey5Dd()
public DropDownModel getDomKey5DdWM()
public EditBoxModel getDomKey5WM()
public String getDomKey6()
public String getDomKey6Dd()
public DropDownModel getDomKey6DdWM()
public EditBoxModel getDomKey6WM()
public String getFieldLayout()
public String getFieldLayoutDd()
public DropDownModel getFieldLayoutDdWM()
public EditBoxModel getFieldLayoutWM()
public String getFollowOnFormAlternate()
public String getFollowOnFormAlternateDd()
public DropDownModel getFollowOnFormAlternateDdWM()
public EditBoxModel getFollowOnFormAlternateWM()
public String getFollowOnFormName()
public String getFollowOnFormNameDd()
public DropDownModel getFollowOnFormNameDdWM()
public EditBoxModel getFollowOnFormNameWM()
public String getFormAlternate()
public String getFormAlternateDd()
public DropDownModel getFormAlternateDdWM()
public EditBoxModel getFormAlternateWM()
public String getFormId()
public String getFormIdDd()
public DropDownModel getFormIdDdWM()
public EditBoxModel getFormIdWM()
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getFormTemplate()
public String getFormTemplateDd()
public DropDownModel getFormTemplateDdWM()
public EditBoxModel getFormTemplateWM()
public String getFormVariation()
public String getFormVariationDd()
public DropDownModel getFormVariationDdWM()
public EditBoxModel getFormVariationWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public String getRemarks()
public String getRemarksDd()
public DropDownModel getRemarksDdWM()
public EditBoxModel getRemarksWM()
public void populateRows(GridModel rows)
public void setAdditionalDataComponent(String additionalDataComponent)
public void setAdditionalDataComponentDd(String additionalDataComponentDd)
public void setAdditionalDataComponentDdWV(String value)
public void setAdditionalDataComponentWV(String value)
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
public void setDomClass(String domClass)
public void setDomClassDd(String domClassDd)
public void setDomClassDdWV(String value)
public void setDomClassWV(String value)
public void setDomFactory(String domFactory)
public void setDomFactoryDd(String domFactoryDd)
public void setDomFactoryDdWV(String value)
public void setDomFactoryWV(String value)
public void setDomKey1(String domKey1)
public void setDomKey1Dd(String domKey1Dd)
public void setDomKey1DdWV(String value)
public void setDomKey1WV(String value)
public void setDomKey2(String domKey2)
public void setDomKey2Dd(String domKey2Dd)
public void setDomKey2DdWV(String value)
public void setDomKey2WV(String value)
public void setDomKey3(String domKey3)
public void setDomKey3Dd(String domKey3Dd)
public void setDomKey3DdWV(String value)
public void setDomKey3WV(String value)
public void setDomKey4(String domKey4)
public void setDomKey4Dd(String domKey4Dd)
public void setDomKey4DdWV(String value)
public void setDomKey4WV(String value)
public void setDomKey5(String domKey5)
public void setDomKey5Dd(String domKey5Dd)
public void setDomKey5DdWV(String value)
public void setDomKey5WV(String value)
public void setDomKey6(String domKey6)
public void setDomKey6Dd(String domKey6Dd)
public void setDomKey6DdWV(String value)
public void setDomKey6WV(String value)
public void setFieldLayout(String fieldLayout)
public void setFieldLayoutDd(String fieldLayoutDd)
public void setFieldLayoutDdWV(String value)
public void setFieldLayoutWV(String value)
public void setFollowOnFormAlternate(String followOnFormAlternate)
public void setFollowOnFormAlternateDd(String followOnFormAlternateDd)
public void setFollowOnFormAlternateDdWV(String value)
public void setFollowOnFormAlternateWV(String value)
public void setFollowOnFormName(String followOnFormName)
public void setFollowOnFormNameDd(String followOnFormNameDd)
public void setFollowOnFormNameDdWV(String value)
public void setFollowOnFormNameWV(String value)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormAlternateDdWV(String value)
public void setFormAlternateWV(String value)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormIdDdWV(String value)
public void setFormIdWV(String value)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setFormTemplate(String formTemplate)
public void setFormTemplateDd(String formTemplateDd)
public void setFormTemplateDdWV(String value)
public void setFormTemplateWV(String value)
public void setFormVariation(String formVariation)
public void setFormVariationDd(String formVariationDd)
public void setFormVariationDdWV(String value)
public void setFormVariationWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
public void setRemarks(String remarks)
public void setRemarksDd(String remarksDd)
public void setRemarksDdWV(String value)
public void setRemarksWV(String value)
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.IFormDefinitionMaintenance**

```
public FormDefinitionMaintenanceRetrieveOutDto create(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormDefinitionMaintenancePrevalidateOutDto prevalidateCreate(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenancePrevalidateOutDto prevalidateUpdate(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenanceRetrieveOutDto retrieve(FormDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenanceRetrieveOutDto update(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenanceCreateInDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public HeaderDto getHeaderDto()
public byte[] getLayoutData()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public byte[] getTemplateData()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey6(java.lang.String domKey6)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setHeaderDto(HeaderDto headerDto)
public void setLayoutData(byte[] layoutData)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenanceDeleteInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenanceRetrieveInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenanceRetrieveOutDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey6(java.lang.String domKey6)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.FormDefinitionMaintenanceUpdateInDto**

```
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.tx.FormDefinitionMaintenanceTx**

```
public FormDefinitionMaintenanceRetrieveOutDto create(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormDefinitionMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormDefinitionMaintenancePrevalidateOutDto prevalidateCreate(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenancePrevalidateOutDto prevalidateUpdate(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenanceRetrieveOutDto retrieve(FormDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionMaintenanceRetrieveOutDto update(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public FormFile getFile()
public FormFile getFileLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public byte[] getLayoutData()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public byte[] getTemplateData()
public void quit()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey6(java.lang.String domKey6)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFile(FormFile file)
public void setFileLayout(FormFile fileLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLayoutData(byte[] layoutData)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public void setTemplateData(byte[] templateData)
```
***
**org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getAdditionalDataComponent()
public EditBoxModel getAdditionalDataComponentWM()
public java.lang.String getCreatedBy()
public EditBoxModel getCreatedByWM()
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public java.lang.String getDomClass()
public EditBoxModel getDomClassWM()
public java.lang.String getDomFactory()
public EditBoxModel getDomFactoryWM()
public java.lang.String getDomKey1()
public EditBoxModel getDomKey1WM()
public java.lang.String getDomKey2()
public EditBoxModel getDomKey2WM()
public java.lang.String getDomKey3()
public EditBoxModel getDomKey3WM()
public java.lang.String getDomKey4()
public EditBoxModel getDomKey4WM()
public java.lang.String getDomKey5()
public EditBoxModel getDomKey5WM()
public java.lang.String getDomKey6()
public EditBoxModel getDomKey6WM()
public java.lang.String getFieldLayout()
public EditBoxModel getFieldLayoutWM()
public FormFile getFile()
public java.lang.String getFollowOnFormAlternate()
public EditBoxModel getFollowOnFormAlternateWM()
public java.lang.String getFollowOnFormName()
public EditBoxModel getFollowOnFormNameWM()
public java.lang.String getFormAlternate()
public EditBoxModel getFormAlternateWM()
public java.lang.Long getFormId()
public EditBoxModel getFormIdWM()
public java.lang.String getFormName()
public EditBoxModel getFormNameWM()
public java.lang.String getFormTemplate()
public EditBoxModel getFormTemplateWM()
public java.lang.String getFormVariation()
public EditBoxModel getFormVariationWM()
public java.lang.String getLastChangedBy()
public EditBoxModel getLastChangedByWM()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public DateTimeModel getLastChangedOnWM()
public FormFile getLayoutFile()
public java.lang.String getOutputType()
public EditBoxModel getOutputTypeWM()
public java.lang.String getRemarks()
public EditBoxModel getRemarksWM()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setAdditionalDataComponentWV(String value)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedByWV(String value)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setDomClass(java.lang.String domClass)
public void setDomClassWV(String value)
public void setDomFactory(java.lang.String domFactory)
public void setDomFactoryWV(String value)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey1WV(String value)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey2WV(String value)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey3WV(String value)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey4WV(String value)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey5WV(String value)
public void setDomKey6(java.lang.String domKey6)
public void setDomKey6WV(String value)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFieldLayoutWV(String value)
public void setFile(FormFile file)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormAlternateWV(String value)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFollowOnFormNameWV(String value)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormAlternateWV(String value)
public void setFormId(java.lang.Long formId)
public void setFormIdWV(String value)
public void setFormName(java.lang.String formName)
public void setFormNameWV(String value)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormTemplateWV(String value)
public void setFormVariation(java.lang.String formVariation)
public void setFormVariationWV(String value)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedByWV(String value)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLastChangedOnWV(String value)
public void setLayoutFile(FormFile fileLayout)
public void setOutputType(java.lang.String outputType)
public void setOutputTypeWV(String value)
public void setRemarks(java.lang.String remarks)
public void setRemarksWV(String value)
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.IFormDefinitionViewer**

```
public void destroy()
public byte[] loadFormTemplate(FormDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionViewerOutDto read(FormDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerOutDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setDomKey4(java.lang.String domKey4)
public void setDomKey5(java.lang.String domKey5)
public void setDomKey6(java.lang.String domKey6)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.tx.FormDefinitionViewerTx**

```
public void destroy()
public byte[] loadFormTemplate(FormDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
public FormDefinitionViewerOutDto read(FormDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
public FormKey do_ViewFileContents_Clicked()
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormDefinitionViewerOutDto getFormDefinitionViewerOutDto()
public java.lang.Long getFormId()
public FormKey getViewerFormKey()
public File loadFormTemplate() throws FrameworkException, ApplicationExceptions, IOException
public void quit()
public void setFormDefinitionViewerOutDto(FormDefinitionViewerOutDto outputDto)
public void setFormId(java.lang.Long formId)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerForm**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
```
***
**org.jaffa.modules.printing.components.formeventfinder.IFormEventFinder**

```
public void destroy()
public FormEventFinderOutDto find(FormEventFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventfinder.dto.FormEventFinderInDto**

```
public StringCriteriaField getDescription()
public StringCriteriaField getEventName()
public HeaderDto getHeaderDto()
public void setDescription(StringCriteriaField description)
public void setEventName(StringCriteriaField eventName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventfinder.dto.FormEventFinderOutDto**

```
public void addRows(FormEventFinderOutRowDto rows)
public void clearRows()
public FormEventFinderOutRowDto getRows(int index)
public FormEventFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormEventFinderOutRowDto rows)
public void setRows(FormEventFinderOutRowDto rows, int index)
public void setRows(FormEventFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventfinder.dto.FormEventFinderOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getEventName()
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventfinder.tx.FormEventFinderTx**

```
public void destroy()
public FormEventFinderOutDto find(FormEventFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventfinder.ui.FormEventFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formeventfinder.ui.FormEventFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String eventName) throws ApplicationExceptions, FrameworkException
public String getDescription()
public String getDescriptionDd()
public String getEventName()
public String getEventNameDd()
public void quit()
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public FormKey updateObject(java.lang.String eventName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formeventfinder.ui.FormEventFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getEventName()
public String getEventNameDd()
public DropDownModel getEventNameDdWM()
public EditBoxModel getEventNameWM()
public void populateRows(GridModel rows)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setEventNameDdWV(String value)
public void setEventNameWV(String value)
```
***
**org.jaffa.modules.printing.components.formeventlookup.IFormEventLookup**

```
public void destroy()
public FormEventLookupOutDto find(FormEventLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventlookup.dto.FormEventLookupInDto**

```
public StringCriteriaField getDescription()
public StringCriteriaField getEventName()
public HeaderDto getHeaderDto()
public void setDescription(StringCriteriaField description)
public void setEventName(StringCriteriaField eventName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventlookup.dto.FormEventLookupOutDto**

```
public void addRows(FormEventLookupOutRowDto rows)
public void clearRows()
public FormEventLookupOutRowDto getRows(int index)
public FormEventLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormEventLookupOutRowDto rows)
public void setRows(FormEventLookupOutRowDto rows, int index)
public void setRows(FormEventLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventlookup.dto.FormEventLookupOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getEventName()
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventlookup.tx.FormEventLookupTx**

```
public void destroy()
public FormEventLookupOutDto find(FormEventLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventlookup.ui.FormEventLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formeventlookup.ui.FormEventLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String eventName) throws ApplicationExceptions, FrameworkException
public String getDescription()
public String getDescriptionDd()
public String getEventName()
public String getEventNameDd()
public Integer getMaxRecords()
public void quit()
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setMaxRecords(Integer maxRecords)
public FormKey updateObject(java.lang.String eventName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formeventlookup.ui.FormEventLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getEventName()
public String getEventNameDd()
public DropDownModel getEventNameDdWM()
public EditBoxModel getEventNameWM()
public void populateRows(GridModel rows)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setEventNameDdWV(String value)
public void setEventNameWV(String value)
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.IFormEventMaintenance**

```
public FormEventMaintenanceRetrieveOutDto create(FormEventMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormEventMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormEventMaintenancePrevalidateOutDto prevalidateCreate(FormEventMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenancePrevalidateOutDto prevalidateUpdate(FormEventMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenanceRetrieveOutDto retrieve(FormEventMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenanceRetrieveOutDto update(FormEventMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenanceCreateInDto**

```
public java.lang.String getDescription()
public java.lang.String getEventName()
public HeaderDto getHeaderDto()
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenanceDeleteInDto**

```
public java.lang.String getEventName()
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public void setEventName(java.lang.String eventName)
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenanceRetrieveInDto**

```
public java.lang.String getEventName()
public HeaderDto getHeaderDto()
public void setEventName(java.lang.String eventName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenanceRetrieveOutDto**

```
public void addFormUsage(FormUsageDto formUsage)
public void clearFormUsage()
public java.lang.String getDescription()
public java.lang.String getEventName()
public FormUsageDto getFormUsage(int index)
public FormUsageDto[] getFormUsage()
public int getFormUsageCount()
public boolean removeFormUsage(FormUsageDto formUsage)
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public void setFormUsage(FormUsageDto formUsage, int index)
public void setFormUsage(FormUsageDto[] objects)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormEventMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.dto.FormUsageDto**

```
public java.lang.String getEventName()
public void setEventName(java.lang.String eventName)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.tx.FormEventMaintenanceTx**

```
public FormEventMaintenanceRetrieveOutDto create(FormEventMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormEventMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormEventMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormEventMaintenancePrevalidateOutDto prevalidateCreate(FormEventMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenancePrevalidateOutDto prevalidateUpdate(FormEventMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenanceRetrieveOutDto retrieve(FormEventMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormEventMaintenanceRetrieveOutDto update(FormEventMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.ui.FormEventMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.ui.FormEventMaintenanceComponent**

```
public java.lang.String getDescription()
public java.lang.String getEventName()
public FormUsageDto[] getRelatedObjectFormUsageDto()
public void quit()
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
```
***
**org.jaffa.modules.printing.components.formeventmaintenance.ui.FormEventMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public java.lang.String getEventName()
public EditBoxModel getEventNameWM()
public GridModel getRelatedFormUsageWM()
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setEventName(java.lang.String eventName)
public void setEventNameWV(String value)
public void setRelatedFormUsageWV(String value)
```
***
**org.jaffa.modules.printing.components.formeventviewer.IFormEventViewer**

```
public void destroy()
public FormEventViewerOutDto read(FormEventViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerInDto**

```
public java.lang.String getEventName()
public HeaderDto getHeaderDto()
public void setEventName(java.lang.String eventName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerOutDto**

```
public void addFormUsage(FormUsageDto formUsage)
public void clearFormUsage()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getEventName()
public FormUsageDto getFormUsage(int index)
public FormUsageDto[] getFormUsage()
public int getFormUsageCount()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public boolean removeFormUsage(FormUsageDto formUsage)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public void setFormUsage(FormUsageDto formUsage, int index)
public void setFormUsage(FormUsageDto[] objects)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventviewer.dto.FormUsageDto**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formeventviewer.tx.FormEventViewerTx**

```
public void destroy()
public FormEventViewerOutDto read(FormEventViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formeventviewer.ui.FormEventViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_RelatedFormUsage_Clicked(String rowNum)
public FormKey do_RelatedFormUsage_Delete_Clicked(String rowNum)
public FormKey do_RelatedFormUsage_Update_Clicked(String rowNum)
public FormKey do_RelatedFormUsage_View_Clicked(String rowNum)
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.formeventviewer.ui.FormEventViewerComponent**

```
public FormKey deleteFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject() throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getEventName()
public FormEventViewerOutDto getFormEventViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setEventName(java.lang.String eventName)
public void setFormEventViewerOutDto(FormEventViewerOutDto outputDto)
public FormKey updateFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
public FormKey viewFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formeventviewer.ui.FormEventViewerForm**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getEventName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public GridModel getRelatedFormUsageWM()
public void setRelatedFormUsageWV(String value)
```
***
**org.jaffa.modules.printing.components.formgroupfinder.IFormGroupFinder**

```
public void destroy()
public FormGroupFinderOutDto find(FormGroupFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupfinder.dto.FormGroupFinderInDto**

```
public StringCriteriaField getFormName()
public StringCriteriaField getFormType()
public HeaderDto getHeaderDto()
public void setFormName(StringCriteriaField formName)
public void setFormType(StringCriteriaField formType)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupfinder.dto.FormGroupFinderOutDto**

```
public void addRows(FormGroupFinderOutRowDto rows)
public void clearRows()
public FormGroupFinderOutRowDto getRows(int index)
public FormGroupFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormGroupFinderOutRowDto rows)
public void setRows(FormGroupFinderOutRowDto rows, int index)
public void setRows(FormGroupFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupfinder.dto.FormGroupFinderOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupfinder.tx.FormGroupFinderTx**

```
public void destroy()
public FormGroupFinderOutDto find(FormGroupFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupfinder.ui.FormGroupFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formgroupfinder.ui.FormGroupFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public String getFormName()
public String getFormNameDd()
public String getFormType()
public void quit()
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormType(String formType)
public FormKey updateObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formgroupfinder.ui.FormGroupFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getFormType()
public GridModel getFormTypeWM()
public void populateRows(GridModel rows)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setFormType(String formType)
public void setFormTypeWV(String value)
```
***
**org.jaffa.modules.printing.components.formgrouplookup.IFormGroupLookup**

```
public void destroy()
public FormGroupLookupOutDto find(FormGroupLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupInDto**

```
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public StringCriteriaField getFormName()
public StringCriteriaField getFormType()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setFormName(StringCriteriaField formName)
public void setFormType(StringCriteriaField formType)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutDto**

```
public void addRows(FormGroupLookupOutRowDto rows)
public void clearRows()
public FormGroupLookupOutRowDto getRows(int index)
public FormGroupLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormGroupLookupOutRowDto rows)
public void setRows(FormGroupLookupOutRowDto rows, int index)
public void setRows(FormGroupLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgrouplookup.tx.FormGroupLookupTx**

```
public void destroy()
public FormGroupLookupOutDto find(FormGroupLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgrouplookup.ui.FormGroupLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formgrouplookup.ui.FormGroupLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getDescription()
public String getDescriptionDd()
public String getFormName()
public String getFormNameDd()
public String getFormType()
public String getFormTypeDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public void quit()
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormType(String formType)
public void setFormTypeDd(String formTypeDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public FormKey updateObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formgrouplookup.ui.FormGroupLookupForm**

```
public boolean doValidate(HttpServletRequest request)
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
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getFormType()
public String getFormTypeDd()
public DropDownModel getFormTypeDdWM()
public EditBoxModel getFormTypeWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public void populateRows(GridModel rows)
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
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setFormType(String formType)
public void setFormTypeDd(String formTypeDd)
public void setFormTypeDdWV(String value)
public void setFormTypeWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.IFormGroupMaintenance**

```
public FormGroupMaintenanceRetrieveOutDto create(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormGroupMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public boolean doValidations(String formName, String formAlternate) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenancePrevalidateOutDto prevalidateCreate(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenancePrevalidateOutDto prevalidateUpdate(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenanceRetrieveOutDto retrieve(FormGroupMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenanceRetrieveOutDto update(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormDefinitionDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenanceCreateInDto**

```
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public HeaderDto getHeaderDto()
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenanceDeleteInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenanceRetrieveInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenanceRetrieveOutDto**

```
public void addFormUsage(FormUsageDto formUsage)
public void clearFormUsage()
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public FormUsageDto getFormUsage(int index)
public FormUsageDto[] getFormUsage()
public int getFormUsageCount()
public boolean removeFormUsage(FormUsageDto formUsage)
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setFormUsage(FormUsageDto formUsage, int index)
public void setFormUsage(FormUsageDto[] objects)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormGroupMaintenanceUpdateInDto**

```
public void addFormUsage(FormUsageDto formUsage)
public void clearFormUsage()
public FormUsageDto getFormUsage(int index)
public FormUsageDto[] getFormUsage()
public int getFormUsageCount()
public Boolean getPerformDirtyReadCheck()
public boolean removeFormUsage(FormUsageDto formUsage)
public void setFormUsage(FormUsageDto formUsage, int index)
public void setFormUsage(FormUsageDto[] objects)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.dto.FormUsageDto**

```
public java.lang.Long getCopies()
public java.lang.String getDescription()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public void setCopies(java.lang.Long copies)
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.tx.FormGroupMaintenanceTx**

```
public FormGroupMaintenanceRetrieveOutDto create(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormGroupMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormGroupMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public boolean doValidations(String formName, String formAlternate) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenancePrevalidateOutDto prevalidateCreate(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenancePrevalidateOutDto prevalidateUpdate(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenanceRetrieveOutDto retrieve(FormGroupMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormGroupMaintenanceRetrieveOutDto update(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceAction**

```
public FormKey do_AddRelatedFormUsage_Clicked()
public FormKey do_RemoveRelatedFormUsage_Clicked()
public FormKey do_Save_Clicked()
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceComponent**

```
public boolean duplicateCheckForFormUsageEntry(List arr, String eventname)
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public FormUsageDto[] getRelatedObjectFormUsageDto()
public FormKey invokeFormEventLookup() throws FrameworkException, ApplicationExceptions
public boolean processRows(GridModel model) throws FrameworkException, ApplicationExceptions
public void quit()
public void removeFormUsageEntry(ArrayList arr, GridModel model) throws FrameworkException, ApplicationExceptions
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setFormUsage(FormUsageDto[] formusagedtos)
```
***
**org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public java.lang.String getFormName()
public EditBoxModel getFormNameWM()
public java.lang.String getFormType()
public RadioButtonModel getFormTypeWM()
public GridModel getRelatedFormUsageWM()
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setFormName(java.lang.String formName)
public void setFormNameWV(String value)
public void setFormType(java.lang.String formType)
public void setFormTypeWV(String value)
public void setRelatedFormUsageWV(String value)
```
***
**org.jaffa.modules.printing.components.formgroupviewer.IFormGroupViewer**

```
public void destroy()
public FormGroupViewerOutDto read(FormGroupViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupviewer.dto.FormDefinitionDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public java.lang.String getRemarks()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDomClass(java.lang.String domClass)
public void setDomFactory(java.lang.String domFactory)
public void setDomKey1(java.lang.String domKey1)
public void setDomKey2(java.lang.String domKey2)
public void setDomKey3(java.lang.String domKey3)
public void setFieldLayout(java.lang.String fieldLayout)
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
public void setFollowOnFormName(java.lang.String followOnFormName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormId(java.lang.Long formId)
public void setFormName(java.lang.String formName)
public void setFormTemplate(java.lang.String formTemplate)
public void setFormVariation(java.lang.String formVariation)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public void setRemarks(java.lang.String remarks)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupviewer.dto.FormGroupViewerInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupviewer.dto.FormGroupViewerOutDto**

```
public void addFormUsage(FormUsageDto formUsage)
public void clearFormUsage()
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public FormUsageDto getFormUsage(int index)
public FormUsageDto[] getFormUsage()
public int getFormUsageCount()
public boolean removeFormUsage(FormUsageDto formUsage)
public void setDescription(java.lang.String description)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setFormUsage(FormUsageDto formUsage, int index)
public void setFormUsage(FormUsageDto[] objects)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupviewer.dto.FormUsageDto**

```
public java.lang.Long getCopies()
public java.lang.String getDescription()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public void setCopies(java.lang.Long copies)
public void setDescription(java.lang.String description)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public String toString()
```
***
**org.jaffa.modules.printing.components.formgroupviewer.tx.FormGroupViewerTx**

```
public void destroy()
public FormGroupViewerOutDto read(FormGroupViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formgroupviewer.ui.FormGroupViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.formgroupviewer.ui.FormGroupViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormGroupViewerOutDto getFormGroupViewerOutDto()
public java.lang.String getFormName()
public FormKey getViewerFormKey()
public void quit()
public void setFormGroupViewerOutDto(FormGroupViewerOutDto outputDto)
public void setFormName(java.lang.String formName)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formgroupviewer.ui.FormGroupViewerForm**

```
public java.lang.String getDescription()
public java.lang.String getFormName()
public java.lang.String getFormType()
public GridModel getRelatedFormUsageWM()
public void setRelatedFormUsageWV(String value)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.FormSelectionException**

```
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.IAdditionalData**

```
public void validate() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.IAdditionalDataObject**

```
public java.lang.Object getAdditionalDataObject()
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.IFormSelectionMaintenance**

```
public void destroy()
public void dispatchPrintRequest(FormPrintRequest formPrintRequest) throws FrameworkException, ApplicationExceptions
public FormSelectionMaintenanceOutDto find(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions
public FormSelectionMaintenanceOutDto findOutDto(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceInDto**

```
public String[] getDefaultPrinters()
public StringCriteriaField getEvent()
public HeaderDto getHeaderDto()
public StringCriteriaField getKey1()
public StringCriteriaField getKey2()
public StringCriteriaField getKey3()
public StringCriteriaField getKey4()
public StringCriteriaField getKey5()
public StringCriteriaField getKey6()
public StringCriteriaField getValue1()
public StringCriteriaField getValue2()
public StringCriteriaField getValue3()
public StringCriteriaField getValue4()
public StringCriteriaField getValue5()
public StringCriteriaField getValue6()
public void setDefaultPrinters(String[] defaultPrinters)
public void setEvent(StringCriteriaField event)
public void setHeaderDto(HeaderDto headerDto)
public void setKey1(StringCriteriaField key1)
public void setKey2(StringCriteriaField key2)
public void setKey3(StringCriteriaField key3)
public void setKey4(StringCriteriaField key4)
public void setKey5(StringCriteriaField key5)
public void setKey6(StringCriteriaField key6)
public void setValue1(StringCriteriaField value1)
public void setValue2(StringCriteriaField value2)
public void setValue3(StringCriteriaField value3)
public void setValue4(StringCriteriaField value4)
public void setValue5(StringCriteriaField value5)
public void setValue6(StringCriteriaField value6)
public String toString()
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutDto**

```
public void addRows(FormSelectionMaintenanceOutRowDto rows)
public void clearRows()
public FormSelectionMaintenanceOutRowDto getRows(int index)
public FormSelectionMaintenanceOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormSelectionMaintenanceOutRowDto rows)
public void setRows(FormSelectionMaintenanceOutRowDto rows, int index)
public void setRows(FormSelectionMaintenanceOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto**

```
public java.lang.String getAdditionalDataComponent()
public java.lang.Object getAdditionalDataObject()
public java.lang.Long getCopies()
public java.lang.String getEmail()
public java.lang.String getErrMessage()
public java.lang.String getEvent()
public java.lang.String getFormAlternateName()
public java.lang.String getFormName()
public java.lang.String getFormType()
public java.lang.String getFormVariation()
public java.lang.String getKey1()
public java.lang.String getKey2()
public java.lang.String getKey3()
public String getKey4()
public String getKey5()
public String getKey6()
public java.lang.String getPrinter()
public java.lang.Boolean getPublish()
public java.lang.Boolean getSelect()
public java.lang.String getValue1()
public java.lang.String getValue2()
public java.lang.String getValue3()
public String getValue4()
public String getValue5()
public String getValue6()
public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
public void setCopies(java.lang.Long copies)
public void setEmail(java.lang.String email)
public void setErrMessage(java.lang.String errMessage)
public void setEvent(java.lang.String event)
public void setFormAlternateName(java.lang.String formAlternateName)
public void setFormName(java.lang.String formName)
public void setFormType(java.lang.String formType)
public void setKey1(java.lang.String key1)
public void setKey2(java.lang.String key2)
public void setKey3(java.lang.String key3)
public void setKey4(String key4)
public void setKey5(String key5)
public void setKey6(String key6)
public void setPrinter(java.lang.String printer)
public void setPublish(java.lang.Boolean publish)
public void setSelect(java.lang.Boolean select)
public void setValue1(java.lang.String value1)
public void setValue2(java.lang.String value2)
public void setValue3(java.lang.String value3)
public void setValue4(String value4)
public void setValue5(String value5)
public void setValue6(String value6)
public void setVariation(java.lang.String formVariation)
public String toString()
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.tx.FormSelectionMaintenanceTx**

```
public void destroy()
public void dispatchPrintRequest(FormPrintRequest formPrintRequest) throws FrameworkException, ApplicationExceptions
public FormSelectionMaintenanceOutDto find(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions
public FormSelectionMaintenanceOutDto findOutDto(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions
public String getUserEmail()
public void setUserEmail(String email)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceAction**

```
public FormKey do_CancelResult_Clicked()
public FormKey do_Finish_Clicked()
public FormKey do_Preview_Clicked()
public FormKey do_Print_Clicked()
public FormKey do_Rows_ShowForm_Clicked(String rowNum)
public FormKey do_Rows_UpdateDetail_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public FormKey doFinish(GridModel model) throws ApplicationExceptions, FrameworkException
public FormKey doPrintForms(GridModel model) throws ApplicationExceptions, FrameworkException
public FormKey doShowForm(GridModel model) throws ApplicationExceptions, FrameworkException
public FormKey doShowForm(GridModelRow selectedRow) throws ApplicationExceptions, FrameworkException
public FormKey doUpdateDetail(GridModelRow selectedRow, String actionPath) throws ApplicationExceptions, FrameworkException
public java.lang.Object getAdditionalDataObject()
public boolean getAutoDisplayAll()
public String[] getDefaultPrinters()
public boolean getDirectDisplay()
public FormKey getDisplayFormKey()
public Boolean getDisplayResultsScreen()
public boolean getEmail()
public String getEmailAddress()
public String getEvent()
public String getEventDd()
public String getFileNames()
public String getFormUserId()
public String getKey1()
public String getKey1Dd()
public String getKey2()
public String getKey2Dd()
public String getKey3()
public String getKey3Dd()
public String getKey4()
public String getKey4Dd()
public String getKey5()
public String getKey5Dd()
public String getKey6()
public String getKey6Dd()
public String getPrinterList()
public boolean getPrinting()
public String getValue1()
public String getValue1Dd()
public String getValue2()
public String getValue2Dd()
public String getValue3()
public String getValue3Dd()
public String getValue4()
public String getValue4Dd()
public String getValue5()
public String getValue5Dd()
public String getValue6()
public String getValue6Dd()
public boolean getWebPublish()
public Boolean isFormConfigured() throws ApplicationExceptions, FrameworkException
public void print() throws ApplicationExceptions, Exception
public void quit()
public void setAdditionalDataObject(java.lang.Object m_additionalDataObject)
public void setAutoDisplayAll(boolean autoDisplayAll)
public void setDefaultPrinters(String[] defaultPrinters)
public void setDirectDisplay(boolean directDisplay)
public void setEmail(boolean email)
public void setEmailAddress(String EmailAddress)
public void setEvent(String event)
public void setEventDd(String eventDd)
public void setFileNames(String fileNames)
public void setFormUserId(String formUserId)
public void setKey1(String key1)
public void setKey1Dd(String key1Dd)
public void setKey2(String key2)
public void setKey2Dd(String key2Dd)
public void setKey3(String key3)
public void setKey3Dd(String key3Dd)
public void setKey4(String key4)
public void setKey4Dd(String key4Dd)
public void setKey5(String key5)
public void setKey5Dd(String key5Dd)
public void setKey6(String key6)
public void setKey6Dd(String key6Dd)
public void setPrinterList(String printerList)
public void setPrinting(boolean printing)
public void setValue1(String value1)
public void setValue1Dd(String value1Dd)
public void setValue2(String value2)
public void setValue2Dd(String value2Dd)
public void setValue3(String value3)
public void setValue3Dd(String value3Dd)
public void setValue4(String value4)
public void setValue4Dd(String value4Dd)
public void setValue5(String value5)
public void setValue5Dd(String value5Dd)
public void setValue6(String value6)
public void setValue6Dd(String value6Dd)
public void setWebPublish(boolean webPublish)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean getAutoDisplayAll()
public boolean getDirectDisplay()
public boolean getEmail()
public String getEvent()
public String getEventDd()
public DropDownModel getEventDdWM()
public EditBoxModel getEventWM()
public String getKey1()
public String getKey1Dd()
public DropDownModel getKey1DdWM()
public EditBoxModel getKey1WM()
public String getKey2()
public String getKey2Dd()
public DropDownModel getKey2DdWM()
public EditBoxModel getKey2WM()
public String getKey3()
public String getKey3Dd()
public DropDownModel getKey3DdWM()
public EditBoxModel getKey3WM()
public String getKey4()
public String getKey4Dd()
public DropDownModel getKey4DdWM()
public EditBoxModel getKey4WM()
public String getKey5()
public String getKey5Dd()
public DropDownModel getKey5DdWM()
public EditBoxModel getKey5WM()
public String getKey6()
public String getKey6Dd()
public DropDownModel getKey6DdWM()
public EditBoxModel getKey6WM()
public boolean getPrinting()
public String getValue1()
public String getValue1Dd()
public DropDownModel getValue1DdWM()
public EditBoxModel getValue1WM()
public String getValue2()
public String getValue2Dd()
public DropDownModel getValue2DdWM()
public EditBoxModel getValue2WM()
public String getValue3()
public String getValue3Dd()
public DropDownModel getValue3DdWM()
public EditBoxModel getValue3WM()
public String getValue4()
public String getValue4Dd()
public DropDownModel getValue4DdWM()
public EditBoxModel getValue4WM()
public String getValue5()
public String getValue5Dd()
public DropDownModel getValue5DdWM()
public EditBoxModel getValue5WM()
public String getValue6()
public String getValue6Dd()
public DropDownModel getValue6DdWM()
public EditBoxModel getValue6WM()
public boolean getWebPublish()
public void populateRows(GridModel rows)
public void setAutoDisplayAll(boolean autoDisplayAll)
public void setDirectDisplay(boolean directDisplay)
public void setEmail(boolean email)
public void setEvent(String event)
public void setEventDd(String eventDd)
public void setEventDdWV(String value)
public void setEventWV(String value)
public void setKey1(String key1)
public void setKey1Dd(String key1Dd)
public void setKey1DdWV(String value)
public void setKey1WV(String value)
public void setKey2(String key2)
public void setKey2Dd(String key2Dd)
public void setKey2DdWV(String value)
public void setKey2WV(String value)
public void setKey3(String key3)
public void setKey3Dd(String key3Dd)
public void setKey3DdWV(String value)
public void setKey3WV(String value)
public void setKey4(String key4)
public void setKey4Dd(String key4Dd)
public void setKey4DdWV(String value)
public void setKey4WV(String value)
public void setKey5(String key5)
public void setKey5Dd(String key5Dd)
public void setKey5DdWV(String value)
public void setKey5WV(String value)
public void setKey6(String key6)
public void setKey6Dd(String key6Dd)
public void setKey6DdWV(String value)
public void setKey6WV(String value)
public void setPrinting(boolean printing)
public void setValue1(String value1)
public void setValue1Dd(String value1Dd)
public void setValue1DdWV(String value)
public void setValue1WV(String value)
public void setValue2(String value2)
public void setValue2Dd(String value2Dd)
public void setValue2DdWV(String value)
public void setValue2WV(String value)
public void setValue3(String value3)
public void setValue3Dd(String value3Dd)
public void setValue3DdWV(String value)
public void setValue3WV(String value)
public void setValue4(String value4)
public void setValue4Dd(String value4Dd)
public void setValue4DdWV(String value)
public void setValue4WV(String value)
public void setValue5(String value5)
public void setValue5Dd(String value5Dd)
public void setValue5DdWV(String value)
public void setValue5WV(String value)
public void setValue6(String value6)
public void setValue6Dd(String value6Dd)
public void setValue6DdWV(String value)
public void setValue6WV(String value)
public void setWebPublish(boolean webPublish)
```
***
**org.jaffa.modules.printing.components.formselectionmaintenance.ui.ShowPdfServlet**

```
public void destroy()
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
public String getServletInfo()
public void init(ServletConfig config) throws ServletException
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.IFormTemplateFinder**

```
public void destroy()
public FormTemplateFinderOutDto find(FormTemplateFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderInDto**

```
public IntegerCriteriaField getFormId()
public HeaderDto getHeaderDto()
public RawCriteriaField getLayoutData()
public RawCriteriaField getTemplateData()
public void setFormId(IntegerCriteriaField formId)
public void setHeaderDto(HeaderDto headerDto)
public void setLayoutData(RawCriteriaField layoutData)
public void setTemplateData(RawCriteriaField templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderOutDto**

```
public void addRows(FormTemplateFinderOutRowDto rows)
public void clearRows()
public FormTemplateFinderOutRowDto getRows(int index)
public FormTemplateFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormTemplateFinderOutRowDto rows)
public void setRows(FormTemplateFinderOutRowDto rows, int index)
public void setRows(FormTemplateFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderOutRowDto**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void setFormId(java.lang.Long formId)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.tx.FormTemplateFinderTx**

```
public void destroy()
public FormTemplateFinderOutDto find(FormTemplateFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.ui.FormTemplateFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.ui.FormTemplateFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public String getFormId()
public String getFormIdDd()
public String getLayoutData()
public String getLayoutDataDd()
public String getTemplateData()
public String getTemplateDataDd()
public void quit()
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setLayoutData(String layoutData)
public void setLayoutDataDd(String layoutDataDd)
public void setTemplateData(String templateData)
public void setTemplateDataDd(String templateDataDd)
public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formtemplatefinder.ui.FormTemplateFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getFormId()
public String getFormIdDd()
public DropDownModel getFormIdDdWM()
public EditBoxModel getFormIdWM()
public String getLayoutData()
public String getLayoutDataDd()
public DropDownModel getLayoutDataDdWM()
public EditBoxModel getLayoutDataWM()
public String getTemplateData()
public String getTemplateDataDd()
public DropDownModel getTemplateDataDdWM()
public EditBoxModel getTemplateDataWM()
public void populateRows(GridModel rows)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormIdDdWV(String value)
public void setFormIdWV(String value)
public void setLayoutData(String layoutData)
public void setLayoutDataDd(String layoutDataDd)
public void setLayoutDataDdWV(String value)
public void setLayoutDataWV(String value)
public void setTemplateData(String templateData)
public void setTemplateDataDd(String templateDataDd)
public void setTemplateDataDdWV(String value)
public void setTemplateDataWV(String value)
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.IFormTemplateLookup**

```
public void destroy()
public FormTemplateLookupOutDto find(FormTemplateLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.dto.FormTemplateLookupInDto**

```
public IntegerCriteriaField getFormId()
public HeaderDto getHeaderDto()
public RawCriteriaField getLayoutData()
public RawCriteriaField getTemplateData()
public void setFormId(IntegerCriteriaField formId)
public void setHeaderDto(HeaderDto headerDto)
public void setLayoutData(RawCriteriaField layoutData)
public void setTemplateData(RawCriteriaField templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.dto.FormTemplateLookupOutDto**

```
public void addRows(FormTemplateLookupOutRowDto rows)
public void clearRows()
public FormTemplateLookupOutRowDto getRows(int index)
public FormTemplateLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormTemplateLookupOutRowDto rows)
public void setRows(FormTemplateLookupOutRowDto rows, int index)
public void setRows(FormTemplateLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.dto.FormTemplateLookupOutRowDto**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void setFormId(java.lang.Long formId)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.tx.FormTemplateLookupTx**

```
public void destroy()
public FormTemplateLookupOutDto find(FormTemplateLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.ui.FormTemplateLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.ui.FormTemplateLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public String getFormId()
public String getFormIdDd()
public String getLayoutData()
public String getLayoutDataDd()
public String getTemplateData()
public String getTemplateDataDd()
public void quit()
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setLayoutData(String layoutData)
public void setLayoutDataDd(String layoutDataDd)
public void setTemplateData(String templateData)
public void setTemplateDataDd(String templateDataDd)
public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formtemplatelookup.ui.FormTemplateLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getFormId()
public String getFormIdDd()
public DropDownModel getFormIdDdWM()
public EditBoxModel getFormIdWM()
public String getLayoutData()
public String getLayoutDataDd()
public DropDownModel getLayoutDataDdWM()
public EditBoxModel getLayoutDataWM()
public String getTemplateData()
public String getTemplateDataDd()
public DropDownModel getTemplateDataDdWM()
public EditBoxModel getTemplateDataWM()
public void populateRows(GridModel rows)
public void setFormId(String formId)
public void setFormIdDd(String formIdDd)
public void setFormIdDdWV(String value)
public void setFormIdWV(String value)
public void setLayoutData(String layoutData)
public void setLayoutDataDd(String layoutDataDd)
public void setLayoutDataDdWV(String value)
public void setLayoutDataWV(String value)
public void setTemplateData(String templateData)
public void setTemplateDataDd(String templateDataDd)
public void setTemplateDataDdWV(String value)
public void setTemplateDataWV(String value)
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.IFormTemplateMaintenance**

```
public FormTemplateMaintenanceRetrieveOutDto create(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormTemplateMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormTemplateMaintenancePrevalidateOutDto prevalidateCreate(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenancePrevalidateOutDto prevalidateUpdate(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenanceRetrieveOutDto retrieve(FormTemplateMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenanceRetrieveOutDto update(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenanceCreateInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenanceDeleteInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenanceRetrieveInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenanceRetrieveOutDto**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void setFormId(java.lang.Long formId)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.dto.FormTemplateMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.tx.FormTemplateMaintenanceTx**

```
public FormTemplateMaintenanceRetrieveOutDto create(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormTemplateMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormTemplateMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormTemplateMaintenancePrevalidateOutDto prevalidateCreate(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenancePrevalidateOutDto prevalidateUpdate(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenanceRetrieveOutDto retrieve(FormTemplateMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormTemplateMaintenanceRetrieveOutDto update(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceComponent**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void quit()
public void setFormId(java.lang.Long formId)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
```
***
**org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.Long getFormId()
public EditBoxModel getFormIdWM()
public byte[] getLayoutData()
public EditBoxModel getLayoutDataWM()
public byte[] getTemplateData()
public EditBoxModel getTemplateDataWM()
public void setFormId(java.lang.Long formId)
public void setFormIdWV(String value)
public void setLayoutData(byte[] layoutData)
public void setLayoutDataWV(String value)
public void setTemplateData(byte[] templateData)
public void setTemplateDataWV(String value)
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.IFormTemplateViewer**

```
public void destroy()
public FormTemplateViewerOutDto read(FormTemplateViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.dto.FormTemplateViewerInDto**

```
public java.lang.Long getFormId()
public HeaderDto getHeaderDto()
public void setFormId(java.lang.Long formId)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.dto.FormTemplateViewerOutDto**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void setFormId(java.lang.Long formId)
public void setLayoutData(byte[] layoutData)
public void setTemplateData(byte[] templateData)
public String toString()
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.tx.FormTemplateViewerTx**

```
public void destroy()
public FormTemplateViewerOutDto read(FormTemplateViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.ui.FormTemplateViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.ui.FormTemplateViewerComponent**

```
public FormKey deleteObject() throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.Long getFormId()
public FormTemplateViewerOutDto getFormTemplateViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setFormId(java.lang.Long formId)
public void setFormTemplateViewerOutDto(FormTemplateViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formtemplateviewer.ui.FormTemplateViewerForm**

```
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
```
***
**org.jaffa.modules.printing.components.formusagefinder.IFormUsageFinder**

```
public void destroy()
public FormUsageFinderOutDto find(FormUsageFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagefinder.dto.FormUsageFinderInDto**

```
public IntegerCriteriaField getCopies()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getEventName()
public StringCriteriaField getFormAlternate()
public StringCriteriaField getFormName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public void setCopies(IntegerCriteriaField copies)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setEventName(StringCriteriaField eventName)
public void setFormAlternate(StringCriteriaField formAlternate)
public void setFormName(StringCriteriaField formName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagefinder.dto.FormUsageFinderOutDto**

```
public void addRows(FormUsageFinderOutRowDto rows)
public void clearRows()
public FormUsageFinderOutRowDto getRows(int index)
public FormUsageFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormUsageFinderOutRowDto rows)
public void setRows(FormUsageFinderOutRowDto rows, int index)
public void setRows(FormUsageFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagefinder.dto.FormUsageFinderOutRowDto**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagefinder.tx.FormUsageFinderTx**

```
public void destroy()
public FormUsageFinderOutDto find(FormUsageFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagefinder.ui.FormUsageFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formusagefinder.ui.FormUsageFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public String getCopies()
public String getCopiesDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getEventName()
public String getEventNameDd()
public String getFormAlternate()
public String getFormAlternateDd()
public String getFormName()
public String getFormNameDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public void quit()
public void setCopies(String copies)
public void setCopiesDd(String copiesDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public FormKey updateObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formusagefinder.ui.FormUsageFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCopies()
public String getCopiesDd()
public DropDownModel getCopiesDdWM()
public EditBoxModel getCopiesWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getEventName()
public String getEventNameDd()
public DropDownModel getEventNameDdWM()
public EditBoxModel getEventNameWM()
public String getFormAlternate()
public String getFormAlternateDd()
public DropDownModel getFormAlternateDdWM()
public EditBoxModel getFormAlternateWM()
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public void populateRows(GridModel rows)
public void setCopies(String copies)
public void setCopiesDd(String copiesDd)
public void setCopiesDdWV(String value)
public void setCopiesWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setEventNameDdWV(String value)
public void setEventNameWV(String value)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormAlternateDdWV(String value)
public void setFormAlternateWV(String value)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
```
***
**org.jaffa.modules.printing.components.formusagelookup.IFormUsageLookup**

```
public void destroy()
public FormUsageLookupOutDto find(FormUsageLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagelookup.dto.FormUsageLookupInDto**

```
public IntegerCriteriaField getCopies()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getEventName()
public StringCriteriaField getFormAlternate()
public StringCriteriaField getFormName()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public void setCopies(IntegerCriteriaField copies)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setEventName(StringCriteriaField eventName)
public void setFormAlternate(StringCriteriaField formAlternate)
public void setFormName(StringCriteriaField formName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagelookup.dto.FormUsageLookupOutDto**

```
public void addRows(FormUsageLookupOutRowDto rows)
public void clearRows()
public FormUsageLookupOutRowDto getRows(int index)
public FormUsageLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(FormUsageLookupOutRowDto rows)
public void setRows(FormUsageLookupOutRowDto rows, int index)
public void setRows(FormUsageLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagelookup.dto.FormUsageLookupOutRowDto**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagelookup.tx.FormUsageLookupTx**

```
public void destroy()
public FormUsageLookupOutDto find(FormUsageLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagelookup.ui.FormUsageLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.formusagelookup.ui.FormUsageLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public String getCopies()
public String getCopiesDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getEventName()
public String getEventNameDd()
public String getFormAlternate()
public String getFormAlternateDd()
public String getFormName()
public String getFormNameDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public void quit()
public void setCopies(String copies)
public void setCopiesDd(String copiesDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public FormKey updateObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formusagelookup.ui.FormUsageLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCopies()
public String getCopiesDd()
public DropDownModel getCopiesDdWM()
public EditBoxModel getCopiesWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getEventName()
public String getEventNameDd()
public DropDownModel getEventNameDdWM()
public EditBoxModel getEventNameWM()
public String getFormAlternate()
public String getFormAlternateDd()
public DropDownModel getFormAlternateDdWM()
public EditBoxModel getFormAlternateWM()
public String getFormName()
public String getFormNameDd()
public DropDownModel getFormNameDdWM()
public EditBoxModel getFormNameWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public void populateRows(GridModel rows)
public void setCopies(String copies)
public void setCopiesDd(String copiesDd)
public void setCopiesDdWV(String value)
public void setCopiesWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setEventName(String eventName)
public void setEventNameDd(String eventNameDd)
public void setEventNameDdWV(String value)
public void setEventNameWV(String value)
public void setFormAlternate(String formAlternate)
public void setFormAlternateDd(String formAlternateDd)
public void setFormAlternateDdWV(String value)
public void setFormAlternateWV(String value)
public void setFormName(String formName)
public void setFormNameDd(String formNameDd)
public void setFormNameDdWV(String value)
public void setFormNameWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.IFormUsageMaintenance**

```
public FormUsageMaintenanceRetrieveOutDto create(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormUsageMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormUsageMaintenancePrevalidateOutDto prevalidateCreate(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenancePrevalidateOutDto prevalidateUpdate(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenanceRetrieveOutDto retrieve(FormUsageMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenanceRetrieveOutDto update(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenanceCreateInDto**

```
public java.lang.Long getCopies()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public void setCopies(java.lang.Long copies)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenanceDeleteInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenanceRetrieveInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenanceRetrieveOutDto**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.dto.FormUsageMaintenanceUpdateInDto**

```
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.tx.FormUsageMaintenanceTx**

```
public FormUsageMaintenanceRetrieveOutDto create(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormUsageMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(FormUsageMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public FormUsageMaintenancePrevalidateOutDto prevalidateCreate(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenancePrevalidateOutDto prevalidateUpdate(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenanceRetrieveOutDto retrieve(FormUsageMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public FormUsageMaintenanceRetrieveOutDto update(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.ui.FormUsageMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.ui.FormUsageMaintenanceComponent**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void quit()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
```
***
**org.jaffa.modules.printing.components.formusagemaintenance.ui.FormUsageMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.Long getCopies()
public EditBoxModel getCopiesWM()
public java.lang.String getCreatedBy()
public EditBoxModel getCreatedByWM()
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public java.lang.String getEventName()
public EditBoxModel getEventNameWM()
public java.lang.String getFormAlternate()
public EditBoxModel getFormAlternateWM()
public java.lang.String getFormName()
public EditBoxModel getFormNameWM()
public java.lang.String getLastChangedBy()
public EditBoxModel getLastChangedByWM()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public DateTimeModel getLastChangedOnWM()
public void setCopies(java.lang.Long copies)
public void setCopiesWV(String value)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedByWV(String value)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setEventName(java.lang.String eventName)
public void setEventNameWV(String value)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormAlternateWV(String value)
public void setFormName(java.lang.String formName)
public void setFormNameWV(String value)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedByWV(String value)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLastChangedOnWV(String value)
```
***
**org.jaffa.modules.printing.components.formusageviewer.IFormUsageViewer**

```
public void destroy()
public FormUsageViewerOutDto read(FormUsageViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusageviewer.dto.FormUsageViewerInDto**

```
public java.lang.String getFormName()
public HeaderDto getHeaderDto()
public void setFormName(java.lang.String formName)
public void setHeaderDto(HeaderDto headerDto)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusageviewer.dto.FormUsageViewerOutDto**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void setCopies(java.lang.Long copies)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setEventName(java.lang.String eventName)
public void setFormAlternate(java.lang.String formAlternate)
public void setFormName(java.lang.String formName)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public String toString()
```
***
**org.jaffa.modules.printing.components.formusageviewer.tx.FormUsageViewerTx**

```
public void destroy()
public FormUsageViewerOutDto read(FormUsageViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.formusageviewer.ui.FormUsageViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.formusageviewer.ui.FormUsageViewerComponent**

```
public FormKey deleteObject() throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getFormName()
public FormUsageViewerOutDto getFormUsageViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setFormName(java.lang.String formName)
public void setFormUsageViewerOutDto(FormUsageViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.formusageviewer.ui.FormUsageViewerForm**

```
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.IOutputCommandFinder**

```
public void destroy()
public OutputCommandFinderOutDto find(OutputCommandFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.dto.OutputCommandFinderInDto**

```
public StringCriteriaField getCommandLine()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOsPattern()
public IntegerCriteriaField getOutputCommandId()
public StringCriteriaField getOutputType()
public IntegerCriteriaField getSequenceNo()
public void setCommandLine(StringCriteriaField commandLine)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOsPattern(StringCriteriaField osPattern)
public void setOutputCommandId(IntegerCriteriaField outputCommandId)
public void setOutputType(StringCriteriaField outputType)
public void setSequenceNo(IntegerCriteriaField sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.dto.OutputCommandFinderOutDto**

```
public void addRows(OutputCommandFinderOutRowDto rows)
public void clearRows()
public OutputCommandFinderOutRowDto getRows(int index)
public OutputCommandFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(OutputCommandFinderOutRowDto rows)
public void setRows(OutputCommandFinderOutRowDto rows, int index)
public void setRows(OutputCommandFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.dto.OutputCommandFinderOutRowDto**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.tx.OutputCommandFinderTx**

```
public void destroy()
public OutputCommandFinderOutDto find(OutputCommandFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.ui.OutputCommandFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.ui.OutputCommandFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public String getCommandLine()
public String getCommandLineDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOsPattern()
public String getOsPatternDd()
public String getOutputCommandId()
public String getOutputCommandIdDd()
public String getOutputType()
public String getOutputTypeDd()
public String getSequenceNo()
public String getSequenceNoDd()
public void quit()
public void setCommandLine(String commandLine)
public void setCommandLineDd(String commandLineDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOsPattern(String osPattern)
public void setOsPatternDd(String osPatternDd)
public void setOutputCommandId(String outputCommandId)
public void setOutputCommandIdDd(String outputCommandIdDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setSequenceNo(String sequenceNo)
public void setSequenceNoDd(String sequenceNoDd)
public FormKey updateObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.outputcommandfinder.ui.OutputCommandFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCommandLine()
public String getCommandLineDd()
public DropDownModel getCommandLineDdWM()
public EditBoxModel getCommandLineWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOsPattern()
public String getOsPatternDd()
public DropDownModel getOsPatternDdWM()
public EditBoxModel getOsPatternWM()
public String getOutputCommandId()
public String getOutputCommandIdDd()
public DropDownModel getOutputCommandIdDdWM()
public EditBoxModel getOutputCommandIdWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public String getSequenceNo()
public String getSequenceNoDd()
public DropDownModel getSequenceNoDdWM()
public EditBoxModel getSequenceNoWM()
public void populateRows(GridModel rows)
public void setCommandLine(String commandLine)
public void setCommandLineDd(String commandLineDd)
public void setCommandLineDdWV(String value)
public void setCommandLineWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOsPattern(String osPattern)
public void setOsPatternDd(String osPatternDd)
public void setOsPatternDdWV(String value)
public void setOsPatternWV(String value)
public void setOutputCommandId(String outputCommandId)
public void setOutputCommandIdDd(String outputCommandIdDd)
public void setOutputCommandIdDdWV(String value)
public void setOutputCommandIdWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
public void setSequenceNo(String sequenceNo)
public void setSequenceNoDd(String sequenceNoDd)
public void setSequenceNoDdWV(String value)
public void setSequenceNoWV(String value)
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.IOutputCommandLookup**

```
public void destroy()
public OutputCommandLookupOutDto find(OutputCommandLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupInDto**

```
public StringCriteriaField getCommandLine()
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOsPattern()
public IntegerCriteriaField getOutputCommandId()
public StringCriteriaField getOutputType()
public IntegerCriteriaField getSequenceNo()
public void setCommandLine(StringCriteriaField commandLine)
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOsPattern(StringCriteriaField osPattern)
public void setOutputCommandId(IntegerCriteriaField outputCommandId)
public void setOutputType(StringCriteriaField outputType)
public void setSequenceNo(IntegerCriteriaField sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupOutDto**

```
public void addRows(OutputCommandLookupOutRowDto rows)
public void clearRows()
public OutputCommandLookupOutRowDto getRows(int index)
public OutputCommandLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(OutputCommandLookupOutRowDto rows)
public void setRows(OutputCommandLookupOutRowDto rows, int index)
public void setRows(OutputCommandLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupOutRowDto**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.tx.OutputCommandLookupTx**

```
public void destroy()
public OutputCommandLookupOutDto find(OutputCommandLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.ui.OutputCommandLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.ui.OutputCommandLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public String getCommandLine()
public String getCommandLineDd()
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOsPattern()
public String getOsPatternDd()
public String getOutputCommandId()
public String getOutputCommandIdDd()
public String getOutputType()
public String getOutputTypeDd()
public String getSequenceNo()
public String getSequenceNoDd()
public void quit()
public void setCommandLine(String commandLine)
public void setCommandLineDd(String commandLineDd)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOsPattern(String osPattern)
public void setOsPatternDd(String osPatternDd)
public void setOutputCommandId(String outputCommandId)
public void setOutputCommandIdDd(String outputCommandIdDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setSequenceNo(String sequenceNo)
public void setSequenceNoDd(String sequenceNoDd)
public FormKey updateObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.outputcommandlookup.ui.OutputCommandLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getCommandLine()
public String getCommandLineDd()
public DropDownModel getCommandLineDdWM()
public EditBoxModel getCommandLineWM()
public String getCreatedBy()
public String getCreatedByDd()
public DropDownModel getCreatedByDdWM()
public EditBoxModel getCreatedByWM()
public String getCreatedOn()
public String getCreatedOnDd()
public DropDownModel getCreatedOnDdWM()
public EditBoxModel getCreatedOnWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOsPattern()
public String getOsPatternDd()
public DropDownModel getOsPatternDdWM()
public EditBoxModel getOsPatternWM()
public String getOutputCommandId()
public String getOutputCommandIdDd()
public DropDownModel getOutputCommandIdDdWM()
public EditBoxModel getOutputCommandIdWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public String getSequenceNo()
public String getSequenceNoDd()
public DropDownModel getSequenceNoDdWM()
public EditBoxModel getSequenceNoWM()
public void populateRows(GridModel rows)
public void setCommandLine(String commandLine)
public void setCommandLineDd(String commandLineDd)
public void setCommandLineDdWV(String value)
public void setCommandLineWV(String value)
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedByDdWV(String value)
public void setCreatedByWV(String value)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setCreatedOnDdWV(String value)
public void setCreatedOnWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOsPattern(String osPattern)
public void setOsPatternDd(String osPatternDd)
public void setOsPatternDdWV(String value)
public void setOsPatternWV(String value)
public void setOutputCommandId(String outputCommandId)
public void setOutputCommandIdDd(String outputCommandIdDd)
public void setOutputCommandIdDdWV(String value)
public void setOutputCommandIdWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
public void setSequenceNo(String sequenceNo)
public void setSequenceNoDd(String sequenceNoDd)
public void setSequenceNoDdWV(String value)
public void setSequenceNoWV(String value)
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.IOutputCommandMaintenance**

```
public OutputCommandMaintenanceRetrieveOutDto create(OutputCommandMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(OutputCommandMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public Long getNextSequence(String outputType) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenancePrevalidateOutDto prevalidateCreate(OutputCommandMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenancePrevalidateOutDto prevalidateUpdate(OutputCommandMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenanceRetrieveOutDto retrieve(OutputCommandMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenanceRetrieveOutDto update(OutputCommandMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceCreateInDto**

```
public java.lang.String getCommandLine()
public HeaderDto getHeaderDto()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setHeaderDto(HeaderDto headerDto)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public java.lang.Long getOutputCommandId()
public Boolean getPerformDirtyReadCheck()
public void setHeaderDto(HeaderDto headerDto)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public java.lang.Long getOutputCommandId()
public void setHeaderDto(HeaderDto headerDto)
public void setOutputCommandId(java.lang.Long outputCommandId)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceRetrieveOutDto**

```
public java.lang.String getCommandLine()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.tx.OutputCommandMaintenanceTx**

```
public OutputCommandMaintenanceRetrieveOutDto create(OutputCommandMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(OutputCommandMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(OutputCommandMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public Long getNextSequence(String outputType) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenancePrevalidateOutDto prevalidateCreate(OutputCommandMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenancePrevalidateOutDto prevalidateUpdate(OutputCommandMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenanceRetrieveOutDto retrieve(OutputCommandMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public OutputCommandMaintenanceRetrieveOutDto update(OutputCommandMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.ui.OutputCommandMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.ui.OutputCommandMaintenanceComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getCommandLine()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void quit()
public void setCommandLine(java.lang.String commandLine)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
```
***
**org.jaffa.modules.printing.components.outputcommandmaintenance.ui.OutputCommandMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getCommandLine()
public EditBoxModel getCommandLineWM()
public java.lang.String getOsPattern()
public EditBoxModel getOsPatternWM()
public java.lang.Long getOutputCommandId()
public EditBoxModel getOutputCommandIdWM()
public java.lang.String getOutputType()
public EditBoxModel getOutputTypeWM()
public java.lang.Long getSequenceNo()
public EditBoxModel getSequenceNoWM()
public void setCommandLine(java.lang.String commandLine)
public void setCommandLineWV(String value)
public void setOsPattern(java.lang.String osPattern)
public void setOsPatternWV(String value)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputCommandIdWV(String value)
public void setOutputType(java.lang.String outputType)
public void setOutputTypeWV(String value)
public void setSequenceNo(java.lang.Long sequenceNo)
public void setSequenceNoWV(String value)
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.IOutputCommandViewer**

```
public void destroy()
public OutputCommandViewerOutDto read(OutputCommandViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.dto.OutputCommandViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.Long getOutputCommandId()
public void setHeaderDto(HeaderDto headerDto)
public void setOutputCommandId(java.lang.Long outputCommandId)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.dto.OutputCommandViewerOutDto**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.tx.OutputCommandViewerTx**

```
public void destroy()
public OutputCommandViewerOutDto read(OutputCommandViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.ui.OutputCommandViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Delete_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.ui.OutputCommandViewerComponent**

```
public FormKey deleteObject() throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.Long getOutputCommandId()
public OutputCommandViewerOutDto getOutputCommandViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputCommandViewerOutDto(OutputCommandViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.outputcommandviewer.ui.OutputCommandViewerForm**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.IPrinterDefinitionFinder**

```
public void destroy()
public PrinterDefinitionFinderOutDto find(PrinterDefinitionFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderInDto**

```
public StringCriteriaField getDescription()
public HeaderDto getHeaderDto()
public StringCriteriaField getLocationCode()
public StringCriteriaField getOutputType()
public StringCriteriaField getPrinterId()
public StringCriteriaField getRealPrinterName()
public BooleanCriteriaField getRemote()
public StringCriteriaField getScaleToPageSize()
public StringCriteriaField getSiteCode()
public void setDescription(StringCriteriaField description)
public void setHeaderDto(HeaderDto headerDto)
public void setLocationCode(StringCriteriaField locationCode)
public void setOutputType(StringCriteriaField outputType)
public void setPrinterId(StringCriteriaField printerId)
public void setRealPrinterName(StringCriteriaField realPrinterName)
public void setRemote(BooleanCriteriaField remote)
public void setScaleToPageSize(StringCriteriaField scaleToPageSize)
public void setSiteCode(StringCriteriaField siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderOutDto**

```
public void addRows(PrinterDefinitionFinderOutRowDto rows)
public void clearRows()
public PrinterDefinitionFinderOutRowDto getRows(int index)
public PrinterDefinitionFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(PrinterDefinitionFinderOutRowDto rows)
public void setRows(PrinterDefinitionFinderOutRowDto rows, int index)
public void setRows(PrinterDefinitionFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void setDescription(java.lang.String description)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.tx.PrinterDefinitionFinderTx**

```
public void destroy()
public PrinterDefinitionFinderOutDto find(PrinterDefinitionFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.ui.PrinterDefinitionFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.ui.PrinterDefinitionFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
public String getDescription()
public String getDescriptionDd()
public String getLocationCode()
public String getLocationCodeDd()
public String getOutputType()
public CodeHelperOutElementDto getOutputTypeCodes()
public String getOutputTypeDd()
public String getPrinterId()
public String getPrinterIdDd()
public String getRealPrinterName()
public String getRealPrinterNameDd()
public String getRemote()
public String getScaleToPageSize()
public CodeHelperOutElementDto getScaleToPageSizeCodes()
public String getScaleToPageSizeDd()
public String getSiteCode()
public String getSiteCodeDd()
public void quit()
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setLocationCode(String locationCode)
public void setLocationCodeDd(String locationCodeDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setPrinterId(String printerId)
public void setPrinterIdDd(String printerIdDd)
public void setRealPrinterName(String realPrinterName)
public void setRealPrinterNameDd(String realPrinterNameDd)
public void setRemote(String remote)
public void setScaleToPageSize(String scaleToPageSize)
public void setScaleToPageSizeDd(String scaleToPageSizeDd)
public void setSiteCode(String siteCode)
public void setSiteCodeDd(String siteCodeDd)
public FormKey updateObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printerdefinitionfinder.ui.PrinterDefinitionFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getLocationCode()
public String getLocationCodeDd()
public DropDownModel getLocationCodeDdWM()
public EditBoxModel getLocationCodeWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public DropDownModel getOutputTypeWM()
public String getPrinterId()
public String getPrinterIdDd()
public DropDownModel getPrinterIdDdWM()
public EditBoxModel getPrinterIdWM()
public String getRealPrinterName()
public String getRealPrinterNameDd()
public DropDownModel getRealPrinterNameDdWM()
public EditBoxModel getRealPrinterNameWM()
public String getRemote()
public DropDownModel getRemoteWM()
public String getScaleToPageSize()
public String getScaleToPageSizeDd()
public DropDownModel getScaleToPageSizeDdWM()
public DropDownModel getScaleToPageSizeWM()
public String getSiteCode()
public String getSiteCodeDd()
public DropDownModel getSiteCodeDdWM()
public EditBoxModel getSiteCodeWM()
public void populateRows(GridModel rows)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setLocationCode(String locationCode)
public void setLocationCodeDd(String locationCodeDd)
public void setLocationCodeDdWV(String value)
public void setLocationCodeWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
public void setPrinterId(String printerId)
public void setPrinterIdDd(String printerIdDd)
public void setPrinterIdDdWV(String value)
public void setPrinterIdWV(String value)
public void setRealPrinterName(String realPrinterName)
public void setRealPrinterNameDd(String realPrinterNameDd)
public void setRealPrinterNameDdWV(String value)
public void setRealPrinterNameWV(String value)
public void setRemote(String remote)
public void setRemoteWV(String value)
public void setScaleToPageSize(String scaleToPageSize)
public void setScaleToPageSizeDd(String scaleToPageSizeDd)
public void setScaleToPageSizeDdWV(String value)
public void setScaleToPageSizeWV(String value)
public void setSiteCode(String siteCode)
public void setSiteCodeDd(String siteCodeDd)
public void setSiteCodeDdWV(String value)
public void setSiteCodeWV(String value)
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.IPrinterDefinitionLookup**

```
public void destroy()
public PrinterDefinitionLookupOutDto find(PrinterDefinitionLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.dto.PrinterDefinitionLookupInDto**

```
public StringCriteriaField getDescription()
public HeaderDto getHeaderDto()
public StringCriteriaField getLocationCode()
public StringCriteriaField getOutputType()
public StringCriteriaField getPrinterId()
public StringCriteriaField getRealPrinterName()
public BooleanCriteriaField getRemote()
public StringCriteriaField getScaleToPageSize()
public StringCriteriaField getSiteCode()
public void setDescription(StringCriteriaField description)
public void setHeaderDto(HeaderDto headerDto)
public void setLocationCode(StringCriteriaField locationCode)
public void setOutputType(StringCriteriaField outputType)
public void setPrinterId(StringCriteriaField printerId)
public void setRealPrinterName(StringCriteriaField realPrinterName)
public void setRemote(BooleanCriteriaField remote)
public void setScaleToPageSize(StringCriteriaField scaleToPageSize)
public void setSiteCode(StringCriteriaField siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.dto.PrinterDefinitionLookupOutDto**

```
public void addRows(PrinterDefinitionLookupOutRowDto rows)
public void clearRows()
public PrinterDefinitionLookupOutRowDto getRows(int index)
public PrinterDefinitionLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(PrinterDefinitionLookupOutRowDto rows)
public void setRows(PrinterDefinitionLookupOutRowDto rows, int index)
public void setRows(PrinterDefinitionLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.dto.PrinterDefinitionLookupOutRowDto**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void setDescription(java.lang.String description)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.tx.PrinterDefinitionLookupTx**

```
public void destroy()
public PrinterDefinitionLookupOutDto find(PrinterDefinitionLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.ui.PrinterDefinitionLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.ui.PrinterDefinitionLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
public String getDescription()
public String getDescriptionDd()
public String getLocationCode()
public String getLocationCodeDd()
public String getOutputType()
public CodeHelperOutElementDto getOutputTypeCodes()
public String getOutputTypeDd()
public String getPrinterId()
public String getPrinterIdDd()
public String getRealPrinterName()
public String getRealPrinterNameDd()
public String getRemote()
public String getScaleToPageSize()
public CodeHelperOutElementDto getScaleToPageSizeCodes()
public String getScaleToPageSizeDd()
public String getSiteCode()
public String getSiteCodeDd()
public void quit()
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setLocationCode(String locationCode)
public void setLocationCodeDd(String locationCodeDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setPrinterId(String printerId)
public void setPrinterIdDd(String printerIdDd)
public void setRealPrinterName(String realPrinterName)
public void setRealPrinterNameDd(String realPrinterNameDd)
public void setRemote(String remote)
public void setScaleToPageSize(String scaleToPageSize)
public void setScaleToPageSizeDd(String scaleToPageSizeDd)
public void setSiteCode(String siteCode)
public void setSiteCodeDd(String siteCodeDd)
public FormKey updateObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printerdefinitionlookup.ui.PrinterDefinitionLookupForm**

```
public boolean doValidate(HttpServletRequest request)
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getLocationCode()
public String getLocationCodeDd()
public DropDownModel getLocationCodeDdWM()
public EditBoxModel getLocationCodeWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public DropDownModel getOutputTypeWM()
public String getPrinterId()
public String getPrinterIdDd()
public DropDownModel getPrinterIdDdWM()
public EditBoxModel getPrinterIdWM()
public String getRealPrinterName()
public String getRealPrinterNameDd()
public DropDownModel getRealPrinterNameDdWM()
public EditBoxModel getRealPrinterNameWM()
public String getRemote()
public DropDownModel getRemoteWM()
public String getScaleToPageSize()
public String getScaleToPageSizeDd()
public DropDownModel getScaleToPageSizeDdWM()
public DropDownModel getScaleToPageSizeWM()
public String getSiteCode()
public String getSiteCodeDd()
public DropDownModel getSiteCodeDdWM()
public EditBoxModel getSiteCodeWM()
public void populateRows(GridModel rows)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setLocationCode(String locationCode)
public void setLocationCodeDd(String locationCodeDd)
public void setLocationCodeDdWV(String value)
public void setLocationCodeWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
public void setPrinterId(String printerId)
public void setPrinterIdDd(String printerIdDd)
public void setPrinterIdDdWV(String value)
public void setPrinterIdWV(String value)
public void setRealPrinterName(String realPrinterName)
public void setRealPrinterNameDd(String realPrinterNameDd)
public void setRealPrinterNameDdWV(String value)
public void setRealPrinterNameWV(String value)
public void setRemote(String remote)
public void setRemoteWV(String value)
public void setScaleToPageSize(String scaleToPageSize)
public void setScaleToPageSizeDd(String scaleToPageSizeDd)
public void setScaleToPageSizeDdWV(String value)
public void setScaleToPageSizeWV(String value)
public void setSiteCode(String siteCode)
public void setSiteCodeDd(String siteCodeDd)
public void setSiteCodeDdWV(String value)
public void setSiteCodeWV(String value)
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.IPrinterDefinitionMaintenance**

```
public PrinterDefinitionMaintenanceRetrieveOutDto create(PrinterDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public PrinterDefinitionMaintenancePrevalidateOutDto prevalidateCreate(PrinterDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenancePrevalidateOutDto prevalidateUpdate(PrinterDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenanceRetrieveOutDto retrieve(PrinterDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenanceRetrieveOutDto update(PrinterDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceCreateInDto**

```
public java.lang.String getDescription()
public HeaderDto getHeaderDto()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void setDescription(java.lang.String description)
public void setHeaderDto(HeaderDto headerDto)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setPrinterOption1(java.lang.String printerOption1)
public void setPrinterOption2(java.lang.String printerOption2)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public Boolean getPerformDirtyReadCheck()
public java.lang.String getPrinterId()
public void setHeaderDto(HeaderDto headerDto)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public void setPrinterId(java.lang.String printerId)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getPrinterId()
public void setHeaderDto(HeaderDto headerDto)
public void setPrinterId(java.lang.String printerId)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceRetrieveOutDto**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void setDescription(java.lang.String description)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setPrinterOption1(java.lang.String printerOption1)
public void setPrinterOption2(java.lang.String printerOption2)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.PrinterDefinitionMaintenanceUpdateInDto**

```
public Boolean getPerformDirtyReadCheck()
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.tx.PrinterDefinitionMaintenanceTx**

```
public PrinterDefinitionMaintenanceRetrieveOutDto create(PrinterDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterDefinitionMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public PrinterDefinitionMaintenancePrevalidateOutDto prevalidateCreate(PrinterDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenancePrevalidateOutDto prevalidateUpdate(PrinterDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenanceRetrieveOutDto retrieve(PrinterDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public PrinterDefinitionMaintenanceRetrieveOutDto update(PrinterDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceAction**

```
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceComponent**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public CodeHelperOutElementDto getOutputTypeCodes()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public CodeHelperOutElementDto getScaleToPageSizeCodes()
public java.lang.String getSiteCode()
public void quit()
public void setDescription(java.lang.String description)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setPrinterOption1(java.lang.String printerOption1)
public void setPrinterOption2(java.lang.String printerOption2)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
```
***
**org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public java.lang.String getLocationCode()
public EditBoxModel getLocationCodeWM()
public java.lang.String getOutputType()
public DropDownModel getOutputTypeWM()
public java.lang.String getPrinterId()
public EditBoxModel getPrinterIdWM()
public java.lang.String getPrinterOption1()
public EditBoxModel getPrinterOption1WM()
public java.lang.String getPrinterOption2()
public EditBoxModel getPrinterOption2WM()
public java.lang.String getRealPrinterName()
public EditBoxModel getRealPrinterNameWM()
public java.lang.Boolean getRemote()
public CheckBoxModel getRemoteWM()
public java.lang.String getScaleToPageSize()
public DropDownModel getScaleToPageSizeWM()
public java.lang.String getSiteCode()
public EditBoxModel getSiteCodeWM()
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setLocationCode(java.lang.String locationCode)
public void setLocationCodeWV(String value)
public void setOutputType(java.lang.String outputType)
public void setOutputTypeWV(String value)
public void setPrinterId(java.lang.String printerId)
public void setPrinterIdWV(String value)
public void setPrinterOption1(java.lang.String printerOption1)
public void setPrinterOption1WV(String value)
public void setPrinterOption2(java.lang.String printerOption2)
public void setPrinterOption2WV(String value)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRealPrinterNameWV(String value)
public void setRemote(java.lang.Boolean remote)
public void setRemoteWV(String value)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setScaleToPageSizeWV(String value)
public void setSiteCode(java.lang.String siteCode)
public void setSiteCodeWV(String value)
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.IPrinterDefinitionViewer**

```
public void destroy()
public PrinterDefinitionViewerOutDto read(PrinterDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.dto.PrinterDefinitionViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getPrinterId()
public void setHeaderDto(HeaderDto headerDto)
public void setPrinterId(java.lang.String printerId)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.dto.PrinterDefinitionViewerOutDto**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void setDescription(java.lang.String description)
public void setLocationCode(java.lang.String locationCode)
public void setOutputType(java.lang.String outputType)
public void setPrinterId(java.lang.String printerId)
public void setPrinterOption1(java.lang.String printerOption1)
public void setPrinterOption2(java.lang.String printerOption2)
public void setRealPrinterName(java.lang.String realPrinterName)
public void setRemote(java.lang.Boolean remote)
public void setScaleToPageSize(java.lang.String scaleToPageSize)
public void setSiteCode(java.lang.String siteCode)
public String toString()
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.tx.PrinterDefinitionViewerTx**

```
public void destroy()
public PrinterDefinitionViewerOutDto read(PrinterDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.ui.PrinterDefinitionViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.ui.PrinterDefinitionViewerComponent**

```
public FormKey display() throws ApplicationExceptions, FrameworkException
public PrinterDefinitionViewerOutDto getPrinterDefinitionViewerOutDto()
public java.lang.String getPrinterId()
public FormKey getViewerFormKey()
public void quit()
public void setPrinterDefinitionViewerOutDto(PrinterDefinitionViewerOutDto outputDto)
public void setPrinterId(java.lang.String printerId)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printerdefinitionviewer.ui.PrinterDefinitionViewerForm**

```
public java.lang.String getDescription()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public CheckBoxModel getRemoteWM()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.IPrinterOutputTypeFinder**

```
public void destroy()
public PrinterOutputTypeFinderOutDto find(PrinterOutputTypeFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderInDto**

```
public StringCriteriaField getDescription()
public BooleanCriteriaField getDirectPrinting()
public HeaderDto getHeaderDto()
public StringCriteriaField getOutputType()
public void setDescription(StringCriteriaField description)
public void setDirectPrinting(BooleanCriteriaField directPrinting)
public void setHeaderDto(HeaderDto headerDto)
public void setOutputType(StringCriteriaField outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderOutDto**

```
public void addRows(PrinterOutputTypeFinderOutRowDto rows)
public void clearRows()
public PrinterOutputTypeFinderOutRowDto getRows(int index)
public PrinterOutputTypeFinderOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(PrinterOutputTypeFinderOutRowDto rows)
public void setRows(PrinterOutputTypeFinderOutRowDto rows, int index)
public void setRows(PrinterOutputTypeFinderOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderOutRowDto**

```
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getOutputType()
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.tx.PrinterOutputTypeFinderTx**

```
public void destroy()
public PrinterOutputTypeFinderOutDto find(PrinterOutputTypeFinderInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.ui.PrinterOutputTypeFinderAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Clicked(String rowNum)
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.ui.PrinterOutputTypeFinderComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
public String getDescription()
public String getDescriptionDd()
public String getDirectPrinting()
public String getOutputType()
public String getOutputTypeDd()
public void quit()
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDirectPrinting(String directPrinting)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public FormKey updateObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printeroutputtypefinder.ui.PrinterOutputTypeFinderForm**

```
public boolean doValidate(HttpServletRequest request)
public String getDescription()
public String getDescriptionDd()
public DropDownModel getDescriptionDdWM()
public EditBoxModel getDescriptionWM()
public String getDirectPrinting()
public DropDownModel getDirectPrintingWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public void populateRows(GridModel rows)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDescriptionDdWV(String value)
public void setDescriptionWV(String value)
public void setDirectPrinting(String directPrinting)
public void setDirectPrintingWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.IPrinterOutputTypeLookup**

```
public void destroy()
public PrinterOutputTypeLookupOutDto find(PrinterOutputTypeLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.dto.PrinterOutputTypeLookupInDto**

```
public StringCriteriaField getCreatedBy()
public DateTimeCriteriaField getCreatedOn()
public StringCriteriaField getDescription()
public BooleanCriteriaField getDirectPrinting()
public HeaderDto getHeaderDto()
public StringCriteriaField getLastChangedBy()
public DateTimeCriteriaField getLastChangedOn()
public StringCriteriaField getOutputType()
public void setCreatedBy(StringCriteriaField createdBy)
public void setCreatedOn(DateTimeCriteriaField createdOn)
public void setDescription(StringCriteriaField description)
public void setDirectPrinting(BooleanCriteriaField directPrinting)
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedBy(StringCriteriaField lastChangedBy)
public void setLastChangedOn(DateTimeCriteriaField lastChangedOn)
public void setOutputType(StringCriteriaField outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.dto.PrinterOutputTypeLookupOutDto**

```
public void addRows(PrinterOutputTypeLookupOutRowDto rows)
public void clearRows()
public PrinterOutputTypeLookupOutRowDto getRows(int index)
public PrinterOutputTypeLookupOutRowDto[] getRows()
public int getRowsCount()
public boolean removeRows(PrinterOutputTypeLookupOutRowDto rows)
public void setRows(PrinterOutputTypeLookupOutRowDto rows, int index)
public void setRows(PrinterOutputTypeLookupOutRowDto[] rows)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.dto.PrinterOutputTypeLookupOutRowDto**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.tx.PrinterOutputTypeLookupTx**

```
public void destroy()
public PrinterOutputTypeLookupOutDto find(PrinterOutputTypeLookupInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.ui.PrinterOutputTypeLookupAction**

```
public FormKey do_CreateFromCriteria_Clicked()
public FormKey do_CreateFromResults_Clicked()
public FormKey do_Rows_Delete_Clicked(String rowNum)
public FormKey do_Rows_Update_Clicked(String rowNum)
public FormKey do_Rows_View_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.ui.PrinterOutputTypeLookupComponent**

```
public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException
public FormKey createFromResults() throws ApplicationExceptions, FrameworkException
public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException
public FormKey deleteObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
public String getCreatedBy()
public String getCreatedByDd()
public String getCreatedOn()
public String getCreatedOnDd()
public String getDescription()
public String getDescriptionDd()
public String getDirectPrinting()
public String getLastChangedBy()
public String getLastChangedByDd()
public String getLastChangedOn()
public String getLastChangedOnDd()
public String getOutputType()
public String getOutputTypeDd()
public void quit()
public void setCreatedBy(String createdBy)
public void setCreatedByDd(String createdByDd)
public void setCreatedOn(String createdOn)
public void setCreatedOnDd(String createdOnDd)
public void setDescription(String description)
public void setDescriptionDd(String descriptionDd)
public void setDirectPrinting(String directPrinting)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public FormKey updateObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
public FormKey viewObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printeroutputtypelookup.ui.PrinterOutputTypeLookupForm**

```
public boolean doValidate(HttpServletRequest request)
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
public String getDirectPrinting()
public DropDownModel getDirectPrintingWM()
public String getLastChangedBy()
public String getLastChangedByDd()
public DropDownModel getLastChangedByDdWM()
public EditBoxModel getLastChangedByWM()
public String getLastChangedOn()
public String getLastChangedOnDd()
public DropDownModel getLastChangedOnDdWM()
public EditBoxModel getLastChangedOnWM()
public String getOutputType()
public String getOutputTypeDd()
public DropDownModel getOutputTypeDdWM()
public EditBoxModel getOutputTypeWM()
public void populateRows(GridModel rows)
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
public void setDirectPrinting(String directPrinting)
public void setDirectPrintingWV(String value)
public void setLastChangedBy(String lastChangedBy)
public void setLastChangedByDd(String lastChangedByDd)
public void setLastChangedByDdWV(String value)
public void setLastChangedByWV(String value)
public void setLastChangedOn(String lastChangedOn)
public void setLastChangedOnDd(String lastChangedOnDd)
public void setLastChangedOnDdWV(String value)
public void setLastChangedOnWV(String value)
public void setOutputType(String outputType)
public void setOutputTypeDd(String outputTypeDd)
public void setOutputTypeDdWV(String value)
public void setOutputTypeWV(String value)
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.IPrinterOutputTypeMaintenance**

```
public PrinterOutputTypeMaintenanceRetrieveOutDto create(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterOutputTypeMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void destroy()
public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateCreate(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateUpdate(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenanceRetrieveOutDto retrieve(PrinterOutputTypeMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenanceRetrieveOutDto update(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.OutputCommandDto**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenanceCreateInDto**

```
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public HeaderDto getHeaderDto()
public java.lang.String getOutputType()
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setHeaderDto(HeaderDto headerDto)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenanceDeleteInDto**

```
public HeaderDto getHeaderDto()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public Boolean getPerformDirtyReadCheck()
public void setHeaderDto(HeaderDto headerDto)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenancePrevalidateOutDto**

```
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenanceRetrieveInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getOutputType()
public void setHeaderDto(HeaderDto headerDto)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenanceRetrieveOutDto**

```
public void addOutputCommand(OutputCommandDto outputCommand)
public void clearOutputCommand()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public OutputCommandDto getOutputCommand(int index)
public OutputCommandDto[] getOutputCommand()
public int getOutputCommandCount()
public java.lang.String getOutputType()
public boolean removeOutputCommand(OutputCommandDto outputCommand)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputCommand(OutputCommandDto outputCommand, int index)
public void setOutputCommand(OutputCommandDto[] objects)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.PrinterOutputTypeMaintenanceUpdateInDto**

```
public org.jaffa.datatypes.DateTime getLastChangedOn()
public Boolean getPerformDirtyReadCheck()
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.tx.PrinterOutputTypeMaintenanceTx**

```
public PrinterOutputTypeMaintenanceRetrieveOutDto create(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterOutputTypeMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions
public void delete(PrinterOutputTypeMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions
public void destroy()
public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateCreate(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateUpdate(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenanceRetrieveOutDto retrieve(PrinterOutputTypeMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions
public PrinterOutputTypeMaintenanceRetrieveOutDto update(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceAction**

```
public FormKey do_RelatedOutputCommand_Create_Clicked()
public FormKey do_RelatedOutputCommand_Delete_Clicked(String rowNum)
public FormKey do_RelatedOutputCommand_Update_Clicked(String rowNum)
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceComponent**

```
public FormKey createOutputCommand() throws ApplicationExceptions, FrameworkException
public FormKey deleteOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public OutputCommandDto[] getRelatedObjectOutputCommandDto()
public void quit()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputType(java.lang.String outputType)
public FormKey updateOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceForm**

```
public boolean doValidate(HttpServletRequest request)
public boolean doValidate0(HttpServletRequest request)
public java.lang.String getCreatedBy()
public EditBoxModel getCreatedByWM()
public org.jaffa.datatypes.DateTime getCreatedOn()
public DateTimeModel getCreatedOnWM()
public java.lang.String getDescription()
public EditBoxModel getDescriptionWM()
public java.lang.Boolean getDirectPrinting()
public CheckBoxModel getDirectPrintingWM()
public java.lang.String getLastChangedBy()
public EditBoxModel getLastChangedByWM()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public DateTimeModel getLastChangedOnWM()
public java.lang.String getOutputType()
public EditBoxModel getOutputTypeWM()
public GridModel getRelatedOutputCommandWM()
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedByWV(String value)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setCreatedOnWV(String value)
public void setDescription(java.lang.String description)
public void setDescriptionWV(String value)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setDirectPrintingWV(String value)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedByWV(String value)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setLastChangedOnWV(String value)
public void setOutputType(java.lang.String outputType)
public void setOutputTypeWV(String value)
public void setRelatedOutputCommandWV(String value)
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.IPrinterOutputTypeViewer**

```
public void destroy()
public PrinterOutputTypeViewerOutDto read(PrinterOutputTypeViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.OutputCommandDto**

```
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public java.lang.Long getSequenceNo()
public void setCommandLine(java.lang.String commandLine)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOsPattern(java.lang.String osPattern)
public void setOutputCommandId(java.lang.Long outputCommandId)
public void setOutputType(java.lang.String outputType)
public void setSequenceNo(java.lang.Long sequenceNo)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.PrinterOutputTypeViewerInDto**

```
public HeaderDto getHeaderDto()
public java.lang.String getOutputType()
public void setHeaderDto(HeaderDto headerDto)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.PrinterOutputTypeViewerOutDto**

```
public void addOutputCommand(OutputCommandDto outputCommand)
public void clearOutputCommand()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public OutputCommandDto getOutputCommand(int index)
public OutputCommandDto[] getOutputCommand()
public int getOutputCommandCount()
public java.lang.String getOutputType()
public boolean removeOutputCommand(OutputCommandDto outputCommand)
public void setCreatedBy(java.lang.String createdBy)
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
public void setDescription(java.lang.String description)
public void setDirectPrinting(java.lang.Boolean directPrinting)
public void setLastChangedBy(java.lang.String lastChangedBy)
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
public void setOutputCommand(OutputCommandDto outputCommand, int index)
public void setOutputCommand(OutputCommandDto[] objects)
public void setOutputType(java.lang.String outputType)
public String toString()
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.tx.PrinterOutputTypeViewerTx**

```
public void destroy()
public PrinterOutputTypeViewerOutDto read(PrinterOutputTypeViewerInDto input) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.ui.PrinterOutputTypeViewerAction**

```
public FormKey do_Close_Clicked()
public FormKey do_RelatedOutputCommand_Delete_Clicked(String rowNum)
public FormKey do_RelatedOutputCommand_Update_Clicked(String rowNum)
public FormKey do_Update_Clicked()
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.ui.PrinterOutputTypeViewerComponent**

```
public FormKey deleteOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
public FormKey display() throws ApplicationExceptions, FrameworkException
public java.lang.String getOutputType()
public PrinterOutputTypeViewerOutDto getPrinterOutputTypeViewerOutDto()
public FormKey getViewerFormKey()
public void quit()
public void setOutputType(java.lang.String outputType)
public void setPrinterOutputTypeViewerOutDto(PrinterOutputTypeViewerOutDto outputDto)
public FormKey updateObject() throws ApplicationExceptions, FrameworkException
public FormKey updateOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException
```
***
**org.jaffa.modules.printing.components.printeroutputtypeviewer.ui.PrinterOutputTypeViewerForm**

```
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public CheckBoxModel getDirectPrintingWM()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public GridModel getRelatedOutputCommandWM()
public void setRelatedOutputCommandWV(String value)
```
***
**org.jaffa.modules.printing.domain.CompositeKey**

```
public boolean equals(Object obj)
public java.lang.String getEventName()
public java.lang.String getFormName()
public int hashCode()
public void setEventName(java.lang.String eventName)
public void setFormName(java.lang.String formName)
```
***
**org.jaffa.modules.printing.domain.FormDefinition**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.Long formId) throws FrameworkException
public static FormDefinition findByCK(UOW uow, String formName, String formAlternate, String formVariation, String outputType) throws FrameworkException
public static Criteria findByCKCriteria(String formName, String formAlternate, String formVariation, String outputType)
public static FormDefinition findByPK(UOW uow, java.lang.Long formId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.Long formId)
public java.lang.String getAdditionalDataComponent()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getDomClass()
public java.lang.String getDomFactory()
public java.lang.String getDomKey1()
public java.lang.String getDomKey2()
public java.lang.String getDomKey3()
public java.lang.String getDomKey4()
public java.lang.String getDomKey5()
public java.lang.String getDomKey6()
public java.lang.String getFieldLayout()
public java.lang.String getFollowOnFormAlternate()
public java.lang.String getFollowOnFormName()
public java.lang.String getFormAlternate()
public FormGroup getFormGroupObject() throws ValidationException, FrameworkException
public java.lang.Long getFormId()
public java.lang.String getFormName()
public java.lang.String getFormTemplate()
public FormTemplate getFormTemplateObject() throws FrameworkException
public java.lang.String getFormVariation()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOutputType()
public PrinterOutputType getPrinterOutputTypeObject() throws ValidationException, FrameworkException
public java.lang.String getRemarks()
public FormTemplate newFormTemplateObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setAdditionalDataComponent(java.lang.String additionalDataComponent) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomClass(java.lang.String domClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomFactory(java.lang.String domFactory) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey1(java.lang.String domKey1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey2(java.lang.String domKey2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey3(java.lang.String domKey3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey4(java.lang.String domKey4) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey5(java.lang.String domKey5) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDomKey6(java.lang.String domKey6) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFieldLayout(java.lang.String fieldLayout) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFollowOnFormName(java.lang.String followOnFormName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormAlternate(java.lang.String formAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormId(java.lang.Long formId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormName(java.lang.String formName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormTemplate(java.lang.String formTemplate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormVariation(java.lang.String formVariation) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateAdditionalDataComponent(java.lang.String additionalDataComponent) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomClass(java.lang.String domClass) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomFactory(java.lang.String domFactory) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey1(java.lang.String domKey1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey2(java.lang.String domKey2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey3(java.lang.String domKey3) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey4(java.lang.String domKey4) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey5(java.lang.String domKey5) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDomKey6(java.lang.String domKey6) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFieldLayout(java.lang.String fieldLayout) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFollowOnFormAlternate(java.lang.String followOnFormAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFollowOnFormName(java.lang.String followOnFormName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormAlternate(java.lang.String formAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormId(java.lang.Long formId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormName(java.lang.String formName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormTemplate(java.lang.String formTemplate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormVariation(java.lang.String formVariation) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemarks(java.lang.String remarks) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateAdditionalDataComponent(java.lang.String additionalDataComponent) throws ValidationException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateDomClass(java.lang.String domClass) throws ValidationException, FrameworkException
public java.lang.String validateDomFactory(java.lang.String domFactory) throws ValidationException, FrameworkException
public java.lang.String validateDomKey1(java.lang.String domKey1) throws ValidationException, FrameworkException
public java.lang.String validateDomKey2(java.lang.String domKey2) throws ValidationException, FrameworkException
public java.lang.String validateDomKey3(java.lang.String domKey3) throws ValidationException, FrameworkException
public java.lang.String validateDomKey4(java.lang.String domKey4) throws ValidationException, FrameworkException
public java.lang.String validateDomKey5(java.lang.String domKey5) throws ValidationException, FrameworkException
public java.lang.String validateDomKey6(java.lang.String domKey6) throws ValidationException, FrameworkException
public java.lang.String validateFieldLayout(java.lang.String fieldLayout) throws ValidationException, FrameworkException
public java.lang.String validateFollowOnFormAlternate(java.lang.String followOnFormAlternate) throws ValidationException, FrameworkException
public java.lang.String validateFollowOnFormName(java.lang.String followOnFormName) throws ValidationException, FrameworkException
public java.lang.String validateFormAlternate(java.lang.String formAlternate) throws ValidationException, FrameworkException
public java.lang.Long validateFormId(java.lang.Long formId) throws ValidationException, FrameworkException
public java.lang.String validateFormName(java.lang.String formName) throws ValidationException, FrameworkException
public java.lang.String validateFormTemplate(java.lang.String formTemplate) throws ValidationException, FrameworkException
public java.lang.String validateFormVariation(java.lang.String formVariation) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateOutputType(java.lang.String outputType) throws ValidationException, FrameworkException
public java.lang.String validateRemarks(java.lang.String remarks) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.FormDefinitionMeta**

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
**org.jaffa.modules.printing.domain.FormEvent**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String eventName) throws FrameworkException
public static FormEvent findByPK(UOW uow, java.lang.String eventName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String eventName)
public Criteria findFormUsageCriteria()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getEventName()
public FormUsage[] getFormUsageArray() throws FrameworkException
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public FormUsage newFormUsageObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEventName(java.lang.String eventName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEventName(java.lang.String eventName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateEventName(java.lang.String eventName) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.FormEventMeta**

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
**org.jaffa.modules.printing.domain.FormGroup**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String formName) throws FrameworkException
public static FormGroup findByPK(UOW uow, java.lang.String formName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String formName)
public Criteria findFormDefinitionCriteria()
public Criteria findFormUsageCriteria()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public FormDefinition[] getFormDefinitionArray() throws FrameworkException
public java.lang.String getFormName()
public java.lang.String getFormType()
public FormUsage[] getFormUsageArray() throws FrameworkException
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public FormDefinition newFormDefinitionObject() throws ValidationException, FrameworkException
public FormUsage newFormUsageObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormName(java.lang.String formName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormType(java.lang.String formType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormName(java.lang.String formName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormType(java.lang.String formType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateFormName(java.lang.String formName) throws ValidationException, FrameworkException
public java.lang.String validateFormType(java.lang.String formType) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.FormGroupMeta**

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
**org.jaffa.modules.printing.domain.FormTemplate**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.Long formId) throws FrameworkException
public static FormTemplate findByPK(UOW uow, java.lang.Long formId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.Long formId)
public FormDefinition getFormDefinitionObject() throws ValidationException, FrameworkException
public java.lang.Long getFormId()
public byte[] getLayoutData()
public byte[] getTemplateData()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void setFormId(java.lang.Long formId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLayoutData(byte[] layoutData) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setTemplateData(byte[] templateData) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateFormId(java.lang.Long formId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLayoutData(byte[] layoutData) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateTemplateData(byte[] templateData) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.Long validateFormId(java.lang.Long formId) throws ValidationException, FrameworkException
public byte[] validateLayoutData(byte[] layoutData) throws ValidationException, FrameworkException
public byte[] validateTemplateData(byte[] templateData) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.FormTemplateMeta**

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
**org.jaffa.modules.printing.domain.FormUsage**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String formName, java.lang.String eventName) throws FrameworkException
public static FormUsage findByPK(UOW uow, java.lang.String formName, java.lang.String eventName) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String formName, java.lang.String eventName)
public CompositeKey getCompositeKey()
public java.lang.Long getCopies()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getEventName()
public java.lang.String getFormAlternate()
public FormEvent getFormEventObject() throws ValidationException, FrameworkException
public FormGroup getFormGroupObject() throws ValidationException, FrameworkException
public java.lang.String getFormName()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCompositeKey(CompositeKey compositeKey)
public void setCopies(java.lang.Long copies) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setEventName(java.lang.String eventName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormAlternate(java.lang.String formAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setFormName(java.lang.String formName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCopies(java.lang.Long copies) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateEventName(java.lang.String eventName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormAlternate(java.lang.String formAlternate) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateFormName(java.lang.String formName) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.Long validateCopies(java.lang.Long copies) throws ValidationException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateEventName(java.lang.String eventName) throws ValidationException, FrameworkException
public java.lang.String validateFormAlternate(java.lang.String formAlternate) throws ValidationException, FrameworkException
public java.lang.String validateFormName(java.lang.String formName) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.FormUsageMeta**

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
**org.jaffa.modules.printing.domain.OutputCommand**

```
public void adjustSequenceNoAfterDelete(UOW uow) throws FrameworkException, ApplicationExceptions
public void adjustSequenceNoBeforeAdd(UOW uow) throws FrameworkException, ApplicationExceptions
public void adjustSequenceNoBeforeUpdate(UOW uow) throws FrameworkException, ApplicationExceptions
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.Long outputCommandId) throws FrameworkException
public static OutputCommand findByCK(UOW uow, String outputType, Long sequenceNo) throws FrameworkException
public static Criteria findByCKCriteria(String outputType, Long sequenceNo)
public static OutputCommand findByPK(UOW uow, java.lang.Long outputCommandId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.Long outputCommandId)
public java.lang.String getCommandLine()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getOsPattern()
public java.lang.Long getOutputCommandId()
public java.lang.String getOutputType()
public PrinterOutputType getPrinterOutputTypeObject() throws ValidationException, FrameworkException
public java.lang.Long getSequenceNo()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCommandLine(java.lang.String commandLine) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOsPattern(java.lang.String osPattern) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOutputCommandId(java.lang.Long outputCommandId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSequenceNo(java.lang.Long sequenceNo) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCommandLine(java.lang.String commandLine) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOsPattern(java.lang.String osPattern) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOutputCommandId(java.lang.Long outputCommandId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSequenceNo(java.lang.Long sequenceNo) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void validate() throws ApplicationExceptions, FrameworkException
public java.lang.String validateCommandLine(java.lang.String commandLine) throws ValidationException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateOsPattern(java.lang.String osPattern) throws ValidationException, FrameworkException
public java.lang.Long validateOutputCommandId(java.lang.Long outputCommandId) throws ValidationException, FrameworkException
public java.lang.String validateOutputType(java.lang.String outputType) throws ValidationException, FrameworkException
public java.lang.Long validateSequenceNo(java.lang.Long sequenceNo) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.OutputCommandMeta**

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
**org.jaffa.modules.printing.domain.PrinterDefinition**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String printerId) throws FrameworkException
public static PrinterDefinition findByPK(UOW uow, java.lang.String printerId) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String printerId)
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public java.lang.String getLocationCode()
public java.lang.String getOutputType()
public java.lang.String getPrinterId()
public java.lang.String getPrinterOption1()
public java.lang.String getPrinterOption2()
public PrinterOutputType getPrinterOutputTypeObject() throws ValidationException, FrameworkException
public java.lang.String getRealPrinterName()
public java.lang.Boolean getRemote()
public java.lang.String getScaleToPageSize()
public java.lang.String getSiteCode()
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLocationCode(java.lang.String locationCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPrinterId(java.lang.String printerId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPrinterOption1(java.lang.String printerOption1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setPrinterOption2(java.lang.String printerOption2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRealPrinterName(java.lang.String realPrinterName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setRemote(java.lang.Boolean remote) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setScaleToPageSize(java.lang.String scaleToPageSize) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setSiteCode(java.lang.String siteCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLocationCode(java.lang.String locationCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOutputType(java.lang.String outputType) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePrinterId(java.lang.String printerId) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePrinterOption1(java.lang.String printerOption1) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updatePrinterOption2(java.lang.String printerOption2) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRealPrinterName(java.lang.String realPrinterName) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateRemote(java.lang.Boolean remote) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateScaleToPageSize(java.lang.String scaleToPageSize) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateSiteCode(java.lang.String siteCode) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateLocationCode(java.lang.String locationCode) throws ValidationException, FrameworkException
public java.lang.String validateOutputType(java.lang.String outputType) throws ValidationException, FrameworkException
public java.lang.String validatePrinterId(java.lang.String printerId) throws ValidationException, FrameworkException
public java.lang.String validatePrinterOption1(java.lang.String printerOption1) throws ValidationException, FrameworkException
public java.lang.String validatePrinterOption2(java.lang.String printerOption2) throws ValidationException, FrameworkException
public java.lang.String validateRealPrinterName(java.lang.String realPrinterName) throws ValidationException, FrameworkException
public java.lang.Boolean validateRemote(java.lang.Boolean remote) throws ValidationException, FrameworkException
public java.lang.String validateScaleToPageSize(java.lang.String scaleToPageSize) throws ValidationException, FrameworkException
public java.lang.String validateSiteCode(java.lang.String siteCode) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.PrinterDefinitionMeta**

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
**org.jaffa.modules.printing.domain.PrinterOutputType**

```
public Object clone() throws CloneNotSupportedException
public static boolean exists(UOW uow, java.lang.String outputType) throws FrameworkException
public static PrinterOutputType findByPK(UOW uow, java.lang.String outputType) throws FrameworkException
public static Criteria findByPKCriteria(java.lang.String outputType)
public Criteria findOutputCommandCriteria()
public Criteria findPrinterDefinitionCriteria()
public java.lang.String getCreatedBy()
public org.jaffa.datatypes.DateTime getCreatedOn()
public java.lang.String getDescription()
public java.lang.Boolean getDirectPrinting()
public java.lang.String getLastChangedBy()
public org.jaffa.datatypes.DateTime getLastChangedOn()
public OutputCommand[] getOutputCommandArray() throws FrameworkException
public java.lang.String getOutputType()
public PrinterDefinition[] getPrinterDefinitionArray() throws FrameworkException
public OutputCommand newOutputCommandObject() throws ValidationException, FrameworkException
public PrinterDefinition newPrinterDefinitionObject() throws ValidationException, FrameworkException
public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException
public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException
public void preAdd() throws PreAddFailedException
public void preUpdate() throws PreUpdateFailedException
public void setCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setDirectPrinting(java.lang.Boolean directPrinting) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void setOutputType(java.lang.String outputType) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public String toString()
public void updateCreatedBy(java.lang.String createdBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDescription(java.lang.String description) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateDirectPrinting(java.lang.Boolean directPrinting) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public void updateOutputType(java.lang.String outputType) throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException
public java.lang.String validateCreatedBy(java.lang.String createdBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn) throws ValidationException, FrameworkException
public java.lang.String validateDescription(java.lang.String description) throws ValidationException, FrameworkException
public java.lang.Boolean validateDirectPrinting(java.lang.Boolean directPrinting) throws ValidationException, FrameworkException
public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy) throws ValidationException, FrameworkException
public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) throws ValidationException, FrameworkException
public java.lang.String validateOutputType(java.lang.String outputType) throws ValidationException, FrameworkException
```
***
**org.jaffa.modules.printing.domain.PrinterOutputTypeMeta**

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
**org.jaffa.modules.printing.services.AbstractDataBeanFactory**

```
public static IDataBeanFactory newInstance(String customFactory) throws DataBeanClassNotFoundException, EngineInstantiationException
```
***
**org.jaffa.modules.printing.services.ColorHelper**

```
public static Color getColor(String name)
public static int getRgbValue(String name)
```
***
**org.jaffa.modules.printing.services.ConvertPDFLibBlocks**

```
public static void convert(String templateFilename, String output)
public static void main(String[] args)
```
***
**org.jaffa.modules.printing.services.ConvertPDFLibBlocksTest**

```
public static Test suite()
public void testFieldExtraction() throws Exception
```
***
**org.jaffa.modules.printing.services.CustomDataBeanFactory**

```
public IDataBean getInstance() throws FrameworkException, ApplicationExceptions
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
public void setBeanClass(Class beanClass)
public void setFormName(String formName)
public void setKey(String key, String value)
```
***
**org.jaffa.modules.printing.services.DocumentPrintedListener**

```
public void documentPrinted(EventObject event)
```
***
**org.jaffa.modules.printing.services.DocumentPrintedListenerTest**

```
public static Test suite()
public void testMultiDocumentEmailTest() throws Exception
public void testSinglePageBadEmailTest() throws Exception
public void testSinglePageEmailTest() throws Exception
public void testSinglePageTest() throws Exception
```
***
**org.jaffa.modules.printing.services.DomValue**

```
```
***
**org.jaffa.modules.printing.services.DomainDataBean**

```
public DocumentPrintedListener getDocumentPrintedListener()
public Object getDocumentRoot()
public String getFormAlternateName()
public void populate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.services.DomainDataBeanFactory**

```
public IDataBean getInstance() throws FrameworkException, ApplicationExceptions
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
public void setBeanClass(Class beanClass)
public void setFormName(String formName)
public void setKey(String key, String value)
```
***
**org.jaffa.modules.printing.services.DomainDataBeanTest**

```
public static Test suite()
public void testOutputDomainForm() throws Exception
```
***
**org.jaffa.modules.printing.services.FieldProperties**

```
```
***
**org.jaffa.modules.printing.services.FileSchemaOutputResolver**

```
public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException
```
***
**org.jaffa.modules.printing.services.FormCache**

```
public void addForm(FormDefinition form, IDataBean data)
public String getTemplate(FormDefinition form, String engineType) throws FrameworkException
public String getTemplatePath()
public IDataBean lookupForm(FormDefinition form)
```
***
**org.jaffa.modules.printing.services.FormData**

```
public String getCurrentDateTime()
public String[] getLines()
public String[] getLines2()
public String[] getLines3()
public String[] renderTemplate() throws ApplicationExceptions
public void setLines() throws ApplicationExceptions
public void setLines(String formName) throws ApplicationExceptions
public void setLines2() throws ApplicationExceptions
public void setLines2(String formName) throws ApplicationExceptions
public void setLines3() throws ApplicationExceptions
public void setLines3(String formName) throws ApplicationExceptions
```
***
**org.jaffa.modules.printing.services.FormDataWrapper**

```
```
***
**org.jaffa.modules.printing.services.FormDelivery**

```
public static void deliver(PrintRequest request, File document, Object context) throws FrameworkException, ApplicationExceptions
public static void sendEmail(PrintRequest request, File document, Object dom) throws FrameworkException, ApplicationExceptions
public static void sendToPrinter(String printerId, int copies, File document) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.services.FormFormatHelper**

```
public void addNum(String key, Double num)
public String block(String str, int length)
public String block(int iStr, int length)
public String blockr(String str, int length)
public String getNum(String key)
public String getNum(String key, String format)
public int length(String[] array)
public int length(List list)
public String newLine()
public String newLine(int quantity)
public String pad(int length)
public void setNum(String key, int num)
public void setNum(String key, Double num)
public String[] split(String str1, int lineLength)
public String substr(String str, int begIdx, int length)
```
***
**org.jaffa.modules.printing.services.FormPrintEngine**

```
public void generate() throws FormPrintException
public int getCurrentPage()
public PageDetails getCurrentPageData()
public int getCurrentPageOffset()
public int getCurrentTemplatePage()
public Object getDataSource()
public String getTemplateName()
public int getTemplatePages()
public String getTemplateSearchPath()
public int getTotalPages()
public int getTotalPagesOverride()
public void initialize() throws FormPrintException
public boolean isProcessed()
public boolean isTemplateMode()
public void setCurrentPageOffset(int currentPageOffset)
public void setDataSource(Object dataSource)
public void setDocumentProperties(Properties documentProperties)
public void setTemplateName(String templateName)
public void setTemplateSearchPath(String templateSearchPath)
public void setTotalPagesOverride(int totalPagesOverride)
public static String translateFormPath(String ref, Map arrayOffset)
```
***
**org.jaffa.modules.printing.services.FormPrintEngineIText**

```
public byte[] getGeneratedForm() throws FormPrintException
public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException
public File writeForm() throws FormPrintException
public File writeForm(File fileout) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.FormPrintEnginePDFlibTest**

```
public static Test suite()
public void testCreateEngine() throws Exception
public void testGenerateForm() throws Exception
```
***
**org.jaffa.modules.printing.services.FormPrintEnginePdfLib**

```
public byte[] getGeneratedForm() throws FormPrintException
public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException
public File writeForm() throws FormPrintException
public File writeForm(File fileout) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.FormPrintEngineVelocity**

```
public void generate() throws FormPrintException
public int getCurrentPageOffset()
public Object getDataSource()
public byte[] getGeneratedForm() throws FormPrintException
public String getTemplateName()
public String getTemplateSearchPath()
public int getTotalPages()
public int getTotalPagesOverride()
public void initialize() throws FormPrintException
public boolean isProcessed()
public void setCurrentPageOffset(int currentPageOffset)
public void setDataSource(Object dataSource)
public void setDocumentProperties(Properties documentProperties)
public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException
public void setTemplateName(String templateName)
public void setTemplateSearchPath(String templateSearchPath)
public void setTotalPagesOverride(int totalPagesOverride)
public File writeForm() throws FormPrintException
public File writeForm(File fileout) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.FormPrintEngineiTextImageTest**

```
public static Test suite()
public void testGenerateForm() throws Exception
```
***
**org.jaffa.modules.printing.services.FormPrintEngineiTextTest**

```
public static Test suite()
public void testCreateEngine() throws Exception
public void testGenerateForm() throws Exception
```
***
**org.jaffa.modules.printing.services.FormPrintFactory**

```
public static IFormPrintFactory getExternalFactory()
public static IFormPrintEngine newInstance() throws EngineInstantiationException
public static IFormPrintEngine newInstance(String engineType) throws EngineInstantiationException
public static void setExternalFactory(IFormPrintFactory externalFactory)
```
***
**org.jaffa.modules.printing.services.FormPrintFactoryTest**

```
public void testExternalFactory() throws EngineInstantiationException
```
***
**org.jaffa.modules.printing.services.FormPrintRequest**

```
public java.lang.Object getAdditionalDataObject()
public String getFormAlternateName()
public String getFormName()
public LinkedHashMap getKeys()
public String getVariation()
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
public void setFormAlternateName(String formAlternateName)
public void setFormName(String formName)
public void setKeys(LinkedHashMap keys)
public void setVariation(String variation)
```
***
**org.jaffa.modules.printing.services.FormPrintingHelper**

```
public static void dispatchPrintRequest(FormPrintRequest formPrintRequest) throws FrameworkException, ApplicationExceptions
public static void dispatchPrintRequest(FormPrintRequest formPrintRequest, Boolean useJMS) throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.services.FormProcessor**

```
public static FormDefinition findFormDefinition(UOW uow, String formName, String alternateName, String variation, String outputType, String[] keys) throws FrameworkException
public static FormDefinition getFormDefinition(UOW uow, String formName, String alternateName, String variation, String outputType, String keySet) throws FrameworkException
public static IFormLocalization getFormLocalization()
public static LocaleProvider getLocaleProvider()
public static void process(FormPrintRequest request) throws FrameworkException, ApplicationExceptions
public static void setFormLocalization(IFormLocalization formLocalization)
public static void setLocaleProvider(LocaleProvider localeProvider)
```
***
**org.jaffa.modules.printing.services.FormProcessorLabelTest**

```
public static Test suite()
public void testEmailLabel() throws Exception
public void testOutputLabel() throws Exception
```
***
**org.jaffa.modules.printing.services.FormProcessorPdfTest**

```
public static Test suite()
public void testBadEmailForm() throws Exception
public void testEmailPdfForm() throws Exception
public void testInvalidPrinter() throws Exception
public void testNoFormData() throws Exception
public void testOutputPdfForm() throws Exception
public void testPrintPdfOneCopy() throws Exception
```
***
**org.jaffa.modules.printing.services.IDataBean**

```
public DocumentPrintedListener getDocumentPrintedListener()
public Object getDocumentRoot()
public String getFormAlternateName()
public void populate() throws FrameworkException, ApplicationExceptions
```
***
**org.jaffa.modules.printing.services.IDataBeanFactory**

```
public IDataBean getInstance() throws FrameworkException, ApplicationExceptions
public void setAdditionalDataObject(java.lang.Object additionalDataObject)
public void setBeanClass(Class beanClass)
public void setFormName(String formName)
public void setKey(String key, String value)
```
***
**org.jaffa.modules.printing.services.IFormLocalization**

```
```
***
**org.jaffa.modules.printing.services.IFormPrintEngine**

```
public void generate() throws FormPrintException
public int getCurrentPageOffset()
public Object getDataSource()
public byte[] getGeneratedForm() throws FormPrintException
public String getTemplateName()
public String getTemplateSearchPath()
public int getTotalPages()
public int getTotalPagesOverride()
public void initialize() throws FormPrintException
public void setCurrentPageOffset(int currentPageOffset)
public void setDataSource(Object dataSource)
public void setDocumentProperties(Properties documentProperties)
public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException
public void setTemplateName(String templateName)
public void setTemplateSearchPath(String templateSearchPath)
public void setTotalPagesOverride(int totalPagesOverride)
public File writeForm() throws FormPrintException
public File writeForm(File fileout) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.IFormPrintFactory**

```
```
***
**org.jaffa.modules.printing.services.ImageDom**

```
public Image getAwtImage()
public File getFileImage()
public String getStringClasspath()
public String getStringLocalFile()
public String getStringUrl()
public URL getUrlImage()
```
***
**org.jaffa.modules.printing.services.InvoiceDataBean**

```
public DocumentPrintedListener getDocumentPrintedListener()
public Object getDocumentRoot()
public String getFormAlternateName()
public List getItem()
public DateOnly getNeedDate()
public DateOnly getOrderDate()
public String getOrderNo()
public double getOrderTotal()
public String getRemarks()
public String getShippingAddress()
public String getVendorAddress()
public String getVendorCode()
public String getVendorName()
public void populate() throws FrameworkException, ApplicationExceptions
public void setItem(List item)
public void setNeedDate(DateOnly needDate)
public void setOrderDate(DateOnly orderDate)
public void setOrderNo(String orderNo)
public void setOrderTotal(double orderTotal)
public void setRemarks(String remarks)
public void setShippingAddress(String shippingAddress)
public void setVendorAddress(String vendorAddress)
public void setVendorCode(String vendorCode)
public void setVendorName(String vendorName)
```
***
**org.jaffa.modules.printing.services.InvoiceLineItem**

```
public String getDescription()
public Double getExtendedPrice()
public int getItemNo()
public Long getQuantity()
public Double getUnitPrice()
public void setDescription(String description)
public void setExtendedPrice(Double extendedPrice)
public void setItemNo(int itemNo)
public void setQuantity(Long quantity)
public void setUnitPrice(Double unitPrice)
public String toString()
```
***
**org.jaffa.modules.printing.services.MultiFormPrintEngine**

```
public void addDocument(String templateName, Object objectModel)
public void generate() throws FormPrintException
public String getEngineType()
public byte[] getGeneratedForm() throws FormPrintException
public List getObjectModels()
public String getPageSize()
public List<String> getTemplateNames()
public String getTemplateSearchPath()
public int getTotalPages()
public static void main(String[] args)
public static byte[] mergePdf(List<byte[]> documents) throws IOException, DocumentException
public static byte[] mergeText(List<byte[]> documents)
public void setDocumentProperties(Properties documentProperties)
public void setEngineType(String engineType)
public void setPageSize(String pageSize)
public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException
public void setTemplateSearchPath(String templateSearchPath)
public File writeForm() throws FormPrintException
public File writeForm(File fileout) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.MultiFormPrintEngineTest**

```
public static Test suite()
public void testPDFlibMultiForm() throws Exception
public void testVelocityMultiForm() throws Exception
public void testiTextMultiForm() throws Exception
```
***
**org.jaffa.modules.printing.services.PageDetails**

```
```
***
**org.jaffa.modules.printing.services.PageDetailsExtended**

```
```
***
**org.jaffa.modules.printing.services.PdfHelper**

```
public static byte[] scalePdfPages(byte[] pdfOutput, String pageSize, boolean noEnlarge, boolean preserveAspectRatio) throws FormPrintException
```
***
**org.jaffa.modules.printing.services.PrintRequest**

```
public Properties getDocumentProperties()
public String getEmailFromAddress()
public String getEmailMessage()
public String getEmailSubject()
public String getEmailToAddresses()
public Integer getPrintCopies()
public String getPrinterId()
public String getSaveFileName()
public String getUserName()
public void setDocumentProperties(Properties documentProperties)
public void setEmailFromAddress(String emailFromAddress)
public void setEmailMessage(String emailMessage)
public void setEmailSubject(String emailSubject)
public void setEmailToAddresses(String emailToAddresses)
public void setPrintCopies(Integer printCopies)
public void setPrinterId(String printerId)
public void setSaveFileName(String saveFileName)
public void setUserName(String userName)
```
***
**org.jaffa.modules.printing.services.PrintXmlUtility**

```
public void writeXml(Object object, String filename) throws JAXBException
public void writeXsd(String filename) throws IOException
```
***
**org.jaffa.modules.printing.services.VelocityTemplateHelper**

```
public Object getDataSource()
public String getTemplate()
public String[] renderTemplateLines() throws FormPrintException
public void setDataSource(Object dataSource)
public void setTemplate(String template)
```
***
**org.jaffa.modules.printing.services.XmlTransformerUtil**

```
public static void transform(byte[] source, InputStream xslInputStream, File output) throws IOException, TransformerException
```
***
**org.jaffa.modules.printing.services.exceptions.DataBeanClassNotFoundException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.DataNotFoundException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.DataNotReadyException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.EmailFailedException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.EngineInstantiationException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.EngineProcessingException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.FormPrintException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.FormTemplateUrlNotSupportedException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.PdfProcessingException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.PrintFailedException**

```
```
***
**org.jaffa.modules.printing.services.exceptions.PrinterNotFoundException**

```
```
***
**org.jaffa.unittest.AbstractDataWrapper**

```
```
***
**org.jaffa.unittest.ContextWrapper**

```
```
***
**org.jaffa.unittest.InitLog4J**

```
public static void init()
```
***
**org.jaffa.unittest.TestPrinterDiscovery**

```
public static void lookup()
public static void lookup(PrintServiceLookup lookup)
public static void main(String[] args)
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
