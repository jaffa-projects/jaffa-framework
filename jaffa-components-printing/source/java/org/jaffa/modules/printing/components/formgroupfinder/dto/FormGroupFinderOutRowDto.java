// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormGroupFinder contains an array of instances of this class.
 */
public class FormGroupFinderOutRowDto {

    /** Holds value of property formName. */
    private java.lang.String formName;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property formType. */
    private java.lang.String formType;


    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return formName;
    }
    
    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        if (formName == null || formName.length() == 0)
            this.formName = null;
        else
            this.formName = formName;
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

    /** Getter for property formType.
     * @return Value of property formType.
     */
    public java.lang.String getFormType() {
        return formType;
    }
    
    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(java.lang.String formType) {
        if (formType == null || formType.length() == 0)
            this.formType = null;
        else
            this.formType = formType;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormGroupFinderOutRowDto>");
        buf.append("<formName>"); if (formName != null) buf.append(StringHelper.convertToHTML(Formatter.format(formName))); buf.append("</formName>");
        buf.append("<description>"); if (description != null) buf.append(StringHelper.convertToHTML(Formatter.format(description))); buf.append("</description>");
        buf.append("<formType>"); if (formType != null) buf.append(StringHelper.convertToHTML(Formatter.format(formType))); buf.append("</formType>");
        buf.append("</FormGroupFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
