// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionlookup.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.modules.printing.components.formdefinitionlookup.IFormDefinitionLookup;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupInDto;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutDto;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerComponent;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormDefinitionLookup.
 */
public class FormDefinitionLookupComponent extends LookupComponent2 {

    private static Logger log = Logger.getLogger(FormDefinitionLookupComponent.class);

    private String m_formId = null;
    private String m_formIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_formName = null;
    private String m_formNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_formAlternate = null;
    private String m_formAlternateDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_formVariation = null;
    private String m_formVariationDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_outputType = null;
    private String m_outputTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_formTemplate = null;
    private String m_formTemplateDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_fieldLayout = null;
    private String m_fieldLayoutDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_description = null;
    private String m_descriptionDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_remarks = null;
    private String m_remarksDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domFactory = null;
    private String m_domFactoryDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domClass = null;
    private String m_domClassDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey1 = null;
    private String m_domKey1Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey2 = null;
    private String m_domKey2Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey3 = null;
    private String m_domKey3Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey4 = null;
    private String m_domKey4Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey5 = null;
    private String m_domKey5Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_domKey6 = null;
    private String m_domKey6Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_additionalDataComponent = null;
    private String m_additionalDataComponentDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_followOnFormName = null;
    private String m_followOnFormNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_followOnFormAlternate = null;
    private String m_followOnFormAlternateDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_createdOn = null;
    private String m_createdOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_createdBy = null;
    private String m_createdByDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastChangedOn = null;
    private String m_lastChangedOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastChangedBy = null;
    private String m_lastChangedByDd = CriteriaField.RELATIONAL_EQUALS;

