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

package org.jaffa.modules.messaging.services;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.common.IMessageHandler;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.messaging.services.configdomain.Param;
import org.jaffa.modules.messaging.services.configdomain.QueueInfo;
import org.jaffa.persistence.UOW;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.JAXBHelper;

/** A utility class for browsing queues.
 */
public class JmsBrowser {
    
    private static final Logger log = Logger.getLogger(JmsBrowser.class);
    
    
    /** A constant used to identify the ID for the Message. The JMS Provider sets this element. Can be used when searching for specific messages. */
    public static final String HEADER_JMS_MESSAGE_ID = "JMSMessageID";
    
    /** A constant used to set a Header element with the userId, when writing a Message to a Queue. */
    public static final String HEADER_USER_ID = "jaffa_userId";
    
    /** A constant used to set a Header element with the scheduledTaskId, when writing a Message to a Queue. */
    public static final String HEADER_SCHEDULED_TASK_ID = "jaffa_scheduledTaskId";
    
    /** A constant used to set a Header element with the className of the payload, when writing a Message to a Queue. */
    public static final String HEADER_DATA_BEAN_CLASS_NAME = "jaffa_dataBeanClassName";
    
    /** A constant used to set a Header element with the original queueName, when writing an Error Message to the ErrorQueue. */
    public static final String HEADER_ORIGINAL_QUEUE_NAME = "jaffa_originalQueueName";
    
    /** A constant used to set a Header element with the error details, when writing an Error Message to the ErrorQueue. */
    public static final String HEADER_ERROR_DETAILS = "jaffa_errorDetails";
    
    /** A constant used to set a Header element with the original messageId, when writing an Error Message to the ErrorQueue, or when changing the priority or resubmitting a message. */
    public static final String HEADER_ORIGINAL_MESSAGE_ID = "jaffa_originalMessageId";

    /** Returns an array of queue names, as defined in the configuration file.
     * The array will contain the accessible queues only.
     * The error queue will not be included in this list.
     * @return an array of queue names, as defined in the configuration file.
     */
    public static String[] getAccessibleQueueNames() {
        String[] queueNames = ConfigurationService.getInstance().getQueueNames();
        if (queueNames != null && queueNames.length > 0) {
            List<String> accessibleQueueNames = new LinkedList<String>();
            for (String queueName : queueNames) {
                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
                if (!queueInfo.isErrorQueue() && hasBrowseQueueAccess(queueInfo))
                    accessibleQueueNames.add(queueName);
                else {
                    if (log.isDebugEnabled() && !queueInfo.isErrorQueue())
                        log.debug("No browseQueue access to " + queueName);
                }
            }
            queueNames = accessibleQueueNames.toArray(new String[accessibleQueueNames.size()]);
        }
        return queueNames;
    }

