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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.*;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.user.services.UserContextWrapper;
import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.presentation.portlet.session.UserSessionSetupException;
import org.jaffa.security.SecurityManager;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.EmailerBean;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.MessageHelper;

/**
 * This implementation for consuming messages written by the JmsClientHelper.
 */
public final class JaffaMessageConsumer implements MessageListener {

  private static final Logger LOGGER = Logger
      .getLogger(JaffaMessageConsumer.class);

  private static final JaffaMessageConsumer INSTANCE = new JaffaMessageConsumer();

  private static final String RULE_FAILURE_NOTIFICATION_PREFIX = "jaffa.messaging.failureNotification.";
  private static final String EMAIL_SUBJECT_NOTIFICATION = "email.subject.messaging.failureNotification";
  private static final String EMAIL_BODY_NOTIFICATION = "email.body.messaging.failureNotification";

  private JaffaMessageConsumer() {
  }

  /**
   * This method is invoked by the messaging system, when a message is received
   * into a Destination with which this MDB is registered. It'll invoke the
   * handler associated with the input Message's payload, as obtained from the
   * configuration file. In case of an error, the Message will be redirected to
   * the ErrorQueue. If an error is thrown while redirecting the Message to the
   * ErrorQueue, that error will be logged by the logging-system. When handling
   * a message published to a Topic, errors if any, will be logged only.
   *
   * @param message
   *          the Message.
   */
  public static void processMessage(final Message message) {
    INSTANCE.onMessageInternal(message);
  }

    public void onMessage(Message message) {
        JaffaMessageConsumer.processMessage(message);
    }


  /**
   * Invokes the handler associated with the input payload.
   *
   * @param messageInfo
   *          The messageInfo for the payload from the configuration file.
   * @param payload
   *          Any serializable object.
   * @param userId
   *          the user id.
   * @param scheduledTaskId
   *          contains the id of the scheduled task, if invoked by the
   *          scheduler.
   * @param originalMessageId
   *          the original id of the Message being processed.
   * @throws FrameworkException
   *           Indicates some system error.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  public static void processPayload(final MessageInfo messageInfo,
      final Object payload, final String userId, final String scheduledTaskId,
      final String originalMessageId) throws FrameworkException,
      ApplicationExceptions {
    INSTANCE.processPayload(null, messageInfo, payload, userId,
        scheduledTaskId, originalMessageId);
  }

  /**
   * This method is invoked by the messaging system, when a message is received
   * into a Destination with which this MDB is registered. It'll invoke the
   * handler associated with the input Message's payload, as obtained from the
   * configuration file. In case of an error, the Message will be redirected to
   * the ErrorQueue. If an error is thrown while redirecting the Message to the
   * ErrorQueue, that error will be logged by the logging-system. When handling
   * a message published to a Topic, errors if any, will be logged only.
   *
   * @param aMessage
   *          the Message.
   */
  private void onMessageInternal(final Message aMessage) {
    try {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Consuming the message " + aMessage);

      // we want to log the total processing time, get the start time
      final long startTime = System.nanoTime();

      // Reads the MessageConfig from the ConfigurationService, based on the
      // dataBeanClassName
      final String dataBeanClassName = aMessage
          .getStringProperty(JmsBrowser.HEADER_DATA_BEAN_CLASS_NAME);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("DataBean Class Name " + dataBeanClassName);
      final MessageInfo messageInfo = ConfigurationService.getInstance()
          .getMessageInfo(dataBeanClassName);
      if (messageInfo == null)
        throw new JaffaMessagingFrameworkException(
            JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING,
            new Object[] { dataBeanClassName });

      // Unmarshals the Message payload into a dataBean using the
      // dataBeanClassName
      final Object payload = JAXBHelper.unmarshalPayload(
          obtainMessageContents(aMessage), dataBeanClassName);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Obtained payload for the dataBean " + dataBeanClassName
            + '\n' + payload);

      // Reads the userId from the Message header
      String userId = aMessage.getStringProperty(JmsBrowser.HEADER_USER_ID);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("The userId is " + userId);

      // Reads the scheduledTaskId from the Message header
      final String scheduledTaskId = aMessage
          .getStringProperty(JmsBrowser.HEADER_SCHEDULED_TASK_ID);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("The scheduledTaskId is " + scheduledTaskId);

      // Reads the originalMessageId from the Message header
      String originalMessageId = aMessage
          .getStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID);
      if (originalMessageId == null)
        originalMessageId = aMessage.getJMSMessageID();
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("The originalMessageId is " + originalMessageId);

