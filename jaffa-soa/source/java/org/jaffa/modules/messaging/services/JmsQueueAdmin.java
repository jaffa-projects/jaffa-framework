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
package org.jaffa.modules.messaging.services;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.modules.messaging.services.configdomain.ConsumerPolicy;
import org.jaffa.modules.messaging.services.configdomain.DisplayParam;
import org.jaffa.modules.messaging.services.configdomain.QueueInfo;
import org.jaffa.qm.apis.IQueueAdmin;
import org.jaffa.qm.apis.data.MessageAdminResponse;
import org.jaffa.qm.apis.data.MessageCriteria;
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
import org.jaffa.soa.graph.GraphComparator;
import org.jaffa.soa.graph.ServiceError;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.MessageHelper;

/**
 * This IQueueAdmin implementation is based on the JMS Queues.
 * @author GautamJ
 */
public class JmsQueueAdmin implements IQueueAdmin {

    private static final Logger log = Logger.getLogger(JmsQueueAdmin.class);
    public static final String QUEUE_SYSTEM_ID = "JMS";
    public static final MessageGraph.Status[] SUPPORTED_MESSAGE_STATUS = new MessageGraph.Status[]{MessageGraph.Status.OPEN, MessageGraph.Status.ERROR, MessageGraph.Status.IN_PROCESS};
    public static final Boolean SUPPORTS_TECHNICAL_FIELDS = Boolean.TRUE;
    public static final Boolean SUPPORTS_BUSINESS_EVENT_LOGS = Boolean.TRUE;
    public static final Boolean SUPPORTS_DEPENDENCIES = Boolean.FALSE;

