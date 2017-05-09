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

package org.jaffa.persistence.engines.jdbcengine.datasource;

import org.apache.log4j.Logger;

import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;

import org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCreationException;
import org.jaffa.persistence.engines.jdbcengine.security.IJdbcSecurityPlugin;

import java.lang.reflect.Method;

import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Param;
import org.jaffa.util.BeanHelper;


/**
 * Factory implementation for making us DataSource objects to execute DDL against.
 * This uses a Connection pool to create DataSource objects.
 */
public class DataSourceFactory {

    private static final Logger log = Logger.getLogger(DataSourceFactory.class);

    private static final String ACQUIRING_CONNECTION_ERROR_MESSAGE = "Error in acquiring a Connection from the ConnectionFactory";
    private static final String NEW_CONNECTION_TRIGGER_ERROR_MESSAGE = "Error in executing the newConnection trigger of the JdbcSecurityPlugin. Freeing up the connection and then throwing the exception.";

    // the ConnectionFactory, used for pooling connections
    private static IConnectionFactory c_connectionFactory = null;

    // The JDBC Security Plugin
    private static IJdbcSecurityPlugin c_jdbcSecurityPlugin = null;

    /**
     * Make private to ensure this never gets instantiated.
     */
    private DataSourceFactory() {
    }

    /**
     * Returns a new DataSource instance.
     *
     * @param database the configuration information for the data source.
     * @return the DataSource.
     * @throws DataSourceCreationException if the connection could not be acquired.
     */
    public static DataSource getDataSource(Database database) throws DataSourceCreationException {
        if (c_connectionFactory == null)
            initialize(database);
        
        if (log.isDebugEnabled()) log.debug("Creating a new DataSource");
        DataSource dataSource = new DataSource(getConnection(), 
                database.getHitlistSize().getValue(), 
                database.getEngine(), 
                database.getUsePreparedStatement().isValue());
        
        turnOffAutoCommit(dataSource.getConnection());
        initializeSecurityContext(dataSource.getConnection());
        
        return dataSource;
    }

    /**
     * Returns a new DataSource instance having the connection provided.
     *
     * @param database the configuration information for the data source.
     * @return the DataSource.
     */
    public static DataSource getDataSource(Database database, Connection connection) throws DataSourceCreationException {
        
        if (log.isDebugEnabled()) log.debug("Creating a new DataSource");
        DataSource dataSource = new DataSource(connection, 
                database.getHitlistSize().getValue(), 
                database.getEngine(), 
                database.getUsePreparedStatement().isValue());
        
        return dataSource;
    }

    /**
     * Free up the DataSource. This will free up the underlying pooled connection.
     *
     * @param ds the DataSource.
     */
    public static void freeDataSource(DataSource ds) {
        Connection connection = ds.getConnection();
        freeConnection(connection);
        ds.freeConnection();
        ds = null;
    }

    /**
     * When using the DBCP or JBoss, a wrapper connection is created. Extract the actual connection from the wrapper.
     *
     * @param connection The wrapper connection.
     * @return the original connection if using DBCP or JBoss.
     * @throws Exception if any error occurs.
     */
    public static Connection getUnderlyingConnection(Connection connection) throws Exception {
        //use the unwrap method if InternalConnectionManager is available
        if(c_connectionFactory instanceof InternalConnectionManager){
            return ((InternalConnectionManager) c_connectionFactory).getInternalConnection(connection);
        }

        connection = checkDbcpWrapper(connection);
        connection = checkJbossWrapper(connection);
        return connection;
    }

    /**
     * When using the dbcp DataSource, a wrapper connection is created. Extract the actual connection from the wrapper.
     *
     * @param connection The dbcp connection.
     * @return the original connection if using dbcp.
     * @throws Exception if any error occurs.
     */
    private static Connection checkDbcpWrapper(Connection connection) throws Exception {
        // When using the dbcp DataSource, a wrapper connection is created. Extract the actual connection from the wrapper.
        Class<?> dbcpDelegatingConnectionClass = null;
        try {
            dbcpDelegatingConnectionClass = Class.forName("org.apache.commons.dbcp.DelegatingConnection");
        } catch (Throwable e) {
            // Commons dbcp is not being used. Just return
            return connection;
        }
        if (dbcpDelegatingConnectionClass.isInstance(connection)) {
            log.debug("Obtain the underlying connection from the dbcp DelegatingConnection");
            Method m = connection.getClass().getMethod("getInnermostDelegate", (Class[]) null);
            connection = (Connection) m.invoke(connection, (Object[]) null);
        }
        return connection;
    }

    /**
     * When using JBoss, a wrapper connection is created. Extract the actual connection from the wrapper.
     *
     * @param connection The dbcp connection.
     * @return the original connection if using JBoss.
     * @throws Exception if any error occurs.
     */
    private static Connection checkJbossWrapper(Connection connection) throws Exception {
        // When using JBoss, a wrapper connection is created. Extract the actual oracle connection from the wrapper.
        if (connection.getClass().getName().equals("org.jboss.resource.adapter.jdbc.WrappedConnection")) {
            log.debug("Obtain the underlying connection from the jboss WrappedConnection");
            Method m = connection.getClass().getMethod("getUnderlyingConnection", (Class[]) null);
            connection = (Connection) m.invoke(connection, (Object[]) null);
        }
        return connection;
    }

