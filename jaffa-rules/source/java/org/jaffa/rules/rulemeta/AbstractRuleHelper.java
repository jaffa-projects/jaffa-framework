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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.MetaDataRulesEngine;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.rules.variation.VariationRepository;
import org.jaffa.security.VariationContext;
import org.jaffa.util.ExceptionHelper;

/**
 * An Abstract Rule Helper to split the generic methods out from Default Rule Helper and Audit Rule Helper
 */

public abstract class AbstractRuleHelper implements IRuleHelper {
	
	
	private static final Logger log = Logger.getLogger(AbstractRuleHelper.class);
	
	 /** Validates the input RuleMetaData.
     * Ensures that the mandatory parameters have been provided.
     * Ensures that valid values have been specified for a parameter.
     * It sets the textParameter.
     * It sets the default values for the parameters.
     * It changes the case-type of the case-insensitive parameters to lowercase.
     * It sets the variation, if unspecified, by looking up a matching source URI in the VariationRepository.
     * @param rule the input
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs
     */
    public void validate(RuleMetaData rule) throws ApplicationExceptions, FrameworkException {
        Rule ruleInfo = findRuleInfo(rule);
        
        // Set the textParameter, if not already provided
        if (ruleInfo.getTextParameter() != null && rule.getParameter(ruleInfo.getTextParameter()) == null) {
            rule.addParameter(ruleInfo.getTextParameter(), rule.getText());
            rule.setText(null);
        }
        
        // Iterate through the necessary parameters, setting the default, and checking for mandatory and valid-values
        if (ruleInfo.getParameters() != null) {
            for (Parameter parameter : ruleInfo.getParameters()) {
                String name = parameter.getName();
                String value = rule.getParameter(name);
                
                // Set the default, if required
                if (parameter.getDefault() != null && value == null) {
                    value = parameter.getDefault();
                    rule.addParameter(name, value);
                }
                
                // Change the case-type, if required
                if (parameter.getCaseInsensitive() != null && parameter.getCaseInsensitive() && value != null) {
                    value = value.toLowerCase();
                    rule.addParameter(name, value);
                }
                
                // Mandatory check
                if (parameter.getMandatory() != null && parameter.getMandatory() && value == null) {
                    log.error("Parameter " + name + " is missing in " + rule);
                    throw new JaffaRulesFrameworkException(JaffaRulesFrameworkException.MISSING_PARAMETER, new Object[] {parameter, rule});
                }
                
                // Valid Value check
                if (parameter.getValidValues() != null && parameter.getValidValues().length > 0 && Arrays.binarySearch(parameter.getValidValues(), value) < 0) {
                    log.error("Invalid value " + value + " passed for parameter " + name + " in " + rule + ". Valid values are " + parameter);
                    throw new JaffaRulesFrameworkException(JaffaRulesFrameworkException.INVALID_PARAMETER, new Object[] {value, name, rule, parameter});
                }
            }
        }
        
        // Search for unknown parameters
        if (rule.getParameters() != null) {
            for (String name : rule.getParameters().keySet()) {
                if (ruleInfo.getParameter(name) == null) {
                    log.error("Unknown parameter " + name + " passed in rule " + rule);
                    throw new JaffaRulesFrameworkException(JaffaRulesFrameworkException.UNKNOWN_PARAMETER, new Object[] {name, rule});
                }
            }
        }
        
        // Set the variation, if unspecified, by looking up a matching source URI in the VariationRepository
        if (rule.getVariation() == null) {
            String variation = VariationRepository.instance().find(rule.getSource());
            if (variation != null) {
                if (log.isDebugEnabled())
                    log.debug("By matching the source URI, variation has been set to " + variation);
                rule.setVariation(variation);
            }
        }
    }
    
    /** Returns a true if the 'supportedExecutionRealms' is null or if the execution-realm of the input class matches the 'supportedExecutionRealms'.
     * @param className The target class.
     * @param ruleInfo the Rule instance containing the 'supportedExecutionRealms'.
     * @return a true if the 'supportedExecutionRealms' is null or if the execution-realm of the input class matches the 'supportedExecutionRealms'.
     */
    public boolean checkExecutionRealm(String className, Rule ruleInfo) {
        if (ruleInfo.getExecutionRealms() == null || ruleInfo.getExecutionRealms().length == 0) {
            return true;
        } else {
            String executionRealm = RealmRepository.instance().find(className);
            return executionRealm != null ? Arrays.binarySearch(ruleInfo.getExecutionRealms(), executionRealm) >= 0 : false;
        }
    }
    
