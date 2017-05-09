package org.jaffa.modules.messaging.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DateTime;
import org.jaffa.soa.graph.GraphDataObject;

/**
 * The ValidFieldValue Data Object.
 * <p/>
 * This models the data held in the ValidFieldValue record.
 * <p/>
 *
 * @version 1.0
 */
public class BusinessEventLogGraph extends GraphDataObject {

    private String logId;
    private String correlationType;
    private String correlationKey1;
    private String correlationKey2;
    private String correlationKey3;
    private DateTime loggedOn;
    private String loggedBy;
    private String processName;
    private String subProcessName;
    private String messageType;
    private String messageText;
    private String sourceClass;
    private String stackTrace;
    private String messageId;
    private String scheduledTaskId;

    /**
     * Default constructor.
     */
    public BusinessEventLogGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property logId.
     *
     * @return Value of property logId.
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Setter for property logId.
     *
     * @param logId Value of property logId.
     */
    public void setLogId(String logId) {
        String oldLogId = this.logId;
        this.logId = logId;
        propertyChangeSupport.firePropertyChange("logId", oldLogId, logId);
    }

    /**
     * Getter for property correlationType.
     *
     * @return Value of property correlationType.
     */
    public String getCorrelationType() {
        return correlationType;
    }

    /**
     * Setter for property correlationType.
     *
     * @param correlationType Value of property correlationType.
     */
    public void setCorrelationType(String correlationType) {
        String oldCorrelationType = this.correlationType;
        this.correlationType = correlationType;
        propertyChangeSupport.firePropertyChange("correlationType", oldCorrelationType, correlationType);
    }

    /**
     * Getter for property correlationKey1.
     *
     * @return Value of property correlationKey1.
     */
    public String getCorrelationKey1() {
        return correlationKey1;
    }

    /**
     * Setter for property correlationKey1.
     *
     * @param correlationKey1 Value of property correlationKey1.
     */
    public void setCorrelationKey1(String correlationKey1) {
        String oldCorrelationKey1 = this.correlationKey1;
        this.correlationKey1 = correlationKey1;
        propertyChangeSupport.firePropertyChange("correlationKey1", oldCorrelationKey1, correlationKey1);
    }

    /**
     * Getter for property correlationKey2.
     *
     * @return Value of property correlationKey2.
     */
    public String getCorrelationKey2() {
        return correlationKey2;
    }

    /**
     * Setter for property correlationKey2.
     *
     * @param correlationKey2 Value of property correlationKey2.
     */
    public void setCorrelationKey2(String correlationKey2) {
        String oldCorrelationKey2 = this.correlationKey2;
        this.correlationKey2 = correlationKey2;
        propertyChangeSupport.firePropertyChange("correlationKey2", oldCorrelationKey2, correlationKey2);
    }

    /**
     * Getter for property correlationKey3.
     *
     * @return Value of property correlationKey3.
     */
    public String getCorrelationKey3() {
        return correlationKey3;
    }

    /**
     * Setter for property correlationKey3.
     *
     * @param correlationKey3 Value of property correlationKey3.
     */
    public void setCorrelationKey3(String correlationKey3) {
        String oldCorrelationKey3 = this.correlationKey3;
        this.correlationKey3 = correlationKey3;
        propertyChangeSupport.firePropertyChange("correlationKey3", oldCorrelationKey3, correlationKey3);
    }

    /**
     * Get the value of loggedOn
     *
     * @return the value of loggedOn
     */
    public DateTime getLoggedOn() {
        return loggedOn;
    }

    /**
     * Set the value of loggedOn
     *
     * @param loggedOn new value of loggedOn
     */
    public void setLoggedOn(DateTime loggedOn) {
        DateTime oldLoggedOn = this.loggedOn;
        this.loggedOn = loggedOn;
        propertyChangeSupport.firePropertyChange("loggedOn", oldLoggedOn, loggedOn);
    }

