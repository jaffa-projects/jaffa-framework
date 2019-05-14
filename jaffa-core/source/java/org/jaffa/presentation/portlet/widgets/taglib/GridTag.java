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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.metadata.DateTimeFieldMetaData;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.presentation.portlet.widgets.controller.UserGridManager;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.JspWriteRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.OuterFormTagMissingRuntimeException;
import org.jaffa.presentation.portlet.widgets.taglib.exceptions.TagCannotBeEnclosedRuntimeException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

/** Tag Handler for the Grid widget.*/
public class GridTag extends CustomModelTag implements IWidgetTag,IFormTag,IBodyTag {

    private static Logger log = Logger.getLogger(GridTag.class);

    /** A global constant for the Web Page display type.*/
    public final static String OUTPUT_TYPE_WEB_PAGE = "WebPage";
    /** A global constant for the Excel display type.*/
    public final static String OUTPUT_TYPE_EXCEL = "Excel";
    /** A global constant for the XML display type.*/
    public final static String OUTPUT_TYPE_XML = "Xml";
    /** The "double click on a row" event */
    public static final String EVENT_DETAIL = "Clicked";

    public static final String RULE_USERGRID_POPUP = "jaffa.widgets.usergrid.showtips";
    public static final String RULE_GRID_POPUP = "jaffa.widgets.grid.showtips";

    private static final String TAG_NAME = "GridTag";
    private static final String COLUMN_NAME_PREFIX = "c";
    private static final String NO_RECORDS_LABEL = "[label.Jaffa.Widgets.UserGrid.NoRecordsKey]";

    //private static final String CSS_LOCATION = "jaffa/css/widgets/tablesort.css";

    private static final String JS_COMMON =
            "<script type='text/javascript' src='jaffa/js/widgets/gridMain.js'></script>\n" +
            "<script type='text/javascript' src='jaffa/js/widgets/gridSort.js'></script>\n" +
            "<script type='text/javascript' src='jaffa/js/widgets/gridPopupHints.js'></script>\n" +
            "<script type='text/javascript' src='jaffa/js/widgets/gridConfigure.js'></script>\n" +
            "<script type='text/javascript' src='jaffa/js/widgets/gridTree.js'></script>\n";
    private static final String JS_LOCALE_EN_US = JS_COMMON +
            "<script type='text/javascript' src='jaffa/js/widgets/gridSort_us.js'></script>\n";
    private static final String JS_LOCALE_EN_GB = JS_COMMON +
            "<script type='text/javascript' src='jaffa/js/widgets/gridSort_gb.js'></script>\n";

    //Variable names used in the JavaScript
    private static final String HTML_ID_AVAILABLE = "_availableCols";
    private static final String HTML_ID_SELECTED = "_selectedCols";

    //Default tags for the XML
    private static final String XML_ROOT = "Root";
    private static final String XML_ROW = "Row";

    //----------------------------------------------
    //-- TLD Attributes
    /** property declaration for tag attribute: cssClass. */
    private String m_cssClass;
    /** property declaration for tag attribute: headerCssClass. */
    private String m_headerCssClass;
    /** property declaration for tag attribute: rowCssClasses. */
    private String m_rowCssClasses;
    /** property declaration for tag attribute: columnCssClasses. */
    private String m_columnCssClasses;
    /** property declaration for tag attribute: detail. */
    private boolean m_detail;
    /** property declaration for tag attribute: displayOnly. */
    private boolean m_displayOnly;
    /** property declaration for tag attribute: noRecordsKey. */
    private String m_noRecordsKey = null;
    /** property declaration for tag attribute: outputType.*/
    private String m_outputType;
    /** property declaration for tag attribute: popupHints.*/
    private boolean m_popupHints;
    /** property declaration for tag attribute: target.*/
    private String m_target;
    /** property declaration for tag attribute: userGridId.*/
    private String m_userGridId;
    /** property declaration for tag attribute: style.*/
    private String m_style;
    //----------------------------------------------


    /** Store the original state of the pagecontext attributes */
    private Map m_originalAttributes;
    /** This iterator will be used for getting the GridModelRows of a grid */
    private Iterator m_modelIterator;
    /** Maintains a map of Label/ColumnHead objects for each GridColumnTag */
    private LinkedHashMap m_columnHeads;
    /** This indicates the Row No */
    private int m_rowNo;
    /** This value is used to generate a unique ColumnName for each GridColumn within the Grid */
    private int m_currColumnNo;
    /** This is the unique id (based on m_currColumnNo) given to each column */
    private String m_currColName = null;
    /** Inidicator which is set to false if the grid is empty */
    private boolean m_hasRows;
    /** The model for the Grid */
    private GridModel m_model = null;
    /** The model for the current row being processed */
    private GridModelRow m_currentRow = null;
    /** The id for the hidden field representing this Grid. It will be added to the context for the inner widgets */
    private String m_nodeId = null;

    //---------------------------------------------------
    //-- Extra Features Addded to support UserGrid
    //---------------------------------------------------
    /** This is a list of the user columns read from the grid configuration file,
     *  This is null if this is not a userGrid, or there are no user or default
     *  settings for this userGridId
     */
    private List m_selectedCols;
    /** Maintains a collection of Available Cols, i.e. those in m_columnHeads but not in m_userColumns*/
    private List m_availableCols;
    /** This map if filled in as the content of each column a it is generated.
     * key = column label, value = html text
     */
    private LinkedHashMap m_rowStack;
    /** Comma seperated list of the columns marked as keys, to be used by the floating hints */
    private String m_keyColumns;
    /** Should the option of popuphints be provided? */
    private boolean m_popupHintsOption;
    /** Has popupHints option been set manually?? */
    private boolean m_popupHintsSet;


    //---------------------------------------------------
    //-- Extra Features Addded to support Trees in the Grid
    //---------------------------------------------------
    /** The value of the root level */
    public static final int ROOT_LEVEL = 1;
    private Map m_levelIds = null;
    private String m_currentLevelId = null;
    private boolean m_isDisplayed = false;
    private boolean m_isTree = false;

    //---------------------------------------------------
    //-- Extra Features Addded to support CSS and styles
    //---------------------------------------------------
    /** Counter for the style on each row */
    private int m_cssRowCounter = 0;
    /** CSSes for the rows */
    private String[] m_cssRows = null;
    /** Counter for the style on each column */
    private int m_cssColumnCounter = 0;
    /** CSSes for the columns */
    private String[] m_cssColumns = null;
    /** Holds value of property rowCssClass.*/
    private String m_rowCssClass;
    /** Holds value of property columnCssClass. */
    private String m_columnCssClass;
    /** Holds value of property rowStyle. */
    private String m_rowStyle;
    /** Holds value of perperty rowAttributes */
    private Properties m_rowAttributes;
    
