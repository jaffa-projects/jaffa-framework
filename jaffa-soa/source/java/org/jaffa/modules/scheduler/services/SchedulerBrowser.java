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

import org.apache.log4j.Logger;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.jaffa.security.SecurityManager;
import org.jaffa.transaction.services.ConfigurationService;
import org.jaffa.transaction.services.JaffaTransactionFrameworkException;
import org.jaffa.transaction.services.TransactionEngine;
import org.jaffa.transaction.services.configdomain.TransactionInfo;


/** This class provides Security related helper routines for the Scheduler.
 */
public class SchedulerBrowser {
    
    private static final Logger log = Logger.getLogger(SchedulerBrowser.class);
    
    /** Checks if the current user has access to Browse the Queue corresponding to the input Task type.
     * @param taskType the Task type.
     * @return true if the current user has the access.
     * @throws FrameworkException in case any internal error occurs.
     */
    public static boolean hasBrowseTaskAccess(String taskType) throws FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Finding Browse access to the Task Type " + taskType);
        boolean access = false;
        if (taskType != null) {
            String typeName = findTypeName(taskType);
            if (typeName != null)
            	access = TransactionEngine.getInstance().hasBrowseAccess(typeName);
                //access = JmsBrowser.hasBrowseQueueAccess(queueName);
        }
        if (log.isDebugEnabled())
            log.debug("Access is " + access);
        return access;
    }
    
    /** Checks if the current user has access to Browse the Queue corresponding to the input Task.
     * If the current user is not the owner of the Task, it also checks if the current user has access to Browse All Messages in that Queue.
     * @param task the Task.
     * @return true if the current user has the access.
     * @throws FrameworkException in case any internal error occurs.
     */
    public static boolean hasBrowseTaskAccess(ScheduledTask task) throws FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Finding Browse access to the Task Type " + (task != null ? task.getTaskType() : ""));
        boolean access = false;
        if (task != null && task.getTaskType() != null) {
            String typeName = findTypeName(task.getTaskType());
            if (typeName != null) {
            	if(TransactionEngine.getInstance().hasBrowseAccess(typeName))
            		access = isTaskOwner(task) || TransactionEngine.getInstance().hasAdminAccess(typeName);
            }
        }
        if (log.isDebugEnabled())
            log.debug("Access is " + access);
        return access;
    }
    
    /** Checks if the current user has access to Admin the Queue corresponding to the input Task type.
     * @param taskType the Task type.
     * @return true if the current user has the access.
     * @throws FrameworkException in case any internal error occurs.
     */
    public static boolean hasAdminTaskAccess(String taskType) throws FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Finding Admin access to the Task Type " + taskType);
        boolean access = false;
        if (taskType != null) {
            String typeName = findTypeName(taskType);
            if (typeName != null)
            	access = TransactionEngine.getInstance().hasAdminAccess(typeName);
        }
        if (log.isDebugEnabled())
            log.debug("Access is " + access);
        return access;
    }
    
    /** Returns the typeName corresponding to the input Task type.
     * @param taskType the Task type.
     * @return the typeName corresponding to the input Task.
     * @throws FrameworkException in case any internal error occurs.
     */
    public static String findTypeName(String taskType) throws FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Finding the Queue corresponding to the Task Type " + taskType);
        String typeName = null;
        if (taskType != null) {
            Task t = SchedulerConfiguration.getInstance().getTask(taskType);
            if (t != null && t.getDataBean() != null) {
                try {
                    TransactionInfo transactionInfo = ConfigurationService.getInstance().getTransactionInfo(t.getDataBean());
                    if (transactionInfo != null) {
                        typeName = transactionInfo.getType();
                        if (log.isDebugEnabled())
                            log.debug("Found Type " + typeName);
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("Type not found since Transaction has not been configured for dataBean " + t.getDataBean());
                    }
                } catch (ClassNotFoundException e) {
                    log.error("Error in locating the Transaction info for " + t.getDataBean(), e);
                    throw new JaffaTransactionFrameworkException(JaffaTransactionFrameworkException.TRANSACTION_INFO_MISSING, new Object[] {t.getDataBean()}, e);
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Queue not found since Task has not been configured for type " + taskType);
            }
        }
        return typeName;
    }
    
    /** Returns true if the input Task was created by the currently authenticated user.
     * @param task the Task.
     * @return true if the input Task was created by the currently authenticated user.
     */
    public static boolean isTaskOwner(ScheduledTask task) {
        String currentUserId = SecurityManager.getPrincipal() != null ? SecurityManager.getPrincipal().getName() : null;
        String createdBy = task.getCreatedBy();
        boolean result = currentUserId != null && currentUserId.equals(createdBy);
        if (!result && log.isDebugEnabled())
            log.debug(currentUserId + " is not owner of " +  task + ", which was created by " + createdBy);
        return result;
    }
    
}
