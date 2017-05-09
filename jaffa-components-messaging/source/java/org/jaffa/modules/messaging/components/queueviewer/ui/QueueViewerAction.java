package org.jaffa.modules.messaging.components.queueviewer.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;

/** The Action class for handling events related to the View screen.
 */
public class QueueViewerAction extends ActionBase {
    
    private static final Logger log = Logger.getLogger(QueueViewerAction.class);
    
    
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        return ((QueueViewerForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        QueueViewerForm myForm = (QueueViewerForm) form;
        QueueViewerComponent myComp = (QueueViewerComponent) myForm.getComponent();
        
        try {
            if(myForm.doValidate(request))
                myComp.doInquiry();
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
            fk = myComp.getViewerFormKey();
        return fk;
    }
    
    public FormKey do_RelatedQueueHeader_View_Clicked(String rowNum) {
        FormKey fk = null;
        QueueViewerForm myForm = (QueueViewerForm) form;
        QueueViewerComponent myComp = (QueueViewerComponent) myForm.getComponent();
        
        try {
            GridModel model = (GridModel) myForm.getRelatedQueueHeaderWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null)
                fk = myComp.viewMessage((java.lang.String) selectedRow.get("messageId"));
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("RelatedQueueHeader_View Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        // The Viewer will be rendered in a new window
        // We don't want to see the existing HistoryNav in that window
        // Hence, initialize the HistoryNav
        HistoryNav.initializeHistoryNav(request);

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getViewerFormKey();
        return fk;
    }
    
    public FormKey do_RelatedQueueHeader_Delete_Clicked(String rowNum) {
        FormKey fk = null;
        QueueViewerForm myForm = (QueueViewerForm) form;
        QueueViewerComponent myComp = (QueueViewerComponent) myForm.getComponent();
        
        try {
            // This will stop double submits
            performTokenValidation(request);
            
            GridModel model = (GridModel) myForm.getRelatedQueueHeaderWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null)
                fk = myComp.deleteMessage((java.lang.String) selectedRow.get("messageId"));
            
            //Invoke QueueView Listener
            myComp.invokeQueueViewerListeners();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("RelatedQueueHeader_Delete Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getViewerFormKey();
        return fk;
    }
    
    public FormKey do_RelatedQueueHeader_ReSubmit_Clicked(String rowNum) {
        FormKey fk = null;
        QueueViewerForm myForm = (QueueViewerForm) form;
        QueueViewerComponent myComp = (QueueViewerComponent) myForm.getComponent();
        
        try {
            // This will stop double submits
            performTokenValidation(request);
            
            GridModel model = (GridModel) myForm.getRelatedQueueHeaderWM();
            GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
            if (selectedRow != null)
                fk = myComp.resubmitMessage((java.lang.String) selectedRow.get("messageId"));
            
            //Invoke QueueView Listener
            myComp.invokeQueueViewerListeners();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("RelatedQueueHeader_ReSubmit Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getViewerFormKey();
        return fk;
    }
}
