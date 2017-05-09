// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormDefinitionFinder contains an array of instances of this class.
 */
public class FormDefinitionFinderOutRowDto {

    /** Holds value of property formId. */
    private java.lang.Long formId;

    /** Holds value of property formName. */
    private java.lang.String formName;

    /** Holds value of property formAlternate. */
    private java.lang.String formAlternate;

    /** Holds value of property formVariation. */
    private java.lang.String formVariation;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property formTemplate. */
    private java.lang.String formTemplate;

    /** Holds value of property outputType. */
    private java.lang.String outputType;


    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        return formId;
    }
    
    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(java.lang.Long formId) {
        this.formId = formId;
    }

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

    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return formAlternate;
    }
    
    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(java.lang.String formAlternate) {
        if (formAlternate == null || formAlternate.length() == 0)
            this.formAlternate = null;
        else
            this.formAlternate = formAlternate;
    }

    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public java.lang.String getFormVariation() {
        return formVariation;
    }
    
    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(java.lang.String formVariation) {
        if (formVariation == null || formVariation.length() == 0)
            this.formVariation = null;
        else
            this.formVariation = formVariation;
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

    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public java.lang.String getFormTemplate() {
        return formTemplate;
    }
    
    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(java.lang.String formTemplate) {
        if (formTemplate == null || formTemplate.length() == 0)
            this.formTemplate = null;
        else
            this.formTemplate = formTemplate;
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



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormDefinitionFinderOutRowDto>");
        buf.append("<formId>"); if (formId != null) buf.append(StringHelper.convertToHTML(Formatter.format(formId))); buf.append("</formId>");
        buf.append("<formName>"); if (formName != null) buf.append(StringHelper.convertToHTML(Formatter.format(formName))); buf.append("</formName>");
        buf.append("<formAlternate>"); if (formAlternate != null) buf.append(StringHelper.convertToHTML(Formatter.format(formAlternate))); buf.append("</formAlternate>");
        buf.append("<formVariation>"); if (formVariation != null) buf.append(StringHelper.convertToHTML(Formatter.format(formVariation))); buf.append("</formVariation>");
        buf.append("<description>"); if (description != null) buf.append(StringHelper.convertToHTML(Formatter.format(description))); buf.append("</description>");
        buf.append("<formTemplate>"); if (formTemplate != null) buf.append(StringHelper.convertToHTML(Formatter.format(formTemplate))); buf.append("</formTemplate>");
        buf.append("<outputType>"); if (outputType != null) buf.append(StringHelper.convertToHTML(Formatter.format(outputType))); buf.append("</outputType>");
        buf.append("</FormDefinitionFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
