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
import org.jaffa.rules.commons.MetaData;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.Rule;
import org.jaffa.rules.rulemeta.RuleRepository;

import java.util.*;

/**
 * Encapsulates all the class-level rules and properties defined for a class.
 * <p/>
 * It has the following properties:
 * <ul>condition: Optional. The rules defined in this class will be executed only if the condition evaluates to true, unless overridden by another condition at the property/rule level.
 * <ul>language: Optional. The scripting language used in the condition parameter, unless overridden by another language at the property/rule level.
 * <ul>variation: Optional. The MetaData will be applicable to the specified comma-separated list of variations only.
 * <ul>executionRealm: Optional. The execution realm for the rules defined in this class.
 * <ul>extendsClass: Optional. Indicates if rules are to be inherited from the properties of another class.
 * <ul>rules: Optional. The class-level rules.
 * <ul>properties: Optional. The properties for the class.
 * <ul>Check {@link MetaData} for more properties
 */
public class ClassMetaData extends MetaData {

    private static Logger log = Logger.getLogger(ClassMetaData.class);
    private String m_condition;
    private String m_language;
    private String m_variation;
    private String m_executionRealm;
    private String m_extendsClass;
    private List<RuleMetaData> m_rules;
    private List<PropertyMetaData> m_properties;
    private String labelFormat = "[%s]";

    /**
     * Default constructor.
     */
    public ClassMetaData() {
    }

    /**
     * Constructs class meta-data with the specified name.
     *
     * @param name the name.
     */
    public ClassMetaData(String name) {
        setName(name);
    }

    /**
     * Constructs class meta-data with the specified class.
     *
     * @param clazz the class.
     */
    public ClassMetaData(Class<?> clazz) {
        setName(clazz.getName());
    }

    /**
     * Constructs class meta-data with the specified name and extended class.
     *
     * @param name         the name.
     * @param extendsClass the class to extend.
     */
    public ClassMetaData(String name, Class<?> extendsClass) {
        setName(name);
        setExtendsClass(extendsClass.getName());
    }

    /**
     * Constructs class meta-data with the specified class and extended class.
     *
     * @param clazz        the class.
     * @param extendsClass the class to extend.
     */
    public ClassMetaData(Class<?> clazz, Class<?> extendsClass) {
        setName(clazz.getName());
        setExtendsClass(extendsClass.getName());
    }

    /**
     * Iterates through the input rules, returning only those that can be inherited as defined by the 'includes' and 'excludes' attributes of the SUPER rule.
     * All rules will be returned if the 'includes' and 'excludes' attributes are not defined.
     * This method also removes the non-inheritable rules as defined by the 'inheritable' attribute of the rulemeta.
     *
     * @param superRule      the SUPER rule.
     * @param inheritedRules the rules to be inherited.
     * @return inheritable rules.
     */
    static List<RuleMetaData> getInheritableRules(RuleMetaData superRule, List<RuleMetaData> inheritedRules) {
        if (inheritedRules != null && inheritedRules.size() > 0) {
            String includesParam = superRule.getParameter("includes");
            String excludesParam = superRule.getParameter("excludes");
            String[] includes = includesParam != null ? includesParam.split(",") : null;
            String[] excludes = excludesParam != null ? excludesParam.split(",") : null;
            if (includes != null)
                Arrays.sort(includes);
            if (excludes != null)
                Arrays.sort(excludes);
            for (Iterator<RuleMetaData> i = inheritedRules.iterator(); i.hasNext(); ) {
                RuleMetaData inheritedRule = i.next();
                String ruleName = inheritedRule.getName();
                if ((includes != null && Arrays.binarySearch(includes, ruleName) < 0) || (excludes != null && Arrays.binarySearch(excludes, ruleName) >= 0)) {
                    if (log.isDebugEnabled())
                        log.debug(inheritedRule + " excluded as per the includes/excludes definition in " + superRule);
                    i.remove();
                } else {
                    Rule ruleInfo = RuleRepository.instance().getRuleByName(ruleName);
                    if (!ruleInfo.isInheritable()) {
                        if (log.isDebugEnabled())
                            log.debug(inheritedRule + " excluded as per the 'inheritable' attribute in the rulemeta " + ruleInfo);
                        i.remove();
                    }
                }
            }
        }
        return inheritedRules;
    }

