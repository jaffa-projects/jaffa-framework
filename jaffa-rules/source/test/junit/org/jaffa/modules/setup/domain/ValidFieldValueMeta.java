// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.setup.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the ValidFieldValue persistent class.
 */
public class ValidFieldValueMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.modules.setup.domain.ValidFieldValue";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Setup.ValidFieldValue]";



    // Field constants
    /** A constant to identity the TableName field.*/
    public static final String TABLE_NAME = "TableName";
    /** A constant to identity the FieldName field.*/
    public static final String FIELD_NAME = "FieldName";
    /** A constant to identity the LegalValue field.*/
    public static final String LEGAL_VALUE = "LegalValue";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the Remarks field.*/
    public static final String REMARKS = "Remarks";


    // Meta Data Definitions

    /** A constant which holds the meta information for the TableName field.*/
    public static final FieldMetaData META_TABLE_NAME = new StringFieldMetaData(TABLE_NAME, "[label.Jaffa.Setup.ValidFieldValue.TableName]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the FieldName field.*/
    public static final FieldMetaData META_FIELD_NAME = new StringFieldMetaData(FIELD_NAME, "[label.Jaffa.Setup.ValidFieldValue.FieldName]", Boolean.TRUE, null, new Integer(30), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the LegalValue field.*/
    public static final FieldMetaData META_LEGAL_VALUE = new StringFieldMetaData(LEGAL_VALUE, "[label.Jaffa.Setup.ValidFieldValue.LegalValue]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Description field.*/
    public static final FieldMetaData META_DESCRIPTION = new StringFieldMetaData(DESCRIPTION, "[label.Jaffa.Setup.ValidFieldValue.Description]", Boolean.FALSE, null, new Integer(40), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Remarks field.*/
    public static final FieldMetaData META_REMARKS = new StringFieldMetaData(REMARKS, "[label.Jaffa.Setup.ValidFieldValue.Remarks]", Boolean.FALSE, null, new Integer(250), FieldMetaData.MIXED_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(TABLE_NAME, META_TABLE_NAME);
        m_fieldMap.put(FIELD_NAME, META_FIELD_NAME);
        m_fieldMap.put(LEGAL_VALUE, META_LEGAL_VALUE);
        m_fieldMap.put(DESCRIPTION, META_DESCRIPTION);
        m_fieldMap.put(REMARKS, META_REMARKS);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_TABLE_NAME);
        m_keyFields.add(META_FIELD_NAME);
        m_keyFields.add(META_LEGAL_VALUE);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_TABLE_NAME);
        m_mandatoryFields.add(META_FIELD_NAME);
        m_mandatoryFields.add(META_LEGAL_VALUE);
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