    /** Default constructor. */
    public GridTag() {
        super();
        initTag();
    }


    /** Sets the GridModel and an iterator on the rows of the GridModel.
     */
    public void otherDoStartTagOperations() throws JspException {

        super.otherDoStartTagOperations();

        // Ensure a correct value is passed in outputType
        if (m_outputType == null)
            m_outputType = OUTPUT_TYPE_WEB_PAGE;
        else if (!OUTPUT_TYPE_WEB_PAGE.equals(m_outputType) &&
                !OUTPUT_TYPE_EXCEL.equals(m_outputType) &&
                !OUTPUT_TYPE_XML.equals(m_outputType))
            throw new IllegalArgumentException("Illegal outputType '" + m_outputType + "' passed to the " + TAG_NAME + ". Valid values are " + OUTPUT_TYPE_WEB_PAGE + ',' + OUTPUT_TYPE_EXCEL + ',' + OUTPUT_TYPE_XML);

        // The grid tag cannot be enclosed withing another tag !!!
        if (TagHelper.isEnclosed(pageContext) ) {
            String str = "The " + TAG_NAME + " cannot be enclosed within another tag";
            log.error(str);
            throw new TagCannotBeEnclosedRuntimeException(str);
        }

        // Store the Original Attributes
        m_originalAttributes = TagHelper.getOriginalAttributes(pageContext);

        // Get the formtag from the page & register the widget
        FormTag formTag = TagHelper.getFormTag(pageContext);
        if(formTag == null) {
            String str = "The " + TAG_NAME + " should be inside a FormTag";
            log.error(str);
            throw new OuterFormTagMissingRuntimeException(str);
        }

        // Get the model
        try {
            m_model = (GridModel) TagHelper.getModel(pageContext, getField(), TAG_NAME);
        } catch (ClassCastException e) {
            String str = "Wrong WidgetModel for " + TAG_NAME + " on field " + getField();
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }

        if (m_model != null) {
            Collection rows = m_model.getRows();
            if (rows != null && rows.size() > 0) {
                m_hasRows = true;
                m_modelIterator = rows.iterator();
            }
        }

        if(isUserGrid()) {
            // raise an error, if the error-flag is set on the model
            if ((m_model != null) && m_model.getErrorInSavingUserSettings()) {
                TagHelper.getFormBase(pageContext).raiseError((HttpServletRequest) pageContext.getRequest(), ActionMessages.GLOBAL_MESSAGE, "error.widgets.usergrid.savefailed");
                m_model.setErrorInSavingUserSettings(false);
            }

            // See if there are any user configuration settings available for this user and grid.
            UserSession us = UserSession.getUserSession((HttpServletRequest) pageContext.getRequest());
            UserGridManager userGridManager = new UserGridManager();

            // returns null if no configuration settings are found.
            m_selectedCols = userGridManager.getColSettings(us.getUserId() , getUserGridId());

            //@todo: There was legacy code at this point as we used to store translated labels
            // in the XML files. We have for a while now, only stored labels. This TODO is to add
            // back in the legacy support...if the need arises.
        }

        // Determine based on widget type and Context rules if user has disabled hints
        if(!m_popupHintsSet) {
            m_popupHints = isUserGrid();
            String rule = isUserGrid() ? RULE_USERGRID_POPUP : RULE_GRID_POPUP;
            String popupHints = (String) ContextManagerFactory.instance().getProperty(rule);
            if(popupHints!=null)
                m_popupHints = Boolean.valueOf(popupHints);
            log.debug("popupHints (from rule: " + rule+") defaults to " + m_popupHints);
        }

    }


    /** This method is called just once, before the body is evaluated.
     * It sets the initial context.
     * @throws JspException if any error occurs.
     */
    public void doInitBody() throws JspException {
        m_rowNo = 0;
        if (m_modelIterator != null)
            m_currentRow = (GridModelRow) m_modelIterator.next();
        setRowContext();
        // set the pagecontext attributes
        pageContext.setAttribute(TagHelper.ATTRIBUTE_ENCLOSED, Boolean.TRUE, pageContext.REQUEST_SCOPE);
    }


    /**
     * Used to get notification of any inner GridColumn or GridRow Tags
     * GridColumn tags are used to register the list of columns which are available
     * GridRow tags are used to set visual properties about the row.
     * All other tags are passed on and registered with this Grids Parent
     * @param tag Inner tag being registered
     */
    public void register(ICustomTag tag) {
        if(tag instanceof GridColumnTag) {
            GridColumnTag col = (GridColumnTag) tag;
            // Set column tracking values
            m_currColumnNo++;
            m_currColName = COLUMN_NAME_PREFIX+m_currColumnNo;
            boolean hidden = isColumnHidden(col.getLabel());
            if(!hidden) {
                // Set the default css class for this column
                setColumnCssClass(getNextColumnCssClass());
                if(col.getColumnCssClass()!=null)
                    setColumnCssClass(col.getColumnCssClass());
            }

            // Only process this on the first interation
            if (isFirstPass()) {
                addColumnHead(col.getLabel(), col.getStyle(), col.getDataType(), col.getLabelEditorLink(),
                        col.isRequired() || col.getTreeField()!=null, getColumnCssClass() );
                // Don't register the key for hidden columns
                if(!hidden && col.isKey())
                    addKeyColumn(col.getLabel());
                // Add hidden columns to list of available ones
                if(hidden)
                    addAvailableCols(col.getLabel());
                // Flag this Grid as being a tree
                if(col.getTreeField()!=null)
                    m_isTree=true;
            }
        } else if(tag instanceof GridRowTag) {
            GridRowTag rowTag = (GridRowTag) tag;
            if(rowTag.getRowCssClass()!=null)
                setRowCssClass(rowTag.getRowCssClass());
            if(rowTag.getStyle()!=null)
                setRowStyle(rowTag.getStyle());
            if (rowTag.getAttributes() != null)
                setRowAttributes(rowTag.getAttributes());
            log.debug("register in GridTag:"+getRowAttributes());
        } else
            super.register(tag);
    }


    /** Returns a true if all the rows of the GridModel have not been iterated through.
     * @return a true if all the rows of the GridModel have not been iterated through.
     */
    public boolean theBodyShouldBeEvaluatedAgain() throws JspException {
        if (m_modelIterator != null && m_modelIterator.hasNext() ) {
            m_currentRow = (GridModelRow) m_modelIterator.next();
            setRowContext();
            return true;
        } else {
            return false;
        }

    }


