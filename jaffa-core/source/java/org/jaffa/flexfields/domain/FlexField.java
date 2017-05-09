// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.flexfields.domain;

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

import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FLEX_FIELDS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FLEX_FIELDS")
@SqlResultSetMapping(name="FlexField",entities={@EntityResult(entityClass=FlexField.class)})
public class FlexField extends Persistent {

    private static final Logger log = Logger.getLogger(FlexField.class);
    /** Holds value of property flexFieldId. */
    @XmlElement(name="flexFieldId")
    @Id
    @Column(name="FLEX_FIELD_ID")    
    private java.lang.String m_flexFieldId;

    /** Holds value of property objectName. */
    @XmlElement(name="objectName")
    @Column(name="OBJECT_NAME")    
    private java.lang.String m_objectName;

    /** Holds value of property key1. */
    @XmlElement(name="key1")
    @Column(name="KEY1")    
    private java.lang.String m_key1;

    /** Holds value of property key2. */
    @XmlElement(name="key2")
    @Column(name="KEY2")    
    private java.lang.String m_key2;

    /** Holds value of property key3. */
    @XmlElement(name="key3")
    @Column(name="KEY3")    
    private java.lang.String m_key3;

    /** Holds value of property fieldName. */
    @XmlElement(name="fieldName")
    @Column(name="FIELD_NAME")    
    private java.lang.String m_fieldName;

    /** Holds value of property value. */
    @XmlElement(name="value")
    @Column(name="VALUE")    
    private java.lang.String m_value;

