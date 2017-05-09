// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formeventmaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.util.StringHelper;

import org.jaffa.modules.printing.components.formeventmaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormEventMeta;

import org.jaffa.modules.printing.domain.FormUsageMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the FormEventMaintenance.
 */
public class FormEventMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(FormEventMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        return ( (FormEventMaintenanceComponent) getComponent() ).getEventName();
    }

    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(java.lang.String eventName) {
        ( (FormEventMaintenanceComponent) getComponent() ).setEventName(eventName);
    }

    /** Getter for property eventName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eventName.
     */
    public EditBoxModel getEventNameWM() {
        EditBoxModel w_eventName = (EditBoxModel) getWidgetCache().getModel("eventName");
        if (w_eventName == null) {
            if (getEventName() != null)
                w_eventName = new EditBoxModel(getEventName(), (StringFieldMetaData) FormEventMeta.META_EVENT_NAME);
            else
                w_eventName = new EditBoxModel((StringFieldMetaData) FormEventMeta.META_EVENT_NAME);
            // .//GEN-END:eventName_1_be
            // Add custom code //GEN-FIRST:eventName_1


            // .//GEN-LAST:eventName_1
            // .//GEN-BEGIN:eventName_2_be
            getWidgetCache().addModel("eventName", w_eventName);
        }
        return w_eventName;
    }

    /** Setter for property eventName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property eventName.
     */
    public void setEventNameWV(String value) {
        EditBoxController.updateModel(value, getEventNameWM());
    }
    // .//GEN-END:eventName_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (FormEventMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (FormEventMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) FormEventMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) FormEventMeta.META_DESCRIPTION);
            // .//GEN-END:description_1_be
            // Add custom code //GEN-FIRST:description_1


            // .//GEN-LAST:description_1
            // .//GEN-BEGIN:description_2_be
            getWidgetCache().addModel("description", w_description);
        }
        return w_description;
    }

    /** Setter for property description. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property description.
     */
    public void setDescriptionWV(String value) {
        EditBoxController.updateModel(value, getDescriptionWM());
    }
    // .//GEN-END:description_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        if (!doValidate0(request))
            valid = false;

        // .//GEN-END:_doValidate_1_be
        // Add custom validation code //GEN-FIRST:_doValidate_1


        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_doValidateMain_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        boolean valid = true;
        try {
            String htmlString = getEventNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormEventMeta.META_EVENT_NAME, true);

            setEventName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormEventMeta.META_EVENT_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormEventMeta.META_DESCRIPTION, true);

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormEventMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // .//GEN-BEGIN:RelatedFormUsage_1_be
    /** Getter for property relatedFormUsage. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property relatedFormUsage.
     */
    public GridModel getRelatedFormUsageWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedFormUsage");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedFormUsage(rows);
            getWidgetCache().addModel("relatedFormUsage", rows);
        }
        return rows;
    }
    
    /** Setter for property relatedFormUsage. This is invoked by the servlet, when a post is done on the screen.
     * It sets the selected rows on the model.
     * @param value New value of property relatedFormUsage.
     */
    public void setRelatedFormUsageWV(String value) {
        GridController.updateModel(value, getRelatedFormUsageWM());
    }
    
    private void populateRelatedFormUsage(GridModel rows) {
        rows.clearRows();
        FormUsageDto[] formUsage = ((FormEventMaintenanceComponent) getComponent()).getRelatedObjectFormUsageDto();
        if (formUsage != null) {
            GridModelRow row = null;
            for (int i = 0; i < formUsage.length; i++) {
                FormUsageDto rowDto = formUsage[i];
                row = rows.newRow();
                row.addElement("eventName", rowDto.getEventName());
                // .//GEN-END:RelatedFormUsage_1_be
                // Add custom code for the row //GEN-FIRST:RelatedFormUsage_1


                // .//GEN-LAST:RelatedFormUsage_1
                // .//GEN-BEGIN:RelatedFormUsage_2_be
            }
        }
    }
    // .//GEN-END:RelatedFormUsage_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
