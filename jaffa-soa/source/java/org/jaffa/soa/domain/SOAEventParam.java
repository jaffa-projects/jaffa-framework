// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.soa.domain;

import java.io.Serializable;
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
import org.jaffa.soa.domain.SOAEvent;
import org.jaffa.soa.domain.SOAEventMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.soa.services.ConfigurationService;
import org.jaffa.soa.services.configdomain.Param;
import org.jaffa.soa.services.configdomain.SoaEventInfo;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_SOA_EVENT_PARAMS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_SOA_EVENT_PARAMS")
@SqlResultSetMapping(name="SOAEventParam",entities={@EntityResult(entityClass=SOAEventParam.class)})
public class SOAEventParam extends Persistent {

    private static final Logger log = Logger.getLogger(SOAEventParam.class);

    /** Holds value of property m_compositeKey. */
    @XmlElement(name="compositeKey")
    @EmbeddedId
    private CompositeKey m_compositeKey;
    
    /** Holds value of property eventId. */
    @Transient
    private java.lang.String m_eventId;

    /** Holds value of property name. */
    @Transient
    private java.lang.String m_name;

    /** Holds value of property value. */
    @XmlElement(name="value")
    @Column(name="VALUE")    
    private java.lang.String m_value;

    /** Holds related foreign SOAEvent object. */
    private transient SOAEvent m_sOAEventObject;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public SOAEventParam() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String eventId, java.lang.String name) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(eventId, name);
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
    public static SOAEventParam findByPK(UOW uow, java.lang.String eventId, java.lang.String name) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(eventId, name);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (SOAEventParam) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String eventId, java.lang.String name) {
        Criteria criteria = new Criteria();
        criteria.setTable(SOAEventParamMeta.getName());
        criteria.addCriteria(SOAEventParamMeta.EVENT_ID, eventId);
        criteria.addCriteria(SOAEventParamMeta.NAME, name);
        return criteria;
    }
    // .//GEN-END:2_be

    /** Getter for property m_compositeKey.
     * @return Value of property m_compositeKey.
     */
    public CompositeKey getCompositeKey() {
        return m_compositeKey;
    }

    /** Setter for property m_compositeKey.
     * @sets Value of property m_compositeKey.
     */
    public void setCompositeKey(CompositeKey compositeKey) {
        this.m_compositeKey = compositeKey;
    }     
        // .//GEN-BEGIN:eventId_be
    /** Getter for property eventId.
     * @return Value of property eventId.
     */
    public java.lang.String getEventId() {
        if(m_eventId!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setEventId(m_eventId);
        }
        return getCompositeKey()!=null ? getCompositeKey().getEventId() : null;
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
        if (getEventId() == null ? eventId == null : getEventId().equals(eventId))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        eventId = validateEventId(eventId);
        // .//GEN-END:eventId_be
        // Add custom code before setting the value//GEN-FIRST:eventId


        // .//GEN-LAST:eventId
        // .//GEN-BEGIN:eventId_1_be
        super.update();
        super.addInitialValue(SOAEventParamMeta.EVENT_ID, getEventId());
        getCompositeKey().setEventId(eventId);
        m_eventId = eventId;
        m_sOAEventObject = null;
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
        eventId = FieldValidator.validate(eventId, (StringFieldMetaData) SOAEventParamMeta.META_EVENT_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventParamMeta.getName(), SOAEventParamMeta.EVENT_ID, eventId, this.getUOW());

        // .//GEN-END:eventId_3_be
        // Add custom code after a successful validation//GEN-FIRST:eventId_2


        // .//GEN-LAST:eventId_2
        // .//GEN-BEGIN:eventId_4_be
        return eventId;
    }
    // .//GEN-END:eventId_4_be
    // .//GEN-BEGIN:name_be
    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        if(m_name!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setName(m_name);
        }
        return getCompositeKey()!=null ? getCompositeKey().getName() : null;
    }
    
    /** Use this method to update the property name.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param name New value of property name.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setName(java.lang.String name)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (getName() == null ? name == null : getName().equals(name))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        name = validateName(name);
        // .//GEN-END:name_be
        // Add custom code before setting the value//GEN-FIRST:name


        // .//GEN-LAST:name
        // .//GEN-BEGIN:name_1_be
        super.update();
        super.addInitialValue(SOAEventParamMeta.NAME, getName());
        getCompositeKey().setName(name);
        m_name = name;
        // .//GEN-END:name_1_be
        // Add custom code after setting the value//GEN-FIRST:name_3


        // .//GEN-LAST:name_3
        // .//GEN-BEGIN:name_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setName() method.
     * @param name New value of property name.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateName(java.lang.String name)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setName(name);
    }

    /** Use this method to validate a value for the property name.
     * @param name Value to be validated for the property name.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateName(java.lang.String name)
    throws ValidationException, FrameworkException {
        // .//GEN-END:name_2_be
        // Add custom code before validation//GEN-FIRST:name_1


        // .//GEN-LAST:name_1
        // .//GEN-BEGIN:name_3_be
        name = FieldValidator.validate(name, (StringFieldMetaData) SOAEventParamMeta.META_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventParamMeta.getName(), SOAEventParamMeta.NAME, name, this.getUOW());

        // .//GEN-END:name_3_be
        // Add custom code after a successful validation//GEN-FIRST:name_2


        // .//GEN-LAST:name_2
        // .//GEN-BEGIN:name_4_be
        return name;
    }
    // .//GEN-END:name_4_be
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
        super.addInitialValue(SOAEventParamMeta.VALUE, m_value);
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
        value = FieldValidator.validate(value, (StringFieldMetaData) SOAEventParamMeta.META_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SOAEventParamMeta.getName(), SOAEventParamMeta.VALUE, value, this.getUOW());

        // .//GEN-END:value_3_be
        // Add custom code after a successful validation//GEN-FIRST:value_2


        // .//GEN-LAST:value_2
        // .//GEN-BEGIN:value_4_be
        return value;
    }
    // .//GEN-END:value_4_be
    // .//GEN-BEGIN:sOAEventObject_1_be
    /** Returns the related foreign SOAEvent object.
     * The object is lazy-loaded.
     * @return the related foreign SOAEvent object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public SOAEvent getSOAEventObject() throws ValidationException, FrameworkException  {
        findSOAEventObject(false);
        return m_sOAEventObject;
    }
    
    /** Finds the related foreign SOAEvent object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findSOAEventObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_sOAEventObject == null && getEventId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(SOAEventMeta.getName());
                criteria.addCriteria(SOAEventMeta.EVENT_ID, getEventId());
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
                        m_sOAEventObject = (SOAEvent) itr.next();
                }
                if (m_sOAEventObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(SOAEventParamMeta.META_EVENT_ID.getLabelToken(), new Object[] {SOAEventMeta.getLabelToken(), SOAEventMeta.META_EVENT_ID.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:sOAEventObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<SOAEventParam>");
        buf.append("<eventId>"); if (getEventId() != null) buf.append(getEventId()); buf.append("</eventId>");
        buf.append("<name>"); if (getName() != null) buf.append(getName()); buf.append("</name>");
        buf.append("<value>"); if (m_value != null) buf.append(m_value); buf.append("</value>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</SOAEventParam>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        SOAEventParam obj = (SOAEventParam) super.clone();
        obj.m_sOAEventObject = null;
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
            if (isModified(SOAEventParamMeta.EVENT_ID))
                findSOAEventObject(true);
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
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier eventId
     * @supplierQualifier eventId
     * @link association
     */
    /*#SOAEvent lnkSOAEvent;*/

    // .//GEN-END:3_be

    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        // Ensure that the eventId is specified
        if (getEventId() == null)
            throw new ApplicationExceptions(new MandatoryFieldException(SOAEventParamMeta.META_EVENT_ID.getLabelToken()));

        // Log a WARNing message if the paramter is not documented in soa-events.xml
        if (getName() != null && isModified(SOAEventParamMeta.NAME)) {
            try {
                SOAEvent soaEvent = getSOAEventObject();
                SoaEventInfo soaEventInfo = ConfigurationService.getInstance().getSoaEventInfo(soaEvent.getEventName());
                if (soaEventInfo != null) {
                    boolean foundParam = false;
                    for (Param param : soaEventInfo.getParam()) {
                        if (getName().equals(param.getName())) {
                            foundParam = true;
                            break;
                        }
                    }
                    if (!foundParam)
                        log.warn("SOA Event Parameter'" + soaEvent.getEventName() + ": " + getName() + "' should be documented in soa-events.xml");
                }
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }

        super.validate();
    }
    // .//GEN-LAST:custom

