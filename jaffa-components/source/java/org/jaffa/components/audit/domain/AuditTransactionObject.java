// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.audit.domain;

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
import org.jaffa.components.audit.domain.AuditTransaction;
import org.jaffa.components.audit.domain.AuditTransactionMeta;
import org.jaffa.components.audit.domain.AuditTransactionField;
import org.jaffa.components.audit.domain.AuditTransactionFieldMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports

import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_AUDIT_TRANSACTION_OBJECTS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_AUDIT_TRANSACTION_OBJECTS")
@SqlResultSetMapping(name="AuditTransactionObject",entities={@EntityResult(entityClass=AuditTransactionObject.class)})
public class AuditTransactionObject extends Persistent {

    private static final Logger log = Logger.getLogger(AuditTransactionObject.class);
    /** Holds value of property objectId. */
    @XmlElement(name="objectId")
    @Id
    @Column(name="OBJECT_ID")    
    private java.lang.String m_objectId;

    /** Holds value of property transactionId. */
    @XmlElement(name="transactionId")
    @Column(name="TRANSACTION_ID")    
    private java.lang.String m_transactionId;

    /** Holds value of property objectName. */
    @XmlElement(name="objectName")
    @Column(name="OBJECT_NAME")    
    private java.lang.String m_objectName;

    /** Holds value of property changeType. */
    @XmlElement(name="changeType")
    @Column(name="CHANGE_TYPE")    
    private java.lang.String m_changeType;

    /** Holds related foreign AuditTransaction object. */
    private transient AuditTransaction m_auditTransactionObject;

