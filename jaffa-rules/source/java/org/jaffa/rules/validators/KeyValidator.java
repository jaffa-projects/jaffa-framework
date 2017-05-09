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
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.util.BeanHelper;

import java.util.List;
import java.util.Map;

/**
 * When applied to a property, ensures that a valid key is entered.
 * Apply it at class-level to support composite-keys.
 *
 * @param <T> the type of object to validate.
 */
public abstract class KeyValidator<T> extends RuleValidator<T> {
    private static Logger log = Logger.getLogger(KeyValidator.class);

    /**
     * Constructs a validator of persisted key references.
     *
     * @param name the name of the validator.
     */
    protected KeyValidator(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(T targetObject) throws ApplicationException, FrameworkException {
        UOW uow = getUOW(targetObject);
        if (uow == null) {
            if (log.isDebugEnabled()) {
                log.debug(getName() + " check not performed since UOW is not available on the targetObject " + targetObject);
            }
            return;
        }

        // No need to perform validations for an object that has not been modified.
        if (isModified(targetObject)) {
            for (Map.Entry<String, List<RuleMetaData>> entry : getRuleMap().entrySet()) {
                String targetPropertyName = entry.getKey();
                List<RuleMetaData> rules = entry.getValue();
                // No need to perform validations for a property that has not been modified.
                if (isModified(targetObject, targetPropertyName)) {
                    List<RuleMetaData> applicableRules = trimInapplicableRules(targetObject, rules);
                    if (applicableRules != null && !applicableRules.isEmpty()) {
                        validateProperty(targetObject, targetPropertyName, applicableRules, uow);
                    }
                }
            }
        }
    }

    /**
     * Validates a single property.
     *
     * @param targetObject        the target object.
     * @param targetPropertyName  the target property name.
     * @param targetPropertyValue the target property value.
     * @param rules               the rules to be applied.
     * @param uow                 the UOW for key validation.
     * @throws ValidationException if any validation fails.
     * @throws FrameworkException  if any framework error occurs.
     */
    protected abstract void validateProperty(T targetObject,
                                             String targetPropertyName,
                                             Object targetPropertyValue,
                                             List<RuleMetaData> rules,
                                             UOW uow) throws ApplicationException, FrameworkException;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules) throws ApplicationException, FrameworkException {
        // This version of validateProperty is supported but inefficient.
        UOW uow = getUOW(targetObject);
        if (uow == null) {
            if (log.isDebugEnabled()) {
                log.debug(getName() + " check not performed since UOW is not available on the targetObject " + targetObject);
            }
        } else {
            validateProperty(targetObject, targetPropertyName, targetPropertyValue, rules, uow);
        }
    }

    /**
     * Validates a single property.
     *
     * @param targetObject       the target object.
     * @param targetPropertyName the target property name.
     * @param rules              the rules to be applied.
     * @throws ValidationException if any validation fails.
     * @throws FrameworkException  if any framework error occurs.
     */
    private void validateProperty(T targetObject,
                                  String targetPropertyName,
                                  List<RuleMetaData> rules,
                                  UOW uow) throws ApplicationException, FrameworkException {
        Object targetPropertyValue;
        try {
            targetPropertyValue = targetPropertyName != null ? BeanHelper.getField(targetObject, targetPropertyName) : null;
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to get value for the property " + targetPropertyName + ". It may not exist in the targetObject", e);
            return;
        }
        validateProperty(targetObject, targetPropertyName, targetPropertyValue, rules, uow);
    }
}
