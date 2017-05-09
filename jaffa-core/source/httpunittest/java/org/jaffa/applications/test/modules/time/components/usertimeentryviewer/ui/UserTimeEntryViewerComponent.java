// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui;

import org.apache.log4j.Logger;
import java.util.EventObject;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.middleware.Factory;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.maint.*;
import org.jaffa.components.dto.HeaderDto;

import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.IUserTimeEntryViewer;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerInDto;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerOutDto;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntry;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta;



import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui.UserTimeEntryMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserTimeEntryViewer.
 */
public class UserTimeEntryViewerComponent extends Component {
    
    private static Logger log = Logger.getLogger(UserTimeEntryViewerComponent.class);
    private HeaderDto m_headerDto = null;

    private java.lang.String m_userName;
    private java.lang.String m_projectCode;
    private java.lang.String m_task;
    private org.jaffa.datatypes.DateTime m_periodStart;
    private org.jaffa.datatypes.DateTime m_periodEnd;
    private UserTimeEntryViewerOutDto m_outputDto = null;
    private IUserTimeEntryViewer m_tx = null;

    private UserTimeEntryMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
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
        if (m_updateComponent != null) {
            m_updateComponent.quit();
            m_updateComponent = null;
        }
        m_updateListener = null;

        m_outputDto = null;
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
    // .//GEN-END:projectCode_1_be
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
    // .//GEN-BEGIN:_UserTimeEntryViewerOutDto_1_be
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */    
    public UserTimeEntryViewerOutDto getUserTimeEntryViewerOutDto() {
        return m_outputDto;
    }
    
    /** Setter for property outputDto.
     * @param outputDto New value of property outputDto.
     */    
    public void setUserTimeEntryViewerOutDto(UserTimeEntryViewerOutDto outputDto) {
        m_outputDto = outputDto;
    }
    // .//GEN-END:_UserTimeEntryViewerOutDto_1_be
    // .//GEN-BEGIN:_display_1_be
    /** This retrieves the details for the UserTimeEntry.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set, or if no data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */    
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_display_1_be
        // Add custom code before processing the method //GEN-FIRST:_display_1


        // .//GEN-LAST:_display_1
        // .//GEN-BEGIN:_display_2_be
        if (getUserName() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(UserTimeEntryMeta.META_USER_NAME.getLabelToken()) );
        }
        if (getProjectCode() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(UserTimeEntryMeta.META_PROJECT_CODE.getLabelToken()) );
        }
        if (getTask() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(UserTimeEntryMeta.META_TASK.getLabelToken()) );
        }
        if (getPeriodStart() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(UserTimeEntryMeta.META_PERIOD_START.getLabelToken()) );
        }
        if (getPeriodEnd() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(UserTimeEntryMeta.META_PERIOD_END.getLabelToken()) );
        }
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        doInquiry();
        return getViewerFormKey();
    }
    // .//GEN-END:_display_2_be
    // .//GEN-BEGIN:_inquiry_1_be
    private void doInquiry() throws ApplicationExceptions, FrameworkException {
        UserTimeEntryViewerInDto inputDto = new UserTimeEntryViewerInDto();
        // .//GEN-END:_inquiry_1_be
        // Add custom code before building the input dto //GEN-FIRST:_inquiry_1


        // .//GEN-LAST:_inquiry_1
        // .//GEN-BEGIN:_inquiry_2_be
        inputDto.setUserName(m_userName);
        inputDto.setProjectCode(m_projectCode);
        inputDto.setTask(m_task);
        inputDto.setPeriodStart(m_periodStart);
        inputDto.setPeriodEnd(m_periodEnd);

        inputDto.setHeaderDto(createHeaderDto());

        // create the Tx
        if (m_tx == null)
            m_tx = (IUserTimeEntryViewer) Factory.createObject(IUserTimeEntryViewer.class);

        // .//GEN-END:_inquiry_2_be
        // Add custom code before invoking the Tx //GEN-FIRST:_inquiry_2


        // .//GEN-LAST:_inquiry_2
        // .//GEN-BEGIN:_inquiry_3_be
        // now get the details
        m_outputDto = m_tx.read(inputDto);

        // uncache the widgets
        getUserSession().getWidgetCache(getComponentId()).clear();
        // .//GEN-END:_inquiry_3_be
        // Add custom code after invoking the Tx //GEN-FIRST:_inquiry_3


        // .//GEN-LAST:_inquiry_3
        // .//GEN-BEGIN:_inquiry_4_be
        // throw an exception if the output is null
        if (m_outputDto == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(UserTimeEntryMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_inquiry_4_be
    // .//GEN-BEGIN:_createHeaderDto_1_be
    private HeaderDto createHeaderDto() {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId( getUserSession().getUserId() );
            m_headerDto.setVariation( getUserSession().getVariation() );
            // .//GEN-END:_createHeaderDto_1_be
            // Add custom code before processing the action //GEN-FIRST:_createHeaderDto_1


            // .//GEN-LAST:_createHeaderDto_1
            // .//GEN-BEGIN:_createHeaderDto_2_be
        }
        return m_headerDto;
    }
    // .//GEN-END:_createHeaderDto_2_be
    // .//GEN-BEGIN:_getViewerFormKey_1_be
    public FormKey getViewerFormKey() {
        return new FormKey(UserTimeEntryViewerForm.NAME, getComponentId());
    }
    // .//GEN-END:_getViewerFormKey_1_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the UserTimeEntry.UserTimeEntryMaintenance component for updating the UserTimeEntry object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject() throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (UserTimeEntryMaintenanceComponent) run("UserTimeEntry.UserTimeEntryMaintenance");
            m_updateComponent.setReturnToFormKey(getViewerFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setUserName(getUserName());
        m_updateComponent.setProjectCode(getProjectCode());
        m_updateComponent.setTask(getTask());
        m_updateComponent.setPeriodStart(getPeriodStart());
        m_updateComponent.setPeriodEnd(getPeriodEnd());
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
                        doInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }

    private void addListeners(Component comp) {
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
    }
    // .//GEN-END:_updateObject_3_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
