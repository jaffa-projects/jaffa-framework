// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionfinder.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.modules.printing.components.formdefinitionfinder.IFormDefinitionFinder;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderInDto;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutDto;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.formdefinitionviewer.ui.FormDefinitionViewerComponent;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.ui.FormDefinitionMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormDefinitionFinder.
 */
public class FormDefinitionFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(FormDefinitionFinderComponent.class);

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

    private IFormDefinitionFinder m_tx = null;
    private FormDefinitionMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private FormDefinitionMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private FormDefinitionMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public FormDefinitionFinderComponent() {
        super();
        super.setSortDropDown("FormName");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_formDefinitionFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_formDefinitionFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_formDefinitionFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_printing_formDefinitionFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_printing_formDefinitionFinderXmlResults";
    }

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
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        FormDefinitionFinderInDto inputDto = new FormDefinitionFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method//GEN-FIRST:_doInquiry_1


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


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IFormDefinitionFinder) Factory.createObject(IFormDefinitionFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction//GEN-FIRST:_doInquiry_2


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
        // Add custom code before invoking the component//GEN-FIRST:_createObject_1

        
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
                        // Add custom code//GEN-FIRST:_createObject_2


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
        // Add custom code before invoking the component//GEN-FIRST:_viewObject_1


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
        // Add custom code before invoking the component//GEN-FIRST:_updateObject_2

        // when we call maintenance screen through Finder we will set a flage 
        //@FIXME should not be implemented this way
        //m_updateComponent.setMaintenanceScreen(true);

        
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
                        // Add custom code//GEN-FIRST:_updateObject_1


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
        // Add custom code before invoking the component//GEN-FIRST:_deleteObject_2
        
        //@FIXME should not be implemented this way
        //m_deleteComponent.setMaintenanceScreen(true);

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
                        // Add custom code//GEN-FIRST:_deleteObject_1


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
    // All the custom code goes here//GEN-FIRST:_custom


    



    // .//GEN-LAST:_custom
}