    private static Connection getConnection() {
        try {
            return c_connectionFactory.createConnection();
        } 
        catch (Exception e) {
            log.fatal(e.getMessage(),e);
            return null;
        }
    }
    
    private static void initializeSecurityContext(Connection connection) throws DataSourceCreationException {
        // now invoke the newConnection trigger of the security plugin
        try {
            if (c_jdbcSecurityPlugin != null) {
                if (log.isDebugEnabled()) log.debug("Invoking the newConnection trigger on the JdbcSecurityPlugin");
                c_jdbcSecurityPlugin.newConnection(connection);
            }
        } 
        catch (Exception e) {
            log.error(NEW_CONNECTION_TRIGGER_ERROR_MESSAGE, e);
            try {
                c_connectionFactory.freeConnection(connection);
            } 
            catch (Exception e1) {
                // do nothing
                log.warn("Error in returning a Connection back to the ConnectionFactory", e1);
            }

            throw new DataSourceCreationException(DataSourceCreationException.NEW_CONNECTION_OF_JDBC_PLUGIN_FAILED, null, e);
        }
    }
   
    private static void freeConnection(Connection connection) {
        // invoke the freeConnection trigger of the security plugin
        try {
            if (c_jdbcSecurityPlugin != null) {
                if (log.isDebugEnabled())
                    log.debug("Invoking the freeConnection trigger on the JdbcSecurityPlugin");
                c_jdbcSecurityPlugin.freeConnection(connection);
            }
        } catch (Exception e) {
            String str = "Error in executing the freeConnection trigger of the JdbcSecurityPlugin. Forcibly closing the connection, to avoid re-use by the connection pool.";
            log.warn(str, e);
            try {
                forciblyCloseConnection(connection);
            } catch (Exception e1) {
                String str1 = "Error in closing the connection. Continuing with the process.";
                log.warn(str1, e1);
            }
        }

        // now free the connection
        try {
            c_connectionFactory.freeConnection(connection);
        } catch (Exception e) {
            // do nothing
            log.warn("Error in returning a Connection back to the ConnectionFactory", e);
        }
    }

    private synchronized static void initialize(Database database)
            throws DataSourceCreationException {
        if (c_connectionFactory == null) {
            try {
                // create an instance of the JDBC Security Plugin, while instantiating the ConnectionFactory
                if (database.getJdbcSecurityPlugin() != null && database.getJdbcSecurityPlugin().getValue() != null
                        && database.getJdbcSecurityPlugin().getValue().length() > 0)
                    c_jdbcSecurityPlugin = (IJdbcSecurityPlugin) Class.forName(database.getJdbcSecurityPlugin().getValue()).newInstance();
            } catch (Exception e) {
                String str = "Error in creating an instance of the JDBC Security Plugin for the class: " + database.getJdbcSecurityPlugin();
                log.error(str, e);
                throw new DataSourceCreationException(DataSourceCreationException.JDBC_PLUGIN_CREATION_FAILED, new Object[]{database.getJdbcSecurityPlugin()}, e);
            }

            try {
                // now instantiate the ConnectionFactory and set its properties
                IConnectionFactory connectionFactory = (IConnectionFactory) Class.forName(database.getConnectionFactory().getClassName()).newInstance();
                if (database.getConnectionFactory().getParam() != null) {
                    for (Iterator<?> i = database.getConnectionFactory().getParam().iterator(); i.hasNext(); ) {
                        Param param = (Param) i.next();
                        BeanHelper.setField(connectionFactory, param.getName(), param.getValue());
                    }
                }
                c_connectionFactory = connectionFactory;
            } catch (Exception e) {
                String str = "Error in creating an instance of the ConnectionFactory for the class: " + database.getConnectionFactory().getClassName();
                log.error(str, e);
                throw new DataSourceCreationException(DataSourceCreationException.CONNECTION_FACTORY_CREATION_FAILED, new Object[]{database.getConnectionFactory().getClassName()}, e);
            }
        }
    }

    private static void turnOffAutoCommit(Connection connection) throws DataSourceCreationException {
        if (connection == null) {
            log.error(ACQUIRING_CONNECTION_ERROR_MESSAGE);
            throw new DataSourceCreationException(DataSourceCreationException.CONNECTION_FAILED, null, null);
        }
        try {
            connection.setAutoCommit(false);
        } 
        catch (SQLException e) {
            log.warn("Error in turning off AutoCommit on the Connection", e);
        }
    }

    private static void forciblyCloseConnection(Connection connection) throws Exception {
        connection = getUnderlyingConnection(connection);
        connection.close();
    }

}
