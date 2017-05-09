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
/** This class has the meta information for the Asset persistent class.
 */
public class AssetMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.persistence.domainobjects.Asset";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.App1.Material.Asset]";



    // Field constants
    /** A constant to identity the AssetTk field.*/
    public static final String ASSET_TK = "AssetTk";
    /** A constant to identity the AssetId field.*/
    public static final String ASSET_ID = "AssetId";
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Custodian field.*/
    public static final String CUSTODIAN = "Custodian";
    /** A constant to identity the Condition field.*/
    public static final String CONDITION = "Condition";
    /** A constant to identity the CreatedDatetime field.*/
    public static final String CREATED_DATETIME = "CreatedDatetime";
    /** A constant to identity the Qty field.*/
    public static final String QTY = "Qty";
    /** A constant to identity the KeyRef field.*/
    public static final String KEY_REF = "KeyRef";


    // Meta Data Definitions

    /** A constant which holds the meta information for the AssetTk field.*/
    public static final FieldMetaData META_ASSET_TK = new IntegerFieldMetaData(ASSET_TK, "[label.App1.Material.Asset.AssetTk]", Boolean.FALSE, null, null, null, new Integer(20));

    /** A constant which holds the meta information for the AssetId field.*/
    public static final FieldMetaData META_ASSET_ID = new StringFieldMetaData(ASSET_ID, "[label.App1.Material.Asset.AssetId]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Part field.*/
    public static final FieldMetaData META_PART = new StringFieldMetaData(PART, "[label.App1.Material.Asset.Part]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Custodian field.*/
    public static final FieldMetaData META_CUSTODIAN = new StringFieldMetaData(CUSTODIAN, "[label.App1.Material.Asset.Sc]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the Condition field.*/
    public static final FieldMetaData META_CONDITION = new StringFieldMetaData(CONDITION, "[label.App1.Material.Asset.Condition]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the CreatedDatetime field.*/
    public static final FieldMetaData META_CREATED_DATETIME = new DateTimeFieldMetaData(CREATED_DATETIME, "[label.App1.Material.Asset.CreatedDatetime]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the Qty field.*/
    public static final FieldMetaData META_QTY = new IntegerFieldMetaData(QTY, "[label.App1.Material.Asset.Qty]", Boolean.FALSE, null, null, null, new Integer(8));

    /** A constant which holds the meta information for the KeyRef field.*/
    public static final FieldMetaData META_KEY_REF = new StringFieldMetaData(KEY_REF, "[label.App1.Material.Asset.KeyRef]", Boolean.FALSE, null, new Integer(50), FieldMetaData.UPPER_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ASSET_TK, META_ASSET_TK);
        m_fieldMap.put(ASSET_ID, META_ASSET_ID);
        m_fieldMap.put(PART, META_PART);
        m_fieldMap.put(CUSTODIAN, META_CUSTODIAN);
        m_fieldMap.put(CONDITION, META_CONDITION);
        m_fieldMap.put(CREATED_DATETIME, META_CREATED_DATETIME);
        m_fieldMap.put(QTY, META_QTY);
        m_fieldMap.put(KEY_REF, META_KEY_REF);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_ASSET_TK);
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

