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
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.util.MessageHelper;

/** Tag Handler for the Button widget.*/
public class ButtonTag extends CustomModelTag implements IWidgetTag,IFormTag {
//public class ButtonTag extends TagSupport implements TryCatchFinally {
    
    private static Logger log = Logger.getLogger(ButtonTag.class);
    private static final String TAG_NAME = "ButtonTag";
    private static String c_header = "<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/button.js\"></SCRIPT>";
    
    // Define The Events
    /** The detail event.*/
    public static final String EVENT_DETAIL = "Clicked";
    
    /** Default CSS Classname for a Regular "Default" HTML Button */
    public static final String CSS_REGULAR = "WidgetButton";
    
    /** Default CSS Classname for a Image HTML Button */
    public static final String CSS_IMAGE = "WidgetButtonImage";
    
    /**Default CSS Classname for a Link HTML Button */
    public static final String CSS_LINK = "WidgetButtonLink";
    
    
    /** property declaration for tag attribute: label.
     */
    private String m_label;
    
    /** property declaration for tag attribute: color.
     */
    private String m_color;
    
    /** property declaration for tag attribute: fontname.
     */
    private String m_fontname;
    
    /** property declaration for tag attribute: fontsize.
     */
    private String m_fontsize;
    
    /** property declaration for tag attribute: height.
     */
    private String m_height;
    
    /** property declaration for tag attribute: width.
     */
    private String m_width;
    
    /** property declaration for tag attribute: image.
     */
    private String m_image;
    
    /** property declaration for tag attribute: rolloverImage.
     */
    private String m_rolloverImage;
    
    /** property declaration for tag attribute: text.
     */
    private boolean m_text;
    
    /** property declaration for tag attribute: detail.
     */
    private boolean m_detail;
    
    /** property declaration for tag attribute: submit.
     * A 'submit' button/image will be created if the value is true.
     * This property has no effect on a 'text' button.
     */
    private boolean m_submit;
    
    /** property declaration for tag attribute: guarded.
     */
    private boolean m_guarded;
    
    /** property declaration for tag attribute: confirm.
     */
    private String m_confirm;
    
    /** property declaration for tag attribute: preprocess.
     */
    private boolean m_preprocess;
    
    /** property declaration for tag attribute: target.
     */
    private String m_target;
    
    /** property declaration for tag attribute: type.
     */
    private String m_type;
    
    /** property declaration for tag attribute: classExtn.
     */
    private String m_classExtn;
    
    /** property declaration for tag attribute: classOverride.
     */
    private String m_classOverride;
    
    /**property declaration for tag attribute: widthFactor.
     */
    private int m_widthFactor;
    
    /** property declaration for tag attribute: arg0.
     */
    private String m_arg0;
    
    /** property declaration for tag attribute: arg1.
     */
    private String m_arg1;
    
    /** property declaration for tag attribute: arg2.
     */
    private String m_arg2;
    
    /** property declaration for tag attribute: arg3.
     */
    private String m_arg3;
    
    /** property declaration for tag attribute: arg4.
     */
    private String m_arg4;
    
    /** The value for this field will be computed based on m_label */
    private String m_labelEditorLink;
    
