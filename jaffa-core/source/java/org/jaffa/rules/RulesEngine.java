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

import org.apache.log4j.Logger;
import java.util.*;
import org.jaffa.persistence.UOW;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.fieldvalidators.IFieldValidator;
import org.jaffa.rules.metadata.*;
import org.jaffa.security.VariationContext;
import org.jaffa.util.BeanHelper;
import java.io.FileNotFoundException;
import org.jaffa.util.MessageHelper;
import java.net.MalformedURLException;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.util.PersistentHelper;

/** This class provides methods to validate a field or a domain object. It will utilise the XML configuration files to determine the
 * rules to invoke for the validations. If the variation has been set for the Thread [for eg. the Portlet Servlet or
 * WebServicesWrapper calling the VariationContext.setVariation()], then that variation will be used to determine the rules.
 */
public class RulesEngine {
    
    private static final Logger log = Logger.getLogger(RulesEngine.class);
    private static final String LABEL_TOKEN_PREFIX = "label.";
    
    /** This will only invoke the 'Mandatory' Rules defined for the field.
     * @param domainClassName The domain class to which the field belongs.
     * @param fieldName The field to be validated.
     * @param fieldValue The value to be validated.
     * @param uow The UOW.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public static void doMandatoryValidationsForDomainField(String domainClassName,
    String fieldName, Object fieldValue, UOW uow)
    throws ValidationException, FrameworkException {
        doValidationsForDomainField(domainClassName, fieldName, fieldValue, uow, true);
    }
    
    /** This will invoke all the Rules defined for the field.
     * @param domainClassName The domain class to which the field belongs.
     * @param fieldName The field to be validated.
     * @param fieldValue The value to be validated.
     * @param uow The UOW.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public static void doAllValidationsForDomainField(String domainClassName,
    String fieldName, Object fieldValue, UOW uow)
    throws ValidationException, FrameworkException {
        doValidationsForDomainField(domainClassName, fieldName, fieldValue, uow, false);
    }
    
    /** This will only invoke the 'Mandatory' Rules defined for the fields of the Domain object.
     * @param domainObject The domain object to be validated.
     * @param uow The UOW.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public static void doMandatoryValidationsForDomainObject(Object domainObject, UOW uow)
    throws ValidationException, FrameworkException {
        doValidationsForDomainObject(domainObject, uow, true);
    }
    
    /** This will invoke all the Rules defined for the fields of the Domain object.
     * @param domainObject The domain object to be validated.
     * @param uow The UOW.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public static void doAllValidationsForDomainObject(Object domainObject, UOW uow)
    throws ValidationException, FrameworkException {
        doValidationsForDomainObject(domainObject, uow, false);
    }
    
    
    
    
    
    
    
    
    /** Returns the ClassMetaData object for the given className + variation. A null will be returned, in case the rules-file is not found */
    private static ClassMetaData getClassMetaData(String className, String variation) throws FrameworkException {
        ClassMetaData classMetaData = null;
        try {
            classMetaData = RulesMetaDataService.getClassMetaData(className, variation);
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (MalformedURLException e) {
            // do nothing
        } catch (Exception e) {
            String str = "Error thrown while trying to read the rules for the class '" + className + "', variation '" + variation + '\'';
            log.error(str, e);
            throw new RulesEngineException(RulesEngineException.LOADING_RULES_FAILED, new Object[] {className, variation}, e);
        }
        return classMetaData;
    }
    
    /** Uses introspection to determine the value of a field from a bean */
    private static Object getFieldValue(Object bean, String field) throws FrameworkException {
        Object fieldValue = null;
        try {
            fieldValue = BeanHelper.getField(bean, field);
        } catch (Exception e) {
            String str = "Error thrown while trying to read the value for the field '" + field + "', from the object " + bean;
            log.error(str, e);
            throw new RulesEngineException(RulesEngineException.FIELD_READ_FAILED, new Object[] {field, bean.getClass().getName()}, e);
        }
        return fieldValue;
    }
    
    private static String getLabelToken(String domainClassName, String fieldName) throws FrameworkException {
        String labelToken = null;
        try {
            labelToken = PersistentHelper.getLabelToken(domainClassName, fieldName);
        } catch (Exception e) {
            // don't do anything.. just return the domainClassName + fieldName
        }
        if (labelToken == null)
            labelToken = MessageHelper.tokenize(LABEL_TOKEN_PREFIX + domainClassName + '.' + fieldName);
        return labelToken;
    }
    
    /** Invokes the doFieldValidaions for each field of the ClassMetaData object for the domainObject. */
    private static void doValidationsForDomainObject(Object domainObject, UOW uow, boolean doOnlyMandatory)
    throws ValidationException, FrameworkException {
        String variation = VariationContext.getVariation();
        
        boolean localUow = false;
        try {
            // create a UOW, if not passed in
            if (uow == null) {
                uow = new UOW();
                localUow = true;
            }
            
            // Determine the domain class. A persistent object could very well be a proxy. Hence invoke the appropriate method
            String domainClassName = domainObject instanceof IPersistent ? uow.getActualPersistentClass(domainObject).getName()
            : domainObject.getClass().getName();
            
            // determine the ClassMetaData
            ClassMetaData classMetaData = getClassMetaData(domainClassName, variation);

            // determine the core ClassMetaData, if required
            ClassMetaData coreClassMetaData = VariationContext.DEFAULT_VARIATION.equals(variation) ? null : getClassMetaData(domainClassName, VariationContext.DEFAULT_VARIATION);
        
            // perform validations for all the fields
            if (classMetaData != null) {
                FieldMetaData[] fields = classMetaData.getFields();
                for (int i = 0; i < fields.length; i++) {
                    FieldMetaData fieldMetaData = fields[i];
                    FieldMetaData coreFieldMetaData = coreClassMetaData != null ? coreClassMetaData.getField(fieldMetaData.getName()) : null;
                    String labelToken = getLabelToken(domainClassName, fieldMetaData.getName());
                    Object fieldValue = getFieldValue(domainObject, fieldMetaData.getName());
                    doFieldValidaions(fieldValue, uow, doOnlyMandatory, labelToken, fieldMetaData, coreFieldMetaData);
                }
            }
            
            // perform validations for the fields in the core file, which are not defined in the variant file
            if (coreClassMetaData != null) {
                FieldMetaData[] fields = coreClassMetaData.getFields();
                for (int i = 0; i < fields.length; i++) {
                    FieldMetaData fieldMetaData = fields[i];
                    if (classMetaData == null || classMetaData.getField(fieldMetaData.getName()) == null) {
                        // create a UOW, if not passed in
                        if (uow == null) {
                            uow = new UOW();
                            localUow = true;
                        }
                        String labelToken = getLabelToken(domainClassName, fieldMetaData.getName());
                        Object fieldValue = getFieldValue(domainObject, fieldMetaData.getName());
                        doFieldValidaions(fieldValue, uow, doOnlyMandatory, labelToken, fieldMetaData, null);
                    }
                }
            }
            
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    
    /** Invokes the doFieldValidaions for the field. */
    private static void doValidationsForDomainField(String domainClassName,
    String fieldName, Object fieldValue, UOW uow, boolean doOnlyMandatory)
    throws ValidationException, FrameworkException {
        String variation = VariationContext.getVariation();
        
        // determine the ClassMetaData
        ClassMetaData classMetaData = getClassMetaData(domainClassName, variation);
        
        // determine the core ClassMetaData, if required
        ClassMetaData coreClassMetaData = VariationContext.DEFAULT_VARIATION.equals(variation) ? null : getClassMetaData(domainClassName, VariationContext.DEFAULT_VARIATION);
        
        boolean localUow = false;
        try {
            String labelToken = null;
            
            // perform validations for the field
            if (classMetaData != null) {
                FieldMetaData fieldMetaData = classMetaData.getField(fieldName);
                if (fieldMetaData != null) {
                    // create a UOW, if not passed in
                    if (uow == null) {
                        uow = new UOW();
                        localUow = true;
                    }
                    FieldMetaData coreFieldMetaData = coreClassMetaData != null ? coreClassMetaData.getField(fieldName) : null;
                    labelToken = getLabelToken(domainClassName, fieldMetaData.getName());
                    doFieldValidaions(fieldValue, uow, doOnlyMandatory, labelToken, fieldMetaData, coreFieldMetaData);
                }
            }
            
            
            // perform validations for the field in the core file, if not defined in the variant file
            if (coreClassMetaData != null) {
                if (classMetaData == null || classMetaData.getField(fieldName) == null) {
                    FieldMetaData fieldMetaData = coreClassMetaData.getField(fieldName);
                    if (fieldMetaData != null) {
                        // create a UOW, if not passed in
                        if (uow == null) {
                            uow = new UOW();
                            localUow = true;
                        }
                        if (labelToken == null)
                            labelToken = getLabelToken(domainClassName, fieldMetaData.getName());
                        doFieldValidaions(fieldValue, uow, doOnlyMandatory, labelToken, fieldMetaData, null);
                    }
                }
            }
            
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }
    
    
    // Invokes doValidationsForDomainField() for the parent-field, if any. Finally it invokes the invokeRules() method.
    private static void doFieldValidaions(Object fieldValue, UOW uow, boolean doOnlyMandatory,
    String labelToken, FieldMetaData fieldMetaData, FieldMetaData coreFieldMetaData)
    throws ValidationException, FrameworkException {
        // Perform the core validations first, if not overridden.
        if (coreFieldMetaData != null && !fieldMetaData.getOverridesDefault()) {
            // Perform the validations for the field that core-field extends (recursive)
            if (coreFieldMetaData.getExtendsClass() != null && coreFieldMetaData.getExtendsField() != null)
                doValidationsForDomainField(coreFieldMetaData.getExtendsClass(), coreFieldMetaData.getExtendsField(), fieldValue, uow, doOnlyMandatory);
            
            // Now perform the validations for the core-field
            invokeRules(fieldValue, uow, doOnlyMandatory, labelToken, coreFieldMetaData);
        }
        
        
        // Perform the validations for the field that this extends (recursive).
        if (fieldMetaData.getExtendsClass() != null && fieldMetaData.getExtendsField() != null) {
            // Ensure the check against the core to prevent infinite loop
            if (coreFieldMetaData == null ||
            !( fieldMetaData.getExtendsClass().equals(coreFieldMetaData.getExtendsClass()) && fieldMetaData.getExtendsField().equals(coreFieldMetaData.getExtendsField()) ) )
                doValidationsForDomainField(fieldMetaData.getExtendsClass(), fieldMetaData.getExtendsField(), fieldValue, uow, doOnlyMandatory);
        }
        
        // Now perform the validations for the field
        invokeRules(fieldValue, uow, doOnlyMandatory, labelToken, fieldMetaData);
    }
    
    /** This will invoke all the rules for a field. */
    private static void invokeRules(Object fieldValue, UOW uow, boolean doOnlyMandatory,
    String labelToken, FieldMetaData fieldMetaData)
    throws ValidationException, FrameworkException {
        RuleMetaData[] rules = fieldMetaData.getRules();
        for (int i = 0; i < rules.length; i++) {
            RuleMetaData rule = rules[i];
            IFieldValidator validator = null;
            try {
                FieldValidatorMetaData fieldValidatorMetaData = ValidatorMetaDataService.getFieldValidatorMetaData(rule.getName());
                if (!doOnlyMandatory || fieldValidatorMetaData.getMandatory()) {
                    // Create an instance of the validator
                    validator = (IFieldValidator) Class.forName(fieldValidatorMetaData.getClassName()).newInstance();
                    
                    // Perform the initialization
                    validator.init();
                    
                    // Set some values
                    validator.setValue(fieldValue);
                    validator.setLabelToken(labelToken);
                    validator.setUow(uow);
                    
                    // Set the Parameters, if specified in the validators.xml file
                    if (fieldValidatorMetaData.getParameters() != null) {
                        for (Iterator itr = fieldValidatorMetaData.getParameters().entrySet().iterator(); itr.hasNext(); ) {
                            Map.Entry me = (Map.Entry) itr.next();
                            BeanHelper.setField(validator, (String) me.getKey(), (String) me.getValue());
                        }
                    }
                    
                    // Set the Parameters provided in the rules.xml file
                    for (Iterator itr = rule.getParameters().entrySet().iterator(); itr.hasNext(); ) {
                        Map.Entry me = (Map.Entry) itr.next();
                        BeanHelper.setField(validator, (String) me.getKey(), (String) me.getValue());
                    }
                    
                    // Now perform validation
                    if (log.isDebugEnabled())
                        log.debug("Invoking the rule " + rule.getName() + " for field " + fieldMetaData.getName());
                    validator.validate();
                }
            } catch (ValidationException e) {
                throw e;
            } catch (FrameworkException e) {
                throw e;
            } catch (Exception e) {
                String str = "Error thrown while trying to invoke the rules for the field '" + labelToken + "', having the value '" + fieldValue + "', using the fieldMetaData - " + fieldMetaData;
                log.error(str, e);
                throw new RulesEngineException(RulesEngineException.RULE_INVOCATION_FAILED, new Object[] {labelToken, fieldValue}, e);
            } finally {
                if (validator != null) {
                    validator.cleanup();
                    validator = null;
                }
            }
        }
    }
    
    
}
