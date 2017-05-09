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
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DuplicateCandidateKeyException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexProperty;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.flexfields.domain.FlexFieldMeta;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;

import java.util.*;

/**
 *  This rule is used to validate the uniqueness of an object.
 *  It'll ensure that no record exists in the database for the given candidate-key fields.
 */
public class CandidateKeyValidator<T> extends KeyValidator<T> {

    private static Logger log = Logger.getLogger(CandidateKeyValidator.class);

    /**
     * Creates an instance.
     */
    public CandidateKeyValidator() {
        super("candidate-key");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules, UOW uow) throws ApplicationException, FrameworkException {

        String targetClassName = getActualClassName(targetObject.getClass());
        IPersistent persistentTargetObject = null;

        try {
            // Use the associated persistentObject for a FlexBean instance
            if (targetObject instanceof FlexBean && ((FlexBean) targetObject).getPersistentObject() != null) {
                persistentTargetObject = ((FlexBean) targetObject).getPersistentObject();
                targetClassName = persistentTargetObject.getClass().getName();
            }

            // This rules makes sense on IPersistent instances only. Bail out on all other instances
            if (!(targetObject instanceof IPersistent)) {
                return;
            } else if (persistentTargetObject == null) {
                persistentTargetObject = (IPersistent) targetObject;
            }

            // Obtain a Map containing key fields and corresponding values
            Map<String, Object> keyValueMap = getKeyValueMap(targetClassName, (IPersistent) targetObject);

            // Create a criteria such that the current object is not included in the search for candidate-key violations
            AtomicCriteria criteriaToDiscardCurrentObject = createCriteriaToDiscardCurrentObject(persistentTargetObject, keyValueMap);

            // Invoke each rule
            for (RuleMetaData rule : rules) {
                invoke(targetClassName, persistentTargetObject, uow, rule, criteriaToDiscardCurrentObject, keyValueMap);
            }
        } catch (ApplicationExceptions e) {
            if (log.isDebugEnabled()) {
                log.debug("Error in validateProperty method for object: " + targetClassName, e);
            }
            throw new JaffaRulesFrameworkException("Error in validateProperty method for object: " + targetClassName, null, e);
        }
    }

