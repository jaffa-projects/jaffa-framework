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

package org.jaffa.components.finder;

import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.jaffa.components.codehelper.dto.CodeHelperOutCodeDto;
import org.jaffa.components.codehelper.dto.CodeHelperOutElementDto;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.GetQueryDataException;
import org.jaffa.exceptions.SaveQueryException;
import org.jaffa.exceptions.SetQueryDataException;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.widgets.controller.DropDownController;
import org.jaffa.presentation.portlet.widgets.model.DropDownModel;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;
import org.jaffa.util.MessageHelper; 

/** This is the base class for all Finder components created by using the object_finder_2_0 pattern.
 * It has the following properties -
 *    1- displayResultsScreen : If set to true, then the Results screen will be directly brought up, bypassing the Criteria screen.
 *    2- consolidatedCriteriaAndResults : If set to true, then the Criteria and Results screen will be shown together
 *    3- sortDropDown : The sort criteria to use for the inquiry
 *    4- exportType : The export option to use for the inquiry (initialized to regular Web Pages)
 *    5- maxRecords: The maximum number of records to retrieve. All records will be retrieved, if no value is specified.
 *
 * A Finder class will have to provide an implementation for the doInquiry(), getCriteriaFormName(), getResultsFormName() and getConsolidatedCriteriaAndResultsFormName() methods.
 * @author  GautamJ
 */
public abstract class FinderComponent2 extends Component {

    private static final Logger log = Logger.getLogger(FinderComponent2.class);
    private static final String SPACE = " ";
    private static final String COMMA = ",";
    private static final String DESC = "DESC";
    /** A global constant for the Web Page export option.*/
    public final static String EXPORT_TYPE_WEB_PAGE = "W";
    /** A global constant for the Excel export option.*/
    public final static String EXPORT_TYPE_EXCEL = "E";
    /** A global constant for the XML export option.*/
    public final static String EXPORT_TYPE_XML = "X";
    /** This request stream attribute is set after a query if the exportType is XML.
     * The ResultsJsp will use display the XML stored by this attribute. */
    public static final String ATTRIBUTE_EXPORT_TYPE_XML = "org.jaffa.components.finder.FinderComponent.attributeExportTypeXml";
    public static final String DEFAULT_QUERY_NO = "false";
    public static final String DEFAULT_QUERY_YES = "true";
    public static final String QUERY_HAS_SHORTCUT_NO = "false";
    public static final String QUERY_HAS_SHORTCUT_YES = "true";

    // Properties
    private Boolean m_displayResultsScreen = null;
    private Boolean m_consolidatedCriteriaAndResults = null;
    private String m_sortDropDown = null;
    private String m_exportType = EXPORT_TYPE_WEB_PAGE;
    private Integer m_maxRecords = new Integer( ( (Number) CriteriaDropDownOptions.getDefaultMaxRecordsDropDownOption()).intValue() );

    // Fields
    private HeaderDto m_headerDto = null;
    private FinderOutDto m_finderOutDto = null;
    private CodeHelperOutElementDto m_queryIdCodes = null;
    private Long m_savedQueryId = null;
    private String m_newQueryName = null;
    private Boolean m_defaultQueryYn = null;
    private Boolean m_queryHasShortcutYn = null;
    private String m_useQuery = null;
    private Boolean m_useDefaultQuery = null;
    private DateTime m_queryLastRunOn = null;

    // **************************************************************
    // Component Properties
    // **************************************************************

    /** Getter for property displayResultsScreen.
     * @return Value of property displayResultsScreen.
     */
    public Boolean getDisplayResultsScreen() {
        return m_displayResultsScreen;
    }

    /** Setter for property displayResultsScreen.
     * @param displayResultsScreen New value of property displayResultsScreen.
     */
    public void setDisplayResultsScreen(Boolean displayResultsScreen) {
        m_displayResultsScreen = displayResultsScreen;
    }

    /** Getter for property consolidatedCriteriaAndResults.
     * @return Value of property consolidatedCriteriaAndResults.
     */
    public Boolean getConsolidatedCriteriaAndResults() {
        return m_consolidatedCriteriaAndResults;
    }

    /** Setter for property consolidatedCriteriaAndResults.
     * @param consolidatedCriteriaAndResults New value of property consolidatedCriteriaAndResults.
     */
    public void setConsolidatedCriteriaAndResults(Boolean consolidatedCriteriaAndResults) {
        m_consolidatedCriteriaAndResults = consolidatedCriteriaAndResults;
    }

