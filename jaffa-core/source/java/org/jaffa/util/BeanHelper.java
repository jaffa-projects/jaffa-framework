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

package org.jaffa.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.IDateBase;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.IFlexFields;

/** This has convenience methods for dealing with Java Beans.
 */
public class BeanHelper {
    private static Logger log = Logger.getLogger(BeanHelper.class);
    
    /** This will inspect the specified java bean, and extract an Object from the beans
     * getXxx() method, where 'xxx' is the field name passed in.
     * A null will be returned in case there is any error in invoking the getter.
     * @param bean The Java Bean.
     * @param field The field.
     * @throws NoSuchMethodException if there is no getter for the input field.
     * @return the output of the getter for the field.
     */
    public static Object getField(Object bean, String field)
    throws NoSuchMethodException {
        java.lang.reflect.Method method = null;
        try {
            if (bean instanceof DynaBean) {
                try {
                    return PropertyUtils.getProperty(bean, field);
                } catch (NoSuchMethodException e) {
                    // If the bean is a FlexBean instance, then the field could exist on the associated persistentObject
                    if (bean instanceof FlexBean && ((FlexBean) bean).getPersistentObject() != null)
                        return PropertyUtils.getProperty(((FlexBean) bean).getPersistentObject(), field);
                    else
                        throw e;
                }
            } else {
                // Get the Java Bean Info
                java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean.getClass());
                if ( info != null ) {
                    // Get all the properties
                    java.beans.PropertyDescriptor[] pds = info.getPropertyDescriptors();
                    if (pds != null) {
                        // Loop for a matching method
                        for (PropertyDescriptor pd : pds) {
                            if (StringHelper.equalsIgnoreCaseFirstChar(pd.getName(), field)) {
                                // Match found....
                                method = pd.getReadMethod();
                                break;
                            }
                        }
                    }
                }
                if(method != null) {
                    return method.invoke(bean, new Object[] { });
                }

                // Finally, check the FlexBean
                if (bean instanceof IFlexFields) {
                    FlexBean flexBean = ((IFlexFields) bean).getFlexBean();
                    if (flexBean != null && flexBean.get(field)!=null) {
                    	return flexBean.get(field);
                    }
                    else {
                    	throw new NoSuchMethodException();
                    }
                }
            }
        } catch (NoSuchMethodException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Introspection of Property " + field + " on Bean " + bean + " failed. Reason : " + ex.getMessage(), ex);
            return null;
        }
        
