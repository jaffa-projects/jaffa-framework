// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.domain;

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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jaffa.modules.printing.domain.FormEvent;
import org.jaffa.modules.printing.domain.FormEventMeta;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FORM_USAGES table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FORM_USAGES")
@SqlResultSetMapping(name="FormUsage",entities={@EntityResult(entityClass=FormUsage.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class FormUsage extends Persistent {

    private static final Logger log = Logger.getLogger(FormUsage.class);

    /** Holds value of property m_compositeKey. */
    @XmlElement(name="compositeKey")
    @EmbeddedId
    private CompositeKey m_compositeKey;
    
    /** Holds value of property formName. */
    @Transient
    private java.lang.String m_formName;

    /** Holds value of property eventName. */
    @Transient
    private java.lang.String m_eventName;

    /** Holds value of property formAlternate. */
    @XmlElement(name="formAlternate")
    @Column(name="FORM_ALTERNATE")    
    private java.lang.String m_formAlternate;

    /** Holds value of property copies. */
    @XmlElement(name="copies")
    @Column(name="COPIES")    
    private java.lang.Long m_copies;

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

    /** Holds related foreign FormEvent object. */
    private transient FormEvent m_formEventObject;

    /** Holds related foreign FormGroup object. */
    private transient FormGroup m_formGroupObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FormUsage() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String formName, java.lang.String eventName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(formName, eventName);
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
    public static FormUsage findByPK(UOW uow, java.lang.String formName, java.lang.String eventName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(formName, eventName);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FormUsage) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String formName, java.lang.String eventName) {
        Criteria criteria = new Criteria();
        criteria.setTable(FormUsageMeta.getName());
        criteria.addCriteria(FormUsageMeta.FORM_NAME, formName);
        criteria.addCriteria(FormUsageMeta.EVENT_NAME, eventName);
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
        // .//GEN-BEGIN:formName_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        if(m_formName!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setFormName(m_formName);
        }
        return getCompositeKey()!=null ? getCompositeKey().getFormName() : null;
    }
    
    /** Use this method to update the property formName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formName New value of property formName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormName(java.lang.String formName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (getFormName() == null ? formName == null : getFormName().equals(formName))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        formName = validateFormName(formName);
        // .//GEN-END:formName_be
        // Add custom code before setting the value//GEN-FIRST:formName


        // .//GEN-LAST:formName
        // .//GEN-BEGIN:formName_1_be
        super.update();
        super.addInitialValue(FormUsageMeta.FORM_NAME, getFormName());
        getCompositeKey().setFormName(formName);
        m_formName = formName;
        m_formGroupObject = null;
        // .//GEN-END:formName_1_be
        // Add custom code after setting the value//GEN-FIRST:formName_3


        // .//GEN-LAST:formName_3
        // .//GEN-BEGIN:formName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormName() method.
     * @param formName New value of property formName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormName(java.lang.String formName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormName(formName);
    }

    /** Use this method to validate a value for the property formName.
     * @param formName Value to be validated for the property formName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormName(java.lang.String formName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formName_2_be
        // Add custom code before validation//GEN-FIRST:formName_1


        // .//GEN-LAST:formName_1
        // .//GEN-BEGIN:formName_3_be
        formName = FieldValidator.validate(formName, (StringFieldMetaData) FormUsageMeta.META_FORM_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.FORM_NAME, formName, this.getUOW());

        // .//GEN-END:formName_3_be
        // Add custom code after a successful validation//GEN-FIRST:formName_2


        // .//GEN-LAST:formName_2
        // .//GEN-BEGIN:formName_4_be
        return formName;
    }
    // .//GEN-END:formName_4_be
    // .//GEN-BEGIN:eventName_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        if(m_eventName!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setEventName(m_eventName);
        }
        return getCompositeKey()!=null ? getCompositeKey().getEventName() : null;
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
        if (getEventName() == null ? eventName == null : getEventName().equals(eventName))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        eventName = validateEventName(eventName);
        // .//GEN-END:eventName_be
        // Add custom code before setting the value//GEN-FIRST:eventName


        // .//GEN-LAST:eventName
        // .//GEN-BEGIN:eventName_1_be
        super.update();
        super.addInitialValue(FormUsageMeta.EVENT_NAME, getEventName());
        getCompositeKey().setEventName(eventName);
        m_eventName = eventName;
        m_formEventObject = null;
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
        eventName = FieldValidator.validate(eventName, (StringFieldMetaData) FormUsageMeta.META_EVENT_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.EVENT_NAME, eventName, this.getUOW());

        // .//GEN-END:eventName_3_be
        // Add custom code after a successful validation//GEN-FIRST:eventName_2


        // .//GEN-LAST:eventName_2
        // .//GEN-BEGIN:eventName_4_be
        return eventName;
    }
    // .//GEN-END:eventName_4_be
    // .//GEN-BEGIN:formAlternate_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return m_formAlternate;
    }
    
    /** Use this method to update the property formAlternate.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formAlternate New value of property formAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormAlternate(java.lang.String formAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formAlternate == null ? formAlternate == null : m_formAlternate.equals(formAlternate))
            return;


        formAlternate = validateFormAlternate(formAlternate);
        // .//GEN-END:formAlternate_be
        // Add custom code before setting the value//GEN-FIRST:formAlternate


        // .//GEN-LAST:formAlternate
        // .//GEN-BEGIN:formAlternate_1_be
        super.update();
        super.addInitialValue(FormUsageMeta.FORM_ALTERNATE, m_formAlternate);
        m_formAlternate = formAlternate;
        // .//GEN-END:formAlternate_1_be
        // Add custom code after setting the value//GEN-FIRST:formAlternate_3


        // .//GEN-LAST:formAlternate_3
        // .//GEN-BEGIN:formAlternate_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormAlternate() method.
     * @param formAlternate New value of property formAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormAlternate(java.lang.String formAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormAlternate(formAlternate);
    }

    /** Use this method to validate a value for the property formAlternate.
     * @param formAlternate Value to be validated for the property formAlternate.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormAlternate(java.lang.String formAlternate)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formAlternate_2_be
        // Add custom code before validation//GEN-FIRST:formAlternate_1


        // .//GEN-LAST:formAlternate_1
        // .//GEN-BEGIN:formAlternate_3_be
        formAlternate = FieldValidator.validate(formAlternate, (StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.FORM_ALTERNATE, formAlternate, this.getUOW());

        // .//GEN-END:formAlternate_3_be
        // Add custom code after a successful validation//GEN-FIRST:formAlternate_2


        // .//GEN-LAST:formAlternate_2
        // .//GEN-BEGIN:formAlternate_4_be
        return formAlternate;
    }
    // .//GEN-END:formAlternate_4_be
    // .//GEN-BEGIN:copies_be
    /** Getter for property copies.
     * @return Value of property copies.
     */
    public java.lang.Long getCopies() {
        return m_copies;
    }
    
    /** Use this method to update the property copies.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param copies New value of property copies.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCopies(java.lang.Long copies)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_copies == null ? copies == null : m_copies.equals(copies))
            return;


        copies = validateCopies(copies);
        // .//GEN-END:copies_be
        // Add custom code before setting the value//GEN-FIRST:copies


        // .//GEN-LAST:copies
        // .//GEN-BEGIN:copies_1_be
        super.update();
        super.addInitialValue(FormUsageMeta.COPIES, m_copies);
        m_copies = copies;
        // .//GEN-END:copies_1_be
        // Add custom code after setting the value//GEN-FIRST:copies_3


        // .//GEN-LAST:copies_3
        // .//GEN-BEGIN:copies_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCopies() method.
     * @param copies New value of property copies.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCopies(java.lang.Long copies)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCopies(copies);
    }

    /** Use this method to validate a value for the property copies.
     * @param copies Value to be validated for the property copies.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateCopies(java.lang.Long copies)
    throws ValidationException, FrameworkException {
        // .//GEN-END:copies_2_be
        // Add custom code before validation//GEN-FIRST:copies_1


        // .//GEN-LAST:copies_1
        // .//GEN-BEGIN:copies_3_be
        copies = FieldValidator.validate(copies, (IntegerFieldMetaData) FormUsageMeta.META_COPIES, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.COPIES, copies, this.getUOW());

        // .//GEN-END:copies_3_be
        // Add custom code after a successful validation//GEN-FIRST:copies_2


        // .//GEN-LAST:copies_2
        // .//GEN-BEGIN:copies_4_be
        return copies;
    }
    // .//GEN-END:copies_4_be
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
        super.addInitialValue(FormUsageMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) FormUsageMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.CREATED_ON, createdOn, this.getUOW());

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
        super.addInitialValue(FormUsageMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) FormUsageMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.CREATED_BY, createdBy, this.getUOW());

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
        super.addInitialValue(FormUsageMeta.LAST_CHANGED_ON, m_lastChangedOn);
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
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) FormUsageMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

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
        super.addInitialValue(FormUsageMeta.LAST_CHANGED_BY, m_lastChangedBy);
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
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormUsageMeta.getName(), FormUsageMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
    // .//GEN-BEGIN:formEventObject_1_be
    /** Returns the related foreign FormEvent object.
     * The object is lazy-loaded.
     * @return the related foreign FormEvent object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public FormEvent getFormEventObject() throws ValidationException, FrameworkException  {
        findFormEventObject(false);
        return m_formEventObject;
    }
    
    /** Finds the related foreign FormEvent object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findFormEventObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_formEventObject == null && getEventName() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormEventMeta.getName());
                criteria.addCriteria(FormEventMeta.EVENT_NAME, getEventName());
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
                        m_formEventObject = (FormEvent) itr.next();
                }
                if (m_formEventObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(FormUsageMeta.META_EVENT_NAME.getLabelToken(), new Object[] {FormEventMeta.getLabelToken(), FormEventMeta.META_EVENT_NAME.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:formEventObject_1_be
    // .//GEN-BEGIN:formGroupObject_1_be
    /** Returns the related foreign FormGroup object.
     * The object is lazy-loaded.
     * @return the related foreign FormGroup object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public FormGroup getFormGroupObject() throws ValidationException, FrameworkException  {
        findFormGroupObject(false);
        return m_formGroupObject;
    }
    
    /** Finds the related foreign FormGroup object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findFormGroupObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_formGroupObject == null && getFormName() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormGroupMeta.getName());
                criteria.addCriteria(FormGroupMeta.FORM_NAME, getFormName());
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
                        m_formGroupObject = (FormGroup) itr.next();
                }
                if (m_formGroupObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(FormUsageMeta.META_FORM_NAME.getLabelToken(), new Object[] {FormGroupMeta.getLabelToken(), FormGroupMeta.META_FORM_NAME.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:formGroupObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormUsage>");
        buf.append("<formName>"); if (getFormName() != null) buf.append(getFormName()); buf.append("</formName>");
        buf.append("<eventName>"); if (getEventName() != null) buf.append(getEventName()); buf.append("</eventName>");
        buf.append("<formAlternate>"); if (m_formAlternate != null) buf.append(m_formAlternate); buf.append("</formAlternate>");
        buf.append("<copies>"); if (m_copies != null) buf.append(m_copies); buf.append("</copies>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FormUsage>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FormUsage obj = (FormUsage) super.clone();
        obj.m_formEventObject = null;
        obj.m_formGroupObject = null;
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
            if (isModified(FormUsageMeta.EVENT_NAME))
                findFormEventObject(true);
        } catch (ValidationException e) {
            appExps.add(e);
        }
        try {
            if (isModified(FormUsageMeta.FORM_NAME))
                findFormGroupObject(true);
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
     * @clientQualifier eventName
     * @supplierQualifier eventName
     * @link association
     */
    /*#FormEvent lnkFormEvent;*/

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier formName
     * @supplierQualifier formName
     * @link association
     */
    /*#FormGroup lnkFormGroup;*/

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
            if (getLastChangedOn() == null || !isModified(FormUsageMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(FormUsageMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
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

/**
 * Auto Generated Persistent class for the CompositeKey Composite Key.
 * @author  Auto-Generated
 */
    @Embeddable
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompositeKey implements Serializable{

        /** Holds value of property formName. */
        @XmlElement(name="formName")
        @Column(name="FORM_NAME")
        private java.lang.String m_formName;

        /** Holds value of property eventName. */
        @XmlElement(name="eventName")
        @Column(name="EVENT_NAME")
        private java.lang.String m_eventName;
    
        /** Getter for property m_formName.
        * @return Value of property m_formName.
        */
        public java.lang.String getFormName() {
            return m_formName;
        }  

        /** Setter for property m_formName.
        * @sets Value of property m_formName.
        */
        public void setFormName(java.lang.String formName){
            m_formName = formName;
        }    
    
        /** Getter for property m_eventName.
        * @return Value of property m_eventName.
        */
        public java.lang.String getEventName() {
            return m_eventName;
        }  

        /** Setter for property m_eventName.
        * @sets Value of property m_eventName.
        */
        public void setEventName(java.lang.String eventName){
            m_eventName = eventName;
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
                equals = equals && (getFormName() == null ? ((FormUsage.CompositeKey) obj).getFormName() == null : getFormName().equals(((FormUsage.CompositeKey) obj).getFormName()));
                equals = equals && (getEventName() == null ? ((FormUsage.CompositeKey) obj).getEventName() == null : getEventName().equals(((FormUsage.CompositeKey) obj).getEventName()));
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
            i += getFormName()!=null ? getFormName().hashCode() : 0;
            i += getEventName()!=null ? getEventName().hashCode() : 0;
            return i;
        } 
    }
}
