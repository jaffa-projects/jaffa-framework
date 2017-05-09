// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.messaging.domain;

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
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;


// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_BUSINESS_EVENT_LOGS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_BUSINESS_EVENT_LOGS")
@SqlResultSetMapping(name="BusinessEventLog",entities={@EntityResult(entityClass=BusinessEventLog.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class BusinessEventLog extends Persistent {

    private static final Logger log = Logger.getLogger(BusinessEventLog.class);
    /** Holds value of property logId. */
    @XmlElement(name="logId")
    @Id
    @Column(name="LOG_ID")    
    private java.lang.String m_logId;

    /** Holds value of property correlationType. */
    @XmlElement(name="correlationType")
    @Column(name="CORRELATION_TYPE")    
    private java.lang.String m_correlationType;

    /** Holds value of property correlationKey1. */
    @XmlElement(name="correlationKey1")
    @Column(name="CORRELATION_KEY1")    
    private java.lang.String m_correlationKey1;

    /** Holds value of property correlationKey2. */
    @XmlElement(name="correlationKey2")
    @Column(name="CORRELATION_KEY2")    
    private java.lang.String m_correlationKey2;

    /** Holds value of property correlationKey3. */
    @XmlElement(name="correlationKey3")
    @Column(name="CORRELATION_KEY3")    
    private java.lang.String m_correlationKey3;

    /** Holds value of property scheduledTaskId. */
    @XmlElement(name="scheduledTaskId")
    @Column(name="SCHEDULED_TASK_ID")    
    private java.lang.String m_scheduledTaskId;

    /** Holds value of property messageId. */
    @XmlElement(name="messageId")
    @Column(name="MESSAGE_ID")    
    private java.lang.String m_messageId;

    /** Holds value of property loggedOn. */
    @XmlElement(name="loggedOn")
    @Type(type="dateTimeType")
    @Column(name="LOGGED_ON")    
    private org.jaffa.datatypes.DateTime m_loggedOn;

    /** Holds value of property loggedBy. */
    @XmlElement(name="loggedBy")
    @Column(name="LOGGED_BY")    
    private java.lang.String m_loggedBy;

    /** Holds value of property processName. */
    @XmlElement(name="processName")
    @Column(name="PROCESS_NAME")    
    private java.lang.String m_processName;

    /** Holds value of property subProcessName. */
    @XmlElement(name="subProcessName")
    @Column(name="SUB_PROCESS_NAME")    
    private java.lang.String m_subProcessName;

    /** Holds value of property messageType. */
    @XmlElement(name="messageType")
    @Column(name="MESSAGE_TYPE")    
    private java.lang.String m_messageType;

    /** Holds value of property messageText. */
    @XmlElement(name="messageText")
    @Column(name="MESSAGE_TEXT")    
    private java.lang.String m_messageText;

    /** Holds value of property sourceClass. */
    @XmlElement(name="sourceClass")
    @Column(name="SOURCE_CLASS")    
    private java.lang.String m_sourceClass;

    /** Holds value of property sourceMethod. */
    @XmlElement(name="sourceMethod")
    @Column(name="SOURCE_METHOD")    
    private java.lang.String m_sourceMethod;

    /** Holds value of property sourceLine. */
    @XmlElement(name="sourceLine")
    @Column(name="SOURCE_LINE")    
    private java.lang.Long m_sourceLine;

    /** Holds value of property stackTrace. */
    @XmlElement(name="stackTrace")
    @Column(name="STACK_TRACE")    
    private java.lang.String m_stackTrace;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public BusinessEventLog() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String logId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(logId);
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
    public static BusinessEventLog findByPK(UOW uow, java.lang.String logId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(logId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (BusinessEventLog) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String logId) {
        Criteria criteria = new Criteria();
        criteria.setTable(BusinessEventLogMeta.getName());
        criteria.addCriteria(BusinessEventLogMeta.LOG_ID, logId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:logId_be
    /** Getter for property logId.
     * @return Value of property logId.
     */
    public java.lang.String getLogId() {
        return m_logId;
    }
    
    /** Use this method to update the property logId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param logId New value of property logId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLogId(java.lang.String logId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_logId == null ? logId == null : m_logId.equals(logId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        logId = validateLogId(logId);
        // .//GEN-END:logId_be
        // Add custom code before setting the value//GEN-FIRST:logId


        // .//GEN-LAST:logId
        // .//GEN-BEGIN:logId_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.LOG_ID, m_logId);
        m_logId = logId;
        // .//GEN-END:logId_1_be
        // Add custom code after setting the value//GEN-FIRST:logId_3


        // .//GEN-LAST:logId_3
        // .//GEN-BEGIN:logId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLogId() method.
     * @param logId New value of property logId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLogId(java.lang.String logId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLogId(logId);
    }

    /** Use this method to validate a value for the property logId.
     * @param logId Value to be validated for the property logId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLogId(java.lang.String logId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:logId_2_be
        // Add custom code before validation//GEN-FIRST:logId_1


        // .//GEN-LAST:logId_1
        // .//GEN-BEGIN:logId_3_be
        logId = FieldValidator.validate(logId, (StringFieldMetaData) BusinessEventLogMeta.META_LOG_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.LOG_ID, logId, this.getUOW());

        // .//GEN-END:logId_3_be
        // Add custom code after a successful validation//GEN-FIRST:logId_2


        // .//GEN-LAST:logId_2
        // .//GEN-BEGIN:logId_4_be
        return logId;
    }
    // .//GEN-END:logId_4_be
    // .//GEN-BEGIN:correlationType_be
    /** Getter for property correlationType.
     * @return Value of property correlationType.
     */
    public java.lang.String getCorrelationType() {
        return m_correlationType;
    }
    
    /** Use this method to update the property correlationType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param correlationType New value of property correlationType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCorrelationType(java.lang.String correlationType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_correlationType == null ? correlationType == null : m_correlationType.equals(correlationType))
            return;


        correlationType = validateCorrelationType(correlationType);
        // .//GEN-END:correlationType_be
        // Add custom code before setting the value//GEN-FIRST:correlationType


        // .//GEN-LAST:correlationType
        // .//GEN-BEGIN:correlationType_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.CORRELATION_TYPE, m_correlationType);
        m_correlationType = correlationType;
        // .//GEN-END:correlationType_1_be
        // Add custom code after setting the value//GEN-FIRST:correlationType_3


        // .//GEN-LAST:correlationType_3
        // .//GEN-BEGIN:correlationType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCorrelationType() method.
     * @param correlationType New value of property correlationType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCorrelationType(java.lang.String correlationType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCorrelationType(correlationType);
    }

    /** Use this method to validate a value for the property correlationType.
     * @param correlationType Value to be validated for the property correlationType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCorrelationType(java.lang.String correlationType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:correlationType_2_be
        // Add custom code before validation//GEN-FIRST:correlationType_1


        // .//GEN-LAST:correlationType_1
        // .//GEN-BEGIN:correlationType_3_be
        correlationType = FieldValidator.validate(correlationType, (StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.CORRELATION_TYPE, correlationType, this.getUOW());

        // .//GEN-END:correlationType_3_be
        // Add custom code after a successful validation//GEN-FIRST:correlationType_2


        // .//GEN-LAST:correlationType_2
        // .//GEN-BEGIN:correlationType_4_be
        return correlationType;
    }
    // .//GEN-END:correlationType_4_be
    // .//GEN-BEGIN:correlationKey1_be
    /** Getter for property correlationKey1.
     * @return Value of property correlationKey1.
     */
    public java.lang.String getCorrelationKey1() {
        return m_correlationKey1;
    }
    
    /** Use this method to update the property correlationKey1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param correlationKey1 New value of property correlationKey1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCorrelationKey1(java.lang.String correlationKey1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_correlationKey1 == null ? correlationKey1 == null : m_correlationKey1.equals(correlationKey1))
            return;


        correlationKey1 = validateCorrelationKey1(correlationKey1);
        // .//GEN-END:correlationKey1_be
        // Add custom code before setting the value//GEN-FIRST:correlationKey1


        // .//GEN-LAST:correlationKey1
        // .//GEN-BEGIN:correlationKey1_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.CORRELATION_KEY1, m_correlationKey1);
        m_correlationKey1 = correlationKey1;
        // .//GEN-END:correlationKey1_1_be
        // Add custom code after setting the value//GEN-FIRST:correlationKey1_3


        // .//GEN-LAST:correlationKey1_3
        // .//GEN-BEGIN:correlationKey1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCorrelationKey1() method.
     * @param correlationKey1 New value of property correlationKey1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCorrelationKey1(java.lang.String correlationKey1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCorrelationKey1(correlationKey1);
    }

    /** Use this method to validate a value for the property correlationKey1.
     * @param correlationKey1 Value to be validated for the property correlationKey1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCorrelationKey1(java.lang.String correlationKey1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:correlationKey1_2_be
        // Add custom code before validation//GEN-FIRST:correlationKey1_1


        // .//GEN-LAST:correlationKey1_1
        // .//GEN-BEGIN:correlationKey1_3_be
        correlationKey1 = FieldValidator.validate(correlationKey1, (StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.CORRELATION_KEY1, correlationKey1, this.getUOW());

        // .//GEN-END:correlationKey1_3_be
        // Add custom code after a successful validation//GEN-FIRST:correlationKey1_2


        // .//GEN-LAST:correlationKey1_2
        // .//GEN-BEGIN:correlationKey1_4_be
        return correlationKey1;
    }
    // .//GEN-END:correlationKey1_4_be
    // .//GEN-BEGIN:correlationKey2_be
    /** Getter for property correlationKey2.
     * @return Value of property correlationKey2.
     */
    public java.lang.String getCorrelationKey2() {
        return m_correlationKey2;
    }
    
    /** Use this method to update the property correlationKey2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param correlationKey2 New value of property correlationKey2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCorrelationKey2(java.lang.String correlationKey2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_correlationKey2 == null ? correlationKey2 == null : m_correlationKey2.equals(correlationKey2))
            return;


        correlationKey2 = validateCorrelationKey2(correlationKey2);
        // .//GEN-END:correlationKey2_be
        // Add custom code before setting the value//GEN-FIRST:correlationKey2


        // .//GEN-LAST:correlationKey2
        // .//GEN-BEGIN:correlationKey2_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.CORRELATION_KEY2, m_correlationKey2);
        m_correlationKey2 = correlationKey2;
        // .//GEN-END:correlationKey2_1_be
        // Add custom code after setting the value//GEN-FIRST:correlationKey2_3


        // .//GEN-LAST:correlationKey2_3
        // .//GEN-BEGIN:correlationKey2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCorrelationKey2() method.
     * @param correlationKey2 New value of property correlationKey2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCorrelationKey2(java.lang.String correlationKey2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCorrelationKey2(correlationKey2);
    }

    /** Use this method to validate a value for the property correlationKey2.
     * @param correlationKey2 Value to be validated for the property correlationKey2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCorrelationKey2(java.lang.String correlationKey2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:correlationKey2_2_be
        // Add custom code before validation//GEN-FIRST:correlationKey2_1


        // .//GEN-LAST:correlationKey2_1
        // .//GEN-BEGIN:correlationKey2_3_be
        correlationKey2 = FieldValidator.validate(correlationKey2, (StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.CORRELATION_KEY2, correlationKey2, this.getUOW());

        // .//GEN-END:correlationKey2_3_be
        // Add custom code after a successful validation//GEN-FIRST:correlationKey2_2


        // .//GEN-LAST:correlationKey2_2
        // .//GEN-BEGIN:correlationKey2_4_be
        return correlationKey2;
    }
    // .//GEN-END:correlationKey2_4_be
    // .//GEN-BEGIN:correlationKey3_be
    /** Getter for property correlationKey3.
     * @return Value of property correlationKey3.
     */
    public java.lang.String getCorrelationKey3() {
        return m_correlationKey3;
    }
    
    /** Use this method to update the property correlationKey3.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param correlationKey3 New value of property correlationKey3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCorrelationKey3(java.lang.String correlationKey3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_correlationKey3 == null ? correlationKey3 == null : m_correlationKey3.equals(correlationKey3))
            return;


        correlationKey3 = validateCorrelationKey3(correlationKey3);
        // .//GEN-END:correlationKey3_be
        // Add custom code before setting the value//GEN-FIRST:correlationKey3


        // .//GEN-LAST:correlationKey3
        // .//GEN-BEGIN:correlationKey3_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.CORRELATION_KEY3, m_correlationKey3);
        m_correlationKey3 = correlationKey3;
        // .//GEN-END:correlationKey3_1_be
        // Add custom code after setting the value//GEN-FIRST:correlationKey3_3


        // .//GEN-LAST:correlationKey3_3
        // .//GEN-BEGIN:correlationKey3_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCorrelationKey3() method.
     * @param correlationKey3 New value of property correlationKey3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCorrelationKey3(java.lang.String correlationKey3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCorrelationKey3(correlationKey3);
    }

    /** Use this method to validate a value for the property correlationKey3.
     * @param correlationKey3 Value to be validated for the property correlationKey3.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCorrelationKey3(java.lang.String correlationKey3)
    throws ValidationException, FrameworkException {
        // .//GEN-END:correlationKey3_2_be
        // Add custom code before validation//GEN-FIRST:correlationKey3_1


        // .//GEN-LAST:correlationKey3_1
        // .//GEN-BEGIN:correlationKey3_3_be
        correlationKey3 = FieldValidator.validate(correlationKey3, (StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY3, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.CORRELATION_KEY3, correlationKey3, this.getUOW());

        // .//GEN-END:correlationKey3_3_be
        // Add custom code after a successful validation//GEN-FIRST:correlationKey3_2


        // .//GEN-LAST:correlationKey3_2
        // .//GEN-BEGIN:correlationKey3_4_be
        return correlationKey3;
    }
    // .//GEN-END:correlationKey3_4_be
    // .//GEN-BEGIN:scheduledTaskId_be
    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public java.lang.String getScheduledTaskId() {
        return m_scheduledTaskId;
    }
    
    /** Use this method to update the property scheduledTaskId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param scheduledTaskId New value of property scheduledTaskId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setScheduledTaskId(java.lang.String scheduledTaskId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_scheduledTaskId == null ? scheduledTaskId == null : m_scheduledTaskId.equals(scheduledTaskId))
            return;


        scheduledTaskId = validateScheduledTaskId(scheduledTaskId);
        // .//GEN-END:scheduledTaskId_be
        // Add custom code before setting the value//GEN-FIRST:scheduledTaskId


        // .//GEN-LAST:scheduledTaskId
        // .//GEN-BEGIN:scheduledTaskId_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.SCHEDULED_TASK_ID, m_scheduledTaskId);
        m_scheduledTaskId = scheduledTaskId;
        // .//GEN-END:scheduledTaskId_1_be
        // Add custom code after setting the value//GEN-FIRST:scheduledTaskId_3


        // .//GEN-LAST:scheduledTaskId_3
        // .//GEN-BEGIN:scheduledTaskId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setScheduledTaskId() method.
     * @param scheduledTaskId New value of property scheduledTaskId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateScheduledTaskId(java.lang.String scheduledTaskId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setScheduledTaskId(scheduledTaskId);
    }

    /** Use this method to validate a value for the property scheduledTaskId.
     * @param scheduledTaskId Value to be validated for the property scheduledTaskId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateScheduledTaskId(java.lang.String scheduledTaskId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:scheduledTaskId_2_be
        // Add custom code before validation//GEN-FIRST:scheduledTaskId_1


        // .//GEN-LAST:scheduledTaskId_1
        // .//GEN-BEGIN:scheduledTaskId_3_be
        scheduledTaskId = FieldValidator.validate(scheduledTaskId, (StringFieldMetaData) BusinessEventLogMeta.META_SCHEDULED_TASK_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.SCHEDULED_TASK_ID, scheduledTaskId, this.getUOW());

        // .//GEN-END:scheduledTaskId_3_be
        // Add custom code after a successful validation//GEN-FIRST:scheduledTaskId_2


        // .//GEN-LAST:scheduledTaskId_2
        // .//GEN-BEGIN:scheduledTaskId_4_be
        return scheduledTaskId;
    }
    // .//GEN-END:scheduledTaskId_4_be
    // .//GEN-BEGIN:messageId_be
    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public java.lang.String getMessageId() {
        return m_messageId;
    }
    
    /** Use this method to update the property messageId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param messageId New value of property messageId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setMessageId(java.lang.String messageId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_messageId == null ? messageId == null : m_messageId.equals(messageId))
            return;


        messageId = validateMessageId(messageId);
        // .//GEN-END:messageId_be
        // Add custom code before setting the value//GEN-FIRST:messageId


        // .//GEN-LAST:messageId
        // .//GEN-BEGIN:messageId_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.MESSAGE_ID, m_messageId);
        m_messageId = messageId;
        // .//GEN-END:messageId_1_be
        // Add custom code after setting the value//GEN-FIRST:messageId_3


        // .//GEN-LAST:messageId_3
        // .//GEN-BEGIN:messageId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setMessageId() method.
     * @param messageId New value of property messageId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateMessageId(java.lang.String messageId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setMessageId(messageId);
    }

    /** Use this method to validate a value for the property messageId.
     * @param messageId Value to be validated for the property messageId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateMessageId(java.lang.String messageId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:messageId_2_be
        // Add custom code before validation//GEN-FIRST:messageId_1


        // .//GEN-LAST:messageId_1
        // .//GEN-BEGIN:messageId_3_be
        messageId = FieldValidator.validate(messageId, (StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.MESSAGE_ID, messageId, this.getUOW());

        // .//GEN-END:messageId_3_be
        // Add custom code after a successful validation//GEN-FIRST:messageId_2


        // .//GEN-LAST:messageId_2
        // .//GEN-BEGIN:messageId_4_be
        return messageId;
    }
    // .//GEN-END:messageId_4_be
    // .//GEN-BEGIN:loggedOn_be
    /** Getter for property loggedOn.
     * @return Value of property loggedOn.
     */
    public org.jaffa.datatypes.DateTime getLoggedOn() {
        return m_loggedOn;
    }
    
    /** Use this method to update the property loggedOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param loggedOn New value of property loggedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLoggedOn(org.jaffa.datatypes.DateTime loggedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_loggedOn == null ? loggedOn == null : m_loggedOn.equals(loggedOn))
            return;


        loggedOn = validateLoggedOn(loggedOn);
        // .//GEN-END:loggedOn_be
        // Add custom code before setting the value//GEN-FIRST:loggedOn


        // .//GEN-LAST:loggedOn
        // .//GEN-BEGIN:loggedOn_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.LOGGED_ON, m_loggedOn);
        m_loggedOn = loggedOn;
        // .//GEN-END:loggedOn_1_be
        // Add custom code after setting the value//GEN-FIRST:loggedOn_3


        // .//GEN-LAST:loggedOn_3
        // .//GEN-BEGIN:loggedOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLoggedOn() method.
     * @param loggedOn New value of property loggedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLoggedOn(org.jaffa.datatypes.DateTime loggedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLoggedOn(loggedOn);
    }

    /** Use this method to validate a value for the property loggedOn.
     * @param loggedOn Value to be validated for the property loggedOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateLoggedOn(org.jaffa.datatypes.DateTime loggedOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:loggedOn_2_be
        // Add custom code before validation//GEN-FIRST:loggedOn_1


        // .//GEN-LAST:loggedOn_1
        // .//GEN-BEGIN:loggedOn_3_be
        loggedOn = FieldValidator.validate(loggedOn, (DateTimeFieldMetaData) BusinessEventLogMeta.META_LOGGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.LOGGED_ON, loggedOn, this.getUOW());

        // .//GEN-END:loggedOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:loggedOn_2


        // .//GEN-LAST:loggedOn_2
        // .//GEN-BEGIN:loggedOn_4_be
        return loggedOn;
    }
    // .//GEN-END:loggedOn_4_be
    // .//GEN-BEGIN:loggedBy_be
    /** Getter for property loggedBy.
     * @return Value of property loggedBy.
     */
    public java.lang.String getLoggedBy() {
        return m_loggedBy;
    }
    
    /** Use this method to update the property loggedBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param loggedBy New value of property loggedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLoggedBy(java.lang.String loggedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_loggedBy == null ? loggedBy == null : m_loggedBy.equals(loggedBy))
            return;


        loggedBy = validateLoggedBy(loggedBy);
        // .//GEN-END:loggedBy_be
        // Add custom code before setting the value//GEN-FIRST:loggedBy


        // .//GEN-LAST:loggedBy
        // .//GEN-BEGIN:loggedBy_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.LOGGED_BY, m_loggedBy);
        m_loggedBy = loggedBy;
        // .//GEN-END:loggedBy_1_be
        // Add custom code after setting the value//GEN-FIRST:loggedBy_3


        // .//GEN-LAST:loggedBy_3
        // .//GEN-BEGIN:loggedBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLoggedBy() method.
     * @param loggedBy New value of property loggedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLoggedBy(java.lang.String loggedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLoggedBy(loggedBy);
    }

    /** Use this method to validate a value for the property loggedBy.
     * @param loggedBy Value to be validated for the property loggedBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLoggedBy(java.lang.String loggedBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:loggedBy_2_be
        // Add custom code before validation//GEN-FIRST:loggedBy_1


        // .//GEN-LAST:loggedBy_1
        // .//GEN-BEGIN:loggedBy_3_be
        loggedBy = FieldValidator.validate(loggedBy, (StringFieldMetaData) BusinessEventLogMeta.META_LOGGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.LOGGED_BY, loggedBy, this.getUOW());

        // .//GEN-END:loggedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:loggedBy_2


        // .//GEN-LAST:loggedBy_2
        // .//GEN-BEGIN:loggedBy_4_be
        return loggedBy;
    }
    // .//GEN-END:loggedBy_4_be
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
        super.addInitialValue(BusinessEventLogMeta.PROCESS_NAME, m_processName);
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
        processName = FieldValidator.validate(processName, (StringFieldMetaData) BusinessEventLogMeta.META_PROCESS_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.PROCESS_NAME, processName, this.getUOW());

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
        super.addInitialValue(BusinessEventLogMeta.SUB_PROCESS_NAME, m_subProcessName);
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
        subProcessName = FieldValidator.validate(subProcessName, (StringFieldMetaData) BusinessEventLogMeta.META_SUB_PROCESS_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.SUB_PROCESS_NAME, subProcessName, this.getUOW());

        // .//GEN-END:subProcessName_3_be
        // Add custom code after a successful validation//GEN-FIRST:subProcessName_2


        // .//GEN-LAST:subProcessName_2
        // .//GEN-BEGIN:subProcessName_4_be
        return subProcessName;
    }
    // .//GEN-END:subProcessName_4_be
    // .//GEN-BEGIN:messageType_be
    /** Getter for property messageType.
     * @return Value of property messageType.
     */
    public java.lang.String getMessageType() {
        return m_messageType;
    }
    
    /** Use this method to update the property messageType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param messageType New value of property messageType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setMessageType(java.lang.String messageType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_messageType == null ? messageType == null : m_messageType.equals(messageType))
            return;


        messageType = validateMessageType(messageType);
        // .//GEN-END:messageType_be
        // Add custom code before setting the value//GEN-FIRST:messageType


        // .//GEN-LAST:messageType
        // .//GEN-BEGIN:messageType_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.MESSAGE_TYPE, m_messageType);
        m_messageType = messageType;
        // .//GEN-END:messageType_1_be
        // Add custom code after setting the value//GEN-FIRST:messageType_3


        // .//GEN-LAST:messageType_3
        // .//GEN-BEGIN:messageType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setMessageType() method.
     * @param messageType New value of property messageType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateMessageType(java.lang.String messageType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setMessageType(messageType);
    }

    /** Use this method to validate a value for the property messageType.
     * @param messageType Value to be validated for the property messageType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateMessageType(java.lang.String messageType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:messageType_2_be
        // Add custom code before validation//GEN-FIRST:messageType_1


        // .//GEN-LAST:messageType_1
        // .//GEN-BEGIN:messageType_3_be
        messageType = FieldValidator.validate(messageType, (StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.MESSAGE_TYPE, messageType, this.getUOW());

        // .//GEN-END:messageType_3_be
        // Add custom code after a successful validation//GEN-FIRST:messageType_2


        // .//GEN-LAST:messageType_2
        // .//GEN-BEGIN:messageType_4_be
        return messageType;
    }
    // .//GEN-END:messageType_4_be
    // .//GEN-BEGIN:messageText_be
    /** Getter for property messageText.
     * @return Value of property messageText.
     */
    public java.lang.String getMessageText() {
        return m_messageText;
    }
    
    /** Use this method to update the property messageText.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param messageText New value of property messageText.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setMessageText(java.lang.String messageText)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_messageText == null ? messageText == null : m_messageText.equals(messageText))
            return;


        messageText = validateMessageText(messageText);
        // .//GEN-END:messageText_be
        // Add custom code before setting the value//GEN-FIRST:messageText


        // .//GEN-LAST:messageText
        // .//GEN-BEGIN:messageText_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.MESSAGE_TEXT, m_messageText);
        m_messageText = messageText;
        // .//GEN-END:messageText_1_be
        // Add custom code after setting the value//GEN-FIRST:messageText_3


        // .//GEN-LAST:messageText_3
        // .//GEN-BEGIN:messageText_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setMessageText() method.
     * @param messageText New value of property messageText.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateMessageText(java.lang.String messageText)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setMessageText(messageText);
    }

    /** Use this method to validate a value for the property messageText.
     * @param messageText Value to be validated for the property messageText.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateMessageText(java.lang.String messageText)
    throws ValidationException, FrameworkException {
        // .//GEN-END:messageText_2_be
        // Add custom code before validation//GEN-FIRST:messageText_1


        // .//GEN-LAST:messageText_1
        // .//GEN-BEGIN:messageText_3_be
        messageText = FieldValidator.validate(messageText, (StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_TEXT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.MESSAGE_TEXT, messageText, this.getUOW());

        // .//GEN-END:messageText_3_be
        // Add custom code after a successful validation//GEN-FIRST:messageText_2


        // .//GEN-LAST:messageText_2
        // .//GEN-BEGIN:messageText_4_be
        return messageText;
    }
    // .//GEN-END:messageText_4_be
    // .//GEN-BEGIN:sourceClass_be
    /** Getter for property sourceClass.
     * @return Value of property sourceClass.
     */
    public java.lang.String getSourceClass() {
        return m_sourceClass;
    }
    
    /** Use this method to update the property sourceClass.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sourceClass New value of property sourceClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSourceClass(java.lang.String sourceClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_sourceClass == null ? sourceClass == null : m_sourceClass.equals(sourceClass))
            return;


        sourceClass = validateSourceClass(sourceClass);
        // .//GEN-END:sourceClass_be
        // Add custom code before setting the value//GEN-FIRST:sourceClass


        // .//GEN-LAST:sourceClass
        // .//GEN-BEGIN:sourceClass_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.SOURCE_CLASS, m_sourceClass);
        m_sourceClass = sourceClass;
        // .//GEN-END:sourceClass_1_be
        // Add custom code after setting the value//GEN-FIRST:sourceClass_3


        // .//GEN-LAST:sourceClass_3
        // .//GEN-BEGIN:sourceClass_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSourceClass() method.
     * @param sourceClass New value of property sourceClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSourceClass(java.lang.String sourceClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSourceClass(sourceClass);
    }

    /** Use this method to validate a value for the property sourceClass.
     * @param sourceClass Value to be validated for the property sourceClass.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSourceClass(java.lang.String sourceClass)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sourceClass_2_be
        // Add custom code before validation//GEN-FIRST:sourceClass_1


        // .//GEN-LAST:sourceClass_1
        // .//GEN-BEGIN:sourceClass_3_be
        sourceClass = FieldValidator.validate(sourceClass, (StringFieldMetaData) BusinessEventLogMeta.META_SOURCE_CLASS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.SOURCE_CLASS, sourceClass, this.getUOW());

        // .//GEN-END:sourceClass_3_be
        // Add custom code after a successful validation//GEN-FIRST:sourceClass_2


        // .//GEN-LAST:sourceClass_2
        // .//GEN-BEGIN:sourceClass_4_be
        return sourceClass;
    }
    // .//GEN-END:sourceClass_4_be
    // .//GEN-BEGIN:sourceMethod_be
    /** Getter for property sourceMethod.
     * @return Value of property sourceMethod.
     */
    public java.lang.String getSourceMethod() {
        return m_sourceMethod;
    }
    
    /** Use this method to update the property sourceMethod.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sourceMethod New value of property sourceMethod.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSourceMethod(java.lang.String sourceMethod)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_sourceMethod == null ? sourceMethod == null : m_sourceMethod.equals(sourceMethod))
            return;


        sourceMethod = validateSourceMethod(sourceMethod);
        // .//GEN-END:sourceMethod_be
        // Add custom code before setting the value//GEN-FIRST:sourceMethod


        // .//GEN-LAST:sourceMethod
        // .//GEN-BEGIN:sourceMethod_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.SOURCE_METHOD, m_sourceMethod);
        m_sourceMethod = sourceMethod;
        // .//GEN-END:sourceMethod_1_be
        // Add custom code after setting the value//GEN-FIRST:sourceMethod_3


        // .//GEN-LAST:sourceMethod_3
        // .//GEN-BEGIN:sourceMethod_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSourceMethod() method.
     * @param sourceMethod New value of property sourceMethod.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSourceMethod(java.lang.String sourceMethod)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSourceMethod(sourceMethod);
    }

    /** Use this method to validate a value for the property sourceMethod.
     * @param sourceMethod Value to be validated for the property sourceMethod.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSourceMethod(java.lang.String sourceMethod)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sourceMethod_2_be
        // Add custom code before validation//GEN-FIRST:sourceMethod_1


        // .//GEN-LAST:sourceMethod_1
        // .//GEN-BEGIN:sourceMethod_3_be
        sourceMethod = FieldValidator.validate(sourceMethod, (StringFieldMetaData) BusinessEventLogMeta.META_SOURCE_METHOD, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.SOURCE_METHOD, sourceMethod, this.getUOW());

        // .//GEN-END:sourceMethod_3_be
        // Add custom code after a successful validation//GEN-FIRST:sourceMethod_2


        // .//GEN-LAST:sourceMethod_2
        // .//GEN-BEGIN:sourceMethod_4_be
        return sourceMethod;
    }
    // .//GEN-END:sourceMethod_4_be
    // .//GEN-BEGIN:sourceLine_be
    /** Getter for property sourceLine.
     * @return Value of property sourceLine.
     */
    public java.lang.Long getSourceLine() {
        return m_sourceLine;
    }
    
    /** Use this method to update the property sourceLine.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sourceLine New value of property sourceLine.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSourceLine(java.lang.Long sourceLine)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_sourceLine == null ? sourceLine == null : m_sourceLine.equals(sourceLine))
            return;


        sourceLine = validateSourceLine(sourceLine);
        // .//GEN-END:sourceLine_be
        // Add custom code before setting the value//GEN-FIRST:sourceLine


        // .//GEN-LAST:sourceLine
        // .//GEN-BEGIN:sourceLine_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.SOURCE_LINE, m_sourceLine);
        m_sourceLine = sourceLine;
        // .//GEN-END:sourceLine_1_be
        // Add custom code after setting the value//GEN-FIRST:sourceLine_3


        // .//GEN-LAST:sourceLine_3
        // .//GEN-BEGIN:sourceLine_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSourceLine() method.
     * @param sourceLine New value of property sourceLine.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSourceLine(java.lang.Long sourceLine)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSourceLine(sourceLine);
    }

    /** Use this method to validate a value for the property sourceLine.
     * @param sourceLine Value to be validated for the property sourceLine.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateSourceLine(java.lang.Long sourceLine)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sourceLine_2_be
        // Add custom code before validation//GEN-FIRST:sourceLine_1


        // .//GEN-LAST:sourceLine_1
        // .//GEN-BEGIN:sourceLine_3_be
        sourceLine = FieldValidator.validate(sourceLine, (IntegerFieldMetaData) BusinessEventLogMeta.META_SOURCE_LINE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.SOURCE_LINE, sourceLine, this.getUOW());

        // .//GEN-END:sourceLine_3_be
        // Add custom code after a successful validation//GEN-FIRST:sourceLine_2


        // .//GEN-LAST:sourceLine_2
        // .//GEN-BEGIN:sourceLine_4_be
        return sourceLine;
    }
    // .//GEN-END:sourceLine_4_be
    // .//GEN-BEGIN:stackTrace_be
    /** Getter for property stackTrace.
     * @return Value of property stackTrace.
     */
    public java.lang.String getStackTrace() {
        return m_stackTrace;
    }
    
    /** Use this method to update the property stackTrace.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param stackTrace New value of property stackTrace.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setStackTrace(java.lang.String stackTrace)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_stackTrace == null ? stackTrace == null : m_stackTrace.equals(stackTrace))
            return;


        stackTrace = validateStackTrace(stackTrace);
        // .//GEN-END:stackTrace_be
        // Add custom code before setting the value//GEN-FIRST:stackTrace


        // .//GEN-LAST:stackTrace
        // .//GEN-BEGIN:stackTrace_1_be
        super.update();
        super.addInitialValue(BusinessEventLogMeta.STACK_TRACE, m_stackTrace);
        m_stackTrace = stackTrace;
        // .//GEN-END:stackTrace_1_be
        // Add custom code after setting the value//GEN-FIRST:stackTrace_3


        // .//GEN-LAST:stackTrace_3
        // .//GEN-BEGIN:stackTrace_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setStackTrace() method.
     * @param stackTrace New value of property stackTrace.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateStackTrace(java.lang.String stackTrace)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setStackTrace(stackTrace);
    }

    /** Use this method to validate a value for the property stackTrace.
     * @param stackTrace Value to be validated for the property stackTrace.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateStackTrace(java.lang.String stackTrace)
    throws ValidationException, FrameworkException {
        // .//GEN-END:stackTrace_2_be
        // Add custom code before validation//GEN-FIRST:stackTrace_1


        // .//GEN-LAST:stackTrace_1
        // .//GEN-BEGIN:stackTrace_3_be
        stackTrace = FieldValidator.validate(stackTrace, (StringFieldMetaData) BusinessEventLogMeta.META_STACK_TRACE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(BusinessEventLogMeta.getName(), BusinessEventLogMeta.STACK_TRACE, stackTrace, this.getUOW());

        // .//GEN-END:stackTrace_3_be
        // Add custom code after a successful validation//GEN-FIRST:stackTrace_2


        // .//GEN-LAST:stackTrace_2
        // .//GEN-BEGIN:stackTrace_4_be
        return stackTrace;
    }
    // .//GEN-END:stackTrace_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<BusinessEventLog>");
        buf.append("<logId>"); if (m_logId != null) buf.append(m_logId); buf.append("</logId>");
        buf.append("<correlationType>"); if (m_correlationType != null) buf.append(m_correlationType); buf.append("</correlationType>");
        buf.append("<correlationKey1>"); if (m_correlationKey1 != null) buf.append(m_correlationKey1); buf.append("</correlationKey1>");
        buf.append("<correlationKey2>"); if (m_correlationKey2 != null) buf.append(m_correlationKey2); buf.append("</correlationKey2>");
        buf.append("<correlationKey3>"); if (m_correlationKey3 != null) buf.append(m_correlationKey3); buf.append("</correlationKey3>");
        buf.append("<scheduledTaskId>"); if (m_scheduledTaskId != null) buf.append(m_scheduledTaskId); buf.append("</scheduledTaskId>");
        buf.append("<messageId>"); if (m_messageId != null) buf.append(m_messageId); buf.append("</messageId>");
        buf.append("<loggedOn>"); if (m_loggedOn != null) buf.append(m_loggedOn); buf.append("</loggedOn>");
        buf.append("<loggedBy>"); if (m_loggedBy != null) buf.append(m_loggedBy); buf.append("</loggedBy>");
        buf.append("<processName>"); if (m_processName != null) buf.append(m_processName); buf.append("</processName>");
        buf.append("<subProcessName>"); if (m_subProcessName != null) buf.append(m_subProcessName); buf.append("</subProcessName>");
        buf.append("<messageType>"); if (m_messageType != null) buf.append(m_messageType); buf.append("</messageType>");
        buf.append("<messageText>"); if (m_messageText != null) buf.append(m_messageText); buf.append("</messageText>");
        buf.append("<sourceClass>"); if (m_sourceClass != null) buf.append(m_sourceClass); buf.append("</sourceClass>");
        buf.append("<sourceMethod>"); if (m_sourceMethod != null) buf.append(m_sourceMethod); buf.append("</sourceMethod>");
        buf.append("<sourceLine>"); if (m_sourceLine != null) buf.append(m_sourceLine); buf.append("</sourceLine>");
        buf.append("<stackTrace>"); if (m_stackTrace != null) buf.append(m_stackTrace); buf.append("</stackTrace>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</BusinessEventLog>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        BusinessEventLog obj = (BusinessEventLog) super.clone();
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

    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        // Generate the technical-key, if required
        if (getLogId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(BusinessEventLogMeta.LOG_ID);
                vg.setLabelToken(BusinessEventLogMeta.META_LOG_ID.getLabelToken());
                setLogId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }

        super.validate();
    }
    // .//GEN-LAST:custom
}