    /** Selects the rules to be applied from the input.
     * Performs the variation check. An input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. An input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * The precedence for the rule is used to determine the rule(s) to be passed back.
     * @param className the target class.
     * @param rules the input list of rules.
     * @return the rules to be applied.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs.
     */
    public List<RuleMetaData> getApplicableRules(String className, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException {
        if (rules != null && rules.size() > 0) {
            // Assume that the input rules are of the same type. Pick the Rule info based on the very first input rule
            Rule ruleInfo = findRuleInfo(rules.get(0));

            List<RuleMetaData> output = null;
            if (ruleInfo.isPrecedenceLast()) {
                // Return the first valid rule starting from the end of the list
                for (ListIterator<RuleMetaData> litr = rules.listIterator(rules.size()); litr.hasPrevious(); ) {
                    RuleMetaData rule = litr.previous();
                    if (check(className, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new ArrayList<RuleMetaData>(1);
                        output.add(rule);
                        break;
                    }
                }
            } else if (ruleInfo.isPrecedenceFirst()) {
                // Return the first valid rule starting from the beginning of the list
                for (Iterator<RuleMetaData> itr = rules.iterator(); itr.hasNext(); ) {
                    RuleMetaData rule = itr.next();
                    if (check(className, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new ArrayList<RuleMetaData>();
                        output.add(rule);
                        break;
                    }
                }
            } else {
                // Return all valid rules
                for (Iterator<RuleMetaData> itr = rules.iterator(); itr.hasNext(); ) {
                    RuleMetaData rule = itr.next();
                    if (check(className, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new LinkedList<RuleMetaData>();
                        output.add(rule);
                    }
                }
            }

            return output;
        }
        return rules;
    }
    
    /** Selects the rules to be applied from the input.
     * Performs the variation check. An input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. An input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Performs the condition check. An input rule having a condition will be ignored, if the evaluation of its condition script returns a false or if the Object is null (provided the condition does not contain the word 'bean').
     * The precedence for the rule is used to determine the rule(s) to be passed back.
     * @param className the target class.
     * @param obj the instance on which the rules are to be applied.
     * @param rules the input list of rules.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @return the rules to be applied.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs.
     */
    public List<RuleMetaData> getApplicableRules(String className, Object obj, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException {
        if (rules != null && rules.size() > 0) {
            // Assume that the input rules are of the same type. Pick the Rule info based on the very first input rule
            Rule ruleInfo = findRuleInfo(rules.get(0));
            
            List<RuleMetaData> output = null;
            if (ruleInfo.isPrecedenceLast()) {
                // Return the first valid rule starting from the end of the list
                for (ListIterator<RuleMetaData> litr = rules.listIterator(rules.size()); litr.hasPrevious(); ) {
                    RuleMetaData rule = litr.previous();
                    if (check(className, obj, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new ArrayList<RuleMetaData>(1);
                        output.add(rule);
                        break;
                    }
                }
            } else if (ruleInfo.isPrecedenceFirst()) {
                // Return the first valid rule starting from the beginning of the list
                for (Iterator<RuleMetaData> itr = rules.iterator(); itr.hasNext(); ) {
                    RuleMetaData rule = itr.next();
                    if (check(className, obj, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new ArrayList<RuleMetaData>();
                        output.add(rule);
                        break;
                    }
                }
            } else {
                // Return all valid rules
                for (Iterator<RuleMetaData> itr = rules.iterator(); itr.hasNext(); ) {
                    RuleMetaData rule = itr.next();
                    if (check(className, obj, rule, executionRealmCheck, ruleInfo)) {
                        if (output == null)
                            output = new LinkedList<RuleMetaData>();
                        output.add(rule);
                    }
                }
            }
            
            return output;
        }
        return rules;
    }
    
    
    /** Obtains the Rule instance from the RuleRepository based on the rule name.
     * @param rule the input
     * @throws FrameworkException if any internal error occurs
     * @return the Rule instance from the RuleRepository based on the rule name.
     */
    protected Rule findRuleInfo(RuleMetaData rule) throws JaffaRulesFrameworkException {
        Rule ruleInfo = RuleRepository.instance().getRuleByName(rule.getName());
        if (ruleInfo == null) {
            log.error("Invalid Rule " + rule);
            throw new JaffaRulesFrameworkException(JaffaRulesFrameworkException.INVALID_RULE, new Object[] {rule});
        }
        return ruleInfo;
    }
    
    
    
    /** Returns true if the variation of the input rule is null or matches the variation in the thread context.
     * @param rule a rule.
     * @return true if the variation of the input rule is null or matches the variation in the thread context.
     */
    protected boolean checkVariation(RuleMetaData rule) {
        return rule.getVariationArray() != null ? Arrays.binarySearch(rule.getVariationArray(), VariationContext.getVariation()) >= 0 : true;
    }
    
    /** Performs the variation check. The input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. The input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Performs the condition check. An input rule having a condition will be ignored, if the evaluation of its condition script returns a false or if the Object is null (provided the condition does not contain the word 'bean').
     * @param className the target class.
     * @param obj the instance on which the rules are to be applied.
     * @param rule the rule to be checked.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @param ruleInfo the Rule instance corresponding to the input rule.
     * @return true if all the checks return true.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs.
     */
    protected abstract boolean check(String className, Object obj, RuleMetaData rule, boolean executionRealmCheck, Rule ruleInfo) throws ApplicationExceptions, FrameworkException;

    /** Performs the variation check. The input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. The input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Does not performs the condition check because the object is not passed to the method
     *
     * @param className the target class.
     * @param rule the rule to be checked.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @param ruleInfo the Rule instance corresponding to the input rule.
     * @return true if all the checks return true.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs.
     */
    protected abstract boolean check(String className, RuleMetaData rule, boolean executionRealmCheck, Rule ruleInfo) throws ApplicationExceptions, FrameworkException;
}
