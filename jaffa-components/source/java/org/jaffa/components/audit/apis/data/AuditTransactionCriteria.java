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
package org.jaffa.components.audit.apis.data;

import org.jaffa.components.audit.domain.AuditTransactionFieldMeta;
import org.jaffa.components.audit.domain.AuditTransactionMeta;
import org.jaffa.components.audit.domain.AuditTransactionObjectMeta;
import org.jaffa.components.audit.domain.AuditTransactionViewMeta;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;

/**
 *
 * @author GautamJ
 */
public class AuditTransactionCriteria extends GraphCriteria {

    private boolean queryView; //Determines if the query should be against the View or Table
    private StringCriteriaField transactionId;
    private StringCriteriaField processName;
    private StringCriteriaField subProcessName;
    private StringCriteriaField reason;
    private StringCriteriaField changeType;
    private StringCriteriaField createdBy;
    private DateTimeCriteriaField createdOn;
    private String objectId;
    private String objectName;
    private AuditTransactionFieldCriteria[] auditTransactionFields;

    /**
     * If the input parameter is true, then the query will be against the AuditTransaction view,
     * else the query will be against AuditTransaction table.
     * @param queryView boolean indicating if the query should be against the AuditTransaction view or table.
     */
    public void queryView(boolean queryView) {
        this.queryView = queryView;
    }

    /** Returns the criteria object used for retrieving AuditTransaction records.
     * Based on the state of the queryView property, the criteria will target
     * either the AuditTransaction view or the table.
     * @param c Criteria object
     * @return the criteria object used for retrieving AuditTransaction records.
     */
    @Override
    public Criteria returnQueryClause(Criteria c) {
        c = super.returnQueryClause(c);
        return this.queryView ? returnQueryClauseAgainstView(c) : returnQueryClauseAgainstTable(c);
    }

    /** Returns the criteria object used for retrieving AuditTransaction records from the table.
     * @param c Criteria object
     * @return the criteria object used for retrieving AuditTransaction records.
     */
    private Criteria returnQueryClauseAgainstTable(Criteria c) {
        c.setTable(AuditTransactionMeta.getName());
        FinderTx.addCriteria(getTransactionId(), AuditTransactionMeta.TRANSACTION_ID, c);
        FinderTx.addCriteria(getProcessName(), AuditTransactionMeta.PROCESS_NAME, c);
        FinderTx.addCriteria(getSubProcessName(), AuditTransactionMeta.SUB_PROCESS_NAME, c);
        FinderTx.addCriteria(getReason(), AuditTransactionMeta.REASON, c);
        FinderTx.addCriteria(getCreatedBy(), AuditTransactionMeta.CREATED_BY, c);
        FinderTx.addCriteria(getCreatedOn(), AuditTransactionMeta.CREATED_ON, c);

        // Join AuditTransactionObject with AuditTransaction
        if (getObjectName() != null) {
            Criteria oc = new Criteria();
            oc.setTable(AuditTransactionObjectMeta.getName());
            oc.addInnerCriteria(AuditTransactionObjectMeta.TRANSACTION_ID, AuditTransactionMeta.TRANSACTION_ID);
            oc.addCriteria(AuditTransactionObjectMeta.OBJECT_NAME, getObjectName());
            c.addAggregate(oc);

            // Join each AuditTransactionField with AuditTransactionObject
            if (getAuditTransactionFields() != null) {
                for (AuditTransactionFieldCriteria atf : getAuditTransactionFields()) {
                    if (atf.getFieldName() != null) {
                        Criteria fc = new Criteria();
                        fc.setTable(AuditTransactionFieldMeta.getName());
                        fc.addInnerCriteria(AuditTransactionFieldMeta.OBJECT_ID, AuditTransactionObjectMeta.OBJECT_ID);
                        fc.addCriteria(AuditTransactionFieldMeta.FIELD_NAME, atf.getFieldName());
                        FinderTx.addCriteria(atf.getToValue(), AuditTransactionFieldMeta.TO_VALUE, fc);
                        FinderTx.addCriteria(atf.getChanged(), AuditTransactionFieldMeta.CHANGED, fc);
                        FinderTx.addCriteria(atf.getFlex(), AuditTransactionFieldMeta.FLEX, fc);
                        oc.addAggregate(fc);
                    }
                }
            }
        }
        return c;
    }

