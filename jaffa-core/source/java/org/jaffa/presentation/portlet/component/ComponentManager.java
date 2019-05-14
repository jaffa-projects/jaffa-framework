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
package org.jaffa.presentation.portlet.component;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.presentation.portlet.component.componentdomain.Loader;

import java.util.Map;
import java.util.HashMap;

import org.jaffa.security.SecurityManager;
import org.owasp.encoder.Encode;

import java.security.AccessControlException;

/**
 * This is a Factory for creating Component instances
 */
public class ComponentManager {

    private static Logger log = Logger.getLogger(ComponentManager.class);

    /**
     * Creates an instance of the named component. This component is then added to the UserSession. This may throw the runtime ComponentCreationRuntimeException
     *
     * @param comp The name of the component to create. There should be a valid definition for this name in the 'components.xml' file
     * @param us   The UserSession to which this component will be added
     * @return An instance of the Component
     */
    public static Component run(String comp, UserSession us) {
        Component compInst = null;
        if (log.isDebugEnabled())
            log.debug("Create Component " + comp + " for user " + (us == null ? "null" : us.getUserId()));
        try {

            // see if this user has access to this component
            if (!SecurityManager.checkComponentAccess(comp)) {
                throw new AccessControlException("No Access To Component " + Encode.forHtml(comp));
            }

            //Find the Definition
            ComponentDefinition cd = find(comp);
            if (cd == null) {
                // Not Found
                String str = "No ComponentDefinition found for " + comp;
                log.error(str);
                throw new ComponentCreationRuntimeException(Encode.forHtml(str));
            }

            // Get the ClassObject of the component
            Class clazz;
            if (cd.isRia()) clazz = RiaWrapperComponent.class;
            else clazz = Class.forName(cd.getComponentClass());

            if (us != null) {
                // Create an instance of the component
                compInst = (Component) clazz.newInstance();
                compInst.setComponentId(us.getNextComponentId());
                compInst.setComponentDefinition(cd);
                compInst.setUserSession(us);

                // Add the component to the UserSession
                us.addComponent(compInst);
                if (log.isInfoEnabled()) {
                    log.info("Created component " + comp + " having id " + compInst.getComponentId() +
                             " for user " + us.getUserId());
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String str = "Error in running the Component " + Encode.forHtml(comp);
            log.error(str, e);
            throw new ComponentCreationRuntimeException(str, e);
        }
        return compInst;
    }

    /**
     * Locate the named component. This routine uses a cache of ComponentDefinitions.
     * If the cache has not been initialized, this will initialize it by reading in
     * and XML file into the component domain objects (via JAXB), and then create a
     * pool of component definition objects.
     * <p/>
     * If the specified component id not found a null is returned
     */
    public static ComponentDefinition find(String comp) {
        Map<String, ComponentDefinition> componentPool = Loader.getComponentPool();
        // Bail if the pool did not get loaded
        if (componentPool == null) {
            log.error("Failed to build the ComponentDefinition cache");
            return null;
        }

        if (componentPool.containsKey(comp)) return componentPool.get(comp);
        else {
            // Not Found
            if (log.isDebugEnabled()) log.debug("No ComponentDefinition found for " + comp);
            return null;
        }
    }

    /**
     * Get the component requirements, this is Map of mandatory business functions
     * that a user must have access to, to run this component. Each entry in the map is a
     * component and its 'required' functions is stored as the 'value'. The 'value' is stored
     * as and Array([]) of Strings .
     * If the value in the map for a given a component is null, it has no required business functions
     * and is therefore not constrained by the security system.
     *
     * @return a Map containing component requirements.
     */
    public static Map<String, String[]> getComponentRequirements() {
        Map<String, String[]> m = new HashMap<String, String[]>();
        Map<String, ComponentDefinition> componentPool = Loader.getComponentPool();
        // Bail if the pool did not get loaded
        if (componentPool == null) {
            log.error("Failed to build the ComponentDefinition cache");
            return m;
        }

        // Loop through all the definitions
        for (Map.Entry<String, ComponentDefinition> me : componentPool.entrySet()) {
            String comp = me.getKey();
            String[] funcs = me.getValue().getMandatoryFunctions();
            if (funcs != null && funcs.length != 0) m.put(comp, funcs);
            else m.put(comp, null);
        }

        return m;
    }
}