    /** Holds related AuditTransactionField objects. */
    private transient Collection m_auditTransactionFieldCollection;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AuditTransactionObject() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String objectId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(objectId);
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
    public static AuditTransactionObject findByPK(UOW uow, java.lang.String objectId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(objectId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (AuditTransactionObject) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String objectId) {
        Criteria criteria = new Criteria();
        criteria.setTable(AuditTransactionObjectMeta.getName());
        criteria.addCriteria(AuditTransactionObjectMeta.OBJECT_ID, objectId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:objectId_be
    /** Getter for property objectId.
     * @return Value of property objectId.
     */
    public java.lang.String getObjectId() {
        return m_objectId;
    }
    
    /** Use this method to update the property objectId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param objectId New value of property objectId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setObjectId(java.lang.String objectId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_objectId == null ? objectId == null : m_objectId.equals(objectId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        objectId = validateObjectId(objectId);
        // .//GEN-END:objectId_be
        // Add custom code before setting the value//GEN-FIRST:objectId


        // .//GEN-LAST:objectId
        // .//GEN-BEGIN:objectId_1_be
        super.update();
        super.addInitialValue(AuditTransactionObjectMeta.OBJECT_ID, m_objectId);
        m_objectId = objectId;
        // .//GEN-END:objectId_1_be
        // Add custom code after setting the value//GEN-FIRST:objectId_3


        // .//GEN-LAST:objectId_3
        // .//GEN-BEGIN:objectId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setObjectId() method.
     * @param objectId New value of property objectId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateObjectId(java.lang.String objectId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setObjectId(objectId);
    }

    /** Use this method to validate a value for the property objectId.
     * @param objectId Value to be validated for the property objectId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateObjectId(java.lang.String objectId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:objectId_2_be
        // Add custom code before validation//GEN-FIRST:objectId_1


        // .//GEN-LAST:objectId_1
        // .//GEN-BEGIN:objectId_3_be
        objectId = FieldValidator.validate(objectId, (StringFieldMetaData) AuditTransactionObjectMeta.META_OBJECT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionObjectMeta.getName(), AuditTransactionObjectMeta.OBJECT_ID, objectId, this.getUOW());

        // .//GEN-END:objectId_3_be
        // Add custom code after a successful validation//GEN-FIRST:objectId_2


        // .//GEN-LAST:objectId_2
        // .//GEN-BEGIN:objectId_4_be
        return objectId;
    }
    // .//GEN-END:objectId_4_be
    // .//GEN-BEGIN:transactionId_be
    /** Getter for property transactionId.
     * @return Value of property transactionId.
     */
    public java.lang.String getTransactionId() {
        return m_transactionId;
    }
    
    /** Use this method to update the property transactionId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param transactionId New value of property transactionId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTransactionId(java.lang.String transactionId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_transactionId == null ? transactionId == null : m_transactionId.equals(transactionId))
            return;


        transactionId = validateTransactionId(transactionId);
        // .//GEN-END:transactionId_be
        // Add custom code before setting the value//GEN-FIRST:transactionId


        // .//GEN-LAST:transactionId
        // .//GEN-BEGIN:transactionId_1_be
        super.update();
        super.addInitialValue(AuditTransactionObjectMeta.TRANSACTION_ID, m_transactionId);
        m_transactionId = transactionId;
        m_auditTransactionObject = null;
        // .//GEN-END:transactionId_1_be
        // Add custom code after setting the value//GEN-FIRST:transactionId_3


        // .//GEN-LAST:transactionId_3
        // .//GEN-BEGIN:transactionId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setTransactionId() method.
     * @param transactionId New value of property transactionId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTransactionId(java.lang.String transactionId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setTransactionId(transactionId);
    }

    /** Use this method to validate a value for the property transactionId.
     * @param transactionId Value to be validated for the property transactionId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateTransactionId(java.lang.String transactionId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:transactionId_2_be
        // Add custom code before validation//GEN-FIRST:transactionId_1


        // .//GEN-LAST:transactionId_1
        // .//GEN-BEGIN:transactionId_3_be
        transactionId = FieldValidator.validate(transactionId, (StringFieldMetaData) AuditTransactionObjectMeta.META_TRANSACTION_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionObjectMeta.getName(), AuditTransactionObjectMeta.TRANSACTION_ID, transactionId, this.getUOW());

        // .//GEN-END:transactionId_3_be
        // Add custom code after a successful validation//GEN-FIRST:transactionId_2


        // .//GEN-LAST:transactionId_2
        // .//GEN-BEGIN:transactionId_4_be
        return transactionId;
    }
    // .//GEN-END:transactionId_4_be
    // .//GEN-BEGIN:objectName_be
    /** Getter for property objectName.
     * @return Value of property objectName.
     */
    public java.lang.String getObjectName() {
        return m_objectName;
    }
    
    /** Use this method to update the property objectName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param objectName New value of property objectName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setObjectName(java.lang.String objectName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_objectName == null ? objectName == null : m_objectName.equals(objectName))
            return;


        objectName = validateObjectName(objectName);
        // .//GEN-END:objectName_be
        // Add custom code before setting the value//GEN-FIRST:objectName


        // .//GEN-LAST:objectName
        // .//GEN-BEGIN:objectName_1_be
        super.update();
        super.addInitialValue(AuditTransactionObjectMeta.OBJECT_NAME, m_objectName);
        m_objectName = objectName;
        // .//GEN-END:objectName_1_be
        // Add custom code after setting the value//GEN-FIRST:objectName_3


        // .//GEN-LAST:objectName_3
        // .//GEN-BEGIN:objectName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setObjectName() method.
     * @param objectName New value of property objectName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateObjectName(java.lang.String objectName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setObjectName(objectName);
    }

    /** Use this method to validate a value for the property objectName.
     * @param objectName Value to be validated for the property objectName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateObjectName(java.lang.String objectName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:objectName_2_be
        // Add custom code before validation//GEN-FIRST:objectName_1


        // .//GEN-LAST:objectName_1
        // .//GEN-BEGIN:objectName_3_be
        objectName = FieldValidator.validate(objectName, (StringFieldMetaData) AuditTransactionObjectMeta.META_OBJECT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionObjectMeta.getName(), AuditTransactionObjectMeta.OBJECT_NAME, objectName, this.getUOW());

        // .//GEN-END:objectName_3_be
        // Add custom code after a successful validation//GEN-FIRST:objectName_2


        // .//GEN-LAST:objectName_2
        // .//GEN-BEGIN:objectName_4_be
        return objectName;
    }
    // .//GEN-END:objectName_4_be
    // .//GEN-BEGIN:changeType_be
    /** Getter for property changeType.
     * @return Value of property changeType.
     */
    public java.lang.String getChangeType() {
        return m_changeType;
    }
    
    /** Use this method to update the property changeType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param changeType New value of property changeType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setChangeType(java.lang.String changeType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_changeType == null ? changeType == null : m_changeType.equals(changeType))
            return;


        changeType = validateChangeType(changeType);
        // .//GEN-END:changeType_be
        // Add custom code before setting the value//GEN-FIRST:changeType


        // .//GEN-LAST:changeType
        // .//GEN-BEGIN:changeType_1_be
        super.update();
        super.addInitialValue(AuditTransactionObjectMeta.CHANGE_TYPE, m_changeType);
        m_changeType = changeType;
        // .//GEN-END:changeType_1_be
        // Add custom code after setting the value//GEN-FIRST:changeType_3


        // .//GEN-LAST:changeType_3
        // .//GEN-BEGIN:changeType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setChangeType() method.
     * @param changeType New value of property changeType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateChangeType(java.lang.String changeType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setChangeType(changeType);
    }

    /** Use this method to validate a value for the property changeType.
     * @param changeType Value to be validated for the property changeType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateChangeType(java.lang.String changeType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:changeType_2_be
        // Add custom code before validation//GEN-FIRST:changeType_1


        // .//GEN-LAST:changeType_1
        // .//GEN-BEGIN:changeType_3_be
        changeType = FieldValidator.validate(changeType, (StringFieldMetaData) AuditTransactionObjectMeta.META_CHANGE_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionObjectMeta.getName(), AuditTransactionObjectMeta.CHANGE_TYPE, changeType, this.getUOW());

        // .//GEN-END:changeType_3_be
        // Add custom code after a successful validation//GEN-FIRST:changeType_2


        // .//GEN-LAST:changeType_2
        // .//GEN-BEGIN:changeType_4_be
        return changeType;
    }
    // .//GEN-END:changeType_4_be
    // .//GEN-BEGIN:auditTransactionObject_1_be
    /** Returns the related foreign AuditTransaction object.
     * The object is lazy-loaded.
     * @return the related foreign AuditTransaction object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public AuditTransaction getAuditTransactionObject() throws ValidationException, FrameworkException  {
        findAuditTransactionObject(false);
        return m_auditTransactionObject;
    }
    
    /** Finds the related foreign AuditTransaction object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findAuditTransactionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_auditTransactionObject == null && getTransactionId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(AuditTransactionMeta.getName());
                criteria.addCriteria(AuditTransactionMeta.TRANSACTION_ID, getTransactionId());
                if (checkExistenceOnly)
                    criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
                Number count = null;
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Iterator itr = uow.query(criteria).iterator();
                if (itr.hasNext()) {
                    if (checkExistenceOnly)
                        count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                    else
                        m_auditTransactionObject = (AuditTransaction) itr.next();
                }
                if (m_auditTransactionObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(AuditTransactionObjectMeta.META_TRANSACTION_ID.getLabelToken(), new Object[] {AuditTransactionMeta.getLabelToken(), AuditTransactionMeta.META_TRANSACTION_ID.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:auditTransactionObject_1_be
    // .//GEN-BEGIN:auditTransactionFieldArray_1_be
    /** Returns an array of related AuditTransactionField objects.
     * @return an array of related AuditTransactionField objects.
     * @throws FrameworkException Indicates some system error
     */
    public AuditTransactionField[] getAuditTransactionFieldArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            AuditTransactionField[] output = null;
            if (m_auditTransactionFieldCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findAuditTransactionFieldCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_auditTransactionFieldCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_auditTransactionFieldCollection.add(itr.next());
            }

            if (m_auditTransactionFieldCollection != null)
                output = (AuditTransactionField[]) m_auditTransactionFieldCollection.toArray(new AuditTransactionField[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related AuditTransactionField objects.
     * @return a Criteria object for retrieving the related AuditTransactionField objects.
     */
    public Criteria findAuditTransactionFieldCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(AuditTransactionFieldMeta.getName());
        criteria.addCriteria(AuditTransactionFieldMeta.OBJECT_ID, getObjectId());
        // .//GEN-END:auditTransactionFieldArray_1_be
        // Add custom criteria//GEN-FIRST:auditTransactionFieldArray_1


        // .//GEN-LAST:auditTransactionFieldArray_1
        // .//GEN-BEGIN:auditTransactionFieldArray_2_be
        return criteria;
    }
    /** Creates a new AuditTransactionField object and initializes the related fields.
     * This will uncache the related AuditTransactionField objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related AuditTransactionField object with the initialized related fields.
     */
    public AuditTransactionField newAuditTransactionFieldObject()
    throws ValidationException, FrameworkException {
        m_auditTransactionFieldCollection = null;
        AuditTransactionField auditTransactionField = new AuditTransactionField();
        auditTransactionField.setObjectId(getObjectId());
        // .//GEN-END:auditTransactionFieldArray_2_be
        // Add custom code//GEN-FIRST:auditTransactionFieldArray_2


        // .//GEN-LAST:auditTransactionFieldArray_2
        // .//GEN-BEGIN:auditTransactionFieldArray_3_be
        return auditTransactionField;
    }
    // .//GEN-END:auditTransactionFieldArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AuditTransactionObject>");
        buf.append("<objectId>"); if (m_objectId != null) buf.append(m_objectId); buf.append("</objectId>");
        buf.append("<transactionId>"); if (m_transactionId != null) buf.append(m_transactionId); buf.append("</transactionId>");
        buf.append("<objectName>"); if (m_objectName != null) buf.append(m_objectName); buf.append("</objectName>");
        buf.append("<changeType>"); if (m_changeType != null) buf.append(m_changeType); buf.append("</changeType>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</AuditTransactionObject>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        AuditTransactionObject obj = (AuditTransactionObject) super.clone();
        obj.m_auditTransactionObject = null;
        obj.m_auditTransactionFieldCollection = null;
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
        try {
            if (isModified(AuditTransactionObjectMeta.TRANSACTION_ID))
                findAuditTransactionObject(true);
        } catch (ValidationException e) {
            appExps.add(e);
        }
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
        AuditTransactionField[] auditTransactionFieldArray = null;
        try {
            auditTransactionFieldArray = getAuditTransactionFieldArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (auditTransactionFieldArray != null && auditTransactionFieldArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < auditTransactionFieldArray.length; i++)
                    getUOW().delete(auditTransactionFieldArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier transactionId
     * @supplierQualifier transactionId
     * @link association
     */
    /*#AuditTransaction lnkAuditTransaction;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier objectId
     * @supplierQualifier objectId
     * @link composition
     */
    /*#AuditTransactionField lnkAuditTransactionField;*/

    // .//GEN-END:3_be

    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        generateKey();
        super.validate();
    }

    /** Generate the technical-key, if required. */
    public void generateKey() throws ApplicationExceptions, FrameworkException {
        if (getObjectId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(AuditTransactionObjectMeta.OBJECT_ID);
                vg.setLabelToken(AuditTransactionObjectMeta.META_OBJECT_ID.getLabelToken());
                setObjectId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    // .//GEN-LAST:custom
}
