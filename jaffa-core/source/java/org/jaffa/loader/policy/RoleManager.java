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

package org.jaffa.loader.policy;

import org.apache.log4j.Logger;
import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.security.PolicyCache;
import org.jaffa.security.securityrolesdomain.GrantFunctionAccess;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.security.securityrolesdomain.Roles;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *   RoleManager - The RoleManager is used to handle security role creates, removes, updates, and deletes from the
 *   role manager repository.
 */
public class RoleManager implements IManager {

    /**
     * The name of the configuration file which this class handles.
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "roles.xml";

    /**
     * The default location for the jaffa role manager configuration file
     */
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/security/securityrolesdomain/security-roles_1_0.xsd";

    /**
     * The repository of the role names as keys and Roles as values.
     */
    private IRepository<Role> roleRepository = new MapRepository<>();

    private static Logger log = Logger.getLogger(PolicyCache.class);

    /**
     * registerResource - Registers the roles into the role repository from the roles.xml files found in META-INF/roles.xml
     * that exist in the classpath.
     * @param resource the object that contains the xml config file.
     * @param context  key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerResource(Resource resource, String context, String variation) throws JAXBException, SAXException, IOException {

        Roles roles = JAXBHelper.unmarshalConfigFile(Roles.class, resource, CONFIGURATION_SCHEMA_FILE);
        if (roles.getRole() != null) {
            for (final Role role : roles.getRole()) {
                ContextKey key = new ContextKey(role.getName(), resource.getURI().toString(), variation, context);
                registerRole(key, role);
            }
        }
    }

    /**
     * getResourceFileName - Returns the default XML file name of the security roles XSD.
     * @return return the name of the roles XSD file
     */
    @Override
    public String getResourceFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    /**
     * getRole - Returns the Role object based on the key or name of the role
     * @param roleName
     * @return return the Role being queried.
     */
    public Role getRole(String roleName) {
        return roleRepository.query(roleName);
    }

    /**
     * getRoles - Returns the Roles object which contains a list of Role objects.
     * @return the Roles object which will contain a List of Role
     */
    public Roles getRoles() {
        Roles roles = new Roles();
        roles.getRole().addAll(getAllRoles());
        return roles;
    }

    /**
     * getAllRoles - Returns a list of all roles contained in the roles repository.
     * @return returns a list of all Roles
     */
    public List<Role> getAllRoles() {
        return roleRepository.getAllValues();
    }

    /**
     * buildRoleMap - Builds a hashmap of name-grantfunctionAccess lists for use in the PolicyCache.
     *
     * @return A Map of Role names as keys and values that contain a List of GrantFunctionAccess.
     */
    public Map<String, List<String>> buildRoleMap() {
        Map<String, List<String>> nameGrantFunctionAccessMap = new HashMap<>();
        List<Role> roleList = getAllRoles();
        for (Role role : roleList) {
            if (log.isDebugEnabled())
                log.debug("Processing Role: " + role.getName());
            List<GrantFunctionAccess> access = role.getGrantFunctionAccess();
            List<String> funcs = null;
            if (access != null) {
                funcs = new LinkedList<>();
                // Add all the names in all of the GrantAccess objects to the list.
                for (GrantFunctionAccess gfa : access) {
                    funcs.add(gfa.getName());
                    if (log.isDebugEnabled())
                        log.debug("Processing Role: " + role.getName() + " has function " + gfa.getName());
                }
            }
            // If there are some functions, add it to the master Map
            if (funcs != null)
                nameGrantFunctionAccessMap.put(role.getName(), funcs);
        }
        return nameGrantFunctionAccessMap;
    }

    /**
     * registerRole - Registers a role in the roles repository by name and values.
     * @param contextKey
     * @param role
     */
    public void registerRole(ContextKey contextKey, Role role) {
        getRoleRepository().register(contextKey, role);
    }

    /**
     * unregisterRole - Removes the role in the roles repository by name.
     * @param contextKey
     */
    public void unregisterRole(ContextKey contextKey) {
        getRoleRepository().unregister(contextKey);
    }

    /**
     * getRoleRepository - Returns an instance of the role repository.
     * @return Returns an instance of the role repository
     */
    public IRepository<Role> getRoleRepository() {
        return roleRepository;
    }

    /**
     * setRoleRepository - Allows the role repository to be set to a new instance of the role repository.
     * @param roleRepository
     */
    public void setRoleRepository(IRepository<Role> roleRepository) {
        this.roleRepository = roleRepository;
    }
}
