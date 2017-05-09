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

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.log4j.Logger;
import org.jaffa.metadata.DateOnlyFieldMetaData;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;

/**
 * CalendarTag used to generate a calendar popup to pick dates/times. Complements the Editbox widgets
 * when an editbox is being used to enter a DateOnly or DateTime value
 */
public class CalendarTag extends CustomModelTag implements IWidgetTag,IFormTag {
    
    private static Logger log = Logger.getLogger(LookupTag.class);
    
    /**
     * property declaration for tag attribute: date.
     */
    private boolean m_date;
    
    /**
     * property declaration for tag attribute: time.
     */
    private boolean m_time;
    
    
    /**
     * property declaration for tag attribute: time.
     */
    private String m_layout;
    
    
    IPropertyRuleIntrospector m_propertyRuleIntrospector = null;
    
    private static final String TAG_NAME = "CalendarTag";
    
    public CalendarTag() {
        super();
        initTag();
    }
    
    
    private void initTag() {
        setField(null);
        m_date = false;
        m_time = false;
        m_layout = null;
        m_propertyRuleIntrospector = null;
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    
    /** Initalize the widget based on the field the calendar is associated to
     *
     * Fill in this method to perform other operations from doStartTag().
     *
     */
    public void otherDoStartTagOperations()  {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error(TAG_NAME+".otherDoStartTagOperations() error: " + e);
        }
        
        // Preprocess if within a Property widget, and initialize rules
        lookupPropertyTag();
        
        
        // raise an error, if the 'component' is null
        if (getField() == null) {
            String str = "The " + TAG_NAME + " requires 'field' parameter to be supplied or it should be within a PropertyTag which has a ForeignKey rule containing the 'component' parameter";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
    }
    
    /** This tag should have no body content
     */
    public boolean theBodyShouldBeEvaluated()  {
        return false;
    }
    
    
    /** The HTML is generated in this end tag, assuming the underlying field
     *  is not read-only or hidden
     */
    public void otherDoEndTagOperations() throws JspException {
        
        super.otherDoEndTagOperations();
        
        if (getPropertyRuleIntrospector() == null ||
                (getPropertyRuleIntrospector() != null && !getPropertyRuleIntrospector().isHidden() && !getPropertyRuleIntrospector().isReadOnly() ) ) {
            
            try {
                JspWriter out = pageContext.getOut();
                out.println( getHtml() );
            } catch (IOException e) {
                String str = "Exception in writing the " + TAG_NAME;
                log.error(str, e);
                throw new JspWriteRuntimeException(str, e);
            }
        } else {
            log.debug(TAG_NAME + " Not displayed as field " + getField() + " is hidden or read-only");
        }
        
    }
    
    
    public String getLayout() {
        // If the layout has not been set, derive it from the underlying field, and rules engine
        if (m_layout == null && getPropertyRuleIntrospector() != null)
            return getPropertyRuleIntrospector().getLayout();
        else
            return m_layout;
    }
    
    public void setLayout(java.lang.String value) {
        m_layout = value;
    }
    
    
    public boolean getDate() {
        return m_date;
    }
    
    public void setDate(boolean value) {
        m_date = value;
    }
    
    public boolean getTime() {
        return m_time;
    }
    
    public void setTime(boolean value) {
        m_time = value;
    }
    
    
    
    /** Checks for the nearest outer PropertyTag.
     *  Will set the field and layout values of the Tag based on the property tag, and
     *  and rules associated to the underlying field.
     */
    private void lookupPropertyTag() {
        
        PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
        if (propertyTag != null) {
            if (getField() == null)
                setField(propertyTag.getField());
            
            // initialize rule base for this tag
            setPropertyRuleIntrospector(propertyTag.getPropertyRuleIntrospector());
        }
        
        try {
            // Check for an IPropertyRuleIntrospector, if this tag is not within a Property widget
            if(getPropertyRuleIntrospector() == null)
                // initialize rule base for this tag if not set by an outer PropertyTag
                setPropertyRuleIntrospector(TagHelper.getPropertyRuleIntrospector(pageContext, getField()));
            
            // Get the model of related field
            SimpleWidgetModel model = null;
            try {
                model = (SimpleWidgetModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
            } catch (ClassCastException e) {
                String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
                log.error(str, e);
                throw new JspWriteRuntimeException(str, e);
            }
            
            // Wrap the propertyRuleIntrospector with any rules of the related field
            setPropertyRuleIntrospector( TagHelper.wrapPropertyRuleIntrospector(getPropertyRuleIntrospector(), model) );
            
        } catch (JspException e) {
            log.error(TAG_NAME+".otherDoStartTagOperations(): error="+e);
        }
        
    }
    
    
    private String getHtml() {
        StringBuffer buf = new StringBuffer("<button");
        buf.append(" class='Calendar'");
        buf.append(" type='button'")
        .append(" onclick='javascript:NewCal(\"")
        .append(getHtmlIdPrefix())
        .append("\", \"")
        .append(DateOnlyFieldMetaData.getDateOnlyFormat())
        .append("\")'>&nbsp;</button>");
        return buf.toString();
    }
    
    
    /** Get the current rule introspector for this property */
    public IPropertyRuleIntrospector getPropertyRuleIntrospector() {
        return m_propertyRuleIntrospector;
    }
    
    /** Set the current rule introspector for this property */
    public void setPropertyRuleIntrospector(IPropertyRuleIntrospector propertyRuleIntrospector) {
        m_propertyRuleIntrospector = propertyRuleIntrospector;
    }
    
}
