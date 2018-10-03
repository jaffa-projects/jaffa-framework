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

package org.jaffa.loader.soa;
import org.apache.log4j.Logger;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SoaEventConfigManager - SoaEventConfigManager is the managing class for all soaevent config properties as defined by the
 * soa-event-config.properties files.
 */
public class SoaEventConfigManager implements IManager{

    /** The key to locate the default, non-localized resources. */
    public static final String DEFAULT_PROPERTIES = "SoaEventDefaultProperties";

    /**
     * Instantiates a new Properties repository
     */
    private IRepository<String> soaEventConfigRepository = new MapRepository<>(DEFAULT_PROPERTIES);
    private static final Logger log = Logger.getLogger(SoaEventConfigManager.class);

    /**
     * The list of repositories managed by this class
     */
    private HashMap managedRepositories = new HashMap<String, IRepository>() {
        {
            put(soaEventConfigRepository.getName(), soaEventConfigRepository);
        }
    };

    /**
     * Provides the pattern to search for soaEventConfig
     */
    private static final String DEFAULT_PROPERTY_FILE_NAME = "soa-events-config.properties";


    /**
     * registerProperties - Registers an individual Properties object into the IRepository
     *
     * @param contextKey
     * @param property
     */
    public void registerProperties(ContextKey contextKey, String property) {
        getSoaEventConfigRepository().register(contextKey, property);
    }

    /**
     * unregisterProperties - Unregisters a Properties object
     *
     * @param contextKey
     */
    public void unregisterProperties(ContextKey contextKey) {
        getSoaEventConfigRepository().unregister(contextKey);
    }

    /**
     * getSoaEventConfigRepository - Returns the SoaEvent resource repository
     *
     * @return A repository of properties
     */
    public IRepository<String> getSoaEventConfigRepository() {
        return soaEventConfigRepository;
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
     * getSoaEventConfigRepository - Sets the soaEventConfig resources repository
     *
     * @param soaEventConfigRepository
     */
    public void getSoaEventConfigRepository(IRepository<String> soaEventConfigRepository) {
        this.soaEventConfigRepository = soaEventConfigRepository;
    }

    /**
     * getSoaEventConfig - Returns the properties as defined by the soaEventConfig.properties
     * file.
     *
     * @return soaEventConfig properties
     */
    public Properties getSoaEventConfig() {
        return getSoaEventConfigVariation(VariationContext.NULL_VARIATION);
    }

    /**
     * getSoaEventConfigVariation - Returns the properties defined by the variation as defined by the soaEventConfig.properties
     * file.
     * @param variation
     * @return soaEventConfig properties for the variation
     */
    public Properties getSoaEventConfigVariation(String variation) {
        IRepository<String> propertyRepository = getSoaEventConfigRepository();
        Properties properties = new Properties();
        if (null != propertyRepository) {
            Map<String, String> variationRepo = propertyRepository.getRepositoryByVariation(variation);
            properties.putAll(variationRepo);
        }
        return properties;
    }

    /**
     * getSoaEventConfigLocaleVariation - Returns the locale properties defined by the variation as defined by the soaEventConfig_*.properties
     * file.
     *
     * @param variation
     * @return soaEventConfig locale properties for the variation
     * @Param locale :id
     */
    public Properties getSoaEventConfig(String variation) {
        return getSoaEventConfigVariation(VariationContext.NULL_VARIATION);
    }

    /**
     * getSoaEventConfigVariation - Returns the properties defined by the variation as defined by the soaEventConfig.properties
     * file.
     *
     * @return soaEventConfig properties for the variation
     */
    public Properties getAllSoaEvents() {
        //Read Global SoaEvents
        Properties properties = getSoaEventConfig();
        //Load the variation properties based on Current Variation Context.
        if (!VariationContext.NULL_VARIATION.equals(VariationContext.getVariation())) {
            Properties variationProperties = getSoaEventConfigVariation(VariationContext.getVariation());
            if (variationProperties != null) {
                properties.putAll(variationProperties);
            }
        }
        return properties;
    }

    /**
     * registerResource - Provides a method which submits the contents of the resource file to the soaEventConfig resource
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

            if (log.isDebugEnabled()) {
                log.debug("Filename :" + resource.getFilename());
            }
            if (!properties.isEmpty()) {
                for (Object property : properties.keySet()) {
                    ContextKey key = new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                    registerProperties(key, properties.getProperty((String) property));
                }
            }
        }
    }

    /**
     * unregisterResource - Provides a method which removes the contents of the resource file to the soaEventConfig resource
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

            if (log.isDebugEnabled()) {
                log.debug("Filename :" + resource.getFilename());
            }
            if (!properties.isEmpty()) {
                for (Object property : properties.keySet()) {
                    ContextKey key = new ContextKey((String) property, resource.getURI().toString(), variation, precedence);
                    unregisterProperties(key);
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
