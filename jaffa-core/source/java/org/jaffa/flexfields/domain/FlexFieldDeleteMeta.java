// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.flexfields.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the FlexFieldDelete persistent class.
 */
public class FlexFieldDeleteMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.flexfields.domain.FlexFieldDelete";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.FlexFields.FlexFieldDelete]";



    // Field constants
    /** A constant to identity the FlexFieldId field.*/
    public static final String FLEX_FIELD_ID = "FlexFieldId";
    /** A constant to identity the ObjectName field.*/
    public static final String OBJECT_NAME = "ObjectName";
    /** A constant to identity the Key1 field.*/
    public static final String KEY1 = "Key1";
    /** A constant to identity the VersionNumber field.*/
    public static final String VERSION_NUMBER = "VersionNumber";
    /** A constant to identity the DeletionCreatedOn field.*/
    public static final String DELETION_CREATED_ON = "DeletionCreatedOn";


    // Meta Data Definitions

    /** A constant which holds the meta information for the FlexFieldId field.*/
    public static final FieldMetaData META_FLEX_FIELD_ID = new StringFieldMetaData(FLEX_FIELD_ID, "[label.Jaffa.FlexFields.FlexFieldDelete.FlexFieldId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the ObjectName field.*/
    public static final FieldMetaData META_OBJECT_NAME = new StringFieldMetaData(OBJECT_NAME, "[label.Jaffa.FlexFields.FlexField.ObjectName]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the Key1 field.*/
    public static final FieldMetaData META_KEY1 = new StringFieldMetaData(KEY1, "[label.Jaffa.FlexFields.FlexField.Key1]", Boolean.TRUE, null, new Integer(80), null);

    /** A constant which holds the meta information for the VersionNumber field.*/
    public static final FieldMetaData META_VERSION_NUMBER = new IntegerFieldMetaData(VERSION_NUMBER, "[label.Jaffa.FlexFields.FlexFieldDelete.VersionNumber]", Boolean.FALSE, null, null, null, new Integer(10));

    /** A constant which holds the meta information for the DeletionCreatedOn field.*/
    public static final FieldMetaData META_DELETION_CREATED_ON = new DateTimeFieldMetaData(DELETION_CREATED_ON, "[label.Jaffa.FlexFields.FlexFieldDelete.DeletionCreatedOn]", Boolean.FALSE, null, null, null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(FLEX_FIELD_ID, META_FLEX_FIELD_ID);
        m_fieldMap.put(OBJECT_NAME, META_OBJECT_NAME);
        m_fieldMap.put(KEY1, META_KEY1);
        m_fieldMap.put(VERSION_NUMBER, META_VERSION_NUMBER);
        m_fieldMap.put(DELETION_CREATED_ON, META_DELETION_CREATED_ON);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_FLEX_FIELD_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_OBJECT_NAME);
        m_mandatoryFields.add(META_KEY1);
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

