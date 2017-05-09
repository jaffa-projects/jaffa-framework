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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FLEX_FIELDS_DEL table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FLEX_FIELDS_DEL")
@SqlResultSetMapping(name="FlexFieldDelete",entities={@EntityResult(entityClass=FlexFieldDelete.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class FlexFieldDelete extends Persistent {

    private static final Logger log = Logger.getLogger(FlexFieldDelete.class);
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
    public FlexFieldDelete() {
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
    public static FlexFieldDelete findByPK(UOW uow, java.lang.String flexFieldId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(flexFieldId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FlexFieldDelete) itr.next();
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
        criteria.setTable(FlexFieldDeleteMeta.getName());
        criteria.addCriteria(FlexFieldDeleteMeta.FLEX_FIELD_ID, flexFieldId);
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
        super.addInitialValue(FlexFieldDeleteMeta.FLEX_FIELD_ID, m_flexFieldId);
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
        flexFieldId = FieldValidator.validate(flexFieldId, (StringFieldMetaData) FlexFieldDeleteMeta.META_FLEX_FIELD_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldDeleteMeta.getName(), FlexFieldDeleteMeta.FLEX_FIELD_ID, flexFieldId, this.getUOW());

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
        super.addInitialValue(FlexFieldDeleteMeta.OBJECT_NAME, m_objectName);
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
        objectName = FieldValidator.validate(objectName, (StringFieldMetaData) FlexFieldDeleteMeta.META_OBJECT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldDeleteMeta.getName(), FlexFieldDeleteMeta.OBJECT_NAME, objectName, this.getUOW());

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
        super.addInitialValue(FlexFieldDeleteMeta.KEY1, m_key1);
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
        key1 = FieldValidator.validate(key1, (StringFieldMetaData) FlexFieldDeleteMeta.META_KEY1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldDeleteMeta.getName(), FlexFieldDeleteMeta.KEY1, key1, this.getUOW());

        // .//GEN-END:key1_3_be
        // Add custom code after a successful validation//GEN-FIRST:key1_2


        // .//GEN-LAST:key1_2
        // .//GEN-BEGIN:key1_4_be
        return key1;
    }
    // .//GEN-END:key1_4_be
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
        super.addInitialValue(FlexFieldDeleteMeta.VERSION_NUMBER, m_versionNumber);
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
        versionNumber = FieldValidator.validate(versionNumber, (IntegerFieldMetaData) FlexFieldDeleteMeta.META_VERSION_NUMBER, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldDeleteMeta.getName(), FlexFieldDeleteMeta.VERSION_NUMBER, versionNumber, this.getUOW());

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
        super.addInitialValue(FlexFieldDeleteMeta.DELETION_CREATED_ON, m_deletionCreatedOn);
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
        deletionCreatedOn = FieldValidator.validate(deletionCreatedOn, (DateTimeFieldMetaData) FlexFieldDeleteMeta.META_DELETION_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FlexFieldDeleteMeta.getName(), FlexFieldDeleteMeta.DELETION_CREATED_ON, deletionCreatedOn, this.getUOW());

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
        buf.append("<FlexFieldDelete>");
        buf.append("<flexFieldId>"); if (m_flexFieldId != null) buf.append(m_flexFieldId); buf.append("</flexFieldId>");
        buf.append("<objectName>"); if (m_objectName != null) buf.append(m_objectName); buf.append("</objectName>");
        buf.append("<key1>"); if (m_key1 != null) buf.append(m_key1); buf.append("</key1>");
        buf.append("<versionNumber>"); if (m_versionNumber != null) buf.append(m_versionNumber); buf.append("</versionNumber>");
        buf.append("<deletionCreatedOn>"); if (m_deletionCreatedOn != null) buf.append(m_deletionCreatedOn); buf.append("</deletionCreatedOn>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FlexFieldDelete>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FlexFieldDelete obj = (FlexFieldDelete) super.clone();
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

        // This custom code to populate the Key1,ObjectName for Data Distribution Bulk Process.
        try {
            FlexField flexField = FlexField.findByPK(getUOW(), getFlexFieldId());
            if (flexField != null) {
                setKey1(flexField.getKey1());
                setObjectName(flexField.getObjectName());
            }
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[]{"Key1,ObjectName: Population Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[]{"Key1,ObjectName: Population Error"}, e);
        }     

        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.preAdd();
    }
    // .//GEN-END:preAdd_2_be

    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
