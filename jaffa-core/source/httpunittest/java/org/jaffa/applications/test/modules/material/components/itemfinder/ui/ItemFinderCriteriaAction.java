// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.components.finder.*;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderInDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The Action class for handling events related to the Criteria screen.
 */
public class ItemFinderCriteriaAction extends ActionBase {
    private static final Logger log = Logger.getLogger(ItemFinderCriteriaAction.class);

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_do_Search_Clicked_1_be
    /** Creates a criteria object and invokes the displayResults() method on the component.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_Search_Clicked() {
        FormKey fk = null;
        // .//GEN-END:_do_Search_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_Search_Clicked_1


        // .//GEN-LAST:_do_Search_Clicked_1
        // .//GEN-BEGIN:_do_Search_Clicked_2_be
        ItemFinderCriteriaForm myForm = (ItemFinderCriteriaForm) form;
        ItemFinderComponent myComp = (ItemFinderComponent) myForm.getComponent();

        if (myForm.doValidate(request)) {
            try {
                // .//GEN-END:_do_Search_Clicked_2_be
                // Add custom code before invoking the component //GEN-FIRST:_do_Search_Clicked_2


                // .//GEN-LAST:_do_Search_Clicked_2
                // .//GEN-BEGIN:_do_Search_Clicked_3_be
                fk = myComp.displayResults();
                if (FinderComponent.EXPORT_TYPE_XML.equals(myComp.getExportType())) {
                    Object dto = myComp.getItemFinderOutDto();
                    if (dto == null)
                        dto = "";
                    else
                        dto = dto.toString();
                    request.setAttribute(FinderComponent.ATTRIBUTE_EXPORT_TYPE_XML, dto);
                } else
                    request.removeAttribute(FinderComponent.ATTRIBUTE_EXPORT_TYPE_XML);
            } catch (ApplicationExceptions e){
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // .//GEN-END:_do_Search_Clicked_3_be
        // Add custom code after returning from the component //GEN-FIRST:_do_Search_Clicked_3


        // .//GEN-LAST:_do_Search_Clicked_3
        // .//GEN-BEGIN:_do_Search_Clicked_4_be
        // Direct User back to current form
        if (fk == null)
            fk = new FormKey(myForm.NAME, myComp.getComponentId());
        return fk;
    }
    // .//GEN-END:_do_Search_Clicked_4_be
    // .//GEN-BEGIN:_do_Close_Clicked_1_be
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        // .//GEN-END:_do_Close_Clicked_1_be
        // Add custom code before processing the action //GEN-FIRST:_do_Close_Clicked_1


        // .//GEN-LAST:_do_Close_Clicked_1
        // .//GEN-BEGIN:_do_Close_Clicked_2_be
        return ((ItemFinderCriteriaForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    // .//GEN-END:_do_Close_Clicked_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
