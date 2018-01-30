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

import org.apache.log4j.Logger;
import org.jaffa.components.navigation.domain.GlobalMenu;
import org.jaffa.config.Config;
import org.jaffa.loader.navigation.NavigationManager;
import org.jaffa.security.VariationContext;
import org.jaffa.util.URLHelper;

import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/** This singleton class loads in the global navigation xml and caches it
 * to in can be used for constructing instances of NavAccessor's
 *
 * @author  PaulE
 * @version 1.0
 */
public class NavCache {

    private static final Logger log = Logger.getLogger(NavCache.class);
    private static final String DEFAULT_NAVIGATION_LOCATION = "classpath:///META-INF/navigation.xml";
    private static final ConcurrentMap<String, NavCache> c_navCacheByVariation = new ConcurrentHashMap<String, NavCache>();
    private static final String DEFAULT_KEY = ""; //this key will used to put the NavCache instance for the default navigation.xml file
    private GlobalMenu m_menu = null;

    /**
     * RoleManager - Returns the Navigation domain objects.  These are the objects created from the Navigation xsd.
     */
    private static NavigationManager navigationManager;


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

    /**
     *
     * @return NavCache
     */
    private static NavCache createNavCacheInstance() {
        return new NavCache();
    }

    /**
     *
     * @return NavCache
     */
    @Deprecated
    private static NavCache getDefaultInstance() {
        String variation = DEFAULT_KEY;
        NavCache navCache = c_navCacheByVariation.get(variation);
        if (navCache == null) {
            NavCache generatedNavCache = new NavCache();
            navCache = c_navCacheByVariation.putIfAbsent(variation, generatedNavCache);
            if (navCache == null)
                navCache = generatedNavCache;
        }
        return navCache;
    }

    /**
     *  NavCache - Constructor for NavCache and also the creator of the m_menu map.
     */
    private NavCache() {
        if (m_menu == null) {
            if (null != navigationManager) {
                m_menu = navigationManager.getGlobalMenu();
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
    @Deprecated
    public static String getDefaultFileLocation() {
        return (String) Config.getProperty(Config.PROP_MENULIST_URL, DEFAULT_NAVIGATION_LOCATION);
    }

    /**
     * Returns the location of navigation_{VAR}.xml file.
     * NOTE: This file may not exist in the file-system.
     */
    @Deprecated
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
    @Deprecated
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

    public static NavigationManager getNavigationManager() {
        return navigationManager;
    }

    public static void setNavigationManager(NavigationManager navigationManager) {
        NavCache.navigationManager = navigationManager;
    }
}
