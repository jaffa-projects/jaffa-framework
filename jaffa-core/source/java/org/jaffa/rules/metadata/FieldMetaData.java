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

/** An instance of this class represents a field defined in the Rules config file. */
public class FieldMetaData {
    
    private String m_name;
    private boolean m_overridesDefault;
    private String m_extendsClass;
    private String m_extendsField;
    private List m_rules;
    
    /** Creates an instance. */
    public FieldMetaData() {
        m_rules = new LinkedList();
    }
    
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
    
    /** Getter for the property overridesDefault.
     * @return Value of property overridesDefault.
     */
    public boolean getOverridesDefault() {
        return m_overridesDefault;
    }
    
    /** Setter for property overridesDefault.
     * @param overridesDefault New value of property overridesDefault.
     */
    public void setOverridesDefault(boolean overridesDefault) {
        m_overridesDefault = overridesDefault;
    }
    
    /** Getter for the property extendsClass.
     * @return Value of property extendsClass.
     */
    public String getExtendsClass() {
        return m_extendsClass;
    }
    
    /** Setter for property extendsClass.
     * @param extendsClass New value of property extendsClass.
     */
    public void setExtendsClass(String extendsClass) {
        m_extendsClass = extendsClass;
    }
    
    /** Getter for the property extendsField.
     * @return Value of property extendsField.
     */
    public String getExtendsField() {
        return m_extendsField;
    }
    
    /** Setter for property extendsField.
     * @param extendsField New value of property extendsField.
     */
    public void setExtendsField(String extendsField) {
        m_extendsField = extendsField;
    }
    
    /** Clears the rules for the field.
     */
    public void clearRules() {
        m_rules.clear();
    }
    
    /** Add a rule to the field.
     * @param rule The rule to be added.
     */
    public void addRule(RuleMetaData rule) {
        m_rules.add(rule);
    }
    
    /** Getter for the property rules.
     * @return Value of property rules.
     */
    public RuleMetaData[] getRules() {
        return (RuleMetaData[]) m_rules.toArray(new RuleMetaData[0]);
    }
    
    /** Setter for property rules.
     * @param rules New value of property rules.
     */
    public void setRules(RuleMetaData[] rules) {
        clearRules();
        if (rules != null) {
            for (int i = 0; i < rules.length; i++)
                addRule(rules[i]);
        }
    }
    
    
    
    /** Returns diagnostic information.
     * @return a String containing diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<FieldMetaData>");
        buf.append("<name>"); if (m_name != null) buf.append(m_name); buf.append("</name>");
        buf.append("<overridesDefault>"); buf.append(m_overridesDefault); buf.append("</overridesDefault>");
        buf.append("<extendsClass>"); if (m_extendsClass != null) buf.append(m_extendsClass); buf.append("</extendsClass>");
        buf.append("<extendsField>"); if (m_extendsField != null) buf.append(m_extendsField); buf.append("</extendsField>");
        for (Iterator i = m_rules.iterator(); i.hasNext(); )
            buf.append(i.next());
        buf.append("</FieldMetaData>");
        return buf.toString();
    }
}
