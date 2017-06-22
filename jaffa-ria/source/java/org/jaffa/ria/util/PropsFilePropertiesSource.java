/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
package org.jaffa.ria.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * <p>
 * This extended class reads the jawr.properties files from META-INF from
 * jar/classpath.
 * 
 * Sample Servlet mapping to use this extended PropsFilePropertiesSource and with default jawr.properties.
 *
 * 
 * <servlet>
 *   <servlet-name>JavascriptServlet</servlet-name>
 *   <servlet-class>net.jawr.web.servlet.JawrServlet</servlet-class>
 *   <init-param>
 *     <param-name>configLocation</param-name>
 *     <param-value>resources/jawr.properties</param-value>
 *   </init-param>
 *   <init-param>
 *     <param-name>configPropertiesSourceClass</param-name>
 *     <param-value>org.jaffa.ria.util.PropsFilePropertiesSource</param-value>
 *   </init-param>    
 *   <init-param>
 *     <param-name>mapping</param-name>
 *     <param-value>/jsJawrPath/</param-value>
 *   </init-param>
 *   <load-on-startup>3</load-on-startup>
 * </servlet>
 * <p>
 */
public class PropsFilePropertiesSource extends net.jawr.web.resource.bundle.factory.util.PropsFilePropertiesSource {

	private static final Logger log = Logger.getLogger(PropsFilePropertiesSource.class);
	private static final String JAWR_PROPS_NOT_FOUND = "jawr configuration could not be found at JAR!META-INF/jawr.properties";
	private static final String NO_JAWR_PROPS = "jawr configuration could not be found either in default location or in JAR!META-INF/jawr.properties";
	private String defaultConfigLocation;
	
	/**
	 * This overridden method reads the jawr.properties from
	 * Jar!META-INF/jawr.properties.
	 */
	@Override
	protected Properties doReadConfig() {
		if (log.isDebugEnabled()) {
			log.debug("PropsFilePropertiesSource::doReadConfig");
		}

		Properties properties;
		/**
		 * Call super class doReadConfig If there is default configLocation
		 * location specified in Servlet Init Params
		 */
		if (defaultConfigLocation != null) {
			properties = super.doReadConfig();
		} else {
			properties = new Properties();
		}

		try {
			PathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
			Resource[] resources = resolver.getResources("classpath*:META-INF/jawr.properties");
			if (resources != null) {
				for (Resource resource : resources) {
					if (log.isDebugEnabled()) {
						log.debug("Properties Resource Location: " + resource.getURL());
					}
					if (resource != null && resource.getInputStream() != null) {
						properties.load(resource.getInputStream());
					}
				}
			} else {
				throw new RuntimeException(JAWR_PROPS_NOT_FOUND);
			}
		} catch (IOException e) {
			throw new RuntimeException(JAWR_PROPS_NOT_FOUND);
		}

		if (properties.size() == 0) {
			throw new RuntimeException(NO_JAWR_PROPS);
		}
		return properties;
	}

	/**
	 * Overridden to see whether configLocation is given in Servlet Init Params.
	 */
	protected Properties readConfigFile(String path) {
		defaultConfigLocation = path;
		return super.readConfigFile(path);
	}
}
