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
package org.jaffa.transaction.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DateTime;
import org.jaffa.soa.graph.GraphDataObject;

/**
 * @author GautamJ
 */
public class TransactionGraph extends GraphDataObject {

    private String id;
    private String direction;
    private String type;
    private String subType;
    private String status;
    private String errorMessage;
    private DateTime createdOn;
    private String createdBy;
    private DateTime lastChangedOn;
    private String lastChangedBy;
    private TransactionPayloadGraph transactionPayload;
    private TransactionFieldGraph[] transactionFields;
    private TransactionDependencyGraph[] transactionDependencies;

    /**
     * Default constructor.
     */
    public TransactionGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property id.
     *
     * @return Value of property id.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for property id.
     *
     * @param id Value of property id.
     */
    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, id);
    }

    /**
     * Getter for property direction.
     *
     * @return Value of property direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Setter for property direction.
     *
     * @param direction Value of property direction.
     */
    public void setDirection(String direction) {
        String oldDirection = this.direction;
        this.direction = direction;
        propertyChangeSupport.firePropertyChange("direction", oldDirection, direction);
    }

    /**
     * Getter for property type.
     *
     * @return Value of property type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for property type.
     *
     * @param type Value of property type.
     */
    public void setType(String type) {
        String oldType = this.type;
        this.type = type;
        propertyChangeSupport.firePropertyChange("type", oldType, type);
    }

    /**
     * Getter for property subType.
     *
     * @return Value of property subType.
     */
    public String getSubType() {
        return subType;
    }

    /**
     * Setter for property subType.
     *
     * @param subType Value of property subType.
     */
    public void setSubType(String subType) {
        String oldSubType = this.subType;
        this.subType = subType;
        propertyChangeSupport.firePropertyChange("subType", oldSubType, subType);
    }

    /**
     * Getter for property status.
     *
     * @return Value of property status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for property status.
     *
     * @param status Value of property status.
     */
    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        propertyChangeSupport.firePropertyChange("status", oldStatus, status);
    }

    /**
     * Getter for property errorMessage.
     *
     * @return Value of property errorMessage.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter for property errorMessage.
     *
     * @param errorMessage Value of property errorMessage.
     */
    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        propertyChangeSupport.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    /**
     * Getter for property createdOn.
     *
     * @return Value of property createdOn.
     */
    public DateTime getCreatedOn() {
        return createdOn;
    }

    /**
     * Setter for property createdOn.
     *
     * @param createdOn Value of property createdOn.
     */
    public void setCreatedOn(DateTime createdOn) {
        DateTime oldCreatedOn = this.createdOn;
        this.createdOn = createdOn;
        propertyChangeSupport.firePropertyChange("createdOn", oldCreatedOn, createdOn);
    }

    /**
     * Getter for property createdBy.
     *
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for property createdBy.
     *
     * @param createdBy Value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        String oldCreatedBy = this.createdBy;
        this.createdBy = createdBy;
        propertyChangeSupport.firePropertyChange("createdBy", oldCreatedBy, createdBy);
    }

    /**
     * Getter for property lastChangedOn.
     *
     * @return Value of property lastChangedOn.
     */
    public DateTime getLastChangedOn() {
        return lastChangedOn;
    }

    /**
     * Setter for property lastChangedOn.
     *
     * @param lastChangedOn Value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTime lastChangedOn) {
        DateTime oldLastChangedOn = this.lastChangedOn;
        this.lastChangedOn = lastChangedOn;
        propertyChangeSupport.firePropertyChange("lastChangedOn", oldLastChangedOn, lastChangedOn);
    }

    /**
     * Getter for property lastChangedBy.
     *
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return lastChangedBy;
    }

    /**
     * Setter for property lastChangedBy.
     *
     * @param lastChangedBy Value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        String oldLastChangedBy = this.lastChangedBy;
        this.lastChangedBy = lastChangedBy;
        propertyChangeSupport.firePropertyChange("lastChangedBy", oldLastChangedBy, lastChangedBy);
    }

    /**
     * Getter for property transactionPayload.
     *
     * @return Value of property transactionPayload.
     */
    public TransactionPayloadGraph getTransactionPayload() {
        return transactionPayload;
    }

    /**
     * Setter for property transactionPayload.
     *
     * @param transactionPayload Value of property transactionPayload.
     */
    public void setTransactionPayload(TransactionPayloadGraph transactionPayload) {
        TransactionPayloadGraph oldTransactionPayload = this.transactionPayload;
        this.transactionPayload = transactionPayload;
        propertyChangeSupport.firePropertyChange("transactionPayload", oldTransactionPayload, transactionPayload);
    }

    /**
     * Getter for property transactionFields.
     *
     * @return Value of property transactionFields.
     */
    public TransactionFieldGraph[] getTransactionFields() {
        return transactionFields;
    }

    /**
     * Setter for property transactionFields.
     *
     * @param transactionFields Value of property transactionFields.
     */
    public void setTransactionFields(TransactionFieldGraph[] transactionFields) {
        TransactionFieldGraph[] oldTransactionFields = this.transactionFields;
        this.transactionFields = transactionFields;
        propertyChangeSupport.firePropertyChange("transactionFields", oldTransactionFields, transactionFields);
    }

    /**
     * Getter for property transactionDependencies.
     *
     * @return Value of property transactionDependencies.
     */
    public TransactionDependencyGraph[] getTransactionDependencies() {
        return transactionDependencies;
    }

    /**
     * Setter for property transactionDependencies.
     *
     * @param transactionDependencies Value of property transactionDependencies.
     */
    public void setTransactionDependencies(TransactionDependencyGraph[] transactionDependencies) {
        TransactionDependencyGraph[] oldTransactionDependencies = this.transactionDependencies;
        this.transactionDependencies = transactionDependencies;
        propertyChangeSupport.firePropertyChange("transactionDependencies", oldTransactionDependencies, transactionDependencies);
    }
}
