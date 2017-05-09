package org.jaffa.modules.messaging.components.queueviewer.dto;

/** The related object returned by the QueueViewer.
 */
public class MessageDto {
    
    /** Holds value of property messageId. */
    private String messageId;
    
    /** Holds value of property manageable. */
    private Boolean manageable;
    
    /** Holds an array of HeaderElement objects. */
    private HeaderElementDto[] headerElements;
    
    
    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public String getMessageId() {
        return messageId;
    }
    
    /** Setter for property messageId.
     * @param messageId New value of property messageId.
     */
    public void setMessageId(String messageId) {
        if (messageId == null || messageId.length() == 0)
            this.messageId = null;
        else
            this.messageId = messageId;
    }
    
    /** Getter for property manageable.
     * @return Value of property manageable.
     */
    public Boolean getManageable() {
        return manageable;
    }
    
    /** Setter for property manageable.
     * @param manageable New value of property manageable.
     */
    public void setManageable(Boolean manageable) {
        this.manageable = manageable;
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
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<MessageDto>");
        buf.append("<messageId>"); if (messageId != null) buf.append(messageId); buf.append("</messageId>");
        buf.append("<manageable>"); if (manageable != null) buf.append(manageable); buf.append("</manageable>");
        buf.append("<headerElements>");
        if (headerElements != null) {
            for (HeaderElementDto headerElement : headerElements)
                buf.append(headerElement);
        }
        buf.append("</headerElements>");
        buf.append("</MessageDto>");
        return buf.toString();
    }
}

