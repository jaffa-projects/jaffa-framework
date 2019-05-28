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

package org.jaffa.persistence.engines.jdbcengine.datasource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jaffa.datatypes.Parser;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCursorRuntimeException;
import org.jaffa.persistence.engines.jdbcengine.paging.IPagingPlugin;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.session.ContextManagerFactory;

/** Encapsulates the connection to a data storage mechanism */
public class DataSource {
    private static final Logger log = Logger.getLogger(DataSource.class);
    private static final String DISABLE_OBJECT_CACHE_RULE_NAME = "jaffa.persistence.jdbcengine.disableObjectCache";

    /** Our actual connection to the database. This connection will be 'close'd on a close().*/
    private Connection m_connection;

    /** The size of the hitlist.*/
    private Integer m_hitlistSize;

    /** The engine type as defined in init.xml*/
    private String m_engineType;

    private Boolean m_usePreparedStatement;

    /** This will hold a Map of Statement objects and corresponding ResultSet objects, created against this connection.
     * They will all be 'close'd, on a commit()/rollback(), and the Map cleared.
     * The Statement object can also be closed manually by calling the closeStatement() method.
     * Its a good idea to do so, when the ResultSet has been completely read.
     */
    private Map m_statements;

    /** The following cache will maintain a cache of objects that have been retrieved so far.
     * The objects are cached by their serializedKey.
     */
    private Map<String, IPersistent> m_objectCache;

    private boolean m_disableObjectCache;

    // **** PACKAGE-ACCESS METHODS ****

