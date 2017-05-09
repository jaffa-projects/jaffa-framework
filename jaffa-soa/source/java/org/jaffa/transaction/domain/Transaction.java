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
import java.sql.Timestamp;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.adapters.*;
import org.jaffa.metadata.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.*;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.security.UserContext;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;



// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_TRANSACTIONS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "J_TRANSACTIONS")
@SqlResultSetMapping(name="Transaction",entities={@EntityResult(entityClass=Transaction.class)})
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
    ,@NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id")
    ,@NamedQuery(name = "Transaction.findByDirection", query = "SELECT t FROM Transaction t WHERE t.direction = :direction")
    ,@NamedQuery(name = "Transaction.findByType", query = "SELECT t FROM Transaction t WHERE t.type = :type")
    ,@NamedQuery(name = "Transaction.findBySubType", query = "SELECT t FROM Transaction t WHERE t.subType = :subType")
    ,@NamedQuery(name = "Transaction.findByStatus", query = "SELECT t FROM Transaction t WHERE t.status = :status")
    ,@NamedQuery(name = "Transaction.findByErrorMessage", query = "SELECT t FROM Transaction t WHERE t.errorMessage = :errorMessage")
    ,@NamedQuery(name = "Transaction.findByCreatedOn", query = "SELECT t FROM Transaction t WHERE t.createdOn = :createdOn")
    ,@NamedQuery(name = "Transaction.findByCreatedBy", query = "SELECT t FROM Transaction t WHERE t.createdBy = :createdBy")
    ,@NamedQuery(name = "Transaction.findByLastChangedOn", query = "SELECT t FROM Transaction t WHERE t.lastChangedOn = :lastChangedOn")
    ,@NamedQuery(name = "Transaction.findByLastChangedBy", query = "SELECT t FROM Transaction t WHERE t.lastChangedBy = :lastChangedBy")
})
public class Transaction extends Persistent {

    private static final Logger log = Logger.getLogger(Transaction.class);
    private static final long serialVersionUID = 1L;

    @XmlElement(name="id")
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private java.lang.String id;

    @XmlElement(name="direction")
    @Column(name = "DIRECTION")
    private java.lang.String direction;

    @XmlElement(name="type")
    @Column(name = "TYPE")
    private java.lang.String type;

    @XmlElement(name="subType")
    @Column(name = "SUB_TYPE")
    private java.lang.String subType;

    @XmlElement(name="status")
    @Column(name = "STATUS")
    private java.lang.String status;

    @XmlElement(name="errorMessage")
    @Column(name = "ERROR_MESSAGE")
    private java.lang.String errorMessage;

    @XmlElement(name="createdOn")
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @XmlElement(name="createdBy")
    @Column(name = "CREATED_BY")
    private java.lang.String createdBy;

    @XmlElement(name="lastChangedOn")
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    @Column(name = "LAST_CHANGED_ON")
    private Timestamp lastChangedOn;

    @XmlElement(name="lastChangedBy")
    @Column(name = "LAST_CHANGED_BY")
    private java.lang.String lastChangedBy;

    private transient TransactionPayload transactionPayloadObject;
    private transient Collection transactionFieldCollection;
    private transient Collection transactionDependencyCollection;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public Transaction() {
        StaticContext.configure(this);
    }

    /**
     * Check if the domain object exists for the input Primary Key.
     *
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String id) throws FrameworkException {
        // TODO This is in generated code...
        return (findByPK(uow, id) != null);
    }

    /**
     * Returns the domain object for the input Primary Key.
     *
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static Transaction findByPK(UOW uow, java.lang.String id) throws FrameworkException {
        // TODO This is in generated code...
        Transaction result = getTransactionDAO().getTransaction(id);
        if (result != null) {
            result.setUOW(uow);
        }
        return result;
    }

    /**
     * Returns a Criteria object for retrieving the domain object based on the input Primary Key.
     *
     * @return a Criteria object for retrieving the domain object based on the input Primary Key.
     */
    public static Criteria findByPKCriteria(java.lang.String id) {
        // TODO This is in generated code...
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
    // .//GEN-BEGIN:id_be
    /**
     * Getter for property id.
     *
     * @return Value of property id.
     */
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * Use this method to update the property id.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param id New value of property id.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setId(java.lang.String id)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.id == null ? id == null : this.id.equals(id))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        id = validateId(id);
        // .//GEN-END:id_be
        // Add custom code before setting the value//GEN-FIRST:id


        // .//GEN-LAST:id
        // .//GEN-BEGIN:id_1_be
        super.update();
        super.addInitialValue(TransactionMeta.ID, this.id);
        this.id = id;
        this.transactionPayloadObject = null;
        // .//GEN-END:id_1_be
        // Add custom code after setting the value//GEN-FIRST:id_3


