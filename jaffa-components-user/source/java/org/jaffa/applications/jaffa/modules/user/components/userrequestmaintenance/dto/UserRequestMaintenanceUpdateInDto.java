// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserRequestMaintenance.
 */
public class UserRequestMaintenanceUpdateInDto extends UserRequestMaintenanceCreateInDto {

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
        buf.append("<UserRequestMaintenanceUpdateInDto>");
        buf.append("<headerDto>"); if (getHeaderDto() != null) buf.append( getHeaderDto().toString() ); buf.append("</headerDto>");
        buf.append("<performDirtyReadCheck>"); if (performDirtyReadCheck != null) buf.append( performDirtyReadCheck.toString() ); buf.append("</performDirtyReadCheck>");

        buf.append("<requestId>"); if (getRequestId() != null) buf.append(getRequestId()); buf.append("</requestId>");
        buf.append("<userName>"); if (getUserName() != null) buf.append(getUserName()); buf.append("</userName>");
        buf.append("<firstName>"); if (getFirstName() != null) buf.append(getFirstName()); buf.append("</firstName>");
        buf.append("<lastName>"); if (getLastName() != null) buf.append(getLastName()); buf.append("</lastName>");
        buf.append("<password1>"); if (getPassword1() != null) buf.append(getPassword1()); buf.append("</password1>");
        buf.append("<password2>"); if (getPassword2() != null) buf.append(getPassword2()); buf.append("</password2>");
        buf.append("<eMailAddress>"); if (getEMailAddress() != null) buf.append(getEMailAddress()); buf.append("</eMailAddress>");
        buf.append("<securityQuestion>"); if (getSecurityQuestion() != null) buf.append(getSecurityQuestion()); buf.append("</securityQuestion>");
        buf.append("<securityQuestion1>"); if (getSecurityQuestion1() != null) buf.append(getSecurityQuestion1()); buf.append("</securityQuestion1>");
        buf.append("<securityAnswer>"); if (getSecurityAnswer() != null) buf.append(getSecurityAnswer()); buf.append("</securityAnswer>");
        buf.append("<remarks>"); if (getRemarks() != null) buf.append(getRemarks()); buf.append("</remarks>");
        buf.append("<createdOn>"); if (getCreatedOn() != null) buf.append(getCreatedOn()); buf.append("</createdOn>");
        buf.append("<processedDatetime>"); if (getProcessedDatetime() != null) buf.append(getProcessedDatetime()); buf.append("</processedDatetime>");
        buf.append("<processedUserId>"); if (getProcessedUserId() != null) buf.append(getProcessedUserId()); buf.append("</processedUserId>");
        buf.append("<status>"); if (getStatus() != null) buf.append(getStatus()); buf.append("</status>");

        buf.append("</UserRequestMaintenanceUpdateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
