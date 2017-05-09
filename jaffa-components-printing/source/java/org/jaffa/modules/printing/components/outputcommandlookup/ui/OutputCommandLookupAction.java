// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.outputcommandlookup.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.modules.printing.components.outputcommandlookup.dto.OutputCommandLookupInDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to OutputCommandLookup.
 */
public class OutputCommandLookupAction extends LookupAction {
    private static final Logger log = Logger.getLogger(OutputCommandLookupAction.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_do_CreateFromCriteria_Clicked_1_be
    /** Invokes the createFromCriteria() method on the component.
     * @return The FormKey for the Create screen.
     */    
    public FormKey do_CreateFromCriteria_Clicked() {
        FormKey fk = null;
        // .//GEN-END:_do_CreateFromCriteria_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_CreateFromCriteria_Clicked_1


        // .//GEN-LAST:_do_CreateFromCriteria_Clicked_1
        // .//GEN-BEGIN:_do_CreateFromCriteria_Clicked_2_be
        OutputCommandLookupForm myForm = (OutputCommandLookupForm) form;
        OutputCommandLookupComponent myComp = (OutputCommandLookupComponent) myForm.getComponent();
        
        if (myForm.doValidate(request)) {
            try {
                // .//GEN-END:_do_CreateFromCriteria_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_CreateFromCriteria_Clicked_2


                // .//GEN-LAST:_do_CreateFromCriteria_Clicked_2
                // .//GEN-BEGIN:_do_CreateFromCriteria_Clicked_3_be
                fk = myComp.createFromCriteria();
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Create Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:_do_CreateFromCriteria_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_CreateFromCriteria_Clicked_3


        // .//GEN-LAST:_do_CreateFromCriteria_Clicked_3
        // .//GEN-BEGIN:_do_CreateFromCriteria_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getCriteriaFormKey();
        return fk;
    }
    // .//GEN-END:_do_CreateFromCriteria_Clicked_4_be
    // .//GEN-BEGIN:_do_CreateFromResults_Clicked_1_be
    /** Invokes the createFromResults() method on the component.
     * @return The FormKey for the Create screen.
     */    
    public FormKey do_CreateFromResults_Clicked() {
        FormKey fk = null;
        // .//GEN-END:_do_CreateFromResults_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_CreateFromResults_Clicked_1


        // .//GEN-LAST:_do_CreateFromResults_Clicked_1
        // .//GEN-BEGIN:_do_CreateFromResults_Clicked_2_be
        OutputCommandLookupForm myForm = (OutputCommandLookupForm) form;
        OutputCommandLookupComponent myComp = (OutputCommandLookupComponent) myForm.getComponent();
        
        try {
            // .//GEN-END:_do_CreateFromResults_Clicked_2_be
            // Add custom code before invoking the component //GEN-FIRST:_do_CreateFromResults_Clicked_2


            // .//GEN-LAST:_do_CreateFromResults_Clicked_2
            // .//GEN-BEGIN:_do_CreateFromResults_Clicked_3_be
            fk = myComp.createFromResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Create Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // .//GEN-END:_do_CreateFromResults_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_CreateFromResults_Clicked_3


        // .//GEN-LAST:_do_CreateFromResults_Clicked_3
        // .//GEN-BEGIN:_do_CreateFromResults_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    // .//GEN-END:_do_CreateFromResults_Clicked_4_be
    // .//GEN-BEGIN:_do_Rows_View_Clicked_1_be
    /** Invokes the viewObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the View screen.
     */    
    public FormKey do_Rows_View_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:_do_Rows_View_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_Rows_View_Clicked_1


        // .//GEN-LAST:_do_Rows_View_Clicked_1
        // .//GEN-BEGIN:_do_Rows_View_Clicked_2_be
        OutputCommandLookupForm myForm = (OutputCommandLookupForm) form;
        OutputCommandLookupComponent myComp = (OutputCommandLookupComponent) myForm.getComponent();

        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                // .//GEN-END:_do_Rows_View_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_Rows_View_Clicked_2


                // .//GEN-LAST:_do_Rows_View_Clicked_2
                // .//GEN-BEGIN:_do_Rows_View_Clicked_3_be
                fk = myComp.viewObject((java.lang.Long) selectedRow.get("outputCommandId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Viewer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:_do_Rows_View_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_Rows_View_Clicked_3


        // .//GEN-LAST:_do_Rows_View_Clicked_3
        // .//GEN-BEGIN:_do_Rows_View_Clicked_4_be
        // The Viewer will be rendered in a new window
        // We don't want to see the existing HistoryNav in that window
        // Hence, initialize the HistoryNav
        HistoryNav.initializeHistoryNav(request);

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    // .//GEN-END:_do_Rows_View_Clicked_4_be
    // .//GEN-BEGIN:_do_Rows_Update_Clicked_1_be
    /** Invokes the updateObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Update screen.
     */    
    public FormKey do_Rows_Update_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:_do_Rows_Update_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_Rows_Update_Clicked_1


        // .//GEN-LAST:_do_Rows_Update_Clicked_1
        // .//GEN-BEGIN:_do_Rows_Update_Clicked_2_be
        OutputCommandLookupForm myForm = (OutputCommandLookupForm) form;
        OutputCommandLookupComponent myComp = (OutputCommandLookupComponent) myForm.getComponent();

        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                // .//GEN-END:_do_Rows_Update_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_Rows_Update_Clicked_2


                // .//GEN-LAST:_do_Rows_Update_Clicked_2
                // .//GEN-BEGIN:_do_Rows_Update_Clicked_3_be
                fk = myComp.updateObject((java.lang.Long) selectedRow.get("outputCommandId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Update Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:_do_Rows_Update_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_Rows_Update_Clicked_3


        // .//GEN-LAST:_do_Rows_Update_Clicked_3
        // .//GEN-BEGIN:_do_Rows_Update_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    // .//GEN-END:_do_Rows_Update_Clicked_4_be
    // .//GEN-BEGIN:_do_Rows_Delete_Clicked_1_be
    /** Invokes the deleteObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Delete screen.
     */    
    public FormKey do_Rows_Delete_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:_do_Rows_Delete_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_Rows_Delete_Clicked_1


        // .//GEN-LAST:_do_Rows_Delete_Clicked_1
        // .//GEN-BEGIN:_do_Rows_Delete_Clicked_2_be
        OutputCommandLookupForm myForm = (OutputCommandLookupForm) form;
        OutputCommandLookupComponent myComp = (OutputCommandLookupComponent) myForm.getComponent();
        try {
            // This will stop double submits
            performTokenValidation(request);
            GridModel model = (GridModel) myForm.getRowsWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null) {
                // .//GEN-END:_do_Rows_Delete_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_Rows_Delete_Clicked_2


                // .//GEN-LAST:_do_Rows_Delete_Clicked_2
                // .//GEN-BEGIN:_do_Rows_Delete_Clicked_3_be
                fk = myComp.deleteObject((java.lang.Long) selectedRow.get("outputCommandId"));
            }
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Delete Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // .//GEN-END:_do_Rows_Delete_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_Rows_Delete_Clicked_3


        // .//GEN-LAST:_do_Rows_Delete_Clicked_3
        // .//GEN-BEGIN:_do_Rows_Delete_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    // .//GEN-END:_do_Rows_Delete_Clicked_4_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
