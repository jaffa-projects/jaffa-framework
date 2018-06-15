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
package org.jaffa.persistence.engines;

import java.util.Collection;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.AddFailedException;
import org.jaffa.persistence.exceptions.AlreadyLockedObjectException;
import org.jaffa.persistence.exceptions.CommitFailedException;
import org.jaffa.persistence.exceptions.DeleteFailedException;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.exceptions.QueryFailedException;
import org.jaffa.persistence.exceptions.RollbackFailedException;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.exceptions.UpdateFailedException;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;

/** The interface for a Persistence Engine.
 */
public interface IPersistenceEngine {

    /** This is called by the Persistence Engine when setting up a UOW. It may be used for initializing resources.
     * @param uow The UOW being processed.
     * @throws FrameworkException if an internal error occurs during initialization.
     */
    public void initialize(UOW uow) throws FrameworkException;

    /** Generates an appropriate instance for the input persistentClass.
     * If the persistentClass is a 'Class', then it should implement the 'IPersistent' interface. The persistence engine will simply instantiate the class.
     * If the persistentClass is an 'Interface', then the persistence engine will generate a dynamic proxy, to implement the IPersistent and the 'persistentClass' interfaces.
     * @param persistentClass The actual persistentClass which can represent a 'Class' or an 'Interface'
     * @return an instance implementing the IPersistent interface.
     */
    public IPersistent newPersistentInstance(Class persistentClass);

    /**
     * This is a helper method to determine the actual class which was used to create an IPersistent instance.
     * It is quite possible that the input object is a dynamic proxy.
     * @return The class which was used for instantiating the instance.
     * @param persistentObject An IPersistence instance.
     */
    public Class getActualPersistentClass(Object persistentObject);

    /**
     * Adds an object to the persistent store.
     * The persistence engine may choose to add the object(s) only on a <code>commit</code or on a <code>flush</code>
     * @param object The object to persist.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws AddFailedException if any error occurs during the process.
     */
    public void add(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException;

    /**
     * Updates an object from the persistent store.
     * The persistence engine may choose to update the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * @param object The object to update.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws UpdateFailedException if any error occurs during the process.
     */
    public void update(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException;

    /**
     * Deletes an object from the persistent store.
     * The persistence engine may choose to delete the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * @param object The object to delete from persistent storage.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws DeleteFailedException if any error occurs during the process.
     */
    public void delete(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException;

    /**
     * Queries the underlying persistent store based on the search profile passed in the Criteria object.
     * @param criteria search profile for the query.
     * @return a Collection of persistent objects.
     * @throws QueryFailedException if any error occurs during the process.
     * @throws PostLoadFailedException if any error occurs during the invocation of the PostLoad trigger on the persistent object.
     */
    public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException;

    /**
     * Flushes the add/update/delete queues of the persistence engine.
     * This will have no effect if the persistence engine does not queue the add, update or the delete operations.
     * @throws UOWException if any error occurs.
     */
    public void flush() throws UOWException;

    /**
     * Objects that have been added, objects that have been deleted,
     * and objects that have been updated, will all be persisted via
     * an invocation of this method.
     * @throws AddFailedException if any error occurs during the addition of objects to the persistent store.
     * @throws UpdateFailedException if any error occurs while updating the objects of the persistent store.
     * @throws DeleteFailedException if any error occurs while deleting the objects of the persistent store.
     * @throws CommitFailedException if any error occurs during the commit.
     */
    public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException;

    /**
     * Rollbacks all the additions, deletions, updations.
     * @throws RollbackFailedException if any error occurs during the process.
     */
    public void rollback() throws RollbackFailedException;

    /** Frees up the connection to the database. */
    public void close();

    /**
     * Returns a Collection of Persistent objects to be deleted from the persistent store.
     *
     * @return a Collection of objects to be deleted.
     */
    public Collection getDeletes();

    /**
     * This will acquire a lock on the database row corrsponding to the input persistent object.
     * @param object The persistent object to be locked.
     * @throws AlreadyLockedObjectException if the database row has been locked by another process.
     */
    public void acquireLock(IPersistent object) throws AlreadyLockedObjectException;

    /** Adds a PersistenceLoggingPlugin.
     * The initialize method will be invoked on the input.
     * @param persistenceLoggingPlugin the persistenceLoggingPlugin.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void addPersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException;

    /** Adds a PersistenceLoggingPlugin at the specified position.
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * The initialize method will be invoked on the input.
     * @param index index at which the specified element is to be inserted.
     * @param persistenceLoggingPlugin the persistenceLoggingPlugin.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void addPersistenceLoggingPlugin(int index, IPersistenceLoggingPlugin persistenceLoggingPlugin) throws ApplicationExceptions, FrameworkException;

    /** Removes a PersistenceLoggingPlugin.
     * @param persistenceLoggingPlugin the PersistenceLoggingPlugin.
     * @return true if the PersistenceLoggingPlugin was removed.
     */
    public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin);

    /** Returns the PersistenceLoggingPlugin at the specified position in this list..
     * @param index of the PersistenceLoggingPlugin to return.
     * @return the PersistenceLoggingPlugin at the specified position in this list
     */
    public IPersistenceLoggingPlugin getPersistenceLoggingPlugin(int index);
}
