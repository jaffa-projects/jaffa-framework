// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.transaction.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the Transaction persistent class.
 */
public class TransactionMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.transaction.domain.Transaction";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Transaction.Transaction]";



    // Field constants
    /** A constant to identity the Id field.*/
    public static final String ID = "Id";
    /** A constant to identity the Direction field.*/
    public static final String DIRECTION = "Direction";
    /** A constant to identity the Type field.*/
    public static final String TYPE = "Type";
    /** A constant to identity the SubType field.*/
    public static final String SUB_TYPE = "SubType";
    /** A constant to identity the Status field.*/
    public static final String STATUS = "Status";
    /** A constant to identity the ErrorMessage field.*/
    public static final String ERROR_MESSAGE = "ErrorMessage";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LastChangedOn field.*/
    public static final String LAST_CHANGED_ON = "LastChangedOn";
    /** A constant to identity the LastChangedBy field.*/
    public static final String LAST_CHANGED_BY = "LastChangedBy";


    // Meta Data Definitions

    /** A constant which holds the meta information for the Id field.*/
    public static final FieldMetaData META_ID = new StringFieldMetaData(ID, "[label.Jaffa.Transaction.Transaction.Id]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Direction field.*/
    public static final FieldMetaData META_DIRECTION = new StringFieldMetaData(DIRECTION, "[label.Jaffa.Transaction.Transaction.Direction]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the Type field.*/
    public static final FieldMetaData META_TYPE = new StringFieldMetaData(TYPE, "[label.Jaffa.Transaction.Transaction.Type]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the SubType field.*/
    public static final FieldMetaData META_SUB_TYPE = new StringFieldMetaData(SUB_TYPE, "[label.Jaffa.Transaction.Transaction.SubType]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the Status field.*/
    public static final FieldMetaData META_STATUS = new StringFieldMetaData(STATUS, "[label.Jaffa.Transaction.Transaction.Status]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the ErrorMessage field.*/
    public static final FieldMetaData META_ERROR_MESSAGE = new StringFieldMetaData(ERROR_MESSAGE, "[label.Jaffa.Transaction.Transaction.ErrorMessage]", Boolean.FALSE, null, new Integer(2000), null);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Common.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Common.CreatedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the LastChangedOn field.*/
    public static final FieldMetaData META_LAST_CHANGED_ON = new DateTimeFieldMetaData(LAST_CHANGED_ON, "[label.Common.LastChangedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LastChangedBy field.*/
    public static final FieldMetaData META_LAST_CHANGED_BY = new StringFieldMetaData(LAST_CHANGED_BY, "[label.Common.LastChangedBy]", Boolean.FALSE, null, new Integer(20), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ID, META_ID);
        m_fieldMap.put(DIRECTION, META_DIRECTION);
        m_fieldMap.put(TYPE, META_TYPE);
        m_fieldMap.put(SUB_TYPE, META_SUB_TYPE);
        m_fieldMap.put(STATUS, META_STATUS);
        m_fieldMap.put(ERROR_MESSAGE, META_ERROR_MESSAGE);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LAST_CHANGED_ON, META_LAST_CHANGED_ON);
        m_fieldMap.put(LAST_CHANGED_BY, META_LAST_CHANGED_BY);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_ID);
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