    /** Getter for property sortDropDown.
     * @return Value of property sortDropDown.
     */
    public String getSortDropDown() {
        return m_sortDropDown;
    }

    /** Setter for property sortDropDown.
     * @param sortDropDown New value of property sortDropDown.
     */
    public void setSortDropDown(String sortDropDown) {
        m_sortDropDown = sortDropDown;
    }

    /** Getter for property exportType.
     * @return Value of property exportType.
     */
    public String getExportType() {
        return m_exportType;
    }

    /** Setter for property exportType.
     * @param exportType New value of property exportType.
     */
    public void setExportType(String exportType) {
        m_exportType = exportType;
    }

    /** Getter for property maxRecords.
     * @return Value of property maxRecords.
     */
    public Integer getMaxRecords() {
        return m_maxRecords;
    }

    /** Setter for property maxRecords.
     * @param maxRecords New value of property maxRecords.
     */
    public void setMaxRecords(Integer maxRecords) {
        m_maxRecords = maxRecords;
    }

    public String getUseQuery() {
        return m_useQuery;
    }

    public void setUseQuery(String useQuery) {
        m_useQuery = useQuery;
    }

    public Boolean getUseDefaultQuery() {
        return m_useDefaultQuery;
    }

    public void setUseDefaultQuery(Boolean useDefaultQuery) {
        m_useDefaultQuery = useDefaultQuery;
    }

    /** Getter for property finderOutDto.
     * @return Value of property finderOutDto.
     */
    public FinderOutDto getFinderOutDto() {
        return m_finderOutDto;
    }

    public void setDefaultQueryYn(Boolean defaultQueryYn) {
        m_defaultQueryYn = defaultQueryYn;
    }

    public Boolean getDefaultQueryYn() {
        return m_defaultQueryYn;
    }

    public void setQueryHasShortcutYn(Boolean queryHasShortcutYn) {
        m_queryHasShortcutYn = queryHasShortcutYn;
    }

    public Boolean getQueryHasShortcutYn() {
        return m_queryHasShortcutYn;
    }

    // **************************************************************
    // Additional methods
    // **************************************************************

    /** If the <B>displayResultsScreen</B> property has not been set or has been set to false, it will return
     * the FormKey for the Criteria screen. If the <B>displayResultsScreen</B> property has been set to true,
     * then a Search will be performed and the FormKey for the Results screen will be returned.
     * <p>If a saved query has been specified via <B>savedQueryId</B> or <B>useDefaultQuery</B>,
     * it will be loaded and the screen will be displayed based on the <B>displayResultsScreen</B>.
     * If there is no saved or default query found, the criteria screen will be displayed regardless
     * of <B>displayResultsScreen</B> setting.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Criteria screen.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {

        if (getUseQuery() != null) {
            setSavedQueryId(new Long(getUseQuery()));
            try {
                loadQuery();
            } catch (QueryNotFoundException e) {
                // If there is no query, go to the criteria screen
                setDisplayResultsScreen(Boolean.FALSE);
            } catch (ApplicationException e) {
                throw new ApplicationExceptions(e);
            }
            if (getDisplayResultsScreen() != null && getDisplayResultsScreen().booleanValue()){
                FormKey fk = displayResultsConsiderExport();
                // If export type is xml we need to set the xml attribute on the request.
                // This is usually done by the form but needs to be done here since component is initiating the query
                if (EXPORT_TYPE_XML.equals(getExportType())) {
                    HttpServletRequest servletRequest = (HttpServletRequest) ContextManagerFactory.instance().getProperty("request");
                    if (servletRequest!=null)
                        servletRequest.setAttribute(ATTRIBUTE_EXPORT_TYPE_XML, getFinderOutDto());
                }
                return fk;
            }
        } else if (getUseDefaultQuery() != null && getUseDefaultQuery().booleanValue()) {
            try {
                loadDefaultQuery();
            } catch (QueryNotFoundException e) {
                // If there is no query, go to the criteria screen
                setDisplayResultsScreen(Boolean.FALSE);
            } catch (ApplicationException e) {
                throw new ApplicationExceptions(e);
            }
        }
        if (getDisplayResultsScreen() != null && getDisplayResultsScreen().booleanValue())
            return displayResults();
        else
            return displayCriteria();
    }

    /** Invoked the initializeCriteriaScreen() method and then returns the FormKey for the Criteria screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Criteria screen.
     */
    public FormKey displayCriteria() throws ApplicationExceptions, FrameworkException {
        initializeCriteriaScreen();
        return getCriteriaFormKey();
    }

