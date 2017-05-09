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

import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.configdomain.Filter;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.messaging.services.configdomain.Param;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.util.ExceptionHelper;

/** A helper class to check for locks in existing messages.
 */
public class LockingService {
    
    private static final Logger log = Logger.getLogger(LockingService.class);
    
    /** Browses all messages in all the queues looking for the locks, as specified in the messageInfo for the input payload.
     * Throws an ApplicationException, if any matching message is found.
     * @param payload Any serializable object.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(Object payload) throws FrameworkException, ApplicationExceptions {
        try {
            // Reads the MessageConfig for the className of the input payload
            MessageInfo messageInfo = ConfigurationService.getInstance().getMessageInfo(payload.getClass().getName());
            if (messageInfo == null)
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[] {payload.getClass().getName()});
            
            // Perform the check
            checkLock(payload, messageInfo);
        } catch (ClassNotFoundException e) {
            log.error("Error in locating the Message info for " + payload.getClass().getName(), e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[] {payload.getClass().getName()}, e);
        }
    }
    
    /** Browses all messages in all the queues looking for the locks, as specified in the input messageInfo.
     * Throws an ApplicationException, if any matching message is found.
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(Object payload, MessageInfo messageInfo) throws FrameworkException, ApplicationExceptions {
        checkOrDeleteLockingMessages(payload, messageInfo, false);
    }
    
    /** Browses all messages in all the queues looking for the locks, as specified in the messageInfo for the input payload.
     * Deletes the matching messages.
     * @param payload Any serializable object.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteLockingMessages(Object payload) throws FrameworkException, ApplicationExceptions {
        try {
            // Reads the MessageConfig for the className of the input payload
            MessageInfo messageInfo = ConfigurationService.getInstance().getMessageInfo(payload.getClass().getName());
            if (messageInfo == null)
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[] {payload.getClass().getName()});
            
            // Obtain the messages
            deleteLockingMessages(payload, messageInfo);
        } catch (ClassNotFoundException e) {
            log.error("Error in locating the Message info for " + payload.getClass().getName(), e);
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, new Object[] {payload.getClass().getName()}, e);
        }
    }
    
    /** Browses all messages in all the queues looking for the locks, as specified in the messageInfo for the input payload.
     * Deletes the matching messages.
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteLockingMessages(Object payload, MessageInfo messageInfo) throws FrameworkException, ApplicationExceptions {
        checkOrDeleteLockingMessages(payload, messageInfo, true);
    }
    
    
    
    
    /** Browses all messages in all the queues looking for the locks, as specified in the messageInfo for the input payload.
     * Throws an ApplicationException, if any matching message is found, and if the input argument 'deleteLockingMessage' is false.
     * Deletes all matching messages, if the input argument 'deleteLockingMessage' is true.
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     * @param deleteLockingMessage determines if the matching messages are to be deleted.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static void checkOrDeleteLockingMessages(Object payload, MessageInfo messageInfo, boolean deleteLockingMessage) throws FrameworkException, ApplicationExceptions {
        Session session = null;
        String queueName = null;
        try {
            // Create the filter to find messages that carry the locks, as specified in the messageInfo
            String filter = createLockFilter(payload, messageInfo);
            
            if (filter != null) {
                // Obtains all the queue names from the ConfigurationService
                String[] queueNames = ConfigurationService.getInstance().getQueueNames();
                if (queueNames != null) {
                    // Obtain a Connection with the JMS provider
                    Connection connection = JaffaConnectionFactory.obtainConnection();
                    // Creates a Session from the Connection
                    session = JmsClientHelper.obtainSession(connection, true);
                    
                    for (int i = 0; i < queueNames.length; i++) {
                        queueName = queueNames[i];
                        checkOrDeleteLockingPendingMessages(queueName, filter, deleteLockingMessage, session);
                    }
                    // Commit the session and close it
                    session.commit();
                    session.close();
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("LockSearch cannot be performed since lockCheck has not been specified for " + messageInfo.getDataBean());
            }
        } catch (JMSException e) {
            log.error("Error in lock search", e);
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
    
    /** Browses all pending messages in the input queue looking for the locks, as specified in the input filter.
     * Throws an ApplicationException, if any matching message is found, and if the input argument 'deleteLockingMessage' is false.
     * Deletes all matching messages, if the input argument 'deleteLockingMessage' is true.
     * @param queueName the queue name.
     * @param filter the filter for retrieving Lock messages.
     * @param deleteLockingMessage determines if the matching messages are to be deleted.
     * @param session the JMS session.
     * @throws JMSException if any JMS error occurs.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static void checkOrDeleteLockingPendingMessages(String queueName, String filter, boolean deleteLockingMessage, Session session)
    throws JMSException, ApplicationExceptions, FrameworkException {
        // Creates a QueueBrowser from the Session, using the filter
        if (log.isDebugEnabled())
            log.debug("Checking for locks in pending messages in " + queueName + " with the filter " + filter);
        QueueBrowser qb = session.createBrowser(JmsClientHelper.obtainQueue(queueName), filter);
        
        // Throws an ApplicationException if any applicable lock is found
        Enumeration e = qb.getEnumeration();
        while (e.hasMoreElements()) {
            Message message = (Message) e.nextElement();
            if (log.isDebugEnabled())
                log.debug("Found a blocking pending message in " + queueName + " with the contents " + message);
            if (deleteLockingMessage)
                JmsBrowser.consumeMessage(session, message, queueName);
            else
                throw new ApplicationExceptions(new JaffaMessagingApplicationException(JaffaMessagingApplicationException.LOCK_ERROR, new Object[] {queueName, message.getJMSMessageID()}));
        }
        
        // Close the QueueBrowser
        qb.close();
    }

    /** Determines the filter clause to find messages that carry the locks, as specified in the messageInfo.
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     * @return the filter clause to find messages that carry the locks, as specified in the messageInfo.
     * @throws FrameworkException Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static String createLockFilter(Object payload, MessageInfo messageInfo) throws FrameworkException, ApplicationExceptions {
        String filter = null;
        if (messageInfo.getLockCheck() != null) {
            // Use the filter provided by the lockCheck, if one exists, or else create one based on the param(s) inside the lockCheck
            Filter filterConfig = messageInfo.getLockCheck().getFilter();
            if (filterConfig != null) {
                if (filterConfig.isExpression()) {
                    try {
                        Object value = ScriptHelper.instance(filterConfig.getLanguage()).evaluate(null, filterConfig.getValue(), payload, null, 0, 0);
                        if (value != null)
                            filter = value.toString();
                    } catch (Exception e) {
                        throw ExceptionHelper.throwAFR(e);
                    }
                } else {
                    filter = filterConfig.getValue();
                }
            } else {
                StringBuilder buf = new StringBuilder();
                for (Param param : messageInfo.getLockCheck().getParam()) {
                    final Object value = InitialContextFactrory.obtainParamValue(param, payload);
                    if (value != null) {
                        if (buf.length() > 0)
                            buf.append(" or ");
                        buf.append(param.getName()).append('=').append('\'').append(value.toString()).append('\'');
                    }
                }
                if (buf.length() > 0)
                    filter = buf.toString();
            }
        }
        return filter;
    }
    
}
