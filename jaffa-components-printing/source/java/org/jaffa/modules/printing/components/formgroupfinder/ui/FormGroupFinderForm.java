// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.modules.printing.services.FormPrintFactory;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.modules.printing.components.formgroupfinder.dto.FormGroupFinderOutDto;
import org.jaffa.modules.printing.components.formgroupfinder.dto.FormGroupFinderOutRowDto;
import org.jaffa.modules.printing.domain.FormGroupMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormGroupFinder.
 */
public class FormGroupFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(FormGroupFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return ( (FormGroupFinderComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        ( (FormGroupFinderComponent) getComponent() ).setFormName(formName);
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
            formNameModel.setStringCase( ((StringFieldMetaData) FormGroupMeta.META_FORM_NAME).getCaseType() );

            // .//GEN-END:formName_1_be
            // Add custom code//GEN-FIRST:formName_1


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
        return ( (FormGroupFinderComponent) getComponent() ).getFormNameDd();
    }

    /** Setter for DropDown property formName.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        ( (FormGroupFinderComponent) getComponent() ).setFormNameDd(formNameDd);
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
    // .//GEN-BEGIN:formType_1_be
    /** Getter for property formType.
     * @return Value of property formType.
     */
    public String getFormType() {
        return ( (FormGroupFinderComponent) getComponent() ).getFormType();
    }

    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(String formType) {
        ( (FormGroupFinderComponent) getComponent() ).setFormType(formType);
    }

    /** Getter for property formType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formType.
     */
    public GridModel getFormTypeWM() {
        GridModel formTypeModel = (GridModel) getWidgetCache().getModel("formType");
        if (formTypeModel == null) {
            formTypeModel = new GridModel();
            Collection existingValues = null;
            Object code = null;
            GridModelRow row = null;
            if (getFormType() != null)
                existingValues = StringHelper.convertToList(getFormType());
            row = formTypeModel.newRow();
            code = "PDF";
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Printing.FormGroup.FormType.PDF]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));
            row = formTypeModel.newRow();
            code = "LABEL";
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Printing.FormGroup.FormType.LABEL]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));

            // .//GEN-END:formType_1_be
            // Add custom code//GEN-FIRST:formType_1
            // added to use the value of these check boxes from a common interface(static values)
            row = formTypeModel.newRow();
            code = FormPrintFactory.ENGINE_TYPE_VELOCITY;
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Printing.FormGroup.FormType."+ FormPrintFactory.ENGINE_TYPE_VELOCITY+"]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));

            formTypeModel.getRow(0).addElement("code", FormPrintFactory.ENGINE_TYPE_ITEXT);
            formTypeModel.getRow(0).addElement("description", "[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_ITEXT+"]");

            formTypeModel.getRow(1).addElement("code", org.jaffa.modules.printing.services.FormPrintFactory.ENGINE_TYPE_FOP);
            formTypeModel.getRow(1).addElement("description", "[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_FOP+"]");
            if (existingValues != null && existingValues.contains(org.jaffa.modules.printing.services.FormPrintFactory.ENGINE_TYPE_FOP))
                formTypeModel.getRow(1).addElement("checked", new CheckBoxModel(true));
            else
                formTypeModel.getRow(1).addElement("checked", new CheckBoxModel(false));

            formTypeModel.getRow(2).addElement("code", org.jaffa.modules.printing.services.FormPrintFactory.ENGINE_TYPE_VELOCITY);
            formTypeModel.getRow(2).addElement("description", "[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_VELOCITY+"]");

            // .//GEN-LAST:formType_1
            // .//GEN-BEGIN:formType_2_be
            getWidgetCache().addModel("formType", formTypeModel);
        }
        return formTypeModel;
    }

    /** Setter for property formType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formType.
     */
    public void setFormTypeWV(String value) {
        GridController.updateModel(value, getFormTypeWM());
    }
    // .//GEN-END:formType_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getFormNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormName(value);
        setFormNameDd(getFormNameDdWM().getValue());

        buf = new StringBuffer();
        if (getFormTypeWM().getRows() != null) {
            for (Iterator i = getFormTypeWM().getRows().iterator(); i.hasNext(); ) {
                GridModelRow row = (GridModelRow) i.next();
                boolean checked = ((CheckBoxModel) row.getElement("checked")).getState();
                if (checked) {
                    if (buf.length() > 0)
                        buf.append(',');
                    buf.append(row.getElement("code"));
                }
            }
        }
        if (buf.length() > 0)
            value = buf.toString();
        else
            value = null;
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormType(value);

        // .//GEN-END:_doValidate_1_be
        // Add custom code//GEN-FIRST:_doValidate_1



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
        FormGroupFinderOutDto outputDto = (FormGroupFinderOutDto) ((FormGroupFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormGroupFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("formName", rowDto.getFormName());
                row.addElement("description", rowDto.getDescription());
                row.addElement("formType", rowDto.getFormType());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row//GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
