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
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.CodeHelperOutElementDto;
import org.jaffa.components.codehelper.dto.CriteriaElementDto;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.CallBackDropdownHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

/** Tag Handler for the DropDown widget.*/
//public class DropDownTag extends BodyTagSupport implements TryCatchFinally {
public class DropDownTag extends CustomModelTag implements IWidgetTag,IFormTag,IBodyTag {
    
    private static Logger log = Logger.getLogger(DropDownTag.class);
    private static final String TAG_NAME = "DropDown";
    
    // ***  additional fields ***
    /** Maintains the WidgetModel*/
    private SimpleWidgetModel m_model = null;
    /** Set to true, if this field is marked as readOnly by the rules engine */
    private boolean m_displayOnly = false;
    
    
    private String m_dependant1 = null;
    private String m_dependant2 = null;
    private String m_dependant3 = null;
    
    private String m_dependant1KeyField = null;
    private String m_dependant2KeyField = null;
    private String m_dependant3KeyField = null;
    
    private String m_domain = null;
    private String m_dropdownValueField = null;
    private String m_dropdownDescField = null;
    
    /** Introspector for the field */
    private IPropertyRuleIntrospector m_propertyRuleIntrospector = null;
    
    /** Default constructor.
     */
    public DropDownTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_field = null;
        m_model = null;
        m_displayOnly = false;
        m_propertyRuleIntrospector = null;
    }
    
    
    /** The DropDownOptionTag invokes this method to add Select-Options.
     * @param label The label.
     * @param value The value.
     */
    public void addOption(String label, String value) {
        m_model.addOption(label, value);
    }
    
    
    /** This gets the WidgetModel for the field from the pageContext.
     */
    public void otherDoStartTagOperations() {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error("DropDownTag.otherDoStartTagOperations() error="+e);
        }
        
        // Preprocess if within a Property widget
        m_propertyRuleIntrospector = lookupPropertyTag();
        
        // raise an error, if the 'field' is null
        if (getField() == null) {
            String str = "The " + TAG_NAME + " requires 'field' parameter to be supplied or it should be within a PropertyTag";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
        
        // Determine the m_model. The HTML will be rendered in the otherDoEndTagOperations() method
        try {
            m_model = (SimpleWidgetModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
        } catch (ClassCastException e) {
            String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }
        
        try {
            // Check for an IPropertyRuleIntrospector, if this tag is not within a Property widget
            if (m_propertyRuleIntrospector == null)
                m_propertyRuleIntrospector = TagHelper.getPropertyRuleIntrospector(pageContext, getField());
            
            // Wrap the propertyRuleIntrospector
            m_propertyRuleIntrospector = TagHelper.wrapPropertyRuleIntrospector(m_propertyRuleIntrospector, m_model);
            
            if(log.isDebugEnabled())
                log.debug(this.NAME + " [field=" + getField() + "] PropertyRuleIntrospector is " + m_propertyRuleIntrospector);
        } catch (JspException e) {
            log.error("DropDownTag.otherDoStartTagOperations(): error wrapping property rule inspector="+e);
        }
        
        if (m_propertyRuleIntrospector.isHidden()) {
            // Display the (Restricted) text for a hidden field
            try {
                pageContext.getOut().print(TagHelper.getTextForHiddenField(pageContext));
            } catch (IOException e) {
                String str = "Exception in writing the " + TAG_NAME;
                log.error(str, e);
                throw new JspWriteRuntimeException(str, e);
            }
        } else {
            if (m_propertyRuleIntrospector.isReadOnly())
                m_displayOnly = true;
        }
        
    }
    
    /** Returns a true.
     * @return a true.
     */
    public boolean theBodyShouldBeEvaluated() {
        return !m_propertyRuleIntrospector.isHidden();
    }
    
    
    /** Called from the doEndTag()
     */
    public void otherDoEndTagOperations() throws JspException {
        
        super.otherDoEndTagOperations();
        
        // Generate the HTML
        if (!m_propertyRuleIntrospector.isHidden() && m_model != null) {
            String html = null;
            if (m_displayOnly) {
                // Just display the text for a readOnly field
                html = getDisplayOnlyHtml();
            } else {
                String classPrefix = m_propertyRuleIntrospector.isMandatory() ? "<span class=\"dropdownMandatoryPrefix\">&nbsp;</span>" : "<span class=\"dropdownOptionalPrefix\">&nbsp;</span>";
                String classSuffix = m_propertyRuleIntrospector.isMandatory() ? "<span class=\"dropdownMandatorySuffix\">&nbsp;</span>" : "<span class=\"dropdownOptionalSuffix\">&nbsp;</span>";
                html = classPrefix + getHtml() + classSuffix;
                html += getJavascript();
            }
            
            if (html != null) {
                // Write the HTML
                JspWriter out = pageContext.getOut();
                try {
                    out.print(html);
                } catch (IOException e) {
                    String str = "Exception in writing the " + TAG_NAME;
                    log.error(str, e);
                    throw new JspWriteRuntimeException(str, e);
                }
            }
        }
    }
    
    /** Getter for the attribute displayOnly.
     * @return Value of attribute displayOnly.
     */
    public boolean getDisplayOnly() {
        return m_displayOnly;
    }
    
    /** Setter for the attribute displayOnly.
     * @param value New value of attribute displayOnly.
     */
    public void setDisplayOnly(boolean value) {
        m_displayOnly = value;
    }
    
    /** Getter for the attribute domain.
     * @return Value of attribute domain.
     */
    public String getDomain() {
        return m_domain;
    }
    
    /** Setter for the attribute domain.
     * @param value New value of attribute domain.
     */
    public void setDomain(String value) {
        m_domain = value;
    }
    
    /** Getter for the attribute dependant1.
     * @return Value of attribute dependant1.
     */
    public String getDependant1() {
        return m_dependant1;
    }
    
    /** Setter for the attribute dependant1.
     * @param value New value of attribute dependant1.
     */
    public void setDependant1(String value) {
        m_dependant1 = value;
    }
    
    /** Getter for the attribute dependant2.
     * @return Value of attribute dependant2.
     */
    public String getDependant2() {
        return m_dependant2;
    }
    
    /** Setter for the attribute dependant2.
     * @param value New value of attribute dependant2.
     */
    public void setDependant2(String value) {
        m_dependant2 = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getDependant3() {
        return m_dependant3;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setDependant3(String value) {
        m_dependant3 = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getKey1() {
        return m_dependant1KeyField;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setKey1(String value) {
        m_dependant1KeyField = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getKey2() {
        return m_dependant2KeyField;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setKey2(String value) {
        m_dependant2KeyField = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getKey3() {
        return m_dependant3KeyField;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setKey3(String value) {
        m_dependant3KeyField = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getDropdownValueField() {
        return m_dropdownValueField;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setDropdownValueField(String value) {
        m_dropdownValueField = value;
    }
    
    /** Getter for the attribute dependant3.
     * @return Value of attribute dependant3.
     */
    public String getDropdownDescField() {
        return m_dropdownDescField;
    }
    
    /** Setter for the attribute dependant3.
     * @param value New value of attribute dependant3.
     */
    public void setDropdownDescField(String value) {
        m_dropdownDescField = value;
    }
    
    
    /** This generates the HTML for the tag.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //
        // The bodycontent will NOT be written.. since its just expected to invoke the addOption() method
        //bodyContent.writeOut(out);
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    private String getHtml() {
        String idPrefix = getHtmlIdPrefix();
        StringBuffer b = new StringBuffer();
        b.append("<select id = \"" + idPrefix + "\""+ (TagHelper.isEnclosed(pageContext) ? "" : " name=\"" + getField() + "WV\"") + " class=\"WidgetDropDown\">\n");
        Object widgetValueObject = m_model.getWidgetValue();
        String widgetValue = widgetValueObject != null ? m_propertyRuleIntrospector.format(widgetValueObject) : m_model.getStringValue();
        
        
        // If Running in dependant Dropdown mode and value is available
        // Build an option for display purposes.
        if (this.getDomain() != null && widgetValue != null && widgetValue.length() > 0) {
            CriteriaElementDto[] criteriaElementDtos;
            criteriaElementDtos = new CriteriaElementDto[1];
            CallBackDropdownHelper cbddHelper = new CallBackDropdownHelper();
            CriteriaElementDto criteriaElementDto = new CriteriaElementDto();
            criteriaElementDto.setFieldName(this.getDropdownValueField());
            criteriaElementDto.setCriteria(StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, widgetValue , null));
            criteriaElementDtos[0] = criteriaElementDto;
            try {
                CodeHelperOutElementDto m_dropDownCodes = cbddHelper.getOptions((HttpServletRequest) pageContext.getRequest(), this.getDropdownValueField(), this.getDropdownDescField(), this.getDomain() ,criteriaElementDtos);
                for (int i=0; i< m_dropDownCodes.getCodeHelperOutCodeDtoCount(); i++) {
                    m_model.addOption("" + m_dropDownCodes.getCodeHelperOutCodeDto(i).getDescription() ,"" +  m_dropDownCodes.getCodeHelperOutCodeDto(i).getCode());
                }
            } catch (ApplicationExceptions e) {
            } catch (FrameworkException e) {
            }
        }
        
        if (widgetValue == null)
            widgetValue = "";
        
        
        // Loop through display columns
        List options = m_model.getOptions();
        if (options != null) {
            for(Iterator i = options.iterator(); i.hasNext(); ) {
                SimpleWidgetModel.Option option = (SimpleWidgetModel.Option) i.next();
                String label = option.getLabel();
                if (label != null)
                    label = MessageHelper.replaceTokens(pageContext, label);
                widgetValueObject = option.getWidgetValue();
                String value = widgetValueObject != null ? m_propertyRuleIntrospector.format(widgetValueObject) : option.getStringValue();
                if (value == null)
                    value = "";
                b.append("<option value=\"" + StringHelper.convertToHTML(value) + "\"" + (value.equals(widgetValue) ? " SELECTED" : "" ) + ">" + label + "</option>\n");
            }
        }
        b.append("</select>\n");
        b.append(getWidgetRegistrationScript(idPrefix, false));
        return b.toString();
    }
    
    private String getJavascript() {
        StringBuffer b = new StringBuffer();
        if (getDomain() != null) {
            b.append("<script>");
            b.append("ddm.registerDropDown(");
            b.append("\""  + getKey1() + "\"");
            b.append(",\"" + getKey2() + "\"");
            b.append(",\"" + getKey3() + "\"");
            b.append(",\"" + getIdPrefix(getDependant1()) + "\"");
            b.append(",\"" + getIdPrefix(getDependant2()) + "\"");
            b.append(",\"" + getIdPrefix(getDependant3()) + "\"");
            b.append(",\"" + getDomain() + "\"");
            b.append(",\"" + getIdPrefix(getField()) + "\"");
            b.append(",\"" + getDropdownValueField() + "\"");
            b.append(",\"" + getDropdownDescField() + "\"");
            b.append(",\"" + this.getField() + "\"");
            b.append(");");
            b.append("</script>");
        }
        return b.toString();
    }
    
    private String getDisplayOnlyHtml() {
        Object widgetValueObject = m_model.getWidgetValue();
        String widgetValue = widgetValueObject != null ? m_propertyRuleIntrospector.format(widgetValueObject) : m_model.getStringValue();
        if (widgetValue == null)
            widgetValue = "";
        
        // Loop through the DropDown options and find the label for the current-value of the DropDown
        List options = m_model.getOptions();
        if (options != null) {
            for(Iterator i = options.iterator(); i.hasNext(); ) {
                SimpleWidgetModel.Option option = (SimpleWidgetModel.Option) i.next();
                widgetValueObject = option.getWidgetValue();
                String value = widgetValueObject != null ? m_propertyRuleIntrospector.format(widgetValueObject) : option.getStringValue();
                if (value == null)
                    value = "";
                if (value.equals(widgetValue)) {
                    if (option.getLabel() != null)
                        widgetValue = MessageHelper.replaceTokens(pageContext, option.getLabel());
                    break;
                }
            }
        }
        
        return widgetValue;
    }
    
    /** Checks for the nearest outer PropertyTag.
     * Will set the 'field' on this tag, if not specifed, with the value from the outer PropertyTag.
     * @return the IPropertyRuleIntrospector from the outer PropertyTag.
     */
    private IPropertyRuleIntrospector lookupPropertyTag() {
        PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
        if (propertyTag != null) {
            if (getField() == null)
                setField(propertyTag.getField());
            return propertyTag.getPropertyRuleIntrospector();
        } else
            return null;
    }
    
    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
    }
    
    private String getIdPrefix(String fieldValue) {
        if (fieldValue != null && fieldValue.length() > 0 ) {
            return pageContext.findAttribute(TagHelper.ATTRIBUTE_ID_PREFIX) + "_" + fieldValue;
        } else {
            return "";
        }
    }
    
    
}
