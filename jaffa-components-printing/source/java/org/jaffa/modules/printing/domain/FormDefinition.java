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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormTemplateMeta;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;
import org.jaffa.modules.printing.domain.PrinterOutputType;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.exceptions.ApplicationException;



// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_FORM_DEFINITIONS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_FORM_DEFINITIONS")
@SqlResultSetMapping(name="FormDefinition",entities={@EntityResult(entityClass=FormDefinition.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class FormDefinition extends Persistent {

    private static final Logger log = Logger.getLogger(FormDefinition.class);
    /** Holds value of property formId. */
    @XmlElement(name="formId")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="S_J_FORM_DEFINITIONS_1")
    @SequenceGenerator(name="S_J_FORM_DEFINITIONS_1", sequenceName="S_J_FORM_DEFINITIONS_1")
    @Column(name="FORM_ID")    
    private java.lang.Long m_formId;

    /** Holds value of property formName. */
    @XmlElement(name="formName")
    @Column(name="FORM_NAME")    
    private java.lang.String m_formName;

    /** Holds value of property formAlternate. */
    @XmlElement(name="formAlternate")
    @Column(name="FORM_ALTERNATE")    
    private java.lang.String m_formAlternate;

    /** Holds value of property formVariation. */
    @XmlElement(name="formVariation")
    @Column(name="FORM_VARIATION")    
    private java.lang.String m_formVariation;

    /** Holds value of property outputType. */
    @XmlElement(name="outputType")
    @Column(name="OUTPUT_TYPE")    
    private java.lang.String m_outputType;

    /** Holds value of property formTemplate. */
    @XmlElement(name="formTemplate")
    @Column(name="FORM_TEMPLATE")    
    private java.lang.String m_formTemplate;

    /** Holds value of property fieldLayout. */
    @XmlElement(name="fieldLayout")
    @Column(name="FIELD_LAYOUT")    
    private java.lang.String m_fieldLayout;

    /** Holds value of property description. */
    @XmlElement(name="description")
    @Column(name="DESCRIPTION")    
    private java.lang.String m_description;

    /** Holds value of property remarks. */
    @XmlElement(name="remarks")
    @Column(name="REMARKS")    
    private java.lang.String m_remarks;

    /** Holds value of property domFactory. */
    @XmlElement(name="domFactory")
    @Column(name="DOM_FACTORY")    
    private java.lang.String m_domFactory;

    /** Holds value of property domClass. */
    @XmlElement(name="domClass")
    @Column(name="DOM_CLASS")    
    private java.lang.String m_domClass;

    /** Holds value of property domKey1. */
    @XmlElement(name="domKey1")
    @Column(name="DOM_KEY1")    
    private java.lang.String m_domKey1;

    /** Holds value of property domKey2. */
    @XmlElement(name="domKey2")
    @Column(name="DOM_KEY2")    
    private java.lang.String m_domKey2;

    /** Holds value of property domKey3. */
    @XmlElement(name="domKey3")
    @Column(name="DOM_KEY3")    
    private java.lang.String m_domKey3;

    /** Holds value of property domKey4. */
    @XmlElement(name="domKey4")
    @Column(name="DOM_KEY4")    
    private java.lang.String m_domKey4;

    /** Holds value of property domKey5. */
    @XmlElement(name="domKey5")
    @Column(name="DOM_KEY5")    
    private java.lang.String m_domKey5;

    /** Holds value of property domKey6. */
    @XmlElement(name="domKey6")
    @Column(name="DOM_KEY6")    
    private java.lang.String m_domKey6;

    /** Holds value of property additionalDataComponent. */
    @XmlElement(name="additionalDataComponent")
    @Column(name="ADDITIONAL_DATA_COMPONENT")    
    private java.lang.String m_additionalDataComponent;

    /** Holds value of property followOnFormName. */
    @XmlElement(name="followOnFormName")
    @Column(name="FOLLOW_ON_FORM_NAME")    
    private java.lang.String m_followOnFormName;

    /** Holds value of property followOnFormAlternate. */
    @XmlElement(name="followOnFormAlternate")
    @Column(name="FOLLOW_ON_FORM_ALTERNATE")    
    private java.lang.String m_followOnFormAlternate;

    /** Holds value of property createdOn. */
    @XmlElement(name="createdOn")
    @Type(type="dateTimeType")
    @Column(name="CREATED_ON")    
    private org.jaffa.datatypes.DateTime m_createdOn;

    /** Holds value of property createdBy. */
    @XmlElement(name="createdBy")
    @Column(name="CREATED_BY")    
    private java.lang.String m_createdBy;

    /** Holds value of property lastChangedOn. */
    @XmlElement(name="lastChangedOn")
    @Type(type="dateTimeType")
    @Column(name="LAST_CHANGED_ON")    
    private org.jaffa.datatypes.DateTime m_lastChangedOn;

    /** Holds value of property lastChangedBy. */
    @XmlElement(name="lastChangedBy")
    @Column(name="LAST_CHANGED_BY")    
    private java.lang.String m_lastChangedBy;

    /** Holds related FormTemplate object. */
    private transient FormTemplate m_formTemplateObject;

    /** Holds related foreign FormGroup object. */
    private transient FormGroup m_formGroupObject;

    /** Holds related foreign PrinterOutputType object. */
    private transient PrinterOutputType m_printerOutputTypeObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FormDefinition() {
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
    public static FormDefinition findByPK(UOW uow, java.lang.Long formId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(formId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FormDefinition) itr.next();
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
        criteria.setTable(FormDefinitionMeta.getName());
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, formId);
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
        super.addInitialValue(FormDefinitionMeta.FORM_ID, m_formId);
        m_formId = formId;
        m_formTemplateObject = null;
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
        formId = FieldValidator.validate(formId, (IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FORM_ID, formId, this.getUOW());

        // .//GEN-END:formId_3_be
        // Add custom code after a successful validation//GEN-FIRST:formId_2


        // .//GEN-LAST:formId_2
        // .//GEN-BEGIN:formId_4_be
        return formId;
    }
    // .//GEN-END:formId_4_be
    // .//GEN-BEGIN:formName_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return m_formName;
    }
    
    /** Use this method to update the property formName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formName New value of property formName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormName(java.lang.String formName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formName == null ? formName == null : m_formName.equals(formName))
            return;


        formName = validateFormName(formName);
        // .//GEN-END:formName_be
        // Add custom code before setting the value//GEN-FIRST:formName


        // .//GEN-LAST:formName
        // .//GEN-BEGIN:formName_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FORM_NAME, m_formName);
        m_formName = formName;
        m_formGroupObject = null;
        // .//GEN-END:formName_1_be
        // Add custom code after setting the value//GEN-FIRST:formName_3


        // .//GEN-LAST:formName_3
        // .//GEN-BEGIN:formName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormName() method.
     * @param formName New value of property formName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormName(java.lang.String formName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormName(formName);
    }

    /** Use this method to validate a value for the property formName.
     * @param formName Value to be validated for the property formName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormName(java.lang.String formName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formName_2_be
        // Add custom code before validation//GEN-FIRST:formName_1


        // .//GEN-LAST:formName_1
        // .//GEN-BEGIN:formName_3_be
        formName = FieldValidator.validate(formName, (StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FORM_NAME, formName, this.getUOW());

        // .//GEN-END:formName_3_be
        // Add custom code after a successful validation//GEN-FIRST:formName_2


        // .//GEN-LAST:formName_2
        // .//GEN-BEGIN:formName_4_be
        return formName;
    }
    // .//GEN-END:formName_4_be
    // .//GEN-BEGIN:formAlternate_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return m_formAlternate;
    }
    
    /** Use this method to update the property formAlternate.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formAlternate New value of property formAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormAlternate(java.lang.String formAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formAlternate == null ? formAlternate == null : m_formAlternate.equals(formAlternate))
            return;


        formAlternate = validateFormAlternate(formAlternate);
        // .//GEN-END:formAlternate_be
        // Add custom code before setting the value//GEN-FIRST:formAlternate


        // .//GEN-LAST:formAlternate
        // .//GEN-BEGIN:formAlternate_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FORM_ALTERNATE, m_formAlternate);
        m_formAlternate = formAlternate;
        // .//GEN-END:formAlternate_1_be
        // Add custom code after setting the value//GEN-FIRST:formAlternate_3


        // .//GEN-LAST:formAlternate_3
        // .//GEN-BEGIN:formAlternate_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormAlternate() method.
     * @param formAlternate New value of property formAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormAlternate(java.lang.String formAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormAlternate(formAlternate);
    }

    /** Use this method to validate a value for the property formAlternate.
     * @param formAlternate Value to be validated for the property formAlternate.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormAlternate(java.lang.String formAlternate)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formAlternate_2_be
        // Add custom code before validation//GEN-FIRST:formAlternate_1


        // .//GEN-LAST:formAlternate_1
        // .//GEN-BEGIN:formAlternate_3_be
        formAlternate = FieldValidator.validate(formAlternate, (StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FORM_ALTERNATE, formAlternate, this.getUOW());

        // .//GEN-END:formAlternate_3_be
        // Add custom code after a successful validation//GEN-FIRST:formAlternate_2


        // .//GEN-LAST:formAlternate_2
        // .//GEN-BEGIN:formAlternate_4_be
        return formAlternate;
    }
    // .//GEN-END:formAlternate_4_be
    // .//GEN-BEGIN:formVariation_be
    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public java.lang.String getFormVariation() {
        return m_formVariation;
    }
    
    /** Use this method to update the property formVariation.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formVariation New value of property formVariation.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormVariation(java.lang.String formVariation)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formVariation == null ? formVariation == null : m_formVariation.equals(formVariation))
            return;


        formVariation = validateFormVariation(formVariation);
        // .//GEN-END:formVariation_be
        // Add custom code before setting the value//GEN-FIRST:formVariation


        // .//GEN-LAST:formVariation
        // .//GEN-BEGIN:formVariation_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FORM_VARIATION, m_formVariation);
        m_formVariation = formVariation;
        // .//GEN-END:formVariation_1_be
        // Add custom code after setting the value//GEN-FIRST:formVariation_3


        // .//GEN-LAST:formVariation_3
        // .//GEN-BEGIN:formVariation_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormVariation() method.
     * @param formVariation New value of property formVariation.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormVariation(java.lang.String formVariation)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormVariation(formVariation);
    }

    /** Use this method to validate a value for the property formVariation.
     * @param formVariation Value to be validated for the property formVariation.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormVariation(java.lang.String formVariation)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formVariation_2_be
        // Add custom code before validation//GEN-FIRST:formVariation_1


        // .//GEN-LAST:formVariation_1
        // .//GEN-BEGIN:formVariation_3_be
        formVariation = FieldValidator.validate(formVariation, (StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FORM_VARIATION, formVariation, this.getUOW());

        // .//GEN-END:formVariation_3_be
        // Add custom code after a successful validation//GEN-FIRST:formVariation_2


        // .//GEN-LAST:formVariation_2
        // .//GEN-BEGIN:formVariation_4_be
        return formVariation;
    }
    // .//GEN-END:formVariation_4_be
    // .//GEN-BEGIN:outputType_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return m_outputType;
    }
    
    /** Use this method to update the property outputType.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param outputType New value of property outputType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setOutputType(java.lang.String outputType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_outputType == null ? outputType == null : m_outputType.equals(outputType))
            return;


        outputType = validateOutputType(outputType);
        // .//GEN-END:outputType_be
        // Add custom code before setting the value//GEN-FIRST:outputType


        // .//GEN-LAST:outputType
        // .//GEN-BEGIN:outputType_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.OUTPUT_TYPE, m_outputType);
        m_outputType = outputType;
        m_printerOutputTypeObject = null;
        // .//GEN-END:outputType_1_be
        // Add custom code after setting the value//GEN-FIRST:outputType_3


        // .//GEN-LAST:outputType_3
        // .//GEN-BEGIN:outputType_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setOutputType() method.
     * @param outputType New value of property outputType.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateOutputType(java.lang.String outputType)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setOutputType(outputType);
    }

    /** Use this method to validate a value for the property outputType.
     * @param outputType Value to be validated for the property outputType.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateOutputType(java.lang.String outputType)
    throws ValidationException, FrameworkException {
        // .//GEN-END:outputType_2_be
        // Add custom code before validation//GEN-FIRST:outputType_1


        // .//GEN-LAST:outputType_1
        // .//GEN-BEGIN:outputType_3_be
        outputType = FieldValidator.validate(outputType, (StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.OUTPUT_TYPE, outputType, this.getUOW());

        // .//GEN-END:outputType_3_be
        // Add custom code after a successful validation//GEN-FIRST:outputType_2


        // .//GEN-LAST:outputType_2
        // .//GEN-BEGIN:outputType_4_be
        return outputType;
    }
    // .//GEN-END:outputType_4_be
    // .//GEN-BEGIN:formTemplate_be
    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public java.lang.String getFormTemplate() {
        return m_formTemplate;
    }
    
    /** Use this method to update the property formTemplate.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param formTemplate New value of property formTemplate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFormTemplate(java.lang.String formTemplate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_formTemplate == null ? formTemplate == null : m_formTemplate.equals(formTemplate))
            return;


        formTemplate = validateFormTemplate(formTemplate);
        // .//GEN-END:formTemplate_be
        // Add custom code before setting the value//GEN-FIRST:formTemplate


        // .//GEN-LAST:formTemplate
        // .//GEN-BEGIN:formTemplate_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FORM_TEMPLATE, m_formTemplate);
        m_formTemplate = formTemplate;
        // .//GEN-END:formTemplate_1_be
        // Add custom code after setting the value//GEN-FIRST:formTemplate_3


        // .//GEN-LAST:formTemplate_3
        // .//GEN-BEGIN:formTemplate_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFormTemplate() method.
     * @param formTemplate New value of property formTemplate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFormTemplate(java.lang.String formTemplate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFormTemplate(formTemplate);
    }

    /** Use this method to validate a value for the property formTemplate.
     * @param formTemplate Value to be validated for the property formTemplate.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFormTemplate(java.lang.String formTemplate)
    throws ValidationException, FrameworkException {
        // .//GEN-END:formTemplate_2_be
        // Add custom code before validation//GEN-FIRST:formTemplate_1


        // .//GEN-LAST:formTemplate_1
        // .//GEN-BEGIN:formTemplate_3_be
        formTemplate = FieldValidator.validate(formTemplate, (StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FORM_TEMPLATE, formTemplate, this.getUOW());

        // .//GEN-END:formTemplate_3_be
        // Add custom code after a successful validation//GEN-FIRST:formTemplate_2


        // .//GEN-LAST:formTemplate_2
        // .//GEN-BEGIN:formTemplate_4_be
        return formTemplate;
    }
    // .//GEN-END:formTemplate_4_be
    // .//GEN-BEGIN:fieldLayout_be
    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public java.lang.String getFieldLayout() {
        return m_fieldLayout;
    }
    
    /** Use this method to update the property fieldLayout.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param fieldLayout New value of property fieldLayout.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFieldLayout(java.lang.String fieldLayout)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_fieldLayout == null ? fieldLayout == null : m_fieldLayout.equals(fieldLayout))
            return;


        fieldLayout = validateFieldLayout(fieldLayout);
        // .//GEN-END:fieldLayout_be
        // Add custom code before setting the value//GEN-FIRST:fieldLayout


        // .//GEN-LAST:fieldLayout
        // .//GEN-BEGIN:fieldLayout_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FIELD_LAYOUT, m_fieldLayout);
        m_fieldLayout = fieldLayout;
        // .//GEN-END:fieldLayout_1_be
        // Add custom code after setting the value//GEN-FIRST:fieldLayout_3


        // .//GEN-LAST:fieldLayout_3
        // .//GEN-BEGIN:fieldLayout_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFieldLayout() method.
     * @param fieldLayout New value of property fieldLayout.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFieldLayout(java.lang.String fieldLayout)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFieldLayout(fieldLayout);
    }

    /** Use this method to validate a value for the property fieldLayout.
     * @param fieldLayout Value to be validated for the property fieldLayout.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFieldLayout(java.lang.String fieldLayout)
    throws ValidationException, FrameworkException {
        // .//GEN-END:fieldLayout_2_be
        // Add custom code before validation//GEN-FIRST:fieldLayout_1


        // .//GEN-LAST:fieldLayout_1
        // .//GEN-BEGIN:fieldLayout_3_be
        fieldLayout = FieldValidator.validate(fieldLayout, (StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FIELD_LAYOUT, fieldLayout, this.getUOW());

        // .//GEN-END:fieldLayout_3_be
        // Add custom code after a successful validation//GEN-FIRST:fieldLayout_2


        // .//GEN-LAST:fieldLayout_2
        // .//GEN-BEGIN:fieldLayout_4_be
        return fieldLayout;
    }
    // .//GEN-END:fieldLayout_4_be
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
        super.addInitialValue(FormDefinitionMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DESCRIPTION, description, this.getUOW());

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
        super.addInitialValue(FormDefinitionMeta.REMARKS, m_remarks);
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
        remarks = FieldValidator.validate(remarks, (StringFieldMetaData) FormDefinitionMeta.META_REMARKS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.REMARKS, remarks, this.getUOW());

        // .//GEN-END:remarks_3_be
        // Add custom code after a successful validation//GEN-FIRST:remarks_2


        // .//GEN-LAST:remarks_2
        // .//GEN-BEGIN:remarks_4_be
        return remarks;
    }
    // .//GEN-END:remarks_4_be
    // .//GEN-BEGIN:domFactory_be
    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public java.lang.String getDomFactory() {
        return m_domFactory;
    }
    
    /** Use this method to update the property domFactory.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domFactory New value of property domFactory.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomFactory(java.lang.String domFactory)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domFactory == null ? domFactory == null : m_domFactory.equals(domFactory))
            return;


        domFactory = validateDomFactory(domFactory);
        // .//GEN-END:domFactory_be
        // Add custom code before setting the value//GEN-FIRST:domFactory


        // .//GEN-LAST:domFactory
        // .//GEN-BEGIN:domFactory_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_FACTORY, m_domFactory);
        m_domFactory = domFactory;
        // .//GEN-END:domFactory_1_be
        // Add custom code after setting the value//GEN-FIRST:domFactory_3


        // .//GEN-LAST:domFactory_3
        // .//GEN-BEGIN:domFactory_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomFactory() method.
     * @param domFactory New value of property domFactory.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomFactory(java.lang.String domFactory)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomFactory(domFactory);
    }

    /** Use this method to validate a value for the property domFactory.
     * @param domFactory Value to be validated for the property domFactory.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomFactory(java.lang.String domFactory)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domFactory_2_be
        // Add custom code before validation//GEN-FIRST:domFactory_1


        // .//GEN-LAST:domFactory_1
        // .//GEN-BEGIN:domFactory_3_be
        domFactory = FieldValidator.validate(domFactory, (StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_FACTORY, domFactory, this.getUOW());

        // .//GEN-END:domFactory_3_be
        // Add custom code after a successful validation//GEN-FIRST:domFactory_2


        // .//GEN-LAST:domFactory_2
        // .//GEN-BEGIN:domFactory_4_be
        return domFactory;
    }
    // .//GEN-END:domFactory_4_be
    // .//GEN-BEGIN:domClass_be
    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public java.lang.String getDomClass() {
        return m_domClass;
    }
    
    /** Use this method to update the property domClass.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domClass New value of property domClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomClass(java.lang.String domClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domClass == null ? domClass == null : m_domClass.equals(domClass))
            return;


        domClass = validateDomClass(domClass);
        // .//GEN-END:domClass_be
        // Add custom code before setting the value//GEN-FIRST:domClass


        // .//GEN-LAST:domClass
        // .//GEN-BEGIN:domClass_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_CLASS, m_domClass);
        m_domClass = domClass;
        // .//GEN-END:domClass_1_be
        // Add custom code after setting the value//GEN-FIRST:domClass_3


        // .//GEN-LAST:domClass_3
        // .//GEN-BEGIN:domClass_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomClass() method.
     * @param domClass New value of property domClass.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomClass(java.lang.String domClass)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomClass(domClass);
    }

    /** Use this method to validate a value for the property domClass.
     * @param domClass Value to be validated for the property domClass.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomClass(java.lang.String domClass)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domClass_2_be
        // Add custom code before validation//GEN-FIRST:domClass_1


        // .//GEN-LAST:domClass_1
        // .//GEN-BEGIN:domClass_3_be
        domClass = FieldValidator.validate(domClass, (StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_CLASS, domClass, this.getUOW());

        // .//GEN-END:domClass_3_be
        // Add custom code after a successful validation//GEN-FIRST:domClass_2


        // .//GEN-LAST:domClass_2
        // .//GEN-BEGIN:domClass_4_be
        return domClass;
    }
    // .//GEN-END:domClass_4_be
    // .//GEN-BEGIN:domKey1_be
    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public java.lang.String getDomKey1() {
        return m_domKey1;
    }
    
    /** Use this method to update the property domKey1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey1 New value of property domKey1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey1(java.lang.String domKey1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey1 == null ? domKey1 == null : m_domKey1.equals(domKey1))
            return;


        domKey1 = validateDomKey1(domKey1);
        // .//GEN-END:domKey1_be
        // Add custom code before setting the value//GEN-FIRST:domKey1


        // .//GEN-LAST:domKey1
        // .//GEN-BEGIN:domKey1_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY1, m_domKey1);
        m_domKey1 = domKey1;
        // .//GEN-END:domKey1_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey1_3


        // .//GEN-LAST:domKey1_3
        // .//GEN-BEGIN:domKey1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey1() method.
     * @param domKey1 New value of property domKey1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey1(java.lang.String domKey1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey1(domKey1);
    }

    /** Use this method to validate a value for the property domKey1.
     * @param domKey1 Value to be validated for the property domKey1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey1(java.lang.String domKey1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey1_2_be
        // Add custom code before validation//GEN-FIRST:domKey1_1


        // .//GEN-LAST:domKey1_1
        // .//GEN-BEGIN:domKey1_3_be
        domKey1 = FieldValidator.validate(domKey1, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY1, domKey1, this.getUOW());

        // .//GEN-END:domKey1_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey1_2


        // .//GEN-LAST:domKey1_2
        // .//GEN-BEGIN:domKey1_4_be
        return domKey1;
    }
    // .//GEN-END:domKey1_4_be
    // .//GEN-BEGIN:domKey2_be
    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public java.lang.String getDomKey2() {
        return m_domKey2;
    }
    
    /** Use this method to update the property domKey2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey2 New value of property domKey2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey2(java.lang.String domKey2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey2 == null ? domKey2 == null : m_domKey2.equals(domKey2))
            return;


        domKey2 = validateDomKey2(domKey2);
        // .//GEN-END:domKey2_be
        // Add custom code before setting the value//GEN-FIRST:domKey2


        // .//GEN-LAST:domKey2
        // .//GEN-BEGIN:domKey2_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY2, m_domKey2);
        m_domKey2 = domKey2;
        // .//GEN-END:domKey2_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey2_3


        // .//GEN-LAST:domKey2_3
        // .//GEN-BEGIN:domKey2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey2() method.
     * @param domKey2 New value of property domKey2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey2(java.lang.String domKey2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey2(domKey2);
    }

    /** Use this method to validate a value for the property domKey2.
     * @param domKey2 Value to be validated for the property domKey2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey2(java.lang.String domKey2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey2_2_be
        // Add custom code before validation//GEN-FIRST:domKey2_1


        // .//GEN-LAST:domKey2_1
        // .//GEN-BEGIN:domKey2_3_be
        domKey2 = FieldValidator.validate(domKey2, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY2, domKey2, this.getUOW());

        // .//GEN-END:domKey2_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey2_2


        // .//GEN-LAST:domKey2_2
        // .//GEN-BEGIN:domKey2_4_be
        return domKey2;
    }
    // .//GEN-END:domKey2_4_be
    // .//GEN-BEGIN:domKey3_be
    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public java.lang.String getDomKey3() {
        return m_domKey3;
    }
    
    /** Use this method to update the property domKey3.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey3 New value of property domKey3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey3(java.lang.String domKey3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey3 == null ? domKey3 == null : m_domKey3.equals(domKey3))
            return;


        domKey3 = validateDomKey3(domKey3);
        // .//GEN-END:domKey3_be
        // Add custom code before setting the value//GEN-FIRST:domKey3


        // .//GEN-LAST:domKey3
        // .//GEN-BEGIN:domKey3_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY3, m_domKey3);
        m_domKey3 = domKey3;
        // .//GEN-END:domKey3_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey3_3


        // .//GEN-LAST:domKey3_3
        // .//GEN-BEGIN:domKey3_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey3() method.
     * @param domKey3 New value of property domKey3.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey3(java.lang.String domKey3)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey3(domKey3);
    }

    /** Use this method to validate a value for the property domKey3.
     * @param domKey3 Value to be validated for the property domKey3.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey3(java.lang.String domKey3)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey3_2_be
        // Add custom code before validation//GEN-FIRST:domKey3_1


        // .//GEN-LAST:domKey3_1
        // .//GEN-BEGIN:domKey3_3_be
        domKey3 = FieldValidator.validate(domKey3, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY3, domKey3, this.getUOW());

        // .//GEN-END:domKey3_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey3_2


        // .//GEN-LAST:domKey3_2
        // .//GEN-BEGIN:domKey3_4_be
        return domKey3;
    }
    // .//GEN-END:domKey3_4_be
    // .//GEN-BEGIN:domKey4_be
    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public java.lang.String getDomKey4() {
        return m_domKey4;
    }
    
    /** Use this method to update the property domKey4.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey4 New value of property domKey4.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey4(java.lang.String domKey4)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey4 == null ? domKey4 == null : m_domKey4.equals(domKey4))
            return;


        domKey4 = validateDomKey4(domKey4);
        // .//GEN-END:domKey4_be
        // Add custom code before setting the value//GEN-FIRST:domKey4


        // .//GEN-LAST:domKey4
        // .//GEN-BEGIN:domKey4_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY4, m_domKey4);
        m_domKey4 = domKey4;
        // .//GEN-END:domKey4_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey4_3


        // .//GEN-LAST:domKey4_3
        // .//GEN-BEGIN:domKey4_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey4() method.
     * @param domKey4 New value of property domKey4.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey4(java.lang.String domKey4)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey4(domKey4);
    }

    /** Use this method to validate a value for the property domKey4.
     * @param domKey4 Value to be validated for the property domKey4.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey4(java.lang.String domKey4)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey4_2_be
        // Add custom code before validation//GEN-FIRST:domKey4_1


        // .//GEN-LAST:domKey4_1
        // .//GEN-BEGIN:domKey4_3_be
        domKey4 = FieldValidator.validate(domKey4, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY4, domKey4, this.getUOW());

        // .//GEN-END:domKey4_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey4_2


        // .//GEN-LAST:domKey4_2
        // .//GEN-BEGIN:domKey4_4_be
        return domKey4;
    }
    // .//GEN-END:domKey4_4_be
    // .//GEN-BEGIN:domKey5_be
    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public java.lang.String getDomKey5() {
        return m_domKey5;
    }
    
    /** Use this method to update the property domKey5.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey5 New value of property domKey5.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey5(java.lang.String domKey5)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey5 == null ? domKey5 == null : m_domKey5.equals(domKey5))
            return;


        domKey5 = validateDomKey5(domKey5);
        // .//GEN-END:domKey5_be
        // Add custom code before setting the value//GEN-FIRST:domKey5


        // .//GEN-LAST:domKey5
        // .//GEN-BEGIN:domKey5_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY5, m_domKey5);
        m_domKey5 = domKey5;
        // .//GEN-END:domKey5_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey5_3


        // .//GEN-LAST:domKey5_3
        // .//GEN-BEGIN:domKey5_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey5() method.
     * @param domKey5 New value of property domKey5.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey5(java.lang.String domKey5)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey5(domKey5);
    }

    /** Use this method to validate a value for the property domKey5.
     * @param domKey5 Value to be validated for the property domKey5.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey5(java.lang.String domKey5)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey5_2_be
        // Add custom code before validation//GEN-FIRST:domKey5_1


        // .//GEN-LAST:domKey5_1
        // .//GEN-BEGIN:domKey5_3_be
        domKey5 = FieldValidator.validate(domKey5, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY5, domKey5, this.getUOW());

        // .//GEN-END:domKey5_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey5_2


        // .//GEN-LAST:domKey5_2
        // .//GEN-BEGIN:domKey5_4_be
        return domKey5;
    }
    // .//GEN-END:domKey5_4_be
    // .//GEN-BEGIN:domKey6_be
    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public java.lang.String getDomKey6() {
        return m_domKey6;
    }
    
    /** Use this method to update the property domKey6.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param domKey6 New value of property domKey6.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDomKey6(java.lang.String domKey6)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_domKey6 == null ? domKey6 == null : m_domKey6.equals(domKey6))
            return;


        domKey6 = validateDomKey6(domKey6);
        // .//GEN-END:domKey6_be
        // Add custom code before setting the value//GEN-FIRST:domKey6


        // .//GEN-LAST:domKey6
        // .//GEN-BEGIN:domKey6_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.DOM_KEY6, m_domKey6);
        m_domKey6 = domKey6;
        // .//GEN-END:domKey6_1_be
        // Add custom code after setting the value//GEN-FIRST:domKey6_3


        // .//GEN-LAST:domKey6_3
        // .//GEN-BEGIN:domKey6_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDomKey6() method.
     * @param domKey6 New value of property domKey6.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDomKey6(java.lang.String domKey6)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDomKey6(domKey6);
    }

    /** Use this method to validate a value for the property domKey6.
     * @param domKey6 Value to be validated for the property domKey6.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDomKey6(java.lang.String domKey6)
    throws ValidationException, FrameworkException {
        // .//GEN-END:domKey6_2_be
        // Add custom code before validation//GEN-FIRST:domKey6_1


        // .//GEN-LAST:domKey6_1
        // .//GEN-BEGIN:domKey6_3_be
        domKey6 = FieldValidator.validate(domKey6, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.DOM_KEY6, domKey6, this.getUOW());

        // .//GEN-END:domKey6_3_be
        // Add custom code after a successful validation//GEN-FIRST:domKey6_2


        // .//GEN-LAST:domKey6_2
        // .//GEN-BEGIN:domKey6_4_be
        return domKey6;
    }
    // .//GEN-END:domKey6_4_be
    // .//GEN-BEGIN:additionalDataComponent_be
    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public java.lang.String getAdditionalDataComponent() {
        return m_additionalDataComponent;
    }
    
    /** Use this method to update the property additionalDataComponent.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param additionalDataComponent New value of property additionalDataComponent.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setAdditionalDataComponent(java.lang.String additionalDataComponent)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_additionalDataComponent == null ? additionalDataComponent == null : m_additionalDataComponent.equals(additionalDataComponent))
            return;


        additionalDataComponent = validateAdditionalDataComponent(additionalDataComponent);
        // .//GEN-END:additionalDataComponent_be
        // Add custom code before setting the value//GEN-FIRST:additionalDataComponent


        // .//GEN-LAST:additionalDataComponent
        // .//GEN-BEGIN:additionalDataComponent_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.ADDITIONAL_DATA_COMPONENT, m_additionalDataComponent);
        m_additionalDataComponent = additionalDataComponent;
        // .//GEN-END:additionalDataComponent_1_be
        // Add custom code after setting the value//GEN-FIRST:additionalDataComponent_3


        // .//GEN-LAST:additionalDataComponent_3
        // .//GEN-BEGIN:additionalDataComponent_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setAdditionalDataComponent() method.
     * @param additionalDataComponent New value of property additionalDataComponent.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateAdditionalDataComponent(java.lang.String additionalDataComponent)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setAdditionalDataComponent(additionalDataComponent);
    }

    /** Use this method to validate a value for the property additionalDataComponent.
     * @param additionalDataComponent Value to be validated for the property additionalDataComponent.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateAdditionalDataComponent(java.lang.String additionalDataComponent)
    throws ValidationException, FrameworkException {
        // .//GEN-END:additionalDataComponent_2_be
        // Add custom code before validation//GEN-FIRST:additionalDataComponent_1


        // .//GEN-LAST:additionalDataComponent_1
        // .//GEN-BEGIN:additionalDataComponent_3_be
        additionalDataComponent = FieldValidator.validate(additionalDataComponent, (StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.ADDITIONAL_DATA_COMPONENT, additionalDataComponent, this.getUOW());

        // .//GEN-END:additionalDataComponent_3_be
        // Add custom code after a successful validation//GEN-FIRST:additionalDataComponent_2


        // .//GEN-LAST:additionalDataComponent_2
        // .//GEN-BEGIN:additionalDataComponent_4_be
        return additionalDataComponent;
    }
    // .//GEN-END:additionalDataComponent_4_be
    // .//GEN-BEGIN:followOnFormName_be
    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public java.lang.String getFollowOnFormName() {
        return m_followOnFormName;
    }
    
    /** Use this method to update the property followOnFormName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param followOnFormName New value of property followOnFormName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFollowOnFormName(java.lang.String followOnFormName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_followOnFormName == null ? followOnFormName == null : m_followOnFormName.equals(followOnFormName))
            return;


        followOnFormName = validateFollowOnFormName(followOnFormName);
        // .//GEN-END:followOnFormName_be
        // Add custom code before setting the value//GEN-FIRST:followOnFormName


        // .//GEN-LAST:followOnFormName
        // .//GEN-BEGIN:followOnFormName_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FOLLOW_ON_FORM_NAME, m_followOnFormName);
        m_followOnFormName = followOnFormName;
        // .//GEN-END:followOnFormName_1_be
        // Add custom code after setting the value//GEN-FIRST:followOnFormName_3


        // .//GEN-LAST:followOnFormName_3
        // .//GEN-BEGIN:followOnFormName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFollowOnFormName() method.
     * @param followOnFormName New value of property followOnFormName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFollowOnFormName(java.lang.String followOnFormName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFollowOnFormName(followOnFormName);
    }

    /** Use this method to validate a value for the property followOnFormName.
     * @param followOnFormName Value to be validated for the property followOnFormName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFollowOnFormName(java.lang.String followOnFormName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:followOnFormName_2_be
        // Add custom code before validation//GEN-FIRST:followOnFormName_1


        // .//GEN-LAST:followOnFormName_1
        // .//GEN-BEGIN:followOnFormName_3_be
        followOnFormName = FieldValidator.validate(followOnFormName, (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FOLLOW_ON_FORM_NAME, followOnFormName, this.getUOW());

        // .//GEN-END:followOnFormName_3_be
        // Add custom code after a successful validation//GEN-FIRST:followOnFormName_2


        // .//GEN-LAST:followOnFormName_2
        // .//GEN-BEGIN:followOnFormName_4_be
        return followOnFormName;
    }
    // .//GEN-END:followOnFormName_4_be
    // .//GEN-BEGIN:followOnFormAlternate_be
    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public java.lang.String getFollowOnFormAlternate() {
        return m_followOnFormAlternate;
    }
    
    /** Use this method to update the property followOnFormAlternate.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_followOnFormAlternate == null ? followOnFormAlternate == null : m_followOnFormAlternate.equals(followOnFormAlternate))
            return;


        followOnFormAlternate = validateFollowOnFormAlternate(followOnFormAlternate);
        // .//GEN-END:followOnFormAlternate_be
        // Add custom code before setting the value//GEN-FIRST:followOnFormAlternate


        // .//GEN-LAST:followOnFormAlternate
        // .//GEN-BEGIN:followOnFormAlternate_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.FOLLOW_ON_FORM_ALTERNATE, m_followOnFormAlternate);
        m_followOnFormAlternate = followOnFormAlternate;
        // .//GEN-END:followOnFormAlternate_1_be
        // Add custom code after setting the value//GEN-FIRST:followOnFormAlternate_3


        // .//GEN-LAST:followOnFormAlternate_3
        // .//GEN-BEGIN:followOnFormAlternate_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setFollowOnFormAlternate() method.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateFollowOnFormAlternate(java.lang.String followOnFormAlternate)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setFollowOnFormAlternate(followOnFormAlternate);
    }

    /** Use this method to validate a value for the property followOnFormAlternate.
     * @param followOnFormAlternate Value to be validated for the property followOnFormAlternate.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateFollowOnFormAlternate(java.lang.String followOnFormAlternate)
    throws ValidationException, FrameworkException {
        // .//GEN-END:followOnFormAlternate_2_be
        // Add custom code before validation//GEN-FIRST:followOnFormAlternate_1


        // .//GEN-LAST:followOnFormAlternate_1
        // .//GEN-BEGIN:followOnFormAlternate_3_be
        followOnFormAlternate = FieldValidator.validate(followOnFormAlternate, (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.FOLLOW_ON_FORM_ALTERNATE, followOnFormAlternate, this.getUOW());

        // .//GEN-END:followOnFormAlternate_3_be
        // Add custom code after a successful validation//GEN-FIRST:followOnFormAlternate_2


        // .//GEN-LAST:followOnFormAlternate_2
        // .//GEN-BEGIN:followOnFormAlternate_4_be
        return followOnFormAlternate;
    }
    // .//GEN-END:followOnFormAlternate_4_be
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
        super.addInitialValue(FormDefinitionMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) FormDefinitionMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.CREATED_ON, createdOn, this.getUOW());

        // .//GEN-END:createdOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdOn_2


        // .//GEN-LAST:createdOn_2
        // .//GEN-BEGIN:createdOn_4_be
        return createdOn;
    }
    // .//GEN-END:createdOn_4_be
    // .//GEN-BEGIN:createdBy_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return m_createdBy;
    }
    
    /** Use this method to update the property createdBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param createdBy New value of property createdBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCreatedBy(java.lang.String createdBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_createdBy == null ? createdBy == null : m_createdBy.equals(createdBy))
            return;


        createdBy = validateCreatedBy(createdBy);
        // .//GEN-END:createdBy_be
        // Add custom code before setting the value//GEN-FIRST:createdBy


        // .//GEN-LAST:createdBy
        // .//GEN-BEGIN:createdBy_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.CREATED_BY, m_createdBy);
        m_createdBy = createdBy;
        // .//GEN-END:createdBy_1_be
        // Add custom code after setting the value//GEN-FIRST:createdBy_3


        // .//GEN-LAST:createdBy_3
        // .//GEN-BEGIN:createdBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCreatedBy() method.
     * @param createdBy New value of property createdBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCreatedBy(java.lang.String createdBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCreatedBy(createdBy);
    }

    /** Use this method to validate a value for the property createdBy.
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) FormDefinitionMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.CREATED_BY, createdBy, this.getUOW());

        // .//GEN-END:createdBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:createdBy_2


        // .//GEN-LAST:createdBy_2
        // .//GEN-BEGIN:createdBy_4_be
        return createdBy;
    }
    // .//GEN-END:createdBy_4_be
    // .//GEN-BEGIN:lastChangedOn_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return m_lastChangedOn;
    }
    
    /** Use this method to update the property lastChangedOn.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param lastChangedOn New value of property lastChangedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_lastChangedOn == null ? lastChangedOn == null : m_lastChangedOn.equals(lastChangedOn))
            return;


        lastChangedOn = validateLastChangedOn(lastChangedOn);
        // .//GEN-END:lastChangedOn_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedOn


        // .//GEN-LAST:lastChangedOn
        // .//GEN-BEGIN:lastChangedOn_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.LAST_CHANGED_ON, m_lastChangedOn);
        m_lastChangedOn = lastChangedOn;
        // .//GEN-END:lastChangedOn_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedOn_3


        // .//GEN-LAST:lastChangedOn_3
        // .//GEN-BEGIN:lastChangedOn_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLastChangedOn() method.
     * @param lastChangedOn New value of property lastChangedOn.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLastChangedOn(lastChangedOn);
    }

    /** Use this method to validate a value for the property lastChangedOn.
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
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

        // .//GEN-END:lastChangedOn_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedOn_2


        // .//GEN-LAST:lastChangedOn_2
        // .//GEN-BEGIN:lastChangedOn_4_be
        return lastChangedOn;
    }
    // .//GEN-END:lastChangedOn_4_be
    // .//GEN-BEGIN:lastChangedBy_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return m_lastChangedBy;
    }
    
    /** Use this method to update the property lastChangedBy.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param lastChangedBy New value of property lastChangedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_lastChangedBy == null ? lastChangedBy == null : m_lastChangedBy.equals(lastChangedBy))
            return;


        lastChangedBy = validateLastChangedBy(lastChangedBy);
        // .//GEN-END:lastChangedBy_be
        // Add custom code before setting the value//GEN-FIRST:lastChangedBy


        // .//GEN-LAST:lastChangedBy
        // .//GEN-BEGIN:lastChangedBy_1_be
        super.update();
        super.addInitialValue(FormDefinitionMeta.LAST_CHANGED_BY, m_lastChangedBy);
        m_lastChangedBy = lastChangedBy;
        // .//GEN-END:lastChangedBy_1_be
        // Add custom code after setting the value//GEN-FIRST:lastChangedBy_3


        // .//GEN-LAST:lastChangedBy_3
        // .//GEN-BEGIN:lastChangedBy_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLastChangedBy() method.
     * @param lastChangedBy New value of property lastChangedBy.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLastChangedBy(java.lang.String lastChangedBy)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLastChangedBy(lastChangedBy);
    }

    /** Use this method to validate a value for the property lastChangedBy.
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
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(FormDefinitionMeta.getName(), FormDefinitionMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
    // .//GEN-BEGIN:formTemplateObject_1_be
    /** Returns a related FormTemplate object.
     * @return a related FormTemplate object.
     * @throws FrameworkException Indicates some system error
     */
    public FormTemplate getFormTemplateObject() throws FrameworkException {
        findFormTemplateObject(false);
        return m_formTemplateObject;
    }
    
    /** Finds the related FormTemplate object.
     * If checkExistenceOnly is false, then the related object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the related object, as oppposed to fetching all the values for that object.
     */
    private void findFormTemplateObject(boolean checkExistenceOnly) throws FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_formTemplateObject == null && getFormId() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormTemplateMeta.getName());
                criteria.addCriteria(FormTemplateMeta.FORM_ID, getFormId());
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
                        m_formTemplateObject = (FormTemplate) itr.next();
                }
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    /** Creates a new FormTemplate object and initializes the related fields.
     * @throws ValidationException if an invalid value is passed.
     * @throws FrameworkException Indicates some system error
     * @return the related FormTemplate object with the initialized related fields.
     */
    public FormTemplate newFormTemplateObject()
    throws ValidationException, FrameworkException {
        FormTemplate formTemplate = new FormTemplate();
        formTemplate.setFormId(getFormId());
        // .//GEN-END:formTemplateObject_1_be
        // Add custom code//GEN-FIRST:formTemplateObject_1


        // .//GEN-LAST:formTemplateObject_1
        // .//GEN-BEGIN:formTemplateObject_2_be
        return formTemplate;
    }
    // .//GEN-END:formTemplateObject_2_be
    // .//GEN-BEGIN:formGroupObject_1_be
    /** Returns the related foreign FormGroup object.
     * The object is lazy-loaded.
     * @return the related foreign FormGroup object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public FormGroup getFormGroupObject() throws ValidationException, FrameworkException  {
        findFormGroupObject(false);
        return m_formGroupObject;
    }
    
    /** Finds the related foreign FormGroup object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findFormGroupObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_formGroupObject == null && getFormName() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormGroupMeta.getName());
                criteria.addCriteria(FormGroupMeta.FORM_NAME, getFormName());
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
                        m_formGroupObject = (FormGroup) itr.next();
                }
                if (m_formGroupObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(FormDefinitionMeta.META_FORM_NAME.getLabelToken(), new Object[] {FormGroupMeta.getLabelToken(), FormGroupMeta.META_FORM_NAME.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:formGroupObject_1_be
    // .//GEN-BEGIN:printerOutputTypeObject_1_be
    /** Returns the related foreign PrinterOutputType object.
     * The object is lazy-loaded.
     * @return the related foreign PrinterOutputType object.
     * @throws ValidationException if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public PrinterOutputType getPrinterOutputTypeObject() throws ValidationException, FrameworkException  {
        findPrinterOutputTypeObject(false);
        return m_printerOutputTypeObject;
    }
    
    /** Finds the related foreign PrinterOutputType object.
     * If checkExistenceOnly is false, then the foreign object will be fetched and assigned to the corresponding member variable of this class.
     * If checkExistenceOnly is true, then a mere existence check is performed for the foreign object, as oppposed to fetching all the values for that object.
     */
    private void findPrinterOutputTypeObject(boolean checkExistenceOnly) throws ValidationException, FrameworkException {
        UOW uow = getUOW();
        boolean localUow = false;
        try {
            if (m_printerOutputTypeObject == null && getOutputType() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(PrinterOutputTypeMeta.getName());
                criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, getOutputType());
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
                        m_printerOutputTypeObject = (PrinterOutputType) itr.next();
                }
                if (m_printerOutputTypeObject == null && (count == null || count.intValue() <= 0))
                    throw new InvalidForeignKeyException(FormDefinitionMeta.META_OUTPUT_TYPE.getLabelToken(), new Object[] {PrinterOutputTypeMeta.getLabelToken(), PrinterOutputTypeMeta.META_OUTPUT_TYPE.getLabelToken()});
            }
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:printerOutputTypeObject_1_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormDefinition>");
        buf.append("<formId>"); if (m_formId != null) buf.append(m_formId); buf.append("</formId>");
        buf.append("<formName>"); if (m_formName != null) buf.append(m_formName); buf.append("</formName>");
        buf.append("<formAlternate>"); if (m_formAlternate != null) buf.append(m_formAlternate); buf.append("</formAlternate>");
        buf.append("<formVariation>"); if (m_formVariation != null) buf.append(m_formVariation); buf.append("</formVariation>");
        buf.append("<outputType>"); if (m_outputType != null) buf.append(m_outputType); buf.append("</outputType>");
        buf.append("<formTemplate>"); if (m_formTemplate != null) buf.append(m_formTemplate); buf.append("</formTemplate>");
        buf.append("<fieldLayout>"); if (m_fieldLayout != null) buf.append(m_fieldLayout); buf.append("</fieldLayout>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<remarks>"); if (m_remarks != null) buf.append(m_remarks); buf.append("</remarks>");
        buf.append("<domFactory>"); if (m_domFactory != null) buf.append(m_domFactory); buf.append("</domFactory>");
        buf.append("<domClass>"); if (m_domClass != null) buf.append(m_domClass); buf.append("</domClass>");
        buf.append("<domKey1>"); if (m_domKey1 != null) buf.append(m_domKey1); buf.append("</domKey1>");
        buf.append("<domKey2>"); if (m_domKey2 != null) buf.append(m_domKey2); buf.append("</domKey2>");
        buf.append("<domKey3>"); if (m_domKey3 != null) buf.append(m_domKey3); buf.append("</domKey3>");
        buf.append("<domKey4>"); if (m_domKey4 != null) buf.append(m_domKey4); buf.append("</domKey4>");
        buf.append("<domKey5>"); if (m_domKey5 != null) buf.append(m_domKey5); buf.append("</domKey5>");
        buf.append("<domKey6>"); if (m_domKey6 != null) buf.append(m_domKey6); buf.append("</domKey6>");
        buf.append("<additionalDataComponent>"); if (m_additionalDataComponent != null) buf.append(m_additionalDataComponent); buf.append("</additionalDataComponent>");
        buf.append("<followOnFormName>"); if (m_followOnFormName != null) buf.append(m_followOnFormName); buf.append("</followOnFormName>");
        buf.append("<followOnFormAlternate>"); if (m_followOnFormAlternate != null) buf.append(m_followOnFormAlternate); buf.append("</followOnFormAlternate>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</FormDefinition>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        FormDefinition obj = (FormDefinition) super.clone();
        obj.m_formTemplateObject = null;
        obj.m_formGroupObject = null;
        obj.m_printerOutputTypeObject = null;
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
            if (isModified(FormDefinitionMeta.FORM_NAME))
                findFormGroupObject(true);
        } catch (ValidationException e) {
            appExps.add(e);
        }
        try {
            if (isModified(FormDefinitionMeta.OUTPUT_TYPE))
                findPrinterOutputTypeObject(true);
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
        FormTemplate formTemplateObject = null;
        try {
            formTemplateObject = getFormTemplateObject();
        } catch (FrameworkException e) {
            throw new PreDeleteFailedException(null, e);
        }
        if (formTemplateObject != null) {
            try {
                // Perform cascade delete
                getUOW().delete(formTemplateObject);
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
     * @clientQualifier formId
     * @supplierQualifier formId
     * @link composition
     */
    /*#FormTemplate lnkFormTemplate;*/

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier formName
     * @supplierQualifier formName
     * @link association
     */
    /*#FormGroup lnkFormGroup;*/

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier outputType
     * @supplierQualifier outputType
     * @link association
     */
    /*#PrinterOutputType lnkPrinterOutputType;*/

    // .//GEN-END:3_be
    // .//GEN-BEGIN:preAdd_1_be
    /** This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * It ensures that the primary-key is unique and that the foreign-keys are valid.
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        try {
            if (getCreatedOn() == null)
                setCreatedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedDateTime Error"}, e);
        }
        try {
            if (getCreatedBy() == null && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setCreatedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreAddFailedException(new String[] {"StampType:CreatedUserId Error"}, e);
        }
        // .//GEN-END:preAdd_1_be
        // Add custom code//GEN-FIRST:preAdd_1


        // .//GEN-LAST:preAdd_1
        // .//GEN-BEGIN:preAdd_2_be
        super.preAdd();
    }
    // .//GEN-END:preAdd_2_be
    // .//GEN-BEGIN:preUpdate_1_be
    /** This method is triggered by the UOW, before adding this object to the Update-Store, but after a UOW has been associated to the object.
     * It ensures that the foreign-keys are valid.
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    public void preUpdate() throws PreUpdateFailedException {
        try {
            if (getLastChangedOn() == null || !isModified(FormDefinitionMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(FormDefinitionMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                setLastChangedBy(SecurityManager.getPrincipal().getName());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedUserId Error"}, e);
        }
        // .//GEN-END:preUpdate_1_be
        // Update custom code//GEN-FIRST:preUpdate_1


        // .//GEN-LAST:preUpdate_1
        // .//GEN-BEGIN:preUpdate_2_be
        super.preUpdate();
    }
    // .//GEN-END:preUpdate_2_be

    // All the custom code goes here//GEN-FIRST:custom
    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        // Ensure that the functional-key is unique
        if (!isDatabaseOccurence() || isModified(FormDefinitionMeta.FORM_NAME) || isModified(FormDefinitionMeta.FORM_ALTERNATE)
                || isModified(FormDefinitionMeta.FORM_VARIATION) || isModified(FormDefinitionMeta.OUTPUT_TYPE)) {
            Criteria criteria = findByCKCriteria(getFormName(), getFormAlternate(), getFormVariation(), getOutputType());
            // Exclude the current object from the query
            if (isDatabaseOccurence())
                criteria.addCriteria(FormDefinitionMeta.FORM_ID, Criteria.RELATIONAL_NOT_EQUALS, getFormId());
            if (getUOW().query(criteria).iterator().hasNext())
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.FormParameterAlreadyExists") {});
        }

        super.validate();
    }

    /** Returns the domain object for the input Candidate Key.
     * @return the domain object for the input Candidate Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static FormDefinition findByCK(UOW uow, String formName, String formAlternate, String formVariation, String outputType) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByCKCriteria(formName, formAlternate, formVariation, outputType);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (FormDefinition) itr.next();
            else
                return null;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    
    /** Returns a Criteria object for retrieving the domain object based on the input Candidate Key.
     * @return a Criteria object for retrieving the domain object based on the input Candidate Key.
     */
    public static Criteria findByCKCriteria(String formName, String formAlternate, String formVariation, String outputType) {
        Criteria criteria = new Criteria();
        criteria.setTable(FormDefinitionMeta.getName());
        if (formName != null)
            criteria.addCriteria(FormDefinitionMeta.FORM_NAME, formName);
        else
            criteria.addCriteria(FormDefinitionMeta.FORM_NAME, Criteria.RELATIONAL_IS_NULL);
        
        if (formAlternate != null)
            criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, formAlternate);
        else
            criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, Criteria.RELATIONAL_IS_NULL);
        
        if (formVariation != null)
            criteria.addCriteria(FormDefinitionMeta.FORM_VARIATION, formVariation);
        else
            criteria.addCriteria(FormDefinitionMeta.FORM_VARIATION, Criteria.RELATIONAL_IS_NULL);
        
        if (outputType != null)
            criteria.addCriteria(FormDefinitionMeta.OUTPUT_TYPE, outputType);
        else
            criteria.addCriteria(FormDefinitionMeta.OUTPUT_TYPE, Criteria.RELATIONAL_IS_NULL);
        
        return criteria;
    }

    // .//GEN-LAST:custom
}
