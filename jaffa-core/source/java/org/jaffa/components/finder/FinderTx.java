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

package org.jaffa.components.finder;

import java.util.Iterator;
import java.util.Map;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;

/** This is the helper class for all Finder Tx (transaction) classes.
 * It is provided to support repetitive functions.
 * @author  GautamJ
 *
 * @TODO fix this so the operators are validated, not ignored when incorrect, as this data may be passed via
 * a web service with an incorrect spelling.
 */
public class FinderTx {

    private static final String TEST_CRITERIA_NAME = "testCriteria";

    /** This method will update the input criteria, based on the 'field' and 'name' inputs.
     * It is invoked by the concrete class while building a Criteria object to query the database.
     * @param field The criteria field which will have the operator and value(s).
     * @param name The name of the field for which the criteria has been passed.
     * @param criteria The Criteria object being built for the query, and which will be updated based on the 'field' and 'name' inputs.
     */
    public static void addCriteria(CriteriaField field, String name, Criteria criteria) {
        if(field != null) {
            // validate the criteria
            field.validate();

            String operator = field.getOperator();
            //@todo - Throw runtime exception if an invalid operator is supplied
            //if(!field.isOperatorValid()) {
            //    throw new IllegalArgumentException(MessageHelper.findToken("error.jaffa.finder.criteriaField",new String[] {name,operator}));
            //}
            Object[] values = field.returnValuesAsObjectArray();
            if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
            else if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NOT_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NOT_NULL);
            else if (values != null && values.length > 0) {
                if (operator != null && operator.length() == 0) {
                    // A non-Jaffa tool may pass empty operator and value, even when they didn't intend to pass them. Bail out!
                    if (values[0] == null || (values[0] instanceof String && values[0].toString().length() == 0))
                        return;
                    // A non-empty value has been passed. So treat the operator like an Equals type
                    operator = null;
                }
                if (operator == null || operator.equals(CriteriaField.RELATIONAL_EQUALS))
                    criteria.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_NOT_EQUALS))
                    criteria.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN))
                    criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN))
                    criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO))
                    criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO))
                    criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
                    criteria.addCriteria(name, Criteria.RELATIONAL_BEGINS_WITH, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
                    criteria.addCriteria(name, Criteria.RELATIONAL_ENDS_WITH, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
                    criteria.addCriteria(name, Criteria.RELATIONAL_LIKE, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_BETWEEN)) {
                    if (values.length > 0 && values[0] != null)
                        criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, values[0]);
                    if (values.length > 1 && values[1] != null)
                        criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, values[1]);
                } else if (operator.equals(CriteriaField.RELATIONAL_IN)) {
                    if (values.length == 1) {
                        if (values[0] == null)
                            criteria.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
                        else
                            criteria.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                    } else {
                        AtomicCriteria atomic = new AtomicCriteria();
                        if (values[0] == null)
                            atomic.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
                        else
                            atomic.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                        for (int i = 1; i < values.length; i++) {
                            if (values[i] == null)
                                atomic.addOrCriteria(name, Criteria.RELATIONAL_IS_NULL);
                            else
                                atomic.addOrCriteria(name, Criteria.RELATIONAL_EQUALS, values[i]);
                        }
                        criteria.addAtomic(atomic);
                    }
                } else if (operator.equals(CriteriaField.RELATIONAL_NOT_IN)) {
                    if (values.length == 1) {
                        if (values[0] == null)
                            criteria.addCriteria(name, Criteria.RELATIONAL_IS_NOT_NULL);
                        else
                            criteria.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS, values[0]);
                    } else {
                        AtomicCriteria atomic = new AtomicCriteria();
                        if (values[0] == null)
                            atomic.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS, Criteria.RELATIONAL_IS_NULL);
                        else
                            atomic.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS, values[0]);
                        for (int i = 1; i < values.length; i++) {
                            if (values[i] == null)
                                atomic.addCriteria(name,Criteria.RELATIONAL_NOT_EQUALS, Criteria.RELATIONAL_IS_NULL);
                            else
                                atomic.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS,  values[i]);
                        }
                        criteria.addAtomic(atomic);
                    }

                }
            }
        }
    }

    /** This method will update the input criteria, based on the 'field' and 'name' inputs.
     * It is invoked by the concrete class while building a Criteria object to query the database.
     * @param field The criteria field which will have the operator and value(s).
     * @param name The name of the field for which the criteria has been passed.
     * @param criteria The Criteria object being built for the query, and which will be updated based on the 'field' and 'name' inputs.
     */
    public static void addCriteria(DateTimeCriteriaField field, String name, Criteria criteria) {
        if(field != null) {
            // validate the criteria
            field.validate();

            String operator = field.getOperator();
            //@todo
            //if(!field.isOperatorValid()) {
            //    throw new IllegalArgumentException(MessageHelper.findToken("error.jaffa.finder.criteriaField",new String[] {name,operator}));
            //}
            Object[] values = field.returnValuesAsObjectArray();
            if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
            else if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NOT_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NOT_NULL);
            else if (values != null && values.length > 0) {
                if (operator != null && operator.length() == 0) {
                    // A non-Jaffa tool may pass empty operator and value, even when they didn't intend to pass them. Bail out!
                    if (values[0] == null)
                        return;
                    // A non-empty value has been passed. So treat the operator like an Equals type
                    operator = null;
                }
                if (operator == null || operator.equals(CriteriaField.RELATIONAL_EQUALS))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_EQUALS, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_NOT_EQUALS))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_NOT_EQUALS, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_GREATER_THAN, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_SMALLER_THAN, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_BEGINS_WITH, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_ENDS_WITH, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
                    criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_LIKE, (DateTime) values[0]));
                else if (operator.equals(CriteriaField.RELATIONAL_BETWEEN)) {
                    if (values.length > 0 && values[0] != null)
                        criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, determineDateTimeLimit((DateTime) values[0], true));
                    if (values.length > 1 && values[1] != null)
                        criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, determineDateTimeLimit((DateTime) values[1], false));
                } else if (operator.equals(CriteriaField.RELATIONAL_IN)) {
                    if (values.length == 1) {
                        if (values[0] == null)
                            criteria.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
                        else
                            criteria.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_EQUALS, (DateTime) values[0]));
                    } else {
                        AtomicCriteria atomic = new AtomicCriteria();
                        if (values[0] == null)
                            atomic.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
                        else
                            atomic.addAtomic(interpretDateTime(name, Criteria.RELATIONAL_EQUALS, (DateTime) values[0]));
                        for (int i = 1; i < values.length; i++) {
                            if (values[i] == null)
                                atomic.addOrCriteria(name, Criteria.RELATIONAL_IS_NULL);
                            else
                                atomic.addOrAtomic(interpretDateTime(name, Criteria.RELATIONAL_EQUALS, (DateTime) values[i]));
                        }
                        criteria.addAtomic(atomic);
                    }
                }
            }
        }
    }

    /**
     *
     * @param field - The CriteriaField to be checked
     * @return true if anything would be added to the criteria block when the addCriteria is used for this same criteria field
     */
    public static boolean isCriteria(CriteriaField field) {

        if(field!=null) {
            //Create a dummy criteria to check if this field would be added
            Criteria c = new Criteria();
            addCriteria(field,TEST_CRITERIA_NAME,c);
            if(c.getCriteriaEntries()!=null && c.getCriteriaEntries().size()>0)
                return true;
        }
        return false;
    }

    /**
     * This method will return true if the input value matches the criteria.
     * A true will also be returned if the criteria is null.
     * @param input a value to be matched.
     * @param criteriaField The criteria field which will have the operator and value(s).
     * @return true if the input value matches the criteria.
     */
    public static boolean match(Object input, CriteriaField criteriaField) {
        boolean match = false;
        if (criteriaField == null) {
            match = true;
        } else {
            // validate the criteria
            criteriaField.validate();

            String operator = criteriaField.getOperator();
            //@todo - Throw runtime exception if an invalid operator is supplied
            //if(!field.isOperatorValid()) {
            //    throw new IllegalArgumentException(MessageHelper.findToken("error.jaffa.finder.criteriaField",new String[] {name,operator}));
            //}
            Object[] values = criteriaField.returnValuesAsObjectArray();
            if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NULL))
                match = input == null;
            else if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NOT_NULL))
                match = input != null;
            else if (values != null && values.length > 0) {
                if (operator != null && operator.length() == 0) {
                    if (values[0] == null || (values[0] instanceof String && values[0].toString().length() == 0)) {
                        // A non-Jaffa tool may pass empty operator and value, even when they didn't intend to pass them. Bail out!
                        match = true;
                    } else {
                        // A non-empty value has been passed. So treat the operator like an Equals type
                        match = input == null ? values[0] == null : input.equals(values[0]);
                    }
                } else if (operator == null || operator.equals(CriteriaField.RELATIONAL_EQUALS))
                    match = input == null ? values[0] == null : input.equals(values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_NOT_EQUALS))
                    match = input == null ? values[0] != null : !input.equals(values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN))
                    match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[0]) > 0;
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN))
                    match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[0]) < 0;
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO))
                    match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[0]) >= 0;
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO))
                    match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[0]) <= 0;
                else if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
                    match = input != null && input instanceof String && values[0] instanceof String && ((String) input).startsWith((String) values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
                    match = input != null && input instanceof String && values[0] instanceof String && ((String) input).endsWith((String) values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
                    match = input != null && input instanceof String && values[0] instanceof String && ((String) input).indexOf((String) values[0]) >= 0;
                else if (operator.equals(CriteriaField.RELATIONAL_BETWEEN)) {
                    if (values.length > 0 && values[0] != null)
                        match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[0]) >= 0;
                    else
                        match = true;
                    if (match && values.length > 1 && values[1] != null)
                        match = input != null && input instanceof Comparable && ((Comparable) input).compareTo(values[1]) <= 0;
                } else if (operator.equals(CriteriaField.RELATIONAL_IN)) {
                    for (Object value : values) {
                        match = input == null ? value == null : input.equals(value);
                        if (match)
                            break;
                    }
                }
            }
        }
        return match;
    }

    /** Runs a query to find the total number of records returned by the input criteria in the absence of the output-limiting parameters.
     * @param uow The UOW.
     * @param input The input to the finder component.
     * @param criteria The criteria based on the input.
     * @param output The output for the finder component
     * @param rowsCount The number of rows retrieved by the query in the presence of output-limiting parameters.
     * @throws FrameworkException if any system error occurs.
     */
    public static void findTotalRecords(UOW uow, FinderInDto input, Criteria criteria, FinderOutDto output, int rowsCount)
            throws FrameworkException {
        // do not find total records, only if not asked to
        if (input.getFindTotalRecords() == null || input.getFindTotalRecords()) {
            int firstRecord = input.getFirstRecord() == null ? 0 : input.getFirstRecord();
            int maxRecords = input.getMaxRecords() == null ? 0 : input.getMaxRecords();
            if (firstRecord > 0 || maxRecords > 0) {
                if (firstRecord <= 0 && rowsCount < maxRecords) {
                    output.setTotalRecords(rowsCount);
                    output.setMoreRecordsExist(Boolean.FALSE);
                } else {
                    criteria.setFirstResult(null);
                    criteria.setMaxResults(null);
                    criteria.clearOrderBy();
                    criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext()) {
                        Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                        output.setTotalRecords(count != null ? count.intValue() : 0);
                        output.setMoreRecordsExist(output.getTotalRecords() > rowsCount);
                    }
                }
            } else {
                output.setTotalRecords(rowsCount);
                output.setMoreRecordsExist(Boolean.FALSE);
            }
        }
    }

    private static AtomicCriteria interpretDateTime(String name, int operator, DateTime value) {
        AtomicCriteria atomic = new AtomicCriteria();

        // determine the start and end of the day
        DateTime dt1 = determineDateTimeLimit(value, true);
        DateTime dt2 = determineDateTimeLimit(value, false);

        if (dt1.equals(dt2)) {
            // an exact value has been passed !!!
            atomic.addCriteria(name, operator, value);
        } else {
            // now perform datatime interpretation based on the operator
            if (operator == Criteria.RELATIONAL_EQUALS) {
                atomic.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, dt1);
                atomic.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, dt2);
            } else if (operator == Criteria.RELATIONAL_NOT_EQUALS) {
                atomic.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN, dt1);
                atomic.addOrCriteria(name, Criteria.RELATIONAL_GREATER_THAN, dt2);
            } else if (operator == Criteria.RELATIONAL_GREATER_THAN) {
                atomic.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN, dt2);
            } else if (operator == Criteria.RELATIONAL_SMALLER_THAN) {
                atomic.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN, dt1);
            } else if (operator == Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO) {
                atomic.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, dt1);
            } else if (operator == Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO) {
                atomic.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, dt2);
            } else {
                // this should never happen
                atomic.addCriteria(name, operator, value);
            }
        }
        return atomic;
    }


    private static DateTime determineDateTimeLimit(DateTime value, boolean lowerLimit) {
        DateTime dt = null;
        int maxHours = 23, maxMinutes = 59, maxSeconds = 59, maxMillis = 999;

        if (value.hourOfDay() == 0 && value.minute() == 0 && value.second() == 0) {
            if (lowerLimit)
                dt = DateTime.addMilli(value, -(value.milli()));
            else
                dt = new DateTime(value.year(), value.month(), value.day(), maxHours, maxMinutes, maxSeconds, maxMillis);
        } else if (value.minute() == 0 && value.second() == 0) {
            if (lowerLimit)
                dt = DateTime.addMilli(value, -(value.milli()));
            else
                dt = new DateTime(value.year(), value.month(), value.day(), value.hourOfDay(), maxMinutes, maxSeconds, maxMillis);
        } else if (value.second() == 0) {
            if (lowerLimit)
                dt = DateTime.addMilli(value, -(value.milli()));
            else
                dt = new DateTime(value.year(), value.month(), value.day(), value.hourOfDay(), value.minute(), maxSeconds, maxMillis);
        } else {
            dt = value;
        }
        return dt;
    }
}
