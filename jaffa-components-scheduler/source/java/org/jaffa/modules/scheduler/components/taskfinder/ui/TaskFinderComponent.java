/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.modules.scheduler.components.taskfinder.ui;

import java.util.EventObject;
import org.apache.log4j.Logger;
import org.jaffa.components.maint.ICreateComponent;
import org.jaffa.components.maint.ICreateListener;
import org.jaffa.components.maint.IDeleteComponent;
import org.jaffa.components.maint.IDeleteListener;
import org.jaffa.components.maint.IMaintComponent;
import org.jaffa.components.maint.IUpdateComponent;
import org.jaffa.components.maint.IUpdateListener;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.middleware.Factory;
import org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerComponent;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.messaging.services.JmsBrowser;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.scheduler.components.taskfinder.ITaskFinder;
import org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutDto;
import org.jaffa.modules.scheduler.components.taskmaintenance.ui.TaskMaintenanceComponent;
import org.jaffa.modules.scheduler.components.taskviewer.ui.TaskViewerComponent;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.component.Component;


/** The controller for the TaskFinder.
 */
public class TaskFinderComponent extends Component {

    private static Logger log = Logger.getLogger(TaskFinderComponent.class);
    private String m_taskType = null;
    private TaskFinderOutDto m_finderOutDto = null;
    private Exception m_error = null;
    private ITaskFinder m_tx = null;
    private TaskMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private TaskMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private TaskMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    /** Getter for property taskType.
     * @return Value of property taskType.
     */
    public String getTaskType() {
        return m_taskType;
    }

    /** Setter for property taskType.
     * @param taskType New value of property taskType.
     */
    public void setTaskType(String taskType) {
        m_taskType = taskType;
    }

    /** Getter for property finderOutDto.
     * @return Value of property finderOutDto.
     */
    public TaskFinderOutDto getFinderOutDto() {
        return m_finderOutDto;
    }

    /** Getter for property error.
     * @return Value of property error.
     */
    public Exception getError() {
        return m_error;
    }

    /** This should be invoked when done with the component.
     */
    public void quit() {
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

    /** Invokes the displayResults() method.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        return displayResults();
    }

    /** Invokes the performInquiry() method and then returns the FormKey for the Results screen.
     * If the consolidatedCriteriaAndResults property has been set to true, then the FormKey for consolidatedCriteriaAndResults screen will be returned.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey displayResults() throws ApplicationExceptions, FrameworkException {
        performInquiry();
        return getResultsFormKey();
    }

    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    public void performInquiry() throws ApplicationExceptions, FrameworkException {
        getUserSession().getWidgetCache(getComponentId()).clear();
        try {
            m_finderOutDto = createTx().find(getTaskType());
            m_error = null;
        } catch (Exception e) {
            // Render an empty screen with an error indicator
            m_finderOutDto = null;
            m_error = e;
        }
    }

    /** Getter for the Results screen's FormKey.
     * @return the FormKey for the Results screen.
     */
    public FormKey getResultsFormKey() {
        return new FormKey(getResultsFormName(), getComponentId());
    }

    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_scheduler_taskFinderResults";
    }