    /**
     * Adds this instance to the MetaDataRepository.
     */
    public void register() {
        MetaDataRepository.instance().addClassMetaData(this);
    }

    /**
     * Getter for property condition.
     *
     * @return Value of property condition.
     */
    public String getCondition() {
        return m_condition;
    }

    /**
     * Setter for property condition.
     *
     * @param condition Value of property condition.
     */
    public void setCondition(String condition) {
        m_condition = condition;
    }

    /**
     * Getter for property language.
     *
     * @return Value of property language.
     */
    public String getLanguage() {
        return m_language;
    }

    /**
     * Setter for property language.
     *
     * @param language Value of property language.
     */
    public void setLanguage(String language) {
        m_language = language;
    }

    /**
     * Getter for property variation.
     *
     * @return Value of property variation.
     */
    public String getVariation() {
        return m_variation;
    }

    /**
     * Setter for property variation.
     *
     * @param variation Value of property variation.
     */
    public void setVariation(String variation) {
        m_variation = variation;
    }

    /**
     * Getter for property executionRealm.
     *
     * @return Value of property executionRealm.
     */
    public String getExecutionRealm() {
        return m_executionRealm;
    }

    /**
     * Setter for property executionRealm.
     *
     * @param executionRealm New value of property executionRealm.
     */
    public void setExecutionRealm(String executionRealm) {
        m_executionRealm = executionRealm;
    }

    /**
     * Getter for property extendsClass.
     *
     * @return Value of property extendsClass.
     */
    public String getExtendsClass() {
        return m_extendsClass;
    }

    /**
     * Setter for property extendsClass.
     *
     * @param extendsClass New value of property extendsClass.
     */
    public void setExtendsClass(String extendsClass) {
        m_extendsClass = extendsClass;
    }

    /**
     * Gets the format used for property labels.
     *
     * @return labelFormat the label format (e.g. "[com.xyz.classname.%s]").
     */
    public String getLabelFormat() {
        return labelFormat;
    }

    /**
     * Sets the format used for property labels.
     *
     * @param labelFormat the label format (e.g. "[com.xyz.classname.%s]").
     */
    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    /**
     * Getter for property rules.
     * <p/>
     * NOTE: This is an expensive operation since rules are inherited from the Property identified by the extendsClass/extendsProperty combination, if any.
     * The rules are inherited only if a 'super' rule has been defined for this Property.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @return Value of property rules.
     */
    public List<RuleMetaData> getRules() {
        List<RuleMetaData> rules = getRules(null);

        // Remove the non-inheritable rules as defined for the realm of the target class
        rules = RealmRepository.instance().getInheritableRules(getName(), rules);

        return rules;
    }

    /**
     * Gets the rules specific to this class.
     *
     * @return the list of rules, or null if none have been defined.
     */
    public List<RuleMetaData> getClassRules() {
        return m_rules;
    }

    /**
     * Adds a rule to this property.
     *
     * @param rule a rule.
     */
    public void addRule(RuleMetaData rule) {
        if (m_rules == null)
            m_rules = new LinkedList<RuleMetaData>();
        m_rules.add(rule);
    }

    /**
     * Getter for property properties.
     * <p/>
     * NOTE: This is an expensive operation since properties are inherited from the Class identified by extendsClass, if any.
     * Only the undefined properties are inherited for this Class.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @return Value of property properties.
     */
    public List<PropertyMetaData> getProperties() {
        return getProperties(null);
    }

