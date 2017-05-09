// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.soa.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the SOAEventParam persistent class.
 */
public class SOAEventParamMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.soa.domain.SOAEventParam";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.SOA.SOAEventParam]";



    // Field constants
    /** A constant to identity the EventId field.*/
    public static final String EVENT_ID = "EventId";
    /** A constant to identity the Name field.*/
    public static final String NAME = "Name";
    /** A constant to identity the Value field.*/
    public static final String VALUE = "Value";


    // Meta Data Definitions

    /** A constant which holds the meta information for the EventId field.*/
    public static final FieldMetaData META_EVENT_ID = new StringFieldMetaData(EVENT_ID, "[label.Jaffa.SOA.SOAEventParam.EventId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Name field.*/
    public static final FieldMetaData META_NAME = new StringFieldMetaData(NAME, "[label.Jaffa.SOA.SOAEventParam.Name]", Boolean.TRUE, null, new Integer(100), null);

    /** A constant which holds the meta information for the Value field.*/
    public static final FieldMetaData META_VALUE = new StringFieldMetaData(VALUE, "[label.Jaffa.SOA.SOAEventParam.Value]", Boolean.FALSE, null, new Integer(100), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(EVENT_ID, META_EVENT_ID);
        m_fieldMap.put(NAME, META_NAME);
        m_fieldMap.put(VALUE, META_VALUE);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_EVENT_ID);
        m_keyFields.add(META_NAME);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_NAME);
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

