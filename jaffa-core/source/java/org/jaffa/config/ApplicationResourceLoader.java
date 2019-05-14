/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2014 JAFFA Development Group
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

import org.apache.log4j.Logger;
import org.jaffa.loader.config.ApplicationResourcesManager;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * ApplicationResourceLoader is the resource loader proxy class to get the resources
 * from repository and it also get the override resources from data directory
 */
public class ApplicationResourceLoader {

    private static final Logger log = Logger.getLogger(ApplicationResourceLoader.class);

    public static final String PROP_APPLICATION_RESOURCES_OVERRIDE = "ApplicationResourcesOverride";
    public static final String DATA_DIRECTORY = "data.directory";
    public static final String DEFAULT_PROP_LOCALE_KEY = "";
    public static final String FILE_PREFIX = "file:///";
    private static final String ERROR_READING_APP_RESOURCES_OVERRIDE = "Error reading :";

    /**
     * singleton instance of the ApplicationResourceLoader
     */
    private static ApplicationResourceLoader instance;

    private static ApplicationResourcesManager applicationResourcesManager;

    /**
     * private constructor and it can be only instantiated via getInstance()
     * method
     */
    public ApplicationResourceLoader() {
    }

    /**
     * This method will return the instance of the ApplicationResourceLoader.
     *
     * @return ApplicationResourceLoader
     */
    public static ApplicationResourceLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationResourceLoader();
            if (log.isDebugEnabled()) {
                log.debug("Singleton ApplicationResourceLoader Created");
            }
        }
        return instance;
    }

    public static ApplicationResourcesManager getApplicationResourcesManager() {
        return applicationResourcesManager;
    }

    public static void setApplicationResourcesManager(ApplicationResourcesManager applicationResourcesManager) {
        ApplicationResourceLoader.applicationResourcesManager = applicationResourcesManager;
    }


    /**
     * This method gets the properties object based on input locale.
     *
     * @param localeKey
     * @return
     */
    public Properties getLocaleProperties(String localeKey) {
        Properties properties = null;
        Properties overrideProperties = null;
        if (getApplicationResourcesManager()!=null
                && getApplicationResourcesManager().getApplicationResourcesLocaleRepository()!=null
                && getApplicationResourcesManager().getApplicationResourcesLocaleRepository().query(localeKey) != null) {
            //locale resources
            properties = getApplicationResourcesLocale(localeKey);
            overrideProperties = getApplicationResourcesOverride(localeKey);
        } else if (getApplicationResourcesManager()!=null){
            //default resources
            properties = getApplicationResourcesDefault();
            overrideProperties = getApplicationResourcesOverride(null);
        }

        if (null != overrideProperties) {
            properties.putAll(overrideProperties);
        }
        return properties;
    }

    /**
     * This method gets the properties object for default application resources.
     *
     * @return ApplicationResourceDefault
     */
    public Properties getApplicationResourcesDefault() {
        Properties properties = new Properties();
        //default resources
        Properties defaultProperties = getApplicationResourcesManager()!=null ? getApplicationResourcesManager().getApplicationResources() : null;
        if (null != defaultProperties) {
            properties.putAll(defaultProperties);
        }
        //Load the variation properties based on Current Variation Context.
        if (!VariationContext.NULL_VARIATION.equals(VariationContext.getVariation())) {
            Properties variationProperties = getApplicationResourcesManager().getApplicationResourcesVariation(VariationContext.getVariation());
            if (null != variationProperties) {
                properties.putAll(variationProperties);
            }

        }
        return properties;
    }

    /**
     * This method gets the properties object for locale application resources.
     *
     * @return getApplicationResourcesLocale
     */
    public Properties getApplicationResourcesLocale(String localeKey) {
        Properties properties = new Properties();
        //locale resources
        if (log.isDebugEnabled()) {
            log.debug("locale :" + localeKey);
        }
        Properties defaultProperties = getApplicationResourcesManager().getApplicationResourcesLocale(localeKey);
        if (null != defaultProperties) {
            properties.putAll(defaultProperties);
        }

        if (!VariationContext.NULL_VARIATION.equals(VariationContext.getVariation())) {
            Properties variationProperties = getApplicationResourcesManager().getApplicationResourcesLocaleVariation(localeKey, VariationContext.getVariation());
            if (null != variationProperties) {
                properties.putAll(variationProperties);
            }
        }
        return properties;
    }

    /**
     * This method gets the properties object for override application resources.
     *
     * @return ApplicationResourceOverride
     */
    public Properties getApplicationResourcesOverride(String localeKey) {
        if (log.isDebugEnabled()) {
            log.debug("ApplicationResourceLoader::loadOverrideResources");
        }
        OrderedPathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();

        Properties properties = new Properties();
        String dataDirectory = (String) ContextManagerFactory.instance().getProperty(DATA_DIRECTORY);
        String applicationResourcesOverrideLocation = null;
        try {
            if (dataDirectory != null && dataDirectory.length() > 0) {
                applicationResourcesOverrideLocation = dataDirectory + File.separator + PROP_APPLICATION_RESOURCES_OVERRIDE+(localeKey!=null?"_"+localeKey:"")+".properties";
                Config.setProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, applicationResourcesOverrideLocation);
            }

            if (applicationResourcesOverrideLocation != null && !"".equals(applicationResourcesOverrideLocation)) {
                //added file prefix for ant style search pattern to search the file from I/O File
                Resource resource = resolver.getResource(FILE_PREFIX + applicationResourcesOverrideLocation);
                if (resource != null) {
                    if (!resource.exists()) {
                        Path resourcePath = Paths.get(resource.getURI());
                        Files.createDirectories(resourcePath.getParent());
                        Files.createFile(resourcePath);
                    }
                    properties.load(resource.getInputStream());
                }
            }
        } catch (IOException e) {
            log.error(ERROR_READING_APP_RESOURCES_OVERRIDE + applicationResourcesOverrideLocation, e);
            throw new RuntimeException(ERROR_READING_APP_RESOURCES_OVERRIDE + applicationResourcesOverrideLocation, e);
        }
        return properties;
    }

}
