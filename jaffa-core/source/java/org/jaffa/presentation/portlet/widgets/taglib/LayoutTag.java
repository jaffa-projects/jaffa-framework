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
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.InvalidParametersRuntimeException;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

/** Tag Handler for the Layout widget.*/
public class LayoutTag extends CustomModelTag implements IWidgetTag,IFormTag,IBodyTag {

    private static Logger log = Logger.getLogger(LayoutTag.class);

    /** the css class used */
    public static final String LAYOUT_CSS_CLASS = "jaffaTabLayout";
    public static final String TAB_STRIP_CSS_CLASS = "jaffaTabLabels-top";
    public static final String TAB_CSS_CLASS = "jaffaTab";
    public static final String TAB_LABEL_CSS_CLASS = "jaffaTabLabelsText";
    public static final String PANE_WRAPPER_CSS_CLASS = "jaffaTabPaneWrapper";
    public static final String PANE_CSS_CLASS = "jaffaTabPane";
    private static String MINIMISE = "jaffa/imgs/foldingsection/arrowminimize.gif";

    private static final String TAG_NAME = "LayoutTag";

    //----------------------------------------------
    //-- TLD Attributes
    /** property declaration for tag attribute: id. */
    private String m_id;
    /** property declaration for tag attribute: type. */
    private String m_type;
    /** property declaration for tag attribute: sectionIds. */
    private String m_sectionIds;
    //----------------------------------------------
    
    public static enum LayoutType { TABS, FOLDING, TABSTRIP };
    public static final String TABS = "tabs";
    public static final String TABSTRIP = "tabstrip";
    public static final String FOLDING = "folding";
    
    
    private LayoutType m_layout = LayoutType.TABS;
    
    /** Store the original state of the pagecontext attributes */
    private Map m_originalAttributes;
    /** Maintains a map of sectionId/Section Tab objects for each SectionTag */
    private LinkedHashMap<String, SectionHead> m_sectionHeads;
    /** Maintains a map of sectionId/Section Pane objects for each SectionTag */
    private LinkedHashMap<String, String> m_sectionContents;

    /** Default constructor. */
    public LayoutTag() {
        super();
        initTag();
    }


    /** Make sure layout type is valid. */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();
        
        // Store the Original Attributes
        m_originalAttributes = TagHelper.getOriginalAttributes(pageContext);

