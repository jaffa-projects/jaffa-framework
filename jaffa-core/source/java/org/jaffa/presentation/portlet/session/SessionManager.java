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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.presentation.portlet.session;

import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.jaffa.config.Config;
import java.util.MissingResourceException;
import org.jaffa.session.ContextManagerFactory;

/** This class holds information about all the UserSession objects in use currently. This may be used as an administration tool.
 */
public class SessionManager {

    /** Name of the context rule for setting the Component Garbage Collection Frequency (in minutes) */
    public static final String RULE_GC_FREQUENCY = "jaffa.session.componentGarbageCollection.frequencyInMinutes";
    /** Name of the context rule for setting the Component Garbage Collection Timeout Period (in minutes) */
    public static final String RULE_GC_TIMEOUT = "jaffa.session.componentGarbageCollection.timeOutInMinutes";

    private static Logger log = Logger.getLogger(SessionManager.class);
    private static IdleComponentGarbageCollector m_idleComponentGarbageCollector = null;
    private static HashMap m_users = new HashMap();
    private static long m_sessionId = 0;

    /** Adds a UserSession. It assigns a unique id to the UserSession before adding it to the internal cache.
     * @param s The UserSession.
     */
    public static void addSession(UserSession s) {
        s.setSessionId( getNextId() );
        m_users.put(s.getSessionId() , s);
    }

    /** Removes a UserSession.
     * @param s The UserSession.
     */
    public static void removeSession(UserSession s) {
        m_users.remove( s.getSessionId() );
    }

    /** Returns an array of all the UserSession objects in use.
     * @return an array of all the UserSession objects in use.
     */
    public static UserSession[] getSessions() {
        return ( UserSession[] ) m_users.values().toArray(new UserSession[0]);
    }

    /** Returns the UserSession object for a sessionId.
     * @param sessionId The id identifying a UserSession.
     * @return the UserSession object for a sessionId.
     */
    public static UserSession getSession(String sessionId) {
        return ( UserSession ) m_users.get(sessionId);

    }

    private static synchronized String getNextId() {
        return Long.toString(++m_sessionId);
    }


    /** This will perform garbage collection of idle components. Idle components are those components which have had no activity performed in the last 'timeOutMinutes' minutes.
     * This will start a Thread which runs every  'timeOutMinutes' minutes
     */
    public static void startGarbageCollectionOfIdleComponents() {
        if (m_idleComponentGarbageCollector == null) {
            int frequency = getComponentGarbageCollectionFrequency();
            int timeOut = getComponentGarbageCollectionTimeOut();
            if (frequency <= 0 || timeOut <= 0) {
                if (log.isInfoEnabled())
                    log.info("Component garbage collection not started. Values for Component garbage collection Frequency and TimeOut should be positive numbers.");
            } else {
                Thread m_idleComponentGarbageCollector = new IdleComponentGarbageCollector(frequency, timeOut);
                m_idleComponentGarbageCollector.start();
            }
        }
    }

    /** This stops the Thread that was started by the startGarbageCollectionOfIdleComponents() method.
     */
    public static void stopGarbageCollectionOfIdleComponents() {
        if (m_idleComponentGarbageCollector != null) {
            m_idleComponentGarbageCollector.m_stopThread = true;
            m_idleComponentGarbageCollector = null;
        }
    }


    private static int getComponentGarbageCollectionFrequency() {
        int frequency = 0;
        String prop = (String) ContextManagerFactory.instance().getProperty(RULE_GC_FREQUENCY);
		if(prop==null)
            try {
                prop = (String) Config.getProperty(Config.PROP_PRESENTATION_COMPONENT_GARBAGE_COLLECTION_FREQUENCY_IN_MINUTES);
            } catch (MissingResourceException e) {
                // do nothing
            }
        if (prop != null && prop.trim().length() > 0)
            frequency = Integer.parseInt(prop);
        return frequency;
    }

    private static int getComponentGarbageCollectionTimeOut() {
        int timeOut = 0;
        String prop = (String) ContextManagerFactory.instance().getProperty(RULE_GC_TIMEOUT);
		if(prop==null)
            try {
                prop = (String) Config.getProperty(Config.PROP_PRESENTATION_COMPONENT_GARBAGE_COLLECTION_TIME_OUT_IN_MINUTES);
            } catch (MissingResourceException e) {
                // do nothing
            }
        if (prop != null && prop.trim().length() > 0)
            timeOut = Integer.parseInt(prop);
        return timeOut;
    }

    /** This thread will be run as a Daemon. */
    private static class IdleComponentGarbageCollector extends Thread {

        private int m_frequency;
        private int m_timeOut;
        private boolean m_stopThread = false;

        private IdleComponentGarbageCollector(int frequency, int timeOut) {
            m_frequency = frequency;
            m_timeOut = timeOut;
            setDaemon(true);
        }

        /** This will perform garbage collection of idle components every 'timeOutMinutes' minutes.
         */
        public void run() {
            while (!m_stopThread) {
                try {
                    sleep(m_frequency * 60 * 1000);
                } catch (InterruptedException e) {
                    // do nothing
                }

                try {
                    if (log.isDebugEnabled())
                        log.debug("Starting the garbage collection of idle components");

                    for (Iterator i = m_users.values().iterator(); i.hasNext();) {
                        UserSession us = (UserSession) i.next();
                        if (log.isDebugEnabled())
                            log.debug("Starting garbage collection of idle components for the user " + us.getUserId() + " having the sessionid " + us.getSessionId());
                        us.garbageCollectIdleComponents(m_timeOut);
                    }

                    if (log.isDebugEnabled())
                        log.debug("Finished the garbage collection of idle components");
                } catch (Exception e) {
                    // just log an info message and continue
                    if (log.isInfoEnabled())
                        log.info("Exception thrown during garbage collection of idle components", e);
                }
            }
        }
    }


}
