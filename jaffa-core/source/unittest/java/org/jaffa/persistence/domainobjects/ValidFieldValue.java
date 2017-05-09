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
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_VALID_FIELD_VALUES table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidFieldValue extends Persistent {

    private static final Logger log = Logger.getLogger(ValidFieldValue.class);

    /** Holds value of property tableName. */
    @XmlElement(name="tableName")
    private java.lang.String m_tableName;

    /** Holds value of property fieldName. */
    @XmlElement(name="fieldName")
    private java.lang.String m_fieldName;

    /** Holds value of property legalValue. */
    @XmlElement(name="legalValue")
    private java.lang.String m_legalValue;

    /** Holds value of property description. */
    @XmlElement(name="description")
    private java.lang.String m_description;

    /** Holds value of property remarks. */
    @XmlElement(name="remarks")
    private java.lang.String m_remarks;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(tableName, fieldName, legalValue);
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
    public static ValidFieldValue findByPK(UOW uow, java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(tableName, fieldName, legalValue);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (ValidFieldValue) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String tableName, java.lang.String fieldName, java.lang.String legalValue) {
        Criteria criteria = new Criteria();
        criteria.setTable(ValidFieldValueMeta.getName());
        criteria.addCriteria(ValidFieldValueMeta.TABLE_NAME, tableName);
        criteria.addCriteria(ValidFieldValueMeta.FIELD_NAME, fieldName);
        criteria.addCriteria(ValidFieldValueMeta.LEGAL_VALUE, legalValue);
        return criteria;
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:tableName_be
    /** Getter for property tableName.
     * @return Value of property tableName.
     */
    public java.lang.String getTableName() {
        return m_tableName;
    }
    
    /** Use this method to update the property tableName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param tableName New value of property tableName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTableName(java.lang.String tableName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_tableName == null ? tableName == null : m_tableName.equals(tableName))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        tableName = validateTableName(tableName);
        // .//GEN-END:tableName_be
        // Add custom code before setting the value//GEN-FIRST:tableName


        // .//GEN-LAST:tableName
        // .//GEN-BEGIN:tableName_1_be
        super.update();
        super.addInitialValue(ValidFieldValueMeta.TABLE_NAME, m_tableName);
        m_tableName = tableName;
        // .//GEN-END:tableName_1_be
        // Add custom code after setting the value//GEN-FIRST:tableName_3


        // .//GEN-LAST:tableName_3
        // .//GEN-BEGIN:tableName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setTableName() method.
     * @param tableName New value of property tableName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTableName(java.lang.String tableName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setTableName(tableName);
    }

    /** Use this method to validate a value for the property tableName.
     * @param tableName Value to be validated for the property tableName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateTableName(java.lang.String tableName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:tableName_2_be
        // Add custom code before validation//GEN-FIRST:tableName_1


        // .//GEN-LAST:tableName_1
        // .//GEN-BEGIN:tableName_3_be
        tableName = FieldValidator.validate(tableName, (StringFieldMetaData) ValidFieldValueMeta.META_TABLE_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ValidFieldValueMeta.getName(), ValidFieldValueMeta.TABLE_NAME, tableName, this.getUOW());

        // .//GEN-END:tableName_3_be
        // Add custom code after a successful validation//GEN-FIRST:tableName_2


        // .//GEN-LAST:tableName_2
        // .//GEN-BEGIN:tableName_4_be
        return tableName;
    }
    // .//GEN-END:tableName_4_be
    // .//GEN-BEGIN:fieldName_be
    /** Getter for property fieldName.
     * @return Value of property fieldName.
     */
    public java.lang.String getFieldName() {
        return m_fieldName;
    }
    
    /** Use this method to update the property fieldName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param fieldName New value of property fieldName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFieldName(java.lang.String fieldName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_fieldName == null ? fieldName == null : m_fieldName.equals(fieldName))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        fieldName = validateFieldName(fieldName);
        // .//GEN-END:fieldName_be
        // Add custom code before setting the value//GEN-FIRST:fieldName


        // .//GEN-LAST:fieldName
        // .//GEN-BEGIN:fieldName_1_be
        super.update();
        super.addInitialValue(ValidFieldValueMeta.FIELD_NAME, m_fieldName);
        m_fieldName = fieldName;
        // .//GEN-END:fieldName_1_be
        // Add custom code after setting the value//GEN-FIRST:fieldName_3


        // .//GEN-LAST:fieldName_3
        // .//GEN-BEGIN:fieldName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFieldName() method.
     * @param fieldName New value of property fieldName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFieldName(java.lang.String fieldName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFieldName(fieldName);
    }

    /** Use this method to validate a value for the property fieldName.
     * @param fieldName Value to be validated for the property fieldName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFieldName(java.lang.String fieldName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:fieldName_2_be
        // Add custom code before validation//GEN-FIRST:fieldName_1


        // .//GEN-LAST:fieldName_1
        // .//GEN-BEGIN:fieldName_3_be
        fieldName = FieldValidator.validate(fieldName, (StringFieldMetaData) ValidFieldValueMeta.META_FIELD_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ValidFieldValueMeta.getName(), ValidFieldValueMeta.FIELD_NAME, fieldName, this.getUOW());

        // .//GEN-END:fieldName_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldName_2


        // .//GEN-LAST:fieldName_2
        // .//GEN-BEGIN:fieldName_4_be
        return fieldName;
    }
    // .//GEN-END:fieldName_4_be
    // .//GEN-BEGIN:legalValue_be
    /** Getter for property legalValue.
     * @return Value of property legalValue.
     */
    public java.lang.String getLegalValue() {
        return m_legalValue;
    }
    
    /** Use this method to update the property legalValue.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param legalValue New value of property legalValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLegalValue(java.lang.String legalValue)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_legalValue == null ? legalValue == null : m_legalValue.equals(legalValue))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        legalValue = validateLegalValue(legalValue);
        // .//GEN-END:legalValue_be
        // Add custom code before setting the value//GEN-FIRST:legalValue


        // .//GEN-LAST:legalValue
        // .//GEN-BEGIN:legalValue_1_be
        super.update();
        super.addInitialValue(ValidFieldValueMeta.LEGAL_VALUE, m_legalValue);
        m_legalValue = legalValue;
        // .//GEN-END:legalValue_1_be
        // Add custom code after setting the value//GEN-FIRST:legalValue_3


        // .//GEN-LAST:legalValue_3
        // .//GEN-BEGIN:legalValue_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLegalValue() method.
     * @param legalValue New value of property legalValue.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLegalValue(java.lang.String legalValue)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLegalValue(legalValue);
    }

    /** Use this method to validate a value for the property legalValue.
     * @param legalValue Value to be validated for the property legalValue.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLegalValue(java.lang.String legalValue)
    throws ValidationException, FrameworkException {
        // .//GEN-END:legalValue_2_be
        // Add custom code before validation//GEN-FIRST:legalValue_1


        // .//GEN-LAST:legalValue_1
        // .//GEN-BEGIN:legalValue_3_be
        legalValue = FieldValidator.validate(legalValue, (StringFieldMetaData) ValidFieldValueMeta.META_LEGAL_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ValidFieldValueMeta.getName(), ValidFieldValueMeta.LEGAL_VALUE, legalValue, this.getUOW());

        // .//GEN-END:legalValue_3_be
        // Add custom code after a successful validation//GEN-FIRST:legalValue_2


        // .//GEN-LAST:legalValue_2
        // .//GEN-BEGIN:legalValue_4_be
        return legalValue;
    }
    // .//GEN-END:legalValue_4_be
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
        super.addInitialValue(ValidFieldValueMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) ValidFieldValueMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ValidFieldValueMeta.getName(), ValidFieldValueMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
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
        super.addInitialValue(ValidFieldValueMeta.REMARKS, m_remarks);
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
        remarks = FieldValidator.validate(remarks, (StringFieldMetaData) ValidFieldValueMeta.META_REMARKS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(ValidFieldValueMeta.getName(), ValidFieldValueMeta.REMARKS, remarks, this.getUOW());

        // .//GEN-END:remarks_3_be
        // Add custom code after a successful validation//GEN-FIRST:remarks_2


        // .//GEN-LAST:remarks_2
        // .//GEN-BEGIN:remarks_4_be
        return remarks;
    }
    // .//GEN-END:remarks_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<ValidFieldValue>");
        buf.append("<tableName>"); if (m_tableName != null) buf.append(m_tableName); buf.append("</tableName>");
        buf.append("<fieldName>"); if (m_fieldName != null) buf.append(m_fieldName); buf.append("</fieldName>");
        buf.append("<legalValue>"); if (m_legalValue != null) buf.append(m_legalValue); buf.append("</legalValue>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<remarks>"); if (m_remarks != null) buf.append(m_remarks); buf.append("</remarks>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</ValidFieldValue>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        ValidFieldValue obj = (ValidFieldValue) super.clone();
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
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
