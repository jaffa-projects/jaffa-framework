// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.outputcommandmaintenance.ui;

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

import org.jaffa.modules.printing.components.outputcommandmaintenance.IOutputCommandMaintenance;
import org.jaffa.modules.printing.components.outputcommandmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the OutputCommandMaintenance.
 */
public class OutputCommandMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(OutputCommandMaintenanceComponent.class);
    private IOutputCommandMaintenance m_tx = null;

    private java.lang.Long m_outputCommandId = null;
    private java.lang.Long m_sequenceNo = null;
    private java.lang.String m_osPattern = null;
    private java.lang.String m_commandLine = null;
    private java.lang.String m_outputType = null;


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
    // .//GEN-BEGIN:outputCommandId_1_be
    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public java.lang.Long getOutputCommandId() {
        return m_outputCommandId;
    }

    /** Setter for property outputCommandId.
     * @param outputCommandId New value of property outputCommandId.
     */
    public void setOutputCommandId(java.lang.Long outputCommandId) {
        m_outputCommandId = outputCommandId;
    }
    // .//GEN-END:outputCommandId_1_be
    // .//GEN-BEGIN:sequenceNo_1_be
    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public java.lang.Long getSequenceNo() {
        return m_sequenceNo;
    }

    /** Setter for property sequenceNo.
     * @param sequenceNo New value of property sequenceNo.
     */
    public void setSequenceNo(java.lang.Long sequenceNo) {
        m_sequenceNo = sequenceNo;
    }
    // .//GEN-END:sequenceNo_1_be
    // .//GEN-BEGIN:osPattern_1_be
    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public java.lang.String getOsPattern() {
        return m_osPattern;
    }

    /** Setter for property osPattern.
     * @param osPattern New value of property osPattern.
     */
    public void setOsPattern(java.lang.String osPattern) {
        m_osPattern = osPattern;
    }
    // .//GEN-END:osPattern_1_be
    // .//GEN-BEGIN:commandLine_1_be
    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public java.lang.String getCommandLine() {
        return m_commandLine;
    }

    /** Setter for property commandLine.
     * @param commandLine New value of property commandLine.
     */
    public void setCommandLine(java.lang.String commandLine) {
        m_commandLine = commandLine;
    }
    // .//GEN-END:commandLine_1_be
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
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        OutputCommandMaintenanceCreateInDto input = new OutputCommandMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code//GEN-FIRST:_doCreate_1


        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputCommandId(getOutputCommandId());
        input.setSequenceNo(getSequenceNo());
        input.setOsPattern(getOsPattern());
        input.setCommandLine(getCommandLine());
        input.setOutputType(getOutputType());
        OutputCommandMaintenanceRetrieveOutDto output = createTx().create(input);
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
        OutputCommandMaintenanceUpdateInDto input = new OutputCommandMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code//GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputCommandId(getOutputCommandId());
        input.setSequenceNo(getSequenceNo());
        input.setOsPattern(getOsPattern());
        input.setCommandLine(getCommandLine());
        input.setOutputType(getOutputType());
        OutputCommandMaintenanceRetrieveOutDto output = createTx().update(input);
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
        OutputCommandMaintenanceDeleteInDto input = new OutputCommandMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code//GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputCommandId(getOutputCommandId());
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
        OutputCommandMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_outputCommandMaintenance_main", true, true, true, true));
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
        OutputCommandMaintenanceCreateInDto input = new OutputCommandMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputCommandId(getOutputCommandId());
        input.setSequenceNo(getSequenceNo());
        input.setOsPattern(getOsPattern());
        input.setCommandLine(getCommandLine());
        input.setOutputType(getOutputType());
        OutputCommandMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
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
        OutputCommandMaintenanceUpdateInDto input = new OutputCommandMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setOutputCommandId(getOutputCommandId());
        input.setSequenceNo(getSequenceNo());
        input.setOsPattern(getOsPattern());
        input.setCommandLine(getCommandLine());
        input.setOutputType(getOutputType());
        OutputCommandMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
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
    private IOutputCommandMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IOutputCommandMaintenance) Factory.createObject(IOutputCommandMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the OutputCommandMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private OutputCommandMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        OutputCommandMaintenanceRetrieveInDto input = new OutputCommandMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setOutputCommandId(getOutputCommandId());
        OutputCommandMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of OutputCommandMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(OutputCommandMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setOutputCommandId(retrieveOutDto.getOutputCommandId());
            setSequenceNo(retrieveOutDto.getSequenceNo());
            setOsPattern(retrieveOutDto.getOsPattern());
            setCommandLine(retrieveOutDto.getCommandLine());
            setOutputType(retrieveOutDto.getOutputType());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of OutputCommandMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(OutputCommandMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setOutputCommandId(prevalidateOutDto.getOutputCommandId());
            setSequenceNo(prevalidateOutDto.getSequenceNo());
            setOsPattern(prevalidateOutDto.getOsPattern());
            setCommandLine(prevalidateOutDto.getCommandLine());
            setOutputType(prevalidateOutDto.getOutputType());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        if (isCreateMode() && getSequenceNo() == null && getOutputType() != null)
            setSequenceNo(createTx().getNextSequence(getOutputType()));
        return super.display();
    }


    // .//GEN-LAST:_custom
}
