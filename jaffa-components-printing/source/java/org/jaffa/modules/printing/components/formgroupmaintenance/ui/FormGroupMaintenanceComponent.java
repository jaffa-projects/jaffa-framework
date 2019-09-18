// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.formgroupmaintenance.ui;

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

import org.jaffa.modules.printing.components.formgroupmaintenance.IFormGroupMaintenance;
import org.jaffa.modules.printing.components.formgroupmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports

import org.jaffa.modules.printing.components.formeventlookup.*;
import org.jaffa.modules.printing.components.formeventlookup.dto.*;
import org.jaffa.modules.printing.components.formeventlookup.ui.*;
import org.jaffa.components.lookup.*;
import org.jaffa.exceptions.ApplicationException;
import java.util.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.modules.printing.domain.FormGroupMeta;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormGroupMaintenance.
 */
public class FormGroupMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(FormGroupMaintenanceComponent.class);
    private IFormGroupMaintenance m_tx = null;

    private java.lang.String m_formName = null;
    private java.lang.String m_description = null;
    private java.lang.String m_formType = null;


    private FormUsageDto[] m_relatedObjectFormUsageDto = null;
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
        m_relatedObjectFormUsageDto = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
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
    // .//GEN-BEGIN:formType_1_be
    /** Getter for property formType.
     * @return Value of property formType.
     */
    public java.lang.String getFormType() {
        return m_formType;
    }

    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(java.lang.String formType) {
        m_formType = formType;
    }
    // .//GEN-END:formType_1_be
    // .//GEN-BEGIN:RelatedObjectFormUsageDto_1_be
    /** Getter for property RelatedObjectFormUsageDto.
     * @return Value of property RelatedObjectFormUsageDto.
     */
    public FormUsageDto[] getRelatedObjectFormUsageDto() {
        return m_relatedObjectFormUsageDto;
    }
    // .//GEN-END:RelatedObjectFormUsageDto_1_be
    // .//GEN-BEGIN:_retrieveFormUsage_1_be
    private void retrieveFormUsage() throws ApplicationExceptions, FrameworkException {
        // perform the retrieve
        FormGroupMaintenanceRetrieveOutDto retrieveOutDto = obtainRetrieveOutDto();

        // clear the widget cache
        getUserSession().getWidgetCache(getComponentId()).removeModel("relatedFormUsage");

        // obtain the data for the related object
        if (retrieveOutDto != null)
            m_relatedObjectFormUsageDto = retrieveOutDto.getFormUsage();
    }
    // .//GEN-END:_retrieveFormUsage_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        FormGroupMaintenanceCreateInDto input = new FormGroupMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code//GEN-FIRST:_doCreate_1

        if (getFormType() == null || getFormType().trim().equals("")){
            throw new ApplicationExceptions(new MandatoryFieldException(FormGroupMeta.META_FORM_TYPE.getLabelToken()));
        }

        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormName(getFormName());
        input.setDescription(getDescription());
        input.setFormType(getFormType());
        FormGroupMaintenanceRetrieveOutDto output = createTx().create(input);
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
        FormGroupMaintenanceUpdateInDto input = new FormGroupMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code//GEN-FIRST:_doUpdate_1

        if (getFormType() == null || getFormType().trim().equals("")){
            throw new ApplicationExceptions(new MandatoryFieldException(FormGroupMeta.META_FORM_TYPE.getLabelToken()));
        }

        if (m_relatedObjectFormUsageDto != null)
            input.setFormUsage(m_relatedObjectFormUsageDto);
        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormName(getFormName());
        input.setDescription(getDescription());
        input.setFormType(getFormType());
        FormGroupMaintenanceRetrieveOutDto output = createTx().update(input);
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
        FormGroupMaintenanceDeleteInDto input = new FormGroupMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code//GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormName(getFormName());
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
        FormGroupMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_formGroupMaintenance_main", true, true, true, true));
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
        FormGroupMaintenanceCreateInDto input = new FormGroupMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormName(getFormName());
        input.setDescription(getDescription());
        input.setFormType(getFormType());
        FormGroupMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
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
        FormGroupMaintenanceUpdateInDto input = new FormGroupMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setFormName(getFormName());
        input.setDescription(getDescription());
        input.setFormType(getFormType());
        FormGroupMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
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
    private IFormGroupMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IFormGroupMaintenance) Factory.createObject(IFormGroupMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the FormGroupMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private FormGroupMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        FormGroupMaintenanceRetrieveInDto input = new FormGroupMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setFormName(getFormName());
        FormGroupMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of FormGroupMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(FormGroupMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setFormName(retrieveOutDto.getFormName());
            setDescription(retrieveOutDto.getDescription());
            setFormType(retrieveOutDto.getFormType());
            m_relatedObjectFormUsageDto = retrieveOutDto.getFormUsage();
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of FormGroupMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(FormGroupMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setFormName(prevalidateOutDto.getFormName());
            setDescription(prevalidateOutDto.getDescription());
            setFormType(prevalidateOutDto.getFormType());
            m_relatedObjectFormUsageDto = prevalidateOutDto.getFormUsage();
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    // Remove the select FormUsage rows from the grid
    public void removeFormUsageEntry(ArrayList arr,GridModel model) throws FrameworkException, ApplicationExceptions {
        for(Iterator itr=model.getRows().iterator();itr.hasNext();){
            GridModelRow row = (GridModelRow)itr.next();
            for(int j=0; j < m_relatedObjectFormUsageDto.length;j++){
                if( m_relatedObjectFormUsageDto[j].getEventName().equals( ((String)row.getElement("eventName")) )){
                    m_relatedObjectFormUsageDto[j].setFormAlternate(((EditBoxModel)row.getElement("formAlternate")).getValue());
                    try{
                        EditBoxModel copiesElement = (EditBoxModel) row.getElement("copies");
                        if (copiesElement == null) {
                            throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormGroupMaintenance.copiesValidation") {});
                        }
                        String copies = copiesElement.getValue();
                        if (copies == null) {
                            throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormGroupMaintenance.copiesValidation") {});
                        }
                        m_relatedObjectFormUsageDto[j].setCopies(Long.valueOf(copies));
                    }catch(NumberFormatException ne){
                        throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormGroupMaintenance.copiesValidation") {});
                    }
                    break;
                }
            }
        }
        ArrayList relatedFormUsageEntryDto=new ArrayList(Arrays.asList(m_relatedObjectFormUsageDto));
        String tc=null;
        for (int i=0;i<arr.size(); i++ ) {
            tc=(String)arr.get(i);
            for (int j =0;j<relatedFormUsageEntryDto.size();j++ ) {
                FormUsageDto usageDto=(FormUsageDto)relatedFormUsageEntryDto.get(j);
                if((usageDto.getEventName()).equals(tc)){
                    relatedFormUsageEntryDto.remove(j);
                    break;
                }
            }
        }
        m_relatedObjectFormUsageDto = (FormUsageDto[]) relatedFormUsageEntryDto.toArray(new FormUsageDto[0]);
        uncacheWidgetModels();
    }
    // Dupicate check on the FormUsage rows from the grid
    public boolean duplicateCheckForFormUsageEntry(List arr,String eventname) {
        for (int i=0;i<arr.size(); i++ ) {
            FormUsageDto usageDto =(FormUsageDto)arr.get(i);
            if((usageDto.getEventName()).equals(eventname)) return true;
        }
        return false;
    }
    // Invokes the FormEventLookup screen and adds the selected rows to the FormUsage Grid
    public FormKey invokeFormEventLookup() throws FrameworkException, ApplicationExceptions {
        FormEventLookupComponent comp = (FormEventLookupComponent) run("Jaffa.Printing.FormEventLookup");
        comp.setReturnToFormKey(determineFormKey());
        comp.addMultiSelectLookupListener(new IMultiSelectLookupListener() {
            public void rowsSelected(MultiSelectLookupEvent event) {
                Object[] selectedRows = event.getSelectedRows();
                if (selectedRows != null && selectedRows.length > 0) {
                    List list = m_relatedObjectFormUsageDto != null ? new ArrayList(Arrays.asList(m_relatedObjectFormUsageDto)) : new ArrayList();
                    for (int i = 0; i < selectedRows.length; i++) {
                        // For each selectedRow, create an FormUsageDto object and add it to the Map
                        // Do not add duplicates
                        FormEventLookupOutRowDto row = (FormEventLookupOutRowDto) selectedRows[i];
                        FormUsageDto newFormUsageEntry = new FormUsageDto();
                        newFormUsageEntry.setEventName(row.getEventName());
                        newFormUsageEntry.setFormAlternate("");
                        newFormUsageEntry.setDescription(row.getDescription());
                        newFormUsageEntry.setCopies(new Long("1"));
                        if (!duplicateCheckForFormUsageEntry(list,row.getEventName()))  // Duplication Stops here
                            list.add(newFormUsageEntry);
                        //m_formUsageEntries.put(row.getEventName(), newFormUsageEntry);

                    }
                    m_relatedObjectFormUsageDto = (FormUsageDto[]) list.toArray(new FormUsageDto[0]);
                    // clear the cache
                    uncacheWidgetModels();
                }
            }
        });
        return comp.display();
    }
    // processing the rows for editboxmodel
    public boolean processRows(GridModel model) throws FrameworkException,ApplicationExceptions {
        boolean validationFlag=true;
        for(Iterator itr=model.getRows().iterator();itr.hasNext();){
            GridModelRow row = (GridModelRow)itr.next();
        }
        for(int j=0; j < m_relatedObjectFormUsageDto.length;j++){
            validationFlag=createTx().doValidations(getFormName(),m_relatedObjectFormUsageDto[j].getFormAlternate());
            if(!validationFlag)
                break;
        }
        return validationFlag;
    }
    public void setFormUsage(FormUsageDto[] formusagedtos){
        m_relatedObjectFormUsageDto=formusagedtos;
    }
    // .//GEN-LAST:_custom
}
