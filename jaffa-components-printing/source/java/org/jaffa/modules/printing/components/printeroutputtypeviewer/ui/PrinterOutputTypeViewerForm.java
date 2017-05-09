// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypeviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.PrinterOutputTypeViewerOutDto;

import java.util.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.OutputCommandDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the PrinterOutputTypeViewer.
 */
public class PrinterOutputTypeViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_printerOutputTypeViewer";
    private static Logger log = Logger.getLogger(PrinterOutputTypeViewerForm.class);


    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getOutputType() : null;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getDescription() : null;
    }

    /** Getter for property directPrinting.
     * @return Value of property directPrinting.
     */
    public java.lang.Boolean getDirectPrinting() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getDirectPrinting() : null;
    }

    /** Getter for property directPrinting. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property directPrinting.
     */
    public CheckBoxModel getDirectPrintingWM() {
        CheckBoxModel directPrinting = (CheckBoxModel) getWidgetCache().getModel("directPrinting");
        if (directPrinting == null) {
            PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
            directPrinting = new CheckBoxModel(outputDto != null && outputDto.getDirectPrinting() != null ? outputDto.getDirectPrinting() : Boolean.FALSE);
            getWidgetCache().addModel("directPrinting", directPrinting);
        }
        return directPrinting;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getCreatedBy() : null;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedOn() : null;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedBy() : null;
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:RelatedOutputCommand_1_be
    /** Getter for property OutputCommand. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property outputCommand.
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
    
    /** Setter for property outputCommand. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property outputCommand.
     */
    public void setRelatedOutputCommandWV(String value) {
        GridController.updateModel(value, getRelatedOutputCommandWM());
    }
    
    private void populateRelatedOutputCommand(GridModel rows) {
        rows.clearRows();
        PrinterOutputTypeViewerOutDto outputDto = ((PrinterOutputTypeViewerComponent) getComponent()).getPrinterOutputTypeViewerOutDto();
        if (outputDto != null) {
            GridModelRow row = null;
            OutputCommandDto[] outputCommand = outputDto.getOutputCommand();
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
