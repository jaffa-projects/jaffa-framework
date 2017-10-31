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

package org.jaffa.rules;


/**
 * This is the interface to the Rules Engine.
 *
 * @author GautamJ
 */
public interface IRulesEngine {

    /**
     * Creates an instance of IObjectRuleIntrospector.
     * This method should be used if the source object is available.
     * The source object can then be used for setting up the context for the 'condition' checks, if any.
     * The className is obtained from the source object.
     *
     * @param obj The Object being introspected.
     * @return an instance of IObjectRuleIntrospector.
     */
    IObjectRuleIntrospector getObjectRuleIntrospector(Object obj);

    /**
     * Creates an instance of IObjectRuleIntrospector.
     * This method should be used if an instance of the source class is not available.
     * Consequently, the context for the 'condition' checks, if any, will not have the instance information.
     *
     * @param clazz The Class being introspected.
     * @return an instance of IObjectRuleIntrospector.
     */
    IObjectRuleIntrospector getObjectRuleIntrospector(Class clazz);

    /**
     * Creates an instance of IObjectRuleIntrospector.
     *
     * @param className the class, virtual or real, being introspected.
     * @param obj       The Object being introspected.
     * @return an instance of IObjectRuleIntrospector.
     */
    IObjectRuleIntrospector getObjectRuleIntrospector(String className, Object obj);

    /**
     * Creates an instance of IObjectRuleIntrospector.
     *
     * @param className the class, virtual or real, being introspected.
     * @return an instance of IObjectRuleIntrospector.
     */
    IObjectRuleIntrospector getAuditRuleIntrospector(String className);


    /**
     * Creates an instance of IPropertyRuleIntrospector.
     * This method should be used if the source object is available.
     * The source object can then be used for setting up the context for the 'condition' checks, if any.
     * The className is obtained from the source object.
     *
     * @param obj          The Object being introspected.
     * @param propertyName The property name.
     * @return an instance of IPropertyRuleIntrospector.
     */
    IPropertyRuleIntrospector getPropertyRuleIntrospector(Object obj, String propertyName);

    /**
     * Creates an instance of IPropertyRuleIntrospector.
     * This method should be used if an instance of the source class is not available.
     * Consequently, the context for the 'condition' checks, if any, will not have the instance information.
     *
     * @param clazz        The Class being introspected.
     * @param propertyName The property name.
     * @return an instance of IPropertyRuleIntrospector.
     */
    IPropertyRuleIntrospector getPropertyRuleIntrospector(Class clazz, String propertyName);

    /**
     * Creates an instance of IObjectRuleIntrospector.
     *
     * @param className    the class, virtual or real, being introspected.
     * @param propertyName The property name.
     * @param obj          The Object being introspected.
     * @return an instance of IObjectRuleIntrospector.
     */
    IPropertyRuleIntrospector getPropertyRuleIntrospector(String className, String propertyName, Object obj);

    /**
     * Returns an array of classNames that have the input rule.
     *
     * @param ruleName the rule name.
     * @return an array of classNames that have the input rule.
     */
    String[] getClassNamesByRuleName(String ruleName);

    /**
     * Clears the "Condition Evaluation Cache".
     * This method should be invoked at the start of each thread execution.
     * An implementation may choose to ignore this call, as it may not need caching.
     */
    void clearConditionEvaluationCache();
}
