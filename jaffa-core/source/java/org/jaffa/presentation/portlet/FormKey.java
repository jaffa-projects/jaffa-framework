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

package org.jaffa.presentation.portlet;

import java.io.Serializable;

/** This object is returned by the event handlers. It indicates the form to which control should be passed & the corresponding Component's id. Ensure that a 'forward' mapping exists for the formName in the struts-config file.
 * The formName and componentId are immutable properties and can only be passed in the constructor.
 * The title can be set at any time and is mainly for use in the historyNav.jsp, for displaying the history bar links.
 */
public class FormKey implements Cloneable, Comparable, Serializable {
    
    private String m_formName = null;
    private String m_componentId = null;
    private String m_title = null;
    private static FormKey c_closeBrowserFormKey = null;
    
    /** Constructs an instance of the FormKey class
     * @param formName The formName
     * @param componentId The componentId
     */
    public FormKey(String formName, String componentId) {
        this(formName, componentId, null);
    }
    
    /** Constructs an instance of the FormKey class
     * @param formName The formName
     * @param componentId The componentId
     * @param title The title.
     */
    public FormKey(String formName, String componentId, String title) {
        m_formName = formName;
        m_componentId = componentId;
        m_title = title;
    }
    
    /** Returns the formName
     * @return The formName
     */
    public String getFormName() {
        return m_formName;
    }
    
    /** Returns the componentId
     * @return The componentId
     */
    public String getComponentId() {
        return m_componentId;
    }
    
    /** Getter for property title.
     * @return Value of property title.
     *
     */
    public String getTitle() {
        return m_title;
    }
    
    /** Setter for property title.
     * @param title New value of property title.
     *
     */
    public void setTitle(String title) {
        m_title = title;
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
    
    /** Compares this object with another FormKey object.
     * It compares the fields componentId, formName of the 2 objects.
     * @param obj the other FormKey object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        FormKey target = (FormKey) obj;
        int i = 0;
        
        // First compare the componentId
        if (m_componentId != null && target.m_componentId != null)
            i = m_componentId.compareTo(target.m_componentId);
        else if (m_componentId != null)
            i = 1;
        else if (target.m_componentId != null)
            i = -1;
        
        // Compare the formName if the componentIds are same
        if (i == 0) {
            if (m_formName != null && target.m_formName != null)
                i = m_formName.compareTo(target.m_formName);
            else if (m_formName != null)
                i = 1;
            else if (target.m_formName != null)
                i = -1;
        }
        
        return i;
    }
    
    /** Returns an int which will be the sum of the of the hashcodes of componentId, formName.
     * @return  an int which will be the sum of the of the hashcodes of componentId, formName.
     */
    public int hashCode() {
        return m_componentId != null ? m_componentId.hashCode() : 0
                + m_formName != null ? m_formName.hashCode() : 0;
    }
    
    /** Compares this object with another FormKey object.
     * Returns a true if both the objects have the same formName, componentId.
     * @param obj the other FormKey object.
     * @return a true if both the objects have the same formName, componentId.
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof FormKey) {
            FormKey target = (FormKey) obj;
            return (m_formName != null ? m_formName.equals(target.m_formName) : target.m_formName == null)
            && (m_componentId != null ? m_componentId.equals(target.m_componentId) : target.m_componentId == null);
        } else {
            return false;
        }
    }
    
    /** Returns the debug information for the object
     * @return The debug information
     */
    public String toString() {
        return "FormName=" + m_formName + ", ComponentId=" + m_componentId + ", Title=" + m_title;
    }
    
    /** Returns the FormKey that will render the JSP used for closing a browser window.
     * The JSP to be rendered is found by the forward mapping 'jaffa_closeBrowser' specified in the struts-config.xml file.
     * @return The FormKey that will render the JSP used for closing a browser window.
     */
    public static FormKey getCloseBrowserFormKey() {
        if (c_closeBrowserFormKey == null)
            c_closeBrowserFormKey = new FormKey("jaffa_closeBrowser", null);
        return c_closeBrowserFormKey;
    }
    
}