        m_layout = null;
        if(m_type!=null && m_type.length()>0) {
            if(TABS.equalsIgnoreCase(m_type))
                m_layout = LayoutType.TABS;
            else if (FOLDING.equalsIgnoreCase(m_type))
                m_layout = LayoutType.FOLDING;
            else if (TABSTRIP.equalsIgnoreCase(m_type))
                m_layout = LayoutType.TABSTRIP;
        } else {
            m_layout = LayoutType.TABS;
            m_type = TABS;
        }
        if(m_layout==null)
            throw new InvalidParametersRuntimeException("type must be one of ["+TABS+","+TABSTRIP+","+FOLDING+"]");
    }


    /**
     * Used to get notification of any inner Section Tags
     * Section tags are used to register the list of sections which are available
     * All other tags are passed on and registered with this Layout Parent
     * @param tag Inner tag being registered
     */
    public void register(ICustomTag tag) {
        if(tag instanceof SectionTag) {
            SectionTag section = (SectionTag) tag;
            m_sectionHeads.put(section.getId(), new SectionHead(section.getId(), section.getLabel(), 
                    section.getLabelEditorLink(), section.getJaffaDataUrl(), section.getDataForward(),
                    section.isClosed(), section.getOnload(), section.getOnclick(), section.getStyleId(),
                    section.isHideIfNoWidgets()));
        } else
            super.register(tag);
    }


    /** Concludes the html for the grid tag.
     * Called from the doEndTag()
     */
    public void otherDoEndTagOperations() throws JspException {
        super.otherDoEndTagOperations();
    }


    /** Adds the widget html information to a linked hashmap for processing of the Section pane
     * @param sectionId The ID of the Section.
     */
    public void addSectionContent(String sectionId , String value) {
        m_sectionContents.put(sectionId , value);
    }


    /** This generates the HTML for the tag.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {

        StringBuffer tabStrip = new StringBuffer();
        StringBuffer sections = new StringBuffer();
        String[] ids;
        
        // Loop over all the sections
        if(m_sectionIds == null) {
            ids = (String[]) m_sectionHeads.keySet().toArray(new String[0]);
        } else {
            ids = m_sectionIds.split(",");
        }
        for(String id : ids) {
            SectionHead sh = (SectionHead) m_sectionHeads.get(id);
            String html = (String) m_sectionContents.get(id);
           
            if(m_layout==LayoutType.TABS) {
                // Generate Tab HTML
                tabStrip.append(sh.writeSectionTab(this));
                sections.append(sh.writeSectionContent(this,html));
            } else if (m_layout==LayoutType.TABSTRIP) {
                tabStrip.append(sh.writeSectionTab(this));
            } else if (m_layout==LayoutType.FOLDING) {
                // Generate Folding Section HTML
                sections.append(sh.writeFoldingSectionBody(this,html));
            }
        }
        
        // Now write out the final html 
        if(m_layout==LayoutType.TABS || m_layout==LayoutType.TABSTRIP) {
            out.println("<div id='"+getId()+"' class='"+LAYOUT_CSS_CLASS+"'>"); 
            out.println("  <input type='hidden' name='"+getId()+"Idx' id='"+getId()+"Idx' value='"+getId()+"-"+ids[0]+"'>");
            out.println("  <img id='hiddenbar' src='imgs/hline.gif' border='0' width='800px' style='display:;visibility:hidden'>");
            // writing the tabs
            out.println("  <div class='"+TAB_STRIP_CSS_CLASS+"' >");
            out.println(tabStrip.toString());
            out.println("  </div>"); // Content Pane Layer
            // writing the content panes
            out.println("  <div class='"+PANE_WRAPPER_CSS_CLASS+"'>");
            if (m_layout==LayoutType.TABS)
                out.println(sections.toString());
            else
                out.println(bodyContent.toString());
            out.println("  </div>"); // Content Pane Layer
            out.println("</div>"); // Outer Layer
            out.println("<script language='javascript'>switchTab(null,'"+getId()+"');</script>");
        } else if (m_layout==LayoutType.FOLDING) {
            out.print(sections.toString());
        }
        
        bodyContent.clearBody();
    }


    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        TagHelper.restoreOriginalAttributes(pageContext, m_originalAttributes);
        // Reset all state in this Tag
        initTag();
        // Clear out base class stuff too
        super.doFinally();
    }


    //---------------------------------------------------
    //-- Private Methods
    //---------------------------------------------------

    /** Initialize all the data for a new tag, or a re-used tag */
    private void initTag() {
        //-- TLD Attributes
        m_id = null;
        m_type = null;
        m_sectionIds = null;
        m_layout = null;

        //-- Layout state
        m_originalAttributes = null;
        m_sectionContents = new LinkedHashMap<String, String>();
        m_sectionHeads = new LinkedHashMap<String, SectionHead>();
    }


    //---------------------------------------------------
    //-- Inner Classes
    //---------------------------------------------------

    /** An instance of this class will be created for each UserGridColumn.
     */
    public static class SectionHead {
        private String m_id;
        private String m_label;
        private String m_labelEditorLink;
        private String m_jaffaDataUrl;
        private String m_dataForward;
        private boolean m_closed;
        private String m_onload;
        private String m_onclick;
        private String m_styleId;
        private boolean m_hideIfNoWidgets;
        

        /** Constructor
         * @param id The id of the section.
         * @param style The style of the column.
         * @param dataType The dataType of the column.
         * @param labelEditorLink The labelEditorLink for the column.
         */
        public SectionHead(String id, String label, String labelEditorLink, 
                String jaffaDataUrl, String dataForward, boolean closed,
                String onload, String onclick, String styleId, boolean hideIfNoWidgets) {
            m_id = id;
            m_label = label;
            m_labelEditorLink = labelEditorLink;
            m_jaffaDataUrl = jaffaDataUrl;
            m_dataForward = dataForward;
            m_closed = closed;
            m_onload = onload;
            m_onclick = onclick;
            m_styleId = styleId;
            m_hideIfNoWidgets = hideIfNoWidgets;
        }
        
        String writeSectionTab(LayoutTag layout) {
            StringBuffer sb = new StringBuffer();
            // generating the tab
            if (layout.getLayoutType()==LayoutType.TABS || layout.getLayoutType()==LayoutType.TABSTRIP) {
                sb.append("    <div id='"+layout.getId()+"-"+getId()+"' class='"+LayoutTag.TAB_CSS_CLASS+"'");
                if (getOnclick() != null && getOnclick().length()>0)
                    sb.append(" onClick='"+getOnclick()+"'");
                if (getStyleId() != null && getStyleId().length()>0)
                    sb.append(" styleId='"+getStyleId()+"'");
                sb.append(">\n")
                .append("      <div>\n")
                .append("        <span tabindex='-1' class='jaffaTabLabelsText'>\n")
                .append(getLabelText()+"\n")
                .append("      </span>\n")
                .append("      </div>\n")
                .append("    </div>\n");
            }
            return sb.toString();
        }

        String writeSectionContent(LayoutTag layout, String bodyContentString) {
            StringBuffer out = new StringBuffer();
                out.append("      <div class='"+PANE_CSS_CLASS+"'")
                .append(" id='"+layout.getId()+"-"+getId()+"p'")
                .append(" style='display: none;'");
                if (getOnload() != null && getOnload().length()>0)
                    out.append(" onload='"+getOnload()+"'");
                if (getJaffaDataUrl()!=null && getJaffaDataUrl().length()>0) {
                    out.append(" jaffaDataUrl='"+getJaffaDataUrl());
                    if (! getJaffaDataUrl().contains("ajaxForward=") && getDataForward()!=null && getDataForward().length()>0)
                        out.append("&ajaxForward="+getDataForward());
                    out.append("'");
                }
                out.append(">\n")
                .append(bodyContentString)
                .append("\n      </div>\n");
            return out.toString();
        }
        
        String writeFoldingSectionBody(LayoutTag layout, String bodyContentString) {
            return createFoldingSection( getId(), getLabel(), getLabelEditorLink(), 
                        isClosed(), bodyContentString);
        }

        /** Getter for the attribute jaffaDataUrl.
         * @return Value of attribute jaffaDataUrl.
         */
        public String getJaffaDataUrl() {
            return m_jaffaDataUrl;
        }

        /** Getter for the attribute dataForward.
         * @return Value of attribute dataForward.
         */
        public String getDataForward() {
            return m_dataForward;
        }

        /** Getter for the attribute id.
         * @return Value of attribute id.
         */
        public String getId() {
            return m_id;
        }

        /** Getter for the editor link of attribute label.
         * @return Text value of attribute label.
         */
        public String getLabelEditorLink() {
            return m_labelEditorLink;
        }

        /** Getter for the attribute label.
         * @return Value of attribute label.
         */
        public String getLabel() {
            return m_label;
        }

        /** @return Text Value of  attribute label.
         */
        public String getLabelText() {
            return MessageHelper.replaceTokens(m_label);
        }

        /** Getter for the attribute styleId.
         * @return Value of attribute styleId.
         */
        public String getStyleId() {
            return m_styleId;
        }

        /** Getter for the attribute onclick.
         * @return Value of attribute onclick.
         */
        public String getOnclick() {
            return m_onclick;
        }

        /** Getter for the attribute onload.
         * @return Value of attribute onload.
         */
        public String getOnload() {
            return m_onload;
        }

        /** Getter for the attribute closed.
         * @return Value of attribute closed.
         */
        public boolean isClosed() {
            return m_closed;
        }

    }



    //---------------------------------------------------
    //-- Start: TLD Attributes
    //---------------------------------------------------

    /** Getter for the attribute id.
     * @return Value of attribute id.
     */
    public String getId() {
        return m_id;
    }

    /** Setter for the attribute id.
     * @param value New value of attribute id.
     */
    public void setId(String value) {
        m_id = value;
    }

    /** Getter for the attribute type.
     * @return Value of attribute type.
     */
    public String getType() {
        return m_type;
    }

    /** Setter for the attribute id.
     * @param value New value of attribute id.
     */
    public void setType(String value) {
        m_type = value;
    }

    /** Getter for the attribute sectionIds.
     * @return Value of attribute sectionIds.
     */
    public String getSectionIds() {
        return m_sectionIds;
    }

    /** Setter for the attribute sectionIds.
     * @param value New value of attribute sectionIds.
     */
    public void setSectionIds(String value) {
        m_sectionIds = value;
    }
    
    public LayoutType getLayoutType() {
        return m_layout;
    }

    
    /** Generate HTML for a folding section */
    static String createFoldingSection(String id, String label, String labelEidtorLink, boolean closed, String bodyContent)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class='foldingSectionOuter' width='100%' border='0' cellspacing='0' cellpadding='0' >\n")
        .append(" <tbody>")
        .append(" <tr>\n")
        .append(" <td colspan='3' >\n")
        .append(" <table class='foldingSectionInner' width='100%' border='0' cellspacing='0' cellpadding='0'>\n")
        .append("  <tbody>")
        .append("  <tr>\n")
        .append("  <td width='123'>&nbsp;</td>\n")
        .append("  <td width='123'>&nbsp;</td>\n")
        .append("  <td colspan='3'>&nbsp;</td>\n")
        .append("  </tr>\n")
        .append("  <tr>\n")
        .append("  <td class='sectionheader' colSpan=4 valign='bottom' width='100%'>\n")
        .append("  <table width='100%'>\n")
        .append("   <tr>\n")
        .append("   <td class='sectiontitle'>")
            .append(label)
            .append(labelEidtorLink)
            .append("</td>\n")
        .append("   <td align='right'><a id='")
            .append(id)
            .append("section' name=" + id + "section href=\"javascript:expand('")
            .append(id)
            .append("' , '")
            .append(id)
            .append("Expand')\"><img src='")
            .append(MINIMISE)
            .append("' border='0'  name='")
            .append(id)
            .append("Expand'></a></td>\n")
        .append("   </tr>\n")
        .append("  </table>\n")
        .append("  </td>\n")
        .append("  </tr>\n")
        .append("  <tr align='middle'>\n")
        .append("  <td colspan='3'>\n")
        .append("  <span id='")
            .append(id)
            .append("' name=\"")
            .append(StringHelper.convertToHTML(MessageHelper.replaceTokens(label)))
            .append("\" style='display:block' class='WidgetFoldingSection'>\n")
        .append("  <table class='outer' cellspacing='0' cellpadding='0' width='100%'>\n")
        .append("   <tr>\n")
        .append("   <td>\n")

        .append(bodyContent)
        
        .append("   </td>\n")
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
        .append(id)
        .append("'));\n");
        if (closed) {
            sb.append("initialExpand('").append(id).append("' , '").append(id).append("Expand');\n");
        } else {
            sb.append("setDefaultState('").append(id).append("' , '").append(id).append("Expand');\n");
        }
        sb.append("</script>\n");
        
        return sb.toString();
    }
        
}
