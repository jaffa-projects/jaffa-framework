// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.material.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the Item persistent class.
 */
public class ItemMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.applications.test.modules.material.domain.Item";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Test.Material.Item]";



    // Field constants
    /** A constant to identity the ItemId field.*/
    public static final String ITEM_ID = "ItemId";
    /** A constant to identity the Sc field.*/
    public static final String SC = "Sc";
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Serial field.*/
    public static final String SERIAL = "Serial";
    /** A constant to identity the Qty field.*/
    public static final String QTY = "Qty";


    // Meta Data Definitions

    /** A constant which holds the meta information for the ItemId field.*/
    public static final FieldMetaData META_ITEM_ID = new StringFieldMetaData(ITEM_ID, "[label.Test.Material.Item.ItemId]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Sc field.*/
    public static final FieldMetaData META_SC = new StringFieldMetaData(SC, "[label.Test.Material.Item.Sc]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Part field.*/
    public static final FieldMetaData META_PART = new StringFieldMetaData(PART, "[label.Test.Material.Item.Part]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Serial field.*/
    public static final FieldMetaData META_SERIAL = new StringFieldMetaData(SERIAL, "[label.Test.Material.Item.Serial]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Qty field.*/
    public static final FieldMetaData META_QTY = new DecimalFieldMetaData(QTY, "[label.Test.Material.Item.Qty]", Boolean.FALSE, null, null, null, new Integer(10), new Integer(5));



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ITEM_ID, META_ITEM_ID);
        m_fieldMap.put(SC, META_SC);
        m_fieldMap.put(PART, META_PART);
        m_fieldMap.put(SERIAL, META_SERIAL);
        m_fieldMap.put(QTY, META_QTY);
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

