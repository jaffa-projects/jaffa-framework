// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.transaction.domain;

import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.jaffa.datatypes.*;
import org.jaffa.datatypes.adapters.DateOnlyType;
import org.jaffa.datatypes.adapters.DateTimeType;
import org.jaffa.metadata.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.util.*;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationExceptions;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import java.nio.charset.Charset;
import javax.xml.bind.JAXBException;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_TRANSACTION_PAYLOADS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "J_TRANSACTION_PAYLOADS")
@SqlResultSetMapping(name="TransactionPayload",entities={@EntityResult(entityClass=TransactionPayload.class)})
public class TransactionPayload extends Persistent {

    private static final Logger log = Logger.getLogger(TransactionPayload.class);

    @XmlElement(name="id")
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private java.lang.String m_id;

    @XmlElement(name="externalMessage")
    @Lob
    @Column(name = "EXTERNAL_MESSAGE")
    private byte[] m_externalMessage;

    @XmlElement(name="internalMessage")
    @Lob
    @Column(name = "INTERNAL_MESSAGE")
    private byte[] m_internalMessage;

    @XmlElement(name="internalMessageClass")
    @Column(name = "INTERNAL_MESSAGE_CLASS")
    private java.lang.String m_internalMessageClass;

    private transient Transaction m_transactionObject;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public TransactionPayload() {
        StaticContext.configure(this);
    }

    /**
     * Check if the domain object exists for the input Primary Key.
     *
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String id) throws FrameworkException {
        // TODO this is generated...
        return (findByPK(uow, id) != null);
    }

    /**
     * Returns the domain object for the input Primary Key.
     *
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static TransactionPayload findByPK(UOW uow, java.lang.String id) throws FrameworkException {
        // TODO this is generated...
        TransactionPayload result = getTransactionDAO().getTransactionPayload(id);
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
    public static Criteria findByPKCriteria(java.lang.String id) {
        // TODO this is generated...
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
        return m_id;
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
        if (m_id == null ? id == null : m_id.equals(id))
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
        super.addInitialValue(TransactionPayloadMeta.ID, m_id);
        m_id = id;
        m_transactionObject = null;
        // .//GEN-END:id_1_be
        // Add custom code after setting the value//GEN-FIRST:id_3


        // .//GEN-LAST:id_3
        // .//GEN-BEGIN:id_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setId() method.
     *
     * @param id New value of property id.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateId(java.lang.String id)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setId(id);
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
        id = FieldValidator.validate(id, (StringFieldMetaData) TransactionPayloadMeta.META_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionPayloadMeta.getName(), TransactionPayloadMeta.ID, id, this.getUOW());

        // .//GEN-END:id_3_be
        // Add custom code after a successful validation//GEN-FIRST:id_2


        // .//GEN-LAST:id_2
        // .//GEN-BEGIN:id_4_be
        return id;
    }
    // .//GEN-END:id_4_be
    // .//GEN-BEGIN:externalMessage_be
    /**
     * Getter for property externalMessage.
     *
     * @return Value of property externalMessage.
     */
    public byte[] getExternalMessage() {
        return m_externalMessage;
    }
    
    /**
     * Use this method to update the property externalMessage.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param externalMessage New value of property externalMessage.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setExternalMessage(byte[] externalMessage)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_externalMessage == null ? externalMessage == null : m_externalMessage.equals(externalMessage))
            return;


        externalMessage = validateExternalMessage(externalMessage);
        // .//GEN-END:externalMessage_be
        // Add custom code before setting the value//GEN-FIRST:externalMessage


        // .//GEN-LAST:externalMessage
        // .//GEN-BEGIN:externalMessage_1_be
        super.update();
        super.addInitialValue(TransactionPayloadMeta.EXTERNAL_MESSAGE, m_externalMessage);
        m_externalMessage = externalMessage;
        // .//GEN-END:externalMessage_1_be
        // Add custom code after setting the value//GEN-FIRST:externalMessage_3


        // .//GEN-LAST:externalMessage_3
        // .//GEN-BEGIN:externalMessage_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setExternalMessage() method.
     *
     * @param externalMessage New value of property externalMessage.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateExternalMessage(byte[] externalMessage)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setExternalMessage(externalMessage);
    }

    /**
     * Use this method to validate a value for the property externalMessage.
     *
     * @param externalMessage Value to be validated for the property externalMessage.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validateExternalMessage(byte[] externalMessage)
    throws ValidationException, FrameworkException {
        // .//GEN-END:externalMessage_2_be
        // Add custom code before validation//GEN-FIRST:externalMessage_1


        // .//GEN-LAST:externalMessage_1
        // .//GEN-BEGIN:externalMessage_3_be
        externalMessage = FieldValidator.validate(externalMessage, (RawFieldMetaData) TransactionPayloadMeta.META_EXTERNAL_MESSAGE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionPayloadMeta.getName(), TransactionPayloadMeta.EXTERNAL_MESSAGE, externalMessage, this.getUOW());

        // .//GEN-END:externalMessage_3_be
        // Add custom code after a successful validation//GEN-FIRST:externalMessage_2


        // .//GEN-LAST:externalMessage_2
        // .//GEN-BEGIN:externalMessage_4_be
        return externalMessage;
    }
    // .//GEN-END:externalMessage_4_be
    // .//GEN-BEGIN:internalMessage_be
    /**
     * Getter for property internalMessage.
     *
     * @return Value of property internalMessage.
     */
    public byte[] getInternalMessage() {
        return m_internalMessage;
    }
    
