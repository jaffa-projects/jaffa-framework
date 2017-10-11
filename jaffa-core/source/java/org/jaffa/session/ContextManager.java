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
 * ContextManager.java
 *
 * Created on October 10, 2004, 11:52 PM
 */

package org.jaffa.session;

import org.apache.log4j.Logger;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.util.NestedMap;
import org.jaffa.util.StringHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Base implementation of the IContextManager. It reads Global Context,
 * Variation context and UserPreferences from properties files, and gets session information
 * from the UserSession object if available.
 *
 * It assumes that a UserSession has been created and initialized prior to
 * invoking setThreadContext().
 *
 * Contexts are built based on the org.jaffa.util.NestedMap, such that Thread
 * settings override Session settings which override User Preferences which override Variation settings which
 * override Global settings.
 *
 * @author  PaulE
 * @version  1.0
 */
public class ContextManager implements IContextManager {

    private static final Logger log = Logger.getLogger(ContextManager.class);


    private static ThreadLocal threadContext = new ThreadLocal();
    private static ThreadLocal userPreferencesInThread = new ThreadLocal();

    private Map m_global = null; // Contains global properties
    private Map m_variation = new WeakHashMap(); // Contains variation/properties pairs

    private static ApplicationRulesManager applicationRulesManager;

    /**
     * Used to set the context on the thread. This uses all the other methods in the class to build
     * up a hierarchical context map.
     * <p>
     * NOTE: as this calls getSessionContext(), you must make sure that the UserSession has been initialized
     * <p>
     * It is recommended that all values are accessed via the thread context, and that the
     * set thread context is invoked prior to any calls to 'getThreadContext()' or
     * 'getProperty()'. If you are concerned about thread context security, then make sure
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
     * @param request the request being processed.
     */
    public void setThreadContext(HttpServletRequest request) {
        Map m = (Map) threadContext.get();
        if (m==null || m.size()==0 || m.get("hasSession")==null) {
            if (log.isDebugEnabled()) log.debug("Setting Thread Context.");
            m = getGlobalContext();
            Map sessionContext = getSessionContext(request);
            String userPreferencesFileName = null;
            if (sessionContext != null) {
                // global + variation
                String variation = (String) sessionContext.get(PROPERTY_USER_VARIATION);
                if (variation != null) {
                    m = new NestedMap(m, getVariationContext(variation));
                }
                // global + variation + user-preferences
                String userId = (String) sessionContext.get(PROPERTY_USER_ID);
                if (userId != null) {
                    // Get the location for the folder containing the user preferences from the global + variant settings
                    userPreferencesFileName = getUserPreferencesFileName(userId, (String) m.get(PROPERTY_USER_PREFERENCES_FOLDER));
                    m = new NestedMap(m, getUserPreferences(userPreferencesFileName));
                }

                // global + variation + user-preferences + session
                m = new NestedMap(m, sessionContext);
            }

            // global {+ variation + user-preferences + session} + thread
            m = new NestedMap(m);
            // Put the request in the thread, as this may be useful later
            m.put("request", request);
            if (sessionContext!=null) m.put("hasSession", true);

            // Add the userPreferencesFileName to the thread context
            if (userPreferencesFileName != null) {
                m.put(PROPERTY_USER_PREFERENCES_FILE, userPreferencesFileName);
            } else {
                m.remove(PROPERTY_USER_PREFERENCES_FILE);
            }
            threadContext.set(m);
        }
    }

    /**
     * Used to unset the context on the thread.
     * @see #setThreadContext(HttpServletRequest)
     */
    public void unsetThreadContext() {
        threadContext.set(null);
        userPreferencesInThread.set(null);
    }

    /**
     * Gets the current context (java Map) associated to the thread, so values can be aquired
     * As the Map returned is a Nested Map, when extracting values it will search the Thread, Session
     * UserPreferences, Variation and Global contexts. Any methods that manipulate the Map will only have an effect on
     * the Thread values, although any value inserted in the Thread context with the same name will
     * override the value in all the other contexts.
     *
     * The GlobalContext is returned, in case a thread context has not been created yet.
     * This may be useful, if a global setting is used during the setting up of the thread context.
     *
     * @return the thread context.
     */
    public Map getThreadContext() {
        Map m = (Map) threadContext.get();
        return m != null ? m : getGlobalContext();
    }

