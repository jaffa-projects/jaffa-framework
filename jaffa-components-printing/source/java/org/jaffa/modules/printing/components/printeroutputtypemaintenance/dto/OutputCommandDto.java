// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The related object returned by the PrinterOutputTypeMaintenance.
 */
public class OutputCommandDto {

    /** Holds value of property outputCommandId. */
    private java.lang.Long outputCommandId;

    /** Holds value of property outputType. */
    private java.lang.String outputType;

    /** Holds value of property sequenceNo. */
    private java.lang.Long sequenceNo;

    /** Holds value of property osPattern. */
    private java.lang.String osPattern;

    /** Holds value of property commandLine. */
    private java.lang.String commandLine;

    /** Holds value of property createdOn. */
    private org.jaffa.datatypes.DateTime createdOn;

    /** Holds value of property createdBy. */
    private java.lang.String createdBy;

    /** Holds value of property lastChangedOn. */
    private org.jaffa.datatypes.DateTime lastChangedOn;

    /** Holds value of property lastChangedBy. */
    private java.lang.String lastChangedBy;

    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public java.lang.Long getOutputCommandId() {
        return outputCommandId;
    }
    
    /** Setter for property outputCommandId.
     * @param outputCommandId New value of property outputCommandId.
     */
    public void setOutputCommandId(java.lang.Long outputCommandId) {
        this.outputCommandId = outputCommandId;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return outputType;
    }
    
    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        if (outputType == null || outputType.length() == 0)
            this.outputType = null;
        else
            this.outputType = outputType;
    }

    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public java.lang.Long getSequenceNo() {
        return sequenceNo;
    }
    
    /** Setter for property sequenceNo.
     * @param sequenceNo New value of property sequenceNo.
     */
    public void setSequenceNo(java.lang.Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public java.lang.String getOsPattern() {
        return osPattern;
    }
    
    /** Setter for property osPattern.
     * @param osPattern New value of property osPattern.
     */
    public void setOsPattern(java.lang.String osPattern) {
        if (osPattern == null || osPattern.length() == 0)
            this.osPattern = null;
        else
            this.osPattern = osPattern;
    }

    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public java.lang.String getCommandLine() {
        return commandLine;
    }
    
    /** Setter for property commandLine.
     * @param commandLine New value of property commandLine.
     */
    public void setCommandLine(java.lang.String commandLine) {
        if (commandLine == null || commandLine.length() == 0)
            this.commandLine = null;
        else
            this.commandLine = commandLine;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return createdOn;
    }
    
    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        this.createdOn = createdOn;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return createdBy;
    }
    
    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        if (createdBy == null || createdBy.length() == 0)
            this.createdBy = null;
        else
            this.createdBy = createdBy;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return lastChangedOn;
    }
    
    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return lastChangedBy;
    }
    
    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        if (lastChangedBy == null || lastChangedBy.length() == 0)
            this.lastChangedBy = null;
        else
            this.lastChangedBy = lastChangedBy;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<OutputCommandDto>");
        buf.append("<outputCommandId>"); if (outputCommandId != null) buf.append(outputCommandId); buf.append("</outputCommandId>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<sequenceNo>"); if (sequenceNo != null) buf.append(sequenceNo); buf.append("</sequenceNo>");
        buf.append("<osPattern>"); if (osPattern != null) buf.append(osPattern); buf.append("</osPattern>");
        buf.append("<commandLine>"); if (commandLine != null) buf.append(commandLine); buf.append("</commandLine>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (createdBy != null) buf.append(createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (lastChangedOn != null) buf.append(lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (lastChangedBy != null) buf.append(lastChangedBy); buf.append("</lastChangedBy>");
        buf.append("</OutputCommandDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}

