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
package org.jaffa.qm.apis;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.qm.apis.data.MessageAdminResponse;
import org.jaffa.qm.apis.data.MessageCriteria;
import org.jaffa.qm.apis.data.MessageGraph;
import org.jaffa.qm.apis.data.MessageQueryResponse;
import org.jaffa.qm.apis.data.QueueAdminResponse;
import org.jaffa.qm.apis.data.QueueCriteria;
import org.jaffa.qm.apis.data.QueueGraph;
import org.jaffa.qm.apis.data.QueueQueryResponse;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.graph.GraphComparator;
import org.jaffa.soa.graph.ServiceError;

/**
 *
 * @author GautamJ
 */
public class QueueManager {

    private static final Logger log = Logger.getLogger(QueueManager.class);
    private static final String RULE = "jaffa.qm.IQueueAdmin.implementations";

    // TODO-SWAT add script events here

    public QueueQueryResponse queueQuery(QueueCriteria criteria) {
        try {
            if (log.isDebugEnabled())
                log.debug("Input to queueQuery: " + criteria);
            QueueQueryResponse aggregateResponse = new QueueQueryResponse();
            IQueueAdmin[] implementations = findImplementations();
            if (implementations != null && implementations.length > 0) {
                for (IQueueAdmin implementation : implementations) {
                    QueueQueryResponse response = implementation.queueQuery(criteria);
                    if (response != null && response.getErrors() != null && response.getErrors().length > 0) {
                        aggregateResponse.setErrors(response.getErrors());
                        break;
                    } else if (response != null && response.getGraphs() != null && response.getGraphs().length > 0) {
                        aggregateResponse.setGraphs(concatenate(aggregateResponse.getGraphs(), response.getGraphs()));
                    }
                }
                if (aggregateResponse.getErrors() == null && aggregateResponse.getGraphs() != null && aggregateResponse.getGraphs().length > 0 && criteria.getOrderByFields() != null && criteria.getOrderByFields().length > 0)
                    Arrays.sort(aggregateResponse.getGraphs(), new GraphComparator(criteria.getOrderByFields()));

                handlePaging(aggregateResponse, criteria);
            }
            if (aggregateResponse.getGraphs() == null)
                aggregateResponse.setGraphs(new QueueGraph[0]);
            if (log.isDebugEnabled())
                log.debug("Response from queueQuery: " + aggregateResponse);
            return aggregateResponse;
        } catch (Exception e) {
            log.error("Error in obtaining IQueueAdmin implementations", e);
            return new QueueQueryResponse(null, ServiceError.generate(e));
        }
    }

    public MessageQueryResponse messageQuery(MessageCriteria criteria) throws ApplicationExceptions {
        try {
            if (log.isDebugEnabled())
                log.debug("Input to messageQuery: " + criteria);
            MessageQueryResponse aggregateResponse = new MessageQueryResponse();
            IQueueAdmin[] implementations = findImplementations();

            //Find which implementation of queue admin can be used to query messages for supplied queue
            IQueueAdmin queryImplementation = null;
            boolean foundMultiple = false;
            if (implementations != null && implementations.length > 0) {
                for (IQueueAdmin implementation : implementations) {
                    QueueCriteria queueCriteria = new QueueCriteria();
                    queueCriteria.setType(criteria.getType());
                    queueCriteria.setQueueSystemId(criteria.getQueueSystemId());
                    QueueQueryResponse queueResponse = implementation.queueQuery(queueCriteria);
                    if (queueResponse != null && queueResponse.getGraphs()!=null && queueResponse.getGraphs().length==1) {
                        if (queryImplementation!=null) foundMultiple = true;
                        queryImplementation = implementation;
                    }else if (queueResponse.getGraphs()!=null && queueResponse.getGraphs().length>1){
                        foundMultiple = true;
                    }
                }
            }
            if (foundMultiple){
                log.error("Found multiple IQueueAdmin implementations for message query");
                throw new ApplicationExceptions(new QueueAdminException(QueueAdminException.MULTIPLE_QUEUES));
            }

            //Use correct queue admin to retrieve messages. Admin class should perform sorting and paging.
            if (queryImplementation != null) {
                MessageQueryResponse response = queryImplementation.messageQuery(criteria);
                if (response != null && response.getErrors() != null && response.getErrors().length > 0) {
                    aggregateResponse.setErrors(response.getErrors());
                } else if (response != null && response.getGraphs() != null && response.getGraphs().length > 0) {
                    aggregateResponse.setGraphs(concatenate(aggregateResponse.getGraphs(), response.getGraphs()));
                }
                if (response.getTotalRecords()!=null && response.getTotalRecords() > 0)
                    aggregateResponse.setTotalRecords(response.getTotalRecords());
            }

            if (aggregateResponse.getGraphs() == null)
                aggregateResponse.setGraphs(new MessageGraph[0]);
            if (log.isDebugEnabled())
                log.debug("Response from messageQuery: " + aggregateResponse);
            return aggregateResponse;

        } catch (Exception e) {
            log.error("Error in obtaining IQueueAdmin implementations", e);
            return new MessageQueryResponse(null, ServiceError.generate(e));
        }
    }

