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

package org.jaffa.modules.user.services;

import org.apache.log4j.Logger;
import org.jaffa.applications.jaffa.modules.admin.domain.UserRole;
import org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.presentation.portlet.PortletFilter;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.presentation.portlet.session.UserSessionSetupException;
import org.jaffa.security.SecurityTag;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.security.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class used to set up user based context for special execution threads that require all
 * the same context information normally provided by the web application.
 * This includes
 * ContextManager
 * SecurityContext
 * LocaleContext
 * VariationContext
 * UserSession/UserData
 * For this to be simple and make use of the current programs, a mock HttpServletRequest and HttpSession
 * object will be used.
 *
 * @author PaulE
 */
public class UserContextWrapper {

    private static final Logger log = Logger.getLogger(UserContextWrapper.class);
    HttpServletRequest m_request = null;
    boolean m_killed = false;
    private UserContext userContext;

    /**
     * Creates an instance of UserContextWrapper for the input user.
     * It uses org.jaffa.presentation.portlet.PortletFilter for setting up the contexts.
     *
     * @param userId the user id.
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    public UserContextWrapper(String userId) throws UserSessionSetupException {
        this(userId, PortletFilter.class);
    }

    /**
     * Creates an instance of UserContextWrapper for the input user.
     * It uses org.jaffa.presentation.portlet.PortletFilter for setting up the contexts.
     *
     * @param userContext the user id and roles
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    public UserContextWrapper(UserContext userContext) throws UserSessionSetupException {
        this(userContext, PortletFilter.class);
    }

    /**
     * Creates an instance of UserContextWrapper for the input user.
     *
     * @param userId             the user id.
     * @param portletFilterClass the PortletFilter class, or its subclass, to be used for setting up the contexts.
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    protected UserContextWrapper(String userId, Class portletFilterClass) throws UserSessionSetupException {
        userContext = new UserContext();
        userContext.setUserId(userId);
        initializeUserContext(portletFilterClass);
    }

    /**
     * Creates an instance of UserContextWrapper for the input user
     *
     * @param context            the user id and roles
     * @param portletFilterClass the PortletFilter class, or its subclass, to be used for setting up the contexts.
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    protected UserContextWrapper(UserContext context, Class portletFilterClass) throws UserSessionSetupException {
        userContext = context;
        initializeUserContext(portletFilterClass);
    }

    /**
     * Loads user information into the thread context
     *
     * @param portletFilterClass defined the version of the PortletFilter class to use during setup
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    private void initializeUserContext(Class portletFilterClass) throws UserSessionSetupException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Creating ThreadContext For User " + userContext.getUserId() + " with PortletFilter " + portletFilterClass);
            }

            if (portletFilterClass == null) {
                portletFilterClass = PortletFilter.class;
            }

            PortletFilter pf = (PortletFilter) portletFilterClass.newInstance();

            // Read the roles from the database if they are not already defined
            if (userContext.getRoles() == null) {
                userContext.setRoles(readUserRoles(userContext.getUserId()));
            }

            // Create a Mock Request
            m_request = new MockHttpServletRequest(userContext.getUserId(), userContext.getRoles());

            // Set security context
            SecurityTag.setThreadContext(m_request);

            // Create a user session and initialize
            pf.autoAuthenticate(m_request, userContext, false);

            // Set Variation and Locale Context
            pf.setContexts(m_request);

            // Set Context Manager
            ContextManagerFactory.instance().setThreadContext(m_request);
        } catch (Exception e) {
            if (!(e instanceof UserSessionSetupException)) {
                log.error("Can't Set up UserContext", e);
                throw new UserSessionSetupException(new String[]{userContext.getUserId()}, e);
            } else {
                throw (UserSessionSetupException) e;
            }
        }
    }

    /**
     * Gets the user ID and user roles for the currently set user context
     *
     * @return the currently set user context containing the ID and roles of the user
     */
    public UserContext getUserContext() {
        return userContext;
    }

    /**
     * Remove all the contexts
     */
    public void unsetContext() {
        log.debug("Removing ThreadContext For User " + m_request.getUserPrincipal().getName());
        ContextManagerFactory.instance().unsetThreadContext();
        VariationContext.setVariation(null);
        LocaleContext.setLocale(null);
        UserSession.getUserSession(m_request).kill();
        HttpSession s = m_request.getSession();
        if (s != null) {
            s.invalidate();
        }
        SecurityTag.unsetThreadContext(m_request);
        m_killed = true;
    }

    /**
     * Read the roles for the user from the database
     *
     * @param userId the user id.
     * @return the roles for the user.
     * @throws FrameworkException if any internal error occurs.
     */
    protected String[] readUserRoles(String userId) throws FrameworkException {
        UOW uow = null;
        List<String> roleList = new ArrayList<String>();
        try {
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(UserRoleMeta.getName());
            c.addCriteria(UserRoleMeta.USER_NAME, userId);
            Collection roles = uow.query(c);
            for (Iterator it = roles.iterator(); it.hasNext(); ) {
                UserRole role = (UserRole) it.next();
                roleList.add(role.getRoleName());
            }
        } catch (UOWException e) {
            // Log the error
            log.error("Can't Get The Roles for User - " + userId, e);
        } finally {
            // Attempt to rollback any open transaction
            try {
                if (uow != null) {
                    uow.rollback();
                }
            } catch (UOWException e) {
                log.error("Rollback", e);
            }
        }
        return (String[]) roleList.toArray(new String[0]);
    }
}