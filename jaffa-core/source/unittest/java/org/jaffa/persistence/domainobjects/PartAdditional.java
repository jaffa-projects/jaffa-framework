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
 * Auto Generated Persistent class for the ZZ_JUT_PART_ADDITIONAL table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PartAdditional extends Persistent {

    private static final Logger log = Logger.getLogger(PartAdditional.class);

    /** Holds value of property part. */
    @XmlElement(name="part")
    private java.lang.String m_part;

    /** Holds value of property field1. */
    @XmlElement(name="field1")
    private java.lang.String m_field1;

    /** Holds value of property field2. */
    @XmlElement(name="field2")
    private java.lang.String m_field2;

    /** Holds value of property field3. */
    @XmlElement(name="field3")
    private java.lang.String m_field3;

    /** Holds related Part object. */
    private transient Part m_partObject;

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
    public static PartAdditional findByPK(UOW uow, java.lang.String part) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(part);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (PartAdditional) itr.next();
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
        criteria.setTable(PartAdditionalMeta.getName());
        criteria.addCriteria(PartAdditionalMeta.PART, part);
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
        super.addInitialValue(PartAdditionalMeta.PART, m_part);
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
        part = FieldValidator.validate(part, (StringFieldMetaData) PartAdditionalMeta.META_PART, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartAdditionalMeta.getName(), PartAdditionalMeta.PART, part, this.getUOW());

        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
        return part;
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:field1_be
    /** Getter for property field1.
     * @return Value of property field1.
     */
    public java.lang.String getField1() {
        return m_field1;
    }
    
    /** Use this method to update the property field1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param field1 New value of property field1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setField1(java.lang.String field1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_field1 == null ? field1 == null : m_field1.equals(field1))
            return;


        field1 = validateField1(field1);
        // .//GEN-END:field1_be
        // Add custom code before setting the value//GEN-FIRST:field1


        // .//GEN-LAST:field1
        // .//GEN-BEGIN:field1_1_be
        super.update();
        super.addInitialValue(PartAdditionalMeta.FIELD1, m_field1);
        m_field1 = field1;
        // .//GEN-END:field1_1_be
        // Add custom code after setting the value//GEN-FIRST:field1_3


        // .//GEN-LAST:field1_3
        // .//GEN-BEGIN:field1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setField1() method.
     * @param field1 New value of property field1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateField1(java.lang.String field1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setField1(field1);
    }

    /** Use this method to validate a value for the property field1.
     * @param field1 Value to be validated for the property field1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateField1(java.lang.String field1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:field1_2_be
        // Add custom code before validation//GEN-FIRST:field1_1


        // .//GEN-LAST:field1_1
        // .//GEN-BEGIN:field1_3_be
        field1 = FieldValidator.validate(field1, (StringFieldMetaData) PartAdditionalMeta.META_FIELD1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartAdditionalMeta.getName(), PartAdditionalMeta.FIELD1, field1, this.getUOW());

        // .//GEN-END:field1_3_be
        // Add custom code after a successful validation//GEN-FIRST:field1_2


        // .//GEN-LAST:field1_2
        // .//GEN-BEGIN:field1_4_be
        return field1;
    }
    // .//GEN-END:field1_4_be
    // .//GEN-BEGIN:field2_be
    /** Getter for property field2.
     * @return Value of property field2.
     */
    public java.lang.String getField2() {
        return m_field2;
    }
    
    /** Use this method to update the property field2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param field2 New value of property field2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setField2(java.lang.String field2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_field2 == null ? field2 == null : m_field2.equals(field2))
            return;


        field2 = validateField2(field2);
        // .//GEN-END:field2_be
        // Add custom code before setting the value//GEN-FIRST:field2


        // .//GEN-LAST:field2
        // .//GEN-BEGIN:field2_1_be
        super.update();
        super.addInitialValue(PartAdditionalMeta.FIELD2, m_field2);
        m_field2 = field2;
        // .//GEN-END:field2_1_be
        // Add custom code after setting the value//GEN-FIRST:field2_3


        // .//GEN-LAST:field2_3
        // .//GEN-BEGIN:field2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setField2() method.
     * @param field2 New value of property field2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateField2(java.lang.String field2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setField2(field2);
    }

    /** Use this method to validate a value for the property field2.
     * @param field2 Value to be validated for the property field2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateField2(java.lang.String field2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:field2_2_be
        // Add custom code before validation//GEN-FIRST:field2_1


        // .//GEN-LAST:field2_1
        // .//GEN-BEGIN:field2_3_be
        field2 = FieldValidator.validate(field2, (StringFieldMetaData) PartAdditionalMeta.META_FIELD2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartAdditionalMeta.getName(), PartAdditionalMeta.FIELD2, field2, this.getUOW());

        // .//GEN-END:field2_3_be
        // Add custom code after a successful validation//GEN-FIRST:field2_2


        // .//GEN-LAST:field2_2
        // .//GEN-BEGIN:field2_4_be
        return field2;
    }
    // .//GEN-END:field2_4_be
    // .//GEN-BEGIN:field3_be
    /** Getter for property field3.
     * @return Value of property field3.
     */
    public java.lang.String getField3() {
        return m_field3;
    }
    
    /** Use this method to update the property field3.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param field3 New value of property field3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setField3(java.lang.String field3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_field3 == null ? field3 == null : m_field3.equals(field3))
            return;


        field3 = validateField3(field3);
        // .//GEN-END:field3_be
        // Add custom code before setting the value//GEN-FIRST:field3


        // .//GEN-LAST:field3
        // .//GEN-BEGIN:field3_1_be
        super.update();
        super.addInitialValue(PartAdditionalMeta.FIELD3, m_field3);
        m_field3 = field3;
        // .//GEN-END:field3_1_be
        // Add custom code after setting the value//GEN-FIRST:field3_3


        // .//GEN-LAST:field3_3
        // .//GEN-BEGIN:field3_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setField3() method.
     * @param field3 New value of property field3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateField3(java.lang.String field3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setField3(field3);
    }

    /** Use this method to validate a value for the property field3.
     * @param field3 Value to be validated for the property field3.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateField3(java.lang.String field3)
    throws ValidationException, FrameworkException {
        // .//GEN-END:field3_2_be
        // Add custom code before validation//GEN-FIRST:field3_1


        // .//GEN-LAST:field3_1
        // .//GEN-BEGIN:field3_3_be
        field3 = FieldValidator.validate(field3, (StringFieldMetaData) PartAdditionalMeta.META_FIELD3, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartAdditionalMeta.getName(), PartAdditionalMeta.FIELD3, field3, this.getUOW());

        // .//GEN-END:field3_3_be
        // Add custom code after a successful validation//GEN-FIRST:field3_2


        // .//GEN-LAST:field3_2
        // .//GEN-BEGIN:field3_4_be
        return field3;
    }
    // .//GEN-END:field3_4_be
    // .//GEN-BEGIN:partObject_1_be
    /** Returns a related Part object.
     * @return a related Part object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Part getPartObject() throws ValidationException, FrameworkException {
        findPartObject(false);
        return m_partObject;
    }
    
    /** Finds the related Part object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
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
                    throw new InvalidForeignKeyException(PartAdditionalMeta.META_PART.getLabelToken(), new Object[] {PartMeta.getLabelToken(), PartMeta.META_PART.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:partObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PartAdditional>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<field1>"); if (m_field1 != null) buf.append(m_field1); buf.append("</field1>");
        buf.append("<field2>"); if (m_field2 != null) buf.append(m_field2); buf.append("</field2>");
        buf.append("<field3>"); if (m_field3 != null) buf.append(m_field3); buf.append("</field3>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</PartAdditional>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        PartAdditional obj = (PartAdditional) super.clone();
        obj.m_partObject = null;
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
            if (isModified(PartAdditionalMeta.PART))
                findPartObject(true);
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
     * @clientCardinality 0..1
     * @supplierCardinality 1
     * @clientQualifier part
     * @supplierQualifier part
     * @link association
     */
    /*#Part lnkPart;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
