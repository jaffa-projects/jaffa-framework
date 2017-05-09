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

import java.lang.reflect.Constructor;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.UserSessionSetupException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.security.UserContext;

/** A factory for creating instances of UserContextFactory.
 */
public class UserContextWrapperFactory {
    
    private static final Logger log = Logger.getLogger(UserContextWrapperFactory.class);
    private static final String RULE_NAME = "jaffa.user.UserContextWrapper";
    
    /** Returns an implementation of the IContextManager interface.
     * The implementation class may be provided by the rule 'jaffa.user.UserContextWrapper'.
     * If the rule is not provided, an instance of org.jaffa.modules.user.services.UserContextWrapper will be returned.
     * @param userId the user id.
     * @return Returns an implentation of the IContextManager interface.
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    public static UserContextWrapper instance(String userId) throws UserSessionSetupException {
        UserContext userContext = new UserContext();
        userContext.setUserId(userId);
        return instance(userContext);
    }

    /** Returns an implementation of the IContextManager interface.
     * The implementation class may be provided by the rule 'jaffa.user.UserContextWrapper'.
     * If the rule is not provided, an instance of org.jaffa.modules.user.services.UserContextWrapper will be returned.
     * @param userContext contains user ID and role info
     * @return Returns an implementation of the IContextManager interface.
     * @throws UserSessionSetupException if any error occurs while creating a UserSession for the user.
     */
    public static UserContextWrapper instance(UserContext userContext) throws UserSessionSetupException {
        String className = (String) ContextManagerFactory.instance().getProperty(RULE_NAME);
        if (className == null) {
            if (log.isDebugEnabled())
                log.debug("Rule '" + RULE_NAME + "' not specified. Creating an instance of " + UserContextWrapper.class);
            return new UserContextWrapper(userContext);
        } else {
            if (log.isDebugEnabled())
                log.debug("Creating an instance of " + className);
            try {
                Class clazz = Class.forName(className);
                if (UserContextWrapper.class.isAssignableFrom(clazz)) {
                    try {
                        Constructor<UserContextWrapper> c = clazz.getConstructor(UserContext.class);
                        return c.newInstance(userContext);
                    } catch (NoSuchMethodException e) {
                        String s = "Class '" + className + "', which has been specified for the rule '" + RULE_NAME + "' should have a constructor that takes a userId as an input parameter";
                        log.error(s, e);
                        throw new IllegalArgumentException(s, e);
                    } catch (Exception e) {
                        String s = "Class '" + className + "', which has been specified for the rule '" + RULE_NAME + "' could not be instantiated";
                        log.error(s, e);
                        throw new IllegalArgumentException(s, e);
                    }
                } else {
                    String s = "Class '" + className + "', which has been specified for the rule '" + RULE_NAME + "' should equal or be a subclass of " + UserContextWrapper.class;
                    log.error(s);
                    throw new IllegalArgumentException(s);
                }
            } catch (ClassNotFoundException e) {
                String s = "Class '" + className + "', which has been specified for the rule '" + RULE_NAME + "' not found";
                log.error(s, e);
                throw new IllegalArgumentException(s, e);
            }
        }
    }
}
