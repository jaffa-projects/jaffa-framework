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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.rules.interceptors;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.jaffa.security.SecurityManager;

import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CXF interceptor that can enforce access rules on web service endpoints.  The interceptor will:
 * - First check if there is a function-guard rule in the rules metadata for the service method
 * - If there is, it will call the SecurityManager that the caller has the permission to access the service method
 */
public class CxfFunctionGuardInterceptor extends AbstractPhaseInterceptor<Message> {

    private static Logger log = Logger.getLogger(CxfFunctionGuardInterceptor.class);

    /**
     * Create the interceptor and register it for the PRE_INVOKE phase
     */
    public CxfFunctionGuardInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    /**
     * Check if the caller has access to the method they are attempting to checkAccess
     *
     * @param message Message sent in the service invocation
     * @throws Fault A fault is thrown if the caller doesn't have permission to use the service
     */
    public void handleMessage(Message message) throws Fault {
        // Extract the service method from the message
        Method serviceMethod = getServiceMethod(message);

        if (serviceMethod != null) {
            try {
                // Check if the user has been granted access to checkAccess this service
                checkAccess(serviceMethod);
            } catch (AccessControlException | ApplicationExceptions | FrameworkException exception) {
                log.error("checkAccess threw exception: " + exception);
                throw new Fault(exception);
            }
        }
    }

    /**
     * Checks if a caller has access to a method being invoked
     *
     * @param method Method being invoked
     * @throws AccessControlException Thrown when the caller doesn't have access
     */
    private void checkAccess(Method method) throws AccessControlException, ApplicationExceptions, FrameworkException {
        String targetClassName = method.getDeclaringClass().getName();

        // Get the rule map
        Map<String, List<RuleMetaData>> ruleMap = getPropertyRuleMap(targetClassName, "function-guard");
        if (ruleMap == null) {
            return;
        }

        // Get the rules
        List<RuleMetaData> rules = ruleMap.get(null);
        if (rules == null) {
            return;
        }

        // Check access for any rules that match
        for (RuleMetaData rule : rules) {
            if (match(method, rule)) {
                checkAccess(method, targetClassName, rule);
            }
        }
    }

    /**
     * Checks if a caller has access to a method being invoked
     *
     * @param method          Method being invoked
     * @param targetClassName Name of the class the method is part of
     * @param rule            Rule metadata to use to check the access
     * @throws AccessControlException Thrown when the caller doesn't have access
     */
    private void checkAccess(Method method, String targetClassName, RuleMetaData rule) throws AccessControlException {
        if (log.isDebugEnabled())
            log.debug("Applying " + rule + " on class " + targetClassName + " method " + method.getName());

        // Use the SecurityManager to check if the user has access
        String name = rule.getParameter("name");
        if (!SecurityManager.checkFunctionAccess(name)) {
            String str = "Access to business-function '" + name + "' is required to be able to checkAccess '" + rule.getParameter("method") + "' on " + targetClassName;
            log.error(str);
            throw new AccessControlException(str);
        }
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * The class-level RuleMetaData instances defined for the className+ruleName combination will be added to the Map with propertyName null.
     * The className is obtained from the targetClass.
     *
     * @param targetClassName The target Class.
     * @param ruleName        the rule to search for.
     * @return A Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    private Map<String, List<RuleMetaData>> getPropertyRuleMap(String targetClassName, String ruleName) throws ApplicationExceptions, FrameworkException {
        if (targetClassName == null) {
            return null;
        }

        Map<String, List<RuleMetaData>> map = MetaDataRepository.instance().getPropertyRuleMap(targetClassName, ruleName);
        if (map == null) {
            return null;
        }

        IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleName);
        Map<String, List<RuleMetaData>> newMap = null;
        for (Map.Entry<String, List<RuleMetaData>> me : map.entrySet()) {
            List<RuleMetaData> rules = ruleHelper.getApplicableRules(targetClassName, null, me.getValue(), true);
            if (rules != null && rules.size() > 0) {
                if (newMap == null) {
                    newMap = new LinkedHashMap<>();
                }
                newMap.put(me.getKey(), rules);
            }
        }

        return newMap;
    }

    /**
     * Extracts the Method that will be invoked by the service from the Message object
     *
     * @param message Message to extract the method from
     * @return Method that will be invoked by the service from the Message object
     */
    private Method getServiceMethod(Message message) {
        Exchange exchange = message.getExchange();
        BindingOperationInfo bindingOperationInfo = exchange.get(BindingOperationInfo.class);
        Service service = exchange.get(Service.class);
        String dispatcherName = MethodDispatcher.class.getName();
        MethodDispatcher methodDispatcher = (MethodDispatcher)service.get(dispatcherName);

        Method method = null;
        if (methodDispatcher != null) {
            method = methodDispatcher.getMethod(bindingOperationInfo);
        }

        if (method == null) {
            OperationResourceInfo resourceInfo = exchange.get(OperationResourceInfo.class);
            if (resourceInfo != null) {
                method = resourceInfo.getMethodToInvoke();
            }
        }
        return method;
    }

    /**
     * Returns true if the input Method matches the given pointcut.
     *
     * @param method A Method instance to be matched.
     * @param rule   Rule to check for a match
     * @return true if the input Method matches the given pointcut.
     */
    private boolean match(Method method, RuleMetaData rule) {
        if (method == null || rule == null) {
            return false;
        }

        // Get the method from the AOP rule
        String ruleMethod = rule.getParameter("method");
        if (ruleMethod == null) {
            return false;
        }

        // If the method name has a parameter list remove it and just match on the method name
        if (ruleMethod.indexOf('(') > 0) {
            ruleMethod = ruleMethod.substring(0, ruleMethod.indexOf('('));
        }

        if (method.getName().equalsIgnoreCase(ruleMethod)) {
            if (log.isDebugEnabled())
                log.debug("Matched");
            return true;
        } else {
            if (log.isDebugEnabled())
                log.debug("Does not match");
            return false;
        }
    }
}