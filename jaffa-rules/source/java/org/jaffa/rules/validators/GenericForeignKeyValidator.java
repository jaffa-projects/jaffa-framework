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
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.exceptions.InvalidGenericForeignKeyException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.util.BeanHelper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * When applied to a property, validates it as a generic foreign key.
 *
 * @param <T> the type of object to validate.
 */
public class GenericForeignKeyValidator<T> extends KeyValidator<T> {

    private static final String PARAMETER_TABLE_NAME = "tableName";
    private static final String PARAMETER_FIELD_NAME = "fieldName";
    private static final String PARAMETER_DOMAIN_CLASS_NAME = "domainClassName";
    private static final String PARAMETER_FIELD_NAME_FOR_TABLE = "fieldNameForTable";
    private static final String PARAMETER_FIELD_NAME_FOR_FIELD = "fieldNameForField";
    private static final String PARAMETER_FIELD_NAME_FOR_VALUE = "fieldNameForValue";
    private static Logger log = Logger.getLogger(GenericForeignKeyValidator.class);

    /**
     * Creates an instance.
     */
    public GenericForeignKeyValidator() {
        super("generic-foreign-key");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules, UOW uow) throws ApplicationException, FrameworkException {
        if (targetPropertyValue != null) {
            for (RuleMetaData rule : rules) {
                if (log.isDebugEnabled()) {
                    log.debug("Applying " + rule + " on " + targetPropertyValue);
                }

                String tableName = rule.getParameter(PARAMETER_TABLE_NAME);
                String fieldName = rule.getParameter(PARAMETER_FIELD_NAME);
                String domainClassName = rule.getParameter(PARAMETER_DOMAIN_CLASS_NAME);
                String fieldNameForTable = rule.getParameter(PARAMETER_FIELD_NAME_FOR_TABLE);
                String fieldNameForField = rule.getParameter(PARAMETER_FIELD_NAME_FOR_FIELD);
                String fieldNameForValue = rule.getParameter(PARAMETER_FIELD_NAME_FOR_VALUE);

                // Transform the value to the data type of the 'LegalValue' field of the Generic Foreign Key class.
                Class valueClass = determineValueClass(domainClassName, fieldNameForValue);
                Object value = DataTypeMapper.instance().map(targetPropertyValue, valueClass);
                Criteria c = new Criteria();
                c.setTable(domainClassName);
                c.addCriteria(fieldNameForTable, tableName);
                c.addCriteria(fieldNameForField, fieldName);
                c.addCriteria(fieldNameForValue, value);
                Collection col = uow.query(c);
                if (!col.iterator().hasNext()) {
                    // Invalid value. Display the list of valid values in the error message
                    StringBuilder validValues = new StringBuilder();
                    c = new Criteria();
                    c.setTable(domainClassName);
                    c.addCriteria(fieldNameForTable, tableName);
                    c.addCriteria(fieldNameForField, fieldName);
                    c.addOrderBy(fieldNameForValue);
                    for (Object o : uow.query(c)) {
                        try {
                            Object legalValue = BeanHelper.getField(o, fieldNameForValue);
                            if (validValues.length() > 0)
                                validValues.append(',');
                            validValues.append(legalValue);
                        } catch (Exception e) {
                            // do nothing
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Generic ForeignKey validation failed for the value '" + value +
                                "' against the table/field - " + tableName + '/' + fieldName +
                                ". Valid values are " + validValues.toString());
                    }
                    String label = getPropertyLabel(targetObject, targetPropertyName);
                    Object[] arguments = getErrorArgumentArray(targetObject, rule);
                    if (arguments == null) {
                        arguments = new Object[]{tableName, fieldName, validValues.toString()};
                    }
                    throw wrapException(new InvalidGenericForeignKeyException(label, arguments), targetObject, rule);
                }
            }
        }
    }

    /**
     * This will determine the class of the 'fieldNameForValue' field of the generic foreign key class.
     * This should typically be a String.class.
     *
     * @param domainClassName   the full name of the domain class.
     * @param fieldNameForValue the field of the generic foreign key class.
     */
    private Class<?> determineValueClass(String domainClassName, String fieldNameForValue) throws FrameworkException {
        try {
            Class<?> domainClass = Class.forName(domainClassName);
            Method method = domainClass.getMethod(BeanHelper.getReaderName(fieldNameForValue), (Class<?>[]) null);
            return method.getReturnType();
        } catch (Exception e) {
            throw new JaffaRulesFrameworkException(e.getMessage(), null, e);
        }
    }
}
