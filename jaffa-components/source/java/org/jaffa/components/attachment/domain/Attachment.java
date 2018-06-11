// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.attachment.domain;

import org.apache.log4j.Logger;
import java.util.*;
import javax.xml.bind.annotation.*;
import javax.persistence.*;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.adapters.*;
import org.jaffa.metadata.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.*;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.RelatedDomainObjectFoundException;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.components.attachment.apis.AttachmentFactory;
import org.jaffa.components.attachment.apis.IAttachmentData;


// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_ATTACHMENTS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_ATTACHMENTS")
@SqlResultSetMapping(name="Attachment",entities={@EntityResult(entityClass=Attachment.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class Attachment extends Persistent {

    private static final Logger log = Logger.getLogger(Attachment.class);
    /** Holds value of property attachmentId. */
    @XmlElement(name="attachmentId")
    @Id
    @Column(name="ATTACHMENT_ID")    
    private java.lang.String m_attachmentId;

    /** Holds value of property documentReferenceId. */
    @XmlElement(name="documentReferenceId")
    @Column(name="DOCUMENT_REFERENCE_ID")    
    private java.lang.String m_documentReferenceId;

    /** Holds value of property serializedKey. */
    @XmlElement(name="serializedKey")
    @Column(name="SERIALIZED_KEY")    
    private java.lang.String m_serializedKey;

    /** Holds value of property originalFileName. */
    @XmlElement(name="originalFileName")
    @Column(name="ORIGINAL_FILE_NAME")    
    private java.lang.String m_originalFileName;

    /** Holds value of property attachmentType. */
    @XmlElement(name="attachmentType")
    @Column(name="ATTACHMENT_TYPE")    
    private java.lang.String m_attachmentType;

    /** Holds value of property contentType. */
    @XmlElement(name="contentType")
    @Column(name="CONTENT_TYPE")    
    private java.lang.String m_contentType;

    /** Holds value of property description. */
    @XmlElement(name="description")
    @Column(name="DESCRIPTION")    
    private java.lang.String m_description;

    /** Holds value of property remarks. */
    @XmlElement(name="remarks")
    @Column(name="REMARKS")    
    private java.lang.String m_remarks;

    /** Holds value of property supercededBy. */
    @XmlElement(name="supercededBy")
    @Column(name="SUPERCEDED_BY")    
    private java.lang.String m_supercededBy;

    /** Holds value of property createdOn. */
    @XmlElement(name="createdOn")
    @Type(type="dateTimeType")
    @Column(name="CREATED_ON")    
    private org.jaffa.datatypes.DateTime m_createdOn;

    /** Holds value of property createdBy. */
    @XmlElement(name="createdBy")
    @Column(name="CREATED_BY")    
    private java.lang.String m_createdBy;

    /** Holds value of property lastChangedOn. */
    @XmlElement(name="lastChangedOn")
    @Type(type="dateTimeType")
    @Column(name="LAST_CHANGED_ON")    
    private org.jaffa.datatypes.DateTime m_lastChangedOn;

    /** Holds value of property lastChangedBy. */
    @XmlElement(name="lastChangedBy")
    @Column(name="LAST_CHANGED_BY")    
    private java.lang.String m_lastChangedBy;

    /** Holds value of property data. */
    @XmlElement(name="data")
    @Column(name="DATA")    
    private byte[] m_data;

    /** Holds value of property versionNumber. */
    @XmlElement(name="versionNumber")
    @Column(name="VERSION_NUMBER")    
    private java.lang.Long m_versionNumber;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public Attachment() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String attachmentId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(attachmentId);
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                exists = count != null && count.intValue() > 0;
            }
            return exists;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }

    /** Returns the domain object for the input Primary Key.
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static Attachment findByPK(UOW uow, java.lang.String attachmentId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(attachmentId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (Attachment) itr.next();
            else
                return null;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }

    /** Returns a Criteria object for retrieving the domain object based on the input Primary Key.
     * @return a Criteria object for retrieving the domain object based on the input Primary Key.
     */
    public static Criteria findByPKCriteria(java.lang.String attachmentId) {
        Criteria criteria = new Criteria();
        criteria.setTable(AttachmentMeta.getName());
        criteria.addCriteria(AttachmentMeta.ATTACHMENT_ID, attachmentId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:attachmentId_be
    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public java.lang.String getAttachmentId() {
        return m_attachmentId;
    }
    
    /** Use this method to update the property attachmentId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param attachmentId New value of property attachmentId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setAttachmentId(java.lang.String attachmentId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_attachmentId == null ? attachmentId == null : m_attachmentId.equals(attachmentId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        attachmentId = validateAttachmentId(attachmentId);
        // .//GEN-END:attachmentId_be
        // Add custom code before setting the value//GEN-FIRST:attachmentId


        // .//GEN-LAST:attachmentId
        // .//GEN-BEGIN:attachmentId_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.ATTACHMENT_ID, m_attachmentId);
        m_attachmentId = attachmentId;
        // .//GEN-END:attachmentId_1_be
        // Add custom code after setting the value//GEN-FIRST:attachmentId_3


        // .//GEN-LAST:attachmentId_3
        // .//GEN-BEGIN:attachmentId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setAttachmentId() method.
     * @param attachmentId New value of property attachmentId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateAttachmentId(java.lang.String attachmentId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setAttachmentId(attachmentId);
    }

    /** Use this method to validate a value for the property attachmentId.
     * @param attachmentId Value to be validated for the property attachmentId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateAttachmentId(java.lang.String attachmentId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:attachmentId_2_be
        // Add custom code before validation//GEN-FIRST:attachmentId_1


        // .//GEN-LAST:attachmentId_1
        // .//GEN-BEGIN:attachmentId_3_be
        attachmentId = FieldValidator.validate(attachmentId, (StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.ATTACHMENT_ID, attachmentId, this.getUOW());

        // .//GEN-END:attachmentId_3_be
        // Add custom code after a successful validation//GEN-FIRST:attachmentId_2


        // .//GEN-LAST:attachmentId_2
        // .//GEN-BEGIN:attachmentId_4_be
        return attachmentId;
    }
    // .//GEN-END:attachmentId_4_be
    // .//GEN-BEGIN:documentReferenceId_be
    /** Getter for property documentReferenceId.
     * @return Value of property documentReferenceId.
     */
    public java.lang.String getDocumentReferenceId() {
        return m_documentReferenceId;
    }
    
    /** Use this method to update the property documentReferenceId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param documentReferenceId New value of property documentReferenceId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDocumentReferenceId(java.lang.String documentReferenceId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_documentReferenceId == null ? documentReferenceId == null : m_documentReferenceId.equals(documentReferenceId))
            return;


        documentReferenceId = validateDocumentReferenceId(documentReferenceId);
        // .//GEN-END:documentReferenceId_be
        // Add custom code before setting the value//GEN-FIRST:documentReferenceId


        // .//GEN-LAST:documentReferenceId
        // .//GEN-BEGIN:documentReferenceId_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.DOCUMENT_REFERENCE_ID, m_documentReferenceId);
        m_documentReferenceId = documentReferenceId;
        // .//GEN-END:documentReferenceId_1_be
        // Add custom code after setting the value//GEN-FIRST:documentReferenceId_3


        // .//GEN-LAST:documentReferenceId_3
        // .//GEN-BEGIN:documentReferenceId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDocumentReferenceId() method.
     * @param documentReferenceId New value of property documentReferenceId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDocumentReferenceId(java.lang.String documentReferenceId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDocumentReferenceId(documentReferenceId);
    }

    /** Use this method to validate a value for the property documentReferenceId.
     * @param documentReferenceId Value to be validated for the property documentReferenceId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDocumentReferenceId(java.lang.String documentReferenceId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:documentReferenceId_2_be
        // Add custom code before validation//GEN-FIRST:documentReferenceId_1


        // .//GEN-LAST:documentReferenceId_1
        // .//GEN-BEGIN:documentReferenceId_3_be
        documentReferenceId = FieldValidator.validate(documentReferenceId, (StringFieldMetaData) AttachmentMeta.META_DOCUMENT_REFERENCE_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.DOCUMENT_REFERENCE_ID, documentReferenceId, this.getUOW());

        // .//GEN-END:documentReferenceId_3_be
        // Add custom code after a successful validation//GEN-FIRST:documentReferenceId_2


        // .//GEN-LAST:documentReferenceId_2
        // .//GEN-BEGIN:documentReferenceId_4_be
        return documentReferenceId;
    }
    // .//GEN-END:documentReferenceId_4_be
    // .//GEN-BEGIN:serializedKey_be
    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public java.lang.String getSerializedKey() {
        return m_serializedKey;
    }
    
    /** Use this method to update the property serializedKey.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param serializedKey New value of property serializedKey.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSerializedKey(java.lang.String serializedKey)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_serializedKey == null ? serializedKey == null : m_serializedKey.equals(serializedKey))
            return;


        serializedKey = validateSerializedKey(serializedKey);
        // .//GEN-END:serializedKey_be
        // Add custom code before setting the value//GEN-FIRST:serializedKey


        // .//GEN-LAST:serializedKey
        // .//GEN-BEGIN:serializedKey_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.SERIALIZED_KEY, m_serializedKey);
        m_serializedKey = serializedKey;
        // .//GEN-END:serializedKey_1_be
        // Add custom code after setting the value//GEN-FIRST:serializedKey_3


        // .//GEN-LAST:serializedKey_3
        // .//GEN-BEGIN:serializedKey_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSerializedKey() method.
     * @param serializedKey New value of property serializedKey.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSerializedKey(java.lang.String serializedKey)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSerializedKey(serializedKey);
    }

    /** Use this method to validate a value for the property serializedKey.
     * @param serializedKey Value to be validated for the property serializedKey.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSerializedKey(java.lang.String serializedKey)
    throws ValidationException, FrameworkException {
        // .//GEN-END:serializedKey_2_be
        // Add custom code before validation//GEN-FIRST:serializedKey_1


        // .//GEN-LAST:serializedKey_1
        // .//GEN-BEGIN:serializedKey_3_be
        serializedKey = FieldValidator.validate(serializedKey, (StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.SERIALIZED_KEY, serializedKey, this.getUOW());

        // .//GEN-END:serializedKey_3_be
        // Add custom code after a successful validation//GEN-FIRST:serializedKey_2


        // .//GEN-LAST:serializedKey_2
        // .//GEN-BEGIN:serializedKey_4_be
        return serializedKey;
    }
    // .//GEN-END:serializedKey_4_be
    // .//GEN-BEGIN:originalFileName_be
    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public java.lang.String getOriginalFileName() {
        return m_originalFileName;
    }
    
    /** Use this method to update the property originalFileName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param originalFileName New value of property originalFileName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setOriginalFileName(java.lang.String originalFileName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_originalFileName == null ? originalFileName == null : m_originalFileName.equals(originalFileName))
            return;


        originalFileName = validateOriginalFileName(originalFileName);
        // .//GEN-END:originalFileName_be
        // Add custom code before setting the value//GEN-FIRST:originalFileName


        // .//GEN-LAST:originalFileName
        // .//GEN-BEGIN:originalFileName_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.ORIGINAL_FILE_NAME, m_originalFileName);
        m_originalFileName = originalFileName;
        // .//GEN-END:originalFileName_1_be
        // Add custom code after setting the value//GEN-FIRST:originalFileName_3


        // .//GEN-LAST:originalFileName_3
        // .//GEN-BEGIN:originalFileName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setOriginalFileName() method.
     * @param originalFileName New value of property originalFileName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateOriginalFileName(java.lang.String originalFileName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setOriginalFileName(originalFileName);
    }

    /** Use this method to validate a value for the property originalFileName.
     * @param originalFileName Value to be validated for the property originalFileName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateOriginalFileName(java.lang.String originalFileName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:originalFileName_2_be
        // Add custom code before validation//GEN-FIRST:originalFileName_1


        // .//GEN-LAST:originalFileName_1
        // .//GEN-BEGIN:originalFileName_3_be
        originalFileName = FieldValidator.validate(originalFileName, (StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.ORIGINAL_FILE_NAME, originalFileName, this.getUOW());

        // .//GEN-END:originalFileName_3_be
        // Add custom code after a successful validation//GEN-FIRST:originalFileName_2


        // .//GEN-LAST:originalFileName_2
        // .//GEN-BEGIN:originalFileName_4_be
        return originalFileName;
    }
    // .//GEN-END:originalFileName_4_be
    // .//GEN-BEGIN:attachmentType_be
    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public java.lang.String getAttachmentType() {
        return m_attachmentType;
    }
    
    /** Use this method to update the property attachmentType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param attachmentType New value of property attachmentType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setAttachmentType(java.lang.String attachmentType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_attachmentType == null ? attachmentType == null : m_attachmentType.equals(attachmentType))
            return;


        attachmentType = validateAttachmentType(attachmentType);
        // .//GEN-END:attachmentType_be
        // Add custom code before setting the value//GEN-FIRST:attachmentType


        // .//GEN-LAST:attachmentType
        // .//GEN-BEGIN:attachmentType_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.ATTACHMENT_TYPE, m_attachmentType);
        m_attachmentType = attachmentType;
        // .//GEN-END:attachmentType_1_be
        // Add custom code after setting the value//GEN-FIRST:attachmentType_3


        // .//GEN-LAST:attachmentType_3
        // .//GEN-BEGIN:attachmentType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setAttachmentType() method.
     * @param attachmentType New value of property attachmentType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateAttachmentType(java.lang.String attachmentType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setAttachmentType(attachmentType);
    }

    /** Use this method to validate a value for the property attachmentType.
     * @param attachmentType Value to be validated for the property attachmentType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateAttachmentType(java.lang.String attachmentType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:attachmentType_2_be
        // Add custom code before validation//GEN-FIRST:attachmentType_1


        // .//GEN-LAST:attachmentType_1
        // .//GEN-BEGIN:attachmentType_3_be
        attachmentType = FieldValidator.validate(attachmentType, (StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.ATTACHMENT_TYPE, attachmentType, this.getUOW());

        // .//GEN-END:attachmentType_3_be
        // Add custom code after a successful validation//GEN-FIRST:attachmentType_2


        // .//GEN-LAST:attachmentType_2
        // .//GEN-BEGIN:attachmentType_4_be
        return attachmentType;
    }
    // .//GEN-END:attachmentType_4_be
    // .//GEN-BEGIN:contentType_be
    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType() {
        return m_contentType;
    }
    
    /** Use this method to update the property contentType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param contentType New value of property contentType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setContentType(java.lang.String contentType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_contentType == null ? contentType == null : m_contentType.equals(contentType))
            return;


        contentType = validateContentType(contentType);
        // .//GEN-END:contentType_be
        // Add custom code before setting the value//GEN-FIRST:contentType


        // .//GEN-LAST:contentType
        // .//GEN-BEGIN:contentType_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.CONTENT_TYPE, m_contentType);
        m_contentType = contentType;
        // .//GEN-END:contentType_1_be
        // Add custom code after setting the value//GEN-FIRST:contentType_3


        // .//GEN-LAST:contentType_3
        // .//GEN-BEGIN:contentType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setContentType() method.
     * @param contentType New value of property contentType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateContentType(java.lang.String contentType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setContentType(contentType);
    }

    /** Use this method to validate a value for the property contentType.
     * @param contentType Value to be validated for the property contentType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateContentType(java.lang.String contentType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:contentType_2_be
        // Add custom code before validation//GEN-FIRST:contentType_1


        // .//GEN-LAST:contentType_1
        // .//GEN-BEGIN:contentType_3_be
        contentType = FieldValidator.validate(contentType, (StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.CONTENT_TYPE, contentType, this.getUOW());

        // .//GEN-END:contentType_3_be
        // Add custom code after a successful validation//GEN-FIRST:contentType_2


        // .//GEN-LAST:contentType_2
        // .//GEN-BEGIN:contentType_4_be
        return contentType;
    }
    // .//GEN-END:contentType_4_be
    // .//GEN-BEGIN:description_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return m_description;
    }
    
    /** Use this method to update the property description.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param description New value of property description.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDescription(java.lang.String description)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_description == null ? description == null : m_description.equals(description))
            return;


        description = validateDescription(description);
        // .//GEN-END:description_be
        // Add custom code before setting the value//GEN-FIRST:description


        // .//GEN-LAST:description
        // .//GEN-BEGIN:description_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.DESCRIPTION, m_description);
        m_description = description;
        // .//GEN-END:description_1_be
        // Add custom code after setting the value//GEN-FIRST:description_3


        // .//GEN-LAST:description_3
        // .//GEN-BEGIN:description_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDescription() method.
     * @param description New value of property description.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDescription(java.lang.String description)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDescription(description);
    }

    /** Use this method to validate a value for the property description.
     * @param description Value to be validated for the property description.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDescription(java.lang.String description)
    throws ValidationException, FrameworkException {
        // .//GEN-END:description_2_be
        // Add custom code before validation//GEN-FIRST:description_1


        // .//GEN-LAST:description_1
        // .//GEN-BEGIN:description_3_be
        description = FieldValidator.validate(description, (StringFieldMetaData) AttachmentMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
    // .//GEN-BEGIN:remarks_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return m_remarks;
    }
    
    /** Use this method to update the property remarks.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param remarks New value of property remarks.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRemarks(java.lang.String remarks)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_remarks == null ? remarks == null : m_remarks.equals(remarks))
            return;


        remarks = validateRemarks(remarks);
        // .//GEN-END:remarks_be
        // Add custom code before setting the value//GEN-FIRST:remarks


        // .//GEN-LAST:remarks
        // .//GEN-BEGIN:remarks_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.REMARKS, m_remarks);
        m_remarks = remarks;
        // .//GEN-END:remarks_1_be
        // Add custom code after setting the value//GEN-FIRST:remarks_3


        // .//GEN-LAST:remarks_3
        // .//GEN-BEGIN:remarks_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRemarks() method.
     * @param remarks New value of property remarks.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRemarks(java.lang.String remarks)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRemarks(remarks);
    }

    /** Use this method to validate a value for the property remarks.
     * @param remarks Value to be validated for the property remarks.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateRemarks(java.lang.String remarks)
    throws ValidationException, FrameworkException {
        // .//GEN-END:remarks_2_be
        // Add custom code before validation//GEN-FIRST:remarks_1


        // .//GEN-LAST:remarks_1
        // .//GEN-BEGIN:remarks_3_be
        remarks = FieldValidator.validate(remarks, (StringFieldMetaData) AttachmentMeta.META_REMARKS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.REMARKS, remarks, this.getUOW());

        // .//GEN-END:remarks_3_be
        // Add custom code after a successful validation//GEN-FIRST:remarks_2


        // .//GEN-LAST:remarks_2
        // .//GEN-BEGIN:remarks_4_be
        return remarks;
    }
    // .//GEN-END:remarks_4_be
    // .//GEN-BEGIN:supercededBy_be
    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public java.lang.String getSupercededBy() {
        return m_supercededBy;
    }
    
    /** Use this method to update the property supercededBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param supercededBy New value of property supercededBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSupercededBy(java.lang.String supercededBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_supercededBy == null ? supercededBy == null : m_supercededBy.equals(supercededBy))
            return;


        supercededBy = validateSupercededBy(supercededBy);
        // .//GEN-END:supercededBy_be
        // Add custom code before setting the value//GEN-FIRST:supercededBy


        // .//GEN-LAST:supercededBy
        // .//GEN-BEGIN:supercededBy_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.SUPERCEDED_BY, m_supercededBy);
        m_supercededBy = supercededBy;
        // .//GEN-END:supercededBy_1_be
        // Add custom code after setting the value//GEN-FIRST:supercededBy_3


        // .//GEN-LAST:supercededBy_3
        // .//GEN-BEGIN:supercededBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSupercededBy() method.
     * @param supercededBy New value of property supercededBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSupercededBy(java.lang.String supercededBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSupercededBy(supercededBy);
    }

    /** Use this method to validate a value for the property supercededBy.
     * @param supercededBy Value to be validated for the property supercededBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSupercededBy(java.lang.String supercededBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:supercededBy_2_be
        // Add custom code before validation//GEN-FIRST:supercededBy_1


        // .//GEN-LAST:supercededBy_1
        // .//GEN-BEGIN:supercededBy_3_be
        supercededBy = FieldValidator.validate(supercededBy, (StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.SUPERCEDED_BY, supercededBy, this.getUOW());

        // .//GEN-END:supercededBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:supercededBy_2


        // .//GEN-LAST:supercededBy_2
        // .//GEN-BEGIN:supercededBy_4_be
        return supercededBy;
    }
    // .//GEN-END:supercededBy_4_be
    // .//GEN-BEGIN:createdOn_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }
    
    /** Use this method to update the property createdOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param createdOn New value of property createdOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_createdOn == null ? createdOn == null : m_createdOn.equals(createdOn))
            return;


        createdOn = validateCreatedOn(createdOn);
        // .//GEN-END:createdOn_be
        // Add custom code before setting the value//GEN-FIRST:createdOn


        // .//GEN-LAST:createdOn
        // .//GEN-BEGIN:createdOn_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.CREATED_ON, m_createdOn);
        m_createdOn = createdOn;
        // .//GEN-END:createdOn_1_be
        // Add custom code after setting the value//GEN-FIRST:createdOn_3


        // .//GEN-LAST:createdOn_3
        // .//GEN-BEGIN:createdOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCreatedOn() method.
     * @param createdOn New value of property createdOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCreatedOn(createdOn);
    }

    /** Use this method to validate a value for the property createdOn.
     * @param createdOn Value to be validated for the property createdOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:createdOn_2_be
        // Add custom code before validation//GEN-FIRST:createdOn_1


        // .//GEN-LAST:createdOn_1
        // .//GEN-BEGIN:createdOn_3_be
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) AttachmentMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.CREATED_ON, createdOn, this.getUOW());

        // .//GEN-END:createdOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdOn_2


        // .//GEN-LAST:createdOn_2
        // .//GEN-BEGIN:createdOn_4_be
        return createdOn;
    }
    // .//GEN-END:createdOn_4_be
    // .//GEN-BEGIN:createdBy_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return m_createdBy;
    }
    
    /** Use this method to update the property createdBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param createdBy New value of property createdBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedBy(java.lang.String createdBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_createdBy == null ? createdBy == null : m_createdBy.equals(createdBy))
            return;


        createdBy = validateCreatedBy(createdBy);
        // .//GEN-END:createdBy_be
        // Add custom code before setting the value//GEN-FIRST:createdBy


        // .//GEN-LAST:createdBy
        // .//GEN-BEGIN:createdBy_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.CREATED_BY, m_createdBy);
        m_createdBy = createdBy;
        // .//GEN-END:createdBy_1_be
        // Add custom code after setting the value//GEN-FIRST:createdBy_3


        // .//GEN-LAST:createdBy_3
        // .//GEN-BEGIN:createdBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCreatedBy() method.
     * @param createdBy New value of property createdBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCreatedBy(java.lang.String createdBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCreatedBy(createdBy);
    }

    /** Use this method to validate a value for the property createdBy.
     * @param createdBy Value to be validated for the property createdBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCreatedBy(java.lang.String createdBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:createdBy_2_be
        // Add custom code before validation//GEN-FIRST:createdBy_1


        // .//GEN-LAST:createdBy_1
        // .//GEN-BEGIN:createdBy_3_be
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) AttachmentMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.CREATED_BY, createdBy, this.getUOW());

        // .//GEN-END:createdBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdBy_2


        // .//GEN-LAST:createdBy_2
        // .//GEN-BEGIN:createdBy_4_be
        return createdBy;
    }
    // .//GEN-END:createdBy_4_be
    // .//GEN-BEGIN:lastChangedOn_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return m_lastChangedOn;
    }
    
    /** Use this method to update the property lastChangedOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param lastChangedOn New value of property lastChangedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_lastChangedOn == null ? lastChangedOn == null : m_lastChangedOn.equals(lastChangedOn))
            return;


        lastChangedOn = validateLastChangedOn(lastChangedOn);
        // .//GEN-END:lastChangedOn_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedOn


        // .//GEN-LAST:lastChangedOn
        // .//GEN-BEGIN:lastChangedOn_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.LAST_CHANGED_ON, m_lastChangedOn);
        m_lastChangedOn = lastChangedOn;
        // .//GEN-END:lastChangedOn_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedOn_3


        // .//GEN-LAST:lastChangedOn_3
        // .//GEN-BEGIN:lastChangedOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLastChangedOn() method.
     * @param lastChangedOn New value of property lastChangedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLastChangedOn(lastChangedOn);
    }

    /** Use this method to validate a value for the property lastChangedOn.
     * @param lastChangedOn Value to be validated for the property lastChangedOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:lastChangedOn_2_be
        // Add custom code before validation//GEN-FIRST:lastChangedOn_1


        // .//GEN-LAST:lastChangedOn_1
        // .//GEN-BEGIN:lastChangedOn_3_be
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) AttachmentMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

        // .//GEN-END:lastChangedOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedOn_2


        // .//GEN-LAST:lastChangedOn_2
        // .//GEN-BEGIN:lastChangedOn_4_be
        return lastChangedOn;
    }
    // .//GEN-END:lastChangedOn_4_be
    // .//GEN-BEGIN:lastChangedBy_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return m_lastChangedBy;
    }
    
    /** Use this method to update the property lastChangedBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param lastChangedBy New value of property lastChangedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_lastChangedBy == null ? lastChangedBy == null : m_lastChangedBy.equals(lastChangedBy))
            return;


        lastChangedBy = validateLastChangedBy(lastChangedBy);
        // .//GEN-END:lastChangedBy_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedBy


        // .//GEN-LAST:lastChangedBy
        // .//GEN-BEGIN:lastChangedBy_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.LAST_CHANGED_BY, m_lastChangedBy);
        m_lastChangedBy = lastChangedBy;
        // .//GEN-END:lastChangedBy_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedBy_3


        // .//GEN-LAST:lastChangedBy_3
        // .//GEN-BEGIN:lastChangedBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLastChangedBy() method.
     * @param lastChangedBy New value of property lastChangedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLastChangedBy(lastChangedBy);
    }

    /** Use this method to validate a value for the property lastChangedBy.
     * @param lastChangedBy Value to be validated for the property lastChangedBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:lastChangedBy_2_be
        // Add custom code before validation//GEN-FIRST:lastChangedBy_1


        // .//GEN-LAST:lastChangedBy_1
        // .//GEN-BEGIN:lastChangedBy_3_be
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
    // .//GEN-BEGIN:data_be
    /** Getter for property data.
     * @return Value of property data.
     */
    public byte[] getData() {
        return m_data;
    }
    
    /** Use this method to update the property data.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param data New value of property data.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setData(byte[] data)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_data == null ? data == null : m_data.equals(data))
            return;


        data = validateData(data);
        // .//GEN-END:data_be
        // Add custom code before setting the value//GEN-FIRST:data


        // .//GEN-LAST:data
        // .//GEN-BEGIN:data_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.DATA, m_data);
        m_data = data;
        // .//GEN-END:data_1_be
        // Add custom code after setting the value//GEN-FIRST:data_3


        // .//GEN-LAST:data_3
        // .//GEN-BEGIN:data_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setData() method.
     * @param data New value of property data.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateData(byte[] data)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setData(data);
    }

    /** Use this method to validate a value for the property data.
     * @param data Value to be validated for the property data.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validateData(byte[] data)
    throws ValidationException, FrameworkException {
        // .//GEN-END:data_2_be
        // Add custom code before validation//GEN-FIRST:data_1


        // .//GEN-LAST:data_1
        // .//GEN-BEGIN:data_3_be
        data = FieldValidator.validate(data, (RawFieldMetaData) AttachmentMeta.META_DATA, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.DATA, data, this.getUOW());

        // .//GEN-END:data_3_be
        // Add custom code after a successful validation//GEN-FIRST:data_2


        // .//GEN-LAST:data_2
        // .//GEN-BEGIN:data_4_be
        return data;
    }
    // .//GEN-END:data_4_be
    // .//GEN-BEGIN:versionNumber_be
    /** Getter for property versionNumber.
     * @return Value of property versionNumber.
     */
    public java.lang.Long getVersionNumber() {
        return m_versionNumber;
    }
    
    /** Use this method to update the property versionNumber.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param versionNumber New value of property versionNumber.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setVersionNumber(java.lang.Long versionNumber)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_versionNumber == null ? versionNumber == null : m_versionNumber.equals(versionNumber))
            return;


        versionNumber = validateVersionNumber(versionNumber);
        // .//GEN-END:versionNumber_be
        // Add custom code before setting the value//GEN-FIRST:versionNumber


        // .//GEN-LAST:versionNumber
        // .//GEN-BEGIN:versionNumber_1_be
        super.update();
        super.addInitialValue(AttachmentMeta.VERSION_NUMBER, m_versionNumber);
        m_versionNumber = versionNumber;
        // .//GEN-END:versionNumber_1_be
        // Add custom code after setting the value//GEN-FIRST:versionNumber_3


        // .//GEN-LAST:versionNumber_3
        // .//GEN-BEGIN:versionNumber_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setVersionNumber() method.
     * @param versionNumber New value of property versionNumber.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateVersionNumber(java.lang.Long versionNumber)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setVersionNumber(versionNumber);
    }

    /** Use this method to validate a value for the property versionNumber.
     * @param versionNumber Value to be validated for the property versionNumber.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateVersionNumber(java.lang.Long versionNumber)
    throws ValidationException, FrameworkException {
        // .//GEN-END:versionNumber_2_be
        // Add custom code before validation//GEN-FIRST:versionNumber_1


        // .//GEN-LAST:versionNumber_1
        // .//GEN-BEGIN:versionNumber_3_be
        versionNumber = FieldValidator.validate(versionNumber, (IntegerFieldMetaData) AttachmentMeta.META_VERSION_NUMBER, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentMeta.getName(), AttachmentMeta.VERSION_NUMBER, versionNumber, this.getUOW());

        // .//GEN-END:versionNumber_3_be
        // Add custom code after a successful validation//GEN-FIRST:versionNumber_2


        // .//GEN-LAST:versionNumber_2
        // .//GEN-BEGIN:versionNumber_4_be
        return versionNumber;
    }
    // .//GEN-END:versionNumber_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Attachment>");
        buf.append("<attachmentId>"); if (m_attachmentId != null) buf.append(m_attachmentId); buf.append("</attachmentId>");
        buf.append("<documentReferenceId>"); if (m_documentReferenceId != null) buf.append(m_documentReferenceId); buf.append("</documentReferenceId>");
        buf.append("<serializedKey>"); if (m_serializedKey != null) buf.append(m_serializedKey); buf.append("</serializedKey>");
        buf.append("<originalFileName>"); if (m_originalFileName != null) buf.append(m_originalFileName); buf.append("</originalFileName>");
        buf.append("<attachmentType>"); if (m_attachmentType != null) buf.append(m_attachmentType); buf.append("</attachmentType>");
        buf.append("<contentType>"); if (m_contentType != null) buf.append(m_contentType); buf.append("</contentType>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<remarks>"); if (m_remarks != null) buf.append(m_remarks); buf.append("</remarks>");
        buf.append("<supercededBy>"); if (m_supercededBy != null) buf.append(m_supercededBy); buf.append("</supercededBy>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        buf.append("<data>"); if (m_data != null) buf.append(m_data); buf.append("</data>");
        buf.append("<versionNumber>"); if (m_versionNumber != null) buf.append(m_versionNumber); buf.append("</versionNumber>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Attachment>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Attachment obj = (Attachment) super.clone();
        return obj;
    }
    // .//GEN-END:clone_1_be
    // .//GEN-BEGIN:performForeignKeyValidations_1_be
    /** This method ensures that the modified foreign-keys are valid.
     * @throws ApplicationExceptions if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = new ApplicationExceptions();
        if (appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:performForeignKeyValidations_1_be
    // .//GEN-BEGIN:performPreDeleteReferentialIntegrity_1_be
    /** This method is triggered by the UOW, before adding this object to the Delete-Store.
     * This will raise an exception if any associated/aggregated objects exist.
     * This will cascade delete all composite objects.
     * @throws PreDeleteFailedException if any error occurs during the process.
     */
    public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException {
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:preAdd_1_be
    /** This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * It ensures that the primary-key is unique and that the foreign-keys are valid.
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        try {
            if (getCreatedOn() == null)
                setCreatedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        }
        try {
            if (getCreatedBy() == null && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setCreatedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        }
        // .//GEN-END:preAdd_1_be
        // Add custom code//GEN-FIRST:preAdd_1


        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.preAdd();
    }
    // .//GEN-END:preAdd_2_be
    // .//GEN-BEGIN:preUpdate_1_be
    /** This method is triggered by the UOW, before adding this object to the Update-Store, but after a UOW has been associated to the object.
     * It ensures that the foreign-keys are valid.
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    public void preUpdate() throws PreUpdateFailedException {
        try {
            if (getLastChangedOn() == null || !isModified(AttachmentMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(AttachmentMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setLastChangedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        }
        // .//GEN-END:preUpdate_1_be
        // Update custom code//GEN-FIRST:preUpdate_1


        // .//GEN-LAST:preUpdate_1
        // .//GEN-BEGIN:preUpdate_2_be
        super.preUpdate();
    }
    // .//GEN-END:preUpdate_2_be
    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        IAttachmentData attachmentDataImpl = AttachmentFactory.getAttachmentDataImpl();
        // Ensure that the attachment is specified
        if(attachmentDataImpl==null) {
            if (getOriginalFileName() == null || ("E".equals(getAttachmentType()) && (getData() == null || getData().length == 0)))
                throw new ApplicationExceptions(new MandatoryFieldException(AttachmentMeta.META_ORIGINAL_FILE_NAME.getLabelToken()));

            // Blank out the uploaded data, if any, for a non-embedded attachment
            if (!"E".equals(getAttachmentType()) && getData() != null) {
                if (log.isDebugEnabled())
                    log.debug("Ensuring that the uploaded data, if any, is blanked out for a non-embedded attachment");
                try {
                    setData(null);
                } catch (ValidationException e) {
                    throw new ApplicationExceptions(e);
                }
            }
        }
        // Generate the technical-key, if required
        if (getAttachmentId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(AttachmentMeta.ATTACHMENT_ID);
                vg.setLabelToken(AttachmentMeta.META_ATTACHMENT_ID.getLabelToken());
                setAttachmentId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }



        if (getDocumentReferenceId() == null && attachmentDataImpl!=null && (getAttachmentType() == null || Arrays.asList(new String[] {"E","D"}).contains(getAttachmentType()))) {
            if (log.isDebugEnabled())
                log.debug("Attachment is stored in external data repository");

            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(AttachmentMeta.DOCUMENT_REFERENCE_ID);
                vg.setLabelToken(AttachmentMeta.META_DOCUMENT_REFERENCE_ID.getLabelToken());
                setDocumentReferenceId(vg.generate());

            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }

        }

        if(getDocumentReferenceId()!=null && attachmentDataImpl!=null){
            try{
                setAttachmentType("D");
                //Null out attachment data as it is stored in document repository
                setData(null);
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }

        super.validate();
    }

  /** This method is triggered by the UOW, after adding this object to the Add-Store.
   * A concrete persistent object should provide the implementation, if its necessary.
   * @throws PostAddFailedException if any error occurs during the process.
   */
  @Override
  public void postAdd() throws PostAddFailedException {
    super.postAdd();
  }

  /** This method is triggered by the UOW, after adding this object to the Update-Store.
   * A concrete persistent object should provide the implementation, if its necessary.
   * @throws PostUpdateFailedException if any error occurs during the process.
   */
  @Override
  public void postUpdate() throws PostUpdateFailedException {
    super.postUpdate();
  }

  /** This method is triggered by the UOW, before adding this object to the Delete-Store.
   * This will perform the following tasks:
   *    Will invoke the performPreDeleteReferentialIntegrity() method.
   * @throws PreDeleteFailedException if any error occurs during the process.
   */
  @Override
  public void preDelete() throws PreDeleteFailedException {
    super.preDelete();
  }

  /** This method is triggered by the UOW, after adding this object to the Delete-Store.
   * A concrete persistent object should provide the implementation, if its necessary.
   * @throws PostDeleteFailedException if any error occurs during the process.
   */
  @Override
  public void postDelete() throws PostDeleteFailedException {
    super.postDelete();
  }

  /** This method is triggered by the UOW after a query loads this object.
   * A concrete persistent object should provide the implementation, if its necessary.
   * @throws PostLoadFailedException if any error occurs during the process.
   */
  @Override
  public void postLoad() throws PostLoadFailedException {
    super.postLoad();
  }

    // .//GEN-LAST:custom
}
