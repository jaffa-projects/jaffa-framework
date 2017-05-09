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
package org.jaffa.soa.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.user.services.UserContextWrapper;
import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.AlreadyLockedObjectException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;
import org.jaffa.soa.domain.SOAEvent;
import org.jaffa.soa.domain.SOAEventMeta;
import org.jaffa.util.EmailerBean;

/** SOA Event Server for picking up J_SOA_EVENTS records from the application and publish them to the appropriate Topic.
 * <p>
 * Create a mapping of the following kind in jaffa-messaging-config.xml to map SOAEventMessage dataBean to the OutboundEvents topic.
 *   <message dataBean='org.jaffa.soa.services.SOAEventMessage' topicName='OutboundEvents'>
 *     <header>
 *       <param expression='true' name='eventId'>bean.eventId</param>
 *       <param expression='true' name='eventName'>bean.eventName</param>
 *       <param expression='true' name='description'>bean.description</param>
 *       <param expression='true' name='createdOn'>bean.createdOn != null ? bean.createdOn.toString() : ""</param>
 *       <param expression='true' name='createdBy'>bean.createdBy</param>
 *     </header>
 *   </message>
 *
 *   <topic name='OutboundEvents' consumerPolicy='none'/>
 * @deprecated Instead of this server, add SOAEventPoller to the Scheduler with the desired frequency. That'll be easier to manage, as well as work well in a cluster.
 */
public class SOAEventServer {

    private static Logger log = Logger.getLogger(SOAEventServer.class);
    private static final String RULE_FREQUENCY = "jaffa.soa.SOAEventServer.sleepTimeMillis";
    private static SOAEventServerThread m_serverThread;

    /** This will start the the SOAEventServer thread to process the records in J_SOA_EVENTS.
     * If the sleep time when idle is not set, or set to zero the SOAEventServer will not be started.
     */
    public static void start() {
        if (m_serverThread == null || !m_serverThread.isAlive()) {
            // Start the server only if sleep time is provided
            int sleepTimeMillis = findSleepTimeMillis();
            if (sleepTimeMillis <= 0) {
                if (log.isInfoEnabled())
                    log.info("SOA Event Server not started. The Rule '" + RULE_FREQUENCY + "' should be set to a positive number");
            } else {
                if (log.isDebugEnabled())
                    log.debug("Starting SOAEventServerThread");
                m_serverThread = new SOAEventServerThread();
                m_serverThread.start();
            }
        }
    }

    /** Stop the thread. The thread should stop once it has finished its current processing task.
     */
    public static void stop() {
        if (m_serverThread != null) {
            m_serverThread.kill();
            m_serverThread = null;
        }
    }

    /** Returns the Server state.
     * @return the Server state.
     */
    public static String getServerState() {
        return m_serverThread != null && m_serverThread.isAlive() ? m_serverThread.getServerState().toString() : SOAEventServerThread.ServerState.STOPPED.
                toString();
    }

    /** Returns the time at which the current event-processing started.
     * @return the time at which the current event-processing started.
     */
    public static DateTime getStartedOn() {
        if (m_serverThread != null) {
            if (m_serverThread.isAlive())
                return m_serverThread.getStartedOn();
        }
        return null;
    }

    /** Returns the current event being processed.
     * @return the current event being processed.
     */
    public static String getEvent() {
        if (m_serverThread != null) {
            if (m_serverThread.isAlive())
                return m_serverThread.getEvent();
        }
        return null;
    }

    /** Reads the "jaffa.soa.SOAEventServer.sleepTimeMillis" business-rule.
     */
    private static int findSleepTimeMillis() {
        // Determine the sleep time
        int sleepTimeMillis = 0;
        IContextManager contextManager = null;
        try {
            contextManager = ContextManagerFactory.instance();
            contextManager.setThreadContext(null);
            sleepTimeMillis = Integer.parseInt((String) contextManager.getProperty(RULE_FREQUENCY));
            if (log.isDebugEnabled())
                log.debug("Sleep time in milliseconds is " + sleepTimeMillis);
        } catch (NumberFormatException e) {
            log.warn("Rule '" + RULE_FREQUENCY + "' should be a valid number", e);
        } finally {
            if (contextManager != null)
                contextManager.unsetThreadContext();
        }
        return sleepTimeMillis;
    }

