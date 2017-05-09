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
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import java.util.Map;
import javax.servlet.jsp.tagext.*;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterLayoutTagMissingRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.MessageHelper;


/** Tag Handler for the Section widget.*/
public class SectionTag extends CustomTag implements IWidgetTag,IFormTag,IBodyTag {

    private static Logger log = Logger.getLogger(SectionTag.class);
    private static final String TAG_NAME = "SectionTag";

    //----------------------------------------------
    //-- TLD Attributes
     /** The value for this field will be computed based on m_label */
    private String m_labelEditorLink;
   /** property declaration for tag attribute: label. */
    private String m_label;
    /** property declaration for tag attribute: id.*/
    private String m_id;
    /** property declaration for tag attribute: jaffaDataUrl.*/
    private String m_jaffaDataUrl;
    /** property declaration for tag attribute: dataForward.*/
    private String m_dataForward;
    /** property declaration for tag attribute: closed.*/
    private boolean m_closed = false;
    /** property declaration for tag attribute: onload.*/
    private String m_onload;
    /** property declaration for tag attribute: onclick.*/
    private String m_onclick;
    /** property declaration for tag attribute: styleId.*/
    private String m_styleId;
    /** property declaration for tag attribute: hideIfNoWidgets.*/
    private boolean m_hideIfNoWidgets = true;
    //----------------------------------------------

    /** This variable contains the parent (enclosing) tag. */
    private LayoutTag m_layoutTag;
    /** Store the original state of the pagecontext attributes */
    private Map m_originalAttributes;
    /** Set to true when the first widget registers with this section */
    private boolean m_containsContent = false;
    
    /** Default constructor. */
    public SectionTag() {
        super();
        initTag();
    }

    private void initTag() {
        m_label = m_id = m_jaffaDataUrl = m_dataForward = m_onload = m_onclick = null;
        m_labelEditorLink = "";
        m_closed = false;
    }

    /** Needed so we can see if this section contains any other widgets */
    public void register(ICustomTag tag) {
        super.register(tag);
        if (tag instanceof IWidgetTag ) {
            if (log.isDebugEnabled() && !m_containsContent )
                log.debug(this.NAME+".register: IWidget component registered as visible");
            m_containsContent=true;
        }
    }

    /** Find the outer layout if there is one. */
    public void otherDoStartTagOperations() throws JspException {

        // This will register this column with the grid.
        super.otherDoStartTagOperations();

        // Store the Original Attributes
        m_originalAttributes = TagHelper.getOriginalAttributes(pageContext);

        // Preprocess if within a Property widget
        lookupPropertyTag();

        // raise an error, if the 'field' is null
        if (getLabel() == null) {
            String str = "The " + TAG_NAME + " requires 'label' parameter to be supplied or it should be within a PropertyTag";
            log.error(str);
            throw new MissingParametersRuntimeException(str);
        }
              
        // See if it has a layout
        if (m_layoutTag == null)
            m_layoutTag = (LayoutTag) findCustomTagAncestorWithClass(this, LayoutTag.class);
        if (m_layoutTag == null)
            log.debug("No Layout for section, assume folding tag");
    }