    /** Creates a new data source.
     * @param connection The connection to the database.
     * @param hitlistSize The size of the hitlist.
     * @param engineType The engine type as defined in init.xml
     * @param usePreparedStatement Decides if PreparedStatements are to be used for Inserts, Updates, Deletes, Locks. This property should be set to 'false' if the primary key of a table has 'CHAR' field(s).
     */
    DataSource(Connection connection, Integer hitlistSize, String engineType,
            Boolean usePreparedStatement) {
        m_connection = connection;
        m_hitlistSize = hitlistSize;
        m_engineType = engineType;
        m_usePreparedStatement = usePreparedStatement;
        m_statements = new HashMap();
        Boolean disableObjectCache = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(DISABLE_OBJECT_CACHE_RULE_NAME));
        m_disableObjectCache = disableObjectCache != null && disableObjectCache;
    }

    /** Returns the Connection object, used for creating the DataSource.
     * @return a Connection.
     */
    Connection getConnection() {
        return m_connection;
    }

    /** Closes the statement and removes it from the internal Map.
     * @param statement the Statement object to be closed.
     * @throws SQLException if any database error occurs.
     */
    public void closeStatement(final Statement statement) throws SQLException {
        final ResultSet resultSet = (ResultSet) m_statements.get(statement);
        if (resultSet != null) {
            try {
                if (log.isDebugEnabled())
                    log.debug("Closing the ResultSet associated with the Statement");
                resultSet.close();
            } catch (SQLException e) {
                // do nothing
                if (log.isDebugEnabled())
                    log.debug("Error in closing the ResultSet", e);
            }
        }

        m_statements.remove(statement);
        try {
            if (log.isDebugEnabled())
                log.debug("Closing the Statement and removing it from the internal Map.");
            statement.close();
        } catch (SQLException e) {
            // do nothing
            if (log.isDebugEnabled())
                log.debug("Error in closing the Statement", e);
        }
    }

    /** Returns a Statement object. This object will be added to an internal Map and will be closed when the DataSource is closed.
     * It is a good idea to invoke the closeStatement() method when done with the statement.
     * @throws SQLException if any database error occurs.
     * @return the Statement object.
     */
    public Statement getStatement() throws SQLException {
        Statement statement = m_connection.createStatement();
        registerStatement(statement, null);
        return statement;
    }




    // **** PUBLIC METHODS ****

    /** Executes a query against the underlying data source. Returns a Collection of persistent objects.
     * The Statement object that is internally used for executing the query, will be automatically closed, once the recordset has been completely traversed.
     * @param sql The query to execute.
     * @param classMetaData The ClassMetaData defintion to be used for molding the ResultSet into Persistent objects.
     * @param criteria The Criteria used for the query. This will provide the values to set the various flags on the Persistent object.
     * @param queryTimeout This will be used for setting the timeout value on the Statement object; zero means there is no limit.
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws DataSourceCursorRuntimeException if any error occurs while molding the row into the Persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return a Collection of persistent objects.
     */
    public Collection executeQuery(String sql, ClassMetaData classMetaData, Criteria criteria, int queryTimeout, IPagingPlugin pagingPlugin)
    throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException {
        Statement statement = getStatement();

        // The following sets the timeout; zero means there is no limit
        // The setting works in MS-Sql-Server only !!
        statement.setQueryTimeout(queryTimeout);

        // set the fetch size
        try {
            statement.setFetchSize(getHitlistSize().intValue());
        } catch (Throwable e) {
            // NOTE: The setFetchSize feature may not be implemented by all the drivers. eg.Postgresql.
            // so just ignore the exception
        }
        ResultSet resultSet = null;
        long currentTimeMillis = 0;
        if (log.isInfoEnabled()) {
            log.info("Executing the SQL:\n" + sql);
            currentTimeMillis = System.currentTimeMillis();
        }
        resultSet = statement.executeQuery(sql);
        if (log.isInfoEnabled()) {
            log.info("Elapsed:" + (System.currentTimeMillis() - currentTimeMillis));
        }
        registerStatement(statement, resultSet);
        return new DataSourceCursor(this, statement, resultSet, classMetaData, criteria, pagingPlugin);
    }

    /** Executes a query against the underlying data source. Returns a Collection of persistent objects.
     * The Statement object will be automatically closed, once the recordset has been completely traversed.
     * @param statement The query to execute.
     * @param classMetaData The ClassMetaData defintion to be used for molding the ResultSet into Persistent objects.
     * @param criteria The Criteria used for the query. This will provide the values to set the various flags on the Persistent object.
     * @param queryTimeout This will be used for setting the timeout value on the Statement object; zero means there is no limit.
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws DataSourceCursorRuntimeException if any error occurs while molding the row into the Persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return a Collection of persistent objects.
     */
    public Collection executeQuery(PreparedStatement statement, ClassMetaData classMetaData, Criteria criteria, int queryTimeout, IPagingPlugin pagingPlugin)
    throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException {
        // The following sets the timeout; zero means there is no limit
        // The setting works in MS-Sql-Server only !!
        statement.setQueryTimeout(queryTimeout);

        // set the fetch size
        try {
            statement.setFetchSize(getHitlistSize().intValue());
        } catch (Throwable e) {
            // NOTE: The setFetchSize feature may not be implemented by all the drivers. eg.Postgresql.
            // so just ignore the exception
        }
        ResultSet resultSet = null;

        long currentTimeMillis = 0;
        if (log.isInfoEnabled()) {
            log.info("Executing the Prepared Statement:\n" + statement);
            currentTimeMillis = System.currentTimeMillis();
        }
            resultSet = statement.executeQuery();
        if (log.isInfoEnabled()) {
            log.info("Elapsed:" + (System.currentTimeMillis() - currentTimeMillis));
        }
        registerStatement(statement, resultSet);
        return new DataSourceCursor(this, statement, resultSet, classMetaData, criteria, pagingPlugin);
    }

    /** Returns a Collection of persistent objects from the input ResultSet
     * @param statement the Statement object used to obtain the ResultSet.
     * @param resultSet the input result set.
     * @param classMetaData The ClassMetaData defintion to be used for molding the ResultSet into Persistent objects.
     * @param criteria The Criteria used for the query. This will provide the values to set the various flags on the Persistent object.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws DataSourceCursorRuntimeException if any error occurs while molding the row into the Persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return a Collection of persistent objects.
     */
    public Collection loadResultSet(Statement statement, ResultSet resultSet, ClassMetaData classMetaData, Criteria criteria)
    throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException {
        registerStatement(statement, resultSet);
        return new DataSourceCursor(this, statement, resultSet, classMetaData, criteria, null);
    }

    /** Executes the sql, which should be an update/insert/delete statement.
     * The Statement object that is internally used for executing the update, will be immediately closed.
     * @param sql the sql to execute.
     * @throws SQLException if any database error occurs.
     */
    public void executeUpdate(String sql) throws SQLException {
        Statement stmnt = getStatement();
        long currentTimeMillis = 0;
        if (log.isInfoEnabled()) {
            log.info("Executing the SQL:\n" + sql);
            currentTimeMillis = System.currentTimeMillis();
        }
        stmnt.executeUpdate(sql);
        if (log.isInfoEnabled()) {
            log.info("Elapsed:" + (System.currentTimeMillis() - currentTimeMillis));
        }
        closeStatement(stmnt);
    }

    /** Execute the input PreparedStatement.
     * The calling program is expected to close the ResultSet(s), if any, that might be returned by executing the update.
     * @param stmt the PreparedStatement to execute.
     * @throws SQLException if any database error occurs.
     */
    public void executeUpdate(PreparedStatement stmt) throws SQLException {
            long currentTimeMillis = System.currentTimeMillis();
        if (log.isInfoEnabled()) {
            log.info("Executing the Prepared Statement:\n" + stmt);
            currentTimeMillis = System.currentTimeMillis();
        }
        stmt.execute();
        if (log.isInfoEnabled()) {
            log.info("Elapsed:" + (System.currentTimeMillis() - currentTimeMillis));
        }
        closeStatement(stmt);
    }

    /** 
     * ensure availability for garbage collection 
     */
    public void freeConnection() {
        closeStatements();
        m_statements = null;
        
        clearObjectCache();
        m_objectCache = null;
        m_connection=null;
    }

    /** Returns the PreparedStatement object for the input sql.
     * @param sql the sql statement which needs to be precompiled.
     * @throws SQLException if any database error occurs.
     * @return the PreparedStatement object.
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        try (PreparedStatement pstmt =
                // Use the regular prepared-statement if the logging is set to warn or higher, else use a proxy
                log.getEffectiveLevel().isGreaterOrEqual(Level.WARN)
                ? m_connection.prepareStatement(sql)
                : new PreparedStatementProxyForDebugging(m_connection, sql)) {
            pstmt.clearBatch();
            pstmt.clearParameters();
            pstmt.clearWarnings();
            registerStatement(pstmt, null);
            return pstmt;
        }
    }

    /** Returns the CallableStatement object for the input sql.
     * @param sql the sql statement.
     * @throws SQLException if any database error occurs.
     * @return the CallableStatement object.
     */
    public CallableStatement getCallableStatement(String sql) throws SQLException {
        try (final CallableStatement cstmt = m_connection.prepareCall(sql)) {
            cstmt.clearBatch();
            cstmt.clearParameters();
            cstmt.clearWarnings();
            registerStatement(cstmt, null);
            return cstmt;
        }
    }

    /** Commits all changes executed against the persistent store.
     * @throws SQLException if any database error occurs.
     */
    public void commit() throws SQLException {
        try {
			m_connection.commit();
        } finally {
            // close all active Statement/ResultSet objects
            closeStatements();
            // clear the object cache
            clearObjectCache();
		}
    }

    /** Rollback the changes executed against the persistent store.
     * @throws SQLException if any database error occurs.
     */
    public void rollback() throws SQLException {
        try {
			m_connection.rollback();
		} finally {
	        // close all active Statement/ResultSet objects
	        closeStatements();
	        // clear the object cache
	        clearObjectCache();
		}
    }

    /** Getter for property hitlistSize.
     * @return Value of property hitlistSize.
     */
    public Integer getHitlistSize() {
        return m_hitlistSize;
    }

    /** Getter for property engineType, as defined in init.xml
     * @return Value of property engineType.
     */
    public String getEngineType() {
        return m_engineType;
    }

    /** Getter for property usePreparedStatement.
     * @return Value of property usePreparedStatement.
     *
     */
    public Boolean getUsePreparedStatement() {
        return m_usePreparedStatement;
    }

    /** Looks up the internal object cache for a Persistent object, as specified by the
     * key fields in the input Criteria. A null will be returned if the object does not
     * exist in the cache.
     * NOTE: The Cache will be looked up only if the locking strategy is Optimistic.
     * @param criteria The Criteria.
     * @return the Persistent object identified by the key-fields in the criteria, if it exists in the cache.
     */
    public IPersistent lookupObjectCache(Criteria criteria) {
        if (m_objectCache != null && m_objectCache.size() > 0 && criteria.getLocking() == Criteria.LOCKING_OPTIMISTIC) {
            try {
                String serializedKey = PersistentHelper.generateSerializedKey(criteria);
                return m_objectCache.get(serializedKey);
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Cannot lookup object cache since exception was thrown while trying to determine the serialized-key from a Criteria object", e);
            }
        }
        return null;
    }

    /** Caches the input Persistent object, for it to be subsequently looked up by its key.
     * NOTE: Caching will be done only if the locking strategy for the object is Optimistic.
     * Caching can be disabled by setting the rule 'jaffa.persistence.jdbcengine.disableObjectCache' to true.
     * @param object The Persistent object.
     */
    public void cacheObject(IPersistent object) {
        if (!m_disableObjectCache && object.getLocking() == Criteria.LOCKING_OPTIMISTIC) {
            try {
                if (m_objectCache == null)
                    m_objectCache = new HashMap<String, IPersistent>();
                String serializedKey = PersistentHelper.generateSerializedKey(object);
                m_objectCache.put(serializedKey, object);
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Cannot cache object since exception was thrown while trying to determine the serialized-key for the object: " + object, e);
            }
        }
    }

    /** Removes the input Persistent object from the cache.
     * @param object The Persistent object.
     */
    public void uncacheObject(IPersistent object) {
        if (m_objectCache != null && m_objectCache.size() > 0) {
            try {
                String serializedKey = PersistentHelper.generateSerializedKey(object);
                m_objectCache.remove(serializedKey);
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Cannot uncache object since exception was thrown while trying to determine the serialized-key for the object: " + object, e);
            }
        }
    }

    /** Clears the object cache.
     */
    public void clearObjectCache() {
        if (m_objectCache != null)
            m_objectCache.clear();
    }



    // *** PRIVATE methods ****

    /** Close all the Statement objects stored in the Map, and clear the Map.*/
    private void closeStatements() {
        if (log.isDebugEnabled())
            log.debug("Closing " + m_statements.size() + " Statements");
        
        
        for (final Iterator i = m_statements.entrySet().iterator(); i.hasNext();) {
            
        	final Map.Entry me = (Map.Entry) i.next();
            final Statement statement = (Statement) me.getKey();
            final ResultSet resultSet = (ResultSet) me.getValue();
            
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // do nothing
                    if (log.isDebugEnabled())
                        log.debug("Error in closing the ResultSet", e);
                }
            }
            
            if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                // do nothing
	                if (log.isDebugEnabled())
	                    log.debug("Error in closing the Statement", e);
	            }
            }
            
        }
        m_statements.clear();
    }

    /** Adds the input Statement to the Map. */
    private void registerStatement(Statement statement, ResultSet resultSet) {
        try {
            ResultSet currentResultSet = (ResultSet) m_statements.get(statement);
            if (currentResultSet != null && currentResultSet != resultSet)
                currentResultSet.close();
        } catch (SQLException e) {
            // do nothing
        }
        m_statements.put(statement, resultSet);
    }
}