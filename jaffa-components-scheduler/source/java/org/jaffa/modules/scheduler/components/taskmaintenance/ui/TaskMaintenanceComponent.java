/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
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

package org.jaffa.modules.scheduler.components.taskmaintenance.ui;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.CodeHelperInDto;
import org.jaffa.components.maint.MaintComponent2;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.middleware.Factory;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerComponent;
import org.jaffa.modules.scheduler.components.taskmaintenance.ITaskMaintenance;
import org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceDto;
import org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceOutDto;
import org.jaffa.modules.scheduler.components.taskviewer.ui.TaskViewerComponent;
import org.jaffa.modules.scheduler.services.JaffaSchedulerFrameworkException;
import org.jaffa.modules.scheduler.services.Recurrence;
import org.jaffa.modules.scheduler.services.ScheduledTask;
import org.jaffa.modules.scheduler.services.SchedulerBrowser;
import org.jaffa.modules.scheduler.services.SchedulerConfiguration;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.component.RiaWrapperComponent;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.JAXBHelper;

import javax.xml.bind.JAXBException;

/** The controller for the TaskMaintenance.
 */
public class TaskMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(TaskMaintenanceComponent.class);
    private ITaskMaintenance m_tx = null;
    private TaskMaintenanceDto m_taskMaintenanceDto = null;
    private String m_serializedBusinessObject = null; //Maintains the XML represenation of the businessObject
    private BusinessEventLogFinderOutDto m_businessEventLog = null;
    private Collection<String> m_taskTypeCodes = null;
    private String businessObjectXML = null;

    /** Creates a new instance.
     */
    public TaskMaintenanceComponent() {
        m_taskMaintenanceDto = new TaskMaintenanceDto();
        if (m_taskMaintenanceDto.getRunAs() == null  && SecurityManager.getPrincipal() != null)
            m_taskMaintenanceDto.setRunAs(SecurityManager.getPrincipal().getName());
        if (m_taskMaintenanceDto.getMisfireRecovery() == null)
            m_taskMaintenanceDto.setMisfireRecovery(ScheduledTask.MisfireRecovery.RUN_LAST_MISSED);
    }


    /**
     * Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public String getScheduledTaskId() {
        return m_taskMaintenanceDto.getScheduledTaskId();
    }

    /**
     * Setter for property scheduledTaskId.
     * @param scheduledTaskId New value of property scheduledTaskId.
     */
    public void setScheduledTaskId(String scheduledTaskId) {
        m_taskMaintenanceDto.setScheduledTaskId(scheduledTaskId);
    }

    /**
     * Getter for property taskType.
     * @return Value of property taskType.
     */
    public String getTaskType() {
        return m_taskMaintenanceDto.getTaskType();
    }

    /**
     * Setter for property taskType.
     * @param taskType New value of property taskType.
     */
    public void setTaskType(String taskType) {
        m_taskMaintenanceDto.setTaskType(taskType);
    }

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return m_taskMaintenanceDto.getDescription();
    }

    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        m_taskMaintenanceDto.setDescription(description);
    }

    /**
     * Getter for property runAs.
     * @return Value of property runAs.
     */
    public String getRunAs() {
        return m_taskMaintenanceDto.getRunAs();
    }

    /**
     * Setter for property runAs.
     * @param runAs New value of property runAs.
     */
    public void setRunAs(String runAs) {
        m_taskMaintenanceDto.setRunAs(runAs);
    }

    /**
     * Getter for property startOn.
     * @return Value of property startOn.
     */
    public DateTime getStartOn() {
        return m_taskMaintenanceDto.getStartOn();
    }

    /**
     * Setter for property startOn.
     * @param startOn New value of property startOn.
     */
    public void setStartOn(DateTime startOn) {
        m_taskMaintenanceDto.setStartOn(startOn);
    }

    /**
     * Getter for property endOn.
     * @return Value of property endOn.
     */
    public DateTime getEndOn() {
        return m_taskMaintenanceDto.getEndOn();
    }

    /**
     * Setter for property endOn.
     * @param endOn New value of property endOn.
     */
    public void setEndOn(DateTime endOn) {
        m_taskMaintenanceDto.setEndOn(endOn);
    }

    /**
     * Getter for property recurrence.
     * @return Value of property recurrence.
     */
    public Recurrence getRecurrence() {
        return m_taskMaintenanceDto.getRecurrence();
    }

    /**
     * Setter for property recurrence.
     * @param recurrence New value of property recurrence.
     */
    public void setRecurrence(Recurrence recurrence) {
        m_taskMaintenanceDto.setRecurrence(recurrence);
    }

    /**
     * Getter for property misfireRecovery.
     * @return Enumeration value for MisfireRecovery.
     */
    public ScheduledTask.MisfireRecovery getMisfireRecovery() {
        return m_taskMaintenanceDto.getMisfireRecovery();
    }

    /**
     * Setter for property misfireRecovery.
     * @param misfireRecovery New value of property misfireRecovery.
     */
    public void setMisfireRecovery(ScheduledTask.MisfireRecovery misfireRecovery) {
        m_taskMaintenanceDto.setMisfireRecovery(misfireRecovery);
    }

    /**
     * Getter for property businessObject.
     * @return Value of property businessObject.
     */
    public Object getBusinessObject() {
        return  m_taskMaintenanceDto.getBusinessObject();
    }

    /**
     * Setter for property businessObject.
     * @param businessObject New value of property businessObject.
     */
    public void setBusinessObject(Object businessObject) {
        m_taskMaintenanceDto.setBusinessObject(businessObject);
    }

    /**
     * Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTime getCreatedOn() {
        return m_taskMaintenanceDto.getCreatedOn();
    }

    /**
     * Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(DateTime createdOn) {
        m_taskMaintenanceDto.setCreatedOn(createdOn);
    }

    /**
     * Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return m_taskMaintenanceDto.getCreatedBy();
    }

    /**
     * Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        m_taskMaintenanceDto.setCreatedBy(createdBy);
    }

    /**
     * Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTime getLastChangedOn() {
        return m_taskMaintenanceDto.getLastChangedOn();
    }

    /**
     * Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTime lastChangedOn) {
        m_taskMaintenanceDto.setLastChangedOn(lastChangedOn);
    }

    /**
     * Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return m_taskMaintenanceDto.getLastChangedBy();
    }

    /**
     * Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        m_taskMaintenanceDto.setLastChangedBy(lastChangedBy);
    }

    /**
     * Getter for property nextDue.
     * @return Value of property nextDue.
     */
    public DateTime getNextDue() {
        return m_taskMaintenanceDto.getNextDue();
    }

    /**
     * Setter for property nextDue.
     * @param nextDue New value of property nextDue.
     */
    public void setNextDue(DateTime nextDue) {
        m_taskMaintenanceDto.setNextDue(nextDue);
    }

    /**
     * Getter for property lastRunOn.
     * @return Value of property lastRunOn.
     */
    public DateTime getLastRunOn() {
        return m_taskMaintenanceDto.getLastRunOn();
    }

    /**
     * Setter for property lastRunOn.
     * @param lastRunOn New value of property lastRunOn.
     */
    public void setLastRunOn(DateTime lastRunOn) {
        m_taskMaintenanceDto.setLastRunOn(lastRunOn);
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public ScheduledTask.TaskStatusEnumeration getStatus() {
        return m_taskMaintenanceDto.getStatus();
    }

    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(ScheduledTask.TaskStatusEnumeration status) {
        m_taskMaintenanceDto.setStatus(status);
    }

    /** Getter for property taskMaintenanceDto.
     * @return Value of property taskMaintenanceDto.
     */
    public TaskMaintenanceDto getTaskMaintenanceDto() {
        return m_taskMaintenanceDto;
    }

    /** Getter for property serializedBusinessObject.
     * @return Value of property serializedBusinessObject.
     */
    public String getSerializedBusinessObject() {
        return m_serializedBusinessObject;
    }

    /** Getter for property businessEventLog.
     * @return Value of property businessEventLog.
     */
    public BusinessEventLogFinderOutDto getBusinessEventLog() {
        return m_businessEventLog;
    }

    /** Getter for property taskTypeCodes.
     * @return Value of property taskTypeCodes.
     */
    public Collection<String> getTaskTypeCodes() {
        return m_taskTypeCodes;
    }

    public String getBusinessObjectXML() {
        return businessObjectXML;
    }

    public void setBusinessObjectXML(String businessObjectXML) {
        this.businessObjectXML = businessObjectXML;
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

    /** Removes all event logs that resulted from the execution of the scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void clearEventLogs() throws FrameworkException, ApplicationExceptions {
        TaskMaintenanceOutDto dto = createTx().clearEventLogs(m_taskMaintenanceDto);
        loadRetrieveOutDto(dto);
        invokeUpdateListeners();
    }

    /** Calls the Jaffa.Messaging.BusinessEventLogViewer component for viewing the BusinessEventLog object.
     * @param logId the identifier for the BusinessEventLog.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey displayBusinessEventLogViewer(String logId) throws ApplicationExceptions, FrameworkException {
        BusinessEventLogViewerComponent viewComponent = (BusinessEventLogViewerComponent) run("Jaffa.Messaging.BusinessEventLogViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setLogId(logId);
        return viewComponent.display();
    }

    /** Calls the Jaffa.Messaging.BusinessEventLogFinder component for displaying the BusinessEventLog objects.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Finder screen.
     */
    public FormKey displayBusinessEventLogFinder() throws ApplicationExceptions, FrameworkException{
        RiaWrapperComponent finderComponent = (RiaWrapperComponent) run("Jaffa.Messaging.BusinessEventLogFinder");
        finderComponent.getParameters().setProperty("displayResultsScreen", Boolean.TRUE.toString());
        finderComponent.getParameters().setProperty("scheduledTaskId", getScheduledTaskId());
        return finderComponent.display();
    }

    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_scheduler_taskMaintenance_main", true, true, true, true));

    }

    /** This will retrieve the set of codes for dropdowns, if any are required
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initDropDownCodes() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;

        if(getBusinessObjectXML() != null && getTaskType() != null){
            Task task = SchedulerConfiguration.getInstance().getTask(getTaskType());
            if (task != null && task.getDataBean() != null) {
                try {
                    setBusinessObject(JAXBHelper.unmarshalPayload(getBusinessObjectXML(), task.getDataBean()));
                } catch (JAXBException e) {
                    log.error("A task has not been configured for the dataBean " + task.getDataBean(), e);
                    throw new JaffaSchedulerFrameworkException(JaffaSchedulerFrameworkException.MISSING_TASK_FOR_DATA_BEAN, new Object[] { task.getDataBean()}, e);
                } catch (ClassNotFoundException e) {
                    log.error("A task has not been configured for the dataBean " + task.getDataBean(), e);
                    throw new JaffaSchedulerFrameworkException(JaffaSchedulerFrameworkException.MISSING_TASK_FOR_DATA_BEAN, new Object[] { task.getDataBean()}, e);
                }
            }
        }
        // Initialize the taskType DropDown
        m_taskTypeCodes = new TreeSet<String>();
        if (getBusinessObject() != null) {
            try {
                Task task = SchedulerConfiguration.getInstance().getTaskByDataBean(getBusinessObject().getClass().getName());
                if (task != null)
                    m_taskTypeCodes.add(task.getType());
            } catch (ClassNotFoundException e) {
                log.error("A task has not been configured for the dataBean " + getBusinessObject().getClass().getName(), e);
                throw new JaffaSchedulerFrameworkException(JaffaSchedulerFrameworkException.MISSING_TASK_FOR_DATA_BEAN, new Object[] {getBusinessObject().getClass().getName()}, e);
            }
        } else {
            Task[] tasks = SchedulerConfiguration.getInstance().getTasks();
            if (tasks != null) {
                for (Task task : tasks) {
                    if (task.isAutoCreateDataBean() && SchedulerBrowser.hasBrowseTaskAccess(task.getType()))
                        m_taskTypeCodes.add(task.getType());
                }
            }
        }
    }


    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        TaskMaintenanceOutDto dto = createTx().create(m_taskMaintenanceDto);
        loadRetrieveOutDto(dto);
    }

    /** This will invoke the update method on the transaction to update an existing domain object.
     * @param performDirtyReadCheck this is not implemented.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        TaskMaintenanceOutDto dto = createTx().update(m_taskMaintenanceDto);
        loadRetrieveOutDto(dto);
    }

    /** This will invoke the delete method on the transaction to delete an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doDelete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        createTx().delete(m_taskMaintenanceDto);
    }

    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doRetrieve() throws ApplicationExceptions, FrameworkException {
        TaskMaintenanceOutDto dto = createTx().retrieve(m_taskMaintenanceDto);
        loadRetrieveOutDto(dto);
    }

    /** Not implemented, since this component has only one screen.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        // do nothing
    }

    /** Not implemented, since this component has only one screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateCreate() throws ApplicationExceptions, FrameworkException {
        // do nothing
    }

    /** Creates a Tx, if it doesn't already exist.
     */
    private ITaskMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (ITaskMaintenance) Factory.createObject(ITaskMaintenance.class);
        return m_tx;
    }

    /** This will load the contents of TaskMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(TaskMaintenanceOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();
        m_taskMaintenanceDto = retrieveOutDto.getTaskMaintenanceDto();
        if (m_taskMaintenanceDto.getBusinessObject() != null) {
            try {
                m_serializedBusinessObject = JAXBHelper.marshalPayload(m_taskMaintenanceDto.getBusinessObject());
            } catch (Exception e) {
                //Just log a warning
                log.warn("Error in marshalling the Business Object into XML", e);
                m_serializedBusinessObject = m_taskMaintenanceDto.getBusinessObject().toString();
            }
        } else
            m_serializedBusinessObject = null;
        m_businessEventLog = retrieveOutDto.getBusinessEventLog();
    }

    public FormKey display() throws ApplicationExceptions, FrameworkException {
        //This display method got inherited by TaskViewerComponent. TaskViewerComponent should have access to Inquiry users.
        if (!(this instanceof TaskViewerComponent) && getBusinessObject()==null && !SecurityManager.checkFunctionAccess("Jaffa.Scheduler.Task.MaintenanceDirect") )
            throw new java.security.AccessControlException("Access to Jaffa.Scheduler.TaskMaintenanceDirect is required to create Scheduled Tasks directly.");

        return super.display();
    }

}
