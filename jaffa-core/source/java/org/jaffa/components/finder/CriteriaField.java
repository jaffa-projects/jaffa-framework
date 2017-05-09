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

/**
 * This is the interface for all the Criteria fields.
 * Each CriteriaField object will consist of an Operator and a array of values.
 * The length of the array will be zero for the 'IsNull' and 'IsNotNull' operators.
 * The length of the array will have to be >= 1, for the 'Between' and the 'In' operators.
 * For all other operators, the length of the array will have to be one.
 * @author  GautamJ
 */
public interface CriteriaField {

    /** A global constant for the EQUALS criteria operator.*/
    public final static String RELATIONAL_EQUALS                 = "Equals";

    /** A global constant for the NOT_EQUALS criteria operator.*/
    public final static String RELATIONAL_NOT_EQUALS             = "NotEquals";

    /** A global constant for the GREATER_THAN criteria operator.*/
    public final static String RELATIONAL_GREATER_THAN           = "GreaterThan";

    /** A global constant for the SMALLER_THAN criteria operator.*/
    public final static String RELATIONAL_SMALLER_THAN           = "SmallerThan";

    /** A global constant for the GREATER_THAN_EQUAL_TO criteria operator.*/
    public final static String RELATIONAL_GREATER_THAN_EQUAL_TO  = "GreaterThanOrEquals";

    /** A global constant for the SMALLER_THAN_EQUAL_TO criteria operator.*/
    public final static String RELATIONAL_SMALLER_THAN_EQUAL_TO  = "SmallerThanOrEquals";

    /** A global constant for the IS_NOT_NULL criteria operator.*/
    public final static String RELATIONAL_IS_NOT_NULL            = "IsNotNull";

    /** A global constant for the IS_NULL criteria operator.*/
    public final static String RELATIONAL_IS_NULL                = "IsNull";

    /** A global constant for the BEGINS_WITH criteria operator.*/
    public final static String RELATIONAL_BEGINS_WITH            = "BeginsWith";

    /** A global constant for the ENDS_WITH criteria operator.*/
    public final static String RELATIONAL_ENDS_WITH              = "EndWith";

    /** A global constant for the LIKE criteria operator.*/
    public final static String RELATIONAL_LIKE                   = "Like";

    /** A global constant for the BETWEEN criteria operator.*/
    public final static String RELATIONAL_BETWEEN                = "Between";

    /** A global constant for the IN criteria operator.*/
    public final static String RELATIONAL_IN                = "In";

        /** A global constant for the IN criteria operator.*/
    public final static String RELATIONAL_NOT_IN                = "NotIn";
    
    /** This character should be used for separating the arguments for the In/Between relational operators.*/
    public final static char SEPARATOR_CHAR_FOR_IN_BETWEEN_OPERATORS = ',';

    /** This is the String representation of the constant SEPARATOR_CHAR_FOR_IN_BETWEEN_OPERATORS.*/
    public final static String SEPARATOR_FOR_IN_BETWEEN_OPERATORS = "" + SEPARATOR_CHAR_FOR_IN_BETWEEN_OPERATORS;


    // These constants are used by the various classes implementing this interface
    final static String CONSECUTIVE_SEPARATORS = SEPARATOR_FOR_IN_BETWEEN_OPERATORS + SEPARATOR_FOR_IN_BETWEEN_OPERATORS;
    final static String CONSECUTIVE_SEPARATORS_WITH_SPACE = SEPARATOR_FOR_IN_BETWEEN_OPERATORS + " " + SEPARATOR_FOR_IN_BETWEEN_OPERATORS;


    /** Getter for property operator.
     * @return Value of property operator.
     */
    public String getOperator();

    /** Getter for property values.
     * This method ensures that a class implementing the CriteriaField interface will have an array of criteria values.
     * Additionally they are expected to have a 'getValues()' method returning actual instances.
     * For eg. StringCriteriaField will have 'String[] getValues()', BooleanCriteriaField will have 'Boolean[] getValues()' and so on.
     * @return An array of values for the Criteria.
     */
    public Object[] returnValuesAsObjectArray();

    /** Validates the criteria.
     * @throws InvalidCriteriaRuntimeException if the criteria is invalid.
     */
    public void validate() throws InvalidCriteriaRuntimeException;

}
