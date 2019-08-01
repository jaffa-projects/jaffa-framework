/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
package org.jaffa.dwr.converters;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.beanutils.DynaClass;
import org.apache.log4j.Logger;
import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.dwrp.ObjectOutboundVariable;
import org.directwebremoting.dwrp.ParseUtil;
import org.directwebremoting.dwrp.ProtocolConstants;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.extend.Property;
import org.directwebremoting.extend.TypeHintContext;
import org.directwebremoting.util.LocalUtil;
import org.directwebremoting.util.Messages;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexParam;

/**
 * Converter for a FlexBean.
 */
public class FlexBeanConverter extends BeanConverter {

    private static final Logger log = Logger.getLogger(FlexBeanConverter.class);

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     *
     * Copied from BasicObjectConverter
     *
     * Added custom code to invoke the setter on the FlexBean for unknown properties.
     */
    @Override
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
        String value = iv.getValue();

        // If the text is null then the whole bean is null
        if (value.trim().equals(ProtocolConstants.INBOUND_NULL)) {
            return null;
        }

        if (!value.startsWith(ProtocolConstants.INBOUND_MAP_START)) {
            throw new MarshallException(paramType, Messages.getString("BeanConverter.FormatError", ProtocolConstants.INBOUND_MAP_START));
        }

        if (!value.endsWith(ProtocolConstants.INBOUND_MAP_END)) {
            throw new MarshallException(paramType, Messages.getString("BeanConverter.FormatError", ProtocolConstants.INBOUND_MAP_START));
        }

        value = value.substring(1, value.length() - 1);

        try {
            FlexBean bean;
            if (instanceType != null) {
                bean = (FlexBean) instanceType.newInstance();
            } else {
                bean = (FlexBean) paramType.newInstance();
            }

            Map properties = getPropertyMapFromObject(bean, false, true);

            // Loop through the properties passed in
            Map tokens = extractInboundTokens(paramType, value);

            // CUSTOM CODE: Determine the appropriate FlexClass, so that the properties are formatted to the correct layout
            {
                String key = "dynaClass";
                String val = (String) tokens.remove(key);
                Property property = (Property) properties.get(key);
                if (val != null && property != null) {
                    Class propType = FlexClass.class; //property.getPropertyType();
                    String[] split = ParseUtil.splitInbound(val);
                    String splitValue = split[LocalUtil.INBOUND_INDEX_VALUE];
                    String splitType = split[LocalUtil.INBOUND_INDEX_TYPE];
                    InboundVariable nested = new InboundVariable(iv.getLookup(), null, splitType, splitValue);
                    TypeHintContext incc = createTypeHintContext(inctx, property);
                    Object output = converterManager.convertInbound(propType, nested, inctx, incc);
                    property.setValue(bean, output);

                    // The input from the client may merely pass the name of the FlexClass
                    // Recreate the FlexClass to load all it's FlexProperties and then recreate the FlexBean
                    DynaClass flexClass = bean.getDynaClass();
                    if (flexClass != null) {
                        flexClass = FlexClass.instance(flexClass.getName());
                        bean = FlexBean.instance((FlexClass) flexClass);
                    }
                }
            }

            // We should put the new object into the working map in case it
            // is referenced later nested down in the conversion process.
            if (instanceType != null) {
                inctx.addConverted(iv, instanceType, bean);
            } else {
                inctx.addConverted(iv, paramType, bean);
            }

            for (Iterator it = tokens.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                Property property = (Property) properties.get(key);
                if (property == null) {
                    // CUSTOM CODE: Instead of logging a warning, assume that the inbound token
                    // is valid, and create a descriptor for it, such that it invokes the setter
                    // on the FlexBean
                    Class propType = bean.getDynaClass() != null && bean.getDynaClass().getDynaProperty(key) != null ? bean.getDynaClass().getDynaProperty(key).getType() : String.class;
                    property = new FlexDescriptor(key, propType);
                }
                Class propType = property.getPropertyType();
                String[] split = ParseUtil.splitInbound(val);
                String splitValue = split[LocalUtil.INBOUND_INDEX_VALUE];
                String splitType = split[LocalUtil.INBOUND_INDEX_TYPE];
                InboundVariable nested = new InboundVariable(iv.getLookup(), null, splitType, splitValue);
                TypeHintContext incc = createTypeHintContext(inctx, property);
                Object output = converterManager.convertInbound(propType, nested, inctx, incc);
                property.setValue(bean, output);
            }
            return bean;
        } catch (MarshallException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new MarshallException(paramType, ex);
        }
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     *
     * Copied from BasicObjectConverter
     *
     * Added custom code to convert the flexParams array as root level properties on the javascript object.
     */
    @Override
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
        FlexBean flexBean = (FlexBean) data;

