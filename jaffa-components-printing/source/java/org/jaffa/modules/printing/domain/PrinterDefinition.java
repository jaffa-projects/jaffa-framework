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




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_PRINTER_DEFINITIONS table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_PRINTER_DEFINITIONS")
@SqlResultSetMapping(name="PrinterDefinition",entities={@EntityResult(entityClass=PrinterDefinition.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class PrinterDefinition extends Persistent {

    private static final Logger log = Logger.getLogger(PrinterDefinition.class);
    /** Holds value of property printerId. */
    @XmlElement(name="printerId")
    @Id
    @Column(name="PRINTER_ID")    
    private java.lang.String m_printerId;

    /** Holds value of property description. */
    @XmlElement(name="description")
    @Column(name="DESCRIPTION")    
    private java.lang.String m_description;

    /** Holds value of property siteCode. */
    @XmlElement(name="siteCode")
    @Column(name="SITE_CODE")    
    private java.lang.String m_siteCode;

    /** Holds value of property locationCode. */
    @XmlElement(name="locationCode")
    @Column(name="LOCATION_CODE")    
    private java.lang.String m_locationCode;

    /** Holds value of property remote. */
    @XmlElement(name="remote")
    @Type(type="true_false")
    @Column(name="REMOTE_PRINTER")    
    private java.lang.Boolean m_remote;

    /** Holds value of property realPrinterName. */
    @XmlElement(name="realPrinterName")
    @Column(name="REAL_PRINTER_NAME")    
    private java.lang.String m_realPrinterName;

    /** Holds value of property printerOption1. */
    @XmlElement(name="printerOption1")
    @Column(name="PRINTER_OPTION1")    
    private java.lang.String m_printerOption1;

    /** Holds value of property printerOption2. */
    @XmlElement(name="printerOption2")
    @Column(name="PRINTER_OPTION2")    
    private java.lang.String m_printerOption2;

    /** Holds value of property outputType. */
    @XmlElement(name="outputType")
    @Column(name="OUTPUT_TYPE")    
    private java.lang.String m_outputType;

    /** Holds value of property scaleToPageSize. */
    @XmlElement(name="scaleToPageSize")
    @Column(name="SCALE_TO_PAGE_SIZE")    
    private java.lang.String m_scaleToPageSize;

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
    public PrinterDefinition() {
        StaticContext.configure(this);
    }

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String printerId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            boolean exists = false;
            Criteria criteria = findByPKCriteria(printerId);
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
    public static PrinterDefinition findByPK(UOW uow, java.lang.String printerId) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(printerId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (PrinterDefinition) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String printerId) {
        Criteria criteria = new Criteria();
        criteria.setTable(PrinterDefinitionMeta.getName());
        criteria.addCriteria(PrinterDefinitionMeta.PRINTER_ID, printerId);
        return criteria;
    }
    // .//GEN-END:2_be
        // .//GEN-BEGIN:printerId_be
    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public java.lang.String getPrinterId() {
        return m_printerId;
    }
    
    /** Use this method to update the property printerId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param printerId New value of property printerId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPrinterId(java.lang.String printerId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_printerId == null ? printerId == null : m_printerId.equals(printerId))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        printerId = validatePrinterId(printerId);
        // .//GEN-END:printerId_be
        // Add custom code before setting the value//GEN-FIRST:printerId


        // .//GEN-LAST:printerId
        // .//GEN-BEGIN:printerId_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.PRINTER_ID, m_printerId);
        m_printerId = printerId;
        // .//GEN-END:printerId_1_be
        // Add custom code after setting the value//GEN-FIRST:printerId_3


        // .//GEN-LAST:printerId_3
        // .//GEN-BEGIN:printerId_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPrinterId() method.
     * @param printerId New value of property printerId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePrinterId(java.lang.String printerId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPrinterId(printerId);
    }

    /** Use this method to validate a value for the property printerId.
     * @param printerId Value to be validated for the property printerId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePrinterId(java.lang.String printerId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:printerId_2_be
        // Add custom code before validation//GEN-FIRST:printerId_1


        // .//GEN-LAST:printerId_1
        // .//GEN-BEGIN:printerId_3_be
        printerId = FieldValidator.validate(printerId, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.PRINTER_ID, printerId, this.getUOW());

        // .//GEN-END:printerId_3_be
        // Add custom code after a successful validation//GEN-FIRST:printerId_2


        // .//GEN-LAST:printerId_2
        // .//GEN-BEGIN:printerId_4_be
        return printerId;
    }
    // .//GEN-END:printerId_4_be
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
        super.addInitialValue(PrinterDefinitionMeta.DESCRIPTION, m_description);
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
        description = FieldValidator.validate(description, (StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
    // .//GEN-BEGIN:siteCode_be
    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public java.lang.String getSiteCode() {
        return m_siteCode;
    }
    
    /** Use this method to update the property siteCode.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param siteCode New value of property siteCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setSiteCode(java.lang.String siteCode)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_siteCode == null ? siteCode == null : m_siteCode.equals(siteCode))
            return;


        siteCode = validateSiteCode(siteCode);
        // .//GEN-END:siteCode_be
        // Add custom code before setting the value//GEN-FIRST:siteCode


        // .//GEN-LAST:siteCode
        // .//GEN-BEGIN:siteCode_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.SITE_CODE, m_siteCode);
        m_siteCode = siteCode;
        // .//GEN-END:siteCode_1_be
        // Add custom code after setting the value//GEN-FIRST:siteCode_3


        // .//GEN-LAST:siteCode_3
        // .//GEN-BEGIN:siteCode_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setSiteCode() method.
     * @param siteCode New value of property siteCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSiteCode(java.lang.String siteCode)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setSiteCode(siteCode);
    }

    /** Use this method to validate a value for the property siteCode.
     * @param siteCode Value to be validated for the property siteCode.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateSiteCode(java.lang.String siteCode)
    throws ValidationException, FrameworkException {
        // .//GEN-END:siteCode_2_be
        // Add custom code before validation//GEN-FIRST:siteCode_1


        // .//GEN-LAST:siteCode_1
        // .//GEN-BEGIN:siteCode_3_be
        siteCode = FieldValidator.validate(siteCode, (StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.SITE_CODE, siteCode, this.getUOW());

        // .//GEN-END:siteCode_3_be
        // Add custom code after a successful validation//GEN-FIRST:siteCode_2


        // .//GEN-LAST:siteCode_2
        // .//GEN-BEGIN:siteCode_4_be
        return siteCode;
    }
    // .//GEN-END:siteCode_4_be
    // .//GEN-BEGIN:locationCode_be
    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public java.lang.String getLocationCode() {
        return m_locationCode;
    }
    
    /** Use this method to update the property locationCode.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param locationCode New value of property locationCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setLocationCode(java.lang.String locationCode)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_locationCode == null ? locationCode == null : m_locationCode.equals(locationCode))
            return;


        locationCode = validateLocationCode(locationCode);
        // .//GEN-END:locationCode_be
        // Add custom code before setting the value//GEN-FIRST:locationCode


        // .//GEN-LAST:locationCode
        // .//GEN-BEGIN:locationCode_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.LOCATION_CODE, m_locationCode);
        m_locationCode = locationCode;
        // .//GEN-END:locationCode_1_be
        // Add custom code after setting the value//GEN-FIRST:locationCode_3


        // .//GEN-LAST:locationCode_3
        // .//GEN-BEGIN:locationCode_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setLocationCode() method.
     * @param locationCode New value of property locationCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateLocationCode(java.lang.String locationCode)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setLocationCode(locationCode);
    }

    /** Use this method to validate a value for the property locationCode.
     * @param locationCode Value to be validated for the property locationCode.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateLocationCode(java.lang.String locationCode)
    throws ValidationException, FrameworkException {
        // .//GEN-END:locationCode_2_be
        // Add custom code before validation//GEN-FIRST:locationCode_1


        // .//GEN-LAST:locationCode_1
        // .//GEN-BEGIN:locationCode_3_be
        locationCode = FieldValidator.validate(locationCode, (StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.LOCATION_CODE, locationCode, this.getUOW());

        // .//GEN-END:locationCode_3_be
        // Add custom code after a successful validation//GEN-FIRST:locationCode_2


        // .//GEN-LAST:locationCode_2
        // .//GEN-BEGIN:locationCode_4_be
        return locationCode;
    }
    // .//GEN-END:locationCode_4_be
    // .//GEN-BEGIN:remote_be
    /** Getter for property remote.
     * @return Value of property remote.
     */
    public java.lang.Boolean getRemote() {
        return m_remote;
    }
    
    /** Use this method to update the property remote.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param remote New value of property remote.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRemote(java.lang.Boolean remote)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_remote == null ? remote == null : m_remote.equals(remote))
            return;


        remote = validateRemote(remote);
        // .//GEN-END:remote_be
        // Add custom code before setting the value//GEN-FIRST:remote


        // .//GEN-LAST:remote
        // .//GEN-BEGIN:remote_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.REMOTE, m_remote);
        m_remote = remote;
        // .//GEN-END:remote_1_be
        // Add custom code after setting the value//GEN-FIRST:remote_3


        // .//GEN-LAST:remote_3
        // .//GEN-BEGIN:remote_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRemote() method.
     * @param remote New value of property remote.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRemote(java.lang.Boolean remote)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRemote(remote);
    }

    /** Use this method to validate a value for the property remote.
     * @param remote Value to be validated for the property remote.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.Boolean validateRemote(java.lang.Boolean remote)
    throws ValidationException, FrameworkException {
        // .//GEN-END:remote_2_be
        // Add custom code before validation//GEN-FIRST:remote_1


        // .//GEN-LAST:remote_1
        // .//GEN-BEGIN:remote_3_be
        remote = FieldValidator.validate(remote, (BooleanFieldMetaData) PrinterDefinitionMeta.META_REMOTE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.REMOTE, remote, this.getUOW());

        // .//GEN-END:remote_3_be
        // Add custom code after a successful validation//GEN-FIRST:remote_2


        // .//GEN-LAST:remote_2
        // .//GEN-BEGIN:remote_4_be
        return remote;
    }
    // .//GEN-END:remote_4_be
    // .//GEN-BEGIN:realPrinterName_be
    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public java.lang.String getRealPrinterName() {
        return m_realPrinterName;
    }
    
    /** Use this method to update the property realPrinterName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param realPrinterName New value of property realPrinterName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setRealPrinterName(java.lang.String realPrinterName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_realPrinterName == null ? realPrinterName == null : m_realPrinterName.equals(realPrinterName))
            return;


        realPrinterName = validateRealPrinterName(realPrinterName);
        // .//GEN-END:realPrinterName_be
        // Add custom code before setting the value//GEN-FIRST:realPrinterName


        // .//GEN-LAST:realPrinterName
        // .//GEN-BEGIN:realPrinterName_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.REAL_PRINTER_NAME, m_realPrinterName);
        m_realPrinterName = realPrinterName;
        // .//GEN-END:realPrinterName_1_be
        // Add custom code after setting the value//GEN-FIRST:realPrinterName_3


        // .//GEN-LAST:realPrinterName_3
        // .//GEN-BEGIN:realPrinterName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setRealPrinterName() method.
     * @param realPrinterName New value of property realPrinterName.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateRealPrinterName(java.lang.String realPrinterName)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setRealPrinterName(realPrinterName);
    }

    /** Use this method to validate a value for the property realPrinterName.
     * @param realPrinterName Value to be validated for the property realPrinterName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateRealPrinterName(java.lang.String realPrinterName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:realPrinterName_2_be
        // Add custom code before validation//GEN-FIRST:realPrinterName_1


        // .//GEN-LAST:realPrinterName_1
        // .//GEN-BEGIN:realPrinterName_3_be
        realPrinterName = FieldValidator.validate(realPrinterName, (StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.REAL_PRINTER_NAME, realPrinterName, this.getUOW());

        // .//GEN-END:realPrinterName_3_be
        // Add custom code after a successful validation//GEN-FIRST:realPrinterName_2


        // .//GEN-LAST:realPrinterName_2
        // .//GEN-BEGIN:realPrinterName_4_be
        return realPrinterName;
    }
    // .//GEN-END:realPrinterName_4_be
    // .//GEN-BEGIN:printerOption1_be
    /** Getter for property printerOption1.
     * @return Value of property printerOption1.
     */
    public java.lang.String getPrinterOption1() {
        return m_printerOption1;
    }
    
    /** Use this method to update the property printerOption1.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param printerOption1 New value of property printerOption1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPrinterOption1(java.lang.String printerOption1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_printerOption1 == null ? printerOption1 == null : m_printerOption1.equals(printerOption1))
            return;


        printerOption1 = validatePrinterOption1(printerOption1);
        // .//GEN-END:printerOption1_be
        // Add custom code before setting the value//GEN-FIRST:printerOption1


        // .//GEN-LAST:printerOption1
        // .//GEN-BEGIN:printerOption1_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.PRINTER_OPTION1, m_printerOption1);
        m_printerOption1 = printerOption1;
        // .//GEN-END:printerOption1_1_be
        // Add custom code after setting the value//GEN-FIRST:printerOption1_3


        // .//GEN-LAST:printerOption1_3
        // .//GEN-BEGIN:printerOption1_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPrinterOption1() method.
     * @param printerOption1 New value of property printerOption1.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePrinterOption1(java.lang.String printerOption1)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPrinterOption1(printerOption1);
    }

    /** Use this method to validate a value for the property printerOption1.
     * @param printerOption1 Value to be validated for the property printerOption1.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePrinterOption1(java.lang.String printerOption1)
    throws ValidationException, FrameworkException {
        // .//GEN-END:printerOption1_2_be
        // Add custom code before validation//GEN-FIRST:printerOption1_1


        // .//GEN-LAST:printerOption1_1
        // .//GEN-BEGIN:printerOption1_3_be
        printerOption1 = FieldValidator.validate(printerOption1, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION1, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.PRINTER_OPTION1, printerOption1, this.getUOW());

        // .//GEN-END:printerOption1_3_be
        // Add custom code after a successful validation//GEN-FIRST:printerOption1_2


        // .//GEN-LAST:printerOption1_2
        // .//GEN-BEGIN:printerOption1_4_be
        return printerOption1;
    }
    // .//GEN-END:printerOption1_4_be
    // .//GEN-BEGIN:printerOption2_be
    /** Getter for property printerOption2.
     * @return Value of property printerOption2.
     */
    public java.lang.String getPrinterOption2() {
        return m_printerOption2;
    }
    
    /** Use this method to update the property printerOption2.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param printerOption2 New value of property printerOption2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPrinterOption2(java.lang.String printerOption2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_printerOption2 == null ? printerOption2 == null : m_printerOption2.equals(printerOption2))
            return;


        printerOption2 = validatePrinterOption2(printerOption2);
        // .//GEN-END:printerOption2_be
        // Add custom code before setting the value//GEN-FIRST:printerOption2


        // .//GEN-LAST:printerOption2
        // .//GEN-BEGIN:printerOption2_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.PRINTER_OPTION2, m_printerOption2);
        m_printerOption2 = printerOption2;
        // .//GEN-END:printerOption2_1_be
        // Add custom code after setting the value//GEN-FIRST:printerOption2_3


        // .//GEN-LAST:printerOption2_3
        // .//GEN-BEGIN:printerOption2_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPrinterOption2() method.
     * @param printerOption2 New value of property printerOption2.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePrinterOption2(java.lang.String printerOption2)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPrinterOption2(printerOption2);
    }

    /** Use this method to validate a value for the property printerOption2.
     * @param printerOption2 Value to be validated for the property printerOption2.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validatePrinterOption2(java.lang.String printerOption2)
    throws ValidationException, FrameworkException {
        // .//GEN-END:printerOption2_2_be
        // Add custom code before validation//GEN-FIRST:printerOption2_1


        // .//GEN-LAST:printerOption2_1
        // .//GEN-BEGIN:printerOption2_3_be
        printerOption2 = FieldValidator.validate(printerOption2, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION2, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.PRINTER_OPTION2, printerOption2, this.getUOW());

        // .//GEN-END:printerOption2_3_be
        // Add custom code after a successful validation//GEN-FIRST:printerOption2_2


        // .//GEN-LAST:printerOption2_2
        // .//GEN-BEGIN:printerOption2_4_be
        return printerOption2;
    }
    // .//GEN-END:printerOption2_4_be
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
        super.addInitialValue(PrinterDefinitionMeta.OUTPUT_TYPE, m_outputType);
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
        outputType = FieldValidator.validate(outputType, (StringFieldMetaData) PrinterDefinitionMeta.META_OUTPUT_TYPE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.OUTPUT_TYPE, outputType, this.getUOW());

        // .//GEN-END:outputType_3_be
        // Add custom code after a successful validation//GEN-FIRST:outputType_2


        // .//GEN-LAST:outputType_2
        // .//GEN-BEGIN:outputType_4_be
        return outputType;
    }
    // .//GEN-END:outputType_4_be
    // .//GEN-BEGIN:scaleToPageSize_be
    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public java.lang.String getScaleToPageSize() {
        return m_scaleToPageSize;
    }
    
    /** Use this method to update the property scaleToPageSize.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param scaleToPageSize New value of property scaleToPageSize.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setScaleToPageSize(java.lang.String scaleToPageSize)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_scaleToPageSize == null ? scaleToPageSize == null : m_scaleToPageSize.equals(scaleToPageSize))
            return;


        scaleToPageSize = validateScaleToPageSize(scaleToPageSize);
        // .//GEN-END:scaleToPageSize_be
        // Add custom code before setting the value//GEN-FIRST:scaleToPageSize


        // .//GEN-LAST:scaleToPageSize
        // .//GEN-BEGIN:scaleToPageSize_1_be
        super.update();
        super.addInitialValue(PrinterDefinitionMeta.SCALE_TO_PAGE_SIZE, m_scaleToPageSize);
        m_scaleToPageSize = scaleToPageSize;
        // .//GEN-END:scaleToPageSize_1_be
        // Add custom code after setting the value//GEN-FIRST:scaleToPageSize_3


        // .//GEN-LAST:scaleToPageSize_3
        // .//GEN-BEGIN:scaleToPageSize_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setScaleToPageSize() method.
     * @param scaleToPageSize New value of property scaleToPageSize.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateScaleToPageSize(java.lang.String scaleToPageSize)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setScaleToPageSize(scaleToPageSize);
    }

    /** Use this method to validate a value for the property scaleToPageSize.
     * @param scaleToPageSize Value to be validated for the property scaleToPageSize.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateScaleToPageSize(java.lang.String scaleToPageSize)
    throws ValidationException, FrameworkException {
        // .//GEN-END:scaleToPageSize_2_be
        // Add custom code before validation//GEN-FIRST:scaleToPageSize_1


        // .//GEN-LAST:scaleToPageSize_1
        // .//GEN-BEGIN:scaleToPageSize_3_be
        scaleToPageSize = FieldValidator.validate(scaleToPageSize, (StringFieldMetaData) PrinterDefinitionMeta.META_SCALE_TO_PAGE_SIZE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.SCALE_TO_PAGE_SIZE, scaleToPageSize, this.getUOW());

        // .//GEN-END:scaleToPageSize_3_be
        // Add custom code after a successful validation//GEN-FIRST:scaleToPageSize_2


        // .//GEN-LAST:scaleToPageSize_2
        // .//GEN-BEGIN:scaleToPageSize_4_be
        return scaleToPageSize;
    }
    // .//GEN-END:scaleToPageSize_4_be
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
        super.addInitialValue(PrinterDefinitionMeta.CREATED_ON, m_createdOn);
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
        createdOn = FieldValidator.validate(createdOn, (DateTimeFieldMetaData) PrinterDefinitionMeta.META_CREATED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.CREATED_ON, createdOn, this.getUOW());

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
        super.addInitialValue(PrinterDefinitionMeta.CREATED_BY, m_createdBy);
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
        createdBy = FieldValidator.validate(createdBy, (StringFieldMetaData) PrinterDefinitionMeta.META_CREATED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.CREATED_BY, createdBy, this.getUOW());

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
        super.addInitialValue(PrinterDefinitionMeta.LAST_CHANGED_ON, m_lastChangedOn);
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
        lastChangedOn = FieldValidator.validate(lastChangedOn, (DateTimeFieldMetaData) PrinterDefinitionMeta.META_LAST_CHANGED_ON, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.LAST_CHANGED_ON, lastChangedOn, this.getUOW());

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
        super.addInitialValue(PrinterDefinitionMeta.LAST_CHANGED_BY, m_lastChangedBy);
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
        lastChangedBy = FieldValidator.validate(lastChangedBy, (StringFieldMetaData) PrinterDefinitionMeta.META_LAST_CHANGED_BY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(PrinterDefinitionMeta.getName(), PrinterDefinitionMeta.LAST_CHANGED_BY, lastChangedBy, this.getUOW());

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
                    throw new InvalidForeignKeyException(PrinterDefinitionMeta.META_OUTPUT_TYPE.getLabelToken(), new Object[] {PrinterOutputTypeMeta.getLabelToken(), PrinterOutputTypeMeta.META_OUTPUT_TYPE.getLabelToken()});
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
        buf.append("<PrinterDefinition>");
        buf.append("<printerId>"); if (m_printerId != null) buf.append(m_printerId); buf.append("</printerId>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<siteCode>"); if (m_siteCode != null) buf.append(m_siteCode); buf.append("</siteCode>");
        buf.append("<locationCode>"); if (m_locationCode != null) buf.append(m_locationCode); buf.append("</locationCode>");
        buf.append("<remote>"); if (m_remote != null) buf.append(m_remote); buf.append("</remote>");
        buf.append("<realPrinterName>"); if (m_realPrinterName != null) buf.append(m_realPrinterName); buf.append("</realPrinterName>");
        buf.append("<printerOption1>"); if (m_printerOption1 != null) buf.append(m_printerOption1); buf.append("</printerOption1>");
        buf.append("<printerOption2>"); if (m_printerOption2 != null) buf.append(m_printerOption2); buf.append("</printerOption2>");
        buf.append("<outputType>"); if (m_outputType != null) buf.append(m_outputType); buf.append("</outputType>");
        buf.append("<scaleToPageSize>"); if (m_scaleToPageSize != null) buf.append(m_scaleToPageSize); buf.append("</scaleToPageSize>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (m_lastChangedOn != null) buf.append(m_lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (m_lastChangedBy != null) buf.append(m_lastChangedBy); buf.append("</lastChangedBy>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</PrinterDefinition>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        PrinterDefinition obj = (PrinterDefinition) super.clone();
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
            if (isModified(PrinterDefinitionMeta.OUTPUT_TYPE))
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
            if (getLastChangedOn() == null || !isModified(PrinterDefinitionMeta.LAST_CHANGED_ON))
                setLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        } catch (FrameworkException e) {
            throw new PreUpdateFailedException(new String[] {"StampType:LastUpdatedDateTime Error"}, e);
        }
        try {
            if ((getLastChangedBy() == null || !isModified(PrinterDefinitionMeta.LAST_CHANGED_BY)) && SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
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






    // .//GEN-LAST:custom
}
