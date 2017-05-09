// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formselectionmaintenance.ui;

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
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceInDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.apache.struts.action.ActionErrors;

import org.jaffa.modules.printing.components.formselectionmaintenance.FormSelectionException;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to FormSelectionMaintenance.
 */
public class FormSelectionMaintenanceAction extends FinderAction {
    private static final Logger log = Logger.getLogger(FormSelectionMaintenanceAction.class);

    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    public FormKey do_CancelResult_Clicked() {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();

        if (fk == null) {
            fk = myComp.quitAndReturnToCallingScreen();
        }
        return fk;
    }

    public FormKey do_Print_Clicked() {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();
        GridModel model = myForm.getRowsWM();
        try {
            fk = myComp.doPrintForms(model);
        } catch (ApplicationExceptions applicationExceptions) {
            myForm.raiseError(request, "APPLICATION EXCEPTION OCCURED", applicationExceptions);
        } catch (FrameworkException frameworkException) {
            myForm.raiseError(request, "FRAMEWORK EXCEPTION OCCURED", "error.framework.general");
        }
        if (fk == null) {
            fk = myComp.getResultsFormKey();
        }
        return fk;
    }

    public FormKey do_Finish_Clicked() {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();
        GridModel model = myForm.getRowsWM();
        try {
            if (myComp.getDirectDisplay() && !(myComp.getPrinting() || myComp.getEmail() || myComp.getWebPublish())) {
                fk = myComp.doShowForm(model);
            } else {
                fk = myComp.doPrintForms(model);
            }
        } catch (ApplicationExceptions applicationExceptions) {
            myForm.raiseError(request, "APPLICATION EXCEPTION OCCURED", applicationExceptions);
            fk = myComp.getResultsFormKey();
        } catch (FrameworkException frameworkException) {
            myForm.raiseError(request, "FRAMEWORK EXCEPTION OCCURED", "error.framework.general");
            fk = myComp.getResultsFormKey();
        }
        if (fk == null) {
            fk = myComp.quitAndReturnToCallingScreen();
        }
        return fk;
    }

    public FormKey do_Rows_UpdateDetail_Clicked(String rowNum) {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.doUpdateDetail(selectedRow, this.mapping.getPath());
            } catch (ApplicationExceptions e) {
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general");
            }
        }
        if (fk == null) {
            fk = myComp.getResultsFormKey();
        }
        return fk;
    }

    public FormKey do_Rows_ShowForm_Clicked(String rowNum) {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                myComp.setFileNames("");
                fk = myComp.doShowForm(selectedRow);
            } catch (ApplicationExceptions e) {
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general");
            }
        }
        if (fk == null) {
            fk = myComp.getResultsFormKey();
        }
        return fk;
    }

    public FormKey do_Preview_Clicked() {
        FormKey fk = null;
        FormSelectionMaintenanceForm myForm = (FormSelectionMaintenanceForm) form;
        FormSelectionMaintenanceComponent myComp = (FormSelectionMaintenanceComponent) myForm.getComponent();
        GridModel model = myForm.getRowsWM();
        try {
            fk = myComp.doShowForm(model);
        } catch (ApplicationExceptions applicationExceptions) {
            myForm.raiseError(request, "APPLICATION EXCEPTION OCCURED", applicationExceptions);
        } catch (FrameworkException frameworkException) {
            myForm.raiseError(request, "FRAMEWORK EXCEPTION OCCURED", "error.framework.general");
        }
        return fk;
    }
    // .//GEN-LAST:_custom
}
