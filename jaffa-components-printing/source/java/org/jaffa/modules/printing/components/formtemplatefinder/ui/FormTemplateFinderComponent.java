// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplatefinder.ui;

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

import org.jaffa.modules.printing.components.formtemplatefinder.IFormTemplateFinder;
import org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderInDto;
import org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderOutDto;
import org.jaffa.modules.printing.domain.FormTemplateMeta;


import org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceComponent;
import org.jaffa.modules.printing.components.formtemplateviewer.ui.FormTemplateViewerComponent;
import org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceComponent;
import org.jaffa.modules.printing.components.formtemplatemaintenance.ui.FormTemplateMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormTemplateFinder.
 */
public class FormTemplateFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(FormTemplateFinderComponent.class);

    private String m_formId = null;
    private String m_formIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_templateData = null;
    private String m_templateDataDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_layoutData = null;
    private String m_layoutDataDd = CriteriaField.RELATIONAL_EQUALS;

    private IFormTemplateFinder m_tx = null;
    private FormTemplateMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private FormTemplateMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private FormTemplateMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public FormTemplateFinderComponent() {
        super();
        super.setSortDropDown("FormId");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_formTemplateFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_formTemplateFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_formTemplateFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_printing_formTemplateFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_printing_formTemplateFinderXmlResults";
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
    // .//GEN-BEGIN:templateData_1_be
    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public String getTemplateData() {
        return m_templateData;
    }

    /** Setter for property templateData.
     * @param templateData New value of property templateData.
     */
    public void setTemplateData(String templateData) {
        m_templateData = templateData;
    }

    /** Getter for property templateDataDd.
     * @return Value of property templateDataDd.
     */
    public String getTemplateDataDd() {
        return m_templateDataDd;
    }

    /** Setter for property templateDataDd.
     * @param templateDataDd New value of property templateDataDd.
     */
    public void setTemplateDataDd(String templateDataDd) {
        m_templateDataDd = templateDataDd;
    }

    // .//GEN-END:templateData_1_be
    // .//GEN-BEGIN:layoutData_1_be
    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public String getLayoutData() {
        return m_layoutData;
    }

    /** Setter for property layoutData.
     * @param layoutData New value of property layoutData.
     */
    public void setLayoutData(String layoutData) {
        m_layoutData = layoutData;
    }

    /** Getter for property layoutDataDd.
     * @return Value of property layoutDataDd.
     */
    public String getLayoutDataDd() {
        return m_layoutDataDd;
    }

    /** Setter for property layoutDataDd.
     * @param layoutDataDd New value of property layoutDataDd.
     */
    public void setLayoutDataDd(String layoutDataDd) {
        m_layoutDataDd = layoutDataDd;
    }

    // .//GEN-END:layoutData_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        FormTemplateFinderInDto inputDto = new FormTemplateFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        try {
            if (getFormId() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getFormIdDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormIdDd() ) )
                inputDto.setFormId(IntegerCriteriaField.getIntegerCriteriaField(getFormIdDd(), getFormId(), (IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getTemplateData() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getTemplateDataDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getTemplateDataDd() ) )
            inputDto.setTemplateData(RawCriteriaField.getRawCriteriaField(getTemplateDataDd(), getTemplateData(), null));

        if (getLayoutData() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLayoutDataDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLayoutDataDd() ) )
            inputDto.setLayoutData(RawCriteriaField.getRawCriteriaField(getLayoutDataDd(), getLayoutData(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IFormTemplateFinder) Factory.createObject(IFormTemplateFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Printing.FormTemplateMaintenance component for creating a new FormTemplate object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Printing.FormTemplateMaintenance component for creating a new FormTemplate object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Printing.FormTemplateMaintenance component for creating a new FormTemplate object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (FormTemplateMaintenanceComponent) run("Jaffa.Printing.FormTemplateMaintenance");
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
    /** Calls the Jaffa.Printing.FormTemplateViewer component for viewing the FormTemplate object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException {
        FormTemplateViewerComponent viewComponent = (FormTemplateViewerComponent) run("Jaffa.Printing.FormTemplateViewer");
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
    /** Calls the Jaffa.Printing.FormTemplateMaintenance component for updating the FormTemplate object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.Long formId) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (FormTemplateMaintenanceComponent) run("Jaffa.Printing.FormTemplateMaintenance");
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
    /** Calls the Jaffa.Printing.FormTemplateMaintenance component for deleting the FormTemplate object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.Long formId)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (FormTemplateMaintenanceComponent) run("Jaffa.Printing.FormTemplateMaintenance");
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
