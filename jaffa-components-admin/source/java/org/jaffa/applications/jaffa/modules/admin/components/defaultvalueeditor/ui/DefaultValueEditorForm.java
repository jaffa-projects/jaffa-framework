/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.datatypes.exceptions.*;

/** The FormBean class.
 */
public class DefaultValueEditorForm extends FormBase {

    private static Logger log = Logger.getLogger(DefaultValueEditorForm.class);

    /** Getter for property components. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property components.
     */
    public GridModel getComponentsWM() {
        GridModel w_components = (GridModel) getWidgetCache().getModel("components");
        if (w_components == null) {
            w_components = new GridModel();
            List[] components = ((DefaultValueEditorComponent) getComponent()).getComponents();
            if (components != null) {
                for (int i = 0; i < components.length; i++) {
                    GridModelRow row = w_components.newRow();
                    row.addElement("ComponentName", components[i].get(0));
                    row.addElement("ComponentClass", components[i].get(1));
                    row.addElement("ComponentType", components[i].get(2));
                    row.addElement("DefaultValueFile", components[i].get(3));
                }
            }
            getWidgetCache().addModel("components", w_components);
        }
        return w_components;
    }

    /** Setter for property components. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property components.
     */
    public void setComponentsWV(String value) {
        GridController.updateModel(value, getComponentsWM());
    }

    /** Getter for property defaultValueFile.
     * @return Value of property defaultValueFile.
     */
    public java.lang.String getDefaultValueFile() {
        return ((DefaultValueEditorComponent) getComponent()).getDefaultValueFile();
    }

    /** Setter for property defaultValueFile.
     * @param defaultValueFile New value of property defaultValueFile.
     */
    public void setDefaultValueFile(java.lang.String defaultValueFile) {
        ((DefaultValueEditorComponent) getComponent()).setDefaultValueFile(defaultValueFile);
    }

    /** Getter for property defaultValues.
     * @return Value of property defaultValues.
     */
    public java.lang.String getDefaultValues() {
        return ((DefaultValueEditorComponent) getComponent()).getDefaultValues();
    }

    /** Setter for property defaultValues.
     * @param defaultValues New value of property defaultValues.
     */
    public void setDefaultValues(java.lang.String defaultValues) {
        ((DefaultValueEditorComponent) getComponent()).setDefaultValues(defaultValues);
    }

    /** Getter for property defaultValues. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property defaultValues.
     */
    public EditBoxModel getDefaultValuesWM() {
        EditBoxModel w_defaultValues = (EditBoxModel) getWidgetCache().getModel("defaultValues");
        if (w_defaultValues == null) {
            if (getDefaultValues() != null) {
                w_defaultValues = new EditBoxModel(getDefaultValues());
            } else {
                w_defaultValues = new EditBoxModel();
            }
            getWidgetCache().addModel("defaultValues", w_defaultValues);
        }
        return w_defaultValues;
    }

    /** Setter for property defaultValues. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property defaultValues.
     */
    public void setDefaultValuesWV(String value) {
        EditBoxController.updateModel(value, getDefaultValuesWM());
    }

    /** Getter for property fileUpdateable.
     * @return Value of property fileUpdateable.
     *
     */
    public boolean isFileUpdateable() {
        return ((DefaultValueEditorComponent) getComponent()).isFileUpdateable();
    }

    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = true;
        if (!doValidate0(request)) {
            valid = false;
        }
        if (!doValidate1(request)) {
            valid = false;
        }
        return valid;
    }

    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        return true;
    }

    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate1(HttpServletRequest request) {
        boolean valid = true;
        String htmlString = null;
        htmlString = getDefaultValuesWM().getValue();
        if (htmlString != null && htmlString.length() == 0) {
            htmlString = null;
        }
        java.lang.String defaultValuesValue = Parser.parseString(htmlString);
        setDefaultValues(defaultValuesValue);

        return valid;
    }

    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return ((DefaultValueEditorComponent) getComponent()).getCurrentScreenCounter();
    }

    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        ((DefaultValueEditorComponent) getComponent()).setCurrentScreenCounter(currentScreenCounter);
    }

    /** Returns true if there is a Next screen after the current screen.
     * @return true if there is a Next screen after the current screen.
     */
    public boolean isNextActionAvailable() {
        return ((DefaultValueEditorComponent) getComponent()).determineNextScreen() != null ? true : false;
    }

    /** Returns true if there is a Previous screen before the current screen.
     * @return true if there is a Previous screen before the current screen.
     */
    public boolean isPreviousActionAvailable() {
        return ((DefaultValueEditorComponent) getComponent()).determinePreviousScreen() != null ? true : false;
    }
}
