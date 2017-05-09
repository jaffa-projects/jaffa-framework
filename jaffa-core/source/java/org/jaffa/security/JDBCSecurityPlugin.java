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
package org.jaffa.security;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.jaffa.persistence.engines.jdbcengine.security.IJdbcSecurityPlugin;
import org.jaffa.exceptions.UOWSecurityException;

import oracle.sql.ARRAY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jaffa.security.jdbcsecurity.domain.JdbcSecuritySetContext;
import org.jaffa.security.jdbcsecurity.domain.JdbcSecurityUnsetContext;
import org.jaffa.session.ContextManagerFactory;

/**
 * JDBCSecurityPlugin.java
 *
 * @author  maheshd
 * @version 1.1
 */
public class JDBCSecurityPlugin implements IJdbcSecurityPlugin {

    private static final Logger log = Logger.getLogger(JDBCSecurityPlugin.class);
    private static final String LOCAL_ID = "jaffa.soa.events.localId";
    private static final String ROLE = "ROLE";
    private static final String CALL_JAFFA_SEC_SET_USERID = "{call jaffa_sec.set_userid(?,?,?)}";
    private static final String CALL_JAFFA_SEC_CLEARCTX = "{call jaffa_sec.clearctx}";
    private static final String SET_THE_CONTEXT_ERROR_MESSAGE = "Error in executing the the Stored Procedure to Set the Context [%s]";
    private static final String CLEARING_THE_CONTEXT_ERROR_MESSAGE = "Error in executing the the Stored Procedure for Clearing the Context";
    private static final String ERROR_EXECUTING_NEW_CONNECTION_TRIGGER = "Error in executing the newConnection trigger of the JdbcSecurityPlugin";

    /** This holds previously calculated values for the role list per user.
     * It is possible for the JVM Garbage Collecter to empty this if needed
     */
    private static Map<String,List<String>> cache = Collections.synchronizedMap(new WeakHashMap<String,List<String>>());

    /** This holds the impersonate userid for the given user, may be the same.
     * It is possible for the JVM Garbage Collecter to empty this if needed
     */
    private static Map<String,String> impersonate = Collections.synchronizedMap(new WeakHashMap<String,String>());

    private Object setCtxLock = new Object();
    private Object clearCtxLock = new Object();

    /** Creates new JDBCSecurityPlugin */
    public JDBCSecurityPlugin() {
    }

