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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Defaults;
import org.jaffa.rules.rulemeta.AuditRuleHelper;
import org.jaffa.rules.rulemeta.AuditRuleMetaHelper;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.util.BeanHelper;

/** MetaDataIntrospector is a helper class to lookup class-level and property-level rules.
 */
public class MetaDataAuditIntrospector extends MetaDataIntrospector {

    private static Logger log = Logger.getLogger(MetaDataAuditIntrospector.class);
    private String m_className = null;
    private String m_propertyName = null;
    private Object m_obj = null;
    

    /** Creates an instance.
     * @param className The Class being instrospected.
     * @param propertyName The property name. A null should be passed if interested in class-level rules only.
     * @param obj The Object being introspected. Conditions, if specified in any of the rules, will be evaluated only if an Object is passed.
     */
    
    
    public MetaDataAuditIntrospector(String className, String propertyName, Object obj) {
       super(className,propertyName,obj);
       m_className = className;
       m_propertyName = propertyName;
       m_obj = obj; 
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

    /** Returns debug info.
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("MetaDataAuditIntrospector")
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

    
    /** Returns a Map of propertyName and first applicable rule for the className/ruleName combination. */
    private Map<String, RuleMetaData> findPropertyRuleMap(String className, Object obj, String ruleName) {
        try {
            Map<String, List<RuleMetaData>> ruleMap = MetaDataRepository.instance().getPropertyRuleMap(className, ruleName);
            if (ruleMap != null) {
                Map<String, RuleMetaData> output = new LinkedHashMap<String, RuleMetaData>();
                IRuleHelper ruleHelper = new AuditRuleHelper();
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
    
    
    /** Returns the first applicable rule for the className/propertyName/ruleName combination. */
    RuleMetaData findRule(String className, String propertyName, Object obj, String ruleName) {
    	
    	RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
    	criteria.setClassName(className);
    	criteria.setPropertyName(propertyName);
    	criteria.setRuleName(ruleName);
    	criteria.setObj(obj);
    	return AuditRuleMetaHelper.findRule(criteria);
    	
    }

    /** Returns all applicable rules for the className/propertyName/ruleName combination. */
    List<RuleMetaData> findRules(String className, String propertyName, Object obj, String ruleName) {
    	
    	RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
    	criteria.setClassName(className);
    	criteria.setPropertyName(propertyName);
    	criteria.setRuleName(ruleName);
    	criteria.setObj(obj);
    	return AuditRuleMetaHelper.findRules(criteria);
    }
    
}
