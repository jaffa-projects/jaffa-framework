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
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.commons.MetaData;
import org.jaffa.rules.rulemeta.RuleHelperFactory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Encapsulates a rule.
 * <p/>
 * It has the following properties:
 * <ul>variation: Optional. The MetaData will be applicable to the specified comma-separated list of variations only.
 * <ul>text: Optional. Provides the free-text for the Rule.
 * <ul>parameters: Optional. Provides the parameters for the Rule.
 * <ul>Check {@link MetaData} for more properties.
 * <p/>
 * It maintains a reference to the ClassMetaData and the (optional) PropertyMetaData instances within which it is defined.
 */
public class RuleMetaData extends MetaData {

    /**
     * The name of the rule used to inherit rules from another property or class.
     */
    public static final String RULE_SUPER = "super";
    /**
     * The name of the 'condition' parameter used by rules.
     */
    public static final String PARAMETER_CONDITION = "condition";
    /**
     * The name of the 'domainObject' parameter used by rules.
     */
    public static final String PARAMETER_DOMAIN_OBJECT = "domainObject";
    /**
     * The name of the 'executeOnReturn' parameter used by rules.
     */
    public static final String PARAMETER_EXECUTE_ON_RETURN = "executeOnReturn";
    /**
     * The name of the 'expression' parameter used by rules.
     */
    public static final String PARAMETER_EXPRESSION = "expression";
    /**
     * The name of the 'file' parameter used by rules.
     */
    public static final String PARAMETER_FILE = "file";
    /**
     * The name of the 'language' parameter used by rules.
     */
    public static final String PARAMETER_LANGUAGE = "language";
    /**
     * The name of the 'member' parameter used by rules.
     */
    public static final String PARAMETER_MEMBER = "member";
    /**
     * The name of the 'separator' parameter used by rules.
     */
    public static final String PARAMETER_SEPARATOR = "separator";
    /**
     * The name of the 'trigger' parameter used by rules.
     */
    public static final String PARAMETER_TRIGGER = "trigger";
    /**
     * The name of the 'value' parameter used BY rules.
     */
    public static final String PARAMETER_VALUE = "value";
    /**
     * The name of the 'value' parameter used BY rules.
     */
    public static final String PARAMETER_NAME = "name";
    private static Logger log = Logger.getLogger(RuleMetaData.class);
    private ClassMetaData m_classMetaData;
    private PropertyMetaData m_propertyMetaData;
    private String m_variation;
    private String[] m_variationArray;
    private String m_text;
    private Map<String, String> m_parameters;
    private RuleCondition<?> condition;

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
     * Getter for property propertyMetaData.
     *
     * @return Value of property propertyMetaData.
     */
    public PropertyMetaData getPropertyMetaData() {
        return m_propertyMetaData;
    }

    /**
     * Setter for property propertyMetaData.
     *
     * @param propertyMetaData New value of property propertyMetaData.
     */
    public void setPropertyMetaData(PropertyMetaData propertyMetaData) {
        m_propertyMetaData = propertyMetaData;
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
        if (variation != null) {
            m_variationArray = variation.split(",");
            Arrays.sort(m_variationArray); //Sort the array so that binarySearch can be performed
        } else
            m_variationArray = null;
    }

    /**
     * Getter for property variation.
     *
     * @return Value of property variation.
     */
    public String[] getVariationArray() {
        return m_variationArray;
    }

    /**
     * Getter for property text.
     *
     * @return Value of property text.
     */
    public String getText() {
        return m_text;
    }

    /**
     * Setter for property text.
     *
     * @param text New value of property text.
     */
    public void setText(String text) {
        m_text = text;
    }

    /**
     * Getter for property parameters.
     *
     * @return Value of property parameters.
     */
    public Map<String, String> getParameters() {
        return m_parameters;
    }

    /**
     * Gets the condition for the rule.
     *
     * @return the condition, or null if unconditional.
     */
    public RuleCondition<?> getCondition() {
        return condition;
    }

    /**
     * Sets the condition for the rule.
     *
     * @param condition the condition, or null if unconditional.
     * @param <T>       the type of the object to evaluate.
     */
    public <T> void setCondition(RuleCondition<T> condition) {
        this.condition = condition;
    }

    /**
     * Evaluates the object to see if it matches the condition.
     *
     * @param bean the object to evaluate.
     * @return true if the object matches the condition.
     */
    public boolean evaluateCondition(Object bean) throws Exception {
        return condition == null || ((RuleCondition<Object>) condition).evaluate(bean);
    }

    /**
     * Returns the value of the given parameter.
     *
     * @param key the parameter name.
     * @return the value of the given parameter.
     */
    public String getParameter(String key) {
        return m_parameters == null ? null : m_parameters.get(key);
    }

    /**
     * Adds the input parameters.
     *
     * @param parameters the parameters.
     */
    public void addParameters(Map<String, String> parameters) {
        if (m_parameters == null)
            m_parameters = parameters;
        else if (parameters != null)
            m_parameters.putAll(parameters);
    }

    /**
     * Sets a parameter.
     *
     * @param key   the parameter name.
     * @param value the parameter value.
     */
    public void addParameter(String key, String value) {
        if (m_parameters == null)
            m_parameters = new LinkedHashMap<String, String>();
        m_parameters.put(key, value);
    }

    /**
     * Add the parameter only if the value is non-null.
     *
     * @param key   the parameter name.
     * @param value the parameter value.
     */
    public void addParamIfNonNull(String key, String value) {
        if (value != null) {
            addParameter(key, value);
        }
    }

    /**
     * Transforms the free-text to an appropriate parameter.
     * Validates this rule for mandatory parameters.
     *
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    public void validate() throws ApplicationExceptions, FrameworkException {
        // Validate this rule for mandatory parameters
        RuleHelperFactory.instance(getName()).validate(this);
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    public String toString() {
        StringBuilder buf = new StringBuilder("<").append(getName()).append(super.toString());
        if (getVariation() != null)
            buf.append(" variation='").append(getVariation()).append('\'');
        if (getParameters() != null) {
            for (Map.Entry<String, String> me : getParameters().entrySet())
                buf.append(' ').append(me.getKey()).append("='").append(me.getValue()).append('\'');
        }
        if (getText() != null) {
            buf.append('>').append(getText()).append("</").append(getName()).append('>');
        } else {
            buf.append("/>");
        }
        return buf.toString();
    }
}
