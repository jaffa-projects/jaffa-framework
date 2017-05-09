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
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.WidgetCache;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.model.StringModel;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;


/**
 * This class extends the EditBoxTag class and is intended to represent tags
 * that are used to represent comment fields for the maintenance pattern.
 *
 * Note: for the view pattern, we use a plain Text Widget
 *
 * @author Mark Watson
 * @deprecated - To Be Merged with Editbox
 */
public class CommentTag extends EditBoxTag implements IWidgetTag {
//
//    static final private int NORMAL_COMMENT = 0;
//    static final private int LIFO_COMMENT = 1;
//    static final private int FIFO_COMMENT = 2;
//
//    private static Logger log = Logger.getLogger(CommentTag.class);
//    private static final String TAG_NAME = "CommentTag";
//
//    protected String m_original_comment = "";
//
//    /** Getter for the original comment field.
//     * @return Value of attribute field.
//     */
//    public String getOriginalComment() {
//        return m_original_comment;
//    }
//
//    /** Setter for the original comment field.
//     * @param value New value of attribute field.
//     */
//    public void setOriginalComment(String value) {
//        m_original_comment = value;
//    }
//
//    private int comment_style = NORMAL_COMMENT;
//
//    public CommentTag() {
//        super();
//        initTag();
//    }
//
//    protected void initTag() {
//        super.initTag();
//        m_original_comment = null;
//        comment_style = NORMAL_COMMENT;
//    }
//
//    public int doStartTag() throws JspException {
//        return super.doStartTag();
//    }
//
//    /**
//     * This generates the HTML for the tag.
//     */
//    public void otherDoStartTagOperations() throws JspException {
//        CustomTag.pushParent(this,pageContext);
//        doRegister();
//        IPropertyRuleIntrospector propertyRuleIntrospector = lookupPropertyTag();
//        if (getField() == null) {
//            String str = "The " + TAG_NAME + " requires 'field' parameter to be supplied or it should be within a PropertyTag";
//            log.error(str);
//            throw new MissingParametersRuntimeException(str);
//        }
//
//        // Get the formtag from the page & register the widget
//        FormTag formTag = TagHelper.getFormTag(pageContext);
//        if(formTag == null) {
//            String str = "The " + TAG_NAME + " should be inside a FormTag";
//            log.error(str);
//            throw new OuterFormTagMissingRuntimeException(str);
//        }
//
//        String s_comment_style = propertyRuleIntrospector.getCommentStyle();
//        if (s_comment_style != null) {    // note: defaults to NORMAL_COMMENT
//            if (s_comment_style.equalsIgnoreCase("lifo"))  comment_style = LIFO_COMMENT;
//            if (s_comment_style.equalsIgnoreCase("fifo"))  comment_style = FIFO_COMMENT;
//        }
//
//        // Will hold the required html
//        String html = null;
//
//        // Get the model
//        SimpleWidgetModel model = null;
//        try {
//            model = (SimpleWidgetModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
//        } catch (ClassCastException e) {
//            String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
//            log.error(str, e);
//            throw new JspWriteRuntimeException(str, e);
//        }
//
//		log.info("otherDoStartTagOperations(): model=" + model);
//
//        // Check for an IPropertyRuleIntrospector, if this tag is not within a Property widget
//        try {
//            if (propertyRuleIntrospector == null)
//                propertyRuleIntrospector = TagHelper.getPropertyRuleIntrospector(pageContext, getField());
//            // Wrap the propertyRuleIntrospector
//            propertyRuleIntrospector = TagHelper.wrapPropertyRuleIntrospector(propertyRuleIntrospector, model);
//        } catch (JspException e) {
//            log.error("CommentTag.otherDoStartTagOperations(): error="+e);
//        }
//
//        if (propertyRuleIntrospector.isHidden()) {
//            // Display the (Restricted) text for a hidden field
//            html = TagHelper.getTextForHiddenField(pageContext);
//        } else {
//            if (model != null) {
//                String formName = TagHelper.getFormTag(pageContext).getHtmlName();
//                String idPrefix = getHtmlIdPrefix();
//                String eventPrefix = getJaffaEventNamePrefix();
//                String classPrefix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatoryPrefix\">&nbsp;</span>" : "<span class=\"editboxOptionalPrefix\">&nbsp;</span>";
//                String classSuffix = propertyRuleIntrospector.isMandatory() ? "<span class=\"editboxMandatorySuffix\">&nbsp;</span>" : "<span class=\"editboxOptionalSuffix\">&nbsp;</span>";
//                // Set the layout on the model from the introspector
//                model.setLayout(propertyRuleIntrospector.getLayout());
//                // Generate the HTML
//                try {
//                    html = "<table class=\"WidgetComment\">\n <tr class=\"WidgetComment\">\n  <td class=\"WidgetComment\">\n";
//                    WidgetCache widget_cache = TagHelper.getFormBase(pageContext).getWidgetCache();
//                    String field_name = "" + getField() + "$original";
//                    StringModel old_content = (StringModel)widget_cache.getModel(field_name);
//                    log.info("old_content="+old_content);
//                    m_original_comment = (String)BeanHelper.getField(TagHelper.getFormBase(pageContext), getField());
//                    log.info("m_original_comment="+m_original_comment+", getField()="+getField());
//
//                    Map map = TagHelper.getModelMap(pageContext);
//                    log.info("extractFieldValue map="+map);
//                    Object value = null;
//                    if (map != null)
//                        value = map.get("comments");
//                    log.info("extractFieldValue value="+value);
//
//                    if (comment_style == LIFO_COMMENT || comment_style == FIFO_COMMENT) {
//                        if (old_content == null) {
//                            if (m_original_comment!=null) old_content = new StringModel(escapeHtmlSpaces(StringHelper.splitLines(m_original_comment, 40)));
//                            else                          old_content = new StringModel();
//                        }
//                        html = html + classPrefix + getHtmlTextArea(idPrefix, eventPrefix, formName, new SimpleWidgetModel(), propertyRuleIntrospector) + classSuffix;
//                        html = html + "\n  </td>\n </tr>\n <tr class=\"WidgetComment\">\n  <td class=\"WidgetComment\">\n<pre class=\"WidgetComment\">" + (""+old_content) + "</pre>\n  </td>\n </tr>\n</table>\n";
//                    } else {
//                        if (old_content == null) {
//                            if (m_original_comment!=null) old_content = new StringModel(m_original_comment);
//                            else                          old_content = new StringModel();
//                        }
//                        SimpleWidgetModel swm;
//                        if (old_content != null) swm = new SimpleWidgetModel(""+old_content);
//                        else                     swm = new SimpleWidgetModel("");
//                        html = html + classPrefix + getHtmlTextArea(idPrefix, eventPrefix, formName, swm, propertyRuleIntrospector) + classSuffix +
//                                "</td>\n </tr>\n</table>\n";
//                    }
//                    widget_cache.addModel(field_name, old_content);
//                } catch (JspException e) {
//                    log.error("Comment.otherDoStartTagOperations(): error in getHtml call: " + e);
//                } catch (NoSuchMethodException e) {
//                    log.error("Comment.otherDoStartTagOperations(): error in getHtml call: no such method: " + e);
//                }
//            }
//        }
//        if (html != null) {
//            // Write the HTML
//            JspWriter out = pageContext.getOut();
//            try {
//                out.print(html);
//            } catch (IOException e) {
//                String str = "Exception in writing the " + TAG_NAME;
//                log.error(str, e);
//                throw new JspWriteRuntimeException(str, e);
//            }
//        }
//    }
//
//    private String escapeHtmlSpaces(String s) {
//        log.info("escapeHtmlSpaces("+s+")");
//        StringBuffer sb = new StringBuffer(s.length() + 40);
//        for (int i=0, size=s.length(); i<size; i++) {
//            char ch = s.charAt(i);
//            if (ch == ' ') {
//                sb.append("&nbsp;");
//            } else {
//                sb.append(ch);
//            }
//        }
//        String ret = sb.toString();
//        log.info(" .. escapeHtmlSpaces() returning: " + ret);
//        return ret;
//    }
//    /**
//     * produce HTML for a text area
//     *
//     * @param idPrefix
//     * @param eventPrefix
//     * @param form
//     * @param model
//     * @param propertyRuleIntrospector
//     * @return
//     * @throws JspException
//     */
//    protected String getHtmlTextArea(String idPrefix, String eventPrefix, String form,
//                                     SimpleWidgetModel model,
//                                     IPropertyRuleIntrospector propertyRuleIntrospector) throws JspException {
//
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("<textarea ");
//        if (!TagHelper.isEnclosed(pageContext)) {
//            sb.append(" NAME=\"");
//        }sb.append(m_field);sb.append("WV");sb.append("\" ");
//        sb.append(" ID=\"");
//        sb.append(idPrefix);
//        sb.append("\" ");
//        sb.append(" CLASS=\"WidgetComment\" ");
//        sb.append(" COLS=\"").append(determineColumnSize(propertyRuleIntrospector)).append("\" ");
//        sb.append(" ROWS=\"").append(m_rows).append("\" ");
//        sb.append( getStyle(propertyRuleIntrospector.getCaseType()) );
//        sb.append(" onBlur=\"javascript:validationRules.validateField(this)");
//        sb.append(getServerSideValidate(idPrefix, eventPrefix, form, model));
//        sb.append("\" ");
//        sb.append(" onChange=\"fieldChange(this);\" beenChanged=\"true\" validated=\"false\"");
//        sb.append(getValidateAttribute(idPrefix, eventPrefix, form, model, propertyRuleIntrospector));
//        sb.append(">");
//        sb.append( StringHelper.convertToHTML(propertyRuleIntrospector.format(model.getWidgetValue())));
//        sb.append("</textarea>\n");
//        sb.append("<script>validationRules.addField(document.getElementById('");
//        sb.append(idPrefix);
//        sb.append("'));</script>");
//        if (m_percentageH != null || m_percentageW != null ) {
//            sb.append("<script>resizeTextArea(document.getElementById('");
//            sb.append(idPrefix);
//            sb.append("') , ");
//            sb.append(m_percentageW);
//            sb.append(" , ");
//            sb.append(m_percentageH);
//            sb.append(" , ");
//            sb.append(m_horizontalOffset);
//            sb.append(" , ");
//            sb.append(m_verticalOffset);
//            sb.append(" , ");
//            sb.append(m_horizontalMax);
//            sb.append(" , ");
//            sb.append(m_verticalMax);
//            sb.append(");</script>");
//        }
//        return sb.toString();
//    }

}
