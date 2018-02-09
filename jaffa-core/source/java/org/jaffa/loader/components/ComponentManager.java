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

package org.jaffa.loader.components;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.ComponentDefinitionException;
import org.jaffa.presentation.portlet.component.componentdomain.Component;
import org.jaffa.presentation.portlet.component.componentdomain.Components;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A class that manages various kinds of component object specifications, as
 * read in from a configuration file.
 * Created by kcassell on 8/7/2017.
 */
public class ComponentManager implements IManager {

    /** The name of the configuration file which this class handles. */
    private static final String DEFAULT_COMPONENTS_FILE = "components.xml";

    /** The location of the schema for the configuration file. */
    private static final String COMPONENT_XSD =
            "org/jaffa/presentation/portlet/component/componentdomain/" +
                    "component-definitions_1_1.xsd";

    /** The name of the configuration file which this class handles. */
    private String configurationFile = DEFAULT_COMPONENTS_FILE;

    /** The ComponentDefinition repository.  The key is the component name of
     * the value in the ComponentDefinition object. */
    private IRepository<ComponentDefinition> componentRepository = new MapRepository<>("ComponentDefinition");

    /**
     * The list of repositories managed by this class
     */
    private HashMap managedRepositories = new HashMap<String, IRepository>() {
        {
            put(componentRepository.getName(), componentRepository);
        }

    };


    /**
     * Unmarshall the contents of the configuration to create and register
     * ComponentDefinition, QueueInfo, TopicInfo, and/or MessageFilter objects.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @param variation with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException for file opening or reading errors, or when an
     * attempt to create a ComponentDefinition throws a
     * ComponentDefinitionException
     */
    @Override
    public void registerResource(Resource resource, String context, String variation)
            throws JAXBException, SAXException, IOException {

        Components components = JAXBHelper.unmarshalConfigFile(Components.class,
                resource, COMPONENT_XSD);

        List<Component> componentList = components.getComponent();

        if (componentList != null) {
            for (final Component component : componentList) {
                ComponentDefinition definition = createComponentDefinition(component);
                ContextKey contextKey = new ContextKey(definition.getComponentName(), resource.getURI().toString(), variation, context);
                registerComponentDefinition(contextKey, definition);
            }
        }
    }

    /**
     * Unregister a given component resource.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @param variation with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException for file opening or reading errors, or when an
     * attempt to create a ComponentDefinition throws a
     * ComponentDefinitionException
     */
    @Override
    public void unregisterResource(Resource resource, String context, String variation)
            throws JAXBException, SAXException, IOException {

        Components components = JAXBHelper.unmarshalConfigFile(Components.class,
                resource, COMPONENT_XSD);

        List<Component> componentList = components.getComponent();

        if (componentList != null) {
            for (final Component component : componentList) {
                ComponentDefinition definition = createComponentDefinition(component);
                ContextKey contextKey = new ContextKey(definition.getComponentName(), resource.getURI().toString(), variation, context);
                unregisterComponentDefinition(contextKey);
            }
        }
    }

    /**
     * retrieves the ComponentDefinition from the repository
     * @param name key used for the repository
     * @return ComponentDefinition
     */
    public ComponentDefinition getComponentDefinition(String name) {
        return componentRepository.query(name);
    }


    ////////////////////////////////////////////////////////////////////////
    //////////////////////////// Registration //////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Register ComponentDefinition in the repository
     * @param contextKey the key associated with the value in the repository
     */
    public void registerComponentDefinition(ContextKey contextKey, ComponentDefinition value){
        componentRepository.register(contextKey, value);
    }

    /**
     * Unregister a ComponentDefinition object from the repository
     * @param contextKey the key for the value being removed from the repository
     */
    public void unregisterComponentDefinition(ContextKey contextKey) {
        componentRepository.unregister(contextKey);
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////// Simple Accessors ////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    @Override
    public String getResourceFileName() {
        return configurationFile;
    }

    /**
     * getRepositoryNames - Retrieve a String list of all the IRepositories managed by this IManager
     * @return A list of repository names managed by this manager
     */
    @Override
    public Set getRepositoryNames() {
        return managedRepositories.keySet();
    }

    /**
     * Retrieve an IRepository managed by this IManager via its String name
     * @param name The name of the repository to be retrieved
     * @return The retrieved repository, or empty if no matching repository was found.
     */
    @Override
    public IRepository<?> getRepositoryByName(String name) {
        return (IRepository<?>) managedRepositories.get(name);
    }

    public IRepository<ComponentDefinition> getComponentRepository() {
        return componentRepository;
    }

    public void setComponentRepository(
            IRepository<ComponentDefinition> repository) {
        this.componentRepository = repository;
    }

    /**
     * Create a component definition from a given component
     * @param component The component to create a definition of
     * @return  The component definition
     * @throws IOException  If the component can not be accessed or operations can not be performed on it
     */
    private ComponentDefinition createComponentDefinition(Component component) throws IOException {
        ComponentDefinition definition;
        try {
            definition = new ComponentDefinition(component);
        } catch (ComponentDefinitionException e) {
            // wrap the thrown exception in an IOException to conform
            // to the interface
            throw new IOException(e.getMessage(), e);
        }
        return definition;
    }

}