    /** Default constructor.
     */
    public ButtonTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_label = null;
        m_color = null;
        m_fontname = null;
        m_fontsize = null;
        m_height = null;
        m_width = null;
        m_image = null;
        m_rolloverImage = null;
        m_text = false;
        m_detail = true;
        m_submit = false;
        m_guarded = false;
        m_confirm = null;
        m_preprocess = true;
        m_target = null;
        m_type = null;
        m_widthFactor = 4;
        m_arg0 = null;
        m_arg1 = null;
        m_arg2 = null;
        m_arg3 = null;
        m_arg4 = null;
        m_labelEditorLink = "";
    }
    
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    /** Returns the string containing the javascript info for the HTML header.
     * @return the string containing the javascript info for the HTML header.
     */
    public static String getHeader() {
        return c_header;
    }
    
    
    //
    // methods called from doStartTag()
    //
    /**
     * This generates the HTML for the tag.
     */
    public void otherDoStartTagOperations()  {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error("ButtonTag.otherDoStartTagOperations() error: " + e);
        }
        
        //
        // TODO: code that performs other operations in doStartTag
        //       should be placed here.
        //       It will be called after initializing variables,
        //       finding the parent, setting IDREFs, etc, and
        //       before calling theBodyShouldBeEvaluated().
        //
        
        // check if the tag is enclosed
        boolean enclosed = TagHelper.isEnclosed(pageContext);
        
        // Get the formtag from the page & register the widget
        FormTag formTag = TagHelper.getFormTag(pageContext);
        if(formTag == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
        }
        //formTag.register(this.getClass());
        //formTag.register(this);
        
        if (getGuarded())
            formTag.registerGuardedButton();
        
        // Generate the HTML
        JspWriter out = pageContext.getOut();
        String formName = TagHelper.getFormTag(pageContext).getHtmlName();
        String idPrefix = getHtmlIdPrefix();
        String eventPrefix = getJaffaEventNamePrefix();
        
        try {
            out.println( getHtml(idPrefix, eventPrefix, formName, enclosed) );
        } catch (IOException e) {
            String str = "Exception in writing the " + TAG_NAME;
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }
    }
    
    
    
    /** Getter for the attribute label.
     * @return Value of attribute label.
     */
    public String getLabel() {
        return m_label;
    }
    
    /** Setter for the attribute label.
     * @param value New value of attribute label.
     */
    public void setLabel(String value) {
        if (value != null) {
            m_label = MessageHelper.replaceTokens(pageContext, value);
            if (MessageHelper.hasTokens(value))
                m_labelEditorLink = TagHelper.getLabelEditorLink(pageContext, value);
        } else
            m_label = value;
    }
    
    /** Getter for the attribute color.
     * @return Value of attribute color.
     */
    public String getColor() {
        return m_color;
    }
    
    /** Setter for the attribute color.
     * @param value New value of attribute color.
     */
    public void setColor(String value) {
        m_color = value;
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
    
    /** Getter for the attribute image.
     * @return Value of attribute image.
     */
    public String getImage() {
        return m_image;
    }
    
    /** Setter for the attribute image.
     * @param value New value of attribute image.
     */
    public void setImage(String value) {
        m_image = value;
    }
    
    /** Getter for the attribute rolloverImage.
     * @return Value of attribute rolloverImage.
     */
    public String getRolloverImage() {
        return m_rolloverImage;
    }
    
    /** Setter for the attribute rolloverImage.
     * @param value New value of attribute rolloverImage.
     */
    public void setRolloverImage(String value) {
        m_rolloverImage = value;
    }
    
    /** Getter for the attribute text.
     * @return Value of attribute text.
     */
    public boolean getText() {
        return m_text;
    }
    
    /** Setter for the attribute text.
     * @param value New value of attribute text.
     */
    public void setText(boolean value) {
        m_text = value;
    }
    
    /** Getter for the attribute detail.
     * @return Value of attribute detail.
     */
    public boolean getDetail() {
        return m_detail;
    }
    
    /** Setter for the attribute detail.
     * @param value New value of attribute detail.
     */
    public void setDetail(boolean value) {
        m_detail = value;
    }
    
    /** Getter for the attribute submit.
     * @return Value of attribute submit.
     */
    public boolean getSubmit() {
        return m_submit;
    }
    
    /** Setter for the attribute submit.
     * @param value New value of attribute submit.
     */
    public void setSubmit(boolean value) {
        m_submit = value;
    }
    
    /** Getter for the attribute guarded.
     * @return Value of attribute guarded.
     */
    public boolean getGuarded() {
        return m_guarded;
    }
    
    /** Setter for the attribute guarded.
     * @param value New value of attribute guarded.
     */
    public void setGuarded(boolean value) {
        m_guarded = value;
    }
    
    /** Getter for the attribute confirm.
     * @return Value of attribute confirm.
     */
    public String getConfirm() {
        return m_confirm;
    }
    
    /** Setter for the attribute confirm.
     * @param value New value of attribute confirm.
     */
    public void setConfirm(String value) {
        m_confirm = value;
    }
    
    /** Getter for the attribute preprocess.
     * @return Value of attribute preprocess.
     */
    public boolean getPreprocess() {
        return m_preprocess;
    }
    
    /** Setter for the attribute preprocess.
     * @param value New value of attribute preprocess.
     */
    public void setPreprocess(boolean value) {
        m_preprocess = value;
    }
    
    /** Getter for the attribute target.
     * @return Value of attribute target.
     */
    public String getTarget() {
        return m_target;
    }
    
    /** Setter for the attribute target.
     * @param value New value of attribute target.
     */
    public void setTarget(String value) {
        m_target = value;
    }
    
    /**Getter for the attribute type.
     * @return Value of attribute type.
     */
    public String getType() {
        return m_type;
    }
    
    /**Setter for the attribute type.
     * @param value New value of attribute type.
     */
    public void setType(String value) {
        m_type = value;
    }
    
    /**Getter for the attribute classExtn.
     * @return Value of attribute classExtn.
     */
    public String getClassExtn() {
        return m_classExtn;
    }
    
    /**Setter for the attribute classExtn.
     * @param value New value of attribute classExtn.
     */
    public void setClassExtn(String value) {
        m_classExtn = value;
    }
    
    /**Getter for the attribute classOverride.
     * @return Value of attribute classOverride.
     */
    public String getClassOverride() {
        return m_classOverride;
    }
    
    /**Setter for the attribute classOverride.
     * @param value New value of attribute classOverride.
     */
    public void setClassOverride(String value) {
        m_classOverride = value;
    }
    
    /**Getter for the attribute widthFactor.
     * @return Value of attribute widthFactor.
     */
    public int getWidthFactor() {
        return m_widthFactor;
    }
    
    /**Setter for the attribute widthFactor.
     * @param value New value of attribute widthFactor.
     */
    public void setWidthFactor(int value) {
        m_widthFactor = value;
    }
    
    /** Getter for the attribute arg0.
     * @return Value of attribute arg0.
     */
    public String getArg0() {
        return m_arg0;
    }
    
    /** Setter for the attribute arg0.
     * @param value New value of attribute arg0.
     */
    public void setArg0(String value) {
        m_arg0 = value;
    }
    
    /** Getter for the attribute arg1.
     * @return Value of attribute arg1.
     */
    public String getArg1() {
        return m_arg1;
    }
    
    /** Setter for the attribute arg1.
     * @param value New value of attribute arg1.
     */
    public void setArg1(String value) {
        m_arg1 = value;
    }
    
    /** Getter for the attribute arg2.
     * @return Value of attribute arg2.
     */
    public String getArg2() {
        return m_arg2;
    }
    
    /** Setter for the attribute arg2.
     * @param value New value of attribute arg2.
     */
    public void setArg2(String value) {
        m_arg2 = value;
    }
    
    /** Getter for the attribute arg3.
     * @return Value of attribute arg3.
     */
    public String getArg3() {
        return m_arg3;
    }
    
    /** Setter for the attribute arg3.
     * @param value New value of attribute arg3.
     */
    public void setArg3(String value) {
        m_arg3 = value;
    }
    
    /** Getter for the attribute arg4.
     * @return Value of attribute arg4.
     */
    public String getArg4() {
        return m_arg4;
    }
    
    /** Setter for the attribute arg4.
     * @param value New value of attribute arg4.
     */
    public void setArg4(String value) {
        m_arg4 = value;
    }
    
    private String getHtml(String idPrefix, String eventPrefix,
            String formName, boolean enclosed) {
        String html = null;
        if ("submit".equalsIgnoreCase(m_field) && !getSubmit()) {
            String str = "The " + TAG_NAME + "  called " + m_field + " requires the parameter sumbit=\"true\" to be supplied";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
        if ((m_type==null || m_type.length() == 0)) {
            if (m_image==null || m_image.length() == 0)
                //setType to a default HTML button
                setType("DEFAULT");
            else
                //setType to an IMAGE button
                setType("IMAGE");
        }
        
        if (m_type.equalsIgnoreCase("IMAGE")) {
            if (m_rolloverImage==null || m_rolloverImage.length() == 0)
                //create a flat image button
                html =  getHtmlButton(idPrefix, eventPrefix, formName, enclosed);
            else
                //create a rollover button
                html =  getHtmlRollButton(idPrefix, eventPrefix, formName, enclosed);
        }
        
        if (m_type.equalsIgnoreCase("DEFAULT"))
            //create a regular button
            html = getHtmlInput(idPrefix, eventPrefix, formName, enclosed);
        
        if (m_type.equalsIgnoreCase("LINK") || m_type.equalsIgnoreCase("SIZED")) {
            //create a link or a CSS style button
            html = getTextButton(idPrefix, eventPrefix, formName);
        }
        
        // Append the labelEditorLink for non-image buttons
        if (!m_type.equalsIgnoreCase("IMAGE"))
            html += m_labelEditorLink;
        
        return html;
    }
    
    private String getStyle() {
        StringBuffer sb = new StringBuffer();
        
        if(m_width != null)
            sb.append(" width: " + m_width + ";");
        if(m_height != null)
            sb.append(" height: " + m_height + ";");
        if(m_fontsize != null)
            sb.append(" font-size: " + m_fontsize + ";");
        if(m_fontname != null)
            sb.append(" font-family: " + m_fontname + ", Arial, sans-serif;");
        if(m_color != null)
            sb.append(" color: " + m_color + ";");
        
        if (sb.length() > 0)
            return " style=\"" + sb.toString() + "\"";
        else
            return "";
    }
    
    
    private String getTextButton(String idPrefix, String eventPrefix, String formName) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("<a ");
        //if classOverride specified, use that as the CSS Class
        if (((m_classOverride != null) && (m_classOverride.length() > 0)))
            sb.append("class=\"" + m_classOverride);
        else {
            //if CSS-type button, calculate the width based on the width factor.
            //By default, widthFactor is 4.
            if (m_type.equalsIgnoreCase("SIZED"))
                sb.append("class=\"" + CSS_REGULAR + ( (((getLabel().length()-1)/m_widthFactor)+1)*m_widthFactor ));
            else
                sb.append("class=\"" + CSS_LINK);
            if(getSubmit())
                sb.append("Submit");
            //if classExtn specified, append that to the CSS-class name
            if ((((m_classExtn != null) && (m_classExtn.length() > 0))))
                sb.append(m_classExtn);
        }
        
        sb.append("\" ");
        sb.append("href=\"javascript:" + getScriptForPost(formName) + "\" ");
        sb.append("onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName) + "\" ");
        sb.append("title='" + getLabel() + "' ");
        sb.append( getStyle() );
        sb.append(">");
        sb.append("" + getLabel());
        sb.append("</a>\n");
        if(getSubmit()) {
            sb.append("<SCRIPT type=\"text/javascript\">\n");
            sb.append("function trapKey(e){\n");
            sb.append(" trap=false;\n");
            sb.append(" try {\n");
            sb.append("  if (document.addEventListener){\n");
            sb.append("   tag=e.target.type.toLowerCase();\n");
            sb.append("  }else{\n");
            sb.append("   tag=e.srcElement.type.toLowerCase();\n");
            sb.append("  }\n");
            sb.append("  trap=(tag==\"text\"||tag==\"password\"||tag==\"select-one\"||tag==\"radio\");\n");
            sb.append(" } catch (ex) {}\n");
            sb.append(" if(e.keyCode == 13 && trap) {\n");
            sb.append("  if(validateTrapKey('" + formName + "')) {");
            sb.append(getScriptForOnClick(idPrefix, eventPrefix, formName) );
            sb.append("\n");
            sb.append(getScriptForPost(formName) );
            sb.append("\n  }\n");
            sb.append(" }");
            sb.append("}\n");
            sb.append("if (document.addEventListener)\n");
            sb.append(" document.addEventListener(\"keypress\", trapKey , false);\n");
            sb.append("else\n");
            sb.append(" document.attachEvent(\"onkeypress\", trapKey);\n");
            sb.append("</SCRIPT>\n");
        }
        return sb.toString();
    }
    
    private String getHtmlInput(String idPrefix, String eventPrefix, String formName,
            boolean enclosed) {
        StringBuffer sb = new StringBuffer();
        
        String cssName = CSS_REGULAR;
        cssName = getClassName(cssName);
        if (m_submit)
            sb.append("<INPUT class=\"" + cssName+"\" TYPE=\"submit\"");
        else
            sb.append("<INPUT class=\"" + cssName+"\" TYPE=\"button\"");
        
        if(!enclosed)
            sb.append(" NAME=\"" + m_field + "\"");
        sb.append(" ID=\"" + idPrefix + "\"");
        sb.append(" VALUE=\"" + m_label + "\"");
        sb.append( getStyle() );
        sb.append(" onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName));
        if (!m_submit)
            sb.append(getScriptForPost(formName));
        sb.append("\">");
        return sb.toString();
    }
    
    private String getHtmlButton(String idPrefix, String eventPrefix, String formName,
            boolean enclosed) {
        StringBuffer sb = new StringBuffer();
        String cssName = CSS_IMAGE;
        cssName = getClassName(cssName);
        
        if (m_submit) {
            sb.append("<INPUT class=\""+cssName+"\" TYPE=\"image\"");
            sb.append(" title='" + m_label + "' ");
            if(!enclosed)
                sb.append(" NAME=\"" + m_field + "\"");
            sb.append(" ID=\"" + idPrefix + "\"");
            sb.append(" SRC=\"" + m_image + "\" border=\"0\" ");
            sb.append( getStyle() );
            sb.append(" onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName) + "\"");
            sb.append(" align=\"absbottom\"");
            sb.append(">");
        } else {
            sb.append("<a class=\""+cssName+"\" href=\"javascript:" + getScriptForPost(formName) + "\" ");
            sb.append("onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName) + "\" ");
            sb.append("title='" + m_label + "' ");
            sb.append(">");
            sb.append("<img src=\""+ m_image +"\" border=\"0\"");
            sb.append( getStyle() );
            sb.append(" align=\"absbottom\">");
            sb.append("</a>");
        }
        return sb.toString();
    }
    
    private String getHtmlRollButton(String idPrefix, String eventPrefix, String formName,
            boolean enclosed) {
        StringBuffer sb = new StringBuffer();
        String cssName = CSS_IMAGE;
        cssName = getClassName(cssName);
        
        if (m_submit) {
            sb.append("<INPUT class=\""+cssName+"\" TYPE=\"image\"");
            sb.append(" title='" + m_label + "' ");
            if(!enclosed)
                sb.append(" NAME=\"" + m_field + "\"");
            sb.append(" ID=\"" + idPrefix + "\"");
            sb.append(" SRC=\"" + m_image + "\" border=\"0\" ");
            sb.append( getStyle() );
            sb.append(" onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName) + "\"");
            sb.append(" align=\"absbottom\"");
            sb.append(" onMouseOut=\"buttonSwapImgRestore();\" ");
            sb.append(" onMouseOver=\"buttonSwapImage('" + idPrefix + "','','"+ m_rolloverImage +"',1);\"");
            sb.append(">");
        } else {
            sb.append("<a class=\""+cssName+"\" href=\"javascript:" + getScriptForPost(formName) + "\" ");
            sb.append("onClick=\"" + getScriptForOnClick(idPrefix, eventPrefix, formName) + "\" ");
            sb.append(" title='" + m_label + "' ");
            sb.append(" onMouseOut=\"buttonSwapImgRestore();\" ");
            sb.append(" onMouseOver=\"buttonSwapImage('" + idPrefix + "','','"+ m_rolloverImage +"',1);\"");
            sb.append(">");
            sb.append("<img name=\"" + idPrefix + "\" src=\""+ m_image +"\" border=\"0\"");
            sb.append( getStyle() );
            sb.append(" align=\"absbottom\">");
            sb.append("</a>");
        }
        return sb.toString();
    }
    
    /** This method will generate javascript for the onClick triggers of the various HTML tags.
     * The javascript it generates, will return a false, in case anything goes wrong.
     */
    private String getScriptForOnClick(String idPrefix, String eventPrefix, String formName) {
        StringBuffer sb = new StringBuffer();
        if(getDetail()) {
            String formObject = "document." + formName;
            String eventField = formObject + ".eventId.value";
            
            // Display the confirmation message(s), if required
            if (getConfirm() != null && getConfirm().length() > 0) {
                String[] confirmMessageArray = getConfirm().split(";");
                if (confirmMessageArray != null && confirmMessageArray.length > 0) {
                    Object[] args = null;
                    if (m_arg0 != null || m_arg1 != null || m_arg2 != null || m_arg3 != null || m_arg4 != null) {
                        args = new Object[] {m_arg0, m_arg1, m_arg2, m_arg3, m_arg4};
                    }
                    for(int i=0; i< confirmMessageArray.length; i++) {
                        String confirmMessage = MessageHelper.findMessage(pageContext, MessageHelper.removeTokenMarkers(confirmMessageArray[i]), args);
                        // If a token evaluates to an empty string, we should suppress the message
                        if (confirmMessage!=null && confirmMessage.length() > 0)
                            sb.append("if (!confirm('" + confirmMessage + "')) return false;");
                    }
                }
            }
            
            // Set the event
            sb.append(eventField + "='" + eventPrefix + ';' + EVENT_DETAIL + "';");
            
            // set the Target
            if (getTarget() != null && getTarget().length() > 0)
                sb.append(formObject + ".target='" + getTarget() + "';");
            else {
                // Set the target using the value specified in the FormTag.. Set to '_self' if not specified
                FormTag formTag = TagHelper.getFormTag(pageContext);
                if (formTag == null || formTag.getTarget() == null || formTag.getTarget().length() == 0)
                    sb.append(formObject + ".target='_self';");
                else
                    sb.append(formObject + ".target='" + formTag.getTarget() + "';");
            }
            
            if (getPreprocess()) {
                // Perform preProcessing. This will generate the appropriate XML for the grid
                // The preProcess in turn will perform the necessary validations, and then start the progress-bar if required
                sb.append("preProcess(" + formObject + ", null);");
            } else if (getGuarded()) {
                // Starts the progress-bar
                sb.append("startProgressBar(" + formObject + ".id);");
            }
        }
        return sb.toString();
    }
    
    /** This method will generate javascript for the href trigger of the anchor tag.
     * It can also be used to generate javascript for the onclick trigger of the button tag.
     * In this case, this will typically append code to the getScriptForOnclick() code;
     * hence the indicateJavaScript should be passes as false.
     */
    private String getScriptForPost(String formName) {
        StringBuffer sb = new StringBuffer();
        if(getDetail()) {
            String formObject = "document." + formName;
            if (getPreprocess()) {
                sb.append("postForm(" + formObject + ", true);");
            } else {
                sb.append("postForm(" + formObject + ", false);");
            }
        }
        return sb.toString();
    }
    
    /** This method returns the CSS classname to be used based on the values of
     *the classOverride, classExtn, submit attributes
     */
    private String getClassName(String value) {
        String newClass = value;
        if (((m_classOverride != null) && (m_classOverride.length() > 0))){
            newClass = m_classOverride;
        } else {
            if (m_submit)
                newClass = newClass+"Submit";
            if (((m_classExtn != null) && (m_classExtn.length() > 0)))
                newClass = newClass+m_classExtn;
        }
        
        return newClass;
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