    /** This generates the HTML for the tag, unless there is a layout, in which case it is sent there.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //log.debug("writeTagBodyContent: " + this.toString());
        if (m_layoutTag != null) {
            m_layoutTag.addSectionContent(m_id,bodyContent.getString());
        } else {
            // Generate a folding section
            if (m_containsContent || (!m_hideIfNoWidgets)) {
                out.print( LayoutTag.createFoldingSection( getId(), getLabel(), getLabelEditorLink(), 
                        isClosed(), bodyContent.getString()));
            }
        }
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }


    /** Checks for the nearest outer PropertyTag.
     * Will set the 'label' on this tag, if not specified, with the value from the rules engine.
     */
    private void lookupPropertyTag() {
        if (m_label == null) {
            PropertyTag propertyTag = (PropertyTag) findCustomTagAncestorWithClass(this, PropertyTag.class);
            if (propertyTag != null) {
                if (log.isDebugEnabled())
                    log.debug("Found Property Tag for Label. Field=" + propertyTag.getField());
                IPropertyRuleIntrospector propertyRuleIntrospector = propertyTag.getPropertyRuleIntrospector();
                if (propertyRuleIntrospector != null) {
                    try {
                        // Wrap the introspector with the FieldMetaData
                        FieldMetaData fieldMetaData = TagHelper.getFieldMetaData(propertyTag.getPropertyClass(), propertyTag.getPropertyName());
                        propertyRuleIntrospector = new PropertyRuleIntrospectorUsingFieldMetaData(propertyRuleIntrospector, fieldMetaData);
                    } catch (Exception e) {
                        // do nothing
                    }
                    if (log.isDebugEnabled())
                        log.debug("Found Label via Rules (Field=" + propertyTag.getField()+") = " + propertyRuleIntrospector.getLabel());
                    String label = propertyRuleIntrospector.getLabel();
                    if (label == null)
                        label = LabelTag.ERROR_LABEL;
                    setLabel(label);
                }
            }
        }
    }


    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        super.doFinally();
        initTag();
        TagHelper.restoreOriginalAttributes(pageContext, m_originalAttributes);
        m_originalAttributes = null;
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

    /** getter for the attribute closed
     */
    public boolean isClosed() {
        return m_closed;
    }

    /** setter for the attribute closed
     */
    public void setClosed(boolean value) {
        m_closed = value;
    }

    /** Getter for the attribute label. This returns the tokenized translated
     * label suitable for outputing on the JSP. To get the original value set on
     * the JSP see getLabelKey()
     */
    public String getLabel() {
        if (m_label == null)
            lookupPropertyTag();
        return m_label;
    }

    /** Setter for the attribute label.
     * @param value New value of attribute label.
     */
    public void setLabel(String value) {
        m_label = value;
        if (MessageHelper.isSingleToken(value))
            m_labelEditorLink = TagHelper.getLabelEditorLink(pageContext, value);
        else
            m_labelEditorLink = null;
    }

    /** Getter for the html to link to the label editor, if enabled.
     */
    String getLabelEditorLink() {
        return m_labelEditorLink;
    }

   
    /** Getter for the attribute jaffaDataUrl.
     * @return Value of attribute jaffaDataUrl.
     */
    public String getJaffaDataUrl() {
        return m_jaffaDataUrl;
    }


    /** Setter for the attribute jaffaDataUrl.
     * @param value New value of attribute jaffaDataUrl.
     */
    public void setJaffaDataUrl(String value) {
        m_jaffaDataUrl = value;
    }

    /** Getter for the attribute dataForward.
     * @return Value of attribute dataForward.
     */
    public String getDataForward() {
        return m_dataForward;
    }


    /** Setter for the attribute dataForward.
     * @param value New value of attribute dataForward.
     */
    public void setDataForward(String value) {
        m_dataForward = value;
    }

    /** Getter for the attribute onload.
     * @return Value of attribute onload.
     */
    public String getOnload() {
        return m_onload;
    }


    /** Setter for the attribute onload.
     * @param value New value of attribute onload.
     */
    public void setOnload(String value) {
        m_onload = value;
    }

    /** Getter for the attribute onclick.
     * @return Value of attribute onclick.
     */
    public String getOnclick() {
        return m_onclick;
    }

    /** Setter for the attribute onclick.
     * @param value New value of attribute onclick.
     */
    public void setOnclick(String value) {
        m_onclick = value;
    }

    /** Getter for the attribute styleId.
     * @return Value of attribute styleId.
     */
    public String getStyleId() {
        return m_styleId;
    }

    /** Setter for the attribute styleId.
     * @param value New value of attribute styleId.
     */
    public void setStyleId(String value) {
        m_styleId = value;
    }

    public boolean isHideIfNoWidgets() {
        return m_hideIfNoWidgets;
    }
    
    public void setHideIfNoWidgets(boolean hideIfNoWidgets) {
        m_hideIfNoWidgets = hideIfNoWidgets;
    }

    //---------------------------------------------------

    /** Display the tag name and label */
    public String toString() {
        String x=super.toString();
        return (new StringBuffer(x==null?"<??? ":x.substring(0,x.length()-2)))
        .append(" label='")
        .append(m_label)
        .append("'>")
        .toString();
    }

}