    /** Invokes the performInquiry() method and then returns the FormKey for the Results screen.
     * If the consolidatedCriteriaAndResults property has been set to true, then the FormKey for consolidatedCriteriaAndResults screen will be returned.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey displayResults() throws ApplicationExceptions, FrameworkException {
        performInquiry();
        return getResultsFormKey();
    }

    /** Invokes the performInquiry() method and then returns the FormKey for the Results screen using export type.
     * If the consolidatedCriteriaAndResults property has been set to true, then the FormKey for consolidatedCriteriaAndResults screen will be returned.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey displayResultsConsiderExport() throws ApplicationExceptions, FrameworkException {
        performInquiry();
        return getResultsFormKey(true);
    }


    /** Invokes the doInquiry() method and then removes the "rows" GridModel object from the cache.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    public void performInquiry() throws ApplicationExceptions, FrameworkException {
        m_finderOutDto = doInquiry();
        uncacheRowsModel();
        m_queryLastRunOn = new DateTime();
    }

    /** Getter for the queryLastRunOn property.
     * @return the Date/Time the query was last run.
     */
    public DateTime getQueryLastRunOn() {
        return m_queryLastRunOn;
    }

    /** Getter for the Criteria screen's FormKey.
     * @return the FormKey for the Criteria screen.
     */
    public FormKey getCriteriaFormKey() {
        return new FormKey(getCriteriaFormName(), getComponentId());
    }

    /** Getter for the Results screen's FormKey.
     * This does not take the exportType into consideration.
     * @return the FormKey for the Results screen.
     */
    public FormKey getResultsFormKey() {
        return getResultsFormKey(false);
    }

    /** Getter for the Results screen's FormKey.
     * @param considerExportType pass true, if the returned FormKey should be based on the exportType.
     * @return the FormKey for the Results screen.
     */
    public FormKey getResultsFormKey(boolean considerExportType) {
        FormKey fk = null;
        if (considerExportType) {
            if (EXPORT_TYPE_EXCEL.equals(getExportType()))
                fk = new FormKey(getExcelFormName(), getComponentId());
            else if (EXPORT_TYPE_XML.equals(getExportType()))
                fk = new FormKey(getXmlFormName(), getComponentId());
        }
        if (fk == null) {
            if (getConsolidatedCriteriaAndResults() != null && getConsolidatedCriteriaAndResults().booleanValue())
                fk = new FormKey(getConsolidatedCriteriaAndResultsFormName(), getComponentId());
            else
                fk = new FormKey(getResultsFormName(), getComponentId());
        }
        return fk;
    }

    /** This will set the property 'maxRecords' to the value following the current one, in the list of values returned by CriteriaDropDownOptions.getMaxRecordsDropDownOptions().
     */
    public void incrementMaxRecords() {
        int currentValue = getMaxRecords() != null ? getMaxRecords().intValue() : 0;
        for (Iterator i = CriteriaDropDownOptions.getMaxRecordsDropDownOptions().keySet().iterator(); i.hasNext();) {
            int dropDownValue = ((Number) i.next()).intValue();
            if (getMaxRecords() == null) {
                setMaxRecords(new Integer(dropDownValue));
                updateMaxRecordsModel();
                break;
            } else if (currentValue == dropDownValue) {
                if (i.hasNext()) {
                    setMaxRecords(new Integer(((Number) i.next()).intValue()));
                    updateMaxRecordsModel();
                }
                break;
            }
        }
    }

    /** The Component should override this method to retrieve the set of codes for dropdowns in criteria screen, if any are required.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        initializeSavedQueries();
    }

    private void initializeSavedQueries() {
        /*
         * Read the stored queries and and create a codeHelperOutElementDto so
         * that this can be used as the options to a dropdown
         */

        //Get the saved Queries
        IContextManager contextManager = ContextManagerFactory.instance();
        QuerySaver querySaver = new QuerySaver(contextManager);
        Map savedQueries = querySaver.getSavedQueryList(getComponentDefinition().getComponentName());
        String defaultQueryId = querySaver.getDefaultQueryId(getComponentDefinition().getComponentName());

        m_queryIdCodes = new CodeHelperOutElementDto();

