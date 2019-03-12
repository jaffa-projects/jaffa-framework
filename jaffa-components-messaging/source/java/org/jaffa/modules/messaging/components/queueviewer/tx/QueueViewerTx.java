package org.jaffa.modules.messaging.components.queueviewer.tx;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerInDto;

import org.jaffa.modules.messaging.components.queueviewer.IQueueViewer;
import org.jaffa.modules.messaging.components.queueviewer.dto.*;

import org.jaffa.modules.messaging.services.*;
import org.jaffa.modules.messaging.services.configdomain.*;
import javax.jms.Message;
import javax.jms.JMSException;
import org.jaffa.datatypes.DateTime;
import org.jaffa.util.BeanHelper;

/** Viewer for Queue objects.
 */
public class QueueViewerTx implements IQueueViewer {
    
    private static Logger log = Logger.getLogger(QueueViewerTx.class);

    // TODO-SWAT add script events here
    
    /** This should be invoked, when done with the component. */
    public void destroy() {
    }
    
    /** Returns the details for Queue.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public QueueViewerOutDto read(QueueViewerInDto input)
    throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input: " + input);
        QueueViewerOutDto output = buildDto(input);
        if (log.isDebugEnabled())
            log.debug("Output: " + output);
        return output;
    }
    
    /** Deletes a Message.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void deleteMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException{
        // TODO-SWAT fire custom handler here
        if (input.getMessageMode() == null || input.getMessageMode() == MessageModeEnum.PENDING)
            JmsBrowser.deleteMessage(input.getQueue(), input.getJMSMessageID());
        else if (input.getMessageMode() == MessageModeEnum.ERROR)
            JmsBrowser.deleteMessage(input.getQueue(), input.getJMSMessageID());
        else
            log.warn("Message cannot be deleted since unsupported messageMode '" + input.getMessageMode() + "' has been passed");
    }
    
    /** Resubmits a Message.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void resubmitMessage(MessageViewerInDto input) throws ApplicationExceptions, FrameworkException{
        if (input.getMessageMode() == MessageModeEnum.ERROR)
            JmsBrowser.resubmitMessage(input.getQueue(), input.getJMSMessageID());
        else
            log.warn("Only an Error message can be resubmitted");
    }
    
    
    private QueueViewerOutDto buildDto(QueueViewerInDto input)
    throws FrameworkException, ApplicationExceptions {
        QueueViewerOutDto output =  new QueueViewerOutDto();
        output.setHasAdminAccess(JmsBrowser.hasAdminMessageAccess(input.getQueueName()));
        addRelatedDtos(input, output);
        return output;
    }
    
    private void addRelatedDtos(QueueViewerInDto input, QueueViewerOutDto output)
    throws FrameworkException, ApplicationExceptions {
        Message[] messages = null;
        
        if (input.getMessageMode() == null || input.getMessageMode() == MessageModeEnum.PENDING)
            messages = JmsBrowser.getPendingMessages(input.getQueueName(), input.getFilter());
        else
            log.warn("Messages cannot be retrieved since unsupported messageMode '" + input.getMessageMode() + "' has been passed");
        
        try {
            // Determine the header elements for the queue
            QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(input.getQueueName());
            if (queueInfo != null && queueInfo.getDisplayParam() != null) {
                List<HeaderElementDto> headerElementDtos = new LinkedList<HeaderElementDto>();
                for (DisplayParam displayParam : queueInfo.getDisplayParam()) {
                    HeaderElementDto headerElementDto = new HeaderElementDto();
                    headerElementDto.setName(displayParam.getName());
                    headerElementDto.setLabel(displayParam.getLabel());
                    headerElementDtos.add(headerElementDto);
                }
                output.setHeaderElements(headerElementDtos.toArray(new HeaderElementDto[headerElementDtos.size()]));
            }
            
            if (messages != null && messages.length > 0) {
                List<MessageDto> messageDtos = new LinkedList<MessageDto>();
                String[] manageableMessages = null;
                for (Message message : messages) {
                    MessageDto messageDto = new MessageDto();
                    messageDto.setMessageId(message.getJMSMessageID());
                    if (input.getMessageMode() == MessageModeEnum.IN_PROCESS) {
                        messageDto.setManageable(Arrays.binarySearch(manageableMessages, message.getJMSMessageID()) >= 0 ? Boolean.TRUE : Boolean.FALSE);
                    } else
                        messageDto.setManageable(Boolean.FALSE);
                    // Evaluate the header elements for the message
                    if (queueInfo != null && queueInfo.getDisplayParam() != null) {
                        List<HeaderElementDto> headerElementDtos = new LinkedList<HeaderElementDto>();
                        for (DisplayParam displayParam : queueInfo.getDisplayParam()) {
                            HeaderElementDto headerElementDto = new HeaderElementDto();
                            headerElementDto.setName(displayParam.getName());
                            headerElementDto.setLabel(displayParam.getLabel());
                            Object value = message.getObjectProperty(displayParam.getName());
                            if (value == null) {
                                // It is possible that the parameter could be a property on the Message (eg. JMSMessageID, JMSPriority etc.)
                                // Use bean intropsection to extract the value of the property
                                try {
                                    if (displayParam.getName().equals("JMSTimestamp")) {
                                        value = message.getJMSTimestamp() != 0 ? new DateTime(message.getJMSTimestamp()) : null;
                                    } else {
                                        value = BeanHelper.getField(message, displayParam.getName());
                                    }
                                } catch (Exception e) {
                                    // do nothing
                                }
                            }
                            if (value != null)
                                headerElementDto.setValue(Formatter.format(value));
                            headerElementDtos.add(headerElementDto);
                        }
                        messageDto.setHeaderElements(headerElementDtos.toArray(new HeaderElementDto[headerElementDtos.size()]));
                    }
                    messageDtos.add(messageDto);
                }
                output.setMessages(messageDtos.toArray(new MessageDto[messageDtos.size()]));
            }
        } catch(JMSException e){
            throw new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.MESSAGE_INFO_MISSING, null, e);
        }
    }
}