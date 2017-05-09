// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formusagemaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormUsageMaintenance prevalidations.
 */
public class FormUsageMaintenancePrevalidateOutDto extends FormUsageMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormUsageMaintenancePrevalidateOutDto>");
        buf.append("<formAlternate>"); if (getFormAlternate() != null) buf.append(getFormAlternate()); buf.append("</formAlternate>");
        buf.append("<copies>"); if (getCopies() != null) buf.append(getCopies()); buf.append("</copies>");
        buf.append("<createdOn>"); if (getCreatedOn() != null) buf.append(getCreatedOn()); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (getCreatedBy() != null) buf.append(getCreatedBy()); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (getLastChangedOn() != null) buf.append(getLastChangedOn()); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (getLastChangedBy() != null) buf.append(getLastChangedBy()); buf.append("</lastChangedBy>");
        buf.append("<eventName>"); if (getEventName() != null) buf.append(getEventName()); buf.append("</eventName>");
        buf.append("<formName>"); if (getFormName() != null) buf.append(getFormName()); buf.append("</formName>");

        buf.append("</FormUsageMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
