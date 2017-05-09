package org.jaffa.modules.messaging.components.messageviewer.dto;

import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;

/** The input for the MessageViewer.
 */
public class MessageViewerInDto {
    
    /** Holds value of property JMSMessageID. */
    private java.lang.String JMSMessageID;
    
    /** Holds value of property queue. */
    private java.lang.String queue;
    
    /** Holds value of property queue. */
    private MessageModeEnum messageMode;
    
    
    /** Getter for property JMSMessageID.
     * @return Value of property JMSMessageID.
     */
    public java.lang.String getJMSMessageID() {
        return JMSMessageID;
    }
    
    /** Setter for property JMSMessageID.
     * @param JMSMessageID New value of property JMSMessageID.
     */
    public void setJMSMessageID(java.lang.String JMSMessageID) {
        if (JMSMessageID == null || JMSMessageID.length() == 0)
            this.JMSMessageID = null;
        else
            this.JMSMessageID = JMSMessageID;
    }
    
    /** Getter for property queue.
     * @return Value of property queue.
     */
    public java.lang.String getQueue() {
        return queue;
    }
    
    /** Setter for property queue.
     * @param queue New value of property queue.
     */
    public void setQueue(java.lang.String queue) {
        if (queue == null || queue.length() == 0)
            this.queue = null;
        else
            this.queue = queue;
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
        buf.append("<MessageViewerInDto>");
        buf.append("<JMSMessageID>"); if (JMSMessageID != null) buf.append(JMSMessageID); buf.append("</JMSMessageID>");
        buf.append("<queue>"); if (queue != null) buf.append(queue); buf.append("</queue>");
        buf.append("<messageMode>"); if (messageMode != null) buf.append(messageMode); buf.append("</messageMode>");
        buf.append("</MessageViewerInDto>");
        return buf.toString();
    }
}
