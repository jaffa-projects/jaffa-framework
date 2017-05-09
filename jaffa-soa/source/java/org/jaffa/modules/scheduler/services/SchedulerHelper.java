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

package org.jaffa.modules.scheduler.services;

import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** The interface SchedulerHelper offers wrappers of the scheduling functionality required.
 */
public interface SchedulerHelper {

    /**
     * <p>
     * Initialize the <code>{@link org.quartz.SchedulerFactory}</code> with the
     * contents of the given <code>Properties</code> object.
     * </p>
     */
    public void instantiateSchedulerFactory();

    /**
     * An enumeration of Scheduler Status.
     */
    public enum SchedulerStatusEnumeration {
        STOPPED, RUNNING, PAUSED, UNKNOWN
    }

    /**
     * Returns the list of Tasks from the Scheduler.
     *
     * @return the list of Tasks from the Scheduler.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public ScheduledTask[] getAllTasks() throws FrameworkException,
            ApplicationExceptions;

    /**
     * Returns the list of Tasks from the Scheduler created by a given user id with a given task type.
     *
     * @param userId   the identifier for a user.
     * @param taskType the type of tasks to be returned.
     * @return the list of Tasks from the Scheduler.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public ScheduledTask[] getTasks(String userId, String taskType) throws FrameworkException, ApplicationExceptions;

    /**
     * Returns the list of Tasks from the Scheduler for the given userId.
     *
     * @param userId the identifier for a user.
     * @return the list of Tasks from the Scheduler for the given userId.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public ScheduledTask[] getUserTasks(String userId) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Returns the Task from the Scheduler based on the given taskId.
     *
     * @param taskId the identifier for a Task.
     * @return the Task from the Scheduler based on the given taskId.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public ScheduledTask getTask(String taskId) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Updates the Task in the Scheduler.
     *
     * @param task the Task.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTask(ScheduledTask task) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Adds the Task to the Scheduler.
     *
     * @param task the Task.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void addTask(ScheduledTask task) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Deletes the Task from the Scheduler.
     *
     * @param taskId the identifier for a Task.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void deleteTask(String taskId) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Activates the Task in the Scheduler.
     *
     * @param taskId the identifier for a Task.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void activateTask(String taskId) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Inactivates the Task in the Scheduler.
     *
     * @param taskId the identifier for a Task.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void inactivateTask(String taskId) throws FrameworkException,
            ApplicationExceptions;

    /**
     * Pauses the Scheduler.
     */
    public void pauseScheduler();

    /**
     * Starts the Scheduler.
     */
    public void startScheduler();

    /**
     * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
     * and cleans up all resources associated with the Scheduler. Equivalent to
     * <code>shutdown(false)</code>.
     */
    public void shutdownScheduler();

    /**
     * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
     * and cleans up all resources associated with the Scheduler. Equivalent to
     * <code>shutdown(false)</code>.
     *
     * @param waitForJobsToComplete if <code>true</code> the scheduler will not allow this method to
     *                              return until all currently executing jobs have completed.
     */
    public void shutdownScheduler(boolean waitForJobsToComplete);

    /**
     * Returns the status of the Scheduler.
     *
     * @return the status of the Scheduler.
     */
    public SchedulerStatusEnumeration getSchedulerStatus();

    /**
     * Returns an array of execution dates for the given task, after the given
     * date.
     *
     * @param task  the Task.
     * @param after the date after which the execution dates are to be computed.
     * @param count the number of execution dates to compute.
     * @return an array of execution dates for the given task, after the given
     * date.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public DateTime[] getExecutionDates(ScheduledTask task, DateTime after,
                                        Integer count) throws FrameworkException, ApplicationExceptions;
}
