// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the BusinessEventLogFinder contains an array of instances of this class.
 */
public class BusinessEventLogFinderOutRowDto {

    /** Holds value of property logId. */
    private java.lang.String logId;

    /** Holds value of property correlationType. */
    private java.lang.String correlationType;

    /** Holds value of property correlationKey1. */
    private java.lang.String correlationKey1;

    /** Holds value of property correlationKey2. */
    private java.lang.String correlationKey2;

    /** Holds value of property correlationKey3. */
    private java.lang.String correlationKey3;

    /** Holds value of property scheduledTaskId. */
    private java.lang.String scheduledTaskId;

    /** Holds value of property messageId. */
    private java.lang.String messageId;

    /** Holds value of property loggedOn. */
    private org.jaffa.datatypes.DateTime loggedOn;

    /** Holds value of property loggedBy. */
    private java.lang.String loggedBy;

    /** Holds value of property processName. */
    private java.lang.String processName;

    /** Holds value of property subProcessName. */
    private java.lang.String subProcessName;

    /** Holds value of property messageType. */
    private java.lang.String messageType;

    /** Holds value of property messageText. */
    private java.lang.String messageText;

    /** Holds value of property sourceClass. */
    private java.lang.String sourceClass;

    /** Holds value of property sourceMethod. */
    private java.lang.String sourceMethod;

    /** Holds value of property sourceLine. */
    private java.lang.Long sourceLine;

    /** Holds value of property stackTrace. */
    private java.lang.String stackTrace;


    /** Getter for property logId.
     * @return Value of property logId.
     */
    public java.lang.String getLogId() {
        return logId;
    }
    
    /** Setter for property logId.
     * @param logId New value of property logId.
     */
    public void setLogId(java.lang.String logId) {
        if (logId == null || logId.length() == 0)
            this.logId = null;
        else
            this.logId = logId;
    }

    /** Getter for property correlationType.
     * @return Value of property correlationType.
     */
    public java.lang.String getCorrelationType() {
        return correlationType;
    }
    
    /** Setter for property correlationType.
     * @param correlationType New value of property correlationType.
     */
    public void setCorrelationType(java.lang.String correlationType) {
        if (correlationType == null || correlationType.length() == 0)
            this.correlationType = null;
        else
            this.correlationType = correlationType;
    }

    /** Getter for property correlationKey1.
     * @return Value of property correlationKey1.
     */
    public java.lang.String getCorrelationKey1() {
        return correlationKey1;
    }
    
    /** Setter for property correlationKey1.
     * @param correlationKey1 New value of property correlationKey1.
     */
    public void setCorrelationKey1(java.lang.String correlationKey1) {
        if (correlationKey1 == null || correlationKey1.length() == 0)
            this.correlationKey1 = null;
        else
            this.correlationKey1 = correlationKey1;
    }

    /** Getter for property correlationKey2.
     * @return Value of property correlationKey2.
     */
    public java.lang.String getCorrelationKey2() {
        return correlationKey2;
    }
    
    /** Setter for property correlationKey2.
     * @param correlationKey2 New value of property correlationKey2.
     */
    public void setCorrelationKey2(java.lang.String correlationKey2) {
        if (correlationKey2 == null || correlationKey2.length() == 0)
            this.correlationKey2 = null;
        else
            this.correlationKey2 = correlationKey2;
    }

    /** Getter for property correlationKey3.
     * @return Value of property correlationKey3.
     */
    public java.lang.String getCorrelationKey3() {
        return correlationKey3;
    }
    
    /** Setter for property correlationKey3.
     * @param correlationKey3 New value of property correlationKey3.
     */
    public void setCorrelationKey3(java.lang.String correlationKey3) {
        if (correlationKey3 == null || correlationKey3.length() == 0)
            this.correlationKey3 = null;
        else
            this.correlationKey3 = correlationKey3;
    }

    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public java.lang.String getScheduledTaskId() {
        return scheduledTaskId;
    }
    
