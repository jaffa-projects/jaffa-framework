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




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_ATTACHMENTS_DEL table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_ATTACHMENTS_DEL")
@SqlResultSetMapping(name="AttachmentDelete",entities={@EntityResult(entityClass=AttachmentDelete.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class AttachmentDelete extends Persistent {

    private static final Logger log = Logger.getLogger(AttachmentDelete.class);
    /** Holds value of property attachmentId. */
    @XmlElement(name="attachmentId")
    @Id
    @Column(name="ATTACHMENT_ID")    
    private java.lang.String m_attachmentId;

    /** Holds value of property serializedKey. */
    @XmlElement(name="serializedKey")
    @Column(name="SERIALIZED_KEY")    
    private java.lang.String m_serializedKey;

    /** Holds value of property versionNumber. */
    @XmlElement(name="versionNumber")
    @Column(name="VERSION_NUMBER")    
    private java.lang.Long m_versionNumber;

    /** Holds value of property deletionCreatedOn. */
    @XmlElement(name="deletionCreatedOn")
    @Type(type="dateTimeType")
    @Column(name="DELETION_CREATED_ON")    
    private org.jaffa.datatypes.DateTime m_deletionCreatedOn;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AttachmentDelete() {
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
    public static AttachmentDelete findByPK(UOW uow, java.lang.String attachmentId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(attachmentId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (AttachmentDelete) itr.next();
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
        criteria.setTable(AttachmentDeleteMeta.getName());
        criteria.addCriteria(AttachmentDeleteMeta.ATTACHMENT_ID, attachmentId);
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
        super.addInitialValue(AttachmentDeleteMeta.ATTACHMENT_ID, m_attachmentId);
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
        attachmentId = FieldValidator.validate(attachmentId, (StringFieldMetaData) AttachmentDeleteMeta.META_ATTACHMENT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentDeleteMeta.getName(), AttachmentDeleteMeta.ATTACHMENT_ID, attachmentId, this.getUOW());

        // .//GEN-END:attachmentId_3_be
        // Add custom code after a successful validation//GEN-FIRST:attachmentId_2


        // .//GEN-LAST:attachmentId_2
        // .//GEN-BEGIN:attachmentId_4_be
        return attachmentId;
    }
    // .//GEN-END:attachmentId_4_be
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
        super.addInitialValue(AttachmentDeleteMeta.SERIALIZED_KEY, m_serializedKey);
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
        serializedKey = FieldValidator.validate(serializedKey, (StringFieldMetaData) AttachmentDeleteMeta.META_SERIALIZED_KEY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentDeleteMeta.getName(), AttachmentDeleteMeta.SERIALIZED_KEY, serializedKey, this.getUOW());

        // .//GEN-END:serializedKey_3_be
        // Add custom code after a successful validation//GEN-FIRST:serializedKey_2


        // .//GEN-LAST:serializedKey_2
        // .//GEN-BEGIN:serializedKey_4_be
        return serializedKey;
    }
    // .//GEN-END:serializedKey_4_be
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
        super.addInitialValue(AttachmentDeleteMeta.VERSION_NUMBER, m_versionNumber);
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
        versionNumber = FieldValidator.validate(versionNumber, (IntegerFieldMetaData) AttachmentDeleteMeta.META_VERSION_NUMBER, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentDeleteMeta.getName(), AttachmentDeleteMeta.VERSION_NUMBER, versionNumber, this.getUOW());

        // .//GEN-END:versionNumber_3_be
        // Add custom code after a successful validation//GEN-FIRST:versionNumber_2


        // .//GEN-LAST:versionNumber_2
        // .//GEN-BEGIN:versionNumber_4_be
        return versionNumber;
    }
    // .//GEN-END:versionNumber_4_be
    // .//GEN-BEGIN:deletionCreatedOn_be
    /** Getter for property deletionCreatedOn.
     * @return Value of property deletionCreatedOn.
     */
    public org.jaffa.datatypes.DateTime getDeletionCreatedOn() {
        return m_deletionCreatedOn;
    }
    
    /** Use this method to update the property deletionCreatedOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param deletionCreatedOn New value of property deletionCreatedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_deletionCreatedOn == null ? deletionCreatedOn == null : m_deletionCreatedOn.equals(deletionCreatedOn))
            return;


        deletionCreatedOn = validateDeletionCreatedOn(deletionCreatedOn);
        // .//GEN-END:deletionCreatedOn_be
        // Add custom code before setting the value//GEN-FIRST:deletionCreatedOn


        // .//GEN-LAST:deletionCreatedOn
        // .//GEN-BEGIN:deletionCreatedOn_1_be
        super.update();
        super.addInitialValue(AttachmentDeleteMeta.DELETION_CREATED_ON, m_deletionCreatedOn);
        m_deletionCreatedOn = deletionCreatedOn;
        // .//GEN-END:deletionCreatedOn_1_be
        // Add custom code after setting the value//GEN-FIRST:deletionCreatedOn_3


        // .//GEN-LAST:deletionCreatedOn_3
        // .//GEN-BEGIN:deletionCreatedOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDeletionCreatedOn() method.
     * @param deletionCreatedOn New value of property deletionCreatedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDeletionCreatedOn(deletionCreatedOn);
    }

    /** Use this method to validate a value for the property deletionCreatedOn.
     * @param deletionCreatedOn Value to be validated for the property deletionCreatedOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateDeletionCreatedOn(org.jaffa.datatypes.DateTime deletionCreatedOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:deletionCreatedOn_2_be
        // Add custom code before validation//GEN-FIRST:deletionCreatedOn_1


        // .//GEN-LAST:deletionCreatedOn_1
        // .//GEN-BEGIN:deletionCreatedOn_3_be
        deletionCreatedOn = FieldValidator.validate(deletionCreatedOn, (DateTimeFieldMetaData) AttachmentDeleteMeta.META_DELETION_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AttachmentDeleteMeta.getName(), AttachmentDeleteMeta.DELETION_CREATED_ON, deletionCreatedOn, this.getUOW());

        // .//GEN-END:deletionCreatedOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:deletionCreatedOn_2


        // .//GEN-LAST:deletionCreatedOn_2
        // .//GEN-BEGIN:deletionCreatedOn_4_be
        return deletionCreatedOn;
    }
    // .//GEN-END:deletionCreatedOn_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AttachmentDelete>");
        buf.append("<attachmentId>"); if (m_attachmentId != null) buf.append(m_attachmentId); buf.append("</attachmentId>");
        buf.append("<serializedKey>"); if (m_serializedKey != null) buf.append(m_serializedKey); buf.append("</serializedKey>");
        buf.append("<versionNumber>"); if (m_versionNumber != null) buf.append(m_versionNumber); buf.append("</versionNumber>");
        buf.append("<deletionCreatedOn>"); if (m_deletionCreatedOn != null) buf.append(m_deletionCreatedOn); buf.append("</deletionCreatedOn>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</AttachmentDelete>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        AttachmentDelete obj = (AttachmentDelete) super.clone();
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
            if (getDeletionCreatedOn() == null)
                setDeletionCreatedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        }
        // .//GEN-END:preAdd_1_be
        // Add custom code//GEN-FIRST:preAdd_1
        
        // This custom code to populate the SerializedKey for Data Distribution Bulk Process.
        try {
            Attachment attachment = Attachment.findByPK(getUOW(), getAttachmentId());
            if (attachment != null) {
                setSerializedKey(attachment.getSerializedKey());
            }
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[]{"SerializedKey: Population Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[]{"SerializedKey: Population Error"}, e);
        }


        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.preAdd();
    }
    // .//GEN-END:preAdd_2_be

    // All the custom code goes here//GEN-FIRST:custom

  /** This method is triggered by the UOW, after adding this object to the Add-Store.
   * A concrete persistent object should provide the implementation, if its necessary.
   * @throws PostAddFailedException if any error occurs during the process.
   */
  @Override
  public void postAdd() throws PostAddFailedException {
    super.postAdd();
  }

  /** This method is triggered by the UOW, before adding this object to the Update-Store.
   * This will perform the following tasks:
   *    Will invoke the performForeignKeyValidations() method to ensure no invalid foreign-keys are set.
   *    Will invoke PersistentHelper.checkMandatoryFields() to perform mandatory field checks.
   *    Will invoke the Rules Engine to perform mandatory field checks.
   * @throws PreUpdateFailedException if any error occurs during the process.
   */
  @Override
  public void preUpdate() throws PreUpdateFailedException {
    super.preUpdate();
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
