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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;

/**
 * This class loads the
 * ApplicationResource.properties,ApplicationResource.override and
 * ApplicationResource_{language}_{country}.properties into memory on startup by
 * Servlet listener and will be used in PropertyMessageResources.loadLocale.
 * 
 */
public class ApplicationResourceLoader {

	private static final Logger log = Logger.getLogger(ApplicationResourceLoader.class);

	public static final String PROP_APPLICATION_RESOURCES_DEFAULT = "ApplicationResourcesDefault";
	public static final String PROP_APPLICATION_RESOURCES_DEFAULT_OVERRIDE = "ApplicationResourcesDefaultOverride";
	
	public static final String DEFAULT_PROP_LOCALE_KEY = "";
	
	public static final String FILE_PREFIX = "file:///";
	

	// Default Errors
	private static final String APP_RESOURCES_NOT_FOUND = "ApplicationResources.properties not found in jar!META-INF";
	private static final String ERROR_READING_APP_RESOURCES = "Error reading jar!META-INF/ApplicationResources.properties";

	// Override Errors
	private static final String APP_RESOURCES_OVERRIDE_NOT_FOUND = "ApplicationResources.override not found in jar!META-INF";
	private static final String ERROR_READING_APP_RESOURCES_OVERRIDE = "Error reading jar!META-INF/ApplicationResources.override";

	// Locale Errors
	private static final String APP_RESOURCES_LOCALE_NOT_FOUND = "ApplicationResource_{language}_{country}.properties not found in jar!META-INF";
	private static final String ERROR_READING_APP_RESOURCES_LOCALE = "Error reading jar!META-INF/ApplicationResource_{language}_{country}.properties";

	/**
	 * singleton instance of the ApplicationResourceLoader
	 */
	private static ApplicationResourceLoader instance;

	/**
	 * The collection of properties per locale.
	 */
	private Map<String, Properties> applicationResources = new HashMap<String, Properties>();

	/**
	 * This method gets the properties object based on input locale.
	 * 
	 * @param locale
	 * @return
	 */
	public Properties getLocaleProperties(String locale) {
		return applicationResources.get(locale);
	}

	/**
	 * This method gets the properties object for default application resources.
	 * 
	 * @return ApplicationResourceDefault
	 */
	public Properties getApplicationResourcesDefault() {
		return applicationResources.get(PROP_APPLICATION_RESOURCES_DEFAULT);
	}

	/**
	 * This method gets the properties object for override application resources
	 * .
	 * 
	 * @return ApplicationResourceOverride
	 */
	public Properties getApplicationResourcesOverride() {
		OrderedPathMatchingResourcePatternResolver resolver = new OrderedPathMatchingResourcePatternResolver();
		return loadOverrideResources(resolver);
	}

	/**
	 * This method gets the collection of properties for all available locale.
	 * 
	 * @return map
	 */
	public Map<String, Properties> getApplicationResources() {
		return applicationResources;
	}

