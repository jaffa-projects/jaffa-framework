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
import org.jaffa.persistence.domainobjects.Asset;
import org.jaffa.persistence.domainobjects.AssetMeta;
import org.jaffa.persistence.domainobjects.Item;
import org.jaffa.persistence.domainobjects.ItemMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the ZZ_JUT_CONDITION table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Condition extends Persistent {

    private static final Logger log = Logger.getLogger(Condition.class);

    /** Holds value of property condition. */
    @XmlElement(name="condition")
    private java.lang.String m_condition;

    /** Holds value of property description. */
    @XmlElement(name="description")
    private java.lang.String m_description;

    /** Holds related Asset objects. */
    private transient Collection m_assetCollection;

    /** Holds related Item objects. */
    private transient Collection m_itemCollection;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String condition) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(condition);
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
    public static Condition findByPK(UOW uow, java.lang.String condition) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(condition);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (Condition) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String condition) {
        Criteria criteria = new Criteria();
        criteria.setTable(ConditionMeta.getName());
        criteria.addCriteria(ConditionMeta.CONDITION, condition);
        return criteria;
    }
    // .//GEN-END:2_be
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
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCondition(java.lang.String condition)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_condition == null ? condition == null : m_condition.equals(condition))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        condition = validateCondition(condition);
        // .//GEN-END:condition_be
        // Add custom code before setting the value//GEN-FIRST:condition


        // .//GEN-LAST:condition
        // .//GEN-BEGIN:condition_1_be
        super.update();
        super.addInitialValue(ConditionMeta.CONDITION, m_condition);
        m_condition = condition;
        // .//GEN-END:condition_1_be
        // Add custom code after setting the value//GEN-FIRST:condition_3


        // .//GEN-LAST:condition_3
        // .//GEN-BEGIN:condition_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCondition() method.
     * @param condition New value of property condition.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCondition(java.lang.String condition)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        condition = FieldValidator.validate(condition, (StringFieldMetaData) ConditionMeta.META_CONDITION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ConditionMeta.getName(), ConditionMeta.CONDITION, condition, this.getUOW());

        // .//GEN-END:condition_3_be
        // Add custom code after a successful validation//GEN-FIRST:condition_2


        // .//GEN-LAST:condition_2
        // .//GEN-BEGIN:condition_4_be
        return condition;
    }
    // .//GEN-END:condition_4_be
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
        super.addInitialValue(ConditionMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) ConditionMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ConditionMeta.getName(), ConditionMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
    // .//GEN-BEGIN:assetArray_1_be
    /** Returns an array of related Asset objects.
     * @return an array of related Asset objects.
     * @throws FrameworkException Indicates some system error
     */
    public Asset[] getAssetArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            Asset[] output = null;
            if (m_assetCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findAssetCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_assetCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_assetCollection.add(itr.next());
            }

            if (m_assetCollection != null)
                output = (Asset[]) m_assetCollection.toArray(new Asset[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related Asset objects.
     * @return a Criteria object for retrieving the related Asset objects.
     */
    public Criteria findAssetCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(AssetMeta.getName());
        criteria.addCriteria(AssetMeta.CONDITION, getCondition());
        // .//GEN-END:assetArray_1_be
        // Add custom criteria//GEN-FIRST:assetArray_1


        // .//GEN-LAST:assetArray_1
        // .//GEN-BEGIN:assetArray_2_be
        return criteria;
    }
    /** Creates a new Asset object and initializes the related fields.
     * This will uncache the related Asset objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related Asset object with the initialized related fields.
     */
    public Asset newAssetObject()
    throws ValidationException, FrameworkException {
        m_assetCollection = null;
        Asset asset = new Asset();
        asset.setCondition(getCondition());
        // .//GEN-END:assetArray_2_be
        // Add custom code//GEN-FIRST:assetArray_2


        // .//GEN-LAST:assetArray_2
        // .//GEN-BEGIN:assetArray_3_be
        return asset;
    }
    // .//GEN-END:assetArray_3_be
    // .//GEN-BEGIN:itemArray_1_be
    /** Returns an array of related Item objects.
     * @return an array of related Item objects.
     * @throws FrameworkException Indicates some system error
     */
    public Item[] getItemArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            Item[] output = null;
            if (m_itemCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findItemCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_itemCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_itemCollection.add(itr.next());
            }

            if (m_itemCollection != null)
                output = (Item[]) m_itemCollection.toArray(new Item[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related Item objects.
     * @return a Criteria object for retrieving the related Item objects.
     */
    public Criteria findItemCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(ItemMeta.getName());
        criteria.addCriteria(ItemMeta.CONDITION, getCondition());
        // .//GEN-END:itemArray_1_be
        // Add custom criteria//GEN-FIRST:itemArray_1


        // .//GEN-LAST:itemArray_1
        // .//GEN-BEGIN:itemArray_2_be
        return criteria;
    }
    /** Creates a new Item object and initializes the related fields.
     * This will uncache the related Item objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related Item object with the initialized related fields.
     */
    public Item newItemObject()
    throws ValidationException, FrameworkException {
        m_itemCollection = null;
        Item item = new Item();
        item.setCondition(getCondition());
        // .//GEN-END:itemArray_2_be
        // Add custom code//GEN-FIRST:itemArray_2


        // .//GEN-LAST:itemArray_2
        // .//GEN-BEGIN:itemArray_3_be
        return item;
    }
    // .//GEN-END:itemArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Condition>");
        buf.append("<condition>"); if (m_condition != null) buf.append(m_condition); buf.append("</condition>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Condition>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Condition obj = (Condition) super.clone();
        obj.m_assetCollection = null;
        obj.m_itemCollection = null;
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
        Asset[] assetArray = null;
        try {
            assetArray = getAssetArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (assetArray != null && assetArray.length > 0) {
            // Stop the deletion !!
            throw new PreDeleteFailedException(null, new RelatedDomainObjectFoundException(AssetMeta.getLabelToken()));
        }
        Item[] itemArray = null;
        try {
            itemArray = getItemArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (itemArray != null && itemArray.length > 0) {
            // Stop the deletion !!
            throw new PreDeleteFailedException(null, new RelatedDomainObjectFoundException(ItemMeta.getLabelToken()));
        }
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier condition
     * @supplierQualifier condition
     * @link association
     */
    /*#Asset lnkAsset;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier condition
     * @supplierQualifier condition
     * @link association
     */
    /*#Item lnkItem;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
