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

/**
 * An instance of this class will hold meta information for a Currency field.
 */
public class CurrencyFieldMetaData extends FieldMetaData {

    /** Default width.*/
    public static final int DEFAULT_WIDTH = 20;

    /** Default Integer Size.*/
    public static final int DEFAULT_INT_SIZE = 10;

    /** Default Fraction Size.*/
    public static final int DEFAULT_FRAC_SIZE = 2;

    /** Default Layout To Use.*/
    //public static final String DEFAULT_LAYOUT = null;

    // NOTE: keep the equals(), clone(), compareTo(), hashCode() methods in sync
    private String m_layout = null;
    private Currency m_minValue = null;
    private Currency m_maxValue = null;
    private Integer m_intSize = null;
    private Integer m_fracSize = null;

    /** Creates an instance.
     */
    public CurrencyFieldMetaData() {
        this(null, null, Boolean.FALSE, getCurrencyFormat(), new Currency(Double.MIN_VALUE), new Currency(Double.MAX_VALUE), new Integer(DEFAULT_INT_SIZE), new Integer(DEFAULT_FRAC_SIZE));
    }

    /** Creates an instance.
     * @param name The field name.
     * @param labelToken The token used for displaying labels.
     * @param isMandatory Indicates if the field is mandatory.
     * @param layout The layout.
     * @param minValue The minimum value.
     * @param maxValue The maximum value.
     * @param intSize The maximum number of significant digits.
     * @param fracSize The maximum number of fractional digits.
     */
    public CurrencyFieldMetaData(String name, String labelToken, Boolean isMandatory,
                                 String layout, Currency minValue, Currency maxValue, Integer intSize, Integer fracSize) {
        super(name, Defaults.CURRENCY, labelToken, isMandatory);
        m_layout = layout;
        m_minValue = minValue;
        m_maxValue = maxValue;
        m_intSize = intSize;
        m_fracSize = fracSize;
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
    public Currency getMinValue() {
        return m_minValue;
    }

    /** Getter for property maxValue.
     * @return Value of property maxValue.
     */
    public Currency getMaxValue() {
        return m_maxValue;
    }

    /** Getter for property intSize.
     * @return Value of property intSize.
     */
    public Integer getIntSize() {
        return m_intSize;
    }

    /** Getter for property fracSize.
     * @return Value of property fracSize.
     */
    public Integer getFracSize() {
        return m_fracSize;
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
        if ( m_maxValue != null ) i += m_maxValue.hashCode();
        if ( m_intSize != null ) i += m_intSize.hashCode();
        if ( m_fracSize != null ) i += m_fracSize.hashCode();
        return i;
    }

    /** Compares this object with another CurrencyFieldMetaData object.
     * Returns a true if both the objects have the same properties.
     * @param obj the other CurrencyFieldMetaData object.
     * @return a true if both the objects have the same properties.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ( obj instanceof CurrencyFieldMetaData ) {
            CurrencyFieldMetaData field2 = (CurrencyFieldMetaData) obj;
            if ( super.equals(field2) ) {
                if ( ( ( m_layout!=null && m_layout.equals(field2.m_layout) ) || (m_layout == null && field2.m_layout == null) )
                && ( ( m_minValue!=null && m_minValue.equals(field2.m_minValue) ) || (m_minValue == null && field2.m_minValue == null) )
                && ( ( m_maxValue!=null && m_maxValue.equals(field2.m_maxValue) ) || (m_maxValue == null && field2.m_maxValue == null) )
                && ( ( m_fracSize!=null && m_fracSize.equals(field2.m_fracSize) ) || (m_fracSize == null && field2.m_fracSize == null) )
                && ( ( m_intSize!=null && m_intSize.equals(field2.m_intSize) ) || (m_intSize == null && field2.m_intSize == null) ) )
                    isEqual = true;
            }
        }
        return isEqual;
    }

    /** Compares this object with another CurrencyFieldMetaData object.
     * Note: this class has a natural ordering that is inconsistent with equals
     * @param obj the other CurrencyFieldMetaData object.
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
        buffer.append(comma); buffer.append("IntSize"); buffer.append(equals); buffer.append(m_intSize);
        buffer.append(comma); buffer.append("FracSize"); buffer.append(equals); buffer.append(m_fracSize);

        return buffer.toString();
    }

    /** Getter for property width.
     * @return Value of property width.
     */
    public int getWidth() {
        int i = 0;
        if ( m_intSize != null )
            i = m_intSize.intValue();

        if ( m_fracSize != null )
            i = m_fracSize.intValue();

        if ( i == 0 )
            i = DEFAULT_WIDTH;
        else {
            //decimal separator
            i += 1;
            //group separator
            i += i/3;
            //negative sign
            i += 1;
        }
        return i;
    }

    public static String getCurrencyFormat() {
       return LocaleHelper.getProperty("currency.format");
    }
}
