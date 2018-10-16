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

import org.apache.log4j.Logger;
import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.security.VariationContext;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * ApplicationResourcesManager - ApplicationResourcesManager is the
 * managing class for all application resources(lables) as defined by the
 * ApplicationResource.properties files.
 */
public class ApplicationResourcesManager implements IManager {

    /** The key to locate the default, non-localized resources. */
    public static final String DEFAULT_PROPERTIES = "DefaultProperties";

    /** The key to locate the specialized, localized resources. */
    public static final String LOCALE_PROPERTIES = "LocaleProperties";

    /** The key to locate the combined resources - Default properties,
     * potentially overwritten by locale properties. */
    public static final String APPLICATION_RESOURCES_PROPERTIES = "ApplicationResources";

    /**
     * Instantiates a new Properties repository
     */
    private IRepository<String> applicationResourcesRepository =
            new MapRepository<>(DEFAULT_PROPERTIES);
    private IRepository<Properties> applicationResourcesLocaleRepository =
            new MapRepository<>(LOCALE_PROPERTIES);
    private static final Logger log = Logger.getLogger(ApplicationResourcesManager.class);

    /**
     * The list of repositories managed by this class
     */
    private HashMap managedRepositories = new HashMap<String, IRepository>() {
        {
            put(applicationResourcesRepository.getName(), applicationResourcesRepository);
            put(applicationResourcesLocaleRepository.getName(), applicationResourcesLocaleRepository);
            // TODO decide whether the two below is a good idea.  This could be confusing.
            // "ApplicationResources" may need to be the default
            // resources, perhaps overwritten by some other (locale) resources.  Of course,
            // the methods can arbitrarily manipulate data from anywhere.
            put(APPLICATION_RESOURCES_PROPERTIES, applicationResourcesRepository);
        }
    };

    /**
     * Provides the pattern to search for ApplicationResources
     */
    private static final String DEFAULT_PROPERTY_FILE_NAME = "ApplicationResources*.properties";


    /**
     * registerProperties - Registers an individual Properties object into the IRepository
     *
     * @param contextKey
     * @param property
     */
    public void registerProperties(ContextKey contextKey, String property) {
        getApplicationResourcesRepository().register(contextKey, property);
    }

    /**
     * unregisterProperties - Unregisters a Properties object
     *
     * @param contextKey
     */
    public void unregisterProperties(ContextKey contextKey) {
        getApplicationResourcesRepository().unregister(contextKey);
    }

    /**
     * registerLocaleProperties - Registers an individual Locale Properties object into the IRepository
     *
     * @param contextKey
     * @param properties
     */
    public void registerLocaleProperties(ContextKey contextKey, Properties properties) {
        getApplicationResourcesLocaleRepository().register(contextKey, properties);
    }

    /**
     * unregisterLocaleProperties - Unregisters a Locale Properties object
     *
     * @param contextKey
     */
    public void unregisterLocaleProperties(ContextKey contextKey) {
        getApplicationResourcesLocaleRepository().unregister(contextKey);
    }


    /**
     * getApplicationResourceRepository - Returns the application resource repository
     *
     * @return A repository of properties
     */
    public IRepository<String> getApplicationResourcesRepository() {
        return applicationResourcesRepository;
    }

    /**
     * getApplicationResourceLocaleRepository - Returns the application locale resource repository
     *
     * @return A repository of properties
     */
    public IRepository<Properties> getApplicationResourcesLocaleRepository() {
        return applicationResourcesLocaleRepository;
    }

    /**
     * getRepositoryNames - Retrieve a String list of all the IRepositories managed by this IManager
     *
     * @return A list of repository names managed by this manager
     */
    @Override
    public Set getRepositoryNames() {
        return managedRepositories.keySet();
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     *
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or empty if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        return (IRepository<?>) managedRepositories.get(name);
    }