    /** Returns the criteria object used for retrieving AuditTransaction records from the view.
     * @param c Criteria object
     * @return the criteria object used for retrieving AuditTransaction records.
     */
    private Criteria returnQueryClauseAgainstView(Criteria c) {
        c.setTable(AuditTransactionViewMeta.getName());
        FinderTx.addCriteria(getTransactionId(), AuditTransactionViewMeta.TRANSACTION_ID, c);
        FinderTx.addCriteria(getProcessName(), AuditTransactionViewMeta.PROCESS_NAME, c);
        FinderTx.addCriteria(getSubProcessName(), AuditTransactionViewMeta.SUB_PROCESS_NAME, c);
        FinderTx.addCriteria(getReason(), AuditTransactionViewMeta.REASON, c);
        FinderTx.addCriteria(getChangeType(), AuditTransactionViewMeta.CHANGE_TYPE, c);
        FinderTx.addCriteria(getCreatedBy(), AuditTransactionViewMeta.CREATED_BY, c);
        FinderTx.addCriteria(getCreatedOn(), AuditTransactionViewMeta.CREATED_ON, c);

        if (getObjectName() != null || getObjectId() != null) {
            if (getObjectName() != null) {
                c.addCriteria(AuditTransactionViewMeta.OBJECT_NAME, getObjectName());
            } else {
                c.addCriteria(AuditTransactionViewMeta.OBJECT_ID, getObjectId());
            }

            // Join each AuditTransactionField with AuditTransactionObject
            if (getAuditTransactionFields() != null) {
                for (AuditTransactionFieldCriteria atf : getAuditTransactionFields()) {
                    if (atf.getFieldName() != null) {
                        Criteria fc = new Criteria();
                        fc.setTable(AuditTransactionFieldMeta.getName());
                        fc.addInnerCriteria(AuditTransactionFieldMeta.OBJECT_ID, AuditTransactionViewMeta.OBJECT_ID);
                        fc.addCriteria(AuditTransactionFieldMeta.FIELD_NAME, atf.getFieldName());
                        FinderTx.addCriteria(atf.getToValue(), AuditTransactionFieldMeta.TO_VALUE, fc);
                        FinderTx.addCriteria(atf.getChanged(), AuditTransactionFieldMeta.CHANGED, fc);
                        FinderTx.addCriteria(atf.getFlex(), AuditTransactionFieldMeta.FLEX, fc);
                        c.addAggregate(fc);
                    }
                }
            }
        }
        return c;
    }

    public StringCriteriaField getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(StringCriteriaField transactionId) {
        this.transactionId = transactionId;
    }

    public StringCriteriaField getProcessName() {
        return processName;
    }

    public void setProcessName(StringCriteriaField processName) {
        this.processName = processName;
    }

    public StringCriteriaField getSubProcessName() {
        return subProcessName;
    }

    public void setSubProcessName(StringCriteriaField subProcessName) {
        this.subProcessName = subProcessName;
    }

    public StringCriteriaField getReason() {
        return reason;
    }

    public void setReason(StringCriteriaField reason) {
        this.reason = reason;
    }

    public StringCriteriaField getChangeType() {
        return changeType;
    }

    public void setChangeType(StringCriteriaField changeType) {
        this.changeType = changeType;
    }

    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public AuditTransactionFieldCriteria[] getAuditTransactionFields() {
        return auditTransactionFields;
    }

    public void setAuditTransactionFields(AuditTransactionFieldCriteria[] auditTransactionFields) {
        this.auditTransactionFields = auditTransactionFields;
    }
}
