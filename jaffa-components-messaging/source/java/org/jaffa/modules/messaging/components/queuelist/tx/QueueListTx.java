package org.jaffa.modules.messaging.components.queuelist.tx;

import java.util.Collection;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

import org.jaffa.modules.messaging.services.*;
import javax.jms.Message;

import org.jaffa.modules.messaging.components.queuelist.IQueueList;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutDto;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto;
import org.jaffa.modules.messaging.services.configdomain.ConsumerPolicy;
import org.jaffa.modules.messaging.services.configdomain.QueueInfo;

/** Finder for QueueAdministration objects.
 */
public class QueueListTx implements IQueueList {
    
    private static Logger log = Logger.getLogger(QueueListTx.class);
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy() {
    }
    
    /** Searches for QueueAdministration objects.
     * @param filter The optional filter is used for returning matching queues only.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public QueueListOutDto query(String filter)
    throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input filter: " + filter);
        
        // Convert the domain objects into the outbound dto
        QueueListOutDto output = buildDto(filter);
        
        // Print Debug Information for the output
        if (log.isDebugEnabled())
            log.debug("Output: " + output);
        
        return output;
    }

    private QueueListOutDto buildDto(String filter) throws FrameworkException, ApplicationExceptions {
        QueueListOutDto output = new QueueListOutDto();
        return output;
    }
}