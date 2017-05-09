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

import org.jaffa.datatypes.Defaults;

/**
 * An instance of this class will hold meta information for a String field.
 */
public class StringFieldMetaData extends FieldMetaData {

    /** Default width.*/
    public static final int DEFAULT_WIDTH = 20;

    // NOTE: keep the equals(), clone(), compareTo(), hashCode() methods in sync
    private String m_pattern = null;
    private Integer m_length = null;
    private String m_caseType = null;
    private Integer m_minLength = null;


    /** Creates an instance.
     */
    public StringFieldMetaData() {
        super(null, Defaults.STRING, null, null);
    }

    /** Creates an instance.
     * @param name The field name.
     * @param labelToken The token used for displaying labels.
     * @param isMandatory Indicates if the field is mandatory.
     * @param pattern The pattern.
     * @param length The maximum length.
     * @param caseType The case type (upper, lower or mixed).
     */
    public StringFieldMetaData(String name, String labelToken,
    Boolean isMandatory, String pattern, Integer length, String caseType) {
        this(name, labelToken, isMandatory, pattern, length, caseType, null);
    }

    /** Creates an instance.
     * @param name The field name.
     * @param labelToken The token used for displaying labels.
     * @param isMandatory Indicates if the field is mandatory.
     * @param pattern The pattern.
     * @param length The maximum length.
     * @param caseType The case type (upper, lower or mixed).
     * @param minLength The minimum length.
     */
    public StringFieldMetaData(String name, String labelToken,
    Boolean isMandatory, String pattern, Integer length, String caseType, Integer minLength) {
        super(name, Defaults.STRING, labelToken, isMandatory);
        m_pattern = pattern;
        m_length = length;
        m_caseType = caseType;
        m_minLength = minLength;
    }

    /** Getter for property pattern.
     * @return Value of property pattern.
     */
    public String getPattern() {
        return m_pattern;
    }

    /** Getter for property length.
     * @return Value of property length.
     */
    public Integer getLength() {
        return m_length;
    }

    /** Getter for property caseType.
     * @return Value of property caseType.
     */
    public String getCaseType() {
        return m_caseType;
    }

    /** Getter for property minLength.
     * @return Value of property minLength.
     */
    public Integer getMinLength() {
        return m_minLength;
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
        if ( m_pattern != null ) i += m_pattern.hashCode();
        if ( m_length != null ) i += m_length.hashCode();
        if ( m_caseType != null ) i += m_caseType.hashCode();
        if ( m_minLength != null ) i += m_minLength.hashCode();
        return i;
    }

    /** Compares this object with another StringFieldMetaData object.
     * Returns a true if both the objects have the same properties.
     * @param obj the other StringFieldMetaData object.
     * @return a true if both the objects have the same properties.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ( obj instanceof StringFieldMetaData ) {
            StringFieldMetaData field2 = (StringFieldMetaData) obj;
            if ( super.equals(field2) ) {
            if ( ( ( m_pattern!=null && m_pattern.equals(field2.m_pattern) ) || (m_pattern == null && field2.m_pattern == null) )
            && ( ( m_length!=null && m_length.equals(field2.m_length) ) || (m_length == null && field2.m_length == null) )
            && ( ( m_caseType!=null && m_caseType.equals(field2.m_caseType) ) || (m_caseType == null && field2.m_caseType == null) )
            && ( ( m_minLength!=null && m_minLength.equals(field2.m_minLength) ) || (m_minLength == null && field2.m_minLength == null) ) )
                isEqual = true;
            }
        }
        return isEqual;
    }

    /** Compares this object with another StringFieldMetaData object.
     * Note: this class has a natural ordering that is inconsistent with equals
     * @param obj the other StringFieldMetaData object.
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

        buffer.append(comma); buffer.append("Pattern"); buffer.append(equals); buffer.append(m_pattern);
        buffer.append(comma); buffer.append("Length"); buffer.append(equals); buffer.append(m_length);
        buffer.append(comma); buffer.append("CaseType"); buffer.append(equals); buffer.append(m_caseType);
        buffer.append(comma); buffer.append("MinLength"); buffer.append(equals); buffer.append(m_minLength);

        return buffer.toString();
    }

    /** Getter for property width.
     * @return Value of property width.
     */
    public int getWidth() {
        int i = 0;
        if ( m_length == null )
            i = DEFAULT_WIDTH;
        else
            i = m_length.intValue();
        return i;
    }
}
