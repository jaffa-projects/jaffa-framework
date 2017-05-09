package org.jaffa.modules.messaging.components.messageviewer.ui;

import java.util.Collection;
import org.apache.log4j.Logger;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerComponent;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.middleware.Factory;
import org.jaffa.modules.messaging.components.messageviewer.IMessageViewer;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerInDto;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerOutDto;
import org.jaffa.presentation.portlet.component.RiaWrapperComponent;

/** The controller for the MessageViewer.
 */
public class MessageViewerComponent extends Component {
    
    private static Logger log = Logger.getLogger(MessageViewerComponent.class);
    private java.lang.String m_JMSMessageID;
    private java.lang.String m_queue;
    private MessageModeEnum m_messageMode;
    private java.lang.Long m_priority;
    private MessageViewerOutDto m_outputDto = null;
    private IMessageViewer m_tx = null;
    private Collection m_messageViewerListeners;
    
    /** This should be invoked when done with the component.
     */
    public void quit() {
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        super.quit();
    }
    
    /** Getter for property JMSMessageID.
     * @return Value of property JMSMessageID.
     */
    public java.lang.String getJMSMessageID() {
        return m_JMSMessageID;
    }
    
    /** Setter for property JMSMessageID.
     * @param JMSMessageID New value of property JMSMessageID.
     */
    public void setJMSMessageID(java.lang.String JMSMessageID) {
        m_JMSMessageID = JMSMessageID;
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
    
    /** Getter for property priority.
     * @return Value of property priority.
     */
    public java.lang.Long getPriority() {
        return m_priority;
    }
    
    /** Setter for property priority.
     * @param priority New value of property priority.
     */
    public void setPriority(java.lang.Long priority) {
        m_priority = priority;
    }
    
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */
    public MessageViewerOutDto getMessageViewerOutDto() {
        return m_outputDto;
    }
    
    /** Setter for property outputDto.
     * @param outputDto New value of property outputDto.
     */
    public void setMessageViewerOutDto(MessageViewerOutDto outputDto) {
        m_outputDto = outputDto;
    }
    
    /** This retrieves the details for the MessageHeader.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set, or if no data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        
        if (getJMSMessageID() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException("[label.Jaffa.Messaging.Message.JMSMessageID]") );
        }
        
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
        m_outputDto = createTx().read(createMessageViewerInDto());
        setPriority(m_outputDto.getPriority());
        getUserSession().getWidgetCache(getComponentId()).clear();
    }
    
    public FormKey getViewerFormKey() {
        return new FormKey(MessageViewerForm.NAME, getComponentId());
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addMessageViewerListener(IMessageViewerListener listener) {
        if (m_messageViewerListeners == null)
            m_messageViewerListeners = new HashSet();
        m_messageViewerListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeMessageViewerListener(IMessageViewerListener listener) {
        if (m_messageViewerListeners != null)
            return m_messageViewerListeners.remove(listener);
        else
            return false;
    }
    
    /** Invokes the processDone() method of the registered IMessageViewerListener objects in the same thread.
     */
    protected void invokeMessageViewerListeners() throws ApplicationExceptions, FrameworkException{
        if (m_messageViewerListeners != null) {
            EventObject eventObject = new EventObject(this);
            for (Iterator i = m_messageViewerListeners.iterator(); i.hasNext(); )
                ( (IMessageViewerListener) i.next() ).processDone(eventObject);
        }
    }
    
    public FormKey changePriority() throws FrameworkException, ApplicationExceptions{
        //Get new Message Id and set it to component variable
        String messageId = createTx().changePriority(createMessageViewerInDto(), getPriority());
        setJMSMessageID(messageId);
        doInquiry();
        return getViewerFormKey();
    }
    
    /** Calls the Jaffa.Messaging.BusinessEventLogViewer component for viewing the BusinessEventLog object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey displayBusinessEventLogViewer(String logId) throws ApplicationExceptions, FrameworkException {
        BusinessEventLogViewerComponent viewComponent = (BusinessEventLogViewerComponent) run("Jaffa.Messaging.BusinessEventLogViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setLogId(logId);
        return viewComponent.display();
    }
    
    public FormKey displayBusinessEventLogFinder() throws ApplicationExceptions, FrameworkException {
        RiaWrapperComponent finderComponent = (RiaWrapperComponent) run("Jaffa.Messaging.BusinessEventLogFinder");
        finderComponent.getParameters().setProperty("displayResultsScreen", Boolean.TRUE.toString());
        finderComponent.getParameters().setProperty("messageId", createTx().getMessageIdForBusinessEventLog(createMessageViewerInDto()));
        return finderComponent.display();
    }
    
    private IMessageViewer createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IMessageViewer) Factory.createObject(IMessageViewer.class);
        return m_tx;
    }
    
    private MessageViewerInDto createMessageViewerInDto() {
        MessageViewerInDto inputDto = new MessageViewerInDto();
        inputDto.setJMSMessageID(getJMSMessageID());
        inputDto.setQueue(getQueue());
        inputDto.setMessageMode(getMessageMode());
        return inputDto;
    }
    
}