    /** This thread monitors the records in J_SOA_EVENTS and publishes them to the appropriate Topic.
     * It will be run as a Daemon thread.
     */
    private static class SOAEventServerThread extends Thread {

        /** An enumeration of Server states. */
        public enum ServerState {

            /** Initial State of the Server. */
            STARTING,
            /** When the Server is searching for Events. */
            QUEUEING,
            /** When the Server is processing an Event. */
            PROCESSING,
            /** When the Server has processed an Event. */
            CLEANUP,
            /** When the Server is sleeping. */
            WAITING,
            /** When the Server is about to stop. */
            STOPPING,
            /** When the Server has stopped. */
            STOPPED
        }
        private static final int RETRY_LIMIT = 5;
        private ServerState m_serverState = ServerState.STARTING;
        private DateTime m_startedOn;
        private String m_event;
        private boolean m_kill;

        /** Creates a new instance.
         * Sets itself as a daemon thread.
         */
        public SOAEventServerThread() {
            setDaemon(true);
        }

        /** Kills this thread by setting an internal flag, and by interrupting the process.
         */
        public void kill() {
            if (log.isDebugEnabled())
                log.debug("Killing SOAEventServerThread");
            m_kill = true;
            interrupt();
        }

        /** Returns the Server state.
         * @return the Server state.
         */
        public ServerState getServerState() {
            return m_serverState;
        }

        /** Sets the Server state.
         * @param serverState the Server state.
         */
        private void setServerState(ServerState serverState) {
            m_serverState = serverState;
        }

        /** Returns the time at which the current event-processing started.
         * @return the time at which the current event-processing started.
         */
        public DateTime getStartedOn() {
            return m_startedOn;
        }

        /** Returns the current event being processed.
         * @return the current event being processed.
         */
        public String getEvent() {
            return m_event;
        }

        /** Sets the current event being processed.
         * @param event the current event being processed.
         */
        private void setEvent(SOAEvent event) {
            if (event == null) {
                m_event = null;
                m_startedOn = null;
            } else {
                m_event = event.toString();
                m_startedOn = new DateTime();
            }
        }

        /** This will monitor J_SOA_EVENTS.
         */
        public void run() {
            int internalErrors = 0;
            Exception lastException = null;
            while (internalErrors < RETRY_LIMIT && !m_kill && !isInterrupted()) {
                UOW uow = null;
                try {
                    uow = new UOW();
                    SOAEvent event = findSOAEvent(uow);
                    if (event != null) {
                        processSOAEvent(event);
                        uow.commit();
                    } else {
                        uow.rollback();
                        int sleepTimeMillis = findSleepTimeMillis();
                        if (sleepTimeMillis <= 0) {
                            if (log.isInfoEnabled())
                                log.info("The Rule '" + RULE_FREQUENCY + "' is not positive. Will stop the SOA Event Server");
                            break;
                        }
                        setServerState(ServerState.WAITING);
                        if (log.isDebugEnabled())
                            log.debug("Waiting for " + sleepTimeMillis + "ms");
                        sleep(sleepTimeMillis);
                    }
                    internalErrors = 0;
                } catch (InterruptedException e) {
                    if (log.isDebugEnabled())
                        log.debug("Interrupted while Waiting", e);
                    break;
                } catch (Exception e) {
                    ++internalErrors;
                    lastException = e;
                    log.error("Internal Error [" + internalErrors + "]", e);
                } finally {
                    try {
                        if (uow != null)
                            uow.rollback();
                    } catch (FrameworkException e) {
                        log.warn("Exception thrown while closing a UOW", e);
                    }
                }
            }
            if (log.isDebugEnabled())
                log.debug("Stopping PrintServerThread");
            setServerState(ServerState.STOPPING);
            if (internalErrors == RETRY_LIMIT) {
                if (log.isDebugEnabled())
                    log.debug("Reporting internal error by sending an email to the Administrator");
                StringBuffer body = new StringBuffer("An Internal Error Caused the SOA Event Server to Self-Terminate...\n\n");
                StringWriter sw = new StringWriter();
                lastException.printStackTrace(new PrintWriter(sw));
                sw.flush();
                body.append(sw.toString()).append('\n').append('\n');
                EmailerBean eb = new EmailerBean();
                try {
                    eb.sendMail(new String[]{eb.getAdministrator()}, "ERROR - SOA Event Server Self-Terminated", body.
                            toString());
                } catch (Exception e) {
                    log.fatal("Cannot email error messages to Administrator " + eb.getAdministrator(), e);
                }
            }
            setServerState(ServerState.STOPPED);
        }