    /**
     * Obtain a Map containing key fields and corresponding values.
     */
    private Map<String, Object> getKeyValueMap(String targetClassName, IPersistent targetObject)
            throws ApplicationExceptions, FrameworkException {

        // Search for the first available class-level primary-key rule
        String[] keys = null;
        Map<String, List<RuleMetaData>> pkMap = getPropertyRuleMap(targetClassName, targetObject, "primary-key");
        if (pkMap != null && pkMap.containsKey(null)) {
            List<RuleMetaData> rules = pkMap.get(null);
            if (rules != null && rules.size() > 0) {
                RuleMetaData rule = rules.get(0);
                keys = rule.getParameter(RuleMetaData.PARAMETER_VALUE).split(",");
            }
        }

        // Search for corresponding DomainMeta class, if required
        if (keys == null || keys.length == 0) {
            try {
                FieldMetaData[] keyFields = PersistentHelper.getKeyFields(targetObject.getClass().getName());
                if (keyFields != null) {
                    keys = new String[keyFields.length];
                    for (int i = 0; i < keyFields.length; i++)
                        keys[i] = keyFields[i].getName();
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        // Create the keyValueMap
        if (keys != null && keys.length > 0) {
            Map<String, Object> keyValueMap = new LinkedHashMap<String, Object>();
            for (String key : keys) {
                Object value = null;
                try {
                    value = BeanHelper.getField(targetObject, key);
                } catch (NoSuchMethodException e) {
                    if (log.isDebugEnabled()) {
                        log.debug("Could not get the field: " + targetObject.getClass().getName() + "." + key, e);
                    }
                    throw new JaffaRulesFrameworkException(targetObject.getClass().getName() + "." + key, null, e);
                }
                keyValueMap.put(key, value);
            }
            return keyValueMap;
        } else {
            return null;
        }
    }

    /**
     *  Create a criteria such that the input object is not included in the search for candidate-key violations.
     */
    private AtomicCriteria createCriteriaToDiscardCurrentObject(IPersistent targetObject, Map<String,
            Object> keyValueMap) throws FrameworkException {

        // No need to create a primary-key criteria for an IPersistent instance that has not been saved yet
        if (!targetObject.isDatabaseOccurence())
            return null;

        // Create the criteria
        if (keyValueMap != null && keyValueMap.size() > 0) {
            AtomicCriteria criteriaToDiscardCurrentObject = null;
            for (Map.Entry<String, Object> me : keyValueMap.entrySet()) {
                String key = me.getKey();
                Object value = me.getValue();
                if (value != null) {
                    if (criteriaToDiscardCurrentObject == null) {
                        criteriaToDiscardCurrentObject = new AtomicCriteria();
                        criteriaToDiscardCurrentObject.addCriteria(StringHelper.getUpper1(key), Criteria.RELATIONAL_NOT_EQUALS, value);
                    } else {
                        criteriaToDiscardCurrentObject.addOrCriteria(StringHelper.getUpper1(key), Criteria.RELATIONAL_NOT_EQUALS, value);
                    }
                    if (log.isDebugEnabled())
                        log.debug("Added to primary-key exclusion criteria: " + key + '=' + value);
                } else {
                    // Do not create the criteria if any part of the key is null
                    if (log.isDebugEnabled())
                        log.debug("The Key field " + key + " is null. The logic to discount the current object cannot be performed");
                    criteriaToDiscardCurrentObject = null;
                    break;
                }
            }
            return criteriaToDiscardCurrentObject;
        } else {
            return null;
        }
    }

    /**
     * Apply the candidate-key validation.
     */
    private void invoke(String targetClassName, IPersistent targetObject, UOW uow, RuleMetaData rule,
                        AtomicCriteria criteriaToDiscardCurrentObject, Map<String, Object> keyValueMap)
            throws ApplicationException, ApplicationExceptions, FrameworkException {

        if (log.isDebugEnabled()) {
            log.debug("Applying " + rule + " on " + targetObject);
        }

        Criteria criteria = null;
        String ck = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
        String[] ckFields = ck.split(",");
        Boolean ignoreNull = Parser.parseBoolean(rule.getParameter("ignore-null"));

        // Flex fields having null values are deleted from the database table.
        // The persistence engine does not support queries of the type 'where not exists...'.
        // Hence we'll retrieve all rows and throw an exception only if the fields in the collection are null in any of the rows
        Collection<String> nullFields = null;
        FlexClass flexClass = targetObject instanceof IFlexFields && ((IFlexFields) targetObject).getFlexBean() != null ? (FlexClass) ((IFlexFields) targetObject).getFlexBean().getDynaClass() : null;

        boolean keyModified = false;
        for (String ckField : ckFields) {
            // determine if this is a flex field
            FlexProperty flexProperty = flexClass != null ? flexClass.getDynaProperty(ckField) : null;

            // Obtain the value
            Object ckValue;
            try {
                ckValue = BeanHelper.getField(flexProperty != null ? ((IFlexFields) targetObject).getFlexBean() : targetObject, ckField);
                if (ignoreNull != null && ignoreNull && ckValue == null) {
                    if (log.isDebugEnabled())
                        log.debug("Rule will be ignored since " + ckField + " is null");
                    return;
                }
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Exception thrown while trying to get value for the property " + ckField + ". It may not exist in the targetObject", e);
                return;
            }

            // Perform the check only if the field is modified, or is NULL and Object has not been saved yet
            if (!keyModified)
                keyModified = isModified(flexProperty != null ? ((IFlexFields) targetObject).getFlexBean() : targetObject, ckField) || (ckValue == null && !targetObject.isDatabaseOccurence());

            // Change the ckField if it is a flex field with a domain-mapping
            // NOTE: This change should not happen before the check for modified fields, since the
            // mapped field in the associated persistent object may have been marked as unmodified subsequent to a successful update of that object.
            // This happens cause the FlexBean.validate() is invoked in the postUpdate() trigger of the persistent object,
            // and any other rule may cause the actual update to be executed.
            if (flexProperty != null && flexProperty.getFlexInfo() != null && flexProperty.getFlexInfo().getProperty("domain-mapping") != null) {
                ckField = flexProperty.getFlexInfo().getProperty("domain-mapping");
                flexProperty = null;
            }

            // Add to criteria
            if (criteria == null)
                criteria = new Criteria();
            if (ckValue == null) {
                if (flexProperty != null) {
                    if (nullFields == null)
                        nullFields = new LinkedList<String>();
                    nullFields.add(ckField);
                } else {
                    criteria.addCriteria(StringHelper.getUpper1(ckField), Criteria.RELATIONAL_IS_NULL);
                }
            } else {
                if (flexProperty != null) {
                    Criteria flexCriteria = new Criteria();
                    flexCriteria.setTable(FlexFieldMeta.getName());
                    int i = 0;
                    for (String key : keyValueMap.keySet())
                        flexCriteria.addInnerCriteria("Key" + ++i, StringHelper.getUpper1(key));
                    flexCriteria.addCriteria(FlexFieldMeta.OBJECT_NAME, flexClass.getLogicalName());
                    flexCriteria.addCriteria(FlexFieldMeta.FIELD_NAME, flexProperty.getLogicalName());
                    flexCriteria.addCriteria(FlexFieldMeta.VALUE, DataTypeMapper.instance().map(ckValue, String.class, flexProperty.getFlexInfo() != null ? flexProperty.getFlexInfo().getProperty("layout") : null));
                    criteria.addAggregate(flexCriteria);
                } else {
                    criteria.addCriteria(StringHelper.getUpper1(ckField), ckValue);
                }
            }
        }

        if (keyModified && criteria != null) {
            if (criteriaToDiscardCurrentObject != null)
                criteria.addAtomic(criteriaToDiscardCurrentObject);
            criteria.setTable(targetClassName);
            //try {
            for (Object o : uow.query(criteria)) {
                boolean violation = nullFields == null;
                if (!violation) {
                    // Not a violation if any of the fields is not null
                    for (String ckField : nullFields) {
                        try {
                            violation = BeanHelper.getField(targetObject, ckField) == null;
                            if (!violation) {
                                break;
                            }
                        } catch (Exception e) {
                            if (log.isDebugEnabled()) {
                                log.debug("Error in invoke method during query execution.", e);
                            }
                            throw new JaffaRulesFrameworkException("Error in invoke method during query execution on object: " + targetClassName, null, e);
                        }
                    }
                }
                if (violation) {
                    if (log.isDebugEnabled()) {
                        log.debug("Candidate key '" + ck + "' is not unique for the object " + targetObject);
                    }
                    String objectLabel = getObjectLabel(targetClassName, targetObject);
                    StringBuilder ckLabel = new StringBuilder();
                    for (int i = 0; i < ckFields.length; i++) {
                        if (i > 0) {
                            ckLabel.append(',');
                        }
                        ckLabel.append(getPropertyLabel(targetObject, ckFields[i]));
                    }

                    throw wrapException(new DuplicateCandidateKeyException(objectLabel, ckLabel.toString()), (T) targetObject, rule);
                }
            }
        }
    }
}