	/**
	 * private constructor and it can be only instantiated via getInstance()
	 * method
	 */
	private ApplicationResourceLoader() {
		// load resources from class path/META-INF/Data Directory
		loadResources();
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

	/**
	 * Load all resources from properties files.
	 */
	private void loadResources() {
		OrderedPathMatchingResourcePatternResolver resolver = new OrderedPathMatchingResourcePatternResolver();
		loadDefaultResources(resolver);
		loadLocaleResources(resolver);
	}

	/**
	 * This method loads the default properties from
	 * jar!META-INF/ApplicationResources.properties.
	 *
	 * @param resolver
	 */
	private void loadDefaultResources(OrderedPathMatchingResourcePatternResolver resolver) {

		if (log.isDebugEnabled()) {
			log.debug("ApplicationResourceLoader::loadDefaultResources");
		}

		Properties defaultProperties = getDefaultResources(resolver);
		Properties overriddeProperties = loadOverrideResources(resolver);

		Properties appResourceProperties = new Properties();

		if (defaultProperties != null && defaultProperties.size() > 0) {
			appResourceProperties.putAll(defaultProperties);
		}

		if (overriddeProperties != null && overriddeProperties.size() > 0) {
			appResourceProperties.putAll(overriddeProperties);
		}

		if (appResourceProperties.size() > 0) {
			// loading the ApplicationResource.properties(default)
			applicationResources.put("", appResourceProperties);
		}
	}

	private Properties getDefaultResources(OrderedPathMatchingResourcePatternResolver resolver) {
		Properties properties = null;

		try {
			Resource[] resources = resolver.getResources("classpath*:META-INF/ApplicationResources.properties");
			if (resources != null) {
				for (Resource resource : resources) {
					if (properties == null) {
						properties = new Properties();
					}
					loadProperties(resource, properties);
				}
			} else {
				log.error(APP_RESOURCES_NOT_FOUND);
			}

			// Storing default resource in memory to use it in label editor
			if (properties != null && properties.size() > 0) {
				applicationResources.put(PROP_APPLICATION_RESOURCES_DEFAULT, properties);
			}

		} catch (IOException e) {
			log.error(ERROR_READING_APP_RESOURCES, e);
			throw new RuntimeException(ERROR_READING_APP_RESOURCES, e);
		}

		return properties;
	}

	/**
	 * This method load override properties from customer jar/blue print
	 * jar/data directory
	 * 
	 * @param resolver
	 * @param properties
	 */
	private Properties loadOverrideResources(OrderedPathMatchingResourcePatternResolver resolver) {
		if (log.isDebugEnabled()) {
			log.debug("ApplicationResourceLoader::loadOverrideResources");
		}
		Properties properties = null;
		try {
			// First load the override resource from customer jar/blue print jar
			/*
			 * TODO - Question: Are we going to move the override file under
			 * jar!META-INF or going to leave under resources folder in jar?
			 * Based on above question we need to consider changing the resource
			 * path.
			 * 
			 */
			//The default override from jar will not be loaded again if its loaded already.
			properties = applicationResources.get(PROP_APPLICATION_RESOURCES_DEFAULT_OVERRIDE);
			if (properties == null) {
				Resource[] resources = resolver.getResources("classpath*:META-INF/ApplicationResources.override");
				if (resources != null) {
					for (Resource resource : resources) {
						if (properties == null) {
							properties = new Properties();
						}
						loadProperties(resource, properties);
					}
				} else {
					log.error(APP_RESOURCES_OVERRIDE_NOT_FOUND);
				}
			}

			//Storing override resource in memory to use it in label editor
			if (properties != null && properties.size() > 0) {
				applicationResources.put(PROP_APPLICATION_RESOURCES_DEFAULT_OVERRIDE, properties);
			}			

			String applicationResourcesOverrideLocation = (String) Config
					.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);

			/*
			 * * TODO: PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION is not set
			 * in Config when this first invoked,so we need to make sure to set
			 * the override location before this point
			 */
			// Next load the override resource from customer data directory. Always reload the data directory override
			if (applicationResourcesOverrideLocation != null && !"".equals(applicationResourcesOverrideLocation)) {
				//added file prefix for ant style search pattern to search the file from I/O File
				Resource resource = resolver.getResource(FILE_PREFIX+applicationResourcesOverrideLocation);
				loadProperties(resource, properties);
			}

		} catch (IOException e) {
			log.error(ERROR_READING_APP_RESOURCES_OVERRIDE, e);
			throw new RuntimeException(ERROR_READING_APP_RESOURCES_OVERRIDE, e);
		}
		return properties;
	}

	/**
	 * This method loads the locale specific resources into memory based on
	 * derived/available locale from
	 * ApplicationResource_{language}_{country}_{variation}.properties
	 * 
	 * @param resolver
	 */
	private void loadLocaleResources(OrderedPathMatchingResourcePatternResolver resolver) {
		if (log.isDebugEnabled()) {
			log.debug("ApplicationResourceLoader::loadLocaleResources");
		}
		try {
			/*
			 * TODO - Are we going to move the locale properties file
			 * (ApplicationResource_{language}_{country}_{variation}.
			 * properties) under jar!META-INF or going to leave under resources
			 * folder in jar? Based on above question we need to consider
			 * changing the resource path.
			 */
			Resource[] resources = resolver.getResources("classpath*:META-INF/ApplicationResources_*.properties");
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					Properties localeProperties = new Properties();

					// derives locale key from resource file name(e.g. ar_OM,
					// ar_OM_JWL)
					String localeKey = resource.getFilename();
					if (localeKey != null && localeKey.indexOf("_") > 0) {
						localeKey = localeKey.substring(localeKey.indexOf("_") + 1);
					}
					if (localeKey != null && localeKey.indexOf(".") > 0) {
						localeKey = localeKey.substring(0, localeKey.indexOf("."));
					}

					loadProperties(resource, localeProperties);

					// loading the properties into memory per locale
					applicationResources.put(localeKey, localeProperties);
				}
			} else {
				log.error(APP_RESOURCES_LOCALE_NOT_FOUND);
			}
		} catch (IOException e) {
			log.error(ERROR_READING_APP_RESOURCES_OVERRIDE, e);
			throw new RuntimeException(ERROR_READING_APP_RESOURCES_LOCALE, e);
		}
	}

	private void loadProperties(Resource resource, Properties properties) throws IOException {
		if (resource != null) {
			if (log.isDebugEnabled()) {
				log.debug("Properties Resource Location: " + resource.getURL());
			}
			if (resource != null && resource.getInputStream() != null) {
				properties.load(resource.getInputStream());
			}
		}
	}
}
