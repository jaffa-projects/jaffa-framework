// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.outputcommandmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the OutputCommandMaintenance.
 */
public class OutputCommandMaintenanceCreateInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property outputCommandId. */
    private java.lang.Long outputCommandId;

    /** Holds value of property sequenceNo. */
    private java.lang.Long sequenceNo;

    /** Holds value of property osPattern. */
    private java.lang.String osPattern;

    /** Holds value of property commandLine. */
    private java.lang.String commandLine;

    /** Holds value of property outputType. */
    private java.lang.String outputType;



    /** Getter for property headerDto.
     * @return Value of property headerDto.
     */
    public HeaderDto getHeaderDto() {
        return headerDto;
    }
    
    /** Setter for property headerDto.
     * @param headerDto New value of property headerDto.
     */
    public void setHeaderDto(HeaderDto headerDto) {
        this.headerDto = headerDto;
    }

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



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<OutputCommandMaintenanceCreateInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<outputCommandId>"); if (outputCommandId != null) buf.append(outputCommandId); buf.append("</outputCommandId>");
        buf.append("<sequenceNo>"); if (sequenceNo != null) buf.append(sequenceNo); buf.append("</sequenceNo>");
        buf.append("<osPattern>"); if (osPattern != null) buf.append(osPattern); buf.append("</osPattern>");
        buf.append("<commandLine>"); if (commandLine != null) buf.append(commandLine); buf.append("</commandLine>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");

        buf.append("</OutputCommandMaintenanceCreateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
