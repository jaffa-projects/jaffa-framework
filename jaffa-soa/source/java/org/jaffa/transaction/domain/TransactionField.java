// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.transaction.domain;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.adapters.DateOnlyType;
import org.jaffa.datatypes.adapters.DateTimeType;
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
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports

import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;



// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_TRANSACTION_FIELDS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "J_TRANSACTION_FIELDS")
@SqlResultSetMapping(name="TransactionField",entities={@EntityResult(entityClass=TransactionField.class)})
public class TransactionField extends Persistent {

    private static final Logger log = Logger.getLogger(TransactionField.class);

    /** Holds value of property m_compositeKey. */
    @XmlElement(name="compositeKey")
    @EmbeddedId
    private CompositeKey m_compositeKey;

    @XmlElement(name="value")
    @Column(name = "VALUE")
    private java.lang.String m_value;

    @Transient
    private java.lang.String m_transactionId;

    @Transient
 	private java.lang.String m_fieldName;

    private transient Transaction m_transactionObject;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public TransactionField() {
        StaticContext.configure(this);
    }

    /**
     * Check if the domain object exists for the input Primary Key.
     *
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String transactionId, java.lang.String fieldName) throws FrameworkException {
        // TODO this is generated code...
        return (findByPK(uow, transactionId, fieldName) != null);
    }

    /**
     * Returns the domain object for the input Primary Key.
     *
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static TransactionField findByPK(UOW uow, java.lang.String transactionId, java.lang.String fieldName) throws FrameworkException {
        // TODO this is generated code...
        TransactionField result = getTransactionDAO().getTransactionField(transactionId, fieldName);
        if (result != null) {
            result.setUOW(uow);
        }
        return result;
    }

    /**
     *  Returns a Criteria object for retrieving the domain object based on the input Primary Key.
     *
     * @return a Criteria object for retrieving the domain object based on the input Primary Key.
     */
    public static Criteria findByPKCriteria(java.lang.String transactionId, java.lang.String fieldName) {
        // TODO this is generated code...
        return null;
    }
    /**
     * Check for existence prior to adding to the database.
     *
     * @throws PreAddFailedException
     */
    protected void performPreAddExistenceCheck() {
        // Empty override
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:transactionId_be
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

    /**
     * Getter for property transactionId.
     *
     * @return Value of property transactionId.
     */
    public java.lang.String getTransactionId() {
        return getCompositeKey()!=null ? getCompositeKey().getTransactionId() : null;
    }

    /**
     * Use this method to update the property transactionId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param transactionId New value of property transactionId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTransactionId(java.lang.String transactionId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ((getTransactionId() == null && transactionId == null) ||(getTransactionId() != null && getTransactionId().equals(transactionId)))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        transactionId = validateTransactionId(transactionId);
        // .//GEN-END:transactionId_be
        // Add custom code before setting the value//GEN-FIRST:transactionId


        // .//GEN-LAST:transactionId
        // .//GEN-BEGIN:transactionId_1_be
        super.update();
        super.addInitialValue(TransactionFieldMeta.TRANSACTION_ID, getTransactionId());
        getCompositeKey().setTransactionId(transactionId);
        m_transactionObject = null;
        // .//GEN-END:transactionId_1_be
        // Add custom code after setting the value//GEN-FIRST:transactionId_3


        // .//GEN-LAST:transactionId_3
        // .//GEN-BEGIN:transactionId_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setTransactionId() method.
     *
     * @param transactionId New value of property transactionId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTransactionId(java.lang.String transactionId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setTransactionId(transactionId);
    }

    /**
     * Use this method to validate a value for the property transactionId.
     *
     * @param transactionId Value to be validated for the property transactionId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateTransactionId(java.lang.String transactionId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:transactionId_2_be
        // Add custom code before validation//GEN-FIRST:transactionId_1


        // .//GEN-LAST:transactionId_1
        // .//GEN-BEGIN:transactionId_3_be
        transactionId = FieldValidator.validate(transactionId, (StringFieldMetaData) TransactionFieldMeta.META_TRANSACTION_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionFieldMeta.getName(), TransactionFieldMeta.TRANSACTION_ID, transactionId, this.getUOW());

        // .//GEN-END:transactionId_3_be
        // Add custom code after a successful validation//GEN-FIRST:transactionId_2


        // .//GEN-LAST:transactionId_2
        // .//GEN-BEGIN:transactionId_4_be
        return transactionId;
    }
    // .//GEN-END:transactionId_4_be
    // .//GEN-BEGIN:fieldName_be
    /**
     * Getter for property fieldName.
     *
     * @return Value of property fieldName.
     */
    public java.lang.String getFieldName() {
        return getCompositeKey()!=null ? getCompositeKey().getFieldName() : null;
    }
    
    /**
     * Use this method to update the property fieldName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
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
        if ((getFieldName() == null && fieldName == null) ||(getFieldName() != null && getFieldName().equals(fieldName)))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        fieldName = validateFieldName(fieldName);
        // .//GEN-END:fieldName_be
        // Add custom code before setting the value//GEN-FIRST:fieldName


        // .//GEN-LAST:fieldName
        // .//GEN-BEGIN:fieldName_1_be
        super.update();
        super.addInitialValue(TransactionFieldMeta.FIELD_NAME, getFieldName());
        getCompositeKey().setFieldName(fieldName);
        m_transactionObject = null;
        // .//GEN-END:fieldName_1_be
        // Add custom code after setting the value//GEN-FIRST:fieldName_3


        // .//GEN-LAST:fieldName_3
        // .//GEN-BEGIN:fieldName_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setFieldName() method.
     *
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

    /**
     * Use this method to validate a value for the property fieldName.
     *
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
        fieldName = FieldValidator.validate(fieldName, (StringFieldMetaData) TransactionFieldMeta.META_FIELD_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionFieldMeta.getName(), TransactionFieldMeta.FIELD_NAME, fieldName, this.getUOW());

        // .//GEN-END:fieldName_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldName_2


        // .//GEN-LAST:fieldName_2
        // .//GEN-BEGIN:fieldName_4_be
        return fieldName;
    }
    // .//GEN-END:fieldName_4_be
    // .//GEN-BEGIN:value_be
    /**
     * Getter for property value.
     *
     * @return Value of property value.
     */
    public java.lang.String getValue() {
        return m_value;
    }
    
    /**
     * Use this method to update the property value.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
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
        super.addInitialValue(TransactionFieldMeta.VALUE, m_value);
        m_value = value;
        // .//GEN-END:value_1_be
        // Add custom code after setting the value//GEN-FIRST:value_3


        // .//GEN-LAST:value_3
        // .//GEN-BEGIN:value_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setValue() method.
     *
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

    /**
     * Use this method to validate a value for the property value.
     *
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
        value = FieldValidator.validate(value, (StringFieldMetaData) TransactionFieldMeta.META_VALUE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionFieldMeta.getName(), TransactionFieldMeta.VALUE, value, this.getUOW());

        // .//GEN-END:value_3_be
        // Add custom code after a successful validation//GEN-FIRST:value_2


        // .//GEN-LAST:value_2
        // .//GEN-BEGIN:value_4_be
        return value;
    }
    // .//GEN-END:value_4_be
    // .//GEN-BEGIN:transactionObject_1_be
    /**
     * Returns the related foreign Transaction object.
     * The object is lazy-loaded.
     *
     * @return the related foreign Transaction object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Transaction getTransactionObject() throws ValidationException, FrameworkException  {
        findTransactionObject(false);
        return m_transactionObject;
    }
    
    /**
     * Finds the related foreign Transaction object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findTransactionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        // TODO this is generated...
        if ((m_transactionObject == null) && (getTransactionId() != null)) {
            Transaction transaction = getTransactionDAO().getTransaction(getTransactionId());
            Number count = null;
            if (checkExistenceOnly) {
                if (transaction != null) {
                    count = 1;
                }
            } else {
                m_transactionObject = transaction;
            }
            if ((m_transactionObject == null) && ((count == null) || (count.intValue() <= 0))) {
                throw new InvalidForeignKeyException(TransactionFieldMeta.META_TRANSACTION_ID.getLabelToken(),
                                                     new Object[] {TransactionMeta.getLabelToken(),
                                                             TransactionMeta.META_ID.getLabelToken()});
            }
        }
    }
    // .//GEN-END:transactionObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /**
    * This returns the diagnostic information.
    *
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<TransactionField>");
        buf.append("<transactionId>"); if (getCompositeKey() != null) buf.append(getCompositeKey().getTransactionId()); buf.append("</transactionId>");
        buf.append("<fieldName>"); if (getCompositeKey() != null) buf.append(getCompositeKey().getFieldName()); buf.append("</fieldName>");
        buf.append("<value>"); if (m_value != null) buf.append(m_value); buf.append("</value>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</TransactionField>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /**
     * Returns a clone of the object.
     *
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        TransactionField obj = (TransactionField) super.clone();
        obj.m_transactionObject = null;
        return obj;
    }
    // .//GEN-END:clone_1_be
    // .//GEN-BEGIN:performForeignKeyValidations_1_be
    /**
     * This method ensures that the modified foreign-keys are valid.
     *
     * @throws ApplicationExceptions if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException {

    }
    // .//GEN-END:performForeignKeyValidations_1_be
    // .//GEN-BEGIN:performPreDeleteReferentialIntegrity_1_be
    /**
     * This method is triggered by the UOW, before adding this object to the Delete-Store.
     *
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
     * @clientQualifier transactionId
     * @supplierQualifier id
     * @link association
     */
    /*#Transaction lnkTransaction;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    /** This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * This will perform the following tasks:
     *    Will invoke the PersistentHelper.exists() to check against duplicate keys.
     *    Will invoke the performForeignKeyValidations() method to ensure no invalid foreign-keys are set.
     *    Will invoke PersistentHelper.checkMandatoryFields() to perform mandatory field checks.
     *    Will invoke the Rules Engine to perform mandatory field checks.
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        super.doPreAdd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        super.validate();
    }

    /**
     * Gets a DAO for transactions.
     * @return a DAO.
     */
    private static TransactionMessageDAO getTransactionDAO() {
        return TransactionMessageDAOFactory.getTransactionMessageDAO();
    }

    // .//GEN-LAST:custom

    @Embeddable
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompositeKey implements Serializable {

        @XmlElement(name="transactionId")
        @Column(name = "TRANSACTION_ID")
        private java.lang.String m_transactionId;

        @XmlElement(name="fieldName")
        @Column(name = "FIELD_NAME")
        private java.lang.String m_fieldName;

        /** Getter for property m_transactionId.
         * @return Value of property m_transactionId.
         */
        public java.lang.String getTransactionId() {
            return m_transactionId;
        }

        /** Setter for property m_transactionId.
         * @sets Value of property m_transactionId.
         */
        public void setTransactionId(java.lang.String transactionId){
            m_transactionId = transactionId;
        }

        /** Getter for property m_fieldName.
         * @return Value of property m_fieldName.
         */
        public java.lang.String getFieldName() {
            return m_fieldName;
        }

        /** Setter for property m_fieldName.
         * @sets Value of property m_fieldName.
         */
        public void setFieldName(java.lang.String fieldName){
            m_fieldName = fieldName;
        }
        /**
         * Compares this candidateKey with another candidateKey object.
         * Returns a true if both the objects have the same candidate key.
         *
         * @param obj the other Persistent object.
         * @return a true if both the objects have the same candidate key.
         */
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof TransactionField) {
                boolean equals = true;
                equals = equals && (getTransactionId() == null ? ((TransactionField.CompositeKey) obj).getTransactionId() == null : getTransactionId().equals(((TransactionField.CompositeKey) obj).getTransactionId()));
                equals = equals && (getFieldName() == null ? ((TransactionField.CompositeKey) obj).getFieldName() == null : getFieldName().equals(((TransactionField.CompositeKey) obj).getFieldName()));
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
            i += getTransactionId()!=null ? getTransactionId().hashCode() : 0;
            i += getFieldName()!=null ? getFieldName().hashCode() : 0;
            return i;
        }
    }
}
