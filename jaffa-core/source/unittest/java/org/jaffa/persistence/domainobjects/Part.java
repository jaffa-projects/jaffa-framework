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
import org.jaffa.persistence.domainobjects.CategoryOfInstrument;
import org.jaffa.persistence.domainobjects.CategoryOfInstrumentMeta;
import org.jaffa.persistence.domainobjects.PartRemarks;
import org.jaffa.persistence.domainobjects.PartRemarksMeta;
import org.jaffa.persistence.domainobjects.PartPicture;
import org.jaffa.persistence.domainobjects.PartPictureMeta;
import org.jaffa.persistence.domainobjects.PartRemarksPicture;
import org.jaffa.persistence.domainobjects.PartRemarksPictureMeta;
import org.jaffa.persistence.domainobjects.PartAdditional;
import org.jaffa.persistence.domainobjects.PartAdditionalMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the ZZ_JUT_PART table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Part extends Persistent {

    private static final Logger log = Logger.getLogger(Part.class);

    /** Holds value of property part. */
    @XmlElement(name="part")
    private java.lang.String m_part;

    /** Holds value of property noun. */
    @XmlElement(name="noun")
    private java.lang.String m_noun;

    /** Holds value of property categoryInstrument. */
    @XmlElement(name="categoryInstrument")
    private java.lang.String m_categoryInstrument;

    /** Holds related Asset objects. */
    private transient Collection m_assetCollection;

    /** Holds related Item objects. */
    private transient Collection m_itemCollection;

    /** Holds related foreign CategoryOfInstrument object. */
    private transient CategoryOfInstrument m_categoryOfInstrumentObject;

    /** Holds related PartRemarks object. */
    private transient PartRemarks m_partRemarksObject;

    /** Holds related PartPicture object. */
    private transient PartPicture m_partPictureObject;

    /** Holds related PartRemarksPicture object. */
    private transient PartRemarksPicture m_partRemarksPictureObject;

    /** Holds related PartAdditional object. */
    private transient PartAdditional m_partAdditionalObject;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String part) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(part);
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
    public static Part findByPK(UOW uow, java.lang.String part) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(part);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (Part) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String part) {
        Criteria criteria = new Criteria();
        criteria.setTable(PartMeta.getName());
        criteria.addCriteria(PartMeta.PART, part);
        return criteria;
    }
    // .//GEN-END:2_be
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
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPart(java.lang.String part)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_part == null ? part == null : m_part.equals(part))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        part = validatePart(part);
        // .//GEN-END:part_be
        // Add custom code before setting the value//GEN-FIRST:part


        // .//GEN-LAST:part
        // .//GEN-BEGIN:part_1_be
        super.update();
        super.addInitialValue(PartMeta.PART, m_part);
        m_part = part;
        m_partRemarksObject = null;
        m_partPictureObject = null;
        m_partRemarksPictureObject = null;
        m_partAdditionalObject = null;
        // .//GEN-END:part_1_be
        // Add custom code after setting the value//GEN-FIRST:part_3


        // .//GEN-LAST:part_3
        // .//GEN-BEGIN:part_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPart() method.
     * @param part New value of property part.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePart(java.lang.String part)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        part = FieldValidator.validate(part, (StringFieldMetaData) PartMeta.META_PART, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartMeta.getName(), PartMeta.PART, part, this.getUOW());

        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
        return part;
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:noun_be
    /** Getter for property noun.
     * @return Value of property noun.
     */
    public java.lang.String getNoun() {
        return m_noun;
    }
    
    /** Use this method to update the property noun.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param noun New value of property noun.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setNoun(java.lang.String noun)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_noun == null ? noun == null : m_noun.equals(noun))
            return;


        noun = validateNoun(noun);
        // .//GEN-END:noun_be
        // Add custom code before setting the value//GEN-FIRST:noun


        // .//GEN-LAST:noun
        // .//GEN-BEGIN:noun_1_be
        super.update();
        super.addInitialValue(PartMeta.NOUN, m_noun);
        m_noun = noun;
        // .//GEN-END:noun_1_be
        // Add custom code after setting the value//GEN-FIRST:noun_3


        // .//GEN-LAST:noun_3
        // .//GEN-BEGIN:noun_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setNoun() method.
     * @param noun New value of property noun.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateNoun(java.lang.String noun)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setNoun(noun);
    }

    /** Use this method to validate a value for the property noun.
     * @param noun Value to be validated for the property noun.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateNoun(java.lang.String noun)
    throws ValidationException, FrameworkException {
        // .//GEN-END:noun_2_be
        // Add custom code before validation//GEN-FIRST:noun_1


        // .//GEN-LAST:noun_1
        // .//GEN-BEGIN:noun_3_be
        noun = FieldValidator.validate(noun, (StringFieldMetaData) PartMeta.META_NOUN, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartMeta.getName(), PartMeta.NOUN, noun, this.getUOW());

        // .//GEN-END:noun_3_be
        // Add custom code after a successful validation//GEN-FIRST:noun_2


        // .//GEN-LAST:noun_2
        // .//GEN-BEGIN:noun_4_be
        return noun;
    }
    // .//GEN-END:noun_4_be
    // .//GEN-BEGIN:categoryInstrument_be
    /** Getter for property categoryInstrument.
     * @return Value of property categoryInstrument.
     */
    public java.lang.String getCategoryInstrument() {
        return m_categoryInstrument;
    }
    
    /** Use this method to update the property categoryInstrument.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param categoryInstrument New value of property categoryInstrument.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCategoryInstrument(java.lang.String categoryInstrument)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_categoryInstrument == null ? categoryInstrument == null : m_categoryInstrument.equals(categoryInstrument))
            return;


        categoryInstrument = validateCategoryInstrument(categoryInstrument);
        // .//GEN-END:categoryInstrument_be
        // Add custom code before setting the value//GEN-FIRST:categoryInstrument


        // .//GEN-LAST:categoryInstrument
        // .//GEN-BEGIN:categoryInstrument_1_be
        super.update();
        super.addInitialValue(PartMeta.CATEGORY_INSTRUMENT, m_categoryInstrument);
        m_categoryInstrument = categoryInstrument;
        m_categoryOfInstrumentObject = null;
        // .//GEN-END:categoryInstrument_1_be
        // Add custom code after setting the value//GEN-FIRST:categoryInstrument_3


        // .//GEN-LAST:categoryInstrument_3
        // .//GEN-BEGIN:categoryInstrument_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCategoryInstrument() method.
     * @param categoryInstrument New value of property categoryInstrument.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCategoryInstrument(java.lang.String categoryInstrument)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCategoryInstrument(categoryInstrument);
    }

    /** Use this method to validate a value for the property categoryInstrument.
     * @param categoryInstrument Value to be validated for the property categoryInstrument.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCategoryInstrument(java.lang.String categoryInstrument)
    throws ValidationException, FrameworkException {
        // .//GEN-END:categoryInstrument_2_be
        // Add custom code before validation//GEN-FIRST:categoryInstrument_1


        // .//GEN-LAST:categoryInstrument_1
        // .//GEN-BEGIN:categoryInstrument_3_be
        categoryInstrument = FieldValidator.validate(categoryInstrument, (StringFieldMetaData) PartMeta.META_CATEGORY_INSTRUMENT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartMeta.getName(), PartMeta.CATEGORY_INSTRUMENT, categoryInstrument, this.getUOW());

        // .//GEN-END:categoryInstrument_3_be
        // Add custom code after a successful validation//GEN-FIRST:categoryInstrument_2


        // .//GEN-LAST:categoryInstrument_2
        // .//GEN-BEGIN:categoryInstrument_4_be
        return categoryInstrument;
    }
    // .//GEN-END:categoryInstrument_4_be
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
        criteria.addCriteria(AssetMeta.PART, getPart());
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
        asset.setPart(getPart());
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
        criteria.addCriteria(ItemMeta.PART, getPart());
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
        item.setPart(getPart());
        // .//GEN-END:itemArray_2_be
        // Add custom code//GEN-FIRST:itemArray_2


        // .//GEN-LAST:itemArray_2
        // .//GEN-BEGIN:itemArray_3_be
        return item;
    }
    // .//GEN-END:itemArray_3_be
    // .//GEN-BEGIN:categoryOfInstrumentObject_1_be
    /** Returns the related foreign CategoryOfInstrument object.
     * The object is lazy-loaded.
     * @return the related foreign CategoryOfInstrument object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public CategoryOfInstrument getCategoryOfInstrumentObject() throws ValidationException, FrameworkException  {
        findCategoryOfInstrumentObject(false);
        return m_categoryOfInstrumentObject;
    }
    
    /** Finds the related foreign CategoryOfInstrument object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findCategoryOfInstrumentObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_categoryOfInstrumentObject == null && getCategoryInstrument() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(CategoryOfInstrumentMeta.getName());
                criteria.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, getCategoryInstrument());
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
                        m_categoryOfInstrumentObject = (CategoryOfInstrument) itr.next();
                }
                if (m_categoryOfInstrumentObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(PartMeta.META_CATEGORY_INSTRUMENT.getLabelToken(), new Object[] {CategoryOfInstrumentMeta.getLabelToken(), CategoryOfInstrumentMeta.META_CATEGORY_INSTRUMENT.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:categoryOfInstrumentObject_1_be
    // .//GEN-BEGIN:partRemarksObject_1_be
    /** Returns a related PartRemarks object.
     * @return a related PartRemarks object.
     * @throws FrameworkException Indicates some system error
     */
    public PartRemarks getPartRemarksObject() throws FrameworkException {
        findPartRemarksObject(false);
        return m_partRemarksObject;
    }
    
    /** Finds the related PartRemarks object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findPartRemarksObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_partRemarksObject == null && getPart() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PartRemarksMeta.getName());
                criteria.addCriteria(PartRemarksMeta.PART, getPart());
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
                        m_partRemarksObject = (PartRemarks) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new PartRemarks object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related PartRemarks object with the initialized related fields.
     */
    public PartRemarks newPartRemarksObject()
    throws ValidationException, FrameworkException {
        PartRemarks partRemarks = new PartRemarks();
        partRemarks.setPart(getPart());
        // .//GEN-END:partRemarksObject_1_be
        // Add custom code//GEN-FIRST:partRemarksObject_1


        // .//GEN-LAST:partRemarksObject_1
        // .//GEN-BEGIN:partRemarksObject_2_be
        return partRemarks;
    }
    // .//GEN-END:partRemarksObject_2_be
    // .//GEN-BEGIN:partPictureObject_1_be
    /** Returns a related PartPicture object.
     * @return a related PartPicture object.
     * @throws FrameworkException Indicates some system error
     */
    public PartPicture getPartPictureObject() throws FrameworkException {
        findPartPictureObject(false);
        return m_partPictureObject;
    }
    
    /** Finds the related PartPicture object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findPartPictureObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_partPictureObject == null && getPart() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PartPictureMeta.getName());
                criteria.addCriteria(PartPictureMeta.PART, getPart());
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
                        m_partPictureObject = (PartPicture) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new PartPicture object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related PartPicture object with the initialized related fields.
     */
    public PartPicture newPartPictureObject()
    throws ValidationException, FrameworkException {
        PartPicture partPicture = new PartPicture();
        partPicture.setPart(getPart());
        // .//GEN-END:partPictureObject_1_be
        // Add custom code//GEN-FIRST:partPictureObject_1


        // .//GEN-LAST:partPictureObject_1
        // .//GEN-BEGIN:partPictureObject_2_be
        return partPicture;
    }
    // .//GEN-END:partPictureObject_2_be
    // .//GEN-BEGIN:partRemarksPictureObject_1_be
    /** Returns a related PartRemarksPicture object.
     * @return a related PartRemarksPicture object.
     * @throws FrameworkException Indicates some system error
     */
    public PartRemarksPicture getPartRemarksPictureObject() throws FrameworkException {
        findPartRemarksPictureObject(false);
        return m_partRemarksPictureObject;
    }
    
    /** Finds the related PartRemarksPicture object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findPartRemarksPictureObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_partRemarksPictureObject == null && getPart() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PartRemarksPictureMeta.getName());
                criteria.addCriteria(PartRemarksPictureMeta.PART, getPart());
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
                        m_partRemarksPictureObject = (PartRemarksPicture) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new PartRemarksPicture object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related PartRemarksPicture object with the initialized related fields.
     */
    public PartRemarksPicture newPartRemarksPictureObject()
    throws ValidationException, FrameworkException {
        PartRemarksPicture partRemarksPicture = new PartRemarksPicture();
        partRemarksPicture.setPart(getPart());
        // .//GEN-END:partRemarksPictureObject_1_be
        // Add custom code//GEN-FIRST:partRemarksPictureObject_1


        // .//GEN-LAST:partRemarksPictureObject_1
        // .//GEN-BEGIN:partRemarksPictureObject_2_be
        return partRemarksPicture;
    }
    // .//GEN-END:partRemarksPictureObject_2_be
    // .//GEN-BEGIN:partAdditionalObject_1_be
    /** Returns a related PartAdditional object.
     * @return a related PartAdditional object.
     * @throws FrameworkException Indicates some system error
     */
    public PartAdditional getPartAdditionalObject() throws FrameworkException {
        findPartAdditionalObject(false);
        return m_partAdditionalObject;
    }
    
    /** Finds the related PartAdditional object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findPartAdditionalObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_partAdditionalObject == null && getPart() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PartAdditionalMeta.getName());
                criteria.addCriteria(PartAdditionalMeta.PART, getPart());
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
                        m_partAdditionalObject = (PartAdditional) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new PartAdditional object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related PartAdditional object with the initialized related fields.
     */
    public PartAdditional newPartAdditionalObject()
    throws ValidationException, FrameworkException {
        PartAdditional partAdditional = new PartAdditional();
        partAdditional.setPart(getPart());
        // .//GEN-END:partAdditionalObject_1_be
        // Add custom code//GEN-FIRST:partAdditionalObject_1


        // .//GEN-LAST:partAdditionalObject_1
        // .//GEN-BEGIN:partAdditionalObject_2_be
        return partAdditional;
    }
    // .//GEN-END:partAdditionalObject_2_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Part>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<noun>"); if (m_noun != null) buf.append(m_noun); buf.append("</noun>");
        buf.append("<categoryInstrument>"); if (m_categoryInstrument != null) buf.append(m_categoryInstrument); buf.append("</categoryInstrument>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Part>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        Part obj = (Part) super.clone();
        obj.m_assetCollection = null;
        obj.m_itemCollection = null;
        obj.m_categoryOfInstrumentObject = null;
        obj.m_partRemarksObject = null;
        obj.m_partPictureObject = null;
        obj.m_partRemarksPictureObject = null;
        obj.m_partAdditionalObject = null;
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
            if (isModified(PartMeta.CATEGORY_INSTRUMENT))
                findCategoryOfInstrumentObject(true);
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
        Asset[] assetArray = null;
        try {
            assetArray = getAssetArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (assetArray != null && assetArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < assetArray.length; i++)
                    getUOW().delete(assetArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        Item[] itemArray = null;
        try {
            itemArray = getItemArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (itemArray != null && itemArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < itemArray.length; i++)
                    getUOW().delete(itemArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        PartRemarks partRemarksObject = null;
        try {
            partRemarksObject = getPartRemarksObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (partRemarksObject != null) {
            // Stop the deletion !!
            throw new PreDeleteFailedException(null, new RelatedDomainObjectFoundException(PartRemarksMeta.getLabelToken()));
        }
        PartPicture partPictureObject = null;
        try {
            partPictureObject = getPartPictureObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (partPictureObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(partPictureObject);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        PartRemarksPicture partRemarksPictureObject = null;
        try {
            partRemarksPictureObject = getPartRemarksPictureObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (partRemarksPictureObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(partRemarksPictureObject);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        PartAdditional partAdditionalObject = null;
        try {
            partAdditionalObject = getPartAdditionalObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (partAdditionalObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(partAdditionalObject);
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
     * @clientQualifier part
     * @supplierQualifier part
     * @link composition
     */
    /*#Asset lnkAsset;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier part
     * @supplierQualifier part
     * @link composition
     */
    /*#Item lnkItem;*/

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier categoryInstrument
     * @supplierQualifier categoryInstrument
     * @link association
     */
    /*#CategoryOfInstrument lnkCategoryOfInstrument;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier part
     * @supplierQualifier part
     * @link association
     */
    /*#PartRemarks lnkPartRemarks;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier part
     * @supplierQualifier part
     * @link composition
     */
    /*#PartPicture lnkPartPicture;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier part
     * @supplierQualifier part
     * @link composition
     */
    /*#PartRemarksPicture lnkPartRemarksPicture;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier part
     * @supplierQualifier part
     * @link composition
     */
    /*#PartAdditional lnkPartAdditional;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