    /** Setter for property scheduledTaskId.
     * @param scheduledTaskId New value of property scheduledTaskId.
     */
    public void setScheduledTaskId(java.lang.String scheduledTaskId) {
        if (scheduledTaskId == null || scheduledTaskId.length() == 0)
            this.scheduledTaskId = null;
        else
            this.scheduledTaskId = scheduledTaskId;
    }

    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public java.lang.String getMessageId() {
        return messageId;
    }
    
    /** Setter for property messageId.
     * @param messageId New value of property messageId.
     */
    public void setMessageId(java.lang.String messageId) {
        if (messageId == null || messageId.length() == 0)
            this.messageId = null;
        else
            this.messageId = messageId;
    }

    /** Getter for property loggedOn.
     * @return Value of property loggedOn.
     */
    public org.jaffa.datatypes.DateTime getLoggedOn() {
        return loggedOn;
    }
    
    /** Setter for property loggedOn.
     * @param loggedOn New value of property loggedOn.
     */
    public void setLoggedOn(org.jaffa.datatypes.DateTime loggedOn) {
        this.loggedOn = loggedOn;
    }

    /** Getter for property loggedBy.
     * @return Value of property loggedBy.
     */
    public java.lang.String getLoggedBy() {
        return loggedBy;
    }
    
    /** Setter for property loggedBy.
     * @param loggedBy New value of property loggedBy.
     */
    public void setLoggedBy(java.lang.String loggedBy) {
        if (loggedBy == null || loggedBy.length() == 0)
            this.loggedBy = null;
        else
            this.loggedBy = loggedBy;
    }

    /** Getter for property processName.
     * @return Value of property processName.
     */
    public java.lang.String getProcessName() {
        return processName;
    }
    
    /** Setter for property processName.
     * @param processName New value of property processName.
     */
    public void setProcessName(java.lang.String processName) {
        if (processName == null || processName.length() == 0)
            this.processName = null;
        else
            this.processName = processName;
    }

    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public java.lang.String getSubProcessName() {
        return subProcessName;
    }
    
    /** Setter for property subProcessName.
     * @param subProcessName New value of property subProcessName.
     */
    public void setSubProcessName(java.lang.String subProcessName) {
        if (subProcessName == null || subProcessName.length() == 0)
            this.subProcessName = null;
        else
            this.subProcessName = subProcessName;
    }

    /** Getter for property messageType.
     * @return Value of property messageType.
     */
    public java.lang.String getMessageType() {
        return messageType;
    }
    
    /** Setter for property messageType.
     * @param messageType New value of property messageType.
     */
    public void setMessageType(java.lang.String messageType) {
        if (messageType == null || messageType.length() == 0)
            this.messageType = null;
        else
            this.messageType = messageType;
    }

    /** Getter for property messageText.
     * @return Value of property messageText.
     */
    public java.lang.String getMessageText() {
        return messageText;
    }
    
    /** Setter for property messageText.
     * @param messageText New value of property messageText.
     */
    public void setMessageText(java.lang.String messageText) {
        if (messageText == null || messageText.length() == 0)
            this.messageText = null;
        else
            this.messageText = messageText;
    }

    /** Getter for property sourceClass.
     * @return Value of property sourceClass.
     */
    public java.lang.String getSourceClass() {
        return sourceClass;
    }
    
    /** Setter for property sourceClass.
     * @param sourceClass New value of property sourceClass.
     */
    public void setSourceClass(java.lang.String sourceClass) {
        if (sourceClass == null || sourceClass.length() == 0)
            this.sourceClass = null;
        else
            this.sourceClass = sourceClass;
    }

    /** Getter for property sourceMethod.
     * @return Value of property sourceMethod.
     */
    public java.lang.String getSourceMethod() {
        return sourceMethod;
    }
    
    /** Setter for property sourceMethod.
     * @param sourceMethod New value of property sourceMethod.
     */
    public void setSourceMethod(java.lang.String sourceMethod) {
        if (sourceMethod == null || sourceMethod.length() == 0)
            this.sourceMethod = null;
        else
            this.sourceMethod = sourceMethod;
    }

