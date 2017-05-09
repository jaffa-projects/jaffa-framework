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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.Parser;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.rulemeta.IFormatter;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.security.VariationContext;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

/** MetaDataIntrospector is a helper class to lookup class-level and property-level rules.
 */
public class MetaDataIntrospector implements IObjectRuleIntrospector, IPropertyRuleIntrospector {

    private static Logger log = Logger.getLogger(MetaDataIntrospector.class);
    private static final Pattern VALUE_AND_LABEL_PATTERN = Pattern.compile("(.*)=(.+)");
    private String m_className = null;
    private String m_propertyName = null;
    private Object m_obj = null;

    /* This array contains the parameters which are not included by the ruleToProperties routine.
     * It mainly contains the parameters common to all rules.
     * NOTE: It is immportant to specify the parameters in the correct sort order.
     */
    private static final String[] PARAMS_TO_EXCLUDE = {"condition", "errorCode", "errorParameters", "language", "variation"};

    /** Creates an instance.
     * @param className The Class being instrospected.
     * @param propertyName The property name. A null should be passed if interested in class-level rules only.
     * @param obj The Object being introspected. Conditions, if specified in any of the rules, will be evaluated only if an Object is passed.
     */
    public MetaDataIntrospector(String className, String propertyName, Object obj) {
        m_className = className;
        m_propertyName = StringHelper.getJavaBeanStyle(propertyName);
        m_obj = obj;
    }

