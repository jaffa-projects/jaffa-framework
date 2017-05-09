// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.transaction.domain;

import org.apache.log4j.Logger;
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
 * Auto Generated Persistent class for the J_TRANSACTION_DEPENDENCIES table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "J_TRANSACTION_DEPENDENCIES")
@SqlResultSetMapping(name="TransactionDependency",entities={@EntityResult(entityClass=TransactionDependency.class)})
public class TransactionDependency extends Persistent {

    private static final Logger log = Logger.getLogger(TransactionDependency.class);

    @XmlElement(name="transactionId")
    @Id
    @Basic(optional = false)
    @Column(name = "TRANSACTION_ID")
    private java.lang.String m_transactionId;

    @XmlElement(name="dependsOnId")
    @Column(name = "DEPENDS_ON_ID")
    private java.lang.String m_dependsOnId;

    @XmlElement(name="status")
    @Column(name = "STATUS")
    private java.lang.String m_status;

    private transient Transaction m_transactionObject;
    private transient Transaction m_dependsOnTransactionObject;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public TransactionDependency() {
        StaticContext.configure(this);
    }

    /**
     * Check if the domain object exists for the input Primary Key.
     *
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String transactionId, java.lang.String dependsOnId) throws FrameworkException {
        // TODO this is generated code....
        return (findByPK(uow, transactionId, dependsOnId) != null);
    }

    /**
     * Returns the domain object for the input Primary Key.
     *
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static TransactionDependency findByPK(UOW uow, java.lang.String transactionId, java.lang.String dependsOnId) throws FrameworkException {
        // TODO this is generated code....
        TransactionDependency result = getTransactionDAO().getTransactionDependency(transactionId, dependsOnId);
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
    public static Criteria findByPKCriteria(java.lang.String transactionId, java.lang.String dependsOnId) {
        // TODO this is generated code....
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
    /**
     * Getter for property transactionId.
     *
     * @return Value of property transactionId.
     */
    public java.lang.String getTransactionId() {
        return m_transactionId;
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
        if (m_transactionId == null ? transactionId == null : m_transactionId.equals(transactionId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        transactionId = validateTransactionId(transactionId);
        // .//GEN-END:transactionId_be
        // Add custom code before setting the value//GEN-FIRST:transactionId


        // .//GEN-LAST:transactionId
        // .//GEN-BEGIN:transactionId_1_be
        super.update();
        super.addInitialValue(TransactionDependencyMeta.TRANSACTION_ID, m_transactionId);
        m_transactionId = transactionId;
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
        transactionId = FieldValidator.validate(transactionId, (StringFieldMetaData) TransactionDependencyMeta.META_TRANSACTION_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionDependencyMeta.getName(), TransactionDependencyMeta.TRANSACTION_ID, transactionId, this.getUOW());

        // .//GEN-END:transactionId_3_be
        // Add custom code after a successful validation//GEN-FIRST:transactionId_2


        // .//GEN-LAST:transactionId_2
        // .//GEN-BEGIN:transactionId_4_be
        return transactionId;
    }
    // .//GEN-END:transactionId_4_be
    // .//GEN-BEGIN:dependsOnId_be
    /**
     * Getter for property dependsOnId.
     *
     * @return Value of property dependsOnId.
     */
    public java.lang.String getDependsOnId() {
        return m_dependsOnId;
    }
    
    /**
     * Use this method to update the property dependsOnId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param dependsOnId New value of property dependsOnId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDependsOnId(java.lang.String dependsOnId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_dependsOnId == null ? dependsOnId == null : m_dependsOnId.equals(dependsOnId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        dependsOnId = validateDependsOnId(dependsOnId);
        // .//GEN-END:dependsOnId_be
        // Add custom code before setting the value//GEN-FIRST:dependsOnId


        // .//GEN-LAST:dependsOnId
        // .//GEN-BEGIN:dependsOnId_1_be
        super.update();
        super.addInitialValue(TransactionDependencyMeta.DEPENDS_ON_ID, m_dependsOnId);
        m_dependsOnId = dependsOnId;
        m_dependsOnTransactionObject = null;
        // .//GEN-END:dependsOnId_1_be
        // Add custom code after setting the value//GEN-FIRST:dependsOnId_3


        // .//GEN-LAST:dependsOnId_3
        // .//GEN-BEGIN:dependsOnId_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setDependsOnId() method.
     *
     * @param dependsOnId New value of property dependsOnId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDependsOnId(java.lang.String dependsOnId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDependsOnId(dependsOnId);
    }

    /**
     * Use this method to validate a value for the property dependsOnId.
     *
     * @param dependsOnId Value to be validated for the property dependsOnId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDependsOnId(java.lang.String dependsOnId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:dependsOnId_2_be
        // Add custom code before validation//GEN-FIRST:dependsOnId_1


        // .//GEN-LAST:dependsOnId_1
        // .//GEN-BEGIN:dependsOnId_3_be
        dependsOnId = FieldValidator.validate(dependsOnId, (StringFieldMetaData) TransactionDependencyMeta.META_DEPENDS_ON_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionDependencyMeta.getName(), TransactionDependencyMeta.DEPENDS_ON_ID, dependsOnId, this.getUOW());

        // .//GEN-END:dependsOnId_3_be
        // Add custom code after a successful validation//GEN-FIRST:dependsOnId_2


        // .//GEN-LAST:dependsOnId_2
        // .//GEN-BEGIN:dependsOnId_4_be
        return dependsOnId;
    }
    // .//GEN-END:dependsOnId_4_be
    // .//GEN-BEGIN:status_be
    /**
     * Getter for property status.
     *
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return m_status;
    }
    
    /**
     * Use this method to update the property status.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param status New value of property status.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setStatus(java.lang.String status)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_status == null ? status == null : m_status.equals(status))
            return;


        status = validateStatus(status);
        // .//GEN-END:status_be
        // Add custom code before setting the value//GEN-FIRST:status


        // .//GEN-LAST:status
        // .//GEN-BEGIN:status_1_be
        super.update();
        super.addInitialValue(TransactionDependencyMeta.STATUS, m_status);
        m_status = status;
        // .//GEN-END:status_1_be
        // Add custom code after setting the value//GEN-FIRST:status_3


        // .//GEN-LAST:status_3
        // .//GEN-BEGIN:status_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setStatus() method.
     *
     * @param status New value of property status.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateStatus(java.lang.String status)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setStatus(status);
    }

    /**
     * Use this method to validate a value for the property status.
     *
     * @param status Value to be validated for the property status.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateStatus(java.lang.String status)
    throws ValidationException, FrameworkException {
        // .//GEN-END:status_2_be
        // Add custom code before validation//GEN-FIRST:status_1


        // .//GEN-LAST:status_1
        // .//GEN-BEGIN:status_3_be
        status = FieldValidator.validate(status, (StringFieldMetaData) TransactionDependencyMeta.META_STATUS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionDependencyMeta.getName(), TransactionDependencyMeta.STATUS, status, this.getUOW());

        // .//GEN-END:status_3_be
        // Add custom code after a successful validation//GEN-FIRST:status_2


        // .//GEN-LAST:status_2
        // .//GEN-BEGIN:status_4_be
        return status;
    }
    // .//GEN-END:status_4_be
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
        // TODO this is generated code...
        if (m_transactionObject == null && getTransactionId() != null) {
            Transaction transaction = getTransactionDAO().getTransaction(getTransactionId());
            Number count = null;
            if (checkExistenceOnly) {
                if (transaction != null) {
                    count = 1;
                }
            }
            else {
                m_transactionObject = transaction;
            }
            if ((m_transactionObject == null) && ((count == null) || (count.intValue() <= 0))) {
                throw new InvalidForeignKeyException(TransactionDependencyMeta.META_TRANSACTION_ID.getLabelToken(),
                                                     new Object[] {TransactionMeta.getLabelToken(),
                                                             TransactionMeta.META_ID.getLabelToken()});
            }
        }
    }
    // .//GEN-END:transactionObject_1_be
    // .//GEN-BEGIN:dependsOnTransactionObject_1_be
    /**
     * Returns the related foreign Transaction object.
     * The object is lazy-loaded.
     *
     * @return the related foreign Transaction object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Transaction getDependsOnTransactionObject() throws ValidationException, FrameworkException  {
        findDependsOnTransactionObject(false);
        return m_dependsOnTransactionObject;
    }
    
    /**
     * Finds the related foreign Transaction object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findDependsOnTransactionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        // TODO this is generated code...
        if (m_dependsOnTransactionObject == null && getDependsOnId() != null) {
            Transaction transaction = getTransactionDAO().getTransaction(getDependsOnId());
            Number count = null;
            if (checkExistenceOnly) {
                if (transaction != null) {
                    count = 1;
                }
            } else {
                m_dependsOnTransactionObject = transaction;
            }
            if ((m_dependsOnTransactionObject == null) && ((count == null) || (count.intValue() <= 0))) {
                throw new InvalidForeignKeyException(TransactionDependencyMeta.META_DEPENDS_ON_ID.getLabelToken(),
                                                     new Object[] {TransactionMeta.getLabelToken(),
                                                             TransactionMeta.META_ID.getLabelToken()});
            }
        }
    }
    // .//GEN-END:dependsOnTransactionObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /**
    * This returns the diagnostic information.
    *
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<TransactionDependency>");
        buf.append("<transactionId>"); if (m_transactionId != null) buf.append(m_transactionId); buf.append("</transactionId>");
        buf.append("<dependsOnId>"); if (m_dependsOnId != null) buf.append(m_dependsOnId); buf.append("</dependsOnId>");
        buf.append("<status>"); if (m_status != null) buf.append(m_status); buf.append("</status>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</TransactionDependency>");
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
        TransactionDependency obj = (TransactionDependency) super.clone();
        obj.m_transactionObject = null;
        obj.m_dependsOnTransactionObject = null;
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

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier dependsOnId
     * @supplierQualifier id
     * @link association
     */
    /*#Transaction lnkTransaction;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    /** An enumeration of TransactionDependency Status. */
    public enum Status {O, S}

    @Override
    public void postUpdate() throws PostUpdateFailedException {
        super.postUpdate();

        //If status changes to "S" and if "SELECT COUNT(*) FROM TRANSACTION_DEPENDENCIES WHERE TRANSACTION_ID={thisTransactionDependency.transactionId} AND STATUS='O'" is zero,
        //then UPDATE TRANSACTIONS SET STATUS='O' WHERE ID={thisTransactionDependency.transactionId} and STATUS='H'
        if (isModified(TransactionDependencyMeta.STATUS) && Status.S.toString().equals(getStatus())) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("TransactionDependency status has changed to S. Will Open parent Transaction if no other Open TransactionDependency record exists");
                }
                TransactionMessageDAO transactionDAO = getTransactionDAO();
                Transaction transaction = transactionDAO.getTransaction(getTransactionId());
                if (transaction != null && Transaction.Status.H.toString().equals(transaction.getStatus())) {
                    long count = transactionDAO.getOpenTransactionDependencyCount(transaction.getId());
                    if (count == 0) {
                        transaction.setStatus(Transaction.Status.O.toString());
                        getUOW().update(transaction);
                    }
                }
            } catch (Exception e) {
                String s = "Exception in opening parent Transaction when the associated dependencies have all been satisfied. This is being done for the TransactionDependency instance " + this;
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null) {
                    if (log.isDebugEnabled())
                        log.debug(s, appExps);
                } else {
                    log.error(s, e);
                }
                throw new PostUpdateFailedException(new String[]{s}, e);
            }
        }
    }

    @Override
    public void postDelete() throws PostDeleteFailedException {
        super.postDelete();

        //If status was "O" and if "SELECT COUNT(*) FROM TRANSACTION_DEPENDENCIES WHERE TRANSACTION_ID={thisTransactionDependency.transactionId} AND STATUS='O'" is zero,
        //then UPDATE TRANSACTIONS SET STATUS='O' WHERE ID={thisTransactionDependency.transactionId} and STATUS='H'
        if (Status.O.toString().equals(getStatus())) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("An Open TransactionDependency status has been deleted. Will Open parent Transaction if no other Open TransactionDependency record exists");
                }
                TransactionMessageDAO transactionDAO = getTransactionDAO();
                Transaction transaction = transactionDAO.getTransaction(getTransactionId());
                if ((transaction != null) && Transaction.Status.H.toString().equals(transaction.getStatus())) {
                    long count = transactionDAO.getOpenTransactionDependencyCount(transaction.getId());
                    if (count == 0) {
                        transaction.setStatus(Transaction.Status.O.toString());
                        getUOW().update(transaction);
                    }
                }
            } catch (Exception e) {
                String s = "Exception in opening parent Transaction when the associated dependencies have all been satisfied. This is being done for the deleted TransactionDependency instance " + this;
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null) {
                    if (log.isDebugEnabled())
                        log.debug(s, appExps);
                } else {
                    log.error(s, e);
                }
                throw new PostDeleteFailedException(new String[]{s}, e);
            }
        }
    }

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
}
