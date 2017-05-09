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
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.meta.MetaDataRulesEngine;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.util.ExceptionHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of this interface is used to validate RuleMetaData, and to select the appropriate rules based on precedence.
 */
public class DefaultRuleHelper extends AbstractRuleHelper implements IRuleEvaluator {

    private static final Logger log = Logger.getLogger(DefaultRuleHelper.class);

    /**
     * Performs the variation check. The input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. The input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Performs the condition check. An input rule having a condition will be ignored, if the evaluation of its condition script returns a false or if the Object is null (provided the condition does not contain the word 'bean').
     *
     * @param className           the target class.
     * @param obj                 the instance on which the rules are to be applied.
     * @param rule                the rule to be checked.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @param ruleInfo            the Rule instance corresponding to the input rule.
     * @return true if all the checks return true.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    protected boolean check(String className, Object obj, RuleMetaData rule, boolean executionRealmCheck, Rule ruleInfo) throws ApplicationExceptions, FrameworkException {
        return checkVariation(rule)
                && (!executionRealmCheck || checkExecutionRealm(className, ruleInfo))
                && (checkCondition(obj, rule));
    }

    /**
     * Performs the variation check. The input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. The input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Does not performs the condition check because the object is not passed to the method
     *
     * @param className           the target class.
     * @param rule                the rule to be checked.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @param ruleInfo            the Rule instance corresponding to the input rule.
     * @return true if all the checks return true.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    protected boolean check(String className, RuleMetaData rule, boolean executionRealmCheck, Rule ruleInfo) throws ApplicationExceptions, FrameworkException {
        return checkVariation(rule)
                && (!executionRealmCheck || checkExecutionRealm(className, ruleInfo));
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkCondition(Object obj, RuleMetaData rule) throws ApplicationExceptions, FrameworkException {
        if (rule.getParameter(RuleMetaData.PARAMETER_CONDITION) == null) {
            return true;
        } else if (obj == null && rule.getParameter(RuleMetaData.PARAMETER_CONDITION).indexOf(ScriptHelper.CONTEXT_BEAN) >= 0) {
            return false;
        } else {
            try {
                String condition = rule.getParameter(RuleMetaData.PARAMETER_CONDITION);

                // For better performance, cache the result of condition evaluations
                Map<Object, Map<String, Boolean>> cache = MetaDataRulesEngine.getConditionEvaluationCache();
                Map<String, Boolean> map = cache.get(obj);
                if (map == null) {
                    map = new HashMap<String, Boolean>();
                    cache.put(obj, map);
                }
                Boolean value = map.get(condition);
                if (value == null) {
                    Object result = ScriptHelper.instance(rule.getParameter(RuleMetaData.PARAMETER_LANGUAGE)).evaluate(null, condition, obj, rule.getSource(), rule.getLine() != null ? rule.getLine() : 0, 0);
                    value = result != null && result instanceof Boolean ? (Boolean) result : Boolean.FALSE;
                    map.put(condition, value);
                }
                return value;
            } catch (Exception e) {
                throw ExceptionHelper.throwAFR(e);
            }
        }
    }
}
