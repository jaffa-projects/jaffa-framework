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

package org.jaffa.rules.rulemeta;

import org.apache.log4j.Logger;
import org.jaffa.rules.commons.MetaData;

import java.util.*;

/**
 * Defines a Rule.
 * <p/>
 * It has the following properties:
 * <ul>prepared: Internal. A flag to indicate if the dependencies in a MetaData instance have been resolved.
 * <ul>executionRealms: Optional. The comma-separated list of supported execution realms for the Rule.
 * <ul>precedence: Optional. Can be all, first or last. Default is all
 * <ul>textParameter: Optional. The parameter which gets the inner-text of a Rule instance.
 * <ul>helper: Optional. Class used to verify the parameters provided to a Rule instance, as well as select the appropriate Rule instance(s) from a List.
 * <ul>interceptor: Optional. AOP-Implementation specific class used to inject behavior at runtime
 * <ul>extends-rule: Optional. To inherit the parameters from another Rule instance
 * <ul>parameters: Optional. The parameters for a Rule.
 * <ul>Check {@link MetaData} for more properties
 * <p/>
 * NOTE: An instance of this class inherits the attributes from a parent Rule in the prepare() method.
 * Subsequent changes to the Parent rule will not be reflected in the instance.
 * This should be not cause any problem, since we do not anticipate frequent changes to the Rules even during the development phase.
 */
public class Rule extends MetaData {

    public static final String NAME = "name";
    public static final String EXECUTION_REALMS = "execution-realms";
    public static final String PRECEDENCE = "precedence";
    public static final String TEXT_PARAMETER = "text-parameter";
    public static final String HELPER = "helper";
    public static final String INTERCEPTOR = "interceptor";
    public static final String EXTENDS_RULE = "extends-rule";
    public static final String INHERITABLE = "inheritable";
    public static final String MANDATORY = "mandatory";
    public static final String DEFAULT = "default";
    public static final String VALID_VALUES = "valid-values";
    public static final String CASE_INSENSITIVE = "case-insensitive";
    private static final String PRECEDENCE_ALL = "all";
    private static final String PRECEDENCE_FIRST = "first";
    private static final String PRECEDENCE_LAST = "last";
    private static Logger log = Logger.getLogger(Rule.class);
    private boolean m_prepared;
    private String[] m_executionRealms;
    private String m_precedence;
    private String m_textParameter;
    private String m_helper;
    private String m_interceptor;
    private String m_extendsRule;
    private boolean m_inheritable;
    private List<Parameter> m_parameters;
    private Map<String, Parameter> m_parameterByName;

    /**
     * Default constructor.
     */
    public Rule() {
    }

    /**
     * Constructs a rule with a name.
     *
     * @param name the name.
     */
    public Rule(String name) {
        setName(name);
    }

    /**
     * Getter for property prepared.
     *
     * @return Value of property prepared.
     */
    public boolean isPrepared() {
        return m_prepared;
    }

    /**
     * Setter for property prepared.
     *
     * @param prepared New value of property prepared.
     */
    protected void setPrepared(boolean prepared) {
        m_prepared = prepared;
    }

    /**
     * Getter for property executionRealms.
     *
     * @return Value of property executionRealms.
     */
    public String[] getExecutionRealms() {
        return m_executionRealms;
    }

    /**
     * Setter for property executionRealms.
     *
     * @param executionRealms Value of property executionRealms.
     */
    public void setExecutionRealms(String[] executionRealms) {
        if (executionRealms != null)
            Arrays.sort(executionRealms);
        m_executionRealms = executionRealms;
    }

    /**
     * Getter for property precedence.
     *
     * @return Value of property precedence.
     */
    public String getPrecedence() {
        return m_precedence;
    }

    /**
     * Setter for property precedence.
     *
     * @param precedence Value of property precedence.
     */
    public void setPrecedence(String precedence) {
        m_precedence = precedence;
    }

    /**
     * Getter for property textParameter.
     *
     * @return Value of property textParameter.
     */
    public String getTextParameter() {
        return m_textParameter;
    }

    /**
     * Setter for property textParameter.
     *
     * @param textParameter Value of property textParameter.
     */
    public void setTextParameter(String textParameter) {
        m_textParameter = textParameter;
    }

    /**
     * Getter for property helper.
     *
     * @return Value of property helper.
     */
    public String getHelper() {
        return m_helper;
    }

    /**
     * Setter for property helper.
     *
     * @param helper Value of property helper.
     */
    public void setHelper(String helper) {
        m_helper = helper;
    }

    /**
     * Getter for property interceptor.
     *
     * @return Value of property interceptor.
     */
    public String getInterceptor() {
        return m_interceptor;
    }

    /**
     * Setter for property interceptor.
     *
     * @param interceptor Value of property interceptor.
     */
    public void setInterceptor(String interceptor) {
        m_interceptor = interceptor;
    }

