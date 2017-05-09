// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.formeventmaintenance.ui;

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

import org.jaffa.modules.printing.components.formeventmaintenance.IFormEventMaintenance;
import org.jaffa.modules.printing.components.formeventmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormEventMaintenance.
 */
public class FormEventMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(FormEventMaintenanceComponent.class);
    private IFormEventMaintenance m_tx = null;

    private java.lang.String m_eventName = null;
    private java.lang.String m_description = null;


    private FormUsageDto[] m_relatedObjectFormUsageDto = null;
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
        m_relatedObjectFormUsageDto = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        return m_eventName;
    }

    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(java.lang.String eventName) {
        m_eventName = eventName;
    }
    // .//GEN-END:eventName_1_be
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
        FormEventMaintenanceRetrieveOutDto retrieveOutDto = obtainRetrieveOutDto();

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
        FormEventMaintenanceCreateInDto input = new FormEventMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code //GEN-FIRST:_doCreate_1


        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setEventName(getEventName());
        input.setDescription(getDescription());
        FormEventMaintenanceRetrieveOutDto output = createTx().create(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doCreate_2_be
        // Add custom code //GEN-FIRST:_doCreate_2


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
        FormEventMaintenanceUpdateInDto input = new FormEventMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code //GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setEventName(getEventName());
        input.setDescription(getDescription());
        FormEventMaintenanceRetrieveOutDto output = createTx().update(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doUpdate_2_be
        // Add custom code //GEN-FIRST:_doUpdate_2


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
        FormEventMaintenanceDeleteInDto input = new FormEventMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code //GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setEventName(getEventName());
        createTx().delete(input);
        // .//GEN-END:_doDelete_2_be
        // Add custom code //GEN-FIRST:_doDelete_2


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
        FormEventMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_formEventMaintenance_main", true, true, true, true));
        // .//GEN-END:_addScreens_1_be
        // Add custom code //GEN-FIRST:_addScreens_1


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
        FormEventMaintenanceCreateInDto input = new FormEventMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setEventName(getEventName());
        input.setDescription(getDescription());
        FormEventMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateCreate_2_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_2


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
        FormEventMaintenanceUpdateInDto input = new FormEventMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setEventName(getEventName());
        input.setDescription(getDescription());
        FormEventMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateUpdate_2_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_2


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
    private IFormEventMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IFormEventMaintenance) Factory.createObject(IFormEventMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the FormEventMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private FormEventMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        FormEventMaintenanceRetrieveInDto input = new FormEventMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setEventName(getEventName());
        FormEventMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of FormEventMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(FormEventMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setEventName(retrieveOutDto.getEventName());
            setDescription(retrieveOutDto.getDescription());
            m_relatedObjectFormUsageDto = retrieveOutDto.getFormUsage();
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of FormEventMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(FormEventMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setEventName(prevalidateOutDto.getEventName());
            setDescription(prevalidateOutDto.getDescription());
            m_relatedObjectFormUsageDto = prevalidateOutDto.getFormUsage();
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code //GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
