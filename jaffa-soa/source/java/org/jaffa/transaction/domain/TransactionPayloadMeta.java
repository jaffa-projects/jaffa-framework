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
/** This class has the meta information for the TransactionPayload persistent class.
 */
public class TransactionPayloadMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.transaction.domain.TransactionPayload";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Transaction.TransactionPayload]";



    // Field constants
    /** A constant to identity the Id field.*/
    public static final String ID = "Id";
    /** A constant to identity the ExternalMessage field.*/
    public static final String EXTERNAL_MESSAGE = "ExternalMessage";
    /** A constant to identity the InternalMessage field.*/
    public static final String INTERNAL_MESSAGE = "InternalMessage";
    /** A constant to identity the InternalMessageClass field.*/
    public static final String INTERNAL_MESSAGE_CLASS = "InternalMessageClass";


    // Meta Data Definitions

    /** A constant which holds the meta information for the Id field.*/
    public static final FieldMetaData META_ID = new StringFieldMetaData(ID, "[label.Jaffa.Transaction.TransactionPayload.Id]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ExternalMessage field.*/
    public static final FieldMetaData META_EXTERNAL_MESSAGE = new RawFieldMetaData(EXTERNAL_MESSAGE, "[label.Jaffa.Transaction.TransactionPayload.ExternalMessage]", Boolean.FALSE);

    /** A constant which holds the meta information for the InternalMessage field.*/
    public static final FieldMetaData META_INTERNAL_MESSAGE = new RawFieldMetaData(INTERNAL_MESSAGE, "[label.Jaffa.Transaction.TransactionPayload.InternalMessage]", Boolean.FALSE);

    /** A constant which holds the meta information for the InternalMessageClass field.*/
    public static final FieldMetaData META_INTERNAL_MESSAGE_CLASS = new StringFieldMetaData(INTERNAL_MESSAGE_CLASS, "[label.Jaffa.Transaction.TransactionPayload.InternalMessageClass]", Boolean.FALSE, null, new Integer(2000), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ID, META_ID);
        m_fieldMap.put(EXTERNAL_MESSAGE, META_EXTERNAL_MESSAGE);
        m_fieldMap.put(INTERNAL_MESSAGE, META_INTERNAL_MESSAGE);
        m_fieldMap.put(INTERNAL_MESSAGE_CLASS, META_INTERNAL_MESSAGE_CLASS);
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

