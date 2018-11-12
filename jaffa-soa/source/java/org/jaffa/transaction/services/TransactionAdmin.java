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

package org.jaffa.transaction.services;

import org.apache.log4j.Logger;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.common.IMessageHandler;
import org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException;
import org.jaffa.modules.messaging.services.JmsBrowser;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.IllegalPersistentStateRuntimeException;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.exceptions.QueryFailedException;
import org.jaffa.qm.apis.IQueueAdmin;
import org.jaffa.qm.apis.data.MessageAdminResponse;
import org.jaffa.qm.apis.data.MessageCriteria;
import org.jaffa.qm.apis.data.MessageDependency;
import org.jaffa.qm.apis.data.MessageField;
import org.jaffa.qm.apis.data.MessageFieldCriteria;
import org.jaffa.qm.apis.data.MessageFieldMetaData;
import org.jaffa.qm.apis.data.MessageGraph;
import org.jaffa.qm.apis.data.MessageQueryResponse;
import org.jaffa.qm.apis.data.QueueAdminResponse;
import org.jaffa.qm.apis.data.QueueCriteria;
import org.jaffa.qm.apis.data.QueueGraph;
import org.jaffa.qm.apis.data.QueueMetaData;
import org.jaffa.qm.apis.data.QueueQueryResponse;
import org.jaffa.qm.util.PropertyFilter;
import org.jaffa.soa.graph.ServiceError;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionDependency;
import org.jaffa.transaction.domain.TransactionField;
import org.jaffa.transaction.domain.TransactionPayload;
import org.jaffa.transaction.domain.TransactionStatusCount;
import org.jaffa.transaction.services.configdomain.DisplayParam;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.MessageHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This IQueueAdmin implementation is based on the JMS Queues.
 *
 * @author GautamJ
 */
public class TransactionAdmin implements IQueueAdmin {

    private static final Logger log = Logger.getLogger(TransactionAdmin.class);
    public static final String QUEUE_SYSTEM_ID = "Transaction";
    public static final MessageGraph.Status[] SUPPORTED_MESSAGE_STATUS = MessageGraph.Status.values();
    public static final Boolean SUPPORTS_TECHNICAL_FIELDS = Boolean.FALSE;
    public static final Boolean SUPPORTS_BUSINESS_EVENT_LOGS = Boolean.TRUE;
    public static final Boolean SUPPORTS_DEPENDENCIES = Boolean.TRUE;
    private TransactionMessageDAO transactionDAO = TransactionMessageDAOFactory.getTransactionMessageDAO();

