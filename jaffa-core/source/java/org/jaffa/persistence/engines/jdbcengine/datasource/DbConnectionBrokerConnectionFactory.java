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

import com.javaexchange.dbConnectionBroker.DbConnectionBroker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * This is a DbConnectionBroker based implementation of IConnectionFactory.
 * DbConnectionBroker is a fairly proven and stable implemenation for database connection pooling.
 * It requires 'dbConnectionBroker.jar' in the classpath.
 *
 * @author GautamJ
 */
public class DbConnectionBrokerConnectionFactory implements IConnectionFactory {

    private static final Logger log = Logger.getLogger(DbConnectionBrokerConnectionFactory.class);

    // the DbConnectionBroker, used for pooling connections
    private static DbConnectionBroker c_dbConnectionBroker = null;

    /** An object used exclusively for locking purposes during synchronization of
     * operations on the DB connection broker. */
    protected static final Object connectionBrokerLock = new Object();

    // **************************************
    // Properties for this Connection Factory
    // **************************************
    private String m_driverClass;
    private String m_url;
    private String m_user;
    private String m_password;
    private Integer m_minimumConnections;
    private Integer m_maximumConnections;
    private String m_logFileName;
    private Double m_maxConnTime;
    private Boolean m_logAppend;
    private Integer m_maxCheckoutSeconds;
    private Integer m_debugLevel;


    /**
     * Getter for property driverClass.
     *
     * @return Value of property driverClass.
     */
    public String getDriverClass() {
        return m_driverClass;
    }

    /**
     * Setter for property driverClass.
     *
     * @param driverClass New value of property driverClass.
     */
    public void setDriverClass(String driverClass) {
        if (driverClass == null || driverClass.length() == 0)
            m_driverClass = null;
        else
            m_driverClass = driverClass;
    }

    /**
     * Getter for property url.
     *
     * @return Value of property url.
     */
    public String getUrl() {
        return m_url;
    }

    /**
     * Setter for property url.
     *
     * @param url New value of property url.
     */
    public void setUrl(String url) {
        if (url == null || url.length() == 0)
            m_url = null;
        else
            m_url = url;
    }

    /**
     * Getter for property user.
     *
     * @return Value of property user.
     */
    public String getUser() {
        return m_user;
    }

    /**
     * Setter for property user.
     *
     * @param user New value of property user.
     */
    public void setUser(String user) {
        if (user == null || user.length() == 0)
            m_user = null;
        else
            m_user = user;
    }

    /**
     * Getter for property password.
     *
     * @return Value of property password.
     */
    public String getPassword() {
        return m_password;
    }

    /**
     * Setter for property password.
     *
     * @param password New value of property password.
     */
    public void setPassword(String password) {
        if (password == null || password.length() == 0)
            m_password = null;
        else
            m_password = password;
    }

    /**
     * Getter for property minimumConnections.
     *
     * @return Value of property minimumConnections.
     */
    public Integer getMinimumConnections() {
        return m_minimumConnections;
    }

    /**
     * Setter for property minimumConnections.
     *
     * @param minimumConnections New value of property minimumConnections.
     */
    public void setMinimumConnections(Integer minimumConnections) {
        m_minimumConnections = minimumConnections;
    }

    /**
     * Getter for property maximumConnections.
     *
     * @return Value of property maximumConnections.
     */
    public Integer getMaximumConnections() {
        return m_maximumConnections;
    }

    /**
     * Setter for property maximumConnections.
     *
     * @param maximumConnections New value of property maximumConnections.
     */
    public void setMaximumConnections(Integer maximumConnections) {
        m_maximumConnections = maximumConnections;
    }

    /**
     * Getter for property logFileName.
     *
     * @return Value of property logFileName.
     */
    public String getLogFileName() {
        return m_logFileName;
    }

    /**
     * Setter for property logFileName.
     *
     * @param logFileName New value of property logFileName.
     */
    public void setLogFileName(String logFileName) {
        if (logFileName == null || logFileName.length() == 0)
            m_logFileName = null;
        else
            m_logFileName = logFileName;
    }

    /**
     * Getter for property maxConnTime.
     *
     * @return Value of property maxConnTime.
     */
    public Double getMaxConnTime() {
        return m_maxConnTime;
    }

