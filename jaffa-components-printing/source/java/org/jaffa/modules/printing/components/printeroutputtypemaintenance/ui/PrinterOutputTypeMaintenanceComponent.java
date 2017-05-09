// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui;

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

import org.jaffa.modules.printing.components.printeroutputtypemaintenance.IPrinterOutputTypeMaintenance;
import org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.*;

import org.jaffa.modules.printing.components.outputcommandmaintenance.ui.OutputCommandMaintenanceComponent;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the PrinterOutputTypeMaintenance.
 */
public class PrinterOutputTypeMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(PrinterOutputTypeMaintenanceComponent.class);
    private IPrinterOutputTypeMaintenance m_tx = null;

    private java.lang.String m_outputType = null;
    private java.lang.String m_description = null;
    private java.lang.Boolean m_directPrinting = null;
    private org.jaffa.datatypes.DateTime m_createdOn = null;
    private java.lang.String m_createdBy = null;
    private org.jaffa.datatypes.DateTime m_lastChangedOn = null;
    private java.lang.String m_lastChangedBy = null;


    private OutputCommandDto[] m_relatedObjectOutputCommandDto = null;
    private OutputCommandMaintenanceComponent m_createOutputCommand = null;
    private ICreateListener m_createListenerOutputCommand = null;
    private OutputCommandMaintenanceComponent m_updateOutputCommand = null;
    private IUpdateListener m_updateListenerOutputCommand = null;
    private OutputCommandMaintenanceComponent m_deleteOutputCommand = null;
    private IDeleteListener m_deleteListenerOutputCommand = null;
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
        m_relatedObjectOutputCommandDto = null;
        if (m_createOutputCommand != null) {
            m_createOutputCommand.quit();
            m_createOutputCommand = null;
        }
        m_createListenerOutputCommand = null;
        if (m_updateOutputCommand != null) {
            m_updateOutputCommand.quit();
            m_updateOutputCommand = null;
        }
        m_updateListenerOutputCommand = null;
        if (m_deleteOutputCommand != null) {
            m_deleteOutputCommand.quit();
            m_deleteOutputCommand = null;
        }
        m_deleteListenerOutputCommand = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
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
    // .//GEN-BEGIN:directPrinting_1_be
    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public java.lang.Boolean getDirectPrinting() {
        return m_directPrinting;
    }

    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(java.lang.Boolean directPrinting) {
        m_directPrinting = directPrinting;
    }
    // .//GEN-END:directPrinting_1_be
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
    // .//GEN-BEGIN:RelatedObjectOutputCommandDto_1_be
    /** Getter for property RelatedObjectOutputCommandDto.
     * @return Value of property RelatedObjectOutputCommandDto.
     */
    public OutputCommandDto[] getRelatedObjectOutputCommandDto() {
        return m_relatedObjectOutputCommandDto;
    }
    // .//GEN-END:RelatedObjectOutputCommandDto_1_be
    // .//GEN-BEGIN:_createOutputCommand_1_be
    /** Calls the Jaffa.Printing.OutputCommandMaintenance component for creating the OutputCommand object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createOutputCommand() throws ApplicationExceptions, FrameworkException {
        if (m_createOutputCommand == null || !m_createOutputCommand.isActive()) {
            m_createOutputCommand = (OutputCommandMaintenanceComponent) run("Jaffa.Printing.OutputCommandMaintenance");
            m_createOutputCommand.setReturnToFormKey(determineFormKey());
            addListenersOutputCommand(m_createOutputCommand);
        }
        m_createOutputCommand.setOutputType(getOutputType());
        m_createOutputCommand.addDisplayOnlyField("outputType");
        if (m_createOutputCommand instanceof IMaintComponent)
            ((IMaintComponent) m_createOutputCommand).setMode(IMaintComponent.MODE_CREATE);
        // .//GEN-END:_createOutputCommand_1_be
        // Add custom code before invoking the component //GEN-FIRST:_createOutputCommand_2


        // .//GEN-LAST:_createOutputCommand_2
        // .//GEN-BEGIN:_createOutputCommand_2_be
        return m_createOutputCommand.display();
    }

    private ICreateListener getCreateListenerOutputCommand() {
        if (m_createListenerOutputCommand == null) {
            m_createListenerOutputCommand = new ICreateListener() {
                public void createDone(EventObject source) {
                    try {
                        // .//GEN-END:_createOutputCommand_2_be
                        // Add custom code //GEN-FIRST:_createOutputCommand_1


                        // .//GEN-LAST:_createOutputCommand_1
                        // .//GEN-BEGIN:_createOutputCommand_3_be
                        retrieveOutputCommand();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the screen after the Create", e);
                    }
                }
            };
        }
        return m_createListenerOutputCommand;
    }
    // .//GEN-END:_createOutputCommand_3_be
    // .//GEN-BEGIN:_updateOutputCommand_1_be
    /** Calls the Jaffa.Printing.OutputCommandMaintenance component for updating the OutputCommand object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException {
        if (m_updateOutputCommand == null || !m_updateOutputCommand.isActive()) {
            m_updateOutputCommand = (OutputCommandMaintenanceComponent) run("Jaffa.Printing.OutputCommandMaintenance");
            m_updateOutputCommand.setReturnToFormKey(determineFormKey());
            addListenersOutputCommand(m_updateOutputCommand);
        }
        m_updateOutputCommand.setOutputCommandId(outputCommandId);
        if (m_updateOutputCommand instanceof IMaintComponent)
            ((IMaintComponent) m_updateOutputCommand).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateOutputCommand_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateOutputCommand_2


        // .//GEN-LAST:_updateOutputCommand_2
        // .//GEN-BEGIN:_updateOutputCommand_2_be
        return m_updateOutputCommand.display();
    }

    private IUpdateListener getUpdateListenerOutputCommand() {
        if (m_updateListenerOutputCommand == null) {
            m_updateListenerOutputCommand = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateOutputCommand_2_be
                        // Add custom code //GEN-FIRST:_updateOutputCommand_1


                        // .//GEN-LAST:_updateOutputCommand_1
                        // .//GEN-BEGIN:_updateOutputCommand_3_be
                        retrieveOutputCommand();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListenerOutputCommand;
    }
    // .//GEN-END:_updateOutputCommand_3_be
    // .//GEN-BEGIN:_deleteOutputCommand_1_be
    /** Calls the Jaffa.Printing.OutputCommandMaintenance component for deleting the OutputCommand object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteOutputCommand(java.lang.Long outputCommandId) throws ApplicationExceptions, FrameworkException {
        if (m_deleteOutputCommand == null || !m_deleteOutputCommand.isActive()) {
            m_deleteOutputCommand = (OutputCommandMaintenanceComponent) run("Jaffa.Printing.OutputCommandMaintenance");
            m_deleteOutputCommand.setReturnToFormKey(determineFormKey());
            addListenersOutputCommand(m_deleteOutputCommand);
        }
        m_deleteOutputCommand.setOutputCommandId(outputCommandId);
        if (m_deleteOutputCommand instanceof IMaintComponent)
            ((IMaintComponent) m_deleteOutputCommand).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteOutputCommand_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteOutputCommand_2


        // .//GEN-LAST:_deleteOutputCommand_2
        // .//GEN-BEGIN:_deleteOutputCommand_2_be
        return m_deleteOutputCommand.display();
    }

    private IDeleteListener getDeleteListenerOutputCommand() {
        if (m_deleteListenerOutputCommand == null) {
            m_deleteListenerOutputCommand = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteOutputCommand_2_be
                        // Add custom code //GEN-FIRST:_deleteOutputCommand_1


                        // .//GEN-LAST:_deleteOutputCommand_1
                        // .//GEN-BEGIN:_deleteOutputCommand_3_be
                        retrieveOutputCommand();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListenerOutputCommand;
    }
    // .//GEN-END:_deleteOutputCommand_3_be
    // .//GEN-BEGIN:_addListenersOutputCommand_1_be
    private void addListenersOutputCommand(Component comp) {
        if (comp  instanceof ICreateComponent)
            ((ICreateComponent) comp).addCreateListener(getCreateListenerOutputCommand());
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListenerOutputCommand());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListenerOutputCommand());
    }
    // .//GEN-END:_addListenersOutputCommand_1_be
    // .//GEN-BEGIN:_retrieveOutputCommand_1_be
    private void retrieveOutputCommand() throws ApplicationExceptions, FrameworkException {
        // perform the retrieve
        PrinterOutputTypeMaintenanceRetrieveOutDto retrieveOutDto = obtainRetrieveOutDto();

        // clear the widget cache
        getUserSession().getWidgetCache(getComponentId()).removeModel("relatedOutputCommand");

        // obtain the data for the related object
        if (retrieveOutDto != null)
            m_relatedObjectOutputCommandDto = retrieveOutDto.getOutputCommand();
    }
    // .//GEN-END:_retrieveOutputCommand_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        PrinterOutputTypeMaintenanceCreateInDto input = new PrinterOutputTypeMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code //GEN-FIRST:_doCreate_1


        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputType(getOutputType());
        input.setDescription(getDescription());
        input.setDirectPrinting(getDirectPrinting());
        PrinterOutputTypeMaintenanceRetrieveOutDto output = createTx().create(input);
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
        PrinterOutputTypeMaintenanceUpdateInDto input = new PrinterOutputTypeMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code //GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputType(getOutputType());
        input.setDescription(getDescription());
        input.setDirectPrinting(getDirectPrinting());
        input.setLastChangedOn(getLastChangedOn());
        PrinterOutputTypeMaintenanceRetrieveOutDto output = createTx().update(input);
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
        PrinterOutputTypeMaintenanceDeleteInDto input = new PrinterOutputTypeMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code //GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputType(getOutputType());
        input.setLastChangedOn(getLastChangedOn());
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
        PrinterOutputTypeMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_printerOutputTypeMaintenance_main", true, true, true, true));
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
        PrinterOutputTypeMaintenanceCreateInDto input = new PrinterOutputTypeMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputType(getOutputType());
        input.setDescription(getDescription());
        input.setDirectPrinting(getDirectPrinting());
        PrinterOutputTypeMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
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
        PrinterOutputTypeMaintenanceUpdateInDto input = new PrinterOutputTypeMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputType(getOutputType());
        input.setDescription(getDescription());
        input.setDirectPrinting(getDirectPrinting());
        input.setLastChangedOn(getLastChangedOn());
        PrinterOutputTypeMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
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
    private IPrinterOutputTypeMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IPrinterOutputTypeMaintenance) Factory.createObject(IPrinterOutputTypeMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the PrinterOutputTypeMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private PrinterOutputTypeMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        PrinterOutputTypeMaintenanceRetrieveInDto input = new PrinterOutputTypeMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputType(getOutputType());
        PrinterOutputTypeMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of PrinterOutputTypeMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(PrinterOutputTypeMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setOutputType(retrieveOutDto.getOutputType());
            setDescription(retrieveOutDto.getDescription());
            setDirectPrinting(retrieveOutDto.getDirectPrinting());
            setCreatedOn(retrieveOutDto.getCreatedOn());
            setCreatedBy(retrieveOutDto.getCreatedBy());
            setLastChangedOn(retrieveOutDto.getLastChangedOn());
            setLastChangedBy(retrieveOutDto.getLastChangedBy());
            m_relatedObjectOutputCommandDto = retrieveOutDto.getOutputCommand();
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of PrinterOutputTypeMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setOutputType(prevalidateOutDto.getOutputType());
            setDescription(prevalidateOutDto.getDescription());
            setDirectPrinting(prevalidateOutDto.getDirectPrinting());
            setCreatedOn(prevalidateOutDto.getCreatedOn());
            setCreatedBy(prevalidateOutDto.getCreatedBy());
            setLastChangedOn(prevalidateOutDto.getLastChangedOn());
            setLastChangedBy(prevalidateOutDto.getLastChangedBy());
            m_relatedObjectOutputCommandDto = prevalidateOutDto.getOutputCommand();
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
