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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackKeyedObjectPoolFactory;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;

/** This is a Dbcp DataSource based implementation of IConnectionFactory.
 *
 * It requires commons-collections.jar, commons-pool.jar, commons-dbcp.jar, j2ee.jar (for the javax.sql classes), the classes for your (underlying) JDBC driver in the classpath.
 * @author  GautamJ
 */
public class DbcpDataSourceConnectionFactory implements IConnectionFactory {
    
    private static final Logger log = Logger.getLogger(DbcpDataSourceConnectionFactory.class);
    
    // This is the number of times the createConnection will try to acquire a connection from the pool
    private static final int RETRY_LIMIT = 10;
    
    // The default wait time, between connection retries, in milliseconds
    private static final long DEFAULT_WAIT_TIME = 2000;
    
    // the DataSource used for pooling connections
    private static DataSource c_dataSource = null;
    
    
    // **************************************
    // Properties for this Connection Factory
    // **************************************
    private String m_driverClass;
    private String m_url;
    private String m_user;
    private String m_password;
    private Integer m_minimumConnections;
    private Integer m_maximumConnections;
    private Long m_maxWait;
    private Boolean m_testOnBorrow;
    private Boolean m_testOnReturn;
    private String m_validationQuery;
    private Properties m_driverProperties = new Properties();
    
    
    
    
    
    /** Getter for property driverClass.
     * @return Value of property driverClass.
     */
    public String getDriverClass() {
        return m_driverClass;
    }
    
    /** Setter for property driverClass.
     * @param driverClass New value of property driverClass.
     */
    public void setDriverClass(String driverClass) {
        if (driverClass == null || driverClass.length() == 0)
            m_driverClass = null;
        else
            m_driverClass = driverClass;
    }
    
    /** Getter for property url.
     * @return Value of property url.
     */
    public String getUrl() {
        return m_url;
    }
    
    /** Setter for property url.
     * @param url New value of property url.
     */
    public void setUrl(String url) {
        if (url == null || url.length() == 0)
            m_url = null;
        else
            m_url = url;
    }
    
    /** Getter for property user.
     * @return Value of property user.
     */
    public String getUser() {
        return m_user;
    }
    
    /** Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(String user) {
        if (user == null || user.length() == 0)
            m_user = null;
        else
            m_user = user;
    }
    
    /** Getter for property password.
     * @return Value of property password.
     */
    public String getPassword() {
        return m_password;
    }
    
    /** Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(String password) {
        if (password == null || password.length() == 0)
            m_password = null;
        else
            m_password = password;
    }
    
    /** Getter for property minimumConnections.
     * @return Value of property minimumConnections.
     */
    public Integer getMinimumConnections() {
        return m_minimumConnections;
    }
    
    /** Setter for property minimumConnections.
     * @param minimumConnections New value of property minimumConnections.
     */
    public void setMinimumConnections(Integer minimumConnections) {
        m_minimumConnections = minimumConnections;
    }
    
    /** Getter for property maximumConnections.
     * @return Value of property maximumConnections.
     */
    public Integer getMaximumConnections() {
        return m_maximumConnections;
    }
    
    /** Setter for property maximumConnections.
     * @param maximumConnections New value of property maximumConnections.
     */
    public void setMaximumConnections(Integer maximumConnections) {
        m_maximumConnections = maximumConnections;
    }
    
    /** Getter for property maxWait.
     * @return Value of property maxWait.
     *
     */
    public Long getMaxWait() {
        return m_maxWait;
    }
    
    /** Setter for property maxWait.
     * @param maxWait New value of property maxWait.
     *
     */
    public void setMaxWait(Long maxWait) {
        m_maxWait = maxWait;
    }
    
    /** Getter for property testOnBorrow.
     * @return Value of property testOnBorrow.
     *
     */
    public Boolean getTestOnBorrow() {
        return m_testOnBorrow;
    }
    
    /** Setter for property testOnBorrow.
     * @param testOnBorrow New value of property testOnBorrow.
     *
     */
    public void setTestOnBorrow(Boolean testOnBorrow) {
        m_testOnBorrow = testOnBorrow;
    }
    
    /** Getter for property testOnReturn.
     * @return Value of property testOnReturn.
     *
     */
    public Boolean getTestOnReturn() {
        return m_testOnReturn;
    }
    
    /** Setter for property testOnReturn.
     * @param testOnReturn New value of property testOnReturn.
     *
     */
    public void setTestOnReturn(Boolean testOnReturn) {
        m_testOnReturn = testOnReturn;
    }
    
    /** Getter for property validationQuery.
     * @return Value of property validationQuery.
     *
     */
    public String getValidationQuery() {
        return m_validationQuery;
    }
    
    /** Setter for property validationQuery.
     * @param validationQuery New value of property validationQuery.
     *
     */
    public void setValidationQuery(String validationQuery) {
        if (validationQuery == null || validationQuery.length() == 0)
            m_validationQuery = null;
        else
            m_validationQuery = validationQuery;
    }
    
