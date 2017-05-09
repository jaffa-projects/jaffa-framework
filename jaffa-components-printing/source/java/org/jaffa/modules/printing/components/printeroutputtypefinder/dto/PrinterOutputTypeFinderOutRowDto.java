// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypefinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the PrinterOutputTypeFinder contains an array of instances of this class.
 */
public class PrinterOutputTypeFinderOutRowDto {

    /** Holds value of property outputType. */
    private java.lang.String outputType;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property directPrinting. */
    private java.lang.Boolean directPrinting;


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

    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public java.lang.Boolean getDirectPrinting() {
        return directPrinting;
    }
    
    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(java.lang.Boolean directPrinting) {
        this.directPrinting = directPrinting;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<PrinterOutputTypeFinderOutRowDto>");
        buf.append("<outputType>"); if (outputType != null) buf.append(StringHelper.convertToHTML(Formatter.format(outputType))); buf.append("</outputType>");
        buf.append("<description>"); if (description != null) buf.append(StringHelper.convertToHTML(Formatter.format(description))); buf.append("</description>");
        buf.append("<directPrinting>"); if (directPrinting != null) buf.append(StringHelper.convertToHTML(Formatter.format(directPrinting))); buf.append("</directPrinting>");
        buf.append("</PrinterOutputTypeFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
