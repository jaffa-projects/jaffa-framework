// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.IUserRequestFinder;
import org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderInDto;
import org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto.UserRequestFinderOutDto;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;


import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent;
import org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.ui.UserRequestViewerComponent;
import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent;
import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserRequestFinder.
 */
public class UserRequestFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(UserRequestFinderComponent.class);

    private String m_requestId = null;
    private String m_requestIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_userName = null;
    private String m_userNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_firstName = null;
    private String m_firstNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastName = null;
    private String m_lastNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_password = null;
    private String m_passwordDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_eMailAddress = null;
    private String m_eMailAddressDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_securityQuestion = null;
    private String m_securityQuestionDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_securityAnswer = null;
    private String m_securityAnswerDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_remarks = null;
    private String m_remarksDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_createdOn = null;
    private String m_createdOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_processedDatetime = null;
    private String m_processedDatetimeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_processedUserId = null;
    private String m_processedUserIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_status = null;
    private String m_statusDd = CriteriaField.RELATIONAL_EQUALS;

    private IUserRequestFinder m_tx = null;
    private UserRequestMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private UserRequestMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private UserRequestMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public UserRequestFinderComponent() {
        super();
        super.setSortDropDown("RequestId");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "user_userRequestFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "user_userRequestFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "user_userRequestFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "user_userRequestFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "user_userRequestFinderXmlResults";
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method //GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        if (m_createComponent != null) {
            m_createComponent.quit();
            m_createComponent = null;
        }
        m_createListener = null;
        if (m_updateComponent != null) {
            m_updateComponent.quit();
            m_updateComponent = null;
        }
        m_updateListener = null;
        if (m_deleteComponent != null) {
            m_deleteComponent.quit();
            m_deleteComponent = null;
        }
        m_deleteListener = null;

        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:requestId_1_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public String getRequestId() {
        return m_requestId;
    }

    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(String requestId) {
        m_requestId = requestId;
    }

    /** Getter for property requestIdDd.
     * @return Value of property requestIdDd.
     */
    public String getRequestIdDd() {
        return m_requestIdDd;
    }

    /** Setter for property requestIdDd.
     * @param requestIdDd New value of property requestIdDd.
     */
    public void setRequestIdDd(String requestIdDd) {
        m_requestIdDd = requestIdDd;
    }

    // .//GEN-END:requestId_1_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return m_userName;
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        m_userName = userName;
    }

    /** Getter for property userNameDd.
     * @return Value of property userNameDd.
     */
    public String getUserNameDd() {
        return m_userNameDd;
    }

    /** Setter for property userNameDd.
     * @param userNameDd New value of property userNameDd.
     */
    public void setUserNameDd(String userNameDd) {
        m_userNameDd = userNameDd;
    }

    // .//GEN-END:userName_1_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public String getFirstName() {
        return m_firstName;
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName) {
        m_firstName = firstName;
    }

    /** Getter for property firstNameDd.
     * @return Value of property firstNameDd.
     */
    public String getFirstNameDd() {
        return m_firstNameDd;
    }

    /** Setter for property firstNameDd.
     * @param firstNameDd New value of property firstNameDd.
     */
    public void setFirstNameDd(String firstNameDd) {
        m_firstNameDd = firstNameDd;
    }

    // .//GEN-END:firstName_1_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public String getLastName() {
        return m_lastName;
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(String lastName) {
        m_lastName = lastName;
    }

    /** Getter for property lastNameDd.
     * @return Value of property lastNameDd.
     */
    public String getLastNameDd() {
        return m_lastNameDd;
    }

    /** Setter for property lastNameDd.
     * @param lastNameDd New value of property lastNameDd.
     */
    public void setLastNameDd(String lastNameDd) {
        m_lastNameDd = lastNameDd;
    }

    // .//GEN-END:lastName_1_be
    // .//GEN-BEGIN:password_1_be
    /** Getter for property password.
     * @return Value of property password.
     */
    public String getPassword() {
        return m_password;
    }

    /** Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(String password) {
        m_password = password;
    }

    /** Getter for property passwordDd.
     * @return Value of property passwordDd.
     */
    public String getPasswordDd() {
        return m_passwordDd;
    }

    /** Setter for property passwordDd.
     * @param passwordDd New value of property passwordDd.
     */
    public void setPasswordDd(String passwordDd) {
        m_passwordDd = passwordDd;
    }

    // .//GEN-END:password_1_be
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public String getEMailAddress() {
        return m_eMailAddress;
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(String eMailAddress) {
        m_eMailAddress = eMailAddress;
    }

    /** Getter for property eMailAddressDd.
     * @return Value of property eMailAddressDd.
     */
    public String getEMailAddressDd() {
        return m_eMailAddressDd;
    }

    /** Setter for property eMailAddressDd.
     * @param eMailAddressDd New value of property eMailAddressDd.
     */
    public void setEMailAddressDd(String eMailAddressDd) {
        m_eMailAddressDd = eMailAddressDd;
    }

    // .//GEN-END:eMailAddress_1_be
    // .//GEN-BEGIN:securityQuestion_1_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public String getSecurityQuestion() {
        return m_securityQuestion;
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(String securityQuestion) {
        m_securityQuestion = securityQuestion;
    }

    /** Getter for property securityQuestionDd.
     * @return Value of property securityQuestionDd.
     */
    public String getSecurityQuestionDd() {
        return m_securityQuestionDd;
    }

    /** Setter for property securityQuestionDd.
     * @param securityQuestionDd New value of property securityQuestionDd.
     */
    public void setSecurityQuestionDd(String securityQuestionDd) {
        m_securityQuestionDd = securityQuestionDd;
    }

    // .//GEN-END:securityQuestion_1_be
    // .//GEN-BEGIN:securityAnswer_1_be
    /** Getter for property securityAnswer.
     * @return Value of property securityAnswer.
     */
    public String getSecurityAnswer() {
        return m_securityAnswer;
    }

    /** Setter for property securityAnswer.
     * @param securityAnswer New value of property securityAnswer.
     */
    public void setSecurityAnswer(String securityAnswer) {
        m_securityAnswer = securityAnswer;
    }

    /** Getter for property securityAnswerDd.
     * @return Value of property securityAnswerDd.
     */
    public String getSecurityAnswerDd() {
        return m_securityAnswerDd;
    }

    /** Setter for property securityAnswerDd.
     * @param securityAnswerDd New value of property securityAnswerDd.
     */
    public void setSecurityAnswerDd(String securityAnswerDd) {
        m_securityAnswerDd = securityAnswerDd;
    }

    // .//GEN-END:securityAnswer_1_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return m_remarks;
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        m_remarks = remarks;
    }

    /** Getter for property remarksDd.
     * @return Value of property remarksDd.
     */
    public String getRemarksDd() {
        return m_remarksDd;
    }

    /** Setter for property remarksDd.
     * @param remarksDd New value of property remarksDd.
     */
    public void setRemarksDd(String remarksDd) {
        m_remarksDd = remarksDd;
    }

    // .//GEN-END:remarks_1_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return m_createdOn;
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        m_createdOn = createdOn;
    }

    /** Getter for property createdOnDd.
     * @return Value of property createdOnDd.
     */
    public String getCreatedOnDd() {
        return m_createdOnDd;
    }

    /** Setter for property createdOnDd.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        m_createdOnDd = createdOnDd;
    }

    // .//GEN-END:createdOn_1_be
    // .//GEN-BEGIN:processedDatetime_1_be
    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public String getProcessedDatetime() {
        return m_processedDatetime;
    }

    /** Setter for property processedDatetime.
     * @param processedDatetime New value of property processedDatetime.
     */
    public void setProcessedDatetime(String processedDatetime) {
        m_processedDatetime = processedDatetime;
    }

    /** Getter for property processedDatetimeDd.
     * @return Value of property processedDatetimeDd.
     */
    public String getProcessedDatetimeDd() {
        return m_processedDatetimeDd;
    }

    /** Setter for property processedDatetimeDd.
     * @param processedDatetimeDd New value of property processedDatetimeDd.
     */
    public void setProcessedDatetimeDd(String processedDatetimeDd) {
        m_processedDatetimeDd = processedDatetimeDd;
    }

    // .//GEN-END:processedDatetime_1_be
    // .//GEN-BEGIN:processedUserId_1_be
    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public String getProcessedUserId() {
        return m_processedUserId;
    }

    /** Setter for property processedUserId.
     * @param processedUserId New value of property processedUserId.
     */
    public void setProcessedUserId(String processedUserId) {
        m_processedUserId = processedUserId;
    }

    /** Getter for property processedUserIdDd.
     * @return Value of property processedUserIdDd.
     */
    public String getProcessedUserIdDd() {
        return m_processedUserIdDd;
    }

    /** Setter for property processedUserIdDd.
     * @param processedUserIdDd New value of property processedUserIdDd.
     */
    public void setProcessedUserIdDd(String processedUserIdDd) {
        m_processedUserIdDd = processedUserIdDd;
    }

    // .//GEN-END:processedUserId_1_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public String getStatus() {
        return m_status;
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(String status) {
        m_status = status;
    }

    /** Getter for property statusDd.
     * @return Value of property statusDd.
     */
    public String getStatusDd() {
        return m_statusDd;
    }

    /** Setter for property statusDd.
     * @param statusDd New value of property statusDd.
     */
    public void setStatusDd(String statusDd) {
        m_statusDd = statusDd;
    }

    // .//GEN-END:status_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        UserRequestFinderInDto inputDto = new UserRequestFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        try {
            if (getRequestId() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getRequestIdDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getRequestIdDd() ) )
                inputDto.setRequestId(IntegerCriteriaField.getIntegerCriteriaField(getRequestIdDd(), getRequestId(), (IntegerFieldMetaData) UserRequestMeta.META_REQUEST_ID));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getUserName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getUserNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getUserNameDd() ) )
            inputDto.setUserName(StringCriteriaField.getStringCriteriaField(getUserNameDd(), getUserName(), null));

        if (getFirstName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFirstNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFirstNameDd() ) )
            inputDto.setFirstName(StringCriteriaField.getStringCriteriaField(getFirstNameDd(), getFirstName(), null));

        if (getLastName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLastNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLastNameDd() ) )
            inputDto.setLastName(StringCriteriaField.getStringCriteriaField(getLastNameDd(), getLastName(), null));

        if (getPassword() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getPasswordDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getPasswordDd() ) )
            inputDto.setPassword(StringCriteriaField.getStringCriteriaField(getPasswordDd(), getPassword(), null));

        if (getEMailAddress() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getEMailAddressDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getEMailAddressDd() ) )
            inputDto.setEMailAddress(StringCriteriaField.getStringCriteriaField(getEMailAddressDd(), getEMailAddress(), null));

        try {
            if (getSecurityQuestion() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getSecurityQuestionDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSecurityQuestionDd() ) )
                inputDto.setSecurityQuestion(IntegerCriteriaField.getIntegerCriteriaField(getSecurityQuestionDd(), getSecurityQuestion(), (IntegerFieldMetaData) UserRequestMeta.META_SECURITY_QUESTION));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getSecurityAnswer() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSecurityAnswerDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSecurityAnswerDd() ) )
            inputDto.setSecurityAnswer(StringCriteriaField.getStringCriteriaField(getSecurityAnswerDd(), getSecurityAnswer(), null));

        if (getRemarks() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getRemarksDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getRemarksDd() ) )
            inputDto.setRemarks(StringCriteriaField.getStringCriteriaField(getRemarksDd(), getRemarks(), null));

        try {
            if (getCreatedOn() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getCreatedOnDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCreatedOnDd() ) )
                inputDto.setCreatedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getCreatedOnDd(), getCreatedOn(), (DateTimeFieldMetaData) UserRequestMeta.META_CREATED_ON));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        try {
            if (getProcessedDatetime() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getProcessedDatetimeDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getProcessedDatetimeDd() ) )
                inputDto.setProcessedDatetime(DateTimeCriteriaField.getDateTimeCriteriaField(getProcessedDatetimeDd(), getProcessedDatetime(), (DateTimeFieldMetaData) UserRequestMeta.META_PROCESSED_DATETIME));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getProcessedUserId() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getProcessedUserIdDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getProcessedUserIdDd() ) )
            inputDto.setProcessedUserId(StringCriteriaField.getStringCriteriaField(getProcessedUserIdDd(), getProcessedUserId(), null));

        if (getStatus() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getStatusDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getStatusDd() ) )
            inputDto.setStatus(StringCriteriaField.getStringCriteriaField(getStatusDd(), getStatus(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IUserRequestFinder) Factory.createObject(IUserRequestFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.User.UserRequestMaintenance component for creating a new UserRequest object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.User.UserRequestMaintenance component for creating a new UserRequest object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.User.UserRequestMaintenance component for creating a new UserRequest object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (UserRequestMaintenanceComponent) run("Jaffa.User.UserRequestMaintenance");
        m_createComponent.setReturnToFormKey(formKey);
        // Add the Listener only if a search has been done
        if (getFinderOutDto() != null)
            addListeners(m_createComponent);
        if (m_createComponent instanceof IMaintComponent)
            ((IMaintComponent) m_createComponent).setMode(IMaintComponent.MODE_CREATE);
        // .//GEN-END:_createObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_createObject_1


        // .//GEN-LAST:_createObject_1
        // .//GEN-BEGIN:_createObject_2_be
        return m_createComponent.display();
    }

    private ICreateListener getCreateListener() {
        if (m_createListener == null) {
            m_createListener = new ICreateListener() {
                public void createDone(EventObject source) {
                    try {
                        // .//GEN-END:_createObject_2_be
                        // Add custom code //GEN-FIRST:_createObject_2


                        // .//GEN-LAST:_createObject_2
                        // .//GEN-BEGIN:_createObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Create", e);
                    }
                }
            };
        }
        return m_createListener;
    }
    // .//GEN-END:_createObject_3_be
    // .//GEN-BEGIN:_viewObject_1_be
    /** Calls the Jaffa.User.UserRequestViewer component for viewing the UserRequest object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException {
        UserRequestViewerComponent viewComponent = (UserRequestViewerComponent) run("Jaffa.User.UserRequestViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setRequestId(requestId);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.User.UserRequestMaintenance component for updating the UserRequest object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.Long requestId) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (UserRequestMaintenanceComponent) run("Jaffa.User.UserRequestMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setRequestId(requestId);
        if (m_updateComponent instanceof IMaintComponent)
            ((IMaintComponent) m_updateComponent).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateObject_2


        // .//GEN-LAST:_updateObject_2
        // .//GEN-BEGIN:_updateObject_2_be
        return m_updateComponent.display();
    }

    private IUpdateListener getUpdateListener() {
        if (m_updateListener == null) {
            m_updateListener = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateObject_2_be
                        // Add custom code //GEN-FIRST:_updateObject_1


                        // .//GEN-LAST:_updateObject_1
                        // .//GEN-BEGIN:_updateObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }
    // .//GEN-END:_updateObject_3_be
    // .//GEN-BEGIN:_deleteObject_1_be
    /** Calls the Jaffa.User.UserRequestMaintenance component for deleting the UserRequest object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.Long requestId)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (UserRequestMaintenanceComponent) run("Jaffa.User.UserRequestMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setRequestId(requestId);
        if (m_deleteComponent instanceof IMaintComponent)
            ((IMaintComponent) m_deleteComponent).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteObject_2


        // .//GEN-LAST:_deleteObject_2
        // .//GEN-BEGIN:_deleteObject_2_be
        return m_deleteComponent.display();
    }

    private IDeleteListener getDeleteListener() {
        if (m_deleteListener == null) {
            m_deleteListener = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteObject_2_be
                        // Add custom code //GEN-FIRST:_deleteObject_1


                        // .//GEN-LAST:_deleteObject_1
                        // .//GEN-BEGIN:_deleteObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListener;
    }
    // .//GEN-END:_deleteObject_3_be
    // .//GEN-BEGIN:_addListeners_1_be
    private void addListeners(Component comp) {
        if (comp  instanceof ICreateComponent)
            ((ICreateComponent) comp).addCreateListener(getCreateListener());
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListener());
    }
    // .//GEN-END:_addListeners_1_be
    // .//GEN-BEGIN:_initializeCriteriaScreen_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initializeCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
