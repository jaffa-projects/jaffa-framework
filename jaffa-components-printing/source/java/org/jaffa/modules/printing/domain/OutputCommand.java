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
import org.jaffa.modules.printing.domain.PrinterOutputType;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports
import org.jaffa.datatypes.exceptions.BelowMinimumException;
import org.jaffa.exceptions.ApplicationException;



// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_OUTPUT_COMMANDS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_OUTPUT_COMMANDS")
@SqlResultSetMapping(name="OutputCommand",entities={@EntityResult(entityClass=OutputCommand.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class OutputCommand extends Persistent {

    private static final Logger log = Logger.getLogger(OutputCommand.class);
    /** Holds value of property outputCommandId. */
    @XmlElement(name="outputCommandId")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="S_J_OUTPUT_COMMANDS_1")
    @SequenceGenerator(name="S_J_OUTPUT_COMMANDS_1", sequenceName="S_J_OUTPUT_COMMANDS_1")
    @Column(name="OUTPUT_COMMAND_ID")    
    private java.lang.Long m_outputCommandId;

    /** Holds value of property outputType. */
    @XmlElement(name="outputType")
    @Column(name="OUTPUT_TYPE")    
    private java.lang.String m_outputType;

    /** Holds value of property sequenceNo. */
    @XmlElement(name="sequenceNo")
    @Column(name="SEQUENCE_NO")    
    private java.lang.Long m_sequenceNo;

    /** Holds value of property osPattern. */
    @XmlElement(name="osPattern")
    @Column(name="OS_PATTERN")    
    private java.lang.String m_osPattern;

    /** Holds value of property commandLine. */
    @XmlElement(name="commandLine")
    @Column(name="COMMAND_LINE")    
    private java.lang.String m_commandLine;

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

    /** Holds related foreign PrinterOutputType object. */
    private transient PrinterOutputType m_printerOutputTypeObject;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public OutputCommand() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.Long outputCommandId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(outputCommandId);
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
    public static OutputCommand findByPK(UOW uow, java.lang.Long outputCommandId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(outputCommandId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (OutputCommand) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.Long outputCommandId) {
        Criteria criteria = new Criteria();
        criteria.setTable(OutputCommandMeta.getName());
        criteria.addCriteria(OutputCommandMeta.OUTPUT_COMMAND_ID, outputCommandId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:outputCommandId_be
    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public java.lang.Long getOutputCommandId() {
        return m_outputCommandId;
    }
    
    /** Use this method to update the property outputCommandId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param outputCommandId New value of property outputCommandId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setOutputCommandId(java.lang.Long outputCommandId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_outputCommandId == null ? outputCommandId == null : m_outputCommandId.equals(outputCommandId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        outputCommandId = validateOutputCommandId(outputCommandId);
        // .//GEN-END:outputCommandId_be
        // Add custom code before setting the value//GEN-FIRST:outputCommandId


        // .//GEN-LAST:outputCommandId
        // .//GEN-BEGIN:outputCommandId_1_be
        super.update();
        super.addInitialValue(OutputCommandMeta.OUTPUT_COMMAND_ID, m_outputCommandId);
        m_outputCommandId = outputCommandId;
        // .//GEN-END:outputCommandId_1_be
        // Add custom code after setting the value//GEN-FIRST:outputCommandId_3


        // .//GEN-LAST:outputCommandId_3
        // .//GEN-BEGIN:outputCommandId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setOutputCommandId() method.
     * @param outputCommandId New value of property outputCommandId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateOutputCommandId(java.lang.Long outputCommandId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setOutputCommandId(outputCommandId);
    }

    /** Use this method to validate a value for the property outputCommandId.
     * @param outputCommandId Value to be validated for the property outputCommandId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateOutputCommandId(java.lang.Long outputCommandId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:outputCommandId_2_be
        // Add custom code before validation//GEN-FIRST:outputCommandId_1


        // .//GEN-LAST:outputCommandId_1
        // .//GEN-BEGIN:outputCommandId_3_be
        outputCommandId = FieldValidator.validate(outputCommandId, (IntegerFieldMetaData) OutputCommandMeta.META_OUTPUT_COMMAND_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.OUTPUT_COMMAND_ID, outputCommandId, this.getUOW());

        // .//GEN-END:outputCommandId_3_be
        // Add custom code after a successful validation//GEN-FIRST:outputCommandId_2


        // .//GEN-LAST:outputCommandId_2
        // .//GEN-BEGIN:outputCommandId_4_be
        return outputCommandId;
    }
    // .//GEN-END:outputCommandId_4_be
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
        super.addInitialValue(OutputCommandMeta.OUTPUT_TYPE, m_outputType);
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
        outputType = FieldValidator.validate(outputType, (StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.OUTPUT_TYPE, outputType, this.getUOW());

        // .//GEN-END:outputType_3_be
        // Add custom code after a successful validation//GEN-FIRST:outputType_2


        // .//GEN-LAST:outputType_2
        // .//GEN-BEGIN:outputType_4_be
        return outputType;
    }
    // .//GEN-END:outputType_4_be
    // .//GEN-BEGIN:sequenceNo_be
    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public java.lang.Long getSequenceNo() {
        return m_sequenceNo;
    }
    
    /** Use this method to update the property sequenceNo.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sequenceNo New value of property sequenceNo.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSequenceNo(java.lang.Long sequenceNo)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_sequenceNo == null ? sequenceNo == null : m_sequenceNo.equals(sequenceNo))
            return;


        sequenceNo = validateSequenceNo(sequenceNo);
        // .//GEN-END:sequenceNo_be
        // Add custom code before setting the value//GEN-FIRST:sequenceNo


        // .//GEN-LAST:sequenceNo
        // .//GEN-BEGIN:sequenceNo_1_be
        super.update();
        super.addInitialValue(OutputCommandMeta.SEQUENCE_NO, m_sequenceNo);
        m_sequenceNo = sequenceNo;
        // .//GEN-END:sequenceNo_1_be
        // Add custom code after setting the value//GEN-FIRST:sequenceNo_3


        // .//GEN-LAST:sequenceNo_3
        // .//GEN-BEGIN:sequenceNo_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSequenceNo() method.
     * @param sequenceNo New value of property sequenceNo.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSequenceNo(java.lang.Long sequenceNo)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSequenceNo(sequenceNo);
    }

    /** Use this method to validate a value for the property sequenceNo.
     * @param sequenceNo Value to be validated for the property sequenceNo.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Long validateSequenceNo(java.lang.Long sequenceNo)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sequenceNo_2_be
        // Add custom code before validation//GEN-FIRST:sequenceNo_1


        // .//GEN-LAST:sequenceNo_1
        // .//GEN-BEGIN:sequenceNo_3_be
        sequenceNo = FieldValidator.validate(sequenceNo, (IntegerFieldMetaData) OutputCommandMeta.META_SEQUENCE_NO, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.SEQUENCE_NO, sequenceNo, this.getUOW());

        // .//GEN-END:sequenceNo_3_be
        // Add custom code after a successful validation//GEN-FIRST:sequenceNo_2


        // .//GEN-LAST:sequenceNo_2
        // .//GEN-BEGIN:sequenceNo_4_be
        return sequenceNo;
    }
    // .//GEN-END:sequenceNo_4_be
    // .//GEN-BEGIN:osPattern_be
    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public java.lang.String getOsPattern() {
        return m_osPattern;
    }
    
    /** Use this method to update the property osPattern.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param osPattern New value of property osPattern.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setOsPattern(java.lang.String osPattern)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_osPattern == null ? osPattern == null : m_osPattern.equals(osPattern))
            return;


        osPattern = validateOsPattern(osPattern);
        // .//GEN-END:osPattern_be
        // Add custom code before setting the value//GEN-FIRST:osPattern


        // .//GEN-LAST:osPattern
        // .//GEN-BEGIN:osPattern_1_be
        super.update();
        super.addInitialValue(OutputCommandMeta.OS_PATTERN, m_osPattern);
        m_osPattern = osPattern;
        // .//GEN-END:osPattern_1_be
        // Add custom code after setting the value//GEN-FIRST:osPattern_3


        // .//GEN-LAST:osPattern_3
        // .//GEN-BEGIN:osPattern_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setOsPattern() method.
     * @param osPattern New value of property osPattern.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateOsPattern(java.lang.String osPattern)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setOsPattern(osPattern);
    }

    /** Use this method to validate a value for the property osPattern.
     * @param osPattern Value to be validated for the property osPattern.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateOsPattern(java.lang.String osPattern)
    throws ValidationException, FrameworkException {
        // .//GEN-END:osPattern_2_be
        // Add custom code before validation//GEN-FIRST:osPattern_1


        // .//GEN-LAST:osPattern_1
        // .//GEN-BEGIN:osPattern_3_be
        osPattern = FieldValidator.validate(osPattern, (StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.OS_PATTERN, osPattern, this.getUOW());

        // .//GEN-END:osPattern_3_be
        // Add custom code after a successful validation//GEN-FIRST:osPattern_2


        // .//GEN-LAST:osPattern_2
        // .//GEN-BEGIN:osPattern_4_be
        return osPattern;
    }
    // .//GEN-END:osPattern_4_be
    // .//GEN-BEGIN:commandLine_be
    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public java.lang.String getCommandLine() {
        return m_commandLine;
    }
    
    /** Use this method to update the property commandLine.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param commandLine New value of property commandLine.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setCommandLine(java.lang.String commandLine)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_commandLine == null ? commandLine == null : m_commandLine.equals(commandLine))
            return;


        commandLine = validateCommandLine(commandLine);
        // .//GEN-END:commandLine_be
        // Add custom code before setting the value//GEN-FIRST:commandLine


        // .//GEN-LAST:commandLine
        // .//GEN-BEGIN:commandLine_1_be
        super.update();
        super.addInitialValue(OutputCommandMeta.COMMAND_LINE, m_commandLine);
        m_commandLine = commandLine;
        // .//GEN-END:commandLine_1_be
        // Add custom code after setting the value//GEN-FIRST:commandLine_3


        // .//GEN-LAST:commandLine_3
        // .//GEN-BEGIN:commandLine_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setCommandLine() method.
     * @param commandLine New value of property commandLine.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateCommandLine(java.lang.String commandLine)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setCommandLine(commandLine);
    }

    /** Use this method to validate a value for the property commandLine.
     * @param commandLine Value to be validated for the property commandLine.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateCommandLine(java.lang.String commandLine)
    throws ValidationException, FrameworkException {
        // .//GEN-END:commandLine_2_be
        // Add custom code before validation//GEN-FIRST:commandLine_1


        // .//GEN-LAST:commandLine_1
        // .//GEN-BEGIN:commandLine_3_be
        commandLine = FieldValidator.validate(commandLine, (StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.COMMAND_LINE, commandLine, this.getUOW());

        // .//GEN-END:commandLine_3_be
        // Add custom code after a successful validation//GEN-FIRST:commandLine_2


        // .//GEN-LAST:commandLine_2
        // .//GEN-BEGIN:commandLine_4_be
        return commandLine;
    }
    // .//GEN-END:commandLine_4_be
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
        super.addInitialValue(OutputCommandMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) OutputCommandMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.CREATED_ON, createdOn, this.getUOW());

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
        super.addInitialValue(OutputCommandMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) OutputCommandMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.CREATED_BY, createdBy, this.getUOW());

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
        super.addInitialValue(OutputCommandMeta.LAST_CHANGED_ON, m_lastChangedOn);
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
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) OutputCommandMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

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
        super.addInitialValue(OutputCommandMeta.LAST_CHANGED_BY, m_lastChangedBy);
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
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) OutputCommandMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(OutputCommandMeta.getName(), OutputCommandMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

        // .//GEN-END:lastChangedBy_3_be
        // Add custom code after a successful validation//GEN-FIRST:lastChangedBy_2


        // .//GEN-LAST:lastChangedBy_2
        // .//GEN-BEGIN:lastChangedBy_4_be
        return lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_4_be
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
                    throw new InvalidForeignKeyException(OutputCommandMeta.META_OUTPUT_TYPE.getLabelToken(), new Object[] {PrinterOutputTypeMeta.getLabelToken(), PrinterOutputTypeMeta.META_OUTPUT_TYPE.getLabelToken()});
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
        buf.append("<OutputCommand>");
        buf.append("<outputCommandId>"); if (m_outputCommandId != null) buf.append(m_outputCommandId); buf.append("</outputCommandId>");
        buf.append("<outputType>"); if (m_outputType != null) buf.append(m_outputType); buf.append("</outputType>");
        buf.append("<sequenceNo>"); if (m_sequenceNo != null) buf.append(m_sequenceNo); buf.append("</sequenceNo>");
        buf.append("<osPattern>"); if (m_osPattern != null) buf.append(m_osPattern); buf.append("</osPattern>");
        buf.append("<commandLine>"); if (m_commandLine != null) buf.append(m_commandLine); buf.append("</commandLine>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</OutputCommand>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        OutputCommand obj = (OutputCommand) super.clone();
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
            if (isModified(OutputCommandMeta.OUTPUT_TYPE))
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
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // .//GEN-BEGIN:3_be
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
            if (getLastChangedOn() == null || !isModified(OutputCommandMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(OutputCommandMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
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
        // Do not perform the sequenceNo related validations if this instance is being resequenced
        if (!m_isBeingResequenced) {
            // The sequenceNo should be at least 1
            if (isModified(OutputCommandMeta.SEQUENCE_NO)) {
                if (getSequenceNo() == null || getSequenceNo().longValue() < 1)
                    throw new ApplicationExceptions(new BelowMinimumException(OutputCommandMeta.META_SEQUENCE_NO.getLabelToken(), new Object[] {new Long(1)}));
            }

            // The sequenceNo should not exceed the total number of records for a given outputType by more than 1
            if (isModified(OutputCommandMeta.OUTPUT_TYPE) || isModified(OutputCommandMeta.SEQUENCE_NO)) {
                long count = 0;
                Criteria criteria = new Criteria();
                criteria.setTable(OutputCommandMeta.getName());
                if (getOutputCommandId() != null)
                    criteria.addCriteria(OutputCommandMeta.OUTPUT_COMMAND_ID, Criteria.RELATIONAL_NOT_EQUALS, getOutputCommandId());
                criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, getOutputType());
                criteria.addFunction(Criteria.FUNCTION_COUNT, null, "f1");
                Iterator itr = getUOW().query(criteria).iterator();
                if (itr.hasNext()) {
                    Map row = (Map) itr.next();
                    if (row.get("f1") != null)
                        count = ((Number) row.get("f1")).longValue();
                }
                if ((getSequenceNo().longValue() - count) > 1) {
                    log.error("The sequenceNo " + getSequenceNo() + " is much greater than the number of rows " + count);
                    throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.OutputCommandMaintenance.ValidSequenceNo"){});
                }
            }
        }

        super.validate();
    }

    /** Indicates if an instance is being resequenced. It prevents unnecessary validations.*/
    @Transient
    private boolean m_isBeingResequenced = false;
    
    /** Returns the domain object for the input Candidate Key.
     * @return the domain object for the input Candidate Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static OutputCommand findByCK(UOW uow, String outputType, Long sequenceNo) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByCKCriteria(outputType, sequenceNo);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (OutputCommand) itr.next();
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
    public static Criteria findByCKCriteria(String outputType, Long sequenceNo) {
        Criteria criteria = new Criteria();
        criteria.setTable(OutputCommandMeta.getName());
        if (outputType != null)
            criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, outputType);
        else
            criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, Criteria.RELATIONAL_IS_NULL);
        
        if (sequenceNo != null)
            criteria.addCriteria(OutputCommandMeta.SEQUENCE_NO, sequenceNo);
        else
            criteria.addCriteria(OutputCommandMeta.SEQUENCE_NO, Criteria.RELATIONAL_IS_NULL);
        
        return criteria;
    }

    /** Resequences all instances having sequenceNo greater than this instance's sequenceNo, decrementing each by 1.
     *
     * NOTE: This method should be invoked AFTER sending this instance to the UOW for deletion.
     * Otheriwse the database will thrown errors if the candidate-key checks are implemented at the database level.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException Indicates some system error
     */
    public void adjustSequenceNoAfterDelete(UOW uow) throws FrameworkException, ApplicationExceptions {
        adjustSequence(uow, getOutputType(), getSequenceNo(), true);
    }
    
    /** Resequences all instances having sequenceNo greater than or equal to this instance's sequenceNo, incrementing each by 1.
     *
     * NOTE: This method should be invoked BEFORE sending this instance to the UOW for addition.
     * Otheriwse the database will thrown errors if the candidate-key checks are implemented at the database level.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException Indicates some system error
     */
    public void adjustSequenceNoBeforeAdd(UOW uow) throws FrameworkException, ApplicationExceptions {
        adjustSequence(uow, getOutputType(), getSequenceNo(), false);
    }
    
    /** Resequences all instances having sequenceNo greater than or equal to this instance's sequenceNo, incrementing each by 1.
     *
     * NOTE: This method should be invoked BEFORE sending this instance to the UOW for updation.
     * Otheriwse the database will thrown errors if the candidate-key checks are implemented at the database level.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException Indicates some system error
     */
    public void adjustSequenceNoBeforeUpdate(UOW uow) throws FrameworkException, ApplicationExceptions {
        // Note: This supports the resequencing even if the outputType is changed, in which case 2 separate sets of records are resequenced
        if (isModified(OutputCommandMeta.OUTPUT_TYPE) || isModified(OutputCommandMeta.SEQUENCE_NO)) {
            String originalOutputType = isModified(OutputCommandMeta.OUTPUT_TYPE) ? (String) returnInitialValue(OutputCommandMeta.OUTPUT_TYPE) : getOutputType();
            Long originalSequenceNo = isModified(OutputCommandMeta.SEQUENCE_NO) ? (Long) returnInitialValue(OutputCommandMeta.SEQUENCE_NO) : getSequenceNo();
            String newOutputType = getOutputType();
            Long newSequenceNo = getSequenceNo();
            if (log.isDebugEnabled())
                log.debug("outputType/sequenceNo changed from " + originalOutputType + '/' + originalSequenceNo + " to " + newOutputType + '/' + newSequenceNo);
            
            // Treat this as if we are deleting the original, and adding a new
            // However we do not want to generate a new technical-key
            // Hence we'll first update the sequenceNo of the current instance to a temporary value, say '-1' and then simulate the deletion + addition
            // This will prevent any duplicate candidate-key errors in the database, if at all
            try {
                if (log.isDebugEnabled())
                    log.debug("Temporarily changing the sequenceNo to an invalid value -1");
                m_isBeingResequenced = true; // Prevent unnecessary validations
                setSequenceNo(new Long(-1));
                uow.update(this);
            } catch(ValidationException e) {
                throw new ApplicationExceptions(e);
            }
            
            // Simulate a deletion for the original
            if (log.isDebugEnabled())
                log.debug("adjustSequenceNoBeforeUpdate: Treating outputType=" + originalOutputType + ", sequenceNo=" + originalSequenceNo + " as being deleted");
            adjustSequence(uow, originalOutputType, originalSequenceNo, true);
            
            // Simulate an addition for the new
            if (log.isDebugEnabled())
                log.debug("adjustSequenceNoBeforeUpdate: Treating outputType=" + newOutputType + ", sequenceNo=" + newSequenceNo + " as being added");
            adjustSequence(uow, newOutputType, newSequenceNo, false);
            
            // Restore the original sequenceNo before returning
            try {
                if (log.isDebugEnabled())
                    log.debug("Restoring the sequenceNo back to " + newSequenceNo);
                m_isBeingResequenced = false; // The new sequenceNo could be invalid!
                setSequenceNo(newSequenceNo);
            } catch(ValidationException e) {
                throw new ApplicationExceptions(e);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Resequencing is not required since both outputType and sequenceNo are unchanged");
        }
    }
    
    /** If isBeingDeleted is true, resequences all instances having sequenceNo greater than the input sequenceNo, decrementing each by 1.
     * If isBeingDeleted is false, resequences all instances having sequenceNo greater than or equal to the input sequenceNo, incrementing each by 1.
     * Otheriwse the database will thrown errors if the candidate-key checks are implemented at the database level.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException Indicates some system error
     */
    private static void adjustSequence(UOW uow, String outputType, Long sequenceNo, boolean isBeingDeleted) throws FrameworkException, ApplicationExceptions {
        long baseSequenceNo = isBeingDeleted ? sequenceNo.longValue() : sequenceNo.longValue() - 1;
        if (log.isDebugEnabled())
            log.debug(isBeingDeleted ? "adjustSequenceNoAfterDelete" : "adjustSequenceNoBeforeAdd" + ": Locking all OutputCommands records having outputType=" + outputType + ", and then resequencing records having sequenceNo > " + baseSequenceNo);
        Criteria criteria = new Criteria();
        criteria.setTable(OutputCommandMeta.getName());
        criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, outputType);
        criteria.addOrderBy(OutputCommandMeta.SEQUENCE_NO, isBeingDeleted ? Criteria.ORDER_BY_ASC : Criteria.ORDER_BY_DESC);
        // Lock the entire set of records for an outputType. We do not want any other thread to start resequencing on the same set of records.
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        for (Iterator itr = uow.query(criteria).iterator(); itr.hasNext(); ) {
            OutputCommand oc = (OutputCommand) itr.next();
            if (oc.getSequenceNo().longValue() > baseSequenceNo) {
                try {
                    oc.m_isBeingResequenced = true; // Prevent unnecessary validations
                    oc.setSequenceNo(new Long(isBeingDeleted ? oc.getSequenceNo().longValue() - 1 : oc.getSequenceNo().longValue() + 1));
                } catch(ValidationException e) {
                    throw new ApplicationExceptions(e);
                }
                uow.update(oc);
            }
        }
    }
    

    // .//GEN-LAST:custom
}
