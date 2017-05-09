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
/** This class has the meta information for the AuditTransactionView persistent class.
 */
public class AuditTransactionViewMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.audit.domain.AuditTransactionView";

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
    /** A constant to identity the ObjectId field.*/
    public static final String OBJECT_ID = "ObjectId";
    /** A constant to identity the ObjectName field.*/
    public static final String OBJECT_NAME = "ObjectName";
    /** A constant to identity the ChangeType field.*/
    public static final String CHANGE_TYPE = "ChangeType";
    /** A constant to identity the FieldId field.*/
    public static final String FIELD_ID = "FieldId";
    /** A constant to identity the FieldName field.*/
    public static final String FIELD_NAME = "FieldName";
    /** A constant to identity the FromValue field.*/
    public static final String FROM_VALUE = "FromValue";
    /** A constant to identity the ToValue field.*/
    public static final String TO_VALUE = "ToValue";
    /** A constant to identity the Changed field.*/
    public static final String CHANGED = "Changed";
    /** A constant to identity the Flex field.*/
    public static final String FLEX = "Flex";
    /** A constant to identity the OverflowFlag field.*/
    public static final String OVERFLOW_FLAG = "OverflowFlag";
    /** A constant to identity the MultilineHtmlFlag field.*/
    public static final String MULTILINE_HTML_FLAG = "MultilineHtmlFlag";
    /** A constant to identity the PK field.*/
    public static final String PK = "PK";


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

    /** A constant which holds the meta information for the ObjectId field.*/
    public static final FieldMetaData META_OBJECT_ID = new StringFieldMetaData(OBJECT_ID, "[label.Jaffa.Audit.AuditTransactionObject.ObjectId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ObjectName field.*/
    public static final FieldMetaData META_OBJECT_NAME = new StringFieldMetaData(OBJECT_NAME, "[label.Jaffa.Audit.AuditTransactionObject.ObjectName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ChangeType field.*/
    public static final FieldMetaData META_CHANGE_TYPE = new StringFieldMetaData(CHANGE_TYPE, "[label.Jaffa.Audit.AuditTransactionObject.ChangeType]", Boolean.FALSE, null, new Integer(1), null);

    /** A constant which holds the meta information for the FieldId field.*/
    public static final FieldMetaData META_FIELD_ID = new StringFieldMetaData(FIELD_ID, "[label.Jaffa.Audit.AuditTransactionField.FieldId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the FieldName field.*/
    public static final FieldMetaData META_FIELD_NAME = new StringFieldMetaData(FIELD_NAME, "[label.Jaffa.Audit.AuditTransactionField.FieldName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the FromValue field.*/
    public static final FieldMetaData META_FROM_VALUE = new StringFieldMetaData(FROM_VALUE, "[label.Jaffa.Audit.AuditTransactionField.FromValue]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ToValue field.*/
    public static final FieldMetaData META_TO_VALUE = new StringFieldMetaData(TO_VALUE, "[label.Jaffa.Audit.AuditTransactionField.ToValue]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Changed field.*/
    public static final FieldMetaData META_CHANGED = new BooleanFieldMetaData(CHANGED, "[label.Jaffa.Audit.AuditTransactionField.Changed]", Boolean.FALSE, null, null);

    /** A constant which holds the meta information for the Flex field.*/
    public static final FieldMetaData META_FLEX = new BooleanFieldMetaData(FLEX, "[label.Jaffa.Audit.AuditTransactionField.Flex]", Boolean.FALSE, null, null);

    /** A constant which holds the meta information for the OverflowFlag field.*/
    public static final FieldMetaData META_OVERFLOW_FLAG = new BooleanFieldMetaData(OVERFLOW_FLAG, "[label.Jaffa.Audit.AuditTransactionField.OverflowFlag]", Boolean.FALSE, null, null);

    /** A constant which holds the meta information for the MultilineHtmlFlag field.*/
    public static final FieldMetaData META_MULTILINE_HTML_FLAG = new StringFieldMetaData(MULTILINE_HTML_FLAG, "[label.Jaffa.Audit.AuditTransactionField.MultilineHtmlFlag]", Boolean.FALSE, null, new Integer(1), null);

    /** A constant which holds the meta information for the PK field.*/
    public static final FieldMetaData META_PK = new StringFieldMetaData(PK, "[label.Jaffa.Audit.AuditTransactionField.PK]", Boolean.TRUE, null, new Integer(32), FieldMetaData.UPPER_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(TRANSACTION_ID, META_TRANSACTION_ID);
        m_fieldMap.put(PROCESS_NAME, META_PROCESS_NAME);
        m_fieldMap.put(SUB_PROCESS_NAME, META_SUB_PROCESS_NAME);
        m_fieldMap.put(REASON, META_REASON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(OBJECT_ID, META_OBJECT_ID);
        m_fieldMap.put(OBJECT_NAME, META_OBJECT_NAME);
        m_fieldMap.put(CHANGE_TYPE, META_CHANGE_TYPE);
        m_fieldMap.put(FIELD_ID, META_FIELD_ID);
        m_fieldMap.put(FIELD_NAME, META_FIELD_NAME);
        m_fieldMap.put(FROM_VALUE, META_FROM_VALUE);
        m_fieldMap.put(TO_VALUE, META_TO_VALUE);
        m_fieldMap.put(CHANGED, META_CHANGED);
        m_fieldMap.put(FLEX, META_FLEX);
        m_fieldMap.put(OVERFLOW_FLAG, META_OVERFLOW_FLAG);
        m_fieldMap.put(MULTILINE_HTML_FLAG, META_MULTILINE_HTML_FLAG);
        m_fieldMap.put(PK, META_PK);
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

// .//GEN-END:2_be
// All the custom code goes here//GEN-FIRST:custom






// .//GEN-LAST:custom
}

