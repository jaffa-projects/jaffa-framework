// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formeventlookup.ui;

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
import org.jaffa.modules.printing.components.formeventlookup.dto.FormEventLookupOutDto;
import org.jaffa.modules.printing.components.formeventlookup.dto.FormEventLookupOutRowDto;
import org.jaffa.modules.printing.domain.FormEventMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support FormEventLookup.
 */
public class FormEventLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(FormEventLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public String getEventName() {
        return ( (FormEventLookupComponent) getComponent() ).getEventName();
    }

    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(String eventName) {
        ( (FormEventLookupComponent) getComponent() ).setEventName(eventName);
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
            eventNameModel.setStringCase( ((StringFieldMetaData) FormEventMeta.META_EVENT_NAME).getCaseType() );

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
        return ( (FormEventLookupComponent) getComponent() ).getEventNameDd();
    }

    /** Setter for DropDown property eventName.
     * @param eventNameDd New value of property eventNameDd.
     */
    public void setEventNameDd(String eventNameDd) {
        ( (FormEventLookupComponent) getComponent() ).setEventNameDd(eventNameDd);
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
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (FormEventLookupComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (FormEventLookupComponent) getComponent() ).setDescription(description);
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
            descriptionModel.setStringCase( ((StringFieldMetaData) FormEventMeta.META_DESCRIPTION).getCaseType() );

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
        return ( (FormEventLookupComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (FormEventLookupComponent) getComponent() ).setDescriptionDd(descriptionDd);
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
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getEventNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setEventName(value);
        setEventNameDd(getEventNameDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

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
        FormEventLookupOutDto outputDto = (FormEventLookupOutDto) ((FormEventLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                FormEventLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("eventName", rowDto.getEventName());
                row.addElement("description", rowDto.getDescription());
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
