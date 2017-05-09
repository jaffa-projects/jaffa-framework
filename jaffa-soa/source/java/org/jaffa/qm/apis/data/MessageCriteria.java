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

import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.IntegerCriteriaField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.soa.graph.GraphCriteria;

/**
 * A criteria class for querying Messages.
 *
 * @author GautamJ
 */
public class MessageCriteria extends GraphCriteria {

    private StringCriteriaField queueSystemId;
    private StringCriteriaField type;
    private StringCriteriaField subType;
    private StringCriteriaField messageId;
    private StringCriteriaField direction;
    private IntegerCriteriaField priority;
    private StringCriteriaField status;
    private DateTimeCriteriaField createdOn;
    private StringCriteriaField createdBy;
    private DateTimeCriteriaField lastChangedOn;
    private StringCriteriaField lastChangedBy;
    private StringCriteriaField errorMessage;
    private MessageFieldCriteria[] applicationFields;

    public StringCriteriaField getQueueSystemId() {
        return queueSystemId;
    }

    public void setQueueSystemId(StringCriteriaField queueSystemId) {
        this.queueSystemId = queueSystemId;
    }

    public StringCriteriaField getType() {
        return type;
    }

    public void setType(StringCriteriaField type) {
        this.type = type;
    }

    public StringCriteriaField getSubType() {
        return subType;
    }

    public void setSubType(StringCriteriaField subType) {
        this.subType = subType;
    }

    public StringCriteriaField getMessageId() {
        return messageId;
    }

    public void setMessageId(StringCriteriaField messageId) {
        this.messageId = messageId;
    }

    public StringCriteriaField getDirection() {
        return direction;
    }

    public void setDirection(StringCriteriaField direction) {
        this.direction = direction;
    }

    public IntegerCriteriaField getPriority() {
        return priority;
    }

    public void setPriority(IntegerCriteriaField priority) {
        this.priority = priority;
    }

    public StringCriteriaField getStatus() {
        return status;
    }

    public void setStatus(StringCriteriaField status) {
        this.status = status;
    }

    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    public DateTimeCriteriaField getLastChangedOn() {
        return lastChangedOn;
    }

    public void setLastChangedOn(DateTimeCriteriaField lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    public StringCriteriaField getLastChangedBy() {
        return lastChangedBy;
    }

    public void setLastChangedBy(StringCriteriaField lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    public StringCriteriaField getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(StringCriteriaField errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MessageFieldCriteria[] getApplicationFields() {
        return applicationFields;
    }

    public void setApplicationFields(MessageFieldCriteria[] applicationFields) {
        this.applicationFields = applicationFields;
    }
}
