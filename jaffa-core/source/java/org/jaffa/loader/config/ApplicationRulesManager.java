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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.loader.config;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.security.VariationContext;
import org.jaffa.util.StringHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ApplicationRulesManager - ApplicationManager is the managing class for all application rules as defined by the
 * ApplicationRules.* files.
 */
public class ApplicationRulesManager implements IManager {

    /** The key to locate the resources. */
    public static final String APPLICATION_RULES_PROPERTIES = "Properties";


    /**
     * Instantiates a new Properties repository
     */
    private IRepository<String> applicationRulesRepository = new MapRepository<>("Properties");


    /**
     * The list of repositories managed by this class
     */
    private HashMap managedRepositories = new HashMap<String, IRepository>() {
        {
            put(applicationRulesRepository.getName(), applicationRulesRepository);
        }

    };

    /**
     * Provides the pattern to search for ApplicationRules
     */
    private static final String DEFAULT_PROPERTY_FILE_NAME = "ApplicationRules.properties";


    /**
     * registerProperties - Registers an individual Properties object into the IRepository
     *
     * @param contextKey
     * @param property
     */
    public void registerProperties(ContextKey contextKey, String property) {
        getApplicationRulesRepository().register(contextKey, property);
    }

    /**
     * unregisterProperties - Unregisters a Properties object
     *
     * @param contextKey
     */
    public void unregisterProperties(ContextKey contextKey) {
        getApplicationRulesRepository().unregister(contextKey);
    }

    /**
     * getLocalePropertiesRepository - Returns the application rules repository
     *
     * @return A repository of properties
     */
    public IRepository<String> getApplicationRulesRepository() {
        return applicationRulesRepository;
    }

    /**
     * getRepositoryNames - Retrieve a String list of all the IRepositories managed by this IManager
     * @return A list of repository names managed by this manager
     */
    @Override
    public Set getRepositoryNames() {
        return managedRepositories.keySet();
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or empty if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        return (IRepository<?>) managedRepositories.get(name);
    }

    /**
     * registerProperties() - Registers each property from a provided ContextKey for repository access
     * @param mapRepository The repository to register the properties to
     * @param key   The ContextKey corresponding to the property values
     * @param properties    The Properties object containing property key/value pairs
     */

    private void registerProperties(MapRepository<String> mapRepository, ContextKey key, Properties properties) {
        Iterator<String> contextKeyPropertiesIterator = properties.stringPropertyNames().iterator();
        while (contextKeyPropertiesIterator.hasNext()) {
            String propertyKey = contextKeyPropertiesIterator.next();
            String propertyValue = properties.getProperty(propertyKey);
            mapRepository.register(
                    new ContextKey(propertyKey, key.getFileName(), key.getVariation(), key.getPrecedence()), propertyValue);
        }
    }

    /**
     * setLocalePropertiesRepository - Sets the application rules repository
     *
     * @param applicationRulesRepository
     */
    public void setApplicationRulesRepository(IRepository<String> applicationRulesRepository) {
        this.applicationRulesRepository = applicationRulesRepository;
    }

    /**
     * getApplicationRulesGlobal - Returns the global properties as defined by the ApplicationRules.global properties
     * file.
     *
     * @return ApplicationRules_global properties
     */
    public Properties getApplicationRulesGlobal() {
        return getApplicationRulesVariation(VariationContext.NULL_VARIATION);
    }

    /**
     * getApplicationRulesVariation - Returns the properties defined by the variation as defined by the ApplicationRules.*
     * file.
     *
     * @param variation
     * @return ApplicationRules_{variation} properties
     */
    public Properties getApplicationRulesVariation(String variation) {
        IRepository<String> propertyRepository = getApplicationRulesRepository();
        Properties properties = new Properties();
        if (null != propertyRepository) {
            Map<String, String> variationRepo = propertyRepository.getRepositoryByVariation(variation);
            properties.putAll(variationRepo);
        }
        return properties;
    }

