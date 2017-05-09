// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.outputcommandviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.outputcommandviewer.dto.OutputCommandViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the OutputCommandViewer.
 */
public class OutputCommandViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_outputCommandViewer";
    private static Logger log = Logger.getLogger(OutputCommandViewerForm.class);


    /** Getter for property outputCommandId.
     * @return Value of property outputCommandId.
     */
    public java.lang.Long getOutputCommandId() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getOutputCommandId() : null;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getOutputType() : null;
    }

    /** Getter for property sequenceNo.
     * @return Value of property sequenceNo.
     */
    public java.lang.Long getSequenceNo() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getSequenceNo() : null;
    }

    /** Getter for property osPattern.
     * @return Value of property osPattern.
     */
    public java.lang.String getOsPattern() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getOsPattern() : null;
    }

    /** Getter for property commandLine.
     * @return Value of property commandLine.
     */
    public java.lang.String getCommandLine() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getCommandLine() : null;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getCreatedBy() : null;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedOn() : null;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        OutputCommandViewerOutDto outputDto = ((OutputCommandViewerComponent) getComponent()).getOutputCommandViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedBy() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
