/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2014 JAFFA Development Group
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

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A Connection factory that uses   BoneCP as its connection pool
 */
public class BoneCPDataSourceConnectionFactory implements IConnectionFactory, InternalConnectionManager {
    private static final Logger log = Logger.getLogger(BoneCPDataSourceConnectionFactory.class);

    // **************************************
    // Properties for this Connection Factory
    // **************************************
    private String driverClass;
    private String url;
    private String user;
    private String password;
    private Integer minimumConnections;
    private Integer maximumConnections;
    private Double maxConnTime;
    private Integer partitions;
    private Integer retryAttempts;
    private Integer maxCheckoutSeconds;
    private static BoneCP connectionPool;

    /** An object used exclusively for locking purposes during synchronization of
     * operations on the connection pool. */
    protected static final Object connectionPoolLock = new Object();

    private static Map<Connection, Connection> connectionsLookup = Collections.synchronizedMap(new HashMap<Connection, Connection>());

    /**
     * Creates a connection from the BONECP poll
     * @return A proxy connection
     * @throws SQLException exception if there is a sql issue
     * @throws IOException if the driver can not be loaded
     */
    @Override
    public Connection createConnection() throws SQLException, IOException {
        if (connectionPool == null) {
            try {
                configPool();
            } catch (ClassNotFoundException ex) {
                throw new IOException("Can not load Driver", ex);
            }
        }

        Connection connection = connectionPool.getConnection();
        if (connection != null && connection.isClosed()) {
            log.error("Failed to get connection in open state");
        }
        return connection;
    }

    /**
     * Configures the bone cp pool
     * @throws ClassNotFoundException Exception thrown if the driver can not be loaded
     * @throws SQLException  if there is any sql issues with the connection
     */
    private void configPool() throws ClassNotFoundException, SQLException {
        synchronized (connectionPoolLock) {
            if (connectionPool != null) {
                return;
            }
            // load the driver
            Class.forName(getDriverClass());
            // set up the configuration
            BoneCPConfig config = new BoneCPConfig();

            config.setAcquireRetryDelay(getMaxConnTime() == null ? 1 : getMaxConnTime().longValue(), TimeUnit.SECONDS);
            config.setDisableConnectionTracking(true);
            config.setMaxConnectionsPerPartition((getMaximumConnections() == null ? 50 : getMaximumConnections()) / 4);
            config.setMinConnectionsPerPartition((getMinimumConnections() == null ? 20 : getMinimumConnections()) / 4);
            config.setMaxConnectionAge(getMaxCheckoutSeconds() == null ? 30 : getMaxCheckoutSeconds(), TimeUnit.SECONDS);
            config.setJdbcUrl(getUrl());
            config.setUsername(getUser());
            config.setPartitionCount(getPartitions() == null || getPartitions() <= 0 ? 1 : getPartitions());
            config.setAcquireRetryAttempts(getRetryAttempts() == null ? 10 : getRetryAttempts());
            config.setPassword(getPassword());
            // create the pool
            connectionPool = new BoneCP(config);
        }
    }

    /**
     * Free up the connection and return it to the pool
     * @param connection The connection to return back to the pool.
     * @throws SQLException   if there is any sql issues with the connection occured while closing the connection
     * @throws IOException
     */
    @Override
    public  void freeConnection(Connection connection) throws SQLException, IOException {

        if (connectionsLookup.containsKey(connection)) {
            cleanInnerConnection(connection);
            return;
        }
        if (!connection.isClosed()) {
            if (connection instanceof com.jolbox.bonecp.ConnectionHandle) {
                Connection internalConnection = (Connection) ((com.jolbox.bonecp.ConnectionHandle) connection).getInternalConnection();
                removeFromMap(internalConnection);
                if(log.isDebugEnabled()){
                long startTime = ((com.jolbox.bonecp.ConnectionHandle) connection).getConnectionCreationTimeInMs();

                log.debug("Duration for connection:" + (System.currentTimeMillis() - startTime));
                }
                connection.close();
                return;
            }
            if(log.isDebugEnabled()){
                long startTime = ((com.jolbox.bonecp.ConnectionHandle) connection).getConnectionCreationTimeInMs();

                log.debug("Duration for connection:" + (System.currentTimeMillis() - startTime));
            }
        }

        if(log.isDebugEnabled()){
            long startTime = ((com.jolbox.bonecp.ConnectionHandle) connection).getConnectionCreationTimeInMs();

            log.debug("Duration for connection:" + (System.currentTimeMillis() - startTime));
        }

    }

    /**
     *
     * @param internalConnection
     */
    private synchronized void removeFromMap(Connection internalConnection) {
        if (connectionsLookup.containsKey(internalConnection)) {
            connectionsLookup.remove(internalConnection);
        }
    }

