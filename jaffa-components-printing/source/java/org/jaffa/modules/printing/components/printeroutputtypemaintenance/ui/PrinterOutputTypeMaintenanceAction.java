// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypemaintenance.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.components.maint.MaintAction;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.presentation.portlet.widgets.controller.PropertyEditorHelper;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to the Maintenance screen.
 */
public class PrinterOutputTypeMaintenanceAction extends MaintAction {

    private static final Logger log = Logger.getLogger(PrinterOutputTypeMaintenanceAction.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:do_RelatedOutputCommand_Create_Clicked_1_be
    /** Invokes the createOutputCommand() method on the component.
     * @return The FormKey for the Create screen of the OutputCommand object.
     */    
    public FormKey do_RelatedOutputCommand_Create_Clicked() {
        FormKey fk = null;
        // .//GEN-END:do_RelatedOutputCommand_Create_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:do_RelatedOutputCommand_Create_Clicked_1


        // .//GEN-LAST:do_RelatedOutputCommand_Create_Clicked_1
        // .//GEN-BEGIN:do_RelatedOutputCommand_Create_Clicked_2_be
        PrinterOutputTypeMaintenanceForm myForm = (PrinterOutputTypeMaintenanceForm) form;
        PrinterOutputTypeMaintenanceComponent myComp = (PrinterOutputTypeMaintenanceComponent) myForm.getComponent();

        try {
            // .//GEN-END:do_RelatedOutputCommand_Create_Clicked_2_be
            // Add custom code before invoking the component //GEN-FIRST:do_RelatedOutputCommand_Create_Clicked_2


            // .//GEN-LAST:do_RelatedOutputCommand_Create_Clicked_2
            // .//GEN-BEGIN:do_RelatedOutputCommand_Create_Clicked_3_be
            fk = myComp.createOutputCommand();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Createer Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // .//GEN-END:do_RelatedOutputCommand_Create_Clicked_3_be
        // Add custom code before returning //GEN-FIRST:do_RelatedOutputCommand_Create_Clicked_3


        // .//GEN-LAST:do_RelatedOutputCommand_Create_Clicked_3
        // .//GEN-BEGIN:do_RelatedOutputCommand_Create_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }
    // .//GEN-END:do_RelatedOutputCommand_Create_Clicked_4_be
    // .//GEN-BEGIN:do_RelatedOutputCommand_Update_Clicked_1_be
    /** Invokes the updateOutputCommand() method on the component.
     * @param rowNum The selected row on the Grid.
     * @return The FormKey for the Update screen of the OutputCommand object.
     */    
    public FormKey do_RelatedOutputCommand_Update_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:do_RelatedOutputCommand_Update_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:do_RelatedOutputCommand_Update_Clicked_1


        // .//GEN-LAST:do_RelatedOutputCommand_Update_Clicked_1
        // .//GEN-BEGIN:do_RelatedOutputCommand_Update_Clicked_2_be
        PrinterOutputTypeMaintenanceForm myForm = (PrinterOutputTypeMaintenanceForm) form;
        PrinterOutputTypeMaintenanceComponent myComp = (PrinterOutputTypeMaintenanceComponent) myForm.getComponent();

        GridModel model = (GridModel) myForm.getRelatedOutputCommandWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                // .//GEN-END:do_RelatedOutputCommand_Update_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:do_RelatedOutputCommand_Update_Clicked_2


                // .//GEN-LAST:do_RelatedOutputCommand_Update_Clicked_2
                // .//GEN-BEGIN:do_RelatedOutputCommand_Update_Clicked_3_be
                fk = myComp.updateOutputCommand((java.lang.Long) selectedRow.get("outputCommandId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Updateer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:do_RelatedOutputCommand_Update_Clicked_3_be
        // Add custom code before returning //GEN-FIRST:do_RelatedOutputCommand_Update_Clicked_3


        // .//GEN-LAST:do_RelatedOutputCommand_Update_Clicked_3
        // .//GEN-BEGIN:do_RelatedOutputCommand_Update_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }
    // .//GEN-END:do_RelatedOutputCommand_Update_Clicked_4_be
    // .//GEN-BEGIN:do_RelatedOutputCommand_Delete_Clicked_1_be
    /** Invokes the deleteOutputCommand() method on the component.
     * @param rowNum The selected row on the Grid.
     * @return The FormKey for the Delete screen of the OutputCommand object.
     */    
    public FormKey do_RelatedOutputCommand_Delete_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:do_RelatedOutputCommand_Delete_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:do_RelatedOutputCommand_Delete_Clicked_1


        // .//GEN-LAST:do_RelatedOutputCommand_Delete_Clicked_1
        // .//GEN-BEGIN:do_RelatedOutputCommand_Delete_Clicked_2_be
        PrinterOutputTypeMaintenanceForm myForm = (PrinterOutputTypeMaintenanceForm) form;
        PrinterOutputTypeMaintenanceComponent myComp = (PrinterOutputTypeMaintenanceComponent) myForm.getComponent();

        try {
            // This will stop double submits
            performTokenValidation(request);
            
            GridModel model = (GridModel) myForm.getRelatedOutputCommandWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null) {
                // .//GEN-END:do_RelatedOutputCommand_Delete_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:do_RelatedOutputCommand_Delete_Clicked_2


                // .//GEN-LAST:do_RelatedOutputCommand_Delete_Clicked_2
                // .//GEN-BEGIN:do_RelatedOutputCommand_Delete_Clicked_3_be
                fk = myComp.deleteOutputCommand((java.lang.Long) selectedRow.get("outputCommandId"));
            }
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Deleteer Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // .//GEN-END:do_RelatedOutputCommand_Delete_Clicked_3_be
        // Add custom code before returning //GEN-FIRST:do_RelatedOutputCommand_Delete_Clicked_3


        // .//GEN-LAST:do_RelatedOutputCommand_Delete_Clicked_3
        // .//GEN-BEGIN:do_RelatedOutputCommand_Delete_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }
    // .//GEN-END:do_RelatedOutputCommand_Delete_Clicked_4_be
    // All the custom code goes here //GEN-FIRST:_custom



    // .//GEN-LAST:_custom
}
