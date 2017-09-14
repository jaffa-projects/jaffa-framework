/*
 *  ====================================================================
 *  JAFFA - Java Application Framework For All
 *
 *  Copyright (c) 2017 JAFFA Development Group
 *
 *      This library is free software; you can redistribute it and/or
 *      modify it under the terms of the GNU Lesser General Public
 *      License as published by the Free Software Foundation; either
 *      version 2.1 of the License, or (at your option) any later version.
 *
 *      This library is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *      Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public
 *      License along with this library; if not, write to the Free Software
 *      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Redistribution and use of this software and associated documentation ("Software"),
 *  with or without modification, are permitted provided that the following conditions are met:
 *  1.	Redistributions of source code must retain copyright statements and notices.
 *          Redistributions must also contain a copy of this document.
 *  2.	Redistributions in binary form must reproduce the above copyright notice,
 *  	this list of conditions and the following disclaimer in the documentation
 *  	and/or other materials provided with the distribution.
 *  3.	The name "JAFFA" must not be used to endorse or promote products derived from
 *  	this Software without prior written permission. For written permission,
 *  	please contact mail to: jaffagroup@yahoo.com.
 *  4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  	appear in their names without prior written permission.
 *  5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 */

package org.jaffa.soa.dataaccess;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.modules.messaging.services.HeaderParam;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.soa.services.RaiseEventService;
import org.jaffa.util.BeanHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base class for all handlers that want to internally fire SOA Events based on an injected rule.
 * <p/>
 * Created by ndzwill on 9/8/2017.
 */
public abstract class SOAEventBaseHandler {

    protected static Logger log = Logger.getLogger(SOAEventBaseHandler.class);

    // the raise SOA event rule meta data
    protected RuleMetaData ruleMetaData;

    /**
     * Set the rule meta data for the handler
     *
     * @param ruleMetaData the rule meta data for the handler
     */
    public SOAEventBaseHandler(RuleMetaData ruleMetaData) {
        this.ruleMetaData = ruleMetaData;
    }

    /**
     * Check if the method being invoked is
     *
     * @param invocationMethod method being invoked
     * @param target           the target object
     * @throws ApplicationExceptions
     * @throws FrameworkException
     */
    protected void raiseSOAEvent(String invocationMethod, Object target) throws ApplicationExceptions, FrameworkException {
        raiseSOAEvent(invocationMethod, target, null);
    }

