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
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the ZZ_JUT_INSTRUMENT table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryOfInstrument extends Persistent {

    private static final Logger log = Logger.getLogger(CategoryOfInstrument.class);

    /** Holds value of property categoryInstrument. */
    @XmlElement(name="categoryInstrument")
    private java.lang.String m_categoryInstrument;

    /** Holds value of property description. */
    @XmlElement(name="description")
    private java.lang.String m_description;

    /** Holds value of property supportEquip. */
    @XmlElement(name="supportEquip")
    private java.lang.Boolean m_supportEquip;

    /** Holds value of property calculateMtbf. */
    @XmlElement(name="calculateMtbf")
    private java.lang.Boolean m_calculateMtbf;

    /** Holds related Part objects. */
    private transient Collection m_partCollection;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String categoryInstrument) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(categoryInstrument);
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
    public static CategoryOfInstrument findByPK(UOW uow, java.lang.String categoryInstrument) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(categoryInstrument);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (CategoryOfInstrument) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String categoryInstrument) {
        Criteria criteria = new Criteria();
        criteria.setTable(CategoryOfInstrumentMeta.getName());
        criteria.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, categoryInstrument);
        return criteria;
    }
    // .//GEN-END:2_be
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
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCategoryInstrument(java.lang.String categoryInstrument)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_categoryInstrument == null ? categoryInstrument == null : m_categoryInstrument.equals(categoryInstrument))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        categoryInstrument = validateCategoryInstrument(categoryInstrument);
        // .//GEN-END:categoryInstrument_be
        // Add custom code before setting the value//GEN-FIRST:categoryInstrument


        // .//GEN-LAST:categoryInstrument
        // .//GEN-BEGIN:categoryInstrument_1_be
        super.update();
        super.addInitialValue(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, m_categoryInstrument);
        m_categoryInstrument = categoryInstrument;
        // .//GEN-END:categoryInstrument_1_be
        // Add custom code after setting the value//GEN-FIRST:categoryInstrument_3


        // .//GEN-LAST:categoryInstrument_3
        // .//GEN-BEGIN:categoryInstrument_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCategoryInstrument() method.
     * @param categoryInstrument New value of property categoryInstrument.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCategoryInstrument(java.lang.String categoryInstrument)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        categoryInstrument = FieldValidator.validate(categoryInstrument, (StringFieldMetaData) CategoryOfInstrumentMeta.META_CATEGORY_INSTRUMENT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(CategoryOfInstrumentMeta.getName(), CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, categoryInstrument, this.getUOW());

        // .//GEN-END:categoryInstrument_3_be
        // Add custom code after a successful validation//GEN-FIRST:categoryInstrument_2


        // .//GEN-LAST:categoryInstrument_2
        // .//GEN-BEGIN:categoryInstrument_4_be
        return categoryInstrument;
    }
    // .//GEN-END:categoryInstrument_4_be
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
        super.addInitialValue(CategoryOfInstrumentMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) CategoryOfInstrumentMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(CategoryOfInstrumentMeta.getName(), CategoryOfInstrumentMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
    // .//GEN-BEGIN:supportEquip_be
    /** Getter for property supportEquip.
     * @return Value of property supportEquip.
     */
    public java.lang.Boolean getSupportEquip() {
        return m_supportEquip;
    }
    
    /** Use this method to update the property supportEquip.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param supportEquip New value of property supportEquip.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSupportEquip(java.lang.Boolean supportEquip)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_supportEquip == null ? supportEquip == null : m_supportEquip.equals(supportEquip))
            return;


        supportEquip = validateSupportEquip(supportEquip);
        // .//GEN-END:supportEquip_be
        // Add custom code before setting the value//GEN-FIRST:supportEquip


        // .//GEN-LAST:supportEquip
        // .//GEN-BEGIN:supportEquip_1_be
        super.update();
        super.addInitialValue(CategoryOfInstrumentMeta.SUPPORT_EQUIP, m_supportEquip);
        m_supportEquip = supportEquip;
        // .//GEN-END:supportEquip_1_be
        // Add custom code after setting the value//GEN-FIRST:supportEquip_3


        // .//GEN-LAST:supportEquip_3
        // .//GEN-BEGIN:supportEquip_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSupportEquip() method.
     * @param supportEquip New value of property supportEquip.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSupportEquip(java.lang.Boolean supportEquip)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSupportEquip(supportEquip);
    }

    /** Use this method to validate a value for the property supportEquip.
     * @param supportEquip Value to be validated for the property supportEquip.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateSupportEquip(java.lang.Boolean supportEquip)
    throws ValidationException, FrameworkException {
        // .//GEN-END:supportEquip_2_be
        // Add custom code before validation//GEN-FIRST:supportEquip_1


        // .//GEN-LAST:supportEquip_1
        // .//GEN-BEGIN:supportEquip_3_be
        supportEquip = FieldValidator.validate(supportEquip, (BooleanFieldMetaData) CategoryOfInstrumentMeta.META_SUPPORT_EQUIP, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(CategoryOfInstrumentMeta.getName(), CategoryOfInstrumentMeta.SUPPORT_EQUIP, supportEquip, this.getUOW());

        // .//GEN-END:supportEquip_3_be
        // Add custom code after a successful validation//GEN-FIRST:supportEquip_2


        // .//GEN-LAST:supportEquip_2
        // .//GEN-BEGIN:supportEquip_4_be
        return supportEquip;
    }
    // .//GEN-END:supportEquip_4_be
    // .//GEN-BEGIN:calculateMtbf_be
    /** Getter for property calculateMtbf.
     * @return Value of property calculateMtbf.
     */
    public java.lang.Boolean getCalculateMtbf() {
        return m_calculateMtbf;
    }
    
    /** Use this method to update the property calculateMtbf.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param calculateMtbf New value of property calculateMtbf.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCalculateMtbf(java.lang.Boolean calculateMtbf)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_calculateMtbf == null ? calculateMtbf == null : m_calculateMtbf.equals(calculateMtbf))
            return;


        calculateMtbf = validateCalculateMtbf(calculateMtbf);
        // .//GEN-END:calculateMtbf_be
        // Add custom code before setting the value//GEN-FIRST:calculateMtbf


        // .//GEN-LAST:calculateMtbf
        // .//GEN-BEGIN:calculateMtbf_1_be
        super.update();
        super.addInitialValue(CategoryOfInstrumentMeta.CALCULATE_MTBF, m_calculateMtbf);
        m_calculateMtbf = calculateMtbf;
        // .//GEN-END:calculateMtbf_1_be
        // Add custom code after setting the value//GEN-FIRST:calculateMtbf_3


        // .//GEN-LAST:calculateMtbf_3
        // .//GEN-BEGIN:calculateMtbf_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCalculateMtbf() method.
     * @param calculateMtbf New value of property calculateMtbf.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCalculateMtbf(java.lang.Boolean calculateMtbf)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCalculateMtbf(calculateMtbf);
    }

    /** Use this method to validate a value for the property calculateMtbf.
     * @param calculateMtbf Value to be validated for the property calculateMtbf.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateCalculateMtbf(java.lang.Boolean calculateMtbf)
    throws ValidationException, FrameworkException {
        // .//GEN-END:calculateMtbf_2_be
        // Add custom code before validation//GEN-FIRST:calculateMtbf_1


        // .//GEN-LAST:calculateMtbf_1
        // .//GEN-BEGIN:calculateMtbf_3_be
        calculateMtbf = FieldValidator.validate(calculateMtbf, (BooleanFieldMetaData) CategoryOfInstrumentMeta.META_CALCULATE_MTBF, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(CategoryOfInstrumentMeta.getName(), CategoryOfInstrumentMeta.CALCULATE_MTBF, calculateMtbf, this.getUOW());

        // .//GEN-END:calculateMtbf_3_be
        // Add custom code after a successful validation//GEN-FIRST:calculateMtbf_2


        // .//GEN-LAST:calculateMtbf_2
        // .//GEN-BEGIN:calculateMtbf_4_be
        return calculateMtbf;
    }
    // .//GEN-END:calculateMtbf_4_be
    // .//GEN-BEGIN:partArray_1_be
    /** Returns an array of related Part objects.
     * @return an array of related Part objects.
     * @throws FrameworkException Indicates some system error
     */
    public Part[] getPartArray() throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            Part[] output = null;
            if (m_partCollection == null && isDatabaseOccurence()) {
                Criteria criteria = findPartCriteria();
                if (uow == null || !uow.isActive()) {
                    uow = new UOW();
                    localUow = true;
                }
                Collection col = uow.query(criteria);
                m_partCollection = new ArrayList();
                for (Iterator itr = col.iterator(); itr.hasNext(); )
                    m_partCollection.add(itr.next());
            }

            if (m_partCollection != null)
                output = (Part[]) m_partCollection.toArray(new Part[0]);
            return output;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Returns a Criteria object for retrieving the related Part objects.
     * @return a Criteria object for retrieving the related Part objects.
     */
    public Criteria findPartCriteria() {
        Criteria criteria = new Criteria();
        criteria.setTable(PartMeta.getName());
        criteria.addCriteria(PartMeta.CATEGORY_INSTRUMENT, getCategoryInstrument());
        // .//GEN-END:partArray_1_be
        // Add custom criteria//GEN-FIRST:partArray_1


        // .//GEN-LAST:partArray_1
        // .//GEN-BEGIN:partArray_2_be
        return criteria;
    }
    /** Creates a new Part object and initializes the related fields.
     * This will uncache the related Part objects.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related Part object with the initialized related fields.
     */
    public Part newPartObject()
    throws ValidationException, FrameworkException {
        m_partCollection = null;
        Part part = new Part();
        part.setCategoryInstrument(getCategoryInstrument());
        // .//GEN-END:partArray_2_be
        // Add custom code//GEN-FIRST:partArray_2


        // .//GEN-LAST:partArray_2
        // .//GEN-BEGIN:partArray_3_be
        return part;
    }
    // .//GEN-END:partArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<CategoryOfInstrument>");
        buf.append("<categoryInstrument>"); if (m_categoryInstrument != null) buf.append(m_categoryInstrument); buf.append("</categoryInstrument>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<supportEquip>"); if (m_supportEquip != null) buf.append(m_supportEquip); buf.append("</supportEquip>");
        buf.append("<calculateMtbf>"); if (m_calculateMtbf != null) buf.append(m_calculateMtbf); buf.append("</calculateMtbf>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</CategoryOfInstrument>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        CategoryOfInstrument obj = (CategoryOfInstrument) super.clone();
        obj.m_partCollection = null;
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
        Part[] partArray = null;
        try {
            partArray = getPartArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (partArray != null && partArray.length > 0) {
            // Stop the deletion !!
            throw new PreDeleteFailedException(null, new RelatedDomainObjectFoundException(PartMeta.getLabelToken()));
        }
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier categoryInstrument
     * @supplierQualifier categoryInstrument
     * @link association
     */
    /*#Part lnkPart;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
