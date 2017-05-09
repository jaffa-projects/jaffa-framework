// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.outputcommandfinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the OutputCommandFinder.
 */
public class OutputCommandFinderInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property outputCommandId. */
    private IntegerCriteriaField outputCommandId;

    /** Holds value of property outputType. */
    private StringCriteriaField outputType;

    /** Holds value of property sequenceNo. */
    private IntegerCriteriaField sequenceNo;

    /** Holds value of property osPattern. */
    private StringCriteriaField osPattern;

    /** Holds value of property commandLine. */
    private StringCriteriaField commandLine;

    /** Holds value of property createdOn. */
    private DateTimeCriteriaField createdOn;

    /** Holds value of property createdBy. */
    private StringCriteriaField createdBy;

    /** Holds value of property lastChangedOn. */
    private DateTimeCriteriaField lastChangedOn;

    /** Holds value of property lastChangedBy. */
    private StringCriteriaField lastChangedBy;



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
    public IntegerCriteriaField getOutputCommandId() {
        return outputCommandId;
    }
    
    /** Setter for property outputCommandId.
     * @param outputCommandId New value of property outputCommandId.
     */
    public void setOutputCommandId(IntegerCriteriaField outputCommandId) {
        this.outputCommandId = outputCommandId;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public StringCriteriaField getOutputType() {
        return outputType;
    }
    
    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(StringCriteriaField outputType) {
        this.outputType = outputType;
    }

    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public IntegerCriteriaField getSequenceNo() {
        return sequenceNo;
    }
    
    /** Setter for property sequenceNo.
     * @param sequenceNo New value of property sequenceNo.
     */
    public void setSequenceNo(IntegerCriteriaField sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public StringCriteriaField getOsPattern() {
        return osPattern;
    }
    
    /** Setter for property osPattern.
     * @param osPattern New value of property osPattern.
     */
    public void setOsPattern(StringCriteriaField osPattern) {
        this.osPattern = osPattern;
    }

    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public StringCriteriaField getCommandLine() {
        return commandLine;
    }
    
    /** Setter for property commandLine.
     * @param commandLine New value of property commandLine.
     */
    public void setCommandLine(StringCriteriaField commandLine) {
        this.commandLine = commandLine;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }
    
    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }
    
    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTimeCriteriaField getLastChangedOn() {
        return lastChangedOn;
    }
    
    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTimeCriteriaField lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public StringCriteriaField getLastChangedBy() {
        return lastChangedBy;
    }
    
    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(StringCriteriaField lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<OutputCommandFinderInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<outputCommandId>"); if (outputCommandId != null) buf.append(outputCommandId); buf.append("</outputCommandId>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<sequenceNo>"); if (sequenceNo != null) buf.append(sequenceNo); buf.append("</sequenceNo>");
        buf.append("<osPattern>"); if (osPattern != null) buf.append(osPattern); buf.append("</osPattern>");
        buf.append("<commandLine>"); if (commandLine != null) buf.append(commandLine); buf.append("</commandLine>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (createdBy != null) buf.append(createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (lastChangedOn != null) buf.append(lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (lastChangedBy != null) buf.append(lastChangedBy); buf.append("</lastChangedBy>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</OutputCommandFinderInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
