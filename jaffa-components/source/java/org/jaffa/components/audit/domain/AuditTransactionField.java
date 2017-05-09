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
import org.jaffa.components.audit.domain.AuditTransactionOverflow;
import org.jaffa.components.audit.domain.AuditTransactionOverflowMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports

import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_AUDIT_TRANSACTION_FIELDS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_AUDIT_TRANSACTION_FIELDS")
@SqlResultSetMapping(name="AuditTransactionField",entities={@EntityResult(entityClass=AuditTransactionField.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class AuditTransactionField extends Persistent {

    private static final Logger log = Logger.getLogger(AuditTransactionField.class);
    /** Holds value of property fieldId. */
    @XmlElement(name="fieldId")
    @Id
    @Column(name="FIELD_ID")    
    private java.lang.String m_fieldId;

    /** Holds value of property objectId. */
    @XmlElement(name="objectId")
    @Column(name="OBJECT_ID")    
    private java.lang.String m_objectId;

    /** Holds value of property fieldName. */
    @XmlElement(name="fieldName")
    @Column(name="FIELD_NAME")    
    private java.lang.String m_fieldName;

    /** Holds value of property fromValue. */
    @XmlElement(name="fromValue")
    @Column(name="FROM_VALUE")    
    private java.lang.String m_fromValue;

    /** Holds value of property toValue. */
    @XmlElement(name="toValue")
    @Column(name="TO_VALUE")    
    private java.lang.String m_toValue;

    /** Holds value of property changed. */
    @XmlElement(name="changed")
    @Type(type="true_false")
    @Column(name="CHANGED")    
    private java.lang.Boolean m_changed;

    /** Holds value of property flex. */
    @XmlElement(name="flex")
    @Type(type="true_false")
    @Column(name="FLEX")    
    private java.lang.Boolean m_flex;

    /** Holds value of property overflowFlag. */
    @XmlElement(name="overflowFlag")
    @Type(type="true_false")
    @Column(name="OVERFLOW_FLAG")    
    private java.lang.Boolean m_overflowFlag;

    /** Holds value of property multilineHtmlFlag. */
    @XmlElement(name="multilineHtmlFlag")
    @Column(name="MULTILINE_HTML_FLAG")    
    private java.lang.String m_multilineHtmlFlag;

    /** Holds related foreign AuditTransactionObject object. */
    private transient AuditTransactionObject m_auditTransactionObjectObject;

    /** Holds related AuditTransactionOverflow object. */
    private transient AuditTransactionOverflow m_auditTransactionOverflowObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AuditTransactionField() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String fieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(fieldId);
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
    public static AuditTransactionField findByPK(UOW uow, java.lang.String fieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(fieldId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (AuditTransactionField) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String fieldId) {
        Criteria criteria = new Criteria();
        criteria.setTable(AuditTransactionFieldMeta.getName());
        criteria.addCriteria(AuditTransactionFieldMeta.FIELD_ID, fieldId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:fieldId_be
    /** Getter for property fieldId.
     * @return Value of property fieldId.
     */
    public java.lang.String getFieldId() {
        return m_fieldId;
    }
    
    /** Use this method to update the property fieldId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param fieldId New value of property fieldId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFieldId(java.lang.String fieldId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_fieldId == null ? fieldId == null : m_fieldId.equals(fieldId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        fieldId = validateFieldId(fieldId);
        // .//GEN-END:fieldId_be
        // Add custom code before setting the value//GEN-FIRST:fieldId


        // .//GEN-LAST:fieldId
        // .//GEN-BEGIN:fieldId_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.FIELD_ID, m_fieldId);
        m_fieldId = fieldId;
        m_auditTransactionOverflowObject = null;
        // .//GEN-END:fieldId_1_be
        // Add custom code after setting the value//GEN-FIRST:fieldId_3


        // .//GEN-LAST:fieldId_3
        // .//GEN-BEGIN:fieldId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFieldId() method.
     * @param fieldId New value of property fieldId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFieldId(java.lang.String fieldId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFieldId(fieldId);
    }

    /** Use this method to validate a value for the property fieldId.
     * @param fieldId Value to be validated for the property fieldId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFieldId(java.lang.String fieldId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:fieldId_2_be
        // Add custom code before validation//GEN-FIRST:fieldId_1


        // .//GEN-LAST:fieldId_1
        // .//GEN-BEGIN:fieldId_3_be
        fieldId = FieldValidator.validate(fieldId, (StringFieldMetaData) AuditTransactionFieldMeta.META_FIELD_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.FIELD_ID, fieldId, this.getUOW());

        // .//GEN-END:fieldId_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldId_2


        // .//GEN-LAST:fieldId_2
        // .//GEN-BEGIN:fieldId_4_be
        return fieldId;
    }
    // .//GEN-END:fieldId_4_be
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
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setObjectId(java.lang.String objectId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_objectId == null ? objectId == null : m_objectId.equals(objectId))
            return;


        objectId = validateObjectId(objectId);
        // .//GEN-END:objectId_be
        // Add custom code before setting the value//GEN-FIRST:objectId


        // .//GEN-LAST:objectId
        // .//GEN-BEGIN:objectId_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.OBJECT_ID, m_objectId);
        m_objectId = objectId;
        m_auditTransactionObjectObject = null;
        m_auditTransactionOverflowObject = null;
        // .//GEN-END:objectId_1_be
        // Add custom code after setting the value//GEN-FIRST:objectId_3


        // .//GEN-LAST:objectId_3
        // .//GEN-BEGIN:objectId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setObjectId() method.
     * @param objectId New value of property objectId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateObjectId(java.lang.String objectId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        objectId = FieldValidator.validate(objectId, (StringFieldMetaData) AuditTransactionFieldMeta.META_OBJECT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.OBJECT_ID, objectId, this.getUOW());

        // .//GEN-END:objectId_3_be
        // Add custom code after a successful validation//GEN-FIRST:objectId_2


        // .//GEN-LAST:objectId_2
        // .//GEN-BEGIN:objectId_4_be
        return objectId;
    }
    // .//GEN-END:objectId_4_be
    // .//GEN-BEGIN:fieldName_be
    /** Getter for property fieldName.
     * @return Value of property fieldName.
     */
    public java.lang.String getFieldName() {
        return m_fieldName;
    }
    
    /** Use this method to update the property fieldName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param fieldName New value of property fieldName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFieldName(java.lang.String fieldName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_fieldName == null ? fieldName == null : m_fieldName.equals(fieldName))
            return;


        fieldName = validateFieldName(fieldName);
        // .//GEN-END:fieldName_be
        // Add custom code before setting the value//GEN-FIRST:fieldName


        // .//GEN-LAST:fieldName
        // .//GEN-BEGIN:fieldName_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.FIELD_NAME, m_fieldName);
        m_fieldName = fieldName;
        // .//GEN-END:fieldName_1_be
        // Add custom code after setting the value//GEN-FIRST:fieldName_3


        // .//GEN-LAST:fieldName_3
        // .//GEN-BEGIN:fieldName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFieldName() method.
     * @param fieldName New value of property fieldName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFieldName(java.lang.String fieldName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFieldName(fieldName);
    }

    /** Use this method to validate a value for the property fieldName.
     * @param fieldName Value to be validated for the property fieldName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFieldName(java.lang.String fieldName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:fieldName_2_be
        // Add custom code before validation//GEN-FIRST:fieldName_1


        // .//GEN-LAST:fieldName_1
        // .//GEN-BEGIN:fieldName_3_be
        fieldName = FieldValidator.validate(fieldName, (StringFieldMetaData) AuditTransactionFieldMeta.META_FIELD_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.FIELD_NAME, fieldName, this.getUOW());

        // .//GEN-END:fieldName_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldName_2


        // .//GEN-LAST:fieldName_2
        // .//GEN-BEGIN:fieldName_4_be
        return fieldName;
    }
    // .//GEN-END:fieldName_4_be
    // .//GEN-BEGIN:fromValue_be
    /** Getter for property fromValue.
     * @return Value of property fromValue.
     */
    public java.lang.String getFromValue() {
        return m_fromValue;
    }
    
    /** Use this method to update the property fromValue.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param fromValue New value of property fromValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFromValue(java.lang.String fromValue)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_fromValue == null ? fromValue == null : m_fromValue.equals(fromValue))
            return;


        fromValue = validateFromValue(fromValue);
        // .//GEN-END:fromValue_be
        // Add custom code before setting the value//GEN-FIRST:fromValue


        // .//GEN-LAST:fromValue
        // .//GEN-BEGIN:fromValue_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.FROM_VALUE, m_fromValue);
        m_fromValue = fromValue;
        // .//GEN-END:fromValue_1_be
        // Add custom code after setting the value//GEN-FIRST:fromValue_3


        // .//GEN-LAST:fromValue_3
        // .//GEN-BEGIN:fromValue_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFromValue() method.
     * @param fromValue New value of property fromValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFromValue(java.lang.String fromValue)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFromValue(fromValue);
    }

    /** Use this method to validate a value for the property fromValue.
     * @param fromValue Value to be validated for the property fromValue.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFromValue(java.lang.String fromValue)
    throws ValidationException, FrameworkException {
        // .//GEN-END:fromValue_2_be
        // Add custom code before validation//GEN-FIRST:fromValue_1


        // .//GEN-LAST:fromValue_1
        // .//GEN-BEGIN:fromValue_3_be
        fromValue = FieldValidator.validate(fromValue, (StringFieldMetaData) AuditTransactionFieldMeta.META_FROM_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.FROM_VALUE, fromValue, this.getUOW());

        // .//GEN-END:fromValue_3_be
        // Add custom code after a successful validation//GEN-FIRST:fromValue_2


        // .//GEN-LAST:fromValue_2
        // .//GEN-BEGIN:fromValue_4_be
        return fromValue;
    }
    // .//GEN-END:fromValue_4_be
    // .//GEN-BEGIN:toValue_be
    /** Getter for property toValue.
     * @return Value of property toValue.
     */
    public java.lang.String getToValue() {
        return m_toValue;
    }
    
    /** Use this method to update the property toValue.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param toValue New value of property toValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setToValue(java.lang.String toValue)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_toValue == null ? toValue == null : m_toValue.equals(toValue))
            return;


        toValue = validateToValue(toValue);
        // .//GEN-END:toValue_be
        // Add custom code before setting the value//GEN-FIRST:toValue


        // .//GEN-LAST:toValue
        // .//GEN-BEGIN:toValue_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.TO_VALUE, m_toValue);
        m_toValue = toValue;
        // .//GEN-END:toValue_1_be
        // Add custom code after setting the value//GEN-FIRST:toValue_3


        // .//GEN-LAST:toValue_3
        // .//GEN-BEGIN:toValue_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setToValue() method.
     * @param toValue New value of property toValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateToValue(java.lang.String toValue)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setToValue(toValue);
    }

    /** Use this method to validate a value for the property toValue.
     * @param toValue Value to be validated for the property toValue.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateToValue(java.lang.String toValue)
    throws ValidationException, FrameworkException {
        // .//GEN-END:toValue_2_be
        // Add custom code before validation//GEN-FIRST:toValue_1


        // .//GEN-LAST:toValue_1
        // .//GEN-BEGIN:toValue_3_be
        toValue = FieldValidator.validate(toValue, (StringFieldMetaData) AuditTransactionFieldMeta.META_TO_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.TO_VALUE, toValue, this.getUOW());

        // .//GEN-END:toValue_3_be
        // Add custom code after a successful validation//GEN-FIRST:toValue_2


        // .//GEN-LAST:toValue_2
        // .//GEN-BEGIN:toValue_4_be
        return toValue;
    }
    // .//GEN-END:toValue_4_be
    // .//GEN-BEGIN:changed_be
    /** Getter for property changed.
     * @return Value of property changed.
     */
    public java.lang.Boolean getChanged() {
        return m_changed;
    }
    
    /** Use this method to update the property changed.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param changed New value of property changed.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setChanged(java.lang.Boolean changed)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_changed == null ? changed == null : m_changed.equals(changed))
            return;


        changed = validateChanged(changed);
        // .//GEN-END:changed_be
        // Add custom code before setting the value//GEN-FIRST:changed


        // .//GEN-LAST:changed
        // .//GEN-BEGIN:changed_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.CHANGED, m_changed);
        m_changed = changed;
        // .//GEN-END:changed_1_be
        // Add custom code after setting the value//GEN-FIRST:changed_3


        // .//GEN-LAST:changed_3
        // .//GEN-BEGIN:changed_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setChanged() method.
     * @param changed New value of property changed.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateChanged(java.lang.Boolean changed)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setChanged(changed);
    }

    /** Use this method to validate a value for the property changed.
     * @param changed Value to be validated for the property changed.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateChanged(java.lang.Boolean changed)
    throws ValidationException, FrameworkException {
        // .//GEN-END:changed_2_be
        // Add custom code before validation//GEN-FIRST:changed_1


        // .//GEN-LAST:changed_1
        // .//GEN-BEGIN:changed_3_be
        changed = FieldValidator.validate(changed, (BooleanFieldMetaData) AuditTransactionFieldMeta.META_CHANGED, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.CHANGED, changed, this.getUOW());

        // .//GEN-END:changed_3_be
        // Add custom code after a successful validation//GEN-FIRST:changed_2


        // .//GEN-LAST:changed_2
        // .//GEN-BEGIN:changed_4_be
        return changed;
    }
    // .//GEN-END:changed_4_be
    // .//GEN-BEGIN:flex_be
    /** Getter for property flex.
     * @return Value of property flex.
     */
    public java.lang.Boolean getFlex() {
        return m_flex;
    }
    
    /** Use this method to update the property flex.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param flex New value of property flex.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFlex(java.lang.Boolean flex)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_flex == null ? flex == null : m_flex.equals(flex))
            return;


        flex = validateFlex(flex);
        // .//GEN-END:flex_be
        // Add custom code before setting the value//GEN-FIRST:flex


        // .//GEN-LAST:flex
        // .//GEN-BEGIN:flex_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.FLEX, m_flex);
        m_flex = flex;
        // .//GEN-END:flex_1_be
        // Add custom code after setting the value//GEN-FIRST:flex_3


        // .//GEN-LAST:flex_3
        // .//GEN-BEGIN:flex_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFlex() method.
     * @param flex New value of property flex.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFlex(java.lang.Boolean flex)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFlex(flex);
    }

    /** Use this method to validate a value for the property flex.
     * @param flex Value to be validated for the property flex.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateFlex(java.lang.Boolean flex)
    throws ValidationException, FrameworkException {
        // .//GEN-END:flex_2_be
        // Add custom code before validation//GEN-FIRST:flex_1


        // .//GEN-LAST:flex_1
        // .//GEN-BEGIN:flex_3_be
        flex = FieldValidator.validate(flex, (BooleanFieldMetaData) AuditTransactionFieldMeta.META_FLEX, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.FLEX, flex, this.getUOW());

        // .//GEN-END:flex_3_be
        // Add custom code after a successful validation//GEN-FIRST:flex_2


        // .//GEN-LAST:flex_2
        // .//GEN-BEGIN:flex_4_be
        return flex;
    }
    // .//GEN-END:flex_4_be
    // .//GEN-BEGIN:overflowFlag_be
    /** Getter for property overflowFlag.
     * @return Value of property overflowFlag.
     */
    public java.lang.Boolean getOverflowFlag() {
        return m_overflowFlag;
    }
    
    /** Use this method to update the property overflowFlag.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param overflowFlag New value of property overflowFlag.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setOverflowFlag(java.lang.Boolean overflowFlag)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_overflowFlag == null ? overflowFlag == null : m_overflowFlag.equals(overflowFlag))
            return;


        overflowFlag = validateOverflowFlag(overflowFlag);
        // .//GEN-END:overflowFlag_be
        // Add custom code before setting the value//GEN-FIRST:overflowFlag


        // .//GEN-LAST:overflowFlag
        // .//GEN-BEGIN:overflowFlag_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.OVERFLOW_FLAG, m_overflowFlag);
        m_overflowFlag = overflowFlag;
        // .//GEN-END:overflowFlag_1_be
        // Add custom code after setting the value//GEN-FIRST:overflowFlag_3


        // .//GEN-LAST:overflowFlag_3
        // .//GEN-BEGIN:overflowFlag_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setOverflowFlag() method.
     * @param overflowFlag New value of property overflowFlag.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateOverflowFlag(java.lang.Boolean overflowFlag)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setOverflowFlag(overflowFlag);
    }

    /** Use this method to validate a value for the property overflowFlag.
     * @param overflowFlag Value to be validated for the property overflowFlag.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateOverflowFlag(java.lang.Boolean overflowFlag)
    throws ValidationException, FrameworkException {
        // .//GEN-END:overflowFlag_2_be
        // Add custom code before validation//GEN-FIRST:overflowFlag_1


        // .//GEN-LAST:overflowFlag_1
        // .//GEN-BEGIN:overflowFlag_3_be
        overflowFlag = FieldValidator.validate(overflowFlag, (BooleanFieldMetaData) AuditTransactionFieldMeta.META_OVERFLOW_FLAG, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.OVERFLOW_FLAG, overflowFlag, this.getUOW());

        // .//GEN-END:overflowFlag_3_be
        // Add custom code after a successful validation//GEN-FIRST:overflowFlag_2


        // .//GEN-LAST:overflowFlag_2
        // .//GEN-BEGIN:overflowFlag_4_be
        return overflowFlag;
    }
    // .//GEN-END:overflowFlag_4_be
    // .//GEN-BEGIN:multilineHtmlFlag_be
    /** Getter for property multilineHtmlFlag.
     * @return Value of property multilineHtmlFlag.
     */
    public java.lang.String getMultilineHtmlFlag() {
        return m_multilineHtmlFlag;
    }
    
    /** Use this method to update the property multilineHtmlFlag.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param multilineHtmlFlag New value of property multilineHtmlFlag.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setMultilineHtmlFlag(java.lang.String multilineHtmlFlag)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_multilineHtmlFlag == null ? multilineHtmlFlag == null : m_multilineHtmlFlag.equals(multilineHtmlFlag))
            return;


        multilineHtmlFlag = validateMultilineHtmlFlag(multilineHtmlFlag);
        // .//GEN-END:multilineHtmlFlag_be
        // Add custom code before setting the value//GEN-FIRST:multilineHtmlFlag


        // .//GEN-LAST:multilineHtmlFlag
        // .//GEN-BEGIN:multilineHtmlFlag_1_be
        super.update();
        super.addInitialValue(AuditTransactionFieldMeta.MULTILINE_HTML_FLAG, m_multilineHtmlFlag);
        m_multilineHtmlFlag = multilineHtmlFlag;
        // .//GEN-END:multilineHtmlFlag_1_be
        // Add custom code after setting the value//GEN-FIRST:multilineHtmlFlag_3


        // .//GEN-LAST:multilineHtmlFlag_3
        // .//GEN-BEGIN:multilineHtmlFlag_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setMultilineHtmlFlag() method.
     * @param multilineHtmlFlag New value of property multilineHtmlFlag.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateMultilineHtmlFlag(java.lang.String multilineHtmlFlag)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setMultilineHtmlFlag(multilineHtmlFlag);
    }

    /** Use this method to validate a value for the property multilineHtmlFlag.
     * @param multilineHtmlFlag Value to be validated for the property multilineHtmlFlag.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateMultilineHtmlFlag(java.lang.String multilineHtmlFlag)
    throws ValidationException, FrameworkException {
        // .//GEN-END:multilineHtmlFlag_2_be
        // Add custom code before validation//GEN-FIRST:multilineHtmlFlag_1


        // .//GEN-LAST:multilineHtmlFlag_1
        // .//GEN-BEGIN:multilineHtmlFlag_3_be
        multilineHtmlFlag = FieldValidator.validate(multilineHtmlFlag, (StringFieldMetaData) AuditTransactionFieldMeta.META_MULTILINE_HTML_FLAG, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionFieldMeta.getName(), AuditTransactionFieldMeta.MULTILINE_HTML_FLAG, multilineHtmlFlag, this.getUOW());

        // .//GEN-END:multilineHtmlFlag_3_be
        // Add custom code after a successful validation//GEN-FIRST:multilineHtmlFlag_2


        // .//GEN-LAST:multilineHtmlFlag_2
        // .//GEN-BEGIN:multilineHtmlFlag_4_be
        return multilineHtmlFlag;
    }
    // .//GEN-END:multilineHtmlFlag_4_be
    // .//GEN-BEGIN:auditTransactionObjectObject_1_be
    /** Returns the related foreign AuditTransactionObject object.
     * The object is lazy-loaded.
     * @return the related foreign AuditTransactionObject object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public AuditTransactionObject getAuditTransactionObjectObject() throws ValidationException, FrameworkException  {
        findAuditTransactionObjectObject(false);
        return m_auditTransactionObjectObject;
    }
    
    /** Finds the related foreign AuditTransactionObject object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findAuditTransactionObjectObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_auditTransactionObjectObject == null && getObjectId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(AuditTransactionObjectMeta.getName());
                criteria.addCriteria(AuditTransactionObjectMeta.OBJECT_ID, getObjectId());
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
                        m_auditTransactionObjectObject = (AuditTransactionObject) itr.next();
                }
                if (m_auditTransactionObjectObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(AuditTransactionFieldMeta.META_OBJECT_ID.getLabelToken(), new Object[] {AuditTransactionObjectMeta.getLabelToken(), AuditTransactionObjectMeta.META_OBJECT_ID.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:auditTransactionObjectObject_1_be
    // .//GEN-BEGIN:auditTransactionOverflowObject_1_be
    /** Returns a related AuditTransactionOverflow object.
     * @return a related AuditTransactionOverflow object.
     * @throws FrameworkException Indicates some system error
     */
    public AuditTransactionOverflow getAuditTransactionOverflowObject() throws FrameworkException {
        findAuditTransactionOverflowObject(false);
        return m_auditTransactionOverflowObject;
    }
    
    /** Finds the related AuditTransactionOverflow object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findAuditTransactionOverflowObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_auditTransactionOverflowObject == null && getFieldId() != null && getObjectId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(AuditTransactionOverflowMeta.getName());
                criteria.addCriteria(AuditTransactionOverflowMeta.FIELD_ID, getFieldId());
                criteria.addCriteria(AuditTransactionOverflowMeta.OBJECT_ID, getObjectId());
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
                        m_auditTransactionOverflowObject = (AuditTransactionOverflow) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new AuditTransactionOverflow object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related AuditTransactionOverflow object with the initialized related fields.
     */
    public AuditTransactionOverflow newAuditTransactionOverflowObject()
    throws ValidationException, FrameworkException {
        AuditTransactionOverflow auditTransactionOverflow = new AuditTransactionOverflow();
        auditTransactionOverflow.setFieldId(getFieldId());
        auditTransactionOverflow.setObjectId(getObjectId());
        // .//GEN-END:auditTransactionOverflowObject_1_be
        // Add custom code//GEN-FIRST:auditTransactionOverflowObject_1


        // .//GEN-LAST:auditTransactionOverflowObject_1
        // .//GEN-BEGIN:auditTransactionOverflowObject_2_be
        return auditTransactionOverflow;
    }
    // .//GEN-END:auditTransactionOverflowObject_2_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AuditTransactionField>");
        buf.append("<fieldId>"); if (m_fieldId != null) buf.append(m_fieldId); buf.append("</fieldId>");
        buf.append("<objectId>"); if (m_objectId != null) buf.append(m_objectId); buf.append("</objectId>");
        buf.append("<fieldName>"); if (m_fieldName != null) buf.append(m_fieldName); buf.append("</fieldName>");
        buf.append("<fromValue>"); if (m_fromValue != null) buf.append(m_fromValue); buf.append("</fromValue>");
        buf.append("<toValue>"); if (m_toValue != null) buf.append(m_toValue); buf.append("</toValue>");
        buf.append("<changed>"); if (m_changed != null) buf.append(m_changed); buf.append("</changed>");
        buf.append("<flex>"); if (m_flex != null) buf.append(m_flex); buf.append("</flex>");
        buf.append("<overflowFlag>"); if (m_overflowFlag != null) buf.append(m_overflowFlag); buf.append("</overflowFlag>");
        buf.append("<multilineHtmlFlag>"); if (m_multilineHtmlFlag != null) buf.append(m_multilineHtmlFlag); buf.append("</multilineHtmlFlag>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</AuditTransactionField>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        AuditTransactionField obj = (AuditTransactionField) super.clone();
        obj.m_auditTransactionObjectObject = null;
        obj.m_auditTransactionOverflowObject = null;
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
            if (isModified(AuditTransactionFieldMeta.OBJECT_ID))
                findAuditTransactionObjectObject(true);
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
        AuditTransactionOverflow auditTransactionOverflowObject = null;
        try {
            auditTransactionOverflowObject = getAuditTransactionOverflowObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (auditTransactionOverflowObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(auditTransactionOverflowObject);
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
     * @clientQualifier objectId
     * @supplierQualifier objectId
     * @link association
     */
    /*#AuditTransactionObject lnkAuditTransactionObject;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier fieldId,objectId
     * @supplierQualifier fieldId,objectId
     * @link composition
     */
    /*#AuditTransactionOverflow lnkAuditTransactionOverflow;*/

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
        if (getFieldId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(AuditTransactionFieldMeta.FIELD_ID);
                vg.setLabelToken(AuditTransactionFieldMeta.META_FIELD_ID.getLabelToken());
                setFieldId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    // .//GEN-LAST:custom
}
