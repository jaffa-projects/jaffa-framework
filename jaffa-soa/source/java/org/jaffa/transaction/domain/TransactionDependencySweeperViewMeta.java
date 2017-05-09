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
/** This class has the meta information for the TransactionDependencySweeperView persistent class.
 */
public class TransactionDependencySweeperViewMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.transaction.domain.TransactionDependencySweeperView";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Transaction.TransactionDependency]";



    // Field constants
    /** A constant to identity the TransactionId field.*/
    public static final String TRANSACTION_ID = "TransactionId";
    /** A constant to identity the DependsOnId field.*/
    public static final String DEPENDS_ON_ID = "DependsOnId";
    /** A constant to identity the Status field.*/
    public static final String STATUS = "Status";


    // Meta Data Definitions

    /** A constant which holds the meta information for the TransactionId field.*/
    public static final FieldMetaData META_TRANSACTION_ID = new StringFieldMetaData(TRANSACTION_ID, "[label.Jaffa.Transaction.TransactionDependency.TransactionId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the DependsOnId field.*/
    public static final FieldMetaData META_DEPENDS_ON_ID = new StringFieldMetaData(DEPENDS_ON_ID, "[label.Jaffa.Transaction.TransactionDependency.DependsOnId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Status field.*/
    public static final FieldMetaData META_STATUS = new StringFieldMetaData(STATUS, "[label.Jaffa.Transaction.TransactionDependency.Status]", Boolean.FALSE, null, new Integer(20), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(TRANSACTION_ID, META_TRANSACTION_ID);
        m_fieldMap.put(DEPENDS_ON_ID, META_DEPENDS_ON_ID);
        m_fieldMap.put(STATUS, META_STATUS);
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