    /**
     * Getter for property extendsRule.
     *
     * @return Value of property extendsRule.
     */
    public String getExtendsRule() {
        return m_extendsRule;
    }

    /**
     * Setter for property extendsRule.
     *
     * @param extendsRule Value of property extendsRule.
     */
    public void setExtendsRule(String extendsRule) {
        m_extendsRule = extendsRule;
    }

    /**
     * Getter for property inheritable.
     *
     * @return Value of property inheritable.
     */
    public boolean isInheritable() {
        return m_inheritable;
    }

    /**
     * Setter for property inheritable.
     *
     * @param inheritable New value of property inheritable.
     */
    protected void setInheritable(boolean inheritable) {
        m_inheritable = inheritable;
    }

    /**
     * Getter for property parameters.
     *
     * @return Value of property parameters.
     */
    public List<Parameter> getParameters() {
        return m_parameters;
    }

    /**
     * Getter for property parameter.
     *
     * @param name the parameter name.
     * @return Value of property parameter.
     */
    public Parameter getParameter(String name) {
        return m_parameterByName != null ? m_parameterByName.get(name) : null;
    }

    /**
     * Adds the parameters.
     *
     * @param parameters the parameters.
     */
    public void addParameters(List<Parameter> parameters) {
        if (parameters != null) {
            for (Parameter parameter : parameters)
                addParameter(parameter);
        }
    }

    /**
     * Adds a parameter to this property.
     *
     * @param parameter a parameter.
     */
    public void addParameter(Parameter parameter) {
        if (m_parameters == null)
            m_parameters = new LinkedList<Parameter>();
        m_parameters.add(parameter);

        if (m_parameterByName == null)
            m_parameterByName = new HashMap<String, Parameter>();
        m_parameterByName.put(parameter.getName(), parameter);
    }

    /**
     * Prepares this Rule, inheriting the attributes from the Rule identified by extendsRule.
     * The prepared flag will be set after the completion of the process to avoid any re-runs.
     */
    public void prepare() {
        if (!isPrepared())
            prepare(null);
    }

    /**
     * Returns true if the precedence for this Rule is null or "all".
     *
     * @return true if the precedence for this Rule is null or "all".
     */
    public boolean isPrecedenceAll() {
        return getPrecedence() == null || PRECEDENCE_ALL.equals(getPrecedence());
    }

    /**
     * Returns true if the precedence for this Rule is "first".
     *
     * @return true if the precedence for this Rule is "first".
     */
    public boolean isPrecedenceFirst() {
        return PRECEDENCE_FIRST.equals(getPrecedence());
    }

