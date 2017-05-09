/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.modules.messaging.services;

import java.io.Serializable;
import java.util.*;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.configdomain.MessageFilter;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.messaging.services.configdomain.Param;
import org.jaffa.persistence.engines.IMessagingEngine;
import org.jaffa.persistence.engines.ISender;
import org.jaffa.security.SecurityManager;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.services.event.EventMessage;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.util.JAXBHelper;

/** This is a helper class to send JMS messages.
 * <p>
 * Use the static send() method for sending a single message without creating any JMS transaction.
 * <p>
 * To send multiple messages as part of a JMS transaction, perform the following steps:
 *      1- Obtain an instance of this class by invoking the getInstance() method. This will start the JMS transaction.
 *      2- Invoke the add() method for each Message to be sent.
 *      3- Invoke the commit() method to commit the JMS transaction, so as to send the messages.
 *      4- Always invoke the rollback() method in the finally-block
 */
public class JmsClientHelper implements IMessagingEngine {

    private static final Logger log = Logger.getLogger(JmsClientHelper.class);

    private static final String QUEUE_JNDI_PREFIX = "queue/";
    private static final String TOPIC_JNDI_PREFIX = "topic/";

    /** The rule name used to determine if a payload is to be processed immediately.
     * NOTE: This rule should NOT be enabled in a production enviroment. It is merely an aid to the developer to test some functionality, by bypassing the messaging system, and having the payload processed immediately.
     */
    private static final String RULE_POST_IMMEDIATE = "jaffa.messaging.postImmediate";
    /** Holds a session for an instance to support transactions. */
    private Session m_session;
    /** Holds a list of Message instances to be sent for the current session. The destinationName is maintained for each Message. */
    private Map<Message, String> m_messages;

    // *****************************************************************************
    // THE FOLLOWING METHODS ARE USED TO SEND MESSAGES IMMEDIATELY
    // *****************************************************************************
    /** Writes a JMS Message to the destination, as defined in the configuration file.
     * The message is sent immediately.
     * @param payload Any serializable object.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the MessageId.
     */
    public static String send(Object payload) throws FrameworkException, ApplicationExceptions {
        return send(payload, null, null);
    }

    /** Writes a JMS Message to the destination, as defined in the configuration file.
     * The message is sent immediately.
     * This method will typically be called by automated processes, which do not have an authenticated session.
     * The scheduledTaskId, if provided, will be put in the header, and flags the Message to be a result of a Scheduled Task.
     *
     * @param payload Any serializable object.
     * @param userId A header element in the JMS message will be set to this value.
     * @param scheduledTaskId A header element in the JMS message will be set to this value.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the MessageId.
     */
    public static String send(Object payload, String userId, String scheduledTaskId) throws FrameworkException, ApplicationExceptions {
        return send(null, applyFilters(payload), userId, scheduledTaskId);
    }