    /**
     * Setter for property maxConnTime.
     *
     * @param maxConnTime New value of property maxConnTime.
     */
    public void setMaxConnTime(Double maxConnTime) {
        m_maxConnTime = maxConnTime;
    }

    /**
     * Getter for property logAppend.
     *
     * @return Value of property logAppend.
     */
    public Boolean getLogAppend() {
        return m_logAppend;
    }

    /**
     * Setter for property logAppend.
     *
     * @param logAppend New value of property logAppend.
     */
    public void setLogAppend(Boolean logAppend) {
        m_logAppend = logAppend;
    }

    /**
     * Getter for property maxCheckoutSeconds.
     *
     * @return Value of property maxCheckoutSeconds.
     */
    public Integer getMaxCheckoutSeconds() {
        return m_maxCheckoutSeconds;
    }

    /**
     * Setter for property maxCheckoutSeconds.
     *
     * @param maxCheckoutSeconds New value of property maxCheckoutSeconds.
     */
    public void setMaxCheckoutSeconds(Integer maxCheckoutSeconds) {
        m_maxCheckoutSeconds = maxCheckoutSeconds;
    }

    /**
     * Getter for property debugLevel.
     *
     * @return Value of property debugLevel.
     */
    public Integer getDebugLevel() {
        return m_debugLevel;
    }

    /**
     * Setter for property debugLevel.
     *
     * @param debugLevel New value of property debugLevel.
     */
    public void setDebugLevel(Integer debugLevel) {
        m_debugLevel = debugLevel;
    }


    // **************************************
    // Implementation methods
    // **************************************

    /**
     * Creates a connection using DbConnectionBroker.
     * this will block the caller until a connection can be achieved
     *
     * @return a Connection
     * @throws SQLException if any SQL error occurs
     * @throws IOException  if any IO error occurs
     */
    public Connection createConnection() throws SQLException, IOException {
        if (c_dbConnectionBroker == null)
            createDbConnectionBroker();

        Connection connection = c_dbConnectionBroker.getConnection();

        //retry connection take from pool
        while (connection == null) {
            try {
                Thread.sleep(20);
                connection = c_dbConnectionBroker.getConnection();
            } catch (Exception e) {
                log.error(e);
                break;
            }

        }

        if (connection == null) {
            String str = "Could not acquire a connection from DbConnectionBroker. Try again.";
            log.error(str);
            throw new SQLException(str);
        }

        if (log.isDebugEnabled())
            log.debug("Acquired a pooled Connection from DbConnectionBroker: " + connection);

        return connection;
    }

    /**
     * Returns the connection back to the pool.
     *
     * @param connection The connection to return back to the pool.
     * @throws SQLException if any SQL error occurs
     * @throws IOException  if any IO error occurs
     */
    public void freeConnection(Connection connection) throws SQLException, IOException {
        if (log.isDebugEnabled())
            log.debug("Freeing up the pooled Connection back to DbConnectionBroker: " + connection);
        c_dbConnectionBroker.freeConnection(connection);
    }

    /**
     * Creates the singleton connection broker.
     * @throws IOException when DbConnectionBroker constructor has problems
     */
    private void createDbConnectionBroker()
            throws IOException {
        synchronized (connectionBrokerLock) {
            if (c_dbConnectionBroker == null) {
                double maxConnTime = getMaxConnTime() != null ? getMaxConnTime() : 1;
                // the connections will be reset every day
                boolean logAppend = getLogAppend() != null ? getLogAppend() : true;
                int maxCheckoutSeconds = getMaxCheckoutSeconds() != null ? getMaxCheckoutSeconds() : 0;
                // this will turn off the option of recycling the Connection after x seconds (default is 60 seconds)
                int debugLevel = getDebugLevel() != null ? getDebugLevel() : 2;

                c_dbConnectionBroker = new DbConnectionBroker(getDriverClass(), getUrl(), getUser(), getPassword(),
                                                              getMinimumConnections(),
                                                              getMaximumConnections(), getLogFileName(),
                                                              maxConnTime, logAppend, maxCheckoutSeconds, debugLevel);
                if (log.isDebugEnabled())
                    log.debug("Created the DbConnectionBroker");
            }
        }
    }

}
