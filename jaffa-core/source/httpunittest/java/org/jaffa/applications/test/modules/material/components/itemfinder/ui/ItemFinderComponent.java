// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.ui;


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

import org.jaffa.applications.test.modules.material.components.itemfinder.IItemFinder;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderInDto;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutDto;
import org.jaffa.applications.test.modules.material.domain.ItemMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the ItemFinder.
 */
public class ItemFinderComponent extends FinderComponent {

    private static Logger log = Logger.getLogger(ItemFinderComponent.class);

    private String m_segregationCode = null;
    private String m_segregationCodeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_partNo = null;
    private String m_partNoDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_serial = null;
    private String m_serialDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_qty = null;
    private String m_qtyDd = CriteriaField.RELATIONAL_EQUALS;

    private ItemFinderOutDto m_outputDto = null;
    private IItemFinder m_tx = null;

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

        m_outputDto = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:segregationCode_1_be
    /** Getter for property segregationCode.
     * @return Value of property segregationCode.
     */
    public String getSegregationCode() {
        return m_segregationCode;
    }

    /** Setter for property segregationCode.
     * @param segregationCode New value of property segregationCode.
     */
    public void setSegregationCode(String segregationCode) {
        m_segregationCode = segregationCode;
    }

    /** Getter for property segregationCodeDd.
     * @return Value of property segregationCodeDd.
     */
    public String getSegregationCodeDd() {
        return m_segregationCodeDd;
    }

    /** Setter for property segregationCodeDd.
     * @param segregationCodeDd New value of property segregationCodeDd.
     */
    public void setSegregationCodeDd(String segregationCodeDd) {
        m_segregationCodeDd = segregationCodeDd;
    }

    // .//GEN-END:segregationCode_1_be
    // .//GEN-BEGIN:partNo_1_be
    /** Getter for property partNo.
     * @return Value of property partNo.
     */
    public String getPartNo() {
        return m_partNo;
    }

    /** Setter for property partNo.
     * @param partNo New value of property partNo.
     */
    public void setPartNo(String partNo) {
        m_partNo = partNo;
    }

    /** Getter for property partNoDd.
     * @return Value of property partNoDd.
     */
    public String getPartNoDd() {
        return m_partNoDd;
    }

    /** Setter for property partNoDd.
     * @param partNoDd New value of property partNoDd.
     */
    public void setPartNoDd(String partNoDd) {
        m_partNoDd = partNoDd;
    }

    // .//GEN-END:partNo_1_be
    // .//GEN-BEGIN:serial_1_be
    /** Getter for property serial.
     * @return Value of property serial.
     */
    public String getSerial() {
        return m_serial;
    }

    /** Setter for property serial.
     * @param serial New value of property serial.
     */
    public void setSerial(String serial) {
        m_serial = serial;
    }

    /** Getter for property serialDd.
     * @return Value of property serialDd.
     */
    public String getSerialDd() {
        return m_serialDd;
    }

    /** Setter for property serialDd.
     * @param serialDd New value of property serialDd.
     */
    public void setSerialDd(String serialDd) {
        m_serialDd = serialDd;
    }

    // .//GEN-END:serial_1_be
    // .//GEN-BEGIN:qty_1_be
    /** Getter for property qty.
     * @return Value of property qty.
     */
    public String getQty() {
        return m_qty;
    }

    /** Setter for property qty.
     * @param qty New value of property qty.
     */
    public void setQty(String qty) {
        m_qty = qty;
    }

    /** Getter for property qtyDd.
     * @return Value of property qtyDd.
     */
    public String getQtyDd() {
        return m_qtyDd;
    }

    /** Setter for property qtyDd.
     * @param qtyDd New value of property qtyDd.
     */
    public void setQtyDd(String qtyDd) {
        m_qtyDd = qtyDd;
    }

    // .//GEN-END:qty_1_be
    // .//GEN-BEGIN:_ItemFinderOutDto_1_be
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */
    public ItemFinderOutDto getItemFinderOutDto() {
        return m_outputDto;
    }
    // .//GEN-END:_ItemFinderOutDto_1_be
    // .//GEN-BEGIN:_display_1_be
    /** If the displayResultsScreen property has not been set or has been set to false, it will return the FormKey for the Criteria screen.
     * If the displayResultsScreen property has been set to true, then a Search will be performed & the FormKey for the Results screen will be returned.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Criteria screen.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        // .//GEN-END:_display_1_be
        // Add custom code before processing the method //GEN-FIRST:_display_1


        // .//GEN-LAST:_display_1
        // .//GEN-BEGIN:_display_2_be
        if (getDisplayResultsScreen() != null && getDisplayResultsScreen().booleanValue())
            return displayResults();
        else
            return displayCriteria();
    }
    // .//GEN-END:_display_2_be
    // .//GEN-BEGIN:_displayCriteria_1_be
    /** Returns the FormKey for the Criteria screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Criteria screen.
     */
    public FormKey displayCriteria() throws ApplicationExceptions, FrameworkException {
        // .//GEN-END:_displayCriteria_1_be
        // Add custom code before processing the method //GEN-FIRST:_displayCriteria_1


        // .//GEN-LAST:_displayCriteria_1
        // .//GEN-BEGIN:_displayCriteria_2_be
        initCriteriaScreen();
        return new FormKey(ItemFinderCriteriaForm.NAME, getComponentId());
    }
    // .//GEN-END:_displayCriteria_2_be
    // .//GEN-BEGIN:_displayResults_1_be
    /** Performs a search and returns the FormKey for the Results screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey displayResults() throws ApplicationExceptions, FrameworkException {
        // .//GEN-END:_displayResults_1_be
        // Add custom code before processing the method //GEN-FIRST:_displayResults_1


        // .//GEN-LAST:_displayResults_1
        // .//GEN-BEGIN:_displayResults_2_be
        doInquiry();
        return new FormKey(ItemFinderResultsForm.NAME, getComponentId());
    }
    // .//GEN-END:_displayResults_2_be
    // .//GEN-BEGIN:_doInquiry_1_be
    private void doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        ItemFinderInDto inputDto = new ItemFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getSegregationCode() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSegregationCodeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSegregationCodeDd() ) )
            inputDto.setSegregationCode(StringCriteriaField.getStringCriteriaField(getSegregationCodeDd(), getSegregationCode(), null));

        if (getPartNo() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getPartNoDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getPartNoDd() ) )
            inputDto.setPartNo(StringCriteriaField.getStringCriteriaField(getPartNoDd(), getPartNo(), null));

        if (getSerial() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSerialDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSerialDd() ) )
            inputDto.setSerial(StringCriteriaField.getStringCriteriaField(getSerialDd(), getSerial(), null));

        try {
            if (getQty() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getQtyDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getQtyDd() ) )
                inputDto.setQty(DecimalCriteriaField.getDecimalCriteriaField(getQtyDd(), getQty(), null));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            e.setField( ItemMeta.META_QTY.getLabelToken() );
            appExps.add(e);
        }


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        if (getSortDropDown() != null) {
            StringTokenizer str = new StringTokenizer(getSortDropDown(), " ,");
            while (str.hasMoreTokens())
                inputDto.addOrderByFields(new OrderByField( str.nextToken() ));
        }


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IItemFinder) Factory.createObject(IItemFinder.class);
        m_outputDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        invokeFinderListener();
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_initCriteriaScreen_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     */
    private void initCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
