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
import org.jaffa.util.StringHelper;

import java.util.*;

/**
 * Encapsulates all the rules defined for a property.
 * <p/>
 * It has the following properties:
 * <ul>condition: Optional. The rules defined in this property will be executed only if the condition evaluates to true, unless overridden by another condition at the rule level.
 * <ul>language: Optional. The scripting language used in the condition parameter, unless overridden by another language at the rule level.
 * <ul>variation: Optional. The MetaData will be applicable to the specified comma-separated list of variations only.
 * <ul>extendsClass, extendsProperty: Optional. Indicates if rules are to be inherited from another property. Both extendsClass and extendsProperty need to be provided for this feature.
 * <ul>rules: Optional. The rules for a Property.
 * <ul>Check {@link MetaData} for more properties.
 * <p/>
 * It maintains a reference to the ClassMetaData instance within which it is defined.
 */
public class PropertyMetaData extends MetaData {

    private static Logger log = Logger.getLogger(PropertyMetaData.class);
    private ClassMetaData m_classMetaData;
    private String m_condition;
    private String m_language;
    private String m_variation;
    private String m_extendsClass;
    private String m_extendsProperty;
    private List<RuleMetaData> m_rules;

    /**
     * Setter for property name.
     *
     * @param name New value of property name.
     */
    @Override
    public void setName(String name) {
        super.setName(StringHelper.getJavaBeanStyle(name));
    }

    /**
     * Getter for property classMetaData.
     *
     * @return Value of property classMetaData.
     */
    public ClassMetaData getClassMetaData() {
        return m_classMetaData;
    }

