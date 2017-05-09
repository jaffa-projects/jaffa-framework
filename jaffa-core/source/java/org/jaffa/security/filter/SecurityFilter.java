/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2009 JAFFA Development Group
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
 
package org.jaffa.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/** SecurityFilter is meant to be used as an addition filter to enable role based security. 
 * It provides a mechanism similar to the container based security where a given user has
 * a set of roles based on the authenticated userid. This filter presumes that another filter 
 * is in place that will wrapper the getRemoteUser call with the authenticated userid.  
 *
 */
public class SecurityFilter implements Filter {

    /** Set up Logging for Log4J */
    private static final Logger log = Logger.getLogger(SecurityFilter.class);

    String dsJndiName = null;
    String rolesQuery = null;
    String defaultRoles = null;
    String principalsQuery = null;
    String errorUrl = null;

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    /** Initialize the filter.
     * @param filterConfig FilterConfig the Config block from the filter definition
     * @throws ServletException if any required Config parameter is not found.
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("init()");

        dsJndiName = filterConfig.getInitParameter("dsJndiName");
        if(dsJndiName == null) {
           log.debug("dsJndiName not found!");
           throw new ServletException("dsJndiName parameter required");
        }
        log.debug("dsJndiName [" + dsJndiName + "]");

        rolesQuery = filterConfig.getInitParameter("rolesQuery");
        if(rolesQuery == null) {
           log.debug("rolesQuery not found!");
           throw new ServletException("rolesQuery parameter required");
        }
        log.debug("rolesQuery [" + rolesQuery + "]");

        defaultRoles = filterConfig.getInitParameter("defaultRoles");
        log.debug("defaultRoles [" + defaultRoles + "]");

        principalsQuery = filterConfig.getInitParameter("principalsQuery");
        log.debug("principalsQuery [" + principalsQuery + "]");
    }

    /** Perform the filter action.
     * @param request ServletRequest the request being perfomred
     * @param response ServletResponse the response to be sent
     * @param chain FilterChain the filter chain to continue execution of
     * @throws IOException 
     * @throws ServletException 
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("doFilter()");

        HttpSession session = ((HttpServletRequest) request).getSession(false);

        log.debug("Getting session principal");
        Principal principal = (Principal) session.getAttribute("org.jaffa.security.filter.SecurityFilterPrincipal");
        if(principal == null) {
           // principal was null, not authenticated/new session

           String user = ((HttpServletRequest) request).getRemoteUser().toUpperCase();
           if(user == null) {
              throw new ServletException("Request user not found.");
           }

           boolean authenticated = authenticateUser(user);

           if(!authenticated) {
              log.debug("User ["+user+"] failed authentication");

              response.setContentType("text/html");

              PrintWriter out = response.getWriter();
              out.println("<html><head><title>Authentication Failed</title></head><body>");
              out.println("<h2>Sorry you are not authorized to use this application</h2>");
              out.println("</body></html>");
              out.close();

              log.debug("Invalidating session");
              session.invalidate();

              log.debug("return");
              return;
           }

           log.debug("Getting Roles for user [" + user + "]");
           List roles = getRoles(user);

           log.debug("Setting session principal");
           principal = new SecurityFilterPrincipal(user, roles);
           session.setAttribute("org.jaffa.security.filter.SecurityFilterPrincipal", principal);
        }
        log.debug("Principal [" + principal.getName() + "]");

        log.debug("Wrapping request through SecurityFilterRequestWrapper");
        request = new SecurityFilterRequestWrapper((HttpServletRequest) request);

        log.debug("chain.doFilter()");
        chain.doFilter(request, response);
    }

    /** destory method
     */
    public void destroy() {
    }

    // ------------------------------------------------------------------------
    // Protected Methods
    // ------------------------------------------------------------------------

    /** Get the roles for the given user. 
     * Performs a sql query (rolesQuery) with the given user as the parameter. 
     * @param user String The username to perform the rolesQuery for
     * @return Returns an ArrayList of roles 
     *         If no roles are found it will check if defaultRoles are to be used and return those
     *         otherwise an empty ArrayList is returned
     */
    protected ArrayList getRoles(String user) {
        ArrayList rolesList = new ArrayList();

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dsJndiName);
            conn = ds.getConnection();

            ps = conn.prepareStatement(rolesQuery);
            try {
                ps.setString(1, user);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                // The query may not have any parameters so just try it
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                log.debug("no roles found, using defaultRoles");
                if(defaultRoles != null) {
                   rolesList.addAll(Arrays.asList(defaultRoles.trim().split(",")));
                }
                return rolesList;
            }

            do {
                String role = rs.getString(1);
                log.debug("User [" + user + "] has Role [" + role + "]");
                rolesList.add(role);
            } while (rs.next());
            rs.close();
        } catch (NamingException ex) {
            log.error("SQL failure", ex);
        } catch (SQLException ex) {
            log.error("SQL failure", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }

        return rolesList;
    }

    /** Validate the given user. 
     * Performs a sql query (principalsQuery) with the given user as the parameter. 
     * @param user String The username to perform the principalsQuery for
     * @return Returns a boolean if the principalsQuery had a match.
     *         If no query availavle will return true
     *         If the query returns zero rows returns false
     */
    private boolean authenticateUser(String user) {
        Connection conn = null;
        PreparedStatement ps = null;

        // no query to authenticate user with
        if(principalsQuery == null) {
           return true;
        }

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dsJndiName);
            conn = ds.getConnection();

            ps = conn.prepareStatement(principalsQuery);
            try {
                ps.setString(1, user);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                // The query may not have any parameters so just try it
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                log.debug("no rows found, not a valid user");
                return false;
            }
            rs.close();
        } catch (NamingException ex) {
            log.error("SQL failure", ex);
        } catch (SQLException ex) {
            log.error("SQL failure", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }

        return true;
    }
}
