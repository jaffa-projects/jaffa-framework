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
package org.jaffa.polling;

import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.user.services.UserContextWrapper;
import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.engines.jdbcengine.LockedApplicationException;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.util.ExceptionHelper;

/**
 * A helper class to poll a database table for new rows. An instance of this
 * class is expected to be invoked periodically, either by a Scheduler or a Java
 * Thread.
 * <p>
 * Once a row is found, this class will mold that row into a databean, and send
 * it to the Messaging system for further processing.
 * <p>
 * A concrete implementation is expected to modify the row, both for successful
 * processing and error conditions, such that the row is not picked up again in
 * subsequent polls.
 */
public abstract class DatabasePoller {

    private static final Logger LOGGER = Logger.getLogger(DatabasePoller.class);
    private static final String INFO_VM_SHUTDOWN_PREFIX = "The virtual-machine shutdown trigger has stopped the ";
    private static final String INFO_VM_SHUTDOWN_SUFFIX = ". Re-start the poller to complete processing of the remained records.";

    /** The virtual-machine shutdown hook flag. */
    private boolean shutdownFlag;

    /**
     * Polls the database for the presence of new rows in a certain table.
     */
    protected void poll(final UOW uow) throws FrameworkException, ApplicationException, ApplicationExceptions {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(this.getClass().getSimpleName() + ": Polling database...");
        final Criteria criteria = new Criteria();
        customizeCriteria(criteria);
        poll(uow.query(criteria));
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Done polling database!");
        }
    }

    /**
     * Polls the database for a row represented by the input domain object. This
     * will reload the row in a new UOW with paranoid locking, and then invoke
     * the process() method on the reloaded domain object.
     * @param domains <code>Collection</code> the collection of the domain
     *            objects.
     */
    protected void poll(final Collection<IPersistent> domains) {
        if (domains != null) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Processing domains: " + domains.size());
            }
            final DatabasePollerShutdownThread shutdownThread = new DatabasePollerShutdownThread(this);
            Runtime.getRuntime().addShutdownHook(shutdownThread);
            for (final Iterator<IPersistent> itr = domains.iterator(); itr.hasNext();) {
                if (shutdownFlag) {
                    System.out.println(INFO_VM_SHUTDOWN_PREFIX + this.getClass().getSimpleName() + INFO_VM_SHUTDOWN_SUFFIX);
                    break;
                }
                poll(itr.next());
            }
            // Interrupts ShutdownThread thread.
            shutdownThread.interrupt();
        }
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Done process domains!");
        }
    }

    /**
     * Polls the database for a row represented by the input domain object. This
     * will reload the row in a new UOW with paranoid locking, and then invoke
     * the process() method on the reloaded domain object.
     * @param domain The domain object.
     */
    protected void poll(final IPersistent domain) {
        UserContextWrapper ucw = null;
        UOW uow = null;
        try {
            // Setup the user context based on the person who created the row in
            // the database.
            ucw = UserContextWrapperFactory.instance(findCreatedBy(domain));

            // Reload the input domain object in a new UOW with paranoid locking
            // NOTE: Another thread may have modified some flags on the
            // underlying record; in which case a
            // primary-key-based query will still reload the object. But we want
            // to avoid re-processing that object.
            // Hence re-apply the original filters
            uow = new UOW();
            final Criteria criteria = PersistentHelper.generateKeyCriteria(domain);
            customizeCriteria(criteria);
            criteria.setLocking(Criteria.LOCKING_PARANOID);
            final Iterator<IPersistent> i = uow.query(criteria).iterator();
            if (i.hasNext()) {
                process(i.next());
                uow.commit();
            } else {
              LOGGER.error(this.getClass().getSimpleName() + ": Unable to reload domain object. It may already have been processed by another thread. " + domain);
            }
        } catch (Exception e) {
            if (ExceptionHelper.extractException(e, LockedApplicationException.class) != null) {
                LOGGER.error(this.getClass().getSimpleName() + ": Error in reloading domain object. It may be locked by another thread. " + domain, e);
            } else
                LOGGER.error(this.getClass().getSimpleName() + ": Error in polling domain object " + domain, e);
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                    LOGGER.error(this.getClass().getSimpleName() + ": Error in closing UOW", e);
            }

            if (ucw != null)
                ucw.unsetContext();
        }
    }

    /**
     * Molds the input domain object into a databean, which is then sent to the
     * messaging system for further processing. The cleanup() method will then
     * be invoked, to ensure that the same row is not picked up in subsequent
     * polls.
     * @param domain The domain object.
     * @throws Exception if any error occurs.
     */
    protected void process(IPersistent domain) throws Exception {
        try {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(this.getClass().getSimpleName() + ": Processing domain object " + domain);

            // Mold the domain object into a databean.
            final Object payload = buildPayload(domain);

            if (LOGGER.isDebugEnabled())
                LOGGER.debug(this.getClass().getSimpleName() + ": Payload being sent to Messaging system: " + payload);

            domain.getUOW().addMessage(payload);

            // Ensure that subsequent polls do not pick up the same row.
            cleanup(domain, null);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getSimpleName() + ": Error in processing domain object " + domain, e);
            cleanup(domain, e);
        }
    }

    /**
     * Add the following to the input Criteria:
     * <ul>
     * <li>The table to be queried.</li>
     * <li>Filters to pick up unprocessed rows.</li>
     * <li>OrderBy clause to pick up the earliest row first.</li>
     * </ul>
     * @param criteria The Criteria to be customized.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected abstract void customizeCriteria(Criteria criteria) throws FrameworkException, ApplicationExceptions, ApplicationException;

    /**
     * Returns the userId of the person who created the row in the database. The
     * userId will be used to setup the appropriate context, within which the
     * processing of the domain object is to happen.
     * @param domain The domain object.
     * @return the userId of the person who created the row in the database.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected abstract String findCreatedBy(IPersistent domain) throws FrameworkException, ApplicationExceptions, ApplicationException;

    /**
     * Molds the input domain object into a databean, which is to be submitted
     * to the Messaging system.
     * <p>
     * It is recommended that the key-information of the domain object be
     * stamped on the payload, so that the corresponding handler can
     * update/delete the domain object after it's done processing.
     * @param domain The domain object.
     * @return the databean representing the domain object.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected abstract Object buildPayload(IPersistent domain) throws FrameworkException, ApplicationExceptions, ApplicationException;

    /**
     * Modifies the input domain object, such that the corresponding row is not
     * picked up in subsequent polls.
     * <p>
     * A concrete implementation is expected to either update some flag on the
     * row, or delete the row entirely.
     * @param domain The domain object.
     * @param e indicates if the processing of the row has been a failure.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected abstract void cleanup(IPersistent domain, Exception e) throws FrameworkException, ApplicationExceptions, ApplicationException;

    /**
     * Sets the virtual-machine shutdown hook flag to true.
     */
    public void stopPoll() {
        shutdownFlag = true;
    }

    /**
     * The DatabasePollerShutdownThread is the thread we pass to the
     * addShutdownHook method.
     */
    private class DatabasePollerShutdownThread extends Thread {

        private DatabasePoller databasePoller;

        /**
         * Allocates a new <code>Thread</code> object. This constructor has the
         * same effect as <code>Thread(null, null, name)</code>.
         * @param databasePoller <code>DatabasePoller</code> a helper class to
         *            poll a database table for new rows.
         */
        public DatabasePollerShutdownThread(final DatabasePoller databasePoller) {
            super(DatabasePollerShutdownThread.class.getSimpleName());
            this.databasePoller = databasePoller;
        }

        /**
         * If this thread was constructed using a separate <code>Runnable</code>
         * run object, then that <code>Runnable</code> object's <code>run</code>
         * method is called; otherwise, this method does nothing and returns.
         */
        public void run() {
            databasePoller.stopPoll();
        }
    }
}