// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionlookup.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutDto;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutRowDto;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormDefinitionLookup.
 */
public class FormDefinitionLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(FormDefinitionLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public String getFormId() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormId();
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(String formId) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormId(formId);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormIdDd();
    }

    /** Setter for DropDown property formId.
     * @param formIdDd New value of property formIdDd.
     */
    public void setFormIdDd(String formIdDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormIdDd(formIdDd);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormName(formName);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormNameDd();
    }

    /** Setter for DropDown property formName.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormNameDd(formNameDd);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormAlternate();
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(String formAlternate) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormAlternate(formAlternate);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormAlternateDd();
    }

    /** Setter for DropDown property formAlternate.
     * @param formAlternateDd New value of property formAlternateDd.
     */
    public void setFormAlternateDd(String formAlternateDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormAlternateDd(formAlternateDd);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormVariation();
    }

    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(String formVariation) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormVariation(formVariation);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormVariationDd();
    }

    /** Setter for DropDown property formVariation.
     * @param formVariationDd New value of property formVariationDd.
     */
    public void setFormVariationDd(String formVariationDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormVariationDd(formVariationDd);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        ( (FormDefinitionLookupComponent) getComponent() ).setOutputType(outputType);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getOutputTypeDd();
    }

    /** Setter for DropDown property outputType.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setOutputTypeDd(outputTypeDd);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormTemplate();
    }

    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(String formTemplate) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormTemplate(formTemplate);
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
        return ( (FormDefinitionLookupComponent) getComponent() ).getFormTemplateDd();
    }

    /** Setter for DropDown property formTemplate.
     * @param formTemplateDd New value of property formTemplateDd.
     */
    public void setFormTemplateDd(String formTemplateDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFormTemplateDd(formTemplateDd);
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
    // .//GEN-BEGIN:fieldLayout_1_be
    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public String getFieldLayout() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFieldLayout();
    }

    /** Setter for property fieldLayout.
     * @param fieldLayout New value of property fieldLayout.
     */
    public void setFieldLayout(String fieldLayout) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFieldLayout(fieldLayout);
    }

    /** Getter for property fieldLayout. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fieldLayout.
     */
    public EditBoxModel getFieldLayoutWM() {
        EditBoxModel fieldLayoutModel = (EditBoxModel) getWidgetCache().getModel("fieldLayout");
        if (fieldLayoutModel == null) {
            if (getFieldLayout() != null)
                fieldLayoutModel = new EditBoxModel( getFieldLayout() );
            else
                fieldLayoutModel = new EditBoxModel();
            fieldLayoutModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT).getCaseType() );

            // .//GEN-END:fieldLayout_1_be
            // Add custom code //GEN-FIRST:fieldLayout_1


            // .//GEN-LAST:fieldLayout_1
            // .//GEN-BEGIN:fieldLayout_2_be
            getWidgetCache().addModel("fieldLayout", fieldLayoutModel);
        }
        return fieldLayoutModel;
    }

    /** Setter for property fieldLayout. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property fieldLayout.
     */
    public void setFieldLayoutWV(String value) {
        EditBoxController.updateModel(value, getFieldLayoutWM());
    }

    /** Getter for DropDown property fieldLayout.
     * @return Value of property fieldLayoutDd.
     */
    public String getFieldLayoutDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFieldLayoutDd();
    }

    /** Setter for DropDown property fieldLayout.
     * @param fieldLayoutDd New value of property fieldLayoutDd.
     */
    public void setFieldLayoutDd(String fieldLayoutDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFieldLayoutDd(fieldLayoutDd);
    }

    /** Getter for DropDown property fieldLayout. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fieldLayoutDd.
     */
    public DropDownModel getFieldLayoutDdWM() {
        DropDownModel fieldLayoutDdModel = (DropDownModel) getWidgetCache().getModel("fieldLayoutDd");
        if (fieldLayoutDdModel == null) {
            if (getFieldLayoutDd() != null)
                fieldLayoutDdModel = new DropDownModel( getFieldLayoutDd() );
            else
                fieldLayoutDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                fieldLayoutDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("fieldLayoutDd", fieldLayoutDdModel);
        }
        return fieldLayoutDdModel;
    }

    /** Setter for DropDown property fieldLayout. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property fieldLayoutDd.
     */
    public void setFieldLayoutDdWV(String value) {
        DropDownController.updateModel(value, getFieldLayoutDdWM());
    }

    // .//GEN-END:fieldLayout_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel descriptionModel = (EditBoxModel) getWidgetCache().getModel("description");
        if (descriptionModel == null) {
            if (getDescription() != null)
                descriptionModel = new EditBoxModel( getDescription() );
            else
                descriptionModel = new EditBoxModel();
            descriptionModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION).getCaseType() );

            // .//GEN-END:description_1_be
            // Add custom code //GEN-FIRST:description_1


            // .//GEN-LAST:description_1
            // .//GEN-BEGIN:description_2_be
            getWidgetCache().addModel("description", descriptionModel);
        }
        return descriptionModel;
    }

    /** Setter for property description. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property description.
     */
    public void setDescriptionWV(String value) {
        EditBoxController.updateModel(value, getDescriptionWM());
    }

    /** Getter for DropDown property description.
     * @return Value of property descriptionDd.
     */
    public String getDescriptionDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDescriptionDd(descriptionDd);
    }

    /** Getter for DropDown property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property descriptionDd.
     */
    public DropDownModel getDescriptionDdWM() {
        DropDownModel descriptionDdModel = (DropDownModel) getWidgetCache().getModel("descriptionDd");
        if (descriptionDdModel == null) {
            if (getDescriptionDd() != null)
                descriptionDdModel = new DropDownModel( getDescriptionDd() );
            else
                descriptionDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                descriptionDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("descriptionDd", descriptionDdModel);
        }
        return descriptionDdModel;
    }

    /** Setter for DropDown property description. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property descriptionDd.
     */
    public void setDescriptionDdWV(String value) {
        DropDownController.updateModel(value, getDescriptionDdWM());
    }

    // .//GEN-END:description_2_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        ( (FormDefinitionLookupComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel remarksModel = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (remarksModel == null) {
            if (getRemarks() != null)
                remarksModel = new EditBoxModel( getRemarks() );
            else
                remarksModel = new EditBoxModel();
            remarksModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_REMARKS).getCaseType() );

            // .//GEN-END:remarks_1_be
            // Add custom code //GEN-FIRST:remarks_1


            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", remarksModel);
        }
        return remarksModel;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }

    /** Getter for DropDown property remarks.
     * @return Value of property remarksDd.
     */
    public String getRemarksDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getRemarksDd();
    }

    /** Setter for DropDown property remarks.
     * @param remarksDd New value of property remarksDd.
     */
    public void setRemarksDd(String remarksDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setRemarksDd(remarksDd);
    }

    /** Getter for DropDown property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarksDd.
     */
    public DropDownModel getRemarksDdWM() {
        DropDownModel remarksDdModel = (DropDownModel) getWidgetCache().getModel("remarksDd");
        if (remarksDdModel == null) {
            if (getRemarksDd() != null)
                remarksDdModel = new DropDownModel( getRemarksDd() );
            else
                remarksDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                remarksDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("remarksDd", remarksDdModel);
        }
        return remarksDdModel;
    }

    /** Setter for DropDown property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarksDd.
     */
    public void setRemarksDdWV(String value) {
        DropDownController.updateModel(value, getRemarksDdWM());
    }

    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:domFactory_1_be
    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public String getDomFactory() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomFactory();
    }

    /** Setter for property domFactory.
     * @param domFactory New value of property domFactory.
     */
    public void setDomFactory(String domFactory) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomFactory(domFactory);
    }

    /** Getter for property domFactory. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domFactory.
     */
    public EditBoxModel getDomFactoryWM() {
        EditBoxModel domFactoryModel = (EditBoxModel) getWidgetCache().getModel("domFactory");
        if (domFactoryModel == null) {
            if (getDomFactory() != null)
                domFactoryModel = new EditBoxModel( getDomFactory() );
            else
                domFactoryModel = new EditBoxModel();
            domFactoryModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY).getCaseType() );

            // .//GEN-END:domFactory_1_be
            // Add custom code //GEN-FIRST:domFactory_1


            // .//GEN-LAST:domFactory_1
            // .//GEN-BEGIN:domFactory_2_be
            getWidgetCache().addModel("domFactory", domFactoryModel);
        }
        return domFactoryModel;
    }

    /** Setter for property domFactory. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domFactory.
     */
    public void setDomFactoryWV(String value) {
        EditBoxController.updateModel(value, getDomFactoryWM());
    }

    /** Getter for DropDown property domFactory.
     * @return Value of property domFactoryDd.
     */
    public String getDomFactoryDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomFactoryDd();
    }

    /** Setter for DropDown property domFactory.
     * @param domFactoryDd New value of property domFactoryDd.
     */
    public void setDomFactoryDd(String domFactoryDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomFactoryDd(domFactoryDd);
    }

    /** Getter for DropDown property domFactory. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domFactoryDd.
     */
    public DropDownModel getDomFactoryDdWM() {
        DropDownModel domFactoryDdModel = (DropDownModel) getWidgetCache().getModel("domFactoryDd");
        if (domFactoryDdModel == null) {
            if (getDomFactoryDd() != null)
                domFactoryDdModel = new DropDownModel( getDomFactoryDd() );
            else
                domFactoryDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domFactoryDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domFactoryDd", domFactoryDdModel);
        }
        return domFactoryDdModel;
    }

    /** Setter for DropDown property domFactory. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domFactoryDd.
     */
    public void setDomFactoryDdWV(String value) {
        DropDownController.updateModel(value, getDomFactoryDdWM());
    }

    // .//GEN-END:domFactory_2_be
    // .//GEN-BEGIN:domClass_1_be
    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public String getDomClass() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomClass();
    }

    /** Setter for property domClass.
     * @param domClass New value of property domClass.
     */
    public void setDomClass(String domClass) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomClass(domClass);
    }

    /** Getter for property domClass. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domClass.
     */
    public EditBoxModel getDomClassWM() {
        EditBoxModel domClassModel = (EditBoxModel) getWidgetCache().getModel("domClass");
        if (domClassModel == null) {
            if (getDomClass() != null)
                domClassModel = new EditBoxModel( getDomClass() );
            else
                domClassModel = new EditBoxModel();
            domClassModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS).getCaseType() );

            // .//GEN-END:domClass_1_be
            // Add custom code //GEN-FIRST:domClass_1


            // .//GEN-LAST:domClass_1
            // .//GEN-BEGIN:domClass_2_be
            getWidgetCache().addModel("domClass", domClassModel);
        }
        return domClassModel;
    }

    /** Setter for property domClass. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domClass.
     */
    public void setDomClassWV(String value) {
        EditBoxController.updateModel(value, getDomClassWM());
    }

    /** Getter for DropDown property domClass.
     * @return Value of property domClassDd.
     */
    public String getDomClassDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomClassDd();
    }

    /** Setter for DropDown property domClass.
     * @param domClassDd New value of property domClassDd.
     */
    public void setDomClassDd(String domClassDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomClassDd(domClassDd);
    }

    /** Getter for DropDown property domClass. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domClassDd.
     */
    public DropDownModel getDomClassDdWM() {
        DropDownModel domClassDdModel = (DropDownModel) getWidgetCache().getModel("domClassDd");
        if (domClassDdModel == null) {
            if (getDomClassDd() != null)
                domClassDdModel = new DropDownModel( getDomClassDd() );
            else
                domClassDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domClassDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domClassDd", domClassDdModel);
        }
        return domClassDdModel;
    }

    /** Setter for DropDown property domClass. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domClassDd.
     */
    public void setDomClassDdWV(String value) {
        DropDownController.updateModel(value, getDomClassDdWM());
    }

    // .//GEN-END:domClass_2_be
    // .//GEN-BEGIN:domKey1_1_be
    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public String getDomKey1() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey1();
    }

    /** Setter for property domKey1.
     * @param domKey1 New value of property domKey1.
     */
    public void setDomKey1(String domKey1) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey1(domKey1);
    }

    /** Getter for property domKey1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey1.
     */
    public EditBoxModel getDomKey1WM() {
        EditBoxModel domKey1Model = (EditBoxModel) getWidgetCache().getModel("domKey1");
        if (domKey1Model == null) {
            if (getDomKey1() != null)
                domKey1Model = new EditBoxModel( getDomKey1() );
            else
                domKey1Model = new EditBoxModel();
            domKey1Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1).getCaseType() );

            // .//GEN-END:domKey1_1_be
            // Add custom code //GEN-FIRST:domKey1_1


            // .//GEN-LAST:domKey1_1
            // .//GEN-BEGIN:domKey1_2_be
            getWidgetCache().addModel("domKey1", domKey1Model);
        }
        return domKey1Model;
    }

    /** Setter for property domKey1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey1.
     */
    public void setDomKey1WV(String value) {
        EditBoxController.updateModel(value, getDomKey1WM());
    }

    /** Getter for DropDown property domKey1.
     * @return Value of property domKey1Dd.
     */
    public String getDomKey1Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey1Dd();
    }

    /** Setter for DropDown property domKey1.
     * @param domKey1Dd New value of property domKey1Dd.
     */
    public void setDomKey1Dd(String domKey1Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey1Dd(domKey1Dd);
    }

    /** Getter for DropDown property domKey1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey1Dd.
     */
    public DropDownModel getDomKey1DdWM() {
        DropDownModel domKey1DdModel = (DropDownModel) getWidgetCache().getModel("domKey1Dd");
        if (domKey1DdModel == null) {
            if (getDomKey1Dd() != null)
                domKey1DdModel = new DropDownModel( getDomKey1Dd() );
            else
                domKey1DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey1DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey1Dd", domKey1DdModel);
        }
        return domKey1DdModel;
    }

    /** Setter for DropDown property domKey1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey1Dd.
     */
    public void setDomKey1DdWV(String value) {
        DropDownController.updateModel(value, getDomKey1DdWM());
    }

    // .//GEN-END:domKey1_2_be
    // .//GEN-BEGIN:domKey2_1_be
    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public String getDomKey2() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey2();
    }

    /** Setter for property domKey2.
     * @param domKey2 New value of property domKey2.
     */
    public void setDomKey2(String domKey2) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey2(domKey2);
    }

    /** Getter for property domKey2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey2.
     */
    public EditBoxModel getDomKey2WM() {
        EditBoxModel domKey2Model = (EditBoxModel) getWidgetCache().getModel("domKey2");
        if (domKey2Model == null) {
            if (getDomKey2() != null)
                domKey2Model = new EditBoxModel( getDomKey2() );
            else
                domKey2Model = new EditBoxModel();
            domKey2Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2).getCaseType() );

            // .//GEN-END:domKey2_1_be
            // Add custom code //GEN-FIRST:domKey2_1


            // .//GEN-LAST:domKey2_1
            // .//GEN-BEGIN:domKey2_2_be
            getWidgetCache().addModel("domKey2", domKey2Model);
        }
        return domKey2Model;
    }

    /** Setter for property domKey2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey2.
     */
    public void setDomKey2WV(String value) {
        EditBoxController.updateModel(value, getDomKey2WM());
    }

    /** Getter for DropDown property domKey2.
     * @return Value of property domKey2Dd.
     */
    public String getDomKey2Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey2Dd();
    }

    /** Setter for DropDown property domKey2.
     * @param domKey2Dd New value of property domKey2Dd.
     */
    public void setDomKey2Dd(String domKey2Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey2Dd(domKey2Dd);
    }

    /** Getter for DropDown property domKey2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey2Dd.
     */
    public DropDownModel getDomKey2DdWM() {
        DropDownModel domKey2DdModel = (DropDownModel) getWidgetCache().getModel("domKey2Dd");
        if (domKey2DdModel == null) {
            if (getDomKey2Dd() != null)
                domKey2DdModel = new DropDownModel( getDomKey2Dd() );
            else
                domKey2DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey2DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey2Dd", domKey2DdModel);
        }
        return domKey2DdModel;
    }

    /** Setter for DropDown property domKey2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey2Dd.
     */
    public void setDomKey2DdWV(String value) {
        DropDownController.updateModel(value, getDomKey2DdWM());
    }

    // .//GEN-END:domKey2_2_be
    // .//GEN-BEGIN:domKey3_1_be
    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public String getDomKey3() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey3();
    }

    /** Setter for property domKey3.
     * @param domKey3 New value of property domKey3.
     */
    public void setDomKey3(String domKey3) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey3(domKey3);
    }

    /** Getter for property domKey3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey3.
     */
    public EditBoxModel getDomKey3WM() {
        EditBoxModel domKey3Model = (EditBoxModel) getWidgetCache().getModel("domKey3");
        if (domKey3Model == null) {
            if (getDomKey3() != null)
                domKey3Model = new EditBoxModel( getDomKey3() );
            else
                domKey3Model = new EditBoxModel();
            domKey3Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3).getCaseType() );

            // .//GEN-END:domKey3_1_be
            // Add custom code //GEN-FIRST:domKey3_1


            // .//GEN-LAST:domKey3_1
            // .//GEN-BEGIN:domKey3_2_be
            getWidgetCache().addModel("domKey3", domKey3Model);
        }
        return domKey3Model;
    }

    /** Setter for property domKey3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey3.
     */
    public void setDomKey3WV(String value) {
        EditBoxController.updateModel(value, getDomKey3WM());
    }

    /** Getter for DropDown property domKey3.
     * @return Value of property domKey3Dd.
     */
    public String getDomKey3Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey3Dd();
    }

    /** Setter for DropDown property domKey3.
     * @param domKey3Dd New value of property domKey3Dd.
     */
    public void setDomKey3Dd(String domKey3Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey3Dd(domKey3Dd);
    }

    /** Getter for DropDown property domKey3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey3Dd.
     */
    public DropDownModel getDomKey3DdWM() {
        DropDownModel domKey3DdModel = (DropDownModel) getWidgetCache().getModel("domKey3Dd");
        if (domKey3DdModel == null) {
            if (getDomKey3Dd() != null)
                domKey3DdModel = new DropDownModel( getDomKey3Dd() );
            else
                domKey3DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey3DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey3Dd", domKey3DdModel);
        }
        return domKey3DdModel;
    }

    /** Setter for DropDown property domKey3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey3Dd.
     */
    public void setDomKey3DdWV(String value) {
        DropDownController.updateModel(value, getDomKey3DdWM());
    }

    // .//GEN-END:domKey3_2_be
    // .//GEN-BEGIN:domKey4_1_be
    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public String getDomKey4() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey4();
    }

    /** Setter for property domKey4.
     * @param domKey4 New value of property domKey4.
     */
    public void setDomKey4(String domKey4) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey4(domKey4);
    }

    /** Getter for property domKey4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey4.
     */
    public EditBoxModel getDomKey4WM() {
        EditBoxModel domKey4Model = (EditBoxModel) getWidgetCache().getModel("domKey4");
        if (domKey4Model == null) {
            if (getDomKey4() != null)
                domKey4Model = new EditBoxModel( getDomKey4() );
            else
                domKey4Model = new EditBoxModel();
            domKey4Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4).getCaseType() );

            // .//GEN-END:domKey4_1_be
            // Add custom code //GEN-FIRST:domKey4_1


            // .//GEN-LAST:domKey4_1
            // .//GEN-BEGIN:domKey4_2_be
            getWidgetCache().addModel("domKey4", domKey4Model);
        }
        return domKey4Model;
    }

    /** Setter for property domKey4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey4.
     */
    public void setDomKey4WV(String value) {
        EditBoxController.updateModel(value, getDomKey4WM());
    }

    /** Getter for DropDown property domKey4.
     * @return Value of property domKey4Dd.
     */
    public String getDomKey4Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey4Dd();
    }

    /** Setter for DropDown property domKey4.
     * @param domKey4Dd New value of property domKey4Dd.
     */
    public void setDomKey4Dd(String domKey4Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey4Dd(domKey4Dd);
    }

    /** Getter for DropDown property domKey4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey4Dd.
     */
    public DropDownModel getDomKey4DdWM() {
        DropDownModel domKey4DdModel = (DropDownModel) getWidgetCache().getModel("domKey4Dd");
        if (domKey4DdModel == null) {
            if (getDomKey4Dd() != null)
                domKey4DdModel = new DropDownModel( getDomKey4Dd() );
            else
                domKey4DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey4DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey4Dd", domKey4DdModel);
        }
        return domKey4DdModel;
    }

    /** Setter for DropDown property domKey4. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey4Dd.
     */
    public void setDomKey4DdWV(String value) {
        DropDownController.updateModel(value, getDomKey4DdWM());
    }

    // .//GEN-END:domKey4_2_be
    // .//GEN-BEGIN:domKey5_1_be
    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public String getDomKey5() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey5();
    }

    /** Setter for property domKey5.
     * @param domKey5 New value of property domKey5.
     */
    public void setDomKey5(String domKey5) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey5(domKey5);
    }

    /** Getter for property domKey5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey5.
     */
    public EditBoxModel getDomKey5WM() {
        EditBoxModel domKey5Model = (EditBoxModel) getWidgetCache().getModel("domKey5");
        if (domKey5Model == null) {
            if (getDomKey5() != null)
                domKey5Model = new EditBoxModel( getDomKey5() );
            else
                domKey5Model = new EditBoxModel();
            domKey5Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5).getCaseType() );

            // .//GEN-END:domKey5_1_be
            // Add custom code //GEN-FIRST:domKey5_1


            // .//GEN-LAST:domKey5_1
            // .//GEN-BEGIN:domKey5_2_be
            getWidgetCache().addModel("domKey5", domKey5Model);
        }
        return domKey5Model;
    }

    /** Setter for property domKey5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey5.
     */
    public void setDomKey5WV(String value) {
        EditBoxController.updateModel(value, getDomKey5WM());
    }

    /** Getter for DropDown property domKey5.
     * @return Value of property domKey5Dd.
     */
    public String getDomKey5Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey5Dd();
    }

    /** Setter for DropDown property domKey5.
     * @param domKey5Dd New value of property domKey5Dd.
     */
    public void setDomKey5Dd(String domKey5Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey5Dd(domKey5Dd);
    }

    /** Getter for DropDown property domKey5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey5Dd.
     */
    public DropDownModel getDomKey5DdWM() {
        DropDownModel domKey5DdModel = (DropDownModel) getWidgetCache().getModel("domKey5Dd");
        if (domKey5DdModel == null) {
            if (getDomKey5Dd() != null)
                domKey5DdModel = new DropDownModel( getDomKey5Dd() );
            else
                domKey5DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey5DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey5Dd", domKey5DdModel);
        }
        return domKey5DdModel;
    }

    /** Setter for DropDown property domKey5. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey5Dd.
     */
    public void setDomKey5DdWV(String value) {
        DropDownController.updateModel(value, getDomKey5DdWM());
    }

    // .//GEN-END:domKey5_2_be
    // .//GEN-BEGIN:domKey6_1_be
    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public String getDomKey6() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey6();
    }

    /** Setter for property domKey6.
     * @param domKey6 New value of property domKey6.
     */
    public void setDomKey6(String domKey6) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey6(domKey6);
    }

    /** Getter for property domKey6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey6.
     */
    public EditBoxModel getDomKey6WM() {
        EditBoxModel domKey6Model = (EditBoxModel) getWidgetCache().getModel("domKey6");
        if (domKey6Model == null) {
            if (getDomKey6() != null)
                domKey6Model = new EditBoxModel( getDomKey6() );
            else
                domKey6Model = new EditBoxModel();
            domKey6Model.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6).getCaseType() );

            // .//GEN-END:domKey6_1_be
            // Add custom code //GEN-FIRST:domKey6_1


            // .//GEN-LAST:domKey6_1
            // .//GEN-BEGIN:domKey6_2_be
            getWidgetCache().addModel("domKey6", domKey6Model);
        }
        return domKey6Model;
    }

    /** Setter for property domKey6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey6.
     */
    public void setDomKey6WV(String value) {
        EditBoxController.updateModel(value, getDomKey6WM());
    }

    /** Getter for DropDown property domKey6.
     * @return Value of property domKey6Dd.
     */
    public String getDomKey6Dd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getDomKey6Dd();
    }

    /** Setter for DropDown property domKey6.
     * @param domKey6Dd New value of property domKey6Dd.
     */
    public void setDomKey6Dd(String domKey6Dd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setDomKey6Dd(domKey6Dd);
    }

    /** Getter for DropDown property domKey6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey6Dd.
     */
    public DropDownModel getDomKey6DdWM() {
        DropDownModel domKey6DdModel = (DropDownModel) getWidgetCache().getModel("domKey6Dd");
        if (domKey6DdModel == null) {
            if (getDomKey6Dd() != null)
                domKey6DdModel = new DropDownModel( getDomKey6Dd() );
            else
                domKey6DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                domKey6DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("domKey6Dd", domKey6DdModel);
        }
        return domKey6DdModel;
    }

    /** Setter for DropDown property domKey6. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property domKey6Dd.
     */
    public void setDomKey6DdWV(String value) {
        DropDownController.updateModel(value, getDomKey6DdWM());
    }

    // .//GEN-END:domKey6_2_be
    // .//GEN-BEGIN:additionalDataComponent_1_be
    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public String getAdditionalDataComponent() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getAdditionalDataComponent();
    }

    /** Setter for property additionalDataComponent.
     * @param additionalDataComponent New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponent(String additionalDataComponent) {
        ( (FormDefinitionLookupComponent) getComponent() ).setAdditionalDataComponent(additionalDataComponent);
    }

    /** Getter for property additionalDataComponent. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property additionalDataComponent.
     */
    public EditBoxModel getAdditionalDataComponentWM() {
        EditBoxModel additionalDataComponentModel = (EditBoxModel) getWidgetCache().getModel("additionalDataComponent");
        if (additionalDataComponentModel == null) {
            if (getAdditionalDataComponent() != null)
                additionalDataComponentModel = new EditBoxModel( getAdditionalDataComponent() );
            else
                additionalDataComponentModel = new EditBoxModel();
            additionalDataComponentModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT).getCaseType() );

            // .//GEN-END:additionalDataComponent_1_be
            // Add custom code //GEN-FIRST:additionalDataComponent_1


            // .//GEN-LAST:additionalDataComponent_1
            // .//GEN-BEGIN:additionalDataComponent_2_be
            getWidgetCache().addModel("additionalDataComponent", additionalDataComponentModel);
        }
        return additionalDataComponentModel;
    }

    /** Setter for property additionalDataComponent. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponentWV(String value) {
        EditBoxController.updateModel(value, getAdditionalDataComponentWM());
    }

    /** Getter for DropDown property additionalDataComponent.
     * @return Value of property additionalDataComponentDd.
     */
    public String getAdditionalDataComponentDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getAdditionalDataComponentDd();
    }

    /** Setter for DropDown property additionalDataComponent.
     * @param additionalDataComponentDd New value of property additionalDataComponentDd.
     */
    public void setAdditionalDataComponentDd(String additionalDataComponentDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setAdditionalDataComponentDd(additionalDataComponentDd);
    }

    /** Getter for DropDown property additionalDataComponent. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property additionalDataComponentDd.
     */
    public DropDownModel getAdditionalDataComponentDdWM() {
        DropDownModel additionalDataComponentDdModel = (DropDownModel) getWidgetCache().getModel("additionalDataComponentDd");
        if (additionalDataComponentDdModel == null) {
            if (getAdditionalDataComponentDd() != null)
                additionalDataComponentDdModel = new DropDownModel( getAdditionalDataComponentDd() );
            else
                additionalDataComponentDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                additionalDataComponentDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("additionalDataComponentDd", additionalDataComponentDdModel);
        }
        return additionalDataComponentDdModel;
    }

    /** Setter for DropDown property additionalDataComponent. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property additionalDataComponentDd.
     */
    public void setAdditionalDataComponentDdWV(String value) {
        DropDownController.updateModel(value, getAdditionalDataComponentDdWM());
    }

    // .//GEN-END:additionalDataComponent_2_be
    // .//GEN-BEGIN:followOnFormName_1_be
    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public String getFollowOnFormName() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFollowOnFormName();
    }

    /** Setter for property followOnFormName.
     * @param followOnFormName New value of property followOnFormName.
     */
    public void setFollowOnFormName(String followOnFormName) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFollowOnFormName(followOnFormName);
    }

    /** Getter for property followOnFormName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormName.
     */
    public EditBoxModel getFollowOnFormNameWM() {
        EditBoxModel followOnFormNameModel = (EditBoxModel) getWidgetCache().getModel("followOnFormName");
        if (followOnFormNameModel == null) {
            if (getFollowOnFormName() != null)
                followOnFormNameModel = new EditBoxModel( getFollowOnFormName() );
            else
                followOnFormNameModel = new EditBoxModel();
            followOnFormNameModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME).getCaseType() );

            // .//GEN-END:followOnFormName_1_be
            // Add custom code //GEN-FIRST:followOnFormName_1


            // .//GEN-LAST:followOnFormName_1
            // .//GEN-BEGIN:followOnFormName_2_be
            getWidgetCache().addModel("followOnFormName", followOnFormNameModel);
        }
        return followOnFormNameModel;
    }

    /** Setter for property followOnFormName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property followOnFormName.
     */
    public void setFollowOnFormNameWV(String value) {
        EditBoxController.updateModel(value, getFollowOnFormNameWM());
    }

    /** Getter for DropDown property followOnFormName.
     * @return Value of property followOnFormNameDd.
     */
    public String getFollowOnFormNameDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFollowOnFormNameDd();
    }

    /** Setter for DropDown property followOnFormName.
     * @param followOnFormNameDd New value of property followOnFormNameDd.
     */
    public void setFollowOnFormNameDd(String followOnFormNameDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFollowOnFormNameDd(followOnFormNameDd);
    }

    /** Getter for DropDown property followOnFormName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormNameDd.
     */
    public DropDownModel getFollowOnFormNameDdWM() {
        DropDownModel followOnFormNameDdModel = (DropDownModel) getWidgetCache().getModel("followOnFormNameDd");
        if (followOnFormNameDdModel == null) {
            if (getFollowOnFormNameDd() != null)
                followOnFormNameDdModel = new DropDownModel( getFollowOnFormNameDd() );
            else
                followOnFormNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                followOnFormNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("followOnFormNameDd", followOnFormNameDdModel);
        }
        return followOnFormNameDdModel;
    }

    /** Setter for DropDown property followOnFormName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property followOnFormNameDd.
     */
    public void setFollowOnFormNameDdWV(String value) {
        DropDownController.updateModel(value, getFollowOnFormNameDdWM());
    }

    // .//GEN-END:followOnFormName_2_be
    // .//GEN-BEGIN:followOnFormAlternate_1_be
    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public String getFollowOnFormAlternate() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFollowOnFormAlternate();
    }

    /** Setter for property followOnFormAlternate.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternate(String followOnFormAlternate) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFollowOnFormAlternate(followOnFormAlternate);
    }

    /** Getter for property followOnFormAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormAlternate.
     */
    public EditBoxModel getFollowOnFormAlternateWM() {
        EditBoxModel followOnFormAlternateModel = (EditBoxModel) getWidgetCache().getModel("followOnFormAlternate");
        if (followOnFormAlternateModel == null) {
            if (getFollowOnFormAlternate() != null)
                followOnFormAlternateModel = new EditBoxModel( getFollowOnFormAlternate() );
            else
                followOnFormAlternateModel = new EditBoxModel();
            followOnFormAlternateModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE).getCaseType() );

            // .//GEN-END:followOnFormAlternate_1_be
            // Add custom code //GEN-FIRST:followOnFormAlternate_1


            // .//GEN-LAST:followOnFormAlternate_1
            // .//GEN-BEGIN:followOnFormAlternate_2_be
            getWidgetCache().addModel("followOnFormAlternate", followOnFormAlternateModel);
        }
        return followOnFormAlternateModel;
    }

    /** Setter for property followOnFormAlternate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternateWV(String value) {
        EditBoxController.updateModel(value, getFollowOnFormAlternateWM());
    }

    /** Getter for DropDown property followOnFormAlternate.
     * @return Value of property followOnFormAlternateDd.
     */
    public String getFollowOnFormAlternateDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getFollowOnFormAlternateDd();
    }

    /** Setter for DropDown property followOnFormAlternate.
     * @param followOnFormAlternateDd New value of property followOnFormAlternateDd.
     */
    public void setFollowOnFormAlternateDd(String followOnFormAlternateDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setFollowOnFormAlternateDd(followOnFormAlternateDd);
    }

    /** Getter for DropDown property followOnFormAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormAlternateDd.
     */
    public DropDownModel getFollowOnFormAlternateDdWM() {
        DropDownModel followOnFormAlternateDdModel = (DropDownModel) getWidgetCache().getModel("followOnFormAlternateDd");
        if (followOnFormAlternateDdModel == null) {
            if (getFollowOnFormAlternateDd() != null)
                followOnFormAlternateDdModel = new DropDownModel( getFollowOnFormAlternateDd() );
            else
                followOnFormAlternateDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                followOnFormAlternateDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("followOnFormAlternateDd", followOnFormAlternateDdModel);
        }
        return followOnFormAlternateDdModel;
    }

    /** Setter for DropDown property followOnFormAlternate. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property followOnFormAlternateDd.
     */
    public void setFollowOnFormAlternateDdWV(String value) {
        DropDownController.updateModel(value, getFollowOnFormAlternateDdWM());
    }

    // .//GEN-END:followOnFormAlternate_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (FormDefinitionLookupComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public EditBoxModel getCreatedOnWM() {
        EditBoxModel createdOnModel = (EditBoxModel) getWidgetCache().getModel("createdOn");
        if (createdOnModel == null) {
            if (getCreatedOn() != null)
                createdOnModel = new EditBoxModel( getCreatedOn() );
            else
                createdOnModel = new EditBoxModel();

            // .//GEN-END:createdOn_1_be
            // Add custom code //GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", createdOnModel);
        }
        return createdOnModel;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        EditBoxController.updateModel(value, getCreatedOnWM());
    }

    /** Getter for DropDown property createdOn.
     * @return Value of property createdOnDd.
     */
    public String getCreatedOnDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setCreatedOnDd(createdOnDd);
    }

    /** Getter for DropDown property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOnDd.
     */
    public DropDownModel getCreatedOnDdWM() {
        DropDownModel createdOnDdModel = (DropDownModel) getWidgetCache().getModel("createdOnDd");
        if (createdOnDdModel == null) {
            if (getCreatedOnDd() != null)
                createdOnDdModel = new DropDownModel( getCreatedOnDd() );
            else
                createdOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                createdOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("createdOnDd", createdOnDdModel);
        }
        return createdOnDdModel;
    }

    /** Setter for DropDown property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOnDd.
     */
    public void setCreatedOnDdWV(String value) {
        DropDownController.updateModel(value, getCreatedOnDdWM());
    }

    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        ( (FormDefinitionLookupComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel createdByModel = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (createdByModel == null) {
            if (getCreatedBy() != null)
                createdByModel = new EditBoxModel( getCreatedBy() );
            else
                createdByModel = new EditBoxModel();
            createdByModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_CREATED_BY).getCaseType() );

            // .//GEN-END:createdBy_1_be
            // Add custom code //GEN-FIRST:createdBy_1


            // .//GEN-LAST:createdBy_1
            // .//GEN-BEGIN:createdBy_2_be
            getWidgetCache().addModel("createdBy", createdByModel);
        }
        return createdByModel;
    }

    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        EditBoxController.updateModel(value, getCreatedByWM());
    }

    /** Getter for DropDown property createdBy.
     * @return Value of property createdByDd.
     */
    public String getCreatedByDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getCreatedByDd();
    }

    /** Setter for DropDown property createdBy.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setCreatedByDd(createdByDd);
    }

    /** Getter for DropDown property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdByDd.
     */
    public DropDownModel getCreatedByDdWM() {
        DropDownModel createdByDdModel = (DropDownModel) getWidgetCache().getModel("createdByDd");
        if (createdByDdModel == null) {
            if (getCreatedByDd() != null)
                createdByDdModel = new DropDownModel( getCreatedByDd() );
            else
                createdByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                createdByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("createdByDd", createdByDdModel);
        }
        return createdByDdModel;
    }

    /** Setter for DropDown property createdBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdByDd.
     */
    public void setCreatedByDdWV(String value) {
        DropDownController.updateModel(value, getCreatedByDdWM());
    }

    // .//GEN-END:createdBy_2_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public String getLastChangedOn() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        ( (FormDefinitionLookupComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public EditBoxModel getLastChangedOnWM() {
        EditBoxModel lastChangedOnModel = (EditBoxModel) getWidgetCache().getModel("lastChangedOn");
        if (lastChangedOnModel == null) {
            if (getLastChangedOn() != null)
                lastChangedOnModel = new EditBoxModel( getLastChangedOn() );
            else
                lastChangedOnModel = new EditBoxModel();

            // .//GEN-END:lastChangedOn_1_be
            // Add custom code //GEN-FIRST:lastChangedOn_1


            // .//GEN-LAST:lastChangedOn_1
            // .//GEN-BEGIN:lastChangedOn_2_be
            getWidgetCache().addModel("lastChangedOn", lastChangedOnModel);
        }
        return lastChangedOnModel;
    }

    /** Setter for property lastChangedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedOn.
     */
    public void setLastChangedOnWV(String value) {
        EditBoxController.updateModel(value, getLastChangedOnWM());
    }

    /** Getter for DropDown property lastChangedOn.
     * @return Value of property lastChangedOnDd.
     */
    public String getLastChangedOnDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getLastChangedOnDd();
    }

    /** Setter for DropDown property lastChangedOn.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setLastChangedOnDd(lastChangedOnDd);
    }

    /** Getter for DropDown property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOnDd.
     */
    public DropDownModel getLastChangedOnDdWM() {
        DropDownModel lastChangedOnDdModel = (DropDownModel) getWidgetCache().getModel("lastChangedOnDd");
        if (lastChangedOnDdModel == null) {
            if (getLastChangedOnDd() != null)
                lastChangedOnDdModel = new DropDownModel( getLastChangedOnDd() );
            else
                lastChangedOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastChangedOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastChangedOnDd", lastChangedOnDdModel);
        }
        return lastChangedOnDdModel;
    }

    /** Setter for DropDown property lastChangedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDdWV(String value) {
        DropDownController.updateModel(value, getLastChangedOnDdWM());
    }

    // .//GEN-END:lastChangedOn_2_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        ( (FormDefinitionLookupComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel lastChangedByModel = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (lastChangedByModel == null) {
            if (getLastChangedBy() != null)
                lastChangedByModel = new EditBoxModel( getLastChangedBy() );
            else
                lastChangedByModel = new EditBoxModel();
            lastChangedByModel.setStringCase( ((StringFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_BY).getCaseType() );

            // .//GEN-END:lastChangedBy_1_be
            // Add custom code //GEN-FIRST:lastChangedBy_1


            // .//GEN-LAST:lastChangedBy_1
            // .//GEN-BEGIN:lastChangedBy_2_be
            getWidgetCache().addModel("lastChangedBy", lastChangedByModel);
        }
        return lastChangedByModel;
    }

    /** Setter for property lastChangedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedBy.
     */
    public void setLastChangedByWV(String value) {
        EditBoxController.updateModel(value, getLastChangedByWM());
    }

    /** Getter for DropDown property lastChangedBy.
     * @return Value of property lastChangedByDd.
     */
    public String getLastChangedByDd() {
        return ( (FormDefinitionLookupComponent) getComponent() ).getLastChangedByDd();
    }

    /** Setter for DropDown property lastChangedBy.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        ( (FormDefinitionLookupComponent) getComponent() ).setLastChangedByDd(lastChangedByDd);
    }

    /** Getter for DropDown property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedByDd.
     */
    public DropDownModel getLastChangedByDdWM() {
        DropDownModel lastChangedByDdModel = (DropDownModel) getWidgetCache().getModel("lastChangedByDd");
        if (lastChangedByDdModel == null) {
            if (getLastChangedByDd() != null)
                lastChangedByDdModel = new DropDownModel( getLastChangedByDd() );
            else
                lastChangedByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastChangedByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastChangedByDd", lastChangedByDdModel);
        }
        return lastChangedByDdModel;
    }

    /** Setter for DropDown property lastChangedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedByDd.
     */
    public void setLastChangedByDdWV(String value) {
        DropDownController.updateModel(value, getLastChangedByDdWM());
    }

    // .//GEN-END:lastChangedBy_2_be
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

        value = getFieldLayoutWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFieldLayout(value);
        setFieldLayoutDd(getFieldLayoutDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

        value = getRemarksWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRemarks(value);
        setRemarksDd(getRemarksDdWM().getValue());

        value = getDomFactoryWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomFactory(value);
        setDomFactoryDd(getDomFactoryDdWM().getValue());

        value = getDomClassWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomClass(value);
        setDomClassDd(getDomClassDdWM().getValue());

        value = getDomKey1WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey1(value);
        setDomKey1Dd(getDomKey1DdWM().getValue());

        value = getDomKey2WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey2(value);
        setDomKey2Dd(getDomKey2DdWM().getValue());

        value = getDomKey3WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey3(value);
        setDomKey3Dd(getDomKey3DdWM().getValue());

        value = getDomKey4WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey4(value);
        setDomKey4Dd(getDomKey4DdWM().getValue());

        value = getDomKey5WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey5(value);
        setDomKey5Dd(getDomKey5DdWM().getValue());

        value = getDomKey6WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDomKey6(value);
        setDomKey6Dd(getDomKey6DdWM().getValue());

        value = getAdditionalDataComponentWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setAdditionalDataComponent(value);
        setAdditionalDataComponentDd(getAdditionalDataComponentDdWM().getValue());

        value = getFollowOnFormNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFollowOnFormName(value);
        setFollowOnFormNameDd(getFollowOnFormNameDdWM().getValue());

        value = getFollowOnFormAlternateWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFollowOnFormAlternate(value);
        setFollowOnFormAlternateDd(getFollowOnFormAlternateDdWM().getValue());

        value = getCreatedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCreatedOn(value);
        setCreatedOnDd(getCreatedOnDdWM().getValue());

        value = getCreatedByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCreatedBy(value);
        setCreatedByDd(getCreatedByDdWM().getValue());

        value = getLastChangedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastChangedOn(value);
        setLastChangedOnDd(getLastChangedOnDdWM().getValue());

        value = getLastChangedByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastChangedBy(value);
        setLastChangedByDd(getLastChangedByDdWM().getValue());

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
        FormDefinitionLookupOutDto outputDto = (FormDefinitionLookupOutDto) ((FormDefinitionLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormDefinitionLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("formId", rowDto.getFormId());
                row.addElement("formName", rowDto.getFormName());
                row.addElement("formAlternate", rowDto.getFormAlternate());
                row.addElement("formVariation", rowDto.getFormVariation());
                row.addElement("outputType", rowDto.getOutputType());
                row.addElement("formTemplate", rowDto.getFormTemplate());
                row.addElement("fieldLayout", rowDto.getFieldLayout());
                row.addElement("description", rowDto.getDescription());
                row.addElement("remarks", rowDto.getRemarks());
                row.addElement("domFactory", rowDto.getDomFactory());
                row.addElement("domClass", rowDto.getDomClass());
                row.addElement("domKey1", rowDto.getDomKey1());
                row.addElement("domKey2", rowDto.getDomKey2());
                row.addElement("domKey3", rowDto.getDomKey3());
                row.addElement("domKey4", rowDto.getDomKey4());
                row.addElement("domKey5", rowDto.getDomKey5());
                row.addElement("domKey6", rowDto.getDomKey6());
                row.addElement("additionalDataComponent", rowDto.getAdditionalDataComponent());
                row.addElement("followOnFormName", rowDto.getFollowOnFormName());
                row.addElement("followOnFormAlternate", rowDto.getFollowOnFormAlternate());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("createdBy", rowDto.getCreatedBy());
                row.addElement("lastChangedOn", rowDto.getLastChangedOn());
                row.addElement("lastChangedBy", rowDto.getLastChangedBy());
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
