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
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Defaults;
import org.jaffa.util.MessageHelper;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.ColumnAlreadyExistsRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.TagCannotBeEnclosedRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.model.TableModel;

/** Tag Handler for the Table widget.*/
public class TableTag extends CustomModelTag implements IWidgetTag,IFormTag,IBodyTag {
    
    private static Logger log = Logger.getLogger(TableTag.class);
    private static final String TAG_NAME = "TableTag";
    
    private static final String CSS_LOCATION = "jaffa/css/widgets/tablesort.css";
    private static final String JS_LOCATION = "jaffa/js/widgets/tablesort.js";
    private static final String JS_LOCATION2 = "jaffa/js/widgets/tableresize.js";
    private static String c_header = "<script type=\"text/javascript\" src=\""
            + JS_LOCATION +"\"></script>\n" + "<script type=\"text/javascript\" src=\""
            + JS_LOCATION2 +"\"></script>";
    
    private static final String DEFAULT_WIDTH = "100%";
    private static final int DEFAULT_TABLE_BORDER = 0;
    private static final int DEFAULT_TABLE_CELL_SPACING = 0;
    private static final int DEFAULT_TABLE_CELL_PADDING = 0;
    private static final String DEFAULT_ALIGNMENT = "center";
    private static final String DEFAULT_TABLE_BG_COLOR = "465e3b";
    private static final String DEFAULT_ROLLOVER_COLOR = "C2C2C2";
    private static final String DEFAULT_SELECT_COLOR = "F4E4D1";
    
    
    // Define The Events
    /** The detail event.*/
    public static final String EVENT_DETAIL = "Clicked";
    
    
    /** property declaration for tag attribute: field.
     */
    //private String m_field;      // now inherited from CustomModelTag
    
    /** property declaration for tag attribute: align.
     */
    private String m_align;
    
    /** property declaration for tag attribute: selectcolor.
     */
    private String m_selectcolor;
    
    /** property declaration for tag attribute: rollovercolor.
     */
    private String m_rollovercolor;
    
    /** property declaration for tag attribute: multiSelect.
     */
    private boolean m_multiSelect;
    
    /** property declaration for tag attribute: detail.
     */
    private boolean m_detail;
    
    
    
    // ***  additional fields ***
    /** This field holds the columns added by the TableColumnTag */
    private List m_columns;
    
    private String m_noRecordsKey = null;
    
    
    /** This string holds the comma-separated list of existing selected-rowNums*/
    private String m_selectHidden;
    private String m_selector;
    
    /** Default constructor.
     */
    public TableTag() {
        super();
        initTag();
    }
    
