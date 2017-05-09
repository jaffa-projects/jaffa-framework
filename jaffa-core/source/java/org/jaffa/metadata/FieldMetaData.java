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

import java.io.Serializable;

/**
 * This is the base class for all the meta objects.
 */
public abstract class FieldMetaData  implements Cloneable, Comparable, Serializable {

    /** A constant indicating uppercase.*/
    public static final String UPPER_CASE = "UpperCase";

    /** A constant indicating lowercase.*/
    public static final String LOWER_CASE = "LowerCase";

    /** A constant indicating mixedcase.*/
    public static final String MIXED_CASE = "MixedCase";

    // NOTE: keep the equals(), clone(), compareTo(), hashCode() methods in sync
    private String m_name = null;
    private String m_dataType = null;
    private String m_labelToken = null;
    private Boolean m_isMandatory = null;

    /** Creates an instance.
     * @param name The field name.
     * @param dataType The data type.
     * @param labelToken The token used for displaying labels.
     * @param isMandatory Indicates if the field is mandatory.
     */
    protected FieldMetaData(String name, String dataType, String labelToken, Boolean isMandatory) {
        m_name = name;
        m_dataType = dataType;
        m_labelToken = labelToken;
        m_isMandatory = isMandatory;
    }


    /** Getter for property layout.
     * @return Value of property layout.
     */
    public String getName() {
        return m_name;
    }

    /** Getter for property dataType.
     * @return Value of property dataType.
     */
    public String getDataType() {
        return m_dataType;
    }

    /** Getter for property labelToken.
     * @return Value of property labelToken.
     */
    public String getLabelToken() {
        return m_labelToken;
    }

    /** Getter for property isMandatory.
     * @return Value of property isMandatory.
     */
    public Boolean isMandatory() {
        return m_isMandatory;
    }

    /** Returns a clone of the object.
     * @return a clone of the object.
     */
    public Object clone() {
        try {
            return  super.clone();
            // no more processing required since the fields are immutable
        } catch(CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            return null;
        }
    }

    /** Returns the hash code.
     * @return the hash code.
     */
    public int hashCode() {
        int i = 0;
        if ( m_name != null ) i += m_name.hashCode();
        if ( m_dataType != null ) i += m_dataType.hashCode();
        if ( m_labelToken != null ) i += m_labelToken.hashCode();
        if ( m_isMandatory != null ) i += m_isMandatory.hashCode();
        return i;
    }

    /** Compares this object with another FieldMetaData object.
     * Returns a true if both the objects have the same properties.
     * @param obj the other FieldMetaData object.
     * @return a true if both the objects have the same properties.
     */
    public boolean equals(Object obj) {
        if ( obj instanceof FieldMetaData ) {
            FieldMetaData field2 = (FieldMetaData) obj;
            if ( ( ( m_name!=null && m_name.equals(field2.m_name) ) || (m_name == null && field2.m_name == null) )
            && ( ( m_dataType!=null && m_dataType.equals(field2.m_dataType) ) || (m_dataType == null && field2.m_dataType == null) )
            && ( ( m_labelToken!=null && m_labelToken.equals(field2.m_labelToken) ) || (m_labelToken == null && field2.m_labelToken == null) )
            && ( ( m_isMandatory!=null && m_isMandatory.equals(field2.m_isMandatory) ) || (m_isMandatory == null && field2.m_isMandatory == null) ) )
                return true;
            else
                return false;
        } else
            return false;
    }

    /** Compares this object with another FieldMetaData object.
     * Note: this class has a natural ordering that is inconsistent with equals
     * Only the field name is compared.
     * @param obj the other FieldMetaData object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        int i;
        FieldMetaData target = (FieldMetaData) obj;
        if ( m_name != null && target.m_name == null)
            i = 1;
        else if ( m_name == null && target.m_name != null )
            i = -1;
        else if ( m_name != null && target.m_name != null )
            i = m_name.compareTo(target.m_name);
        else
            i = 0;
        return i;
    }

    /** Returns the diagnostic information.
     * @return the diagnostic information.
     */
    public String toString() {
        String comma = ", ";
        String equals = "=";
        StringBuffer buffer = new StringBuffer();

        buffer.append("Name");  buffer.append(equals); buffer.append(m_name);
        buffer.append(comma); buffer.append("DataType"); buffer.append(equals); buffer.append(m_dataType);
        buffer.append(comma); buffer.append("LabelToken"); buffer.append(equals); buffer.append(m_labelToken);
        buffer.append(comma); buffer.append("Mandatory"); buffer.append(equals); buffer.append(m_isMandatory);

        return buffer.toString();
    }

    /** Getter for property width.
     * @return Value of property width.
     */
    public abstract int getWidth();
}
