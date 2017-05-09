// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.domain;

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
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FORM_TEMPLATES table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FORM_TEMPLATES")
@SqlResultSetMapping(name="FormTemplate",entities={@EntityResult(entityClass=FormTemplate.class)})
public class FormTemplate extends Persistent {

    private static final Logger log = Logger.getLogger(FormTemplate.class);
    /** Holds value of property formId. */
    @XmlElement(name="formId")
    @Id
    @Column(name="FORM_ID")    
    private java.lang.Long m_formId;

    /** Holds value of property templateData. */
    @XmlElement(name="templateData")
    @Column(name="TEMPLATE_DATA")    
    private byte[] m_templateData;

    /** Holds value of property layoutData. */
    @XmlElement(name="layoutData")
    @Column(name="LAYOUT_DATA")    
    private byte[] m_layoutData;

    /** Holds related FormDefinition object. */
    private transient FormDefinition m_formDefinitionObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FormTemplate() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.Long formId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(formId);
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
    public static FormTemplate findByPK(UOW uow, java.lang.Long formId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(formId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FormTemplate) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.Long formId) {
        Criteria criteria = new Criteria();
        criteria.setTable(FormTemplateMeta.getName());
        criteria.addCriteria(FormTemplateMeta.FORM_ID, formId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:formId_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        return m_formId;
    }
    
    /** Use this method to update the property formId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formId New value of property formId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormId(java.lang.Long formId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formId == null ? formId == null : m_formId.equals(formId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        formId = validateFormId(formId);
        // .//GEN-END:formId_be
        // Add custom code before setting the value//GEN-FIRST:formId


        // .//GEN-LAST:formId
        // .//GEN-BEGIN:formId_1_be
        super.update();
        super.addInitialValue(FormTemplateMeta.FORM_ID, m_formId);
        m_formId = formId;
        m_formDefinitionObject = null;
        // .//GEN-END:formId_1_be
        // Add custom code after setting the value//GEN-FIRST:formId_3


        // .//GEN-LAST:formId_3
        // .//GEN-BEGIN:formId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormId() method.
     * @param formId New value of property formId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormId(java.lang.Long formId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormId(formId);
    }

    /** Use this method to validate a value for the property formId.
     * @param formId Value to be validated for the property formId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateFormId(java.lang.Long formId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formId_2_be
        // Add custom code before validation//GEN-FIRST:formId_1


        // .//GEN-LAST:formId_1
        // .//GEN-BEGIN:formId_3_be
        formId = FieldValidator.validate(formId, (IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormTemplateMeta.getName(), FormTemplateMeta.FORM_ID, formId, this.getUOW());

        // .//GEN-END:formId_3_be
        // Add custom code after a successful validation//GEN-FIRST:formId_2


        // .//GEN-LAST:formId_2
        // .//GEN-BEGIN:formId_4_be
        return formId;
    }
    // .//GEN-END:formId_4_be
    // .//GEN-BEGIN:templateData_be
    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public byte[] getTemplateData() {
        return m_templateData;
    }
    
    /** Use this method to update the property templateData.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param templateData New value of property templateData.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTemplateData(byte[] templateData)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_templateData == null ? templateData == null : m_templateData.equals(templateData))
            return;


        templateData = validateTemplateData(templateData);
        // .//GEN-END:templateData_be
        // Add custom code before setting the value//GEN-FIRST:templateData


        // .//GEN-LAST:templateData
        // .//GEN-BEGIN:templateData_1_be
        super.update();
        super.addInitialValue(FormTemplateMeta.TEMPLATE_DATA, m_templateData);
        m_templateData = templateData;
        // .//GEN-END:templateData_1_be
        // Add custom code after setting the value//GEN-FIRST:templateData_3


        // .//GEN-LAST:templateData_3
        // .//GEN-BEGIN:templateData_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setTemplateData() method.
     * @param templateData New value of property templateData.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTemplateData(byte[] templateData)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setTemplateData(templateData);
    }

    /** Use this method to validate a value for the property templateData.
     * @param templateData Value to be validated for the property templateData.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validateTemplateData(byte[] templateData)
    throws ValidationException, FrameworkException {
        // .//GEN-END:templateData_2_be
        // Add custom code before validation//GEN-FIRST:templateData_1


        // .//GEN-LAST:templateData_1
        // .//GEN-BEGIN:templateData_3_be
        templateData = FieldValidator.validate(templateData, (RawFieldMetaData) FormTemplateMeta.META_TEMPLATE_DATA, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormTemplateMeta.getName(), FormTemplateMeta.TEMPLATE_DATA, templateData, this.getUOW());

        // .//GEN-END:templateData_3_be
        // Add custom code after a successful validation//GEN-FIRST:templateData_2


        // .//GEN-LAST:templateData_2
        // .//GEN-BEGIN:templateData_4_be
        return templateData;
    }
    // .//GEN-END:templateData_4_be
    // .//GEN-BEGIN:layoutData_be
    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public byte[] getLayoutData() {
        return m_layoutData;
    }
    
    /** Use this method to update the property layoutData.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param layoutData New value of property layoutData.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLayoutData(byte[] layoutData)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_layoutData == null ? layoutData == null : m_layoutData.equals(layoutData))
            return;


        layoutData = validateLayoutData(layoutData);
        // .//GEN-END:layoutData_be
        // Add custom code before setting the value//GEN-FIRST:layoutData


        // .//GEN-LAST:layoutData
        // .//GEN-BEGIN:layoutData_1_be
        super.update();
        super.addInitialValue(FormTemplateMeta.LAYOUT_DATA, m_layoutData);
        m_layoutData = layoutData;
        // .//GEN-END:layoutData_1_be
        // Add custom code after setting the value//GEN-FIRST:layoutData_3


        // .//GEN-LAST:layoutData_3
        // .//GEN-BEGIN:layoutData_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLayoutData() method.
     * @param layoutData New value of property layoutData.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLayoutData(byte[] layoutData)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLayoutData(layoutData);
    }

    /** Use this method to validate a value for the property layoutData.
     * @param layoutData Value to be validated for the property layoutData.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public byte[] validateLayoutData(byte[] layoutData)
    throws ValidationException, FrameworkException {
        // .//GEN-END:layoutData_2_be
        // Add custom code before validation//GEN-FIRST:layoutData_1


        // .//GEN-LAST:layoutData_1
        // .//GEN-BEGIN:layoutData_3_be
        layoutData = FieldValidator.validate(layoutData, (RawFieldMetaData) FormTemplateMeta.META_LAYOUT_DATA, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormTemplateMeta.getName(), FormTemplateMeta.LAYOUT_DATA, layoutData, this.getUOW());

        // .//GEN-END:layoutData_3_be
        // Add custom code after a successful validation//GEN-FIRST:layoutData_2


        // .//GEN-LAST:layoutData_2
        // .//GEN-BEGIN:layoutData_4_be
        return layoutData;
    }
    // .//GEN-END:layoutData_4_be
    // .//GEN-BEGIN:formDefinitionObject_1_be
    /** Returns a related FormDefinition object.
     * @return a related FormDefinition object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public FormDefinition getFormDefinitionObject() throws ValidationException, FrameworkException {
        findFormDefinitionObject(false);
        return m_formDefinitionObject;
    }
    
    /** Finds the related FormDefinition object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findFormDefinitionObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_formDefinitionObject == null && getFormId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormDefinitionMeta.getName());
                criteria.addCriteria(FormDefinitionMeta.FORM_ID, getFormId());
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
                        m_formDefinitionObject = (FormDefinition) itr.next();
                }
                if (m_formDefinitionObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(FormTemplateMeta.META_FORM_ID.getLabelToken(), new Object[] {FormDefinitionMeta.getLabelToken(), FormDefinitionMeta.META_FORM_ID.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:formDefinitionObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormTemplate>");
        buf.append("<formId>"); if (m_formId != null) buf.append(m_formId); buf.append("</formId>");
        buf.append("<templateData>"); if (m_templateData != null) buf.append(m_templateData); buf.append("</templateData>");
        buf.append("<layoutData>"); if (m_layoutData != null) buf.append(m_layoutData); buf.append("</layoutData>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FormTemplate>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FormTemplate obj = (FormTemplate) super.clone();
        obj.m_formDefinitionObject = null;
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
            if (isModified(FormTemplateMeta.FORM_ID))
                findFormDefinitionObject(true);
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
     * @clientQualifier formId
     * @supplierQualifier formId
     * @link association
     */
    /*#FormDefinition lnkFormDefinition;*/

    // .//GEN-END:3_be

    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