    public QueueQueryResponse queueQuery(QueueCriteria criteria) {
        if (log.isDebugEnabled())
            log.debug("Input to queueQuery: " + criteria);
        QueueQueryResponse output = new QueueQueryResponse();

        try {
            //Match the queueSystemId as well apply checks for unsupported criteria
            if (FinderTx.match(QUEUE_SYSTEM_ID, criteria.getQueueSystemId()) && FinderTx.match(null, criteria.getSuccessCount()) && FinderTx.match(null, criteria.getHoldCount()) && FinderTx.match(null, criteria.getInterruptedCount())) {
                String[] queueNames = JmsBrowser.getAccessibleQueueNames();
                if (queueNames != null && queueNames.length > 0) {
                    PropertyFilter pf = PropertyFilter.getInstance(QueueGraph.class, criteria.getResultGraphRules());
                    Collection<QueueGraph> graphs = new LinkedList<QueueGraph>();
                    for (String queueName : queueNames) {
                        if (FinderTx.match(queueName, criteria.getType())) {
                            QueueGraph graph = new QueueGraph();

                            //Compute message count only if required by criteria or if it is included in the property-filter
                            if (criteria.getOpenCount() != null || pf.isFieldIncluded("openCount")) {
                                Message[] messages = JmsBrowser.getPendingMessages(queueName);
                                Long count = messages != null ? messages.length : 0L;
                                if (!FinderTx.match(count, criteria.getOpenCount()))
                                    continue;
                                if (pf.isFieldIncluded("openCount"))
                                    graph.setOpenCount(count);
                            }

                            //Apply the status criteria
                            if (criteria.getStatus() != null || pf.isFieldIncluded("status")) {
                                QueueGraph.Status status;
                                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
                                if (queueInfo != null && queueInfo.getConsumerPolicy() != ConsumerPolicy.NONE)
                                    status = QueueGraph.Status.ACTIVE;
                                else
                                    status = QueueGraph.Status.ACTIVE;
                                if (!FinderTx.match(status.toString(), criteria.getStatus()))
                                    continue;
                                if (pf.isFieldIncluded("status"))
                                    graph.setStatus(status);
                            }

                            //Stamp the remaining properties, if included in the property-filter
                            graph.setQueueMetaData(createQueueMetaData(queueName, pf));
                            if (pf.isFieldIncluded("type"))
                                graph.setType(queueName);
                            if (pf.isFieldIncluded("hasAdminAccess"))
                                graph.setHasAdminAccess(JmsBrowser.hasAdminMessageAccess(queueName));

                            graphs.add(graph);
                        }
                    }
                    if (graphs.size() > 0)
                        output.setGraphs(graphs.toArray(new QueueGraph[graphs.size()]));
                }
            }
        } catch (Exception e) {
            // add errors to the response
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled())
                    log.debug("Error in queueQuery execution", appExps);
                output.setErrors(ServiceError.generate(appExps));
            } else {
                log.error("Internal Error in queueQuery execution", e);
                output.setErrors(ServiceError.generate(e));
            }
        }

        if (log.isDebugEnabled())
            log.debug("Output from queueQuery: " + output);
        return output;
    }

    public MessageQueryResponse messageQuery(MessageCriteria criteria) {
        if (log.isDebugEnabled())
            log.debug("Input to messageQuery: " + criteria);
        MessageQueryResponse output = new MessageQueryResponse();

        try {
            //Match the queueSystemId as well apply checks for unsupported criteria
            if (FinderTx.match(QUEUE_SYSTEM_ID, criteria.getQueueSystemId()) && FinderTx.match(null, criteria.getSubType()) && FinderTx.match(null, criteria.getDirection()) && FinderTx.match(null, criteria.getLastChangedOn()) && FinderTx.match(null, criteria.getLastChangedBy())) {
                String[] queueNames = JmsBrowser.getAccessibleQueueNames();
                if (queueNames != null && queueNames.length > 0) {
                    String jmsFilter = createJmsFilter(criteria);
                    PropertyFilter pf = PropertyFilter.getInstance(MessageGraph.class, criteria.getResultGraphRules());
                    Collection<MessageGraph> graphs = new LinkedList<MessageGraph>();
                    for (String queueName : queueNames) {
                        if (FinderTx.match(queueName, criteria.getType())) {
                            //Retrieve messages for each requested status
                            for (MessageGraph.Status status : SUPPORTED_MESSAGE_STATUS) {
                                if (FinderTx.match(status.toString(), criteria.getStatus())) {
                                    Message[] messages = null;
                                    if (status == MessageGraph.Status.OPEN)
                                        messages = JmsBrowser.getPendingMessages(queueName, jmsFilter);
                                    if (messages != null) {
                                        for (Message message : messages) {
                                            MessageGraph graph = createMessageGraph(message, queueName, pf);
                                            if (pf.isFieldIncluded("status"))
                                                graph.setStatus(status);
                                            graphs.add(graph);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (graphs.size() > 0){
                        output.setGraphs(graphs.toArray(new MessageGraph[graphs.size()]));
                        if (criteria.getOrderByFields() != null && criteria.getOrderByFields().length > 0)
    	                    Arrays.sort(output.getGraphs(), new GraphComparator(criteria.getOrderByFields()));
	                    handlePaging(output, criteria);
					}
                }
            }
        } catch (Exception e) {
            // add errors to the response
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled())
                    log.debug("Error in messageQuery execution", appExps);
                output.setErrors(ServiceError.generate(appExps));
            } else {
                log.error("Internal Error in messageQuery execution", e);
                output.setErrors(ServiceError.generate(e));
            }
        }

        if (log.isDebugEnabled())
            log.debug("Output from messageQuery: " + output);
        return output;
    }

    /**
     * NOTE: Each graph is expected to contain type. It may also contain the optional queueMetaData.queueSystemId, which may help optimize the process.
     */
    public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs) {
        if (log.isDebugEnabled())
            log.debug("Input to toggleQueueStatus: " + Arrays.toString(graphs));
        Collection<QueueAdminResponse> output = null;
        String[] queueNames = null;
        for (QueueGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                // Initialize and sort the queueNames array; if not already done
                if (queueNames == null) {
                    queueNames = ConfigurationService.getInstance().getQueueNames();
                    Arrays.sort(queueNames);
                }
                if (Arrays.binarySearch(queueNames, graph.getType()) >= 0) {
                    try {
                        if (!JmsBrowser.hasAdminMessageAccess(graph.getType()))
                            throw new JaffaMessagingApplicationException(JaffaMessagingApplicationException.NO_ADMIN_MESSAGE_ACESSS, new Object[]{graph.getType()});
                        if (ConfigurationService.getInstance().getQueueInfo(graph.getType()).getConsumerPolicy() == ConsumerPolicy.NONE) {
                            if (log.isDebugEnabled())
                                log.debug("ConsumerPolicy of queue " + graph.getType() + " is NONE. It's status cannnot be toggled");
                        }
                    } catch (Exception e) {
                        QueueAdminResponse response = new QueueAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled())
                                log.debug("Error while toggling status of queue " + graph.getType(), appExps);
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while toggling status of queue " + graph.getType(), e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null)
                            output = new LinkedList<QueueAdminResponse>();
                        output.add(response);
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Status of queue " + graph.getType() + " will not be toggled by this implementation, since the queue does not belong to this system");
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Status of queue " + graph.getType() + " will not be toggled by this implementation, since the input queueSystemId does not match this system");
            }
        }
        if (log.isDebugEnabled())
            log.debug("Output from toggleQueueStatus: " + output);
        return output != null && output.size() > 0 ? output.toArray(new QueueAdminResponse[output.size()]) : null;
    }

    /**
     * NOTE: Each graph is expected to contain type, messageId and status. It may also contain the optional queueMetaData.queueSystemId, which may help optimize the process.
     */
    public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs) {
        if (log.isDebugEnabled())
            log.debug("Input to deleteMessage: " + Arrays.toString(graphs));
        Collection<MessageAdminResponse> output = null;
        String[] queueNames = null;
        for (MessageGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                // Initialize and sort the queueNames array; if not already done
                if (queueNames == null) {
                    queueNames = ConfigurationService.getInstance().getQueueNames();
                    Arrays.sort(queueNames);
                }
                if (Arrays.binarySearch(queueNames, graph.getType()) >= 0) {
                    try {
                        if (graph.getStatus() == MessageGraph.Status.OPEN) {
                            if (log.isDebugEnabled())
                                log.debug("Deleting OPEN message " + graph.getMessageId());
                            JmsBrowser.deleteMessage(graph.getType(), graph.getMessageId());
                        } else if (graph.getStatus() == MessageGraph.Status.ERROR) {
                            if (log.isDebugEnabled())
                                log.debug("Deleting ERROR message " + graph.getMessageId());
                            JmsBrowser.deleteMessage(graph.getType(), graph.getMessageId());
                        } else {
                            if (log.isDebugEnabled())
                                log.debug("Message cannnot be deleted since unsupported status " + graph.getStatus() + " has been passed");
                        }
                    } catch (Exception e) {
                        MessageAdminResponse response = new MessageAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled())
                                log.debug("Error while deleting Message " + graph, appExps);
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while deleting Message " + graph, e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null)
                            output = new LinkedList<MessageAdminResponse>();
                        output.add(response);
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Message " + graph + " will not be deleted by this implementation, since the queue does not belong to this implementation");
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Message " + graph + " will not be deleted by this implementation, since the input queueSystemId does not match this system");
            }
        }
        if (log.isDebugEnabled())
            log.debug("Output from deleteMessage: " + output);
        return output != null && output.size() > 0 ? output.toArray(new MessageAdminResponse[output.size()]) : null;
    }

    /**
     * NOTE: Each graph is expected to contain type, messageId and status. It may also contain the optional queueMetaData.queueSystemId, which may help optimize the process.
     */
    public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs) {
        if (log.isDebugEnabled())
            log.debug("Input to resubmitMessage: " + Arrays.toString(graphs));
        Collection<MessageAdminResponse> output = null;
        String[] queueNames = null;
        for (MessageGraph graph : graphs) {
            if (graph.getQueueMetaData() == null || graph.getQueueMetaData().getQueueSystemId() == null || graph.getQueueMetaData().getQueueSystemId().equals(QUEUE_SYSTEM_ID)) {
                // Initialize and sort the queueNames array; if not already done
                if (queueNames == null) {
                    queueNames = ConfigurationService.getInstance().getQueueNames();
                    Arrays.sort(queueNames);
                }
                if (Arrays.binarySearch(queueNames, graph.getType()) >= 0) {
                    try {
                        if (graph.getStatus() == MessageGraph.Status.ERROR) {
                            if (log.isDebugEnabled())
                                log.debug("Resubmitting ERROR message " + graph.getMessageId());
                            JmsBrowser.resubmitMessage(graph.getType(), graph.getMessageId());
                        } else {
                            if (log.isDebugEnabled())
                                log.debug("Message cannnot be resubmitted since unsupported status " + graph.getStatus() + " has been passed");
                        }
                    } catch (Exception e) {
                        MessageAdminResponse response = new MessageAdminResponse();
                        response.setSource(graph);
                        ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                        if (appExps != null) {
                            if (log.isDebugEnabled())
                                log.debug("Error while resubmitting Message " + graph, appExps);
                            response.setErrors(ServiceError.generate(appExps));
                        } else {
                            log.error("Internal Error while resubmitting Message " + graph, e);
                            response.setErrors(ServiceError.generate(e));
                        }
                        if (output == null)
                            output = new LinkedList<MessageAdminResponse>();
                        output.add(response);
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Message " + graph + " will not be resubmitted by this implementation, since the queue does not belong to this implementation");
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Message " + graph + " will not be resubmitted by this implementation, since the input queueSystemId does not match this system");
            }
        }
        if (log.isDebugEnabled())
            log.debug("Output from resubmitMessage: " + output);
        return output != null && output.size() > 0 ? output.toArray(new MessageAdminResponse[output.size()]) : null;
    }

    /** Creates MetaData for the input Queue, based on the available fields in the PropertyFilter. */
    private static QueueMetaData createQueueMetaData(String queueName, PropertyFilter pf) {
        QueueMetaData qmd = null;
        if (pf.isFieldIncluded("queueMetaData")) {
            qmd = new QueueMetaData();
            if (pf.isFieldIncluded("queueMetaData.queueSystemId"))
                qmd.setQueueSystemId(QUEUE_SYSTEM_ID);
            if (pf.isFieldIncluded("queueMetaData.type"))
                qmd.setType(queueName);
            if (pf.isFieldIncluded("queueMetaData.supportedMessageStatus"))
                qmd.setSupportedMessageStatus(SUPPORTED_MESSAGE_STATUS);

            if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields")) {
                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
                if (queueInfo != null && queueInfo.getDisplayParam() != null) {
                    Collection<MessageFieldMetaData> supportedApplicationFields = new LinkedList<MessageFieldMetaData>();
                    for (DisplayParam displayParam : queueInfo.getDisplayParam()) {
                        //Ignore fields that are included in the main graph or are part of the technical-details
                        if (displayParam.getName().startsWith("JMS") || displayParam.getName().startsWith("jaffa_"))
                            continue;
                        MessageFieldMetaData supportedApplicationField = new MessageFieldMetaData();
                        if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields.name"))
                            supportedApplicationField.setName(displayParam.getName());
                        if (pf.isFieldIncluded("queueMetaData.supportedApplicationFields.label"))
                            supportedApplicationField.setLabel(MessageHelper.replaceTokens(displayParam.getLabel()));
                        supportedApplicationFields.add(supportedApplicationField);
                    }
                    if (!supportedApplicationFields.isEmpty())
                        qmd.setSupportedApplicationFields(supportedApplicationFields.toArray(new MessageFieldMetaData[supportedApplicationFields.size()]));
                }
            }

            if (pf.isFieldIncluded("queueMetaData.supportsTechnicalFields"))
                qmd.setSupportsTechnicalFields(SUPPORTS_TECHNICAL_FIELDS);
            if (pf.isFieldIncluded("queueMetaData.supportsBusinessEventLogs"))
                qmd.setSupportsBusinessEventLogs(SUPPORTS_BUSINESS_EVENT_LOGS);
            if (pf.isFieldIncluded("queueMetaData.supportsDependencies"))
                qmd.setSupportsDependencies(SUPPORTS_DEPENDENCIES);
        }
        return qmd;
    }

    /** Build a criteria string from messageId, priority, createdOn, createdBy, errorMessage and applicationFields, which can be used as a JMS filter. */
    private static String createJmsFilter(MessageCriteria criteria) {
        StringBuilder sql = new StringBuilder();
        addCriteria(criteria.getMessageId(), JmsBrowser.HEADER_JMS_MESSAGE_ID, sql);
        addCriteria(criteria.getPriority(), "JMSPriority", sql);
        addCriteria(criteria.getCreatedOn(), "JMSTimestamp", sql);
        addCriteria(criteria.getCreatedBy(), JmsBrowser.HEADER_USER_ID, sql);
        addCriteria(criteria.getErrorMessage(), JmsBrowser.HEADER_ERROR_DETAILS, sql);
        if (criteria.getApplicationFields() != null) {
            for (MessageFieldCriteria messageFieldCriteria : criteria.getApplicationFields())
                addCriteria(messageFieldCriteria.getValue(), messageFieldCriteria.getName(), sql);
        }
        if (log.isDebugEnabled() && sql.length() > 0)
            log.debug("Created JmsFilter: " + sql);
        return sql.length() > 0 ? sql.toString() : null;
    }

    /** Adds JMS-compatible criteria to the input StringBuilder, based on the input CriteriaField. */
    private static void addCriteria(CriteriaField criteriaField, String fieldName, StringBuilder sql) {
        if (criteriaField != null) {
            // validate the criteria
            criteriaField.validate();

            String operator = criteriaField.getOperator();
            Object[] values = criteriaField.returnValuesAsObjectArray();
            if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NULL))
                addCriteria(fieldName + " IS NULL", sql);
            else if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NOT_NULL))
                addCriteria(fieldName + " IS NOT NULL", sql);
            else if (values != null && values.length > 0) {
                if (operator != null && operator.length() == 0) {
                    // A non-Jaffa tool may pass empty operator and value, even when they didn't intend to pass them. Bail out!
                    if (values[0] == null || (values[0] instanceof String && values[0].toString().length() == 0))
                        return;
                    // A non-empty value has been passed. So treat the operator like an Equals type
                    operator = null;
                }
                if (operator == null || operator.equals(CriteriaField.RELATIONAL_EQUALS))
                    addCriteria(fieldName + '=' + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_NOT_EQUALS))
                    addCriteria(fieldName + "!=" + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN))
                    addCriteria(fieldName + '>' + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN))
                    addCriteria(fieldName + '<' + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO))
                    addCriteria(fieldName + ">=" + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO))
                    addCriteria(fieldName + "<=" + createSqlCriteriaString(values[0]), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
                    addCriteria(fieldName + " LIKE " + createSqlLikeCriteriaString(values[0], operator), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
                    addCriteria(fieldName + " LIKE " + createSqlLikeCriteriaString(values[0], operator), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
                    addCriteria(fieldName + " LIKE " + createSqlLikeCriteriaString(values[0], operator), sql);
                else if (operator.equals(CriteriaField.RELATIONAL_BETWEEN)) {
                    if (values.length > 0 && values[0] != null)
                        addCriteria(fieldName + ">=" + createSqlCriteriaString(values[0]), sql);
                    if (values.length > 1 && values[1] != null)
                        addCriteria(fieldName + "<=" + createSqlCriteriaString(values[1]), sql);
                } else if (operator.equals(CriteriaField.RELATIONAL_IN)) {
                    if (values.length == 1) {
                        if (values[0] == null)
                            addCriteria(fieldName + " IS NULL", sql);
                        else
                            addCriteria(fieldName + '=' + createSqlCriteriaString(values[0]), sql);
                    } else {
                        StringBuilder atomic = new StringBuilder();
                        if (values[0] == null)
                            atomic.append(fieldName).append(" IS NULL");
                        else
                            atomic.append(fieldName).append('=').append(createSqlCriteriaString(values[0]));
                        for (int i = 1; i < values.length; i++) {
                            atomic.append(" OR ");
                            if (values[i] == null)
                                atomic.append(fieldName).append(" IS NULL");
                            else
                                atomic.append(fieldName).append('=').append(createSqlCriteriaString(values[i]));
                        }
                        addCriteria('(' + atomic.toString() + ')', sql);
                    }
                }
            }
        }
    }

    /** Adds JMS-compatible criteria to the input StringBuilder. */
    private static void addCriteria(String criteria, StringBuilder sql) {
        if (sql.length() > 0)
            sql.append(" AND ");
        sql.append(criteria);
    }

    /** Surrounds the input with single-quotes, so that it can be used within a SQL criteria. */
    private static String createSqlCriteriaString(Object value) {
        String s = null;
        if (value == null)
            s = "''";
        else if (value instanceof DateTime)
            s = "" + ((DateTime) value).timeInMillis();
        else if (value instanceof Number)
            s = value.toString();
        else
            s = '\'' + value.toString() + '\'';
        return s;
    }

    /** Surrounds the input with single-quotes, so that it can be used within a SQL like-criteria. */
    private static String createSqlLikeCriteriaString(Object value, String operator) {
        String s = (value == null ? "" : (value instanceof DateTime ? "" + ((DateTime) value).timeInMillis() : value.toString()));
        if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
            s = '\'' + s + "%'";
        else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
            s = "'%" + s + '\'';
        else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
            s = "'%" + s + "%'";
        else
            s = '\'' + s + '\'';
        return s;
    }

    /** Molds the input JMS Message into a MessageGraph. */
    private static MessageGraph createMessageGraph(Message message, String queueName, PropertyFilter pf) throws JMSException, IntrospectionException {
        MessageGraph graph = new MessageGraph();
        graph.setQueueMetaData(createQueueMetaData(queueName, pf));
        if (pf.isFieldIncluded("type"))
            graph.setType(queueName);
        if (pf.isFieldIncluded("messageId"))
            graph.setMessageId(message.getJMSMessageID());
        if (pf.isFieldIncluded("priority"))
            graph.setPriority(new Long(message.getJMSPriority()));
        if (pf.isFieldIncluded("createdOn"))
            graph.setCreatedOn(message.getJMSTimestamp() != 0 ? new DateTime(message.getJMSTimestamp()) : null);
        if (pf.isFieldIncluded("createdBy"))
            graph.setCreatedBy(message.getStringProperty(JmsBrowser.HEADER_USER_ID));
        if (pf.isFieldIncluded("errorMessage"))
            graph.setErrorMessage(message.getStringProperty(JmsBrowser.HEADER_ERROR_DETAILS));
        if (pf.isFieldIncluded("payload") && message instanceof TextMessage)
            graph.setPayload(((TextMessage) message).getText());
        if (pf.isFieldIncluded("hasAdminAccess"))
            graph.setHasAdminAccess(JmsBrowser.hasAdminMessageAccess(queueName));

        if (pf.isFieldIncluded("applicationFields") || pf.isFieldIncluded("technicalFields")) {
            //Generate a Map of header elements, keyed by the name of each header element
            //Names beginning with JMS and jaffa_ will be treated as techincal, while the rest will be assumed to be application-specific
            Map<String, MessageField> applicationFields = new LinkedHashMap<String, MessageField>();
            Map<String, MessageField> technicalFields = new TreeMap<String, MessageField>();
            for (Enumeration e = message.getPropertyNames(); e.hasMoreElements();) {
                String name = (String) e.nextElement();
                String value = Formatter.format(message.getObjectProperty(name));
                MessageField messageField = new MessageField();
                messageField.setName(name);
                messageField.setValue(value);
                if (name.startsWith("JMS") || name.startsWith("jaffa_"))
                    technicalFields.put(name, messageField);
                else
                    applicationFields.put(name, messageField);
            }

            //Treat all the JMS* properties of the Message as technical
            if (pf.isFieldIncluded("technicalFields")) {
                BeanInfo beanInfo = Introspector.getBeanInfo(Message.class);
                if (beanInfo != null) {
                    PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                    if (pds != null) {
                        for (PropertyDescriptor pd : pds) {
                            if (pd.getReadMethod() != null && !pd.getPropertyType().isArray()) {
                                String name = pd.getName();
                                try {
                                    Object value = pd.getReadMethod().invoke(message);
                                    MessageField messageField = new MessageField();
                                    messageField.setName(name);
                                    messageField.setValue(Formatter.format(value));
                                    technicalFields.put(name, messageField);
                                } catch (Exception e) {
                                    if (log.isDebugEnabled())
                                        log.debug("Exception thrown while reading Message property: " + name, e);
                                }
                            }
                        }
                    }
                }
                if (!technicalFields.isEmpty())
                    graph.setTechnicalFields(technicalFields.values().toArray(new MessageField[technicalFields.size()]));
            }

            //Add labels to the application-fields from the configuration-file
            if (pf.isFieldIncluded("applicationFields")) {
                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
                if (queueInfo != null && queueInfo.getDisplayParam() != null) {
                    for (DisplayParam displayParam : queueInfo.getDisplayParam()) {
                        if (displayParam.getLabel() != null && applicationFields.containsKey(displayParam.getName()))
                            applicationFields.get(displayParam.getName()).setLabel(MessageHelper.replaceTokens(displayParam.getLabel()));
                    }
                }
                if (!applicationFields.isEmpty())
                    graph.setApplicationFields(applicationFields.values().toArray(new MessageField[applicationFields.size()]));
            }
        }
        return graph;
    }

    /** Applies the objectStart, objectLimit and findTotalRecords criteria options to the response. */
    private static void handlePaging(MessageQueryResponse response, MessageCriteria criteria) {
        if (criteria.getFindTotalRecords() == null || criteria.getFindTotalRecords())
            response.setTotalRecords(response.getGraphs() != null ? response.getGraphs().length : 0);
        if (response.getGraphs() != null && response.getGraphs().length > 0) {
            if ((criteria.getObjectStart() != null && criteria.getObjectStart() > 0) || (criteria.getObjectLimit() != null && criteria.getObjectLimit() > 0)) {
                MessageGraph[] graphs = response.getGraphs();
                int from = criteria.getObjectStart() != null ? criteria.getObjectStart() : 0;
                if (from >= graphs.length) {
                    //Return nothing if starting point is outside the range
                    graphs = null;
                } else {
                    //Return everything if objectLimit <= 0
                    int to = criteria.getObjectLimit() == null || criteria.getObjectLimit() <= 0 ? graphs.length : (from + criteria.getObjectLimit());
                    if (to > graphs.length)
                        to = graphs.length;
                    graphs = Arrays.copyOfRange(graphs, from, to);
                }
                response.setGraphs(graphs);
            }
        }
    }

}
