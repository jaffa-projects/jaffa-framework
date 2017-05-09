// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printerdefinitionviewer.dto;

import java.util.*;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the PrinterDefinitionViewer.
 */
public class PrinterDefinitionViewerOutDto {

    /** Holds value of property printerId. */
    private java.lang.String printerId;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property siteCode. */
    private java.lang.String siteCode;

    /** Holds value of property locationCode. */
    private java.lang.String locationCode;

    /** Holds value of property remote. */
    private java.lang.Boolean remote;

    /** Holds value of property realPrinterName. */
    private java.lang.String realPrinterName;

    /** Holds value of property printerOption1. */
    private java.lang.String printerOption1;

    /** Holds value of property printerOption2. */
    private java.lang.String printerOption2;

    /** Holds value of property outputType. */
    private java.lang.String outputType;

    /** Holds value of property scaleToPageSize. */
    private java.lang.String scaleToPageSize;


    /** Default Constructor.*/    
    public PrinterDefinitionViewerOutDto() {
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

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        if (description == null || description.length() == 0)
            this.description = null;
        else
            this.description = description;
    }

    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public java.lang.String getSiteCode() {
        return siteCode;
    }
    
    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(java.lang.String siteCode) {
        if (siteCode == null || siteCode.length() == 0)
            this.siteCode = null;
        else
            this.siteCode = siteCode;
    }

    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public java.lang.String getLocationCode() {
        return locationCode;
    }
    
    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(java.lang.String locationCode) {
        if (locationCode == null || locationCode.length() == 0)
            this.locationCode = null;
        else
            this.locationCode = locationCode;
    }

    /** Getter for property remote.
     * @return Value of property remote.
     */
    public java.lang.Boolean getRemote() {
        return remote;
    }
    
    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(java.lang.Boolean remote) {
        this.remote = remote;
    }

    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public java.lang.String getRealPrinterName() {
        return realPrinterName;
    }
    
    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(java.lang.String realPrinterName) {
        if (realPrinterName == null || realPrinterName.length() == 0)
            this.realPrinterName = null;
        else
            this.realPrinterName = realPrinterName;
    }

    /** Getter for property printerOption1.
     * @return Value of property printerOption1.
     */
    public java.lang.String getPrinterOption1() {
        return printerOption1;
    }
    
    /** Setter for property printerOption1.
     * @param printerOption1 New value of property printerOption1.
     */
    public void setPrinterOption1(java.lang.String printerOption1) {
        if (printerOption1 == null || printerOption1.length() == 0)
            this.printerOption1 = null;
        else
            this.printerOption1 = printerOption1;
    }

    /** Getter for property printerOption2.
     * @return Value of property printerOption2.
     */
    public java.lang.String getPrinterOption2() {
        return printerOption2;
    }
    
    /** Setter for property printerOption2.
     * @param printerOption2 New value of property printerOption2.
     */
    public void setPrinterOption2(java.lang.String printerOption2) {
        if (printerOption2 == null || printerOption2.length() == 0)
            this.printerOption2 = null;
        else
            this.printerOption2 = printerOption2;
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

    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public java.lang.String getScaleToPageSize() {
        return scaleToPageSize;
    }
    
    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(java.lang.String scaleToPageSize) {
        if (scaleToPageSize == null || scaleToPageSize.length() == 0)
            this.scaleToPageSize = null;
        else
            this.scaleToPageSize = scaleToPageSize;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterDefinitionViewerOutDto>");
        buf.append("<printerId>"); if (printerId != null) buf.append(printerId); buf.append("</printerId>");
        buf.append("<description>"); if (description != null) buf.append(description); buf.append("</description>");
        buf.append("<siteCode>"); if (siteCode != null) buf.append(siteCode); buf.append("</siteCode>");
        buf.append("<locationCode>"); if (locationCode != null) buf.append(locationCode); buf.append("</locationCode>");
        buf.append("<remote>"); if (remote != null) buf.append(remote); buf.append("</remote>");
        buf.append("<realPrinterName>"); if (realPrinterName != null) buf.append(realPrinterName); buf.append("</realPrinterName>");
        buf.append("<printerOption1>"); if (printerOption1 != null) buf.append(printerOption1); buf.append("</printerOption1>");
        buf.append("<printerOption2>"); if (printerOption2 != null) buf.append(printerOption2); buf.append("</printerOption2>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<scaleToPageSize>"); if (scaleToPageSize != null) buf.append(scaleToPageSize); buf.append("</scaleToPageSize>");

        buf.append("</PrinterDefinitionViewerOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
