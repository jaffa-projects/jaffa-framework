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
public class FormTemplateMaintenanceUpdateInDto extends FormTemplateMaintenanceCreateInDto {

    /** Holds value of property performDirtyReadCheck. */
    private Boolean performDirtyReadCheck;


    /** Getter for property performDirtyReadCheck.
     * @return Value of property performDirtyReadCheck.
     */
    public Boolean getPerformDirtyReadCheck() {
        return performDirtyReadCheck;
    }
    
    /** Setter for property performDirtyReadCheck.
     * @param performDirtyReadCheck New value of property performDirtyReadCheck.
     */
    public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck) {
        this.performDirtyReadCheck = performDirtyReadCheck;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormTemplateMaintenanceUpdateInDto>");
        buf.append("<headerDto>"); if (getHeaderDto() != null) buf.append( getHeaderDto().toString() ); buf.append("</headerDto>");
        buf.append("<performDirtyReadCheck>"); if (performDirtyReadCheck != null) buf.append( performDirtyReadCheck.toString() ); buf.append("</performDirtyReadCheck>");

        buf.append("<templateData>"); if (getTemplateData() != null) buf.append(getTemplateData()); buf.append("</templateData>");
        buf.append("<layoutData>"); if (getLayoutData() != null) buf.append(getLayoutData()); buf.append("</layoutData>");
        buf.append("<formId>"); if (getFormId() != null) buf.append(getFormId()); buf.append("</formId>");

        buf.append("</FormTemplateMaintenanceUpdateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