        if (savedQueries.size() > 0) {
            // Create the codeHelperOutElementDto by iterating over the key maps
            Iterator savedQueriesIterator = savedQueries.keySet().iterator();
            while (savedQueriesIterator.hasNext()) {
                String element = (String) savedQueriesIterator.next();

                log.info("Adding " + element + " to Saved Queries");
                CodeHelperOutCodeDto codeHelperOutCodeDto = new CodeHelperOutCodeDto();
                codeHelperOutCodeDto.setCode(element);
                if (defaultQueryId != null && element.equals(defaultQueryId))
                {
                    codeHelperOutCodeDto.setDescription("** " + savedQueries.get(element) + " **");
                }
                else
                {
                    codeHelperOutCodeDto.setDescription(savedQueries.get(element));
                }
                m_queryIdCodes.addCodeHelperOutCodeDto(codeHelperOutCodeDto);
            }
        } else {

            log.info("No Queries to add to Saved Queries");

            CodeHelperOutCodeDto codeHelperOutCodeDto = new CodeHelperOutCodeDto();
            codeHelperOutCodeDto.setCode("");
            codeHelperOutCodeDto.setDescription(MessageHelper.findMessage("label.Jaffa.Finder.NoSavedQueries", null));
            m_queryIdCodes.addCodeHelperOutCodeDto(codeHelperOutCodeDto);
        }
    }

    /** Returns a map of query ids and their export type. Is used by UI to determine if results should be opened in a new window.
     * @return Map containing export types for saved queries.
     */
    public Map<String,String> retrieveQueryTypes() {
        /*
         * Read the stored queries and create map
         * this can be used to determine export type
         */

        //Get the saved Queries
        Map<String,String> queryTypes = null;
        IContextManager contextManager = ContextManagerFactory.instance();
        QuerySaver querySaver = new QuerySaver(contextManager);
        Map savedQueries = querySaver.getSavedQueryList(getComponentDefinition().getComponentName());

        if (savedQueries.size() > 0) {
            queryTypes = new HashMap<String,String>();

            Iterator savedQueriesIterator = savedQueries.keySet().iterator();
            while (savedQueriesIterator.hasNext()) {
                String element = (String) savedQueriesIterator.next();
                Map queryFields = querySaver.getSavedQueryFields(getComponentDefinition().getComponentName(), element);
                String type = (String) queryFields.get("ExportType");
                queryTypes.put(element, type);
            }
        }
        return queryTypes;
    }

    /** Returns the HeaderDto. This can be used for passing the header info to the Tx, where required.
     * @return the HeaderDto.
     */
    protected HeaderDto getHeaderDto() {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId(getUserSession().getUserId());
            m_headerDto.setVariation(VariationContext.getVariation());
        }
        return m_headerDto;
    }

    /** Interprets the value of the 'sortDropDown' property adding suitable values to the input Dto.
     * @param inputDto The Dto to which the orderby fields will be added.
     */
    protected void addSortCriteria(FinderInDto inputDto) {
        if (getSortDropDown() != null) {
            // these variables will be used while interpreting the sort field
            String token = null;
            boolean startOfField = true;
            String field = null;
            Boolean isAscending = null;

            StringTokenizer str = new StringTokenizer(getSortDropDown(), " ,", true);
            while (str.hasMoreTokens()) {
                token = str.nextToken();
                if (token.equals(COMMA)) {
                    // check for an existing field
                    if (field != null) {
                        if (isAscending != null)
                            inputDto.addOrderByFields(new OrderByField(field, isAscending));
                        else
                            inputDto.addOrderByFields(new OrderByField(field));
                        // reset the fields
                        field = null;
                        isAscending = null;
                    }
                    startOfField = true;
                } else if (token.equals(SPACE)) {
                    // do nothing
                } else if (startOfField) {
                    field = token;
                    startOfField = false;
                } else {
                    if (token.toUpperCase().equals(DESC))
                        isAscending = Boolean.FALSE;
                    else
                        isAscending = Boolean.TRUE;
                    if (field != null) {
                        if (isAscending != null)
                            inputDto.addOrderByFields(new OrderByField(field, isAscending));
                        else
                            inputDto.addOrderByFields(new OrderByField(field));
                        // reset the fields
                        field = null;
                        isAscending = null;
                    }
                }
            }
            // one final check
            if (field != null) {
                if (isAscending != null)
                    inputDto.addOrderByFields(new OrderByField(field, isAscending));
                else
                    inputDto.addOrderByFields(new OrderByField(field));
            }
        }
    }

    /** Update the DropDownModel for the MaxRecords field, if one already exists. */
    protected void updateMaxRecordsModel() {
        DropDownModel wm = (DropDownModel) getUserSession().getWidgetCache(getComponentId()).getModel(FinderForm.MAX_RECORDS);
        if (wm != null)
            DropDownController.updateModel(getMaxRecords().toString(), wm);
    }

    /** Removes the 'rows' GridModel from the WidgetCache.
     */
    protected void uncacheRowsModel() {
        getUserSession().getWidgetCache(getComponentId()).removeModel(FinderForm.ROWS);
    }

    /** The Component should provide an implementation for this method to perform the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected abstract FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException;

    /** The Component should provide an implementation for this method to return the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected abstract String getCriteriaFormName();

    /** The Component should provide an implementation for this method to return the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected abstract String getResultsFormName();

    /** The Component should provide an implementation for this method to return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected abstract String getConsolidatedCriteriaAndResultsFormName();

    /** The Component should provide an implementation for this method to return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected abstract String getExcelFormName();

    /** The Component should provide an implementation for this method to return the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected abstract String getXmlFormName();

    /**
     * Getter for property m_savedQueryId
     * @return selected saved query
     */
    public Long getSavedQueryId() {
        return m_savedQueryId;
    }

    public void setSavedQueryId(Long savedQueryId) {
        m_savedQueryId = savedQueryId;
    }

    public CodeHelperOutElementDto getSavedQueryIdCodes() {
        if (m_queryIdCodes == null) {
            initializeSavedQueries();
        }
        return m_queryIdCodes;
    }

    public String getNewQueryName() {
        return m_newQueryName;
    }

    public void setNewQueryName(String newQueryName) {
        m_newQueryName = newQueryName;
    }

    public void loadDefaultQuery() throws ApplicationException {
        IContextManager contextManager = ContextManagerFactory.instance();

        QuerySaver querySaver = new QuerySaver(contextManager);

        String defaultQueryId = querySaver.getDefaultQueryId(this.getComponentDefinition().getComponentName());
        if (defaultQueryId != null) {
            setSavedQueryId(new Long(defaultQueryId));
            loadQuery();
        } else {
            throw new QueryNotFoundException("Default Query");
    }
    }

    public void loadQuery() throws ApplicationException {
        if (getSavedQueryId() != null) {

            getUserSession().getWidgetCache(getComponentId()).clear();

            Map queryData = null;
            IContextManager contextManager = ContextManagerFactory.instance();

            QuerySaver querySaver = new QuerySaver(contextManager);

            queryData = querySaver.getSavedQueryFields(this.getComponentDefinition().getComponentName(), getSavedQueryId().toString() );

            if (queryData != null) {
                setQueryData(queryData);
                String defaultQueryId = querySaver.getDefaultQueryId(this.getComponentDefinition().getComponentName());
                if (defaultQueryId != null && defaultQueryId.equals(getSavedQueryId().toString()))
                    setDefaultQueryYn(new Boolean(true));
                else
                    setDefaultQueryYn(new Boolean(false));

                if (querySaver.savedQueryHasShortcut(this.getComponentDefinition().getComponentName(), getSavedQueryId().toString()))
                    setQueryHasShortcutYn(new Boolean(true));
                else
                    setQueryHasShortcutYn(new Boolean(false));

                setNewQueryName(querySaver.getSavedQueryName(this.getComponentDefinition().getComponentName(),
                                                         getSavedQueryId().toString()));
            } else {
                throw new QueryNotFoundException(getSavedQueryId().toString());
            }
        }

    }

    /** deleteQuery
     *
     * @throws ApplicationException
     *
     * Deletes the currntly selected saved query
     */
    public void deleteQuery() throws ApplicationException {
        if (getSavedQueryId() != null) {

            IContextManager contextManager = ContextManagerFactory.instance();

            QuerySaver querySaver = new QuerySaver(contextManager);

            try {
                querySaver.removeSavedQuery(getComponentDefinition().getComponentName(), getSavedQueryId().toString());
            } catch (IOException e) {
                log.error("IOError removing Queries ", e);
                throw new GetQueryDataException(e);
            }
        }
        initializeSavedQueries();
        getUserSession().getWidgetCache(getComponentId()).clear();
    }

    /**
     * setQueryData
     * @param queryData the fields and values to set
     * @throws ApplicationException
     *
     * Sets the stored values
     */
    private void setQueryData(Map queryData) throws ApplicationException {

        for (Iterator queryDataKeysIterator = queryData.keySet().iterator(); queryDataKeysIterator.hasNext();) {
            String queryDataName = (String) queryDataKeysIterator.next();
            String queryDataValue = (String)queryData.get(queryDataName);

            try {
                BeanHelper.setField(this,queryDataName, queryDataValue);
            } catch (IntrospectionException e) {
                log.error("IIntrospectionException setting " + queryDataName, e);
                throw new SetQueryDataException(e);
            } catch (IllegalAccessException e) {
                log.error("IllegalAccessException setting " + queryDataName, e);
                throw new SetQueryDataException(e);
            } catch (InvocationTargetException e) {
                log.error("InvocationTargetException setting " + queryDataName, e);
                throw new SetQueryDataException(e);
            } catch (NullPointerException e) {
                log.error("NullPointerException setting " + queryDataName, e);
                throw new SetQueryDataException(e);
            }
        }
    }

    /**
     * saveQuery
     * @throws ApplicationException
     *
     * Saves the current query
     */
    public void saveQuery() throws ApplicationException {

        Map queryData = null;

        queryData = getQueryData();
        queryData.putAll(getCommonQueryData());

        try {

            IContextManager contextManager = ContextManagerFactory.instance();

            QuerySaver querySaver = new QuerySaver(contextManager);
            querySaver.saveQuery(getNewQueryName(),
                    this.getComponentDefinition().getComponentName(),
                    queryData,
                    getDefaultQueryYn().booleanValue(),
                    getQueryHasShortcutYn().booleanValue());
        } catch (IOException e) {
            log.error("IOError saving Queries ", e);
            throw new GetQueryDataException(e);
        }
        initializeSavedQueries();
        getUserSession().getWidgetCache(getComponentId()).clear();
    }

    /**
     * getCommonQueryData
     * @return a map of the fields that are common to all
     * Finder screens:maxRecordCount, exportType and Sort
     * @throws GetQueryDataException
     */
    private Map getCommonQueryData() throws GetQueryDataException{
        Map retVal = new HashMap();

        try {
            // Store Export Type
            retVal.put("ExportType", BeanHelper.getField(this, "exportType"));
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException getting ExportTpe" , e);
            throw new GetQueryDataException(e);
        }

        try {
            // Store Max Records
            retVal.put("MaxRecords", BeanHelper.getField(this, "maxRecords"));
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException getting maxRecords" , e);
            throw new GetQueryDataException(e);
        }

        try {
            // Store SortDropDown
            retVal.put("SortDropDown", BeanHelper.getField(this, "sortDropDown"));
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException getting sortDropDown" , e);
            throw new GetQueryDataException(e);
        }

        return retVal;
    }

    /**
     * getQueryData
     * @return map of all fields and data to be saved
     * @throws GetQueryDataException
     */
    private Map getQueryData() throws GetQueryDataException {

        List fields = locateFinderFields();

        Map retVal = generateFieldValuesMap(fields);

        return retVal;
    }

    /**
     * generateFieldValuesMap
     * @param fields to save values of
     * @return of fields and values
     * @throws GetQueryDataException
     */
    private Map generateFieldValuesMap(List fields) throws GetQueryDataException {
        Map retVal = new HashMap();
        Iterator fieldsIterator = fields.iterator();
        while (fieldsIterator.hasNext()) {

            String fieldName = (String) fieldsIterator.next();

            try {
                retVal.put(fieldName, BeanHelper.getField(this, fieldName));
            } catch (NoSuchMethodException e) {
                log.error("NoSuchMethodException getting " + fieldName, e);
                throw new GetQueryDataException(e);
            }
        }
        return retVal;
    }

    /** locateFinderFields
     *
     * @return a list of the fields to store the values of
     * @throws GetQueryDataException
     *
     * This method looks for all fields that have an associated Dd
     * method.  It will not capture fields that are grid models, such
     * as check boxes.  To capture a grid model, the individual finder
     * component should extend this method, call super.locateFinderFields
     *
     */
    protected List locateFinderFields() throws GetQueryDataException {
        List fields = new ArrayList();

        Method[] myMethods = this.getClass().getMethods();

        for (int loop = 0; loop < myMethods.length; loop++)
        {
            String methodName = myMethods[loop].getName();
            if (methodName.startsWith("get"))
            {
                if (methodName.endsWith("Dd"))
                {
                    String requiredFieldName = StringHelper.getJavaBeanStyle(methodName.substring(3));
                    fields.add(requiredFieldName);
                    fields.add(requiredFieldName.substring(0,(requiredFieldName.length() -2)));
                }
            }
        }
        return fields;
    }

    class QueryNotFoundException extends ApplicationException {
        QueryNotFoundException(String queryName) {
            super("Jaffa.Components.Finder.savedQueryNotFound",new Object[] {queryName});
        }
    }
}
