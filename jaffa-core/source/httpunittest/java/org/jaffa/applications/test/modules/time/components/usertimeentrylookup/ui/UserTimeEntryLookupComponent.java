// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.test.modules.time.components.usertimeentrylookup.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.applications.test.modules.time.components.usertimeentrylookup.IUserTimeEntryLookup;
import org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupInDto;
import org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupOutDto;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta;


import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceComponent;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui.UserTimeEntryViewerComponent;
import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceComponent;
import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserTimeEntryLookup.
 */
public class UserTimeEntryLookupComponent extends LookupComponent2 {

    private static Logger log = Logger.getLogger(UserTimeEntryLookupComponent.class);

    private String m_userName = null;
    private String m_userNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_projectCode = null;
    private String m_projectCodeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_activity = null;
    private String m_activityDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_task = null;
    private String m_taskDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_periodStart = null;
    private String m_periodStartDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_periodEnd = null;
    private String m_periodEndDd = CriteriaField.RELATIONAL_EQUALS;

    private IUserTimeEntryLookup m_tx = null;
    private UserTimeEntryMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private UserTimeEntryMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private UserTimeEntryMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public UserTimeEntryLookupComponent() {
        super();
        super.setSortDropDown("UserName, ProjectCode, Task, PeriodStart, PeriodEnd");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "usertimeentry_userTimeEntryLookupCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "usertimeentry_userTimeEntryLookupResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "usertimeentry_userTimeEntryLookupConsolidatedCriteriaAndResults";
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
    // .//GEN-BEGIN:projectCode_1_be
    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public String getProjectCode() {
        return m_projectCode;
    }

    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(String projectCode) {
        m_projectCode = projectCode;
    }

    /** Getter for property projectCodeDd.
     * @return Value of property projectCodeDd.
     */
    public String getProjectCodeDd() {
        return m_projectCodeDd;
    }

    /** Setter for property projectCodeDd.
     * @param projectCodeDd New value of property projectCodeDd.
     */
    public void setProjectCodeDd(String projectCodeDd) {
        m_projectCodeDd = projectCodeDd;
    }

    // .//GEN-END:projectCode_1_be
    // .//GEN-BEGIN:activity_1_be
    /** Getter for property activity.
     * @return Value of property activity.
     */
    public String getActivity() {
        return m_activity;
    }

    /** Setter for property activity.
     * @param activity New value of property activity.
     */
    public void setActivity(String activity) {
        m_activity = activity;
    }

    /** Getter for property activityDd.
     * @return Value of property activityDd.
     */
    public String getActivityDd() {
        return m_activityDd;
    }

    /** Setter for property activityDd.
     * @param activityDd New value of property activityDd.
     */
    public void setActivityDd(String activityDd) {
        m_activityDd = activityDd;
    }

    // .//GEN-END:activity_1_be
    // .//GEN-BEGIN:task_1_be
    /** Getter for property task.
     * @return Value of property task.
     */
    public String getTask() {
        return m_task;
    }

    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(String task) {
        m_task = task;
    }

    /** Getter for property taskDd.
     * @return Value of property taskDd.
     */
    public String getTaskDd() {
        return m_taskDd;
    }

    /** Setter for property taskDd.
     * @param taskDd New value of property taskDd.
     */
    public void setTaskDd(String taskDd) {
        m_taskDd = taskDd;
    }

    // .//GEN-END:task_1_be
    // .//GEN-BEGIN:periodStart_1_be
    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public String getPeriodStart() {
        return m_periodStart;
    }

    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(String periodStart) {
        m_periodStart = periodStart;
    }

    /** Getter for property periodStartDd.
     * @return Value of property periodStartDd.
     */
    public String getPeriodStartDd() {
        return m_periodStartDd;
    }

    /** Setter for property periodStartDd.
     * @param periodStartDd New value of property periodStartDd.
     */
    public void setPeriodStartDd(String periodStartDd) {
        m_periodStartDd = periodStartDd;
    }

    // .//GEN-END:periodStart_1_be
    // .//GEN-BEGIN:periodEnd_1_be
    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public String getPeriodEnd() {
        return m_periodEnd;
    }

    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(String periodEnd) {
        m_periodEnd = periodEnd;
    }

    /** Getter for property periodEndDd.
     * @return Value of property periodEndDd.
     */
    public String getPeriodEndDd() {
        return m_periodEndDd;
    }

    /** Setter for property periodEndDd.
     * @param periodEndDd New value of property periodEndDd.
     */
    public void setPeriodEndDd(String periodEndDd) {
        m_periodEndDd = periodEndDd;
    }

    // .//GEN-END:periodEnd_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        UserTimeEntryLookupInDto inputDto = new UserTimeEntryLookupInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getUserName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getUserNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getUserNameDd() ) )
            inputDto.setUserName(StringCriteriaField.getStringCriteriaField(getUserNameDd(), getUserName(), null));

        if (getProjectCode() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getProjectCodeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getProjectCodeDd() ) )
            inputDto.setProjectCode(StringCriteriaField.getStringCriteriaField(getProjectCodeDd(), getProjectCode(), null));

        if (getActivity() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getActivityDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getActivityDd() ) )
            inputDto.setActivity(StringCriteriaField.getStringCriteriaField(getActivityDd(), getActivity(), null));

        if (getTask() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getTaskDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getTaskDd() ) )
            inputDto.setTask(StringCriteriaField.getStringCriteriaField(getTaskDd(), getTask(), null));

        try {
            if (getPeriodStart() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getPeriodStartDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getPeriodStartDd() ) )
                inputDto.setPeriodStart(DateTimeCriteriaField.getDateTimeCriteriaField(getPeriodStartDd(), getPeriodStart(), (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_START));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        try {
            if (getPeriodEnd() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getPeriodEndDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getPeriodEndDd() ) )
                inputDto.setPeriodEnd(DateTimeCriteriaField.getDateTimeCriteriaField(getPeriodEndDd(), getPeriodEnd(), (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_END));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IUserTimeEntryLookup) Factory.createObject(IUserTimeEntryLookup.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for creating a new UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for creating a new UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for creating a new UserTimeEntry object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (UserTimeEntryMaintenanceComponent) run("UserTimeEntry.UserTimeEntryMaintenance");
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
    /** Calls the UserTimeEntry.UserTimeEntryViewer component for viewing the UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException {
        UserTimeEntryViewerComponent viewComponent = (UserTimeEntryViewerComponent) run("UserTimeEntry.UserTimeEntryViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setUserName(userName);
        viewComponent.setProjectCode(projectCode);
        viewComponent.setTask(task);
        viewComponent.setPeriodStart(periodStart);
        viewComponent.setPeriodEnd(periodEnd);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for updating the UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (UserTimeEntryMaintenanceComponent) run("UserTimeEntry.UserTimeEntryMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setUserName(userName);
        m_updateComponent.setProjectCode(projectCode);
        m_updateComponent.setTask(task);
        m_updateComponent.setPeriodStart(periodStart);
        m_updateComponent.setPeriodEnd(periodEnd);
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
    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for deleting the UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (UserTimeEntryMaintenanceComponent) run("UserTimeEntry.UserTimeEntryMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setUserName(userName);
        m_deleteComponent.setProjectCode(projectCode);
        m_deleteComponent.setTask(task);
        m_deleteComponent.setPeriodStart(periodStart);
        m_deleteComponent.setPeriodEnd(periodEnd);
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
