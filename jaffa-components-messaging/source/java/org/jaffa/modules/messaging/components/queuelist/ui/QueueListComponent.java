package org.jaffa.modules.messaging.components.queuelist.ui;

import java.util.EventObject;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.middleware.Factory;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.modules.messaging.components.queuelist.IQueueList;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutDto;
import org.jaffa.modules.messaging.components.queueviewer.ui.IQueueViewerListener;
import org.jaffa.modules.messaging.components.queueviewer.ui.QueueViewerComponent;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.component.Component;


/** The controller for the QueueList.
 */
public class QueueListComponent extends Component {
    
    private static Logger log = Logger.getLogger(QueueListComponent.class);
    
    private String m_filter = null;
    private QueueListOutDto m_finderOutDto = null;
    private Exception m_error = null;
    private IQueueList m_tx = null;
    
    
    /** Getter for property filter.
     * @return Value of property filter.
     */
    public String getFilter() {
        return m_filter;
    }
    
    /** Setter for property filter.
     * @param filter New value of property filter.
     */
    public void setFilter(String filter) {
        m_filter = filter;
    }
    
    /** Getter for property finderOutDto.
     * @return Value of property finderOutDto.
     */
    public QueueListOutDto getFinderOutDto() {
        return m_finderOutDto;
    }
    
    /** Getter for property error.
     * @return Value of property error.
     */
    public Exception getError() {
        return m_error;
    }

    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_messaging_queueListResults";
    }
    
    
    /** This should be invoked when done with the component.
     */
    public void quit() {
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        super.quit();
    }
    
    
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        return displayResults();
    }

    public FormKey displayResults() throws ApplicationExceptions, FrameworkException {
        getUserSession().getWidgetCache(getComponentId()).clear();
        try {
            m_finderOutDto = createTx().query(getFilter());
            m_error = null;
        } catch (Exception e) {
            // Render an empty screen with an error indicator
            m_finderOutDto = null;
            m_error = e;
        }
        return getResultsFormKey();
    }

    /** Getter for the Results screen's FormKey.
     * @return the FormKey for the Results screen.
     */
    public FormKey getResultsFormKey() {
        return new FormKey(getResultsFormName(), getComponentId());
    }
    
    public FormKey viewMessages(String queue, MessageModeEnum messageMode) throws ApplicationExceptions, FrameworkException {
        QueueViewerComponent viewComponent = (QueueViewerComponent) run("Jaffa.Messaging.QueueViewer");
        viewComponent.setReturnToFormKey(getResultsFormKey());
        viewComponent.setQueue(queue);
        viewComponent.setMessageMode(messageMode);
        viewComponent.addQueueViewerListener(new IQueueViewerListener() {
            public void processDone(EventObject source) throws ApplicationExceptions, FrameworkException{
                displayResults();
            }
        });
        
        return viewComponent.display();
    }
    
    private IQueueList createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IQueueList) Factory.createObject(IQueueList.class);
        return m_tx;
    }
}