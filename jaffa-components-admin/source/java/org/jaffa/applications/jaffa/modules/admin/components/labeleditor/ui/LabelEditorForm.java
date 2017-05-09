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

package org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.SimpleWidgetController;
import org.jaffa.presentation.portlet.widgets.controller.GridController;

/** This is the FormBean for the Label Editor.
 *
 * @author  GautamJ
 * @version 1.0
 */
public class LabelEditorForm extends FormBase {
    
    private static Logger log = Logger.getLogger(LabelEditorForm.class);
    
    /** The constant which returns the struts-forward for displaying this form */
    public static final String NAME = "admin_labelEditor";
    
    /** Getter for property labelFilter.
     * @return Value of property labelFilter.
     */
    public String getLabelFilter() {
        return ((LabelEditorComponent) getComponent()).getLabelFilter();
    }
    
    /** Setter for property labelFilter.
     * @param labelFilter New value of property labelFilter.
     */
    public void setLabelFilter(String labelFilter) {
        ((LabelEditorComponent) getComponent()).setLabelFilter(labelFilter);
    }
    
    /** Getter for property labelFilter. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property labelFilter.
     */
    public SimpleWidgetModel getLabelFilterWM() {
        SimpleWidgetModel labelFilterModel = (SimpleWidgetModel) getWidgetCache().getModel("labelFilter");
        if (labelFilterModel == null) {
            labelFilterModel = new SimpleWidgetModel(getLabelFilter());
            getWidgetCache().addModel("labelFilter", labelFilterModel);
        }
        return labelFilterModel;
    }
    
    /** Setter for property labelFilter. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property labelFilter.
     */
    public void setLabelFilterWV(String value) {
        SimpleWidgetController.updateModel(value, getLabelFilterWM());
    }
    
    /** Getter for property displayOverridesOnly.
     * @return Value of property displayOverridesOnly.
     */
    public Boolean getDisplayOverridesOnly() {
        return ((LabelEditorComponent) getComponent()).getDisplayOverridesOnly();
    }
    
    /** Setter for property displayOverridesOnly.
     * @param displayOverridesOnly New value of property displayOverridesOnly.
     */
    public void setDisplayOverridesOnly(Boolean displayOverridesOnly) {
        ((LabelEditorComponent) getComponent()).setDisplayOverridesOnly(displayOverridesOnly);
    }
    
    /** Getter for property displayOverridesOnly. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property displayOverridesOnly.
     */
    public SimpleWidgetModel getDisplayOverridesOnlyWM() {
        SimpleWidgetModel displayOverridesOnlyModel = (SimpleWidgetModel) getWidgetCache().getModel("displayOverridesOnly");
        if (displayOverridesOnlyModel == null) {
            displayOverridesOnlyModel = new SimpleWidgetModel(getDisplayOverridesOnly());
            getWidgetCache().addModel("displayOverridesOnly", displayOverridesOnlyModel);
        }
        return displayOverridesOnlyModel;
    }
    
    /** Setter for property displayOverridesOnly. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property displayOverridesOnly.
     */
    public void setDisplayOverridesOnlyWV(String value) {
        SimpleWidgetController.updateModel(value, getDisplayOverridesOnlyWM());
    }
    
    /** Getter for property searchPathForSourceFragments.
     * @return Value of property searchPathForSourceFragments.
     */
    public String getSearchPathForSourceFragments() {
        return ((LabelEditorComponent) getComponent()).getSearchPathForSourceFragments();
    }
    
    /** Setter for property searchPathForSourceFragments.
     * @param searchPathForSourceFragments New value of property searchPathForSourceFragments.
     */
    public void setSearchPathForSourceFragments(String searchPathForSourceFragments) {
        ((LabelEditorComponent) getComponent()).setSearchPathForSourceFragments(searchPathForSourceFragments);
    }
    
    /** Getter for property searchPathForSourceFragments. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property searchPathForSourceFragments.
     */
    public SimpleWidgetModel getSearchPathForSourceFragmentsWM() {
        SimpleWidgetModel searchPathForSourceFragmentsModel = (SimpleWidgetModel) getWidgetCache().getModel("searchPathForSourceFragments");
        if (searchPathForSourceFragmentsModel == null) {
            searchPathForSourceFragmentsModel = new SimpleWidgetModel(getSearchPathForSourceFragments());
            getWidgetCache().addModel("searchPathForSourceFragments", searchPathForSourceFragmentsModel);
        }
        return searchPathForSourceFragmentsModel;
    }
    
