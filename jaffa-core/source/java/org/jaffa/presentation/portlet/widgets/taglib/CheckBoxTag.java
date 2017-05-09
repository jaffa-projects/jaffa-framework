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
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.MessageHelper;

/** Tag Handler for the CheckBox widget.
 * @todo - add a 'label' attribute that would add a 'title' attribute to the HTML checkbox for floating hints
 */
public class CheckBoxTag extends CustomModelTag implements IWidgetTag,IFormTag {
    
    private static Logger log = Logger.getLogger(CheckBoxTag.class);
    private static final String TAG_NAME = "CheckBoxTag";
    private static String c_header = "<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/checkbox.js\"></SCRIPT>";
    private static String DEFAULT_IMAGE_ON = "jaffa/imgs/checkbox/check_box.gif";
    private static String DEFAULT_IMAGE_OFF = "jaffa/imgs/checkbox/box.gif";
    private static String DEFAULT_DISPLAY_ONLY_IMAGE_ON = "jaffa/imgs/checkbox/check.gif";
    private static String DEFAULT_DISPLAY_ONLY_IMAGE_OFF = "jaffa/imgs/checkbox/false.gif";
    /** property declaration for tag attribute: field.
     */
    //private String m_field;   // defined now in CustomModelTag super class
    
    /** property declaration for tag attribute: type.
     */
    private String m_type;
    
    /** property declaration for tag attribute: imageOn.
     */
    private String m_imageOn;
    
    /** property declaration for tag attribute: imageOff.
     */
    private String m_imageOff;
    
    /** property declaration for tag attribute: displayOnly.
     */
    private boolean m_displayOnly;
    
    /** property declaration for tag attribute: tooltip.
     */
    private String m_tooltip;
    
