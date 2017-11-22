package org.jaffa.modules.messaging.apis.data;

import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;

/**
 * The Criteria Data Object, for specifying retrieve profiles for
 * a list of ValidFieldValue Domain Object Graphs.
 * <p>
 * This is a list of ValidFieldValue criteria fields, that when populated, will be used to
 * construct the query clause (see {@link #returnQueryClause(Criteria)} for retrieving a set
 * of ValidFieldValue objects.
 */
public class BusinessEventLogCriteria extends GraphCriteria {

    private StringCriteriaField logId;
    private StringCriteriaField correlationType;
    private StringCriteriaField correlationKey1;
    private StringCriteriaField correlationKey2;
    private StringCriteriaField correlationKey3;
    private StringCriteriaField processName;
    private StringCriteriaField subProcessName;
    private StringCriteriaField messageType;
    private StringCriteriaField messageText;
    private StringCriteriaField sourceClass;
    private DateTimeCriteriaField loggedOn;
    private StringCriteriaField loggedBy;
    private StringCriteriaField scheduledTaskId;
    private StringCriteriaField messageId;

    /**
     * Returns the criteria object used for retrieving ValidFieldValue records.
     * @return the criteria object used for retrieving ValidFieldValue records.
     */
    @Override
    public Criteria returnQueryClause(Criteria c) {
        c = super.returnQueryClause(c);
        c.setTable(BusinessEventLogMeta.getName());
        FinderTx.addCriteria(getLogId(), BusinessEventLogMeta.LOG_ID, c);
        FinderTx.addCriteria(getCorrelationType(), BusinessEventLogMeta.CORRELATION_TYPE, c);
        FinderTx.addCriteria(getCorrelationKey1(), BusinessEventLogMeta.CORRELATION_KEY1, c);
        FinderTx.addCriteria(getCorrelationKey2(), BusinessEventLogMeta.CORRELATION_KEY2, c);
        FinderTx.addCriteria(getCorrelationKey3(), BusinessEventLogMeta.CORRELATION_KEY3, c);
        FinderTx.addCriteria(getProcessName(), BusinessEventLogMeta.PROCESS_NAME, c);
        FinderTx.addCriteria(getSubProcessName(), BusinessEventLogMeta.SUB_PROCESS_NAME, c);
        FinderTx.addCriteria(getMessageType(), BusinessEventLogMeta.MESSAGE_TYPE, c);
        FinderTx.addCriteria(getMessageText(), BusinessEventLogMeta.MESSAGE_TEXT, c);
        FinderTx.addCriteria(getSourceClass(), BusinessEventLogMeta.SOURCE_CLASS, c);
        FinderTx.addCriteria(getLoggedOn(), BusinessEventLogMeta.LOGGED_ON, c);
        FinderTx.addCriteria(getLoggedBy(), BusinessEventLogMeta.LOGGED_BY, c);
        FinderTx.addCriteria(getScheduledTaskId(), BusinessEventLogMeta.SCHEDULED_TASK_ID, c);
        FinderTx.addCriteria(getMessageId(), BusinessEventLogMeta.MESSAGE_ID, c);

        return c;
    }

    /**
     * Get the value of logId
     *
     * @return the value of logId
     */
    public StringCriteriaField getLogId() {
        return logId;
    }

    /**
     * Set the value of logId
     *
     * @param logId new value of logId
     */
    public void setLogId(StringCriteriaField logId) {
        this.logId = logId;
    }

    /**
     * @return the  correlationType
     */
    public StringCriteriaField getCorrelationType() {
        return  correlationType;
    }

    /**
     * @param  correlationType the  correlationType to set
     */
    public void setCorrelationType(StringCriteriaField correlationType) {
        this. correlationType =  correlationType;
    }

    /**
     * @return the correlationKey1
     */
    public StringCriteriaField getCorrelationKey1() {
        return correlationKey1;
    }

