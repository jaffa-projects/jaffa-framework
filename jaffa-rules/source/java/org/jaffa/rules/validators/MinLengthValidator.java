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
package org.jaffa.rules.validators;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.exceptions.TooLittleDataException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.InvalidRuleParameterException;

import java.util.List;

/**
 * Validates that string properties are of a minimum length.
 */
public class MinLengthValidator<T> extends RuleValidator<T> {

    private static Logger log = Logger.getLogger(MinLengthValidator.class);

    /**
     * Constructs a validator with no rules.
     */
    public MinLengthValidator() {
        super("min-length");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateProperty(T targetObject, String targetPropertyName, Object targetPropertyValue,
                                    List<RuleMetaData> rules) throws ApplicationException, FrameworkException {
        if (targetPropertyValue != null) {
            for (RuleMetaData rule : rules) {
                if (log.isDebugEnabled()) {
                    log.debug("Applying " + rule + " on " + targetPropertyValue);
                }

                // Determine the minLength
                Integer minLength = null;
                try {
                    minLength = new Integer(rule.getParameter(RuleMetaData.PARAMETER_VALUE));
                } catch (NumberFormatException e) {
                    throw new InvalidRuleParameterException(targetPropertyName, getName(), "value", rule.getParameter(RuleMetaData.PARAMETER_VALUE));
                }

                if (minLength != null) {
                    if (targetPropertyValue instanceof String) {
                        if (((String) targetPropertyValue).length() < minLength.intValue()) {
                            throw logErrorCreateException(targetObject, targetPropertyName, targetPropertyValue, rule);
                        }
                    } else if (targetPropertyValue instanceof Number) {
                        if (!checkLength((Number) targetPropertyValue, minLength)) {
                            throw logErrorCreateException(targetObject, targetPropertyName, targetPropertyValue, rule);
                        }
                    } else if (targetPropertyValue instanceof Currency) {
                        if (!checkLength(((Currency) targetPropertyValue).getValue(), minLength)) {
                            throw logErrorCreateException(targetObject, targetPropertyName, targetPropertyValue, rule);
                        }
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("This rule does not support instances of " + targetPropertyValue.getClass().getName());
                    }
                }
            }
        }
    }

    /** This method will ensure that the input Number exceeds the specified limit.
     * @param input The number whose length is to be checked.
     * @param intSize The lower limit on the number of digits allowed before the decimal.
     * @return a true if the input Number exceeds the specified limit.
     */
    private boolean checkLength(Number input, Integer intSize) {
        if (input != null && intSize != null) {
            double value = Math.abs(input.doubleValue());

            double intLimit = Math.pow(10, intSize.intValue() - 1);
            if ((long) value < (long) intLimit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Logs a debug message and creates a TooMuchDataException exception that can to throw by the caller.
     *
     * @param targetObject        the target object.
     * @param targetPropertyName  the target property name.
     * @param targetPropertyValue the target property value.
     * @param rule                the rule that failed validation.
     * @return the exception to throw
     */
    private ApplicationException logErrorCreateException(T targetObject, String targetPropertyName,
                                                           Object targetPropertyValue, RuleMetaData rule) {
        if (log.isDebugEnabled()) {
            log.debug("The length of the value '" + targetPropertyValue + "' is below '" + rule.getParameter(RuleMetaData.PARAMETER_VALUE) + "'");
        }

        return wrapException(TooLittleDataException.class, targetObject, targetPropertyName, rule);
    }
}