        // .//GEN-LAST:id_3
        // .//GEN-BEGIN:id_2_be
    }

    /**
     * Use this method to validate a value for the property id.
     *
     * @param id Value to be validated for the property id.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateId(java.lang.String id)
    throws ValidationException, FrameworkException {
        // .//GEN-END:id_2_be
        // Add custom code before validation//GEN-FIRST:id_1


        // .//GEN-LAST:id_1
        // .//GEN-BEGIN:id_3_be
        id = FieldValidator.validate(id, (StringFieldMetaData) TransactionMeta.META_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.ID, id, this.getUOW());

        // .//GEN-END:id_3_be
        // Add custom code after a successful validation//GEN-FIRST:id_2


        // .//GEN-LAST:id_2
        // .//GEN-BEGIN:id_4_be
        return id;
    }
    // .//GEN-END:id_4_be
    // .//GEN-BEGIN:direction_be
    /**
     * Getter for property direction.
     *
     * @return Value of property direction.
     */
    public java.lang.String getDirection() {
        return this.direction;
    }

    /**
     * Use this method to update the property direction.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param direction New value of property direction.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDirection(java.lang.String direction)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.direction == null ? direction == null : this.direction.equals(direction))
            return;


        direction = validateDirection(direction);
        // .//GEN-END:direction_be
        // Add custom code before setting the value//GEN-FIRST:direction


        // .//GEN-LAST:direction
        // .//GEN-BEGIN:direction_1_be
        super.update();
        super.addInitialValue(TransactionMeta.DIRECTION, this.direction);
        this.direction = direction;
        // .//GEN-END:direction_1_be
        // Add custom code after setting the value//GEN-FIRST:direction_3


        // .//GEN-LAST:direction_3
        // .//GEN-BEGIN:direction_2_be
    }

    /**
     * Use this method to validate a value for the property direction.
     *
     * @param direction Value to be validated for the property direction.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDirection(java.lang.String direction)
    throws ValidationException, FrameworkException {
        // .//GEN-END:direction_2_be
        // Add custom code before validation//GEN-FIRST:direction_1


        // .//GEN-LAST:direction_1
        // .//GEN-BEGIN:direction_3_be
        direction = FieldValidator.validate(direction, (StringFieldMetaData) TransactionMeta.META_DIRECTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.DIRECTION, direction, this.getUOW());

        // .//GEN-END:direction_3_be
        // Add custom code after a successful validation//GEN-FIRST:direction_2


        // .//GEN-LAST:direction_2
        // .//GEN-BEGIN:direction_4_be
        return direction;
    }
    // .//GEN-END:direction_4_be
    // .//GEN-BEGIN:type_be
    /**
     * Getter for property type.
     *
     * @return Value of property type.
     */
    public java.lang.String getType() {
        return this.type;
    }

    /**
     * Use this method to update the property type.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param type New value of property type.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setType(java.lang.String type)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.type == null ? type == null : this.type.equals(type))
            return;


        type = validateType(type);
        // .//GEN-END:type_be
        // Add custom code before setting the value//GEN-FIRST:type


        // .//GEN-LAST:type
        // .//GEN-BEGIN:type_1_be
        super.update();
        super.addInitialValue(TransactionMeta.TYPE, this.type);
        this.type = type;
        // .//GEN-END:type_1_be
        // Add custom code after setting the value//GEN-FIRST:type_3


        // .//GEN-LAST:type_3
        // .//GEN-BEGIN:type_2_be
    }

    /**
     * Use this method to validate a value for the property type.
     *
     * @param type Value to be validated for the property type.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateType(java.lang.String type)
    throws ValidationException, FrameworkException {
        // .//GEN-END:type_2_be
        // Add custom code before validation//GEN-FIRST:type_1


        // .//GEN-LAST:type_1
        // .//GEN-BEGIN:type_3_be
        type = FieldValidator.validate(type, (StringFieldMetaData) TransactionMeta.META_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.TYPE, type, this.getUOW());

        // .//GEN-END:type_3_be
        // Add custom code after a successful validation//GEN-FIRST:type_2


        // .//GEN-LAST:type_2
        // .//GEN-BEGIN:type_4_be
        return type;
    }
    // .//GEN-END:type_4_be
    // .//GEN-BEGIN:subType_be
    /**
     * Getter for property subType.
     *
     * @return Value of property subType.
     */
    public java.lang.String getSubType() {
        return this.subType;
    }

    /**
     * Use this method to update the property subType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param subType New value of property subType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSubType(java.lang.String subType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.subType == null ? subType == null : this.subType.equals(subType))
            return;


        subType = validateSubType(subType);
        // .//GEN-END:subType_be
        // Add custom code before setting the value//GEN-FIRST:subType


        // .//GEN-LAST:subType
        // .//GEN-BEGIN:subType_1_be
        super.update();
        super.addInitialValue(TransactionMeta.SUB_TYPE, this.subType);
        this.subType = subType;
        // .//GEN-END:subType_1_be
        // Add custom code after setting the value//GEN-FIRST:subType_3


        // .//GEN-LAST:subType_3
        // .//GEN-BEGIN:subType_2_be
    }

    /**
     * Use this method to validate a value for the property subType.
     *
     * @param subType Value to be validated for the property subType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSubType(java.lang.String subType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:subType_2_be
        // Add custom code before validation//GEN-FIRST:subType_1


        // .//GEN-LAST:subType_1
        // .//GEN-BEGIN:subType_3_be
        subType = FieldValidator.validate(subType, (StringFieldMetaData) TransactionMeta.META_SUB_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.SUB_TYPE, subType, this.getUOW());

        // .//GEN-END:subType_3_be
        // Add custom code after a successful validation//GEN-FIRST:subType_2


        // .//GEN-LAST:subType_2
        // .//GEN-BEGIN:subType_4_be
        return subType;
    }
    // .//GEN-END:subType_4_be
    // .//GEN-BEGIN:status_be
    /**
     * Getter for property status.
     *
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return this.status;
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
        if (this.status == null ? status == null : this.status.equals(status))
            return;


        status = validateStatus(status);
        // .//GEN-END:status_be
        // Add custom code before setting the value//GEN-FIRST:status

        // if we are putting the transaction into the "H" status, un-queue it first
        if (status.equals(Status.H.name())) {
            if (getUOW() != null) {
                getUOW().flush();
            }
        }

        // .//GEN-LAST:status
        // .//GEN-BEGIN:status_1_be
        super.update();
        super.addInitialValue(TransactionMeta.STATUS, this.status);
        this.status = status;
        // .//GEN-END:status_1_be
        // Add custom code after setting the value//GEN-FIRST:status_3


        // .//GEN-LAST:status_3
        // .//GEN-BEGIN:status_2_be
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
        status = FieldValidator.validate(status, (StringFieldMetaData) TransactionMeta.META_STATUS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.STATUS, status, this.getUOW());

        // .//GEN-END:status_3_be
        // Add custom code after a successful validation//GEN-FIRST:status_2


        // .//GEN-LAST:status_2
        // .//GEN-BEGIN:status_4_be
        return status;
    }
    // .//GEN-END:status_4_be
    // .//GEN-BEGIN:errorMessage_be
    /**
     * Getter for property errorMessage.
     *
     * @return Value of property errorMessage.
     */
    public java.lang.String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Use this method to update the property errorMessage.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param errorMessage New value of property errorMessage.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setErrorMessage(java.lang.String errorMessage)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.errorMessage == null ? errorMessage == null : this.errorMessage.equals(errorMessage))
            return;


        errorMessage = validateErrorMessage(errorMessage);
        // .//GEN-END:errorMessage_be
        // Add custom code before setting the value//GEN-FIRST:errorMessage


        // .//GEN-LAST:errorMessage
        // .//GEN-BEGIN:errorMessage_1_be
        super.update();
        super.addInitialValue(TransactionMeta.ERROR_MESSAGE, this.errorMessage);
        this.errorMessage = errorMessage;
        // .//GEN-END:errorMessage_1_be
        // Add custom code after setting the value//GEN-FIRST:errorMessage_3


        // .//GEN-LAST:errorMessage_3
        // .//GEN-BEGIN:errorMessage_2_be
    }

    /**
     * Use this method to validate a value for the property errorMessage.
     *
     * @param errorMessage Value to be validated for the property errorMessage.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateErrorMessage(java.lang.String errorMessage)
    throws ValidationException, FrameworkException {
        // .//GEN-END:errorMessage_2_be
        // Add custom code before validation//GEN-FIRST:errorMessage_1


        // .//GEN-LAST:errorMessage_1
        // .//GEN-BEGIN:errorMessage_3_be
        errorMessage = FieldValidator.validate(errorMessage, (StringFieldMetaData) TransactionMeta.META_ERROR_MESSAGE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.ERROR_MESSAGE, errorMessage, this.getUOW());

        // .//GEN-END:errorMessage_3_be
        // Add custom code after a successful validation//GEN-FIRST:errorMessage_2


        // .//GEN-LAST:errorMessage_2
        // .//GEN-BEGIN:errorMessage_4_be
        return errorMessage;
    }
    // .//GEN-END:errorMessage_4_be
    // .//GEN-BEGIN:createdOn_be
    /**
     * Getter for property createdOn.
     *
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return this.createdOn != null ? new DateTime(this.createdOn) : null;
    }

    /**
     * Use this method to update the property createdOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param createdOn New value of property createdOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.createdOn == null ? createdOn == null : new DateTime(this.createdOn).equals(createdOn))
            return;


        createdOn = validateCreatedOn(createdOn);
        // .//GEN-END:createdOn_be
        // Add custom code before setting the value//GEN-FIRST:createdOn


        // .//GEN-LAST:createdOn
        // .//GEN-BEGIN:createdOn_1_be
        super.update();
        super.addInitialValue(TransactionMeta.CREATED_ON, getCreatedOn());
        this.createdOn = createdOn != null ? createdOn.timestamp() : null;
        // .//GEN-END:createdOn_1_be
        // Add custom code after setting the value//GEN-FIRST:createdOn_3


        // .//GEN-LAST:createdOn_3
        // .//GEN-BEGIN:createdOn_2_be
    }

    /**
     * Use this method to validate a value for the property createdOn.
     *
     * @param createdOn Value to be validated for the property createdOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:createdOn_2_be
        // Add custom code before validation//GEN-FIRST:createdOn_1


        // .//GEN-LAST:createdOn_1
        // .//GEN-BEGIN:createdOn_3_be
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) TransactionMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.CREATED_ON, createdOn, this.getUOW());

        // .//GEN-END:createdOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdOn_2


        // .//GEN-LAST:createdOn_2
        // .//GEN-BEGIN:createdOn_4_be
        return createdOn;
    }
    // .//GEN-END:createdOn_4_be
    // .//GEN-BEGIN:createdBy_be
    /**
     * Getter for property createdBy.
     *
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Use this method to update the property createdBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param createdBy New value of property createdBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedBy(java.lang.String createdBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.createdBy == null ? createdBy == null : this.createdBy.equals(createdBy))
            return;


        createdBy = validateCreatedBy(createdBy);
        // .//GEN-END:createdBy_be
        // Add custom code before setting the value//GEN-FIRST:createdBy


        // .//GEN-LAST:createdBy
        // .//GEN-BEGIN:createdBy_1_be
        super.update();
        super.addInitialValue(TransactionMeta.CREATED_BY, this.createdBy);
        this.createdBy = createdBy;
        // .//GEN-END:createdBy_1_be
        // Add custom code after setting the value//GEN-FIRST:createdBy_3

        // if the createdBy user is not the current user, ensure the UserContext is null
        if ((SecurityManager.getPrincipal() != null) && (SecurityManager.getPrincipal().getName() != null)) {
            if (!this.createdBy.equals(SecurityManager.getPrincipal().getName())) {
                userContext = null;
            }
        }

        // .//GEN-LAST:createdBy_3
        // .//GEN-BEGIN:createdBy_2_be
    }

    /**
     * Use this method to validate a value for the property createdBy.
     *
     * @param createdBy Value to be validated for the property createdBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCreatedBy(java.lang.String createdBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:createdBy_2_be
        // Add custom code before validation//GEN-FIRST:createdBy_1


        // .//GEN-LAST:createdBy_1
        // .//GEN-BEGIN:createdBy_3_be
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) TransactionMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.CREATED_BY, createdBy, this.getUOW());

        // .//GEN-END:createdBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdBy_2


        // .//GEN-LAST:createdBy_2
        // .//GEN-BEGIN:createdBy_4_be
        return createdBy;
    }
    // .//GEN-END:createdBy_4_be
    // .//GEN-BEGIN:lastChangedOn_be
    /**
     * Getter for property lastChangedOn.
     *
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return this.lastChangedOn != null ? new DateTime(this.lastChangedOn) : null;
    }

    /**
     * Use this method to update the property lastChangedOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param lastChangedOn New value of property lastChangedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.lastChangedOn == null ? lastChangedOn == null : new DateTime(this.lastChangedOn).equals(lastChangedOn))
            return;


        lastChangedOn = validateLastChangedOn(lastChangedOn);
        // .//GEN-END:lastChangedOn_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedOn


        // .//GEN-LAST:lastChangedOn
        // .//GEN-BEGIN:lastChangedOn_1_be
        super.update();
        super.addInitialValue(TransactionMeta.LAST_CHANGED_ON, getLastChangedOn());
        this.lastChangedOn = lastChangedOn != null ? lastChangedOn.timestamp() : null;
        // .//GEN-END:lastChangedOn_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedOn_3


        // .//GEN-LAST:lastChangedOn_3
        // .//GEN-BEGIN:lastChangedOn_2_be
    }

    /**
     * Use this method to validate a value for the property lastChangedOn.
     *
     * @param lastChangedOn Value to be validated for the property lastChangedOn.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, FrameworkException {
        // .//GEN-END:lastChangedOn_2_be
        // Add custom code before validation//GEN-FIRST:lastChangedOn_1


        // .//GEN-LAST:lastChangedOn_1
        // .//GEN-BEGIN:lastChangedOn_3_be
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) TransactionMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

        // .//GEN-END:lastChangedOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedOn_2


        // .//GEN-LAST:lastChangedOn_2
        // .//GEN-BEGIN:lastChangedOn_4_be
        return lastChangedOn;
    }
    // .//GEN-END:lastChangedOn_4_be
    // .//GEN-BEGIN:lastChangedBy_be
    /**
     * Getter for property lastChangedBy.
     *
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return this.lastChangedBy;
    }

    /**
     * Use this method to update the property lastChangedBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param lastChangedBy New value of property lastChangedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (this.lastChangedBy == null ? lastChangedBy == null : this.lastChangedBy.equals(lastChangedBy))
            return;


        lastChangedBy = validateLastChangedBy(lastChangedBy);
        // .//GEN-END:lastChangedBy_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedBy


        // .//GEN-LAST:lastChangedBy
        // .//GEN-BEGIN:lastChangedBy_1_be
        super.update();
        super.addInitialValue(TransactionMeta.LAST_CHANGED_BY, this.lastChangedBy);
        this.lastChangedBy = lastChangedBy;
        // .//GEN-END:lastChangedBy_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedBy_3


        // .//GEN-LAST:lastChangedBy_3
        // .//GEN-BEGIN:lastChangedBy_2_be
    }

    /**
     * Use this method to validate a value for the property lastChangedBy.
     *
     * @param lastChangedBy Value to be validated for the property lastChangedBy.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, FrameworkException {
        // .//GEN-END:lastChangedBy_2_be
        // Add custom code before validation//GEN-FIRST:lastChangedBy_1


        // .//GEN-LAST:lastChangedBy_1
        // .//GEN-BEGIN:lastChangedBy_3_be
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) TransactionMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionMeta.getName(), TransactionMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
    // .//GEN-BEGIN:transactionPayloadObject_1_be
    /**
     * Returns a related TransactionPayload object.
     *
     * @return a related TransactionPayload object.
     * @throws FrameworkException Indicates some system error
     */
    public TransactionPayload getTransactionPayloadObject() throws FrameworkException {
        findTransactionPayloadObject(false);
        return this.transactionPayloadObject;
    }

    /**
     * Finds the related TransactionPayload object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findTransactionPayloadObject(boolean checkExistenceOnly) throws FrameworkException {
        // TODO this is generated code
        if ((transactionPayloadObject == null) && (getId() != null)) {
            if (checkExistenceOnly) {
                return;
            }
            transactionPayloadObject = getTransactionDAO().getTransactionPayloadByTransactionId(getId());
            if (transactionPayloadObject != null) {
                transactionPayloadObject.setUOW(getUOW());
            }
        }
    }
    /**
     * Creates a new TransactionPayload object and initializes the related fields.
     *
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related TransactionPayload object with the initialized related fields.
     */
    public TransactionPayload newTransactionPayloadObject()
    throws ValidationException, FrameworkException {
        TransactionPayload transactionPayload = new TransactionPayload();
        transactionPayload.setId(getId());
        // .//GEN-END:transactionPayloadObject_1_be
        // Add custom code//GEN-FIRST:transactionPayloadObject_1


        // .//GEN-LAST:transactionPayloadObject_1
        // .//GEN-BEGIN:transactionPayloadObject_2_be
        return transactionPayload;
    }
    // .//GEN-END:transactionPayloadObject_2_be
    // .//GEN-BEGIN:transactionFieldArray_1_be
    /**
     * Returns an array of related TransactionField objects.
     *
     * @return an array of related TransactionField objects.
     * @throws FrameworkException Indicates some system error
     */
    public TransactionField[] getTransactionFieldArray() throws FrameworkException {
        // TODO this is generated code...
        TransactionField[] output = null;
        if ((transactionFieldCollection == null) && isDatabaseOccurence()) {
            transactionFieldCollection = getTransactionDAO().getTransactionFields(getId());
        }

        if ((transactionFieldCollection != null) && !transactionFieldCollection.isEmpty()) {
            output = (TransactionField[]) transactionFieldCollection.toArray(new TransactionField[0]);
            for (TransactionField field : output) {
                field.setUOW(getUOW());
            }
        }
        return output;
    }
    /**
     * Returns a Criteria object for retrieving the related TransactionField objects.
     *
     * @return a Criteria object for retrieving the related TransactionField objects.
     */
    public Criteria findTransactionFieldCriteria() {
        // TODO this is generated code...
        return null;
    }
    /**
     * Creates a new TransactionField object and initializes the related fields.
     * This will uncache the related TransactionField objects.
     *
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related TransactionField object with the initialized related fields.
     */
    public TransactionField newTransactionFieldObject()
    throws ValidationException, FrameworkException {
        this.transactionFieldCollection = null;
        TransactionField transactionField = new TransactionField();
        transactionField.setTransactionId(getId());
        // .//GEN-END:transactionFieldArray_2_be
        // Add custom code//GEN-FIRST:transactionFieldArray_2


        // .//GEN-LAST:transactionFieldArray_2
        // .//GEN-BEGIN:transactionFieldArray_3_be
        return transactionField;
    }
    // .//GEN-END:transactionFieldArray_3_be
    // .//GEN-BEGIN:transactionDependencyArray_1_be
    /**
     * Returns an array of related TransactionDependency objects.
     *
     * @return an array of related TransactionDependency objects.
     * @throws FrameworkException Indicates some system error
     */
    public TransactionDependency[] getTransactionDependencyArray() throws FrameworkException {
        // TODO this is generated code...
        TransactionDependency[] output = null;
        if ((transactionDependencyCollection == null) && isDatabaseOccurence()) {
            transactionDependencyCollection = getTransactionDAO().getTransactionDependencies(getId());
        }
        if ((transactionDependencyCollection != null) && !transactionDependencyCollection.isEmpty()) {
            output = (TransactionDependency[]) this.transactionDependencyCollection.toArray(new TransactionDependency[0]);
            for (TransactionDependency dependency : output) {
                dependency.setUOW(getUOW());
            }
        }
        return output;
    }
    /**
     * Returns a Criteria object for retrieving the related TransactionDependency objects.
     *
     * @return a Criteria object for retrieving the related TransactionDependency objects.
     */
    public Criteria findTransactionDependencyCriteria() {
        // TODO this is generated code...
        return null;
    }
    /**
     * Creates a new TransactionDependency object and initializes the related fields.
     * This will uncache the related TransactionDependency objects.
     *
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related TransactionDependency object with the initialized related fields.
     */
    public TransactionDependency newTransactionDependencyObject()
    throws ValidationException, FrameworkException {
        this.transactionDependencyCollection = null;
        TransactionDependency transactionDependency = new TransactionDependency();
        transactionDependency.setTransactionId(getId());
        // .//GEN-END:transactionDependencyArray_2_be
        // Add custom code//GEN-FIRST:transactionDependencyArray_2


        // .//GEN-LAST:transactionDependencyArray_2
        // .//GEN-BEGIN:transactionDependencyArray_3_be
        return transactionDependency;
    }
    // .//GEN-END:transactionDependencyArray_3_be
    // .//GEN-BEGIN:toString_1_be
    /**
    * This returns the diagnostic information.
    *
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Transaction>");
        buf.append("<id>"); if (this.id != null) buf.append(this.id); buf.append("</id>");
        buf.append("<direction>"); if (this.direction != null) buf.append(this.direction); buf.append("</direction>");
        buf.append("<type>"); if (this.type != null) buf.append(this.type); buf.append("</type>");
        buf.append("<subType>"); if (this.subType != null) buf.append(this.subType); buf.append("</subType>");
        buf.append("<status>"); if (this.status != null) buf.append(this.status); buf.append("</status>");
        buf.append("<errorMessage>"); if (this.errorMessage != null) buf.append(this.errorMessage); buf.append("</errorMessage>");
        buf.append("<createdOn>"); if (this.createdOn != null) buf.append(this.createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (this.createdBy != null) buf.append(this.createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (this.lastChangedOn != null) buf.append(this.lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (this.lastChangedBy != null) buf.append(this.lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</Transaction>");
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
        Transaction obj = (Transaction) super.clone();
        obj.transactionPayloadObject = null;
        obj.transactionFieldCollection = null;
        obj.transactionDependencyCollection = null;
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
     * This will raise an exception if any associated/aggregated objects exist.
     * This will cascade delete all composite objects.
     *
     * @throws PreDeleteFailedException if any error occurs during the process.
     */
    public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException {
        TransactionPayload transactionPayloadObject = null;
        try {
            transactionPayloadObject = getTransactionPayloadObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (transactionPayloadObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(transactionPayloadObject);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        TransactionField[] transactionFieldArray = null;
        try {
            transactionFieldArray = getTransactionFieldArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (transactionFieldArray != null && transactionFieldArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < transactionFieldArray.length; i++)
                    getUOW().delete(transactionFieldArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
        TransactionDependency[] transactionDependencyArray = null;
        try {
            transactionDependencyArray = getTransactionDependencyArray();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (transactionDependencyArray != null && transactionDependencyArray.length > 0) {
            try {
                // Perform cascade deletes
                for (int i = 0; i < transactionDependencyArray.length; i++)
                    getUOW().delete(transactionDependencyArray[i]);
            } catch (Exception e) {
                throw new PreDeleteFailedException(null, e);
            }
        }
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
    /**
     * @clientCardinality 1
     * @supplierCardinality 0..1
     * @clientQualifier id
     * @supplierQualifier id
     * @link composition
     */
    /*#TransactionPayload lnkTransactionPayload;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier id
     * @supplierQualifier transactionId
     * @link composition
     */
    /*#TransactionField lnkTransactionField;*/

    /**
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier id
     * @supplierQualifier transactionId
     * @link composition
     */
    /*#TransactionDependency lnkTransactionDependency;*/

    // .//GEN-END:3_be
    // .//GEN-BEGIN:preAdd_1_be
    /**
     * This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * It ensures that the primary-key is unique and that the foreign-keys are valid.
     *
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        try {
            if (getCreatedBy() == null && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setCreatedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        }
        try {
            if (getCreatedOn() == null)
                setCreatedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        }
        try {
            if (getLastChangedBy() == null)
                setLastChangedBy(getCreatedBy());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        }
        try {
            if (getLastChangedOn() == null)
                setLastChangedOn(getCreatedOn());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        // .//GEN-END:preAdd_1_be
        // Add custom code//GEN-FIRST:preAdd_1

        // Generate the technical-key, if required
        try {
            generateKey();
        } catch (Exception e) {
            throw new PreAddFailedException(new String[]{"TechnicalKey generation error"}, e);
        }

        // Clear errorMessage if status is S
        try {
            if (Status.S.toString().equals(getStatus()) && getErrorMessage() != null)
                setErrorMessage(null);
        } catch (Exception e) {
            throw new PreAddFailedException(new String[]{"Error in clearing errorMessage"}, e);
        }

        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.doPreAdd();
    }
    // .//GEN-END:preAdd_2_be
    // .//GEN-BEGIN:preUpdate_1_be
    /**
     * This method is triggered by the UOW, before adding this object to the Update-Store, but after a UOW has been associated to the object.
     * It ensures that the foreign-keys are valid.
     *
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    public void preUpdate() throws PreUpdateFailedException {
        try {
            if (getLastChangedOn() == null || !isModified(TransactionMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(TransactionMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setLastChangedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        }
        // .//GEN-END:preUpdate_1_be
        // Update custom code//GEN-FIRST:preUpdate_1
        // Clear errorMessage if status is S
        try {
            if (isModified(TransactionMeta.STATUS) && Status.S.toString().equals(getStatus()) && getErrorMessage() != null)
                setErrorMessage(null);
        } catch (Exception e) {
            throw new PreUpdateFailedException(new String[]{"Error in clearing errorMessage"}, e);
        }

        // .//GEN-LAST:preUpdate_1
        // .//GEN-BEGIN:preUpdate_2_be
        super.preUpdate();
    }
    // .//GEN-END:preUpdate_2_be
    // All the custom code goes here//GEN-FIRST:custom

    // used during the consumption of transactions
    private transient UserContext userContext;
    private transient TransactionInfo transactionInfo;
    public static final String RULE_POST_IMMEDIATE = "jaffa.transaction.postImmediate";

    /** Associates this object to a UOW.
     * Note: This method is for internal use by the Persistence Engine only.
     * If this transaction has its fields defined, set the UOW on them too.
     * @param uow The UOW.
     */
    @Override
    public void setUOW(UOW uow) {
        super.setUOW(uow);
        if (transactionPayloadObject != null) {
            transactionPayloadObject.setUOW(getUOW());
        }
        if (transactionFieldCollection != null) {
            for (Object field : transactionFieldCollection) {
                ((TransactionField) field).setUOW(getUOW());
            }
        }
        if (transactionDependencyCollection != null) {
            for (Object dependency : transactionDependencyCollection) {
                ((TransactionDependency) dependency).setUOW(getUOW());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        super.validate();
    }

    /**
     * Getter for the user context.  This is a transient field that is used when consuming transactions locally.
     *
     * @return the user ID and roles associated with this transaction
     */
    public UserContext getUserContext() {
        return userContext;
    }

    /**
     * Sets the user context for this Transaction.  This will be used whn consuming Transactions.
     *
     * @param userContext the user information for the user that created this transaction
     */
    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    /**
     * Getter for the Transaction Info of this transaction.  This field is not persisted and is only used
     * when consuming transactions locally.
     *
     * @return the transaction info field
     */
    public TransactionInfo getTransactionInfo() {
        return transactionInfo;
    }

    /**
     * Setter for the transaction info of this transaction.  This field is not persisted and is only used
     * when consuming transactions locally.
     *
     * @param transactionInfo the transaction info to set
     */
    public void setTransactionInfo(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

   /** An enumeration of Transaction Status. */
    public enum Status {O, S, E, H, I, INT}

    /** An enumeration of Transaction Direction. */
    public enum Direction {IN, OUT}

    /** Generate the technical-key, if required. */
    public void generateKey() throws ApplicationExceptions, FrameworkException {
        if (getId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(TransactionMeta.ID);
                vg.setLabelToken(TransactionMeta.META_ID.getLabelToken());
                setId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    /** A convenience method to set the errorMessage based on the input Exception, as well as set the status to E. */
    public void stampError(Exception exception) throws ValidationException, FrameworkException {
        String em = ExceptionHelper.extractErrorMessage(exception);
        int maxLength = ((StringFieldMetaData) TransactionMeta.META_ERROR_MESSAGE).getLength();
        if (em.length() > maxLength)
            em = em.substring(0, maxLength);

        setStatus(Status.E.toString());
        setErrorMessage(em);
    }

    @Override
    public void postAdd() throws PostAddFailedException {
        super.postAdd();
    }

    @Override
    public void postUpdate() throws PostUpdateFailedException {
        super.postUpdate();

        //If status changes to "S", then UPDATE TRANSACTION_DEPENDENCIES SET STATUS='S' WHERE DEPENDS_ON_ID={thisTransaction.id} and STATUS='O'
        if (isModified(TransactionMeta.STATUS) && Status.S.toString().equals(getStatus())) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Transaction status has changed to S. Will update all Open TransactionDependency records that depend on this Transaction to status S.");
                }
                Collection<TransactionDependency> dependencies = getTransactionDAO().getOpenDependenciesByDependsOnId(getId());
                for (TransactionDependency transactionDependency : dependencies) {
                    transactionDependency.setUOW(getUOW());
                    transactionDependency.setStatus(TransactionDependency.Status.S.toString());
                    getUOW().update(transactionDependency);
                }
            } catch (Exception e) {
                String s = "Exception in satisfying all Open TransactionDependency records that depend on the Transaction instance " + this;
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null) {
                    if (log.isDebugEnabled()) {
                        log.debug(s, appExps);
                    }
                } else {
                    log.error(s, e);
                }
                throw new PostUpdateFailedException(new String[]{s}, e);
            }
        }

        //If status changes to "O", write to JMS
        //However, do not write to JMS if a HALT record exists for the given Transaction type
        if (isModified(TransactionMeta.STATUS) && Direction.IN.toString().equals(getDirection()) && Status.O.toString().equals(getStatus())) {
            try {
              if (log.isDebugEnabled())
                log.debug("Transaction status has changed to O. Will write to JMS.");
              getUOW().addMessage(getTransactionPayloadObject());
            } catch (Exception e) {
                String s = "Exception in writing to JMS for the just Opened Transaction instance " + this;
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
    public void preDelete() throws PreDeleteFailedException {
        super.preDelete();

        //DELETE FROM TRANSACTION_DEPENDENCIES WHERE DEPENDS_ON_ID={thisTransaction.id}
        try {
            if (log.isDebugEnabled()) {
                log.debug("Deleting all TransactionDependency records that depend on this Transaction");
            }
            Collection<TransactionDependency> dependencies = getTransactionDAO().getDependenciesByDependsOnId(getId());
            if (dependencies == null) {
                return;
            }
            for (TransactionDependency transactionDependency : dependencies) {
                transactionDependency.setUOW(getUOW());
                getUOW().delete(transactionDependency);
            }
        } catch (Exception e) {
            String s = "Exception in deleting TransactionDependency records that depend on the Transaction instance " + this;
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled()) {
                    log.debug(s, appExps);
                }
            } else {
                log.error(s, e);
            }
            throw new PreDeleteFailedException(new String[]{s}, e);
        }
    }

    @Override
    public void postDelete() throws PostDeleteFailedException {
        super.postDelete();
    }

    /**
     * Set the transactionPayload
     * @param transactionPayload the transaction payload
     * @throws FrameworkException
     */
    public void setTransactionPayload(TransactionPayload transactionPayload) throws FrameworkException {
        this.transactionPayloadObject = transactionPayload;
    }

    /**
     * Set the transaction dependency collection
     * @param transactionDependencyCollection the transaction dependency collection
     */
    public void setTransactionDependencyCollection(Collection transactionDependencyCollection) {
        this.transactionDependencyCollection = new ArrayList(transactionDependencyCollection);
    }

    /**
     * Set the transaction fields collection
     * @param transactionFieldCollection
     */
    public void setTransactionFieldCollection(Collection transactionFieldCollection){
        this.transactionFieldCollection = new ArrayList(transactionFieldCollection);
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
