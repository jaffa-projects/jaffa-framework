// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui;

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

import org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.*;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;

import org.jaffa.modules.printing.domain.OutputCommandMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the PrinterOutputTypeMaintenance.
 */
public class PrinterOutputTypeMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(PrinterOutputTypeMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public EditBoxModel getOutputTypeWM() {
        EditBoxModel w_outputType = (EditBoxModel) getWidgetCache().getModel("outputType");
        if (w_outputType == null) {
            if (getOutputType() != null)
                w_outputType = new EditBoxModel(getOutputType(), (StringFieldMetaData) PrinterOutputTypeMeta.META_OUTPUT_TYPE);
            else
                w_outputType = new EditBoxModel((StringFieldMetaData) PrinterOutputTypeMeta.META_OUTPUT_TYPE);
            // .//GEN-END:outputType_1_be
            // Add custom code //GEN-FIRST:outputType_1


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
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) PrinterOutputTypeMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) PrinterOutputTypeMeta.META_DESCRIPTION);
            // .//GEN-END:description_1_be
            // Add custom code //GEN-FIRST:description_1


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
    // .//GEN-BEGIN:directPrinting_1_be
    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public java.lang.Boolean getDirectPrinting() {
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getDirectPrinting();
    }

    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(java.lang.Boolean directPrinting) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setDirectPrinting(directPrinting);
    }

    /** Getter for property directPrinting. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property directPrinting.
     */
    public CheckBoxModel getDirectPrintingWM() {
        CheckBoxModel w_directPrinting = (CheckBoxModel) getWidgetCache().getModel("directPrinting");
        if (w_directPrinting == null) {
            if (getDirectPrinting() != null)
                w_directPrinting = new CheckBoxModel( getDirectPrinting() );
            else
                w_directPrinting = new CheckBoxModel(false);
            // .//GEN-END:directPrinting_1_be
            // Add custom code //GEN-FIRST:directPrinting_1


            // .//GEN-LAST:directPrinting_1
            // .//GEN-BEGIN:directPrinting_2_be
            getWidgetCache().addModel("directPrinting", w_directPrinting);
        }
        return w_directPrinting;
    }

    /** Setter for property directPrinting. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property directPrinting.
     */
    public void setDirectPrintingWV(String value) {
        CheckBoxController.updateModel(value, getDirectPrintingWM());
    }
    // .//GEN-END:directPrinting_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) PrinterOutputTypeMeta.META_CREATED_ON);
            // .//GEN-END:createdOn_1_be
            // Add custom code //GEN-FIRST:createdOn_1


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
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel w_createdBy = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            if (getCreatedBy() != null)
                w_createdBy = new EditBoxModel(getCreatedBy(), (StringFieldMetaData) PrinterOutputTypeMeta.META_CREATED_BY);
            else
                w_createdBy = new EditBoxModel((StringFieldMetaData) PrinterOutputTypeMeta.META_CREATED_BY);
            // .//GEN-END:createdBy_1_be
            // Add custom code //GEN-FIRST:createdBy_1


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
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public DateTimeModel getLastChangedOnWM() {
        DateTimeModel w_lastChangedOn = (DateTimeModel) getWidgetCache().getModel("lastChangedOn");
        if (w_lastChangedOn == null) {
            w_lastChangedOn = new DateTimeModel(getLastChangedOn(), (DateTimeFieldMetaData) PrinterOutputTypeMeta.META_LAST_CHANGED_ON);
            // .//GEN-END:lastChangedOn_1_be
            // Add custom code //GEN-FIRST:lastChangedOn_1


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
        return ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        ( (PrinterOutputTypeMaintenanceComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel w_lastChangedBy = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (w_lastChangedBy == null) {
            if (getLastChangedBy() != null)
                w_lastChangedBy = new EditBoxModel(getLastChangedBy(), (StringFieldMetaData) PrinterOutputTypeMeta.META_LAST_CHANGED_BY);
            else
                w_lastChangedBy = new EditBoxModel((StringFieldMetaData) PrinterOutputTypeMeta.META_LAST_CHANGED_BY);
            // .//GEN-END:lastChangedBy_1_be
            // Add custom code //GEN-FIRST:lastChangedBy_1


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
            String htmlString = getOutputTypeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterOutputTypeMeta.META_OUTPUT_TYPE, true);

            setOutputType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterOutputTypeMeta.META_OUTPUT_TYPE).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterOutputTypeMeta.META_DESCRIPTION, true);

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterOutputTypeMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        try {
            java.lang.Boolean value = new Boolean(getDirectPrintingWM().getState());
            value = FieldValidator.validate(value, (BooleanFieldMetaData) PrinterOutputTypeMeta.META_DIRECT_PRINTING, true);

            setDirectPrinting(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((BooleanFieldMetaData) PrinterOutputTypeMeta.META_DIRECT_PRINTING).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // .//GEN-BEGIN:RelatedOutputCommand_1_be
    /** Getter for property relatedOutputCommand. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property relatedOutputCommand.
     */
    public GridModel getRelatedOutputCommandWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedOutputCommand");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedOutputCommand(rows);
            getWidgetCache().addModel("relatedOutputCommand", rows);
        }
        return rows;
    }
    
    /** Setter for property relatedOutputCommand. This is invoked by the servlet, when a post is done on the screen.
     * It sets the selected rows on the model.
     * @param value New value of property relatedOutputCommand.
     */
    public void setRelatedOutputCommandWV(String value) {
        GridController.updateModel(value, getRelatedOutputCommandWM());
    }
    
    private void populateRelatedOutputCommand(GridModel rows) {
        rows.clearRows();
        OutputCommandDto[] outputCommand = ((PrinterOutputTypeMaintenanceComponent) getComponent()).getRelatedObjectOutputCommandDto();
        if (outputCommand != null) {
            GridModelRow row = null;
            for (int i = 0; i < outputCommand.length; i++) {
                OutputCommandDto rowDto = outputCommand[i];
                row = rows.newRow();
                row.addElement("outputCommandId", rowDto.getOutputCommandId());
                row.addElement("outputType", rowDto.getOutputType());
                row.addElement("sequenceNo", rowDto.getSequenceNo());
                row.addElement("osPattern", rowDto.getOsPattern());
                row.addElement("commandLine", rowDto.getCommandLine());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("createdBy", rowDto.getCreatedBy());
                row.addElement("lastChangedOn", rowDto.getLastChangedOn());
                row.addElement("lastChangedBy", rowDto.getLastChangedBy());
                // .//GEN-END:RelatedOutputCommand_1_be
                // Add custom code for the row //GEN-FIRST:RelatedOutputCommand_1


                // .//GEN-LAST:RelatedOutputCommand_1
                // .//GEN-BEGIN:RelatedOutputCommand_2_be
            }
        }
    }
    // .//GEN-END:RelatedOutputCommand_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
