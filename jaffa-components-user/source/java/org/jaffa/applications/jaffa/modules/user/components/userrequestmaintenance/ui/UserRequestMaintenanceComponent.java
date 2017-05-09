// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui;

import java.util.EventObject;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.util.BeanHelper;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.maint.*;
import org.jaffa.components.finder.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.IUserRequestMaintenance;
import org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserRequestMaintenance.
 */
public class UserRequestMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(UserRequestMaintenanceComponent.class);
    private IUserRequestMaintenance m_tx = null;

    private java.lang.Long m_requestId = null;
    private java.lang.String m_userName = null;
    private java.lang.String m_firstName = null;
    private java.lang.String m_lastName = null;
    private java.lang.String m_password1 = null;
    private java.lang.String m_password2 = null;
    private java.lang.String m_eMailAddress = null;
    private java.lang.Long m_securityQuestion = null;
    private java.lang.String m_securityQuestion1 = null;
    private java.lang.String m_securityAnswer = null;
    private java.lang.String m_remarks = null;
    private org.jaffa.datatypes.DateTime m_createdOn = null;
    private org.jaffa.datatypes.DateTime m_processedDatetime = null;
    private java.lang.String m_processedUserId = null;
    private java.lang.String m_status = null;


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
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:requestId_1_be
    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        return m_requestId;
    }

    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(java.lang.Long requestId) {
        m_requestId = requestId;
    }
    // .//GEN-END:requestId_1_be
    // .//GEN-BEGIN:userName_1_be
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
    // .//GEN-END:userName_1_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        return m_firstName;
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(java.lang.String firstName) {
        m_firstName = firstName;
    }
    // .//GEN-END:firstName_1_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        return m_lastName;
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(java.lang.String lastName) {
        m_lastName = lastName;
    }
    // .//GEN-END:lastName_1_be
    // .//GEN-BEGIN:password1_1_be
    /** Getter for property password1.
     * @return Value of property password1.
     */
    public java.lang.String getPassword1() {
        return m_password1;
    }

    /** Setter for property password1.
     * @param password1 New value of property password1.
     */
    public void setPassword1(java.lang.String password1) {
        m_password1 = password1;
    }
    // .//GEN-END:password1_1_be
    // .//GEN-BEGIN:password2_1_be
    /** Getter for property password2.
     * @return Value of property password2.
     */
    public java.lang.String getPassword2() {
        return m_password2;
    }

    /** Setter for property password2.
     * @param password2 New value of property password2.
     */
    public void setPassword2(java.lang.String password2) {
        m_password2 = password2;
    }
    // .//GEN-END:password2_1_be
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public java.lang.String getEMailAddress() {
        return m_eMailAddress;
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(java.lang.String eMailAddress) {
        m_eMailAddress = eMailAddress;
    }
    // .//GEN-END:eMailAddress_1_be
    // .//GEN-BEGIN:securityQuestion_1_be
    /** Getter for property securityQuestion.
     * @return Value of property securityQuestion.
     */
    public java.lang.Long getSecurityQuestion() {
        return m_securityQuestion;
    }

    /** Setter for property securityQuestion.
     * @param securityQuestion New value of property securityQuestion.
     */
    public void setSecurityQuestion(java.lang.Long securityQuestion) {
        m_securityQuestion = securityQuestion;
    }
    // .//GEN-END:securityQuestion_1_be
    // .//GEN-BEGIN:securityQuestion1_1_be
    /** Getter for property securityQuestion1.
     * @return Value of property securityQuestion1.
     */
    public java.lang.String getSecurityQuestion1() {
        return m_securityQuestion1;
    }

    /** Setter for property securityQuestion1.
     * @param securityQuestion1 New value of property securityQuestion1.
     */
    public void setSecurityQuestion1(java.lang.String securityQuestion1) {
        m_securityQuestion1 = securityQuestion1;
    }
    // .//GEN-END:securityQuestion1_1_be
    // .//GEN-BEGIN:securityAnswer_1_be
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
    // .//GEN-END:securityAnswer_1_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return m_remarks;
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        m_remarks = remarks;
    }
    // .//GEN-END:remarks_1_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        m_createdOn = createdOn;
    }
    // .//GEN-END:createdOn_1_be
    // .//GEN-BEGIN:processedDatetime_1_be
    /** Getter for property processedDatetime.
     * @return Value of property processedDatetime.
     */
    public org.jaffa.datatypes.DateTime getProcessedDatetime() {
        return m_processedDatetime;
    }

    /** Setter for property processedDatetime.
     * @param processedDatetime New value of property processedDatetime.
     */
    public void setProcessedDatetime(org.jaffa.datatypes.DateTime processedDatetime) {
        m_processedDatetime = processedDatetime;
    }
    // .//GEN-END:processedDatetime_1_be
    // .//GEN-BEGIN:processedUserId_1_be
    /** Getter for property processedUserId.
     * @return Value of property processedUserId.
     */
    public java.lang.String getProcessedUserId() {
        return m_processedUserId;
    }

    /** Setter for property processedUserId.
     * @param processedUserId New value of property processedUserId.
     */
    public void setProcessedUserId(java.lang.String processedUserId) {
        m_processedUserId = processedUserId;
    }
    // .//GEN-END:processedUserId_1_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return m_status;
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(java.lang.String status) {
        m_status = status;
    }
    // .//GEN-END:status_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceCreateInDto input = new UserRequestMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code //GEN-FIRST:_doCreate_1
        input.setUrl(getUrl());
        if (this.getSecurityQuestion1() != null)
	        this.setSecurityQuestion(new Long(this.getSecurityQuestion1()));
        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setRequestId(getRequestId());
        input.setUserName(getUserName());
        input.setFirstName(getFirstName());
        input.setLastName(getLastName());
        input.setPassword1(getPassword1());
        input.setPassword2(getPassword2());
        input.setEMailAddress(getEMailAddress());
        input.setSecurityQuestion(getSecurityQuestion());
        input.setSecurityQuestion1(getSecurityQuestion1());
        input.setSecurityAnswer(getSecurityAnswer());
        input.setRemarks(getRemarks());
        input.setCreatedOn(getCreatedOn());
        input.setProcessedDatetime(getProcessedDatetime());
        input.setProcessedUserId(getProcessedUserId());
        input.setStatus(getStatus());
        UserRequestMaintenanceRetrieveOutDto output = createTx().create(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doCreate_2_be
        // Add custom code //GEN-FIRST:_doCreate_2


        // .//GEN-LAST:_doCreate_2
        // .//GEN-BEGIN:_doCreate_3_be
    }
    // .//GEN-END:_doCreate_3_be
    // .//GEN-BEGIN:_doUpdate_1_be
    /** This will invoke the update method on the transaction to update an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceUpdateInDto input = new UserRequestMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code //GEN-FIRST:_doUpdate_1
       if (this.getSecurityQuestion1() != null)
	        this.setSecurityQuestion(new Long(this.getSecurityQuestion1()));

        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setRequestId(getRequestId());
        input.setUserName(getUserName());
        input.setFirstName(getFirstName());
        input.setLastName(getLastName());
        input.setPassword1(getPassword1());
        input.setPassword2(getPassword2());
        input.setEMailAddress(getEMailAddress());
        input.setSecurityQuestion(getSecurityQuestion());
        input.setSecurityQuestion1(getSecurityQuestion1());
        input.setSecurityAnswer(getSecurityAnswer());
        input.setRemarks(getRemarks());
        input.setCreatedOn(getCreatedOn());
        input.setProcessedDatetime(getProcessedDatetime());
        input.setProcessedUserId(getProcessedUserId());
        input.setStatus(getStatus());
        UserRequestMaintenanceRetrieveOutDto output = createTx().update(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doUpdate_2_be
        // Add custom code //GEN-FIRST:_doUpdate_2


        // .//GEN-LAST:_doUpdate_2
        // .//GEN-BEGIN:_doUpdate_3_be
    }
    // .//GEN-END:_doUpdate_3_be
    // .//GEN-BEGIN:_doDelete_1_be
    /** This will invoke the delete method on the transaction to delete an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doDelete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceDeleteInDto input = new UserRequestMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code //GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setRequestId(getRequestId());
        createTx().delete(input);
        // .//GEN-END:_doDelete_2_be
        // Add custom code //GEN-FIRST:_doDelete_2


        // .//GEN-LAST:_doDelete_2
        // .//GEN-BEGIN:_doDelete_3_be
    }
    // .//GEN-END:_doDelete_3_be
    // .//GEN-BEGIN:_doRetrieve_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doRetrieve() throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("user_userRequestMaintenance_main", true, true, true, true));
        // .//GEN-END:_addScreens_1_be
        // Add custom code //GEN-FIRST:_addScreens_1


        // .//GEN-LAST:_addScreens_1
        // .//GEN-BEGIN:_addScreens_2_be
    }
    // .//GEN-END:_addScreens_2_be
    // .//GEN-BEGIN:_doPrevalidateCreate_1_be
    /** This performs prevalidations before creating a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateCreate() throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceCreateInDto input = new UserRequestMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setRequestId(getRequestId());
        input.setUserName(getUserName());
        input.setFirstName(getFirstName());
        input.setLastName(getLastName());
        input.setPassword1(getPassword1());
        input.setPassword2(getPassword2());
        input.setEMailAddress(getEMailAddress());
        input.setSecurityQuestion(getSecurityQuestion());
        input.setSecurityQuestion1(getSecurityQuestion1());
        input.setSecurityAnswer(getSecurityAnswer());
        input.setRemarks(getRemarks());
        input.setCreatedOn(getCreatedOn());
        input.setProcessedDatetime(getProcessedDatetime());
        input.setProcessedUserId(getProcessedUserId());
        input.setStatus(getStatus());
        UserRequestMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateCreate_2_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_2


        // .//GEN-LAST:_doPrevalidateCreate_2
        // .//GEN-BEGIN:_doPrevalidateCreate_3_be
    }
    // .//GEN-END:_doPrevalidateCreate_3_be
    // .//GEN-BEGIN:_doPrevalidateUpdate_1_be
    /** This performs prevalidations before updating a domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceUpdateInDto input = new UserRequestMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setRequestId(getRequestId());
        input.setUserName(getUserName());
        input.setFirstName(getFirstName());
        input.setLastName(getLastName());
        input.setPassword1(getPassword1());
        input.setPassword2(getPassword2());
        input.setEMailAddress(getEMailAddress());
        input.setSecurityQuestion(getSecurityQuestion());
        input.setSecurityQuestion1(getSecurityQuestion1());
        input.setSecurityAnswer(getSecurityAnswer());
        input.setRemarks(getRemarks());
        input.setCreatedOn(getCreatedOn());
        input.setProcessedDatetime(getProcessedDatetime());
        input.setProcessedUserId(getProcessedUserId());
        input.setStatus(getStatus());
        UserRequestMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateUpdate_2_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_2


        // .//GEN-LAST:_doPrevalidateUpdate_2
        // .//GEN-BEGIN:_doPrevalidateUpdate_3_be
    }
    // .//GEN-END:_doPrevalidateUpdate_3_be
    // .//GEN-BEGIN:_initDropDownCodes_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     */
    protected void initDropDownCodes() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initDropDownCodes_1_be
    // .//GEN-BEGIN:_createTx_1_be
    private IUserRequestMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IUserRequestMaintenance) Factory.createObject(IUserRequestMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the UserRequestMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private UserRequestMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        UserRequestMaintenanceRetrieveInDto input = new UserRequestMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setRequestId(getRequestId());
        UserRequestMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of UserRequestMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(UserRequestMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setRequestId(retrieveOutDto.getRequestId());
            setUserName(retrieveOutDto.getUserName());
            setFirstName(retrieveOutDto.getFirstName());
            setLastName(retrieveOutDto.getLastName());
            setPassword1(retrieveOutDto.getPassword1());
            setPassword2(retrieveOutDto.getPassword2());
            setEMailAddress(retrieveOutDto.getEMailAddress());
            setSecurityQuestion(retrieveOutDto.getSecurityQuestion());
            setSecurityQuestion1(retrieveOutDto.getSecurityQuestion1());
            setSecurityAnswer(retrieveOutDto.getSecurityAnswer());
            setRemarks(retrieveOutDto.getRemarks());
            setCreatedOn(retrieveOutDto.getCreatedOn());
            setProcessedDatetime(retrieveOutDto.getProcessedDatetime());
            setProcessedUserId(retrieveOutDto.getProcessedUserId());
            setStatus(retrieveOutDto.getStatus());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_loadRetrieveOutDto_1
 if (retrieveOutDto != null) {
       setSecurityQuestion1("" + retrieveOutDto.getSecurityQuestion());
   }

        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of UserRequestMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(UserRequestMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setRequestId(prevalidateOutDto.getRequestId());
            setUserName(prevalidateOutDto.getUserName());
            setFirstName(prevalidateOutDto.getFirstName());
            setLastName(prevalidateOutDto.getLastName());
            setPassword1(prevalidateOutDto.getPassword1());
            setPassword2(prevalidateOutDto.getPassword2());
            setEMailAddress(prevalidateOutDto.getEMailAddress());
            setSecurityQuestion(prevalidateOutDto.getSecurityQuestion());
            setSecurityQuestion1(prevalidateOutDto.getSecurityQuestion1());
            setSecurityAnswer(prevalidateOutDto.getSecurityAnswer());
            setRemarks(prevalidateOutDto.getRemarks());
            setCreatedOn(prevalidateOutDto.getCreatedOn());
            setProcessedDatetime(prevalidateOutDto.getProcessedDatetime());
            setProcessedUserId(prevalidateOutDto.getProcessedUserId());
            setStatus(prevalidateOutDto.getStatus());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code //GEN-FIRST:_loadPrevalidateOutDto_1
   if (prevalidateOutDto != null) {
       setSecurityQuestion1("" + prevalidateOutDto.getSecurityQuestion());
   }

        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here //GEN-FIRST:_custom
    String urlValue = null;
    public void setUrl(String value) {
        urlValue = value;
    }

    public String getUrl() {
        return urlValue;
    }

  public FormKey display() throws ApplicationExceptions, FrameworkException {

      if (this.getSecurityQuestion() == null) {
        this.setSecurityQuestion(new Long("1"));
      } else {
          this.setSecurityQuestion1("" + this.getSecurityQuestion());
      }
      if (this.getStatus() == null)
          this.setStatus("O");


      return super.display();

  }

    // .//GEN-LAST:_custom
}
