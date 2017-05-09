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

/** An instance of this class represents the 'field-validator' element of the Validator config file. */
public class FieldValidatorMetaData {
    
    private String m_name;
    private String m_description;
    private String m_className;
    private boolean m_mandatory;
    private Map m_parameters;
    
    /** Getter for the property name.
     * @return Value of property name.
     */
    public String getName() {
        return m_name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        m_name = name;
    }
    
    /** Getter for the property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return m_description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        m_description = description;
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
    
    /** Getter for the property mandatory.
     * @return Value of property mandatory.
     */
    public boolean getMandatory() {
        return m_mandatory;
    }
    
    /** Setter for property mandatory.
     * @param mandatory New value of property mandatory.
     */
    public void setMandatory(boolean mandatory) {
        m_mandatory = mandatory;
    }
    
    /** Clears the parameters.
     */
    public void clearParameters() {
        if (m_parameters != null)
            m_parameters.clear();
    }
    
    /** Add a parameter.
     * @param name The name of the parameter.
     * @param value The value of the parameter.
     */
    public void addParameter(String name, String value) {
        if (m_parameters == null)
            m_parameters = new HashMap();
        m_parameters.put(name, value);
    }
    
    /** Getter for the property parameters.
     * @return Value of property parameters.
     */
    public Map getParameters() {
        return m_parameters;
    }
    
    /** Returns the value of the parameter.
     * @param name The name of the parameter.
     * @return Value of the parameter.
     */
    public String getParameter(String name) {
        if (m_parameters != null)
            return (String) m_parameters.get(name);
        else
            return null;
    }
    
    
    
    /** Returns diagnostic information.
     * @return a String containing diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<FieldValidatorMetaData>");
        buf.append("<name>"); if (m_name != null) buf.append(m_name); buf.append("</name>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        buf.append("<className>"); if (m_className != null) buf.append(m_className); buf.append("</className>");
        buf.append("<mandatory>"); buf.append(m_mandatory); buf.append("</mandatory>");
        if (m_parameters != null) {
            for (Iterator i = m_parameters.entrySet().iterator(); i.hasNext(); ) {
                buf.append("<parameter>");
                Map.Entry me = (Map.Entry) i.next();
                buf.append("<name>"); buf.append(me.getKey()); buf.append("</name>");
                buf.append("<value>"); buf.append(me.getValue()); buf.append("</value>");
                buf.append("</parameter>");
            }
        }
        buf.append("</FieldValidatorMetaData>");
        return buf.toString();
    }
}
