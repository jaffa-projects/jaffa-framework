// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the UserTimeEntryLookup.
 */
public class UserTimeEntryLookupInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property userName. */
    private StringCriteriaField userName;

    /** Holds value of property projectCode. */
    private StringCriteriaField projectCode;

    /** Holds value of property activity. */
    private StringCriteriaField activity;

    /** Holds value of property task. */
    private StringCriteriaField task;

    /** Holds value of property periodStart. */
    private DateTimeCriteriaField periodStart;

    /** Holds value of property periodEnd. */
    private DateTimeCriteriaField periodEnd;



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
    public StringCriteriaField getUserName() {
        return userName;
    }
    
    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(StringCriteriaField userName) {
        this.userName = userName;
    }

    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public StringCriteriaField getProjectCode() {
        return projectCode;
    }
    
    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(StringCriteriaField projectCode) {
        this.projectCode = projectCode;
    }

    /** Getter for property activity.
     * @return Value of property activity.
     */
    public StringCriteriaField getActivity() {
        return activity;
    }
    
    /** Setter for property activity.
     * @param activity New value of property activity.
     */
    public void setActivity(StringCriteriaField activity) {
        this.activity = activity;
    }

    /** Getter for property task.
     * @return Value of property task.
     */
    public StringCriteriaField getTask() {
        return task;
    }
    
    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(StringCriteriaField task) {
        this.task = task;
    }

    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public DateTimeCriteriaField getPeriodStart() {
        return periodStart;
    }
    
    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(DateTimeCriteriaField periodStart) {
        this.periodStart = periodStart;
    }

    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public DateTimeCriteriaField getPeriodEnd() {
        return periodEnd;
    }
    
    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(DateTimeCriteriaField periodEnd) {
        this.periodEnd = periodEnd;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserTimeEntryLookupInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<userName>"); if (userName != null) buf.append(userName); buf.append("</userName>");
        buf.append("<projectCode>"); if (projectCode != null) buf.append(projectCode); buf.append("</projectCode>");
        buf.append("<activity>"); if (activity != null) buf.append(activity); buf.append("</activity>");
        buf.append("<task>"); if (task != null) buf.append(task); buf.append("</task>");
        buf.append("<periodStart>"); if (periodStart != null) buf.append(periodStart); buf.append("</periodStart>");
        buf.append("<periodEnd>"); if (periodEnd != null) buf.append(periodEnd); buf.append("</periodEnd>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</UserTimeEntryLookupInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