    /**
     * Setter for property classMetaData.
     *
     * @param classMetaData New value of property classMetaData.
     */
    public void setClassMetaData(ClassMetaData classMetaData) {
        m_classMetaData = classMetaData;
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
     * Sets the extendsClass.
     *
     * @param extendsClass the extendsClass.
     * @return this property meta-data.
     */
    public PropertyMetaData extendsClass(String extendsClass) {
        setExtendsClass(extendsClass);
        return this;
    }

    /**
     * Getter for property extendsProperty.
     *
     * @return Value of property extendsProperty.
     */
    public String getExtendsProperty() {
        return m_extendsProperty;
    }

    /**
     * Setter for property extendsProperty.
     *
     * @param extendsProperty New value of property extendsProperty.
     */
    public void setExtendsProperty(String extendsProperty) {
        m_extendsProperty = StringHelper.getJavaBeanStyle(extendsProperty);
    }

    /**
     * Sets the extendsProperty.
     *
     * @param extendsProperty the extendsProperty.
     * @return this property meta-data.
     */
    public PropertyMetaData extendsProperty(String extendsProperty) {
        setExtendsProperty(extendsProperty);
        return this;
    }

    /**
     * Getter for property rules.
     * <p/>
     * NOTE: This is an expensive operation since rules are inherited from the Property identified by the extendsClass/extendsProperty combination, if any.
     * The rules are inherited only if a 'super' rule has been defined for this Property.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @param targetClassName the class for which the rules are being determined. This is used for filtering the non-inheritable rules, based on the realm of the target class.
     * @return Value of property rules.
     */
    public List<RuleMetaData> getRules(String targetClassName) {
        List<RuleMetaData> rules = getRules((Stack<PropertyMetaData>) null);

        // Remove the non-inheritable rules as defined for the realm of the target class
        rules = RealmRepository.instance().getInheritableRules(targetClassName, rules);

        return rules;
    }

    /**
     * Gets the rules that apply only to this property, not those from inherited elements.
     *
     * @return the rules, or null if none have been defined.
     */
    public List<RuleMetaData> getRules() {
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
     * Returns debug info.
     *
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("<property name='").append(getName()).append('\'');
        if (getCondition() != null)
            buf.append(" condition='").append(getCondition()).append('\'');
        if (getLanguage() != null)
            buf.append(" language='").append(getLanguage()).append('\'');
        if (getVariation() != null)
            buf.append(" variation='").append(getVariation()).append('\'');
        if (getExtendsClass() != null)
            buf.append(" extendsClass='").append(getExtendsClass()).append('\'');
        if (getExtendsProperty() != null)
            buf.append(" extendsProperty='").append(getExtendsProperty()).append('\'');
        buf.append(super.toString());
        if (m_rules != null && m_rules.size() > 0) {
            buf.append(">");
            for (RuleMetaData rule : m_rules)
                buf.append(rule);
            buf.append("</property>");
        } else
            buf.append("/>");
        return buf.toString();
    }

    /**
     * Getter for property rules.
     * <p/>
     * NOTE: This is an expensive operation since rules are inherited from the Property identified by the extendsClass/extendsProperty combination, if any.
     * The rules are inherited only if a 'super' rule has been defined for this Property.
     * The inheritance logic is performed on every invocation and hence should be used with caution.
     *
     * @param propertiesBeingScanned To avoid circular references, maintain a stack of PropertyMetaData instances being scanned.
     * @return Value of property rules.
     */
    private List<RuleMetaData> getRules(Stack<PropertyMetaData> propertiesBeingScanned) {
        List<RuleMetaData> rules = m_rules != null ? new LinkedList<RuleMetaData>(m_rules) : null;
        if (getExtendsClass() != null && getExtendsProperty() != null && rules != null && rules.size() > 0) {
            for (ListIterator<RuleMetaData> litr = rules.listIterator(); litr.hasNext(); ) {
                RuleMetaData rule = litr.next();
                if (rule.getName().equals(RuleMetaData.RULE_SUPER)) {
                    List<PropertyMetaData> superProperties = MetaDataRepository.instance().getPropertyMetaDataListByClassName(getExtendsClass(), getExtendsProperty());
                    if (superProperties != null && superProperties.size() > 0) {
                        // The stack will need to be initialized just once, and should not happen in the recursive call
                        if (propertiesBeingScanned == null) {
                            propertiesBeingScanned = new Stack<PropertyMetaData>();
                            propertiesBeingScanned.push(this);
                        }

                        for (PropertyMetaData superProperty : superProperties) {
                            // Check for circular reference by searching for the super property in the stack
                            if (propertiesBeingScanned.search(superProperty) > 0) {
                                StringBuilder buf = new StringBuilder("Circular Reference:").append('\n').append('\t').append(superProperty);
                                for (ListIterator<PropertyMetaData> i = propertiesBeingScanned.listIterator(propertiesBeingScanned.size()); i.hasPrevious(); )
                                    buf.append('\n').append('\t').append(i.previous());
                                log.warn(buf);
                                break;
                            }

                            // Push the super property onto the stack
                            propertiesBeingScanned.push(superProperty);

                            // Recursively get the rules from the super property
                            List<RuleMetaData> superRules = superProperty.getRules(propertiesBeingScanned);

                            // Remove the non-inheritable rules as defined for the SUPER rule, as well as by the 'inheritable' attribute of the rulemeta
                            superRules = ClassMetaData.getInheritableRules(rule, superRules);

                            // Pop the super property from the stack
                            propertiesBeingScanned.pop();

                            // Insert the rules from the super property immediately after the 'super' rule
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
     * Adds a label rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData label(String value) {
        return label(value, null);
    }

    /**
     * Adds a label rule.
     *
     * @param value     the value.
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData label(String value, RuleCondition<T> condition) {
        return rule("label", value, condition);
    }

    /**
     * Formatting a label means creating a new label using the class meta-data format.
     * This method will use the name of this property as the input name, but use upper-case for the first character.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData formatLabel() {
        String partialName = Character.toUpperCase(getName().charAt(0)) + getName().substring(1);
        return formatLabel(partialName);
    }

    /**
     * Formatting a label means creating a new label using the class meta-data format.
     *
     * @param partialName the portion of the label name used in the format.
     * @return this property meta-data.
     */
    public PropertyMetaData formatLabel(String partialName) {
        return formatLabel(partialName, null);
    }

    /**
     * Formatting a label means creating a new label using the class meta-data format.
     *
     * @param partialName the portion of the label name used in the format.
     * @param condition   the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData formatLabel(String partialName, RuleCondition<T> condition) {
        return label(String.format(m_classMetaData.getLabelFormat(), partialName), condition);
    }

    /**
     * Adds a mandatory rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData mandatory() {
        return rule("mandatory", null, null);
    }

    /**
     * Adds a max-length rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData maxLength(Number value) {
        return maxLength(value.toString());
    }

    /**
     * Adds a max-length rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData maxLength(String value) {
        return rule("max-length", value, null);
    }

    /**
     * Adds a min-length rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData minLength(Number value) {
        return minLength(value.toString());
    }

    /**
     * Adds a min-length rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData minLength(String value) {
        return rule("min-length", value, null);
    }

    /**
     * Adds a case-type rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData upperCase() {
        return caseType("upper");
    }

    /**
     * Adds a case-type rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData lowerCase() {
        return caseType("lower");
    }

    /**
     * Adds a case-type rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData mixedCase() {
        return caseType("mixed");
    }

    /**
     * Adds a case-type rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData caseType(String value) {
        return rule("case-type", value, null);
    }

    /**
     * Adds a max-value rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData maxValue(Number value) {
        return maxValue(value.toString());
    }

    /**
     * Adds a max-value rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData maxValue(String value) {
        return rule("max-value", value, null);
    }

    /**
     * Adds a min-value rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData minValue(Number value) {
        return minValue(value.toString());
    }

    /**
     * Adds a min-value rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData minValue(String value) {
        return rule("min-value", value, null);
    }

    /**
     * Adds an in-list rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData inList(String value) {
        return inList(value, null);
    }

    /**
     * Adds an in-list rule.
     *
     * @param value     the value.
     * @param errorCode the errorCode
     * @return this property meta-data.
     */
    public PropertyMetaData inList(String value, String errorCode) {
        RuleMetaData rule = newRule("in-list", value, null);
        rule.addParamIfNonNull("errorCode", errorCode);
        return this;
    }

    /**
     * Adds a not-in-list rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData notInList(String value) {
        return notInList(value, null);
    }

    /**
     * Adds a not-in-list rule.
     *
     * @param value     the value.
     * @param errorCode the errorCode
     * @return this property meta-data.
     */
    public PropertyMetaData notInList(String value, String errorCode) {
        RuleMetaData rule = newRule("not-in-list", value, null);
        rule.addParamIfNonNull("errorCode", errorCode);
        return this;
    }

    /**
     * Adds a comment rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData comment(String value) {
        return comment(value, null);
    }

    /**
     * Adds a comment rule.
     *
     * @param value     the value.
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData comment(String value, RuleCondition<T> condition) {
        return rule("comment", value, null);
    }

    /**
     * Adds a comment rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData pattern(String value) {
        return rule("pattern", value, null);
    }

    /**
     * Adds a foreign-key rule.
     *
     * @param domainObject the fully qualified name of the class against which the foreign key check is to be performed.
     * @return this property meta-data.
     */
    public PropertyMetaData foreignKey(String domainObject) {
        RuleMetaData rule = newRule("foreign-key", null, null);
        rule.addParamIfNonNull("domainObject", domainObject);
        return this;
    }

    /**
     * Adds a generic foreign-key rule.
     *
     * @param tableName used to lookup the set of valid values from the Generic Foreign Key table.
     * @param fieldName used to lookup the set of valid values from the Generic Foreign Key table.
     * @return this property meta-data.
     */
    public PropertyMetaData genericForeignKey(String tableName, String fieldName) {
        return genericForeignKey(tableName, fieldName, null);
    }

    /**
     * Adds a generic foreign-key rule.
     *
     * @param tableName used to lookup the set of valid values from the Generic Foreign Key table.
     * @param fieldName used to lookup the set of valid values from the Generic Foreign Key table.
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData genericForeignKey(String tableName, String fieldName, RuleCondition<T> condition) {
        return genericForeignKey(tableName, fieldName, condition, null);
    }

    /**
     * Adds a generic foreign-key rule.
     *
     * @param tableName       used to lookup the set of valid values from the Generic Foreign Key table.
     * @param fieldName       used to lookup the set of valid values from the Generic Foreign Key table.
     * @param condition       the condition.
     * @param domainClassName the fully qualified name of the domain class representing the Generic Foreign Key table.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData genericForeignKey(String tableName, String fieldName, RuleCondition<T> condition,
                                              String domainClassName) {
        RuleMetaData rule = newRule("generic-foreign-key", null, condition);
        rule.addParamIfNonNull("tableName", tableName);
        rule.addParamIfNonNull("fieldName", fieldName);
        rule.addParamIfNonNull("domainClassName", domainClassName);
        return this;
    }

    /**
     * Adds a partial foreign-key rule.
     *
     * @param domainObject defines the domain object class to validate the field agents.
     * @return this property meta-data.
     */
    public PropertyMetaData partialForeignKey(String domainObject) {
        return partialForeignKey(domainObject, null);
    }

    /**
     * Adds a partial foreign-key rule.
     *
     * @param domainObject defines the domain object class to validate the field agents.
     * @param propertyName defines the property name in the domain object to query on.
     * @return this property meta-data.
     */
    public PropertyMetaData partialForeignKey(String domainObject, String propertyName) {
        RuleMetaData rule = newRule("partial-foreign-key", null, null);
        rule.addParamIfNonNull("domainObject", domainObject);
        rule.addParamIfNonNull("propertyName", propertyName);
        return this;
    }

    /**
     * Adds a primary key rule.
     *
     * @param value The column names that make up the primary key
     * @return the property
     */
    public PropertyMetaData primaryKey(String value) {
        return rule("primary-key", value, null);
    }

    /**
     * Adds a candidate key rule.
     *
     * @param value       The column names that make up the candidate key
     * @param ignoreNulls Ignore Nulls
     * @return the property
     */
    public PropertyMetaData candidateKey(String value, Boolean ignoreNulls) {
        RuleMetaData rule = newRule("candidate-key", value, null);
        rule.addParamIfNonNull("ignore-null", ignoreNulls.toString().toLowerCase());
        return this;
    }

    /**
     * Adds a candidate key rule.  Ignore nulls is defaulted to false.
     *
     * @param value the column names that make up the candidate key
     * @return this property meta-data.
     */
    public PropertyMetaData candidateKey(String value) {
        return rule("candidate-key", value, null);
    }

    /**
     * Adds a lookup rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData lookup() {
        return rule("lookup", null, null);
    }

    /**
     * Adds a hidden rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData hidden() {
        return rule("hidden", null, null);
    }

    /**
     * Adds a hidden rule.
     *
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData hidden(RuleCondition<T> condition) {
        return rule("hidden", null, condition);
    }

    /**
     * Adds a layout rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData layout(String value) {
        return rule("layout", value, null);
    }

    /**
     * Adds a read-only rule.
     *
     * @return this property meta-data.
     */
    public PropertyMetaData readOnly() {
        return readOnly(null);
    }

    /**
     * Adds a read-only rule.
     *
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData readOnly(RuleCondition<T> condition) {
        return rule("read-only", null, condition);
    }

    /**
     * Adds an initialize rule.
     *
     * @param value the value.
     * @return this property meta-data.
     */
    public PropertyMetaData initialize(String value) {
        return initialize(value, null);
    }

    /**
     * Adds an initialize rule.
     *
     * @param value  the value.
     * @param member the member.
     * @return this property meta-data.
     */
    public PropertyMetaData initialize(String value, String member) {
        return initialize(value, member, null);
    }

    /**
     * Adds an initialize rule.
     *
     * @param value     the value.
     * @param member    the member.
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData initialize(String value, String member, RuleCondition<T> condition) {
        return initialize(value, member, condition, null);
    }

    /**
     * Adds an initialize rule.
     *
     * @param value      the value.
     * @param condition  the condition.
     * @param member     the member.
     * @param expression the expression.
     * @param <T>        the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData initialize(String value, String member, RuleCondition<T> condition, String expression) {
        return rule("initialize", value, condition,
                RuleMetaData.PARAMETER_MEMBER, member,
                RuleMetaData.PARAMETER_EXPRESSION, expression);
    }

    /**
     * Add a rule.
     *
     * @param name      the name of the rule.
     * @param value     the value.
     * @param condition the condition.
     * @param params    key-value pairs of parameters for the rule.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    public <T> PropertyMetaData rule(String name, String value, RuleCondition<T> condition, String... params) {
        RuleMetaData rule = newRule(name, value, condition);
        for (int i = 0; i < params.length; i += 2) {
            rule.addParameter(params[i], params[i + 1]);
        }
        return this;
    }

    /**
     * Add a rule.
     *
     * @param name      the name of the rule.
     * @param value     the value.
     * @param condition the condition.
     * @param <T>       the type of the object to evaluate.
     * @return this property meta-data.
     */
    private <T> RuleMetaData newRule(String name, String value, RuleCondition<T> condition) {
        RuleMetaData rule = new RuleMetaData();
        rule.setName(name);
        rule.addParamIfNonNull(RuleMetaData.PARAMETER_VALUE, value);
        rule.setCondition(condition);
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
        rule.setClassMetaData(m_classMetaData);
        rule.setPropertyMetaData(this);
        rule.setSource(getSource());
        rule.setLine(getLine());

        // If the variation is not provided get it from the property.
        if (rule.getVariation() == null) {
            rule.setVariation(m_variation != null ? m_variation : m_classMetaData.getVariation());
        }

        // Apply 'condition' from property/class, but only if it is not defined for the rule.
        if (rule.getParameter(RuleMetaData.PARAMETER_CONDITION) == null) {
            String condition = m_condition != null ? m_condition : m_classMetaData.getCondition();
            rule.addParamIfNonNull(RuleMetaData.PARAMETER_CONDITION, condition);
        }

        // Apply 'language' from property/class, but only if it is not defined for the rule.
        if (rule.getParameter(RuleMetaData.PARAMETER_LANGUAGE) == null) {
            String language = m_language != null ? m_language : m_classMetaData.getLanguage();
            rule.addParamIfNonNull(RuleMetaData.PARAMETER_LANGUAGE, language);
        }
    }
}
