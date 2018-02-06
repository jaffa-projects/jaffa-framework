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

package org.jaffa.config;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.jaffa.util.StringHelper;

/** This class manages all the access to the frameworks configuration data
 * In addition it is used for all text string translation.
 *
 * This configuration object manages both static (read from a resource) and dynamic
 * (set by initilization params).
 *
 * Each configuration paramerter will have a static name associated with it. Only
 * the parameters flagged as dynamic will be accepted by the setProperty() method.
 *
 * By default the getters will return Ojects, but any property read from the resource
 * will be returned as string. The dynapic properties will return the same object that
 * was passed into it.
 *
 * NOTE: This has been modified to use the resource bundle libraries from Jakarta/Struts
 * NOTE: Changed Back to use normal resource bundles as Struts doesnt seem to work under J2EE
 *
 * @author  PaulE
 * @version 1.0
 */
public class Config {
    private static Logger log = Logger.getLogger( Config.class.getName() );


    /////////////////////////////////////////////////////////////////////////////
    // Define all the supported system properties here...
    /////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////
    // Dynamic Properties (update setProperty() to support this method)

    /** Dynamic, Returns java.lang.String : The physical path of where the web route is. Set in the initialization servlet. Example "C:\tomcat\webapps\Tools" */
    public static final String PROP_WEB_SERVER_ROOT = "framework.WebServerRoot";

    /** Dynamic, Returns java.lang.String : The physical path of where the ApplicationResources.properties is. Set in the initialization servlet. Example "C:\tomcat\webapps\Tools\WEB-INF\classes\ApplicationResources.properties" */
    public static final String PROP_APPLICATION_RESOURCES_LOCATION = "framework.ApplicationResourcesLocation";

    /** Dynamic, Returns java.lang.String : The physical path of where the ApplicationResources.default is. Set in the initialization servlet. Example "C:\tomcat\webapps\Tools\WEB-INF\classes\ApplicationResources.default" */
    public static final String PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION = "framework.ApplicationResourcesDefaultLocation";

    /** Dynamic, Returns java.lang.String : The physical path of where the ApplicationResources.override is. Set in the initialization servlet. Example "C:\ApplicationResources.override" */
    public static final String PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION = "framework.ApplicationResourcesOverrideLocation";



    /////////////////////////////////////////////////////////////////////////////
    // Static Properties (should be defined and in-sync with the definition in the config.properties)

    /** Static : This the the name of a file in the web servers route directory. It is used to get the WEB_SERVER_ROOT. '/index.html' is often a good value to use */
    public static final String PROP_WEB_SERVER_ROOT_FILE = "framework.WebServerRootFile";
    /** Static : Log4j Configuration To Use. 'none', 'default' or specified xml config file */
    public static final String PROP_LOG4J_CONFIG = "framework.Log4JConfig";
    /** Static : Location in the Web Application Tree of the Components.xml file */
    public static final String PROP_COMPONENTS_FILE = "framework.ComponentsFile";
    /** Static : The configuration file to be used when the JDBCEngine provider is used */
    public static final String PROP_JDBC_ENGINE_INIT = "framework.persistence.jdbcengine.init";
    /** Static : The no. of tiers being used */
    public static final String PROP_TIER = "framework.middleware.Tier";
    /** Static : The default html fragment used for guarded-buttons */
    public static final String PROP_PRESENTATION_DEFAULT_GUARDED_HTML_LOCATION = "framework.presentation.defaultGuardedHtml.location";
    /** Static : The invalidate or not to invalidate a Session, when redirecting to the FinalUrl */
    public static final String PROP_SECURITY_PORTLET_INVALIDATE_SESSION_BEFORE_REDIRECTING_TO_FINAL_URL = "framework.security.portlet.invalidateSessionBeforeRedirectingToFinalUrl";

    /** Static : The URL location of the Role based security policy file */
    public static final String PROP_SECURITY_POLICY_URL = "framework.security.policy.url";
    /** Static : The URL location of the User based user grid settings file */
    public static final String PROP_USER_GRID_SETTINGS_URI = "framework.widgets.usergrid.user.url";
    /** Static : The URL location of the User based user grid settings file */
    public static final String PROP_DEFAULT_GRID_SETTINGS_URI = "framework.widgets.usergrid.default.url";
    /** Static : The URL location of the Menu list file */
    public static final String PROP_MENULIST_URL = "framework.menu.url";

    /** Static : The frequency at which the Component Garbage Collector is run. */
    public static final String PROP_PRESENTATION_COMPONENT_GARBAGE_COLLECTION_FREQUENCY_IN_MINUTES = "framework.presentation.componentGarbageCollection.frequencyInMinutes";
    /** Static : This value is used to determine when a component is idle and should be garbage collected. */
    public static final String PROP_PRESENTATION_COMPONENT_GARBAGE_COLLECTION_TIME_OUT_IN_MINUTES = "framework.presentation.componentGarbageCollection.timeOutInMinutes";

    /** Static : This property holds the url for the core-rules file used by the Dynamic Rules Engine. */
    public static final String PROP_RULES_ENGINE_CORE_RULES_URL = "framework.rules.core-rules.url";
    /** Static : This value is used to determine the directory in which the variations to the core-rules of the Dynamic-Rules-Engine are located. */
    public static final String PROP_RULES_ENGINE_VARIATIONS_DIR = "framework.rules.variations.directory";
    /** Static : This property holds the comma-separated list of the various validator.xml urls used by the Dynamic Rules Engine. */
    public static final String PROP_RULES_ENGINE_VALIDATORS_URL_LIST = "framework.rules.validators.url.list";

