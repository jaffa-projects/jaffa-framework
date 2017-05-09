// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.ria.finder.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the SavedQuery persistent class.
 */
public class SavedQueryMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.ria.finder.domain.SavedQuery";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.SavedQueries.SavedQuery]";



    // Field constants
    /** A constant to identity the QueryId field.*/
    public static final String QUERY_ID = "QueryId";
    /** A constant to identity the UserId field.*/
    public static final String USER_ID = "UserId";
    /** A constant to identity the ComponentRef field.*/
    public static final String COMPONENT_REF = "ComponentRef";
    /** A constant to identity the ContextRef field.*/
    public static final String CONTEXT_REF = "ContextRef";
    /** A constant to identity the QueryName field.*/
    public static final String QUERY_NAME = "QueryName";
    /** A constant to identity the IsDefault field.*/
    public static final String IS_DEFAULT = "IsDefault";
    /** A constant to identity the Criteria field.*/
    public static final String CRITERIA = "Criteria";


    // Meta Data Definitions

    /** A constant which holds the meta information for the QueryId field.*/
    public static final FieldMetaData META_QUERY_ID = new StringFieldMetaData(QUERY_ID, "[label.Jaffa.SavedQueries.SavedQuery.QueryId]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the UserId field.*/
    public static final FieldMetaData META_USER_ID = new StringFieldMetaData(USER_ID, "[label.Jaffa.SavedQueries.SavedQuery.UserId]", Boolean.TRUE, null, new Integer(20), null);

    /** A constant which holds the meta information for the ComponentRef field.*/
    public static final FieldMetaData META_COMPONENT_REF = new StringFieldMetaData(COMPONENT_REF, "[label.Jaffa.SavedQueries.SavedQuery.ComponentRef]", Boolean.TRUE, null, new Integer(100), null);

    /** A constant which holds the meta information for the ContextRef field.*/
    public static final FieldMetaData META_CONTEXT_REF = new StringFieldMetaData(CONTEXT_REF, "[label.Jaffa.SavedQueries.SavedQuery.ContextRef]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the QueryName field.*/
    public static final FieldMetaData META_QUERY_NAME = new StringFieldMetaData(QUERY_NAME, "[label.Jaffa.SavedQueries.SavedQuery.QueryName]", Boolean.TRUE, null, new Integer(100), null);

    /** A constant which holds the meta information for the IsDefault field.*/
    public static final FieldMetaData META_IS_DEFAULT = new BooleanFieldMetaData(IS_DEFAULT, "[label.Jaffa.SavedQueries.SavedQuery.IsDefault]", Boolean.FALSE, null, null);

    /** A constant which holds the meta information for the Criteria field.*/
    public static final FieldMetaData META_CRITERIA = new StringFieldMetaData(CRITERIA, "[label.Jaffa.SavedQueries.SavedQuery.Criteria]", Boolean.FALSE, null, null, null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(QUERY_ID, META_QUERY_ID);
        m_fieldMap.put(USER_ID, META_USER_ID);
        m_fieldMap.put(COMPONENT_REF, META_COMPONENT_REF);
        m_fieldMap.put(CONTEXT_REF, META_CONTEXT_REF);
        m_fieldMap.put(QUERY_NAME, META_QUERY_NAME);
        m_fieldMap.put(IS_DEFAULT, META_IS_DEFAULT);
        m_fieldMap.put(CRITERIA, META_CRITERIA);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_QUERY_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_USER_ID);
        m_mandatoryFields.add(META_COMPONENT_REF);
        m_mandatoryFields.add(META_QUERY_NAME);
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

