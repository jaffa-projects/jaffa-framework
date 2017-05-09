package org.jaffa.modules.messaging.components.queuelist;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutDto;

/** Interface for QueueList components.
 */
public interface IQueueList {
    
    /** Searches for QueueAdministration objects.
     * @param filter The optional filter is used for returning matching queues only.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public QueueListOutDto query(String filter) throws FrameworkException, ApplicationExceptions;

    /** This should be invoked, when done with the component.
     */
    public void destroy();
}