    /** Concludes the html for the grid tag.
     * Called from the doEndTag()
     */
    public void otherDoEndTagOperations() throws JspException {
        super.otherDoEndTagOperations();
        try {
            JspWriter out = pageContext.getOut();
            out.println( getEndingHtml() );
            if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType) && isUserGrid() )
                out.println( writeSettingsTable() );
        } catch (IOException e) {
            String str = "Exception in writing the " + TAG_NAME;
            log.error(str, e);
            throw new JspWriteRuntimeException(str, e);
        }

    }


    /** The GridColumnTag registers its label during the 1st pass.
     * @param label The label of the column.
     * @param style The style of the column.
     * @param dataType The dataType of the column.
     * @param labelEditorLink The labelEditorLink for the column.
     */
    public void addColumnHead(String label, String style, String dataType,
                              String labelEditorLink, boolean required,
                              String columnCss) {
        m_columnHeads.put(label, new ColumnHead(label, style, dataType, labelEditorLink,
                MessageHelper.replaceTokens(pageContext, label), required, columnCss ));
    }


    /** Add a column that is defined in the JSP, but is not visible on the UserGrid
     */
    public void addAvailableCols(String columnHeadLabel) {
        if(m_availableCols==null)
            m_availableCols = new ArrayList();
        m_availableCols.add(columnHeadLabel);
    }


    /** Adds the widget html information to a linked hashmap for processing for the row
     * @param label The label of the column.
     */
    public void addColumnContentsToStack(String label , String value) {
        /*if(log.isDebugEnabled()) {
            if(value==null||value.trim().length()==0)
                log.debug("Row " + m_rowNo + " / Column '" + label + "' has posted an generated an empty cell?");
            else
                log.debug("Row " + m_rowNo + " / Column '" + label + "' cell content size = " + value.length());
        }*/
        m_rowStack.put(label , value);
    }


    /** Add a field to the list of key fields */
    public void addKeyColumn(String label) {
        if (m_keyColumns == null) {
            m_keyColumns = label;
        } else {
            m_keyColumns += "|" + label;
        }
        log.debug("Added key column : " + m_keyColumns);
    }


    /** Get the list of fields that make up the primary field */
    public String getKeyColumns() {
        return m_keyColumns;
    }


    /** Will check against to see if the Column is one currently being displayed
     * @return true if the tag is column is not to be processed
     */
    public boolean isColumnHidden(String label){
        boolean hidden = m_selectedCols!=null && !m_selectedCols.contains(label);
        //log.debug("isColumnHidden("+label+") = "+hidden);
        return hidden;
    }


    /** Clears the Stack of row information */
    public void clearStack() {
        m_rowStack.clear();
    }


    /** Returns a true if this is the first iteration through the GridTag.
     * @return a true if this is the first iteration through the GridTag.
     */
    public boolean isFirstPass() {
        return m_rowNo == 0 ? true : false;
    }


    /** Returns a new column name.
     * @return a new column name.
     */
    public String getNewColumnName() {
        return COLUMN_NAME_PREFIX + m_currColumnNo++;
    }


    public String getNoRecordsText() {
        String label = "No Records Found";
        if (getNoRecordsKey() != null) {
            if (MessageHelper.hasTokens(getNoRecordsKey())) {
                label = MessageHelper.replaceTokens(pageContext, getNoRecordsKey());
            } else {
                if (MessageHelper.hasTokens(NO_RECORDS_LABEL))
                    label = MessageHelper.replaceTokens(pageContext, NO_RECORDS_LABEL);
            }
        } else {
            if (MessageHelper.hasTokens(NO_RECORDS_LABEL))
                label = MessageHelper.replaceTokens(pageContext, NO_RECORDS_LABEL);
        }
        return label;
    }


    /** This generates the HTML for the tag.
     * @param out The JspWriter object.
     * @param bodyContent The BodyContent object.
     * @throws IOException if any I/O error occurs.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {

        if (isFirstPass())
            // Write the main table tag, the table column headings, and start the table body
            out.println( getInitialHtml() );

        // Write out the start and end of the rows.
        out.println( getRowStartHtml() );
        if (m_hasRows)
            out.println( processRow());
        out.println( getRowEndingHtml() );

        // clear the body content for the next time through.
        bodyContent.clearBody();

        // Increment the RowNo
        ++m_rowNo;

        //Reset the column counters
        m_currColumnNo = 0;
        m_currColName = null;
    }


    /** Invoked in all cases after doEndTag() for any class implementing Tag, IterationTag or BodyTag. This method is invoked even if an exception has occurred in the BODY of the tag, or in any of the following methods: Tag.doStartTag(), Tag.doEndTag(), IterationTag.doAfterBody() and BodyTag.doInitBody().
     * This method is not invoked if the Throwable occurs during one of the setter methods.
     * This will reset the internal fields, so that the Tag can be re-used.
     */
    public void doFinally() {
        TagHelper.restoreOriginalAttributes(pageContext, m_originalAttributes);
        pageContext.removeAttribute(TagHelper.ATTRIBUTE_MODEL_MAP, pageContext.REQUEST_SCOPE);
        pageContext.removeAttribute("row", pageContext.REQUEST_SCOPE);
        // Reset all state in this Tag
        initTag();
        // Clear out base class stuff too
        super.doFinally();
    }

    /** Get the row from the model that is being processed */
    public GridModelRow currentGridRow() {
        return m_currentRow;
    }


    /**
     * Return javascript included needed for the UserGrid
     * Determine which javascript sort is needed based on the default
     * date/time format of the current local. Use UK based sorting if it
     * starts with 'dd'.
     * @return HTML to insert at the end of the page
     */
    public String getFooterHtml() {
        //if (LocaleContext.getLocale().toString().equals("en_GB")) {
        if( DateTimeFieldMetaData.getDateTimeFormat() != null &&
                DateTimeFieldMetaData.getDateTimeFormat().toLowerCase().startsWith("dd") ) {
            return JS_LOCALE_EN_GB;
        } else {
            return JS_LOCALE_EN_US;
        }
    }


    /** Get the translated column title based on the current label */
    public String getColumnTitle(String label) {
        ColumnHead ch = (ColumnHead) m_columnHeads.get(label);
        if(ch!=null)
            return ch.getColumnTitle();
        return null;
    }



    //---------------------------------------------------
    //-- Private Methods
    //---------------------------------------------------

    /** Initialize all the data for a new tag, or a re-used tag */
    private void initTag() {
        //-- TLD Attributes
        m_cssClass = "sort";
        m_headerCssClass = "sort";
        m_rowCssClasses = null;
        m_columnCssClasses = null;
        m_detail = false;
        m_displayOnly = false;
        m_noRecordsKey = null;
        m_outputType = OUTPUT_TYPE_WEB_PAGE;
        m_popupHintsOption = true;
        m_popupHintsSet = false;
        m_target = null;
        m_userGridId = null;
        m_style = null;

        //-- Grid state
        m_originalAttributes = null;
        m_modelIterator = null;
        m_columnHeads = new LinkedHashMap();
        m_rowNo = 0;
        m_currColumnNo = 0;
        m_currColName = null;
        m_hasRows = false;
        m_model = null;
        m_currentRow = null;
        m_nodeId = null;

        //-- Extra Features Addded to support UserGrid
        m_selectedCols = null;
        m_availableCols = null;
        m_rowStack = new LinkedHashMap();
        m_keyColumns = null;

        //-- Extra Features Addded to support Trees in the Grid
        m_levelIds = null;
        m_currentLevelId = null;
        m_isDisplayed = false;
        m_isTree = false;

        //-- Extra Features Addded to support CSS and styles
        m_cssRowCounter = 0;
        m_cssRows =  new String[] {"sort","altsort"};
        m_cssColumnCounter = 0;
        m_cssColumns =  new String[] {"sort"};
        m_rowCssClass = null;
        m_columnCssClass = null;
        m_rowStyle = null;
        m_rowAttributes = null;

    }


    /** Common repeated setup from doInitBody() and theBodyShouldBeEvaluatedAgain() */
    private void setRowContext() throws JspException {
        m_cssColumnCounter=0;
        setRowCssClass(getNextRowCssClass());
        m_rowStyle = null;
        if(log.isDebugEnabled())
            log.debug("Process Row " + m_rowNo + " Css="+getRowCssClass());

        pageContext.setAttribute(TagHelper.ATTRIBUTE_ID_PREFIX, getHtmlIdPrefix(m_currentRow), pageContext.REQUEST_SCOPE);
        pageContext.setAttribute(TagHelper.ATTRIBUTE_EVENT_PREFIX, getJaffaEventNamePrefix(m_currentRow), pageContext.REQUEST_SCOPE);
        pageContext.setAttribute(TagHelper.ATTRIBUTE_PARENT_NODE_ID, getNodeId(), pageContext.REQUEST_SCOPE);
        if (m_currentRow != null) {
            pageContext.setAttribute(TagHelper.ATTRIBUTE_MODEL_MAP, m_currentRow, pageContext.REQUEST_SCOPE);
            // Set for use by expression languages
            pageContext.setAttribute("row", m_currentRow, pageContext.REQUEST_SCOPE);
        }
    }


    /** Get the id used for all objects in this grid row */
    private String getHtmlIdPrefix(GridModelRow row) {
        return getHtmlIdPrefix() + "_" + (row != null ? row.getRowId() : 0);
    }

    /** Get the event name used for all objects in this grid row */
    private String getJaffaEventNamePrefix(GridModelRow row) {
        return getJaffaEventNamePrefix() + ";" + (row != null ? row.getRowId() : 0);
    }


    /** Write the <table> , <thead></thead> and <tbody> tags */
    private String getInitialHtml() {
        StringBuffer buf = new StringBuffer();
        int index = 0;
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType)) {
            buf.append("<input id=\"")
            .append(getNodeId())
            .append("\" name=\"")
            .append(getField())
            .append("WV\" type=\"hidden\" class=\"WidgetGrid\">\n")
            .append(getWidgetRegistrationScript((String) m_originalAttributes.get(TagHelper.ATTRIBUTE_PARENT_NODE_ID), getNodeId(), false));
            if(isUserGrid()) {
                // Only need the span for overlaying the grid with settings
                buf.append("<span id='" + getHtmlIdPrefix() + "_main' style='visibility:visible;display:block' onContextMenu='return false;'>\n");
            }
            buf.append("<table id='")
            .append(getHtmlIdPrefix())
            .append("'");
            String tmpCssClass = m_cssClass;
            if (getDisplayOnly()) {
                tmpCssClass = "displayOnly";
            } else {
                tmpCssClass = "notDisplayOnly";
            }
            if (m_cssClass != null) {
                tmpCssClass += " " + m_cssClass;
            }
            if(tmpCssClass!=null)
                buf.append(" class='exportTable ")
                .append(tmpCssClass)
                .append("'");
            buf.append(" cellpadding='0' cellspacing='0' border='0' width='100%'>\n");
            //--Orig Grid
            //buf.append("<TABLE CLASS='" + getClassName() + "' cellpadding=\"3\" cellspacing=\"0\" border=\"0\" " + (m_width != null ? "width=\"" + m_width + "\"" : "width=\"100%\"") + "><TR><TD>\n");
            //buf.append("<TABLE id=\"" + getTablePrefix() + "_main\" CLASS='" + getClassName() + "' " + (m_hasRows && !getDisplayOnly() ? "" : "" ) + "cellpadding=\"3\" cellspacing=\"0\" border=\"0\" " + (m_width != null ? "width=\"" + m_width + "\"" : "width=\"100%\"") + ">\n");
            //--
        } else if (OUTPUT_TYPE_EXCEL.equals(m_outputType)) {
            buf.append("<table border='1'>\n");
        } else if (OUTPUT_TYPE_XML.equals(m_outputType)) {
            buf.append("<" + XML_ROOT + ">\n");
        }

        if (!getDisplayOnly() && !OUTPUT_TYPE_XML.equals(m_outputType)) {
            if(m_selectedCols == null) {
                log.debug("No Default or User Layout specified");
                // Display header for all columns in the Grid
                if (m_columnHeads != null && m_columnHeads.size() > 0) {
                    if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType))
                        buf.append("<thead"+(isTree()?" sorting='false'":"")+">");
                    buf.append("<tr>");
                    for (Iterator i = m_columnHeads.values().iterator(); i.hasNext(); ) {
                        ColumnHead columnHead = (ColumnHead) i.next();
                        outputColumnHeading(buf, columnHead);
                    }
                    buf.append("</tr>");
                    if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType))
                        buf.append("</thead>");
                } else
                    log.debug("No Column Tags Have Been Registered!");
            } else {
                // Display header for user selected columns in the Grid
                if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType))
                    buf.append("<thead"+(isTree()?" sorting='false'":"")+">");
                buf.append("<tr>");
                for (Iterator itr = m_selectedCols.iterator(); itr.hasNext(); ) {
                    ColumnHead columnHead = (ColumnHead) m_columnHeads.get(itr.next());
                    if (columnHead != null) {
                        outputColumnHeading(buf, columnHead);
                    }
                }
                buf.append("</tr>");
                if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType))
                    buf.append("</thead>");
            }
        }
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType))
            buf.append("<tbody>");
        return buf.toString();
    }


    /** Write a column heading cell <td> to the buffer */
    private void outputColumnHeading(StringBuffer buf, ColumnHead columnHead) {
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType)) {
            //buf.append("<TD id=\"" + getTablePrefix() + columnHead.getLabel() + "\"" + columnHead.getStyle() + " class='sort'" + (columnHead.getDataType() != null ? "type=\"" + columnHead.getDataType() + "\""  : "" ) + ">" + columnHead.getLabel() + columnHead.getLabelEditorLink() + "</TD>\n");
            // Removed Id from column head
            buf.append("<td");
            if(m_headerCssClass!=null)
                buf.append(" class='")
                // if there is a * in the class, replace it with the CSS used for this specific column
                .append(m_headerCssClass.replace("*",columnHead.getColumnCss()) )
                .append("'");
            if( columnHead.getDataType() != null && !isTree() )
                buf.append(" type='")
                .append(columnHead.getDataType())
                .append("'");
            buf.append("><span class='exportColumnHeader'>")
            .append(columnHead.getColumnTitle())
            .append("</span>")
            .append(columnHead.getLabelEditorLink())
            .append("</td>\n");
        } else if (OUTPUT_TYPE_EXCEL.equals(m_outputType))
            buf.append("<th style=\"background-color: Silver;border : groove;\">" + columnHead.getColumnTitle() + "</th>\n");
    }


    /** Write the </tbody>, </table>  tags */
    private String getEndingHtml() {
        StringBuffer buf = new StringBuffer();
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType)) {
            if (!m_hasRows)
                buf.append("<tr")
                .append(getRowClassAndStyle())
                .append("><td colspan='")
                .append(m_columnHeads.size())
                .append("' align='center'>")
                .append(getNoRecordsText())
                .append("</td></tr>");
            buf.append("</tbody>\n");
            buf.append("</table>\n");
            if(isUserGrid())
                buf.append("</span>\n");

            //See if there is a row to be targetted in the Tree
            if (m_model.getTarget() != null) {
                buf.append("<script>window.location.hash = \"rowTarget\";</script>");
                m_model.setTarget(null);
            }
        } else if (OUTPUT_TYPE_EXCEL.equals(m_outputType)) {
            buf.append("</table>\n");
        } else if (OUTPUT_TYPE_XML.equals(m_outputType)) {
            buf.append("</" + XML_ROOT + ">\n");
        }
        return buf.toString();
    }


    /** Write out the <tr> tag for a row of data */
    private String getRowStartHtml() {
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType)) {
            StringBuffer buf = new StringBuffer();
            buf.append("<tr");
            if(isTree() && getCurrentLevelId() != null) {
                // Add the id needed for trees
                buf.append(" id='")
                .append(getCurrentLevelId())
                .append("'");
                // See if it should be hidden in the tree on display
                if( !isRowDisplayed() &&
                        ((Integer)currentGridRow().get("level")).intValue() > ROOT_LEVEL ) {
                    buf.append(" style='display:none'");
                }
            }
            // Add the css
            buf.append(getRowClassAndStyle())
            .append(getRowAttributes());
            log.debug("GridTag write to row:"+getRowAttributes());
            
            // Add event handler
            buf.append(getEventCall())
            .append(">");
            return buf.toString();
        } else if (OUTPUT_TYPE_EXCEL.equals(m_outputType))
            return "<tr>";
        else if (OUTPUT_TYPE_XML.equals(m_outputType))
            return '<' + XML_ROW + '>';
        else
            return null;
    }


    /** Processes the row all in one go rather than per inner widget.
     * Processes and generates the HTML for the Table row
     */
    private String processRow(){
        StringBuffer sb = new StringBuffer();
        if (m_selectedCols != null) {
            log.debug("Print out row. Selected Cols=" + m_selectedCols.size() + ", Populated Cols=" + m_rowStack.size());

            for (Iterator itr = m_selectedCols.iterator(); itr.hasNext(); ) {
                String colName=(String)itr.next();
                Object html = m_rowStack.get(colName);
                if (html != null)
                    sb.append(html);
                else
                    log.debug("Column " + colName + " is blank??");
            }
            return sb.toString();
        } else {
            log.debug("Print out row for all " + m_columnHeads.keySet().size() + " columns");
            for (Iterator i = m_columnHeads.keySet().iterator(); i.hasNext(); )
                sb.append(m_rowStack.get((String) i.next()));
            return sb.toString();
        }
    }

    /** Write out the </tr> tag for a row of data */
    private String getRowEndingHtml() {
        String out = null;
        if (OUTPUT_TYPE_WEB_PAGE.equals(m_outputType) || OUTPUT_TYPE_EXCEL.equals(m_outputType))
            out = "</tr>";
        else if (OUTPUT_TYPE_XML.equals(m_outputType))
            out = "</" + XML_ROW + '>';
        return out;
    }


    /** Generate the javascript event calls for double clicking a row */
    private String getEventCall() {
        if( getDetail() ) {
            StringBuffer sb = new StringBuffer(" onDblClick=\"");
            String formName=TagHelper.getFormTag(pageContext).getHtmlName();
            // Set the event field on the form
            sb.append("document.")
            .append(formName)
            .append(".eventId.value='")
            .append(getJaffaEventNamePrefix(m_currentRow))
            .append(';')
            .append(EVENT_DETAIL)
            .append("';");

            // Set the Target on the form
            if (getTarget() != null && getTarget().length() > 0)
                sb.append("document." + formName + ".target='" + getTarget() + "';");
            else {
                // Set the target using the value specified in the FormTag.. Set to '_self' if not specified
                FormTag formTag = TagHelper.getFormTag(pageContext);
                if (formTag == null || formTag.getTarget() == null || formTag.getTarget().length() == 0)
                    //@todo Question:Isn't _self this implied? Why do we need to set it!
                    sb.append("document." + formName + ".target='_self';");
                else
                    //@todo Question:Isn't this set by the FormTag? Why do we need to set it again!
                    sb.append("document." + formName + ".target='" + formTag.getTarget() + "';");
            }
            // Post the form
            sb.append("postForm(document." + formName + ",null);\"");
            return sb.toString();
        } else
            return "";
    }


    private String getNodeId() {
        if (m_nodeId == null) {
            String parentNodeId = TagHelper.getParentNodeId(pageContext);
            m_nodeId = (parentNodeId != null ? parentNodeId + '_' + getField() : getHtmlIdPrefix()) + "_h";
        }
        return m_nodeId;
    }


    //---------------------------------------------------
    //-- Inner Classes
    //---------------------------------------------------


    /** An instance of this class will be created for each UserGridColumn.
     */
    private static class ColumnHead {
        private String m_label;
        private String m_style;
        private String m_dataType;
        private String m_labelEditorLink;
        private String m_columnTitle;
        private boolean m_required;
        private String m_columnCss;

        /** Constructor
         * @param label The label of the column.
         * @param style The style of the column.
         * @param dataType The dataType of the column.
         * @param labelEditorLink The labelEditorLink for the column.
         */
        public ColumnHead(String label, String style, String dataType, String labelEditorLink,
                          String columnTitle, boolean required, String columnCss) {
            m_label = label;
            m_style = style;
            m_dataType = dataType;
            m_labelEditorLink = labelEditorLink;
            m_columnTitle = columnTitle;
            m_required = required;
            m_columnCss = columnCss;
        }

        /** Getter for the attribute label.
         * @return Value of attribute label.
         */
        public String getLabel() {
            return m_label;
        }

        /** Getter for the attribute style.
         * @return Value of attribute style.
         */
        public String getStyle() {
            return m_style;
        }

        /** Getter for the attribute dataType.
         * @return Value of attribute dataType.
         */
        public String getDataType() {
            return m_dataType;
        }

        /** Getter for the attribute labelEditorLink.
         * @return Value of attribute labelEditorLink.
         */
        public String getLabelEditorLink() {
            return m_labelEditorLink;
        }

        /**
         * Getter for property columnTitle.
         * @return Value of property columnTitle.
         */
        public String getColumnTitle() {
            return m_columnTitle;
        }

        /**
         * Getter for property required.
         * @return Value of property required.
         */
        public boolean isRequired() {
            return m_required;
        }

        /**
         * Getter for property columnCss.
         * @return Value of property columnCss.
         */
        public String getColumnCss() {
            return m_columnCss;
        }

    }


    //---------------------------------------------------
    //-- Extra Features Addded to support Trees in the Grid
    //---------------------------------------------------
    private boolean isTree() {
        return m_isTree;
    }

    public String getServerSideCall() {
        return TagHelper.getEventPrefix(pageContext);
    }

    public String retrieveLastLevelId(Integer level) {
        if (m_levelIds != null) {
            return (String) m_levelIds.get(level);
        } else {
            return null;
        }
    }

    public void updateLastLevelId(Integer level, String Id) {
        if (m_levelIds == null)
            m_levelIds = new HashMap();
        m_levelIds.put(level , Id);
    }

    public String getCurrentLevelId() {
        return m_currentLevelId;
    }

    public void setCurrentLevelId(String value) {
        m_currentLevelId = value;
    }

    public void setRowDisplayed(boolean value) {
        m_isDisplayed = value;
    }

    public boolean isRowDisplayed() {
        return m_isDisplayed;
    }

    public boolean getIsTarget() {
        if (currentGridRow().equals(m_model.getTarget())) {
            return true;
        } else {
            return false;
        }
    }

    //---------------------------------------------------
    //-- Extra Features Addded to support UserGrid
    //---------------------------------------------------
    private boolean isUserGrid() {
        return m_userGridId!=null;
    }

    public int getRowNumber() {
        return m_rowNo;
    }

    /** Get next css class to be used, and advance to next one */
    private String getNextColumnCssClass() {
        if(m_cssColumns==null || m_cssColumns.length==0)
            return null;
        String css=m_cssColumns[m_cssColumnCounter++];
        if(m_cssColumnCounter>=m_cssColumns.length)
            m_cssColumnCounter=0;
        return css;
    }

    /** Get next css class to be used, and advance to next one */
    private String getNextRowCssClass() {
        if(m_cssRows==null || m_cssRows.length==0)
            return null;
        String css=m_cssRows[m_cssRowCounter++];
        if(m_cssRowCounter>=m_cssRows.length)
            m_cssRowCounter=0;
        return css;
    }

    /** For a row, return the current style and class attributes */
    private String getRowClassAndStyle() {
        return (m_rowCssClass!=null?" class='"+m_rowCssClass+"'":"") +
                (m_rowStyle!=null?" style='"+m_rowStyle+"'":"");
    }

    /** For a column, return the current class attribute */
    private String getColumnClassAttribute() {
        return (m_columnCssClass!=null?" class='"+m_columnCssClass+"'":"");
    }

    /** write the HTML code for the user settings layer */
    private String writeSettingsTable(){
        StringBuffer sb = new StringBuffer();
        String gridHtmlId = getHtmlIdPrefix();
        sb.append("<span id=\"" + gridHtmlId + "_settings\"  style=\"visibility:hidden;display:none\">\n");

        // Get list of visible columns
        List visibleCols = m_selectedCols;
        if (visibleCols == null && m_columnHeads != null) {
            visibleCols = new ArrayList();
            for (Iterator i = m_columnHeads.keySet().iterator(); i.hasNext(); ) {
                visibleCols.add((String) i.next());
            }
        }
        sb.append("<input type='hidden' id=\"" + gridHtmlId + HTML_ID_AVAILABLE + "Value\">\n");
        sb.append("<input type='hidden' id=\"" + gridHtmlId + HTML_ID_SELECTED + "Value\">\n");
        sb.append("<table id=\"" + gridHtmlId + "outerTable\" width=\"100%\" onContextMenu='return false;'>\n");
        sb.append("<thead>\n");
        sb.append("<tr>");
        sb.append("<td class='sort' colspan='4' align='center'>"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.ConfigurableTableSettings",null))+"</td>\n");
        sb.append("</tr>\n");
        sb.append("</thead>\n");
        sb.append("<tr class='sort'><td class='sort' colspan='4'>&nbsp;</td></tr>\n");
        sb.append("<tr class='sort'>\n");
        sb.append("<td class='sort' valign='top'>\n");
        sb.append("<table id=\"" + gridHtmlId + HTML_ID_AVAILABLE + "\" class='scroll'>\n");
        sb.append("<thead>\n");
        sb.append("<tr>\n");
        sb.append("<td class='sort'>"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.AvailableColumns",null))+"</td>\n");
        sb.append("</tr>\n");
        sb.append("</thead>\n");
        sb.append("<tbody>\n");
        if ((m_availableCols != null) && (m_columnHeads != null)) {
            for (int j = 0; j < m_availableCols.size(); j++) {
                ColumnHead colHead = (ColumnHead)m_columnHeads.get(m_availableCols.get(j));
                if (colHead != null) {
                    sb.append("<tr id=\"")
                    .append(gridHtmlId)
                    .append("_ca")
                    .append(j)
                    .append("\" name=\"")
                    .append(m_availableCols.get(j))
                    .append("\" onDblClick=\"doubleClickEvent(this.parentNode.parentNode.id, '")
                    .append(gridHtmlId)
                    .append("')\" onClick=\"selectRow(this.parentNode.parentNode.id, this.id, '")
                    .append(gridHtmlId)
                    .append("')\">\n")
                    .append("<td>")
                    .append( colHead.getColumnTitle() )
                    .append("</td>\n")
                    .append("</tr>\n");
                }
            }
        }
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        sb.append("</td>\n");
        sb.append("<td >\n");
        sb.append("<table >");
        sb.append("<tr>\n");
        sb.append("<td align='center'><a class=\"WidgetButton8\" href=\"javascript:addRow('" + gridHtmlId + "')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.AddButton",null))+"</a></td>\n");
        sb.append("</tr>\n");
        sb.append("<tr>\n");
        sb.append("<td align='center'><a class=\"WidgetButton8\" href=\"javascript:removeRow('" + gridHtmlId + "')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.RemoveButton",null))+"</a></td>\n");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append("</td>\n");
        sb.append("<td class='sort' valign=\"top\">\n");
        sb.append("<table id=\"" + gridHtmlId + HTML_ID_SELECTED + "\" class='scroll'>\n");
        sb.append("<thead>\n");
        sb.append("<tr>\n");
        sb.append("<td class='sort'>"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.SelectedColumns",null))+"</td>\n");
        sb.append("</tr>\n");
        sb.append("</thead>\n");
        sb.append("<tbody>\n");
        if (visibleCols != null) {
            for (int k = 0; k < visibleCols.size(); k++) {
                ColumnHead colHead = (ColumnHead)m_columnHeads.get(visibleCols.get(k));
                if (colHead != null) {
                    sb.append("<tr id=\"")
                    .append(gridHtmlId)
                    .append("_cs")
                    .append(k)
                    .append("\" name=\"")
                    .append(visibleCols.get(k))
                    .append("\"");
                    if(colHead.isRequired())
                        sb.append(" required='true'");
                    sb.append(" onDblClick=\"doubleClickEvent(this.parentNode.parentNode.id, '")
                    .append(gridHtmlId)
                    .append("')\" onclick=\"selectRow(this.parentNode.parentNode.id, this.id, '")
                    .append(gridHtmlId)
                    .append("')\">\n")
                    .append("<td");
                    if(colHead.isRequired())
                        sb.append(" class='gridColumnRequired'");
                    sb.append(">")
                    .append( colHead.getColumnTitle() )
                    .append("</td>\n")
                    .append("</tr>\n");
                }
            }
        }
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        sb.append("</td>\n");
        sb.append("<td>\n");
        sb.append("<table>");
        sb.append("<tr>\n");
        sb.append("<td align='center'><a class=\"WidgetButton12\" href=\"javascript:moveRowUp('" + gridHtmlId + "')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.MoveUpButton",null))+"</a></td>\n");
        sb.append("</tr>\n");
        sb.append("<tr>\n");
        sb.append("<td align='center'><a class=\"WidgetButton12\" href=\"javascript:moveRowDown('" + gridHtmlId + "')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.MoveDownButton",null))+"</a></td>\n");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append("</td>\n");
        sb.append("</tr>\n");
        sb.append("<tr class='sort'>\n");
        sb.append("<td colspan=\"4\" align=\"right\">");
        sb.append("<table align=\"right\">");
        sb.append("<tr>\n");
        sb.append("<td><a class='WidgetButton12' href=\"javascript:buildXML('")
        .append(gridHtmlId)
        .append("','")
        .append(getUserGridId())
        .append("','")
        .append(m_originalAttributes.get(TagHelper.ATTRIBUTE_ID_PREFIX))
        .append("',false,'');\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.SaveSettings",null))+"</a></td>\n");

        sb.append("<td><a class='WidgetButton8' href=\"javascript:")
        .append("userGrid_mainSwapBack('")
        .append(gridHtmlId)
        .append("')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.CancelButton",null))+"</a></td>\n");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append("</td>\n");
        sb.append("</tr>\n");

        sb.append("</table>\n");
        sb.append("</span>\n");

        // Write out the pop-up menu
        sb.append("<div id='")
        .append(gridHtmlId)
        .append("_mainMenu' class='UserGridPopupMenu' style='visibility:hidden;display:none;position:absolute;' oncontextmenu='return false;'>\n")
        .append("<table cellspacing='1' cellpadding='1' border='1' class='UserGridPopupMenu'>\n")
        /* Save Settings has no real use anymore
        .append("<tr><td class='sort'><a href=\"javascript:buildXML('")
        .append(gridHtmlId)
        .append("','")
        .append(getUserGridId())
        .append("','")
        .append(m_originalAttributes.get(TagHelper.ATTRIBUTE_ID_PREFIX))
        .append("',false);\">Save Settings</a></td></tr>\n")
         */
        //Configure Table Settings
        .append("<tr><td class='sort'><a href='javascript:void(0);' onclick=\"userGrid_mainSwap('")
        .append(gridHtmlId)
        .append("')\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.ConfigurableTableSettings",null))+"</a></td></tr>\n")
        //Default Settings
        .append("<tr><td class='sort'><a href=\"javascript:buildXML('")
        .append(gridHtmlId)
        .append("','")
        .append(getUserGridId())
        .append("','")
        .append(m_originalAttributes.get(TagHelper.ATTRIBUTE_ID_PREFIX))
        .append("',true,'');\">"+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.DefaultColoumnSettings",null))+"</a></td></tr>\n");
        // Toggle popup hints be toggled
        if(m_popupHintsOption)
            sb.append("<tr><td class='sort'><a href=\"javascript:buildXML('")
            .append(gridHtmlId)
            .append("','")
            .append(getUserGridId())
            .append("','")
            .append(m_originalAttributes.get(TagHelper.ATTRIBUTE_ID_PREFIX))
            .append("',false,'")
            .append(!m_popupHints)
            .append("');\">")
            .append(m_popupHints?StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.Disable",null)):StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.Button.Enable",null)))
            .append(" "+StringHelper.convertToHTML(MessageHelper.findMessage("label.Jaffa.Widgets.Grid.ColumnHeader.FloatingHelp",null))+"</a></td></tr>\n");

        sb.append("</table>\n")
        .append("</div>\n")
        .append("<input id='").append(gridHtmlId).append("_colSettings' name='settings' type='hidden' class='Settings'>");


        // Insert the java script to cancel browsers native popup menu.
        sb.append("\n<script>\n{\n")
        .append("var m = document.getElementById('")
        .append(gridHtmlId)
        .append("_main');\n")
        .append("if(m.addEventListener)\n")
        .append("  m.addEventListener('contextmenu', function(e) { e.stopPropagation(); }, false);\n")
        .append("else if(m.attachEvent)\n")
        .append("  m.attachEvent('oncontextmenu', function() { event.cancelBubble = true; });\n")
        .append("}\n");

        // Insert the java script to control the pop-up menu, only if there are rows.
        if (m_hasRows) {
            sb.append("function ")
            .append(gridHtmlId)
            .append("_mainClick(e) ")
            .append("{return userGrid_mainClick(e,'")
            .append(gridHtmlId)
            .append("');}\n")
            .append("document.getElementById('")
            .append(gridHtmlId)
            .append("_main').onmousedown=")
            .append(gridHtmlId)
            .append("_mainClick;\n");
        }
        sb.append("</script>");

        if(m_popupHints) {
            // Create to strings, one listing the key field names, the other the column number
            List orderedCols = m_selectedCols;
            if(orderedCols==null) {
                orderedCols = new ArrayList();
                orderedCols.addAll(m_columnHeads.keySet());
            }
            log.debug("Get column positions for : " + getKeyColumns());
            String[] keys = StringHelper.parseString(getKeyColumns(),"|");
            String keyPos = null;
            String keyList = null;
            for(int i=0;i<keys.length;i++) {
                int pos = orderedCols.indexOf(keys[i]);
                if(pos>-1) {
                    log.debug("Column positions for : " + keys[i] + " is " + pos);
                    if(keyPos==null) {
                        keyPos=""+(pos+1);
                        keyList=getColumnTitle(keys[i]);
                    } else {
                        keyPos+="|"+(pos+1);
                        keyList+="|"+getColumnTitle(keys[i]);
                    }
                } else
                    log.debug("Column positions for : " + keys[i] + " not found!");

            }

            // Javasript was modified to create this DIV on the fly - remove it for now...
            //sb.append("<div id='tipDiv' style='z-index: 100; visibility: hidden; position: absolute;' class='UserGridHint'></div>")

            // Hidden field for column names for each key using a | seperator
            sb.append("<input id='")
            .append(gridHtmlId)
            .append("_colNames' type='hidden' value=\"")
            .append(keyList==null?"":StringHelper.convertToHTML(keyList))
            .append("\">")
            // Hidden field for column positions for each key using a | seperator
            .append("<input id='")
            .append(gridHtmlId)
            .append("_colIndexes' type='hidden' value=\"")
            .append(keyPos==null?"":keyPos)
            .append("\">")
            // Hidden fields for 'Keys' and 'Column' labels
            .append("<input id='")
            .append(gridHtmlId)
            .append("_keyLabel' type='hidden' value=\"")
            .append(StringHelper.convertToHTML(MessageHelper.findMessage(pageContext,"label.Jaffa.Widgets.UserGrid.KeyName", null)))
            .append("\">")
            .append("<input id='")
            .append(gridHtmlId)
            .append("_colLabel' type='hidden' value=\"")
            .append(StringHelper.convertToHTML(MessageHelper.findMessage(pageContext,"label.Jaffa.Widgets.UserGrid.ColName", null)))
            .append("\">");
        }
        return sb.toString();
    }

    //---------------------------------------------------
    //-- Start: TLD Attributes
    //---------------------------------------------------

    /** Getter for the attribute width.
     * @return Value of attribute width.
     */
    public String getCssClass() {
        return m_cssClass;
    }

    /** Setter for the attribute field.
     * @param value New value of attribute field.
     */
    public void setCssClass(String value) {
        m_cssClass = value;
    }

    /** Set the CSS class to be applied to the Grid's table header, default is "sort" */
    public void setHeaderCssClass(String css) {
        m_headerCssClass = css;
    }

    /** Set the list of CSS classes to be applied in order to the rows */
    public void setRowCssClasses(String css) {
        m_cssRows = StringHelper.parseString(css,",");
    }

    /** Set the list of CSS classes to be applied in order to the columns */
    public void setColumnCssClasses(String css) {
        m_cssColumns = StringHelper.parseString(css,",");
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

    /** Getter for the attribute width.
     * @return Value of attribute width.
     */
    public boolean getDisplayOnly() {
        return m_displayOnly;
    }

    /** Setter for the attribute field.
     * @param value New value of attribute field.
     */
    public void setDisplayOnly(boolean value) {
        m_displayOnly = value;
        if(value) {
            m_cssClass = null;
            m_headerCssClass = null;
            m_cssColumns = null;
            m_cssRows = null;
        }
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

    /** Getter for the attribute outputType.
     * @return Value of attribute outputType.
     */
    public String getOutputType() {
        return m_outputType;
    }

    /** Setter for the attribute outputType.
     * @param value New value of attribute outputType.
     */
    public void setOutputType(String value) {
        m_outputType = value;
    }

    /** Getter for the attribute popupHints.
     * @return Value of attribute popupHints.
     */
    public boolean getPopupHints() {
        return m_popupHints;
    }

    /** Setter for the attribute popupHints
     * @param value New value of attribute popupHints.
     */
    public void setPopupHints(boolean value) {
        m_popupHints = value;
        m_popupHintsSet = true;
        if(!m_popupHints)
            m_popupHintsOption=false;
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

    /** getter for the attribute userGridId
     */
    public String getUserGridId() {
        return m_userGridId;
    }

    /** setter for the attribute
     */
    public void setUserGridId(String value) {
        if(value!=null && value.trim().length()==0)
            value=null;
        m_userGridId = value;
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

    //---------------------------------------------------

    /**
     * Getter for property rowCssClass.
     * @return Value of property rowCssClass.
     */
    public String getRowCssClass() {
        return m_rowCssClass;
    }

    /**
     * Setter for property rowCssClass.
     * @param rowCssClass New value of property rowCssClass.
     */
    public void setRowCssClass(String rowCssClass) {
        m_rowCssClass = rowCssClass;
    }

    /**
     * Getter for property columnCssClass.
     * @return Value of property columnCssClass.
     */
    public String getColumnCssClass() {
        return m_columnCssClass;
    }

    /**
     * Setter for property columnCssClass.
     * @param columnCssClass New value of property columnCssClass.
     */
    public void setColumnCssClass(String columnCssClass) {
        m_columnCssClass = columnCssClass;
    }

    /**
     * Getter for property rowStyle.
     * @return Value of property rowStyle.
     */
    public String getRowStyle() {
        return m_rowStyle;
    }

    /**
     * Setter for property rowStyle.
     * @param rowStyle New value of property rowStyle.
     */
    public void setRowStyle(String rowStyle) {
        m_rowStyle = rowStyle;
    }
    
    /**
     * Getter for property rowAttributes.
     * @return String of a list of row attributes.
     */
    public String getRowAttributes() {
        if (m_rowAttributes == null) 
          return "";
        StringBuffer sb = new StringBuffer();
        for (Iterator i=m_rowAttributes.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry me = (Map.Entry) i.next();
            sb.append(" ").append(me.getKey()).append("='").append(me.getValue()).append("'");
        }
        return sb.toString();
    }
    
    /**
     * Setter for property rowAttributes.
     * @param rowAttributes New value of property rowAttributes.
     */
    public void setRowAttributes(Properties rowAttributes) {
        m_rowAttributes = rowAttributes;
    }
    
}
