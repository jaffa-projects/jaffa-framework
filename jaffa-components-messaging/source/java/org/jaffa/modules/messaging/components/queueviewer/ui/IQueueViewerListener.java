/*
 * IQueueViewerListener.java
 *
 * Created on March 22, 2007, 3:46 AM
 *
 */

package org.jaffa.modules.messaging.components.queueviewer.ui;

import java.util.EventListener;
import java.util.EventObject;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** The QueueViewerComponent invokes the processDone() method of registered instances of this listener on any action
 */
public interface IQueueViewerListener extends EventListener  {
    
    /** The ImportComponent invokes the processDone() method of registered instances of this listener on any action
     * @param source The EventObject which will contain the component on which any Event occurred.
     */
    public void processDone(EventObject source) throws ApplicationExceptions, FrameworkException;    
}
