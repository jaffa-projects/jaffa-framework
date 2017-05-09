// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formgroupmaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormGroupMaintenance prevalidations.
 */
public class FormGroupMaintenancePrevalidateOutDto extends FormGroupMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormGroupMaintenancePrevalidateOutDto>");
        buf.append("<formName>"); if (getFormName() != null) buf.append(getFormName()); buf.append("</formName>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<formType>"); if (getFormType() != null) buf.append(getFormType()); buf.append("</formType>");

        buf.append("<formUsages>");
        FormUsageDto[] formUsages = getFormUsage();
        for (int i = 0; i < formUsages.length; i++) {
            buf.append(formUsages[i].toString());
        }
        buf.append("</formUsages>");

        buf.append("</FormGroupMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