        // If we reach here, the method was not found
        throw new NoSuchMethodException("Field Name = " + field);
    }
    
    /** Get the name of the reader method for the specified string.
     * For example 'hello' will return 'getHello'
     * @param field The field.
     * @return the name of the reader/getter method for the specified string.
     */
    public static String getReaderName(String field) {
        if(field == null) return null;
        return "get" + field.substring(0,1).toUpperCase() + field.substring(1);
    }
    
    
    /** This method will introspect the bean & get the setter method for the input propertyName.
     * It will then try & convert the propertyValue to the appropriate datatype.
     * Finally it will invoke the setter.
     * @return A true indicates, the property was succesfully set to the passed value. A false indicates the property doesn't exist or the propertyValue passed is not compatible with the setter.
     * @param bean The bean class to be introspected.
     * @param propertyName The Property being searched for.
     * @param propertyValue The value to be set.
     * @throws IntrospectionException if an exception occurs during introspection.
     * @throws IllegalAccessException if the underlying method is inaccessible.
     * @throws InvocationTargetException if the underlying method throws an exception.
     */
    public static boolean setField(Object bean, String propertyName, String propertyValue)
    throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (propertyValue != null && propertyValue.length() == 0)
            propertyValue = null;
        return setField(bean, propertyName, (Object) propertyValue);
    }
    
    /** This method will introspect the bean & get the setter method for the input propertyName.
     * It will then try & convert the propertyValue to the appropriate datatype.
     * Finally it will invoke the setter.
     * @return A true indicates, the property was succesfully set to the passed value. A false indicates the property doesn't exist or the propertyValue passed is not compatible with the setter.
     * @param bean The bean class to be introspected.
     * @param propertyName The Property being searched for.
     * @param propertyValue The value to be set.
     * @throws IntrospectionException if an exception occurs during introspection.
     * @throws IllegalAccessException if the underlying method is inaccessible.
     * @throws InvocationTargetException if the underlying method throws an exception.
     */
    public static boolean setField(Object bean, String propertyName, Object propertyValue)
    throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        boolean result = false;
        if (bean instanceof DynaBean) {
            try {
                PropertyUtils.setProperty(bean, propertyName, propertyValue);
                result = true;
            } catch (NoSuchMethodException ignore) {
                // If the bean is a FlexBean instance, then the field could exist on the associated persistentObject
                if (bean instanceof FlexBean && ((FlexBean) bean).getPersistentObject() != null) {
                    try {
                        PropertyUtils.setProperty(((FlexBean) bean).getPersistentObject(), propertyName, propertyValue);
                        result = true;
                    } catch (NoSuchMethodException ignore2) {
                    }
                }
            }
        } else {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            if (beanInfo != null) {
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                if (pds != null) {
                    for (PropertyDescriptor pd : pds) {
                        if (StringHelper.equalsIgnoreCaseFirstChar(pd.getName(), propertyName)) {
                            Method m = pd.getWriteMethod();
                            Object convertedPropertyValue = null;
                            if (propertyValue != null) {
                                if (pd.getPropertyType().isEnum()) {
                                    convertedPropertyValue = findEnum(pd.getPropertyType(), propertyValue.toString());
                                } else {
                                    try {
                                        convertedPropertyValue = DataTypeMapper.instance().map(propertyValue, pd.getPropertyType());
                                    } catch (Exception e) {
                                        // do nothing
                                        break;
                                    }
                                }
                            }
                            m.invoke(bean, new Object[] {convertedPropertyValue});
                            result = true;
                            break;
                        }
                    }
                }
            }

            try {
                // Finally, check the FlexBean
                if (!result && bean instanceof IFlexFields && ((IFlexFields) bean).getFlexBean() != null && ((IFlexFields) bean).getFlexBean().getDynaClass().getDynaProperty(propertyName) != null) {
                    ((IFlexFields) bean).getFlexBean().set(propertyName, propertyValue);
                    result = true;
                }
            } catch (Exception ignore) {
            }
        }
        return result;
    }
    
    /**
     * This will inspect the input beanClass, and obtain the type for the input property.
     * A null will be returned in case there is no such property.
     * @return the type for the property.
     * @param beanClass The bean class to be introspected.
     * @param propertyName The property name.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static Class getPropertyType(Class beanClass, String propertyName)
    throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        if (beanInfo != null) {
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if (pds != null) {
                for (PropertyDescriptor pd : pds) {
                    if (StringHelper.equalsIgnoreCaseFirstChar(pd.getName(), propertyName))
                        return pd.getPropertyType();
                }
            }
        }
        return null;
    }
    
    /** Clones the input bean, performing a deep copy of its properties.
     * @param bean the bean to be cloned.
     * @param deepCloneForeignField if false, then the foreign-fields of a GraphDataObject will not be deep cloned.
     * @return a clone of the input bean.
     * @throws IllegalAccessException if the underlying method is inaccessible.
     * @throws InvocationTargetException if the underlying method throws an exception.
     * @throws InstantiationException if the bean cannot be instantiated.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    public static Object cloneBean(Object bean, boolean deepCloneForeignField) throws IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        if (bean == null)
            return bean;

        Class beanClass = bean.getClass();

        // Return the input as-is, if immutable
        if (beanClass == String.class || beanClass == Boolean.class || Number.class.isAssignableFrom(beanClass) || IDateBase.class.isAssignableFrom(beanClass) || Currency.class.isAssignableFrom(beanClass) || beanClass.isPrimitive()) {
            return bean;
        }

        // Handle an array
        if (beanClass.isArray()) {
            Class componentType = beanClass.getComponentType();
            int length = Array.getLength(bean);
            Object clone = Array.newInstance(componentType, length);
            for (int i = 0; i < length; i++) {
                Object arrayElementClone = cloneBean(Array.get(bean, i), deepCloneForeignField);
                Array.set(clone, i, arrayElementClone);
            }
            return clone;
        }

        // Handle a Collection
        if (bean instanceof Collection) {
            Collection clone = (Collection) bean.getClass().newInstance();
            for (Object collectionElement : (Collection) bean) {
                Object collectionElementClone = cloneBean(collectionElement, deepCloneForeignField);
                clone.add(collectionElementClone);
            }
            return clone;
        }

        // Handle a Map
        if (bean instanceof Map) {
            Map clone = (Map) bean.getClass().newInstance();
            for (Iterator i = ((Map) bean).entrySet().iterator(); i.hasNext();) {
                Map.Entry me = (Map.Entry) i.next();
                Object keyClone = cloneBean(me.getKey(), deepCloneForeignField);
                Object valueClone = cloneBean(me.getValue(), deepCloneForeignField);
                clone.put(keyClone, valueClone);
            }
            return clone;
        }

        // Invoke the 'public Object clone()' method, if available
        if (bean instanceof Cloneable) {
            try {
                Method cloneMethod = beanClass.getMethod("clone");
                return cloneMethod.invoke(bean);
            } catch (NoSuchMethodException e) {
                // do nothing
            }
        }

        // Create a clone using bean introspection
        Object clone = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        if (beanInfo != null) {
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            if (pds != null) {
                // Obtain a GraphMapping; only if foreign-fields are not to be cloned
                //Use reflection to achieve the following:
                //GraphMapping graphMapping = !deepCloneForeignField && bean instanceof GraphDataObject ? MappingFactory.getInstance(bean) : null;
                Object graphMapping = null;
                Method isForeignFieldMethod = null;
                try {
                    if (!deepCloneForeignField && Class.forName("org.jaffa.soa.graph.GraphDataObject").isInstance(bean)) {
                        graphMapping = Class.forName("org.jaffa.soa.dataaccess.MappingFactory").getMethod("getInstance", Object.class).invoke(null, bean);
                        isForeignFieldMethod = graphMapping.getClass().getMethod("isForeignField", String.class);
                    }
                } catch (Exception e) {
                    // do nothing since JaffaSOA may not be deployed
                    if (log.isDebugEnabled())
                        log.debug("Exception in obtaining the GraphMapping", e);
                }

                for (PropertyDescriptor pd : pds) {
                    if (pd.getReadMethod() != null && pd.getWriteMethod() != null) {
                        // Do not clone a foreign-field
                        Object property = pd.getReadMethod().invoke(bean);

                        //Use reflection to achieve the following:
                        //Object propertyClone = graphMapping != null && graphMapping.isForeignField(pd.getName()) ? property : cloneBean(property, deepCloneForeignField);
                        Object propertyClone = null;
                        boolean propertyCloned = false;
                        if (graphMapping != null && isForeignFieldMethod != null) {
                            try {
                                if ((Boolean) isForeignFieldMethod.invoke(graphMapping, pd.getName())) {
                                    propertyClone = property;
                                    propertyCloned = true;
                                }
                            } catch (Exception e) {
                                // do nothing since JaffaSOA may not be deployed
                                if (log.isDebugEnabled())
                                    log.debug("Exception in invoking GraphMapping.isForeignField()", e);
                            }
                        }
                        if (!propertyCloned)
                            propertyClone = cloneBean(property, deepCloneForeignField);

                        pd.getWriteMethod().invoke(clone, propertyClone);
                    }
                }
            }
        }
        return clone;
    }

    /** This method will introspect the beanClass & get the getter method for the input propertyName.
     * It will then try & convert the propertyValue to the appropriate datatype.
     * @param beanClass The bean class to be introspected.
     * @param propertyName The Property being searched for.
     * @param propertyValue The value to be converted.
     * @throws IntrospectionException if an exception occurs during introspection.
     * @throws IllegalArgumentException if the property cannot be found on the input class.
     * @throws ClassCastException if the input value can't be mapped to target class
     * @return a converted propertyValue compatible with the getter.
     * @deprecated Use DataTypeMapper.instance() instead.
     */
    public static Object convertDataType(Class beanClass, String propertyName, String propertyValue)
    throws IntrospectionException, IllegalArgumentException, ClassCastException {
        Object convertedPropertyValue = null;
        if (propertyValue != null) {
            boolean foundProperty = false;
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            if (beanInfo != null) {
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                if (pds != null) {
                    for (PropertyDescriptor pd : pds) {
                        if (StringHelper.equalsIgnoreCaseFirstChar(pd.getName(), propertyName)) {
                            foundProperty = true;
                            if (pd.getPropertyType().isEnum()) {
                                convertedPropertyValue = findEnum(pd.getPropertyType(), propertyValue.toString());
                                if (convertedPropertyValue == null)
                                    throw new IllegalArgumentException("Property " + beanClass.getName() + '.' + propertyName + ", cannot be set to the value " + propertyValue + ", since it is not defined in the Enum " + pd.getPropertyType());
                            } else {
                                convertedPropertyValue = DataTypeMapper.instance().map(propertyValue, pd.getPropertyType());
                            }
                            break;
                        }
                    }
                }
            }
            if (!foundProperty)
                throw new IllegalArgumentException("Could not find the the property: " + beanClass.getName() + '.' + propertyName);
        }
        return convertedPropertyValue;
    }
    
    
    /** This is a helper that can be used for debugging, it will return a list of all the public methods
     * on this class.
     */
    public static String listMethods(Class beanClass) {
        try {
            StringBuffer buf = new StringBuffer();
            Method[] methods = beanClass.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    buf.append("public ")
                    .append(method.getReturnType().getName())
                    .append(' ')
                    .append(method.getName())
                    .append("( ");
                    Class[] classes = method.getParameterTypes();
                    if (classes != null) {
                        for (Class clazz : classes)
                            buf.append(clazz.getName()).append(' ');
                    }
                    buf.append(")\n");
                }
            }
            return buf.toString();
        } catch(Exception e) {
            return e.getMessage();
        }
    }
    
    /** Iterates through the defined constants for the input Enum class.
     * Returns the constant that matches the input value.
     * @param enumClass The Enum class.
     * @param value The value whose corresponding Enum constant is to be returned.
     * @return the Enum constant that matches the input value. A null is returned if no match is found or if the input class is not an Enum.
     */
    private static Object findEnum(Class enumClass, String value) {
        if (enumClass.isEnum()) {
            Object[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants != null) {
                for (Object enumConstant : enumConstants) {
                    if (enumConstant.toString().equals(value))
                        return enumConstant;
                }
            }
        }
        return null;
    }
    
}
