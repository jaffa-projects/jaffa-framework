package org.jaffa.modules.messaging.components.queueviewer.dto;

import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;

/** The input for the QueueViewer.
 */
public class QueueViewerInDto {
    
    /** Holds value of property queueName. */
    private java.lang.String queueName;
    
    /** Holds value of property filter. */
    private java.lang.String filter;
    
    /** Holds value of property queue. */
    private MessageModeEnum messageMode;
    
    
    
    /** Getter for property queueName.
     * @return Value of property queueName.
     */
    public java.lang.String getQueueName() {
        return queueName;
    }
    
    /** Setter for property queueName.
     * @param queueName New value of property queueName.
     */
    public void setQueueName(java.lang.String queueName) {
        if (queueName == null || queueName.length() == 0)
            this.queueName = null;
        else
            this.queueName = queueName;
    }
    
    /** Getter for property filter.
     * @return Value of property filter.
     */
    public java.lang.String getFilter() {
        return filter;
    }
    
    /** Setter for property filter.
     * @param filter New value of property filter.
     */
    public void setFilter(java.lang.String filter) {
        if (filter == null || filter.length() == 0)
            this.filter = null;
        else
            this.filter = filter;
    }
    
    /** Getter for property messageMode.
     * @return Value of property messageMode.
     */
    public MessageModeEnum getMessageMode() {
        return messageMode;
    }
    
    /** Setter for property messageMode.
     * @param messageMode New value of property messageMode.
     */
    public void setMessageMode(MessageModeEnum messageMode) {
        this.messageMode = messageMode;
    }
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<QueueViewerInDto>");
        buf.append("<queueName>"); if (queueName != null) buf.append(queueName); buf.append("</queueName>");
        buf.append("<filter>"); if (filter != null) buf.append(filter); buf.append("</filter>");
        buf.append("<messageMode>"); if (messageMode != null) buf.append(messageMode); buf.append("</messageMode>");
        buf.append("</QueueViewerInDto>");
        return buf.toString();
    }
}
