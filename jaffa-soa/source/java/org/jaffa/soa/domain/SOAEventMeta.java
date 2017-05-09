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
/** This class has the meta information for the SOAEvent persistent class.
 */
public class SOAEventMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.soa.domain.SOAEvent";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.SOA.SOAEvent]";



    // Field constants
    /** A constant to identity the EventId field.*/
    public static final String EVENT_ID = "EventId";
    /** A constant to identity the EventName field.*/
    public static final String EVENT_NAME = "EventName";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LocalId field.*/
    public static final String LOCAL_ID = "LocalId";


    // Meta Data Definitions

    /** A constant which holds the meta information for the EventId field.*/
    public static final FieldMetaData META_EVENT_ID = new StringFieldMetaData(EVENT_ID, "[label.Jaffa.SOA.SOAEvent.EventId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the EventName field.*/
    public static final FieldMetaData META_EVENT_NAME = new StringFieldMetaData(EVENT_NAME, "[label.Jaffa.SOA.SOAEvent.EventName]", Boolean.TRUE, null, new Integer(100), null);

    /** A constant which holds the meta information for the Description field.*/
    public static final FieldMetaData META_DESCRIPTION = new StringFieldMetaData(DESCRIPTION, "[label.Jaffa.SOA.SOAEvent.Description]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Common.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Common.CreatedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the LocalId field.*/
    public static final FieldMetaData META_LOCAL_ID = new StringFieldMetaData(LOCAL_ID, "[label.Jaffa.SOA.SOAEvent.LocalId]", Boolean.FALSE, null, new Integer(80), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(EVENT_ID, META_EVENT_ID);
        m_fieldMap.put(EVENT_NAME, META_EVENT_NAME);
        m_fieldMap.put(DESCRIPTION, META_DESCRIPTION);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LOCAL_ID, META_LOCAL_ID);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_EVENT_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_EVENT_NAME);
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

