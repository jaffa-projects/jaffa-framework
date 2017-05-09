// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui;

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

import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.IUserTimeEntryMaintenance;
import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserTimeEntryMaintenance.
 */
public class UserTimeEntryMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(UserTimeEntryMaintenanceComponent.class);
    private IUserTimeEntryMaintenance m_tx = null;

    private java.lang.String m_userName = null;
    private java.lang.String m_projectCode = null;
    private CodeHelperOutElementDto m_projectCodeCodes = null;
    private java.lang.String m_activity = null;
    private java.lang.String m_task = null;
    private org.jaffa.datatypes.DateTime m_periodStart = null;
    private org.jaffa.datatypes.DateTime m_periodEnd = null;

    private ICodeHelper m_codeHelperTx = null;

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
        if (m_codeHelperTx != null) {
            m_codeHelperTx.destroy();
            m_codeHelperTx = null;
        }
        m_projectCodeCodes = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
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
    // .//GEN-BEGIN:projectCode_1_be
    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public java.lang.String getProjectCode() {
        return m_projectCode;
    }

    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(java.lang.String projectCode) {
        m_projectCode = projectCode;
    }

    /** Getter for property projectCodeCodes.
     * @return Value of property projectCodeDd.
     */
    public CodeHelperOutElementDto getProjectCodeCodes() {
        return m_projectCodeCodes;
    }
    // .//GEN-END:projectCode_1_be
    // .//GEN-BEGIN:activity_1_be
    /** Getter for property activity.
     * @return Value of property activity.
     */
    public java.lang.String getActivity() {
        return m_activity;
    }

    /** Setter for property activity.
     * @param activity New value of property activity.
     */
    public void setActivity(java.lang.String activity) {
        m_activity = activity;
    }
    // .//GEN-END:activity_1_be
    // .//GEN-BEGIN:task_1_be
    /** Getter for property task.
     * @return Value of property task.
     */
    public java.lang.String getTask() {
        return m_task;
    }

    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(java.lang.String task) {
        m_task = task;
    }
    // .//GEN-END:task_1_be
    // .//GEN-BEGIN:periodStart_1_be
    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public org.jaffa.datatypes.DateTime getPeriodStart() {
        return m_periodStart;
    }

    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart) {
        m_periodStart = periodStart;
    }
    // .//GEN-END:periodStart_1_be
    // .//GEN-BEGIN:periodEnd_1_be
    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public org.jaffa.datatypes.DateTime getPeriodEnd() {
        return m_periodEnd;
    }

    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd) {
        m_periodEnd = periodEnd;
    }
    // .//GEN-END:periodEnd_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        UserTimeEntryMaintenanceCreateInDto input = new UserTimeEntryMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code //GEN-FIRST:_doCreate_1


        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setActivity(getActivity());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
        UserTimeEntryMaintenanceRetrieveOutDto output = createTx().create(input);
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
        UserTimeEntryMaintenanceUpdateInDto input = new UserTimeEntryMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code //GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setActivity(getActivity());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
        UserTimeEntryMaintenanceRetrieveOutDto output = createTx().update(input);
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
        UserTimeEntryMaintenanceDeleteInDto input = new UserTimeEntryMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code //GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
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
        UserTimeEntryMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("usertimeentry_userTimeEntryMaintenance_main", true, true, true, true));
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
        UserTimeEntryMaintenanceCreateInDto input = new UserTimeEntryMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setActivity(getActivity());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
        UserTimeEntryMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
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
        UserTimeEntryMaintenanceUpdateInDto input = new UserTimeEntryMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code //GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setActivity(getActivity());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
        UserTimeEntryMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
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
        if (m_codeHelperTx == null)
            m_codeHelperTx = (ICodeHelper) Factory.createObject(ICodeHelper.class);
        if (m_projectCodeCodes == null) {
            if (input == null)
                input = new CodeHelperInDto();
            CodeHelperInElementDto codeHelperInElementDto = new CodeHelperInElementDto();
            codeHelperInElementDto.setCode("projectCode");
            codeHelperInElementDto.setDomainClassName("org.jaffa.applications.test.modules.time.domain.ProjectCode");
            codeHelperInElementDto.setCodeFieldName("ProjectCode");
            codeHelperInElementDto.setDescriptionFieldName("Description");
            input.addCodeHelperInElementDto(codeHelperInElementDto);
        }

        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        // Get the Codes and populate the respective fields
        if (input != null) {
            input.setHeaderDto(getHeaderDto());
            CodeHelperOutDto output = m_codeHelperTx.getCodes(input);
            if (output != null && output.getCodeHelperOutElementDtoCount() > 0) {
                CodeHelperOutElementDto[] codeHelperOutElementDtos = output.getCodeHelperOutElementDtos();
                for (int i = 0; i < codeHelperOutElementDtos.length; i++) {
                    CodeHelperOutElementDto codeHelperOutElementDto = codeHelperOutElementDtos[i];
                    String code = codeHelperOutElementDto.getCode();
                    if (code.equals("projectCode"))
                        m_projectCodeCodes = codeHelperOutElementDto;
                }
            }
        }



    }
    // .//GEN-END:_initDropDownCodes_1_be
    // .//GEN-BEGIN:_createTx_1_be
    private IUserTimeEntryMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IUserTimeEntryMaintenance) Factory.createObject(IUserTimeEntryMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the UserTimeEntryMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private UserTimeEntryMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        UserTimeEntryMaintenanceRetrieveInDto input = new UserTimeEntryMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setUserName(getUserName());
        input.setProjectCode(getProjectCode());
        input.setTask(getTask());
        input.setPeriodStart(getPeriodStart());
        input.setPeriodEnd(getPeriodEnd());
        UserTimeEntryMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code //GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of UserTimeEntryMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(UserTimeEntryMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setUserName(retrieveOutDto.getUserName());
            setProjectCode(retrieveOutDto.getProjectCode());
            setActivity(retrieveOutDto.getActivity());
            setTask(retrieveOutDto.getTask());
            setPeriodStart(retrieveOutDto.getPeriodStart());
            setPeriodEnd(retrieveOutDto.getPeriodEnd());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_loadRetrieveOutDto_1


        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of UserTimeEntryMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(UserTimeEntryMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setUserName(prevalidateOutDto.getUserName());
            setProjectCode(prevalidateOutDto.getProjectCode());
            setActivity(prevalidateOutDto.getActivity());
            setTask(prevalidateOutDto.getTask());
            setPeriodStart(prevalidateOutDto.getPeriodStart());
            setPeriodEnd(prevalidateOutDto.getPeriodEnd());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code //GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
