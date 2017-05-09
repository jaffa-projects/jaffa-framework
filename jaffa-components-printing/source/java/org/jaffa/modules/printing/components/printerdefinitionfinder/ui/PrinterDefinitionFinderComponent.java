// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printerdefinitionfinder.ui;

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

import org.jaffa.modules.printing.components.printerdefinitionfinder.IPrinterDefinitionFinder;
import org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderInDto;
import org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderOutDto;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;


import org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.printerdefinitionviewer.ui.PrinterDefinitionViewerComponent;
import org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceComponent;
import org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui.PrinterDefinitionMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.session.ContextManagerFactory;



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the PrinterDefinitionFinder.
 */
public class PrinterDefinitionFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(PrinterDefinitionFinderComponent.class);

    private String m_printerId = null;
    private String m_printerIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_description = null;
    private String m_descriptionDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_siteCode = null;
    private String m_siteCodeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_locationCode = null;
    private String m_locationCodeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_realPrinterName = null;
    private String m_realPrinterNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_outputType = null;
    private String m_outputTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private CodeHelperOutElementDto m_outputTypeCodes = null;
    private String m_scaleToPageSize = null;
    private String m_scaleToPageSizeDd = CriteriaField.RELATIONAL_EQUALS;
    private CodeHelperOutElementDto m_scaleToPageSizeCodes = null;
    private String[] scaleToPageSizesArray;
    private String m_remote = null;

    private IPrinterDefinitionFinder m_tx = null;
    private PrinterDefinitionMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private PrinterDefinitionMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private PrinterDefinitionMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;
    private ICodeHelper m_codeHelperTx = null;

    public PrinterDefinitionFinderComponent() {
        super();
        super.setSortDropDown("PrinterId");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_printerDefinitionFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_printerDefinitionFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_printerDefinitionFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_printing_printerDefinitionFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_printing_printerDefinitionFinderXmlResults";
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method //GEN-FIRST:_quit_1

        m_scaleToPageSizeCodes = null;

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
    public String getPrinterId() {
        return m_printerId;
    }

    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(String printerId) {
        m_printerId = printerId;
    }

    /** Getter for property printerIdDd.
     * @return Value of property printerIdDd.
     */
    public String getPrinterIdDd() {
        return m_printerIdDd;
    }

    /** Setter for property printerIdDd.
     * @param printerIdDd New value of property printerIdDd.
     */
    public void setPrinterIdDd(String printerIdDd) {
        m_printerIdDd = printerIdDd;
    }

    // .//GEN-END:printerId_1_be
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
    // .//GEN-BEGIN:siteCode_1_be
    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public String getSiteCode() {
        return m_siteCode;
    }

    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(String siteCode) {
        m_siteCode = siteCode;
    }

    /** Getter for property siteCodeDd.
     * @return Value of property siteCodeDd.
     */
    public String getSiteCodeDd() {
        return m_siteCodeDd;
    }

    /** Setter for property siteCodeDd.
     * @param siteCodeDd New value of property siteCodeDd.
     */
    public void setSiteCodeDd(String siteCodeDd) {
        m_siteCodeDd = siteCodeDd;
    }

    // .//GEN-END:siteCode_1_be
    // .//GEN-BEGIN:locationCode_1_be
    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public String getLocationCode() {
        return m_locationCode;
    }

    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(String locationCode) {
        m_locationCode = locationCode;
    }

    /** Getter for property locationCodeDd.
     * @return Value of property locationCodeDd.
     */
    public String getLocationCodeDd() {
        return m_locationCodeDd;
    }

    /** Setter for property locationCodeDd.
     * @param locationCodeDd New value of property locationCodeDd.
     */
    public void setLocationCodeDd(String locationCodeDd) {
        m_locationCodeDd = locationCodeDd;
    }

    // .//GEN-END:locationCode_1_be
    // .//GEN-BEGIN:realPrinterName_1_be
    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public String getRealPrinterName() {
        return m_realPrinterName;
    }

    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(String realPrinterName) {
        m_realPrinterName = realPrinterName;
    }

    /** Getter for property realPrinterNameDd.
     * @return Value of property realPrinterNameDd.
     */
    public String getRealPrinterNameDd() {
        return m_realPrinterNameDd;
    }

    /** Setter for property realPrinterNameDd.
     * @param realPrinterNameDd New value of property realPrinterNameDd.
     */
    public void setRealPrinterNameDd(String realPrinterNameDd) {
        m_realPrinterNameDd = realPrinterNameDd;
    }

    // .//GEN-END:realPrinterName_1_be
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
    public String getScaleToPageSize() {
        return m_scaleToPageSize;
    }

    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(String scaleToPageSize) {
        m_scaleToPageSize = scaleToPageSize;
    }

    /** Getter for property scaleToPageSizeDd.
     * @return Value of property scaleToPageSizeDd.
     */
    public String getScaleToPageSizeDd() {
        return m_scaleToPageSizeDd;
    }

    /** Setter for property scaleToPageSizeDd.
     * @param scaleToPageSizeDd New value of property scaleToPageSizeDd.
     */
    public void setScaleToPageSizeDd(String scaleToPageSizeDd) {
        m_scaleToPageSizeDd = scaleToPageSizeDd;
    }

    // .//GEN-END:scaleToPageSize_1_be
    // .//GEN-BEGIN:remote_1_be
    /** Getter for property remote.
     * @return Value of property remote.
     */
    public String getRemote() {
        return m_remote;
    }

    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(String remote) {
        m_remote = remote;
    }

    // .//GEN-END:remote_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        PrinterDefinitionFinderInDto inputDto = new PrinterDefinitionFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getPrinterId() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getPrinterIdDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getPrinterIdDd() ) )
            inputDto.setPrinterId(StringCriteriaField.getStringCriteriaField(getPrinterIdDd(), getPrinterId(), null));

        if (getDescription() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDescriptionDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDescriptionDd() ) )
            inputDto.setDescription(StringCriteriaField.getStringCriteriaField(getDescriptionDd(), getDescription(), null));

        if (getSiteCode() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSiteCodeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSiteCodeDd() ) )
            inputDto.setSiteCode(StringCriteriaField.getStringCriteriaField(getSiteCodeDd(), getSiteCode(), null));

        if (getLocationCode() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLocationCodeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLocationCodeDd() ) )
            inputDto.setLocationCode(StringCriteriaField.getStringCriteriaField(getLocationCodeDd(), getLocationCode(), null));

        if (getRealPrinterName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getRealPrinterNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getRealPrinterNameDd() ) )
            inputDto.setRealPrinterName(StringCriteriaField.getStringCriteriaField(getRealPrinterNameDd(), getRealPrinterName(), null));

        if (getOutputType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getOutputTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getOutputTypeDd() ) )
            inputDto.setOutputType(StringCriteriaField.getStringCriteriaField(getOutputTypeDd(), getOutputType(), null));

        if (getScaleToPageSize() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getScaleToPageSizeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getScaleToPageSizeDd() ) )
            inputDto.setScaleToPageSize(StringCriteriaField.getStringCriteriaField(getScaleToPageSizeDd(), getScaleToPageSize(), null));

        if (getRemote() != null)
            inputDto.setRemote(BooleanCriteriaField.getBooleanCriteriaField(CriteriaField.RELATIONAL_EQUALS, getRemote(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IPrinterDefinitionFinder) Factory.createObject(IPrinterDefinitionFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Printing.PrinterDefinitionMaintenance component for creating a new PrinterDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Printing.PrinterDefinitionMaintenance component for creating a new PrinterDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Printing.PrinterDefinitionMaintenance component for creating a new PrinterDefinition object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (PrinterDefinitionMaintenanceComponent) run("Jaffa.Printing.PrinterDefinitionMaintenance");
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
    /** Calls the Jaffa.Printing.PrinterDefinitionViewer component for viewing the PrinterDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException {
        PrinterDefinitionViewerComponent viewComponent = (PrinterDefinitionViewerComponent) run("Jaffa.Printing.PrinterDefinitionViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setPrinterId(printerId);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Printing.PrinterDefinitionMaintenance component for updating the PrinterDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String printerId) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (PrinterDefinitionMaintenanceComponent) run("Jaffa.Printing.PrinterDefinitionMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setPrinterId(printerId);
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
    /** Calls the Jaffa.Printing.PrinterDefinitionMaintenance component for deleting the PrinterDefinition object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String printerId)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (PrinterDefinitionMaintenanceComponent) run("Jaffa.Printing.PrinterDefinitionMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setPrinterId(printerId);
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
        if (m_codeHelperTx == null)
            m_codeHelperTx = (ICodeHelper) Factory.createObject(ICodeHelper.class);
        if (m_outputTypeCodes == null) {
            if (input == null)
                input = new CodeHelperInDto();
            CodeHelperInElementDto codeHelperInElementDto = new CodeHelperInElementDto();
            codeHelperInElementDto.setCode("outputType");
            codeHelperInElementDto.setDomainClassName("org.jaffa.modules.printing.domain.PrinterOutputType");
            codeHelperInElementDto.setCodeFieldName("OutputType");
            codeHelperInElementDto.setDescriptionFieldName("Description");
            codeHelperInElementDto.setAppendCodeAndDescription(true);
            input.addCodeHelperInElementDto(codeHelperInElementDto);
        }

        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

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
    // .//GEN-END:_initializeCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom

    /** Getter for property scaleToPageSizeCodes.
     * @return Value of property scaleToPageSizeCodes.
     */
    public CodeHelperOutElementDto getScaleToPageSizeCodes() {
        return m_scaleToPageSizeCodes;
    }




    // .//GEN-LAST:_custom
}
