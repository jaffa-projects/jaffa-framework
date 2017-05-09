// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplatefinder.ui;

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
import org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderOutDto;
import org.jaffa.modules.printing.components.formtemplatefinder.dto.FormTemplateFinderOutRowDto;
import org.jaffa.modules.printing.domain.FormTemplateMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormTemplateFinder.
 */
public class FormTemplateFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(FormTemplateFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formId_1_be
    /** Getter for property formId.
     * @return Value of property formId.
     */
    public String getFormId() {
        return ( (FormTemplateFinderComponent) getComponent() ).getFormId();
    }

    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(String formId) {
        ( (FormTemplateFinderComponent) getComponent() ).setFormId(formId);
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
        return ( (FormTemplateFinderComponent) getComponent() ).getFormIdDd();
    }

    /** Setter for DropDown property formId.
     * @param formIdDd New value of property formIdDd.
     */
    public void setFormIdDd(String formIdDd) {
        ( (FormTemplateFinderComponent) getComponent() ).setFormIdDd(formIdDd);
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
    // .//GEN-BEGIN:templateData_1_be
    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public String getTemplateData() {
        return ( (FormTemplateFinderComponent) getComponent() ).getTemplateData();
    }

    /** Setter for property templateData.
     * @param templateData New value of property templateData.
     */
    public void setTemplateData(String templateData) {
        ( (FormTemplateFinderComponent) getComponent() ).setTemplateData(templateData);
    }

    /** Getter for property templateData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property templateData.
     */
    public EditBoxModel getTemplateDataWM() {
        EditBoxModel templateDataModel = (EditBoxModel) getWidgetCache().getModel("templateData");
        if (templateDataModel == null) {
            if (getTemplateData() != null)
                templateDataModel = new EditBoxModel( getTemplateData() );
            else
                templateDataModel = new EditBoxModel();

            // .//GEN-END:templateData_1_be
            // Add custom code //GEN-FIRST:templateData_1


            // .//GEN-LAST:templateData_1
            // .//GEN-BEGIN:templateData_2_be
            getWidgetCache().addModel("templateData", templateDataModel);
        }
        return templateDataModel;
    }

    /** Setter for property templateData. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property templateData.
     */
    public void setTemplateDataWV(String value) {
        EditBoxController.updateModel(value, getTemplateDataWM());
    }

    /** Getter for DropDown property templateData.
     * @return Value of property templateDataDd.
     */
    public String getTemplateDataDd() {
        return ( (FormTemplateFinderComponent) getComponent() ).getTemplateDataDd();
    }

    /** Setter for DropDown property templateData.
     * @param templateDataDd New value of property templateDataDd.
     */
    public void setTemplateDataDd(String templateDataDd) {
        ( (FormTemplateFinderComponent) getComponent() ).setTemplateDataDd(templateDataDd);
    }

    /** Getter for DropDown property templateData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property templateDataDd.
     */
    public DropDownModel getTemplateDataDdWM() {
        DropDownModel templateDataDdModel = (DropDownModel) getWidgetCache().getModel("templateDataDd");
        if (templateDataDdModel == null) {
            if (getTemplateDataDd() != null)
                templateDataDdModel = new DropDownModel( getTemplateDataDd() );
            else
                templateDataDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                templateDataDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("templateDataDd", templateDataDdModel);
        }
        return templateDataDdModel;
    }

    /** Setter for DropDown property templateData. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property templateDataDd.
     */
    public void setTemplateDataDdWV(String value) {
        DropDownController.updateModel(value, getTemplateDataDdWM());
    }

    // .//GEN-END:templateData_2_be
    // .//GEN-BEGIN:layoutData_1_be
    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public String getLayoutData() {
        return ( (FormTemplateFinderComponent) getComponent() ).getLayoutData();
    }

    /** Setter for property layoutData.
     * @param layoutData New value of property layoutData.
     */
    public void setLayoutData(String layoutData) {
        ( (FormTemplateFinderComponent) getComponent() ).setLayoutData(layoutData);
    }

    /** Getter for property layoutData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property layoutData.
     */
    public EditBoxModel getLayoutDataWM() {
        EditBoxModel layoutDataModel = (EditBoxModel) getWidgetCache().getModel("layoutData");
        if (layoutDataModel == null) {
            if (getLayoutData() != null)
                layoutDataModel = new EditBoxModel( getLayoutData() );
            else
                layoutDataModel = new EditBoxModel();

            // .//GEN-END:layoutData_1_be
            // Add custom code //GEN-FIRST:layoutData_1


            // .//GEN-LAST:layoutData_1
            // .//GEN-BEGIN:layoutData_2_be
            getWidgetCache().addModel("layoutData", layoutDataModel);
        }
        return layoutDataModel;
    }

    /** Setter for property layoutData. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property layoutData.
     */
    public void setLayoutDataWV(String value) {
        EditBoxController.updateModel(value, getLayoutDataWM());
    }

    /** Getter for DropDown property layoutData.
     * @return Value of property layoutDataDd.
     */
    public String getLayoutDataDd() {
        return ( (FormTemplateFinderComponent) getComponent() ).getLayoutDataDd();
    }

    /** Setter for DropDown property layoutData.
     * @param layoutDataDd New value of property layoutDataDd.
     */
    public void setLayoutDataDd(String layoutDataDd) {
        ( (FormTemplateFinderComponent) getComponent() ).setLayoutDataDd(layoutDataDd);
    }

    /** Getter for DropDown property layoutData. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property layoutDataDd.
     */
    public DropDownModel getLayoutDataDdWM() {
        DropDownModel layoutDataDdModel = (DropDownModel) getWidgetCache().getModel("layoutDataDd");
        if (layoutDataDdModel == null) {
            if (getLayoutDataDd() != null)
                layoutDataDdModel = new DropDownModel( getLayoutDataDd() );
            else
                layoutDataDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                layoutDataDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("layoutDataDd", layoutDataDdModel);
        }
        return layoutDataDdModel;
    }

    /** Setter for DropDown property layoutData. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property layoutDataDd.
     */
    public void setLayoutDataDdWV(String value) {
        DropDownController.updateModel(value, getLayoutDataDdWM());
    }

    // .//GEN-END:layoutData_2_be
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

        value = getTemplateDataWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setTemplateData(value);
        setTemplateDataDd(getTemplateDataDdWM().getValue());

        value = getLayoutDataWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLayoutData(value);
        setLayoutDataDd(getLayoutDataDdWM().getValue());

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
        FormTemplateFinderOutDto outputDto = (FormTemplateFinderOutDto) ((FormTemplateFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormTemplateFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("formId", rowDto.getFormId());
                row.addElement("templateData", rowDto.getTemplateData());
                row.addElement("layoutData", rowDto.getLayoutData());
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
