package org.jaffa.modules.messaging.components.queuelist.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;

/** The Action class for handling events related to ProcessLogFinder.
 */
public class QueueListAction extends ActionBase {
    private static final Logger log = Logger.getLogger(QueueListAction.class);
    
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        return ((QueueListForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    
    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        QueueListForm myForm = (QueueListForm) form;
        QueueListComponent myComp = (QueueListComponent) myForm.getComponent();
        return myComp.getResultsFormKey();
    }
    
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        QueueListForm myForm = (QueueListForm) form;
        QueueListComponent myComp = (QueueListComponent) myForm.getComponent();
        
        try {
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Refresh Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    public FormKey do_Rows_ViewPendingMessages_Clicked(String rowNum) {
        return viewMessages(rowNum, MessageModeEnum.PENDING);
    }
    
    public FormKey do_Rows_ViewErrorMessages_Clicked(String rowNum) {
        return viewMessages(rowNum, MessageModeEnum.ERROR);
    }
    
    public FormKey do_Rows_ViewInProcessMessages_Clicked(String rowNum) {
        return viewMessages(rowNum, MessageModeEnum.IN_PROCESS);
    }

    private FormKey viewMessages(String rowNum, MessageModeEnum messageMode) {
        FormKey fk = null;
        QueueListForm myForm = (QueueListForm) form;
        QueueListComponent myComp = (QueueListComponent) myForm.getComponent();
        
        try {
            // This will stop double submits
            performTokenValidation(request);
            
            GridModel model = (GridModel) myForm.getRowsWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null)
                fk = myComp.viewMessages((java.lang.String) selectedRow.get("queue"), messageMode);
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("ViewErrorMessages Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
}
