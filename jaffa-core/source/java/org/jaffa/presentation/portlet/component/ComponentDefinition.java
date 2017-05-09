/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2012 JAFFA Development Group
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

package org.jaffa.presentation.portlet.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.presentation.portlet.component.componentdomain.Component;
import org.jaffa.presentation.portlet.component.componentdomain.DependentComponent;
import org.jaffa.presentation.portlet.component.componentdomain.MandatoryFunction;
import org.jaffa.presentation.portlet.component.componentdomain.ObjectTypeParam;
import org.jaffa.presentation.portlet.component.componentdomain.OptionalFunction;
import org.jaffa.session.ContextManagerFactory;

/**
 * This class holds the information for a component as defined in the
 * components.xml file.
 */
public class ComponentDefinition {

    private static final Logger LOGGER = Logger.getLogger(ComponentDefinition.class);
    /**
     * The rule name for checking component class existence on startup. If true,
     * then all component classes will loaded by the ClassLoader during startup,
     * thus increasing the startup time.
     */
    private static final String CHECK_COMPONENT_EXISTENCE_ON_STARTUP_RULE = "jaffa.component.checkClassExistenceOnStartup";

    private static final String JSP_EXTENSION = ".jsp";

    private static final String BACKSLASH = "/";

    private static final String CONSTRACTOR_ERROR_MSG = "The Java class for component complex type is NULL.";

    /** The Java class for component complex type. */
    private org.jaffa.presentation.portlet.component.componentdomain.Component component;

    /**
     * The collection of the security components (name, isComponentRef,
     * isRequired, ref) represented by <tt>ComponentSecurity</tt> object.
     */
    private List<ComponentSecurity> ComponentSecuritys;

    /**
     * Holds value of property RIA. True if this is a component based on the new
     * Jaffa RIA / Web 2.0 framework.
     */
    private boolean isRIA;

