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
/** This class has the meta information for the AuditTransactionObject persistent class.
 */
public class AuditTransactionObjectMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.audit.domain.AuditTransactionObject";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Audit.AuditTransactionObject]";



    // Field constants
    /** A constant to identity the ObjectId field.*/
    public static final String OBJECT_ID = "ObjectId";
    /** A constant to identity the TransactionId field.*/
    public static final String TRANSACTION_ID = "TransactionId";
    /** A constant to identity the ObjectName field.*/
    public static final String OBJECT_NAME = "ObjectName";
    /** A constant to identity the ChangeType field.*/
    public static final String CHANGE_TYPE = "ChangeType";


    // Meta Data Definitions

    /** A constant which holds the meta information for the ObjectId field.*/
    public static final FieldMetaData META_OBJECT_ID = new StringFieldMetaData(OBJECT_ID, "[label.Jaffa.Audit.AuditTransactionObject.ObjectId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the TransactionId field.*/
    public static final FieldMetaData META_TRANSACTION_ID = new StringFieldMetaData(TRANSACTION_ID, "[label.Jaffa.Audit.AuditTransactionObject.TransactionId]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ObjectName field.*/
    public static final FieldMetaData META_OBJECT_NAME = new StringFieldMetaData(OBJECT_NAME, "[label.Jaffa.Audit.AuditTransactionObject.ObjectName]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ChangeType field.*/
    public static final FieldMetaData META_CHANGE_TYPE = new StringFieldMetaData(CHANGE_TYPE, "[label.Jaffa.Audit.AuditTransactionObject.ChangeType]", Boolean.FALSE, null, new Integer(1), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(OBJECT_ID, META_OBJECT_ID);
        m_fieldMap.put(TRANSACTION_ID, META_TRANSACTION_ID);
        m_fieldMap.put(OBJECT_NAME, META_OBJECT_NAME);
        m_fieldMap.put(CHANGE_TYPE, META_CHANGE_TYPE);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_OBJECT_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_TRANSACTION_ID);
        m_mandatoryFields.add(META_OBJECT_NAME);
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

