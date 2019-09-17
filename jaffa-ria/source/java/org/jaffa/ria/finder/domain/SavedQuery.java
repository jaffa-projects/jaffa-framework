// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.ria.finder.domain;

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
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.util.JAXBHelper;
import javax.xml.bind.JAXBException;
import java.nio.charset.Charset;
import net.sf.json.*;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.componentdomain.*;

// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_SAVED_QUERIES table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_SAVED_QUERIES")
@SqlResultSetMapping(name="SavedQuery",entities={@EntityResult(entityClass=SavedQuery.class)})
public class SavedQuery extends Persistent {

    private static final Logger log = Logger.getLogger(SavedQuery.class);
    /** Holds value of property queryId. */
    @XmlElement(name="queryId")
    @Id
    @Column(name="QUERY_ID")    
    private java.lang.String m_queryId;

    /** Holds value of property userId. */
    @XmlElement(name="userId")
    @Column(name="USER_ID")    
    private java.lang.String m_userId;

    /** Holds value of property componentRef. */
    @XmlElement(name="componentRef")
    @Column(name="COMPONENT_REF")    
    private java.lang.String m_componentRef;

    /** Holds value of property contextRef. */
    @XmlElement(name="contextRef")
    @Column(name="CONTEXT_REF")    
    private java.lang.String m_contextRef;

    /** Holds value of property queryName. */
    @XmlElement(name="queryName")
    @Column(name="QUERY_NAME")    
    private java.lang.String m_queryName;

    /** Holds value of property isDefault. */
    @XmlElement(name="isDefault")
    @Convert(converter = BooleanTFConverter.class)
    @Column(name="IS_DEFAULT")    
    private java.lang.Boolean m_isDefault;

