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
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.StringHelper;

/** Tag Handler for the DateTime widget.
 * @deprecated This tag is no longer supported. Instead use the combination of EditBoxTag and CalendarTag.
 */
public class DateTimeTag extends CustomModelTag implements IWidgetTag,IFormTag {
    
    private static Logger log = Logger.getLogger(DateTimeTag.class);
    private static final String TAG_NAME = "DateTimeTag";
    private static String c_header = null;
    static {
        StringBuffer sb = new StringBuffer();
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/AnchorPosition.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/CalendarPopup.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/PopupWindow.js\"></SCRIPT>\n");
        
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/CalendarSettings.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/datetime.js\"></SCRIPT>\n");
        
        sb.append("<SCRIPT language=\"JavaScript\">document.write(cal.getStyles());</SCRIPT>\n");
        sb.append("<style id=\"calendardd\">select.dd {visibility : hidden};");
        sb.append("select.WidgetDropDown {visibility : hidden}</style>");
        c_header = sb.toString();
    }
    
    private static String c_headeruk = null;
    static {
        StringBuffer sb = new StringBuffer();
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/AnchorPosition.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/CalendarPopup.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/PopupWindow.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/CalendarSettingsuk.js\"></SCRIPT>\n");
        sb.append("<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/datetimeuk.js\"></SCRIPT>\n");
        sb.append("<SCRIPT language=\"JavaScript\">document.write(cal.getStyles());</SCRIPT>\n");
        sb.append("<style id=\"calendardd\">select.dd {visibility : hidden};");
        sb.append("select.WidgetDropDown {visibility : hidden}</style>");
        c_headeruk = sb.toString();
    }
    /** If the minute interval is undefined, or not does not divide 60 exactly, this default will be used */
    private static final int DEFAULT_MINUTE_INTERVAL = 15;
    
    
    // Define The Events
    /** The validate event.*/
    public static final String EVENT_VALIDATE = "Validate";
    
    
    /** property declaration for tag attribute: field.
     */
    //private String m_field;  // now defined in super class CustomModelTag
    
    /** property declaration for tag attribute: bold.
     */
    private boolean m_bold;
    
    /** property declaration for tag attribute: columns.
     */
    private int m_columns;
    
    /** property declaration for tag attribute: minInterval.
     */
    private int m_minInterval;
    
    /** property declaration for tag attribute: validate.
     */
    private boolean m_validate;
    
    /** property declaration for tag attribute: dateOnly.
     */
    private boolean m_dateOnly;
    
    /** Default constructor.
     */
    public DateTimeTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_bold = false;
        m_columns = 20;
        m_minInterval = DEFAULT_MINUTE_INTERVAL;
        m_validate = false;
        m_dateOnly = false;
    }
    
    
    /** Returns the string containing the javascript info for the HTML header.
     * @return the string containing the javascript info for the HTML header.
     */
    public static String getHeader() {
        if (LocaleContext.getLocale().toString().equals("en_GB")) {
            return c_headeruk;
        } else {
            return c_header;
        }
    }
    
    /**
     * This generates the HTML for the tag.
     */
    public void otherDoStartTagOperations() {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error("DateTimeTag.otherDoStartTagOperations(): error="+e);
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
            log.error("DateTimeTag.otherDoStartTagOperations(): error: property editor: " + e);
        }
        
        if (propertyRuleIntrospector.isHidden()) {
            // Display the (Restricted) text for a hidden field
            html = TagHelper.getTextForHiddenField(pageContext);
        } else {
            if (model != null) {
                if (propertyRuleIntrospector.isReadOnly()) {
                    // Just display the text for a readOnly field
                    Object value = getDateOnly() ? (Object) model.getDateOnlyValue() : (Object) model.getDateTimeValue();
                    html = StringHelper.convertToHTML(Formatter.format(value));
                } else {
                    String classPrefix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatoryPrefix\">&nbsp;</span>" : "<span class=\"editboxOptionalPrefix\">&nbsp;</span>";
                    String classSuffix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatorySuffix\">&nbsp;</span>" : "<span class=\"editboxOptionalSuffix\">&nbsp;</span>";
                    html = classPrefix + getHtml(getHtmlIdPrefix(), model, propertyRuleIntrospector) + classSuffix;
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
    
    /** Getter for the attribute minInterval.
     * @return Value of attribute minInterval.
     */
    public int getMinInterval() {
        return m_minInterval;
    }
    
    /** Setter for the attribute minInterval.
     * @param value New value of attribute minInterval.
     */
    public void setMinInterval(int value) {
        m_minInterval = value;
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
    
    /** Getter for the attribute dateOnly.
     * @return Value of attribute dateOnly.
     */
    public boolean getDateOnly() {
        return m_dateOnly;
    }
    
    /** Setter for the attribute dateOnly.
     * @param value New value of attribute dateOnly.
     */
    public void setDateOnly(boolean value) {
        m_dateOnly = value;
    }
    
    
    private String getHtml(String idPrefix, SimpleWidgetModel model, IPropertyRuleIntrospector propertyRuleIntrospector) {
        StringBuffer sb = new StringBuffer( getValidateScript(idPrefix) );
        
        // ensure a valid value for the interval-size
        if (!checkValidInterval(m_minInterval))
            m_minInterval = DEFAULT_MINUTE_INTERVAL;
        
        // set the value & meta
        DateTime value = getRoundedDateTime(model, m_minInterval);
        
        // Format just the Date portion
        String formattedDate = value == null ? "" : Formatter.format(DateTime.toDateOnly(value), propertyRuleIntrospector.getLayout());
        if (formattedDate.indexOf(":") != -1) {
            formattedDate = formattedDate.substring(0,formattedDate.length() - 8);
        }
        
        sb.append("<A id=\"" + idPrefix + "_anchor\" name=\"" + idPrefix + "_anchor\">");
        
        sb.append("<INPUT");
        sb.append(" TYPE=\"text\"");
        sb.append(" ID=\"" + idPrefix + "_date\"");
        sb.append(" VALUE=\"" + formattedDate + "\"" + (propertyRuleIntrospector.isMandatory() ? " class=\"datetimeMandatory\" " : ""));
        sb.append(" SIZE=\"" + m_columns + "\"");
        sb.append(" MAXLENGTH=\"" + propertyRuleIntrospector.getMaxLength() + "\"");
        sb.append( getStyle() );
        sb.append(" onBlur=\"javascript:" + getValidateCall(idPrefix) + "\"");
        sb.append(" onFocus=\"javascript:" + idPrefix + "_tmp = this.value;\"");
        //sb.append("</A><A id=\"" + m_name + "_lookup\" onclick=\"setTextField('" + idPrefix + "') ;");
        sb.append("</A><A id=\"" + idPrefix + "_lookup\" onclick=\"setTextField('" + idPrefix + "') ;");
        sb.append("setCurrentDate('" + idPrefix + "_date') ; document.getElementById('calendardd').disabled = false ;  cal2.showCalendar('" + idPrefix + "_anchor'); ");
        sb.append("return false;\" name=\"anchor6\" href=\"javascript:void(0);\">");
        sb.append("<img src=\"jaffa/imgs/datetime/calendar_32b.gif\" width=\"20\" height=\"20\" valign=\"middle\" align=\"absbottom\" border=\"0\"> </A>");
        
        sb.append("<DIV id=\"testdiv1\" style=\"VISIBILITY: hidden; background-color: #FFFFFF; layer-background-color: #FFFFFF; POSITION: absolute; z-index:100\"></DIV>\n");
        
        // add hidden fields if only the date is to be displayed.. else add dropdowns
        if (getDateOnly() ) {
            sb.append(" <input type=\"HIDDEN\" id=\"" + idPrefix + "_hours\" value=\"0\"/>");
            sb.append(" <input type=\"HIDDEN\" id=\"" + idPrefix + "_mins\" value=\"0\"/>");
        } else {
            sb.append(" <select id=\"" + idPrefix + "_hours\" class=\"dd\" onChange=\"javascript:datetime_updateHidden('" + idPrefix + "');\">");
            //sb.append("<option value=\"\"> </option>");
            for ( int i = 0 ; i < 24 ; i++) {
                if (i < 10) {
                    sb.append("<option value=\"0" + i + "\"" + ( value != null && value.hourOfDay() == i ? "SELECTED" : "") + ">0" + i + "</option>\n");
                } else {
                    sb.append("<option value=\"" + i + "\"" + ( value != null && value.hourOfDay() == i ? "SELECTED" : "") + ">" + i + "</option>\n");
                }
            }
            sb.append("</select>\n");
            
            sb.append(" : <select id=\"" + idPrefix + "_mins\"  class=\"dd\" onChange=\"javascript:datetime_updateHidden('" + idPrefix + "')\";>");
            //sb.append("<option value=\"\"> </option>");
            for ( int i = 0 ; i < 60 ; i = i + m_minInterval) {
                if (i < 10) {
                    sb.append("<option value=\"0" + i + "\""  + ( value != null && value.minute() == i ? "SELECTED" : "") + ">0" + i + "</option>\n");
                } else {
                    sb.append("<option value=\"" + i + "\"" + ( value != null && value.minute() == i ? "SELECTED" : "") + ">" + i + "</option>\n");
                }
            }
            sb.append("</select>\n");
        }
        
        sb.append("<input type=\"HIDDEN\" ");
        sb.append("id=\"" + idPrefix + "\"");
        if(!TagHelper.isEnclosed(pageContext))
            sb.append(" NAME=\"" + m_field + "WV\" ");
        sb.append(" CLASS=\"WidgetDateTime\" ");
        String formattedDateTime = "";
        if (value != null)
            formattedDateTime = formattedDate + " " + value.hourOfDay() + ":" + value.minute();
        sb.append(" VALUE=\"" + formattedDateTime + "\"\n>");
        sb.append(getWidgetRegistrationScript(idPrefix, false));
        return sb.toString();
    }
    
    private String getStyle() {
        StringBuffer sb = new StringBuffer();
        sb.append(" STYLE=\"");
        if(m_bold)
            sb.append(" font-weight: bold;");
        //        if(m_width != null)
        //            sb.append(" width: " + m_width + ";");
        //        if(m_height != null)
        //            sb.append(" height: " + m_height + ";");
        //        if(m_fontSize != null)
        //            sb.append(" font-size: " + m_fontSize + ";");
        //        if(m_fontName != null)
        //            sb.append(" font-family: " + m_fontName + ", Arial, sans-serif;");
        //        if(m_color != null)
        //            sb.append(" color: " + m_color + ";");
        sb.append("\"");
        return sb.toString();
    }
    
    private String getValidateScript(String idPrefix) {
        StringBuffer sb = new StringBuffer();
        sb.append("<SCRIPT type=\"text/javascript\">\n");
        
        // Temporary Variable for seeing if value has changed
        sb.append("  var " + idPrefix + "_tmp = \"\";\n");
        sb.append("  var " + idPrefix + "_validated = false;\n");
        sb.append("  var " + idPrefix + "_fired = false;\n");
        // Define the validate function
        sb.append("function " + idPrefix + "_validate(field , validateMode) {\n");
        sb.append("  var v = field.value;\n");
        sb.append("  if((v == " + idPrefix + "_tmp) && (" + idPrefix + "_validated != false) && (v != \"\"))  return true;\n");
        
        sb.append("  var dateError = datetime_checkDateOnly(field , '" + !m_dateOnly + "' ,'" +  idPrefix +"' ,'" + m_minInterval +"');\n");
        sb.append("	 if(dateError != \"\") {\n");
        
        sb.append("         if(validateMode != \"Global\" &&  " + idPrefix + "_fired == false) {\n");
        sb.append( "" + idPrefix + "_fired = true; \n");
        sb.append("		alert(dateError);\n");
        sb.append("         }");
        sb.append("         " + idPrefix + "_validated = false;\n");
        sb.append("		field.focus();\n");
        sb.append("		field.select();\n");
        sb.append( "" + idPrefix + "_fired = false; \n");
        sb.append("             return false;\n");
        sb.append("		} else {\n");
        sb.append("         " + idPrefix + "_validated = true;\n");
        sb.append("         datetime_updateHidden('" + idPrefix + "');\n");
        sb.append("         return true;\n");
        sb.append("  } \n");
        
        // See if this has a remote callback...
        if(getValidate()) {
            // @todo : this doesn't work.. needs more work
            sb.append("  DateTimeEvent(field, '" + EVENT_VALIDATE + "');\n");
        }
        sb.append("}\n");
        sb.append("</SCRIPT>\n");
        return sb.toString();
    }
    
    private String getValidateCall(String idPrefix) {
        return idPrefix + "_validate(this , '');";
    }
    
    private boolean checkValidInterval(int intervalSize) {
        boolean result = false;
        if (intervalSize != 0) {
            if ((60 % intervalSize) == 0) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }
    
    /**
     * Get the DateTime whose minute portion is a multiple of the intervalSize
     * Update the model, in case the DateTime is changed
     */
    private DateTime getRoundedDateTime(SimpleWidgetModel model, int intervalSize) {
        DateTime value = model.getDateTimeValue();
        if (value != null) {
            if (getDateOnly() ) {
                if (value.hourOfDay() > 0 || value.minute() > 0
                        || value.second() > 0 || value.milli() > 0) {
                    // create a dateOnly
                    value = new DateTime(value.year(), value.month(), value.day());
                    
                    // update the model too !!!
                    model.setWidgetValue(value);
                }
            } else {
                int minute = value.minute();
                int roundedMinute = ((minute + (intervalSize / 2)) / intervalSize) * intervalSize;
                if (roundedMinute > 59) {
                    roundedMinute = 0;
                    value = DateTime.addHour(value, 1);
                }
                if (roundedMinute != minute || value.second() > 0 || value.milli() > 0) {
                    value = new DateTime( value.year(), value.month(), value.day(),
                            value.hourOfDay(), roundedMinute, 0, 0 );
                    
                    if (log.isDebugEnabled() )
                        log.debug("Updating the SimpleWidgetModel from "
                                + model.getStringValue() + " to "
                                + value.toString() );
                    
                    // update the model too !!!
                    model.setWidgetValue(value);
                }
            }
        }
        return value;
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
        if (LocaleContext.getLocale().toString().equals("en_GB")) {
            return c_headeruk;
        } else {
            return c_header;
        }
    }
    
}