    /**
     * registerResource - Provides a method which submits the contents of the resource file to the application rules
     * repository.
     *
     * @param resource   the object that contains the xml config file.
     * @param precedence associated with the module based on its definition in manifest
     * @param variation  associated with the module based on its definition in manifest
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException {
        if (resource != null) {
            InputStream resourceInputStream = resource.getInputStream();
            if (resourceInputStream != null) {
                Properties properties = new Properties();
                loadPropertiesResource(resourceInputStream, properties);
                if (!properties.isEmpty()) {
                    for (Object property : properties.keySet()) {
                        ContextKey key =
                                new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                        registerProperties(key, properties.getProperty((String) property));
                    }
                }
                resourceInputStream.close();
            }
        }
    }


    /**
     * unregisterResource - Provides a method which unregisters a given properties resource.
     *
     * @param resource   the object that contains the xml config file.
     * @param precedence associated with the module based on its definition in manifest
     * @param variation  associated with the module based on its definition in manifest
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void unregisterResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException {
        if (resource != null) {
            InputStream resourceInputStream = resource.getInputStream();
            if (resourceInputStream != null) {
                Properties properties = new Properties();
                loadPropertiesResource(resourceInputStream, properties);
                if (!properties.isEmpty()) {
                    for (Object property : properties.keySet()) {
                        ContextKey key =
                                new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                        unregisterProperties(key);
                    }
                }
                resourceInputStream.close();
            }
        }
    }

  /**
   * loadPropertiesResouce - Load the properties from provided resource file
   * @param resourceInputStream The input data from the provided resource file
   * @param properties  The properties object to inject properties into
   * @throws IOException    When a file cannot be accessed or operations cannot be performed on it
   */
    private void loadPropertiesResource(InputStream resourceInputStream, Properties properties) throws IOException {
        properties.load(resourceInputStream);
        for (Object property : properties.keySet()) {
            String systemPropertyValue = System.getProperty((String) property);
            if (systemPropertyValue == null) {
                systemPropertyValue = replaceTokens(properties, properties.getProperty((String) property));
            }
            properties.setProperty((String) property, systemPropertyValue);
        }
    }

    /**
     * getResourceFileName - Provides the file name of the resource file.
     *
     * @return  The resource file name
     */
    @Override
    public String getResourceFileName() {
        return DEFAULT_PROPERTY_FILE_NAME;
    }

    private String replaceTokens(Properties properties, String appRuleValue) {
        //Regular expression to find ${word} tokens in the application rule value
        Pattern pt = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = pt.matcher(appRuleValue);

        while (matcher.find()) {
            String tokenValue;
            if (matcher.group(1)!=null && (matcher.group(1).startsWith("env.") || matcher.group(1).startsWith("ENV."))
                    && matcher.group(1).length() > 4) {
                tokenValue = System.getenv(matcher.group(1).substring(4));
                //If no environment variable is defined, remove the apprule value which is ${env.ENV_VAR}
                if(tokenValue == null){
                    tokenValue = "";
                }
            }else {
                tokenValue = getPropertyValue(properties, matcher.group(1));
            }
            if (tokenValue != null) {
                appRuleValue = StringHelper.replace(appRuleValue, matcher.group(0), tokenValue);
                appRuleValue = replaceTokens(properties, appRuleValue);
            }
        }
        return appRuleValue;
    }

  /**
   * getPropertyValue - The value of a property in a Properties object
   * @param properties  The Properties object to search
   * @param key The key corresponding to the queried value
   * @return    The system property value found in a properties object, or null if the
   * property does not exist.
   */
    private String getPropertyValue(Properties properties, String key){
        String systemPropertyValue = System.getProperty(key);
        if (systemPropertyValue == null) {
            systemPropertyValue = properties.getProperty(key);
        }
        return systemPropertyValue;
    }

    /**
     * Returns ApplicationRules Map for the user's context
     * @return
     */
    public ConcurrentMap<String, String> getMyApplicationRules(){
        return applicationRulesRepository.getMyRepository();
    }
}
