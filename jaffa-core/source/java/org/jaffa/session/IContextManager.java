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

/*
 * IContextManager.java
 *
 * Created on October 10, 2004, 11:56 PM
 */

package org.jaffa.session;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * Methods on this interface can be accessed via
 * ContextManagerFactory.instance(). For example to get a property based on the
 * context heirarchy use the following code.
 * <code>
 * String value = (String) ContextManagerFactory.instance().getProperty("some.application.setting");
 * </code>
 * <p>
 * It is recommended that all values are accessed after the
 * set thread context is invoked prior. If you are concerned about thread context security, then make sure
 * the 'unsetThreadContext()' is called. For example
 * <code><pre>
 * try {
 *    ContextManagerFactory.instance().setThreadContext(request);
 *
 *    // rest of code
 * } finally {
 *    ContextManagerFactory.instance().unsetThreadContext();
 * }
 * </pre></code>
 *
 * @author PaulE
 * @version 1.0
 */
public interface IContextManager {

    public static final String PROPERTY_USER_ID = "user.id";
    public static final String PROPERTY_USER_PRINCIPAL = "user.principal";
    public static final String PROPERTY_USER_HOSTNAME = "user.hostname";
    public static final String PROPERTY_USER_DATA = "user.data";
    public static final String PROPERTY_USER_SESSION_ID = "user.sessionId";
    public static final String PROPERTY_USER_LOCALE = "user.locale";
    public static final String PROPERTY_USER_VARIATION = "user.variation";
    public static final String PROPERTY_USER_PREFERENCES_FOLDER = "user.preferences.folder";
    public static final String PROPERTY_USER_PREFERENCES_FILE = "user.preferences.file";
    
    /** Sets the thread context.
     * @param request the request being processed.
     */
    public void setThreadContext(HttpServletRequest request);
    
    /** Unsets the thread context.
     */
    public void unsetThreadContext();
    
    /** Returns the thread context.
     * @return the thread context.
     */
    public Map getThreadContext();
    
    /** Returns the value for the given property.
     * This will traverse up the context chain to search for the property.
     * @param key the property.
     * @return the value for the given property.
     */
    public Object getProperty(Object key);
    
    /** Sets the property in the thread context.
     * This property will be available only for the duration of the thread and will not be persisted.
     * @param key the key for a property.
     * @param value the value for a property.
     * @return the previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setProperty(Object key, Object value);
    
    /** Returns a set containing all the keys in the different contexts.
     * @return a set containing all the keys in the different contexts.
     */
    public Set getPropertyNames();
    
    /** Returns a set containing all the keys in the different contexts and which match the input regex filter.
     * @param filter the regular expression to filter the keys.
     * @return a set containing all the keys in the different contexts and which match the input regex filter.
     */
    public Set getPropertyNames(String filter);
    
    /** Set a user preference, which will persisted beyond a session.
     * @param name Name of the preference.
     * @param value Value of the preference.
     * @throws IOException if any I/O error occurs.
     */
    public void setUserPreference(String name, String value) throws IOException;
    
    /** Set user preferences, which will persisted beyond a session.
     * @param userPreferences Property Object containing preferences.
     * @throws IOException if any I/O error occurs.
     */
    public void setUserPreferences(Properties userPreferences) throws IOException;

    /** Unset user preferences already persisted, which will be removed beyond a session.
     * @param userPreferences Set containing preference names to be removed.
     * @throws IOException if any I/O error occurs.
     */
    public void unSetUserPreferences(Set userPreferences) throws IOException;

    /** Unset a user preference already persisted, which will be removed beyond a session.
     * @param name Name of the preference.
     * @throws IOException if any I/O error occurs.
     */
    public void unSetUserPreference(String name) throws IOException;
}
