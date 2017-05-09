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
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.util.StringHelper;

import java.util.List;

/**
 * When applied to a property, validates it as a partial foreign key.
 *
 * @param <T> the type of object to validate.
 */
public class PartialForeignKeyValidator<T> extends KeyValidator<T> {
    private static final String PARAMETER_DOMAIN_NAME = "domainObject";
    private static final String PARAMETER_PROPERTY_NAME = "propertyName";
    private static Logger log = Logger.getLogger(PartialForeignKeyValidator.class);

    /**
     * Creates an instance.
     */
    public PartialForeignKeyValidator() {
        super("partial-foreign-key");
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

                String domainName = rule.getParameter(PARAMETER_DOMAIN_NAME);
                String propName = rule.getParameter(PARAMETER_PROPERTY_NAME);
                if (propName == null) {
                    propName = targetPropertyName;
                }
                propName = StringHelper.getUpper1(propName);

                Criteria c = new Criteria();
                c.setTable(domainName);
                c.addCriteria(propName, targetPropertyValue);
                if (!uow.query(c).iterator().hasNext()) {
                    String className;
                    try {
                        className = Class.forName(domainName).getName();
                    } catch (ClassNotFoundException e) {
                        throw new JaffaRulesFrameworkException(e.getMessage(), null, e);
                    }

                    // Invalid value. Display the list of valid values in the error message
                    // It is valid for multiple row to be returned so we don't check for that here
                    String domainLabel = getObjectLabel(className, null);
                    String fkLabel = getPropertyLabel(targetObject, targetPropertyName);
                    String primaryKeyLabel = getPrimaryKeyLabel(className, null);
                    if (primaryKeyLabel == null) {
                        primaryKeyLabel = fkLabel.toString();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("PartialForeignKey validation for the value '" + targetPropertyValue + "' of '"
                                + targetPropertyName + "' failed against the domainObject '" + domainName + '\'');
                    }

                    String errorCode = getErrorCode(primaryKeyLabel, rule);
                    Object[] arguments = getErrorArgumentArray(targetObject, rule);
                    if (arguments == null) {
                        arguments = new Object[]{domainLabel, primaryKeyLabel};
                    }
                    throw wrapException(new InvalidForeignKeyException(errorCode, arguments), targetObject, rule);
                }
            }
        }
    }
}
