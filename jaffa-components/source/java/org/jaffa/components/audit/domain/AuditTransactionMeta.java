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
/** This class has the meta information for the AuditTransaction persistent class.
 */
public class AuditTransactionMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.audit.domain.AuditTransaction";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Audit.AuditTransaction]";



    // Field constants
    /** A constant to identity the TransactionId field.*/
    public static final String TRANSACTION_ID = "TransactionId";
    /** A constant to identity the ProcessName field.*/
    public static final String PROCESS_NAME = "ProcessName";
    /** A constant to identity the SubProcessName field.*/
    public static final String SUB_PROCESS_NAME = "SubProcessName";
    /** A constant to identity the Reason field.*/
    public static final String REASON = "Reason";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";


    // Meta Data Definitions

    /** A constant which holds the meta information for the TransactionId field.*/
    public static final FieldMetaData META_TRANSACTION_ID = new StringFieldMetaData(TRANSACTION_ID, "[label.Jaffa.Audit.AuditTransaction.TransactionId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ProcessName field.*/
    public static final FieldMetaData META_PROCESS_NAME = new StringFieldMetaData(PROCESS_NAME, "[label.Jaffa.Audit.AuditTransaction.ProcessName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the SubProcessName field.*/
    public static final FieldMetaData META_SUB_PROCESS_NAME = new StringFieldMetaData(SUB_PROCESS_NAME, "[label.Jaffa.Audit.AuditTransaction.SubProcessName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Reason field.*/
    public static final FieldMetaData META_REASON = new StringFieldMetaData(REASON, "[label.Jaffa.Audit.AuditTransaction.Reason]", Boolean.FALSE, null, new Integer(250), null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Jaffa.Audit.AuditTransaction.CreatedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Jaffa.Audit.AuditTransaction.CreatedOn]", Boolean.FALSE, null, null, null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(TRANSACTION_ID, META_TRANSACTION_ID);
        m_fieldMap.put(PROCESS_NAME, META_PROCESS_NAME);
        m_fieldMap.put(SUB_PROCESS_NAME, META_SUB_PROCESS_NAME);
        m_fieldMap.put(REASON, META_REASON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_TRANSACTION_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
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

