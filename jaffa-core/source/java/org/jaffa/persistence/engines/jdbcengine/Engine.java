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
package org.jaffa.persistence.engines.jdbcengine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.engines.IJdbcPersistenceEngine;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.datasource.DataSource;
import org.jaffa.persistence.engines.jdbcengine.datasource.PersistentTransaction;
import org.jaffa.persistence.engines.jdbcengine.interceptor.AbstractInterceptor;
import org.jaffa.persistence.engines.jdbcengine.interceptor.AddInterceptor;
import org.jaffa.persistence.engines.jdbcengine.interceptor.DeleteInterceptor;
import org.jaffa.persistence.engines.jdbcengine.interceptor.LockInterceptor;
import org.jaffa.persistence.engines.jdbcengine.interceptor.QueryInterceptor;
import org.jaffa.persistence.engines.jdbcengine.interceptor.UpdateInterceptor;
import org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceFactory;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.JdbcBridge;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.ExceptionHelper;

/**
 * An implementation of the IPersistenceEngine interface. This uses the JDBC API for persistence.
 */
public class Engine implements IJdbcPersistenceEngine {

    private static final Logger log = Logger.getLogger(Engine.class);
    private static final String DEFAULT_DATA_SOURCE_NAME = "default";
    private static final String CUSTOM_SQL_ERROR_CODE_RULE_NAME = "jaffa.persistence.jdbcengine.customSqlErrorCodeRange";
    private static Integer c_customSqlErrorCodeStart = null;
    private static Integer c_customSqlErrorCodeEnd = null;
    // this will be the 1st interceptor in the chain.
    private static AbstractInterceptor c_interceptor = null;

    static {
        if (log.isDebugEnabled())
            log.debug("Initializing the ConfigurationService");
        ConfigurationService.getInstance();

        // create the Interceptor chain
        createInterceptorChain();

        // Determine the customSqlErrorRange
        String customSqlErrorCodeRange = (String) ContextManagerFactory.instance().getProperty(CUSTOM_SQL_ERROR_CODE_RULE_NAME);
        if (customSqlErrorCodeRange != null) {
            String[] rangeBoundary = customSqlErrorCodeRange.split(",");
            if (rangeBoundary.length == 2) {
                try {
                    int start = Parser.parseInteger(rangeBoundary[0]).intValue();
                    int end = Parser.parseInteger(rangeBoundary[1]).intValue();
                    if (start <= end) {
                        c_customSqlErrorCodeStart = new Integer(start);
                        c_customSqlErrorCodeEnd = new Integer(end);
                        if (log.isInfoEnabled())
                            log.info("The JdbcEngine will perform conversion of SQLException to ApplicationException when it encounters ErrorCodes in the range " + c_customSqlErrorCodeStart + " to " + c_customSqlErrorCodeEnd);
                    } else
                        log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers, the first being <= the second. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
                } catch (Exception e) {
                    log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers. The JdbcEngine will not perform any conversion of SQLException to ApplicationException", e);
                }
            } else
                log.warn("Invalid value '" + customSqlErrorCodeRange + "' found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. It should be a comma-separated list of 2 integers. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
        } else if (log.isInfoEnabled())
            log.info("No value found for the rule '" + CUSTOM_SQL_ERROR_CODE_RULE_NAME + "'. The JdbcEngine will not perform any conversion of SQLException to ApplicationException");
    }

    // The PersistentTransaction object associated with an Engine, which in turn is associated to an UOW
    private PersistentTransaction m_pt = null;

    /**
     * This is called by the Persistence Engine when setting up a UOW. It may be used for initializing resources.
     *
     * @param uow The UOW being processed.
     * @throws FrameworkException if an internal error occurs during initialization.
     */
    public void initialize(UOW uow) throws FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Acquiring the Database configuration for: " + DEFAULT_DATA_SOURCE_NAME);
        Database database = ConfigurationService.getInstance().getDatabase(DEFAULT_DATA_SOURCE_NAME);

        if (log.isDebugEnabled())
            log.debug("Creating a PersistentTransaction for the database: " + DEFAULT_DATA_SOURCE_NAME);
        m_pt = new PersistentTransaction(uow, database);
    }

    /**
     * instantiates a PersistentTransaction using the uow and existing datasource provided
     *
     * @param uow The UOW being processed.
     * @throws FrameworkException if an internal error occurs during initialization.
     */
    @Override
    public void initialize(UOW uow, DataSource dataSource) throws FrameworkException {
        m_pt = new PersistentTransaction(uow, dataSource);
    }