    /** Getter for property driverProperties.
     * @return Value of property driverProperties.
     */
    public Properties getDriverProperties() {
        return m_driverProperties;
    }
    
    /** Setter for property driverProperties.
     * @param driverProperties New value of property driverProperties.
     */
    public void setDriverProperties(Properties driverProperties) {
        m_driverProperties = driverProperties;
    }
    
    
    
    
    // **************************************
    // Implementation methods
    // **************************************
    
    /** Creates a connection using Dbcp DataSource implementation.
     * @throws SQLException if any SQL error occurs
     * @throws IOException if any IO error occurs
     * @return a Connection
     */
    public Connection createConnection() throws SQLException, IOException {
        if (c_dataSource == null)
            createDbcpDataSource();
        
        Connection connection = null;
        SQLException se = null;
        for (int i = 0; i < RETRY_LIMIT; i++) {
            try {
                connection = c_dataSource.getConnection();
            } catch (SQLException e) {
                // maintain a reference to the exception
                se = e;
            }
            if (connection != null)
                break;
            
            if (log.isDebugEnabled())
                log.debug((i+1) + " : Could not acquire Connection from Dbcp DataSource. Will try again.");
            
            try {
                Thread.sleep(getMaxWait() != null ? getMaxWait().longValue() : DEFAULT_WAIT_TIME);
            } catch (Exception e) {
                // do nothing
            }
        }
        
        if (connection == null) {
            String str = "Could not acquire a connection from Dbcp DataSource even after " + RETRY_LIMIT + " tries";
            log.error(str, se);
            throw se != null ? se : new SQLException(str);
        }
        
        if (log.isDebugEnabled())
            log.debug("Acquired a pooled Connection from DbcpDataSource: " + connection);
        
        return connection;
    }
    
    /** Returns the connection back to the pool.
     * @param connection The connection to return back to the pool.
     * @throws SQLException if any SQL error occurs
     * @throws IOException if any IO error occurs
     */
    public void freeConnection(Connection connection) throws SQLException, IOException {
        if (log.isDebugEnabled())
            log.debug("Freeing up the pooled Connection back to DbcpDataSource: " + connection);
        connection.close();
    }
    
    
    
    private synchronized void createDbcpDataSource()
    throws SQLException {
        try {
            if (c_dataSource == null) {
                // First we load the underlying JDBC driver.
                Class.forName(getDriverClass());
                
                // Next, we'll need a ObjectPool that serves as the actual pool of connections.
                // We'll use a GenericObjectPool instance
                GenericObjectPool connectionPool = new GenericObjectPool(null);
                if (getMaximumConnections() != null)
                    connectionPool.setMaxActive(getMaximumConnections().intValue());
                if (getMinimumConnections() != null)
                    connectionPool.setMaxIdle(getMinimumConnections().intValue());
                if (getMaxWait() != null)
                    connectionPool.setMaxWait(getMaxWait().longValue());
                if (getTestOnBorrow() != null)
                    connectionPool.setTestOnBorrow(getTestOnBorrow().booleanValue());
                if (getTestOnReturn() != null)
                    connectionPool.setTestOnReturn(getTestOnReturn().booleanValue());
                
                
                // Next, we'll create a ConnectionFactory that the pool will use to create Connections.
                // We'll use the DriverManagerConnectionFactory
                ConnectionFactory connectionFactory = null;
                if(m_driverProperties == null || m_driverProperties.isEmpty())
                    connectionFactory = new DriverManagerConnectionFactory(getUrl(), getUser(), getPassword());
                else
                    connectionFactory = new DriverManagerConnectionFactory(getUrl(), getDriverProperties());
                // Now we'll create the PoolableConnectionFactory, which wraps the "real" Connections created by the ConnectionFactory with the classes that implement the pooling functionality.
                KeyedObjectPoolFactory stmtPoolFactory = new StackKeyedObjectPoolFactory();
                String validationQuery = getValidationQuery();
                boolean defaultReadOnly = false;
                boolean defaultAutoCommit = false;
                PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, stmtPoolFactory, validationQuery, defaultReadOnly, defaultAutoCommit);
                
                // Finally, we create the PoolingDriver itself, passing in the object pool we created.
                c_dataSource = new PoolingDataSource(connectionPool);
                
                // This will allow us to access the underlying Connection objects, required by the JdbcSecurityPlugin
                ((PoolingDataSource) c_dataSource).setAccessToUnderlyingConnectionAllowed(true);
                
                if (log.isDebugEnabled())
                    log.debug("Created the Dbcp DataSource");
            }
        } catch (Exception e) {
            String str = "Error in creating the Dbcp DataSource";
            log.error(str, e);
            throw new SQLException(str);
        }
    }
    
}
