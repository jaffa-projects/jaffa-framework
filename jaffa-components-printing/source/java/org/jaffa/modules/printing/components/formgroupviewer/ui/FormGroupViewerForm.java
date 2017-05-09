// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.formgroupviewer.dto.FormGroupViewerOutDto;

import java.util.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.modules.printing.components.formgroupviewer.dto.FormUsageDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the FormGroupViewer.
 */
public class FormGroupViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_formGroupViewer";
    private static Logger log = Logger.getLogger(FormGroupViewerForm.class);


    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        FormGroupViewerOutDto outputDto = ((FormGroupViewerComponent) getComponent()).getFormGroupViewerOutDto();
        return outputDto != null ? outputDto.getFormName() : null;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        FormGroupViewerOutDto outputDto = ((FormGroupViewerComponent) getComponent()).getFormGroupViewerOutDto();
        return outputDto != null ? outputDto.getDescription() : null;
    }

    /** Getter for property formType.
     * @return Value of property formType.
     */
    public java.lang.String getFormType() {
        FormGroupViewerOutDto outputDto = ((FormGroupViewerComponent) getComponent()).getFormGroupViewerOutDto();
        return outputDto != null ? outputDto.getFormType() : null;
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:RelatedFormUsage_1_be
    /** Getter for property FormUsage. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property formUsage.
     */
    public GridModel getRelatedFormUsageWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedFormUsage");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedFormUsage(rows);
            getWidgetCache().addModel("relatedFormUsage", rows);
        }
        return rows;
    }
    
    /** Setter for property formUsage. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property formUsage.
     */
    public void setRelatedFormUsageWV(String value) {
        GridController.updateModel(value, getRelatedFormUsageWM());
    }
    
    private void populateRelatedFormUsage(GridModel rows) {
        rows.clearRows();
        FormGroupViewerOutDto outputDto = ((FormGroupViewerComponent) getComponent()).getFormGroupViewerOutDto();
        if (outputDto != null) {
            GridModelRow row = null;
            FormUsageDto[] formUsage = outputDto.getFormUsage();
            for (int i = 0; i < formUsage.length; i++) {
                FormUsageDto rowDto = formUsage[i];
                row = rows.newRow();
                row.addElement("eventName", rowDto.getEventName());
                row.addElement("formAlternate", rowDto.getFormAlternate());
                row.addElement("copies", rowDto.getCopies());
                row.addElement("description", rowDto.getDescription());
                // .//GEN-END:RelatedFormUsage_1_be
                // Add custom code for the row//GEN-FIRST:RelatedFormUsage_1


                // .//GEN-LAST:RelatedFormUsage_1
                // .//GEN-BEGIN:RelatedFormUsage_2_be
            }
        }
    }
    // .//GEN-END:RelatedFormUsage_2_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
