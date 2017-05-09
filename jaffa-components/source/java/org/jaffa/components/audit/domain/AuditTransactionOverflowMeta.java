// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.audit.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the AuditTransactionOverflow persistent class.
 */
public class AuditTransactionOverflowMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.audit.domain.AuditTransactionOverflow";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Audit.AuditTransactionOverflow]";



    // Field constants
    /** A constant to identity the FieldId field.*/
    public static final String FIELD_ID = "FieldId";
    /** A constant to identity the ObjectId field.*/
    public static final String OBJECT_ID = "ObjectId";
    /** A constant to identity the FieldName field.*/
    public static final String FIELD_NAME = "FieldName";
    /** A constant to identity the FromValue field.*/
    public static final String FROM_VALUE = "FromValue";
    /** A constant to identity the ToValue field.*/
    public static final String TO_VALUE = "ToValue";


    // Meta Data Definitions

    /** A constant which holds the meta information for the FieldId field.*/
    public static final FieldMetaData META_FIELD_ID = new StringFieldMetaData(FIELD_ID, "[label.Jaffa.Audit.AuditTransactionOverflow.FieldId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ObjectId field.*/
    public static final FieldMetaData META_OBJECT_ID = new StringFieldMetaData(OBJECT_ID, "[label.Jaffa.Audit.AuditTransactionOverflow.ObjectId]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the FieldName field.*/
    public static final FieldMetaData META_FIELD_NAME = new StringFieldMetaData(FIELD_NAME, "[label.Jaffa.Audit.AuditTransactionOverflow.FieldName]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the FromValue field.*/
    public static final FieldMetaData META_FROM_VALUE = new StringFieldMetaData(FROM_VALUE, "[label.Jaffa.Audit.AuditTransactionOverflow.FromValue]", Boolean.FALSE, null, null, FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the ToValue field.*/
    public static final FieldMetaData META_TO_VALUE = new StringFieldMetaData(TO_VALUE, "[label.Jaffa.Audit.AuditTransactionOverflow.ToValue]", Boolean.FALSE, null, null, FieldMetaData.MIXED_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(FIELD_ID, META_FIELD_ID);
        m_fieldMap.put(OBJECT_ID, META_OBJECT_ID);
        m_fieldMap.put(FIELD_NAME, META_FIELD_NAME);
        m_fieldMap.put(FROM_VALUE, META_FROM_VALUE);
        m_fieldMap.put(TO_VALUE, META_TO_VALUE);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_FIELD_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_OBJECT_ID);
        m_mandatoryFields.add(META_FIELD_NAME);
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