    /**
     *
     * @param connection
     * @throws SQLException
     */
    private synchronized void cleanInnerConnection(Connection connection) throws SQLException {
        if (connectionsLookup.containsKey(connection)) {
        Connection rootConnection = connectionsLookup.get(connection);
        if (!rootConnection.isClosed()) {
            rootConnection.close();
        }
        long startTime = ((com.jolbox.bonecp.ConnectionHandle) rootConnection).getConnectionCreationTimeInMs();
        if(log.isDebugEnabled()){
        log.debug("Duration for connection:" + (System.currentTimeMillis() - startTime));
        }
        removeFromMap(connection);
        return;
        }
    }


    /**
     * Gets the internal connection from the connection proxy
     * @param connection the connection to use to get the internal connection from
     * @return   a SQLConnection returned from the  inner connection
     */
    @Override
    public synchronized Connection getInternalConnection(Connection connection) {

        if (connection instanceof com.jolbox.bonecp.ConnectionHandle) {
            Connection innerConnection = ((com.jolbox.bonecp.ConnectionHandle) connection).getInternalConnection();
            connectionsLookup.put(innerConnection, connection);
            return innerConnection;
        }

        return connection;
    }

    /**
     * gets the number of partitions for the pool
     * @return a Integer representing the number of partitions for the pool
     */
    public Integer getPartitions() {
        return partitions;
    }

    /**
     *  sets the number of partitions
     * @param partitions an integer representing the number of partitions to place in the pool
     */
    public void setPartitions(Integer partitions) {
        this.partitions = partitions;
    }

    /**
     * get the number of retry attempts when a retry fails
     * @return an Integer representing the number of times to retry getting a connection from the pool
     */
    public Integer getRetryAttempts() {
        return retryAttempts;
    }

    /**
     * sets the number of retry attempts
     * @param retryAttempts an integer that represents the maximum of times the pool should retry to attempt to get a
     *                      connection from the pool
     */
    public void setRetryAttempts(Integer retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    /**
     *  Gets the maximum connection timeout value in seconds
     * @return the maximum connection timeout in seconds
     */
    public Double getMaxConnTime() {
        return maxConnTime;
    }

    /**
     * Sets the maximum connection timeout value in seconds
     * @param maxConnTime the maximum connection timeout in seconds
     */
    public void setMaxConnTime(Double maxConnTime) {
        this.maxConnTime = maxConnTime;
    }



    /**
     * gets the maximum number of connections to have in the pool ( this is divided amongst the partitions)
     * @return An integer representing the maximum number of connections
     */
    public Integer getMaximumConnections() {
        return maximumConnections;
    }

    /**
     * sets the maximum number of connections to have in the pool ( this is divided amongst the partitions)
     * @param maximumConnections  An integer representing the maximum number of connections
     */
    public void setMaximumConnections(Integer maximumConnections) {
        this.maximumConnections = maximumConnections;
    }

    /**
     * gets the minimum number of connections to have in the pool ( this is divided amongst the partitions)
     * @return An integer representing the minimum number of connections
     */
    public Integer getMinimumConnections() {
        return minimumConnections;
    }

    /**
     * sets the minimum number of connections to have in the pool ( this is divided amongst the partitions)
     * @param minimumConnections  An integer representing the minimum number of connections
     */
    public void setMinimumConnections(Integer minimumConnections) {
        this.minimumConnections = minimumConnections;
    }

    /**
     *  gets the password for the sql server
     * @return the password for connecting to the sql server
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password for the sql server
     * @param password  the password for connecting to the sql server
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the username for connecting to the sql server
     * @return the user name for connecting to the sql server
     */
    public String getUser() {
        return user;
    }

    /**
     * gets the username for connecting to the sql server
     * @param user the user name for connecting to the sql server
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * gets the url for connecting to the sql server
     * @return the url for connecting to the sql server
     */
    public String getUrl() {
        return url;
    }

    /**
     * sets the url for connecting to the sql server
     * @param url the url for connecting to the sql server
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * gets the driver for connecting to the sql server
     * @return the driver for connecting to the sql server
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * sets the driver  for connecting to the sql server
     * @param driverClass the driver for connecting to the sql server
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * gets the maximum time a connection can be held for in seconds
     * @return the maximum time a connection can be held for in seconds
     */
    public Integer getMaxCheckoutSeconds() {
        return maxCheckoutSeconds;
    }

    /**
     * gets the maximum time a connection can be held for in seconds
     * @param  maxCheckoutSeconds the maximum time a connection can be held for in seconds
     */
    public void setMaxCheckoutSeconds(Integer maxCheckoutSeconds) {
        this.maxCheckoutSeconds = maxCheckoutSeconds;
    }




}
