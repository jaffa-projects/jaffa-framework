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
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
 * Loader.java
 *
 * Created on April 17, 2002, 11:53 AM
 */
package org.jaffa.presentation.portlet.component.componentdomain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.*;
import org.jaffa.config.ComponentLoader;
import org.jaffa.config.Config;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.ComponentDefinitionException;
import org.jaffa.presentation.portlet.component.componentdomain.Component;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.jaffa.util.XmlHelper;

/** This class is used to load the domain information from the Domain Objects based
 * on the XML data, into definition objects that can be used by the rest of the architecture
 *
 * @author  paule
 * @version 1.0
 */
public class Loader {

    private static final Logger LOGGER = Logger.getLogger(Loader.class);
    // If no property is specified in the framework.properties file, this is where the system
    // will look for the components.xml file.
    private static final String DEFAULT_COMPONENTS_LOCATION = "classpath:///resources/components.xml";
    private static final String SCHEMA = "org/jaffa/presentation/portlet/component/componentdomain/component-definitions_1_1.xsd";
    private static Map<String, ComponentDefinition> c_componentPool = null;

    /** Read in the xml component definitions. Used by the component manager to aquire
     * all the component definitions. This abstracts the component manager from dealing with
     * the specifics of where the definitions are held and in what format.
     * @return Returns a Map where the key is the component name, and the value is a ComponentDefinition object
     */
    public static Map<String, ComponentDefinition> getComponentPool() {
        if (c_componentPool == null)
            buildComponentPool();
        return c_componentPool;
    }

	/**
	 * Read in the xml component definitions. Used by the component manager to
	 * aquire all the component definitions. This abstracts the component
	 * manager from dealing with the specifics of where the definitions are held
	 * and in what format.
	 * 
	 * @return Returns a Map where the key is the component name, and the value
	 *         is a ComponentDefinition object
	 */
	private static synchronized void buildComponentPool() {
		if (c_componentPool == null) {
			final Components compList = readComponents();
			final Map<String, ComponentDefinition> pool = new HashMap<String, ComponentDefinition>();

			// Now go through the component list and build a Map of Component
			// Definitions
			for (final Component c : compList.getComponent()) {
				ComponentDefinition cd;
				try {
					cd = new ComponentDefinition(c);
					// Add it to the pool.
					pool.put(cd.getComponentName(), cd);
				} catch (Exception e) {
					LOGGER.fatal(e);
				}
			}
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Loaded Component Definitions - Total=" + pool.size());
			c_componentPool = pool;
		}
	}

    /** Reads the componentx.xml file.
     * @return the Object representation of components.xml file.
     */
    private static Components readComponents() {
        String name = (String) Config.getProperty(Config.PROP_COMPONENTS_FILE, DEFAULT_COMPONENTS_LOCATION);
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Loading in the Components Definition File - " + name);
        InputStream stream = null;
        Components components = null;
        try {
			try {
				final URL xmlFile = URLHelper.newExtendedURL(name);

				stream = xmlFile.openStream();
			} catch (MalformedURLException e) {
				String s = "Can't Find Components Definition File. Bad URL - " + name;
				LOGGER.error(s, e);
			} catch(FileNotFoundException e){
				String s = "Can't Find Components Definition File. Bad URL - " + name;
				LOGGER.error(s, e);
			}
			
        	if(stream != null ){
				// create a JAXBContext capable of handling classes generated into the package
				JAXBContext jc = JAXBContext.newInstance("org.jaffa.presentation.portlet.component.componentdomain");

				// create an Unmarshaller
				Unmarshaller u = jc.createUnmarshaller();

				// enable validation
				u.setSchema(JAXBHelper.createSchema(SCHEMA));

				// unmarshal a document into a tree of Java content objects composed of classes from the package.
				components = (Components) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
        	}else {
        		//Load components from modules
				components = ComponentLoader.getInstance().getComponents();
			}
			return components;
        } catch (Exception e) {
            String s = "Error in Reading Components Definition File - " + name;
            LOGGER.fatal(s, e);
            throw new SecurityException(s, e);
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                // do nothing
                LOGGER.warn("Unable to close the InputStream associated to the URL " + name, e);
            }
        }
    }
}
