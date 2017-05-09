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

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.metadata.StringFieldMetaData;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.StringHelper;

/**
 * This class will be used by the Finder components to hold a String criteria.
 */
public class StringCriteriaField implements CriteriaField {

    private static final Logger log = Logger.getLogger(StringCriteriaField.class);
    private String m_operator = null;
    private String[] m_values = null;

    /** Default constructor.
     */
    public StringCriteriaField() {
    }

    /** Adds a Criteria.
     * @param operator the operator of the criteria.
     * @param value the value of the criteria.
     */
    public StringCriteriaField(String operator, String value) {
        this(operator, value != null ? new String[] {value} : null);
    }

    /** Adds a Criteria.
     * @param operator the operator of the criteria.
     * @param values the value array of the criteria.
     */
    public StringCriteriaField(String operator, String[] values) {
        m_operator = operator;
        m_values = values;
    }

    /** Getter for property operator.
     * @return Value of property operator.
     */
    public String getOperator() {
        return m_operator;
    }

    /** Setter for the property operator.
     * @param operator The value of the property operator.
     */
    public void setOperator(String operator) {
        m_operator = operator;
    }

    /** Getter for property values.
     * @return An array of values for the Criteria.
     */
    public String[] getValues() {
        return m_values;
    }

    /** Setter for the property values.
     * @param values The value of the property values.
     */
    public void setValues(String[] values) {
        m_values = values;
    }

    /** Getter for property values.
     * This basically invokes the getValues() method.
     * @return An array of values for the Criteria.
     */
    public Object[] returnValuesAsObjectArray() {
        return getValues();
    }

    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("<StringCriteriaField>");
        buf.append("<operator>"); if (m_operator != null) buf.append(m_operator); buf.append("</operator>");
        if (m_values != null) {
            for (int i = 0; i < m_values.length; i++) {
                Object value = m_values[i];
                buf.append("<value>"); if (value != null) buf.append(value); buf.append("</value>");
            }
        }
        buf.append("</StringCriteriaField>");
        return buf.toString();
    }

    /** This will generate a CriteriaField object based on the input parameters.
     * @param operator The operator of the criteria.
     * @param value The value for the criteria. Multiple values should be separated by comma.
     * @param meta The FieldMetaData object to obtain the layout for parsing.
     * @return a CriteriaField object based on the input parameters.
     */
    public static StringCriteriaField getStringCriteriaField(String operator,
            String value, StringFieldMetaData meta) {
        StringCriteriaField criteriaField = null;
        String nullValue = null;

        if (value != null)
            value = value.trim();

        if (value != null && value.length() > 0) {
            List values = new ArrayList();
            if (RELATIONAL_BETWEEN.equals(operator) || RELATIONAL_IN.equals(operator)) {
                // replace ",," with ", ,"
                value = StringHelper.replace(value, CONSECUTIVE_SEPARATORS, CONSECUTIVE_SEPARATORS_WITH_SPACE);

                if (value.startsWith(SEPARATOR_FOR_IN_BETWEEN_OPERATORS))
                    values.add(null);

                StringTokenizer tknzr = new StringTokenizer(value, SEPARATOR_FOR_IN_BETWEEN_OPERATORS);
                while (tknzr.hasMoreTokens())
                    parseAndAdd(tknzr.nextToken().trim(), meta, values);

                if (value.endsWith(SEPARATOR_FOR_IN_BETWEEN_OPERATORS))
                    values.add(null);
            } else {
                parseAndAdd(value, meta, values);
            }
            if (values.size() > 0)
                criteriaField = new StringCriteriaField(operator, (String[]) values.toArray(new String[0]));
            else
                criteriaField = new StringCriteriaField(operator, nullValue);
        } else
            criteriaField = new StringCriteriaField(operator, nullValue);
        return criteriaField;
    }

    private static void parseAndAdd(String str, StringFieldMetaData meta,
            List values) {
        if (str != null && str.length() > 0)
            values.add(Parser.parseString(str));
        else
            values.add(null);
    }

    /** Validates the criteria.
     * @throws InvalidCriteriaRuntimeException if the criteria is invalid.
     */
    public void validate() throws InvalidCriteriaRuntimeException {
        if (m_operator != null && m_operator.length() > 0
                && !CriteriaDropDownOptions.getAllCriteriaDropDownOptions().containsKey(m_operator)) {
            String s = "Operator '" + m_operator + "' is invalid. Valid values are " + CriteriaDropDownOptions.getAllCriteriaDropDownOptions().keySet().toString();
            log.error(s);
            throw new InvalidCriteriaRuntimeException(s);
        }
    }

    /**
     * Returns true if the fields of the input match the fields of this object.
     * @param obj the object to match.
     * @return true if the fields of the input match the fields of this object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final StringCriteriaField other = (StringCriteriaField) obj;
        if ((this.m_operator == null) ? (other.m_operator != null) : !this.m_operator.equals(other.m_operator))
            return false;
        if (!Arrays.deepEquals(this.m_values, other.m_values))
            return false;
        return true;
    }

    /**
     * Returns the sum of hashCodes of all the fields in this object.
     * @return the sum of hashCodes of all the fields in this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.m_operator != null ? this.m_operator.hashCode() : 0);
        hash = 17 * hash + Arrays.deepHashCode(this.m_values);
        return hash;
    }
}
