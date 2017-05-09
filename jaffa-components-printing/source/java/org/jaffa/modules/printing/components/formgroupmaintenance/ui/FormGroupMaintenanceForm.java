// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupmaintenance.ui;

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

import org.jaffa.modules.printing.components.formgroupmaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormGroupMeta;

import org.jaffa.modules.printing.domain.FormUsageMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the FormGroupMaintenance.
 */
public class FormGroupMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(FormGroupMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return ( (FormGroupMaintenanceComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        ( (FormGroupMaintenanceComponent) getComponent() ).setFormName(formName);
    }

    /** Getter for property formName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formName.
     */
    public EditBoxModel getFormNameWM() {
        EditBoxModel w_formName = (EditBoxModel) getWidgetCache().getModel("formName");
        if (w_formName == null) {
            if (getFormName() != null)
                w_formName = new EditBoxModel(getFormName(), (StringFieldMetaData) FormGroupMeta.META_FORM_NAME);
            else
                w_formName = new EditBoxModel((StringFieldMetaData) FormGroupMeta.META_FORM_NAME);
            // .//GEN-END:formName_1_be
            // Add custom code//GEN-FIRST:formName_1
            

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
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (FormGroupMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (FormGroupMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) FormGroupMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) FormGroupMeta.META_DESCRIPTION);
            w_description.setMandatory(true);
            // .//GEN-END:description_1_be
            // Add custom code//GEN-FIRST:description_1
            
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
    // .//GEN-BEGIN:formType_1_be
    /** Getter for property formType.
     * @return Value of property formType.
     */
    public java.lang.String getFormType() {
        return ( (FormGroupMaintenanceComponent) getComponent() ).getFormType();
    }

    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(java.lang.String formType) {
        ( (FormGroupMaintenanceComponent) getComponent() ).setFormType(formType);
    }

    /** Getter for property formType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formType.
     */
    public RadioButtonModel getFormTypeWM() {
        RadioButtonModel w_formType = (RadioButtonModel) getWidgetCache().getModel("formType");
        if (w_formType == null) {
            w_formType = new RadioButtonModel((getFormType() != null ? getFormType() : ""));

            // .//GEN-END:formType_1_be
            // Add custom code//GEN-FIRST:formType_1

            // .//GEN-LAST:formType_1
            // .//GEN-BEGIN:formType_2_be
            getWidgetCache().addModel("formType", w_formType);
        }
        return w_formType;
    }

    /** Setter for property formType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formType.
     */
    public void setFormTypeWV(String value) {
        RadioButtonController.updateModel(value, getFormTypeWM());
    }
    // .//GEN-END:formType_2_be
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
        // Add custom validation code//GEN-FIRST:_doValidate_1
        
        
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
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormGroupMeta.META_FORM_NAME, true);

            setFormName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormGroupMeta.META_FORM_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormGroupMeta.META_DESCRIPTION, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormGroupMeta.META_DESCRIPTION).getLabelToken());

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormGroupMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        try {
            java.lang.String value = getFormTypeWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) FormGroupMeta.META_FORM_TYPE, true);
            setFormType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormGroupMeta.META_FORM_TYPE).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code//GEN-FIRST:_doValidateMain_1
     
        
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
        FormUsageDto[] formUsage = ((FormGroupMaintenanceComponent) getComponent()).getRelatedObjectFormUsageDto();
        if (formUsage != null) {
            GridModelRow row = null;
            for (int i = 0; i < formUsage.length; i++) {
                FormUsageDto rowDto = formUsage[i];
                row = rows.newRow();
                row.addElement("eventName", rowDto.getEventName());
                row.addElement("formAlternate", rowDto.getFormAlternate());
                row.addElement("copies", rowDto.getCopies());
                row.addElement("description", rowDto.getDescription());
                // .//GEN-END:RelatedFormUsage_1_be
                // Add custom code for the row//GEN-FIRST:RelatedFormUsage_1
                row.addElement("select", new CheckBoxModel(false));
                row.addElement("formAlternate", new EditBoxModel(rowDto.getFormAlternate()));
                row.addElement("copies",  new EditBoxModel(rowDto.getCopies()));
                // .//GEN-LAST:RelatedFormUsage_1
                // .//GEN-BEGIN:RelatedFormUsage_2_be
            }
        }
    }
    // .//GEN-END:RelatedFormUsage_2_be
    // All the custom code goes here//GEN-FIRST:_custom
        
    
    
    // .//GEN-LAST:_custom
}
