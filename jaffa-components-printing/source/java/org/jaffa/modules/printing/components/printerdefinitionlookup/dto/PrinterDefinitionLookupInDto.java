// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printerdefinitionlookup.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the PrinterDefinitionLookup.
 */
public class PrinterDefinitionLookupInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property printerId. */
    private StringCriteriaField printerId;

    /** Holds value of property description. */
    private StringCriteriaField description;

    /** Holds value of property siteCode. */
    private StringCriteriaField siteCode;

    /** Holds value of property locationCode. */
    private StringCriteriaField locationCode;

    /** Holds value of property realPrinterName. */
    private StringCriteriaField realPrinterName;

    /** Holds value of property outputType. */
    private StringCriteriaField outputType;

    /** Holds value of property scaleToPageSize. */
    private StringCriteriaField scaleToPageSize;

    /** Holds value of property remote. */
    private BooleanCriteriaField remote;



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
    public StringCriteriaField getPrinterId() {
        return printerId;
    }
    
    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(StringCriteriaField printerId) {
        this.printerId = printerId;
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

    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public StringCriteriaField getSiteCode() {
        return siteCode;
    }
    
    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(StringCriteriaField siteCode) {
        this.siteCode = siteCode;
    }

    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public StringCriteriaField getLocationCode() {
        return locationCode;
    }
    
    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(StringCriteriaField locationCode) {
        this.locationCode = locationCode;
    }

    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public StringCriteriaField getRealPrinterName() {
        return realPrinterName;
    }
    
    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(StringCriteriaField realPrinterName) {
        this.realPrinterName = realPrinterName;
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

    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public StringCriteriaField getScaleToPageSize() {
        return scaleToPageSize;
    }
    
    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(StringCriteriaField scaleToPageSize) {
        this.scaleToPageSize = scaleToPageSize;
    }

    /** Getter for property remote.
     * @return Value of property remote.
     */
    public BooleanCriteriaField getRemote() {
        return remote;
    }
    
    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(BooleanCriteriaField remote) {
        this.remote = remote;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterDefinitionLookupInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<printerId>"); if (printerId != null) buf.append(printerId); buf.append("</printerId>");
        buf.append("<description>"); if (description != null) buf.append(description); buf.append("</description>");
        buf.append("<siteCode>"); if (siteCode != null) buf.append(siteCode); buf.append("</siteCode>");
        buf.append("<locationCode>"); if (locationCode != null) buf.append(locationCode); buf.append("</locationCode>");
        buf.append("<realPrinterName>"); if (realPrinterName != null) buf.append(realPrinterName); buf.append("</realPrinterName>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<scaleToPageSize>"); if (scaleToPageSize != null) buf.append(scaleToPageSize); buf.append("</scaleToPageSize>");
        buf.append("<remote>"); if (remote != null) buf.append(remote); buf.append("</remote>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</PrinterDefinitionLookupInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
