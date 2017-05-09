// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formusageviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.formusageviewer.dto.FormUsageViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the FormUsageViewer.
 */
public class FormUsageViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_formUsageViewer";
    private static Logger log = Logger.getLogger(FormUsageViewerForm.class);


    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getFormName() : null;
    }

    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getEventName() : null;
    }

    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getFormAlternate() : null;
    }

    /** Getter for property copies.
     * @return Value of property copies.
     */
    public java.lang.Long getCopies() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getCopies() : null;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getCreatedBy() : null;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedOn() : null;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        FormUsageViewerOutDto outputDto = ((FormUsageViewerComponent) getComponent()).getFormUsageViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedBy() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
