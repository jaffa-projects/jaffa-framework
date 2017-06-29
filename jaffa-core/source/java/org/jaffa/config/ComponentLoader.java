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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.componentdomain.Components;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.jaffa.util.XmlHelper;
import org.springframework.core.io.Resource;

/**
 * This Loader class scan through all jar!META-INF for components.xml and load
 * it in memory on container startup to use it in application
 */

public class ComponentLoader {

	private static final Logger log = Logger.getLogger(ComponentLoader.class);
	private Components m_components = null;
	private static final String SCHEMA = "org/jaffa/presentation/portlet/component/componentdomain/component-definitions_1_1.xsd";
	private static final String JAXB_CONTEXT = "org.jaffa.presentation.portlet.component.componentdomain";

	// error
	private static final String ERROR_COMP_READING = "Error in Reading Components Definition";

	/**
	 * singleton instance of the ComponentLoader
	 */
	private static ComponentLoader instance;

	public Components getComponents() {
		if (m_components == null) {
			loadComponent();
		}
		return m_components;
	}

	/**
	 * private constructor and it can be only instantiated via getInstance()
	 * method
	 */
	private ComponentLoader() {
		/**
		 * load components.xml from class jar!/META-INF
		 */
		loadComponent();
	}

	/**
	 * This method will return the instance of the ApplicationRulesLoader.
	 * 
	 * @return ApplicationRulesLoader
	 */
	public static ComponentLoader getInstance() {
		if (instance == null) {
			instance = new ComponentLoader();
			if (log.isDebugEnabled()) {
				log.debug("Singleton ComponentLoader Created");
			}
		}
		return instance;
	}

	/**
	 * This will load the components.
	 */
	private void loadComponent() {

		if (log.isDebugEnabled()) {
			log.debug("ApplicationRulesLoader::loadComponent");
		}

		OrderedPathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
		try {

			Resource[] resources = resolver.getResources("classpath*:META-INF/components.xml");
			if (resources != null) {
				m_components = new Components();
				for (Resource resource : resources) {

					if (resource == null) {
						continue;
					}

					try {
						// create a JAXBContext capable of handling classes
						// generated into the package
						JAXBContext jc = JAXBContext.newInstance(JAXB_CONTEXT);

						// create an Unmarshaller
						Unmarshaller u = jc.createUnmarshaller();

						// enable validation
						u.setSchema(JAXBHelper.createSchema(SCHEMA));

						Components comp = (Components) u
								.unmarshal(XmlHelper.stripDoctypeDeclaration(resource.getInputStream()));

						m_components.getComponent().addAll(comp.getComponent());

					} catch (Exception e) {
						String s = ERROR_COMP_READING.concat(" :") + resource.getURL();
						log.fatal(s, e);
						throw new SecurityException(s, e);
					}
				}
			} else {
				log.error(ERROR_COMP_READING);
				throw new SecurityException(ERROR_COMP_READING);
			}

		} catch (IOException e) {
			log.error(ERROR_COMP_READING, e);
			throw new SecurityException(ERROR_COMP_READING, e);
		}
	}
}
