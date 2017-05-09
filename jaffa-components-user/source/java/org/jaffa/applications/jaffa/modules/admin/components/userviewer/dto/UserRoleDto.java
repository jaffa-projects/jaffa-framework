/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
// .//GEN-BEGIN:_1_be
package org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The related object returned by the UserViewer.
 */
public class UserRoleDto {

    /** Holds value of property userName. */
    private java.lang.String userName;

    /** Holds value of property roleName. */
    private java.lang.String roleName;

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

    /** Getter for property roleName.
     * @return Value of property roleName.
     */
    public java.lang.String getRoleName() {
        return roleName;
    }
    
    /** Setter for property roleName.
     * @param roleName New value of property roleName.
     */
    public void setRoleName(java.lang.String roleName) {
        if (roleName == null || roleName.length() == 0)
            this.roleName = null;
        else
            this.roleName = roleName;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserRoleDto>");
        buf.append("<userName>"); if (userName != null) buf.append(userName); buf.append("</userName>");
        buf.append("<roleName>"); if (roleName != null) buf.append(roleName); buf.append("</roleName>");
        buf.append("</UserRoleDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}

