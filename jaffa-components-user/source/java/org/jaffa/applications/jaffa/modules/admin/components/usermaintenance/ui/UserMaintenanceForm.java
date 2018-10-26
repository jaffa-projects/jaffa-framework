// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.util.StringHelper;

import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.*;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;

import org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.apache.struts.action.ActionMessage;
import org.jaffa.security.PolicyManager;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.security.securityrolesdomain.Roles;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.security.securityrolesdomain.Include;
import org.jaffa.security.securityrolesdomain.Exclude;



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the UserMaintenance.
 */
public class UserMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(UserMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return ( (UserMaintenanceComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        ( (UserMaintenanceComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel w_userName = (EditBoxModel) getWidgetCache().getModel("userName");
        if (w_userName == null) {
            if (getUserName() != null)
                w_userName = new EditBoxModel(getUserName(), (StringFieldMetaData) UserMeta.META_USER_NAME);
            else
                w_userName = new EditBoxModel((StringFieldMetaData) UserMeta.META_USER_NAME);
            // .//GEN-END:userName_1_be
            // Add custom code //GEN-FIRST:userName_1


            // .//GEN-LAST:userName_1
            // .//GEN-BEGIN:userName_2_be
            getWidgetCache().addModel("userName", w_userName);
        }
        return w_userName;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }
    // .//GEN-END:userName_2_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        return ( (UserMaintenanceComponent) getComponent() ).getFirstName();
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(java.lang.String firstName) {
        ( (UserMaintenanceComponent) getComponent() ).setFirstName(firstName);
    }

    /** Getter for property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstName.
     */
    public EditBoxModel getFirstNameWM() {
        EditBoxModel w_firstName = (EditBoxModel) getWidgetCache().getModel("firstName");
        if (w_firstName == null) {
            if (getFirstName() != null)
                w_firstName = new EditBoxModel(getFirstName(), (StringFieldMetaData) UserMeta.META_FIRST_NAME);
            else
                w_firstName = new EditBoxModel((StringFieldMetaData) UserMeta.META_FIRST_NAME);
            // .//GEN-END:firstName_1_be
            // Add custom code //GEN-FIRST:firstName_1


            // .//GEN-LAST:firstName_1
            // .//GEN-BEGIN:firstName_2_be
            getWidgetCache().addModel("firstName", w_firstName);
        }
        return w_firstName;
    }

    /** Setter for property firstName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property firstName.
     */
    public void setFirstNameWV(String value) {
        EditBoxController.updateModel(value, getFirstNameWM());
    }
    // .//GEN-END:firstName_2_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        return ( (UserMaintenanceComponent) getComponent() ).getLastName();
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(java.lang.String lastName) {
        ( (UserMaintenanceComponent) getComponent() ).setLastName(lastName);
    }

    /** Getter for property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastName.
     */
    public EditBoxModel getLastNameWM() {
        EditBoxModel w_lastName = (EditBoxModel) getWidgetCache().getModel("lastName");
        if (w_lastName == null) {
            if (getLastName() != null)
                w_lastName = new EditBoxModel(getLastName(), (StringFieldMetaData) UserMeta.META_LAST_NAME);
            else
                w_lastName = new EditBoxModel((StringFieldMetaData) UserMeta.META_LAST_NAME);
            // .//GEN-END:lastName_1_be
            // Add custom code //GEN-FIRST:lastName_1


            // .//GEN-LAST:lastName_1
            // .//GEN-BEGIN:lastName_2_be
            getWidgetCache().addModel("lastName", w_lastName);
        }
        return w_lastName;
    }

    /** Setter for property lastName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastName.
     */
    public void setLastNameWV(String value) {
        EditBoxController.updateModel(value, getLastNameWM());
    }
    // .//GEN-END:lastName_2_be
    // .//GEN-BEGIN:password1_1_be
    /** Getter for property password1.
     * @return Value of property password1.
     */
    public java.lang.String getPassword1() {
        return ( (UserMaintenanceComponent) getComponent() ).getPassword1();
    }

    /** Setter for property password1.
     * @param password1 New value of property password1.
     */
    public void setPassword1(java.lang.String password1) {
        ( (UserMaintenanceComponent) getComponent() ).setPassword1(password1);
    }

    /** Getter for property password1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property password1.
     */
    public EditBoxModel getPassword1WM() {
        EditBoxModel w_password1 = (EditBoxModel) getWidgetCache().getModel("password1");
        if (w_password1 == null) {
            if (getPassword1() != null)
                w_password1 = new EditBoxModel( getPassword1() );
            else
                w_password1 = new EditBoxModel();
            // .//GEN-END:password1_1_be
            // Add custom code //GEN-FIRST:password1_1
            if (((UserMaintenanceComponent) getComponent()).isCreateMode())
                w_password1.setMandatory(true);

            // .//GEN-LAST:password1_1
            // .//GEN-BEGIN:password1_2_be
            getWidgetCache().addModel("password1", w_password1);
        }
        return w_password1;
    }

    /** Setter for property password1. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property password1.
     */
    public void setPassword1WV(String value) {
        EditBoxController.updateModel(value, getPassword1WM());
    }
    // .//GEN-END:password1_2_be
    // .//GEN-BEGIN:requestId_1_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.String getRequestId() {
        return ( (UserMaintenanceComponent) getComponent() ).getRequestId();
    }

    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(java.lang.String requestId) {
        ( (UserMaintenanceComponent) getComponent() ).setRequestId(requestId);
    }

    /** Getter for property requestId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property requestId.
     */
    public EditBoxModel getRequestIdWM() {
        EditBoxModel w_requestId = (EditBoxModel) getWidgetCache().getModel("requestId");
        if (w_requestId == null) {
            if (getRequestId() != null)
                w_requestId = new EditBoxModel( getRequestId() );
            else
                w_requestId = new EditBoxModel();
            // .//GEN-END:requestId_1_be
            // Add custom code //GEN-FIRST:requestId_1


            // .//GEN-LAST:requestId_1
            // .//GEN-BEGIN:requestId_2_be
            getWidgetCache().addModel("requestId", w_requestId);
        }
        return w_requestId;
    }

    /** Setter for property requestId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property requestId.
     */
    public void setRequestIdWV(String value) {
        EditBoxController.updateModel(value, getRequestIdWM());
    }
    // .//GEN-END:requestId_2_be
    // .//GEN-BEGIN:password2_1_be
    /** Getter for property password2.
     * @return Value of property password2.
     */
    public java.lang.String getPassword2() {
        return ( (UserMaintenanceComponent) getComponent() ).getPassword2();
    }

    /** Setter for property password2.
     * @param password2 New value of property password2.
     */
    public void setPassword2(java.lang.String password2) {
        ( (UserMaintenanceComponent) getComponent() ).setPassword2(password2);
    }

    /** Getter for property password2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property password2.
     */
    public EditBoxModel getPassword2WM() {
        EditBoxModel w_password2 = (EditBoxModel) getWidgetCache().getModel("password2");
        if (w_password2 == null) {
            if (getPassword2() != null)
                w_password2 = new EditBoxModel( getPassword2() );
            else
                w_password2 = new EditBoxModel();
            // .//GEN-END:password2_1_be
            // Add custom code //GEN-FIRST:password2_1
            if (((UserMaintenanceComponent) getComponent()).isCreateMode())
                w_password2.setMandatory(true);

            // .//GEN-LAST:password2_1
            // .//GEN-BEGIN:password2_2_be
            getWidgetCache().addModel("password2", w_password2);
        }
        return w_password2;
    }

    /** Setter for property password2. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property password2.
     */
    public void setPassword2WV(String value) {
        EditBoxController.updateModel(value, getPassword2WM());
    }
    // .//GEN-END:password2_2_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return ( (UserMaintenanceComponent) getComponent() ).getStatus();
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(java.lang.String status) {
        ( (UserMaintenanceComponent) getComponent() ).setStatus(status);
    }

    /** Getter for property status. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property status.
     */
    public DropDownModel getStatusWM() {
        DropDownModel w_status = (DropDownModel) getWidgetCache().getModel("status");
        if (w_status == null) {
            w_status = new DropDownModel((getStatus() != null ? getStatus() : ""), (StringFieldMetaData) UserMeta.META_STATUS);
            w_status.addOption("[label.Jaffa.Admin.User.Status.N]", "N");
            w_status.addOption("[label.Jaffa.Admin.User.Status.A]", "A");
            w_status.addOption("[label.Jaffa.Admin.User.Status.I]", "I");

            // .//GEN-END:status_1_be
            // Add custom code //GEN-FIRST:status_1


            // .//GEN-LAST:status_1
            // .//GEN-BEGIN:status_2_be
            getWidgetCache().addModel("status", w_status);
        }
        return w_status;
    }

    /** Setter for property status. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property status.
     */
    public void setStatusWV(String value) {
        DropDownController.updateModel(value, getStatusWM());
    }
    // .//GEN-END:status_2_be
    // .//GEN-BEGIN:EMailAddress_1_be
    /** Getter for property EMailAddress.
     * @return Value of property EMailAddress.
     */
    public java.lang.String getEMailAddress() {
        return ( (UserMaintenanceComponent) getComponent() ).getEMailAddress();
    }

    /** Setter for property EMailAddress.
     * @param EMailAddress New value of property EMailAddress.
     */
    public void setEMailAddress(java.lang.String EMailAddress) {
        ( (UserMaintenanceComponent) getComponent() ).setEMailAddress(EMailAddress);
    }

    /** Getter for property EMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property EMailAddress.
     */
    public EditBoxModel getEMailAddressWM() {
        EditBoxModel w_EMailAddress = (EditBoxModel) getWidgetCache().getModel("EMailAddress");
        if (w_EMailAddress == null) {
            if (getEMailAddress() != null)
                w_EMailAddress = new EditBoxModel(getEMailAddress(), (StringFieldMetaData) UserMeta.META_E_MAIL_ADDRESS);
            else
                w_EMailAddress = new EditBoxModel((StringFieldMetaData) UserMeta.META_E_MAIL_ADDRESS);
            // .//GEN-END:EMailAddress_1_be
            // Add custom code //GEN-FIRST:EMailAddress_1


            // .//GEN-LAST:EMailAddress_1
            // .//GEN-BEGIN:EMailAddress_2_be
            getWidgetCache().addModel("EMailAddress", w_EMailAddress);
        }
        return w_EMailAddress;
    }

    /** Setter for property EMailAddress. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property EMailAddress.
     */
    public void setEMailAddressWV(String value) {
        EditBoxController.updateModel(value, getEMailAddressWM());
    }
    // .//GEN-END:EMailAddress_2_be
    // .//GEN-BEGIN:securityQuestion_1_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        return ( (UserMaintenanceComponent) getComponent() ).getSecurityQuestion();
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.Long securityQuestion) {
        ( (UserMaintenanceComponent) getComponent() ).setSecurityQuestion(securityQuestion);
    }

    /** Getter for property securityQuestion. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestion.
     */
    public EditBoxModel getSecurityQuestionWM() {
        EditBoxModel w_securityQuestion = (EditBoxModel) getWidgetCache().getModel("securityQuestion");
        if (w_securityQuestion == null) {
            if (getSecurityQuestion() != null)
                w_securityQuestion = new EditBoxModel(getSecurityQuestion(), (IntegerFieldMetaData) UserMeta.META_SECURITY_QUESTION);
            else
                w_securityQuestion = new EditBoxModel((IntegerFieldMetaData) UserMeta.META_SECURITY_QUESTION);
            // .//GEN-END:securityQuestion_1_be
            // Add custom code //GEN-FIRST:securityQuestion_1


            // .//GEN-LAST:securityQuestion_1
            // .//GEN-BEGIN:securityQuestion_2_be
            getWidgetCache().addModel("securityQuestion", w_securityQuestion);
        }
        return w_securityQuestion;
    }

    /** Setter for property securityQuestion. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property securityQuestion.
     */
    public void setSecurityQuestionWV(String value) {
        EditBoxController.updateModel(value, getSecurityQuestionWM());
    }
    // .//GEN-END:securityQuestion_2_be
    // .//GEN-BEGIN:securityQuestion1_1_be
    /** Getter for property securityQuestion1.
     * @return Value of property securityQuestion1.
     */
    public java.lang.String getSecurityQuestion1() {
        return ( (UserMaintenanceComponent) getComponent() ).getSecurityQuestion1();
    }

    /** Setter for property securityQuestion1.
     * @param securityQuestion1 New value of property securityQuestion1.
     */
    public void setSecurityQuestion1(java.lang.String securityQuestion1) {
        ( (UserMaintenanceComponent) getComponent() ).setSecurityQuestion1(securityQuestion1);
    }

    /** Getter for property securityQuestion1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestion1.
     */
    public DropDownModel getSecurityQuestion1WM() {
        DropDownModel w_securityQuestion1 = (DropDownModel) getWidgetCache().getModel("securityQuestion1");
        if (w_securityQuestion1 == null) {
            w_securityQuestion1 = new DropDownModel((getSecurityQuestion1() != null ? getSecurityQuestion1() : ""));
            w_securityQuestion1.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.1]", "1");
            w_securityQuestion1.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.2]", "2");
            w_securityQuestion1.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.3]", "3");
            w_securityQuestion1.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.4]", "4");

            w_securityQuestion1.setMandatory(true);
            // .//GEN-END:securityQuestion1_1_be
            // Add custom code //GEN-FIRST:securityQuestion1_1


            // .//GEN-LAST:securityQuestion1_1
            // .//GEN-BEGIN:securityQuestion1_2_be
            getWidgetCache().addModel("securityQuestion1", w_securityQuestion1);
        }
        return w_securityQuestion1;
    }

    /** Setter for property securityQuestion1. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property securityQuestion1.
     */
    public void setSecurityQuestion1WV(String value) {
        DropDownController.updateModel(value, getSecurityQuestion1WM());
    }
    // .//GEN-END:securityQuestion1_2_be
    // .//GEN-BEGIN:securityAnswer_1_be
    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        return ( (UserMaintenanceComponent) getComponent() ).getSecurityAnswer();
    }

    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(java.lang.String securityAnswer) {
        ( (UserMaintenanceComponent) getComponent() ).setSecurityAnswer(securityAnswer);
    }

    /** Getter for property securityAnswer. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityAnswer.
     */
    public EditBoxModel getSecurityAnswerWM() {
        EditBoxModel w_securityAnswer = (EditBoxModel) getWidgetCache().getModel("securityAnswer");
        if (w_securityAnswer == null) {
            if (getSecurityAnswer() != null)
                w_securityAnswer = new EditBoxModel(getSecurityAnswer(), (StringFieldMetaData) UserMeta.META_SECURITY_ANSWER);
            else
                w_securityAnswer = new EditBoxModel((StringFieldMetaData) UserMeta.META_SECURITY_ANSWER);
            // .//GEN-END:securityAnswer_1_be
            // Add custom code //GEN-FIRST:securityAnswer_1
                w_securityAnswer.setMandatory(true);

            // .//GEN-LAST:securityAnswer_1
            // .//GEN-BEGIN:securityAnswer_2_be
            getWidgetCache().addModel("securityAnswer", w_securityAnswer);
        }
        return w_securityAnswer;
    }

    /** Setter for property securityAnswer. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property securityAnswer.
     */
    public void setSecurityAnswerWV(String value) {
        EditBoxController.updateModel(value, getSecurityAnswerWM());
    }
    // .//GEN-END:securityAnswer_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (UserMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (UserMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) UserMeta.META_CREATED_ON);
            // .//GEN-END:createdOn_1_be
            // Add custom code //GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", w_createdOn);
        }
        return w_createdOn;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        DateTimeController.updateModel(value, getCreatedOnWM());
    }
    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:autoPassword_1_be
    /** Getter for property autoPassword.
     * @return Value of property autoPassword.
     */
    public java.lang.Boolean getAutoPassword() {
        return ( (UserMaintenanceComponent) getComponent() ).getAutoPassword();
    }

    /** Setter for property autoPassword.
     * @param autoPassword New value of property autoPassword.
     */
    public void setAutoPassword(java.lang.Boolean autoPassword) {
        ( (UserMaintenanceComponent) getComponent() ).setAutoPassword(autoPassword);
    }

    /** Getter for property autoPassword. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property autoPassword.
     */
    public CheckBoxModel getAutoPasswordWM() {
        CheckBoxModel w_autoPassword = (CheckBoxModel) getWidgetCache().getModel("autoPassword");
        if (w_autoPassword == null) {
            if (getAutoPassword() != null)
                w_autoPassword = new CheckBoxModel( getAutoPassword() );
            else
                w_autoPassword = new CheckBoxModel(false);
            // .//GEN-END:autoPassword_1_be
            // Add custom code //GEN-FIRST:autoPassword_1


            // .//GEN-LAST:autoPassword_1
            // .//GEN-BEGIN:autoPassword_2_be
            getWidgetCache().addModel("autoPassword", w_autoPassword);
        }
        return w_autoPassword;
    }

    /** Setter for property autoPassword. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property autoPassword.
     */
    public void setAutoPasswordWV(String value) {
        CheckBoxController.updateModel(value, getAutoPasswordWM());
    }
    // .//GEN-END:autoPassword_2_be
    // .//GEN-BEGIN:notifyUser_1_be
    /** Getter for property notifyUser.
     * @return Value of property notifyUser.
     */
    public java.lang.Boolean getNotifyUser() {
        return ( (UserMaintenanceComponent) getComponent() ).getNotifyUser();
    }

    /** Setter for property notifyUser.
     * @param notifyUser New value of property notifyUser.
     */
    public void setNotifyUser(java.lang.Boolean notifyUser) {
        ( (UserMaintenanceComponent) getComponent() ).setNotifyUser(notifyUser);
    }

    /** Getter for property notifyUser. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property notifyUser.
     */
    public CheckBoxModel getNotifyUserWM() {
        CheckBoxModel w_notifyUser = (CheckBoxModel) getWidgetCache().getModel("notifyUser");
        if (w_notifyUser == null) {
            if (getNotifyUser() != null)
                w_notifyUser = new CheckBoxModel( getNotifyUser() );
            else
                w_notifyUser = new CheckBoxModel(false);
            // .//GEN-END:notifyUser_1_be
            // Add custom code //GEN-FIRST:notifyUser_1


            // .//GEN-LAST:notifyUser_1
            // .//GEN-BEGIN:notifyUser_2_be
            getWidgetCache().addModel("notifyUser", w_notifyUser);
        }
        return w_notifyUser;
    }

    /** Setter for property notifyUser. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property notifyUser.
     */
    public void setNotifyUserWV(String value) {
        CheckBoxController.updateModel(value, getNotifyUserWM());
    }
    // .//GEN-END:notifyUser_2_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return ( (UserMaintenanceComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        ( (UserMaintenanceComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel w_createdBy = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            if (getCreatedBy() != null)
                w_createdBy = new EditBoxModel(getCreatedBy(), (StringFieldMetaData) UserMeta.META_CREATED_BY);
            else
                w_createdBy = new EditBoxModel((StringFieldMetaData) UserMeta.META_CREATED_BY);
            // .//GEN-END:createdBy_1_be
            // Add custom code //GEN-FIRST:createdBy_1


            // .//GEN-LAST:createdBy_1
            // .//GEN-BEGIN:createdBy_2_be
            getWidgetCache().addModel("createdBy", w_createdBy);
        }
        return w_createdBy;
    }

    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        EditBoxController.updateModel(value, getCreatedByWM());
    }
    // .//GEN-END:createdBy_2_be
    // .//GEN-BEGIN:lastUpdatedOn_1_be
    /** Getter for property lastUpdatedOn.
     * @return Value of property lastUpdatedOn.
     */
    public org.jaffa.datatypes.DateTime getLastUpdatedOn() {
        return ( (UserMaintenanceComponent) getComponent() ).getLastUpdatedOn();
    }

    /** Setter for property lastUpdatedOn.
     * @param lastUpdatedOn New value of property lastUpdatedOn.
     */
    public void setLastUpdatedOn(org.jaffa.datatypes.DateTime lastUpdatedOn) {
        ( (UserMaintenanceComponent) getComponent() ).setLastUpdatedOn(lastUpdatedOn);
    }

    /** Getter for property lastUpdatedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastUpdatedOn.
     */
    public DateTimeModel getLastUpdatedOnWM() {
        DateTimeModel w_lastUpdatedOn = (DateTimeModel) getWidgetCache().getModel("lastUpdatedOn");
        if (w_lastUpdatedOn == null) {
            w_lastUpdatedOn = new DateTimeModel(getLastUpdatedOn(), (DateTimeFieldMetaData) UserMeta.META_LAST_UPDATED_ON);
            // .//GEN-END:lastUpdatedOn_1_be
            // Add custom code //GEN-FIRST:lastUpdatedOn_1


            // .//GEN-LAST:lastUpdatedOn_1
            // .//GEN-BEGIN:lastUpdatedOn_2_be
            getWidgetCache().addModel("lastUpdatedOn", w_lastUpdatedOn);
        }
        return w_lastUpdatedOn;
    }

    /** Setter for property lastUpdatedOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastUpdatedOn.
     */
    public void setLastUpdatedOnWV(String value) {
        DateTimeController.updateModel(value, getLastUpdatedOnWM());
    }
    // .//GEN-END:lastUpdatedOn_2_be
    // .//GEN-BEGIN:lastUpdatedBy_1_be
    /** Getter for property lastUpdatedBy.
     * @return Value of property lastUpdatedBy.
     */
    public java.lang.String getLastUpdatedBy() {
        return ( (UserMaintenanceComponent) getComponent() ).getLastUpdatedBy();
    }

    /** Setter for property lastUpdatedBy.
     * @param lastUpdatedBy New value of property lastUpdatedBy.
     */
    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        ( (UserMaintenanceComponent) getComponent() ).setLastUpdatedBy(lastUpdatedBy);
    }

    /** Getter for property lastUpdatedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastUpdatedBy.
     */
    public EditBoxModel getLastUpdatedByWM() {
        EditBoxModel w_lastUpdatedBy = (EditBoxModel) getWidgetCache().getModel("lastUpdatedBy");
        if (w_lastUpdatedBy == null) {
            if (getLastUpdatedBy() != null)
                w_lastUpdatedBy = new EditBoxModel(getLastUpdatedBy(), (StringFieldMetaData) UserMeta.META_LAST_UPDATED_BY);
            else
                w_lastUpdatedBy = new EditBoxModel((StringFieldMetaData) UserMeta.META_LAST_UPDATED_BY);
            // .//GEN-END:lastUpdatedBy_1_be
            // Add custom code //GEN-FIRST:lastUpdatedBy_1


            // .//GEN-LAST:lastUpdatedBy_1
            // .//GEN-BEGIN:lastUpdatedBy_2_be
            getWidgetCache().addModel("lastUpdatedBy", w_lastUpdatedBy);
        }
        return w_lastUpdatedBy;
    }

    /** Setter for property lastUpdatedBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastUpdatedBy.
     */
    public void setLastUpdatedByWV(String value) {
        EditBoxController.updateModel(value, getLastUpdatedByWM());
    }
    // .//GEN-END:lastUpdatedBy_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        if (!doValidate0(request))
            valid = false;

        // .//GEN-END:_doValidate_1_be
        // Add custom validation code //GEN-FIRST:_doValidate_1


        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_doValidateMain_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        boolean valid = true;
        try {
            String htmlString = getUserNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_USER_NAME, true);

            setUserName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_USER_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getFirstNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_FIRST_NAME, true);

            setFirstName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_FIRST_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getLastNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_LAST_NAME, true);

            setLastName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_LAST_NAME).getLabelToken(), e);
        }


        {
            String htmlString = getPassword1WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = Parser.parseString(htmlString);
            if (valid)
                setPassword1(value);
        }

        {
            String htmlString = getPassword2WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = Parser.parseString(htmlString);
            if (valid)
                setPassword2(value);
        }

        try {
            java.lang.String value = getStatusWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) UserMeta.META_STATUS, true);
            setStatus(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_STATUS).getLabelToken(), e);
        }


        {
            Boolean value = new Boolean(getAutoPasswordWM().getState());
            if (valid)
                setAutoPassword(value);
        }

        {
            Boolean value = new Boolean(getNotifyUserWM().getState());
            if (valid)
                setNotifyUser(value);
        }

        {
            String htmlString = getSecurityQuestion1WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = Parser.parseString(htmlString);
            if (value == null) {
                valid = false;
                raiseError(request, "SecurityQuestion1", new MandatoryFieldException("SecurityQuestion1"));
            }
            if (valid)
                setSecurityQuestion1(value);
        }

        try {
            String htmlString = getSecurityAnswerWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_SECURITY_ANSWER, true);

            setSecurityAnswer(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_SECURITY_ANSWER).getLabelToken(), e);
        }


        try {
            String htmlString = getEMailAddressWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_E_MAIL_ADDRESS, true);

            setEMailAddress(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_E_MAIL_ADDRESS).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getCreatedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserMeta.META_CREATED_ON, true);
            setCreatedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserMeta.META_CREATED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getCreatedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_CREATED_BY, true);

            setCreatedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_CREATED_BY).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getLastUpdatedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserMeta.META_LAST_UPDATED_ON, true);
            setLastUpdatedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserMeta.META_LAST_UPDATED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getLastUpdatedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserMeta.META_LAST_UPDATED_BY, true);

            setLastUpdatedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserMeta.META_LAST_UPDATED_BY).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1
        if (this.getAutoPassword() != null && this.getAutoPassword().booleanValue()) {
            String generatedPassword = generatePassword();
            this.setPassword1WV(generatedPassword);
            this.setPassword2WV(generatedPassword);
            this.setPassword1(generatedPassword);
            this.setPassword2(generatedPassword);
        }

        if (!passwordFieldCheck(request))
            valid = false;

        if (!updateUserRole(request))
            valid = false;


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // .//GEN-BEGIN:RelatedUserRole_1_be
    /** Getter for property relatedUserRole. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property relatedUserRole.
     */
    public GridModel getRelatedUserRoleWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedUserRole");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedUserRole(rows);
            getWidgetCache().addModel("relatedUserRole", rows);
        }
        return rows;
    }
    
    /** Setter for property relatedUserRole. This is invoked by the servlet, when a post is done on the screen.
     * It sets the selected rows on the model.
     * @param value New value of property relatedUserRole.
     */
    public void setRelatedUserRoleWV(String value) {
        GridController.updateModel(value, getRelatedUserRoleWM());
    }
    
    private void populateRelatedUserRole(GridModel rows) {
        rows.clearRows();
        UserRoleDto[] userRole = ((UserMaintenanceComponent) getComponent()).getRelatedObjectUserRoleDto();
        if (userRole != null) {
            GridModelRow row = null;
            for (int i = 0; i < userRole.length; i++) {
                UserRoleDto rowDto = userRole[i];
                row = rows.newRow();
                row.addElement("roleName", rowDto.getRoleName());
                // .//GEN-END:RelatedUserRole_1_be
                // Add custom code for the row //GEN-FIRST:RelatedUserRole_1


                // .//GEN-LAST:RelatedUserRole_1
                // .//GEN-BEGIN:RelatedUserRole_2_be
            }
        }
    }
    // .//GEN-END:RelatedUserRole_2_be
    // All the custom code goes here //GEN-FIRST:_custom
    /** How many columsn are there in the layout for the user roles */
    private static final int USER_ROLES_COLUMNS = 3;

    /** Getter for property userId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userId.
     */
    public GridModel getRolesGridWM() {
        GridModel w_rolesGrid = (GridModel) getWidgetCache().getModel("rolesGrid");
        if (w_rolesGrid == null) {
            w_rolesGrid = initRolesGrid();
            getWidgetCache().addModel("rolesGrid", w_rolesGrid);
        }
        return w_rolesGrid;
    }

    /** Setter for property userId. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property userId.
     */
    public void setRolesGridWV(String value) {
        GridController.updateModel(value, getRolesGridWM());
    }

    /** Initialize the model for the user roles
     */
    private GridModel initRolesGrid() {
        GridModel model = new GridModel();

        // Build current list of roles
        Collection userRole = new HashSet();
        UserRoleDto[] userRoleDtos = ((UserMaintenanceComponent) getComponent()).getRelatedObjectUserRoleDto();
        if (userRoleDtos != null && userRoleDtos.length > 0) {
            for (int i = 0; i < userRoleDtos.length; i++)
                userRole.add(userRoleDtos[i].getRoleName());
        }

        // Get all possible Roles from the roles.xml
        RoleManager roleManager = PolicyManager.getRoleManager();
        Roles root = (null != roleManager) ? roleManager.getRoles() : null ;
        if(root != null) {
            List roles = root.getRole();
            if (roles != null && roles.size() > 0) {
                GridModelRow row = null;
                int i = 0;
                for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
                    Role role = (Role) itr.next();

                    // Start of new row
                    if(i%USER_ROLES_COLUMNS == 0)
                        row = model.newRow();
                    String suffix = "" + i%USER_ROLES_COLUMNS;
                    row.addElement("rolename" + suffix, role.getName());
                    row.addElement("description" + suffix, role.getDescription());
                    CheckBoxModel cb = new CheckBoxModel(userRole.contains(role.getName()));
                    row.addElement("checkbox" + suffix, cb);
                    i++;
                }
            }
        }

        return model;
    }

    /** Perform password checks */
    private boolean passwordFieldCheck(HttpServletRequest request) {
        boolean valid = true;

        String pass1 = getPassword1WM().getValue();
        if (pass1 != null && pass1.length() == 0)
            pass1 = null;
        pass1 = Parser.parseString(pass1);

        String pass2 = getPassword2WM().getValue();
        if (pass2 != null && pass2.length() == 0)
            pass2 = null;
        pass2 = Parser.parseString(pass2);

        if (pass1 == null && ((UserMaintenanceComponent) getComponent()).isCreateMode()) {
            raiseError(request, UserMeta.META_PASSWORD.getLabelToken(), new MandatoryFieldException(UserMeta.META_PASSWORD.getLabelToken()));
            valid = false;
        }

        if (pass2 == null && ((UserMaintenanceComponent) getComponent()).isCreateMode()) {
            raiseError(request, "[label.Jaffa.Admin.User.VerifyPassword]", new MandatoryFieldException("[label.Jaffa.Admin.User.VerifyPassword]"));
            valid = false;
        }

        if((pass1 != null && !pass1.equals(pass2)) || (pass2 != null && !pass2.equals(pass1))) {
            raiseError(request, UserMeta.META_PASSWORD.getLabelToken(), new ActionMessage("error.Jaffa.Admin.UserMaintenance.InvalidPassword.Different"));
            valid = false;
        }

        return valid;
    }

    /** Build the list of selected roles from the contents of the rolesGrid and update the component
     */
    private boolean updateUserRole(HttpServletRequest request) {
        // Build the list of selected roles
        List userRole = new ArrayList();
        GridModel model = getRolesGridWM();
        for(Iterator it = model.getRows().iterator(); it.hasNext(); ) {
            GridModelRow row = (GridModelRow) it.next();
            for(int i = 0; i < USER_ROLES_COLUMNS; i++) {
                String suffix = "" + i;
                CheckBoxModel cb = (CheckBoxModel) row.get("checkbox" + suffix);
                String key = (String) row.get("rolename" + suffix);
                if(key != null && cb != null && cb.getState())
                    userRole.add(key);
            }
        }

        // Add roles marked as to-be-included for each role in the userRole list.
        boolean valid = true;
        while (!processIncludes(request, userRole)) {
            valid = false;
            // Add roles marked as to-be-included for additional roles that have been added to the list.
            continue;
        }
        if (!valid)
            return false;

        // check for excluded roles
        if (!processExcludes(request, userRole))
            return false;

        // finally update the component
        UserRoleDto[] userRoleDtos = new UserRoleDto[userRole.size()];
        for (int i = 0; i < userRole.size(); i++) {
            UserRoleDto userRoleDto = new UserRoleDto();
            userRoleDto.setRoleName((String) userRole.get(i));
            userRoleDtos[i] = userRoleDto;
        }
        ((UserMaintenanceComponent) getComponent()).setRelatedObjectUserRoleDto(userRoleDtos);
        return true;
    }

    private boolean processIncludes(HttpServletRequest request, Collection userRole) {
        boolean noNewElement = true;
        RoleManager roleManager = PolicyManager.getRoleManager();
        Roles root = (null != roleManager) ? roleManager.getRoles() : null ;
        if(root != null) {
            List roleObjects = root.getRole();
            if(roleObjects != null) {
                for (Iterator it = roleObjects.iterator(); it.hasNext(); ) {
                    Role role = (Role) it.next();
                    if (userRole.contains(role.getName())) {
                        List includes = role.getInclude();
                        if (includes != null) {
                            for(Iterator it2 = includes.iterator(); it2.hasNext(); ) {
                                Include includedObject = (Include) it2.next();
                                String includeName = includedObject.getName();
                                if (!userRole.contains(includeName)) {
                                    userRole.add(includeName);
                                    updateRolesModel(includeName);
                                    raiseError(request, "roles", new ActionMessage("error.Jaffa.Admin.UserMaintenance.AddRolesSelection", "" + role.getName() , "" + includeName));
                                    noNewElement = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return noNewElement;
    }

    private boolean processExcludes(HttpServletRequest request, Collection userRole) {
        boolean valid = true;
        boolean foundExcludedRole = false;
        RoleManager roleManager = PolicyManager.getRoleManager();
        Roles root = (null != roleManager) ? roleManager.getRoles() : null ;
        if(root != null) {
            List roleObjects = root.getRole();
            if(roleObjects != null) {
                for (Iterator it = roleObjects.iterator(); it.hasNext(); ) {
                    Role role = (Role)it.next();
                    if (userRole.contains(role.getName())) {
                        List excludes = role.getExclude();
                        if ((excludes != null) && (!foundExcludedRole)) {
                            StringBuffer excludedRoles = new StringBuffer();
                            for(Iterator it2 = excludes.iterator(); it2.hasNext(); ) {
                                Exclude excludedObject = (Exclude)it2.next();
                                String  excludeName = excludedObject.getName();
                                if (userRole.contains(excludeName)) {
                                    foundExcludedRole = true;
                                    valid = false;
                                }
                                if (excludedRoles.length() == 0)
                                    excludedRoles.append(excludeName);
                                else
                                    excludedRoles.append("," + excludeName);
                            }
                            if (foundExcludedRole)
                                raiseError(request, "roles", new ActionMessage("error.Jaffa.Admin.UserMaintenance.ExcludedRolesSelection", "" + role.getName() , "" + excludedRoles));
                        }
                    }
                }
            }
        }
        return valid;
    }

    private void updateRolesModel(String selectedRoleKey) {
        GridModel model = getRolesGridWM();
        for(Iterator it = model.getRows().iterator(); it.hasNext(); ) {
            GridModelRow row = (GridModelRow) it.next();
            for(int i = 0; i < USER_ROLES_COLUMNS; i++) {
                String suffix = "" + i;
                CheckBoxModel cb = (CheckBoxModel) row.get("checkbox" + suffix);
                String key = (String) row.get("rolename" + suffix);
                if(key != null && cb != null) {
                    if(key.equals(selectedRoleKey)) {
                        if(log.isDebugEnabled())
                            log.debug("Setting state for the " + key + "checkbox");
                        cb.setState(true);
                        return;
                    }
                }
            }
        }
    }

   public static final char[] NUMBERS_AND_LETTERS_ALPHABET = {
        'A','B','C','D','E','F','G','H',
        'I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X',
        'Y','Z','a','b','c','d','e','f',
        'g','h','i','j','k','l','m','n',
        'o','p','q','r','s','t','u','v',
        'w','x','y','z','0','1','2','3',
        '4','5','6','7','8','9',
    };


    public String generatePassword() {

        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i < 10 ; i++) {
            int random = 1 + (int) (Math.random() * (61 - 1 + 1));
            sb.append(NUMBERS_AND_LETTERS_ALPHABET[random]);
        }
        return sb.toString();
    }
    // .//GEN-LAST:_custom
}
