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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.util.MessageHelper;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.StringHelper;


/**
 *  Generated tag class.
 */
public class FoldingSectionTag extends CustomTag implements IWidgetTag,IFormTag,IBodyTag {
    //public class FoldingSectionTag extends BodyTagSupport {
    
    private static final String UNDEFINED_LABEL = "UNDEFINED ";
    private static final String TAG_NAME = "FoldingSectionTag";
    private static String MINIMISE = "jaffa/imgs/foldingsection/arrowminimize.gif";
    private static Logger log = Logger.getLogger(FoldingSectionTag.class);
    private static String c_header = "<SCRIPT type=\"text/javascript\" src=\"jaffa/js/widgets/folding.js\"></SCRIPT>";
    
    /** property declaration for tag attribute: key.
     */
    private String m_key;
    
    /** property declaration for tag attribute: label.
     */
    private String m_label;
    
    /** property declaration for tag attribute: id.
     */
    private String m_id;
    
    /** property declaration for tag attribute: id.
     */
    private boolean m_closed;
    
    /** property declaration for tag attribute: id.
     */
    private boolean m_hideIfNoWidgets = true;
    
    /** property declaration for tag attribute: id.
     */
    private boolean m_containsContent = false;
    
    private StringBuffer debug = null;
    
    public FoldingSectionTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        m_key = m_label = m_id = null;
        m_closed = false;
        m_hideIfNoWidgets = true;
        m_containsContent = false;
        debug = new StringBuffer();
    }
    
    /** Make sure tag is within a form
     */
    public void otherDoStartTagOperations()  {
        if (log.isDebugEnabled())
            log.debug(this.NAME+".otherDoStartTagOperations: START");
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error(this.NAME+".otherDoStartTagOperations: error: " + e, e);
        }
        
        if (log.isDebugEnabled())
            log.debug(this.NAME+".otherDoStartTagOperations: m_containsContent=" + m_containsContent);
        
        // Preprocess if within a Property widget
        lookupPropertyTag();
        
        // Get the formtag from the page & register the widget
        FormTag formTag = TagHelper.getFormTag(pageContext);
        if(formTag == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
        }
    }
    
    // for debug
    public int doAfterBody() throws JspException {
        if (log.isDebugEnabled())
            log.debug(this.NAME+".doAfterBody: START. pageContext="+pageContext+ ", BodyContent="+getBodyContent() );
        
        if(getBodyContent()==null) {
            log.debug(this.NAME+".doAfterBody: Null Body, contains widgets...\n" + debug.toString() );
            return SKIP_BODY;
        } else
            return super.doAfterBody();
    }
    
    /**
     * Wrapper the body with the folding section if applicable
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        if (log.isDebugEnabled())
            log.debug(this.NAME+".otherDoEndTagOperations(): containsContent=" + m_containsContent+", hideIfNoWidgets = "+m_hideIfNoWidgets);
        
        // test to see if anything should be displayed:
        if (m_containsContent || (m_hideIfNoWidgets == false)) {
            // Write out the first part of the folding section
            StringBuffer sb = new StringBuffer();
            sb.append("<table class='foldingSectionOuter' width='100%' border='0' cellspacing='0' cellpadding='0' >\n")
            .append(" <tbody>")
            .append(" <tr>\n")
            .append(" <td colspan='3' >\n")
            .append(" <table class='foldingSectionInner' width='100%' border='0' cellspacing='0' cellpadding='0'>\n")
            .append("  <tbody>")
            .append("  <tr>\n")
            .append("  <td class='sectionheader' colSpan=4 valign='bottom' width='100%'>\n")
            .append("  <table width='100%'>\n")
            .append("   <tr>\n")
            .append("   <td class='sectiontitle'>").append(getName()).append((getKey() != null ? TagHelper.getLabelEditorLink(pageContext, getKey()) : "")).append("</td>\n")
            .append("   <td align='right'><a id='").append(getId()).append("section' name=" + getId() + "section href=\"javascript:expand('").append(getId()).append("' , '").append(getId()).append("Expand')\"><img src='").append(MINIMISE).append("' border='0'  name='").append(getId()).append("Expand'></a></td>\n")
            .append("   </tr>\n")
            .append("  </table>\n")
            .append("  </td>\n")
            .append("  </tr>\n")
            .append("  <tr class='foldingsectionleft'>\n")
            .append("  <td colspan='3' class='foldingsectionpad'>\n")
            .append("  <span id='").append(getId()).append("' name=\"").append(StringHelper.convertToHTML(getName())).append("\" style='display:block' class='WidgetFoldingSection'>\n")
            .append("  <table class='outer' cellspacing='0' cellpadding='0' width='100%'>\n")
            .append("   <tr>\n")
            .append("   <td>\n");
            // NOTE: The StringHelper.convertToHTML() routine escapes doubles-quotes, but not single-quotes. Hence we've used double-quotes around the 'name' attribute of the <span> tag above
            
            // Write the header stuff
            out.print(sb.toString());
            if (log.isDebugEnabled())
                log.debug(this.NAME+".doAfterBody: Write out top part");
            
            // write the body content (after processing by the JSP engine) on the output Writer
            bodyContent.writeOut(out);
            if (log.isDebugEnabled())
                log.debug(this.NAME+".doAfterBody: Write Body = ...\n" + bodyContent.getString());
            
            // Write out the closing part of the folding section
            sb = new StringBuffer();
            sb.append("   </td>\n")
            .append("   </tr>\n")
            .append("  </table>\n")
            .append("  </span>\n")
            .append("  </td>\n")
            .append("  </tr>\n")
            .append("  </tbody>\n")
            .append(" </table>\n")
            .append(" </td>\n")
            .append(" </tr>\n")
            .append(" </tbody>\n")
            .append("</table>\n")
            .append("<script>\nfoldingSectionList.push(document.getElementById('")
            .append(getId())
            .append("'));\n");
            if (getClosed()) {
                sb.append("initialExpand('").append(getId()).append("' , '").append(getId()).append("Expand');\n");
            } else {
                sb.append("setDefaultState('").append(getId()).append("' , '").append(getId()).append("Expand');\n");
            }
            sb.append("</script>\n");
            
            //@TODO - need to keep track of the state using cookies?
            //sb.append("<script>SetDefaultState('" + getId() + "');</script>");
            out.print(sb.toString());
        } else {
            log.debug("FoldingSection contents suppressed as it has no visible registered widgets. Body was=\n" + bodyContent.getString());
        }
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    public String getName() {
        if (getKey() != null) {
            if (MessageHelper.hasTokens(getKey()))
                return MessageHelper.replaceTokens(pageContext, getKey());
            else
                return MessageHelper.findMessage(pageContext, getKey(), null);
        } else if (getLabel() != null) {
            return getLabel();
        } else {
            return "LABEL/KEY UNDEFINED!!!";
        }
    }
    
    public String getKey() {
        return m_key;
    }
    
    public void setKey(String value) {
        m_key = value;
    }
    
    public String getLabel() {
        return m_label;
    }
    
    public void setLabel(String value) {
        m_label = value;
    }
    
    public String getId() {
        return m_id;
    }
    
    public void setId(String value) {
        m_id = value;
    }
    
    public String getHeaderHtml() {
        return c_header;
    }
    
    public boolean getClosed() {
        return m_closed;
    }
    
    public void setClosed(boolean value) {
        m_closed = value;
    }
    
    
    public boolean getHideIfNoWidgets() {
        return m_hideIfNoWidgets;
    }
    
    public void setHideIfNoWidgets(boolean hideIfNoWidgets) {
        m_hideIfNoWidgets = hideIfNoWidgets;
    }
    
    public void setFoldingTagNotHidden() {
        m_containsContent = true;
    }
    
    
    public void register(ICustomTag tag) {
        super.register(tag);
        if (tag instanceof IWidgetTag ) {
            if (log.isDebugEnabled() && !m_containsContent )
                log.debug(this.NAME+".register: IWidget component registered as visible");
            m_containsContent=true;
            
            debug.append(tag).append(" ");
            if(tag instanceof CustomModelTag)
                debug.append(" Field=").append( ((CustomModelTag)tag).getField() );
            debug.append("\n");
            
        }
    }
    
    public void doFinally() {
        super.doFinally();
        initTag();
    }
    
    public String toString() {
        if(m_id==null)
            return super.toString();
        else {
            String x=super.toString();
            return (new StringBuffer(x==null?"<??? ":x.substring(0,x.length()-2)))
            .append(" id='")
            .append(m_id)
            .append("'>")
            .toString();
        }
    }
    
    /** Checks for the nearest outer PropertyTag.
     * Will set the 'key' on this tag, if not specified, with the value from the rules engine.
     * If not specifed, it will use the values from the outer PropertyTag to determine the FieldMetaData object to obtain the key
     */
    private void lookupPropertyTag() {
        PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
        if (propertyTag != null) {
            IPropertyRuleIntrospector propertyRuleIntrospector = propertyTag.getPropertyRuleIntrospector();
            if (getKey() == null && getLabel() == null) {
                try {
                    String domainClassName = propertyTag.getPropertyClass();
                    String fieldName = propertyTag.getPropertyName();
                    setKey(TagHelper.getFieldMetaData(domainClassName, fieldName).getLabelToken());
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }
    
}
