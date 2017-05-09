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
/** This class has the meta information for the Item persistent class.
 */
public class ItemMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.persistence.domainobjects.Item";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.App1.Material.Item]";



    // Field constants
    /** A constant to identity the ItemId field.*/
    public static final String ITEM_ID = "ItemId";
    /** A constant to identity the ReceivedItemId field.*/
    public static final String RECEIVED_ITEM_ID = "ReceivedItemId";
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Prime field.*/
    public static final String PRIME = "Prime";
    /** A constant to identity the Sc field.*/
    public static final String SC = "Sc";
    /** A constant to identity the Condition field.*/
    public static final String CONDITION = "Condition";
    /** A constant to identity the CreatedDatetime field.*/
    public static final String CREATED_DATETIME = "CreatedDatetime";
    /** A constant to identity the Qty field.*/
    public static final String QTY = "Qty";
    /** A constant to identity the KeyRef field.*/
    public static final String KEY_REF = "KeyRef";
    /** A constant to identity the Status1 field.*/
    public static final String STATUS1 = "Status1";
    /** A constant to identity the Status2 field.*/
    public static final String STATUS2 = "Status2";
    /** A constant to identity the Status3 field.*/
    public static final String STATUS3 = "Status3";
    /** A constant to identity the Price field.*/
    public static final String PRICE = "Price";


    // Meta Data Definitions

    /** A constant which holds the meta information for the ItemId field.*/
    public static final FieldMetaData META_ITEM_ID = new StringFieldMetaData(ITEM_ID, "[label.App1.Material.Item.ItemId]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the ReceivedItemId field.*/
    public static final FieldMetaData META_RECEIVED_ITEM_ID = new StringFieldMetaData(RECEIVED_ITEM_ID, "[label.App1.Material.Item.ReceivedItemId]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Part field.*/
    public static final FieldMetaData META_PART = new StringFieldMetaData(PART, "[label.App1.Material.Item.Part]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Prime field.*/
    public static final FieldMetaData META_PRIME = new StringFieldMetaData(PRIME, "[label.App1.Material.Item.Prime]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Sc field.*/
    public static final FieldMetaData META_SC = new StringFieldMetaData(SC, "[label.App1.Material.Item.Sc]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Condition field.*/
    public static final FieldMetaData META_CONDITION = new StringFieldMetaData(CONDITION, "[label.App1.Material.Item.Condition]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the CreatedDatetime field.*/
    public static final FieldMetaData META_CREATED_DATETIME = new DateTimeFieldMetaData(CREATED_DATETIME, "[label.App1.Material.Item.CreatedDatetime]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the Qty field.*/
    public static final FieldMetaData META_QTY = new IntegerFieldMetaData(QTY, "[label.App1.Material.Item.Qty]", Boolean.FALSE, null, null, null, new Integer(8));

    /** A constant which holds the meta information for the KeyRef field.*/
    public static final FieldMetaData META_KEY_REF = new StringFieldMetaData(KEY_REF, "[label.App1.Material.Item.KeyRef]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Status1 field.*/
    public static final FieldMetaData META_STATUS1 = new StringFieldMetaData(STATUS1, "[label.App1.Material.Item.Status1]", Boolean.FALSE, null, new Integer(1), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Status2 field.*/
    public static final FieldMetaData META_STATUS2 = new StringFieldMetaData(STATUS2, "[label.App1.Material.Item.Status2]", Boolean.FALSE, null, new Integer(1), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Status3 field.*/
    public static final FieldMetaData META_STATUS3 = new StringFieldMetaData(STATUS3, "[label.App1.Material.Item.Status3]", Boolean.FALSE, null, new Integer(1), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Price field.*/
    public static final FieldMetaData META_PRICE = new DecimalFieldMetaData(PRICE, "[label.App1.Material.Item.Price]", Boolean.FALSE, null, null, null, new Integer(10), new Integer(5));



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ITEM_ID, META_ITEM_ID);
        m_fieldMap.put(RECEIVED_ITEM_ID, META_RECEIVED_ITEM_ID);
        m_fieldMap.put(PART, META_PART);
        m_fieldMap.put(PRIME, META_PRIME);
        m_fieldMap.put(SC, META_SC);
        m_fieldMap.put(CONDITION, META_CONDITION);
        m_fieldMap.put(CREATED_DATETIME, META_CREATED_DATETIME);
        m_fieldMap.put(QTY, META_QTY);
        m_fieldMap.put(KEY_REF, META_KEY_REF);
        m_fieldMap.put(STATUS1, META_STATUS1);
        m_fieldMap.put(STATUS2, META_STATUS2);
        m_fieldMap.put(STATUS3, META_STATUS3);
        m_fieldMap.put(PRICE, META_PRICE);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_ITEM_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_ITEM_ID);
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