    /** Holds value of property versionNumber. */
    @XmlElement(name="versionNumber")
    @Column(name="VERSION_NUMBER")    
    private java.lang.Long m_versionNumber;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FlexField() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String flexFieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(flexFieldId);
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
    public static FlexField findByPK(UOW uow, java.lang.String flexFieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(flexFieldId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FlexField) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String flexFieldId) {
        Criteria criteria = new Criteria();
        criteria.setTable(FlexFieldMeta.getName());
        criteria.addCriteria(FlexFieldMeta.FLEX_FIELD_ID, flexFieldId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:flexFieldId_be
    /** Getter for property flexFieldId.
     * @return Value of property flexFieldId.
     */
    public java.lang.String getFlexFieldId() {
        return m_flexFieldId;
    }
    
    /** Use this method to update the property flexFieldId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param flexFieldId New value of property flexFieldId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFlexFieldId(java.lang.String flexFieldId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_flexFieldId == null ? flexFieldId == null : m_flexFieldId.equals(flexFieldId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        flexFieldId = validateFlexFieldId(flexFieldId);
        // .//GEN-END:flexFieldId_be
        // Add custom code before setting the value//GEN-FIRST:flexFieldId


        // .//GEN-LAST:flexFieldId
        // .//GEN-BEGIN:flexFieldId_1_be
        super.update();
        super.addInitialValue(FlexFieldMeta.FLEX_FIELD_ID, m_flexFieldId);
        m_flexFieldId = flexFieldId;
        // .//GEN-END:flexFieldId_1_be
        // Add custom code after setting the value//GEN-FIRST:flexFieldId_3


        // .//GEN-LAST:flexFieldId_3
        // .//GEN-BEGIN:flexFieldId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFlexFieldId() method.
     * @param flexFieldId New value of property flexFieldId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFlexFieldId(java.lang.String flexFieldId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFlexFieldId(flexFieldId);
    }

    /** Use this method to validate a value for the property flexFieldId.
     * @param flexFieldId Value to be validated for the property flexFieldId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFlexFieldId(java.lang.String flexFieldId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:flexFieldId_2_be
        // Add custom code before validation//GEN-FIRST:flexFieldId_1


        // .//GEN-LAST:flexFieldId_1
        // .//GEN-BEGIN:flexFieldId_3_be
        flexFieldId = FieldValidator.validate(flexFieldId, (StringFieldMetaData) FlexFieldMeta.META_FLEX_FIELD_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.FLEX_FIELD_ID, flexFieldId, this.getUOW());

        // .//GEN-END:flexFieldId_3_be
        // Add custom code after a successful validation//GEN-FIRST:flexFieldId_2


        // .//GEN-LAST:flexFieldId_2
        // .//GEN-BEGIN:flexFieldId_4_be
        return flexFieldId;
    }
    // .//GEN-END:flexFieldId_4_be
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
        super.addInitialValue(FlexFieldMeta.OBJECT_NAME, m_objectName);
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
        objectName = FieldValidator.validate(objectName, (StringFieldMetaData) FlexFieldMeta.META_OBJECT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.OBJECT_NAME, objectName, this.getUOW());

        // .//GEN-END:objectName_3_be
        // Add custom code after a successful validation//GEN-FIRST:objectName_2


        // .//GEN-LAST:objectName_2
        // .//GEN-BEGIN:objectName_4_be
        return objectName;
    }
    // .//GEN-END:objectName_4_be
    // .//GEN-BEGIN:key1_be
    /** Getter for property key1.
     * @return Value of property key1.
     */
    public java.lang.String getKey1() {
        return m_key1;
    }
    
    /** Use this method to update the property key1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param key1 New value of property key1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setKey1(java.lang.String key1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_key1 == null ? key1 == null : m_key1.equals(key1))
            return;


        key1 = validateKey1(key1);
        // .//GEN-END:key1_be
        // Add custom code before setting the value//GEN-FIRST:key1


        // .//GEN-LAST:key1
        // .//GEN-BEGIN:key1_1_be
        super.update();
        super.addInitialValue(FlexFieldMeta.KEY1, m_key1);
        m_key1 = key1;
        // .//GEN-END:key1_1_be
        // Add custom code after setting the value//GEN-FIRST:key1_3


        // .//GEN-LAST:key1_3
        // .//GEN-BEGIN:key1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setKey1() method.
     * @param key1 New value of property key1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateKey1(java.lang.String key1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setKey1(key1);
    }

    /** Use this method to validate a value for the property key1.
     * @param key1 Value to be validated for the property key1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateKey1(java.lang.String key1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:key1_2_be
        // Add custom code before validation//GEN-FIRST:key1_1


        // .//GEN-LAST:key1_1
        // .//GEN-BEGIN:key1_3_be
        key1 = FieldValidator.validate(key1, (StringFieldMetaData) FlexFieldMeta.META_KEY1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.KEY1, key1, this.getUOW());

        // .//GEN-END:key1_3_be
        // Add custom code after a successful validation//GEN-FIRST:key1_2


        // .//GEN-LAST:key1_2
        // .//GEN-BEGIN:key1_4_be
        return key1;
    }
    // .//GEN-END:key1_4_be
    // .//GEN-BEGIN:key2_be
    /** Getter for property key2.
     * @return Value of property key2.
     */
    public java.lang.String getKey2() {
        return m_key2;
    }
    
    /** Use this method to update the property key2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param key2 New value of property key2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setKey2(java.lang.String key2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_key2 == null ? key2 == null : m_key2.equals(key2))
            return;


        key2 = validateKey2(key2);
        // .//GEN-END:key2_be
        // Add custom code before setting the value//GEN-FIRST:key2


        // .//GEN-LAST:key2
        // .//GEN-BEGIN:key2_1_be
        super.update();
        super.addInitialValue(FlexFieldMeta.KEY2, m_key2);
        m_key2 = key2;
        // .//GEN-END:key2_1_be
        // Add custom code after setting the value//GEN-FIRST:key2_3


        // .//GEN-LAST:key2_3
        // .//GEN-BEGIN:key2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setKey2() method.
     * @param key2 New value of property key2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateKey2(java.lang.String key2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setKey2(key2);
    }

    /** Use this method to validate a value for the property key2.
     * @param key2 Value to be validated for the property key2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateKey2(java.lang.String key2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:key2_2_be
        // Add custom code before validation//GEN-FIRST:key2_1


        // .//GEN-LAST:key2_1
        // .//GEN-BEGIN:key2_3_be
        key2 = FieldValidator.validate(key2, (StringFieldMetaData) FlexFieldMeta.META_KEY2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.KEY2, key2, this.getUOW());

        // .//GEN-END:key2_3_be
        // Add custom code after a successful validation//GEN-FIRST:key2_2


        // .//GEN-LAST:key2_2
        // .//GEN-BEGIN:key2_4_be
        return key2;
    }
    // .//GEN-END:key2_4_be
    // .//GEN-BEGIN:key3_be
    /** Getter for property key3.
     * @return Value of property key3.
     */
    public java.lang.String getKey3() {
        return m_key3;
    }
    
    /** Use this method to update the property key3.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param key3 New value of property key3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setKey3(java.lang.String key3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_key3 == null ? key3 == null : m_key3.equals(key3))
            return;


        key3 = validateKey3(key3);
        // .//GEN-END:key3_be
        // Add custom code before setting the value//GEN-FIRST:key3


        // .//GEN-LAST:key3
        // .//GEN-BEGIN:key3_1_be
        super.update();
        super.addInitialValue(FlexFieldMeta.KEY3, m_key3);
        m_key3 = key3;
        // .//GEN-END:key3_1_be
        // Add custom code after setting the value//GEN-FIRST:key3_3


        // .//GEN-LAST:key3_3
        // .//GEN-BEGIN:key3_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setKey3() method.
     * @param key3 New value of property key3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateKey3(java.lang.String key3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setKey3(key3);
    }

    /** Use this method to validate a value for the property key3.
     * @param key3 Value to be validated for the property key3.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateKey3(java.lang.String key3)
    throws ValidationException, FrameworkException {
        // .//GEN-END:key3_2_be
        // Add custom code before validation//GEN-FIRST:key3_1


        // .//GEN-LAST:key3_1
        // .//GEN-BEGIN:key3_3_be
        key3 = FieldValidator.validate(key3, (StringFieldMetaData) FlexFieldMeta.META_KEY3, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.KEY3, key3, this.getUOW());

        // .//GEN-END:key3_3_be
        // Add custom code after a successful validation//GEN-FIRST:key3_2


        // .//GEN-LAST:key3_2
        // .//GEN-BEGIN:key3_4_be
        return key3;
    }
    // .//GEN-END:key3_4_be
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
        super.addInitialValue(FlexFieldMeta.FIELD_NAME, m_fieldName);
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
        fieldName = FieldValidator.validate(fieldName, (StringFieldMetaData) FlexFieldMeta.META_FIELD_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.FIELD_NAME, fieldName, this.getUOW());

        // .//GEN-END:fieldName_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldName_2


        // .//GEN-LAST:fieldName_2
        // .//GEN-BEGIN:fieldName_4_be
        return fieldName;
    }
    // .//GEN-END:fieldName_4_be
    // .//GEN-BEGIN:value_be
    /** Getter for property value.
     * @return Value of property value.
     */
    public java.lang.String getValue() {
        return m_value;
    }
    
    /** Use this method to update the property value.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param value New value of property value.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setValue(java.lang.String value)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_value == null ? value == null : m_value.equals(value))
            return;


        value = validateValue(value);
        // .//GEN-END:value_be
        // Add custom code before setting the value//GEN-FIRST:value


        // .//GEN-LAST:value
        // .//GEN-BEGIN:value_1_be
        super.update();
        super.addInitialValue(FlexFieldMeta.VALUE, m_value);
        m_value = value;
        // .//GEN-END:value_1_be
        // Add custom code after setting the value//GEN-FIRST:value_3


        // .//GEN-LAST:value_3
        // .//GEN-BEGIN:value_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setValue() method.
     * @param value New value of property value.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateValue(java.lang.String value)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setValue(value);
    }

    /** Use this method to validate a value for the property value.
     * @param value Value to be validated for the property value.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateValue(java.lang.String value)
    throws ValidationException, FrameworkException {
        // .//GEN-END:value_2_be
        // Add custom code before validation//GEN-FIRST:value_1


        // .//GEN-LAST:value_1
        // .//GEN-BEGIN:value_3_be
        value = FieldValidator.validate(value, (StringFieldMetaData) FlexFieldMeta.META_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.VALUE, value, this.getUOW());

        // .//GEN-END:value_3_be
        // Add custom code after a successful validation//GEN-FIRST:value_2


        // .//GEN-LAST:value_2
        // .//GEN-BEGIN:value_4_be
        return value;
    }
    // .//GEN-END:value_4_be
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
        super.addInitialValue(FlexFieldMeta.VERSION_NUMBER, m_versionNumber);
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
        versionNumber = FieldValidator.validate(versionNumber, (IntegerFieldMetaData) FlexFieldMeta.META_VERSION_NUMBER, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldMeta.getName(), FlexFieldMeta.VERSION_NUMBER, versionNumber, this.getUOW());

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
        buf.append("<FlexField>");
        buf.append("<flexFieldId>"); if (m_flexFieldId != null) buf.append(m_flexFieldId); buf.append("</flexFieldId>");
        buf.append("<objectName>"); if (m_objectName != null) buf.append(m_objectName); buf.append("</objectName>");
        buf.append("<key1>"); if (m_key1 != null) buf.append(m_key1); buf.append("</key1>");
        buf.append("<key2>"); if (m_key2 != null) buf.append(m_key2); buf.append("</key2>");
        buf.append("<key3>"); if (m_key3 != null) buf.append(m_key3); buf.append("</key3>");
        buf.append("<fieldName>"); if (m_fieldName != null) buf.append(m_fieldName); buf.append("</fieldName>");
        buf.append("<value>"); if (m_value != null) buf.append(m_value); buf.append("</value>");
        buf.append("<versionNumber>"); if (m_versionNumber != null) buf.append(m_versionNumber); buf.append("</versionNumber>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FlexField>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FlexField obj = (FlexField) super.clone();
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
        generateKey();
        super.validate();
    }

    /** Generate the technical-key, if required. */
    public void generateKey() throws ApplicationExceptions, FrameworkException {
        if (getFlexFieldId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(FlexFieldMeta.FLEX_FIELD_ID);
                vg.setLabelToken(FlexFieldMeta.META_FLEX_FIELD_ID.getLabelToken());
                setFlexFieldId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    // .//GEN-LAST:custom
}
