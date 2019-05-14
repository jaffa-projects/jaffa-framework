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

package org.jaffa.components.finder;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.components.codehelper.dto.CodeHelperOutCodeDto;
import org.jaffa.components.codehelper.dto.CodeHelperOutElementDto;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.controller.DropDownController;
import org.jaffa.presentation.portlet.widgets.controller.EditBoxController;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.controller.RadioButtonController;
import org.jaffa.presentation.portlet.widgets.model.DropDownModel;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.RadioButtonModel;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;

/** This is the base class for all FinderComponent FormBeans.
 * It has the following properties to support the Criteria Screen -
 *    1- sortDropDown : The sort criteria to use for the inquiry
 *    2- exportType : The export option to use for the inquiry (initialized to regular Web Pages)
 *    3- maxRecords: The maximum number of records to retrieve. This will be initialized to the first value in the option list.
 *
 * It has the following properties to support the Results Screen -
 *    1- rows : This GridModel object will be used for displaying the data in the Results screen
 *    2- moreRecordsExist : This indicates if more records will be retrieved, if the query is done by using a higher value for the maxRecords dropdown.
 *
 * A Finder FormBean will have to provide an implementation for the populateRows() method.
 * @author GautamJ
 */
public abstract class FinderForm extends FormBase {

    private static final Logger log = Logger.getLogger(FinderForm.class);

    /** A global constant for the sortDropDown widget.*/
    public static final String SORT_DROP_DOWN = "sortDropDown";
    /** A global constant for the exportType widget.*/
    public static final String EXPORT_TYPE = "exportType";
    /** A global constant for the maxRecords widget.*/
    public static final String MAX_RECORDS = "maxRecords";
    /** A global constant for the rows widget.*/
    public static final String ROWS = "rows";

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public FinderForm() {
        StaticContext.configure(this);
    }


    // ************************************************************
    // Methods used by the Criteria Screen
    // ************************************************************

    /** Getter for property sortDropDown.
     * @return Value of property sortDropDown.
     */
    public String getSortDropDown() {
        return ( (FinderComponent2) getComponent() ).getSortDropDown();
    }

    /** Setter for property sortDropDown.
     * @param sortDropDown New value of property sortDropDown.
     */
    public void setSortDropDown(String sortDropDown) {
        ( (FinderComponent2) getComponent() ).setSortDropDown(sortDropDown);
    }

    /** Getter for DropDown property sortDropDown.
     * @return Value of DropDown property sortDropDown.
     */
    public DropDownModel getSortDropDownWM() {
        DropDownModel sortDropDownModel = (DropDownModel) getWidgetCache().getModel(SORT_DROP_DOWN);
        if (sortDropDownModel == null) {
            String sortDropDown = getSortDropDown();
            if (sortDropDown != null)
                sortDropDownModel = new DropDownModel(sortDropDown);
            else
                sortDropDownModel = new DropDownModel("");
            getWidgetCache().addModel(SORT_DROP_DOWN, sortDropDownModel);
        }
        return sortDropDownModel;
    }

    /** Setter for DropDown property sortDropDown. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of DropDown property sortDropDown.
     */
    public void setSortDropDownWV(String value) {
        DropDownController.updateModel(value, getSortDropDownWM());
    }


    /** Getter for property exportType.
     * @return Value of property exportType.
     */
    public String getExportType() {
        return ( (FinderComponent2) getComponent() ).getExportType();
    }

    /** Setter for property exportType.
     * @param exportType New value of property exportType.
     */
    public void setExportType(String exportType) {
        ( (FinderComponent2) getComponent() ).setExportType(exportType);
    }

    /** Getter for property exportType.
     * @return Value of property exportType.
     */
    public RadioButtonModel getExportTypeWM() {
        RadioButtonModel exportTypeModel = (RadioButtonModel) getWidgetCache().getModel(EXPORT_TYPE);
        if (exportTypeModel == null) {
            String exportType = getExportType();
            if (exportType != null)
                exportTypeModel = new RadioButtonModel(exportType);
            else
                exportTypeModel = new RadioButtonModel(FinderComponent2.EXPORT_TYPE_WEB_PAGE);
            getWidgetCache().addModel(EXPORT_TYPE, exportTypeModel);
        }
        return exportTypeModel;
    }

    /** Setter for property exportType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property exportType.
     */
    public void setExportTypeWV(String value) {
        RadioButtonController.updateModel(value, getExportTypeWM());
    }


    /** Getter for property maxRecords.
     * @return Value of property maxRecords.
     */
    public Integer getMaxRecords() {
        return ( (FinderComponent2) getComponent() ).getMaxRecords();
    }

    /** Setter for property maxRecords.
     * @param maxRecords New value of property maxRecords.
     */
    public void setMaxRecords(Integer maxRecords) {
        ( (FinderComponent2) getComponent() ).setMaxRecords(maxRecords);
    }