        /** Returns the earliest record from J_SOA_EVENTS with an exclusive lock.
         * @returns the earliest record from J_SOA_EVENTS with an exclusive lock.
         */
        private SOAEvent findSOAEvent(UOW uow) throws FrameworkException {
            if (log.isDebugEnabled())
                log.debug("Scanning SOAEvents");
            setServerState(ServerState.QUEUEING);
            Criteria criteria = new Criteria();
            criteria.setTable(SOAEventMeta.getName());
            criteria.addOrderBy(SOAEventMeta.CREATED_ON);
            criteria.addOrderBy(SOAEventMeta.EVENT_ID);
            for (Iterator i = uow.query(criteria).iterator(); i.hasNext();) {
                SOAEvent event = (SOAEvent) i.next();
                try {
                    uow.acquireLock(event);
                    return event;
                } catch (AlreadyLockedObjectException e) {
                    if (log.isDebugEnabled())
                        log.debug("Could not acquire a lock on " + event);
                }
            }
            return null;
        }

        /** Publishes a Message, based on the Event, to the appropriate Topic.
         */
        private void processSOAEvent(SOAEvent event) throws FrameworkException {
            if (log.isDebugEnabled())
                log.debug("Processing " + event);
            UserContextWrapper ucw = null;
            try {
                setServerState(ServerState.PROCESSING);
                setEvent(event);
                ucw = UserContextWrapperFactory.instance(event.getCreatedBy());
                publishMessage(event);
            } catch (Exception e) {
                log.error("Failed To Process event " + event, e);
                if (log.isDebugEnabled())
                    log.debug("Reporting error by sending an email to the Administrator");
                StringBuffer body = new StringBuffer("An Internal Error occured in the SOA Event Server while processing the SOAEvent\n");
                body.append(event).append('\n').append('\n');
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                sw.flush();
                body.append(sw.toString()).append('\n').append('\n');
                EmailerBean eb = new EmailerBean();
                try {
                    eb.sendMail(new String[] {eb.getAdministrator()}, "ERROR - SOA Event Server", body.toString());
                } catch (Exception e2) {
                    log.fatal("Cannot email error message to Administrator " + eb.getAdministrator(), e2);
                }
            } finally {
                cleanup(event);
                if (ucw != null)
                    ucw.unsetContext();
            }
        }

        /** Deletes the event.
         */
        private void cleanup(SOAEvent event) throws FrameworkException {
            if (log.isDebugEnabled())
                log.debug("Deleting " + event);
            setServerState(ServerState.CLEANUP);
            setEvent(null);
            event.getUOW().delete(event);
        }

        /** Creates and publishes a Message based on the input event.
         */
        private void publishMessage(SOAEvent event) throws Exception {
            if (log.isDebugEnabled())
                log.debug("Creating message for the event " + event);
            event.getUOW().addMessage(new SOAEventQueueMessage(event));
        }
    }
}