    /** Setter for property searchPathForSourceFragments. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property searchPathForSourceFragments.
     */
    public void setSearchPathForSourceFragmentsWV(String value) {
        SimpleWidgetController.updateModel(value, getSearchPathForSourceFragmentsWM());
    }
    
    /** Getter for property sourceFragmentName.
     * @return Value of property sourceFragmentName.
     */
    public String getSourceFragmentName() {
        return ((LabelEditorComponent) getComponent()).getSourceFragmentName();
    }
    
    /** Setter for property sourceFragmentName.
     * @param sourceFragmentName New value of property sourceFragmentName.
     */
    public void setSourceFragmentName(String sourceFragmentName) {
        ((LabelEditorComponent) getComponent()).setSourceFragmentName(sourceFragmentName);
    }
    
    /** Getter for property sourceFragmentName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceFragmentName.
     */
    public SimpleWidgetModel getSourceFragmentNameWM() {
        SimpleWidgetModel sourceFragmentNameModel = (SimpleWidgetModel) getWidgetCache().getModel("sourceFragmentName");
        if (sourceFragmentNameModel == null) {
            sourceFragmentNameModel = new SimpleWidgetModel(getSourceFragmentName());
            getWidgetCache().addModel("sourceFragmentName", sourceFragmentNameModel);
        }
        return sourceFragmentNameModel;
    }
    
    /** Setter for property sourceFragmentName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceFragmentName.
     */
    public void setSourceFragmentNameWV(String value) {
        SimpleWidgetController.updateModel(value, getSourceFragmentNameWM());
    }
    
    /** Getter for property Rows. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property Rows.
     */
    public GridModel getRowsWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("rows");
        if (rows == null) {
            rows = new GridModel();
            populateRows(rows);
            getWidgetCache().addModel("rows", rows);
        }
        return rows;
    }
    
    /** Setter for property Rows. This is invoked by the servlet, when a post is done on the screen.
     * @param value New value of property relatedUserRoles.
     */
    public void setRowsWV(String value) {
        GridController.updateModel(value, getRowsWM());
    }
    
    
    /** This method should be invoked to copy the fields from the FormBean to the component.
     * @return true of the values are copied successfully
     */
    public boolean doValidate() {
        String value = null;
        
        value = getLabelFilterWM().getStringValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLabelFilter(value);
        
        setDisplayOverridesOnly(getDisplayOverridesOnlyWM().getBooleanValue());
        
        value = getSearchPathForSourceFragmentsWM().getStringValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSearchPathForSourceFragments(value);
        
        value = getSourceFragmentNameWM().getStringValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSourceFragmentName(value);
        
        Map labels = ((LabelEditorComponent) getComponent()).getLabels();
        GridModel rows = getRowsWM();
        for (Iterator itr = rows.getRows().iterator(); itr.hasNext(); ) {
            GridModelRow row = (GridModelRow) itr.next();
            String label = (String) row.get(LabelEditorComponent.LABEL);
            String override = ((SimpleWidgetModel) row.get(LabelEditorComponent.OVERRIDE)).getStringValue();
            if (override != null && override.length() == 0)
                override = null;
            Map map = (Map) labels.get(label);
            map.put(LabelEditorComponent.OVERRIDE, override);
        }
        return true;
    }
    
    private void populateRows(GridModel rows) {
        rows.clearRows();
        Map labels = ((LabelEditorComponent) getComponent()).getLabels();
        for (Iterator itr = labels.keySet().iterator(); itr.hasNext(); ) {
            Object label = itr.next();
            Map map = (Map) labels.get(label);
            GridModelRow row = rows.newRow();
            row.addElement(LabelEditorComponent.LABEL, label);
            row.addElement(LabelEditorComponent.DEFAULT, map.get(LabelEditorComponent.DEFAULT));
            
            SimpleWidgetModel overrideModel = null;
            Object overrideValue = map.get(LabelEditorComponent.OVERRIDE);
            overrideModel = new SimpleWidgetModel(overrideValue);
            row.addElement(LabelEditorComponent.OVERRIDE, overrideModel);
        }
    }
    
}
