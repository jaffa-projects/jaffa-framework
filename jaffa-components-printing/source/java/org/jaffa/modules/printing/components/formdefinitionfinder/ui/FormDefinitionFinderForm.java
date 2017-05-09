// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutDto;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutRowDto;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormDefinitionFinder.
 */
public class FormDefinitionFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(FormDefinitionFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public String getFormId() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormId();
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(String formId) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormId(formId);
    }

    /** Getter for property formId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formId.
     */
    public EditBoxModel getFormIdWM() {
        EditBoxModel formIdModel = (EditBoxModel) getWidgetCache().getModel("formId");
        if (formIdModel == null) {
            if (getFormId() != null)
                formIdModel = new EditBoxModel( getFormId() );
            else
                formIdModel = new EditBoxModel();

            // .//GEN-END:formId_1_be
            // Add custom code //GEN-FIRST:formId_1


            // .//GEN-LAST:formId_1
            // .//GEN-BEGIN:formId_2_be
            getWidgetCache().addModel("formId", formIdModel);
        }
        return formIdModel;
    }

    /** Setter for property formId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formId.
     */
    public void setFormIdWV(String value) {
        EditBoxController.updateModel(value, getFormIdWM());
    }

    /** Getter for DropDown property formId.
     * @return Value of property formIdDd.
     */
    public String getFormIdDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormIdDd();
    }

    /** Setter for DropDown property formId.
     * @param formIdDd New value of property formIdDd.
     */
    public void setFormIdDd(String formIdDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormIdDd(formIdDd);
    }

    /** Getter for DropDown property formId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formIdDd.
     */
    public DropDownModel getFormIdDdWM() {
        DropDownModel formIdDdModel = (DropDownModel) getWidgetCache().getModel("formIdDd");
        if (formIdDdModel == null) {
            if (getFormIdDd() != null)
                formIdDdModel = new DropDownModel( getFormIdDd() );
            else
                formIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formIdDd", formIdDdModel);
        }
        return formIdDdModel;
    }

    /** Setter for DropDown property formId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formIdDd.
     */
    public void setFormIdDdWV(String value) {
        DropDownController.updateModel(value, getFormIdDdWM());
    }

    // .//GEN-END:formId_2_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormName(formName);
    }

    /** Getter for property formName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formName.
     */
    public EditBoxModel getFormNameWM() {
        EditBoxModel formNameModel = (EditBoxModel) getWidgetCache().getModel("formName");
        if (formNameModel == null) {
            if (getFormName() != null)
                formNameModel = new EditBoxModel( getFormName() );
            else
                formNameModel = new EditBoxModel();
            formNameModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME).getCaseType() );

            // .//GEN-END:formName_1_be
            // Add custom code //GEN-FIRST:formName_1


            // .//GEN-LAST:formName_1
            // .//GEN-BEGIN:formName_2_be
            getWidgetCache().addModel("formName", formNameModel);
        }
        return formNameModel;
    }

    /** Setter for property formName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formName.
     */
    public void setFormNameWV(String value) {
        EditBoxController.updateModel(value, getFormNameWM());
    }

    /** Getter for DropDown property formName.
     * @return Value of property formNameDd.
     */
    public String getFormNameDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormNameDd();
    }

    /** Setter for DropDown property formName.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormNameDd(formNameDd);
    }

    /** Getter for DropDown property formName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formNameDd.
     */
    public DropDownModel getFormNameDdWM() {
        DropDownModel formNameDdModel = (DropDownModel) getWidgetCache().getModel("formNameDd");
        if (formNameDdModel == null) {
            if (getFormNameDd() != null)
                formNameDdModel = new DropDownModel( getFormNameDd() );
            else
                formNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formNameDd", formNameDdModel);
        }
        return formNameDdModel;
    }

    /** Setter for DropDown property formName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formNameDd.
     */
    public void setFormNameDdWV(String value) {
        DropDownController.updateModel(value, getFormNameDdWM());
    }

    // .//GEN-END:formName_2_be
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public String getFormAlternate() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormAlternate();
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(String formAlternate) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormAlternate(formAlternate);
    }

    /** Getter for property formAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formAlternate.
     */
    public EditBoxModel getFormAlternateWM() {
        EditBoxModel formAlternateModel = (EditBoxModel) getWidgetCache().getModel("formAlternate");
        if (formAlternateModel == null) {
            if (getFormAlternate() != null)
                formAlternateModel = new EditBoxModel( getFormAlternate() );
            else
                formAlternateModel = new EditBoxModel();
            formAlternateModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE).getCaseType() );

            // .//GEN-END:formAlternate_1_be
            // Add custom code //GEN-FIRST:formAlternate_1


            // .//GEN-LAST:formAlternate_1
            // .//GEN-BEGIN:formAlternate_2_be
            getWidgetCache().addModel("formAlternate", formAlternateModel);
        }
        return formAlternateModel;
    }

    /** Setter for property formAlternate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formAlternate.
     */
    public void setFormAlternateWV(String value) {
        EditBoxController.updateModel(value, getFormAlternateWM());
    }

    /** Getter for DropDown property formAlternate.
     * @return Value of property formAlternateDd.
     */
    public String getFormAlternateDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormAlternateDd();
    }

    /** Setter for DropDown property formAlternate.
     * @param formAlternateDd New value of property formAlternateDd.
     */
    public void setFormAlternateDd(String formAlternateDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormAlternateDd(formAlternateDd);
    }

    /** Getter for DropDown property formAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formAlternateDd.
     */
    public DropDownModel getFormAlternateDdWM() {
        DropDownModel formAlternateDdModel = (DropDownModel) getWidgetCache().getModel("formAlternateDd");
        if (formAlternateDdModel == null) {
            if (getFormAlternateDd() != null)
                formAlternateDdModel = new DropDownModel( getFormAlternateDd() );
            else
                formAlternateDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formAlternateDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formAlternateDd", formAlternateDdModel);
        }
        return formAlternateDdModel;
    }

    /** Setter for DropDown property formAlternate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formAlternateDd.
     */
    public void setFormAlternateDdWV(String value) {
        DropDownController.updateModel(value, getFormAlternateDdWM());
    }

    // .//GEN-END:formAlternate_2_be
    // .//GEN-BEGIN:formVariation_1_be
    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public String getFormVariation() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormVariation();
    }

    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(String formVariation) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormVariation(formVariation);
    }

    /** Getter for property formVariation. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formVariation.
     */
    public EditBoxModel getFormVariationWM() {
        EditBoxModel formVariationModel = (EditBoxModel) getWidgetCache().getModel("formVariation");
        if (formVariationModel == null) {
            if (getFormVariation() != null)
                formVariationModel = new EditBoxModel( getFormVariation() );
            else
                formVariationModel = new EditBoxModel();
            formVariationModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION).getCaseType() );

            // .//GEN-END:formVariation_1_be
            // Add custom code //GEN-FIRST:formVariation_1


            // .//GEN-LAST:formVariation_1
            // .//GEN-BEGIN:formVariation_2_be
            getWidgetCache().addModel("formVariation", formVariationModel);
        }
        return formVariationModel;
    }

    /** Setter for property formVariation. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formVariation.
     */
    public void setFormVariationWV(String value) {
        EditBoxController.updateModel(value, getFormVariationWM());
    }

    /** Getter for DropDown property formVariation.
     * @return Value of property formVariationDd.
     */
    public String getFormVariationDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormVariationDd();
    }

    /** Setter for DropDown property formVariation.
     * @param formVariationDd New value of property formVariationDd.
     */
    public void setFormVariationDd(String formVariationDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormVariationDd(formVariationDd);
    }

    /** Getter for DropDown property formVariation. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formVariationDd.
     */
    public DropDownModel getFormVariationDdWM() {
        DropDownModel formVariationDdModel = (DropDownModel) getWidgetCache().getModel("formVariationDd");
        if (formVariationDdModel == null) {
            if (getFormVariationDd() != null)
                formVariationDdModel = new DropDownModel( getFormVariationDd() );
            else
                formVariationDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formVariationDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formVariationDd", formVariationDdModel);
        }
        return formVariationDdModel;
    }

    /** Setter for DropDown property formVariation. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formVariationDd.
     */
    public void setFormVariationDdWV(String value) {
        DropDownController.updateModel(value, getFormVariationDdWM());
    }

    // .//GEN-END:formVariation_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public String getOutputType() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        ( (FormDefinitionFinderComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public EditBoxModel getOutputTypeWM() {
        EditBoxModel outputTypeModel = (EditBoxModel) getWidgetCache().getModel("outputType");
        if (outputTypeModel == null) {
            if (getOutputType() != null)
                outputTypeModel = new EditBoxModel( getOutputType() );
            else
                outputTypeModel = new EditBoxModel();
            outputTypeModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE).getCaseType() );

            // .//GEN-END:outputType_1_be
            // Add custom code //GEN-FIRST:outputType_1


            // .//GEN-LAST:outputType_1
            // .//GEN-BEGIN:outputType_2_be
            getWidgetCache().addModel("outputType", outputTypeModel);
        }
        return outputTypeModel;
    }

    /** Setter for property outputType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputType.
     */
    public void setOutputTypeWV(String value) {
        EditBoxController.updateModel(value, getOutputTypeWM());
    }

    /** Getter for DropDown property outputType.
     * @return Value of property outputTypeDd.
     */
    public String getOutputTypeDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getOutputTypeDd();
    }

    /** Setter for DropDown property outputType.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setOutputTypeDd(outputTypeDd);
    }

    /** Getter for DropDown property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputTypeDd.
     */
    public DropDownModel getOutputTypeDdWM() {
        DropDownModel outputTypeDdModel = (DropDownModel) getWidgetCache().getModel("outputTypeDd");
        if (outputTypeDdModel == null) {
            if (getOutputTypeDd() != null)
                outputTypeDdModel = new DropDownModel( getOutputTypeDd() );
            else
                outputTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                outputTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("outputTypeDd", outputTypeDdModel);
        }
        return outputTypeDdModel;
    }

    /** Setter for DropDown property outputType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputTypeDd.
     */
    public void setOutputTypeDdWV(String value) {
        DropDownController.updateModel(value, getOutputTypeDdWM());
    }

    // .//GEN-END:outputType_2_be
    // .//GEN-BEGIN:formTemplate_1_be
    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public String getFormTemplate() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormTemplate();
    }

    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(String formTemplate) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormTemplate(formTemplate);
    }

    /** Getter for property formTemplate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formTemplate.
     */
    public EditBoxModel getFormTemplateWM() {
        EditBoxModel formTemplateModel = (EditBoxModel) getWidgetCache().getModel("formTemplate");
        if (formTemplateModel == null) {
            if (getFormTemplate() != null)
                formTemplateModel = new EditBoxModel( getFormTemplate() );
            else
                formTemplateModel = new EditBoxModel();
            formTemplateModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE).getCaseType() );

            // .//GEN-END:formTemplate_1_be
            // Add custom code //GEN-FIRST:formTemplate_1


            // .//GEN-LAST:formTemplate_1
            // .//GEN-BEGIN:formTemplate_2_be
            getWidgetCache().addModel("formTemplate", formTemplateModel);
        }
        return formTemplateModel;
    }

    /** Setter for property formTemplate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formTemplate.
     */
    public void setFormTemplateWV(String value) {
        EditBoxController.updateModel(value, getFormTemplateWM());
    }

    /** Getter for DropDown property formTemplate.
     * @return Value of property formTemplateDd.
     */
    public String getFormTemplateDd() {
        return ( (FormDefinitionFinderComponent) getComponent() ).getFormTemplateDd();
    }

    /** Setter for DropDown property formTemplate.
     * @param formTemplateDd New value of property formTemplateDd.
     */
    public void setFormTemplateDd(String formTemplateDd) {
        ( (FormDefinitionFinderComponent) getComponent() ).setFormTemplateDd(formTemplateDd);
    }

    /** Getter for DropDown property formTemplate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formTemplateDd.
     */
    public DropDownModel getFormTemplateDdWM() {
        DropDownModel formTemplateDdModel = (DropDownModel) getWidgetCache().getModel("formTemplateDd");
        if (formTemplateDdModel == null) {
            if (getFormTemplateDd() != null)
                formTemplateDdModel = new DropDownModel( getFormTemplateDd() );
            else
                formTemplateDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formTemplateDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formTemplateDd", formTemplateDdModel);
        }
        return formTemplateDdModel;
    }

    /** Setter for DropDown property formTemplate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formTemplateDd.
     */
    public void setFormTemplateDdWV(String value) {
        DropDownController.updateModel(value, getFormTemplateDdWM());
    }

    // .//GEN-END:formTemplate_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getFormIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormId(value);
        setFormIdDd(getFormIdDdWM().getValue());

        value = getFormNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormName(value);
        setFormNameDd(getFormNameDdWM().getValue());

        value = getFormAlternateWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormAlternate(value);
        setFormAlternateDd(getFormAlternateDdWM().getValue());

        value = getFormVariationWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormVariation(value);
        setFormVariationDd(getFormVariationDdWM().getValue());

        value = getOutputTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOutputType(value);
        setOutputTypeDd(getOutputTypeDdWM().getValue());

        value = getFormTemplateWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormTemplate(value);
        setFormTemplateDd(getFormTemplateDdWM().getValue());

        // .//GEN-END:_doValidate_1_be
        // Add custom code //GEN-FIRST:_doValidate_1



        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        FormDefinitionFinderOutDto outputDto = (FormDefinitionFinderOutDto) ((FormDefinitionFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormDefinitionFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("formId", rowDto.getFormId());
                row.addElement("formName", rowDto.getFormName());
                row.addElement("formAlternate", rowDto.getFormAlternate());
                row.addElement("formVariation", rowDto.getFormVariation());
                row.addElement("description", rowDto.getDescription());
                row.addElement("formTemplate", rowDto.getFormTemplate());
                row.addElement("outputType", rowDto.getOutputType());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