    /**
     * Get the value of loggedBy
     *
     * @return the value of loggedBy
     */
    public String getLoggedBy() {
        return loggedBy;
    }

    /**
     * Set the value of loggedBy
     *
     * @param loggedBy new value of loggedBy
     */
    public void setLoggedBy(String loggedBy) {
        String oldLoggedBy = this.loggedBy;
        this.loggedBy = loggedBy;
        propertyChangeSupport.firePropertyChange("loggedBy", oldLoggedBy, loggedBy);
    }

    /**
     * Get the value of processName
     *
     * @return the value of processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * Set the value of processName
     *
     * @param processName new value of processName
     */
    public void setProcessName(String processName) {
        String oldProcessName = this.processName;
        this.processName = processName;
        propertyChangeSupport.firePropertyChange("processName", oldProcessName, processName);
    }

    /**
     * Get the value of subProcessName
     *
     * @return the value of subProcessName
     */
    public String getSubProcessName() {
        return subProcessName;
    }

    /**
     * Set the value of subProcessName
     *
     * @param subProcessName new value of subProcessName
     */
    public void setSubProcessName(String subProcessName) {
        String oldSubProcessName = this.subProcessName;
        this.subProcessName = subProcessName;
        propertyChangeSupport.firePropertyChange("subProcessName", oldSubProcessName, subProcessName);
    }

    /**
     * Get the value of messageType
     *
     * @return the value of messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Set the value of messageType
     *
     * @param messageType new value of messageType
     */
    public void setMessageType(String messageType) {
        String oldMessageType = this.messageType;
        this.messageType = messageType;
        propertyChangeSupport.firePropertyChange("messageType", oldMessageType, messageType);
    }

    /**
     * Get the value of messageText
     *
     * @return the value of messageText
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Set the value of messageText
     *
     * @param messageText new value of messageText
     */
    public void setMessageText(String messageText) {
        String oldMessageText = this.messageText;
        this.messageText = messageText;
        propertyChangeSupport.firePropertyChange("messageText", oldMessageText, messageText);
    }

    /**
     * Get the value of sourceClass
     *
     * @return the value of sourceClass
     */
    public String getSourceClass() {
        return sourceClass;
    }

    /**
     * Set the value of sourceClass
     *
     * @param sourceClass new value of sourceClass
     */
    public void setSourceClass(String sourceClass) {
        String oldSourceClass = this.sourceClass;
        this.sourceClass = sourceClass;
        propertyChangeSupport.firePropertyChange("sourceClass", oldSourceClass, sourceClass);
    }

    /**
     * Getter for property stackTrace.
     *
     * @return Value of property stackTrace.
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * Setter for property stackTrace.
     *
     * @param stackTrace Value of property stackTrace.
     */
    public void setStackTrace(String stackTrace) {
        String oldStackTrace = this.stackTrace;
        this.stackTrace = stackTrace;
        propertyChangeSupport.firePropertyChange("stackTrace", oldStackTrace, stackTrace);
    }

    /**
     * Getter for property messageId.
     *
     * @return Value of property messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Setter for property messageId.
     *
     * @param stackTrace Value of property messageId.
     */
    public void setMessageId(String messageId) {
        String oldMessageId = this.messageId;
        this.messageId = messageId;
        propertyChangeSupport.firePropertyChange("messageId", oldMessageId, messageId);
    }

    /**
     * Getter for property scheduledTaskId.
     *
     * @return Value of property scheduledTaskId
     */
    public String getScheduledTaskId() {
        return scheduledTaskId;
    }

    /**
     * Setter for property scheduledTaskId.
     *
     * @param stackTrace Value of property scheduledTaskId.
     */
    public void setScheduledTaskId(String scheduledTaskId) {
        String oldScheduledTaskId = this.scheduledTaskId;
        this.scheduledTaskId = scheduledTaskId;
        propertyChangeSupport.firePropertyChange("scheduledTaskId", oldScheduledTaskId, scheduledTaskId);
    }

}
