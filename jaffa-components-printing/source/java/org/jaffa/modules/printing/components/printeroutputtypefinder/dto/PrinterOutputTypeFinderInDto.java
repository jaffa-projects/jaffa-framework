// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypefinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the PrinterOutputTypeFinder.
 */
public class PrinterOutputTypeFinderInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property outputType. */
    private StringCriteriaField outputType;

    /** Holds value of property description. */
    private StringCriteriaField description;

    /** Holds value of property directPrinting. */
    private BooleanCriteriaField directPrinting;



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

    /** Getter for property description.
     * @return Value of property description.
     */
    public StringCriteriaField getDescription() {
        return description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(StringCriteriaField description) {
        this.description = description;
    }

    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public BooleanCriteriaField getDirectPrinting() {
        return directPrinting;
    }
    
    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(BooleanCriteriaField directPrinting) {
        this.directPrinting = directPrinting;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterOutputTypeFinderInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<description>"); if (description != null) buf.append(description); buf.append("</description>");
        buf.append("<directPrinting>"); if (directPrinting != null) buf.append(directPrinting); buf.append("</directPrinting>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</PrinterOutputTypeFinderInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
