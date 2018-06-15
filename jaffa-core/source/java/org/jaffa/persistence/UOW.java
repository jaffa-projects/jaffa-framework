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
package org.jaffa.persistence;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.engines.IJdbcPersistenceEngine;
import org.jaffa.persistence.engines.IMessagingEngine;
import org.jaffa.persistence.engines.IPersistenceEngine;
import org.jaffa.persistence.engines.MessagingEngineFactory;
import org.jaffa.persistence.engines.PersistenceEngineFactory;
import org.jaffa.persistence.exceptions.AddFailedException;
import org.jaffa.persistence.exceptions.AlreadyLockedObjectException;
import org.jaffa.persistence.exceptions.CommitFailedException;
import org.jaffa.persistence.exceptions.DeleteFailedException;
import org.jaffa.persistence.exceptions.InactiveUowRuntimeException;
import org.jaffa.persistence.exceptions.InvalidUowRuntimeException;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.exceptions.QueryFailedException;
import org.jaffa.persistence.exceptions.RollbackFailedException;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.exceptions.UpdateFailedException;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;

/**
 * The UOW (Unit of Work) is the application developers interface to the persistence layer.
 * Through this all writes, updates and deletes are executed.
 * The UOW also provides the querying mechanism against the persistent store.
 */
public class UOW {

    private static final Logger log = Logger.getLogger(UOW.class);
    private boolean m_inactive = true;
    private IPersistenceEngine m_engine;
    private IMessagingEngine m_messagingEngine;
    private Throwable m_pointOfCreation = new Exception("This exception can be used for pinpointing the code which fails to close an UOW instance");

    /**
     * Creates new UOW. A connection is established with the underlying persistence store.
     *
     * @throws UOWException if any error occurs during the process.
     */
    public UOW() throws UOWException {
        m_engine = PersistenceEngineFactory.newInstance(this);
        m_inactive = false;
    }

    /**
     * Creates new UOW using connection from a parent UOW.
     *
     * @throws UOWException 
     */
    public UOW(UOW parentUow) throws FrameworkException {
        m_inactive = true;
        m_engine = PersistenceEngineFactory.newInstance();
        if ((m_engine instanceof IJdbcPersistenceEngine) && (parentUow.m_engine instanceof IJdbcPersistenceEngine)) {
            ((IJdbcPersistenceEngine) m_engine).initialize(this,((IJdbcPersistenceEngine)parentUow.m_engine).getExistingDataSource());
            m_inactive = false;
        }
    }

    /**
     * Generates an appropriate instance for the input persistentClass.
     * If the persistentClass is a 'Class', then it should implement the 'IPersistent' interface. The persistence engine will simply instantiate the class.
     * If the persistentClass is an 'Interface', then the persistence engine will generate a dynamic proxy, to implement the IPersistent and the 'persistentClass' interfaces.
     *
     * @param persistentClass The actual persistentClass which can represent a 'Class' or an 'Interface'
     * @return an instance implementing the IPersistent interface.
     */
    public IPersistent newPersistentInstance(Class persistentClass) {
        return m_engine.newPersistentInstance(persistentClass);
    }

    /**
     * This is a helper method to determine the actual class which was used to create an IPersistent instance.
     * It is quite possible that the input object is a dynamic proxy.
     *
     * @param persistentObject An IPersistence instance.
     * @return The class which was used for instantiating the instance.
     */
    public Class getActualPersistentClass(Object persistentObject) {
        return m_engine.getActualPersistentClass(persistentObject);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload Any serializable object.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String addMessage(Object payload) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.add(payload, null, null);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload      Any serializable object.
     * @param dependencies Array of dependency transactions.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String addMessage(Object payload, String[] dependencies) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.add(payload, dependencies, null);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload      Any serializable object.
     * @param externalMessage Array of dependency transactions.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String addMessage(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.add(payload, null, externalMessage);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload      Any serializable object.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public String addOutboundMessage(Object payload) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.addOutbound(payload);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload Any serializable object.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public Object addTransaction(Object payload) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.addTransaction(payload, null);
    }

    /**
     * Creates a Jaffa Transaction, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the uow is committed.
     *
     * @param payload      Any serializable object.
     * @param externalMessage Array of dependency transactions.
     * @return id The created transaction key
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public Object addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        ensureActiveState();
        if (m_messagingEngine == null) {
            m_messagingEngine = MessagingEngineFactory.newInstance(this);
        }

        return m_messagingEngine.addTransaction(payload, externalMessage);
    }

    /**
     * Adds an object to the UOW for addition to the persistent store.
     * The persistence engine may choose to add the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     *
     * @param object The object to persist. It should implement the IPersistent interface.
     * @throws AddFailedException if any error occurs during the process.
     */
    public void add(Object object) throws AddFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        preAddChecks(persistentObject);
        persistentObject.setUOW(this);
        m_engine.add(persistentObject, true);
    }

