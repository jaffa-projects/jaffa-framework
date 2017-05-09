// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto.UserRequestViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the UserRequestViewer.
 */
public class UserRequestViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "user_userRequestViewer";
    private static Logger log = Logger.getLogger(UserRequestViewerForm.class);


    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getRequestId() : null;
    }

    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getUserName() : null;
    }

    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getFirstName() : null;
    }

    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getLastName() : null;
    }

    /** Getter for property password.
     * @return Value of property password.
     */
    public java.lang.String getPassword() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getPassword() : null;
    }

    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public java.lang.String getEMailAddress() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getEMailAddress() : null;
    }

    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getSecurityQuestion() : null;
    }

    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getSecurityAnswer() : null;
    }

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getRemarks() : null;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public org.jaffa.datatypes.DateTime getProcessedDatetime() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getProcessedDatetime() : null;
    }

    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public java.lang.String getProcessedUserId() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getProcessedUserId() : null;
    }

    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        UserRequestViewerOutDto outputDto = ((UserRequestViewerComponent) getComponent()).getUserRequestViewerOutDto();
        return outputDto != null ? outputDto.getStatus() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
