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
/** This class has the meta information for the Attachment persistent class.
 */
public class AttachmentMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.components.attachment.domain.Attachment";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Attachment.Attachment]";



    // Field constants
    /** A constant to identity the AttachmentId field.*/
    public static final String ATTACHMENT_ID = "AttachmentId";
    /** A constant to identity the DocumentReferenceId field.*/
    public static final String DOCUMENT_REFERENCE_ID = "DocumentReferenceId";
    /** A constant to identity the SerializedKey field.*/
    public static final String SERIALIZED_KEY = "SerializedKey";
    /** A constant to identity the OriginalFileName field.*/
    public static final String ORIGINAL_FILE_NAME = "OriginalFileName";
    /** A constant to identity the AttachmentType field.*/
    public static final String ATTACHMENT_TYPE = "AttachmentType";
    /** A constant to identity the ContentType field.*/
    public static final String CONTENT_TYPE = "ContentType";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the Remarks field.*/
    public static final String REMARKS = "Remarks";
    /** A constant to identity the SupercededBy field.*/
    public static final String SUPERCEDED_BY = "SupercededBy";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LastChangedOn field.*/
    public static final String LAST_CHANGED_ON = "LastChangedOn";
    /** A constant to identity the LastChangedBy field.*/
    public static final String LAST_CHANGED_BY = "LastChangedBy";
    /** A constant to identity the Data field.*/
    public static final String DATA = "Data";
    /** A constant to identity the VersionNumber field.*/
    public static final String VERSION_NUMBER = "VersionNumber";


    // Meta Data Definitions

    /** A constant which holds the meta information for the AttachmentId field.*/
    public static final FieldMetaData META_ATTACHMENT_ID = new StringFieldMetaData(ATTACHMENT_ID, "[label.Jaffa.Attachment.Attachment.AttachmentId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the DocumentReferenceId field.*/
    public static final FieldMetaData META_DOCUMENT_REFERENCE_ID = new StringFieldMetaData(DOCUMENT_REFERENCE_ID, "[label.Jaffa.Attachment.Attachment.DocumentReferenceId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the SerializedKey field.*/
    public static final FieldMetaData META_SERIALIZED_KEY = new StringFieldMetaData(SERIALIZED_KEY, "[label.Jaffa.Attachment.Attachment.SerializedKey]", Boolean.TRUE, null, new Integer(500), null);

    /** A constant which holds the meta information for the OriginalFileName field.*/
    public static final FieldMetaData META_ORIGINAL_FILE_NAME = new StringFieldMetaData(ORIGINAL_FILE_NAME, "[label.Jaffa.Attachment.Attachment.OriginalFileName]", Boolean.FALSE, null, new Integer(255), null);

    /** A constant which holds the meta information for the AttachmentType field.*/
    public static final FieldMetaData META_ATTACHMENT_TYPE = new StringFieldMetaData(ATTACHMENT_TYPE, "[label.Jaffa.Attachment.Attachment.AttachmentType]", Boolean.FALSE, null, new Integer(1), null);

    /** A constant which holds the meta information for the ContentType field.*/
    public static final FieldMetaData META_CONTENT_TYPE = new StringFieldMetaData(CONTENT_TYPE, "[label.Jaffa.Attachment.Attachment.ContentType]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the Description field.*/
    public static final FieldMetaData META_DESCRIPTION = new StringFieldMetaData(DESCRIPTION, "[label.Jaffa.Attachment.Attachment.Description]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the Remarks field.*/
    public static final FieldMetaData META_REMARKS = new StringFieldMetaData(REMARKS, "[label.Jaffa.Attachment.Attachment.Remarks]", Boolean.FALSE, null, new Integer(255), null);

    /** A constant which holds the meta information for the SupercededBy field.*/
    public static final FieldMetaData META_SUPERCEDED_BY = new StringFieldMetaData(SUPERCEDED_BY, "[label.Jaffa.Attachment.Attachment.SupercededBy]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Common.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Common.CreatedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the LastChangedOn field.*/
    public static final FieldMetaData META_LAST_CHANGED_ON = new DateTimeFieldMetaData(LAST_CHANGED_ON, "[label.Common.LastChangedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LastChangedBy field.*/
    public static final FieldMetaData META_LAST_CHANGED_BY = new StringFieldMetaData(LAST_CHANGED_BY, "[label.Common.LastChangedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the Data field.*/
    public static final FieldMetaData META_DATA = new RawFieldMetaData(DATA, "[label.Jaffa.Attachment.Attachment.Data]", Boolean.FALSE);

    /** A constant which holds the meta information for the VersionNumber field.*/
    public static final FieldMetaData META_VERSION_NUMBER = new IntegerFieldMetaData(VERSION_NUMBER, "[label.Jaffa.Attachment.AttachmentDelete.VersionNumber]", Boolean.FALSE, null, null, null, new Integer(10));



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(ATTACHMENT_ID, META_ATTACHMENT_ID);
        m_fieldMap.put(DOCUMENT_REFERENCE_ID, META_DOCUMENT_REFERENCE_ID);
        m_fieldMap.put(SERIALIZED_KEY, META_SERIALIZED_KEY);
        m_fieldMap.put(ORIGINAL_FILE_NAME, META_ORIGINAL_FILE_NAME);
        m_fieldMap.put(ATTACHMENT_TYPE, META_ATTACHMENT_TYPE);
        m_fieldMap.put(CONTENT_TYPE, META_CONTENT_TYPE);
        m_fieldMap.put(DESCRIPTION, META_DESCRIPTION);
        m_fieldMap.put(REMARKS, META_REMARKS);
        m_fieldMap.put(SUPERCEDED_BY, META_SUPERCEDED_BY);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LAST_CHANGED_ON, META_LAST_CHANGED_ON);
        m_fieldMap.put(LAST_CHANGED_BY, META_LAST_CHANGED_BY);
        m_fieldMap.put(DATA, META_DATA);
        m_fieldMap.put(VERSION_NUMBER, META_VERSION_NUMBER);
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