    /** Getter for DropDown property maxRecords.
     * @return Value of DropDown property maxRecords.
     */
    public DropDownModel getMaxRecordsWM() {
        DropDownModel maxRecordsModel = (DropDownModel) getWidgetCache().getModel(MAX_RECORDS);
        if (maxRecordsModel == null) {
            // create a list of valid values (Long objects) for the maxRecords dropdown
            List maxRecordsList = new ArrayList();
            Map maxRecordsDropDownOptions = CriteriaDropDownOptions.getMaxRecordsDropDownOptions();
            for (Iterator i = maxRecordsDropDownOptions.entrySet().iterator(); i.hasNext();) {
                Map.Entry me = (Map.Entry) i.next();
                maxRecordsList.add(me.getKey());
            }

            // Now determine an initial value for the dropdown
            Integer maxRecords = getMaxRecords();
            Long initialValue = maxRecords != null ? new Long(maxRecords.intValue()) : null;
            if (initialValue == null || !maxRecordsList.contains(initialValue))
                initialValue = CriteriaDropDownOptions.getDefaultMaxRecordsDropDownOption();

            // Now build the DropDownModel, adding the options
            maxRecordsModel = new DropDownModel(initialValue.toString());
            for (Iterator i = maxRecordsDropDownOptions.entrySet().iterator(); i.hasNext();) {
                Map.Entry me = (Map.Entry) i.next();
                maxRecordsModel.addOption((String) me.getValue(), me.getKey().toString());
            }

            getWidgetCache().addModel(MAX_RECORDS, maxRecordsModel);
        }
        return maxRecordsModel;
    }

    /** Setter for DropDown property maxRecords. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of DropDown property maxRecords.
     */
    public void setMaxRecordsWV(String value) {
        DropDownController.updateModel(value, getMaxRecordsWM());
    }