    /**
     * returns the datasource for the persistence transaction
     */
    @Override
    public DataSource getExistingDataSource() {
        return m_pt.getExistingDataSource();
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
        return PersistentInstanceFactory.newPersistentInstance(persistentClass);
    }

    /**
     * This is a helper method to determine the actual class which was used to create an IPersistent instance.
     * It is quite possible that the input object is a dynamic proxy.
     *
     * @param persistentObject The object which implements the IPersistent instance.
     * @return The class which was used for instantiating the instance.
     */
    public Class getActualPersistentClass(Object persistentObject) {
        return PersistentInstanceFactory.getActualPersistentClass((IPersistent) persistentObject);
    }

    /**
     * Adds an object to the persistent store.
     * The object(s) will be added only on a <code>commit</code>.
     *
     * @param object                The object to persist.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws AddFailedException if any error occurs during the process.
     */
    public void add(IPersistent object, boolean invokeLifecycleEvents) throws AddFailedException {
        try {
            if (log.isDebugEnabled())
                log.debug("Invoking the add on the PersistentTransaction");
            m_pt.addObject(object, invokeLifecycleEvents);
        } catch (AddFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new AddFailedException(null, appExps) : e;
        }
    }

    /**
     * Updates an object from the persistent store.
     * The object(s) will be updated only on a <code>commit</code>.
     *
     * @param object                The object to update.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws UpdateFailedException if any error occurs during the process.
     */
    public void update(IPersistent object, boolean invokeLifecycleEvents) throws UpdateFailedException {
        try {
            if (log.isDebugEnabled())
                log.debug("Invoking the update on the PersistentTransaction");
            m_pt.updateObject(object, invokeLifecycleEvents);
        } catch (UpdateFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new UpdateFailedException(null, appExps) : e;
        }
    }

    /**
     * Deletes an object from the persistent store.
     * The object(s) will be deleted only on a <code>commit</code>.
     *
     * @param object                The object to delete from persistent storage.
     * @param invokeLifecycleEvents Lifecycle events will be invoked if this parameter is true.
     * @throws DeleteFailedException if any error occurs during the process.
     */
    public void delete(IPersistent object, boolean invokeLifecycleEvents) throws DeleteFailedException {
        try {
            if (log.isDebugEnabled())
                log.debug("Invoking the delete on the PersistentTransaction");
            m_pt.deleteObject(object, invokeLifecycleEvents);
        } catch (DeleteFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new DeleteFailedException(null, appExps) : e;
        }
    }

    /**
     * Queries the underlying persistent store based on the search profile passed in the Criteria object.
     * This will execute the Add, Update, Delete interceptors before performing the query.
     *
     * @param criteria search profile for the query.
     * @return a Collection of persistent objects.
     * @throws QueryFailedException    if any error occurs during the process.
     * @throws PostLoadFailedException if any error occurs during the invocation of the PostLoad trigger on the persistent object.
     */
    public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException {
        if (log.isDebugEnabled())
            log.debug("Invoking the query on the PersistentTransaction");
        m_pt.setQuery(criteria);

        try {
            if (log.isDebugEnabled())
                log.debug("Initiating the Interceptor chain with " + c_interceptor.getClass().getName());
            return (Collection) c_interceptor.invoke(m_pt);
        } catch (QueryFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new QueryFailedException(null, appExps) : e;
        } catch (PostLoadFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new PostLoadFailedException(null, appExps) : e;
        } catch (PreAddFailedException e) {
            throw new QueryFailedException(null, e.getCause() instanceof DuplicateKeyException ? e.getCause() : e);
        } catch (UOWException e) {
            // wrap all other uow exceptions inside the QueryFailedException
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new QueryFailedException(null, appExps) : new QueryFailedException(null, e);
        }
    }

    /**
     * Flushes the add/update/delete queues of the persistence engine.
     *
     * @throws UOWException if any error occurs.
     */
    public void flush() throws UOWException {
        try {
            if (log.isDebugEnabled())
                log.debug("Flushing the add/update/delete queues by initiating the Interceptor chain with " + c_interceptor.getClass().getName());
            c_interceptor.invoke(m_pt);
            m_pt.getDataSource().clearObjectCache();
        } catch (UOWException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new UpdateFailedException(null, appExps) : e;
        }
    }

