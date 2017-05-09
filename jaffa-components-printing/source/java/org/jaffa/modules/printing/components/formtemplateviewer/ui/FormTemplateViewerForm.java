// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplateviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.formtemplateviewer.dto.FormTemplateViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the FormTemplateViewer.
 */
public class FormTemplateViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_formTemplateViewer";
    private static Logger log = Logger.getLogger(FormTemplateViewerForm.class);


    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        FormTemplateViewerOutDto outputDto = ((FormTemplateViewerComponent) getComponent()).getFormTemplateViewerOutDto();
        return outputDto != null ? outputDto.getFormId() : null;
    }

    /** Getter for property templateData.
     * @return Value of property templateData.
     */
    public byte[] getTemplateData() {
        FormTemplateViewerOutDto outputDto = ((FormTemplateViewerComponent) getComponent()).getFormTemplateViewerOutDto();
        return outputDto != null ? outputDto.getTemplateData() : null;
    }

    /** Getter for property layoutData.
     * @return Value of property layoutData.
     */
    public byte[] getLayoutData() {
        FormTemplateViewerOutDto outputDto = ((FormTemplateViewerComponent) getComponent()).getFormTemplateViewerOutDto();
        return outputDto != null ? outputDto.getLayoutData() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