    /**
     * Adds a property to this property.
     *
     * @param property a property.
     */
    public void addProperty(PropertyMetaData property) {
        if (m_properties == null)
            m_properties = new LinkedList<PropertyMetaData>();
        m_properties.add(property);
    }

    /**
     * Gets or creates a PropertyMetaData object with the specified name.
     *
     * @param name the PropertyMetaData's name.
     * @return the PropertyMetaData object.
     */
    public PropertyMetaData property(String name) {
        if (m_properties == null) {
            m_properties = new LinkedList<>();
        }

        for (PropertyMetaData property : m_properties) {
            if (name.equals(property.getName())) {
                return property;
            }
        }

        PropertyMetaData property = new PropertyMetaData();
        property.setName(name);
        applyDefaults(property);
        addProperty(property);

        return property;
    }

    /**
     * Creates a PropertyMetaData object that extends another property. This will have the "super" rule added.
     *
     * @param name         the property's name.
     * @param extendsClass the class of the extended property.
     * @return the new property.
     */
    public PropertyMetaData property(String name, Class<?> extendsClass) {
        return property(name, extendsClass, null);
    }

    /**
     * Creates a PropertyMetaData object that extends another property. This will have the "super" rule added.
     *
     * @param name            the property's name.
     * @param extendsClass    the class of the extended property.
     * @param extendsProperty the extended property.
     * @return the new property.
     */
    public PropertyMetaData property(String name, Class<?> extendsClass, String extendsProperty) {
        return property(name)
                .extendsClass(extendsClass.getName())
                .extendsProperty(extendsProperty)
                .rule(RuleMetaData.RULE_SUPER, null, null);
    }

    /**
     * Creates a PropertyMetaData object that extends another property. This will have the "super" rule added.
     *
     * @param name            the property's name.
     * @param extendsClass    the class of the extended property.
     * @param extendsProperty the extended property.
     * @param superExcludes   the "excludes" parameter for the "super" rule.
     * @return the new property.
     */
    public PropertyMetaData property(String name, Class<?> extendsClass, String extendsProperty, String superExcludes) {
        return property(name)
                .extendsClass(extendsClass.getName())
                .extendsProperty(extendsProperty)
                .rule(RuleMetaData.RULE_SUPER, null, null, "excludes", superExcludes);
    }

    /**
     * Adds a label rule.
     *
     * @param value the value.
     * @return this class meta-data.
     */
    public ClassMetaData label(String value) {
        return rule("label", value, null);
    }

    /**
     * Adds a "delete-domain-info" rule.
     *
     * @param name the delete domain name.
     * @return this class meta-data.
     */
    public ClassMetaData deleteDomainInfo(String name) {
        return rule("delete-domain-info", null, null, "name", name);
    }

    /**
     * Add a rule.
     *
     * @param name      the name of the rule.
     * @param value     the value.
     * @param condition the condition.
     * @param params    key-value pairs of parameters for the rule.
     * @return this class meta-data.
     */
    public ClassMetaData rule(String name, String value, String condition, String... params) {
        RuleMetaData rule = newRule(name, value, condition);
        for (int i = 0; i < params.length; i += 2) {
            rule.addParameter(params[i], params[i + 1]);
        }
        return this;
    }

    /**
     * Applies default values if they're not already set in the property.
     *
     * @param property the property.
     */
    private void applyDefaults(PropertyMetaData property) {
        property.setClassMetaData(this);
        property.setCondition(m_condition);
        property.setLanguage(m_language);
        property.setVariation(m_variation);
        property.setExtendsClass(m_extendsClass);
        property.setExtendsProperty(property.getName());
        property.setSource(getSource());
        property.setLine(getLine());
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("<class name='").append(getName()).append('\'');
        if (getCondition() != null)
            buf.append(" condition='").append(getCondition()).append('\'');
        if (getLanguage() != null)
            buf.append(" language='").append(getLanguage()).append('\'');
        if (getVariation() != null)
            buf.append(" variation='").append(getVariation()).append('\'');
        if (getExecutionRealm() != null)
            buf.append(" executionRealm='").append(getExecutionRealm()).append('\'');
        if (getExtendsClass() != null)
            buf.append(" extendsClass='").append(getExtendsClass()).append('\'');
        buf.append(super.toString());
        if ((m_rules != null && m_rules.size() > 0) || (m_properties != null && m_properties.size() > 0)) {
            buf.append(">");
            if (m_rules != null) {
                for (RuleMetaData rule : m_rules)
                    buf.append(rule);
            }
            if (m_properties != null) {
                for (PropertyMetaData property : m_properties)
                    buf.append(property);
            }
            buf.append("</class>");
        } else
            buf.append("/>");
        return buf.toString();
    }