    /** used internally
     */
    private String tooltipLabel;
    
    
    /** Default constructor.
     */
    public CheckBoxTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_type = null;
        m_imageOn = null;
        m_imageOff = null;
        m_displayOnly = false;
        m_tooltip = null;
        tooltipLabel = null;
    }
    
    
    /** Returns the string containing the javascript info for the HTML header.
     * @return the string containing the javascript info for the HTML header.
     */
    public static String getHeader() {
        return c_header;
    }
    
    /**
     * This generates the HTML for the tag.
     */
    public void otherDoStartTagOperations() {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error("CheckBoxTag.otherDoStartTagOperations() error="+e);
        }
        
        
        // Preprocess if within a Property widget
        IPropertyRuleIntrospector propertyRuleIntrospector = lookupPropertyTag();
        
        // raise an error, if the 'field' is null
        if (getField() == null) {
            String str = "The " + TAG_NAME + " requires 'field' parameter to be supplied or it should be within a PropertyTag";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
        
        // Get the formtag from the page & register the widget
        FormTag formTag = TagHelper.getFormTag(pageContext);
        if(formTag == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
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
        try {
            if (propertyRuleIntrospector == null)
                propertyRuleIntrospector = TagHelper.getPropertyRuleIntrospector(pageContext, getField());
            
            // Wrap the propertyRuleIntrospector
            propertyRuleIntrospector = TagHelper.wrapPropertyRuleIntrospector(propertyRuleIntrospector, model);
        } catch (JspException e) {
            log.error("CheckBoxTag.otherDoStartTagOperations(): error property inspector: " + e);
        }
        
        if (propertyRuleIntrospector.isHidden()) {
            // Display the (Restricted) text for a hidden field
            html = TagHelper.getTextForHiddenField(pageContext);
        } else {
            if (model != null) {
                if (propertyRuleIntrospector.isReadOnly()) {
                    // Render a readOnly field as a displayOnly checkbox
                    m_displayOnly = true;
                    if (m_type != null && m_type.equalsIgnoreCase("HTML"))
                        m_type = "IMAGE";
                }
                html = getHtml(getHtmlIdPrefix(), model);
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
    
    
    /** Getter for the attribute type.
     * @return Value of attribute type.
     */
    public String getType() {
        return m_type;
    }
    
    /** Setter for the attribute type.
     * @param value New value of attribute type.
     */
    public void setType(String value) {
        m_type = value;
    }
    
    /** Getter for the attribute imageOn.
     * @return Value of attribute imageOn.
     */
    public String getImageOn() {
        return m_imageOn;
    }
    
    /** Setter for the attribute imageOn.
     * @param value New value of attribute imageOn.
     */
    public void setImageOn(String value) {
        m_imageOn = value;
    }
    
    /** Getter for the attribute imageOff.
     * @return Value of attribute imageOff.
     */
    public String getImageOff() {
        return m_imageOff;
    }
    
    /** Setter for the attribute imageOff.
     * @param value New value of attribute imageOff.
     */
    public void setImageOff(String value) {
        m_imageOff = value;
    }
    
    
    private String getHtml(String idPrefix, SimpleWidgetModel model) {
        String html = null;
        boolean widgetValue = determineWidgetValue(model);
        if (m_type == null) {
            if (m_displayOnly)
                html = getDisplayOnlyCheckBox(idPrefix, widgetValue);
            else
                html = getHtmlCheckBox(idPrefix, widgetValue);
            
        } else if (m_type.equalsIgnoreCase("HTML")) {
            if (m_displayOnly) {
                String str = "The checkbox widget does not support display only HTML";
                log.error(str);
                throw new UnsupportedOperationException(str);
            } else
                html = getHtmlCheckBox(idPrefix, widgetValue);
            
        } else if (m_type.equalsIgnoreCase("IMAGE")) {
            if (m_displayOnly)
                html = getDisplayOnlyCheckBox(idPrefix, widgetValue);
            else
                html = getImageCheckBox(idPrefix, widgetValue);
            
        } else if (m_type.equalsIgnoreCase("CUSTOM")) {
            if (m_displayOnly)
                html = getDisplayOnlyCustomCheckBox(idPrefix, widgetValue);
            else
                html = getCustomCheckBox(idPrefix, widgetValue);
            
        } else {
            String str = "The checkbox widget does not support the type " + m_type;
            log.error(str);
            throw new UnsupportedOperationException(str);
        }
        
        return html;
    }
    
    private boolean determineWidgetValue(SimpleWidgetModel model) {
        Boolean widgetValue = model.getBooleanValue();
        return widgetValue != null ? widgetValue.booleanValue() : false;
    }
    
    private String getImageCheckBox(String idPrefix, boolean widgetValue) {
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"javascript:checkBox_toggle('" + idPrefix + "' , '"+ idPrefix + "_img' , '" + DEFAULT_IMAGE_ON + "' , '" + DEFAULT_IMAGE_OFF + "' );\">\n");
        sb.append("<img valign=\"bottom\" align=\"absbottom\" id=\"" + idPrefix + "_img\" border=\"0\" src=\"" + (widgetValue ? DEFAULT_IMAGE_ON : DEFAULT_IMAGE_OFF) + "\"");
        if (tooltipLabel()!=null && tooltipLabel().length()>0) {
          sb.append(" title="+"'"+tooltipLabel()+"'");
        }
        sb.append(">\n");
        sb.append("</a>");
        sb.append("<input ID=\"" + idPrefix + "\"");
        if(!TagHelper.isEnclosed(pageContext))
            sb.append(" NAME=\"" + getField() + "WV\" ");
        sb.append(" TYPE=\"hidden\"");
        sb.append(" CLASS=\"WidgetCheckBox\"");
        sb.append(" VALUE=\"" + (widgetValue ? "true" : "false") + "\"/>\n");
        sb.append(getWidgetRegistrationScript(idPrefix, false));
        return sb.toString();
    }
    
    
    private String getCustomCheckBox(String idPrefix, boolean widgetValue) {
        String imageOn = m_imageOn == null ? DEFAULT_IMAGE_ON : m_imageOn;
        String imageOff = m_imageOff == null ? DEFAULT_IMAGE_OFF : m_imageOff;
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"javascript:checkBox_toggle('" + idPrefix + "' , '"+ idPrefix + "_img' , '" + imageOn + "' , '" + imageOff + "' );\">\n");
        sb.append("<img valign=\"bottom\" align=\"absbottom\" id=\"" + idPrefix + "_img\" border=\"0\" src=\"" + (widgetValue ? imageOn : imageOff) + "\"");
        if (tooltipLabel()!=null && tooltipLabel().length()>0) {
          sb.append(" title="+"'"+tooltipLabel()+"'");
        }
        sb.append(">\n");
        sb.append("</a>");
        sb.append("<input ID=\"" + idPrefix + "\"");
        if(!TagHelper.isEnclosed(pageContext))
            sb.append(" NAME=\"" + getField() + "WV\" ");
        sb.append(" TYPE=\"hidden\"");
        sb.append(" CLASS=\"WidgetCheckBox\"");
        sb.append(" VALUE=\"" + (widgetValue ? "true" : "false") + "\"/>\n");
        sb.append(getWidgetRegistrationScript(idPrefix, false));
        
        return sb.toString();
    }
    
    private String getDisplayOnlyCheckBox(String idPrefix, boolean widgetValue) {
        String imageOn = DEFAULT_DISPLAY_ONLY_IMAGE_ON;
        String imageOff =DEFAULT_DISPLAY_ONLY_IMAGE_OFF;
        StringBuffer sb = new StringBuffer();
        
        sb.append("<img valign=\"bottom\" align=\"absbottom\" id=\"" + idPrefix + "_img\" border=\"0\" src=\"" + (widgetValue ? imageOn : imageOff) + "\"");
        if (tooltipLabel()!=null && tooltipLabel().length()>0) {
          sb.append(" title="+"'"+tooltipLabel()+"'");
        }
        sb.append(">\n");
        
        sb.append("<input ID=\"" + idPrefix + "\"");
        if(!TagHelper.isEnclosed(pageContext))
            sb.append(" NAME=\"" + getField() + "WV\" ");
        sb.append(" TYPE=\"hidden\"");
        sb.append(" CLASS=\"WidgetCheckBox\"");
        sb.append(" VALUE=\"" + (widgetValue ? "true" : "false") + "\"/>\n");
        
        return sb.toString();
    }
    
    
    private String getDisplayOnlyCustomCheckBox(String idPrefix, boolean widgetValue) {
        String imageOn = m_imageOn == null ? DEFAULT_DISPLAY_ONLY_IMAGE_ON : m_imageOn;
        String imageOff = m_imageOff == null ? DEFAULT_DISPLAY_ONLY_IMAGE_OFF : m_imageOff;
        StringBuffer sb = new StringBuffer();
        sb.append("<img valign=\"bottom\" align=\"absbottom\" id=\"" + idPrefix + "_img\" border=\"0\" src=\"" + (widgetValue ? imageOn : imageOff) + "\"");
        if (tooltipLabel()!=null && tooltipLabel().length()>0) {
          sb.append(" title="+"'"+tooltipLabel()+"'");
        }
        sb.append(">\n");
        sb.append("<input ID=\"" + idPrefix + "\"");
        if(!TagHelper.isEnclosed(pageContext))
            sb.append(" NAME=\"" + getField() + "WV\" ");
        sb.append(" TYPE=\"hidden\"");
        sb.append(" CLASS=\"WidgetCheckBox\"");
        sb.append(" VALUE=\"" + (widgetValue ? "true" : "false") + "\"/>\n");
        
        return sb.toString();
    }
    
    
    private String getHtmlCheckBox(String idPrefix, boolean widgetValue) {
        StringBuffer sb = new StringBuffer();
        if(!TagHelper.isEnclosed(pageContext)) {
            // Create a checkbox & hidden fields. The hidden field will be kept in sync with the checkbox.
            // This is required since the browser does not post any value for a checkbox if it is unchecked.
            // The hidden field will ensure that the underlying boolean field in the Form is set to false if the user unchecks a checkbox.
            // An alternative is to always set the underlying boolean field in the Form in it's reset() method, which is quite cumbersome
            sb.append("<input id='")
            .append(idPrefix)
            .append("_user' type='checkbox' onclick='javascript:checkBox_toggle(\"")
            .append(idPrefix)
            .append("\");'")
            .append(widgetValue ? " checked" : "");
            if (tooltipLabel()!=null && tooltipLabel().length()>0) {
              sb.append(" title="+"'"+tooltipLabel()+"'");
            }
            sb.append("/>\n<input id='")
            .append(idPrefix)
            .append("' name='")
            .append(getField())
            .append("WV' type='hidden' class='WidgetCheckBox' value='")
            .append(widgetValue ? "true" : "false")
            .append("'/>\n");
        } else {
            // We are inside a Grid. The gridPack() javascript method will ensure that a false value is passed if the user unchecks a checkbox.
            // Hence no need to create a hidden field
            // NOTE: Even though the value is set to 'true', the control submits that value only if the user has selected the checkbox. Otherwise, the control submits no value.
            // If the value is not provided, then the control may submit the default value 'on'
            sb.append("<input id='")
            .append(idPrefix)
            .append("' type='checkbox'")
            .append(widgetValue ? " checked" : "");
            if (tooltipLabel()!=null && tooltipLabel().length()>0) {
              sb.append(" title="+"'"+tooltipLabel()+"'");
            }
            sb.append(" class='WidgetCheckBox' value='true'/>\n");
        }
        sb.append(getWidgetRegistrationScript(idPrefix, false));
        return sb.toString();
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
    
    /** Getter for the attribute tooltip.
     * @return Value of attribute tooltip.
     */
    public String getTooltip() {
        return m_tooltip;
    }
    
    /** Setter for the attribute displayOnly.
     * @param value New value of attribute displayOnly.
     */
    public void setTooltip(String value) {
        m_tooltip = value;
        tooltipLabel = null;
    }
    
    private String tooltipLabel() {
      if (tooltipLabel==null) {
        String key = null;
        if (m_tooltip != null) 
          key = m_tooltip;
        else return null;
        
        // get the label from the resource bundle
        if (MessageHelper.hasTokens(key)) {
            // Remove the begin/end token-markers
            String detokenizedKey = MessageHelper.removeTokenMarkers(key);

            // Check if there are more tokens left
            if (MessageHelper.hasTokens(detokenizedKey))
                tooltipLabel = MessageHelper.replaceTokens(pageContext, key);
            else
                key = detokenizedKey;
        }

        if (tooltipLabel == null) {
            tooltipLabel = MessageHelper.findMessage(pageContext, key, null);
        }
      }
      return tooltipLabel;
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
    
    
    public String getFooterHtml() {
        return c_header;
    }
    
}
