// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formtemplatemaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be

import java.util.Base64;

/** The output for the FormTemplateMaintenance prevalidations.
 */
public class FormTemplateMaintenancePrevalidateOutDto extends FormTemplateMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormTemplateMaintenancePrevalidateOutDto>");
        buf.append("<templateData>");
        if (getTemplateData() != null) {
            buf.append(Base64.getEncoder().encodeToString(getTemplateData()));
        }
        buf.append("</templateData>");
        buf.append("<layoutData>");
        if (getLayoutData() != null) {
            buf.append(Base64.getEncoder().encodeToString(getLayoutData()));
        }
        buf.append("</layoutData>");
        buf.append("<formId>");
        if (getFormId() != null) buf.append(getFormId()); buf.append("</formId>");

        buf.append("</FormTemplateMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