    /**
     * Objects that have been added, objects that have been deleted,
     * and objects that have been updated, will all be persisted via
     * an invocation of this method.
     *
     * @throws AddFailedException    if any error occurs during the addition of objects to the persistent store.
     * @throws UpdateFailedException if any error occurs while updating the objects of the persistent store.
     * @throws DeleteFailedException if any error occurs while deleting the objects of the persistent store.
     * @throws CommitFailedException if any error occurs during the commit.
     */
    public void commit() throws AddFailedException, UpdateFailedException, DeleteFailedException, CommitFailedException {
        try {
            if (log.isDebugEnabled())
                log.debug("Initiating the Interceptor chain with " + c_interceptor.getClass().getName());
            c_interceptor.invoke(m_pt);

            if (log.isDebugEnabled())
                log.debug("Invoking the commit on the PersistentTransaction");
            m_pt.commit();
        } catch (AddFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new AddFailedException(null, appExps) : e;
        } catch (UpdateFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new UpdateFailedException(null, appExps) : e;
        } catch (DeleteFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new DeleteFailedException(null, appExps) : e;
        } catch (CommitFailedException e) {
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new CommitFailedException(null, appExps) : e;
        } catch (UOWException e) {
            // wrap all other uow exceptions inside the CommitFailedException
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new CommitFailedException(null, appExps) : new CommitFailedException(null, e);
        } catch (SQLException e) {
            log.error("Error in committing the transaction", e);
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new CommitFailedException(null, appExps) : new CommitFailedException(null, e);
        }
    }

    /**
     * Rollbacks all the additions, deletions, updations.
     *
     * @throws RollbackFailedException if any error occurs during the process.
     */
    public void rollback() throws RollbackFailedException {
        try {
            if (log.isDebugEnabled())
                log.debug("Invoking the rollback on the PersistentTransaction");
            m_pt.rollback();
        } catch (SQLException e) {
            String str = "Error in executing a rollback on the transaction";
            log.error(str, e);
            throw new RollbackFailedException(null, e);
        }
    }

    /**
     * Frees up the connection to the database.
     */
    public void close() {
        try {
            if (log.isDebugEnabled())
                log.debug("Invoking the close on the PersistentTransaction");
            m_pt.close();
        } catch (SQLException e) {
            String str = "Error in closing the transaction. But continuing with execution.";
            log.error(str, e);
        }
    }

    /**
     * This will acquire a lock on the database row corrsponding to the input persistent object.
     *
     * @param object The persistent object to be locked.
     * @throws AlreadyLockedObjectException if the database row has been locked by another process.
     */
    public void acquireLock(IPersistent object) throws AlreadyLockedObjectException {
        try {
            if (object.isLocked()) {
                if (log.isDebugEnabled())
                    log.debug("Already locked object " + object);
            } else {
                if (log.isDebugEnabled())
                    log.debug("Invoking JdbcBridge.executeLock() for the object " + object);
                JdbcBridge.executeLock(object, m_pt.getDataSource());
            }
        } catch (Exception e) {
            log.error("Error while locking the Persistent object in the database: " + object, e);
            ApplicationExceptions appExps = extractApplicationExceptions(e);
            throw appExps != null ? new AlreadyLockedObjectException(null, appExps) : new AlreadyLockedObjectException(null, e);
        }
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
        m_pt.addPersistenceLoggingPlugin(persistenceLoggingPlugin);
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
        m_pt.addPersistenceLoggingPlugin(index, persistenceLoggingPlugin);
    }

    /**
     * Removes a PersistenceLoggingPlugin.
     *
     * @param persistenceLoggingPlugin the PersistenceLoggingPlugin.
     * @return true if the PersistenceLoggingPlugin was removed.
     */
    public boolean removePersistenceLoggingPlugin(IPersistenceLoggingPlugin persistenceLoggingPlugin) {
        return m_pt.removePersistenceLoggingPlugin(persistenceLoggingPlugin);
    }

    /** Returns the PersistenceLoggingPlugin at the specified position in this list..
     * @param class name of the PersistenceLoggingPlugin to return.
     * @return the PersistenceLoggingPlugin at the specified position in this list
     */
    public IPersistenceLoggingPlugin getPersistenceLoggingPluginByClass(Class<?> clazz) {
        return m_pt.getPersistenceLoggingPluginByClass(clazz);
    }

    /**
     * Returns a Collection of Persistent objects to be deleted from the persistent store.
     *
     * @return a Collection of objects to be deleted.
     */
    public Collection getDeletes() {
        return m_pt != null ? m_pt.getDeletes() : new ArrayList();
    }