    /**
     * Constructs a new <tt>ComponentDefinition</tt> class. Only the Loader
     * creates ComponentDefinition objects.
     *
     * @param component <code>org.jaffa.presentation.portlet.component.componentdomain.Component</code>
     *        the Java class for component complex type.
     */
    public ComponentDefinition(final org.jaffa.presentation.portlet.component.componentdomain.Component component) throws ComponentDefinitionException {
        this.component = component;

        if (component == null)
            throw new ComponentDefinitionException(CONSTRACTOR_ERROR_MSG);

        final List<Object> params = getParameters();
        if (params!=null){
            for (final Object param : params) {
                if (param != null && param instanceof ObjectTypeParam) {
                    if (((ObjectTypeParam) param).getClassName() != null) {
                        try {
                            Class.forName(((ObjectTypeParam) param).getClassName());
                        } catch (ClassNotFoundException e) {
                            LOGGER.error(e);
                            new ComponentDefinitionException(component, ((ObjectTypeParam) param));
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the collection of the security components (name, isComponentRef,
     * isRequired, ref) represented by <tt>ComponentSecurity</tt> object.
     *
     * @return <code>List<ComponentSecurity></code> the collection of the entity
     *         (class, interface, array class, primitive type, or void)
     *         represented by <tt>ComponentSecurity</tt> object.
     */
    private List<ComponentSecurity> getSecurityInfo() {
        if (ComponentSecuritys == null) {
            ComponentSecuritys = new ArrayList<ComponentSecurity>();
            if (validate()) {
                checkMandatoryFunctions();
                checkOptionalFunctions();
                checkDependentComponents();
            }
        }
        return ComponentSecuritys;
    }

    /**
     * Returns true if this
     * <tt>org.jaffa.presentation.portlet.component.componentdomain.Component</tt>
     * object represents represents a Web 1.0 or Web 2.0 component type.
     * <p>
     * Note that if this component is a Web 2.0 / RIA component, the RIA flag
     * would also set to true.
     *
     * @return <tt>true</tt> if this component object represents a Web 1.0 or
     *         Web 2.0 component type; <tt>false</tt> otherwise.
     */
    private boolean validate() {

        boolean ret = false;

        if (component == null) {
            return ret;
        }

        final String clazzName = component.getClassName();

        if (clazzName.endsWith(JSP_EXTENSION) || clazzName.indexOf(BACKSLASH) >= 0) {
            // This is a Web 2.0 / RIA component
            // @TODO check for the specified file in the web archive
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Not able to validate RIA component at this time: " + clazzName);
            isRIA = true;
            ret = true;
        } else {
            // This is a Web 1.0 component
            // Ignore the component, if it is not deployed.
            // This requires the loading of all component classes, which can
            // be quite time consuming.
            // This feature is thus controlled by a business rule.
            final Boolean checkComponentExistence = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(CHECK_COMPONENT_EXISTENCE_ON_STARTUP_RULE));
            if (checkComponentExistence != null && checkComponentExistence) {
                try {
                    Class.forName(clazzName);
                    ret = true;
                } catch (ClassNotFoundException e) {
                    LOGGER.warn("Ignoring component '" + component.getId() + "', since the classloader could not load the class: " + clazzName);
                }
            }
        }

        return ret;
    }

    /**
     * Loop through the list of mandatory functions.
     */
    private void checkMandatoryFunctions() {
        final List<MandatoryFunction> list = component.getMandatoryFunction();
        if (list != null && 0 < list.size()) {
            for (final MandatoryFunction function : list) {
                final ComponentSecurity ComponentSecurity = new ComponentSecurity();

                if(LOGGER.isDebugEnabled())
                  LOGGER.debug("MandatoryFunction:" + function.getName());

                ComponentSecurity.setRequired(true);
                ComponentSecurity.setName(function.getName());
                ComponentSecurity.setRef(function.getRef());
                ComponentSecurity.setComponentRef(false);
                ComponentSecuritys.add(ComponentSecurity);
            }
        } else {
           LOGGER.warn("Mandatory function has not been defined for component: " + getComponentName());
        }
    }

    /**
     * Loop through the list of optional functions.
     */
    private void checkOptionalFunctions() {
        final List<OptionalFunction> list = component.getOptionalFunction();
        if (list != null && 0 < list.size()) {
            for (final OptionalFunction function : list) {
                final ComponentSecurity ComponentSecurity = new ComponentSecurity();
                ComponentSecurity.setRequired(false);
                ComponentSecurity.setName(function.getName());
                ComponentSecurity.setRef(function.getRef());
                ComponentSecurity.setComponentRef(false);
                ComponentSecuritys.add(ComponentSecurity);
            }
        }
    }

    /**
     * Loop through the list of dependent components.
     */
    private void checkDependentComponents() {
        final List<DependentComponent> list = component.getDependentComponent();
        if (list != null && 0 < list.size()) {
            for (final DependentComponent function : list) {
                final ComponentSecurity ComponentSecurity = new ComponentSecurity();
                ComponentSecurity.setRequired(false);
                ComponentSecurity.setName(function.getName());
                ComponentSecurity.setRef(function.getRef());
                ComponentSecurity.setComponentRef(true);
                ComponentSecuritys.add(ComponentSecurity);
            }
        }
    }

    /**
     * Returns the collection of the params (name, type, isRequired) represented
     * by <tt>Param</tt> object.
     *
     * @return <code>List<Object></code> the collection of the params (name,
     *         type, isRequired) represented by <tt>Param</tt> object.
     */
    public List<Object> getParameters() {
        List<Object> list = null;
        if (component != null) {
            final Component.Params cp = component.getParams();
            if (cp != null)
                list = cp.getParamStringOrParamIntOrParamBoolean();
        }
        return list;
    }

    /**
     * Getter for property componentDescription.
     *
     * @return Value of property componentDescription.
     */
    public String getComponentDescription() {
        return component != null ? component.getDescription() : null;
    }

    /**
     * Getter for property componentType.
     *
     * @return Value of property componentType.
     */
    public String getComponentType() {
        return component != null ? component.getType() : null;
    }

    /**
     * Getter for property componentName.
     *
     * @return Value of property componentName.
     */
    public String getComponentName() {
        return component != null ? component.getId() : null;
    }

    /**
     * Returns the class name of the <tt>Component</tt> object.
     *
     * @return <code>List</code> the class name of the <tt>Component</tt>
     *         object.
     */
    public String getComponentClass() {
        return component != null ? component.getClassName() : null;
    }

    /**
     * Returns the collection of the security components (name, isComponentRef,
     * isRequired, ref) represented by <tt>ComponentSecurity</tt> object.
     *
     * @return <code>List</code> the collection of the entity (class, interface,
     *         array class, primitive type, or void) represented by
     *         <tt>ComponentSecurity</tt> object.
     */
    public List<ComponentSecurity> getRefFunctions() {
        final List<ComponentSecurity> list = new ArrayList<ComponentSecurity>();
        for (final ComponentSecurity ComponentSecurity : getSecurityInfo()) {
            if (ComponentSecurity.getRef() != null)
                list.add(ComponentSecurity);
        }
        return list;
    }

    /**
     * Returns the string array of the mandatory functions.
     *
     * @return <code>String[]</code> the string array of the mandatory
     *         functions.
     */
    public String[] getMandatoryFunctions() {
        final ArrayList<String> list = new ArrayList<String>();
        for (final ComponentSecurity ComponentSecurity : getSecurityInfo()) {
            if (ComponentSecurity.isRequired())
                list.add(ComponentSecurity.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Returns the string array of the optional functions.
     *
     * @return <code>String[]</code> the string array of the optional functions.
     */
    public String[] getOptionalFunctions() {
        final ArrayList<String> list = new ArrayList<String>();
        for (final ComponentSecurity ComponentSecurity : getSecurityInfo()) {
            if (!ComponentSecurity.isRequired())
                list.add(ComponentSecurity.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Determines if this <tt>Component</tt> if this component is a Web 2.0 /
     * RIA component.
     *
     * @return <tt>true</tt> if this <tt>Component</tt> if this component is a
     *         Web 2.0 / RIA component.; <tt>false</tt> otherwise.
     */
    public boolean isRia() {
        return isRIA;
    }
}