    /**
     * @param correlationKey1 the correlationKey1 to set
     */
    public void setCorrelationKey1(StringCriteriaField correlationKey1) {
        this.correlationKey1 = correlationKey1;
    }

    /**
     * @return the correlationKey2
     */
    public StringCriteriaField getCorrelationKey2() {
        return correlationKey2;
    }

    /**
     * @param correlationKey2 the correlationKey2 to set
     */
    public void setCorrelationKey2(StringCriteriaField correlationKey2) {
        this.correlationKey2 = correlationKey2;
    }

    /**
     * @return the correlationKey3
     */
    public StringCriteriaField getCorrelationKey3() {
        return correlationKey3;
    }

    /**
     * @param correlationKey3 the correlationKey3 to set
     */
    public void setCorrelationKey3(StringCriteriaField correlationKey3) {
        this.correlationKey3 = correlationKey3;
    }

    /**
     * @return the processName
     */
    public StringCriteriaField getProcessName() {
        return processName;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(StringCriteriaField processName) {
        this.processName = processName;
    }

    /**
     * @param subProcessName the subProcessName to set
     */
    public void setSubProcessName(StringCriteriaField subProcessName) {
        this.subProcessName = subProcessName;
    }

    /**
     * @return the subProcessName
     */
    public StringCriteriaField getSubProcessName() {
        return subProcessName;
    }

    /**
     * Get the value of messageType
     *
     * @return the value of messageType
     */
    public StringCriteriaField getMessageType() {
        return messageType;
    }

    /**
     * Set the value of messageType
     *
     * @param messageType new value of messageType
     */
    public void setMessageType(StringCriteriaField messageType) {
        this.messageType = messageType;
    }

    /**
     * Get the value of messageText
     *
     * @return the value of messageText
     */
    public StringCriteriaField getMessageText() {
        return messageText;
    }

    /**
     * Set the value of messageText
     *
     * @param messageText new value of messageText
     */
    public void setMessageText(StringCriteriaField messageText) {
        this.messageText = messageText;
    }

    /**
     * Get the value of sourceClass
     *
     * @return the value of sourceClass
     */
    public StringCriteriaField getSourceClass() {
        return sourceClass;
    }

    /**
     * Set the value of sourceClass
     *
     * @param sourceClass new value of sourceClass
     */
    public void setSourceClass(StringCriteriaField sourceClass) {
        this.sourceClass = sourceClass;
    }

    /**
     * Get the value of loggedOn
     *
     * @return the value of loggedOn
     */
    public DateTimeCriteriaField getLoggedOn() {
        return loggedOn;
    }

    /**
     * Set the value of loggedOn
     *
     * @param loggedOn new value of loggedOn
     */
    public void setLoggedOn(DateTimeCriteriaField loggedOn) {
        this.loggedOn = loggedOn;
    }

    /**
     * Get the value of loggedBy
     *
     * @return the value of loggedBy
     */
    public StringCriteriaField getLoggedBy() {
        return loggedBy;
    }

    /**
     * Set the value of loggedBy
     *
     * @param loggedBy new value of loggedBy
     */
    public void setLoggedBy(StringCriteriaField loggedBy) {
        this.loggedBy = loggedBy;
    }

    /**
     * Get the value of scheduledTaskId
     *
     * @return the value of scheduledTaskId
     */
    public StringCriteriaField getScheduledTaskId() {
        return scheduledTaskId;
    }

    /**
     * Set the value of scheduledTaskId
     *
     * @param scheduledTaskId new value of scheduledTaskId
     */
    public void setScheduledTaskId(StringCriteriaField scheduledTaskId) {
        this.scheduledTaskId = scheduledTaskId;
    }

    /**
     * Get the value of messageId
     *
     * @return the value of messageId
     */
    public StringCriteriaField getMessageId() {
        return messageId;
    }

    /**
     * Set the value of messageId
     *
     * @param messageId new value of messageId
     */
    public void setMessageId(StringCriteriaField messageId) {
        this.messageId = messageId;
    }

}
