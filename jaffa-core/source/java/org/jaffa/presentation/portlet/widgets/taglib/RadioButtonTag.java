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
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;

/** Tag Handler for the RadioButton widget.*/
public class RadioButtonTag extends CustomModelTag implements IWidgetTag,IFormTag {
    private static Logger log = Logger.getLogger(RadioButtonTag.class);
    private static final String TAG_NAME = "RadioButtonTag";
    private static String DEFAULT_DISPLAY_ONLY_IMAGE_ON = "jaffa/imgs/checkbox/check.gif";
    private static String DEFAULT_DISPLAY_ONLY_IMAGE_OFF = "jaffa/imgs/checkbox/false.gif";
    // couldn't find radiobutton.js
    //private static String c_header = "<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/RadioButton.js\"></SCRIPT>\n";
    /** property declaration for tag attribute: selectValue.
     */
    private String m_selectValue;
    /** property declaration for tag attribute: displayOnly.
     */
    private boolean m_displayOnly;
    /** Default constructor.
     */
    public RadioButtonTag() {
        super();
        initTag();
    }
    private void initTag() {
        m_field = null;
        m_selectValue = null;
        m_displayOnly = false;
    }
    /**
     * This generates the HTML for the tag.
     * @throws JspException if any error occurs.
     */
    public void otherDoStartTagOperations() throws JspException {
        super.otherDoStartTagOperations();
        // Preprocess if within a Property widget
        IPropertyRuleIntrospector propertyRuleIntrospector = lookupPropertyTag();
        // raise an error, if the 'field' is null
        if (getField() == null) {
            String str = "The " + TAG_NAME + " requires 'field' parameter to be supplied or it should be within a PropertyTag";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
        // Will hold the required html
        String html = null;
        // Get the model
        SimpleWidgetModel model = null;
        try {
            model = (SimpleWidgetModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
        } catch (ClassCastException e) {
            String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }
        // Check for an IPropertyRuleIntrospector, if this tag is not within a Property widget
        if (propertyRuleIntrospector == null)
            propertyRuleIntrospector = TagHelper.getPropertyRuleIntrospector(pageContext, getField());
        // Wrap the propertyRuleIntrospector
        propertyRuleIntrospector = TagHelper.wrapPropertyRuleIntrospector(propertyRuleIntrospector, model);
        if (propertyRuleIntrospector.isHidden()) {
            // Display the (Restricted) text for a hidden field
            html = TagHelper.getTextForHiddenField(pageContext);
        } else {
            if (model != null) {
                if (propertyRuleIntrospector.isReadOnly()) {
                    // Render a readOnly field as a displayOnly checkbox
                    m_displayOnly = true;
                }
                html = getHtml(getHtmlIdPrefix(), model, propertyRuleIntrospector);
            }
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
    /** Getter for the attribute selectValue.
     * @return Value of attribute selectValue.
     */
    public String getSelectValue() {
        return m_selectValue;
    }
    /** Setter for the attribute selectValue.
     * @param value New value of attribute selectValue.
     */
    public void setSelectValue(String value) {
        m_selectValue = value;
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
    
    private String getHtml(String idPrefix, SimpleWidgetModel model, IPropertyRuleIntrospector propertyRuleIntrospector) {
        Object widgetValueObject = model.getWidgetValue();
        String widgetValue = widgetValueObject != null ? propertyRuleIntrospector.format(widgetValueObject) : model.getStringValue();
        if (widgetValue == null)
            widgetValue = "";
        if (m_displayOnly) {
            return "<img valign='bottom' align='absbottom' id='" + idPrefix + "_img' border='0' src='" + (getSelectValue().equals(widgetValue) ? DEFAULT_DISPLAY_ONLY_IMAGE_ON : DEFAULT_DISPLAY_ONLY_IMAGE_OFF) + "'>";
        } else {
            StringBuffer sb = new StringBuffer();
            String btnName = "";
            sb.append("<input id=\"" + idPrefix + "\" ");
            if (!TagHelper.isEnclosed(pageContext)) {
                btnName = getField() + "WV";
                sb.append("name=\"" + btnName +"\" ");
                // A RadioGroup needs a name as that is how they are linked, but if it is enclosed on the table
                // we don't want the values posed back, as the data is encoded in the enclosing widget.
            } else {
                btnName = idPrefix;
                sb.append("name=\"" + btnName + "\" ");
            }
            sb.append("type=\"Radio\" ");
            sb.append("class=\"WidgetRadioButton\" ");
            sb.append("value=\"" + getSelectValue() + "\"" +
                    (getSelectValue().equals(widgetValue) ? " checked" : "") + ">\n");
            sb.append(getWidgetRegistrationScript(btnName, false));
            return sb.toString();
        }
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
    /** A convenience method which returns the javascript for registering the Tag with the global widgetCache, and with the RulesEngine.
     *  This method overwrites what is in CustomModelTag to handle group widgets.
     * @param parentId The id for this Tag's parent.
     * @param id The id for this tag.
     * @param registerWithRulesEngine If true, this tag will be registered with the javascript-RulesEngine.
     * @return the javascript for registering this tag.
     */
    protected String getWidgetRegistrationScript(String parentId, String name, boolean registerWithRulesEngine) {
        String parentNodeIdScript = parentId != null ? "document.getElementById('" + parentId + "')" : "null";
        StringBuffer buf = new StringBuffer("<script>var tmp = document.getElementsByName('")
        .append(name)
        .append("');")
        .append("tmp=tmp[tmp.length-1];");
        if (registerWithRulesEngine)
            buf.append("validationRules.addField(tmp);");
        buf.append("registerGroupWidget(tmp.form, ")
        .append(parentNodeIdScript)
        .append(", tmp);</script>\n");
        return buf.toString();
    }
}
