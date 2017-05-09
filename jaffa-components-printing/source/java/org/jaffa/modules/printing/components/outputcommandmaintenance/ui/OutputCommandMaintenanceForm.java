// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.outputcommandmaintenance.ui;

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

import org.jaffa.modules.printing.components.outputcommandmaintenance.dto.*;
import org.jaffa.modules.printing.domain.OutputCommandMeta;

import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the OutputCommandMaintenance.
 */
public class OutputCommandMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(OutputCommandMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:outputCommandId_1_be
    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public java.lang.Long getOutputCommandId() {
        return ( (OutputCommandMaintenanceComponent) getComponent() ).getOutputCommandId();
    }

    /** Setter for property outputCommandId.
     * @param outputCommandId New value of property outputCommandId.
     */
    public void setOutputCommandId(java.lang.Long outputCommandId) {
        ( (OutputCommandMaintenanceComponent) getComponent() ).setOutputCommandId(outputCommandId);
    }

    /** Getter for property outputCommandId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputCommandId.
     */
    public EditBoxModel getOutputCommandIdWM() {
        EditBoxModel w_outputCommandId = (EditBoxModel) getWidgetCache().getModel("outputCommandId");
        if (w_outputCommandId == null) {
            if (getOutputCommandId() != null)
                w_outputCommandId = new EditBoxModel(getOutputCommandId(), (IntegerFieldMetaData) OutputCommandMeta.META_OUTPUT_COMMAND_ID);
            else
                w_outputCommandId = new EditBoxModel((IntegerFieldMetaData) OutputCommandMeta.META_OUTPUT_COMMAND_ID);
            // .//GEN-END:outputCommandId_1_be
            // Add custom code//GEN-FIRST:outputCommandId_1


            // .//GEN-LAST:outputCommandId_1
            // .//GEN-BEGIN:outputCommandId_2_be
            getWidgetCache().addModel("outputCommandId", w_outputCommandId);
        }
        return w_outputCommandId;
    }

    /** Setter for property outputCommandId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property outputCommandId.
     */
    public void setOutputCommandIdWV(String value) {
        EditBoxController.updateModel(value, getOutputCommandIdWM());
    }
    // .//GEN-END:outputCommandId_2_be
    // .//GEN-BEGIN:sequenceNo_1_be
    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public java.lang.Long getSequenceNo() {
        return ( (OutputCommandMaintenanceComponent) getComponent() ).getSequenceNo();
    }

    /** Setter for property sequenceNo.
     * @param sequenceNo New value of property sequenceNo.
     */
    public void setSequenceNo(java.lang.Long sequenceNo) {
        ( (OutputCommandMaintenanceComponent) getComponent() ).setSequenceNo(sequenceNo);
    }

    /** Getter for property sequenceNo. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sequenceNo.
     */
    public EditBoxModel getSequenceNoWM() {
        EditBoxModel w_sequenceNo = (EditBoxModel) getWidgetCache().getModel("sequenceNo");
        if (w_sequenceNo == null) {
            if (getSequenceNo() != null)
                w_sequenceNo = new EditBoxModel(getSequenceNo(), (IntegerFieldMetaData) OutputCommandMeta.META_SEQUENCE_NO);
            else
                w_sequenceNo = new EditBoxModel((IntegerFieldMetaData) OutputCommandMeta.META_SEQUENCE_NO);
            // .//GEN-END:sequenceNo_1_be
            // Add custom code//GEN-FIRST:sequenceNo_1

            // .//GEN-LAST:sequenceNo_1
            // .//GEN-BEGIN:sequenceNo_2_be
            getWidgetCache().addModel("sequenceNo", w_sequenceNo);
        }
        return w_sequenceNo;
    }

    /** Setter for property sequenceNo. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property sequenceNo.
     */
    public void setSequenceNoWV(String value) {
        EditBoxController.updateModel(value, getSequenceNoWM());
    }
    // .//GEN-END:sequenceNo_2_be
    // .//GEN-BEGIN:osPattern_1_be
    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public java.lang.String getOsPattern() {
        return ( (OutputCommandMaintenanceComponent) getComponent() ).getOsPattern();
    }

    /** Setter for property osPattern.
     * @param osPattern New value of property osPattern.
     */
    public void setOsPattern(java.lang.String osPattern) {
        ( (OutputCommandMaintenanceComponent) getComponent() ).setOsPattern(osPattern);
    }

    /** Getter for property osPattern. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property osPattern.
     */
    public EditBoxModel getOsPatternWM() {
        EditBoxModel w_osPattern = (EditBoxModel) getWidgetCache().getModel("osPattern");
        if (w_osPattern == null) {
            if (getOsPattern() != null)
                w_osPattern = new EditBoxModel(getOsPattern(), (StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN);
            else
                w_osPattern = new EditBoxModel((StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN);
            // .//GEN-END:osPattern_1_be
            // Add custom code//GEN-FIRST:osPattern_1


            // .//GEN-LAST:osPattern_1
            // .//GEN-BEGIN:osPattern_2_be
            getWidgetCache().addModel("osPattern", w_osPattern);
        }
        return w_osPattern;
    }

    /** Setter for property osPattern. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property osPattern.
     */
    public void setOsPatternWV(String value) {
        EditBoxController.updateModel(value, getOsPatternWM());
    }
    // .//GEN-END:osPattern_2_be
    // .//GEN-BEGIN:commandLine_1_be
    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public java.lang.String getCommandLine() {
        return ( (OutputCommandMaintenanceComponent) getComponent() ).getCommandLine();
    }

    /** Setter for property commandLine.
     * @param commandLine New value of property commandLine.
     */
    public void setCommandLine(java.lang.String commandLine) {
        ( (OutputCommandMaintenanceComponent) getComponent() ).setCommandLine(commandLine);
    }

    /** Getter for property commandLine. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property commandLine.
     */
    public EditBoxModel getCommandLineWM() {
        EditBoxModel w_commandLine = (EditBoxModel) getWidgetCache().getModel("commandLine");
        if (w_commandLine == null) {
            if (getCommandLine() != null)
                w_commandLine = new EditBoxModel(getCommandLine(), (StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE);
            else
                w_commandLine = new EditBoxModel((StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE);
            // .//GEN-END:commandLine_1_be
            // Add custom code//GEN-FIRST:commandLine_1


            // .//GEN-LAST:commandLine_1
            // .//GEN-BEGIN:commandLine_2_be
            getWidgetCache().addModel("commandLine", w_commandLine);
        }
        return w_commandLine;
    }

    /** Setter for property commandLine. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property commandLine.
     */
    public void setCommandLineWV(String value) {
        EditBoxController.updateModel(value, getCommandLineWM());
    }
    // .//GEN-END:commandLine_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return ( (OutputCommandMaintenanceComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        ( (OutputCommandMaintenanceComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public EditBoxModel getOutputTypeWM() {
        EditBoxModel w_outputType = (EditBoxModel) getWidgetCache().getModel("outputType");
        if (w_outputType == null) {
            if (getOutputType() != null)
                w_outputType = new EditBoxModel(getOutputType(), (StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE);
            else
                w_outputType = new EditBoxModel((StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE);
            w_outputType.setMandatory(false);
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
            String htmlString = getOutputTypeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE, false);

            setOutputType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE).getLabelToken(), e);
        }
        try {
            String htmlString = getSequenceNoWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.Long value = (java.lang.Long) FieldValidator.validateData(htmlString, (IntegerFieldMetaData) OutputCommandMeta.META_SEQUENCE_NO, true);

            setSequenceNo(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((IntegerFieldMetaData) OutputCommandMeta.META_SEQUENCE_NO).getLabelToken(), e);
        }


        try {
            String htmlString = getOsPatternWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN, true);

            setOsPattern(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN).getLabelToken(), e);
        }


        try {
            String htmlString = getCommandLineWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE, true);

            setCommandLine(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code//GEN-FIRST:_doValidateMain_1
        
        
        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here//GEN-FIRST:_custom
   
    // .//GEN-LAST:_custom
}
