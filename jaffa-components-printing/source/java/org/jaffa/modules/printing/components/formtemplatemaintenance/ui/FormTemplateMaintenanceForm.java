// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplatemaintenance.ui;

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

import org.jaffa.modules.printing.components.formtemplatemaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormTemplateMeta;

import org.jaffa.modules.printing.domain.FormDefinitionMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the FormTemplateMaintenance.
 */
public class FormTemplateMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(FormTemplateMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:templateData_1_be
    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public byte[] getTemplateData() {
        return ( (FormTemplateMaintenanceComponent) getComponent() ).getTemplateData();
    }

    /** Setter for property templateData.
     * @param templateData New value of property templateData.
     */
    public void setTemplateData(byte[] templateData) {
        ( (FormTemplateMaintenanceComponent) getComponent() ).setTemplateData(templateData);
    }

    /** Getter for property templateData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property templateData.
     */
    public EditBoxModel getTemplateDataWM() {
        EditBoxModel w_templateData = (EditBoxModel) getWidgetCache().getModel("templateData");
        if (w_templateData == null) {
            if (getTemplateData() != null)
                w_templateData = new EditBoxModel(getTemplateData(), (RawFieldMetaData) FormTemplateMeta.META_TEMPLATE_DATA);
            else
                w_templateData = new EditBoxModel((RawFieldMetaData) FormTemplateMeta.META_TEMPLATE_DATA);
            // .//GEN-END:templateData_1_be
            // Add custom code //GEN-FIRST:templateData_1


            // .//GEN-LAST:templateData_1
            // .//GEN-BEGIN:templateData_2_be
            getWidgetCache().addModel("templateData", w_templateData);
        }
        return w_templateData;
    }

    /** Setter for property templateData. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property templateData.
     */
    public void setTemplateDataWV(String value) {
        EditBoxController.updateModel(value, getTemplateDataWM());
    }
    // .//GEN-END:templateData_2_be
    // .//GEN-BEGIN:layoutData_1_be
    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public byte[] getLayoutData() {
        return ( (FormTemplateMaintenanceComponent) getComponent() ).getLayoutData();
    }

    /** Setter for property layoutData.
     * @param layoutData New value of property layoutData.
     */
    public void setLayoutData(byte[] layoutData) {
        ( (FormTemplateMaintenanceComponent) getComponent() ).setLayoutData(layoutData);
    }

    /** Getter for property layoutData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property layoutData.
     */
    public EditBoxModel getLayoutDataWM() {
        EditBoxModel w_layoutData = (EditBoxModel) getWidgetCache().getModel("layoutData");
        if (w_layoutData == null) {
            if (getLayoutData() != null)
                w_layoutData = new EditBoxModel(getLayoutData(), (RawFieldMetaData) FormTemplateMeta.META_LAYOUT_DATA);
            else
                w_layoutData = new EditBoxModel((RawFieldMetaData) FormTemplateMeta.META_LAYOUT_DATA);
            // .//GEN-END:layoutData_1_be
            // Add custom code //GEN-FIRST:layoutData_1


            // .//GEN-LAST:layoutData_1
            // .//GEN-BEGIN:layoutData_2_be
            getWidgetCache().addModel("layoutData", w_layoutData);
        }
        return w_layoutData;
    }

    /** Setter for property layoutData. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property layoutData.
     */
    public void setLayoutDataWV(String value) {
        EditBoxController.updateModel(value, getLayoutDataWM());
    }
    // .//GEN-END:layoutData_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        return ( (FormTemplateMaintenanceComponent) getComponent() ).getFormId();
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(java.lang.Long formId) {
        ( (FormTemplateMaintenanceComponent) getComponent() ).setFormId(formId);
    }

    /** Getter for property formId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formId.
     */
    public EditBoxModel getFormIdWM() {
        EditBoxModel w_formId = (EditBoxModel) getWidgetCache().getModel("formId");
        if (w_formId == null) {
            if (getFormId() != null)
                w_formId = new EditBoxModel(getFormId(), (IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID);
            else
                w_formId = new EditBoxModel((IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID);
            w_formId.setMandatory(true);
            // .//GEN-END:formId_1_be
            // Add custom code //GEN-FIRST:formId_1


            // .//GEN-LAST:formId_1
            // .//GEN-BEGIN:formId_2_be
            getWidgetCache().addModel("formId", w_formId);
        }
        return w_formId;
    }

    /** Setter for property formId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formId.
     */
    public void setFormIdWV(String value) {
        EditBoxController.updateModel(value, getFormIdWM());
    }
    // .//GEN-END:formId_2_be
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
            String htmlString = getFormIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID, true);
            if (value == null)
                throw new MandatoryFieldException(((IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID).getLabelToken());

            setFormId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) FormTemplateMeta.META_FORM_ID).getLabelToken(), e);
        }
        try {
            String htmlString = getTemplateDataWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            byte[] value = (byte[]) FieldValidator.validateData(htmlString, (RawFieldMetaData) FormTemplateMeta.META_TEMPLATE_DATA, true);

            setTemplateData(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((RawFieldMetaData) FormTemplateMeta.META_TEMPLATE_DATA).getLabelToken(), e);
        }


        try {
            String htmlString = getLayoutDataWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            byte[] value = (byte[]) FieldValidator.validateData(htmlString, (RawFieldMetaData) FormTemplateMeta.META_LAYOUT_DATA, true);

            setLayoutData(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((RawFieldMetaData) FormTemplateMeta.META_LAYOUT_DATA).getLabelToken(), e);
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
