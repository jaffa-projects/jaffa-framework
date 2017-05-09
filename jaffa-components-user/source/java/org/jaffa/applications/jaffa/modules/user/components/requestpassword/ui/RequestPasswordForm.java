/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.requestpassword.ui;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.datatypes.exceptions.*;
import org.jaffa.exceptions.DomainObjectNotFoundException;




/** The FormBean class.
 */
public class RequestPasswordForm extends FormBase {

    private static Logger log = Logger.getLogger(RequestPasswordForm.class);









    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return ( (RequestPasswordComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        ( (RequestPasswordComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel w_userName = (EditBoxModel) getWidgetCache().getModel("userName");
        if (w_userName == null) {
            if (getUserName() != null)
                w_userName = new EditBoxModel(getUserName());
            else
                w_userName = new EditBoxModel();
            getWidgetCache().addModel("userName", w_userName);
        }
        return w_userName;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }

    /** Getter for property email.
     * @return Value of property email.
     */
    public java.lang.String getEmail() {
        return ( (RequestPasswordComponent) getComponent() ).getEmail();
    }

    /** Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(java.lang.String email) {
        ( (RequestPasswordComponent) getComponent() ).setEmail(email);
    }

    /** Getter for property email. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property email.
     */
    public EditBoxModel getEmailWM() {
        EditBoxModel w_email = (EditBoxModel) getWidgetCache().getModel("email");
        if (w_email == null) {
            if (getEmail() != null)
                w_email = new EditBoxModel(getEmail());
            else
                w_email = new EditBoxModel();
            getWidgetCache().addModel("email", w_email);
        }
        return w_email;
    }

    /** Setter for property email. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property email.
     */
    public void setEmailWV(String value) {
        EditBoxController.updateModel(value, getEmailWM());
    }

    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.String getSecurityQuestion() {
        return ( (RequestPasswordComponent) getComponent() ).getSecurityQuestion();
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.String securityQuestion) {
        ( (RequestPasswordComponent) getComponent() ).setSecurityQuestion(securityQuestion);
    }

    /** Getter for property securityQuestion. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityQuestion.
     */
    public DropDownModel getSecurityQuestionWM() {
        DropDownModel w_securityQuestion = (DropDownModel) getWidgetCache().getModel("securityQuestion");
        if (w_securityQuestion == null) {
            w_securityQuestion = new DropDownModel((getSecurityQuestion() != null ? getSecurityQuestion() : ""));
            w_securityQuestion.addOption("None", "None");
            w_securityQuestion.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.1]", "1");
            w_securityQuestion.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.2]", "2");
            w_securityQuestion.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.3]", "3");
            w_securityQuestion.addOption("[label.Jaffa.User.UserRequest.SecurityQuestion.4]", "4");
            // .
            // Add custom code


            // .
            // .
            getWidgetCache().addModel("securityQuestion", w_securityQuestion);
        }
        return w_securityQuestion;
    }

    /** Setter for property securityQuestion. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property securityQuestion.
     */
    public void setSecurityQuestionWV(String value) {
        DropDownController.updateModel(value, getSecurityQuestionWM());
    }

    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        return ( (RequestPasswordComponent) getComponent() ).getSecurityAnswer();
    }

    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(java.lang.String securityAnswer) {
        ( (RequestPasswordComponent) getComponent() ).setSecurityAnswer(securityAnswer);
    }

    /** Getter for property securityAnswer. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property securityAnswer.
     */
    public EditBoxModel getSecurityAnswerWM() {
        EditBoxModel w_securityAnswer = (EditBoxModel) getWidgetCache().getModel("securityAnswer");
        if (w_securityAnswer == null) {
            if (getSecurityAnswer() != null)
                w_securityAnswer = new EditBoxModel(getSecurityAnswer());
            else
                w_securityAnswer = new EditBoxModel();
            getWidgetCache().addModel("securityAnswer", w_securityAnswer);
        }
        return w_securityAnswer;
    }

    /** Setter for property securityAnswer. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property securityAnswer.
     */
    public void setSecurityAnswerWV(String value) {
        EditBoxController.updateModel(value, getSecurityAnswerWM());
    }




    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = true;
        if (!doValidate0(request))
            valid = false;
        return valid;
    }
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        boolean valid = true;
        String htmlString = null;
        htmlString = getUserNameWM().getValue();
        if (htmlString != null && htmlString.length() == 0)
            htmlString = null;
        java.lang.String userNameValue = Parser.parseString(htmlString);
        setUserName(userNameValue);

        htmlString = getEmailWM().getValue();
        if (htmlString != null && htmlString.length() == 0)
            htmlString = null;
        java.lang.String emailValue = Parser.parseString(htmlString);
        setEmail(emailValue);

        java.lang.String securityQuestionValue = getSecurityQuestionWM().getValue();
        setSecurityQuestion(securityQuestionValue);

        htmlString = getSecurityAnswerWM().getValue();
        if (htmlString != null && htmlString.length() == 0)
            htmlString = null;
        java.lang.String securityAnswerValue = Parser.parseString(htmlString);
        setSecurityAnswer(securityAnswerValue);

        if (this.getEmail() == null && this.getSecurityAnswer() == null && this.getSecurityQuestion().equalsIgnoreCase("none") && this.getEmail() == null) {
            raiseError(request, "User", new DomainObjectNotFoundException(UserMeta.getLabelToken()));
            valid = false;
        }
        return valid;
    }


    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return ((RequestPasswordComponent) getComponent()).getCurrentScreenCounter();
    }

    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        ((RequestPasswordComponent) getComponent()).setCurrentScreenCounter(currentScreenCounter);
    }

    /** Returns true if there is a Next screen after the current screen.
     * @return true if there is a Next screen after the current screen.
     */
    public boolean isNextActionAvailable() {
        return ((RequestPasswordComponent) getComponent()).determineNextScreen() != null ? true : false;
    }

    /** Returns true if there is a Previous screen before the current screen.
     * @return true if there is a Previous screen before the current screen.
     */
    public boolean isPreviousActionAvailable() {
        return ((RequestPasswordComponent) getComponent()).determinePreviousScreen() != null ? true : false;
    }

}