    public QueueAdminResponse[] toggleQueueStatus(QueueGraph[] graphs) {
        try {
            QueueAdminResponse[] aggregateResponse = null;
            IQueueAdmin[] implementations = findImplementations();
            if (implementations != null && implementations.length > 0) {
                for (IQueueAdmin implementation : implementations) {
                    QueueAdminResponse[] response = implementation.toggleQueueStatus(graphs);
                    if (response != null && response.length > 0)
                        aggregateResponse = concatenate(aggregateResponse, response);
                }
            }
            return aggregateResponse;
        } catch (Exception e) {
            log.error("Error in obtaining IQueueAdmin implementations", e);
            return new QueueAdminResponse[]{new QueueAdminResponse(null, ServiceError.generate(e))};
        }
    }

    public MessageAdminResponse[] deleteMessage(MessageGraph[] graphs) {
        // TODO-SWAT fire custom handler here
        try {
            MessageAdminResponse[] aggregateResponse = null;
            IQueueAdmin[] implementations = findImplementations();
            if (implementations != null && implementations.length > 0) {
                for (IQueueAdmin implementation : implementations) {
                    MessageAdminResponse[] response = implementation.deleteMessage(graphs);
                    if (response != null && response.length > 0)
                        aggregateResponse = concatenate(aggregateResponse, response);
                }
            }
            return aggregateResponse;
        } catch (Exception e) {
            log.error("Error in obtaining IQueueAdmin implementations", e);
            return new MessageAdminResponse[]{new MessageAdminResponse(null, ServiceError.generate(e))};
        }
    }

    public MessageAdminResponse[] resubmitMessage(MessageGraph[] graphs) {
        try {
            MessageAdminResponse[] aggregateResponse = null;
            IQueueAdmin[] implementations = findImplementations();
            if (implementations != null && implementations.length > 0) {
                for (IQueueAdmin implementation : implementations) {
                    MessageAdminResponse[] response = implementation.resubmitMessage(graphs);
                    if (response != null && response.length > 0)
                        aggregateResponse = concatenate(aggregateResponse, response);
                }
            }
            return aggregateResponse;
        } catch (Exception e) {
            log.error("Error in obtaining IQueueAdmin implementations", e);
            return new MessageAdminResponse[]{new MessageAdminResponse(null, ServiceError.generate(e))};
        }
    }

    /** Instantiates the comma-separated list of classes specified for the "jaffa.qm.IQueueAdmin.implementations" application rule. */
    private static IQueueAdmin[] findImplementations() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        IQueueAdmin[] implementations = null;
        String rule = (String) ContextManagerFactory.instance().getProperty(RULE);
        if (rule != null && rule.trim().length() > 0) {
            String[] classNames = rule.trim().split(",");
            implementations = new IQueueAdmin[classNames.length];
            for (int i = 0; i < classNames.length; i++) {
                String className = classNames[i].trim();
                if (log.isDebugEnabled())
                    log.debug("Instantiating " + className);
                implementations[i] = (IQueueAdmin) Class.forName(className).newInstance();
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("IQAdmin implementations not provided by the application rule " + RULE);
        }
        return implementations;
    }

    /** Concatenates the 2 input arrays. */
    private static <T> T[] concatenate(T[] a1, T[] a2) {
        T[] output = null;
        if (a1 != null && a2 != null) {
            output = Arrays.copyOf(a1, a1.length + a2.length);
            System.arraycopy(a2, 0, output, a1.length, a2.length);
        } else if (a1 != null) {
            output = a1;
        } else if (a2 != null) {
            output = a2;
        }
        return output;
    }

    /** Applies the objectStart, objectLimit and findTotalRecords criteria options to the response. */
    private static void handlePaging(QueueQueryResponse response, QueueCriteria criteria) {
        if (criteria.getFindTotalRecords() == null || criteria.getFindTotalRecords())
            response.setTotalRecords(response.getGraphs() != null ? response.getGraphs().length : 0);
        if (response.getGraphs() != null && response.getGraphs().length > 0) {
            if ((criteria.getObjectStart() != null && criteria.getObjectStart() > 0) || (criteria.getObjectLimit() != null && criteria.getObjectLimit() > 0)) {
                QueueGraph[] graphs = response.getGraphs();
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
