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

import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.transaction.domain.TransactionFieldMeta;
import org.jaffa.transaction.domain.TransactionMeta;
import org.jaffa.transaction.services.TransactionEngine;

/**
 * This class is used for passing query criteria to a graph-based service.
 */
public class TransactionCriteria extends GraphCriteria {

    private StringCriteriaField id;
    private StringCriteriaField direction;
    private StringCriteriaField type;
    private StringCriteriaField subType;
    private StringCriteriaField status;
    private StringCriteriaField errorMessage;
    private DateTimeCriteriaField createdOn;
    private StringCriteriaField createdBy;
    private DateTimeCriteriaField lastChangedOn;
    private StringCriteriaField lastChangedBy;
    private TransactionFieldCriteria[] transactionFields;

    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public StringCriteriaField getId() {
        return id;
    }

    /**
     * Setter for property id.
     * @param id Value of property id.
     */
    public void setId(StringCriteriaField id) {
        this.id = id;
    }

    /**
     * Getter for property direction.
     * @return Value of property direction.
     */
    public StringCriteriaField getDirection() {
        return direction;
    }

    /**
     * Setter for property direction.
     * @param direction Value of property direction.
     */
    public void setDirection(StringCriteriaField direction) {
        this.direction = direction;
    }

    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public StringCriteriaField getType() {
        return type;
    }

    /**
     * Setter for property type.
     * @param type Value of property type.
     */
    public void setType(StringCriteriaField type) {
        this.type = type;
    }

    /**
     * Getter for property subType.
     * @return Value of property subType.
     */
    public StringCriteriaField getSubType() {
        return subType;
    }

    /**
     * Setter for property subType.
     * @param subType Value of property subType.
     */
    public void setSubType(StringCriteriaField subType) {
        this.subType = subType;
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public StringCriteriaField getStatus() {
        return status;
    }

    /**
     * Setter for property status.
     * @param status Value of property status.
     */
    public void setStatus(StringCriteriaField status) {
        this.status = status;
    }

    /**
     * Getter for property errorMessage.
     * @return Value of property errorMessage.
     */
    public StringCriteriaField getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter for property errorMessage.
     * @param errorMessage Value of property errorMessage.
     */
    public void setErrorMessage(StringCriteriaField errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }

    /**
     * Setter for property createdOn.
     * @param createdOn Value of property createdOn.
     */
    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for property createdBy.
     * @param createdBy Value of property createdBy.
     */
    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTimeCriteriaField getLastChangedOn() {
        return lastChangedOn;
    }

    /**
     * Setter for property lastChangedOn.
     * @param lastChangedOn Value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTimeCriteriaField lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /**
     * Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public StringCriteriaField getLastChangedBy() {
        return lastChangedBy;
    }

    /**
     * Setter for property lastChangedBy.
     * @param lastChangedBy Value of property lastChangedBy.
     */
    public void setLastChangedBy(StringCriteriaField lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    /**
     * Getter for property transactionFields.
     * @return Value of property transactionFields.
     */
    public TransactionFieldCriteria[] getTransactionFields() {
        return transactionFields;
    }

    /**
     * Setter for property transactionFields.
     * @param transactionFields Value of property transactionFields.
     */
    public void setTransactionFields(TransactionFieldCriteria[] transactionFields) {
        this.transactionFields = transactionFields;
    }

    /** Returns the criteria object used for retrieving domain objects.
     * @param nestedClause Minimal criteria used to retreive the nested object. Will be null for the root query.
     * @return the criteria object used for retrieving domain objects.
     */
    @Override
    public Criteria returnQueryClause(Criteria nestedClause) {
        setTableName(TransactionMeta.getName());
        Criteria c = super.returnQueryClause(nestedClause);
        FinderTx.addCriteria(getId(), TransactionMeta.ID, c);
        FinderTx.addCriteria(getDirection(), TransactionMeta.DIRECTION, c);
        
        FinderTx.addCriteria(getSubType(), TransactionMeta.SUB_TYPE, c);
        FinderTx.addCriteria(getStatus(), TransactionMeta.STATUS, c);
        FinderTx.addCriteria(getErrorMessage(), TransactionMeta.ERROR_MESSAGE, c);
        FinderTx.addCriteria(getCreatedOn(), TransactionMeta.CREATED_ON, c);
        FinderTx.addCriteria(getCreatedBy(), TransactionMeta.CREATED_BY, c);
        FinderTx.addCriteria(getLastChangedOn(), TransactionMeta.LAST_CHANGED_ON, c);
        FinderTx.addCriteria(getLastChangedBy(), TransactionMeta.LAST_CHANGED_BY, c);

        FinderTx.addCriteria(getType(), TransactionMeta.TYPE, c);
        
        String[] types = TransactionEngine.getInstance().getAccessibleTypeNames();
        StringCriteriaField strCrit = new StringCriteriaField();
        strCrit.setOperator(CriteriaField.RELATIONAL_IN);
        if(types!=null && types.length>0) {
        	strCrit.setValues(types);
        } else {
        	strCrit.setValues(new String[]{null});
        }
        FinderTx.addCriteria(strCrit, TransactionMeta.TYPE, c);
                
        // Include TransactionField criteria
        if (getTransactionFields() != null) {
            for (TransactionFieldCriteria tfc : getTransactionFields()) {
                if (tfc.getFieldName() != null) {
                    // SELECT
                    //   J_TRANSACTIONS.*
                    // FROM
                    //   J_TRANSACTIONS, J_TRANSACTION_FIELDS T1, J_TRANSACTION_FIELDS T2, ...
                    // WHERE
                    //   J_TRANSACTIONS.SOME_FIELD='SOME_VALUE'...
                    //   AND T1.TRANSACTION_ID = J_TRANSACTIONS.ID AND (T1.FIELD_NAME='F1') AND (T1.VALUE='V1')
                    //   AND T2.TRANSACTION_ID = J_TRANSACTIONS.ID AND (T2.FIELD_NAME='F2') AND (T2.VALUE='V2')
                    //   AND ...
                    Criteria joinCriteria = new Criteria();
                    joinCriteria.setTable(TransactionFieldMeta.getName());
                    joinCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
                    joinCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, tfc.getFieldName());
                    FinderTx.addCriteria(tfc.getValue(), TransactionFieldMeta.VALUE, joinCriteria);
                    c.addAggregate(joinCriteria);
                }
            }
        }
                return c;
    }
}
