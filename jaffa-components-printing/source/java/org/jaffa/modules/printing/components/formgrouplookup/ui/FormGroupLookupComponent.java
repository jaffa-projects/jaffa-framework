// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgrouplookup.ui;

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

import org.jaffa.modules.printing.components.formgrouplookup.IFormGroupLookup;
import org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupInDto;
import org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutDto;
import org.jaffa.modules.printing.domain.FormGroupMeta;


import org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceComponent;
import org.jaffa.modules.printing.components.formgroupviewer.ui.FormGroupViewerComponent;
import org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceComponent;
import org.jaffa.modules.printing.components.formgroupmaintenance.ui.FormGroupMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormGroupLookup.
 */
public class FormGroupLookupComponent extends LookupComponent2 {

    private static Logger log = Logger.getLogger(FormGroupLookupComponent.class);

    private String m_formName = null;
    private String m_formNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_description = null;
    private String m_descriptionDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_formType = null;
    private String m_formTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_createdOn = null;
    private String m_createdOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_createdBy = null;
    private String m_createdByDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastChangedOn = null;
    private String m_lastChangedOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastChangedBy = null;
    private String m_lastChangedByDd = CriteriaField.RELATIONAL_EQUALS;

    private IFormGroupLookup m_tx = null;
    private FormGroupMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private FormGroupMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private FormGroupMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public FormGroupLookupComponent() {
        super();
        super.setSortDropDown("FormName");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_formGroupLookupCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_formGroupLookupResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_formGroupLookupConsolidatedCriteriaAndResults";
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
    // .//GEN-BEGIN:formType_1_be
    /** Getter for property formType.
     * @return Value of property formType.
     */
    public String getFormType() {
        return m_formType;
    }

    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(String formType) {
        m_formType = formType;
    }

    /** Getter for property formTypeDd.
     * @return Value of property formTypeDd.
     */
    public String getFormTypeDd() {
        return m_formTypeDd;
    }

    /** Setter for property formTypeDd.
     * @param formTypeDd New value of property formTypeDd.
     */
    public void setFormTypeDd(String formTypeDd) {
        m_formTypeDd = formTypeDd;
    }

    // .//GEN-END:formType_1_be
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
        FormGroupLookupInDto inputDto = new FormGroupLookupInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getFormName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormNameDd() ) )
            inputDto.setFormName(StringCriteriaField.getStringCriteriaField(getFormNameDd(), getFormName(), null));

        if (getDescription() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDescriptionDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDescriptionDd() ) )
            inputDto.setDescription(StringCriteriaField.getStringCriteriaField(getDescriptionDd(), getDescription(), null));

        if (getFormType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFormTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFormTypeDd() ) )
            inputDto.setFormType(StringCriteriaField.getStringCriteriaField(getFormTypeDd(), getFormType(), null));

        try {
            if (getCreatedOn() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getCreatedOnDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCreatedOnDd() ) )
                inputDto.setCreatedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getCreatedOnDd(), getCreatedOn(), (DateTimeFieldMetaData) FormGroupMeta.META_CREATED_ON));
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
                inputDto.setLastChangedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getLastChangedOnDd(), getLastChangedOn(), (DateTimeFieldMetaData) FormGroupMeta.META_LAST_CHANGED_ON));
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
            m_tx = (IFormGroupLookup) Factory.createObject(IFormGroupLookup.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Printing.FormGroupMaintenance component for creating a new FormGroup object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Printing.FormGroupMaintenance component for creating a new FormGroup object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Printing.FormGroupMaintenance component for creating a new FormGroup object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (FormGroupMaintenanceComponent) run("Jaffa.Printing.FormGroupMaintenance");
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
    /** Calls the Jaffa.Printing.FormGroupViewer component for viewing the FormGroup object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException {
        FormGroupViewerComponent viewComponent = (FormGroupViewerComponent) run("Jaffa.Printing.FormGroupViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setFormName(formName);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Printing.FormGroupMaintenance component for updating the FormGroup object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String formName) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (FormGroupMaintenanceComponent) run("Jaffa.Printing.FormGroupMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setFormName(formName);
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
    /** Calls the Jaffa.Printing.FormGroupMaintenance component for deleting the FormGroup object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String formName)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (FormGroupMaintenanceComponent) run("Jaffa.Printing.FormGroupMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setFormName(formName);
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
