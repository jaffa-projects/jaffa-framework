// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypefinder.ui;

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
import org.jaffa.util.MessageHelper;
import org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderOutDto;
import org.jaffa.modules.printing.components.printeroutputtypefinder.dto.PrinterOutputTypeFinderOutRowDto;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support PrinterOutputTypeFinder.
 */
public class PrinterOutputTypeFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(PrinterOutputTypeFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public String getOutputType() {
        return ( (PrinterOutputTypeFinderComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        ( (PrinterOutputTypeFinderComponent) getComponent() ).setOutputType(outputType);
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
            outputTypeModel.setStringCase( ((StringFieldMetaData) PrinterOutputTypeMeta.META_OUTPUT_TYPE).getCaseType() );

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
        return ( (PrinterOutputTypeFinderComponent) getComponent() ).getOutputTypeDd();
    }

    /** Setter for DropDown property outputType.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        ( (PrinterOutputTypeFinderComponent) getComponent() ).setOutputTypeDd(outputTypeDd);
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
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (PrinterOutputTypeFinderComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (PrinterOutputTypeFinderComponent) getComponent() ).setDescription(description);
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
            descriptionModel.setStringCase( ((StringFieldMetaData) PrinterOutputTypeMeta.META_DESCRIPTION).getCaseType() );

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
        return ( (PrinterOutputTypeFinderComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (PrinterOutputTypeFinderComponent) getComponent() ).setDescriptionDd(descriptionDd);
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
    // .//GEN-BEGIN:directPrinting_1_be
    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public String getDirectPrinting() {
        return ( (PrinterOutputTypeFinderComponent) getComponent() ).getDirectPrinting();
    }

    /** Setter for property directPrinting.
     * @param directPrinting New value of property directPrinting.
     */
    public void setDirectPrinting(String directPrinting) {
        ( (PrinterOutputTypeFinderComponent) getComponent() ).setDirectPrinting(directPrinting);
    }

    /** Getter for property directPrinting. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property directPrinting.
     */
    public DropDownModel getDirectPrintingWM() {
        DropDownModel directPrintingModel = (DropDownModel) getWidgetCache().getModel("directPrinting");
        if (directPrintingModel == null) {
            if (getDirectPrinting() != null)
                directPrintingModel = new DropDownModel( getDirectPrinting() );
            else
                directPrintingModel = new DropDownModel("");
            Map optionsMap = CriteriaDropDownOptions.getBooleanCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                directPrintingModel.addOption( "[label.Jaffa.Printing.PrinterOutputType.DirectPrinting." + MessageHelper.replaceTokens((String) option.getValue()) + "]", (String) option.getKey());
            }
            // .//GEN-END:directPrinting_1_be
            // Add custom code //GEN-FIRST:directPrinting_1


            // .//GEN-LAST:directPrinting_1
            // .//GEN-BEGIN:directPrinting_2_be
            getWidgetCache().addModel("directPrinting", directPrintingModel);
        }
        return directPrintingModel;
    }

    /** Setter for property directPrinting. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property directPrinting.
     */
    public void setDirectPrintingWV(String value) {
        DropDownController.updateModel(value, getDirectPrintingWM());
    }
    // .//GEN-END:directPrinting_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getOutputTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOutputType(value);
        setOutputTypeDd(getOutputTypeDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

        value = getDirectPrintingWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDirectPrinting(value);

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
        PrinterOutputTypeFinderOutDto outputDto = (PrinterOutputTypeFinderOutDto) ((PrinterOutputTypeFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                PrinterOutputTypeFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("outputType", rowDto.getOutputType());
                row.addElement("description", rowDto.getDescription());
                row.addElement("directPrinting", new CheckBoxModel( (rowDto.getDirectPrinting() != null ? rowDto.getDirectPrinting() : Boolean.FALSE) ));
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