    // *****************************************************************************
    // THE FOLLOWING METHODS ARE USED TO SEND MULTIPLE MESSAGES IN A JMS TRANSACTION
    // *****************************************************************************
    /** Creates an instance.
     * It obtains a connection with the JmsProvider and starts a Session.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private JmsClientHelper() throws FrameworkException, ApplicationExceptions {
        Connection connection = JaffaConnectionFactory.obtainConnection();
        m_session = obtainSession(connection, true);
        if (log.isDebugEnabled())
            log.debug("Started a transactional JMS session");
    }

    /** Creates an instance of this class.
     * It obtains a connection with the JmsProvider and starts a Session.
     * @return an instance of this class.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static JmsClientHelper getInstance() throws FrameworkException, ApplicationExceptions {
        return new JmsClientHelper();
    }

    /** Creates a JMS Message, as defined in the configuration file for the payload.
     * The message will only be sent when the JMS transaction is committed.
     * @param payload Any serializable object.
     * @param dependsOnIds transactions this transaction depends on
     * @param externalMessage the external message of the transaction payload
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String add(Object payload,String[] dependsOnIds, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        checkState();
        send(this, applyFilters(payload), null, null);
        return null;
    }

    /**
     * Creates a JMS Message, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the JMS transaction is committed.
     *
     * @param payload      Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String addOutbound(Object payload) throws FrameworkException, ApplicationExceptions {
        checkState();
        send(this, applyFilters(payload), null, null);
        return null;
    }

    /**
     * Creates a JMS Message, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the JMS transaction is committed.
     *
     * @param payload      Any serializable object.
     * @param externalMessage the external message of the transaction payload
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public Transaction addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        checkState();
        send(this, applyFilters(payload), null, null);
        return null;
    }

    /** Sends the cached Messages and commits the JMS transaction.
     * NOTE: This instance should not be used after a commit();
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void commit() throws FrameworkException, ApplicationExceptions {
        checkState();
        try {
            // Send the messages that've been cached so far
            if (m_messages != null) {
                if (log.isDebugEnabled())
                    log.debug("Sending cached Messages");
                for (Map.Entry<Message, String> me : m_messages.entrySet())
                    send(m_session, me.getKey(), me.getValue());
            }
            m_session.commit();
            if (log.isDebugEnabled())
                log.debug("JMS Session committed");
            close();
        } catch (JMSException e) {
            log.error("Error in commit", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.COMMIT_ERROR, null, e);
        }
    }

    /** Rollbacks the JMS transaction.
     * NOTE: This instance should not be used after a rollback();
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void rollback() throws FrameworkException, ApplicationExceptions {
        if (m_session != null) {
            try {
                m_session.rollback();
                if (log.isDebugEnabled())
                    log.debug("JMS Session rolled back");
                close();
            } catch (JMSException e) {
                log.error("Error in rollback", e);
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.ROLLBACK_ERROR, null, e);
            }
        }
    }

    /**
     * Gets the sender
     * This is not used in the JMS implementation
     *
     * @return the sender
     */
    @Override
    public ISender getSender() {
        return null;
    }

    /**
     * Sets the sender
     *
     * @param sender the sender
     */
    @Override
    public void setSender(ISender sender) {
        // This is not used in the JMS implementation
    }

    /**
     * {@inheritDoc}
     */
    public void prepareDeletesForCommit(Collection deletes) {
        // No implementation needed here.
    }

    /** Creates a JMS session.
     * @param connection the JMS connection.
     * @param transacted indicates if the session is transactional, or otherwise.
     * @throws FrameworkException in case any internal error occurs.
     * @return a JMS Session.
     */
    static Session obtainSession(Connection connection, boolean transacted) throws FrameworkException {
        try {
            return connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            log.error("Error in creating a JMS Session", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.SESSION_ERROR, null, e);
        }
    }

    /**
     * Converts the input queue name to conform to rules of the JMS Provider. It
     * appends the 'queue/' prefix to the input.
     *
     * @param queueName
     *          the queue name.
     * @return the converted queue name.
     */
    public static String toQueueName(String queueName) {
        return queueName == null || queueName.startsWith(QUEUE_JNDI_PREFIX) ? queueName
            : QUEUE_JNDI_PREFIX + queueName;
    }

    /**
     * Converts the input topic name to conform to rules of the JMS Provider. It
     * appends the 'topic/' prefix to the input.
     *
     * @param topicName
     *          the topic name.
     * @return the converted topic name.
     */
    public static String toTopicName(String topicName) {
        return topicName == null || topicName.startsWith(TOPIC_JNDI_PREFIX) ? topicName
            : TOPIC_JNDI_PREFIX + topicName;
    }

    /** Looks up the input destination using JNDI.
     * @param destinationName the Destination to write to.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the JMS Destination.
     */
    static Destination obtainDestination(String destinationName) throws FrameworkException, ApplicationExceptions {
        boolean isQueue = ConfigurationService.getInstance().getQueueInfo(destinationName) != null;
        destinationName = isQueue ? toQueueName(destinationName) : toTopicName(destinationName);
        try {
            InitialContext context = InitialContextFactrory.obtainInitialContext();
            return (Destination) context.lookup(destinationName);
        } catch (NamingException e) {
            log.error("Error in obtaining the Destination object from JNDI for " + destinationName, e);
            String errorCode = isQueue ? JaffaMessagingFrameworkException.QUEUE_NOT_FOUND : JaffaMessagingFrameworkException.TOPIC_NOT_FOUND;
            throw new JaffaMessagingFrameworkException(errorCode, null, e);
        }
    }

