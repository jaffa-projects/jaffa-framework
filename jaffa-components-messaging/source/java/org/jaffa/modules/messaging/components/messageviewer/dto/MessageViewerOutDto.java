package org.jaffa.modules.messaging.components.messageviewer.dto;

import org.jaffa.datatypes.DateTime;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;

/** The output for the MessageViewer.
 */
public class MessageViewerOutDto{
    
    /** Holds value of property JMSMessageID. */
    private java.lang.String JMSMessageID;
    
    /** Holds value of property error. */
    private java.lang.String error;
    
    /** Holds value of property priority. */
    private java.lang.Long priority;
    
    /** Holds an array of HeaderElement objects. */
    private HeaderElementDto[] headerElements;
    
    /** Holds value of property JMSDestination. */
    private javax.jms.Destination JMSDestination;
    
    /** Holds value of property JMSDeliveryMode. */
    private java.lang.Integer JMSDeliveryMode;
    
    /** Holds value of property JMSTimestamp. */
    private DateTime JMSTimestamp;
    
    /** Holds value of property JMSCorrelationID. */
    private java.lang.String JMSCorrelationID;
    
    /** Holds value of property JMSReplyTo. */
    private javax.jms.Destination JMSReplyTo;
    
    /** Holds value of property JMSRedelivered. */
    private java.lang.Boolean JMSRedelivered;
    
    /** Holds value of property JMSType. */
    private java.lang.String JMSType;
    
    /** Holds value of property JMSExpiration. */
    private java.lang.Long JMSExpiration;
    
    /** Holds value of property payLoad. */
    private java.lang.String payLoad;
    
    /** Holds value of property hasPriorityAccess. */
    private java.lang.Boolean hasPriorityAccess;
    
    /** Holds an array of BusinessEventLog objects to be returned. */
    private BusinessEventLogFinderOutDto businessEventLogDto;
    
    
    /**
     * Getter for property JMSMessageID.
     * @return Value of property JMSMessageID.
     */
    public java.lang.String getJMSMessageID() {
        return JMSMessageID;
    }
    
    /** Setter for property JMSMessageID.
     * @param JMSMessageID New value of property JMSMessageID.
     */
    public void setJMSMessageID(java.lang.String JMSMessageID) {
        this.JMSMessageID = JMSMessageID;
    }
    
    /**
     * Getter for property error.
     * @return Value of property error.
     */
    public java.lang.String getError() {
        return error;
    }
    
    /** Setter for property error.
     * @param error New value of property error.
     */
    public void setError(java.lang.String error) {
        this.error = error;
    }
    
    /**
     * Getter for property priority.
     * @return Value of property priority.
     */
    public java.lang.Long getPriority() {
        return priority;
    }
    
    /** Setter for property priority.
     * @param priority New value of property priority.
     */
    public void setPriority(java.lang.Long priority) {
        this.priority = priority;
    }
    
    /** Returns an array of HeaderElement objects.
     * @return An array of HeaderElement objects.
     */
    public HeaderElementDto[] getHeaderElements() {
        return headerElements;
    }
    
    /** Add an array of HeaderElement objects. This will overwrite existing HeaderElement objects.
     * @param objects An array of HeaderElement objects.
     */
    public void setHeaderElements(HeaderElementDto[] headerElements) {
        this.headerElements = headerElements;
    }
    
    /**
     * Getter for property JMSDestination.
     * @return Value of property JMSDestination.
     */
    public javax.jms.Destination getJMSDestination() {
        return JMSDestination;
    }
    
    /** Setter for property JMSDestination.
     * @param JMSDestination New value of property JMSDestination.
     */
    public void setJMSDestination(javax.jms.Destination JMSDestination) {
        this.JMSDestination = JMSDestination;
    }
    
    /**
     * Getter for property JMSDeliveryMode.
     * @return Value of property JMSDeliveryMode.
     */
    public java.lang.Integer getJMSDeliveryMode() {
        return JMSDeliveryMode;
    }
    
    /** Setter for property JMSDeliveryMode.
     * @param JMSDeliveryMode New value of property JMSDeliveryMode.
     */
    public void setJMSDeliveryMode(java.lang.Integer JMSDeliveryMode) {
        this.JMSDeliveryMode = JMSDeliveryMode;
    }
    
    /**
     * Getter for property JMSTimestamp.
     * @return Value of property JMSTimestamp.
     */
    public DateTime getJMSTimestamp() {
        return JMSTimestamp;
    }
    
    /** Setter for property JMSTimestamp.
     * @param JMSTimestamp New value of property JMSTimestamp.
     */
    public void setJMSTimestamp(DateTime JMSTimestamp) {
        this.JMSTimestamp = JMSTimestamp;
    }
    
    /**
     * Getter for property JMSCorrelationID.
     * @return Value of property JMSCorrelationID.
     */
    public java.lang.String getJMSCorrelationID() {
        return JMSCorrelationID;
    }
    
    /** Setter for property JMSCorrelationID.
     * @param JMSCorrelationID New value of property JMSCorrelationID.
     */
    public void setJMSCorrelationID(java.lang.String JMSCorrelationID) {
        this.JMSCorrelationID = JMSCorrelationID;
    }
    
    /**
     * Getter for property JMSReplyTo.
     * @return Value of property JMSReplyTo.
     */
    public javax.jms.Destination getJMSReplyTo() {
        return JMSReplyTo;
    }
    