    /**
     * Adds an object to the UOW for updation to the persistent store.
     * The persistence engine may choose to update the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     *
     * @param object The object to update. It should implement the IPersistent interface.
     * @throws UpdateFailedException if any error occurs during the process.
     */
    public void update(Object object) throws UpdateFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        preUpdateChecks(persistentObject);
        m_engine.update(persistentObject, true);
    }

    /**
     * Adds an object to the UOW for deletion from the persistent store.
     * The persistence engine may choose to delete the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     *
     * @param object The object to delete from persistent storage. It should implement the IPersistent interface.
     * @throws DeleteFailedException if any error occurs during the process.
     */
    public void delete(Object object) throws DeleteFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        preDeleteChecks(persistentObject);
        m_engine.delete(persistentObject, true);
    }

    /**
     * Queries the underlying persistent store based on the search profile passed in the {@link Criteria} object.
     *
     * @param criteria search profile for the query.
     * @return a Collection of persistent objects.
     * @throws QueryFailedException    if any error occurs during the process.
     * @throws PostLoadFailedException if any error occurs during the invocation of the PostLoad trigger on the persistent object.
     */
    public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException {
        ensureActiveState();
        criteria.setUow(this);
        return m_engine.query(criteria);
    }

    /**
     * Flushes the add/update/delete queues of the persistence engine.
     * This will have no effect if the persistence engine does not queue the add, update or the delete operations.
     *
     * @throws UOWException if any error occurs.
     */
    public void flush() throws UOWException {
        ensureActiveState();
        m_engine.flush();
    }

    /**
     * Objects that have been added, objects that have been deleted,
     * and objects that have been updated, will all be persisted via
     * an invocation of this method.
     * Note: After a successful commit, this object will free up its connection to the database,
     * and will no longer be available.
     *
     * @throws AddFailedException    if any error occurs during the addition of objects to the persistent store.
     * @throws UpdateFailedException if any error occurs while updating the objects of the persistent store.
     * @throws DeleteFailedException if any error occurs while deleting the objects of the persistent store.
     * @throws CommitFailedException if any error occurs during the commit.
     */
    public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException {
        ensureActiveState();

        Collection deletes = m_engine.getDeletes();
        if (deletes != null) {
            if (m_messagingEngine == null) {
                try {
                    m_messagingEngine = MessagingEngineFactory.newInstance(this);
                } catch (FrameworkException e) {
                    // failed creating the messaging engine, do not fail the overall commit
                } catch (ApplicationExceptions applicationExceptions) {
                    // failed creating the messaging engine, do not fail the overall commit
                }
            }
            if (m_messagingEngine != null) {
                m_messagingEngine.prepareDeletesForCommit(deletes);
            }
        }

        // commit the persistence engine
        m_engine.commit();

        // commit the messaging engine
        if (m_messagingEngine != null) {
            try {
                m_messagingEngine.commit();
            } catch (Exception e) {
                log.fatal("The database transaction has been committed. Exception raised while sending Message(s) to the JMS system", e);
                throw new CommitFailedException(null, e);
            }
        }
        close();
    }

    /**
     * Rollbacks all the additions, deletions, updations.
     * Note: This object will free up its connection to the database, and will no longer be available.
     *
     * @throws RollbackFailedException if any error occurs during the process.
     */
    public void rollback() throws RollbackFailedException {
        if (isActive()) {
            RollbackFailedException ex = null;
            try {
                m_engine.rollback();
            } catch (RollbackFailedException e) {
                ex = e;
            }
            if (m_messagingEngine != null) {
                try {
                    m_messagingEngine.rollback();
                } catch (Exception e) {
                    ex = new RollbackFailedException(null, e);
                }
            }
            // Close the connection, no matter what
            close();
            if (ex != null)
                throw ex;
        }
    }

    /**
     * This will acquire a lock on the database row corrsponding to the input persistent object.
     *
     * @param object The persistent object to be locked. It should implement the IPersistent interface.
     * @throws AlreadyLockedObjectException if the database row has been locked by another process.
     */
    public void acquireLock(Object object) throws AlreadyLockedObjectException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        preAcquireLockChecks(persistentObject);
        m_engine.acquireLock(persistentObject);
    }

    /**
     * Adds a PersistenceLoggingPlugin.
     * The initialize method will be invoked on the input.
     *
     * @param persistenceLoggingPlugin the persistenceLoggingPlugin.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException {
        m_engine.addPersistenceLoggingPlugin(persistenceLoggingPlugin);
    }

    /**
     * Adds a PersistenceLoggingPlugin at the specified position.
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * The initialize method will be invoked on the input.
     *
     * @param index                    index at which the specified element is to be inserted.
     * @param persistenceLoggingPlugin the persistenceLoggingPlugin.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException {
        m_engine.addPersistenceLoggingPlugin(index, persistenceLoggingPlugin);
    }

    /**
     * Removes a PersistenceLoggingPlugin.
     *
     * @param persistenceLoggingPlugin the PersistenceLoggingPlugin.
     * @return true if the PersistenceLoggingPlugin was removed.
     */
    public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) {
        return m_engine.removePersistenceLoggingPlugin(persistenceLoggingPlugin);
    }


    /** Returns the PersistenceLoggingPlugin at the specified position in this list..
     * @param index of the PersistenceLoggingPlugin to return.
     * @return the PersistenceLoggingPlugin at the specified position in this list
     */
    public IPersistenceLoggingPlugin getPersistenceLoggingPlugin(int index) {
        return m_engine.getPersistenceLoggingPlugin(index);
    }

    /**
     * Returns true if the UOW is active. The UOW becomes inactive after a commit or a rollback.
     *
     * @return true if the UOW is active.
     */
    public boolean isActive() {
        return !m_inactive;
    }

    /**
     * Closes the connection and marks the UOW as inactive
     */
    public void close() {
        if (m_engine != null) {
            m_engine.close();
            m_engine = null;
        }
        m_inactive = true;
    }

    /**
     * Throws an InactiveUowRuntimeException if the UOW is inactive
     */
    private void ensureActiveState() throws InactiveUowRuntimeException {
        if (!isActive())
            throw new InactiveUowRuntimeException();
    }

    private void preAddChecks(IPersistent object) {
        if (object.getUOW() != null && object.getUOW() != this)
            throw new InvalidUowRuntimeException();
        if (object.isDatabaseOccurence())
            throw new IllegalArgumentException("Cannot add an object, which already exists in the database.");
    }

    private void preUpdateChecks(IPersistent object) {
        if (object.getUOW() != this)
            throw new InvalidUowRuntimeException();
        if (!object.isDatabaseOccurence())
            throw new IllegalArgumentException("Cannot update an object, which is not already in the database.");
    }

    private void preDeleteChecks(IPersistent object) {
        if (object.getUOW() != this)
            throw new InvalidUowRuntimeException();
        if (!object.isDatabaseOccurence())
            throw new IllegalArgumentException("Cannot delete an object, which is not already in the database.");
    }

    private void preAcquireLockChecks(IPersistent object) {
        if (object.getUOW() != this)
            throw new InvalidUowRuntimeException();
        if (!object.isDatabaseOccurence())
            throw new IllegalArgumentException("Cannot lock an object, which is not already in the database.");
    }

    /**
     * Adds an object to the UOW for addition to the persistent store.
     * The persistence engine may choose to add the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * <p/>
     * NOTE: This is a special version of the 'add' operation. Lifecycle events will not be fired for this operation.
     *
     * @param object The object to persist. It should implement the IPersistent interface.
     * @throws AddFailedException if any error occurs during the process.
     */
    public void addSpecial(Object object) throws AddFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        persistentObject.setUOW(this);
        m_engine.add(persistentObject, false);
    }

    /**
     * Adds an object to the UOW for updation to the persistent store.
     * The persistence engine may choose to update the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * <p/>
     * NOTE: This is a special version of the 'update' operation. Lifecycle events will not be fired for this operation.
     *
     * @param object The object to update. It should implement the IPersistent interface.
     * @throws UpdateFailedException if any error occurs during the process.
     */
    public void updateSpecial(Object object) throws UpdateFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        persistentObject.setUOW(this);
        m_engine.update(persistentObject, false);
    }

    /**
     * Adds an object to the UOW for deletion from the persistent store.
     * The persistence engine may choose to delete the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * <p/>
     * NOTE: This is a special version of the 'delete' operation. Lifecycle events will not be fired for this operation.
     *
     * @param object The object to delete from persistent storage. It should implement the IPersistent interface.
     * @throws DeleteFailedException if any error occurs during the process.
     */
    public void deleteSpecial(Object object) throws DeleteFailedException {
        IPersistent persistentObject = (IPersistent) object;
        ensureActiveState();
        persistentObject.setUOW(this);
        m_engine.delete(persistentObject, false);
    }
}
