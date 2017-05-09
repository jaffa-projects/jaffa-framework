// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.audit.domain;

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




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_AUDIT_TRANSACTIONS$VIEW table.
 * @author  Auto-Generated
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="J_AUDIT_TRANSACTIONS$VIEW")
@SqlResultSetMapping(name="AuditTransactionView",entities={@EntityResult(entityClass=AuditTransactionView.class)})
@TypeDefs({@TypeDef(name="dateTimeType", typeClass=DateTimeType.class), @TypeDef(name="dateOnlyType", typeClass=DateOnlyType.class)})
public class AuditTransactionView extends Persistent {

    private static final Logger log = Logger.getLogger(AuditTransactionView.class);
    /** Holds value of property transactionId. */
    @XmlElement(name="transactionId")
    @Column(name="TRANSACTION_ID")    
    private java.lang.String m_transactionId;

    /** Holds value of property processName. */
    @XmlElement(name="processName")
    @Column(name="PROCESS_NAME")    
    private java.lang.String m_processName;

    /** Holds value of property subProcessName. */
    @XmlElement(name="subProcessName")
    @Column(name="SUB_PROCESS_NAME")    
    private java.lang.String m_subProcessName;

    /** Holds value of property reason. */
    @XmlElement(name="reason")
    @Column(name="REASON")    
    private java.lang.String m_reason;

    /** Holds value of property createdBy. */
    @XmlElement(name="createdBy")
    @Column(name="CREATED_BY")    
    private java.lang.String m_createdBy;

    /** Holds value of property createdOn. */
    @XmlElement(name="createdOn")
    @Type(type="dateTimeType")
    @Column(name="CREATED_ON")    
    private org.jaffa.datatypes.DateTime m_createdOn;

    /** Holds value of property objectId. */
    @XmlElement(name="objectId")
    @Column(name="OBJECT_ID")    
    private java.lang.String m_objectId;

    /** Holds value of property objectName. */
    @XmlElement(name="objectName")
    @Column(name="OBJECT_NAME")    
    private java.lang.String m_objectName;

    /** Holds value of property changeType. */
    @XmlElement(name="changeType")
    @Column(name="CHANGE_TYPE")    
    private java.lang.String m_changeType;

    /** Holds value of property fieldId. */
    @XmlElement(name="fieldId")
    @Column(name="FIELD_ID")    
    private java.lang.String m_fieldId;

    /** Holds value of property fieldName. */
    @XmlElement(name="fieldName")
    @Column(name="FIELD_NAME")    
    private java.lang.String m_fieldName;

    /** Holds value of property fromValue. */
    @XmlElement(name="fromValue")
    @Column(name="FROM_VALUE")    
    private java.lang.String m_fromValue;

    /** Holds value of property toValue. */
    @XmlElement(name="toValue")
    @Column(name="TO_VALUE")    
    private java.lang.String m_toValue;

    /** Holds value of property changed. */
    @XmlElement(name="changed")
    @Type(type="true_false")
    @Column(name="CHANGED")    
    private java.lang.Boolean m_changed;

    /** Holds value of property flex. */
    @XmlElement(name="flex")
    @Type(type="true_false")
    @Column(name="FLEX")    
    private java.lang.Boolean m_flex;

    /** Holds value of property overflowFlag. */
    @XmlElement(name="overflowFlag")
    @Type(type="true_false")
    @Column(name="OVERFLOW_FLAG")    
    private java.lang.Boolean m_overflowFlag;

    /** Holds value of property multilineHtmlFlag. */
    @XmlElement(name="multilineHtmlFlag")
    @Column(name="MULTILINE_HTML_FLAG")    
    private java.lang.String m_multilineHtmlFlag;

    /** Holds value of property pK. */
    @XmlElement(name="PK")
    @Id
    @Column(name="PK")    
    private java.lang.String m_pK;


    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public AuditTransactionView() {
        StaticContext.configure(this);
    }

    // .//GEN-END:2_be
    // .//GEN-BEGIN:transactionId_be
    /** Getter for property transactionId.
     * @return Value of property transactionId.
     */
    public java.lang.String getTransactionId() {
        return m_transactionId;
    }
    
    /** Setter for property transactionId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param transactionId New value of property transactionId.
     */
    public void setTransactionId(java.lang.String transactionId) {
        m_transactionId = transactionId;
    }
    // .//GEN-END:transactionId_be
    // .//GEN-BEGIN:processName_be
    /** Getter for property processName.
     * @return Value of property processName.
     */
    public java.lang.String getProcessName() {
        return m_processName;
    }
    
