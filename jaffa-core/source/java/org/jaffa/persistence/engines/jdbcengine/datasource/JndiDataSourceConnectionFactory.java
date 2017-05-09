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
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/** This is a JNDI specific implementation of IConnectionFactory.
 * It assumes that the Jdbc Engine is being executed in the same VM in which a DataSource has already been defined.
 * It will use JNDI to get a handle on the DataSource resource and a Connection from that DataSource.
 *
 * @author  GautamJ
 */
public class JndiDataSourceConnectionFactory implements IConnectionFactory,InternalConnectionManager {
    
    private static final Logger log = Logger.getLogger(JndiDataSourceConnectionFactory.class);
    
    // This is the number of times the createConnection will try to acquire a connection from the pool
    private static final int RETRY_LIMIT = 10;
    
    // The default wait time, between connection retries, in milliseconds
    private static final long DEFAULT_WAIT_TIME = 2000;
    
    // constants for compatibility with configs without connectionClassName
    private static final String DEFAULT_WRAPPER_CLASS = "org.jboss.resource.adapter.jdbc.WrappedConnection"; 
    private static final String DEFAULT_UNWRAP_METHOD = "getUnderlyingConnection";
    
    // **************************************
    // Properties for this Connection Factory
    // **************************************
    private String m_jndiDataSourceName;
    private Long m_maxWait;
    private String connectionClassName;
    private boolean hasConnectionClassName;
    private Class<Connection> connectionClazz;
    private boolean hasConnectionClazz;
    private Method unwrapMethod;
    private boolean hasUnwrapMethod;
        
    /** Getter for property jndiDataSourceName.
     * @return Value of property jndiDataSourceName.
     *
     */
    public String getJndiDataSourceName() {
        return m_jndiDataSourceName;
    }
    
    /** Setter for property jndiDataSourceName.
     * @param jndiDataSourceName New value of property jndiDataSourceName.
     *
     */
    public void setJndiDataSourceName(String jndiDataSourceName) {
        if (jndiDataSourceName == null || jndiDataSourceName.length() == 0)
            m_jndiDataSourceName = null;
        else
            m_jndiDataSourceName = jndiDataSourceName;
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
    
	/** 
	 * Getter for property connectionClassName.
	 * @return String value of property connectionClassName.
	 */
    public String getConnectionClassName() {
		return connectionClassName;
	}

    /**
     * setter used to configure class argument in unwrap call
     * when connectionClassName is not configured, DEFAULT_WRAPPER_CLASS will be used
     * @return
     */
	public void setConnectionClassName(String className) {
		connectionClassName = className;
		hasConnectionClassName = (!(className == null || className.isEmpty()));
	}  
    
    // **************************************
    // Implementation methods
    // **************************************
    
    /** Creates a connection using the DataSource obtained via JNDI.
     * @throws SQLException if any SQL error occurs
     * @throws IOException if any IO error occurs
     * @return a Connection
     */
    public Connection createConnection() throws SQLException, IOException {
        try {
            Context initContext = new InitialContext();
            DataSource dataSource = (DataSource) initContext.lookup(getJndiDataSourceName());
            Connection connection = null;
            SQLException se = null;
            
            for (int i = 0; i < RETRY_LIMIT; i++) {
                try {
                    connection = dataSource.getConnection();
                } catch (SQLException e) {
                    // maintain a reference to the exception
                    se = e;
                }
                
                if (connection != null)
                    break;
                
                if (log.isDebugEnabled())
                    log.debug((i+1) + " : Could not acquire Connection from JNDI DataSource. Will try again.");
                
                try {
                    Thread.sleep(getMaxWait() != null ? getMaxWait().longValue() : DEFAULT_WAIT_TIME);
                } catch (Exception e) {
                    // do nothing
                }
            }
            
            if (connection == null) {
                String str = "Could not acquire a connection from JNDI DataSource even after " + RETRY_LIMIT + " tries";
                log.error(str, se);
                throw se != null ? se : new SQLException(str);
            }
            
            if (log.isDebugEnabled())
                log.debug("Acquired a pooled Connection from JNDI DataSource resource: " + connection);
            return connection;
            
        } catch (NamingException e) {
            String str = "Error in performing a JNDI lookup for the JNDI DataSource resource: " + getJndiDataSourceName();
            log.error(str, e);
            throw new IOException(str);
        }
        
    }
    
    /** Returns the connection back to the pool.
     * @param connection The connection to return back to the pool.
     * @throws SQLException if any SQL error occurs
     * @throws IOException if any IO error occurs
     */
    public void freeConnection(Connection connection) throws SQLException, IOException {
        if (log.isDebugEnabled())
            log.debug("Freeing up the pooled Connection back to the JNDI pool: " + connection);
        connection.close();
    }


    @Override
    public Connection getInternalConnection(Connection connection) throws SQLException {
	    if (log.isDebugEnabled())
            log.debug("Obtain the underlying connection from the WrappedConnection");
        try {
    	    if (hasConnectionClassName) {
				connection = connection.unwrap(getConnectionClass());
    	    }
            else if (getConnectionClass().getName().equals(connection.getClass().getName())) { 
				connection = (Connection) getUnwrapMethod().invoke(connection, (Object[]) null);   		   
    	    }
        }
        catch (Exception exp){
            log.error( exp);
            throw new SQLException(exp);
        }

        return connection;
    }
	
	private Class<Connection> getConnectionClass() throws ClassNotFoundException {
		if (!hasConnectionClazz) {
			String className = (hasConnectionClassName) ? connectionClassName : DEFAULT_WRAPPER_CLASS;
			connectionClazz = (Class<Connection>) Class.forName(className); 
			hasConnectionClazz = true;
		}
		return connectionClazz;
	}
	
	private Method getUnwrapMethod() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		if (!hasUnwrapMethod) {
			unwrapMethod = getConnectionClass().getMethod((DEFAULT_UNWRAP_METHOD),(Class[]) null);
			hasUnwrapMethod = true;
		}
		return unwrapMethod;
	}
}
