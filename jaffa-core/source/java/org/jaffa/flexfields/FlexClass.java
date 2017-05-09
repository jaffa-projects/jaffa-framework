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
 * 1.  Redistributions of source code must retain copyright statements and notices.
 *     Redistributions must also contain a copy of this document.
 * 2.  Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.  The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.  Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.  Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.flexfields;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.beanutils.DynaClass;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.StringHelper;

/**
 * FlexClass implements the DynaClass interface.
 * It holds the following information:
 *   name: the name for an instance
 *   logicalName: the name to be used while persisting associated FlexBean to the database.
 *   flexProperties: the associated FlexProperty instances
 */
public class FlexClass implements DynaClass {

    private String name;
    private String logicalName;
    private Map<String, FlexProperty> flexProperties = new LinkedHashMap<String, FlexProperty>();
    private Map<String, String> logicalNames = new LinkedHashMap<String, String>();

    // *************************
    // **** IMPLEMENTATION *****
    // *************************
    /**
     * Returns the name.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the FlexProperty for the input property name.
     * @param name the property name.
     * @return the FlexProperty for the input property name.
     */
    public FlexProperty getDynaProperty(String name) {
        return flexProperties.get(name);
    }

    /**
     * Returns all the FlexProperty instances.
     * @return all the FlexProperty instances.
     */
    public FlexProperty[] getDynaProperties() {
        return flexProperties.values().toArray(new FlexProperty[flexProperties.size()]);
    }

    /**
     * Creates a new instance of FlexBean.
     * @return a new instance of FlexBean
     * @throws IllegalAccessException if the Class or the appropriate constructor is not accessible.
     * @throws InstantiationException if this Class represents an abstract class, an array class, a primitive type, or void; or if instantiation fails for some other reason.
     */
    public FlexBean newInstance() throws IllegalAccessException, InstantiationException {
        return new FlexBean(this);
    }

    // ***********************************
    // **** METHODS TO SUPPORT TOOLS *****
    // ***********************************
    /**
     * Setter for the property name.
     * @param name new value for the property name.
     */
    public void setName(String name) {
        this.name = name;
    }

    // *****************************
    // **** ADDITIONAL METHODS *****
    // *****************************
    /**
     * Returns the logicalName for this class.
     * @return the logicalName for this class.
     */
    public String getLogicalName() {
        return logicalName;
    }

    /**
     * Returns the property name for the input logical property name.
     * @param logicalName the logical name for a property.
     * @return the property name for the input logical property name.
     */
    public String getNameByLogicalName(String logicalName) {
        return logicalNames.get(logicalName);
    }

    /**
     * Returns the FlexProperty for the input logical property name.
     * @param logicalName the logical name for a property.
     * @return the FlexProperty for the input logical property name.
     */
    public FlexProperty getDynaPropertyByLogicalName(String logicalName) {
        return getDynaProperty(getNameByLogicalName(logicalName));
    }

    /**
     * Creates a FlexClass instance based on the input.
     * @param className the class name.
     * @return a FlexClass instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public static FlexClass instance(String className) throws ApplicationExceptions, FrameworkException {
        return instance(className, null, null);
    }

    /**
     * Creates a FlexClass instance based on the input.
     * @param clazz the Class.
     * @return a FlexClass instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public static FlexClass instance(Class clazz) throws ApplicationExceptions, FrameworkException {
        return instance(null, clazz, null);
    }

    /**
     * Creates a FlexClass instance based on the input.
     * @param object the Object.
     * @return a FlexClass instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public static FlexClass instance(Object object) throws ApplicationExceptions, FrameworkException {
        return instance(null, null, object);
    }

    /** Creates a FlexClass instance based on the input. */
    private static FlexClass instance(String className, Class clazz, Object object) throws ApplicationExceptions, FrameworkException {
        try {
            // determine the arguments
            if (object != null)
                clazz = object.getClass();
            if (clazz != null)
                className = clazz.getName();

            // Obtain flex info
            IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, object);
            Properties flexInfo = introspector.getFlexInfo();

            // If flex info is not found for the input class, traverse up the class hierarchy until the flexInfo is found
            if (flexInfo == null) {
                try {
                    Class c = clazz == null ? Class.forName(className).getSuperclass() : clazz.getSuperclass();
                    while (c != null && c != Object.class) {
                        IObjectRuleIntrospector i = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(c.getName(), object);
                        flexInfo = i.getFlexInfo();
                        if (flexInfo != null) {
                            clazz = c;
                            className = c.getName();
                            introspector = i;
                            break;
                        }
                        c = c.getSuperclass();
                    }
                } catch (ClassNotFoundException ignore) {
                }
            }

            // Determine name and logicalName
            String name, logicalName;
            if (flexInfo != null) {
                name = flexInfo.getProperty("source");
                logicalName = flexInfo.getProperty("name");
            } else {
                name = className;
                Properties domainInfo = introspector.getDomainInfo();
                logicalName = domainInfo != null ? domainInfo.getProperty("name") : StringHelper.getShortClassName(className);
            }

            // @todo: Cache FlexClass instances based on the 'name'

            // Load all the properties for the source class
            if (!className.equals(name))
                introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(name, object);
            Map<String, Properties> flexInfoForProperties = introspector.getFlexInfoForProperties();

            // Create the FlexClass
            FlexClass flexClass = new FlexClass();
            flexClass.name = name;
            flexClass.logicalName = logicalName;
            if (flexInfoForProperties != null) {
                for (Map.Entry<String, Properties> me : flexInfoForProperties.entrySet()) {
                    String propertyName = me.getKey();
                    FlexProperty flexProperty = new FlexProperty(propertyName, me.getValue());
                    flexClass.flexProperties.put(propertyName, flexProperty);
                    flexClass.logicalNames.put(flexProperty.getLogicalName(), propertyName);
                }
            }
            return flexClass;
        } catch (Throwable e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }
}