    protected void raiseSOAEvent(String invocationMethod, Object target, UOW uow) throws ApplicationExceptions, FrameworkException {
        raiseSOAEvent(invocationMethod, target, uow, null);
    }
    /**
     * Check if the method being invoked on the handler is the trigger method defined in the rule meta data then raise the event
     *
     * @param invocationMethod method being invoked
     * @param target           the target object
     * @param uow              unit of work for the life cycle event handler
     * @throws ApplicationExceptions
     * @throws FrameworkException
     */
    protected void raiseSOAEvent(String invocationMethod, Object target, UOW uow, Object[] args) throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled()) {
            log.debug("Handle Event : " + invocationMethod + "  for " + " (Target=" + shortClassName(target) + ")");
        }

        if (invocationMethod.equals(ruleMetaData.getParameter(RuleMetaData.PARAMETER_TRIGGER))) {

            // Need to check the condition
            List<RuleMetaData> rules = new ArrayList<>();
            rules.add(ruleMetaData);

            IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleMetaData.getName());

            // Check if the rule is applicable for the target object (evaluates rule condition)
            List<RuleMetaData> applicableRules = ruleHelper.getApplicableRules(target.getClass().getName(), target, rules, false);

            // raise event for each applicableRule
            if (applicableRules != null) {
                for (RuleMetaData rule : applicableRules) {
                    raiseSOAEvent(uow, target, rule, args);
                }
            }
        }
    }

    /**
     * Raises the SOA event defined in the rule meta data
     *
     * @param uow          UOW for handler life cycle event
     * @param targetObject the target object
     * @param rule         the raise soa event rule meta data
     * @throws ApplicationExceptions
     * @throws FrameworkException
     */
    private void raiseSOAEvent(UOW uow, Object targetObject, RuleMetaData rule, Object[] args) throws ApplicationExceptions, FrameworkException {
        UOW localUow = uow;
        if (uow == null || !uow.isActive()) {
            localUow = getUOW(targetObject);
        }

        if (log.isDebugEnabled()) {
            log.debug("Applying " + rule + " on " + targetObject);
        }

        // Add Parameters for the SOAEvent
        List<HeaderParam> headerParamsList = new ArrayList<>();
        headerParamsList.addAll(createSOAEventParameters(rule.getParameter("staticParameters"), null, null, null));
        headerParamsList.addAll(createSOAEventParameters(rule.getParameter("dynamicParameters"), targetObject, args, rule));

        RaiseEventService raiseEventService = new RaiseEventService();
        raiseEventService.raiseSoaEvent(localUow, rule.getParameter("eventName"), rule.getParameter("description"),
                rule.getParameter("category"), headerParamsList);
    }

    /**
     * Creates the required SOAEventParam header params, based on the input
     *
     * @param parameters   A semi-colon separated list of parameters (name-value pairs).
     * @param targetObject The target Object. Parameters will be considered static if this argument is null.
     * @throws FrameworkException exception if bean script fails
     */
    private List<HeaderParam> createSOAEventParameters(String parameters, Object targetObject, Object args, RuleMetaData rule) throws ApplicationExceptions {
        List<HeaderParam> headerParamsList = new ArrayList<>();
        if (parameters != null) {
            for (String parameter : parameters.split(";")) {
                if (log.isDebugEnabled())
                    log.debug("Handling parameter: " + parameter);
                int i = parameter.indexOf('=');
                if (i <= 0 || i == (parameter.length() - 1)) {
                    String str = "Illegal argument passed: " + parameters;
                    log.error(str);
                    throw new IllegalArgumentException(str);
                }
                String name = parameter.substring(0, i);
                String value = parameter.substring(i + 1);
                if (targetObject != null) {
                    try {
                        Object fieldValue = BeanHelper.getField(targetObject, value);
                        value = format(fieldValue);
                    } catch (NoSuchMethodException e) {
                        // Assume the dynamic parameter to be a script
                        Map beans = new HashMap();
                        beans.put(ScriptHelper.CONTEXT_BEAN, targetObject);
                        if (args!=null){
                            beans.put(ScriptHelper.CONTEXT_ARGUMENTS, args);
                        }
                        try {
                            Object fieldValue = ScriptHelper.instance(rule.getParameter(RuleMetaData.PARAMETER_LANGUAGE)).evaluate(null, value, beans,
                                    rule.getSource(), rule.getLine() != null ? rule.getLine() : 0, 0);
                            value = format(fieldValue);
                        } catch (Throwable t) {
                            throw new ApplicationExceptions(new ApplicationException(t.getLocalizedMessage(), null, t));
                        }
                    }
                    if (log.isDebugEnabled())
                        log.debug("Value is: " + value);
                }

                HeaderParam headerParam = new HeaderParam(name, value);
                if (log.isDebugEnabled())
                    log.debug("Created " + headerParam);

                headerParamsList.add(headerParam);
            }
        }
        return headerParamsList;
    }

    /**
     * Converts date objects to strings
     *
     * @param fieldValue the date object to be converted
     * @return formatted string representation of the date object
     */
    private String format(Object fieldValue) {
        if (fieldValue instanceof DateOnly) {
            return Formatter.format((DateOnly) fieldValue, "yyyy-MM-dd");
        } else if (fieldValue instanceof DateTime) {
            return Formatter.format((DateTime) fieldValue, "yyyy-MM-dd'T'HH:mm:ss");
        } else {
            return Formatter.format(fieldValue);
        }
    }

    /**
     * Get the UOW, if present, from the input Object.
     * A null will be returned, if the input Object has no such property.
     *
     * @param targetObject The target Object.
     * @return Value of UOW on the target Object.
     */
    protected UOW getUOW(Object targetObject) {
        UOW uow = null;
        if (targetObject instanceof IPersistent)
            uow = ((IPersistent) targetObject).getUOW();
        else if (targetObject instanceof FlexBean) {
            IPersistent persistentObject = ((FlexBean) targetObject).getPersistentObject();
            if (persistentObject != null)
                uow = persistentObject.getUOW();
        } else {
            try {
                Object obj = BeanHelper.getField(targetObject, "UOW");
                if (obj instanceof UOW)
                    uow = (UOW) obj;
            } catch (NoSuchMethodException e) {
                // do nothing.. will simply return a null.
            }
        }
        return uow;
    }

    /**
     * Gets the short class name of the input object
     */
    private String shortClassName(Object o) {
        return o != null ? o.getClass().getSimpleName() : null;
    }
}
