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
 * Auto Generated Persistent class for the ZZ_JUT_ASSET table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Asset extends Persistent {

    private static final Logger log = Logger.getLogger(Asset.class);

    /** Holds value of property assetTk. */
    @XmlElement(name="assetTk")
    private java.lang.Long m_assetTk;

    /** Holds value of property assetId. */
    @XmlElement(name="assetId")
    private java.lang.String m_assetId;

    /** Holds value of property part. */
    @XmlElement(name="part")
    private java.lang.String m_part;

    /** Holds value of property custodian. */
    @XmlElement(name="custodian")
    private java.lang.String m_custodian;

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

    /** Holds related foreign Part object. */
    private transient Part m_partObject;

    /** Holds related foreign Condition object. */
    private transient Condition m_conditionObject;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.Long assetTk) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(assetTk);
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
    public static Asset findByPK(UOW uow, java.lang.Long assetTk) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(assetTk);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (Asset) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.Long assetTk) {
        Criteria criteria = new Criteria();
        criteria.setTable(AssetMeta.getName());
        criteria.addCriteria(AssetMeta.ASSET_TK, assetTk);
        return criteria;
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:assetTk_be
    /** Getter for property assetTk.
     * @return Value of property assetTk.
     */
    public java.lang.Long getAssetTk() {
        return m_assetTk;
    }
    
    /** Use this method to update the property assetTk.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param assetTk New value of property assetTk.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setAssetTk(java.lang.Long assetTk)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_assetTk == null ? assetTk == null : m_assetTk.equals(assetTk))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        assetTk = validateAssetTk(assetTk);
        // .//GEN-END:assetTk_be
        // Add custom code before setting the value//GEN-FIRST:assetTk


        // .//GEN-LAST:assetTk
        // .//GEN-BEGIN:assetTk_1_be
        super.update();
        super.addInitialValue(AssetMeta.ASSET_TK, m_assetTk);
        m_assetTk = assetTk;
        // .//GEN-END:assetTk_1_be
        // Add custom code after setting the value//GEN-FIRST:assetTk_3


        // .//GEN-LAST:assetTk_3
        // .//GEN-BEGIN:assetTk_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setAssetTk() method.
     * @param assetTk New value of property assetTk.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateAssetTk(java.lang.Long assetTk)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setAssetTk(assetTk);
    }

    /** Use this method to validate a value for the property assetTk.
     * @param assetTk Value to be validated for the property assetTk.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateAssetTk(java.lang.Long assetTk)
    throws ValidationException, FrameworkException {
        // .//GEN-END:assetTk_2_be
        // Add custom code before validation//GEN-FIRST:assetTk_1


        // .//GEN-LAST:assetTk_1
        // .//GEN-BEGIN:assetTk_3_be
        assetTk = FieldValidator.validate(assetTk, (IntegerFieldMetaData) AssetMeta.META_ASSET_TK, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.ASSET_TK, assetTk, this.getUOW());

        // .//GEN-END:assetTk_3_be
        // Add custom code after a successful validation//GEN-FIRST:assetTk_2


        // .//GEN-LAST:assetTk_2
        // .//GEN-BEGIN:assetTk_4_be
        return assetTk;
    }
    // .//GEN-END:assetTk_4_be
    // .//GEN-BEGIN:assetId_be
    /** Getter for property assetId.
     * @return Value of property assetId.
     */
    public java.lang.String getAssetId() {
        return m_assetId;
    }
    
    /** Use this method to update the property assetId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param assetId New value of property assetId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setAssetId(java.lang.String assetId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_assetId == null ? assetId == null : m_assetId.equals(assetId))
            return;


        assetId = validateAssetId(assetId);
        // .//GEN-END:assetId_be
        // Add custom code before setting the value//GEN-FIRST:assetId


        // .//GEN-LAST:assetId
        // .//GEN-BEGIN:assetId_1_be
        super.update();
        super.addInitialValue(AssetMeta.ASSET_ID, m_assetId);
        m_assetId = assetId;
        // .//GEN-END:assetId_1_be
        // Add custom code after setting the value//GEN-FIRST:assetId_3


        // .//GEN-LAST:assetId_3
        // .//GEN-BEGIN:assetId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setAssetId() method.
     * @param assetId New value of property assetId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateAssetId(java.lang.String assetId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setAssetId(assetId);
    }

    /** Use this method to validate a value for the property assetId.
     * @param assetId Value to be validated for the property assetId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateAssetId(java.lang.String assetId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:assetId_2_be
        // Add custom code before validation//GEN-FIRST:assetId_1


        // .//GEN-LAST:assetId_1
        // .//GEN-BEGIN:assetId_3_be
        assetId = FieldValidator.validate(assetId, (StringFieldMetaData) AssetMeta.META_ASSET_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.ASSET_ID, assetId, this.getUOW());

        // .//GEN-END:assetId_3_be
        // Add custom code after a successful validation//GEN-FIRST:assetId_2


        // .//GEN-LAST:assetId_2
        // .//GEN-BEGIN:assetId_4_be
        return assetId;
    }
    // .//GEN-END:assetId_4_be
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
        super.addInitialValue(AssetMeta.PART, m_part);
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
        part = FieldValidator.validate(part, (StringFieldMetaData) AssetMeta.META_PART, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.PART, part, this.getUOW());

        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
        return part;
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:custodian_be
    /** Getter for property custodian.
     * @return Value of property custodian.
     */
    public java.lang.String getCustodian() {
        return m_custodian;
    }
    
    /** Use this method to update the property custodian.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param custodian New value of property custodian.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCustodian(java.lang.String custodian)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_custodian == null ? custodian == null : m_custodian.equals(custodian))
            return;


        custodian = validateCustodian(custodian);
        // .//GEN-END:custodian_be
        // Add custom code before setting the value//GEN-FIRST:custodian


        // .//GEN-LAST:custodian
        // .//GEN-BEGIN:custodian_1_be
        super.update();
        super.addInitialValue(AssetMeta.CUSTODIAN, m_custodian);
        m_custodian = custodian;
        // .//GEN-END:custodian_1_be
        // Add custom code after setting the value//GEN-FIRST:custodian_3


        // .//GEN-LAST:custodian_3
        // .//GEN-BEGIN:custodian_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCustodian() method.
     * @param custodian New value of property custodian.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCustodian(java.lang.String custodian)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCustodian(custodian);
    }

    /** Use this method to validate a value for the property custodian.
     * @param custodian Value to be validated for the property custodian.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCustodian(java.lang.String custodian)
    throws ValidationException, FrameworkException {
        // .//GEN-END:custodian_2_be
        // Add custom code before validation//GEN-FIRST:custodian_1


        // .//GEN-LAST:custodian_1
        // .//GEN-BEGIN:custodian_3_be
        custodian = FieldValidator.validate(custodian, (StringFieldMetaData) AssetMeta.META_CUSTODIAN, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.CUSTODIAN, custodian, this.getUOW());

        // .//GEN-END:custodian_3_be
        // Add custom code after a successful validation//GEN-FIRST:custodian_2


        // .//GEN-LAST:custodian_2
        // .//GEN-BEGIN:custodian_4_be
        return custodian;
    }
    // .//GEN-END:custodian_4_be
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
        super.addInitialValue(AssetMeta.CONDITION, m_condition);
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
        condition = FieldValidator.validate(condition, (StringFieldMetaData) AssetMeta.META_CONDITION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.CONDITION, condition, this.getUOW());

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
        super.addInitialValue(AssetMeta.CREATED_DATETIME, m_createdDatetime);
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
        createdDatetime = FieldValidator.validate(createdDatetime, (DateTimeFieldMetaData) AssetMeta.META_CREATED_DATETIME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.CREATED_DATETIME, createdDatetime, this.getUOW());

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
        super.addInitialValue(AssetMeta.QTY, m_qty);
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
        qty = FieldValidator.validate(qty, (IntegerFieldMetaData) AssetMeta.META_QTY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.QTY, qty, this.getUOW());

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
        super.addInitialValue(AssetMeta.KEY_REF, m_keyRef);
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
        keyRef = FieldValidator.validate(keyRef, (StringFieldMetaData) AssetMeta.META_KEY_REF, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(AssetMeta.getName(), AssetMeta.KEY_REF, keyRef, this.getUOW());

        // .//GEN-END:keyRef_3_be
        // Add custom code after a successful validation//GEN-FIRST:keyRef_2


        // .//GEN-LAST:keyRef_2
        // .//GEN-BEGIN:keyRef_4_be
        return keyRef;
    }
    // .//GEN-END:keyRef_4_be
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
                    throw new InvalidForeignKeyException(AssetMeta.META_PART.getLabelToken(), new Object[] {PartMeta.getLabelToken(), PartMeta.META_PART.getLabelToken()});
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
                    throw new InvalidForeignKeyException(AssetMeta.META_CONDITION.getLabelToken(), new Object[] {ConditionMeta.getLabelToken(), ConditionMeta.META_CONDITION.getLabelToken()});
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
        buf.append("<Asset>");
        buf.append("<assetTk>"); if (m_assetTk != null) buf.append(m_assetTk); buf.append("</assetTk>");
        buf.append("<assetId>"); if (m_assetId != null) buf.append(m_assetId); buf.append("</assetId>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<custodian>"); if (m_custodian != null) buf.append(m_custodian); buf.append("</custodian>");
        buf.append("<condition>"); if (m_condition != null) buf.append(m_condition); buf.append("</condition>");
        buf.append("<createdDatetime>"); if (m_createdDatetime != null) buf.append(m_createdDatetime); buf.append("</createdDatetime>");
        buf.append("<qty>"); if (m_qty != null) buf.append(m_qty); buf.append("</qty>");
        buf.append("<keyRef>"); if (m_keyRef != null) buf.append(m_keyRef); buf.append("</keyRef>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Asset>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Asset obj = (Asset) super.clone();
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
            if (isModified(AssetMeta.PART))
                findPartObject(true);
        } catch (ValidationException e) {
            appExps.add(e);
        }
        try {
            if (isModified(AssetMeta.CONDITION))
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






    // .//GEN-LAST:custom
}
