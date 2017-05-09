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
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.InvalidRuleParameterException;
import org.jaffa.util.BeanHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * When applied to a property, ensures that a valid foreign key is entered. Apply it at class-level to support composite-keys.
 *
 * @param <T> the type of object to validate.
 */
public class ForeignKeyValidator<T> extends KeyValidator<T> {

    private static Logger log = Logger.getLogger(ForeignKeyValidator.class);

    /**
     * Creates an instance.
     */
    public ForeignKeyValidator() {
        super("foreign-key");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules, UOW uow) throws ApplicationException, FrameworkException {
        if (targetPropertyName == null) {
            // Class-level rule
            for (RuleMetaData rule : rules) {
                validateClass(targetObject, rule, uow);
            }
        } else if (targetPropertyValue != null) {
            // Property-level rule
            for (RuleMetaData rule : rules) {
                validateKey(targetObject, new String[]{targetPropertyName}, new Object[]{uow, targetPropertyValue}, rule);
            }
        }
    }

    /**
     * Apply the foreign-key validation at the class-level.
     */
    private void validateClass(T targetObject, RuleMetaData rule, UOW uow)
            throws ApplicationException, JaffaRulesFrameworkException {
        String[] fkFields = rule.getParameter(RuleMetaData.PARAMETER_VALUE).split(",");
        Object[] fkValues = new Object[fkFields.length + 1];
        fkValues[0] = uow;
        boolean keyProvided = true;
        boolean keyModified = false;
        for (int i = 0; i < fkFields.length; i++) {
            Object value;
            try {
                value = BeanHelper.getField(targetObject, fkFields[i]);
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Exception thrown while trying to get value for the property " + fkFields[i] +
                            ". It may not exist in the targetObject", e);
                }
                return;
            }
            if (value == null) {
                if (log.isDebugEnabled()) {
                    log.debug("The check " + rule + " not performed, since " + fkFields[i] + " is null");
                }
                keyProvided = false;
                break;
            }
            if (!keyModified) {
                keyModified = isModified(targetObject, fkFields[i]);
            }
            fkValues[i + 1] = value;
        }
        if (keyProvided && keyModified) {
            validateKey(targetObject, fkFields, fkValues, rule);
        }
    }

    /**
     * Apply the foreign-key validation at the property-level.
     */
    private void validateKey(T targetObject, String[] fkFields, Object[] fkValues, RuleMetaData rule)
            throws ApplicationException, JaffaRulesFrameworkException {
        if (log.isDebugEnabled()) {
            log.debug("Applying the check " + rule + " for the fields " + fkFields + " on " + targetObject);
        }

        // Find the 'public static boolean exists(UOW uow, Object key...)' method
        Method m = findMethod(fkFields, rule);

        // Invoke the 'public static boolean exists(UOW uow, Object key...)' method
        Boolean result;
        try {
            result = (Boolean) m.invoke(null, fkValues);
        } catch (Exception e) {
            throw new JaffaRulesFrameworkException(e.getMessage(), null, e);
        }

        if (result == null || !result) {
            String domainLabel = getObjectLabel(m.getDeclaringClass().getName(), null);
            StringBuilder fkLabel = new StringBuilder();
            for (int i = 0; i < fkFields.length; i++) {
                if (i > 0) {
                    fkLabel.append(',');
                }
                fkLabel.append(getPropertyLabel(targetObject, fkFields[i]));
            }
            String fieldKeyLabel = fkLabel.toString();

            String primaryKeyLabel = getPrimaryKeyLabel(m.getDeclaringClass().getName(), null);
            if (primaryKeyLabel == null) {
                primaryKeyLabel = fieldKeyLabel;
            }
            if (log.isDebugEnabled()) {
                log.debug("ForeignKey validation for the value '" + fkValues + "' of '" + fkFields +
                        "' failed against the domainObject '" + m.getDeclaringClass().getName() + '\'');
            }

            Object[] arguments = getErrorArgumentArray(targetObject, rule);
            if (arguments.length == 0) {
                arguments = new Object[]{domainLabel, primaryKeyLabel};
            }
            throw wrapException(new InvalidForeignKeyException(fkLabel.toString(), arguments), targetObject, rule);
        }
    }

    /**
     * An InvalidRuleParameterException will be thrown if the domainObject does not have a 'public static boolean exists(UOW uow, Object key)' method.
     * A reference to the method is maintained in a field.
     */
    private Method findMethod(String[] fkFields, RuleMetaData rule) {
        // Get a handle on the 'public static boolean exists(UOW uow, Object key1...)' method.
        String domainClassName = rule.getParameter(RuleMetaData.PARAMETER_DOMAIN_OBJECT);
        try {
            Class domainClass = Class.forName(domainClassName);
            Method[] methods = domainClass.getMethods();
            Method m = null;
            if (methods != null) {
                for (Method method : methods) {
                    m = method;
                    if (m.getName().equals("exists") && m.getParameterTypes() != null && m.getParameterTypes().length == (fkFields.length + 1) && UOW.class.isAssignableFrom(m.getParameterTypes()[0]) && m.getReturnType() == Boolean.TYPE && Modifier.isStatic(m.getModifiers())) {
                        break;
                    } else {
                        m = null;
                    }
                }
            }
            if (m == null) {
                log.error("The 'public static boolean exists(UOW uow, Object key)' method not found on the domainObject '" + domainClassName + "'");
                throw new InvalidRuleParameterException(fkFields.toString(), getName(), "domainObject", domainClassName);
            }
            return m;
        } catch (ClassNotFoundException e) {
            log.error("The domainObject '" + domainClassName + "' class not found", e);
            throw new InvalidRuleParameterException(fkFields.toString(), getName(), "domainObject", domainClassName);
        }
    }
}