/**
 * Auto Generated Persistent class for the CompositeKey Composite Key.
 * @author  Auto-Generated
 */
    @Embeddable
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompositeKey implements Serializable{

        /** Holds value of property eventId. */
        @XmlElement(name="eventId")
        @Column(name="EVENT_ID")
        private java.lang.String m_eventId;

        /** Holds value of property name. */
        @XmlElement(name="name")
        @Column(name="NAME")
        private java.lang.String m_name;
    
        /** Getter for property m_eventId.
        * @return Value of property m_eventId.
        */
        public java.lang.String getEventId() {
            return m_eventId;
        }  

        /** Setter for property m_eventId.
        * @sets Value of property m_eventId.
        */
        public void setEventId(java.lang.String eventId){
            m_eventId = eventId;
        }    
    
        /** Getter for property m_name.
        * @return Value of property m_name.
        */
        public java.lang.String getName() {
            return m_name;
        }  

        /** Setter for property m_name.
        * @sets Value of property m_name.
        */
        public void setName(java.lang.String name){
            m_name = name;
        }    
        /**
         * Compares this candidateKey with another candidateKey object.
         * Returns a true if both the objects have the same candidate key.
         *
         * @param obj the other Persistent object.
         * @return a true if both the objects have the same candidate key.
         */
        public boolean equals(Object obj) {
            if (obj != null && this.getClass() == obj.getClass()) {
                boolean equals = true;
                equals = equals && (getEventId() == null ? ((SOAEventParam.CompositeKey) obj).getEventId() == null : getEventId().equals(((SOAEventParam.CompositeKey) obj).getEventId()));
                equals = equals && (getName() == null ? ((SOAEventParam.CompositeKey) obj).getName() == null : getName().equals(((SOAEventParam.CompositeKey) obj).getName()));
                return equals;
            }
            return super.equals(obj);
        }

        /**
         * Returns the hashCode of this object based on it's candidate key.
         *
         * @return the hashCode of this object based on it's candidate key.
         */
        public int hashCode() {
            int i = 0;
            i += getEventId()!=null ? getEventId().hashCode() : 0;
            i += getName()!=null ? getName().hashCode() : 0;
            return i;
        } 
    }
}