    /** Setter for property JMSReplyTo.
     * @param JMSReplyTo New value of property JMSReplyTo.
     */
    public void setJMSReplyTo(javax.jms.Destination JMSReplyTo) {
        this.JMSReplyTo = JMSReplyTo;
    }
    
    /**
     * Getter for property JMSRedelivered.
     * @return Value of property JMSRedelivered.
     */
    public java.lang.Boolean getJMSRedelivered() {
        return JMSRedelivered;
    }
    
    /** Setter for property JMSRedelivered.
     * @param JMSRedelivered New value of property JMSRedelivered.
     */
    public void setJMSRedelivered(java.lang.Boolean JMSRedelivered) {
        this.JMSRedelivered = JMSRedelivered;
    }
    
    /**
     * Getter for property JMSType.
     * @return Value of property JMSType.
     */
    public java.lang.String getJMSType() {
        return JMSType;
    }
    
    /** Setter for property JMSType.
     * @param JMSType New value of property JMSType.
     */
    public void setJMSType(java.lang.String JMSType) {
        this.JMSType = JMSType;
    }
    
    /**
     * Getter for property JMSExpiration.
     * @return Value of property JMSExpiration.
     */
    public java.lang.Long getJMSExpiration() {
        return JMSExpiration;
    }
    
    /** Setter for property JMSExpiration.
     * @param JMSExpiration New value of property JMSExpiration.
     */
    public void setJMSExpiration(java.lang.Long JMSExpiration) {
        this.JMSExpiration = JMSExpiration;
    }
    
    /**
     * Getter for property payLoad.
     * @return Value of property payLoad.
     */
    public java.lang.String getPayLoad() {
        return payLoad;
    }
    
    /** Setter for property payLoad.
     * @param payLoad New value of property payLoad.
     */
    public void setPayLoad(java.lang.String payLoad) {
        this.payLoad = payLoad;
    }
    
    /** Getter for property hasPriorityAccess.
     * @return Value of property hasPriorityAccess.
     */
    public java.lang.Boolean getHasPriorityAccess() {
        return hasPriorityAccess;
    }
    
    /** Setter for property hasPriorityAccess.
     * @param hasPriorityAccess New value of property hasPriorityAccess.
     */
    public void setHasPriorityAccess(java.lang.Boolean hasPriorityAccess) {
        this.hasPriorityAccess = hasPriorityAccess;
    }
    
    /** Getter for property businessEventLogDto.
     * @return Value of property businessEventLogDto.
     */
    public BusinessEventLogFinderOutDto getBusinessEventLog() {
        return businessEventLogDto;
    }
    
    /** Setter for property businessEventLogDto.
     * @param businessEventLogDto New value of property businessEventLogDto.
     */
    public void setBusinessEventLog(BusinessEventLogFinderOutDto businessEventLogDto) {
        this.businessEventLogDto = businessEventLogDto;
    }
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<QueueViewerOutDto>");
        buf.append("<JMSMessageID>"); if (getJMSMessageID() != null) buf.append(getJMSMessageID()); buf.append("</JMSMessageID>");
        buf.append("<error>"); if (getError() != null) buf.append(getError()); buf.append("</error>");
        buf.append("<priority>"); if (getPriority() != null) buf.append(getPriority()); buf.append("</priority>");
        
        buf.append("<headerElements>");
        if (headerElements != null) {
            for (HeaderElementDto headerElement : headerElements)
                buf.append(headerElement);
        }
        buf.append("</headerElements>");
        
        buf.append("<JMSDestination>"); if (getJMSDestination() != null) buf.append(getJMSDestination()); buf.append("</JMSDestination>");
        buf.append("<JMSDeliveryMode>"); if (getJMSDeliveryMode() != null) buf.append(getJMSDeliveryMode()); buf.append("</JMSDeliveryMode>");
        buf.append("<JMSTimestamp>"); if (getJMSTimestamp() != null) buf.append(getJMSTimestamp()); buf.append("</JMSTimestamp>");
        buf.append("<JMSCorrelationID>"); if (getJMSCorrelationID() != null) buf.append(getJMSCorrelationID()); buf.append("</JMSCorrelationID>");
        buf.append("<JMSReplyTo>"); if (getJMSReplyTo() != null) buf.append(getJMSReplyTo()); buf.append("</JMSReplyTo>");
        buf.append("<JMSRedelivered>"); if (getJMSRedelivered() != null) buf.append(getJMSRedelivered()); buf.append("</JMSRedelivered>");
        buf.append("<JMSType>"); if (getJMSType() != null) buf.append(getJMSType()); buf.append("</JMSType>");
        buf.append("<JMSExpiration>"); if (getJMSExpiration() != null) buf.append(getJMSExpiration()); buf.append("</JMSExpiration>");
        buf.append("<payLoad>"); if (getPayLoad() != null) buf.append(getPayLoad()); buf.append("</payLoad>");
        buf.append("<hasPriorityAccess>"); if (hasPriorityAccess != null) buf.append(hasPriorityAccess); buf.append("</hasPriorityAccess>");
        
        buf.append("<businessEventLogs>");
        buf.append(getBusinessEventLog().toString());
        buf.append("</businessEventLogs>");
        
        buf.append("</QueueViewerOutDto>");
        return buf.toString();
    }
}