    /** Looks up the input queue using JNDI.
     * @param queueName the Queue to write to.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the JMS Queue.
     */
    public static Queue obtainQueue(String queueName) throws FrameworkException, ApplicationExceptions {
        String jndi_name = null;
        try {
            jndi_name = toQueueName(queueName);
            return (Queue) InitialContextFactrory.obtainInitialContext().lookup(jndi_name);
        } catch (NamingException e) {
            log.error("Error in obtaining the Queue object from JNDI for " + jndi_name, e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.QUEUE_NOT_FOUND, new Object[]{jndi_name}, e);
        }
    }

    /** Writes a JMS Message to the destination, as defined in the configuration file.
     * @param session the JMS Session.
     * @param message the JMS Message.
     * @param destinationName the Destination to write to.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    static void send(Session session, Message message, String destinationName) throws FrameworkException, ApplicationExceptions {
        try {
            Destination destination = obtainDestination(destinationName);
            MessageProducer producer = session.createProducer(destination);
            int messagePriority = message.getJMSPriority();
            if (messagePriority != producer.getPriority() && messagePriority >= 0 && messagePriority <= 9)
                producer.setPriority(messagePriority);
            producer.send(message);
            if (log.isDebugEnabled())
                log.debug("Sent message " + message + " to " + destinationName);
        } catch (JMSException e) {
            log.error("Error in sending a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.SEND_ERROR, null, e);
        }
    }

    /** Clones the input message, so that it can redirected to the ErrorQueue.
     * @param session the JMS Session.
     * @param input the Message.
     * @return the cloned message.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    static Message cloneMessage(Session session, Message input) throws FrameworkException, ApplicationExceptions {
        try {
            Message newMessage = null;
            // Support only the TextMessage for now
            if (input instanceof TextMessage) {
                String contents = ((TextMessage) input).getText();
                newMessage = session.createTextMessage(contents);

                // set the properties
                for (final Enumeration<?> e = input.getPropertyNames(); e.hasMoreElements();) {
                    String propertyName = (String) e.nextElement();
                    try {
                        newMessage.setObjectProperty(propertyName, input.getObjectProperty(propertyName));
                    } catch (JMSException ex) {
                        // This may happen when a JMS provider inserts a JMS* property in the Message and routes it to a remote consumer
                        // Ignore the exception
                        if (log.isDebugEnabled())
                            log.debug("Exception thrown while setting property: " + propertyName + ". Will ignore it and continue processing.", ex);
                    }
                }

                // set the JMS properties
                newMessage.setJMSCorrelationID(input.getJMSCorrelationID());
                //newMessage.setJMSCorrelationIDAsBytes(input.getJMSCorrelationIDAsBytes());
                newMessage.setJMSDeliveryMode(input.getJMSDeliveryMode());
                newMessage.setJMSDestination(input.getJMSDestination());
                newMessage.setJMSExpiration(input.getJMSExpiration());
                //newMessage.setJMSMessageID(input.getJMSMessageID());
                newMessage.setJMSPriority(input.getJMSPriority());
                //newMessage.setJMSRedelivered(input.getJMSRedelivered());
                //newMessage.setJMSReplyTo(input.getJMSReplyTo());
                newMessage.setJMSTimestamp(input.getJMSTimestamp());
                newMessage.setJMSType(input.getJMSType());

                // Retain the original messageId in the header, so that related BusinessEventLog records can still be retrieved
                String originalMessageId = input.getStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID);
                if (originalMessageId == null)
                    originalMessageId = input.getJMSMessageID();
                newMessage.setStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID, originalMessageId);
            }
            return newMessage;
        } catch (JMSException e) {
            log.error("Error in copying a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.CLONE_ERROR, null, e);
        }
    }

    /** Throws an UnsupportedOperationException if this instance is reused after a commit/rollback.
     */
    private void checkState() {
        if (m_session == null)
            throw new UnsupportedOperationException("This object has been committed/rolled-back. It cannot be used anymore");
    }

    /** Closes the session.
     */
    private void close() {
        if (m_session != null) {
            try {
                m_session.close();
                m_session = null;
                m_messages = null;
                if (log.isDebugEnabled())
                    log.debug("JMS Session closed");
            } catch (JMSException e) {
                log.warn("Error in closing a JMS Session", e);
            }
        }
    }

