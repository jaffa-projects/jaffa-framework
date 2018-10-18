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
package org.jaffa.security;

import org.apache.log4j.Logger;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.security.securityrolesdomain.Role;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/** This class in the main interface to the secuity policy.
 * Its pupose its to allow the Security Manager to request specific
 * information about the policy.
 *
 * @author paule
 * @version 1.0
 */
public class PolicyManager {

    private static Logger log = Logger.getLogger(PolicyManager.class);
    /** Stores the function index, don't access this directly, use the
     * getFunctionRoleIndex() method, which will build this if not initialized
     * Each entry in the hashmap is a list of strings.
     */
    private static ConcurrentMap<String, Map<String, List<String>>> c_functionRoleIndexByVariation = new ConcurrentHashMap<String, Map<String, List<String>>>();
    /** Stores the component index, don't access this directly, use the
     * getComponentRoleIndex() method, which will build this if not initialized
     * Each entry in the hashmap is a list of strings.
     */
    private static ConcurrentMap<String, Map<String, List<String>>> c_componentRoleIndexByVariation = new ConcurrentHashMap<String, Map<String, List<String>>>();

    /**
     * Provides a role manager to be set and allows the client to view those roles along with
     * related GrantFunctionAccess.
     */
    private static RoleManager roleManager;

    /** Get the list of Role names that have access to the specified business function
     * @param functionName The function name to get the roles for
     * @return Returns an array of Strings, each entry is a role name. If no roles have access to the function a null will be returned
     */
    public static String[] getRolesForFunction(String functionName) {
        Map<String, List<String>> index = getFunctionRoleIndex();
        if (index == null)
            return null;

        // Convert the extracted list to an array
        List<String> l = index.get(functionName);
        return l != null ? l.toArray(new String[l.size()]) : null;
    }

    /** Get the list of Role names that have access to the specified component
     * @param componentName The component name to get the roles for
     * @return Returns an array of Strings, each entry is a role name.
     *         If no roles have access to the component an empty array (new String[] {}) will be returned,
     *         If all roles have access to the component 'null' will be returned
     * @throws SecurityException if the component is invalid
     */
    public static String[] getRolesForComponent(String componentName) {
        Map<String, List<String>> index = getComponentRoleIndex();
        if (index == null)
            return null;

        // Throw an exception if there is no entry for the component. This would mean an invalid component.
        if (!index.containsKey(componentName))
            throw new SecurityException("PolicyManager could not find component '" + componentName + "'");

        // Convert the extracted list to an array
        List<String> l = index.get(componentName);
        return l != null ? l.toArray(new String[l.size()]) : null;
    }

    /** Get the Set of roles defined for the application.
     * @return the Set of roles defined for the application.
     */
    public static Set<String> getRoleSet() {
            List<Role> allRoles = getAllRoles();
            Set<String> roles = allRoles.stream().map(n -> n.getName()).collect(Collectors.toSet());
            return (roles != null && roles.size() > 0) ? roles : new HashSet<>();
    }

    /**
     * Returns the list of roles defined for an application
     * @return
     */
    public static List<Role> getAllRoles() {
        RoleManager roleManager = getRoleManager();
        if (null != roleManager) {
            return roleManager.getAllRoles();
        }
        return new ArrayList<>();
    }

    /** Clear the cached policy. Will be reloaded on the next access.
     */
    public static void clearCache() {
        c_functionRoleIndexByVariation.clear();
        c_componentRoleIndexByVariation.clear();
    }

    /** Return the function role index HashMap, if it has not been initialized
     * yet, then initialize it!
     * @return Return the function role index HashMap
     *
     */
    private static Map<String, List<String>> getFunctionRoleIndex() {
        String variation = VariationContext.getVariation();
        Map<String, List<String>> functionRoleIndex = c_functionRoleIndexByVariation.get(variation);
        if (functionRoleIndex == null) {
            Map<String, List<String>> generatedFunctionRoleIndex = buildFunctionRoleIndex();
            functionRoleIndex = c_functionRoleIndexByVariation.putIfAbsent(variation, generatedFunctionRoleIndex);
            if (functionRoleIndex == null)
                functionRoleIndex = generatedFunctionRoleIndex;
        }
        return functionRoleIndex;
    }

