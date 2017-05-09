// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the PrinterDefinition persistent class.
 */
public class PrinterDefinitionMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.modules.printing.domain.PrinterDefinition";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Printing.PrinterDefinition]";



    // Field constants
    /** A constant to identity the PrinterId field.*/
    public static final String PRINTER_ID = "PrinterId";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the SiteCode field.*/
    public static final String SITE_CODE = "SiteCode";
    /** A constant to identity the LocationCode field.*/
    public static final String LOCATION_CODE = "LocationCode";
    /** A constant to identity the Remote field.*/
    public static final String REMOTE = "Remote";
    /** A constant to identity the RealPrinterName field.*/
    public static final String REAL_PRINTER_NAME = "RealPrinterName";
    /** A constant to identity the PrinterOption1 field.*/
    public static final String PRINTER_OPTION1 = "PrinterOption1";
    /** A constant to identity the PrinterOption2 field.*/
    public static final String PRINTER_OPTION2 = "PrinterOption2";
    /** A constant to identity the OutputType field.*/
    public static final String OUTPUT_TYPE = "OutputType";
    /** A constant to identity the ScaleToPageSize field.*/
    public static final String SCALE_TO_PAGE_SIZE = "ScaleToPageSize";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LastChangedOn field.*/
    public static final String LAST_CHANGED_ON = "LastChangedOn";
    /** A constant to identity the LastChangedBy field.*/
    public static final String LAST_CHANGED_BY = "LastChangedBy";


    // Meta Data Definitions

    /** A constant which holds the meta information for the PrinterId field.*/
    public static final FieldMetaData META_PRINTER_ID = new StringFieldMetaData(PRINTER_ID, "[label.Jaffa.Printing.PrinterDefinition.PrinterId]", Boolean.TRUE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Description field.*/
    public static final FieldMetaData META_DESCRIPTION = new StringFieldMetaData(DESCRIPTION, "[label.Jaffa.Printing.PrinterDefinition.Description]", Boolean.FALSE, null, new Integer(100), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the SiteCode field.*/
    public static final FieldMetaData META_SITE_CODE = new StringFieldMetaData(SITE_CODE, "[label.Jaffa.Printing.PrinterDefinition.SiteCode]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the LocationCode field.*/
    public static final FieldMetaData META_LOCATION_CODE = new StringFieldMetaData(LOCATION_CODE, "[label.Jaffa.Printing.PrinterDefinition.Location]", Boolean.FALSE, null, new Integer(40), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Remote field.*/
    public static final FieldMetaData META_REMOTE = new BooleanFieldMetaData(REMOTE, "[label.Jaffa.Printing.PrinterDefinition.Remote]", Boolean.TRUE, null, null);

    /** A constant which holds the meta information for the RealPrinterName field.*/
    public static final FieldMetaData META_REAL_PRINTER_NAME = new StringFieldMetaData(REAL_PRINTER_NAME, "[label.Jaffa.Printing.PrinterDefinition.RealPrinterName]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the PrinterOption1 field.*/
    public static final FieldMetaData META_PRINTER_OPTION1 = new StringFieldMetaData(PRINTER_OPTION1, "[label.Jaffa.Printing.PrinterDefinition.PrinterOption1]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the PrinterOption2 field.*/
    public static final FieldMetaData META_PRINTER_OPTION2 = new StringFieldMetaData(PRINTER_OPTION2, "[label.Jaffa.Printing.PrinterDefinition.PrinterOption2]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the OutputType field.*/
    public static final FieldMetaData META_OUTPUT_TYPE = new StringFieldMetaData(OUTPUT_TYPE, "[label.Jaffa.Printing.PrinterOutputTypes.OutputType]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the ScaleToPageSize field.*/
    public static final FieldMetaData META_SCALE_TO_PAGE_SIZE = new StringFieldMetaData(SCALE_TO_PAGE_SIZE, "[label.Jaffa.Printing.PrinterDefinition.ScaleToPageSize]", Boolean.FALSE, null, new Integer(30), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Jaffa.Common.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Jaffa.Common.CreatedBy]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the LastChangedOn field.*/
    public static final FieldMetaData META_LAST_CHANGED_ON = new DateTimeFieldMetaData(LAST_CHANGED_ON, "[label.Jaffa.Common.LastChangedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LastChangedBy field.*/
    public static final FieldMetaData META_LAST_CHANGED_BY = new StringFieldMetaData(LAST_CHANGED_BY, "[label.Jaffa.Common.LastChangedBy]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(PRINTER_ID, META_PRINTER_ID);
        m_fieldMap.put(DESCRIPTION, META_DESCRIPTION);
        m_fieldMap.put(SITE_CODE, META_SITE_CODE);
        m_fieldMap.put(LOCATION_CODE, META_LOCATION_CODE);
        m_fieldMap.put(REMOTE, META_REMOTE);
        m_fieldMap.put(REAL_PRINTER_NAME, META_REAL_PRINTER_NAME);
        m_fieldMap.put(PRINTER_OPTION1, META_PRINTER_OPTION1);
        m_fieldMap.put(PRINTER_OPTION2, META_PRINTER_OPTION2);
        m_fieldMap.put(OUTPUT_TYPE, META_OUTPUT_TYPE);
        m_fieldMap.put(SCALE_TO_PAGE_SIZE, META_SCALE_TO_PAGE_SIZE);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LAST_CHANGED_ON, META_LAST_CHANGED_ON);
        m_fieldMap.put(LAST_CHANGED_BY, META_LAST_CHANGED_BY);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_PRINTER_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_PRINTER_ID);
        m_mandatoryFields.add(META_REMOTE);
        m_mandatoryFields.add(META_OUTPUT_TYPE);
    }
    
    
    
    
    
    /** Returns the name of the persistent class.
     * @return the name of the persistent class.
     */
    public static String getName() {
        return m_name;
    }
    
    /** Getter for property labelToken.
     * @return Value of property labelToken.
     */
    public static String getLabelToken() {
        return m_labelToken;
    }
    
    /** This returns an array of all the fields of the persistent class.
     * @return an array of all the fields of the persistent class.
     */
    public static String[] getAttributes() {
        return DomainMetaDataHelper.getAttributes(m_fieldMap);
    }
    
    /** This returns an array of meta information for all the fields of the persistent class.
     * @return an array of meta information for all the fields of the persistent class.
     */
    public static FieldMetaData[] getFieldMetaData() {
        return DomainMetaDataHelper.getFieldMetaData(m_fieldMap);
    }
    
    /** This returns meta information for the input field.
     * @param fieldName the field name.
     * @return meta information for the input field.
     */
    public static FieldMetaData getFieldMetaData(String fieldName) {
        return DomainMetaDataHelper.getFieldMetaData(m_fieldMap, fieldName);
    }

    /** This returns an array of meta information for all the key fields of the persistent class.
     * @return an array of meta information for all the key fields of the persistent class.
     */
    public static FieldMetaData[] getKeyFields() {
        return (FieldMetaData[]) m_keyFields.toArray(new FieldMetaData[0]);
    }
    
    /** This returns an array of meta information for all the mandatory fields of the persistent class.
     * @return an array of meta information for all the mandatory fields of the persistent class.
     */
    public static FieldMetaData[] getMandatoryFields() {
        return (FieldMetaData[]) m_mandatoryFields.toArray(new FieldMetaData[0]);
    }
    
// .//GEN-END:2_be
// All the custom code goes here//GEN-FIRST:custom






// .//GEN-LAST:custom
}