    private IFormDefinitionLookup m_tx = null;
    private FormDefinitionMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private FormDefinitionMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private FormDefinitionMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public FormDefinitionLookupComponent() {
        super();
        super.setSortDropDown("FormId");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_formDefinitionLookupCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_formDefinitionLookupResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_formDefinitionLookupConsolidatedCriteriaAndResults";
    }
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method //GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        if (m_createComponent != null) {
            m_createComponent.quit();
            m_createComponent = null;
        }
        m_createListener = null;
        if (m_updateComponent != null) {
            m_updateComponent.quit();
            m_updateComponent = null;
        }
        m_updateListener = null;
        if (m_deleteComponent != null) {
            m_deleteComponent.quit();
            m_deleteComponent = null;
        }
        m_deleteListener = null;

        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public String getFormId() {
        return m_formId;
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(String formId) {
        m_formId = formId;
    }

    /** Getter for property formIdDd.
     * @return Value of property formIdDd.
     */
    public String getFormIdDd() {
        return m_formIdDd;
    }

    /** Setter for property formIdDd.
     * @param formIdDd New value of property formIdDd.
     */
    public void setFormIdDd(String formIdDd) {
        m_formIdDd = formIdDd;
    }

    // .//GEN-END:formId_1_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return m_formName;
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        m_formName = formName;
    }

    /** Getter for property formNameDd.
     * @return Value of property formNameDd.
     */
    public String getFormNameDd() {
        return m_formNameDd;
    }

    /** Setter for property formNameDd.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        m_formNameDd = formNameDd;
    }

    // .//GEN-END:formName_1_be
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public String getFormAlternate() {
        return m_formAlternate;
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(String formAlternate) {
        m_formAlternate = formAlternate;
    }

    /** Getter for property formAlternateDd.
     * @return Value of property formAlternateDd.
     */
    public String getFormAlternateDd() {
        return m_formAlternateDd;
    }

    /** Setter for property formAlternateDd.
     * @param formAlternateDd New value of property formAlternateDd.
     */
    public void setFormAlternateDd(String formAlternateDd) {
        m_formAlternateDd = formAlternateDd;
    }

    // .//GEN-END:formAlternate_1_be
    // .//GEN-BEGIN:formVariation_1_be
    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public String getFormVariation() {
        return m_formVariation;
    }

    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(String formVariation) {
        m_formVariation = formVariation;
    }

    /** Getter for property formVariationDd.
     * @return Value of property formVariationDd.
     */
    public String getFormVariationDd() {
        return m_formVariationDd;
    }

    /** Setter for property formVariationDd.
     * @param formVariationDd New value of property formVariationDd.
     */
    public void setFormVariationDd(String formVariationDd) {
        m_formVariationDd = formVariationDd;
    }

    // .//GEN-END:formVariation_1_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public String getOutputType() {
        return m_outputType;
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        m_outputType = outputType;
    }

    /** Getter for property outputTypeDd.
     * @return Value of property outputTypeDd.
     */
    public String getOutputTypeDd() {
        return m_outputTypeDd;
    }

    /** Setter for property outputTypeDd.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        m_outputTypeDd = outputTypeDd;
    }

    // .//GEN-END:outputType_1_be
    // .//GEN-BEGIN:formTemplate_1_be
    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public String getFormTemplate() {
        return m_formTemplate;
    }

    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(String formTemplate) {
        m_formTemplate = formTemplate;
    }

    /** Getter for property formTemplateDd.
     * @return Value of property formTemplateDd.
     */
    public String getFormTemplateDd() {
        return m_formTemplateDd;
    }

    /** Setter for property formTemplateDd.
     * @param formTemplateDd New value of property formTemplateDd.
     */
    public void setFormTemplateDd(String formTemplateDd) {
        m_formTemplateDd = formTemplateDd;
    }

    // .//GEN-END:formTemplate_1_be
    // .//GEN-BEGIN:fieldLayout_1_be
    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public String getFieldLayout() {
        return m_fieldLayout;
    }

    /** Setter for property fieldLayout.
     * @param fieldLayout New value of property fieldLayout.
     */
    public void setFieldLayout(String fieldLayout) {
        m_fieldLayout = fieldLayout;
    }

    /** Getter for property fieldLayoutDd.
     * @return Value of property fieldLayoutDd.
     */
    public String getFieldLayoutDd() {
        return m_fieldLayoutDd;
    }

    /** Setter for property fieldLayoutDd.
     * @param fieldLayoutDd New value of property fieldLayoutDd.
     */
    public void setFieldLayoutDd(String fieldLayoutDd) {
        m_fieldLayoutDd = fieldLayoutDd;
    }

    // .//GEN-END:fieldLayout_1_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return m_description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        m_description = description;
    }

    /** Getter for property descriptionDd.
     * @return Value of property descriptionDd.
     */
    public String getDescriptionDd() {
        return m_descriptionDd;
    }

    /** Setter for property descriptionDd.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        m_descriptionDd = descriptionDd;
    }

    // .//GEN-END:description_1_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return m_remarks;
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        m_remarks = remarks;
    }

    /** Getter for property remarksDd.
     * @return Value of property remarksDd.
     */
    public String getRemarksDd() {
        return m_remarksDd;
    }

    /** Setter for property remarksDd.
     * @param remarksDd New value of property remarksDd.
     */
    public void setRemarksDd(String remarksDd) {
        m_remarksDd = remarksDd;
    }

    // .//GEN-END:remarks_1_be
    // .//GEN-BEGIN:domFactory_1_be
    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public String getDomFactory() {
        return m_domFactory;
    }

    /** Setter for property domFactory.
     * @param domFactory New value of property domFactory.
     */
    public void setDomFactory(String domFactory) {
        m_domFactory = domFactory;
    }

    /** Getter for property domFactoryDd.
     * @return Value of property domFactoryDd.
     */
    public String getDomFactoryDd() {
        return m_domFactoryDd;
    }

    /** Setter for property domFactoryDd.
     * @param domFactoryDd New value of property domFactoryDd.
     */
    public void setDomFactoryDd(String domFactoryDd) {
        m_domFactoryDd = domFactoryDd;
    }

    // .//GEN-END:domFactory_1_be
    // .//GEN-BEGIN:domClass_1_be
    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public String getDomClass() {
        return m_domClass;
    }

    /** Setter for property domClass.
     * @param domClass New value of property domClass.
     */
    public void setDomClass(String domClass) {
        m_domClass = domClass;
    }

    /** Getter for property domClassDd.
     * @return Value of property domClassDd.
     */
    public String getDomClassDd() {
        return m_domClassDd;
    }

    /** Setter for property domClassDd.
     * @param domClassDd New value of property domClassDd.
     */
    public void setDomClassDd(String domClassDd) {
        m_domClassDd = domClassDd;
    }

    // .//GEN-END:domClass_1_be
    // .//GEN-BEGIN:domKey1_1_be
    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public String getDomKey1() {
        return m_domKey1;
    }

    /** Setter for property domKey1.
     * @param domKey1 New value of property domKey1.
     */
    public void setDomKey1(String domKey1) {
        m_domKey1 = domKey1;
    }

    /** Getter for property domKey1Dd.
     * @return Value of property domKey1Dd.
     */
    public String getDomKey1Dd() {
        return m_domKey1Dd;
    }

    /** Setter for property domKey1Dd.
     * @param domKey1Dd New value of property domKey1Dd.
     */
    public void setDomKey1Dd(String domKey1Dd) {
        m_domKey1Dd = domKey1Dd;
    }

    // .//GEN-END:domKey1_1_be
    // .//GEN-BEGIN:domKey2_1_be
    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public String getDomKey2() {
        return m_domKey2;
    }

    /** Setter for property domKey2.
     * @param domKey2 New value of property domKey2.
     */
    public void setDomKey2(String domKey2) {
        m_domKey2 = domKey2;
    }

    /** Getter for property domKey2Dd.
     * @return Value of property domKey2Dd.
     */
    public String getDomKey2Dd() {
        return m_domKey2Dd;
    }

    /** Setter for property domKey2Dd.
     * @param domKey2Dd New value of property domKey2Dd.
     */
    public void setDomKey2Dd(String domKey2Dd) {
        m_domKey2Dd = domKey2Dd;
    }

    // .//GEN-END:domKey2_1_be
    // .//GEN-BEGIN:domKey3_1_be
    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public String getDomKey3() {
        return m_domKey3;
    }

    /** Setter for property domKey3.
     * @param domKey3 New value of property domKey3.
     */
    public void setDomKey3(String domKey3) {
        m_domKey3 = domKey3;
    }

    /** Getter for property domKey3Dd.
     * @return Value of property domKey3Dd.
     */
    public String getDomKey3Dd() {
        return m_domKey3Dd;
    }

    /** Setter for property domKey3Dd.
     * @param domKey3Dd New value of property domKey3Dd.
     */
    public void setDomKey3Dd(String domKey3Dd) {
        m_domKey3Dd = domKey3Dd;
    }

    // .//GEN-END:domKey3_1_be
    // .//GEN-BEGIN:domKey4_1_be
    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public String getDomKey4() {
        return m_domKey4;
    }

    /** Setter for property domKey4.
     * @param domKey4 New value of property domKey4.
     */
    public void setDomKey4(String domKey4) {
        m_domKey4 = domKey4;
    }

    /** Getter for property domKey4Dd.
     * @return Value of property domKey4Dd.
     */
    public String getDomKey4Dd() {
        return m_domKey4Dd;
    }

    /** Setter for property domKey4Dd.
     * @param domKey4Dd New value of property domKey4Dd.
     */
    public void setDomKey4Dd(String domKey4Dd) {
        m_domKey4Dd = domKey4Dd;
    }

    // .//GEN-END:domKey4_1_be
    // .//GEN-BEGIN:domKey5_1_be
    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public String getDomKey5() {
        return m_domKey5;
    }

    /** Setter for property domKey5.
     * @param domKey5 New value of property domKey5.
     */
    public void setDomKey5(String domKey5) {
        m_domKey5 = domKey5;
    }

    /** Getter for property domKey5Dd.
     * @return Value of property domKey5Dd.
     */
    public String getDomKey5Dd() {
        return m_domKey5Dd;
    }

    /** Setter for property domKey5Dd.
     * @param domKey5Dd New value of property domKey5Dd.
     */
    public void setDomKey5Dd(String domKey5Dd) {
        m_domKey5Dd = domKey5Dd;
    }

    // .//GEN-END:domKey5_1_be
    // .//GEN-BEGIN:domKey6_1_be
    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public String getDomKey6() {
        return m_domKey6;
    }

    /** Setter for property domKey6.
     * @param domKey6 New value of property domKey6.
     */
    public void setDomKey6(String domKey6) {
        m_domKey6 = domKey6;
    }

    /** Getter for property domKey6Dd.
     * @return Value of property domKey6Dd.
     */
    public String getDomKey6Dd() {
        return m_domKey6Dd;
    }

    /** Setter for property domKey6Dd.
     * @param domKey6Dd New value of property domKey6Dd.
     */
    public void setDomKey6Dd(String domKey6Dd) {
        m_domKey6Dd = domKey6Dd;
    }

    // .//GEN-END:domKey6_1_be
    // .//GEN-BEGIN:additionalDataComponent_1_be
    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public String getAdditionalDataComponent() {
        return m_additionalDataComponent;
    }

    /** Setter for property additionalDataComponent.
     * @param additionalDataComponent New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponent(String additionalDataComponent) {
        m_additionalDataComponent = additionalDataComponent;
    }

    /** Getter for property additionalDataComponentDd.
     * @return Value of property additionalDataComponentDd.
     */
    public String getAdditionalDataComponentDd() {
        return m_additionalDataComponentDd;
    }

    /** Setter for property additionalDataComponentDd.
     * @param additionalDataComponentDd New value of property additionalDataComponentDd.
     */
    public void setAdditionalDataComponentDd(String additionalDataComponentDd) {
        m_additionalDataComponentDd = additionalDataComponentDd;
    }

    // .//GEN-END:additionalDataComponent_1_be
    // .//GEN-BEGIN:followOnFormName_1_be
    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public String getFollowOnFormName() {
        return m_followOnFormName;
    }

    /** Setter for property followOnFormName.
     * @param followOnFormName New value of property followOnFormName.
     */
    public void setFollowOnFormName(String followOnFormName) {
        m_followOnFormName = followOnFormName;
    }

    /** Getter for property followOnFormNameDd.
     * @return Value of property followOnFormNameDd.
     */
    public String getFollowOnFormNameDd() {
        return m_followOnFormNameDd;
    }

    /** Setter for property followOnFormNameDd.
     * @param followOnFormNameDd New value of property followOnFormNameDd.
     */
    public void setFollowOnFormNameDd(String followOnFormNameDd) {
        m_followOnFormNameDd = followOnFormNameDd;
    }

    // .//GEN-END:followOnFormName_1_be
    // .//GEN-BEGIN:followOnFormAlternate_1_be
    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public String getFollowOnFormAlternate() {
        return m_followOnFormAlternate;
    }

    /** Setter for property followOnFormAlternate.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternate(String followOnFormAlternate) {
        m_followOnFormAlternate = followOnFormAlternate;
    }

    /** Getter for property followOnFormAlternateDd.
     * @return Value of property followOnFormAlternateDd.
     */
    public String getFollowOnFormAlternateDd() {
        return m_followOnFormAlternateDd;
    }

    /** Setter for property followOnFormAlternateDd.
     * @param followOnFormAlternateDd New value of property followOnFormAlternateDd.
     */
    public void setFollowOnFormAlternateDd(String followOnFormAlternateDd) {
        m_followOnFormAlternateDd = followOnFormAlternateDd;
    }

    // .//GEN-END:followOnFormAlternate_1_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return m_createdOn;
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        m_createdOn = createdOn;
    }

    /** Getter for property createdOnDd.
     * @return Value of property createdOnDd.
     */
    public String getCreatedOnDd() {
        return m_createdOnDd;
    }

    /** Setter for property createdOnDd.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        m_createdOnDd = createdOnDd;
    }

    // .//GEN-END:createdOn_1_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return m_createdBy;
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        m_createdBy = createdBy;
    }

    /** Getter for property createdByDd.
     * @return Value of property createdByDd.
     */
    public String getCreatedByDd() {
        return m_createdByDd;
    }

    /** Setter for property createdByDd.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        m_createdByDd = createdByDd;
    }

    // .//GEN-END:createdBy_1_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public String getLastChangedOn() {
        return m_lastChangedOn;
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        m_lastChangedOn = lastChangedOn;
    }

    /** Getter for property lastChangedOnDd.
     * @return Value of property lastChangedOnDd.
     */
    public String getLastChangedOnDd() {
        return m_lastChangedOnDd;
    }

    /** Setter for property lastChangedOnDd.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        m_lastChangedOnDd = lastChangedOnDd;
    }

    // .//GEN-END:lastChangedOn_1_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return m_lastChangedBy;
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        m_lastChangedBy = lastChangedBy;
    }

    /** Getter for property lastChangedByDd.
     * @return Value of property lastChangedByDd.
     */
    public String getLastChangedByDd() {
        return m_lastChangedByDd;
    }

    /** Setter for property lastChangedByDd.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        m_lastChangedByDd = lastChangedByDd;
    }

    // .//GEN-END:lastChangedBy_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        FormDefinitionLookupInDto inputDto = new FormDefinitionLookupInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        try {
            if (getFormId() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getFormIdDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormIdDd() ) )
                inputDto.setFormId(IntegerCriteriaField.getIntegerCriteriaField(getFormIdDd(), getFormId(), (IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getFormName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormNameDd() ) )
            inputDto.setFormName(StringCriteriaField.getStringCriteriaField(getFormNameDd(), getFormName(), null));

        if (getFormAlternate() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormAlternateDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormAlternateDd() ) )
            inputDto.setFormAlternate(StringCriteriaField.getStringCriteriaField(getFormAlternateDd(), getFormAlternate(), null));

        if (getFormVariation() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormVariationDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormVariationDd() ) )
            inputDto.setFormVariation(StringCriteriaField.getStringCriteriaField(getFormVariationDd(), getFormVariation(), null));

        if (getOutputType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getOutputTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getOutputTypeDd() ) )
            inputDto.setOutputType(StringCriteriaField.getStringCriteriaField(getOutputTypeDd(), getOutputType(), null));

        if (getFormTemplate() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormTemplateDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormTemplateDd() ) )
            inputDto.setFormTemplate(StringCriteriaField.getStringCriteriaField(getFormTemplateDd(), getFormTemplate(), null));

        if (getFieldLayout() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFieldLayoutDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFieldLayoutDd() ) )
            inputDto.setFieldLayout(StringCriteriaField.getStringCriteriaField(getFieldLayoutDd(), getFieldLayout(), null));

        if (getDescription() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDescriptionDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDescriptionDd() ) )
            inputDto.setDescription(StringCriteriaField.getStringCriteriaField(getDescriptionDd(), getDescription(), null));

        if (getRemarks() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getRemarksDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getRemarksDd() ) )
            inputDto.setRemarks(StringCriteriaField.getStringCriteriaField(getRemarksDd(), getRemarks(), null));

        if (getDomFactory() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomFactoryDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomFactoryDd() ) )
            inputDto.setDomFactory(StringCriteriaField.getStringCriteriaField(getDomFactoryDd(), getDomFactory(), null));

        if (getDomClass() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomClassDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomClassDd() ) )
            inputDto.setDomClass(StringCriteriaField.getStringCriteriaField(getDomClassDd(), getDomClass(), null));

        if (getDomKey1() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey1Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey1Dd() ) )
            inputDto.setDomKey1(StringCriteriaField.getStringCriteriaField(getDomKey1Dd(), getDomKey1(), null));

        if (getDomKey2() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey2Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey2Dd() ) )
            inputDto.setDomKey2(StringCriteriaField.getStringCriteriaField(getDomKey2Dd(), getDomKey2(), null));

        if (getDomKey3() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey3Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey3Dd() ) )
            inputDto.setDomKey3(StringCriteriaField.getStringCriteriaField(getDomKey3Dd(), getDomKey3(), null));

        if (getDomKey4() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey4Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey4Dd() ) )
            inputDto.setDomKey4(StringCriteriaField.getStringCriteriaField(getDomKey4Dd(), getDomKey4(), null));

        if (getDomKey5() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey5Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey5Dd() ) )
            inputDto.setDomKey5(StringCriteriaField.getStringCriteriaField(getDomKey5Dd(), getDomKey5(), null));

        if (getDomKey6() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDomKey6Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDomKey6Dd() ) )
            inputDto.setDomKey6(StringCriteriaField.getStringCriteriaField(getDomKey6Dd(), getDomKey6(), null));

        if (getAdditionalDataComponent() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getAdditionalDataComponentDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getAdditionalDataComponentDd() ) )
            inputDto.setAdditionalDataComponent(StringCriteriaField.getStringCriteriaField(getAdditionalDataComponentDd(), getAdditionalDataComponent(), null));

        if (getFollowOnFormName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFollowOnFormNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFollowOnFormNameDd() ) )
            inputDto.setFollowOnFormName(StringCriteriaField.getStringCriteriaField(getFollowOnFormNameDd(), getFollowOnFormName(), null));

        if (getFollowOnFormAlternate() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFollowOnFormAlternateDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFollowOnFormAlternateDd() ) )
            inputDto.setFollowOnFormAlternate(StringCriteriaField.getStringCriteriaField(getFollowOnFormAlternateDd(), getFollowOnFormAlternate(), null));

        try {
            if (getCreatedOn() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getCreatedOnDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCreatedOnDd() ) )
                inputDto.setCreatedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getCreatedOnDd(), getCreatedOn(), (DateTimeFieldMetaData) FormDefinitionMeta.META_CREATED_ON));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getCreatedBy() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getCreatedByDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCreatedByDd() ) )
            inputDto.setCreatedBy(StringCriteriaField.getStringCriteriaField(getCreatedByDd(), getCreatedBy(), null));

        try {
            if (getLastChangedOn() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getLastChangedOnDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLastChangedOnDd() ) )
                inputDto.setLastChangedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getLastChangedOnDd(), getLastChangedOn(), (DateTimeFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_ON));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getLastChangedBy() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLastChangedByDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLastChangedByDd() ) )
            inputDto.setLastChangedBy(StringCriteriaField.getStringCriteriaField(getLastChangedByDd(), getLastChangedBy(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IFormDefinitionLookup) Factory.createObject(IFormDefinitionLookup.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Printing.FormDefinitionMaintenance component for creating a new FormDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Printing.FormDefinitionMaintenance component for creating a new FormDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Printing.FormDefinitionMaintenance component for creating a new FormDefinition object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (FormDefinitionMaintenanceComponent) run("Jaffa.Printing.FormDefinitionMaintenance");
        m_createComponent.setReturnToFormKey(formKey);
        // Add the Listener only if a search has been done
        if (getFinderOutDto() != null)
            addListeners(m_createComponent);
        if (m_createComponent instanceof IMaintComponent)
            ((IMaintComponent) m_createComponent).setMode(IMaintComponent.MODE_CREATE);
        // .//GEN-END:_createObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_createObject_1


        // .//GEN-LAST:_createObject_1
        // .//GEN-BEGIN:_createObject_2_be
        return m_createComponent.display();
    }

    private ICreateListener getCreateListener() {
        if (m_createListener == null) {
            m_createListener = new ICreateListener() {
                public void createDone(EventObject source) {
                    try {
                        // .//GEN-END:_createObject_2_be
                        // Add custom code //GEN-FIRST:_createObject_2


                        // .//GEN-LAST:_createObject_2
                        // .//GEN-BEGIN:_createObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Create", e);
                    }
                }
            };
        }
        return m_createListener;
    }
    // .//GEN-END:_createObject_3_be
    // .//GEN-BEGIN:_viewObject_1_be
    /** Calls the Jaffa.Printing.FormDefinitionViewer component for viewing the FormDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException {
        FormDefinitionViewerComponent viewComponent = (FormDefinitionViewerComponent) run("Jaffa.Printing.FormDefinitionViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setFormId(formId);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Printing.FormDefinitionMaintenance component for updating the FormDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (FormDefinitionMaintenanceComponent) run("Jaffa.Printing.FormDefinitionMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setFormId(formId);
        if (m_updateComponent instanceof IMaintComponent)
            ((IMaintComponent) m_updateComponent).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateObject_2


        // .//GEN-LAST:_updateObject_2
        // .//GEN-BEGIN:_updateObject_2_be
        return m_updateComponent.display();
    }

    private IUpdateListener getUpdateListener() {
        if (m_updateListener == null) {
            m_updateListener = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateObject_2_be
                        // Add custom code //GEN-FIRST:_updateObject_1


                        // .//GEN-LAST:_updateObject_1
                        // .//GEN-BEGIN:_updateObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }
    // .//GEN-END:_updateObject_3_be
    // .//GEN-BEGIN:_deleteObject_1_be
    /** Calls the Jaffa.Printing.FormDefinitionMaintenance component for deleting the FormDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.Long formId)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (FormDefinitionMaintenanceComponent) run("Jaffa.Printing.FormDefinitionMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setFormId(formId);
        if (m_deleteComponent instanceof IMaintComponent)
            ((IMaintComponent) m_deleteComponent).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteObject_2


        // .//GEN-LAST:_deleteObject_2
        // .//GEN-BEGIN:_deleteObject_2_be
        return m_deleteComponent.display();
    }

    private IDeleteListener getDeleteListener() {
        if (m_deleteListener == null) {
            m_deleteListener = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteObject_2_be
                        // Add custom code //GEN-FIRST:_deleteObject_1


                        // .//GEN-LAST:_deleteObject_1
                        // .//GEN-BEGIN:_deleteObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListener;
    }
    // .//GEN-END:_deleteObject_3_be
    // .//GEN-BEGIN:_addListeners_1_be
    private void addListeners(Component comp) {
        if (comp  instanceof ICreateComponent)
            ((ICreateComponent) comp).addCreateListener(getCreateListener());
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListener());
    }
    // .//GEN-END:_addListeners_1_be
    // .//GEN-BEGIN:_initializeCriteriaScreen_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initializeCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