    /**
     * Runs the input payload through a series of MessageFilters, as defined in the configuration file.
     * The filters may leave the input payload as-is, modify it, add new payload(s) or replace it.
     * The filters may even choose to return an empty List, thereby stopping the submission of any message to the messaging system.
     * @param payload Any serializable object.
     * @return a List of payloads. An empty List may be returned to stop further processing.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static List<Object> applyFilters(final Object payload) throws FrameworkException, ApplicationExceptions {
        final List<Object> payloads = new LinkedList<Object>();
        payloads.add(payload);
        final List<MessageFilter> messageFilters = ConfigurationService.getInstance().getMessageFilters();
        if (messageFilters != null) {
            for (MessageFilter messageFilter : messageFilters) {
                IMessageFilter filterImpl;
                try {
                    if (log.isDebugEnabled())
                        log.debug("Instantiating MessageFilter (name=" + messageFilter.getFilterName() + ", class=" + messageFilter.getFilterClass() + ')');
                    filterImpl = (IMessageFilter) Class.forName(messageFilter.getFilterClass()).newInstance();
                } catch (Exception e) {
                    log.error("Error in instantiating MessageFilter (name=" + messageFilter.getFilterName() + ", class=" + messageFilter.getFilterClass() + ')', e);
                    throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_FILTER_ERROR, new Object[]{messageFilter.getFilterName(), messageFilter.getFilterClass()}, e);
                }
                if (log.isDebugEnabled())
                    log.debug("PayloadsList before applying filter: " + payloads);
                filterImpl.doFilter(payloads);
                if (log.isDebugEnabled())
                    log.debug("PayloadsList after applying filter: " + payloads);
            }
        }
        return payloads;
    }

    /**
     * For each input payload, writes a JMS Message to the destination, as defined in the configuration file.
     * @param inst An instance of this class. If passed, then the newly created Message will simply be cached to be sent later. Else it will be sent right away.
     * @param payloads A List of serializable objects.
     * @param userId A header element in the JMS message will be set to this value.
     * @param scheduledTaskId A header element in the JMS message will be set to this value.
     * @return a comma-separated list of MessageIds, one for each payload.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static String send(JmsClientHelper inst, List<Object> payloads, String userId, String scheduledTaskId) throws FrameworkException, ApplicationExceptions {
        StringBuilder output = null;
        if (payloads != null) {
            for (Object payload : payloads) {
                if (output == null)
                    output = new StringBuilder();
                else
                    output.append(',');
                output.append(send(inst, payload, userId, scheduledTaskId));
            }
        }
        return output != null ? output.toString() : null;
    }

    /** Writes a JMS Message to the destination, as defined in the configuration file.
     * @param inst An instance of this class. If passed, then the newly created Message will simply be cached to be sent later. Else it will be sent right away.
     * @param payload Any serializable object.
     * @param userId A header element in the JMS message will be set to this value.
     * @param scheduledTaskId A header element in the JMS message will be set to this value.
     * @return the MessageId.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static String send(JmsClientHelper inst, Object payload, String userId, String scheduledTaskId) throws FrameworkException, ApplicationExceptions {
        Session session = inst != null ? inst.m_session : null;
        try {
            if (log.isDebugEnabled())
                log.debug("Input to send is userId=" + userId + ", scheduledTaskId=" + scheduledTaskId + ", payload=" + payload);

            if (userId == null) {
                // If the input userId is null, then use SecurityManager.getPrincipal().getName()
                if (SecurityManager.getPrincipal() != null)
                    userId = SecurityManager.getPrincipal().getName();
            } 
            
            if(userId == null || userId.length() ==0){
                log.error("Error in locating the user id info for " + payload.getClass().getName());
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.USERID_NOT_FOUND, new Object[]{payload.
                            getClass().getName()
                        });
            }

            // Reads the MessageInfo for the className of the input payload
            MessageInfo messageInfo = null;
            try {
                messageInfo = ConfigurationService.getInstance().getMessageInfo(payload.getClass().getName());
                if (messageInfo == null)
                    throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[]{payload.
                                getClass().getName()
                            });
            } catch (ClassNotFoundException e) {
                log.error("Error in locating the Message info for " + payload.getClass().getName(), e);
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[]{payload.
                            getClass().getName()
                        }, e);
            }

            // Invokes the LockingService, to ensure that no locks exist if specified in the MessageInfo
            LockingService.checkLock(payload, messageInfo);

            // Process the payload if RULE_POST_IMMEDIATE is true
            Boolean postImmediate = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(RULE_POST_IMMEDIATE));

            if (postImmediate != null && postImmediate.booleanValue()) {

                if (log.isDebugEnabled())
                  log.debug("jaffa.messaging.postImmediate=true");

                if (log.isDebugEnabled())
                    log.debug("Rule '" + RULE_POST_IMMEDIATE + "' is enabled. Will process the payload immediately");

                // Process the payload
                JaffaMessageConsumer.processPayload(messageInfo, payload, userId, scheduledTaskId, null);

                // Return a null since no message has been created.
                return null;
            } else {

                if (log.isDebugEnabled())
                  log.debug("jaffa.messaging.postImmediate=false");

                // Create a Session, if not passed
                if (inst == null) {
                    final Connection connection = JaffaConnectionFactory.obtainConnection();
                    session = obtainSession(connection, false);
                }

                // Marshals the payload into XML
                String xml = marshalPayload(payload);

                // Obtain the destination from the payload, if it implements IHasDestinationName.
                // Fallback to the static message config, if the payload does not provide any destination.
                String queueName = payload instanceof IHasDestinationName ? ((IHasDestinationName) payload).getQueueName() : null;

                if (log.isDebugEnabled())
                  log.debug("Destination from the payload: queueName=" + queueName);

                if (queueName == null) {
                  queueName = messageInfo.getQueueName();

                  if (log.isDebugEnabled())
                    log.debug("Fallback to the static message config: queueName=" + queueName);
                }

                String topicName = payload instanceof IHasDestinationName ? ((IHasDestinationName) payload).getTopicName() : null;
                if (topicName == null)
                    topicName = messageInfo.getTopicName();

                // Creates the JMS Message and sets the standard properties
                Message message = session.createTextMessage(xml);
                message.setStringProperty(JmsBrowser.HEADER_USER_ID, userId);
                message.setStringProperty(JmsBrowser.HEADER_SCHEDULED_TASK_ID, scheduledTaskId);
                message.setStringProperty(JmsBrowser.HEADER_DATA_BEAN_CLASS_NAME, payload.getClass().getName());
                if (queueName != null)
                    message.setStringProperty(JmsBrowser.HEADER_ORIGINAL_QUEUE_NAME, queueName);

                // Sets the header elements as defined in the configuration file.
                if (messageInfo.getHeader() != null && messageInfo.getHeader().
                        getParam() != null) {
                    for (Param param : messageInfo.getHeader().getParam()) {
                        Object value = InitialContextFactrory.obtainParamValue(param, payload);
                        message.setObjectProperty(param.getName(), value);
                    }
                }

                // Sets additional header elements if the payload implements the IHasHeaderParams interface
                if (payload instanceof IHasHeaderParams) {
                    HeaderParam[] headerParams = ((IHasHeaderParams) payload).getHeaderParams();
                    if (headerParams != null) {
                        for (HeaderParam headerParam : headerParams)
                            message.setStringProperty(headerParam.getName(), headerParam.getValue());
                    }
                }

                // Sets additional header elements if the payload has header params.
                if (payload instanceof EventMessage) {
                    List<org.jaffa.soa.services.event.HeaderParam> headerParams = ((EventMessage) payload).getHeaderParams();
                    if (headerParams != null) {
                        for (org.jaffa.soa.services.event.HeaderParam headerParam : headerParams) {
                            message.setStringProperty(headerParam.getName(), headerParam.getValue());
                        }
                    }
                }

                if ((queueName == null || 0==queueName.length()) && (topicName == null || 0==topicName.length())){
                  return message.getJMSMessageID();
                }
                
                // Sends the Message for a local Session only. The Message will be sent later when part of a transaction
                if (inst == null) {
                    if (queueName != null && 0<queueName.length())
                        send(session, message, queueName);
                    if (topicName != null && 0<topicName.length())
                        send(session, message, topicName);
                } else {
                    if (inst.m_messages == null)
                        inst.m_messages = new LinkedHashMap<Message, String>();
                    if (queueName != null && 0<queueName.length())
                        inst.m_messages.put(message, queueName);
                    if (topicName != null && 0<topicName.length())
                        inst.m_messages.put(message, topicName);
                }

                // Return the message id
                return message.getJMSMessageID();
            }
        } catch (JMSException e) {
            log.error("Error in sending the message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.SEND_ERROR, null, e);
        } finally {
            if (inst == null && session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.warn("Error in closing a JMS Session", e);
                }
            }
        }
    }

    /** Marshals the payload into XML using JAXB
     * @param payload Any serializable object.
     * @throws FrameworkException Indicates some system error.
     * @return the XML representation of the payload.
     */
    private static String marshalPayload(Object payload) throws FrameworkException {
        try {
            return JAXBHelper.marshalPayload(payload);
        } catch (JAXBException e) {
            log.error("Error in JAXB marshalling", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.PAYLOAD_ERROR, null, e);
        }
    }
    
