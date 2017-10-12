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

package org.jaffa.components.lookup;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.FinderComponent2;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.TagHelper;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineException;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.CssParser;

/** This is the base class for all Lookup components.
 * It inherits the properties from FinderComponent.
 * Additionally it has the following properties -
 *    1- targetFields : This will have the target-fields to be set based on the selected values from the Lookup.
 *    2- multiSelectLookupListeners : This will maintain the listeners to be notified when row(s) are selected in the Lookup.
 *    3- selectLookupListeners : This will maintain the listeners to be notified when row is selected in the Lookup.
 *    4- autoSelect: For multiSelectMode, this will simulate the selection of all records. For singleSelectMode, this will simulate the selection of a record, if the query returned only one record. For non-modal mode and if the 'request'is available in the context, this will simulate the selection of a record, if the query returned only one record.
 *    5- errorIfNoRows: If no rows are returned, then in modal mode, null data will be passed to the registered listener, and the FormKey of the calling component will be returned. In non-modal mode, an ApplicationException will be thrown.
 * It has a convenience method, which should be called by the component's Action class to forward to the generic lookup jsp.
 * @author  GautamJ
 */
public abstract class LookupComponent2 extends FinderComponent2 {
    
    private static final Logger log = Logger.getLogger(LookupComponent2.class);
    
    /** A constant used to pass the selected values to the Http Request stream.*/
    public static final String LOOKUP_ATTRIBUTE = "org.jaffa.components.lookup.LookupComponent.Lookup";
    public static final String LOOKUP_STYLE_ATTRIBUTE = "org.jaffa.components.lookup.LookupComponent.LookupStyle";
    
    /** A constant used to store the CheckBoxModel for each row in the GridModel in the FormBean.*/
    public static final String MULTI_SELECT_CHECKBOX = "LookupComponent.MultiSelectCheckBox";
    
    private static final String LOOKUP_JSP_NAME = "jaffa_lookup";
    private static final FormKey LOOKUP_FORM_KEY = new FormKey(LOOKUP_JSP_NAME, null);
    
    private String m_targetFields;
    private Collection m_multiSelectLookupListeners;
    private Collection m_selectLookupListeners;
    private Boolean m_autoSelect;
    private Boolean m_errorIfNoRows;
    private String m_returnStyle;

    /**
     * Pass all derived classes to the static context to be initialized.
     */
    public LookupComponent2() {
        initializeThis();
    }
    
    // **************************************************************
    // Component Properties
    // **************************************************************
    
    /** Getter for property targetFields.
     * @return Value of property targetFields.
     */
    public String getTargetFields() {
        return m_targetFields;
    }
    
    /** Setter for property targetFields.
     * @param targetFields New value of property targetFields.
     */
    public void setTargetFields(String targetFields) {
        m_targetFields = targetFields;
    }
    
    /** Getter for property autoSelect.
     * @return Value of property autoSelect.
     */
    public Boolean getAutoSelect() {
        return m_autoSelect;
    }
    
    /** Setter for property autoSelect.
     * @param autoSelect New value of property autoSelect.
     */
    public void setAutoSelect(Boolean autoSelect) {
        m_autoSelect = autoSelect;
    }
    
    /** Getter for property errorIfNoRows.
     * @return Value of property errorIfNoRows.
     */
    public Boolean getErrorIfNoRows() {
        return m_errorIfNoRows;
    }
    
