package org.jaffa.modules.messaging.components.messageviewer.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;


/** The Action class for handling events related to the View screen.
 */
public class MessageViewerAction extends ActionBase {
    
    private static final Logger log = Logger.getLogger(MessageViewerAction.class);
    
    
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        return ((MessageViewerForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    
    public FormKey do_ChangePriority_Clicked() {
        FormKey fk = null;
        MessageViewerForm myForm = (MessageViewerForm) form;
        MessageViewerComponent myComp = (MessageViewerComponent) myForm.getComponent();
        
        try {
            if(myForm.doValidate(request))
                fk = myComp.changePriority();
            
            //Invoke MessageView Listener
            myComp.invokeMessageViewerListeners();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Change Priority Failed");
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
    
    /** This will increment the value of the property MaxRecords. It then re-executes the search, using the same criteria as used before.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_MoreRecords_Clicked() {
        FormKey fk = null;
        MessageViewerForm myForm = (MessageViewerForm) form;
        MessageViewerComponent myComp = (MessageViewerComponent) myForm.getComponent();
        
        try {
            fk = myComp.displayBusinessEventLogFinder();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Search for More Records Failed");
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
    
    /** Invokes the viewObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the View screen.
     */
    public FormKey do_RelatedBusinessEventLog_View_Clicked(String rowNum) {
        FormKey fk = null;
        MessageViewerForm myForm = (MessageViewerForm) form;
        MessageViewerComponent myComp = (MessageViewerComponent) myForm.getComponent();
        
        GridModel model = (GridModel) myForm.getRelatedBusinessEventLogWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.displayBusinessEventLogViewer((String) selectedRow.get("logId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Viewer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
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
}