    /** Return the component role index HashMap, if it has not been initialized
     * yet, then initialize it!
     * @return Return the component role index HashMap
     *
     */
    private static Map<String, List<String>> getComponentRoleIndex() {
        String variation = VariationContext.getVariation();
        Map<String, List<String>> componentRoleIndex = c_componentRoleIndexByVariation.get(variation);
        if (componentRoleIndex == null) {
            Map<String, List<String>> generatedComponentRoleIndex = buildComponentRoleIndex();
            componentRoleIndex = c_componentRoleIndexByVariation.putIfAbsent(variation, generatedComponentRoleIndex);
            if (componentRoleIndex == null)
                componentRoleIndex = generatedComponentRoleIndex;
        }
        return componentRoleIndex;
    }

    /** Builds the FunctionRoleIndex based on information aquired from the RoleManager.
     * This supplies the information as a list of roles with function access. The build process
     * transposes this mapping.
     */
    private static Map<String, List<String>> buildFunctionRoleIndex() {
        Map<String, List<String>> index = new HashMap<String, List<String>>();

        for (Role role: PolicyManager.getAllRoles()) {
                List<String> funcs = role.getGrantFunctionAccess().stream().map(n -> n.getName()).collect(Collectors.toList());
                for (String func : funcs) {
                    // Get the function list for this function
                    List<String> idxFunc = index.get(func);
                    if (idxFunc == null) {
                        // New function, create a list and entry for it...
                        idxFunc = new LinkedList<String>();
                        index.put(func, idxFunc);
                    }
                    // Add the role to this function list if not already there
                    // the uniquess check should be removed if uniqueness is inforced in
                    // the XML Policy file!.. For now, assume it is not
                    if (!idxFunc.contains(role.getName()))
                        idxFunc.add(role.getName());
                }
        }
        return index;
    }

    /** Builds the ComponentRoleIndex based on information aquired from the RoleManager object.
     */
    private static Map<String, List<String>> buildComponentRoleIndex() {
        // For each component, loop through each role and see if it has access to the
        // set of business function, if so store the role as having access to this component.
        Map<String, List<String>> index = new HashMap<String, List<String>>();

        // Get the component requirements, each entry in the list is a
        // component and its 'required' functions. The value is of type String[]
        // If there is no entry in here for a component, it has not security
        // requirements and hence full access is allowed. If there is an entry
        // its value is null or an empty array, then NO roles have access to the Component
        Map<String, String[]> compList = ComponentManager.getComponentRequirements();

        // Get the role mappings
        RoleManager roleManager = getRoleManager();
        if (compList != null && roleManager != null) {
            // Loop through all the components that have required functions for access
            for (Map.Entry<String, String[]> me1 : compList.entrySet()) {
                String comp = me1.getKey();
                String[] funcs = me1.getValue();

                // If the value in the map for a given a component is null, it has no required business functions
                // and is therefore not constrained by the security system. Simply add a NULL for that component.
                if (funcs == null) {
                    index.put(comp, null);
                    continue;
                }

                List<String> allowedRoles = new LinkedList<String>();
                // Now check each role for access
                for (Role role : roleManager.getAllRoles()) {
                    List<String> roleList = role.getGrantFunctionAccess().stream().map(n -> n.getName()).collect(Collectors.toList());
                    // Now make sure that all functions in funcs() are available in roleList
                    boolean failed = false;
                    for (int i = 0; i < funcs.length && !failed; i++)
                        failed = !roleList.contains(funcs[i]);
                    // If this role has the requirements for this component, save it!
                    if (!failed)
                        allowedRoles.add(role.getName());
                }

                // Now add this to the master list for this component
                index.put(comp, allowedRoles);
            }
        }
        return index;
    }

    /** Utility function that dumps out the information loaded about the current policy.
     * This writes the output to System.out
     */
    static void printPolicy() {
        printPolicy(new PrintWriter(System.out, true));
    }

    /** Utility function that dumps out the information loaded about the current policy.
     * This writes the output to the specified writer
     */
    static void printPolicy(PrintWriter out) {
        Map<String, List<String>> m = getFunctionRoleIndex();
        if (m == null || m.size() == 0) {
            out.println("No Policy Configured");
            return;
        }

        for (Map.Entry<String, List<String>> me : m.entrySet()) {
            String func = me.getKey();
            out.print("Business Function '" + func + "' can be accessed by ");
            List<String> l = me.getValue();
            if (l == null || l.size() == 0)
                out.println("Nobody!");
            else {
                boolean first = true;
                for (String role : l) {
                    if (!first)
                        out.print(", ");
                    first = false;
                    out.print(role);
                }
                out.println(".");
            }
        }
    }

    public static RoleManager getRoleManager() {
        return roleManager;
    }

    public static void setRoleManager(RoleManager roleManager) {
        PolicyManager.roleManager = roleManager;
    }
}
