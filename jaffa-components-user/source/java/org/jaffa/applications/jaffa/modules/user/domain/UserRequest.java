// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.user.domain;

import org.apache.log4j.Logger;
import java.util.*;
import javax.xml.bind.annotation.*;
import javax.persistence.*;
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
import org.jaffa.exceptions.RelatedDomainObjectFoundException;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.applications.jaffa.modules.user.exceptions.RequestException;
import java.security.Principal;
import org.jaffa.security.SecurityManager;


// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the USER_REQUEST table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="USER_REQUEST")
@SqlResultSetMapping(name="UserRequest",entities={@EntityResult(entityClass=UserRequest.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class UserRequest extends Persistent {

    private static final Logger log = Logger.getLogger(UserRequest.class);
    /** Holds value of property requestId. */
    @XmlElement(name="requestId")
    @Id
    @Column(name="REQUEST_ID")    
    private java.lang.Long m_requestId;

    /** Holds value of property userName. */
    @XmlElement(name="userName")
    @Column(name="USER_NAME")    
    private java.lang.String m_userName;

    /** Holds value of property firstName. */
    @XmlElement(name="firstName")
    @Column(name="FIRST_NAME")    
    private java.lang.String m_firstName;

    /** Holds value of property lastName. */
    @XmlElement(name="lastName")
    @Column(name="LAST_NAME")    
    private java.lang.String m_lastName;

    /** Holds value of property password. */
    @XmlElement(name="password")
    @Column(name="PASSWORD")    
    private java.lang.String m_password;

    /** Holds value of property eMailAddress. */
    @XmlElement(name="EMailAddress")
    @Column(name="E_MAIL_ADDRESS")    
    private java.lang.String m_eMailAddress;

    /** Holds value of property securityQuestion. */
    @XmlElement(name="securityQuestion")
    @Column(name="SECURITY_QUESTION")    
    private java.lang.Long m_securityQuestion;

    /** Holds value of property securityAnswer. */
    @XmlElement(name="securityAnswer")
    @Column(name="SECURITY_ANSWER")    
    private java.lang.String m_securityAnswer;

    /** Holds value of property remarks. */
    @XmlElement(name="remarks")
    @Column(name="REMARKS")    
    private java.lang.String m_remarks;

    /** Holds value of property createdOn. */
    @XmlElement(name="createdOn")
    @Type(type="dateTimeType")
    @Column(name="CREATED_DATETIME")    
    private org.jaffa.datatypes.DateTime m_createdOn;

    /** Holds value of property processedDatetime. */
    @XmlElement(name="processedDatetime")
    @Type(type="dateTimeType")
    @Column(name="PROCESSED_DATETIME")    
    private org.jaffa.datatypes.DateTime m_processedDatetime;

    /** Holds value of property processedUserId. */
    @XmlElement(name="processedUserId")
    @Column(name="PROCESSED_USER")    
    private java.lang.String m_processedUserId;

    /** Holds value of property status. */
    @XmlElement(name="status")
    @Column(name="STATUS")    
    private java.lang.String m_status;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public UserRequest() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.Long requestId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(requestId);
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
    public static UserRequest findByPK(UOW uow, java.lang.Long requestId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(requestId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (UserRequest) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.Long requestId) {
        Criteria criteria = new Criteria();
        criteria.setTable(UserRequestMeta.getName());
        criteria.addCriteria(UserRequestMeta.REQUEST_ID, requestId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:requestId_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        return m_requestId;
    }
    
    /** Use this method to update the property requestId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param requestId New value of property requestId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRequestId(java.lang.Long requestId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_requestId == null ? requestId == null : m_requestId.equals(requestId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        requestId = validateRequestId(requestId);
        // .//GEN-END:requestId_be
        // Add custom code before setting the value//GEN-FIRST:requestId


        // .//GEN-LAST:requestId
        // .//GEN-BEGIN:requestId_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.REQUEST_ID, m_requestId);
        m_requestId = requestId;
        // .//GEN-END:requestId_1_be
        // Add custom code after setting the value//GEN-FIRST:requestId_3


        // .//GEN-LAST:requestId_3
        // .//GEN-BEGIN:requestId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRequestId() method.
     * @param requestId New value of property requestId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRequestId(java.lang.Long requestId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRequestId(requestId);
    }

    /** Use this method to validate a value for the property requestId.
     * @param requestId Value to be validated for the property requestId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateRequestId(java.lang.Long requestId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:requestId_2_be
        // Add custom code before validation//GEN-FIRST:requestId_1


        // .//GEN-LAST:requestId_1
        // .//GEN-BEGIN:requestId_3_be
        requestId = FieldValidator.validate(requestId, (IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.REQUEST_ID, requestId, this.getUOW());

        // .//GEN-END:requestId_3_be
        // Add custom code after a successful validation//GEN-FIRST:requestId_2


        // .//GEN-LAST:requestId_2
        // .//GEN-BEGIN:requestId_4_be
        return requestId;
    }
    // .//GEN-END:requestId_4_be
    // .//GEN-BEGIN:userName_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return m_userName;
    }
    
    /** Use this method to update the property userName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setUserName(java.lang.String userName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_userName == null ? userName == null : m_userName.equals(userName))
            return;


        userName = validateUserName(userName);
        // .//GEN-END:userName_be
        // Add custom code before setting the value//GEN-FIRST:userName


        // .//GEN-LAST:userName
        // .//GEN-BEGIN:userName_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.USER_NAME, m_userName);
        m_userName = userName;
        // .//GEN-END:userName_1_be
        // Add custom code after setting the value//GEN-FIRST:userName_3


        // .//GEN-LAST:userName_3
        // .//GEN-BEGIN:userName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setUserName() method.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateUserName(java.lang.String userName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setUserName(userName);
    }

    /** Use this method to validate a value for the property userName.
     * @param userName Value to be validated for the property userName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateUserName(java.lang.String userName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:userName_2_be
        // Add custom code before validation//GEN-FIRST:userName_1


        // .//GEN-LAST:userName_1
        // .//GEN-BEGIN:userName_3_be
        userName = FieldValidator.validate(userName, (StringFieldMetaData) UserRequestMeta.META_USER_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.USER_NAME, userName, this.getUOW());

        // .//GEN-END:userName_3_be
        // Add custom code after a successful validation//GEN-FIRST:userName_2


        // .//GEN-LAST:userName_2
        // .//GEN-BEGIN:userName_4_be
        return userName;
    }
    // .//GEN-END:userName_4_be
    // .//GEN-BEGIN:firstName_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        return m_firstName;
    }
    
    /** Use this method to update the property firstName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param firstName New value of property firstName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFirstName(java.lang.String firstName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_firstName == null ? firstName == null : m_firstName.equals(firstName))
            return;


        firstName = validateFirstName(firstName);
        // .//GEN-END:firstName_be
        // Add custom code before setting the value//GEN-FIRST:firstName


        // .//GEN-LAST:firstName
        // .//GEN-BEGIN:firstName_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.FIRST_NAME, m_firstName);
        m_firstName = firstName;
        // .//GEN-END:firstName_1_be
        // Add custom code after setting the value//GEN-FIRST:firstName_3


        // .//GEN-LAST:firstName_3
        // .//GEN-BEGIN:firstName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFirstName() method.
     * @param firstName New value of property firstName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFirstName(java.lang.String firstName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFirstName(firstName);
    }

    /** Use this method to validate a value for the property firstName.
     * @param firstName Value to be validated for the property firstName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFirstName(java.lang.String firstName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:firstName_2_be
        // Add custom code before validation//GEN-FIRST:firstName_1


        // .//GEN-LAST:firstName_1
        // .//GEN-BEGIN:firstName_3_be
        firstName = FieldValidator.validate(firstName, (StringFieldMetaData) UserRequestMeta.META_FIRST_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.FIRST_NAME, firstName, this.getUOW());

        // .//GEN-END:firstName_3_be
        // Add custom code after a successful validation//GEN-FIRST:firstName_2


        // .//GEN-LAST:firstName_2
        // .//GEN-BEGIN:firstName_4_be
        return firstName;
    }
    // .//GEN-END:firstName_4_be
    // .//GEN-BEGIN:lastName_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        return m_lastName;
    }
    
    /** Use this method to update the property lastName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param lastName New value of property lastName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastName(java.lang.String lastName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_lastName == null ? lastName == null : m_lastName.equals(lastName))
            return;


        lastName = validateLastName(lastName);
        // .//GEN-END:lastName_be
        // Add custom code before setting the value//GEN-FIRST:lastName


        // .//GEN-LAST:lastName
        // .//GEN-BEGIN:lastName_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.LAST_NAME, m_lastName);
        m_lastName = lastName;
        // .//GEN-END:lastName_1_be
        // Add custom code after setting the value//GEN-FIRST:lastName_3


        // .//GEN-LAST:lastName_3
        // .//GEN-BEGIN:lastName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLastName() method.
     * @param lastName New value of property lastName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLastName(java.lang.String lastName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLastName(lastName);
    }

    /** Use this method to validate a value for the property lastName.
     * @param lastName Value to be validated for the property lastName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLastName(java.lang.String lastName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:lastName_2_be
        // Add custom code before validation//GEN-FIRST:lastName_1


        // .//GEN-LAST:lastName_1
        // .//GEN-BEGIN:lastName_3_be
        lastName = FieldValidator.validate(lastName, (StringFieldMetaData) UserRequestMeta.META_LAST_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.LAST_NAME, lastName, this.getUOW());

        // .//GEN-END:lastName_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastName_2


        // .//GEN-LAST:lastName_2
        // .//GEN-BEGIN:lastName_4_be
        return lastName;
    }
    // .//GEN-END:lastName_4_be
    // .//GEN-BEGIN:password_be
    /** Getter for property password.
     * @return Value of property password.
     */
    public java.lang.String getPassword() {
        return m_password;
    }
    
    /** Use this method to update the property password.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param password New value of property password.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPassword(java.lang.String password)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_password == null ? password == null : m_password.equals(password))
            return;


        password = validatePassword(password);
        // .//GEN-END:password_be
        // Add custom code before setting the value//GEN-FIRST:password


        // .//GEN-LAST:password
        // .//GEN-BEGIN:password_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.PASSWORD, m_password);
        m_password = password;
        // .//GEN-END:password_1_be
        // Add custom code after setting the value//GEN-FIRST:password_3


        // .//GEN-LAST:password_3
        // .//GEN-BEGIN:password_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPassword() method.
     * @param password New value of property password.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePassword(java.lang.String password)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPassword(password);
    }

    /** Use this method to validate a value for the property password.
     * @param password Value to be validated for the property password.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePassword(java.lang.String password)
    throws ValidationException, FrameworkException {
        // .//GEN-END:password_2_be
        // Add custom code before validation//GEN-FIRST:password_1


        // .//GEN-LAST:password_1
        // .//GEN-BEGIN:password_3_be
        password = FieldValidator.validate(password, (StringFieldMetaData) UserRequestMeta.META_PASSWORD, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.PASSWORD, password, this.getUOW());

        // .//GEN-END:password_3_be
        // Add custom code after a successful validation//GEN-FIRST:password_2


        // .//GEN-LAST:password_2
        // .//GEN-BEGIN:password_4_be
        return password;
    }
    // .//GEN-END:password_4_be
    // .//GEN-BEGIN:eMailAddress_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public java.lang.String getEMailAddress() {
        return m_eMailAddress;
    }
    
    /** Use this method to update the property eMailAddress.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param eMailAddress New value of property eMailAddress.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setEMailAddress(java.lang.String eMailAddress)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_eMailAddress == null ? eMailAddress == null : m_eMailAddress.equals(eMailAddress))
            return;


        eMailAddress = validateEMailAddress(eMailAddress);
        // .//GEN-END:eMailAddress_be
        // Add custom code before setting the value//GEN-FIRST:eMailAddress


        // .//GEN-LAST:eMailAddress
        // .//GEN-BEGIN:eMailAddress_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.E_MAIL_ADDRESS, m_eMailAddress);
        m_eMailAddress = eMailAddress;
        // .//GEN-END:eMailAddress_1_be
        // Add custom code after setting the value//GEN-FIRST:eMailAddress_3


        // .//GEN-LAST:eMailAddress_3
        // .//GEN-BEGIN:eMailAddress_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setEMailAddress() method.
     * @param eMailAddress New value of property eMailAddress.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateEMailAddress(java.lang.String eMailAddress)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setEMailAddress(eMailAddress);
    }

    /** Use this method to validate a value for the property eMailAddress.
     * @param eMailAddress Value to be validated for the property eMailAddress.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateEMailAddress(java.lang.String eMailAddress)
    throws ValidationException, FrameworkException {
        // .//GEN-END:eMailAddress_2_be
        // Add custom code before validation//GEN-FIRST:eMailAddress_1


        // .//GEN-LAST:eMailAddress_1
        // .//GEN-BEGIN:eMailAddress_3_be
        eMailAddress = FieldValidator.validate(eMailAddress, (StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.E_MAIL_ADDRESS, eMailAddress, this.getUOW());

        // .//GEN-END:eMailAddress_3_be
        // Add custom code after a successful validation//GEN-FIRST:eMailAddress_2


        // .//GEN-LAST:eMailAddress_2
        // .//GEN-BEGIN:eMailAddress_4_be
        return eMailAddress;
    }
    // .//GEN-END:eMailAddress_4_be
    // .//GEN-BEGIN:securityQuestion_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        return m_securityQuestion;
    }
    
    /** Use this method to update the property securityQuestion.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param securityQuestion New value of property securityQuestion.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSecurityQuestion(java.lang.Long securityQuestion)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_securityQuestion == null ? securityQuestion == null : m_securityQuestion.equals(securityQuestion))
            return;


        securityQuestion = validateSecurityQuestion(securityQuestion);
        // .//GEN-END:securityQuestion_be
        // Add custom code before setting the value//GEN-FIRST:securityQuestion


        // .//GEN-LAST:securityQuestion
        // .//GEN-BEGIN:securityQuestion_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.SECURITY_QUESTION, m_securityQuestion);
        m_securityQuestion = securityQuestion;
        // .//GEN-END:securityQuestion_1_be
        // Add custom code after setting the value//GEN-FIRST:securityQuestion_3


        // .//GEN-LAST:securityQuestion_3
        // .//GEN-BEGIN:securityQuestion_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSecurityQuestion() method.
     * @param securityQuestion New value of property securityQuestion.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSecurityQuestion(java.lang.Long securityQuestion)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSecurityQuestion(securityQuestion);
    }

    /** Use this method to validate a value for the property securityQuestion.
     * @param securityQuestion Value to be validated for the property securityQuestion.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateSecurityQuestion(java.lang.Long securityQuestion)
    throws ValidationException, FrameworkException {
        // .//GEN-END:securityQuestion_2_be
        // Add custom code before validation//GEN-FIRST:securityQuestion_1


        // .//GEN-LAST:securityQuestion_1
        // .//GEN-BEGIN:securityQuestion_3_be
        securityQuestion = FieldValidator.validate(securityQuestion, (IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.SECURITY_QUESTION, securityQuestion, this.getUOW());

        // .//GEN-END:securityQuestion_3_be
        // Add custom code after a successful validation//GEN-FIRST:securityQuestion_2


        // .//GEN-LAST:securityQuestion_2
        // .//GEN-BEGIN:securityQuestion_4_be
        return securityQuestion;
    }
    // .//GEN-END:securityQuestion_4_be
    // .//GEN-BEGIN:securityAnswer_be
    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        return m_securityAnswer;
    }
    
    /** Use this method to update the property securityAnswer.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param securityAnswer New value of property securityAnswer.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSecurityAnswer(java.lang.String securityAnswer)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_securityAnswer == null ? securityAnswer == null : m_securityAnswer.equals(securityAnswer))
            return;


        securityAnswer = validateSecurityAnswer(securityAnswer);
        // .//GEN-END:securityAnswer_be
        // Add custom code before setting the value//GEN-FIRST:securityAnswer


        // .//GEN-LAST:securityAnswer
        // .//GEN-BEGIN:securityAnswer_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.SECURITY_ANSWER, m_securityAnswer);
        m_securityAnswer = securityAnswer;
        // .//GEN-END:securityAnswer_1_be
        // Add custom code after setting the value//GEN-FIRST:securityAnswer_3


        // .//GEN-LAST:securityAnswer_3
        // .//GEN-BEGIN:securityAnswer_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSecurityAnswer() method.
     * @param securityAnswer New value of property securityAnswer.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSecurityAnswer(java.lang.String securityAnswer)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSecurityAnswer(securityAnswer);
    }

    /** Use this method to validate a value for the property securityAnswer.
     * @param securityAnswer Value to be validated for the property securityAnswer.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSecurityAnswer(java.lang.String securityAnswer)
    throws ValidationException, FrameworkException {
        // .//GEN-END:securityAnswer_2_be
        // Add custom code before validation//GEN-FIRST:securityAnswer_1


        // .//GEN-LAST:securityAnswer_1
        // .//GEN-BEGIN:securityAnswer_3_be
        securityAnswer = FieldValidator.validate(securityAnswer, (StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.SECURITY_ANSWER, securityAnswer, this.getUOW());

        // .//GEN-END:securityAnswer_3_be
        // Add custom code after a successful validation//GEN-FIRST:securityAnswer_2


        // .//GEN-LAST:securityAnswer_2
        // .//GEN-BEGIN:securityAnswer_4_be
        return securityAnswer;
    }
    // .//GEN-END:securityAnswer_4_be
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
        super.addInitialValue(UserRequestMeta.REMARKS, m_remarks);
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
        remarks = FieldValidator.validate(remarks, (StringFieldMetaData) UserRequestMeta.META_REMARKS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.REMARKS, remarks, this.getUOW());

        // .//GEN-END:remarks_3_be
        // Add custom code after a successful validation//GEN-FIRST:remarks_2


        // .//GEN-LAST:remarks_2
        // .//GEN-BEGIN:remarks_4_be
        return remarks;
    }
    // .//GEN-END:remarks_4_be
    // .//GEN-BEGIN:createdOn_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }
    
    /** Use this method to update the property createdOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param createdOn New value of property createdOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_createdOn == null ? createdOn == null : m_createdOn.equals(createdOn))
            return;


        createdOn = validateCreatedOn(createdOn);
        // .//GEN-END:createdOn_be
        // Add custom code before setting the value//GEN-FIRST:createdOn


        // .//GEN-LAST:createdOn
        // .//GEN-BEGIN:createdOn_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.CREATED_ON, m_createdOn);
        m_createdOn = createdOn;
        // .//GEN-END:createdOn_1_be
        // Add custom code after setting the value//GEN-FIRST:createdOn_3


        // .//GEN-LAST:createdOn_3
        // .//GEN-BEGIN:createdOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCreatedOn() method.
     * @param createdOn New value of property createdOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCreatedOn(org.jaffa.datatypes.DateTime createdOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCreatedOn(createdOn);
    }

    /** Use this method to validate a value for the property createdOn.
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) UserRequestMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.CREATED_ON, createdOn, this.getUOW());

        // .//GEN-END:createdOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdOn_2


        // .//GEN-LAST:createdOn_2
        // .//GEN-BEGIN:createdOn_4_be
        return createdOn;
    }
    // .//GEN-END:createdOn_4_be
    // .//GEN-BEGIN:processedDatetime_be
    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public org.jaffa.datatypes.DateTime getProcessedDatetime() {
        return m_processedDatetime;
    }
    
    /** Use this method to update the property processedDatetime.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param processedDatetime New value of property processedDatetime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_processedDatetime == null ? processedDatetime == null : m_processedDatetime.equals(processedDatetime))
            return;


        processedDatetime = validateProcessedDatetime(processedDatetime);
        // .//GEN-END:processedDatetime_be
        // Add custom code before setting the value//GEN-FIRST:processedDatetime


        // .//GEN-LAST:processedDatetime
        // .//GEN-BEGIN:processedDatetime_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.PROCESSED_DATETIME, m_processedDatetime);
        m_processedDatetime = processedDatetime;
        // .//GEN-END:processedDatetime_1_be
        // Add custom code after setting the value//GEN-FIRST:processedDatetime_3


        // .//GEN-LAST:processedDatetime_3
        // .//GEN-BEGIN:processedDatetime_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setProcessedDatetime() method.
     * @param processedDatetime New value of property processedDatetime.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setProcessedDatetime(processedDatetime);
    }

    /** Use this method to validate a value for the property processedDatetime.
     * @param processedDatetime Value to be validated for the property processedDatetime.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validateProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime)
    throws ValidationException, FrameworkException {
        // .//GEN-END:processedDatetime_2_be
        // Add custom code before validation//GEN-FIRST:processedDatetime_1


        // .//GEN-LAST:processedDatetime_1
        // .//GEN-BEGIN:processedDatetime_3_be
        processedDatetime = FieldValidator.validate(processedDatetime, (DateTimeFieldMetaData) UserRequestMeta.META_PROCESSED_DATETIME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.PROCESSED_DATETIME, processedDatetime, this.getUOW());

        // .//GEN-END:processedDatetime_3_be
        // Add custom code after a successful validation//GEN-FIRST:processedDatetime_2


        // .//GEN-LAST:processedDatetime_2
        // .//GEN-BEGIN:processedDatetime_4_be
        return processedDatetime;
    }
    // .//GEN-END:processedDatetime_4_be
    // .//GEN-BEGIN:processedUserId_be
    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public java.lang.String getProcessedUserId() {
        return m_processedUserId;
    }
    
    /** Use this method to update the property processedUserId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param processedUserId New value of property processedUserId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setProcessedUserId(java.lang.String processedUserId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_processedUserId == null ? processedUserId == null : m_processedUserId.equals(processedUserId))
            return;


        processedUserId = validateProcessedUserId(processedUserId);
        // .//GEN-END:processedUserId_be
        // Add custom code before setting the value//GEN-FIRST:processedUserId


        // .//GEN-LAST:processedUserId
        // .//GEN-BEGIN:processedUserId_1_be
        super.update();
        super.addInitialValue(UserRequestMeta.PROCESSED_USER_ID, m_processedUserId);
        m_processedUserId = processedUserId;
        // .//GEN-END:processedUserId_1_be
        // Add custom code after setting the value//GEN-FIRST:processedUserId_3


        // .//GEN-LAST:processedUserId_3
        // .//GEN-BEGIN:processedUserId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setProcessedUserId() method.
     * @param processedUserId New value of property processedUserId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateProcessedUserId(java.lang.String processedUserId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setProcessedUserId(processedUserId);
    }

    /** Use this method to validate a value for the property processedUserId.
     * @param processedUserId Value to be validated for the property processedUserId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateProcessedUserId(java.lang.String processedUserId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:processedUserId_2_be
        // Add custom code before validation//GEN-FIRST:processedUserId_1


        // .//GEN-LAST:processedUserId_1
        // .//GEN-BEGIN:processedUserId_3_be
        processedUserId = FieldValidator.validate(processedUserId, (StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.PROCESSED_USER_ID, processedUserId, this.getUOW());

        // .//GEN-END:processedUserId_3_be
        // Add custom code after a successful validation//GEN-FIRST:processedUserId_2


        // .//GEN-LAST:processedUserId_2
        // .//GEN-BEGIN:processedUserId_4_be
        return processedUserId;
    }
    // .//GEN-END:processedUserId_4_be
    // .//GEN-BEGIN:status_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return m_status;
    }
    
    /** Use this method to update the property status.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
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
        super.addInitialValue(UserRequestMeta.STATUS, m_status);
        m_status = status;
        // .//GEN-END:status_1_be
        // Add custom code after setting the value//GEN-FIRST:status_3


        // .//GEN-LAST:status_3
        // .//GEN-BEGIN:status_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setStatus() method.
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

    /** Use this method to validate a value for the property status.
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
        status = FieldValidator.validate(status, (StringFieldMetaData) UserRequestMeta.META_STATUS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRequestMeta.getName(), UserRequestMeta.STATUS, status, this.getUOW());

        // .//GEN-END:status_3_be
        // Add custom code after a successful validation//GEN-FIRST:status_2


        // .//GEN-LAST:status_2
        // .//GEN-BEGIN:status_4_be
        return status;
    }
    // .//GEN-END:status_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserRequest>");
        buf.append("<requestId>"); if (m_requestId != null) buf.append(m_requestId); buf.append("</requestId>");
        buf.append("<userName>"); if (m_userName != null) buf.append(m_userName); buf.append("</userName>");
        buf.append("<firstName>"); if (m_firstName != null) buf.append(m_firstName); buf.append("</firstName>");
        buf.append("<lastName>"); if (m_lastName != null) buf.append(m_lastName); buf.append("</lastName>");
        buf.append("<password>"); if (m_password != null) buf.append(m_password); buf.append("</password>");
        buf.append("<eMailAddress>"); if (m_eMailAddress != null) buf.append(m_eMailAddress); buf.append("</eMailAddress>");
        buf.append("<securityQuestion>"); if (m_securityQuestion != null) buf.append(m_securityQuestion); buf.append("</securityQuestion>");
        buf.append("<securityAnswer>"); if (m_securityAnswer != null) buf.append(m_securityAnswer); buf.append("</securityAnswer>");
        buf.append("<remarks>"); if (m_remarks != null) buf.append(m_remarks); buf.append("</remarks>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<processedDatetime>"); if (m_processedDatetime != null) buf.append(m_processedDatetime); buf.append("</processedDatetime>");
        buf.append("<processedUserId>"); if (m_processedUserId != null) buf.append(m_processedUserId); buf.append("</processedUserId>");
        buf.append("<status>"); if (m_status != null) buf.append(m_status); buf.append("</status>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</UserRequest>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        UserRequest obj = (UserRequest) super.clone();
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



      public void preAdd() throws PreAddFailedException {
          try {
              if(getCreatedOn()==null)
                    setCreatedOn(new DateTime());
              super.preAdd();
          } catch (ValidationException e) {


          } catch (FrameworkException e) {

          }

      }

     public void preUpdate() throws PreUpdateFailedException {
         String userId = null;
          try {
              if(getCreatedOn()== null) {
                  ApplicationExceptions aep = new ApplicationExceptions();
                  aep.add(new RequestException(RequestException.CANNOT_UPDATE_REQUEST));
                  throw new PreUpdateFailedException(null, aep);
              } else {
                  Principal p = SecurityManager.getPrincipal();
                  if (p != null) {
                    userId = p.getName();
                    this.setProcessedUserId(userId);
                  }

                  this.setProcessedDatetime(new DateTime());
              }
              super.preUpdate();
          } catch (ValidationException e) {


          } catch (FrameworkException e) {

          }

      }



    // .//GEN-LAST:custom
}