      // Process the payload
      processPayload(aMessage, messageInfo, payload, userId, scheduledTaskId,
          originalMessageId);

      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Message successfully consumed");

      // we want to log the total processing time, get the end time
      if (LOGGER.isInfoEnabled()) {
        final long endTime = System.nanoTime();
        final long duration = endTime - startTime;
        LOGGER.info("[" + originalMessageId + "]Total time to process the Message: " + duration + "ms");
      }
    } catch (Exception e) {
      // Just log the error
      LOGGER.error(
          "Exception thrown while consuming the message. Message was: "
              + aMessage, e);
    }
  }

  /**
   * Invokes the handler associated with the input payload. If any error occurs
   * while processing the payload, and if the input message is not null, then
   * the message will be redirected to the ErrorQueue. When handling a message
   * published to a Topic, errors if any, will be logged only.
   *
   * @param aMessage
   *          the Message.
   * @param messageInfo
   *          The messageInfo for the payload from the configuration file.
   * @param payload
   *          Any serializable object.
   * @param userId
   *          the user id.
   * @param scheduledTaskId
   *          contains the id of the scheduled task, if invoked by the
   *          scheduler.
   * @param originalMessageId
   *          the original id of the Message being processed.
   * @throws FrameworkException
   *           Indicates some system error.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private void processPayload(final Message aMessage,
      final MessageInfo messageInfo, final Object payload, final String userId,
      final String scheduledTaskId, final String originalMessageId)
      throws FrameworkException, ApplicationExceptions {
    // Maintain a reference to the currently logged in user. This may typically
    // happen in POST_IMMEDIATE mode
    final String currentUserId = SecurityManager.getPrincipal() != null ? SecurityManager
        .getPrincipal().getName() : null;
    UserContextWrapper ucw = null;
    boolean createdLoggingContext = false;
    try {
      if (currentUserId != null && currentUserId.equals(userId)) {
        // No need to set any context if the current userId matches the input
        // userId
        if (LOGGER.isDebugEnabled())
          LOGGER
              .debug("Context will not be created for the authenticated user "
                  + userId);
      } else {
        // Sets the context based on the userId
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Creating context for the user " + userId);
        ucw = createUserContextWrapper(userId);
      }

      // Sets Log4J's MDC to enable BusinessEventLogging
      LoggingService.setLoggingContext(payload, messageInfo, userId,
          scheduledTaskId, originalMessageId);
      createdLoggingContext = true;

      if (LOGGER.isInfoEnabled())
        LOGGER.info(MessageHelper.findMessage(
            "label.Jaffa.Messaging.JaffaMessageBean.start",
            new Object[] { payload }));

      // Invokes the handler as specified by the 'toClass and toMethod'
      // combination in the MessageConfig
      invokeHandler(aMessage != null ? aMessage.getJMSMessageID() : null,
          messageInfo, payload);

      if (LOGGER.isInfoEnabled())
        LOGGER.info(MessageHelper.findMessage(
            "label.Jaffa.Messaging.JaffaMessageBean.success",
            new Object[] { payload }));
    } catch (Exception e) {
      LOGGER.error(MessageHelper.findMessage(
          "error.Jaffa.Messaging.JaffaMessageBean.error",
          new Object[] { payload }), e);
      // Do nothing when handling a message published to a Topic
      if (messageInfo.getQueueName() != null) {
        // Redirect to ErrorQueue, if the original message was passed. Else
        // rethrow the exception
        if (aMessage != null)
          handleException(aMessage, payload, e);
        else
          throw ExceptionHelper.throwAFR(e);
      }
    } finally {
      // Unset the Logging context
      if (createdLoggingContext)
        LoggingService.unsetLoggingContext(payload, messageInfo);

      // Clear context for this user
      if (ucw != null) {
        ucw.unsetContext();

        // Restore the context for the original user
        if (currentUserId != null) {
          try {
            if (LOGGER.isDebugEnabled())
              LOGGER.debug("Restoring context for the user " + currentUserId);
            createUserContextWrapper(currentUserId);
          } catch (Exception e) {
            if (LOGGER.isDebugEnabled())
              LOGGER.debug(
                  "Exception thrown while restoring context for the user "
                      + currentUserId, e);
          }
        }
      }
    }
  }

  /**
   * Creates a UserContextWrapper based on the input userId.
   *
   * @param userId
   *          the user id.
   * @return a UserContextWrapper based on the input userId.
   * @throws UserSessionSetupException
   *           in case any error occurs during the context setup.
   */
  private UserContextWrapper createUserContextWrapper(String userId)
      throws UserSessionSetupException {
    return UserContextWrapperFactory.instance(userId);
  }

  /**
   * Obtains the contents of the input message. This currently supports a
   * TextMessage only. A null will be returned for all other message types.
   *
   * @param aMessage
   *          the Message.
   * @throws JMSException
   *           if any error occurs in reading the input message.
   * @return the contents of the input message.
   */
  private String obtainMessageContents(Message aMessage) throws JMSException {
    String contents = null;
    if (aMessage instanceof TextMessage)
      contents = ((TextMessage) aMessage).getText();
    return contents;
  }

  /**
   * Invokes the handler to process the payload.
   *
   * @param messageId
   *          the message Id.
   * @param messageInfo
   *          the messageInfo that contains the handler information.
   * @param payload
   *          the payload to be processed.
   * @throws ClassNotFoundException
   *           if the handler class or the dataBean class are not found.
   * @throws NoSuchMethodException
   *           if the handler method that takes the payload as an argument, is
   *           not found.
   * @throws InstantiationException
   *           if any error occurs while invoking the handler.
   * @throws IllegalAccessException
   *           if any error occurs while invoking the handler.
   * @throws InvocationTargetException
   *           if any error occurs while invoking the handler.
   * @throws FrameworkException
   *           in case any internal error occurs.
   * @throws ApplicationExceptions
   *           Indicates application error(s).
   */
  private void invokeHandler(String messageId, MessageInfo messageInfo,
      Object payload) throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException,
      InvocationTargetException, FrameworkException, ApplicationExceptions {

    // we want to log the total time the business logic of this handler takes, get the start time
    final long startTime = System.nanoTime();

    if (messageInfo.getToClass() == null
        || messageInfo.getToClass().length() == 0
        || messageInfo.getToMethod() == null
        || messageInfo.getToMethod().length() == 0) {
      if (LOGGER.isDebugEnabled())
        LOGGER
            .debug("Message is ignored, since handler has not been defined for "
                + messageInfo.getDataBean());
    } else {
      Method handlerMethod = null;

      // Obtain the handlerClass
      Class<?> handlerClass = Class.forName(messageInfo.getToClass());

      // Obtain the handler method
      try {
        handlerMethod = handlerClass.getMethod(messageInfo.getToMethod(),
            new Class[] { payload.getClass() });
      } catch (NoSuchMethodException e) {
        // Note that the payload could be a subclass of the argument that the
        // handler method expects
        // Hence use the dataBeanClass specified in the messageInfo to get the
        // appropriate handlerMethod
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Handler method " + messageInfo.getToClass() + '.'
              + messageInfo.getToMethod() + '(' + payload.getClass().getName()
              + ')' + " not found. Will look for a method that takes "
              + messageInfo.getDataBean() + " as argument");
        Class<?> dataBeanClass = Class.forName(messageInfo.getDataBean());
        handlerMethod = handlerClass.getMethod(messageInfo.getToMethod(),
            new Class[] { dataBeanClass });
      }

      // Create an instance of handlerClass, if the handlerMethod is not static
      // or if the handlerClass implements the IManageableMessageHandler
      // interface
      Object handlerObject = null;
      if (!Modifier.isStatic(handlerMethod.getModifiers())
          || (messageId != null && IManageableMessageHandler.class
              .isAssignableFrom(handlerClass)))
        handlerObject = handlerClass.newInstance();


        // Invoke the handler
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Invoking the handler " + handlerMethod);
        handlerMethod.invoke(handlerObject, new Object[] { payload });

        // we want to log the total processing time, get the end time
        if (LOGGER.isInfoEnabled()) {
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            LOGGER.info(getLogMessage(messageId, messageInfo));
            LOGGER.info("[" + messageId + "]Total time to invoke the handler: " + duration + "ms");
        }
    }
  }

    /**
     * Gets a log message to record information about the message that was processed.
     *
     * @param id          the ID of the message that was processed
     * @param messageInfo contains the information that we want to log
     * @return the formatted log string
     */
    private String getLogMessage(String id, MessageInfo messageInfo) {
        final String dataBean = messageInfo.getDataBean();
        final String type = messageInfo.getQueueName();
        final String handlerClass = messageInfo.getToClass();
        final String handlerMethod = messageInfo.getToMethod();

        // build up the message to return
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(id);
        sb.append("]");
        sb.append("Successfully processed Message: ");
        sb.append("DataBean = ");
        sb.append(dataBean);
        sb.append(", Type = ");
        sb.append(type);
        sb.append(", Handler Class = ");
        sb.append(handlerClass);
        sb.append(", Handler Method = ");
        sb.append(handlerMethod);
        return sb.toString();
    }

  /**
   * Handles the error that may be thrown while consuming the message. The
   * message is redirected to the ErrorQueue. A RuntimeException is thrown in
   * case an error is thrown while redirecting the Message to the ErrorQueue.
   *
   * @param aMessage
   *          the Message.
   * @param payload
   *          Any serializable object.
   * @param e
   *          the error that was thrown while consuming the message.
   */
  private void handleException(Message aMessage, Object payload, Exception e) {
    Session session = null;
    try {
      if (LOGGER.isInfoEnabled())
        LOGGER.info(MessageHelper.findMessage(
            "label.Jaffa.Messaging.JaffaMessageBean.redirect", null));

      // Clones the input Message
      final Connection connection = JaffaConnectionFactory.obtainConnection();
      session = JmsClientHelper.obtainSession(connection, false);
      Message newMessage = JmsClientHelper.cloneMessage(session, aMessage);

      // Sets a header element with the error details
      String errorMessage = null;
      FrameworkException fe = ExceptionHelper.extractFrameworkException(e);
      if (fe != null) {
        errorMessage = fe.getLocalizedMessage();
      } else {
        ApplicationExceptions aes = ExceptionHelper
            .extractApplicationExceptions(e);
        if (aes != null && aes.size() > 0) {
          StringBuilder buf = new StringBuilder();
          for (Iterator<ApplicationException> itr = aes.iterator(); itr
              .hasNext();) {
            ApplicationException ae = (ApplicationException) itr.next();
            if (buf.length() > 0)
              buf.append('\n');
            buf.append(ae.getLocalizedMessage());
          }
          errorMessage = buf.toString();
        } else {
          StringBuilder buf = new StringBuilder();
          Throwable exception = e;
          while (exception != null) {
            String str = exception.getLocalizedMessage();
            if (str != null) {
              if (buf.length() > 0)
                buf.append('\n');
              buf.append(str);
            }
            exception = exception.getCause();
          }
          errorMessage = buf.toString();
        }
      }
      newMessage.setObjectProperty(JmsBrowser.HEADER_ERROR_DETAILS,
          errorMessage);

      // send a failure notification, if configured
      sendFailureNotification(newMessage, payload, e);
    } catch (Exception e1) {
      LOGGER.error(MessageHelper.findMessage(
          "error.Jaffa.Messaging.JaffaMessageBean.redirectError", null), e1);
      throw new RuntimeException(
          "Exception thrown while redirecting the Message to the ErrorQueue",
          e1);
    } finally {
      if (session != null) {
        try {
          session.close();
        } catch (JMSException e1) {
          LOGGER.warn("Error in closing a JMS Session", e1);
        }
      }
    }
  }

  /**
   * Constructs an email with error information, provided the application rule
   * 'jaffa.messaging.failureNotification.{queuename}' is defined.
   */
  private void sendFailureNotification(Message aMessage, Object payload,
      Exception e) throws JMSException, MessagingException {
    String queueName = aMessage
        .getStringProperty(JmsBrowser.HEADER_ORIGINAL_QUEUE_NAME);
    String ruleName = RULE_FAILURE_NOTIFICATION_PREFIX + queueName;
    String ruleValue = (String) ContextManagerFactory.instance().getProperty(
        ruleName);
    if (ruleValue != null && ruleValue.length() > 0) {
      // the rule value is expected to be a semicolon-separated list of
      // email-addresses
      String[] to = ruleValue.split(";");
      String token = EMAIL_SUBJECT_NOTIFICATION + '.'
          + payload.getClass().getName();

      // obtain the template for the subject
      String subject = MessageHelper.findMessage(token, null);
      if (subject == null || subject.length() == 0 || subject.startsWith("???")) {
        if (LOGGER.isDebugEnabled())
          LOGGER
              .debug("Entry '"
                  + token
                  + "' not found in the resource bundle for generating the subject of the failure notification. Will use the generic subject.");
        subject = MessageHelper.findMessage(EMAIL_SUBJECT_NOTIFICATION, null);
      }

      // obtain the template for the body
      token = EMAIL_BODY_NOTIFICATION + '.' + payload.getClass().getName();
      String body = MessageHelper.findMessage(token, null);
      if (body == null || body.length() == 0 || body.startsWith("???")) {
        if (LOGGER.isDebugEnabled())
          LOGGER
              .debug("Entry '"
                  + token
                  + "' not found in the resource bundle for generating the body of the failure notification. Will use the generic body.");
        body = MessageHelper.findMessage(EMAIL_BODY_NOTIFICATION, null);
      }

      // create the context for the template
      final Map<String, Object> context = new HashMap<String, Object>();
      context.put("message", aMessage);
      context.put("bean", payload);
      context.put("exception", e);
      context.put("queue", queueName);
      context.put("errorMessage",
          aMessage.getStringProperty(JmsBrowser.HEADER_ERROR_DETAILS));
      context.put("context", ContextManagerFactory.instance()
          .getThreadContext());
      context.put("appUrl",
          ContextManagerFactory.instance().getProperty("app.url"));

      // transform the stacktrace into an attachment
      final BodyPart attachment = new MimeBodyPart();
      attachment.setContent(ExceptionHelper.getStackTrace(e), "text/plain");
      attachment.setFileName("stacktrace.txt");

      // send the failure notification
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Sending failure notification to " + ruleValue);
      final EmailerBean eb = new EmailerBean();
      eb.sendMailTemplate(null, to, subject, body, context,
          new BodyPart[] { attachment });
    } else {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Failure Notification will not be sent since rule "
            + ruleName + " is undefined");
    }
  }
}