    /** Holds value of property criteria. */
    @XmlElement(name="criteria")
    @Column(name="CRITERIA")    
    private java.lang.String m_criteria;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public SavedQuery() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String queryId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(queryId);
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
    public static SavedQuery findByPK(UOW uow, java.lang.String queryId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(queryId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (SavedQuery) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String queryId) {
        Criteria criteria = new Criteria();
        criteria.setTable(SavedQueryMeta.getName());
        criteria.addCriteria(SavedQueryMeta.QUERY_ID, queryId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:queryId_be
    /** Getter for property queryId.
     * @return Value of property queryId.
     */
    public java.lang.String getQueryId() {
        return m_queryId;
    }
    
    /** Use this method to update the property queryId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param queryId New value of property queryId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setQueryId(java.lang.String queryId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_queryId == null ? queryId == null : m_queryId.equals(queryId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        queryId = validateQueryId(queryId);
        // .//GEN-END:queryId_be
        // Add custom code before setting the value//GEN-FIRST:queryId


        // .//GEN-LAST:queryId
        // .//GEN-BEGIN:queryId_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.QUERY_ID, m_queryId);
        m_queryId = queryId;
        // .//GEN-END:queryId_1_be
        // Add custom code after setting the value//GEN-FIRST:queryId_3


        // .//GEN-LAST:queryId_3
        // .//GEN-BEGIN:queryId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setQueryId() method.
     * @param queryId New value of property queryId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateQueryId(java.lang.String queryId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setQueryId(queryId);
    }

    /** Use this method to validate a value for the property queryId.
     * @param queryId Value to be validated for the property queryId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateQueryId(java.lang.String queryId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:queryId_2_be
        // Add custom code before validation//GEN-FIRST:queryId_1


        // .//GEN-LAST:queryId_1
        // .//GEN-BEGIN:queryId_3_be
        queryId = FieldValidator.validate(queryId, (StringFieldMetaData) SavedQueryMeta.META_QUERY_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.QUERY_ID, queryId, this.getUOW());

        // .//GEN-END:queryId_3_be
        // Add custom code after a successful validation//GEN-FIRST:queryId_2


        // .//GEN-LAST:queryId_2
        // .//GEN-BEGIN:queryId_4_be
        return queryId;
    }
    // .//GEN-END:queryId_4_be
    // .//GEN-BEGIN:userId_be
    /** Getter for property userId.
     * @return Value of property userId.
     */
    public java.lang.String getUserId() {
        return m_userId;
    }
    
    /** Use this method to update the property userId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param userId New value of property userId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setUserId(java.lang.String userId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_userId == null ? userId == null : m_userId.equals(userId))
            return;


        userId = validateUserId(userId);
        // .//GEN-END:userId_be
        // Add custom code before setting the value//GEN-FIRST:userId


        // .//GEN-LAST:userId
        // .//GEN-BEGIN:userId_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.USER_ID, m_userId);
        m_userId = userId;
        // .//GEN-END:userId_1_be
        // Add custom code after setting the value//GEN-FIRST:userId_3


        // .//GEN-LAST:userId_3
        // .//GEN-BEGIN:userId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setUserId() method.
     * @param userId New value of property userId.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateUserId(java.lang.String userId)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setUserId(userId);
    }

    /** Use this method to validate a value for the property userId.
     * @param userId Value to be validated for the property userId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateUserId(java.lang.String userId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:userId_2_be
        // Add custom code before validation//GEN-FIRST:userId_1


        // .//GEN-LAST:userId_1
        // .//GEN-BEGIN:userId_3_be
        userId = FieldValidator.validate(userId, (StringFieldMetaData) SavedQueryMeta.META_USER_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.USER_ID, userId, this.getUOW());

        // .//GEN-END:userId_3_be
        // Add custom code after a successful validation//GEN-FIRST:userId_2


        // .//GEN-LAST:userId_2
        // .//GEN-BEGIN:userId_4_be
        return userId;
    }
    // .//GEN-END:userId_4_be
    // .//GEN-BEGIN:componentRef_be
    /** Getter for property componentRef.
     * @return Value of property componentRef.
     */
    public java.lang.String getComponentRef() {
        return m_componentRef;
    }
    
    /** Use this method to update the property componentRef.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param componentRef New value of property componentRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setComponentRef(java.lang.String componentRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_componentRef == null ? componentRef == null : m_componentRef.equals(componentRef))
            return;


        componentRef = validateComponentRef(componentRef);
        // .//GEN-END:componentRef_be
        // Add custom code before setting the value//GEN-FIRST:componentRef


        // .//GEN-LAST:componentRef
        // .//GEN-BEGIN:componentRef_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.COMPONENT_REF, m_componentRef);
        m_componentRef = componentRef;
        // .//GEN-END:componentRef_1_be
        // Add custom code after setting the value//GEN-FIRST:componentRef_3


        // .//GEN-LAST:componentRef_3
        // .//GEN-BEGIN:componentRef_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setComponentRef() method.
     * @param componentRef New value of property componentRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateComponentRef(java.lang.String componentRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setComponentRef(componentRef);
    }

    /** Use this method to validate a value for the property componentRef.
     * @param componentRef Value to be validated for the property componentRef.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateComponentRef(java.lang.String componentRef)
    throws ValidationException, FrameworkException {
        // .//GEN-END:componentRef_2_be
        // Add custom code before validation//GEN-FIRST:componentRef_1


        // .//GEN-LAST:componentRef_1
        // .//GEN-BEGIN:componentRef_3_be
        componentRef = FieldValidator.validate(componentRef, (StringFieldMetaData) SavedQueryMeta.META_COMPONENT_REF, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.COMPONENT_REF, componentRef, this.getUOW());

        // .//GEN-END:componentRef_3_be
        // Add custom code after a successful validation//GEN-FIRST:componentRef_2


        // .//GEN-LAST:componentRef_2
        // .//GEN-BEGIN:componentRef_4_be
        return componentRef;
    }
    // .//GEN-END:componentRef_4_be
    // .//GEN-BEGIN:contextRef_be
    /** Getter for property contextRef.
     * @return Value of property contextRef.
     */
    public java.lang.String getContextRef() {
        return m_contextRef;
    }
    
    /** Use this method to update the property contextRef.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param contextRef New value of property contextRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setContextRef(java.lang.String contextRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_contextRef == null ? contextRef == null : m_contextRef.equals(contextRef))
            return;


        contextRef = validateContextRef(contextRef);
        // .//GEN-END:contextRef_be
        // Add custom code before setting the value//GEN-FIRST:contextRef


        // .//GEN-LAST:contextRef
        // .//GEN-BEGIN:contextRef_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.CONTEXT_REF, m_contextRef);
        m_contextRef = contextRef;
        // .//GEN-END:contextRef_1_be
        // Add custom code after setting the value//GEN-FIRST:contextRef_3


        // .//GEN-LAST:contextRef_3
        // .//GEN-BEGIN:contextRef_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setContextRef() method.
     * @param contextRef New value of property contextRef.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateContextRef(java.lang.String contextRef)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setContextRef(contextRef);
    }

    /** Use this method to validate a value for the property contextRef.
     * @param contextRef Value to be validated for the property contextRef.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateContextRef(java.lang.String contextRef)
    throws ValidationException, FrameworkException {
        // .//GEN-END:contextRef_2_be
        // Add custom code before validation//GEN-FIRST:contextRef_1


        // .//GEN-LAST:contextRef_1
        // .//GEN-BEGIN:contextRef_3_be
        contextRef = FieldValidator.validate(contextRef, (StringFieldMetaData) SavedQueryMeta.META_CONTEXT_REF, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.CONTEXT_REF, contextRef, this.getUOW());

        // .//GEN-END:contextRef_3_be
        // Add custom code after a successful validation//GEN-FIRST:contextRef_2


        // .//GEN-LAST:contextRef_2
        // .//GEN-BEGIN:contextRef_4_be
        return contextRef;
    }
    // .//GEN-END:contextRef_4_be
    // .//GEN-BEGIN:queryName_be
    /** Getter for property queryName.
     * @return Value of property queryName.
     */
    public java.lang.String getQueryName() {
        return m_queryName;
    }
    
    /** Use this method to update the property queryName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param queryName New value of property queryName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setQueryName(java.lang.String queryName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_queryName == null ? queryName == null : m_queryName.equals(queryName))
            return;


        queryName = validateQueryName(queryName);
        // .//GEN-END:queryName_be
        // Add custom code before setting the value//GEN-FIRST:queryName


        // .//GEN-LAST:queryName
        // .//GEN-BEGIN:queryName_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.QUERY_NAME, m_queryName);
        m_queryName = queryName;
        // .//GEN-END:queryName_1_be
        // Add custom code after setting the value//GEN-FIRST:queryName_3


        // .//GEN-LAST:queryName_3
        // .//GEN-BEGIN:queryName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setQueryName() method.
     * @param queryName New value of property queryName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateQueryName(java.lang.String queryName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setQueryName(queryName);
    }

    /** Use this method to validate a value for the property queryName.
     * @param queryName Value to be validated for the property queryName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateQueryName(java.lang.String queryName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:queryName_2_be
        // Add custom code before validation//GEN-FIRST:queryName_1


        // .//GEN-LAST:queryName_1
        // .//GEN-BEGIN:queryName_3_be
        queryName = FieldValidator.validate(queryName, (StringFieldMetaData) SavedQueryMeta.META_QUERY_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.QUERY_NAME, queryName, this.getUOW());

        // .//GEN-END:queryName_3_be
        // Add custom code after a successful validation//GEN-FIRST:queryName_2


        // .//GEN-LAST:queryName_2
        // .//GEN-BEGIN:queryName_4_be
        return queryName;
    }
    // .//GEN-END:queryName_4_be
    // .//GEN-BEGIN:isDefault_be
    /** Getter for property isDefault.
     * @return Value of property isDefault.
     */
    public java.lang.Boolean getIsDefault() {
        return m_isDefault;
    }
    
    /** Use this method to update the property isDefault.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param isDefault New value of property isDefault.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setIsDefault(java.lang.Boolean isDefault)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_isDefault == null ? isDefault == null : m_isDefault.equals(isDefault))
            return;


        isDefault = validateIsDefault(isDefault);
        // .//GEN-END:isDefault_be
        // Add custom code before setting the value//GEN-FIRST:isDefault


        // .//GEN-LAST:isDefault
        // .//GEN-BEGIN:isDefault_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.IS_DEFAULT, m_isDefault);
        m_isDefault = isDefault;
        // .//GEN-END:isDefault_1_be
        // Add custom code after setting the value//GEN-FIRST:isDefault_3


        // .//GEN-LAST:isDefault_3
        // .//GEN-BEGIN:isDefault_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setIsDefault() method.
     * @param isDefault New value of property isDefault.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateIsDefault(java.lang.Boolean isDefault)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setIsDefault(isDefault);
    }

    /** Use this method to validate a value for the property isDefault.
     * @param isDefault Value to be validated for the property isDefault.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateIsDefault(java.lang.Boolean isDefault)
    throws ValidationException, FrameworkException {
        // .//GEN-END:isDefault_2_be
        // Add custom code before validation//GEN-FIRST:isDefault_1


        // .//GEN-LAST:isDefault_1
        // .//GEN-BEGIN:isDefault_3_be
        isDefault = FieldValidator.validate(isDefault, (BooleanFieldMetaData) SavedQueryMeta.META_IS_DEFAULT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.IS_DEFAULT, isDefault, this.getUOW());

        // .//GEN-END:isDefault_3_be
        // Add custom code after a successful validation//GEN-FIRST:isDefault_2


        // .//GEN-LAST:isDefault_2
        // .//GEN-BEGIN:isDefault_4_be
        return isDefault;
    }
    // .//GEN-END:isDefault_4_be
    // .//GEN-BEGIN:criteria_be
    /** Getter for property criteria.
     * @return Value of property criteria.
     */
    public java.lang.String getCriteria() {
        return m_criteria;
    }
    
    /** Use this method to update the property criteria.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param criteria New value of property criteria.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCriteria(java.lang.String criteria)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_criteria == null ? criteria == null : m_criteria.equals(criteria))
            return;


        criteria = validateCriteria(criteria);
        // .//GEN-END:criteria_be
        // Add custom code before setting the value//GEN-FIRST:criteria


        // .//GEN-LAST:criteria
        // .//GEN-BEGIN:criteria_1_be
        super.update();
        super.addInitialValue(SavedQueryMeta.CRITERIA, m_criteria);
        m_criteria = criteria;
        // .//GEN-END:criteria_1_be
        // Add custom code after setting the value//GEN-FIRST:criteria_3


        // .//GEN-LAST:criteria_3
        // .//GEN-BEGIN:criteria_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCriteria() method.
     * @param criteria New value of property criteria.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCriteria(java.lang.String criteria)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCriteria(criteria);
    }

    /** Use this method to validate a value for the property criteria.
     * @param criteria Value to be validated for the property criteria.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCriteria(java.lang.String criteria)
    throws ValidationException, FrameworkException {
        // .//GEN-END:criteria_2_be
        // Add custom code before validation//GEN-FIRST:criteria_1


        // .//GEN-LAST:criteria_1
        // .//GEN-BEGIN:criteria_3_be
        criteria = FieldValidator.validate(criteria, (StringFieldMetaData) SavedQueryMeta.META_CRITERIA, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(SavedQueryMeta.getName(), SavedQueryMeta.CRITERIA, criteria, this.getUOW());

        // .//GEN-END:criteria_3_be
        // Add custom code after a successful validation//GEN-FIRST:criteria_2


        // .//GEN-LAST:criteria_2
        // .//GEN-BEGIN:criteria_4_be
        return criteria;
    }
    // .//GEN-END:criteria_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<SavedQuery>");
        buf.append("<queryId>"); if (m_queryId != null) buf.append(m_queryId); buf.append("</queryId>");
        buf.append("<userId>"); if (m_userId != null) buf.append(m_userId); buf.append("</userId>");
        buf.append("<componentRef>"); if (m_componentRef != null) buf.append(m_componentRef); buf.append("</componentRef>");
        buf.append("<contextRef>"); if (m_contextRef != null) buf.append(m_contextRef); buf.append("</contextRef>");
        buf.append("<queryName>"); if (m_queryName != null) buf.append(m_queryName); buf.append("</queryName>");
        buf.append("<isDefault>"); if (m_isDefault != null) buf.append(m_isDefault); buf.append("</isDefault>");
        buf.append("<criteria>"); if (m_criteria != null) buf.append(m_criteria); buf.append("</criteria>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</SavedQuery>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        SavedQuery obj = (SavedQuery) super.clone();
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

    private static final String PROPERTY_USER_ID = "user.id";

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        generateKey();
        super.validate();
    }

    /** Generate the technical-key, if required. */
    public void generateKey() throws ApplicationExceptions, FrameworkException {
        if (getQueryId() == null) {
            try {
                IVoucherGenerator vg = VoucherGeneratorFactory.instance();
                vg.setUow(getUOW());
                vg.setDomainClassName(getClass().getName());
                vg.setFieldName(SavedQueryMeta.QUERY_ID);
                vg.setLabelToken(SavedQueryMeta.META_QUERY_ID.getLabelToken());
                setQueryId(vg.generate());
            } catch (ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        }
    }

    /** Return all saved queries in a JSON string*/
    public static String getSavedQueries(String componentRef, String contextRef) throws FrameworkException{
        UOW uow = null;
        try {
            StringBuffer json = new StringBuffer();
            uow = new UOW();
            SavedQuery savedQuery = null;

            Criteria criteria = new Criteria();
            criteria.setTable(SavedQueryMeta.getName());
            String userId = (String) ContextManagerFactory.instance().getProperty(PROPERTY_USER_ID);
            criteria.addCriteria(SavedQueryMeta.USER_ID, userId);
            criteria.addOrderBy(SavedQueryMeta.QUERY_NAME, Criteria.ORDER_BY_ASC);
            if (componentRef!=null)
                criteria.addCriteria(SavedQueryMeta.COMPONENT_REF, componentRef);
            if (contextRef!=null && !contextRef.equals(""))
                criteria.addCriteria(SavedQueryMeta.CONTEXT_REF, contextRef);
            Iterator it = uow.query(criteria).iterator();
            json.append("[");
            boolean isFirst = true;
            while(it.hasNext()){
                String jsonCriteria;
                savedQuery = (SavedQuery)it.next();
                if (isFirst) {
                    isFirst = false;
                } else {
                    json.append(",");
                }

                //get criteria here and deserialize it!
                ComponentDefinition component = Loader.getComponentPool().get(componentRef);
                List<Object> params = component.getParameters();
                String criteriaClass = "";


                if (params!=null){
                    for (final Object param : params) {
                        if (param != null && param instanceof ObjectTypeParam) {
                            if (((ObjectTypeParam) param).getClassName() != null) {
                                String name = ((ObjectTypeParam) param).getName();
                                String type = ((ObjectTypeParam) param).getClassName();
                                if (name.equals("criteria")) criteriaClass = type;
                            }
                        }
                    }
                }

                if (criteriaClass!=null&&!criteriaClass.equals("")){
                    //final Class<?> clazz = Class.forName(criteriaClass);

                    Object payload = null;
                    if (savedQuery.getCriteria() != null) {
                        try {
                            payload = JAXBHelper.unmarshalPayload(savedQuery.getCriteria(), criteriaClass);
                        } catch (Exception e) {
                            String s = "Error in unmarshalling criteria to a " + criteriaClass + " object via JAXB";
                            log.error(s, e);
                            throw new RuntimeException(s, e);
                        }
                    }
                    final JSONObject jsonObject = JSONObject.fromObject(payload);
                    jsonCriteria = jsonObject.toString();
                } else {
                    jsonCriteria = savedQuery.getCriteria();
                }
                // should build string like - ['queryName',{'criteria':{field1:value1,isDefault:true}]
                json.append("['").append(savedQuery.getQueryName()).append("', {'criteria':").append(jsonCriteria).append(", 'isDefault':").append(savedQuery.getIsDefault()?"true":"false").append("}]");
            }
            json.append("]");
            return json.toString();

        }catch(Exception e){
          e.printStackTrace();
        }finally{
            if(uow!=null)
                uow.rollback();
        }
        return null;
    }

    /** Saves a query*/
    public static void saveQuery(String componentRef, String contextRef, String queryName, String queryData, Boolean isDefault) throws FrameworkException{
        UOW uow = null;
        try {
            uow = new UOW();
            String userId = (String) ContextManagerFactory.instance().getProperty(PROPERTY_USER_ID);
            Criteria criteria = new Criteria();
            criteria.setTable(SavedQueryMeta.getName());
            criteria.addCriteria(SavedQueryMeta.COMPONENT_REF, componentRef);
            if (contextRef!=null && !contextRef.equals(""))
                criteria.addCriteria(SavedQueryMeta.CONTEXT_REF, contextRef);
            criteria.addCriteria(SavedQueryMeta.QUERY_NAME, queryName);
            criteria.addCriteria(SavedQueryMeta.USER_ID, userId);
            Collection col = uow.query(criteria);
            // Check is record exists, if it does update it otherwise create it
            if (col.isEmpty()) {
                SavedQuery savedQuery = new SavedQuery();
                savedQuery.setUserId(userId);
                savedQuery.setComponentRef(componentRef);
                savedQuery.setContextRef(contextRef);
                savedQuery.setQueryName(queryName);
                savedQuery.setIsDefault(isDefault);

                String criteriaClass = getCriteriaClass(componentRef);
                if (criteriaClass!=null&&!criteriaClass.equals("")){
                    savedQuery.setCriteria(marshallObject(queryData, criteriaClass));
                } else {
                    savedQuery.setCriteria(queryData);
                }
                uow.add(savedQuery);
            } else {
                SavedQuery savedQuery = (SavedQuery) col.iterator().next();
                String criteriaClass = getCriteriaClass(componentRef);
                if (criteriaClass!=null&&!criteriaClass.equals("")){
                    savedQuery.setCriteria(marshallObject(queryData, criteriaClass));
                } else {
                    savedQuery.setCriteria(queryData);
                }
                savedQuery.setIsDefault(isDefault);
                uow.update(savedQuery);
            }

            uow.commit();

        } catch (Exception ex) {
            log.warn("Error: "+ex.toString());
        }finally{
             if(uow!=null)
                 uow.rollback();
        }

    }

    private static String getCriteriaClass (String componentRef){
        ComponentDefinition component = Loader.getComponentPool().get(componentRef);
        List<Object> params = component.getParameters();

        if (params != null) {
            for (final Object param : params) {
                if (param instanceof ObjectTypeParam) {
                    if (((ObjectTypeParam) param).getClassName() != null) {
                        String name = ((ObjectTypeParam) param).getName();
                        String type = ((ObjectTypeParam) param).getClassName();
                        if (name.equals("criteria"))
                            return type;
                    }
                }
            }
        }
        return null;
    }

    private static String marshallObject (String queryData, String criteriaClass) throws Exception{
        try {
            final Class<?> clazz = Class.forName(criteriaClass);
            final JSONObject json = JSONObject.fromObject(queryData);
            final JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(clazz);
            Object object = JSONSerializer.toJava(json, jsonConfig);
            String xml = null;

            if (object != null) {
                try {
                    xml = JAXBHelper.marshalPayload(object);
                } catch (JAXBException e) {
                    String s = "Error in marshalling '" + object + "' to XML via JAXB";
                    log.error(s, e);
                    throw new RuntimeException(s, e);
                }
            }
            return xml;
        } catch (Exception ex) {
            log.warn("Error: "+ex.toString());
            return null;
        }
    }

    /** Deletes a query (String componentRef, String contextRef, string queryName) */
    public static void deleteQuery(String componentRef, String contextRef, String queryName) throws FrameworkException{
        UOW uow = null;
        try {
            uow = new UOW();
            String userId = (String) ContextManagerFactory.instance().getProperty(PROPERTY_USER_ID);
            Criteria criteria = new Criteria();
            criteria.setTable(SavedQueryMeta.getName());
            criteria.addCriteria(SavedQueryMeta.COMPONENT_REF, componentRef);
            if (contextRef!=null && !contextRef.equals(""))
                criteria.addCriteria(SavedQueryMeta.CONTEXT_REF, contextRef);
            criteria.addCriteria(SavedQueryMeta.QUERY_NAME, queryName);
            criteria.addCriteria(SavedQueryMeta.USER_ID, userId);
            Collection col = uow.query(criteria);
            // Check is record exists, if it does update it otherwise create it
            if (!col.isEmpty()) {
                SavedQuery savedQuery = (SavedQuery) col.iterator().next();
                uow.delete(savedQuery);
            }

            uow.commit();

        } catch (Exception ex) {
            log.warn("Error: "+ex.toString());
        }finally{
            if(uow!=null)
                uow.rollback();
        }
    }
    // .//GEN-LAST:custom
}