    /** Calls the Jaffa.Scheduler.TaskMaintenance component for creating a new Task object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Scheduler.TaskMaintenance component for creating a new Task object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    private FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (TaskMaintenanceComponent) run("Jaffa.Scheduler.TaskMaintenance");
        m_createComponent.setReturnToFormKey(formKey);
        addListeners(m_createComponent);
        if (m_createComponent instanceof IMaintComponent)
            ((IMaintComponent) m_createComponent).setMode(IMaintComponent.MODE_CREATE);
        return m_createComponent.display();
    }

    private ICreateListener getCreateListener() {
        if (m_createListener == null) {
            m_createListener = new ICreateListener() {
                public void createDone(EventObject source) {
                    try {
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Create", e);
                    }
                }
            };
        }
        return m_createListener;
    }

    /** Calls the Jaffa.Scheduler.TaskViewer component for viewing the Task object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String scheduledTaskId) throws ApplicationExceptions, FrameworkException {
        TaskViewerComponent viewComponent = (TaskViewerComponent) run("Jaffa.Scheduler.TaskViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setScheduledTaskId(scheduledTaskId);
        return viewComponent.display();
    }

    /** Calls the Jaffa.Scheduler.TaskMaintenance component for updating the Task object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String scheduledTaskId) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (TaskMaintenanceComponent) run("Jaffa.Scheduler.TaskMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setScheduledTaskId(scheduledTaskId);
        if (m_updateComponent instanceof IMaintComponent)
            ((IMaintComponent) m_updateComponent).setMode(IMaintComponent.MODE_UPDATE);
        return m_updateComponent.display();
    }

    private IUpdateListener getUpdateListener() {
        if (m_updateListener == null) {
            m_updateListener = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }
    /** Calls the Jaffa.Scheduler.TaskMaintenance component for deleting the Task object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String scheduledTaskId)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (TaskMaintenanceComponent) run("Jaffa.Scheduler.TaskMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setScheduledTaskId(scheduledTaskId);
        if (m_deleteComponent instanceof IMaintComponent)
            ((IMaintComponent) m_deleteComponent).setMode(IMaintComponent.MODE_DELETE);
        return m_deleteComponent.display();
    }

    private IDeleteListener getDeleteListener() {
        if (m_deleteListener == null) {
            m_deleteListener = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListener;
    }

    private void addListeners(Component comp) {
        if (comp  instanceof ICreateComponent)
            ((ICreateComponent) comp).addCreateListener(getCreateListener());
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListener());
    }

    private ITaskFinder createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (ITaskFinder) Factory.createObject(ITaskFinder.class);
        return m_tx;
    }

    /** Activates the Task in the Scheduler.
     * @param taskId the identifier for a Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions {
        createTx().activateTask(taskId);
    }

    /** Inactivates the Task in the Scheduler.
     * @param taskId the identifier for a Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions {
        createTx().inactivateTask(taskId);
    }

    /** Pauses the Scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void pauseScheduler() throws FrameworkException, ApplicationExceptions {
        createTx().pauseScheduler();
    }

    /** Starts the Scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void startScheduler() throws FrameworkException, ApplicationExceptions {
        createTx().startScheduler();
    }

    /** Stops the Scheduler.
     * NOTE: The scheduler cannot be re-started.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void stopScheduler() throws FrameworkException, ApplicationExceptions {
        createTx().stopScheduler();
    }

    /** Removes all event logs that resulted from the execution of the scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void clearEventLogs() throws FrameworkException, ApplicationExceptions {
        createTx().clearEventLogs();
    }

    /** Calls the Jaffa.Messaging.QueueViewer component for viewing the failed tasks.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the QueueViewer screen.
     */
    public FormKey viewFailedTasks(String scheduledTaskId, Object businessObject) throws ApplicationExceptions, FrameworkException {
        // Create a filter with the input scheduledTaskId
        String filter = new StringBuilder(JmsBrowser.HEADER_SCHEDULED_TASK_ID)
        .append('=').append('\'').append(scheduledTaskId).append('\'').toString();

        // Figure out the appropriate queueName based on the businessObject, so that the QueueViewer displays the relevant columns
        String queueName = null;
        if (businessObject != null) {
            try {
                MessageInfo messageInfo = ConfigurationService.getInstance().getMessageInfo(businessObject.getClass().getName());
                if (messageInfo!=null)
                    queueName = messageInfo.getQueueName();
            } catch (ClassNotFoundException e) {
                // do nothing
            }
        }

        // Instantiate the QueueViewer
        QueueViewerComponent viewComponent = (QueueViewerComponent) run("Jaffa.Messaging.QueueViewer");
        viewComponent.setFilter(filter);
        if (queueName != null) {
            viewComponent.setQueue(queueName);
            viewComponent.setMessageMode(MessageModeEnum.ERROR);
        }
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        return viewComponent.display();
    }
}