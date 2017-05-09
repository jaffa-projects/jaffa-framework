// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserMaintenance prevalidations.
 */
public class UserMaintenancePrevalidateOutDto extends UserMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserMaintenancePrevalidateOutDto>");
        buf.append("<userName>"); if (getUserName() != null) buf.append(getUserName()); buf.append("</userName>");
        buf.append("<firstName>"); if (getFirstName() != null) buf.append(getFirstName()); buf.append("</firstName>");
        buf.append("<lastName>"); if (getLastName() != null) buf.append(getLastName()); buf.append("</lastName>");
        buf.append("<password1>"); if (getPassword1() != null) buf.append(getPassword1()); buf.append("</password1>");
        buf.append("<requestId>"); if (getRequestId() != null) buf.append(getRequestId()); buf.append("</requestId>");
        buf.append("<password2>"); if (getPassword2() != null) buf.append(getPassword2()); buf.append("</password2>");
        buf.append("<status>"); if (getStatus() != null) buf.append(getStatus()); buf.append("</status>");
        buf.append("<EMailAddress>"); if (getEMailAddress() != null) buf.append(getEMailAddress()); buf.append("</EMailAddress>");
        buf.append("<securityQuestion>"); if (getSecurityQuestion() != null) buf.append(getSecurityQuestion()); buf.append("</securityQuestion>");
        buf.append("<securityQuestion1>"); if (getSecurityQuestion1() != null) buf.append(getSecurityQuestion1()); buf.append("</securityQuestion1>");
        buf.append("<securityAnswer>"); if (getSecurityAnswer() != null) buf.append(getSecurityAnswer()); buf.append("</securityAnswer>");
        buf.append("<createdOn>"); if (getCreatedOn() != null) buf.append(getCreatedOn()); buf.append("</createdOn>");
        buf.append("<autoPassword>"); if (getAutoPassword() != null) buf.append(getAutoPassword()); buf.append("</autoPassword>");
        buf.append("<notifyUser>"); if (getNotifyUser() != null) buf.append(getNotifyUser()); buf.append("</notifyUser>");
        buf.append("<createdBy>"); if (getCreatedBy() != null) buf.append(getCreatedBy()); buf.append("</createdBy>");
        buf.append("<lastUpdatedOn>"); if (getLastUpdatedOn() != null) buf.append(getLastUpdatedOn()); buf.append("</lastUpdatedOn>");
        buf.append("<lastUpdatedBy>"); if (getLastUpdatedBy() != null) buf.append(getLastUpdatedBy()); buf.append("</lastUpdatedBy>");

        buf.append("<userRoles>");
        UserRoleDto[] userRoles = getUserRole();
        for (int i = 0; i < userRoles.length; i++) {
            buf.append(userRoles[i].toString());
        }
        buf.append("</userRoles>");

        buf.append("</UserMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
