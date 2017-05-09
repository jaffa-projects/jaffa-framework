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

import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterGridTagMissingRuntimeException;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.MissingParametersRuntimeException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.MessageHelper;


/** Tag Handler for the GridColumn widget.*/
public class GridColumnTag extends CustomTag implements IWidgetTag,IFormTag,IBodyTag {

    private static Logger log = Logger.getLogger(GridColumnTag.class);
    private static final String TAG_NAME = "GridColumnTag";

    //----------------------------------------------
    //-- TLD Attributes
    /** property declaration for tag attribute: label. */
    private String m_label;
    /** property declaration for tag attribute: dataType.*/
    private String m_dataType;
    /** property declaration for tag attribute: treeField.*/
    private String m_treeField;
    /** property declaration for tag attribute: treeSpacer.*/
    private String m_treeSpacer;
    /** property declaration for tag attribute: key.*/
    private boolean m_key;
    /** property declaration for tag attribute: columnCssClass.*/
    private String m_columnCssClass;
    /** property declaration for tag attribute: style.*/
    private String m_style;
    /** property declaration for tag attribute: required.*/
    private boolean m_required;
    //----------------------------------------------

    /** This variable contains the parent (enclosing) tag. */
    private GridTag m_gridTag;
    /** Store the original state of the pagecontext attributes */
    private Map m_originalAttributes;
    /** The value for this field will be computed based on m_label */
    private String m_labelEditorLink;
    /** This value is a widget class name if there is only one widget in the cell, 
     * null if no widgets are found, or empty string if multiple widgets are found. 
     */
    private String m_singleWidgetType;

    /** Default constructor. */
    public GridColumnTag() {
        super();
        initTag();
    }

    private void initTag() {
        m_label = null;
        m_gridTag = null;
        m_dataType = null;
        m_treeField = null;
        m_treeSpacer = TagHelper.HTML_SPACE_CHARACTER;
        m_labelEditorLink = "";
        m_key = false;
        m_columnCssClass = null;
        m_style = null;
        m_singleWidgetType = null;
    }


    /** Adds a ColumnHead to the outer GridTag. */
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

