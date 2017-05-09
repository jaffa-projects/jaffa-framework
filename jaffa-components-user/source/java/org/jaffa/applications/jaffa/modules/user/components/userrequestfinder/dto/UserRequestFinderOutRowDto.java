// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto;

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserRequestFinder contains an array of instances of this class.
 */
public class UserRequestFinderOutRowDto {

    /** Holds value of property requestId. */
    private java.lang.Long requestId;

    /** Holds value of property userName. */
    private java.lang.String userName;

    /** Holds value of property firstName. */
    private java.lang.String firstName;

    /** Holds value of property lastName. */
    private java.lang.String lastName;

    /** Holds value of property password. */
    private java.lang.String password;

    /** Holds value of property eMailAddress. */
    private java.lang.String eMailAddress;

    /** Holds value of property securityQuestion. */
    private java.lang.Long securityQuestion;

    /** Holds value of property securityAnswer. */
    private java.lang.String securityAnswer;

    /** Holds value of property remarks. */
    private java.lang.String remarks;

    /** Holds value of property createdOn. */
    private org.jaffa.datatypes.DateTime createdOn;

    /** Holds value of property processedDatetime. */
    private org.jaffa.datatypes.DateTime processedDatetime;

    /** Holds value of property processedUserId. */
    private java.lang.String processedUserId;

    /** Holds value of property status. */
    private java.lang.String status;


    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        return requestId;
    }
    
    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(java.lang.Long requestId) {
        this.requestId = requestId;
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

    /** Getter for property password.
     * @return Value of property password.
     */
    public java.lang.String getPassword() {
        return password;
    }
    
    /** Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(java.lang.String password) {
        if (password == null || password.length() == 0)
            this.password = null;
        else
            this.password = password;
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

    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        return securityQuestion;
    }
    
    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.Long securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        return securityAnswer;
    }
    
    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(java.lang.String securityAnswer) {
        if (securityAnswer == null || securityAnswer.length() == 0)
            this.securityAnswer = null;
        else
            this.securityAnswer = securityAnswer;
    }

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return remarks;
    }
    
    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        if (remarks == null || remarks.length() == 0)
            this.remarks = null;
        else
            this.remarks = remarks;
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

    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public org.jaffa.datatypes.DateTime getProcessedDatetime() {
        return processedDatetime;
    }
    
    /** Setter for property processedDatetime.
     * @param processedDatetime New value of property processedDatetime.
     */
    public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) {
        this.processedDatetime = processedDatetime;
    }

    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public java.lang.String getProcessedUserId() {
        return processedUserId;
    }
    
    /** Setter for property processedUserId.
     * @param processedUserId New value of property processedUserId.
     */
    public void setProcessedUserId(java.lang.String processedUserId) {
        if (processedUserId == null || processedUserId.length() == 0)
            this.processedUserId = null;
        else
            this.processedUserId = processedUserId;
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



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserRequestFinderOutRowDto>");
        buf.append("<requestId>"); if (requestId != null) buf.append(StringHelper.convertToHTML(Formatter.format(requestId))); buf.append("</requestId>");
        buf.append("<userName>"); if (userName != null) buf.append(StringHelper.convertToHTML(Formatter.format(userName))); buf.append("</userName>");
        buf.append("<firstName>"); if (firstName != null) buf.append(StringHelper.convertToHTML(Formatter.format(firstName))); buf.append("</firstName>");
        buf.append("<lastName>"); if (lastName != null) buf.append(StringHelper.convertToHTML(Formatter.format(lastName))); buf.append("</lastName>");
        buf.append("<password>"); if (password != null) buf.append(StringHelper.convertToHTML(Formatter.format(password))); buf.append("</password>");
        buf.append("<eMailAddress>"); if (eMailAddress != null) buf.append(StringHelper.convertToHTML(Formatter.format(eMailAddress))); buf.append("</eMailAddress>");
        buf.append("<securityQuestion>"); if (securityQuestion != null) buf.append(StringHelper.convertToHTML(Formatter.format(securityQuestion))); buf.append("</securityQuestion>");
        buf.append("<securityAnswer>"); if (securityAnswer != null) buf.append(StringHelper.convertToHTML(Formatter.format(securityAnswer))); buf.append("</securityAnswer>");
        buf.append("<remarks>"); if (remarks != null) buf.append(StringHelper.convertToHTML(Formatter.format(remarks))); buf.append("</remarks>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(StringHelper.convertToHTML(Formatter.format(createdOn))); buf.append("</createdOn>");
        buf.append("<processedDatetime>"); if (processedDatetime != null) buf.append(StringHelper.convertToHTML(Formatter.format(processedDatetime))); buf.append("</processedDatetime>");
        buf.append("<processedUserId>"); if (processedUserId != null) buf.append(StringHelper.convertToHTML(Formatter.format(processedUserId))); buf.append("</processedUserId>");
        buf.append("<status>"); if (status != null) buf.append(StringHelper.convertToHTML(Formatter.format(status))); buf.append("</status>");
        buf.append("</UserRequestFinderOutRowDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
