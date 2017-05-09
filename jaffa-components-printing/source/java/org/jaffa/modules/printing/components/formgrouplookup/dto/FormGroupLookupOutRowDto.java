// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgrouplookup.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormGroupLookup contains an array of instances of this class.
 */
public class FormGroupLookupOutRowDto {

    /** Holds value of property formName. */
    private java.lang.String formName;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property formType. */
    private java.lang.String formType;

    /** Holds value of property createdOn. */
    private org.jaffa.datatypes.DateTime createdOn;

    /** Holds value of property createdBy. */
    private java.lang.String createdBy;

    /** Holds value of property lastChangedOn. */
    private org.jaffa.datatypes.DateTime lastChangedOn;

    /** Holds value of property lastChangedBy. */
    private java.lang.String lastChangedBy;


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

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return createdOn;
    }
    
    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        this.createdOn = createdOn;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return createdBy;
    }
    
    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        if (createdBy == null || createdBy.length() == 0)
            this.createdBy = null;
        else
            this.createdBy = createdBy;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return lastChangedOn;
    }
    
    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return lastChangedBy;
    }
    
    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        if (lastChangedBy == null || lastChangedBy.length() == 0)
            this.lastChangedBy = null;
        else
            this.lastChangedBy = lastChangedBy;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormGroupLookupOutRowDto>");
        buf.append("<formName>"); if (formName != null) buf.append(StringHelper.convertToHTML(Formatter.format(formName))); buf.append("</formName>");
        buf.append("<description>"); if (description != null) buf.append(StringHelper.convertToHTML(Formatter.format(description))); buf.append("</description>");
        buf.append("<formType>"); if (formType != null) buf.append(StringHelper.convertToHTML(Formatter.format(formType))); buf.append("</formType>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(createdOn))); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (createdBy != null) buf.append(StringHelper.convertToHTML(Formatter.format(createdBy))); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (lastChangedOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastChangedOn))); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (lastChangedBy != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastChangedBy))); buf.append("</lastChangedBy>");
        buf.append("</FormGroupLookupOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
