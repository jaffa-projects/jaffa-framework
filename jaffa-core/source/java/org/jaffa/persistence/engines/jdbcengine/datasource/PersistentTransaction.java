/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.persistence.engines.jdbcengine.datasource;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.ILifecycleHandler;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCreationException;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.ExceptionHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to hold a connection to the database. It holds collections of objects to be added, updated, deleted or queried.
 * The UOW will add objects to these collections. The JdbcBridge will utilize the collections to perform the relevant operations.
 * It is important to invoke the close() method, which will free up the pooled connection.
 */
public class PersistentTransaction {

    private static final Logger log = Logger.getLogger(PersistentTransaction.class);

    private static final String DEBUG_CLEAR_LOG = "Invoking the clearLog method on the IPersistenceLoggingPlugin instances.";
    private static final String DEBUG_FREEUP_DATASOURCE = "Freeing up the DataSource.";
    private static final String DEBUG_ROLLBACK = "Invoking the rollback on the DataSource.";
    private static final String DEBUG_CLEAR_ALL = "Clearing up all the internal collections for the added, updated, deleted and query objects.";

    private static final String ERROR_1 = "Error thrown when invoking the clearLog method on the IPersistenceLoggingPlugin instance.";
    private static final String ERROR_2 = "Error in obtaining the Persistence Logger instance.";

    private UOW uow;
    private ArrayList<IPersistent> adds;
    private ArrayList<IPersistent> updates;
    private ArrayList<IPersistent> deletes;
    private Criteria query;
    private List<IPersistenceLoggingPlugin> persistenceLoggingPlugins;
    private boolean commitFlag;
    private final DataSourceContainer dataSourceContainer;

    /**
     * Creates a PersistentTransaction. It uses the input Database configuration to acquire a Connection to the database.
     *
     * @param uow      The UOW being processed.
     * @param database The object with the requisite information for acquiring a Connection to the database.
     * @throws DataSourceCreationException if the connection could not be acquired.
     */
    public PersistentTransaction(UOW uow, Database database) throws DataSourceCreationException {
        dataSourceContainer = new DataSourceContainer(database);
        initialize(uow);
    }

    /**
     * Creates a PersistentTransaction for the DataSource provided.
     *
     * @param uow      The UOW being processed.
     * @param dataSource A Datasource with an existing connection.
     * @throws DataSourceCreationException
     */
    public PersistentTransaction(UOW uow, DataSource dataSource) throws DataSourceCreationException {
        dataSourceContainer = new DataSourceContainer(dataSource);
        initialize(uow);
    }

