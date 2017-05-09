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
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_AUDIT_TRANSACTION_OVERFLOW table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_AUDIT_TRANSACTION_OVERFLOW")
@SqlResultSetMapping(name="AuditTransactionOverflow",entities={@EntityResult(entityClass=AuditTransactionOverflow.class)})
public class AuditTransactionOverflow extends Persistent {

    private static final Logger log = Logger.getLogger(AuditTransactionOverflow.class);
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


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AuditTransactionOverflow() {
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
    public static AuditTransactionOverflow findByPK(UOW uow, java.lang.String fieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(fieldId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (AuditTransactionOverflow) itr.next();
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
        criteria.setTable(AuditTransactionOverflowMeta.getName());
        criteria.addCriteria(AuditTransactionOverflowMeta.FIELD_ID, fieldId);
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
        super.addInitialValue(AuditTransactionOverflowMeta.FIELD_ID, m_fieldId);
        m_fieldId = fieldId;
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
        fieldId = FieldValidator.validate(fieldId, (StringFieldMetaData) AuditTransactionOverflowMeta.META_FIELD_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionOverflowMeta.getName(), AuditTransactionOverflowMeta.FIELD_ID, fieldId, this.getUOW());

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
        super.addInitialValue(AuditTransactionOverflowMeta.OBJECT_ID, m_objectId);
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
        objectId = FieldValidator.validate(objectId, (StringFieldMetaData) AuditTransactionOverflowMeta.META_OBJECT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionOverflowMeta.getName(), AuditTransactionOverflowMeta.OBJECT_ID, objectId, this.getUOW());

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
        super.addInitialValue(AuditTransactionOverflowMeta.FIELD_NAME, m_fieldName);
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
        fieldName = FieldValidator.validate(fieldName, (StringFieldMetaData) AuditTransactionOverflowMeta.META_FIELD_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionOverflowMeta.getName(), AuditTransactionOverflowMeta.FIELD_NAME, fieldName, this.getUOW());

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
        super.addInitialValue(AuditTransactionOverflowMeta.FROM_VALUE, m_fromValue);
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
        fromValue = FieldValidator.validate(fromValue, (StringFieldMetaData) AuditTransactionOverflowMeta.META_FROM_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionOverflowMeta.getName(), AuditTransactionOverflowMeta.FROM_VALUE, fromValue, this.getUOW());

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
        super.addInitialValue(AuditTransactionOverflowMeta.TO_VALUE, m_toValue);
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
        toValue = FieldValidator.validate(toValue, (StringFieldMetaData) AuditTransactionOverflowMeta.META_TO_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AuditTransactionOverflowMeta.getName(), AuditTransactionOverflowMeta.TO_VALUE, toValue, this.getUOW());

        // .//GEN-END:toValue_3_be
        // Add custom code after a successful validation//GEN-FIRST:toValue_2


        // .//GEN-LAST:toValue_2
        // .//GEN-BEGIN:toValue_4_be
        return toValue;
    }
    // .//GEN-END:toValue_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AuditTransactionOverflow>");
        buf.append("<fieldId>"); if (m_fieldId != null) buf.append(m_fieldId); buf.append("</fieldId>");
        buf.append("<objectId>"); if (m_objectId != null) buf.append(m_objectId); buf.append("</objectId>");
        buf.append("<fieldName>"); if (m_fieldName != null) buf.append(m_fieldName); buf.append("</fieldName>");
        buf.append("<fromValue>"); if (m_fromValue != null) buf.append(m_fromValue); buf.append("</fromValue>");
        buf.append("<toValue>"); if (m_toValue != null) buf.append(m_toValue); buf.append("</toValue>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</AuditTransactionOverflow>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        AuditTransactionOverflow obj = (AuditTransactionOverflow) super.clone();
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






    // .//GEN-LAST:custom
}
