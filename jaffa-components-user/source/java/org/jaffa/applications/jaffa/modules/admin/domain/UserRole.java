// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.admin.domain;

import java.io.Serializable;
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
import org.jaffa.applications.jaffa.modules.admin.domain.User;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports


// UserRole.xml has been renamed UserRole.xml.bak to prevent code generation.  If your need to regenerate this
// class, make sure entity mapping is unique.  The entity name needs to be unique for Hibernate, or else
// there will be a conflict with GOLDesp UserRole entity.	
// @Entity(name="jaffa.UserRole") 
// @SqlResultSetMapping(name="jaffa.UserRole",entities={@EntityResult(entityClass=UserRole.class)})
// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the USER_ROLE table.
 * @author  Auto-Generated
 */ 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="jaffa.UserRole") 
@Table(name="USER_ROLE")
@SqlResultSetMapping(name="jaffa.UserRole",entities={@EntityResult(entityClass=UserRole.class)})
public class UserRole extends Persistent {

    private static final Logger log = Logger.getLogger(UserRole.class);

    /** Holds value of property m_compositeKey. */
    @XmlElement(name="compositeKey")
    @EmbeddedId
    private CompositeKey m_compositeKey;
    
    /** Holds value of property userName. */
    @Transient
    private java.lang.String m_userName;

    /** Holds value of property roleName. */
    @Transient
    private java.lang.String m_roleName;

    /** Holds related foreign User object. */
    private transient User m_userObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public UserRole() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String userName, java.lang.String roleName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(userName, roleName);
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
    public static UserRole findByPK(UOW uow, java.lang.String userName, java.lang.String roleName) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(userName, roleName);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (UserRole) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String userName, java.lang.String roleName) {
        Criteria criteria = new Criteria();
        criteria.setTable(UserRoleMeta.getName());
        criteria.addCriteria(UserRoleMeta.USER_NAME, userName);
        criteria.addCriteria(UserRoleMeta.ROLE_NAME, roleName);
        return criteria;
    }
    // .//GEN-END:2_be

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
        // .//GEN-BEGIN:userName_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        if(m_userName!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setUserName(m_userName);
        }
        return getCompositeKey()!=null ? getCompositeKey().getUserName() : null;
    }
    
    /** Use this method to update the property userName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setUserName(java.lang.String userName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (getUserName() == null ? userName == null : getUserName().equals(userName))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        userName = validateUserName(userName);
        // .//GEN-END:userName_be
        // Add custom code before setting the value//GEN-FIRST:userName


        // .//GEN-LAST:userName
        // .//GEN-BEGIN:userName_1_be
        super.update();
        super.addInitialValue(UserRoleMeta.USER_NAME, getUserName());
        getCompositeKey().setUserName(userName);
        m_userName = userName;
        m_userObject = null;
        // .//GEN-END:userName_1_be
        // Add custom code after setting the value//GEN-FIRST:userName_3


        // .//GEN-LAST:userName_3
        // .//GEN-BEGIN:userName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setUserName() method.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateUserName(java.lang.String userName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
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
        userName = FieldValidator.validate(userName, (StringFieldMetaData) UserRoleMeta.META_USER_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRoleMeta.getName(), UserRoleMeta.USER_NAME, userName, this.getUOW());

        // .//GEN-END:userName_3_be
        // Add custom code after a successful validation//GEN-FIRST:userName_2


        // .//GEN-LAST:userName_2
        // .//GEN-BEGIN:userName_4_be
        return userName;
    }
    // .//GEN-END:userName_4_be
    // .//GEN-BEGIN:roleName_be
    /** Getter for property roleName.
     * @return Value of property roleName.
     */
    public java.lang.String getRoleName() {
        if(m_roleName!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setRoleName(m_roleName);
        }
        return getCompositeKey()!=null ? getCompositeKey().getRoleName() : null;
    }
    
    /** Use this method to update the property roleName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param roleName New value of property roleName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRoleName(java.lang.String roleName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (getRoleName() == null ? roleName == null : getRoleName().equals(roleName))
            return;

        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
    
        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        roleName = validateRoleName(roleName);
        // .//GEN-END:roleName_be
        // Add custom code before setting the value//GEN-FIRST:roleName


        // .//GEN-LAST:roleName
        // .//GEN-BEGIN:roleName_1_be
        super.update();
        super.addInitialValue(UserRoleMeta.ROLE_NAME, getRoleName());
        getCompositeKey().setRoleName(roleName);
        m_roleName = roleName;
        // .//GEN-END:roleName_1_be
        // Add custom code after setting the value//GEN-FIRST:roleName_3


        // .//GEN-LAST:roleName_3
        // .//GEN-BEGIN:roleName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRoleName() method.
     * @param roleName New value of property roleName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRoleName(java.lang.String roleName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRoleName(roleName);
    }

    /** Use this method to validate a value for the property roleName.
     * @param roleName Value to be validated for the property roleName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateRoleName(java.lang.String roleName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:roleName_2_be
        // Add custom code before validation//GEN-FIRST:roleName_1


        // .//GEN-LAST:roleName_1
        // .//GEN-BEGIN:roleName_3_be
        roleName = FieldValidator.validate(roleName, (StringFieldMetaData) UserRoleMeta.META_ROLE_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserRoleMeta.getName(), UserRoleMeta.ROLE_NAME, roleName, this.getUOW());

        // .//GEN-END:roleName_3_be
        // Add custom code after a successful validation//GEN-FIRST:roleName_2


        // .//GEN-LAST:roleName_2
        // .//GEN-BEGIN:roleName_4_be
        return roleName;
    }
    // .//GEN-END:roleName_4_be
    // .//GEN-BEGIN:userObject_1_be
    /** Returns the related foreign User object.
     * The object is lazy-loaded.
     * @return the related foreign User object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public User getUserObject() throws ValidationException, FrameworkException  {
        findUserObject(false);
        return m_userObject;
    }
    
    /** Finds the related foreign User object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findUserObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_userObject == null && getUserName() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(UserMeta.getName());
                criteria.addCriteria(UserMeta.USER_NAME, getUserName());
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
                        m_userObject = (User) itr.next();
                }
                if (m_userObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(UserRoleMeta.META_USER_NAME.getLabelToken(), new Object[] {UserMeta.getLabelToken(), UserMeta.META_USER_NAME.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:userObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserRole>");
        buf.append("<userName>"); if (getUserName() != null) buf.append(getUserName()); buf.append("</userName>");
        buf.append("<roleName>"); if (getRoleName() != null) buf.append(getRoleName()); buf.append("</roleName>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</UserRole>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        UserRole obj = (UserRole) super.clone();
        obj.m_userObject = null;
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
            if (isModified(UserRoleMeta.USER_NAME))
                findUserObject(true);
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
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier userName
     * @supplierQualifier userName
     * @link association
     */
    /*#User lnkUser;*/

    // .//GEN-END:3_be

    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom

