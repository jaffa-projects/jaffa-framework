/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.apache.struts.action.ActionMessages;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.EventHandlerMissingRuntimeException;

/** The Action class for handling events.
 */
public class ViewerHyperlinkConfigAction extends ActionBase {

    private static final Logger log = Logger.getLogger(ViewerHyperlinkConfigAction.class);


    /** Clicked event handler for the field Save.
     * @return The FormKey.
     */
    public FormKey do_Save_Clicked() {
        FormKey fk = null;
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();

        try {
            if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
                if (myComp.getCurrentScreenCounter() == 0)
                    myComp.storeDomainFieldViewerComponentMappingFile();
                else
                    myComp.storeKeyFieldPerViewerComponentFile();
            }
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Save Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        }

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Finish.
     * @return The FormKey.
     */
    public FormKey do_Finish_Clicked() {
        FormKey fk = null;
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();
        fk = do_Save_Clicked();
        if (!myForm.hasErrors(request))
            fk = myComp.quitAndReturnToCallingScreen();

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Refresh.
     * @return The FormKey.
     */
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();

        try {
            if (myComp.getCurrentScreenCounter() == 0)
                myComp.loadDomainFieldViewerComponentMappingFile();
            else
                myComp.loadKeyFieldPerViewerComponentFile();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Refresh Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        }

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }



    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();
        return myComp.determineFormKey();
    }

    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the calling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Cancel_Clicked() {
        return component.quitAndReturnToCallingScreen();
    }

    /** This will invoke the doValidate() method for the current screen.
     * It then moves to the next screen
     * @return The FormKey for the next screen.
     */
    public FormKey do_Next_Clicked() {
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();

        if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
            myComp.determineAndSetNextScreen();
        }
        return myComp.determineFormKey();
    }

    /** It moves to the previous screen
     * @return The FormKey for the previous screen.
     */
    public FormKey do_Previous_Clicked() {
        ViewerHyperlinkConfigForm myForm = (ViewerHyperlinkConfigForm) form;
        ViewerHyperlinkConfigComponent myComp = (ViewerHyperlinkConfigComponent) myForm.getComponent();
        myComp.determineAndSetPreviousScreen();
        return myComp.determineFormKey();
    }

    /* This will invoke the 'public boolean doValidate{n}(HttpServletRequest request)' method
     */
    private boolean invokeDoValidateForScreen(int screenCounter) {
        String methodName = "doValidate" + screenCounter;
        Method method = null;
        try {
            method = form.getClass().getMethod(methodName, new Class[] {HttpServletRequest.class});
            if (method.getReturnType() != Boolean.TYPE)
                throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            String str = "The method 'public boolean doValidate" + screenCounter + "(HttpServletRequest request) needs to be defined in the class " + form.getClass().getName();
            log.error(str, e);
            throw new EventHandlerMissingRuntimeException(str);
        }

        try {
            Boolean output = (Boolean) method.invoke(form, new Object[] {request});
            return output.booleanValue();
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }

}
