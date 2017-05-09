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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jaffa.datatypes.DateTime;

/**
 *  This is the DTO class for the tasks contains all attributes pertaining to
 *  them.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduledTask implements Serializable {
    
    /** An enumeration of choices available to the user for handling a misfire. */
    public enum MisfireRecovery {START_AS_SCHEDULED, RUN_LAST_MISSED}
    
    /** An enumeration of Task Status. */
    public enum TaskStatusEnumeration {BLOCKED, COMPLETE, ERROR, NORMAL, PAUSED, UNKNOWN}
    
    
    @XmlElement
    private String scheduledTaskId;
    
    @XmlElement
    private String taskType;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private String runAs;
    
    @XmlElement
    private DateTime startOn;
    
    @XmlElement
    private DateTime endOn;
    
    private transient Recurrence recurrence;
    
    @XmlElement
    private MisfireRecovery misfireRecovery;
    
    private transient Object businessObject;
    
    @XmlElement
    private DateTime createdOn;
    
    @XmlElement
    private String createdBy;
    
    @XmlElement
    private DateTime lastChangedOn;
    
    @XmlElement
    private String lastChangedBy;
    
    // *** The following properties are for info-only ***
    private transient DateTime nextDue;
    private transient DateTime lastRunOn;
    private transient TaskStatusEnumeration status;
    
    
    /**
     * Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public String getScheduledTaskId() {
        return this.scheduledTaskId;
    }
    
    /**
     * Setter for property scheduledTaskId.
     * @param scheduledTaskId New value of property scheduledTaskId.
     */
    public void setScheduledTaskId(String scheduledTaskId) {
        this.scheduledTaskId = scheduledTaskId;
    }
    
    /**
     * Getter for property taskType.
     * @return Value of property taskType.
     */
    public String getTaskType() {
        return this.taskType;
    }
    
    /**
     * Setter for property taskType.
     * @param taskType New value of property taskType.
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Getter for property runAs.
     * @return Value of property runAs.
     */
    public String getRunAs() {
        return this.runAs;
    }
    
    /**
     * Setter for property runAs.
     * @param runAs New value of property runAs.
     */
    public void setRunAs(String runAs) {
        this.runAs = runAs;
    }
    
    /**
     * Getter for property startOn.
     * @return Value of property startOn.
     */
    public DateTime getStartOn() {
        return this.startOn;
    }
    
    /**
     * Setter for property startOn.
     * @param startOn New value of property startOn.
     */
    public void setStartOn(DateTime startOn) {
        this.startOn = startOn;
    }
    
    /**
     * Getter for property endOn.
     * @return Value of property endOn.
     */
    public DateTime getEndOn() {
        return this.endOn;
    }
    
    /**
     * Setter for property endOn.
     * @param endOn New value of property endOn.
     */
    public void setEndOn(DateTime endOn) {
        this.endOn = endOn;
    }
    
    /**
     * Getter for property recurrence.
     * @return Value of property recurrence.
     */
    public Recurrence getRecurrence() {
        return this.recurrence;
    }
    
    /**
     * Setter for property recurrence.
     * @param recurrence New value of property recurrence.
     */
    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }
    
    /**
     * Getter for property misfireRecovery.
     * @return Enumeration value for MisfireRecovery.
     */
    public MisfireRecovery getMisfireRecovery() {
        return this.misfireRecovery;
    }
    
    /**
     * Setter for property misfireRecovery.
     * @param misfireRecovery New value of property misfireRecovery.
     */
    public void setMisfireRecovery(MisfireRecovery misfireRecovery) {
        this.misfireRecovery = misfireRecovery;
    }
    
    /**
     * Getter for property businessObject.
     * @return Value of property businessObject.
     */
    public Object getBusinessObject() {
        return this.businessObject;
    }
    
    /**
     * Setter for property businessObject.
     * @param businessObject New value of property businessObject.
     */
    public void setBusinessObject(Object businessObject) {
        this.businessObject = businessObject;
    }
    
    /**
     * Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTime getCreatedOn() {
        return this.createdOn;
    }
    
    /**
     * Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }
    
    /**
     * Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    /**
     * Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTime getLastChangedOn() {
        return this.lastChangedOn;
    }
    
    /**
     * Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTime lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }
    
    /**
     * Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return this.lastChangedBy;
    }
    
    /**
     * Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }
    
    /**
     * Getter for property nextDue.
     * @return Value of property nextDue.
     */
    public DateTime getNextDue() {
        return this.nextDue;
    }
    
    /**
     * Setter for property nextDue.
     * @param nextDue New value of property nextDue.
     */
    public void setNextDue(DateTime nextDue) {
        this.nextDue = nextDue;
    }
    
    /**
     * Getter for property lastRunOn.
     * @return Value of property lastRunOn.
     */
    public DateTime getLastRunOn() {
        return this.lastRunOn;
    }
    
    /**
     * Setter for property lastRunOn.
     * @param lastRunOn New value of property lastRunOn.
     */
    public void setLastRunOn(DateTime lastRunOn) {
        this.lastRunOn = lastRunOn;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public TaskStatusEnumeration getStatus() {
        return this.status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(TaskStatusEnumeration status) {
        this.status = status;
    }
    
    
    /** Returns debug info.
     * @return debug info.
     */
    public String toString() {
        StringBuilder buf = new StringBuilder("<ScheduledTask>")
        .append("<scheduledTaskId>").append(scheduledTaskId != null ? scheduledTaskId : "").append("</scheduledTaskId>")
        .append("<taskType>").append(taskType != null ? taskType : "").append("</taskType>")
        .append("<description>").append(description != null ? description : "").append("</description>")
        .append("<runAs>").append(runAs != null ? runAs : "").append("</runAs>")
        .append("<startOn>").append(startOn != null ? startOn : "").append("</startOn>")
        .append("<endOn>").append(endOn != null ? endOn : "").append("</endOn>")
        .append("<recurrence>").append(recurrence != null ? recurrence : "").append("</recurrence>")
        .append("<misfireRecovery>").append(misfireRecovery != null ? misfireRecovery : "").append("</misfireRecovery>")
        .append("<businessObject>").append(businessObject != null ? businessObject : "").append("</businessObject>")
        .append("<createdOn>").append(createdOn != null ? createdOn : "").append("</createdOn>")
        .append("<createdBy>").append(createdBy != null ? createdBy : "").append("</createdBy>")
        .append("<lastChangedOn>").append(lastChangedOn != null ? lastChangedOn : "").append("</lastChangedOn>")
        .append("<lastChangedBy>").append(lastChangedBy != null ? lastChangedBy : "").append("</lastChangedBy>")
        .append("<nextDue>").append(nextDue != null ? nextDue : "").append("</nextDue>")
        .append("<lastRunOn>").append(lastRunOn != null ? lastRunOn : "").append("</lastRunOn>")
        .append("<status>").append(status != null ? status : "").append("</status>")
        .append("</ScheduledTask>");
        return buf.toString();
    }
    
}
