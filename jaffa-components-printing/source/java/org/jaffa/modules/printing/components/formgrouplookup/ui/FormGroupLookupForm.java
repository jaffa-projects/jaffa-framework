// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgrouplookup.ui;

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
import org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutDto;
import org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto;
import org.jaffa.modules.printing.domain.FormGroupMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormGroupLookup.
 */
public class FormGroupLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(FormGroupLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return ( (FormGroupLookupComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        ( (FormGroupLookupComponent) getComponent() ).setFormName(formName);
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
        return ( (FormGroupLookupComponent) getComponent() ).getFormNameDd();
    }

    /** Setter for DropDown property formName.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        ( (FormGroupLookupComponent) getComponent() ).setFormNameDd(formNameDd);
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
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (FormGroupLookupComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (FormGroupLookupComponent) getComponent() ).setDescription(description);
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
            descriptionModel.setStringCase( ((StringFieldMetaData) FormGroupMeta.META_DESCRIPTION).getCaseType() );

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
        return ( (FormGroupLookupComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (FormGroupLookupComponent) getComponent() ).setDescriptionDd(descriptionDd);
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
    // .//GEN-BEGIN:formType_1_be
    /** Getter for property formType.
     * @return Value of property formType.
     */
    public String getFormType() {
        return ( (FormGroupLookupComponent) getComponent() ).getFormType();
    }

    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(String formType) {
        ( (FormGroupLookupComponent) getComponent() ).setFormType(formType);
    }

    /** Getter for property formType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formType.
     */
    public EditBoxModel getFormTypeWM() {
        EditBoxModel formTypeModel = (EditBoxModel) getWidgetCache().getModel("formType");
        if (formTypeModel == null) {
            if (getFormType() != null)
                formTypeModel = new EditBoxModel( getFormType() );
            else
                formTypeModel = new EditBoxModel();
            formTypeModel.setStringCase( ((StringFieldMetaData) FormGroupMeta.META_FORM_TYPE).getCaseType() );

            // .//GEN-END:formType_1_be
            // Add custom code //GEN-FIRST:formType_1


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
        EditBoxController.updateModel(value, getFormTypeWM());
    }

    /** Getter for DropDown property formType.
     * @return Value of property formTypeDd.
     */
    public String getFormTypeDd() {
        return ( (FormGroupLookupComponent) getComponent() ).getFormTypeDd();
    }

    /** Setter for DropDown property formType.
     * @param formTypeDd New value of property formTypeDd.
     */
    public void setFormTypeDd(String formTypeDd) {
        ( (FormGroupLookupComponent) getComponent() ).setFormTypeDd(formTypeDd);
    }

    /** Getter for DropDown property formType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property formTypeDd.
     */
    public DropDownModel getFormTypeDdWM() {
        DropDownModel formTypeDdModel = (DropDownModel) getWidgetCache().getModel("formTypeDd");
        if (formTypeDdModel == null) {
            if (getFormTypeDd() != null)
                formTypeDdModel = new DropDownModel( getFormTypeDd() );
            else
                formTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                formTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("formTypeDd", formTypeDdModel);
        }
        return formTypeDdModel;
    }

    /** Setter for DropDown property formType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property formTypeDd.
     */
    public void setFormTypeDdWV(String value) {
        DropDownController.updateModel(value, getFormTypeDdWM());
    }

    // .//GEN-END:formType_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (FormGroupLookupComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (FormGroupLookupComponent) getComponent() ).setCreatedOn(createdOn);
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
        return ( (FormGroupLookupComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (FormGroupLookupComponent) getComponent() ).setCreatedOnDd(createdOnDd);
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
        return ( (FormGroupLookupComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        ( (FormGroupLookupComponent) getComponent() ).setCreatedBy(createdBy);
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
            createdByModel.setStringCase( ((StringFieldMetaData) FormGroupMeta.META_CREATED_BY).getCaseType() );

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
        return ( (FormGroupLookupComponent) getComponent() ).getCreatedByDd();
    }

    /** Setter for DropDown property createdBy.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        ( (FormGroupLookupComponent) getComponent() ).setCreatedByDd(createdByDd);
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
        return ( (FormGroupLookupComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        ( (FormGroupLookupComponent) getComponent() ).setLastChangedOn(lastChangedOn);
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
        return ( (FormGroupLookupComponent) getComponent() ).getLastChangedOnDd();
    }

    /** Setter for DropDown property lastChangedOn.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        ( (FormGroupLookupComponent) getComponent() ).setLastChangedOnDd(lastChangedOnDd);
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
        return ( (FormGroupLookupComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        ( (FormGroupLookupComponent) getComponent() ).setLastChangedBy(lastChangedBy);
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
            lastChangedByModel.setStringCase( ((StringFieldMetaData) FormGroupMeta.META_LAST_CHANGED_BY).getCaseType() );

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
        return ( (FormGroupLookupComponent) getComponent() ).getLastChangedByDd();
    }

    /** Setter for DropDown property lastChangedBy.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        ( (FormGroupLookupComponent) getComponent() ).setLastChangedByDd(lastChangedByDd);
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

        value = getFormNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormName(value);
        setFormNameDd(getFormNameDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

        value = getFormTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormType(value);
        setFormTypeDd(getFormTypeDdWM().getValue());

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
        FormGroupLookupOutDto outputDto = (FormGroupLookupOutDto) ((FormGroupLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormGroupLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("formName", rowDto.getFormName());
                row.addElement("description", rowDto.getDescription());
                row.addElement("formType", rowDto.getFormType());
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