    /** Setter for property errorIfNoRows.
     * @param errorIfNoRows New value of property errorIfNoRows.
     */
    public void setErrorIfNoRows(Boolean errorIfNoRows) {
        m_errorIfNoRows = errorIfNoRows;
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addMultiSelectLookupListener(IMultiSelectLookupListener listener) {
        if (m_multiSelectLookupListeners == null)
            m_multiSelectLookupListeners = new HashSet();
        m_multiSelectLookupListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeMultiSelectLookupListener(IMultiSelectLookupListener listener) {
        if (m_multiSelectLookupListeners != null)
            return m_multiSelectLookupListeners.remove(listener);
        else
            return false;
    }
    
    /** Returns a true if a MultiSelectLookupListener has been registered with this Lookup.
     * The associated JSP will use this method to determine if multi-select capability is to be presented to the user.
     * @return true if a MultiSelectLookupListener has been registered with this Lookup.
     */
    public boolean isInMultiSelectLookupMode() {
        return m_multiSelectLookupListeners != null && m_multiSelectLookupListeners.size() > 0;
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addSelectLookupListener(ISelectLookupListener listener) {
        if (m_selectLookupListeners == null)
            m_selectLookupListeners = new HashSet();
        m_selectLookupListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeSelectLookupListener(ISelectLookupListener listener) {
        if (m_selectLookupListeners != null)
            return m_selectLookupListeners.remove(listener);
        else
            return false;
    }
    
    /** Returns a true if a SelectLookupListener has been registered with this Lookup.
     * The associated JSP will use this method to determine if the lookup is in modal-mode.
     * @return true if a SelectLookupListener has been registered with this Lookup.
     */
    public boolean isInSelectLookupMode() {
        return m_selectLookupListeners != null && m_selectLookupListeners.size() > 0;
    }
    
    
    
    // **************************************************************
    // Additional methods
    // **************************************************************
    
    /** This returns a null, since the Lookup components are not expected to serve up Excel content.
     * @return null.
     */
    protected String getExcelFormName() {
        return null;
    }
    
    /** This returns a null, since the Lookup components are not expected to serve up XML content.
     * @return null.
     */
    protected String getXmlFormName() {
        return null;
    }
    
    /** This will remove the 'lookup' attribute from the request stream.
     * It will then invoke the quitAndReturnToCallingScreen() method, if any listeners were registered.
     * Otherwise it will quit() and return a FormKey object for the generic lookup jsp, which will close the browser.
     * @param request The HTTP request we are processing
     * @return a FormKey object.
     */
    public FormKey quitLookup(HttpServletRequest request) {
        if (request != null)
            request.removeAttribute(LOOKUP_ATTRIBUTE);
        
        if (isInMultiSelectLookupMode() || isInSelectLookupMode()) {
            return quitAndReturnToCallingScreen();
        } else {
            // Quit this component
            quit();
            
            // forward to the jsp
            return LOOKUP_FORM_KEY;
        }
    }
    
    /** Invokes the rowsSelected() method of the registered IMultiSelectLookupListener objects in the same thread.
     * It will then invoke the quitLookup() method.
     * @param request The HTTP request we are processing
     * @param event The event object containing the row(s) selected.
     * @return a FormKey object.
     */
    public FormKey performMultiSelectLookup(HttpServletRequest request, MultiSelectLookupEvent event)
    throws ApplicationExceptions, FrameworkException {
        invokeMultiSelectLookupListeners(event);
        return quitLookup(request);
    }
    
    /** Invokes the rowSelected() method of the registered ISelectLookupListener objects in the same thread.
     * It will then invoke the quitLookup() method.
     * @param request The HTTP request we are processing
     * @param event The event object containing the row(s) selected.
     * @return a FormKey object.
     */
    public FormKey performSelectLookup(HttpServletRequest request, SelectLookupEvent event)
    throws ApplicationExceptions, FrameworkException {
        invokeSelectLookupListeners(event);
        return quitLookup(request);
    }
    
    
    /** This will add the 'lookup' attribute on the request stream, with a Map containing the fieldnames (from the targetFields property) and values (from the input selectedRow).
     * It will then invoke the quit() method on itself.
     * Finally it will return a FormKey object for the generic lookup jsp.
     * @param request The HTTP request we are processing
     * @param selectedRow The row that gets selected on the Results screen.
     * @return a FormKey object for the generic lookup jsp.
     */
    public FormKey performLookup(HttpServletRequest request, Object selectedRow) {
        Map lookupMap = new HashMap();
        Class outRowDtoClass = null;
        if (selectedRow != null && getTargetFields() != null && getTargetFields().length() > 0) {
            StringTokenizer tokenizer = new StringTokenizer(getTargetFields(), ";");
            while (tokenizer.hasMoreTokens()) {
                String key = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    String valueField = tokenizer.nextToken();
                    Object value = null;
                    if (selectedRow instanceof Map)
                        value = ((Map) selectedRow).get(valueField);
                    else {
                        try {
                            value = BeanHelper.getField(selectedRow, valueField);
                        } catch (Exception e) {
                            // do nothing
                            log.warn("Unable to get value from field " + valueField + " from object " + selectedRow);
                        }
                    }
                    
                    if (value != null && value instanceof SimpleWidgetModel)
                        value = ((SimpleWidgetModel) value).getWidgetValue();
                    // Format non-String values
                    if (value != null && !(value instanceof String)) {
                        if (outRowDtoClass == null)
                            outRowDtoClass = determineOutRowDtoClass();
                        String layout = obtainLayout(outRowDtoClass, valueField);
                        value = Formatter.format(value, layout);
                    }
                    lookupMap.put(key, (value == null ? "" : value));
                }
            }
        }
        if (lookupMap.size() > 0) {
            request.setAttribute(LOOKUP_ATTRIBUTE, lookupMap);
            try {
                request.setAttribute(LOOKUP_STYLE_ATTRIBUTE, CssParser.parse(m_returnStyle));
            } catch (ParseException e) {
                log.error("Internal error with returnStlye format:" + m_returnStyle, e);
            }
        } else {
            request.removeAttribute(LOOKUP_ATTRIBUTE);
            request.removeAttribute(LOOKUP_STYLE_ATTRIBUTE);
        }
        
        // Quit this component
        quit();
        
        // forward to the jsp
        return LOOKUP_FORM_KEY;
    }
    
    /** Returns a Collection of IMultiSelectLookupListener objects.
     * @return a Collection of IMultiSelectLookupListener objects.
     */
    protected Collection getMultiSelectLookupListeners() {
        return m_multiSelectLookupListeners;
    }
    
    /** Invokes the rowsSelected() method of the registered IMultiSelectLookupListener objects in the same thread.
     * @param event The event object containing the row(s) selected.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void invokeMultiSelectLookupListeners(MultiSelectLookupEvent event)
    throws ApplicationExceptions, FrameworkException {
        if (m_multiSelectLookupListeners != null) {
            for (Iterator i = m_multiSelectLookupListeners.iterator(); i.hasNext(); )
                ( (IMultiSelectLookupListener) i.next() ).rowsSelected(event);
        }
    }
    
    /** Returns a Collection of ISelectLookupListener objects.
     * @return a Collection of ISelectLookupListener objects.
     */
    protected Collection getSelectLookupListeners() {
        return m_selectLookupListeners;
    }
    
    /** Invokes the rowsSelected() method of the registered ISelectLookupListener objects in the same thread.
     * @param event The event object containing the row(s) selected.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void invokeSelectLookupListeners(SelectLookupEvent event)
    throws ApplicationExceptions, FrameworkException {
        if (m_selectLookupListeners != null) {
            for (Iterator i = m_selectLookupListeners.iterator(); i.hasNext(); )
                ( (ISelectLookupListener) i.next() ).rowSelected(event);
        }
    }
    
    /** Invokes the performInquiry() method and then returns the FormKey for the Results screen.
     * If the consolidatedCriteriaAndResults property has been set to true, then the FormKey for consolidatedCriteriaAndResults screen will be returned.
     * This will then process the 'errorIfNoRows' and 'autoSelect' properties.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Results screen.
     */
    public FormKey displayResults() throws ApplicationExceptions, FrameworkException {
        FormKey fk = super.displayResults();
        
        FormKey fk1 = raiseErrorIfNoRows();
        if (fk1 != null)
            fk = fk1;
        else {
            fk1 = performAutoSelect();
            if (fk1 != null)
                fk = fk1;
        }
        return fk;
    }
    
    /**
     * This method has effect only if the property 'errorIfNoRows' is set to true and if no rows are retrieved by a query.
     * In modal mode, null data will be passed to the registered listener, and the FormKey of the calling component will be returned
     * In non-modal mode, an ApplicationException will be thrown.
     * @return the A null will be returned if the query returned data.
     * @throws ApplicationExceptions if in non-modal mode, no rows are returned by the query.
     */
    protected FormKey raiseErrorIfNoRows() throws ApplicationExceptions, FrameworkException {
        if (m_errorIfNoRows != null && m_errorIfNoRows.booleanValue()) {
            int noOfRows = 0;
            if (getFinderOutDto() != null) {
                try {
                    Class finderOutDtoClass = getFinderOutDto().getClass();
                    Method method = finderOutDtoClass.getMethod("getRows", (Class[]) null);
                    Object rows = method.invoke(getFinderOutDto(), (Object[]) null);
                    if (rows != null && rows.getClass().isArray())
                        noOfRows = Array.getLength(rows);
                } catch (Exception e) {
                    String str = "Exception thrown when trying to check if zero rows were retrieved";
                    log.warn(str, e);
                    return null;
                }
            }
            if (noOfRows == 0) {
                if (isInSelectLookupMode()) {
                    if (log.isDebugEnabled())
                        log.debug("Number of rows is 0. Will invoke the SelectLookupListeners");
                    return performSelectLookup(null, new SelectLookupEvent(this, null));
                } else if (isInMultiSelectLookupMode()) {
                    if (log.isDebugEnabled())
                        log.debug("Number of rows is 0. Will invoke the MultiSelectLookupListeners");
                    return performMultiSelectLookup(null, new MultiSelectLookupEvent(this, null));
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Number of rows is 0. Will throw an exception");
                    throw new ApplicationExceptions(new DomainObjectNotFoundException("Data"));
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("No error raised since rows exceed 0: " + noOfRows);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("No error will be raised since the 'errorIfNoRows' property is not set to true");
        }
        return null;
    }
    
    /**
     * This method has effect only if the property 'autoSelect' is set to true.
     * For multiSelectMode, this will simulate the selection of all records.
     * For singleSelectMode, this will simulate the selection of a record, if the query returned only one record.
     * For non-modal mode and if the 'request'is available in the context, this will simulate the selection of a record, if the query returned only one record.
     * @return the FormKey for the calling component after performing an autoSelect. A null will be returned in case no autoSelect is performed.
     */
    protected FormKey performAutoSelect() throws ApplicationExceptions, FrameworkException {
        try {
            if (m_autoSelect != null && m_autoSelect.booleanValue() && getFinderOutDto() != null) {
                Class finderOutDtoClass = getFinderOutDto().getClass();
                Method method = finderOutDtoClass.getMethod("getRows", (Class[]) null);
                Object rows = method.invoke(getFinderOutDto(), (Object[]) null);
                if (rows != null && rows.getClass().isArray()) {
                    if (isInMultiSelectLookupMode()) {
                        if (log.isDebugEnabled())
                            log.debug("Will invoke the MultiSelectLookupListeners to autoSelect all the rows");
                        return performMultiSelectLookup(null, new MultiSelectLookupEvent(this, (Object[]) rows));
                    } else if (Array.getLength(rows) == 1) {
                        Object selectedRow = Array.get(rows, 0);
                        if (isInSelectLookupMode()) {
                            if (log.isDebugEnabled())
                                log.debug("Number of rows is 1. Will invoke the SelectLookupListeners to autoSelect the row");
                            return performSelectLookup(null, new SelectLookupEvent(this, selectedRow));
                        } else {
                            Object request = ContextManagerFactory.instance().getProperty("request");
                            if (request != null && request instanceof HttpServletRequest) {
                                if (log.isDebugEnabled())
                                    log.debug("Number of rows is 1. Will autoSelect the row");
                                return performLookup((HttpServletRequest) request, selectedRow);
                            } else {
                                if (log.isInfoEnabled())
                                    log.info("HttpServletRequest not found in the context. AutoSelect cannot be performed");
                            }
                        }
                    } else {
                        if (log.isDebugEnabled())
                            log.debug("Number of rows exceeds 1. AutoSelect cannot be performed");
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("AutoSelected will not be performed since there is no output from the query");
                }
            } else {
                if (log.isDebugEnabled()) {
                    StringBuffer buf = new StringBuffer("AutoSelected will not be performed since ");
                    if (m_autoSelect == null || !m_autoSelect.booleanValue())
                        buf.append("the 'autoSelect' property is not set to true");
                    else if (getFinderOutDto() == null)
                        buf.append("there is no output from the query");
                    log.debug(buf);
                    
                }
            }
        } catch (ApplicationExceptions e) {
            throw e;
        } catch (FrameworkException e) {
            throw e;
        } catch (Exception e) {
            String str = "Exception thrown when trying to autoSelect";
            log.warn(str, e);
        }
        return null;
    }
    
    /** Getter for the attribute returnStyle.
     * @return Value of attribute returnStyle.
     */
    public String getReturnStyle() {
        return m_returnStyle;
    }
    
    /** Setter for the attribute returnStyle.
     * @param returnStyle New value of attribute returnStyle.
     */
    public void setReturnStyle(String returnStyle) {
        m_returnStyle = returnStyle;
    }
    
    
    /** Determine the layout from either the PropertyRuleIntrospector or FieldMetaData.
     */
    private String obtainLayout(Class outRowDtoClass, String propertyName) {
        String layout = null;
        
        // Obtain the layout from the PropertyRuleIntrospector
        if (outRowDtoClass != null) {
            try {
                if (log.isDebugEnabled())
                    log.debug("Obtaining layout from PropertyRuleIntrospector for " + propertyName + " of " + outRowDtoClass.getName());
                IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
                if (rulesEngine != null) {
                    IPropertyRuleIntrospector propertyRuleIntrospector = rulesEngine.getPropertyRuleIntrospector(outRowDtoClass, propertyName);
                    if (propertyRuleIntrospector != null) {
                        layout = propertyRuleIntrospector.getLayout();
                        if (log.isDebugEnabled())
                            log.debug("Obtained layout from PropertyRuleIntrospector for " + propertyName + " of " + outRowDtoClass.getName() + ": " + layout);
                    }
                }
            } catch (RulesEngineException e) {
                // do nothing
                if (log.isDebugEnabled())
                    log.debug("Unable to obtain PropertyRuleIntrospector for " + propertyName + " of " + outRowDtoClass.getName());
            }
        }
        
        // Search for an appropriate FieldMetaData if the layout cannot be found from the PropertyRuleIntrospector
        // Note that this is a hack. It uses a regular expression to determine the domain class from the component class!
        if (layout == null) {
            String className = this.getClass().getName();
            try {
                if (log.isDebugEnabled())
                    log.debug("Obtaining layout from FieldMetaData for " + propertyName + " of " + className);
                FieldMetaData meta = TagHelper.getFieldMetaData(className, propertyName);
                if (meta != null) {
                    layout = (String) BeanHelper.getField(meta, "layout");
                    if (log.isDebugEnabled())
                        log.debug("Obtained layout from " + meta + ": " + layout);
                }
            } catch (Exception e) {
                // do nothing
                if (log.isDebugEnabled())
                    log.debug("Error in determining FieldMetaData for " + className + '.' + propertyName, e);
            }
        }
        return layout;
    }
    
    /** Use introspection to determine the OutRowDto class for this component.
     * Note: The getFinderOutDto() for the current instance should return a non-null value for this to work.
     */
    private Class determineOutRowDtoClass() {
        Class outRowDtoClass = null;
        if (this.getFinderOutDto() != null) {
            Class finderOutDtoClass = this.getFinderOutDto().getClass();
            try {
                //Lets assumes that the finderOutDto class has a method 'public XyzOutRowDto[] getRows()'
                if (log.isDebugEnabled())
                    log.debug("Determining the OutRowDto class for " + finderOutDtoClass.getName());
                Method m = finderOutDtoClass.getMethod("getRows", (Class[]) null);
                outRowDtoClass = m.getReturnType().getComponentType();
                if (log.isDebugEnabled())
                    log.debug("The OutRowDto class is: " + outRowDtoClass);
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Error in determining the OutRowDto class for " + finderOutDtoClass.getName(), e);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("The OutRowDto class cannot be determined since the getFinderOutDto() has returned a NULL");
        }
        return outRowDtoClass;
    }
    
}
