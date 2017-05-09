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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jaffa.components.audit.domain.AuditTransactionObject;
import org.jaffa.components.audit.domain.AuditTransactionObjectMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports

import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_AUDIT_TRANSACTIONS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_AUDIT_TRANSACTIONS")
@SqlResultSetMapping(name="AuditTransaction",entities={@EntityResult(entityClass=AuditTransaction.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class AuditTransaction extends Persistent {

    private static final Logger log = Logger.getLogger(AuditTransaction.class);
    /** Holds value of property transactionId. */
    @XmlElement(name="transactionId")
    @Id
    @Column(name="TRANSACTION_ID")    
    private java.lang.String m_transactionId;

    /** Holds value of property processName. */
    @XmlElement(name="processName")
    @Column(name="PROCESS_NAME")    
    private java.lang.String m_processName;

    /** Holds value of property subProcessName. */
    @XmlElement(name="subProcessName")
    @Column(name="SUB_PROCESS_NAME")    
    private java.lang.String m_subProcessName;

    /** Holds value of property reason. */
    @XmlElement(name="reason")
    @Column(name="REASON")    
    private java.lang.String m_reason;

    /** Holds value of property createdBy. */
    @XmlElement(name="createdBy")
    @Column(name="CREATED_BY")    
    private java.lang.String m_createdBy;

    /** Holds value of property createdOn. */
    @XmlElement(name="createdOn")
    @Type(type="dateTimeType")
    @Column(name="CREATED_ON")    
    private org.jaffa.datatypes.DateTime m_createdOn;

    /** Holds related AuditTransactionObject objects. */
    private transient Collection m_auditTransactionObjectCollection;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AuditTransaction() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String transactionId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(transactionId);
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
    public static AuditTransaction findByPK(UOW uow, java.lang.String transactionId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(transactionId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (AuditTransaction) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String transactionId) {
        Criteria criteria = new Criteria();
        criteria.setTable(AuditTransactionMeta.getName());
        criteria.addCriteria(AuditTransactionMeta.TRANSACTION_ID, transactionId);
        return criteria;
    }
    // .//GEN-END:2_be
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
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTransactionId(java.lang.String transactionId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_transactionId == null ? transactionId == null : m_transactionId.equals(transactionId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        transactionId = validateTransactionId(transactionId);
        // .//GEN-END:transactionId_be
        // Add custom code before setting the value//GEN-FIRST:transactionId


        // .//GEN-LAST:transactionId
        // .//GEN-BEGIN:transactionId_1_be
        super.update();
        super.addInitialValue(AuditTransactionMeta.TRANSACTION_ID, m_transactionId);
        m_transactionId = transactionId;
        // .//GEN-END:transactionId_1_be
        // Add custom code after setting the value//GEN-FIRST:transactionId_3


        // .//GEN-LAST:transactionId_3
        // .//GEN-BEGIN:transactionId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setTransactionId() method.
     * @param transactionId New value of property transactionId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTransactionId(java.lang.String transactionId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        transactionId = FieldValidator.validate(transactionId, (StringFieldMetaData) AuditTransactionMeta.META_TRANSACTION_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.TRANSACTION_ID, transactionId, this.getUOW());

        // .//GEN-END:transactionId_3_be
        // Add custom code after a successful validation//GEN-FIRST:transactionId_2


        // .//GEN-LAST:transactionId_2
        // .//GEN-BEGIN:transactionId_4_be
        return transactionId;
    }
    // .//GEN-END:transactionId_4_be
    // .//GEN-BEGIN:processName_be
    /** Getter for property processName.
     * @return Value of property processName.
     */
    public java.lang.String getProcessName() {
        return m_processName;
    }
    
    /** Use this method to update the property processName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param processName New value of property processName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setProcessName(java.lang.String processName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_processName == null ? processName == null : m_processName.equals(processName))
            return;


        processName = validateProcessName(processName);
        // .//GEN-END:processName_be
        // Add custom code before setting the value//GEN-FIRST:processName


        // .//GEN-LAST:processName
        // .//GEN-BEGIN:processName_1_be
        super.update();
        super.addInitialValue(AuditTransactionMeta.PROCESS_NAME, m_processName);
        m_processName = processName;
        // .//GEN-END:processName_1_be
        // Add custom code after setting the value//GEN-FIRST:processName_3


        // .//GEN-LAST:processName_3
        // .//GEN-BEGIN:processName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setProcessName() method.
     * @param processName New value of property processName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateProcessName(java.lang.String processName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setProcessName(processName);
    }

    /** Use this method to validate a value for the property processName.
     * @param processName Value to be validated for the property processName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateProcessName(java.lang.String processName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:processName_2_be
        // Add custom code before validation//GEN-FIRST:processName_1


        // .//GEN-LAST:processName_1
        // .//GEN-BEGIN:processName_3_be
        processName = FieldValidator.validate(processName, (StringFieldMetaData) AuditTransactionMeta.META_PROCESS_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.PROCESS_NAME, processName, this.getUOW());

        // .//GEN-END:processName_3_be
        // Add custom code after a successful validation//GEN-FIRST:processName_2


        // .//GEN-LAST:processName_2
        // .//GEN-BEGIN:processName_4_be
        return processName;
    }
    // .//GEN-END:processName_4_be
    // .//GEN-BEGIN:subProcessName_be
    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public java.lang.String getSubProcessName() {
        return m_subProcessName;
    }
    
    /** Use this method to update the property subProcessName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param subProcessName New value of property subProcessName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSubProcessName(java.lang.String subProcessName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_subProcessName == null ? subProcessName == null : m_subProcessName.equals(subProcessName))
            return;


        subProcessName = validateSubProcessName(subProcessName);
        // .//GEN-END:subProcessName_be
        // Add custom code before setting the value//GEN-FIRST:subProcessName


        // .//GEN-LAST:subProcessName
        // .//GEN-BEGIN:subProcessName_1_be
        super.update();
        super.addInitialValue(AuditTransactionMeta.SUB_PROCESS_NAME, m_subProcessName);
        m_subProcessName = subProcessName;
        // .//GEN-END:subProcessName_1_be
        // Add custom code after setting the value//GEN-FIRST:subProcessName_3


        // .//GEN-LAST:subProcessName_3
        // .//GEN-BEGIN:subProcessName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSubProcessName() method.
     * @param subProcessName New value of property subProcessName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSubProcessName(java.lang.String subProcessName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSubProcessName(subProcessName);
    }

    /** Use this method to validate a value for the property subProcessName.
     * @param subProcessName Value to be validated for the property subProcessName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSubProcessName(java.lang.String subProcessName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:subProcessName_2_be
        // Add custom code before validation//GEN-FIRST:subProcessName_1


        // .//GEN-LAST:subProcessName_1
        // .//GEN-BEGIN:subProcessName_3_be
        subProcessName = FieldValidator.validate(subProcessName, (StringFieldMetaData) AuditTransactionMeta.META_SUB_PROCESS_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.SUB_PROCESS_NAME, subProcessName, this.getUOW());

        // .//GEN-END:subProcessName_3_be
        // Add custom code after a successful validation//GEN-FIRST:subProcessName_2


        // .//GEN-LAST:subProcessName_2
        // .//GEN-BEGIN:subProcessName_4_be
        return subProcessName;
    }
    // .//GEN-END:subProcessName_4_be
    // .//GEN-BEGIN:reason_be
    /** Getter for property reason.
     * @return Value of property reason.
     */
    public java.lang.String getReason() {
        return m_reason;
    }
    
    /** Use this method to update the property reason.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param reason New value of property reason.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setReason(java.lang.String reason)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_reason == null ? reason == null : m_reason.equals(reason))
            return;


        reason = validateReason(reason);
        // .//GEN-END:reason_be
        // Add custom code before setting the value//GEN-FIRST:reason


        // .//GEN-LAST:reason
        // .//GEN-BEGIN:reason_1_be
        super.update();
        super.addInitialValue(AuditTransactionMeta.REASON, m_reason);
        m_reason = reason;
        // .//GEN-END:reason_1_be
        // Add custom code after setting the value//GEN-FIRST:reason_3


        // .//GEN-LAST:reason_3
        // .//GEN-BEGIN:reason_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setReason() method.
     * @param reason New value of property reason.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateReason(java.lang.String reason)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setReason(reason);
    }

    /** Use this method to validate a value for the property reason.
     * @param reason Value to be validated for the property reason.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateReason(java.lang.String reason)
    throws ValidationException, FrameworkException {
        // .//GEN-END:reason_2_be
        // Add custom code before validation//GEN-FIRST:reason_1


        // .//GEN-LAST:reason_1
        // .//GEN-BEGIN:reason_3_be
        reason = FieldValidator.validate(reason, (StringFieldMetaData) AuditTransactionMeta.META_REASON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.REASON, reason, this.getUOW());

        // .//GEN-END:reason_3_be
        // Add custom code after a successful validation//GEN-FIRST:reason_2


        // .//GEN-LAST:reason_2
        // .//GEN-BEGIN:reason_4_be
        return reason;
    }
    // .//GEN-END:reason_4_be
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
        super.addInitialValue(AuditTransactionMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) AuditTransactionMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.CREATED_BY, createdBy, this.getUOW());

        // .//GEN-END:createdBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdBy_2


        // .//GEN-LAST:createdBy_2
        // .//GEN-BEGIN:createdBy_4_be
        return createdBy;
    }
    // .//GEN-END:createdBy_4_be
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
        super.addInitialValue(AuditTransactionMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) AuditTransactionMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionMeta.getName(), AuditTransactionMeta.CREATED_ON, createdOn, this.getUOW());

        // .//GEN-END:createdOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdOn_2


        // .//GEN-LAST:createdOn_2
        // .//GEN-BEGIN:createdOn_4_be
        return createdOn;
    }
    // .//GEN-END:createdOn_4_be
    // .//GEN-BEGIN:auditTransactionObjectArray_1_be
    /** Returns an array of related AuditTransactionObject objects.
     * @return an array of related AuditTransactionObject objects.
     * @throws FrameworkException Indicates some system error
     */
    public AuditTransactionObject[] getAuditTransactionObjectArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            AuditTransactionObject[] output = null;
            if (m_auditTransactionObjectCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findAuditTransactionObjectCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_auditTransactionObjectCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_auditTransactionObjectCollection.add(itr.next());
            }

            if (m_auditTransactionObjectCollection != null)
                output = (AuditTransactionObject[]) m_auditTransactionObjectCollection.toArray(new AuditTransactionObject[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related AuditTransactionObject objects.
     * @return a Criteria object for retrieving the related AuditTransactionObject objects.
     */
    public Criteria findAuditTransactionObjectCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(AuditTransactionObjectMeta.getName());
        criteria.addCriteria(AuditTransactionObjectMeta.TRANSACTION_ID, getTransactionId());
        // .//GEN-END:auditTransactionObjectArray_1_be
        // Add custom criteria//GEN-FIRST:auditTransactionObjectArray_1


        // .//GEN-LAST:auditTransactionObjectArray_1
        // .//GEN-BEGIN:auditTransactionObjectArray_2_be
        return criteria;
    }
    /** Creates a new AuditTransactionObject object and initializes the related fields.
     * This will uncache the related AuditTransactionObject objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related AuditTransactionObject object with the initialized related fields.
     */
    public AuditTransactionObject newAuditTransactionObjectObject()
    throws ValidationException, FrameworkException {
        m_auditTransactionObjectCollection = null;
        AuditTransactionObject auditTransactionObject = new AuditTransactionObject();
        auditTransactionObject.setTransactionId(getTransactionId());
        // .//GEN-END:auditTransactionObjectArray_2_be
        // Add custom code//GEN-FIRST:auditTransactionObjectArray_2


        // .//GEN-LAST:auditTransactionObjectArray_2
        // .//GEN-BEGIN:auditTransactionObjectArray_3_be
        return auditTransactionObject;
    }
    // .//GEN-END:auditTransactionObjectArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AuditTransaction>");
        buf.append("<transactionId>"); if (m_transactionId != null) buf.append(m_transactionId); buf.append("</transactionId>");
        buf.append("<processName>"); if (m_processName != null) buf.append(m_processName); buf.append("</processName>");
        buf.append("<subProcessName>"); if (m_subProcessName != null) buf.append(m_subProcessName); buf.append("</subProcessName>");
        buf.append("<reason>"); if (m_reason != null) buf.append(m_reason); buf.append("</reason>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</AuditTransaction>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        AuditTransaction obj = (AuditTransaction) super.clone();
        obj.m_auditTransactionObjectCollection = null;
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
        AuditTransactionObject[] auditTransactionObjectArray = null;
        try {
            auditTransactionObjectArray = getAuditTransactionObjectArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (auditTransactionObjectArray != null && auditTransactionObjectArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < auditTransactionObjectArray.length; i++)
                    getUOW().delete(auditTransactionObjectArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier transactionId
     * @supplierQualifier transactionId
     * @link composition
     */
    /*#AuditTransactionObject lnkAuditTransactionObject;*/

    // .//GEN-END:3_be
    // .//GEN-BEGIN:preAdd_1_be
    /** This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * It ensures that the primary-key is unique and that the foreign-keys are valid.
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        try {
            if (getCreatedBy() == null && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setCreatedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        }
        try {
            if (getCreatedOn() == null)
                setCreatedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        }
        // .//GEN-END:preAdd_1_be
        // Add custom code//GEN-FIRST:preAdd_1


        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.preAdd();
    }
    // .//GEN-END:preAdd_2_be

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
        if (getTransactionId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(AuditTransactionMeta.TRANSACTION_ID);
                vg.setLabelToken(AuditTransactionMeta.META_TRANSACTION_ID.getLabelToken());
                setTransactionId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    // .//GEN-LAST:custom
}
