// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.persistence.domainobjects;

import org.apache.log4j.Logger;
import java.util.*;
import javax.xml.bind.annotation.*;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.*;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.RelatedDomainObjectFoundException;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.persistence.domainobjects.Part;
import org.jaffa.persistence.domainobjects.PartMeta;
import org.jaffa.persistence.domainobjects.Condition;
import org.jaffa.persistence.domainobjects.ConditionMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the ZZ_JUT_ITEM table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item extends Persistent {

    private static final Logger log = Logger.getLogger(Item.class);

    /** Holds value of property itemId. */
    @XmlElement(name="itemId")
    private java.lang.String m_itemId;

    /** Holds value of property receivedItemId. */
    @XmlElement(name="receivedItemId")
    private java.lang.String m_receivedItemId;

    /** Holds value of property part. */
    @XmlElement(name="part")
    private java.lang.String m_part;

    /** Holds value of property prime. */
    @XmlElement(name="prime")
    private java.lang.String m_prime;

    /** Holds value of property sc. */
    @XmlElement(name="sc")
    private java.lang.String m_sc;

    /** Holds value of property condition. */
    @XmlElement(name="condition")
    private java.lang.String m_condition;

    /** Holds value of property createdDatetime. */
    @XmlElement(name="createdDatetime")
    private org.jaffa.datatypes.DateTime m_createdDatetime;

    /** Holds value of property qty. */
    @XmlElement(name="qty")
    private java.lang.Long m_qty;

    /** Holds value of property keyRef. */
    @XmlElement(name="keyRef")
    private java.lang.String m_keyRef;

    /** Holds value of property status1. */
    @XmlElement(name="status1")
    private java.lang.String m_status1;

    /** Holds value of property status2. */
    @XmlElement(name="status2")
    private java.lang.String m_status2;

    /** Holds value of property status3. */
    @XmlElement(name="status3")
    private java.lang.String m_status3;

    /** Holds value of property price. */
    @XmlElement(name="price")
    private java.lang.Double m_price;

    /** Holds related foreign Part object. */
    private transient Part m_partObject;

    /** Holds related foreign Condition object. */
    private transient Condition m_conditionObject;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String itemId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(itemId);
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
    public static Item findByPK(UOW uow, java.lang.String itemId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(itemId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (Item) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String itemId) {
        Criteria criteria = new Criteria();
        criteria.setTable(ItemMeta.getName());
        criteria.addCriteria(ItemMeta.ITEM_ID, itemId);
        return criteria;
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:itemId_be
    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public java.lang.String getItemId() {
        return m_itemId;
    }
    
    /** Use this method to update the property itemId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param itemId New value of property itemId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setItemId(java.lang.String itemId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_itemId == null ? itemId == null : m_itemId.equals(itemId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        itemId = validateItemId(itemId);
        // .//GEN-END:itemId_be
        // Add custom code before setting the value//GEN-FIRST:itemId


        // .//GEN-LAST:itemId
        // .//GEN-BEGIN:itemId_1_be
        super.update();
        super.addInitialValue(ItemMeta.ITEM_ID, m_itemId);
        m_itemId = itemId;
        // .//GEN-END:itemId_1_be
        // Add custom code after setting the value//GEN-FIRST:itemId_3


        // .//GEN-LAST:itemId_3
        // .//GEN-BEGIN:itemId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setItemId() method.
     * @param itemId New value of property itemId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateItemId(java.lang.String itemId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setItemId(itemId);
    }

    /** Use this method to validate a value for the property itemId.
     * @param itemId Value to be validated for the property itemId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateItemId(java.lang.String itemId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:itemId_2_be
        // Add custom code before validation//GEN-FIRST:itemId_1


        // .//GEN-LAST:itemId_1
        // .//GEN-BEGIN:itemId_3_be
        itemId = FieldValidator.validate(itemId, (StringFieldMetaData) ItemMeta.META_ITEM_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.ITEM_ID, itemId, this.getUOW());

        // .//GEN-END:itemId_3_be
        // Add custom code after a successful validation//GEN-FIRST:itemId_2


        // .//GEN-LAST:itemId_2
        // .//GEN-BEGIN:itemId_4_be
        return itemId;
    }
    // .//GEN-END:itemId_4_be
    // .//GEN-BEGIN:receivedItemId_be
    /** Getter for property receivedItemId.
     * @return Value of property receivedItemId.
     */
    public java.lang.String getReceivedItemId() {
        return m_receivedItemId;
    }
    
    /** Use this method to update the property receivedItemId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param receivedItemId New value of property receivedItemId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setReceivedItemId(java.lang.String receivedItemId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_receivedItemId == null ? receivedItemId == null : m_receivedItemId.equals(receivedItemId))
            return;


        receivedItemId = validateReceivedItemId(receivedItemId);
        // .//GEN-END:receivedItemId_be
        // Add custom code before setting the value//GEN-FIRST:receivedItemId


        // .//GEN-LAST:receivedItemId
        // .//GEN-BEGIN:receivedItemId_1_be
        super.update();
        super.addInitialValue(ItemMeta.RECEIVED_ITEM_ID, m_receivedItemId);
        m_receivedItemId = receivedItemId;
        // .//GEN-END:receivedItemId_1_be
        // Add custom code after setting the value//GEN-FIRST:receivedItemId_3


        // .//GEN-LAST:receivedItemId_3
        // .//GEN-BEGIN:receivedItemId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setReceivedItemId() method.
     * @param receivedItemId New value of property receivedItemId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateReceivedItemId(java.lang.String receivedItemId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setReceivedItemId(receivedItemId);
    }

    /** Use this method to validate a value for the property receivedItemId.
     * @param receivedItemId Value to be validated for the property receivedItemId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateReceivedItemId(java.lang.String receivedItemId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:receivedItemId_2_be
        // Add custom code before validation//GEN-FIRST:receivedItemId_1


        // .//GEN-LAST:receivedItemId_1
        // .//GEN-BEGIN:receivedItemId_3_be
        receivedItemId = FieldValidator.validate(receivedItemId, (StringFieldMetaData) ItemMeta.META_RECEIVED_ITEM_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.RECEIVED_ITEM_ID, receivedItemId, this.getUOW());

        // .//GEN-END:receivedItemId_3_be
        // Add custom code after a successful validation//GEN-FIRST:receivedItemId_2


        // .//GEN-LAST:receivedItemId_2
        // .//GEN-BEGIN:receivedItemId_4_be
        return receivedItemId;
    }
    // .//GEN-END:receivedItemId_4_be
    // .//GEN-BEGIN:part_be
    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart() {
        return m_part;
    }
    
    /** Use this method to update the property part.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param part New value of property part.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPart(java.lang.String part)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_part == null ? part == null : m_part.equals(part))
            return;


        part = validatePart(part);
        // .//GEN-END:part_be
        // Add custom code before setting the value//GEN-FIRST:part


        // .//GEN-LAST:part
        // .//GEN-BEGIN:part_1_be
        super.update();
        super.addInitialValue(ItemMeta.PART, m_part);
        m_part = part;
        m_partObject = null;
        // .//GEN-END:part_1_be
        // Add custom code after setting the value//GEN-FIRST:part_3


        // .//GEN-LAST:part_3
        // .//GEN-BEGIN:part_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPart() method.
     * @param part New value of property part.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePart(java.lang.String part)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPart(part);
    }

    /** Use this method to validate a value for the property part.
     * @param part Value to be validated for the property part.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePart(java.lang.String part)
    throws ValidationException, FrameworkException {
        // .//GEN-END:part_2_be
        // Add custom code before validation//GEN-FIRST:part_1


        // .//GEN-LAST:part_1
        // .//GEN-BEGIN:part_3_be
        part = FieldValidator.validate(part, (StringFieldMetaData) ItemMeta.META_PART, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.PART, part, this.getUOW());

        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
        return part;
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:prime_be
    /** Getter for property prime.
     * @return Value of property prime.
     */
    public java.lang.String getPrime() {
        return m_prime;
    }
    
    /** Use this method to update the property prime.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param prime New value of property prime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPrime(java.lang.String prime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_prime == null ? prime == null : m_prime.equals(prime))
            return;


        prime = validatePrime(prime);
        // .//GEN-END:prime_be
        // Add custom code before setting the value//GEN-FIRST:prime


        // .//GEN-LAST:prime
        // .//GEN-BEGIN:prime_1_be
        super.update();
        super.addInitialValue(ItemMeta.PRIME, m_prime);
        m_prime = prime;
        // .//GEN-END:prime_1_be
        // Add custom code after setting the value//GEN-FIRST:prime_3


        // .//GEN-LAST:prime_3
        // .//GEN-BEGIN:prime_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPrime() method.
     * @param prime New value of property prime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePrime(java.lang.String prime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPrime(prime);
    }

    /** Use this method to validate a value for the property prime.
     * @param prime Value to be validated for the property prime.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePrime(java.lang.String prime)
    throws ValidationException, FrameworkException {
        // .//GEN-END:prime_2_be
        // Add custom code before validation//GEN-FIRST:prime_1


        // .//GEN-LAST:prime_1
        // .//GEN-BEGIN:prime_3_be
        prime = FieldValidator.validate(prime, (StringFieldMetaData) ItemMeta.META_PRIME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.PRIME, prime, this.getUOW());

        // .//GEN-END:prime_3_be
        // Add custom code after a successful validation//GEN-FIRST:prime_2


        // .//GEN-LAST:prime_2
        // .//GEN-BEGIN:prime_4_be
        return prime;
    }
    // .//GEN-END:prime_4_be
    // .//GEN-BEGIN:sc_be
    /** Getter for property sc.
     * @return Value of property sc.
     */
    public java.lang.String getSc() {
        return m_sc;
    }
    
    /** Use this method to update the property sc.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sc New value of property sc.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSc(java.lang.String sc)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_sc == null ? sc == null : m_sc.equals(sc))
            return;


        sc = validateSc(sc);
        // .//GEN-END:sc_be
        // Add custom code before setting the value//GEN-FIRST:sc


        // .//GEN-LAST:sc
        // .//GEN-BEGIN:sc_1_be
        super.update();
        super.addInitialValue(ItemMeta.SC, m_sc);
        m_sc = sc;
        // .//GEN-END:sc_1_be
        // Add custom code after setting the value//GEN-FIRST:sc_3


        // .//GEN-LAST:sc_3
        // .//GEN-BEGIN:sc_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSc() method.
     * @param sc New value of property sc.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSc(java.lang.String sc)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSc(sc);
    }

    /** Use this method to validate a value for the property sc.
     * @param sc Value to be validated for the property sc.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSc(java.lang.String sc)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sc_2_be
        // Add custom code before validation//GEN-FIRST:sc_1


        // .//GEN-LAST:sc_1
        // .//GEN-BEGIN:sc_3_be
        sc = FieldValidator.validate(sc, (StringFieldMetaData) ItemMeta.META_SC, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.SC, sc, this.getUOW());

        // .//GEN-END:sc_3_be
        // Add custom code after a successful validation//GEN-FIRST:sc_2


        // .//GEN-LAST:sc_2
        // .//GEN-BEGIN:sc_4_be
        return sc;
    }
    // .//GEN-END:sc_4_be
    // .//GEN-BEGIN:condition_be
    /** Getter for property condition.
     * @return Value of property condition.
     */
    public java.lang.String getCondition() {
        return m_condition;
    }
    
    /** Use this method to update the property condition.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param condition New value of property condition.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCondition(java.lang.String condition)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_condition == null ? condition == null : m_condition.equals(condition))
            return;


        condition = validateCondition(condition);
        // .//GEN-END:condition_be
        // Add custom code before setting the value//GEN-FIRST:condition


        // .//GEN-LAST:condition
        // .//GEN-BEGIN:condition_1_be
        super.update();
        super.addInitialValue(ItemMeta.CONDITION, m_condition);
        m_condition = condition;
        m_conditionObject = null;
        // .//GEN-END:condition_1_be
        // Add custom code after setting the value//GEN-FIRST:condition_3


        // .//GEN-LAST:condition_3
        // .//GEN-BEGIN:condition_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCondition() method.
     * @param condition New value of property condition.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCondition(java.lang.String condition)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCondition(condition);
    }

    /** Use this method to validate a value for the property condition.
     * @param condition Value to be validated for the property condition.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCondition(java.lang.String condition)
    throws ValidationException, FrameworkException {
        // .//GEN-END:condition_2_be
        // Add custom code before validation//GEN-FIRST:condition_1


        // .//GEN-LAST:condition_1
        // .//GEN-BEGIN:condition_3_be
        condition = FieldValidator.validate(condition, (StringFieldMetaData) ItemMeta.META_CONDITION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.CONDITION, condition, this.getUOW());

        // .//GEN-END:condition_3_be
        // Add custom code after a successful validation//GEN-FIRST:condition_2


        // .//GEN-LAST:condition_2
        // .//GEN-BEGIN:condition_4_be
        return condition;
    }
    // .//GEN-END:condition_4_be
    // .//GEN-BEGIN:createdDatetime_be
    /** Getter for property createdDatetime.
     * @return Value of property createdDatetime.
     */
    public org.jaffa.datatypes.DateTime getCreatedDatetime() {
        return m_createdDatetime;
    }
    
    /** Use this method to update the property createdDatetime.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param createdDatetime New value of property createdDatetime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_createdDatetime == null ? createdDatetime == null : m_createdDatetime.equals(createdDatetime))
            return;


        createdDatetime = validateCreatedDatetime(createdDatetime);
        // .//GEN-END:createdDatetime_be
        // Add custom code before setting the value//GEN-FIRST:createdDatetime


        // .//GEN-LAST:createdDatetime
        // .//GEN-BEGIN:createdDatetime_1_be
        super.update();
        super.addInitialValue(ItemMeta.CREATED_DATETIME, m_createdDatetime);
        m_createdDatetime = createdDatetime;
        // .//GEN-END:createdDatetime_1_be
        // Add custom code after setting the value//GEN-FIRST:createdDatetime_3


        // .//GEN-LAST:createdDatetime_3
        // .//GEN-BEGIN:createdDatetime_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCreatedDatetime() method.
     * @param createdDatetime New value of property createdDatetime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCreatedDatetime(createdDatetime);
    }

    /** Use this method to validate a value for the property createdDatetime.
     * @param createdDatetime Value to be validated for the property createdDatetime.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime)
    throws ValidationException, FrameworkException {
        // .//GEN-END:createdDatetime_2_be
        // Add custom code before validation//GEN-FIRST:createdDatetime_1


        // .//GEN-LAST:createdDatetime_1
        // .//GEN-BEGIN:createdDatetime_3_be
        createdDatetime = FieldValidator.validate(createdDatetime, (DateTimeFieldMetaData) ItemMeta.META_CREATED_DATETIME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.CREATED_DATETIME, createdDatetime, this.getUOW());

        // .//GEN-END:createdDatetime_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdDatetime_2


        // .//GEN-LAST:createdDatetime_2
        // .//GEN-BEGIN:createdDatetime_4_be
        return createdDatetime;
    }
    // .//GEN-END:createdDatetime_4_be
    // .//GEN-BEGIN:qty_be
    /** Getter for property qty.
     * @return Value of property qty.
     */
    public java.lang.Long getQty() {
        return m_qty;
    }
    
    /** Use this method to update the property qty.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param qty New value of property qty.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setQty(java.lang.Long qty)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_qty == null ? qty == null : m_qty.equals(qty))
            return;


        qty = validateQty(qty);
        // .//GEN-END:qty_be
        // Add custom code before setting the value//GEN-FIRST:qty


        // .//GEN-LAST:qty
        // .//GEN-BEGIN:qty_1_be
        super.update();
        super.addInitialValue(ItemMeta.QTY, m_qty);
        m_qty = qty;
        // .//GEN-END:qty_1_be
        // Add custom code after setting the value//GEN-FIRST:qty_3


        // .//GEN-LAST:qty_3
        // .//GEN-BEGIN:qty_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setQty() method.
     * @param qty New value of property qty.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateQty(java.lang.Long qty)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setQty(qty);
    }

    /** Use this method to validate a value for the property qty.
     * @param qty Value to be validated for the property qty.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateQty(java.lang.Long qty)
    throws ValidationException, FrameworkException {
        // .//GEN-END:qty_2_be
        // Add custom code before validation//GEN-FIRST:qty_1


        // .//GEN-LAST:qty_1
        // .//GEN-BEGIN:qty_3_be
        qty = FieldValidator.validate(qty, (IntegerFieldMetaData) ItemMeta.META_QTY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.QTY, qty, this.getUOW());

        // .//GEN-END:qty_3_be
        // Add custom code after a successful validation//GEN-FIRST:qty_2


        // .//GEN-LAST:qty_2
        // .//GEN-BEGIN:qty_4_be
        return qty;
    }
    // .//GEN-END:qty_4_be
    // .//GEN-BEGIN:keyRef_be
    /** Getter for property keyRef.
     * @return Value of property keyRef.
     */
    public java.lang.String getKeyRef() {
        return m_keyRef;
    }
    
    /** Use this method to update the property keyRef.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param keyRef New value of property keyRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setKeyRef(java.lang.String keyRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_keyRef == null ? keyRef == null : m_keyRef.equals(keyRef))
            return;


        keyRef = validateKeyRef(keyRef);
        // .//GEN-END:keyRef_be
        // Add custom code before setting the value//GEN-FIRST:keyRef


        // .//GEN-LAST:keyRef
        // .//GEN-BEGIN:keyRef_1_be
        super.update();
        super.addInitialValue(ItemMeta.KEY_REF, m_keyRef);
        m_keyRef = keyRef;
        // .//GEN-END:keyRef_1_be
        // Add custom code after setting the value//GEN-FIRST:keyRef_3


        // .//GEN-LAST:keyRef_3
        // .//GEN-BEGIN:keyRef_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setKeyRef() method.
     * @param keyRef New value of property keyRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateKeyRef(java.lang.String keyRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setKeyRef(keyRef);
    }

    /** Use this method to validate a value for the property keyRef.
     * @param keyRef Value to be validated for the property keyRef.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateKeyRef(java.lang.String keyRef)
    throws ValidationException, FrameworkException {
        // .//GEN-END:keyRef_2_be
        // Add custom code before validation//GEN-FIRST:keyRef_1


        // .//GEN-LAST:keyRef_1
        // .//GEN-BEGIN:keyRef_3_be
        keyRef = FieldValidator.validate(keyRef, (StringFieldMetaData) ItemMeta.META_KEY_REF, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.KEY_REF, keyRef, this.getUOW());

        // .//GEN-END:keyRef_3_be
        // Add custom code after a successful validation//GEN-FIRST:keyRef_2


        // .//GEN-LAST:keyRef_2
        // .//GEN-BEGIN:keyRef_4_be
        return keyRef;
    }
    // .//GEN-END:keyRef_4_be
    // .//GEN-BEGIN:status1_be
    /** Getter for property status1.
     * @return Value of property status1.
     */
    public java.lang.String getStatus1() {
        return m_status1;
    }
    
    /** Use this method to update the property status1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param status1 New value of property status1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setStatus1(java.lang.String status1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_status1 == null ? status1 == null : m_status1.equals(status1))
            return;


        status1 = validateStatus1(status1);
        // .//GEN-END:status1_be
        // Add custom code before setting the value//GEN-FIRST:status1


        // .//GEN-LAST:status1
        // .//GEN-BEGIN:status1_1_be
        super.update();
        super.addInitialValue(ItemMeta.STATUS1, m_status1);
        m_status1 = status1;
        // .//GEN-END:status1_1_be
        // Add custom code after setting the value//GEN-FIRST:status1_3


        // .//GEN-LAST:status1_3
        // .//GEN-BEGIN:status1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setStatus1() method.
     * @param status1 New value of property status1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateStatus1(java.lang.String status1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setStatus1(status1);
    }

    /** Use this method to validate a value for the property status1.
     * @param status1 Value to be validated for the property status1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateStatus1(java.lang.String status1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:status1_2_be
        // Add custom code before validation//GEN-FIRST:status1_1


        // .//GEN-LAST:status1_1
        // .//GEN-BEGIN:status1_3_be
        status1 = FieldValidator.validate(status1, (StringFieldMetaData) ItemMeta.META_STATUS1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.STATUS1, status1, this.getUOW());

        // .//GEN-END:status1_3_be
        // Add custom code after a successful validation//GEN-FIRST:status1_2


        // .//GEN-LAST:status1_2
        // .//GEN-BEGIN:status1_4_be
        return status1;
    }
    // .//GEN-END:status1_4_be
    // .//GEN-BEGIN:status2_be
    /** Getter for property status2.
     * @return Value of property status2.
     */
    public java.lang.String getStatus2() {
        return m_status2;
    }
    
    /** Use this method to update the property status2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param status2 New value of property status2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setStatus2(java.lang.String status2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_status2 == null ? status2 == null : m_status2.equals(status2))
            return;


        status2 = validateStatus2(status2);
        // .//GEN-END:status2_be
        // Add custom code before setting the value//GEN-FIRST:status2


        // .//GEN-LAST:status2
        // .//GEN-BEGIN:status2_1_be
        super.update();
        super.addInitialValue(ItemMeta.STATUS2, m_status2);
        m_status2 = status2;
        // .//GEN-END:status2_1_be
        // Add custom code after setting the value//GEN-FIRST:status2_3


        // .//GEN-LAST:status2_3
        // .//GEN-BEGIN:status2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setStatus2() method.
     * @param status2 New value of property status2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateStatus2(java.lang.String status2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setStatus2(status2);
    }

    /** Use this method to validate a value for the property status2.
     * @param status2 Value to be validated for the property status2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateStatus2(java.lang.String status2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:status2_2_be
        // Add custom code before validation//GEN-FIRST:status2_1


        // .//GEN-LAST:status2_1
        // .//GEN-BEGIN:status2_3_be
        status2 = FieldValidator.validate(status2, (StringFieldMetaData) ItemMeta.META_STATUS2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.STATUS2, status2, this.getUOW());

        // .//GEN-END:status2_3_be
        // Add custom code after a successful validation//GEN-FIRST:status2_2


        // .//GEN-LAST:status2_2
        // .//GEN-BEGIN:status2_4_be
        return status2;
    }
    // .//GEN-END:status2_4_be
    // .//GEN-BEGIN:status3_be
    /** Getter for property status3.
     * @return Value of property status3.
     */
    public java.lang.String getStatus3() {
        return m_status3;
    }
    
    /** Use this method to update the property status3.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param status3 New value of property status3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setStatus3(java.lang.String status3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_status3 == null ? status3 == null : m_status3.equals(status3))
            return;


        status3 = validateStatus3(status3);
        // .//GEN-END:status3_be
        // Add custom code before setting the value//GEN-FIRST:status3


        // .//GEN-LAST:status3
        // .//GEN-BEGIN:status3_1_be
        super.update();
        super.addInitialValue(ItemMeta.STATUS3, m_status3);
        m_status3 = status3;
        // .//GEN-END:status3_1_be
        // Add custom code after setting the value//GEN-FIRST:status3_3


        // .//GEN-LAST:status3_3
        // .//GEN-BEGIN:status3_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setStatus3() method.
     * @param status3 New value of property status3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateStatus3(java.lang.String status3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setStatus3(status3);
    }

    /** Use this method to validate a value for the property status3.
     * @param status3 Value to be validated for the property status3.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateStatus3(java.lang.String status3)
    throws ValidationException, FrameworkException {
        // .//GEN-END:status3_2_be
        // Add custom code before validation//GEN-FIRST:status3_1


        // .//GEN-LAST:status3_1
        // .//GEN-BEGIN:status3_3_be
        status3 = FieldValidator.validate(status3, (StringFieldMetaData) ItemMeta.META_STATUS3, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.STATUS3, status3, this.getUOW());

        // .//GEN-END:status3_3_be
        // Add custom code after a successful validation//GEN-FIRST:status3_2


        // .//GEN-LAST:status3_2
        // .//GEN-BEGIN:status3_4_be
        return status3;
    }
    // .//GEN-END:status3_4_be
    // .//GEN-BEGIN:price_be
    /** Getter for property price.
     * @return Value of property price.
     */
    public java.lang.Double getPrice() {
        return m_price;
    }
    
    /** Use this method to update the property price.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param price New value of property price.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPrice(java.lang.Double price)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_price == null ? price == null : m_price.equals(price))
            return;


        price = validatePrice(price);
        // .//GEN-END:price_be
        // Add custom code before setting the value//GEN-FIRST:price


        // .//GEN-LAST:price
        // .//GEN-BEGIN:price_1_be
        super.update();
        super.addInitialValue(ItemMeta.PRICE, m_price);
        m_price = price;
        // .//GEN-END:price_1_be
        // Add custom code after setting the value//GEN-FIRST:price_3


        // .//GEN-LAST:price_3
        // .//GEN-BEGIN:price_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPrice() method.
     * @param price New value of property price.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePrice(java.lang.Double price)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPrice(price);
    }

    /** Use this method to validate a value for the property price.
     * @param price Value to be validated for the property price.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Double validatePrice(java.lang.Double price)
    throws ValidationException, FrameworkException {
        // .//GEN-END:price_2_be
        // Add custom code before validation//GEN-FIRST:price_1


        // .//GEN-LAST:price_1
        // .//GEN-BEGIN:price_3_be
        price = FieldValidator.validate(price, (DecimalFieldMetaData) ItemMeta.META_PRICE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), ItemMeta.PRICE, price, this.getUOW());

        // .//GEN-END:price_3_be
        // Add custom code after a successful validation//GEN-FIRST:price_2


        // .//GEN-LAST:price_2
        // .//GEN-BEGIN:price_4_be
        return price;
    }
    // .//GEN-END:price_4_be
    // .//GEN-BEGIN:partObject_1_be
    /** Returns the related foreign Part object.
     * The object is lazy-loaded.
     * @return the related foreign Part object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Part getPartObject() throws ValidationException, FrameworkException  {
        findPartObject(false);
        return m_partObject;
    }
    
    /** Finds the related foreign Part object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findPartObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_partObject == null && getPart() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PartMeta.getName());
                criteria.addCriteria(PartMeta.PART, getPart());
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
                        m_partObject = (Part) itr.next();
                }
                if (m_partObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(ItemMeta.META_PART.getLabelToken(), new Object[] {PartMeta.getLabelToken(), PartMeta.META_PART.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:partObject_1_be
    // .//GEN-BEGIN:conditionObject_1_be
    /** Returns the related foreign Condition object.
     * The object is lazy-loaded.
     * @return the related foreign Condition object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Condition getConditionObject() throws ValidationException, FrameworkException  {
        findConditionObject(false);
        return m_conditionObject;
    }
    
    /** Finds the related foreign Condition object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findConditionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_conditionObject == null && getCondition() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(ConditionMeta.getName());
                criteria.addCriteria(ConditionMeta.CONDITION, getCondition());
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
                        m_conditionObject = (Condition) itr.next();
                }
                if (m_conditionObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(ItemMeta.META_CONDITION.getLabelToken(), new Object[] {ConditionMeta.getLabelToken(), ConditionMeta.META_CONDITION.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:conditionObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Item>");
        buf.append("<itemId>"); if (m_itemId != null) buf.append(m_itemId); buf.append("</itemId>");
        buf.append("<receivedItemId>"); if (m_receivedItemId != null) buf.append(m_receivedItemId); buf.append("</receivedItemId>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<prime>"); if (m_prime != null) buf.append(m_prime); buf.append("</prime>");
        buf.append("<sc>"); if (m_sc != null) buf.append(m_sc); buf.append("</sc>");
        buf.append("<condition>"); if (m_condition != null) buf.append(m_condition); buf.append("</condition>");
        buf.append("<createdDatetime>"); if (m_createdDatetime != null) buf.append(m_createdDatetime); buf.append("</createdDatetime>");
        buf.append("<qty>"); if (m_qty != null) buf.append(m_qty); buf.append("</qty>");
        buf.append("<keyRef>"); if (m_keyRef != null) buf.append(m_keyRef); buf.append("</keyRef>");
        buf.append("<status1>"); if (m_status1 != null) buf.append(m_status1); buf.append("</status1>");
        buf.append("<status2>"); if (m_status2 != null) buf.append(m_status2); buf.append("</status2>");
        buf.append("<status3>"); if (m_status3 != null) buf.append(m_status3); buf.append("</status3>");
        buf.append("<price>"); if (m_price != null) buf.append(m_price); buf.append("</price>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Item>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Item obj = (Item) super.clone();
        obj.m_partObject = null;
        obj.m_conditionObject = null;
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
            if (isModified(ItemMeta.PART))
                findPartObject(true);
        } catch (ValidationException e) {
            appExps.add(e);
        }
        try {
            if (isModified(ItemMeta.CONDITION))
                findConditionObject(true);
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
     * @clientQualifier part
     * @supplierQualifier part
     * @link association
     */
    /*#Part lnkPart;*/

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier condition
     * @supplierQualifier condition
     * @link association
     */
    /*#Condition lnkCondition;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    public void preAdd() throws PreAddFailedException {
        try {
            setReceivedItemId("ADD" + getItemId());
        } catch (Exception e) {
            throw new PreAddFailedException(null, e);
        }
        super.preAdd();
    }
    
    public void preUpdate() throws PreUpdateFailedException {
        try {
            setReceivedItemId("UPD" + getItemId());
        } catch (Exception e) {
            throw new PreUpdateFailedException(null, e);
        }
        super.preUpdate();
    }
    
    public void preDelete() throws PreDeleteFailedException {
        try {
            setReceivedItemId("DEL" + getItemId());
        } catch (Exception e) {
            throw new PreDeleteFailedException(null, e);
        }
        super.preDelete();
    }

    // .//GEN-LAST:custom
}
