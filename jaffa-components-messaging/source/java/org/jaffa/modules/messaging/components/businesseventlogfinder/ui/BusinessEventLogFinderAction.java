// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogfinder.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to BusinessEventLogFinder.
 */
public class BusinessEventLogFinderAction extends FinderAction {
    private static final Logger log = Logger.getLogger(BusinessEventLogFinderAction.class);

    /** Invokes the do_Rows_View_Clicked() method.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the View screen.
     */ 
    public FormKey do_Rows_Clicked(String rowNum) {
        return do_Rows_View_Clicked(rowNum);
    }
    // .//GEN-END:_2_be
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
        BusinessEventLogFinderForm myForm = (BusinessEventLogFinderForm) form;
        BusinessEventLogFinderComponent myComp = (BusinessEventLogFinderComponent) myForm.getComponent();

        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                // .//GEN-END:_do_Rows_View_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_Rows_View_Clicked_2


                // .//GEN-LAST:_do_Rows_View_Clicked_2
                // .//GEN-BEGIN:_do_Rows_View_Clicked_3_be
                fk = myComp.viewObject((java.lang.String) selectedRow.get("logId"));
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
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