    /** Static : The values to be displayed in the 'MaxRecords' dropdown of the Finder/Lookup criteria screens. */
    public static final String PROP_FINDER_MAX_RECORDS_DROP_DOWN_OPTIONS = "framework.finder.maxRecordsDropDownOptions";

    /** Static : The TextTag uses this properties file to determine if a hyperlink to a Viewer component should be generated for a field. */
    public static final String PROP_DOMAIN_FIELD_VIEWER_COMPONENT_MAPPING_FILE = "framework.DomainFieldViewerComponentMappingFile";

    /** Static : The TextTag uses this properties file to determine the key-field of a Viewer component for which it has generated a hyperlink. */
    public static final String PROP_KEY_FIELD_PER_VIEWER_COMPONENT_FILE = "framework.KeyFieldPerViewerComponentFile";

    /** Static : The Jaffa exceptions typically contain error codes. Invoking the getLocalizedMessage() on a Jaffa exception will return an appropriate message by looking up the error-code in the specified ResourceBundle. No error is raised in case an invalid ResourceBundle is specified, or if no entry exists for the error-code. The ResourceBundle should refer to a properties file in the classpath. Eg. org/jaffa/resources/ApplicationResources */
    public static final String PROP_MESSAGE_RESOURCES_BUNDLE = "framework.messageResources.bundle";

    /////////////////////////////////////////////////////////////////////////////
    // Static data to hold all the properties
    /////////////////////////////////////////////////////////////////////////////
    private static final String CONFIG_RESOURCE = "org.jaffa.config.framework";
    private static HashMap m_staticProperties;

    /**
     * @associates Object
     */
    private static HashMap m_dynamicProperties = new HashMap();


    /** Set Property. This is only valid on properties classifed as 'dynamic'
     * If an invalid property is set, an logging error is raised, and the
     * set opertaion is ignored.
     *
     * Each time a new dynamic property is added, this method must be updated to
     * accept it.
     * @param key the property name.
     * @param value the property value.
     */
    public static void setProperty(String key, Object value) {
        if(key.equals(PROP_WEB_SERVER_ROOT)
        || key.equals(PROP_APPLICATION_RESOURCES_LOCATION)
        || key.equals(PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION)
        || key.equals(PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION)) {
            // Set the property
            m_dynamicProperties.put(key,value);
        } else {
            log.warn("Attempt to set property '" + key + "' failed. This is not a dynamic property." );
        }
    }

    /** Returns the value of a property.
     * @param key the name of the property.
     * @throws MissingResourceException if the property is not found
     * @return the value of a property.
     */
    public static Object getProperty(String key)
    throws MissingResourceException {

        // Look at the dynapic ones first
        if(m_dynamicProperties != null && m_dynamicProperties.containsKey(key)) {
            return m_dynamicProperties.get(key);
        }

        // Now look at the static ones...
        if(m_staticProperties == null)
            initResources();

      Object obj = m_staticProperties.get(key);
        //obj = m_staticProperties.getMessage(key);

        // If nothing was returned, see it it was an undefiend key...
        if(obj == null /*&& !m_staticProperties.isPresent(key)*/ ) {
            log.warn("Config Property '" + key + "' Requested, but not found!");
            throw new MissingResourceException("Not Found",CONFIG_RESOURCE,key);
        }

        if(obj != null)
            return obj.toString();
        else
            return null;
    }

    /** Get a property, can be dynamic or static. Allows for a default value to be returned if the property is not found,
     * or if the property has an empty/null value.
     * @param key the name of the property.
     * @param defValue this value will be returned in case the property has a null value.
     * @return Either a string (for a static property) or the appropriate object (for a dynamic one)
     */
    public static Object getProperty(String key, Object defValue) {
        try {
            Object obj = getProperty(key);
            if(obj == null)
                return defValue;
            else
                return obj;
        } catch (MissingResourceException e) {
            return defValue;
        }
    }

    /** Initialize the resources.
     * This looks for the configuration properties in package "org.jaffa.config" file "framework.properties"
     */
    private static void initResources() {
        if (m_staticProperties == null)
            try {
                m_staticProperties = new HashMap();
                PropertyResourceBundle staticPropertiesFromBundle = ( PropertyResourceBundle )PropertyResourceBundle.getBundle(CONFIG_RESOURCE);
                if(staticPropertiesFromBundle!=null){
                    Enumeration<String> keys = staticPropertiesFromBundle.getKeys();
                    while(keys.hasMoreElements()){
                        String key = keys.nextElement();
                        String value = staticPropertiesFromBundle.getString(key);
                        m_staticProperties.put(key, replaceTokens(value));
                    }
                }
                //m_staticProperties = MessageResources.getMessageResources(CONFIG_RESOURCE);

            } catch (MissingResourceException e) {
                log.fatal("Can't Find Config File : " + CONFIG_RESOURCE + ".properties", e);
                // This is a mission critical thing to not find !!
                throw new RuntimeException("Fatal Error: missing resource bundle: " + CONFIG_RESOURCE);
            }

    }

    private static String replaceTokens(String configValue) {
        //Regular expression to find ${word} tokens in the application rule value
        Pattern pt = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = pt.matcher(configValue);

        while (matcher.find()) {
            String tokenValue = getPropertyValue(matcher.group(1));
            if (tokenValue != null) {
                configValue = StringHelper.replace(configValue, matcher.group(0), tokenValue);
            }
        }
        return configValue;
    }

    private static String getPropertyValue(String sysProperty){
        String systemPropertyValue = System.getProperty(sysProperty);
        if (systemPropertyValue == null || "".equals(systemPropertyValue)) {
            return sysProperty;
        }
        return systemPropertyValue;
    }

}