    /**
     * Sends JMS Text Message message to the Queue queueName
     * @param queueName
     * @param message
     */
    public void sendTextMessage(final String queueName, final String message) {
        sendTextMessage(queueName, message, null);
    }
    
    /**
     * Sends JMS Text Message message with properties to the Queue queueName
     * @param queueName
     * @param message
     * @param properties
     */
    public void sendTextMessage(final String queueName, final String message, final Properties properties) {
        Session session = null;
        try {
            final Connection connection = JaffaConnectionFactory.obtainConnection();
            session = obtainSession(connection, false);
            // Create the destination (Topic or Queue)
            final Destination destination = session.createQueue(queueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            final MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            final TextMessage textMessage = session.createTextMessage(message);
            
            // Populate message properties
            if(properties != null) {
                for(String key : properties.stringPropertyNames()) {
                    textMessage.setStringProperty(key, properties.getProperty(key));
                }
            }

            // Tell the producer to send the message
            producer.send(textMessage);

        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.warn("Error in closing a JMS Session", e);
                }
            }
        }
    }
	
	 /**
     * Sends a serializable object in a JMS message.
     * @param queueOrTopicName name of queue or topic to publish to
     * @param message any serializable object
     * @param postToTopic send true to publish message to a topic, false to 
     * send message to queue
     * @param properties optional set of name/value pairs to be added to the 
     * message as properties. Send null if no props are required.
     */
    public void sendObjectMessage(String queueOrTopicName, Serializable message, 
            boolean postToTopic, Properties properties){
        log.debug("sendObjectMessage: queueOrTopicName: " + queueOrTopicName + 
                " message: " + message);
        if(queueOrTopicName == null || message == null){
            return;
        }
        Session session = null;
        try {
            final Connection connection = JaffaConnectionFactory.obtainConnection();
            session = obtainSession(connection, false);
            // Create the destination (Topic or Queue)
            Destination destination = null;
            if(postToTopic){
                destination = session.createTopic(queueOrTopicName);
                log.debug("sendObjectMessage: created topic " + destination);
            }else{
                destination = session.createQueue(queueOrTopicName);
                log.debug("sendObjectMessage: created queue " + destination);
            }
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            ObjectMessage objectMessage = session.createObjectMessage(message);
            populateMessageProperties(objectMessage, properties);
            log.debug("sendObjectMessage: created object message");
            producer.send(objectMessage);
            log.debug("sendObjectMessage: sent object message");
        } catch (Exception e) {
            log.error("problem sending JMS message", e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    log.warn("Error in closing a JMS Session", e);
                }
            }
        }
    }
    
    /**
     * Add any properties (name/value pairs) to the message as string properties
     * @param message properties will be added here
     * @param props the set of additional properties to be added to message.
     * NOTE: If values other than strings need to be added to Messages this 
     * method can be refactored to support.
     */
    private void populateMessageProperties(Message message, Properties props){
        if(props == null || message == null){
            return;
        }

        Set<String> propNames = props.stringPropertyNames();
        try{
            for(String propName : propNames){
                String value = props.getProperty(propName);
                if(value != null){
                    message.setStringProperty(propName, value);
                }
            }
        }catch(JMSException jmse){
            log.error("Problem added properties to Message", jmse);
        }
    }
}