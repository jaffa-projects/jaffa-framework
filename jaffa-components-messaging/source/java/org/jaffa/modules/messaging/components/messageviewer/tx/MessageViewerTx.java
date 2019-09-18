package org.jaffa.modules.messaging.components.messageviewer.tx;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.components.messageviewer.IMessageViewer;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.tx.BusinessEventLogFinderTx;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.components.messageviewer.dto.HeaderElementDto;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerInDto;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerOutDto;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException;
import org.jaffa.modules.messaging.services.JmsBrowser;
import org.jaffa.modules.messaging.services.configdomain.DisplayParam;
import org.jaffa.modules.messaging.services.configdomain.QueueInfo;
import org.jaffa.util.BeanHelper;

/** Viewer for Message objects.
 */
public class MessageViewerTx implements IMessageViewer {
    
    private static Logger log = Logger.getLogger(MessageViewerTx.class);
    
    /** This should be invoked, when done with the component. */
    public void destroy() {
    }
    
    /** Returns the details for Message.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public MessageViewerOutDto read(MessageViewerInDto input)
    throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input: " + input);
        MessageViewerOutDto output = buildDto(input);
        if (log.isDebugEnabled())
            log.debug("Output: " + output);
        return output;
    }
    
    /** Changes the priority of a Message.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The new messageId, in case the implementation were to recreate the message.
     */
    public String changePriority(MessageViewerInDto input, Long newPriority) throws FrameworkException, ApplicationExceptions {
        String newMessageId = input.getJMSMessageID();
        if (newPriority != null) {
            if (input.getMessageMode() == null || input.getMessageMode() == MessageModeEnum.PENDING)
                newMessageId = JmsBrowser.changeMessagePriority(input.getQueue(), input.getJMSMessageID(), newPriority.intValue());
            else if (input.getMessageMode() == MessageModeEnum.ERROR)
                newMessageId = JmsBrowser.changeMessagePriority(input.getQueue(), input.getJMSMessageID(), newPriority.intValue());
            else
                log.warn("Priority can only be changed for Pending or Error messages");
        } else {
            if (log.isDebugEnabled())
                log.debug("Priority is unchanged, since the new priorty is null");
        }
        return newMessageId;
    }
    
