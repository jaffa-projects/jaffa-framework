// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.persistence.domainobjects;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the PartAdditional persistent class.
 */
public class PartAdditionalMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.persistence.domainobjects.PartAdditional";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.App1.Catalog.PartAdditional]";



    // Field constants
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Field1 field.*/
    public static final String FIELD1 = "Field1";
    /** A constant to identity the Field2 field.*/
    public static final String FIELD2 = "Field2";
    /** A constant to identity the Field3 field.*/
    public static final String FIELD3 = "Field3";


    // Meta Data Definitions

    /** A constant which holds the meta information for the Part field.*/
    public static final FieldMetaData META_PART = new StringFieldMetaData(PART, "[label.App1.Catalog.PartAdditional.Part]", Boolean.TRUE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Field1 field.*/
    public static final FieldMetaData META_FIELD1 = new StringFieldMetaData(FIELD1, "[label.App1.Catalog.PartAdditional.Field1]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Field2 field.*/
    public static final FieldMetaData META_FIELD2 = new StringFieldMetaData(FIELD2, "[label.App1.Catalog.PartAdditional.Field2]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Field3 field.*/
    public static final FieldMetaData META_FIELD3 = new StringFieldMetaData(FIELD3, "[label.App1.Catalog.PartAdditional.Field3]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(PART, META_PART);
        m_fieldMap.put(FIELD1, META_FIELD1);
        m_fieldMap.put(FIELD2, META_FIELD2);
        m_fieldMap.put(FIELD3, META_FIELD3);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_PART);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_PART);
        m_mandatoryFields.add(META_FIELD1);
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

