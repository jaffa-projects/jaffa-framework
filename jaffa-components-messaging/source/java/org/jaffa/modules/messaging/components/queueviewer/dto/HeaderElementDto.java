package org.jaffa.modules.messaging.components.queueviewer.dto;

/** The related object returned by the QueueViewer.
 */
public class HeaderElementDto {
    
    /** Holds value of property Name. */
    private java.lang.String name;
    
    /** Holds value of property Label. */
    private java.lang.String label;
    
    /** Holds value of property Value. */
    private java.lang.String value;
    
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(java.lang.String name) {
        if (name == null || name.length() == 0)
            this.name = null;
        else
            this.name = name;
    }
    
    /** Getter for property label.
     * @return Value of property label.
     */
    public java.lang.String getLabel() {
        return label;
    }
    
    /** Setter for property label.
     * @param label New value of property label.
     */
    public void setLabel(java.lang.String label) {
        if (label == null || label.length() == 0)
            this.label = null;
        else
            this.label = label;
    }
    
    /** Getter for property value.
     * @return Value of property value.
     */
    public java.lang.String getValue() {
        return value;
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(java.lang.String value) {
        if (value == null || value.length() == 0)
            this.value = null;
        else
            this.value = value;
    }
    
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<HeaderElementDto>");
        buf.append("<name>"); if (name != null) buf.append(name); buf.append("</name>");
        buf.append("<label>"); if (label != null) buf.append(label); buf.append("</label>");
        buf.append("<value>"); if (value != null) buf.append(value); buf.append("</value>");
        buf.append("</HeaderElementDto>");
        return buf.toString();
    }
}

