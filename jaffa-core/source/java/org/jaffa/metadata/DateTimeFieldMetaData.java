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

package org.jaffa.metadata;

import org.jaffa.datatypes.*;
import org.jaffa.util.LocaleHelper;
import java.util.StringTokenizer;

/**
 * An instance of this class will hold meta information for a DateTime field.
 */
public class DateTimeFieldMetaData extends FieldMetaData {

    /** Default width.*/
    public static final int DEFAULT_WIDTH = 20;

    /** Default Layout To Use for Formatting a DateOnly object into a String.*/
    //public static final String DEFAULT_FORMAT_LAYOUT = "MM/dd/yyyy HH:mm:ss";

    /** An array of Default Layouts To Use for Parsing a String into a DateOnly object.
     * The String will parsed based on each layout, until a successful parse occurs.
     * This is to support entry of just the date, or date + hours, or date + hours + minutes....
     */
    //public static final String[] DEFAULT_PARSE_LAYOUT = new String[] {"MM/dd/yy HH:mm:ss", "MM/dd/yy HH:mm", "MM/dd/yy HH", "MM/dd/yy"};


    // NOTE: keep the equals(), clone(), compareTo(), hashCode() methods in sync
    private String m_layout = null;
    private DateTime m_minValue = null;
    private DateTime m_maxValue = null;

    /** Creates an instance.
     */
    public DateTimeFieldMetaData() {
        super(null, Defaults.DATETIME, null, null);
    }

    /** Creates an instance.
     * @param name The field name.
     * @param labelToken The token used for displaying labels.
     * @param isMandatory Indicates if the field is mandatory.
     * @param layout The layout.
     * @param minValue The minimum value.
     * @param maxValue The maximum value.
     */
    public DateTimeFieldMetaData(String name,String labelToken,Boolean isMandatory,String layout,DateTime minValue,DateTime maxValue) {
        super(name, Defaults.DATETIME, labelToken, isMandatory);
        m_layout = layout;
        m_minValue = minValue;
        m_maxValue = maxValue;
    }

    /** Getter for property layout.
     * @return Value of property layout.
     */
    public String getLayout() {
        return m_layout;
    }

    /** Getter for property minValue.
     * @return Value of property minValue.
     */
    public DateTime getMinValue() {
        return m_minValue;
    }

    /** Getter for property maxValue.
     * @return Value of property maxValue.
     */
    public DateTime getMaxValue() {
        return m_maxValue;
    }


    /** Returns a clone of the object.
     * @return a clone of the object.
     */
    public Object clone() {
        // no more processing required since the fields are immutable
        return  super.clone();
    }

    /** Returns the hash code.
     * @return the hash code.
     */
    public int hashCode() {
        int i = 0;
        i = super.hashCode();
        if ( m_layout != null ) i += m_layout.hashCode();
        if ( m_minValue != null ) i += m_minValue.hashCode();
        if ( m_minValue != null ) i += m_maxValue.hashCode();
        return i;
    }

    /** Compares this object with another DateTimeFieldMetaData object.
     * Returns a true if both the objects have the same properties.
     * @param obj the other DateTimeFieldMetaData object.
     * @return a true if both the objects have the same properties.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ( obj instanceof DateTimeFieldMetaData ) {
            DateTimeFieldMetaData field2 = (DateTimeFieldMetaData) obj;
            if ( super.equals(field2) ) {
                if ( ( ( m_layout!=null && m_layout.equals(field2.m_layout) ) || (m_layout == null && field2.m_layout == null) )
                && ( ( m_minValue!=null && m_minValue.equals(field2.m_minValue) ) || (m_minValue == null && field2.m_minValue == null) )
                && ( ( m_maxValue!=null && m_maxValue.equals(field2.m_maxValue) ) || (m_maxValue == null && field2.m_maxValue == null) ) )
                    isEqual = true;
            }
        }
        return isEqual;
    }

    /** Compares this object with another DateTimeFieldMetaData object.
     * Note: this class has a natural ordering that is inconsistent with equals
     * @param obj the other DateTimeFieldMetaData object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        // NOTE: this isnt a perfect compare !!!
        return super.compareTo(obj);
    }

    /** Returns the diagnostic information.
     * @return the diagnostic information.
     */
    public String toString() {
        String comma = ", ";
        String equals = "=";
        StringBuffer buffer = new StringBuffer( super.toString() );

        buffer.append(comma); buffer.append("Layout"); buffer.append(equals); buffer.append(m_layout);
        buffer.append(comma); buffer.append("MinValue"); buffer.append(equals); buffer.append(m_minValue);
        buffer.append(comma); buffer.append("MaxValue"); buffer.append(equals); buffer.append(m_maxValue);

        return buffer.toString();
    }

    /** Getter for property width.
     * @return Value of property width.
     */
    public int getWidth() {
        return DEFAULT_WIDTH;
    }

    /** Returns an equivalent DateOnlyFieldMetaData object.
     * @return an equivalent DateOnlyFieldMetaData object.
     */
    public DateOnlyFieldMetaData toDateOnlyFieldMetaData() {
        String name = getName();
        String labelToken = getLabelToken();
        Boolean isMandatory = isMandatory();
        String layout = getLayout();
        DateOnly minValue = m_minValue == null ? null : DateTime.toDateOnly(m_minValue);
        DateOnly maxValue = m_maxValue == null ? null : DateTime.toDateOnly(m_maxValue);

        return new DateOnlyFieldMetaData(name, labelToken,
        isMandatory, layout, minValue, maxValue);
    }

    public static String[] getDateTimeParse() {
        StringTokenizer value = new StringTokenizer(LocaleHelper.getProperty("datetime.parse"), ",");
        String[] nameList = new String[value.countTokens()];
        int counter = 0;
        while (value.hasMoreTokens()) {
            nameList[counter] = value.nextToken();
            counter++;
        }
        return nameList;
    }

    public static String getDateTimeFormat() {
        return LocaleHelper.getProperty("datetime.format");
    }
}