    /**
     * Adds an object to the transaction to be written.
     * The PreAdd trigger for will be invoked and the domain object will be validated before the addition to the transaction.
     *
     * @param object                the object to be created.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws AddFailedException if any error occurs during the validation of the persistent object.
     * @throws IllegalPersistentStateRuntimeException
     *                            this RuntimeException will be thrown if the domain object has been submitted to the UOW for an Add/Update/Delete and commit hasnt yet been performed.
     */
    public void addObject(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException, IllegalPersistentStateRuntimeException {
        if (object.isQueued()) {
            String str = "The domain object has already been submitted to the UOW for an Add/Update/Delete. No more updates can be performed until after a commit";
            log.error(str);
            throw new IllegalPersistentStateRuntimeException(str);
        }

        List<ILifecycleHandler> handlers = null;
        if (object != null) {
            handlers = object.getLifecycleHandlers();
        }

        if (invokeLifecycleEvents) {
            if (log.isDebugEnabled())
                log.debug("Invoking the PreAdd trigger on the Persistent object");
            for (ILifecycleHandler lifeCycleHandler : handlers) {
                lifeCycleHandler.preAdd();
            }
        }

        if (adds == null) {
            adds = new ArrayList<IPersistent>();
        }
        adds.add(object);

        if (log.isDebugEnabled()) {
            log.debug("Added the Persistent object to the ADD collection");
        }

        object.setQueued(true);

        if (invokeLifecycleEvents) {
            // If there is a persistence logging plugin add the object to it
            if (persistenceLoggingPlugins != null) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoking the add method on the IPersistenceLoggingPlugin instances");
                    }

                    for (IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins) {
                        persistenceLoggingPlugin.add(object);
                    }
                } catch (Exception e) {
                    log.error("Error in logging the ADD event", e);
                    throw new AddFailedException(null, e);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking the PostAdd trigger on the Persistent object");
            }

            // Invoke the post add behaviors
            for (ILifecycleHandler lifecycleHandler : handlers) {
                lifecycleHandler.postAdd();
            }
        }
    }

    /**
     * Adds an object to the transaction to be updated.
     * The PreUpdate trigger will be invoked and the domain object will be validated before the updation to the transaction.
     *
     * @param object                the object to be updated.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws UpdateFailedException if any error occurs during the validation of the persistent object.
     * @throws IllegalPersistentStateRuntimeException
     *                               this RuntimeException will be thrown if the domain object has been submitted to the UOW for an Add/Update/Delete and commit hasnt yet been performed.
     */
    public void updateObject(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException, IllegalPersistentStateRuntimeException {
        if (object.isQueued()) {
            String str = "The domain object has already been submitted to the UOW for an Add/Update/Delete. No more updates can be performed until after a commit";
            log.error(str);
            throw new IllegalPersistentStateRuntimeException(str);
        }

        if (!object.isModified()) {
            if (log.isDebugEnabled())
                log.debug("The Persistent object has not been modified. So it will not be added to the Interceptor queue for an Add");
            return;
        }

        List<ILifecycleHandler> handlers = null;
        if (object != null) {
            handlers = object.getLifecycleHandlers();
        }

        if (invokeLifecycleEvents) {
            if (log.isDebugEnabled())
                log.debug("Invoking the PreUpdate trigger on the Persistent object");
            for (ILifecycleHandler lifecycleHandler : handlers) {
                lifecycleHandler.preUpdate();
            }
        }

        if (updates == null) {
            updates = new ArrayList<IPersistent>();
        }
        updates.add(object);

        if (log.isDebugEnabled()) {
            log.debug("Added the Persistent object to the UPDATE collection");
        }

        object.setQueued(true);

        if (invokeLifecycleEvents) {
            // If there is a persistence logging plugin update the object
            if (persistenceLoggingPlugins != null) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoking the update method on the IPersistenceLoggingPlugin instances");
                    }

                    for (IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins) {
                        persistenceLoggingPlugin.update(object);
                    }
                } catch (Exception e) {
                    log.error("Error in logging the UPDATE event", e);
                    throw new UpdateFailedException(null, e);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking the PostUpdate trigger on the Persistent object");
            }

            // Invoke the post update behaviors
            for (ILifecycleHandler lifecycleHandler : handlers) {
                lifecycleHandler.postUpdate();
            }
        }
    }

    /**
     * Adds an object to the transaction to be deleted.
     * The PreDelete trigger will be invoked before the deletion from the transaction.
     *
     * @param object                the object to be deleted.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws DeleteFailedException if any error occurs during the process.
     * @throws IllegalPersistentStateRuntimeException
     *                               this RuntimeException will be thrown if the domain object has been submitted to the UOW for an Add/Update/Delete and commit hasnt yet been performed.
     */
    public void deleteObject(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException, IllegalPersistentStateRuntimeException {
        if (object.isQueued()) {
            String str = "The domain object has already been submitted to the UOW for an Add/Update/Delete. No more updates can be performed until after a commit";
            log.error(str);
            throw new IllegalPersistentStateRuntimeException(str);
        }

        List<ILifecycleHandler> handlers = null;
        if (object != null) {
            handlers = object.getLifecycleHandlers();
        }

        if (invokeLifecycleEvents) {
            if (log.isDebugEnabled())
                log.debug("Invoking the PreDelete trigger on the Persistent object");
            for (ILifecycleHandler lifecycleHandler : handlers) {
                lifecycleHandler.preDelete();
            }
        }

        if (deletes == null) {
            deletes = new ArrayList<IPersistent>();
        }
        deletes.add(object);

        if (log.isDebugEnabled()) {
            log.debug("Added the Persistent object to the DELETE collection");
        }

        object.setQueued(true);

        if (invokeLifecycleEvents) {
            // If there is a persistence logging plugin delete the object
            if (persistenceLoggingPlugins != null) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoking the delete method on the IPersistenceLoggingPlugin instances");
                    }

                    for (IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins) {
                        persistenceLoggingPlugin.delete(object);
                    }
                } catch (Exception e) {
                    log.error("Error in logging the DELETE event", e);
                    throw new DeleteFailedException(null, e);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking the PostDelete trigger on the Persistent object");
            }

            // Invoke the post delete behaviors
            for (ILifecycleHandler lifecycleHandler : handlers) {
                lifecycleHandler.postDelete();
            }
        }
    }

    /**
     * Adds a Criteria object to the transaction for performing a query.
     *
     * @param criteria the Criteria based on which a query is to be performed.
     */
    public void setQuery(Criteria criteria) {
        query = criteria;
    }

    /**
     * Commits all changes executed against the persistent store.
     *
     * @throws CommitFailedException if any error occurs during the commit.
     * @throws SQLException          if any database error occurs.
     */
    public void commit() throws CommitFailedException, SQLException {
        // If there is a persistence logging plugin commit the changes
        if (persistenceLoggingPlugins != null) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Invoking the writeLog method on the IPersistenceLoggingPlugin instances");
                }

                commitFlag = true;

                for (IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins) {
                    persistenceLoggingPlugin.writeLog();
                }
            } catch (Exception e) {
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("Logic Error in writing the logs for a transaction", appExps);
                    }

                    e = appExps;
                } else {
                    log.error("Error in writing the logs for a transaction", e);
                }
                throw new CommitFailedException(null, e);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Invoking the commit on the DataSource");
        }

        // Commit the change to the DB
        DataSource dataSource = dataSourceContainer.get(false);
        if (dataSource != null) {
            dataSource.commit();
        }

        // Clean up changes and free the data source
        clearCollections();
    }

    /**
     * Rollback the changes executed against the persistent store.
     *
     * @throws SQLException if any database error occurs.
     */
    public void rollback() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug(DEBUG_ROLLBACK);
        }

        try {
            DataSource dataSource = dataSourceContainer.get(false);
            if (dataSource != null) {
                dataSource.rollback();
            }
        } finally {
            clearCollections();
        }
    }

    /**
     * This will free up the underlying pooled connection. It will first do an implicit rollback().
     *
     * @throws SQLException if any database error occurs.
     */
    public void close() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug(DEBUG_ROLLBACK);
        }

        try {
            rollback();
        } finally {
            clearLogger();
            dataSourceContainer.free();
        }
    }

    /**
     * Clear the logger
     */
    private void clearLogger() {
        if (persistenceLoggingPlugins != null) {
            if (log.isDebugEnabled()) {
                log.debug(DEBUG_CLEAR_LOG);
            }

            for (final IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins) {
                try {
                    if (persistenceLoggingPlugin != null) {
                        persistenceLoggingPlugin.clearLog();
                    }
                } catch (FrameworkException | ApplicationExceptions e) {
                    log.warn(ERROR_1, e);
                }
            }
        }
    }

    /**
     * Returns the DataSource holding the Connection.
     *
     * @return the DataSource.
     */
    public DataSource getDataSource() {
        return dataSourceContainer.get();
    }

    /**
     * Returns the DataSource holding the Connection.
     *
     * @return the DataSource.
     */
    public DataSource getExistingDataSource() {
        return dataSourceContainer.get(Boolean.FALSE);
    }

    /**
     * Returns a Collection of Persistent objects to be added to the persistent store.
     *
     * @return a Collection of objects to be added.
     */
    public Collection getAdds() {
        return adds;
    }

    /**
     * Returns a Collection of Persistent objects to be updated in the persistent store.
     *
     * @return a Collection of objects to be updated.
     */
    public Collection getUpdates() {
        return updates;
    }

    /**
     * Returns a Collection of Persistent objects to be deleted from the persistent store.
     *
     * @return a Collection of objects to be deleted.
     */
    public Collection getDeletes() {
        return deletes;
    }

    /**
     * Returns a Criteria object to be used for querying the persistent store.
     *
     * @return a Criteria object to be used for queries.
     */
    public Criteria getQuery() {
        return query;
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
        int index = persistenceLoggingPlugins != null ? persistenceLoggingPlugins.size() : 0;
        addPersistenceLoggingPlugin(index, persistenceLoggingPlugin);
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
        persistenceLoggingPlugin.initialize(uow);
        if (persistenceLoggingPlugins == null) {
            persistenceLoggingPlugins = new LinkedList<IPersistenceLoggingPlugin>();
        }

        if (!commitFlag) {
            persistenceLoggingPlugins.add(index, persistenceLoggingPlugin);
        } else {
            log.warn("Prevented adding a new persistentLoggingPlugin during a commit");
        }
    }

    /**
     * Removes a PersistenceLoggingPlugin.
     *
     * @param persistenceLoggingPlugin the PersistenceLoggingPlugin.
     * @return true if the PersistenceLoggingPlugin was removed.
     */
    public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) {
        return persistenceLoggingPlugins.remove(persistenceLoggingPlugin);
    }


    /** Returns the PersistenceLoggingPlugin at the specified position in this list..
     * @param class name of the PersistenceLoggingPlugin to return.
     * @return the PersistenceLoggingPlugin at the specified position in this list
     */
    public IPersistenceLoggingPlugin getPersistenceLoggingPluginByClass(Class<?> clazz){
        for(IPersistenceLoggingPlugin persistenceLoggingPlugin : persistenceLoggingPlugins){
            if(persistenceLoggingPlugin.getClass() == clazz){
                return persistenceLoggingPlugin;
            }
        }
        return null;
    }

    /**
     * Clears the collections that hold the adds, updates, and deletes
     */
    private void clearCollections() {
        if (log.isDebugEnabled()) {
            log.debug(DEBUG_CLEAR_ALL);
        }

        if (adds != null) {
            adds.clear();
        }

        if (updates != null) {
            updates.clear();
        }

        if (deletes != null) {
            deletes.clear();
        }

        query = null;
    }

    /**
     * Creates and initializes IPersistenceLoggingPlugin instances.
     */
    private List<IPersistenceLoggingPlugin> obtainPersistenceLoggingPlugins() throws Exception {
        List<IPersistenceLoggingPlugin> persistenceLoggers = null;
        String implementationClassNames = (String) ContextManagerFactory.instance().getProperty("jaffa.persistence.IPersistenceLoggingPlugin");

        if (implementationClassNames != null) {
            String[] implementationClassNameArray = implementationClassNames.split(",");
            persistenceLoggers = new LinkedList<IPersistenceLoggingPlugin>();
            for (String implementationClassName : implementationClassNameArray) {
                if (log.isDebugEnabled()) {
                    log.debug("Instantiating the IPersistenceLoggingPlugin implemenation class " + implementationClassName);
                }
                IPersistenceLoggingPlugin persistenceLogger = (IPersistenceLoggingPlugin) Class.forName(implementationClassName.trim()).newInstance();
                persistenceLogger.initialize(uow);
                persistenceLoggers.add(persistenceLogger);
            }
        }

        return persistenceLoggers;
    }

    private void initialize(UOW uow) throws DataSourceCreationException {
        this.uow = uow;

        if (log.isDebugEnabled()) {
            log.debug("Acquiring a new DataSource");
        }

        // Create an IPersistenceLoggingPlugin instance, if specified
        try {
            persistenceLoggingPlugins = obtainPersistenceLoggingPlugins();
        } catch (Exception e) {
            log.error(ERROR_2, e);
            throw new DataSourceCreationException(DataSourceCreationException.PERSISTENCE_LOGGER_CREATION_FAILED, null, e);
        }
    }

    /**
     * Class to hold a dta source to allow for lazy initialization
     */
    private static class DataSourceContainer {

        // Database to create the DataSource from
        private final Database m_database;

        // Time the current connection was created
        private long m_connectionCreationTime = 0;

        // DataSource the container is managing
        private DataSource m_dataSource = null;

        /**
         * Creates a Database container
         *
         * @param database Database to create the DataSource from
         */
        public DataSourceContainer(Database database) {
            m_database = database;
        }

        /**
         * Creates a Database container for an e
         *
         * @param dataSource Database to create the DataSource from
         */
        public DataSourceContainer(DataSource dataSource) {
            m_dataSource = dataSource;
            m_database = null;
        }

        /**
         * Gets the data source.  If one has not been created one will be.
         *
         * @return DataSource to connect to the database
         */
        synchronized public DataSource get() {
            return get(true);
        }

        /**
         * Gets the data source this instance is managing;
         *
         * @param createIfNeeded If true a DataSource will be created if one has not been
         * @return DataSource to connect to the database (return can be null if createIfNeeded is false and a DataSource
         *         has not yet been created.
         */
        synchronized public DataSource get(boolean createIfNeeded) {
            // If there is not a data source, create one
            if (m_dataSource == null && createIfNeeded) {
                try {
                    m_dataSource = DataSourceFactory.getDataSource(m_database);
                    m_connectionCreationTime = System.currentTimeMillis();
                } catch (DataSourceCreationException e) {
                    log.error("Error creating connection to DB", e);
                }
            }

            return m_dataSource;
        }

        /**
         * Frees the DataSource this instance is managing
         */
        synchronized public void free() {
            // If the data source has not been initialized, exit early
            if (m_dataSource == null) {
                return;
            }

            if (log.isDebugEnabled()) {
                log.debug(DEBUG_FREEUP_DATASOURCE);
            }

            // Free the data source
            DataSourceFactory.freeDataSource(m_dataSource);

            if (log.isDebugEnabled()) {
                log.debug("Connection open for: " + (System.currentTimeMillis() - m_connectionCreationTime));
            }

            // Clear the data source and connection creation time
            m_connectionCreationTime = 0;
            m_dataSource = null;
        }
    }
}