    /**
     * Get a property from the thread context. This is typically used as following
     * <code>
     * String rule = (String) ContextManagerFactory.instance().getProperty('my.rule');
     * </code>
     * <p>
     * It is a short hand for
     * <code>
     * String rule = (String) ContextManagerFactory.instance().getThreadContext().get('my.rule');
     * </code>
     * @param key the property.
     * @return the value for the given property.
     */
    public Object getProperty(Object key) {
        Map m = getThreadContext();
        String appRuleValue = m != null ? (String) m.get(key) : null;
        if(appRuleValue != null && appRuleValue.contains("${")){
            String tokenName = appRuleValue.substring(appRuleValue.indexOf("${") + 2, appRuleValue.lastIndexOf("}"));
            String tokenValue = (String) getProperty(tokenName);
            if(log.isDebugEnabled()) {
                log.debug("Token Name Used " + tokenName + " and its value " + tokenValue);
            }
            if(tokenValue != null){
                appRuleValue = StringHelper.replace(appRuleValue, "${" + tokenName + "}", tokenValue);
            }

        }
        return appRuleValue;
    }

    /** Sets the property in the thread context.
     * This property will be available only for the duration of the thread and will not be persisted.
     * @param key the key for a property.
     * @param value the value for a property.
     * @return the previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setProperty(Object key, Object value) {
        Map m = getThreadContext();
        return m != null ? m.put(key, value) : null;
    }

    /** Returns a set containing all the keys in the different contexts.
     * @return a set containing all the keys in the different contexts.
     */
    public Set getPropertyNames() {
        Map m = getThreadContext();
        return m != null ? m.keySet() : null;
    }

    /** Returns a set containing all the keys in the different contexts and which match the input regex filter.
     * @param filter the regular expression to filter the keys.
     * @return a set containing all the keys in the different contexts and which match the input regex filter.
     */
    public Set getPropertyNames(String filter) {
        Set s = getPropertyNames();
        if (s != null && filter != null) {
            Set filteredSet = new HashSet();
            for (Iterator i = s.iterator(); i.hasNext();) {
                String key = i.next().toString();
                if (key.matches(filter)) {
                    filteredSet.add(key);
                }
            }
            return filteredSet;
        } else {
            return s;
        }
    }

    /** Set a user preference, which will persisted beyond a session.
     * @param name Name of the preference.
     * @param value Value of the preference.
     * @throws IOException if any I/O error occurs.
     */
    public void setUserPreference(String name, String value) throws IOException {
        String userPreferencesFileName = (String) getProperty(PROPERTY_USER_PREFERENCES_FILE);
        if (userPreferencesFileName != null) {
            Map m = getUserPreferences(userPreferencesFileName);
            m.put(name, value);

            // Save the property to file
            storePropertiesToFile((Properties) m, userPreferencesFileName);
        }
    }


    /** Unset a user preference already persisted, which will be removed beyond a session.
     * @param name Name of the preference.
     * @throws IOException if any I/O error occurs.
     */
    public void unSetUserPreference(String name) throws IOException {
        String userPreferencesFileName = (String) getProperty(PROPERTY_USER_PREFERENCES_FILE);
        if (userPreferencesFileName != null) {
            Map m = getUserPreferences(userPreferencesFileName);
            m.remove(name);

            // Save the property to file
            storePropertiesToFile((Properties) m, userPreferencesFileName);
        }
    }


