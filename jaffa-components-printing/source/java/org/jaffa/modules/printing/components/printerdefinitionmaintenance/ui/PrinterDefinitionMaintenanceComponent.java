// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui;

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

import org.jaffa.modules.printing.components.printerdefinitionmaintenance.IPrinterDefinitionMaintenance;
import org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.session.ContextManagerFactory;
// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the PrinterDefinitionMaintenance.
 */
public class PrinterDefinitionMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(PrinterDefinitionMaintenanceComponent.class);
    private IPrinterDefinitionMaintenance m_tx = null;

    private java.lang.String m_printerId = null;
    private java.lang.String m_description = null;
    private java.lang.String m_siteCode = null;
    private java.lang.String m_locationCode = null;
    private java.lang.Boolean m_remote = null;
    private java.lang.String m_realPrinterName = null;
    private java.lang.String m_printerOption1 = null;
    private java.lang.String m_printerOption2 = null;
    private java.lang.String m_outputType = null;
    private CodeHelperOutElementDto m_outputTypeCodes = null;
    private java.lang.String m_scaleToPageSize = null;
    private CodeHelperOutElementDto m_scaleToPageSizeCodes = null;
    private ICodeHelper m_codeHelperTx = null;
    private String[] scaleToPageSizesArray;

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method//GEN-FIRST:_quit_1

        m_scaleToPageSizeCodes = null;

        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        if (m_codeHelperTx != null) {
            m_codeHelperTx.destroy();
            m_codeHelperTx = null;
        }
        m_outputTypeCodes = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:printerId_1_be
    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public java.lang.String getPrinterId() {
        return m_printerId;
    }

    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(java.lang.String printerId) {
        m_printerId = printerId;
    }
    // .//GEN-END:printerId_1_be
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
    // .//GEN-BEGIN:siteCode_1_be
    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public java.lang.String getSiteCode() {
        return m_siteCode;
    }

    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(java.lang.String siteCode) {
        m_siteCode = siteCode;
    }
    // .//GEN-END:siteCode_1_be
    // .//GEN-BEGIN:locationCode_1_be
    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public java.lang.String getLocationCode() {
        return m_locationCode;
    }

    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(java.lang.String locationCode) {
        m_locationCode = locationCode;
    }
    // .//GEN-END:locationCode_1_be
    // .//GEN-BEGIN:remote_1_be
    /** Getter for property remote.
     * @return Value of property remote.
     */
    public java.lang.Boolean getRemote() {
        return m_remote;
    }

    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(java.lang.Boolean remote) {
        m_remote = remote;
    }
    // .//GEN-END:remote_1_be
    // .//GEN-BEGIN:realPrinterName_1_be
    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public java.lang.String getRealPrinterName() {
        return m_realPrinterName;
    }

    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(java.lang.String realPrinterName) {
        m_realPrinterName = realPrinterName;
    }
    // .//GEN-END:realPrinterName_1_be
    // .//GEN-BEGIN:printerOption1_1_be
    /** Getter for property printerOption1.
     * @return Value of property printerOption1.
     */
    public java.lang.String getPrinterOption1() {
        return m_printerOption1;
    }

    /** Setter for property printerOption1.
     * @param printerOption1 New value of property printerOption1.
     */
    public void setPrinterOption1(java.lang.String printerOption1) {
        m_printerOption1 = printerOption1;
    }
    // .//GEN-END:printerOption1_1_be
    // .//GEN-BEGIN:printerOption2_1_be
    /** Getter for property printerOption2.
     * @return Value of property printerOption2.
     */
    public java.lang.String getPrinterOption2() {
        return m_printerOption2;
    }

    /** Setter for property printerOption2.
     * @param printerOption2 New value of property printerOption2.
     */
    public void setPrinterOption2(java.lang.String printerOption2) {
        m_printerOption2 = printerOption2;
    }
    // .//GEN-END:printerOption2_1_be
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

    /** Getter for property outputTypeCodes.
     * @return Value of property outputTypeDd.
     */
    public CodeHelperOutElementDto getOutputTypeCodes() {
        return m_outputTypeCodes;
    }
    // .//GEN-END:outputType_1_be
    // .//GEN-BEGIN:scaleToPageSize_1_be
    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public java.lang.String getScaleToPageSize() {
        return m_scaleToPageSize;
    }

    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(java.lang.String scaleToPageSize) {
        m_scaleToPageSize = scaleToPageSize;
    }
    // .//GEN-END:scaleToPageSize_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        PrinterDefinitionMaintenanceCreateInDto input = new PrinterDefinitionMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code//GEN-FIRST:_doCreate_1
        
        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPrinterId(getPrinterId());
        input.setDescription(getDescription());
        input.setSiteCode(getSiteCode());
        input.setLocationCode(getLocationCode());
        input.setRemote(getRemote());
        input.setRealPrinterName(getRealPrinterName());
        input.setPrinterOption1(getPrinterOption1());
        input.setPrinterOption2(getPrinterOption2());
        input.setOutputType(getOutputType());
        input.setScaleToPageSize(getScaleToPageSize());
        PrinterDefinitionMaintenanceRetrieveOutDto output = createTx().create(input);
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
        PrinterDefinitionMaintenanceUpdateInDto input = new PrinterDefinitionMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code//GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setPrinterId(getPrinterId());
        input.setDescription(getDescription());
        input.setSiteCode(getSiteCode());
        input.setLocationCode(getLocationCode());
        input.setRemote(getRemote());
        input.setRealPrinterName(getRealPrinterName());
        input.setPrinterOption1(getPrinterOption1());
        input.setPrinterOption2(getPrinterOption2());
        input.setOutputType(getOutputType());
        input.setScaleToPageSize(getScaleToPageSize());
        PrinterDefinitionMaintenanceRetrieveOutDto output = createTx().update(input);
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
        PrinterDefinitionMaintenanceDeleteInDto input = new PrinterDefinitionMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code//GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setPrinterId(getPrinterId());
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
        PrinterDefinitionMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_printing_printerDefinitionMaintenance_main", true, true, true, true));
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
        PrinterDefinitionMaintenanceCreateInDto input = new PrinterDefinitionMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPrinterId(getPrinterId());
        input.setDescription(getDescription());
        input.setSiteCode(getSiteCode());
        input.setLocationCode(getLocationCode());
        input.setRemote(getRemote());
        input.setRealPrinterName(getRealPrinterName());
        input.setPrinterOption1(getPrinterOption1());
        input.setPrinterOption2(getPrinterOption2());
        input.setOutputType(getOutputType());
        input.setScaleToPageSize(getScaleToPageSize());
        PrinterDefinitionMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
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
        PrinterDefinitionMaintenanceUpdateInDto input = new PrinterDefinitionMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setPrinterId(getPrinterId());
        input.setDescription(getDescription());
        input.setSiteCode(getSiteCode());
        input.setLocationCode(getLocationCode());
        input.setRemote(getRemote());
        input.setRealPrinterName(getRealPrinterName());
        input.setPrinterOption1(getPrinterOption1());
        input.setPrinterOption2(getPrinterOption2());
        input.setOutputType(getOutputType());
        input.setScaleToPageSize(getScaleToPageSize());
        PrinterDefinitionMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
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
        CodeHelperInDto input = null;
        if (m_codeHelperTx == null)
            m_codeHelperTx = (ICodeHelper) Factory.createObject(ICodeHelper.class);
        if (m_outputTypeCodes == null) {
            if (input == null)
                input = new CodeHelperInDto();
            CodeHelperInElementDto codeHelperInElementDto = new CodeHelperInElementDto();
            codeHelperInElementDto.setCode("outputType");
            codeHelperInElementDto.setDomainClassName("org.jaffa.modules.printing.domain.PrinterOutputType");
            codeHelperInElementDto.setCodeFieldName("OutputType");
            codeHelperInElementDto.setDescriptionFieldName("OutputType");
            input.addCodeHelperInElementDto(codeHelperInElementDto);
        }

        // Get the Codes and populate the respective fields
        if (input != null) {
            input.setHeaderDto(getHeaderDto());
            CodeHelperOutDto output = m_codeHelperTx.getCodes(input);
            if (output != null && output.getCodeHelperOutElementDtoCount() > 0) {
                CodeHelperOutElementDto[] codeHelperOutElementDtos = output.getCodeHelperOutElementDtos();
                for (int i = 0; i < codeHelperOutElementDtos.length; i++) {
                    CodeHelperOutElementDto codeHelperOutElementDto = codeHelperOutElementDtos[i];
                    String code = codeHelperOutElementDto.getCode();
                    if (code.equals("outputType"))
                        m_outputTypeCodes = codeHelperOutElementDto;
                }
            }
        }

        // Get comma-separated page size business rule and create drop-down list,
        // if scaleToPageSizes business rule is defined. e.g. scaleToPageSizes=LETTER,A4
        CodeHelperOutElementDto codeHelperOutElementDto;
        CodeHelperOutCodeDto codeHelperOutCodeDto;
        if (scaleToPageSizesArray == null) {
            String scaleToPageSizesRule = (String) ContextManagerFactory.instance().getProperty("jaffa.printing.scaleToPageSizes");
            scaleToPageSizesArray = scaleToPageSizesRule != null ? scaleToPageSizesRule.split(",") : null;
            codeHelperOutElementDto = new CodeHelperOutElementDto();
            if (scaleToPageSizesArray != null) {
                for (String size : scaleToPageSizesArray) {
                    codeHelperOutCodeDto = new CodeHelperOutCodeDto();
                    codeHelperOutCodeDto.setCode(size);
                    codeHelperOutCodeDto.setDescription(size);
                    codeHelperOutElementDto.addCodeHelperOutCodeDto(codeHelperOutCodeDto);
                }
                m_scaleToPageSizeCodes = codeHelperOutElementDto;
            }
        }
    }
    // .//GEN-END:_initDropDownCodes_1_be
    // .//GEN-BEGIN:_createTx_1_be
    private IPrinterDefinitionMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IPrinterDefinitionMaintenance) Factory.createObject(IPrinterDefinitionMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the PrinterDefinitionMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private PrinterDefinitionMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        PrinterDefinitionMaintenanceRetrieveInDto input = new PrinterDefinitionMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPrinterId(getPrinterId());
        PrinterDefinitionMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of PrinterDefinitionMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(PrinterDefinitionMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setPrinterId(retrieveOutDto.getPrinterId());
            setDescription(retrieveOutDto.getDescription());
            setSiteCode(retrieveOutDto.getSiteCode());
            setLocationCode(retrieveOutDto.getLocationCode());
            setRemote(retrieveOutDto.getRemote());
            setRealPrinterName(retrieveOutDto.getRealPrinterName());
            setPrinterOption1(retrieveOutDto.getPrinterOption1());
            setPrinterOption2(retrieveOutDto.getPrinterOption2());
            setOutputType(retrieveOutDto.getOutputType());
            setScaleToPageSize(retrieveOutDto.getScaleToPageSize());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of PrinterDefinitionMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(PrinterDefinitionMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setPrinterId(prevalidateOutDto.getPrinterId());
            setDescription(prevalidateOutDto.getDescription());
            setSiteCode(prevalidateOutDto.getSiteCode());
            setLocationCode(prevalidateOutDto.getLocationCode());
            setRemote(prevalidateOutDto.getRemote());
            setRealPrinterName(prevalidateOutDto.getRealPrinterName());
            setPrinterOption1(prevalidateOutDto.getPrinterOption1());
            setPrinterOption2(prevalidateOutDto.getPrinterOption2());
            setOutputType(prevalidateOutDto.getOutputType());
            setScaleToPageSize(prevalidateOutDto.getScaleToPageSize());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    /** Getter for property scaleToPageSizeCodes.
     * @return Value of property scaleToPageSizeCodes.
     */
    public CodeHelperOutElementDto getScaleToPageSizeCodes() {
        return m_scaleToPageSizeCodes;
    }


    // .//GEN-LAST:_custom
}
