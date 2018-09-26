/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.rules.util;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexProperty;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.IRuleEvaluator;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * A class that uses meta-data rules to act upon an object.
 */
public class RuleActor<T> {
    private static Logger log = Logger.getLogger(RuleActor.class);
    // The following fields are needed to avoid a reverse dependency on the JaffaSOA module
    private static Class c_graphDataObjectClass;
    private static Method c_hasGraphDataObjectChanged;
    private static Method c_hasGraphDataPropertyChanged;
    private static Method c_getGraphDataPropertyOriginalValue;

    static {
        try {
            c_graphDataObjectClass = Class.forName("org.jaffa.soa.graph.GraphDataObject");
            c_hasGraphDataObjectChanged = c_graphDataObjectClass != null ? c_graphDataObjectClass.getMethod("hasChanged") : null;
            c_hasGraphDataPropertyChanged = c_graphDataObjectClass != null ? c_graphDataObjectClass.getMethod("hasChanged", String.class) : null;
            c_getGraphDataPropertyOriginalValue = c_graphDataObjectClass != null ? c_graphDataObjectClass.getMethod("getOriginalValue", String.class) : null;
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Exception thrown while trying to get a handle on the GraphDataObject's methods for determining if the object and/or it's properties have been modified", e);
            }
        }
    }

    private Map<String, List<RuleMetaData>> ruleMap = new HashMap<>();
    private String name;
    private IRuleEvaluator ruleEvaluator;

    /**
     * Constructs a new rule actor.
     *
     * @param name the name of the rule actor.
     */
    protected RuleActor(String name) {
        this.name = name;
    }

    /**
     * Sets the actor's rule map from meta-data. This is the same as calling
     * setRuleMap(getPropertyMap(className, getName()))
     *
     * @param classNames the names of the class hierarchy this rule actor is for.
     */
    public void initializeRuleMapFromMetaData(List<String> classNames) throws FrameworkException, ApplicationExceptions {
        for (String className : classNames) {
            Map<String, List<RuleMetaData>> classRuleMap = getPropertyRuleMap(className, name);
            if (classRuleMap != null) {
                this.ruleMap.putAll(classRuleMap);
            }
        }
    }

    /**
     * Gets the name of the rule actor.
     *
     * @return the name of the rule actor.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the map of property names to rules.
     *
     * @return the map of property names to rules.
     */
    public Map<String, List<RuleMetaData>> getRuleMap() {
        return ruleMap;
    }

    /**
     * Sets the map of property names to rules.
     *
     * @param ruleMap the map of property names to rules.
     */
    public void setRuleMap(Map<String, List<RuleMetaData>> ruleMap) {
        // Eliminate entries with null property names or empty lists. Neither should be the case, but this
        // was a run-time check in the original code so it may have been done for a reason.
        this.ruleMap = new HashMap<>();
        if (ruleMap != null) {
            for (Map.Entry<String, List<RuleMetaData>> entry : ruleMap.entrySet()) {
                String property = entry.getKey();
                List<RuleMetaData> rules = entry.getValue();
                if (rules != null && !rules.isEmpty()) {
                    this.ruleMap.put(property, rules);
                }
            }
        }
    }

    /**
     * Gets the rule evaluator.
     *
     * @return the rule evaluator.
     */
    public IRuleEvaluator getRuleEvaluator() {
        return ruleEvaluator;
    }

    /**
     * Sets the rule evaluator.
     *
     * @param ruleEvaluator the rule evaluator.
     */
    public void setRuleEvaluator(IRuleEvaluator ruleEvaluator) {
        this.ruleEvaluator = ruleEvaluator;
    }

    /**
     * Trims rules that have a condition that is not met.
     *
     * @param targetObject the target object.
     * @param rules        the rules.
     * @return a list of applicable rules, or null.
     * @throws org.jaffa.exceptions.FrameworkException
     *          if any internal error occurs.
     */
    protected List<RuleMetaData> trimInapplicableRules(Object targetObject, List<RuleMetaData> rules) throws FrameworkException {
        try {
            List<RuleMetaData> applicableRules = null;
            if (rules.size() == 1) {
                // Optimization for the most common case; 1 rule with no condition.
                if (checkCondition(targetObject, rules.get(0))) {
                    return rules;
                }
            } else {
                applicableRules = new ArrayList<>();
                for (RuleMetaData rule : rules) {
                    if (checkCondition(targetObject, rule)) {
                        applicableRules.add(rule);
                    }
                }
            }
            return applicableRules;
        } catch (ApplicationExceptions e) {
            throw new JaffaRulesFrameworkException(e.getMessage(), null, e);
        }
    }

    /**
     * Checks the condition of the object to determine if the specified rule is applicable.
     *
     * @param targetObject the target object.
     * @param rule         the rule to be applied.
     * @return true if the rule should be applied.
     * @throws FrameworkException    if any framework error occurs.
     * @throws ApplicationExceptions if any application exception occurs.
     */
    protected boolean checkCondition(Object targetObject, RuleMetaData rule) throws FrameworkException, ApplicationExceptions {
        return ruleEvaluator == null || ruleEvaluator.checkCondition(targetObject, rule);
    }

    /**
     * A convenience method to return the object label to be used in error messages.
     * It'll check for the label in the following order
     * - Lookup a 'label' rule for the input class
     * - Return the class name
     *
     * @param targetClassName The target Class.
     * @param targetObject    The target Object.
     * @return label for the class.
     */
    protected String getObjectLabel(String targetClassName, Object targetObject) {
        String label = null;
        try {
            // Search for the first available class-level label rule
            Map<String, List<RuleMetaData>> pkMap = getPropertyRuleMap(targetClassName, targetObject, "label");
            if (pkMap != null && pkMap.containsKey(null)) {
                List<RuleMetaData> rules = pkMap.get(null);
                if (rules != null && rules.size() > 0) {
                    RuleMetaData rule = rules.get(0);
                    label = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                }
            }
        } catch (Exception e) {
            // do nothing
        }

        // See if this can be resolved with a persistence meta class
        if (label == null) {
            try {
                label = PersistentHelper.getLabelToken(targetClassName);
            } catch (Exception e) {
                // do nothing
            }
        }

        // Finally default to the simple class name
        if (label == null) {
            label = StringHelper.getShortClassName(targetClassName);
        }

        return label;
    }

    /**
     * A convenience method to return the label of the key fields for the input domain class, to be used in error messages.
     * For a composite key, the labels will be comma-separated.
     * It'll check for the label in the following order
     * - First looks up the 'primary-key' rule for the input class. A null will be returned if the key is not defined.
     * - Lookup a 'label' rule for each key field. The field name will be returned if the label is not defined.
     *
     * @param targetClassName The target Class.
     * @param targetObject    The target Object.
     * @return label of the key fields for the input domain class.
     */
    protected String getPrimaryKeyLabel(String targetClassName, Object targetObject) {
        String label = null;
        try {
            String[] primaryKey = null;

            // Search for the first available class-level primary-key rule
            Map<String, List<RuleMetaData>> pkMap = getPropertyRuleMap(targetClassName, targetObject, "primary-key");
            if (pkMap != null && pkMap.containsKey(null)) {
                List<RuleMetaData> rules = pkMap.get(null);
                if (rules != null && rules.size() > 0) {
                    RuleMetaData rule = rules.get(0);
                    String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                    if (value != null) {
                        primaryKey = value.split(",");
                    }
                }
            }

            if (primaryKey != null) {
                StringBuilder primaryKeyLabel = new StringBuilder();
                for (int i = 0; i < primaryKey.length; i++) {
                    if (i > 0) {
                        primaryKeyLabel.append(',');
                    }
                    primaryKeyLabel.append(getPropertyLabel(targetObject, primaryKey[i]));
                }
                label = primaryKeyLabel.toString();
            }
        } catch (Exception e) {
            // do nothing
        }
        return label;
    }

    /**
     * Returns true if the input Object has been modified.
     * This check can only be performed on instances of IPersistent and GraphDataObject.
     * On all other instances, this check is carried out of a 'public boolean hasChanged()' method exists.
     * A true will be returned otherwise.
     *
     * @param targetObject the object to check.
     * @return true if the input Object has been modified.
     */
    protected boolean isModified(Object targetObject) {
        if (targetObject instanceof IPersistent)
            return ((IPersistent) targetObject).isModified();
        else if (c_graphDataObjectClass != null && c_hasGraphDataObjectChanged != null && c_graphDataObjectClass.isInstance(targetObject)) {
            try {
                return (Boolean) c_hasGraphDataObjectChanged.invoke(targetObject);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the hasChanged() method of the GraphDataObject to determine if the object has been modified. Will assume it to be modified", e);
                }
            }
        } else {
            try {
                // invoke 'public boolean hasChanged()', if available
                Method m = targetObject.getClass().getMethod("hasChanged");
                if (m.getReturnType() == Boolean.class || m.getReturnType() == Boolean.TYPE) {
                    return (Boolean) m.invoke(targetObject);
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the hasChanged() method of " + targetObject + " to determine if the object has been modified. Will assume it to be modified", e);
                }
            }
        }
        return true;
    }

    /**
     * Returns true if a given property of the input Object has been modified.
     * This check can only be performed on instances of IPersistent and GraphDataObject.
     * On all other instances, this check is carried out of a 'public boolean hasChanged(String property)' method exists.
     * A true will be returned otherwise.
     *
     * @param targetObject       the object to check.
     * @param targetPropertyName the property to check.
     * @return true if a given property of the input Object has been modified.
     */
    protected boolean isModified(Object targetObject, String targetPropertyName) {
        if (targetObject instanceof IPersistent)
            return (!((IPersistent) targetObject).isDatabaseOccurence() || ((IPersistent) targetObject).isModified(StringHelper.getUpper1(targetPropertyName)));
        else if (c_graphDataObjectClass != null && c_hasGraphDataPropertyChanged != null && c_graphDataObjectClass.isInstance(targetObject)) {
            try {
                return (Boolean) c_hasGraphDataPropertyChanged.invoke(targetObject, targetPropertyName);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the hasChanged(\"" + targetPropertyName + "\") method of the GraphDataObject to determine if the property has been modified. Will assume it to be modified", e);
                }
            }
        } else {
            try {
                // invoke 'public boolean hasChanged(String property)', if available
                Method m = targetObject.getClass().getMethod("hasChanged", String.class);
                if (m.getReturnType() == Boolean.class || m.getReturnType() == Boolean.TYPE)
                    return (Boolean) m.invoke(targetObject, targetPropertyName);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the hasChanged(\"" + targetPropertyName + "\") method of " + targetObject + " to determine if the property has been modified. Will assume it to be modified", e);
                }
            }
        }
        return true;
    }

    /**
     * Returns the original value of a given property of the input Object before it was modified.
     * This method is currently supported only on instances of IPersistent and GraphDataObject.
     * On all other instances, the 'public Object getOriginalValue(String property)' method is invoked, if available.
     * A null will be returned otherwise.
     *
     * @param targetObject       the object to check.
     * @param targetPropertyName the property to check.
     * @return the original value of a given property of the input Object before it was modified.
     */
    protected Object returnInitialValue(Object targetObject, String targetPropertyName) {
        if (targetObject instanceof IPersistent) {
            return ((IPersistent) targetObject).returnInitialValue(StringHelper.getUpper1(targetPropertyName));
        } else if (c_graphDataObjectClass != null && c_hasGraphDataPropertyChanged != null && c_graphDataObjectClass.isInstance(targetObject)) {
            try {
                return c_getGraphDataPropertyOriginalValue.invoke(targetObject, targetPropertyName);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the getOriginalValue(\"" + targetPropertyName + "\") method of the GraphDataObject to determine it's initial value. Will assume it to be null", e);
                }
            }
        } else {
            try {
                // invoke 'public Object getOriginalValue(String property)', if available
                Method m = targetObject.getClass().getMethod("getOriginalValue", String.class);
                if (m.getReturnType() != Void.class) {
                    return m.invoke(targetObject, targetPropertyName);
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to invoke the getOriginalValue(\"" + targetPropertyName + "\") method of " + targetObject + " to determine it's initial value. Will assume it to be null", e);
                }
            }
        }
        return null;
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * The class-level RuleMetaData instances defined for the className+ruleName combination will be added to the Map with propertyName null.
     * The className is obtained from the targetClass.
     * The ruleName is obtained by the invocation of the getName() method.
     *
     * @param targetClassName The target Class.
     * @param targetObject    The target Object.
     * @return a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    protected Map<String, List<RuleMetaData>> getPropertyRuleMap(String targetClassName, Object targetObject) throws ApplicationExceptions, FrameworkException {
        return getPropertyRuleMap(targetClassName, targetObject, getName());
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * The class-level RuleMetaData instances defined for the className+ruleName combination will be added to the Map with propertyName null.
     * The className is obtained from the targetClass.
     *
     * @param targetClassName The target Class.
     * @param targetObject    The target Object.
     * @param ruleName        the rule to search for.
     * @return a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    protected Map<String, List<RuleMetaData>> getPropertyRuleMap(String targetClassName, Object targetObject, String ruleName) throws ApplicationExceptions, FrameworkException {
        if (targetClassName != null) {
            Map<String, List<RuleMetaData>> map = MetaDataRepository.instance().getPropertyRuleMap(targetClassName, ruleName);
            if (map != null) {
                IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleName);
                Map<String, List<RuleMetaData>> newMap = null;
                for (Map.Entry<String, List<RuleMetaData>> me : map.entrySet()) {
                    List<RuleMetaData> rules = ruleHelper.getApplicableRules(targetClassName, targetObject, me.getValue(), true);
                    if (rules != null && rules.size() > 0) {
                        if (newMap == null) {
                            newMap = new LinkedHashMap<>();
                        }
                        newMap.put(me.getKey(), rules);
                    }
                }
                map = newMap;
            }
            return map;
        } else {
            return null;
        }
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * The class-level RuleMetaData instances defined for the className+ruleName combination will be added to the Map with propertyName null.
     * The className is obtained from the targetClass.
     *
     * @param targetClassName The target Class.
     * @param ruleName        the rule to search for.
     * @return a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * @throws ApplicationExceptions if any application exception occurs.
     * @throws FrameworkException    if any internal error occurs.
     */
    protected Map<String, List<RuleMetaData>> getPropertyRuleMap(String targetClassName, String ruleName) throws ApplicationExceptions, FrameworkException {
        if (targetClassName != null) {
            Map<String, List<RuleMetaData>> map = MetaDataRepository.instance().getPropertyRuleMap(targetClassName, ruleName);
            if (map != null) {
                IRuleHelper ruleHelper = RuleHelperFactory.instance(ruleName);
                Map<String, List<RuleMetaData>> newMap = null;
                for (Map.Entry<String, List<RuleMetaData>> me : map.entrySet()) {
                    List<RuleMetaData> rules = ruleHelper.getApplicableRules(targetClassName, me.getValue(), true);
                    if (rules != null && rules.size() > 0) {
                        if (newMap == null) {
                            newMap = new LinkedHashMap<>();
                        }
                        newMap.put(me.getKey(), rules);
                    }
                }
                map = newMap;
            }
            return map;
        } else {
            return null;
        }
    }

    /**
     * A convenience method to return the property label to be used in error messages.
     * It'll check for the label in the following order
     * - Lookup a 'label' rule for this property
     * - Return the property name
     *
     * @param targetObject       The target Object.
     * @param targetPropertyName The property for which the label is to be found.
     * @return label for the property.
     */
    protected String getPropertyLabel(Object targetObject, String targetPropertyName) {
        String label = null;
        String targetClassName = getActualClassName(targetObject.getClass());
        try {
            if (targetObject instanceof FlexBean) {
                FlexBean flexBean = (FlexBean) targetObject;
                FlexClass flexClass = (FlexClass) flexBean.getDynaClass();
                FlexProperty flexProperty = flexClass.getDynaProperty(targetPropertyName);
                if (flexProperty == null && ((FlexBean) targetObject).getPersistentObject() != null) {
                    targetObject = ((FlexBean) targetObject).getPersistentObject();
                    targetClassName = targetObject.getClass().getName();
                } else if (flexProperty != null && ((FlexBean) targetObject).getPersistentObject() == null) {
                    targetClassName = flexClass.getName();
                }
            } else if (targetObject instanceof IFlexFields) {
                if (((IFlexFields) targetObject).getFlexBean() != null && ((IFlexFields) targetObject).getFlexBean().getDynaClass().getDynaProperty(targetPropertyName) != null) {
                    targetObject = ((IFlexFields) targetObject).getFlexBean();
                    targetClassName = ((FlexBean) targetObject).getDynaClass().getName();
                }
            }
            // Search for the first available property-level label rule
            Map<String, List<RuleMetaData>> pkMap = getPropertyRuleMap(targetClassName, targetObject, "label");
            if (pkMap != null && pkMap.containsKey(targetPropertyName)) {
                List<RuleMetaData> rules = pkMap.get(targetPropertyName);
                if (rules != null && rules.size() > 0) {
                    RuleMetaData rule = rules.get(0);
                    label = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
                }
            }
        } catch (Exception e) {
            // do nothing
        }

        if (label == null) {
            label = targetPropertyName;
        }

        return label;
    }

    /**
     * This is a helper method to determine the actual class from the input Class,
     * since the input Class could represent a dynamic proxy.
     * The input Class is returned as is, if it is not a dynamic proxy.
     *
     * @param clazz The input Class.
     * @return The actual Class.
     */
    public String getActualClassName(Class clazz) {
        if (Proxy.isProxyClass(clazz)) {
            for (Class interfaceClass : clazz.getInterfaces()) {
                if (interfaceClass != IPersistent.class) {
                    clazz = interfaceClass;
                    break;
                }
            }
        }
        return clazz.getName();
    }

    /**
     * Gets an error code for the specified rule.
     *
     * @param defaultCode the code to use if the rule has none.
     * @param rule        the rule.
     * @return the error code.
     */
    protected String getErrorCode(String defaultCode, RuleMetaData rule) {
        String errorCode = rule.getParameter("errorCode");
        return errorCode != null ? errorCode : defaultCode;
    }

    /**
     * Returns an array of strings containing exception arguments
     *
     * @param targetObject The target object
     * @param rule         The rule that caused the exception
     * @return an array of strings containing exception arguments
     */
    protected Object[] getErrorArgumentArray(Object targetObject, RuleMetaData rule) {
        ArrayList<String> arguments = new ArrayList<>();

        String errorParamList = rule.getParameter("errorParameters");
        if (errorParamList != null && errorParamList.length() > 0) {
            String[] errorParams = errorParamList.split(",");
            for (String errorParam : errorParams) {
                String paramValue;
                try {
                    // invoke the GET method and add the value to the argument list
                    paramValue = org.jaffa.datatypes.Formatter.format(BeanHelper.getField(targetObject, errorParam));
                } catch (Exception e) {
                    // suppress error
                    paramValue = "{" + errorParam + "}";
                }
                arguments.add(paramValue != null ? paramValue : "");
            }
        }

        return arguments.toArray();
    }

    /**
     * If a custom errorCode (with optional errorParameters) has been declared for the input rule,
     * a new ApplicationException will be returned; else a new exception of type <S> will be created and
     * returned.
     *
     * @param exception    The exception class type.
     * @param targetObject The target Object.
     * @param rule         The rule being applied.
     * @return a new ApplicationException, if an errorCode was declared on the rule, or the input exception as-is.
     */
    public <S extends ApplicationException> ApplicationException wrapException(Class<S> exception,
                                                                               T targetObject,
                                                                               String targetPropertyName,
                                                                               RuleMetaData rule) {

        S newException = null;
        String errorCode = rule.getParameter("errorCode");
        Object[] baseArguments = getErrorArgumentArray(targetObject, rule);
		Object[] arguments = new Object[]{ rule.getParameter(RuleMetaData.PARAMETER_VALUE) };
        String propertyLabel = getPropertyLabel(targetObject, targetPropertyName);

        try {
            newException = exception.getDeclaredConstructor(String.class, Object[].class).newInstance(propertyLabel, arguments);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            if (log.isDebugEnabled()) {
                log.debug("Error creating a new ApplicationException with errorCode: " + errorCode, e);
            }
        }

        if (errorCode != null) {
            return new ApplicationException(errorCode, baseArguments, newException);
        }

        return newException;
    }

    /**
     * If a custom errorCode (with optional errorParameters) has been declared for the input rule,
     * a new ApplicationException will be returned; else the input exception will be
     * returned as-is.
     *
     * @param exception    The original exception.
     * @param targetObject The target Object.
     * @param rule         The rule being applied.
     * @return a new ApplicationException, if an errorCode was declared on the rule, or the
     *         input exception as-is.
     */
    protected ApplicationException wrapException(ApplicationException exception, T targetObject, RuleMetaData rule) {
        String errorCode = rule.getParameter("errorCode");

        if (errorCode != null) {
            Object[] arguments = getErrorArgumentArray(targetObject, rule);
            exception = new ApplicationException(errorCode, arguments, exception);
        }

        return exception;
    }

    /**
     * Get the UOW, if present, from the input object.
     * A null will be returned, if the input object has no such property.
     *
     * @param targetObject the target object.
     * @return the value of the UOW on the target object.
     */
    protected UOW getUOW(Object targetObject) {
        UOW uow = null;
        if (targetObject instanceof IPersistent) {
            uow = ((IPersistent) targetObject).getUOW();
        } else if (targetObject instanceof FlexBean) {
            IPersistent persistentObject = ((FlexBean) targetObject).getPersistentObject();
            if (persistentObject != null) {
                uow = persistentObject.getUOW();
            }
        } else {
            try {
                Object obj = BeanHelper.getField(targetObject, "UOW");
                if (obj instanceof UOW) {
                    uow = (UOW) obj;
                }
            } catch (NoSuchMethodException e) {
                // do nothing.. will simply return a null.
            }
        }
        return uow;
    }

    /**
     * Overrides base method. Hashcode is calculated by using the name provided.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    /**
     * Overrides base method.  Equals is determined by using the name provided.
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof RuleActor) {
            RuleActor helper = (RuleActor) o;
            return getName().equals(helper.getName());
        }
        return false;
    }
}
