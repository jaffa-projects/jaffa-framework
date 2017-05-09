// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formusagefinder.ui;

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
import org.jaffa.modules.printing.components.formusagefinder.dto.FormUsageFinderOutDto;
import org.jaffa.modules.printing.components.formusagefinder.dto.FormUsageFinderOutRowDto;
import org.jaffa.modules.printing.domain.FormUsageMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormUsageFinder.
 */
public class FormUsageFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(FormUsageFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:formName_1_be
    /** Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return ( (FormUsageFinderComponent) getComponent() ).getFormName();
    }

    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        ( (FormUsageFinderComponent) getComponent() ).setFormName(formName);
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
            formNameModel.setStringCase( ((StringFieldMetaData) FormUsageMeta.META_FORM_NAME).getCaseType() );

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
        return ( (FormUsageFinderComponent) getComponent() ).getFormNameDd();
    }

    /** Setter for DropDown property formName.
     * @param formNameDd New value of property formNameDd.
     */
    public void setFormNameDd(String formNameDd) {
        ( (FormUsageFinderComponent) getComponent() ).setFormNameDd(formNameDd);
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
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public String getEventName() {
        return ( (FormUsageFinderComponent) getComponent() ).getEventName();
    }

    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(String eventName) {
        ( (FormUsageFinderComponent) getComponent() ).setEventName(eventName);
    }

    /** Getter for property eventName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eventName.
     */
    public EditBoxModel getEventNameWM() {
        EditBoxModel eventNameModel = (EditBoxModel) getWidgetCache().getModel("eventName");
        if (eventNameModel == null) {
            if (getEventName() != null)
                eventNameModel = new EditBoxModel( getEventName() );
            else
                eventNameModel = new EditBoxModel();
            eventNameModel.setStringCase( ((StringFieldMetaData) FormUsageMeta.META_EVENT_NAME).getCaseType() );

            // .//GEN-END:eventName_1_be
            // Add custom code //GEN-FIRST:eventName_1


            // .//GEN-LAST:eventName_1
            // .//GEN-BEGIN:eventName_2_be
            getWidgetCache().addModel("eventName", eventNameModel);
        }
        return eventNameModel;
    }

    /** Setter for property eventName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eventName.
     */
    public void setEventNameWV(String value) {
        EditBoxController.updateModel(value, getEventNameWM());
    }

    /** Getter for DropDown property eventName.
     * @return Value of property eventNameDd.
     */
    public String getEventNameDd() {
        return ( (FormUsageFinderComponent) getComponent() ).getEventNameDd();
    }

    /** Setter for DropDown property eventName.
     * @param eventNameDd New value of property eventNameDd.
     */
    public void setEventNameDd(String eventNameDd) {
        ( (FormUsageFinderComponent) getComponent() ).setEventNameDd(eventNameDd);
    }

    /** Getter for DropDown property eventName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eventNameDd.
     */
    public DropDownModel getEventNameDdWM() {
        DropDownModel eventNameDdModel = (DropDownModel) getWidgetCache().getModel("eventNameDd");
        if (eventNameDdModel == null) {
            if (getEventNameDd() != null)
                eventNameDdModel = new DropDownModel( getEventNameDd() );
            else
                eventNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                eventNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("eventNameDd", eventNameDdModel);
        }
        return eventNameDdModel;
    }

    /** Setter for DropDown property eventName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eventNameDd.
     */
    public void setEventNameDdWV(String value) {
        DropDownController.updateModel(value, getEventNameDdWM());
    }

    // .//GEN-END:eventName_2_be
    // .//GEN-BEGIN:formAlternate_1_be
    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public String getFormAlternate() {
        return ( (FormUsageFinderComponent) getComponent() ).getFormAlternate();
    }

    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(String formAlternate) {
        ( (FormUsageFinderComponent) getComponent() ).setFormAlternate(formAlternate);
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
            formAlternateModel.setStringCase( ((StringFieldMetaData) FormUsageMeta.META_FORM_ALTERNATE).getCaseType() );

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
        return ( (FormUsageFinderComponent) getComponent() ).getFormAlternateDd();
    }

    /** Setter for DropDown property formAlternate.
     * @param formAlternateDd New value of property formAlternateDd.
     */
    public void setFormAlternateDd(String formAlternateDd) {
        ( (FormUsageFinderComponent) getComponent() ).setFormAlternateDd(formAlternateDd);
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
    // .//GEN-BEGIN:copies_1_be
    /** Getter for property copies.
     * @return Value of property copies.
     */
    public String getCopies() {
        return ( (FormUsageFinderComponent) getComponent() ).getCopies();
    }

    /** Setter for property copies.
     * @param copies New value of property copies.
     */
    public void setCopies(String copies) {
        ( (FormUsageFinderComponent) getComponent() ).setCopies(copies);
    }

    /** Getter for property copies. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property copies.
     */
    public EditBoxModel getCopiesWM() {
        EditBoxModel copiesModel = (EditBoxModel) getWidgetCache().getModel("copies");
        if (copiesModel == null) {
            if (getCopies() != null)
                copiesModel = new EditBoxModel( getCopies() );
            else
                copiesModel = new EditBoxModel();

            // .//GEN-END:copies_1_be
            // Add custom code //GEN-FIRST:copies_1


            // .//GEN-LAST:copies_1
            // .//GEN-BEGIN:copies_2_be
            getWidgetCache().addModel("copies", copiesModel);
        }
        return copiesModel;
    }

    /** Setter for property copies. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property copies.
     */
    public void setCopiesWV(String value) {
        EditBoxController.updateModel(value, getCopiesWM());
    }

    /** Getter for DropDown property copies.
     * @return Value of property copiesDd.
     */
    public String getCopiesDd() {
        return ( (FormUsageFinderComponent) getComponent() ).getCopiesDd();
    }

    /** Setter for DropDown property copies.
     * @param copiesDd New value of property copiesDd.
     */
    public void setCopiesDd(String copiesDd) {
        ( (FormUsageFinderComponent) getComponent() ).setCopiesDd(copiesDd);
    }

    /** Getter for DropDown property copies. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property copiesDd.
     */
    public DropDownModel getCopiesDdWM() {
        DropDownModel copiesDdModel = (DropDownModel) getWidgetCache().getModel("copiesDd");
        if (copiesDdModel == null) {
            if (getCopiesDd() != null)
                copiesDdModel = new DropDownModel( getCopiesDd() );
            else
                copiesDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                copiesDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("copiesDd", copiesDdModel);
        }
        return copiesDdModel;
    }

    /** Setter for DropDown property copies. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property copiesDd.
     */
    public void setCopiesDdWV(String value) {
        DropDownController.updateModel(value, getCopiesDdWM());
    }

    // .//GEN-END:copies_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (FormUsageFinderComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (FormUsageFinderComponent) getComponent() ).setCreatedOn(createdOn);
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
        return ( (FormUsageFinderComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (FormUsageFinderComponent) getComponent() ).setCreatedOnDd(createdOnDd);
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
        return ( (FormUsageFinderComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        ( (FormUsageFinderComponent) getComponent() ).setCreatedBy(createdBy);
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
            createdByModel.setStringCase( ((StringFieldMetaData) FormUsageMeta.META_CREATED_BY).getCaseType() );

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
        return ( (FormUsageFinderComponent) getComponent() ).getCreatedByDd();
    }

    /** Setter for DropDown property createdBy.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        ( (FormUsageFinderComponent) getComponent() ).setCreatedByDd(createdByDd);
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
        return ( (FormUsageFinderComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        ( (FormUsageFinderComponent) getComponent() ).setLastChangedOn(lastChangedOn);
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
        return ( (FormUsageFinderComponent) getComponent() ).getLastChangedOnDd();
    }

    /** Setter for DropDown property lastChangedOn.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        ( (FormUsageFinderComponent) getComponent() ).setLastChangedOnDd(lastChangedOnDd);
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
        return ( (FormUsageFinderComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        ( (FormUsageFinderComponent) getComponent() ).setLastChangedBy(lastChangedBy);
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
            lastChangedByModel.setStringCase( ((StringFieldMetaData) FormUsageMeta.META_LAST_CHANGED_BY).getCaseType() );

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
        return ( (FormUsageFinderComponent) getComponent() ).getLastChangedByDd();
    }

    /** Setter for DropDown property lastChangedBy.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        ( (FormUsageFinderComponent) getComponent() ).setLastChangedByDd(lastChangedByDd);
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

        value = getEventNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setEventName(value);
        setEventNameDd(getEventNameDdWM().getValue());

        value = getFormAlternateWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFormAlternate(value);
        setFormAlternateDd(getFormAlternateDdWM().getValue());

        value = getCopiesWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCopies(value);
        setCopiesDd(getCopiesDdWM().getValue());

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
        FormUsageFinderOutDto outputDto = (FormUsageFinderOutDto) ((FormUsageFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormUsageFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("formName", rowDto.getFormName());
                row.addElement("eventName", rowDto.getEventName());
                row.addElement("formAlternate", rowDto.getFormAlternate());
                row.addElement("copies", rowDto.getCopies());
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
