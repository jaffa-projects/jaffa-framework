// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formtemplatemaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormTemplateMaintenance.
 */
public class FormTemplateMaintenanceCreateInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property templateData. */
    private byte[] templateData;

    /** Holds value of property layoutData. */
    private byte[] layoutData;

    /** Holds value of property formId. */
    private java.lang.Long formId;



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



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormTemplateMaintenanceCreateInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<templateData>"); if (templateData != null) buf.append(templateData); buf.append("</templateData>");
        buf.append("<layoutData>"); if (layoutData != null) buf.append(layoutData); buf.append("</layoutData>");
        buf.append("<formId>"); if (formId != null) buf.append(formId); buf.append("</formId>");

        buf.append("</FormTemplateMaintenanceCreateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
