// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the PrinterDefinitionMaintenance.
 */
public class PrinterDefinitionMaintenanceRetrieveInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property printerId. */
    private java.lang.String printerId;



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
    

    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public java.lang.String getPrinterId() {
        return printerId;
    }
    
    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(java.lang.String printerId) {
        if (printerId == null || printerId.length() == 0)
            this.printerId = null;
        else
            this.printerId = printerId;
    }





    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterDefinitionMaintenanceRetrieveInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<printerId>"); if (printerId != null) buf.append(printerId); buf.append("</printerId>");
        buf.append("</PrinterDefinitionMaintenanceRetrieveInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