    /**
     * setApplicationResourcesRepository - Sets the application resources repository
     *
     * @param applicationResourcesRepository
     */
    public void setApplicationResourcesRepository(IRepository<String> applicationResourcesRepository) {
        this.applicationResourcesRepository = applicationResourcesRepository;
    }

    /**
     * getApplicationResources - Returns the properties as defined by the ApplicationResources.properties
     * file.
     *
     * @return ApplicationResources properties
     */
    public Properties getApplicationResources() {
        return getApplicationResourcesVariation(VariationContext.NULL_VARIATION);
    }

    public Properties getApplicationResourcesLocale(String locale) {
        return getApplicationResourcesLocaleVariation(locale, VariationContext.NULL_VARIATION);
    }

    /**
     * getApplicationResourcesVariation - Returns the properties defined by the variation as defined by the ApplicationResources.properties
     * file.
     *
     * @param variation
     * @return ApplicationResources properties for the variation
     */
    public Properties getApplicationResourcesVariation(String variation) {
        IRepository<String> propertyRepository = getApplicationResourcesRepository();
        Properties properties = new Properties();
        if (null != propertyRepository) {
            Map<String, String> variationRepo = propertyRepository.getRepositoryByVariation(variation);
            properties.putAll(variationRepo);
        }
        return properties;
    }

    /**
     * getApplicationResourcesLocaleVariation - Returns the locale properties defined by the variation as defined by the ApplicationResources_*.properties
     * file.
     *
     * @param variation
     * @return ApplicationResources locale properties for the variation
     * @Param locale :id
     */
    public Properties getApplicationResourcesLocaleVariation(String locale, String variation) {
        return getApplicationResourcesLocaleRepository().queryByVariation(locale, variation);
    }

    /**
     * registerResource - Provides a method which submits the contents of the resource file to the application resource
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

        if (resource != null && resource.getInputStream() != null) {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            boolean isLocaleResource = Boolean.FALSE;
            String locale = null;
            if (log.isDebugEnabled()) {
                log.debug("Filename :" + resource.getFilename());
            }
            if ((resource.getFilename().indexOf("_") > 0)) {
                locale = resource.getFilename().substring(resource.getFilename().indexOf("_") + 1, resource.getFilename().lastIndexOf("."));
                isLocaleResource = Boolean.TRUE;
            }

            if (!properties.isEmpty()) {
                if (isLocaleResource) {
                    //locale resources
                    ContextKey key = new ContextKey(locale+"_"+variation, resource.getURI().toString(), variation, precedence);
                    registerLocaleProperties(key, properties);
                } else {
                    //default resource
                    for (Object property : properties.keySet()) {
                        ContextKey key = new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                        registerProperties(key, properties.getProperty((String) property));
                    }
                }
            }
        }
    }

    /**
     * unregisterResource - Provides a method which removes the contents of the resource file to the application resource
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
    public void unregisterResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException {

        if (resource != null && resource.getInputStream() != null) {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            boolean isLocaleResource = Boolean.FALSE;
            String locale = null;
            if (log.isDebugEnabled()) {
                log.debug("Filename :" + resource.getFilename());
            }
            if ((resource.getFilename().indexOf("_") > 0)) {
                locale = resource.getFilename().substring(resource.getFilename().indexOf("_") + 1, resource.getFilename().lastIndexOf("."));
                isLocaleResource = Boolean.TRUE;
            }

            if (!properties.isEmpty()) {
                if (isLocaleResource) {
                    //locale resources
                    ContextKey key = new ContextKey(locale+"_"+variation, resource.getURI().toString(), variation, precedence);
                    unregisterLocaleProperties(key);
                } else {
                    //default resource
                    for (Object property : properties.keySet()) {
                        ContextKey key = new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                        unregisterProperties(key);
                    }
                }
            }
        }
    }




    /**
     * getResourceFileName - Provides the file name of the resource file.
     *
     * @return
     */
    @Override
    public String getResourceFileName() {
        return DEFAULT_PROPERTY_FILE_NAME;
    }
}
