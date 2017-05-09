// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionmaintenance.ui;

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

import org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;

import org.jaffa.modules.printing.domain.FormTemplateMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports

import org.apache.struts.upload.FormFile;


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the FormDefinitionMaintenance.
 */
public class FormDefinitionMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(FormDefinitionMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFormId();
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(java.lang.Long formId) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFormId(formId);
    }

    /** Getter for property formId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formId.
     */
    public EditBoxModel getFormIdWM() {
        EditBoxModel w_formId = (EditBoxModel) getWidgetCache().getModel("formId");
        if (w_formId == null) {
            if (getFormId() != null)
                w_formId = new EditBoxModel(getFormId(), (IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID);
            else
                w_formId = new EditBoxModel((IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID);
            // .//GEN-END:formId_1_be
            // Add custom code//GEN-FIRST:formId_1


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
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFormName(formName);
    }

    /** Getter for property formName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formName.
     */
    public EditBoxModel getFormNameWM() {
        EditBoxModel w_formName = (EditBoxModel) getWidgetCache().getModel("formName");
        if (w_formName == null) {
            if (getFormName() != null)
                w_formName = new EditBoxModel(getFormName(), (StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME);
            else
                w_formName = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME);
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
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFormAlternate();
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(java.lang.String formAlternate) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFormAlternate(formAlternate);
    }

    /** Getter for property formAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formAlternate.
     */
    public EditBoxModel getFormAlternateWM() {
        EditBoxModel w_formAlternate = (EditBoxModel) getWidgetCache().getModel("formAlternate");
        if (w_formAlternate == null) {
            if (getFormAlternate() != null)
                w_formAlternate = new EditBoxModel(getFormAlternate(), (StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE);
            else
                w_formAlternate = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE);
            // .//GEN-END:formAlternate_1_be
            // Add custom code//GEN-FIRST:formAlternate_1


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
    // .//GEN-BEGIN:formVariation_1_be
    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public java.lang.String getFormVariation() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFormVariation();
    }

    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(java.lang.String formVariation) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFormVariation(formVariation);
    }

    /** Getter for property formVariation. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formVariation.
     */
    public EditBoxModel getFormVariationWM() {
        EditBoxModel w_formVariation = (EditBoxModel) getWidgetCache().getModel("formVariation");
        if (w_formVariation == null) {
            if (getFormVariation() != null)
                w_formVariation = new EditBoxModel(getFormVariation(), (StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION);
            else
                w_formVariation = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION);
            // .//GEN-END:formVariation_1_be
            // Add custom code//GEN-FIRST:formVariation_1


            // .//GEN-LAST:formVariation_1
            // .//GEN-BEGIN:formVariation_2_be
            getWidgetCache().addModel("formVariation", w_formVariation);
        }
        return w_formVariation;
    }

    /** Setter for property formVariation. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formVariation.
     */
    public void setFormVariationWV(String value) {
        EditBoxController.updateModel(value, getFormVariationWM());
    }
    // .//GEN-END:formVariation_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public EditBoxModel getOutputTypeWM() {
        EditBoxModel w_outputType = (EditBoxModel) getWidgetCache().getModel("outputType");
        if (w_outputType == null) {
            if (getOutputType() != null)
                w_outputType = new EditBoxModel(getOutputType(), (StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE);
            else
                w_outputType = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE);
            w_outputType.setMandatory(true);
            // .//GEN-END:outputType_1_be
            // Add custom code//GEN-FIRST:outputType_1


            // .//GEN-LAST:outputType_1
            // .//GEN-BEGIN:outputType_2_be
            getWidgetCache().addModel("outputType", w_outputType);
        }
        return w_outputType;
    }

    /** Setter for property outputType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property outputType.
     */
    public void setOutputTypeWV(String value) {
        EditBoxController.updateModel(value, getOutputTypeWM());
    }
    // .//GEN-END:outputType_2_be
    // .//GEN-BEGIN:formTemplate_1_be
    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public java.lang.String getFormTemplate() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFormTemplate();
    }

    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(java.lang.String formTemplate) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFormTemplate(formTemplate);
    }

    /** Getter for property formTemplate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formTemplate.
     */
    public EditBoxModel getFormTemplateWM() {
        EditBoxModel w_formTemplate = (EditBoxModel) getWidgetCache().getModel("formTemplate");
        if (w_formTemplate == null) {
            if (getFormTemplate() != null)
                w_formTemplate = new EditBoxModel(getFormTemplate(), (StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE);
            else
                w_formTemplate = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE);
            // .//GEN-END:formTemplate_1_be
            // Add custom code//GEN-FIRST:formTemplate_1

            
            // .//GEN-LAST:formTemplate_1
            // .//GEN-BEGIN:formTemplate_2_be
            getWidgetCache().addModel("formTemplate", w_formTemplate);
        }
        return w_formTemplate;
    }

    /** Setter for property formTemplate. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property formTemplate.
     */
    public void setFormTemplateWV(String value) {
        EditBoxController.updateModel(value, getFormTemplateWM());
    }
    // .//GEN-END:formTemplate_2_be
    // .//GEN-BEGIN:fieldLayout_1_be
    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public java.lang.String getFieldLayout() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFieldLayout();
    }

    /** Setter for property fieldLayout.
     * @param fieldLayout New value of property fieldLayout.
     */
    public void setFieldLayout(java.lang.String fieldLayout) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFieldLayout(fieldLayout);
    }

    /** Getter for property fieldLayout. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fieldLayout.
     */
    public EditBoxModel getFieldLayoutWM() {
        EditBoxModel w_fieldLayout = (EditBoxModel) getWidgetCache().getModel("fieldLayout");
        if (w_fieldLayout == null) {
            if (getFieldLayout() != null)
                w_fieldLayout = new EditBoxModel(getFieldLayout(), (StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT);
            else
                w_fieldLayout = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT);
            // .//GEN-END:fieldLayout_1_be
            // Add custom code//GEN-FIRST:fieldLayout_1
            

            // .//GEN-LAST:fieldLayout_1
            // .//GEN-BEGIN:fieldLayout_2_be
            getWidgetCache().addModel("fieldLayout", w_fieldLayout);
        }
        return w_fieldLayout;
    }

    /** Setter for property fieldLayout. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property fieldLayout.
     */
    public void setFieldLayoutWV(String value) {
        EditBoxController.updateModel(value, getFieldLayoutWM());
    }
    // .//GEN-END:fieldLayout_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION);
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
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel w_remarks = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (w_remarks == null) {
            if (getRemarks() != null)
                w_remarks = new EditBoxModel(getRemarks(), (StringFieldMetaData) FormDefinitionMeta.META_REMARKS);
            else
                w_remarks = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_REMARKS);
            // .//GEN-END:remarks_1_be
            // Add custom code//GEN-FIRST:remarks_1

            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", w_remarks);
        }
        return w_remarks;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }
    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:domFactory_1_be
    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public java.lang.String getDomFactory() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomFactory();
    }

    /** Setter for property domFactory.
     * @param domFactory New value of property domFactory.
     */
    public void setDomFactory(java.lang.String domFactory) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomFactory(domFactory);
    }

    /** Getter for property domFactory. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domFactory.
     */
    public EditBoxModel getDomFactoryWM() {
        EditBoxModel w_domFactory = (EditBoxModel) getWidgetCache().getModel("domFactory");
        if (w_domFactory == null) {
            if (getDomFactory() != null)
                w_domFactory = new EditBoxModel(getDomFactory(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY);
            else
                w_domFactory = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY);
            w_domFactory.setMandatory(true);
            // .//GEN-END:domFactory_1_be
            // Add custom code//GEN-FIRST:domFactory_1


            // .//GEN-LAST:domFactory_1
            // .//GEN-BEGIN:domFactory_2_be
            getWidgetCache().addModel("domFactory", w_domFactory);
        }
        return w_domFactory;
    }

    /** Setter for property domFactory. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domFactory.
     */
    public void setDomFactoryWV(String value) {
        EditBoxController.updateModel(value, getDomFactoryWM());
    }
    // .//GEN-END:domFactory_2_be
    // .//GEN-BEGIN:domClass_1_be
    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public java.lang.String getDomClass() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomClass();
    }

    /** Setter for property domClass.
     * @param domClass New value of property domClass.
     */
    public void setDomClass(java.lang.String domClass) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomClass(domClass);
    }

    /** Getter for property domClass. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domClass.
     */
    public EditBoxModel getDomClassWM() {
        EditBoxModel w_domClass = (EditBoxModel) getWidgetCache().getModel("domClass");
        if (w_domClass == null) {
            if (getDomClass() != null)
                w_domClass = new EditBoxModel(getDomClass(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS);
            else
                w_domClass = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS);
            w_domClass.setMandatory(true);
            // .//GEN-END:domClass_1_be
            // Add custom code//GEN-FIRST:domClass_1


            // .//GEN-LAST:domClass_1
            // .//GEN-BEGIN:domClass_2_be
            getWidgetCache().addModel("domClass", w_domClass);
        }
        return w_domClass;
    }

    /** Setter for property domClass. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domClass.
     */
    public void setDomClassWV(String value) {
        EditBoxController.updateModel(value, getDomClassWM());
    }
    // .//GEN-END:domClass_2_be
    // .//GEN-BEGIN:domKey1_1_be
    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public java.lang.String getDomKey1() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey1();
    }

    /** Setter for property domKey1.
     * @param domKey1 New value of property domKey1.
     */
    public void setDomKey1(java.lang.String domKey1) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey1(domKey1);
    }

    /** Getter for property domKey1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey1.
     */
    public EditBoxModel getDomKey1WM() {
        EditBoxModel w_domKey1 = (EditBoxModel) getWidgetCache().getModel("domKey1");
        if (w_domKey1 == null) {
            if (getDomKey1() != null)
                w_domKey1 = new EditBoxModel(getDomKey1(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1);
            else
                w_domKey1 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1);
            w_domKey1.setMandatory(true);
            // .//GEN-END:domKey1_1_be
            // Add custom code//GEN-FIRST:domKey1_1


            // .//GEN-LAST:domKey1_1
            // .//GEN-BEGIN:domKey1_2_be
            getWidgetCache().addModel("domKey1", w_domKey1);
        }
        return w_domKey1;
    }

    /** Setter for property domKey1. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey1.
     */
    public void setDomKey1WV(String value) {
        EditBoxController.updateModel(value, getDomKey1WM());
    }
    // .//GEN-END:domKey1_2_be
    // .//GEN-BEGIN:domKey2_1_be
    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public java.lang.String getDomKey2() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey2();
    }

    /** Setter for property domKey2.
     * @param domKey2 New value of property domKey2.
     */
    public void setDomKey2(java.lang.String domKey2) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey2(domKey2);
    }

    /** Getter for property domKey2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey2.
     */
    public EditBoxModel getDomKey2WM() {
        EditBoxModel w_domKey2 = (EditBoxModel) getWidgetCache().getModel("domKey2");
        if (w_domKey2 == null) {
            if (getDomKey2() != null)
                w_domKey2 = new EditBoxModel(getDomKey2(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2);
            else
                w_domKey2 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2);
            // .//GEN-END:domKey2_1_be
            // Add custom code//GEN-FIRST:domKey2_1


            // .//GEN-LAST:domKey2_1
            // .//GEN-BEGIN:domKey2_2_be
            getWidgetCache().addModel("domKey2", w_domKey2);
        }
        return w_domKey2;
    }

    /** Setter for property domKey2. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey2.
     */
    public void setDomKey2WV(String value) {
        EditBoxController.updateModel(value, getDomKey2WM());
    }
    // .//GEN-END:domKey2_2_be
    // .//GEN-BEGIN:domKey3_1_be
    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public java.lang.String getDomKey3() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey3();
    }

    /** Setter for property domKey3.
     * @param domKey3 New value of property domKey3.
     */
    public void setDomKey3(java.lang.String domKey3) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey3(domKey3);
    }

    /** Getter for property domKey3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey3.
     */
    public EditBoxModel getDomKey3WM() {
        EditBoxModel w_domKey3 = (EditBoxModel) getWidgetCache().getModel("domKey3");
        if (w_domKey3 == null) {
            if (getDomKey3() != null)
                w_domKey3 = new EditBoxModel(getDomKey3(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3);
            else
                w_domKey3 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3);
            // .//GEN-END:domKey3_1_be
            // Add custom code//GEN-FIRST:domKey3_1


            // .//GEN-LAST:domKey3_1
            // .//GEN-BEGIN:domKey3_2_be
            getWidgetCache().addModel("domKey3", w_domKey3);
        }
        return w_domKey3;
    }

    /** Setter for property domKey3. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey3.
     */
    public void setDomKey3WV(String value) {
        EditBoxController.updateModel(value, getDomKey3WM());
    }
    // .//GEN-END:domKey3_2_be
    // .//GEN-BEGIN:domKey4_1_be
    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public java.lang.String getDomKey4() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey4();
    }

    /** Setter for property domKey4.
     * @param domKey4 New value of property domKey4.
     */
    public void setDomKey4(java.lang.String domKey4) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey4(domKey4);
    }

    /** Getter for property domKey4. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey4.
     */
    public EditBoxModel getDomKey4WM() {
        EditBoxModel w_domKey4 = (EditBoxModel) getWidgetCache().getModel("domKey4");
        if (w_domKey4 == null) {
            if (getDomKey4() != null)
                w_domKey4 = new EditBoxModel(getDomKey4(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4);
            else
                w_domKey4 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4);
            // .//GEN-END:domKey4_1_be
            // Add custom code //GEN-FIRST:domKey4_1


            // .//GEN-LAST:domKey4_1
            // .//GEN-BEGIN:domKey4_2_be
            getWidgetCache().addModel("domKey4", w_domKey4);
        }
        return w_domKey4;
    }

    /** Setter for property domKey4. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey4.
     */
    public void setDomKey4WV(String value) {
        EditBoxController.updateModel(value, getDomKey4WM());
    }
    // .//GEN-END:domKey4_2_be
    // .//GEN-BEGIN:domKey5_1_be
    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public java.lang.String getDomKey5() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey5();
    }

    /** Setter for property domKey5.
     * @param domKey5 New value of property domKey5.
     */
    public void setDomKey5(java.lang.String domKey5) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey5(domKey5);
    }

    /** Getter for property domKey5. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey5.
     */
    public EditBoxModel getDomKey5WM() {
        EditBoxModel w_domKey5 = (EditBoxModel) getWidgetCache().getModel("domKey5");
        if (w_domKey5 == null) {
            if (getDomKey5() != null)
                w_domKey5 = new EditBoxModel(getDomKey5(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5);
            else
                w_domKey5 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5);
            // .//GEN-END:domKey5_1_be
            // Add custom code //GEN-FIRST:domKey5_1


            // .//GEN-LAST:domKey5_1
            // .//GEN-BEGIN:domKey5_2_be
            getWidgetCache().addModel("domKey5", w_domKey5);
        }
        return w_domKey5;
    }

    /** Setter for property domKey5. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey5.
     */
    public void setDomKey5WV(String value) {
        EditBoxController.updateModel(value, getDomKey5WM());
    }
    // .//GEN-END:domKey5_2_be
    // .//GEN-BEGIN:domKey6_1_be
    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public java.lang.String getDomKey6() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getDomKey6();
    }

    /** Setter for property domKey6.
     * @param domKey6 New value of property domKey6.
     */
    public void setDomKey6(java.lang.String domKey6) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setDomKey6(domKey6);
    }

    /** Getter for property domKey6. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domKey6.
     */
    public EditBoxModel getDomKey6WM() {
        EditBoxModel w_domKey6 = (EditBoxModel) getWidgetCache().getModel("domKey6");
        if (w_domKey6 == null) {
            if (getDomKey6() != null)
                w_domKey6 = new EditBoxModel(getDomKey6(), (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6);
            else
                w_domKey6 = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6);
            // .//GEN-END:domKey6_1_be
            // Add custom code //GEN-FIRST:domKey6_1


            // .//GEN-LAST:domKey6_1
            // .//GEN-BEGIN:domKey6_2_be
            getWidgetCache().addModel("domKey6", w_domKey6);
        }
        return w_domKey6;
    }

    /** Setter for property domKey6. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property domKey6.
     */
    public void setDomKey6WV(String value) {
        EditBoxController.updateModel(value, getDomKey6WM());
    }
    // .//GEN-END:domKey6_2_be
    // .//GEN-BEGIN:additionalDataComponent_1_be
    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public java.lang.String getAdditionalDataComponent() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getAdditionalDataComponent();
    }

    /** Setter for property additionalDataComponent.
     * @param additionalDataComponent New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponent(java.lang.String additionalDataComponent) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setAdditionalDataComponent(additionalDataComponent);
    }

    /** Getter for property additionalDataComponent. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property additionalDataComponent.
     */
    public EditBoxModel getAdditionalDataComponentWM() {
        EditBoxModel w_additionalDataComponent = (EditBoxModel) getWidgetCache().getModel("additionalDataComponent");
        if (w_additionalDataComponent == null) {
            if (getAdditionalDataComponent() != null)
                w_additionalDataComponent = new EditBoxModel(getAdditionalDataComponent(), (StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT);
            else
                w_additionalDataComponent = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT);
            // .//GEN-END:additionalDataComponent_1_be
            // Add custom code//GEN-FIRST:additionalDataComponent_1


            // .//GEN-LAST:additionalDataComponent_1
            // .//GEN-BEGIN:additionalDataComponent_2_be
            getWidgetCache().addModel("additionalDataComponent", w_additionalDataComponent);
        }
        return w_additionalDataComponent;
    }

    /** Setter for property additionalDataComponent. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property additionalDataComponent.
     */
    public void setAdditionalDataComponentWV(String value) {
        EditBoxController.updateModel(value, getAdditionalDataComponentWM());
    }
    // .//GEN-END:additionalDataComponent_2_be
    // .//GEN-BEGIN:followOnFormName_1_be
    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public java.lang.String getFollowOnFormName() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFollowOnFormName();
    }

    /** Setter for property followOnFormName.
     * @param followOnFormName New value of property followOnFormName.
     */
    public void setFollowOnFormName(java.lang.String followOnFormName) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFollowOnFormName(followOnFormName);
    }

    /** Getter for property followOnFormName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormName.
     */
    public EditBoxModel getFollowOnFormNameWM() {
        EditBoxModel w_followOnFormName = (EditBoxModel) getWidgetCache().getModel("followOnFormName");
        if (w_followOnFormName == null) {
            if (getFollowOnFormName() != null)
                w_followOnFormName = new EditBoxModel(getFollowOnFormName(), (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME);
            else
                w_followOnFormName = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME);
            // .//GEN-END:followOnFormName_1_be
            // Add custom code//GEN-FIRST:followOnFormName_1


            // .//GEN-LAST:followOnFormName_1
            // .//GEN-BEGIN:followOnFormName_2_be
            getWidgetCache().addModel("followOnFormName", w_followOnFormName);
        }
        return w_followOnFormName;
    }

    /** Setter for property followOnFormName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property followOnFormName.
     */
    public void setFollowOnFormNameWV(String value) {
        EditBoxController.updateModel(value, getFollowOnFormNameWM());
    }
    // .//GEN-END:followOnFormName_2_be
    // .//GEN-BEGIN:followOnFormAlternate_1_be
    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public java.lang.String getFollowOnFormAlternate() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFollowOnFormAlternate();
    }

    /** Setter for property followOnFormAlternate.
     * @param followOnFormAlternate New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternate(java.lang.String followOnFormAlternate) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setFollowOnFormAlternate(followOnFormAlternate);
    }

    /** Getter for property followOnFormAlternate. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property followOnFormAlternate.
     */
    public EditBoxModel getFollowOnFormAlternateWM() {
        EditBoxModel w_followOnFormAlternate = (EditBoxModel) getWidgetCache().getModel("followOnFormAlternate");
        if (w_followOnFormAlternate == null) {
            if (getFollowOnFormAlternate() != null)
                w_followOnFormAlternate = new EditBoxModel(getFollowOnFormAlternate(), (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE);
            else
                w_followOnFormAlternate = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE);
            // .//GEN-END:followOnFormAlternate_1_be
            // Add custom code//GEN-FIRST:followOnFormAlternate_1


            // .//GEN-LAST:followOnFormAlternate_1
            // .//GEN-BEGIN:followOnFormAlternate_2_be
            getWidgetCache().addModel("followOnFormAlternate", w_followOnFormAlternate);
        }
        return w_followOnFormAlternate;
    }

    /** Setter for property followOnFormAlternate. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property followOnFormAlternate.
     */
    public void setFollowOnFormAlternateWV(String value) {
        EditBoxController.updateModel(value, getFollowOnFormAlternateWM());
    }
    // .//GEN-END:followOnFormAlternate_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) FormDefinitionMeta.META_CREATED_ON);
            // .//GEN-END:createdOn_1_be
            // Add custom code//GEN-FIRST:createdOn_1


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
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel w_createdBy = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            if (getCreatedBy() != null)
                w_createdBy = new EditBoxModel(getCreatedBy(), (StringFieldMetaData) FormDefinitionMeta.META_CREATED_BY);
            else
                w_createdBy = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_CREATED_BY);
            // .//GEN-END:createdBy_1_be
            // Add custom code//GEN-FIRST:createdBy_1


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
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public DateTimeModel getLastChangedOnWM() {
        DateTimeModel w_lastChangedOn = (DateTimeModel) getWidgetCache().getModel("lastChangedOn");
        if (w_lastChangedOn == null) {
            w_lastChangedOn = new DateTimeModel(getLastChangedOn(), (DateTimeFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_ON);
            // .//GEN-END:lastChangedOn_1_be
            // Add custom code//GEN-FIRST:lastChangedOn_1


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
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        ( (FormDefinitionMaintenanceComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel w_lastChangedBy = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (w_lastChangedBy == null) {
            if (getLastChangedBy() != null)
                w_lastChangedBy = new EditBoxModel(getLastChangedBy(), (StringFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_BY);
            else
                w_lastChangedBy = new EditBoxModel((StringFieldMetaData) FormDefinitionMeta.META_LAST_CHANGED_BY);
            // .//GEN-END:lastChangedBy_1_be
            // Add custom code//GEN-FIRST:lastChangedBy_1


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
            String htmlString = getFormIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID, true);

            setFormId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) FormDefinitionMeta.META_FORM_ID).getLabelToken(), e);
        }


        try {
            String htmlString = getFormNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME, true);

            setFormName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FORM_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getFormAlternateWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE, true);

            setFormAlternate(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FORM_ALTERNATE).getLabelToken(), e);
        }


        try {
            String htmlString = getFormVariationWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION, true);

            setFormVariation(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FORM_VARIATION).getLabelToken(), e);
        }


        try {
            String htmlString = getOutputTypeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE).getLabelToken());

            setOutputType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_OUTPUT_TYPE).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION, true);

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        try {
            String htmlString = getRemarksWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_REMARKS, true);

            setRemarks(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_REMARKS).getLabelToken(), e);
        }


        try {
            String htmlString = getFollowOnFormNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME, true);

            setFollowOnFormName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getFormTemplateWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE, true);

            setFormTemplate(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FORM_TEMPLATE).getLabelToken(), e);
        }


        try {
            String htmlString = getFieldLayoutWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT, true);

            setFieldLayout(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FIELD_LAYOUT).getLabelToken(), e);
        }


        try {
            String htmlString = getDomClassWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS).getLabelToken());

            setDomClass(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_CLASS).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey1WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1).getLabelToken());

            setDomKey1(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY1).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey2WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2, true);

            setDomKey2(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY2).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey3WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3, true);

            setDomKey3(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY3).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey4WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4, true);

            setDomKey4(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY4).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey5WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5, true);

            setDomKey5(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY5).getLabelToken(), e);
        }


        try {
            String htmlString = getDomKey6WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6, true);

            setDomKey6(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_KEY6).getLabelToken(), e);
        }


        try {
            String htmlString = getDomFactoryWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY, true);
            if (value == null)
                throw new MandatoryFieldException(((StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY).getLabelToken());

            setDomFactory(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_DOM_FACTORY).getLabelToken(), e);
        }


        try {
            String htmlString = getAdditionalDataComponentWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT, true);

            setAdditionalDataComponent(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_ADDITIONAL_DATA_COMPONENT).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code//GEN-FIRST:_doValidateMain_1

        try {
            String htmlString = getFollowOnFormAlternateWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE, true);

            setFollowOnFormAlternate(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) FormDefinitionMeta.META_FOLLOW_ON_FORM_ALTERNATE).getLabelToken(), e);
        }
        

        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here//GEN-FIRST:_custom


    /** Getter for property file.
     * @return Value of property file.
     */
    public FormFile getFile() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFile();
    }

    /** Setter for property file.
     * @param file New value of property file.
     */
    public void setFile(FormFile file) {
        ((FormDefinitionMaintenanceComponent) getComponent()).setFile(file);
    }

    /** Getter for property fileLayout.
     * @return Value of property fileLayout.
     */
    public FormFile getLayoutFile() {
        return ( (FormDefinitionMaintenanceComponent) getComponent() ).getFileLayout();
    }

    /** Setter for property fileLayout.
     * @param fileLayout New value of property fileLayout.
     */
    public void setLayoutFile(FormFile fileLayout) {
        ((FormDefinitionMaintenanceComponent) getComponent()).setFileLayout(fileLayout);
    }


    // .//GEN-LAST:_custom
}
