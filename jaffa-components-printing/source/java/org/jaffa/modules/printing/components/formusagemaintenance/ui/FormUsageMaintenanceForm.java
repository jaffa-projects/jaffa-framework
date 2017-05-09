// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formusagemaintenance.ui;

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

import org.jaffa.modules.printing.components.formusagemaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormUsageMeta;

import org.jaffa.modules.printing.domain.FormEventMeta;
import org.jaffa.modules.printing.domain.FormGroupMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the FormUsageMaintenance.
 */
public class FormUsageMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(FormUsageMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getFormAlternate();
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(java.lang.String formAlternate) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setFormAlternate(formAlternate);
    }

    /** Getter for property formAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formAlternate.
     */
    public EditBoxModel getFormAlternateWM() {
        EditBoxModel w_formAlternate = (EditBoxModel) getWidgetCache().getModel("formAlternate");
        if (w_formAlternate == null) {
            if (getFormAlternate() != null)
                w_formAlternate = new EditBoxModel(getFormAlternate(), (StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE);
            else
                w_formAlternate = new EditBoxModel((StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE);
            // .//GEN-END:formAlternate_1_be
            // Add custom code //GEN-FIRST:formAlternate_1


            // .//GEN-LAST:formAlternate_1
            // .//GEN-BEGIN:formAlternate_2_be
            getWidgetCache().addModel("formAlternate", w_formAlternate);
        }
        return w_formAlternate;
    }

    /** Setter for property formAlternate. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formAlternate.
     */
    public void setFormAlternateWV(String value) {
        EditBoxController.updateModel(value, getFormAlternateWM());
    }
    // .//GEN-END:formAlternate_2_be
    // .//GEN-BEGIN:copies_1_be
    /** Getter for property copies.
     * @return Value of property copies.
     */
    public java.lang.Long getCopies() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getCopies();
    }

    /** Setter for property copies.
     * @param copies New value of property copies.
     */
    public void setCopies(java.lang.Long copies) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setCopies(copies);
    }

    /** Getter for property copies. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property copies.
     */
    public EditBoxModel getCopiesWM() {
        EditBoxModel w_copies = (EditBoxModel) getWidgetCache().getModel("copies");
        if (w_copies == null) {
            if (getCopies() != null)
                w_copies = new EditBoxModel(getCopies(), (IntegerFieldMetaData) FormUsageMeta.META_COPIES);
            else
                w_copies = new EditBoxModel((IntegerFieldMetaData) FormUsageMeta.META_COPIES);
            // .//GEN-END:copies_1_be
            // Add custom code //GEN-FIRST:copies_1


            // .//GEN-LAST:copies_1
            // .//GEN-BEGIN:copies_2_be
            getWidgetCache().addModel("copies", w_copies);
        }
        return w_copies;
    }

    /** Setter for property copies. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property copies.
     */
    public void setCopiesWV(String value) {
        EditBoxController.updateModel(value, getCopiesWM());
    }
    // .//GEN-END:copies_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) FormUsageMeta.META_CREATED_ON);
            // .//GEN-END:createdOn_1_be
            // Add custom code //GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", w_createdOn);
        }
        return w_createdOn;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        DateTimeController.updateModel(value, getCreatedOnWM());
    }
    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel w_createdBy = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            if (getCreatedBy() != null)
                w_createdBy = new EditBoxModel(getCreatedBy(), (StringFieldMetaData) FormUsageMeta.META_CREATED_BY);
            else
                w_createdBy = new EditBoxModel((StringFieldMetaData) FormUsageMeta.META_CREATED_BY);
            // .//GEN-END:createdBy_1_be
            // Add custom code //GEN-FIRST:createdBy_1


            // .//GEN-LAST:createdBy_1
            // .//GEN-BEGIN:createdBy_2_be
            getWidgetCache().addModel("createdBy", w_createdBy);
        }
        return w_createdBy;
    }

    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        EditBoxController.updateModel(value, getCreatedByWM());
    }
    // .//GEN-END:createdBy_2_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public DateTimeModel getLastChangedOnWM() {
        DateTimeModel w_lastChangedOn = (DateTimeModel) getWidgetCache().getModel("lastChangedOn");
        if (w_lastChangedOn == null) {
            w_lastChangedOn = new DateTimeModel(getLastChangedOn(), (DateTimeFieldMetaData) FormUsageMeta.META_LAST_CHANGED_ON);
            // .//GEN-END:lastChangedOn_1_be
            // Add custom code //GEN-FIRST:lastChangedOn_1


            // .//GEN-LAST:lastChangedOn_1
            // .//GEN-BEGIN:lastChangedOn_2_be
            getWidgetCache().addModel("lastChangedOn", w_lastChangedOn);
        }
        return w_lastChangedOn;
    }

    /** Setter for property lastChangedOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedOn.
     */
    public void setLastChangedOnWV(String value) {
        DateTimeController.updateModel(value, getLastChangedOnWM());
    }
    // .//GEN-END:lastChangedOn_2_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel w_lastChangedBy = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (w_lastChangedBy == null) {
            if (getLastChangedBy() != null)
                w_lastChangedBy = new EditBoxModel(getLastChangedBy(), (StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY);
            else
                w_lastChangedBy = new EditBoxModel((StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY);
            // .//GEN-END:lastChangedBy_1_be
            // Add custom code //GEN-FIRST:lastChangedBy_1


            // .//GEN-LAST:lastChangedBy_1
            // .//GEN-BEGIN:lastChangedBy_2_be
            getWidgetCache().addModel("lastChangedBy", w_lastChangedBy);
        }
        return w_lastChangedBy;
    }

    /** Setter for property lastChangedBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedBy.
     */
    public void setLastChangedByWV(String value) {
        EditBoxController.updateModel(value, getLastChangedByWM());
    }
    // .//GEN-END:lastChangedBy_2_be
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getEventName();
    }

    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(java.lang.String eventName) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setEventName(eventName);
    }

    /** Getter for property eventName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eventName.
     */
    public EditBoxModel getEventNameWM() {
        EditBoxModel w_eventName = (EditBoxModel) getWidgetCache().getModel("eventName");
        if (w_eventName == null) {
            if (getEventName() != null)
                w_eventName = new EditBoxModel(getEventName(), (StringFieldMetaData) FormUsageMeta.META_EVENT_NAME);
            else
                w_eventName = new EditBoxModel((StringFieldMetaData) FormUsageMeta.META_EVENT_NAME);
            w_eventName.setMandatory(true);
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
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return ( (FormUsageMaintenanceComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        ( (FormUsageMaintenanceComponent) getComponent() ).setFormName(formName);
    }

    /** Getter for property formName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formName.
     */
    public EditBoxModel getFormNameWM() {
        EditBoxModel w_formName = (EditBoxModel) getWidgetCache().getModel("formName");
        if (w_formName == null) {
            if (getFormName() != null)
                w_formName = new EditBoxModel(getFormName(), (StringFieldMetaData) FormUsageMeta.META_FORM_NAME);
            else
                w_formName = new EditBoxModel((StringFieldMetaData) FormUsageMeta.META_FORM_NAME);
            w_formName.setMandatory(true);
            // .//GEN-END:formName_1_be
            // Add custom code //GEN-FIRST:formName_1


            // .//GEN-LAST:formName_1
            // .//GEN-BEGIN:formName_2_be
            getWidgetCache().addModel("formName", w_formName);
        }
        return w_formName;
    }

    /** Setter for property formName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formName.
     */
    public void setFormNameWV(String value) {
        EditBoxController.updateModel(value, getFormNameWM());
    }
    // .//GEN-END:formName_2_be
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
            String htmlString = getFormNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormUsageMeta.META_FORM_NAME, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormUsageMeta.META_FORM_NAME).getLabelToken());

            setFormName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormUsageMeta.META_FORM_NAME).getLabelToken(), e);
        }
        try {
            String htmlString = getEventNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormUsageMeta.META_EVENT_NAME, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormUsageMeta.META_EVENT_NAME).getLabelToken());

            setEventName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormUsageMeta.META_EVENT_NAME).getLabelToken(), e);
        }
        try {
            String htmlString = getFormAlternateWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE, true);

            setFormAlternate(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE).getLabelToken(), e);
        }


        try {
            String htmlString = getCopiesWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) FormUsageMeta.META_COPIES, true);

            setCopies(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) FormUsageMeta.META_COPIES).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getCreatedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) FormUsageMeta.META_CREATED_ON, true);
            setCreatedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) FormUsageMeta.META_CREATED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getCreatedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormUsageMeta.META_CREATED_BY, true);

            setCreatedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormUsageMeta.META_CREATED_BY).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getLastChangedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) FormUsageMeta.META_LAST_CHANGED_ON, true);
            setLastChangedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) FormUsageMeta.META_LAST_CHANGED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getLastChangedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY, true);

            setLastChangedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
