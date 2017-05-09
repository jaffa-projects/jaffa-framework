// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.domain;

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
import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormUsageMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FORM_EVENTS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FORM_EVENTS")
@SqlResultSetMapping(name="FormEvent",entities={@EntityResult(entityClass=FormEvent.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class FormEvent extends Persistent {

    private static final Logger log = Logger.getLogger(FormEvent.class);
    /** Holds value of property eventName. */
    @XmlElement(name="eventName")
    @Id
    @Column(name="EVENT_NAME")    
    private java.lang.String m_eventName;

    /** Holds value of property description. */
    @XmlElement(name="description")
    @Column(name="DESCRIPTION")    
    private java.lang.String m_description;

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

    /** Holds related FormUsage objects. */
    private transient Collection m_formUsageCollection;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FormEvent() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String eventName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(eventName);
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
    public static FormEvent findByPK(UOW uow, java.lang.String eventName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(eventName);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FormEvent) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String eventName) {
        Criteria criteria = new Criteria();
        criteria.setTable(FormEventMeta.getName());
        criteria.addCriteria(FormEventMeta.EVENT_NAME, eventName);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:eventName_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        return m_eventName;
    }
    
    /** Use this method to update the property eventName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param eventName New value of property eventName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setEventName(java.lang.String eventName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_eventName == null ? eventName == null : m_eventName.equals(eventName))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        eventName = validateEventName(eventName);
        // .//GEN-END:eventName_be
        // Add custom code before setting the value//GEN-FIRST:eventName


        // .//GEN-LAST:eventName
        // .//GEN-BEGIN:eventName_1_be
        super.update();
        super.addInitialValue(FormEventMeta.EVENT_NAME, m_eventName);
        m_eventName = eventName;
        // .//GEN-END:eventName_1_be
        // Add custom code after setting the value//GEN-FIRST:eventName_3


        // .//GEN-LAST:eventName_3
        // .//GEN-BEGIN:eventName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setEventName() method.
     * @param eventName New value of property eventName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateEventName(java.lang.String eventName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setEventName(eventName);
    }

    /** Use this method to validate a value for the property eventName.
     * @param eventName Value to be validated for the property eventName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateEventName(java.lang.String eventName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:eventName_2_be
        // Add custom code before validation//GEN-FIRST:eventName_1


        // .//GEN-LAST:eventName_1
        // .//GEN-BEGIN:eventName_3_be
        eventName = FieldValidator.validate(eventName, (StringFieldMetaData) FormEventMeta.META_EVENT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.EVENT_NAME, eventName, this.getUOW());

        // .//GEN-END:eventName_3_be
        // Add custom code after a successful validation//GEN-FIRST:eventName_2


        // .//GEN-LAST:eventName_2
        // .//GEN-BEGIN:eventName_4_be
        return eventName;
    }
    // .//GEN-END:eventName_4_be
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
        super.addInitialValue(FormEventMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) FormEventMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
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
        super.addInitialValue(FormEventMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) FormEventMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.CREATED_ON, createdOn, this.getUOW());

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
        super.addInitialValue(FormEventMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) FormEventMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.CREATED_BY, createdBy, this.getUOW());

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
        super.addInitialValue(FormEventMeta.LAST_CHANGED_ON, m_lastChangedOn);
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
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) FormEventMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

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
        super.addInitialValue(FormEventMeta.LAST_CHANGED_BY, m_lastChangedBy);
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
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) FormEventMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormEventMeta.getName(), FormEventMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
    // .//GEN-BEGIN:formUsageArray_1_be
    /** Returns an array of related FormUsage objects.
     * @return an array of related FormUsage objects.
     * @throws FrameworkException Indicates some system error
     */
    public FormUsage[] getFormUsageArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            FormUsage[] output = null;
            if (m_formUsageCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findFormUsageCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_formUsageCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_formUsageCollection.add(itr.next());
            }

            if (m_formUsageCollection != null)
                output = (FormUsage[]) m_formUsageCollection.toArray(new FormUsage[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related FormUsage objects.
     * @return a Criteria object for retrieving the related FormUsage objects.
     */
    public Criteria findFormUsageCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(FormUsageMeta.getName());
        criteria.addCriteria(FormUsageMeta.EVENT_NAME, getEventName());
        // .//GEN-END:formUsageArray_1_be
        // Add custom criteria//GEN-FIRST:formUsageArray_1


        // .//GEN-LAST:formUsageArray_1
        // .//GEN-BEGIN:formUsageArray_2_be
        return criteria;
    }
    /** Creates a new FormUsage object and initializes the related fields.
     * This will uncache the related FormUsage objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related FormUsage object with the initialized related fields.
     */
    public FormUsage newFormUsageObject()
    throws ValidationException, FrameworkException {
        m_formUsageCollection = null;
        FormUsage formUsage = new FormUsage();
        formUsage.setEventName(getEventName());
        // .//GEN-END:formUsageArray_2_be
        // Add custom code//GEN-FIRST:formUsageArray_2


        // .//GEN-LAST:formUsageArray_2
        // .//GEN-BEGIN:formUsageArray_3_be
        return formUsage;
    }
    // .//GEN-END:formUsageArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormEvent>");
        buf.append("<eventName>"); if (m_eventName != null) buf.append(m_eventName); buf.append("</eventName>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FormEvent>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FormEvent obj = (FormEvent) super.clone();
        obj.m_formUsageCollection = null;
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
        FormUsage[] formUsageArray = null;
        try {
            formUsageArray = getFormUsageArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (formUsageArray != null && formUsageArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < formUsageArray.length; i++)
                    getUOW().delete(formUsageArray[i]);
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
     * @clientQualifier eventName
     * @supplierQualifier eventName
     * @link composition
     */
    /*#FormUsage lnkFormUsage;*/

    // .//GEN-END:3_be
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
            if (getLastChangedOn() == null || !isModified(FormEventMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(FormEventMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
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






    // .//GEN-LAST:custom
}
