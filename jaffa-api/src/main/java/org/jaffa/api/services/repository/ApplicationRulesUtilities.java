/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2018 JAFFA Development Group
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
package org.jaffa.api.services.repository;

import org.apache.log4j.Logger;
import org.jaffa.api.rules.IMetaDataRepository;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexProperty;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.util.MessageHelper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * This class contains utility functions for producing content pertaining
 * to application rules.
 * This class was derived from
 * jaffa-ria/source/html/js/extjs/jaffa/metadata/classMetaData.jsp
 */
public class ApplicationRulesUtilities {

    private static final Logger log = Logger.getLogger(ApplicationRulesUtilities.class);

    /** The rule names that are included when transferring rule metadata to another
     * host.
     */
    private static final List<String> RULE_NAMES_TO_TRANSPORT =
            Arrays.asList(
                    "annotation",
                    "case-type",
                    "data-type",
                    "default-value",
                    "hidden",
                    "in-list",
                    "label",
                    "max-length",
                    "max-value",
                    "min-length",
                    "min-value",
                    "read-only"
            );

    public static final String ANNOTATION = "annotation";
    public static final String CASE_TYPE = "caseType";
    public static final String DATA_TYPE = "dataType";
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String HIDDEN = "hidden";
    public static final String IN_LIST = "inList";
    public static final String LABEL = "label";
    public static final String MIN_LENGTH = "minLength";
    public static final String MAX_LENGTH = "maxLength";
    public static final String MIN_VALUE = "minValue";
    public static final String MAX_VALUE = "maxValue";
    public static final String READ_ONLY = "readOnly";

    /**
     * Find the rules for the input Class.
     * @param className the class to locate
     * @param metaDataRepository the repository of metadata that contains
     *                          properties and their corresponding values
     */
    public HashMap<String, Object> getRuleMetaData(String className,
                                                   IMetaDataRepository metaDataRepository)
            throws Exception {
        HashMap<String, Object> metadata = new HashMap<>();
        // Load the Class
        Class clazz = getClass(className);
        String outputClassName = getOutputClassName(className, clazz);

        // Show all the Property attributes
        Map<String, Object> properties = gatherProperties(className, clazz, metaDataRepository);
        metadata.put(outputClassName, properties);
        return metadata;
    }

    /**
     * Return the class corresponding to the provided class name
     * @param className the class to locate
     * @return the class corresponding to the class name, if one exists; null otherwise.
     */
    private Class getClass(String className) {
        Class result = null;
        try {
            result = Class.forName(className);
        } catch (Exception e) {
            // Null output for "virtual" (non-existent) class
        }
        return result;
    }

    /**
     * Find the name used for output with the input Class.
     * @param className the class name (which may be a "pseudo-class name" for
     *                  a non-existent class.
     * @param clazz the class (may be null for a pseudo-class.
     * @return the out name for the class
     */
    private String getOutputClassName(String className, Class clazz) {
        String outputClassName = null;
        if (clazz != null) {
            outputClassName = clazz.getSimpleName();
        }
        else {
            // Pseudo class name output for "virtual" (non-existent) class
            String[] splitClass = className.split("\\.");
            outputClassName = splitClass[splitClass.length-1];
        }
        return outputClassName;
    }