    /**
     * Getter for property rules.
     * <p/>
     * NOTE: This is an expensive operation since rules are inherited from the Class identified by extendsClass, if any.
     * The class-level rules are inherited only if a 'super' rule has been defined for this Class.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @param classesBeingScanned To avoid circular references, maintain a stack of ClassMetaData instances being scanned.
     * @return Value of property rules.
     */
    private List<RuleMetaData> getRules(Stack<ClassMetaData> classesBeingScanned) {
        List<RuleMetaData> rules = m_rules != null ? new LinkedList<RuleMetaData>(m_rules) : null;
        if (getExtendsClass() != null && rules != null && rules.size() > 0) {
            for (ListIterator<RuleMetaData> litr = rules.listIterator(); litr.hasNext(); ) {
                RuleMetaData rule = litr.next();
                if (rule.getName().equals(RuleMetaData.RULE_SUPER)) {
                    List<ClassMetaData> superClasses = MetaDataRepository.instance().getClassMetaDataListByClassName(getExtendsClass());
                    if (superClasses != null && superClasses.size() > 0) {
                        // The stack will need to be initialized just once, and should not happen in the recursive call
                        if (classesBeingScanned == null) {
                            classesBeingScanned = new Stack<ClassMetaData>();
                            classesBeingScanned.push(this);
                        }

                        for (ClassMetaData superClass : superClasses) {
                            // Check for circular reference by searching for the super class in the stack
                            if (classesBeingScanned.search(superClass) > 0) {
                                StringBuilder buf = new StringBuilder("Circular Reference:").append('\n').append('\t').append(superClass);
                                for (ListIterator<ClassMetaData> i = classesBeingScanned.listIterator(classesBeingScanned.size()); i.hasPrevious(); )
                                    buf.append('\n').append('\t').append(i.previous());
                                log.warn(buf);
                                break;
                            }

                            // Push the super class onto the stack
                            classesBeingScanned.push(superClass);

                            // Recursively get the rules from the super class
                            List<RuleMetaData> superRules = superClass.getRules(classesBeingScanned);

                            // Remove the non-inheritable rules as defined for the SUPER rule, as well as by the 'inheritable' attribute of the rulemeta
                            superRules = getInheritableRules(rule, superRules);

                            // Pop the super class from the stack
                            classesBeingScanned.pop();

                            // Insert the rules from the super class immediately after the 'super' rule
                            if (superRules != null) {
                                for (RuleMetaData superRule : superRules)
                                    litr.add(superRule);
                            }
                        }
                    }

                    // Break out of the loop. Any subsequent 'super' rules will be ignored
                    break;
                }
            }
        }

        // Validate all the rules, removing SUPER rules if any
        if (rules != null) {
            for (Iterator<RuleMetaData> itr = rules.iterator(); itr.hasNext(); ) {
                RuleMetaData rule = itr.next();
                try {
                    if (rule.getName().equals(RuleMetaData.RULE_SUPER))
                        itr.remove();
                    else
                        rule.validate();
                } catch (Exception e) {
                    log.warn("Error in validating the rule: " + rule, e);
                    itr.remove();
                }
            }
        }
        return rules;
    }

