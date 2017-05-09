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
/** The output for the PrinterOutputTypeMaintenance prevalidations.
 */
public class PrinterOutputTypeMaintenancePrevalidateOutDto extends PrinterOutputTypeMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterOutputTypeMaintenancePrevalidateOutDto>");
        buf.append("<outputType>"); if (getOutputType() != null) buf.append(getOutputType()); buf.append("</outputType>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<directPrinting>"); if (getDirectPrinting() != null) buf.append(getDirectPrinting()); buf.append("</directPrinting>");
        buf.append("<createdOn>"); if (getCreatedOn() != null) buf.append(getCreatedOn()); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (getCreatedBy() != null) buf.append(getCreatedBy()); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (getLastChangedOn() != null) buf.append(getLastChangedOn()); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (getLastChangedBy() != null) buf.append(getLastChangedBy()); buf.append("</lastChangedBy>");

        buf.append("<outputCommands>");
        OutputCommandDto[] outputCommands = getOutputCommand();
        for (int i = 0; i < outputCommands.length; i++) {
            buf.append(outputCommands[i].toString());
        }
        buf.append("</outputCommands>");

        buf.append("</PrinterOutputTypeMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
