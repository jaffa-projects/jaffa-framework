// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderOutDto;
import org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderOutRowDto;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support UserRequestFinder.
 */
public class UserRequestFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(UserRequestFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:requestId_1_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public String getRequestId() {
        return ( (UserRequestFinderComponent) getComponent() ).getRequestId();
    }

    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(String requestId) {
        ( (UserRequestFinderComponent) getComponent() ).setRequestId(requestId);
    }

    /** Getter for property requestId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property requestId.
     */
    public EditBoxModel getRequestIdWM() {
        EditBoxModel requestIdModel = (EditBoxModel) getWidgetCache().getModel("requestId");
        if (requestIdModel == null) {
            if (getRequestId() != null)
                requestIdModel = new EditBoxModel( getRequestId() );
            else
                requestIdModel = new EditBoxModel();

            // .//GEN-END:requestId_1_be
            // Add custom code //GEN-FIRST:requestId_1


            // .//GEN-LAST:requestId_1
            // .//GEN-BEGIN:requestId_2_be
            getWidgetCache().addModel("requestId", requestIdModel);
        }
        return requestIdModel;
    }

    /** Setter for property requestId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property requestId.
     */
    public void setRequestIdWV(String value) {
        EditBoxController.updateModel(value, getRequestIdWM());
    }

    /** Getter for DropDown property requestId.
     * @return Value of property requestIdDd.
     */
    public String getRequestIdDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getRequestIdDd();
    }

    /** Setter for DropDown property requestId.
     * @param requestIdDd New value of property requestIdDd.
     */
    public void setRequestIdDd(String requestIdDd) {
        ( (UserRequestFinderComponent) getComponent() ).setRequestIdDd(requestIdDd);
    }

    /** Getter for DropDown property requestId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property requestIdDd.
     */
    public DropDownModel getRequestIdDdWM() {
        DropDownModel requestIdDdModel = (DropDownModel) getWidgetCache().getModel("requestIdDd");
        if (requestIdDdModel == null) {
            if (getRequestIdDd() != null)
                requestIdDdModel = new DropDownModel( getRequestIdDd() );
            else
                requestIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                requestIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("requestIdDd", requestIdDdModel);
        }
        return requestIdDdModel;
    }

    /** Setter for DropDown property requestId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property requestIdDd.
     */
    public void setRequestIdDdWV(String value) {
        DropDownController.updateModel(value, getRequestIdDdWM());
    }

    // .//GEN-END:requestId_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return ( (UserRequestFinderComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        ( (UserRequestFinderComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel userNameModel = (EditBoxModel) getWidgetCache().getModel("userName");
        if (userNameModel == null) {
            if (getUserName() != null)
                userNameModel = new EditBoxModel( getUserName() );
            else
                userNameModel = new EditBoxModel();
            userNameModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_USER_NAME).getCaseType() );

            // .//GEN-END:userName_1_be
            // Add custom code //GEN-FIRST:userName_1


            // .//GEN-LAST:userName_1
            // .//GEN-BEGIN:userName_2_be
            getWidgetCache().addModel("userName", userNameModel);
        }
        return userNameModel;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }

    /** Getter for DropDown property userName.
     * @return Value of property userNameDd.
     */
    public String getUserNameDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getUserNameDd();
    }

    /** Setter for DropDown property userName.
     * @param userNameDd New value of property userNameDd.
     */
    public void setUserNameDd(String userNameDd) {
        ( (UserRequestFinderComponent) getComponent() ).setUserNameDd(userNameDd);
    }

    /** Getter for DropDown property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userNameDd.
     */
    public DropDownModel getUserNameDdWM() {
        DropDownModel userNameDdModel = (DropDownModel) getWidgetCache().getModel("userNameDd");
        if (userNameDdModel == null) {
            if (getUserNameDd() != null)
                userNameDdModel = new DropDownModel( getUserNameDd() );
            else
                userNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                userNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("userNameDd", userNameDdModel);
        }
        return userNameDdModel;
    }

    /** Setter for DropDown property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userNameDd.
     */
    public void setUserNameDdWV(String value) {
        DropDownController.updateModel(value, getUserNameDdWM());
    }

    // .//GEN-END:userName_2_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public String getFirstName() {
        return ( (UserRequestFinderComponent) getComponent() ).getFirstName();
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName) {
        ( (UserRequestFinderComponent) getComponent() ).setFirstName(firstName);
    }

    /** Getter for property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstName.
     */
    public EditBoxModel getFirstNameWM() {
        EditBoxModel firstNameModel = (EditBoxModel) getWidgetCache().getModel("firstName");
        if (firstNameModel == null) {
            if (getFirstName() != null)
                firstNameModel = new EditBoxModel( getFirstName() );
            else
                firstNameModel = new EditBoxModel();
            firstNameModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_FIRST_NAME).getCaseType() );

            // .//GEN-END:firstName_1_be
            // Add custom code //GEN-FIRST:firstName_1


            // .//GEN-LAST:firstName_1
            // .//GEN-BEGIN:firstName_2_be
            getWidgetCache().addModel("firstName", firstNameModel);
        }
        return firstNameModel;
    }

    /** Setter for property firstName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property firstName.
     */
    public void setFirstNameWV(String value) {
        EditBoxController.updateModel(value, getFirstNameWM());
    }

    /** Getter for DropDown property firstName.
     * @return Value of property firstNameDd.
     */
    public String getFirstNameDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getFirstNameDd();
    }

    /** Setter for DropDown property firstName.
     * @param firstNameDd New value of property firstNameDd.
     */
    public void setFirstNameDd(String firstNameDd) {
        ( (UserRequestFinderComponent) getComponent() ).setFirstNameDd(firstNameDd);
    }

    /** Getter for DropDown property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstNameDd.
     */
    public DropDownModel getFirstNameDdWM() {
        DropDownModel firstNameDdModel = (DropDownModel) getWidgetCache().getModel("firstNameDd");
        if (firstNameDdModel == null) {
            if (getFirstNameDd() != null)
                firstNameDdModel = new DropDownModel( getFirstNameDd() );
            else
                firstNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                firstNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("firstNameDd", firstNameDdModel);
        }
        return firstNameDdModel;
    }

    /** Setter for DropDown property firstName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property firstNameDd.
     */
    public void setFirstNameDdWV(String value) {
        DropDownController.updateModel(value, getFirstNameDdWM());
    }

    // .//GEN-END:firstName_2_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public String getLastName() {
        return ( (UserRequestFinderComponent) getComponent() ).getLastName();
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(String lastName) {
        ( (UserRequestFinderComponent) getComponent() ).setLastName(lastName);
    }

    /** Getter for property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastName.
     */
    public EditBoxModel getLastNameWM() {
        EditBoxModel lastNameModel = (EditBoxModel) getWidgetCache().getModel("lastName");
        if (lastNameModel == null) {
            if (getLastName() != null)
                lastNameModel = new EditBoxModel( getLastName() );
            else
                lastNameModel = new EditBoxModel();
            lastNameModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_LAST_NAME).getCaseType() );

            // .//GEN-END:lastName_1_be
            // Add custom code //GEN-FIRST:lastName_1


            // .//GEN-LAST:lastName_1
            // .//GEN-BEGIN:lastName_2_be
            getWidgetCache().addModel("lastName", lastNameModel);
        }
        return lastNameModel;
    }

    /** Setter for property lastName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastName.
     */
    public void setLastNameWV(String value) {
        EditBoxController.updateModel(value, getLastNameWM());
    }

    /** Getter for DropDown property lastName.
     * @return Value of property lastNameDd.
     */
    public String getLastNameDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getLastNameDd();
    }

    /** Setter for DropDown property lastName.
     * @param lastNameDd New value of property lastNameDd.
     */
    public void setLastNameDd(String lastNameDd) {
        ( (UserRequestFinderComponent) getComponent() ).setLastNameDd(lastNameDd);
    }

    /** Getter for DropDown property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastNameDd.
     */
    public DropDownModel getLastNameDdWM() {
        DropDownModel lastNameDdModel = (DropDownModel) getWidgetCache().getModel("lastNameDd");
        if (lastNameDdModel == null) {
            if (getLastNameDd() != null)
                lastNameDdModel = new DropDownModel( getLastNameDd() );
            else
                lastNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastNameDd", lastNameDdModel);
        }
        return lastNameDdModel;
    }

    /** Setter for DropDown property lastName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastNameDd.
     */
    public void setLastNameDdWV(String value) {
        DropDownController.updateModel(value, getLastNameDdWM());
    }

    // .//GEN-END:lastName_2_be
    // .//GEN-BEGIN:password_1_be
    /** Getter for property password.
     * @return Value of property password.
     */
    public String getPassword() {
        return ( (UserRequestFinderComponent) getComponent() ).getPassword();
    }

    /** Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(String password) {
        ( (UserRequestFinderComponent) getComponent() ).setPassword(password);
    }

    /** Getter for property password. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property password.
     */
    public EditBoxModel getPasswordWM() {
        EditBoxModel passwordModel = (EditBoxModel) getWidgetCache().getModel("password");
        if (passwordModel == null) {
            if (getPassword() != null)
                passwordModel = new EditBoxModel( getPassword() );
            else
                passwordModel = new EditBoxModel();
            passwordModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_PASSWORD).getCaseType() );

            // .//GEN-END:password_1_be
            // Add custom code //GEN-FIRST:password_1


            // .//GEN-LAST:password_1
            // .//GEN-BEGIN:password_2_be
            getWidgetCache().addModel("password", passwordModel);
        }
        return passwordModel;
    }

    /** Setter for property password. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property password.
     */
    public void setPasswordWV(String value) {
        EditBoxController.updateModel(value, getPasswordWM());
    }

    /** Getter for DropDown property password.
     * @return Value of property passwordDd.
     */
    public String getPasswordDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getPasswordDd();
    }

    /** Setter for DropDown property password.
     * @param passwordDd New value of property passwordDd.
     */
    public void setPasswordDd(String passwordDd) {
        ( (UserRequestFinderComponent) getComponent() ).setPasswordDd(passwordDd);
    }

    /** Getter for DropDown property password. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property passwordDd.
     */
    public DropDownModel getPasswordDdWM() {
        DropDownModel passwordDdModel = (DropDownModel) getWidgetCache().getModel("passwordDd");
        if (passwordDdModel == null) {
            if (getPasswordDd() != null)
                passwordDdModel = new DropDownModel( getPasswordDd() );
            else
                passwordDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                passwordDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("passwordDd", passwordDdModel);
        }
        return passwordDdModel;
    }

    /** Setter for DropDown property password. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property passwordDd.
     */
    public void setPasswordDdWV(String value) {
        DropDownController.updateModel(value, getPasswordDdWM());
    }

    // .//GEN-END:password_2_be
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public String getEMailAddress() {
        return ( (UserRequestFinderComponent) getComponent() ).getEMailAddress();
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(String eMailAddress) {
        ( (UserRequestFinderComponent) getComponent() ).setEMailAddress(eMailAddress);
    }

    /** Getter for property eMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eMailAddress.
     */
    public EditBoxModel getEMailAddressWM() {
        EditBoxModel eMailAddressModel = (EditBoxModel) getWidgetCache().getModel("eMailAddress");
        if (eMailAddressModel == null) {
            if (getEMailAddress() != null)
                eMailAddressModel = new EditBoxModel( getEMailAddress() );
            else
                eMailAddressModel = new EditBoxModel();
            eMailAddressModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_E_MAIL_ADDRESS).getCaseType() );

            // .//GEN-END:eMailAddress_1_be
            // Add custom code //GEN-FIRST:eMailAddress_1


            // .//GEN-LAST:eMailAddress_1
            // .//GEN-BEGIN:eMailAddress_2_be
            getWidgetCache().addModel("eMailAddress", eMailAddressModel);
        }
        return eMailAddressModel;
    }

    /** Setter for property eMailAddress. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eMailAddress.
     */
    public void setEMailAddressWV(String value) {
        EditBoxController.updateModel(value, getEMailAddressWM());
    }

    /** Getter for DropDown property eMailAddress.
     * @return Value of property eMailAddressDd.
     */
    public String getEMailAddressDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getEMailAddressDd();
    }

    /** Setter for DropDown property eMailAddress.
     * @param eMailAddressDd New value of property eMailAddressDd.
     */
    public void setEMailAddressDd(String eMailAddressDd) {
        ( (UserRequestFinderComponent) getComponent() ).setEMailAddressDd(eMailAddressDd);
    }

    /** Getter for DropDown property eMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eMailAddressDd.
     */
    public DropDownModel getEMailAddressDdWM() {
        DropDownModel eMailAddressDdModel = (DropDownModel) getWidgetCache().getModel("eMailAddressDd");
        if (eMailAddressDdModel == null) {
            if (getEMailAddressDd() != null)
                eMailAddressDdModel = new DropDownModel( getEMailAddressDd() );
            else
                eMailAddressDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                eMailAddressDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("eMailAddressDd", eMailAddressDdModel);
        }
        return eMailAddressDdModel;
    }

    /** Setter for DropDown property eMailAddress. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eMailAddressDd.
     */
    public void setEMailAddressDdWV(String value) {
        DropDownController.updateModel(value, getEMailAddressDdWM());
    }

    // .//GEN-END:eMailAddress_2_be
    // .//GEN-BEGIN:securityQuestion_1_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public String getSecurityQuestion() {
        return ( (UserRequestFinderComponent) getComponent() ).getSecurityQuestion();
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(String securityQuestion) {
        ( (UserRequestFinderComponent) getComponent() ).setSecurityQuestion(securityQuestion);
    }

    /** Getter for property securityQuestion. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestion.
     */
    public EditBoxModel getSecurityQuestionWM() {
        EditBoxModel securityQuestionModel = (EditBoxModel) getWidgetCache().getModel("securityQuestion");
        if (securityQuestionModel == null) {
            if (getSecurityQuestion() != null)
                securityQuestionModel = new EditBoxModel( getSecurityQuestion() );
            else
                securityQuestionModel = new EditBoxModel();

            // .//GEN-END:securityQuestion_1_be
            // Add custom code //GEN-FIRST:securityQuestion_1


            // .//GEN-LAST:securityQuestion_1
            // .//GEN-BEGIN:securityQuestion_2_be
            getWidgetCache().addModel("securityQuestion", securityQuestionModel);
        }
        return securityQuestionModel;
    }

    /** Setter for property securityQuestion. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property securityQuestion.
     */
    public void setSecurityQuestionWV(String value) {
        EditBoxController.updateModel(value, getSecurityQuestionWM());
    }

    /** Getter for DropDown property securityQuestion.
     * @return Value of property securityQuestionDd.
     */
    public String getSecurityQuestionDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getSecurityQuestionDd();
    }

    /** Setter for DropDown property securityQuestion.
     * @param securityQuestionDd New value of property securityQuestionDd.
     */
    public void setSecurityQuestionDd(String securityQuestionDd) {
        ( (UserRequestFinderComponent) getComponent() ).setSecurityQuestionDd(securityQuestionDd);
    }

    /** Getter for DropDown property securityQuestion. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestionDd.
     */
    public DropDownModel getSecurityQuestionDdWM() {
        DropDownModel securityQuestionDdModel = (DropDownModel) getWidgetCache().getModel("securityQuestionDd");
        if (securityQuestionDdModel == null) {
            if (getSecurityQuestionDd() != null)
                securityQuestionDdModel = new DropDownModel( getSecurityQuestionDd() );
            else
                securityQuestionDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                securityQuestionDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("securityQuestionDd", securityQuestionDdModel);
        }
        return securityQuestionDdModel;
    }

    /** Setter for DropDown property securityQuestion. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property securityQuestionDd.
     */
    public void setSecurityQuestionDdWV(String value) {
        DropDownController.updateModel(value, getSecurityQuestionDdWM());
    }

    // .//GEN-END:securityQuestion_2_be
    // .//GEN-BEGIN:securityAnswer_1_be
    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public String getSecurityAnswer() {
        return ( (UserRequestFinderComponent) getComponent() ).getSecurityAnswer();
    }

    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(String securityAnswer) {
        ( (UserRequestFinderComponent) getComponent() ).setSecurityAnswer(securityAnswer);
    }

    /** Getter for property securityAnswer. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityAnswer.
     */
    public EditBoxModel getSecurityAnswerWM() {
        EditBoxModel securityAnswerModel = (EditBoxModel) getWidgetCache().getModel("securityAnswer");
        if (securityAnswerModel == null) {
            if (getSecurityAnswer() != null)
                securityAnswerModel = new EditBoxModel( getSecurityAnswer() );
            else
                securityAnswerModel = new EditBoxModel();
            securityAnswerModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_SECURITY_ANSWER).getCaseType() );

            // .//GEN-END:securityAnswer_1_be
            // Add custom code //GEN-FIRST:securityAnswer_1


            // .//GEN-LAST:securityAnswer_1
            // .//GEN-BEGIN:securityAnswer_2_be
            getWidgetCache().addModel("securityAnswer", securityAnswerModel);
        }
        return securityAnswerModel;
    }

    /** Setter for property securityAnswer. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property securityAnswer.
     */
    public void setSecurityAnswerWV(String value) {
        EditBoxController.updateModel(value, getSecurityAnswerWM());
    }

    /** Getter for DropDown property securityAnswer.
     * @return Value of property securityAnswerDd.
     */
    public String getSecurityAnswerDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getSecurityAnswerDd();
    }

    /** Setter for DropDown property securityAnswer.
     * @param securityAnswerDd New value of property securityAnswerDd.
     */
    public void setSecurityAnswerDd(String securityAnswerDd) {
        ( (UserRequestFinderComponent) getComponent() ).setSecurityAnswerDd(securityAnswerDd);
    }

    /** Getter for DropDown property securityAnswer. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityAnswerDd.
     */
    public DropDownModel getSecurityAnswerDdWM() {
        DropDownModel securityAnswerDdModel = (DropDownModel) getWidgetCache().getModel("securityAnswerDd");
        if (securityAnswerDdModel == null) {
            if (getSecurityAnswerDd() != null)
                securityAnswerDdModel = new DropDownModel( getSecurityAnswerDd() );
            else
                securityAnswerDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                securityAnswerDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("securityAnswerDd", securityAnswerDdModel);
        }
        return securityAnswerDdModel;
    }

    /** Setter for DropDown property securityAnswer. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property securityAnswerDd.
     */
    public void setSecurityAnswerDdWV(String value) {
        DropDownController.updateModel(value, getSecurityAnswerDdWM());
    }

    // .//GEN-END:securityAnswer_2_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return ( (UserRequestFinderComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        ( (UserRequestFinderComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel remarksModel = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (remarksModel == null) {
            if (getRemarks() != null)
                remarksModel = new EditBoxModel( getRemarks() );
            else
                remarksModel = new EditBoxModel();
            remarksModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_REMARKS).getCaseType() );

            // .//GEN-END:remarks_1_be
            // Add custom code //GEN-FIRST:remarks_1


            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", remarksModel);
        }
        return remarksModel;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }

    /** Getter for DropDown property remarks.
     * @return Value of property remarksDd.
     */
    public String getRemarksDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getRemarksDd();
    }

    /** Setter for DropDown property remarks.
     * @param remarksDd New value of property remarksDd.
     */
    public void setRemarksDd(String remarksDd) {
        ( (UserRequestFinderComponent) getComponent() ).setRemarksDd(remarksDd);
    }

    /** Getter for DropDown property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarksDd.
     */
    public DropDownModel getRemarksDdWM() {
        DropDownModel remarksDdModel = (DropDownModel) getWidgetCache().getModel("remarksDd");
        if (remarksDdModel == null) {
            if (getRemarksDd() != null)
                remarksDdModel = new DropDownModel( getRemarksDd() );
            else
                remarksDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                remarksDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("remarksDd", remarksDdModel);
        }
        return remarksDdModel;
    }

    /** Setter for DropDown property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarksDd.
     */
    public void setRemarksDdWV(String value) {
        DropDownController.updateModel(value, getRemarksDdWM());
    }

    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (UserRequestFinderComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (UserRequestFinderComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public EditBoxModel getCreatedOnWM() {
        EditBoxModel createdOnModel = (EditBoxModel) getWidgetCache().getModel("createdOn");
        if (createdOnModel == null) {
            if (getCreatedOn() != null)
                createdOnModel = new EditBoxModel( getCreatedOn() );
            else
                createdOnModel = new EditBoxModel();

            // .//GEN-END:createdOn_1_be
            // Add custom code //GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", createdOnModel);
        }
        return createdOnModel;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        EditBoxController.updateModel(value, getCreatedOnWM());
    }

    /** Getter for DropDown property createdOn.
     * @return Value of property createdOnDd.
     */
    public String getCreatedOnDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (UserRequestFinderComponent) getComponent() ).setCreatedOnDd(createdOnDd);
    }

    /** Getter for DropDown property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOnDd.
     */
    public DropDownModel getCreatedOnDdWM() {
        DropDownModel createdOnDdModel = (DropDownModel) getWidgetCache().getModel("createdOnDd");
        if (createdOnDdModel == null) {
            if (getCreatedOnDd() != null)
                createdOnDdModel = new DropDownModel( getCreatedOnDd() );
            else
                createdOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                createdOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("createdOnDd", createdOnDdModel);
        }
        return createdOnDdModel;
    }

    /** Setter for DropDown property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOnDd.
     */
    public void setCreatedOnDdWV(String value) {
        DropDownController.updateModel(value, getCreatedOnDdWM());
    }

    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:processedDatetime_1_be
    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public String getProcessedDatetime() {
        return ( (UserRequestFinderComponent) getComponent() ).getProcessedDatetime();
    }

    /** Setter for property processedDatetime.
     * @param processedDatetime New value of property processedDatetime.
     */
    public void setProcessedDatetime(String processedDatetime) {
        ( (UserRequestFinderComponent) getComponent() ).setProcessedDatetime(processedDatetime);
    }

    /** Getter for property processedDatetime. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedDatetime.
     */
    public EditBoxModel getProcessedDatetimeWM() {
        EditBoxModel processedDatetimeModel = (EditBoxModel) getWidgetCache().getModel("processedDatetime");
        if (processedDatetimeModel == null) {
            if (getProcessedDatetime() != null)
                processedDatetimeModel = new EditBoxModel( getProcessedDatetime() );
            else
                processedDatetimeModel = new EditBoxModel();

            // .//GEN-END:processedDatetime_1_be
            // Add custom code //GEN-FIRST:processedDatetime_1


            // .//GEN-LAST:processedDatetime_1
            // .//GEN-BEGIN:processedDatetime_2_be
            getWidgetCache().addModel("processedDatetime", processedDatetimeModel);
        }
        return processedDatetimeModel;
    }

    /** Setter for property processedDatetime. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processedDatetime.
     */
    public void setProcessedDatetimeWV(String value) {
        EditBoxController.updateModel(value, getProcessedDatetimeWM());
    }

    /** Getter for DropDown property processedDatetime.
     * @return Value of property processedDatetimeDd.
     */
    public String getProcessedDatetimeDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getProcessedDatetimeDd();
    }

    /** Setter for DropDown property processedDatetime.
     * @param processedDatetimeDd New value of property processedDatetimeDd.
     */
    public void setProcessedDatetimeDd(String processedDatetimeDd) {
        ( (UserRequestFinderComponent) getComponent() ).setProcessedDatetimeDd(processedDatetimeDd);
    }

    /** Getter for DropDown property processedDatetime. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedDatetimeDd.
     */
    public DropDownModel getProcessedDatetimeDdWM() {
        DropDownModel processedDatetimeDdModel = (DropDownModel) getWidgetCache().getModel("processedDatetimeDd");
        if (processedDatetimeDdModel == null) {
            if (getProcessedDatetimeDd() != null)
                processedDatetimeDdModel = new DropDownModel( getProcessedDatetimeDd() );
            else
                processedDatetimeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                processedDatetimeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("processedDatetimeDd", processedDatetimeDdModel);
        }
        return processedDatetimeDdModel;
    }

    /** Setter for DropDown property processedDatetime. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processedDatetimeDd.
     */
    public void setProcessedDatetimeDdWV(String value) {
        DropDownController.updateModel(value, getProcessedDatetimeDdWM());
    }

    // .//GEN-END:processedDatetime_2_be
    // .//GEN-BEGIN:processedUserId_1_be
    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public String getProcessedUserId() {
        return ( (UserRequestFinderComponent) getComponent() ).getProcessedUserId();
    }

    /** Setter for property processedUserId.
     * @param processedUserId New value of property processedUserId.
     */
    public void setProcessedUserId(String processedUserId) {
        ( (UserRequestFinderComponent) getComponent() ).setProcessedUserId(processedUserId);
    }

    /** Getter for property processedUserId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedUserId.
     */
    public EditBoxModel getProcessedUserIdWM() {
        EditBoxModel processedUserIdModel = (EditBoxModel) getWidgetCache().getModel("processedUserId");
        if (processedUserIdModel == null) {
            if (getProcessedUserId() != null)
                processedUserIdModel = new EditBoxModel( getProcessedUserId() );
            else
                processedUserIdModel = new EditBoxModel();
            processedUserIdModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_PROCESSED_USER_ID).getCaseType() );

            // .//GEN-END:processedUserId_1_be
            // Add custom code //GEN-FIRST:processedUserId_1


            // .//GEN-LAST:processedUserId_1
            // .//GEN-BEGIN:processedUserId_2_be
            getWidgetCache().addModel("processedUserId", processedUserIdModel);
        }
        return processedUserIdModel;
    }

    /** Setter for property processedUserId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processedUserId.
     */
    public void setProcessedUserIdWV(String value) {
        EditBoxController.updateModel(value, getProcessedUserIdWM());
    }

    /** Getter for DropDown property processedUserId.
     * @return Value of property processedUserIdDd.
     */
    public String getProcessedUserIdDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getProcessedUserIdDd();
    }

    /** Setter for DropDown property processedUserId.
     * @param processedUserIdDd New value of property processedUserIdDd.
     */
    public void setProcessedUserIdDd(String processedUserIdDd) {
        ( (UserRequestFinderComponent) getComponent() ).setProcessedUserIdDd(processedUserIdDd);
    }

    /** Getter for DropDown property processedUserId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processedUserIdDd.
     */
    public DropDownModel getProcessedUserIdDdWM() {
        DropDownModel processedUserIdDdModel = (DropDownModel) getWidgetCache().getModel("processedUserIdDd");
        if (processedUserIdDdModel == null) {
            if (getProcessedUserIdDd() != null)
                processedUserIdDdModel = new DropDownModel( getProcessedUserIdDd() );
            else
                processedUserIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                processedUserIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("processedUserIdDd", processedUserIdDdModel);
        }
        return processedUserIdDdModel;
    }

    /** Setter for DropDown property processedUserId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processedUserIdDd.
     */
    public void setProcessedUserIdDdWV(String value) {
        DropDownController.updateModel(value, getProcessedUserIdDdWM());
    }

    // .//GEN-END:processedUserId_2_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public String getStatus() {
        return ( (UserRequestFinderComponent) getComponent() ).getStatus();
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(String status) {
        ( (UserRequestFinderComponent) getComponent() ).setStatus(status);
    }

    /** Getter for property status. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property status.
     */
    public EditBoxModel getStatusWM() {
        EditBoxModel statusModel = (EditBoxModel) getWidgetCache().getModel("status");
        if (statusModel == null) {
            if (getStatus() != null)
                statusModel = new EditBoxModel( getStatus() );
            else
                statusModel = new EditBoxModel();
            statusModel.setStringCase( ((StringFieldMetaData) UserRequestMeta.META_STATUS).getCaseType() );

            // .//GEN-END:status_1_be
            // Add custom code //GEN-FIRST:status_1


            // .//GEN-LAST:status_1
            // .//GEN-BEGIN:status_2_be
            getWidgetCache().addModel("status", statusModel);
        }
        return statusModel;
    }

    /** Setter for property status. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property status.
     */
    public void setStatusWV(String value) {
        EditBoxController.updateModel(value, getStatusWM());
    }

    /** Getter for DropDown property status.
     * @return Value of property statusDd.
     */
    public String getStatusDd() {
        return ( (UserRequestFinderComponent) getComponent() ).getStatusDd();
    }

    /** Setter for DropDown property status.
     * @param statusDd New value of property statusDd.
     */
    public void setStatusDd(String statusDd) {
        ( (UserRequestFinderComponent) getComponent() ).setStatusDd(statusDd);
    }

    /** Getter for DropDown property status. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property statusDd.
     */
    public DropDownModel getStatusDdWM() {
        DropDownModel statusDdModel = (DropDownModel) getWidgetCache().getModel("statusDd");
        if (statusDdModel == null) {
            if (getStatusDd() != null)
                statusDdModel = new DropDownModel( getStatusDd() );
            else
                statusDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                statusDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("statusDd", statusDdModel);
        }
        return statusDdModel;
    }

    /** Setter for DropDown property status. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property statusDd.
     */
    public void setStatusDdWV(String value) {
        DropDownController.updateModel(value, getStatusDdWM());
    }

    // .//GEN-END:status_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getRequestIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRequestId(value);
        setRequestIdDd(getRequestIdDdWM().getValue());

        value = getUserNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setUserName(value);
        setUserNameDd(getUserNameDdWM().getValue());

        value = getFirstNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFirstName(value);
        setFirstNameDd(getFirstNameDdWM().getValue());

        value = getLastNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastName(value);
        setLastNameDd(getLastNameDdWM().getValue());

        value = getPasswordWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setPassword(value);
        setPasswordDd(getPasswordDdWM().getValue());

        value = getEMailAddressWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setEMailAddress(value);
        setEMailAddressDd(getEMailAddressDdWM().getValue());

        value = getSecurityQuestionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSecurityQuestion(value);
        setSecurityQuestionDd(getSecurityQuestionDdWM().getValue());

        value = getSecurityAnswerWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSecurityAnswer(value);
        setSecurityAnswerDd(getSecurityAnswerDdWM().getValue());

        value = getRemarksWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRemarks(value);
        setRemarksDd(getRemarksDdWM().getValue());

        value = getCreatedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCreatedOn(value);
        setCreatedOnDd(getCreatedOnDdWM().getValue());

        value = getProcessedDatetimeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setProcessedDatetime(value);
        setProcessedDatetimeDd(getProcessedDatetimeDdWM().getValue());

        value = getProcessedUserIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setProcessedUserId(value);
        setProcessedUserIdDd(getProcessedUserIdDdWM().getValue());

        value = getStatusWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setStatus(value);
        setStatusDd(getStatusDdWM().getValue());

        // .//GEN-END:_doValidate_1_be
        // Add custom code //GEN-FIRST:_doValidate_1



        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        UserRequestFinderOutDto outputDto = (UserRequestFinderOutDto) ((UserRequestFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                UserRequestFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("requestId", rowDto.getRequestId());
                row.addElement("userName", rowDto.getUserName());
                row.addElement("firstName", rowDto.getFirstName());
                row.addElement("lastName", rowDto.getLastName());
                row.addElement("password", rowDto.getPassword());
                row.addElement("eMailAddress", rowDto.getEMailAddress());
                row.addElement("securityQuestion", rowDto.getSecurityQuestion());
                row.addElement("securityAnswer", rowDto.getSecurityAnswer());
                row.addElement("remarks", rowDto.getRemarks());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("processedDatetime", rowDto.getProcessedDatetime());
                row.addElement("processedUserId", rowDto.getProcessedUserId());
                row.addElement("status", rowDto.getStatus());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