    /** This method should be invoked to ensure a valid state of the FormBean.
     * It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        String value = getSortDropDownWM().getValue();
        if (value != null && value.trim().length() == 0) {
            value = null;
        }
        setSortDropDown(value);

        value = getExportTypeWM().getValue();
        if (value != null && value.trim().length() == 0) {
            value = null;
        }
        setExportType(value);

        value = getMaxRecordsWM().getValue();
        if (value == null || value.trim().length() == 0) {
            setMaxRecords(null);
        }
        else {
            setMaxRecords(Integer.valueOf(value));
        }
        return true;
    }




    // ************************************************************
    // Methods used by the Results Screen
    // ************************************************************

    /** Getter for property rows. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property userRef1.
     */
    public GridModel getRowsWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel(ROWS);
        if (rows == null) {
            rows = new GridModel();
            getWidgetCache().addModel(ROWS, rows);
            populateRows(rows);
        }
        return rows;
    }

    /** Setter for property rows. This is invoked by the servlet, when a post is done on the Results screen.
     * It sets the selected rows on the model.
     * @param value New value of property userRef1.
     */
    public void setRowsWV(String value) {
        GridController.updateModel(value, getRowsWM());
    }

    /** Getter for property moreRecordsExist.
     * @return Value of property moreRecordsExist.
     */
    public boolean getMoreRecordsExist() {
        FinderOutDto finderOutDto = ( (FinderComponent2) getComponent() ).getFinderOutDto();
        if (finderOutDto != null && finderOutDto.getMoreRecordsExist() != null)
            return finderOutDto.getMoreRecordsExist().booleanValue();
        else
            return false;
    }

    /** Getter for property numberOfRecords.
     * @return Value of property numberOfRecords.
     */
    public Long getNumberOfRecords() {
        GridModel rows = getRowsWM();
        return rows != null && rows.getRows() != null ? new Long(rows.getRows().size()) : new Long(0);
    }

    /** Getter for the queryLastRunOn property.
     * @return the Date/Time the query was last run.
     */
    public DateTime getQueryLastRunOn() {
        return ( (FinderComponent2) getComponent() ).getQueryLastRunOn();
    }

    /** The FormBean should provide an implementation for this method.
     * This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public abstract void populateRows(GridModel rows);

    /** Getter for property savedQueryId.
     * @return Value of property savedQueryId.
     */
    public Long getSavedQueryId() {
        return ( (FinderComponent2) getComponent() ).getSavedQueryId();
    }

    /** Setter for property savedQueryId.
     * @param savedQueryId New value of property savedQueryId.
     */
    public void setSavedQueryId(Long savedQueryId) {
        ( (FinderComponent2) getComponent() ).setSavedQueryId(savedQueryId);
    }

    public String getNewQueryName() {
        return ( (FinderComponent2) getComponent() ).getNewQueryName();
    }

    public void setNewQueryName(String newQueryName) {
        ( (FinderComponent2) getComponent() ).setNewQueryName(newQueryName);
    }

    public void setDefaultQueryYn(Boolean defaultQueryYn) {
        ( (FinderComponent2) getComponent() ).setDefaultQueryYn(defaultQueryYn);
    }

    public Boolean getDefaultQueryYn() {
        return ( (FinderComponent2) getComponent() ).getDefaultQueryYn();
    }

    public void setQueryHasShortcutYn(Boolean queryHasShortcutYn) {
        ( (FinderComponent2) getComponent() ).setQueryHasShortcutYn(queryHasShortcutYn);
    }

    public Boolean getQueryHasShortcutYn() {
        return ( (FinderComponent2) getComponent() ).getQueryHasShortcutYn();
    }

    /** Getter for property savedQueryId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property savedQueryId.
     */
    public DropDownModel getSavedQueryIdWM() {
        DropDownModel w_savedQueryId = (DropDownModel) getWidgetCache().getModel("savedQueryId");
        if (w_savedQueryId == null) {
            w_savedQueryId = new DropDownModel((getSavedQueryId() != null ? "" + getSavedQueryId() : ""));
            CodeHelperOutElementDto dto = ( (FinderComponent2) getComponent() ).getSavedQueryIdCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    w_savedQueryId.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }

            getWidgetCache().addModel("savedQueryId", w_savedQueryId);
        }
        return w_savedQueryId;
    }

    public void setSavedQueryIdWV(String value) {
        DropDownController.updateModel(value, getSavedQueryIdWM());
    }

    /** Getter to test if the saved queries exist.
     */
    public boolean isHasSavedQueries() {
        DropDownModel ddm = getSavedQueryIdWM();
        if (ddm.getOptions().size()>1) return true;
        else if (ddm.getOptions().size()==0) return false;
        else {
          SimpleWidgetModel ot = (SimpleWidgetModel) ddm.getOptions().get(0);
          if ("".equals(ot.getWidgetValue())) return false;
          else return true;
        }
    }

    public EditBoxModel getNewQueryNameWM() {
        EditBoxModel w_newQueryName = (EditBoxModel) getWidgetCache().getModel("newQueryName");
        if (w_newQueryName == null) {
            if (getNewQueryName() != null)
                w_newQueryName = new EditBoxModel( getNewQueryName() );
            else
                w_newQueryName = new EditBoxModel();


            getWidgetCache().addModel("newQueryName", w_newQueryName);
        }
        return w_newQueryName;
    }

    public void setNewQueryNameWV(String value) {
        EditBoxController.updateModel(value, getNewQueryNameWM());
    }

    public RadioButtonModel getDefaultQueryYnWM() {
        RadioButtonModel w_defaultQueryYn = (RadioButtonModel) getWidgetCache().getModel("defaultQueryYn");
        if (w_defaultQueryYn == null) {
            String defaultQueryYn = (getDefaultQueryYn() == null ? null : getDefaultQueryYn().toString());
            if (defaultQueryYn != null)
                w_defaultQueryYn = new RadioButtonModel(defaultQueryYn);
            else
                w_defaultQueryYn = new RadioButtonModel(FinderComponent2.DEFAULT_QUERY_NO);
            getWidgetCache().addModel("defaultQueryYn", w_defaultQueryYn);
        }
        return w_defaultQueryYn;
    }

    public void setDefaultQueryYnWV(String value) {
        RadioButtonController.updateModel(value, getDefaultQueryYnWM());
    }

    public RadioButtonModel getQueryHasShortcutYnWM() {
        RadioButtonModel w_queryHasShortcutYn = (RadioButtonModel) getWidgetCache().getModel("queryHasShortcutYn");
        if (w_queryHasShortcutYn == null) {
            String queryHasShortcutYn = (getQueryHasShortcutYn() == null ? null : getQueryHasShortcutYn().toString());
            if (queryHasShortcutYn != null)
                w_queryHasShortcutYn = new RadioButtonModel(queryHasShortcutYn);
            else
                w_queryHasShortcutYn = new RadioButtonModel(FinderComponent2.QUERY_HAS_SHORTCUT_NO);
            getWidgetCache().addModel("queryHasShortcutYn", w_queryHasShortcutYn);
        }
        return w_queryHasShortcutYn;
    }

    public void setQueryHasShortcutYnWV(String value) {
        RadioButtonController.updateModel(value, getQueryHasShortcutYnWM());
    }

    public boolean doValidateForLoadQuery(HttpServletRequest request) {
        String value = null;

        //Set the selectedQuery
        value = getSavedQueryIdWM().getValue();
        if (value == null || value.trim().length() == 0) {
            setSavedQueryId(null);
        }
        else {
            setSavedQueryId(Long.valueOf(value));
        }
        return true;
    }

    public boolean doValidateForSaveQuery(HttpServletRequest request) {
        boolean retVal = true;
        String value = null;

        value = getDefaultQueryYnWM().getValue();
        if (value != null && value.trim().length() == 0) {
            setDefaultQueryYn(null);
        } else {
            setDefaultQueryYn(new Boolean(value));
        }

        value = getQueryHasShortcutYnWM().getValue();
        if (value != null && value.trim().length() == 0) {
            setQueryHasShortcutYn(null);
        } else {
            setQueryHasShortcutYn(new Boolean(value));
        }

        //Set the selectedQuery
        value = getNewQueryNameWM().getValue();
        if (value == null || value.trim().length() == 0) {
            // setNewQueryName(null);
            raiseError(request, "NewQueryName", new MandatoryFieldException("Query Name"));
            retVal = false;
        } else {
            setNewQueryName(value.trim());
        }
        return retVal;
    }

}
