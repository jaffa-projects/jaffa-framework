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

package org.jaffa.rules.metadata;

import java.util.*;


/** An instance of this class represents a domain object or DTO defined in the Rules config file. */
public class ClassMetaData {
    
    private String m_className;
    private Map m_fields;
    
    /** Creates a new instance. */
    public ClassMetaData() {
        m_fields = new LinkedHashMap();
    }
    
    /** Getter for the property className.
     * @return Value of property className.
     */
    public String getClassName() {
        return m_className;
    }
    
    /** Setter for property className.
     * @param className New value of property className.
     */
    public void setClassName(String className) {
        m_className = className;
    }
    
    /** Clears the fields.
     */
    public void clearFields() {
        m_fields.clear();
    }
    
    /** Add a field.
     * @param field The field to be added.
     */
    public void addField(FieldMetaData field) {
        m_fields.put(field.getName(), field);
    }
    
    /** Getter for the property fields.
     * @return Value of property fields.
     */
    public FieldMetaData[] getFields() {
        return (FieldMetaData[]) m_fields.values().toArray(new FieldMetaData[0]);
    }
    
    /** Returns the FieldMetaData for the given field.
     * @param fieldName the field for which the FieldMetaData is to be returned. A null will be returned in case no such definition is found.
     * @return the FieldMetaData.
     */
    public FieldMetaData getField(String fieldName) {
        return (FieldMetaData) m_fields.get(fieldName);
    }
    
    /** Setter for property fields.
     * @param fields New value of property fields.
     */
    public void setFields(FieldMetaData[] fields) {
        clearFields();
        if (fields != null) {
            for (int i = 0; i < fields.length; i++)
                addField(fields[i]);
        }
    }
    
    
    
    /** Returns diagnostic information.
     * @return a String containing diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<ClassMetaData>");
        buf.append("<className>"); if (m_className != null) buf.append(m_className); buf.append("</className>");
        for (Iterator i = m_fields.values().iterator(); i.hasNext(); )
            buf.append(i.next());
        buf.append("</ClassMetaData>");
        return buf.toString();
    }
}