    /**
     *  Determine the property names for the input Class.
     * @param className the class to locate
     * @param clazz the class (may be null for a pseudo-class.
     * @param metaDataRepository the repository of metadata that contains
     *                          properties and their corresponding values
     */
    private String[] determinePropertyNames(String className,
                                            Class clazz,
                                            IMetaDataRepository metaDataRepository)
            throws Exception {
        Collection<String> checkedPropertyNames = new LinkedHashSet<>();
        if (clazz == null) {
            // A flex class may have been passed. Load the corresponding FlexProperties to find the properties
            try {
                FlexClass flexClass = FlexClass.instance(className);
                for (FlexProperty flexProperty : flexClass.getDynaProperties()){
                    checkedPropertyNames.add(flexProperty.getName());
                }
            } catch (Exception ignore) {
            }

            if (checkedPropertyNames.isEmpty()) {
                // A non-flex virtual class is passed. Find properties for which rules have been declared
//                Collection ruleNames = RuleRepository.instance().getRuleNames();
                for (String ruleName : RULE_NAMES_TO_TRANSPORT) {
                    Set<String> properties =
                            metaDataRepository.getPropertyNames(className, ruleName);
                    if (properties != null) {
                        for (String propertyName : properties) {
                            if (propertyName != null)
                                checkedPropertyNames.add(propertyName);
                        }
                    }
                }
            }
        } else {
            // Use the Bean introspector to find the properties
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null) {
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
                    checkedPropertyNames.add(propertyDescriptor.getName());
            }
        }
        String[] nameArray = ((checkedPropertyNames != null) && (checkedPropertyNames.size() > 0)) ?
                checkedPropertyNames.toArray(new String[0]) :
                null;
        return nameArray;
    }

    /** Determine the properties for the input Class.
     * @param className the class to locate
     * @param clazz the class (may be null for a pseudo-class.
     * @param metaDataRepository the repository of metadata that contains
     *                          properties and their corresponding values
     */
    private Map<String, Object> gatherProperties(String className,
                                                 Class clazz,
                                                 IMetaDataRepository metaDataRepository)
            throws Exception {
        Map<String, Object> propertyMetaData = new HashMap<>();
        String[] propertyNames = determinePropertyNames(className, clazz, metaDataRepository);
        if (propertyNames != null && propertyNames.length > 0) {
            for (String propertyName : propertyNames) {
                propertyMetaData = addPropertyMetaData(className, propertyName,null, propertyMetaData);
            }
        }
        return propertyMetaData;
    }


    /**
     * Puts metadata for the given property into a map,
     *  provided rules have been defined for the property.
     * @param className the class to locate
     * @param propertyName the property name to use to find the value
     * @param domainClass the class (may be null for a pseudo-class).
     * @param propertyMetaData the repository of metadata that contains
     *                          properties and their corresponding values
     */
    public Map<String, Object> addPropertyMetaData(String className, String propertyName,
                                                    Class domainClass, Map<String, Object> propertyMetaData)
            throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
        IPropertyRuleIntrospector pri = rulesEngine.getPropertyRuleIntrospector(className, propertyName, null);
        String type = toJsType(pri.getPropertyType());

        if (domainClass != null && type.equals("object")) {
            IPropertyRuleIntrospector propertyRuleIntrospector =
                    rulesEngine.getPropertyRuleIntrospector(domainClass, propertyName);
            type = toJsType(propertyRuleIntrospector.getPropertyType());
        }
        map.put(ANNOTATION, pri.getAnnotation());
        map.put(CASE_TYPE, pri.getCaseType());
        map.put(DATA_TYPE, type);
        map.put(DEFAULT_VALUE, getDefaultValue(className, propertyName));
        map.put(HIDDEN, pri.isHidden());
        map.put(IN_LIST, getInListValues(pri));

        Object label = pri.getLabel();
        if (label != null ) {
            String labelString = MessageHelper.replaceTokens((String) label);
            map.put(LABEL, labelString);
        }
        map.put(MIN_LENGTH, pri.getMinLength());
        map.put(MAX_LENGTH, pri.getMaxLength());
        map.put(MIN_VALUE, pri.getMinValue());
        map.put(MAX_VALUE, pri.getMaxValue());
        map.put(READ_ONLY, pri.isReadOnly());
        propertyMetaData.put(propertyName, map);
        return propertyMetaData;
    }

    /**
     * Get the default value for a property
     * @param className the class whose default value is desired
     * @param propertyName the property whose default value is desired
     * @return the default value
     */
    private String getDefaultValue (String className, String propertyName) {
        String defaultValue = null;
        RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
        criteria.setClassName(className);
        criteria.setPropertyName(propertyName);
        criteria.setRuleName("default-value");
        RuleMetaData ruleMetaData = RuleMetaHelper.findRule(criteria);

        if (ruleMetaData != null) {
            defaultValue = ruleMetaData.getText();
        }
        return defaultValue;
    }

    /**
     * Get the "inList" values.
     * @param introspector the introspector to use to find the values
     * @return the default value
     */
    private List<List<String>> getInListValues(IPropertyRuleIntrospector introspector) {
        List<List<String>> result = new ArrayList<>();
        Map<String, String> inListValues = introspector.getInListValues();
        if (inListValues != null && inListValues.size() > 0) {
            for (Map.Entry<String, String> mapEntry : inListValues.entrySet()) {
                List<String> oneEntry = new ArrayList<>();
                String value = toHtml(mapEntry.getKey());
                String label = mapEntry.getValue();
                label = label != null ? toHtml(MessageHelper.replaceTokens(label)) : value;
                oneEntry.add(value);
                oneEntry.add(label);
                result.add(oneEntry);
            }
        }
        return result;
    }

    /** Convert the input to HTML compatible String. */
    private String toHtml(Object obj) {
        // TODO remove this function?
//        return obj == null ? "" :  StringHelper.escapeJavascript(StringHelper.convertToHTML(obj.toString()));
        return obj == null ? "" :  obj.toString();
    }

    /* Convert a Java Class to a JavaScript Equivilent */
    private String toJsType(Class cls) {
        // TODO remove this function?
        if (cls == null) return "object";
        if (String.class.isAssignableFrom(cls)) return "string";
        if (Number.class.isAssignableFrom(cls)) return "number";
        if (Boolean.class.isAssignableFrom(cls)) return "boolean";
        if (DateTime.class.isAssignableFrom(cls)) return "datetime";
        if (DateOnly.class.isAssignableFrom(cls)) return "dateonly";
        if (Currency.class.isAssignableFrom(cls)) return "currency";
        return "object";
    }

}
