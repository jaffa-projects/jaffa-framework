// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserTimeEntryMaintenance prevalidations.
 */
public class UserTimeEntryMaintenancePrevalidateOutDto extends UserTimeEntryMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserTimeEntryMaintenancePrevalidateOutDto>");
        buf.append("<userName>"); if (getUserName() != null) buf.append(getUserName()); buf.append("</userName>");
        buf.append("<projectCode>"); if (getProjectCode() != null) buf.append(getProjectCode()); buf.append("</projectCode>");
        buf.append("<activity>"); if (getActivity() != null) buf.append(getActivity()); buf.append("</activity>");
        buf.append("<task>"); if (getTask() != null) buf.append(getTask()); buf.append("</task>");
        buf.append("<periodStart>"); if (getPeriodStart() != null) buf.append(getPeriodStart()); buf.append("</periodStart>");
        buf.append("<periodEnd>"); if (getPeriodEnd() != null) buf.append(getPeriodEnd()); buf.append("</periodEnd>");

        buf.append("</UserTimeEntryMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