    protected void executeStoredProcedure(final Connection pooledConnection, final String userid) throws java.sql.SQLException {
        CallableStatement cs = null;

        final String localId = (String) ContextManagerFactory.instance().getProperty(LOCAL_ID);
        Object[] roles = getUserRoles(userid).toArray();
        ARRAY newArray = null;

        Connection connection = null;
        try {
            // Extract the actual oracle connection
            connection = pooledConnection.unwrap(oracle.jdbc.OracleConnection.class);
            if (!(connection instanceof oracle.jdbc.OracleConnection)) {
            	throw new Exception(String.format("Not an Oracle Connection : %s",connection.getClass().getName()));
            }

            cs = connection.prepareCall(CALL_JAFFA_SEC_SET_USERID);
            cs.setString(1,userid);

            newArray = ((oracle.jdbc.OracleConnection)connection).createARRAY(ROLE,roles);
            cs.setArray(2,newArray);
            cs.setString(3,localId);

            if (log.isDebugEnabled()) {
                // Log the call to the stored procedure
                debugStatement(userid, localId, roles, newArray);
            }

            if(log.isDebugEnabled()) log.debug("Calling the Stored Procedure to Set the Context");
            cs.execute();
            connection.commit();
        }
        catch (Exception exception) {
        	final String message = String.format(String.format(SET_THE_CONTEXT_ERROR_MESSAGE,exception.getMessage()));
            log.error(message, exception);
            if (connection != null)
            	connection.rollback();
            throw new UOWSecurityException(message, exception);
        }
        finally {
            roles = null;
            if (newArray != null)
                newArray.free();
            if (cs != null) {
                cs.clearParameters();
                cs.close();
                cs = null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newConnection(Connection pooledConnection) throws java.sql.SQLException {
        synchronized(setCtxLock) {
            if(log.isDebugEnabled()) log.debug("Getting the Security Context");
            SecurityContext current = SecurityManager.getCurrentContext();
            if(current != null) {
                if(log.isDebugEnabled()) log.debug("Getting the Principal");
                Principal p = current.getPrincipal();
                if(p != null){
                    String userid = p.getName();
                    if(userid != null){
                        try{
                            if (pooledConnection != null) {
                                userid = checkImpersonate(userid);
                                executeStoredProcedure(pooledConnection,userid);
                            }
                        }
                        catch (SQLException e) {
                            log.error(ERROR_EXECUTING_NEW_CONNECTION_TRIGGER, e);
                            throw new UOWSecurityException(ERROR_EXECUTING_NEW_CONNECTION_TRIGGER, e);
                        }
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeConnection(Connection pooledConnection)  throws java.sql.SQLException {
        synchronized(clearCtxLock) {
            CallableStatement cs = null;
            try {
                cs = pooledConnection.prepareCall(CALL_JAFFA_SEC_CLEARCTX);
                if(log.isDebugEnabled()) log.debug("Clearing the Security Context");
                cs.execute();
            }
            catch (SQLException e) {
                log.error(CLEARING_THE_CONTEXT_ERROR_MESSAGE, e);
                throw new UOWSecurityException(CLEARING_THE_CONTEXT_ERROR_MESSAGE, e);
            }
            finally {
                if (cs != null) {
                    cs.clearParameters();
                    cs.close();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSecurableConnection(Connection connection) {
        try {
            return connection != null && connection.unwrap(oracle.jdbc.OracleConnection.class) instanceof oracle.jdbc.OracleConnection;
        }catch (SQLException e){
            return false;
        }
    }

    /** Get the roles for this user. If we can find a cached list use that, else use
     * the current security context to check them
     */
    private List<String> getUserRoles(final String userid) {
        List<String> cachedList = cache.get(userid);
        if(cachedList != null)
            return cachedList;

        List roleList = SecurityManager.getUserRoles();

        // Cache array and return value.
        cache.put(userid, roleList);
        return roleList;
    }

    /** Flush the cached list of security roles
     */
    public static void clearCache() {
        cache = Collections.synchronizedMap(new WeakHashMap<String,List<String>>());
    }

    /** Flush the cached list of security roles for a specific user
     */
    public static void clearCacheForUser(final String userId) {
        cache.remove(userId);
    }

    /** Check SSO session variable
    */
    private static String checkImpersonate(String userid) {
       String ssouser = impersonate.get(userid);
       if(ssouser == null) {
          HttpServletRequest request = null;
          HttpSession session = null;

          try {
              if(log.isDebugEnabled())
                  log.debug("getting request");
              request = (HttpServletRequest) ContextManagerFactory.instance().getProperty("request");
          } catch (Exception e) {
             if(log.isDebugEnabled())
                log.debug("unable to get request");
          }

          if(request != null) {
             if(log.isDebugEnabled())
                log.debug("got request");
             session = request.getSession(false);
             if(session != null) {
                if(log.isDebugEnabled())
                   log.debug("got session");
                ssouser = (String) session.getAttribute("org.jaffa.security.impersonate.user");
          	    if(ssouser != null) {
          	    	 if(log.isDebugEnabled())
                      log.debug("found sso session attribute, setting userid to "+ssouser);
                } else {
                   if(log.isDebugEnabled())
                      log.debug("ssouser is null, setting value to "+userid);
                   ssouser = userid;
                }
                impersonate.put(userid,ssouser);
             }
          } else {
          	 if(log.isDebugEnabled())
          	    log.debug("null request, returning "+userid);
          	 return userid;
          }
       }
       if(log.isDebugEnabled())
          log.debug("returning "+ssouser);
       return ssouser;
    }

    /** write statement call to log
     *
     * @param userid
     * @param localId
     * @param roles
     * @param newArray
     */
    private void debugStatement(String userid, final String localId, final Object[] roles, final ARRAY newArray) {
        StringBuffer buf = new StringBuffer("call jaffa_sec.set_userid(?,?,?) with arg1='")
        .append(userid)
        .append("', arg2='")
        .append(newArray)
        .append("'");
        if (roles != null) {
            buf.append(". Arg2 has the roles: ");
            for (int i = 0; i < roles.length; i++) {
                if (i > 0)
                    buf.append(',');
                buf.append(roles[i]);
            }
        }
        buf.append(", arg3='");
        buf.append(localId);
        buf.append("'");

        log.debug(buf.toString());
    }
}
