// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.soa.domain;

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
import org.jaffa.soa.domain.SOAEventParam;
import org.jaffa.soa.domain.SOAEventParamMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.soa.services.ConfigurationService;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_SOA_EVENTS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_SOA_EVENTS")
@SqlResultSetMapping(name="SOAEvent",entities={@EntityResult(entityClass=SOAEvent.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class SOAEvent extends Persistent {

    private static final Logger log = Logger.getLogger(SOAEvent.class);
    /** Holds value of property eventId. */
    @XmlElement(name="eventId")
    @Id
    @Column(name="EVENT_ID")    
    private java.lang.String m_eventId;

    /** Holds value of property eventName. */
    @XmlElement(name="eventName")
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

    /** Holds value of property localId. */
    @XmlElement(name="localId")
    @Column(name="LOCAL_ID")    
    private java.lang.String m_localId;

    /** Holds related SOAEventParam objects. */
    private transient Collection m_sOAEventParamCollection;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public SOAEvent() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String eventId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(eventId);
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
    public static SOAEvent findByPK(UOW uow, java.lang.String eventId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(eventId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (SOAEvent) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String eventId) {
        Criteria criteria = new Criteria();
        criteria.setTable(SOAEventMeta.getName());
        criteria.addCriteria(SOAEventMeta.EVENT_ID, eventId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:eventId_be
    /** Getter for property eventId.
     * @return Value of property eventId.
     */
    public java.lang.String getEventId() {
        return m_eventId;
    }
    
    /** Use this method to update the property eventId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param eventId New value of property eventId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setEventId(java.lang.String eventId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_eventId == null ? eventId == null : m_eventId.equals(eventId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        eventId = validateEventId(eventId);
        // .//GEN-END:eventId_be
        // Add custom code before setting the value//GEN-FIRST:eventId


        // .//GEN-LAST:eventId
        // .//GEN-BEGIN:eventId_1_be
        super.update();
        super.addInitialValue(SOAEventMeta.EVENT_ID, m_eventId);
        m_eventId = eventId;
        // .//GEN-END:eventId_1_be
        // Add custom code after setting the value//GEN-FIRST:eventId_3


        // .//GEN-LAST:eventId_3
        // .//GEN-BEGIN:eventId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setEventId() method.
     * @param eventId New value of property eventId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateEventId(java.lang.String eventId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setEventId(eventId);
    }

    /** Use this method to validate a value for the property eventId.
     * @param eventId Value to be validated for the property eventId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateEventId(java.lang.String eventId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:eventId_2_be
        // Add custom code before validation//GEN-FIRST:eventId_1


        // .//GEN-LAST:eventId_1
        // .//GEN-BEGIN:eventId_3_be
        eventId = FieldValidator.validate(eventId, (StringFieldMetaData) SOAEventMeta.META_EVENT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.EVENT_ID, eventId, this.getUOW());

        // .//GEN-END:eventId_3_be
        // Add custom code after a successful validation//GEN-FIRST:eventId_2


        // .//GEN-LAST:eventId_2
        // .//GEN-BEGIN:eventId_4_be
        return eventId;
    }
    // .//GEN-END:eventId_4_be
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
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setEventName(java.lang.String eventName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_eventName == null ? eventName == null : m_eventName.equals(eventName))
            return;


        eventName = validateEventName(eventName);
        // .//GEN-END:eventName_be
        // Add custom code before setting the value//GEN-FIRST:eventName


        // .//GEN-LAST:eventName
        // .//GEN-BEGIN:eventName_1_be
        super.update();
        super.addInitialValue(SOAEventMeta.EVENT_NAME, m_eventName);
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
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateEventName(java.lang.String eventName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        eventName = FieldValidator.validate(eventName, (StringFieldMetaData) SOAEventMeta.META_EVENT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.EVENT_NAME, eventName, this.getUOW());

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
        super.addInitialValue(SOAEventMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) SOAEventMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.DESCRIPTION, description, this.getUOW());

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
        super.addInitialValue(SOAEventMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) SOAEventMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.CREATED_ON, createdOn, this.getUOW());

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
        super.addInitialValue(SOAEventMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) SOAEventMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.CREATED_BY, createdBy, this.getUOW());

        // .//GEN-END:createdBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdBy_2


        // .//GEN-LAST:createdBy_2
        // .//GEN-BEGIN:createdBy_4_be
        return createdBy;
    }
    // .//GEN-END:createdBy_4_be
    // .//GEN-BEGIN:localId_be
    /** Getter for property localId.
     * @return Value of property localId.
     */
    public java.lang.String getLocalId() {
        return m_localId;
    }
    
    /** Use this method to update the property localId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param localId New value of property localId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLocalId(java.lang.String localId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_localId == null ? localId == null : m_localId.equals(localId))
            return;


        localId = validateLocalId(localId);
        // .//GEN-END:localId_be
        // Add custom code before setting the value//GEN-FIRST:localId


        // .//GEN-LAST:localId
        // .//GEN-BEGIN:localId_1_be
        super.update();
        super.addInitialValue(SOAEventMeta.LOCAL_ID, m_localId);
        m_localId = localId;
        // .//GEN-END:localId_1_be
        // Add custom code after setting the value//GEN-FIRST:localId_3


        // .//GEN-LAST:localId_3
        // .//GEN-BEGIN:localId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLocalId() method.
     * @param localId New value of property localId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLocalId(java.lang.String localId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLocalId(localId);
    }

    /** Use this method to validate a value for the property localId.
     * @param localId Value to be validated for the property localId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLocalId(java.lang.String localId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:localId_2_be
        // Add custom code before validation//GEN-FIRST:localId_1


        // .//GEN-LAST:localId_1
        // .//GEN-BEGIN:localId_3_be
        localId = FieldValidator.validate(localId, (StringFieldMetaData) SOAEventMeta.META_LOCAL_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventMeta.getName(), SOAEventMeta.LOCAL_ID, localId, this.getUOW());

        // .//GEN-END:localId_3_be
        // Add custom code after a successful validation//GEN-FIRST:localId_2


        // .//GEN-LAST:localId_2
        // .//GEN-BEGIN:localId_4_be
        return localId;
    }
    // .//GEN-END:localId_4_be
    // .//GEN-BEGIN:sOAEventParamArray_1_be
    /** Returns an array of related SOAEventParam objects.
     * @return an array of related SOAEventParam objects.
     * @throws FrameworkException Indicates some system error
     */
    public SOAEventParam[] getSOAEventParamArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            SOAEventParam[] output = null;
            if (m_sOAEventParamCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findSOAEventParamCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_sOAEventParamCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_sOAEventParamCollection.add(itr.next());
            }

            if (m_sOAEventParamCollection != null)
                output = (SOAEventParam[]) m_sOAEventParamCollection.toArray(new SOAEventParam[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related SOAEventParam objects.
     * @return a Criteria object for retrieving the related SOAEventParam objects.
     */
    public Criteria findSOAEventParamCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(SOAEventParamMeta.getName());
        criteria.addCriteria(SOAEventParamMeta.EVENT_ID, getEventId());
        // .//GEN-END:sOAEventParamArray_1_be
        // Add custom criteria//GEN-FIRST:sOAEventParamArray_1


        // .//GEN-LAST:sOAEventParamArray_1
        // .//GEN-BEGIN:sOAEventParamArray_2_be
        return criteria;
    }
    /** Creates a new SOAEventParam object and initializes the related fields.
     * This will uncache the related SOAEventParam objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related SOAEventParam object with the initialized related fields.
     */
    public SOAEventParam newSOAEventParamObject()
    throws ValidationException, FrameworkException {
        m_sOAEventParamCollection = null;
        SOAEventParam sOAEventParam = new SOAEventParam();
        sOAEventParam.setEventId(getEventId());
        // .//GEN-END:sOAEventParamArray_2_be
        // Add custom code//GEN-FIRST:sOAEventParamArray_2


        // .//GEN-LAST:sOAEventParamArray_2
        // .//GEN-BEGIN:sOAEventParamArray_3_be
        return sOAEventParam;
    }
    // .//GEN-END:sOAEventParamArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<SOAEvent>");
        buf.append("<eventId>"); if (m_eventId != null) buf.append(m_eventId); buf.append("</eventId>");
        buf.append("<eventName>"); if (m_eventName != null) buf.append(m_eventName); buf.append("</eventName>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<localId>"); if (m_localId != null) buf.append(m_localId); buf.append("</localId>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1
        try {
            if (getSOAEventParamArray() != null) {
                for (SOAEventParam param : getSOAEventParamArray())
                    buf.append(param);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Unable to obtain child SOAEventParam instances for the toString() representation", e);
        }

        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</SOAEvent>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        SOAEvent obj = (SOAEvent) super.clone();
        obj.m_sOAEventParamCollection = null;
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
        SOAEventParam[] sOAEventParamArray = null;
        try {
            sOAEventParamArray = getSOAEventParamArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (sOAEventParamArray != null && sOAEventParamArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < sOAEventParamArray.length; i++)
                    getUOW().delete(sOAEventParamArray[i]);
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
     * @clientQualifier eventId
     * @supplierQualifier eventId
     * @link composition
     */
    /*#SOAEventParam lnkSOAEventParam;*/

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

    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        // Generate the technical-key, if required
        if (getEventId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(SOAEventMeta.EVENT_ID);
                vg.setLabelToken(SOAEventMeta.META_EVENT_ID.getLabelToken());
                setEventId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }

        // Log a WARNing message if the event is not documented in soa-events.xml
        if (getEventName() != null && isModified(SOAEventMeta.EVENT_NAME) && ConfigurationService.getInstance().getSoaEventInfo(getEventName()) == null)
            log.warn("SOA Event '" + getEventName() + "' should be documented in soa-events.xml");

        super.validate();
    }
    // .//GEN-LAST:custom
}
