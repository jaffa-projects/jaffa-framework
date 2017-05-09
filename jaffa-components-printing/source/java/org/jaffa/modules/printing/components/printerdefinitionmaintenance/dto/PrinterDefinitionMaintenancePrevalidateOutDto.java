// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the PrinterDefinitionMaintenance prevalidations.
 */
public class PrinterDefinitionMaintenancePrevalidateOutDto extends PrinterDefinitionMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterDefinitionMaintenancePrevalidateOutDto>");
        buf.append("<printerId>"); if (getPrinterId() != null) buf.append(getPrinterId()); buf.append("</printerId>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<siteCode>"); if (getSiteCode() != null) buf.append(getSiteCode()); buf.append("</siteCode>");
        buf.append("<locationCode>"); if (getLocationCode() != null) buf.append(getLocationCode()); buf.append("</locationCode>");
        buf.append("<remote>"); if (getRemote() != null) buf.append(getRemote()); buf.append("</remote>");
        buf.append("<realPrinterName>"); if (getRealPrinterName() != null) buf.append(getRealPrinterName()); buf.append("</realPrinterName>");
        buf.append("<printerOption1>"); if (getPrinterOption1() != null) buf.append(getPrinterOption1()); buf.append("</printerOption1>");
        buf.append("<printerOption2>"); if (getPrinterOption2() != null) buf.append(getPrinterOption2()); buf.append("</printerOption2>");
        buf.append("<outputType>"); if (getOutputType() != null) buf.append(getOutputType()); buf.append("</outputType>");
        buf.append("<scaleToPageSize>"); if (getScaleToPageSize() != null) buf.append(getScaleToPageSize()); buf.append("</scaleToPageSize>");

        buf.append("</PrinterDefinitionMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
