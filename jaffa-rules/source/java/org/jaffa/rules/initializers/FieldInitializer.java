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

package org.jaffa.rules.initializers;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.util.RuleActor;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.util.BeanHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Initializes a property to a default initial value
 *
 * @param <T> the type of the object
 */
public class FieldInitializer<T> extends RuleActor<T> implements Initializer<T> {
    private static Logger logger = Logger.getLogger(FieldInitializer.class);

    /**
     * Constructs an initializer.
     */
    public FieldInitializer() {
        super("initialize");
    }

    /**
     * Initializes the object, throwing an exception for any problem encountered.
     *
     * @param object the object to initialize.
     * @return the initialized object
     * @throws ApplicationException if any validation fails.
     * @throws FrameworkException   if any framework error occurs.
     */
    @Override
    public T initialize(T object) throws FrameworkException {

        if (logger.isDebugEnabled()) {
            logger.debug(getName() + ": of object " + object.getClass().getSimpleName());
        }

        Map<String, List<RuleMetaData>> ruleMap = getRuleMap();
        if (ruleMap != null) {
            for (Map.Entry<String, List<RuleMetaData>> me : ruleMap.entrySet()) {
                String propertyName = me.getKey();
                List<RuleMetaData> rules = me.getValue();
                if (rules != null && rules.size() > 0) {
                    List<RuleMetaData> applicableRules = trimInapplicableRules(object, rules);
                    if (applicableRules != null && applicableRules.size() > 0) {
                        initialize(object, propertyName, applicableRules.get(0));
                    }
                }
            }
        }

        return object;
    }

    /**
     * Apply the initialize rule
     *
     * @param object       the object to be initialized
     * @param propertyName the name of the property to be initialized
     * @param rule         the rule used for initialization
     * @throws Exception if any error occurs
     */
    private void initialize(Object object, String propertyName, RuleMetaData rule) throws JaffaRulesFrameworkException {

        // Ignore the rule if the object is a FlexBean and is associated with an in-database persistentObject.
        // This is important, else the current value for the flex field will be unintentionally overwritten with the initial value.
        if (object instanceof FlexBean) {
            FlexBean flexBean = (FlexBean) object;
            if (flexBean.getPersistentObject() != null && flexBean.getPersistentObject().isDatabaseOccurence()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Rule " + rule + " on property" + propertyName + " of " + object + " cannot be applied since the targetObject is a FlexBean associated with an in-database persistentObject");
                }
                return;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Applying " + rule + " on property" + propertyName + " of " + object);
        }

        String value = rule.getParameter(RuleMetaData.PARAMETER_VALUE);
        String member = rule.getParameter(RuleMetaData.PARAMETER_MEMBER);
        Boolean expression = Parser.parseBoolean(rule.getParameter(RuleMetaData.PARAMETER_EXPRESSION));

        //evaluate expression if it exists
        Object evaluatedValue = value;
        if (expression != null && expression) {
            String language = rule.getParameter(RuleMetaData.PARAMETER_LANGUAGE);

            try {
                evaluatedValue = ScriptHelper.instance(language).evaluate(null, value, object, rule.getSource(),
                        rule.getLine() != null ? rule.getLine() : 0, 0);
            } catch (Exception exception) {
                throw new JaffaRulesFrameworkException("Error evaluating expression during initialize of object: " + object.getClass().getName(), null, exception);
            }

            if (evaluatedValue != null && !(evaluatedValue instanceof String)) {
                evaluatedValue = DataTypeMapper.instance().map(evaluatedValue, String.class);
            }
        }

        if (member == null) {
            try {
                BeanHelper.setField(object, propertyName, (String) evaluatedValue);
            } catch (Exception exception) {
                // The rules engine should be passive and ignore the exceptions that a setter might throw
                if (logger.isDebugEnabled()) {
                    logger.debug("Set property '" + propertyName + " = " + evaluatedValue + "' has failed", exception);
                }
            }
        } else {
            try {
                Field field = null;
                Class clazz = object.getClass();
                while (field == null) {
                    try {
                        field = clazz.getDeclaredField(member);
                    } catch (NoSuchFieldException e) {
                        if (clazz.getSuperclass() == null)
                            break;
                        clazz = clazz.getSuperclass();
                    }
                }
                if (field == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Member field " + member + " not found on class " + object.getClass().getName());
                    }
                } else {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.set(object, DataTypeMapper.instance().map(evaluatedValue, field.getType()));
                }
            } catch (Exception exception) {
                // The rules engine should be passive and ignore the exceptions that a setter might throw
                if (logger.isDebugEnabled()) {
                    logger.debug("Set member '" + member + " = " + evaluatedValue + "' has failed", exception);
                }
            }
        }
    }
}