    /** Getter for property sourceLine.
     * @return Value of property sourceLine.
     */
    public java.lang.Long getSourceLine() {
        return sourceLine;
    }
    
    /** Setter for property sourceLine.
     * @param sourceLine New value of property sourceLine.
     */
    public void setSourceLine(java.lang.Long sourceLine) {
        this.sourceLine = sourceLine;
    }

    /** Getter for property stackTrace.
     * @return Value of property stackTrace.
     */
    public java.lang.String getStackTrace() {
        return stackTrace;
    }
    
    /** Setter for property stackTrace.
     * @param stackTrace New value of property stackTrace.
     */
    public void setStackTrace(java.lang.String stackTrace) {
        if (stackTrace == null || stackTrace.length() == 0)
            this.stackTrace = null;
        else
            this.stackTrace = stackTrace;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<BusinessEventLogFinderOutRowDto>");
        buf.append("<logId>"); if (logId != null) buf.append(StringHelper.convertToHTML(Formatter.format(logId))); buf.append("</logId>");
        buf.append("<correlationType>"); if (correlationType != null) buf.append(StringHelper.convertToHTML(Formatter.format(correlationType))); buf.append("</correlationType>");
        buf.append("<correlationKey1>"); if (correlationKey1 != null) buf.append(StringHelper.convertToHTML(Formatter.format(correlationKey1))); buf.append("</correlationKey1>");
        buf.append("<correlationKey2>"); if (correlationKey2 != null) buf.append(StringHelper.convertToHTML(Formatter.format(correlationKey2))); buf.append("</correlationKey2>");
        buf.append("<correlationKey3>"); if (correlationKey3 != null) buf.append(StringHelper.convertToHTML(Formatter.format(correlationKey3))); buf.append("</correlationKey3>");
        buf.append("<scheduledTaskId>"); if (scheduledTaskId != null) buf.append(StringHelper.convertToHTML(Formatter.format(scheduledTaskId))); buf.append("</scheduledTaskId>");
        buf.append("<messageId>"); if (messageId != null) buf.append(StringHelper.convertToHTML(Formatter.format(messageId))); buf.append("</messageId>");
        buf.append("<loggedOn>"); if (loggedOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(loggedOn))); buf.append("</loggedOn>");
        buf.append("<loggedBy>"); if (loggedBy != null) buf.append(StringHelper.convertToHTML(Formatter.format(loggedBy))); buf.append("</loggedBy>");
        buf.append("<processName>"); if (processName != null) buf.append(StringHelper.convertToHTML(Formatter.format(processName))); buf.append("</processName>");
        buf.append("<subProcessName>"); if (subProcessName != null) buf.append(StringHelper.convertToHTML(Formatter.format(subProcessName))); buf.append("</subProcessName>");
        buf.append("<messageType>"); if (messageType != null) buf.append(StringHelper.convertToHTML(Formatter.format(messageType))); buf.append("</messageType>");
        buf.append("<messageText>"); if (messageText != null) buf.append(StringHelper.convertToHTML(Formatter.format(messageText))); buf.append("</messageText>");
        buf.append("<sourceClass>"); if (sourceClass != null) buf.append(StringHelper.convertToHTML(Formatter.format(sourceClass))); buf.append("</sourceClass>");
        buf.append("<sourceMethod>"); if (sourceMethod != null) buf.append(StringHelper.convertToHTML(Formatter.format(sourceMethod))); buf.append("</sourceMethod>");
        buf.append("<sourceLine>"); if (sourceLine != null) buf.append(StringHelper.convertToHTML(Formatter.format(sourceLine))); buf.append("</sourceLine>");
        buf.append("<stackTrace>"); if (stackTrace != null) buf.append(StringHelper.convertToHTML(Formatter.format(stackTrace))); buf.append("</stackTrace>");
        buf.append("</BusinessEventLogFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
