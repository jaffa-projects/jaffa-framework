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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.qm.apis.data;

import javax.xml.bind.annotation.XmlEnum;
import org.jaffa.datatypes.DateTime;

/**
 * Represents a Queue in a queue-system.
 *
 * @author GautamJ
 */
public class QueueGraph {

    @XmlEnum(String.class)
    public enum Status {

        ACTIVE, INACTIVE
    }
    private QueueMetaData queueMetaData;
    private String type;
    private Long openCount;
    private Long successCount;
    private Long errorCount;
    private Long holdCount;
    private Long inProcessCount;
    private Long interruptedCount;
    private Long consumerCount;
    private Status status;
    private Boolean hasAdminAccess;
    private DateTime lastErroredOn;

    public QueueMetaData getQueueMetaData() {
        return queueMetaData;
    }

    public void setQueueMetaData(QueueMetaData queueMetaData) {
        this.queueMetaData = queueMetaData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Long openCount) {
        this.openCount = openCount;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public Long getHoldCount() {
        return holdCount;
    }

    public void setHoldCount(Long holdCount) {
        this.holdCount = holdCount;
    }

    public Long getInProcessCount() {
        return inProcessCount;
    }

    public void setInProcessCount(Long inProcessCount) {
        this.inProcessCount = inProcessCount;
    }

    public Long getInterruptedCount() {
        return interruptedCount;
    }

    public void setInterruptedCount(Long interruptedCount) {
        this.interruptedCount = interruptedCount;
    }

    public Long getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(Long consumerCount) {
        this.consumerCount = consumerCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getHasAdminAccess() {
        return hasAdminAccess;
    }

    public void setHasAdminAccess(Boolean hasAdminAccess) {
        this.hasAdminAccess = hasAdminAccess;
    }

    public DateTime getLastErroredOn() {
        return lastErroredOn;
    }

    public void setLastErroredOn(DateTime lastErroredOn) {
        this.lastErroredOn = lastErroredOn;
    }
}
