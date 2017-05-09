// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserFinder contains an array of instances of this class.
 */
public class UserFinderOutRowDto {

    /** Holds value of property userName. */
    private java.lang.String userName;

    /** Holds value of property firstName. */
    private java.lang.String firstName;

    /** Holds value of property lastName. */
    private java.lang.String lastName;

    /** Holds value of property status. */
    private java.lang.String status;

    /** Holds value of property statusDescription. */
    private java.lang.String statusDescription;

    /** Holds value of property eMailAddress. */
    private java.lang.String eMailAddress;

    /** Holds value of property createdOn. */
    private org.jaffa.datatypes.DateTime createdOn;

    /** Holds value of property createdBy. */
    private java.lang.String createdBy;

    /** Holds value of property lastUpdatedOn. */
    private org.jaffa.datatypes.DateTime lastUpdatedOn;

    /** Holds value of property lastUpdatedBy. */
    private java.lang.String lastUpdatedBy;


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

    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        return firstName;
    }
    
    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(java.lang.String firstName) {
        if (firstName == null || firstName.length() == 0)
            this.firstName = null;
        else
            this.firstName = firstName;
    }

    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        return lastName;
    }
    
    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(java.lang.String lastName) {
        if (lastName == null || lastName.length() == 0)
            this.lastName = null;
        else
            this.lastName = lastName;
    }

    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return status;
    }
    
    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(java.lang.String status) {
        if (status == null || status.length() == 0)
            this.status = null;
        else
            this.status = status;
    }

    /** Getter for property statusDescription.
     * @return Value of property statusDescription.
     */
    public java.lang.String getStatusDescription() {
        return statusDescription;
    }
    
    /** Setter for property statusDescription.
     * @param statusDescription New value of property statusDescription.
     */
    public void setStatusDescription(java.lang.String statusDescription) {
        if (statusDescription == null || statusDescription.length() == 0)
            this.statusDescription = null;
        else
            this.statusDescription = statusDescription;
    }

    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public java.lang.String getEMailAddress() {
        return eMailAddress;
    }
    
    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(java.lang.String eMailAddress) {
        if (eMailAddress == null || eMailAddress.length() == 0)
            this.eMailAddress = null;
        else
            this.eMailAddress = eMailAddress;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return createdOn;
    }
    
    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        this.createdOn = createdOn;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return createdBy;
    }
    
    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        if (createdBy == null || createdBy.length() == 0)
            this.createdBy = null;
        else
            this.createdBy = createdBy;
    }

    /** Getter for property lastUpdatedOn.
     * @return Value of property lastUpdatedOn.
     */
    public org.jaffa.datatypes.DateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }
    
    /** Setter for property lastUpdatedOn.
     * @param lastUpdatedOn New value of property lastUpdatedOn.
     */
    public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    /** Getter for property lastUpdatedBy.
     * @return Value of property lastUpdatedBy.
     */
    public java.lang.String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    /** Setter for property lastUpdatedBy.
     * @param lastUpdatedBy New value of property lastUpdatedBy.
     */
    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        if (lastUpdatedBy == null || lastUpdatedBy.length() == 0)
            this.lastUpdatedBy = null;
        else
            this.lastUpdatedBy = lastUpdatedBy;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserFinderOutRowDto>");
        buf.append("<userName>"); if (userName != null) buf.append(StringHelper.convertToHTML(Formatter.format(userName))); buf.append("</userName>");
        buf.append("<firstName>"); if (firstName != null) buf.append(StringHelper.convertToHTML(Formatter.format(firstName))); buf.append("</firstName>");
        buf.append("<lastName>"); if (lastName != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastName))); buf.append("</lastName>");
        buf.append("<status>"); if (status != null) buf.append(StringHelper.convertToHTML(Formatter.format(status))); buf.append("</status>");
        buf.append("<statusDescription>"); if (statusDescription != null) buf.append(StringHelper.convertToHTML(Formatter.format(statusDescription))); buf.append("</statusDescription>");
        buf.append("<eMailAddress>"); if (eMailAddress != null) buf.append(StringHelper.convertToHTML(Formatter.format(eMailAddress))); buf.append("</eMailAddress>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(createdOn))); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (createdBy != null) buf.append(StringHelper.convertToHTML(Formatter.format(createdBy))); buf.append("</createdBy>");
        buf.append("<lastUpdatedOn>"); if (lastUpdatedOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastUpdatedOn))); buf.append("</lastUpdatedOn>");
        buf.append("<lastUpdatedBy>"); if (lastUpdatedBy != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastUpdatedBy))); buf.append("</lastUpdatedBy>");
        buf.append("</UserFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