    /**
     * Getter for property properties.
     * <p/>
     * NOTE: This is an expensive operation since properties are inherited from the Class identified by extendsClass, if any.
     * Only the undefined properties are inherited for this Class.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @param classesBeingScanned To avoid circular references, maintain a stack of ClassMetaData instances being scanned.
     * @return Value of property properties.
     */
    private List<PropertyMetaData> getProperties(Stack<ClassMetaData> classesBeingScanned) {
        List<PropertyMetaData> properties = m_properties != null ? new LinkedList<PropertyMetaData>(m_properties) : null;
        if (getExtendsClass() != null) {
            List<ClassMetaData> superClasses = MetaDataRepository.instance().getClassMetaDataListByClassName(getExtendsClass());
            if (superClasses != null && superClasses.size() > 0) {
                // The stack will need to be initialized just once, and should not happen in the recursive call
                if (classesBeingScanned == null) {
                    classesBeingScanned = new Stack<ClassMetaData>();
                    classesBeingScanned.push(this);
                }

                // Create a list containing the propertyNames originally defined for this class
                Collection<String> originalPropertyNames = null;
                if (m_properties != null && m_properties.size() > 0) {
                    originalPropertyNames = new HashSet<String>();
                    for (PropertyMetaData property : m_properties)
                        originalPropertyNames.add(property.getName());
                }

                for (ClassMetaData superClass : superClasses) {
                    // Check for circular reference by searching for the super class in the stack
                    if (classesBeingScanned.search(superClass) > 0) {
                        StringBuilder buf = new StringBuilder("Circular Reference:").append('\n').append('\t').append(superClass);
                        for (ListIterator<ClassMetaData> i = classesBeingScanned.listIterator(classesBeingScanned.size()); i.hasPrevious(); )
                            buf.append('\n').append('\t').append(i.previous());
                        log.warn(buf);
                        break;
                    }

                    // Push the super class onto the stack
                    classesBeingScanned.push(superClass);

                    // Recursively get the properties from the super class
                    List<PropertyMetaData> superProperties = superClass.getProperties(classesBeingScanned);

                    // Pop the super class from the stack
                    classesBeingScanned.pop();

                    // Inherit the undefined properties from the super class
                    if (superProperties != null) {
                        for (PropertyMetaData superProperty : superProperties) {
                            if (originalPropertyNames == null || !originalPropertyNames.contains(superProperty.getName())) {
                                if (properties == null)
                                    properties = new LinkedList<PropertyMetaData>();
                                properties.add(superProperty);
                            }
                        }
                    }
                }
            }
        }
        return properties;
    }

    /**
     * Add a rule.
     *
     * @param name      the name of the rule.
     * @param value     the value.
     * @param condition the condition.
     * @return this property meta-data.
     */
    private RuleMetaData newRule(String name, String value, String condition) {
        RuleMetaData rule = new RuleMetaData();
        rule.setName(name);
        rule.addParamIfNonNull(RuleMetaData.PARAMETER_VALUE, value);
        rule.addParamIfNonNull(RuleMetaData.PARAMETER_CONDITION, condition);
        applyDefaults(rule);
        addRule(rule);
        return rule;
    }

    /**
     * Applies default values if they're not already set in the rule.
     *
     * @param rule the rule.
     */
    private void applyDefaults(RuleMetaData rule) {
        rule.setClassMetaData(this);
        rule.setSource(getSource());
        rule.setLine(getLine());

        if (rule.getVariation() == null && m_variation != null) {
            rule.setVariation(m_variation);
        }

        if (rule.getParameter(RuleMetaData.PARAMETER_CONDITION) == null) {
            rule.addParamIfNonNull(RuleMetaData.PARAMETER_CONDITION, m_condition);
        }

        if (rule.getParameter(RuleMetaData.PARAMETER_LANGUAGE) == null) {
            rule.addParamIfNonNull(RuleMetaData.PARAMETER_LANGUAGE, m_language);
        }
    }
}
