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

/*
 * @todo widget-within-widget?
 * @todo Caching?
 * @todo How is rules-serverValidation being implemented in javascript - can see related code?
 */

package org.jaffa.presentation.portlet.widgets.taglib;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Parser;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.InvalidParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.LocaleHelper;
import org.jaffa.util.StringHelper;

/** Tag Handler for the EditBox widget.*/
public class EditBoxTag extends CustomModelTag implements IWidgetTag,IFormTag {
    
    public static final String PLAIN = "plain";
    public static final String FIFO = "fifo";
    public static final String LIFO = "lifo";
    
    private static Logger log = Logger.getLogger(EditBoxTag.class);
    private static final String TAG_NAME = "EditBoxTag";
    private static String c_footer = null;
    static {
        StringBuffer sb = new StringBuffer();
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/editbox.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/AnchorPosition.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/buildDiv.js\"></SCRIPT>\n");
        c_footer = sb.toString();
    }
    private static final byte DEFAULT_COLUMNS = 50;
    private static final byte DEFAULT_COLUMN_ADJUSTMENT = 2;
    private static final byte DEFAULT_COLUMN_MULTIPLIER = 10;
    
    // Define The Events
    /** The validate event.*/
    public static final String EVENT_VALIDATE = "Validate";
    
    /** Th name of the rule used for determining the 'wrap' attribute for a textarea tag.
     * The 'wrap' attribute will not be generated if the this rule is absent or if it has no value (default)
     * If present, it can have the following values
     *   off:  Turns off word-wrapping.
     *   soft: Turns on word-wrapping. The browser does not add any newlines to the data before posting it.
     *   hard: Turns on word-wrapping. The browser adds newlines to the data before posting it.
     *
     * NOTE: The width of a textarea is determined by its 'cols' attribute, and can be overridden through a style definition.
     * The point at which newlines are inserted is browser specific.
     * In IE, it is determined by the width of the TextArea.
     * In Mozilla, it is determined by the 'cols' attribute.
     * Hence, if using hard wrapping, it is important to specify an appropriate 'cols' attribute in Mozilla, even if the visible width is being controlled through style definitions
     */
    public static final String RULE_TEXTAREA_WRAP = "jaffa.widgets.textarea.wrap";
    
    /** Th name of the rule used for determining the default 'cols' attribute for a textarea tag.
     * The columns attribute for the EditBox widget determines the 'cols' attribute of a textarea tag.
     * If the columns attribute is not specified, this class uses the rule to determine the default 'cols' for the textarea.
     * If the rule is not specified, it sets the cols to 50
     */
    public static final String RULE_TEXTAREA_DEFAULT_COLUMNS = "jaffa.widgets.textarea.defaultColumns";
    
    /** property declaration for tag attribute: columns.
     */
    private int m_columns;
    
    /** property declaration for tag attribute: password.
     */
    private boolean m_password;
    
    /** property declaration for tag attribute: rows.
     */
    protected int m_rows;
    
    /** property declaration for tag attribute: fontname.
     */
    private String m_fontname;
    
    /** property declaration for tag attribute: fontsize.
     */
    private String m_fontsize;
    
    /** property declaration for tag attribute: italics.
     */
    private boolean m_italics;
    
    /** property declaration for tag attribute: bold.
     */
    private boolean m_bold;
    
    /** property declaration for tag attribute: width.
     */
    private String m_width;
    
    /** property declaration for tag attribute: height.
     */
    private String m_height;
    
    /** property declaration for tag attribute: lookup.
     */
    private boolean m_lookup;
    
    /** property declaration for tag attribute: validate.
     */
    private boolean m_validate;
    
    /** property declaration for tag attribute: trim.
     */
    private boolean m_trim;
    
    /** property declaration for tag attribute: autoTabTo.
     */
    private String m_autoTabTo;
    
    /** property declaration for global variable m_onValidate.
     */
    private String m_onValidate;
    
    /**
     * property declaration for tag attribute: verticalSizePercentage.
     */
    protected String m_percentageH;
    
    /**
     * property declaration for tag attribute: horizontalSizePercentage.
     */
    protected String m_percentageW;
    
    /**
     * property declaration for tag attribute: verticalAbsolutePixelSize.
     */
    protected String m_horizontalMax;
    
    /**
     * property declaration for tag attribute: verticalAbsolutePixelSize.
     */
    protected String m_verticalMax;
    
    /**
     * property declaration for tag attribute: verticalOffset.
     */
    protected String m_verticalOffset;
    
    /**
     * property declaration for tag attribute: horizontalOffset.
     */
    protected String m_horizontalOffset;
    
    /**
     * property declaration for tag attribute: ignoreDefaultColumnLimit.
     * By default, this attribute is set to false. Which would imply that the size of an EditBox will be limited to a default.
     * If this attribute is set to true, then the limit will not be applied.
     */
    private boolean m_ignoreDefaultColumnLimit;

    /** This is an internal flag to denote if the trim attribute was passed in the tag declaration. This allows us to set the default value for the trim property, without losing the user-entered value. */
    private boolean m_trimAttributePassed;
    
    public EditBoxTag() {
        super();
        initTag();
    }
    
    protected void initTag() {
        m_field = null;
        m_columns = 0;
        m_password = false;
        m_rows = 1;
        m_fontname = null;
        m_fontsize = null;
        m_italics = false;
        m_bold = false;
        m_width = null;
        m_height = null;
        m_lookup = false;
        m_validate = false;
        m_trim = true;
        m_autoTabTo = null;
        m_onValidate = null;
        
        m_percentageH = null;
        m_percentageW = null;
        m_horizontalMax = null;
        m_verticalMax = null;
        m_verticalOffset = null;
        m_horizontalOffset = null;
        m_ignoreDefaultColumnLimit = false;
        m_trimAttributePassed = false;
    }
    
    
    
    /** Returns the string containing the javascript info for the HTML header.
     * @return the string containing the javascript info for the HTML header.
     */
    public static String getHeader() {   // not used?????
        return c_footer;
    }
    
    
    public String getGroupingSymbol(Locale value) {
        DecimalFormatSymbols decimalSystem = new  DecimalFormatSymbols(value);
        return Character.toString(decimalSystem.getGroupingSeparator());
    }
    
    
    public char getDecimalSymbol(Locale value) {
        DecimalFormatSymbols decimalSystem = new  DecimalFormatSymbols(value);
        return decimalSystem.getDecimalSeparator();
    }
    
    
    /**
     * This generates the HTML for the tag.
     */
    public void otherDoStartTagOperations() throws JspException {
        
        log.debug(this.NAME+".otherDoStartTagOperations()...");
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error(this.NAME+".otherDoStartTagOperations(): error="+e);
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
            
            if(log.isDebugEnabled())
                log.debug(this.NAME + " [field=" + getField() + "] PropertyRuleIntrospector is " + propertyRuleIntrospector);
            
        } catch (JspException e) {
            log.error("EditBoxTag.otherDoStartTagOperations(): error="+e);
        }
        
        if (propertyRuleIntrospector.isHidden()) {
            // Display the (Restricted) text for a hidden field
            html = TagHelper.getTextForHiddenField(pageContext);
        } else {
            if (model != null) {
                if (propertyRuleIntrospector.isReadOnly()) {
                    if (!m_password) {
                        // Just display the text for a readOnly field
                        html = StringHelper.convertToHTML(propertyRuleIntrospector.format(model.getWidgetValue()));
                        html = convertTextToHtml(html, getRows() > 1);
                    }
                } else {
                    String formName = TagHelper.getFormTag(pageContext).getHtmlName();
                    String idPrefix = getHtmlIdPrefix();
                    String eventPrefix = getJaffaEventNamePrefix();
                    String classPrefix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatoryPrefix\">&nbsp;</span>" : "<span class=\"editboxOptionalPrefix\">&nbsp;</span>";
                    String classSuffix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatorySuffix\">&nbsp;</span>" : "<span class=\"editboxOptionalSuffix\">&nbsp;</span>";
                    
                    // Set the layout on the model from the introspector
                    model.setLayout(propertyRuleIntrospector.getLayout());
                    
                    // Generate the HTML
                    try {
                        //@TODO - need to use the getHtmlTextArea regardless of rows, if this is a LIFO/FIFO comment style field
                        String commentStyle = propertyRuleIntrospector.getCommentStyle();
                        if (PLAIN.equals(commentStyle)) commentStyle = null;
                        
                        // Default the trim attribute to 'false' for textarea and password fields. Default to 'true' for all others
                        if (!m_trimAttributePassed)
                          m_trim = getRows() > 1 || commentStyle != null || m_password ? false : true;
                        
                        if( getRows() > 1 || commentStyle != null ) {
                            // retrieve the original text from the orignal model. Create one if the orignal model does not exist when LIFO/FIFO.
                            String originalText = null;
                            if(LIFO.equals(commentStyle) || FIFO.equals(commentStyle) ) {
                                FormBase formObject = TagHelper.getFormBase(pageContext);
                                SimpleWidgetModel origWM = (SimpleWidgetModel) formObject.getWidgetCache().getModel(getField()+"_original");
                                if(origWM==null) {
                                    // this is the first time this comment tag has been executed
                                    formObject.getWidgetCache().addModel(getField()+"_original",new SimpleWidgetModel(model) );
                                    originalText = model.getStringValue();
                                    model.setWidgetValue(null);
                                } else {
                                    // the original text was cached
                                    originalText = origWM.getStringValue();
                                }
                            }
                            
                            // create the html string
                            StringBuffer sb = new StringBuffer();
                            if(FIFO.equals(commentStyle) ) {
                                if (originalText != null)
                                    sb.append(convertTextToHtml(originalText, true)).append("<br/>");
                            }
                            sb.append( classPrefix )
                            .append( getHtmlTextArea(idPrefix, eventPrefix, formName, model, propertyRuleIntrospector) )
                            .append( classSuffix );
                            if(LIFO.equals(commentStyle) ) {
                                if (originalText != null)
                                    sb.append("<br/>").append(convertTextToHtml(originalText, true));
                            }
                            html = sb.toString();
                        } else {
                            html = classPrefix + getHtml(idPrefix, eventPrefix, formName, model, propertyRuleIntrospector) + classSuffix;
                        }
                    } catch (JspException e) {
                        log.error("EditBox.otherDoStartTagOperations(): error in getHtml call: " + e);
                    }
                }
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
    
    /** Convert text to html, optionally convert new lines to <BR> */
    private String convertTextToHtml(String html, boolean replaceNewLines) {
        if (replaceNewLines) {
            html = StringHelper.replace(html, "\r\n", "<BR/>");
            html = StringHelper.replace(html, "\n\r", "<BR/>");
            html = StringHelper.replace(html, "\r", "<BR/>");
            html = StringHelper.replace(html, "\n", "<BR/>");
        }
        return html;
    }
    
    
    /** Getter for property width.
     * @return Value of property width.
     */
    public int getMetaWidth(Integer intSize , Integer fracSize, boolean suppressNegative) {
        int i = 0;
        if ( intSize != null ) {
            i = intSize.intValue();
            //group separator
            i += i/3;
            //negative sign
            if (!suppressNegative)
                i += 1;
        }
        if ( fracSize != null ) {
            i += fracSize.intValue();
            //decimal separator
            i += 1;
        }
        if ( i == 0 )
            i = 20;
        return i;
    }
    
    
    /** Getter for the attribute columns.
     * @return Value of attribute columns.
     */
    public int getColumns() {
        return m_columns;
    }
    
    /** Setter for the attribute columns.
     * @param value New value of attribute columns.
     */
    public void setColumns(int value) {
        m_columns = value;
    }
    
    /** Getter for the attribute password.
     * @return Value of attribute password.
     */
    public boolean getPassword() {
        return m_password;
    }
    
    /** Setter for the attribute password.
     * @param value New value of attribute password.
     */
    public void setPassword(boolean value) {
        m_password = value;
    }
    
    /** Getter for the attribute rows.
     * @return Value of attribute rows.
     */
    public int getRows() {
        return m_rows;
    }
    
    /** Setter for the attribute rows.
     * @param value New value of attribute rows.
     */
    public void setRows(int value) {
        m_rows = value;
    }
    
    /** Getter for the attribute autoTabTo.
     * @return Value of attribute autoTabTo.
     */
    public String getAutoTabTo() {
        return m_autoTabTo;
    }
    
    /** Setter for the attribute autoTabTo.
     * @param value New value of attribute autoTabTo.
     */
    public void setAutoTabTo(String value) {
        m_autoTabTo = value;
    }
    
    /** Getter for the attribute fontname.
     * @return Value of attribute fontname.
     */
    public String getFontname() {
        return m_fontname;
    }
    
    /** Setter for the attribute fontname.
     * @param value New value of attribute fontname.
     */
    public void setFontname(String value) {
        m_fontname = value;
    }
    
    /** Getter for the attribute fontsize.
     * @return Value of attribute fontsize.
     */
    public String getFontsize() {
        return m_fontsize;
    }
    
    /** Setter for the attribute fontsize.
     * @param value New value of attribute fontsize.
     */
    public void setFontsize(String value) {
        m_fontsize = value;
    }
    
    /** Getter for the attribute italics.
     * @return Value of attribute italics.
     */
    public boolean getItalics() {
        return m_italics;
    }
    
    /** Setter for the attribute italics.
     * @param value New value of attribute italics.
     */
    public void setItalics(boolean value) {
        m_italics = value;
    }
    
    /** Getter for the attribute bold.
     * @return Value of attribute bold.
     */
    public boolean getBold() {
        return m_bold;
    }
    
    /** Setter for the attribute bold.
     * @param value New value of attribute bold.
     */
    public void setBold(boolean value) {
        m_bold = value;
    }
    
    /** Getter for the attribute width.
     * @return Value of attribute width.
     */
    public String getWidth() {
        return m_width;
    }
    
    /** Setter for the attribute width.
     * @param value New value of attribute width.
     */
    public void setWidth(String value) {
        m_width = value;
    }
    
    /** Getter for the attribute height.
     * @return Value of attribute height.
     */
    public String getHeight() {
        return m_height;
    }
    
    /** Setter for the attribute height.
     * @param value New value of attribute height.
     */
    public void setHeight(String value) {
        m_height = value;
    }
    
    /** Getter for the attribute lookup.
     * @return Value of attribute lookup.
     */
    public boolean getLookup() {
        return m_lookup;
    }
    
    /** Setter for the attribute lookup.
     * @param value New value of attribute lookup.
     */
    public void setLookup(boolean value) {
        m_lookup = value;
    }
    
    /** Getter for the attribute validate.
     * @return Value of attribute validate.
     */
    public boolean getValidate() {
        return m_validate;
    }
    
    /** Setter for the attribute validate.
     * @param value New value of attribute validate.
     */
    public void setValidate(boolean value) {
        m_validate = value;
    }
    
    /** Getter for the attribute trim.
     * @return Value of attribute trim.
     */
    public boolean getTrim() {
        return m_trim;
    }
    
    /** Setter for the attribute trim.
     * @param value New value of attribute trim.
     */
    public void setTrim(boolean value) {
        m_trim = value;
        m_trimAttributePassed = true;
    }
    
    /** Getter for the attribute script.
     * @return Value of attribute script.
     */
    public String getOnValidate() {
        return m_onValidate;
    }
    
    /** Setter for the attribute sctip.
     * @param value New value of attribute script.
     */
    public void setOnValidate(String value) {
        m_onValidate = value;
    }
    
    public void setVerticalSizePercentage(String value) {
        m_percentageH = value;
    }
    
    public void setHorizontalSizePercentage(String value) {
        m_percentageW = value;
    }
    
    public void setHorizontalAbsolutePixelSize(String value) {
        m_horizontalMax = value;
    }
    
    public void setVerticalAbsolutePixelSize(String value) {
        m_verticalMax = value;
    }
    
    public void setVerticalOffset(String value) {
        m_verticalOffset = value;
    }
    
    public void setHorizontalOffset(String value) {
        m_horizontalOffset = value;
    }
    
    public String getVerticalSizePercentage() {
        return m_percentageH;
    }
    
    public String getHorizontalSizePercentage() {
        return m_percentageW;
    }
    
    public String getHorizontalAbsolutePixelSize() {
        return m_horizontalMax;
    }
    
    public String getVerticalAbsolutePixelSize() {
        return m_verticalMax;
    }
    
    public String getVerticalOffset() {
        return m_verticalOffset;
    }
    
    public String getHorizontalOffset() {
        return m_horizontalOffset;
    }

    public boolean getIgnoreDefaultColumnLimit() {
        return m_ignoreDefaultColumnLimit;
    }

    public void setIgnoreDefaultColumnLimit(boolean ignoreDefaultColumnLimit) {
        m_ignoreDefaultColumnLimit = ignoreDefaultColumnLimit;
    }



    protected String getHtml(String idPrefix, String eventPrefix, String form,
            SimpleWidgetModel model, IPropertyRuleIntrospector propertyRuleIntrospector) throws JspException {
        Object widgetValue = model.getWidgetValue();
        String value = widgetValue != null ? propertyRuleIntrospector.format(widgetValue) : model.getStringValue();
        StringBuffer sb = new StringBuffer();
        sb.append("<input type='");
        sb.append(m_password ? "password'" : "text'");
        if (!TagHelper.isEnclosed(pageContext)) {
            sb.append(" name='")
            .append(m_field)
            .append("WV'");
        }
        sb.append(" id='")
        .append(idPrefix)
        .append("' class='")
        .append(propertyRuleIntrospector.isMandatory() ? "WidgetMandatoryEditBox" : "WidgetEditBox")
        .append("' value=\"")
        .append(StringHelper.convertToHTML(value))
        .append("\" size='")
        .append(determineColumnSize(propertyRuleIntrospector))
        .append("' maxlength='")
        .append(propertyRuleIntrospector.getMaxLength() != null ? "" + determineMaxLen(propertyRuleIntrospector) : "")
        .append("'")
        .append(getStyle(propertyRuleIntrospector.getCaseType()) )
        .append(" onBlur=\"validationRules.validateField(this);")
        .append(getServerSideValidate(idPrefix, eventPrefix, form, model))
        .append("\" ")
        .append(" onChange=\"fieldChange(this);\" beenChanged='true' validated='false'")
        .append(getValidateAttribute(idPrefix, eventPrefix, form, model, propertyRuleIntrospector));
        if (getAutoTabTo() != null)
            sb.append(" onkeypress=\"changeField('")
            .append(pageContext.findAttribute(TagHelper.ATTRIBUTE_ID_PREFIX))
            .append("_")
            .append(getAutoTabTo())
            .append("',this)\"");
        sb.append(">\n")
        .append(getWidgetRegistrationScript(idPrefix, true));
        return sb.toString();
        
    }
    
    /** Generate a TextArea box because there are more that one rows of input, or this is a comment widget.
     *
     */
    protected String getHtmlTextArea(String idPrefix, String eventPrefix, String form,
            SimpleWidgetModel model, IPropertyRuleIntrospector propertyRuleIntrospector) throws JspException {
        
        Object widgetValue = model.getWidgetValue();
        String value = widgetValue != null ? propertyRuleIntrospector.format(widgetValue) : model.getStringValue();
        StringBuffer sb = new StringBuffer();
        sb.append("<textarea ");
        if (!TagHelper.isEnclosed(pageContext)) {
            sb.append(" name='" + m_field + "WV" + "'");
        }
        sb.append(" id='")
        .append(idPrefix)
        .append("' class='")
        .append((propertyRuleIntrospector.isMandatory() ? "WidgetEditBox" : "WidgetMandatoryEditBox"))
        .append("' cols='")
        .append(determineColumnSize(propertyRuleIntrospector))
        .append("' rows='")
        .append(m_rows)
        .append("'")
        .append(generateTextAreaWrapAttribute())
        .append( getStyle(propertyRuleIntrospector.getCaseType()) )
        .append(" onBlur=\"javascript:validationRules.validateField(this)")
        .append(getServerSideValidate(idPrefix, eventPrefix, form, model))
        .append("\" onChange=\"fieldChange(this);\" beenChanged=\"true\" validated=\"false\"")
        .append(getValidateAttribute(idPrefix, eventPrefix, form, model, propertyRuleIntrospector))
        .append(">\n")
        .append( StringHelper.convertToHTML(value))
        .append("</textarea>\n")
        .append(getWidgetRegistrationScript(idPrefix, true));
        if (m_percentageH != null || m_percentageW != null ) {
            sb.append("<script>resizeTextArea(document.getElementById('" + idPrefix + "') , " + m_percentageW + " , " + m_percentageH + " , " + m_horizontalOffset + " , " + m_verticalOffset + " , " + m_horizontalMax + " , " + m_verticalMax + ");</script>" );
        }
        
        return sb.toString();
    }
    
    protected String generateTextAreaWrapAttribute() {
        String textareaWrap = (String) ContextManagerFactory.instance().getProperty(RULE_TEXTAREA_WRAP);
        if (textareaWrap != null && textareaWrap.length() > 0) {
            return new StringBuffer(" wrap='").append(textareaWrap).append('\'').toString();
        }
        return "";
    }
    
    protected String getStyle(String caseType) {
        StringBuffer sb = new StringBuffer();
        sb.append(" STYLE=\"");
        if(m_bold)
            sb.append(" font-weight: bold;");
        if(m_width != null)
            sb.append(" width: " + m_width + ";");
        if(m_height != null)
            sb.append(" height: " + m_height + ";");
        if(m_fontsize != null)
            sb.append(" font-size: " + m_fontsize + ";");
        if(m_italics)
            sb.append(" font-style: italics;");
        if(m_fontname != null) {
            sb.append(" font-family: ").append(m_fontname).append(", Arial, Helvetica, sans-serif;");
        }
        //        if(m_color != null)
        //            sb.append(" color: " + m_color + ";");
        if ( FieldMetaData.UPPER_CASE.equals(caseType) )
            sb.append(" text-transform: uppercase;");
        else if ( FieldMetaData.LOWER_CASE.equals(caseType) )
            sb.append(" text-transform: lowercase;");
        sb.append("\"");
        return sb.toString();
    }
    
    protected String getValidateAttribute(String idPrefix, String eventPrefix, String form, SimpleWidgetModel model, IPropertyRuleIntrospector propertyRuleIntrospector) {
        Class propertyType = propertyRuleIntrospector.getPropertyType();
        StringBuffer sb = new StringBuffer();
        if(propertyRuleIntrospector.isMandatory()) {
            sb.append(" rules-mandatory=\"\"");
        }
        
        if (propertyType == String.class) {
            sb.append(" rules-datatype=\"String\"");
            if (propertyRuleIntrospector.getMaxLength() != null) {
                sb.append(" rules-datatype-maxSize=\"");
                sb.append(propertyRuleIntrospector.getMaxLength());
                sb.append("\"");
            }
            if (propertyRuleIntrospector.getMinLength() != null) {
                sb.append(" rules-datatype-minSize=\"");
                sb.append(propertyRuleIntrospector.getMinLength());
                sb.append("\"");
            }
            if (propertyRuleIntrospector.getPattern() != null && propertyRuleIntrospector.getPattern().length > 0) {
                sb.append(" rules-datatype-layout=\"");
                sb.append(propertyRuleIntrospector.getPattern()[0]);
                sb.append("\" ");
            }
        } else if (propertyType == Float.class || propertyType == Float.TYPE
                || propertyType == Double.class || propertyType == Double.TYPE
                || propertyType == BigDecimal.class) {
            sb.append(" rules-datatype=\"Decimal\"");
            if (propertyRuleIntrospector.getMaxLength() != null) {
                if (propertyRuleIntrospector.getMaxFracLength() != null) {
                    sb.append(" rules-datatype-maxSize=\"");
                    sb.append((propertyRuleIntrospector.getMaxLength().intValue() + propertyRuleIntrospector.getMaxFracLength().intValue()));
                    sb.append("\" ");
                } else {
                    sb.append(" rules-datatype-maxSize=\"");
                    sb.append((propertyRuleIntrospector.getMaxLength().intValue()));
                    sb.append("\" ");
                }
            }
            if (propertyRuleIntrospector.getMaxFracLength() != null) {
                sb.append(" rules-datatype-maxDigits=\"");
                sb.append(propertyRuleIntrospector.getMaxFracLength());
                sb.append("\" ");
            }
            if (propertyRuleIntrospector.getMinValue() != null) {
                sb.append(" rules-datatype-minValue=\"");
                sb.append(propertyRuleIntrospector.getMinValue());
                sb.append("\" ");
            }
            if (propertyRuleIntrospector.getMaxValue() != null)
                sb.append(" rules-datatype-maxValue=\"" + propertyRuleIntrospector.getMaxValue() + "\" ");
        } else if (Number.class.isAssignableFrom(propertyType)) {
            sb.append(" rules-datatype=\"Integer\"");
            if (propertyRuleIntrospector.getMaxLength() != null)
                sb.append(" rules-datatype-maxSize=\"" + propertyRuleIntrospector.getMaxLength() + "\" ");
            if (propertyRuleIntrospector.getMinValue() != null)
                sb.append(" rules-datatype-minValue=\"" + propertyRuleIntrospector.getMinValue()+ "\" ");
            if (propertyRuleIntrospector.getMaxValue() != null)
                sb.append(" rules-datatype-maxValue=\"" + propertyRuleIntrospector.getMaxValue()+ "\" ");
        } else if (propertyType == Currency.class) {
            sb.append(" rules-datatype=\"Decimal\"");
            if (propertyRuleIntrospector.getMaxLength() != null) {
                if (propertyRuleIntrospector.getMaxFracLength() != null) {
                    sb.append(" rules-datatype-maxSize=\"" + (propertyRuleIntrospector.getMaxLength().intValue() + propertyRuleIntrospector.getMaxFracLength().intValue()) +"\" ");
                } else {
                    sb.append(" rules-datatype-maxSize=\"" + (propertyRuleIntrospector.getMaxLength().intValue()) +"\" ");
                }
            }
            if (propertyRuleIntrospector.getMaxFracLength() != null)
                sb.append(" rules-datatype-maxDigits=\"" + propertyRuleIntrospector.getMaxFracLength() + "\" ");
            if (propertyRuleIntrospector.getMinValue() != null)
                sb.append(" rules-datatype-minValue=\"" + ((Currency) propertyRuleIntrospector.getMinValue()).getValue()+ "\" ");
            if (propertyRuleIntrospector.getMaxValue() != null)
                sb.append(" rules-datatype-maxValue=\"" + ((Currency) propertyRuleIntrospector.getMaxValue()).getValue()+ "\" ");
        } else if (propertyType == DateOnly.class) {
            log.debug("EditBox is a DateOnly");
            sb.append(" rules-datatype=\"DateOnly\"");
            if (propertyRuleIntrospector.getLayout() != null)
                sb.append(" rules-datatype-layout=\"" + LocaleHelper.determineLayout(propertyRuleIntrospector.getLayout()) + "\" ");
        } else if (propertyType == DateTime.class) {
            log.debug("EditBox is a DateTime");
            sb.append(" rules-datatype=\"DateTime\"");
            if (propertyRuleIntrospector.getLayout() != null)
                sb.append(" rules-datatype-layout=\"" + LocaleHelper.determineLayout(propertyRuleIntrospector.getLayout()) + "\" ");
        } else if (Date.class.isAssignableFrom(propertyType)) {
            log.debug("EditBox is a java.util.Date");
            sb.append(" rules-datatype=\"DateTime\"");
            if (propertyRuleIntrospector.getLayout() != null)
                sb.append(" rules-datatype-layout=\"" + LocaleHelper.determineLayout(propertyRuleIntrospector.getLayout()) + "\" ");
        }
        
        if (getOnValidate() != null) {
            if (getOnValidate().indexOf("\"") != -1) {
                String str = "You Cannot pass the literal chatacter '\"' in the code block";
                log.error(str);
                throw new InvalidParametersRuntimeException(str);
            } else {
                sb.append(" rules-serverValidation=\"" + getOnValidate() + "\" ");
            }
        }
        
        if (getTrim())
            sb.append(" rules-trim=\"\"");
        log.debug("EditBox Rules : " + sb.toString());
        return sb.toString();
    }
    
    protected String getServerSideValidate(String idPrefix, String eventPrefix, String form, SimpleWidgetModel model) {
        StringBuffer sb = new StringBuffer();
        
        
        // See if this has a remote callback...
        // JavaScript...
        //      document.page1.eventId.value = 'table1=1:acol:qty:VALIDATE';
        //      postForm(document.page1, f);
        
        //        if(model.hasEventListener(EditBoxModel.EVENT_VALIDATE)) {
        if (m_validate) {
            sb.append("    document." + form + ".eventId.value = '" + eventPrefix + ';' + EVENT_VALIDATE + "';\n");
            sb.append("    postForm(document." + form + ", this);\n");
        }
        
        return sb.toString();
    }
    
    private String getValidateCall(String idPrefix, String caseType) {
        //return idPrefix + "_validate(this);";
        StringBuffer buf = new StringBuffer();
        if (getTrim())
            buf.append("editBoxTrim(this);");
        buf.append(idPrefix + "_validate(this,'');");
        return buf.toString();
    }
    
    protected int determineColumnSize(IPropertyRuleIntrospector propertyRuleIntrospector) {
        int columns;
        if (getColumns() > 0) {
            // The columns attribute will override all other logic
            columns = getColumns();
        } else if (getRows() > 1) {
            // Determine the Default for a TextArea
            String s = (String) ContextManagerFactory.instance().getProperty(RULE_TEXTAREA_DEFAULT_COLUMNS);
            columns = s != null && s.length() > 0 ? Integer.parseInt(s) : DEFAULT_COLUMNS;
        } else {
            // Add an adjustment to the maxLen
            columns = (propertyRuleIntrospector != null && propertyRuleIntrospector.getMaxLength() != null ? propertyRuleIntrospector.getMaxLength().intValue() : DEFAULT_COLUMNS )+ DEFAULT_COLUMN_ADJUSTMENT;
            // Now round it to the nearest 10
            byte mod = (byte) (columns % DEFAULT_COLUMN_MULTIPLIER);
            if (mod > 0)
                columns = columns - mod + DEFAULT_COLUMN_MULTIPLIER;
            
            // Ensure that the default isn't exceeded
            if (columns > DEFAULT_COLUMNS && !getIgnoreDefaultColumnLimit())
                columns = DEFAULT_COLUMNS;
        }
        return columns;
        
    }
    
    private int determineMaxLen(IPropertyRuleIntrospector propertyRuleIntrospector) {
        int max = 50;
        if (propertyRuleIntrospector != null) {
            Class propertyType = propertyRuleIntrospector.getPropertyType();
            if (propertyType != null &&
                (Number.class.isAssignableFrom(propertyType) || Currency.class.isAssignableFrom(propertyType) ||
                 (propertyType.isPrimitive() && propertyType != Boolean.TYPE))) {
                if (propertyRuleIntrospector.getMaxLength() != null) {
                    boolean suppressNegative = false;
                    Object minValueObj = propertyRuleIntrospector.getMinValue();
                    if (minValueObj != null) {
                        try {
                            Double minValue = Parser.parseDecimal(minValueObj.toString());
                            suppressNegative = minValue != null && minValue >= 0;
                        } catch (Exception e) {
                            if (log.isDebugEnabled())
                                log.debug("Exception in trying to parse the minValue '" + minValueObj +
                                          "', while deciding if the maxLength should allow the minus sign", e);
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Compute maxlength allowing for the minus sign? " + !suppressNegative);
                    }
                    max = getMetaWidth(propertyRuleIntrospector.getMaxLength(),
                                       propertyRuleIntrospector.getMaxFracLength(), suppressNegative);
                }
            } else {
                max = propertyRuleIntrospector.getMaxLength();
            }
        }
        return max;
    }
    
    /** Checks for the nearest outer PropertyTag.
     * Will set the 'field' on this tag, if not specifed, with the value from the outer PropertyTag.
     * @return the IPropertyRuleIntrospector from the outer PropertyTag.
     */
    protected IPropertyRuleIntrospector lookupPropertyTag() {
        PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
        if (propertyTag != null) {
            if (getField() == null)
                setField(propertyTag.getField());
            return propertyTag.getPropertyRuleIntrospector();
        } else
            return null;
    }
    
    public String getFooterHtml() {
        return c_footer;
    }
    
    public void doFinally() {
        super.doFinally();
        initTag();
    }
    
}
