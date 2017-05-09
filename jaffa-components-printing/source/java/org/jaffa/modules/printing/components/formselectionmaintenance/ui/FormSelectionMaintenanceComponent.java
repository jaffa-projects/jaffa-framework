// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formselectionmaintenance.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.*;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.security.VariationContext;
import org.jaffa.modules.printing.components.formselectionmaintenance.IFormSelectionMaintenance;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceInDto;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutDto;


// .//GEN-END:_1_be
//GEN-FIRST:_imports
import java.util.Iterator;
import java.io.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import java.lang.reflect.InvocationTargetException;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.modules.printing.components.formselectionmaintenance.FormSelectionException;
import org.apache.commons.beanutils.BeanUtils;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.modules.printing.components.formselectionmaintenance.IAdditionalData;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.modules.printing.services.FormPrintRequest;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;
import org.jaffa.modules.printing.components.formselectionmaintenance.IAdditionalDataObject;
import org.jaffa.session.ContextManagerFactory;
import javax.servlet.http.HttpServletRequest;
import org.jaffa.presentation.portlet.component.RiaWrapperComponent;
import org.jaffa.security.SecurityManager;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormSelectionMaintenance.
 */
public class FormSelectionMaintenanceComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(FormSelectionMaintenanceComponent.class);
    private String m_event = null;
    private String m_eventDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_key1 = null;
    private String m_key1Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_key2 = null;
    private String m_key2Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_key3 = null;
    private String m_key3Dd = CriteriaField.RELATIONAL_EQUALS;

    private String m_key4 = null;
    private String m_key4Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_key5 = null;
    private String m_key5Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_key6 = null;
    private String m_key6Dd = CriteriaField.RELATIONAL_EQUALS;

    private String m_value1 = null;
    private String m_value1Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_value2 = null;
    private String m_value2Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_value3 = null;
    private String m_value3Dd = CriteriaField.RELATIONAL_EQUALS;

    private String m_value4 = null;
    private String m_value4Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_value5 = null;
    private String m_value5Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_value6 = null;
    private String m_value6Dd = CriteriaField.RELATIONAL_EQUALS;

    private IFormSelectionMaintenance m_tx = null;

    public FormSelectionMaintenanceComponent() {
        super();
        super.setSortDropDown("FormName");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_printing_formSelectionMaintenanceCriteria";
    }

    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_printing_formSelectionMaintenanceResults";
    }

    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_printing_formSelectionMaintenanceConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_printing_formSelectionMaintenanceExcelResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_printing_formSelectionMaintenanceXmlResults";
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

        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:event_1_be

    /** Getter for property event.
     * @return Value of property event.
     */
    public String getEvent() {
        return m_event;
    }

    /** Setter for property event.
     * @param event New value of property event.
     */
    public void setEvent(String event) {
        m_event = event;
    }

    /** Getter for property eventDd.
     * @return Value of property eventDd.
     */
    public String getEventDd() {
        return m_eventDd;
    }

    /** Setter for property eventDd.
     * @param eventDd New value of property eventDd.
     */
    public void setEventDd(String eventDd) {
        m_eventDd = eventDd;
    }

    // .//GEN-END:event_1_be
    // .//GEN-BEGIN:key1_1_be
    /** Getter for property key1.
     * @return Value of property key1.
     */
    public String getKey1() {
        return m_key1;
    }

    /** Setter for property key1.
     * @param key1 New value of property key1.
     */
    public void setKey1(String key1) {
        m_key1 = key1;
    }

    /** Getter for property key1Dd.
     * @return Value of property key1Dd.
     */
    public String getKey1Dd() {
        return m_key1Dd;
    }

    /** Setter for property key1Dd.
     * @param key1Dd New value of property key1Dd.
     */
    public void setKey1Dd(String key1Dd) {
        m_key1Dd = key1Dd;
    }

    // .//GEN-END:key1_1_be
    // .//GEN-BEGIN:key2_1_be
    /** Getter for property key2.
     * @return Value of property key2.
     */
    public String getKey2() {
        return m_key2;
    }

    /** Setter for property key2.
     * @param key2 New value of property key2.
     */
    public void setKey2(String key2) {
        m_key2 = key2;
    }

    /** Getter for property key2Dd.
     * @return Value of property key2Dd.
     */
    public String getKey2Dd() {
        return m_key2Dd;
    }

    /** Setter for property key2Dd.
     * @param key2Dd New value of property key2Dd.
     */
    public void setKey2Dd(String key2Dd) {
        m_key2Dd = key2Dd;
    }

    // .//GEN-END:key2_1_be
    // .//GEN-BEGIN:key3_1_be
    /** Getter for property key3.
     * @return Value of property key3.
     */
    public String getKey3() {
        return m_key3;
    }

    /** Setter for property key3.
     * @param key3 New value of property key3.
     */
    public void setKey3(String key3) {
        m_key3 = key3;
    }

    /** Getter for property key3Dd.
     * @return Value of property key3Dd.
     */
    public String getKey3Dd() {
        return m_key3Dd;
    }

    /** Setter for property key3Dd.
     * @param key3Dd New value of property key3Dd.
     */
    public void setKey3Dd(String key3Dd) {
        m_key3Dd = key3Dd;
    }

        // .//GEN-END:key3_1_be
    // .//GEN-BEGIN:key4_1_be
    /** Getter for property key4.
     * @return Value of property key4.
     */
    public String getKey4() {
        return m_key4;
    }

    /** Setter for property key4.
     * @param key4 New value of property key4.
     */
    public void setKey4(String key4) {
        m_key4 = key4;
    }

    /** Getter for property key4Dd.
     * @return Value of property key4Dd.
     */
    public String getKey4Dd() {
        return m_key4Dd;
    }

    /** Setter for property key4Dd.
     * @param key4Dd New value of property key4Dd.
     */
    public void setKey4Dd(String key4Dd) {
        m_key4Dd = key4Dd;
    }

    // .//GEN-END:key4_1_be
    // .//GEN-BEGIN:key5_1_be
    /** Getter for property key5.
     * @return Value of property key5.
     */
    public String getKey5() {
        return m_key5;
    }

    /** Setter for property key5.
     * @param key5 New value of property key5.
     */
    public void setKey5(String key5) {
        m_key5 = key5;
    }

    /** Getter for property key5Dd.
     * @return Value of property key5Dd.
     */
    public String getKey5Dd() {
        return m_key5Dd;
    }

    /** Setter for property key5Dd.
     * @param key5Dd New value of property key5Dd.
     */
    public void setKey5Dd(String key5Dd) {
        m_key5Dd = key5Dd;
    }

    // .//GEN-END:key5_1_be
    // .//GEN-BEGIN:key6_1_be
    /** Getter for property key6.
     * @return Value of property key6.
     */
    public String getKey6() {
        return m_key6;
    }

    /** Setter for property key6.
     * @param key6 New value of property key6.
     */
    public void setKey6(String key6) {
        m_key6 = key6;
    }

    /** Getter for property key6Dd.
     * @return Value of property key6Dd.
     */
    public String getKey6Dd() {
        return m_key6Dd;
    }

    /** Setter for property key6Dd.
     * @param key6Dd New value of property key6Dd.
     */
    public void setKey6Dd(String key6Dd) {
        m_key6Dd = key6Dd;
    }

    // .//GEN-END:key6_1_be
    // .//GEN-BEGIN:value1_1_be
    /** Getter for property value1.
     * @return Value of property value1.
     */
    public String getValue1() {
        return m_value1;
    }

    /** Setter for property value1.
     * @param value1 New value of property value1.
     */
    public void setValue1(String value1) {
        m_value1 = value1;
    }

    /** Getter for property value1Dd.
     * @return Value of property value1Dd.
     */
    public String getValue1Dd() {
        return m_value1Dd;
    }

    /** Setter for property value1Dd.
     * @param value1Dd New value of property value1Dd.
     */
    public void setValue1Dd(String value1Dd) {
        m_value1Dd = value1Dd;
    }

    // .//GEN-END:value1_1_be
    // .//GEN-BEGIN:value2_1_be
    /** Getter for property value2.
     * @return Value of property value2.
     */
    public String getValue2() {
        return m_value2;
    }

    /** Setter for property value2.
     * @param value2 New value of property value2.
     */
    public void setValue2(String value2) {
        m_value2 = value2;
    }

    /** Getter for property value2Dd.
     * @return Value of property value2Dd.
     */
    public String getValue2Dd() {
        return m_value2Dd;
    }

    /** Setter for property value2Dd.
     * @param value2Dd New value of property value2Dd.
     */
    public void setValue2Dd(String value2Dd) {
        m_value2Dd = value2Dd;
    }

    // .//GEN-END:value2_1_be
    // .//GEN-BEGIN:value3_1_be
    /** Getter for property value3.
     * @return Value of property value3.
     */
    public String getValue3() {
        return m_value3;
    }

    /** Setter for property value3.
     * @param value3 New value of property value3.
     */
    public void setValue3(String value3) {
        m_value3 = value3;
    }

    /** Getter for property value3Dd.
     * @return Value of property value3Dd.
     */
    public String getValue3Dd() {
        return m_value3Dd;
    }

    /** Setter for property value3Dd.
     * @param value3Dd New value of property value3Dd.
     */
    public void setValue3Dd(String value3Dd) {
        m_value3Dd = value3Dd;
    }

    // .//GEN-END:value3_1_be
       // .//GEN-BEGIN:value4_1_be
    /** Getter for property value4.
     * @return Value of property value4.
     */
    public String getValue4() {
        return m_value4;
    }

    /** Setter for property value4.
     * @param value4 New value of property value4.
     */
    public void setValue4(String value4) {
        m_value4 = value4;
    }

    /** Getter for property value4Dd.
     * @return Value of property value4Dd.
     */
    public String getValue4Dd() {
        return m_value4Dd;
    }

    /** Setter for property value4Dd.
     * @param value4Dd New value of property value4Dd.
     */
    public void setValue4Dd(String value4Dd) {
        m_value4Dd = value4Dd;
    }

    // .//GEN-END:value4_1_be
    // .//GEN-BEGIN:value5_1_be
    /** Getter for property value5.
     * @return Value of property value5.
     */
    public String getValue5() {
        return m_value5;
    }

    /** Setter for property value5.
     * @param value5 New value of property value5.
     */
    public void setValue5(String value5) {
        m_value5 = value5;
    }

    /** Getter for property value5Dd.
     * @return Value of property value5Dd.
     */
    public String getValue5Dd() {
        return m_value5Dd;
    }

    /** Setter for property value5Dd.
     * @param value5Dd New value of property value5Dd.
     */
    public void setValue5Dd(String value5Dd) {
        m_value5Dd = value5Dd;
    }

    // .//GEN-END:value5_1_be
    // .//GEN-BEGIN:value6_1_be
    /** Getter for property value6.
     * @return Value of property value6.
     */
    public String getValue6() {
        return m_value6;
    }

    /** Setter for property value6.
     * @param value6 New value of property value6.
     */
    public void setValue6(String value6) {
        m_value6 = value6;
    }

    /** Getter for property value6Dd.
     * @return Value of property value6Dd.
     */
    public String getValue6Dd() {
        return m_value6Dd;
    }

    /** Setter for property value6Dd.
     * @param value6Dd New value of property value6Dd.
     */
    public void setValue6Dd(String value6Dd) {
        m_value6Dd = value6Dd;
    }
    
    // .//GEN-END:value6_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        FormSelectionMaintenanceInDto inputDto = new FormSelectionMaintenanceInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method//GEN-FIRST:_doInquiry_1

        /**
         *  Check that a proper form Event was found.
         *  Give a message that no events are defined for the form preview link.
         *  Conditions are:
         *  getEvent().startsWith("???") indicates the event label was not found.
         *  getEvent().equals("") indicates the event label was found but no event was defined.
         */
        if (getAutoDisplayAll()) {
            if (getEvent() == null || getEvent().startsWith("???") || getEvent().equals("")) {
                throw new ApplicationExceptions(new FormSelectionException(FormSelectionException.FORM_PREVIEW_EVENT_NOT_DEFINED) {
                });
            }
        }
        //Check for Mandatory fields (Event & Key1/Voucher are mandatory)
        if (getEvent() == null) {
            if (appExps == null) {
                appExps = new ApplicationExceptions();
            }
            appExps.add(new MandatoryFieldException("[label.Jaffa.Printing.FormSelection.Event]"));
        }
        if (getKey1() == null) {
            if (appExps == null) {
                appExps = new ApplicationExceptions();
            }
            appExps.add(new MandatoryFieldException("[label.Jaffa.Printing.FormSelection.Key1]"));
        }

        if (appExps != null && appExps.size() > 0) {
            throw appExps;
        }
        inputDto.setDefaultPrinters(getDefaultPrinters());
        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());
        if (getEvent() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getEventDd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getEventDd())) {
            inputDto.setEvent(StringCriteriaField.getStringCriteriaField(getEventDd(), getEvent(), null));
        }

        if (getKey1() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey1Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey1Dd())) {
            inputDto.setKey1(StringCriteriaField.getStringCriteriaField(getKey1Dd(), getKey1(), null));
        }

        if (getKey2() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey2Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey2Dd())) {
            inputDto.setKey2(StringCriteriaField.getStringCriteriaField(getKey2Dd(), getKey2(), null));
        }

        if (getKey3() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey3Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey3Dd())) {
            inputDto.setKey3(StringCriteriaField.getStringCriteriaField(getKey3Dd(), getKey3(), null));
        }

        if (getKey4() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey4Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey4Dd())) {
            inputDto.setKey4(StringCriteriaField.getStringCriteriaField(getKey4Dd(), getKey4(), null));
        }

        if (getKey5() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey5Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey5Dd())) {
            inputDto.setKey5(StringCriteriaField.getStringCriteriaField(getKey5Dd(), getKey5(), null));
        }

        if (getKey6() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getKey6Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getKey6Dd())) {
            inputDto.setKey6(StringCriteriaField.getStringCriteriaField(getKey6Dd(), getKey6(), null));
        }

        if (getValue1() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue1Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue1Dd())) {
            inputDto.setValue1(StringCriteriaField.getStringCriteriaField(getValue1Dd(), getValue1(), null));
        }

        if (getValue2() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue2Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue2Dd())) {
            inputDto.setValue2(StringCriteriaField.getStringCriteriaField(getValue2Dd(), getValue2(), null));
        }

        if (getValue3() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue3Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue3Dd())) {
            inputDto.setValue3(StringCriteriaField.getStringCriteriaField(getValue3Dd(), getValue3(), null));
        }

        if (getValue4() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue4Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue4Dd())) {
            inputDto.setValue4(StringCriteriaField.getStringCriteriaField(getValue4Dd(), getValue4(), null));
        }

        if (getValue5() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue5Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue5Dd())) {
            inputDto.setValue5(StringCriteriaField.getStringCriteriaField(getValue5Dd(), getValue5(), null));
        }

        if (getValue6() != null
                || CriteriaField.RELATIONAL_IS_NULL.equals(getValue6Dd())
                || CriteriaField.RELATIONAL_IS_NOT_NULL.equals(getValue6Dd())) {
            inputDto.setValue6(StringCriteriaField.getStringCriteriaField(getValue6Dd(), getValue6(), null));
        }

        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0) {
            throw appExps;
        }
        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);

        // perform the inquiry
        if (m_tx == null) {
            m_tx = (IFormSelectionMaintenance) Factory.createObject(IFormSelectionMaintenance.class);
        }
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction//GEN-FIRST:_doInquiry_2
        finderOutDto = m_tx.findOutDto(inputDto);

        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
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
    private IUpdateListener m_updateListener = null;
    private String[] m_defaultPrinters = null;

    /** Getter for property defaultPrinters.
     * @return Value of property defaultPrinters.
     */
    public String[] getDefaultPrinters() {
        return m_defaultPrinters;
    }

    /** Setter for property defaultPrinters.
     * @param defaultPrinters New value of property defaultPrinters.
     */
    public void setDefaultPrinters(String[] defaultPrinters) {
        m_defaultPrinters = defaultPrinters;
    }
    
    /**
     * Setter for property setPrinterList.
     * @param printerList Sets the default printers from one printer or a comma-separated list of printers.
     *        Used to set the default printers using a URL post.
     */
    public void setPrinterList(String printerList) {
        String[] printerArray = null;
        if (printerList != null) {
            printerArray = printerList.split(",");
        }
        m_defaultPrinters = printerArray;
    }
    
    /*
     * @return A comma-separated list of default printers.
     */
    public String getPrinterList() {
        String printerList = null;
        if (m_defaultPrinters != null && m_defaultPrinters.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (String printer : m_defaultPrinters) {
                sb.append(",").append(printer);
            }
            printerList = sb.substring(1);
        }
        return printerList;
    }

    public FormKey doFinish(GridModel model) throws ApplicationExceptions, FrameworkException {
        return doPrintForms(model);
    }

    public FormKey doPrintForms(GridModel model) throws ApplicationExceptions, FrameworkException {
        FormKey fk = null;
        GridModelRow row = null;
        ApplicationExceptions appExps = null;
        CheckBoxModel checkBoxModel = null;
        boolean isValidationOk = true;
        boolean isSelected = false;
        try {
            if (model != null && model.getRows() != null && model.getRows().size() > 0) {
                for (Iterator itr = model.getRows().iterator(); itr.hasNext();) {
                    appExps = new ApplicationExceptions();
                    row = (GridModelRow) itr.next();
                    //Field validation
                    checkBoxModel = (CheckBoxModel) row.get("select");
                    if (checkBoxModel.getState()) {
                        isSelected = true;
                    }
                    validateFields(row, appExps, false);
                    row.addElement("errMessage", appExps);
                    if (appExps != null && appExps.size() > 0) {
                        if (isValidationOk) {
                            isValidationOk = false;
                        }
                    }
                }

                if (isSelected && isValidationOk) {
                    String variation = VariationContext.getVariation();
                    for (Iterator itr = model.getRows().iterator(); itr.hasNext();) {
                        try {
                            row = (GridModelRow) itr.next();
                            checkBoxModel = (CheckBoxModel) row.get("select");
                            if (checkBoxModel.getState()) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Just before dispatching Print request");
                                }
                                row.addElement("formVariation", variation);
                                m_tx.dispatchPrintRequest(createPrintRequestObject(row, false));
                                //itr.remove();
                            }
                        } catch (ApplicationExceptions ae) {
                            row.addElement("errMessage", ae);
                            isValidationOk = false;
                        } catch (FrameworkException fe) {
                            row.addElement("errMessage", fe);
                            isValidationOk = false;
                        }
                    }
                }

                if (!isValidationOk) {
                    fk = getResultsFormKey();
                }
            }
        } catch (ApplicationExceptions applicationException) {
            throw applicationException;
        } catch (FrameworkException frameworkException) {
            throw frameworkException;
        }

        return fk;
    }

    protected void validateFields(GridModelRow row, ApplicationExceptions appExps, boolean showForm) throws ApplicationExceptions, FrameworkException {
        String additionalComponent = null;
        CheckBoxModel checkBoxModel = (CheckBoxModel) row.get("select");
        if ((checkBoxModel != null && checkBoxModel.getState()) || showForm) {
            //Check copies are greater then zero
            EditBoxModel mCopies = (EditBoxModel) row.get("copies");
            if (mCopies != null && !showForm) {
                if (mCopies.getValue() != null && mCopies.getValue().length() > 0) {
                    int iCopies = Integer.parseInt(mCopies.getValue());
                    if (iCopies < 1) {
                        appExps.add(new FormSelectionException(FormSelectionException.INVALID_COPIES));
                    }
                } else {
                    appExps.add(new FormSelectionException(FormSelectionException.INVALID_COPIES));
                }
            }
            if (!showForm) {
                //check either printer, email or publish has value
                EditBoxModel mPrinter = (EditBoxModel) row.get("printer");
                EditBoxModel mEmail = (EditBoxModel) row.get("email");
                CheckBoxModel mPublish = (CheckBoxModel) row.get("publish");
                if ((mPrinter.getValue() == null) && (mEmail.getValue() == null) && (mPublish.getState() != true) && !showForm) {
                    appExps.add(new FormSelectionException(FormSelectionException.INVALID_OUTPUT_DESTINATION));
                }
            }
            //Validate AdditionalData exist or not
            try {
                FormSelectionMaintenanceOutRowDto rowDto = (FormSelectionMaintenanceOutRowDto) row.get("object");
                additionalComponent = rowDto.getAdditionalDataComponent();
                if (additionalComponent != null) {
                    Component comp = (Component) run(additionalComponent);
                    BeanUtils.setProperty(comp, getKey1(), getValue1());
                    if (getKey2() != null) {
                        BeanUtils.setProperty(comp, getKey2(), getValue2());
                    }
                    if (getKey3() != null) {
                        BeanUtils.setProperty(comp, getKey3(), getValue3());
                    }
                    if (getKey4() != null) {
                        BeanUtils.setProperty(comp, getKey4(), getValue4());
                    }
                    if (getKey5() != null) {
                        BeanUtils.setProperty(comp, getKey5(), getValue5());
                    }
                    if (getKey6() != null) {
                        BeanUtils.setProperty(comp, getKey6(), getValue6());
                    }
                    if (comp instanceof IAdditionalData) {
                        ((IAdditionalData) comp).validate();
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Additional Data Interface not implemented ");
                        }
                    }
                }
            } catch (SecurityException se) {
                log.error("SecurityException Occurred ", se);
                appExps.add(new FormSelectionException(FormSelectionException.SECURITY_EXCEPTION));
            } catch (IllegalAccessException e) {
                log.error("IllegalAccessException Occurred ", e);
            } catch (InvocationTargetException e) {
                log.error("InvocationTargetException Occurred ", e);
            } catch (IllegalArgumentException e) {
                log.error("IllegalArgumentException Occurred ", e);
            } catch (ApplicationExceptions applicationException) {
                log.debug("Catch : Application Exception from additional data component");
                if (applicationException != null && applicationException.size() > 0) {
                    for (Iterator i = applicationException.iterator(); i.hasNext();) {
                        ApplicationException appEx = (ApplicationException) i.next();
                        appExps.add(appEx);
                    }
                }
            } catch (FrameworkException frameworkException) {
                throw frameworkException;
            }
        }
    }

    /**
     * Show the AdditionalData Component.
     * Pass the Additional Data Object if there is one.
     */
    public FormKey doUpdateDetail(GridModelRow selectedRow, String actionPath) throws ApplicationExceptions, FrameworkException {
        FormKey fk = null;
        String additionalComponent = null;

        try {
            final FormSelectionMaintenanceOutRowDto rowDto = (FormSelectionMaintenanceOutRowDto) selectedRow.get("object");
            additionalComponent = rowDto.getAdditionalDataComponent();
            Component comp = (Component) run(additionalComponent);
            BeanUtils.setProperty(comp, getKey1(), getValue1());
            if (getKey2() != null) {
                BeanUtils.setProperty(comp, getKey2(), getValue2());
            }
            if (getKey3() != null) {
                BeanUtils.setProperty(comp, getKey3(), getValue3());
            }
            if (getKey4() != null) {
                BeanUtils.setProperty(comp, getKey4(), getValue4());
            }
            if (getKey5() != null) {
                BeanUtils.setProperty(comp, getKey5(), getValue5());
            }
            if (getKey6() != null) {
                BeanUtils.setProperty(comp, getKey6(), getValue6());
            }

            // Add a listener and set the additionalDataObject if the additional data component
            // implements IAdditionalDataObject.
            if (comp instanceof IAdditionalDataObject) {
                ((IAdditionalDataObject) comp).setAdditionalDataObject(rowDto.getAdditionalDataObject());
                ((IUpdateComponent) comp).addUpdateListener(
                        new IUpdateListener() {

                            public void updateDone(EventObject source) {
                                try {
                                    rowDto.setAdditionalDataObject(((IAdditionalDataObject) source.getSource()).getAdditionalDataObject());
                                } catch (Exception e) {
                                    log.warn("Error setting the Additional Data Object after the Update", e);
                                }
                            }
                        });
            }

            comp.setReturnToFormKey(getResultsFormKey());
            // Populate info to allow Web 2.0 component to return to this component
            if (comp instanceof RiaWrapperComponent) {
                RiaWrapperComponent c = (RiaWrapperComponent) comp;
                c.getParameters().setProperty("returnToActionPath", actionPath);
                c.getParameters().setProperty("returnToFormKey_componentId", comp.getReturnToFormKey().getComponentId());
            }
            fk = comp.display();
        } catch (SecurityException se) {
            log.error("SecurityException Occurred ", se);
            throw new ApplicationExceptions(new FormSelectionException(FormSelectionException.SECURITY_EXCEPTION));
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException Occurred ", e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException Occurred ", e);
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException Occurred ", e);
        } catch (ApplicationExceptions applicationException) {
            throw applicationException;
        } catch (FrameworkException frameworkException) {
            throw frameworkException;
        }
        return fk;
    }

    public Boolean getDisplayResultsScreen() {
        return Boolean.TRUE;
    }

    private FormPrintRequest createPrintRequestObject(GridModelRow row, boolean showForm) {
        if (log.isDebugEnabled()) {
            log.debug("Create Print Request Object");
        }
        FormSelectionMaintenanceOutRowDto rowDto = (FormSelectionMaintenanceOutRowDto) row.get("object");
        EditBoxModel mPrinter = (EditBoxModel) row.get("printer");
        EditBoxModel mCopies = (EditBoxModel) row.get("copies");
        EditBoxModel mEmail = (EditBoxModel) row.get("email");
        CheckBoxModel mPublish = (CheckBoxModel) row.get("publish");
        String variation = (String) row.get("formVariation");
        FormPrintRequest formPrintRequest = new FormPrintRequest();
        formPrintRequest.setFormAlternateName(rowDto.getFormAlternateName());
        formPrintRequest.setFormName(rowDto.getFormName());

        if (getFormUserId() != null) {
            formPrintRequest.setUserName(getFormUserId());
        } else {
            formPrintRequest.setUserName(getHeaderDto().getUserId());
        }
        formPrintRequest.setVariation(variation);

        // Set the additionalDataObject per row, else use the global object.
        if (rowDto.getAdditionalDataObject() != null) {
            formPrintRequest.setAdditionalDataObject(rowDto.getAdditionalDataObject());
        } else if (getAdditionalDataObject() != null) {
            formPrintRequest.setAdditionalDataObject(getAdditionalDataObject());
        }

        LinkedHashMap keys = new LinkedHashMap();
        keys.put(getKey1(), getValue1());
        if (getKey2() != null) {
            keys.put(getKey2(), getValue2());
        }
        if (getKey3() != null) {
            keys.put(getKey3(), getValue3());
        }
        if (getKey4() != null) {
            keys.put(getKey4(), getValue4());
        }
        if (getKey5() != null) {
            keys.put(getKey5(), getValue5());
        }
        if (getKey6() != null) {
            keys.put(getKey6(), getValue6());
        }
        formPrintRequest.setKeys(keys);

        if (showForm) {
            formPrintRequest.setPrintCopies(new Integer(0));
        } else {
            formPrintRequest.setEmailToAddresses(mEmail.getValue());
            formPrintRequest.setPrintCopies(new Integer(mCopies.getValue()));
            formPrintRequest.setPrinterId(mPrinter.getValue());
        }

        if (log.isDebugEnabled()) {
            log.debug("*** Creating Print Request.  Setting Additional Data Object = " + rowDto.getAdditionalDataObject());
        }

        return formPrintRequest;
    }
    private boolean m_autoDisplayAll = false;
    private boolean m_directDisplay = false;
    private boolean m_printing = true;
    private boolean m_email = true;
    private boolean m_webPublish = false;
    public final String FORM_DIRECT_DISPLAY = "D";
    public final String FORM_PRINTING = "P";
    public final String FORM_EMAIL = "E";
    public final String FORM_WEB_PUBLISH = "W";
    private StringBuilder fileNames = new StringBuilder();
    private Object m_additionalDataObject = null;
    private String m_formUserId = null;

    /** Getter for property autoDisplayAll.
     * @return Value of property autoDisplayAll.
     */
    public boolean getAutoDisplayAll() {
        return m_autoDisplayAll;
    }

    /** Setter for property autoDisplayAll.
     * @param autoDisplayAll New value of property autoDisplayAll.
     */
    public void setAutoDisplayAll(boolean autoDisplayAll) {
        m_autoDisplayAll = autoDisplayAll;
    }

    /** Getter for property directDisplay.
     * @return Value of property directDisplay.
     */
    public boolean getDirectDisplay() {
        return m_directDisplay;
    }

    /** Setter for property directDisplay.
     * @param directDisplay New value of property directDisplay.
     */
    public void setDirectDisplay(boolean directDisplay) {
        m_directDisplay = directDisplay;
    }

    /** Getter for property printing.
     * @return Value of property printing.
     */
    public boolean getPrinting() {
        return m_printing;
    }

    /** Setter for property directDisplay.
     * @param directDisplay New value of property directDisplay.
     */
    public void setPrinting(boolean printing) {
        m_printing = printing;
    }

    /** Getter for property email.
     * @return Value of property email.
     */
    public boolean getEmail() {
        return m_email;
    }

    /** Setter for property email.
     * @param directDisplay New value of property email.
     */
    public void setEmail(boolean email) {
        m_email = email;
    }

    /** Getter for property webPublish.
     * @return Value of property webPublish.
     */
    public boolean getWebPublish() {
        return m_webPublish;
    }

    /** Setter for property webPublish.
     * @param directDisplay New value of property webPublish.
     */
    public void setWebPublish(boolean webPublish) {
        m_webPublish = webPublish;
    }

    /**
     * Getter for property additionalDataObject.
     * @return Value of property additionalDataObject.
     */
    public java.lang.Object getAdditionalDataObject() {
        return m_additionalDataObject;
    }

    public String getFormUserId() {
        return m_formUserId;
    }

    public void setFormUserId(String formUserId) {
        this.m_formUserId = formUserId;
    }

    /**
     * Setter for property additionalDataObject.
     * The additionalDataObject can be set prior to invoking the display() method.
     * It is then set on all rows and passed to the form databean(s).
     */
    public void setAdditionalDataObject(java.lang.Object m_additionalDataObject) {
        this.m_additionalDataObject = m_additionalDataObject;
    }

    /** Following attributes can be set on component invocation to control the types of output allowed
     *  directDisplay (), printing (), email(), webPublish(), autoDisplayAll()
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        FormKey fk = null;
        GridModel rows = new GridModel();
        fileNames = new StringBuilder();
        if (getAutoDisplayAll()) {
            setDirectDisplay(true);
            setPrinting(false);
            setEmail(false);
            setWebPublish(false);
        }

        if (getAutoDisplayAll()) {
            FormSelectionMaintenanceOutDto finderOutDto = (FormSelectionMaintenanceOutDto) doInquiry();
            if (finderOutDto != null && finderOutDto.getRowsCount() > 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Form Preview for event " + getEvent() + ", Key1=" + getKey1() + ", Key2=" + getKey2() + ", Key3=" + getKey3()+ ", Key4=" + getKey4() + ", Key5=" + getKey5() + ", Key6=" + getKey6() + ".  Found " + finderOutDto.getRowsCount() + " rows.");
                }
                for (int i = 0; i < finderOutDto.getRowsCount(); i++) {
                    FormSelectionMaintenanceOutRowDto rowDto = finderOutDto.getRows(i);
                    rowDto.setSelect(Boolean.TRUE);
                    rowDto.setAdditionalDataObject(getAdditionalDataObject());
                    GridModelRow row = rows.newRow();
                    row.addElement("select", new CheckBoxModel((rowDto.getSelect() != null ? rowDto.getSelect() : Boolean.FALSE)));
                    row.addElement("errMessage", rowDto.getErrMessage());
                    row.addElement("formName", rowDto.getFormName());
                    row.addElement("event", rowDto.getEvent());
                    row.addElement("key1", rowDto.getKey1());
                    row.addElement("key2", rowDto.getKey2());
                    row.addElement("key3", rowDto.getKey3());
                    row.addElement("key4", rowDto.getKey4());
                    row.addElement("key5", rowDto.getKey5());
                    row.addElement("key6", rowDto.getKey6());
                    row.addElement("value1", rowDto.getValue1());
                    row.addElement("value2", rowDto.getValue2());
                    row.addElement("value3", rowDto.getValue3());
                    row.addElement("value4", rowDto.getValue4());
                    row.addElement("value5", rowDto.getValue5());
                    row.addElement("value6", rowDto.getValue6());
                    row.addElement("printer", rowDto.getPrinter());
                    row.addElement("email", rowDto.getEmail());
                    row.addElement("publish", new CheckBoxModel((rowDto.getPublish() != null ? rowDto.getPublish() : Boolean.FALSE)));
                    row.addElement("copies", rowDto.getCopies());
                    EditBoxModel printerModel = new EditBoxModel(rowDto.getPrinter(), PrinterDefinitionMeta.META_PRINTER_ID);
                    printerModel.setMandatory(false);
                    row.addElement("printer", printerModel);
                    row.addElement("email", new EditBoxModel(rowDto.getEmail()));
                    row.addElement("copies", new EditBoxModel(rowDto.getCopies()));
                    row.addElement("formType", rowDto.getFormType());
                    row.addElement("object", rowDto);
                    row.addElement("additionalDataComponent", rowDto.getAdditionalDataComponent());
                    fk = doShowForm(row);

                    if (finderOutDto.getRowsCount() == 1 && ((HttpServletRequest) ContextManagerFactory.instance().getProperty("request")).getHeader("user-agent").indexOf("UnixWare") >= 0) {
                        String tmpDir = System.getProperty("java.io.tmpdir");
                        String[] path = getFileNames().split(",");
                        ((HttpServletRequest) ContextManagerFactory.instance().getProperty("request")).setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer", new File(tmpDir, path[0]));
                        fk = new FormKey("jaffa_admin_fileExplorer_download", null);
                    }
                }
            } else {
                throw new ApplicationExceptions(new FormSelectionException(FormSelectionException.NO_FORM_DEFINED_FOR_EVENT, getEvent()) {
                });
            }
        } else {
            fk = super.display();
        }

        return fk;
    }

    /**
     * Determine if there are any forms defined for the given form event and key.
     * Prior to invoking this method, the event and key(s) must be set. At least key1 is mandatory.
     */
    public Boolean isFormConfigured() throws ApplicationExceptions, FrameworkException {
        Boolean formConfigured = null;
        /**
         *  Check that a proper form Event was found.
         *  getEvent().startsWith("???") indicates the event label was not found.
         *  getEvent().equals("") indicates the event label was found but no event was defined.
         */
        if (getEvent() == null || getEvent().startsWith("???") || getEvent().equals("")) {
            formConfigured = Boolean.FALSE;
        } else if (getKey1() == null) {
            formConfigured = Boolean.FALSE;
        } else {
            FormSelectionMaintenanceOutDto finderOutDto = (FormSelectionMaintenanceOutDto) doInquiry();
            if (finderOutDto != null && finderOutDto.getRowsCount() > 0) {
                formConfigured = Boolean.TRUE;
            } else {
                formConfigured = Boolean.FALSE;
            }
        }
        return formConfigured;
    }

    public FormKey getDisplayFormKey() {
        return new FormKey(FormSelectionMaintenanceForm.NAME, getComponentId());
    }

    public FormKey doShowForm(GridModel model) throws ApplicationExceptions, FrameworkException {
        FormKey fk = null;
        boolean isSelected = false;
        GridModelRow row = null;
        ApplicationExceptions appExps = null;
        CheckBoxModel checkBoxModel = null;
        fileNames = new StringBuilder();
        if (model != null && model.getRows() != null && model.getRows().size() > 0) {
            for (Iterator itr = model.getRows().iterator(); itr.hasNext();) {
                row = (GridModelRow) itr.next();
                checkBoxModel = (CheckBoxModel) row.get("select");
                if (checkBoxModel.getState()) {
                    fk = doShowForm(row);
                    isSelected = true;
                }
            }
        }

        if (!isSelected) {
            fk = quitAndReturnToCallingScreen();
        }
        return fk;
    }

    public FormKey doShowForm(GridModelRow selectedRow) throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled()) {
            log.debug("*** Begin doShowForm(row)");
        }

        FormKey fk = null;
        String fileName = "";
        try {
            File fileOut = File.createTempFile("form_", ".pdf");
            fileName = fileOut.getAbsolutePath();
            fileNames.append(fileOut.getName() + ",");
        } catch (IOException io) {
            //FIXME - IOException should not be ignored!
            io.printStackTrace();
        }
        ApplicationExceptions appExps = null;
        boolean isValidationOk = true;
        try {
            appExps = new ApplicationExceptions();
            //Field validation
            validateFields(selectedRow, appExps, true);
            selectedRow.addElement("errMessage", appExps);
            if (appExps != null && appExps.size() > 0) {
                if (isValidationOk) {
                    isValidationOk = false;
                }
            }

            if (isValidationOk) {
                try {
                    selectedRow.addElement("formVariation", VariationContext.getVariation());
                    FormPrintRequest formPrintRequest = createPrintRequestObject(selectedRow, true);
                    formPrintRequest.setSaveFileName(fileName);
                    m_tx.dispatchPrintRequest(formPrintRequest);
                    fk = getDisplayFormKey();
                } catch (ApplicationExceptions ae) {
                    selectedRow.addElement("errMessage", ae);
                    isValidationOk = false;
                    if (getAutoDisplayAll()) {
                        if (log.isDebugEnabled()) {
                            log.debug("*** doShowForm() throwing Application Ex. ");
                        }
                        throw ae;
                    }
                } catch (FrameworkException fe) {
                    selectedRow.addElement("errMessage", fe);
                    isValidationOk = false;
                    if (getAutoDisplayAll()) {
                        if (log.isDebugEnabled()) {
                            log.debug("*** doShowForm() throwing Framework Ex. ");
                        }
                        throw fe;
                    }
                }
            }
            if (!isValidationOk) {
                fk = getResultsFormKey();
            }
        } catch (ApplicationExceptions applicationException) {
            throw applicationException;
        } catch (FrameworkException frameworkException) {
            throw frameworkException;
        }

        if (log.isDebugEnabled()) {
            log.debug("FormKey : " + fk);
            log.debug("*** End doShowForm(row)");
        }
        return fk;
    }

    public String getFileNames() {
        return new String(this.fileNames);
    }

    public void setFileNames(String fileNames) {
        this.fileNames = new StringBuilder(fileNames);
    }

    /**
     * Generates all forms and/or labels for the current Event and Keyset.
     * This method should be used when a User Interface is not needed.
     * Prior to calling this method, set the Event and Keyset using
     * setEvent(), setKey1(), setValue1(), setKey2(), setValue2(),setKey3(), setValue3(),
     * setKey4(), setValue4(), setKey5(), setValue5(), setKey6(), setValue6().
     * The forms and/or labels that match the current
     * Event and Keyset will be printed and/or emailed.
     * @throws ApplicationExceptions - When an exception occurs for a given
     * Form or Label, the exception will be caught and added to the
     * ApplicationExceptions.ApplicationExceptionArray.  Therefore multiple
     * exceptions may be caught and finally thrown at the end of this method.
     * Forms and/or Labels that do not experience an exception will be
     * generated successfully.
     */
    public void print() throws ApplicationExceptions, Exception {
        if (log.isDebugEnabled()) {
            log.debug(" Begin generating forms and labels. ");
            log.debug("Event = " + getEvent() + ".");
            log.debug("Key 1 " + getKey1() + " = " + getValue1() + ".");
            log.debug("Key 2 " + getKey2() + " = " + getValue2() + ".");
            log.debug("Key 3 " + getKey3() + " = " + getValue3() + ".");
            log.debug("Key 4 " + getKey4() + " = " + getValue4() + ".");
            log.debug("Key 5 " + getKey5() + " = " + getValue5() + ".");
            log.debug("Key 6 " + getKey6() + " = " + getValue6() + ".");
        }

        ApplicationExceptions appExps = new ApplicationExceptions();
        GridModel gridModelRows = new GridModel();
        FormSelectionMaintenanceOutDto finderOutDto = null;
        setFormUserId(SecurityManager.getPrincipal() != null ? SecurityManager.getPrincipal().getName() : null);

        // Validate that Event and Key1 are populated at a minimum.
        if (getEvent() == null) {
            throw new ApplicationExceptions(new MandatoryFieldException("[label.Jaffa.Printing.FormSelection.Event]"));
        }
        if (getKey1() == null || getValue1() == null) {
            throw new ApplicationExceptions(new MandatoryFieldException("[label.Jaffa.Printing.FormSelection.Key1]"));
        }

        try {
            finderOutDto = (FormSelectionMaintenanceOutDto) doInquiry2();
        } catch (Exception ex) {
            throw new ApplicationExceptions(new ApplicationException(ex.getMessage()));
        }
        if (finderOutDto != null && finderOutDto.getRowsCount() > 0) {
            EditBoxModel printerModel = null;
            GridModelRow gridModelRow = null;
            FormSelectionMaintenanceOutRowDto[] rows = finderOutDto.getRows();
            for (FormSelectionMaintenanceOutRowDto row : rows) {
                gridModelRow = gridModelRows.newRow();
                gridModelRow.addElement("select", new CheckBoxModel(Boolean.TRUE));
                gridModelRow.addElement("formName", row.getFormName());
                gridModelRow.addElement("formAlternateName", row.getFormAlternateName());
                gridModelRow.addElement("formVariation", row.getFormVariation());
                gridModelRow.addElement("event", row.getEvent());
                gridModelRow.addElement("key1", row.getKey1());
                gridModelRow.addElement("key2", row.getKey2());
                gridModelRow.addElement("key3", row.getKey3());
                gridModelRow.addElement("key4", row.getKey4());
                gridModelRow.addElement("key5", row.getKey5());
                gridModelRow.addElement("key6", row.getKey6());
                gridModelRow.addElement("value1", row.getValue1());
                gridModelRow.addElement("value2", row.getValue2());
                gridModelRow.addElement("value3", row.getValue3());
                gridModelRow.addElement("value4", row.getValue4());
                gridModelRow.addElement("value5", row.getValue5());
                gridModelRow.addElement("value6", row.getValue6());
                printerModel = new EditBoxModel(row.getPrinter(), PrinterDefinitionMeta.META_PRINTER_ID);
                printerModel.setMandatory(false);
                gridModelRow.addElement("printer", printerModel);
                gridModelRow.addElement("email", new EditBoxModel(getEmailAddress()));
                gridModelRow.addElement("publish", new CheckBoxModel(Boolean.FALSE));
                gridModelRow.addElement("copies", new EditBoxModel(row.getCopies()));
                gridModelRow.addElement("object", row);                                                
                try {
                    ApplicationExceptions aes = new ApplicationExceptions();
                    validateFields(gridModelRow, aes, false);
                    if (aes != null && aes.size() > 0) {
                        for (Iterator i = aes.iterator(); i.hasNext();) {
                            ApplicationException appEx = (ApplicationException) i.next();
                            appExps.add(appEx);
                        }
                    } else {
                        m_tx.dispatchPrintRequest(createPrintRequestObject(gridModelRow, false));
                    }
                } catch (Exception ex) {
                    appExps.add(new ApplicationException(ex.getMessage()));
                }
            }
        } else {
            if (appExps.size() == 0) {
                appExps.add(new FormSelectionException(FormSelectionException.NO_FORM_DEFINED_FOR_EVENT, getEvent()));
            }
        }

        if (appExps != null && appExps.size() > 0) {
            throw appExps;
        }

        if (log.isDebugEnabled()) {
            log.debug(" Finished generating forms and labels. ");
        }
    }

    /** This performs the query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    private FinderOutDto doInquiry2() throws ApplicationExceptions, FrameworkException {
        FormSelectionMaintenanceInDto inputDto = new FormSelectionMaintenanceInDto();
        inputDto.setDefaultPrinters(getDefaultPrinters());
        inputDto.setMaxRecords(getMaxRecords());
        if (getEvent() != null) {
            inputDto.setEvent(StringCriteriaField.getStringCriteriaField(getEventDd(), getEvent(), null));
        }
        if (getKey1() != null) {
            inputDto.setKey1(StringCriteriaField.getStringCriteriaField(getKey1Dd(), getKey1(), null));
        }
        if (getKey2() != null) {
            inputDto.setKey2(StringCriteriaField.getStringCriteriaField(getKey2Dd(), getKey2(), null));
        }
        if (getKey3() != null) {
            inputDto.setKey3(StringCriteriaField.getStringCriteriaField(getKey3Dd(), getKey3(), null));
        }
        if (getKey4() != null) {
            inputDto.setKey4(StringCriteriaField.getStringCriteriaField(getKey4Dd(), getKey4(), null));
        }
        if (getKey5() != null) {
            inputDto.setKey5(StringCriteriaField.getStringCriteriaField(getKey5Dd(), getKey5(), null));
        }
        if (getKey6() != null) {
            inputDto.setKey6(StringCriteriaField.getStringCriteriaField(getKey6Dd(), getKey6(), null));
        }
        if (getValue1() != null) {
            inputDto.setValue1(StringCriteriaField.getStringCriteriaField(getValue1Dd(), getValue1(), null));
        }
        if (getValue2() != null) {
            inputDto.setValue2(StringCriteriaField.getStringCriteriaField(getValue2Dd(), getValue2(), null));
        }
        if (getValue3() != null) {
            inputDto.setValue3(StringCriteriaField.getStringCriteriaField(getValue3Dd(), getValue3(), null));
        }
        if (getValue4() != null) {
            inputDto.setValue4(StringCriteriaField.getStringCriteriaField(getValue4Dd(), getValue4(), null));
        }
        if (getValue5() != null) {
            inputDto.setValue5(StringCriteriaField.getStringCriteriaField(getValue5Dd(), getValue5(), null));
        }
        if (getValue6() != null) {
            inputDto.setValue6(StringCriteriaField.getStringCriteriaField(getValue6Dd(), getValue6(), null));
        }
        if (m_tx == null) {
            m_tx = (IFormSelectionMaintenance) Factory.createObject(IFormSelectionMaintenance.class);
        }
        FinderOutDto finderOutDto = m_tx.findOutDto(inputDto);

        return finderOutDto;
    }
    
    private String EmailAddress = null;

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String EmailAddress) {
        this.EmailAddress = EmailAddress;
    }
    //
    // .//GEN-LAST:_custom

    
}
