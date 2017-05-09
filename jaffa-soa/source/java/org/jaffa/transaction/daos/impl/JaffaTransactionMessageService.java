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

package org.jaffa.transaction.daos.impl;

import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.transaction.apis.TransactionService;
import org.jaffa.transaction.apis.data.TransactionCriteria;
import org.jaffa.transaction.apis.data.TransactionFieldCriteria;
import org.jaffa.transaction.apis.data.TransactionGraph;
import org.jaffa.transaction.apis.data.TransactionQueryResponse;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageService;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionDependency;
import org.jaffa.transaction.domain.TransactionDependencyMeta;
import org.jaffa.transaction.domain.TransactionDependencySweeperView;
import org.jaffa.transaction.domain.TransactionDependencySweeperViewMeta;
import org.jaffa.transaction.domain.TransactionField;
import org.jaffa.transaction.domain.TransactionFieldMeta;
import org.jaffa.transaction.domain.TransactionMeta;
import org.jaffa.transaction.domain.TransactionPayload;
import org.jaffa.transaction.domain.TransactionPayloadMeta;
import org.jaffa.transaction.domain.TransactionStatusCount;
import org.jaffa.transaction.domain.TransactionSweeperView;
import org.jaffa.transaction.domain.TransactionSweeperViewMeta;
import org.jaffa.transaction.services.TransactionEngine;
import org.jaffa.transaction.services.configdomain.Param;
import org.jaffa.transaction.services.configdomain.TransactionInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Used to access Transaction model information from outside of the internal processing
 * of Transactions done by Jaffa.  This allows us to abstract the need for data from the specific details
 * of how that data is persisted.
 */
public class JaffaTransactionMessageService implements TransactionMessageService, TransactionMessageDAO {

