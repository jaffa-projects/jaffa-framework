package org.jaffa.modules.messaging.components.queueviewer.ui;

import org.apache.log4j.Logger;
import java.util.EventObject;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerInDto;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.middleware.Factory;

import org.jaffa.modules.messaging.components.queueviewer.IQueueViewer;
import org.jaffa.modules.messaging.components.queueviewer.dto.QueueViewerInDto;
import org.jaffa.modules.messaging.components.queueviewer.dto.QueueViewerOutDto;
import org.jaffa.modules.messaging.components.messageviewer.ui.*;
import java.util.*;

/** The controller for the QueueViewer.
 */
public class QueueViewerComponent extends Component {
    
    private static Logger log = Logger.getLogger(QueueViewerComponent.class);
    
    private java.lang.String m_queue;
    private java.lang.String m_filter;
    private MessageModeEnum m_messageMode;
    private QueueViewerOutDto m_outputDto = null;
    private IQueueViewer m_tx = null;
    private Collection m_queueViewerListeners;
    
    /** This should be invoked when done with the component.
     */
    public void quit() {
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        
        m_outputDto = null;
        super.quit();
    }
    
    /** Getter for property queue.
     * @return Value of property queue.
     */
    public java.lang.String getQueue() {
        return m_queue;
    }
    
    /** Setter for property queue.
     * @param queue New value of property queue.
     */
    public void setQueue(java.lang.String queue) {
        m_queue = queue;
    }
    
    /** Getter for property filter.
     * @return Value of property filter.
     */
    public java.lang.String getFilter() {
        return m_filter;
    }
    
    /** Setter for property filter.
     * @param filter New value of property filter.
     */
    public void setFilter(java.lang.String filter) {
        m_filter = filter;
    }
    
    /** Getter for property messageMode.
     * @return Value of property messageMode.
     */
    public MessageModeEnum getMessageMode() {
        return m_messageMode;
    }
    
    /** Setter for property messageMode.
     * @param messageMode New value of property messageMode.
     */
    public void setMessageMode(MessageModeEnum messageMode) {
        m_messageMode = messageMode;
    }
    
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */
    public QueueViewerOutDto getQueueViewerOutDto() {
        return m_outputDto;
    }
    
    /** Setter for property outputDto.
     * @param outputDto New value of property outputDto.
     */
    public void setQueueViewerOutDto(QueueViewerOutDto outputDto) {
        m_outputDto = outputDto;
    }
    
    /** This retrieves the details for the QueueHeader.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set, or if no data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        
        if (getQueue() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException("[label.Jaffa.Messaging.QueueViewer.Queue]") );
        }
        if (appExps != null && appExps.size() > 0)
            throw appExps;
        
        doInquiry();
        return getViewerFormKey();
    }
    
    public void doInquiry() throws ApplicationExceptions, FrameworkException {
        m_outputDto = createTx().read(createQueueViewerInDto());
        getUserSession().getWidgetCache(getComponentId()).clear();
    }
    
    public FormKey getViewerFormKey() {
        return new FormKey(QueueViewerForm.NAME, getComponentId());
    }
    
    public FormKey viewMessage(String messageId) throws ApplicationExceptions, FrameworkException {
        MessageViewerComponent viewComponent = (MessageViewerComponent) run("Jaffa.Messaging.MessageViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setJMSMessageID(messageId);
        viewComponent.setQueue(getQueue());
        viewComponent.setMessageMode(getMessageMode());
//        viewComponent.addMessageViewerListener(new IMessageViewerListener() {
//            public void processDone(EventObject source) throws ApplicationExceptions, FrameworkException{
//                doInquiry();
//            }
//        });
        
        return viewComponent.display();
    }
    
    public FormKey deleteMessage(String messageId) throws ApplicationExceptions, FrameworkException {
        createTx().deleteMessage(createMessageViewerInDto(messageId));
        doInquiry();
        return getViewerFormKey();
    }
    
    public FormKey resubmitMessage(String messageId) throws ApplicationExceptions, FrameworkException {
        createTx().resubmitMessage(createMessageViewerInDto(messageId));
        doInquiry();
        return getViewerFormKey();
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addQueueViewerListener(IQueueViewerListener listener) {
        if (m_queueViewerListeners == null)
            m_queueViewerListeners = new HashSet();
        m_queueViewerListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeQueueViewerListener(IQueueViewerListener listener) {
        if (m_queueViewerListeners != null)
            return m_queueViewerListeners.remove(listener);
        else
            return false;
    }
    
    /** Invokes the processDone() method of the registered IQueueViewerListener objects in the same thread.
     */
    protected void invokeQueueViewerListeners() throws ApplicationExceptions, FrameworkException{
        if (m_queueViewerListeners != null) {
            EventObject eventObject = new EventObject(this);
            for (Iterator i = m_queueViewerListeners.iterator(); i.hasNext(); )
                ( (IQueueViewerListener) i.next() ).processDone(eventObject);
        }
    }
    
    private IQueueViewer createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IQueueViewer) Factory.createObject(IQueueViewer.class);
        return m_tx;
    }
    
    private QueueViewerInDto createQueueViewerInDto() {
        QueueViewerInDto inputDto = new QueueViewerInDto();
        inputDto.setQueueName(getQueue());
        inputDto.setFilter(getFilter());
        inputDto.setMessageMode(getMessageMode());
        return inputDto;
    }
    
    private MessageViewerInDto createMessageViewerInDto(String messageId) {
        MessageViewerInDto inputDto = new MessageViewerInDto();
        inputDto.setJMSMessageID(messageId);
        inputDto.setQueue(getQueue());
        inputDto.setMessageMode(getMessageMode());
        return inputDto;
    }
    
}