    /**
     * Use this method to update the property internalMessage.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param internalMessage New value of property internalMessage.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setInternalMessage(byte[] internalMessage)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_internalMessage == null ? internalMessage == null : m_internalMessage.equals(internalMessage))
            return;


        internalMessage = validateInternalMessage(internalMessage);
        // .//GEN-END:internalMessage_be
        // Add custom code before setting the value//GEN-FIRST:internalMessage


        // .//GEN-LAST:internalMessage
        // .//GEN-BEGIN:internalMessage_1_be
        super.update();
        super.addInitialValue(TransactionPayloadMeta.INTERNAL_MESSAGE, m_internalMessage);
        m_internalMessage = internalMessage;
        // .//GEN-END:internalMessage_1_be
        // Add custom code after setting the value//GEN-FIRST:internalMessage_3


        // .//GEN-LAST:internalMessage_3
        // .//GEN-BEGIN:internalMessage_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setInternalMessage() method.
     *
     * @param internalMessage New value of property internalMessage.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateInternalMessage(byte[] internalMessage)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setInternalMessage(internalMessage);
    }

    /**
     * Use this method to validate a value for the property internalMessage.
     *
     * @param internalMessage Value to be validated for the property internalMessage.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validateInternalMessage(byte[] internalMessage)
    throws ValidationException, FrameworkException {
        // .//GEN-END:internalMessage_2_be
        // Add custom code before validation//GEN-FIRST:internalMessage_1


        // .//GEN-LAST:internalMessage_1
        // .//GEN-BEGIN:internalMessage_3_be
        internalMessage = FieldValidator.validate(internalMessage, (RawFieldMetaData) TransactionPayloadMeta.META_INTERNAL_MESSAGE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionPayloadMeta.getName(), TransactionPayloadMeta.INTERNAL_MESSAGE, internalMessage, this.getUOW());

        // .//GEN-END:internalMessage_3_be
        // Add custom code after a successful validation//GEN-FIRST:internalMessage_2


        // .//GEN-LAST:internalMessage_2
        // .//GEN-BEGIN:internalMessage_4_be
        return internalMessage;
    }
    // .//GEN-END:internalMessage_4_be
    // .//GEN-BEGIN:internalMessageClass_be
    /**
     * Getter for property internalMessageClass.
     *
     * @return Value of property internalMessageClass.
     */
    public java.lang.String getInternalMessageClass() {
        return m_internalMessageClass;
    }
    