    public QueueQueryResponse queueQuery(QueueCriteria criteria) {
        if (log.isDebugEnabled()) {
            log.debug("Input to queueQuery: " + criteria);
        }
        QueueQueryResponse output = new QueueQueryResponse();
        try {
            //Match the queueSystemId
            if (FinderTx.match(QUEUE_SYSTEM_ID, criteria.getQueueSystemId())) {
                String[] types = TransactionEngine.getInstance().getAccessibleTypeNames();
                if (types != null && types.length > 0) {
                    PropertyFilter pf = PropertyFilter.getInstance(QueueGraph.class, criteria.getResultGraphRules());
                    Collection<QueueGraph> graphs = new LinkedList<QueueGraph>();
                    Map<String, TransactionStatusCount> statusCountMap = transactionDAO.getTransactionCountPerStatusForTypes(types);
                    for (String type : types) {
                        if (FinderTx.match(type, criteria.getType())) {
                            QueueGraph graph = new QueueGraph();
                            TransactionStatusCount statusCount = statusCountMap.get(type);

                            //Compute message count only if required by criteria or if it is included in the property-filter
                            if ((statusCount != null) &&
                                    (criteria.getOpenCount() != null || pf.isFieldIncluded("openCount") ||
                                     criteria.getSuccessCount() != null || pf.isFieldIncluded("successCount") ||
                                     criteria.getErrorCount() != null || pf.isFieldIncluded("errorCount") ||
                                     criteria.getHoldCount() != null || pf.isFieldIncluded("holdCount") ||
                                     criteria.getInProcessCount() != null || pf.isFieldIncluded("inProcessCount") ||
                                     criteria.getInterruptedCount() != null || pf.isFieldIncluded("interruptedCount") ||
                                              pf.isFieldIncluded("lastErroredOn"))) {

                                Long openCount = statusCount.getOpenCount();
                                Long successCount = statusCount.getSuccessCount();
                                Long errorCount = statusCount.getErrorCount();
                                Long holdCount = statusCount.getHoldCount();
                                Long inProcessCount = statusCount.getInProcessCount();
                                Long interruptedCount = statusCount.getInterruptedCount();

                                if (pf.isFieldIncluded("lastErroredOn") && statusCount.getTotalCount() > 0L) {
                                    graph.setLastErroredOn(transactionDAO.getLastErrorTimeByType(type));
                                }

                                if (!FinderTx.match(openCount, criteria.getOpenCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("openCount")) {
                                    graph.setOpenCount(openCount);
                                }

                                if (!FinderTx.match(successCount, criteria.getSuccessCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("successCount")) {
                                    graph.setSuccessCount(successCount);
                                }

                                if (!FinderTx.match(errorCount, criteria.getErrorCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("errorCount")) {
                                    graph.setErrorCount(errorCount);
                                }

                                if (!FinderTx.match(holdCount, criteria.getHoldCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("holdCount")) {
                                    graph.setHoldCount(holdCount);
                                }

                                if (!FinderTx.match(inProcessCount, criteria.getInProcessCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("inProcessCount")) {
                                    graph.setInProcessCount(inProcessCount);
                                }

                                if (!FinderTx.match(interruptedCount, criteria.getInterruptedCount())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("interruptedCount")) {
                                    graph.setInterruptedCount(interruptedCount);
                                }
                            }

                            //Apply the status criteria
                            if (criteria.getStatus() != null || pf.isFieldIncluded("status")) {
                                QueueGraph.Status status = QueueGraph.Status.ACTIVE;
                                if (!FinderTx.match(status.toString(), criteria.getStatus())) {
                                    continue;
                                }
                                if (pf.isFieldIncluded("status")) {
                                    graph.setStatus(status);
                                }
                            }

                            //Stamp the remaining properties, if included in the property-filter
                            graph.setQueueMetaData(createQueueMetaData(type, pf));
                            if (pf.isFieldIncluded("type")) {
                                graph.setType(type);
                            }
                            if (pf.isFieldIncluded("hasAdminAccess")) {
                                graph.setHasAdminAccess(TransactionEngine.getInstance().hasAdminAccess(type));
                            }

                            graphs.add(graph);
                        }
                    }
                    if (graphs.size() > 0) {
                        output.setGraphs(graphs.toArray(new QueueGraph[graphs.size()]));
                    }
                }
            }
        } catch (Exception e) {
            // add errors to the response
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Error in queueQuery execution", appExps);
                }
                output.setErrors(ServiceError.generate(appExps));
            } else {
                log.error("Internal Error in queueQuery execution", e);
                output.setErrors(ServiceError.generate(e));
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Output from queueQuery: " + output);
        }
        return output;
    }

    public MessageQueryResponse messageQuery(MessageCriteria criteria) {
        if (log.isDebugEnabled()) {
            log.debug("Input to messageQuery: " + criteria);
        }
        MessageQueryResponse output = new MessageQueryResponse();
        try {
            //Match the queueSystemId as well apply checks for unsupported criteria
            if (FinderTx.match(QUEUE_SYSTEM_ID, criteria.getQueueSystemId()) && FinderTx.match(null, criteria.getPriority())) {
                PropertyFilter pf = PropertyFilter.getInstance(MessageGraph.class, criteria.getResultGraphRules());
                Collection<MessageGraph> graphs = new LinkedList<MessageGraph>();
                String type = getMessageCriteriaType(criteria);
                String subType = getMessageCriteriaSubType(criteria);

                // we will be getting an ordered list
                LinkedHashMap<String, Boolean> orderBy = getOrderByFields(criteria);

                // the fields the transactions must have
                HashMap<String, List<String>> fields = new HashMap<String, List<String>>();
                if (criteria.getApplicationFields() != null) {
                    for (MessageFieldCriteria messageFieldCriteria : criteria.getApplicationFields()) {
                        List<String> fieldValues = getFieldCriteriaValues(messageFieldCriteria);
                        fields.put(messageFieldCriteria.getName(), fieldValues);
                    }
                }

                // get an ordered list of all transactions of the specified type and subType
                List<Transaction> allTransactions = new ArrayList<Transaction>();
                allTransactions.addAll(transactionDAO.getTransactionsByTypeSubTypeFieldsOrderBy(type, subType, fields, orderBy));

                // filter the list down based on other criteria
                List<Transaction> filteredTransactions = new ArrayList<Transaction>();
                filteredTransactions.addAll(allTransactions);
                for (Transaction transaction : allTransactions) {
                    if (doesTransactionFailFilterCheck(transaction, criteria)) {
                        filteredTransactions.remove(transaction);
                    }
                }

                // now we have a filtered and ordered list of Transactions, check if we only want a page of the results
                int firstIndex = criteria.getObjectStart() == null ? 0 : criteria.getObjectStart();
                int resultsCount = criteria.getObjectLimit() == null ? 0 : criteria.getObjectLimit();
                int lastIndex = firstIndex + resultsCount;
                if (lastIndex > filteredTransactions.size()) {
                    lastIndex = filteredTransactions.size();
                }
                List<Transaction> filteredPagedTransactions = new ArrayList<Transaction>();
                if ((lastIndex > firstIndex) && ((firstIndex > 0) || (lastIndex > 0))) {
                    filteredPagedTransactions.addAll(filteredTransactions.subList(firstIndex, lastIndex));
                } else {
                    filteredPagedTransactions.addAll(filteredTransactions);
                }

                // create a graph from the filtered and paged list of transactions
                for (Transaction transaction : filteredPagedTransactions) {
                    MessageGraph graph = createMessageGraph(transaction, pf);
                    graphs.add(graph);
                }
                if (graphs.size() > 0) {
                    output.setGraphs(graphs.toArray(new MessageGraph[graphs.size()]));
                }

                // set the total count of rows being output
                int rowsCount = graphs.size();
                if ((firstIndex > 0) || (resultsCount > 0)) {
                    if ((firstIndex <= 0) && (rowsCount < resultsCount)) {
                        output.setTotalRecords(rowsCount);
                    } else {
                        output.setTotalRecords(filteredTransactions.size());
                    }
                } else {
                    output.setTotalRecords(rowsCount);
                }
            }
        } catch (Exception e) {
            // add errors to the response
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Error in messageQuery execution", appExps);
                }
                output.setErrors(ServiceError.generate(appExps));
            } else {
                log.error("Internal Error in messageQuery execution", e);
                output.setErrors(ServiceError.generate(e));
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Output from messageQuery: " + output);
        }
        return output;
    }

    /**
     * NOTE: Each graph is expected to contain type. It may also contain the optional queueMetaData.queueSystemId, which may help optimize the process.
     */
    public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs) {
        if (log.isDebugEnabled()) {
            log.debug("Input to toggleQueueStatus: " + Arrays.toString(graphs));
        }
        Collection<QueueAdminResponse> output = null;
        String[] types = TransactionEngine.getInstance().getAccessibleTypeNames();
        Arrays.sort(types);
        for (QueueGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                if (types != null && Arrays.binarySearch(types, graph.getType()) >= 0) {
                    try {
                        if (!TransactionEngine.getInstance().hasAdminAccess(graph.getType())) {
                            throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{graph.getType()}));
                        }
                    } catch (Exception e) {
                        QueueAdminResponse response = new QueueAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled()) {
                                log.debug("Error while toggling status of queue " + graph.getType(), appExps);
                            }
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while toggling status of queue " + graph.getType(), e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null) {
                            output = new LinkedList<QueueAdminResponse>();
                        }
                        output.add(response);
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Status of queue " + graph.getType() + " will not be toggled by this implementation, since the queue does not belong to this system");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Status of queue " + graph.getType() + " will not be toggled by this implementation, since the input queueSystemId does not match this system");
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Output from toggleQueueStatus: " + output);
        }
        return output != null && output.size() > 0 ? output.toArray(new QueueAdminResponse[output.size()]) : null;
    }


    /////////////////////////////////// Add in intercept for delete

    /**
     * NOTE: Each graph is expected to contain messageId. It may also contain the optional queueMetaData.queueSystemId and type, which may help optimize the process.
     */
    public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs) {
        if (log.isDebugEnabled()) {
            log.debug("Input to deleteMessage: " + Arrays.toString(graphs));
        }
        Collection<MessageAdminResponse> output = null;
        String[] types = TransactionEngine.getInstance().getAccessibleTypeNames();
        Arrays.sort(types);
        for (MessageGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                if (graph.getType() == null || (types != null && Arrays.binarySearch(types, graph.getType()) >= 0)) {
                    UOW uow = null;
                    try {
                        String graphType = graph.getType();
                        String messageId = graph.getMessageId();
                        if (graphType != null && !TransactionEngine.getInstance().hasAdminAccess(graphType)) {
                            throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{graphType}));
                        }

                        uow = new UOW();
                        Transaction transaction = transactionDAO.getTransaction(messageId);
                        if (!TransactionEngine.getInstance().hasAdminAccess(transaction.getType())) {
                            throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{transaction.getType()}));
                        }
                        if (transaction != null) {
                            //Invoke Message Handler class to perform onDelete process
                            invokeHandler(uow, transaction, "onDelete");
                            if (log.isDebugEnabled()) {
                                log.debug("Deleting message " + messageId);
                            }
                            transactionDAO.delete(uow, transaction);
                            uow.commit();
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("Delete cannot be performed since message " + messageId + " not found");
                            }
                        }
                    } catch (Exception e) {
                        MessageAdminResponse response = new MessageAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled()) {
                                log.debug("Error while deleting Message " + graph, appExps);
                            }
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while deleting Message " + graph, e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null) {
                            output = new LinkedList<MessageAdminResponse>();
                        }
                        output.add(response);
                    } finally {
                        if (uow != null) {
                            try {
                                uow.rollback();
                            } catch (Exception ignore) {
                            }
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Message " + graph + " will not be deleted by this implementation, since the queue does not belong to this implementation");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Message " + graph + " will not be deleted by this implementation, since the input queueSystemId does not match this system");
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Output from deleteMessage: " + output);
        }
        return output != null && output.size() > 0 ? output.toArray(new MessageAdminResponse[output.size()]) : null;
    }

    /**
     * NOTE: Each graph is expected to contain messageId. It may also contain the optional queueMetaData.queueSystemId and type, which may help optimize the process.
     */
    public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs) {
        if (log.isDebugEnabled()) {
            log.debug("Input to resubmitMessage: " + Arrays.toString(graphs));
        }
        Collection<MessageAdminResponse> output = null;
        String[] types = TransactionEngine.getInstance().getAccessibleTypeNames();
        Arrays.sort(types);
        for (MessageGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                if (graph.getType() == null || (types != null && Arrays.binarySearch(types, graph.getType()) >= 0)) {
                    UOW uow = null;
                    try {
                        String graphType = graph.getType();
                        String messageId = graph.getMessageId();
                        if (graphType != null && !TransactionEngine.getInstance().hasAdminAccess(graphType)) {
                            throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{graphType}));
                        }

                        uow = new UOW();
                        Transaction transaction = transactionDAO.getTransaction(messageId);
                        if (transaction != null) {
                            if (!TransactionEngine.getInstance().hasAdminAccess(transaction.getType())) {
                                throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{transaction.getType()}));
                            }
                            if (Transaction.Status.E.toString().equals(transaction.getStatus())) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Resubmitting ERROR message " + messageId);
                                }
                                transaction.setStatus(Transaction.Status.O.toString());
                                transaction.setErrorMessage(null);
                                transactionDAO.save(uow, transaction);
                                uow.commit();
                            } else {
                                if (log.isDebugEnabled()) {
                                    log.debug("Resubmission cannot be performed since message " + messageId + " is not in ERROR status anymore");
                                }
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("Resubmission cannot be performed since message " + messageId + " not found");
                            }
                        }
                    } catch (Exception e) {
                        MessageAdminResponse response = new MessageAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled()) {
                                log.debug("Error while resubmitting Message " + graph, appExps);
                            }
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while resubmitting Message " + graph, e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null) {
                            output = new LinkedList<MessageAdminResponse>();
                        }
                        output.add(response);
                    } finally {
                        if (uow != null) {
                            try {
                                uow.rollback();
                            } catch (Exception ignore) {
                            }
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Message " + graph + " will not be resubmitted by this implementation, since the queue does not belong to this implementation");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Message " + graph + " will not be resubmitted by this implementation, since the input queueSystemId does not match this system");
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Output from resubmitMessage: " + output);
        }
        return output != null && output.size() > 0 ? output.toArray(new MessageAdminResponse[output.size()]) : null;
    }

    /**
     * Check the Transaction fields against the input criteria to see if it fails any of the checks.
     * Return true if anything defined in the criteria does not match the Transaction.
     *
     * @param transaction the Transaction to check the fields of
     * @param criteria    the criteria to check the values against the Transaction
     * @return true if the Transaction fails the criteria check, false otherwise
     */
    private boolean doesTransactionFailFilterCheck(Transaction transaction, MessageCriteria criteria) {
        String messageId = getMessageCriteriaId(criteria);
        if ((messageId != null) && (transaction.getId() != null)) {
            if (!transaction.getId().equals(messageId)) {
                return true;
            }
        }

        String direction = getMessageCriteriaDirection(criteria);
        if ((direction != null) && (transaction.getDirection() != null)) {
            if (!transaction.getDirection().equals(direction)) {
                return true;
            }
        }

        List<String> statusValues = getMessageCriteriaStatus(criteria);
        if ((statusValues != null) && !statusValues.isEmpty()) {
            List<String> convertedValues = new ArrayList<String>();
            for (String value : statusValues) {
                convertedValues.add(messageToTransactionStatus(value).name());
            }
            if ((transaction.getStatus() == null) || !convertedValues.contains(transaction.getStatus())) {
                return true;
            }
        }

        DateTime createdOn = getMessageCriteriaCreatedOn(criteria);
        String operator  = (( null != criteria.getCreatedOn() && criteria.getCreatedOn().getOperator() != null ) ? criteria.getCreatedOn().getOperator() : null);
        if (createdOn != null && transaction.getCreatedOn() != null) {
            DateOnly criteriaCreatedDate = DateTime.toDateOnly(createdOn);
            DateOnly transactionCreatedDate = DateTime.toDateOnly(transaction.getCreatedOn());
            if (operator == null && !transactionCreatedDate.equals(criteriaCreatedDate)) {
                return true;
            } else if (operator != null && DateTimeCriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO.equals(operator)) {
                return (!(transactionCreatedDate.isAfter(criteriaCreatedDate) || transactionCreatedDate.equals(criteriaCreatedDate)));
            } else if (operator != null && DateTimeCriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO.equals(operator)) {
                return (!(transactionCreatedDate.isBefore(criteriaCreatedDate) || transactionCreatedDate.equals(criteriaCreatedDate)));
            }  else if (operator != null && DateTimeCriteriaField.RELATIONAL_BETWEEN.equals(operator) && criteria.getCreatedOn().getValues().length>1){
                    DateOnly fromCreatedOn = DateTime.toDateOnly(criteria.getCreatedOn().getValues()[0]);
                    DateOnly ToCreatedOn = DateTime.toDateOnly(criteria.getCreatedOn().getValues()[1]);
                    if (transactionCreatedDate.isBefore(fromCreatedOn) || transactionCreatedDate.isAfter(ToCreatedOn)){
                        return true;
                    }
            }
        }

        String createdBy = getMessageCriteriaCreatedBy(criteria);
        if ((createdBy != null) && (transaction.getCreatedBy() != null)) {
            if (!transaction.getCreatedBy().equals(createdBy)) {
                return true;
            }
        }

        DateTime lastChangedOn = getMessageCriteriaLastChangedOn(criteria);
        if ((lastChangedOn != null) && (transaction.getLastChangedOn() != null)) {
            if (!transaction.getLastChangedOn().equals(lastChangedOn)) {
                return true;
            }
        }

        String lastChangedby = getMessageCriteriaLastChangedBy(criteria);
        if ((lastChangedby != null) && (transaction.getLastChangedBy() != null)) {
            if (!transaction.getLastChangedBy().equals(lastChangedby)) {
                return true;
            }
        }

        String errorMessage = getMessageCriteriaErrorMessage(criteria);
        if ((errorMessage != null) && (transaction.getErrorMessage() != null)) {
            if (!transaction.getErrorMessage().equals(errorMessage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the first type defined in the message criteria.
     * Returns null if no type is defined.
     *
     * @param criteria the message criteria to return the type value of
     * @return the first type value of the message criteria
     */
    private String getMessageCriteriaType(MessageCriteria criteria) {
        if ((criteria.getType() != null) && (criteria.getType().getValues() != null)
                && (criteria.getType().getValues().length > 0)) {
            return criteria.getType().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first subType defined in the message criteria.
     * Returns null if no subType is defined.
     *
     * @param criteria the message criteria to return the subType value of
     * @return the first subType value of the message criteria
     */
    private String getMessageCriteriaSubType(MessageCriteria criteria) {
        if ((criteria.getSubType() != null) && (criteria.getSubType().getValues() != null)
                && (criteria.getSubType().getValues().length > 0)) {
            return criteria.getSubType().getValues()[0];
        }
        return null;
    }

    /**
     * Gets a mapping of orderBy fields and their directions.
     * True for ascending and false for descending.
     *
     * @param criteria the criteria to pull the orderBy fields from
     * @return a mapping of orderBy fields to their direction
     */
    private LinkedHashMap<String, Boolean> getOrderByFields(MessageCriteria criteria) {
        LinkedHashMap<String, Boolean> results = new LinkedHashMap<String, Boolean>();
        if ((criteria.getOrderByFields() == null) || (criteria.getOrderByFields().length == 0)) {
            return results;
        }
        for (OrderByField orderByField : criteria.getOrderByFields()) {
            if ((orderByField.getSortAscending() != null) && !orderByField.getSortAscending()) {
                results.put(orderByField.getFieldName(), false);
            } else {
                results.put(orderByField.getFieldName(), true);
            }
        }
        return results;
    }

    /**
     * Gets the values defined in the field criteria.
     *
     * @param criteria the field criteria to return the values of
     * @return the values of the field criteria
     */
    private List<String> getFieldCriteriaValues(MessageFieldCriteria criteria) {
        if ((criteria.getValue() != null) && (criteria.getValue().getValues() != null)
                && (criteria.getValue().getValues().length > 0)) {
            return Arrays.asList(criteria.getValue().getValues());
        }
        return new ArrayList<String>();
    }

    /**
     * Gets the first ID defined in the message criteria.
     * Returns null if no ID is defined.
     *
     * @param criteria the message criteria to return the ID value of
     * @return the first ID value of the message criteria
     */
    private String getMessageCriteriaId(MessageCriteria criteria) {
        if ((criteria.getMessageId() != null) && (criteria.getMessageId().getValues() != null)
                && (criteria.getMessageId().getValues().length > 0)) {
            return criteria.getMessageId().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first Direction defined in the message criteria.
     * Returns null if no Direction is defined.
     *
     * @param criteria the message criteria to return the Direction value of
     * @return the first Direction value of the message criteria
     */
    private String getMessageCriteriaDirection(MessageCriteria criteria) {
        if ((criteria.getDirection() != null) && (criteria.getDirection().getValues() != null)
                && (criteria.getDirection().getValues().length > 0)) {
            return criteria.getDirection().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first Status defined in the message criteria.
     * Returns null if no Status is defined.
     *
     * @param criteria the message criteria to return the Status value of
     * @return the first Status value of the message criteria
     */
    private List<String> getMessageCriteriaStatus(MessageCriteria criteria) {
        if ((criteria.getStatus() != null) && (criteria.getStatus().getValues() != null)
                && (criteria.getStatus().getValues().length > 0)) {
            return Arrays.asList(criteria.getStatus().getValues());
        }
        return new ArrayList<String>();
    }

    /**
     * Gets the first CreatedOn defined in the message criteria.
     * Returns null if no CreatedOn is defined.
     *
     * @param criteria the message criteria to return the CreatedOn value of
     * @return the first CreatedOn value of the message criteria
     */
    private DateTime getMessageCriteriaCreatedOn(MessageCriteria criteria) {
        if ((criteria.getCreatedOn() != null) && (criteria.getCreatedOn().getValues() != null)
                && (criteria.getCreatedOn().getValues().length > 0)) {
            return criteria.getCreatedOn().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first CreatedBy defined in the message criteria.
     * Returns null if no CreatedBy is defined.
     *
     * @param criteria the message criteria to return the CreatedBy value of
     * @return the first CreatedBy value of the message criteria
     */
    private String getMessageCriteriaCreatedBy(MessageCriteria criteria) {
        if ((criteria.getCreatedBy() != null) && (criteria.getCreatedBy().getValues() != null)
                && (criteria.getCreatedBy().getValues().length > 0)) {
            return criteria.getCreatedBy().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first LastChangedOn defined in the message criteria.
     * Returns null if no LastChangedOn is defined.
     *
     * @param criteria the message criteria to return the LastChangedOn value of
     * @return the first LastChangedOn value of the message criteria
     */
    private DateTime getMessageCriteriaLastChangedOn(MessageCriteria criteria) {
        if ((criteria.getLastChangedOn() != null) && (criteria.getLastChangedOn().getValues() != null)
                && (criteria.getLastChangedOn().getValues().length > 0)) {
            return criteria.getLastChangedOn().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first LastChangedBy defined in the message criteria.
     * Returns null if no LastChangedBy is defined.
     *
     * @param criteria the message criteria to return the LastChangedBy value of
     * @return the first LastChangedBy value of the message criteria
     */
    private String getMessageCriteriaLastChangedBy(MessageCriteria criteria) {
        if ((criteria.getLastChangedBy() != null) && (criteria.getLastChangedBy().getValues() != null)
                && (criteria.getLastChangedBy().getValues().length > 0)) {
            return criteria.getLastChangedBy().getValues()[0];
        }
        return null;
    }

    /**
     * Gets the first ErrorMessage defined in the message criteria.
     * Returns null if no ErrorMessage is defined.
     *
     * @param criteria the message criteria to return the ErrorMessage value of
     * @return the first ErrorMessage value of the message criteria
     */
    private String getMessageCriteriaErrorMessage(MessageCriteria criteria) {
        if ((criteria.getErrorMessage() != null) && (criteria.getErrorMessage().getValues() != null)
                && (criteria.getErrorMessage().getValues().length > 0)) {
            return criteria.getErrorMessage().getValues()[0];
        }
        return null;
    }

    /**
     * Creates MetaData for the input Type, based on the available fields in the PropertyFilter.
     */
    private static QueueMetaData createQueueMetaData(String type, PropertyFilter pf) {
        QueueMetaData qmd = null;
        if (pf.isFieldIncluded("queueMetaData")) {
            qmd = new QueueMetaData();
            if (pf.isFieldIncluded("queueMetaData.queueSystemId")) {
                qmd.setQueueSystemId(QUEUE_SYSTEM_ID);
            }
            if (pf.isFieldIncluded("queueMetaData.type")) {
                qmd.setType(type);
            }
            if (pf.isFieldIncluded("queueMetaData.supportedMessageStatus")) {
                qmd.setSupportedMessageStatus(SUPPORTED_MESSAGE_STATUS);
            }

            if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields")) {
                TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(type);
                if (typeInfo != null && typeInfo.getDisplayParam() != null) {
                    Collection<MessageFieldMetaData> supportedApplicationFields = new LinkedList<MessageFieldMetaData>();
                    for (DisplayParam displayParam : typeInfo.getDisplayParam()) {
                        MessageFieldMetaData supportedApplicationField = new MessageFieldMetaData();
                        if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields.name")) {
                            supportedApplicationField.setName(displayParam.getName());
                        }
                        if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields.label")) {
                            supportedApplicationField.setLabel(MessageHelper.replaceTokens(displayParam.getLabel()));
                        }
                        supportedApplicationFields.add(supportedApplicationField);
                    }
                    if (!supportedApplicationFields.isEmpty()) {
                        qmd.setSupportedApplicationFields(supportedApplicationFields.toArray(new MessageFieldMetaData[supportedApplicationFields.size()]));
                    }
                }
            }

            if (pf.isFieldIncluded("queueMetaData.supportsTechnicalFields")) {
                qmd.setSupportsTechnicalFields(SUPPORTS_TECHNICAL_FIELDS);
            }
            if (pf.isFieldIncluded("queueMetaData.supportsBusinessEventLogs")) {
                qmd.setSupportsBusinessEventLogs(SUPPORTS_BUSINESS_EVENT_LOGS);
            }
            if (pf.isFieldIncluded("queueMetaData.supportsDependencies")) {
                qmd.setSupportsDependencies(SUPPORTS_DEPENDENCIES);
            }
        }
        return qmd;
    }

    /**
     * Molds the input Transaction into a MessageGraph.
     */
    private static MessageGraph createMessageGraph(Transaction transaction, PropertyFilter pf) throws FrameworkException {
        MessageGraph graph = new MessageGraph();
        graph.setQueueMetaData(createQueueMetaData(transaction.getSubType(), pf));
        if (pf.isFieldIncluded("type")) {
            graph.setType(transaction.getType());
        }
        if (pf.isFieldIncluded("subType")) {
            graph.setSubType(transaction.getSubType());
        }
        if (pf.isFieldIncluded("messageId")) {
            graph.setMessageId(transaction.getId());
        }
        if (pf.isFieldIncluded("direction")) {
            graph.setDirection(transaction.getDirection());
        }
        if (pf.isFieldIncluded("status")) {
            graph.setStatus(transactionToMessageStatus(transaction.getStatus()));
        }
        if (pf.isFieldIncluded("createdOn")) {
            graph.setCreatedOn(transaction.getCreatedOn());
        }
        if (pf.isFieldIncluded("createdBy")) {
            graph.setCreatedBy(transaction.getCreatedBy());
        }
        if (pf.isFieldIncluded("lastChangedOn")) {
            graph.setLastChangedOn(transaction.getLastChangedOn());
        }
        if (pf.isFieldIncluded("lastChangedBy")) {
            graph.setLastChangedBy(transaction.getLastChangedBy());
        }
        if (pf.isFieldIncluded("errorMessage")) {
            graph.setErrorMessage(transaction.getErrorMessage());
        }
        if (pf.isFieldIncluded("payload")) {
            graph.setPayload(transaction.getTransactionPayloadObject() != null ? transaction.getTransactionPayloadObject().returnInternalPayload() : null);
        }
        if (pf.isFieldIncluded("hasAdminAccess")) {
            graph.setHasAdminAccess(TransactionEngine.getInstance().hasAdminAccess(transaction.getType()));
        }
        if (pf.isFieldIncluded("applicationFields")) {
            Map<String, MessageField> applicationFields = new LinkedHashMap<String, MessageField>();
            TransactionField[] transactionFields = transaction.getTransactionFieldArray();
            if (transactionFields != null && transactionFields.length > 0) {
                for (int i = 0; i < transactionFields.length; i++) {
                    TransactionField transactionField = transactionFields[i];
                    MessageField applicationField = new MessageField();
                    applicationField.setName(transactionField.getFieldName());
                    applicationField.setValue(transactionField.getValue());
                    applicationFields.put(applicationField.getName(), applicationField);
                }
            }
            //Add labels to the application-fields from the configuration-file
            TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(transaction.getType());
            if (typeInfo != null && typeInfo.getDisplayParam() != null) {
                for (DisplayParam displayParam : typeInfo.getDisplayParam()) {
                    if (displayParam.getLabel() != null && applicationFields.containsKey(displayParam.getName())) {
                        applicationFields.get(displayParam.getName()).setLabel(MessageHelper.replaceTokens(displayParam.getLabel()));
                    }
                }
            }
            if (!applicationFields.isEmpty()) {
                graph.setApplicationFields(applicationFields.values().toArray(new MessageField[applicationFields.size()]));
            }
        }
        if (pf.isFieldIncluded("messageDependencies")) {
            TransactionDependency[] transactionDependencies = transaction.getTransactionDependencyArray();
            if (transactionDependencies != null && transactionDependencies.length > 0) {
                MessageDependency[] messageDependencies = new MessageDependency[transactionDependencies.length];
                for (int i = 0; i < transactionDependencies.length; i++) {
                    TransactionDependency transactionDependency = transactionDependencies[i];
                    MessageDependency messageDependency = new MessageDependency();
                    messageDependency.setDependsOnId(transactionDependency.getDependsOnId());
                    if (transactionDependency.getStatus() != null) {
                        MessageDependency.Status messageDependencyStatus = null;
                        TransactionDependency.Status transactionDependencyStatus = TransactionDependency.Status.valueOf(transactionDependency.getStatus());
                        switch (transactionDependencyStatus) {
                            case O:
                                messageDependencyStatus = MessageDependency.Status.OPEN;
                                break;
                            case S:
                                messageDependencyStatus = MessageDependency.Status.SUCCESS;
                                break;
                        }
                        messageDependency.setStatus(messageDependencyStatus);
                    }
                    messageDependencies[i] = messageDependency;
                }
                graph.setMessageDependencies(messageDependencies);
            }
        }
        return graph;
    }

    /**
     * Converts the input Transaction.Status to the corresponding Message.Status
     */
    private static MessageGraph.Status transactionToMessageStatus(String transactionStatus) {
        MessageGraph.Status messageStatus = null;
        if (transactionStatus != null) {
            switch (Transaction.Status.valueOf(transactionStatus)) {
                case O:
                    messageStatus = MessageGraph.Status.OPEN;
                    break;
                case S:
                    messageStatus = MessageGraph.Status.SUCCESS;
                    break;
                case E:
                    messageStatus = MessageGraph.Status.ERROR;
                    break;
                case H:
                    messageStatus = MessageGraph.Status.HOLD;
                    break;
                case I:
                    messageStatus = MessageGraph.Status.IN_PROCESS;
                    break;
                case INT:
                    messageStatus = MessageGraph.Status.INTERRUPTED;
                    break;
            }
        }
        return messageStatus;
    }

    /**
     * Converts the input Message.Status to the corresponding Transaction.Status
     */
    private static Transaction.Status messageToTransactionStatus(String messageStatus) {
        Transaction.Status transactionStatus = null;
        if (messageStatus != null) {
            switch (MessageGraph.Status.valueOf(messageStatus)) {
                case OPEN:
                    transactionStatus = Transaction.Status.O;
                    break;
                case SUCCESS:
                    transactionStatus = Transaction.Status.S;
                    break;
                case ERROR:
                    transactionStatus = Transaction.Status.E;
                    break;
                case HOLD:
                    transactionStatus = Transaction.Status.H;
                    break;
                case IN_PROCESS:
                    transactionStatus = Transaction.Status.I;
                    break;
                case INTERRUPTED:
                    transactionStatus = Transaction.Status.INT;
                    break;
            }
        }
        return transactionStatus;
    }

    /**
     * Converts the input CriteriaField containing Message.Status values to a new CriteriaField containing the corresponding Transaction.Status values.
     */
    private static StringCriteriaField messageToTransactionStatusCriteriaField(StringCriteriaField messageStatusCriteriaField) {
        StringCriteriaField transactionStatusCriteriaField = null;
        if (messageStatusCriteriaField != null && messageStatusCriteriaField.getValues() != null && messageStatusCriteriaField.getValues().length > 0) {
            String[] values = new String[messageStatusCriteriaField.getValues().length];
            for (int i = 0; i < values.length; i++) {
                Transaction.Status value = messageToTransactionStatus(messageStatusCriteriaField.getValues()[i]);
                values[i] = value != null ? value.toString() : null;
            }
            transactionStatusCriteriaField = new StringCriteriaField(messageStatusCriteriaField.getOperator(), values);
        } else {
            transactionStatusCriteriaField = messageStatusCriteriaField;
        }
        return transactionStatusCriteriaField;
    }


    /**
     * Invokes the intended handler. This Handler must implement the IMessageHandler Interface in order to be invoked.
     *
     * @param uow
     * @param transaction
     * @param methodName
     * @throws JaffaMessagingFrameworkException
     *
     */
    private static void invokeHandler(UOW uow, Transaction transaction, String methodName) throws Exception {

        try {
            TransactionPayload tp = transaction.getTransactionPayloadObject();
            Object dataBean = tp != null ? tp.moldInternalPayload() : null;
            if (dataBean != null) {
                //Load transaction configuration

                TransactionInfo transactionInfo = ConfigurationService.getInstance().getTransactionInfo(tp.getInternalMessageClass());
                if (transactionInfo == null) {
                    throw new JaffaMessagingFrameworkException(JaffaTransactionFrameworkException.TRANSACTION_INFO_MISSING, new Object[]{tp.getInternalMessageClass()});
                }

                // Obtain the handlerClass
                if (transactionInfo.getToClass() == null || transactionInfo.getToClass().length() == 0) {
                    if (log.isDebugEnabled()) {
                        log.debug(methodName + " toClass is not defined in data bean configuration: " + transactionInfo.getDataBean());
                    }
                    return;
                }
                Class handlerClass = Class.forName(transactionInfo.getToClass());
                //Class dataBeanClass = Class.forName(transactionInfo.getDataBean());
                if (IMessageHandler.class.isAssignableFrom(handlerClass)) {
                    // Unmarshals the Message payload into a dataBean using the dataBeanClassName

                    Method handlerMethod = null;
                    Object handlerObject = null;
                    // Obtain the handler method
                    try {
                        handlerMethod = handlerClass.getMethod(methodName, new Class[]{UOW.class, Map.class, Object.class});
                    } catch (NoSuchMethodException e) {
                        // Note that the payload could be a subclass of the argument that the handler method expects
                        // Hence use the dataBeanClass specified in the messageInfo to get the appropriate handlerMethod
                        if (log.isDebugEnabled()) {
                            log.debug(methodName + " method not found in " + handlerClass.getName());
                        }
                        return;
                    }
                    handlerObject = handlerClass.newInstance();

                    Map<String, String> headerMap = new HashMap<String, String>();
                    // Sets the transactionField elements as defined in the configuration file.

                    TransactionField[] fields = transaction.getTransactionFieldArray();
                    if (fields != null) {
                        for (TransactionField field : fields) {
                            headerMap.put(field.getFieldName(), field.getValue());
                        }
                    }
                    // Invoke the handler
                    if (log.isDebugEnabled()) {
                        log.debug("Invoking the handler " + handlerMethod);
                    }
                    handlerMethod.invoke(handlerObject, new Object[]{uow, headerMap, dataBean});
                }
            }
        } catch (Exception e) {
            // Just log the error
            log.error("Exception thrown while deleting the transaction. Transaction was: " + transaction, e);
            throw e;
        }
    }
}