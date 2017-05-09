// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.attachment.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the AttachmentDelete persistent class.
 */
public class AttachmentDeleteMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.attachment.domain.AttachmentDelete";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Attachment.AttachmentDelete]";



    // Field constants
    /** A constant to identity the AttachmentId field.*/
    public static final String ATTACHMENT_ID = "AttachmentId";
    /** A constant to identity the SerializedKey field.*/
    public static final String SERIALIZED_KEY = "SerializedKey";
    /** A constant to identity the VersionNumber field.*/
    public static final String VERSION_NUMBER = "VersionNumber";
    /** A constant to identity the DeletionCreatedOn field.*/
    public static final String DELETION_CREATED_ON = "DeletionCreatedOn";


    // Meta Data Definitions

    /** A constant which holds the meta information for the AttachmentId field.*/
    public static final FieldMetaData META_ATTACHMENT_ID = new StringFieldMetaData(ATTACHMENT_ID, "[label.Jaffa.Attachment.AttachmentDelete.AttachmentId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the SerializedKey field.*/
    public static final FieldMetaData META_SERIALIZED_KEY = new StringFieldMetaData(SERIALIZED_KEY, "[label.Jaffa.Attachment.Attachment.SerializedKey]", Boolean.TRUE, null, new Integer(500), null);

    /** A constant which holds the meta information for the VersionNumber field.*/
    public static final FieldMetaData META_VERSION_NUMBER = new IntegerFieldMetaData(VERSION_NUMBER, "[label.Jaffa.Attachment.AttachmentDelete.VersionNumber]", Boolean.FALSE, null, null, null, new Integer(10));

    /** A constant which holds the meta information for the DeletionCreatedOn field.*/
    public static final FieldMetaData META_DELETION_CREATED_ON = new DateTimeFieldMetaData(DELETION_CREATED_ON, "[label.Jaffa.Attachment.AttachmentDelete.DeletionCreatedOn]", Boolean.FALSE, null, null, null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ATTACHMENT_ID, META_ATTACHMENT_ID);
        m_fieldMap.put(SERIALIZED_KEY, META_SERIALIZED_KEY);
        m_fieldMap.put(VERSION_NUMBER, META_VERSION_NUMBER);
        m_fieldMap.put(DELETION_CREATED_ON, META_DELETION_CREATED_ON);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_ATTACHMENT_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_SERIALIZED_KEY);
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