    /** Set user preferences, which will persisted beyond a session.
     * @param userPreferences Property Object containing preferences.
     * @throws IOException if any I/O error occurs.
     */
    public void setUserPreferences(Properties userPreferences) throws IOException {
        String userPreferencesFileName = (String) getProperty(PROPERTY_USER_PREFERENCES_FILE);
        if (userPreferencesFileName != null) {
            Map m = getUserPreferences(userPreferencesFileName);

            // enumerate over the properties and write them to the map
            Enumeration enumeration = userPreferences.keys();
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                String value = userPreferences.getProperty(name);
                m.put(name, value);
            }

            // Save the property to file
            storePropertiesToFile((Properties) m, userPreferencesFileName);
        }
    }

    /** Unset user preferences already persisted, which will be removed beyond a session.
     * @param userPreferences Set containing preference names to be removed.
     * @throws IOException if any I/O error occurs.
     */
    public void unSetUserPreferences(Set userPreferences) throws IOException {
        String userPreferencesFileName = (String) getProperty(PROPERTY_USER_PREFERENCES_FILE);
        if (userPreferencesFileName != null) {
            Map m = getUserPreferences(userPreferencesFileName);

            // enumerate over the properties and write them to the map
            Iterator iterator = userPreferences.iterator();
            while (iterator.hasNext()) {
                String name = (String) iterator.next();
                m.remove(name);
            }

            // Save the property to file
            storePropertiesToFile((Properties) m, userPreferencesFileName);
        }
    }


    /**
     * Get the global context, this is read once and cached. It looks for
     * a properties file called 'resources/ApplicationRules.global'
     * @return the global context.
     */
    private Map getGlobalContext() {
        if (m_global == null) {
            synchronized (this) {
                if (m_global == null) {
                    Properties props = null;
                    props = (applicationRulesManager != null) ? applicationRulesManager.getApplicationRulesGlobal() : null;

                    // Cache an unmodifiable view
					if (props != null) {
						m_global = Collections.unmodifiableMap(props);
					}
                }
            }
        }
        return m_global;
    }

    /**
     * Get a variation context, this is read once and cached per variation. It looks for
     * a properties file called 'resources/ApplicationRules.{variation}'
     * @param variation the variation.
     * @return the variation context.
     */
    private Map getVariationContext(String variation) {
        if (!m_variation.containsKey(variation)) {
            synchronized (m_variation) {
                if (!m_variation.containsKey(variation)) {
                    Properties props = null;
					if (props == null || props.size() == 0) {
						props = applicationRulesManager.getApplicationRulesVariation(variation);
					}
                    
                    // Cache an unmodifiable view
					if (props != null) {
						m_variation.put(variation, Collections.unmodifiableMap(props));
					}
                }
            }
        }
        return (Map) m_variation.get(variation);
    }

    /** Returns the preferences for the input userId.
     * If the 'user.preferences.folder' property is specified, then it looks for a properties file 'getProperty("user.preferences.folder")/{userId}/user.properties'.
     * Else, it looks for a properties file '{System.getProperty("user.home")}/.jaffa/user-preferences/{userId}/user.properties'.
     * This is read once and then cached in the thread.
     * @param location the file containing the user preferences.
     * @return the preferences for the input userId.
     */
    private Map getUserPreferences(String location) {
        Properties props = (Properties) userPreferencesInThread.get();
        if (props == null) {
            // Read the user preferences
            InputStream input = null;
            props = new Properties();
            try {
                // input = URLHelper.getInputStream(location);
                input = new BufferedInputStream(new FileInputStream(new File(location)));
                if (input != null) {
                    props.load(input);
                    if (log.isDebugEnabled()) {
                        if (props.size() < 1) {
                            log.debug("No user preferences defined in file " + location);
                        } else {
                            log.debug("Loaded " + props.size() + " rule(s) from " + location);
                        }
                    }
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("No user preferences found. Can't find file " + location);
                    }
                }
            } catch (IOException e) {
                // No User preference file available;
                if (log.isDebugEnabled()) {
                    log.debug("No user preferences found. Error in loading file " + location, e);
                }
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    if (log.isInfoEnabled()) {
                        log.info("Exception thrown while closing the properties file", e);
                    }
                }
            }
            userPreferencesInThread.set(props);
        }
        return props;
    }

    /**
     * Create a session context and adds in the following entries
     * <ul>
     * <li>user.id (java.lang.String) ID of authenticated user
     * <li>user.principal (java.security.Principal) authenticated user's principal
     * <li>user.hostname (java.lang.String) Name of host user is comming from
     * <li>user.data (java.lang.Object) custom object that can be bound to a user's profile at logon
     * <li>user.sessionId (java.lang.String)
     * <li>user.locale (java.lang.String) Locale of the user, default to local of server if not available
     * <li>user.variation (java.lang.String) Varition applicable to this user. Defined at logon
     * </ul>
     * @param request the request being processed.
     * @return the session context.
     */
    private Map getSessionContext(HttpServletRequest request) {
        if (request != null && UserSession.isUserSession(request)) {
            UserSession us = UserSession.getUserSession(request);
            HashMap h = new HashMap();
            h.put(PROPERTY_USER_ID, us.getUserId());
            h.put(PROPERTY_USER_PRINCIPAL, request.getUserPrincipal());
            h.put(PROPERTY_USER_HOSTNAME, us.getUserHostAddr());
            h.put(PROPERTY_USER_DATA, us.getUserData());
            h.put(PROPERTY_USER_SESSION_ID, us.getSessionId());
            h.put(PROPERTY_USER_LOCALE, request.getLocale());
            h.put(PROPERTY_USER_VARIATION, us.getVariation());
            return h;
        } else {
            return null;
        }
    }

    /** If folder is passed, then it returns '{folder}/{userId}/user.properties'.
     * Else, it returns '{System.getProperty("user.home")}/.jaffa/user-preferences/{userId}/user.properties'.
     */
    private static String getUserPreferencesFileName(String userId, String folder) {
        StringBuffer buf = new StringBuffer();
        if (folder != null) {
            buf.append(folder);

            // Add a '/' if required
            if (buf.length() > 0 && buf.charAt(buf.length() - 1) != '/' && buf.charAt(buf.length() - 1) != '\\') {
                buf.append('/');
            }
        } else {
            // Use the default folder
            buf.append(System.getProperty("user.home"));

            // Add a '/' if required
            if (buf.length() > 0 && buf.charAt(buf.length() - 1) != File.separatorChar) {
                buf.append(File.separatorChar);
            }
            buf.append(".jaffa").append(File.separatorChar).append("user-preferences").append(File.separatorChar);
        }
        String encodedUserId = userId;
        try {
            encodedUserId = URLEncoder.encode(userId, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error("UserId encoding error.", ex);
        }
        buf.append(encodedUserId).append(File.separatorChar).append("user.properties");

        return buf.toString();
    }

    /** Stores the input properties into the given file.
     * The order in which the properties are written to the file is not guaranteed. Comments in an existing file will be lost.
     * @param properties The properties to be stored.
     * @param fileName The file into which the properties will be stored.
     * @throws IOException if any error occurs.
     */
    private static void storePropertiesToFile(Properties properties, String fileName) throws IOException {
        OutputStream os = null;
        try {
            // Create the directory for the input fileName, if it doesn't already exist.
            File f = new File(fileName);
            if (!f.exists()) {
                if (log.isDebugEnabled()) {
                    log.debug("File " + fileName + " does not exist. Check the existence of its directory");
                }
                File dir = f.getParentFile();
                if (dir != null && !dir.exists()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Directory " + dir.getPath() + " does not exist. Creating it..");
                    }
                    dir.mkdirs();
                }
            }
            os = new BufferedOutputStream(new FileOutputStream(f, false));
            properties.store(os, null);
            if (log.isDebugEnabled()) {
                log.debug("Saved properties to " + fileName);
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }


    public static ApplicationRulesManager getApplicationRulesManager() {
        return applicationRulesManager;
    }

    public static void setApplicationRulesManager(ApplicationRulesManager applicationRulesManager) {
        ContextManager.applicationRulesManager = applicationRulesManager;
    }
}