    /**
     * Takes the input list of Transaction IDs and filters it down to the Transactions that are either in
     * the Open or InProgress state.  The filtered list is returned.
     *
     * @param transactionIds the list of all Transaction IDs to check the status of
     * @return Transaction IDs of the input list that are in the Open or InProgress state
     * @throws FrameworkException
     */
    @Override
    public List<String> filterSetOfTxIdsToOpenOrInProgress(List<String> transactionIds) throws FrameworkException {
        UOW uow = null;
        List<String> results = new ArrayList<String>();
        try {
            uow = new UOW();
            AtomicCriteria ac = new AtomicCriteria();
            for (String transactionId : transactionIds) {
                ac.addOrCriteria(TransactionMeta.ID, transactionId);
            }
            AtomicCriteria ac1 = new AtomicCriteria();
            ac1.addCriteria(TransactionMeta.STATUS, Transaction.Status.O.toString());
            ac1.addOrCriteria(TransactionMeta.STATUS, Transaction.Status.I.toString());
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addAtomic(ac);
            criteria.addAtomic(ac1);
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                results.add(((Transaction) result).getId());
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets the count of all Transactions in the input list of IDs that are in the error state.
     *
     * @param transactionIds the IDs of the Transactions to check for the error state
     * @return the count of all Transactions in the error state
     * @throws FrameworkException
     */
    @Override
    public long getCountTxInError(List<String> transactionIds) throws FrameworkException {
        UOW uow = null;
        long errorCount = 0;
        try {
            uow = new UOW();
            AtomicCriteria ac = new AtomicCriteria();
            for (String transactionId : transactionIds) {
                ac.addOrCriteria(TransactionMeta.ID, transactionId);
            }
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addCriteria(TransactionMeta.STATUS, Transaction.Status.E.toString());
            criteria.addAtomic(ac);
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                errorCount = count.longValue();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return errorCount;
    }

    /**
     * Gets the count of Transactions of a specified type.
     *
     * @param type the type of Transactions to return the count of
     * @return the count of Transactions of the input type
     * @throws FrameworkException
     */
    @Override
    public long getCountByType(String type) throws FrameworkException {
        UOW uow = null;
        long transactionCount = 0;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            criteria.addCriteria(TransactionMeta.TYPE, type);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                transactionCount = count.longValue();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionCount;
    }

    /**
     * Gets the count of inbound Transactions with the specified field-value, type and subType that
     * are in the Open or InProgress state.
     *
     * @param field   the field to check the value of
     * @param value   the input value of the field we are looking for
     * @param type    the type of the Transaction
     * @param subType the subType of the Transaction
     * @return the count of Transactions with the specified field-value, type and subType that are in the
     *         Open or inProgress state.
     * @throws FrameworkException
     */
    @Override
    public long getCountInboundOpenInProgress(String field, String value, String type, String subType) throws FrameworkException {
        UOW uow = null;
        long transactionCount = 0;
        try {
            uow = new UOW();
            AtomicCriteria ac = new AtomicCriteria();

            // we want Open and In Progress
            ac.addCriteria(TransactionMeta.STATUS, Transaction.Status.O.toString());
            ac.addOrCriteria(TransactionMeta.STATUS, Transaction.Status.I.toString());

            // add the field name and value
            Criteria joinCriteria = new Criteria();
            joinCriteria.setTable(TransactionFieldMeta.getName());
            joinCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            joinCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field);
            joinCriteria.addCriteria(TransactionFieldMeta.VALUE, value);

            // add the type, subType and direction
            Criteria c = new Criteria();
            c.setTable(TransactionMeta.getName());
            c.addCriteria(TransactionMeta.TYPE, type);
            c.addCriteria(TransactionMeta.SUB_TYPE, subType);
            c.addCriteria(TransactionMeta.DIRECTION, Transaction.Direction.IN.toString());
            c.addAtomic(ac);
            c.addAggregate(joinCriteria);
            c.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);

            // return the count, or zero
            Iterator itr = uow.query(c).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                transactionCount = count.longValue();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionCount;
    }

    /**
     * Gets the count of all Transactions with the specified field-value that are in the Open or InProgress state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @return the count of all Transactions with the specified field-value that are in the Open or InProgress state
     * @throws FrameworkException
     */
    @Override
    public long getCountOpenInProgress(String field, String value) throws FrameworkException {
        UOW uow = null;
        long transactionCount = 0;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());

            AtomicCriteria ac0 = new AtomicCriteria();
            ac0.addCriteria(TransactionMeta.STATUS, Transaction.Status.O.toString());
            ac0.addOrCriteria(TransactionMeta.STATUS, Transaction.Status.I.toString());
            criteria.addAtomic(ac0);

            Criteria transactionFieldCriteria = new Criteria();
            transactionFieldCriteria.setTable(TransactionFieldMeta.getName());
            transactionFieldCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            criteria.addAggregate(transactionFieldCriteria);

            AtomicCriteria ac = new AtomicCriteria();
            ac.addCriteria(TransactionFieldMeta.FIELD_NAME, field);
            ac.addCriteria(TransactionFieldMeta.VALUE, value);

            transactionFieldCriteria.addAtomic(ac);

            // return the count, or zero
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                transactionCount++;
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionCount;
    }

    /**
     * Gets the count of all Transactions with the specified field-value and type that are in the Satisfied or
     * Error state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @param type  the type of the Transaction
     * @return count of all Transactions with the specified field-value that are in the Open or InProgress state
     * @throws FrameworkException
     */
    @Override
    public long getCountSatisfiedOrInError(String field, String value, String type) throws FrameworkException {
        UOW uow = null;
        long transactionCount = 0;
        try {
            uow = new UOW();

            Criteria c1 = new Criteria();
            c1.setTable(TransactionFieldMeta.getName());
            c1.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            c1.addCriteria(TransactionFieldMeta.FIELD_NAME, field);
            c1.addCriteria(TransactionFieldMeta.VALUE, value);

            Criteria c = new Criteria();
            c.setTable(TransactionMeta.getName());
            c.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            c.addCriteria(TransactionMeta.TYPE, type);

            AtomicCriteria statusCriteria = new AtomicCriteria();
            statusCriteria.addCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_EQUALS, Transaction.Status.S.toString());
            statusCriteria.addOrCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_EQUALS, Transaction.Status.E.toString());
            c.addAtomic(statusCriteria);

            // return the count, or zero
            Iterator itr = uow.query(c).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                if (count != null) {
                    transactionCount = count.longValue();
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionCount;
    }

    /**
     * Gets the count of all Transactions with the specified field-value and type that are not in the Satisfied or
     * Error state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @param type  the type of the Transaction
     * @return count of all Transactions with the specified field-value that are not in the Open or InProgress state
     * @throws FrameworkException
     */
    @Override
    public long getCountNotSatisfiedOrInError(String field, String value, String type) throws FrameworkException {
        UOW uow = null;
        long transactionCount = 0;
        try {
            uow = new UOW();

            Criteria c1 = new Criteria();
            c1.setTable(TransactionFieldMeta.getName());
            c1.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            c1.addCriteria(TransactionFieldMeta.FIELD_NAME, field);
            c1.addCriteria(TransactionFieldMeta.VALUE, value);

            Criteria c = new Criteria();
            c.setTable(TransactionMeta.getName());
            c.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            c.addCriteria(TransactionMeta.TYPE, type);
            c.addCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_NOT_EQUALS, Transaction.Status.S.toString());
            c.addCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_NOT_EQUALS, Transaction.Status.E.toString());
            c.addAggregate(c1);

            // return the count, or zero
            Iterator itr = uow.query(c).iterator();
            if (itr.hasNext()) {
                Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                if (count != null) {
                    transactionCount = count.longValue();
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionCount;
    }

    /**
     * Gets a list of all open inbound Transactions of the input type, ordered descending by the createdOn parameter.
     *
     * @param type the type of Transactions to return
     * @return all open inbound Transactions of the input type, ordered by the createdOn parameter
     * @throws FrameworkException
     */
    @Override
    public List<Transaction> getOpenOutboundByTypeOrderByCreatedOnDesc(String type) throws FrameworkException {
        UOW uow = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addCriteria(TransactionMeta.DIRECTION, Transaction.Direction.OUT.toString());
            criteria.addCriteria(TransactionMeta.TYPE, type);
            criteria.addCriteria(TransactionMeta.STATUS, Transaction.Status.O.toString());
            criteria.addOrderBy(TransactionMeta.CREATED_ON);

            for (Object result : uow.query(criteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                transactions.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactions;
    }

    /**
     * Gets all Transactions with the input field-value and returns them ordered descending by the createdOn parameter.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @return all Transactions with the input field-value and returns them ordered by the createdOn parameter
     * @throws FrameworkException
     */
    @Override
    public List<Transaction> findByFieldValueOrderByCreatedOnDesc(String field, String value) throws FrameworkException {
        UOW uow = null;
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            uow = new UOW();
            Criteria transactionCriteria = new Criteria();
            transactionCriteria.setTable(TransactionMeta.getName());
            Criteria joinCriteria = new Criteria();
            joinCriteria.setTable(TransactionFieldMeta.getName());
            joinCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            joinCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field);
            joinCriteria.addCriteria(TransactionFieldMeta.VALUE, value);
            transactionCriteria.addAggregate(joinCriteria);
            transactionCriteria.addOrderBy(TransactionMeta.CREATED_ON);

            for (Object result : uow.query(transactionCriteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                transactions.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactions;
    }

    /**
     * Gets the Transaction that has the specified fields with the input values.
     *
     * @param fields the field name/value pairs
     * @return the Transaction that contains the specified fields with the input values
     * @throws FrameworkException
     */
    @Override
    public Transaction getTransactionByFields(HashMap<String, String> fields) throws FrameworkException {
        UOW uow = null;
        Transaction transaction = null;
        try {
            uow = new UOW();
            Criteria transactionCriteria = new Criteria();
            transactionCriteria.setTable(TransactionMeta.getName());

            // add each field in the field map to the query
            for (Map.Entry<String, String> field : fields.entrySet()) {
                if ((field.getKey() == null) || field.getKey().isEmpty()) {
                    continue;
                }
                Criteria fieldCriteria = new Criteria();
                fieldCriteria.setTable(TransactionFieldMeta.getName());
                fieldCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
                fieldCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field.getKey());
                fieldCriteria.addCriteria(TransactionFieldMeta.VALUE, field.getValue());
                transactionCriteria.addAggregate(fieldCriteria);
            }

            for (Object result : uow.query(transactionCriteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                transaction = (Transaction) result;
                break;
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transaction;
    }

    /**
     * Gets the Transaction that has the specified fields-values and also fields that begin with
     * the specified values.  If multiple beginsWith values are defined for a field, the field will be checked against
     * them all.
     *
     * @param fields     the field name/value pairs
     * @param beginsWith the field name/begins-with values
     * @return the Transaction that contains the specified fields with the input values
     * @throws FrameworkException
     */
    @Override
    public Transaction getTransactionByFieldsAndBeginsWithFields(HashMap<String, String> fields, HashMap<String, String> beginsWith) throws FrameworkException {
        UOW uow = null;
        Transaction transaction = null;
        try {
            uow = new UOW();
            TransactionCriteria transactionCriteria = new TransactionCriteria();
            List<TransactionFieldCriteria> tranFieldCriteriaList = new ArrayList<TransactionFieldCriteria>();

            // add all of the field criteria
            for (Map.Entry<String, String> field : fields.entrySet()) {
                TransactionFieldCriteria fieldCriteria = new TransactionFieldCriteria();
                fieldCriteria.setFieldName(field.getKey());
                StringCriteriaField stringCriteria = StringCriteriaField.getStringCriteriaField(StringCriteriaField.RELATIONAL_EQUALS, field.getValue(), null);
                fieldCriteria.setValue(stringCriteria);
                tranFieldCriteriaList.add(fieldCriteria);
            }

            // add all of the begins with criteria
            for (Map.Entry<String, String> beginsWithField : beginsWith.entrySet()) {
                TransactionFieldCriteria beginsWithCriteria = new TransactionFieldCriteria();
                beginsWithCriteria.setFieldName(beginsWithField.getKey());
                StringCriteriaField stringCriteria = StringCriteriaField.getStringCriteriaField(StringCriteriaField.RELATIONAL_BEGINS_WITH, beginsWithField.getValue(), null);
                beginsWithCriteria.setValue(stringCriteria);
                tranFieldCriteriaList.add(beginsWithCriteria);
            }

            // set all of the field criteria on the transaction criteria
            TransactionFieldCriteria[] criteriaArray = new TransactionFieldCriteria[tranFieldCriteriaList.size()];
            criteriaArray = tranFieldCriteriaList.toArray(criteriaArray);
            transactionCriteria.setTransactionFields(criteriaArray);

            // call the transaction service for a response to the query
            TransactionService transactionService = new TransactionService();
            TransactionQueryResponse response = transactionService.query(transactionCriteria);

            // return a transaction from the response
            for (TransactionGraph transactionGraph : response.getGraphs()) {
                if ((transactionGraph != null) && (transactionGraph.getId() != null)) {
                    transaction = getTransaction(transactionGraph.getId());
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transaction;
    }

    /**
     * Gets all Transactions that have the specified fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param fields the field name/value pairs
     * @return a collection of Transactions that contain the specified fields with the input values
     * @throws FrameworkException
     */
    @Override
    public Collection<Transaction> getTransactionsByFields(HashMap<String, List<Serializable>> fields) throws FrameworkException {
        UOW uow = null;
        List<Transaction> results = new ArrayList<Transaction>();
        try {
            uow = new UOW();
            Criteria transactionCriteria = new Criteria();
            transactionCriteria.setTable(TransactionMeta.getName());

            // add each field in the field map to the query
            for (Map.Entry<String, List<Serializable>> field : fields.entrySet()) {

                // if there is no field or values defined, skip to the next entry
                if ((field.getKey() == null) || field.getKey().isEmpty() || field.getValue().isEmpty()) {
                    continue;
                }

                // if the value list has one entry, create a criteria
                if (field.getValue().size() == 1) {
                    Criteria singleValueCriteria = new Criteria();
                    singleValueCriteria.setTable(TransactionFieldMeta.getName());
                    singleValueCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
                    singleValueCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field.getKey());
                    if (field.getValue().get(0) == null) {
                        singleValueCriteria.addCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                    } else {
                        singleValueCriteria.addCriteria(TransactionFieldMeta.VALUE, field.getValue().get(0));
                    }
                    transactionCriteria.addAggregate(singleValueCriteria);
                    continue;
                }

                // if the value list has multiple entries, create an atomic criteria for each one
                Criteria multiValueCriteria = new AtomicCriteria();
                multiValueCriteria.setTable(TransactionFieldMeta.getName());
                multiValueCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
                multiValueCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field.getKey());
                AtomicCriteria ac = new AtomicCriteria();
                for (Object value : field.getValue()) {

                    // if the value is null, add a relational null criteria
                    // check if this is the first criteria or an 'or' criteria when adding it
                    if (value == null) {
                        if ((ac.getCriteriaEntries() == null) || ac.getCriteriaEntries().isEmpty()) {
                            ac.addCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                        } else {
                            ac.addOrCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                        }
                    } else {
                        if ((ac.getCriteriaEntries() == null) || ac.getCriteriaEntries().isEmpty()) {
                            ac.addCriteria(TransactionFieldMeta.VALUE, value);
                        } else {
                            ac.addOrCriteria(TransactionFieldMeta.VALUE, value);
                        }
                    }
                }
                multiValueCriteria.addAtomic(ac);
                transactionCriteria.addAggregate(multiValueCriteria);
            }

            for (Object result : uow.query(transactionCriteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                results.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets all Transactions that have any of the specified fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param fields the field name/value pairs that a Transaction must have at least one of to ne returned
     * @return a collection of Transactions that contain the specified fields with the input values
     * @throws FrameworkException
     */
    @Override
    public Collection<Transaction> getTransactionsByFieldsOred(HashMap<String, Serializable> fields) throws FrameworkException {
        UOW uow = null;
        List<Transaction> results = new ArrayList<Transaction>();
        try {
            uow = new UOW();
            Criteria transactionCriteria = new Criteria();
            transactionCriteria.setTable(TransactionMeta.getName());

            Criteria transactionFieldCriteria = new Criteria();
            transactionFieldCriteria.setTable(TransactionFieldMeta.getName());
            transactionFieldCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            transactionCriteria.addAggregate(transactionFieldCriteria);

            AtomicCriteria ac = new AtomicCriteria();

            // add each field in the field map to the query
            AtomicCriteria firstCriteria = null;
            for (Map.Entry<String, Serializable> field : fields.entrySet()) {

                // if there is no field defined, skip to the next entry
                if ((field.getKey() == null) || field.getKey().isEmpty()) {
                    continue;
                }

                AtomicCriteria ac1 = new AtomicCriteria();
                ac1.addCriteria(TransactionFieldMeta.FIELD_NAME, field.getKey());
                if (field.getValue() == null) {
                    ac1.addCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                } else {
                    ac1.addCriteria(TransactionFieldMeta.VALUE, field.getValue());
                }

                if (firstCriteria != null) {
                    firstCriteria.addOrAtomic(ac1);
                } else {
                    firstCriteria = ac1;
                    ac.addAtomic(firstCriteria);
                }
            }
            transactionFieldCriteria.addAtomic(ac);

            for (Object result : uow.query(transactionCriteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                results.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets all inbound Transactions that have the specified type, subType and fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param type    the type of Transactions to return
     * @param subType the subType of Transactions to return
     * @param fields  the field name/value pairs
     * @return a collection of inbound Transactions of the input type and subType that contain the specified
     *         fields with the input values
     * @throws FrameworkException
     */
    @Override
    public Collection<Transaction> getInboundByTypeSubTypeAndFields(String type, String subType,
                                                                    HashMap<String, String> fields) throws FrameworkException {
        UOW uow = null;
        Collection<Transaction> transactions = new ArrayList<Transaction>();
        try {
            uow = new UOW();
            Criteria transactionCriteria = new Criteria();

            // query for inbound Transaction with the input type and subType
            transactionCriteria.setTable(TransactionMeta.getName());
            transactionCriteria.addCriteria(TransactionMeta.TYPE, type);
            transactionCriteria.addCriteria(TransactionMeta.SUB_TYPE, subType);
            transactionCriteria.addCriteria(TransactionMeta.DIRECTION, Transaction.Direction.IN.name());

            // add each field in the field map to the query
            for (Map.Entry<String, String> field : fields.entrySet()) {
                if ((field.getKey() == null) || field.getKey().isEmpty()) {
                    continue;
                }
                Criteria fieldCriteria = new Criteria();
                fieldCriteria.setTable(TransactionFieldMeta.getName());
                fieldCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
                fieldCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, field.getKey());
                fieldCriteria.addCriteria(TransactionFieldMeta.VALUE, field.getValue());
                transactionCriteria.addAggregate(fieldCriteria);
            }

            for (Object result : uow.query(transactionCriteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                transactions.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactions;
    }

    /**
     * Get a TansactionField given its name and value.
     *
     * @param fieldName  the name of the field to return
     * @param fieldValue the value of the field to return
     * @return the TransactionField with the input fieldName and value
     * @throws FrameworkException
     */
    @Override
    public TransactionField getFieldByNameValue(String fieldName, String fieldValue) throws FrameworkException {
        UOW uow = null;
        TransactionField transactionField = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionFieldMeta.getName());
            AtomicCriteria ac = new AtomicCriteria();
            ac.addCriteria(TransactionFieldMeta.FIELD_NAME, fieldName);
            criteria.addAtomic(ac);
            criteria.addCriteria(TransactionFieldMeta.VALUE, fieldValue);
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof TransactionField)) {
                    continue;
                }
                transactionField = (TransactionField) result;
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionField;
    }

    /**
     * Gets the Transaction with the input ID
     *
     * @param id the ID of the Transaction to get
     * @return the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public Transaction getTransaction(String id) throws FrameworkException {
        UOW uow = null;
        Transaction transaction = null;
        try {
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(TransactionMeta.getName());
            c.addCriteria(TransactionMeta.ID, id);
            Iterator i = uow.query(c).iterator();
            if (i.hasNext()) {
                transaction = (Transaction) i.next();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transaction;
    }

    /**
     * Saves the input Transaction
     *
     * @param uow         the Unit of Work this save should occur in
     * @param transaction the Transaction to save
     * @return the new version of the saved Transaction
     * @throws FrameworkException
     */
    @Override
    public Transaction save(UOW uow, Transaction transaction) throws FrameworkException {
        if (transaction.isDatabaseOccurence()) {
            transaction.setUOW(uow);
            uow.update(transaction);
        } else {
            uow.add(transaction);
        }
        return transaction;
    }

    /**
     * Saves the input TransactionPayload
     *
     * @param uow                the Unit of Work this save should occur in
     * @param transactionPayload the TransactionPayload to save
     * @return the new version of the saved TransactionPayload
     * @throws FrameworkException
     */
    @Override
    public TransactionPayload save(UOW uow, TransactionPayload transactionPayload) throws FrameworkException {
        if (transactionPayload.isDatabaseOccurence()) {
            transactionPayload.setUOW(uow);
            uow.update(transactionPayload);
        } else {
            uow.add(transactionPayload);
        }
        return transactionPayload;
    }

    /**
     * Saves the input TransactionField
     *
     * @param uow              the Unit of Work this save should occur in
     * @param transactionField the TransactionField to save
     * @return the new version of the saved TransactionField
     * @throws FrameworkException
     */
    @Override
    public TransactionField save(UOW uow, TransactionField transactionField) throws FrameworkException {
        if (transactionField.isDatabaseOccurence()) {
            transactionField.setUOW(uow);
            uow.update(transactionField);
        } else {
            uow.add(transactionField);
        }
        return transactionField;
    }

    /**
     * Saves the input TransactionDependency
     *
     * @param uow                   the Unit of Work this save should occur in
     * @param transactionDependency the TransactionDependency to save
     * @return the new version of the saved TransactionDependency
     * @throws FrameworkException
     */
    @Override
    public TransactionDependency save(UOW uow, TransactionDependency transactionDependency) throws FrameworkException {
        if (transactionDependency.isDatabaseOccurence()) {
            transactionDependency.setUOW(uow);
            uow.update(transactionDependency);
        } else {
            uow.add(transactionDependency);
        }
        return transactionDependency;
    }

    /**
     * Deletes the input Transaction
     *
     * @param uow         the Unit of Work this delete should occur in
     * @param transaction the Transaction to delete
     * @throws FrameworkException
     */
    @Override
    public void delete(UOW uow, Transaction transaction) throws FrameworkException {
        transaction.setUOW(uow);
        uow.delete(transaction);
    }

    /**
     * Deletes the input Transaction Dependency
     *
     * @param uow                   the Unit of Work this delete should occur in
     * @param transactionDependency the TransactionDependency to delete
     * @throws FrameworkException
     */
    @Override
    public void delete(UOW uow, TransactionDependency transactionDependency) throws FrameworkException {
        transactionDependency.setUOW(uow);
        uow.delete(transactionDependency);
    }

    /**
     * Gets an ordered list of Transaction that match the input type, subType and fields.
     * If more than one value is defined for a field, that field will be checked against all values to see if it
     * matched any of them (they will be OR-ed).
     *
     * @param type    the type of Transactions to return
     * @param subType the subType of Transactions to return
     * @param fields  the fields to check the Transaction for, if multiple values are defined for a field, the
     *                field will be checked to see if it matches any of the values (they will be OR-ed)
     * @param orderBy the fields to sort the return list by, each field has a boolean value that determines the
     *                direction.  True for ascending and false for descending.
     * @return an ordered list of Transactions that match the input type, subType and fields
     * @throws FrameworkException
     */
    @Override
    public List<Transaction> getTransactionsByTypeSubTypeFieldsOrderBy(String type, String subType,
                                                                       HashMap<String, List<String>> fields,
                                                                       LinkedHashMap<String, Boolean> orderBy) throws FrameworkException {
        List<Transaction> results = new ArrayList<Transaction>();
        UOW uow = null;
        try {
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(TransactionMeta.getName());

            // query on type and subType
            StringCriteriaField typeField = StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, type, null);
            StringCriteriaField subTypeField = StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, subType, null);
            FinderTx.addCriteria(typeField, TransactionMeta.TYPE, c);
            FinderTx.addCriteria(subTypeField, TransactionMeta.SUB_TYPE, c);

            // add the fields
            for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
                Criteria joinCriteria = new Criteria();
                joinCriteria.setTable(TransactionFieldMeta.getName());
                joinCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);

                // the key is the field name
                joinCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, entry.getKey());

                // the values are OR-ed together
                AtomicCriteria ac = new AtomicCriteria();
                for (String value : entry.getValue()) {
                    if (value == null) {
                        if ((ac.getCriteriaEntries() == null) || ac.getCriteriaEntries().isEmpty()) {
                            ac.addCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                        } else {
                            ac.addOrCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
                        }
                    } else {
                        if ((ac.getCriteriaEntries() == null) || ac.getCriteriaEntries().isEmpty()) {
                            ac.addCriteria(TransactionFieldMeta.VALUE, value);
                        } else {
                            ac.addOrCriteria(TransactionFieldMeta.VALUE, value);
                        }
                    }
                }
                joinCriteria.addAtomic(ac);
                c.addAggregate(joinCriteria);
            }

            // add the orderBy
            for (Map.Entry<String, Boolean> entry : orderBy.entrySet()) {
                int sort = Criteria.ORDER_BY_ASC;
                if (entry.getValue() != null && !entry.getValue()) {
                    sort = Criteria.ORDER_BY_DESC;
                }
                c.addOrderBy(entry.getKey(), sort);
            }

            // add the query results to the return list
            for (Object o : uow.query(c)) {
                results.add((Transaction) o);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets all Transactions that have the specified field with the input value.
     *
     * @param fieldName the field to check for the input value
     * @param value     the value to check against the field
     * @return a collection of Transactions that contain the specified field with the input value
     * @throws FrameworkException
     */
    @Override
    public Collection<Transaction> getTransactionsByField(String fieldName, String value) throws FrameworkException {
        UOW uow = null;
        Collection<Transaction> results = new ArrayList<Transaction>();
        try {
            uow = new UOW();

            // set up a Transaction criteria
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());

            // join to the TransactionField table
            Criteria joinCriteria = new Criteria();
            joinCriteria.setTable(TransactionFieldMeta.getName());
            joinCriteria.addInnerCriteria(TransactionFieldMeta.TRANSACTION_ID, TransactionMeta.ID);
            joinCriteria.addCriteria(TransactionFieldMeta.FIELD_NAME, fieldName);

            // either query for a value or relational null based on the input
            if (value != null) {
                joinCriteria.addCriteria(TransactionFieldMeta.VALUE, value);
            } else {
                joinCriteria.addCriteria(TransactionFieldMeta.VALUE, Criteria.RELATIONAL_IS_NULL);
            }

            criteria.addAggregate(joinCriteria);
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                results.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets the count per status of all open Transaction with the input type
     *
     * @param type the type to filter on
     * @return the count of all open Transaction with the input type
     * @throws FrameworkException
     */
    @Override
    public TransactionStatusCount getTransactionCountPerStatusByType(String type) throws FrameworkException {
        UOW uow = null;
        TransactionStatusCount result = new TransactionStatusCount();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            criteria.addCriteria(TransactionMeta.TYPE, type);
            criteria.addGroupBy(TransactionMeta.STATUS, TransactionMeta.STATUS);
            long openCount = 0L, successCount = 0L, errorCount = 0L, holdCount = 0L, inProcessCount = 0L, interruptedCount = 0L;
            for (Object queryResult : uow.query(criteria)) {
                if (!(queryResult instanceof Map)) {
                    continue;
                }
                Map row = (Map) queryResult;
                String status = (String) row.get(TransactionMeta.STATUS);
                if (status != null) {
                    Transaction.Status enumeratedStatus = Transaction.Status.valueOf(status);
                    Number count = (Number) row.get(Criteria.ID_FUNCTION_COUNT);
                    switch (enumeratedStatus) {
                        case O:
                            openCount += count.longValue();
                            break;
                        case S:
                            successCount += count.longValue();
                            break;
                        case E:
                            errorCount += count.longValue();
                            break;
                        case H:
                            holdCount += count.longValue();
                            break;
                        case I:
                            inProcessCount += count.longValue();
                            break;
                        case INT:
                            interruptedCount += count.longValue();
                            break;
                    }
                }
            }
            result.setOpenCount(openCount);
            result.setSuccessCount(successCount);
            result.setErrorCount(errorCount);
            result.setHoldCount(holdCount);
            result.setInProcessCount(inProcessCount);
            result.setInterruptedCount(interruptedCount);
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return result;
    }

    /**
     * Returns a Map of TransactionStatusCount(s) for all Transaction in the types array
     *
     * @param types The list of types to get the transaction status counts for
     * @return a Map of TransactionStatusCount(s) for all Transaction in the types array
     * @throws FrameworkException
     */
    @Override
    public Map<String, TransactionStatusCount> getTransactionCountPerStatusForTypes(String[] types)
            throws FrameworkException {

        Map<String, TransactionStatusCount> map = new HashMap<>();

        if (types != null) {
            for (String type : types) {
                map.put(type, getTransactionCountPerStatusByType(type));
            }
        }

        return map;
    }

    /**
     * Gets the timestamp that a Transaction of the specified type was most recently put into the error state
     *
     * @param type the type of Transaction
     * @return the timestamp that a Transaction of the specified type was most recently put into the error state
     * @throws FrameworkException
     */
    @Override
    public DateTime getLastErrorTimeByType(String type) throws FrameworkException {
        UOW uow = null;
        DateTime result = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());
            criteria.addFunction(Criteria.FUNCTION_MAX, TransactionMeta.LAST_CHANGED_ON, TransactionMeta.LAST_CHANGED_ON);
            criteria.addCriteria(TransactionMeta.TYPE, type);
            criteria.addCriteria(TransactionMeta.STATUS, Transaction.Status.E.toString());
            for (Object queryResult : uow.query(criteria)) {
                if (!(queryResult instanceof Map)) {
                    continue;
                }
                Map row = (Map) queryResult;
                result = (DateTime) row.get(TransactionMeta.LAST_CHANGED_ON);
                break;
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return result;
    }

    /**
     * Gets the TransactionPayload of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionPayload of
     * @return the TransactionPayload of the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public TransactionPayload getTransactionPayloadByTransactionId(String transactionId) throws FrameworkException {
        UOW uow = null;
        TransactionPayload result = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionPayloadMeta.getName());
            criteria.addCriteria(TransactionPayloadMeta.ID, transactionId);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext()) {
                result = (TransactionPayload) itr.next();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return result;
    }

    /**
     * Gets the TransactionPayload with the input ID.
     *
     * @param transactionPayloadId the ID of the Transaction to return the TransactionPayload of
     * @return the TransactionPayload of the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public TransactionPayload getTransactionPayload(String transactionPayloadId) throws FrameworkException {
        UOW uow = null;
        TransactionPayload payload = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionPayloadMeta.getName());
            criteria.addCriteria(TransactionPayloadMeta.ID, transactionPayloadId);
            for (Object result : uow.query(criteria)) {
                if (result instanceof TransactionPayload) {
                    payload = (TransactionPayload) result;
                    break;
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return payload;
    }

    /**
     * Gets the TransactionField with the input Transaction ID and field name.
     *
     * @param transactionId the ID of the Transaction the TransactionField is part of
     * @param fieldName     the field name of the TransactionField to return
     * @return the TransactionField with the input Transaction ID and field name
     * @throws FrameworkException
     */
    @Override
    public TransactionField getTransactionField(String transactionId, String fieldName) throws FrameworkException {
        UOW uow = null;
        TransactionField field = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionFieldMeta.getName());
            criteria.addCriteria(TransactionFieldMeta.TRANSACTION_ID, transactionId);
            criteria.addCriteria(TransactionFieldMeta.FIELD_NAME, fieldName);
            for (Object result : uow.query(criteria)) {
                if (result instanceof TransactionField) {
                    field = (TransactionField) result;
                    break;
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return field;
    }

    /**
     * Gets the TransactionField collection of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionFields of
     * @return the TransactionFields of the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionField> getTransactionFields(String transactionId) throws FrameworkException {
        UOW uow = null;
        List<TransactionField> results = new ArrayList<TransactionField>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionFieldMeta.getName());
            criteria.addCriteria(TransactionFieldMeta.TRANSACTION_ID, transactionId);
            for (Object result : uow.query(criteria)) {
                results.add((TransactionField) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets a TransactionDependency with the input transaction ID and depends on ID
     *
     * @param transactionId the ID of the Transaction the dependency is for
     * @param dependsOnId   the ID of the Transaction that is being depended on
     * @return the TransactionDependency with the input transaction ID and depends on ID
     * @throws FrameworkException
     */
    @Override
    public TransactionDependency getTransactionDependency(String transactionId, String dependsOnId) throws FrameworkException {
        UOW uow = null;
        TransactionDependency dependency = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencyMeta.getName());
            criteria.addCriteria(TransactionDependencyMeta.TRANSACTION_ID, transactionId);
            criteria.addCriteria(TransactionDependencyMeta.DEPENDS_ON_ID, dependsOnId);
            for (Object result : uow.query(criteria)) {
                if (result instanceof TransactionDependency) {
                    dependency = (TransactionDependency) result;
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return dependency;
    }

    /**
     * Gets the TransactionDependency collection of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionDependencies of
     * @return the TransactionDependencies of the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionDependency> getTransactionDependencies(String transactionId) throws FrameworkException {
        UOW uow = null;
        List<TransactionDependency> results = new ArrayList<TransactionDependency>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencyMeta.getName());
            criteria.addCriteria(TransactionDependencyMeta.TRANSACTION_ID, transactionId);
            for (Object result : uow.query(criteria)) {
                results.add((TransactionDependency) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets the count of TransactionDependencies of the input Transaction that are in the open state
     *
     * @param transactionId the ID of the Transaction to get the open TransactionDependency count for
     * @return the count of open TransactionDependencies for the input Transaction
     * @throws FrameworkException
     */
    @Override
    public long getOpenTransactionDependencyCount(String transactionId) throws FrameworkException {
        UOW uow = null;
        long dependencyCount = 0;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencyMeta.getName());
            criteria.addCriteria(TransactionDependencyMeta.TRANSACTION_ID, transactionId);
            criteria.addCriteria(TransactionDependencyMeta.STATUS, TransactionDependency.Status.O.toString());
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            Number count = (Number) ((Map) uow.query(criteria).iterator().next()).get(Criteria.ID_FUNCTION_COUNT);
            if (count != null) {
                dependencyCount = count.longValue();
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return dependencyCount;
    }

    /**
     * Gets all TransactionDependencies in the open state that depend on the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction
     * @return all TransactionDependencies in the open state that depend on the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionDependency> getOpenDependenciesByDependsOnId(String transactionId) throws FrameworkException {
        UOW uow = null;
        List<TransactionDependency> results = new ArrayList<TransactionDependency>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencyMeta.getName());
            criteria.addCriteria(TransactionDependencyMeta.DEPENDS_ON_ID, transactionId);
            criteria.addCriteria(TransactionDependencyMeta.STATUS, TransactionDependency.Status.O.toString());
            for (Object result : uow.query(criteria)) {
                results.add((TransactionDependency) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets all TransactionDependencies in any state that depend on the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction
     * @return all TransactionDependencies in any state that depend on the Transaction with the input ID
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionDependency> getDependenciesByDependsOnId(String transactionId) throws FrameworkException {
        UOW uow = null;
        List<TransactionDependency> results = new ArrayList<TransactionDependency>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencyMeta.getName());
            criteria.addCriteria(TransactionDependencyMeta.DEPENDS_ON_ID, transactionId);
            for (Object result : uow.query(criteria)) {
                results.add((TransactionDependency) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Gets all Transactions in the open or InProgress state that were last changed before the input time.
     *
     * @param input the time to compare Open and InProgress Transactions lastChangedTime to
     * @return all Transactions in the open or InProgress state that were last changed before the input time
     * @throws FrameworkException
     */
    @Override
    public Collection<Transaction> getOpenInProgressLastChangedBefore(DateTime input) throws FrameworkException {
        UOW uow = null;
        Collection<Transaction> results = new ArrayList<Transaction>();
        try {
            uow = new UOW();

            // get all transactions in the "O" or "I" state that are older than the configured age limit
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionMeta.getName());

            // status can equal "O" or "I"
            AtomicCriteria statusCriteria = new AtomicCriteria();
            statusCriteria.addCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_EQUALS, Transaction.Status.O.toString());
            statusCriteria.addOrCriteria(TransactionMeta.STATUS, Criteria.RELATIONAL_EQUALS, Transaction.Status.I.toString());

            // last changed on should be older than (less than) the retryTime
            AtomicCriteria ageCriteria = new AtomicCriteria();
            ageCriteria.addCriteria(TransactionMeta.LAST_CHANGED_ON, Criteria.RELATIONAL_SMALLER_THAN, input);

            // add the two criteria to one query
            criteria.addAtomic(statusCriteria);
            criteria.addAtomic(ageCriteria);
            Collection transactions = uow.query(criteria);

            // create a list of transactions from the results
            for (Object result : transactions) {
                if (!(result instanceof Transaction)) {
                    continue;
                }
                results.add((Transaction) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Takes a Transaction ID and returns the TransactionSweeperView if the Transaction is in the Hold state but has no
     * TransactionDependency in the Open state.  The TransactionSweeperView is a subset of the Transaction model.
     *
     * @param transactionId the ID of the Transaction in question
     * @return a TransactionSweeperView created from the Transaction with the input ID if that Transaction is in
     *         the Hold state but has no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    @Override
    public TransactionSweeperView getSweeperViewIfOnHoldNoOpenDependency(String transactionId) throws FrameworkException {
        UOW uow = null;
        TransactionSweeperView transactionSweeperView = null;
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionSweeperViewMeta.getName());
            criteria.addCriteria(TransactionSweeperViewMeta.ID, transactionId);
            for (Object result : uow.query(criteria)) {
                if (result instanceof TransactionSweeperView) {
                    transactionSweeperView = (TransactionSweeperView) result;
                    break;
                }
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return transactionSweeperView;
    }

    /**
     * Returns all of the TransactionSweeperViews of the Transactions in the Hold state that have no
     * TransactionDependency in the Open state.  The TransactionSweeperView is a subset of the Transaction model.
     *
     * @return all TransactionSweeperViews created from the Transactions in the Hold state that have
     *         no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionSweeperView> getSweeperViewsIfOnHoldNoOpenDependency() throws FrameworkException {
        UOW uow = null;
        Collection<TransactionSweeperView> results = new ArrayList<TransactionSweeperView>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionSweeperViewMeta.getName());

            // create a list of transactionSweeperViews from the results
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof TransactionSweeperView)) {
                    continue;
                }
                results.add((TransactionSweeperView) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Returns all of the TransactionDependencySweeperViews of the TransactionDependencies in the Open state that have
     * no dependsOn Transaction or have a dependsOn Transaction in the S state.
     * The TransactionDependencySweeperView is a subset of the TransactionDependency model.
     *
     * @return all TransactionSweeperViews created from the Transactions in the Hold state that have
     *         no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    @Override
    public Collection<TransactionDependencySweeperView> getDependencySweeperViewsIfOpenNoDependsOnTx() throws FrameworkException {
        UOW uow = null;
        Collection<TransactionDependencySweeperView> results = new ArrayList<TransactionDependencySweeperView>();
        try {
            uow = new UOW();
            Criteria criteria = new Criteria();
            criteria.setTable(TransactionDependencySweeperViewMeta.getName());

            // create a list of transactionDependencySweeperViews from the results
            for (Object result : uow.query(criteria)) {
                if (!(result instanceof TransactionDependencySweeperView)) {
                    continue;
                }
                results.add((TransactionDependencySweeperView) result);
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
        return results;
    }

    /**
     * Create an array of {@link org.jaffa.transaction.domain.TransactionField} based on the fields of the {@link org
     * .jaffa.transaction.domain.Transaction}
     *
     * @param transaction the transaction to create fields for
     * @param dataBean    the internal object of the transaction payload
     * @return an array of {@link org.jaffa.transaction.domain.TransactionField}
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    private Collection<TransactionField> createTransactionFields(Transaction transaction, Object dataBean)
            throws FrameworkException, ValidationException, ApplicationExceptions {
        Collection<TransactionField> transactionFields = new LinkedList<TransactionField>();

        // Create a TransactionField object for each header element as defined in the configuration file
        final TransactionInfo transactionInfo = transaction.getTransactionInfo();
        if ((transactionInfo.getHeader() != null) && (transactionInfo.getHeader().getParam() != null)) {
            for (Param param : transactionInfo.getHeader().getParam()) {

                // get the value of the header parameter
                String value = TransactionEngine.obtainParamValue(param, dataBean);

                // create a transaction field and set its ID
                TransactionField transactionField = new TransactionField();
                transactionField.setTransactionId(transaction.getId());

                // set the name and value of the transaction field, then add it to the list of all transaction fields
                transactionField.setFieldName(param.getName());
                transactionField.setValue(value);
                transactionFields.add(transactionField);
            }
        }
        return transactionFields;
    }
}