    /** Returns the annotation for the Class/property.
     * @return the annotation for the Class/property.
     */
    public String getAnnotation() {
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "annotation");
        return rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : null;
    }

    /** Returns the audit info for the Class/property.
     * @return the audit info for the Class/property.
     */
    public Properties getAuditInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, "audit");
        if (info != null) {
            Boolean disabled = Parser.parseBoolean(info.getProperty("disabled"));
            if (disabled != null && disabled)
                info = null;
            else {
                if (m_propertyName != null) {
                    // Copy all the property-info, or add the 'name'
                    Properties p = getPropertyInfo();
                    if (p != null)
                        info.putAll(p);
                    else
                        info.setProperty("name", m_propertyName);

                    // Add the data-type
                    Class dataType = getPropertyType();
                    info.setProperty("data-type", dataType != null ? dataType.getName() : String.class.getName());

                    // Add the layout
                    String layout = getLayout();
                    if (layout != null)
                        info.setProperty("layout", layout);
                } else {
                    // Add 'name' to the info by looking up domain-info for the sourceClassName
                    info.setProperty("name", findNameFromDomainInfo(m_className, m_obj));
                }
            }
        }
        return info;
    }

    /** Returns the audit info for all the properties within a Class.
     * @return the audit info for all the properties within a Class.
     */
    public Map<String, Properties> getAuditInfoForProperties() {
        Map<String, Properties> output = null;
        Map<String, RuleMetaData> ruleMap = findPropertyRuleMap(m_className, m_obj, "audit");
        if (ruleMap != null) {
            for (Map.Entry<String, RuleMetaData> me : ruleMap.entrySet()) {
                String propertyName = me.getKey();
                if (propertyName != null) {
                    Properties info = ruleToProperties(me.getValue());

                    //Copy all the property-info
                    Properties propertyInfo = findInfo(m_className, propertyName, m_obj, "property-info");
                    if (propertyInfo != null)
                        info.putAll(propertyInfo);

                    // Add 'name' to the info
                    if (info.getProperty("name") == null)
                        info.setProperty("name", propertyName);

                    // Add the data-type
                    RuleMetaData rule = findRule(m_className, propertyName, m_obj, "data-type");
                    String dataType = rule != null ? Defaults.getClassString(rule.getParameter(RuleMetaData.PARAMETER_VALUE)) : null;
                    if (dataType == null) {
                        // Check the actual Class, if the data-type rule is not defined
                        try {
                            dataType = BeanHelper.getPropertyType(Class.forName(m_className), m_propertyName).getName();
                        } catch (Exception e) {
                            // do nothing
                            if (log.isDebugEnabled())
                                log.debug("Error in determining the property type", e);
                        }
                    }
                    info.setProperty("data-type", dataType != null ? dataType : String.class.getName());

                    // Add the layout
                    rule = findRule(m_className, propertyName, m_obj, "layout");
                    if (rule != null)
                        info.setProperty("layout", rule.getParameter(RuleMetaData.PARAMETER_VALUE));

                    if (output == null)
                        output = new LinkedHashMap<String, Properties>();
                    output.put(propertyName, info);
                }
            }
        }
        return output;
    }

    /** Returns the domain info for the Class.
     * @return the domain info for the Class.
     */
    public Properties getDomainInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, "domain-info");
        if (info != null && info.getProperty("name") == null) {
            String name = StringHelper.getShortClassName(m_className);
            if (name != null)
                info.setProperty("name", name);
        }
        return info;
    }
    

    /**
     * Returns the domain info for the Class.
     *
     * @return the domain info for the Class.
     */
    public Properties getDeleteDomainInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, "delete-domain-info");
        if (info != null && info.getProperty(RuleMetaData.PARAMETER_NAME) != null) {
            info.setProperty(RuleMetaData.PARAMETER_NAME, info.getProperty(RuleMetaData.PARAMETER_NAME));
        }
        return info;
    }
    
    /**
     * Returns the domain info for the Class.
     *
     * @return the domain info for the Class.
     */
    public Properties getBaseDomainInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, "base-domain-info");
        if (info != null && info.getProperty(RuleMetaData.PARAMETER_NAME) != null) {
            info.setProperty(RuleMetaData.PARAMETER_NAME, info.getProperty(RuleMetaData.PARAMETER_NAME));
        }
        return info;
    }

    /** Returns the list of rulemetadata properties for the Class.
     * @return the rulemetadata properties for the Class..
     * @param  ruleName.
     */
    public List<Properties> getMetaDataByRule(String ruleName) {

        if (log.isDebugEnabled()) {
            log.debug("Getting MetaData by rule");
        }
        List<ClassMetaData> clzMetadataList = MetaDataRepository.instance().getClassMetaDataListByClassName(m_className);
        List<Properties> propList = new ArrayList<Properties>();
        if (clzMetadataList != null) {
            for (ClassMetaData classMetaData : clzMetadataList) {
                List<RuleMetaData> ruleMetaDataList = classMetaData.getRules();
                if (ruleMetaDataList != null) {
                    for (RuleMetaData rMetaData : ruleMetaDataList) {
                        if (log.isDebugEnabled()) {
                            log.debug("Available Rules :" + rMetaData.getName() + "for class :" + m_className);
                        }
                        if (ruleName.equals(rMetaData.getName())) {
                            if (log.isDebugEnabled()) {
                                log.debug("Getting property for rule: " + ruleName);
                            }
                            propList.add(ruleToProperties(rMetaData));
                        }
                    }
                }
            }
        }

        return propList;
    }
    
    /** Returns the property-info for all the properties within a Class.
     * @return the property-info for all the properties within a Class.
     */
    public Map<String, Properties> getInfoForProperties() {
        Map<String, Properties> output = null;
        Map<String, RuleMetaData> ruleMap = findPropertyRuleMap(m_className, m_obj, "property-info");
        if (ruleMap != null) {
            for (Map.Entry<String, RuleMetaData> me : ruleMap.entrySet()) {
                String propertyName = me.getKey();
                if (propertyName != null) {
                    Properties info = ruleToProperties(me.getValue());
                    if (info.getProperty("name") == null)
                        info.setProperty("name", propertyName);
                    if (output == null)
                        output = new LinkedHashMap<String, Properties>();
                    output.put(propertyName, info);
                }
            }
        }
        return output;
    }

    /** Returns the flex info for the Class/property.
     * @return the flex info for the Class/property.
     */
    public Properties getFlexInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, m_propertyName == null ? "flex-fields" : "flex-field");
        if (info != null) {
            if (m_propertyName != null) {
                // Copy all the property-info, or add the 'name'
                Properties p = getPropertyInfo();
                if (p != null)
                    info.putAll(p);
                else
                    info.setProperty("name", m_propertyName);

                // Add 'source' to the info
                info.setProperty("source", m_className);

                // Add the data-type
                Class dataType = getPropertyType();
                info.setProperty("data-type", dataType != null ? dataType.getName() : String.class.getName());

                // Add the layout
                String layout = getLayout();
                if (layout != null)
                    info.setProperty("layout", layout);
            } else {
                // Add 'name' to the info by looking up domain-info for the sourceClassName
                info.setProperty("name", findNameFromDomainInfo(info.getProperty("source"), m_obj));
            }
        }
        return info;
    }

    /** Returns all declared flex info for the Class.
     * This method will disregard conditions, if any.
     * @return all declared flex info for the Class.
     */
    public Properties[] getDeclaredFlexInfo() {
        // Returns info for all the rules that pass the variation check
        // Condition and Realm checks will not be performed
        Collection<Properties> output = null;
        Map<String, List<RuleMetaData>> ruleMap = MetaDataRepository.instance().getPropertyRuleMap(m_className, "flex-fields");
        List<RuleMetaData> rules = ruleMap != null ? ruleMap.get(null) : null;
        if (rules != null) {
            outer:
            for (RuleMetaData rule : rules) {
                if (rule.getVariationArray() == null || Arrays.binarySearch(rule.getVariationArray(), VariationContext.getVariation()) >= 0) {
                    if (output == null)
                        output = new LinkedList<Properties>();
                    
                    // Avoid duplication
                    for (Properties p : output) {
                        if (p.getProperty("source").equals(rule.getParameter("source")))
                            continue outer;
                    }

                    // Translate rule to properties and add to output
                    Properties info = this.ruleToProperties(rule);
                    // Add 'name' to the info by looking up domain-info for the sourceClassName
                    info.setProperty("name", findNameFromDomainInfo(info.getProperty("source"), m_obj));
                    output.add(info);
                }
            }
        }
        return output != null ? output.toArray(new Properties[output.size()]) : null;
    }

    /** Returns the flex info for all the properties within a Class.
     * @return the flex info for all the properties within a Class.
     */
    public Map<String, Properties> getFlexInfoForProperties() {
        // Find the source class which contains the flex fields
        Properties p = findInfo(m_className, m_propertyName, m_obj, "flex-fields");
        String sourceClassName = p != null ? p.getProperty("source") : m_className;
        Map<String, Properties> output = null;
        Map<String, RuleMetaData> ruleMap = findPropertyRuleMap(sourceClassName, m_obj, "flex-field");
        if (ruleMap != null) {
            for (Map.Entry<String, RuleMetaData> me : ruleMap.entrySet()) {
                String propertyName = me.getKey();
                if (propertyName != null) {
                    Properties info = ruleToProperties(me.getValue());

                    //Copy all the property-info
                    Properties propertyInfo = findInfo(sourceClassName, propertyName, m_obj, "property-info");
                    if (propertyInfo != null)
                        info.putAll(propertyInfo);

                    // Add 'name' to the info
                    if (info.getProperty("name") == null)
                        info.setProperty("name", propertyName);

                    // Add 'source' to the info
                    info.setProperty("source", sourceClassName);

                    // Add the data-type
                    RuleMetaData rule = findRule(sourceClassName, propertyName, m_obj, "data-type");
                    String dataType = rule != null ? Defaults.getClassString(rule.getParameter(RuleMetaData.PARAMETER_VALUE)) : null;
                    info.setProperty("data-type", dataType != null ? dataType : String.class.getName());

                    // Add the layout
                    rule = findRule(sourceClassName, propertyName, m_obj, "layout");
                    if (rule != null)
                        info.setProperty("layout", rule.getParameter(RuleMetaData.PARAMETER_VALUE));

                    if (output == null)
                        output = new LinkedHashMap<String, Properties>();
                    output.put(propertyName, info);
                }
            }
        }
        return output;
    }

    /** Returns the label for the Class or the Property.
     * @return the label for the Class or the Property.
     */
    public String getLabel() {
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "label");
        return rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : null;
    }

    /** Returns the primary-key for the Class.
     * @return the primary-key for the Class.
     */
    public String[] getPrimaryKey() {
        String[] primaryKey = null;
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "primary-key");
        if (rule != null) {
            String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
            if (value != null)
                primaryKey = value.split(",");
        }
        return primaryKey;
    }

    /** Returns a Class object that identifies the declared type for the property.
     * @return a Class object that identifies the declared type for the property.
     */
    public Class getPropertyType() {
        Class propertyType = null;

        // Try to infer the propertyType by first looking up the data-type rule
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "data-type");
        if (rule != null)
            propertyType = Defaults.getClass(rule.getParameter(RuleMetaData.PARAMETER_VALUE));

        // Check the actual Class, if the data-type rule is not defined
        if (propertyType == null) {
            try {
                propertyType = BeanHelper.getPropertyType(Class.forName(m_className), m_propertyName);
            } catch (Exception e) {
                // do nothing
                if (log.isDebugEnabled())
                    log.debug("Error in determining the property type", e);
            }
        }
        return propertyType;
    }

    /** Returns a true if the property is marked as Hidden.
     * @return a true if the property is marked as Hidden.
     */
    public boolean isHidden() {
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "hidden");
        return rules != null && rules.size() > 0;
    }

    /** Returns a true if the property is marked as ReadOnly.
     * @return a true if the property is marked as ReadOnly.
     */
    public boolean isReadOnly() {
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "read-only");
        return rules != null && rules.size() > 0;
    }

    /** Returns the foreignkey info for the property.
     * @return the foreignkey info for the property.
     */
    public Properties getForeignKeyInfo() {
        Properties p = findInfo(m_className, m_propertyName, m_obj, "foreign-key");
        if (p == null) {
            p = findInfo(m_className, m_propertyName, m_obj, "generic-foreign-key");
            if (p != null) {
                // Mold the generic-foreign-key attributes into equivalent foreign-key attributes
                p.setProperty("domainObject", (String) p.remove("domainClassName"));
                p.setProperty("component", "Jaffa.Setup.ValidFieldValueService");
                p.setProperty("staticParameters", new StringBuilder().append(StringHelper.getJavaBeanStyle((String) p.remove("fieldNameForTable"))).append('=').append(p.remove("tableName")).append(';').append(StringHelper.getJavaBeanStyle((String) p.remove("fieldNameForField"))).append('=').append(p.remove("fieldName")).toString());
                p.setProperty("targetFields", new StringBuilder(m_propertyName).append('=').append(StringHelper.getJavaBeanStyle((String) p.remove("fieldNameForValue"))).toString());
            }
        }
        return p;
    }

    /** Returns the hyperlink info for the property.
     * @return the hyperlink info for the property.
     */
    public Properties getHyperlinkInfo() {
        return findInfo(m_className, m_propertyName, m_obj, "hyperlink");
    }

    /** Returns the info for the property.
     * @return the info for the property.
     */
    public Properties getPropertyInfo() {
        Properties info = findInfo(m_className, m_propertyName, m_obj, "property-info");
        if (info != null) {
            //Always stamp a name
            if (info.getProperty("name") == null)
                info.setProperty("name", m_propertyName);

            // Determine group label by appending the group to the class label
            String group = info.getProperty("display-group");
            if (group != null) {
                RuleMetaData rule = findRule(m_className, null, m_obj, "label");
                String classLabel = rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : "label." + m_className;
                String groupLabel = MessageHelper.removeTokenMarkers(classLabel) + ".group." + group;
                info.setProperty("display-group-label", MessageHelper.findMessage(groupLabel, null));
            }
        }
        return info;
    }

    /** Returns the minLength for the property.
     * @return the minLength for the property.
     */
    public Integer getMinLength() {
        Integer minLength = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "min-length");
        if (rules != null && rules.size() > 0) {
            for (RuleMetaData rule : rules) {
                String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                Integer x = value != null ? new Integer(value) : null;
                minLength = x == null ? minLength : (minLength == null ? x : (x.intValue() > minLength.intValue() ? x : minLength));
            }
        }
        return minLength;
    }

    /** Returns the maxLength for the property.
     * @return the maxLength for the property.
     */
    public Integer getMaxLength() {
        Integer maxLength = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "max-length");
        if (rules != null && rules.size() > 0) {
            for (RuleMetaData rule : rules) {
                String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                String[] lengths = value != null ? value.split(",") : null;
                Integer x = lengths != null && lengths.length > 0 ? new Integer(lengths[0]) : null;
                maxLength = x == null ? maxLength : (maxLength == null ? x : (x.intValue() < maxLength.intValue() ? x : maxLength));
            }
        }
        return maxLength;
    }

    /** Returns the maxFracLength for the property.
     * @return the maxFracLength for the property.
     */
    public Integer getMaxFracLength() {
        Integer maxFracLength = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "max-length");
        if (rules != null && rules.size() > 0) {
            for (RuleMetaData rule : rules) {
                String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                String[] lengths = value != null ? value.split(",") : null;
                Integer x = lengths != null && lengths.length > 1 ? new Integer(lengths[1]) : null;
                maxFracLength = x == null ? maxFracLength : (maxFracLength == null ? x : (x.intValue() < maxFracLength.intValue() ? x : maxFracLength));
            }
        }
        return maxFracLength;
    }

    /** Returns the minValue for the property.
     * @return the minValue for the property.
     */
    public Object getMinValue() {
        Object minValue = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "min-value");
        if (rules != null && rules.size() > 0) {
            Class target = getPropertyType();
            for (RuleMetaData rule : rules) {
                Object x = DataTypeMapper.instance().map(rule.getParameter(RuleMetaData.PARAMETER_VALUE), target);
                if (x != null && x instanceof Comparable) {
                    // Determine the maximum value from all rules
                    if (minValue == null || ((Comparable) x).compareTo(minValue) > 0)
                        minValue = x;
                }
            }
        }
        return minValue;
    }

    /** Returns the maxValue for the property.
     * @return the maxValue for the property.
     */
    public Object getMaxValue() {
        Object maxValue = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "max-value");
        if (rules != null && rules.size() > 0) {
            Class target = getPropertyType();
            for (RuleMetaData rule : rules) {
                Object x = DataTypeMapper.instance().map(rule.getParameter(RuleMetaData.PARAMETER_VALUE), target);
                if (x != null && x instanceof Comparable) {
                    // Determine the minimum value from all rules
                    if (maxValue == null || ((Comparable) x).compareTo(maxValue) < 0)
                        maxValue = x;
                }
            }
        }
        return maxValue;
    }

    /** Returns the caseType for the property.
     * If multiple caseType rules are defined for a property, then the caseType from the first rule will be returned.
     * @return the caseType for the property.
     */
    public String getCaseType() {
        String caseType = null;
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "case-type");
        if (rule != null) {
            caseType = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
            if (caseType != null) {
                if (caseType.equals("upper"))
                    caseType = FieldMetaData.UPPER_CASE;
                else if (caseType.equals("lower"))
                    caseType = FieldMetaData.LOWER_CASE;
                else if (caseType.equals("mixed"))
                    caseType = FieldMetaData.MIXED_CASE;
            }
        }
        return caseType;
    }

    /** Returns the layout for the property.
     * If multiple layout rules are defined for a property, then the layout from the last rule will be returned.
     * @return the layout for the property.
     */
    public String getLayout() {
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "layout");
        return rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : null;
    }

    /** Looks up the layout rules defined for the property and formats the input object using the associated formatterClass.
     * If multiple layout rules are defined for a property, then the layout from the last rule will be used.
     * @param property the property instance to be formatted.
     * @return the formatted property.
     */
    public String format(Object property) {
        String output = null;
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "layout");
        String formatterClass = rule != null ? rule.getParameter("formatterClass") : null;
        String layout = rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : null;
        if (formatterClass != null) {
            try {
                IFormatter formatter = (IFormatter) Class.forName(formatterClass).newInstance();
                formatter.setLayout(layout);
                output = formatter.format(property);
            } catch (Exception e) {
                log.warn("The input formatterClass should implement the IFormatter interface: " + formatterClass, e);
            }
        }
        return output != null ? output : Formatter.format(property, layout);
    }

    /** Returns the pattern(s) for the property.
     * @return the pattern(s) for the property.
     */
    public String[] getPattern() {
        String[] pattern = null;
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "pattern");
        if (rules != null && rules.size() > 0) {
            pattern = new String[rules.size()];
            int i = 0;
            for (RuleMetaData rule : rules)
                pattern[i++] = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
        }
        return pattern;
    }

    /** Returns a true if the property is marked as mandatory.
     * @return a true if the property is marked as mandatory.
     */
    public boolean isMandatory() {
        List<RuleMetaData> rules = findRules(m_className, m_propertyName, m_obj, "mandatory");
        return rules != null && rules.size() > 0;
    }

    /** Returns the client rule for the property.
     * @return the client rule for the property.
     */
    public String getClientRule() {
        // @todo
        return null;
    }

    /** Returns the comment style for the property.
     * @return the comment style for the property.
     */
    public String getCommentStyle() {
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "comment");
        return rule != null ? rule.getParameter(RuleMetaData.PARAMETER_VALUE) : null;
    }

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the in-list values for the property.
     */
    public Map<String, String> getInListValues() {
        Map<String, String> inListValues = null;
        //Even though precedence for the in-list rule is 'all', we'll only consider the first rule
        RuleMetaData rule = findRule(m_className, m_propertyName, m_obj, "in-list");
        if (rule != null)
            inListValues = getInListValues(rule);
        return inListValues;
    }

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the in-list values for the property.
     */
    public static Map<String, String> getInListValues(RuleMetaData rule) {
        Map<String, String> inListValues = new LinkedHashMap<String, String>();
        String[] validStrings = rule.getParameter(RuleMetaData.PARAMETER_VALUE).split(rule.getParameter(RuleMetaData.PARAMETER_SEPARATOR));
        for (String validString : validStrings) {
            // the string could be of the format 'value=label'; extract the value from such a string
            validString = validString.trim();
            Matcher m = VALUE_AND_LABEL_PATTERN.matcher(validString);
            if (m.matches())
                inListValues.put(m.group(1), m.group(2));
            else
                inListValues.put(validString, null);
        }
        return inListValues;
    }

    /** Returns debug info.
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("MetaDataIntrospector")
        .append("\n    class = ").append(m_className)
        .append("\n    object = ").append(m_obj)
        .append("\n    property = ").append(m_propertyName)
        .append("\n    getLabel() = ").append(getLabel());

        if (m_propertyName == null)
            buf.append("\n    getPrimaryKey() = ").append(getPrimaryKey());
        else
            buf.append("\n    getPropertyType() = ").append(getPropertyType())
            .append("\n    isHidden() = ").append(isHidden())
            .append("\n    isReadOnly() = ").append(isReadOnly())
            .append("\n    isMandatory() = ").append(isMandatory())
            .append("\n    getForeignKeyInfo() = ").append(getForeignKeyInfo())
            .append("\n    getMinLength() = ").append(getMinLength())
            .append("\n    getMaxLength() = ").append(getMaxLength())
            .append("\n    getMaxFracLength() = ").append(getMaxFracLength())
            .append("\n    getMinValue() = ").append(getMinValue())
            .append("\n    getMaxValue() = ").append(getMaxValue())
            .append("\n    getCaseType() = ").append(getCaseType())
            .append("\n    getLayout() = ").append(getLayout())
            .append("\n    getPattern() = ").append(getPattern())
            .append("\n    getClientRule() = ").append(getClientRule())
            .append("\n    getCommentStyle() = ").append(getCommentStyle())
            .append("\n    getInListValues() = ").append(getInListValues());

        return buf.toString();
    }

    /** Returns the first applicable rule for the className/propertyName/ruleName combination. */
    RuleMetaData findRule(String className, String propertyName, Object obj, String ruleName) {
    	
    	RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
    	criteria.setClassName(className);
    	criteria.setPropertyName(propertyName);
    	criteria.setRuleName(ruleName);
    	criteria.setObj(obj);
    	return RuleMetaHelper.findRule(criteria);
    }

    /** Returns all applicable rules for the className/propertyName/ruleName combination. */
    List<RuleMetaData> findRules(String className, String propertyName, Object obj, String ruleName) {
    	
    	RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
    	criteria.setClassName(className);
    	criteria.setPropertyName(propertyName);
    	criteria.setRuleName(ruleName);
    	criteria.setObj(obj);
    	return RuleMetaHelper.findRules(criteria);
    }

    /** Returns a Map of propertyName and first applicable rule for the className/ruleName combination. */
    private Map<String, RuleMetaData> findPropertyRuleMap(String className, Object obj, String ruleName) {
        try {
            Map<String, List<RuleMetaData>> ruleMap = MetaDataRepository.instance().getPropertyRuleMap(className, ruleName);
            if (ruleMap != null) {
                Map<String, RuleMetaData> output = new LinkedHashMap<String, RuleMetaData>();
                IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleName);
                for (Map.Entry<String, List<RuleMetaData>> me : ruleMap.entrySet()) {
                    List<RuleMetaData> applicableRules = ruleHelper.getApplicableRules(className, obj, me.getValue(), false);
                    if (applicableRules != null && applicableRules.size() > 0)
                        output.put(me.getKey(), applicableRules.get(0));
                }
                return output;
            }
        } catch (Exception e) {
            // do nothing
            if (log.isDebugEnabled())
                log.debug("Error in finding the applicable rules of type " + ruleName, e);
        }
        return null;
    }

    /** Returns a Map of propertyName and applicable rules for the className/ruleName combination. */
    private Map<String, List<RuleMetaData>> findPropertyRulesMap(String className, Object obj, String ruleName) {
        try {
            Map<String, List<RuleMetaData>> ruleMap = MetaDataRepository.instance().getPropertyRuleMap(className, ruleName);
            if (ruleMap != null) {
                Map<String, List<RuleMetaData>> output = new LinkedHashMap<String, List<RuleMetaData>>();
                IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleName);
                for (Map.Entry<String, List<RuleMetaData>> me : ruleMap.entrySet()) {
                    List<RuleMetaData> applicableRules = ruleHelper.getApplicableRules(className, obj, me.getValue(), false);
                    if (applicableRules != null && applicableRules.size() > 0)
                        output.put(me.getKey(), applicableRules);
                }
                return output;
            }
        } catch (Exception e) {
            // do nothing
            if (log.isDebugEnabled())
                log.debug("Error in finding the applicable rules of type " + ruleName, e);
        }
        return null;
    }

    /** Returns the 'name' parameter from the first applicable domain-info rule. If not found, the simple className will be returned. */
    private String findNameFromDomainInfo(String className, Object obj) {
        RuleMetaData rule = findRule(className, null, obj, "domain-info");
        String name = rule != null ? rule.getParameter("name") : null;
        return name != null ? name : StringHelper.getShortClassName(className);
    }

    /** Molds the parameters for the first applicable rule into a Properties object. */
    public Properties findInfo(String className, String propertyName, Object obj, String ruleName) {
        RuleMetaData rule = findRule(className, propertyName, obj, ruleName);
        return rule != null ? ruleToProperties(rule) : null;
    }

    /** Molds the parameters for the rule into a Properties object. */
    public Properties ruleToProperties(RuleMetaData rule) {
        Properties p = new Properties();
        Map<String, String> parameters = rule.getParameters();
        if (parameters != null) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                if (parameter.getKey() != null && parameter.getValue() != null && Arrays.binarySearch(PARAMS_TO_EXCLUDE, parameter.getKey()) < 0)
                    p.setProperty(parameter.getKey(), parameter.getValue());
            }
        }
        return p;
    }
	
	/** Returns the service info for the Class/property.
     * @return the service info for the Class/property.
     */
    public Map getServiceInfo() {

        Map<String, Object> map = new HashMap<String, Object>();

        RuleMetaData methodParamsRule = findRule(m_className, m_propertyName, m_obj, "method-params");
        RuleMetaData methodReturnRule = findRule(m_className, m_propertyName, m_obj, "method-return");

        map.put("method-params", methodParamsRule != null ? methodParamsRule.getParameter(RuleMetaData.PARAMETER_VALUE) : null);
        map.put("method-return", methodReturnRule != null ? methodReturnRule.getParameter(RuleMetaData.PARAMETER_VALUE) : null);
        map.put("method-param", findInfo(m_className, m_propertyName, m_obj, "method-param"));

        return map;

    }
}
