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

package org.jaffa.rules.meta;

import org.apache.log4j.Logger;
import org.jaffa.api.rules.IMetaDataRepository;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.commons.AbstractLoader;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.util.ListSet;
import org.jaffa.util.StringHelper;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to import metadata and index them by class, property and source.
 */
public class MetaDataRepository extends AbstractLoader
        implements IMetaDataRepository {

    private static final String ELEMENT_PROPERTY = "property";
    private static final String ATTR_CLASS = "class";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_EXTENDS_CLASS = "extends-class";
    private static final String ATTR_EXTENDS_PROPERTY = "extends-property";
    private static final String ATTR_CONDITION = "condition";
    private static final String ATTR_LANGUAGE = "language";
    private static final String ATTR_VARIATION = "variation";
    private static final String ATTR_EXECUTION_REALM = "execution-realm";
    private static Logger log = Logger.getLogger(MetaDataRepository.class);
    // Singleton instance
    private static MetaDataRepository c_instance = new MetaDataRepository();
    // Cache containing 'Map containing a List of RuleMetaData instances per propertyName' per className+ruleName combination.
    private final Map<ClassRuleKey, Map<String, List<RuleMetaData>>> m_cacheOfPropertyRuleMetaDataMapByClassRuleKey = new WeakHashMap<>();
    // Cache containing array of classNames per ruleName
    private final Map<String, List<String>> m_cacheOfClassNamesPerRule = new WeakHashMap<>();
    // Repository containing List of ClassMetaData instances per className
    private Map<String, List<ClassMetaData>> m_classMetaDataListByClassName = new LinkedHashMap<>();
    // Repository containing List of ClassMetaData instances per source
    private Map<String, List<ClassMetaData>> m_classMetaDataListBySource = new LinkedHashMap<>();

    /**
     * Creates an instance.
     */
    private MetaDataRepository() {
    }

    /**
     * Returns the Singleton instance.
     *
     * @return the Singleton instance.
     */
    public static MetaDataRepository instance() {
        return c_instance;
    }

    /**
     * Imports meta data.
     *
     * @param metadataElement the metadata element we are processing within a source.
     * @param source          the name of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled()) {
            log.debug("Loading metadata: " + source + ", " + metadataElement.getAttribute(ATTR_CLASS));
        }

        // Create a ClassMetaData instance
        ClassMetaData cmd = new ClassMetaData();
        cmd.setName(metadataElement.getAttribute(ATTR_CLASS));
        cmd.setCondition(metadataElement.hasAttribute(ATTR_CONDITION) ? metadataElement.getAttribute(ATTR_CONDITION) : null);
        cmd.setLanguage(metadataElement.hasAttribute(ATTR_LANGUAGE) ? metadataElement.getAttribute(ATTR_LANGUAGE) : null);
        cmd.setVariation(metadataElement.hasAttribute(ATTR_VARIATION) ? metadataElement.getAttribute(ATTR_VARIATION) : null);
        cmd.setSource(source);
        cmd.setLine(getLine(metadataElement));
        cmd.setExecutionRealm(metadataElement.hasAttribute(ATTR_EXECUTION_REALM) ? metadataElement.getAttribute(ATTR_EXECUTION_REALM) : null);
        if (cmd.getExecutionRealm() != null)
            RealmRepository.instance().addMapping(cmd.getName(), cmd.getExecutionRealm());
        cmd.setExtendsClass(metadataElement.hasAttribute(ATTR_EXTENDS_CLASS) ? metadataElement.getAttribute(ATTR_EXTENDS_CLASS) : null);

        Element[] propertyElements = getChildren(metadataElement);
        for (Element propertyElement : propertyElements) {
            if (ELEMENT_PROPERTY.equals(propertyElement.getNodeName())) {
                // create a property
                PropertyMetaData pmd = new PropertyMetaData();
                pmd.setClassMetaData(cmd);
                pmd.setName(propertyElement.hasAttribute(ATTR_NAME) ? StringHelper.getJavaBeanStyle(propertyElement.getAttribute(ATTR_NAME)) : null);
                pmd.setCondition(propertyElement.hasAttribute(ATTR_CONDITION) ? propertyElement.getAttribute(ATTR_CONDITION) : cmd.getCondition());
                pmd.setLanguage(propertyElement.hasAttribute(ATTR_LANGUAGE) ? propertyElement.getAttribute(ATTR_LANGUAGE) : cmd.getLanguage());
                pmd.setVariation(propertyElement.hasAttribute(ATTR_VARIATION) ? propertyElement.getAttribute(ATTR_VARIATION) : cmd.getVariation());
                pmd.setSource(source);
                pmd.setLine(getLine(propertyElement));
                pmd.setExtendsClass(propertyElement.hasAttribute(ATTR_EXTENDS_CLASS) ? propertyElement.getAttribute(ATTR_EXTENDS_CLASS) : cmd.getExtendsClass());
                pmd.setExtendsProperty(propertyElement.hasAttribute(ATTR_EXTENDS_PROPERTY) ? propertyElement.getAttribute(ATTR_EXTENDS_PROPERTY) :
                        (pmd.getExtendsClass() != null ? pmd.getName() : null));

                // Add rules to the property
                Element[] ruleElements = getChildren(propertyElement);
                for (Element ruleElement : ruleElements) {
                    pmd.addRule(createRuleMetaData(ruleElement, cmd, pmd));
                }

                // Add the property to ClassMetaData
                cmd.addProperty(pmd);
            } else {
                // This is a ruleElement. Add it to the ClassMetaData
                cmd.addRule(createRuleMetaData(propertyElement, cmd, null));
            }
        }

        if (log.isDebugEnabled())
            log.debug("Loaded metadata: " + cmd);

        addToRepository(cmd);
    }

    /**
     * Add the ClassMetaData object and any properties it contains, populating them with defaults
     * for values that are not provided.
     *
     * @param cmd a configured ClassMetaData object.
     */
    public void addClassMetaData(ClassMetaData cmd) {
        if (cmd.getExecutionRealm() != null) {
            RealmRepository.instance().addMapping(cmd.getName(), cmd.getExecutionRealm());
        }
        addToRepository(cmd);
    }

    /**
     * Adds the meta-data to the repository.
     *
     * @param cmd the class meta-data.
     */
    private void addToRepository(ClassMetaData cmd) {
        synchronized (this) {
            List<ClassMetaData> classMetaDataList = m_classMetaDataListByClassName.get(cmd.getName());
            if (classMetaDataList == null) {
                classMetaDataList = new LinkedList<>();
                m_classMetaDataListByClassName.put(cmd.getName(), classMetaDataList);
            }
            classMetaDataList.add(cmd);

            classMetaDataList = m_classMetaDataListBySource.get(cmd.getSource());
            if (classMetaDataList == null) {
                classMetaDataList = new LinkedList<>();
                m_classMetaDataListBySource.put(cmd.getSource(), classMetaDataList);
            }
            classMetaDataList.add(cmd);
        }
    }

    /**
     * Unloads the XML that was imported from the input.
     *
     * @param uri the URI of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void unload(String uri) throws JaffaRulesFrameworkException {
        // Clear the cache
        if (log.isDebugEnabled())
            log.debug("Clearing cache");
        m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.clear();
        m_cacheOfClassNamesPerRule.clear();

        // Remove from the repository
        List<ClassMetaData> classes = m_classMetaDataListBySource.remove(uri);
        if (classes != null) {
            for (ClassMetaData cmd : classes) {
                if (log.isDebugEnabled())
                    log.debug("Unloading " + cmd);
                List<ClassMetaData> classesByClassName = m_classMetaDataListByClassName.get(cmd.getName());
                if (classesByClassName != null)
                    classesByClassName.remove(cmd);
                if (classesByClassName.size() == 0)
                    m_classMetaDataListByClassName.remove(cmd.getName());
                if (cmd.getExecutionRealm() != null) {
                    if (log.isDebugEnabled())
                        log.debug("Remove the execution realm mapping for " + cmd.getName());
                    RealmRepository.instance().removeMapping(cmd.getName());
                }
            }
        }
    }

    /**
     * Clears the Repository.
     *
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void clear() throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled())
            log.debug("Clearing...");
        m_classMetaDataListByClassName.clear();
        m_classMetaDataListBySource.clear();
        m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.clear();
        m_cacheOfClassNamesPerRule.clear();
    }

    /**
     * Creates a RuleMetaData instance for the input element.
     */
    private RuleMetaData createRuleMetaData(Element element, ClassMetaData cmd, PropertyMetaData pmd) {
        RuleMetaData rmd = new RuleMetaData();
        rmd.setClassMetaData(cmd);
        rmd.setPropertyMetaData(pmd);
        rmd.setName(element.getNodeName());
        rmd.setSource(cmd.getSource());
        rmd.setLine(getLine(element));
        rmd.setText(getText(element));

        // Add all attributes to a Map
        Map<String, String> parameters = new HashMap<String, String>();
        attributesToMap(element, parameters);

        // Get any inner elements as properties
        Element[] children = getChildren(element);
        for (Element child : children)
            parameters.put(child.getNodeName(), getText(child));

        // Stamp the variation from the parameters if provided, else get it from the property
        rmd.setVariation(parameters.containsKey(ATTR_VARIATION) ? parameters.remove(ATTR_VARIATION) :
                pmd != null ? pmd.getVariation() : cmd.getVariation());

        // Apply 'condition' from property/class, but only if it is not defined for the rule
        if (!parameters.containsKey(RuleMetaData.PARAMETER_CONDITION)) {
            String condition = pmd != null ? pmd.getCondition() : cmd.getCondition();
            if (condition != null)
                parameters.put(RuleMetaData.PARAMETER_CONDITION, condition);
        }

        // Apply 'language' from property/class, but only if it is not defined for the rule
        if (!parameters.containsKey(RuleMetaData.PARAMETER_LANGUAGE)) {
            String language = pmd != null ? pmd.getLanguage() : cmd.getLanguage();
            if (language != null)
                parameters.put(RuleMetaData.PARAMETER_LANGUAGE, language);
        }

        rmd.addParameters(parameters);
        return rmd;
    }

    //---------------------------------------------------------------------------------
    //
    //   THESE METHODS SHOULD BE INVOKED ONLY AFTER ALL THE METADATA HAS BEEN LOADED
    //
    //----------------------------------------------------------------------------------

    /**
     * Returns repository containing List of ClassMetaData instances per className.
     *
     * @return repository containing List of ClassMetaData instances per className.
     */
    public Map<String, List<ClassMetaData>> getRepository() {
        return m_classMetaDataListByClassName;
    }

    /**
     * Returns a List of ClassMetaData instances defined for the input class name.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param className the class name.
     * @return a List of ClassMetaData instances defined for the input class name.
     */
    public List<ClassMetaData> getClassMetaDataListByClassName(String className) {
        return m_classMetaDataListByClassName.get(className);
    }

    /**
     * Returns a List of ClassMetaData instances defined in the input source file.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param source the name of the source file.
     * @return a List of ClassMetaData instances defined in the input source file.
     */
    public List<ClassMetaData> getClassMetaDataListBySource(String source) {
        return m_classMetaDataListBySource.get(source);
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the input className+ruleName combination.
     * The class-level RuleMetaData instances defined for the input className+ruleName combination will be added to the Map with propertyName null.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param className the class name.
     * @param ruleName  the rule name.
     * @return a Map containing a List of RuleMetaData instances per propertyName for the input className+ruleName combination.
     */
    public Map<String, List<RuleMetaData>> getPropertyRuleMap(String className, String ruleName) {
        ClassRuleKey key = new ClassRuleKey(className, ruleName);
        if (!m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.containsKey(key)) {
            synchronized (m_cacheOfPropertyRuleMetaDataMapByClassRuleKey) {
                if (!m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.containsKey(key)) {
                    Map<String, List<RuleMetaData>> map = null;
                    List<ClassMetaData> classMetaDataList = getClassMetaDataListByClassName(className);
                    if (classMetaDataList != null) {
                        for (ClassMetaData cmd : classMetaDataList) {
                            // Check class-level rules
                            List<RuleMetaData> classRules = cmd.getRules();
                            if (classRules != null) {
                                for (RuleMetaData rule : classRules) {
                                    if (ruleName.equals(rule.getName()))
                                        map = addRule(map, null, rule);
                                }
                            }

                            // Check property-level rules
                            List<PropertyMetaData> classProperties = cmd.getProperties();
                            if (classProperties != null) {
                                for (PropertyMetaData pmd : classProperties) {
                                    List<RuleMetaData> propertyRules = pmd.getRules(className);
                                    if (propertyRules != null) {
                                        for (RuleMetaData rule : propertyRules) {
                                            if (ruleName.equals(rule.getName()))
                                                map = addRule(map, pmd.getName(), rule);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.put(key, map);
                    return map;
                }
            }
        }
        return m_cacheOfPropertyRuleMetaDataMapByClassRuleKey.get(key);
    }

    /**
     * Returns an array of classNames that have the input rule.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param ruleName the rule name.
     * @return an array of classNames that have the input rule.
     */
    public String[] getClassNamesByRuleName(String ruleName) {
        if (m_cacheOfClassNamesPerRule.isEmpty()) {
            synchronized (m_cacheOfClassNamesPerRule) {
                if (m_cacheOfClassNamesPerRule.isEmpty()) {
                    for (List<ClassMetaData> cmdList : m_classMetaDataListByClassName.values()) {
                        for (ClassMetaData cmd : cmdList) {
                            String className = cmd.getName();
                            List<RuleMetaData> classRules = cmd.getRules();
                            if (classRules != null) {
                                for (RuleMetaData ruleMetaData : classRules) {
                                    String name = ruleMetaData.getName();
                                    if (!m_cacheOfClassNamesPerRule.containsKey(name)) {
                                        List<String> classNames = new ArrayList<>();
                                        classNames.add(className);
                                        m_cacheOfClassNamesPerRule.put(name, classNames);
                                    } else if (!m_cacheOfClassNamesPerRule.get(name).contains(className)) {
                                        m_cacheOfClassNamesPerRule.get(name).add(className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // return an array for backwards compatibility
        List<String> classNames = m_cacheOfClassNamesPerRule.get(ruleName);
        if (classNames == null) {
            return null;
        }
        return classNames.toArray(new String[classNames.size()]);
    }

    /**
     * Returns a List of RuleMetaData instances defined for the input className+propertyName+ruleName combination.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param className    the class name.
     * @param propertyName the propertyName name.
     * @param ruleName     the rule name.
     * @return a List of RuleMetaData instances defined for the input className+propertyName+ruleName combination.
     */
    public List<RuleMetaData> getRuleList(String className, String propertyName, String ruleName) {
        Map<String, List<RuleMetaData>> ruleMap = getPropertyRuleMap(className, ruleName);
        return ruleMap != null ? ruleMap.get(propertyName) : null;
    }

    /**
     * Returns a List of PropertyMetaData instances defined for the input property.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param className    the class name.
     * @param propertyName the property name.
     * @return a List of PropertyMetaData instances defined for the input property.
     */
    List<PropertyMetaData> getPropertyMetaDataListByClassName(String className, String propertyName) {
        List<PropertyMetaData> propertyMetaDataList = new LinkedList<PropertyMetaData>();
        List<ClassMetaData> classMetaDataList = m_classMetaDataListByClassName.get(className);
        if (classMetaDataList != null) {
            for (ClassMetaData cmd : classMetaDataList) {
                List<PropertyMetaData> classProperties = cmd.getProperties();
                if (classProperties != null) {
                    for (PropertyMetaData pmd : classProperties) {
                        if (propertyName.equals(pmd.getName()))
                            propertyMetaDataList.add(pmd);
                    }
                }
            }
        }
        return propertyMetaDataList;
    }

    /**
     * Adds the input rule into a List for the given property in the given Map.
     */
    private Map<String, List<RuleMetaData>> addRule(Map<String, List<RuleMetaData>> map, String propertyName, RuleMetaData rule) {
        if (map == null)
            map = new LinkedHashMap<String, List<RuleMetaData>>();
        List<RuleMetaData> list = map.get(propertyName);
        if (list == null) {
            list = new LinkedList<RuleMetaData>();
            map.put(propertyName, list);
        }
        list.add(rule);
        return map;
    }

    @Override
    public Set<String> getPropertyNames(String className, String ruleName) {
        Set<String> names = new ListSet();
        Map propertyRuleMap = getPropertyRuleMap(className, ruleName);
        if (propertyRuleMap != null) {
            names = propertyRuleMap.keySet();
        }
        return names;
    }

    //---------------------------------------------------------------------------------
    //
    //   CONVENIENCE INNER CLASSES FOR RETRIEVING RULES
    //
    //----------------------------------------------------------------------------------

    /**
     * This class is used to cache class-level rules by className+ruleName combination.
     */
    private static class ClassRuleKey implements Cloneable, Comparable<ClassRuleKey>, Serializable {

        private String m_className;
        private String m_ruleName;

        /**
         * Creates a new intsance.
         *
         * @param className the class name.
         * @param ruleName  the rule name.
         */
        public ClassRuleKey(String className, String ruleName) {
            m_className = className;
            m_ruleName = ruleName;
        }

        /**
         * Getter for property className.
         *
         * @return Value of property className.
         */
        public String getClassName() {
            return m_className;
        }

        /**
         * Getter for property ruleName.
         *
         * @return Value of property ruleName.
         */
        public String getRuleName() {
            return m_ruleName;
        }

        /**
         * Returns a clone of the object.
         *
         * @return a clone of the object.
         */
        @Override
        public Object clone() {
            try {
                return super.clone();
                // no more processing required since the fields are immutable
            } catch (CloneNotSupportedException e) {
                // this shouldn't happen, since we are Cloneable
                return null;
            }
        }

        /**
         * Compares this object with another ClassRuleKey object.
         *
         * @param obj the other ClassRuleKey object.
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
         */
        public int compareTo(ClassRuleKey obj) {
            int i = m_className != null ? (obj.m_className != null ? m_className.compareTo(obj.m_className) : 1) : (obj.m_className != null ? -1 : 0);
            if (i != 0)
                return i;
            i = m_ruleName != null ? (obj.m_ruleName != null ? m_ruleName.compareTo(obj.m_ruleName) : 1) : (obj.m_ruleName != null ? -1 : 0);
            return i;
        }

        /**
         * Compares this object with another ClassRuleKey object.
         * Returns a true if both the objects have the same className and ruleName.
         *
         * @param obj the other ClassRuleKey object.
         * @return a true if both the objects have the same className and ruleName.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof ClassRuleKey) {
                ClassRuleKey target = (ClassRuleKey) obj;
                return (m_className != null ? m_className.equals(target.m_className) : target.m_className == null)
                        && (m_ruleName != null ? m_ruleName.equals(target.m_ruleName) : target.m_ruleName == null);
            } else {
                return false;
            }
        }

        /**
         * Returns an int which will be the sum of the of the hashcodes of the className and ruleName.
         *
         * @return an int which will be the sum of the of the hashcodes of the className and ruleName.
         */
        @Override
        public int hashCode() {
            return (m_className != null ? m_className.hashCode() : 0) + (m_ruleName != null ? m_ruleName.hashCode() : 0);
        }

        /**
         * Returns the diagnostic information.
         *
         * @return the diagnostic information.
         */
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder("<ClassRuleKey>")
                    .append("<className>").append(m_className).append("</className>")
                    .append("<ruleName>").append(m_ruleName).append("</ruleName>")
                    .append("</ClassRuleKey>");
            return buf.toString();
        }
    }

}
