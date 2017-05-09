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

import java.io.Serializable;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;

/** The various WebService implementation have a tough time dealing with the custom DateTime and DateOnly objects.
 * They handle java.util.Date instance much more smoothly.
 * This class should be used as a replacement for DateTimeCriteriaField and DateOnlyCriteriaField instance when transporting data between presentation and business tiers.
 * Constructors and convenience methods have been provided to make the transformation a breeze.
 */
public class DateCriteriaField implements Serializable, Cloneable {

    private static final Logger log = Logger.getLogger(DateCriteriaField.class);
    private String m_operator = null;
    private Date[] m_values = null;

    /** Creates an instance.
     */
    public DateCriteriaField() {
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
    public Date[] getValues() {
        return m_values;
    }

    /** Setter for the property values.
     * @param values The value of the property values.
     */
    public void setValues(Date[] values) {
        m_values = values;
    }


    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<DateCriteriaField>");
        buf.append("<operator>"); if (m_operator != null) buf.append(m_operator); buf.append("</operator>");
        if (m_values != null) {
            for (int i = 0; i < m_values.length; i++) {
                buf.append("<value>"); buf.append(m_values[i]); buf.append("</value>");
            }
        }
        buf.append("</DateCriteriaField>");
        return buf.toString();
    }

    /** Returns a clone of the object.
     * @return a clone of the object.
     */
    public Object clone() {
        try {
            DateCriteriaField obj = (DateCriteriaField) super.clone();
            if (this.m_values != null) {
                obj.m_values = new Date[this.m_values.length];
                for (int i = 0; i < this.m_values.length; i++)
                    obj.m_values[i] = this.m_values[i];
            }
            return obj;
        } catch(CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            return null;
        }
    }

    /** Validates the criteria.
     * @throws InvalidCriteriaRuntimeException if the criteria is invalid.
     */
    public void validate() throws InvalidCriteriaRuntimeException {
        if (m_operator != null && m_operator.length() > 0
                && !CriteriaDropDownOptions.getDateCriteriaDropDownOptions().containsKey(m_operator)) {
            String s = "Operator '" + m_operator + "' is invalid. Valid values are " + CriteriaDropDownOptions.getDateCriteriaDropDownOptions().keySet().toString();
            log.error(s);
            throw new InvalidCriteriaRuntimeException(s);
        }
    }



    // *******************************************************
    // ******** INTERACTION WITH THE CUSTOM CLASSES **********
    // *******************************************************

    /** Creates an instance.
     * Initializes its fields with the values from the input.
     * @param input The input.
     */
    public DateCriteriaField(DateTimeCriteriaField input) {
        if (input != null) {
            m_operator = input.getOperator();
            DateTime[] values = input.getValues();
            if (values != null) {
                m_values = new Date[values.length];
                for (int i = 0; i < values.length; i++)
                    m_values[i] = values[i] != null ? values[i].getUtilDate() : null;
            }
        }
    }

    /** Creates an instance.
     * Initializes its fields with the values from the input.
     * @param input The input.
     */
    public DateCriteriaField(DateOnlyCriteriaField input) {
        if (input != null) {
            m_operator = input.getOperator();
            DateOnly[] values = input.getValues();
            if (values != null) {
                m_values = new Date[values.length];
                for (int i = 0; i < values.length; i++)
                    m_values[i] = values[i] != null ? values[i].getUtilDate() : null;
            }
        }
    }

    /** Creates an instance of DateTimeCriteriaField, based on its existing values.
     * @return an instance of DateTimeCriteriaField, based on its existing values.
     */
    public DateTimeCriteriaField toDateTimeCriteriaField() {
        DateTime[] values = null;
        if (m_values != null) {
            values = new DateTime[m_values.length];
            for (int i = 0; i < m_values.length; i++)
                values[i] = m_values[i] != null ? new DateTime(m_values[i]) : null;
        }
        return new DateTimeCriteriaField(m_operator, values);
    }

    /** Creates an instance of DateOnlyCriteriaField, based on its existing values.
     * @return an instance of DateOnlyCriteriaField, based on its existing values.
     */
    public DateOnlyCriteriaField toDateOnlyCriteriaField() {
        DateOnly[] values = null;
        if (m_values != null) {
            values = new DateOnly[m_values.length];
            for (int i = 0; i < m_values.length; i++)
                values[i] = m_values[i] != null ? new DateOnly(m_values[i]) : null;
        }
        return new DateOnlyCriteriaField(m_operator, values);
    }

}
