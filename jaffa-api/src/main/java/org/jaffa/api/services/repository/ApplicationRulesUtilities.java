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
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineException;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;
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

    private static final String ANNOTATION = "annotation";
    private static final String CASE_TYPE = "caseType";
    private static final String DATA_TYPE = "dataType";
    private static final String DEFAULT_VALUE = "defaultValue";
    private static final String HIDDEN = "hidden";
    private static final String IN_LIST = "inList";
    private static final String LABEL = "label";
    private static final String MIN_LENGTH = "minLength";
    private static final String MAX_LENGTH = "maxLength";
    private static final String MIN_VALUE = "minValue";
    private static final String MAX_VALUE = "maxValue";
    private static final String READ_ONLY = "readOnly";
    private static final String EMBEDDED_RULE_INDICATOR = "{rule:";
    private static final String EMBEDDED_DOMAIN_INDICATOR = "{domain:";

    /**
     * Find the rules for the input class.
     * @param className the class to locate
     * @param metaDataRepository the repository of metadata that contains
     *                          properties and their corresponding values
     * @return a map whose key is the class name, and whose value is
     * the repository of metadata that contains
     * properties, e.g., "com.mirotechnologies.workrecording.core.domain.WorkType"
     * and a map of their corresponding metadata attributes
     */
    public HashMap<String, Object> getRuleMetaData(String className,
                                                   IMetaDataRepository metaDataRepository)
            throws Exception {
        HashMap<String, Object> metadata = new HashMap<>();
        Class clazz = getClass(className);
        String outputClassName = getOutputClassName(className, clazz);
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
        String outputClassName;
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
     * @return the repository of metadata that contains properties, e.g.,
     * "com.mirotechnologies.workrecording.core.domain.WorkType"
     * and a map of their corresponding metadata attributes
     */
    private Map<String, Object> gatherProperties(String className,
                                                 Class clazz,
                                                 IMetaDataRepository metaDataRepository)
            throws Exception {
        Map<String, Object> propertyMetaData = new HashMap<>();
        String[] propertyNames = determinePropertyNames(className, clazz, metaDataRepository);
        if (propertyNames != null && propertyNames.length > 0) {
            // Domain rules are retrieved from a persistent store, so we cache them
            // for efficiency
            Map<String, Collection<String>> domainRulesCache = new HashMap<>();

            for (String propertyName : propertyNames) {
                // Example property, with embedded property:
                // "structure.rules.type.{rule:structure.rules.types}.adHocPosReceiving"
                addPropertyMetaData(className, propertyName,
                        null, propertyMetaData, domainRulesCache);
            }
        }
        return propertyMetaData;
    }


    /**
     * Puts metadata for the given property into a map,
     *  provided rules have been defined for the property.
     * @param className the class to locate
     * @param propertyName the property name to use to find the value.  This may be
     *        a "compound" name that needs to be "expanded", e.g.,
     *        structure.rules.type.{rule:structure.rules.types}.adHocPosReceiving
     *        may be expanded into:
     *                     structure.rules.type.KIT.adHocPosReceiving
     *                     structure.rules.type.AIRCRAFT.adHocPosReceiving
     *                     ...
     * @param domainClass the class (may be null for a pseudo-class).
     * @param propertyMetaData the repository of metadata that contains
     *                          properties and their corresponding values
     */
    public Map<String, Object> addPropertyMetaData(String className,
                                                   String propertyName,
                                                   Class domainClass,
                                                   Map<String, Object> propertyMetaData)
            throws Exception {

        Map<String, Collection<String>> domainRulesCache = new HashMap<>();
        return addPropertyMetaData(className, propertyName, domainClass,
                propertyMetaData, domainRulesCache);
    }

    /**
     * Puts metadata for the given property into a map,
     *  provided rules have been defined for the property.
     * @param className the class to locate
     * @param propertyName the property name to use to find the value.  This may be
     *        a "compound" name that needs to be "expanded", e.g.,
     *        structure.rules.type.{rule:structure.rules.types}.adHocPosReceiving
     *        may be expanded into:
     *                     structure.rules.type.KIT.adHocPosReceiving
     *                     structure.rules.type.AIRCRAFT.adHocPosReceiving
     *                     ...
     * @param domainClass the class (may be null for a pseudo-class).
     * @param propertyMetaData the repository of metadata that contains properties, e.g.,
     *      "com.mirotechnologies.workrecording.core.domain.WorkType"
     *       and a map of their corresponding metadata attributes
     * @param domainRulesCache a cache of embedded domain properties.  For an
     *   embedded property, "{domain:com.mirotechnologies.workrecording.core.domain.WorkType:workTypeId}",
     *   the key would be "com.mirotechnologies.workrecording.core.domain.WorkType", and the associated
     *   value would be a collection of Strings retrieved from the persistent store,
     *   e.g., {"EWIP", "FAB", "NONACT"}
     * @return the repository of metadata that contains
     *         properties, e.g., "com.mirotechnologies.workrecording.core.domain.WorkType"
     *         and a map of their corresponding metadata attributes
     */
    private Map<String, Object> addPropertyMetaData(String className,
                                                   String propertyName,
                                                   Class domainClass,
                                                   Map<String, Object> propertyMetaData,
                                                   Map<String, Collection<String>> domainRulesCache)
            throws Exception {
        HashMap<String, Object> map = putMetadataInMap(className, propertyName, domainClass);
        processEmbeddedElements(propertyName, propertyMetaData, domainRulesCache, map);

        return propertyMetaData;
    }

    /**
     * Puts metadata for the given property into a map,
     *  provided rules have been defined for the property.
     * @param propertyName the property name to use to find the value.  This may be
     *        a "compound" name that needs to be "expanded", e.g.,
     *        structure.rules.type.{rule:structure.rules.types}.adHocPosReceiving
     *        may be expanded into:
     *                     structure.rules.type.KIT.adHocPosReceiving
     *                     structure.rules.type.AIRCRAFT.adHocPosReceiving
     *                     ...
     * @param propertyMetaData the repository of metadata that contains
     *                          properties and their corresponding values
     * @param domainRulesCache a cache of embedded domain properties.  For an
     *   embedded property, "{domain:com.mirotechnologies.workrecording.core.domain.WorkType:workTypeId}",
     *   the key would be "com.mirotechnologies.workrecording.core.domain.WorkType", and the associated
     *   value would be a collection of Strings retrieved from the persistent store,
     *   e.g., {"EWIP", "FAB", "NONACT"}
     * @param map the mapping of attributes to their values, e.g. "readOnly" -> "false"
     */
    private void processEmbeddedElements(String propertyName,
                                         Map<String, Object> propertyMetaData,
                                         Map<String, Collection<String>> domainRulesCache,
                                         HashMap<String, Object> map) {
        // Rules with embedded "rule:" elements need to be expanded, and metadata
        // associated with the expanded key
        if (propertyName.contains(EMBEDDED_RULE_INDICATOR)) {
            // Collect the expanded rule names
            Collection<String> rules = elaborateBusinessRules(propertyName);

            // Add the common metadata for each expanded rule
            if (rules != null) {
                for (String rule : rules) {
                    propertyMetaData.put(rule, map);
                }
            }
        }
        // Rules with embedded "domain:" elements need to be expanded, and metadata
        // associated with the expanded key
        else if (propertyName.contains(EMBEDDED_DOMAIN_INDICATOR)) {
            // Collect the expanded rule names
            Collection<String> rules = elaborateDomainRules(propertyName, domainRulesCache);

            // Add the common metadata for each expanded rule
            if (rules != null) {
                for (String rule : rules) {
                    propertyMetaData.put(rule, map);
                }
            }
        }
        else {
            propertyMetaData.put(propertyName, map);
        }
    }

    /**
     * Puts metadata for the given property into a map,
     * provided rules have been defined for the property.
     *
     * There are instances where we may want to have one description
     * for multiple rules.
     * Sometimes we define a rule that is applicable to multiple submodules,
     * eg. work types or structure types. In these cases we don't want to redefine
     * the rule for every type, so we have an inner property based on
     * another business rule.
     *
     * For example, we have a rule:
     *      module.submodule.types=type1,type2,type3
     * For each of these types we may have a hidden property but we don't
     * want to create the exact same metadata for three different rule names.
     * Instead we can define one rule for
     *    "module.submodule.{rule:module.submodule.types}.hidden".
     * This method associates the metadata value with the unexpanded key.
     * (Later in the processing, metadata may be associated with the expanded
     * versions of the key.)
     * @param className the class to locate
     * @param propertyName the property name to use to find the value
     * @param domainClass the class (may be null for a pseudo-class).
     */
    private HashMap<String, Object> putMetadataInMap(String className, String propertyName, Class domainClass)
            throws RulesEngineException {
        HashMap<String, Object> map = new HashMap<>();
        IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
        IPropertyRuleIntrospector pri =
                rulesEngine.getPropertyRuleIntrospector(className, propertyName, null);
        String type = toJsType(pri.getPropertyType());

        if (domainClass != null && type.equals("object")) {
            pri = rulesEngine.getPropertyRuleIntrospector(domainClass, propertyName);
            type = toJsType(pri.getPropertyType());
        }
        map.put(ANNOTATION, pri.getAnnotation());
        map.put(CASE_TYPE, pri.getCaseType());
        map.put(DATA_TYPE, type);
        map.put(DEFAULT_VALUE, getDefaultValue(className, propertyName));
        map.put(HIDDEN, pri.isHidden());
        map.put(IN_LIST, getInListValues(pri));

        String label = pri.getLabel();
        if (label != null ) {
            String labelString = MessageHelper.replaceTokens(label);
            map.put(LABEL, labelString);
        }
        map.put(MIN_LENGTH, pri.getMinLength());
        map.put(MAX_LENGTH, pri.getMaxLength());
        map.put(MIN_VALUE, pri.getMinValue());
        map.put(MAX_VALUE, pri.getMaxValue());
        map.put(READ_ONLY, pri.isReadOnly());
        return map;
    }

    /**
     * This code generates rule names for rules with
     * "expandable inner properties" containing "rule:".
     *
     * There are instances where we may want to have one description
     * for multiple rules.
     * Sometimes we define a rule that is applicable to multiple submodules,
     * eg. work types or structure types. In these cases we don't want to redefine
     * the rule for every type, so we have an inner property based on
     * another business rule.
     *
     * For example, we have a rule:
     *      module.submodule.types=type1,type2,type3
     * For each of these types we may have a hidden property but we don't
     * want to create the exact same metadata for three different rule names.
     * Instead we can define one rule for
     *    "module.submodule.{rule:module.submodule.types}.hidden".
     * This method returns the three rule names obtained from this single definition.
     * @param sourceRule e.g., "module.submodule.{rule:module.submodule.types}.hidden"
     * @return one or more rule names, after removal of all "rule:" inner elements
     */
    private Collection<String> elaborateBusinessRules(String sourceRule) {
        Collection<String> expandedRules = new ArrayList<>();
        if (sourceRule.contains(EMBEDDED_RULE_INDICATOR)) {
            int iStart, iEnd;
            iStart = sourceRule.indexOf(EMBEDDED_RULE_INDICATOR);
            iEnd = sourceRule.indexOf("}") + 1;
            String innerPropertyName =
                    sourceRule.substring(iStart + EMBEDDED_RULE_INDICATOR.length(),
                            iEnd - 1);

            IContextManager contextManager = ContextManagerFactory.instance();
            // The comma-separated list of names for use in the expanded rules
            // e.g., "KIT,AIRCRAFT,COMPONENT,PLATFORM,ASSETCONTROLLED,ASSETUNCONTROLLED"
            String compoundNames = (String)contextManager.getProperty(innerPropertyName);
            String preRule = sourceRule.substring(0, iStart);
            String postRule = sourceRule.substring(iEnd);

            if (compoundNames != null) {
                String[] busRules = compoundNames.split(",");
                for (String busRule : busRules) {
                    String expandedRule = preRule + busRule + postRule;
                    // Recursively call this method to handle rules with
                    // multiple internal properties.  NOTE: this does not handle
                    // nested internal properties.
                    // TODO Confirm that we don't have nested internal properties
                    Collection<String> furtherExpandedRules =
                            elaborateBusinessRules(expandedRule);
                    expandedRules.addAll(furtherExpandedRules);
                }
            }
        }
        else { // no expansion needed
            expandedRules.add(sourceRule);
        }
        return expandedRules;
    }

    /**
     * This code generates rule names for rules with
     * "expandable inner properties" containing "domain:".
     *
     * There are instances where we may want to have one description
     * for multiple rules.
     * Sometimes we define a rule that is applicable to multiple submodules,
     * eg. worktypes or structuretypes. In these cases, we don't want to redefine
     * the rule for every type, so we have an inner property based
     * on a table column.
     *
     * For example, we have a domain object:
     * com.mirotechnologies.module.submodule.domain.Type that contains a field 'type'
     * For each of these types we may have a hidden property,
     * but we don't want to create the exact same metadata for many
     * different rule names. Instead we can define one rule for
     * 'module.submodule.{domain:com.mirotechnologies.module.submodule.domain.Type:types}.hidden'.
     * This method returns the multiple rule names obtained from this single definition.
     * @param source e.g.,
     *  "module.submodule.{domain:com.mirotechnologies.module.submodule.domain.Type:types}.hidden"
     * @param domainRulesCache a cache of embedded domain properties.  For an
     *   embedded property, "{domain:com.mirotechnologies.workrecording.core.domain.WorkType:workTypeId}",
     *   the key would be "com.mirotechnologies.workrecording.core.domain.WorkType", and the associated
     *   value would be a collection of Strings retrieved from the persistent store,
     *   e.g., {"EWIP", "FAB", "NONACT"}
     * @return one or more rule names, after removal of all "domain:" inner elements
     */
    private Collection<String> elaborateDomainRules(String source,
                                                    Map<String, Collection<String>> domainRulesCache) {
        Collection<String> expandedRules = new ArrayList<>();
        if (source.contains(EMBEDDED_DOMAIN_INDICATOR)) {
            int iStart, iEnd;
            iStart = source.indexOf(EMBEDDED_DOMAIN_INDICATOR);
            iEnd = source.indexOf("}") + 1;
            String innerName = source.substring(iStart, iEnd - 1);
            String[] domainInfo = innerName.split(":");
            String preRule = source.substring(0, iStart);
            String postRule = source.substring(iEnd);

            // If the cache already contains the needed values, use them
            // instead of doing a more expensive retrieval from the
            // persistent store
            if (domainRulesCache.containsKey(domainInfo[1])) {
                Collection<String> domainFieldValues = domainRulesCache.get(domainInfo[1]);
                for (String value : domainFieldValues) {
                    String expandedRule = preRule + value + postRule;
                    // Recursively call this method to handle rules with
                    // multiple internal properties.
                    Collection<String> furtherExpandedRules =
                            elaborateDomainRules(expandedRule, domainRulesCache);
                    expandedRules.addAll(furtherExpandedRules);
                }
            }
            // Retrieve needed values from the persistent store to create
            // expanded rules.
            // Cache the retrieved values for possible reuse later
            else {
                incorporateValuesFromPersistentStore(source, domainRulesCache,
                        expandedRules, domainInfo, preRule, postRule);
            }
//                }
//            }
        }
        else { // no expansion needed
            expandedRules.add(source);
        }
        return expandedRules;
    }

    private void incorporateValuesFromPersistentStore(
            String source, Map<String, Collection<String>> domainRulesCache,
            Collection<String> expandedRules, String[] domainInfo,
            String preRule, String postRule) {
        Criteria myCriteria = new Criteria();
        UOW myUOW = null;

        try {
            myCriteria.setTable(domainInfo[1]);
            /*
             * Added group by to avoid fetching everything. Performance optimization.
             * It will find the unique field value based on the provided field on rule.
             */
            myCriteria.addGroupBy(domainInfo[2], domainInfo[2]);
            myUOW = new UOW();
            Collection<String> domainFieldValues = new ArrayList<>();
            Iterator<Persistent> myCollection = myUOW.query(myCriteria).iterator();
            while (myCollection.hasNext()){
                Map row = (Map)myCollection.next();
                String domainFieldValue = (String)row.get(domainInfo[2]);
                domainFieldValues.add(domainFieldValue);
                String expandedRule = preRule + domainFieldValue + postRule;
                // Recursively call this method to handle rules with
                // multiple internal properties.
                Collection<String> furtherExpandedRules =
                        elaborateDomainRules(expandedRule, domainRulesCache);
                expandedRules.addAll(furtherExpandedRules);
            }
            // Mapping the domain rule name to its content in the cache
            domainRulesCache.put(domainInfo[1], domainFieldValues);
        } catch (Exception e) {
            log.error("Unable to elaborate " + source, e);
        } finally {
            if (myUOW != null) {
                try {
                    myUOW.rollback();
                } catch (Exception e) {
                    log.error("Failed to rollback UOW:" + e);
                }
            }
        }
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
        List<List<String>> result = null;
        Map<String, String> inListValues = introspector.getInListValues();

        if (inListValues != null && inListValues.size() > 0) {
            result = new ArrayList<>();

            for (Map.Entry<String, String> mapEntry : inListValues.entrySet()) {
                List<String> oneEntry = new ArrayList<>();
                String key = mapEntry.getKey();
                String label = mapEntry.getValue();
                label = (label != null) ? MessageHelper.replaceTokens(label) : key;
                oneEntry.add(key);
                oneEntry.add(label);
                result.add(oneEntry);
            }
        }
        return result;
    }

    /** Converts a Java Class to a JavaScript Equivalent */
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
