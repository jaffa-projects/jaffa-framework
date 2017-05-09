// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printerdefinitionviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.printerdefinitionviewer.dto.PrinterDefinitionViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the PrinterDefinitionViewer.
 */
public class PrinterDefinitionViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_printerDefinitionViewer";
    private static Logger log = Logger.getLogger(PrinterDefinitionViewerForm.class);


    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public java.lang.String getPrinterId() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getPrinterId() : null;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDescription() : null;
    }

    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public java.lang.String getSiteCode() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getSiteCode() : null;
    }

    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public java.lang.String getLocationCode() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getLocationCode() : null;
    }

    /** Getter for property remote.
     * @return Value of property remote.
     */
    public java.lang.Boolean getRemote() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getRemote() : null;
    }

    /** Getter for property remote. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remote.
     */
    public CheckBoxModel getRemoteWM() {
        CheckBoxModel remote = (CheckBoxModel) getWidgetCache().getModel("remote");
        if (remote == null) {
            PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
            remote = new CheckBoxModel(outputDto != null && outputDto.getRemote() != null ? outputDto.getRemote() : Boolean.FALSE);
            getWidgetCache().addModel("remote", remote);
        }
        return remote;
    }

    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public java.lang.String getRealPrinterName() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getRealPrinterName() : null;
    }

    /** Getter for property printerOption1.
     * @return Value of property printerOption1.
     */
    public java.lang.String getPrinterOption1() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getPrinterOption1() : null;
    }

    /** Getter for property printerOption2.
     * @return Value of property printerOption2.
     */
    public java.lang.String getPrinterOption2() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getPrinterOption2() : null;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getOutputType() : null;
    }

    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public java.lang.String getScaleToPageSize() {
        PrinterDefinitionViewerOutDto outputDto = ((PrinterDefinitionViewerComponent) getComponent()).getPrinterDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getScaleToPageSize() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