    /**
     * Returns true if the precedence for this Rule is "last".
     *
     * @return true if the precedence for this Rule is "last".
     */
    public boolean isPrecedenceLast() {
        return PRECEDENCE_LAST.equals(getPrecedence());
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    public String toString() {
        StringBuilder buf = new StringBuilder("<rule");
        if (getName() != null)
            buf.append(" name='").append(getName()).append('\'');
        if (getExecutionRealms() != null && getExecutionRealms().length > 0) {
            buf.append(" executionRealms='");
            for (int i = 0; i < getExecutionRealms().length; i++) {
                if (i > 0)
                    buf.append(',');
                buf.append(getExecutionRealms()[i]);
            }
            buf.append('\'');
        }
        if (getPrecedence() != null)
            buf.append(" precedence='").append(getPrecedence()).append('\'');
        if (getTextParameter() != null)
            buf.append(" textParameter='").append(getTextParameter()).append('\'');
        if (getHelper() != null)
            buf.append(" helper='").append(getHelper()).append('\'');
        if (getInterceptor() != null)
            buf.append(" interceptor='").append(getInterceptor()).append('\'');
        if (getExtendsRule() != null)
            buf.append(" extends-rule='").append(getExtendsRule()).append('\'');
        buf.append(" inheritable='").append(isInheritable()).append('\'');
        buf.append(" prepared='").append(isPrepared()).append('\'');
        buf.append(super.toString());
        if (getParameters() != null && getParameters().size() > 0) {
            buf.append(">");
            for (Parameter parameter : getParameters())
                buf.append(parameter);
            buf.append("</rule>");
        } else
            buf.append("/>");
        return buf.toString();
    }

    /**
     * Prepares this Rule, inheriting the attributes from the Rule identified by extendsRule.
     * The prepared flag will be set after the completion of the process to avoid any re-runs.
     *
     * @param rulesBeingPrepared To avoid circular references, maintain a stack of Rule instances being prepared.
     */
    private synchronized void prepare(Stack<Rule> rulesBeingPrepared) {
        if (!isPrepared()) {
            if (getExtendsRule() != null) {
                Rule superRule = RuleRepository.instance().getRuleByName(getExtendsRule(), false);
                if (superRule != null) {
                    // Prepare the super rule if required.
                    if (!superRule.isPrepared()) {
                        // The stack will need to be initialized just once, and should not happen in the recursive call
                        if (rulesBeingPrepared == null) {
                            rulesBeingPrepared = new Stack<Rule>();
                            rulesBeingPrepared.push(this);
                        }

                        // Check for circular reference by searching for the super rule in the stack
                        if (rulesBeingPrepared.search(superRule) > 0) {
                            StringBuilder buf = new StringBuilder("Circular Reference:").append('\n').append('\t').append(superRule);
                            for (ListIterator<Rule> i = rulesBeingPrepared.listIterator(rulesBeingPrepared.size()); i.hasPrevious(); )
                                buf.append('\n').append('\t').append(i.previous());
                            log.warn(buf);
                        } else {

                            // Push the super rule onto the stack
                            rulesBeingPrepared.push(superRule);

                            // Recursively prepare the super rule
                            superRule.prepare(rulesBeingPrepared);

                            // Pop the super rule from the stack
                            rulesBeingPrepared.pop();
                        }
                    }

                    // Do not inherit anything in case there was a circular reference
                    if (superRule.isPrepared()) {
                        if (getExecutionRealms() == null)
                            setExecutionRealms(superRule.getExecutionRealms());
                        if (getPrecedence() == null)
                            setPrecedence(superRule.getPrecedence());
                        if (getTextParameter() == null)
                            setTextParameter(superRule.getTextParameter());
                        if (getHelper() == null)
                            setHelper(superRule.getHelper());
                        if (getInterceptor() == null)
                            setInterceptor(superRule.getInterceptor());
                        if (superRule.getParameters() != null && superRule.getParameters().size() > 0) {
                            // Add non-existent parameters only
                            for (Parameter superParameter : superRule.getParameters()) {
                                if (getParameter(superParameter.getName()) == null)
                                    addParameter(superParameter);
                            }
                        }
                    }
                }
            }

            setPrepared(true);
        }
    }

    /**
     * Sets the precedence to all.
     *
     * @return this rule.
     */
    public Rule precedenceAll() {
        return precedence(PRECEDENCE_ALL);
    }

    /**
     * Sets the precedence to first.
     *
     * @return this rule.
     */
    public Rule precedenceFirst() {
        return precedence(PRECEDENCE_FIRST);
    }

    /**
     * Sets the precedence to last.
     *
     * @return this rule.
     */
    public Rule precedenceLast() {
        return precedence(PRECEDENCE_LAST);
    }

    /**
     * Sets the precedence.
     *
     * @param precedence the precedence.
     * @return this rule.
     */
    private Rule precedence(String precedence) {
        setPrecedence(precedence);
        return this;
    }

    /**
     * Sets the text parameter.
     *
     * @param textParameter the text parameter.
     * @return this rule.
     */
    public Rule textParameter(String textParameter) {
        setTextParameter(textParameter);
        return this;
    }

    /**
     * Sets the execution realms.
     *
     * @param realms the realms.
     * @return this rule.
     */
    public Rule executionRealms(String realms) {
        setExecutionRealms(realms.split(","));
        return this;
    }

    /**
     * Adds a parameter.
     *
     * @param name the name.
     */
    public Rule parameter(String name) {
        return parameter(name, false);
    }

    /**
     * Adds a parameter.
     *
     * @param name      the name.
     * @param mandatory true if the parameter is mandatory.
     */
    public Rule parameter(String name, boolean mandatory) {
        return parameter(name, mandatory, null);
    }

    /**
     * Adds a parameter.
     *
     * @param name      the name.
     * @param mandatory true if the parameter is mandatory.
     * @param def       the default value.
     */
    public Rule parameter(String name, boolean mandatory, String def) {
        return parameter(name, mandatory, def, null);
    }

    /**
     * Adds a parameter.
     *
     * @param name        the name.
     * @param mandatory   true if the parameter is mandatory.
     * @param def         the default value.
     * @param validValues a comma-separated list of valid values.
     */
    public Rule parameter(String name, boolean mandatory, String def, String validValues) {
        return parameter(name, mandatory, def, validValues, false);
    }

    /**
     * Adds a parameter with all values specified.
     *
     * @param name            the name.
     * @param mandatory       true if the parameter is mandatory.
     * @param def             the default value.
     * @param validValues     a comma-separated list of valid values.
     * @param caseInsensitive true if the parameter is case-insensitive.
     */
    public Rule parameter(String name, boolean mandatory, String def, String validValues, boolean caseInsensitive) {
        Parameter param = new Parameter(name);
        param.setMandatory(mandatory);
        param.setDefault(def);
        param.setValidValues(validValues != null ? validValues.split(",") : null);
        param.setCaseInsensitive(caseInsensitive);
        addParameter(param);
        return this;
    }

    /**
     * Registers this rule with the repository.
     *
     * @return this rule.
     */
    public Rule register() {
        RuleRepository.instance().addRule(this);
        return this;
    }
}
