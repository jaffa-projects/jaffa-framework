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
 * ApplicationRules_global.properties,ApplicationRules_(variation).properties
 * into memory on container startup.
 * 
 * <p>
 * The cache looks like below given e.g.
 * 
 * {key} | {value} 
 * ------------------- 
 * {global} | {properties of ApplicationRules_global} 
 * {WOL}    | {properties of ApplicationRules_WOL}
 * 
 * 
 */
public class ApplicationRulesLoader {

	private static final Logger log = Logger.getLogger(ApplicationRulesLoader.class);

	private static final String APP_RULE_GLOBAL = "global";

	private static final String APP_RULE_NOT_FOUND = "ApplicationRules_*.properties not found in jar!META-INF";
	private static final String ERROR_READING_APP_RULES = "Error reading jar!META-INF/ApplicationRules_*.properties";

	/**
	 * singleton instance of the ApplicationResourceLoader
	 */
	private static ApplicationRulesLoader instance;

	/**
	 * The cache per application rules. 
	 */
	private Map<String, Properties> applicationRules = new HashMap<String, Properties>();

	/**
	 * private constructor and it can be only instantiated via getInstance()
	 * method
	 */
	private ApplicationRulesLoader() {
		/**
		 * load resources from class jar!/META-INF
		 * 
		 * @TODO - Needs to think about adding the load function to load the
		 *       rules from Data Directory for Rules Editor
		 */
		loadApplicationRules();
	}

	/**
	 * This method will return the instance of the ApplicationRulesLoader.
	 * 
	 * @return ApplicationRulesLoader
	 */
	public static ApplicationRulesLoader getInstance() {
		if (instance == null) {
			instance = new ApplicationRulesLoader();
			if (log.isDebugEnabled()) {
				log.debug("Singleton ApplicationRulesLoader Created");
			}
		}
		return instance;
	}

	/**
	 * @return ApplicationRules_global properties
	 */
	public Properties getApplicationRulesGlobal() {
		return applicationRules.get(APP_RULE_GLOBAL);
	}

	/**
	 * 
	 * @param variation
	 * @return ApplicationRules_{variation} properties
	 */
	public Properties getApplicationRulesVariation(String variation) {
		return applicationRules.get(variation);
	}

	/**
	 * This will load the ApplicationRules files for global and user variation
	 * 
	 */
	private void loadApplicationRules() {

		if (log.isDebugEnabled()) {
			log.debug("ApplicationRulesLoader::loadApplicationRules");
		}

		OrderedPathMatchingResourcePatternResolver resolver = new OrderedPathMatchingResourcePatternResolver();
		try {

			Resource[] resources = resolver.getResources("classpath*:META-INF/ApplicationRules_*.properties");
			if (resources != null) {
				for (Resource resource : resources) {

					if (resource == null) {
						continue;
					}

					Properties properties = new Properties();

					// derives variation from resource file name(e.g. global,WOL,BIM)
					String ruleVariation = resource.getFilename();

					if (ruleVariation != null && ruleVariation.indexOf("_") > 0) {
						ruleVariation = ruleVariation.substring(ruleVariation.indexOf("_") + 1);
					}
					if (ruleVariation != null && ruleVariation.indexOf(".") > 0) {
						ruleVariation = ruleVariation.substring(0, ruleVariation.indexOf("."));
					}

					loadProperties(resource, properties);

					if (properties != null && properties.size() > 0) {
						applicationRules.put(ruleVariation, properties);
					}
				}
			} else {
				log.error(APP_RULE_NOT_FOUND);
				throw new RuntimeException(APP_RULE_NOT_FOUND);
			}

		} catch (IOException e) {
			log.error(ERROR_READING_APP_RULES, e);
			throw new RuntimeException(ERROR_READING_APP_RULES, e);
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