    /** Returns the messageId to be used for retrieving the BusinessEventLogs for a Message.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The messageId.
     */
    public String getMessageIdForBusinessEventLog(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions{
        try{
            Message message = findMessage(input);

            if (message == null) {
                throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, null);
            }
            String stringProperty = message.getStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID);
            String messageId = stringProperty != null ? stringProperty : message.getJMSMessageID();
            return messageId;
        } catch(JMSException e) {
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, null, e);
        }
    }
    
    /** Obtains the Message based on the messageMode. */
    private Message findMessage(MessageViewerInDto input) throws FrameworkException, ApplicationExceptions {
        Message message = null;
        if (input.getMessageMode() == null || input.getMessageMode() == MessageModeEnum.PENDING)
            message = JmsBrowser.getMessage(input.getQueue(), input.getJMSMessageID());
        else if (input.getMessageMode() == MessageModeEnum.ERROR)
            message = JmsBrowser.getMessage(input.getQueue(), input.getJMSMessageID());
        else
            log.warn("Message cannot be retrieved since unsupported messageMode '" + input.getMessageMode() + "' has been passed");
        return message;
    }
    
    /** Obtains the Message based on the messageMode and creates the output. */
    private MessageViewerOutDto buildDto(MessageViewerInDto input)
    throws FrameworkException, ApplicationExceptions {
        try{
            Message message = findMessage(input);
            MessageViewerOutDto output = new MessageViewerOutDto();

            if (message != null) {
                output.setJMSMessageID(input.getJMSMessageID());
                output.setError(message.getStringProperty(JmsBrowser.HEADER_ERROR_DETAILS));
                output.setPriority((long) message.getJMSPriority());
                output.setHasPriorityAccess(JmsBrowser.hasChangePriorityAccess(message.getStringProperty(JmsBrowser.HEADER_ORIGINAL_QUEUE_NAME)));
                output.setJMSDestination(message.getJMSDestination());
                output.setJMSDeliveryMode(message.getJMSDeliveryMode());
                output.setJMSTimestamp(message.getJMSTimestamp() != 0 ? new DateTime(message.getJMSTimestamp()) : null);
                output.setJMSCorrelationID(message.getJMSCorrelationID());
                output.setJMSReplyTo(message.getJMSReplyTo());
                try {
                    output.setJMSRedelivered(message.getJMSRedelivered());
                } catch (Exception e) {
                    // JBossMessaging throws "java.lang.IllegalStateException: This should never be called directly". Do nothing
                }
                output.setJMSType(message.getJMSType());
                output.setJMSExpiration(message.getJMSExpiration());
                if(message instanceof TextMessage)
                    output.setPayLoad(((TextMessage)message).getText());

                // Generate a Map of header elements, keyed by the name of each header element
                // Ignore Error Details as we are showing it in a separate section
                Map<String, HeaderElementDto> headerElements = new LinkedHashMap<>();
                for (Enumeration e = message.getPropertyNames(); e.hasMoreElements() ;){
                    String name = (String) e.nextElement();
                    if(!JmsBrowser.HEADER_ERROR_DETAILS.equals(name)) {
                        String value = Formatter.format(message.getObjectProperty(name));
                        HeaderElementDto headerElement = headerElements.get(name);
                        if (headerElement == null) {
                            headerElement = new HeaderElementDto();
                            headerElement.setName(name);
                            headerElements.put(name, headerElement);
                        }
                        headerElement.setValue(value);
                    }
                }

                // Add labels to the header-elements based on the QueueInfo
                // It is possible that a display-param points to a property on the Message (eg. JMSMessageID, JMSPriority etc.)
                // Use bean intropsection to extract the value of that property
                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(message.getStringProperty(JmsBrowser.HEADER_ORIGINAL_QUEUE_NAME));
                if (queueInfo != null && queueInfo.getDisplayParam() != null) {
                    for (DisplayParam displayParam : queueInfo.getDisplayParam()) {
                        HeaderElementDto headerElement = headerElements.get(displayParam.getName());
                        if (headerElement == null) {
                            try {
                                headerElement = new HeaderElementDto();
                                headerElement.setName(displayParam.getName());
                                headerElement.setLabel(displayParam.getLabel());
                                headerElements.put(displayParam.getName(), headerElement);
                                if (displayParam.getName().equals("JMSTimestamp")) {
                                    String value = message.getJMSTimestamp() != 0 ? Formatter.format(new DateTime(message.getJMSTimestamp())) : null;
                                    headerElement.setValue(value);
                                } else {
                                    String value = Formatter.format(BeanHelper.getField(message, displayParam.getName()));
                                    headerElement.setValue(value);
                                }
                            } catch (Exception e) {
                                // do nothing
                            }
                        } else {
                            headerElement.setLabel(displayParam.getLabel());
                        }
                    }
                }

                output.setHeaderElements(headerElements.values().toArray(new HeaderElementDto[headerElements.values().size()]));
                buildBusinessEventLogDto(input, output, message);

            }
            return output;
        } catch(JMSException e) {
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, null, e);
        }
    }
    
    /** Obtains the related business event logs. */
    private void buildBusinessEventLogDto(MessageViewerInDto input, MessageViewerOutDto output, Message message)
    throws FrameworkException, ApplicationExceptions, JMSException{
        BusinessEventLogFinderInDto inputDto =  new BusinessEventLogFinderInDto();
        inputDto.setMessageId(message.getStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID) != null ? new StringCriteriaField(CriteriaField.RELATIONAL_EQUALS, message.getStringProperty(JmsBrowser.HEADER_ORIGINAL_MESSAGE_ID))  : new StringCriteriaField(CriteriaField.RELATIONAL_EQUALS, message.getJMSMessageID()));
        inputDto.setMaxRecords(new Integer(10));
        inputDto.setOrderByFields(new OrderByField[] {new OrderByField(BusinessEventLogMeta.LOGGED_ON, Boolean.FALSE)});
        BusinessEventLogFinderOutDto businessEventLogFinderOutDto = new BusinessEventLogFinderTx().find(inputDto);
        output.setBusinessEventLog(businessEventLogFinderOutDto);
    }
    
}
