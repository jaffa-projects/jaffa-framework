// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the ItemFinder.
 */
public class ItemFinderInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property segregationCode. */
    private StringCriteriaField segregationCode;

    /** Holds value of property partNo. */
    private StringCriteriaField partNo;

    /** Holds value of property serial. */
    private StringCriteriaField serial;

    /** Holds value of property qty. */
    private DecimalCriteriaField qty;



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
    

    /** Getter for property segregationCode.
     * @return Value of property segregationCode.
     */
    public StringCriteriaField getSegregationCode() {
        return segregationCode;
    }
    
    /** Setter for property segregationCode.
     * @param segregationCode New value of property segregationCode.
     */
    public void setSegregationCode(StringCriteriaField segregationCode) {
        this.segregationCode = segregationCode;
    }

    /** Getter for property partNo.
     * @return Value of property partNo.
     */
    public StringCriteriaField getPartNo() {
        return partNo;
    }
    
    /** Setter for property partNo.
     * @param partNo New value of property partNo.
     */
    public void setPartNo(StringCriteriaField partNo) {
        this.partNo = partNo;
    }

    /** Getter for property serial.
     * @return Value of property serial.
     */
    public StringCriteriaField getSerial() {
        return serial;
    }
    
    /** Setter for property serial.
     * @param serial New value of property serial.
     */
    public void setSerial(StringCriteriaField serial) {
        this.serial = serial;
    }

    /** Getter for property qty.
     * @return Value of property qty.
     */
    public DecimalCriteriaField getQty() {
        return qty;
    }
    
    /** Setter for property qty.
     * @param qty New value of property qty.
     */
    public void setQty(DecimalCriteriaField qty) {
        this.qty = qty;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<ItemFinderInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<segregationCode>"); if (segregationCode != null) buf.append(segregationCode); buf.append("</segregationCode>");
        buf.append("<partNo>"); if (partNo != null) buf.append(partNo); buf.append("</partNo>");
        buf.append("<serial>"); if (serial != null) buf.append(serial); buf.append("</serial>");
        buf.append("<qty>"); if (qty != null) buf.append(qty); buf.append("</qty>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</ItemFinderInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
