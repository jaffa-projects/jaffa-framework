// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.formdefinitionmaintenance.ui;

import java.util.EventObject;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.util.BeanHelper;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.maint.*;
import org.jaffa.components.finder.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

import org.jaffa.modules.printing.components.formdefinitionmaintenance.IFormDefinitionMaintenance;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports

import org.apache.struts.upload.FormFile;
import java.io.IOException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormDefinitionMaintenance.
 */
public class FormDefinitionMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(FormDefinitionMaintenanceComponent.class);
    private IFormDefinitionMaintenance m_tx = null;

    private java.lang.Long m_formId = null;
    private java.lang.String m_formName = null;
    private java.lang.String m_formAlternate = null;
    private java.lang.String m_formVariation = null;
    private java.lang.String m_outputType = null;
    private java.lang.String m_formTemplate = null;
    private java.lang.String m_fieldLayout = null;
    private java.lang.String m_description = null;
    private java.lang.String m_remarks = null;
    private java.lang.String m_domFactory = null;
    private java.lang.String m_domClass = null;
    private java.lang.String m_domKey1 = null;
    private java.lang.String m_domKey2 = null;
    private java.lang.String m_domKey3 = null;
    private java.lang.String m_domKey4 = null;
    private java.lang.String m_domKey5 = null;
    private java.lang.String m_domKey6 = null;
    private java.lang.String m_additionalDataComponent = null;
    private java.lang.String m_followOnFormName = null;
    private java.lang.String m_followOnFormAlternate = null;
    private org.jaffa.datatypes.DateTime m_createdOn = null;
    private java.lang.String m_createdBy = null;
    private org.jaffa.datatypes.DateTime m_lastChangedOn = null;
    private java.lang.String m_lastChangedBy = null;


    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method//GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        return m_formId;
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(java.lang.Long formId) {
        m_formId = formId;
    }
    // .//GEN-END:formId_1_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return m_formName;
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        m_formName = formName;
    }
    // .//GEN-END:formName_1_be
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return m_formAlternate;
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(java.lang.String formAlternate) {
        m_formAlternate = formAlternate;
    }
    // .//GEN-END:formAlternate_1_be
    // .//GEN-BEGIN:formVariation_1_be
    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public java.lang.String getFormVariation() {
        return m_formVariation;
    }

    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(java.lang.String formVariation) {
        m_formVariation = formVariation;
    }
    // .//GEN-END:formVariation_1_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return m_outputType;
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        m_outputType = outputType;
    }
    // .//GEN-END:outputType_1_be
    // .//GEN-BEGIN:formTemplate_1_be
    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public java.lang.String getFormTemplate() {
        return m_formTemplate;
    }

    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(java.lang.String formTemplate) {
        m_formTemplate = formTemplate;
    }
    // .//GEN-END:formTemplate_1_be
    // .//GEN-BEGIN:fieldLayout_1_be
    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public java.lang.String getFieldLayout() {
        return m_fieldLayout;
    }

    /** Setter for property fieldLayout.
     * @param fieldLayout New value of property fieldLayout.
     */
    public void setFieldLayout(java.lang.String fieldLayout) {
        m_fieldLayout = fieldLayout;
    }
    // .//GEN-END:fieldLayout_1_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return m_description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        m_description = description;
    }
    // .//GEN-END:description_1_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return m_remarks;
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        m_remarks = remarks;
    }
    // .//GEN-END:remarks_1_be
    // .//GEN-BEGIN:domFactory_1_be
    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public java.lang.String getDomFactory() {
        return m_domFactory;
    }

    /** Setter for property domFactory.
     * @param domFactory New value of property domFactory.
     */
    public void setDomFactory(java.lang.String domFactory) {
        m_domFactory = domFactory;
    }
    // .//GEN-END:domFactory_1_be
    // .//GEN-BEGIN:domClass_1_be
    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public java.lang.String getDomClass() {
        return m_domClass;
    }

    /** Setter for property domClass.
     * @param domClass New value of property domClass.
     */
    public void setDomClass(java.lang.String domClass) {
        m_domClass = domClass;
    }
    // .//GEN-END:domClass_1_be
    // .//GEN-BEGIN:domKey1_1_be
    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public java.lang.String getDomKey1() {
        return m_domKey1;
    }

    /** Setter for property domKey1.
     * @param domKey1 New value of property domKey1.
     */
    public void setDomKey1(java.lang.String domKey1) {
        m_domKey1 = domKey1;
    }
    // .//GEN-END:domKey1_1_be
    // .//GEN-BEGIN:domKey2_1_be
    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public java.lang.String getDomKey2() {
        return m_domKey2;
    }

    /** Setter for property domKey2.
     * @param domKey2 New value of property domKey2.
     */
    public void setDomKey2(java.lang.String domKey2) {
        m_domKey2 = domKey2;
    }
    // .//GEN-END:domKey2_1_be
    // .//GEN-BEGIN:domKey3_1_be
    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public java.lang.String getDomKey3() {
        return m_domKey3;
    }

    /** Setter for property domKey3.
     * @param domKey3 New value of property domKey3.
     */
    public void setDomKey3(java.lang.String domKey3) {
        m_domKey3 = domKey3;
    }
    // .//GEN-END:domKey3_1_be
    // .//GEN-BEGIN:domKey4_1_be
    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public java.lang.String getDomKey4() {
        return m_domKey4;
    }

    /** Setter for property domKey4.
     * @param domKey4 New value of property domKey4.
     */
    public void setDomKey4(java.lang.String domKey4) {
        m_domKey4 = domKey4;
    }
    // .//GEN-END:domKey4_1_be
    // .//GEN-BEGIN:domKey5_1_be
    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public java.lang.String getDomKey5() {
        return m_domKey5;
    }

    /** Setter for property domKey5.
     * @param domKey5 New value of property domKey5.
     */
    public void setDomKey5(java.lang.String domKey5) {
        m_domKey5 = domKey5;
    }
    // .//GEN-END:domKey5_1_be
    // .//GEN-BEGIN:domKey6_1_be
    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public java.lang.String getDomKey6() {
        return m_domKey6;
    }

    /** Setter for property domKey6.
     * @param domKey6 New value of property domKey6.
     */
    public void setDomKey6(java.lang.String domKey6) {
        m_domKey6 = domKey6;
    }
    // .//GEN-END:domKey6_1_be
    // .//GEN-BEGIN:additionalDataComponent_1_be
    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public java.lang.String getAdditionalDataComponent() {
        return m_additionalDataComponent;
    }

    /** Setter for property additionalDataComponent.
     * @param additionalDataComponent New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponent(java.lang.String additionalDataComponent) {
        m_additionalDataComponent = additionalDataComponent;
    }
    // .//GEN-END:additionalDataComponent_1_be
    // .//GEN-BEGIN:followOnFormName_1_be
    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public java.lang.String getFollowOnFormName() {
        return m_followOnFormName;
    }

    /** Setter for property followOnFormName.
     * @param followOnFormName New value of property followOnFormName.
     */
    public void setFollowOnFormName(java.lang.String followOnFormName) {
        m_followOnFormName = followOnFormName;
    }
    // .//GEN-END:followOnFormName_1_be
    // .//GEN-BEGIN:followOnFormAlternate_1_be
    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public java.lang.String getFollowOnFormAlternate() {
        return m_followOnFormAlternate;
    }

    /** Setter for property followOnFormAlternate.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate) {
        m_followOnFormAlternate = followOnFormAlternate;
    }
    // .//GEN-END:followOnFormAlternate_1_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        m_createdOn = createdOn;
    }
    // .//GEN-END:createdOn_1_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return m_createdBy;
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        m_createdBy = createdBy;
    }
    // .//GEN-END:createdBy_1_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return m_lastChangedOn;
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        m_lastChangedOn = lastChangedOn;
    }
    // .//GEN-END:lastChangedOn_1_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return m_lastChangedBy;
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        m_lastChangedBy = lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceCreateInDto input = new FormDefinitionMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code//GEN-FIRST:_doCreate_1

        //Read File
        fillFileData();
        input.setTemplateData(getTemplateData());
        input.setLayoutData(getLayoutData());

        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormId(getFormId());
        input.setFormName(getFormName());
        input.setFormAlternate(getFormAlternate());
        input.setFormVariation(getFormVariation());
        input.setOutputType(getOutputType());
        input.setFormTemplate(getFormTemplate());
        input.setFieldLayout(getFieldLayout());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setDomFactory(getDomFactory());
        input.setDomClass(getDomClass());
        input.setDomKey1(getDomKey1());
        input.setDomKey2(getDomKey2());
        input.setDomKey3(getDomKey3());
        input.setDomKey4(getDomKey4());
        input.setDomKey5(getDomKey5());
        input.setDomKey6(getDomKey6());
        input.setAdditionalDataComponent(getAdditionalDataComponent());
        input.setFollowOnFormName(getFollowOnFormName());
        input.setFollowOnFormAlternate(getFollowOnFormAlternate());
        FormDefinitionMaintenanceRetrieveOutDto output = createTx().create(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doCreate_2_be
        // Add custom code//GEN-FIRST:_doCreate_2


        // .//GEN-LAST:_doCreate_2
        // .//GEN-BEGIN:_doCreate_3_be
    }
    // .//GEN-END:_doCreate_3_be
    // .//GEN-BEGIN:_doUpdate_1_be
    /** This will invoke the update method on the transaction to update an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceUpdateInDto input = new FormDefinitionMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code//GEN-FIRST:_doUpdate_1

        fillFileData();
        input.setTemplateData(getTemplateData());
        input.setLayoutData(getLayoutData());

        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormId(getFormId());
        input.setFormName(getFormName());
        input.setFormAlternate(getFormAlternate());
        input.setFormVariation(getFormVariation());
        input.setOutputType(getOutputType());
        input.setFormTemplate(getFormTemplate());
        input.setFieldLayout(getFieldLayout());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setDomFactory(getDomFactory());
        input.setDomClass(getDomClass());
        input.setDomKey1(getDomKey1());
        input.setDomKey2(getDomKey2());
        input.setDomKey3(getDomKey3());
        input.setDomKey4(getDomKey4());
        input.setDomKey5(getDomKey5());
        input.setDomKey6(getDomKey6());
        input.setAdditionalDataComponent(getAdditionalDataComponent());
        input.setFollowOnFormName(getFollowOnFormName());
        input.setFollowOnFormAlternate(getFollowOnFormAlternate());
        input.setLastChangedOn(getLastChangedOn());
        FormDefinitionMaintenanceRetrieveOutDto output = createTx().update(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doUpdate_2_be
        // Add custom code//GEN-FIRST:_doUpdate_2


        // .//GEN-LAST:_doUpdate_2
        // .//GEN-BEGIN:_doUpdate_3_be
    }
    // .//GEN-END:_doUpdate_3_be
    // .//GEN-BEGIN:_doDelete_1_be
    /** This will invoke the delete method on the transaction to delete an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doDelete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceDeleteInDto input = new FormDefinitionMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code//GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormId(getFormId());
        input.setLastChangedOn(getLastChangedOn());
        createTx().delete(input);
        // .//GEN-END:_doDelete_2_be
        // Add custom code//GEN-FIRST:_doDelete_2


        // .//GEN-LAST:_doDelete_2
        // .//GEN-BEGIN:_doDelete_3_be
    }
    // .//GEN-END:_doDelete_3_be
    // .//GEN-BEGIN:_doRetrieve_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doRetrieve() throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_formDefinitionMaintenance_main", true, true, true, true));
        // .//GEN-END:_addScreens_1_be
        // Add custom code//GEN-FIRST:_addScreens_1


        // .//GEN-LAST:_addScreens_1
        // .//GEN-BEGIN:_addScreens_2_be
    }
    // .//GEN-END:_addScreens_2_be
    // .//GEN-BEGIN:_doPrevalidateCreate_1_be
    /** This performs prevalidations before creating a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateCreate() throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceCreateInDto input = new FormDefinitionMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_1
        //Read File
        fillFileData();
        input.setTemplateData(getTemplateData());
        input.setLayoutData(getLayoutData());

        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormId(getFormId());
        input.setFormName(getFormName());
        input.setFormAlternate(getFormAlternate());
        input.setFormVariation(getFormVariation());
        input.setOutputType(getOutputType());
        input.setFormTemplate(getFormTemplate());
        input.setFieldLayout(getFieldLayout());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setDomFactory(getDomFactory());
        input.setDomClass(getDomClass());
        input.setDomKey1(getDomKey1());
        input.setDomKey2(getDomKey2());
        input.setDomKey3(getDomKey3());
        input.setDomKey4(getDomKey4());
        input.setDomKey5(getDomKey5());
        input.setDomKey6(getDomKey6());
        input.setAdditionalDataComponent(getAdditionalDataComponent());
        input.setFollowOnFormName(getFollowOnFormName());
        input.setFollowOnFormAlternate(getFollowOnFormAlternate());
        FormDefinitionMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateCreate_2_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_2


        // .//GEN-LAST:_doPrevalidateCreate_2
        // .//GEN-BEGIN:_doPrevalidateCreate_3_be
    }
    // .//GEN-END:_doPrevalidateCreate_3_be
    // .//GEN-BEGIN:_doPrevalidateUpdate_1_be
    /** This performs prevalidations before updating a domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceUpdateInDto input = new FormDefinitionMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_1
        //Read File
        fillFileData();
        input.setTemplateData(getTemplateData());
        input.setLayoutData(getLayoutData());

        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormId(getFormId());
        input.setFormName(getFormName());
        input.setFormAlternate(getFormAlternate());
        input.setFormVariation(getFormVariation());
        input.setOutputType(getOutputType());
        input.setFormTemplate(getFormTemplate());
        input.setFieldLayout(getFieldLayout());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setDomFactory(getDomFactory());
        input.setDomClass(getDomClass());
        input.setDomKey1(getDomKey1());
        input.setDomKey2(getDomKey2());
        input.setDomKey3(getDomKey3());
        input.setDomKey4(getDomKey4());
        input.setDomKey5(getDomKey5());
        input.setDomKey6(getDomKey6());
        input.setAdditionalDataComponent(getAdditionalDataComponent());
        input.setFollowOnFormName(getFollowOnFormName());
        input.setFollowOnFormAlternate(getFollowOnFormAlternate());
        input.setLastChangedOn(getLastChangedOn());
        FormDefinitionMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateUpdate_2_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_2


        // .//GEN-LAST:_doPrevalidateUpdate_2
        // .//GEN-BEGIN:_doPrevalidateUpdate_3_be
    }
    // .//GEN-END:_doPrevalidateUpdate_3_be
    // .//GEN-BEGIN:_initDropDownCodes_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     */
    protected void initDropDownCodes() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initDropDownCodes_1_be
    // .//GEN-BEGIN:_createTx_1_be
    private IFormDefinitionMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IFormDefinitionMaintenance) Factory.createObject(IFormDefinitionMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the FormDefinitionMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private FormDefinitionMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        FormDefinitionMaintenanceRetrieveInDto input = new FormDefinitionMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormId(getFormId());
        FormDefinitionMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of FormDefinitionMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(FormDefinitionMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setFormId(retrieveOutDto.getFormId());
            setFormName(retrieveOutDto.getFormName());
            setFormAlternate(retrieveOutDto.getFormAlternate());
            setFormVariation(retrieveOutDto.getFormVariation());
            setOutputType(retrieveOutDto.getOutputType());
            setFormTemplate(retrieveOutDto.getFormTemplate());
            setFieldLayout(retrieveOutDto.getFieldLayout());
            setDescription(retrieveOutDto.getDescription());
            setRemarks(retrieveOutDto.getRemarks());
            setDomFactory(retrieveOutDto.getDomFactory());
            setDomClass(retrieveOutDto.getDomClass());
            setDomKey1(retrieveOutDto.getDomKey1());
            setDomKey2(retrieveOutDto.getDomKey2());
            setDomKey3(retrieveOutDto.getDomKey3());
            setDomKey4(retrieveOutDto.getDomKey4());
            setDomKey5(retrieveOutDto.getDomKey5());
            setDomKey6(retrieveOutDto.getDomKey6());
            setAdditionalDataComponent(retrieveOutDto.getAdditionalDataComponent());
            setFollowOnFormName(retrieveOutDto.getFollowOnFormName());
            setFollowOnFormAlternate(retrieveOutDto.getFollowOnFormAlternate());
            setCreatedOn(retrieveOutDto.getCreatedOn());
            setCreatedBy(retrieveOutDto.getCreatedBy());
            setLastChangedOn(retrieveOutDto.getLastChangedOn());
            setLastChangedBy(retrieveOutDto.getLastChangedBy());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of FormDefinitionMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(FormDefinitionMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setFormId(prevalidateOutDto.getFormId());
            setFormName(prevalidateOutDto.getFormName());
            setFormAlternate(prevalidateOutDto.getFormAlternate());
            setFormVariation(prevalidateOutDto.getFormVariation());
            setOutputType(prevalidateOutDto.getOutputType());
            setFormTemplate(prevalidateOutDto.getFormTemplate());
            setFieldLayout(prevalidateOutDto.getFieldLayout());
            setDescription(prevalidateOutDto.getDescription());
            setRemarks(prevalidateOutDto.getRemarks());
            setDomFactory(prevalidateOutDto.getDomFactory());
            setDomClass(prevalidateOutDto.getDomClass());
            setDomKey1(prevalidateOutDto.getDomKey1());
            setDomKey2(prevalidateOutDto.getDomKey2());
            setDomKey3(prevalidateOutDto.getDomKey3());
            setDomKey4(prevalidateOutDto.getDomKey4());
            setDomKey5(prevalidateOutDto.getDomKey5());
            setDomKey6(prevalidateOutDto.getDomKey6());
            setAdditionalDataComponent(prevalidateOutDto.getAdditionalDataComponent());
            setFollowOnFormName(prevalidateOutDto.getFollowOnFormName());
            setFollowOnFormAlternate(prevalidateOutDto.getFollowOnFormAlternate());
            setCreatedOn(prevalidateOutDto.getCreatedOn());
            setCreatedBy(prevalidateOutDto.getCreatedBy());
            setLastChangedOn(prevalidateOutDto.getLastChangedOn());
            setLastChangedBy(prevalidateOutDto.getLastChangedBy());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    private FormFile m_file = null;
    private FormFile m_fileLayout = null;
    private byte[] m_templateData;
    private byte[] m_layoutData;
    
    /** Getter for property file.
     * @return Value of property file.
     */
    public FormFile getFile() {
        return m_file;
    }
    
    /** Setter for property file.
     * @param file New value of property file.
     */
    public void setFile(FormFile file) {
        m_file = file;
    }
    
    /** Getter for property m_fileLayout.
     * @return Value of property m_fileLayout.
     */
    public FormFile getFileLayout() {
        return m_fileLayout;
    }
    
    /** Setter for property m_fileLayout.
     * @param fileLayout New value of property m_fileLayout.
     */
    public void setFileLayout(FormFile fileLayout) {
        m_fileLayout = fileLayout;
    }
    
    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public byte[] getTemplateData() {
        return m_templateData;
    }
    
    /** Setter for property templateData.
     * @param templateData New value of property templateData.
     */
    public void setTemplateData(byte[] templateData) {
        m_templateData = templateData;
    }
    
    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public byte[] getLayoutData() {
        return m_layoutData;
    }
    
    /** Setter for property layoutData.
     * @param layoutData New value of property layoutData.
     */
    public void setLayoutData(byte[] layoutData) {
        m_layoutData = layoutData;
    }
    
    private void fillFileData() throws ApplicationExceptions, FrameworkException {
        try{
            //Read File
            byte[] fileData = getFile().getFileData();
            //Read path in which fiel needs to be uploaded
            String fileName = getFile().getFileName();
            setFormTemplate(fileName);
            setTemplateData(fileData);
            // FieldLayout
            byte[] fileLayoutData = getFileLayout().getFileData();
            //Read path in which fiel needs to be uploaded
            String fileLayoutName = getFileLayout().getFileName();
            setFieldLayout(fileLayoutName);
            setLayoutData(fileLayoutData);
        }catch(IOException e){
            throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.FileNotFound") {});
        }
    }

    // .//GEN-LAST:_custom
}
