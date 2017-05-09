// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.util.StringHelper;

import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.*;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.util.URLHelper;
import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;
import org.apache.struts.action.ActionMessage;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the UserRequestMaintenance.
 */
public class UserRequestMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(UserRequestMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:requestId_1_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getRequestId();
    }

    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(java.lang.Long requestId) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setRequestId(requestId);
    }

    /** Getter for property requestId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property requestId.
     */
    public EditBoxModel getRequestIdWM() {
        EditBoxModel w_requestId = (EditBoxModel) getWidgetCache().getModel("requestId");
        if (w_requestId == null) {
            if (getRequestId() != null)
                w_requestId = new EditBoxModel(getRequestId(), (IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID);
            else
                w_requestId = new EditBoxModel((IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID);
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
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel w_userName = (EditBoxModel) getWidgetCache().getModel("userName");
        if (w_userName == null) {
            if (getUserName() != null)
                w_userName = new EditBoxModel(getUserName(), (StringFieldMetaData) UserRequestMeta.META_USER_NAME);
            else
                w_userName = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_USER_NAME);
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
        return ( (UserRequestMaintenanceComponent) getComponent() ).getFirstName();
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(java.lang.String firstName) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setFirstName(firstName);
    }

    /** Getter for property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstName.
     */
    public EditBoxModel getFirstNameWM() {
        EditBoxModel w_firstName = (EditBoxModel) getWidgetCache().getModel("firstName");
        if (w_firstName == null) {
            if (getFirstName() != null)
                w_firstName = new EditBoxModel(getFirstName(), (StringFieldMetaData) UserRequestMeta.META_FIRST_NAME);
            else
                w_firstName = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_FIRST_NAME);
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
        return ( (UserRequestMaintenanceComponent) getComponent() ).getLastName();
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(java.lang.String lastName) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setLastName(lastName);
    }

    /** Getter for property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastName.
     */
    public EditBoxModel getLastNameWM() {
        EditBoxModel w_lastName = (EditBoxModel) getWidgetCache().getModel("lastName");
        if (w_lastName == null) {
            if (getLastName() != null)
                w_lastName = new EditBoxModel(getLastName(), (StringFieldMetaData) UserRequestMeta.META_LAST_NAME);
            else
                w_lastName = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_LAST_NAME);
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
        return ( (UserRequestMaintenanceComponent) getComponent() ).getPassword1();
    }

    /** Setter for property password1.
     * @param password1 New value of property password1.
     */
    public void setPassword1(java.lang.String password1) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setPassword1(password1);
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
    // .//GEN-BEGIN:password2_1_be
    /** Getter for property password2.
     * @return Value of property password2.
     */
    public java.lang.String getPassword2() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getPassword2();
    }

    /** Setter for property password2.
     * @param password2 New value of property password2.
     */
    public void setPassword2(java.lang.String password2) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setPassword2(password2);
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
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public java.lang.String getEMailAddress() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getEMailAddress();
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(java.lang.String eMailAddress) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setEMailAddress(eMailAddress);
    }

    /** Getter for property eMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eMailAddress.
     */
    public EditBoxModel getEMailAddressWM() {
        EditBoxModel w_eMailAddress = (EditBoxModel) getWidgetCache().getModel("eMailAddress");
        if (w_eMailAddress == null) {
            if (getEMailAddress() != null)
                w_eMailAddress = new EditBoxModel(getEMailAddress(), (StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS);
            else
                w_eMailAddress = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS);
            // .//GEN-END:eMailAddress_1_be
            // Add custom code //GEN-FIRST:eMailAddress_1
            w_eMailAddress.setMandatory(true);

            // .//GEN-LAST:eMailAddress_1
            // .//GEN-BEGIN:eMailAddress_2_be
            getWidgetCache().addModel("eMailAddress", w_eMailAddress);
        }
        return w_eMailAddress;
    }

    /** Setter for property eMailAddress. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property eMailAddress.
     */
    public void setEMailAddressWV(String value) {
        EditBoxController.updateModel(value, getEMailAddressWM());
    }
    // .//GEN-END:eMailAddress_2_be
    // .//GEN-BEGIN:securityQuestion_1_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getSecurityQuestion();
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.Long securityQuestion) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setSecurityQuestion(securityQuestion);
    }

    /** Getter for property securityQuestion. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestion.
     */
    public EditBoxModel getSecurityQuestionWM() {
        EditBoxModel w_securityQuestion = (EditBoxModel) getWidgetCache().getModel("securityQuestion");
        if (w_securityQuestion == null) {
            if (getSecurityQuestion() != null)
                w_securityQuestion = new EditBoxModel(getSecurityQuestion(), (IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION);
            else
                w_securityQuestion = new EditBoxModel((IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION);
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
        return ( (UserRequestMaintenanceComponent) getComponent() ).getSecurityQuestion1();
    }

    /** Setter for property securityQuestion1.
     * @param securityQuestion1 New value of property securityQuestion1.
     */
    public void setSecurityQuestion1(java.lang.String securityQuestion1) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setSecurityQuestion1(securityQuestion1);
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
        return ( (UserRequestMaintenanceComponent) getComponent() ).getSecurityAnswer();
    }

    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(java.lang.String securityAnswer) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setSecurityAnswer(securityAnswer);
    }

    /** Getter for property securityAnswer. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityAnswer.
     */
    public EditBoxModel getSecurityAnswerWM() {
        EditBoxModel w_securityAnswer = (EditBoxModel) getWidgetCache().getModel("securityAnswer");
        if (w_securityAnswer == null) {
            if (getSecurityAnswer() != null)
                w_securityAnswer = new EditBoxModel(getSecurityAnswer(), (StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER);
            else
                w_securityAnswer = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER);
            // .//GEN-END:securityAnswer_1_be
            // Add custom code //GEN-FIRST:securityAnswer_1


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
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel w_remarks = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (w_remarks == null) {
            if (getRemarks() != null)
                w_remarks = new EditBoxModel(getRemarks(), (StringFieldMetaData) UserRequestMeta.META_REMARKS);
            else
                w_remarks = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_REMARKS);
            // .//GEN-END:remarks_1_be
            // Add custom code //GEN-FIRST:remarks_1


            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", w_remarks);
        }
        return w_remarks;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }
    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) UserRequestMeta.META_CREATED_ON);
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
    // .//GEN-BEGIN:processedDatetime_1_be
    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public org.jaffa.datatypes.DateTime getProcessedDatetime() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getProcessedDatetime();
    }

    /** Setter for property processedDatetime.
     * @param processedDatetime New value of property processedDatetime.
     */
    public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setProcessedDatetime(processedDatetime);
    }

    /** Getter for property processedDatetime. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedDatetime.
     */
    public DateTimeModel getProcessedDatetimeWM() {
        DateTimeModel w_processedDatetime = (DateTimeModel) getWidgetCache().getModel("processedDatetime");
        if (w_processedDatetime == null) {
            w_processedDatetime = new DateTimeModel(getProcessedDatetime(), (DateTimeFieldMetaData) UserRequestMeta.META_PROCESSED_DATETIME);
            // .//GEN-END:processedDatetime_1_be
            // Add custom code //GEN-FIRST:processedDatetime_1


            // .//GEN-LAST:processedDatetime_1
            // .//GEN-BEGIN:processedDatetime_2_be
            getWidgetCache().addModel("processedDatetime", w_processedDatetime);
        }
        return w_processedDatetime;
    }

    /** Setter for property processedDatetime. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property processedDatetime.
     */
    public void setProcessedDatetimeWV(String value) {
        DateTimeController.updateModel(value, getProcessedDatetimeWM());
    }
    // .//GEN-END:processedDatetime_2_be
    // .//GEN-BEGIN:processedUserId_1_be
    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public java.lang.String getProcessedUserId() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getProcessedUserId();
    }

    /** Setter for property processedUserId.
     * @param processedUserId New value of property processedUserId.
     */
    public void setProcessedUserId(java.lang.String processedUserId) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setProcessedUserId(processedUserId);
    }

    /** Getter for property processedUserId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedUserId.
     */
    public EditBoxModel getProcessedUserIdWM() {
        EditBoxModel w_processedUserId = (EditBoxModel) getWidgetCache().getModel("processedUserId");
        if (w_processedUserId == null) {
            if (getProcessedUserId() != null)
                w_processedUserId = new EditBoxModel(getProcessedUserId(), (StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID);
            else
                w_processedUserId = new EditBoxModel((StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID);
            // .//GEN-END:processedUserId_1_be
            // Add custom code //GEN-FIRST:processedUserId_1


            // .//GEN-LAST:processedUserId_1
            // .//GEN-BEGIN:processedUserId_2_be
            getWidgetCache().addModel("processedUserId", w_processedUserId);
        }
        return w_processedUserId;
    }

    /** Setter for property processedUserId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property processedUserId.
     */
    public void setProcessedUserIdWV(String value) {
        EditBoxController.updateModel(value, getProcessedUserIdWM());
    }
    // .//GEN-END:processedUserId_2_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return ( (UserRequestMaintenanceComponent) getComponent() ).getStatus();
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(java.lang.String status) {
        ( (UserRequestMaintenanceComponent) getComponent() ).setStatus(status);
    }

    /** Getter for property status. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property status.
     */
    public DropDownModel getStatusWM() {
        DropDownModel w_status = (DropDownModel) getWidgetCache().getModel("status");
        if (w_status == null) {
            w_status = new DropDownModel((getStatus() != null ? getStatus() : ""), (StringFieldMetaData) UserRequestMeta.META_STATUS);
            w_status.addOption("[label.Jaffa.User.UserRequest.Status.O]", "O");
            w_status.addOption("[label.Jaffa.User.UserRequest.Status.S]", "S");
            w_status.addOption("[label.Jaffa.User.UserRequest.Status.C]", "C");

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
            String htmlString = getRequestIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID, true);

            setRequestId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID).getLabelToken(), e);
        }


        try {
            String htmlString = getUserNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_USER_NAME, true);

            setUserName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_USER_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getFirstNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_FIRST_NAME, true);

            setFirstName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_FIRST_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getLastNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_LAST_NAME, true);

            setLastName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_LAST_NAME).getLabelToken(), e);
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
            String htmlString = getEMailAddressWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS, true);

            setEMailAddress(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS).getLabelToken(), e);
        }


        try {
            String htmlString = getSecurityQuestionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION, true);

            setSecurityQuestion(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION).getLabelToken(), e);
        }


        try {
            String htmlString = getSecurityAnswerWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER, true);

            setSecurityAnswer(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER).getLabelToken(), e);
        }


        try {
            String htmlString = getRemarksWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_REMARKS, true);

            setRemarks(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_REMARKS).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getCreatedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserRequestMeta.META_CREATED_ON, true);
            setCreatedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserRequestMeta.META_CREATED_ON).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getProcessedDatetimeWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserRequestMeta.META_PROCESSED_DATETIME, true);
            setProcessedDatetime(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserRequestMeta.META_PROCESSED_DATETIME).getLabelToken(), e);
        }


        try {
            String htmlString = getProcessedUserIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID, true);

            setProcessedUserId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID).getLabelToken(), e);
        }


        try {
            java.lang.String value = getStatusWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) UserRequestMeta.META_STATUS, true);
            setStatus(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserRequestMeta.META_STATUS).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1

        log.debug("The value of EmailAddy is !!!!!!!!!!!!!!!!!!!!!!!!!!!! " + this.getEMailAddress() + "its model value is " + (this.getEMailAddressWM() != null ? this.getEMailAddressWM().getValue() : "its null :-("));

        try {
            String htmlString = getSecurityQuestion1WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            setSecurityQuestion1(htmlString);
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION, true);
            setSecurityQuestion(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION).getLabelToken(), e);
        }

    if (!passwordFieldCheck(request))
            valid = false;

    StringBuffer buf = new StringBuffer();
        buf.append(URLHelper.getBase(request));
        buf.append("startComponent.do?component=Jaffa.User.UserMaintenance&requestId=");
        ( (UserRequestMaintenanceComponent) getComponent() ).setUrl(buf.toString());




        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here //GEN-FIRST:_custom


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

        if (pass1 == null && ((UserRequestMaintenanceComponent) getComponent()).isCreateMode()) {
            raiseError(request, UserMeta.META_PASSWORD.getLabelToken(), new MandatoryFieldException(UserMeta.META_PASSWORD.getLabelToken()));
            valid = false;
        }

        if (pass2 == null && ((UserRequestMaintenanceComponent) getComponent()).isCreateMode()) {
            raiseError(request, "[label.Jaffa.User.User.VerifyPassword]", new MandatoryFieldException("[label.Jaffa.User.User.VerifyPassword]"));
            valid = false;
        }

        if((pass1 != null && !pass1.equals(pass2)) || (pass2 != null && !pass2.equals(pass1))) {
            raiseError(request, UserMeta.META_PASSWORD.getLabelToken(), new ActionMessage("error.Jaffa.User.UserMaintenance.InvalidPassword.Different"));
            valid = false;
        }

        return valid;
    }



    // .//GEN-LAST:_custom
}
