// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupmaintenance.ui;

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

import org.jaffa.presentation.portlet.widgets.model.*;
import java.util.*;
import org.jaffa.components.maint.*;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to the Maintenance screen.
 */
public class FormGroupMaintenanceAction extends MaintAction {

    private static final Logger log = Logger.getLogger(FormGroupMaintenanceAction.class);
    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    //For adding the Formusage rows from the FormEventLookups
    public FormKey do_AddRelatedFormUsage_Clicked() {
        FormKey fk = null;
        FormGroupMaintenanceForm myForm = (FormGroupMaintenanceForm) form;
        FormGroupMaintenanceComponent myComp = (FormGroupMaintenanceComponent) myForm.getComponent();

        if (myForm.doValidate(request)) {
            try {
                fk = myComp.invokeFormEventLookup();
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }
    //For removing the Formusage rows
    public FormKey do_RemoveRelatedFormUsage_Clicked() {
        FormGroupMaintenanceForm myForm = (FormGroupMaintenanceForm) form;
        FormGroupMaintenanceComponent myComp = (FormGroupMaintenanceComponent) myForm.getComponent();
        Collection rows = myForm.getRelatedFormUsageWM().getRows();
        ArrayList arr=new ArrayList();
        String tc = null;
        if (rows != null) {
            for (Iterator itr = rows.iterator(); itr.hasNext(); ) {
                GridModelRow row = (GridModelRow) itr.next();
                if (((CheckBoxModel) row.get("select")).getState()) {
                    tc = (String) row.get("eventName");
                    arr.add(tc);
                    // remove the row from the model
                    itr.remove();
                }

            }
        }
        GridModel model = myForm.getRelatedFormUsageWM();
        if(model != null){
            try {
                myComp.removeFormUsageEntry(arr,model);
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        return myComp.determineFormKey();
    }
    // perform validations before saving the data
    public FormKey do_Save_Clicked() {
        FormKey fk = null;
        FormGroupMaintenanceForm myForm = (FormGroupMaintenanceForm) form;
        FormGroupMaintenanceComponent myComp = (FormGroupMaintenanceComponent) myForm.getComponent();
        // Check for marked rows and delete
        String description = myForm.getDescriptionWM().getValue();
        String formtype = myForm.getFormTypeWM().getValue();
        if( myComp.getMode() == IMaintComponent.MODE_UPDATE ){
            do_RemoveRelatedFormUsage_Clicked();
        }
        myForm.setDescriptionWV(description);
        myForm.setFormTypeWV(formtype);

        if (!myForm.hasErrors(request)){
            try {
                if( myComp.getMode() == IMaintComponent.MODE_UPDATE ){
                    GridModel model = myForm.getRelatedFormUsageWM();
                    //GridModel model = (GridModel) myForm.getWidgetCache().getModel("relatedFormUsage");
                    if(model != null && !myComp.processRows(model)){
                        myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "exception.Jaffa.Printing.FormGroupMaintenance.formAlternateValidation" );
                    }else{
                        fk = super.do_Save_Clicked();
                    }
                }else{
                    fk = super.do_Save_Clicked();
                }

            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }


    // .//GEN-LAST:_custom
}
