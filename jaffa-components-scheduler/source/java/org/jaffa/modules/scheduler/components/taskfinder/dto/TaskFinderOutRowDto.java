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

package org.jaffa.modules.scheduler.components.taskfinder.dto;

import org.jaffa.modules.scheduler.services.ScheduledTask;

/** The output for the TaskFinder contains an array of instances of this class.
 */
public class TaskFinderOutRowDto extends ScheduledTask {
    
    private String lastError;
    private Integer failedTaskCount;
    private Boolean hasAdminTaskAccess;
    
    /** Creates a new instance.
     */
    public TaskFinderOutRowDto() {
        super();
    }
    
    /** Creates a new instance.
     * @param task the new instance is initialized with the values from the input task.
     */
    public TaskFinderOutRowDto(ScheduledTask task) {
        if (task != null) {
            setBusinessObject(task.getBusinessObject());
            setCreatedBy(task.getCreatedBy());
            setCreatedOn(task.getCreatedOn());
            setDescription(task.getDescription());
            setEndOn(task.getEndOn());
            setLastChangedBy(task.getLastChangedBy());
            setLastChangedOn(task.getLastChangedOn());
            setLastRunOn(task.getLastRunOn());
            setMisfireRecovery(task.getMisfireRecovery());
            setNextDue(task.getNextDue());
            setRecurrence(task.getRecurrence());
            setRunAs(task.getRunAs());
            setScheduledTaskId(task.getScheduledTaskId());
            setStartOn(task.getStartOn());
            setStatus(task.getStatus());
            setTaskType(task.getTaskType());
        }
    }
    
    
    /** Getter for property lastError.
     * @return Value of property lastError.
     */
    public String getLastError() {
        return lastError;
    }
    
    /** Setter for property lastError.
     * @param lastError New value of property lastError.
     */
    public void setLastError(String lastError) {
        this.lastError = lastError;
    }
    
    /** Getter for property failedTaskCount.
     * @return Value of property failedTaskCount.
     */
    public Integer getFailedTaskCount() {
        return failedTaskCount;
    }
    
    /** Setter for property failedTaskCount.
     * @param failedTaskCount New value of property failedTaskCount.
     */
    public void setFailedTaskCount(Integer failedTaskCount) {
        this.failedTaskCount = failedTaskCount;
    }
    
    /** Getter for property hasAdminTaskAccess.
     * @return Value of property hasAdminTaskAccess.
     */
    public Boolean getHasAdminTaskAccess() {
        return hasAdminTaskAccess;
    }
    
    /** Setter for property hasAdminTaskAccess.
     * @param hasAdminTaskAccess New value of property hasAdminTaskAccess.
     */
    public void setHasAdminTaskAccess(Boolean hasAdminTaskAccess) {
        this.hasAdminTaskAccess = hasAdminTaskAccess;
    }
    
    
    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<TaskFinderOutRowDto>");
        buf.append(super.toString());
        buf.append("<lastError>"); if (lastError != null) buf.append(lastError); buf.append("</lastError>");
        buf.append("<failedTaskCount>"); if (failedTaskCount != null) buf.append(failedTaskCount); buf.append("</failedTaskCount>");
        buf.append("<hasAdminTaskAccess>"); if (hasAdminTaskAccess != null) buf.append(hasAdminTaskAccess); buf.append("</hasAdminTaskAccess>");
        buf.append("</TaskFinderOutRowDto>");
        return buf.toString();
    }
    
}
