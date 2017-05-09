// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the UserTimeEntryMaintenance.
 */
public class UserTimeEntryMaintenanceRetrieveInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property userName. */
    private java.lang.String userName;

    /** Holds value of property projectCode. */
    private java.lang.String projectCode;

    /** Holds value of property task. */
    private java.lang.String task;

    /** Holds value of property periodStart. */
    private org.jaffa.datatypes.DateTime periodStart;

    /** Holds value of property periodEnd. */
    private org.jaffa.datatypes.DateTime periodEnd;



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
    

    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return userName;
    }
    
    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        if (userName == null || userName.length() == 0)
            this.userName = null;
        else
            this.userName = userName;
    }

    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public java.lang.String getProjectCode() {
        return projectCode;
    }
    
    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(java.lang.String projectCode) {
        if (projectCode == null || projectCode.length() == 0)
            this.projectCode = null;
        else
            this.projectCode = projectCode;
    }

    /** Getter for property task.
     * @return Value of property task.
     */
    public java.lang.String getTask() {
        return task;
    }
    
    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(java.lang.String task) {
        if (task == null || task.length() == 0)
            this.task = null;
        else
            this.task = task;
    }

    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public org.jaffa.datatypes.DateTime getPeriodStart() {
        return periodStart;
    }
    
    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart) {
        this.periodStart = periodStart;
    }

    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public org.jaffa.datatypes.DateTime getPeriodEnd() {
        return periodEnd;
    }
    
    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd) {
        this.periodEnd = periodEnd;
    }





    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserTimeEntryMaintenanceRetrieveInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<userName>"); if (userName != null) buf.append(userName); buf.append("</userName>");
        buf.append("<projectCode>"); if (projectCode != null) buf.append(projectCode); buf.append("</projectCode>");
        buf.append("<task>"); if (task != null) buf.append(task); buf.append("</task>");
        buf.append("<periodStart>"); if (periodStart != null) buf.append(periodStart); buf.append("</periodStart>");
        buf.append("<periodEnd>"); if (periodEnd != null) buf.append(periodEnd); buf.append("</periodEnd>");
        buf.append("</UserTimeEntryMaintenanceRetrieveInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
