// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplatefinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormTemplateFinder contains an array of instances of this class.
 */
public class FormTemplateFinderOutRowDto {

    /** Holds value of property formId. */
    private java.lang.Long formId;

    /** Holds value of property templateData. */
    private byte[] templateData;

    /** Holds value of property layoutData. */
    private byte[] layoutData;


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

    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public byte[] getTemplateData() {
        return templateData;
    }
    
    /** Setter for property templateData.
     * @param templateData New value of property templateData.
     */
    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public byte[] getLayoutData() {
        return layoutData;
    }
    
    /** Setter for property layoutData.
     * @param layoutData New value of property layoutData.
     */
    public void setLayoutData(byte[] layoutData) {
        this.layoutData = layoutData;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormTemplateFinderOutRowDto>");
        buf.append("<formId>"); if (formId != null) buf.append(StringHelper.convertToHTML(Formatter.format(formId))); buf.append("</formId>");
        buf.append("<templateData>"); if (templateData != null) buf.append(StringHelper.convertToHTML(Formatter.format(templateData))); buf.append("</templateData>");
        buf.append("<layoutData>"); if (layoutData != null) buf.append(StringHelper.convertToHTML(Formatter.format(layoutData))); buf.append("</layoutData>");
        buf.append("</FormTemplateFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