        // GridTag should be present
        if (m_gridTag == null)
            m_gridTag = (GridTag) findCustomTagAncestorWithClass(this, GridTag.class);
        if (m_gridTag == null) {
            String str = "The " + TAG_NAME + " for label " + getLabel() + " should be inside a GridTag";
            log.error(str);
            throw new OuterGridTagMissingRuntimeException(str);
        }


    }


    /** Returns a true, if the GridModel has rows.
     * @return a true, if the GridModel has rows.
     */
    public boolean theBodyShouldBeEvaluated()  {
        Map map = TagHelper.getModelMap(pageContext);
        if (map == null || map.size() == 0 || m_gridTag.isColumnHidden(getLabel())) {
            //log.debug("theBodyShouldBeEvaluated: DON'T Evaluate " + this.toString());
            return false;
        } else
            return true;
    }


    /** This generates the HTML for the tag.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //log.debug("writeTagBodyContent: " + this.toString());
        if(!m_gridTag.isColumnHidden(getLabel())) {
            StringBuffer sb = new StringBuffer();
            String bodyContentString = bodyContent.getString();
            sb.append(getColumnStartHtml());

            //
            // write the body content (after processing by the JSP engine) on the output Writer
            //
            if (m_treeField!=null) {
                boolean isDisplayed = false;
                GridModelRow row = (GridModelRow) m_gridTag.currentGridRow();
                Integer level = (Integer) row.get("level");
                Boolean isExpanded = (Boolean)row.get("isExpanded");
                Boolean isParent = (Boolean)row.get("isParent");
                if (row.get("isDisplayed") != null)
                    isDisplayed = ((Boolean)row.get("isDisplayed")).booleanValue();
                if (log.isDebugEnabled()) log.debug("Tree Row " + m_gridTag.getRowNumber() + " : isDisplayed="+row.get("isDisplayed")+" ["+isDisplayed+"]");
                String lastLevelId = m_gridTag.retrieveLastLevelId(level);
                String newLevelId = new String();
                if (lastLevelId != null) {
                    int levelIndex = lastLevelId.lastIndexOf("-");
                    if (lastLevelId.lastIndexOf("-") != -1) {
                        Integer lastDigits = new Integer(lastLevelId.substring((levelIndex + 1), lastLevelId.length()));
                        if (log.isDebugEnabled()) log.debug("levelindex is " + levelIndex + " length is" + lastLevelId.length() + "last levelId is " + lastLevelId);
                        if (lastDigits != null) {
                            lastDigits = new Integer(lastDigits.intValue() + 1);
                        }
                        Integer lastLevel = new Integer(level.intValue() - 1);
                        String parentLevelId = m_gridTag.retrieveLastLevelId(lastLevel);
                        if (parentLevelId != null && !parentLevelId.equals(lastLevelId.substring(0,levelIndex))) {
                            newLevelId = parentLevelId  + "-1";
                        } else {
                            newLevelId = lastLevelId.substring(0,levelIndex) + "-" + lastDigits ;
                        }
                    } else {
                        newLevelId = "" + (new Integer(lastLevelId).intValue() + 1);
                    }
                } else {
                    if (level.intValue() > m_gridTag.ROOT_LEVEL) {
                        Integer lastLevel = new Integer(level.intValue() - 1);
                        String parentLevelId = m_gridTag.retrieveLastLevelId(lastLevel);
                        newLevelId = parentLevelId  + "-1";

                    } else {
                        if (log.isDebugEnabled()) log.debug("level is " + level + " and that first level was fired");
                        newLevelId = "" + m_gridTag.ROOT_LEVEL;
                    }
                }
                if (log.isDebugEnabled()) log.debug("the new levelid is " + newLevelId);
                m_gridTag.updateLastLevelId(level, newLevelId);
                for (int i=1 ; i < level.intValue() ; i++) {
                    sb.append(m_treeSpacer);
                }
                newLevelId = m_gridTag.getField() + ":" + newLevelId;
                if (isParent != null && isParent.booleanValue()) {
                    if (isExpanded.booleanValue()) {
                        sb.append("<script>currentState.addExpanded('")
                        .append(newLevelId)
                        .append("-')</script><a")
                        .append( (m_gridTag.getIsTarget() ? " id='rowTarget' " : "" ) )
                        .append(" style='text-decoration:none' href='javascript:void(0);' onclick=\"toggleRows(this ,'")
                        .append(m_gridTag.getServerSideCall())
                        .append("');return false\" nowrap><img src=\"jaffa/imgs/tree/contractLevel.gif\" border=\"0\"></a>");
                    } else {
                        sb.append("<a")
                        .append( (m_gridTag.getIsTarget() ? " id='rowTarget' " : "" ) )
                        .append(" style='text-decoration:none' href='javascript:void(0);' onclick=\"toggleRows(this ,'")
                        .append(m_gridTag.getServerSideCall())
                        .append("');return false\" nowrap><img src=\"jaffa/imgs/tree/expandLevel.gif\" border=\"0\"></a>");
                    }
                } else {
                    sb.append("<a")
                    .append( (m_gridTag.getIsTarget() ? " id='rowTarget' " : "" ) )
                    .append("><img src=\"jaffa/imgs/tree/childItem.gif\" border=\"0\" nowrap></a>");
                }
                m_gridTag.setCurrentLevelId(newLevelId);
                m_gridTag.setRowDisplayed(isDisplayed);
            }

            if (bodyContentString == null || bodyContentString.trim().length() == 0)
                sb.append(TagHelper.HTML_SPACE_CHARACTER);
            else
                sb.append(bodyContentString.trim());
            sb.append(getColumnEndingHtml())
            .append('\n');
            m_gridTag.addColumnContentsToStack(getLabel(), sb.toString());

            // clear the body content for the next time through.
            bodyContent.clearBody();
        }
        //else
        //    log.debug("No content for hidden column " + getLabel());
    }

    private String getColumnStartHtml() {
        StringBuffer out = new StringBuffer();
        if (UserGridTag.OUTPUT_TYPE_WEB_PAGE.equals(m_gridTag.getOutputType())) {
            out.append("<td");
            String css=m_gridTag.getColumnCssClass();
            if (m_singleWidgetType!=null && m_singleWidgetType.length()>0) {
                String tmpStr = "grid" + m_singleWidgetType.substring(m_singleWidgetType.lastIndexOf('.')+1).replace("Tag", "");
                if (css==null || css.length()==0) 
                    css = tmpStr;
                else
                    css += " " + tmpStr;
            }
            if(css!= null)
                out.append(" class=\"").append(css).append("\"");
            if(m_treeField!=null)
                out.append(" nowrap");
            out.append(getStyleAttribute());
            if( m_gridTag.getPopupHints() )
                out.append(" onMouseOver=\"try{doTooltip(event,this);} catch (err) {}\" onMouseOut=\"try {hideTip()} catch (err) {}\"");
//                out.append(" onmouseover=\"try{doTooltip(event,this.innerHTML,'")
//                   .append(getLabel())
//                   .append("','")
//                   .append(m_gridTag.getHtmlIdPrefix())
//                   .append("' ,'")
//                   .append(m_gridTag.getRowNumber())
//                   .append("','")
//                   .append(m_gridTag.getKeyColumns())
//                   .append("','")
//                   .append(m_gridTag.getColumnTitle(m_label))
//                   .append("')} catch (err) {}\" onmouseout=\"try {hideTip()} catch (err) {}\"");
            out.append(">");
        } else if (UserGridTag.OUTPUT_TYPE_EXCEL.equals(m_gridTag.getOutputType()))
            out.append("<td ")
            .append((m_dataType.equals("CaseInsensitiveString") ? "x:str" : ""))
            .append(">");
        else if (UserGridTag.OUTPUT_TYPE_XML.equals(m_gridTag.getOutputType()))
            out.append('<')
            .append(getTdForXmlDisplayType())
            .append('>');
        return out.toString();
    }

    private String getColumnEndingHtml() {
        String out = null;
        if (UserGridTag.OUTPUT_TYPE_WEB_PAGE.equals(m_gridTag.getOutputType()) || UserGridTag.OUTPUT_TYPE_EXCEL.equals(m_gridTag.getOutputType()))
            out = "</td>";
        else if (UserGridTag.OUTPUT_TYPE_XML.equals(m_gridTag.getOutputType()))
            out = "</" + getTdForXmlDisplayType() + '>';
        return out;
    }


    private String getStyleAttribute() {
        if(getStyle()==null)
            return "";
        else
            return " style='" + getStyle() + "'";
    }

    private String getTdForXmlDisplayType() {
        return getLabel().replace(' ', '-').toLowerCase();
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

    /** Getter for the html to link to the label editor, if enabled.
     */
    String getLabelEditorLink() {
        return m_labelEditorLink;
    }


    //---------------------------------------------------
    //-- Start: TLD Attributes
    //---------------------------------------------------

    /** Getter for the attribute dataType.
     * @return Value of attribute dataType.
     */
    public String getDataType() {
        return m_dataType;
    }


    /** Setter for the attribute dataType.
     * @param value New value of attribute dataType.
     */
    public void setDataType(String value) {
        m_dataType = value;
    }

    /** getter for the attribute key
     */
    public boolean isKey() {
        return m_key;
    }

    /** setter for the attribute key
     */
    public void setKey(boolean value) {
        m_key = value;
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
        if (value != null) {
            m_label = value;
            if (MessageHelper.hasTokens(value))
                m_labelEditorLink = TagHelper.getLabelEditorLink(pageContext, value);
        } else
            m_label = value;
    }

    /** Setter for the attribute treeField.
     * @param value New value of attribute treeField.
     */
    public void setTreeField(String value) {
        m_treeField = value;
    }

    /** Getter for the attribute treeField.
     * @return Value of attribute treeField.
     */
    public String getTreeField() {
        return m_treeField;
    }

    /** Setter for the attribute treeSpacer.
     * @param value New value of attribute treeSpacer.
     */
    public void setTreeSpacer(String value) {
        m_treeSpacer = value;
    }

    /** Getter for the attribute treeSpacer.
     * @return Value of attribute treeSpacer.
     */
    public String getTreeSpacer() {
        return m_treeSpacer;
    }

    /** Setter for the attribute columnCssClass.
     * @param value New value of attribute columnCssClass.
     */
    public void setColumnCssClass(String value) {
        if(value!=null && value.trim().length()==0)
            value=null;
        m_columnCssClass = value;
    }

    /** Getter for the attribute columnCssClass.
     * @return Value of attribute columnCssClass.
     */
    public String getColumnCssClass() {
        return m_columnCssClass;
    }

    /** Setter for the attribute style.
     * @param value New value of attribute style.
     */
    public void setStyle(String value) {
        if(value!=null && value.trim().length()==0)
            value=null;
        m_style = value;
    }

    /** Getter for the attribute style.
     * @return Value of attribute style.
     */
    public String getStyle() {
        return m_style;
    }

    /**
     * Getter for property required.
     * @return Value of property required.
     */
    public boolean isRequired() {
        return m_required;
    }

    /**
     * Setter for property required.
     * @param required New value of property required.
     */
    public void setRequired(boolean required) {
        m_required = required;
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

    public void register(ICustomTag tag) {
        super.register(tag);
        // register the widget class name if there is only one widget in the cell
        if (m_singleWidgetType == null) {
            m_singleWidgetType = tag.getClass().getName();
            if (log.isDebugEnabled())
                log.debug(this.NAME+".register: Found CheckBox");
        } else if (!m_singleWidgetType.equals(tag.getClass().getName()))
            m_singleWidgetType = "";
    }
    
}
