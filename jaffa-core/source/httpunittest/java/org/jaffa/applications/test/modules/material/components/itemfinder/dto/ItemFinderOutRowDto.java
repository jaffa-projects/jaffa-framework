// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the ItemFinder contains an array of instances of this class.
 */
public class ItemFinderOutRowDto {

    /** Holds value of property segregationCode. */
    private String segregationCode;

    /** Holds value of property itemId. */
    private String itemId;

    /** Holds value of property partNo. */
    private String partNo;

    /** Holds value of property serial. */
    private String serial;

    /** Holds value of property qty. */
    private Double qty;


    /** Getter for property segregationCode.
     * @return Value of property segregationCode.
     */
    public String getSegregationCode() {
        return segregationCode;
    }
    
    /** Setter for property segregationCode.
     * @param segregationCode New value of property segregationCode.
     */
    public void setSegregationCode(String segregationCode) {
        if (segregationCode == null || segregationCode.length() == 0)
            this.segregationCode = null;
        else
            this.segregationCode = segregationCode;
    }

    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public String getItemId() {
        return itemId;
    }
    
    /** Setter for property itemId.
     * @param itemId New value of property itemId.
     */
    public void setItemId(String itemId) {
        if (itemId == null || itemId.length() == 0)
            this.itemId = null;
        else
            this.itemId = itemId;
    }

    /** Getter for property partNo.
     * @return Value of property partNo.
     */
    public String getPartNo() {
        return partNo;
    }
    
    /** Setter for property partNo.
     * @param partNo New value of property partNo.
     */
    public void setPartNo(String partNo) {
        if (partNo == null || partNo.length() == 0)
            this.partNo = null;
        else
            this.partNo = partNo;
    }

    /** Getter for property serial.
     * @return Value of property serial.
     */
    public String getSerial() {
        return serial;
    }
    
    /** Setter for property serial.
     * @param serial New value of property serial.
     */
    public void setSerial(String serial) {
        if (serial == null || serial.length() == 0)
            this.serial = null;
        else
            this.serial = serial;
    }

    /** Getter for property qty.
     * @return Value of property qty.
     */
    public Double getQty() {
        return qty;
    }
    
    /** Setter for property qty.
     * @param qty New value of property qty.
     */
    public void setQty(Double qty) {
        this.qty = qty;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<ItemFinderOutRowDto>");
        buf.append("<segregationCode>"); if (segregationCode != null) buf.append(StringHelper.convertToHTML(Formatter.format(segregationCode))); buf.append("</segregationCode>");
        buf.append("<itemId>"); if (itemId != null) buf.append(StringHelper.convertToHTML(Formatter.format(itemId))); buf.append("</itemId>");
        buf.append("<partNo>"); if (partNo != null) buf.append(StringHelper.convertToHTML(Formatter.format(partNo))); buf.append("</partNo>");
        buf.append("<serial>"); if (serial != null) buf.append(StringHelper.convertToHTML(Formatter.format(serial))); buf.append("</serial>");
        buf.append("<qty>"); if (qty != null) buf.append(StringHelper.convertToHTML(Formatter.format(qty))); buf.append("</qty>");
        buf.append("</ItemFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