    /**
     * Use this method to update the property internalMessageClass.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     *
     * @param internalMessageClass New value of property internalMessageClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setInternalMessageClass(java.lang.String internalMessageClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_internalMessageClass == null ? internalMessageClass == null : m_internalMessageClass.equals(internalMessageClass))
            return;


        internalMessageClass = validateInternalMessageClass(internalMessageClass);
        // .//GEN-END:internalMessageClass_be
        // Add custom code before setting the value//GEN-FIRST:internalMessageClass


        // .//GEN-LAST:internalMessageClass
        // .//GEN-BEGIN:internalMessageClass_1_be
        super.update();
        super.addInitialValue(TransactionPayloadMeta.INTERNAL_MESSAGE_CLASS, m_internalMessageClass);
        m_internalMessageClass = internalMessageClass;
        // .//GEN-END:internalMessageClass_1_be
        // Add custom code after setting the value//GEN-FIRST:internalMessageClass_3


        // .//GEN-LAST:internalMessageClass_3
        // .//GEN-BEGIN:internalMessageClass_2_be
    }
    
    /**
     * This method is present for backwards compatibility only.
     * It merely invokes the setInternalMessageClass() method.
     *
     * @param internalMessageClass New value of property internalMessageClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateInternalMessageClass(java.lang.String internalMessageClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setInternalMessageClass(internalMessageClass);
    }

    /**
     * Use this method to validate a value for the property internalMessageClass.
     *
     * @param internalMessageClass Value to be validated for the property internalMessageClass.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateInternalMessageClass(java.lang.String internalMessageClass)
    throws ValidationException, FrameworkException {
        // .//GEN-END:internalMessageClass_2_be
        // Add custom code before validation//GEN-FIRST:internalMessageClass_1


        // .//GEN-LAST:internalMessageClass_1
        // .//GEN-BEGIN:internalMessageClass_3_be
        internalMessageClass = FieldValidator.validate(internalMessageClass, (StringFieldMetaData) TransactionPayloadMeta.META_INTERNAL_MESSAGE_CLASS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TransactionPayloadMeta.getName(), TransactionPayloadMeta.INTERNAL_MESSAGE_CLASS, internalMessageClass, this.getUOW());

        // .//GEN-END:internalMessageClass_3_be
        // Add custom code after a successful validation//GEN-FIRST:internalMessageClass_2


        // .//GEN-LAST:internalMessageClass_2
        // .//GEN-BEGIN:internalMessageClass_4_be
        return internalMessageClass;
    }
    // .//GEN-END:internalMessageClass_4_be
    // .//GEN-BEGIN:transactionObject_1_be
    /**
     * Returns a related Transaction object.
     *
     * @return a related Transaction object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public Transaction getTransactionObject() throws ValidationException, FrameworkException {
        findTransactionObject(false);
        return m_transactionObject;
    }
    
    /**
     * Finds the related Transaction object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findTransactionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        // TODO this is generated...
        if ((m_transactionObject == null) && (getId() != null)) {
            Transaction transaction = getTransactionDAO().getTransaction(getId());
            Number count = null;
            if (checkExistenceOnly) {
                if (transaction != null) {
                    count = 1;
                }
            } else {
                m_transactionObject = transaction;
            }
            if ((m_transactionObject == null) && ((count == null) || (count.intValue() <= 0))) {
                throw new InvalidForeignKeyException(TransactionPayloadMeta.META_ID.getLabelToken(),
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
        buf.append("<TransactionPayload>");
        buf.append("<id>"); if (m_id != null) buf.append(m_id); buf.append("</id>");
        buf.append("<externalMessage>"); if (m_externalMessage != null) buf.append(m_externalMessage); buf.append("</externalMessage>");
        buf.append("<internalMessage>"); if (m_internalMessage != null) buf.append(m_internalMessage); buf.append("</internalMessage>");
        buf.append("<internalMessageClass>"); if (m_internalMessageClass != null) buf.append(m_internalMessageClass); buf.append("</internalMessageClass>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</TransactionPayload>");
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
        TransactionPayload obj = (TransactionPayload) super.clone();
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
     * @clientCardinality 0..1
     * @supplierCardinality 1
     * @clientQualifier id
     * @supplierQualifier id
     * @link association
     */
    /*#Transaction lnkTransaction;*/

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * A helper method to set the internalMessage and internalMessageClass, based on the input payload.
     * @param payload the input to be marshalled into XML.
     */
    public void addInternalPayload(Object payload) throws ValidationException, FrameworkException {
        if (payload == null) {
            setInternalMessage(null);
            setInternalMessageClass(null);
        } else {
            String xml = null;
            try {
                xml = JAXBHelper.marshalPayload(payload);
                setInternalMessage(xml.getBytes(CHARSET));
                setInternalMessageClass(payload.getClass().getName());
            } catch (JAXBException e) {
                String s = "Error in marshalling '" + payload + "' to XML via JAXB";
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        }
    }

    /**
     * A helper method to mold the the internalMessage into an Object, based off the internalMessageClass.
     * @return payload the input to be marshalled into XML.
     */
    public Object moldInternalPayload() {
        Object payload = null;
        if (getInternalMessage() != null && getInternalMessageClass() != null) {
            String xml = null;
            try {
                xml = new String(getInternalMessage(), CHARSET);
                payload = JAXBHelper.unmarshalPayload(xml, getInternalMessageClass());
            } catch (Exception e) {
                String s = "Error in unmarshalling '" + xml + "' to a Java object via JAXB";
                log.error(s, e);
                throw new RuntimeException(s, e);
            }
        }
        return payload;
    }

    /**
     * A helper method to mold the the internalMessage into an XML String.
     */
    public String returnInternalPayload() {
        return getInternalMessage() != null ? new String(getInternalMessage(), CHARSET) : null;
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
