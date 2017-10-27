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

package org.jaffa.loader.navigation;

import org.apache.log4j.Logger;
import org.jaffa.components.navigation.domain.GlobalMenu;
import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * NavigationManager - Handles the management of the GlobalMenu read from the navigation.xml file
 */
public class NavigationManager implements IManager {

    /**
     *  The name of the configuration file that this manager will handle.
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "navigation.xml";

    /**
     * The default location for the Jaffa navigation manager
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/components/navigation/navigation_1_0.xsd";

    /**
     * Defines the name of the globalMenu since it has no identifier.
     */
    private static final String GLOBAL_MENU_ID = "globalMenu";

    /**
     * Return the navigation map repository.
     */
    private IRepository<GlobalMenu> navigationRepository = new MapRepository<>();

    /**
     * The list of repositories managed by this class
     */
    private IRepository<?>[] managedRepositories = new IRepository<?>[] {navigationRepository};

    private static final Logger log = Logger.getLogger(NavigationManager.class);

    /**
     * registerXML - Registers the navigation global menu into the IRepository
     * @param resource the object that contains the xml config file.
     * @param precedence associated with the module based on its definition in manifest
     * @param variation associated with the module based on its definition in manifest
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerResource(Resource resource, String precedence, String variation) throws JAXBException, SAXException, IOException {
        GlobalMenu globalMenu = JAXBHelper.unmarshalConfigFile(GlobalMenu.class, resource, CONFIGURATION_SCHEMA_FILE);
        if (globalMenu != null) {
            ContextKey key = new ContextKey(GLOBAL_MENU_ID, resource.getURI().toString(), variation, precedence);
            registerGlobalMenu(key, globalMenu);
        }
    }

    /**
     * registerGlobalMenu - Registers an individual GlobalMenu object into the IRepository
     * @param contextKey
     * @param globalMenu
     */
    public void registerGlobalMenu(ContextKey contextKey, GlobalMenu globalMenu) {
        getNavigationRepository().register(contextKey, globalMenu);
    }

    /**
     * unregisterGlobalMenu - Unregisters a globalMenu object
     * @param contextKey
     */
    public void unregisterGlobalMenu(ContextKey contextKey) {
        getNavigationRepository().unregister(contextKey);
    }

    /**
     * getXmlFileName - Returns the default file name
     * @return default configuration file
     */
    @Override
    public String getResourceFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    /**
     * getGlobalMenu - Returns an instance of the GlobalMenu
     * @return
     */
    public GlobalMenu getGlobalMenu() {
        return getNavigationRepository().query(GLOBAL_MENU_ID);
    }


    /**
     * getNavigationRepository - Returns the navigation repository
     * @return
     */
    public IRepository<GlobalMenu> getNavigationRepository() {
        return navigationRepository;
    }


    /**
     * getRepositoryNames - Retrieve a String list of all the IRepositories managed by this IManager
     * @return A list of repository names managed by this manager
     */
    @Override
    public List<String> getRepositoryNames() {
        List<String> repositoryNames = new ArrayList<>();
        for (IRepository<?> repository : managedRepositories) {
            repositoryNames.add(repository.getName());
        }
        return repositoryNames;
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or null if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        IRepository<?> matchingRepository = null;
        for (IRepository<?> repository : managedRepositories) {
            if (name.equals(repository.getName())) {
                matchingRepository = repository;
            }
        }
        if (matchingRepository == null) {
            matchingRepository = new MapRepository<>();
        }
        return matchingRepository;
    }

    /**
     * setNavigationRepository - Sets the Navigation repository
     * @param navigationRepository
     */
    public void setNavigationRepository(IRepository<GlobalMenu> navigationRepository) {
        this.navigationRepository = navigationRepository;
    }
}