    /**
     * Creates an InterceptorChain and assign the start of the chain to the static c_interceptor.
     */
    private static void createInterceptorChain() {
        AbstractInterceptor[] interceptors = new AbstractInterceptor[]{
                new AddInterceptor(),
                new LockInterceptor(),
                new UpdateInterceptor(),
                new DeleteInterceptor(),
                new QueryInterceptor()
        };

        if (log.isDebugEnabled())
            log.debug("Creating the Interceptor chain");

        AbstractInterceptor previousInterceptor = null;
        for (int i = 0; i < interceptors.length; i++) {
            if (previousInterceptor == null) {
                // 1st interceptor of the chain
                previousInterceptor = interceptors[i];
                c_interceptor = interceptors[i];
            } else {
                previousInterceptor.setNextInterceptor(interceptors[i]);
                previousInterceptor = interceptors[i];
            }
        }

        if (log.isDebugEnabled()) {
            StringBuffer buf = new StringBuffer("Created the interceptor chain:\n");
            AbstractInterceptor interceptor = c_interceptor;
            boolean firstPass = true;
            while (interceptor != null) {
                if (firstPass)
                    firstPass = false;
                else
                    buf.append(" --> ");
                buf.append(interceptor.getClass().getName());
                interceptor = interceptor.getNextInterceptor();
            }
            log.debug(buf.toString());
        }
    }

    /**
     * This method will loop through the input exception and its cause, looking for an instance of ApplicationException or ApplicationExceptions.
     * If ApplicationException is found, it'll be returned wrapped inside a new ApplicationExceptions instance.
     * If ApplicationExceptions is found, it'll be returned as is.
     * It will return an instance of LockedApplicationException wrapped inside a new ApplicationExceptions instance if any lockign errors had occured.
     * It will then loop through the cause of the input exception, looking for an instance of SQLException
     * If the error code for the SQLException falls in the range specified by the global-rule 'jaffa.persistence.jdbcengine.customSqlErrorCodeRange',
     * an instance of SQLApplicationException will be returned wrapped inside a new ApplicationExceptions instance.
     * Else a null will be returned.
     *
     * @param exception The input exception.
     * @return an instance of ApplicationExceptions if found in the cause stack of the input exception.
     */
    public static ApplicationExceptions extractApplicationExceptions(Throwable exception) {
        ApplicationExceptions appExps = null;

        // Check for ApplicationException(s)
        appExps = ExceptionHelper.extractApplicationExceptions(exception);

        // Check for AlreadyLockedObjectException
        if (appExps == null) {
            AlreadyLockedObjectException e = (AlreadyLockedObjectException) ExceptionHelper.extractException(exception, AlreadyLockedObjectException.class);
            if (e != null) {
                if (log.isDebugEnabled())
                    log.debug("Convert AlreadyLockedObjectException to an ApplicationException");
                appExps = new ApplicationExceptions(new LockedApplicationException(exception));
            }
        }

        // Check for SQLException
        if (appExps == null) {
            SQLException sqlException = (SQLException) ExceptionHelper.extractException(exception, SQLException.class);
            if (sqlException != null) {
                if ("40001".equals(sqlException.getSQLState()) || "61000".equals(sqlException.getSQLState())) {
                    if (log.isDebugEnabled())
                        log.debug("Found a SQL for locking errors: " + sqlException.getSQLState());
                    appExps = new ApplicationExceptions(new LockedApplicationException(exception));
                } else {
                    int errorCode = sqlException.getErrorCode();
                    if (c_customSqlErrorCodeStart != null) {
                        if (errorCode >= c_customSqlErrorCodeStart.intValue() && errorCode <= c_customSqlErrorCodeEnd.intValue()) {
                            //Extract the first line from the message, since we do not want to pass the SQL stacktrace
                            String reason = sqlException.getMessage();
                            if (reason != null) {
                                int i = reason.indexOf('\n');
                                if (i > -1)
                                    reason = reason.substring(0, i);
                            }
                            if (log.isDebugEnabled())
                                log.debug("Convert SQLException to SQLApplicationException since it has a custom sqlErrorCode of " + errorCode);
                            appExps = new ApplicationExceptions(new SQLApplicationException(reason, exception));
                        } else {
                            if (log.isDebugEnabled())
                                log.debug("Ignoring SQLException since its ErrorCode is not custom: " + errorCode);
                        }
                    }
                }
            }
        }
        return appExps;
    }
}