        // Where we collect out converted children
        Map ovs = new TreeMap();

        // We need to do this before collecing the children to save recurrsion
        ObjectOutboundVariable ov = new ObjectOutboundVariable(outctx);
        outctx.put(flexBean, ov);

        try {
            Map properties = getPropertyMapFromObject(flexBean, true, false);
            for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                String name = (String) entry.getKey();
                Property property = (Property) entry.getValue();

                // CUSTOM CODE: Special handling for flexParams
                if ("flexParams".equals(name)) {
                    FlexParam[] flexParams = flexBean.getFlexParams();
                    if (flexParams != null) {
                        for (FlexParam flexParam : flexParams) {
                            // Instead of the formatted value returned by flexParam.getValue(),
                            // use the original value returned by the flexBean. This will ensure
                            // standard DWR handling for those value.
                            Object value = flexBean.get(flexParam.getName());
                            if (value != null) { // Added check to exclude null fields
                                OutboundVariable nested = getConverterManager().convertOutbound(value, outctx);
                                ovs.put(flexParam.getName(), nested);
                            }
                        }
                    }
                } else {
                    Object value = property.getValue(flexBean);
                    if (value != null) { // Added check to exclude null fields
                        OutboundVariable nested = getConverterManager().convertOutbound(value, outctx);
                        ovs.put(name, nested);
                    }
                }
            }
            // Add the className to the object
            if (flexBean != null) {
                String className = flexBean.getClass().getSimpleName();
                OutboundVariable var = getConverterManager().convertOutbound(className, outctx);
                ovs.put("className", var);
            }
        } catch (MarshallException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new MarshallException(flexBean.getClass(), ex);
        }
        ov.init(ovs, getJavascript());
        return ov;
    }

    /** This class is used to encapsulate the setter on the FlexBean class. */
    private static class FlexDescriptor implements Property {

        private String name;
        private Class propertyType;
        private static final Method c_setter;

        static {
            try {
                c_setter = FlexBean.class.getMethod("set", String.class, Object.class);
            } catch (Exception e) {
                String s = "Unable to obtain a handle on the 'public void set(String name, Object value)' method of FlexBean";
                log.fatal(s, e);
                throw new RuntimeException(s, e);
            }
        }

        FlexDescriptor(String name, Class propertyType) {
            this.name = name;
            this.propertyType = propertyType;
        }

        /**
         * Gets the name of this property
         * @return The property name
         */
        public String getName() {
            return name;
        }

        /**
         * What type does this property
         * @return The type of object that will be returned by {@link #getValue(Object)}
         */
        public Class getPropertyType() {
            return propertyType;
        }

        /**
         * Get the value of this property of the passed in java bean
         * @param bean The bean to introspect
         * @return The value assigned to this property of the passed in bean
         * @throws MarshallException If the reflection access fails
         */
        public Object getValue(Object bean) throws MarshallException {
            return ((FlexBean) bean).get(name);
        }

        /**
         * Set the value of this property of the passed in java bean
         * @param bean The bean to introspect
         * @param value The value assigned to this property of the passed in bean
         * @throws MarshallException If the reflection access fails
         */
        public void setValue(Object bean, Object value) throws MarshallException {
            ((FlexBean) bean).set(name, value);
        }

        /**
         * This is a nasty hack - {@link TypeHintContext} needs a {@link Method}.
         * If you are implementing this and not proxying to a {@link PropertyDescriptor}
         * then you can probably return <code>null</code>.
         * We should probably refactor {@link TypeHintContext} to use {@link Property}
         * @return A setter method if one is available, or null otherwise
         */
        public Method getSetter() {
            return c_setter;
        }
    }
}
