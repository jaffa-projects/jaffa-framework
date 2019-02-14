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

package org.jaffa.modules.scheduler.components.taskfinder.tx;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.jms.Message;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.domain.BusinessEventLog;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.messaging.services.JmsBrowser;
import org.jaffa.modules.scheduler.components.taskfinder.ITaskFinder;
import org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutDto;
import org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutRowDto;
import org.jaffa.modules.scheduler.services.ScheduledTask;
import org.jaffa.modules.scheduler.services.SchedulerBrowser;
import org.jaffa.modules.scheduler.services.SchedulerHelper;
import org.jaffa.modules.scheduler.services.SchedulerHelperFactory;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.ExceptionHelper;


/** Finder for Task objects.
 */
public class TaskFinderTx implements ITaskFinder {

    private static final Logger log = Logger.getLogger(TaskFinderTx.class);

    /** This should be invoked, when done with the component.
     */
    public void destroy() {
        // does nothing
    }

    /** Searches for Task objects.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return The search results.
     */
    public TaskFinderOutDto find() throws FrameworkException, ApplicationExceptions {
      return find(null);
    }

    /** Searches for Task objects.
     * @param taskType taskType of tasks that should be returned.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return The search results.
     */
    public TaskFinderOutDto find(String taskType) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Retrieving all scheduled tasks");

            uow = new UOW();
            SchedulerHelper sh = SchedulerHelperFactory.instance();
            TaskFinderOutDto output = new TaskFinderOutDto();
            output.setSchedulerStatus(sh.getSchedulerStatus());
            if (output.getSchedulerStatus() == SchedulerHelper.SchedulerStatusEnumeration.STOPPED) {
                if (log.isDebugEnabled())
                    log.debug("The Scheduler has been shutdown. The Task list cannot be obtained");
            } else {
                Collection<TaskFinderOutRowDto> rows = new LinkedList<TaskFinderOutRowDto>();
                String[] taskTypes = taskType != null ? taskType.split(",") : null;
                if(taskTypes != null){
                    for(String tempTaskType : taskTypes){
                        populateTaskTypes(uow,tempTaskType,sh,rows);
                    }
                } else {
                    populateTaskTypes(uow,taskType,sh,rows);
                 }
                output.setRows((TaskFinderOutRowDto[])rows.toArray(new TaskFinderOutRowDto[rows.size()]));
            }

            if (log.isDebugEnabled())
                log.debug("Output: " + output);
            return output;
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }

    /** Activates the Task in the Scheduler.
     * @param taskId the identifier for a Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void activateTask(String taskId) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Activating Task " + taskId);
        SchedulerHelperFactory.instance().activateTask(taskId);
    }

    /** Inactivates the Task in the Scheduler.
     * @param taskId the identifier for a Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void inactivateTask(String taskId) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Inactivating Task " + taskId);
        SchedulerHelperFactory.instance().inactivateTask(taskId);
    }

    /** Pauses the Scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void pauseScheduler() throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Pausing Scheduler");
        SchedulerHelperFactory.instance().pauseScheduler();
    }

    /** Starts the Scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void startScheduler() throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Starting Scheduler");
        SchedulerHelperFactory.instance().startScheduler();
    }

    /** Stops the Scheduler.
     * NOTE: The scheduler cannot be re-started.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void stopScheduler() throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Stopping Scheduler");
        SchedulerHelperFactory.instance().shutdownScheduler();
    }

    /** Removes all event logs that resulted from the execution of the scheduler.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void clearEventLogs() throws FrameworkException, ApplicationExceptions {
        clearEventLogs(null);
    }

    /** Removes all event logs that resulted from the execution of the input Scheduled Task.
     * @param scheduledTaskId id of a Scheduled Task. If null, all event logs having a scheduledTaskId will be deleted.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void clearEventLogs(String scheduledTaskId) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            final int BATCH_SIZE = 500;
            if (log.isDebugEnabled())
                log.debug("Clearing all event logs that resulted from the execution of the " + (scheduledTaskId == null ? "scheduler" : "Task " + scheduledTaskId) + " in batches of " + BATCH_SIZE + " records");
            int count = 0;
            while (true) {
                uow = new UOW();
                Criteria c = new Criteria();
                c.setTable(BusinessEventLogMeta.getName());
                if (scheduledTaskId == null)
                    c.addCriteria(BusinessEventLogMeta.SCHEDULED_TASK_ID, Criteria.RELATIONAL_IS_NOT_NULL);
                else
                    c.addCriteria(BusinessEventLogMeta.SCHEDULED_TASK_ID, scheduledTaskId);
                //c.setLocking(Criteria.LOCKING_PARANOID); //unable to use paranoid locking with maxResults
                c.setMaxResults(BATCH_SIZE);
                Iterator i = uow.query(c).iterator();
                if (i.hasNext()) {
                    if (log.isDebugEnabled())
                        log.debug("Clearing event logs in loop " + ++count);
                    while (i.hasNext())
                        uow.delete(i.next());
                    uow.commit();
                } else {
                    if (log.isDebugEnabled())
                        log.debug("No more event logs exist for deletion");
                    break;
                }
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }




    /** Finds the last error for the input Task.
     * @param uow the UOW.
     * @param taskId the identifier for a Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private String findLastError(UOW uow, String taskId) throws FrameworkException, ApplicationExceptions {
        // select message_text from j_business_event_logs where scheduled_task_id='{taskId}'
        // and (message_type='ERROR' or message_type='FATAL' order by logged_on desc
        Criteria c = new Criteria();
        c.setTable(BusinessEventLogMeta.getName());
        c.addCriteria(BusinessEventLogMeta.SCHEDULED_TASK_ID, taskId);
        AtomicCriteria ac = new AtomicCriteria();
        ac.addCriteria(BusinessEventLogMeta.MESSAGE_TYPE, Level.ERROR.toString());
        ac.addOrCriteria(BusinessEventLogMeta.MESSAGE_TYPE, Level.FATAL.toString());
        c.addAtomic(ac);
        c.addOrderBy(BusinessEventLogMeta.LOGGED_ON, Criteria.ORDER_BY_DESC);
        Iterator i = uow.query(c).iterator();
        if (i.hasNext())
            return ((BusinessEventLog) i.next()).getMessageText();
        else
            return null;
    }


    private void populateTaskTypes(UOW uow, String taskType, SchedulerHelper sh, Collection<TaskFinderOutRowDto> rows) throws FrameworkException, ApplicationExceptions{
        ScheduledTask[] tasks = null;
        if(SecurityManager.checkFunctionAccess("Jaffa.Scheduler.Task.InquiryAll"))
            tasks = sh.getTasks(null ,taskType!=null?taskType:null);
        else
            tasks = sh.getTasks(org.jaffa.security.SecurityManager.getPrincipal().getName() ,taskType!=null?taskType:null);
        if (tasks != null) {
            for (ScheduledTask task : tasks) {
                if (SchedulerBrowser.hasBrowseTaskAccess(task)) {
                    TaskFinderOutRowDto row = new TaskFinderOutRowDto(task);
                    if (row.getFailedTaskCount() != null && row.getFailedTaskCount() > 0)
                        row.setLastError(findLastError(uow, task.getScheduledTaskId()));
                    row.setHasAdminTaskAccess(SchedulerBrowser.hasAdminTaskAccess(task.getTaskType()));
                    rows.add(row);
                }
            }

        }
    }

}