    /** Returns the unconsumed messages in the input queue.
     * @param queueName the queue name.
     * @return the unconsumed messages in the input queue.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static Message[] getPendingMessages(String queueName) throws FrameworkException, ApplicationExceptions {
        return getPendingMessages(queueName, null);
    }
    
    /** Returns the unconsumed messages in the input queue.
     *
     * @param queueName the queue name.
     * @param filter used for searching within the header information of the retrieved messages. The syntax for the filter will be based on a subset of the SQL92 conditional expression syntax.
     * @return the unconsumed messages in the input queue.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static Message[] getPendingMessages(String queueName, String filter) throws FrameworkException, ApplicationExceptions {
        Session session = null;
        try {
            List<Message> messages = new LinkedList<Message>();
            QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
            if (hasBrowseQueueAccess(queueInfo)) {
                // Obtain a Connection with the JMS provider
                Connection connection = JaffaConnectionFactory.obtainConnection();
                
                // Creates a Session from the Connection
                session = JmsClientHelper.obtainSession(connection, false);
                
                // Creates a QueueBrowser from the Session, using the filter, if supplied
                QueueBrowser qb = session.createBrowser(JmsClientHelper.obtainQueue(queueName), filter);
                if (log.isDebugEnabled())
                    log.debug("QueueBrowser created for " + queueName + " with filter " + filter);
                
                // Check if the user can access all messages in the queue
                boolean browseAllMessagesAccess = hasBrowseAllMessagesAccess(queueInfo);
                if (log.isDebugEnabled())
                    log.debug("browseAllMessages access to " + queueName + " is " + browseAllMessagesAccess);
                
                for (Enumeration e = qb.getEnumeration(); e.hasMoreElements(); ) {
                    Message message = (Message) e.nextElement();
                    // The user should have access to browse all messages in this queue, or should have been the creator of the message.
                    // If this is an error queue, perform the check against the original queue
                    if (queueInfo.isErrorQueue() && message.getStringProperty(HEADER_ORIGINAL_QUEUE_NAME) != null) {
                        String originalQueueName = message.getStringProperty(HEADER_ORIGINAL_QUEUE_NAME);
                        if (hasBrowseAllMessagesAccess(originalQueueName) || isMessageOwner(message))
                            messages.add(message);
                    } else if (browseAllMessagesAccess || isMessageOwner(message)) {
                        messages.add(message);
                    }
                }
                
                // Close the QueueBrowser
                qb.close();
            } else {
                if (log.isDebugEnabled())
                    log.debug("No browseQueue access to " + queueName);
            }
            
            // Returns an array of Messages from the QueueBrowser's enumeration
            Message[] output = messages.toArray(new Message[messages.size()]);
            if (log.isDebugEnabled()) {
                StringBuilder buf = new StringBuilder("<output>");
                for (Message m : output)
                    buf.append("<message>").append(m).append("</message>");
                buf.append("</output>");
                log.debug(output.length + " messages are being returned: " + buf.toString());
            }
            return output;
        } catch (InvalidSelectorException e) {
            if (log.isDebugEnabled())
                log.debug("Invalid filter: \"" + filter + '"', e);
            throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.INVALID_SELECTOR));
        } catch (JMSException e) {
            log.error("Error in reading JMS Messages", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.BROWSE_ERROR, new Object[] {queueName}, e);
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

    /** Returns the unconsumed message in the input queue having the given messageId.
     * @param queueName the queue name.
     * @param messageId the message Id.
     * @return the unconsumed message in the input queue having the given messageId.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static Message getMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions {
        StringBuilder filter = new StringBuilder(HEADER_JMS_MESSAGE_ID)
        .append("='")
        .append(messageId)
        .append('\'');
        Message[] messages = getPendingMessages(queueName, filter.toString());
        if (messages != null && messages.length > 0)
            return messages[0];
        else
            throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.MESSAGE_NOT_FOUND));
    }

    /** Deletes the message from the given queue.
     * The deletion is achieved by creating a temporary consumer for the message.
     *
     * @param queueName the queue name.
     * @param messageId the message Id.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions {
        Session session = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Input to deleteMessage: queueName=" + queueName + ", messageId=" + messageId);
            
            Message message = getMessage(queueName, messageId);
            
            // Perform security check on the original queue, if present, else perform it on the input queue
            String originalQueueName = message.getStringProperty(HEADER_ORIGINAL_QUEUE_NAME);
            if (originalQueueName == null)
                originalQueueName = queueName;
            if (!hasAdminMessageAccess(originalQueueName))
                throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.NO_ADMIN_MESSAGE_ACESSS, new Object[] {originalQueueName}));
            
            //Invoke Message Handler class to perform onDelete process
            invokeHandler(message,"onDelete");
            // Obtain a Connection with the JMS provider
            final Connection connection = JaffaConnectionFactory.obtainConnection();
            
            // Creates a Session from the Connection
            session = JmsClientHelper.obtainSession(connection, false);
            
            // Consume the Message to delete it from the Queue
            consumeMessage(session, message, queueName);
            
            session.close();
            
            if (log.isDebugEnabled())
                log.debug("Deleted message " + messageId);
        } catch (JMSException e) {
            log.error("Error in deleting a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.DELETE_ERROR, null, e);
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
     * Invokes the intended handler. This Handler must implement the IMessageHandler Interface in order to be invoked.
     * 
     * @param message
     * @param methodName
     * @throws JaffaMessagingFrameworkException
     */
    private static void invokeHandler(Message message, String methodName) throws JaffaMessagingFrameworkException {
    	
    	UOW uow = null;
    	try {
	    	// Reads the MessageConfig from the ConfigurationService, based on the dataBeanClassName
	        String dataBeanClassName = message.getStringProperty(JmsBrowser.HEADER_DATA_BEAN_CLASS_NAME);
	        MessageInfo messageInfo = ConfigurationService.getInstance().getMessageInfo(dataBeanClassName);
	        if (messageInfo == null)
	            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[]{dataBeanClassName});
	
	        // Obtain the handlerClass
            Class handlerClass = Class.forName(messageInfo.getToClass());
            if(IMessageHandler.class.isAssignableFrom(handlerClass)) {
	            // Unmarshals the Message payload into a dataBean using the dataBeanClassName
		        Object payload = JAXBHelper.unmarshalPayload(obtainMessageContents(message), dataBeanClassName);
		        if (log.isDebugEnabled())
		            log.debug("Obtained payload for the dataBean " + dataBeanClassName + '\n' + payload);
		        
		        Method handlerMethod = null;
	            Object handlerObject = null;
	            // Obtain the handler method
            	try {
            		handlerMethod = handlerClass.getMethod(methodName, new Class[]{UOW.class,Map.class, Object.class});
	            } catch (NoSuchMethodException e) {
	                // Note that the payload could be a subclass of the argument that the handler method expects
	                // Hence use the dataBeanClass specified in the messageInfo to get the appropriate handlerMethod
	                if (log.isDebugEnabled())
	                    log.debug(methodName + " method not found in " + handlerClass.getName());
	                
	                return;
	            }
	            handlerObject = handlerClass.newInstance();
	            uow = new UOW();
	            
	            Map<String,String> headerMap = new HashMap<String,String>();
	            // Sets the header elements as defined in the configuration file.
                if (messageInfo.getHeader() != null && messageInfo.getHeader().
                        getParam() != null) {
                    for (Param param : messageInfo.getHeader().getParam()) {
                        String headerValue = (String)message.getObjectProperty(param.getName());
                        headerMap.put(param.getName(), headerValue);
                    }
                }
	            handlerMethod.invoke(handlerObject, new Object[]{uow,headerMap,payload});
	            uow.commit();
            }
    	}
        catch (Exception e) {
            // Just log the error
            log.error("Exception thrown while deleting the message. Message was: " + message, e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.DELETE_ERROR, null, e);
        } finally {
        	if(uow!=null) {
        		try {
        			uow.rollback();
        		} catch(Exception e) {
        			throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.DELETE_ERROR, null, e);
        		}
        	}
        }
    	
    }
    
    
    /** Obtains the contents of the input message.
     * This currently supports a TextMessage only.
     * A null will be returned for all other message types.
     * @param aMessage the Message.
     * @throws JMSException if any error occurs in reading the input message.
     * @return the contents of the input message.
     */
    private static String obtainMessageContents(Message aMessage) throws JMSException {
        String contents = null;
        if (aMessage instanceof TextMessage)
            contents = ((TextMessage) aMessage).getText();
        return contents;
    }
    
    /** Resubmits the message, which is currently in the error queue.
     * It is achieved by creating a temporary consumer for the original message to delete it,
     * and by sending a copy of the original message to the original queue.
     * NOTE: The two processes happen in the same JMS transaction.
     *
     * @param queueName the queue name.
     * @param messageId the message Id.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the MessageId of the copy.
     */
    public static String resubmitMessage(String queueName, String messageId) throws FrameworkException, ApplicationExceptions {
        Session session = null;
        String output = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Input to resubmitMessage: queueName=" + queueName + ", messageId=" + messageId);
            
            // Find the message
            Message message = getMessage(queueName, messageId);
            
            // Perform security check on the original queue, if present, else perform it on the input queue
            String originalQueueName = message.getStringProperty(HEADER_ORIGINAL_QUEUE_NAME);
            if (originalQueueName == null)
                originalQueueName = queueName;
            if (!hasAdminMessageAccess(originalQueueName))
                throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.NO_ADMIN_MESSAGE_ACESSS, new Object[] {originalQueueName}));
            
            // Obtain a Connection with the JMS provider
            final Connection connection = JaffaConnectionFactory.obtainConnection();
            
            // Creates a Session from the Connection
            session = JmsClientHelper.obtainSession(connection, true);
            
            // Clone the Message
            Message newMessage = JmsClientHelper.cloneMessage(session, message);
            
            // Send the cloned message to the original Queue
            if (log.isDebugEnabled())
                log.debug("Sending cloned message to the original queue " + originalQueueName);
            JmsClientHelper.send(session, newMessage, originalQueueName);
            output = newMessage.getJMSMessageID();
            
            // Consume the Message to delete it from the Queue
            consumeMessage(session, message, queueName);
            
            // Commit the session and close it
            session.commit();
            session.close();
            
            if (log.isDebugEnabled())
                log.debug("Deleted the original message from the queue " + queueName);
            
            if (log.isDebugEnabled())
                log.debug("Output from resubmit " + output);
            return output;
        } catch (JMSException e) {
            log.error("Error in resubmitting a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.RESUBMIT_ERROR, null, e);
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
    
    /** Changes the priority of the message.
     * It is achieved by creating a temporary consumer for the original message to delete it,
     * and by sending a copy of the original message with the changed priority to the same queue.
     * NOTE: The two processes happen in the same JMS transaction.
     *
     * @param queueName the queue name.
     * @param messageId the message Id.
     * @param newPriority the new priority of the message. The JMS API defines ten levels of priority value, with 0 as the lowest priority and 9 as the highest.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     * @return the MessageId of the copy.
     */
    public static String changeMessagePriority(String queueName, String messageId, int newPriority) throws FrameworkException, ApplicationExceptions {
        Session session = null;
        String output = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Input to changeMessagePriority: queueName=" + queueName + ", messageId=" + messageId + ", newPriority=" + newPriority);
            
            // find the message
            Message message = getMessage(queueName, messageId);
            
            // Perform security check on the original queue, if present, else perform it on the input queue
            String originalQueueName = message.getStringProperty(HEADER_ORIGINAL_QUEUE_NAME);
            if (originalQueueName == null)
                originalQueueName = queueName;
            if (!hasChangePriorityAccess(originalQueueName))
                throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.NO_CHANGE_PRIORITY_ACCESS, new Object[] {originalQueueName}));
            
            // change the priority
            if (message.getJMSPriority() != newPriority) {
//                if (log.isDebugEnabled())
//                    log.debug("Changing the priority from " + message.getJMSPriority() + " to " + newPriority);
//                message.setJMSPriority(newPriority);
//                output = messageId;
                
                // Obtain a Connection with the JMS provider
                final Connection connection = JaffaConnectionFactory.obtainConnection();
                
                // Creates a Session from the Connection
                session = JmsClientHelper.obtainSession(connection, true);
                
                // Clone the Message
                Message newMessage = JmsClientHelper.cloneMessage(session, message);
                
                if (log.isDebugEnabled())
                    log.debug("Changing the priority from " + newMessage.getJMSPriority() + " to " + newPriority);
                newMessage.setJMSPriority(newPriority);
                
                // Send the cloned message to the Queue
                if (log.isDebugEnabled())
                    log.debug("Sending cloned message with new priority " + newMessage.getJMSPriority() + " to the queue " + queueName);
                JmsClientHelper.send(session, newMessage, queueName);
                output = newMessage.getJMSMessageID();
                
                // Consume the existing Message to delete it from the Queue
                consumeMessage(session, message, queueName);
                
                // Commit the session and close it
                session.commit();
                session.close();
                
                if (log.isDebugEnabled())
                    log.debug("Deleted the original message from the queue " + queueName);
            } else {
                if (log.isDebugEnabled())
                    log.debug("The new priority is no different from the existing priority. No changes made");
                output = messageId;
            }
            if (log.isDebugEnabled())
                log.debug("Output from changePriority " + output);
            return output;
        } catch (JMSException e) {
            log.error("Error in changing priority of a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.CHANGE_PRIORITY_ERROR, null, e);
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

    /** Returns true if the current authenticated user is in a role, which has access to browse the input queue.
     * @param queueName the queue name.
     * @return true if the current authenticated user is in a role, which has access to browse the input queue.
     */
    public static boolean hasBrowseQueueAccess(String queueName) {
        QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
        return queueInfo != null && hasBrowseQueueAccess(queueInfo);
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to browse All Messages in the input queue.
     * @param queueName the queue name.
     * @return true if the current authenticated user is in a role, which has access to browse All Messages in the input queue.
     */
    public static boolean hasBrowseAllMessagesAccess(String queueName) {
        QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
        return queueInfo != null && hasBrowseAllMessagesAccess(queueInfo);
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to Delete/Resubmit Messages in the input queue.
     * @param queueName the queue name.
     * @return true if the current authenticated user is in a role, which has access to Delete/Resubmit Messages in the input queue.
     */
    public static boolean hasAdminMessageAccess(String queueName) {
        QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
        return queueInfo != null && hasAdminMessageAccess(queueInfo);
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to Change Priority of Messages in the input queue.
     * @param queueName the queue name.
     * @return true if the current authenticated user is in a role, which has access to Change Priority of Messages in the input queue.
     */
    public static boolean hasChangePriorityAccess(String queueName) {
        QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(queueName);
        return queueInfo != null && hasChangePriorityAccess(queueInfo);
    }
    
    
    
    
    /** Returns true if the current authenticated user is in a role, which has access to browse the input queue.
     * @param queueInfo the QueueInfo for the queue.
     * @return true if the current authenticated user is in a role, which has access to browse the input queue.
     */
    private static boolean hasBrowseQueueAccess(QueueInfo queueInfo) {
        return queueInfo != null && (queueInfo.getSecurity() == null || hasFunctionAccess(queueInfo.getSecurity().getBrowseQueue()));
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to browse All Messages in the input queue.
     * @param queueInfo the QueueInfo for the queue.
     * @return true if the current authenticated user is in a role, which has access to browse All Messages in the input queue.
     */
    private static boolean hasBrowseAllMessagesAccess(QueueInfo queueInfo) {
        return queueInfo != null && (queueInfo.getSecurity() == null || hasFunctionAccess(queueInfo.getSecurity().getBrowseAllMessages()));
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to Delete/Resubmit Messages in the input queue.
     * @param queueInfo the QueueInfo for the queue.
     * @return true if the current authenticated user is in a role, which has access to Delete/Resubmit Messages in the input queue.
     */
    private static boolean hasAdminMessageAccess(QueueInfo queueInfo) {
        return queueInfo != null && (queueInfo.getSecurity() == null || hasFunctionAccess(queueInfo.getSecurity().getAdminMessage()));
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to Change Priority of Messages in the input queue.
     * @param queueInfo the QueueInfo for the queue.
     * @return true if the current authenticated user is in a role, which has access to Change Priority of Messages in the input queue.
     */
    private static boolean hasChangePriorityAccess(QueueInfo queueInfo) {
        return queueInfo != null && (queueInfo.getSecurity() == null || hasFunctionAccess(queueInfo.getSecurity().getChangePriority()));
    }
    
    /** Returns true if the current authenticated user is in a role, which has access to the input business function.
     * A true will be returned if the input is null.
     * A false will be returned if the input is empty.
     * @param functionName the business function.
     * @return true if the current authenticated user is in a role, which has access to the input business function.
     */
    private static boolean hasFunctionAccess(String functionName) {
        boolean access;
        if (functionName == null)
            access = true;
        else if (functionName.trim().length() == 0)
            access = false;
        else
            access = SecurityManager.checkFunctionAccess(functionName);
        return access;
    }
    
    /** Returns true if the input Message was created by the currently authenticated user.
     * @param message the JMS Message.
     * @return true if the input Message was created by the currently authenticated user.
     */
    private static boolean isMessageOwner(Message message) throws JMSException {
        String currentUserId = SecurityManager.getPrincipal() != null ? SecurityManager.getPrincipal().getName() : null;
        String createdBy = message.getStringProperty(HEADER_USER_ID);
        boolean result = currentUserId != null && currentUserId.equals(createdBy);
        if (!result && log.isDebugEnabled())
            log.debug(currentUserId + " is not owner of the message " + message.getJMSMessageID() + ", which was created by " + createdBy);
        return result;
    }
    
    /** Deletes the input Message from the given Queue, by creating a temporary consumer for that Message
     * @param session the JMS Session.
     * @param message the JMS Message.
     * @param queueName the Queue to consume from.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    static void consumeMessage(Session session, Message message, String queueName) throws FrameworkException, ApplicationExceptions {
        try {
            // Creates a consumer on the session for the given queueName, and specifying a selector having HEADER_JMS_MESSAGE_ID as the given messageId
            String selector = new StringBuilder(HEADER_JMS_MESSAGE_ID)
            .append("='")
            .append(message.getJMSMessageID())
            .append('\'')
            .toString();
            MessageConsumer consumer = session.createConsumer(JmsClientHelper.obtainQueue(queueName), selector);
            
            // Consume the message. Wait for 10 seconds at most
            Message m = consumer.receive(10000);
            if (m == null)
                throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.MESSAGE_NOT_FOUND));
            consumer.close();
        } catch (JMSException e) {
            log.error("Error in consuming a JMS Message", e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.DELETE_ERROR, null, e);
        }
    }
    
}
