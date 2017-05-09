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
package org.jaffa.rules.validators;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.RuleActorFactory;
import org.jaffa.rules.fieldvalidators.Validator;
import org.jaffa.rules.fieldvalidators.ValidatorFactory;
import org.jaffa.rules.meta.ClassMetaData;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.PropertyMetaData;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for object validators that are based on meta-data rules.
 */
public class RuleValidatorFactory extends RuleActorFactory<Validator> implements ValidatorFactory, ApplicationContextAware {
    private static final Logger logger = Logger.getLogger(RuleValidatorFactory.class);
    private ApplicationContext appContext;
    private List<String> validatorTypes = new ArrayList<>();

    /**
     * Gets the validator type list
     *
     * @return the validator type list
     */
    public List<String> getValidatorTypes() {
        return this.validatorTypes;
    }

    /**
     * Sets the validator type list
     *
     * @param validatorTypes the validator type list
     */
    public void setValidatorTypes(List<String> validatorTypes) {
        if (validatorTypes == null) {
            logger.error("Validator Type cannot be null.");
        }
        this.validatorTypes = validatorTypes;
    }

    /**
     * Adds the types to the validator type list
     *
     * @param types the types to add
     */
    public void addValidatorTypes(String... types) {
        for (String type : types) {
            validatorTypes.add(type);
        }
    }

    /**
     * Set the application context used to lookup validators by name.
     *
     * @param applicationContext the application context.
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    /**
     * Gets the validator for this object that matches with the object's classname, realm and
     * variation within the current thread context.
     *
     * @param object the object to act upon.
     * @return an actor, or null if none can be found or created.
     */
    @Override
    public Validator getValidator(Object object) {
        return getActor(object);
    }

    /**
     * Returns a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * The class-level RuleMetaData instances defined for the className+ruleName combination will be added to the Map with propertyName null.
     * The className is obtained from the targetClass.
     *
     * @param targetClassName The target Class.
     * @param ruleName        the rule to search for.
     * @return a Map containing a List of RuleMetaData instances per propertyName for the className+ruleName combination.
     * @throws org.jaffa.exceptions.ApplicationExceptions
     *          if any application exception occurs.
     * @throws org.jaffa.exceptions.FrameworkException
     *          if any internal error occurs.
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
                        if (newMap == null)
                            newMap = new LinkedHashMap<>();
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
     * Creates an actor.
     *
     * @param object the object to act upon.
     * @return an actor.
     */
    @Override
    protected Validator createActor(Object object) {
        // Return null if no validator found that corresponds to the combination
        // of classname, realm, and variation.
        if (object != null) {
            String className = object.getClass().getName();
            List<ClassMetaData> metaDataList = MetaDataRepository.instance().getClassMetaDataListByClassName(className);
            if (metaDataList != null) {
                synchronized (syncObject) {
                    return createValidator(className, metaDataList);
                }
            }
        }
        return null;
    }

    /**
     * Creates a validator.
     *
     * @param className    the name of the class to create a validator for.
     * @param metaDataList the class meta data to create a validator from.
     * @return a validator.
     */
    private Validator<?> createValidator(String className, List<ClassMetaData> metaDataList) {
        // create a validator to collect all rule validators for this class
        BatchValidator<?> resultValidator = new BatchValidator();
        // Iterate through class meta data to extract rules property map for each property
        // associated with the class
        for (ClassMetaData classMetaData : metaDataList) {
            // Retrieve class-level rules
            List<RuleMetaData> allRulesForClass = classMetaData.getClassRules();
            if(allRulesForClass != null){
                for(RuleMetaData ruleMetaData : allRulesForClass){
                    String ruleName = ruleMetaData.getName();
                    try {
                        //get the map of rules that apply to the class in new condition
                        Map<String, List<RuleMetaData>> ruleMap = getPropertyRuleMap(className, ruleName);
                        // look up the validator from the context
                        RuleValidator foundValidator = (RuleValidator) lookupValidator(ruleName);

                        if (ruleMap != null && !ruleMap.isEmpty() && foundValidator != null) {
                            // add the property map to the validator which was found in the context
                            foundValidator.setRuleMap(ruleMap);
                            // save the validator to the batch validator
                            if (!resultValidator.getValidatorSet().contains(foundValidator))
                                resultValidator.getValidatorSet().add(foundValidator);
                        }
                    } catch (Exception e) {
                        logger.error("Error occurred creating validator for rule: " + ruleName + " while getting property rule map");
                    }
                }
            }
            List<PropertyMetaData> propertyMetaDataList = classMetaData.getProperties();
            if (propertyMetaDataList != null) {
                // iterate list of properties for this class to determine rules associated with it.
                for (PropertyMetaData propertyMetaData : propertyMetaDataList) {
                    List<RuleMetaData> allRulesForProperty = propertyMetaData.getRules(className);
                    if (allRulesForProperty != null) {
                        // for each property rule, extract a property map that will be passed into the
                        // constructed validator
                        for (RuleMetaData ruleMetaData : allRulesForProperty) {
                            String ruleName = ruleMetaData.getName();
                            try {
                                //get the map of rules that apply to the class in new condition
                                Map<String, List<RuleMetaData>> ruleMap = getPropertyRuleMap(className, ruleName);
                                // look up the validator from the context
                                RuleValidator foundValidator = (RuleValidator) lookupValidator(ruleName);

                                if (ruleMap != null && !ruleMap.isEmpty() && foundValidator != null) {
                                    // add the property map to the validator which was found in the context
                                    foundValidator.setRuleMap(ruleMap);
                                    // save the validator to the batch validator
                                    if (!resultValidator.getValidatorSet().contains(foundValidator)) {
                                        resultValidator.getValidatorSet().add(foundValidator);
                                    } else {
                                        // if a validator of the same type exists in the batch, extract the rule meta
                                        /// data and append to the existing property map
                                        appendPropertyRule(resultValidator, foundValidator, ruleMap);
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("Error occurred creating validator for rule: " + ruleName + " while getting property rule map");
                            }
                        }
                    }
                }
            }
        }
        return resultValidator;
    }

    /**
     * Lookup a specific validator that is managed by the application context.
     *
     * @param name
     * @return
     */
    private Validator lookupValidator(String name) {
        Validator validator = null;
        if (validatorTypes.contains(name)) {
            try {
                validator = appContext.getBean(name, Validator.class);
            } catch (Exception e) {
                logger.warn("No validator with name: " + name + " exists in application context.");
            }
        }
        return validator;
    }

    /**
     * Helper method to append a property rule to an existing property map inside an existing validator
     *
     * @param resultValidator
     * @param foundValidator
     * @param ruleMap
     */
    private void appendPropertyRule(BatchValidator<?> resultValidator, RuleValidator foundValidator, Map<String, List<RuleMetaData>> ruleMap) {
        for (Validator<?> aValidator : resultValidator.getValidatorSet()) {
            if (aValidator instanceof RuleValidator) {
                RuleValidator existingValidator = (RuleValidator) aValidator;
                if (existingValidator.getName().equalsIgnoreCase(foundValidator.getName())) {
                    for (String propertyName : ruleMap.keySet()) {
                        List<RuleMetaData> ruleList = (List<RuleMetaData>) existingValidator.getRuleMap().get(propertyName);
                        if (ruleList != null) {
                            ruleList.addAll(ruleMap.get(propertyName));
                        }
                    }
                }
            }
        }
    }
}
