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
 * Auto Generated Persistent class for the ZZ_JUT_PART_REM_PIC table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PartRemarksPicture extends Persistent {

    private static final Logger log = Logger.getLogger(PartRemarksPicture.class);

    /** Holds value of property part. */
    @XmlElement(name="part")
    private java.lang.String m_part;

    /** Holds value of property remarks. */
    @XmlElement(name="remarks")
    private java.lang.String m_remarks;

    /** Holds value of property picture. */
    @XmlElement(name="picture")
    private byte[] m_picture;

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
    public static PartRemarksPicture findByPK(UOW uow, java.lang.String part) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(part);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (PartRemarksPicture) itr.next();
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
        criteria.setTable(PartRemarksPictureMeta.getName());
        criteria.addCriteria(PartRemarksPictureMeta.PART, part);
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
        super.addInitialValue(PartRemarksPictureMeta.PART, m_part);
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
        part = FieldValidator.validate(part, (StringFieldMetaData) PartRemarksPictureMeta.META_PART, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartRemarksPictureMeta.getName(), PartRemarksPictureMeta.PART, part, this.getUOW());

        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
        return part;
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:remarks_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return m_remarks;
    }
    
    /** Use this method to update the property remarks.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param remarks New value of property remarks.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRemarks(java.lang.String remarks)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_remarks == null ? remarks == null : m_remarks.equals(remarks))
            return;


        remarks = validateRemarks(remarks);
        // .//GEN-END:remarks_be
        // Add custom code before setting the value//GEN-FIRST:remarks


        // .//GEN-LAST:remarks
        // .//GEN-BEGIN:remarks_1_be
        super.update();
        super.addInitialValue(PartRemarksPictureMeta.REMARKS, m_remarks);
        m_remarks = remarks;
        // .//GEN-END:remarks_1_be
        // Add custom code after setting the value//GEN-FIRST:remarks_3


        // .//GEN-LAST:remarks_3
        // .//GEN-BEGIN:remarks_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRemarks() method.
     * @param remarks New value of property remarks.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRemarks(java.lang.String remarks)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRemarks(remarks);
    }

    /** Use this method to validate a value for the property remarks.
     * @param remarks Value to be validated for the property remarks.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateRemarks(java.lang.String remarks)
    throws ValidationException, FrameworkException {
        // .//GEN-END:remarks_2_be
        // Add custom code before validation//GEN-FIRST:remarks_1


        // .//GEN-LAST:remarks_1
        // .//GEN-BEGIN:remarks_3_be
        remarks = FieldValidator.validate(remarks, (StringFieldMetaData) PartRemarksPictureMeta.META_REMARKS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartRemarksPictureMeta.getName(), PartRemarksPictureMeta.REMARKS, remarks, this.getUOW());

        // .//GEN-END:remarks_3_be
        // Add custom code after a successful validation//GEN-FIRST:remarks_2


        // .//GEN-LAST:remarks_2
        // .//GEN-BEGIN:remarks_4_be
        return remarks;
    }
    // .//GEN-END:remarks_4_be
    // .//GEN-BEGIN:picture_be
    /** Getter for property picture.
     * @return Value of property picture.
     */
    public byte[] getPicture() {
        return m_picture;
    }
    
    /** Use this method to update the property picture.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param picture New value of property picture.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPicture(byte[] picture)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_picture == null ? picture == null : m_picture.equals(picture))
            return;


        picture = validatePicture(picture);
        // .//GEN-END:picture_be
        // Add custom code before setting the value//GEN-FIRST:picture


        // .//GEN-LAST:picture
        // .//GEN-BEGIN:picture_1_be
        super.update();
        super.addInitialValue(PartRemarksPictureMeta.PICTURE, m_picture);
        m_picture = picture;
        // .//GEN-END:picture_1_be
        // Add custom code after setting the value//GEN-FIRST:picture_3


        // .//GEN-LAST:picture_3
        // .//GEN-BEGIN:picture_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPicture() method.
     * @param picture New value of property picture.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePicture(byte[] picture)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPicture(picture);
    }

    /** Use this method to validate a value for the property picture.
     * @param picture Value to be validated for the property picture.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validatePicture(byte[] picture)
    throws ValidationException, FrameworkException {
        // .//GEN-END:picture_2_be
        // Add custom code before validation//GEN-FIRST:picture_1


        // .//GEN-LAST:picture_1
        // .//GEN-BEGIN:picture_3_be
        picture = FieldValidator.validate(picture, (RawFieldMetaData) PartRemarksPictureMeta.META_PICTURE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PartRemarksPictureMeta.getName(), PartRemarksPictureMeta.PICTURE, picture, this.getUOW());

        // .//GEN-END:picture_3_be
        // Add custom code after a successful validation//GEN-FIRST:picture_2


        // .//GEN-LAST:picture_2
        // .//GEN-BEGIN:picture_4_be
        return picture;
    }
    // .//GEN-END:picture_4_be
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
                    throw new InvalidForeignKeyException(PartRemarksPictureMeta.META_PART.getLabelToken(), new Object[] {PartMeta.getLabelToken(), PartMeta.META_PART.getLabelToken()});
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
        buf.append("<PartRemarksPicture>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<remarks>"); if (m_remarks != null) buf.append(m_remarks); buf.append("</remarks>");
        buf.append("<picture>"); if (m_picture != null) buf.append(m_picture); buf.append("</picture>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</PartRemarksPicture>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        PartRemarksPicture obj = (PartRemarksPicture) super.clone();
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
            if (isModified(PartRemarksPictureMeta.PART))
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
