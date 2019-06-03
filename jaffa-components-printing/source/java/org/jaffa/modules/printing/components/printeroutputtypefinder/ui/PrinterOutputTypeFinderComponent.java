// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypefinder.ui;

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

import org.jaffa.modules.printing.components.printeroutputtypefinder.IPrinterOutputTypeFinder;
import org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderInDto;
import org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderOutDto;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;


import org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceComponent;
import org.jaffa.modules.printing.components.printeroutputtypeviewer.ui.PrinterOutputTypeViewerComponent;
import org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceComponent;
import org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui.PrinterOutputTypeMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the PrinterOutputTypeFinder.
 */
public class PrinterOutputTypeFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(PrinterOutputTypeFinderComponent.class);

    private String m_outputType = null;
    private String m_outputTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_description = null;
    private String m_descriptionDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_directPrinting = null;

    private IPrinterOutputTypeFinder m_tx = null;
    private PrinterOutputTypeMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private PrinterOutputTypeMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private PrinterOutputTypeMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public PrinterOutputTypeFinderComponent() {
        super();
        super.setSortDropDown("OutputType");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_printerOutputTypeFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_printerOutputTypeFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_printerOutputTypeFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_printing_printerOutputTypeFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_printing_printerOutputTypeFinderXmlResults";
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
    // .//GEN-BEGIN:directPrinting_1_be
    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public String getDirectPrinting() {
        return m_directPrinting;
    }

    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(String directPrinting) {
        m_directPrinting = directPrinting;
    }

    // .//GEN-END:directPrinting_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        PrinterOutputTypeFinderInDto inputDto = new PrinterOutputTypeFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getOutputType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getOutputTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getOutputTypeDd() ) )
            inputDto.setOutputType(StringCriteriaField.getStringCriteriaField(getOutputTypeDd(), getOutputType(), null));

        if (getDescription() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getDescriptionDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getDescriptionDd() ) )
            inputDto.setDescription(StringCriteriaField.getStringCriteriaField(getDescriptionDd(), getDescription(), null));

        if (getDirectPrinting() != null)
            inputDto.setDirectPrinting(BooleanCriteriaField.getBooleanCriteriaField(CriteriaField.RELATIONAL_EQUALS, getDirectPrinting(), null));

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IPrinterOutputTypeFinder) Factory.createObject(IPrinterOutputTypeFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Printing.PrinterOutputTypeMaintenance component for creating a new PrinterOutputType object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Printing.PrinterOutputTypeMaintenance component for creating a new PrinterOutputType object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Printing.PrinterOutputTypeMaintenance component for creating a new PrinterOutputType object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (PrinterOutputTypeMaintenanceComponent) run("Jaffa.Printing.PrinterOutputTypeMaintenance");
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
    /** Calls the Jaffa.Printing.PrinterOutputTypeViewer component for viewing the PrinterOutputType object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException {
        PrinterOutputTypeViewerComponent viewComponent = (PrinterOutputTypeViewerComponent) run("Jaffa.Printing.PrinterOutputTypeViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setOutputType(outputType);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Printing.PrinterOutputTypeMaintenance component for updating the PrinterOutputType object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String outputType) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (PrinterOutputTypeMaintenanceComponent) run("Jaffa.Printing.PrinterOutputTypeMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setOutputType(outputType);
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
    /** Calls the Jaffa.Printing.PrinterOutputTypeMaintenance component for deleting the PrinterOutputType object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String outputType)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (PrinterOutputTypeMaintenanceComponent) run("Jaffa.Printing.PrinterOutputTypeMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setOutputType(outputType);
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
