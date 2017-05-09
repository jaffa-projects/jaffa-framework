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

import java.util.List;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.meta.RuleMetaData;

/** An implementation of this interface is used to validate RuleMetaData, and to select the appropriate rules based on precedence.
 */
public interface IRuleHelper {
    
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
    public void validate(RuleMetaData rule) throws ApplicationExceptions, FrameworkException;
    
    /** Returns a true if the 'supportedExecutionRealms' is null or if the execution-realm of the input class matches the 'supportedExecutionRealms'.
     * @param className The target class.
     * @param ruleInfo the Rule instance containing the 'supportedExecutionRealms'.
     * @return a true if the 'supportedExecutionRealms' is null or if the execution-realm of the input class matches the 'supportedExecutionRealms'.
     */
    public boolean checkExecutionRealm(String className, Rule ruleInfo);
    
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
    public List<RuleMetaData> getApplicableRules(String className, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException;
    
    /** Selects the rules to be applied from the input.
     * Performs the variation check. An input rule will be ignored if its variation attribute does not match the variation in the thread context.
     * Performs the execution-realm check. An input rule will be ignored if the execution-realm of the input class does not match the supportedExecutionRealms for the rule.
     * Performs the condition check. An input rule having a condition will be ignored, if the evaluation of its condition script returns a false or if the Object is null
     * The precedence for the rule is used to determine the rule(s) to be passed back.
     * @param className the target class.
     * @param obj the instance on which the rules are to be applied.
     * @param rules the input list of rules.
     * @param executionRealmCheck decides if executionRealm checks are to be performed.
     * @return the rules to be applied.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException if any internal error occurs.
     */
    public List<RuleMetaData> getApplicableRules(String className, Object obj, List<RuleMetaData> rules, boolean executionRealmCheck) throws ApplicationExceptions, FrameworkException;
    
}