/**
 * Auto Generated Persistent class for the CompositeKey Composite Key.
 * @author  Auto-Generated
 */
    @Embeddable
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompositeKey implements Serializable{

        /** Holds value of property userName. */
        @XmlElement(name="userName")
        @Column(name="USER_NAME")
        private java.lang.String m_userName;

        /** Holds value of property roleName. */
        @XmlElement(name="roleName")
        @Column(name="ROLE_NAME")
        private java.lang.String m_roleName;
    
        /** Getter for property m_userName.
        * @return Value of property m_userName.
        */
        public java.lang.String getUserName() {
            return m_userName;
        }  

        /** Setter for property m_userName.
        * @sets Value of property m_userName.
        */
        public void setUserName(java.lang.String userName){
            m_userName = userName;
        }    
    
        /** Getter for property m_roleName.
        * @return Value of property m_roleName.
        */
        public java.lang.String getRoleName() {
            return m_roleName;
        }  

        /** Setter for property m_roleName.
        * @sets Value of property m_roleName.
        */
        public void setRoleName(java.lang.String roleName){
            m_roleName = roleName;
        }    
        /**
         * Compares this candidateKey with another candidateKey object.
         * Returns a true if both the objects have the same candidate key.
         *
         * @param obj the other Persistent object.
         * @return a true if both the objects have the same candidate key.
         */
        public boolean equals(Object obj) {
            if (obj != null && this.getClass() == obj.getClass()) {
                boolean equals = true;
                equals = equals && (getUserName() == null ? ((UserRole.CompositeKey) obj).getUserName() == null : getUserName().equals(((UserRole.CompositeKey) obj).getUserName()));
                equals = equals && (getRoleName() == null ? ((UserRole.CompositeKey) obj).getRoleName() == null : getRoleName().equals(((UserRole.CompositeKey) obj).getRoleName()));
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
            i += getUserName()!=null ? getUserName().hashCode() : 0;
            i += getRoleName()!=null ? getRoleName().hashCode() : 0;
            return i;
        } 
    }
}