    /** Setter for property processName.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param processName New value of property processName.
     */
    public void setProcessName(java.lang.String processName) {
        m_processName = processName;
    }
    // .//GEN-END:processName_be
    // .//GEN-BEGIN:subProcessName_be
    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public java.lang.String getSubProcessName() {
        return m_subProcessName;
    }
    
    /** Setter for property subProcessName.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param subProcessName New value of property subProcessName.
     */
    public void setSubProcessName(java.lang.String subProcessName) {
        m_subProcessName = subProcessName;
    }
    // .//GEN-END:subProcessName_be
    // .//GEN-BEGIN:reason_be
    /** Getter for property reason.
     * @return Value of property reason.
     */
    public java.lang.String getReason() {
        return m_reason;
    }
    
    /** Setter for property reason.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param reason New value of property reason.
     */
    public void setReason(java.lang.String reason) {
        m_reason = reason;
    }
    // .//GEN-END:reason_be
    // .//GEN-BEGIN:createdBy_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return m_createdBy;
    }
    
    /** Setter for property createdBy.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        m_createdBy = createdBy;
    }
    // .//GEN-END:createdBy_be
    // .//GEN-BEGIN:createdOn_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }
    
    /** Setter for property createdOn.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        m_createdOn = createdOn;
    }
    // .//GEN-END:createdOn_be
    // .//GEN-BEGIN:objectId_be
    /** Getter for property objectId.
     * @return Value of property objectId.
     */
    public java.lang.String getObjectId() {
        return m_objectId;
    }
    
    /** Setter for property objectId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param objectId New value of property objectId.
     */
    public void setObjectId(java.lang.String objectId) {
        m_objectId = objectId;
    }
    // .//GEN-END:objectId_be
    // .//GEN-BEGIN:objectName_be
    /** Getter for property objectName.
     * @return Value of property objectName.
     */
    public java.lang.String getObjectName() {
        return m_objectName;
    }
    
    /** Setter for property objectName.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param objectName New value of property objectName.
     */
    public void setObjectName(java.lang.String objectName) {
        m_objectName = objectName;
    }
    // .//GEN-END:objectName_be
    // .//GEN-BEGIN:changeType_be
    /** Getter for property changeType.
     * @return Value of property changeType.
     */
    public java.lang.String getChangeType() {
        return m_changeType;
    }
    
    /** Setter for property changeType.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param changeType New value of property changeType.
     */
    public void setChangeType(java.lang.String changeType) {
        m_changeType = changeType;
    }
    // .//GEN-END:changeType_be
    // .//GEN-BEGIN:fieldId_be
    /** Getter for property fieldId.
     * @return Value of property fieldId.
     */
    public java.lang.String getFieldId() {
        return m_fieldId;
    }
    
    /** Setter for property fieldId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param fieldId New value of property fieldId.
     */
    public void setFieldId(java.lang.String fieldId) {
        m_fieldId = fieldId;
    }
    // .//GEN-END:fieldId_be
    // .//GEN-BEGIN:fieldName_be
    /** Getter for property fieldName.
     * @return Value of property fieldName.
     */
    public java.lang.String getFieldName() {
        return m_fieldName;
    }
    
    /** Setter for property fieldName.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param fieldName New value of property fieldName.
     */
    public void setFieldName(java.lang.String fieldName) {
        m_fieldName = fieldName;
    }
    // .//GEN-END:fieldName_be
    // .//GEN-BEGIN:fromValue_be
    /** Getter for property fromValue.
     * @return Value of property fromValue.
     */
    public java.lang.String getFromValue() {
        return m_fromValue;
    }
    
    /** Setter for property fromValue.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param fromValue New value of property fromValue.
     */
    public void setFromValue(java.lang.String fromValue) {
        m_fromValue = fromValue;
    }
    // .//GEN-END:fromValue_be
    // .//GEN-BEGIN:toValue_be
    /** Getter for property toValue.
     * @return Value of property toValue.
     */
    public java.lang.String getToValue() {
        return m_toValue;
    }
    
    /** Setter for property toValue.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param toValue New value of property toValue.
     */
    public void setToValue(java.lang.String toValue) {
        m_toValue = toValue;
    }
    // .//GEN-END:toValue_be
    // .//GEN-BEGIN:changed_be
    /** Getter for property changed.
     * @return Value of property changed.
     */
    public java.lang.Boolean getChanged() {
        return m_changed;
    }
    
    /** Setter for property changed.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param changed New value of property changed.
     */
    public void setChanged(java.lang.Boolean changed) {
        m_changed = changed;
    }
    // .//GEN-END:changed_be
    // .//GEN-BEGIN:flex_be
    /** Getter for property flex.
     * @return Value of property flex.
     */
    public java.lang.Boolean getFlex() {
        return m_flex;
    }
    
    /** Setter for property flex.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param flex New value of property flex.
     */
    public void setFlex(java.lang.Boolean flex) {
        m_flex = flex;
    }
    // .//GEN-END:flex_be
    // .//GEN-BEGIN:overflowFlag_be
    /** Getter for property overflowFlag.
     * @return Value of property overflowFlag.
     */
    public java.lang.Boolean getOverflowFlag() {
        return m_overflowFlag;
    }
    
    /** Setter for property overflowFlag.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param overflowFlag New value of property overflowFlag.
     */
    public void setOverflowFlag(java.lang.Boolean overflowFlag) {
        m_overflowFlag = overflowFlag;
    }
    // .//GEN-END:overflowFlag_be
    // .//GEN-BEGIN:multilineHtmlFlag_be
    /** Getter for property multilineHtmlFlag.
     * @return Value of property multilineHtmlFlag.
     */
    public java.lang.String getMultilineHtmlFlag() {
        return m_multilineHtmlFlag;
    }
    
    /** Setter for property multilineHtmlFlag.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param multilineHtmlFlag New value of property multilineHtmlFlag.
     */
    public void setMultilineHtmlFlag(java.lang.String multilineHtmlFlag) {
        m_multilineHtmlFlag = multilineHtmlFlag;
    }
    // .//GEN-END:multilineHtmlFlag_be
    // .//GEN-BEGIN:pK_be
    /** Getter for property pK.
     * @return Value of property pK.
     */
    public java.lang.String getPK() {
        return m_pK;
    }
    
    /** Setter for property pK.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param pK New value of property pK.
     */
    public void setPK(java.lang.String pK) {
        m_pK = pK;
    }
    // .//GEN-END:pK_be
    // .//GEN-BEGIN:3_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AuditTransactionView>");
        buf.append("<transactionId>"); if (m_transactionId != null) buf.append(m_transactionId); buf.append("</transactionId>");
        buf.append("<processName>"); if (m_processName != null) buf.append(m_processName); buf.append("</processName>");
        buf.append("<subProcessName>"); if (m_subProcessName != null) buf.append(m_subProcessName); buf.append("</subProcessName>");
        buf.append("<reason>"); if (m_reason != null) buf.append(m_reason); buf.append("</reason>");
        buf.append("<createdBy>"); if (m_createdBy != null) buf.append(m_createdBy); buf.append("</createdBy>");
        buf.append("<createdOn>"); if (m_createdOn != null) buf.append(m_createdOn); buf.append("</createdOn>");
        buf.append("<objectId>"); if (m_objectId != null) buf.append(m_objectId); buf.append("</objectId>");
        buf.append("<objectName>"); if (m_objectName != null) buf.append(m_objectName); buf.append("</objectName>");
        buf.append("<changeType>"); if (m_changeType != null) buf.append(m_changeType); buf.append("</changeType>");
        buf.append("<fieldId>"); if (m_fieldId != null) buf.append(m_fieldId); buf.append("</fieldId>");
        buf.append("<fieldName>"); if (m_fieldName != null) buf.append(m_fieldName); buf.append("</fieldName>");
        buf.append("<fromValue>"); if (m_fromValue != null) buf.append(m_fromValue); buf.append("</fromValue>");
        buf.append("<toValue>"); if (m_toValue != null) buf.append(m_toValue); buf.append("</toValue>");
        buf.append("<changed>"); if (m_changed != null) buf.append(m_changed); buf.append("</changed>");
        buf.append("<flex>"); if (m_flex != null) buf.append(m_flex); buf.append("</flex>");
        buf.append("<overflowFlag>"); if (m_overflowFlag != null) buf.append(m_overflowFlag); buf.append("</overflowFlag>");
        buf.append("<multilineHtmlFlag>"); if (m_multilineHtmlFlag != null) buf.append(m_multilineHtmlFlag); buf.append("</multilineHtmlFlag>");
        buf.append("<pK>"); if (m_pK != null) buf.append(m_pK); buf.append("</pK>");
        buf.append(super.toString());
        buf.append("</AuditTransactionView>");
        return buf.toString();
    }

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        super.validate();
    }

    // .//GEN-LAST:custom
}
