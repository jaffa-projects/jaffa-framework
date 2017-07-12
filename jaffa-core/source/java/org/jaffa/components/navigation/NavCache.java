//@todo - add code for
//        <xsd:element name="MainLayout" 	        type="xsd:string" minOccurs="0" maxOccurs="1"/>
//        <xsd:element name="FinderLayout" 	type="xsd:string" minOccurs="0" maxOccurs="1"/>
//        <xsd:element name="FinderExcelLayout" 	type="xsd:string" minOccurs="0" maxOccurs="1"/>
//        <xsd:element name="FinderXmlLayout" 	type="xsd:string" minOccurs="0" maxOccurs="1"/>

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
package org.jaffa.components.navigation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.jaffa.components.navigation.domain.GlobalMenu;
import org.jaffa.config.Config;
import org.jaffa.security.VariationContext;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.jaffa.util.URLHelper;
import org.jaffa.util.XmlHelper;
import org.springframework.core.io.Resource;

/** This singleton class loads in the global navigation xml and caches it
 * to in can be used for constructing instances of NavAccessor's
 *
 * @author  PaulE
 * @version 1.0
 */
public class NavCache {

    private static final Logger log = Logger.getLogger(NavCache.class);
    private static final String DEFAULT_NAVIGATION_LOCATION = "classpath:///resources/navigation.xml";
    private static final String MODULE_NAVIGATION_LOCATION = "classpath*:META-INF/navigation.xml";
    private static final String SCHEMA = "org/jaffa/components/navigation/navigation_1_0.xsd";
    private static final ConcurrentMap<String, NavCache> c_navCacheByVariation = new ConcurrentHashMap<String, NavCache>();
    private static final String DEFAULT_KEY = ""; //this key will used to put the NavCache instance for the default navigation.xml file
    private GlobalMenu m_menu = null;

    /** Creates an instance of NavCache, if not already instantiated.
     * @return An instance of the NavCache.
     */
    public static NavCache getInstance() {
        String variation = VariationContext.getVariation();
        NavCache navCache = c_navCacheByVariation.get(variation);
        if (navCache == null) {
            NavCache generatedNavCache = createNavCacheInstance();
            navCache = c_navCacheByVariation.putIfAbsent(variation, generatedNavCache);
            if (navCache == null)
                navCache = generatedNavCache;
        }
        return navCache;
    }

    private static NavCache createNavCacheInstance() {
        String fileLocation = getFileLocation();
        return fileLocation.equals(getDefaultFileLocation()) ? getDefaultInstance() : new NavCache(fileLocation);
    }

    private static NavCache getDefaultInstance() {
        String variation = DEFAULT_KEY;
        NavCache navCache = c_navCacheByVariation.get(variation);
        if (navCache == null) {
            NavCache generatedNavCache = new NavCache(getDefaultFileLocation());
            navCache = c_navCacheByVariation.putIfAbsent(variation, generatedNavCache);
            if (navCache == null)
                navCache = generatedNavCache;
        }
        return navCache;
    }

    private NavCache(String initFile) {
        if (log.isDebugEnabled())
            log.debug("Creating an instance of NavCache using '" + initFile + '\'');
        InputStream stream = null;
        URL navUrl = null;

        try {
            try {
                // Create a URL for the resource file...
                navUrl = URLHelper.newExtendedURL(initFile);
            } catch (MalformedURLException e) {
                if(log.isDebugEnabled())
                    log.debug("Can't Find Navigation File from " + initFile + " Trying to find in classpath.");
            }
            if(navUrl!=null) {
                stream = navUrl.openStream();

                // create a JAXBContext capable of handling classes generated into the package
                JAXBContext jc = JAXBContext.newInstance("org.jaffa.components.navigation.domain");

                // create an Unmarshaller
                Unmarshaller u = jc.createUnmarshaller();

                // enable validation
                u.setSchema(JAXBHelper.createSchema(SCHEMA));

                // unmarshal a document into a tree of Java content objects composed of classes from the package.
                m_menu = (GlobalMenu) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
            }else{
                //try finding navigation from modules
                OrderedPathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
                try {

                    Resource[] resources = resolver.getResources(MODULE_NAVIGATION_LOCATION);
                    if (resources != null && resources.length == 1) {
                        Resource resource = resources[0];
                        // create a JAXBContext capable of handling classes generated into the package
                        JAXBContext jc = JAXBContext.newInstance("org.jaffa.components.navigation.domain");

                        // create an Unmarshaller
                        Unmarshaller u = jc.createUnmarshaller();

                        // enable validation
                        u.setSchema(JAXBHelper.createSchema(SCHEMA));

                        // unmarshal a document into a tree of Java content objects composed of classes from the package.
                        m_menu = (GlobalMenu) u.unmarshal(XmlHelper.stripDoctypeDeclaration(resource.getInputStream()));
                    }else{
                        throw new SecurityException("More than one navigation.xml found");
                    }
                } catch (IOException e) {
                    log.error("Error in Reading Policy", e);
                    throw new SecurityException("Error in Reading Policy", e);
                }
            }

        } catch (Exception e) {
            String str = "Error while parsing the Navigation file " + initFile;
            log.fatal(str, e);
            throw new SecurityException(str);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    public GlobalMenu getGlobalMenu() {
        return m_menu;
    }

    public static void clearCache() {
        c_navCacheByVariation.clear();
    }

    /** Returns the location of the default navigation.xml file. */
    public static String getDefaultFileLocation() {
        return (String) Config.getProperty(Config.PROP_MENULIST_URL, DEFAULT_NAVIGATION_LOCATION);
    }

    /**
     * Returns the location of navigation_{VAR}.xml file.
     * NOTE: This file may not exist in the file-system.
     */
    public static String getVariationFileLocation() {
        String defaultFileLocation = getDefaultFileLocation();

        //Incorporate the variation into the filename
        String variation = VariationContext.getVariation();
        String variationFileLocation = null;
        final String suffix = ".xml";
        if (defaultFileLocation.endsWith(suffix) && defaultFileLocation.length() > suffix.length())
            variationFileLocation = defaultFileLocation.substring(0, defaultFileLocation.length() - suffix.length()) + '_' + variation + suffix;
        else
            variationFileLocation = defaultFileLocation + '_' + variation;
        return variationFileLocation;
    }

    /**
     * Returns the variation file-location, if it exists in the file-system.
     * Else the default file-location will be returned.
     */
    public static String getFileLocation() {
        String variationFileLocation = getVariationFileLocation();
        try {
            URLHelper.newExtendedURL(variationFileLocation);
            if (log.isDebugEnabled())
                log.debug("FileLocation is '" + variationFileLocation + '\'');
            return variationFileLocation;
        } catch (MalformedURLException e) {
            String defaultFileLocation = getDefaultFileLocation();
            if (log.isDebugEnabled())
                log.debug("FileLocation is the default '" + defaultFileLocation + "', since variation file '" + variationFileLocation + "' does not exist");
            return defaultFileLocation;
        }
    }
}
