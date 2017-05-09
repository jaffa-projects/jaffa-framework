package org.jaffa.modules.messaging.components.queueviewer.dto;

/** The output for the QueueViewer.
 */
public class QueueViewerOutDto {
    
    /** Holds value of property hasAdminAccess. */
    private java.lang.Boolean hasAdminAccess;
    
    /** Holds an array of HeaderElement objects. */
    private HeaderElementDto[] headerElements;
    
    /** Holds an array of MessageDto objects. */
    private MessageDto[] messages;
    
    
    /** Getter for property hasAdminAccess.
     * @return Value of property hasAdminAccess.
     */
    public java.lang.Boolean getHasAdminAccess() {
        return hasAdminAccess;
    }
    
    /** Setter for property hasAdminAccess.
     * @param hasAdminAccess New value of property hasAdminAccess.
     */
    public void setHasAdminAccess(java.lang.Boolean hasAdminAccess) {
        this.hasAdminAccess = hasAdminAccess;
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
    
    /** Returns an array of MessageDto objects.
     * @return An array of MessageDto objects.
     */
    public MessageDto[] getMessages() {
        return messages;
    }
    
    /** Add an array of MessageDto objects. This will overwrite existing MessageDto objects.
     * @param objects An array of MessageDto objects.
     */
    public void setMessages(MessageDto[] messages) {
        this.messages = messages;
    }
    
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<QueueViewerOutDto>");
        buf.append("<hasAdminAccess>"); if (hasAdminAccess != null) buf.append(hasAdminAccess); buf.append("</hasAdminAccess>");
        buf.append("<headerElements>");
        if (headerElements != null) {
            for (HeaderElementDto headerElement : headerElements)
                buf.append(headerElement);
        }
        buf.append("</headerElements>");
        buf.append("<messages>");
        if (messages != null) {
            for (MessageDto message : messages)
                buf.append(message);
        }
        buf.append("</messages>");
        buf.append("</QueueViewerOutDto>");
        return buf.toString();
    }
}
