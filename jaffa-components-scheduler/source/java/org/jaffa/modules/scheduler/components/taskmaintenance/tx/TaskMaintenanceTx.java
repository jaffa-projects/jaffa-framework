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

package org.jaffa.modules.scheduler.components.taskmaintenance.tx;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.components.maint.MaintTx;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.tx.BusinessEventLogFinderTx;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.modules.scheduler.components.taskfinder.tx.TaskFinderTx;
import org.jaffa.modules.scheduler.components.taskmaintenance.ITaskMaintenance;
import org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceDto;
import org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceOutDto;
import org.jaffa.modules.scheduler.services.JaffaSchedulerApplicationException;
import org.jaffa.modules.scheduler.services.ScheduledTask;
import org.jaffa.modules.scheduler.services.SchedulerHelperFactory;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.ExceptionHelper;


/** Maintenance for Task objects.
 */
public class TaskMaintenanceTx extends MaintTx implements ITaskMaintenance {
    
    private static Logger log = Logger.getLogger(TaskMaintenanceTx.class);
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy() {
    }
    
    /** Creates a new scheduledTask in the scheduler.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public TaskMaintenanceOutDto create(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input to create(): " + input);
        
        // Add the task to the scheduler
        SchedulerHelperFactory.instance().addTask(input);
        
        // Return the persisted data
        if (log.isDebugEnabled())
            log.debug("Task has been created. Will retrieve the persisted data");
        return retrieve(input);
    }
    
    /** Returns the details for Task.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public TaskMaintenanceOutDto retrieve(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input to retrieve(): " + input);
        
        // Retrieve the task
        ScheduledTask task = SchedulerHelperFactory.instance().getTask(input.getScheduledTaskId());
        if (task == null)
            throw new ApplicationExceptions(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.TASK_NOT_FOUND, new Object[] {input.getScheduledTaskId()}));
        
        // Mold the task into the TaskMaintenanceDto
        TaskMaintenanceOutDto output = buildRetrieveOutDto(task);
        
        if (log.isDebugEnabled())
            log.debug("Output from retrieve(): " + output);
        return output;
    }
    
    /** Updates an existing instance of Task.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public TaskMaintenanceOutDto update(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input to update(): " + input);
        
        // Update the task in the scheduler
        SchedulerHelperFactory.instance().updateTask(input);
        
        // Return the persisted data
        if (log.isDebugEnabled())
            log.debug("Task has been updated. Will retrieve the persisted data");
        return retrieve(input);
    }
    
    /** Deletes an existing instance of Task.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input to delete(): " + input);
        
        // Delete the task from the scheduler
        SchedulerHelperFactory.instance().deleteTask(input.getScheduledTaskId());
        
        if (log.isDebugEnabled())
            log.debug("Task has been deleted");
    }
    
    /** Removes all event logs that resulted from the execution of the given Task.
     * @param input The key values for the Task.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return The object details.
     */
    public TaskMaintenanceOutDto clearEventLogs(TaskMaintenanceDto input) throws FrameworkException, ApplicationExceptions {
        // Delete the associated event logs
        new TaskFinderTx().clearEventLogs(input.getScheduledTaskId());

        // Return the persisted data
        if (log.isDebugEnabled())
            log.debug("Event logs have been cleared. Will retrieve the current data");
        return retrieve(input);
    }
    
    
    private TaskMaintenanceOutDto buildRetrieveOutDto(ScheduledTask task)
    throws ApplicationExceptions, FrameworkException{
        TaskMaintenanceOutDto output = new TaskMaintenanceOutDto();
        output.setTaskMaintenanceDto(new TaskMaintenanceDto(task));
        output.setBusinessEventLog(buildBusinessEventLogDto(task));
        return output;
    }
    
    private BusinessEventLogFinderOutDto buildBusinessEventLogDto(ScheduledTask task)
    throws FrameworkException, ApplicationExceptions{
        //Create BusinessEventLogFinderInDto
        BusinessEventLogFinderInDto inputDto =  new BusinessEventLogFinderInDto();
        inputDto.setScheduledTaskId(new StringCriteriaField(CriteriaField.RELATIONAL_EQUALS, task.getScheduledTaskId())) ;
        inputDto.setMaxRecords(new Integer(10));
        inputDto.setOrderByFields(new OrderByField[] {new OrderByField(BusinessEventLogMeta.LOGGED_ON, Boolean.FALSE)});
        
        //Retrive businessEventLogFinderOutDto
        return new BusinessEventLogFinderTx().find(inputDto);
    }
    
}
