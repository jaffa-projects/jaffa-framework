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
/** The related object returned by the UserMaintenance.
 */
public class UserRoleDto {

    /** Holds value of property roleName. */
    private java.lang.String roleName;

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
        buf.append("<roleName>"); if (roleName != null) buf.append(roleName); buf.append("</roleName>");
        buf.append("</UserRoleDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}

