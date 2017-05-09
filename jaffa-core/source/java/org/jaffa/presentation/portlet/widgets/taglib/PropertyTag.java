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

package org.jaffa.presentation.portlet.widgets.taglib;

import javax.servlet.jsp.JspException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.apache.log4j.Logger;

/** This is wrapper for simple widgets. When combined with the rules engine, this can hide/un-hide the inner widgets.
 * The field name is mandatory. There should be a corresponding getter in the underlying form bean for that field.
 * By default, this widget will obtain rules for the form-bean/field-name combination from the rules engine.
 * This can be overridden by supplying the propertyClass and/or propertyName attributes.
 */
public class PropertyTag extends CustomModelTag implements IFormTag {
    
    private static Logger log = Logger.getLogger(PropertyTag.class);
    /**
     * property declaration for tag attribute: field.
     */
    //private java.lang.String m_field;
    
    /**
     * property declaration for tag attribute: propertyClass.
     */
    private java.lang.String m_propertyClass;
    
    /**
     * property declaration for tag attribute: propertyName.
     */
    private java.lang.String m_propertyName;
    
    
    // ***  additional fields ***
    private IPropertyRuleIntrospector m_propertyRuleIntrospector = null;
    
    
    /** Default constructor.
     */
    public PropertyTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_field = null;
        m_propertyClass = null;
        m_propertyName = null;
        
        m_propertyRuleIntrospector = null;
    }
    
    /** This is invoked from the doStartTag() method.
     * It obtains the necessary IPropertyRuleIntrospector instance from the rules engine.
     * @throws JspException if any error occurs.
     */
    public void otherDoStartTagOperations() throws JspException {
        
        try {
            super.otherDoStartTagOperations();   // 6/17/2005
        } catch (JspException e) {
            log.error("Error calling super.otherDoStartTagOperations()", e);
        }
        
        // Force values on the optional attributes
        if (m_propertyName == null)
            m_propertyName = m_field;
        
        if (m_propertyClass == null) {
            // Look for an outer PropertyTag, which may be provided to scope a set of properties within a given propertyClass
            PropertyTag parent = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
            if (parent != null)
                m_propertyClass = parent.getPropertyClass();
            
            // Use the underlying formbean if the propertyClass is still null
            if (m_propertyClass == null)
                m_propertyClass = TagHelper.getFormBase(pageContext).getClass().getName();
        }
        
        // A propertyTag may be provided to scope a set of properties within a given propertyClass, in which case do not search for a propertyRuleIntrospector
        if (m_field != null)
            m_propertyRuleIntrospector = TagHelper.getPropertyRuleIntrospector(pageContext, m_propertyName, m_propertyClass);
    }
    
    /** This method if called from doStartTag().
     * It returns a false if the property is marked as 'hidden' in the rules engine.
     * @return a false if the property is marked as 'hidden' in the rules engine.
     */
    public boolean theBodyShouldBeEvaluated()  {
        return m_propertyRuleIntrospector == null || !m_propertyRuleIntrospector.isHidden();
    }
    
    
    /**
     * Getter for property propertyClass.
     * @return Value of property propertyClass.
     */
    public java.lang.String getPropertyClass() {
        return m_propertyClass;
    }
    
    /** Setter for property propertyClass.
     * @param value New value of property propertyClass.
     */
    public void setPropertyClass(java.lang.String value) {
        m_propertyClass = value;
    }
    
    /**
     * Getter for property propertyName.
     * @return Value of property propertyName.
     */
    public java.lang.String getPropertyName() {
        return m_propertyName;
    }
    
    /** Setter for property propertyName.
     * @param value New value of property propertyName.
     */
    public void setPropertyName(java.lang.String value) {
        m_propertyName = value;
    }
    
    
    /**
     * Getter for property propertyRuleIntrospector.
     * @return Value of property propertyRuleIntrospector.
     */
    public IPropertyRuleIntrospector getPropertyRuleIntrospector() {
        return m_propertyRuleIntrospector;
    }
    
    
    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
    }
    
    // do not inherit from parent class:
    
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }
}
