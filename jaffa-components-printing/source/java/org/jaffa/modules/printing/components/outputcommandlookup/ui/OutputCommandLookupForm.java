// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.outputcommandlookup.ui;

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
import org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupOutDto;
import org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupOutRowDto;
import org.jaffa.modules.printing.domain.OutputCommandMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support OutputCommandLookup.
 */
public class OutputCommandLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(OutputCommandLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:outputCommandId_1_be
    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public String getOutputCommandId() {
        return ( (OutputCommandLookupComponent) getComponent() ).getOutputCommandId();
    }

    /** Setter for property outputCommandId.
     * @param outputCommandId New value of property outputCommandId.
     */
    public void setOutputCommandId(String outputCommandId) {
        ( (OutputCommandLookupComponent) getComponent() ).setOutputCommandId(outputCommandId);
    }

    /** Getter for property outputCommandId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputCommandId.
     */
    public EditBoxModel getOutputCommandIdWM() {
        EditBoxModel outputCommandIdModel = (EditBoxModel) getWidgetCache().getModel("outputCommandId");
        if (outputCommandIdModel == null) {
            if (getOutputCommandId() != null)
                outputCommandIdModel = new EditBoxModel( getOutputCommandId() );
            else
                outputCommandIdModel = new EditBoxModel();

            // .//GEN-END:outputCommandId_1_be
            // Add custom code //GEN-FIRST:outputCommandId_1


            // .//GEN-LAST:outputCommandId_1
            // .//GEN-BEGIN:outputCommandId_2_be
            getWidgetCache().addModel("outputCommandId", outputCommandIdModel);
        }
        return outputCommandIdModel;
    }

    /** Setter for property outputCommandId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputCommandId.
     */
    public void setOutputCommandIdWV(String value) {
        EditBoxController.updateModel(value, getOutputCommandIdWM());
    }

    /** Getter for DropDown property outputCommandId.
     * @return Value of property outputCommandIdDd.
     */
    public String getOutputCommandIdDd() {
        return ( (OutputCommandLookupComponent) getComponent() ).getOutputCommandIdDd();
    }

    /** Setter for DropDown property outputCommandId.
     * @param outputCommandIdDd New value of property outputCommandIdDd.
     */
    public void setOutputCommandIdDd(String outputCommandIdDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setOutputCommandIdDd(outputCommandIdDd);
    }

    /** Getter for DropDown property outputCommandId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputCommandIdDd.
     */
    public DropDownModel getOutputCommandIdDdWM() {
        DropDownModel outputCommandIdDdModel = (DropDownModel) getWidgetCache().getModel("outputCommandIdDd");
        if (outputCommandIdDdModel == null) {
            if (getOutputCommandIdDd() != null)
                outputCommandIdDdModel = new DropDownModel( getOutputCommandIdDd() );
            else
                outputCommandIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                outputCommandIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("outputCommandIdDd", outputCommandIdDdModel);
        }
        return outputCommandIdDdModel;
    }

    /** Setter for DropDown property outputCommandId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputCommandIdDd.
     */
    public void setOutputCommandIdDdWV(String value) {
        DropDownController.updateModel(value, getOutputCommandIdDdWM());
    }

    // .//GEN-END:outputCommandId_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public String getOutputType() {
        return ( (OutputCommandLookupComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        ( (OutputCommandLookupComponent) getComponent() ).setOutputType(outputType);
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
            outputTypeModel.setStringCase( ((StringFieldMetaData) OutputCommandMeta.META_OUTPUT_TYPE).getCaseType() );

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
        return ( (OutputCommandLookupComponent) getComponent() ).getOutputTypeDd();
    }

    /** Setter for DropDown property outputType.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setOutputTypeDd(outputTypeDd);
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
    // .//GEN-BEGIN:sequenceNo_1_be
    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public String getSequenceNo() {
        return ( (OutputCommandLookupComponent) getComponent() ).getSequenceNo();
    }

    /** Setter for property sequenceNo.
     * @param sequenceNo New value of property sequenceNo.
     */
    public void setSequenceNo(String sequenceNo) {
        ( (OutputCommandLookupComponent) getComponent() ).setSequenceNo(sequenceNo);
    }

    /** Getter for property sequenceNo. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sequenceNo.
     */
    public EditBoxModel getSequenceNoWM() {
        EditBoxModel sequenceNoModel = (EditBoxModel) getWidgetCache().getModel("sequenceNo");
        if (sequenceNoModel == null) {
            if (getSequenceNo() != null)
                sequenceNoModel = new EditBoxModel( getSequenceNo() );
            else
                sequenceNoModel = new EditBoxModel();

            // .//GEN-END:sequenceNo_1_be
            // Add custom code //GEN-FIRST:sequenceNo_1


            // .//GEN-LAST:sequenceNo_1
            // .//GEN-BEGIN:sequenceNo_2_be
            getWidgetCache().addModel("sequenceNo", sequenceNoModel);
        }
        return sequenceNoModel;
    }

    /** Setter for property sequenceNo. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sequenceNo.
     */
    public void setSequenceNoWV(String value) {
        EditBoxController.updateModel(value, getSequenceNoWM());
    }

    /** Getter for DropDown property sequenceNo.
     * @return Value of property sequenceNoDd.
     */
    public String getSequenceNoDd() {
        return ( (OutputCommandLookupComponent) getComponent() ).getSequenceNoDd();
    }

    /** Setter for DropDown property sequenceNo.
     * @param sequenceNoDd New value of property sequenceNoDd.
     */
    public void setSequenceNoDd(String sequenceNoDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setSequenceNoDd(sequenceNoDd);
    }

    /** Getter for DropDown property sequenceNo. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sequenceNoDd.
     */
    public DropDownModel getSequenceNoDdWM() {
        DropDownModel sequenceNoDdModel = (DropDownModel) getWidgetCache().getModel("sequenceNoDd");
        if (sequenceNoDdModel == null) {
            if (getSequenceNoDd() != null)
                sequenceNoDdModel = new DropDownModel( getSequenceNoDd() );
            else
                sequenceNoDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                sequenceNoDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("sequenceNoDd", sequenceNoDdModel);
        }
        return sequenceNoDdModel;
    }

    /** Setter for DropDown property sequenceNo. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sequenceNoDd.
     */
    public void setSequenceNoDdWV(String value) {
        DropDownController.updateModel(value, getSequenceNoDdWM());
    }

    // .//GEN-END:sequenceNo_2_be
    // .//GEN-BEGIN:osPattern_1_be
    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public String getOsPattern() {
        return ( (OutputCommandLookupComponent) getComponent() ).getOsPattern();
    }

    /** Setter for property osPattern.
     * @param osPattern New value of property osPattern.
     */
    public void setOsPattern(String osPattern) {
        ( (OutputCommandLookupComponent) getComponent() ).setOsPattern(osPattern);
    }

    /** Getter for property osPattern. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property osPattern.
     */
    public EditBoxModel getOsPatternWM() {
        EditBoxModel osPatternModel = (EditBoxModel) getWidgetCache().getModel("osPattern");
        if (osPatternModel == null) {
            if (getOsPattern() != null)
                osPatternModel = new EditBoxModel( getOsPattern() );
            else
                osPatternModel = new EditBoxModel();
            osPatternModel.setStringCase( ((StringFieldMetaData) OutputCommandMeta.META_OS_PATTERN).getCaseType() );

            // .//GEN-END:osPattern_1_be
            // Add custom code //GEN-FIRST:osPattern_1


            // .//GEN-LAST:osPattern_1
            // .//GEN-BEGIN:osPattern_2_be
            getWidgetCache().addModel("osPattern", osPatternModel);
        }
        return osPatternModel;
    }

    /** Setter for property osPattern. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property osPattern.
     */
    public void setOsPatternWV(String value) {
        EditBoxController.updateModel(value, getOsPatternWM());
    }

    /** Getter for DropDown property osPattern.
     * @return Value of property osPatternDd.
     */
    public String getOsPatternDd() {
        return ( (OutputCommandLookupComponent) getComponent() ).getOsPatternDd();
    }

    /** Setter for DropDown property osPattern.
     * @param osPatternDd New value of property osPatternDd.
     */
    public void setOsPatternDd(String osPatternDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setOsPatternDd(osPatternDd);
    }

    /** Getter for DropDown property osPattern. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property osPatternDd.
     */
    public DropDownModel getOsPatternDdWM() {
        DropDownModel osPatternDdModel = (DropDownModel) getWidgetCache().getModel("osPatternDd");
        if (osPatternDdModel == null) {
            if (getOsPatternDd() != null)
                osPatternDdModel = new DropDownModel( getOsPatternDd() );
            else
                osPatternDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                osPatternDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("osPatternDd", osPatternDdModel);
        }
        return osPatternDdModel;
    }

    /** Setter for DropDown property osPattern. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property osPatternDd.
     */
    public void setOsPatternDdWV(String value) {
        DropDownController.updateModel(value, getOsPatternDdWM());
    }

    // .//GEN-END:osPattern_2_be
    // .//GEN-BEGIN:commandLine_1_be
    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public String getCommandLine() {
        return ( (OutputCommandLookupComponent) getComponent() ).getCommandLine();
    }

    /** Setter for property commandLine.
     * @param commandLine New value of property commandLine.
     */
    public void setCommandLine(String commandLine) {
        ( (OutputCommandLookupComponent) getComponent() ).setCommandLine(commandLine);
    }

    /** Getter for property commandLine. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property commandLine.
     */
    public EditBoxModel getCommandLineWM() {
        EditBoxModel commandLineModel = (EditBoxModel) getWidgetCache().getModel("commandLine");
        if (commandLineModel == null) {
            if (getCommandLine() != null)
                commandLineModel = new EditBoxModel( getCommandLine() );
            else
                commandLineModel = new EditBoxModel();
            commandLineModel.setStringCase( ((StringFieldMetaData) OutputCommandMeta.META_COMMAND_LINE).getCaseType() );

            // .//GEN-END:commandLine_1_be
            // Add custom code //GEN-FIRST:commandLine_1


            // .//GEN-LAST:commandLine_1
            // .//GEN-BEGIN:commandLine_2_be
            getWidgetCache().addModel("commandLine", commandLineModel);
        }
        return commandLineModel;
    }

    /** Setter for property commandLine. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property commandLine.
     */
    public void setCommandLineWV(String value) {
        EditBoxController.updateModel(value, getCommandLineWM());
    }

    /** Getter for DropDown property commandLine.
     * @return Value of property commandLineDd.
     */
    public String getCommandLineDd() {
        return ( (OutputCommandLookupComponent) getComponent() ).getCommandLineDd();
    }

    /** Setter for DropDown property commandLine.
     * @param commandLineDd New value of property commandLineDd.
     */
    public void setCommandLineDd(String commandLineDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setCommandLineDd(commandLineDd);
    }

    /** Getter for DropDown property commandLine. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property commandLineDd.
     */
    public DropDownModel getCommandLineDdWM() {
        DropDownModel commandLineDdModel = (DropDownModel) getWidgetCache().getModel("commandLineDd");
        if (commandLineDdModel == null) {
            if (getCommandLineDd() != null)
                commandLineDdModel = new DropDownModel( getCommandLineDd() );
            else
                commandLineDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                commandLineDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("commandLineDd", commandLineDdModel);
        }
        return commandLineDdModel;
    }

    /** Setter for DropDown property commandLine. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property commandLineDd.
     */
    public void setCommandLineDdWV(String value) {
        DropDownController.updateModel(value, getCommandLineDdWM());
    }

    // .//GEN-END:commandLine_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (OutputCommandLookupComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (OutputCommandLookupComponent) getComponent() ).setCreatedOn(createdOn);
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
        return ( (OutputCommandLookupComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setCreatedOnDd(createdOnDd);
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
        return ( (OutputCommandLookupComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        ( (OutputCommandLookupComponent) getComponent() ).setCreatedBy(createdBy);
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
            createdByModel.setStringCase( ((StringFieldMetaData) OutputCommandMeta.META_CREATED_BY).getCaseType() );

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
        return ( (OutputCommandLookupComponent) getComponent() ).getCreatedByDd();
    }

    /** Setter for DropDown property createdBy.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setCreatedByDd(createdByDd);
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
        return ( (OutputCommandLookupComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        ( (OutputCommandLookupComponent) getComponent() ).setLastChangedOn(lastChangedOn);
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
        return ( (OutputCommandLookupComponent) getComponent() ).getLastChangedOnDd();
    }

    /** Setter for DropDown property lastChangedOn.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setLastChangedOnDd(lastChangedOnDd);
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
        return ( (OutputCommandLookupComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        ( (OutputCommandLookupComponent) getComponent() ).setLastChangedBy(lastChangedBy);
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
            lastChangedByModel.setStringCase( ((StringFieldMetaData) OutputCommandMeta.META_LAST_CHANGED_BY).getCaseType() );

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
        return ( (OutputCommandLookupComponent) getComponent() ).getLastChangedByDd();
    }

    /** Setter for DropDown property lastChangedBy.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        ( (OutputCommandLookupComponent) getComponent() ).setLastChangedByDd(lastChangedByDd);
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

        value = getOutputCommandIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOutputCommandId(value);
        setOutputCommandIdDd(getOutputCommandIdDdWM().getValue());

        value = getOutputTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOutputType(value);
        setOutputTypeDd(getOutputTypeDdWM().getValue());

        value = getSequenceNoWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSequenceNo(value);
        setSequenceNoDd(getSequenceNoDdWM().getValue());

        value = getOsPatternWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOsPattern(value);
        setOsPatternDd(getOsPatternDdWM().getValue());

        value = getCommandLineWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCommandLine(value);
        setCommandLineDd(getCommandLineDdWM().getValue());

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
        OutputCommandLookupOutDto outputDto = (OutputCommandLookupOutDto) ((OutputCommandLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                OutputCommandLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("outputCommandId", rowDto.getOutputCommandId());
                row.addElement("outputType", rowDto.getOutputType());
                row.addElement("sequenceNo", rowDto.getSequenceNo());
                row.addElement("osPattern", rowDto.getOsPattern());
                row.addElement("commandLine", rowDto.getCommandLine());
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
