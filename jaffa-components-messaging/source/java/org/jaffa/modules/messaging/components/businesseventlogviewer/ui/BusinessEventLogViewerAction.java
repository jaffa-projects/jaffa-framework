// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogviewer.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerOutDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.components.attachment.util.AttachmentComponentHelper;



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to the View screen.
 */
public class BusinessEventLogViewerAction extends ActionBase {

    private static final Logger log = Logger.getLogger(BusinessEventLogViewerAction.class);

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:do_RelatedAttachment_View_Clicked_1_be
    /** Invokes the do_RelatedAttachment_View_Clicked() method.
     * @param rowNum The selected row on the Grid.
     * @return The FormKey for the View screen of the Attachment object.
     */ 
    public FormKey do_RelatedAttachment_Clicked(String rowNum) {
        return do_RelatedAttachment_View_Clicked(rowNum);
    }

    /** Invokes the viewAttachment() method on the component.
     * @param rowNum The selected row on the Grid.
     * @return The FormKey for the View screen of the Attachment object.
     */    
    public FormKey do_RelatedAttachment_View_Clicked(String rowNum) {
        FormKey fk = null;
        // .//GEN-END:do_RelatedAttachment_View_Clicked_1_be
        // Add custom code before processing the action//GEN-FIRST:do_RelatedAttachment_View_Clicked_1


        // .//GEN-LAST:do_RelatedAttachment_View_Clicked_1
        // .//GEN-BEGIN:do_RelatedAttachment_View_Clicked_2_be
        BusinessEventLogViewerForm myForm = (BusinessEventLogViewerForm) form;
        BusinessEventLogViewerComponent myComp = (BusinessEventLogViewerComponent) myForm.getComponent();

        GridModel model = (GridModel) myForm.getRelatedAttachmentWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                // .//GEN-END:do_RelatedAttachment_View_Clicked_2_be
                // Add custom code before invoking the component//GEN-FIRST:do_RelatedAttachment_View_Clicked_2


                // .//GEN-LAST:do_RelatedAttachment_View_Clicked_2
                // .//GEN-BEGIN:do_RelatedAttachment_View_Clicked_3_be
                fk = myComp.viewAttachment((java.lang.String) selectedRow.get("attachmentId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Viewer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:do_RelatedAttachment_View_Clicked_3_be
        // Add custom code before returning//GEN-FIRST:do_RelatedAttachment_View_Clicked_3


        // .//GEN-LAST:do_RelatedAttachment_View_Clicked_3
        // .//GEN-BEGIN:do_RelatedAttachment_View_Clicked_4_be
        // The Viewer will be rendered in a new window
        // We don't want to see the existing HistoryNav in that window
        // Hence, initialize the HistoryNav
        HistoryNav.initializeHistoryNav(request);

        // Direct User back to current form
        if (fk == null)
            fk = new FormKey(myForm.NAME, myComp.getComponentId());
        return fk;
    }
    // .//GEN-END:do_RelatedAttachment_View_Clicked_4_be
    // .//GEN-BEGIN:_do_Close_Clicked_1_be
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */    
    public FormKey do_Close_Clicked() {
        // .//GEN-END:_do_Close_Clicked_1_be
        // Add custom code before processing the action//GEN-FIRST:_do_Close_Clicked_1


        // .//GEN-LAST:_do_Close_Clicked_1
        // .//GEN-BEGIN:_do_Close_Clicked_2_be
        return ((BusinessEventLogViewerForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    // .//GEN-END:_do_Close_Clicked_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    /** Creates a temporary file with the attachment data and adds it as an attribute to the request stream.
     * Returns a FormKey pointing to Jaffa's FileExplorer servlet which downloads the temporary file.
     * @param rowNum The selected row on the Results screen.
     * @return the FormKey for the FileExplorer servlet. A null is returned if the input data is null.
     */
    public FormKey do_RelatedAttachment_ViewAttachmentData_Clicked(String rowNum){
        FormKey fk = null;
        BusinessEventLogViewerForm myForm = (BusinessEventLogViewerForm) form;
        BusinessEventLogViewerComponent myComp = (BusinessEventLogViewerComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRelatedAttachmentWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null)
            fk = AttachmentComponentHelper.loadAttachmentData(myForm, request, (String) selectedRow.get("originalFileName"), (byte[]) selectedRow.get("data"));
        if (fk == null)
            fk = myComp.getViewerFormKey();
        return fk;
    }





    // .//GEN-LAST:_custom
}