    private void initTag() {
        //m_field = null;   // defined as null in super class constructor
        m_align = DEFAULT_ALIGNMENT;
        m_selectcolor = DEFAULT_SELECT_COLOR;
        m_rollovercolor = DEFAULT_ROLLOVER_COLOR;
        m_multiSelect = false;
        m_detail = false;
        m_noRecordsKey = null;
        
        m_columns = new ArrayList();
        m_selectHidden = "";
        m_selector = "";
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
    
    /** The TableColumnTag will invoke this method to add columns to the TableTag.
     * @param columnName The name of the column.
     * @param title The title of the column.
     * @param width The width of the column.
     * @param labelEditorLink The labelEditorLink for the column.
     */
    public void addColumn(String columnName, String title, String width, String labelEditorLink) {
        Column column = new Column(columnName, title, width, labelEditorLink);
        if (m_columns.contains(column) ) {
            String str = "Column " + columnName + " has already been defined";
            log.error(str);
            throw new ColumnAlreadyExistsRuntimeException(str);
        }
        m_columns.add(column);
    }
    
    /** Getter for the attribute noRecordsKey.
     * @return Value of attribute noRecordsKey.
     */
    public String getNoRecordsKey() {
        return m_noRecordsKey;
    }
    
    /** Setter for the attribute noRecordsKey.
     * @param value New value of attribute noRecordsKey.
     */
    public void setNoRecordsKey(String value) {
        m_noRecordsKey = value;
    }
    
    private String getNoRecordsText() {
        String label = "No Records Found";
        if (getNoRecordsKey() != null) {
            if (MessageHelper.hasTokens(getNoRecordsKey())) {
                label = MessageHelper.replaceTokens(pageContext, getNoRecordsKey());
            }else {
                if (MessageHelper.hasTokens("[label.Jaffa.Widgets.UserGrid.NoRecordsKey]"))
                    label = MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.UserGrid.NoRecordsKey]");
            }
        } else {
            if (MessageHelper.hasTokens("[label.Jaffa.Widgets.UserGrid.NoRecordsKey]"))
                label = MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.UserGrid.NoRecordsKey]");
        }
        return label;
    }
    //
    // methods called from doStartTag()
    //
    /** This registers the widget with the outer Form tag.
     */
    public void otherDoStartTagOperations()  {
        
        try {
            super.otherDoStartTagOperations();
        } catch (JspException e) {
            log.error("Error in TableTag.otherDoStartTagOperations(): " + e);
        }
        
        
        // The table tag cannot be enclosed withing another tag !!!
        if (TagHelper.isEnclosed(pageContext) ) {
            String str = "The " + TAG_NAME + " cannot be enclosed within another tag";
            log.error(str);
            throw new TagCannotBeEnclosedRuntimeException(str);
        }
        
        // Get the formtag from the page & register the widget
        FormTag formTag = TagHelper.getFormTag(pageContext);
        if(formTag == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
        }
    }
    
    
    /** Getter for the attribute align.
     * @return Value of attribute align.
     */
    public String getAlign() {
        return m_align;
    }
    
    /** Setter for the attribute align.
     * @param value New value of attribute align.
     */
    public void setAlign(String value) {
        m_align = value;
    }
    
    /** Getter for the attribute selectcolor.
     * @return Value of attribute selectcolor.
     */
    public String getSelectcolor() {
        return m_selectcolor;
    }
    
    /** Setter for the attribute selectcolor.
     * @param value New value of attribute selectcolor.
     */
    public void setSelectcolor(String value) {
        m_selectcolor = value;
    }
    
    /** Getter for the attribute rollovercolor.
     * @return Value of attribute rollovercolor.
     */
    public String getRollovercolor() {
        return m_rollovercolor;
    }
    
    /** Setter for the attribute rollovercolor.
     * @param value New value of attribute rollovercolor.
     */
    public void setRollovercolor(String value) {
        m_rollovercolor = value;
    }
    
    /** Getter for the attribute multiSelect.
     * @return Value of attribute multiSelect.
     */
    public boolean getMultiSelect() {
        return m_multiSelect;
    }
    
    /** Setter for the attribute multiSelect.
     * @param value New value of attribute multiSelect.
     */
    public void setMultiSelect(boolean value) {
        m_multiSelect = value;
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
    
    /** This generates the HTML for the tag.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
        
        // get the model
        TableModel model = null;
        try {
            model = (TableModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
        } catch (ClassCastException e) {
            String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }
        
        if (model != null) {
            // write out the html
            String idPrefix = getHtmlIdPrefix();
            out.println( getHtml(idPrefix, model));
        }
    }
    
    
    private String getHtml(String idPrefix, TableModel model) {
        StringBuffer sb = null;
        if (m_multiSelect) {
            sb = new StringBuffer( buildMultiScript(idPrefix) );
            sb.append( startTable(idPrefix, model) );
            sb.append( buildHeader(idPrefix, model) );
            sb.append( buildData(idPrefix, model) );
            sb.append( "</TABLE>\n" +
                    "<input id=\"" + idPrefix + "_hidden" + "\" NAME=\"" + m_field + "WV"
                    + "\" value=\"" + m_selectHidden + "\" type=\"hidden\" class=\"WidgetTable\">\n" +
                    m_selector );
        } else {
            sb = new StringBuffer( buildScript(idPrefix) );
            sb.append( buildHidden(idPrefix, model) );
            sb.append( startTable(idPrefix, model) );
            sb.append( buildHeader(idPrefix, model) );
            sb.append( buildData(idPrefix, model) );
            sb.append( "</TABLE>\n" +
                    "<input id=\"" + idPrefix + "_hidden" + "\" NAME=\"" + m_field + "WV"
                    + "\"  type=\"hidden\" class=\"WidgetTable\">\n" );
            sb.append( preSelectScript(idPrefix) );
        }
        sb.append(getWidgetRegistrationScript(idPrefix, false));
        return sb.toString();
    }
    
    
    
    private String getEventCall() {
        if( getDetail() ) {
            StringBuffer sb = new StringBuffer(" ondblclick=\"javascript:");
            String formName=TagHelper.getFormTag(pageContext).getHtmlName();
            sb.append("document.")
            .append(formName)
            .append(".eventId.value = '")
            .append(getJaffaEventNamePrefix())
            .append(';')
            .append(EVENT_DETAIL)
            .append("';")
            .append("postForm(document." + formName + ", null);\"");
            return sb.toString();
        } else
            return "";
    }
    
    
    
    private String startTable(String idPrefix , TableModel model) {
        StringBuffer sb = new StringBuffer();
        sb.append("<TABLE width=\"100%\" class=\"sort\" cellpadding=\"0\" cellspacing=\"0\" ");
        sb.append("id=\"" + idPrefix + "_table\" ");
        sb.append("style=\"");
        if (countRows(model) > 0) {
            sb.append("behavior:url(jaffa/htc/widgets/table.htc);");
        }
        sb.append(" border:" + DEFAULT_TABLE_BORDER + ";");
        sb.append(" bgcolor:" + DEFAULT_TABLE_BG_COLOR + ";");
        sb.append(" cellspacing:" + DEFAULT_TABLE_CELL_SPACING + ";");
        sb.append(" cellpadding:" + DEFAULT_TABLE_CELL_PADDING + ";");
        sb.append((m_align == null ? "" : " align:" + m_align + ":\""));
        if (countRows(model) > 0) {
            sb.append("borderColor=\"#999999\" cellSpacing=\"0\" cellPadding=\"2\" border=\"1\" dragcolor='gray' slcolor=" + m_selectcolor + " hlcolor=" + m_rollovercolor + ">\n");
        } else {
            sb.append(">\n");
        }
        return sb.toString();
    }
    
    private String buildScript(String idPrefix) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>\n");
        sb.append("   var " + idPrefix + "_lastClick = \"undefined\";\n");
        sb.append("   var " + idPrefix + "_originalColor = \"undefined\";\n");
        sb.append("   function " + idPrefix + "_changeto(TRow , highlightColor , row){\n");
        sb.append("   if ((" + idPrefix + "_lastClick != \"undefined\")) {\n");
        sb.append("       " + idPrefix + "_lastClick.style.backgroundColor=" + idPrefix + "_originalColor;\n");
        sb.append("   }\n");
        sb.append("   " + idPrefix + "_originalColor = TRow.style.backgroundColor;\n");
        sb.append("   TRow.style.backgroundColor=highlightColor;\n");
        sb.append("   " + idPrefix + "_lastClick = TRow;\n");
        sb.append("   document.getElementById('"+ idPrefix + "_hidden').value = row;\n");
        sb.append("   }\n");
        sb.append("</script>\n");
        return sb.toString();
    }
    
    private String buildMultiScript(String idPrefix) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>\n");
        sb.append("var myRow = \"\";");
        sb.append("   function " + idPrefix + "_changeto(TRow , highlightColor , row){\n");
        sb.append("	// work out what rows have been highlighted \n");
        sb.append("	if (document.getElementById('" + idPrefix + "_hidden').value !=\"\"){\n");
        sb.append("		myRow = \",\" + document.getElementById('" + idPrefix + "_hidden').value + \",\";\n");
        sb.append("	}\n");
        sb.append("   rExp = row + \",\";\n");
        sb.append("	results = myRow.search(',' + rExp) \n");
        sb.append("	var highlight = false;\n");
        sb.append("	if (results != \"-1\") { \n");
        sb.append("	highlight = false;\n");
        sb.append("		myRow = myRow.replace(rExp  , \"\");\n");
        sb.append("	} else {\n");
        sb.append("		highlight = true; \n");
        sb.append("	}\n");
        sb.append("	var start = 0; \n");
        sb.append("	var end = myRow.length;\n ");
        sb.append("	if (myRow.slice(0,1) == \",\") {\n ");
        sb.append("		start = 1;\n");
        sb.append("   }\n");
        sb.append("	if (myRow.slice(end - 1 , end) == \",\") {\n");
        sb.append("		end = end - 1;\n");
        sb.append("	}\n");
        sb.append("   myRow = myRow.slice(start,end);\n");
        sb.append("	document.getElementById('" + idPrefix + "_hidden').value = myRow;\n");
        sb.append("	if (highlight) {");
        sb.append("		TRow.style.backgroundColor=highlightColor;\n ");
        sb.append("	} else {\n ");
        sb.append("	 	TRow.style.backgroundColor	=	document.getElementById(\"" + idPrefix + "_table\").style.backgroundColor;\n");
        sb.append("	} \n");
        sb.append("	if (highlight) { \n");
        sb.append("	if (document.getElementById('" + idPrefix + "_hidden').value != \"\") {\n ");
        sb.append(" 			document.getElementById('" + idPrefix + "_hidden').value = document.getElementById('" + idPrefix + "_hidden').value + \",\" + row ;\n");
        sb.append("		} else {\n");
        sb.append("			document.getElementById('" + idPrefix + "_hidden').value = document.getElementById('" + idPrefix + "_hidden').value +  row ;\n");
        sb.append("		}\n");
        sb.append("	}");
        sb.append("}");
        sb.append("</script>\n");
        return sb.toString();
    }
    
    private String preSelectScript(String idPrefix) {
        if (m_selectHidden.length() > 0) {
            return "<SCRIPT>\n" +
                    "    var " + idPrefix + "_current = document.getElementById(\"" + m_field + "_" + m_selectHidden + "\"); \n" +
                    "    " + idPrefix + "_changeto(" + idPrefix + "_current , '" + m_selectcolor + "' , " + m_selectHidden  + "); \n" +
                    "</script>";
        } else {
            return "";
        }
    }
    
    /** Build the header section of the table. It looks like
     *
     *  <THEAD>
     *      <TR>
     *          <TD style="width: 60px;" type="String">String</TD>
     *          <TD style="width: 60px;" type="CaseInsensitiveString" title="CaseInsensitiveString">String</TD>
     *          <TD style="width: 60px;" type="Number">Number</TD>
     *          <TD style="width: 60px;" type="Date">Date</TD>
     *      </TR>
     *  </THEAD>
     */
    private String buildHeader(String idPrefix , TableModel model) {
        StringBuffer b = new StringBuffer();
        b.append("<THEAD class=\"sort\">\n");
        b.append("  <TR class=\"sort\">\n");

        for (Object m_column : m_columns) {
            Column c = (Column) m_column;

            // Work out the type
            try {
                String type = model.getColumnDataType(c.getName());
                String dataType = null;
                if (type != null) {
                    if (type.equals(Defaults.CURRENCY) || type.equals(Defaults.DECIMAL) || type.equals(Defaults.INTEGER)) {
                        dataType = "Number";
                    }
                    else if (type.equals(Defaults.DATEONLY) || type.equals(Defaults.DATETIME)) {
                        dataType = "Date";
                    }
                    else {
                        dataType = "CaseInsensitiveString";
                    }
                }
                // <TD style="width: 60px;" type="String">String</TD>
                b.append("    <TD nowrap id=\"" + idPrefix + c.getTitle() + "\" class=\"sort\" " + (
                        c.getWidth() == null ? "" : " width=\"" + c.getWidth()) + "\"" + (dataType == null ?
                                                                                          "" :
                                                                                          " type=\"" + dataType + "\"")
                         + ">" + c.getTitle() + c.getLabelEditorLink() + "</TD>\n");

            }
            catch (Exception e) {
                // Field Is Not Valid
                b.append("    <TD nowrap class=\"sort\" >???</TD>\n");
            }
        }
        
        b.append("  </TR>\n");
        b.append("</THEAD>\n");
        return b.toString();
    }
    
    
    private int countRows(TableModel model) {
        int rowNum = 0;
        if (model != null) {
            rowNum = model.getRows().size();
        }
        if (log.isDebugEnabled()) log.debug("the number of rows are -" + rowNum);
        return rowNum;
    }
    
    
    /** Build the hidden section of the table. It looks like
     *
     *  <THEAD>
     *      <TR>
     *          <TD style="width: 60px;" type="String">String</TD>
     *          <TD style="width: 60px;" type="CaseInsensitiveString" title="CaseInsensitiveString">String</TD>
     *          <TD style="width: 60px;" type="Number">Number</TD>
     *          <TD style="width: 60px;" type="Date">Date</TD>
     *      </TR>
     *  </THEAD>
     */
    private String buildHidden(String idPrefix ,TableModel model) {
        StringBuffer b = new StringBuffer();
        for(Iterator itr = m_columns.iterator(); itr.hasNext(); ) {
            Column c = (Column) itr.next();
            
            // Work out the type
            String type;
            try {
                // <INPUT type="hidden" id="">>
                b.append("    <INPUT type=\"hidden\" id=\"" + idPrefix + c.getTitle() + "_size\" name=\"" + idPrefix + c.getTitle() + "_size\">\n");
                
            } catch (Exception e) {
                // Field Is Not Valid
            }
        }
        return b.toString();
    }
    
    
    
    private String buildData(String idPrefix, TableModel model) {
        // Build a list of indexes which is the order to extract the fields in....
        List sourceColumns = model.getColumnNames();
        List order = new ArrayList();
        StringBuffer b = new StringBuffer();
        m_selector = "<script>";
        m_selectHidden = "";
        b.append("<TBODY class=\"sort\">\n");
        
        // Loop thru display columns
        int colNum = 0;
        for(Iterator itr = m_columns.iterator(); itr.hasNext(); ) {
            colNum++;
            Column c = (Column) itr.next();
            order.add( new Integer( sourceColumns.indexOf(c.getName()) ) );
        }
        
        int rowNum = -1;
        List selectedRows = model.getSelectedRows();
        for(Iterator itr = model.getRows().iterator(); itr.hasNext(); ) {
            rowNum++;
            List row = (List) itr.next();
            if (selectedRows.size() > 0 && selectedRows.contains(row)) {
                if (m_selectHidden.length() == 0) {
                    m_selectHidden = m_selectHidden + rowNum;
                } else {
                    m_selectHidden = m_selectHidden + ',' +  rowNum;
                }
                
                b.append("  <TR class=\"" + ((rowNum % 2) == 1 ? "altsort" : "sort" ) + "\" id=\"" + m_field + "_" + rowNum + "\" onClick=\"" + idPrefix + "_changeto(this , '" + m_selectcolor + "', " + rowNum + ")\"" + getEventCall() + ">\n");
                m_selector = m_selector + "document.getElementById(\"" + m_field + "_" + rowNum + "\").style.backgroundColor =\""+ m_selectcolor +"\";";
            } else {
                b.append("  <TR class=\"" + ((rowNum % 2) == 1 ? "altsort" : "sort" ) + "\" id=\"" + m_field + "_" + rowNum + "\" onClick=\"" + idPrefix + "_changeto(this , '" + m_selectcolor + "', " + rowNum + ")\"" + getEventCall() + ">\n");
            }
            
            for(Iterator i3 = order.iterator(); i3.hasNext(); ) {
                int index = ( (Integer) i3.next() ).intValue();
                Object value = row.get(index);
                b.append("    <TD class=\"sort\">" + (value != null ? value.toString() : TagHelper.HTML_SPACE_CHARACTER)+ "</TD>\n");
            }
            
            b.append("  </TR>\n");
        }
        
        if (rowNum == -1) {
            b.append("<TR class=\"sort\">    <TD class=\"sort\" colspan=\"" + colNum + "\" align=\"center\">" + getNoRecordsText() + "</TD></TR>");
        }
        b.append("</TBODY>\n");
        m_selector = m_selector + "</script>";
        return b.toString();
    }
    
    
    /** An instance of this class will be created for each TableColumn.
     */
    private static class Column {
        private String m_name;
        private String m_title;
        private String m_width;
        private String m_labelEditorLink;
        
        /** Constructor
         * @param name The name of the column.
         * @param title The title of the column.
         * @param width The width of the column.
         * @param labelEditorLink The labelEditorLink for the column.
         */
        public Column(String name, String title, String width, String labelEditorLink) {
            m_name = name;
            m_title = title;
            m_width = width;
            m_labelEditorLink = labelEditorLink;
        }
        
        /** Getter for the attribute name.
         * @return Value of attribute name.
         */
        public String getName() {
            return m_name;
        }
        
        /** Getter for the attribute title.
         * @return Value of attribute title.
         */
        public String getTitle() {
            return m_title;
        }
        
        /** Getter for the attribute width.
         * @return Value of attribute width.
         */
        public String getWidth() {
            return m_width;
        }
        
        /** Getter for the attribute labelEditorLink.
         * @return Value of attribute labelEditorLink.
         */
        public String getLabelEditorLink() {
            return m_labelEditorLink;
        }
        
        /** Compares this object against another column object.
         * Returns a true if they have the same name.
         * @param obj The other Column object.
         * @return a true if the 2 objects have the same name.
         */
        public boolean equals(Object obj) {
            boolean result = false;
            if(obj instanceof Column) {
                Column column = (Column) obj;
                result = m_name == null ? column.m_name == null : m_name.equals(column.m_name);
            }
            return result;
        }
        
        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            return "Name=" + m_name + ", Title=" + m_title + ", Width=" + m_width;
        }
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
