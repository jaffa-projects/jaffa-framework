/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.requestpassword.ui;

import java.util.*;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.jaffa.applications.jaffa.modules.admin.domain.User;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequest;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.security.VariationContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.applications.jaffa.modules.user.components.requestpassword.IRequestPassword;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.util.EmailerBean;
import org.jaffa.util.MessageHelper;


/** The controller for the RequestPassword.
 */
public class RequestPasswordComponent extends Component {
    
    private static Logger log = Logger.getLogger(RequestPasswordComponent.class);
    private IRequestPassword m_tx = null;
    private List m_screens;
    private int m_currentScreenCounter = 0;
    private HeaderDto m_headerDto = null;
    
    
    private java.lang.String m_userName;
    private java.lang.String m_email;
    private java.lang.String m_securityQuestion;
    private java.lang.String m_securityAnswer;
    
    
    
    
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return m_userName;
    }
    
    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        m_userName = userName;
    }
    
    /** Getter for property email.
     * @return Value of property email.
     */
    public java.lang.String getEmail() {
        return m_email;
    }
    
    /** Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(java.lang.String email) {
        m_email = email;
    }
    
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.String getSecurityQuestion() {
        return m_securityQuestion;
    }
    
    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.String securityQuestion) {
        m_securityQuestion = securityQuestion;
    }
    
    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public java.lang.String getSecurityAnswer() {
        return m_securityAnswer;
    }
    
    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(java.lang.String securityAnswer) {
        m_securityAnswer = securityAnswer;
    }
    
    
    
    
    /** This should be invoked when done with the component.
     */
    public void quit() {
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        super.quit();
    }
    
    
    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return m_currentScreenCounter;
    }
    
    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        m_currentScreenCounter = currentScreenCounter;
    }
    
    /** Getter for the Screens.
     * @return the screens.
     */
    public String[] getScreens() {
        return (String[]) m_screens.toArray(new String[0]);
    }
    
    /** Getter for the current Screen.
     * @return the current screen.
     */
    public String determineCurrentScreen() {
        return (String) m_screens.get(m_currentScreenCounter);
    }
    
    /** Getter for the next Screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public String determineNextScreen() {
        return determineAndSetNextScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the next screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public String determineAndSetNextScreen() {
        return determineAndSetNextScreen(true);
    }
    
    private String determineAndSetNextScreen(boolean setCurrentScreenCounter) {
        String screen = null;
        int i = m_currentScreenCounter + 1;
        if (i < m_screens.size()) {
            screen = (String) m_screens.get(i);
            if (setCurrentScreenCounter)
                m_currentScreenCounter = i;
        }
        return screen;
    }
    
    /** Getter for the previous Screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public String determinePreviousScreen() {
        return determineAndSetPreviousScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the previous screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public String determineAndSetPreviousScreen() {
        return determineAndSetPreviousScreen(true);
    }
    
    private String determineAndSetPreviousScreen(boolean setCurrentScreenCounter) {
        String screen = null;
        int i = m_currentScreenCounter - 1;
        if (i >= 0) {
            screen = (String) m_screens.get(i);
            if (setCurrentScreenCounter)
                m_currentScreenCounter = i;
        }
        return screen;
    }
    
    /** Getter for the current screen's FormKey.
     * @return the FormKey for the current screen.
     */
    public FormKey determineFormKey() {
        return new FormKey(determineCurrentScreen(), getComponentId());
    }
    
    /** Brings up the first screen of the component.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        if (m_screens == null) {
            m_screens = new ArrayList();
            addScreens(m_screens);
        }
        return determineFormKey();
    }
    
    /** This sets up the screen information.
     * @param screens The component should add String objects to this list; each String representing a struts-forward name.
     */
    protected void addScreens(List screens) {
        screens.add("nonsecure_requestPassword_request Password");
    }
    
    private IRequestPassword createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IRequestPassword) Factory.createObject(IRequestPassword.class);
        return m_tx;
    }
    
    /** Returns the HeaderDto. This can be used for passing the header info to the Tx, where required.
     * @return the HeaderDto.
     */
    protected HeaderDto getHeaderDto() {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId(getUserSession().getUserId());
            m_headerDto.setVariation(VariationContext.getVariation());
        }
        return m_headerDto;
    }
    
    public void sendEmail() {
        
        
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        if (this.getUserName() != null)
            criteria.addCriteria(UserMeta.USER_NAME, this.getUserName());
        if (this.getEmail() != null)
            criteria.addCriteria(UserMeta.E_MAIL_ADDRESS , this.getEmail());
        if (!this.getSecurityQuestion().equalsIgnoreCase("None"))
            criteria.addCriteria(UserMeta.SECURITY_QUESTION , this.getSecurityQuestion());
        if (this.getSecurityAnswer() != null)
            criteria.addCriteria(UserMeta.SECURITY_ANSWER , this.getSecurityAnswer());
        criteria.addCriteria(UserMeta.STATUS , "A");
        try {
            UOW uow = new UOW();
            if (!uow.query(criteria).isEmpty()) {
                StringBuffer body = new StringBuffer();
                if (uow.query(criteria).size() > 1) {
                    body.append("You have " + uow.query(criteria).size() + " Active Accounts");
                    int counter = 1;
                    for(Iterator itr = uow.query(criteria).iterator(); itr.hasNext(); ) {
                        User ur = (User) itr.next();
                        body.append(", Account " + counter++ + ", User Name is " + ur.getUserName() + ", Security Question is '" + MessageHelper.replaceTokens("[label.Jaffa.User.UserRequest.SecurityQuestion." + ur.getSecurityQuestion() + "]") + "'" );
                    }
                } else {
                    User ur = (User) uow.query(criteria).iterator().next();
                    if (this.getUserName() != null) {
                        
                        body.append("You have the following Active Account  ");
                        body.append(", User Name is " + ur.getUserName() + ", Password is " + ur.getPassword() + "." );
                    } else if (this.getSecurityQuestion() != null && new Long(this.getSecurityQuestion()).equals(ur.getSecurityQuestion()) && (this.getSecurityAnswer().equals(ur.getSecurityAnswer()))) {
                                     body.append("You have the following Active Account  ");
                        body.append(", User Name is " + ur.getUserName() + ", Password is " + ur.getPassword() + "." );
                    } else {
                        
                        body.append("You have the following Active Account");
                        body.append(", User Name is " + ur.getUserName() + ", Security Question is '" + MessageHelper.replaceTokens("[label.Jaffa.User.UserRequest.SecurityQuestion." + ur.getSecurityQuestion() + "]") + "'." );                    }                    
                }
                EmailerBean email = new EmailerBean();
                String[] to = new String[] {this.getEmail()};
                try {
                    email.sendMail(to, "Account Information" , body.toString());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } catch (UOWException e) {
            e.printStackTrace();
        }
    }
    
}
